package com.ehl.dispatch.bdispatch.dataAccess;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.LineUtil;

/**
 * �¹���Ϣ�༭���ݲ�����
 * author��jc
 */
public class AlarmInfoData {

	private String roadLayerName = "csdlzxx_pl";

	private SDEAccess sde = new SDEAccess(); // ����SDE

	/**
	 * ��������λ�ø����ĵ�·
	 */
	public Object[][] getLineByMouse(String point) throws Throwable {

		Object[][] lines = null;
		String strFields = "DLBH,DLMC,SHAPE";

		try {
			LineUtil lineUtil = new LineUtil(sde.initConnection(),
					roadLayerName);
			lines = lineUtil.getFieldsByPosition(point, "30", strFields);
		} catch (Exception ex) {
			throw new RuntimeException();
		} finally {
			sde.closeAO();
		}

		return lines;
	}
	
	/**
	 * @�༭ lidongqiang
	 * @desc �������ݵ���������
	 * @param res ���뵽��������ݼ�
	 * @return 1
	 */
	private String insertAlarmInfo(Object[] res) {
		String inalarmsql = "";
		String deptid = res[3].toString();
		String SUPERUNIT = deptid.substring(0, 6) + StringUtil.fill("0", deptid.length()-6); //��������
		// String title=res[1]+res[13]+res[19]+res[18];
		//add by wangxt 090515 ����һ��SUBEVENTSOURCE�ֶ�
		
		//add by LDQ 091216 ����[ALARMREGION-����Ͻ��],[ALARMREGIONID-����Ͻ��id],[SUPERUNIT-������λ����Ӧ�¼��ϱ�����(�����֧��)��������֧��id]�ֶ�
		inalarmsql += "insert into T_Attemper_Alarm" +
				"(AlarmID,AlarmTime,AlarmAddress,AlarmUnit,EventSource,EventType,EventLevel,EventThinType,EventState,AlarmDesc,x,y,RoadID,RoadName,DISPOSETIME,DISPOSEPERSON,DISPOSEUNIT,DISPOSEIDEA,title,REPORTTIME,REPORTPERSON,REPORTUNIT,SUBEVENTSOURCE,ALARMREGION,ALARMREGIONID,SUPERUNIT)";
		inalarmsql += " values('" + res[0] + "',to_date('" + res[1]
				+ "','yyyy-mm-dd hh24:mi'),'" + res[2] + "','" + res[3] + "','"
				+ res[4] + "','" + res[5] + "','" + res[6] + "','" + res[7]
				+ "','" + res[8] + "','" + res[9] + "','" + res[10] + "','"
				+ res[11] + "','" + res[12] + "','" + res[13] + "',to_date('"
				+ res[14] + "','yyyy-mm-dd hh24:mi'),'" + res[15] + "','"
				+ res[16] + "','" + res[17] + "','" + res[19] + "',to_date('"
				+ res[20] + "','yyyy-mm-dd hh24:mi'),'" + res[21] + "','" + res[22] + "','"+res[23]+"','" + res[22] + "','" + res[3] + "','" + SUPERUNIT + "')";
		System.out.println("inalarmsql==="+inalarmsql);
		return inalarmsql;
	}

	/**
	 * ����һ���¹ʱ�����Ϣ
	 */
	public boolean insertAccidentInfo(Object[] res) throws Throwable {

		String inalarmsql = insertAlarmInfo(res);

		// �����¹��ӱ�
		String accidentsql = "insert into t_attemper_accident(ALARMID,ALARMCARTYPE,ALARMCARNUMBER,ALARMCARGENRE,REMARK,AFFECTLEVEL)";
		accidentsql += " values('" + res[0] + "',";
		for (int i = 24; i < res.length; i++) {
			if (i != res.length - 1) {
				accidentsql += "'" + res[i] + "',";
			} else {
				accidentsql += "'" + res[i] + "'";
			}
		}
		accidentsql += ")";
		// accidentsql += " values('" + res[0] + "','" + res[15] + "','"
		// + res[16] + "','" + res[17] + "','" + res[18] + "','"
		// + res[19] + "','" + res[20] + "','" + res[21] + "','"
		// + res[22] + "','" + res[23] + "',";
		// accidentsql += "'" + res[24] + "','" + res[25] + "','" + res[26]
		// + "','" + res[27] + "')";
		System.out.println(accidentsql);

		return executeData(inalarmsql, accidentsql);
	}

