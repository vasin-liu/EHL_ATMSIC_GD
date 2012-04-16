package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.business.AlarmInfoOpt;
import com.ehl.dispatch.bdispatch.business.CompleAlarmEditOpt;

public class CompleAlarmInfoEdit extends Controller {

	private Object[] getCompleBaseAlarmInfo(HttpServletRequest request,Object[] res){
		String alarmid=StringHelper.obj2str(request.getParameter("complealarmId"), "");
		String alarmTime=StringHelper.obj2str(request.getParameter("complealarmTime"), "");
		String alarmSite=StringHelper.obj2str(request.getParameter("complealarmSite"), "");
		String alarmDept=StringHelper.obj2str(request.getParameter("complealarmDept"), "");
		String subEventSource = StringHelper.obj2str(request.getParameter("complesubEventSource"),"");
		String alarmSouce=StringHelper.obj2str(request.getParameter("complealarmSouce"), "");
		String alarmType=StringHelper.obj2str(request.getParameter("complealarmType"), "");
		String alarmLevel=StringHelper.obj2str(request.getParameter("complealarmLevel"), "");
		String alarmThinType=StringHelper.obj2str(request.getParameter("complealarmThinType"), "");
		String alarmState=StringHelper.obj2str(request.getParameter("complealarmState"), "");
		String alarmDesc=StringHelper.obj2str(request.getParameter("complealarmDesc"), "");
		String roadId=StringHelper.obj2str(request.getParameter("compleroadId"), "");
		String roadName=StringHelper.obj2str(request.getParameter("compleroadName"), "");
		String X=StringHelper.obj2str(request.getParameter("compleX"), "");
		String Y=StringHelper.obj2str(request.getParameter("compleY"), "");
		
		String alarmLevelValue=StringHelper.obj2str(request.getParameter("complealarmLevelValue"), "");
		String alarmThinTypeValue=StringHelper.obj2str(request.getParameter("complealarmThinTypeValue"), "");
		
		String disposeTime=StringHelper.obj2str(request.getParameter("compledisposeTime"), "");
		String disposePerson=StringHelper.obj2str(request.getParameter("compledisposePerson"), "");
		String disposeUnit=StringHelper.obj2str(request.getParameter("compledisposeUnit"), "");
		String disposeIdea=StringHelper.obj2str(request.getParameter("compledisposeIdea"), "");
		
		
		String reportUnitName=StringHelper.obj2str(request.getParameter("complereportUnitName"), "");
		String reportTime=StringHelper.obj2str(request.getParameter("complereportTime"), "");
		String reportPerson=StringHelper.obj2str(request.getParameter("complereportPerson"), "");
		
		String complefksj=StringHelper.obj2str(request.getParameter("complefksj"), "");
		String complefkdw=StringHelper.obj2str(request.getParameter("complefkdw"), "");
		String complefkr=StringHelper.obj2str(request.getParameter("complefkr"), "");
		String complecjdw=StringHelper.obj2str(request.getParameter("complecjdw"), "");
		String complecjsj=StringHelper.obj2str(request.getParameter("complecjsj"), "");
		String compledjsj=StringHelper.obj2str(request.getParameter("compledjsj"), "");
		String complecjr=StringHelper.obj2str(request.getParameter("complecjr"), "");
		String compleajfssj=StringHelper.obj2str(request.getParameter("compleajfssj"), "");
		String compleajjssj=StringHelper.obj2str(request.getParameter("compleajjssj"), "");
		String complecjrs=StringHelper.obj2str(request.getParameter("complecjrs"), "");
		String complecdcl=StringHelper.obj2str(request.getParameter("complecdcl"), "");
		String complecljg=StringHelper.obj2str(request.getParameter("complecljg"), "");
		String complefkdwName=StringHelper.obj2str(request.getParameter("complefkdwName"), "");
		String complecjdwName=StringHelper.obj2str(request.getParameter("complecjdwName"), "");
		
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
		res[23]=subEventSource;
		
		res[24]=complefksj; //����ʱ��
		res[25]=complefkdw; //������λ
		res[26]=complefkr;	// ������
		res[27]=complecjdw; //������λ
		res[28]=complecjsj;	//����ʱ��
		res[29]=compledjsj;	//����ʱ��
		res[30]=complecjr;	//������
		
		res[31]=compleajjssj;	//����ʱ��
		res[32]=complecjrs;	// ��������
		res[33]=complecdcl;	//��������
		res[34]=complecljg;	 //������
		res[35]=complefkdwName;	//������λ����
		res[36]=complecjdwName;	//������λ����
		res[37]=compleajfssj;	//����ʱ��
		return res;
	}
	
