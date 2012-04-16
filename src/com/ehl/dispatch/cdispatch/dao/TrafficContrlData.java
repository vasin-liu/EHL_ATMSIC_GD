package com.ehl.dispatch.cdispatch.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
/**
 * 
 * @author wxt
 * 
 *
 */
public class TrafficContrlData {
	
	private static final Logger logger = Logger.getLogger(TrafficContrlData.class);
	//����ʩ��ռ����Ϣ
    public boolean insertTrafficRoadInfo(Object[] res) throws Throwable {
    	// ����ָ�ӵ����¼���¼��
		//String inalarmsql = insertMainAlarmInfo(res);
		String inalarmsql = null;
		String sonsql = null;
		String alarmid = res[0].toString();
		int count = isSameRoadBuild(alarmid);
		if(count==0) {
			inalarmsql =  insertMainAlarmInfo(res);
			sonsql = insertTrafficRoadInfoSql(res);
		}
		else {
			inalarmsql =  editMainAlarmInfo(res);
			sonsql = editTrafficRoadInfoSql(res);
		}
		System.out.println("����ʩ��ռ����Ϣ sql>>>>"+sonsql);
		return executeData(inalarmsql, sonsql);
    }
    /**
     * �༭ʩ��ռ������SQL
     * @param res
     * @return
     */
    public String editMainAlarmInfo(Object[] res) {
    	String inalarmsql = "";
    	
    	inalarmsql += "update T_Attemper_Alarm" ;
    	inalarmsql += " set AlarmTime=to_date('" + res[1]+ "','yyyy-mm-dd hh24:mi'),";
    	inalarmsql += " ALARMDESC='"+res[2]+"',";
    	inalarmsql += " EVENTSTATE='"+res[3]+"',";
    	inalarmsql += " REPORTPERSON='"+res[4]+"',";
    	inalarmsql += " ALARMREGION='1',EVENTSOURCE='1',EVENTTYPE='001008',ALARMUNIT='1',TITLE='1',ALARMREGIONID='1'";
    	inalarmsql += " where alarmid='"+res[0]+"'";
		System.out.println("maininalarmsql==="+inalarmsql);
		return inalarmsql;
    	
    }
    /**
     * �༭ʩ��ռ��SQL
     * @param res
     * @return
     */
    public String editTrafficRoadInfoSql(Object[] res){
    	String sonsql = "insert into T_ATTEMPER_ROADBUILD(ALARMID,ROID,ROADBH,PROPOSER,PRINCIPAL,PHONE_P,ROADSECTION,AFTRANGE,PERMITTYPE,STARTTIME,ENDTIME,PLAN";
		sonsql += ")";
		sonsql = "update T_ATTEMPER_ROADBUILD set ROADBH='"+res[8]+"',";
		sonsql +=" PROPOSER='"+res[5]+"',PRINCIPAL='"+res[6]+"',PHONE_P='"+res[7]+"',";
		sonsql +=" ROADSECTION='"+res[9]+"',AFTRANGE='"+res[10]+"',PERMITTYPE='"+res[11]+"',";
		sonsql +=" STARTTIME=to_date('" + res[12] + "','yyyy-mm-dd hh24:mi'),";
		sonsql +=" ENDTIME=to_date('" + res[13] + "','yyyy-mm-dd hh24:mi'),";
		sonsql +=" PLAN='1'";
		sonsql += " where roid='"+res[0]+"'";
		return sonsql;
    }
    /**
     * ����ʩ��ռ��SQL
     * @param res
     * @return
     */
    public String insertTrafficRoadInfoSql(Object[] res) {
    	// �����ӱ�
    	String sonsql = "insert into T_ATTEMPER_ROADBUILD(ALARMID,ROID,ROADBH,PROPOSER,PRINCIPAL,PHONE_P,ROADSECTION,AFTRANGE,PERMITTYPE,STARTTIME,ENDTIME,PLAN";
		sonsql += ")";
		sonsql += " values('" + res[0] + "',";
		//sonsql += "to_date('" + res[24] + "','yyyy-mm-dd hh24:mi'),";
		sonsql += " '"+res[0] +"','"+res[8]+"','"+res[5]+"','"+res[6]+"','"+res[7]+"','"+res[9]+"','"+res[10]+"',";
		sonsql +=" '"+res[11]+"',";
		sonsql += "to_date('" + res[12] + "','yyyy-mm-dd hh24:mi'),";
		sonsql += "to_date('" + res[13] + "','yyyy-mm-dd hh24:mi'),";
		sonsql += "'1'";
		sonsql += ")";
		return sonsql;
    }
    
