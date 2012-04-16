package com.ehl.dispatch.cdispatch.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;


public class AccUtil {
	Logger logger = Logger.getLogger(AccUtil.class);
	
	/**
	 * @作者: lidq
	 * @版本号：1.0
	 * @函数说明：根据事故信息内容生产sql集合
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-10
	 * @修改者：
	 * @修改日期：
	 */
	public static List<String> setAccidentInfo(HttpServletRequest request){
		
		List<String> sqlList = new ArrayList<String>();//
		List<String> alarmflow = new ArrayList<String>();//反馈表信息
		List<String> alarmInfo = new ArrayList<String>();//报警表信息
		List<String> accInfo = new ArrayList<String>();//事故子表信息
		List<String> AccCar = new ArrayList<String>();//事故子表信息
		String insertOrUpdate = StringHelper.obj2str(request.getParameter("insertOrUpdate"),"");
		
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		String feedBackId = StringHelper.obj2str(request.getParameter("feedBackId"),"");
		String feedBackTime = StringHelper.obj2str(request.getParameter("feedBackTime"),"");
		String feedBackPerson = StringHelper.obj2str(request.getParameter("feedBackPerson"),"");
		String feedBackType = StringHelper.obj2str(request.getParameter("feedBackType"),"");
		String responsiblePerson = StringHelper.obj2str(request.getParameter("responsiblePerson"),"");
		String feedBackUnit = StringHelper.obj2str(request.getParameter("feedBackUnit"),"");
		alarmflow.add(feedBackId);//上报流程id
		alarmflow.add(alarmId);//报警id
		alarmflow.add(feedBackTime);//上报时间
		alarmflow.add(feedBackPerson);//上报人
		alarmflow.add(responsiblePerson);//上报批准人
		alarmflow.add(feedBackType);//上报方式
		alarmflow.add(feedBackUnit);//上报单位
		if(insertOrUpdate.equals("0")){
			String alarmflowSql = modifyFlowInfo(alarmflow,insertOrUpdate);
			sqlList.add(alarmflowSql);
		}
		
		String ReceiveType = StringHelper.obj2str(request.getParameter("ReceiveType"),"");
		String ReceiveUnitId = StringHelper.obj2str(request.getParameter("ReceiveUnitId"),"");
		String ReceiveUnit = StringHelper.obj2str(request.getParameter("ReceiveUnit"),"");
		String ReceiveTime = StringHelper.obj2str(request.getParameter("ReceiveTime"),"");
		String ReceivePerson = StringHelper.obj2str(request.getParameter("ReceivePerson"),"");
		
		String X = StringHelper.obj2str(request.getParameter("X"),"");
		String Y = StringHelper.obj2str(request.getParameter("Y"),"");
		String alarmState = StringHelper.obj2str(request.getParameter("alarmState"),"");
		String alarmTime = StringHelper.obj2str(request.getParameter("alarmTime"),"");
		String AlarmAddress = StringHelper.obj2str(request.getParameter("AlarmAddress"),"");
		String roadID = StringHelper.obj2str(request.getParameter("roadID"),"");
		String roadName = StringHelper.obj2str(request.getParameter("roadName"),"");
		String kmvalue = StringHelper.obj2str(request.getParameter("kmvalue"),"");
		String mvalue = StringHelper.obj2str(request.getParameter("mvalue"),"");
		if(X.equals("")||Y.equals("")){
			if(!roadID.equals("") && !kmvalue.equals("")){
				if(mvalue.equals("")){
					mvalue = "0";
				}
				//快速定位
				
			}
		}
		String sjjj = StringHelper.obj2str(request.getParameter("sjjj"),"");
		String swsg = StringHelper.obj2str(request.getParameter("swsg"),"");
		String whsg =  StringHelper.obj2str(request.getParameter("whsg"),"");
		String zcyd = StringHelper.obj2str(request.getParameter("zcyd"),"");
		String ydtkm = StringHelper.obj2str(request.getParameter("ydtkm"),"");
		String yydksg = StringHelper.obj2str(request.getParameter("yydksg"),"");
		String rodetype = StringHelper.obj2str(request.getParameter("rodetype"),"");
		
		String carId = StringHelper.obj2str(request.getParameter("carId"),"");
		String hpzl = StringHelper.obj2str(request.getParameter("hpzl"),"");
		String hphm = StringHelper.obj2str(request.getParameter("hphm"),"");
		String drvname = StringHelper.obj2str(request.getParameter("drvname"),"");
		
		String carIdList[] = carId.split(",");
		if(!carIdList[0].equals("0")){
			//先删除车辆再新增
			String delAccCar = "DELETE FROM T_ATTEMPER_ACCCAR WHERE ALARMID='" + alarmId + "'";
			sqlList.add(delAccCar);
		}
		String hpzlList[] = hpzl.split(",");;
		String hphmList[] = hphm.split(",");;
		String drvnameList[] = drvname.split(",");
		if(drvnameList == null){
			drvnameList = new String[hpzlList.length];
		}
		
		String carIdStr = "",hpzlStr = "",hphmStr = "",drvnameStr = "";
		for (int i = 0; i < hpzlList.length; i++) {
//			carIdStr = carIdList[i];
			hpzlStr = hpzlList[i];
			if(hpzlStr.equals("")){
				break;
			}
			hphmStr = hphmList[i];
			if(drvnameList[i].equals("0")){
				drvnameStr = "";
			}else{
				drvnameStr = StringHelper.obj2str(drvnameList[i],"");
			}
			AccCar.add(feedBackUnit);//上报机构id
			AccCar.add(carIdStr);//车辆id
			AccCar.add(alarmId);//报警id
			AccCar.add(hpzlStr);//号牌种类
			AccCar.add(hphmStr);//号牌号码
			AccCar.add(drvnameStr);//驾驶人姓名
			String AcccarSql = "";
			AcccarSql = modifyAccCarInfo(AccCar,insertOrUpdate);
			if(!AcccarSql.equals("")){
				sqlList.add(AcccarSql);
			}
			AccCar = new ArrayList<String>();
		}
		
		String sgdj = StringHelper.obj2str(request.getParameter("sgdj"),"");
		String sgyy = StringHelper.obj2str(request.getParameter("sgyy"),"");
		String sgxt = StringHelper.obj2str(request.getParameter("sgxt"),"");
		String dcswrs = StringHelper.obj2str(request.getParameter("dcswrs"),"");
		int ssrs = StringHelper.obj2int(request.getParameter("ssrs"),0);
		String xlbmrs = StringHelper.obj2str(request.getParameter("xlbmrs"),"");
		String qjwxswrs = StringHelper.obj2str(request.getParameter("qjwxswrs"),"");
		String alarmDesc = StringHelper.obj2str(request.getParameter("alarmDesc"),"");
		
		accInfo.add(alarmId);//报警id
		accInfo.add(ReceiveTime);//接警时间
		accInfo.add(ReceiveType);//接警方式 同报警方式
		accInfo.add(ReceiveUnitId);//接警单位id
		accInfo.add(ReceivePerson);//接警人
		accInfo.add(sgdj);//事故等级
		accInfo.add(sgyy);//事故原因
		accInfo.add(sgxt);//事故形态
		accInfo.add(xlbmrs);//下落不明人数
		accInfo.add(qjwxswrs);//抢救无效死亡人数
		accInfo.add(sjjj);//是否涉及军警
		accInfo.add(swsg);//否涉外事务
		accInfo.add(zcyd);//是否造成拥堵
		accInfo.add(ydtkm);//拥堵千米值
		accInfo.add(yydksg);//否营运大客事故
		accInfo.add(dcswrs);//当场死亡人数
		accInfo.add(ssrs+"");//受伤人数
		accInfo.add(whsg);//是否危化车辆
		accInfo.add(rodetype);//事故道路类型 add by 2010-01-19
		accInfo.add(alarmTime);//事故发生时间

		String accInfoSql = modifyAccInfo(accInfo,insertOrUpdate);
		sqlList.add(accInfoSql);
		
		alarmInfo.add(alarmId);//报警单号
		alarmInfo.add(alarmTime);//报警时间
		alarmInfo.add(alarmDesc);//报警内容
		alarmInfo.add(AlarmAddress);//报警地点
		alarmInfo.add(ReceiveUnitId);//报警辖区编号
		alarmInfo.add(ReceiveUnit);//报警辖区
		alarmInfo.add(X);//X坐标
		alarmInfo.add(Y);//Y坐标
		alarmInfo.add(alarmState);//事件状态
		alarmInfo.add(roadID);//道路编号
		alarmInfo.add(roadName);//道路名称
		alarmInfo.add(kmvalue);//道路千米值
		alarmInfo.add(mvalue);//百米值
		alarmInfo.add(ReceiveUnitId);//报警机构
		alarmInfo.add("001001");//事件类型 交通事故
		alarmInfo.add("002022");//事件来源 警情上报
		String SUPERUNIT = "";
		if(ReceiveUnitId.length()<4){
			//查询其所属支队
		}else{
			//机构编码符合规则的情况
			SUPERUNIT = ReceiveUnitId.substring(0, 4) + StringUtil.fill("0", ReceiveUnitId.length()-4); //所属机构
		}
		
		alarmInfo.add(SUPERUNIT);//事件来源 警情上报
		if(!"".equals(roadName)){
			if(!"".equals(kmvalue)){
				roadName += kmvalue + "公里";
			}
			if(!"".equals(mvalue)){
				roadName += mvalue + "米";
			}
		}
		
		alarmInfo.add(AlarmAddress + roadName +" 发生 交通事故");//事件标题
		//死亡事故，营运大客事故，危化事故，涉外事故，涉及军警事故，受伤人数超过10
		if(!dcswrs.equals("0")||yydksg.equals("true")||whsg.equals("true")||swsg.equals("true")||sjjj.equals("true")||ssrs>10){
			alarmInfo.add("006001");//事件等级
		}
		String alarmInfoSql = modifyAlarmInfo(alarmInfo,insertOrUpdate);
		sqlList.add(alarmInfoSql);
		return sqlList;
	}
	
