package com.ehl.dynamicinfo.roadRepair.action;

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

public class roadShowAction extends Controller {
	Logger logger = Logger.getLogger(roadShowAction.class);
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetroadBuildAllInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		PrintWriter out = response.getWriter();
	 	
		// 警情编号,道路名称,施工方向,X,Y
		String sql = "  select B.ALARMID, B.roadid, B.ROADDIRECTION, B.x, B.y " + 
		  "  from  t_attemper_alarm B, T_ATTEMPER_ROADBUILD C " + 
		  "  where " + 
		  "  B.ALARMID = C.ALARMID " + 
		  "  and B.Eventtype = '001023' " + 
		  "  and B.x is not null " + 
		  "  and b.y is not null  and B.EVENTSTATE = '570005' " + 
		  "  order by B.ALARMTIME desc ";
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
	public ActionForward doGetroadBuild(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

//		获取传送过来的经纬度信息
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
	
		// 车辆编号,车牌号码,部门名称,车辆状态,X,Y
		// 警情编号,道路名称,施工方向,X,Y
		String sql = "  select B.ALARMID, B.roadid , B.ROADDIRECTION, B.x, B.y, B.roadname,B.CASEHAPPENTIME,B.CASEENDTIME,B.roadname,C.PLAN " + 
		  "    from  t_attemper_alarm B, T_ATTEMPER_ROADBUILD C " + 
		  "  where " + 
		  "  B.ALARMID = C.ALARMID " + 
		  "  and B.ALARMID = '"  + alarmId + "'" +
		  "  and B.Eventtype = '001023' " +
		  "  and B.x is not null " + 
		  "  and b.y is not null " + 
		  "  order by B.ALARMTIME desc ";
		System.out.println("***********"+sql);

		// 生成显示信息
		StringBuffer sbXml = new StringBuffer(); 
		try {
			Object[][] oResult = DBHandler.getMultiResult(sql);
			
			if (oResult != null) {
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>道路名称：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][1],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>路段名称：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][8],"") + "</td></tr>");
				//sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>方向：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][2],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>施工开始时间：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][6],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>施工结束时间：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][7],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>分流管制措施：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][9],"") + "</td></tr>");
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
