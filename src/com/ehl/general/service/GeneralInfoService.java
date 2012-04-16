
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.general.service <br> 
	* 文件名称：GeneralInfoService.java <br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1 <br>  
	* 创建日期：2011-12-12 <br>  
	* 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2011-12-12 下午7:00:21<br>  
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-12 下午7:00:21  <br> 
	* 修改备注：   <br>
	*/
package com.ehl.general.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.general.dao.GeneralInfoDao;


	/**   
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.general.service  <br>
 * 类名称：GeneralInfoService  <br> 
 * 文件描述：警情信息概览Service   <br>
 *
 * @see <br>
 * 版本信息：Ver 1.1 <br>
 *********************************<br>
 * 创建人：Vasin  <br> 
 * 创建日期：2011-12-12 下午7:00:21  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2011-12-12 下午7:00:21  <br> 
 * 修改备注：     <br>
 */
//@org.springframework.context.annotation.Scope("prototype")
//@org.springframework.stereotype.Service("generalInfoService")
public class GeneralInfoService{
	
	Logger logger = Logger.getLogger(GeneralInfoService.class);
	
//	@javax.annotation.Resource(name="generalInfoDao")
//	public GeneralInfoDao generalInfoDao;
	
	/**
	 * 
		* 方法名称：getGeneralInfoCount2<br>
		* 方法描述： for jquery jqgird <br>
		* @param params
		* @return<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-16 下午3:50:38 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-16 下午3:50:38  <br> 
		* 修改备注：    <br>
	 */
	public JSONObject getGeneralInfoCount(Map<?, ?> params) {
		JSONObject json = null;
		Object[][] resultObjects = null;
		Map<String, String> map = getParams(params);
		String sSearch = map.get("sSearch");
		map.remove("sSearch");
		map.put("sSearch", "");
		GeneralInfoDao generalInfoDao = new GeneralInfoDao();
		String total = StringHelper.obj2str(generalInfoDao.getCount(getSql(map,true)), "0");
		map.put("total", total);
		if (sSearch != "") {
			map.remove("sSearch");
			map.put("sSearch", sSearch);
			String totalAfterFilter = StringHelper.obj2str(generalInfoDao.getCount(getSql(map,true)),"0");
			map.put("totalBeforeFilter", total);
			map.remove("toatl");
			map.put("total", totalAfterFilter);
		}else {
			map.put("totalBeforeFilter", total);
		}
		if (Integer.parseInt(map.get("total")) > 0) {
			int totalPages = (int) Math.ceil(Double.parseDouble(map.get("total"))/Double.parseDouble(map.get("iDisplayLength")));
			map.put("totalPages", String.valueOf(totalPages));
			if (Integer.parseInt(map.get("iDisplayStart")) > totalPages) {
				map.remove("iDisplayStart");
				map.put("iDisplayStart", String.valueOf(totalPages));
			}
		}
		
		resultObjects = generalInfoDao.getInfo(getSql(map,false));
		json = getJsonObject(resultObjects, map);
		return json;
	}
	
