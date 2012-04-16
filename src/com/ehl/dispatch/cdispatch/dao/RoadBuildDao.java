package com.ehl.dispatch.cdispatch.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.common.AlarmInfoJoin;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.tira.util.Sql;

/**
 * ��·����������
 * @author wangxt
 * @date 2009-1-16
 *
 */
public class RoadBuildDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);
	public AccDeptDao adDao = new AccDeptDao();
	public PoliceRemindDao prdao = new PoliceRemindDao();
	/**
	 * ������·��Ϣ
	 * @param res
	 * @return
	 */
	public boolean addRoadBuildInfo(HashMap res){
		String sql = null;
		boolean flag = false;
		try {
			String ROADID = res.get("ROADID").toString();
			String EVENTSOURCE = res.get("EVENTSOURCE").toString();
			String EVENTTYPE = res.get("EVENTTYPE").toString();
			String ALARMUNIT = res.get("ALARMUNIT").toString();
			String TITLE = res.get("TITLE").toString();
			String ALARMREGIONID = res.get("ALARMREGIONID").toString();
			String ALARMREGION = res.get("ALARMREGION").toString();
			String ROADNAME = res.get("ROADNAME").toString();
			String KMVALUE = res.get("KMVALUE").toString();
			String MVALUE = res.get("MVALUE").toString();
			String EndKMVALUE = res.get("EndKMVALUE").toString();
			String EndMVALUE = res.get("EndMVALUE").toString();
			String CaseHappenTime = res.get("CaseHappenTime").toString();
			String CaseEndTime = res.get("CaseEndTime").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTPERSON = res.get("REPORTPERSON").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();
			String PLAN = res.get("PLAN").toString();
			String ISALLCLOSE = res.get("ISALLCLOSE").toString();
			String ROLANENUM = res.get("ROLANENUM").toString();
			String ISHAVEPLAN = res.get("ISHAVEPLAN").toString();
			String ALARMID = res.get("ALARMID").toString();
			String EVENTSTATE = "570005";//ʩ��ռ����
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			String Xvalue = res.get("Xvalue").toString();
			String Yvalue = res.get("Yvalue").toString();
//			String radioType = "";
//			if("1".equals(ROADDIRECTION)) {
//				radioType = "����";
//			} else {
//				radioType = "����";
//			}
			sql ="insert into t_attemper_alarm(ALARMID,ROADID,EVENTSOURCE,EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION," +
					"ROADNAME,KMVALUE,MVALUE,EndKMVALUE,EndMVALUE,CaseHappenTime,CaseEndTime,REPORTUNIT,REPORTPERSON,REPORTTIME," +
					"ALARMTIME,EVENTSTATE,ROADDIRECTION,X,Y,GZCS)";
			sql += " values('"+ALARMID+"','"+ROADID+"','"+EVENTSOURCE+"','"+EVENTTYPE+"','"+ALARMUNIT+"','"+TITLE+"','"+ALARMREGIONID+"','"+ALARMREGION+"','"
					+ROADNAME+"','"+KMVALUE+"','"+MVALUE+"','"+EndKMVALUE+"','"+EndMVALUE+"',to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi'),to_date('"+CaseEndTime+"','yyyy-mm-dd hh24:mi'),'"
					+REPORTUNIT+"','"+REPORTPERSON+"',to_date('"+REPORTTIME+"','yyyy-mm-dd hh24:mi'),to_date('"+REPORTTIME+"','yyyy-mm-dd hh24:mi'),'"
					+EVENTSTATE+"','"+ROADDIRECTION+"','"+Xvalue+"','"+Yvalue +"','"+res.get("PLAN")+"')";
			System.out.println("addRoadBuildInfo1:"+sql);
			flag = DBHandler.execute(sql);
			sql ="insert into T_ATTEMPER_ROADBUILD(ALARMID,PLAN,ISALLCLOSE,ROLANENUM,ISHAVEPLAN,STARTTIME,ENDTIME)";
			sql += " values('"+ALARMID+"','"+PLAN+"','"+ISALLCLOSE+"','"+ROLANENUM+"','"+ISHAVEPLAN+"',to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi'),to_date('"+CaseEndTime+"','yyyy-mm-dd hh24:mi'))";
			System.out.println("addRoadBuildInfo2:"+sql);
			flag = DBHandler.execute(sql);
			//Modified by Liuwx 2011-06-28
			//Ĭ����Ӹ�����Ϊ���յ�λ
			//���½��յ�λ��Ϣ״̬Ϊ1��������
			Map<String,String> adMap = new HashMap<String, String>();
			adMap.put("aid", ALARMID);
			adMap.put("atype", "3");
			adMap.put("mstate", "1");
			adMap.put("rpdcode", Constant.getParents(REPORTUNIT));
			adDao.adds(adMap);
			
		}catch(Exception e) {
			logger.error("����ռ����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * �༭��·��Ϣ
	 * @param res
	 * @return
	 */
	public boolean editRoadBuildInfo(HashMap res) {
		String sql = null;
		boolean flag = false;
		try {
			String ROADID = res.get("ROADID").toString();
			String EVENTSOURCE = res.get("EVENTSOURCE").toString();
			String EVENTTYPE = res.get("EVENTTYPE").toString();
			String ALARMUNIT = res.get("ALARMUNIT").toString();
			String TITLE = res.get("TITLE").toString();
			String ALARMREGIONID = res.get("ALARMREGIONID").toString();
			String ALARMREGION = res.get("ALARMREGION").toString();
			String ROADNAME = res.get("ROADNAME").toString();
			String KMVALUE = res.get("KMVALUE").toString();
			String MVALUE = res.get("MVALUE").toString();
			String EndKMVALUE = res.get("EndKMVALUE").toString();
			String EndMVALUE = res.get("EndMVALUE").toString();
			String CaseHappenTime = res.get("CaseHappenTime").toString();
			String CaseEndTime = res.get("CaseEndTime").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTPERSON = res.get("REPORTPERSON").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();
			String PLAN = res.get("PLAN").toString();
			String ISALLCLOSE = res.get("ISALLCLOSE").toString();
			String ROLANENUM = res.get("ROLANENUM").toString();
			String ISHAVEPLAN = res.get("ISHAVEPLAN").toString();
			String ALARMID = res.get("ALARMID").toString();
			String EVENTSTATE = "570005";//ʩ��ռ����
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			
			String Xvalue = res.get("Xvalue").toString();
			String Yvalue = res.get("Yvalue").toString();
			
			String radioType = "";
			radioType = ROADDIRECTION;
//			if("1".equals(ROADDIRECTION)) {
//				radioType = "����";
//			} else {
//				radioType = "����";
//			}
			sql ="update t_attemper_alarm";
			sql +=" set ROADID='"+ROADID+"'"
				+",ROADNAME='"+ROADNAME+"'"
				+",X='"+Xvalue+"'"
				+",Y='"+Yvalue+"'"
				+",KMVALUE='"+KMVALUE+"'"
				+",MVALUE='"+MVALUE+"'"
				+",EndKMVALUE='"+EndKMVALUE+"'"
				+",EndMVALUE='"+EndMVALUE+"'";
				if(CaseHappenTime != null && !("".equals(CaseHappenTime)) && !("��".equals(CaseHappenTime))) {
					sql +=",CaseHappenTime=to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi')";
				}
				if(CaseEndTime != null && !("".equals(CaseEndTime)) && !("��".equals(CaseEndTime))) {
					sql +=",CaseEndTime=to_date('"+CaseEndTime+"','yyyy-mm-dd hh24:mi')";
				}
				sql +=",ROADDIRECTION='"+radioType+"'";
				/*
				+",REPORTUNIT='"+REPORTUNIT+"'"
				+",REPORTPERSON='"+REPORTPERSON+"'"
				+",REPORTTIME='"+REPORTTIME+"'"
				+",EVENTSOURCE='"+EVENTSOURCE+"'"
				+",EVENTTYPE='"+EVENTTYPE+"'"
				+",ALARMUNIT='"+ALARMUNIT+"'"
				+",TITLE='"+TITLE+"'"
				+",ALARMREGIONID='"+ALARMREGIONID+"'"
				+",ALARMREGION='"+ALARMREGION+"'"
				+",KMVALUE='"+KMVALUE+"'"
				+",EndKMVALUE='"+EndKMVALUE+"'"
				+",EVENTSTATE='"+EVENTSTATE+"'";
				*/
			sql +=" where ALARMID='"+ALARMID+"'";
			System.out.println("1:"+sql);
			flag = DBHandler.execute(sql);
			
			sql ="update T_ATTEMPER_ROADBUILD";
			sql +=" set PLAN='"+PLAN+"'"
				+",ISALLCLOSE='"+ISALLCLOSE+"'"
				+",ROLANENUM='"+ROLANENUM+"'"
				+",ISHAVEPLAN='"+ISHAVEPLAN+"'"
				+",STARTTIME=to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi')"
				+",ENDTIME=to_date('"+CaseEndTime+"','yyyy-mm-dd hh24:mi')";
			sql +=" where ALARMID='"+ALARMID+"'";
			System.out.println("2:"+sql);
			flag = DBHandler.execute(sql);
			//Modified by Liuwx 2011-06-28
			//���½��յ�λ��Ϣ״̬Ϊ2�����£�
			if (flag) {
				adDao.updateMState(ALARMID, "2","1");
			}
			//Modification finished
		}catch(Exception e) {
			logger.error("�༭ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ɾ����·��Ϣ
	 * @param ALARMID
	 * @return
	 */
	public boolean delteRoadBuildInfo(String ALARMID) {
		String sql = null;
		boolean flag = false;
		try {
			sql ="delete from T_ATTEMPER_ROADBUILD";
			sql +=" where ALARMID='"+ALARMID+"'";
			flag = DBHandler.execute(sql);
			sql ="delete from T_ATTEMPER_ALARM";
			sql +=" where ALARMID='"+ALARMID+"'";
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("ɾ����·��Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ���ռ��
	 * @param ALARMID
	 * @return
	 */
	public boolean updateRoadBuildInfo(String ALARMID) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "update T_ATTEMPER_ROADBUILD set buildstate='1',ENDTIME=SYSDATE ";
			sql +=" where ALARMID='"+ALARMID+"'";
			flag = DBHandler.execute(sql);
			sql = "update T_ATTEMPER_ALARM set eventstate='570006',CASEENDTIME=SYSDATE ";
			sql += " where ALARMID='"+ALARMID+"'";
			flag = DBHandler.execute(sql) && flag;
			//Modified by Liuwx 2011-07-04
			//���½��յ�λ��Ϣ״̬Ϊ2�����£�
			if (flag) {
				adDao.updateMState(ALARMID, "2","1");
			}
			//Modification finished
			
		}catch(Exception e) {
			logger.error("���ռ��:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ��ѯһ����·��Ϣ
	 * @param alarmid
	 * @return
	 */
	public Object[] getRoadBuildInfo(String alarmid) {
		String sql = null;
		Object[] obj=null;
		try {
			sql ="select taa.ALARMID,taa.ROADID,tar.PLAN,taa.ROADDIRECTION,taa.ROADNAME,taa.KMVALUE,taa.EndKMVALUE,to_char(taa.CaseHappenTime,'yyyy-mm-dd HH24:mi')" +
					",to_char(taa.CaseEndTime,'yyyy-mm-dd HH24:mi'),taa.alarmregion,taa.REPORTPERSON,to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi')" +
					",tar.ISALLCLOSE,tar.ROLANENUM,tar.ISHAVEPLAN,taa.eventstate,taa.MVALUE,taa.EndMVALUE,(select roadlevel from T_OA_DICTDLFX where dlmc=taa.roadid)," +
					"tar.zodbz" +
					" from T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar";
			sql +=" where taa.ALARMID='"+alarmid+"' and tar.ALARMID=taa.ALARMID";
			System.out.println("3:"+sql);
			obj = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			logger.error("��ѯһ��ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * Liuwx
	 * ��ѯʩ��ռ����Ϣ
	 * @param rpdcode
	 * @param state
	 * @param mstate
	 * @return
	 */
	public String searchRoadBuild(String rpdcode, String state, String mstate){
		Object[][] data = null;
		String xml = null;
		if(rpdcode != null && state != null && mstate != null){
			String colStr = "taa.ALARMID,taa.ALARMREGION,taa.roadid,taa.ROADNAME,dic.begin||'->'||dic.end ," +
				"to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'),"+
				"decode(taa.eventstate,'570005','ռ����','570006','�ѽ��') as eventstate," +
				"decode(toa.adid,null,'2','3'),taa.eventstate,taa.alarmregionid ";
			String tableStr = "T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar,T_OA_DICTDLFX dic ,t_oa_acceptdept toa";
			String whereStr = "taa.ALARMID = toa.aid and taa.ALARMID = tar.ALARMID and taa.roadid = dic.dlmc ";
			whereStr += " and toa.atype='3'";
			whereStr += " and toa.rpdcode='"+rpdcode+"'";
			whereStr += " and toa.state = '" + state + "'";
			whereStr += " and toa.mstate = '" + mstate + "'";
			String sql = Sql.select(tableStr, colStr, whereStr, null, "taa.ALARMID desc");
			data = FlowUtil.readMilte(sql, logger, "��ѯʩ��ռ����Ϣ");
			xml = DataToXML.objArrayToXml(data);
		}else if (rpdcode != null && state != null && mstate == null) {
			String colStr = "taa.ALARMID,taa.ALARMREGION,taa.roadid,taa.ROADNAME,dic.begin||'->'||dic.end ," +
				"to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'),"+
				"decode(taa.eventstate,'570005','ռ����','570006','�ѽ��') as eventstate," +
				"'',taa.eventstate,taa.alarmregionid ";
			String tableStr = "T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar,T_OA_DICTDLFX dic ";
			String whereStr = "taa.ALARMID = tar.ALARMID and taa.roadid = dic.dlmc ";
			whereStr += " AND taa.eventstate='570005' ";
			whereStr += " AND taa.alarmregionid='"+rpdcode+"' ";
			whereStr += " AND (SELECT SYSDATE FROM DUAL)>=TAA.CASEENDTIME ";
			String sql = Sql.select(tableStr, colStr, whereStr, null, "taa.ALARMID desc");
			data = FlowUtil.readMilte(sql, logger, "��ѯʩ��ռ����Ϣ");
			xml = DataToXML.objArrayToXml(data);
		}
		return xml;
	}
	
	public ArrayList<String> getRoadBuildCount(Map<String, String> pimap) {
		ArrayList<String> results = new ArrayList<String>();
		String rpdcode = pimap.get("rpdcode");
		String sql = "SELECT COUNT(TAA.ALARMID) FROM T_ATTEMPER_ALARM TAA WHERE TAA.ALARMREGIONID="+ rpdcode
				+" AND TAA.EVENTSTATE='570005' AND (SELECT SYSDATE FROM DUAL)>=TAA.CASEENDTIME ORDER BY 1 ";
		Object obj = DBHandler.getSingleResult(sql);
		if (obj != null) {
			results.add(StringHelper.obj2str(obj,""));
		}
		return results;
	}
	
	/**
	 * �����ܶӱ�ע
	 * @param aid ������
	 * @param zodbz �ܶӱ�ע
	 * @return �����Ƿ�ɹ�
	 */
	public boolean updateZodbz(String aid, String zodbz){
		boolean isSuccess = false;
		if(aid != null && zodbz != null){
			String sql = "update T_ATTEMPER_ROADBUILD set zodbz='" + zodbz
					+ "' where alarmid='" + aid + "'";
			isSuccess = FlowUtil.write(sql,logger,"�����ܶӱ�ע");
		}
		return isSuccess;
	}

    /**
     *
     * @param res
     * @return
     */
    //Modified by Liuwx 2012��3��13��11:16:22
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

            isExistSql = "select count(*) from (select taa.alarmid from t_attemper_alarm taa where taa.eventtype='001023' and " +
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
            logger.error("��ѯռ����Ϣ����:");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }
}
