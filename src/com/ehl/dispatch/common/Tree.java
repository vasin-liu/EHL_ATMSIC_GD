package com.ehl.dispatch.common;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.common.Setting;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.base.Constant;
import com.ehl.sm.pcs.DepartmentManage;
import com.ehl.tira.util.Sql;
import com.ehl.tira.util.XML;

public class Tree extends Controller {
	
	public Logger logger = Logger.getLogger(Tree.class);
	
	
	/**
	 * <pre>
	 * ��ȡ�û�������
	 * dttype(dept tree type)���������ͣ�2λ����
	 * null����ʾ���л���
	 * 0������ʾ
	 * 1����ʾһ��
	 * 2����ʾ�༶
	 * 1λ�����ڵ�
	 * 2λ���ӽڵ�
	 * Ĭ����ʾ��ǰ����
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetDeptTree(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String xml = null;
		String jgid = request.getParameter("jgid");
		if (jgid == null) {
			jgid = DepartmentManage.getDeptInfo(request, "0");
		}
		String dttype = request.getParameter("dttype");
		String sql = getDeptTreeSql(jgid, dttype);
		
		Object[][] data = FlowUtil.readMilte(sql, logger, "��ȡ�������б�");
		if(data != null){
			String zdxml = "", zidxml = "", ddxml = "",deptRemark="</dept>\n";
			String jglx;
			for (int i = 0; i < data.length; i++) {
				jglx = data[i][3]+"";
				if(jglx.equals("0")){
					if(!zdxml.equals("")){
						zdxml += zidxml; 
						zdxml += deptRemark;
						zidxml = "";
					}
					zdxml += "\n"+getDeptXml(data[i])+"\n";
				}else if(jglx.equals("1")){
					if(!zidxml.equals("")){
						zidxml += ddxml;
						zidxml += deptRemark;
						ddxml = "";
					}
					zidxml += getDeptXml(data[i])+"\n";
				}else if(jglx.equals("2")){
					ddxml += getDeptXml(data[i])+deptRemark;
				}
			}
			xml = ddxml;
			if(!zidxml.equals("")){
				zidxml += ddxml;
				zidxml += deptRemark;
				xml = zidxml;
			}
			if(!zdxml.equals("")){
				zdxml += zidxml;
				zdxml += deptRemark;
				xml = zdxml;
			}
			xml = XML.getXML("tree", xml);
		}
		System.out.println("depttreeXML:\n"+xml);
		out.write(xml);
		out.flush();
		out.close();
		return null;
	 }
	
	/**
	 * @param udcode
	 * @param dttype
	 * @return
	 */
	private String getDeptTreeSql(String udcode, String dttype) {
		String sql = null;
		String whereDept = null;
		if (dttype != null) {
			String whereParent = null;
			int ptype = Integer.parseInt(dttype.substring(0,1));
			switch (ptype) {
				case 1:
					String parent = Constant.getParent(udcode);
					if(parent != null){
						whereParent = "jgid='" + parent + "'";
					}
					break;
				case 2:
					String parents = Constant.getParents(udcode);
					if(parents != null){
						whereParent = "instr('" + parents + "',jgid)!=0";
					}
					break;
			}
			if (whereParent != null) {
				whereParent = "(" + whereParent + ")";
				whereDept = whereParent;
			}
			
			String whereChild = null;
			int level = Constant.getLevel(udcode);
			int ctype = Integer.parseInt(dttype.substring(1,2));
			if(ctype == 0){
				whereChild = "jgid='"+udcode+"'";
			}else if(ctype ==1 || ctype == 2){
				whereChild = "jgid like '" + udcode.substring(0, level * 2) + "%'";
				if(ctype == 1){
					level++;
					whereChild += " and substr(jgid," + (level * 2 + 1) + "," + Constant.JGDJBZCD + ")='" + Constant.JGDJBZ + "'";
				}
			}
			if(whereChild != null){
				whereChild = "("+whereChild+")";
				if(whereDept != null){
					whereDept += " or " + whereChild;
					whereDept = "("+whereDept+")";
				}else{
					whereDept = whereChild;
				}
			}
		}
		
		String colStr = "jgid,jgmc,jgccbm,jglx";
		String whereStr = "jgid<=445331000000 and length(jgid)=12 and substr(jgid,7)='000000'";//�ų��ơ��ҡ��ж�
		if(whereDept != null){
			whereStr += " and " + whereDept;
		}
		sql = Sql.select("t_sys_department", colStr, whereStr, null, "jgccbm,jgid");
		return sql;
	}
	
	
	
