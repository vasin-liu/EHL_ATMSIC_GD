package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.business.FeedBackMgr;
import com.ehl.sm.common.util.StringUtil;

public class PoliceDisoposeCtrl extends Controller {
	
	public ActionForward doGetPoliceBaseInfos(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 
		PrintWriter out = response.getWriter();
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");
		String mobilephone=StringHelper.obj2str(request.getParameter("mobilephone"),"");
		String feedbackId = StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
		String time = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm");
		String hourTime=StringUtil.getCurrDateTime("HH:mm");
		String serchsql="select feedbackid,FeedbackDesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		Object[][] serchres=DBHandler.getMultiResult(serchsql);
		boolean msg=false;
		String desc="";
		if(serchres==null){
			String alarmsql="select to_char(AlarmTime,'hh24:mi'),(select name from t_attemper_code where id=t.EVENTSOURCE),disposeperson,ALARMADDRESS,ALARMDESC,EVENTSOURCE from T_Attemper_Alarm t where alarmid='"+alarmId+"'";
			Object[] alarms=DBHandler.getLineResult(alarmsql);
			desc=StringHelper.obj2str(alarms[0],"")+" "+StringHelper.obj2str(alarms[1],"")+" "+StringHelper.obj2str(alarms[2],"")+" "+StringHelper.obj2str(alarms[3],"")+" "+StringHelper.obj2str(alarms[4],"");
			String desctext=desc+"*NextRow*";
			desctext+=hourTime+" "+attemper+"("+mobilephone+")"+" 开始调度*NextRow*";
			String backsql="insert into T_Attemper_Feedback(FeedbackID,AlarmID,FeedbackDesc) values('"+feedbackId+"','"+alarmId+"','"+desctext+"')";
			DBHandler.execute(backsql);
			String mainsql="update T_Attemper_Alarm set EventState='004021',ATTEMPER='"+attemper+"',attemperid='"+attemperid+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			if(StringHelper.obj2str(alarms[5],"").equals("002001")){
	        	FeedBackMgr.SendFeedBackMsg(alarmId,"1");
			}
			msg=true;
		}
		String haveAttemperSql="select EventState,ATTEMPER,EVENTSOURCE from T_Attemper_Alarm where  alarmid='"+alarmId+"'";
		Object[] alarmres=DBHandler.getLineResult(haveAttemperSql);
		String dbAttemper=StringHelper.obj2str(alarmres[1],"");
		if(dbAttemper.equals("")){
			String mainsql="update T_Attemper_Alarm set ATTEMPER='"+attemper+"',attemperid='"+attemperid+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String textdesc=StringHelper.obj2str(serchres[0][1],"")+hourTime+" "+attemper+"("+mobilephone+")"+" 开始调度*NextRow*";;
			String descsql="update T_Attemper_Feedback set FeedbackDesc='"+textdesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(descsql);
		}		
		if(StringHelper.obj2str(alarmres[0],"").equals("004012")){
			String mainsql="update T_Attemper_Alarm set EventState='004021',ATTEMPER='"+attemper+"',attemperid='"+attemperid+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);			
			if(StringHelper.obj2str(alarmres[2],"").equals("002001")){
	        	FeedBackMgr.SendFeedBackMsg(alarmId,"1");
			}	
			msg=true;
		}
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		String sql="select AlarmID,to_char(AlarmTime,'yyyy-mm-dd hh24:mi'),AlarmAddress,EventType,EventLevel,AlarmDesc" +
				",to_char(ASSIGNTIME,'hh24:mi'),to_char(ComeOutTime,'hh24:mi'),to_char(COMEOUTARRIVETIME,'hh24:mi')" +
				",to_char(FINISHTIME,'hh24:mi'),to_char(TRAFFICREVERTTIME,'hh24:mi')" +
				" ,WEATHER,EVENTTHINTYPE,ROADTYPE,DISPOSERESULT from T_Attemper_Alarm where AlarmID='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(sql);
		String feedbacksql="select to_char(FEEDBACKTIME,'hh24:mi'),feedbackdesc,FLESHWOUNDPERSONCOUNT,DEATHPERSONCOUNT from T_Attemper_Feedback where AlarmID='"+alarmId+"'";
		Object[][] feedbackres=DBHandler.getMultiResult(feedbacksql);
//		String personsql="select POLICEID,POLICENAME from T_ATTEMPER_POLICEHISTORY where alarmid='"+alarmId+"' and type='PDA' and (policeid in (select policeid from t_attemper_duty where state = '1' and policeid is not null) or policeid not in (select policeid from t_attemper_duty where  policeid is not null) )";
		String personsql="select POLICEID,POLICENAME,code.name from T_ATTEMPER_POLICEHISTORY,t_attemper_code code where alarmid='"+alarmId+"' and type='PDA' and state in ('004006','004017','004018','004010') and state=code.id";
		Object[][] personres=DBHandler.getMultiResult(personsql);
		
//		String carsql="select carcode,carnumber from T_ATTEMPER_POLICEHISTORY where alarmid='"+alarmId+"' and type='GPS' and (carcode in (select carcode from t_attemper_duty where state = '1' and carcode is not null) or carcode not in (select carcode from t_attemper_duty where carcode is not null) )";
		String carsql="select carcode,carnumber,code.name from T_ATTEMPER_POLICEHISTORY,t_attemper_code code  where alarmid='"+alarmId+"' and type='GPS' and state in ('004006','004017','004018','004010') and state=code.id";
		Object[][] cars=DBHandler.getMultiResult(carsql);
		
		String accidentsql="select ALARMPERSON,ALARMPHONE from T_ATTEMPER_ACCIDENT where alarmid='"+alarmId+"'";
		Object[][] accident=DBHandler.getMultiResult(accidentsql);
		
//		for(int i=0;i<res.length;i++){
			sbXml.append("<row id='0'>\n");
			for (int j = 0; j < res[0].length; j++) {
				sbXml.append("<col>");
				sbXml.append(StringHelper.obj2str(res[0][j],""));
				sbXml.append("</col>\n");
			}
			if(feedbackres!=null){
				for (int j = 0; j < feedbackres[0].length; j++) {
					sbXml.append("<col>");
					sbXml.append(StringHelper.obj2str(feedbackres[0][j],""));
					sbXml.append("</col>\n");
				}
			}
			if(accident!=null){
				if(!StringHelper.obj2str(accident[0][1],"").equals("")){
					sbXml.append("<col>");
					sbXml.append(StringHelper.obj2str(accident[0][0],"")+"("+StringHelper.obj2str(accident[0][1],"")+")");
					sbXml.append("</col>\n");
				}else{
					sbXml.append("<col>");
					sbXml.append(StringHelper.obj2str(accident[0][0],""));
					sbXml.append("</col>\n");
				}
			}
			sbXml.append("</row>\n");
//		}
		if(personres!=null){
			for(int i=0;i<personres.length;i++){
				sbXml.append("<person pid='"+personres[i][0]+"' pname='"+personres[i][1]+"' pstate='"+personres[i][2]+"'>");				
				sbXml.append("</person>\n");				
			}			
		}
		
