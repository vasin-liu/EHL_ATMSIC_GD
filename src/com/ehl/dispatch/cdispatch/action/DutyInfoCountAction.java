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
	public final static String nodeDesc = "交通警情->值班信息统计->信息分类统计";
	public final static String accType = "事故类型";
	public final static String alarmType = "警情类型";
	
	public DutyInfoCountCore core = new DutyInfoCountCore();
	//需统计的警情类别
	private String eventObj[][] = {{"001024","交通事故"},{"001002","交通拥堵"},{"001023","施工占道"},{"","其他"}};
	
	
	/**
	 * 事故类型统计
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
		//排序项目
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),0);
		//Modification finished
		String jgid = String.valueOf(request.getSession().getAttribute(Constant.JGID_VAR));
		if (dateS == null || dateE == null || statType == null || deptid == null) {
			logger.error(nodeDesc + "[" + accType + "统计]:" + "参数列表缺失！");
			return null;
		}
		int statTypeInt;
		try {
			statTypeInt = Integer.parseInt(statType);
		} catch (Exception e) {
			logger.error(nodeDesc + "[" + accType + "统计]:" + "参数数据类型不符！");
			return null;
		}
		
		String xml = core.accType(statTypeInt,dateS,dateE,deptid,area,jgid,sortItem);
		
		PrintWriter out = response.getWriter();
		out.write(xml);
		return null;
	}
	
	/**
	 * 警情类型统计
	 */
	public ActionForward doAlarmType(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		//初始化字符集
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		//获取参数
		String dateS = request.getParameter("stime");
		String dateE = request.getParameter("etime");
		String statType = request.getParameter("sostyle");
		String deptid = request.getParameter("sobject");
		String area = request.getParameter("area");
		//Modified by Liuwx 2011-10-17
		//排序项目
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),0);
		//Modification finished
		String jgid = String.valueOf(request.getSession().getAttribute(Constant.JGID_VAR));
		//验证参数->有无验证
		if (dateS == null || dateE == null || statType == null || deptid == null) {
			logger.error(nodeDesc + "[" + alarmType + "统计]:" + "参数列表缺失！");
			return null;
		}
		//验证参数->参数类型
		int statTypeInt;
		try {
			statTypeInt = Integer.parseInt(statType);
		} catch (Exception e) {
			logger.error(nodeDesc + "[" + alarmType + "统计]:" + "参数数据类型不符！");
			return null;
		}
		//验证参数->其他验证
		//.......
		//取得统计分析字符串表示
		String xml = core.alarmType(statTypeInt,dateS,dateE,deptid,area,jgid,sortItem);
		//响应->输出
		PrintWriter out = response.getWriter();
		out.write(xml);
		//响应->跳转
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 取得统计信息<br/>
	 * 统计信息取得处理的进行
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
		// 取得用“;”连接的单位id
		String departIds = StringHelper.obj2str(request.getParameter("depId"),"");
		// 取得用“;”连接的单位名称
		String depName = StringHelper.obj2str(request.getParameter("depName"),"");
		String jglx = StringHelper.obj2str(request.getParameter("jglx"),"0");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String deptType = StringHelper.obj2str(request.getParameter("deptType"),"");
		
		DutyInfoCountDao dicd = new DutyInfoCountDao();
		Object[][] dutyInfo = null;
		String eventtype_1 = "001024";//交通事故(重大警情)
		String eventtype_2 = "001002";//交通拥堵
		String eventtype_3 = "001023";//施工占道
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
		String[] deptArr = null;//机构
		if(!"2".equals(jglx)){
			DutyInfoCountDao dicd = new DutyInfoCountDao();
			deptArr = dicd.getDeptData(eventtype, sTARTTIME, eNDTIME, jglx, jgid, deptType);
		}else{
			deptArr = depName.split(";");
		}
		StringBuffer xmlData = new StringBuffer();
//		String caption = sTARTTIME + "至" + eNDTIME + "值班信息统计";
		xmlData.append("<chart caption=\""+sTARTTIME + "至" + eNDTIME + "值班信息统计"+"\" shownames=\"1\" showvalues=\"0\" decimals=\"0\" baseFont=\"宋体\" baseFontSize=\"15\">");
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
