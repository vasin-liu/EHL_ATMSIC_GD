package com.ehl.dispatch.duty.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.output.SaveAsExcelCore;
import com.ehl.base.util.StringUtil;
import com.ehl.dispatch.duty.dao.DutyDao;
import com.ehl.dispatch.xcbk.dao.XcbkDao;
import com.ehl.sm.base.Constant;
/**
 * 值班信息action
 * 
 * @author xulf
 * 
 */
public class DutyAction extends Controller {

	/**
	 * 值班信息插入
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doInsert(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), ""); // 值班部門id
		String person = StringHelper
				.obj2str(request.getParameter("person"), ""); // 值班人員
		String leader = StringHelper
				.obj2str(request.getParameter("leader"), ""); // 值班領導
		//Modified by Liuwx 2011-8-10
		String mobilePhone = StringHelper.obj2str(request.getParameter("mobilePhone"),"");//值班手机
		
		Object[] duty = { deptId, leader, person,mobilePhone };
		//Modification finished
		DutyDao dao = new DutyDao();
		boolean flag = dao.insert(duty);
		if(flag == true){
			HttpSession session = request.getSession(false);
			if(session != null){
				session.setAttribute(Constant.ZBRXM_VAR, person);
				session.setAttribute(Constant.ZBLDXM_VAR, leader);
			}
		}
		out.write(flag == true ? "true" : "false");
		out.close();
		return null;
	}

	/**
	 * 值班信息更新
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdate(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String id = StringHelper.obj2str(request.getParameter("id"), ""); // 值班id
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), ""); // 值班部門id
		String person = StringHelper
				.obj2str(request.getParameter("person"), ""); // 值班人員
		String leader = StringHelper
				.obj2str(request.getParameter("leader"), ""); // 值班領導
		//Modified by Liuwx 2011-8-10
		String mobilePhone = StringHelper
		.obj2str(request.getParameter("mobilePhone"), ""); // 值班手机
		//Modification finished
		DutyDao dao = new DutyDao();
		//Modified by Liuwx 2011-8-10
		Object[] duty = { id, deptId, leader, person,mobilePhone };
		//Modification finished
		
		boolean flag = dao.update(duty);
		if(flag == true){
			HttpSession session = request.getSession(false);
			if(session != null){
				session.setAttribute(Constant.ZBRXM_VAR, person);
				session.setAttribute(Constant.ZBLDXM_VAR, leader);
			}
		}
		out.write(flag == true ? "true" : "false");
		out.close();
		return null;
	}

	/**
	 * 值班信息插入
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetByDeptId(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), ""); // 值班部門id
		DutyDao dao = new DutyDao();
		Object[] result = dao.getByDeptId(deptId);
		StringBuffer xmlData = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rows>\n");
		if (result == null) {
			xmlData.append("<reslt>null</result>\n");
		} else {
			xmlData.append("<row>\n");
			xmlData.append(" id='" + result[0] + "'");
			xmlData.append(" deptId='" + result[1] + "'");
			xmlData.append(" leader='" + result[2] + "'");
			xmlData.append(" person='" + result[3] + "'");
			xmlData.append(" time='" + result[4] + "'");
			//Modified by Liuwx 2011-8-10
			xmlData.append(" mobilePhone='" + result[5] + "'");
			//Modification finished
			xmlData.append("</row>\n");
			//Modify by Xiayx 2011-10-27
			HttpSession session = request.getSession(false);
			if(session != null){
				session.setAttribute(Constant.ZBRXM_VAR, result[3]);
				session.setAttribute(Constant.ZBLDXM_VAR, result[2]);
			}
			//Modification finished
		}
		xmlData.append("</rows>\n");
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	/**
	 * 修改值班电话
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyTel(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String jgid = StringHelper
				.obj2str(request.getParameter("jgid"), ""); // 值班部門id
		String tel = StringHelper
				.obj2str(request.getParameter("tel"), ""); // 值班电话
		DutyDao dao = new DutyDao();
		boolean flag = dao.ModifyTel(jgid, tel);
		out.write(flag == true ? "true" : "false");
		out.close();
		return null;
	}
	/**
	 * 根据机构id得到值班电话
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTel(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String jgid = StringHelper
				.obj2str(request.getParameter("jgid"), ""); // 值班部門id
		DutyDao dao = new DutyDao();
		Object result = dao.geTelByJgid(jgid);
		String tel = StringHelper.obj2str(result, "");
		out.write(tel);
		out.close();
		return null;
	}
	/**
	 * 得到所有值班电话
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTelAll(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String jgid = StringHelper
		.obj2str(request.getParameter("jgid"), ""); // 值班部門id
		DutyDao dao = new DutyDao();
		Object[][] result = dao.getTelAll(jgid);
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rows>\n");
		if(result!=null){
			for(int i=0;i<result.length;i++){
				xmlData.append("<row id='"+i+"'>");
				for(int j=0;j<result[i].length;j++){
					xmlData.append("<cell>" + StringHelper.obj2str(result[i][j],"")+ "</cell>");
				}
				xmlData.append("</row>\n");
			}
		}
		xmlData.append("</rows>\n");
		out.write(xmlData.toString());
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
		String fileName = "值班电话信息";
		String titleName = "";
		String tabHeader = "编号,部门名称,值班电话";
		
		String sql = StringHelper.obj2str(request.getParameter("searchSql"), "");
		sql=com.ehl.base.util.StringUtil.convertSql(sql);

		try {
			
			if (tabHeader == null || sql == null)
				return null;
			
			titleName = "值班电话信息";
			String sheetName = "值班电话信息表";
			
			Object tabData[][] = DBHandler.getMultiResult(sql.toString());
			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
			saveAsExcelCore.setTabHeader(tabHeader);
			fileName = new String(fileName.getBytes(), "ISO8859_1");
			saveAsExcelCore.setFileName(fileName);
			saveAsExcelCore.setTitleName(titleName);
			saveAsExcelCore.wirteToExcel(sheetName, titleName, tabHeader, tabData, response);
		} catch(Exception e) {
			
		}
		return null;
	}
}
