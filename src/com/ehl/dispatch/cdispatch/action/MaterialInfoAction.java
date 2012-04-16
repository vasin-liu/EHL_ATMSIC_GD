/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.MaterialInfoDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.common.DispatchUtil;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.common.PromptLog;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.output.BuildChart;
import com.ehl.sm.common.util.CreateSequence;

/**
 * 重大警情控制类
 * 
 * @author dxn
 * @date 2010-05-13
 * 
 */
public class MaterialInfoAction extends Controller {
	/**
	 * 编辑重大警情信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyMaterialInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request
				.getParameter("insrtOrUpdt"), 0);

		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),
				"");// 0
		String TELENUM = StringHelper.obj2str(request.getParameter("TELENUM"),
				"");// 联系电话
		String ROADID = StringHelper
				.obj2str(request.getParameter("ROADID"), "");// 1
		String EVENTSOURCE = StringHelper.obj2str(request
				.getParameter("EVENTSOURCE"), "");// 2
		String EVENTTYPE = StringHelper.obj2str(request
				.getParameter("EVENTTYPE"), "");// 3
		String ALARMUNIT = StringHelper.obj2str(request
				.getParameter("ALARMUNIT"), "");// 4
		String TITLE = StringHelper.obj2str(request.getParameter("TITLE"), "");// 5
		String ALARMREGIONID = StringHelper.obj2str(request
				.getParameter("ALARMREGIONID"), "");// 6
		String ALARMREGION = StringHelper.obj2str(request
				.getParameter("ALARMREGION"), "");// 7
		String ROADNAME = StringHelper.obj2str(
				request.getParameter("ROADNAME"), "");// 8
		String KMVALUE = StringHelper.obj2str(request.getParameter("KMVALUE"),
				"");// 9
		String MVALUE = StringHelper
				.obj2str(request.getParameter("MVALUE"), "");// 9
		String CASEHAPPENTIME = StringHelper.obj2str(request
				.getParameter("CASEHAPPENTIME"), "");// 10
		String ALARMTIME = StringHelper.obj2str(request
				.getParameter("ALARMTIME"), "");// 10
		String REPORTUNIT = StringHelper.obj2str(request
				.getParameter("REPORTUNITIDVALUE"), "");// 11
		String REPORTPERSON = StringHelper.obj2str(request
				.getParameter("REPORTPERSON"), "");// 12
		String RESPONSIBLEPERSON = StringHelper.obj2str(request
				.getParameter("RESPONSIBLEPERSON"), "");
		String REPORTTIME = StringHelper.obj2str(request
				.getParameter("REPORTTIME"), "");// 13
		String ISBUS = StringHelper.obj2str(request.getParameter("ISBUS"), "");
		String ISSCHOOLBUS = StringHelper.obj2str(request
				.getParameter("ISSCHOOLBUS"), "");
		String ISDANAGERCAR = StringHelper.obj2str(request
				.getParameter("ISDANAGERCAR"), "");
		String ISFOREIGNAFFAIRS = StringHelper.obj2str(request
				.getParameter("ISFOREIGNAFFAIRS"), "");
		String ISPOLICE = StringHelper.obj2str(
				request.getParameter("ISPOLICE"), "");
		String ISMASSESCASE = StringHelper.obj2str(request
				.getParameter("ISMASSESCASE"), "");
		String ISCONGESTION = StringHelper.obj2str(request
				.getParameter("ISCONGESTION"), "");
		String ROADDIRECTION = StringHelper.obj2str(request
				.getParameter("ROADDIRECTION"), "");
		String DEATHPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("DEATHPERSONCOUNT"), "");
		String BRUISEPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("BRUISEPERSONCOUNT"), "");
		String ALARMDESC = StringHelper.obj2str(request
				.getParameter("ALARMDESC"), "");
		String zbrName = StringHelper.obj2str(request
				.getParameter("zbrName"), "");

		String FLOW1 = StringHelper.obj2str(request.getParameter("FLOW1"), "");
		String FLOWID1 = StringHelper.obj2str(request.getParameter("FLOWID1"),
				"");
		String FLOWKIND1 = StringHelper.obj2str(request
				.getParameter("FLOWKIND1"), "");
		String FLOW2 = StringHelper.obj2str(request.getParameter("FLOW2"), "");
		String FLOWID2 = StringHelper.obj2str(request.getParameter("FLOWID2"),
				"");
		String FLOWKIND2 = StringHelper.obj2str(request
				.getParameter("FLOWKIND2"), "");
		String FLOW3 = StringHelper.obj2str(request.getParameter("FLOW3"), "");
		String FLOWID3 = StringHelper.obj2str(request.getParameter("FLOWID3"),
				"");
		String FLOWKIND3 = StringHelper.obj2str(request
				.getParameter("FLOWKIND3"), "");
		String FLOW4 = StringHelper.obj2str(request.getParameter("FLOW4"), "");
		String FLOWID4 = StringHelper.obj2str(request.getParameter("FLOWID4"),
				"");
		String FLOWKIND4 = StringHelper.obj2str(request
				.getParameter("FLOWKIND4"), "");
		String FLOW5 = StringHelper.obj2str(request.getParameter("FLOW5"), "");
		String FLOWID5 = StringHelper.obj2str(request.getParameter("FLOWID5"),
				"");
		String FLOWKIND5 = StringHelper.obj2str(request
				.getParameter("FLOWKIND5"), "");
		String FLOW6 = StringHelper.obj2str(request.getParameter("FLOW6"), "");
		String FLOWID6 = StringHelper.obj2str(request.getParameter("FLOWID6"),
				"");
		String FLOWKIND6 = StringHelper.obj2str(request
				.getParameter("FLOWKIND6"), "");
		String FLOW7 = StringHelper.obj2str(request.getParameter("FLOW7"), "");
		String FLOWID7 = StringHelper.obj2str(request.getParameter("FLOWID7"),
				"");
		String FLOWKIND7 = StringHelper.obj2str(request
				.getParameter("FLOWKIND7"), "");
		String FLOW8 = StringHelper.obj2str(request.getParameter("FLOW8"), "");
		String FLOWID8 = StringHelper.obj2str(request.getParameter("FLOWID8"),
				"");
		String FLOWKIND8 = StringHelper.obj2str(request
				.getParameter("FLOWKIND8"), "");
		String FLOW9 = StringHelper.obj2str(request.getParameter("FLOW9"), "");
		String FLOWID9 = StringHelper.obj2str(request.getParameter("FLOWID9"),
				"");
		String FLOWKIND9 = StringHelper.obj2str(request
				.getParameter("FLOWKIND9"), "");

		String OPERATEID = StringHelper.obj2str(request
				.getParameter("OPERATEID"), "");

		// trafficInfo事故详细描述
		String trafficInfo = StringHelper.obj2str(request
				.getParameter("trafficInfo"), "");
		// 追加事故发生时间
		String info1 = StringHelper.obj2str(request.getParameter("info1"), "");
		// 追加受伤人数
		String info15 = StringHelper
				.obj2str(request.getParameter("info15"), "");
		// 追加接警人
		String RECEIVEPERSON = StringHelper.obj2str(request
				.getParameter("RECEIVEPERSON"), "");
		// buttonFlag("0"保存,"1"上报)
		String buttonFlag = StringHelper.obj2str(request
				.getParameter("buttonFlag"), "");
		// 当前用户的机构编号(2位：总队4位：支队6位：大队)
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		// 当前用户的机构id
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		// 当前用户为大队用户时，取得大队的所属支队
		String buttonPressed = StringHelper.obj2str(request
				.getParameter("buttonPressed"), "");
		String jgid_Zd = "";
		if (jgbh.length() == 6) {
			jgid_Zd = jgid.substring(0, 4);
		}
		String saveFlag = StringHelper.obj2str(
				request.getParameter("saveFlag"), "");
		String reportunitId = StringHelper.obj2str(request
				.getParameter("reportunitId"), "");

		// 2010年7月19日追加 "其他","失踪人数","上报电话"
		String ISOthersItem = StringHelper.obj2str(request
				.getParameter("ISOthersItem"), "");
		String MISSINGPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("MISSINGPERSONCOUNT"), "");
		String TELNUMSB = StringHelper.obj2str(
				request.getParameter("TELNUMSB"), "");

		String caseHappenTime = StringHelper.obj2str(request
				.getParameter("caseHappenTime"), "");
		String caseHappenPlace = StringHelper.obj2str(request
				.getParameter("caseHappenPlace"), "");
		String REPORTPERSONXM = StringHelper.obj2str(request
				.getParameter("REPORTPERSONXM"), "");

		String glAccNum = StringHelper.obj2str(
				request.getParameter("glAccNum"), "");
		String ISARMYACC = StringHelper.obj2str(request
				.getParameter("ISARMYACC"), "");
		String receivetime = StringHelper.obj2str(
				request.getParameter("receivetime"), "");
		String ddapprover = StringHelper.obj2str(request
				.getParameter("ddapprover"), "");
		String zdapprover = StringHelper.obj2str(request
				.getParameter("zdapprover"), "");
		String zodapprover = StringHelper.obj2str(request
				.getParameter("zodapprover"), "");
		
		// 添加总队转发机构
		String adcode = request.getParameter("adcode");

		HashMap hm = new HashMap();
		hm.put("ALARMID", ALARMID);
		hm.put("TELENUM", TELENUM);
		hm.put("ROADID", ROADID);
		hm.put("EVENTSOURCE", EVENTSOURCE);
		hm.put("EVENTTYPE", EVENTTYPE);
		hm.put("ALARMUNIT", ALARMUNIT);
		hm.put("TITLE", TITLE);
		hm.put("ALARMREGIONID", ALARMREGIONID);
		hm.put("ALARMREGION", ALARMREGION);
		hm.put("ROADNAME", ROADNAME);
		hm.put("KMVALUE", KMVALUE);
		hm.put("MVALUE", MVALUE);
		hm.put("CASEHAPPENTIME", CASEHAPPENTIME);
		
		// 接警时间
		hm.put("receivetime", receivetime);
		hm.put("ALARMTIME", ALARMTIME);
		hm.put("REPORTUNIT", REPORTUNIT);
		hm.put("REPORTPERSON", REPORTPERSON);
		hm.put("RESPONSIBLEPERSON", RESPONSIBLEPERSON);
		hm.put("REPORTTIME", REPORTTIME);
		hm.put("ddapprover", ddapprover);
		hm.put("zdapprover", zdapprover);
		hm.put("zodapprover", zodapprover);

		hm.put("ISBUS", ISBUS);
		hm.put("ISSCHOOLBUS", ISSCHOOLBUS);
		hm.put("ISDANAGERCAR", ISDANAGERCAR);
		hm.put("ISFOREIGNAFFAIRS", ISFOREIGNAFFAIRS);
		hm.put("ISPOLICE", ISPOLICE);
		hm.put("ISMASSESCASE", ISMASSESCASE);
		hm.put("ISCONGESTION", ISCONGESTION);
		hm.put("ROADDIRECTION", ROADDIRECTION);
		hm.put("DEATHPERSONCOUNT", DEATHPERSONCOUNT);
		hm.put("BRUISEPERSONCOUNT", BRUISEPERSONCOUNT);
		hm.put("ALARMDESC", ALARMDESC);

		hm.put("FLOW1", FLOW1);
		hm.put("FLOWID1", FLOWID1);
		hm.put("FLOWKIND1", FLOWKIND1);
		hm.put("FLOW2", FLOW2);
		hm.put("FLOWID2", FLOWID2);
		hm.put("FLOWKIND2", FLOWKIND2);
		hm.put("FLOW3", FLOW3);
		hm.put("FLOWID3", FLOWID3);
		hm.put("FLOWKIND3", FLOWKIND3);
		hm.put("FLOW4", FLOW4);
		hm.put("FLOWID4", FLOWID4);
		hm.put("FLOWKIND4", FLOWKIND4);
		hm.put("FLOW5", FLOW5);
		hm.put("FLOWID5", FLOWID5);
		hm.put("FLOWKIND5", FLOWKIND5);
		hm.put("FLOW6", FLOW6);
		hm.put("FLOWID6", FLOWID6);
		hm.put("FLOWKIND6", FLOWKIND6);
		hm.put("FLOW7", FLOW7);
		hm.put("FLOWID7", FLOWID7);
		hm.put("FLOWKIND7", FLOWKIND7);
		hm.put("FLOW8", FLOW8);
		hm.put("FLOWID8", FLOWID8);
		hm.put("FLOWKIND8", FLOWKIND8);
		hm.put("FLOW9", FLOW9);
		hm.put("FLOWID9", FLOWID9);
		hm.put("FLOWKIND9", FLOWKIND9);
		hm.put("OPERATEID", OPERATEID);
		// 事故详细描述
		hm.put("trafficInfo", trafficInfo);
		// 事故发生时间
		hm.put("info1", info1);
		// 事故受伤人数
		hm.put("info15", info15);
		// 事故接警人
		hm.put("RECEIVEPERSON", RECEIVEPERSON);
		hm.put("zbrName", zbrName);
		// buttonFlag("0"保存,"1"上报)
		hm.put("buttonFlag", buttonFlag);
		hm.put("buttonPressed", buttonPressed);
		// 机构编号2位：总队4位：支队6位：大队
		hm.put("jgbh", jgbh);
		// saveFlag为1时表示要下发支队
		hm.put("saveFlag", saveFlag);
		hm.put("reportunitId", reportunitId);

		// 2010年7月19日追加 "其他","失踪人数","上报电话"
		hm.put("ISOthersItem", ISOthersItem);
		hm.put("MISSINGPERSONCOUNT", MISSINGPERSONCOUNT);
		hm.put("TELNUMSB", TELNUMSB);
		hm.put("jgid_Zd", jgid_Zd);

		hm.put("caseHappenTime", caseHappenTime);
		hm.put("caseHappenPlace", caseHappenPlace);

		hm.put("REPORTPERSONXM", REPORTPERSONXM);
		hm.put("glAccNum", glAccNum);
		hm.put("ISARMYACC", ISARMYACC);

		System.out.println("FLOW1=" + FLOW1 + ",FLOW2=" + FLOW2
				+ ",insertOrUpdate=" + insertOrUpdate);
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = false;
		if (insertOrUpdate == 0) {
			// insert
			flag = cmd.addMaterialInfo(hm);
			if (flag) {
				out.write("新增重大警情成功！");
			} else {
				out.write("新增重大警情失败！");
			}
		} else {
			// update
			hm.put("adcode", adcode);// 添加总队转发机构
			flag = cmd.editMaterialInfo(hm);
			if ("2".equals(buttonFlag)) {
				if (flag) {
					if ("1".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("下发")) {
						out.write("下发重大警情成功！");
					} else if ("1".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("转发")) {
						out.write("转发重大警情成功！");
					} else if ("0".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("转发")) {
						out.write("转发重大警情成功！");
					} else if ("0".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("保存")) {
						out.write("处理重大警情成功！");
					}

				} else {
					if ("1".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("下发")) {
						out.write("下发重大警情失败！");
					} else if ("1".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("转发")) {
						out.write("转发重大警情失败！");
					} else if ("0".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("转发")) {
						out.write("转发重大警情失败！");
					} else if ("0".equals(saveFlag.trim())
							&& buttonPressed.trim().equals("保存")) {
						out.write("处理重大警情失败！");
					}
				}
			} else if ("4".equals(buttonFlag) || "3".equals(buttonFlag)) {
				if (flag) {
					out.write("签收成功！");
				} else {
					out.write("签收失败！");
				}
			} else {
				if (flag) {
					if(jgbh.length() == 4 && "5".equals(buttonFlag)) {
						if (adcode.indexOf("440000000000") != -1) {
							out.write("上报重大警情成功！");
						} else {
							out.write("下发重大警情成功！");
						}
					} else{
						out.write("上报重大警情成功！");
					}
				} else {
					out.write("上报重大警情失败！");
				}
			}
		}
		out.close();
		return null;
	}

	/**
	 * 删除重大警情信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteMaterialInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = cmd.delteMaterialInfo(alarmId);
		out.write(String.valueOf(flag));
		out.close();
		return null;
	}

	/**
	 * 删除重大警情信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public boolean doReceivedMaterialInfo(String ALARMID, String RECIVEUNIT) {
		MaterialInfoDao cmd = new MaterialInfoDao();
		return cmd.receivedMaterialInfo(ALARMID, RECIVEUNIT);
	}

	/**
	 * 查询重大警情信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetMaterialInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String updateFlag = StringHelper.obj2str(request.getParameter("updateFlag"), "");
		String zbrName = StringHelper.obj2str(request.getParameter("zbrName"), "");
		
		MaterialInfoDao cmd = new MaterialInfoDao();
		// 主表
		Object[] obj = cmd.getMaterialInfo(alarmId, jgbh);
		// 车辆
		Object[][] obj0 = cmd.getAccidentCar(alarmId);
		Object[][] obj1 = cmd.getAccidentForeigner(alarmId);
		Object[][] obj2 = cmd.getAlarmFlow(alarmId);
		Object[][] obj3 = cmd.getAlarmAttachment(alarmId);
		Object[][] obj4 = cmd.getNewXbInfo(alarmId);

		StringBuffer xmlData = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");

		if (obj != null) {
			for (int j = 0; j < obj.length; j++) {
				if (obj[j] == null || "".equals(obj[j].toString().trim())) {
					xmlData.append("<col>　</col>");
				} else {
					xmlData.append("<col>" + StringHelper.obj2str(obj[j])
							+ "</col>");
				}
			}
		}
		xmlData.append("\n</row>\n");
		if (obj0 != null) {

			for (int i = 0; i < obj0.length; i++) {
				xmlData.append("<car>\n");
				for (int j = 0; j < obj0[i].length; j++) {
					if (obj0[i][j] == null
							|| "".equals(obj0[i][j].toString().trim())) {
						xmlData.append("<col>　</col>");
					} else {
						xmlData.append("<col>"
								+ StringHelper.obj2str(obj0[i][j]) + "</col>");
					}
				}
				xmlData.append("\n</car>\n");
			}
		}
		if (obj1 != null) {

			for (int i = 0; i < obj1.length; i++) {
				xmlData.append("<foreigner>\n");
				for (int j = 0; j < obj1[i].length; j++) {
					if (obj1[i][j] == null
							|| "".equals(obj1[i][j].toString().trim())) {
						xmlData.append("<col>　</col>");
					} else {
						xmlData.append("<col>"
								+ StringHelper.obj2str(obj1[i][j]) + "</col>");
					}
				}
				xmlData.append("\n</foreigner>\n");
			}
		}
		if (obj2 != null) {

			for (int i = 0; i < obj2.length; i++) {
				xmlData.append("<flow>\n");
				for (int j = 0; j < obj2[i].length; j++) {
					if (obj2[i][j] == null
							|| "".equals(obj2[i][j].toString().trim())) {
						xmlData.append("<col>　</col>");
					} else {
						xmlData.append("<col>"
								+ StringHelper.obj2str(obj2[i][j]) + "</col>");
					}
				}
				xmlData.append("\n</flow>\n");
			}
		}
		if (obj3 != null) {

			for (int i = 0; i < obj3.length; i++) {
				xmlData.append("<jqfj>\n");
				for (int j = 0; j < obj3[i].length; j++) {
					if (obj3[i][j] == null
							|| "".equals(obj3[i][j].toString().trim())) {
						xmlData.append("<col>　</col>");
					} else {
						xmlData.append("<col>"
								+ StringHelper.obj2str(obj3[i][j]) + "</col>");
					}
				}
				xmlData.append("\n</jqfj>\n");
			}
		}
		if (obj4 != null) {

			for (int i = 0; i < obj4.length; i++) {
				xmlData.append("<xbinfo>\n");
				for (int j = 0; j < obj4[i].length; j++) {
					if (obj4[i][j] == null
							|| "".equals(obj4[i][j].toString().trim())) {
						xmlData.append("<col>　</col>");
					} else {
						xmlData.append("<col>"
								+ StringHelper.obj2str(obj4[i][j]) + "</col>");
					}
				}
				xmlData.append("\n</xbinfo>\n");
			}
		}
//		// 获取接收单位状态描述
		AccDeptCore core = new AccDeptCore();
//		System.out.println(jgbh);
//		if (jgbh.length() != 2) {
//			jgbh = "44";
//		}
//		
		String cjgid = FlowUtil.rpadding(jgbh, "0", 12 - jgbh.length());
//		Object[][] accdept = core.dao.getAccDept(alarmId, cjgid, 2);
//
//		if (accdept != null) {
//			String id = String.valueOf(accdept[0][0]);
//			String xml = core.getDisDept(id, new String[] { "1", "2" });
//			if (xml != null) {
//				xmlData.append(xml.toString());
//			}
//		}
		
		//获取填报单位的相关单位
		String jgid = String.valueOf(obj[20]);
		String pjgid = Constant.getParents(jgid);
		
		if (pjgid != null) {
			jgid = jgid + "," + pjgid;
		}
		String sql = "select replace(jgmc,'"+Constant.GAJJTJC+"','"+Constant.JJ+"') from t_sys_department where instr('"+jgid+"',jgid)!=0 order by jgid desc";
		Object[][] jgmcs = DBHandler.getMultiResult(sql);
		String jgmc = null;
		if (jgmcs != null) {
			jgmc = (String) jgmcs[0][0];
			for (int i = 1; i < jgmcs.length; i++) {
				jgmc += "," + jgmcs[i][0];
			}
		}
		String jginfo = "<jginfo>" + jgid + ";" + jgmc + "</jginfo>";
		xmlData.append(jginfo);
		
		//获取发送单位信息
		sql = "select adid from t_oa_acceptdept where aid='"+alarmId+"' and rpdcode='"+cjgid+"' order by id desc";
		sql = "select adid from (" + sql + ") where rownum=1";
		Object adid = FlowUtil.readSingle(sql);
		if(adid != null){
			sql = "select sid from t_oa_content where id='"+adid+"'";
			sql = "("+sql+")";
			sql = "select f_get_dept(rpdcode),rpname from t_oa_acceptdept where id=nvl("+sql+",'"+adid+"')";
			Object[] adinfo = FlowUtil.readLine(sql);
			if(adinfo != null){
				String sendInfo = "<sendInfo>"+adinfo[0]+","+Constant.nvl(adinfo[1])+ "</sendInfo>";
				xmlData.append(sendInfo);
			}
		}
		
		//获取支队下发大队单位
		Object[][] zdaccdept = core.dao.getZDAccDept(alarmId);
		String zdaccdeptId = "";
		String zdaccdeptName = "";
		
		//获得支队所属的大队数目
		int childCount = Constant.getChildCount(jgid.substring(0,4) + "00000000");
		int zdcount = 0;
		
		if (zdaccdept != null) {
			int count = 0;
			
			for (Object[] objects : zdaccdept) {
				for (Object object : objects) {
						zdcount++;
				}
			}
			for(int i=0;i<zdaccdept.length;i++){
				for(int j=0;j<zdaccdept[i].length;j++){
			    	zdaccdeptId += String.valueOf(zdaccdept[i][j]);
			    }
			    if(i + 1 < zdaccdept.length) {
			    	count++;
					if(count == 3) {
						break;
					}
			    	zdaccdeptId += ",";
			    }
			}
			
			String zdaccdeptsql = "select f_get_dept('" + zdaccdeptId + "') from dual";
			zdaccdeptName = String.valueOf(DBHandler.getSingleResult(zdaccdeptsql));
			
			String[] zdaccdeptNames = zdaccdeptName.split(",");
			zdaccdeptName = "";
			for(int i=0; i < zdaccdeptNames.length; i++) {
				zdaccdeptName += zdaccdeptNames[i];
				if(i + 1 < zdaccdeptNames.length) {
					zdaccdeptName += "、";
			    }
				
			}
			if(zdcount > 3) {
				zdaccdeptName += "等";
			}
			
			String zdxfname = "";
			if(zdcount == childCount) {
				zdxfname = "\n<zdxfname>全市大队</zdxfname>";
			} else {
				zdxfname = "\n<zdxfname>" + zdaccdeptName + "</zdxfname>";
			}
			xmlData.append(zdxfname);
		}
		
		//获取总队转发单位
		Object[][] zodaccdept = core.dao.getZODAccDept(alarmId);
		String zodaccdeptId = "";
		String zodaccdeptName = "";
		int zodcount = 0;
		
		if (zodaccdept != null) {
			for (Object[] objects : zodaccdept) {
				for (Object object : objects) {
					if(String.valueOf(object).substring(4,12).equals("00000000")) {
						zodcount++;
					}
				}
			}
			
			if (zodaccdept != null) {
				int count = 0;
				
				for(int i=0;i<zodaccdept.length;i++){
					for(int j=0;j<zodaccdept[i].length;j++){
						if(String.valueOf(zodaccdept[i][j]).substring(4,12).equals("00000000")) {
							count++;
							zodaccdeptId += String.valueOf(zodaccdept[i][j]);
							if(count == 3) {
								break;
							}
							if(i + 1 < zodaccdept.length) {
						    	zodaccdeptId += ",";
						    }
							
						}
				    }
					if(count == 3) {
						break;
					}
				}
				
				String zodaccdeptsql = "select f_get_dept('" + zodaccdeptId + "') from dual";
				zodaccdeptName = String.valueOf(DBHandler.getSingleResult(zodaccdeptsql));
				
				String[] zodaccdeptNames = zodaccdeptName.split(",");
				zodaccdeptName = "";
				
				for(int i=0; i < zodaccdeptNames.length; i++) {
					zodaccdeptName += zodaccdeptNames[i];
					if(i + 1 < zodaccdeptNames.length) {
						zodaccdeptName += "、";
				    }
				}
				
				if(zodcount > 3) {
					zodaccdeptName += "等";
				}
			}
			
			String zodxfname = "";
			if(!zodaccdeptName.equals("null")) {
				if(zodcount == 21) {
					zodxfname = "\n<zodxfname>全省支队</zodxfname>\n";
				} else{
					zodxfname = "\n<zodxfname>" + zodaccdeptName + "</zodxfname>\n";
				}
				xmlData.append(zodxfname);
			}
		}

		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		System.out.println("accXML:\n" + xmlData.toString());
		
		if(updateFlag.equals("1") && zbrName != null) {
			String updateRpnameSql = "update T_OA_ACCEPTDEPT set RPNAME = '" + zbrName + "' where  aid= '"+ alarmId +"' and rpname is null and rpdcode = "+ jgid +");";
			DBHandler.execute(updateRpnameSql);
		}
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	
	
	/**
	 * 获得当前的事故状态
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetMaterialState(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String itemId = StringHelper.obj2str(request.getParameter("itemId"),"");
		String oldState = StringHelper.obj2str(request.getParameter("oldState"),"");
		String flag = StringHelper.obj2str(request.getParameter("flag"),"");
		
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[] obj = cmd.getMaterialState(alarmId);
		
		String nowState = "";
		
		for (Object object : obj) {
			nowState = String.valueOf(object);
		}
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<mist>\n");
		xmlData.append("<col>").append(itemId).append("</col>");
		xmlData.append("<col>").append(alarmId).append("</col>");
		xmlData.append("<col>").append(jgbh).append("</col>");
		xmlData.append("<col>").append(oldState).append("</col>");
		xmlData.append("<col>").append(nowState).append("</col>");
		xmlData.append("<col>").append(flag).append("</col>");
		xmlData.append("\n</mist>");
		
		System.out.println("nowStateXML:\n" + xmlData.toString());
		response.getWriter().write(xmlData.toString());
		return null;
	}

	public ActionForward doGetMaterialInfo0(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[] obj = cmd.getMaterialInfo0(alarmId);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 查询道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAlarmFlow(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MaterialInfoDao cmd = new MaterialInfoDao();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		Object[][] obj = cmd.getAlarmFlow(alarmId);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 编辑道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyFlow(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request
				.getParameter("insrtOrUpdt"), 0);

		String FLOWID = StringHelper
				.obj2str(request.getParameter("FLOWID"), "");// 0
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),
				"");// 0
		String ALARMUNIT = StringHelper.obj2str(request
				.getParameter("ALARMUNIT"), "");// 0
		String REPORTUNIT = StringHelper.obj2str(request
				.getParameter("REPORTUNIT"), "");// 0
		String REPORTPERSON = StringHelper.obj2str(request
				.getParameter("REPORTPERSON"), "");// 0
		String REPORTTIME = StringHelper.obj2str(request
				.getParameter("REPORTTIME"), "");// 0
		String ALARMDESC = StringHelper.obj2str(request
				.getParameter("ALARMDESC"), "");// 0
		String REPORTSITUATION = StringHelper.obj2str(request
				.getParameter("REPORTSITUATION"), "");// 0
		String VERIFYSITUATION = StringHelper.obj2str(request
				.getParameter("VERIFYSITUATION"), "");// 0
		String RECIVEUNIT = StringHelper.obj2str(request
				.getParameter("RECIVEUNIT"), "");

		HashMap hm = new HashMap();
		hm.put("FLOWID", FLOWID);
		hm.put("ALARMID", ALARMID);
		hm.put("ALARMUNIT", ALARMUNIT);
		hm.put("REPORTUNIT", REPORTUNIT);
		hm.put("REPORTPERSON", REPORTPERSON);
		hm.put("REPORTTIME", REPORTTIME);
		hm.put("ALARMDESC", ALARMDESC);
		hm.put("REPORTSITUATION", REPORTSITUATION);
		hm.put("VERIFYSITUATION", VERIFYSITUATION);
		hm.put("RECIVEUNIT", RECIVEUNIT);

		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = false;
		if (insertOrUpdate == 0) {
			flag = cmd.addFlow(hm);
			if (flag) {
				out.write("新增续报成功！");
			} else {
				out.write("新增续报失败！");
			}
		} else {
			flag = cmd.editFlow(hm);
			if (flag) {
				out.write("修改续报成功！");
			} else {
				out.write("修改续报失败！");
			}
		}
		out.close();
		return null;
	}

	/**
	 * 查询道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAccidentCarId(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jgId = StringHelper.obj2str(request.getParameter("jgId"), "");
		Object[] obj = { MaterialInfoDao.getRandomId(jgId) };
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 查询道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAccidentCar(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MaterialInfoDao cmd = new MaterialInfoDao();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		Object[][] obj = cmd.getAccidentCar(alarmId);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 删除道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyAccidentCar(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ccId = StringHelper.obj2str(request.getParameter("ccId"), "");
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String hpzl = StringHelper.obj2str(request.getParameter("hpzl"), "");
		String hphm = StringHelper.obj2str(request.getParameter("hphm"), "");
		String[] args = { ccId, alarmId, hpzl, hphm };
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = cmd.addAccidentCar(args);
		if (flag) {
			out.write("1");
		} else {
			out.write("0");
		}
		out.close();
		return null;
	}

	/**
	 * 删除道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteAccidentCar(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ccId = StringHelper.obj2str(request.getParameter("ccId"), "");
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = cmd.deleteAccidentCar(ccId);
		if (flag) {
			out.write("1");
		} else {
			out.write("0");
		}
		out.close();
		return null;
	}

	/**
	 * 查询涉外人员信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAccidentForeignerId(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jgId = StringHelper.obj2str(request.getParameter("jgId"), "");
		Object[] obj = { MaterialInfoDao.getRandomId(jgId) };
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 查询涉外人员信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAccidentForeigner(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MaterialInfoDao cmd = new MaterialInfoDao();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		Object[][] obj = cmd.getAccidentForeigner(alarmId);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 删除涉外人员信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyAccidentForeigner(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String afId = StringHelper.obj2str(request.getParameter("afId"), "");
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String afxm = StringHelper.obj2str(request.getParameter("afxm"), "");
		String afgj = StringHelper.obj2str(request.getParameter("afgj"), "");
		String[] args = { afId, alarmId, afxm, afgj };
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = cmd.addAccidentForeigner(args);
		if (flag) {
			out.write("1");
		} else {
			out.write("0");
		}
		out.close();
		return null;
	}

	/**
	 * 删除涉外人员信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteAccidentForeigner(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String afId = StringHelper.obj2str(request.getParameter("afId"), "");
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = cmd.deleteAccidentForeigner(afId);
		if (flag) {
			out.write("1");
		} else {
			out.write("0");
		}
		out.close();
		return null;
	}

	public ActionForward doDeleteFile(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String fjid = StringHelper.obj2str(request.getParameter("fjid"), "");
		MaterialInfoDao cmd = new MaterialInfoDao();
		boolean flag = cmd.deleteFile(fjid);
		if (flag) {
			out.write("删除文件成功！");
		} else {
			out.write("删除文件失败！");
		}
		out.close();
		return null;
	}

	public ActionForward doGetFlapperType(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[][] obj = cmd.getFlapperType();
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 取得提醒信息件数<br/>
	 * 提醒信息件数的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String reciveunit = (String) DispatchUtil.getCurrentUserData(
					request).get("BRANCHID");
			MaterialInfoDao cmd = new MaterialInfoDao();
			Object[] obj = cmd.getPromptCount(reciveunit);// 事故信息
			int count = cmd.getPromptXBCount(reciveunit);// 事故续报信息
			Object[][] tbcounts = cmd.getPromptTb("count(distinct alarmid)",
					reciveunit);
			String tbcount = "0";
			if (tbcounts != null) {
				tbcount = tbcounts[0][0] + "";
			}
			StringBuffer xmlData = new StringBuffer(
					"<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rfXML>\n");
			xmlData.append("<RFWin>\n");
			xmlData.append("<row>\n");
			xmlData.append("<col>" + obj[0] + "</col>");
			xmlData.append("<col>" + count + "</col>");
			xmlData.append("<col>" + tbcount + "</col>");
			xmlData.append("\n</row>\n");
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
			out.write(String.valueOf(xmlData));
			PromptLog.debug(request, "交通事故", new String[]{"事故信息","事故续报信息","事故通报信息"}, new Object[]{obj[0],count,tbcount}, xmlData.toString());
			out.close();
		} catch (Exception e) {
			out.write("Fail");
			out.close();
		}
		return null;
	}

	/**
	 * 取得提醒详细信息<br/>
	 * 提醒详细信息的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reportkind = StringHelper.obj2str(request
				.getParameter("reportkind"), "");
		String reciveunit = (String) DispatchUtil.getCurrentUserData(request)
				.get("BRANCHID");
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[][] obj = cmd.getPromptInfo(reportkind, reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 取得提醒通告信息件数<br/>
	 * 提醒通告信息件数的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptTbInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reciveunit = (String) DispatchUtil.getCurrentUserData(request)
				.get("BRANCHID");
		//拼接总队和支队id
		String zdReciveunit = reciveunit.substring(0, 4) + "00000000";
		String zodReciveunit = reciveunit.substring(0, 2) + "0000000000";
		MaterialInfoDao cmd = new MaterialInfoDao();
		String colStr = "";
		String state = "decode(f_get_accsjgid(al.alarmid,'" + reciveunit +"'),null,(select name from t_attemper_code where id=al.EVENTSTATE), " + zdReciveunit + ",'支队下发','" + zodReciveunit + "','总队转发')";
		if(!reciveunit.substring(2, 12).equals("0000000000") && !reciveunit.substring(4, 12).equals("00000000")) {
			colStr = "distinct al.alarmid,al.alarmregion,al.title,al.reportperson,al.reporttime,decode(ad.state,'1','未签收','已签收'),al.eventstate," + state;
		} else {
			colStr = "al.alarmid,al.alarmregion,al.title,al.reportperson,al.reporttime,decode(ad.state,'1','未签收','已签收'),al.eventstate";
		}
		
		Object[][] obj = cmd.getPromptTb(colStr, reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 取得提醒通告信息件数<br/>
	 * 提醒通告信息件数的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNoticeCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reciveunit = (String) DispatchUtil.getCurrentUserData(request)
				.get("BRANCHID");
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[] obj = cmd.getNoticeCount(reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 取得提醒通告详细信息<br/>
	 * 提醒通告详细信息的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNoticeInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reportkind = StringHelper.obj2str(request
				.getParameter("reportkind"), "");
		String reciveunit = (String) DispatchUtil.getCurrentUserData(request)
				.get("BRANCHID");
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[][] obj = cmd.getNoticeInfo(reportkind, reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	// 杜追加
	/**
	 * 取得续报初期化信息<br>
	 * 取得续报初期化信息的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetFlowInitInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MaterialInfoDao cmd = new MaterialInfoDao();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		Object[] obj = cmd.getFlowInitInfo(alarmId, jgbh);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 续报情报的保存<br>
	 * 续报情报的保存处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	/*
	 * public ActionForward doInsertXbInfo(Action action, HttpServletRequest
	 * request, HttpServletResponse response) throws Throwable {
	 * request.setCharacterEncoding("UTF-8");
	 * response.setCharacterEncoding("UTF-8");
	 * response.setContentType("text/xml; charset=UTF-8"); PrintWriter out =
	 * response.getWriter(); String alarmId =
	 * StringHelper.obj2str(request.getParameter("alarmId"),""); String deptcode
	 * = StringHelper.obj2str(request.getParameter("deptcode"),""); String
	 * PERSONWRITE =
	 * StringHelper.obj2str(request.getParameter("PERSONWRITE"),""); String
	 * TELEPHONE = StringHelper.obj2str(request.getParameter("TELEPHONE"),"");
	 * String XBBT = StringHelper.obj2str(request.getParameter("XBBT"),"");
	 * String XBNR = StringHelper.obj2str(request.getParameter("XBNR"),""); //
	 * 取得最大squence String maxOpid = MaterialInfoDao.getRandomId(deptcode); //
	 * 续报信息插入的sql StringBuffer sql = newStringBuffer(
	 * "insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR) values('"
	 * ); sql.append(maxOpid).append("','"); sql.append(alarmId).append("','");
	 * sql.append("警情续报").append("','"); sql.append(PERSONWRITE).append("','");
	 * sql.append(TELEPHONE).append("','"); sql.append(XBBT).append("','");
	 * sql.append(XBNR).append("')"); DBHandler.execute(String.valueOf(sql));
	 * out.close(); return null; }
	 */

