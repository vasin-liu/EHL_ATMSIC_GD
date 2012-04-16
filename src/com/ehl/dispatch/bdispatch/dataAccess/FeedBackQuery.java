package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;

public class FeedBackQuery {
	
	private static final Logger logger = Logger.getLogger(FeedBackQuery.class);
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从反馈表中获取历次反馈记录
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static String getEventListById(String alarmId){
		StringBuffer xmlData = new StringBuffer("");
		try {
			String strSql = " SELECT FEEDBACKID,ALARMID,F_GET_DEPT(COMEOUTUNIT) COMEOUTUNIT,COMEOUTPERSON," +
					" TO_CHAR(COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI') ,(select name from t_attemper_code where id=t.state),STATE" +
					",COMEOUTCAR,COMEOUTCOUNT"+
					" FROM T_ATTEMPER_FEEDBACK  t WHERE ALARMID='" + alarmId + "' order by COMEOUTTIME desc";
			Object[][] feedback = DBHandler.getMultiResult(strSql);
			
			if(feedback != null){
				for(int i=0;i<feedback.length;i++){
					xmlData.append( "<feedback id='"+ i +"'>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][0],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][1],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][2],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][3],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][4],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][5],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][6],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][7],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][8],"") + "</back>");
					xmlData.append( "<back>" + StringHelper.obj2str(feedback[i][9],"") + "</back>");
					xmlData.append( "</feedback>");
				}
			}
			System.out.println("xmlData=="+xmlData);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("从反馈表中获取历次反馈记录数据库错误!");
		}
		return xmlData.toString();
	}
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：当没有案件类型时从指挥调度事件主表获取事故相关信息
	 * @参数：alarmId 报警编号
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public String getMesOnAlarm(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * 顺序：报警编号,案件结束时间,
			 * 案件类型,案发地址,事故等级,
			 * 出动车辆,出警人数,
			 * 出警单位,出警人,事件状态
			 * 出警时间,到达现场时间,是否与其他接警单归并,事件来源
			 */ 
			String strSql = "SELECT ALARM.ALARMID,TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
				" ALARM.EVENTTYPE,ALARM.ALARMADDRESS,ALARM.EVENTLEVEL, " +
				" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT," +
				" F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON,ALARM.EVENTSTATE," +
				" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
				" FROM T_ATTEMPER_ALARM ALARM WHERE ALARM.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='alarmState_feedBack'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='sgdj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
			}
			strXml.append( "</row>\n");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("当没有案件类型时从指挥调度事件主表获取事故相关信息数据库错误!");
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
			 * 顺序：报警编号,案件开始时间,案件结束时间,
			 * 案件类型,案发地址,事故等级,
			 * 经济损失,轻伤人数,重伤人数,死亡人数,
			 * 涉案人数,抓获人数,救助人数,
			 * 出动车辆,出警人数,
			 * 是否破获刑事案件,是否查处治安案件,是否解决纠纷,是否反馈终结 
			 * 出警单位,出警人,事件状态
			 * 出警时间,到达现场时间,是否与其他接警单归并,事件来源
			 */ 
			String strSql = "SELECT ALARM.ALARMID,TO_CHAR(ACCIDENT.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME,TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
				" ALARM.EVENTTYPE,ALARM.ALARMADDRESS,ALARM.EVENTLEVEL, " +
				" ACCIDENT.ECONOMYLOSS,ACCIDENT.FLESHWOUNDPERSONCOUNT,ACCIDENT.GBHPERSONCOUNT,ACCIDENT.DEATHPERSONCOUNT," +
				" ACCIDENT.DEALWITHPERSONCOUNT,ACCIDENT.GRAPPLEPERSONCOUNT,ACCIDENT.SALVATIONPERSONCOUNT," +
				" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT," +
				" ACCIDENT.ISUNCOVERCRIMINALCASE,ACCIDENT.ISCHECKORDERCASE,ACCIDENT.ISRESOLVEDISSENSION,ACCIDENT.ISFEEDBACKEND," +
				" F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON,ALARM.EVENTSTATE," +
				" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
				" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_ACCIDENT ACCIDENT ON ALARM.ALARMID=ACCIDENT.ALARMID WHERE ALARM.ALARMID='" 
				+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='alarmState_feedBack'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sgdj'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='jjss'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='qsrs'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='zsrs'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='swrs'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='sars'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='zhrs'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='jzrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='phxsaj'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
				strXml.append( "<col id='cczaaj'>" + StringHelper.obj2str(feedback[16],"") + "</col>");
				strXml.append( "<col id='jjjf'>" + StringHelper.obj2str(feedback[17],"") + "</col>");
				
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[19],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[20],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[21],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[22],"") + "</col>");
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[23],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[24],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[25],"") + "</col>");
//				strXml.append( "<fkzj>" + feedback[18] + "</fkzj>";
				strXml.append( "<col id='fkzj'>" + StringHelper.obj2str(feedback[18],"") + "</col>");
			}
			strXml.append( "</row>\n");
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
			/**
			 * 顺序：报警编号,案件开始时间,案件结束时间,
			 * 案件类型,案发地址,事故等级,
			 * 经济损失,<轻伤人数,重伤人数>,死亡人数,
			 * <涉案人数,抓获人数,救助人数,>
			 * 出动车辆,出警人数,
			 * <是否破获刑事案件,是否查处治安案件,是否解决纠纷>,是否反馈终结 
			 * 出警单位,出警人,事件状态
			 * 出警时间,到达现场时间,是否与其他接警单归并,事件来源
			 */ 
			String strSql = "SELECT ALARM.ALARMID,TO_CHAR(CONGESTION.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME,TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
				" ALARM.EVENTTYPE,ALARM.ALARMADDRESS,ALARM.EVENTLEVEL, " +
				" CONGESTION.ECONOMYLOSS,CONGESTION.FLESHWOUNDPERSONCOUNT,CONGESTION.GBHPERSONCOUNT,CONGESTION.DEATHPERSONCOUNT," +
				" CONGESTION.DEALWITHPERSONCOUNT,CONGESTION.GRAPPLEPERSONCOUNT,CONGESTION.SALVATIONPERSONCOUNT," +
				" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT," +
				" CONGESTION.ISUNCOVERCRIMINALCASE,CONGESTION.ISCHECKORDERCASE,CONGESTION.ISRESOLVEDISSENSION,CONGESTION.ISFEEDBACKEND," +
				" F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON,ALARM.EVENTSTATE," +
				" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
				" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_CONGESTION CONGESTION ON ALARM.ALARMID=CONGESTION.ALARMID WHERE ALARM.ALARMID='" 
				+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='alarmState_feedBack'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sgdj'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='jjss'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='qsrs'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='zsrs'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='swrs'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='sars'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='zhrs'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='jzrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='phxsaj'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
				strXml.append( "<col id='cczaaj'>" + StringHelper.obj2str(feedback[16],"") + "</col>");
				strXml.append( "<col id='jjjf'>" + StringHelper.obj2str(feedback[17],"") + "</col>");
				
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[19],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[20],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[21],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[22],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[23],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[24],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[25],"") + "</col>");
//				strXml.append( "<fkzj>" + feedback[18] + "</fkzj>");
				strXml.append( "<col id='fkzj'>" + StringHelper.obj2str(feedback[18],"") + "</col>");
			}
			strXml.append( "</row>\n");
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
		 * 顺序：报警编号,出警单位,出警人,
		 * 出警时间,到达现场时间,事件来源
		 * 案件类型,司机姓名、故障车单位、
		 * 案件开始时间,案件结束时间,
		 * 出动车辆,出警人数,
		 * 是否与其他接警单归并,事件来源
		 */ 
		try{
			String sql = "SELECT ALARM.ALARMID,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON," +
					" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.EVENTSOURCE," +
					" ALARM.EVENTTYPE,EXCEPTIONCAR.DRIVERNAME,EXCEPTIONCAR.EXCEPTIONCARUNIT," +
					" TO_CHAR(EXCEPTIONCAR.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
					" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,ALARM.EVENTSTATE," +
					" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
					" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_EXCEPTIONCAR EXCEPTIONCAR ON ALARM.ALARMID=EXCEPTIONCAR.ALARMID WHERE ALARM.ALARMID='" 
					+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
//			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[3],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='sjxm_Exception'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='gzcdw_Exception'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
			}
