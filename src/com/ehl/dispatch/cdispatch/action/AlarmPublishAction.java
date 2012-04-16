package com.ehl.dispatch.cdispatch.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.cdispatch.core.AlarmPublishCore;

/**
 * ������Ϣ����
 * @author Administrator
 *
 */
public class AlarmPublishAction extends Controller {
	
	private AlarmPublishCore core = new AlarmPublishCore();
	
	/**����֧�����о�����������*/
	public final static String ALLZDAllALARMCOUNT_VAR = "allZdAllAlarmCount";
	/**һ��֧�Ӽ�������Ͻ����Ӳ�ͬ���;�����������*/
	public final static String ZDALLDDALARMCOUNT_VAR = "zdAllDdAlarmCount";
	/**������ʾ������Ϣ����*/
	public final static String POLICEREMIND_VAR = "policeRemind";
	
	
	/**
	 * ��ȡ����֧���������Ͻ���ڷ����ľ������
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
	 * ��ȡһ��֧�Ӽ���Ͻ�����д���������Ͻ���ڷ����Ĳ�ͬ���͵ľ������
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
	 * ��ȡ������ʾ������Ϣ
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
		//����������Ϣ�Ļ�ȡ�Ѻ�ϵͳ�ڹ�����Ϣͬ�����˴����ٻ�ȡ��Ϣ������������·����
		//Ҳ��ֱ�ӷ���policeRemindPublish.jspҳ��
//		Object[][] result = core.getPoliceRemind();
//		request.setAttribute(POLICEREMIND_VAR, result);
		//Modification finished
		ActionForward forward = action.findForward("success");
		return forward;
	}
	
	
}