	/**
	 * @说明：编辑报警流程表信息
	 * @param alarmflow
	 * @return sql
	 */
	public static String modifyFlowInfo(List<String> alarmflow,String insertOrUpdate){
		StringBuffer infoSql = new StringBuffer();
		String flowid = alarmflow.get(0);
		String alarmid = alarmflow.get(1);
		String reporttime = alarmflow.get(2);
		String reportperson = alarmflow.get(3);
		String responsibleperson = alarmflow.get(4);
		String reporttype = alarmflow.get(5);
		String reportunit = alarmflow.get(6);

		if("".equals(flowid)){
			flowid += reportunit.substring(0,6);
			flowid += StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
			infoSql.append("INSERT INTO T_ATTEMPER_ALARMFLOW(FLOWID,ALARMID,REPORTTIME,REPORTPERSON,RESPONSIBLEPERSON,REPORTTYPE,REPORTUNIT,");
			infoSql.append("RECIVEUNIT,RECIVETIME,RECIVETYPE) ");
			infoSql.append("VALUES('" + flowid+"','"+alarmid+"',TO_DATE('" + reporttime + "','yyyy-mm-dd hh24:mi'),");
			infoSql.append("'" + reportperson+"','"+responsibleperson+"','"+reporttype+"','"+reportunit+"',");
			infoSql.append("'" + reportunit+"',TO_DATE('" + reporttime + "','yyyy-mm-dd hh24:mi'),'"+reporttype+"')");
		}else{
			infoSql.append("");
		}
		
		return infoSql.toString();
	}
	
