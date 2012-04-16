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
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵�����ӷ������л�ȡ���η�����¼
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵����
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
			 //�����¼��������ѯ
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
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵��������eventTypeȡ��ָ�ӵ����¼��ӱ�
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
				eventDesc = feedBack.getMesOnAccident(alarmId);//��ͨ�¹�
				break;
			case 2:
				eventDesc = feedBack.getMesOnCongestion(alarmId);//��ͨӵ��
				break;
			case 5:
				eventDesc = feedBack.getMesOnBlackList(alarmId);//���ɳ���
				break;
			case 6:
				eventDesc = feedBack.getMesOnWeather(alarmId);//�ֺ�����
				break;
			case 7:
				eventDesc = feedBack.getMesOnPoliceEvent(alarmId);//�ΰ��¼�
				break;
			case 8:
				eventDesc = feedBack.getMesOnExceptionCar(alarmId);//���ͳ�����
				break;
			case 10:
				eventDesc = feedBack.getMesOnGeoLogicDisaster(alarmId);//�����ֺ�<��·������������ʯ��>
				break;
			case 11:
				eventDesc = feedBack.getMesOnTownPlan(alarmId);//�����¼�<ú��������й¶������ˮ��ˮ��ͣ��>
				break;
			case 12:
				eventDesc = feedBack.getMesOnFireAndBlast(alarmId);//���ֱ�ը
				break;
			default:
				break;
			}
		}
		
		return eventDesc;
	}
}