	/**
	 * ����һ��ӵ�±�����Ϣ
	 */
	public boolean insertCongestionInfo(Object[] res) throws Throwable {

		String inalarmsql = insertAlarmInfo(res);
		String congestionsql = "insert into t_attemper_congestion(ALARMID,AFFECTLEVEL,REMARK)";

		congestionsql += " values('" + res[0] + "',";
		for (int i = 24; i < res.length; i++) {
			if (i != res.length - 1) {
				congestionsql += "'" + res[i] + "',";
			} else {
				congestionsql += "'" + res[i] + "'";
			}
		}
		congestionsql += ")";

		System.out.println(congestionsql);

		return executeData(inalarmsql, congestionsql);
	}

	/**
	 * ����һ�����ϳ���Ϣ
	 */
	public boolean insertExceptionCarInfo(Object[] res) throws Throwable {

		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String exceptionsql = "insert into T_Attemper_ExceptionCar(ALARMID,EXCEPTIONCARADDRESS,EXCEPTIONCAUSE,CARSHAPE,AFFECTLEVEL,REMARK)";
		exceptionsql += " values('" + res[0] + "',";
		for (int i = 24; i < res.length; i++) {
			if (i != res.length - 1) {
				exceptionsql += "'" + res[i] + "',";
			} else {
				exceptionsql += "'" + res[i] + "'";
			}
		}
		exceptionsql += ")";

		System.out.println(exceptionsql);
		return executeData(inalarmsql, exceptionsql);

	}

	/**
	 * ����һ���ΰ���Ϣ
	 */
	public boolean insertPoliceEventInfo(Object[] res) throws Throwable {

		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String sonsql = "insert into T_ATTEMPER_POLICEEVENT(ALARMID,AFFECTTRAFFICLEVEL,AFFECTROAD,REMARK,CASEHAPPENTIME)";
		sonsql += " values('" + res[0] + "',";
		for (int i = 24; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "to_date('" + res[i] + "','yyyy-mm-dd hh24:mi')";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);

		return executeData(inalarmsql, sonsql);
	}

	/**
	 * ����һ������������Ϣ
	 */
	public boolean insertBadWeatherInfo(Object[] res) throws Throwable {

		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String sonsql = "insert into T_ATTEMPER_WEATHER(ALARMID,VILEWEATHERSITUATION,ROADSITUATION,AFFECTROAD,AFFECTREGION,ALARMPHONE,ALARMPERSON,AFFECTEXTENT,REMARK)";
		sonsql += " values('" + res[0] + "',";
		for (int i = 24; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);
		return executeData(inalarmsql, sonsql);
	}

	/**
	 * ����һ��������Ϣ
	 */
	public boolean insertBlackListInfo(Object[] res) throws Throwable {

		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String sonsql = "insert into T_Attemper_BlackList(ALARMID,CARNUMBER,CARSORT,REMARK)";
		// sonsql += " values('" + res[0] + "','" + res[15] + "','"
		// + res[16] + "','" + res[17] + "')";
		sonsql += " values('" + res[0] + "',";
		for (int i = 24; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);

		return executeData(inalarmsql, sonsql);

	}

	/**
	 * ����һ��������Ϣ
	 */
	public boolean insertTownplanInfo(Object[] res) throws Throwable {

		// ����ָ�ӵ����¼���¼��
		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String sonsql = "insert into T_ATTEMPER_TOWNPLAN(ALARMID,CASEHAPPENTIME,ALARMPERSON,AFFECTROAD,AFFECTEXTENT,AFFECTTRAFFICLEVEL,REMARK)";
		sonsql += " values('" + res[0] + "',";
		sonsql += "to_date('" + res[24] + "','yyyy-mm-dd hh24:mi'),";
		for (int i = 25; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);

		return executeData(inalarmsql, sonsql);
	}

	/**
	 * ����һ��������Ϣ
	 */
	public boolean insertTFireAndBlastInfo(Object[] res) throws Throwable {

		// ����ָ�ӵ����¼���¼��
		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String sonsql = "insert into T_ATTEMPER_FIREANDBLAST(ALARMID,CASEHAPPENTIME,AFFECTTRAFFICLEVEL,HAVEORNONEHARTLOSEPERSON,ALARMPERSON,REMARK)";
		sonsql += " values('" + res[0] + "',";
		sonsql += "to_date('" + res[24] + "','yyyy-mm-dd hh24:mi'),";
		for (int i = 25; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);

		return executeData(inalarmsql, sonsql);
	}

