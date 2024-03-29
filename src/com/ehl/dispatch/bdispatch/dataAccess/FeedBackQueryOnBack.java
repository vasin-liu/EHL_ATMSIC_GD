package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
/**
 * @作者: ldq
 * @版本号：1.0
 * @函数说明：从反馈表,事件主表及相关子表中获取单条反馈记录信息
 * @参数：
 * @返回：
 * @创建日期：2009-04-09
 * @修改者：
 * @修改日期：
 */
public class FeedBackQueryOnBack {
	private static final Logger logger = Logger.getLogger(FeedBackQueryOnBack.class);

	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从反馈表中获取单条反馈记录信息
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static String getMesOnFeedBack(String FEEDBACKID,String eventType){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * 顺序：报警编号,案件结束时间,出动车辆,出警人数,
			 * 出警单位,出警人,事件状态
			 * 出警时间,到达现场时间
			 * 经济损失,轻伤人数,重伤人数,死亡人数,抓获人数,
			 * 涉案人数,救助人数,是否解决纠纷,是否查处治安案件,是否反馈终结
			 * 是否破获刑事案件,影响道路,波及范围,故障车单位,司机姓名,
			 */ 
			String strSql = "SELECT BACK.ALARMID,TO_CHAR(BACK.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME, BACK.COMEOUTCAR,BACK.COMEOUTCOUNT," +
					" F_GET_DEPT(BACK.COMEOUTUNIT) COMEOUTUNIT,BACK.COMEOUTPERSON,BACK.STATE," +
					" TO_CHAR(BACK.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(BACK.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),FEEDBACKDESC," +
					" BACK.ECONOMYLOSS,BACK.FLESHWOUNDPERSONCOUNT,BACK.GBHPERSONCOUNT,BACK.DEATHPERSONCOUNT,BACK.GRAPPLEPERSONCOUNT,"+
					" BACK.DEALWITHPERSONCOUNT,BACK.SALVATIONPERSONCOUNT,BACK.ISRESOLVEDISSENSION,BACK.ISCHECKORDERCASE,BACK.ISFEEDBACKEND,"+
					" BACK.ISUNCOVERCRIMINALCASE,BACK.AFFECTROAD,BACK.AFFECTEXTENT,BACK.EXCEPTIONCARUNIT,BACK.DRIVERNAME"+
					" FROM T_ATTEMPER_FEEDBACK BACK WHERE BACK.FEEDBACKID='" + FEEDBACKID + "'";
			System.out.println("feedback detail strsql="+strSql);
			Object[] feedback = DBHandler.getLineResult(strSql);
			
			if(feedback != null){
				strXml.append( "<row id='FeedBack'>\n");
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='cljg'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				
				strXml.append( "<col id='jjss'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='qsrs'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='zsrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='swrs'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='sars'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='zhrs'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
				strXml.append( "<col id='jzrs'>" + StringHelper.obj2str(feedback[16],"") + "</col>");
				strXml.append( "<col id='jjjf'>" + StringHelper.obj2str(feedback[17],"") + "</col>");
				strXml.append( "<col id='cczaaj'>" + StringHelper.obj2str(feedback[18],"") + "</col>");
				strXml.append( "<col id='fkzj'>" + StringHelper.obj2str(feedback[19],"") + "</col>");
				strXml.append( "<col id='phxsaj'>" + StringHelper.obj2str(feedback[20],"") + "</col>");
				strXml.append( "<col id='yxdl'>" + StringHelper.obj2str(feedback[21],"") + "</col>");
				strXml.append( "<col id='bjfw'>" + StringHelper.obj2str(feedback[22],"") + "</col>");
				strXml.append( "<col id='gzcdw_Exception'>" + StringHelper.obj2str(feedback[23],"") + "</col>");
				strXml.append( "<col id='sjxm_Exception'>" + StringHelper.obj2str(feedback[24],"") + "</col>");
				
				strXml.append( "</row>\n");
				System.out.println("strxmlaaaa=="+strXml);
			}else{
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("从反馈表中获取单条反馈记录信息数据库错误!");
		}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件主表获取事件相关信息
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static String getMesOnAlarm(String alarmId){StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * 顺序：案件类型,案发地址,事故等级,
			 * 是否与其他接警单归并,事件来源
			 */ 
			String strSql = "SELECT ALARM.EVENTTYPE,ALARM.ALARMADDRESS,ALARM.EVENTLEVEL," +
				" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE," +
				" ALARM.TITLE,to_char(ALARM.ALARMTIME,'yyyy-mm-dd hh24:mi')"+
				" FROM T_ATTEMPER_ALARM ALARM WHERE ALARM.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='Alarm'>\n");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='alarmState_feedBack'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='sgdj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='eventtile'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("从指挥调度事件主表获取事件相关信息数据库错误!");
		}
		
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件交通事故子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnAccident(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * 顺序： 案件开始时间
			 */ 
			String strSql = "SELECT TO_CHAR(ACCIDENT.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME " +
			" FROM T_ATTEMPER_ACCIDENT ACCIDENT WHERE ACCIDENT.ALARMID='" + alarmId + "'" ;
			Object feedback = DBHandler.getSingleResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='Accident'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback,"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("从指挥调度事件交通事故子表获取交通事故相关信息数据库错误!");
		}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件交通拥堵子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnCongestion(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/** 案件开始时间
			 */ 
			String strSql = "SELECT TO_CHAR(CONGESTION.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME" +
				" FROM T_ATTEMPER_CONGESTION CONGESTION WHERE CONGESTION.ALARMID='" 
				+ alarmId + "'" ;
			Object feedback = DBHandler.getSingleResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='Congestion'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback,"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("从指挥调度事件交通拥堵子表获取交通事故相关信息数据库错误!");
		}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件大型故障车子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnExceptionCar(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * 顺序：案件开始时间
		 */ 
		try{
			String sql = "SELECT TO_CHAR(EXCEPTIONCAR.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_EXCEPTIONCAR EXCEPTIONCAR WHERE EXCEPTIONCAR.ALARMID='" + alarmId + "'" ;
			Object feedback = DBHandler.getSingleResult(sql);
			if(feedback != null){
				strXml.append( "<row id='ExceptionCar'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback,"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("从指挥调度事件大型故障车子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件嫌疑布控车辆子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnBlackList(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * 顺序：救助人数
		 */ 
		try{
			String sql = "SELECT BLACKLIST.SALVATIONPERSONCOUNT" +
					" FROM T_ATTEMPER_BLACKLIST BLACKLIST WHERE BLACKLIST.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='BlackList'>\n");
				strXml.append( "<col id='jzrs_BlackList'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("从指挥调度事件嫌疑布控车辆子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件灾害恶劣天气子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnWeather(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * 顺序：路面状况、影响道路、
		 * 案件开始时间
		 */ 
		try{
			String sql = "SELECT WEATHER.ROADSITUATION,WEATHER.AFFECTROAD,TO_CHAR(WEATHER.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_WEATHER WEATHER WHERE WEATHER.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='Weather'>\n");
				strXml.append( "<col id='lmqk_Weather'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='yxdl_Weather'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("从指挥调度事件灾害恶劣天气子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件治安事件子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnPoliceEvent(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * 顺序：影响道路、波及范围、
		 * 案件开始时间
		 */ 
		try{
			String sql = "SELECT POLICEEVENT.AFFECTROAD,POLICEEVENT.AFFECTEXTENT," +
					" TO_CHAR(POLICEEVENT.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_POLICEEVENT POLICEEVENT WHERE POLICEEVENT.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='PoliceEvent'>\n");
				strXml.append( "<col id='yxdl_PoliceEvent'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='bjfw_PoliceEvent'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("从指挥调度事件治安事件子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件火灾、爆炸信息子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnFireAndBlast(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * 顺序：案件开始时间
			 * 经济损失,轻伤人数,重伤人数,
			 * 死亡人数,影响道路,
			 */ 
			String strSql = "SELECT TO_CHAR(FIREANDBLAST.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME," +
				" FIREANDBLAST.ECONOMYLOSS,FIREANDBLAST.FLESHWOUNDPERSONCOUNT,FIREANDBLAST.GBHPERSONCOUNT," +
				" FIREANDBLAST.DEATHPERSONCOUNT,FIREANDBLAST.AFFECTROAD" +
				" FROM T_ATTEMPER_FIREANDBLAST FIREANDBLAST WHERE FIREANDBLAST.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='FireAndBlast'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='jjss_FireAndBlast'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='qsrs_FireAndBlast'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='zsrs_FireAndBlast'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='swrs_FireAndBlast'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='yxdl_FireAndBlast'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("从指挥调度事件火灾、爆炸信息子表获取交通事故相关信息数据库错误!");
		}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件地质灾害子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnGeoLogicDisaster(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * 顺序：影响道路、波及范围、
		 * 案件开始时间
		 */ 
		try{
			String sql = "SELECT GEOLOGICDISASTER.AFFECTROAD,GEOLOGICDISASTER.AFFECTEXTENT," +
					" TO_CHAR(GEOLOGICDISASTER.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_GEOLOGICDISASTER GEOLOGICDISASTER WHERE GEOLOGICDISASTER.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='GeoLogicDisaster'>\n");
				strXml.append( "<col id='yxdl_GeoLogicDisaster'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='bjfw_GeoLogicDisaster'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("从指挥调度事件地质灾害子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从指挥调度事件市政事件子表获取交通事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnTownPlan(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * 顺序：影响道路、波及范围、
		 * 案件开始时间
		 */ 
		try{
			String sql = "SELECT TOWNPLAN.AFFECTROAD,TOWNPLAN.AFFECTEXTENT," +
					" TO_CHAR(TOWNPLAN.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_TOWNPLAN TOWNPLAN WHERE TOWNPLAN.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='TownPlan'>\n");
				strXml.append( "<col id='yxdl_TownPlan'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='bjfw_TownPlan'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(getClass().getName() + ":" + e.getMessage());
		logger.error("从指挥调度事件市政事件子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
}
