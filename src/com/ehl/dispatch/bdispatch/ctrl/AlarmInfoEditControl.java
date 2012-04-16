package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.business.AlarmInfoMsg;
import com.ehl.dispatch.bdispatch.business.AlarmInfoOpt;
import com.ehl.dispatch.bdispatch.util.DispatchUtil;
import com.ehl.sm.common.Constants;
/**
 * 
 * 报警信息编辑控制类
 * 
 */
public class AlarmInfoEditControl extends Controller {

	/**
	 * 获得鼠标点击附件的路段
	 */
	public ActionForward doGetNearRoadByMouse(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String x=StringHelper.obj2str(request.getParameter("x"),"");
		String y=StringHelper.obj2str(request.getParameter("y"),"");
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String xmlStr=alarmInfoOpt.getNearRoadByMouse(x, y);
//		System.out.println(xmlStr);
		out.write(xmlStr);
		out.close();
		return null;
	}	
	
	
	private Object[] getBaseAlarmInfo(HttpServletRequest request,Object[] res){
		String alarmid=StringHelper.obj2str(request.getParameter("alarmId"), "");
		String alarmTime=StringHelper.obj2str(request.getParameter("alarmTime"), "");
		String alarmSite=StringHelper.obj2str(request.getParameter("alarmSite"), "");
		String alarmDept=StringHelper.obj2str(request.getParameter("alarmDept"), "");
		//add by wangxt 090515
		String subEventSource = StringHelper.obj2str(request.getParameter("subEventSource"),"");
		String alarmSouce=StringHelper.obj2str(request.getParameter("alarmSouce"), "");
		String alarmType=StringHelper.obj2str(request.getParameter("alarmType"), "");
		String alarmLevel=StringHelper.obj2str(request.getParameter("alarmLevel"), "");
		String alarmThinType=StringHelper.obj2str(request.getParameter("alarmThinType"), "");
		String alarmState=StringHelper.obj2str(request.getParameter("alarmState"), "");
		String alarmDesc=StringHelper.obj2str(request.getParameter("alarmDesc"), "");
		String roadId=StringHelper.obj2str(request.getParameter("roadId"), "");
		String roadName=StringHelper.obj2str(request.getParameter("roadName"), "");
		String X=StringHelper.obj2str(request.getParameter("X"), "");
		String Y=StringHelper.obj2str(request.getParameter("Y"), "");
		
		String dlmc = StringHelper.obj2str(request.getParameter("dlmc"), "");
		String kmz = StringHelper.obj2str(request.getParameter("kmz"), "");
		String bmz = StringHelper.obj2str(request.getParameter("bmz"), "");
		
		String resStr = new DispatchUtil().getQmzPoint(dlmc, kmz, bmz);
		
		if(!resStr.equals("")){
			X = resStr.split(",")[0];
			Y = resStr.split(",")[1];
		}
		
		
		String alarmLevelValue=StringHelper.obj2str(request.getParameter("alarmLevelValue"), "");
		String alarmThinTypeValue=StringHelper.obj2str(request.getParameter("alarmThinTypeValue"), "");
		
		String disposeTime=StringHelper.obj2str(request.getParameter("disposeTime"), "");
		String disposePerson=StringHelper.obj2str(request.getParameter("disposePerson"), "");
		String disposeUnit=StringHelper.obj2str(request.getParameter("disposeUnit"), "");
		String disposeIdea=StringHelper.obj2str(request.getParameter("disposeIdea"), "");
		
		
		String reportUnitName=StringHelper.obj2str(request.getParameter("reportUnitName"), "");
		String reportTime=StringHelper.obj2str(request.getParameter("reportTime"), "");
		String reportPerson=StringHelper.obj2str(request.getParameter("reportPerson"), "");
		
		res[0]=alarmid;
		res[1]=alarmTime;
		res[2]=alarmSite;
		res[3]=alarmDept;
		res[4]=alarmSouce;
		res[5]=alarmType;
		res[6]=alarmLevel;
		res[7]=alarmThinType;
		res[8]=alarmState;
		res[9]=alarmDesc;		
		res[10]=X;
		res[11]=Y;
		res[12]=roadId;
		res[13]=roadName;
		
		res[14]=disposeTime;
		res[15]=disposePerson;
		res[16]=disposeUnit;
		res[17]=disposeIdea;
		
		res[18]=alarmLevelValue;
		res[19]=alarmThinTypeValue;
				
		res[20]=reportTime;
		res[21]=reportPerson;
		res[22]=reportUnitName;
		// add by wangxt 090515
		res[23]=subEventSource;
		//res[24]="";	//备用
		
		return res;
	}
	
