package com.ehl.dispatch.bdispatch.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.Constants;

public class MapInfo extends Controller {
	public ActionForward doGetConfigMapInfo(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");

		//从session中获取登录人员的所属部门ID
		HttpSession session = request.getSession();
		String usercode = (String) session.getAttribute(Constants.PCODE_KEY);		
		String dwmcsql = "SELECT DEPTCODE FROM T_SYS_USER WHERE USERCODE='"+ usercode + "'" ;
		String deptid=DBHandler.getSingleResult(dwmcsql).toString();
		String sql="select x,y,mlevel from t_SYS_department where jgid='"+deptid+"'";
	
		Object[] obj=DBHandler.getLineResult(sql);
		
		
		PrintWriter out = response.getWriter();		
		String str="";
		str=StringHelper.obj2str(obj[0],"")+","+StringHelper.obj2str(obj[1],"")+","+StringHelper.obj2str(obj[2],"");
		
		out.write(str);
		out.close();

		return null;
	}	
}
