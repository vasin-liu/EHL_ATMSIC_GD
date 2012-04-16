
	/** 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
	* �ļ�·����com.ehl.notices.service <br> 
	* �ļ����ƣ�SystemNoticesService.java <br>
	* �ļ���ţ�   <br>
	* �ļ�������  <br>
	*
	* �汾��Ϣ�� Ver 1.1 <br>  
	* �������ڣ�2012-2-7 <br>  
	* ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����    <br>
	**************************************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2012-2-7 ����10:38:10<br>  
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2012-2-7 ����10:38:10  <br> 
	* �޸ı�ע��   <br>
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
	* ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
	* ��·����com.ehl.notices.service  <br>
	* �����ƣ�SystemNoticesService  <br> 
	* �ļ�������ϵͳ����   <br>
	*
	* @see <br>
	* �汾��Ϣ��Ver 1.1 <br>
	*********************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2012-2-7 ����10:38:10  <br>
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2012-2-7 ����10:38:10  <br> 
	* �޸ı�ע��     <br>
 */
public class SystemNoticesService {
	
	Logger logger = Logger.getLogger(SystemNoticesService.class);

	/**
	 * 
		* �������ƣ�getSystemNotices<br>
		* ���������� ��ȡϵͳ�����б� <br>
		* @param params
		* @return JSONObject<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����11:08:49 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����11:08:49  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getSystemNoticesDetail<br>
		* ���������� ��ȡ��ϸ������Ϣ <br>
		* @param params
		* @return JSONObject<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����2:08:08 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����2:08:08  <br> 
		* �޸ı�ע��    <br>
	 */
	public JSONObject getSystemNoticesDetail(Map<?, ?> params) {
		JSONObject json = null;
		Object[][] resultObjects = null;
		Map<String, String> map = getParams(params);
		SystemNoticesDao systemNoticesDao = new SystemNoticesDao();
		boolean success = systemNoticesDao.updateInfo(getUpdateStateSql(map));
		if (!success) {
			logger.error("ϵͳ����״̬����ʧ�ܡ�");
		}
		resultObjects = systemNoticesDao.getInfo(getDetailSql(map, false));
		json = getJsonObject(resultObjects, map);
		return json;
	}
	
	/**
	 * 
		* �������ƣ�updateSystemNotices<br>
		* ���������� ����ϵͳ����״̬ <br>
		* @param params
		* @return boolean <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����2:52:30 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����2:52:30  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getParams<br>
		* ���������� ��ȡ����ֵ����������Ĭ�ϲ��� <br>
		* @param params
		* @return Map<String, String> <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����11:07:50 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����11:07:50  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getListSql<br>
		* ���������� ��ȡ�б�SQL��� <br>
		* @param params
		* @param isCount
		* @return String <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����11:05:20 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����11:05:20  <br> 
		* �޸ı�ע��    <br>
	 */
	public String getListSql(Map<String, String> params,boolean isCount) {
		String sql = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TSN.NOTICE_ID, TSN.NOTICE_TITLE,TSN.READ_USER ")
		.append(" FROM T_SYS_NOTICES TSN ")
		.append(" WHERE 1=1 AND TSN.IS_DISPLAY = 1 ")
		.append(" AND TO_DATE('").append(params.get("noticesDay")).append("','YYYY-MM-DD')")
		.append(" BETWEEN TSN.START_DATE AND TSN.END_DATE ORDER BY TSN.START_DATE");
		
		if (isCount) {//ͳ�Ƽ�¼����
			sql = getCounterSql(sb.toString());
		}else {
			sql = sb.toString();
		}
		
		return sql;
	}
	
	/**
	 * 
		* �������ƣ�getDetailSql<br>
		* ���������� ��ȡ��ϸ��Ϣsql��� <br>
		* @param params
		* @param isCount
		* @return String <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����2:28:35 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����2:28:35  <br> 
		* �޸ı�ע��    <br>
	 */
	public String getDetailSql(Map<String, String> params,boolean isCount) {
		String sql = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TSN.NOTICE_ID,TSN.NOTICE_TITLE,TSN.NOTICE_CONTENT, ")
		.append("TO_CHAR(TSN.START_DATE,'YYYY-MM-DD') START_DATE,TSN.NOTICE_TYPE,TSN.RELEASE_DEPARTMENT ")
		.append(" FROM T_SYS_NOTICES TSN ")
		.append(" WHERE 1=1 AND TSN.IS_DISPLAY = 1 AND TSN.NOTICE_ID = '")
		.append(params.get("noticeId")).append("'");
		
		if (isCount) {//ͳ�Ƽ�¼����
			sql = getCounterSql(sb.toString());
		}else {
			sql = sb.toString();
		}
		
		return sql;
	}
	
	/**
	 * 
		* �������ƣ�getUpdateStateSql<br>
		* ���������� ��ȡ���¹���״̬sql��� <br>
		* @param params
		* @return String <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����2:51:42 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����2:51:42  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getUpdateNoticesSql<br>
		* ���������� ��ȡ������sql��� <br>
		* @param params
		* @return String <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����2:51:07 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����2:51:07  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getInsertNoticesSql<br>
		* ���������� ��ȡ����ϵͳ����sql��� <br>
		* @param params
		* @return String <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����3:10:04 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����3:10:04  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getCounterSql<br>
		* ���������� ��ȡ���� <br>
		* @param sql
		* @return String <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����1:14:03 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����1:14:03  <br> 
		* �޸ı�ע��    <br>
	 */
	public String getCounterSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ")";
	}
	
	/**
	 * 
		* �������ƣ�getJsonObject<br>
		* ���������� ��ȡJSON���� <br>
		* @param objects
		* @param map
		* @return JSONObject<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����11:04:06 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����11:04:06  <br> 
		* �޸ı�ע��    <br>
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