	/**
	 * 新增一条事故报警信息
	 */
	public ActionForward doEditAccidentInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		//yyyyMMddHH24missSS		
		
		String accidentAlarmCarType	=StringHelper.obj2str(request.getParameter("accidentAlarmCarType"), "");	
		String accidentAlarmCarCodeDept	=StringHelper.obj2str(request.getParameter("accidentAlarmCarCodeDept"), "");	
		String accidentAlarmCarCode	=StringHelper.obj2str(request.getParameter("accidentAlarmCarCode"), "");	
		String accidentAlarmCarGenre=StringHelper.obj2str(request.getParameter("accidentAlarmCarGenre"), "");		
		String accidentremark=StringHelper.obj2str(request.getParameter("accidentremark"), "");
		String accidentAffectLevel=StringHelper.obj2str(request.getParameter("accidentAffectLevel"), "");
		
		Object[] res=new Object[29];
	
		res=getBaseAlarmInfo(request,res);
		
				
		res[24]=accidentAlarmCarType;
		res[25]=accidentAlarmCarCodeDept+accidentAlarmCarCode;
		res[26]=accidentAlarmCarGenre;
		res[27]=accidentremark;
		res[28]=accidentAffectLevel;
		
		AlarmInfoOpt accidentEditOpt=new AlarmInfoOpt();
		String resStr=accidentEditOpt.editAccidentInfo(res);
					
