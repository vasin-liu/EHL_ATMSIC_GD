package com.ehl.dispatch.bdispatch.business;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.dataAccess.AlarmInfoData;
import com.ehl.duty.common.DutyFactory;
import com.ehl.sm.common.util.StringUtil;

/**
 * 
 * 事故编辑信息操作类
 * 
 * @author JC
 * 
 */
public class AlarmInfoOpt {

	Logger logger = Logger.getLogger(AlarmInfoOpt.class);

	/**
	 * 获得鼠标点击位置附近的道路操作，将信息转成XML
	 */
	public String getNearRoadByMouse(String x, String y) throws Throwable {
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		String xmlStr = "";
		String point = x + "," + y;
		try {
			if (!x.equals("") && !y.equals("")) {
				Object[][] lines = alarmInfoData.getLineByMouse(point);
				if (lines != null) {
					StringBuffer sbXml = new StringBuffer();
					sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
					sbXml.append("<rfXML>\n");
					sbXml.append("<RFWin>\n");
					for (int i = 0; i < lines.length; i++) {
						// sbXml.append("<row id='" + i + "'
						// dlbh='"+lines[i][0]+"'
						// dlmc='"+lines[i][1]+"' shape='"+lines[i][2]+"'>");
						sbXml.append("<row id='" + i + "'>\n");
						for (int j = 0; j < lines[i].length; j++) {
							sbXml.append("<col>");
							sbXml.append(lines[i][j]);
							sbXml.append("</col>\n");
						}
						sbXml.append("</row>\n");
					}
					sbXml.append("</RFWin>\n");
					sbXml.append("</rfXML>\n");
					xmlStr = sbXml.toString();
				}
			}
		} catch (Exception e) {
			logger.error("[分控指挥掉度]" + "新增警情地图定位时获取路网生成xml时错误");
			System.out.println(e.getMessage());
		}
		// System.out.println(xmlStr);
		return xmlStr;
	}

