package com.ehl.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.dao.RoadManageDao;
import com.ehl.sm.common.output.BuildChart;

public class HomePageCtrl extends Controller {
	
	private Logger logger = Logger.getLogger(RoadManageDao.class);

	/**
	 * 设置模块主页<br/>
	 * 假如该用户没有权限查看默认页，则跳到左树第一个字节点
	 * Modified by Leisx 2011-11-22
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetHomePage(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		String sID = StringHelper.obj2str(request.getParameter("sID"), "");
		String userName = StringHelper.obj2str(
				request.getParameter("userName"), "");
		String homePage = "";
		String funcId = "";
		
		if(!userName.equals("admin")) {
			String sql = "select distinct id, n_id, N_parent_id, text,isDefault from t_sys_func a, (select distinct funccode from t_sys_privfunc";
			sql += " where privcode in (select distinct privcode from t_sys_rolepriv where rolecode in (select distinct rolecode";
			sql += " from t_sys_userrole, t_sys_user where t_sys_user.username = '"
					+ userName + "' and t_sys_userrole.usercode =";
			sql += " t_sys_user.usercode))) b where a.id = b.funccode and a.n_Parent_Id != '1' and subStr(id, 0, 2) = '"
					+ sID + "' order by id";
			
			Object[][] results = DBHandler.getMultiResult(sql);
			System.out.println("HomePageCtrl.getHomePage.sql:" + sql);
			
			if(results != null) {
				for (int i = 0; i < results.length; i++) {
					if(results[i][4].equals("1")) {
						homePage = (String) results[i][1];
						System.out.println("homePage = " + homePage);
						funcId = (String) results[i][0];
						break;
					}
				}
				
				if(homePage == "") {
					for (int i = 0; i < results.length; i++) {
						if(!((String) results[i][1]).substring(((String) results[i][1]).length() - 4).equals("main")) {
							homePage = String.valueOf(results[i][1]);
							System.out.println("homePage = " + homePage);
							funcId = String.valueOf(results[i][0]);
							break;
						}
					}
				}
			}
			if(homePage == "") {
				homePage = "exception.jsp";
			}
		}
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<hp>\n");
		xmlData.append("<col>" + homePage + "</col>\n");
		xmlData.append("<col>" + funcId + "</col>\n");
		xmlData.append("</hp>\n");
		System.out.println("homePageXml:\n" + xmlData.toString());
		out.write(xmlData.toString());
		out.close();
		return null;
	}
}
