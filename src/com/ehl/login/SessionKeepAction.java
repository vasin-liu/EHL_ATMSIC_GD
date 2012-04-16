package com.ehl.login;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;

/**
 * ��������ҳ��Ķ�ʱ���󣬱���session������
 * ����session����ʱ��>ҳ������ʱ��
 * @author xiayx
 *
 */
public class SessionKeepAction extends Controller{
	
	/**
	 * ����session
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doKeepSession(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		//���ֻỰ
		return null;
	}
	
	/**
	 * ��ʾsession
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doShowSession(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		System.out.println("showSession");
		HttpSession session = request.getSession(false);
		String msg;
		if(session != null){
			msg = "session exist\n";
			String sid = session.getId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String stime = sdf.format(new Date(session.getCreationTime()));
			String mtime = sdf.format(new Date(session.getLastAccessedTime()));
			String ttime = sdf.format(new Date());
			int timeout = session.getMaxInactiveInterval();
			msg += "sessionId:"+sid+"\n";
			msg += "create time:"+stime+"\n";
			msg += "last accessed time:"+mtime+"\n";
			msg += "this accessed time:"+ttime+"\n";
			msg += "����ʱ��:"+(new Date().getTime()-session.getLastAccessedTime())/1000+"��\n";
			msg += "����ʱ��:"+(timeout/60)+"��\n";
		}else{
			msg = "no session";
		}
		System.out.println(msg);
		return null;
	}
}
