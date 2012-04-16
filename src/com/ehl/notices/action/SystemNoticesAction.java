
/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 文件路径：com.ehl.notices.action <br>
 * 文件名称：SystemNoticesAction.java <br>
 * 文件编号：   <br>
 * 文件描述：  <br>
 *
 * 版本信息： Ver 1.1 <br>
 * 创建日期：2012-2-7 <br>
 * 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
 **************************************************<br>
 * 创建人：Vasin  <br>
 * 创建日期：2012-2-7 上午10:34:45 <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012-2-7 上午10:34:45   <br>
 * 修改备注：   <br>
 */
package com.ehl.notices.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.notices.service.SystemNoticesService;

/**
 *
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.notices.action  <br>
 * 类名称：SystemNotices  <br>
 * 文件描述：系统公告   <br>
 *
 * @see <br>
 * 版本信息：Ver 1.1 <br>
 *********************************<br>
 * 创建人：Vasin  <br>
 * 创建日期：2012-2-7 上午10:34:45  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012-2-7 上午10:34:45  <br>
 * 修改备注：     <br>
 */
public class SystemNoticesAction extends Controller {

    private Logger logger = Logger.getLogger(SystemNoticesAction.class);

    /**
     *
     * 方法名称：doGetSystemNotices<br>
     * 方法描述： 获取系统公告列表 <br>
     * @param action
     * @param request
     * @param response
     * @return ActionForward
     * @throws Throwable <br>
     * 版本信息：Ver 1.1 <br>
     *********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012-2-7 上午10:35:15 <br>
     ************* 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012-2-7 上午10:35:15  <br>
     * 修改备注：    <br>
     */
    public ActionForward doGetSystemNoticesList(Action action, HttpServletRequest request,
                                                HttpServletResponse response) throws Throwable {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();

        HashMap<String, String> hashMap = new HashMap<String, String>();
        String noticesDay = StringHelper.obj2str(request.getParameter("noticesDay"),new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String userName = StringHelper.obj2str(request.getParameter("userName"),"");
        hashMap.put("noticesDay", noticesDay);
        hashMap.put("userName", userName);

        logger.debug(hashMap);

        SystemNoticesService systemNoticesService = new SystemNoticesService();

        JSONObject json = systemNoticesService.getSystemNoticesList(hashMap);

        out.print(json);
        logger.info(json);

        logger.debug(json);

        out.close();
        return null;
    }

    /**
     *
     * 方法名称：doGetSystemNoticesDetail<br>
     * 方法描述： 获取详细系统公告信息 <br>
     * @param action
     * @param request
     * @param response
     * @return ActionForward
     * @throws Throwable<br>
     * 版本信息：Ver 1.1 <br>
     *********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012-2-7 下午2:12:19 <br>
     ************* 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012-2-7 下午2:12:19  <br>
     * 修改备注：    <br>
     */
    public ActionForward doGetSystemNoticesDetail(Action action, HttpServletRequest request,
                                                  HttpServletResponse response) throws Throwable {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();

        HashMap<String, String> hashMap = new HashMap<String, String>();
        String noticeId = StringHelper.obj2str(request.getParameter("noticeId"),"");
        String userName = StringHelper.obj2str(request.getParameter("userName"),"");
        hashMap.put("noticeId", noticeId);
        hashMap.put("userName", userName);

        logger.debug(hashMap);

        SystemNoticesService systemNoticesService = new SystemNoticesService();

        JSONObject json = systemNoticesService.getSystemNoticesDetail(hashMap);

        out.print(json);
        logger.info(json);

        logger.debug(json);

        out.close();
        return null;
    }

    /**
     *
     * 方法名称：doUpdateSystemNotices<br>
     * 方法描述： 更新系统公告 <br>
     * @param action
     * @param request
     * @param response
     * @return ActionForward
     * @throws Throwable<br>
     * 版本信息：Ver 1.1 <br>
     *********************************<br>
     * 创建人：Vasin  <br>
     * 创建时间：2012-2-7 下午2:03:53 <br>
     ************* 修改历史  *************<br>
     * 修改人：Vasin   <br>
     * 修改时间：2012-2-7 下午2:03:53  <br>
     * 修改备注：    <br>
     */
    public ActionForward doUpdateSystemNotices(Action action, HttpServletRequest request,
                                               HttpServletResponse response) throws Throwable {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();

        HashMap<String, String> hashMap = new HashMap<String, String>();
        String noticesDay = StringHelper.obj2str(request.getParameter("noticesDay"),new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String userName = StringHelper.obj2str(request.getParameter("userName"),"");
        hashMap.put("noticesDay", noticesDay);
        hashMap.put("userName", userName);

        logger.debug(hashMap);

        SystemNoticesService systemNoticesService = new SystemNoticesService();

        boolean success = systemNoticesService.updateSystemNotices(hashMap);

        out.print(success);

        logger.debug(success);

        out.close();
        return null;
    }
}