		if(cars!=null){
			for(int i=0;i<cars.length;i++){
				sbXml.append("<car cid='"+cars[i][0]+"' cnum='"+cars[i][1]+"' cstate='"+cars[i][2]+"'>");				
				sbXml.append("</car>\n");				
			}			
		}
		sbXml.append("<desc>");				
		sbXml.append(desc);				
		sbXml.append("</desc>\n");	
		if(msg){
			FeedBackMgr.SendDisposeMsg(alarmId,"",StringHelper.obj2str(res[0][3],""),"","004021",time,attemper,"","","","","");
//			SendDisposeMsg(String alarmId,String eventsource ,String eventtype,String eventunit,String state,String time,String attemper,String title,String eventlevel,String isquit,String persons,String cars)
		}
		sbXml.append("</RFWin>\n");
		sbXml.append("</rfXML>\n");
		String xml=sbXml.toString();
		out.write(xml);
		out.close();
		return null;
	}	
	
	//得到人员和车辆最新状态信息
	public ActionForward doGetPersonAndCar(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
	 	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
        
        String personsql="select POLICEID,POLICENAME,code.name from T_ATTEMPER_POLICEHISTORY,t_attemper_code code where alarmid='"+alarmId+"' and type='PDA' and state in ('004006','004017','004018','004010') and state=code.id";
		Object[][] personres=DBHandler.getMultiResult(personsql);
        
		String carsql="select carcode,carnumber,code.name from T_ATTEMPER_POLICEHISTORY,t_attemper_code code  where alarmid='"+alarmId+"' and type='GPS' and state in ('004006','004017','004018','004010') and state=code.id";
		Object[][] cars=DBHandler.getMultiResult(carsql);
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		if(personres!=null){
			for(int i=0;i<personres.length;i++){
				sbXml.append("<person pid='"+personres[i][0]+"' pname='"+personres[i][1]+"' pstate='"+personres[i][2]+"'>");				
				sbXml.append("</person>\n");				
			}			
		}
		
		if(cars!=null){
			for(int i=0;i<cars.length;i++){
				sbXml.append("<car cid='"+cars[i][0]+"' cnum='"+cars[i][1]+"' cstate='"+cars[i][2]+"'>");				
				sbXml.append("</car>\n");				
			}			
		}
		sbXml.append("</RFWin>\n");
		sbXml.append("</rfXML>\n");
		String xml=sbXml.toString();
		out.write(xml);
        out.close();
        return null;
	}
	
	public boolean startDispose(String alarmId){
		boolean msg=false;
		String feedbackId = StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
		String time = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm");
		String backsql="insert into T_Attemper_Feedback(FeedbackID,AlarmID) values('"+feedbackId+"','"+alarmId+"')";
		msg=DBHandler.execute(backsql);
		String mainsql="update T_Attemper_Alarm set EventState='004021' where alarmid='"+alarmId+"'";
		DBHandler.execute(mainsql);
//		FeedBackMgr.SendDisposeMsg(alarmId,"","","","004021",time);
		return msg;
	}
	
	

	//派警
	public ActionForward doAssignPolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String assignTime = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventLevel=StringHelper.obj2str(request.getParameter("eventLevel"),"");
		String weatherinfo=StringHelper.obj2str(request.getParameter("weatherinfo"),"");	
		String disposeRoadType=StringHelper.obj2str(request.getParameter("disposeRoadType"),"");	
		String disposeResult=StringHelper.obj2str(request.getParameter("disposeResult"),"");	
		String disposeThinType=StringHelper.obj2str(request.getParameter("disposeThinType"),"");	
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");	
		String state="004006";
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String phone=StringHelper.obj2str(request.getParameter("phone"),"");		
		String time = StringUtil.getCurrDateTime("HH:mm");
		
		String personIds = StringHelper.obj2str(request.getParameter("personIds"),"");	
		String personNames = StringHelper.obj2str(request.getParameter("personNames"),"");
		
		String carIds = StringHelper.obj2str(request.getParameter("carIds"),"");	
		String carNums = StringHelper.obj2str(request.getParameter("carNums"),"");
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");
		//判断该警情是否正被调度
		String attempersql="select attemper,EventState from T_Attemper_Alarm where alarmid='"+alarmId+"'";
		Object[] dbinfos=DBHandler.getLineResult(attempersql);
		String dbattemper=StringHelper.obj2str(dbinfos[0],"");
		if(!dbattemper.equals(attemper)){
			out.write("null");
			out.close();
			return null;
		}
		String assignstate=StringHelper.obj2str(dbinfos[1],"");
		
		List<String> tempperson =new ArrayList<String>();
		List<String> temppersonName =new ArrayList<String>();
		List<String> tempcar =new ArrayList<String>();
		List<String> tempcarNum =new ArrayList<String>();
		
		String desc=time+" "+person+"("+phone+") 执行派警操作";
		String newPersonNames="";
		if(!personNames.equals("")){			
			String historysql="select policeid from T_ATTEMPER_POLICEHISTORY where type='PDA' and alarmid='"+alarmId+"' and state in ('004006','004017','004018','004010')";
			Object[][] res=DBHandler.getMultiResult(historysql); //已派警人员
			String[] persons=personIds.split(",");
			String[] personxms=personNames.split(",");
			if(res==null){
				personNames=personNames.substring(0,personNames.length()-1);
				desc+=" 派警员 "+personNames;
				if(persons!=null){
					for(int j=0;j<persons.length;j++){
						tempperson.add(persons[j]);
						temppersonName.add(personxms[j]);
					}
				}
				newPersonNames=personNames;
			}else{				
				for(int j=0;j<persons.length;j++){
					boolean tag=false;
					for(int i=0;i<res.length;i++){
						if(StringHelper.obj2str(res[i][0]).equals(StringHelper.obj2str(persons[j], ""))){							
							tag=true;
						}						
					}
					if(tag==false){
						tempperson.add(persons[j]);
						temppersonName.add(personxms[j]);						
							if(j!=persons.length-1){
								newPersonNames+=personxms[j]+",";
							}else{
								newPersonNames+=personxms[j];
							}						
					}
				}
				desc+=" 派警员 "+newPersonNames;
			}			
		}		
		desc+="*NextRow*";
		//判断是否选择警力
		if(newPersonNames.equals("")){
			out.write("kong");
			out.close();
			return null;
		}
		
		String newCarNums="";
		if(!carNums.equals("")){			
			String historysql="select carcode from T_ATTEMPER_POLICEHISTORY where type='GPS' and alarmid='"+alarmId+"' and state in ('004006','004017','004018','004010')";
			Object[][] res=DBHandler.getMultiResult(historysql); 
			String[] cars=carIds.split(",");
			String[] carnumbers=carNums.split(",");
			if(res==null){
				carNums=carNums.substring(0,carNums.length()-1);
//				desc+=" 派警员 "+personNames;
				if(cars!=null){
					for(int j=0;j<cars.length;j++){
						tempcar.add(cars[j]);
						tempcarNum.add(carnumbers[j]);
					}
				}
				newCarNums=carNums;
			}else{				
				for(int j=0;j<cars.length;j++){
					boolean tag=false;
					for(int i=0;i<res.length;i++){
						if(StringHelper.obj2str(res[i][0]).equals(StringHelper.obj2str(cars[j], ""))){							
							tag=true;
						}						
					}
					if(tag==false){
						tempcar.add(cars[j]);
						tempcarNum.add(carnumbers[j]);						
						if(j!=cars.length-1){
							newCarNums+=carnumbers[j]+",";
						}else{
							newCarNums+=carnumbers[j];
						}						
					}
				}
//				desc+=" 派警员 "+newPersonNames;
			}			
		}				
		
		String feedbackinfosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(feedbackinfosql);
		String alldesc=StringHelper.obj2str(res[0][0],"")+desc;
		
		String timesql="select to_char(ASSIGNTIME,'hh24:mi') from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		String assigntime=StringHelper.obj2str(DBHandler.getSingleResult(timesql),"");		
