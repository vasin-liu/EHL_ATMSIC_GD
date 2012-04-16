package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;

public class CompleAlarmDate {

	private static final Logger logger = Logger.getLogger(CompleAlarmDate.class);
	/**
	 * 插入数据事务操作
	 */
	private boolean executeData(String [] sqls) throws Throwable {
		boolean msg = false;
		Database db = null;
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			for(int i=0;i<sqls.length;i++){
				System.out.println(sqls[i]);
				int in=db.executeUpdate(sqls[i]);
				if(in<0){
					msg = false;
					break;
				}else{
					msg = true;
				}
			}			
			if (msg) {
				db.commitTrans(false);				
			} else {
				db.commitTrans(true);
			}
		} catch (Exception e) {
			logger.error("插入数据事务操作错误!");
			throw new RuntimeException();
			
		} finally {
			DBFactory.closeDatabase(db);
		}

		return msg;
	}
	
	
	private String insertAlarmInfo(Object[] res) {
		String inalarmsql = "";
				
		inalarmsql += "insert into T_Attemper_Alarm(AlarmID,AlarmTime,AlarmAddress,AlarmUnit,EventSource,EventType,EventLevel,EventThinType,EventState,AlarmDesc,x,y,RoadID,RoadName,DISPOSETIME,DISPOSEPERSON,DISPOSEUNIT,DISPOSEIDEA,title,REPORTTIME,REPORTPERSON,REPORTUNIT,SUBEVENTSOURCE" 
			+",COMEOUTUNIT,COMEOUTTIME,COMEOUTARRIVETIME,COMEOUTPERSON,FINISHTIME,COMEOUTCOUNT,COMEOUTCAR)";
		inalarmsql += " values('" + res[0] + "',to_date('" + res[1]
				+ "','yyyy-mm-dd hh24:mi'),'" + res[2] + "','" + res[3] + "','"
				+ res[4] + "','" + res[5] + "','" + res[6] + "','" + res[7]
				+ "','" + res[8] + "','" + res[9] + "','" + res[10] + "','"
				+ res[11] + "','" + res[12] + "','" + res[13] + "',to_date('"
				+ res[14] + "','yyyy-mm-dd hh24:mi'),'" + res[15] + "','"
				+ res[16] + "','" + res[17] + "','" + res[19] + "',to_date('"
				+ res[20] + "','yyyy-mm-dd hh24:mi'),'" + res[21] + "','" + res[22] + "','"+res[23]+"'" 
				+",'"+res[27]+"',to_date('"+ res[28] + "','yyyy-mm-dd hh24:mi'),to_date('"+ res[29] + "','yyyy-mm-dd hh24:mi'),"
				+"'"+res[30]+"',to_date('"+ res[31] + "','yyyy-mm-dd hh24:mi'),'"+res[32]+"','"+res[33]+"')";
		return inalarmsql;
	}
	

	private String insertFeedBackInfo(Object[] res) {
		String inFeedBacksql = "";
		String cols="FEEDBACKID,ALARMID,FEEDBACKTIME,FEEDBACKUNIT,FEEDBACKPERSON,FEEDBACKDESC";		
		inFeedBacksql += "insert into T_ATTEMPER_FEEDBACK("+cols+")";
		inFeedBacksql += " values('" + res[0] + "','" + res[0] + "',to_date('" + res[24]
				+ "','yyyy-mm-dd hh24:mi'),'" + res[25] + "','" + res[26] + "','"
				+ res[34] + "')";
		return inFeedBacksql;
	}
	
	/**
	 * 新增一条事故报警信息
	 */
	public boolean insertAccidentInfo(Object[] res) throws Throwable {
		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res);
		
		String cols="CASEHAPPENTIME,ALARMCARTYPE,ALARMCARNUMBER,ALARMCARGENRE,REMARK,AFFECTLEVEL,ECONOMYLOSS,";
		cols+="FLESHWOUNDPERSONCOUNT,GBHPERSONCOUNT,DEATHPERSONCOUNT,DEALWITHPERSONCOUNT,GRAPPLEPERSONCOUNT,SALVATIONPERSONCOUNT,";
		cols+="ISUNCOVERCRIMINALCASE,ISCHECKORDERCASE,ISRESOLVEDISSENSION";
		
		String accidentsql ="insert into t_attemper_accident(ALARMID,"+cols+")";		
		accidentsql += " values('" + res[0] + "',to_date('" + res[37]+ "','yyyy-mm-dd hh24:mi'),";
		
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				accidentsql += "'" + res[i] + "',";
			} else {
				accidentsql += "'" + res[i] + "'";
			}
		}
		accidentsql += ")";
	
		System.out.println(accidentsql);
		sqlres[2]=accidentsql;
		
		return executeData(sqlres);
	}
	
	
	public boolean insertCongestionInfo(Object[] res) throws Throwable {
		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res);
		
		String cols="CASEHAPPENTIME,ALARMCARTYPE,ALARMCARNUMBER,ALARMCARGENRE,REMARK,AFFECTLEVEL,ECONOMYLOSS,";
		cols+="FLESHWOUNDPERSONCOUNT,GBHPERSONCOUNT,DEATHPERSONCOUNT,DEALWITHPERSONCOUNT,GRAPPLEPERSONCOUNT,SALVATIONPERSONCOUNT,";
		cols+="ISUNCOVERCRIMINALCASE,ISCHECKORDERCASE,ISRESOLVEDISSENSION";
		
		String accidentsql ="insert into T_ATTEMPER_CONGESTION(ALARMID,"+cols+")";		
		accidentsql += " values('" + res[0] + "',to_date('" + res[37]+ "','yyyy-mm-dd hh24:mi'),";
		
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				accidentsql += "'" + res[i] + "',";
			} else {
				accidentsql += "'" + res[i] + "'";
			}
		}
		accidentsql += ")";
	
		System.out.println(accidentsql);
		sqlres[2]=accidentsql;
		
		return executeData(sqlres);
	}
	
	
	/**
	 * 新增一条恶劣天气信息
	 */
	public boolean insertBadWeatherInfo(Object[] res) throws Throwable {

		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res);
		
		// 插入子表
		String sonsql = "insert into T_ATTEMPER_WEATHER(ALARMID,CASEHAPPENTIME,VILEWEATHERSITUATION,ROADSITUATION,AFFECTROAD,AFFECTREGION,ALARMPHONE,ALARMPERSON,AFFECTEXTENT,REMARK)";
		sonsql += " values('" + res[0] + "',to_date('" + res[37]+ "','yyyy-mm-dd hh24:mi'),";
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		sqlres[2]=sonsql;
		System.out.println(sonsql);
		return executeData(sqlres);
	}
	
	
	/**
	 * 新增一条治安信息
	 */
	public boolean insertPoliceEventInfo(Object[] res) throws Throwable {

		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res); 
		// 插入子表
		String sonsql = "insert into T_ATTEMPER_POLICEEVENT(ALARMID,AFFECTTRAFFICLEVEL,AFFECTROAD,REMARK,AFFECTEXTENT,CASEHAPPENTIME)";
		sonsql += " values('" + res[0] + "',";
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "to_date('" + res[i] + "','yyyy-mm-dd hh24:mi')";
			}
		}
		sonsql += ")";
		sqlres[2]=sonsql;
		System.out.println(sonsql);
		return executeData(sqlres);
	}
	
	/**
	 * 新增一条故障车信息
	 */
	public boolean insertExceptionCarInfo(Object[] res) throws Throwable {

		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res); 
		// 插入子表
		String exceptionsql = "insert into T_Attemper_ExceptionCar(ALARMID,CASEHAPPENTIME,EXCEPTIONCARADDRESS,EXCEPTIONCAUSE,CARSHAPE,AFFECTLEVEL,REMARK,DRIVERNAME,EXCEPTIONCARUNIT)";
		exceptionsql += " values('" + res[0] + "',to_date('" + res[37]+ "','yyyy-mm-dd hh24:mi'),";
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				exceptionsql += "'" + res[i] + "',";
			} else {
				exceptionsql += "'" + res[i] + "'";
			}
		}
		exceptionsql += ")";
		sqlres[2]=exceptionsql;
		System.out.println(exceptionsql);
		return executeData(sqlres);

	}
	
	/**
	 * 新增一条地质信息
	 */
	public boolean insertGeoLogicDisasterInfo(Object[] res) throws Throwable {
		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res); 
		// 插入子表
		String sonsql = "insert into T_ATTEMPER_GEOLOGICDISASTER(ALARMID,CASEHAPPENTIME,AFFECTROAD,AFFECTTRAFFICLEVEL,AFFECTEXTENT,ALARMPERSON,REMARK)";
		sonsql += " values('" + res[0] + "',";
		sonsql += "to_date('" + res[37] + "','yyyy-mm-dd hh24:mi'),";
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		sqlres[2]=sonsql;
		System.out.println(sonsql);
		return executeData(sqlres);

	}
	
	
	/**
	 * 新增一条市政信息
	 */
	public boolean insertTownplanInfo(Object[] res) throws Throwable {

		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res); 
		
		// 插入子表
		String sonsql = "insert into T_ATTEMPER_TOWNPLAN(ALARMID,CASEHAPPENTIME,ALARMPERSON,AFFECTROAD,AFFECTEXTENT,AFFECTTRAFFICLEVEL,REMARK)";
		sonsql += " values('" + res[0] + "',";
		sonsql += "to_date('" + res[37] + "','yyyy-mm-dd hh24:mi'),";
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);
		sqlres[2]=sonsql;
		System.out.println(sonsql);
		return executeData(sqlres);
	}
	
	/**
	 * 新增一条火灾信息
	 */
	public boolean insertTFireAndBlastInfo(Object[] res) throws Throwable {

		String sqlres[]=new String[3];
		sqlres[0]=insertAlarmInfo(res);
		sqlres[1]=insertFeedBackInfo(res); 
		// 插入子表
		String sonsql = "insert into T_ATTEMPER_FIREANDBLAST(ALARMID,CASEHAPPENTIME,AFFECTTRAFFICLEVEL,HAVEORNONEHARTLOSEPERSON,ALARMPERSON,REMARK,AFFECTROAD,FLESHWOUNDPERSONCOUNT,GBHPERSONCOUNT,DEATHPERSONCOUNT,ECONOMYLOSS)";
		sonsql += " values('" + res[0] + "',";
		sonsql += "to_date('" + res[37] + "','yyyy-mm-dd hh24:mi'),";
		for (int i = 38; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		sqlres[2]=sonsql;
		System.out.println(sonsql);
		return executeData(sqlres);
	}

}