//		strXml.append( "</row>\n");
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
		 * 顺序：报警编号,出警单位,出警人,
		 * 出警时间,到达现场时间,事件来源
		 * 案件类型, 案件结束时间,
		 * 出动车辆,出警人数
		 * 是否与其他接警单归并,事件来源,救助人数
		 */ 
		try{
			String sql = "SELECT ALARM.ALARMID,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON," +
					" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.EVENTSOURCE," +
					" ALARM.EVENTTYPE, TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
					" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,ALARM.EVENTSTATE," +
					" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE,BLACKLIST.SALVATIONPERSONCOUNT" +
					" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_BLACKLIST BLACKLIST ON ALARM.ALARMID=BLACKLIST.ALARMID WHERE ALARM.ALARMID='" 
					+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
//			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[3],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
//				strXml.append( "<col id='sjxm_Exception'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
//				strXml.append( "<col id='gzcdw_Exception'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
//				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='jzrs_BlackList'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
			}
//		strXml.append( "</row>\n");
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
		 * 顺序：报警编号,出警单位,出警人,
		 * 出警时间,到达现场时间,事件来源
		 * 案件类型,路面状况、影响道路、
		 * 案件开始时间,案件结束时间,
		 * 出动车辆,出警人数
		 * 是否与其他接警单归并,事件来源
		 */ 
		try{
			String sql = "SELECT ALARM.ALARMID,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON," +
					" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.EVENTSOURCE," +
					" ALARM.EVENTTYPE,WEATHER.ROADSITUATION,WEATHER.AFFECTROAD," +
					" TO_CHAR(WEATHER.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
					" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,ALARM.EVENTSTATE," +
					" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
					" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_WEATHER WEATHER ON ALARM.ALARMID=WEATHER.ALARMID WHERE ALARM.ALARMID='" 
					+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
//			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[3],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='lmqk_Weather'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='yxdl_Weather'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
			}
