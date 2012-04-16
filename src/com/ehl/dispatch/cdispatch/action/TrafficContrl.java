/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.TrafficContrlData;


/**
 * @author wxt
 *
 */
public class TrafficContrl extends Controller{
	
	private final static String maintable = "T_ATTEMPER_ALARM";
	private final static String roadtable="T_ATTEMPER_ROADBUILD";
	private final static String contrltable="T_ATTEMPER_TRAFFICRESTRAIN";
	/**
	 * 新增一条交通管制信息
	 */
	public ActionForward doEditTrafficControlInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		//填报单号
		String alarmId_TrafficRestrain = StringHelper.obj2str(request.getParameter("alarmId_TrafficRestrain"),"");
		//填报时间
		String alarmTime_TrafficRestrain = StringHelper.obj2str(request.getParameter("alarmTime_TrafficRestrain"),"");
		//上报单位
		String alarmDept_TrafficRestrain = StringHelper.obj2str(request.getParameter("alarmDept_TrafficRestrain"),"");
		//填报人
		String reportPerson_TrafficRestrain = StringHelper.obj2str(request.getParameter("reportPerson_TrafficRestrain"),"");
		//填报单位
		String reportDept_TrafficRestrain = StringHelper.obj2str(request.getParameter("reportDept_TrafficRestrain"),"");
		//联系电话
		String telpone_TrafficRestrain = StringHelper.obj2str(request.getParameter("telpone_TrafficRestrain"),"");
		//管制原因
		String reson_TrafficRestrain = StringHelper.obj2str(request.getParameter("reson_TrafficRestrain"),"");
		//事发路段
		String accsection_TrafficRestrain = StringHelper.obj2str(request.getParameter("accsection_TrafficRestrain"),"");
		//公里
		String mvalue_TrafficRestrain = StringHelper.obj2str(request.getParameter("mvalue_TrafficRestrain"),"");
		//管制地点
		String address_TrafficRestrain = StringHelper.obj2str(request.getParameter("address_TrafficRestrain"),"");
		//管制方向
		String direction_TrafficRestrain_td = StringHelper.obj2str(request.getParameter("direction_TrafficRestrain"),"");
		//管制类型
		String type_TrafficRestrain = StringHelper.obj2str(request.getParameter("type_TrafficRestrain"),"");
		//管制开始时间
		String starttime_TrafficRestrain = StringHelper.obj2str(request.getParameter("starttime_TrafficRestrain"),"");
		//管制结束时间
		String endtime_TrafficRestrain = StringHelper.obj2str(request.getParameter("endtime_TrafficRestrain"),"");
		//备注
		String desc_TrafficRestrain = StringHelper.obj2str(request.getParameter("desc_TrafficRestrain"),"");
		HashMap datamap =  new HashMap();
		datamap.put("alarmId_TrafficRestrain", alarmId_TrafficRestrain);
		datamap.put("alarmTime_TrafficRestrain", alarmTime_TrafficRestrain);
		datamap.put("alarmDept_TrafficRestrain", alarmDept_TrafficRestrain);
		datamap.put("reportPerson_TrafficRestrain", reportPerson_TrafficRestrain);
		datamap.put("telpone_TrafficRestrain", telpone_TrafficRestrain);
		datamap.put("reson_TrafficRestrain",reson_TrafficRestrain);
		datamap.put("accsection_TrafficRestrain", accsection_TrafficRestrain);
		datamap.put("mvalue_TrafficRestrain", mvalue_TrafficRestrain);
		datamap.put("address_TrafficRestrain", address_TrafficRestrain);
		datamap.put("direction_TrafficRestrain", direction_TrafficRestrain_td);
		datamap.put("type_TrafficRestrain", type_TrafficRestrain);
		datamap.put("starttime_TrafficRestrain", starttime_TrafficRestrain);
		datamap.put("endtime_TrafficRestrain", endtime_TrafficRestrain);
		datamap.put("desc_TrafficRestrain", desc_TrafficRestrain);
		datamap.put("reportDept_TrafficRestrain",  reportDept_TrafficRestrain);
	    TrafficContrlData tcd = new TrafficContrlData();
	    boolean flag=tcd.editTrafficContrl(datamap);
	    if(flag) {
	    	out.write("success");
	    }else {
	    	out.write("fail");
	    }
		out.close();
		return null;
	}
	/**
	 * 查询交通管制详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTrafficControlInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String alarmid = StringHelper.obj2str(request.getParameter("alarmid"));
		TrafficContrlData tcd = new TrafficContrlData();
		Object[] res = tcd.getTrafficContrl(alarmid);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		return null;
	}
	/**
	 * 查询施工占道详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTrafficRoadInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String alarmid = StringHelper.obj2str(request.getParameter("alarmid"));
		TrafficContrlData tcd = new TrafficContrlData();
		Object[] res = tcd.getTrafficRoadInfo(alarmid);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		return null;
	}
	/**
	 * 新增一条施工占道信息
	 */
	public ActionForward doEditTrafficRoadInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		//警情单号		
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"), "");
		//填报时间
		String alarmTime_RoadBuild=StringHelper.obj2str(request.getParameter("alarmTime_RoadBuild"), "");
		//填报人
		String reportPerson_RoadBuild=StringHelper.obj2str(request.getParameter("reportPerson_RoadBuild"), "");
		//占道单位
		String alarmDept_RoadBuild=StringHelper.obj2str(request.getParameter("alarmDept_RoadBuild"), "");
		//负责人
		String fzr_RoadBuild = StringHelper.obj2str(request.getParameter("fzr_RoadBuild"),"");
		//联系电话
		String phone_RoadBuild=StringHelper.obj2str(request.getParameter("phone_RoadBuild"), "");
		//占用道路
		String alarmRoad_RoadBuild_td=StringHelper.obj2str(request.getParameter("alarmRoad_RoadBuild_td"), "");
		//相关路段
		String alarmFiled_RoadBuild=StringHelper.obj2str(request.getParameter("alarmFiled_RoadBuild"), "");
		//占用情况
		String rotype_RoadBuild=StringHelper.obj2str(request.getParameter("rotype_RoadBuild"), "");
		//准行情况
		String permittype_RoadBuild=StringHelper.obj2str(request.getParameter("permittype_RoadBuild"), "");
		//占道开始时间
		String starttime_RoadBuild=StringHelper.obj2str(request.getParameter("starttime_RoadBuild"), "");
		//占道终止时间
		String endtime_RoadBuild=StringHelper.obj2str(request.getParameter("endtime_RoadBuild"), "");
		//警情状态
		String alarmRoad_state_td=StringHelper.obj2str(request.getParameter("alarmState"), "");
		//事件内容
		String alarmDesc_RoadBuild=StringHelper.obj2str(request.getParameter("alarmDesc_RoadBuild"), "");
		//机构
		//String alarmDept=StringHelper.obj2str(request.getParameter("alarmDept"), "");
		Object[] res=new Object[14];
		List mailist = new ArrayList<String>();
		List sonlist = new ArrayList<String>();
		//res=getBaseAlarmInfo(request,res);
		//主表内容
		res[0] = alarmId;
		res[1] = alarmTime_RoadBuild;
		res[2] = alarmDesc_RoadBuild;
		res[3] = alarmRoad_state_td;
		res[4] = reportPerson_RoadBuild;
		mailist.add(alarmId);
		mailist.add(alarmTime_RoadBuild);
		mailist.add(alarmDesc_RoadBuild);
		mailist.add(alarmRoad_state_td);
		mailist.add(reportPerson_RoadBuild);
		//子表内容
		res[5] = alarmDept_RoadBuild;
		res[6] = fzr_RoadBuild;
		res[7] = phone_RoadBuild;
		res[8] = alarmRoad_RoadBuild_td;
		res[9] = alarmFiled_RoadBuild;
		res[10] = rotype_RoadBuild;
		res[11] = permittype_RoadBuild;
		res[12] = starttime_RoadBuild;
		res[13] = endtime_RoadBuild;
		sonlist.add(alarmDept_RoadBuild);
		sonlist.add(fzr_RoadBuild);
		sonlist.add(phone_RoadBuild);
		sonlist.add(alarmRoad_RoadBuild_td);
		sonlist.add(alarmFiled_RoadBuild);
		sonlist.add(rotype_RoadBuild);
		sonlist.add(permittype_RoadBuild);
		sonlist.add(starttime_RoadBuild);
		sonlist.add(endtime_RoadBuild);
		//res[14] = alarmDept;
		TrafficContrlData alarmInfoOpt=new TrafficContrlData();
		boolean msg = alarmInfoOpt.insertTrafficRoadInfo(res);
//		if(isMesServer()){
//			AlarmInfoMsg.SendAlarmMsg(res);
//		}
		String resStr=null;
		if (msg) {
			resStr="success";
		} else {
			resStr="fail";
		}
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * @作者: wangxt
	 * @版本号：1.0
	 * @函数说明：删除交通管制信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-10
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doDelTrafficInfo (Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String roadid = request.getParameter("roadid");
		TrafficContrlData tc =  new TrafficContrlData();
		boolean flag = tc.deleteTrafficContrl(roadid,maintable,contrltable);
	    if(flag){
	    	out.write("success");
	    }else {
	    	out.write("fail");
	    }
		return null;
	}
	
	
	/**
	 * @作者: wangxt
	 * @版本号：1.0
	 * @函数说明：删除施工占道信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-10
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doDelTrafficRoadInfo (Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String roadid = request.getParameter("roadid");
		TrafficContrlData tc =  new TrafficContrlData();
		boolean flag = tc.deleteTrafficContrl(roadid,maintable,roadtable);
	    if(flag){
	    	out.write("success");
	    }else {
	    	out.write("fail");
	    }
		return null;
	}
	
}