//		if(assigntime!=null&&!assigntime.equals("")){
		if(!assignstate.equals("")&&!assignstate.equals("004021")){
			time=assigntime;
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',ComeOutUnit='"+deptId+"',COMEOUTPERSON='"+personNames+"',ATTEMPER='"+attemper+"'" +
					",WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
			" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String backsql="update T_Attemper_Feedback set COMEOUTUNIT='"+deptId+"'," +
							"COMEOUTPERSON='"+personNames+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);	
		}else{
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',EventState='"+state+"',ComeOutUnit='"+deptId+"',COMEOUTPERSON='"+personNames+"'," +
			"ASSIGNTIME=to_date('"+assignTime+"','YYYY-MM-DD HH24:mi:ss'),WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"'" +
			" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String backsql="update T_Attemper_Feedback set ASSIGNTIME=to_date('"+assignTime+"','YYYY-MM-DD HH24:mi:ss'),COMEOUTUNIT='"+deptId+"'," +
							"COMEOUTPERSON='"+personNames+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
		}
				
		if(!personIds.equals("")){
//			String[] persons=personIds.split(",");
//			String[] personxms=personNames.split(",");
			int num=tempperson.size();
			for(int i=0;i<num;i++){
				String sql="insert into T_ATTEMPER_POLICEHISTORY(ALARMID,TYPE,POLICEID,POLICENAME,ASSINGTIME,state)" +
						" values('"+alarmId+"','PDA','"+tempperson.get(i)+"','"+temppersonName.get(i)+"',to_date('"+assignTime+"','yyyy-mm-dd hh24:mi:ss'),'"+state+"')";
				DBHandler.execute(sql);
				String dutysql="update T_ATTEMPER_DUTY set STATE='1' where POLICEID='"+tempperson.get(i)+"'";
				DBHandler.execute(dutysql);
			}
		}		
		
		if(!carIds.equals("")){
			String[] carids=carIds.split(",");
			String[] carnums=carNums.split(",");
			for(int i=0;i<tempcar.size();i++){
				String sql="insert into T_ATTEMPER_POLICEHISTORY(ALARMID,TYPE,carcode,carnumber,ASSINGTIME,state)" +
						" values('"+alarmId+"','GPS','"+tempcar.get(i)+"','"+tempcarNum.get(i)+"',to_date('"+assignTime+"','yyyy-mm-dd hh24:mi:ss'),'"+state+"')";
				DBHandler.execute(sql);
				String dutysql="update T_ATTEMPER_DUTY set STATE='1' where CARCODE='"+tempcar.get(i)+"'";
				DBHandler.execute(dutysql);
			}
		}
		
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alarmId);	
        xmlData.append("</col>\n");	
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        out.write(xmlData.toString());
        out.close();
        FeedBackMgr.SendDisposeMsg(alarmId,"",deptId,"",state,assignTime,attemper,"","","","","");
		return null;
	}	

//	出警
	public ActionForward doOutPolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String outTime = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventLevel=StringHelper.obj2str(request.getParameter("eventLevel"),"");
		String weatherinfo=StringHelper.obj2str(request.getParameter("weatherinfo"),"");	
		String disposeRoadType=StringHelper.obj2str(request.getParameter("disposeRoadType"),"");	
		String disposeResult=StringHelper.obj2str(request.getParameter("disposeResult"),"");
		String disposeThinType=StringHelper.obj2str(request.getParameter("disposeThinType"),"");
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");	
		String state="004017";
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String phone=StringHelper.obj2str(request.getParameter("phone"),"");		
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");
		String personIds=StringHelper.obj2str(request.getParameter("personIds"),"");	
		String personNames=StringHelper.obj2str(request.getParameter("personNames"),"");	
		String carIds=StringHelper.obj2str(request.getParameter("carIds"),"");	
		String carNums=StringHelper.obj2str(request.getParameter("carNums"),"");	
//		判断该警情是否正被调度
		String attempersql="select attemper,eventstate from T_Attemper_Alarm where alarmid='"+alarmId+"'";
		Object[] dbinfo=DBHandler.getLineResult(attempersql);
		String dbattemper=StringHelper.obj2str(dbinfo[0],"");
		if(!dbattemper.equals(attemper)){
			out.write("null");
			out.close();
			return null;
		}
		String outstate=StringHelper.obj2str(dbinfo[1],"");
		String time = StringUtil.getCurrDateTime("HH:mm");
		if(personNames.endsWith(",")){
			personNames=personNames.substring(0, personNames.length()-1);
		}		
		String desc=time+" "+person+"("+phone+") 确认警员 "+personNames+" 已出警"+"*NextRow*";
//		String desc=time+" "+person+"("+phone+") 确认已出警*NextRow*";
		String timesql="select to_char(COMEOUTTIME,'hh24:mi') from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		String comeoutTime=StringHelper.obj2str(DBHandler.getSingleResult(timesql),"");
		String alldesc="";		
		
		if(comeoutTime!=null&&!comeoutTime.equals("")){
//		if(!outstate.equals("")&&!outstate.equals("004021")&&!outstate.equals("004006")){
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',ComeOutUnit='"+deptId+"'," +
					"COMEOUTTIME=to_date('"+outTime+"','YYYY-MM-DD HH24:mi:ss'),ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
					" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
			Object[][] res=DBHandler.getMultiResult(infosql);
			alldesc=StringHelper.obj2str(res[0][0],"")+desc;
			String backsql="update T_Attemper_Feedback set COMEOUTTIME=to_date('"+outTime+"','YYYY-MM-DD HH24:mi:ss'),COMEOUTUNIT='"+deptId+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
			time=comeoutTime;
		}else{
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',EventState='"+state+"',ComeOutUnit='"+deptId+"'," +
			"COMEOUTTIME=to_date('"+outTime+"','YYYY-MM-DD HH24:mi:ss'),ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
			" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
			Object[][] res=DBHandler.getMultiResult(infosql);
			alldesc=StringHelper.obj2str(res[0][0],"")+desc;
			String backsql="update T_Attemper_Feedback set COMEOUTTIME=to_date('"+outTime+"','YYYY-MM-DD HH24:mi:ss'),COMEOUTUNIT='"+deptId+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
		}
		String[] pids=personIds.split(",");
		if(pids!=null){
			for(int i=0;i<pids.length;i++){
				String personstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+state+"',LEAVETIME=to_date('"+outTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and POLICEID='"+pids[i]+"'";
				DBHandler.execute(personstatesql);
			}
		}
		
		String[] cids=carIds.split(",");
		if(cids!=null){
			for(int i=0;i<cids.length;i++){
				String carstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+state+"',LEAVETIME=to_date('"+outTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and CARCODE='"+cids[i]+"'";
				DBHandler.execute(carstatesql);
			}
		}
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alarmId);	
        xmlData.append("</col>\n");	
//        String[] descinfo=alldesc.split("*NextRow*");
//        
//        for(int i=0;i<descinfo.length;i++){
//        	 xmlData.append("<desc>\n");	
//             xmlData.append(descinfo[i]);	
//             xmlData.append("</desc>\n");	
//        }
//        
       
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        out.write(xmlData.toString());
        out.close();
        FeedBackMgr.SendDisposeMsg(alarmId,"",deptId,"",state,outTime,attemper,"","","","","");
		return null;
	}	
	
	 
//	到达
	public ActionForward doArrivePolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");
		String arriveTime = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventLevel=StringHelper.obj2str(request.getParameter("eventLevel"),"");
		String weatherinfo=StringHelper.obj2str(request.getParameter("weatherinfo"),"");	
		String disposeRoadType=StringHelper.obj2str(request.getParameter("disposeRoadType"),"");	
		String disposeResult=StringHelper.obj2str(request.getParameter("disposeResult"),"");	
		String disposeThinType=StringHelper.obj2str(request.getParameter("disposeThinType"),"");
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");	
		String state="004018";
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String phone=StringHelper.obj2str(request.getParameter("phone"),"");		
		String personIds=StringHelper.obj2str(request.getParameter("personIds"),"");	
		String personNames=StringHelper.obj2str(request.getParameter("personNames"),"");	
		String carIds=StringHelper.obj2str(request.getParameter("carIds"),"");	
		String carNums=StringHelper.obj2str(request.getParameter("carNums"),"");	
//		判断该警情是否正被调度
		String attempersql="select attemper from T_Attemper_Alarm where alarmid='"+alarmId+"'";
		String dbattemper=StringHelper.obj2str(DBHandler.getSingleResult(attempersql),"");
		if(!dbattemper.equals(attemper)){
			out.write("null");
			out.close();
			return null;
		}
		String time = StringUtil.getCurrDateTime("HH:mm");
		if(personNames.endsWith(",")){
			personNames=personNames.substring(0, personNames.length()-1);
		}		
		String desc=time+" "+person+"("+phone+") 确认警员 "+personNames+" 已到达"+"*NextRow*";