	/**
	 * @说明：编辑报警表信息
	 * @param accInfo
	 * @return
	 */
	public static String modifyAlarmInfo(List<String> alarmInfo,String insertOrUpdate){
		StringBuffer infoSql = new StringBuffer();
		if(insertOrUpdate.equals("0")){
			infoSql.append("INSERT INTO T_ATTEMPER_ALARM(ALARMID,ALARMTIME,ALARMDESC,ALARMADDRESS,ALARMREGIONID,ALARMREGION,");
			infoSql.append("X,Y,EVENTSTATE,ROADID,ROADNAME,KMVALUE,MVALUE,ALARMUNIT,EVENTTYPE,EVENTSOURCE,SUPERUNIT,TITLE,EVENTLEVEL) ");
			infoSql.append("VALUES('" + alarmInfo.get(0) + "',TO_DATE('" + alarmInfo.get(1) + "','yyyy-mm-dd hh24:mi'),");
			for (int i = 2; i < alarmInfo.size(); i++) {
				if (i != alarmInfo.size() - 1) {
					infoSql.append("'" + alarmInfo.get(i) + "',");
				} else {
					infoSql.append("'" + alarmInfo.get(i) + "')");
				}
			}
		}else if(insertOrUpdate.equals("1")){
			infoSql.append("UPDATE T_ATTEMPER_ALARM SET ALARMDESC='" + alarmInfo.get(2) + "',ALARMADDRESS='" + alarmInfo.get(3) + "',");
			infoSql.append("ALARMREGIONID='" + alarmInfo.get(4) + "',ALARMREGION='" + alarmInfo.get(5) + "',");
			infoSql.append("X='" + alarmInfo.get(6) + "',Y='" + alarmInfo.get(7) + "',EVENTSTATE='" + alarmInfo.get(8) + "',");
			infoSql.append("ROADID='" + alarmInfo.get(9) + "',ROADNAME='" + alarmInfo.get(10) + "',KMVALUE='" + alarmInfo.get(11) + "',");
			infoSql.append("MVALUE='" + alarmInfo.get(12) + "',ALARMUNIT='" + alarmInfo.get(13) + "',EVENTTYPE='" + alarmInfo.get(14) + "',");
			infoSql.append("EVENTSOURCE='" + alarmInfo.get(15) + "',SUPERUNIT='" + alarmInfo.get(16) + "',TITLE='" + alarmInfo.get(17) + "' ");
			infoSql.append("WHERE ALARMID='" + alarmInfo.get(0) + "'");
		}
		
		return infoSql.toString();
	}
	