//		strXml.append( "</row>\n");
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
		 * 顺序：报警编号,出警单位,出警人,
		 * 出警时间,到达现场时间,事件来源
		 * 案件类型,影响道路、波及范围、
		 * 案件开始时间,案件结束时间,
		 * 出动车辆,出警人数
		 * 是否与其他接警单归并,事件来源
		 */ 
		try{
			String sql = "SELECT ALARM.ALARMID,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON," +
					" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.EVENTSOURCE," +
					" ALARM.EVENTTYPE,POLICEEVENT.AFFECTROAD,POLICEEVENT.AFFECTEXTENT," +
					" TO_CHAR(POLICEEVENT.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
					" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,ALARM.EVENTSTATE," +
					" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
					" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_POLICEEVENT POLICEEVENT ON ALARM.ALARMID=POLICEEVENT.ALARMID WHERE ALARM.ALARMID='" 
					+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
//			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[3],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='yxdl_PoliceEvent'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='bjfw_PoliceEvent'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
			}
//		strXml.append( "</row>\n");
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
			 * 顺序：报警编号,案件开始时间,案件结束时间,
			 * 案件类型,经济损失,轻伤人数,重伤人数,
			 * 死亡人数,出动车辆,出警人数,出警单位,
			 * 出警人,事件状态,出警时间,到达现场时间,
			 * 影响道路,是否与其他接警单归并,事件来源
			 */ 
			String strSql = "SELECT ALARM.ALARMID,TO_CHAR(FIREANDBLAST.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME,TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
				" ALARM.EVENTTYPE,FIREANDBLAST.ECONOMYLOSS,FIREANDBLAST.FLESHWOUNDPERSONCOUNT,FIREANDBLAST.GBHPERSONCOUNT," +
				" FIREANDBLAST.DEATHPERSONCOUNT,ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT," +
				" ALARM.COMEOUTPERSON,ALARM.EVENTSTATE,TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI')," +
				" FIREANDBLAST.AFFECTROAD,ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
				" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_FIREANDBLAST FIREANDBLAST ON ALARM.ALARMID=FIREANDBLAST.ALARMID WHERE ALARM.ALARMID='" 
				+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='jjss_FireAndBlast'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='qsrs_FireAndBlast'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='zsrs_FireAndBlast'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='swrs_FireAndBlast'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='yxdl_FireAndBlast'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[16],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[17],"") + "</col>");
			}
			strXml.append( "</row>\n");
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
		 * 顺序：报警编号,出警单位,出警人,
		 * 出警时间,到达现场时间,事件来源
		 * 案件类型,影响道路、波及范围、
		 * 案件开始时间,案件结束时间,
		 * 出动车辆,出警人数
		 * 是否与其他接警单归并,事件来源
		 */ 
		try{
			String sql = "SELECT ALARM.ALARMID,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON," +
					" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.EVENTSOURCE," +
					" ALARM.EVENTTYPE,GEOLOGICDISASTER.AFFECTROAD,GEOLOGICDISASTER.AFFECTEXTENT," +
					" TO_CHAR(GEOLOGICDISASTER.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
					" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,ALARM.EVENTSTATE," +
					" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE" +
					" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_GEOLOGICDISASTER GEOLOGICDISASTER ON ALARM.ALARMID=GEOLOGICDISASTER.ALARMID WHERE ALARM.ALARMID='" 
					+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
//			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[3],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='yxdl_GeoLogicDisaster'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='bjfw_GeoLogicDisaster'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
				
			}
