package com.ehl.dispatch.cdispatch.action;

import org.apache.log4j.Logger;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.utils.StringHelper;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.AlarmInfoOpt;
import com.ehl.dispatch.cdispatch.core.AccUtil;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.AccDao;
import com.ehl.sm.common.Constants;
import com.ehl.sm.sysmanage.LogManage;

/**
 * @����: lidq
 * @�汾�ţ�1.0
 * @˵�����ϱ���Ϣ������
 * @�������ڣ�2010-01-10
 * @�޸��ߣ�
 * @�޸����ڣ�
 */
public class AccCtrl  extends Controller {
	Logger logger = Logger.getLogger(AccCtrl.class);
	
	/**
	 * @����: lidq
	 * @�汾�ţ�1.0
	 * @����˵�����༭�¹���Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-10
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doEditAccidentInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		if("".equals(alarmId)){
			out.write("error");
			out.close();
			return null;
		}
		
		List<String> sqlList = AccUtil.setAccidentInfo(request);
		AccDao acc = new AccDao();
		boolean bRes = acc.excuteSql(sqlList);
		if(bRes){
			out.write("success");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭��ͨ�¹��ϱ���Ϣ");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * ��ʱ��ȡ������Ϣ
	 * ������timer ��ֵ
	 */
	public ActionForward doGetPoliceOnTime(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		
		//String timer = StringHelper.obj2str(request.getParameter("timer"),"");
		int depttype = StringHelper.obj2int(request.getParameter("depttype"), 0);
		String deptcode = StringHelper.obj2str(request.getParameter("deptcode"),"");
		
		if("".equals(deptcode)){
			out.close();
			return null;
		}
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		
		AccDao acc = new AccDao();
		
		//�������ע�¹�����
		Object alarmMonInfo = acc.getPoliceOnTime(depttype,deptcode,"MM");
		xmlData.append("<row>");
		xmlData.append("<col>");
		xmlData.append(StringHelper.obj2str(alarmMonInfo,"0"));
		xmlData.append("</col>");
		
		//���24Сʱ���ע�¹�����
		Object alarmHH24Info = acc.getPoliceOnTime(depttype,deptcode,"DD");
		xmlData.append("<col>");
		xmlData.append(StringHelper.obj2str(alarmHH24Info,"0"));
		xmlData.append("</col>");
		
		xmlData.append("</row>");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	
	/**
	 * @����: lidq
	 * @�汾�ţ�1.0
	 * @����˵������ȡ�¹���Ϣ
	 * @�������¼�id
	 * @���أ�
	 * @�������ڣ�2010-01-10
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doGetAccidentInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		if("".equals(alarmId)){
			out.write("");
			out.close();
			return null;
		}
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		
		AccDao acc = new AccDao();
		//������Ϣ���ϱ���Ϣ
		Object alarmInfo [] = acc.getAlarmInfo(alarmId);
		xmlData.append(DataToXML.objArrayToXml(alarmInfo));
		
		//֧���ܶӴ�����Ϣ
		Object flowInfo [][] = acc.getAlarmFlowInfo(alarmId);
		if(flowInfo != null && flowInfo.length>0){
			xmlData.append(DataToXML.objArrayToXml(flowInfo,"flow"));
		}
		
		if(alarmInfo != null && alarmInfo.length>0){
			//�¹ʳ�����Ϣ
			Object accCarInfo [][] = acc.getAccCarInfo(alarmId);
			xmlData.append(DataToXML.objArrayToXml(accCarInfo,"car"));
		}
		
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	
	/**
	 * @����: lidq
	 * @�汾�ţ�1.0
	 * @����˵����ɾ������
	 * @�������¼�id
	 * @���أ�
	 * @�������ڣ�2010-01-10
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doDelPoliceInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		String policeType = StringHelper.obj2str(request.getParameter("policeType"),"");
		if("".equals(alarmId)){
			out.write("");
			out.close();
			return null;
		}
		List<String> sqlList = AccUtil.delPolice(alarmId, policeType);
		AccDao acc = new AccDao();
		boolean bRes = acc.excuteSql(sqlList);
		if(bRes){
			out.write("1");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "ɾ����ͨ�¹��ϱ���Ϣ");
		}else{
			out.write("0");
		}
		out.write("");
		out.close();
		return null;
	}
	
	/**
	 * �õ��Զ����ɵ�id��ʱ��
	 */
	public ActionForward doGetNewInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		HttpSession session =request.getSession();
		
		String usercode=(String)session.getAttribute(Constants.PCODE_KEY);
		AccDao acc = new AccDao();
		String str=acc.setNewInfo(usercode);
		out.write(str);
		return null;
	
	}
}
