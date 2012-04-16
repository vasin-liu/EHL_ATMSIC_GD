package com.ehl.dynamicinfo.tgs.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dynamicinfo.tgs.core.BayonetFlowCore;

/**
 * 卡口流量控制类
 * @author xiayx
 *
 */
public class BayonetFlowAction extends Controller{
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BayonetFlowCore core = new BayonetFlowCore();
	
	
	
	
	/**
	 *  统计一段日期内，各个卡口未获取到流量的天数
	 */
	public ActionForward doStatisWrongDays(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String state = request.getParameter("state");
		JSONObject json = core.statisWrongDays(id, date, state);
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}
	
	/**
	 *  统计一段日期内，某个卡口每天未获取到流量的小时数
	 */
	public ActionForward doStatisWrongHours(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		JSONObject json = core.statisWrongHours(id,date);
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}
	
	/**
	 *  获取某个卡口某天各个小时的流量值
	 */
	public ActionForward doGetFlow(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		JSONObject json = core.getFlow(id,date);
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}
	
	/**
	 *  查看各个卡口视频状态，能否查看视频
	 */
	public ActionForward doWatchVideoState(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		String state = request.getParameter("state");
		JSONObject json = core.watchVideoState(state);
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}
	
	/**
	 *  查看各个卡口视频状态，能否查看视频
	 */
	public ActionForward doGetBayonet(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		JSONArray json = core.getBayonet();
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}
	
	
}
