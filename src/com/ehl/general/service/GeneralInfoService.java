
	/** 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
	* �ļ�·����com.ehl.general.service <br> 
	* �ļ����ƣ�GeneralInfoService.java <br>
	* �ļ���ţ�   <br>
	* �ļ�������  <br>
	*
	* �汾��Ϣ�� Ver 1.1 <br>  
	* �������ڣ�2011-12-12 <br>  
	* ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����    <br>
	**************************************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2011-12-12 ����7:00:21<br>  
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2011-12-12 ����7:00:21  <br> 
	* �޸ı�ע��   <br>
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
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.general.service  <br>
 * �����ƣ�GeneralInfoService  <br> 
 * �ļ�������������Ϣ����Service   <br>
 *
 * @see <br>
 * �汾��Ϣ��Ver 1.1 <br>
 *********************************<br>
 * �����ˣ�Vasin  <br> 
 * �������ڣ�2011-12-12 ����7:00:21  <br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2011-12-12 ����7:00:21  <br> 
 * �޸ı�ע��     <br>
 */
//@org.springframework.context.annotation.Scope("prototype")
//@org.springframework.stereotype.Service("generalInfoService")
public class GeneralInfoService{
	
	Logger logger = Logger.getLogger(GeneralInfoService.class);
	
//	@javax.annotation.Resource(name="generalInfoDao")
//	public GeneralInfoDao generalInfoDao;
	
