package com.ehl.drpt.dailyRpt.action;
/**
 * 
 * @======================================================================================================================================
 * @类型说明: 春运道路交通安全管理日报操作Action
 * @创建者：dxn
 * @创建日期 2010-01-11
 * @======================================================================================================================================
 */
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.drpt.dailyRpt.dao.DailyRptDao;
import com.ehl.sm.common.Constants;
import com.ehl.sm.common.tree.PageCustomTree;
import com.ehl.sm.sysmanage.LogManage;

public class DailyRptModifyAction extends Controller{
	private DailyRptDao drDa0 = new DailyRptDao();
	private int p_hour = getHourPrmt();
	
	// 取出当前的年
	int year = new GregorianCalendar().get(GregorianCalendar.YEAR); 
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报新增&修改
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	public ActionForward doModify(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		boolean flag = false;
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
		int insertOrUpdate = StringHelper.obj2int(request
				.getParameter("insertOrUpdate"), 0);
		String xmlStr = request.getParameter("xmlStr");
		xmlStr = "<?xml version='1.0' encoding='UTF-8'?>" + xmlStr.replace("$", "=");
		Object[][] results = PageCustomTree.getJDomResult(xmlStr, "RFWin");
		
		String RZBH = results[0][1].toString().substring(0, 6) + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime());
//		
		if (insertOrUpdate == 0) {
			if(!chkTimeWithDept(results[0][1].toString(),year+"-" + results[0][2] + "-" + results[0][3])){
				out.write("操作失败，已存在当天日志！");
				out.close();
				LogManage.writeEvent(LCODE, "新增春运道路交通安全管理日报");
				return null;
			}
			flag = drDa0.addDailyRpt(results,RZBH);
			if(flag){
				out.write("success");
				out.close();
				LogManage.writeEvent(LCODE, "新增春运道路交通安全管理日报");
				return null;
			}
		}else{
			flag = drDa0.updateDailyRpt(results,RZBH);
			if(flag){
				out.write(results[0][1].toString());
				out.close();
				LogManage.writeEvent(LCODE, "修改春运道路交通安全管理日报");
				return null;
			}
		}
		out.write("操作失败，请重试！");
		out.close();
		return null;
	} 
	
	public ActionForward doQuery(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String RZBH = StringHelper.obj2str(request.getParameter("RZBH"),""); //单位id

		out.write(drDa0.query(RZBH));
		out.close();
		return null;
	}
	
	public ActionForward doQueryRoad(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String RZBH = StringHelper.obj2str(request.getParameter("RZBH"),""); //单位id

		out.write(drDa0.queryRoad(RZBH));
		out.close();
		return null;
	}
	
	public ActionForward doDelete(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
		response.setContentType("text/xml"); // 指定输出的XML格式
		String RZBH = request.getParameter("RZBH") == null ? "" : request.getParameter("RZBH");
		out.write(drDa0.delete(RZBH)?"记录被成功删除！":"操作失败，请重试！");
		LogManage.writeEvent(LCODE, "删除春运道路交通安全管理日报");
		out.close();
		return null;
	}
		
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报新增界面获取日期参数
	 * @参数：
	 * @返回：日报统计日期
	 * @创建日期：2010-01-11
	 */
	public int[] getRptDate() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(new Date(new Date().getTime() + (hour < p_hour? -1 : 0)*24*60*60*1000));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DAY_OF_MONTH);
		
		return new int[]{year,month,date};
	}

/*	public boolean chkRzbh(String gwbh, int subday) {
		if("0000".endsWith(gwbh.substring(4, 6))){
			return false;
		}
		String RZBH_0 = gwbh.toString().substring(0, 6)
			+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + subday*24*60*60*1000));
		return drDa0.chkRzbh(RZBH_0);
	}*/
	
	public int getHourPrmt(){
		return drDa0.getHourPrmt();
	}
	
	public boolean chkTimeWithDept(String jgid, String tjrq){
		return drDa0.chkTimeWithDept(jgid,tjrq);
	}
	
	public boolean chkTimeWithTjrq(String gwbh,String RZBH,String tjrq){
		return drDa0.chkTimeWithTjrq(gwbh, RZBH, tjrq);
	//	return drDa0.checkOverTime(tjrq,gwbh);
	}
	
	public ActionForward doInfoCalBack(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //单位id
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间

		out.write(drDa0.infoCalBack(department, year+"-"+startTime, year+"-"+endTime));
		out.close();
		return null;
	}
	
	public ActionForward doTfmCalBack(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		DailyRptDao  dao = new DailyRptDao();
		String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //单位id
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
		
		out.write(dao.tfmCalBack(department, year+"-"+startTime, year+"-"+endTime));
		out.close();
		return null;
	} 
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报新增界面获取填报单位
	 * @参数：
	 * @返回：日报统计日期
	 * @创建日期：2010-01-11
	 */
	public Object[] getRptDpt(String username) {
		return drDa0.getRptDpt(username);
	}
//	public static void main(String[] args){
//		DailyRptModifyAction dra = new DailyRptModifyAction();
//		int tjn = dra.getRptDate()[0];
//		int tjy = dra.getRptDate()[1];
//		int tjr = dra.getRptDate()[2];
//		String tjrq = tjn + "-" + tjy + "-" +tjr;
//		System.out.println(tjrq+Integer.toString(tjy));
//		
//	}
}