	/**
	 * 
		* 方法名称：getParams<br>
		* 方法描述： 获取参数值，或者设置默认参数 <br>
		* @param params
		* @return Map<String, String><br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-16 下午3:51:16 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-16 下午3:51:16  <br> 
		* 修改备注：    <br>
	 */
	public Map<String, String> getParams(Map<?, ?> params) {
		Map<String, String> map = new HashMap<String, String>();
		if (params != null) {
			Date nowDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String infoType = StringHelper.obj2str(params.get("infoType"), "001024");
			String countBy = StringHelper.obj2str(params.get("countBy"), "alarmregion");
			String startDate = StringHelper.obj2str(params.get("startDate"), sdf.format(nowDate));
			String endDate = StringHelper.obj2str(params.get("endDate"), sdf.format(nowDate));
			int iDisplayLength = StringHelper.obj2int(params.get("iDisplayLength"), 10);
			int iDisplayStart = StringHelper.obj2int(params.get("iDisplayStart"), 0);
			String sortIndex = StringHelper.obj2str(params.get("sortIndex"), "1");
			String sord = StringHelper.obj2str(params.get("sord"), "asc");
			String sSearch = StringHelper.obj2str(params.get("sSearch"), "");
			String ALARMREGIONID = StringHelper.obj2str(params.get("ALARMREGIONID"), "");
			String ROADLEVEL = StringHelper.obj2str(params.get("ROADLEVEL"), "1");
			String ROADID = StringHelper.obj2str(params.get("ROADID"), "");
			String detailType = StringHelper.obj2str(params.get("detailType"), "");
			String detailID = StringHelper.obj2str(params.get("detailID"), "");
			String detailParentID = StringHelper.obj2str(params.get("detailParentID"), "");
			if (iDisplayStart < 0) {
				iDisplayStart = 0;
			}
			map.put("infoType", infoType);
			map.put("countBy", countBy);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("iDisplayLength", String.valueOf(iDisplayLength));
			map.put("iDisplayStart", String.valueOf(iDisplayStart));
			map.put("sortIndex", sortIndex);
			map.put("sord", sord);
			map.put("sSearch", sSearch);
			map.put("ALARMREGIONID", ALARMREGIONID);
			map.put("ROADLEVEL", ROADLEVEL);
			map.put("ROADID", ROADID);
			map.put("detailType", detailType);
			map.put("detailID", detailID);
			map.put("detailParentID", detailParentID);
		}else {
			Date nowDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("infoType", "001024");
			map.put("countBy", "roadid");
			map.put("startDate", sdf.format(nowDate));
			map.put("endDate", sdf.format(nowDate));
			map.put("iDisplayLength", "10");
			map.put("iDisplayStart", "0");
			map.put("sortIndex", "1");
			map.put("sord", "asc");
			map.put("sSearch", "");
			map.put("ALARMREGIONID", "");
			map.put("ROADLEVEL", "1");
			map.put("ROADID", "");
			map.put("detailType", "");
			map.put("detailID", "");
			map.put("detailParentID", "");
		}
		return map;
	}
	