	public String getDeptXml(Object[] dept){
		String deptXml = null;
		if(dept != null){
			deptXml = "<dept id='" + dept[0] + "' text='" + dept[1] + "' jgccbm='" + dept[2] + "' >";
		}
		return deptXml;
	}
	
	

	private String xmlStr = "";
	
	
	/**
	 * ���ɻ����ܶ���
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSetAllTree(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");// ָ�������XML��ʽ
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");

		// String deptstr=Setting.getString(divId); //�������ļ�ȡ��Ҫ��ʾ�Ļ���
		String deptstr = getDeptRes(state); // ���Ҫ��ʾ�Ļ���
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //��ǰ�û���������
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //��ǰ�û�������α���
		try {
			Object[][] res = null;
			String sql = "";
			xmlStr += "<?xml version='1.0' encoding='UTF-8'?>\n";
			xmlStr += "<tree>\n";

			sql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where  jgid like '"
					+ deptId + "%' order by jgccbm";

			res = DBHandler.getMultiResult(sql);

			xmlStr += "<dept id='"+res[0][0]+"' text='" + res[0][1]
					+ "' jgccbm='" + res[0][2] + "'>\n";
			setXml(res[0], deptstr);
			xmlStr += "</dept>\n";

			// ���ͬ�����ڵ�
			Object[][] obj = getBaseRoot(res);
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {
					xmlStr += "<dept id='" + obj[i][0] + "' text='" + obj[i][1]
							+ "' jgccbm='" + obj[i][2] + "'>\n";
					setXml(obj[i], deptstr);
					xmlStr += "</dept>\n";
				}
			}
			xmlStr += "</tree>\n";
			System.out.println(xmlStr);
			out.write(xmlStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
			return null;
		}

	}
	public ActionForward doSetTree(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");// ָ�������XML��ʽ
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");
		
		//Modified by Liuwx 2011-06-20
		int isShowSuperior = StringHelper.obj2int(request.getParameter("isShowSuperior"),0);

		// String deptstr=Setting.getString(divId); //�������ļ�ȡ��Ҫ��ʾ�Ļ���
		String deptstr = getDeptRes(state); // ���Ҫ��ʾ�Ļ���
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //��ǰ�û���������
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //��ǰ�û�������α���
		try {
			Object[][] res = null;
			String sql = "";
			xmlStr += "<?xml version='1.0' encoding='UTF-8'?>\n";
			xmlStr += "<tree>\n";

//			sql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where  jgid like '"
//					+ deptId + "%' order by jgccbm";
//
//			res = DBHandler.getMultiResult(sql);
//
//			xmlStr += "<dept  text='" + res[0][1]
//					+ "' jgccbm='" + res[0][2] + "'>\n";
//			setXml(res[0], deptstr);
//			xmlStr += "</dept>\n";
//
//			// ���ͬ�����ڵ�
//			Object[][] obj = getBaseRoot(res);
//			if (obj != null && obj.length > 0) {
//				for (int i = 0; i < obj.length; i++) {
//					xmlStr += "<dept id='" + obj[i][0] + "' text='" + obj[i][1]
//							+ "' jgccbm='" + obj[i][2] + "'>\n";
//					setXml(obj[i], deptstr);
//					xmlStr += "</dept>\n";
//				}
//			}

			setDeptTreeXml(deptId, isShowSuperior);
			xmlStr += "</tree>\n";
		//Modification finished
			System.out.println(xmlStr);
			out.write(xmlStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
			return null;
		}

	}
	/**
	 * ���ɻ���֧����
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSetZhiTree(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");// ָ�������XML��ʽ
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");

		// String deptstr=Setting.getString(divId); //�������ļ�ȡ��Ҫ��ʾ�Ļ���
		String deptstr = getDeptRes(state); // ���Ҫ��ʾ�Ļ���
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //��ǰ�û���������
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //��ǰ�û�������α���
		try {
			String xmlStr="";
			Object[][] res = null;
			String sql = "";
			xmlStr += "<?xml version='1.0' encoding='UTF-8'?>\n";
			xmlStr += "<tree>\n";

			sql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where  jgid ='440000000000'";

			 res = DBHandler.getMultiResult(sql);

			xmlStr += "<dept id='" + res[0][0] + "' text='" + res[0][1]
					+ "' jgccbm='" + res[0][2] + "'>\n";
			Object[][]  objThis = DBHandler.getMultiResult("select jgid,jgmc,jgccbm,jglx from t_sys_department where jgid = '"+deptId+"'");
			xmlStr += "<dept text='" + objThis[0][1]
			                                     					+ "' jgccbm='" + objThis[0][2] + "'>\n";
			Object[][]  objChild = DBHandler.getMultiResult("select jgid,jgmc,jgccbm,jglx from t_sys_department where jgccbm like '"+objThis[0][2]+"%'");
			for(int j=0;j<objChild.length;j++){
				if((objThis[0][2].toString()).equals(objChild[j][2])){
						continue;
				}
					xmlStr += "<dept id='" + objChild[j][0] + "' text='" + objChild[j][1]+ "' jgccbm='" + objChild[j][2] + "'>\n";
					xmlStr+="</dept>\n";
			}
				xmlStr+="</dept>\n";
			// ���ͬ�����ڵ�
			Object[][] obj = DBHandler.getMultiResult("select jgid,jgmc,jgccbm,jglx from t_sys_department where length(jgccbm) =4");
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {
					if((obj[i][0].toString()).equals(deptId)){
						continue;
					}
					xmlStr += "<dept id='" + obj[i][0] + "' text='" + obj[i][1]
							+ "' jgccbm='" + obj[i][2] + "'>\n";
					setXml(obj[i], deptstr);
					xmlStr += "</dept>\n";
				}
			}
			xmlStr += "</dept>\n";
			xmlStr += "</tree>\n";
			System.out.println(xmlStr);
			out.write(xmlStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
			return null;
		}

	}
	/**
	 * ���ɻ��������
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSetDaTree(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");// ָ�������XML��ʽ
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");

		// String deptstr=Setting.getString(divId); //�������ļ�ȡ��Ҫ��ʾ�Ļ���
		String deptstr = getDeptRes(state); // ���Ҫ��ʾ�Ļ���
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //��ǰ�û���������
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //��ǰ�û�������α���
		try {
			Object[][] res = null;
			String jgid=deptId.substring(0,4);
			String xmlStr="";
			String sql = "";
			xmlStr += "<?xml version='1.0' encoding='UTF-8'?>\n";
			xmlStr += "<tree>\n";

			sql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where  jgid like '%"+jgid+"%' order by jgid";

			res = DBHandler.getMultiResult(sql);
			for(int i=0;i<res.length;i++){
				if(StringHelper.obj2str(res[i][0]).equals(deptId)){
					continue;
				}
				xmlStr += "<dept id='" + res[i][0] + "' text='" + res[i][1]+ "' jgccbm='" + res[i][2] + "'>\n";
			
			if((res[i][2].toString()).length()==6){	
				xmlStr+="</dept>\n";
			}
			   
			}
			// ���ͬ�����ڵ�
			 xmlStr += "</dept>\n";
			xmlStr += "</tree>\n";
			System.out.println(xmlStr);
			out.write(xmlStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
			return null;
		}

	}

	/**
	 * ��ȡ���ŵ���������
	 * 
	 * @param deptId
	 * @param deptstr
	 * @return
	 */
	private Object[][] childsDept(String deptId, String deptstr) {
		String ccsql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where jgid='"
				+ deptId + "'";
		Object[] deptRes = DBHandler.getLineResult(ccsql);
		String jgccbm = deptRes[2].toString();
		int ccbmLength = jgccbm.length();

		String sql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where jgccbm like '"
				+ jgccbm + "%' and length(jgccbm)=(" + ccbmLength + "+2) ";

		if (deptstr != null && !deptstr.equals("null") && !deptstr.equals("")) {
			sql += " and jgid in (" + deptstr + ") ";
		}
		sql += " order by jgccbm";

		Object[][] res = DBHandler.getMultiResult(sql);

		return res;
	}

	
	/**
	 * ���ɲ�����xml�ַ���
	 * @author Liuwx
	 * @param dept
	 * @param deptstr
	 * @return
	 */
	private String setDeptTreeXml(String deptId, int isShowAll) throws Exception {
		String ccsql = "select jgid,jgmc,jgccbm,jglx from t_sys_department where jgid='" + deptId + "'";
		int incZongDdui=0,incZhiDui=0;
		
		Object[] deptRes = DBHandler.getLineResult(ccsql);
		String jgccbm = deptRes[2].toString();
//		int ccbmLength = jgccbm.length();

		String showSuperiorSQL = "";
		
		if (isShowAll == 1) {
			showSuperiorSQL = " or jgccbm='" + jgccbm.substring(0, jgccbm.length()-2)+"' or jgccbm='"
				+ jgccbm.substring(0, jgccbm.length()-4) + "' ";
		}
		
		String sql = "select jgid,"
			+"decode(length(jgccbm),2,jgmc) zd,"
			+ "decode(length(jgccbm),4,jgmc) zd," 
			+ "decode(length(jgccbm),6,jgmc) dd," 
			+ "jgccbm,jglx from t_sys_department where jgccbm like '"
			+ jgccbm 
			+ "%' "
			+ showSuperiorSQL
			+ " order by jgccbm";
		Object[][] result = DBHandler.getMultiResult(sql);
		
		for (int i = 0; i < result.length; i++) {
			if (result[i][1]!=null) {
				xmlStr += "<dept  text='" + result[i][1] + "' jgccbm='" + result[i][4] + "'>\n";
			}
			if (result[i][2]!=null && i<2) {
				xmlStr += "<dept id='" + result[i][0] + "' text='" + result[i][2]	+ "' jgccbm='" + result[i][4] + "'>\n";
			}
			else if (result[i][2]!=null && i>3) {
				xmlStr += "</dept>\n";
				xmlStr += "<dept id='" + result[i][0] + "' text='" + result[i][2]	+ "' jgccbm='" + result[i][4] + "'>\n";
			}
			if (result[i][3]!=null) {
				xmlStr += "<dept id='" + result[i][0] + "' text='" + result[i][3]	+ "' jgccbm='" + result[i][4] + "'>\n";
				xmlStr += "</dept>\n";
			}
			//=================
			
			if (result[i][1] !=null) {
				++incZongDdui;
			}
			if (result[i][2] !=null) {
				++incZhiDui;
			}
			if (i == result.length - 1  && incZongDdui>0) {
				xmlStr += "</dept>\n";
				
			}
			if ( i == result.length - 1 && incZhiDui >0) {
				xmlStr += "</dept>\n";
			}
		}
//		xmlStr += "</dept>\n";
		return xmlStr;
	}

