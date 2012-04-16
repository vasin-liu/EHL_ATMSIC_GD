package com.ehl.login;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.common.Setting;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.Constants;

public class SessionTimeoutFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String uri = request.getRequestURI();
		String craction = uri.substring(uri.lastIndexOf("/")+1);//当前请求action名称或者jsp页面名称
		String lraction = "login.loginGrid.login.d";//登录请求action名称
		String lpnameTemp = Setting.getString("redirecturl");//获取登录页面名称的临时字符串
		//Modify by Xiayx 2011-11-14
		//添加未登录即可访问的页面
		String pages = Constant.nvl(Setting.getString("allow_access_nologin"));//允许未登录即可访问的页面。allow_access_nologin位于setting_sys.xml
		//Modification finished
		int glpnameIndex = lpnameTemp.lastIndexOf("/");//获取登录页面名称的索引
		String lpname = glpnameIndex==-1?lpnameTemp:lpnameTemp.substring(glpnameIndex+1);//登录页面名称
		boolean isDispatch = false;//是否转发
		//Modify by Xiayx 2011-11-14
		//添加未登录即可访问的页面
//		if (!craction.equals("") && !craction.equals(lraction) && !craction.equals(lpname)) {
		if (!craction.equals("") && !craction.equals(lraction)
				&& !craction.equals(lpname) && pages.indexOf(craction) == -1) {
		//Modification finished
			HttpSession session = request.getSession(false);
			if (session != null) {
				if(session.getAttribute(Constants.PNAME_KEY) == null){
					isDispatch = true;
				}else{
					long ltime = session.getLastAccessedTime();
					long ctime = new Date().getTime();
					int outtime = session.getMaxInactiveInterval();
					if ((ctime - ltime) / 1000 > outtime){
						isDispatch = true;
					}
				}
			}else{
				session = request.getSession();
				isDispatch = true;
			}
			if(isDispatch){
				session.setAttribute(Constants.ERRMESSUSER_KEY, "登录超时，请重新登录!");
				String cntxtPath = request.getContextPath()+"/";
				String lppath = request.getRequestURL().toString().replace(uri, "")+cntxtPath+lpname;
				HttpServletResponse response = (HttpServletResponse)arg1;
				response.sendRedirect(lppath);
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
