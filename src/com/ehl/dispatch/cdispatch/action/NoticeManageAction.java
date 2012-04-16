/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.MaterialInfoDao;
import com.ehl.dispatch.cdispatch.dao.NoticeInfoDao;
import com.ehl.dispatch.cdispatch.dao.RoadManageDao;
import com.ehl.dispatch.common.DispatchUtil;

/**
 * 重大警情控制类
 * @author dxn
 * @date 2010-05-13
 *
 */
public class NoticeManageAction extends Controller{
	
	/**
	 * 取得提醒信息<br/>
	 * 提醒信息的取得处理
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNoticeCount(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reciveunit = (String)DispatchUtil.getCurrentUserData(request).get("BRANCHID");
		NoticeInfoDao nid = new NoticeInfoDao();
		Object[] obj = nid.getNoticeCount(reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}
	
	/**
	 * 取得提醒详细信息<br/>
	 * 提醒详细信息的取得处理
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNoticeList(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//String reportkind = StringHelper.obj2str(request.getParameter("reportkind"),"");
		String reciveunit = (String)DispatchUtil.getCurrentUserData(request).get("BRANCHID");
		NoticeInfoDao nid = new NoticeInfoDao();
		Object[][] obj = nid.getNoticeList(reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		System.out.println("doGetNoticeList.xml==>"+DataToXML.objArrayToXml(obj));
		return null;
	}
	
	/**
	 * 查询唯一通知信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNoticeInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String noticeId = StringHelper.obj2str(request.getParameter("noticeId"),"");
		NoticeInfoDao nid = new NoticeInfoDao();
		Object[] obj = nid.getNoticeInfo(noticeId);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
}