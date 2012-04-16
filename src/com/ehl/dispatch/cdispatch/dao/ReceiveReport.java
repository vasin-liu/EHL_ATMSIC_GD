package com.ehl.dispatch.cdispatch.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.common.util.StringUtil;

/**
 * @author wxt
 * @version1.0
 * @since 
 *
 */
public class ReceiveReport {
	private static final Logger logger = Logger.getLogger(ReceiveReport.class);
	
	/**
	 * �༭�¹ʽӱ���Ϣ
	 * @param res
	 * @return
	 */
	public boolean insertReceiveReport(List<String> res,String depttype,String state) {
		boolean flag = false;
		boolean flag1= false;
		boolean flag2 = false;
		try {
			String sql = editReceiveReportSql(res);
			String alarmid = res.get(1);
			String eventstatesql = getEventeStateSql(alarmid,depttype,state);
			List<String> sqllist = new ArrayList<String>();
			if(sql!=null&&eventstatesql!=null) {
				sqllist.add(sql);
				sqllist.add(eventstatesql);
			}else {
				return false;
			}
			flag = excuteSql(sqllist);
//			flag = DBHandler.execute(sql);
//			flag1 = DBHandler.execute(eventstatesql);
			//flag = this.excuteSql(sqllist);
//			if(flag&&flag1) {
//				flag2 = true;
//			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("�༭�¹ʽӱ���Ϣsql�쳣");
		}
		return flag;
	}
	
