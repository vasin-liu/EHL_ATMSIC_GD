package com.ehl.dispatch.cdispatch.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import a.a.a.a.w;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.action.Jspsmart;
import com.ehl.dispatch.cdispatch.core.DepartmentUtil;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.notice.core.ContentCore;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.tira.util.Sql;

/**
 * 道路管理数据类
 * 
 * @author wangxt
 * @date 2009-1-16
 * 
 */
public class AllMaterialInfoDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);

	public static Object[][] queryAllMaterialInfo(Map<String, String> map) {
		String jgid = map.get("jgid").toString().trim();
		String jgbh = map.get("jgbh").toString().trim();
		String start = map.get("start").toString().trim();
		String end = map.get("end").toString().trim();
		String jgmc = map.get("jgmc").toString().trim();
		String info_type = map.get("info_type").toString().trim();
		String info_title = map.get("info_title").toString().trim();
		String alarmId = map.get("alarmId").toString().trim();
		String sord = map.get("sord").toString().trim();
		String sortIndex = map.get("sortIndex").toString().trim();

		String eventstate = "";
		String stateStr = "";
		String zdSql = "";

		String selectSql = "SELECT * FROM (SELECT rownum row_num, a.*  FROM (select col2, col3,col4, col5, col6, col7, col8, col1, col9,col10,col11 from (";
		String unionSql = " union ";
		String whereSql = ") a  ) b WHERE 1=1 ";
		if (!(info_title.equals(""))) {
			whereSql += " AND COL2 LIKE '%" + info_title + "%'";
		}
		if (!(jgmc.equals(""))) {
			whereSql += " AND COL3 LIKE '%" + jgmc + "%'";
		}
		if (!(info_type.equals(""))) {
			whereSql += " AND COL6 LIKE '%" + info_type + "%'";
		}
		if (!(start.equals("")) && !(end.equals(""))) {
			whereSql += " AND TRUNC(TO_DATE(COL4,'YYYY-MM-DD HH24:MI:SS'),'DD') BETWEEN TO_DATE('" + start + "','YYYY-MM-DD') " +
					" AND TO_DATE('" + end + "','YYYY-MM-DD')"; 
		}
		
		if (sortIndex.equals("check") || sortIndex.equals("resolve")) {
			whereSql += "  ORDER BY col4 desc";
		} else {
			whereSql += " ORDER BY " + sortIndex + " " + sord;
		}

		String materialInfoSql = "";
		String trafficCrowdSql = "";
		String roadBuildSql = "";
		String noticeSql = "";
		String xcbkSql = "";

		if (jgbh.length() == 6) {
			String zhiduiID = jgbh.substring(0, 4) + "00000000";
			String zongduiID = jgbh.substring(0, 2) + "0000000000";
			eventstate = "('004032','004033','004042','004034')";
			zdSql = "T_ATTEMPER_ACCIDENT ac, T_ATTEMPER_ALARM al";
			stateStr = "decode(f_get_accsjgid(al.alarmid,'"
					+ jgid
					+ "'),null,(select name from t_attemper_code where id=al.EVENTSTATE),'"
					+ zhiduiID + "','支队下发','" + zongduiID + "','总队转发')";
		} else if (jgbh.length() == 4) {
			eventstate = "('004031','004033','004042','004043','004034','004035','004037','004036','004032')";
			stateStr = "(select name from t_attemper_code where id=al.EVENTSTATE)";
			zdSql = "T_ATTEMPER_ACCIDENT_zd ac, T_ATTEMPER_ALARM_zd al";
		} else {
			eventstate = "('004031','004034','004035','004036','004037','004043')";
			stateStr = "(select name from t_attemper_code where id=al.EVENTSTATE)";
			zdSql = "T_ATTEMPER_ACCIDENT_zd ac, T_ATTEMPER_ALARM_zd al";
		}

		materialInfoSql += " select col1, col2, col3, col4, col5, col6, col7, col8,'' as col9,'' as col10,'' as col11 from (select al.alarmid col1,";
		materialInfoSql += " al.title col2, (select jgmc  from t_sys_department where jgid = al.reportunit) as col3,";
		materialInfoSql += " to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as col4, ";
		materialInfoSql += stateStr
				+ "  as col5, '重特大事故' as col6, al.EVENTSTATE as col7,";
		materialInfoSql += " (select jgid from t_sys_department where jgid = al.reportunit) as col8 from "
				+ zdSql;
		materialInfoSql += " where al.alarmid = ac.alarmid and al.EVENTSTATE in";
		materialInfoSql += eventstate
				+ " and al.EVENTSOURCE = '002022' and al.EVENTTYPE = '001024'";
		if (jgbh.length() == 6) {
			materialInfoSql += " and (subStr(al.REPORTUNIT, 0, 6) = '" + jgbh
					+ "' or (instr(f_get_accdept(al.alarmid, 3, 1), '" + jgid
					+ "') != 0)))";
		} else if (jgbh.length() == 4) {
			materialInfoSql += " and (subStr(al.REPORTUNIT, 0, 4) = '" + jgbh
					+ "' or (instr(f_get_accdept(al.alarmid, 3, 1), '" + jgid
					+ "') != 0)))";
		} else {
			materialInfoSql += " and (subStr(al.REPORTUNIT, 0, 2) = '" + jgbh
					+ "' or (instr(f_get_accdept(al.alarmid, 3, 1), '" + jgid
					+ "') != 0)))";
		}
        //Modified by liuwx 2012年3月16日12:00:22
//		trafficCrowdSql += " select taa.ALARMID as col1,  to_char(taa.REPORTTIME, 'yyyy') || '年' ||";
//		trafficCrowdSql += " to_char(taa.REPORTTIME, 'mm') || '月' || to_char(taa.REPORTTIME, 'dd') || '日' || '，在' ||";
//		trafficCrowdSql += " taa.roadid || taa.ROADNAME || '发生一起交通拥堵。' as col2, taa.ALARMREGION as col3,";
        trafficCrowdSql += " select taa.ALARMID as col1, ";
        trafficCrowdSql += " taa.roadid || taa.ROADNAME || '发生一起交通拥堵。' as col2, taa.ALARMREGION as col3,";
        //Modification finished
		trafficCrowdSql += " to_char(taa.REPORTTIME, 'yyyy-mm-dd HH24:mi') as col4, (select name from t_attemper_code";
		trafficCrowdSql += " where id = taa.eventstate) as col5, '交通拥堵' as col6, taa.eventstate as col7, taa.reportunit as col8,'' as col9,'' as col10,'' as col11";
		trafficCrowdSql += " from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc where taa.ALARMID like '"
				+ jgbh + "%'";
		// trafficCrowdSql +=
		// " and taa.ALARMID = tcc.ALARMID and taa.eventstate = '570001'  UNION select taa.ALARMID as col1, to_char(taa.REPORTTIME, 'yyyy') || '年' ||";
        //Modified by liuwx 2012年3月16日12:00:22
//        trafficCrowdSql += " and taa.ALARMID = tcc.ALARMID   UNION select taa.ALARMID as col1, to_char(taa.REPORTTIME, 'yyyy') || '年' ||";
//      trafficCrowdSql += " to_char(taa.REPORTTIME, 'mm') || '月' || to_char(taa.REPORTTIME, 'dd') || '日' || '，在' ||";
//		trafficCrowdSql += " taa.roadid || taa.ROADNAME || '发生一起交通拥堵。' as col2, taa.ALARMREGION as col3, to_char(taa.REPORTTIME, 'yyyy-mm-dd HH24:mi') as col4,";
        trafficCrowdSql += " and taa.ALARMID = tcc.ALARMID   UNION select taa.ALARMID as col1, ";
        trafficCrowdSql += " taa.roadid || taa.ROADNAME || '发生一起交通拥堵。' as col2, taa.ALARMREGION as col3, to_char(taa.REPORTTIME, 'yyyy-mm-dd HH24:mi') as col4,";
        //Modification finished
        trafficCrowdSql += " (select name  from t_attemper_code where id = taa.eventstate) as col5, '交通拥堵' as col6,";
		trafficCrowdSql += " taa.eventstate as col7, taa.reportunit as col8,'' as col9,'' as col10,'' as col11  FROM T_ATTEMPER_ALARM      taa,";
		trafficCrowdSql += " T_ATTEMPER_CONGESTION tcc, t_oa_acceptdept ad  WHERE taa.alarmid = tcc.ALARMID";
		trafficCrowdSql += " AND taa.alarmid = ad.aid AND ad.rpdcode = '"
				+ jgid + "' ";
		// + jgid + "' and taa.eventstate = '570001'";

		xcbkSql += " select xcbk.id col1, '关于' || xcbk.carnumber || '的协查布控' as col2, f_get_dept(xcbk.frpdcode) col3,";
		xcbkSql += " to_char(xcbk.frtime, 'yyyy-mm-dd hh24:mi:ss')col4, decode(xcbk.state, '1', '通报中', '2', '已撤销')col5, '协查通报' as col6,";
		xcbkSql += " xcbk.state col7, xcbk.frpdcode col8, '1' col9, '1' col10, '无' col11 from t_oa_xcbk xcbk where xcbk.frpdcode = '"
				+ jgid + "'";
		xcbkSql += " union select xcbk.id col1, '关于' || xcbk.carnumber || '的协查布控' as col2, f_get_dept(xcbk.frpdcode) col3,";
		xcbkSql += " to_char(xcbk.frtime, 'yyyy-mm-dd hh24:mi:ss')col4,  decode(xcbk.state, '1', '通报中', '2', '已撤销')col5, '协查通报' as col6,";
		xcbkSql += " xcbk.state col7, xcbk.frpdcode col8, ad.state col9,  '2' col10, ad.id col11 from t_oa_xcbk xcbk, t_oa_acceptdept ad";
		xcbkSql += " where xcbk.id = ad.aid and ad.rpdcode = '" + jgid
				+ "' and ad.adid is null";
		xcbkSql += " union select xcbk.id col1, '关于' || xcbk.carnumber || '的协查布控' as col2, f_get_dept(xcbk.frpdcode) col3,";
		xcbkSql += " to_char(xcbk.frtime, 'yyyy-mm-dd hh24:mi:ss')col4, decode(xcbk.state, '1', '通报中', '2', '已撤销')col5,'协查通报' as col6,";
		xcbkSql += " xcbk.state col7, xcbk.frpdcode col8, ad.state col9,  '3' col10, ad.id col11 from t_oa_xcbk xcbk, t_oa_acceptdept ad";
		xcbkSql += " where xcbk.id = ad.aid and ad.rpdcode = '" + jgid
				+ "' and ad.adid is not null";
        //Modified by liuwx 2012年3月16日12:00:22
//		roadBuildSql += " select taa.ALARMID as col1, to_char(taa.REPORTTIME, 'yyyy') || '年' || to_char(taa.REPORTTIME, 'mm') || '月' ||";
//		roadBuildSql += " to_char(taa.REPORTTIME, 'dd') || '日' || '，在' || taa.roadid || taa.ROADNAME || '发生一起施工占道。' as col2,";
        roadBuildSql += " select taa.ALARMID as col1, ";
        roadBuildSql += " taa.roadid || taa.ROADNAME || '备案了一起施工占道。' as col2,";
		//Modification finished
        roadBuildSql += " taa.ALARMREGION as col3, to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi') as col4, (select name";
		roadBuildSql += " from t_attemper_code where id = taa.eventstate) as col5, '施工占道' as col6, taa.eventstate as col7,";
		roadBuildSql += " taa.reportunit col8,'' as col9,'' as col10,'' as col11 from T_ATTEMPER_ALARM     taa, T_ATTEMPER_ROADBUILD tar,  T_OA_DICTDLFX        dic";
		roadBuildSql += " where taa.ALARMID like '" + jgbh
				+ "%' and taa.ALARMID = tar.ALARMID and taa.roadid = dic.dlmc ";
		// +
		// "%' and taa.ALARMID = tar.ALARMID and taa.roadid = dic.dlmc and taa.eventstate = '570005'";

		noticeSql += " select no.id as col1, no.title as col2, f_get_dept(no.spdcode) as col3, to_char(no.stime, 'yyyy-mm-dd hh24:mi') col4,";
		noticeSql += " decode(no.state, '1', '未签收', '2', '已签收', '3', '已处理') col5, '其他重大情况/值班日志' as col6,";
		noticeSql += " no.state as col7, no.spdcode as col8,'1' as col9,'' as col10,'' as col11 from t_oa_notice no  where 1 = 1";
		if (jgbh.length() == 6) {
			noticeSql += " and substr(no.spdcode, 1, 6) = '" + jgbh
					+ "' and no.spdcode = '" + jgid + "'";
		} else if (jgbh.length() == 4) {
			noticeSql += " and substr(no.spdcode, 1, 4) = '" + jgbh
					+ "' and no.spdcode = '" + jgid + "'";
		} else {
			noticeSql += " and substr(no.spdcode, 1, 2) = '" + jgbh
					+ "' and no.spdcode = '" + jgid + "'";
		}

		noticeSql += " union select no.id as col1, no.title as col2, f_get_dept(no.spdcode) as col3, to_char(no.stime, 'yyyy-mm-dd hh24:mi') col4,";
		noticeSql += " decode(no.state, '1', '未签收', '2', '已签收', '3', '已处理') col5,";
		noticeSql += " '其他重大情况/值班日志' as col6, ad.state as col7, no.spdcode as col8,decode(ad.adid, null, '2', '3') as col9, '' as col10, '' as col11  from t_oa_notice no, t_oa_acceptdept ad";
		noticeSql += "  where 1 = 1 and no.id = ad.aid and ad.rpdcode = '"
				+ jgid + "'  and (select spdcode from t_oa_notice";
		noticeSql += " where id = ad.aid) != ad.rpdcode ";
		if (jgbh.length() == 6) {
			noticeSql += " and substr(no.spdcode, 1, 6) = '" + jgbh
					+ "' ) order by col4 desc";
		} else if (jgbh.length() == 4) {
			noticeSql += " and substr(no.spdcode, 1, 4) = '" + jgbh
					+ "' ) order by col4 desc";
		} else {
			noticeSql += " and substr(no.spdcode, 1, 2) = '" + jgbh
					+ "' ) order by col4 desc";
		}

		selectSql += materialInfoSql + unionSql + trafficCrowdSql + unionSql
				+ xcbkSql + unionSql + roadBuildSql + unionSql + noticeSql
				+ whereSql;

		Object[][] results = null;
		try {
			results = DBHandler.getMultiResult(selectSql);
			System.out.println("queryAllMaterialInfo--->" + selectSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static Object[][] queryMaterialInfo(Map<String, String> map) {

		String jgid = map.get("jgid").toString().trim();
		String jgbh = map.get("jgbh").toString().trim();
		String alarmId = map.get("alarmId").toString().trim();
		String sord = map.get("sord").toString().trim();
		String sortIndex = map.get("sortIndex").toString().trim();

		String eventstate = "";
		String stateStr = "";
		String zdSql = "";

		String whereSql = "";
		whereSql = "ORDER BY " + sortIndex + " " + sord;

		String materialInfoSql = "";

		if (jgbh.length() == 6) {
			String zhiduiID = jgbh.substring(0, 4) + "00000000";
			String zongduiID = jgbh.substring(0, 2) + "0000000000";
			eventstate = "('004032','004033','004042','004034')";
			zdSql = "T_ATTEMPER_ACCIDENT ac, T_ATTEMPER_ALARM al";
			stateStr = "decode(f_get_accsjgid(al.alarmid,'"
					+ jgid
					+ "'),null,(select name from t_attemper_code where id=al.EVENTSTATE),'"
					+ zhiduiID + "','支队下发','" + zongduiID + "','总队转发')";
		} else if (jgbh.length() == 4) {
			eventstate = "('004031','004033','004042','004043','004034','004035','004037','004036','004032')";
			stateStr = "(select name from t_attemper_code where id=al.EVENTSTATE)";
			zdSql = "T_ATTEMPER_ACCIDENT_zd ac, T_ATTEMPER_ALARM_zd al";
		} else {
			eventstate = "('004031','004034','004035','004036','004037','004043')";
			stateStr = "(select name from t_attemper_code where id=al.EVENTSTATE)";
			zdSql = "T_ATTEMPER_ACCIDENT_zd ac, T_ATTEMPER_ALARM_zd al";
		}

		materialInfoSql += " select rownum , a.* from (select al.alarmid col1, al.title col2, (select jgmc from t_sys_department where jgid = al.reportunit) as col3,";
		materialInfoSql += " al.reportperson col4, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as col5, "+ stateStr +" as col6,";
		materialInfoSql += " al.EVENTSTATE as col7, (select jgid from t_sys_department where jgid = al.reportunit) as col8, al.GXDD as col9 from "
				+ zdSql;
		materialInfoSql += " where al.alarmid = ac.alarmid and al.EVENTSTATE in";
		materialInfoSql += eventstate + " and al.EVENTSOURCE = '002022' and al.EVENTTYPE = '001024'";
		if (jgbh.length() == 6) {
			materialInfoSql += " and (subStr(al.REPORTUNIT, 0, 6) = '" + jgbh
					+ "' or (instr(f_get_accdept(al.alarmid, 3, 1), '" + jgid
					+ "') != 0)) order by col5 desc)a " + whereSql + "";
		} else if (jgbh.length() == 4) {
			materialInfoSql += " and (subStr(al.REPORTUNIT, 0, 4) = '" + jgbh
					+ "' or (instr(f_get_accdept(al.alarmid, 3, 1), '" + jgid
					+ "') != 0)) order by col5 desc)a " + whereSql + "";
		} else {
			materialInfoSql += " and (subStr(al.REPORTUNIT, 0, 2) = '" + jgbh
					+ "' or (instr(f_get_accdept(al.alarmid, 3, 1), '" + jgid
					+ "') != 0)) order by col5 desc)a " + whereSql + "";
		}
		
		Object[][] results = DBHandler.getMultiResult(materialInfoSql);
		System.out.println("materialInfoSql----->" + materialInfoSql);
		return results;
	}
	
	public static Object[][] queryCrowdInfo(Map<String, String> map) {

		String jgid = map.get("jgid").toString().trim();
		String jgbh = map.get("jgbh").toString().trim();
		String alarmId = map.get("alarmId").toString().trim();
		String sord = map.get("sord").toString().trim();
		String sortIndex = map.get("sortIndex").toString().trim();

		String eventstate = "";
		String stateStr = "";
		String zdSql = "";

		String whereSql = "";
		whereSql = " ORDER BY " + sortIndex + " " + sord;

		String trafficCrowdSql = "";

		trafficCrowdSql += " select rownum,a.* from (select taa.ALARMID col1, taa.ALARMREGION col2, taa.ROADNAME col3, taa.ROADDIRECTION col4,";
		trafficCrowdSql += " taa.roadid col5,  tcc.CONGESTIONREASON col6, to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') col7,";
		trafficCrowdSql += " taa.eventstate col8, (select begin from t_oa_dictdlfx where dlmc=taa.roadid)col9,  (select end from t_oa_dictdlfx where dlmc=taa.roadid)col10,";
		trafficCrowdSql += " taa.reportunit col11, '2' as col12,  (select roadLevel from t_oa_dictdlfx where dlmc=taa.roadid) col13,  taa.ReceiveTime col14";
		trafficCrowdSql += " from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc where taa.ALARMID like '"
				+ jgbh + "%'";
		trafficCrowdSql += " and taa.ALARMID = tcc.ALARMID   UNION select taa.ALARMID col1, taa.ALARMREGION col2, taa.ROADNAME col3,";
		trafficCrowdSql += " taa.ROADDIRECTION col4, taa.roadid col5,  tcc.CONGESTIONREASON col6, to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') col7,";
		trafficCrowdSql += " taa.eventstate col8, (select begin from t_oa_dictdlfx where dlmc=taa.roadid)col9, (select end from t_oa_dictdlfx where dlmc=taa.roadid)col10,";
		trafficCrowdSql += " taa.reportunit col11, DECODE(ad.adid,null,'2','3') as col12,  (select roadLevel from t_oa_dictdlfx where dlmc=taa.roadid) col13,"; 
		trafficCrowdSql += " taa.ReceiveTime col14  FROM T_ATTEMPER_ALARM      taa,";
		trafficCrowdSql += " T_ATTEMPER_CONGESTION tcc, t_oa_acceptdept ad  WHERE taa.alarmid = tcc.ALARMID";
		trafficCrowdSql += " AND taa.alarmid = ad.aid AND ad.rpdcode = '"
				+ jgid + "' order by col7 desc) a  " + whereSql;
		
		Object[][] results = DBHandler.getMultiResult(trafficCrowdSql);
		System.out.println("trafficCrowdSql----->" + trafficCrowdSql);
		return results;
	}
	
	public static Object[][] queryRoadBuildInfo(Map<String, String> map) {
		String jgid = map.get("jgid").toString().trim();
		String jgbh = map.get("jgbh").toString().trim();
		String alarmId = map.get("alarmId").toString().trim();
		String sord = map.get("sord").toString().trim();
		String sortIndex = map.get("sortIndex").toString().trim();

		String eventstate = "";
		String stateStr = "";
		String zdSql = "";

		String whereSql = "";
		whereSql = " ORDER BY " + sortIndex + " " + sord;

		String roadBuildSql = "";

		roadBuildSql += " select rownum,a.* from (select taa.ALARMID col1, taa.ALARMREGION col2, taa.roadid col3, taa.ROADNAME col4, taa.ROADDIRECTION col5,";
		roadBuildSql += " to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi') col6, to_char(taa.CASEENDTIME, 'yyyy-mm-dd HH24:mi') col7,";
		roadBuildSql += " taa.eventstate col8, dic.begin col9, dic.end col10, taa.reportunit col11, tar.BUILDSTATE col12,'2' as  col13";
		roadBuildSql += " from T_ATTEMPER_ALARM taa, T_ATTEMPER_ROADBUILD tar, T_OA_DICTDLFX dic where taa.ALARMID like '"+ jgbh +"%' and taa.ALARMID = tar.ALARMID and taa.roadid = dic.dlmc";
		roadBuildSql += " UNION SELECT taa.ALARMID col1, taa.ALARMREGION col2, taa.roadid col3, taa.ROADNAME col4, taa.ROADDIRECTION col5,";
		roadBuildSql += " to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi') col6, to_char(taa.CASEENDTIME, 'yyyy-mm-dd HH24:mi') col7,";
		roadBuildSql += " taa.eventstate col8, dic.begin col9, dic.end col10, taa.reportunit col11, tar.BUILDSTATE col13, DECODE(toa.adid, null, '2', '3') as stype";
		roadBuildSql += " FROM T_ATTEMPER_ALARM     taa, T_ATTEMPER_ROADBUILD tar, T_OA_DICTDLFX        dic, t_oa_acceptdept      toa";
		roadBuildSql += " WHERE taa.ALARMID = toa.aid AND taa.ALARMID = tar.ALARMID AND taa.roadid = dic.dlmc AND toa.atype = '3'";
		roadBuildSql += " AND toa.rpdcode = '"+ jgid +"' ORDER BY col6 DESC)a " + whereSql;
		
		Object[][] results = DBHandler.getMultiResult(roadBuildSql);
		System.out.println("roadBuildSql----->" + roadBuildSql);
		return results;
	}
	
	public static Object[][] queryXCBKInfo(Map<String, String> map) {
		String jgid = map.get("jgid").toString().trim();
		String jgbh = map.get("jgbh").toString().trim();
		String alarmId = map.get("alarmId").toString().trim();
		String sord = map.get("sord").toString().trim();
		String sortIndex = map.get("sortIndex").toString().trim();

		String eventstate = "";
		String stateStr = "";
		String zdSql = "";

		String whereSql = "";
		whereSql = " ORDER BY " + sortIndex + " " + sord;

		String xcbkSql = "";

		xcbkSql += " select rownum, a.* from (select xcbk.id col1, xcbk.carnumber col2, f_get_dept(xcbk.frpdcode) col3, to_char(xcbk.frtime, 'yyyy-mm-dd hh24:mi:ss') col4,";
		xcbkSql += " f_get_accdept(xcbk.id, 1, 2) col5, '无' col6, decode(xcbk.state, '1', '通报中', '2', '已撤销') col7, xcbk.state col8,";
		xcbkSql += " '1' col9, 1 col10, '无' col11 from t_oa_xcbk xcbk where xcbk.frpdcode = '" + jgid + "'";
		xcbkSql += " union select xcbk.id col1,  xcbk.carnumber col2, f_get_dept(xcbk.frpdcode) col3, to_char(xcbk.frtime, 'yyyy-mm-dd hh24:mi:ss') col4,";
		xcbkSql += " f_get_dept("+ jgid +") col5, F_GET_DISPATCHDEPT(xcbk.id, '"+ jgid +"', '无') col6, decode(xcbk.state, '1', '通报中', '2', '已撤销') clo7,";
		xcbkSql += " xcbk.state col8, ad.state col9, 2 col10, ad.id col11 from t_oa_xcbk xcbk, t_oa_acceptdept ad";
		xcbkSql += " where xcbk.id = ad.aid and ad.rpdcode = '" + jgid
				+ "' and ad.adid is null";
		xcbkSql += " union select xcbk.id col1, xcbk.carnumber col2, f_get_dept(xcbk.frpdcode) col3, to_char(xcbk.frtime, 'yyyy-mm-dd hh24:mi:ss') col4,";
		xcbkSql += " f_get_dept((select rpdcode from t_oa_acceptdept where id = ad.adid)) col5, f_get_dept('"+ jgid +"') col6,";
		xcbkSql += "  decode(xcbk.state, '1', '通报中', '2', '已撤销') col7, xcbk.state col8, ad.state col9, 3 col10,  ad.id col11 from t_oa_xcbk xcbk, t_oa_acceptdept ad";
		xcbkSql += " where xcbk.id = ad.aid and ad.rpdcode = '" + jgid
				+ "' and ad.adid is not null order by col4 desc)a " + whereSql;
		
		Object[][] results = DBHandler.getMultiResult(xcbkSql);
		System.out.println("xcbkSql----->" + xcbkSql);
		return results;
	}
	
	public static Object[][] queryNoticeInfo(Map<String, String> map) {
		String jgid = map.get("jgid").toString().trim();
		String jgbh = map.get("jgbh").toString().trim();
		String alarmId = map.get("alarmId").toString().trim();
		String sord = map.get("sord").toString().trim();
		String sortIndex = map.get("sortIndex").toString().trim();

		String eventstate = "";
		String stateStr = "";
		String zdSql = "";

		String whereSql = "";
		whereSql = " ORDER BY " + sortIndex + " " + sord;

		String noticeSql = "";

		noticeSql += " select rownum,a.* from (select no.id col1, no.title col2, f_get_dept(no.spdcode) col3, f_get_accdept(no.id, 1, 2) col4,";
		noticeSql += " to_char(no.stime, 'yyyy-mm-dd hh24:mi') col5, no.state col6, '1' col7 from t_oa_notice no  where 1 = 1";
		if (jgbh.length() == 6) {
			noticeSql += " and substr(no.spdcode, 1, 6) = '" + jgbh
					+ "' and no.spdcode = '" + jgid + "'";
		} else if (jgbh.length() == 4) {
			noticeSql += " and substr(no.spdcode, 1, 4) = '" + jgbh
					+ "' and no.spdcode = '" + jgid + "'";
		} else {
			noticeSql += " and substr(no.spdcode, 1, 2) = '" + jgbh
					+ "' and no.spdcode = '" + jgid + "'";
		}

		noticeSql += " union select no.id col1, no.title col2, f_get_dept(no.spdcode) col3,  f_get_dept(ad.rpdcode)col4,";
		noticeSql += " to_char(no.stime, 'yyyy-mm-dd hh24:mi') col5, ad.state col6, decode(ad.adid, null, '2', '3') col7  from t_oa_notice no, t_oa_acceptdept ad";
		noticeSql += " where 1 = 1 and no.id = ad.aid and ad.rpdcode = '"
				+ jgid + "'  and (select spdcode from t_oa_notice";
		noticeSql += " where id = ad.aid) != ad.rpdcode ";
		if (jgbh.length() == 6) {
			noticeSql += " and substr(no.spdcode, 1, 6) = '" + jgbh
					+ "'  order by col5 desc)a" + whereSql;
		} else if (jgbh.length() == 4) {
			noticeSql += " and substr(no.spdcode, 1, 4) = '" + jgbh
					+ "'  order by col5 desc)a" + whereSql;
		} else {
			noticeSql += " and substr(no.spdcode, 1, 2) = '" + jgbh
					+ "'  order by col5 desc)a" + whereSql;
		}
		
		Object[][] results = DBHandler.getMultiResult(noticeSql);
		System.out.println("noticeSql----->" + noticeSql);
		return results;
	}
}