	/**
	 * ����xml�ַ���
	 * 
	 * @param dept
	 * @param deptstr
	 * @return
	 */
	private String setXml(Object[] dept, String deptstr) {


		Object[][] res = childsDept(dept[0].toString(), deptstr);
		if (res != null) {
			for (int i = 0; i < res.length; i++) {
				xmlStr += "<dept id='" + res[i][0] + "' text='" + res[i][1]
						+ "' jgccbm='" + res[i][2] + "'>\n";

				setXml(res[i], deptstr);
				xmlStr += "</dept>\n";
			}
		}
		return xmlStr;
	}
	
	
	/**
	 * ������Աxml
	 * 
	 * @param persons
	 * @param dept
	 * @return
	 */
	private String setPersonXml(Object[][] persons, Object[] dept, boolean tag) {
		String personXml = "";
		if (persons != null) {
			if (tag) {
				for (int i = 0; i < persons.length; i++) {
					personXml += "<person id='" + persons[i][0] + "' name='"
							+ persons[i][1] + "' deptId='0_" + dept[0]
							+ "' ccbm='0_" + dept[2] + "'>\n";
					personXml += "</person>\n";
				}
			} else {
				for (int i = 0; i < persons.length; i++) {
					personXml += "<person id='" + persons[i][0] + "' name='"
							+ persons[i][1] + "' deptId='" + dept[0]
							+ "' ccbm='" + dept[2] + "'>\n";
					personXml += "</person>\n";
				}
			}

		}
		return personXml;

	}

