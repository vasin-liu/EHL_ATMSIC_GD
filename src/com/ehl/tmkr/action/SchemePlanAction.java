package com.ehl.tmkr.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tmkr.core.SchemePlanCore;
import com.ehl.tmkr.dao.SchemePlanDao;

public class SchemePlanAction extends Controller{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *添加预案方案管理信息<br/>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doAdd(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		Map<String, String> order = FlowUtil.getParams(request, SchemePlanDao.cnames, true);
		boolean isSuccess = SchemePlanCore.add(order) == null ? false : true;
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *获取预案方案管理信息<br/>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGet(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String xml = SchemePlanCore.get(id);
		logger.info("orderXml:\n"+xml);
		System.out.println("orderXml:\n"+xml);
		out.write(xml);
		out.close();
		return null;
	}
	
	/**
	 *修改预案方案管理信息<br/>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModify(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 
		PrintWriter out = response.getWriter();
		Map<String, String> planManage = FlowUtil.getParams(request, SchemePlanDao.cnames, true);
		boolean isSuccess = SchemePlanCore.modify(planManage);
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
}