	/**
	 * 判断是否是事故
	 */
	public boolean isAccident(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001001")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是拥堵
	 */
	public boolean isCongestion(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001002")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是故障车
	 */
	public boolean isExceptionCar(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001008")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是治安
	 */
	public boolean isPoliceEvent(Object typeId) {

		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001007")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是恶劣天气
	 */
	public boolean isBadWeather(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001006")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是市政信息
	 */
	public boolean isTownplan(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001011")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是火灾信息
	 */
	public boolean isFireAndBlast(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001012")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是地质信息
	 */
	public boolean isGeoLogicDisaster(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001010")) {
			is = true;
		}
		return is;
	}

	/**
	 * 判断是否是黑名单车
	 */
	public boolean isBlackList(Object typeId) {
		boolean is = false;
		if (StringHelper.obj2str(typeId, "").equals("001005")) {
			is = true;
		}
		return is;
	}

	public Object[] setTitle(Object[] res) {
		String title = "";
		String time = res[1].toString().split(" ")[1];
		String timestr = time.split(":")[0] + "时" + time.split(":")[1] + "分 ";
		String roadStr = StringHelper.obj2str(res[13], "");
		String thintypestr = StringHelper.obj2str(res[19], "");
		String thintype = StringHelper.obj2str(res[7], "");
		String levelstr="";
//		levelstr = StringHelper.obj2str(res[18], "");
		if (thintype.equals("")) {
			title = timestr + roadStr + levelstr;
		} else if (!thintypestr.equals("")) {
			title = timestr + roadStr + thintypestr;
		} else {
			title = timestr + roadStr + thintype;
		}
		res[19] = title;
		return res;
	}

	/**
	 * 新增一条事故报警信息
	 */
	public String editAccidentInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertAccidentInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		
		if (msg) {
			return "success";
		} else {
			return "error";
		}

	}

	/**
	 * 新增一条拥堵报警信息
	 */
	public String editCongestionInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertCongestionInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条故障车信息
	 */
	public String editExceptionCarInfo(Object[] res) throws Throwable {

		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertExceptionCarInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条治安信息
	 */
	public String editPoliceEventInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertPoliceEventInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条市政信息
	 */
	public String editTownplanInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertTownplanInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条火灾信息
	 */
	public String editFireAndBlastInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertTFireAndBlastInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条地质信息
	 */
	public String editGeoLogicDisasterInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertGeoLogicDisasterInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条恶劣天气信息
	 */
	public String editBadWeatherInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertBadWeatherInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 新增一条布控信息
	 */
	public String editBlackListInfo(Object[] res) throws Throwable {
		res = setTitle(res);
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		boolean msg = alarmInfoData.insertBlackListInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}

	public String DictConversion(Object str) {
		String lowerStr = "";
		if (str != null) {
			str = str.toString().toLowerCase().trim();
			if (str.equals("true")) {
				lowerStr = "是";
			} else {
				lowerStr = "否";
			}
		}
		return lowerStr;
	}

	/**
	 * 根据报警id获得的报警信息转成XML
	 */
	public String getAlarmInfoById(String alarmId) {
		String alarmInfoXml = "";
		Object[] res = null;
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		Object[] alarms = alarmInfoData.getAlarmInfoByAlarmId(alarmId);

		if (alarms[16].equals("004012") || alarms[16].equals("004005")) {
			alarmInfoData.updateAlarmState(alarmId, "004002");
			alarms[8] = "已接收";
			alarms[16] = "004002";
		}

		Object[][] sons = alarmInfoData.getSonListById(alarmId);
		String haveSon = "";
		if (sons != null) {
			haveSon = "1";
		} else {
			haveSon = "0";
		}
		Object[] infos = null;
		// 事故信息
		if (alarms != null && isAccident(alarms[15])) {
			res = new Object[48];
			alarms[7] = alarmInfoData.getEventThinTypeName(alarms[7]);
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getAccidentInfoByAlarmId(alarmId);
			infos[12] = DictConversion(infos[12]);
			infos[19] = DictConversion(infos[19]);
			infos[20] = DictConversion(infos[20]);
			infos[21] = DictConversion(infos[21]);
			infos[22] = DictConversion(infos[22]);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		// 拥堵信息
		else if (alarms != null && isCongestion(alarms[15])) {
			res = new Object[39];
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getCongestionInfoByAlarmId(alarmId);
			infos[16] = DictConversion(infos[16]);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		// 故障车信息
		else if (alarms != null && isExceptionCar(alarms[15])) {
			res = new Object[30];
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getExceptionCarInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		// 治安信息
		else if (alarms != null && isPoliceEvent(alarms[15])) {
			res = new Object[30];
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getPoliceEventInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		// 恶劣天气信息
		else if (alarms != null && isBadWeather(alarms[15])) {
			res = new Object[31];
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getBadWeatherInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}
		// 布控信息
		else if (alarms != null && isBlackList(alarms[15])) {
			res = new Object[26];
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getBlackListInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		// 市政信息
		else if (alarms != null && isTownplan(alarms[15])) {
			res = new Object[31];
			alarms[7] = alarmInfoData.getEventThinTypeName(alarms[7]);
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getTownplanInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}
		// 火灾信息
		else if (alarms != null && isFireAndBlast(alarms[15])) {
			res = new Object[28];
			alarms[7] = alarmInfoData.getEventThinTypeName(alarms[7]);
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getFireAndBlastInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		// 地质信息
		else if (alarms != null && isGeoLogicDisaster(alarms[15])) {
			res = new Object[28];
			alarms[7] = alarmInfoData.getEventThinTypeName(alarms[7]);
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
			infos = alarmInfoData.getGeoLogicDisasterInfoByAlarmId(alarmId);
			if (infos != null) {
				for (int i = 1; i < infos.length; i++) {
					res[i + 22] = infos[i];
				}
			}
		}

		else {
			res = new Object[23];
			alarms[7] = alarmInfoData.getEventThinTypeName(alarms[7]);
			for (int i = 0; i < alarms.length; i++) {
				res[i] = alarms[i];
			}
			res[22] = haveSon;
		}
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		if (res != null) {
			sbXml.append("<row i='0'>\n");
			for (int i = 0; i < res.length; i++) {
				sbXml.append("<col>");
				sbXml.append(StringHelper.obj2str(res[i], ""));
				sbXml.append("</col>\n");
			}
			sbXml.append("</row>\n");
		}

		sbXml.append("</RFWin>\n");
		sbXml.append("</rfXML>\n");
		alarmInfoXml = sbXml.toString();
		System.out.println(alarmInfoXml);
		return alarmInfoXml;

	}

	/**
	 * 根据mainid获取子单列表信息，形成xml
	 */
	public String getSonList(String id) {
		String strXml = "";
		AlarmInfoData alarmInfoData = new AlarmInfoData();
		Object[][] res = alarmInfoData.getSonListById(id);
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");

		if (res != null) {

			for (int i = 0; i < res.length; i++) {
				sbXml.append("<row i='" + i + "'>\n");
				for (int j = 0; j < res[i].length; j++) {
					sbXml.append("<col>");
					sbXml.append(StringHelper.obj2str(res[i][j], ""));
					sbXml.append("</col>\n");
				}
				sbXml.append("</row>\n");
			}

		}

		sbXml.append("</RFWin>\n");
		sbXml.append("</rfXML>\n");
		strXml = sbXml.toString();
		System.out.println(strXml);

		return strXml;
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
			logger.error("[分控指挥掉度]" + "新增警情生成新的事件id，时间，上报单位时的XML错误");
			System.out.println(e.getMessage());
		}
		return xmlstr;
	}

	/**
	 * 生成当天勤务人员信息
	 */
	public String setDutyPersons(String deptId){
		String date = StringUtil.getCurrDateTime("yyyy-MM-dd");
		
		String xmlstr="";
		StringBuffer sbXml = new StringBuffer();
		Object[][] res=DutyFactory.getQueryDutyDao().getPersonsByDate(date,deptId);
		
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		try{
			if(res!=null){
				for(int i=0;i<res.length;i++){
					sbXml.append("<row>\n");
					for(int  j=0;j<res[i].length;j++){
						sbXml.append("<col>\n");
						sbXml.append(StringHelper.obj2str(res[i][j],""));
						sbXml.append("</col>\n");
					}
					sbXml.append("</row>\n");
				}
			}				
			sbXml.append("</RFWin>\n");
			sbXml.append("</rfXML>\n");
			xmlstr = sbXml.toString();
			System.out.println(xmlstr);
		} catch (Exception e) {
			logger.error("[分控指挥调度]" + "生成当天勤务人员信息XML错误");
			System.out.println(e.getMessage());
		}
		return xmlstr;
	}
	
	/**
	 * 人员信息
	 */
	public String setPcsPersons(String deptId){
		
		String xmlstr="";
		StringBuffer sbXml = new StringBuffer();
		
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		try{
			AlarmInfoData alarmInfoData = new AlarmInfoData();
			Object[][] res = alarmInfoData.getPcsPersons(deptId);
			
			if(res!=null){
				for(int i=0;i<res.length;i++){
					sbXml.append("<row>\n");
					for(int  j=0;j<res[i].length;j++){
						sbXml.append("<col>\n");
						sbXml.append(StringHelper.obj2str(res[i][j],""));
						sbXml.append("</col>\n");
					}
					sbXml.append("</row>\n");
				}
			}				
			sbXml.append("</RFWin>\n");
			sbXml.append("</rfXML>\n");
			xmlstr = sbXml.toString();
			System.out.println(xmlstr);
		} catch (Exception e) {
			logger.error("[分控指挥调度]" + "生成当天PCS人员信息XML错误");
			System.out.println(e.getMessage());
		}
		return xmlstr;
	}
	
	
	/**
	 * 人员信息
	 */
	public String setPcsCars(String deptId){
		
		String xmlstr="";
		StringBuffer sbXml = new StringBuffer();
		
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		try{
			AlarmInfoData alarmInfoData = new AlarmInfoData();
			Object[][] res = alarmInfoData.getPcsCars(deptId);
			
			if(res!=null){
				for(int i=0;i<res.length;i++){
					sbXml.append("<row>\n");
					for(int  j=0;j<res[i].length;j++){
						sbXml.append("<col>\n");
						sbXml.append(StringHelper.obj2str(res[i][j],""));
						sbXml.append("</col>\n");
					}
					sbXml.append("</row>\n");
				}
			}				
			sbXml.append("</RFWin>\n");
			sbXml.append("</rfXML>\n");
			xmlstr = sbXml.toString();
			System.out.println(xmlstr);
		} catch (Exception e) {
			logger.error("[分控指挥调度]" + "生成当天PCS车辆信息XML错误");
			System.out.println(e.getMessage());
		}
		return xmlstr;
	}
	
	/**
	 * 生成当天勤务车辆信息
	 */
	public String setDutyCars(String deptId){
		String date = StringUtil.getCurrDateTime("yyyy-MM-dd");
		
		String xmlstr="";
		StringBuffer sbXml = new StringBuffer();
		Object[][] res=DutyFactory.getQueryDutyDao().getCarsByDate(date,deptId);
				
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		try{
			if(res!=null){
				for(int i=0;i<res.length;i++){
					sbXml.append("<row>\n");
					for(int  j=0;j<res[i].length;j++){
						sbXml.append("<col>\n");
						sbXml.append(StringHelper.obj2str(res[i][j],""));
						sbXml.append("</col>\n");
					}
					sbXml.append("</row>\n");
				}
			}				
			sbXml.append("</RFWin>\n");
			sbXml.append("</rfXML>\n");
			xmlstr = sbXml.toString();
			System.out.println(xmlstr);
		} catch (Exception e) {
			logger.error("[分控指挥调度]" + "生成当天勤务车辆信息XML错误");
			System.out.println(e.getMessage());
		}
		return xmlstr;
	}
	
	
	// public static void main(String[] args) {
	// setNewInfo();
	// }
}
