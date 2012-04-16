package com.ehl.base;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.common.FlowUtil;

/**
 * 基本控制类
 * 
 * @author Xiayx
 * 
 */
public abstract class BaseAction extends Controller {

    /** 日志类 */
    private Logger logger;

    /** 基本业务逻辑类 */
    private BaseCore core;

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
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	String[] cnames = core.getDao().getCnames();
	Map<String, String> object = FlowUtil.getParams(request, cnames, true);
	String id = core.insert(object);
	out.write(String.valueOf(id != null));
	out.close();
	return null;
    }

    /**
     * 获取信息，通过主键编号
     * 
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doGetById(Action action, HttpServletRequest request,
	    HttpServletResponse response) throws Throwable {
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	String id = request.getParameter(core.getDao().getPk());
	String xml = core.getById(id);
	logger.info("xml:\n"+xml);
	out.write(xml);
	out.close();
	return null;
    }

    /**
     * 修改信息，通过主键编号
     * 
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doModifyById(Action action,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Throwable {
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	String[] cnames = core.getDao().getCnames();
	Map<String, String> object = FlowUtil.getParams(request, cnames, true);
	boolean isSuccess = core.modifyById(object);
	out.write(String.valueOf(isSuccess));
	out.close();
	return null;
    }

    /**
     * 删除信息，通过主键编号
     * 
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doDeleteById(Action action,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Throwable {
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	String id = request.getParameter(core.getDao().getPk());
	boolean isSuccess = core.deleteById(id);
	out.write(String.valueOf(isSuccess));
	out.close();
	return null;
    }

    public Logger getLogger() {
	return logger;
    }

    public void setLogger(Logger logger) {
	this.logger = logger;
    }

    public BaseCore getCore() {
	return core;
    }

    public void setCore(BaseCore core) {
	this.core = core;
    }
}