	public ActionForward doEditCompleAccidentInfo (Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
				
		String accidentAlarmCarType	=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarType"), "");	
		String accidentAlarmCarCodeDept	=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarCodeDept"), "");	
		String accidentAlarmCarCode	=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarCode"), "");	
		String accidentAlarmCarGenre=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarGenre"), "");		
		String accidentremark=StringHelper.obj2str(request.getParameter("compleaccidentremark"), "");
		String accidentAffectLevel=StringHelper.obj2str(request.getParameter("compleaccidentAffectLevel"), "");
		
		String complejjss=StringHelper.obj2str(request.getParameter("complejjss"), "");	
		String compleqsrs=StringHelper.obj2str(request.getParameter("compleqsrs"), "");	
		String complezsrs=StringHelper.obj2str(request.getParameter("complezsrs"), "");	
		String compleswrs=StringHelper.obj2str(request.getParameter("compleswrs"), "");		
		String complesars=StringHelper.obj2str(request.getParameter("complesars"), "");
		String complezhrs=StringHelper.obj2str(request.getParameter("complezhrs"), "");
		String complejzrs	=StringHelper.obj2str(request.getParameter("complejzrs"), "");	
		String complephxsaj	=StringHelper.obj2str(request.getParameter("complephxsaj"), "");	
		String complecczaaj	=StringHelper.obj2str(request.getParameter("complecczaaj"), "");	
		String complejjjf=StringHelper.obj2str(request.getParameter("complejjjf"), "");	
		
		Object[] res=new Object[53];
		
		res=getCompleBaseAlarmInfo(request,res);		
				
		res[38]=accidentAlarmCarType;	//��������
		res[39]=accidentAlarmCarCodeDept+accidentAlarmCarCode;	//��������
		res[40]=accidentAlarmCarGenre;	//��������
		res[41]=accidentremark;	//��ע
		res[42]=accidentAffectLevel;	//Ӱ��̶ȣ�
		
		res[43]=complejjss;	//������ʧ��
		res[44]=compleqsrs;	//��������
		res[45]=complezsrs;	//��������
		res[46]=compleswrs;	//��������
		res[47]=complesars;	//�永����
		
		res[48]=complezhrs;	//ץ������
		res[49]=complejzrs;	//��������
		res[50]=complephxsaj;	//�ƻ����°���
		res[51]=complecczaaj;	//�鴦�ΰ�����
		res[52]=complejjjf;	//�������
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editAccidentInfo(res);
		out.write(resStr);
		out.close();
		return null;
	}	
	
	
	
	public ActionForward doEditCompleCongestionInfo (Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
				
		String accidentAlarmCarType	=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarType"), "");	
		String accidentAlarmCarCodeDept	=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarCodeDept"), "");	
		String accidentAlarmCarCode	=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarCode"), "");	
		String accidentAlarmCarGenre=StringHelper.obj2str(request.getParameter("compleaccidentAlarmCarGenre"), "");		
		String accidentremark=StringHelper.obj2str(request.getParameter("compleaccidentremark"), "");
		String accidentAffectLevel=StringHelper.obj2str(request.getParameter("compleaccidentAffectLevel"), "");
		
		String complejjss=StringHelper.obj2str(request.getParameter("complejjss"), "");	
		String compleqsrs=StringHelper.obj2str(request.getParameter("compleqsrs"), "");	
		String complezsrs=StringHelper.obj2str(request.getParameter("complezsrs"), "");	
		String compleswrs=StringHelper.obj2str(request.getParameter("compleswrs"), "");		
		String complesars=StringHelper.obj2str(request.getParameter("complesars"), "");
		String complezhrs=StringHelper.obj2str(request.getParameter("complezhrs"), "");
		String complejzrs	=StringHelper.obj2str(request.getParameter("complejzrs"), "");	
		String complephxsaj	=StringHelper.obj2str(request.getParameter("complephxsaj"), "");	
		String complecczaaj	=StringHelper.obj2str(request.getParameter("complecczaaj"), "");	
		String complejjjf=StringHelper.obj2str(request.getParameter("complejjjf"), "");	
		
		Object[] res=new Object[53];
		
		res=getCompleBaseAlarmInfo(request,res);		
				
		res[38]=accidentAlarmCarType;	//��������
		res[39]=accidentAlarmCarCodeDept+accidentAlarmCarCode;	//��������
		res[40]=accidentAlarmCarGenre;	//��������
		res[41]=accidentremark;	//��ע
		res[42]=accidentAffectLevel;	//Ӱ��̶ȣ�
		
		res[43]=complejjss;	//������ʧ��
		res[44]=compleqsrs;	//��������
		res[45]=complezsrs;	//��������
		res[46]=compleswrs;	//��������
		res[47]=complesars;	//�永����
		
		res[48]=complezhrs;	//ץ������
		res[49]=complejzrs;	//��������
		res[50]=complephxsaj;	//�ƻ����°���
		res[51]=complecczaaj;	//�鴦�ΰ�����
		res[52]=complejjjf;	//�������
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editCongestionInfo(res);
		out.write(resStr);
		out.close();
		return null;
	}	
	