	/**
	 * ����һ��������Ϣ
	 */
	public boolean insertGeoLogicDisasterInfo(Object[] res) throws Throwable {

		// ����ָ�ӵ����¼���¼��
		String inalarmsql = insertAlarmInfo(res);
		// �����ӱ�
		String sonsql = "insert into T_ATTEMPER_GEOLOGICDISASTER(ALARMID,CASEHAPPENTIME,AFFECTTRAFFICLEVEL,AFFECTEXTENT,ALARMPERSON,REMARK)";
		sonsql += " values('" + res[0] + "',";
		sonsql += "to_date('" + res[24] + "','yyyy-mm-dd hh24:mi'),";
		for (int i = 25; i < res.length; i++) {
			if (i != res.length - 1) {
				sonsql += "'" + res[i] + "',";
			} else {
				sonsql += "'" + res[i] + "'";
			}
		}
		sonsql += ")";
		System.out.println(sonsql);
		return executeData(inalarmsql, sonsql);

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
	 * �õ��ӵ��б�
	 */
	public Object[][] getSonListById(String alarmId) {
		String sql = "select AlarmID,to_char(AlarmTime,'hh24:mi'),(select name from t_attemper_code where id=t.eventsource),(select name from t_attemper_code where id=t.eventtype),eventsource, eventtype from t_attemper_alarm t where mainalarmid='"
				+ alarmId + "'";
		System.out.println(sql);
		Object[][] infos = DBHandler.getMultiResult(sql);
		return infos;
	}

	public boolean updateAlarmState(String alarmid, String state) {
		String sql = "update T_Attemper_Alarm set EventState='" + state
				+ "' where alarmid='" + alarmid + "'";
		return DBHandler.execute(sql);
	}

	/**
	 * ���ݱ���ID��ñ�����Ϣ
	 */
	public Object[] getAlarmInfoByAlarmId(String alarmId) {

		String sql = "select AlarmID,to_char(AlarmTime,'yyyy-mm-dd hh24:mi'),AlarmAddress,AlarmUnit,";
		sql += "(select name from t_attemper_code where id=t.EventSource),(select name from t_attemper_code where id=t.EventType),";
		sql += "(select name from t_attemper_code where id=t.EventLevel),EventThinType,";
		sql += "(select name from t_attemper_code where id=t.EventState),AlarmDesc,x,y,RoadID,RoadName,f_get_dept(AlarmUnit),EventType,EventState,to_char(DISPOSETIME,'yyyy-mm-dd hh24:mi'),DISPOSEPERSON,f_get_dept(DISPOSEUNIT),DISPOSEIDEA ,";
		sql += "(select name from t_attemper_code where id=t.SUBEVENTSOURCE)";
		sql += " from T_Attemper_Alarm t where alarmid='" + alarmId + "'";
		System.out.println("�����ѯ=="+sql);
		Object[] alarms = DBHandler.getLineResult(sql);

		return alarms;
	}

	/**
	 * ϸ��
	 */
	public String getEventThinTypeName(Object id) {
		String value = "";
		String sql = "select name from t_attemper_code where id=" + id + "";
		value = StringHelper.obj2str(DBHandler.getSingleResult(sql), "");
		return value;
	}

	/**
	 * ���ݱ���ID����¹���Ϣ
	 */
	public Object[] getAccidentInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,ALARMPERSON,AlarmUnit,ALARMPHONE,PHONENAME,PHONEADDRESS,ALARMPERSONSEX,ALARMPERSONADDRESS,f_get_name('011001',ALARMCARTYPE),ALARMCARNUMBER,FLESHWOUNDPERSONCOUNT,DEATHPERSONCOUNT,"
				+ "ISDANAGERCAR,REMARK,GBHPERSONCOUNT,GRAPPLEPERSONCOUNT,DEALWITHPERSONCOUNT,SALVATIONPERSONCOUNT,"
				+ "ECONOMYLOSS,ISUNCOVERCRIMINALCASE,ISCHECKORDERCASE,ISRESOLVEDISSENSION,ISFEEDBACKEND,f_get_name('011008',ALARMCARGENRE),ALARMMANNER,AFFECTLEVEL"
				+ " from t_attemper_accident where alarmid='" + alarmId + "'";
		System.out.println(sql);
		Object[] accidents = DBHandler.getLineResult(sql);
		return accidents;
	}

