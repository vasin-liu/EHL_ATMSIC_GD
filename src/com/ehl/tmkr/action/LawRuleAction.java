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
import com.ehl.tmkr.core.LawRuleCore;
import com.ehl.tmkr.dao.LawRuleDao;

public class LawRuleAction extends Controller{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *添加法律法规信息<br/>
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
		Map<String, String> lawRule = FlowUtil.getParams(request, LawRuleDao.cnames, true);
		boolean isSuccess = LawRuleCore.add(lawRule) == null ? false : true;
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *获取法律法规信息<br/>
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
		String xml = LawRuleCore.get(id);
		logger.info("xml:\n"+xml);
		System.out.println("xml:\n"+xml);
		out.write(xml);
		out.close();
		return null;
	}
	
	/**
	 *修改法律法规信息<br/>
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
		Map<String, String> lawRule = FlowUtil.getParams(request, LawRuleDao.cnames, true);
		boolean isSuccess = LawRuleCore.modify(lawRule);
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *删除法律法规信息<br/>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDelete(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		boolean isSuccess = LawRuleCore.delete(id);
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
}
