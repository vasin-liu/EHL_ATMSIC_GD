package com.ehl.dispatch.cdispatch.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.appframe.Console;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;

/**
 * 
 * @author
 * 
 */
public class CommonUtility {
	Logger logger = Logger.getLogger(CommonUtility.class);

	private int length2;// 数组第二维的长度，按小时统计长度为26，按月统计12，按周和按日统计需要计算长度
	private ArrayList<String> weekList;// 存放周时间段
	private HashMap dateMap;// 存放日期
	private String[] header;// 存放表头
	private int timetype;//
	private int stattype;
	private String starttime;
	private String endtime;
	private String startyearId;
	private String startmonthId;
	private String endyearId;
	private String endmonthId;
	private String year;
	private String departIds;
	private String depNames;
	private String monthSelect;
	private String jgbh;
	private String jgid;
	private String departType;

	private HashMap<String, String> keyValueMap = new HashMap<String, String>();

	/**
	 * 获取折线图数据<br/>
	 * 获取折线图数据的取得方法
	 * 
	 * @param timetype
	 * @param stattype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 * @return
	 */
	public ArrayList getCharArr(String timetype, String stattype,
			String starttime, String endtime, String startyearId,
			String startmonthId, String year, String departIds,
			String depNames, String endyearId, String endmonthId,
			String monthSelect, String jgbh, String jgid, String departType) {
		this.timetype = StringHelper.obj2int(timetype, 0);
		this.stattype = StringHelper.obj2int(stattype, 0);
		this.starttime = starttime;
		this.endtime = endtime;
		this.startyearId = startyearId;
		this.startmonthId = startmonthId;
		this.endyearId = endyearId;
		this.endmonthId = endmonthId;
		this.year = year;
		this.departIds = departIds;
		this.monthSelect = monthSelect;
		this.jgbh = jgbh;
		this.jgid = jgid;
		this.departType = departType;
		analyzeLength2AndHeader(timetype, stattype, starttime, endtime,
				startyearId, startmonthId, year, departIds, endyearId,
				endmonthId);
		Object[][] arr = getData(timetype, stattype, starttime, endtime,
				startyearId, startmonthId, year, departIds, depNames,
				endyearId, endmonthId);
		arr = dataDispose(arr, stattype);
		ArrayList list = new ArrayList();
		list.add(arr);
		list.add(this.header);
		return list;
	}

	/**
	 * 取得统计信息<br/>
	 * 统计信息的取得处理
	 * 
	 * @param timetype
	 * @param stattype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 * @return
	 */
	public String getXml(String timetype, String stattype, String starttime,
			String endtime, String startyearId, String startmonthId,
			String year, String departIds, String depNames, String endyearId,
			String endmonthId, String monthSelect, String jgbh, String jgid,
			String departType) {
		this.timetype = StringHelper.obj2int(timetype, 0);
		this.stattype = StringHelper.obj2int(stattype, 0);
		this.starttime = starttime;
		this.endtime = endtime;
		this.startyearId = startyearId;
		this.startmonthId = startmonthId;
		this.endyearId = endyearId;
		this.endmonthId = endmonthId;
		this.year = year;
		this.departIds = departIds;
		this.depNames = depNames;
		this.monthSelect = monthSelect;
		this.jgbh = jgbh;
		this.jgid = jgid;
		this.departType = departType;
		analyzeLength2AndHeader(timetype, stattype, starttime, endtime,
				startyearId, startmonthId, year, departIds, endyearId,
				endmonthId);
		// 取得检索数据结果集方法的呼出
		Object[][] arr = getData(timetype, stattype, starttime, endtime,
				startyearId, startmonthId, year, departIds, depNames,
				endyearId, endmonthId);
		// 统计类型"时间"时的处理
		if ("1".equals(stattype)) {
			// 将结果集转成二维数组并对结果集排序,处理后的数组长度
			arr = dataDispose(arr, stattype);
		} else {
			Arrays.sort(arr, new ArrayComparator());
			// arr = dataFormatSort(arr,stattype);
			arr = subArr(arr);
		}
		// 将二维数组转成xml字符串
		String xml = bulidXml(arr);
		System.out.println(xml);
		return xml;
	}

	/**
	 * 取得统计信息<br/>
	 * 统计信息的取得处理
	 * 
	 * @param timetype
	 * @param stattype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 * @return
	 */
	public String getChartXml(String timetype, String stattype,
			String starttime, String endtime, String startyearId,
			String startmonthId, String year, String departIds,
			String depNames, String endyearId, String endmonthId,
			String monthSelect, String jgbh, String jgid, String departType) {
		this.timetype = StringHelper.obj2int(timetype, 0);
		this.stattype = StringHelper.obj2int(stattype, 0);
		this.starttime = starttime;
		this.endtime = endtime;
		this.startyearId = startyearId;
		this.startmonthId = startmonthId;
		this.endyearId = endyearId;
		this.endmonthId = endmonthId;
		this.year = year;
		this.departIds = departIds;
		this.depNames = depNames;
		this.monthSelect = monthSelect;
		this.jgbh = jgbh;
		this.jgid = jgid;
		this.departType = departType;
		analyzeLength2AndHeader(timetype, stattype, starttime, endtime,
				startyearId, startmonthId, year, departIds, endyearId,
				endmonthId);
		// 取得检索数据结果集方法的呼出
		Object[][] arr = getData(timetype, stattype, starttime, endtime,
				startyearId, startmonthId, year, departIds, depNames,
				endyearId, endmonthId);
		// 统计类型"时间"时的处理
		if ("1".equals(stattype)) {
			// 将结果集转成二维数组并对结果集排序,处理后的数组长度
			arr = dataDispose(arr, stattype);
		} else {
			Arrays.sort(arr, new ArrayComparator());
			// arr = dataFormatSort(arr,stattype);
			arr = subArr(arr);
		}
		// 将二维数组转成xml字符串
		String xml = bulidXml(arr);
		System.out.println(xml);
		return xml;
	}

	/**
	 * 取得检索数据结果集<br/>
	 * 取得检索数据结果集的处理
	 * 
	 * @param timetype
	 * @param stattype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 * @return
	 */
	private Object[][] getData(String timetype, String stattype,
			String starttime, String endtime, String startyearId,
			String startmonthId, String year, String departIds,
			String depNames, String endyearId, String endmonthId) {
		// 统计方式“时间”时的处理
		if (stattype.equals("1")) {
			return getInfoByAlarmUnit(timetype, starttime, endtime,
					startyearId, startmonthId, year, endyearId, endmonthId);
			// 统计方式“报警类别”时的处理
		} else {
			return getInfoByAlarmType(timetype, starttime, endtime,
					startyearId, startmonthId, year, departIds, depNames,
					endyearId, endmonthId);
		}
	}

