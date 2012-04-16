package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.dataAccess.FeedBackQuery;
import com.ehl.dispatch.bdispatch.dataAccess.FeedBackQueryOnBack;

public class PoliceFeedBackCtrl extends Controller{

	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：从反馈表中获取历次反馈记录
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static String doGetEventListById(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append(FeedBackQuery.getEventListById(alarmId));
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doGetEventById(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		String eventType = StringHelper.obj2str(request.getParameter("eventType"),"");
		String FEEDBACKID = StringHelper.obj2str(request.getParameter("FEEDBACKID"), "");
		
		try {
			StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rfXML>\n");
			xmlData.append("<RFWin>\n");
			
			String strFeedBackDesc = "";
			String strAlarmDesc = "";
			String strChildDesc = "";
			
			if(!FEEDBACKID.equals("")){
				strFeedBackDesc = FeedBackQueryOnBack.getMesOnFeedBack(FEEDBACKID,eventType);
				xmlData.append(strFeedBackDesc);
			}else{
				xmlData.append( "<row id='FeedBack'>\n");
				xmlData.append( "<col id='ALARMID_FEED'>" + alarmId + "</col>");
				xmlData.append( "<col id='ajjssj'></col>");
				xmlData.append( "<col id='cdcl'></col>");
				xmlData.append( "<col id='cjrs'></col>");
				xmlData.append( "<col id='cjdw'></col>");
				xmlData.append( "<col id='cjr'></col>");
				xmlData.append( "<col id='sjzt'></col>");
				xmlData.append( "<col id='cjsj'></col>");
				xmlData.append( "<col id='djsj'></col>");
				
				xmlData.append( "<col id='jjss'></col>");
				xmlData.append( "<col id='qsrs'></col>");
				xmlData.append( "<col id='zsrs'></col>");
				xmlData.append( "<col id='swrs'></col>");
				xmlData.append( "<col id='sars'></col>");
				xmlData.append( "<col id='zhrs'></col>");
				xmlData.append( "<col id='jzrs'></col>");
				xmlData.append( "<col id='jjjf'></col>");
				xmlData.append( "<col id='cczaaj'></col>");
				xmlData.append( "<col id='fkzj'></col>");
				xmlData.append( "<col id='phxsaj'></col>");
				xmlData.append( "<col id='yxdl'></col>");
				xmlData.append( "<col id='bjfw'></col>");
				xmlData.append( "<col id='gzcdw'></col>");
				xmlData.append( "<col id='sjxm'></col>");
				xmlData.append( "<col id='lmqk'></col>");
				
				xmlData.append( "</row>\n");
			}
			 //各个事件的详情查询
			if(!alarmId.equals("")){
				strAlarmDesc = FeedBackQueryOnBack.getMesOnAlarm(alarmId);
				strChildDesc = checkEventTable(alarmId,eventType);
				
				if(strAlarmDesc.equals("")){
					out.write("F");
					out.close();
					return null;
				}else{
					xmlData.append(strAlarmDesc);
					if(!strChildDesc.equals("")){
						xmlData.append(strChildDesc);
					}
				}
			}
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
			
			out.write(xmlData.toString());
//			System.out.println(xmlData.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.close();
		}
		out.close();
		return null;
	}
		
	/**
	 * @作者: ldq
	 * @版本号：1.0
	 * @函数说明：根据eventType取得指挥调度事件子表
	 * @参数：
	 * @返回：
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public static String checkEventTable(String alarmId,String eventType){
		String eventDesc = "";
		FeedBackQueryOnBack feedBack = new FeedBackQueryOnBack();
		
		if(eventType.equals("")){
			return eventDesc;
		}else{
			int type = StringHelper.obj2int(eventType.substring(3, 6),0);
			switch (type) {
			case 1:
				eventDesc = feedBack.getMesOnAccident(alarmId);//交通事故
				break;
			case 2:
				eventDesc = feedBack.getMesOnCongestion(alarmId);//交通拥堵
				break;
			case 5:
				eventDesc = feedBack.getMesOnBlackList(alarmId);//嫌疑车辆
				break;
			case 6:
				eventDesc = feedBack.getMesOnWeather(alarmId);//灾害天气
				break;
			case 7:
				eventDesc = feedBack.getMesOnPoliceEvent(alarmId);//治安事件
				break;
			case 8:
				eventDesc = feedBack.getMesOnExceptionCar(alarmId);//大型车故障
				break;
			case 10:
				eventDesc = feedBack.getMesOnGeoLogicDisaster(alarmId);//地质灾害<公路桥梁塌方、泥石流>
				break;
			case 11:
				eventDesc = feedBack.getMesOnTownPlan(alarmId);//市政事件<煤气、热气泄露、自来水跑水、停电>
				break;
			case 12:
				eventDesc = feedBack.getMesOnFireAndBlast(alarmId);//火灾爆炸
				break;
			default:
				break;
			}
		}
		
		return eventDesc;
	}
}
