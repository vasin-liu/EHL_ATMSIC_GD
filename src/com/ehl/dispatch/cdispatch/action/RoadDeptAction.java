package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.RoadDeptCore;

public class RoadDeptAction extends Controller {
	
	private RoadDeptCore roadDept = new RoadDeptCore();
	
	public ActionForward doGetDeptRoad(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String roadName = StringHelper.obj2str(request.getParameter("dlmc"),"");
		String rsegName = StringHelper.obj2str(request.getParameter("xqldmc"),"");
		String startKM = StringHelper.obj2str(request.getParameter("qslc"),"");
		String endKM = StringHelper.obj2str(request.getParameter("jslc"),"");
		String xzqhmc = StringHelper.obj2str(request.getParameter("xzqhmc"),"");
		String dldj = StringHelper.obj2str(request.getParameter("dldj"),"");
		
		String dept = roadDept.getDeptRoad(roadName, rsegName, startKM, endKM, xzqhmc,dldj);
		dept = (dept==null?"":dept);
		out.write(dept);
		
		return null;
	}
	
	public ActionForward doGetDeptByRoad(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String roadName = StringHelper.obj2str(request.getParameter("dlmc"),"");
		String rsegName = StringHelper.obj2str(request.getParameter("xqldmc"),"");
		String startKM = StringHelper.obj2str(request.getParameter("qslc"),"");
		String endKM = StringHelper.obj2str(request.getParameter("jslc"),"");
		String dldj = StringHelper.obj2str(request.getParameter("dldj"),"");
		
		String dept = roadDept.getDeptByRoad(roadName, rsegName, startKM, endKM,dldj);
		dept = (dept==null?"":dept);
		out.write(dept);
		
		return null;
	}
	
	public ActionForward doGetRoadByDept(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String xzqhmc = StringHelper.obj2str(request.getParameter("xzqhmc"),"");
		
		String road = roadDept.getRoadByDept(xzqhmc);
		road = (road==null?"":road);
		out.write(road);
		
		return null;
	}
}
