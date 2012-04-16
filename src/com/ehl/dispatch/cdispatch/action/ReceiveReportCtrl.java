package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.ReceiveReport;

/**
 * @author wxt
 *
 */
public class ReceiveReportCtrl extends Controller{
	/**
	 * @作者: wangxt
	 * @版本号：1.0
	 * @函数说明：插入接报信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-10
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doAddReceiveReport (Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		List<String> result = new ArrayList<String>();
		String feedbackid = StringHelper.obj2str(request.getParameter("feedbackid"), "");
		String alarmid = StringHelper.obj2str(request.getParameter("alarmid"), "");
		String receiveunit = StringHelper.obj2str(request.getParameter("deptname"));
		String receivetype = StringHelper.obj2str(request.getParameter("receivetype"));
		String feedbacktime = StringHelper.obj2str(request.getParameter("receive_time"), "");
		String feedbackperson =  StringHelper.obj2str(request.getParameter("duty_person"), "");
		String pass_person = StringHelper.obj2str(request.getParameter("pass_person"), "");
		String feedbackdesc = StringHelper.obj2str(request.getParameter("infoVerify_Accident"), "");
		String feedbackreport =  StringHelper.obj2str(request.getParameter("reportHead_Accident"), "");
		String depttype = StringHelper.obj2str(request.getParameter("depttype"),"");
		String updatestate = StringHelper.obj2str(request.getParameter("updatestate"),"");
		result.add(feedbackid);
		result.add(alarmid);
		result.add(receiveunit);
		result.add(receivetype);
		result.add(feedbacktime);
		result.add(feedbackperson);
		result.add(pass_person);
		result.add(feedbackdesc);
		result.add(feedbackreport);
		ReceiveReport rr =  new ReceiveReport();
		boolean flag = rr.insertReceiveReport(result,depttype,updatestate);
	    if(flag){
	    	out.write("success");
	    }else {
	    	out.write("fail");
	    }
	    out.close();
		return null;
	}
	/**
	 * @作者: wangxt
	 * @版本号：1.0
	 * @函数说明：查询接报信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-10
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doGetReceiveReport(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String flowid = StringHelper.obj2str(request.getParameter("flowid"),"");
		ReceiveReport rr =  new ReceiveReport();
		Object[] result = rr.getReceiveReport(flowid);
		String str = DataToXML.objArrayToXml(result);
		out.write(str);
		out.close();
		return null;
	}
	
	/**
	 * @作者: wangxt
	 * @版本号：1.0
	 * @函数说明：更改已经接受状态
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-10
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doGetReceiveEventState(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String flowid = StringHelper.obj2str(request.getParameter("flowid"),"");
		ReceiveReport rr =  new ReceiveReport();
		boolean flag = rr.updateEventState(flowid);
		if(flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
		out.close();
		return null;
	}

}
