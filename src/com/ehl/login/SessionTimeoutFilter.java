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
		String craction = uri.substring(uri.lastIndexOf("/")+1);//��ǰ����action���ƻ���jspҳ������
		String lraction = "login.loginGrid.login.d";//��¼����action����
		String lpnameTemp = Setting.getString("redirecturl");//��ȡ��¼ҳ�����Ƶ���ʱ�ַ���
		//Modify by Xiayx 2011-11-14
		//���δ��¼���ɷ��ʵ�ҳ��
		String pages = Constant.nvl(Setting.getString("allow_access_nologin"));//����δ��¼���ɷ��ʵ�ҳ�档allow_access_nologinλ��setting_sys.xml
		//Modification finished
		int glpnameIndex = lpnameTemp.lastIndexOf("/");//��ȡ��¼ҳ�����Ƶ�����
		String lpname = glpnameIndex==-1?lpnameTemp:lpnameTemp.substring(glpnameIndex+1);//��¼ҳ������
		boolean isDispatch = false;//�Ƿ�ת��
		//Modify by Xiayx 2011-11-14
		//���δ��¼���ɷ��ʵ�ҳ��
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
				session.setAttribute(Constants.ERRMESSUSER_KEY, "��¼��ʱ�������µ�¼!");
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
