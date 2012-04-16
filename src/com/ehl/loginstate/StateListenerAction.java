/**
 * 
 */
package com.ehl.loginstate;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.ehl.cctv.VidiconInfoMap;
import com.ehl.dispatch.cdispatch.core.DataToXML;

/**
 * 取得在线人数<br/>
 *
 */
public class StateListenerAction extends Controller{
	Logger logger = Logger.getLogger(VidiconInfoMap.class);
	/**
	 * 通过流水号取得一条续报信息<br/>
	 * 通过流水号取得一条续报信息的处理
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doShowOnlineUsers(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			// 取得在线人数sql
			String sql = "select deptname ||'：' || '在线人数' || count(1)  || '人' " + 
							" from T_SYS_USERSTATE group by deptid, deptname order by deptid";
			
			Object[][] result = null;
			result = DBHandler.getMultiResult(sql.toString());
			out.write(DataToXML.objArrayToXml(result));
			System.out.println(DataToXML.objArrayToXml(result));
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("取得各个部门在线人数时出错！");
		}
		
		return null;
	}
}