		out.write(resStr);
		out.close();
		return null;
	}
	
	
	/**
	 * 在道路上快速标注点
	 */
	public ActionForward doGetPointOnRoad(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"), "");
		if(alarmId.equals("")){
			out.write("");
			out.close();
			return null;
		}
		String dlmc = StringHelper.obj2str(request.getParameter("dlmc"), "");
		String kmz = StringHelper.obj2str(request.getParameter("kmz"), "");
		String bmz = StringHelper.obj2str(request.getParameter("bmz"), "");
		
		String resStr = new DispatchUtil().getQmzPoint(dlmc, kmz, bmz);
		boolean bres = false;
		if(!resStr.equals("")){
			String X = resStr.split(",")[0].toString();
			String Y = resStr.split(",")[1].toString();
			String sql="update t_attemper_alarm set ROADID='',ROADNAME='',X='"+X+"',Y='"+Y+"' where ALARMID='"+alarmId+"'";
			
			bres = DBHandler.execute(sql);;
		}
		
		if(!bres){
			resStr = "false";
		}
		out.write(resStr);
		out.close();
		return null;
	}
	/**
	 * 新增一条拥堵报警信息
	 */
	public ActionForward doEditCongestionInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
				
		String congestionAffectLevel=StringHelper.obj2str(request.getParameter("congestionAffectLevel"), "");
		String congestionRemark=StringHelper.obj2str(request.getParameter("congestionRemark"), "");
		
		Object[] res=new Object[26];
		res=getBaseAlarmInfo(request,res);
				
		res[24]=congestionAffectLevel;
		res[25]=congestionRemark;
		
		AlarmInfoOpt congestionEditOpt=new AlarmInfoOpt();
		String resStr=congestionEditOpt.editCongestionInfo(res);
					
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * 新增一条故障车报警信息
	 */
	public ActionForward doEditExceptionCarInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String exceptionCarAddress=StringHelper.obj2str(request.getParameter("exceptionCarAddress"), "");
		String exceptionCause=StringHelper.obj2str(request.getParameter("exceptionCause"), "");
		String exceptioCarShape=StringHelper.obj2str(request.getParameter("exceptioCarShape"), "");
		String exceptioAffectLevel=StringHelper.obj2str(request.getParameter("exceptioAffectLevel"), "");
		String exceptionRemark=StringHelper.obj2str(request.getParameter("exceptionRemark"), "");
		
		Object[] res=new Object[29];
		res=getBaseAlarmInfo(request,res);
		
		res[24]=exceptionCarAddress;
		res[25]=exceptionCause;
		res[26]=exceptioCarShape;
		res[27]=exceptioAffectLevel;
		res[28]=exceptionRemark;
		
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String resStr=alarmInfoOpt.editExceptionCarInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * 新增一条治安报警信息
	 */
	public ActionForward doEditPoliceEventInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
				
		String PoliceEventAffectTrafficLevel=StringHelper.obj2str(request.getParameter("PoliceEventAffectTrafficLevel"), "");
		String PoliceEventAffectRoad=StringHelper.obj2str(request.getParameter("PoliceEventAffectRoad"), "");
		String PoliceEventRemark=StringHelper.obj2str(request.getParameter("PoliceEventRemark"), "");
		String PoliceEventTime=StringHelper.obj2str(request.getParameter("PoliceEventTime"), "");
		
		Object[] res=new Object[28];
		res=getBaseAlarmInfo(request,res);
		
		res[24]=PoliceEventAffectTrafficLevel;
		res[25]=PoliceEventAffectRoad;
		res[26]=PoliceEventRemark;
		res[27]=PoliceEventTime;
		
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String resStr=alarmInfoOpt.editPoliceEventInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * 新增一条恶劣天气信息
	 */
	public ActionForward doEditBadWeatherInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
				
		String weathersituation=StringHelper.obj2str(request.getParameter("weathersituation"), "");
		String weatherRoadStatus=StringHelper.obj2str(request.getParameter("weatherRoadStatus"), "");
		String weatherAffectRoad=StringHelper.obj2str(request.getParameter("weatherAffectRoad"), "");
		String weatheAffectDept=StringHelper.obj2str(request.getParameter("weatheAffectDept"), "");
		String weatherAlarmPhone=StringHelper.obj2str(request.getParameter("weatherAlarmPhone"), "");
		String weatherAlarmPerson=StringHelper.obj2str(request.getParameter("weatherAlarmPerson"), "");
		String weatherAffectextent=StringHelper.obj2str(request.getParameter("weatherAffectextent"), "");
		String weatherRemark=StringHelper.obj2str(request.getParameter("weatherRemark"), "");
		
		Object[] res=new Object[32];
		res=getBaseAlarmInfo(request,res);
		
		res[24]=weathersituation;
		res[25]=weatherRoadStatus;
		res[26]=weatherAffectRoad;
		res[27]=weatheAffectDept;
		res[28]=weatherAlarmPhone;
		res[29]=weatherAlarmPerson;
		res[30]=weatherAffectextent;
		res[31]=weatherRemark;
		
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String resStr=alarmInfoOpt.editBadWeatherInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * 新增一条市政信息
	 */
	public ActionForward doEditTownplanInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
				
		String townPlanHappenTime=StringHelper.obj2str(request.getParameter("townPlanHappenTime"), "");
		String townPlanPerson=StringHelper.obj2str(request.getParameter("townPlanPerson"), "");
		String townPlanAffectRoad=StringHelper.obj2str(request.getParameter("townPlanAffectRoad"), "");
		String townPlanAffectExent=StringHelper.obj2str(request.getParameter("townPlanAffectExent"), "");
		String townPlanLevel=StringHelper.obj2str(request.getParameter("townPlanLevel"), "");
		String townPlanRemark=StringHelper.obj2str(request.getParameter("townPlanRemark"), "");
		
		Object[] res=new Object[30];
		res=getBaseAlarmInfo(request,res);
		
		res[24]=townPlanHappenTime;
		res[25]=townPlanPerson;
		res[26]=townPlanAffectRoad;
		res[27]=townPlanAffectExent;
		res[28]=townPlanLevel;
		res[29]=townPlanRemark;
	
		
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String resStr=alarmInfoOpt.editTownplanInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}

	
	/**
	 * 新增一条火灾信息
	 */
	public ActionForward doEditFireAndBlastInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
				
		String fireTime=StringHelper.obj2str(request.getParameter("fireTime"), "");
		String fireAffectLevel=StringHelper.obj2str(request.getParameter("fireAffectLevel"), "");
		String fireHaveCasualty=StringHelper.obj2str(request.getParameter("fireHaveCasualty"), "");
		String fireAlarmPerson=StringHelper.obj2str(request.getParameter("fireAlarmPerson"), "");
		String fireRemark=StringHelper.obj2str(request.getParameter("fireRemark"), "");
		
		Object[] res=new Object[29];
		res=getBaseAlarmInfo(request,res);
		
		res[24]=fireTime;
		res[25]=fireAffectLevel;
		res[26]=fireHaveCasualty;
		res[27]=fireAlarmPerson;
		res[28]=fireRemark;
	
		
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String resStr=alarmInfoOpt.editFireAndBlastInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * 新增一条地质灾害信息
	 */
	public ActionForward doEditGeoLogicDisasterInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
				
		String geoLogicDisasterTime=StringHelper.obj2str(request.getParameter("GeoLogicDisasterTime"), "");
		String geoLogicDisasterLevel=StringHelper.obj2str(request.getParameter("GeoLogicDisasterLevel"), "");
		String geoLogicDisasterAffectExent=StringHelper.obj2str(request.getParameter("GeoLogicDisasterAffectExent"), "");
		String geoLogicDisasterAlarmPerson=StringHelper.obj2str(request.getParameter("GeoLogicDisasterAlarmPerson"), "");
		String geoLogicDisasterRemark=StringHelper.obj2str(request.getParameter("GeoLogicDisasterRemark"), "");
		
		Object[] res=new Object[29];
		res=getBaseAlarmInfo(request,res);
		
		res[24]=geoLogicDisasterTime;
		res[25]=geoLogicDisasterLevel;
		res[26]=geoLogicDisasterAffectExent;
		res[27]=geoLogicDisasterAlarmPerson;
		res[28]=geoLogicDisasterRemark;
	
		
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String resStr=alarmInfoOpt.editGeoLogicDisasterInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
//	/**
//	 * 新增一条布控信息
//	 */
//	public ActionForward doEditBlackListInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
//	{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/xml"); 	// 指定输出内容的格式
//		
//		PrintWriter out = response.getWriter();
//		//yyyyMMddHH24missSS		
////		String alarmid=StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
//		String alarmid=StringHelper.obj2str(request.getParameter("alarmId"), "");
//		String alarmTime=StringHelper.obj2str(request.getParameter("alarmTime"), "");
//		String alarmSite=StringHelper.obj2str(request.getParameter("alarmSite"), "");
//		String alarmDept=StringHelper.obj2str(request.getParameter("alarmDept"), "");
//		String alarmSouce=StringHelper.obj2str(request.getParameter("alarmSouce"), "");
//		String alarmType=StringHelper.obj2str(request.getParameter("alarmType"), "");
//		String alarmLevel=StringHelper.obj2str(request.getParameter("alarmLevel"), "");
//		String alarmThinType=StringHelper.obj2str(request.getParameter("alarmThinType"), "");
//		String alarmState=StringHelper.obj2str(request.getParameter("alarmState"), "");
//		String alarmDesc=StringHelper.obj2str(request.getParameter("alarmDesc"), "");
//		String roadId=StringHelper.obj2str(request.getParameter("roadId"), "");
//		String roadName=StringHelper.obj2str(request.getParameter("roadName"), "");
//		String X=StringHelper.obj2str(request.getParameter("X"), "");
//		String Y=StringHelper.obj2str(request.getParameter("Y"), "");
//		
//		String disposeTime=StringHelper.obj2str(request.getParameter("disposeTime"), "");
//		String disposePerson=StringHelper.obj2str(request.getParameter("disposePerson"), "");
//		String disposeUnit=StringHelper.obj2str(request.getParameter("disposeUnit"), "");
//		String disposeIdea=StringHelper.obj2str(request.getParameter("disposeIdea"), "");
//		
//		String blackListCarNumber=StringHelper.obj2str(request.getParameter("blackListCarNumber"), "");
//		String blackListCarSort=StringHelper.obj2str(request.getParameter("blackListCarSort"), "");
//		String blackListRemark=StringHelper.obj2str(request.getParameter("blackListRemark"), "");
//		
//		Object[] res=new Object[18];
//		res[0]=alarmid;
//		res[1]=alarmTime;
//		res[2]=alarmSite;
//		res[3]=alarmDept;
//		res[4]=alarmSouce;
//		res[5]=alarmType;
//		res[6]=alarmLevel;
//		res[7]=alarmThinType;
//		res[8]=alarmState;
//		res[9]=alarmDesc;		
//		res[10]=X;
//		res[11]=Y;
//		res[12]=roadId;
//		res[13]=roadName;
//
//		res[14]=disposeTime;
//		res[15]=disposePerson;
//		res[16]=disposeUnit;
//		res[17]=disposeIdea;
//		
//		res[18]="";	//备用
//		
//		res[15]=blackListCarNumber;
//		res[16]=blackListCarSort;
//		res[17]=blackListRemark;
//		
//		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
//		String resStr=alarmInfoOpt.editBlackListInfo(res);
//		
//		out.write(resStr);
//		out.close();
//		return null;
//	}
	
	/**
	 * 根据报警ID获得报警信息
	 */
	public ActionForward doGetAlarmInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String alarmid=StringHelper.obj2str(request.getParameter("alarmid"),"");
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String xmlstr=alarmInfoOpt.getAlarmInfoById(alarmid);
		out.write(xmlstr);		
		out.close();
		return null;
	
	}	
	
	
	/**
	 * 得到子单列表
	 */
	public ActionForward doGetSonList(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String id=StringHelper.obj2str(request.getParameter("id"),"");
		AlarmInfoOpt alarmInfoOpt=new AlarmInfoOpt();
		String xmlstr=alarmInfoOpt.getSonList(id);
		out.write(xmlstr);		
		out.close();
		return null;
	
	}
	
	/**
	 * 得到自动生成的id和时间
	 */
	public ActionForward doGetNewInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		HttpSession session =request.getSession();
		
		String usercode=(String)session.getAttribute(Constants.PCODE_KEY);
	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		String str=infoOpt.setNewInfo(usercode);
		out.write(str);
		return null;
	
	}
	
	
	
	/**
	 * 得到当天勤务人员列表
	 */
	public ActionForward doGetDutyPerosn(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");
	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		String str=infoOpt.setDutyPersons(deptId);
		out.write(str);
		out.close();
		return null;	
	}
	

	/**
	 * 得到当天勤务车辆列表
	 */
	public ActionForward doGetDutyCar(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");
	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		String str=infoOpt.setDutyCars(deptId);
		out.write(str);
		out.close();
		return null;	
	}
	
	
	
	/**
	 * 根据机构得到人员列表
	 */
	public ActionForward doGetPcsPerosn(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
	 
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		String str=infoOpt.setPcsPersons(deptId);
		out.write(str);
		out.close();
		return null;
	}	
	
	
	
	/**
	 * 根据机构得到车辆列表
	 */
	public ActionForward doGetPcsCar(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
	 
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		String str=infoOpt.setPcsCars(deptId);
		out.write(str);
		out.close();
		return null;
	}	
	
	
	public ActionForward doReMarkPoint(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String roadId=StringHelper.obj2str(request.getParameter("roadId"),"");
		String roadName=StringHelper.obj2str(request.getParameter("roadName"),"");
		String X=StringHelper.obj2str(request.getParameter("X"),"");
		String Y=StringHelper.obj2str(request.getParameter("Y"),"");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		
		String sql="update t_attemper_alarm set ROADID='"+roadId+"',ROADNAME='"+roadName+"',X='"+X+"',Y='"+Y+"' where ALARMID='"+alarmId+"'";
		boolean msg=DBHandler.execute(sql);
		if(msg){
			out.write("success");
		}
		out.close();
		return null;
	
	}
	

	public ActionForward doGetArriveTime(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		
		String feedbackId=StringHelper.obj2str(request.getParameter("feedbackId"),"");		
		String sql="select to_char(t.COMEOUTARRIVETIME,'yyyy-mm-dd hh24:mi'),(select a.EVENTTYPE from t_attemper_alarm a where a.alarmid=t.alarmid) from t_attemper_feedback t where t.feedbackid='"+feedbackId+"'";
		Object[] res=DBHandler.getLineResult(sql);
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		if(res!=null){
			for(int i=0;i<res.length;i++){
				sbXml.append("<col>\n");
				sbXml.append(StringHelper.obj2str(res[i],""));
				sbXml.append("</col>\n");
			}
		}
		
		sbXml.append("</RFWin>\n");
		sbXml.append("</rfXML>\n");
		String xmlstr = sbXml.toString();
		out.write(xmlstr);
		out.close();
		return null;
	
	}
	
	
