package com.ehl.dynamicinfo.accNow.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;

public class accNowAction extends Controller {
	Logger logger = Logger.getLogger(accNowAction.class);
	
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllAccNowInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
	 	// ��������,�¼�����,�¼�״̬,X,Y
		String sql = " select ALARMID," +
	       			" (select name from t_attemper_code where id = '001024') EVENTTYPE," +
	       			" (select name from t_attemper_code where id = EVENTSTATE) EVENTSTATE, x,Y" +
					" from T_ATTEMPER_ALARM_ZD" +
					" where to_char(ALARMTIME,'yyyy-mm-dd') = to_char(SYSDATE,'yyyy-mm-dd') " +
					" and x is not null and y is not null  and EVENTSTATE != '004032' " +
					" order by ALARMTIME desc";
	 	
		System.out.println("***********"+sql);
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
	public ActionForward doGetAccNowInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");

		// �¹ʱ�ţ��¹ʷ���ʱ�䣬�¹�����,�¹ʱ���
		String sql = " select taa.alarmid,taa.casehappentime,taa.alarmdesc,taa.title,taa.ALARMREGION ," +
				"tcc.DEATHPERSONCOUNT ,tcc.BRUISEPERSONCOUNT from T_ATTEMPER_ALARM_ZD taa, T_ATTEMPER_ACCIDENT_ZD tcc";
		sql += " where taa.alarmid = tcc.alarmid and taa.alarmid = '" +alarmId+ "'";
		// ������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		try {
			Object[] oResult = DBHandler.getLineResult(sql);
			
			if (oResult != null) {
				String str = sdf0.format(oResult[1]);
				String showStr =  str.substring(0,4) + "��" + str.substring(4,6) + "��" + str.substring(6,8) + "��" + str.substring(8,10) + "ʱ" + str.substring(10,12) + "��";  
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�¹ʷ���ʱ�䣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + StringHelper.obj2str(showStr,"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�¹ʱ��⣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[3],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�¹�Ͻ����</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[4],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>����������</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[5],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>����������</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[6],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>���������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[2],"") + "</td></tr>");
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
