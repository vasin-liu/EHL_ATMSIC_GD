package com.ehl.dynamicinfo.forbid.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;

public class ForbidAction extends Controller {
	Logger logger = Logger.getLogger(ForbidAction.class);
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllForbidInfo(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String sql = "SELECT taa.ALARMID,taa.ROADNAME,taa.ROADDIRECTION,taa.X,taa.Y FROM T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat ";
		sql += " WHERE taa.ALARMID=tat.ALARMID  and taa.X is not null and taa.Y is not null  and taa.EVENTSTATE = '570003' ";
		sql +=" ORDER BY taa.ALARMID DESC";
		Object[][] res = DBHandler.getMultiResult(sql);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		return null;
	}
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetForbidInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

//		��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");

//		 ���Ʊ�ţ����Ʒ���ʱ�䣬���ƽ���ʱ�䣬���Ʒ�������
		String sql = " select taa.alarmid,to_char(taa.casehappentime,'yyyy-mm-dd hh24:mi:ss')," +
				" to_char(taa.caseendtime,'yyyy-mm-dd hh24:mi:ss'),tat.plan, taa.ROADID, taa.ROADNAME, taa.ROADDIRECTION" +
				" from T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat";
		sql += " where taa.alarmid = '" +alarmId+ "' and taa.alarmid = tat.alarmid";
		System.out.println(sql);
//		 ������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();
		try {
			Object[] oResult = DBHandler.getLineResult(sql);
			
			if (oResult != null) {
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>��·���ƣ�</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + StringHelper.obj2str(oResult[4],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>·�����ƣ�</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + StringHelper.obj2str(oResult[5],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>����</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + StringHelper.obj2str(oResult[6],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>���ƿ�ʼʱ�䣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + StringHelper.obj2str(oResult[1],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>���ƽ���ʱ�䣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[2],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>���Ʒ���������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[3],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td colspan='2'>&nbsp;</td></tr>");
				sbXml.append("</table>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();

		out.write(sbXml.toString());
		out.close();
		return null;

	}
}