	/**
	 * 
		* �������ƣ�getGeneralInfoCount2<br>
		* ���������� for jquery jqgird <br>
		* @param params
		* @return<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-16 ����3:50:38 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-16 ����3:50:38  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getParams<br>
		* ���������� ��ȡ����ֵ����������Ĭ�ϲ��� <br>
		* @param params
		* @return Map<String, String><br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-16 ����3:51:16 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-16 ����3:51:16  <br> 
		* �޸ı�ע��    <br>
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
		* �������ƣ�getPagingSql<br>
		* ���������� ��ȡ��ҳSQL��䣨���߼�¼��SQL��䣩 <br>
		* @param params
		* @param isCount
		* @return String<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-18 ����4:39:02 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-18 ����4:39:02  <br> 
		* �޸ı�ע��    <br>
	 */
	public String getSql(Map<String, String> params,boolean isCount) {
		//��ҳSQL
		String pagingSql = "";
		//���ս����SQL
		String finalSql = "",finalSelect = ""/*,finalWhere = ""*/,finalFrom = "", finalGroupBy = "",finalOrderBy = "";
		//����ͳ�ƽ��SQL
		String baseSql = "", baseTableName = "T_ATTEMPER_ALARM",baseSelect = "",baseWhere = "",baseFrom = "", baseGroupBy = ""/*,baseOrderBy = ""*/;
		baseSelect = " SELECT COUNT (*) NUM, TAA.EVENTTYPE ";
		baseFrom = " FROM "+ baseTableName +" TAA ";
		baseWhere = " WHERE 1=1 ";
		baseGroupBy = " GROUP BY TAA.EVENTTYPE ";
		if (params.get("countBy").equalsIgnoreCase("ALARMREGIONID")) {/**������ͳ�ƣ��������**/
			baseSelect += " ,TAA.ALARMREGIONID COUNT_TYPE";
			baseWhere += " AND TAA.ALARMREGION LIKE '%" + params.get("ALARMREGIONID") + "%'";
			baseGroupBy += " , TAA.ALARMREGIONID ";
		}else if (params.get("countBy").equalsIgnoreCase("ALARMAREA")) {/**������ͳ�ƣ�������ݡ����ڵȵ�**/    
			baseSelect += " , SUBSTR(TAA.ALARMREGIONID,0,4) COUNT_TYPE ";
			baseWhere += " AND SUBSTR(TAA.ALARMREGIONID,3,2) <> '00' ";
			baseGroupBy += " , SUBSTR(TAA.ALARMREGIONID,0,4) ";
		}else if (params.get("countBy").equalsIgnoreCase("ROADID")) {/**����·ͳ��**/     
			baseSelect += " , NVL(TAA.ROADID,'����') COUNT_TYPE ";
			if(Integer.parseInt(params.get("ROADLEVEL")) != 4){
				baseFrom += " ,T_OA_DICTDLFX TOD ";
				baseWhere += " AND TAA.ROADID  = TOD.DLMC AND TOD.ROADLEVEL = " + params.get("ROADLEVEL");
			}else {
				baseWhere += " AND NOT EXISTS(SELECT TOD.DLMC FROM T_OA_DICTDLFX TOD WHERE TAA.ROADID=TOD.DLMC) ";
			}
			baseGroupBy += " , TAA.ROADID ";
		}else if (params.get("countBy").equalsIgnoreCase("ROADLEVEL")) {/**����·�ȼ�ͳ��**/   
			baseSelect += " , DECODE(TOD.ROADLEVEL,1,'����',2,'����',3,'ʡ��','����') COUNT_TYPE ";
			baseFrom += " ,T_OA_DICTDLFX TOD ";
			baseWhere += "  AND TAA.ROADID = TOD.DLMC(+) ";
			baseGroupBy += " , TOD.ROADLEVEL ";
		}else if (params.get("countBy").equalsIgnoreCase("ROADREGION")) {/**����·��Ͻ����ͳ��**/     
			baseSelect += " , TAA.ALARMREGIONID COUNT_TYPE ";
			baseWhere += " AND TAA.ROADID = '" + params.get("ROADID") + "'";
			baseGroupBy += " , TAA.ALARMREGIONID ";
		}else if(params.get("countBy").equalsIgnoreCase("showDetail")){/**��ʾ��ϸ��Ϣ**/
			String detailType = params.get("detailType");
			String detailID = params.get("detailID");//.replace("���ٹ�·", "����").replace("�����ֽ�ͨ����", "����");
			String detailParentID = params.get("detailParentID");
			if (detailType.equalsIgnoreCase("001002")) {//ӵ��
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
			}else if (detailType.equalsIgnoreCase("001023")) {//ʩ��ռ��
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
			}else if (detailType.equalsIgnoreCase("001024")) {//�ش��¹�
				baseSelect = "SELECT TO_CHAR(VA.DTIME,'YYYY-MM-DD HH24:MI:SS') DTIME,VA.ALARMADDRESS,VA.REASON,VA.ALARMID ";
				baseFrom = " FROM V_ATTEMPER_ACCIDENT_DETAIL VA ";
				baseWhere = " WHERE VA.ALARMREGION LIKE '%"+detailID + "%' AND TRUNC(VA.DTIME,'DD') BETWEEN TO_DATE('" + params.get("startDate") + "','yyyy-MM-dd') " +
				" AND TO_DATE('" + params.get("endDate") + "','yyyy-MM-dd') ";
				if(detailParentID != null && !(detailParentID.equals(""))){
					baseWhere += " AND VA.ROAD_NAME = '" + detailParentID + "' ";
				}
				baseGroupBy = "";
			}
		}else {/**�������**/
			baseSelect += " ,TAA.ALARMREGIONID COUNT_TYPE";
			baseGroupBy += " , TAA.ALARMREGIONID ";
//			baseOrderBy += " ALARMREGIONID ";
		}
		//ʩ��ռ��ʱ�����where���
		String rbWhereString = " AND TAA.EVENTTYPE='001023' AND (" +
				"(TRUNC(TAA.CASEHAPPENTIME,'DD') BETWEEN TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd') AND TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd')) " +
				"OR (TRUNC(TAA.CASEENDTIME,'DD') BETWEEN TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd') AND TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd')) " +
				"OR ((TRUNC(TAA.CASEHAPPENTIME,'DD')<=TO_DATE('"+params.get("startDate")+"','yyyy-MM-dd')) AND (TRUNC(TAA.CASEENDTIME,'DD')>=TO_DATE('"+params.get("endDate")+"','yyyy-MM-dd'))) " +
				")";
		//���ش��¹ʺͽ�ͨӵ��ʱ�����where���
		String otherWhereString = " AND TAA.EVENTTYPE<>'001023' AND TRUNC(TAA.CASEHAPPENTIME,'DD') BETWEEN TO_DATE('" + params.get("startDate") + "','yyyy-MM-dd') " +
				" AND TO_DATE('" + params.get("endDate") + "','yyyy-MM-dd') ";
		
		baseSql = baseSelect + baseFrom + baseWhere + otherWhereString + baseGroupBy +
				" UNION ALL " +
				baseSelect + baseFrom + baseWhere + rbWhereString + baseGroupBy;
		
		//����ͳ�����־������͵���������ͬ�ķ�ʽ����Ȼ������
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
		
		if (isCount) {//ͳ�Ƽ�¼����
			pagingSql = getCounterSql(finalSql);
		}else {
			pagingSql = getPagingSql(finalSql, params);
			//��IDת��Ϊ����
			if (params.get("countBy").equalsIgnoreCase("ALARMREGIONID") 
					|| params.get("countBy").equalsIgnoreCase("ALARMAREA") 
					|| params.get("countBy").equalsIgnoreCase("ROADREGION")) {
				pagingSql = "SELECT TD.RN,(CASE WHEN LENGTH (TD.COUNT_TYPE) = 4 THEN  (SELECT SUBSTR (TSD.JGMC, 0, 2) " +
						" FROM T_SYS_DEPARTMENT TSD WHERE TSD.JGID = TD.COUNT_TYPE || '00000000') WHEN LENGTH (TD.COUNT_TYPE) > 4 " +
						" THEN (SELECT TSD.JGMC COUNT_TYPE FROM T_SYS_DEPARTMENT TSD WHERE TSD.JGID = TD.COUNT_TYPE) " +
						" END) COUNT_TYPE,TD.ACCIDENT,TD.TRAFFIC_CROW,TD.ROAD_BUILD FROM (" + pagingSql + ") TD ";
			}
		}
		
		//��������
		if (params.get("sSearch") != "") {
			pagingSql += " AND ( COUNT_TYPE LIKE '%"+params.get("sSearch")+"%') ";
		}
		
		return pagingSql;
	}
	
