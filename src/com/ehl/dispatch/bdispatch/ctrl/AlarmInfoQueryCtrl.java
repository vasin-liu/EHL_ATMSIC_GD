package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;

public class AlarmInfoQueryCtrl extends Controller
{
	/**
	 * 查询警情详细信息
	 * 	 * 
	 */
	public ActionForward doQueryAlarmInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");	
		String alarmType=StringHelper.obj2str(request.getParameter("alarmType"),"");
				
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sbXml.append("<rfXML>\n");
		sbXml.append("<RFWin>\n");
		Object[][] res=this.getAlarmInfo(alarmId);
		if(res!=null){			
			sbXml.append("<row id='0'>\n");
			for (int j = 0; j < res[0].length; j++) {
				sbXml.append("<col>");
				sbXml.append(StringHelper.obj2str(res[0][j],""));
				sbXml.append("</col>\n");
			}		
			//如果是事故
			if(alarmType.equals("001001")){
				sbXml.append(getAccidentInfo(alarmId));
			}
			sbXml.append("</row>\n");
		}else{
			Object[][] base=this.getAlarmBaseInfo(alarmId);
			sbXml.append("<row id='0'>\n");
			for (int j = 0; j < base[0].length; j++) {
				sbXml.append("<col>");
				sbXml.append(StringHelper.obj2str(base[0][j],""));
				sbXml.append("</col>\n");
			}		
			//如果是事故
			if(alarmType.equals("001001")){
				sbXml.append(getAccidentInfo(alarmId));
			}
			sbXml.append("</row>\n");
		}
		sbXml.append("</RFWin>\n");
		sbXml.append("</rfXML>\n");
		String xml=sbXml.toString();
		out.write(xml);		
		out.close();
		return null;
	}
	
	//得到警情基本信息
	private Object[][] getAlarmBaseInfo(String alarmId){
		String sql="select t.alarmid,t.title,t.alarmregion,to_char(t.AlarmTime,'yyyy-mm-dd hh24:mi'),t.alarmaddress," +
//		"t.eventsource,t.eventtype,t.eventthintype,t.eventlevel,t.roadtype,t.weather," +
		getSql("t.eventsource")+","+getSql("t.eventtype")+","+getSql("t.eventthintype")+","+getSql("t.eventlevel")+","+getSql("t.roadtype")+","+getSql("t.weather")+"," +
		"'','',"+getSql("t.disposeresult")+",t.alarmdesc,t.attemper,t.comeoutperson,t.comeoutcar, " +
		"to_char(t.assigntime,'yyyy-mm-dd hh24:mi'),to_char(t.comeouttime,'yyyy-mm-dd hh24:mi'),to_char(t.comeoutarrivetime,'yyyy-mm-dd hh24:mi')," +
		"'',to_char(t.finishtime,'yyyy-mm-dd hh24:mi'),to_char(t.trafficreverttime,'yyyy-mm-dd hh24:mi')," +
		"t.disposeperson,'' from t_attemper_alarm t" +
		" where t.alarmid='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(sql);		
		return res;
	}
	
	//得到警情详细信息
	private Object[][] getAlarmInfo(String alarmId){
		String sql="select t.alarmid,t.title,t.alarmregion,to_char(t.AlarmTime,'yyyy-mm-dd hh24:mi'),t.alarmaddress," +
//					"t.eventsource,t.eventtype,t.eventthintype,t.eventlevel,t.roadtype,t.weather," +
					getSql("t.eventsource")+","+getSql("t.eventtype")+","+getSql("t.eventthintype")+","+getSql("t.eventlevel")+","+getSql("t.roadtype")+","+getSql("t.weather")+"," +
					"f.FLESHWOUNDPERSONCOUNT,f.DEATHPERSONCOUNT,"+getSql("t.disposeresult")+",t.alarmdesc,t.attemper,t.comeoutperson,t.comeoutcar, " +
					"to_char(t.assigntime,'yyyy-mm-dd hh24:mi'),to_char(t.comeouttime,'yyyy-mm-dd hh24:mi'),to_char(t.comeoutarrivetime,'yyyy-mm-dd hh24:mi')," +
					"to_char(f.feedbacktime,'yyyy-mm-dd hh24:mi'),to_char(t.finishtime,'yyyy-mm-dd hh24:mi'),to_char(t.trafficreverttime,'yyyy-mm-dd hh24:mi')," +
					"t.disposeperson,f.FEEDBACKDESC from t_attemper_alarm t,T_ATTEMPER_FEEDBACK f " +
					" where t.alarmid='"+alarmId+"' and t.alarmid=f.alarmid";
		Object[][] res=DBHandler.getMultiResult(sql);		
		return res;
	}
	
	//在字典表中查询描述
	private String getSql(String colsql){
		String sql="(select name from t_attemper_code where id="+colsql+")";	
		return sql;
	}
	//事故信息
	private String getAccidentInfo(String alarmId){
		String sql="select ALARMPERSON,ALARMPHONE from T_ATTEMPER_ACCIDENT where alarmid='"+alarmId+"'";
		Object[][] res=DBHandler.getMultiResult(sql);
		StringBuffer sbXml = new StringBuffer();
		if(res!=null){
			for (int j = 0; j < res[0].length; j++) {
				sbXml.append("<col>");
				sbXml.append(StringHelper.obj2str(res[0][j],""));
				sbXml.append("</col>\n");
			}	
		}else{
			sbXml.append("<col>");
			sbXml.append("</col>\n");
			sbXml.append("<col>");
			sbXml.append("</col>\n");
		}
		return sbXml.toString();
	}
}