    /**
     * ����ʩ��ռ������SQL
     * @param res
     * @return
     * @throws Throwable
     */
    public String insertMainAlarmInfo(Object[] res) throws Throwable {
    	String inalarmsql = "";
		//String deptid = res[14].toString();
		//String SUPERUNIT = deptid.substring(0, 6) + StringUtil.fill("0", deptid.length()-6); //��������
		// String title=res[1]+res[13]+res[19]+res[18];
		//add by wangxt 090515 ����һ��SUBEVENTSOURCE�ֶ�
		
		//add by LDQ 091216 ����[ALARMREGION-����Ͻ��],[ALARMREGIONID-����Ͻ��id],[SUPERUNIT-������λ����Ӧ�¼��ϱ�����(�����֧��)��������֧��id]�ֶ�
		
    	inalarmsql += "insert into T_Attemper_Alarm" +
				"(AlarmID,AlarmTime,ALARMDESC,EVENTSTATE,REPORTPERSON,ALARMREGION,EVENTSOURCE,EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID)";
		inalarmsql += " values('" + res[0] + "',";
		inalarmsql += "to_date('" + res[1]+ "','yyyy-mm-dd hh24:mi'),";
		inalarmsql +=  "'" + res[2] + "','" + res[3] + "','"+ res[4] +"','1','1','001008','1','1','1')";
		System.out.println("maininalarmsql==="+inalarmsql);
		return inalarmsql;
    }
 
    /**
	 * ���������������
	 */
	public boolean executeData(String mainSql, String sonSql) throws Throwable {
		boolean msg = false;
		Database db = null;
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			int in = db.executeUpdate(mainSql);
			int conestion = 0;
			if(!sonSql.equals("")){
				conestion = db.executeUpdate(sonSql);
			}
			if (in >= 0 && conestion >= 0) {
				db.commitTrans(false);
				msg = true;
			} else {
				db.commitTrans(true);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			DBFactory.closeDatabase(db);
		}

		return msg;
	}
  
  /**
   * �༭ʩ��ռ���ֱ���Ϣ
   */
  public String editTrafficRoadSql(Object[] res) {
	  String insertsql = null;
	   try {
		   //��·���
	      // String roadid = CreateSequence.getMaxForId("T_ATTEMPER_TTATBL", "roid", 12);
		   //�ʱ��
	       String reporttime = res[0].toString();
	       //���
		   String reportpeople = res[1].toString();
		   //ռ����λ
		   String roadunit = res[2].toString();
		   //������
		   String principal = res[3].toString();
		   //��ϵ�绰
		   String telphone = res[4].toString();
		   //ռ�õ�·
		   String addressdes = res[5].toString();
		   //���·��
		   String transactor = res[6].toString();
		   //ռ�����
		   String eventstate = res[7].toString();
		   //׼�����
		   String possstate = res[8].toString();
		   
		   //ռ����ʼʱ��
		   String roadstarttime = res[9].toString();
		   //ռ������ʱ��
		   String roadendtime = res[10].toString();
		   //����״̬
		   String alarmstate = res[11].toString();
		   //�¼�����
		   String eventdes = res[12].toString();
		   String sonsql = "insert into T_ATTEMPER_ROADBUILD(ALARMID,ROID,ROADBH,PROPOSER,PRINCIPAL,PHONE_P,ROADSECTION,AFTRANGE,PERMITTYPE,STARTTIME,ENDTIME,PLAN";
		   insertsql = "update T_ATTEMPER_ROADBUILD set ROID='"+reporttime+"',ROADBH='"+reportpeople+"'";
		   insertsql += ",PROPOSER='"+roadunit+"',PRINCIPAL='"+principal+"',PHONE_P='"+telphone+"'";
		   insertsql += "ROADSECTION = '"+transactor+"',AFTRANGE='"+possstate+"',PERMITTYPE='"+possstate+"',STARTTIME='"+roadstarttime+"',ENDTIME='"+roadendtime+"',PLAN='1'";
	   }catch(Exception e) {
		   e.printStackTrace();
		   logger.error("");
	   }
	   return insertsql;
  }
  