//		strXml.append( "</row>\n");
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
		 * 顺序：报警编号,出警单位,出警人,
		 * 出警时间,到达现场时间,事件来源
		 * 案件类型,影响道路、波及范围、
		 * 案件开始时间,案件结束时间,
		 * 出动车辆,出警人数
		 * 是否与其他接警单归并,事件来源
		 */ 
		try{
			String sql = "SELECT ALARM.ALARMID,F_GET_DEPT(ALARM.COMEOUTUNIT) COMEOUTUNIT,ALARM.COMEOUTPERSON," +
					" TO_CHAR(ALARM.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),ALARM.EVENTSOURCE," +
					" ALARM.EVENTTYPE,TOWNPLAN.AFFECTROAD,TOWNPLAN.AFFECTEXTENT," +
					" TO_CHAR(TOWNPLAN.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(ALARM.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME," +
					" ALARM.COMEOUTCAR,ALARM.COMEOUTCOUNT,ALARM.EVENTSTATE" +
					" FROM T_ATTEMPER_ALARM ALARM INNER JOIN T_ATTEMPER_TOWNPLAN TOWNPLAN ON ALARM.ALARMID=TOWNPLAN.ALARMID WHERE ALARM.ALARMID='" 
					+ alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
//			strXml.append( "<row id='0'>\n");
			if(feedback != null){
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[3],"") + "</col>");//
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='yxdl_TownPlan'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='bjfw_TownPlan'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
			}
//		strXml.append( "</row>\n");
	} catch (Exception e) {
		System.err.println(getClass().getName() + ":" + e.getMessage());
		logger.error("从指挥调度事件市政事件子表获取交通事故相关信息数据库错误!");
	}
		return strXml.toString();
	}
}