	/**
	 *  ���ɽӱ���ϢSQL
	 * @param res
	 * @return
	 */
	public String editReceiveReportSql(List<String> res) {
		String feedbackid = res.get(0);
		String alarmid = res.get(1);
		//�ӱ���λ
		String reciveunit = res.get(2);
		//�ӱ���ʽ
		String recivetype = res.get(3);
		//�ӱ�ʱ��
		//String recivetime = res.get(4);
		String feedbacktime =res.get(4);
		String feedbackperson =res.get(5);
		String responsibleperson =res.get(6);
		String feedbackdesc = res.get(7);
		String feedbackreport = res.get(8);
		String sql = null;
		int num = isSameReceiveReport(alarmid);
		if(num==0) {
			String flowid = "";
			if(alarmid!=null) {
				flowid= alarmid.substring(0,6);
			    flowid += StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
			}
			//String flowid = CreateSequence.getMaxForId("t_attemper_alarmflow", "FLOWID", 10);
            sql = "insert into t_attemper_alarmflow(FLOWID,ALARMID,RECIVEUNIT,RECIVETYPE,RECIVETIME,REPORTPERSON,RESPONSIBLEPERSON,VERIFYSITUATION,REPORTSITUATION)";
			sql += " values('"+flowid+"','"+alarmid+"',";
			sql += "'"+reciveunit+"','"+recivetype+"',";
			//sql += "to_date('" + recivetime + "','yyyy-mm-dd hh24:mi'),";
			sql += "to_date('" + feedbacktime + "','yyyy-mm-dd hh24:mi'),";
			sql += " '"+feedbackperson+"','"+responsibleperson+"','"+feedbackdesc+"','"+feedbackreport+"')";
		}else {
			sql = "update T_ATTEMPER_ALARMFLOW set RECIVEUNIT='"+reciveunit+"',";
			sql += "RECIVETYPE='"+recivetype+"',";
			sql += "REPORTPERSON='"+feedbackperson+"',RESPONSIBLEPERSON='"+responsibleperson+"',";
			sql += "VERIFYSITUATION='"+feedbackdesc+"',REPORTSITUATION='"+feedbackreport+"'";
			//sql += " where FLOWID='"+feedbackid+"'";
			sql +=" where ALARMID='"+alarmid+"'";
			
		}
		return sql;
	}
	/**
	 * 
	 * @param alarmid
	 * @return
	 */
	public int isSameReceiveReport(String alarmid) {
		int num  = 0;
		try {
			String sql = "select count(*) from T_ATTEMPER_ALARMFLOW where ALARMID='"+alarmid+"'";
			String count = DBHandler.getSingleResult(sql).toString();
		    num = Integer.parseInt(count);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return num;
	}
	/**
	 * ��ѯһ���ӱ�����
	 * @param flowid
	 * @return
	 */
//	public List<String> getReceiveReport(String alarmid) {
//		List<String> result = new ArrayList<String>();
//		try {
//			String sql = "select FLOWID,ALARMID,RECIVEUNIT,RECIVETYPE,RECIVETIME,REPORTPERSON,RESPONSIBLEPERSON,VERIFYSITUATION,REPORTSITUATION from t_attemper_alarmflow";
//			sql += " where ALARMID='"+alarmid+"'";
//			Object[] obj = DBHandler.getLineResult(sql);
//			if(obj!=null&&obj.length>0){
//				for(int i=0;i<obj.length;i++) {
//					result.add(StringHelper.obj2str(obj[i].toString(),""));
//					
//				}
//			}else {
//				result.add("");
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//			logger.error("��ѯ�ӱ������쳣��");
//			return null;
//		}
//		return result;
//	}
//	
	
	public Object[] getReceiveReport(String alarmid) {
		Object[] result = null;
		try {
			String sql = "select FLOWID,ALARMID,RECIVEUNIT,RECIVETYPE,RECIVETIME,REPORTPERSON,RESPONSIBLEPERSON,VERIFYSITUATION,REPORTSITUATION from t_attemper_alarmflow";
			sql += " where ALARMID='"+alarmid+"'";
			result = DBHandler.getLineResult(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * @����: wxt
	 * @�汾�ţ�1.0
	 * @����˵����
	 * @������sqlList
	 * @���أ�false or true
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public boolean excuteSql(List<String> sqlList){
//		��ʼ����
		boolean bOk = true;
		if(sqlList == null){
			return bOk;
		}
		Database db = null;
		
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			
			String sql = "";
			for(int i=0;i<sqlList.size();i++){
				sql = StringHelper.obj2str(sqlList.get(i),"");
				if(!sql.equals("") && 0 <db.executeUpdate(sql)){
				}else{
					bOk = false;
					break;
				}
			}
		} catch(Exception e) {
			bOk = false;
			logger.error("������ϵͳ��-->��ͨ�¹��ϱ����������" + e.getMessage());
			System.out.println(e.getMessage());
		}finally {
			if(null != db) {
				if(bOk) {
					db.commitTrans(false);
				} else {
					db.commitTrans(true);
				}
				//�ر����ݶ���
				DBFactory.closeDatabase(db);
			}
		}
		return bOk;
	}
	/**
	 * @����: wxt
	 * @�汾�ţ�1.0
	 * @����˵�������»���������˳��SQL
	 * @������alarmid
	 * @���أ�
	 * @�������ڣ�2010-01-12
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getEventeStateSql(String alarmid,String depttype,String state) {
		//004032 ֧���ѽ���
		//004033 ֧���Ѵ���
		//004034 �ܶ��ѽ���
		//004035 �ܶ��Ѵ���
		String sql = null;
		try {
		  //����˳��
		  String orderby = StringHelper.obj2str(DisposeFlowOrderby(alarmid),"0");
		  int num = Integer.parseInt(orderby);
		  //������
		  if("1".equals(state)){	
			if("0".equals(depttype)&&num<500) {
			   sql = "update t_attemper_alarm set eventstate='004035' where alarmid='"+alarmid+"'";
			}else if("1".equals(depttype)&&num<300) {
			   sql = "update t_attemper_alarm set eventstate='004033' where alarmid='"+alarmid+"'";
			}
		   //�������
		  }else if("0".equals(state)) {
			  if("0".equals(depttype)&&num<400) {
				   sql = "update t_attemper_alarm set eventstate='004034' where alarmid='"+alarmid+"'";
				}else if("1".equals(depttype)&&num<200) {
				   sql = "update t_attemper_alarm set eventstate='004032' where alarmid='"+alarmid+"'";
				}
		  }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sql;
		
	}
	/**
	 * @����: wxt
	 * @�汾�ţ�1.0
	 * @����˵�����ж������Ƿ�Ҫִ��,����ܶ��Ѵ���,֧�Ӳ������ٴ���.
	 * @������alarmid
	 * @���أ�
	 * @�������ڣ�2010-01-12
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String DisposeFlowOrderby(String alarmid) {
		String sql = null;
		String orderby = null;
		try {
			sql = "select orderby from t_attemper_code where id in(select eventstate from t_attemper_alarm where alarmid='"+alarmid+"')";
			orderby = DBHandler.getSingleResult(sql).toString();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return orderby;
	}
	
	/**
	 * @����: wxt
	 * @�汾�ţ�1.0
	 * @����˵���������ѽ���״̬
	 * @������alarmid
	 * @���أ�
	 * @�������ڣ�2010-01-12
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public boolean updateEventState(String alarmid) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "update t_attemper_alarm set eventstate='004012' where alarmid='"+alarmid+"'";
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
