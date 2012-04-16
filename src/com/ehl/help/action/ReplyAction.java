package com.ehl.help.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.mapping.ActionForward;
import com.ehl.base.BaseAction;
import com.ehl.base.BaseCore;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.help.Core.ReplyCore;
import com.ehl.util.Collections;

/**
 * 回复控制类
 * 
 * @author xiayouxue
 * 
 */
public class ReplyAction extends BaseAction {

	/** 日志类 */
	public Logger logger = Logger.getLogger(this.getClass());

	/** 基本业务逻辑类 */
	public BaseCore core = new ReplyCore();
	
	public ReplyAction(){
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
		String id = core.insert(object);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(id != null));
		out.close();
		
		return null;
    }
}
