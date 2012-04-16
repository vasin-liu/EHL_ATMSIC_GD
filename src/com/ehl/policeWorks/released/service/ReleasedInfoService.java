/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
 * �ļ�·����com.ehl.policeWorks.released.service <br>
 * �ļ����ƣ�ReleasedInfoService.java <br>
 * �ļ���ţ�   <br>
 * �ļ�������  <br>
 *
 * �汾��Ϣ�� Ver 1.1 <br>
 * �������ڣ�2011-12-12 <br>
 * ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2012 Copyright(C) ��Ȩ����    <br>
 **************************************************<br>
 * �����ˣ�Vasin  <br>
 * �������ڣ�2012��3��26��16:17:38<br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2012��3��26��16:17:42  <br>
 * �޸ı�ע��   <br>
 */
package com.ehl.policeWorks.released.service;

import com.appframe.utils.StringHelper;
import com.ehl.policeWorks.released.dao.ReleasedInfoDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.policeWorks.released.service  <br>
 * �����ƣ�ReleasedInfoService  <br>
 * �ļ�������������Ϣservice   <br>
 *
 * @see <br>
 *      �汾��Ϣ��Ver 1.1 <br>
 *      ********************************<br>
 *      �����ˣ�Vasin  <br>
 *      �������ڣ�2012��3��26��16:17:55  <br>
 *      ************ �޸���ʷ  *************<br>
 *      �޸��ˣ�Vasin   <br>
 *      �޸�ʱ�䣺2012��3��26��16:17:58  <br>
 *      �޸ı�ע��     <br>
 */
public class ReleasedInfoService {

    Logger logger = Logger.getLogger(ReleasedInfoService.class);

    /**
     * �������ƣ�getReleasedInfo<br>
     * ���������� ��ȡ������Ϣ for jquery jqgrid <br>
     *
     * @param params
     * @return<br> �汾��Ϣ��Ver 1.1 <br>
     * ********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012��3��26��16:18:20 <br>
     * ************ �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012��3��26��16:18:25  <br>
     * �޸ı�ע��    <br>
     */
    public JSONObject getReleasedInfo(Map<?, ?> params) {
        JSONObject json = null;
        Object[][] resultObjects = null;
        Map<String, String> map = getParams(params);
        String sSearch = map.get("sSearch");
        map.remove("sSearch");
        map.put("sSearch", "");
        ReleasedInfoDao releasedInfoDao = new ReleasedInfoDao();
        String total = StringHelper.obj2str(releasedInfoDao.getCount(getSql(map, true)), "0");
        map.put("total", total);
        if (sSearch != "") {
            map.remove("sSearch");
            map.put("sSearch", sSearch);
            String totalAfterFilter = StringHelper.obj2str(releasedInfoDao.getCount(getSql(map, true)), "0");
            map.put("totalBeforeFilter", total);
            map.remove("toatl");
            map.put("total", totalAfterFilter);
        } else {
            map.put("totalBeforeFilter", total);
        }
        if (Integer.parseInt(map.get("total")) > 0) {
            int totalPages = (int) Math.ceil(Double.parseDouble(map.get("total")) / Double.parseDouble(map.get("iDisplayLength")));
            map.put("totalPages", String.valueOf(totalPages));
            if (Integer.parseInt(map.get("iDisplayStart")) > totalPages) {
                map.remove("iDisplayStart");
                map.put("iDisplayStart", String.valueOf(totalPages));
            }
        }

        resultObjects = releasedInfoDao.getInfo(getSql(map, false));
        json = getJsonObject(resultObjects, map);
        return json;
    }

    /**
     * �������ƣ�getParams<br>
     * ���������� ��ȡ����ֵ����������Ĭ�ϲ��� <br>
     *
     * @param params
     * @return Map<String, String><br>
     *         �汾��Ϣ��Ver 1.1 <br>
     *         ********************************<br>
     *         �����ˣ�Vasin  <br>
     *         ����ʱ�䣺2012��3��26��16:20:29 <br>
     *         ************ �޸���ʷ  *************<br>
     *         �޸��ˣ�Vasin   <br>
     *         �޸�ʱ�䣺2012��3��26��16:20:34  <br>
     *         �޸ı�ע��    <br>
     */
    public Map<String, String> getParams(Map<?, ?> params) {
        Map<String, String> map = new HashMap<String, String>();
        if (params != null) {
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String startDate = StringHelper.obj2str(params.get("startDate"), sdf.format(nowDate));
//            String endDate = StringHelper.obj2str(params.get("endDate"), sdf.format(nowDate));
            String startDate = StringHelper.obj2str(params.get("startDate"), "");
            String endDate = StringHelper.obj2str(params.get("endDate"), "");
            int iDisplayLength = StringHelper.obj2int(params.get("iDisplayLength"), 10);
            int iDisplayStart = StringHelper.obj2int(params.get("iDisplayStart"), 0);
            String sortIndex = StringHelper.obj2str(params.get("sortIndex"), "1");
            String sord = StringHelper.obj2str(params.get("sord"), "desc");
            String sSearch = StringHelper.obj2str(params.get("sSearch"), "");
            String releasedDeptId = StringHelper.obj2str(params.get("deptId"), "");
            String infoType = StringHelper.obj2str(params.get("infoType"), "");
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
            map.put("deptId", releasedDeptId);
            map.put("infoType", infoType);
        } else {
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            map.put("startDate", sdf.format(nowDate));
//            map.put("endDate", sdf.format(nowDate));
            map.put("startDate", "");
            map.put("endDate", "");
            map.put("iDisplayLength", "10");
            map.put("iDisplayStart", "0");
            map.put("sortIndex", "1");
            map.put("sord", "desc");
            map.put("sSearch", "");
            map.put("deptId", "");
            map.put("infoType", "");
        }
        return map;
    }

