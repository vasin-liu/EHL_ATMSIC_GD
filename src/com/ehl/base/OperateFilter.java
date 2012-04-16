package com.ehl.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.base.Constant;

public class OperateFilter implements Filter {

	private Logger logger = Logger.getLogger(this.getClass());

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		request.setCharacterEncoding("utf-8");
		// 如果参数中含有funcId表示跳转到一个功能界面，
		// 需要获取用户在该功能上的操作，否则不获取操作信息
		String funcId = request.getParameter("funcId");
		if (funcId != null) {
			HttpSession session = request.getSession();
			String uname = (String) session.getAttribute(Constant.UNAME_VAR);
			String siftFunc = " and";
			if (funcId.equals("")) {
				String url = request.getRequestURI();
				int index = url.indexOf(".");
				if (index != -1) {
					url = url.substring(0, index);
				}
				String funcSql = "select id from t_sys_func where '" + url
						+ "' like '%'||n_id and rownum<=1";
				siftFunc += " pf.funccode = (" + funcSql + ")";
			} else {
				if (funcId.length() >= 6) {
					if (funcId.substring(2, 4).equals("00")) {
						funcId = funcId.substring(0, 2);
					} else if (funcId.substring(4, 6).equals("00")) {
						funcId = funcId.substring(0, 4);
					}
				}
				siftFunc += " substr(pf.funccode,1," + funcId.length() + ")='"
						+ funcId + "'";
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct pf.funccode,o.buttoncode");
			sql.append(" from T_SYS_USER u,T_SYS_USERROLE ur,T_SYS_ROLE r")
					.append(",T_SYS_ROLEPRIV rp,T_SYS_PRIV p,T_SYS_PRIVFUNC pf,T_SYS_OPERATION o");
			sql.append(" where u.usercode = ur.usercode")
					.append(" and ur.rolecode = r.rolecode and r.rolecode = rp.rolecode ")
					.append(" and rp.privcode = p.privcode and p.privcode = pf.privcode ")
					.append(" and u.username = '" + uname + "' ")
					.append(siftFunc)
					.append(" and instr(pf.optlist, o.optcode) != 0 ");
			sql.append(" order by pf.funccode,o.buttoncode");
			String message = "获取" + uname + "在" + funcId + "上的权限";
			Object[][] objects = FlowUtil.readMilte(sql.toString(), logger,
					message);
			if (objects != null) {
				String func = "", operates = "";
				for (int i = 0; i < objects.length; i++) {
					if (func.equals((String) objects[i][0])) {
						operates += "," + objects[i][1];
					} else {
						func = (String) objects[i][0];
						operates += ";" + func + "," + objects[i][1];
					}
				}
				operates = operates.substring(1);
				session.removeAttribute(Constant.OPERATE_VAR);
				request.setAttribute(Constant.OPERATE_VAR, operates);
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