//		String desc=time+" "+person+"("+phone+") 确认警员已到达*NextRow*";
		String timesql="select to_char(COMEOUTARRIVETIME,'hh24:mi') from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		String comeoutarriveTime=StringHelper.obj2str(DBHandler.getSingleResult(timesql),"");
		String alldesc="";
		if(comeoutarriveTime!=null&&!comeoutarriveTime.equals("")){
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',ComeOutUnit='"+deptId+"'," +
					"COMEOUTARRIVETIME=to_date('"+arriveTime+"','YYYY-MM-DD HH24:mi:ss'),ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
					" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
			Object[][] res=DBHandler.getMultiResult(infosql);
			alldesc=StringHelper.obj2str(res[0][0],"")+desc;
			String backsql="update T_Attemper_Feedback set COMEOUTARRIVETIME=to_date('"+arriveTime+"','YYYY-MM-DD HH24:mi:ss'),COMEOUTUNIT='"+deptId+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
			time=comeoutarriveTime;
		}else{
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',EventState='"+state+"',ComeOutUnit='"+deptId+"'," +
			"COMEOUTARRIVETIME=to_date('"+arriveTime+"','YYYY-MM-DD HH24:mi:ss'),ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
			" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
			Object[][] res=DBHandler.getMultiResult(infosql);
			alldesc=StringHelper.obj2str(res[0][0],"")+desc;
			String backsql="update T_Attemper_Feedback set COMEOUTARRIVETIME=to_date('"+arriveTime+"','YYYY-MM-DD HH24:mi:ss'),COMEOUTUNIT='"+deptId+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
		}
		String[] pids=personIds.split(",");
		if(pids!=null){
			for(int i=0;i<pids.length;i++){
				String personstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+state+"',ARRIVETIME=to_date('"+arriveTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and POLICEID='"+pids[i]+"'";
				DBHandler.execute(personstatesql);
			}
		}
		
		String[] cids=carIds.split(",");
		if(cids!=null){
			for(int i=0;i<cids.length;i++){
				String carstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+state+"',ARRIVETIME=to_date('"+arriveTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and CARCODE='"+cids[i]+"'";
				DBHandler.execute(carstatesql);
			}
		}
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alarmId);	
        xmlData.append("</col>\n");	
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        out.write(xmlData.toString());
        FeedBackMgr.SendDisposeMsg(alarmId,"",deptId,"",state,arriveTime,attemper,"","","","","");
        out.close();
		return null;
	}	
	
	//反馈
	public ActionForward doFeedbackPolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");
		String feedbackTime = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventLevel=StringHelper.obj2str(request.getParameter("eventLevel"),"");
		String weatherinfo=StringHelper.obj2str(request.getParameter("weatherinfo"),"");	
		String disposeRoadType=StringHelper.obj2str(request.getParameter("disposeRoadType"),"");	
		String disposeResult=StringHelper.obj2str(request.getParameter("disposeResult"),"");	
		String disposeThinType=StringHelper.obj2str(request.getParameter("disposeThinType"),"");	
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");	
		String state="004010";
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String backdesc=StringHelper.obj2str(request.getParameter("backdesc"),"");
		String disposessrs=StringHelper.obj2str(request.getParameter("disposessrs"),"");
		String disposeswrs=StringHelper.obj2str(request.getParameter("disposeswrs"),"");
//		判断该警情是否正被调度
		String attempersql="select attemper from T_Attemper_Alarm where alarmid='"+alarmId+"'";
		String dbattemper=StringHelper.obj2str(DBHandler.getSingleResult(attempersql),"");
		if(!dbattemper.equals(attemper)){
			out.write("null");
			out.close();
			return null;
		}
		String time = StringUtil.getCurrDateTime("HH:mm");
		String desc=time+" "+backdesc+"*NextRow*";
		String timesql="select to_char(FEEDBACKTIME,'hh24:mi') from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		String feedbacktime=StringHelper.obj2str(DBHandler.getSingleResult(timesql),"");
		String alldesc="";
		if(feedbacktime!=null&&!feedbacktime.equals("")){
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',ATTEMPER='"+attemper+"' ,WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +				
					" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
			Object[][] res=DBHandler.getMultiResult(infosql);
			alldesc=StringHelper.obj2str(res[0][0],"")+desc;
			String backsql="update T_Attemper_Feedback set FEEDBACKTIME=to_date('"+feedbackTime+"','YYYY-MM-DD HH24:mi:ss'),FLESHWOUNDPERSONCOUNT='"+disposessrs+"',DEATHPERSONCOUNT='"+disposeswrs+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
			time=feedbacktime;
		}else{
			String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',EventState='"+state+"',ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +				
					" where alarmid='"+alarmId+"'";
			DBHandler.execute(mainsql);
			String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
			Object[][] res=DBHandler.getMultiResult(infosql);
			alldesc=StringHelper.obj2str(res[0][0],"")+desc;
			String backsql="update T_Attemper_Feedback set FEEDBACKTIME=to_date('"+feedbackTime+"','YYYY-MM-DD HH24:mi:ss'),FLESHWOUNDPERSONCOUNT='"+disposessrs+"',DEATHPERSONCOUNT='"+disposeswrs+"',FEEDBACKUNIT='"+deptId+"'," +
							" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
			DBHandler.execute(backsql);
		}
		
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alarmId);	
        xmlData.append("</col>\n");	
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        FeedBackMgr.SendDisposeMsg(alarmId,"",deptId,"",state,feedbackTime,attemper,"","","","","");
        out.write(xmlData.toString());
        out.close();
		return null;
	}	
	
	
	//撤场
	public ActionForward doFinishPolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String finishTime = "";
		String personFinishTime =StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");		
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventLevel=StringHelper.obj2str(request.getParameter("eventLevel"),"");
		String weatherinfo=StringHelper.obj2str(request.getParameter("weatherinfo"),"");	
		String disposeRoadType=StringHelper.obj2str(request.getParameter("disposeRoadType"),"");	
		String disposeResult=StringHelper.obj2str(request.getParameter("disposeResult"),"");	
		String disposeThinType=StringHelper.obj2str(request.getParameter("disposeThinType"),"");	
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");	
		String personIds=StringHelper.obj2str(request.getParameter("personIds"),"");	
		String personNames=StringHelper.obj2str(request.getParameter("personNames"),"");	
		String carIds=StringHelper.obj2str(request.getParameter("carIds"),"");	
		String carNums=StringHelper.obj2str(request.getParameter("carNums"),"");	
		String allperson=StringHelper.obj2str(request.getParameter("allperson"),"");	
		String allcar=StringHelper.obj2str(request.getParameter("allcar"),"");	
//		String state="004019";
		String state="004010";	//反馈状态
		String clstate="004019"; //撤离状态
//		判断该警情是否正被调度
		String attempersql="select attemper from T_Attemper_Alarm where alarmid='"+alarmId+"'";
		String dbattemper=StringHelper.obj2str(DBHandler.getSingleResult(attempersql),"");
		if(!dbattemper.equals(attemper)){
			out.write("null");
			out.close();
			return null;
		}
		boolean tmp=false;
		if(allperson.equals("true")&&allcar.equals("true")){
			tmp=true;
			state=clstate;
			finishTime =StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		}

		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String phone=StringHelper.obj2str(request.getParameter("phone"),"");		
		String time = StringUtil.getCurrDateTime("HH:mm");
		if(personNames.endsWith(",")){
			personNames=personNames.substring(0, personNames.length()-1);
		}
		
		String desc=time+" "+person+"("+phone+") 确认警员 "+personNames+" 已撤离"+"*NextRow*";
//		String desc=time+" "+person+" 确认警员已撤离";
		String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',EventState='"+state+"'," +
				"FINISHTIME=to_date('"+finishTime+"','YYYY-MM-DD HH24:mi:ss'),ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
				" where alarmid='"+alarmId+"'";
		DBHandler.execute(mainsql);
		String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(infosql);
		String alldesc=StringHelper.obj2str(res[0][0],"")+desc;
		String backsql="update T_Attemper_Feedback set FINISHTIME=to_date('"+finishTime+"','YYYY-MM-DD HH24:mi:ss'),FEEDBACKUNIT='"+deptId+"'," +
						" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
		DBHandler.execute(backsql);
		
		String[] pids=personIds.split(",");
		if(pids!=null){
			for(int i=0;i<pids.length;i++){
				String dutypersonsql="update T_ATTEMPER_DUTY set state='0' where policeid='"+pids[i]+"'";
				DBHandler.execute(dutypersonsql);
				String personstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+clstate+"',REMOVETIME=to_date('"+personFinishTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and POLICEID='"+pids[i]+"'";
				DBHandler.execute(personstatesql);
			}
		}
		
		String[] cids=carIds.split(",");
		if(cids!=null){
			for(int i=0;i<cids.length;i++){
				String dutycarsql="update T_ATTEMPER_DUTY set state='0' where carcode='"+cids[i]+"'";
				DBHandler.execute(dutycarsql);
				String carstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+clstate+"',REMOVETIME=to_date('"+personFinishTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and CARCODE='"+cids[i]+"'";
				DBHandler.execute(carstatesql);
			}
		}
		
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alarmId);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alarmId);	
        xmlData.append("</col>\n");	
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        out.write(xmlData.toString());
        out.close();
