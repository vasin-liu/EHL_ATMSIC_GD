package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.cdispatch.dao.BayonetCartogramDao;
import com.ehl.dispatch.cdispatch.util.BayonetCartogramUtil;

public class BayonetCountsActoin extends Controller {

	private Object[][] object;

	public ActionForward doGetData(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");

		
		String CountType = request.getParameter("CountType"); // 统计类型
		String timeRadioType = request.getParameter("timeRadioType");
		String STARTTIME = request.getParameter("STARTTIME");
		String ENDTIME = request.getParameter("ENDTIME");
		String startyearId = request.getParameter("startyearId");
		String startmonthId = request.getParameter("startmonthId");
		String endyearId = request.getParameter("endyearId");
		String endmonthId = request.getParameter("endmonthId");
		String hours = request.getParameter("hours");
		String roadname = request.getParameter("roadname");
		String departType = request.getParameter("departType");
		String timeStr = request.getParameter("timeStr");
		Object[] objects = roadname.split("、");
		
		PrintWriter out = response.getWriter();
		BayonetCartogramDao bcd = new BayonetCartogramDao();
		BayonetCartogramUtil bcu = new BayonetCartogramUtil();
		
		if (CountType.equals("1")) {
			if (timeRadioType.equals("7") && CountType.equals("1")) {
				object = bcd.getYearDate(bcu.getRoadNameSql(objects), bcu
						.getDateSql(timeRadioType, STARTTIME, ENDTIME,
								startyearId, startmonthId, endyearId,
								endmonthId, hours, timeStr, CountType), bcu
						.getRoadType(departType), bcu.getResult(timeRadioType));
				System.out.println(bcu.getRoadXMl(object, objects));
			} else {
				object = bcd.getDate(bcu.getRoadNameSql(objects), bcu
						.getDateSql(timeRadioType, STARTTIME, ENDTIME,
								startyearId, startmonthId, endyearId,
								endmonthId, hours, timeStr, CountType), bcu
						.getRoadType(departType), bcu.getResult(timeRadioType));
				System.out.println(bcu.getRoadXMl(object, objects));
			}
			out.write(bcu.getRoadXMl(object, objects));
		} else {
			if (timeRadioType.equals("1")&&ENDTIME.equals("")) {
				object = bcd.getDate(bcu.getRoadNameSql(objects), bcu
						.getDateSql(timeRadioType, STARTTIME, ENDTIME,
								startyearId, startmonthId, endyearId,
								endmonthId, hours, timeStr, CountType), bcu
						.getRoadType(departType), bcu.getResult(timeRadioType));
				out.write(bcu.getRoadXMl(object, objects));
			}else if(timeRadioType.equals("3")&&endmonthId.equals("")){
				object = bcd.getDate(bcu.getRoadNameSql(objects), bcu
						.getDateSql(timeRadioType, STARTTIME, ENDTIME,
								startyearId, startmonthId, endyearId,
								endmonthId, hours, timeStr, CountType), bcu
						.getRoadType(departType), bcu.getResult(timeRadioType));
				out.write(bcu.getRoadXMl(object, objects));
			}else{
				out.write(bcu.getCreateCountXML(startyearId, endyearId, STARTTIME, ENDTIME,
					startmonthId, endmonthId, objects, departType,
					timeRadioType));
				
			}
		}
		out.close();
		return null;
	}

	public ActionForward doGetRoadDate(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out = response.getWriter();
		BayonetCartogramDao bcd = new BayonetCartogramDao();
		BayonetCartogramUtil bcu = new BayonetCartogramUtil();

		out.write(bcu.CreateRoadlXMl(bcd.getDate()));
		out.close();
		return null;
	}

	public ActionForward doGetTime(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");

		String start = request.getParameter("start");
		String end = request.getParameter("end");

		PrintWriter out = response.getWriter();
		BayonetCartogramUtil bcu = new BayonetCartogramUtil();
		System.out.println(bcu.sCalendarLundarToSolar(start, end));
		out.write(bcu.sCalendarLundarToSolar(start, end));
		out.close();
		return null;
	}
	
	public ActionForward doLoad(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String roadname = request.getParameter("roadname");
		
		String hours;
		
		BayonetCartogramDao bcd = new BayonetCartogramDao();
		BayonetCartogramUtil bcu = new BayonetCartogramUtil();
		
		Object[] objects = roadname.split("、");
		
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		hours=sFormat.format(new Date());
		System.out.println(hours);
		object = bcd.getYearDate(bcu.getRoadNameSql(objects), bcu
				.getDateSql("7", "","",
						"", "", "",
						"", hours, "", ""), bcu
				.getRoadType("1"), bcu.getResult("7"));
		out.write(bcu.getRoadXMl(object, objects));
		out.close();
		return null;
	
	}
	
}
