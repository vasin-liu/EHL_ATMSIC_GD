package com.ehl.dispatch.cdispatch.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.sm.base.Constant;

/**
 * ��·����������
 * 
 * @author wangxt
 * @date 2009-1-16
 * 
 */
public class CrowdManageDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);
	public AccDeptDao adDao = new AccDeptDao();
	public TrafficNewsFeedsDao tnfDao = new TrafficNewsFeedsDao();


	/**
	 * ������·��Ϣ
	 * 
	 * @param res
	 * @return
	 */
	public boolean addCrowdInfo(HashMap res) {
		String sql = null;
		boolean flag = false;
		try {
			String ROADID = res.get("ROADID").toString();
			String EVENTSOURCE = res.get("EVENTSOURCE").toString();
			String EVENTTYPE = res.get("EVENTTYPE").toString();
			String ALARMUNIT = res.get("ALARMUNIT").toString();
			String TITLE = res.get("TITLE").toString();
			String ALARMREGIONID = res.get("ALARMREGIONID").toString();
			String ALARMREGION = StringHelper.obj2str(res.get("ALARMREGION").toString(),"");
			String ROADNAME = res.get("ROADNAME").toString();
			String KMVALUE = res.get("KMVALUE").toString();
			String MVALUE = res.get("MVALUE").toString();
			String EndKMVALUE = res.get("EndKMVALUE").toString();
			String EndMVALUE = res.get("EndMVALUE").toString();
			String CaseHappenTime = res.get("CaseHappenTime").toString();
			String CaseEndTime = res.get("CaseEndTime").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTPERSON = StringHelper.obj2str(res.get("REPORTPERSON").toString(),"");
			String REPORTTIME = res.get("REPORTTIME").toString();
			String CONGESTIONTYPE = res.get("CONGESTIONTYPE").toString();
			String CONGESTIONREASON = res.get("CONGESTIONREASON").toString();
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			String EVENTSTATE = res.get("EVENTSTATE").toString();
			// ������ӵ�¼���
			String CROWDTYPE = res.get("crowdTypeFlg").toString();
			// ���ӱ��ϱ��
			String BLID = res.get("BLID").toString();
			
			String Xvalue = res.get("Xvalue").toString();
			String Yvalue = res.get("Yvalue").toString();
			String ALARMID = res.get("ALARMID").toString();
			String remindInfo = StringHelper.obj2str(res.get("remindInfo"),"");
			String baoSongFlg = StringHelper.obj2str(res.get("baoSongFlg"),"0");
			String baosongDepartMentId = StringHelper.obj2str(String.valueOf(res.get("baosongDepartMentId")),"");
			
			//Modified by Liuwx 2011-8-8
			String ReceiveTime = StringHelper.obj2str(res.get("ReceiveTime"),"");
			//Modification finished
			//Modified by Xiayx 2011-8-8
			String apname = StringHelper.obj2str(res.get("apname"),"");
			String apath = StringHelper.obj2str(res.get("apath"),"");
			//Modification finished
			
			sql = "insert into t_attemper_alarm(ALARMID,ROADID,EVENTSOURCE,EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION,"
					+ "ROADNAME,KMVALUE,MVALUE,EndKMVALUE,EndMVALUE,CaseHappenTime,CaseEndTime,REPORTUNIT,REPORTPERSON,REPORTTIME," +
					"ALARMTIME,EVENTSTATE,ROADDIRECTION,X,Y,ReceiveTime,DDAPPROVER,apath"
					//Modify by Xiayx 2012-3-1
					//��ӹ��ƴ�ʩ-��ӹ��ƴ�ʩ����
					+",gzcs"+
					//Modification finished
					")";
			sql += " values('" + ALARMID + "','" + ROADID + "','" + EVENTSOURCE
					+ "','" + EVENTTYPE + "','" + ALARMUNIT + "','" + TITLE
					+ "','" + ALARMREGIONID + "','" + ALARMREGION + "','"
					+ ROADNAME + "','" + KMVALUE + "','" + MVALUE + "','"
					+ EndKMVALUE + "','" + EndMVALUE + "',to_date('"
					+ CaseHappenTime + "','yyyy-mm-dd hh24:mi:ss'),to_date('"
					+ CaseEndTime + "','yyyy-mm-dd hh24:mi:ss'),'" + REPORTUNIT
					+ "','" + REPORTPERSON + "',to_date('" + REPORTTIME
					+ "','yyyy-mm-dd hh24:mi:ss'),to_date('" + REPORTTIME
					+ "','yyyy-mm-dd hh24:mi:ss'),'" + EVENTSTATE + "','"
					+ ROADDIRECTION + "','" + Xvalue + "','" + Yvalue 
					//Modified by Liuwx 2011-8-8
					+ "',to_date('" + ReceiveTime + "','yyyy-mm-dd hh24:mi:ss') "
					//Modification finished
					//Modified by Xiayx 2011-8-8
					+ ",'" + apname + "'"
					+ ",'" + apath + "'"
					//Modification finished
					//Modify by Xiayx 2012-3-1
					//��ӹ��ƴ�ʩ-���ò������ݿ���ƴ�ʩֵ
					+ ",'" + res.get("gzcs") + "'"
					//Modification finished
					+ ")";
			System.out.println("addCrowdInfo1:" + sql);
			flag = DBHandler.execute(sql);
			sql = "insert into t_attemper_congestion(ALARMID,CONGESTIONTYPE,CONGESTIONREASON,UPDATE_DATE,CUTFLG,BAOSONGFLG,BAOSONGDEPARTID,CROWDTYPE,BLID)";
			sql += " values('" + ALARMID + "','" + CONGESTIONTYPE + "','" + CONGESTIONREASON + "',sysdate,'0','"
			+baoSongFlg+"','"+baosongDepartMentId+"','"+CROWDTYPE+"','"+BLID+"')";
			System.out.println("addCrowdInfo2:" + sql);
			flag = DBHandler.execute(sql);
			
			
			//Ĭ����Ӹ�����Ϊ���յ�λ
			Map<String,String> adMap = new HashMap<String, String>();
			adMap.put("aid", ALARMID);
			adMap.put("atype", "2");
			adMap.put("mstate", "1");
			adMap.put("rpdcode", Constant.getParents(REPORTUNIT));
			adDao.adds(adMap);
			//Modification finished
		} catch (Exception e) {
			logger.error("����ӵ����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	

	/**
	 * �༭��·��Ϣ
	 * 
	 * @param res
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean editCrowdInfo(HashMap res) {
		String sql = null;
		boolean flag = false;
		try {
			String ROADID = res.get("ROADID").toString();
//			String EVENTSOURCE = res.get("EVENTSOURCE").toString();
//			String EVENTTYPE = res.get("EVENTTYPE").toString();
//			String ALARMUNIT = res.get("ALARMUNIT").toString();
//			String TITLE = res.get("TITLE").toString();
//			String ALARMREGIONID = res.get("ALARMREGIONID").toString();
			String ALARMREGION = res.get("ALARMREGION").toString();
			String ROADNAME = res.get("ROADNAME").toString();
			String KMVALUE = res.get("KMVALUE").toString();
			String MVALUE = res.get("MVALUE").toString();
			String EndKMVALUE = res.get("EndKMVALUE").toString();
			String EndMVALUE = res.get("EndMVALUE").toString();
			String CaseHappenTime = res.get("CaseHappenTime").toString();
			String CaseEndTime = res.get("CaseEndTime").toString();
//			String REPORTUNIT = res.get("REPORTUNIT").toString();
//			String REPORTPERSON = res.get("REPORTPERSON").toString();
//			String REPORTTIME = res.get("REPORTTIME").toString();
			String CONGESTIONTYPE = res.get("CONGESTIONTYPE").toString();
			String CONGESTIONREASON = res.get("CONGESTIONREASON").toString();
			String ALARMID = res.get("ALARMID").toString();
//			String EVENTSTATE = res.get("EVENTSTATE").toString();
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			
			// ������ӵ�¼���
			String CROWDTYPE = res.get("crowdTypeFlg").toString();
			
			String Xvalue = res.get("Xvalue").toString();
			String Yvalue = res.get("Yvalue").toString();
			String remindInfo = StringHelper.obj2str(res.get("remindInfo"),"");
			String currentUserName = StringHelper.obj2str(res.get("currentUserName"),"");

			//Modified by Liuwx 2011-8-8
			String ReceiveTime = res.get("ReceiveTime").toString();
			//Modification finished
			//Modified by Xiayx 2011-8-8
			String apname = res.get("apname").toString();
			String apath = res.get("apath").toString();
			//Modification finished
			
			String radioType = "";
			radioType =ROADDIRECTION;

			sql = "update t_attemper_alarm";
			sql += " set ROADID='" + ROADID + "'" + ",ROADNAME='" + ROADNAME
					+ "'" + ",KMVALUE='" + KMVALUE + "',MVALUE='" + MVALUE
					+ "',EndKMVALUE='" + EndKMVALUE + "',EndMVALUE='" + EndMVALUE + "'";
			if (CaseHappenTime != null && !("".equals(CaseHappenTime))
					&& !("��".equals(CaseHappenTime))) {
				sql += ",CaseHappenTime=to_date('" + CaseHappenTime
						+ "','yyyy-mm-dd hh24:mi')";
			}
			if (CaseEndTime != null && !("".equals(CaseEndTime))
					&& !("��".equals(CaseEndTime))) {
				sql += ",CaseEndTime=to_date('" + CaseEndTime
						+ "','yyyy-mm-dd hh24:mi')";
			}
			sql += ",ROADDIRECTION='" + radioType + "'";
			sql += ",X='" + Xvalue + "'";
			sql += ",Y='" + Yvalue + "'";
			//Modified by Liuwx 2011-8-8
			if (ReceiveTime != null && !("".equals(ReceiveTime)) && !"��".equals(ReceiveTime)) {
				sql += ",ReceiveTime=to_date('" + ReceiveTime + "','yyyy-mm-dd hh24:mi')";
			}
			//Modification finished
			//Modified by Xiayx 2011-8-8
			if(apname != null && !apname.equals("")){
				sql += ",DDAPPROVER='" + apname + "'";
			}
			if(apath != null && !apath.equals("")){
				sql += ",apath='" + apath + "'";
			}
			//Modification finished

			sql += " where ALARMID='" + ALARMID + "'";
			System.out.println("1:" + sql);
			flag = DBHandler.execute(sql);

			sql = "update t_attemper_congestion";
			sql += " set CONGESTIONTYPE='" + CONGESTIONTYPE
				+ "',CONGESTIONREASON='" + CONGESTIONREASON + "'"
				+ " ,CROWDTYPE='" + CROWDTYPE + "'"
				+ " ,UPDATE_DATE=sysdate";
			sql += " where ALARMID='" + ALARMID + "'";
			System.out.println("2:" + sql);
			flag = DBHandler.execute(sql);
			//���½��յ�λ��Ϣ״̬Ϊ2�����£�
			adDao.updateMState(ALARMID, "2","1");
			String acceptDepts = res.get("acceptDept")+"";
			if(!acceptDepts.equals("")){
				//�ж��Ƿ��״�ָ��ӵ�½��ջ���������Ǿ���ӣ�������Ǿ͸��¡�
				//��ʡ���жϣ�һ���ø��·���
				acceptDepts += ",440000000000";//�ܶӲ�����
				flag = updateConAcptDept(ALARMID, acceptDepts);
			}
		} catch (Exception e) {
			logger.error("�༭ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ���ӵ�½��յ�λ
	 * ����ָ���Լ�Ϊӵ�½��յ�λ��Ҳ����ָ��ӵ�����λΪ���յ�λ
	 * @param alarmId ������
	 * @param acceptDepts ���յ�λ���ɴ�һ��������λ
	 * @return 
	 */
	public boolean addConAcptDept(String alarmId, String acceptDepts){
		String methodDesc = "����ܶ��·�ӵ�½��մ��";
		boolean isSuccess = false;
		if (alarmId != null && acceptDepts != null  ) {
			acceptDepts = "'" + acceptDepts.replace(",", "','") + "'";
			Object[][] acceptDeptS = null;
			String sql = "select jgid,jgmc from t_sys_department where jgid in ("+acceptDepts+")";
			try {
				acceptDeptS = DBHandler.getMultiResult(sql);
			} catch (Exception e) {
				logger.error(methodDesc+"����ȡ����������ʧ�ܣ�����SQL��\n"+sql,e);
			}
			if(acceptDeptS != null && acceptDeptS.length > 0){
				isSuccess = true;
				for (int i = 0; i < acceptDeptS.length; i++) {
					sql = "insert into t_attemper_cacptdept(id,alarmid,jgid,jgmc,state) " +
							" values(seq_attemper_cacptdept.nextval,'"+alarmId+"','"+acceptDeptS[i][0]+"','"+acceptDeptS[i][1]+"','0')";
					try {
						isSuccess = isSuccess && DBHandler.execute(sql);
					} catch (Exception e) {
						logger.error(methodDesc+"�����һ��ӵ�½��մ�ӣ�ʧ�ܣ�����SQL��\n"+sql,e);
						break;
					}
				}
			}
		}
		return isSuccess;
	}
	
	/**
	 * ��ȡӵ�½��ջ���<br>
	 * @param alarmId ӵ�¾���ID
	 * @return ��������ͻ�������
	 */
	public String[] getConAcptDept(String alarmId){
		String[] cacptdepts = null;
		String sql = "select state,jgid,jgmc from t_attemper_cacptdept where alarmid='"+alarmId+"' and jgid != '440000000000'";
		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("��ȡӵ�½��ջ�������������SQL��\n"+sql,e);
		}
		if(temp != null && temp.length > 0){
			cacptdepts = new String[6];
			for (int i = 0; i < cacptdepts.length; i++) {
				cacptdepts[i] = "";
			}
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j<temp[i].length; j++) {
					cacptdepts[0] += temp[i][1];
					cacptdepts[1] += temp[i][2];
					if(temp[i][0].equals("0")){
						cacptdepts[2] += temp[i][1];
						cacptdepts[3] += temp[i][2];
					}else if(temp[i][0].equals("1")){
						cacptdepts[4] += temp[i][1];
						cacptdepts[5] += temp[i][2];
					}
				}
			}
		}
		return cacptdepts;
	}
	
	/**
	 * ����ӵ�½��յ�λ������ָ��ӵ�½��յ�λ
	 * @param alarmId
	 * @param cacptdepts
	 * @return
	 */
	public boolean updateConAcptDept(String alarmId, String cacptdepts){
		String methodDesc = "����ӵ�½��յ�λ������ָ��ӵ�½��յ�λ";
		boolean isSuccess = false;
		if(alarmId != null && cacptdepts != null){
			String cacptdeptsDbc = "'" + cacptdepts.replace(",", "','") + "'";
			String sql = "delete from t_attemper_cacptdept where alarmid='"+alarmId+"' and jgid not in ("+cacptdeptsDbc+")";
			try {
				isSuccess = DBHandler.execute(sql);
			} catch (Exception e) {
				logger.error(methodDesc + "��ɾ���ϴ�ָ�������ӵ�½��ջ�����ʧ�ܣ�����SQL��\n"+sql,e);
			}
			if(isSuccess){
				String[] depts = getConAcptDept(alarmId);//��ȡ�ϴ�ָ����ȷ��ӵ�½��ջ���
				if(depts != null && !depts[0].equals("")){
					String jgid = depts[0]+",";
					cacptdepts = (cacptdepts+",").replace(jgid, "");//��ȡ��������ӵ�ӵ�½��ջ���
					cacptdepts = cacptdepts.substring(0,cacptdepts.length()-1);
				}
				isSuccess = addConAcptDept(alarmId, cacptdepts);
			}
		}
		return isSuccess;
	}
	
	/**
	 * ����ӵ�½��յ�λ�����ò鿴״̬Ϊ�Ѳ鿴
	 * @param alarmId ����ID
     * @param jgid
	 * @return 
	 */
	public boolean updateCADRState(String alarmId, String jgid){
		boolean isSuccess = false;
		String sql = "update t_attemper_cacptdept set state='1' where alarmid='"+alarmId+"' and jgid='"+jgid+"'";
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("����ӵ�½��յ�λ�鿴״̬��ʧ�ܡ�����SQL��\n"+sql,e);
		}
		return isSuccess;
	}
	
	
	/**
	 * ���µ�·��Ϣ����״̬Ϊ��ͨ(005005)
	 * 
	 * @param ALARMID
	 * @return
	 */
	public boolean updateCrowdInfo(String ALARMID) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "update t_attemper_congestion";
			sql += " set CONGESTIONTYPE='" + "005005" + "'";
			sql += " where ALARMID='" + ALARMID + "'";
			flag = DBHandler.execute(sql);
			if(flag){
				sql = "update T_ATTEMPER_ALARM set eventstate='570002', CaseEndTime=sysdate  where alarmid='"
					+ ALARMID + "'";
				flag = DBHandler.execute(sql);
			}
			if(flag){
				sql = "update EXCH_T_TRAFFICNEWSFEEDS_TEMP set SFJS='1'" +
						" where bh=(select blid from t_attemper_congestion where alarmid='"+ALARMID+"')";
				flag = DBHandler.execute(sql);
			}
			
			//���½��յ�λ״̬Ϊ3��ɾ����
			adDao.updateMState(ALARMID,  "3","1");
		} catch (Exception e) {
			logger.error("���ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 * @param alarmid
	 * @return
	 */
	public Object[] getCrowdInfo(String alarmid) {
		String sql = null;
		Object[] obj = null;
		try {
			sql = "select taa.ALARMID,taa.ROADID,tcc.CONGESTIONTYPE,taa.ROADDIRECTION,taa.ROADNAME,"
					+ "taa.KMVALUE,taa.EndKMVALUE,to_char(taa.CaseHappenTime,'yyyy-mm-dd HH24:mi')"
					+ ",to_char(taa.CaseEndTime,'yyyy-mm-dd HH24:mi'),tcc.CONGESTIONREASON,taa.ALARMREGION,"
					+ "taa.REPORTPERSON,to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi'),taa.eventstate,taa.MVALUE,taa.ENDMVALUE,"
					+ " tcc.BAOSONGFLG,f_get_dept(taa.ALARMUNIT), tcc.CROWDTYPE,"
					+ " nvl((select roadLevel from T_OA_DICTDLFX where dlmc = taa.roadid and rownum <= 1),'4'),taa.reportunit "
					//Modified by Liuwx 2011-8-8
					+ ",to_char(taa.ReceiveTime,'yyyy-mm-dd HH24:mi') "
					//Modification finished
					//Modified by Xiayx 2011-8-8
					+ ",DDAPPROVER,APATH "
					//Modification finished
					//Modify by Xiayx 2012-3-1
					//��ӹ��ƴ�ʩ-��ȡ���ƴ�ʩ
					+ ",gzcs"
					//Modification finished
					+ " from T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc ";
			sql += " where taa.ALARMID='" + alarmid
					+ "' and tcc.ALARMID=taa.ALARMID";
			System.out.println("3:" + sql);
			obj = DBHandler.getLineResult(sql);
		} catch (Exception e) {
			logger.error("��ѯһ��ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * ������ʾ��Ϣ<br/>
	 * 
	 * @param alarmid
	 * @return
	 */
	public Object[][] getRemindInfo(String alarmid) {
		String sql = null;
		Object[][] obj = null;
		try {
			sql = " select ID,ALARMID,REMINDINFO,to_char(REMINDDATE,'yyyy-mm-dd hh24:mi'),REMINDDEPARTMENT,REMINDPERSON "
					+ " from T_OA_CROWD_REMINDINFO ";
			sql += " where ALARMID='" + alarmid + "' order by REMINDDATE asc";
			System.out.println("3:" + sql);
			obj = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("��ѯ������ʾ��Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 
	 * @param ALARMID
	 * @return
	 */
	public boolean cancelCrowdInfo(String ALARMID) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "update t_attemper_alarm";
			sql += " set CaseEndTime=sysdate";
			sql += " where ALARMID='" + ALARMID + "'";
			System.out.println("cancelCrowdInfo:" + sql);
			flag = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("�༭ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

    /**
     *
     * @param res
     * @return
     */
    //Modified by Liuwx 2012��3��16��20:17:18
    public boolean isExist(HashMap res){
        String isExistSql = null;
        boolean flag = false;
        try {
            String ALARMREGIONID = res.get("ALARMREGIONID").toString();
            String ROADNAME = res.get("ROADNAME").toString();
            String KMVALUE = res.get("KMVALUE").toString();
            String MVALUE = res.get("MVALUE").toString();
            String EndKMVALUE = res.get("EndKMVALUE").toString();
            String EndMVALUE = res.get("EndMVALUE").toString();
            String CaseHappenTime = res.get("CaseHappenTime").toString();
            String CaseEndTime = res.get("CaseEndTime").toString();

            isExistSql = "select count(*) from (select taa.alarmid from t_attemper_alarm taa where taa.eventtype = '001002' and " +
                    " taa.ALARMREGIONID = '"+ALARMREGIONID+"' and" +
                    " taa.ROADNAME='"+ ROADNAME+"' and taa.CaseHappenTime = to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi') and " +
                    " taa.CaseEndTime = to_date('"+CaseEndTime+"','yyyy-mm-dd hh24:mi') and taa.KMVALUE='"+KMVALUE+"' and taa.MVALUE='"+MVALUE+"'" +
                    " and taa.EndKMVALUE='"+EndKMVALUE+"' and taa.EndMVALUE='"+EndMVALUE+"'" +
                    ") t";
            Object isExistResult = DBHandler.getSingleResult(isExistSql);
            int ResultNum = StringHelper.obj2int(isExistResult,0);
            if (ResultNum > 0){
                flag = true;
            }else {
                flag = false;
            }
        }catch(Exception e) {
            logger.error("��ѯӵ����Ϣ����:");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Modified by Liuwx 2012��3��17��16:23:54
     * ӵ�³�ʱδ����ʱ�����ϼ���λ������Ǵ�ӵĻ�ͬʱ�����ܶӣ�
     * @param res
     * @return Object[][]
     */
    public Object[][] getOutToDateCrowInfo(HashMap res){
        Object[][] results = null;
        String sql = null;
        try{
//            String deptId = res.get("deptId").toString();
            String jgbh = res.get("jgbh").toString();
            sql = "SELECT TAA.ALARMID,TAA.ROADID,TAA.ROADNAME," +
                    " TAA.ALARMREGION,TAA.CASEHAPPENTIME,TAC.UPDATE_DATE,TAA.EVENTSTATE,TAA.ALARMREGIONID " +
                    " FROM T_ATTEMPER_ALARM TAA LEFT JOIN T_ATTEMPER_CONGESTION TAC ON TAA.ALARMID = TAC.ALARMID" +
                    " WHERE TAA.EVENTTYPE = '001002' AND TAA.EVENTSTATE = '570001' " +
                    " AND (SYSDATE-TAC.UPDATE_DATE) > 1 / 24 " +
                    " AND TAA.ALARMREGIONID LIKE '" + jgbh + "%'";
            results = DBHandler.getMultiResult(sql);
        }
        catch (Exception e){
            logger.error("��ѯӵ�³�ʱ��Ϣ����\n"+e.getMessage());
            e.printStackTrace();
        }
        return  results;
    }

    /**
     * Modified by Liuwx 2012��3��17��16:23:54
     * ӵ�³�ʱδ������Ϣ����
     * @param res
     * @return Object[][]
     */
    public Object getOutToDateCrowInfoCount(Map<String,String> res){
        Object result = null;
        String sql = null;
        try{
            String jgbh = res.get("jgbh").toString();
            sql = "SELECT COUNT(TAA.ALARMID) INFO_NUM " +
                    " FROM T_ATTEMPER_ALARM TAA LEFT JOIN T_ATTEMPER_CONGESTION TAC ON TAA.ALARMID = TAC.ALARMID" +
                    " WHERE TAA.EVENTTYPE = '001002' AND TAA.EVENTSTATE = '570001' " +
                    " AND (SYSDATE-TAC.UPDATE_DATE) > 1 / 24 " +
                    " AND TAA.ALARMREGIONID LIKE '" + jgbh + "%'";
            result = DBHandler.getSingleResult(sql);
        }
        catch (Exception e){
            logger.error("��ѯӵ�³�ʱ��Ϣ��������\n"+e.getMessage());
            e.printStackTrace();
        }
        return  result;
    }
}