	/**
	 * @说明：编辑事故子表信息
	 * @param accInfo
	 * @return
	 */
	private static String modifyAccInfo(List<String> accInfo,String insertOrUpdate){
		StringBuffer infoSql = new StringBuffer();
		if(insertOrUpdate.equals("0")){
			infoSql.append("INSERT INTO T_ATTEMPER_ACCIDENT(ALARMID,RECEIVETIME,ALARMMANNER,RECEIVEUNIT,RECEIVEPERSON,ACCLEVEL,ACCCAUSE,ACCMORPH,");
			infoSql.append("MISSINGPERSONCOUNT,DEATHPERSONAFTERCOUNT,ISPOLICE,ISFOREIGNAFFAIRS,ISCONGESTION,CONGESTIONKMVALUE,ISBUS,");
			infoSql.append("DEATHPERSONCOUNT,GBHPERSONCOUNT,ISDANAGERCAR,EVENTROADTYPE,CASEHAPPENTIME) ");
			infoSql.append("VALUES('" + accInfo.get(0) + "',TO_DATE('" + accInfo.get(1) + "','yyyy-mm-dd hh24:mi'),");
			for (int i = 2; i < accInfo.size(); i++) {
				if (i != accInfo.size() - 1) {
					infoSql.append("'" + accInfo.get(i) + "',");
				} else {
					infoSql.append("TO_DATE('" + accInfo.get(i) + "','yyyy-mm-dd hh24:mi'))");
				}
			}
		}else if(insertOrUpdate.equals("1")){
			infoSql.append("UPDATE T_ATTEMPER_ACCIDENT SET ACCLEVEL='" + accInfo.get(5) + "',ACCCAUSE='" + accInfo.get(6) + "',ACCMORPH='" + accInfo.get(7) + "',");
			infoSql.append("MISSINGPERSONCOUNT='" + accInfo.get(8) + "',DEATHPERSONAFTERCOUNT='" + accInfo.get(9) + "',ISPOLICE='" + accInfo.get(10) + "',");
			infoSql.append("ISFOREIGNAFFAIRS='" + accInfo.get(11) + "',ISCONGESTION='" + accInfo.get(12) + "',CONGESTIONKMVALUE='" + accInfo.get(13) + "',");
			infoSql.append("ISBUS='" + accInfo.get(14) + "',DEATHPERSONCOUNT='" + accInfo.get(15) + "',GBHPERSONCOUNT='" + accInfo.get(16) + "',");
			infoSql.append("ISDANAGERCAR='" + accInfo.get(17) + "',CASEHAPPENTIME='" + accInfo.get(18) + "' ");
			infoSql.append("WHERE ALARMID='" + accInfo.get(0) + "'");
		}
		return infoSql.toString();
	}
	
