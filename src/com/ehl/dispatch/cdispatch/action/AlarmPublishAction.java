package com.ehl.dispatch.cdispatch.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.cdispatch.core.AlarmPublishCore;

/**
 * 警情信息发布
 * @author Administrator
 *
 */
public class AlarmPublishAction extends Controller {
	
	private AlarmPublishCore core = new AlarmPublishCore();
	
	/**所有支队所有警情数量变量*/
	public final static String ALLZDAllALARMCOUNT_VAR = "allZdAllAlarmCount";
	/**一个支队及其所有辖区大队不同类型警情数量变量*/
	public final static String ZDALLDDALARMCOUNT_VAR = "zdAllDdAlarmCount";
	/**交警提示发布信息变量*/
	public final static String POLICEREMIND_VAR = "policeRemind";
	
	
	/**
	 * 获取所有支队年初至今辖区内发生的警情次数
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllZdAllAlarmCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = core.getAllZdAllAlarmCount();
		request.setAttribute(ALLZDAllALARMCOUNT_VAR, result);
		ActionForward forward = action.findForward("success");
		return forward;
	}
	
	/**
	 * 获取一个支队及其辖区所有大队年初至今辖区内发生的不同类型的警情次数
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetZdAllDdAlarmCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String jgid = request.getParameter("jgid");
		String result = core.getZdAllDdAlarmCount(jgid);
		request.setAttribute(ZDALLDDALARMCOUNT_VAR, result);
		ActionForward forword = action.findForward("success");
		return forword;
	}
	
	/**
	 * 获取交警提示发布信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPoliceRemind(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//Modify by xiayx 2012-3-9
		//发布滚动信息的获取已和系统内滚动信息同步，此处不再获取信息，但保留请求路径，
		//也可直接访问policeRemindPublish.jsp页面
//		Object[][] result = core.getPoliceRemind();
//		request.setAttribute(POLICEREMIND_VAR, result);
		//Modification finished
		ActionForward forward = action.findForward("success");
		return forward;
	}
	
	
}
