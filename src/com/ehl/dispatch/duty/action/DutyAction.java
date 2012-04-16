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
 * ֵ����Ϣaction
 * 
 * @author xulf
 * 
 */
public class DutyAction extends Controller {

	/**
	 * ֵ����Ϣ����
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
				.obj2str(request.getParameter("deptId"), ""); // ֵ�ಿ�Tid
		String person = StringHelper
				.obj2str(request.getParameter("person"), ""); // ֵ���ˆT
		String leader = StringHelper
				.obj2str(request.getParameter("leader"), ""); // ֵ���I��
		//Modified by Liuwx 2011-8-10
		String mobilePhone = StringHelper.obj2str(request.getParameter("mobilePhone"),"");//ֵ���ֻ�
		
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
	 * ֵ����Ϣ����
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

		String id = StringHelper.obj2str(request.getParameter("id"), ""); // ֵ��id
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), ""); // ֵ�ಿ�Tid
		String person = StringHelper
				.obj2str(request.getParameter("person"), ""); // ֵ���ˆT
		String leader = StringHelper
				.obj2str(request.getParameter("leader"), ""); // ֵ���I��
		//Modified by Liuwx 2011-8-10
		String mobilePhone = StringHelper
		.obj2str(request.getParameter("mobilePhone"), ""); // ֵ���ֻ�
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
	 * ֵ����Ϣ����
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
				.obj2str(request.getParameter("deptId"), ""); // ֵ�ಿ�Tid
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
	 * �޸�ֵ��绰
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
				.obj2str(request.getParameter("jgid"), ""); // ֵ�ಿ�Tid
		String tel = StringHelper
				.obj2str(request.getParameter("tel"), ""); // ֵ��绰
		DutyDao dao = new DutyDao();
		boolean flag = dao.ModifyTel(jgid, tel);
		out.write(flag == true ? "true" : "false");
		out.close();
		return null;
	}
	/**
	 * ���ݻ���id�õ�ֵ��绰
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
				.obj2str(request.getParameter("jgid"), ""); // ֵ�ಿ�Tid
		DutyDao dao = new DutyDao();
		Object result = dao.geTelByJgid(jgid);
		String tel = StringHelper.obj2str(result, "");
		out.write(tel);
		out.close();
		return null;
	}
	/**
	 * �õ�����ֵ��绰
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
		.obj2str(request.getParameter("jgid"), ""); // ֵ�ಿ�Tid
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
	 * ����Excel<br/>
	 * ����Excel
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
		String fileName = "ֵ��绰��Ϣ";
		String titleName = "";
		String tabHeader = "���,��������,ֵ��绰";
		
		String sql = StringHelper.obj2str(request.getParameter("searchSql"), "");
		sql=com.ehl.base.util.StringUtil.convertSql(sql);

		try {
			
			if (tabHeader == null || sql == null)
				return null;
			
			titleName = "ֵ��绰��Ϣ";
			String sheetName = "ֵ��绰��Ϣ��";
			
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
