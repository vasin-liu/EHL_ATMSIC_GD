package com.ehl.policeWorks.newsFiles.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.policeWorks.newsFiles.core.OrderContributionCore;
import com.ehl.policeWorks.newsFiles.dao.OrderContributionDao;
import com.ehl.sm.base.Constant;

/**
 * 
 */
public class OrderContributionAction extends Controller {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *添加约稿信息<br/>
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
		Map<String, String> order = FlowUtil.getParams(request, OrderContributionDao.cnames, true);
		String ajgids = request.getParameter("ajgids");
		boolean isSuccess = OrderContributionCore.add(order, ajgids) == null ? false : true;
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *获取约稿信息<br/>
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
		String jgid = (String)request.getSession(false).getAttribute(Constant.JGID_VAR);
		String orderXml = OrderContributionCore.get(id,jgid);
		logger.info("orderXml:\n"+orderXml);
		System.out.println("orderXml:\n"+orderXml);
		out.write(orderXml);
		out.close();
		return null;
	}
	
	/**
	 *修改约稿信息<br/>
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
		Map<String, String> order = FlowUtil.getParams(request, OrderContributionDao.cnames, true);
		String ajgids = request.getParameter("ajgids");
		boolean isSuccess = OrderContributionCore.modify(order, ajgids);
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *查询约稿提醒信息<br/>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSearchPromptInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 
		PrintWriter out = response.getWriter();
		String jgid = (String)request.getSession(false).getAttribute(Constant.JGID_VAR);
		String xml = OrderContributionCore.searchUnSign(jgid);
		out.write(xml);
		out.flush();
		out.close();
		return null;
	}
}