	/**
	 * ����һ������������Ϣ
	 */
	public ActionForward doEditCompleBadWeatherInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
				
		String weathersituation=StringHelper.obj2str(request.getParameter("compleweathersituation"), "");
		String weatherRoadStatus=StringHelper.obj2str(request.getParameter("compleweatherRoadStatus"), "");
		String weatherAffectRoad=StringHelper.obj2str(request.getParameter("compleweatherAffectRoad"), "");
		String weatheAffectDept=StringHelper.obj2str(request.getParameter("compleweatheAffectDept"), "");
		String weatherAlarmPhone=StringHelper.obj2str(request.getParameter("compleweatherAlarmPhone"), "");
		String weatherAlarmPerson=StringHelper.obj2str(request.getParameter("compleweatherAlarmPerson"), "");
		String weatherAffectextent=StringHelper.obj2str(request.getParameter("compleweatherAffectextent"), "");
		String weatherRemark=StringHelper.obj2str(request.getParameter("compleweatherRemark"), "");
		
		Object[] res=new Object[46];
		res=getCompleBaseAlarmInfo(request,res);
		
		res[38]=weathersituation;
		res[39]=weatherRoadStatus;
		res[40]=weatherAffectRoad;
		res[41]=weatheAffectDept;
		res[42]=weatherAlarmPhone;
		res[43]=weatherAlarmPerson;
		res[44]=weatherAffectextent;
		res[45]=weatherRemark;
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editBadWeatherInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	
	/**
	 * ����һ���ΰ�������Ϣ
	 */
	public ActionForward doEditComplePoliceEventInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
				
		String PoliceEventAffectTrafficLevel=StringHelper.obj2str(request.getParameter("complePoliceEventAffectTrafficLevel"), "");
		String PoliceEventAffectRoad=StringHelper.obj2str(request.getParameter("complePoliceEventAffectRoad"), "");
		String PoliceEventRemark=StringHelper.obj2str(request.getParameter("complePoliceEventRemark"), "");
		String PoliceEventTime=StringHelper.obj2str(request.getParameter("complePoliceEventTime"), "");
		String PoliceEventAffectextent=StringHelper.obj2str(request.getParameter("complePoliceEventAffectextent"), "");
		
		Object[] res=new Object[43];
		res=getCompleBaseAlarmInfo(request,res);
		
		res[38]=PoliceEventAffectTrafficLevel;
		res[39]=PoliceEventAffectRoad;
		res[40]=PoliceEventRemark;
		res[41]=PoliceEventAffectextent;
		res[42]=PoliceEventTime;		
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editPoliceEventInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	
	/**
	 * ����һ�����ϳ�������Ϣ
	 */
	public ActionForward doEditCompleExceptionCarInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		
		String exceptionCarAddress=StringHelper.obj2str(request.getParameter("compleexceptionCarAddress"), "");
		String exceptionCause=StringHelper.obj2str(request.getParameter("compleexceptionCause"), "");
		String exceptioCarShape=StringHelper.obj2str(request.getParameter("compleexceptioCarShape"), "");
		String exceptioAffectLevel=StringHelper.obj2str(request.getParameter("compleexceptioAffectLevel"), "");
		String exceptionRemark=StringHelper.obj2str(request.getParameter("compleexceptionRemark"), "");
		String exceptionDriver=StringHelper.obj2str(request.getParameter("compleexceptionDriver"), "");
		String exceptionCarDept=StringHelper.obj2str(request.getParameter("compleexceptionCarDept"), "");
		
		Object[] res=new Object[45];
		res=getCompleBaseAlarmInfo(request,res);
		
