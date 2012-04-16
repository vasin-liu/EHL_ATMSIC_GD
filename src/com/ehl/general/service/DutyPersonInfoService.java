
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.general.service <br> 
	* 文件名称：DutyPersonInfoService.java <br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1 <br>  
	* 创建日期：2011-12-12 <br>  
	* 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2011-12-12 下午3:27:19<br>  
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-12 下午3:27:19  <br> 
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
import com.ehl.general.dao.DutyPersonInfoDao;

	/**   
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.general.service  <br>
 * 类名称：DutyPersonInfoService  <br> 
 * 文件描述：值班人员信息service   <br>
 *
 * @see <br>
 * 版本信息：Ver 1.1 <br>
 *********************************<br>
 * 创建人：Vasin  <br> 
 * 创建日期：2011-12-12 下午3:27:19  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2011-12-12 下午3:27:19  <br> 
 * 修改备注：     <br>
 */
public class DutyPersonInfoService {
	
	Logger logger = Logger.getLogger(DutyPersonInfoService.class);

	/**
	 * 
		* 方法名称：getDutyPersonInfo<br>
		* 方法描述： for jquery jqgrid <br>
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
	public JSONObject getDutyPersonInfo(Map<?, ?> params) {
		JSONObject json = null;
		Object[][] resultObjects = null;
		Map<String, String> map = getParams(params);
		String sSearch = map.get("sSearch");
		map.remove("sSearch");
		map.put("sSearch", "");
		DutyPersonInfoDao dutyPersonInfoDao = new DutyPersonInfoDao();
		String total = StringHelper.obj2str(dutyPersonInfoDao.getCount(getSql(map,true)), "0");
		map.put("total", total);
		if (sSearch != "") {
			map.remove("sSearch");
			map.put("sSearch", sSearch);
			String totalAfterFilter = StringHelper.obj2str(dutyPersonInfoDao.getCount(getSql(map,true)),"0");
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
		
		resultObjects = dutyPersonInfoDao.getInfo(getSql(map,false));
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
			String startDate = StringHelper.obj2str(params.get("startDate"), sdf.format(nowDate));
			String endDate = StringHelper.obj2str(params.get("endDate"), sdf.format(nowDate));
			int iDisplayLength = StringHelper.obj2int(params.get("iDisplayLength"), 10);
			int iDisplayStart = StringHelper.obj2int(params.get("iDisplayStart"), 0);
			String sortIndex = StringHelper.obj2str(params.get("sortIndex"), "1");
			String sord = StringHelper.obj2str(params.get("sord"), "desc");
			String sSearch = StringHelper.obj2str(params.get("sSearch"), "");
			String showLevel = StringHelper.obj2str(params.get("showLevel"), "1");
			String jgid = StringHelper.obj2str(params.get("jgid"), "");
			if (iDisplayStart < 0) {
				iDisplayStart = 0;
			}
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("iDisplayLength", String.valueOf(iDisplayLength));
			map.put("iDisplayStart", String.valueOf(iDisplayStart));
			map.put("sortIndex", sortIndex);
			map.put("sord", sord);
			map.put("sSearch", sSearch);
			map.put("showLevel", showLevel);
			map.put("jgid", jgid);
		}else {
			Date nowDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("startDate", sdf.format(nowDate));
			map.put("endDate", sdf.format(nowDate));
			map.put("iDisplayLength", "10");
			map.put("iDisplayStart", "0");
			map.put("sortIndex", "1");
			map.put("sord", "desc");
			map.put("sSearch", "");
			map.put("showLevel", "1");
			map.put("jgid", "");
		}
		return map;
	}
	
	/**
	 * 
		* 方法名称：getSql<br>
		* 方法描述： 获取SQL语句（或者记录数SQL语句） <br>
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
		String baseSql = "", baseTableName = " V_OA_DUTY_DETAIL VA ",baseSelect = "",baseWhere = "",baseFrom = "",baseOrderBy = "";
		baseSelect = " SELECT SUBSTR(VA.DUTY_TIME,1,10) DUTY_TIME,VA.JGID, VA.JGMC," +
				"VA.DUTY_LEADER,VA.DUTY_PERSON,VA.DUTY_PHONE ";
		baseFrom = " FROM "+ baseTableName;
		baseWhere = " WHERE 1=1 ";
		baseOrderBy = " ORDER BY VA.JGID";
		
		if (params.get("showLevel") != "") {
			baseWhere += " AND VA.JGLX=" + params.get("showLevel");
		}
		
		if (params.get("jgid") != "") {
			baseWhere += " AND VA.JGID LIKE '%" + params.get("jgid").substring(0, 4) + "%' " +
					" AND VA.JGID <> " + params.get("jgid");
		}
		
		baseWhere += " AND TRUNC(TO_DATE(VA.DUTY_TIME,'YYYY-MM-DD HH24:MI:SS'),'DD') BETWEEN " +
				"TO_DATE('" + params.get("startDate") + "','YYYY-MM-DD')" +
			    " AND TO_DATE('" + params.get("endDate") + "','YYYY-MM-DD')";
		//sort
		String sortIndex = params.get("sortIndex");
		String sord = params.get("sord");
		if(sortIndex.equalsIgnoreCase("jgmc")){
			sortIndex = "jgid";
		}
		baseOrderBy = "  ORDER BY " + sortIndex + " " + sord;
		
		// 过滤条件
		if (params.get("sSearch") != "") {
			baseWhere += " AND ( JGMC LIKE '%" + params.get("sSearch") + "%'"
					+ " OR DUTY_LEADER LIKE '%" + params.get("sSearch") + "%'"
					+ " OR DUTY_PERSON LIKE '%" + params.get("sSearch") + "%'"
					+ " OR DUTY_PHONE LIKE '%" + params.get("sSearch") + "%'"
					+ ") ";
		}
		
		baseSql = baseSelect + baseFrom + baseWhere + baseOrderBy;
		
		if (isCount) {//统计记录数量
			pagingSql = getCounterSql(baseSql);
		}else {
			pagingSql = getPagingSql(baseSql, params);
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
		* 创建时间：2011-12-27 下午1:55:37 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-27 下午1:55:37  <br> 
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
