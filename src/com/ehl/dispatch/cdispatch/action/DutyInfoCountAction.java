package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DutyInfoCountCore;
import com.ehl.dispatch.cdispatch.dao.DutyInfoCountDao;
import com.ehl.sm.base.Constant;

public class DutyInfoCountAction extends Controller{
	Logger logger = Logger.getLogger(DutyInfoCountAction.class);
	public final static String nodeDesc = "��ͨ����->ֵ����Ϣͳ��->��Ϣ����ͳ��";
	public final static String accType = "�¹�����";
	public final static String alarmType = "��������";
	
	public DutyInfoCountCore core = new DutyInfoCountCore();
	//��ͳ�Ƶľ������
	private String eventObj[][] = {{"001024","��ͨ�¹�"},{"001002","��ͨӵ��"},{"001023","ʩ��ռ��"},{"","����"}};
	
	
	/**
	 * �¹�����ͳ��
	 */
	public ActionForward doAccType(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		//eventtype,STARTTIME,ENDTIME,deptType,TSJGID
		String dateS = request.getParameter("stime");
		String dateE = request.getParameter("etime");
		String statType = request.getParameter("sostyle");
		String deptid = request.getParameter("sobject");
		String area = request.getParameter("area");
		//Modified by Liuwx 2011-10-17
		//������Ŀ
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),0);
		//Modification finished
		String jgid = String.valueOf(request.getSession().getAttribute(Constant.JGID_VAR));
		if (dateS == null || dateE == null || statType == null || deptid == null) {
			logger.error(nodeDesc + "[" + accType + "ͳ��]:" + "�����б�ȱʧ��");
			return null;
		}
		int statTypeInt;
		try {
			statTypeInt = Integer.parseInt(statType);
		} catch (Exception e) {
			logger.error(nodeDesc + "[" + accType + "ͳ��]:" + "�����������Ͳ�����");
			return null;
		}
		
		String xml = core.accType(statTypeInt,dateS,dateE,deptid,area,jgid,sortItem);
		
		PrintWriter out = response.getWriter();
		out.write(xml);
		return null;
	}
	
	/**
	 * ��������ͳ��
	 */
	public ActionForward doAlarmType(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		//��ʼ���ַ���
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		//��ȡ����
		String dateS = request.getParameter("stime");
		String dateE = request.getParameter("etime");
		String statType = request.getParameter("sostyle");
		String deptid = request.getParameter("sobject");
		String area = request.getParameter("area");
		//Modified by Liuwx 2011-10-17
		//������Ŀ
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),0);
		//Modification finished
		String jgid = String.valueOf(request.getSession().getAttribute(Constant.JGID_VAR));
		//��֤����->������֤
		if (dateS == null || dateE == null || statType == null || deptid == null) {
			logger.error(nodeDesc + "[" + alarmType + "ͳ��]:" + "�����б�ȱʧ��");
			return null;
		}
		//��֤����->��������
		int statTypeInt;
		try {
			statTypeInt = Integer.parseInt(statType);
		} catch (Exception e) {
			logger.error(nodeDesc + "[" + alarmType + "ͳ��]:" + "�����������Ͳ�����");
			return null;
		}
		//��֤����->������֤
		//.......
		//ȡ��ͳ�Ʒ����ַ�����ʾ
		String xml = core.alarmType(statTypeInt,dateS,dateE,deptid,area,jgid,sortItem);
		//��Ӧ->���
		PrintWriter out = response.getWriter();
		out.write(xml);
		//��Ӧ->��ת
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ȡ��ͳ����Ϣ<br/>
	 * ͳ����Ϣȡ�ô���Ľ���
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doBuildChart(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String eventtype = StringHelper.obj2str(request.getParameter("eventtype"),"");
		String STARTTIME = StringHelper.obj2str(request.getParameter("STARTTIME"),"");
		String ENDTIME = StringHelper.obj2str(request.getParameter("ENDTIME"),"");
		// ȡ���á�;�����ӵĵ�λid
		String departIds = StringHelper.obj2str(request.getParameter("depId"),"");
		// ȡ���á�;�����ӵĵ�λ����
		String depName = StringHelper.obj2str(request.getParameter("depName"),"");
		String jglx = StringHelper.obj2str(request.getParameter("jglx"),"0");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String deptType = StringHelper.obj2str(request.getParameter("deptType"),"");
		
		DutyInfoCountDao dicd = new DutyInfoCountDao();
		Object[][] dutyInfo = null;
		String eventtype_1 = "001024";//��ͨ�¹�(�ش���)
		String eventtype_2 = "001002";//��ͨӵ��
		String eventtype_3 = "001023";//ʩ��ռ��
		if("".equals(eventtype)){
			dutyInfo = dicd.getData(eventtype,STARTTIME,ENDTIME,departIds,depName,jglx,jgid,deptType);
		}else if(eventtype_1.equals(eventtype)){
			dutyInfo = dicd.getData(eventtype,STARTTIME,ENDTIME,departIds,depName,jglx,jgid,deptType);
		}else if(eventtype_2.equals(eventtype)){
			dutyInfo = dicd.getData(eventtype,STARTTIME,ENDTIME,departIds,depName,jglx,jgid,deptType);
		}else if(eventtype_3.equals(eventtype)){
			dutyInfo = dicd.getData(eventtype,STARTTIME,ENDTIME,departIds,depName,jglx,jgid,deptType);
		}
		
		String xmlStr = buildChartStr(eventtype,STARTTIME,ENDTIME,departIds,depName,jglx,jgid,deptType,dutyInfo);
		out.write(xmlStr);
		out.close();
		return null;
	}
	
	private String buildChartStr(String eventtype, String sTARTTIME, String eNDTIME, String departIds, String depName, String jglx, String jgid, String deptType,Object[][] dutyInfo){
		String[] deptArr = null;//����
		if(!"2".equals(jglx)){
			DutyInfoCountDao dicd = new DutyInfoCountDao();
			deptArr = dicd.getDeptData(eventtype, sTARTTIME, eNDTIME, jglx, jgid, deptType);
		}else{
			deptArr = depName.split(";");
		}
		StringBuffer xmlData = new StringBuffer();
//		String caption = sTARTTIME + "��" + eNDTIME + "ֵ����Ϣͳ��";
		xmlData.append("<chart caption=\""+sTARTTIME + "��" + eNDTIME + "ֵ����Ϣͳ��"+"\" shownames=\"1\" showvalues=\"0\" decimals=\"0\" baseFont=\"����\" baseFontSize=\"15\">");
		if(deptArr != null && deptArr.length>0){
			xmlData.append("<categories>");
			for(int i=0;i<deptArr.length;i++){
				xmlData.append("<category label=\""+deptArr[i]+"\"/>");
			}
			xmlData.append("</categories>");
		
			String deptName = "";
			String jgmc = "";
			String eventType = "";
			String sjlb = "";
			try {
				boolean check = false;
				if(dutyInfo!= null && dutyInfo.length>0){
					for (int i = 0; i < eventObj.length; i++){
						eventType = eventObj[i][0];
						xmlData.append("<dataset seriesName=\""+eventObj[i][1]+"\" showValues=\"0\"> ");
						for(int j = 0; j<deptArr.length;j++){
							deptName = deptArr[j];
							for(int k = 0; k<dutyInfo.length;k++){
								jgmc = StringHelper.obj2str(dutyInfo[k][0]);
								sjlb = StringHelper.obj2str(dutyInfo[k][2]);
								if(deptName.equals(jgmc) && eventType.equals(sjlb)){
									xmlData.append("<set value=\""+StringHelper.obj2str(dutyInfo[k][1],"0")+"\"/>");
									check = true;
									break;
								}
							}
							if(!check){
								xmlData.append("<set value=\"0\"/>");
							}
							check = false;
						}
						xmlData.append("</dataset>");
					}
				}
			} catch (Exception e) {
		
			}
		}
		xmlData.append("</chart> ");
		return xmlData.toString();
	}
}
