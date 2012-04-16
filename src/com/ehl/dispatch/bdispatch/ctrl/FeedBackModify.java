package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.business.FeedBackMgr;
import com.ehl.dispatch.bdispatch.util.PoliceTime;
import com.ehl.sm.common.util.StringUtil;

/**
 * @类型说明:事件反馈业务处理类
 * @创建者：ldq
 * @创建日期 2009-04-14
 */
public class FeedBackModify extends Controller {
	
	public static final String CJ = "004006"; //已派警
	public static final String DDXC = "004014";//派警到现场
	public static final String CLWB = "004013";//处理完毕
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：交通事故事件业务处理类
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doEditForAccident (Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"), "");
		if(ALARMID.equals("")){
			out.write("反馈出错，请重试!");
			return null;
		}
		
		String time = StringHelper.obj2str(request.getParameter("time"), "");
		
		String eventType = StringHelper.obj2str(request.getParameter("eventType"), "");
		String FEEDBACKID = StringHelper.obj2str(request.getParameter("FEEDBACKID"), "");
		String fkdw = StringHelper.obj2str(request.getParameter("fkdw"), "");
		String fkr = StringHelper.obj2str(request.getParameter("fkr"), "");
		String cljg = StringHelper.obj2str(request.getParameter("cljg"), "");
		String cjsj = StringHelper.obj2str(request.getParameter("cjsj"), "");
		
		
		String ajfssj = StringHelper.obj2str(request.getParameter("ajfssj"), "");
		String ajjssj = StringHelper.obj2str(request.getParameter("ajjssj"), "");
		String ajlx = StringHelper.obj2str(request.getParameter("ajlx"), "");
		
		String alarmState = StringHelper.obj2str(request.getParameter("alarmState"), "");
		String sgdj = StringHelper.obj2str(request.getParameter("sgdj"), "");
		String cdcl = StringHelper.obj2str(request.getParameter("cdcl"), "");
		String cjrs = StringHelper.obj2str(request.getParameter("cjrs"), "");
		String cjdw = StringHelper.obj2str(request.getParameter("cjdw"), "");
		String cjr = StringHelper.obj2str(request.getParameter("cjr"), "");
		
		String djsj = StringHelper.obj2str(request.getParameter("djsj"), "");
//		String jdhb = StringHelper.obj2str(request.getParameter("jdhb"), "");
		String sjly = StringHelper.obj2str(request.getParameter("sjly"), "");
//		事件预处理状态
		String EVENTSTATE = StringHelper.obj2str(request.getParameter("EVENTSTATE"), "");
//		事件当前状态
		String EVENTSTATE_NOW = StringHelper.obj2str(request.getParameter("EVENTSTATE_NOW"), "");
		
		String feedBackSql = "";
		
		/* 反馈表新加字段*/
		String jjss = StringHelper.obj2str(request.getParameter("jjss"), "");
		String qsrs = StringHelper.obj2str(request.getParameter("qsrs"), "");
		String zsrs = StringHelper.obj2str(request.getParameter("zsrs"), "");
		String swrs = StringHelper.obj2str(request.getParameter("swrs"), "");
		String sars = StringHelper.obj2str(request.getParameter("sars"), "");
		String zhrs = StringHelper.obj2str(request.getParameter("zhrs"), "");
		String jzrs = StringHelper.obj2str(request.getParameter("jzrs"), "");
		/* 是否破获刑事案件*/
		String phxsaj = StringHelper.obj2str(request.getParameter("phxsaj"), "");
		/*是否查处治安案件 */
		String cczaaj = StringHelper.obj2str(request.getParameter("cczaaj"), "");
		/* 是否解决纠纷*/
		String jjjf = StringHelper.obj2str(request.getParameter("jjjf"), "");
		/* 是否反馈终结*/
		String fkzj = StringHelper.obj2str(request.getParameter("fkzj"), "");
		/*影响道路*/
		String yxdl = StringHelper.obj2str(request.getParameter("yxdl"), "");
		/*波及范围*/
		String bjfw = StringHelper.obj2str(request.getParameter("bjfw"), "");
		/*故障车单位*/
		String gzcdw= StringHelper.obj2str(request.getParameter("gzcdw"), "");
		/*司机姓名*/
		String sjxm = StringHelper.obj2str(request.getParameter("sjxm"), "");
		/*路面状况*/
		String lmqk = StringHelper.obj2str(request.getParameter("lmqk"), "");
		
//		//-->大型故障车信息
//		String sjxm_Exception = StringHelper.obj2str(request.getParameter("sjxm_Exception"), "");
//		String gzcdw_Exception = StringHelper.obj2str(request.getParameter("gzcdw_Exception"), "");
//	  //String alarmState_Exception = StringHelper.obj2str(request.getParameter("alarmState_Exception"), "");
//		//大型故障车信息<--
//		
//		//-->灾害天气
//		String yxdl_Weather = StringHelper.obj2str(request.getParameter("yxdl_Weather"), "");
//		String lmqk_Weather = StringHelper.obj2str(request.getParameter("lmqk_Weather"), "");
//		//灾害天气<--
//		
//		//-->治安事件
//		String yxdl_PoliceEvent = StringHelper.obj2str(request.getParameter("yxdl_PoliceEvent"), "");
//		String bjfw_PoliceEvent = StringHelper.obj2str(request.getParameter("bjfw_PoliceEvent"), "");
//		//治安事件<--
//		
//		//--->嫌疑车辆
//		String jzrs_BlackList =  StringHelper.obj2str(request.getParameter("jzrs_BlackList"), "");
//		//嫌疑车辆<---
//		
//		//-->地质灾害
//		String yxdl_GeoLogicDisaster = StringHelper.obj2str(request.getParameter("yxdl_GeoLogicDisaster"), "");
//		String bjfw_GeoLogicDisaster = StringHelper.obj2str(request.getParameter("bjfw_GeoLogicDisaster"), "");
//		//地质灾害<--
//		
//		//-->市政事件
//		String yxdl_TownPlan = StringHelper.obj2str(request.getParameter("yxdl_TownPlan"), "");
//		String bjfw_TownPlan = StringHelper.obj2str(request.getParameter("bjfw_TownPlan"), "");
		//市政事件<--
		
		//-->火灾爆炸
//		String jjss_FireAndBlast = StringHelper.obj2str(request.getParameter("jjss_FireAndBlast"), "");
//		String qsrs_FireAndBlast = StringHelper.obj2str(request.getParameter("qsrs_FireAndBlast"), "");
//		String zsrs_FireAndBlast = StringHelper.obj2str(request.getParameter("zsrs_FireAndBlast"), "");
//		String swrs_FireAndBlast = StringHelper.obj2str(request.getParameter("swrs_FireAndBlast"), "");
//		String yxdl_FireAndBlast = StringHelper.obj2str(request.getParameter("yxdl_FireAndBlast"), "");
//		
		//更新反馈表
		if(!EVENTSTATE.equals(CLWB)){
			if(FEEDBACKID.equals("")){
				FEEDBACKID=StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
				feedBackSql = " INSERT INTO T_ATTEMPER_FEEDBACK (FEEDBACKID,ALARMID,FEEDBACKTIME,FEEDBACKUNIT,FEEDBACKPERSON,FEEDBACKDESC," +
				" COMEOUTUNIT,COMEOUTPERSON,COMEOUTTIME,COMEOUTARRIVETIME,COMEOUTCAR,COMEOUTCOUNT,STATE," 
				+ "ECONOMYLOSS,FLESHWOUNDPERSONCOUNT,GBHPERSONCOUNT,DEATHPERSONCOUNT,GRAPPLEPERSONCOUNT,DEALWITHPERSONCOUNT,SALVATIONPERSONCOUNT,"
				+ " ISRESOLVEDISSENSION,ISCHECKORDERCASE,ISFEEDBACKEND,ISUNCOVERCRIMINALCASE,AFFECTROAD,AFFECTEXTENT,EXCEPTIONCARUNIT,DRIVERNAME,ROADSITUATION,FINISHTIME)"
				+ " VALUES ('" + FEEDBACKID + "','"+ ALARMID + "',TO_DATE('" + time +"','YYYY-MM-DD HH24:MI'),'"+ fkdw +"','" + fkr +"','" 
				+ cljg +"','" + cjdw +"','"+ cjr + "',TO_DATE('" + cjsj +"','YYYY-MM-DD HH24:MI'),TO_DATE('" + djsj +"','YYYY-MM-DD HH24:MI'),'"
				+ cdcl +"','" + cjrs +"','" + EVENTSTATE +"',"
				+ "'"+jjss+"','"+qsrs+"','"+zsrs+"','"+swrs+"','"+sars+"','"+zhrs+"','"+jzrs+"','"+jjjf+"','"+cczaaj+"','"+fkzj+"',"
				+"'"+phxsaj+"','"+yxdl+"','"+bjfw+"','"+gzcdw+"','"+sjxm+"','"+lmqk+"',TO_DATE('" + ajjssj +"','YYYY-MM-DD HH24:MI'))";
				
			}else{
				feedBackSql = "UPDATE T_ATTEMPER_FEEDBACK SET FEEDBACKTIME=TO_DATE('" + time +"','YYYY-MM-DD HH24:MI'),FEEDBACKUNIT='"+ fkdw +"'," +
						" FEEDBACKPERSON='" + fkr +"',FEEDBACKDESC='" + cljg +"',COMEOUTUNIT='"+ cjdw +"',COMEOUTPERSON='"+ cjr + "'," +
						" COMEOUTTIME=TO_DATE('" + cjsj +"','YYYY-MM-DD HH24:MI'),COMEOUTARRIVETIME=TO_DATE('" + djsj +"','YYYY-MM-DD HH24:MI')," +
						" COMEOUTCAR='"+ cdcl +"',COMEOUTCOUNT='" + cjrs +"',STATE='" + EVENTSTATE +"'," +
						" ECONOMYLOSS='"+jjss+"',FLESHWOUNDPERSONCOUNT='"+qsrs+"',GBHPERSONCOUNT='"+zsrs+"',DEATHPERSONCOUNT='"+swrs+"',"+
						" GRAPPLEPERSONCOUNT='"+sars+"',DEALWITHPERSONCOUNT='"+zhrs+"',SALVATIONPERSONCOUNT='"+jzrs+"',ISRESOLVEDISSENSION='"+jjjf+"',"+
						" ISCHECKORDERCASE='"+cczaaj+"',ISFEEDBACKEND='"+fkzj+"',ISUNCOVERCRIMINALCASE='"+phxsaj+"',AFFECTROAD='"+yxdl+"',"+
						" AFFECTEXTENT='"+bjfw+"',EXCEPTIONCARUNIT='"+gzcdw+"',DRIVERNAME='"+sjxm+"',ROADSITUATION='"+lmqk+"',"+
						" FINISHTIME=TO_DATE('" + ajjssj +"','YYYY-MM-DD HH24:MI') WHERE FEEDBACKID='" + FEEDBACKID + "'";
			}			
		}else{
			
		}
		System.out.println("feedBackSql="+feedBackSql);
		String alarmSql = "";
		String complateSql = "";
		if(EVENTSTATE.equals(CLWB)){
			//修改反馈表该事件所有反馈状态为处理完毕
			complateSql = "UPDATE T_ATTEMPER_FEEDBACK SET STATE='" + EVENTSTATE +"' WHERE ALARMID='" + ALARMID + "'";
//			如果处理完毕,汇总所有反馈的出动车辆,处警人数等写入报警主表
			String strCdcl = "",strCjrs = "",strCjr = "";
			String getFeedBackSql = "SELECT T1.COMEOUTPERSON,T1.COMEOUTCAR," +
					" (SELECT SUM(T2.COMEOUTCOUNT) FROM T_ATTEMPER_FEEDBACK T2  WHERE T2.ALARMID='" + ALARMID + "') " +
					" FROM T_ATTEMPER_FEEDBACK T1 WHERE T1.ALARMID='" + ALARMID + "'";
			Object[][] feedback = DBHandler.getMultiResult(getFeedBackSql);
			
			if(feedback != null){
				strCjrs = StringHelper.obj2str(feedback[0][2], "");
				for(int i=0;i<feedback.length;i++){
					if(feedback[i][0] != null){
						strCjr += StringHelper.obj2str(feedback[i][0]) + ",";
					}
					if(feedback[i][1] != null){
						strCdcl += StringHelper.obj2str(feedback[i][1]) + ",";
					}
				}
				if(!strCdcl.equals("")){
					strCdcl = strCdcl.substring(0, strCdcl.lastIndexOf(","));
				}
				if(!strCjr.equals("")){
					strCjr = strCjr.substring(0, strCjr.lastIndexOf(","));
				}
			}
			alarmSql = "UPDATE T_ATTEMPER_ALARM ALARM SET  ALARM.FINISHTIME=TO_DATE('" + ajjssj +"','YYYY-MM-DD HH24:MI')," +
			" ALARM.EVENTTYPE='" + ajlx + "'," ;
			if(!alarmState.equals("")){
				alarmSql += " ALARM.ALARMADDRESS='"+alarmState+"',";//报警地点
			}
			if(!sgdj.equals("")){
				alarmSql += " ALARM.EVENTLEVEL='" + sgdj + "',";//事故等级
			}
			alarmSql += " ALARM.COMEOUTCAR='" + strCdcl + "',ALARM.COMEOUTCOUNT='"+ strCjrs + "'," ;
			alarmSql += " ALARM.COMEOUTPERSON='" + strCjr + "',ALARM.EVENTSTATE='" + EVENTSTATE + "'";
			alarmSql += " WHERE ALARM.ALARMID='" + ALARMID + "'" ;

		}else{
			//更新报警主表
			alarmSql = "UPDATE T_ATTEMPER_ALARM ALARM SET  ALARM.EVENTTYPE='" + ajlx + "'" ;
			if(!alarmState.equals("")){
				alarmSql += " ,ALARM.ALARMADDRESS='"+alarmState+"'";//报警地点
			}
			if(!sgdj.equals("")){
				alarmSql += " ,ALARM.EVENTLEVEL='" + sgdj + "'";//事故等级
			}
			
			if(StringHelper.obj2int(EVENTSTATE_NOW,0)<StringHelper.obj2int(CJ,0)){
				alarmSql += " ,ALARM.EVENTSTATE='" + EVENTSTATE + "'";//事件状态
			}else if(isUpdate(EVENTSTATE,EVENTSTATE_NOW)){
				alarmSql += " ,ALARM.EVENTSTATE='" + EVENTSTATE + "'";//事件状态
			}
			alarmSql += " WHERE ALARM.ALARMID='" + ALARMID + "'" ;
		}
		
		
		
		//-->火灾爆炸
		
		String eventSql = "select sysdate from dual";
		if(eventType.equals("")){
			
		}else{
			int type = StringHelper.obj2int(eventType.substring(3, 6),0);
			switch (type) {
				case 1:
					//交通事故
					eventSql = "UPDATE T_ATTEMPER_ACCIDENT ACCIDENT SET ACCIDENT.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI'),ACCIDENT.ECONOMYLOSS='"+ jjss +"',ACCIDENT.FLESHWOUNDPERSONCOUNT='" + qsrs + "'," +
					" ACCIDENT.GBHPERSONCOUNT='" + zsrs + "',ACCIDENT.DEATHPERSONCOUNT='" + swrs + "',ACCIDENT.DEALWITHPERSONCOUNT='" + sars + "'," +
					" ACCIDENT.GRAPPLEPERSONCOUNT='" + zhrs +"',ACCIDENT.SALVATIONPERSONCOUNT='" + jzrs +"',ACCIDENT.ISUNCOVERCRIMINALCASE='" + phxsaj + "'," +
					" ACCIDENT.ISCHECKORDERCASE='" + cczaaj + "',ACCIDENT.ISRESOLVEDISSENSION='" + jjjf + "',ACCIDENT.ISFEEDBACKEND='"+ fkzj+"'" +
					" WHERE ACCIDENT.ALARMID='"+ALARMID+"'" ;
					break;
				case 2:
					//交通拥堵
					eventSql = "UPDATE T_ATTEMPER_CONGESTION CONGESTION SET CONGESTION.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI'),CONGESTION.ECONOMYLOSS='"+ jjss +"',CONGESTION.FLESHWOUNDPERSONCOUNT='" + qsrs + "'," +
					" CONGESTION.GBHPERSONCOUNT='" + zsrs + "',CONGESTION.DEATHPERSONCOUNT='" + swrs + "',CONGESTION.DEALWITHPERSONCOUNT='" + sars + "'," +
					" CONGESTION.GRAPPLEPERSONCOUNT='" + zhrs +"',CONGESTION.SALVATIONPERSONCOUNT='" + jzrs +"',CONGESTION.ISUNCOVERCRIMINALCASE='" + phxsaj + "'," +
					" CONGESTION.ISCHECKORDERCASE='" + cczaaj + "',CONGESTION.ISRESOLVEDISSENSION='" + jjjf + "',CONGESTION.ISFEEDBACKEND='"+ fkzj+"'" +
					" WHERE CONGESTION.ALARMID='"+ALARMID+"'" ;
					break;
				case 5:
					//嫌疑车辆
					eventSql = "UPDATE T_ATTEMPER_BLACKLIST BLACKLIST SET BLACKLIST.SALVATIONPERSONCOUNT=' " + jzrs +
					"' WHERE BLACKLIST.ALARMID='"+ALARMID+"'" ;
					break;
				case 6:
					//灾害天气
					eventSql = "UPDATE T_ATTEMPER_WEATHER WEATHER SET WEATHER.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" WEATHER.ROADSITUATION='" + lmqk + "'," +
					" WEATHER.AFFECTROAD='" + yxdl + "' WHERE WEATHER.ALARMID='"+ALARMID+"'" ;
					break;
				case 7:
					//治安事件
					eventSql = "UPDATE T_ATTEMPER_POLICEEVENT POLICEEVENT SET POLICEEVENT.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" POLICEEVENT.AFFECTEXTENT='" + bjfw + "'," +
					" POLICEEVENT.AFFECTROAD='" + yxdl+ "' WHERE POLICEEVENT.ALARMID='"+ALARMID+"'" ;
					break;
				case 8:
					//大型车故障
					eventSql = "UPDATE T_ATTEMPER_EXCEPTIONCAR EXCEPTIONCAR SET EXCEPTIONCAR.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" EXCEPTIONCAR.DriverName='" + sjxm + "'," +
					" EXCEPTIONCAR.ExceptionCarUnit='" + gzcdw + "' WHERE EXCEPTIONCAR.ALARMID='"+ALARMID+"'" ;
					break;
				case 10:
					//地质灾害<公路桥梁塌方、泥石流>
					eventSql = "UPDATE T_ATTEMPER_GEOLOGICDISASTER GEOLOGICDISASTER SET GEOLOGICDISASTER.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" GEOLOGICDISASTER.AFFECTEXTENT='" + bjfw + "'," +
					" GEOLOGICDISASTER.AFFECTROAD='" + yxdl + "' WHERE GEOLOGICDISASTER.ALARMID='"+ALARMID+"'" ;
					break;
				case 11:
					//市政事件<煤气、热气泄露、自来水跑水、停电>
					eventSql = "UPDATE T_ATTEMPER_TOWNPLAN TOWNPLAN SET TOWNPLAN.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" TOWNPLAN.AFFECTEXTENT='" + bjfw + "'," +
					" TOWNPLAN.AFFECTROAD='" + yxdl + "' WHERE TOWNPLAN.ALARMID='"+ALARMID+"'" ;
					break;
				case 12:
					//火灾爆炸
					eventSql = "UPDATE T_ATTEMPER_FIREANDBLAST FIREANDBLAST SET FIREANDBLAST.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" FIREANDBLAST.ECONOMYLOSS='"+ jjss +"',FIREANDBLAST.FLESHWOUNDPERSONCOUNT='" + qsrs + "'," +
					" FIREANDBLAST.GBHPERSONCOUNT='" + zsrs + "',FIREANDBLAST.DEATHPERSONCOUNT='" + swrs + "', " +
					" FIREANDBLAST.AFFECTROAD='" + yxdl + "'WHERE FIREANDBLAST.ALARMID='"+ALARMID+"'" ;
					break;
				default:
					eventSql = "select sysdate from dual";
				break;
			}
		}
		
		ArrayList<String> list = new ArrayList<String>(); 
		if(!feedBackSql.equals("")){
			list.add(feedBackSql); 
		}
		list.add(alarmSql); 
		list.add(eventSql); 
		if(!complateSql.equals("")){
			list.add(complateSql);
		}
		System.out.println("feedBackSql="+feedBackSql);
		System.out.println("eventSql="+eventSql);
		System.out.println("alarmSql="+alarmSql);
		//执行更新操作
		boolean isOk = excuteSql(list);
		if(isOk){
			out.write("T");
			
			PoliceTime policeTime=new PoliceTime();
			String longLatsql="select x||','||y from t_attemper_alarm where  alarmid='"+ALARMID+"'";
			String longLat=DBHandler.getSingleResult(longLatsql).toString();
			String car=cdcl.split(";")[0];
			if(!longLat.equals(",")){
				policeTime.stop();
				policeTime.start(car, longLat, FEEDBACKID);
			}
			if(isUpdate(EVENTSTATE,EVENTSTATE_NOW)){
				FeedBackMgr.SendFeedBackMsg(ALARMID,sjly,ajlx,fkdw,EVENTSTATE,time);
				if(sjly.equals("002001")){
					//发给三台的
					new FeedBackMgr().SendFeedBackMsg(ALARMID,"0");
				}
			}
		}else{
			out.write("F");
		}
		out.close();
		return null;
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：判断事件预处理状态级别是否高于事件当前状态
	 * @参数：alarmState:事件预处理状态；alarmState_now：事件当前状态
	 * @返回：false/true
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static boolean isUpdate(String eventState ,String eventState_now){
		boolean isOk = false;
//		如果当前事件状态为空,或者事件状态是派警到现场且不等于当前事件状态写入报警主表
		if(StringHelper.obj2int(eventState_now,0) < StringHelper.obj2int(CJ,0)){
			isOk = true;
		}else if(!eventState.equals(eventState_now) && (eventState.equals(DDXC)||eventState.equals(CLWB))){
			isOk = true;
		}
		return isOk;
	}
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：交通事故事件业务处理
	 * @参数：feedBackSql:更新反馈表；eventSql：更新事件子表；alarmSql：更新报警主表
	 * @返回：false or true
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static boolean excuteSql(ArrayList list){
//		开始事务
		boolean bOk = true;
		if(list == null){
			return bOk;
		}
		Database db = null;
		
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			
			String sql = "";
			for(int i=0;i<list.size();i++){
				sql = StringHelper.obj2str(list.get(i),"");
				if(!sql.equals("") && 0 <db.executeUpdate(sql)){
				}else{
					bOk = false;
					break;
				}
			}
		} catch(Exception e) {
			bOk = false;
			System.out.println(e.getMessage());
		}finally {
			if(null != db) {
				if(bOk) {
					db.commitTrans(false);
				} else {
					db.commitTrans(true);
				}
				//关闭数据对象
				DBFactory.closeDatabase(db);
			}
		}
		return bOk;
	}
}