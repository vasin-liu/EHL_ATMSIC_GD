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
 * ȡ����������<br/>
 *
 */
public class StateListenerAction extends Controller{
	Logger logger = Logger.getLogger(VidiconInfoMap.class);
	/**
	 * ͨ����ˮ��ȡ��һ��������Ϣ<br/>
	 * ͨ����ˮ��ȡ��һ��������Ϣ�Ĵ���
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
			// ȡ����������sql
			String sql = "select deptname ||'��' || '��������' || count(1)  || '��' " + 
							" from T_SYS_USERSTATE group by deptid, deptname order by deptid";
			
			Object[][] result = null;
			result = DBHandler.getMultiResult(sql.toString());
			out.write(DataToXML.objArrayToXml(result));
			System.out.println(DataToXML.objArrayToXml(result));
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("ȡ�ø���������������ʱ����");
		}
		
		return null;
	}
}