	public Object[][] getZdFullCityInfo() {

		StringBuffer strBuffer = new StringBuffer();
		int type = this.timetype;
		if (type == 1) {
			// 日
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm-dd'),");
			strBuffer.append(" count(1),");
			strBuffer.append(" substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");

			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			strBuffer.append(this.starttime);
			strBuffer.append("' AND ");
			strBuffer.append("TO_CHAR(ALARMTIME,'yyyy-mm-dd') <= '");
			strBuffer.append(this.endtime);
			strBuffer.append("' and substr(ALARMUNIT, 0, 4) = '");
			strBuffer.append(this.jgid.substring(0, 4));
			strBuffer.append("'");

			strBuffer.append(" group by substr(ALARMUNIT, 0, 4),");
			strBuffer
					.append(" f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm-dd')");
		} else if (type == 2) {
			// 周
			strBuffer
					.append("SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART, COUNT(1),substr(ALARMUNIT, 0, 4) FROM  ");
			strBuffer.append("(SELECT ALARMUNIT,");
			strBuffer.append("(CASE WHEN TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(0));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(1));
			strBuffer
					.append("' THEN '1' ELSE ( CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(2));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(3));
			strBuffer
					.append("' THEN '2' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(4));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(5));
			strBuffer
					.append("' THEN '3' ELSE (CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(6));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(7));
			strBuffer.append("' THEN '4' ");
			if (this.weekList.size() == 10) {
				strBuffer
						.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(8));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(9));
				strBuffer
						.append("' THEN '5'   END) END) END) END) END) PART ,ALARMTIME");
			} else if (this.weekList.size() == 12) {
				strBuffer
						.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(8));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(9));
				strBuffer
						.append("' THEN '5' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(10));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(11));
				strBuffer
						.append("' THEN '6'    END) END) END) END) END) END) PART ,ALARMTIME");
			} else {
				strBuffer.append("  END) END) END) END) PART ,ALARMTIME ");
			}
			strBuffer.append("   FROM T_ATTEMPER_ALARM_ZD   ");
			strBuffer.append(" WHERE  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			strBuffer.append(this.weekList.get(0));
			strBuffer.append("' AND TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(this.weekList.size() - 1));
			strBuffer.append("' and substr(ALARMUNIT, 0, 4) = '");
			strBuffer.append(this.jgid.substring(0, 4));
			strBuffer.append("'");
			strBuffer.append(")");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART,substr(ALARMUNIT, 0, 4)");

		} else if (type == 3) {
			// 月
			String startYm = this.startyearId + "-" + this.startmonthId;
			String endYm = this.endyearId + "-" + this.endmonthId;

			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm'),");
			strBuffer.append(" count(1),");
			strBuffer.append(" substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");

			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm') >= '");
			strBuffer.append(startYm);
			strBuffer.append("' AND ");
			strBuffer.append("TO_CHAR(ALARMTIME,'yyyy-mm') <= '");
			strBuffer.append(endYm);
			strBuffer.append("' and substr(ALARMUNIT, 0, 4) = '");
			strBuffer.append(this.jgid.substring(0, 4));
			strBuffer.append("'");

			strBuffer.append(" group by substr(ALARMUNIT, 0, 4),");
			strBuffer
					.append(" f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm')");

		} else if (type == 4) {
			// 季度
			strBuffer
					.append("SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART, count(1), substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM (SELECT ALARMUNIT,");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-01' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-03' THEN");
			strBuffer.append(" '1'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-04' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-06' THEN");
			strBuffer.append(" '2'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-07' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-09' THEN");
			strBuffer.append(" '3'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-10' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-12' THEN");
			strBuffer.append(" '4'");
			strBuffer.append(" END) END) END) END) PART,");
			strBuffer.append(" ALARMTIME");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
			strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') = '"
					+ this.year);
			strBuffer.append("' and substr(ALARMUNIT, 0, 4) = '");
			strBuffer.append(this.jgid.substring(0, 4));
			strBuffer.append("' )");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART,substr(ALARMUNIT, 0, 4)");

		} else if (type == 5) {
			// 半年
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART, count(1), substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM (SELECT ALARMUNIT,");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-01' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-06' THEN");
			strBuffer.append(" '1'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-07' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-12' THEN");
			strBuffer.append(" '2'");
			strBuffer.append(" END) END) PART,");
			strBuffer.append(" ALARMTIME");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
			strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') = '");
			strBuffer.append(this.year);
			strBuffer.append("' and substr(ALARMUNIT, 0, 4) = '");
			strBuffer.append(this.jgid.substring(0, 4));
			strBuffer.append("' )");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART,substr(ALARMUNIT, 0, 4)");

		} else if (type == 6) {
			// 年
			String startYm = this.startyearId;
			String endYm = this.endyearId;
			strBuffer
					.append(" SELECT  f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),TO_CHAR(ALARMTIME,'yyyy'),COUNT(1),substr(ALARMUNIT, 0, 4) ");
			strBuffer.append(" FROM   T_ATTEMPER_ALARM_ZD ");
			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy') >= '");
			strBuffer.append(startYm);
			strBuffer.append("' and  TO_CHAR(ALARMTIME,'yyyy') <= '");
			strBuffer.append(endYm);
			strBuffer.append("' and substr(ALARMUNIT, 0, 4) = '");
			strBuffer.append(this.jgid.substring(0, 4));
			strBuffer.append("'");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), TO_CHAR(ALARMTIME,'yyyy'),substr(ALARMUNIT, 0, 4)");
		}
		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getInfoByAlarmUnit()查询数据时SQL错误： "
					+ strBuffer);
		}
		return temp;

	}

	/**
	 * 统计方式“时间”时统计信息的取得<br/>
	 * 统计方式“时间”时统计信息的取得处理
	 * 
	 * @param timetype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 * @return
	 */
	private Object[][] getInfoByAlarmUnit(String timetype, String starttime,
			String endtime, String startyearId, String startmonthId,
			String year, String endyearId, String endmonthId) {
		Object[][] temp = null;
		// 按日统计时信息的取得处理
		if (timetype.equals("1")) {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT f_get_dept(ALARMUNIT),TO_CHAR(ALARMTIME,'yyyy-mm-dd'),COUNT(1) ");
			sql.append(" FROM   T_ATTEMPER_ALARM_ZD ");
			sql.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			sql.append(starttime);
			sql.append("' AND ");
			sql.append("TO_CHAR(ALARMTIME,'yyyy-mm-dd') <= '");
			sql.append(endtime);

			if (this.jgbh.length() == 2) {
				// 总队时检索单位的设置
				if ("1".equals(departType)) {
					// 统计单位"全省统计"时的处理
					return getAllTotalInfo();
				} else if ("2".equals(departType)) {
					// 统计单位"支队统计"时的处理
					return getZdSearchInfo();
				} else if ("3".equals(departType)) {
					// 统计单位"大队统计"时的处理
					if ((departIds != null) && (!"".equals(departIds))) {
						sql.append("' AND '" + departIds
								+ "' like '%'||ALARMUNIT||'%'");
					} else {
						sql.append("' ");
					}
				}
			} else if (this.jgbh.length() == 4) {
				// 支队统计时全市统计时的处理
				if ("4".equals(departType)) {
					return getZdFullCityInfo();
				}
				// 支队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append("' AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append("' AND ALARMUNIT like '");
					sql.append(jgid.substring(0, 4));
					sql.append("%'");
				}
			} else if (this.jgbh.length() == 6) {
				// 大队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append("' AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append("' AND ALARMUNIT = '");
					sql.append(jgid);
					sql.append("' ");
				}
			}
			sql.append(" group by ALARMUNIT, TO_CHAR(ALARMTIME,'yyyy-mm-dd') ");
			sql.append(" order by ALARMUNIT, TO_CHAR(ALARMTIME,'yyyy-mm-dd') desc");
			try {
				temp = DBHandler.getMultiResult(sql.toString());
			} catch (Exception e) {
				logger.error("[重大警情统计]" + "getSGDD()查询数据时SQL错误： " + sql);
			}

			// 按小时统计时信息的取得处理
		} else if (timetype.equals("7")) {
			// 按月统计时信息的取得处理
		} else if (timetype.equals("3")) {
			String startYm = startyearId + "-" + startmonthId;
			String endYm = endyearId + "-" + endmonthId;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT f_get_dept(ALARMUNIT),TO_CHAR(ALARMTIME,'yyyy-mm'),COUNT(1) ");
			sql.append(" FROM   T_ATTEMPER_ALARM_ZD ");
			sql.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm') >= '");
			sql.append(startYm);
			sql.append("' and  TO_CHAR(ALARMTIME,'yyyy-mm') <= '");
			sql.append(endYm);

			if (this.jgbh.length() == 2) {
				// 总队时检索单位的设置
				if ("1".equals(departType)) {
					// 统计单位"全省统计"时的处理
					return getAllTotalInfo();
				} else if ("2".equals(departType)) {
					// 统计单位"支队统计"时的处理
					return getZdSearchInfo();
				} else if ("3".equals(departType)) {
					// 统计单位"大队统计"时的处理
					if ((departIds != null) && (!"".equals(departIds))) {
						sql.append("' AND '" + departIds
								+ "' like '%'||ALARMUNIT||'%'");
					} else {
						sql.append("' ");
					}
				}
			} else if (this.jgbh.length() == 4) {
				// 支队统计时全市统计时的处理
				if ("4".equals(departType)) {
					return getZdFullCityInfo();
				}
				// 支队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append("' AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append("' AND ALARMUNIT like '");
					sql.append(jgid.substring(0, 4));
					sql.append("%'");
				}
			} else if (this.jgbh.length() == 6) {
				// 大队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append("' AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append("' AND ALARMUNIT = '");
					sql.append(jgid);
					sql.append("' ");
				}
			}
			sql.append(" group by ALARMUNIT,TO_CHAR(ALARMTIME,'yyyy-mm') ");
			sql.append(" order by ALARMUNIT, TO_CHAR(ALARMTIME,'yyyy-mm') desc");
			try {
				temp = DBHandler.getMultiResult(sql.toString());
			} catch (Exception e) {
				logger.error("[重大警情统计]" + "getInfoByAlarmUnit()查询数据时SQL错误： "
						+ sql);
			}
			// 按周统计时信息的取得处理
		} else if (timetype.equals("2")) {// 按周统计
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT f_get_dept(ALARMUNIT), PART, COUNT(*) FROM  ");
			sql.append("(SELECT ALARMUNIT,");
			sql.append("(CASE WHEN TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(0));
			sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(1));
			sql.append("' THEN '1' ELSE ( CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(2));
			sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(3));
			sql.append("' THEN '2' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(4));
			sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(5));
			sql.append("' THEN '3' ELSE (CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(6));
			sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(7));
			sql.append("' THEN '4' ");
			if (this.weekList.size() == 10) {
				sql.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				sql.append(this.weekList.get(8));
				sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				sql.append(this.weekList.get(9));
				sql.append("' THEN '5'   END) END) END) END) END) PART ,ALARMTIME");
			} else if (this.weekList.size() == 12) {
				sql.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				sql.append(this.weekList.get(8));
				sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				sql.append(this.weekList.get(9));
				sql.append("' THEN '5' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				sql.append(this.weekList.get(10));
				sql.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				sql.append(this.weekList.get(11));
				sql.append("' THEN '6'    END) END) END) END) END) END) PART ,ALARMTIME");
			} else {
				sql.append("  END) END) END) END) PART ,ALARMTIME ");
			}
			sql.append("   FROM T_ATTEMPER_ALARM_ZD   ");
			sql.append(" WHERE  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			sql.append(this.weekList.get(0));
			sql.append("' AND TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(this.weekList.size() - 1));
			sql.append("'");
			if (this.jgbh.length() == 2) {
				// 总队时检索单位的设置
				if ("1".equals(departType)) {
					// 统计单位"全省统计"时的处理
					return getAllTotalInfo();
				} else if ("2".equals(departType)) {
					// 统计单位"支队统计"时的处理
					return getZdSearchInfo();
				} else if ("3".equals(departType)) {
					// 统计单位"大队统计"时的处理
					if ((departIds != null) && (!"".equals(departIds))) {
						sql.append(" AND '" + departIds
								+ "' like '%'||ALARMUNIT||'%'");
					} else {
						sql.append(" ");
					}
				}
			} else if (this.jgbh.length() == 4) {
				// 支队统计时全市统计时的处理
				if ("4".equals(departType)) {
					return getZdFullCityInfo();
				}
				// 支队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append(" AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append(" AND ALARMUNIT like '");
					sql.append(jgid.substring(0, 4));
					sql.append("%'");
				}
			} else if (this.jgbh.length() == 6) {
				// 大队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append(" AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append(" AND ALARMUNIT = '");
					sql.append(jgid);
					sql.append("' ");
				}
			}
			sql.append(")");
			sql.append(" GROUP BY ALARMUNIT, PART  ORDER BY ALARMUNIT, PART ");
			try {
				System.out.println(sql);
				temp = DBHandler.getMultiResult(sql.toString());
			} catch (Exception e) {
				logger.error("[重大警情统计]" + "getInfoByAlarmUnit()查询数据时SQL错误： "
						+ sql.toString());
			}
			// 按季度统计时的处理
		} else if (timetype.equals("4")) {
			temp = getSeasonInfo();
			// 按半年统计时信息的取得处理
		} else if (timetype.equals("5")) {
			temp = getHalfYearInfo();
			// 按年统计时信息的取得处理
		} else if (timetype.equals("6")) {
			String startYm = startyearId;
			String endYm = endyearId;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT f_get_dept(ALARMUNIT),TO_CHAR(ALARMTIME,'yyyy'),COUNT(1) ");
			sql.append(" FROM   T_ATTEMPER_ALARM_ZD ");
			sql.append(" where  TO_CHAR(ALARMTIME,'yyyy') >= '");
			sql.append(startYm);
			sql.append("' and  TO_CHAR(ALARMTIME,'yyyy') <= '");
			sql.append(endYm);
			if (this.jgbh.length() == 2) {
				// 总队时检索单位的设置
				if ("1".equals(departType)) {
					// 统计单位"全部"时的处理
					return getAllTotalInfo();
				} else if ("2".equals(departType)) {
					// 统计单位"支队统计"时的处理
					return getZdSearchInfo();
				} else if ("3".equals(departType)) {
					// 统计单位"大队统计"时的处理
					if ((departIds != null) && (!"".equals(departIds))) {
						sql.append("' AND '" + departIds
								+ "' like '%'||ALARMUNIT||'%'");
					} else {
						sql.append("' ");
					}
				}
			} else if (this.jgbh.length() == 4) {
				// 支队统计时全市统计时的处理
				if ("4".equals(departType)) {
					return getZdFullCityInfo();
				}
				// 支队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append("' AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append("' AND ALARMUNIT like '");
					sql.append(jgid.substring(0, 4));
					sql.append("%'");
				}
			} else if (this.jgbh.length() == 6) {
				// 大队时检索单位的设置
				if ((departIds != null) && (!"".equals(departIds))) {
					sql.append("' AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					sql.append("' AND ALARMUNIT = '");
					sql.append(jgid);
					sql.append("' ");
				}
			}
			sql.append(" group by ALARMUNIT,TO_CHAR(ALARMTIME,'yyyy') ");
			sql.append(" order by ALARMUNIT, TO_CHAR(ALARMTIME,'yyyy') desc");
			try {
				temp = DBHandler.getMultiResult(sql.toString());
			} catch (Exception e) {
				logger.error("[重大警情统计]" + "getInfoByAlarmUnit()查询数据时SQL错误： "
						+ sql);
			}
		}
		return temp;
	}

	/**
	 * 取得支队统计单位时所有支队数据<br/>
	 * 取得支队统计单位时所有支队警情数据
	 * 
	 * @return
	 */
	public Object[][] getZdSearchInfo() {
		StringBuffer strBuffer = new StringBuffer();
		int type = this.timetype;
		if (type == 1) {
			// 日
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm-dd'),");
			strBuffer.append(" count(1),");
			strBuffer.append(" substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");

			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			strBuffer.append(this.starttime);
			strBuffer.append("' AND ");
			strBuffer.append("TO_CHAR(ALARMTIME,'yyyy-mm-dd') <= '");
			strBuffer.append(this.endtime);
			strBuffer.append("' ");

			strBuffer.append(" group by substr(ALARMUNIT, 0, 4),");
			strBuffer
					.append(" f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm-dd')");
		} else if (type == 2) {
			// 周
			strBuffer
					.append("SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART, COUNT(1),substr(ALARMUNIT, 0, 4) FROM  ");
			strBuffer.append("(SELECT ALARMUNIT,");
			strBuffer.append("(CASE WHEN TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(0));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(1));
			strBuffer
					.append("' THEN '1' ELSE ( CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(2));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(3));
			strBuffer
					.append("' THEN '2' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(4));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(5));
			strBuffer
					.append("' THEN '3' ELSE (CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(6));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(7));
			strBuffer.append("' THEN '4' ");
			if (this.weekList.size() == 10) {
				strBuffer
						.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(8));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(9));
				strBuffer
						.append("' THEN '5'   END) END) END) END) END) PART ,ALARMTIME");
			} else if (this.weekList.size() == 12) {
				strBuffer
						.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(8));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(9));
				strBuffer
						.append("' THEN '5' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(10));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(11));
				strBuffer
						.append("' THEN '6'    END) END) END) END) END) END) PART ,ALARMTIME");
			} else {
				strBuffer.append("  END) END) END) END) PART ,ALARMTIME ");
			}
			strBuffer.append("   FROM T_ATTEMPER_ALARM_ZD   ");
			strBuffer.append(" WHERE  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			strBuffer.append(this.weekList.get(0));
			strBuffer.append("' AND TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(this.weekList.size() - 1));
			strBuffer.append("'");
			strBuffer.append(")");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART,substr(ALARMUNIT, 0, 4)");

		} else if (type == 3) {
			// 月
			String startYm = this.startyearId + "-" + this.startmonthId;
			String endYm = this.endyearId + "-" + this.endmonthId;

			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm'),");
			strBuffer.append(" count(1),");
			strBuffer.append(" substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");

			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm') >= '");
			strBuffer.append(startYm);
			strBuffer.append("' AND ");
			strBuffer.append("TO_CHAR(ALARMTIME,'yyyy-mm') <= '");
			strBuffer.append(endYm);
			strBuffer.append("' ");

			strBuffer.append(" group by substr(ALARMUNIT, 0, 4),");
			strBuffer
					.append(" f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm')");

		} else if (type == 4) {
			// 季度
			strBuffer
					.append("SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART, count(1), substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM (SELECT ALARMUNIT,");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-01' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-03' THEN");
			strBuffer.append(" '1'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-04' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-06' THEN");
			strBuffer.append(" '2'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-07' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-09' THEN");
			strBuffer.append(" '3'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-10' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-12' THEN");
			strBuffer.append(" '4'");
			strBuffer.append(" END) END) END) END) PART,");
			strBuffer.append(" ALARMTIME");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
			strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') >= '"
					+ this.year + "')");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART,substr(ALARMUNIT, 0, 4)");

		} else if (type == 5) {
			// 半年
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART, count(1), substr(ALARMUNIT, 0, 4) zd");
			strBuffer.append(" FROM (SELECT ALARMUNIT,");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-01' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-06' THEN");
			strBuffer.append(" '1'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-07' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-12' THEN");
			strBuffer.append(" '2'");
			strBuffer.append(" END) END) PART,");
			strBuffer.append(" ALARMTIME");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
			strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') >= '");
			strBuffer.append(this.year);
			strBuffer.append("' )");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), PART,substr(ALARMUNIT, 0, 4)");

		} else if (type == 6) {
			// 年
			String startYm = this.startyearId;
			String endYm = this.endyearId;
			strBuffer
					.append(" SELECT  f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'),TO_CHAR(ALARMTIME,'yyyy'),COUNT(1),substr(ALARMUNIT, 0, 4) ");
			strBuffer.append(" FROM   T_ATTEMPER_ALARM_ZD ");
			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy') >= '");
			strBuffer.append(startYm);
			strBuffer.append("' and  TO_CHAR(ALARMTIME,'yyyy') <= '");
			strBuffer.append(endYm);
			strBuffer.append("'");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000'), TO_CHAR(ALARMTIME,'yyyy'),substr(ALARMUNIT, 0, 4)");
		}
		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getInfoByAlarmUnit()查询数据时SQL错误： "
					+ strBuffer);
		}
		return temp;
	}

	/**
	 * 取得全省数据<br/>
	 * 取得全省统计数据
	 * 
	 * @return
	 */
	public Object[][] getAllTotalInfo() {
		StringBuffer strBuffer = new StringBuffer();
		int type = this.timetype;
		if (type == 1) {
			// 日
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm-dd'),");
			strBuffer.append(" count(1),");
			strBuffer.append(" substr(ALARMUNIT, 0, 2) zd");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");

			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			strBuffer.append(this.starttime);
			strBuffer.append("' AND ");
			strBuffer.append("TO_CHAR(ALARMTIME,'yyyy-mm-dd') <= '");
			strBuffer.append(this.endtime);
			strBuffer.append("' ");

			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),substr(ALARMUNIT, 0, 2),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm-dd')");
		} else if (type == 2) {
			// 周
			strBuffer
					.append("SELECT f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'), PART, COUNT(1),substr(ALARMUNIT, 0, 2) FROM  ");
			strBuffer.append("(SELECT ALARMUNIT,");
			strBuffer.append("(CASE WHEN TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(0));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(1));
			strBuffer
					.append("' THEN '1' ELSE ( CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(2));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(3));
			strBuffer
					.append("' THEN '2' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(4));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(5));
			strBuffer
					.append("' THEN '3' ELSE (CASE  WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
			strBuffer.append(this.weekList.get(6));
			strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(7));
			strBuffer.append("' THEN '4' ");
			if (this.weekList.size() == 10) {
				strBuffer
						.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(8));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(9));
				strBuffer
						.append("' THEN '5'   END) END) END) END) END) PART ,ALARMTIME");
			} else if (this.weekList.size() == 12) {
				strBuffer
						.append(" ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(8));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(9));
				strBuffer
						.append("' THEN '5' ELSE (CASE WHEN  TO_CHAR(ALARMTIME,'yyyy-mm-dd')>='");
				strBuffer.append(this.weekList.get(10));
				strBuffer.append("' AND  TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
				strBuffer.append(this.weekList.get(11));
				strBuffer
						.append("' THEN '6'    END) END) END) END) END) END) PART ,ALARMTIME");
			} else {
				strBuffer.append("  END) END) END) END) PART ,ALARMTIME ");
			}
			strBuffer.append("   FROM T_ATTEMPER_ALARM_ZD   ");
			strBuffer.append(" WHERE  TO_CHAR(ALARMTIME,'yyyy-mm-dd') >= '");
			strBuffer.append(this.weekList.get(0));
			strBuffer.append("' AND TO_CHAR(ALARMTIME,'yyyy-mm-dd')<='");
			strBuffer.append(this.weekList.get(this.weekList.size() - 1));
			strBuffer.append("'");
			strBuffer.append(")");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),substr(ALARMUNIT, 0, 2),PART");

		} else if (type == 3) {
			// 月
			String startYm = this.startyearId + "-" + this.startmonthId;
			String endYm = this.endyearId + "-" + this.endmonthId;

			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm'),");
			strBuffer.append(" count(1),");
			strBuffer.append(" substr(ALARMUNIT, 0, 2) zd");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");

			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy-mm') >= '");
			strBuffer.append(startYm);
			strBuffer.append("' AND ");
			strBuffer.append("TO_CHAR(ALARMTIME,'yyyy-mm') <= '");
			strBuffer.append(endYm);
			strBuffer.append("' ");

			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),substr(ALARMUNIT, 0, 2),");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm')");

		} else if (type == 4) {
			// 季度
			strBuffer
					.append("SELECT f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'), PART, count(1), substr(ALARMUNIT, 0, 2) zd");
			strBuffer.append(" FROM (SELECT ALARMUNIT,");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-01' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-03' THEN");
			strBuffer.append(" '1'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-04' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-06' THEN");
			strBuffer.append(" '2'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-07' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-09' THEN");
			strBuffer.append(" '3'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-10' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-12' THEN");
			strBuffer.append(" '4'");
			strBuffer.append(" END) END) END) END) PART,");
			strBuffer.append(" ALARMTIME");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
			strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') >= '"
					+ this.year + "')");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),substr(ALARMUNIT, 0, 2), PART ");

		} else if (type == 5) {
			// 半年
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'), PART, count(1), substr(ALARMUNIT, 0, 2) zd");
			strBuffer.append(" FROM (SELECT ALARMUNIT,");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-01' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-06' THEN");
			strBuffer.append(" '1'");
			strBuffer.append(" ELSE");
			strBuffer.append(" (CASE");
			strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '"
					+ this.year + "-07' AND");
			strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
					+ "-12' THEN");
			strBuffer.append(" '2'");
			strBuffer.append(" END) END) PART,");
			strBuffer.append(" ALARMTIME");
			strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
			strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') >= '");
			strBuffer.append(this.year);
			strBuffer.append("' )");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),substr(ALARMUNIT, 0, 2),PART");

		} else if (type == 6) {
			// 年
			String startYm = this.startyearId;
			String endYm = this.endyearId;
			strBuffer
					.append(" SELECT f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),TO_CHAR(ALARMTIME,'yyyy'),COUNT(1),substr(ALARMUNIT, 0, 2) ");
			strBuffer.append(" FROM   T_ATTEMPER_ALARM_ZD ");
			strBuffer.append(" where  TO_CHAR(ALARMTIME,'yyyy') >= '");
			strBuffer.append(startYm);
			strBuffer.append("' and  TO_CHAR(ALARMTIME,'yyyy') <= '");
			strBuffer.append(endYm);
			strBuffer.append("'");
			strBuffer
					.append(" group by f_get_dept(substr(ALARMUNIT, 0, 2) || '0000000000'),substr(ALARMUNIT, 0,2), TO_CHAR(ALARMTIME,'yyyy')");
		}
		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getInfoByAlarmUnit()查询数据时SQL错误： "
					+ strBuffer);
		}
		return temp;
	}

	/**
	 * 获取全年数据<br/>
	 * 
	 * @return
	 */
	public Object[][] getFullYearAlarmInfo() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" SELECT f_get_dept(ALARMUNIT),  count(1)");
		strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
		strBuffer.append(" where TO_CHAR(ALARMTIME, 'yyyy') = '");
		strBuffer.append(this.year);
		strBuffer.append("'");
		strBuffer.append(" group by f_get_dept(ALARMUNIT)");
		strBuffer.append(" order by f_get_dept(ALARMUNIT)");
		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getFullYearAlarmInfo()查询数据时SQL错误： "
					+ strBuffer);
		}
		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {

			}
		}
		return temp;
	}

	/**
	 * 获取选定年度各季度数据<br>
	 * 
	 * @return
	 */
	public Object[][] getSeasonInfo() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" SELECT f_get_dept(ALARMUNIT), PART, COUNT(*)");
		strBuffer.append(" FROM (SELECT ALARMUNIT,");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '" + this.year
				+ "-01' AND");
		strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
				+ "-03' THEN");
		strBuffer.append(" '1'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '" + this.year
				+ "-04' AND");
		strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
				+ "-06' THEN");
		strBuffer.append(" '2'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '" + this.year
				+ "-07' AND");
		strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
				+ "-09' THEN");
		strBuffer.append(" '3'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '" + this.year
				+ "-10' AND");
		strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
				+ "-12' THEN");
		strBuffer.append(" '4'");
		strBuffer.append(" END) END) END) END) PART,");
		strBuffer.append(" ALARMTIME");
		strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
		strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') = '" + this.year
				+ "'");
		if (this.jgbh.length() == 2) {
			// 总队时检索单位的设置
			if ("1".equals(departType)) {
				// 统计单位"全省统计"时的处理
				return getAllTotalInfo();
			} else if ("2".equals(departType)) {
				// 统计单位"支队统计"时的处理
				return getZdSearchInfo();
			} else if ("3".equals(departType)) {
				// 统计单位"大队统计"时的处理
				if ((departIds != null) && (!"".equals(departIds))) {
					strBuffer.append(" AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					strBuffer.append(" ");
				}
			}
		} else if (this.jgbh.length() == 4) {
			// 支队统计时全市统计时的处理
			if ("4".equals(departType)) {
				return getZdFullCityInfo();
			}
			// 支队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||ALARMUNIT||'%'");
			} else {
				strBuffer.append(" AND ALARMUNIT like '");
				strBuffer.append(jgid.substring(0, 4));
				strBuffer.append("%'");
			}
		} else if (this.jgbh.length() == 6) {
			// 大队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||ALARMUNIT||'%'");
			} else {
				strBuffer.append(" AND ALARMUNIT = '");
				strBuffer.append(jgid);
				strBuffer.append("' ");
			}
		}
		strBuffer.append(")");
		strBuffer.append(" GROUP BY ALARMUNIT, PART");
		strBuffer.append(" ORDER BY ALARMUNIT, PART");

		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getSeasonOneInfo()查询数据时SQL错误： "
					+ strBuffer);
		}
		return temp;
	}

	/**
	 * 获取选定年度上下半年数据<br>
	 * 
	 * @return
	 */
	public Object[][] getHalfYearInfo() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" SELECT f_get_dept(ALARMUNIT), PART, COUNT(*)");
		strBuffer.append(" FROM (SELECT ALARMUNIT,");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '" + this.year
				+ "-01' AND");
		strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
				+ "-06' THEN");
		strBuffer.append(" '1'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(ALARMTIME, 'yyyy-mm') >= '" + this.year
				+ "-07' AND");
		strBuffer.append(" TO_CHAR(ALARMTIME, 'yyyy-mm') <= '" + this.year
				+ "-12' THEN");
		strBuffer.append(" '2'");
		strBuffer.append(" END) END) PART,");
		strBuffer.append(" ALARMTIME");
		strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD");
		strBuffer.append(" WHERE TO_CHAR(ALARMTIME, 'yyyy') = '");
		strBuffer.append(this.year);
		strBuffer.append("'");
		if (this.jgbh.length() == 2) {
			// 总队时检索单位的设置
			if ("1".equals(departType)) {
				// 统计单位"全省统计"时的处理
				return getAllTotalInfo();
			} else if ("2".equals(departType)) {
				// 统计单位"支队统计"时的处理
				return getZdSearchInfo();
			} else if ("3".equals(departType)) {
				// 统计单位"大队统计"时的处理
				if ((departIds != null) && (!"".equals(departIds))) {
					strBuffer.append(" AND '" + departIds
							+ "' like '%'||ALARMUNIT||'%'");
				} else {
					strBuffer.append(" ");
				}
			}
		} else if (this.jgbh.length() == 4) {
			// 支队统计时全市统计时的处理
			if ("4".equals(departType)) {
				return getZdFullCityInfo();
			}
			// 支队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||ALARMUNIT||'%'");
			} else {
				strBuffer.append(" AND ALARMUNIT like '");
				strBuffer.append(jgid.substring(0, 4));
				strBuffer.append("%'");
			}
		} else if (this.jgbh.length() == 6) {
			// 大队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||ALARMUNIT||'%'");
			} else {
				strBuffer.append(" AND ALARMUNIT = '");
				strBuffer.append(jgid);
				strBuffer.append("' ");
			}
		}
		strBuffer.append(")");
		strBuffer.append(" GROUP BY ALARMUNIT, PART");
		strBuffer.append(" ORDER BY ALARMUNIT, PART");
		Object[][] temp = null;
		try {
			temp = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getSeasonOneInfo()查询数据时SQL错误： "
					+ strBuffer);
		}
		return temp;
	}

	/**
	 * 统计单位"支队统计"时统计数据的取得<br/>
	 * 统计单位"支队统计"时统计数据的取得处理
	 * 
	 * @return
	 */
	public Object[][] getInfoByDepartType() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append(" select f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000')");
		strBuffer.append(" FROM T_ATTEMPER_ALARM_ZD a1");
		strBuffer.append(" where TO_CHAR(a1.ALARMTIME, 'yyyy-mm-dd') >= '");
		strBuffer.append(this.starttime);
		strBuffer.append("'");
		strBuffer.append(" AND TO_CHAR(a1.ALARMTIME, 'yyyy-mm-dd') <= '");
		strBuffer.append(this.endtime);
		strBuffer.append("'");
		strBuffer
				.append(" group by f_get_dept(substr(ALARMUNIT, 0, 4) || '00000000')");
		Object[][] tempL = null;
		try {
			tempL = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getInfoByDepartType()查询数据时SQL错误： "
					+ strBuffer.toString());
		}
		Object[][] returnObj = null;
		Object[][] temp = null;
		if (tempL != null) {
			returnObj = new Object[tempL.length][12];
			int sum = 0;
			for (int j = 0; j < tempL.length; j++) {
				// 按日统计时信息的取得处理
				if (timetype == 1) {
					StringBuffer sql = null;
					for (int i = 1; i <= 10; i++) {
						returnObj[j][0] = tempL[j][0];
						sql = new StringBuffer();
						if (i == 1) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName , '01' as modfiy , count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 2) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '02' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 3) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '03' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 4) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '04' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 5) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '05' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 6) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '06' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 7) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '07' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 8) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '08' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 9) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '09' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						} else if (i == 10) {
							sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') as depName, '10' as modfiy ,  count(1),substr(a1.ALARMUNIT, 0, 4) ");
						}
						sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
						if (i == 1) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
						} else if (i == 2) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
						} else if (i == 3) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
						} else if (i == 4) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
						} else if (i == 5) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
						} else if (i == 6) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
						} else if (i == 7) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISARMYACC = '1' AND ");
						} else if (i == 8) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
						} else if (i == 9) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
						} else if (i == 10) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
						}
						sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') >= '");
						sql.append(starttime);
						sql.append("' AND ");
						sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') <= '");
						sql.append(endtime);
						sql.append("' AND '"
								+ tempL[j][0]
								+ "' = f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') ");
						sql.append(" group by substr(a1.ALARMUNIT, 0, 4), f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000') ");
						try {
							temp = DBHandler.getMultiResult(sql.toString());
							if (temp == null) {
								returnObj[j][i] = "0";
								if (i == 10) {
									returnObj[j][11] = sum;
									sum = 0;
								}
							} else {
								returnObj[j][i] = temp[0][2];
								sum = sum + StringHelper.obj2int(temp[0][2], 0);
								if (i == 10) {
									returnObj[j][11] = sum;
									sum = 0;
								}
							}
						} catch (Exception e) {
							logger.error("[重大警情统计]"
									+ "getInfoByAlarmType()查询数据时SQL错误： " + sql);
						}
					}
				}
			}
		}

		return returnObj;
	}

	/**
	 * 取得全市统计时按"报警类型"的统计结果<br/>
	 * 取得全市统计时按"报警类型"的统计结果
	 * 
	 * @return
	 */
	public Object[][] getAllZdInfo() {
		StringBuffer sql = null;
		Object[][] temp = null;
		Object[][] returnObj = new Object[1][12];
		boolean flg = false;
		int sum = 0;
		for (int i = 1; i <= 10; i++) {
			temp = null;
			sql = new StringBuffer();
			if (i == 1) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '01' as modfiy ,  count(1) ");
			} else if (i == 2) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '02' as modfiy ,  count(1) ");
			} else if (i == 3) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '03' as modfiy ,  count(1) ");
			} else if (i == 4) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '04' as modfiy ,  count(1) ");
			} else if (i == 5) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '05' as modfiy ,  count(1) ");
			} else if (i == 6) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '06' as modfiy ,  count(1) ");
			} else if (i == 7) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '07' as modfiy ,  count(1) ");
			} else if (i == 8) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '08' as modfiy ,  count(1) ");
			} else if (i == 9) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '09' as modfiy ,  count(1) ");
			} else if (i == 10) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000'), '09' as modfiy ,  count(1) ");
			}
			sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
			if (i == 1) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
			} else if (i == 2) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
			} else if (i == 3) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
			} else if (i == 4) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
			} else if (i == 5) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
			} else if (i == 6) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
			} else if (i == 7) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISARMYACC = '1' AND ");
			} else if (i == 8) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
			} else if (i == 9) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
			} else if (i == 10) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
			}
			sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') >= '");
			sql.append(starttime);
			sql.append("' AND ");
			sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') <= '");
			sql.append(endtime);
			sql.append("' and substr(a1.ALARMUNIT, 0, 4) = '");
			sql.append(this.jgid.substring(0, 4));
			sql.append("'");
			sql.append(" group by f_get_dept(substr(a1.ALARMUNIT, 0, 4) || '00000000')");
			try {
				temp = DBHandler.getMultiResult(sql.toString());
				if (temp == null) {
					returnObj[0][i] = "0";
					if (i == 10) {
						returnObj[0][11] = sum;
						sum = 0;
					}
				} else {
					flg = true;
					returnObj[0][0] = temp[0][0];
					returnObj[0][i] = temp[0][2];
					sum = sum + StringHelper.obj2int(temp[0][2], 0);
					if (i == 10) {
						returnObj[0][11] = sum;
						sum = 0;
					}
				}
			} catch (Exception e) {
				logger.error("[重大警情统计]" + "getInfoByAlarmType()查询数据时SQL错误： "
						+ sql);
			}
		}
		if (!flg) {
			returnObj = null;
		}

		return returnObj;
	}

	/**
	 * 取得全省统计时按"报警类型"的统计结果<br/>
	 * 取得全省统计时按"报警类型"的统计结果
	 * 
	 * @return
	 */
	public Object[][] getAllZdAlarInfo() {
		StringBuffer sql = null;
		Object[][] temp = null;
		Object[][] returnObj = new Object[1][12];
		boolean flg = false;
		int sum = 0;
		for (int i = 1; i <= 10; i++) {
			temp = null;
			sql = new StringBuffer();
			if (i == 1) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '01' as modfiy ,  count(1) ");
			} else if (i == 2) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '02' as modfiy ,  count(1) ");
			} else if (i == 3) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '03' as modfiy ,  count(1) ");
			} else if (i == 4) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '04' as modfiy ,  count(1) ");
			} else if (i == 5) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '05' as modfiy ,  count(1) ");
			} else if (i == 6) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '06' as modfiy ,  count(1) ");
			} else if (i == 7) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '07' as modfiy ,  count(1) ");
			} else if (i == 8) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '08' as modfiy ,  count(1) ");
			} else if (i == 9) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '09' as modfiy ,  count(1) ");
			} else if (i == 10) {
				sql.append(" select f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000'), '10' as modfiy ,  count(1) ");
			}
			sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
			if (i == 1) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
			} else if (i == 2) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
			} else if (i == 3) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
			} else if (i == 4) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
			} else if (i == 5) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
			} else if (i == 6) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
			} else if (i == 7) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISARMYACC = '1' AND ");
			} else if (i == 8) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
			} else if (i == 9) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
			} else if (i == 10) {
				sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
			}
			sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') >= '");
			sql.append(starttime);
			sql.append("' AND ");
			sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') <= '");
			sql.append(endtime);
			sql.append("'");
			sql.append(" group by f_get_dept(substr(a1.ALARMUNIT, 0, 2) || '0000000000')");
			try {
				temp = DBHandler.getMultiResult(sql.toString());
				if (temp == null) {
					returnObj[0][i] = "0";
					if (i == 10) {
						returnObj[0][11] = sum;
						sum = 0;
					}
				} else {
					flg = true;
					returnObj[0][0] = temp[0][0];
					returnObj[0][i] = temp[0][2];
					sum = sum + StringHelper.obj2int(temp[0][2], 0);
					if (i == 10) {
						returnObj[0][11] = sum;
						sum = 0;
					}
				}
			} catch (Exception e) {
				logger.error("[重大警情统计]" + "getInfoByAlarmType()查询数据时SQL错误： "
						+ sql);
			}
		}
		if (!flg) {
			returnObj = null;
		}

		return returnObj;
	}

	/**
	 * 统计方式“报警类别”时的统计信息的取得<br/>
	 * 统计方式“报警类别”时的统计信息的取得处理
	 * 
	 * @param timetype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 * @return
	 */
	private Object[][] getInfoByAlarmType(String timetype, String starttime,
			String endtime, String startyearId, String startmonthId,
			String year, String departIds, String depNames, String endyearId,
			String endmonthId) {
		Object[][] temp = null;
		Object[][] returnObj = {};
		StringBuffer strBuffer = new StringBuffer();

		if (this.jgbh.length() == 2) {
			// 统计单位"全省统计"时的处理
			if ("1".equals(departType)) {
				return getAllZdAlarInfo();
			}

			// 统计单位"支队统计"时的处理
			if ("2".equals(this.departType)) {
				return getInfoByDepartType();
			}
			// 总队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||a1.ALARMUNIT||'%'");
			} else {
				strBuffer.append("''");
			}
		} else if (this.jgbh.length() == 4) {

			// 统计单位"全市统计"时的处理
			if ("4".equals(this.departType)) {
				return getAllZdInfo();
			}
			// 支队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||a1.ALARMUNIT||'%'");
			} else {
				strBuffer.append(" AND a1.ALARMUNIT like '");
				strBuffer.append(jgid.substring(0, 4));
				strBuffer.append("%'");
			}
		} else if (this.jgbh.length() == 6) {
			// 大队时检索单位的设置
			if ((departIds != null) && (!"".equals(departIds))) {
				strBuffer.append(" AND '" + departIds
						+ "' like '%'||a1.ALARMUNIT||'%'");
			} else {
				strBuffer.append(" AND a1.ALARMUNIT = '");
				strBuffer.append(jgid);
				strBuffer.append("' ");
			}
		}

		String startYm = startyearId + "-" + startmonthId;
		String endYm = endyearId + "-" + endmonthId;
		String sqll = "select f_get_dept(a1.ALARMUNIT) FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ";
		// 按"日"统计时信息的取得处理
		if (timetype.equals("1")) {
			sqll = sqll + " where  TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') >= '"
					+ starttime + "' AND "
					+ " TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') <= '" + endtime
					+ "'" + strBuffer.toString()
					+ " group by f_get_dept(a1.ALARMUNIT)";
		} else { // 按"月"统计时的取得处理
			sqll = sqll + " where  TO_CHAR(a1.ALARMTIME,'yyyy-mm') >= '";
			sqll = sqll + startYm;
			sqll = sqll + "' and  TO_CHAR(a1.ALARMTIME,'yyyy-mm') <= '";
			sqll = sqll + endYm;
			sqll = sqll + "'" + strBuffer.toString()
					+ " group by f_get_dept(a1.ALARMUNIT)";
		}
		Object[][] tempL = null;
		try {
			tempL = DBHandler.getMultiResult(sqll.toString());
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "getInfoByAlarmType()查询数据时SQL错误： " + sqll);
		}
		if (tempL == null) {
			return null;
		}
		if (departIds == null || "".equals(departIds)) {
			returnObj = new Object[tempL.length][12];
			int sum = 0;
			for (int j = 0; j < tempL.length; j++) {
				// 按日统计时信息的取得处理
				if (timetype.equals("1")) {
					StringBuffer sql = null;
					for (int i = 1; i <= 10; i++) {
						returnObj[j][0] = tempL[j][0];
						sql = new StringBuffer();
						if (i == 1) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '01' as modfiy ,  count(1) ");
						} else if (i == 2) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '02' as modfiy ,  count(1) ");
						} else if (i == 3) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '03' as modfiy ,  count(1) ");
						} else if (i == 4) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '04' as modfiy ,  count(1) ");
						} else if (i == 5) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '05' as modfiy ,  count(1) ");
						} else if (i == 6) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '06' as modfiy ,  count(1) ");
						} else if (i == 7) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '07' as modfiy ,  count(1) ");
						} else if (i == 8) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '08' as modfiy ,  count(1) ");
						} else if (i == 9) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '09' as modfiy ,  count(1) ");
						} else if (i == 10) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '10' as modfiy ,  count(1) ");
						}
						sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
						if (i == 1) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
						} else if (i == 2) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
						} else if (i == 3) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
						} else if (i == 4) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
						} else if (i == 5) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
						} else if (i == 6) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
						} else if (i == 7) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISARMYACC = '1' AND ");
						} else if (i == 8) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
						} else if (i == 9) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
						} else if (i == 10) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
						}
						sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') >= '");
						sql.append(starttime);
						sql.append("' AND ");
						sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') <= '");
						sql.append(endtime);
						sql.append("' AND '" + tempL[j][0]
								+ "' = f_get_dept(a1.ALARMUNIT) ");
						sql.append(" group by f_get_dept(a1.ALARMUNIT) ");
						sql.append(" order by f_get_dept(a1.ALARMUNIT) ");
						try {
							temp = DBHandler.getMultiResult(sql.toString());
							if (temp == null) {
								returnObj[j][i] = "0";
								if (i == 10) {
									returnObj[j][11] = sum;
									sum = 0;
								}
							} else {
								returnObj[j][i] = temp[0][2];
								sum = sum + StringHelper.obj2int(temp[0][2], 0);
								if (i == 10) {
									returnObj[j][11] = sum;
									sum = 0;
								}
							}
						} catch (Exception e) {
							logger.error("[重大警情统计]"
									+ "getInfoByAlarmType()查询数据时SQL错误： " + sql);
						}
					}

					// 按“月”统计时信息的取得处理
				} else if (timetype.equals("3")) {
					returnObj[j][0] = tempL[j][0];
					StringBuffer sql = null;
					for (int i = 1; i <= 9; i++) {
						sql = new StringBuffer();
						if (i == 1) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '01' as modfiy ,  count(1) ");
						} else if (i == 2) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '02' as modfiy ,  count(1) ");
						} else if (i == 3) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '03' as modfiy ,  count(1) ");
						} else if (i == 4) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '04' as modfiy ,  count(1) ");
						} else if (i == 5) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '05' as modfiy ,  count(1) ");
						} else if (i == 6) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '06' as modfiy ,  count(1) ");
						} else if (i == 7) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '07' as modfiy ,  count(1) ");
						} else if (i == 8) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '08' as modfiy ,  count(1) ");
						} else if (i == 9) {
							sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '09' as modfiy ,  count(1) ");
						}
						sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
						if (i == 1) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
						} else if (i == 2) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
						} else if (i == 3) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
						} else if (i == 4) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
						} else if (i == 5) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
						} else if (i == 6) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
						} else if (i == 7) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
						} else if (i == 8) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
						} else if (i == 9) {
							sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
						}
						sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm') >= '");
						sql.append(startYm);
						sql.append("' and  TO_CHAR(a1.ALARMTIME,'yyyy-mm') <= '");
						sql.append(endYm);

						sql.append("' AND '" + tempL[j][0]
								+ "' = f_get_dept(a1.ALARMUNIT) ");
						sql.append(" and f_get_dept(a1.ALARMUNIT) = '"
								+ tempL[j][0] + "'");
						sql.append(" group by f_get_dept(a1.ALARMUNIT) ");
						sql.append(" order by f_get_dept(a1.ALARMUNIT) ");
						try {
							temp = DBHandler.getMultiResult(sql.toString());
							if (temp == null) {
								returnObj[j][i] = "0";
								if (i == 9) {
									returnObj[j][10] = sum;
									sum = 0;
								}
							} else {
								returnObj[j][i] = temp[0][2];
								sum = sum + StringHelper.obj2int(temp[0][2], 0);
								if (i == 9) {
									returnObj[j][10] = sum;
									sum = 0;
								}
							}
						} catch (Exception e) {
							logger.error("[重大警情统计]"
									+ "getInfoByAlarmType()查询数据时SQL错误： " + sql);
						}
					}
					// 按周统计时信息的取得处理
				}
			}
		} else {
			String[] departId = departIds.split(";");
			String[] depName = depNames.split("；");
			int lengthLeft = 0;
			for (int k = 0; k < depName.length; k++) {
				String dep = depName[k];
				if (CommonUtility.checkObj(dep, tempL)) {
					lengthLeft = lengthLeft + 1;
				}
			}

			returnObj = new Object[lengthLeft][12];
			int count = 0;
			int sum = 0;
			for (int k = 0; k < depName.length; k++) {
				String dep = depName[k];
				if (CommonUtility.checkObj(dep, tempL)) {
					returnObj[count][0] = dep;
					// 按日统计时信息的取得处理
					if (timetype.equals("1")) {
						StringBuffer sql = null;
						for (int i = 1; i <= 9; i++) {
							sql = new StringBuffer();
							if (i == 1) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '01' as modfiy ,  count(1) ");
							} else if (i == 2) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '02' as modfiy ,  count(1) ");
							} else if (i == 3) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '03' as modfiy ,  count(1) ");
							} else if (i == 4) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '04' as modfiy ,  count(1) ");
							} else if (i == 5) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '05' as modfiy ,  count(1) ");
							} else if (i == 6) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '06' as modfiy ,  count(1) ");
							} else if (i == 7) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '07' as modfiy ,  count(1) ");
							} else if (i == 8) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '08' as modfiy ,  count(1) ");
							} else if (i == 9) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '09' as modfiy ,  count(1) ");
							} else if (i == 10) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName, '10' as modfiy ,  count(1) ");
							}
							sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
							if (i == 1) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
							} else if (i == 2) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
							} else if (i == 3) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
							} else if (i == 4) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
							} else if (i == 5) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
							} else if (i == 6) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
							} else if (i == 7) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISARMYACC = '1' AND ");
							} else if (i == 8) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
							} else if (i == 9) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
							} else if (i == 10) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
							}
							sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') >= '");
							sql.append(starttime);
							sql.append("' AND ");
							sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm-dd') <= '");
							sql.append(endtime);
							if ((departIds != null) && (!"".equals(departIds))) {
								sql.append("' AND '" + dep
										+ "' = f_get_dept(a1.ALARMUNIT) ");
							} else {
								sql.append("' ");
							}
							sql.append(" group by f_get_dept(a1.ALARMUNIT) ");
							sql.append(" order by f_get_dept(a1.ALARMUNIT) ");
							try {
								temp = DBHandler.getMultiResult(sql.toString());
								if (temp == null) {
									returnObj[count][i] = "0";
									if (i == 10) {
										returnObj[count][11] = sum;
										sum = 0;
										count = count + 1;
									}
								} else {
									returnObj[count][i] = temp[0][2];
									sum = sum
											+ StringHelper.obj2int(temp[0][2],
													0);
									if (i == 10) {
										returnObj[count][11] = sum;
										sum = 0;
										count = count + 1;
									}
								}
							} catch (Exception e) {
								logger.error("[重大警情统计]"
										+ "getInfoByAlarmType()查询数据时SQL错误： "
										+ sql);
							}
						}
						// 按“月”统计时信息的取得处理
					} else if (timetype.equals("3")) {
						StringBuffer sql = null;
						for (int i = 1; i <= 9; i++) {
							sql = new StringBuffer();
							if (i == 1) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '01' as modfiy ,  count(1) ");
							} else if (i == 2) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '02' as modfiy ,  count(1) ");
							} else if (i == 3) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '03' as modfiy ,  count(1) ");
							} else if (i == 4) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '04' as modfiy ,  count(1) ");
							} else if (i == 5) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '05' as modfiy ,  count(1) ");
							} else if (i == 6) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '06' as modfiy ,  count(1) ");
							} else if (i == 7) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '07' as modfiy ,  count(1) ");
							} else if (i == 8) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '08' as modfiy ,  count(1) ");
							} else if (i == 9) {
								sql.append(" select f_get_dept(a1.ALARMUNIT) as depName , '09' as modfiy ,  count(1) ");
							}
							sql.append(" FROM T_ATTEMPER_ALARM_ZD a1, T_ATTEMPER_ACCIDENT_ZD a2 ");
							if (i == 1) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.deathpersoncount >= '3' AND ");
							} else if (i == 2) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISBUS = '1' AND ");
							} else if (i == 3) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISSCHOOLBUS = '1' AND ");
							} else if (i == 4) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISDANAGERCAR = '1' AND ");
							} else if (i == 5) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISFOREIGNAFFAIRS = '1' AND ");
							} else if (i == 6) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISPOLICE = '1' AND ");
							} else if (i == 7) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISMASSESCASE = '1' AND ");
							} else if (i == 8) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISCONGESTION = '1' AND ");
							} else if (i == 9) {
								sql.append(" where a1.alarmid = a2.alarmid AND a2.ISOTHERSITEM = '1' AND ");
							}
							sql.append(" TO_CHAR(a1.ALARMTIME,'yyyy-mm') >= '");
							sql.append(startYm);
							sql.append("' and  TO_CHAR(a1.ALARMTIME,'yyyy-mm') <= '");
							sql.append(endYm);
							if ((departIds != null) && (!"".equals(departIds))) {
								sql.append("' AND '" + dep
										+ "' = f_get_dept(a1.ALARMUNIT) ");
							} else {
								sql.append("' ");
							}
							sql.append(" group by f_get_dept(a1.ALARMUNIT) ");
							sql.append(" order by f_get_dept(a1.ALARMUNIT) ");
							try {
								temp = DBHandler.getMultiResult(sql.toString());
								if (temp == null) {
									returnObj[count][i] = "0";
									if (i == 9) {
										returnObj[count][10] = sum;
										sum = 0;
										count = count + 1;
									}
								} else {
									returnObj[count][i] = temp[0][2];
									sum = sum
											+ StringHelper.obj2int(temp[0][2],
													0);
									if (i == 9) {
										returnObj[count][10] = sum;
										sum = 0;
										count = count + 1;
									}
								}
							} catch (Exception e) {
								logger.error("[重大警情统计]"
										+ "getInfoByAlarmType()查询数据时SQL错误： "
										+ sql);
							}
						}
						// 按周统计时信息的取得处理
					}
				}
			}
		}
		System.out.println(returnObj);
		return returnObj;

	}

	/**
	 * 检测对象数组中是否有指定的字符串<br/>
	 * 
	 * @param depName
	 * @param tempL
	 * @return
	 */
	public static boolean checkObj(String depName, Object[][] tempL) {
		for (int i = 0; i < tempL.length; i++) {
			if (depName.equals(tempL[i][0])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算每月中周的起止时间<br/>
	 * 计算每月中周的起止时间的处理
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public ArrayList getWeek(String year, String month) {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, Integer.parseInt(year));
		ca.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		int ina = (ca.get(Calendar.DAY_OF_WEEK) + 4) % 7;
		ca.set(Calendar.DAY_OF_MONTH, -ina);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		/* 存放周时间段 */
		ArrayList<String> weekList = new ArrayList<String>();

		date.setTime(ca.getTimeInMillis());
		weekList.add(sdf.format(date));
		for (int i = 0; i < 6; i++) {
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + 1);
			if (ca.get(Calendar.YEAR) > Integer.parseInt(year)
					|| (ca.get(Calendar.YEAR) == Integer.parseInt(year) && (ca
							.get(Calendar.MONTH) + 1) > Integer.parseInt(month))) {
				break;
			}
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + 5);
			date.setTime(ca.getTimeInMillis());
			weekList.add(sdf.format(date));
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + 1);
			date.setTime(ca.getTimeInMillis());
			weekList.add(sdf.format(date));
		}
		weekList.remove(weekList.size() - 1);
		return weekList;
	}

	/*
	*//**
	 * 统计类型"时间类型"时的处理<br>
	 * 统计类型"时间类型"时的数据format处理
	 * 
	 * @param arr
	 * @param stattype
	 * @return
	 */
	/*
	 * public static Object [][] dataFormatSort (Object[][] arr,String stattype)
	 * { String tempStr = ""; int cnt = 0; String compare = ""; for(int k=0;
	 * k<arr.length; k++) { if(k==0) { cnt = 1; compare =
	 * StringHelper.obj2str(arr[0][0]); } if(compare.equals(arr[k][0])) {
	 * continue; } else { cnt = cnt + 1; compare =
	 * StringHelper.obj2str(arr[k][0]); } } Object [][] returnObj = new Object
	 * [cnt][12]; if(arr != null) { int count = 0; for (int i = 0; i <
	 * arr.length; i++) { // 第一条数据时的处理 if (i == 0) { tempStr =
	 * StringHelper.obj2str(arr[0][0]); returnObj[0][0] = arr[0][0]; } if
	 * (tempStr.equals(arr[i][0])) { returnObj[count][0] = arr[i][0];
	 * returnObj[count][StringHelper.obj2int(arr[i][1], 0)] = arr[i][2]; } else
	 * { count = count + 1; tempStr = StringHelper.obj2str(arr[i][0]);
	 * returnObj[count][0] = arr[i][0];
	 * returnObj[count][StringHelper.obj2int(arr[i][1], 0)] = arr[i][2]; } } }
	 * 
	 * return returnObj; } public static void main (String srgs []) { Object[][]
	 * arr = {{"广州东山大队", "01", 2},{"广州东山大队1", "02", 3},{"广州东山大队", "03",
	 * 2},{"广州东山大队1", "01", 4},{"广州东山大队5", "01", 6},{"广州东山大队6", "01", 5}};
	 * Arrays.sort(arr, new ArrayComparator()); System.out.println(arr); arr =
	 * CommonUtility.dataFormatSort(arr, ""); System.out.println(arr); }
	 */
	/**
	 * 将结果集转成二维数组并对结果集排序<br/>
	 * 将结果集转成二维数组并对结果集排序,处理后的数组长度
	 * 
	 * @param arr
	 * @return
	 */
	private Object[][] dataDispose(Object[][] arr, String stattype) {
		int tempTimeType = this.timetype;// 统计时间方式

		if (arr == null) {
			return null;
		}
		String[] data = new String[length2];
		ArrayList list = new ArrayList();
		String tempLH = "";
		int sum = 0;

		int tempIndex = 1;// 数组下标序号

		// 统计方式"时间"时的处理
		if ("1".equals(stattype)) {
			for (int i = 0; i < arr.length; i++) {
				if (i == 0) {
					tempLH = StringHelper.obj2str(arr[0][0]);
				}
				if (arr[i][0] == null) {
					continue;// 如果统计方式为空继续退出本次循环
				}
				switch (tempTimeType) {
				case 7:
					tempIndex = StringHelper.obj2int(arr[i][1], 0) + 1;
					break;// 小时
				case 1:
					tempIndex = StringHelper.obj2int(
							this.dateMap.get(StringHelper.obj2str(arr[i][1])),
							0);
					break;// 日
				case 2:
					tempIndex = StringHelper.obj2int(arr[i][1], 0);
					break;// 周
				case 3:
					tempIndex = StringHelper.obj2int(
							this.dateMap.get(StringHelper.obj2str(arr[i][1])),
							0);// 月
					break;// 月
				case 4:
					tempIndex = StringHelper.obj2int(arr[i][1], 0);// 季度
					break;// 季度
				case 5:
					tempIndex = StringHelper.obj2int(arr[i][1], 0);// 半年
					break;// 半年
				case 6:
					tempIndex = StringHelper.obj2int(
							this.dateMap.get(StringHelper.obj2str(arr[i][1])),
							0);// 年
					break;// 年
				}
				if (tempLH.equals(arr[i][0])) {
					sum = sum + StringHelper.obj2int(arr[i][2], 0);
					data[tempIndex] = StringHelper.obj2str(arr[i][2]);
					if ("1".equals(stattype)) {
						data[0] = StringHelper.obj2str(arr[i][0]);
					} else {
						data[0] = keyValueMap.get(StringHelper
								.obj2str(arr[i][0]));
					}
				} else {
					data[length2 - 1] = StringHelper.obj2str(sum);
					tempLH = StringHelper.obj2str(arr[i][0]);
					list.add(data);

					data = new String[length2];
					sum = StringHelper.obj2int(arr[i][2], 0);
					if ("1".equals(stattype)) {
						data[0] = StringHelper.obj2str(arr[i][0]);
					} else {
						data[0] = keyValueMap.get(StringHelper
								.obj2str(arr[i][0]));
					}
					data[tempIndex] = StringHelper.obj2str(arr[i][2]);
				}
				if (i == arr.length - 1) {
					data[length2 - 1] = StringHelper.obj2str(sum);
					list.add(data);
				}
			}

		}
		Object[][] tempob = new Object[list.size()][length2];
		Object[] tt = null;
		for (int i = 0; i < list.size(); i++) {
			tt = (Object[]) list.get(i);
			for (int j = 0; j < tt.length; j++) {
				tempob[i][j] = tt[j];
			}
		}
		Arrays.sort(tempob, new ArrayComparator());
		tempob = subArr(tempob);
		return tempob;
	}

	/**
	 * 将二维数组转成xml字符串<br/>
	 * 将二维数组转成xml字符串处理
	 * 
	 * @param arr
	 * @return
	 */
	private String bulidXml(Object[][] arr) {
		StringBuffer bf = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?>");
		bf.append("<rows>");
		if (header != null) {
			bf.append("<row>");
			for (int i = 0; i < header.length; i++) {
				bf.append("<col>");
				bf.append(header[i]);
				bf.append("</col>");
			}
			bf.append("</row>");
		}
		StringBuffer sqlTemp = new StringBuffer("");
		try {
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					bf.append("<row>");
					for (int j = 0; j < arr[i].length; j++) {
						bf.append("<col>");
						bf.append(StringHelper.obj2str(arr[i][j], "0"));
						bf.append("</col>");
					}
					bf.append("</row>");
				}
			}
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "bulidXml()错误： " + sqlTemp);
			e.printStackTrace();
		}
		bf.append("</rows>");
		return bf.toString();
	}

	/**
	 * 截取数组并纵向求和<br/>
	 * 截取数组并纵向求和的处理
	 * 
	 * @param arr
	 * @return
	 */
	private Object[][] subArr(Object[][] arr) {
		try {
			if (arr == null) {
				return null;
			}
			Object[][] temp = null;
			if (arr.length <= 20) {// 数据少于或等于20条/
				temp = new Object[arr.length + 1][length2];
				/**
				 * 复制
				 */
				for (int i = 0; i < arr.length; i++) {
					for (int j = 0; j < length2; j++) {
						temp[i][j] = arr[i][j];
					}
				}
				/**
				 * end
				 */
				/**
				 * 对列求和
				 */
				int sumTemp[] = new int[length2];
				for (int m = 0; m < length2; m++) {
					sumTemp[m] = 0;
				}
				for (int k = 0; k < temp.length; k++) {
					for (int n = 1; n < length2; n++) {
						sumTemp[n] = sumTemp[n]
								+ StringHelper.obj2int(temp[k][n], 0);
					}
				}
				for (int w = 1; w < length2; w++) {
					temp[temp.length - 1][w] = sumTemp[w];
				}
				temp[temp.length - 1][0] = "小计";
				/**
				 * end
				 */
			} else {// 数据大于20条/
				temp = new Object[22][length2];
				/**
				 * 复制前20条
				 */
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < length2; j++) {
						temp[i][j] = arr[i][j];
					}
				}
				/**
				 * end
				 */
				/**
				 * 将20条之后的记录合并
				 */
				int sumTemp[] = new int[length2];
				for (int m = 1; m < length2; m++) {
					sumTemp[m] = 0;
				}
				for (int k = 20; k < arr.length; k++) {
					for (int n = 1; n < length2; n++) {
						sumTemp[n] = sumTemp[n]
								+ StringHelper.obj2int(arr[k][n], 0);
					}
				}
				for (int w = 1; w < length2; w++) {
					temp[20][w] = sumTemp[w];
				}
				temp[20][0] = "其他";

				/**
				 * end
				 */

				/**
				 * 对列求和
				 */
				for (int m = 0; m < length2; m++) {
					sumTemp[m] = 0;
				}
				for (int k = 0; k < temp.length; k++) {
					for (int n = 1; n < length2; n++) {
						sumTemp[n] = sumTemp[n]
								+ StringHelper.obj2int(temp[k][n], 0);
					}
				}
				for (int w = 1; w < length2; w++) {
					temp[21][w] = sumTemp[w];
				}
				temp[21][0] = "小计";
				/**
				 * end
				 */
			}
			return temp;
		} catch (Exception e) {
			logger.error("[重大警情统计]" + "subArr()错误： \n");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 计算数组第2维长度并生成表头<br/>
	 * 计算数组第2维长度并生成表头的处理
	 * 
	 * @param timetype
	 * @param stattype
	 * @param starttime
	 * @param endtime
	 * @param monthYear
	 * @param monthMonth
	 * @param year
	 */
	private void analyzeLength2AndHeader(String timetype, String stattype,
			String starttime, String endtime, String startyearId,
			String startmonthId, String year, String departIds,
			String endyearId, String endmonthId) {
		if (timetype == null) {
			return;
		}
		if (timetype.equals("7")) {// 小时
			length2 = 26;
			String head[] = { "", "0时", "1时", "2时", "3时", "4时", "5时", "6时",
					"7时", "8时", "9时", "10时", "11时", "12时", "13时", "14时", "15时",
					"16时", "17时", "18时", "19时", "20时", "21时", "22时", "23时",
					"小计" };
			header = head;
		} else if (timetype.equals("3")) {// 月
			String startmonth = startyearId + "-" + startmonthId;
			String endmonth = endyearId + "-" + endmonthId;

			startmonth += "-01";
			// endtime += "-01";
			dateMap = new HashMap();
			if (startmonth.equals(endmonth + "-01")) {
				dateMap.put(startmonth.substring(0, 7), 1);
				length2 = dateMap.size() + 2;
				header = new String[length2];
				header[0] = "";
				header[1] = startmonth.substring(0, 7);
				header[2] = "小计";
				return;
			}
			ArrayList<String> tempList = new ArrayList<String>();
			String strs[] = startmonth.split("-");
			Calendar ca = Calendar.getInstance();
			ca.set(StringHelper.obj2int(strs[0], 0),
					StringHelper.obj2int(strs[1], 0) - 1,
					StringHelper.obj2int(strs[2], 0));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			int i = 1;
			while (!sdf.format(ca.getTime()).equals(endmonth)) {
				dateMap.put(sdf.format(ca.getTime()), i);
				tempList.add(sdf.format(ca.getTime()));
				i++;
				ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + 1);
			}
			dateMap.put(endmonth, i);
			tempList.add(endmonth);
			length2 = dateMap.size() + 2;
			header = new String[length2];
			header[0] = "";
			try {
				for (int j = 1; j < length2 - 1; j++) {
					header[j] = tempList.get(j - 1);
				}
			} catch (Exception e) {
				logger.error("[投诉统计]" + "analyzeLength2AndHeader()错误： \n");
				e.printStackTrace();
			}
			header[length2 - 1] = "小计";

			// length2 =14;
			// String head[] =
			// {"","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月","小计"};
			// header = head;
		} else if (timetype.equals("1")) {// 日
			dateMap = new HashMap();
			if (starttime.equals(endtime)) {
				dateMap.put(starttime, 1);
				length2 = dateMap.size() + 2;
				header = new String[length2];
				header[0] = "";
				header[1] = starttime;
				header[2] = "小计";
				return;
			}
			ArrayList<String> tempList = new ArrayList<String>();
			String strs[] = starttime.split("-");
			Calendar ca = Calendar.getInstance();
			ca.set(StringHelper.obj2int(strs[0], 0),
					StringHelper.obj2int(strs[1], 0) - 1,
					StringHelper.obj2int(strs[2], 0));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int i = 1;
			while (!sdf.format(ca.getTime()).equals(endtime)) {
				dateMap.put(sdf.format(ca.getTime()), i);
				tempList.add(sdf.format(ca.getTime()));
				i++;
				ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + 1);
			}
			dateMap.put(endtime, i);
			tempList.add(endtime);
			length2 = dateMap.size() + 2;
			header = new String[length2];
			header[0] = "";
			try {
				for (int j = 1; j < length2 - 1; j++) {
					header[j] = tempList.get(j - 1);
				}
			} catch (Exception e) {
				logger.error("[警情统计]" + "analyzeLength2AndHeader()错误： \n");
				e.printStackTrace();
			}
			header[length2 - 1] = "小计";
		} else if (timetype.equals("2")) {// 周
			weekList = getWeek(year, this.monthSelect);
			length2 = (weekList.size() / 2 + 2);
			header = new String[length2];
			header[0] = "";
			header[1] = "第一周" + weekList.get(0).substring(5, 7) + "月"
					+ weekList.get(0).substring(8, 10) + "日" + "到"
					+ weekList.get(1).substring(5, 7) + "月"
					+ weekList.get(1).substring(8, 10) + "日";
			header[2] = "第二周" + weekList.get(2).substring(5, 7) + "月"
					+ weekList.get(2).substring(8, 10) + "日" + "到"
					+ weekList.get(3).substring(5, 7) + "月"
					+ weekList.get(3).substring(8, 10) + "日";
			header[3] = "第三周" + weekList.get(4).substring(5, 7) + "月"
					+ weekList.get(4).substring(8, 10) + "日" + "到"
					+ weekList.get(5).substring(5, 7) + "月"
					+ weekList.get(5).substring(8, 10) + "日";
			header[4] = "第四周" + weekList.get(6).substring(5, 7) + "月"
					+ weekList.get(6).substring(8, 10) + "日" + "到"
					+ weekList.get(7).substring(5, 7) + "月"
					+ weekList.get(7).substring(8, 10) + "日";
			if (length2 == 7) {
				header[5] = "第五周" + weekList.get(8).substring(5, 7) + "月"
						+ weekList.get(8).substring(8, 10) + "日" + "到"
						+ weekList.get(9).substring(5, 7) + "月"
						+ weekList.get(9).substring(8, 10) + "日";
				header[6] = "小计";
			} else if (length2 == 8) {
				header[5] = "第五周" + weekList.get(8).substring(5, 7) + "月"
						+ weekList.get(8).substring(8, 10) + "日" + "到"
						+ weekList.get(9).substring(5, 7) + "月"
						+ weekList.get(9).substring(8, 10) + "日";
				header[6] = "第六周" + weekList.get(10).substring(5, 7) + "月"
						+ weekList.get(10).substring(8, 10) + "日" + "到"
						+ weekList.get(11).substring(5, 7) + "月"
						+ weekList.get(11).substring(8, 10) + "日";
				header[7] = "小计";
			} else {
				header[5] = "小计";
			}
		} else if (timetype.equals("4")) {// 季度
			length2 = 6;
			header = new String[length2];
			header[0] = "";
			header[1] = "第一季度";
			header[2] = "第二季度";
			header[3] = "第三季度";
			header[4] = "第四季度";
			header[5] = "小计";
		} else if (timetype.equals("5")) {// 半年
			length2 = 4;
			header = new String[length2];
			header[0] = "";
			header[1] = "上半年";
			header[2] = "下半年";
			header[3] = "小计";
		} else if (timetype.equals("6")) {// 年
			int startY = Integer.valueOf(startyearId);
			int endY = Integer.valueOf(endyearId);
			dateMap = new HashMap();
			length2 = (endY - startY) + 1 + 2;
			header = new String[length2];
			int count = 1;
			for (int i = 0; i < length2; i++) {
				if (i == 0) {
					header[i] = "";
				} else if (i > 0 && (startY <= endY)) {
					dateMap.put(String.valueOf(startY), count);
					header[i] = startY + " 年";
					count = count + 1;
					startY = startY + 1;
				} else {
					header[i] = "小计";
				}
			}
		}

		this.keyValueMap.put("01", "一次死亡3人以上交通事故");
		this.keyValueMap.put("02", "营运大客车事故");
		this.keyValueMap.put("03", "校车事故");
		this.keyValueMap.put("04", "危化品运输车交通事故");
		this.keyValueMap.put("05", "涉港澳台及涉外事故");
		this.keyValueMap.put("06", "涉警交通事故");
		this.keyValueMap.put("07", "涉军交通事故");
		this.keyValueMap.put("08", "涉及群体性事件");
		this.keyValueMap.put("09", "高速公路国省道交通中断");
		this.keyValueMap.put("10", "其他");

		// 统计方式"报警类别"时
		if ("2".equals(stattype)) {
			length2 = 12;
			String head[] = { "", "一次死亡3人以上交通事故", "营运大客车事故", "校车事故",
					"危化品运输车交通事故", "涉港澳台及涉外事故", "涉警交通事故", "涉军交通事故", "涉及群体性事件",
					"高速公路国省道交通中断", "其他", "小计" };
			header = head;
		}
	}

	/**
	 * 合并两个2纬数组<br/>
	 * 合并两个2纬数组的处理
	 * 
	 * @param os1
	 * @param os2
	 * @return
	 */
	public static Object[][] unite(Object[][] os1, Object[][] os2) {
		List<Object[]> list = new ArrayList<Object[]>(
				Arrays.<Object[]> asList(os1));
		list.addAll(Arrays.<Object[]> asList(os2));
		return list.toArray(os1);
	}

	public boolean wirteToExcel(HttpServletResponse response, String tabHeader,
			String fileName, String sheetName, Object[][] oResult,
			String titleName) throws Exception {
		boolean bResult = false; // 执行结果

		List lstTabHeader = StringUtil.divide(tabHeader, ","); // 表头转换为List
		short columnCount = (short) lstTabHeader.size(); // 表头列数
		int titol = 0;
		int titol1 = 0;

		if (lstTabHeader == null || columnCount == 0) // 没有表头
		{
			return false;
		}
		fileName = new String(fileName.getBytes(), "ISO8859_1");
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.setHeader("content-disposition",
					"attachment;filename=exportExcel.xls");
			OutputStream out = new BufferedOutputStream(
					response.getOutputStream());

			// Object[][] oResult = DBHandler.getMultiResult(tabData);
			if (oResult == null || oResult.length == 0
					|| oResult[0].length == 0) // 没统计结果
			{
				return false;
			}

			bResult = executeToExcel(oResult, out, sheetName, titleName,
					columnCount, lstTabHeader);
			out.close();

		} catch (Exception e) {
			Console.infoprintln("wirteToExcel Fail:" + e.getMessage());
			return false;
		}

		return bResult;

	}

	public boolean wirteToExcelBySegRatio(HttpServletResponse response,
			String tabHeader, String fileName, String sheetName,
			Object[][] oResult, String titleName) throws Exception {
		boolean bResult = false; // 执行结果

		List lstTabHeader = StringUtil.divide(tabHeader, ","); // 表头转换为List
		short columnCount = (short) lstTabHeader.size(); // 表头列数
		int titol = 0;
		int titol1 = 0;

		if (lstTabHeader == null || columnCount == 0) // 没有表头
		{
			return false;
		}
		fileName = new String(fileName.getBytes(), "ISO8859_1");
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.setHeader("content-disposition",
					"attachment;filename=exportExcel.xls");
			OutputStream out = new BufferedOutputStream(
					response.getOutputStream());

			// Object[][] oResult = DBHandler.getMultiResult(tabData);
			if (oResult == null || oResult.length == 0
					|| oResult[0].length == 0) // 没统计结果
			{
				return false;
			}

			bResult = executeToExcelBySegRatio(oResult, out, sheetName,
					titleName, columnCount, lstTabHeader);
			out.close();

		} catch (Exception e) {
			Console.infoprintln("wirteToExcel Fail:" + e.getMessage());
			return false;
		}

		return bResult;

	}

	/**
	 * @版本号：1.0
	 * @函数说明：数据转换执行函数
	 * @参数：oResult 结果集. out OutputStream. sheetName Sheet名 titleName 标题名
	 *             lstTabHeader 数值列表表头名称。
	 * @返回：是否加载成功,true-成功;false-失败.
	 * @作者：Jason
	 * @创建日期：2010-1-25
	 * */
	private boolean executeToExcel(Object[][] oResult, OutputStream out,
			String sheetName, String titleName, short columnCount,
			List lstTabHeader) throws Exception {
		final short ColumnWidth = 4000; // 列宽

		try {

			// 创建HSSFWorkBook对象
			HSSFWorkbook hwb = new HSSFWorkbook();

			// 创建HSSFSheet对象
			HSSFSheet sheet = hwb.createSheet();
			hwb.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);

			// 将日期和页数写入页脚
			HSSFFooter footer = sheet.getFooter();
			footer.setCenter("Page " + HSSFFooter.page() + " of "
					+ HSSFFooter.numPages());
			footer.setRight(HSSFFooter.font("Stencil-Normal", "Italic")
					+ HSSFFooter.fontSize((short) 10) + HSSFFooter.date());

			// 创建标题字体
			HSSFFont headerFont = hwb.createFont();
			headerFont.setFontHeightInPoints((short) 16);
			headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// 标题样式
			HSSFCellStyle headerStyle = hwb.createCellStyle();
			headerStyle.setFont(headerFont);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 用工作表第一行做标题
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 500);
			HSSFCell cel = row.createCell((short) 0);
			cel.setEncoding(HSSFCell.ENCODING_UTF_16);
			cel.setCellStyle(headerStyle);
			cel.setCellValue(titleName);

			// 定义字体
			HSSFFont columnNameFont = hwb.createFont();
			columnNameFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// 列名单元格样式
			HSSFCellStyle style = hwb.createCellStyle();
			style.setFont(columnNameFont);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 工作表第二行作列名
			row = sheet.createRow(1);

			// 将列名写入第二行的单元格中,合并标题单元格
			sheet.addMergedRegion(new Region(0, (short) 0, 0,
					(short) (columnCount - 1)));
			String strColumnName = new String("");
			for (short i = 0; i < columnCount; i++) {
				strColumnName = (String) lstTabHeader.get(i);
				if (strColumnName.equals("1")) {
					sheet.setColumnWidth((short) i, (short) 0); // 设置列宽
				} else {
					sheet.setColumnWidth((short) i, (short) ColumnWidth); // 设置列宽
				}
				cel = row.createCell(i);
				cel.setCellStyle(style);
				cel.setEncoding(HSSFCell.ENCODING_UTF_16);
				cel.setCellValue(strColumnName);
			}

			// 数据列单元格样式
			HSSFCellStyle dbStyle = hwb.createCellStyle();
			dbStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			dbStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			dbStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			dbStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

			// 数据类型为字符的样式
			HSSFCellStyle strStyle = hwb.createCellStyle();
			strStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			strStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			strStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			strStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			strStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			strStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

			// 向sheet中写入数据列
			if (oResult != null) {
				for (int i = 0; i < oResult.length; i++) {
					row = sheet.createRow(i + 2);
					for (short j = 0; j < oResult[i].length; j++) {
						cel = row.createCell(j);
						// 按照字符处理
						cel.setEncoding(HSSFCell.ENCODING_UTF_16); // 支持中文
						cel.setCellStyle(dbStyle);// 设置单元格样式
						cel.setCellValue(StringHelper
								.obj2str(oResult[i][j], "")); // 设置单元格值
					}
				}
			}
			hwb.write(out);
		} catch (Exception e) {
			Console.infoprintln("executeToExcel Fail:" + e.getMessage());
			return false;
		}
		return true;
	}

	private boolean executeToExcelBySegRatio(Object[][] oResult,
			OutputStream out, String sheetName, String titleName,
			short columnCount, List lstTabHeader) throws Exception {
		final short ColumnWidth = 4000; // 列宽

		try {

			// 创建HSSFWorkBook对象
			HSSFWorkbook hwb = new HSSFWorkbook();

			// 创建HSSFSheet对象
			HSSFSheet sheet = hwb.createSheet();
			hwb.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);

			// 将日期和页数写入页脚
			HSSFFooter footer = sheet.getFooter();
			footer.setCenter("Page " + HSSFFooter.page() + " of "
					+ HSSFFooter.numPages());
			footer.setRight(HSSFFooter.font("Stencil-Normal", "Italic")
					+ HSSFFooter.fontSize((short) 10) + HSSFFooter.date());

			// 创建标题字体
			HSSFFont headerFont = hwb.createFont();
			headerFont.setFontHeightInPoints((short) 16);
			headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// 标题样式
			HSSFCellStyle headerStyle = hwb.createCellStyle();
			headerStyle.setFont(headerFont);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 用工作表第一行做标题
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 500);
			HSSFCell cel = row.createCell((short) 0);
			cel.setEncoding(HSSFCell.ENCODING_UTF_16);
			cel.setCellStyle(headerStyle);
			cel.setCellValue(titleName);

			// 定义字体
			HSSFFont columnNameFont = hwb.createFont();
			columnNameFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// 列名单元格样式
			HSSFCellStyle style = hwb.createCellStyle();
			style.setFont(columnNameFont);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			// 工作表第二行作列名
			row = sheet.createRow(1);

			// 将列名写入第二行的单元格中,合并标题单元格
			sheet.addMergedRegion(new Region(0, (short) 0, 0,
					(short) (columnCount - 1)));
			
			sheet.addMergedRegion(new Region(1, (short) 0, 2,
					(short) 0));
			String strColumnName = new String("");
			for (short i = 0; i < columnCount; i++) {
				strColumnName = String.valueOf(lstTabHeader.get(i)).split("#")[0];
				if (strColumnName.equals("1")) {
					sheet.setColumnWidth((short) i, (short) 0); // 设置列宽
				} else {
					sheet.setColumnWidth((short) i, (short) ColumnWidth); // 设置列宽
				}
				cel = row.createCell(i);
				cel.setCellStyle(style);
				cel.setEncoding(HSSFCell.ENCODING_UTF_16);
				cel.setCellValue(strColumnName);
				
				sheet.addMergedRegion(new Region(1, (short) 1, 1,
						(short) 2));
				
				sheet.addMergedRegion(new Region(1, (short) 3, 1,
						(short) 4));
				
				sheet.addMergedRegion(new Region(1, (short) 5, 1,
						(short) 6));
				
				sheet.addMergedRegion(new Region(1, (short) 7, 1,
						(short) 8));
			}
			
			row = sheet.createRow(2);
			for (short i = 0; i < columnCount; i++) {
				if (strColumnName.equals("1")) {
					sheet.setColumnWidth((short) i, (short) 0); // 设置列宽
				} else {
					sheet.setColumnWidth((short) i, (short) ColumnWidth); // 设置列宽
				}
				cel = row.createCell(i);
				cel.setCellStyle(style);
				cel.setEncoding(HSSFCell.ENCODING_UTF_16);
				if(i % 2 == 0) {
					cel.setCellValue("环比增减");
				} else {
					cel.setCellValue("平均流量");
				}
			}

			// 数据列单元格样式
			HSSFCellStyle dbStyle = hwb.createCellStyle();
			dbStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			dbStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			dbStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			dbStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

			// 数据类型为字符的样式
			HSSFCellStyle strStyle = hwb.createCellStyle();
			strStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			strStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			strStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			strStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			strStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			strStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

			// 向sheet中写入数据列
			if (oResult != null) {
				for (int i = 0; i < oResult.length; i++) {
					row = sheet.createRow(i + 3);
					for (short j = 0; j < oResult[i].length; j++) {
						cel = row.createCell(j);
						// 按照字符处理
						cel.setEncoding(HSSFCell.ENCODING_UTF_16); // 支持中文
						cel.setCellStyle(dbStyle);// 设置单元格样式
						cel.setCellValue(StringHelper
								.obj2str(oResult[i][j], "")); // 设置单元格值
					}
				}
			}
			hwb.write(out);
		} catch (Exception e) {
			Console.infoprintln("executeToExcel Fail:" + e.getMessage());
			return false;
		}
		return true;
	}
}