	/**
	 * ��ȡ��������Ա
	 * 
	 * @param deptId
	 * @return
	 */
	private Object[][] getPersonByDept(String deptId, String ccbm) {
		Object[][] res = null;
		String sql = "";

		if (ccbm.equals("01")) {
			return res;
		} else if (ccbm.equals("0_01")) {
			sql = "select ryid,xm from t_sys_person where ssjg='" + deptId
					+ "' and xrzw in ('01','02','05','06')";
		} else {
			sql = "select ryid,xm from t_sys_person where ssjg='" + deptId
					+ "'";
		}
		res = DBHandler.getMultiResult(sql);
		return res;
	}

	/**
	 * ��������״̬�õ���Ҫ�·��ĵ�λ����
	 * 
	 * @param state
	 * @return
	 */
	private String getDeptRes(String state) {
		String deptStr = null;
		try {
			// �·�ָ�ӿ�
			if (state.equals("000000") || state.equals("000001")) {
				deptStr = Setting.getString("ZHKJSR");
				return deptStr;
			}
			// ָ�ӿƸ�ָ�Ӵ�
			else if (state.equals("000004")) {
				deptStr = Setting.getString("ZHCJSR");
				return deptStr;
			}
			// ָ�Ӵ�����ҵ�񴦺;��쵼
			else if (state.equals("000007") || state.equals("000073")
					|| state.equals("000076")) {
				deptStr = Setting.getString("JLDJSR") + ","
						+ Setting.getString("YWCJSR"); //
				return deptStr;
			}
			// ҵ�񴦷���֧�ӣ�ָ�Ӵ��;��쵼
			else if (state.equals("000064") || state.equals("000067")
					|| state.equals("000058")) {
				deptStr = Setting.getString("JLDJSR") + ","
						+ Setting.getString("ZHCJSR") + ","
						+ Setting.getString("ZDJGID") + ","
						+ Setting.getString("YWCCBR"); //
				return deptStr;
			}
			// ҵ�񴦷���֧��
			else if (state.equals("000085")) {
				deptStr = Setting.getString("ZDJGID"); //
				return deptStr;
			}
			// ���쵼����֧�ӣ�ָ�Ӵ���ҵ��
			else if (state.equals("000022") || state.equals("000070")) {
				deptStr = Setting.getString("YWCJSR") + ","
						+ Setting.getString("ZHCJSR") + ","
						+ Setting.getString("ZDJGID"); //
				return deptStr;
			}
			// // ֧�Ӹ��а��ˣ����
			// else
			// if(state.equals("000034")||state.equals("000037")||state.equals("000082")){
			// deptStr=Setting.getString("YWCCBR"); //
			// return deptStr;
			// }
			// ֧�Ӹ����
			else if (state.equals("000034") || state.equals("000037")
					|| state.equals("000082")) {
				deptStr = ""; //
				return deptStr;
			}
			// ��Ӹ�֧��
			else if (state.equals("000043")) {
				deptStr = Setting.getString("ZDJGID"); //
				return deptStr;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return deptStr;
		}
	}

	/**
	 * ������״̬����Ա����Ա���뵽��Ӧ�������ֶ��У�ͬʱ�޸�״̬��
	 * 
	 * @param personStr
	 * @param state
	 * @return
	 */
	public String getUpdateSql(String personStr, String state) {
		String sqlStr = "";
		try {
			String sendMsg = getSendType(personStr);// �·���־

			// ֵ��Ա�·�ָ�ӿ�
			if (state.equals("000001")) {
				sqlStr = ",ZHKJSR='" + personStr + "',LZZT='000004'";
				return sqlStr;
			}
			// �����ָ�ӿ��·�ָ�Ӵ�
			else if (state.equals("000004")) {
				sqlStr = ",ZHCJSR='" + personStr + "',LZZT='000007'";
				return sqlStr;
			}
			// �����ָ�Ӵ��·�ҵ��
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("YWCJSR")) {
				sqlStr = ",YWCJSR='" + personStr + "',LZZT='000067'";
				return sqlStr;
			}
			// �����ָ�Ӵ������쵼
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("JLDJSR")) {
				sqlStr = ",JLDJSR='" + personStr + "',LZZT='000070'";
				return sqlStr;
			}
			// ҵ�񴦸�ָ�Ӵ�
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZHCJSR")) {
				sqlStr = ",ZHCJSR='" + personStr + "',LZZT='000073'";
				return sqlStr;
			}
			// ҵ�񴦸����쵼
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("JLDJSR")) {
				sqlStr = ",JLDJSR='" + personStr + "',LZZT='000022'";
				return sqlStr;
			}
			// ���쵼��ҵ��
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("YWCJSR")) {
				sqlStr = ",YWCJSR='" + personStr + "',LZZT='000064'";
				return sqlStr;
			}
			// ���쵼��ָ�Ӵ�
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZHCJSR")) {
				sqlStr = ",ZHCJSR='" + personStr + "',LZZT='000076'";
				return sqlStr;
			}
			// ���쵼��֧��
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZDJGID")) {
				sqlStr = ",ZDJGID='" + personStr + "',LZZT='000079'";
				return sqlStr;
			}
			// ҵ�񴦸�֧��
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZDJGID")) {
				sqlStr = ",ZDJGID='" + personStr + "',LZZT='000031'";
				return sqlStr;
			}
			// ֧�Ӹ��ܶӣ����
			else if ((state.equals("000049") || state.equals("000034")
					|| state.equals("000037") || state.equals("000082") || state
					.equals("000031"))) {
				// ֧�Ӹ����
				if (sendMsg.equals("DDJGID")) {
					sqlStr = ",DDJGID='" + personStr + "',LZZT='000040'";
					return sqlStr;
				} else { // ֧�Ӹ��ܶ�
					sqlStr = ",LZZT='000052'";
					return sqlStr;
				}
			}
			// ��Ӹ�֧��
			else if ((state.equals("000043") || state.equals("000040"))) {
				sqlStr = ",LZZT='000046'";
				return sqlStr;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return sqlStr;
		}
	}

	/**
	 * ������״̬����ѡ��Ա�õ���һ�����̵�״̬
	 * 
	 * @param personStr
	 * @param state
	 * @return
	 */
	public String getNextState(String personStr, String state) {
		String nextState = "";
		if (personStr.equals(""))
			return state;
		try {
			String sendMsg = getSendType(personStr);// �·���־

			// ֵ��Ա�·�ָ�ӿ�
			if (state.equals("000001")) {
				nextState = "000004";
				return nextState;
			}
			// �����ָ�ӿ��·�ָ�Ӵ�
			else if (state.equals("000004")) {
				nextState = "000007";
				return nextState;
			}
			// �����ָ�Ӵ��·�ҵ��
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("YWCJSR")) {
				nextState = "000067";
				return nextState;
			}
			// �����ָ�Ӵ������쵼
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("JLDJSR")) {
				nextState = "000070";
				return nextState;
			}
			// ҵ�񴦸�ָ�Ӵ�
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZHCJSR")) {
				nextState = "000073";
				return nextState;
			}
			// ҵ�񴦸����쵼
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("JLDJSR")) {
				nextState = "000022";
				return nextState;
			}
			// ���쵼��ҵ��
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("YWCJSR")) {
				nextState = "000064";
				return nextState;
			}
			// ���쵼��ָ�Ӵ�
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZHCJSR")) {
				nextState = "000076";
				return nextState;
			}
			// ���쵼��֧��
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZDJGID")) {
				nextState = "000079";
				return nextState;
			}
			// ҵ�񴦸�֧��
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZDJGID")) {
				nextState = "000031";
				return nextState;
			}
			// ֧�Ӹ���ӻ��ܶ�
			else if ((state.equals("000049") || state.equals("000034")
					|| state.equals("000037") || state.equals("000082") || state
					.equals("000031"))) {
				// ֧�Ӹ����
				if (sendMsg.equals("DDJGID")) {
					nextState = "000040";
					return nextState;
				} else { // ֧�Ӹ��ܶ�
					nextState = "000052";
					return nextState;
				}
			}
			// ��Ӹ�֧��
			else if ((state.equals("000043") || state.equals("000040"))) {
				nextState = "000046";
				return nextState;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return nextState;
		}
	}

	/**
	 * ������ѡ��Ա������������ø���Ա���ڵ����ͣ�ָ�ӿƣ�ָ�Ӵ������쵼�ȣ�
	 * 
	 * @param personStr
	 * @param state
	 * @return
	 */
	public String getSendType(String personStr) {
		String sendMsg = "";// �·���־
		try {
			String[] ZHKJSR = Setting.getString("ZHKJSR").split(","); // ָ�ӿ�
			String[] ZHCJSR = Setting.getString("ZHCJSR").split(","); // ָ�Ӵ�
			String[] JLDJSR = Setting.getString("JLDJSR").split(","); // ���쵼
			String[] YWCJSR = Setting.getString("YWCJSR").split(","); // ҵ��
			String[] YWCCBR = Setting.getString("YWCCBR").split(","); // �а���
			String[] ZDJGID = Setting.getString("ZDJGID").split(","); // ֧��
			String[] DDJGID = Setting.getString("DDJGID").split(","); // ���

			String[] persons = personStr.split("��");
			if (persons != null) {
				String deptSql = "select p.ssjg,d.jglx from t_sys_person p,t_sys_department d where d.jgid=p.ssjg and ryid='"
						+ persons[0] + "'";
				Object[] dept = DBHandler.getLineResult(deptSql);
				String deptId = StringHelper.obj2str(dept[0], "");
				String deptccbm = StringHelper.obj2str(dept[1], "");
				// ָ�ӿ�
				if (ZHKJSR != null) {
					for (int i = 0; i < ZHKJSR.length; i++) {
						if (deptId.equals(ZHKJSR[i].replaceAll("'", ""))) {
							sendMsg = "ZHKJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// ָ�Ӵ�
				if (ZHCJSR != null) {
					for (int i = 0; i < ZHCJSR.length; i++) {
						if (deptId.equals(ZHCJSR[i].replaceAll("'", ""))) {
							sendMsg = "ZHCJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// ҵ��
				if (YWCJSR != null) {
					for (int i = 0; i < YWCJSR.length; i++) {
						if (deptId.equals(YWCJSR[i].replaceAll("'", ""))) {
							sendMsg = "YWCJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// ���쵼
				if (JLDJSR != null) {
					for (int i = 0; i < JLDJSR.length; i++) {
						if (deptId.equals(JLDJSR[i].replaceAll("'", ""))) {
							sendMsg = "JLDJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// ҵ�񴦳а���
				/*
				 * if(YWCCBR!=null){ for(int i=0;i<YWCCBR.length;i++){
				 * if(deptId.equals(YWCCBR[i].replaceAll("'",""))){
				 * sendMsg="YWCCBR"; break; } } // return sendMsg; }
				 */
				// ֧��
				if (ZDJGID != null) {
					for (int i = 0; i < ZDJGID.length; i++) {
						if (deptId.equals(ZDJGID[i].replaceAll("'", ""))) {
							sendMsg = "ZDJGID";
							break;
						}
					}
					// return sendMsg;
				}
				// ���
				// if(DDJGID!=null){
				// for(int i=0;i<DDJGID.length;i++){
				// if(deptId.equals(DDJGID[i].replaceAll("'",""))){
				// sendMsg="DDJGID";
				// break;
				// }
				// }
				// // return sendMsg;
				// }
				if (deptccbm.equals("2")) {
					sendMsg = "DDJGID";
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return sendMsg;
		}
	}

	/**
	 * �õ�ͬһ����ĸ��ڵ�
	 * 
	 * @param res
	 * @return
	 */
	private Object[][] getBaseRoot(Object[][] res) {
		List list = new ArrayList();
		if (res != null) {
			int bmlength = StringHelper.obj2str(res[0][2], "").length();
			for (int i = 1; i < res.length; i++) {
				int ccbmlength = StringHelper.obj2str(res[i][2], "").length();
				if (ccbmlength == bmlength) {
					list.add(res[i]);
				}
			}
		}
		Object[][] roots = new Object[list.size()][4];
		System.out.println(list.size());
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				roots[i] = (Object[]) list.get(i);
			}
		} else {
			roots = null;
		}
		return roots;
	}

}
