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
 * ��������������
 * @author xiayx
 *
 */
public class BayonetFlowAction extends Controller{
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BayonetFlowCore core = new BayonetFlowCore();
	
	
	
	
	/**
	 *  ͳ��һ�������ڣ���������δ��ȡ������������
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
	 *  ͳ��һ�������ڣ�ĳ������ÿ��δ��ȡ��������Сʱ��
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
	 *  ��ȡĳ������ĳ�����Сʱ������ֵ
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
	 *  �鿴����������Ƶ״̬���ܷ�鿴��Ƶ
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
	 *  �鿴����������Ƶ״̬���ܷ�鿴��Ƶ
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
