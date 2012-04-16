package com.ehl.dispatch.cdispatch.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;


public class AccDao {
	Logger logger = Logger.getLogger(AccDao.class);
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：交通事故上报事务处理
	 * @参数：sqlList
	 * @返回：false or true
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public boolean excuteSql(List<String> sqlList){
//		开始事务
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
				if(!sql.equals("") && db.executeUpdate(sql)>=0){
				}else{
					bOk = false;
					break;
				}
			}
		} catch(Exception e) {
			bOk = false;
			logger.error("【警情系统】-->上报警情事务处理错误：" + e.getMessage());
			System.out.println(e.getMessage());
		}finally {
			if(null != db) {
				if(bOk) {
					db.commitTrans(false);
				} else {
					db.commitTrans(true);
				}
				//关闭数据对象
				DBFactory.closeDatabase(db);
			}
		}
		return bOk;
	}
	
	/**
	 * 从数据库获取警情上报信息
	 * 说明：只查询支队和总队信息
	 */
	public Object[][] getAlarmFlowInfo(String alarmId){
		Object[][] res = null;
		StringBuffer getInfoSql = new StringBuffer();
		getInfoSql.append("SELECT TO_CHAR(RECIVETIME,'YYYY-MM-DD HH24:MI'),REPORTPERSON,RESPONSIBLEPERSON,VERIFYSITUATION,REPORTSITUATION,REPORTUNIT,");
		getInfoSql.append("DECODE(SUBSTR(REPORTUNIT,3,2),'00','0',DECODE(SUBSTR(REPORTUNIT,5,2),'00','1','2'))DEPTTYPE ");
		getInfoSql.append("FROM T_ATTEMPER_ALARMFLOW ");
		getInfoSql.append("WHERE DECODE(SUBSTR(REPORTUNIT,3,2),'00','0',DECODE(SUBSTR(REPORTUNIT,5,2),'00','1','2')) !='2' ");
		getInfoSql.append("AND ALARMID='"+ alarmId +"'");
		try {
			res = DBHandler.getMultiResult(getInfoSql.toString());
		} catch (Exception e) {
			logger.error("【警情系统】-->获取警情上报信息错误：" + e.getMessage());
		}
		return res;
	}
	
	/**
	 * 从数据库获取警情信息
	 */
	public Object[] getAlarmInfo(String alarmId){
		Object[] res = null;
		
		StringBuffer getInfoSql = new StringBuffer();
		getInfoSql.append("SELECT ALARM.ALARMID,FLOW.FLOWID,TO_CHAR(FLOW.REPORTTIME,'YYYY-MM-DD HH24:MI'),FLOW.REPORTPERSON,FLOW.RESPONSIBLEPERSON,FLOW.REPORTTYPE,FLOW.REPORTUNIT,");
		getInfoSql.append("TO_CHAR(ALARM.ALARMTIME,'YYYY-MM-DD HH24:MI'),ALARM.ALARMDESC,ALARM.ALARMADDRESS,ALARM.ALARMREGIONID,ALARM.ALARMREGION,ALARM.X,ALARM.Y,");
		getInfoSql.append("ALARM.EVENTSTATE,ALARM.ROADID,ALARM.ROADNAME,ALARM.KMVALUE,ALARM.MVALUE,ALARM.ALARMUNIT,ALARM.EVENTTYPE,ALARM.EVENTSOURCE,");
		getInfoSql.append("TO_CHAR(ACC.RECEIVETIME,'YYYY-MM-DD HH24:MI'),ACC.ALARMMANNER,ACC.RECEIVEUNIT,F_GET_DEPT(ACC.RECEIVEUNIT),ACC.RECEIVEPERSON,ACC.ACCLEVEL,");
		getInfoSql.append("ACC.ACCCAUSE,ACC.ACCMORPH,ACC.MISSINGPERSONCOUNT,ACC.DEATHPERSONAFTERCOUNT,ACC.ISPOLICE,ACC.ISFOREIGNAFFAIRS,");
		getInfoSql.append("ACC.ISCONGESTION,ACC.CONGESTIONKMVALUE,ACC.ISBUS,ACC.DEATHPERSONCOUNT,ACC.GBHPERSONCOUNT,ACC.ISDANAGERCAR,F_GET_DEPT(FLOW.REPORTUNIT),ACC.EVENTROADTYPE ");
		getInfoSql.append("FROM T_ATTEMPER_ALARM ALARM,T_ATTEMPER_ACCIDENT ACC,T_ATTEMPER_ALARMFLOW FLOW ");
		getInfoSql.append("WHERE ACC.ALARMID = ALARM.ALARMID AND ACC.ALARMID = FLOW.ALARMID AND ACC.RECEIVEUNIT = FLOW.REPORTUNIT AND ALARM.ALARMID='"+ alarmId +"'");
		
		try {
			res = DBHandler.getLineResult(getInfoSql.toString());
		} catch (Exception e) {
			logger.error("【警情系统】-->获取警情信息错误：" + e.getMessage());
		}
		
		return res;
	}
	
	/**
	 * 从数据库获取事故车辆信息
	 */
	public Object[][] getAccCarInfo(String alarmId){
		Object[][] res = null;
		
		StringBuffer getInfoSql = new StringBuffer();
		getInfoSql.append("SELECT CAR.CARID,CAR.HPZL,CAR.HPZM,CAR.DRVNAME ");
		getInfoSql.append("FROM T_ATTEMPER_ACCCAR CAR WHERE CAR.ALARMID='"+ alarmId +"'");
		
		try {
			res = DBHandler.getMultiResult(getInfoSql.toString());
		} catch (Exception e) {
			logger.error("【警情系统】-->获取事故车辆错误：" + e.getMessage());
		}
		
		return res;
	}
	
	/**
	 * 说明：最近（24小时/当月）需关注警情统计,报警主表中的特大事故
	 * 参数：depttype 机构类型,deptcode 机构代码,countType 统计类型
	 */
	public Object getPoliceOnTime(int depttype,String deptcode,String countType){
		Object res = null;
		
		StringBuffer getInfoSql = new StringBuffer();
		getInfoSql.append("SELECT COUNT(ALARM.ALARMID) FROM T_ATTEMPER_ALARM ALARM WHERE ALARM.EVENTLEVEL='006001' AND ALARM.EVENTSTATE='004001'");
		switch (depttype) {
			case 0://总队
				
				break;
			case 1://支队
				getInfoSql.append("AND SUBSTR(ALARM.ALARMUNIT,0,4)='"+deptcode.substring(0, 4)+"' ");
				break;
			case 2://大队
				getInfoSql.append("AND ALARM.ALARMUNIT='"+deptcode+"' ");
				break;
			default:
				break;
		}
		if(countType.endsWith("DD")){
			getInfoSql.append("AND TRUNC(SYSDATE,'DD')=TRUNC(ALARM.ALARMTIME, 'DD') ");
		}else if(countType.endsWith("MM")){
			getInfoSql.append("AND TRUNC(SYSDATE,'MM')=TRUNC(ALARM.ALARMTIME, 'MM') ");
		}
		
		try {
			res = DBHandler.getSingleResult(getInfoSql.toString());
		} catch (Exception e) {
			logger.error("【警情系统】-->定时获取警情信息错误：" + e.getMessage());
		}
		
		
		return res;
	}
	
	/**
	 * 生成新的事件id，时间，上报单位
	 */
	public  String setNewInfo(String usercode) {
		String xmlstr = "";
		String alarmId = StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
		String alarmDate = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm");

		String deptid = "";
		String deptname = "";
		Object[] people = null;
		try {
			if (usercode != null) {
				String peoplesql = "select ryid,xm from t_sys_person where ryid=(select personcode from t_sys_user where usercode='"
						+ usercode + "')";
				people = DBHandler.getLineResult(peoplesql);
				if(people==null){
					people=new Object[2];
				}
				String dwmcsql = "SELECT DEPTCODE FROM T_SYS_USER WHERE USERCODE='"
						+ usercode + "'";
				deptid = DBHandler.getSingleResult(dwmcsql).toString();
				String deptsql = "select jgmc from t_sys_department where jgid='"
						+ deptid + "'";
				deptname = DBHandler.getSingleResult(deptsql).toString();
			}

			StringBuffer sbXml = new StringBuffer();
			sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
			sbXml.append("<rfXML>\n");
			sbXml.append("<RFWin>\n");

			sbXml.append("<col>");
			sbXml.append(alarmId);
			sbXml.append("</col>\n");
			sbXml.append("<col>");
			sbXml.append(alarmDate);
			sbXml.append("</col>\n");
			sbXml.append("<col>");
			sbXml.append(deptname);
			sbXml.append("</col>\n");
			sbXml.append("<col>");
			sbXml.append(deptid);
			sbXml.append("</col>\n");
			sbXml.append("<col>");
			sbXml.append(StringHelper.obj2str(people[1],""));
			sbXml.append("</col>\n");
			sbXml.append("<col>");
			sbXml.append(StringHelper.obj2str(people[0],""));
			sbXml.append("</col>\n");

			sbXml.append("</RFWin>\n");
			sbXml.append("</rfXML>\n");
			xmlstr = sbXml.toString();
		} catch (Exception e) {
			logger.error("【警情系统】" + "新增警情生成新的事件id，时间，上报单位时的XML错误");
			System.out.println(e.getMessage());
		}
		return xmlstr;
	}
}
