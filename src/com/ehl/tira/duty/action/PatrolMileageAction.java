package com.ehl.tira.duty.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.tira.duty.core.PatrolMileageCore;

/**
 * PatrolMileageAction(巡逻,里程)巡逻里程分析
 * 
 * @author xiayouxue
 * 
 */
public class PatrolMileageAction extends Controller{
	
	private Logger logger = Logger.getLogger(PatrolMileageAction.class);
	private PatrolMileageCore pmCore = new PatrolMileageCore();
	private final String avg = "平均";
	private final String total = "总";
	
	public ActionForward doTotalDept(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化字符集
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取参数
		String area = request.getParameter("area");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//判断参数
		if (area == null || dateS == null || dateE == null ) {
			logger(total, "getParam");
			return null;
		}
		//传递参数，取得数据
		String xml = "";
		xml = pmCore.totalDept(area, dateS, dateE);
		//输出数据
		out.write(xml);
		//页面跳转
		return null;
	}
	
	public ActionForward doTotalTime(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化字符集
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取参数
		String dept = request.getParameter("dept");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//判断参数
		if (dept == null || dateS == null || dateE == null) {
			logger(total, "getParam");
			return null;
		}
		//传递参数，取得数据
		String xml = "";
		xml = pmCore.totalTime(dept, dateS, dateE);
		//输出数据
		out.write(xml);
		//页面跳转
		return null;
	}
	
	public ActionForward doAvgDept(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化字符集
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取参数
		String area = request.getParameter("area");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//判断参数
		if (area == null  || dateS == null || dateE == null) {
			logger(total, "getParam");
			return null;
		}
		//传递参数，取得数据
		String xml = "";
		xml = pmCore.avgDept(area, dateS, dateE);
		//输出数据
		out.write(xml);
		//页面跳转
		return null;
	}
	
	public ActionForward doAvgTime(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化字符集
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取参数
		String dept = request.getParameter("dept");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//判断参数
		if ( dept == null || dateS == null || dateE == null) {
			logger(total, "getParam");
			return null;
		}
		//传递参数，取得数据
		String xml = "";
		xml = pmCore.avgTime(dept, dateS, dateE);
		//输出数据
		out.write(xml);
		//页面跳转
		return null;
	}
	
	
	/**
	 * 巡逻平均里程分析
	 */
	public ActionForward doAnalysis(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化字符集
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取参数
		String area = request.getParameter("area");
		String dept = request.getParameter("dept");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		String isAvgStr = request.getParameter("isAvg");
		if (area == null || dept == null || dateS == null || dateE == null 
				|| isAvgStr == null) {
			logger(total, "getParam");
			return null;
		}
		
		//传递参数，取得数据
		String xml = "";
		xml = pmCore.createPMCX(area, dept, dateS, dateE, Boolean.parseBoolean(isAvgStr));
		//输出数据
		out.write(xml);
		//页面跳转
		return null;
	}

	
	private void logger(String action, String msg){
		Map<String,String> stepM = new HashMap<String, String>();
		stepM.put("getParam", "获取参数");
		logger.error("巡逻"+action+"里程分析->"+stepM.get(msg)+"出错");
	}
}