//	public ActionForward doGetDictInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
//	{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/xml"); 	// 指定输出内容的格式
//		
//		PrintWriter out = response.getWriter();
//		HttpSession session =request.getSession();
//		
//		
//		return null;
//	
//	}
	
	
	//bt保存
	public ActionForward doSavePoliceInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		HttpSession session =request.getSession();
		
		String alarmid=StringHelper.obj2str(request.getParameter("alarmId"), "");
		String alarmTime=StringHelper.obj2str(request.getParameter("alarmTime"), "");
		String alarmSite=StringHelper.obj2str(request.getParameter("alarmSite"), "");
		String alarmDept=StringHelper.obj2str(request.getParameter("alarmDept"), "");
		String alarmDeptId=StringHelper.obj2str(request.getParameter("alarmDeptId"), "");
		String alarmType=StringHelper.obj2str(request.getParameter("alarmType"), "");		
		String subEventSource = StringHelper.obj2str(request.getParameter("subEventSource"),"");
		String region=StringHelper.obj2str(request.getParameter("region"), "");
		String regionId=StringHelper.obj2str(request.getParameter("regionId"), "");
		String alarmState=StringHelper.obj2str(request.getParameter("alarmState"), "");
		String alarmThinType=StringHelper.obj2str(request.getParameter("alarmThinType"), "");
		String alarmperson=StringHelper.obj2str(request.getParameter("alarmperson"), "");
		String alarmphone=StringHelper.obj2str(request.getParameter("alarmphone"), "");
		String alarmaddres=StringHelper.obj2str(request.getParameter("alarmaddres"), "");
		String reportTime=StringHelper.obj2str(request.getParameter("reportTime"), "");
		String reportPerson=StringHelper.obj2str(request.getParameter("reportPerson"), "");
		String reportUnit=StringHelper.obj2str(request.getParameter("reportUnit"), "");
		String reportUnitId=StringHelper.obj2str(request.getParameter("reportUnitId"), "");
		String alarmDesc=StringHelper.obj2str(request.getParameter("alarmDesc"), "");		
		String alarmSouce=StringHelper.obj2str(request.getParameter("alarmSouce"), "");
		
		String title=alarmTime.substring(11, alarmTime.length())+" "+region+" "+alarmSite+" "+alarmDesc;
		Object[] res=new Object[20];
		
		res[0]=alarmid;
		res[5]=alarmType;
		res[4]=alarmSouce;
		res[7]=alarmThinType;
		res[6]="";
		res[8]=alarmState;
		res[19]=title;
		res[1]=alarmTime;
		res[9]=alarmDesc;
		res[3]=alarmDeptId;
		
		String sql="insert into T_Attemper_Alarm(AlarmID,AlarmTime,AlarmAddress,AlarmUnit,AlarmRegionID,AlarmRegion,EventSource,EventType,EventState,EventThinType,AlarmDesc,SUBEVENTSOURCE,ReportTime,ReportPerson,ReportUnit,Title,DISPOSETIME,DISPOSEPERSON)";
		sql+=" VALUES('"+alarmid+"',to_date('"+alarmTime+"','yyyy-mm-dd hh24:mi'),'"+alarmSite+"','"+alarmDeptId+"','"+regionId+"','"+region+"','"+alarmSouce+"','"+alarmType+"','"+alarmState+"','"+alarmThinType+"'" +
				",'"+alarmDesc+"','"+subEventSource+"',to_date('"+reportTime+"','yyyy-mm-dd hh24:mi'),'"+reportPerson+"','"+reportUnitId+"','"+title+"',to_date('"+reportTime+"','yyyy-mm-dd hh24:mi'),'"+reportPerson+"')";
		boolean msg=DBHandler.execute(sql);
		//事故
		if(alarmType.equals("001001")){
			String accidentsql="insert into T_ATTEMPER_ACCIDENT(ALARMID,ALARMPERSON,ALARMPHONE,ALARMPERSONADDRESS) values('"+alarmid+"','"+alarmperson+"','"+alarmphone+"','"+alarmaddres+"')";
			DBHandler.execute(accidentsql);
		}
		//拥堵
		if(alarmType.equals("001002")){
			String congestiontsql="insert into T_ATTEMPER_CONGESTION(ALARMID,ALARMPERSON,ALARMPHONE,ALARMPERSONADDRESS) values('"+alarmid+"','"+alarmperson+"','"+alarmphone+"','"+alarmaddres+"')";
			DBHandler.execute(congestiontsql);
		}
		
		if(msg){
			AlarmInfoMsg infoMsg = new AlarmInfoMsg();
			infoMsg.SendAlarmMsg(res);
			out.write("true");
		}else{
			out.write("false");
		}
		out.close();
		return null;
	}
}
