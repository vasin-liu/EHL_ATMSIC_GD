
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.notices.service <br> 
	* 文件名称：SystemNoticesService.java <br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1 <br>  
	* 创建日期：2012-2-7 <br>  
	* 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2012-2-7 上午10:38:10<br>  
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-7 上午10:38:10  <br> 
	* 修改备注：   <br>
	*/
package com.ehl.notices.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.base.util.CreateSequence;
import com.ehl.notices.dao.SystemNoticesDao;

/**
 * 
	* 项目名称：EHL_ATMSIC_GD<br>
	* 包路径：com.ehl.notices.service  <br>
	* 类名称：SystemNoticesService  <br> 
	* 文件描述：系统公告   <br>
	*
	* @see <br>
	* 版本信息：Ver 1.1 <br>
	*********************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2012-2-7 上午10:38:10  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-7 上午10:38:10  <br> 
	* 修改备注：     <br>
 */
public class SystemNoticesService {
	
	Logger logger = Logger.getLogger(SystemNoticesService.class);

	/**
	 * 
		* 方法名称：getSystemNotices<br>
		* 方法描述： 获取系统公告列表 <br>
		* @param params
		* @return JSONObject<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 上午11:08:49 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 上午11:08:49  <br> 
		* 修改备注：    <br>
	 */
	public JSONObject getSystemNoticesList(Map<?, ?> params) {
		JSONObject json = null;
		Object[][] resultObjects = null;
		Map<String, String> map = getParams(params);
		SystemNoticesDao systemNoticesDao = new SystemNoticesDao();
		resultObjects = systemNoticesDao.getInfo(getListSql(map, false));
		json = getJsonObject(resultObjects, map);
		return json;
	}
	
	/**
	 * 
		* 方法名称：getSystemNoticesDetail<br>
		* 方法描述： 获取详细公告信息 <br>
		* @param params
		* @return JSONObject<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午2:08:08 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午2:08:08  <br> 
		* 修改备注：    <br>
	 */
	public JSONObject getSystemNoticesDetail(Map<?, ?> params) {
		JSONObject json = null;
		Object[][] resultObjects = null;
		Map<String, String> map = getParams(params);
		SystemNoticesDao systemNoticesDao = new SystemNoticesDao();
		boolean success = systemNoticesDao.updateInfo(getUpdateStateSql(map));
		if (!success) {
			logger.error("系统公告状态更新失败。");
		}
		resultObjects = systemNoticesDao.getInfo(getDetailSql(map, false));
		json = getJsonObject(resultObjects, map);
		return json;
	}
	
	/**
	 * 
		* 方法名称：updateSystemNotices<br>
		* 方法描述： 更新系统公告状态 <br>
		* @param params
		* @return boolean <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午2:52:30 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午2:52:30  <br> 
		* 修改备注：    <br>
	 */
	public boolean updateSystemNotices(Map<?, ?> params) {
		boolean success = false;
		Map<String, String> map = getParams(params);
		SystemNoticesDao systemNoticesDao = new SystemNoticesDao();
		if (map.get("insertOrUpdate").equals("1")) {
			success = systemNoticesDao.updateInfo(getInsertNoticesSql(map));
		}else if (map.get("insertOrUpdate").equals("2")) {
			success = systemNoticesDao.updateInfo(getUpdateNoticesSql(map));
		}else{
			return success;
		}
		return success;
	}
	
