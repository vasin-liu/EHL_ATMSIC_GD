package com.ehl.help.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.mapping.ActionForward;
import com.ehl.base.BaseAction;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.help.Core.LeaveCore;
import com.ehl.help.Core.ReplyCore;
import com.ehl.help.dao.LeaveDao;
import com.ehl.sm.base.Constant;
import com.ehl.sm.core.CodeCore;
import com.ehl.sm.core.FuncCore;
import com.ehl.util.Collections;

/**
 * 留言控制类
 * 
 * @author xiayouxue
 * 
 */
public class LeaveAction extends BaseAction {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 基本业务逻辑类 */
	private LeaveCore core = new LeaveCore();
	private CodeCore codeCore = new CodeCore();
	private FuncCore funcCore = new FuncCore();
	private ReplyCore replyCore = new ReplyCore();
	
	public LeaveAction() {
		super.setLogger(logger);
		super.setCore(core);
	}
	
	/**
     * 添加信息
     * 
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doInsert(Action action, HttpServletRequest request,
	    HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		logger.info("params:"+JSONObject.fromObject(Collections.changeSingleArray(request.getParameterMap())));
		String[] cnames = core.getDao().getCnames();
		Map<String, String> object = FlowUtil.getParams(request, cnames, true);
		object.put("time", Constant.getCurrentTime(false));
		object.put("jgid", (String)request.getSession().getAttribute(Constant.JGID_VAR));
		object.put("state", "000001");
		String id = core.insert(object);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(id != null));
		out.close();
		
		return null;
    }
	
	/**
	 * 跳转至明细界面
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doToDetailPage(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
        Map<String,Object> params = Collections.changeSingleArray(request.getParameterMap());
		logger.info("params:"+JSONObject.fromObject(params));
		String pageType = request.getParameter("pageType");
		pageType = pageType == null ? "0" : pageType;
		request.setAttribute("pageType", pageType);
		request.setAttribute("listJjcd", codeCore.select(LeaveDao.JJCD));
		request.setAttribute("listFklx", codeCore.select(LeaveDao.FKLX));
		request.setAttribute("jsonFunc", funcCore.select(null));
		request.setAttribute("listState", codeCore.select(LeaveDao.STATE));
		logger.info(request.getAttribute("jsonFunc"));
		if(!pageType.equals("0")){
			String id = request.getParameter("id");
			JSONObject array = core.getDetail(id);
			request.setAttribute(core.getOname(), array);
			logger.info("result:"+array);
		}
		return action.findForward("success");
	}
	
	/**
	 * 跳转至明细界面
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doToQueryPage(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
        Map<String,Object> params = Collections.changeSingleArray(request.getParameterMap());
		logger.info("params:"+JSONObject.fromObject(params));
		request.setAttribute("listState", codeCore.select(LeaveDao.STATE));
		request.setAttribute("listJjcd", codeCore.select(LeaveDao.JJCD));
		request.setAttribute("listFklx", codeCore.select(LeaveDao.FKLX));
		request.setAttribute("jsonFunc", funcCore.select(null));
		return action.findForward("success");
	}
	
	
	
	/**
	 * 查询
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSelect(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
        Map<String,Object> params = Collections.changeSingleArray(request.getParameterMap());
		logger.info("params:"+JSONObject.fromObject(params));
		JSONObject object = core.select(params);
		logger.info("result:" + object);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(object.toString());
        out.flush();
        out.close();
		return null;
	}
	
	/**
	 * 修改
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyById(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
        Map<String,Object> params = Collections.changeSingleArray(request.getParameterMap());
		logger.info("params:"+JSONObject.fromObject(params));
		Map<String,String> leaveParam = FlowUtil.getParams(request, core.getDao().getCnames(), true);
		boolean result = core.modifyById(leaveParam);
		logger.info("result:" + result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(String.valueOf(result));
        out.flush();
        out.close();
		return null;
	}
	
	/**
     * 回复
     * 
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doReply(Action action, HttpServletRequest request,
	    HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		logger.info("params:"+JSONObject.fromObject(Collections.changeSingleArray(request.getParameterMap())));
		Map<String,String> leaveParam = new HashMap<String, String>();
		leaveParam.put("id", request.getParameter("lid"));
		leaveParam.put("state", request.getParameter("state"));
		boolean result = core.modifyById(leaveParam);
		if(result){
			String[] cnames = replyCore.getDao().getCnames();
			Map<String, String> object = FlowUtil.getParams(request, cnames, true);
			result = replyCore.insert(object) != null;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(result));
		out.close();
		
		return null;
    }
	
	
}
