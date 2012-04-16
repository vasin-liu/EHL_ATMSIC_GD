package com.ehl.dispatch.cdispatch.dao;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.sm.common.util.StringUtil;

/**
 * 道路管理数据类
 * @author wangxt
 * @date 2009-1-16
 *
 */
public class ContrlManageDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);
	 
	/**
	 * 新增道路信息
	 * @param res
	 * @return
	 */
	public boolean addContrlInfo(HashMap res){
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
			String EndKMVALUE = res.get("EndKMVALUE").toString();
			String MVALUE = res.get("MVALUE").toString();
			String EndMVALUE = res.get("EndMVALUE").toString();
			String CaseHappenTime = res.get("CaseHappenTime").toString();
			String CaseEndTime = res.get("CaseEndTime").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTPERSON = res.get("REPORTPERSON").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();
			String PLAN = res.get("PLAN").toString();
			String ALARMID = ALARMUNIT.substring(0,6)+StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
			String EVENTSTATE = res.get("EVENTSTATE").toString();//(0:"管制进行中" 1:"管制已撤销")
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			String Xvalue = res.get("Xvalue").toString();
			String Yvalue = res.get("Yvalue").toString();
			
//			String radioType = "";
//			if("1".equals(ROADDIRECTION)) {
//				radioType = "上行";
//			} else {
//				radioType = "下行";
//			}
			
			sql ="insert into t_attemper_alarm(ALARMID,ROADID,EVENTSOURCE,EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION," +
					"ROADNAME,KMVALUE,EndKMVALUE,CaseHappenTime,CaseEndTime,REPORTUNIT,REPORTPERSON,REPORTTIME,ALARMTIME,EVENTSTATE,ROADDIRECTION,MVALUE,EndMVALUE,X,Y)";
			sql += " values('"+ALARMID+"','"+ROADID+"','"+EVENTSOURCE+"','"+EVENTTYPE+"','"+ALARMUNIT+"','"+TITLE+"','"+ALARMREGIONID+"','"+ALARMREGION+"','"
					+ROADNAME+"','"+KMVALUE+"','"+EndKMVALUE+"',to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi'),to_date('"+CaseEndTime+"','yyyy-mm-dd hh24:mi'),'"
					+REPORTUNIT+"','"+REPORTPERSON+"',to_date('"+REPORTTIME+"','yyyy-mm-dd hh24:mi'),to_date('"+REPORTTIME+"','yyyy-mm-dd hh24:mi'),'"+EVENTSTATE+"','"+ROADDIRECTION+"','"
					+MVALUE+"','"+EndMVALUE+"','"+Xvalue+"','"+Yvalue+"')";
			System.out.println("addContrlInfo1:"+sql);
			flag = DBHandler.execute(sql);
			sql ="insert into T_ATTEMPER_TRAFFICRESTRAIN(ALARMID,PLAN)";
			sql += " values('"+ALARMID+"','"+PLAN+"')";
			System.out.println("addContrlInfo2:"+sql);
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("新增管制信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 编辑道路信息
	 * @param res
	 * @return
	 */
	public boolean editContrlInfo(HashMap res) {
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
			String EndKMVALUE = res.get("EndKMVALUE").toString();
			String CaseHappenTime = res.get("CaseHappenTime").toString();
			String CaseEndTime = res.get("CaseEndTime").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTPERSON = res.get("REPORTPERSON").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();
			String PLAN = res.get("PLAN").toString();
			String ALARMID = res.get("ALARMID").toString();
			String EVENTSTATE = res.get("EVENTSTATE").toString();//(0:"管制进行中" 1:"管制已撤销")
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			String MVALUE = res.get("MVALUE").toString();
			String EndMVALUE = res.get("EndMVALUE").toString();
			String Xvalue = res.get("Xvalue").toString();
			String Yvalue = res.get("Yvalue").toString();
			
			String radioType = "";
			radioType = ROADDIRECTION;
//			if("1".equals(ROADDIRECTION)) {
//				radioType = "上行";
//			} else {
//				radioType = "下行";
//			}
			
			sql ="update t_attemper_alarm";
			sql +=" set ROADID='"+ROADID+"'"
				+",ROADNAME='"+ROADNAME+"'"
				+",X='"+Xvalue+"'"
				+",Y='"+Yvalue+"'"
				+",KMVALUE='"+KMVALUE+"'"
				+",EndKMVALUE='"+EndKMVALUE+"'"
				+",MVALUE='"+MVALUE+"'"
				+",EndMVALUE='"+EndMVALUE+"'";
				if(CaseHappenTime != null && !("".equals(CaseHappenTime)) && !("　".equals(CaseHappenTime))) {
					sql +=",CaseHappenTime=to_date('"+CaseHappenTime+"','yyyy-mm-dd hh24:mi')";
				}
				if(CaseEndTime != null && !("".equals(CaseEndTime)) && !("　".equals(CaseEndTime))) {
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
			
			sql ="update T_ATTEMPER_TRAFFICRESTRAIN";
			sql +=" set PLAN='"+PLAN+"'";
			sql +=" where ALARMID='"+ALARMID+"'";
			System.out.println("2:"+sql);
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("编辑管制信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 删除道路信息
	 * @param bh
	 * @return
	 */
	public boolean delteContrlInfo(String ALARMID) {
		String sql = null;
		boolean flag = false;
		try {
			sql ="update t_attemper_alarm";
			sql +=" set EVENTSTATE='"+"570004"+"'";
			sql +=" where ALARMID='"+ALARMID+"'";
			System.out.println("1:"+sql);
			flag = DBHandler.execute(sql);
			/*sql ="delete from T_ATTEMPER_TRAFFICRESTRAIN";
			sql +=" where ALARMID='"+ALARMID+"'";
			flag = DBHandler.execute(sql);
			sql ="delete from T_ATTEMPER_ALARM";
			sql +=" where ALARMID='"+ALARMID+"'";
			flag = DBHandler.execute(sql);*/
		}catch(Exception e) {
			logger.error("删除管制信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 查询一条管制信息
	 * @param bh
	 * @return
	 */
	public Object[] getContrlInfo(String alarmid) {
		String sql = null;
		Object[] obj=null;
		try {
			sql ="select taa.ALARMID,taa.ROADID,tat.PLAN,taa.ROADDIRECTION,taa.ROADNAME,taa.KMVALUE,taa.EndKMVALUE,to_char(taa.CaseHappenTime,'yyyy-mm-dd HH24:mi')" +
					",to_char(taa.CaseEndTime,'yyyy-mm-dd HH24:mi'), f_get_dept(taa.REPORTUNIT),taa.REPORTPERSON,to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi'),taa.MVALUE,taa.EndMVALUE" +
					" from T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat ";
			sql +=" where taa.ALARMID='"+alarmid+"' and tat.ALARMID=taa.ALARMID ";
			System.out.println("3:"+sql);
			obj = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			logger.error("查询一条管制信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 查询一条管制信息
	 * @param bh
	 * @return
	 */
	public Object[] getRoadWayInfo(String roadId) {
		String sql = null;
		Object[] obj=null;
		try {
			sql ="select ROADSTARTADDNAME,ROADENDADDNAME" +
			" from T_OA_ROAD_INFO ";
			sql +=" where ROADID='"+roadId+"'";
			System.out.println("3:"+sql);
			obj = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			logger.error("查询一条道路出入口信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
}