	/**
	 * 
		* 方法名称：getParams<br>
		* 方法描述： 获取参数值，或者设置默认参数 <br>
		* @param params
		* @return Map<String, String> <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 上午11:07:50 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 上午11:07:50  <br> 
		* 修改备注：    <br>
	 */
	public Map<String, String> getParams(Map<?, ?> params) {
		Map<String, String> map = new HashMap<String, String>();
		if (params != null) {
			String noticesDay = StringHelper.obj2str(params.get("noticesDay"), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			String userName = StringHelper.obj2str(params.get("userName"), "");
			String noticeId = StringHelper.obj2str(params.get("noticeId"),"");
			String insertOrUpdate = StringHelper.obj2str(params.get("insertOrUpdate"),"0");
			
			map.put("noticesDay", noticesDay);
			map.put("userName", userName);
			map.put("noticeId", noticeId);
			map.put("insertOrUpdate", insertOrUpdate);
		}else {
			map.put("noticesDay", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			map.put("userName", "");
			map.put("noticeId", "");
			map.put("insertOrUpdate", "0");
		}
		return map;
	}
	
	/**
	 * 
		* 方法名称：getListSql<br>
		* 方法描述： 获取列表SQL语句 <br>
		* @param params
		* @param isCount
		* @return String <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 上午11:05:20 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 上午11:05:20  <br> 
		* 修改备注：    <br>
	 */
	public String getListSql(Map<String, String> params,boolean isCount) {
		String sql = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TSN.NOTICE_ID, TSN.NOTICE_TITLE,TSN.READ_USER ")
		.append(" FROM T_SYS_NOTICES TSN ")
		.append(" WHERE 1=1 AND TSN.IS_DISPLAY = 1 ")
		.append(" AND TO_DATE('").append(params.get("noticesDay")).append("','YYYY-MM-DD')")
		.append(" BETWEEN TSN.START_DATE AND TSN.END_DATE ORDER BY TSN.START_DATE");
		
		if (isCount) {//统计记录数量
			sql = getCounterSql(sb.toString());
		}else {
			sql = sb.toString();
		}
		
		return sql;
	}
	
	/**
	 * 
		* 方法名称：getDetailSql<br>
		* 方法描述： 获取详细信息sql语句 <br>
		* @param params
		* @param isCount
		* @return String <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午2:28:35 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午2:28:35  <br> 
		* 修改备注：    <br>
	 */
	public String getDetailSql(Map<String, String> params,boolean isCount) {
		String sql = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TSN.NOTICE_ID,TSN.NOTICE_TITLE,TSN.NOTICE_CONTENT, ")
		.append("TO_CHAR(TSN.START_DATE,'YYYY-MM-DD') START_DATE,TSN.NOTICE_TYPE,TSN.RELEASE_DEPARTMENT ")
		.append(" FROM T_SYS_NOTICES TSN ")
		.append(" WHERE 1=1 AND TSN.IS_DISPLAY = 1 AND TSN.NOTICE_ID = '")
		.append(params.get("noticeId")).append("'");
		
		if (isCount) {//统计记录数量
			sql = getCounterSql(sb.toString());
		}else {
			sql = sb.toString();
		}
		
		return sql;
	}
	
	/**
	 * 
		* 方法名称：getUpdateStateSql<br>
		* 方法描述： 获取更新公告状态sql语句 <br>
		* @param params
		* @return String <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午2:51:42 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午2:51:42  <br> 
		* 修改备注：    <br>
	 */
	public String getUpdateStateSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE T_SYS_NOTICES TSN SET TSN.READ_USER= CASE ")
		.append(" WHEN TSN.READ_USER IS NULL THEN '").append(params.get("userName")).append("' ")
		.append(" WHEN INSTR(TSN.READ_USER, '").append(params.get("userName"))
		.append("') <= 0 THEN CONCAT(TSN.READ_USER, ';")
		.append(params.get("userName")).append("') ELSE TSN.READ_USER END ")
		.append(" WHERE 1=1 AND TSN.NOTICE_ID = '")
		.append(params.get("noticeId")).append("' ");
		return sb.toString();
	}
	
	/**
	 * 
		* 方法名称：getUpdateNoticesSql<br>
		* 方法描述： 获取更公告sql语句 <br>
		* @param params
		* @return String <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午2:51:07 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午2:51:07  <br> 
		* 修改备注：    <br>
	 */
	public String getUpdateNoticesSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE T_SYS_NOTICES TSN SET ( TSN.NOTICE_TITLE,TSN.NOTICE_CONTENT,TSN.NOTICE_LEVEL,")
		.append(" TSN.START_DATE,TSN.END_DATE,TSN.READ_USER ) = ")
		.append("('").append(params.get("noticeTitle")).append("','")
		.append(params.get("noticeContent")).append("',")
		.append(params.get("noticeLevel")).append(",'")
		.append(params.get("readUser")).append("', TO_DATE('")
		.append(params.get("startDate")).append("','YYYY-MM-DD')")
		.append(", TO_DATE('").append(params.get("endDate")).append("','YYYY-MM-DD')").append(")")
		.append(" WHERE TSNS.NOTICE_ID = ").append(params.get("noticeId"));
		return sb.toString();
	}
	
	/**
	 * 
		* 方法名称：getInsertNoticesSql<br>
		* 方法描述： 获取插入系统公告sql语句 <br>
		* @param params
		* @return String <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午3:10:04 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午3:10:04  <br> 
		* 修改备注：    <br>
	 */
	public String getInsertNoticesSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO T_SYS_NOTICES TSN (TSN.NOTICE_ID, TSN.NOTICE_TITLE,TSN.NOTICE_CONTENT,")
		.append(" TSN.NOTICE_LEVEL,TSN.START_DATE,TSN.END_DATE,TSN.READ_USER ) ")
		.append(" VALUES('").append(CreateSequence.getMaxForSeq("SEQ_T_SYS_NOTICES",10)).append(params.get("noticeTitle")).append("','")
		.append(params.get("noticeContent")).append("','")
		.append(params.get("noticeLevel")).append("',").append(params.get("noticeLevel")).append(",'")
		.append(params.get("readUser")).append("', TO_DATE('").append(params.get("startDate")).append("','YYYY-MM-DD')")
		.append(", TO_DATE('").append(params.get("endDate")).append("','YYYY-MM-DD')").append(")")
		.append(" WHERE TSNS.NOTICE_ID = ").append(params.get("noticeId"));
		return sb.toString();
	}
	
	/**
	 * 
		* 方法名称：getCounterSql<br>
		* 方法描述： 获取数量 <br>
		* @param sql
		* @return String <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午1:14:03 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午1:14:03  <br> 
		* 修改备注：    <br>
	 */
	public String getCounterSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ")";
	}
	
	/**
	 * 
		* 方法名称：getJsonObject<br>
		* 方法描述： 获取JSON对象 <br>
		* @param objects
		* @param map
		* @return JSONObject<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 上午11:04:06 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 上午11:04:06  <br> 
		* 修改备注：    <br>
	 */
	public JSONObject getJsonObject(Object[][] objects,Map<String, String> map)  {
		JSONObject result = new JSONObject();
	    JSONArray cellArray = new JSONArray();
	    JSONArray cell = new JSONArray();
	    JSONObject cellJsonObject = new JSONObject();
	    if (objects != null && objects.length > 0 && map != null) {
			for (int i = 0; i < objects.length; i++) {
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