	/**
	 * 续报情报的取得<br>
	 * 续报情报的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSearchXbInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String falgZd = StringHelper
				.obj2str(request.getParameter("falgZd"), "");
		// 续报信息取得的sql
		StringBuffer sql = new StringBuffer(
				"select OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH ,a1.EVENTSTATE from t_oa_process ,T_ATTEMPER_ALARM_ZD a1 where YWID = '");
		sql.append(alarmId);
		sql.append("' and YWLX = '警情续报'  and a1.alarmid = YWID ");
		// 如果总队用户时的查询
		if (2 == falgZd.length()) {
			sql.append(" and XBSTATE in('总队已签收续报','支队上报总队续报')");
		}
		Object[][] obj = DBHandler.getMultiResult(String.valueOf(sql));
		String res = DataToXML.objArrayToXml(obj);
		System.out.println(res);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 总队更新警情处理<br>
	 * 总队更新警情的处理的进行。
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateMaterialInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String searchAlarmId = StringHelper.obj2str(request
				.getParameter("searchAlarmId"), "");
		String searchEventState = StringHelper.obj2str(request
				.getParameter("searchEventState"), "");

		// 总队下发支队时支队的更新处理
		if ("004037".equals(searchEventState)) {
			StringBuffer sql = new StringBuffer(
					"update t_attemper_alarm_zd set ");
			sql.append("EVENTSTATE='").append("004035'").append(
					" where ALARMID='").append(searchAlarmId).append("'");
			System.out.println("1:" + sql);
			DBHandler.execute(String.valueOf(sql));

		} else if ("004033".equals(searchEventState)) {
			// 支队用户时的更新设置(大队)
			StringBuffer sql = new StringBuffer("update t_attemper_alarm set ");
			sql.append(" EVENTSTATE = '004042'");
			sql.append(" where ALARMID = '").append(searchAlarmId).append("'");
			System.out.println(sql);
			DBHandler.execute(String.valueOf(sql));

			sql = new StringBuffer("update t_attemper_alarm_zd set ");
			sql.append(" EVENTSTATE = '004042'");
			sql.append(" where ALARMID = '").append(searchAlarmId).append("'");
			System.out.println(sql);
			DBHandler.execute(String.valueOf(sql));

		} else if ("004034".equals(searchEventState)) {
			// 总队用户提醒时的更新
			StringBuffer sql = new StringBuffer(
					"update t_attemper_alarm_zd set ");
			sql.append(" EVENTSTATE = '004043'");
			sql.append(" where ALARMID = '").append(searchAlarmId).append("'");
			System.out.println(sql);
			DBHandler.execute(String.valueOf(sql));
		}

		// System.out.println(res);
		out.write("");
		out.close();
		return null;
	}

	/**
	 * 续报情报的更新<br>
	 * 续报情报的更新处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateXBInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String count = StringHelper.obj2str(request.getParameter("count"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");

		for (int i = 1; i <= Integer.valueOf(count); i++) {

			String PERSONWRITE = StringHelper.obj2str(request
					.getParameter("PERSONWRITE" + i), "");
			String TELEPHONE = StringHelper.obj2str(request
					.getParameter("TELEPHONE" + i), "");
			String XBBT = StringHelper.obj2str(
					request.getParameter("XBBT" + i), "");
			String XBNR = StringHelper.obj2str(
					request.getParameter("XBNR" + i), "");
			String OPID = StringHelper.obj2str(
					request.getParameter("OPID" + i), "");
			String FJMS = StringHelper.obj2str(
					request.getParameter("FJMS" + i), "");
			// 续报信息取得的sql
			StringBuffer sql = new StringBuffer("update t_oa_process set ");
			sql.append(" PERSONWRITE = '").append(PERSONWRITE).append("',");
			sql.append(" TELEPHONE = '").append(TELEPHONE).append("',");
			sql.append(" XBBT = '").append(XBBT).append("',");
			sql.append(" XBNR = '").append(XBNR).append("',");
			if (jgbh.length() == 6) {
				sql.append(" XBSTATE = '").append("新增续报").append("',");
			} else if (jgbh.length() == 4) {
				sql.append(" XBSTATE = '").append("支队上报总队续报").append("',");
			}
			sql.append(" XBFILEMODFIY = '").append(FJMS).append("'");
			sql.append(" where OPID = '").append(OPID).append("'");
			DBHandler.execute(String.valueOf(sql));
			System.out.println(sql);
		}
		out.close();
		return null;
	}

	/**
	 * 附件的上传<br>
	 * 附件的上传处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUploadFile(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		final int size = 20 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();

		String uploadPath = realPath + "EHL_Upload\\";
		String tempPath = realPath + "EHL_Upload\\temp\\";

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}

		String databasePath = "EHL_Upload/";
		String databaseFileName = null;
		// 投诉编号
		String alarmId = "";
		// 附件描述
		String fjms = "";
		String deptcode = "";
		String PERSONWRITE = "";
		String TELEPHONE = "";
		String XBBT = "";
		String XBNR = "";
		String jgbh = "";

		// 附件有无flg
		boolean fileFlg = false;

		String strRerutn = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>";
		strRerutn += "<body onload='window.close();'></body></html>";

		request.setCharacterEncoding(encoding);

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(10240);
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			factory.setRepository(tempFile);

			FileUpload upload = new FileUpload(factory);
			upload.setSizeMax(size);
			List list = null;
			try {
				upload.setHeaderEncoding("utf-8");
				list = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件上传失败");
				out
						.write("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body><script type=\"text/javascript\">"
								+ "alert('上传附件过大！');  window.close();"
								+ "parent.returnValue = \"ok\";</script></body><html>");
				out.close();
				return null;
			}

			Iterator ite = list.iterator();

			int fileCount = 0;

			try {
				// 依次处理每一个文件：
				while (ite.hasNext()) {

					FileItem fi = (FileItem) ite.next();
					if (fi == null) {
						continue;
					}
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("fileInfo" + fileCount)) {
							fileCount = fileCount + 1;
						} else if (fi.getFieldName().equals("insertYwid")) {
							alarmId = fi.getString(encoding);
						} else if (fi.getFieldName().equals("FJMS")) {
							fjms = fi.getString(encoding);
						} else if (fi.getFieldName().equals("deptcode")) {
							deptcode = fi.getString(encoding);
						} else if (fi.getFieldName().equals("PERSONWRITE")) {
							PERSONWRITE = fi.getString(encoding);
						} else if (fi.getFieldName().equals("TELEPHONE")) {
							TELEPHONE = fi.getString(encoding);
						} else if (fi.getFieldName().equals("XBBT")) {
							XBBT = fi.getString(encoding);
						} else if (fi.getFieldName().equals("XBNR")) {
							XBNR = fi.getString(encoding);
						} else if (fi.getFieldName().equals("jgbh")) {
							jgbh = fi.getString(encoding);
						}
					} else if (!fi.isFormField()) {
						// 取得最大squence
						String maxOpid = MaterialInfoDao.getRandomId(deptcode);

						// 取得文件名
						String fileName = fi.getName();
						// 获得文件名，这个文件名包括路径：
						if (fileName.length() != 0 && fileName != null
								&& fileName != "") {
							fileFlg = true;
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1, fileName
									.length());

							databaseFileName = databasePath + fileName;

							String xbKbn = "新增续报";
							if (jgbh.length() == 4) {
								xbKbn = "支队上报总队续报";
							} else {
								xbKbn = "新增续报";
							}
							// 在这里可以记录用户和文件信息
							// ...
							// 写入文件，暂定文件名为a.txt，可以从fileName中提取文件名：
							String newFileName = uploadPath + fileName;
							// 判断是否有相同文件名文件存在
							if (new File(newFileName).exists()
									&& new File(newFileName).isFile()) {
								double doubleRandom = Math.random() * 100000;
								databaseFileName = databasePath + doubleRandom
										+ "/" + fileName;
								uploadPath = uploadPath + doubleRandom + "\\";
								newFileName = uploadPath + fileName;
								File file = new File(uploadPath);

								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									// 续报信息插入的sql
									StringBuffer sql = new StringBuffer(
											"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
									sql.append(maxOpid).append("','");
									sql.append(alarmId).append("','");
									sql.append("警情续报").append("','");
									sql.append(PERSONWRITE).append("','");
									sql.append(TELEPHONE).append("','");
									sql.append(XBBT).append("','");
									sql.append(XBNR).append("','");
									sql.append(fjms).append("','");
									sql.append(databaseFileName).append("','");
									sql.append(xbKbn).append("','");
									sql.append(jgbh.substring(0, 4)).append(
											"')");
									DBHandler.execute(String.valueOf(sql));
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									StringBuffer sql = new StringBuffer(
											"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
									sql.append(maxOpid).append("','");
									sql.append(alarmId).append("','");
									sql.append("警情续报").append("','");
									sql.append(PERSONWRITE).append("','");
									sql.append(TELEPHONE).append("','");
									sql.append(XBBT).append("','");
									sql.append(XBNR).append("','");
									sql.append(fjms).append("','");
									sql.append(databaseFileName).append("','");
									sql.append(xbKbn).append("','");
									sql.append(jgbh.substring(0, 4)).append(
											"')");
									DBHandler.execute(String.valueOf(sql));
								}
							} else {
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									StringBuffer sql = new StringBuffer(
											"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
									sql.append(maxOpid).append("','");
									sql.append(alarmId).append("','");
									sql.append("警情续报").append("','");
									sql.append(PERSONWRITE).append("','");
									sql.append(TELEPHONE).append("','");
									sql.append(XBBT).append("','");
									sql.append(XBNR).append("','");
									sql.append(fjms).append("','");
									sql.append(databaseFileName).append("','");
									sql.append(xbKbn).append("','");
									sql.append(jgbh.substring(0, 4)).append(
											"')");
									DBHandler.execute(String.valueOf(sql));
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									StringBuffer sql = new StringBuffer(
											"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
									sql.append(maxOpid).append("','");
									sql.append(alarmId).append("','");
									sql.append("警情续报").append("','");
									sql.append(PERSONWRITE).append("','");
									sql.append(TELEPHONE).append("','");
									sql.append(XBBT).append("','");
									sql.append(XBNR).append("','");
									sql.append(fjms).append("','");
									sql.append(databaseFileName).append("','");
									sql.append(xbKbn).append("','");
									sql.append(jgbh.substring(0, 4)).append(
											"')");
									DBHandler.execute(String.valueOf(sql));
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("文件上传失败");
				out
						.write("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body><script type=\"text/javascript\">"
								+ "alert('续报失败！');  window.close();"
								+ "parent.returnValue = \"ok\";</script></body><html>");
				out.close();
				return null;
			}
		}

		String xbKbn2 = "新增续报";
		if (jgbh.length() == 4) {
			xbKbn2 = "支队上报总队续报";
		} else {
			xbKbn2 = "新增续报";
		}

		// 没有附件上传时
		if (!fileFlg) {
			// 取得最大squence
			String maxOpid = MaterialInfoDao.getRandomId(deptcode);
			// 上传附件信息的登录
			StringBuffer sql = new StringBuffer(
					"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
			sql.append(maxOpid).append("','");
			sql.append(alarmId).append("','");
			sql.append("警情续报").append("','");
			sql.append(PERSONWRITE).append("','");
			sql.append(TELEPHONE).append("','");
			sql.append(XBBT).append("','");
			sql.append(XBNR).append("','");
			sql.append(fjms).append("','");
			sql.append("").append("','");
			sql.append(xbKbn2).append("','");
			sql.append(jgbh.substring(0, 4)).append("')");
			DBHandler.execute(String.valueOf(sql));
		}
		out
				.write("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body onload=' window.close();'></body></html>");
		out.close();
		return null;
	}
	
	
	/**
	 * 上传续报附件操作<br>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doXBUploadFile(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		boolean isSuccess = false;
		final int size = 50 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();

		String uploadPath = realPath + "EHL_Upload\\";
		String tempPath = realPath + "EHL_Upload\\temp\\";

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}

		String databasePath = "EHL_Upload/";
		String databaseFileName = null;
		// 编号
		String alarmId = "";
		String fileModify = "";
		
		String localFile = "";
		String FJID = "";

		String strRerutn = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>";
		strRerutn += "<body onload='window.close();'></body></html>";

		request.setCharacterEncoding(encoding);

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 1024 * 1024);
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			factory.setRepository(tempFile);

			FileUpload upload = new FileUpload(factory);
			upload.setSizeMax(size);
			List list = null;
			try {
				upload.setHeaderEncoding("utf-8");
				list = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件上传失败");
				out
						.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body><script type=\"text/javascript\">"
								+ "alert('上传附件不能超过50兆！');"
								+ "parent.returnValue = \"ok\";</script></body><html>");
				out.close();
				return null;
			}
			String databaseFileNames = ""; // request.getSession().setAttribute("databaseFileName",
			// databaseFileName);
			Iterator ite = list.iterator();

			int fileCount = 0;

			try {
				// 依次处理每一个文件：
				while (ite.hasNext()) {
					FileItem fi = (FileItem) ite.next();
					localFile = fi.getName();
					if (fi == null) {
						continue;
					}
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("fileInfo" + fileCount)) {
							fileCount = fileCount + 1;
							fileModify = fi.getString(encoding);
						} else if (fi.getFieldName().equals("insertYwid")) {
							alarmId = fi.getString(encoding);
						}
					} else if (!fi.isFormField()) {

						// 取得文件名
						String fileName = fi.getName();
						// 获得文件名，这个文件名包括路径：
						if (fileName.length() != 0 && fileName != null
								&& fileName != "") {
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1, fileName
									.length());

							databaseFileName = databasePath + fileName;

							// 在这里可以记录用户和文件信息
							// ...
							// 写入文件，暂定文件名为a.txt，可以从fileName中提取文件名：
							String newFileName = uploadPath + fileName;
							// 判断是否有相同文件名文件存在
							if (new File(newFileName).exists()
									&& new File(newFileName).isFile()) {
								double doubleRandom = Math.random() * 100000;
								databaseFileName = databasePath + doubleRandom
										+ "/" + fileName;
								uploadPath = uploadPath + doubleRandom + "\\";
								newFileName = uploadPath + fileName;
								File file = new File(uploadPath);

								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									// 续报信息插入的sql
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql);
									FJID = maxSequence;
									isSuccess = true;
									System.out.println("XBUploadFile" + sql);
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql);
									FJID = maxSequence;
									isSuccess = true;
									System.out.println("XBUploadFile" + sql);
								}
							} else {
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									StringBuffer sql = new StringBuffer(
											"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql1 = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql1);
									FJID = maxSequence;
									isSuccess = true;
									System.out.println("XBUploadFile" + sql);
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql);
									FJID = maxSequence;
									isSuccess = true;
									System.out.println("XBUploadFile" + sql);
								}
							}
							databaseFileNames += "," + databaseFileName;
						}
					}
				}
				if (!databaseFileNames.equals("")) {
					databaseFileNames = databaseFileNames.substring(1);
					request.getSession().setAttribute("databaseFileNames",
							databaseFileNames);
				}
			} catch (Exception e) {
				System.out.println("文件上传失败");
				out
						.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body><script type=\"text/javascript\">"
								+ "alert('文件上传失败！');"
								+ "parent.returnValue = \"ok\";</script></body><html>");
				out.close();
				return null;
			}
		}
//		out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><script type='text/javascript'>function returnMessage() {alert('上传成功！');window.close();}</script></head><body onload='returnMessage()'></body></html>");
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<xbfj>\n");
		xmlData.append("<col>").append(FJID).append("</col>");
		xmlData.append("<col>").append(localFile).append("</col>");
		xmlData.append("\n</xbfj>");
		System.out.println("xbfj:\n" + xmlData);
//		out.println(xmlData);
		
		if(FJID != "") {
			out.println(FJID);
			out.println("###");
			out.println(localFile);
			out.println("###");
			out.println(localFile);
			out.println("###");
			out.println(isSuccess);
		}
		
		
		out.close();
		
		return null;
	}
	
	/**
	 * 取得提醒续报信息件数<br/>
	 * 提醒信息续报件数的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptXBCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reciveunit = (String) DispatchUtil.getCurrentUserData(request)
				.get("BRANCHID");
		MaterialInfoDao cmd = new MaterialInfoDao();
		int count = cmd.getPromptXBCount(reciveunit);
		StringBuffer xmlData = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
		xmlData.append("<col>" + count + "</col>");
		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		System.out.println(xmlData);
		out.write(String.valueOf(xmlData));
		out.close();
		return null;
	}

	/**
	 * 取得提醒续报详细信息<br/>
	 * 提醒续报详细信息的取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptXBInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String reciveunit = (String) DispatchUtil.getCurrentUserData(request)
				.get("BRANCHID");
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[][] obj = cmd.getPromptXBInfo(reciveunit);
		out.write(DataToXML.objArrayToXml(obj));
		System.out.println(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 通过流水号取得一条续报信息<br/>
	 * 通过流水号取得一条续报信息的处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetXBInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String searchOpid = StringHelper.obj2str(request
				.getParameter("searchOpid"), "");
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[][] obj = cmd.getXBInfoByOpid(searchOpid);
		out.write(DataToXML.objArrayToXml(obj));
		System.out.println(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 通过流水号取得一条续报信息<br/>
	 * 通过流水号取得一条续报信息的处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateXbByOpid(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String updateOpid = StringHelper.obj2str(request
				.getParameter("updateOpid"), "");
		String TELEPHONE = StringHelper.obj2str(request
				.getParameter("TELEPHONE"), "");
		String XBBT = StringHelper.obj2str(request.getParameter("XBBT"), "");
		String XBNR = StringHelper.obj2str(request.getParameter("XBNR"), "");
		String FJMS = StringHelper.obj2str(request.getParameter("FJMS"), "");
		String PERSONWRITE = StringHelper.obj2str(request
				.getParameter("PERSONWRITE"), "");

		// 更新sql
		StringBuffer sql = new StringBuffer("update t_oa_process set ");
		sql.append(" PERSONWRITE = '").append(PERSONWRITE).append("',");
		sql.append(" TELEPHONE = '").append(TELEPHONE).append("',");
		sql.append(" XBBT = '").append(XBBT).append("',");
		sql.append(" XBNR = '").append(XBNR).append("',");
		sql.append(" XBSTATE = '").append("支队上报总队续报").append("',");
		sql.append(" XBFILEMODFIY = '").append(FJMS).append("'");

		sql.append(" where OPID = '").append(updateOpid).append("'");
		DBHandler.execute(String.valueOf(sql));
		out.close();
		return null;
	}

	/**
	 * 通过流水号取得一条续报信息<br/>
	 * 通过流水号取得一条续报信息的处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateXbInfoByOpid(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		Hashtable prop = DispatchUtil.getCurrentUserData(request);

		// 当前用户信息
		String deptcode = ""; // 部门编码
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
		}

		String jgbh = "";// 总队(2位),支队(4位),大队(6位)
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		StringBuffer sql = new StringBuffer("update T_OA_ALARM_XB set ");

		// 登录用户支队时的更新sql
		if (jgbh.length() == 4) {
			sql.append(" XBFLG = '1'");
			// 登录用户总队时的更新sql
		} else if (jgbh.length() == 2) {
			sql.append(" ZDXBFLG = '1'");
		}

		sql.append(" where ID = '").append(alarmId).append("'");
		System.out.println(sql);
		DBHandler.execute(String.valueOf(sql));
		out.close();
		return null;
	}

	/**
	 * 更新警情为签收状态<br/>
	 * 更新警情为签收状态的处理<br/>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateEventState(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		boolean errFlg = false;

		// 判断jgbn是否为空
		if (jgbh.length() == 4) {
			// 支队用户时的更新设置(大队)
			StringBuffer sql = new StringBuffer("update t_attemper_alarm set ");
			sql.append(" EVENTSTATE = '004042'");
			sql.append(" where ALARMID = '").append(alarmId).append("'");
			System.out.println(sql);
			errFlg = DBHandler.execute(String.valueOf(sql));

			sql = new StringBuffer("update t_attemper_alarm_zd set ");
			sql.append(" EVENTSTATE = '004042'");
			sql.append(" where ALARMID = '").append(alarmId).append("'");
			System.out.println(sql);
			errFlg = DBHandler.execute(String.valueOf(sql));

			sql = new StringBuffer("update t_oa_process set ");
			sql.append(" YWLX = '大队上报支队签收'");
			sql.append(" where YWID = '").append(alarmId).append(
					"' and YWLX in ('大队上报支队未签收')");
			System.out.println(sql);
			errFlg = DBHandler.execute(String.valueOf(sql));

		} else if (jgbh.length() == 2) {
			// 总队用户时的更新
			StringBuffer sql = new StringBuffer(
					"update t_attemper_alarm_zd set ");
			sql.append(" EVENTSTATE = '004043'");
			sql.append(" where ALARMID = '").append(alarmId).append("'");
			System.out.println(sql);
			errFlg = DBHandler.execute(String.valueOf(sql));

			sql = new StringBuffer("update t_oa_process set ");
			sql.append(" YWLX = '支队上报总队签收'");
			sql.append(" where YWID = '").append(alarmId).append(
					"' and YWLX in ('支队上报总队未签收')");
			System.out.println(sql);
			errFlg = DBHandler.execute(String.valueOf(sql));
		}

		out.close();
		return null;
	}

	/**
	 * 获取填报部门名称部门<br/>
	 * 获取填报部门名称部门
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetDepatMentName(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String deptcode = ""; // 部门编码
		String deptname = ""; // 部门名称

		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			deptname = (String) prop.get("BRANCHNAME");
		}
		Object[] obj = { deptcode, deptname };
		System.out.println(DataToXML.objArrayToXml(obj));
		out.write(DataToXML.objArrayToXml(obj));
		out.close();
		return null;
	}

	/**
	 * 续报附件的上传<br>
	 * 续报附件的上传处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetServerFilePath(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String[] strArray = realPath.split("\\\\");
		ArrayList lst = new ArrayList();
		for (int i = 0; i < strArray.length; i++) {
			lst.add(strArray[i]);
		}

		System.out.println(DataToXML.ListDataToXml(lst));
		out.write(DataToXML.ListDataToXml(lst));
		out.close();

		return null;
	}

	/**
	 * 取得统计信息<br/>
	 * 统计信息取得处理的进行
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSearchTotalInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		String alarmTotalType = StringHelper.obj2str(request
				.getParameter("alarmTotalType"), "");
		String timeRadioType = StringHelper.obj2str(request
				.getParameter("timeRadioType"), "");
		String STARTTIME = StringHelper.obj2str(request
				.getParameter("STARTTIME"), "");
		String ENDTIME = StringHelper.obj2str(request.getParameter("ENDTIME"),
				"");
		String startyearId = StringHelper.obj2str(request
				.getParameter("startyearId"), "");
		String startmonthId = StringHelper.obj2str(request
				.getParameter("startmonthId"), "");
		String endyearId = StringHelper.obj2str(request
				.getParameter("endyearId"), "");
		String endmonthId = StringHelper.obj2str(request
				.getParameter("endmonthId"), "");
		String yearSelect = StringHelper.obj2str(request
				.getParameter("yearSelect"), "");
		String monthSelect = StringHelper.obj2str(request
				.getParameter("monthSelect"), "");
		// 取得用“;”连接的单位id
		String departIds = StringHelper.obj2str(request.getParameter("depId"),
				"");
		// 取得用“;”连接的单位名称
		String depName = StringHelper.obj2str(request.getParameter("depName"),
				"");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String departType = StringHelper.obj2str(request
				.getParameter("departType"), "");

		CommonUtility commonUtility = new CommonUtility();
		String xml = commonUtility.getXml(timeRadioType, alarmTotalType,
				STARTTIME, ENDTIME, startyearId, startmonthId, yearSelect,
				departIds, depName, endyearId, endmonthId, monthSelect, jgbh,
				jgid, departType);

		out.write(xml);
		out.close();
		return null;
	}

	/**
	 * 生成图表<br/>
	 * 生成图表的处理方法
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doBuildChart(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String title = StringHelper.obj2str(request.getParameter("title"), "");
		String xDesc = StringHelper.obj2str(request.getParameter("xDesc"), "");
		String yDesc = StringHelper.obj2str(request.getParameter("yDesc"), "");
		String chartType = StringHelper.obj2str(request
				.getParameter("chartType"), "");
		String chartDataStr = StringHelper.obj2str(request
				.getParameter("chartDataStr"), "");
		String[] forChartStrArray1 = chartDataStr.split(";");
		String[][] forChartStrArray = new String[forChartStrArray1.length][];
		for (int i = 0; i < forChartStrArray1.length; i++) {
			forChartStrArray[i] = forChartStrArray1[i].split(",");
		}
		String chartStrResult = BuildChart.doChart(title, xDesc, yDesc,
				chartType, forChartStrArray, request);
		out.write(chartStrResult);
		out.close();
		return null;
	}

	/**
	 * 新增续报<br/>
	 * 新增续报
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doInsertNewXb(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String insertId = StringHelper.obj2str(
				request.getParameter("insertId"), "");
		String PERSONWRITE = StringHelper.obj2str(request
				.getParameter("PERSONWRITE"), "");
		String REPORTTIME = StringHelper.obj2str(request
				.getParameter("REPORTTIME"), "");
		String XBNR = StringHelper.obj2str(request.getParameter("XBNR"), "");
		String deptname = StringHelper.obj2str(
				request.getParameter("deptname"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String DEATHPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("DEATHPERSONCOUNT"), "");
		String BRUISEPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("BRUISEPERSONCOUNT"), "");
		String roadNote = StringHelper.obj2str(
				request.getParameter("roadNote"), "");
		String KMVALUE = StringHelper.obj2str(request.getParameter("KMVALUE"),
				"");
		String MVALUE = StringHelper
				.obj2str(request.getParameter("MVALUE"), "");
		String roadname = StringHelper.obj2str(
				request.getParameter("roadname"), "");
		String roaddirection = StringHelper.obj2str(request
				.getParameter("roaddirection"), "");
		
		//附件id
		String fjid = StringHelper.obj2str(request
				.getParameter("fjid"), "");
		String alarmaddress = "";
		String XBFLG = "";
		String ZDXBFLG = "";

		// 大队续报时 支队，总队的续报查看标志都设置为“0”未查看
		if (jgbh.length() == 6) {
			XBFLG = "0";
			ZDXBFLG = "0";
			// 支队续报时 支队的续报查看标志都设置为“1”已查看 总队的续报查看标志都设置为“0”未查看
		} else if (jgbh.length() == 4) {
			XBFLG = "1";
			ZDXBFLG = "0";
		}

		StringBuffer dlfxSql = new StringBuffer("select f_get_dlfx(");
		dlfxSql.append("'").append(roadname).append("','")
				.append(roaddirection).append("')").append("from dual");
		Object[] obj = DBHandler.getLineResult(String.valueOf(dlfxSql));
		System.out.println("------------------------" + dlfxSql);
		String roadfx = "";
		for (Object object : obj) {
			if (object != null) {
				roadfx = (String) object;
			}
		}

		if (roadfx != "") {
			roadfx = roadfx.replace("->", "往");
			roadfx += "方向";
		}
		if (roadname != "") {
			alarmaddress += roadname;
		}
		if (roadfx != "") {
			alarmaddress += roadfx;
		}
		if (roadname != "") {
			if (KMVALUE != "") {
				alarmaddress += (KMVALUE + "k" + "+" + MVALUE + "米处");
			}
			if (roadNote != "") {
				if (roadname != "") {
					alarmaddress += ("(" + roadNote + ")");
				} else {
					alarmaddress += roadNote;
				}
			}
		} else {
			if (roadNote != "") {
				if (roadname != "") {
					alarmaddress += ("(" + roadNote + ")");
				} else {
					alarmaddress += roadNote;
				}
			}
			if (KMVALUE != "") {
				alarmaddress += (KMVALUE + "k" + "+" + MVALUE + "米处");
			}
		}

		StringBuffer sql = new StringBuffer(
				"insert into T_OA_ALARM_XB(ID,REPORTPERSON,REPORTTIME,REPORTINFO,DEPARTUNIT,XBFLG,ZDXBFLG,FJID) values('");
		sql.append(insertId).append("','");
		sql.append(PERSONWRITE).append("',to_date('");
		sql.append(REPORTTIME).append("','yyyy-mm-dd hh24:mi'),'");
		sql.append(XBNR).append("','");
		sql.append(deptname).append("','");
		sql.append(XBFLG).append("','");
		sql.append(ZDXBFLG).append("','");
		sql.append(fjid).append("')");
		System.out.println(sql);
		DBHandler.execute(String.valueOf(sql));

		StringBuffer updateSql = new StringBuffer(
				"update t_attemper_alarm  set");
		updateSql.append(" KMVALUE = '").append(KMVALUE).append("',");
		updateSql.append(" MVALUE = '").append(MVALUE).append("',");
		updateSql.append(" ALARMADDRESS = '").append(alarmaddress).append("',");
		updateSql.append(" ROADNAME = '").append(roadNote).append("'");
		updateSql.append(" where ALARMID = '").append(insertId).append("'");
		System.out.println(updateSql);
		DBHandler.execute(String.valueOf(updateSql));

		StringBuffer updateSqlZD = new StringBuffer(
				"update t_attemper_alarm_zd set");
		updateSqlZD.append(" KMVALUE = '").append(KMVALUE).append("',");
		updateSqlZD.append(" MVALUE = '").append(MVALUE).append("',");
		updateSqlZD.append(" ALARMADDRESS = '").append(alarmaddress).append(
				"',");
		updateSqlZD.append(" ROADNAME = '").append(roadNote).append("'");
		updateSqlZD.append(" where ALARMID = '").append(insertId).append("'");
		System.out.println(updateSqlZD);
		DBHandler.execute(String.valueOf(updateSqlZD));

		StringBuffer updateSql2 = new StringBuffer(
				"update t_attemper_accident set");
		updateSql2.append(" DEATHPERSONCOUNT = '").append(DEATHPERSONCOUNT)
				.append("',");
		updateSql2.append(" BRUISEPERSONCOUNT = '").append(BRUISEPERSONCOUNT)
				.append("'");
		updateSql2.append(" where ALARMID = '").append(insertId).append("'");
		System.out.println(updateSql2);
		DBHandler.execute(String.valueOf(updateSql2));

		StringBuffer updateSqlZD2 = new StringBuffer(
				"update t_attemper_accident_zd set");
		updateSqlZD2.append(" DEATHPERSONCOUNT = '").append(DEATHPERSONCOUNT)
				.append("',");
		updateSqlZD2.append(" BRUISEPERSONCOUNT = '").append(BRUISEPERSONCOUNT)
				.append("'");
		updateSqlZD2.append(" where ALARMID = '").append(insertId).append("'");
		System.out.println(updateSqlZD2);
		boolean isSuccess = DBHandler.execute(String.valueOf(updateSqlZD2));
		
		out.write(String.valueOf(isSuccess));
		out.close();
		return null;
	}

	/**
	 * 删除附件<br/>
	 * 删除附件
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteAttachmentFile(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String fileId = StringHelper
				.obj2str(request.getParameter("fileId"), "");
		String alarmIdDelete = StringHelper.obj2str(request
				.getParameter("alarmIdDelete"), "");

		boolean exucuteResult = true;
		String attachmentUrl = null;
		String sql = "select FJWZ from T_OA_ATTACHMENT where FJID = '" + fileId
				+ "'";
		try {
			// 附件路径的取得
			attachmentUrl = (String) ((DBHandler.getLineResult(sql))[0]);
		} catch (Exception exp) {
			out.write("附件路径取得错误： " + sql);
			exucuteResult = false;
		}
		if (attachmentUrl != null && attachmentUrl.length() > 0
				&& exucuteResult) {
			// 做成文件路径
			String realPath = request.getSession().getServletContext()
					.getRealPath("/");
			realPath = realPath.substring(0, realPath.length() - 1);
			int index = realPath.lastIndexOf("\\");
			realPath = realPath.substring(0, index + 1);
			String fileName = realPath + attachmentUrl;
			File file = new File(fileName);
			// 调用文件删除方法
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		} else {
			// 删除文件失败!
			out.write("删除文件失败!");
			exucuteResult = false;
		}
		StringBuffer sql2 = new StringBuffer(
				"delete from T_OA_ATTACHMENT where FJID = '" + fileId + "'");
		DBHandler.execute(String.valueOf(sql2));
		System.out.println("deleteAttachment:" + sql2);
		MaterialInfoDao cmd = new MaterialInfoDao();
		Object[][] obj3 = cmd.getAlarmAttachment(alarmIdDelete);
		String res = DataToXML.objArrayToXml(obj3);
		out.write(String.valueOf(res));
		out.close();
		return null;
	}

	/**
	 * 导出Excel<br/>
	 * 导出Excel
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doShowExcel(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String fileName = "Accident.xls";
		String titleName = "";
		String tabHeader = "警情编号,警情标题,填报单位,填报人,填报时间,状态";
		String sql = StringHelper
				.obj2str(request.getParameter("searchSql"), "");
		try {

			if (tabHeader == null || sql == null)
				return null;

			titleName = "重大交通事故信息表";
			String sheetName = "交通事故信息表";

			Object tabData[][] = DBHandler.getMultiResult(sql.toString());

			CommonUtility comUtl = new CommonUtility();
			if (tabData == null) {
				tabData = new Object[1][6];
			}
			comUtl.wirteToExcel(response, tabHeader, fileName, sheetName,
					tabData, titleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
