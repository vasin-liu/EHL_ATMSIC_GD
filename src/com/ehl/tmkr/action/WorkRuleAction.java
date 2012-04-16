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
import com.ehl.tmkr.core.WorkRuleCore;
import com.ehl.tmkr.dao.WorkRuleDao;

public class WorkRuleAction extends Controller{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *添加工作规定信息<br/>
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
		Map<String, String> workRule = FlowUtil.getParams(request, WorkRuleDao.cnames, true);
		boolean isSuccess = WorkRuleCore.add(workRule) == null ? false : true;
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *获取工作规定信息<br/>
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
		String xml = WorkRuleCore.get(id);
		logger.info("xml:\n"+xml);
		System.out.println("xml:\n"+xml);
		out.write(xml);
		out.close();
		return null;
	}
	
	/**
	 *修改工作规定信息<br/>
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
		Map<String, String> workRule = FlowUtil.getParams(request, WorkRuleDao.cnames, true);
		boolean isSuccess = WorkRuleCore.modify(workRule);
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
}