//        FeedBackMgr.SendDisposeMsg(alarmId,"","",deptId,state,finishTime);
        FeedBackMgr.SendDisposeMsg(alarmId,"",deptId,"",state,finishTime,attemper,"","","","","");
//        FeedBackMgr.SendFeedBackMsg(ALARMID,sjly,ajlx,fkdw,EVENTSTATE,time);
		return null;
	}	
	
	
	//恢复交通
	public ActionForward doTrafficrevertPolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String attemper=StringHelper.obj2str(request.getParameter("attemper"),"");
		String traffTime = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventLevel=StringHelper.obj2str(request.getParameter("eventLevel"),"");
		String weatherinfo=StringHelper.obj2str(request.getParameter("weatherinfo"),"");	
		String disposeRoadType=StringHelper.obj2str(request.getParameter("disposeRoadType"),"");	
		String disposeResult=StringHelper.obj2str(request.getParameter("disposeResult"),"");	
		String disposeThinType=StringHelper.obj2str(request.getParameter("disposeThinType"),"");	
		String attemperid=StringHelper.obj2str(request.getParameter("attemperid"),"");	
		String state="004020";
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String phone=StringHelper.obj2str(request.getParameter("phone"),"");		
		String personIds=StringHelper.obj2str(request.getParameter("personIds"),"");	
		String personNames=StringHelper.obj2str(request.getParameter("personNames"),"");	
		String carIds=StringHelper.obj2str(request.getParameter("carIds"),"");	
		String carNums=StringHelper.obj2str(request.getParameter("carNums"),"");	
		String time = StringUtil.getCurrDateTime("HH:mm");

//		判断该警情是否正被调度
		String attempersql="select attemper from T_Attemper_Alarm where alarmid='"+alarmId+"'";
		String dbattemper=StringHelper.obj2str(DBHandler.getSingleResult(attempersql),"");
		if(!dbattemper.equals(attemper)){
			out.write("null");
			out.close();
			return null;
		}
		String desc=time+" "+person+"("+phone+") 确认已处结";
		String mainsql="update T_Attemper_Alarm set EventLevel='"+eventLevel+"',EventState='"+state+"'," +
				"TRAFFICREVERTTIME=to_date('"+traffTime+"','YYYY-MM-DD HH24:mi:ss'),ATTEMPER='"+attemper+"',WEATHER='"+weatherinfo+"',ROADTYPE='"+disposeRoadType+"',DISPOSERESULT='"+disposeResult+"',EVENTTHINTYPE='"+disposeThinType+"',ATTEMPERID='"+attemperid+"' " +
				" where alarmid='"+alarmId+"'";
		DBHandler.execute(mainsql);
		String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(infosql);
		String alldesc=StringHelper.obj2str(res[0][0],"")+desc;
		String backsql="update T_Attemper_Feedback set TRAFFICREVERTTIME=to_date('"+traffTime+"','YYYY-MM-DD HH24:mi:ss'),FEEDBACKUNIT='"+deptId+"'," +
						" FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
		DBHandler.execute(backsql);
		String sourceSql="select EVENTSOURCE from T_ATTEMPER_ALARM where alarmid='"+alarmId+"'";
		String eventsource=StringHelper.obj2str(DBHandler.getSingleResult(sourceSql), "");
		String[] pids=personIds.split(",");
		if(pids!=null){
			for(int i=0;i<pids.length;i++){
				String dutypersonsql="update T_ATTEMPER_DUTY set state='0' where policeid='"+pids[i]+"'";
				DBHandler.execute(dutypersonsql);
				String personstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+state+"',REMOVETIME=to_date('"+traffTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and POLICEID='"+pids[i]+"'";
				DBHandler.execute(personstatesql);
			}
		}
		
		String[] cids=carIds.split(",");
		if(cids!=null){
			for(int i=0;i<cids.length;i++){
				String dutycarsql="update T_ATTEMPER_DUTY set state='0' where carcode='"+cids[i]+"'";
				DBHandler.execute(dutycarsql);
				String carstatesql="update T_ATTEMPER_POLICEHISTORY set STATE='"+state+"',REMOVETIME=to_date('"+traffTime+"','YYYY-MM-DD HH24:mi:ss') where alarmid='"+alarmId+"' and CARCODE='"+cids[i]+"'";
				DBHandler.execute(carstatesql);
			}
		}
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");
        xmlData.append("<col>\n");
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        out.write(xmlData.toString());
        out.close();
//        FeedBackMgr.SendDisposeMsg(alarmId,"","",deptId,state,traffTime);
        FeedBackMgr.SendDisposeMsg(alarmId,"",deptId,"",state,traffTime,attemper,"","","","","");
        //三台消息
        if(eventsource.equals("002001")){
        	FeedBackMgr.SendFeedBackMsg(alarmId,"0");
        }	
		return null;
	}	
	
	//退出调度
	public ActionForward doExitPolice(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	
		PrintWriter out = response.getWriter();
		String exitTime = StringUtil.getCurrDateTime("yyyy-MM-dd HH:mm:ss");
		String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),"");
		String person=StringHelper.obj2str(request.getParameter("person"),"");		
		String phone=StringHelper.obj2str(request.getParameter("phone"),"");	
		String deptId=StringHelper.obj2str(request.getParameter("deptId"),"");	
		String time = StringUtil.getCurrDateTime("HH:mm");
		String state="004012";
		String desc=time+" "+person+"("+phone+") 退出调度"+"*NextRow*";;
		String infosql="select feedbackdesc from T_Attemper_Feedback where alarmid='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(infosql);
		String alldesc=StringHelper.obj2str(res[0][0],"")+desc;
		String sql="select EventState,eventsource,eventtype,title,alarmtime from t_attemper_alarm where alarmid='"+alarmId+"'";
		Object[] alarmres=DBHandler.getLineResult(sql);
		String dbstate=StringHelper.obj2str(alarmres[0],"");
		if(dbstate.equals("004021")){
			String modifysql="update t_attemper_alarm set EventState='"+state+"',attemper='',attemperid='' where alarmid='"+alarmId+"'";
			DBHandler.execute(modifysql);
		}else{
			state=dbstate;
			String modifysql="update t_attemper_alarm set attemper='',attemperid='' where alarmid='"+alarmId+"'";
			DBHandler.execute(modifysql);
		}
		String backsql="update T_Attemper_Feedback set FEEDBACKUNIT='"+deptId+"', FeedbackDesc='"+alldesc+"' where alarmid='"+alarmId+"'";
		DBHandler.execute(backsql);
		StringBuffer xmlData = new StringBuffer("");
		xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        xmlData.append("<rfXML>\n");
        xmlData.append("<RFWin>\n");
        xmlData.append("<col>\n");
        xmlData.append(time);	
        xmlData.append("</col>\n");	
        xmlData.append("<col>\n");	
        xmlData.append(alldesc);	
        xmlData.append("</col>\n");	
        xmlData.append("</RFWin>\n");
        xmlData.append("</rfXML>\n");
        out.write(xmlData.toString());
        out.close();
        //alarmId,eventsource ,eventtype,eventunit,state,time,attemper,title,eventlevel,isquit,persons,cars) {
//	    FeedBackMgr.SendDisposeMsg(alarmId,"","",deptId,state,traffTime);
        FeedBackMgr.SendDisposeMsg(alarmId,StringHelper.obj2str(alarmres[1],""),StringHelper.obj2str(alarmres[2],""),deptId,state,exitTime,"",StringHelper.obj2str(alarmres[3],""),"","Y","","");
    	return null;
	}	
	
