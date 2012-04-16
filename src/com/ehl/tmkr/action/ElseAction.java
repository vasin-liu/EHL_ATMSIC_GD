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
import com.ehl.tmkr.core.ElseCore;
import com.ehl.tmkr.dao.ElseDao;

public class ElseAction extends Controller{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *添加其他知识信息<br/>
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
		Map<String, String> elseInfo = FlowUtil.getParams(request, ElseDao.cnames, true);
		boolean isSuccess = ElseCore.add(elseInfo) == null ? false : true;
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
	/**
	 *获取知识库其他信息<br/>
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
		String xml = ElseCore.get(id);
		logger.info("xml:\n"+xml);
		System.out.println("xml:\n"+xml);
		out.write(xml);
		out.close();
		return null;
	}
	
	/**
	 *修改知识库其他信息<br/>
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
		Map<String, String> elseInfo = FlowUtil.getParams(request, ElseDao.cnames, true);
		boolean isSuccess = ElseCore.modify(elseInfo);
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}
	
}