	/**
	 * 
		* �������ƣ�getCounterSql<br>
		* ���������� ��ȡͳ������SQL <br>
		* @param sql
		* @return<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-28 ����11:12:43 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-28 ����11:12:43  <br> 
		* �޸ı�ע��    <br>
	 */
	public String getCounterSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ")";
	}
	
	/**
	 * 
		* �������ƣ�getPagingSql<br>
		* ���������� ��ȡ��ҳSQL <br>
		* @param sql
		* @param params
		* @return<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-20 ����8:10:34 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-20 ����8:10:34  <br> 
		* �޸ı�ע��    <br>
	 */
	public String getPagingSql(String sql,Map<String, String> params) {
		String pagingSql = "";
		//��ʼҳ��
		int iDisplayStart = Integer.parseInt(params.get("iDisplayStart"));
		//ÿҳ����
		int iDisplayLength = Integer.parseInt(params.get("iDisplayLength"));
		int start = iDisplayStart * iDisplayLength - iDisplayLength;
		int end = iDisplayStart * iDisplayLength;
		if (start < 0) {
			start = 0;
		}
		
		//��ҳSQL
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
		* �������ƣ�getJsonObject<br>
		* ���������� ��ȡJSON���󡪡�for jquery jqgrid <br>
		* @param objects
		* @param map
		* @return<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-16 ����4:21:08 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-16 ����4:21:08  <br> 
		* �޸ı�ע��    <br>
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
