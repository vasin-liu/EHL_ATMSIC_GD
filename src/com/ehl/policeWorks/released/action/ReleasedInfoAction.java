/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 文件路径：com.ehl.policeWorks.released.action <br>
 * 文件名称：ReleasedInfoAction.java <br>
 * 文件编号：   <br>
 * 文件描述：  <br>
 *
 * 版本信息： Ver 1.1 <br>
 * 创建日期：2012-3-26 16:10:07 <br>
 * 公司名称： 北京易华录信息技术股份有限公司  2012 Copyright(C) 版权所有    <br>
 **************************************************<br>
 * 创建人：Vasin  <br>
 * 创建日期：2012年3月26日16:10:16<br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日16:10:22  <br>
 * 修改备注：   <br>
 */
package com.ehl.policeWorks.released.action;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.policeWorks.released.service.ReleasedInfoService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.policeWorks.released.action  <br>
 * 类名称：ReleasedInfoAction  <br>
 * 文件描述：发布信息查询Action   <br>
 *
 * @see <br>
 *      版本信息：Ver 1.1 <br>
 *      ********************************<br>
 *      创建人：Vasin  <br>
 *      创建日期：2012年3月26日16:11:03  <br>
 *      ************ 修改历史  *************<br>
 *      修改人：Vasin   <br>
 *      修改时间：2012年3月26日16:11:08  <br>
 *      修改备注：     <br>
 */
public class ReleasedInfoAction extends Controller {

    private Logger logger = Logger.getLogger(ReleasedInfoAction.class);

    /**
     * 方法名称：doGetReleasedInfo<br>
     * 方法描述：获取发布信息 for jquery jqgrid <br>
     *
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable 版本信息：Ver 1.1 <br>
     *                   ********************************<br>
     *                   创建人：Vasin  <br>
     *                   创建时间：2012年3月26日16:12:01 <br>
     *                   ************ 修改历史  *************<br>
     *                   修改人：Vasin   <br>
     *                   修改时间：2012年3月26日16:12:05  <br>
     *                   修改备注：    <br>
     */
    public ActionForward doGetReleasedInfo(Action action, HttpServletRequest request,
                                           HttpServletResponse response) throws Throwable {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> hashMap = new HashMap<String, String>();
//        String startDate = StringHelper.obj2str(request.getParameter("startDate"), sdf.format(now));
//        String endDate = StringHelper.obj2str(request.getParameter("endDate"), sdf.format(now));
        String startDate = StringHelper.obj2str(request.getParameter("startDate"), "");
        String endDate = StringHelper.obj2str(request.getParameter("endDate"), "");
        String iDisplayLength = StringHelper.obj2str(request.getParameter("rows"), "10");
        String iDisplayStart = StringHelper.obj2str(request.getParameter("page"), "0");
        String sortIndex = StringHelper.obj2str(request.getParameter("sidx"), "1");
        String sord = StringHelper.obj2str(request.getParameter("sord"), "desc");
        String sSearch = StringHelper.obj2str(request.getParameter("sSearch"), "");
        String releasedDeptId = StringHelper.obj2str(request.getParameter("deptId"), "");
        String infoType = StringHelper.obj2str(request.getParameter("infoType"), "");
        hashMap.put("startDate", startDate);
        hashMap.put("endDate", endDate);
        hashMap.put("iDisplayLength", iDisplayLength);
        hashMap.put("iDisplayStart", iDisplayStart);
        hashMap.put("sortIndex", sortIndex);
        hashMap.put("sord", sord);
        hashMap.put("sSearch", sSearch);
        hashMap.put("deptId", releasedDeptId);
        hashMap.put("infoType", infoType);

        ReleasedInfoService releasedInfoService = new ReleasedInfoService();

        JSONObject json = releasedInfoService.getReleasedInfo(hashMap);
        out.print(json);

        out.close();
        return null;
    }
}
