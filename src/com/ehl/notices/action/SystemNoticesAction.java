
/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
 * �ļ�·����com.ehl.notices.action <br>
 * �ļ����ƣ�SystemNoticesAction.java <br>
 * �ļ���ţ�   <br>
 * �ļ�������  <br>
 *
 * �汾��Ϣ�� Ver 1.1 <br>
 * �������ڣ�2012-2-7 <br>
 * ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����    <br>
 **************************************************<br>
 * �����ˣ�Vasin  <br>
 * �������ڣ�2012-2-7 ����10:34:45 <br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2012-2-7 ����10:34:45   <br>
 * �޸ı�ע��   <br>
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
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.notices.action  <br>
 * �����ƣ�SystemNotices  <br>
 * �ļ�������ϵͳ����   <br>
 *
 * @see <br>
 * �汾��Ϣ��Ver 1.1 <br>
 *********************************<br>
 * �����ˣ�Vasin  <br>
 * �������ڣ�2012-2-7 ����10:34:45  <br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2012-2-7 ����10:34:45  <br>
 * �޸ı�ע��     <br>
 */
public class SystemNoticesAction extends Controller {

    private Logger logger = Logger.getLogger(SystemNoticesAction.class);

    /**
     *
     * �������ƣ�doGetSystemNotices<br>
     * ���������� ��ȡϵͳ�����б� <br>
     * @param action
     * @param request
     * @param response
     * @return ActionForward
     * @throws Throwable <br>
     * �汾��Ϣ��Ver 1.1 <br>
     *********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012-2-7 ����10:35:15 <br>
     ************* �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012-2-7 ����10:35:15  <br>
     * �޸ı�ע��    <br>
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
     * �������ƣ�doGetSystemNoticesDetail<br>
     * ���������� ��ȡ��ϸϵͳ������Ϣ <br>
     * @param action
     * @param request
     * @param response
     * @return ActionForward
     * @throws Throwable<br>
     * �汾��Ϣ��Ver 1.1 <br>
     *********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012-2-7 ����2:12:19 <br>
     ************* �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012-2-7 ����2:12:19  <br>
     * �޸ı�ע��    <br>
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
     * �������ƣ�doUpdateSystemNotices<br>
     * ���������� ����ϵͳ���� <br>
     * @param action
     * @param request
     * @param response
     * @return ActionForward
     * @throws Throwable<br>
     * �汾��Ϣ��Ver 1.1 <br>
     *********************************<br>
     * �����ˣ�Vasin  <br>
     * ����ʱ�䣺2012-2-7 ����2:03:53 <br>
     ************* �޸���ʷ  *************<br>
     * �޸��ˣ�Vasin   <br>
     * �޸�ʱ�䣺2012-2-7 ����2:03:53  <br>
     * �޸ı�ע��    <br>
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