  /**
   * ɾ��ʩ��ռ����Ϣ
   * @param roadid
   * @return
   */
  public boolean deleteTrafficRoadInfo(String roadid) {
	   boolean flag = false;
	   try {
		   String delsql = "delete from T_ATTEMPER_ROADBUILD where roadid='"+roadid+"'";
		   flag = DBHandler.execute(delsql);
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   return flag;
  }
  
  /**
	 * 
	 * @param alarmid
	 * @return
	 */
	public int isSameRoadBuild(String alarmid) {
		int num  = 0;
		try {
			String sql = "select count(*) from T_ATTEMPER_ROADBUILD where roid='"+alarmid+"'";
			String count = DBHandler.getSingleResult(sql).toString();
		    num = Integer.parseInt(count);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return num;
	}
	
	/**
	 * 
	 * @param alarmid
	 * @return
	 */
	public int isSameTrafficContrl(String alarmid) {
		int num  = 0;
		try {
			String sql = "select count(*) from T_ATTEMPER_TRAFFICRESTRAIN where roid='"+alarmid+"'";
			String count = DBHandler.getSingleResult(sql).toString();
		    num = Integer.parseInt(count);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return num;
	}
	/**
	 * �༭��ͨ����
	 * @param res
	 * @return
	 * @throws Throwable
	 */
	public boolean editTrafficContrl(HashMap res) throws Throwable {
		String inalarmsql = null;
		String sonsql = null;
        
		String alarmid = res.get("alarmId_TrafficRestrain").toString();
		int count = isSameTrafficContrl(alarmid);
		if(count==0) {
			inalarmsql =  insertTrafficContrlMainSql(res);
			sonsql = insertTrafficContrlSql(res);
		}
		else {
			inalarmsql = editTrafficContrlMainSql(res);
			sonsql = editTrafficContrlSql(res);
		}
		System.out.println("���뽻ͨ������Ϣ���� sql>>>>"+inalarmsql);
		System.out.println("���뽻ͨ������Ϣ�ӱ� sql>>>>"+sonsql);
		return executeData(inalarmsql, sonsql);
    }
		
	
	/**
	 * ���뽻ͨ����SQL
	 * @param res
	 * @return
	 */
	public String insertTrafficContrlSql(HashMap res) {
		String sql = null;
		try {
			String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
			//�ʱ��
			String alarmTime_TrafficRestrain = res.get("alarmTime_TrafficRestrain").toString();
			//�ϱ���λ
			String alarmDept_TrafficRestrain = res.get("alarmDept_TrafficRestrain").toString();
			//���
			String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
			//���λ
			String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
			//��ϵ�绰
			String telpone_TrafficRestrain = res.get("telpone_TrafficRestrain").toString();
			//����ԭ��
			String reson_TrafficRestrain = res.get("reson_TrafficRestrain").toString();
			//�·�·��
			String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
			//����
			String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
			//���Ƶص�
			String address_TrafficRestrain =res.get("address_TrafficRestrain").toString();
			//���Ʒ���
			String direction_TrafficRestrain_td = res.get("direction_TrafficRestrain").toString();
			//��������
			String type_TrafficRestrain =res.get("type_TrafficRestrain").toString();
			//���ƿ�ʼʱ��
			String starttime_TrafficRestrain = res.get("starttime_TrafficRestrain").toString();
			//���ƽ���ʱ��
			String endtime_TrafficRestrain = res.get("endtime_TrafficRestrain").toString();
			//��ע
			String desc_TrafficRestrain = res.get("desc_TrafficRestrain").toString();
			sql = "insert into T_ATTEMPER_TRAFFICRESTRAIN(ROID,PHONE,ARARMID,REASON,CONTROLPLACE,DIRECTION,TYPE,REPORTDEPT,STARTTIME,ENDTIME,REMARK)";
			sql +="values('"+alarmId_TrafficRestrain+"','"+telpone_TrafficRestrain+"','"+alarmId_TrafficRestrain+"',";
			sql += "'"+reson_TrafficRestrain+"','"+address_TrafficRestrain+"','"+direction_TrafficRestrain_td+"',";
			sql +="'"+type_TrafficRestrain+"',";
			sql +="'"+alarmDept_TrafficRestrain+"',";
		    sql +="to_date('"+starttime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +="to_date('"+endtime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +="'"+desc_TrafficRestrain+"')";
		}catch(Exception e){
			e.printStackTrace();
		}
		return sql;
	} 
	/**
	 * ���뽻ͨ���ƾ�������SQL
	 * @param res
	 * @return
	 */
	public String insertTrafficContrlMainSql(HashMap res) {
		String sql = null;
		try {
		//������
		String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
		//���
		String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
		//�ʱ��
		String alarmTime_TrafficRestrain = res.get("alarmTime_TrafficRestrain").toString();
		//���λ
		String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
		//�·�·��
		String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
		String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
		sql = "insert into T_ATTEMPER_ALARM(ALARMID,REPORTPERSON,REPORTUNIT,ROADID,KMVALUE,EVENTTYPE,REPORTTIME)";
		sql += "values('"+alarmId_TrafficRestrain+"','"+reportPerson_TrafficRestrain+"',";
		sql += "'"+reportDept_TrafficRestrain+"','"+accsection_TrafficRestrain+"','"+mvalue_TrafficRestrain+"','001007',";
		sql +="to_date('"+alarmTime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'))";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sql;
	}

	
	/**
	 * ���뽻ͨ���ƾ�������SQL
	 * @param res
	 * @return
	 */
	public String editTrafficContrlMainSql(HashMap res) {
		String sql = null;
		try {
		//������
		String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
		//���
		String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
		//�ʱ��
		String alarmTime_TrafficRestrain = res.get("alarmTime_TrafficRestrain").toString();
		//���λ
		String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
		//�·�·��
		String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
		//����
		String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
		
		sql = "update T_ATTEMPER_ALARM";
		sql +=" set REPORTPERSON='"+reportPerson_TrafficRestrain+"',";
		sql +=" REPORTUNIT='"+reportDept_TrafficRestrain+"',";
		sql +=" ROADID='"+accsection_TrafficRestrain+"',";
		sql +=" KMVALUE='"+mvalue_TrafficRestrain+"',";
		//sql += "REPORTTIME = '"+alarmTime_TrafficRestrain+"'";
		sql +="REPORTTIME=to_date('"+alarmTime_TrafficRestrain+"','yyyy-mm-dd hh24:mi')";
		sql +=" where ALARMID='"+alarmId_TrafficRestrain+"'";
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sql;
	}
	
	
	/**
	 * �༭��ͨ����SQL
	 * @param res
	 * @return
	 */
	public String editTrafficContrlSql(HashMap res) {
		String sql = null;
		try {
			String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
			
			//�ϱ���λ
			String alarmDept_TrafficRestrain = res.get("alarmDept_TrafficRestrain").toString();
			//���
			String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
			//���λ
			String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
			//��ϵ�绰
			String telpone_TrafficRestrain = res.get("telpone_TrafficRestrain").toString();
			//����ԭ��
			String reson_TrafficRestrain = res.get("reson_TrafficRestrain").toString();
			//�·�·��
			String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
			//����
			String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
			//���Ƶص�
			String address_TrafficRestrain =res.get("address_TrafficRestrain").toString();
			//���Ʒ���
			String direction_TrafficRestrain_td = res.get("direction_TrafficRestrain").toString();
			//��������
			String type_TrafficRestrain =res.get("type_TrafficRestrain").toString();
			//���ƿ�ʼʱ��
			String starttime_TrafficRestrain = res.get("starttime_TrafficRestrain").toString();
			//���ƽ���ʱ��
			String endtime_TrafficRestrain = res.get("endtime_TrafficRestrain").toString();
			//��ע
			String desc_TrafficRestrain = res.get("desc_TrafficRestrain").toString();
			sql ="update T_ATTEMPER_TRAFFICRESTRAIN";
		    sql +=" set PHONE='"+telpone_TrafficRestrain+"',REASON='"+reson_TrafficRestrain+"',";
		    sql +=" CONTROLPLACE='"+address_TrafficRestrain+"',DIRECTION='"+direction_TrafficRestrain_td+"',";
		    sql +=" TYPE='"+type_TrafficRestrain+"',REPORTDEPT='"+alarmDept_TrafficRestrain+"',";
		    sql +=" STARTTIME=to_date('"+starttime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +=" ENDTIME=to_date('"+endtime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +=" REMARK='"+desc_TrafficRestrain+"'";
		    sql +=" where roid='"+alarmId_TrafficRestrain+"'";
		}catch(Exception e){
			e.printStackTrace();
		}
		return sql;
	} 
	/**
	 * ���ݾ����Ų�ѯ��ͨ������ϸ��Ϣ
	 * @param alarmid
	 * @return
	 */
	public Object[] getTrafficContrl(String alarmid) {
		Object[] res = null;
		try {
		//String sql = "select ROID,STARTTIME,ENDTIME,ARARMID,CONTROLPLACE,DIRECTION,REASON,TYPE,REPORTDEPT,REMARK from T_ATTEMPER_TRAFFICRESTRAIN";
	   
		String sql = "select taa.alarmid,to_char(taa.REPORTTIME,'YYYY-MM-DD HH24:MI'),taa.REPORTUNIT,taa.REPORTPERSON,";
		sql += " tat.REPORTDEPT,tat.PHONE,tat.REASON,taa.ROADID,taa.KMVALUE,tat.CONTROLPLACE,";
		sql += " tat.DIRECTION,tat.TYPE,to_char(tat.STARTTIME,'YYYY-MM-DD HH24:MI'),to_char(tat.ENDTIME,'YYYY-MM-DD HH24:MI'),tat.REMARK";
		sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat";
		sql +=" where 1=1 and taa.alarmid=tat.ROID";
		sql +=" and alarmid='"+alarmid+"'";
	    res = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	
	/**
	 * ���ݾ����Ų�ѯʩ��ռ����ϸ��Ϣ
	 * @param alarmid
	 * @return
	 */
	public Object[] getTrafficRoadInfo(String alarmid) {
		Object[] res = null;
		try {
		String sql = "select taa.alarmid,to_char(taa.REPORTTIME,'YYYY-MM-DD HH24:MI'),taa.REPORTPERSON,";
		sql += " tat.PROPOSER,tat.PRINCIPAL,tat.PHONE_P,tat.ROADBH,tat.ROADSECTION,tat.AFTRANGE,";
		sql += " to_char(tat.STARTTIME,'YYYY-MM-DD HH24:MI'),to_char(tat.ENDTIME,'YYYY-MM-DD HH24:MI'),tat.PERMITTYPE,tat.TITLE";
		sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tat";
		sql +=" where 1=1 and taa.alarmid=tat.ROID";
		sql +=" and taa.alarmid='"+alarmid+"'";
	    res = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	/**
	 * ɾ����ͨ����,ʩ��ռ����Ϣ
	 * @param alarmid
	 * @return
	 * @throws Throwable 
	 */
	public boolean deleteTrafficContrl(String alarmid,String maintalbe,String sontable) throws Throwable {
		boolean flag = false;
		try {
			String mainsql = "delete from '"+maintalbe+"' where alarmid=''"+alarmid+"''";
			String sonsql = "delete from '"+sontable+"' where roid='"+alarmid+"'";
			flag = executeData(mainsql,sonsql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	

}
