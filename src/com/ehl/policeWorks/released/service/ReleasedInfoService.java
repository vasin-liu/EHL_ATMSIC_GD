/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 文件路径：com.ehl.policeWorks.released.service <br>
 * 文件名称：ReleasedInfoService.java <br>
 * 文件编号：   <br>
 * 文件描述：  <br>
 *
 * 版本信息： Ver 1.1 <br>
 * 创建日期：2011-12-12 <br>
 * 公司名称： 北京易华录信息技术股份有限公司  2012 Copyright(C) 版权所有    <br>
 **************************************************<br>
 * 创建人：Vasin  <br>
 * 创建日期：2012年3月26日16:17:38<br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日16:17:42  <br>
 * 修改备注：   <br>
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
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.policeWorks.released.service  <br>
 * 类名称：ReleasedInfoService  <br>
 * 文件描述：发布信息service   <br>
 *
 * @see <br>
 *      版本信息：Ver 1.1 <br>
 *      ********************************<br>
 *      创建人：Vasin  <br>
 *      创建日期：2012年3月26日16:17:55  <br>
 *      ************ 修改历史  *************<br>
 *      修改人：Vasin   <br>
 *      修改时间：2012年3月26日16:17:58  <br>
 *      修改备注：     <br>
 */
public class ReleasedInfoService {

    Logger logger = Logger.getLogger(ReleasedInfoService.class);

    /**
     * 方法名称：getReleasedInfo<br>
     * 方法描述： 获取发布信息 for jquery jqgrid <br>
     *
     * @param params
     * @return<br> 版本信息：Ver 1.1 <br>
     * ********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012年3月26日16:18:20 <br>
     * ************ 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012年3月26日16:18:25  <br>
     * 修改备注：    <br>
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
     * 方法名称：getParams<br>
     * 方法描述： 获取参数值，或者设置默认参数 <br>
     *
     * @param params
     * @return Map<String, String><br>
     *         版本信息：Ver 1.1 <br>
     *         ********************************<br>
     *         创建人：Vasin  <br>
     *         创建时间：2012年3月26日16:20:29 <br>
     *         ************ 修改历史  *************<br>
     *         修改人：Vasin   <br>
     *         修改时间：2012年3月26日16:20:34  <br>
     *         修改备注：    <br>
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
     * 方法名称：getSql<br>
     * 方法描述： 获取SQL语句（或者记录数SQL语句） <br>
     *
     * @param params
     * @param isCount
     * @return String<br>
     *         版本信息：Ver 1.1 <br>
     *         ********************************<br>
     *         创建人：Vasin  <br>
     *         创建时间：2012年3月26日16:20:20 <br>
     *         ************ 修改历史  *************<br>
     *         修改人：Vasin   <br>
     *         修改时间：2012年3月26日16:20:23  <br>
     *         修改备注：    <br>
     */
    public String getSql(Map<String, String> params, boolean isCount) {
        //分页SQL
        String pagingSql = "";
        String baseSql = "", baseSelect = "", baseWhere = "", baseOrderBy = "";

        baseSelect = "SELECT TAA.ALARMID,TAA.ALARMREGION " +
                " || '发布了一宗在' || TAA.ROADID || TAA.ROADNAME" +
                " || '发生的' || DECODE (TAA.EVENTTYPE, '001002', '交通拥堵', '施工占道') TITLE," +
                " TO_CHAR(TOPR.REMINDTIME,'YYYY-MM-DD HH24:MI:SS') RELEASEDDATE," +
                " DECODE (TAA.EVENTTYPE, '001002', '交通拥堵', '施工占道') INFO_TYPE," +
                " TOPR.PUBLISHTYPE,1 AS SCORING,TOPR.STATE,TAA.EVENTSTATE " +
                " FROM T_ATTEMPER_ALARM TAA JOIN T_OA_POLICE_REMIND TOPR " +
                " ON TAA.ALARMID = TOPR.ALARMID " +
                " WHERE 1=1 ";

        //是否特定类型
        if(params.get("infoType").equals("001002")){
            baseWhere += " AND (TAA.EVENTTYPE = '001002' AND TAA.EVENTSTATE = '570002') ";
        }else if(params.get("infoType").equals("001023")){
            baseWhere += " AND TAA.EVENTTYPE = '001023' ";
        }else {
            baseWhere += " AND ((TAA.EVENTTYPE = '001002' AND TAA.EVENTSTATE = '570002') OR TAA.EVENTTYPE = '001023') ";
        }

        //是否特定日期
        if(!(params.get("startDate").equals("")) && !(params.get("endDate").equals(""))){
            baseWhere += " AND TRUNC(TOPR.REMINDTIME,'DD') BETWEEN " +
                    "TO_DATE('" + params.get("startDate") + "','YYYY-MM-DD')" +
                    " AND TO_DATE('" + params.get("endDate") + "','YYYY-MM-DD') ";
        }

        //是否特定部门
        if(!(params.get("deptId").equals(""))){
            baseWhere += " AND TAA.ALARMREGIONID = '" + params.get("deptId") + "' ";
        }

        //是否已经处理
        /*if(!(params.get("flowState").equals(""))){
            baseWhere += " AND TOPR.FLOWSTATE = '" + params.get("flowState") + "' ";
        }*/

        //sort
        String sortIndex = params.get("sortIndex");
        String sord = params.get("sord");

        baseOrderBy = "  ORDER BY " + sortIndex + " " + sord;

        // 过滤条件--未用
        /*if (params.get("sSearch") != "") {
            baseWhere += " AND ( TAA.ALARMREGION LIKE '%" + params.get("sSearch") + "%'"
                    + " OR TAA.ROADID LIKE '%" + params.get("sSearch") + "%'"
                    + " OR TAA.ROADNAME LIKE '%" + params.get("sSearch") + "%'"
                    + ") ";
        }*/

        baseSql = baseSelect + baseWhere + baseOrderBy;

        if (isCount) {//统计记录数量
            pagingSql = getCounterSql(baseSql);
        } else { //分页
            pagingSql = getPagingSql(baseSql, params);
        }

        return pagingSql;
    }

    /**
     * 方法名称：getCounterSql<br>
     * 方法描述： 获取统计数量SQL <br>
     *
     * @param sql
     * @return<br> 版本信息：Ver 1.1 <br>
     * ********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012年3月26日16:20:09 <br>
     * ************ 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012年3月26日16:20:14  <br>
     * 修改备注：    <br>
     */
    public String getCounterSql(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ")";
    }

    /**
     * 方法名称：getPagingSql<br>
     * 方法描述： 获取分页SQL <br>
     *
     * @param sql
     * @param params
     * @return<br> 版本信息：Ver 1.1 <br>
     * ********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012年3月26日16:20:03 <br>
     * ************ 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012年3月26日16:20:00  <br>
     * 修改备注：    <br>
     */
    public String getPagingSql(String sql, Map<String, String> params) {
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
        String tempSql = " SELECT ROWNUM ROW_NUM,TB.* FROM (" + sql + ") TB WHERE ROWNUM <= " + end;
        pagingSql = "SELECT * FROM (" + tempSql + ") TC WHERE TC.ROW_NUM > " + start;

        return pagingSql;
    }

    /**
     * 方法名称：getJsonObject<br>
     * 方法描述： 获取JSON对象——for jquery jqgrid <br>
     *
     * @param objects
     * @param map
     * @return<br> 版本信息：Ver 1.1 <br>
     * ********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012年3月26日16:18:59 <br>
     * ************ 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012年3月26日16:18:55  <br>
     * 修改备注：    <br>
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
