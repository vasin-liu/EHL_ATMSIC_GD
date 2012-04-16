package com.ehl.dispatch.notice.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.notice.core.ContentCore;

public class ContentAction extends Controller{
	
	private ContentCore core = new ContentCore();
	
	/**
	 * <pre>
	 * 添加内容
	 * 1.添加发送方内容
	 * 	1.1 notice(id)，content(content,spname,stime)
	 * 2.添加接收方内容
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doAddContent(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String[] params = core.dao.cols;
		Map<String,String> paramMap = FlowUtil.getParams(request, params, true);
		String adcode = request.getParameter("adcode");
		boolean result = core.addContent(paramMap,adcode);
		out.write(String.valueOf(result));
		return null;
	}
}