	/**
	 * ���ݱ���ID���ӵ����Ϣ
	 */
	public Object[] getCongestionInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,ALARMPERSON,AlarmUnit,ALARMPHONE,PHONENAME,PHONEADDRESS,ALARMPERSONSEX,ALARMPERSONADDRESS,f_get_name('011001',ALARMCARTYPE),";
		sql += "ALARMCARNUMBER,BRUISEPERSONCOUNT,DEATHPERSONCOUNT,ISDANAGERCAR,AFFECTLEVEL,REMARK,ECONOMYLOSS,ISFEEDBACKEND ";
		sql += "from T_ATTEMPER_CONGESTION where alarmid='" + alarmId + "'";
		System.out.println(sql);
		Object[] congestions = DBHandler.getLineResult(sql);
		return congestions;
	}

	/**
	 * ���ݱ���ID��ù��ϳ���Ϣ
	 */
	public Object[] getExceptionCarInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,EXCEPTIONCARADDRESS,EXCEPTIONCAUSE,f_get_name('011008',CARSHAPE),DRIVERNAME,EXCEPTIONCARUNIT,AFFECTLEVEL,REMARK from T_Attemper_ExceptionCar where alarmid='"
				+ alarmId + "'";
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ���ݱ���ID����ΰ���Ϣ
	 */
	public Object[] getPoliceEventInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,AFFECTTRAFFICLEVEL,AFFECTROAD,REMARK,to_char(CASEHAPPENTIME,'yyyy-mm-dd hh24:mi') from T_ATTEMPER_POLICEEVENT where alarmid='"
				+ alarmId + "'";
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ���ݱ���ID��ö���������Ϣ
	 */
	public Object[] getBadWeatherInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,VILEWEATHERSITUATION,ROADSITUATION,AFFECTROAD,AFFECTREGION,ALARMPHONE,ALARMPERSON,AFFECTEXTENT,REMARK from T_ATTEMPER_WEATHER where alarmid='"
				+ alarmId + "'";
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ���ݱ���ID���������Ϣ
	 */
	public Object[] getTownplanInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,to_char(CASEHAPPENTIME,'yyyy-mm-dd hh24:mi'),ALARMPERSON,AFFECTROAD,AFFECTEXTENT,AFFECTTRAFFICLEVEL,REMARK from T_ATTEMPER_TOWNPLAN where alarmid='"
				+ alarmId + "'";
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ���ݱ���ID��û�����Ϣ
	 */
	public Object[] getFireAndBlastInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,to_char(CASEHAPPENTIME,'yyyy-mm-dd hh24:mi'),AFFECTTRAFFICLEVEL,HAVEORNONEHARTLOSEPERSON,ALARMPERSON,REMARK from T_ATTEMPER_FIREANDBLAST where alarmid='"
				+ alarmId + "'";
		System.out.println(sql);
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ���ݱ���ID��õ�����Ϣ
	 */
	public Object[] getGeoLogicDisasterInfoByAlarmId(String alarmId) {
		// ALARMID,CASEHAPPENTIME,AFFECTTRAFFICLEVEL,AFFECTEXTENT,ALARMPERSON,REMARK
		String sql = "select ALARMID,to_char(CASEHAPPENTIME,'yyyy-mm-dd hh24:mi'),AFFECTTRAFFICLEVEL,AFFECTEXTENT,ALARMPERSON,REMARK from T_ATTEMPER_GEOLOGICDISASTER where alarmid='"
				+ alarmId + "'";
		System.out.println(sql);
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ���ݱ���ID��ò�����Ϣ
	 */
	public Object[] getBlackListInfoByAlarmId(String alarmId) {
		String sql = "select ALARMID,CARNUMBER,f_get_name('011001',CARSORT),REMARK from T_ATTEMPER_BLACKLIST where alarmid='"
				+ alarmId + "'";
		Object[] infos = DBHandler.getLineResult(sql);
		return infos;
	}

	/**
	 * ��ȡ��Ա��Ϣ
	 */
	public Object[][] getPcsPersons(String deptId){
		String sql="select ryid,xm,f_get_dept(ssjg) from t_sys_person where ssjg='"+deptId+"'";
		Object[][] res=DBHandler.getMultiResult(sql);
		return res;
	}
	
	/**
	 * ��ȡ������Ϣ
	 */
	public Object[][] getPcsCars(String deptId){
		String sql="select clbm,hphm from t_sys_car where ssjg='"+deptId+"'";
		Object[][] res=DBHandler.getMultiResult(sql);
		return res;
	}
	
	public Object[] setDictRes() {
		Object[] res = null;
		// �¼�����
		String typesql = "select id,name from t_attemper_code where id like '001%'";
		Object[][] typeres = DBHandler.getMultiResult(typesql);

		// �¼���Դ
		String sourcesql = "select id,name from t_attemper_code where id like '002%'";
		Object[][] sourceres = DBHandler.getMultiResult(sourcesql);

		// һ���¼��̶�
		String levelsql = "select id,name from t_attemper_code where id like '011%'";
		Object[][] levelres = DBHandler.getMultiResult(levelsql);

		// �¼�״̬
		String statesql = "select id,name from t_attemper_code where id like '004%'";
		Object[][] stateres = DBHandler.getMultiResult(statesql);

		// �¹��¼�ϸ��
		String thintypesql = "select id,name from t_attemper_code where id like '007%'";
		Object[][] thintyperes = DBHandler.getMultiResult(thintypesql);

		// �¹��¼��̶�
		String acclevelsql = "select id,name from t_attemper_code where id like '006%'";
		Object[][] acclevelres = DBHandler.getMultiResult(acclevelsql);

		return res;
	}
}