		res[38]=exceptionCarAddress;
		res[39]=exceptionCause;
		res[40]=exceptioCarShape;
		res[41]=exceptioAffectLevel;
		res[42]=exceptionRemark;
		res[43]=exceptionDriver;
		res[44]=exceptionCarDept;
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editExceptionCarInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	

	/**
	 * ����һ�������ֺ���Ϣ
	 */
	public ActionForward doEditCompleGeoLogicDisasterInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
				
		String geoLogicDisasterAffectRoad=StringHelper.obj2str(request.getParameter("compleGeoLogicDisasterAffectRoad"), "");
		String geoLogicDisasterLevel=StringHelper.obj2str(request.getParameter("compleGeoLogicDisasterLevel"), "");
		String geoLogicDisasterAffectExent=StringHelper.obj2str(request.getParameter("compleGeoLogicDisasterAffectExent"), "");
		String geoLogicDisasterAlarmPerson=StringHelper.obj2str(request.getParameter("compleGeoLogicDisasterAlarmPerson"), "");
		String geoLogicDisasterRemark=StringHelper.obj2str(request.getParameter("compleGeoLogicDisasterRemark"), "");
		
		Object[] res=new Object[43];
		res=getCompleBaseAlarmInfo(request,res);
		
		res[38]=geoLogicDisasterAffectRoad;
		res[39]=geoLogicDisasterLevel;
		res[40]=geoLogicDisasterAffectExent;
		res[41]=geoLogicDisasterAlarmPerson;
		res[42]=geoLogicDisasterRemark;
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editGeoLogicDisasterInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * ����һ��������Ϣ
	 */
	public ActionForward doEditCompleTownplanInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
				
		String townPlanPerson=StringHelper.obj2str(request.getParameter("completownPlanPerson"), "");
		String townPlanAffectRoad=StringHelper.obj2str(request.getParameter("completownPlanAffectRoad"), "");
		String townPlanAffectExent=StringHelper.obj2str(request.getParameter("completownPlanAffectExent"), "");
		String townPlanLevel=StringHelper.obj2str(request.getParameter("completownPlanLevel"), "");
		String townPlanRemark=StringHelper.obj2str(request.getParameter("completownPlanRemark"), "");
		
		Object[] res=new Object[43];
		res=getCompleBaseAlarmInfo(request,res);
		
		res[38]=townPlanPerson;
		res[39]=townPlanAffectRoad;
		res[40]=townPlanAffectExent;
		res[41]=townPlanLevel;
		res[42]=townPlanRemark;	
		
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editTownplanInfo(res);
		
		out.write(resStr);
		out.close();
		return null;
	}
	
	/**
	 * ����һ��������Ϣ
	 */
	public ActionForward doEditCompleFireAndBlastInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		
		String fireAffectLevel=StringHelper.obj2str(request.getParameter("complefireAffectLevel"), "");
		String fireHaveCasualty=StringHelper.obj2str(request.getParameter("complefireHaveCasualty"), "");
		String fireAlarmPerson=StringHelper.obj2str(request.getParameter("complefireAlarmPerson"), "");
		String fireRemark=StringHelper.obj2str(request.getParameter("complefireRemark"), "");
		
		String compleaffectRoad=StringHelper.obj2str(request.getParameter("compleaffectRoad"), "");
		String complecompleFLESHWOUNDPERSONCOUNT=StringHelper.obj2str(request.getParameter("complecompleFLESHWOUNDPERSONCOUNT"), "");
		String compleGBHPERSONCOUNT=StringHelper.obj2str(request.getParameter("compleGBHPERSONCOUNT"), "");
		String compleDEATHPERSONCOUNT=StringHelper.obj2str(request.getParameter("compleDEATHPERSONCOUNT"), "");
		String compleeconomyloss=StringHelper.obj2str(request.getParameter("compleeconomyloss"), "");
		
		Object[] res=new Object[47];
		res=getCompleBaseAlarmInfo(request,res);
		
		
		res[38]=fireAffectLevel;
		res[39]=fireHaveCasualty;
		res[40]=fireAlarmPerson;
		res[41]=fireRemark;
		
		res[42]=compleaffectRoad;
		res[43]=complecompleFLESHWOUNDPERSONCOUNT;
		res[44]=compleGBHPERSONCOUNT;
		res[45]=compleDEATHPERSONCOUNT;
		res[46]=compleeconomyloss;
	
		CompleAlarmEditOpt alarmEditOpt=new CompleAlarmEditOpt();
		String resStr=alarmEditOpt.editFireAndBlastInfo(res);
		out.write(resStr);
		out.close();
		return null;
	}
}
