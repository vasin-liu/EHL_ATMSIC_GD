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
 * 接收来自页面的定时请求，保持session不过期
 * 设置session过期时间>页面请求时间
 * @author xiayx
 *
 */
public class SessionKeepAction extends Controller{
	
	/**
	 * 保持session
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doKeepSession(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		//保持会话
		return null;
	}
	
	/**
	 * 显示session
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
			msg += "持续时间:"+(new Date().getTime()-session.getLastAccessedTime())/1000+"秒\n";
			msg += "过期时间:"+(timeout/60)+"分\n";
		}else{
			msg = "no session";
		}
		System.out.println(msg);
		return null;
	}
}