//	事故组人员
	public ActionForward doGetAccidentPerson(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		 	request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("text/xml; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        String result = null;
	        String deptcode = StringHelper.obj2str(request.getParameter("deptId"), "");
	        String sql = "select distinct jgid,jgmc,jglx,jgccbm  from t_sys_department where jgid = '" + deptcode + "'";
	        Object[] depResult = DBHandler.getLineResult(sql);
	        String deptidStr = "";
	        if (depResult != null)
	        {
	            String jgccsql = "select distinct jgid,jgmc,jglx,jgccbm  from t_sys_department where ";
	            jgccsql += " jgccbm like '" + depResult[3] + "%' and jgccbm != '" + depResult[3] + "'";
	            Object[][] zddeptResult = DBHandler.getMultiResult(jgccsql);
	            if (zddeptResult != null)
	            {
	                for (int i = 0; i < zddeptResult.length; i++)
	                {
	                    if (i == 0)
	                    {
	                        deptidStr += StringHelper.obj2str(zddeptResult[i][0], ""); ;
	                    }
	                    else
	                    {
	                        deptidStr += "," + StringHelper.obj2str(zddeptResult[i][0], ""); ;
	                    }
	                }
	            }
	        }
	        if (!deptidStr.equals(""))
	        {
	            result = getDutyPlanXML(deptidStr);
	        }
	        System.out.println("----------------\n" + result);
	        out.write(result);
	        out.close();
	        return null;
	}		
	
    public String getDutyPlanXML(String deptcode)
    {
        Calendar day = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        day.add(Calendar.DATE, -1);
        String lastdate = sdf.format(day.getTime());//昨天时间
        Object[][] areaResult = null;
        Object[][] personResult = null;
        StringBuffer xmlData = new StringBuffer("");
        	
        String datasql = null;
        try
        {
        	xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
	        xmlData.append("<rfXML>\n");
	        xmlData.append("<RFWin>\n");	
            //查相应单位下当前执勤岗
            datasql = " SELECT areaid,SUBSTR(MAX(SYS_CONNECT_BY_PATH(timeid, ',')), 2) timeID ";
            datasql += " FROM (SELECT areaid ,timeid ,rn,LEAD(rn) OVER(PARTITION BY areaid ORDER BY rn) rn1 ";
            datasql += " FROM (SELECT areaid, timeid,ROW_NUMBER() OVER(ORDER BY timeid) rn FROM ( ";

            datasql += " select distinct tpt.areaid,(select AreaName from t_duty_area where areaid=tpt.areaid) area,tpt.timeid,tpt.dutyclass from t_duty_area_baseinfo tdb,t_duty_area_plan_time tpt ";
            datasql += " where tdb.dutyid=tpt.dutyid ";
            datasql += " and tdb.deptid in(" + deptcode + ") and tpt.areaid is not null and tpt.dutyclass in(5,6,7,8,9)";

            datasql += " and ((tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD')";
            datasql += " and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime>=to_char(sysdate,'hh24:mi'))";
            datasql += " or (tpt.endtime>=to_char(sysdate,'hh24:mi') and tdb.DutyDate = to_date('" + lastdate + "', 'YYYY-MM-DD') and tpt.starttime>=to_char(sysdate,'hh24:mi') and tpt.starttime >= tpt.endtime)";
            datasql += " or (tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD') and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime <= to_char(sysdate,'hh24:mi') and tpt.starttime>=tpt.endtime))order by tpt.areaid";

            datasql += " )";
            datasql += " where 1=1";
            datasql += " ))";
            datasql += " START WITH rn1 IS NULL CONNECT BY rn1 = PRIOR rn GROUP BY areaid ";
            areaResult = DBHandler.getMultiResult(datasql);
            if (areaResult != null && areaResult.length > 0)
            {
                for (int j = 0; j < areaResult.length; j++)
                {
                    String areaStr = StringHelper.obj2str(areaResult[j][0], "");
                    String dutysql = null;
                    Object[][] dutyPlanResult = null;
                    //查当前单位事故值班班次
                    dutysql = " SELECT dutyclass,dutyclassMs,SUBSTR(MAX(SYS_CONNECT_BY_PATH(timeid, ',')), 2) timeID  ";
                    dutysql += " FROM (SELECT dutyclass ,dutyclassMs,timeid ,rn,LEAD(rn) OVER(PARTITION BY dutyclass ORDER BY rn) rn1 ";
                    dutysql += " FROM (SELECT dutyclass,dutyclassMs, timeid,ROW_NUMBER() OVER(ORDER BY timeid) rn FROM ( ";

                    dutysql += " select distinct tpt.dutyclass,F_GET_NAME('603008',tpt.dutyclass)dutyclassMs,tpt.timeid from t_duty_area_baseinfo tdb,t_duty_area_plan_time tpt ";
                    dutysql += " where tdb.dutyid=tpt.dutyid ";
                    dutysql += " and tpt.areaid ='" + areaStr + "'";
                    dutysql += "  and timeid in(" + areaResult[j][1] + ")";

                    //dutysql += " and ((tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD')"; 
                    //dutysql += " and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime>=to_char(sysdate,'hh24:mi'))"; 		
                    //dutysql += " or (tpt.endtime>=to_char(sysdate,'hh24:mi') and tdb.DutyDate = to_date('"+lastdate+"', 'YYYY-MM-DD') and tpt.starttime>=to_char(sysdate,'hh24:mi') and tpt.starttime >= tpt.endtime)";
                    //dutysql += " or (tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD') and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime <= to_char(sysdate,'hh24:mi') and tpt.starttime>=tpt.endtime))"; 					

                    dutysql += " )";
                    dutysql += " where 1=1";
                    dutysql += " ))";
                    dutysql += " START WITH rn1 IS NULL CONNECT BY rn1 = PRIOR rn GROUP BY dutyclass,dutyclassMs order by dutyclass ";
                    //System.out.println(datasql);		
                    dutyPlanResult = DBHandler.getMultiResult(dutysql);
                    if (dutyPlanResult != null)
                    {
                        for (int k = 0; k < dutyPlanResult.length; k++)
                        {
                            xmlData.append("<dutypost id='" + k + "'>");
                            xmlData.append("<row>");
                            xmlData.append(dutyPlanResult[k][1].toString());//班次					
                            xmlData.append("</row>");
                            String dutypersql = "select distinct f_get_person(tdp.dutyperson),tdp.dutyperson from t_duty_area_plan tdp,t_duty_area_plan_time tpt where tpt.timeid=tdp.timeid";
                            dutypersql += "  and tdp.timeid in(" + dutyPlanResult[k][2] + ") and tdp.dutyperson is not null";

                            personResult = DBHandler.getMultiResult(dutypersql);
                            if (personResult != null && areaResult[j][0] != null)
                            {
                                xmlData.append("<row>");
                                for (int m = 0; m < personResult.length; m++)
                                {
                                	String busysql="select state from t_attemper_duty where policeid='"+personResult[m][1]+"'";
									Object[][] busyres=DBHandler.getMultiResult(busysql);
                                    if (m == personResult.length - 1)
                                    {
                                    	if(busyres!=null&&StringHelper.obj2str(busyres[0][0],"").equals("0")){		
                                    		xmlData.append(personResult[m][0] == null ? "" : personResult[m][0] + "|" + personResult[m][1]+ "|" +"0");//执勤人员
                                    	}else{
                                    		xmlData.append(personResult[m][0] == null ? "" : personResult[m][0] + "|" + personResult[m][1]+ "|" +"1");//执勤人员
                                    	}
                                    }
                                    else
                                    {
                                    	if(busyres!=null&&StringHelper.obj2str(busyres[0][0],"").equals("0")){		
	                                        xmlData.append(personResult[m][0] == null ? "" : personResult[m][0] + "|" + personResult[m][1]+ "|" +"0");
	                                        xmlData.append(",");
                                    	}else{
                                    		xmlData.append(personResult[m][0] == null ? "" : personResult[m][0] + "|" + personResult[m][1]+ "|" +"1");
	                                        xmlData.append(",");
                                    	}
                                    }
                                }
                                xmlData.append("</row>");
                            }
                            else
                            {
                                xmlData.append("<row>");
                                xmlData.append("</row>");
                            }
                            xmlData.append("</dutypost>");
                        }
                    }
                }
            }	            
            xmlData.append("</RFWin>\n");
            xmlData.append("</rfXML>\n");           
        }
        
        catch (Exception e)
        {
            //logger.error("[勤务管理]" + "当前勤务信息查询错误"+e.getMessage());
        }
        return xmlData.toString();
    }
    
    //勤务人员
    public ActionForward doGetDutyPerson(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
	       
    	String deptcode=StringHelper.obj2str(request.getParameter("deptId"),"");
    	
    	try{
    		StringBuffer xmlData = new StringBuffer("");
			xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
	        xmlData.append("<rfXML>\n");
	        xmlData.append("<RFWin>\n");	
	        Calendar day = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			day.add(Calendar.DATE,-1);
			String lastdate = sdf.format(day.getTime());//昨天时间			
			String sql = "select distinct jgid,jgmc,jglx,jgccbm from t_sys_department where jgid = '"+ deptcode+"'";
			Object[]depResult = DBHandler.getLineResult(sql);			
			if(depResult!=null){
				String jgccsql = "select distinct jgid,jgmc,jglx,jgccbm  from t_sys_department where ";
				jgccsql +=" jgccbm like '" + depResult[3] + "%' and jgccbm != '"+ depResult[3] +"' and jglx='3' and COMMANDORDER<>'0' order by jgccbm";
				Object[][] deptResult = DBHandler.getMultiResult(jgccsql);
				
//				当前执勤人员查询
				if(deptResult!=null){
					for (int i = 0; i < deptResult.length; i++) {
						xmlData.append("<dept>\n");	
						String jgid=deptResult[i][0].toString();
						String jgmc=deptResult[i][1].toString();
						xmlData.append("<row>\n");	
						xmlData.append(jgmc);										
						xmlData.append("</row>\n");	
						String personsql="select POLICENAME,POLICEID,STATE from T_ATTEMPER_DUTY where DEPARTMENTID='"+jgid+"'";
						Object[][] persons=DBHandler.getMultiResult(personsql);
						if(persons!=null){
							for(int n=0;n<persons.length;n++){															
								xmlData.append("<row>\n");	
								xmlData.append(StringHelper.obj2str(persons[n][0], "")+"|"+StringHelper.obj2str(persons[n][1], "")+"|"+StringHelper.obj2str(persons[n][2], "0"));										
								xmlData.append("</row>\n");									
							}
						}
						xmlData.append("</dept>\n");	
					}
				}
			}	
				
//				//勤务查询
//				if(deptResult!=null){
//					for (int i = 0; i < deptResult.length; i++) {
//						xmlData.append("<dept>\n");	
//						String jgid=deptResult[i][0].toString();
//						String jgmc=deptResult[i][1].toString();
//						xmlData.append("<row>\n");	
//						xmlData.append(jgmc);										
//						xmlData.append("</row>\n");	
//						String basesql="select dutyid from t_duty_area_baseinfo where deptid='"+jgid+"' and to_char(dutydate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')";
//						String dutyid=StringHelper.obj2str(DBHandler.getSingleResult(basesql),"");
//						String timesql="select timeid from t_duty_area_plan_time where dutyid='"+dutyid+"' and (starttime <= to_char(sysdate,'hh24:mi') and endtime>=to_char(sysdate,'hh24:mi'))";
//						Object[][] times=DBHandler.getMultiResult(timesql);
//						if(times!=null){
//							for(int j=0;j<times.length;j++){
//								String personsql="select f_get_person(dutyperson),dutyperson from t_duty_area_plan where timeid='"+times[j][0]+"'";
//								Object[][] persons=DBHandler.getMultiResult(personsql);
//								if(persons!=null){
//									for(int n=0;n<persons.length;n++){
//										String busysql="select state from t_attemper_duty where policeid='"+persons[n][1]+"'";
//										Object[][] busyres=DBHandler.getMultiResult(busysql);
//										if(busyres!=null&&StringHelper.obj2str(busyres[0][0],"").equals("1")){										
//											xmlData.append("<row>\n");	
//											xmlData.append(StringHelper.obj2str(persons[n][0], "")+"|"+StringHelper.obj2str(persons[n][1], "")+"|"+"1");										
//											xmlData.append("</row>\n");	
//										}else{
//											xmlData.append("<row>\n");	
//											xmlData.append(StringHelper.obj2str(persons[n][0], "")+"|"+StringHelper.obj2str(persons[n][1], "")+"|"+"0");										
//											xmlData.append("</row>\n");	
//										}
//									}
//								}
//							}
//						}
//						xmlData.append("</dept>\n");	
//					}
//				}
//			}
			
			//wyl勤务查询
//			if(!deptidStr.equals("")){
//				deptcode = deptidStr;
//			}
//			
//			Object[][] areaResult = null;
//			Object[][] personResult = null;
//			
//			String datasql = null;
//			
//			//查所有中队单位下当前执勤岗
//			datasql =" SELECT areaid,SUBSTR(MAX(SYS_CONNECT_BY_PATH(timeid, ',')), 2) timeID ";
//			datasql +=" FROM (SELECT areaid ,timeid ,rn,LEAD(rn) OVER(PARTITION BY areaid ORDER BY rn) rn1 ";
//			datasql +=" FROM (SELECT areaid, timeid,ROW_NUMBER() OVER(ORDER BY timeid) rn FROM ( ";
//			
//			datasql += " select distinct tpt.areaid,(select AreaName from t_duty_area where areaid=tpt.areaid) area,tpt.timeid from t_duty_area_baseinfo tdb,t_duty_area_plan_time tpt " ;
//			datasql += " where tdb.dutyid=tpt.dutyid ";
//			datasql += " and tdb.deptid in("+deptcode+") and tpt.areaid is not null ";	
//			
//			datasql += " and ((tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD')"; 
//			datasql += " and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime>=to_char(sysdate,'hh24:mi'))"; 		
//			datasql += " or (tpt.endtime>=to_char(sysdate,'hh24:mi') and tdb.DutyDate = to_date('"+lastdate+"', 'YYYY-MM-DD') and tpt.starttime>=to_char(sysdate,'hh24:mi') and tpt.starttime >= tpt.endtime)";
//			datasql += " or (tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD') and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime <= to_char(sysdate,'hh24:mi') and tpt.starttime>=tpt.endtime))order by tpt.areaid"; 					
//				
//			datasql +=" )";
//			datasql +=" where 1=1";
//			datasql +=" ))";
//			datasql +=" START WITH rn1 IS NULL CONNECT BY rn1 = PRIOR rn GROUP BY areaid ";
//			areaResult = DBHandler.getMultiResult(datasql);
//			if (areaResult != null&&areaResult.length>0) {					
//				for (int j = 0; j < areaResult.length; j++) {											
//					//String areaStr = StringHelper.obj2str(areaResult[j][0],"");
//					//查执勤人员
//					xmlData.append("<dutypost id='"+j+"'>");		
//					String dutypersql = "select distinct f_get_person(tdp.dutyperson),tdp.dutyperson from t_duty_area_plan tdp,t_duty_area_plan_time tpt where tpt.timeid=tdp.timeid";
//					dutypersql += "  and tdp.timeid in("+areaResult[j][1]+") and tdp.dutyperson is not null";	
//					personResult = DBHandler.getMultiResult(dutypersql);
//					if(personResult!=null&&areaResult[j][0]!=null) {
//					   xmlData.append("<row>");
//					   for(int m=0;m<personResult.length;m++){
//						   if(m==personResult.length-1) {
//							  xmlData.append(personResult[m][0]==null?"":personResult[m][0]+"|"+personResult[m][1]);//执勤人员
//							} else {
//							  xmlData.append(personResult[m][0]==null?"":personResult[m][0]+"|"+personResult[m][1]);
//							  xmlData.append(",");
//							}
//						}
//					   xmlData.append("</row>");
//					}else{
//					   xmlData.append("<row>");
//					   xmlData.append("</row>");
//					}						
//					xmlData.append("</dutypost>");          
//	
//			   }					   		
//			} 
		   xmlData.append("</RFWin>\n");
           xmlData.append("</rfXML>\n");
           System.out.println("----------------\n"+xmlData.toString());
           out.write(xmlData.toString());
           out.close();
		} catch (Exception e) {
			//logger.error("[勤务管理]" + "当前勤务信息查询错误"+e.getMessage());
		}
		return null;
	}
	
//勤务车辆
  public ActionForward doGetDutyCar(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
	       
    	String deptcode=StringHelper.obj2str(request.getParameter("deptId"),"");
		
		String sql = "select distinct jgid,jgmc,jglx,jgccbm  from t_sys_department where jgid = '"+ deptcode+"'";
		Object[]depResult = DBHandler.getLineResult(sql);
		String deptidStr="";
		if(depResult!=null){
			String jgccsql = "select distinct jgid,jgmc,jglx,jgccbm  from t_sys_department where ";
			jgccsql +=" jgccbm like '" + depResult[3] + "%' and jgccbm != '"+ depResult[3] +"'";
			Object[][]zddeptResult = DBHandler.getMultiResult(jgccsql);
			if(zddeptResult!=null){
				for (int i = 0; i < zddeptResult.length; i++) {
					if(i==0){
						deptidStr += StringHelper.obj2str(zddeptResult[i][0],"");;
					}else{
						deptidStr += ","+StringHelper.obj2str(zddeptResult[i][0],"");;
					}
				}
			}
		}
		if(!deptidStr.equals("")){
			deptcode = deptidStr;
		}
		Calendar day = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		day.add(Calendar.DATE,-1);
		String lastdate = sdf.format(day.getTime());//昨天时间
		Object[][] areaResult = null;
		Object[][] carResult = null;
		StringBuffer xmlData = new StringBuffer("");
		String datasql = null;
		try{
			xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
	        xmlData.append("<rfXML>\n");
	        xmlData.append("<RFWin>\n");	
			//查所有中队单位下当前执勤岗
			datasql =" SELECT areaid,SUBSTR(MAX(SYS_CONNECT_BY_PATH(timeid, ',')), 2) timeID ";
			datasql +=" FROM (SELECT areaid ,timeid ,rn,LEAD(rn) OVER(PARTITION BY areaid ORDER BY rn) rn1 ";
			datasql +=" FROM (SELECT areaid, timeid,ROW_NUMBER() OVER(ORDER BY timeid) rn FROM ( ";
			
			datasql += " select distinct tpt.areaid,(select AreaName from t_duty_area where areaid=tpt.areaid) area,tpt.timeid from t_duty_area_baseinfo tdb,t_duty_area_plan_time tpt " ;
			datasql += " where tdb.dutyid=tpt.dutyid ";
			datasql += " and tdb.deptid in("+deptcode+") and tpt.areaid is not null ";	
			
			datasql += " and ((tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD')"; 
			datasql += " and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime>=to_char(sysdate,'hh24:mi'))"; 		
			datasql += " or (tpt.endtime>=to_char(sysdate,'hh24:mi') and tdb.DutyDate = to_date('"+lastdate+"', 'YYYY-MM-DD') and tpt.starttime>=to_char(sysdate,'hh24:mi') and tpt.starttime >= tpt.endtime)";
			datasql += " or (tdb.DutyDate =to_date(to_char(sysdate, 'YYYY-MM-DD'), 'YYYY-MM-DD') and tpt.starttime <= to_char(sysdate,'hh24:mi') and tpt.endtime <= to_char(sysdate,'hh24:mi') and tpt.starttime>=tpt.endtime))order by tpt.areaid"; 					
				
			datasql +=" )";
			datasql +=" where 1=1";
			datasql +=" ))";
			datasql +=" START WITH rn1 IS NULL CONNECT BY rn1 = PRIOR rn GROUP BY areaid ";
			areaResult = DBHandler.getMultiResult(datasql);
			if (areaResult != null&&areaResult.length>0) {					
				for (int j = 0; j < areaResult.length; j++) {											
					//String areaStr = StringHelper.obj2str(areaResult[j][0],"");
					//查执勤车辆
					xmlData.append("<dutypost id='"+j+"'>");		
					String dutyvehsql = "select distinct f_get_vehicle(tdv.dutyvehicle),tdv.dutyvehicle from t_duty_area_vehicle tdv,t_duty_area_plan_time tpt where tpt.timeid=tdv.timeid";				
					dutyvehsql +=" and tdv.timeid in("+areaResult[j][1]+") and tdv.dutyvehicle is not null";
					carResult = DBHandler.getMultiResult(dutyvehsql);
					if(carResult!=null&&areaResult[j][0]!=null) {
					   xmlData.append("<row>");
					   for(int m=0;m<carResult.length;m++){
						   String carstatesql="select state from t_attemper_duty where carcode='"+carResult[m][1]+"'";
						   Object[][] res=DBHandler.getMultiResult(carstatesql);
						   String state="0";
						   if(res!=null){
							   state=StringHelper.obj2str(res[0][0],"0");//状态，默认空闲
						   }
						   if(m==carResult.length-1) {
							  xmlData.append(carResult[m][0]==null?"":carResult[m][0]+"|"+carResult[m][1]+"|"+state);//执勤车辆
							} else {
							  xmlData.append(carResult[m][0]==null?"":carResult[m][0]+"|"+carResult[m][1]+"|"+state);
							  xmlData.append(",");
							}
						}
					   xmlData.append("</row>");
					}else{
					   xmlData.append("<row>");
					   xmlData.append("</row>");
					}						
					xmlData.append("</dutypost>");      	
			   }					   		
			} 
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
			System.out.println("----------------\n"+xmlData.toString());
			out.write( xmlData.toString());
			out.close();
		} catch (Exception e) {
			//logger.error("[勤务管理]" + "当前勤务信息查询错误"+e.getMessage());
		}
		return null;
	}	
  
  //得到PCS人员
  public ActionForward doGetPcsPerson(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/xml; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	       
	    String deptcode=StringHelper.obj2str(request.getParameter("deptId"),"");
    	
    	try{
    		StringBuffer xmlData = new StringBuffer("");
			xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
	        xmlData.append("<rfXML>\n");
	        xmlData.append("<RFWin>\n");	
	        Calendar day = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			day.add(Calendar.DATE,-1);
			String lastdate = sdf.format(day.getTime());//昨天时间			
			String sql = "select distinct jgid,jgmc,jglx,jgccbm from t_sys_department where jgid = '"+ deptcode+"'";
			Object[]depResult = DBHandler.getLineResult(sql);			
			if(depResult!=null){
				String jgccsql = "select distinct jgid,jgmc,jglx,jgccbm  from t_sys_department where ";
				jgccsql +=" jgccbm like '" + depResult[3] + "%' and jgccbm != '"+ depResult[3] +"' and jglx='3' order by jgccbm";
				Object[][] deptResult = DBHandler.getMultiResult(jgccsql);
//				xmlData.append("<row>\n");	
//				xmlData.append(StringHelper.obj2str(persons[n][0], "")+"|"+StringHelper.obj2str(persons[n][1], "")+"|"+"1");										
//				xmlData.append("</row>\n");	
				
				//勤务查询
				if(deptResult!=null){
					for (int i = 0; i < deptResult.length; i++) {
						xmlData.append("<dept>\n");	
						String jgid=deptResult[i][0].toString();
						String jgmc=deptResult[i][1].toString();
						xmlData.append("<row>\n");	
						xmlData.append(jgmc);										
						xmlData.append("</row>\n");	
						String personsql="select xm,ryid from t_sys_person where ssjg='"+jgid+"'";
						Object[][] personres=DBHandler.getMultiResult(personsql);
						if(personres!=null){
							for(int n=0;n<personres.length;n++){
								xmlData.append("<row>\n");	
								xmlData.append(StringHelper.obj2str(personres[n][0], "")+"|"+StringHelper.obj2str(personres[n][1], ""));										
								xmlData.append("</row>\n");	
							}
						}
						xmlData.append("</dept>\n");	
					}
				}
			}
			
			
		   xmlData.append("</RFWin>\n");
           xmlData.append("</rfXML>\n");
           System.out.println("----------------\n"+xmlData.toString());
           out.write(xmlData.toString());
           out.close();
		} catch (Exception e) {
			//logger.error("[勤务管理]" + "当前勤务信息查询错误"+e.getMessage());
		}
		return null;
  	
  }
}