	/**
	 * 
		* 方法名称：getPagingSql<br>
		* 方法描述： 获取分页SQL语句（或者记录数SQL语句） <br>
		* @param params
		* @param isCount
		* @return String<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-18 下午4:39:02 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-18 下午4:39:02  <br> 
		* 修改备注：    <br>
	 */
	public String getSql(Map<String, String> params,boolean isCount) {
		//分页SQL
		String pagingSql = "";
		//最终结果集SQL
		String finalSql = "",finalSelect = ""/*,finalWhere = ""*/,finalFrom = "", finalGroupBy = "",finalOrderBy = "";
		//基础统计结果SQL
		String baseSql = "", baseTableName = "T_ATTEMPER_ALARM",baseSelect = "",baseWhere = "",baseFrom = "", baseGroupBy = ""/*,baseOrderBy = ""*/;
		baseSelect = " SELECT COUNT (*) NUM, TAA.EVENTTYPE ";
		baseFrom = " FROM "+ baseTableName +" TAA ";
		baseWhere = " WHERE 1=1 ";
		baseGroupBy = " GROUP BY TAA.EVENTTYPE ";
		if (params.get("countBy").equalsIgnoreCase("ALARMREGIONID")) {/**按机构统计，包括大队**/
			baseSelect += " ,TAA.ALARMREGIONID COUNT_TYPE";
			baseWhere += " AND TAA.ALARMREGION LIKE '%" + params.get("ALARMREGIONID") + "%'";
			baseGroupBy += " , TAA.ALARMREGIONID ";
		}else if (params.get("countBy").equalsIgnoreCase("ALARMAREA")) {/**按地区统计，例如广州、深圳等等**/    
			baseSelect += " , SUBSTR(TAA.ALARMREGIONID,0,4) COUNT_TYPE ";
			baseWhere += " AND SUBSTR(TAA.ALARMREGIONID,3,2) <> '00' ";
			baseGroupBy += " , SUBSTR(TAA.ALARMREGIONID,0,4) ";
		}else if (params.get("countBy").equalsIgnoreCase("ROADID")) {/**按道路统计**/     
			baseSelect += " , NVL(TAA.ROADID,'其他') COUNT_TYPE ";
			if(Integer.parseInt(params.get("ROADLEVEL")) != 4){
				baseFrom += " ,T_OA_DICTDLFX TOD ";
				baseWhere += " AND TAA.ROADID  = TOD.DLMC AND TOD.ROADLEVEL = " + params.get("ROADLEVEL");
			}else {
				baseWhere += " AND NOT EXISTS(SELECT TOD.DLMC FROM T_OA_DICTDLFX TOD WHERE TAA.ROADID=TOD.DLMC) ";
			}
			baseGroupBy += " , TAA.ROADID ";
		}else if (params.get("countBy").equalsIgnoreCase("ROADLEVEL")) {/**按道路等级统计**/   
			baseSelect += " , DECODE(TOD.ROADLEVEL,1,'高速',2,'国道',3,'省道','其他') COUNT_TYPE ";
			baseFrom += " ,T_OA_DICTDLFX TOD ";
			baseWhere += "  AND TAA.ROADID = TOD.DLMC(+) ";
			baseGroupBy += " , TOD.ROADLEVEL ";
		}else if (params.get("countBy").equalsIgnoreCase("ROADREGION")) {/**按道路管辖机构统计**/     
			baseSelect += " , TAA.ALARMREGIONID COUNT_TYPE ";
			baseWhere += " AND TAA.ROADID = '" + params.get("ROADID") + "'";
			baseGroupBy += " , TAA.ALARMREGIONID ";
		}else if(params.get("countBy").equalsIgnoreCase("showDetail")){/**显示详细信息**/
			String detailType = params.get("detailType");
			String detailID = params.get("detailID");//.replace("高速公路", "高速").replace("公安局交通警察", "交警");
			String detailParentID = params.get("detailParentID");
			if (detailType.equalsIgnoreCase("001002")) {//拥堵
				baseSelect = "SELECT TO_CHAR(VA.DTIME,'YYYY-MM-DD') DTIME," +
						"DECODE(VA.DESCRIPTION,NULL,VA.ROAD_NAME,VA.ROAD_NAME||'('||VA.DESCRIPTION||')') DESCRIPTION" +
						",VA.CAUSATION,VA.STATUS,VA.ALARMID ";
				baseFrom = " FROM V_TRAFFIC_CROW_DETAIL VA ";
				baseWhere = " WHERE VA.ALARMREGION LIKE '%"+detailID + "%' AND TRUNC(VA.DTIME,'DD') BETWEEN TO_DATE('" + params.get("startDate") + "','yyyy-MM-dd') " +
				" AND TO_DATE('" + params.get("endDate") + "','yyyy-MM-dd') ";
				if(detailParentID != null && !(detailParentID.equals(""))){
					baseWhere += " AND VA.ROAD_NAME = '" + detailParentID + "' ";
				}
				baseGroupBy = "";
			}else if (detailType.equalsIgnoreCase("001023")) {//施工占道
				baseSelect = "SELECT TO_CHAR(VA.DTIME,'YYYY-MM-DD') DTIME,TO_CHAR(VA.ETIME,'YYYY-MM-DD') ETIME," +
						"VA.DESCRIPTION,VA.ROLANENUM,VA.STATUS,VA.ALARMID ";
				baseFrom = " FROM V_ROAD_BUILD_DETAIL VA ";
				baseWhere = " WHERE VA.ALARMREGION LIKE '%"+detailID + "%' AND (" +
				"(TRUNC(VA.DTIME,'DD') BETWEEN TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd') AND TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd')) " +
				"OR (TRUNC(VA.ETIME,'DD') BETWEEN TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd') AND TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd')) " +
				"OR ((TRUNC(VA.DTIME,'DD')<=TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd')) AND (TRUNC(VA.ETIME,'DD')>=TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd'))) " +
				")";
				if(detailParentID != null && !(detailParentID.equals(""))){
					baseWhere += " AND VA.ROAD_NAME = '" + detailParentID + "' ";
				}
				baseGroupBy = "";
			}else if (detailType.equalsIgnoreCase("001024")) {//重大事故
				baseSelect = "SELECT TO_CHAR(VA.DTIME,'YYYY-MM-DD HH24:MI:SS') DTIME,VA.ALARMADDRESS,VA.REASON,VA.ALARMID ";
				baseFrom = " FROM V_ATTEMPER_ACCIDENT_DETAIL VA ";
				baseWhere = " WHERE VA.ALARMREGION LIKE '%"+detailID + "%' AND TRUNC(VA.DTIME,'DD') BETWEEN TO_DATE('" + params.get("startDate") + "','yyyy-MM-dd') " +
				" AND TO_DATE('" + params.get("endDate") + "','yyyy-MM-dd') ";
				if(detailParentID != null && !(detailParentID.equals(""))){
					baseWhere += " AND VA.ROAD_NAME = '" + detailParentID + "' ";
				}
				baseGroupBy = "";
			}
		}else {/**其他情况**/
			baseSelect += " ,TAA.ALARMREGIONID COUNT_TYPE";
			baseGroupBy += " , TAA.ALARMREGIONID ";
//			baseOrderBy += " ALARMREGIONID ";
		}
		//施工占道时间过滤where语句
		String rbWhereString = " AND TAA.EVENTTYPE='001023' AND (" +
				"(TRUNC(TAA.CASEHAPPENTIME,'DD') BETWEEN TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd') AND TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd')) " +
				"OR (TRUNC(TAA.CASEENDTIME,'DD') BETWEEN TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd') AND TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd')) " +
				"OR ((TRUNC(TAA.CASEHAPPENTIME,'DD')<=TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd')) AND (TRUNC(TAA.CASEENDTIME,'DD')>=TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd'))) " +
				")";
		//重特大事故和交通拥堵时间过滤where语句
		String otherWhereString = " AND TAA.EVENTTYPE<>'001023' AND TRUNC(TAA.CASEHAPPENTIME,'DD') BETWEEN TO_DATE('" + params.get("startDate") + "','yyyy-MM-dd') " +
				" AND TO_DATE('" + params.get("endDate") + "','yyyy-MM-dd') ";
		
		baseSql = baseSelect + baseFrom + baseWhere + otherWhereString + baseGroupBy +
				" UNION ALL " +
				baseSelect + baseFrom + baseWhere + rbWhereString + baseGroupBy;
		
		//分类统计三种警情类型的数量（不同的方式），然后排序。
		finalSelect = "SELECT TA.COUNT_TYPE," +
				"MAX (DECODE (TA.EVENTTYPE, '001024', NUM, 0)) ACCIDENT," +
				"MAX (DECODE (TA.EVENTTYPE, '001002', NUM, 0)) TRAFFIC_CROW," +
				"MAX (DECODE (TA.EVENTTYPE, '001023', NUM, 0)) ROAD_BUILD ";
		finalFrom = " FROM (" + baseSql + ") TA ";
		finalGroupBy = " GROUP BY TA.COUNT_TYPE ";
		finalOrderBy = "  ORDER BY " + params.get("sortIndex") + " " + params.get("sord");
		
		finalSql = finalSelect + finalFrom + finalGroupBy + finalOrderBy;
		
		if (params.get("countBy").equalsIgnoreCase("showDetail")) {
			finalSql = baseSelect + baseFrom + baseWhere + "  ORDER BY " + params.get("sortIndex") + " " + params.get("sord");
		}
		
		if (isCount) {//统计记录数量
			pagingSql = getCounterSql(finalSql);
		}else {
			pagingSql = getPagingSql(finalSql, params);
			//将ID转换为名称
			if (params.get("countBy").equalsIgnoreCase("ALARMREGIONID") 
					|| params.get("countBy").equalsIgnoreCase("ALARMAREA") 
					|| params.get("countBy").equalsIgnoreCase("ROADREGION")) {
				pagingSql = "SELECT TD.RN,(CASE WHEN LENGTH (TD.COUNT_TYPE) = 4 THEN  (SELECT SUBSTR (TSD.JGMC, 0, 2) " +
						" FROM T_SYS_DEPARTMENT TSD WHERE TSD.JGID = TD.COUNT_TYPE || '00000000') WHEN LENGTH (TD.COUNT_TYPE) > 4 " +
						" THEN (SELECT TSD.JGMC COUNT_TYPE FROM T_SYS_DEPARTMENT TSD WHERE TSD.JGID = TD.COUNT_TYPE) " +
						" END) COUNT_TYPE,TD.ACCIDENT,TD.TRAFFIC_CROW,TD.ROAD_BUILD FROM (" + pagingSql + ") TD ";
			}
		}
		
		//过滤条件
		if (params.get("sSearch") != "") {
			pagingSql += " AND ( COUNT_TYPE LIKE '%"+params.get("sSearch")+"%') ";
		}
		
		return pagingSql;
	}
	