	/**
	 * @说明：编辑事故车辆表信息
	 * @param Acccar
	 * @return
	 */
	private static String modifyAccCarInfo(List<String> AccCar,String insertOrUpdate){
		StringBuffer infoSql = new StringBuffer();
		String reportunit = AccCar.get(0);//上报单位
		String carid = "";//AccCar.get(1);
		if(AccCar.get(2).equals("") || AccCar.get(3).equals("")){
			return infoSql.toString();
		}
//		if("0".equals(carid)){
		carid += reportunit.substring(0,6);
		carid += StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS") + Math.random();
		
		infoSql.append("INSERT INTO T_ATTEMPER_ACCCAR(CARID,ALARMID,HPZL,HPZM,DRVNAME) ");
		infoSql.append("VALUES('" + carid + "',");
		for (int i = 2; i < AccCar.size(); i++) {
			if (i != AccCar.size() - 1) {
				infoSql.append("'" + AccCar.get(i) + "',");
			} else {
				infoSql.append("'" + AccCar.get(i) + "')");
			}
		}
//		}else{
//			infoSql.append("UPDATE T_ATTEMPER_ACCCAR SET HPZL='" + AccCar.get(3) + "',HPZM='" + AccCar.get(4) + "',DRVNAME='" + AccCar.get(5) + "' ");
//			infoSql.append("WHERE ALARMID='" + AccCar.get(2) + "' AND CARID='" + carid + "'");
//		}
		return infoSql.toString();
	}
	
	/**
	 * @说明：删除警情信息
	 * @param alarmId policeType
	 * @return
	 */
	public static List<String> delPolice(String alarmId,String policeType){
		List<String> sqlList = new ArrayList<String>();//
		sqlList.add("DELETE FROM T_ATTEMPER_ALARMFLOW WHERE ALARMID='" + alarmId + "'");
		sqlList.add("DELETE FROM T_ATTEMPER_ALARM WHERE ALARMID='" + alarmId + "'");
		
		int type = StringHelper.obj2int(policeType.substring(3, 6),0);
		switch (type) {
			case 1://交通事故
				sqlList.add("DELETE FROM T_ATTEMPER_ACCIDENT WHERE ALARMID='" + alarmId + "'");
				sqlList.add("DELETE FROM T_ATTEMPER_ACCCAR WHERE ALARMID='" + alarmId + "'");
				break;
			case 3://交通施工占道
				sqlList.add("DELETE FROM T_ATTEMPER_ROADBUILD WHERE ROID='" + alarmId + "'");
				break;
			case 4://交通交通管制
				sqlList.add("DELETE FROM T_ATTEMPER_TRAFFICRESTRAIN WHERE ROID='" + alarmId + "'");
				break;
			default:
				
			break;
		}
		
		return sqlList;
	}
}
