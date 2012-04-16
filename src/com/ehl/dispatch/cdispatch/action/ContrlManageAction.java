/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.output.SaveAsExcelCore;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.ContrlManageDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.cdispatch.util.LcbPtMisUtil;

/**
 * 道路管理控制类
 * @author wkz
 * @date 2009-1-16
 *
 */
public class ContrlManageAction extends Controller{
	/**
	 * 编辑管制信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyContrlInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request.getParameter("insrtOrUpdt"), 0);
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		String ROADID = StringHelper.obj2str(request.getParameter("ROADID"),"");
		String EVENTSOURCE = StringHelper.obj2str(request.getParameter("EVENTSOURCE"),"");
		String EVENTTYPE = StringHelper.obj2str(request.getParameter("EVENTTYPE"),"");
		String ALARMUNIT = StringHelper.obj2str(request.getParameter("ALARMUNIT"),"");
		String TITLE = StringHelper.obj2str(request.getParameter("TITLE"),"");
		String ALARMREGIONID = StringHelper.obj2str(request.getParameter("ALARMREGIONID"),"");
		String ALARMREGION = StringHelper.obj2str(request.getParameter("ALARMREGION"),"");
		String ROADNAME = StringHelper.obj2str(request.getParameter("ROADNAME"),"");
		String KMVALUE = StringHelper.obj2str(request.getParameter("KMVALUE"),"");
		String EndKMVALUE = StringHelper.obj2str(request.getParameter("ENDKMVALUE"),"");
		String CaseHappenTime = StringHelper.obj2str(request.getParameter("CaseHappenTime"),"");
		String CaseEndTime = StringHelper.obj2str(request.getParameter("CaseEndTime"),"");
		String REPORTUNIT = StringHelper.obj2str(request.getParameter("REPORTUNIT"),"");
		String REPORTPERSON = StringHelper.obj2str(request.getParameter("REPORTPERSON"),"");
		String REPORTTIME = StringHelper.obj2str(request.getParameter("REPORTTIME"),"");
		String PLAN = StringHelper.obj2str(request.getParameter("PLAN"),"");
		String ROADDIRECTION = StringHelper.obj2str(request.getParameter("RADIOTYPE"),"");
		String MVALUE = StringHelper.obj2str(request.getParameter("MVALUE"),"");
		String EndMVALUE = StringHelper.obj2str(request.getParameter("ENDMVALUE"),"");
		HashMap hm = new HashMap();
		hm.put("ALARMID", ALARMID);
		hm.put("ROADID", ROADID);
		hm.put("EVENTSOURCE", EVENTSOURCE);
		hm.put("EVENTTYPE", EVENTTYPE);
		hm.put("ALARMUNIT", ALARMUNIT);
		hm.put("TITLE", TITLE);
		hm.put("ALARMREGIONID", ALARMREGIONID);
		hm.put("ALARMREGION", ALARMREGION);
		hm.put("ROADNAME", ROADNAME);
		hm.put("KMVALUE", KMVALUE);
		hm.put("EndKMVALUE", EndKMVALUE);
		hm.put("MVALUE", MVALUE);
		hm.put("EndMVALUE", EndMVALUE);
		hm.put("CaseHappenTime", CaseHappenTime);
		hm.put("CaseEndTime", CaseEndTime);
		hm.put("REPORTUNIT", REPORTUNIT);
		hm.put("REPORTPERSON", REPORTPERSON);
		hm.put("REPORTTIME", REPORTTIME);
		hm.put("PLAN", PLAN);
		hm.put("ROADDIRECTION", ROADDIRECTION);
		// ('570003':"管制中" '570004':"管制结束")
		hm.put("EVENTSTATE", "570003");
		boolean flag = false;
		String[] XYvalue = LcbPtMisUtil.getXYvalue(ROADID, KMVALUE, MVALUE);
		hm.put("Xvalue", XYvalue[0]);
		hm.put("Yvalue", XYvalue[1]);

		ContrlManageDao cmd = new ContrlManageDao();
		if(insertOrUpdate==0) {
			flag = cmd.addContrlInfo(hm);
			if(flag) {
				out.write("新增管制信息成功！");
			}else {
				out.write("新增管制信息失败！");
			}
		} else {
			flag = cmd.editContrlInfo(hm);
			if(flag) {
				out.write("修改管制信息成功！");
			}else {
				out.write("修改管制信息失败！");
			}
		}
		out.close();
		return null;
	} 
	/**
	 * 删除管制信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteContrlInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		ContrlManageDao cmd = new ContrlManageDao();
		boolean flag = cmd.delteContrlInfo(ALARMID);
		if(flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
		out.close();
		return null;
	}
	
	/**
	 * 查询管制信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetContrlInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmid = StringHelper.obj2str(request.getParameter("alarmid"),"");
		ContrlManageDao cmd = new ContrlManageDao();
		Object[] obj = cmd.getContrlInfo(alarmid);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}	
	
	/**
	 * 导出Excel<br/>
	 * 导出Excel
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
		String fileName = "交通管制信息";
		String titleName = "";
		String tabHeader = "管制编号,录入单位,道路名称,路段名称,方向,管制开始时间,管制结束时间,管制状态";
		String sql = StringHelper.obj2str(request.getParameter("searchSql"), "");
		try {
			
			if (tabHeader == null || sql == null)
				return null;
			
			titleName = "交通管制信息表";
			String sheetName = "交通管制信息表";
			
			Object tabData[][] = DBHandler.getMultiResult(sql.toString());
			Object[][] data = null;
			if(tabData != null) {
				data = new Object[tabData.length][8];
				for(int i=0; i<tabData.length; i++) {
					for(int j=0; j<8; j++) {
						data[i][j] = tabData[i][j];
					}
					if("0".equals(tabData[i][3])) {
						data[i][4] = tabData[i][8] +"->"+tabData[i][9];
					} else {
						data[i][4] = tabData[i][9] +"->"+tabData[i][8];
					}
				}
			} else {
				data = new Object[1][8];
			}
			CommonUtility comUtility = new CommonUtility();
			comUtility.wirteToExcel(response, tabHeader, fileName, sheetName, data, titleName);
//			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
//			saveAsExcelCore.setTabHeader(tabHeader);
//			fileName = new String(fileName.getBytes(), "ISO8859_1");
//			saveAsExcelCore.setFileName(fileName);
//			saveAsExcelCore.setTitleName(titleName);
//			saveAsExcelCore.wirteToExcel(sheetName, titleName, tabHeader, tabData, response);
		} catch(Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 查询管制信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetRoadWayInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String roadId = StringHelper.obj2str(request.getParameter("roadId"),"");
		ContrlManageDao cmd = new ContrlManageDao();
		Object[] obj = cmd.getRoadWayInfo(roadId);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}

}