	/**
	 * 
		* 方法名称：getCounterSql<br>
		* 方法描述： 获取统计数量SQL <br>
		* @param sql
		* @return<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-28 上午11:12:43 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-28 上午11:12:43  <br> 
		* 修改备注：    <br>
	 */
	public String getCounterSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ")";
	}
	
	/**
	 * 
		* 方法名称：getPagingSql<br>
		* 方法描述： 获取分页SQL <br>
		* @param sql
		* @param params
		* @return<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-20 下午8:10:34 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-20 下午8:10:34  <br> 
		* 修改备注：    <br>
	 */
	public String getPagingSql(String sql,Map<String, String> params) {
		String pagingSql = "";
		//开始页数
		int iDisplayStart = Integer.parseInt(params.get("iDisplayStart"));
		//每页长度
		int iDisplayLength = Integer.parseInt(params.get("iDisplayLength"));
		int start = iDisplayStart * iDisplayLength - iDisplayLength;
		int end = iDisplayStart * iDisplayLength;
		if (start < 0) {
			start = 0;
		}
		
		//分页SQL
		String tempSql = " SELECT ROWNUM RN ,TB.* FROM (" + sql + ") TB WHERE ROWNUM <= " + end;
		pagingSql = "SELECT * FROM (" + tempSql + ") TC WHERE TC.RN > " + start; 
		
		return pagingSql;
	}
	
	public String getDetailSql(Map<String, String> params) {
		String detailSql = "";
		return detailSql;
	}
	
	/**
	 * 
		* 方法名称：getJsonObject<br>
		* 方法描述： 获取JSON对象——for jquery jqgrid <br>
		* @param objects
		* @param map
		* @return<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-16 下午4:21:08 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-16 下午4:21:08  <br> 
		* 修改备注：    <br>
	 */
	public JSONObject getJsonObject(Object[][] objects,Map<String, String> map) {
		JSONObject result = new JSONObject();
	    JSONArray cellArray = new JSONArray();
	    JSONArray cell = new JSONArray();
	    JSONObject cellJsonObject = new JSONObject();
	    result.put("page", map.get("iDisplayStart"));
	    result.put("total", map.get("totalPages"));
	    result.put("records", map.get("total"));
	    if (objects != null && objects.length > 0 && map != null) {
			for (int i = 0; i < objects.length; i++) {
				cellJsonObject.put("id", objects[i][0]);
				for (int j = 0; j < objects[i].length; j++) {
					cell.add(objects[i][j]);
				}
				cellJsonObject.put("cell", cell);
				cell.clear();
				cellArray.add(cellJsonObject);
			}
			result.put("rows", cellArray);
		}
	    return result;
	}
}
