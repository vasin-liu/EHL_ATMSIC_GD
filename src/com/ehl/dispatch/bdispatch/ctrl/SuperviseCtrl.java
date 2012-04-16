package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;

public class SuperviseCtrl extends Controller{

	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵����չʾ������Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doGetSupervise (Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		
		try {
			String strSql = "SELECT SUPERVISEID,TO_CHAR(SUPERVISETIME,'YYYY-MM-DD HH24:MI:SS'),SUPERVISEPERSON,F_GET_DEPT(SUPERVISEUNITS),SUPERVISEDESC,ALARMID,REMARK " +
						" FROM T_ATTEMPER_SUPERVISE WHERE ALARMID='" + alarmId + "'" ;
			Object[][] supervise = DBHandler.getMultiResult(strSql);
			
			StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rfXML>\n");
			xmlData.append("<RFWin>\n");
			
			if(supervise != null){
				for (int i=0;i<supervise.length;i++){
					xmlData.append("<row id=\""+i+"Row\">\n");
					xmlData.append("<col>" + StringHelper.obj2str(supervise[i][0],"") + "</col>");
					xmlData.append("<col>" + StringHelper.obj2str(supervise[i][1],"") + "</col>");
					xmlData.append("<col>" + StringUtil.xmlDataFilter(StringHelper.obj2str(supervise[i][2],"")) + "</col>");//
					xmlData.append("<col>" + StringHelper.obj2str(supervise[i][3],"") + "</col>");
					xmlData.append("<col>" + StringHelper.obj2str(supervise[i][4],"") + "</col>");
					xmlData.append("<col>" + StringHelper.obj2str(supervise[i][5],"") + "</col>");
					xmlData.append("<col>" + StringHelper.obj2str(supervise[i][6],"") + "</col>");
					xmlData.append("</row>\n");
				}

			}else{
				xmlData.append("");
			}
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
			
			out.write(xmlData.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.close();
		}
		out.close();
		return null;
	}
}