    /**
     * �������ƣ�getSql<br>
     * ���������� ��ȡSQL��䣨���߼�¼��SQL��䣩 <br>
     *
     * @param params
     * @param isCount
     * @return String<br>
     *         �汾��Ϣ��Ver 1.1 <br>
     *         ********************************<br>
     *         �����ˣ�Vasin  <br>
     *         ����ʱ�䣺2012��3��26��16:20:20 <br>
     *         ************ �޸���ʷ  *************<br>
     *         �޸��ˣ�Vasin   <br>
     *         �޸�ʱ�䣺2012��3��26��16:20:23  <br>
     *         �޸ı�ע��    <br>
     */
    public String getSql(Map<String, String> params, boolean isCount) {
        //��ҳSQL
        String pagingSql = "";
        String baseSql = "", baseSelect = "", baseWhere = "", baseOrderBy = "";

        baseSelect = "SELECT TAA.ALARMID,TAA.ALARMREGION " +
                " || '������һ����' || TAA.ROADID || TAA.ROADNAME" +
                " || '������' || DECODE (TAA.EVENTTYPE, '001002', '��ͨӵ��', 'ʩ��ռ��') TITLE," +
                " TO_CHAR(TOPR.REMINDTIME,'YYYY-MM-DD HH24:MI:SS') RELEASEDDATE," +
                " DECODE (TAA.EVENTTYPE, '001002', '��ͨӵ��', 'ʩ��ռ��') INFO_TYPE," +
                " TOPR.PUBLISHTYPE,1 AS SCORING,TOPR.STATE,TAA.EVENTSTATE " +
                " FROM T_ATTEMPER_ALARM TAA JOIN T_OA_POLICE_REMIND TOPR " +
                " ON TAA.ALARMID = TOPR.ALARMID " +
                " WHERE 1=1 ";

        //�Ƿ��ض�����
        if(params.get("infoType").equals("001002")){
            baseWhere += " AND (TAA.EVENTTYPE = '001002' AND TAA.EVENTSTATE = '570002') ";
        }else if(params.get("infoType").equals("001023")){
            baseWhere += " AND TAA.EVENTTYPE = '001023' ";
        }else {
            baseWhere += " AND ((TAA.EVENTTYPE = '001002' AND TAA.EVENTSTATE = '570002') OR TAA.EVENTTYPE = '001023') ";
        }

        //�Ƿ��ض�����
        if(!(params.get("startDate").equals("")) && !(params.get("endDate").equals(""))){
            baseWhere += " AND TRUNC(TOPR.REMINDTIME,'DD') BETWEEN " +
                    "TO_DATE('" + params.get("startDate") + "','YYYY-MM-DD')" +
                    " AND TO_DATE('" + params.get("endDate") + "','YYYY-MM-DD') ";
        }

        //�Ƿ��ض�����
        if(!(params.get("deptId").equals(""))){
            baseWhere += " AND TAA.ALARMREGIONID = '" + params.get("deptId") + "' ";
        }

        //�Ƿ��Ѿ�����
        /*if(!(params.get("flowState").equals(""))){
            baseWhere += " AND TOPR.FLOWSTATE = '" + params.get("flowState") + "' ";
        }*/

        //sort
        String sortIndex = params.get("sortIndex");
        String sord = params.get("sord");

        baseOrderBy = "  ORDER BY " + sortIndex + " " + sord;

        // ��������--δ��
        /*if (params.get("sSearch") != "") {
            baseWhere += " AND ( TAA.ALARMREGION LIKE '%" + params.get("sSearch") + "%'"
                    + " OR TAA.ROADID LIKE '%" + params.get("sSearch") + "%'"
                    + " OR TAA.ROADNAME LIKE '%" + params.get("sSearch") + "%'"
                    + ") ";
        }*/

        baseSql = baseSelect + baseWhere + baseOrderBy;

        if (isCount) {//ͳ�Ƽ�¼����
            pagingSql = getCounterSql(baseSql);
        } else { //��ҳ
            pagingSql = getPagingSql(baseSql, params);
        }

        return pagingSql;
    }

    /**
     * �������ƣ�getCounterSql<br>
     * ���������� ��ȡͳ������SQL <br>
     *
     * @param sql
     * @return<br> �汾��Ϣ��Ver 1.1 <br>
     * ********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012��3��26��16:20:09 <br>
     * ************ �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012��3��26��16:20:14  <br>
     * �޸ı�ע��    <br>
     */
    public String getCounterSql(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ")";
    }

    /**
     * �������ƣ�getPagingSql<br>
     * ���������� ��ȡ��ҳSQL <br>
     *
     * @param sql
     * @param params
     * @return<br> �汾��Ϣ��Ver 1.1 <br>
     * ********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012��3��26��16:20:03 <br>
     * ************ �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012��3��26��16:20:00  <br>
     * �޸ı�ע��    <br>
     */
    public String getPagingSql(String sql, Map<String, String> params) {
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
        String tempSql = " SELECT ROWNUM ROW_NUM,TB.* FROM (" + sql + ") TB WHERE ROWNUM <= " + end;
        pagingSql = "SELECT * FROM (" + tempSql + ") TC WHERE TC.ROW_NUM > " + start;

        return pagingSql;
    }

    /**
     * �������ƣ�getJsonObject<br>
     * ���������� ��ȡJSON���󡪡�for jquery jqgrid <br>
     *
     * @param objects
     * @param map
     * @return<br> �汾��Ϣ��Ver 1.1 <br>
     * ********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012��3��26��16:18:59 <br>
     * ************ �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012��3��26��16:18:55  <br>
     * �޸ı�ע��    <br>
     */
    public JSONObject getJsonObject(Object[][] objects, Map<String, String> map) {
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
