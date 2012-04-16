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
	 * 获取用户机构树
	 * dttype(dept tree type)机构树类型，2位数字
	 * null：显示所有机构
	 * 0：不显示
	 * 1：显示一级
	 * 2：显示多级
	 * 1位：父节点
	 * 2位：子节点
	 * 默认显示当前机构
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
		
		Object[][] data = FlowUtil.readMilte(sql, logger, "获取机构树列表");
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
		String whereStr = "jgid<=445331000000 and length(jgid)=12 and substr(jgid,7)='000000'";//排除科、室、中队
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
	 * 生成机构总队树
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
		response.setContentType("text/xml");// 指定输出的XML格式
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");

		// String deptstr=Setting.getString(divId); //从配置文件取得要显示的机构
		String deptstr = getDeptRes(state); // 获得要显示的机构
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //当前用户机构类型
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //当前用户机构层次编码
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

			// 多个同级根节点
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
		response.setContentType("text/xml");// 指定输出的XML格式
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");
		
		//Modified by Liuwx 2011-06-20
		int isShowSuperior = StringHelper.obj2int(request.getParameter("isShowSuperior"),0);

		// String deptstr=Setting.getString(divId); //从配置文件取得要显示的机构
		String deptstr = getDeptRes(state); // 获得要显示的机构
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //当前用户机构类型
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //当前用户机构层次编码
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
//			// 多个同级根节点
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
	 * 生成机构支队树
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
		response.setContentType("text/xml");// 指定输出的XML格式
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");

		// String deptstr=Setting.getString(divId); //从配置文件取得要显示的机构
		String deptstr = getDeptRes(state); // 获得要显示的机构
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //当前用户机构类型
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //当前用户机构层次编码
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
			// 多个同级根节点
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
	 * 生成机构大队树
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
		response.setContentType("text/xml");// 指定输出的XML格式
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String deptId = StringHelper
				.obj2str(request.getParameter("deptId"), "");
		String divId = StringHelper.obj2str(request.getParameter("divId"), "");
		String state = StringHelper.obj2str(request.getParameter("state"), "");

		// String deptstr=Setting.getString(divId); //从配置文件取得要显示的机构
		String deptstr = getDeptRes(state); // 获得要显示的机构
		// String
		// curdeptType=(String)DispatchUtil.getCurrentUserData(request).get("DEPTTYPE");
		// //当前用户机构类型
		// String
		// curdeptccbm=(String)DispatchUtil.getCurrentUserData(request).get("DEPTCCBM");
		// //当前用户机构层次编码
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
			// 多个同级根节点
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
	 * 获取部门的下属机构
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
	 * 生成部门树xml字符串
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
	 * 生成xml字符串
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
	 * 生成人员xml
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
	 * 获取部门内人员
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
	 * 根据现在状态得到需要下发的单位集合
	 * 
	 * @param state
	 * @return
	 */
	private String getDeptRes(String state) {
		String deptStr = null;
		try {
			// 下发指挥科
			if (state.equals("000000") || state.equals("000001")) {
				deptStr = Setting.getString("ZHKJSR");
				return deptStr;
			}
			// 指挥科给指挥处
			else if (state.equals("000004")) {
				deptStr = Setting.getString("ZHCJSR");
				return deptStr;
			}
			// 指挥处发送业务处和局领导
			else if (state.equals("000007") || state.equals("000073")
					|| state.equals("000076")) {
				deptStr = Setting.getString("JLDJSR") + ","
						+ Setting.getString("YWCJSR"); //
				return deptStr;
			}
			// 业务处发送支队，指挥处和局领导
			else if (state.equals("000064") || state.equals("000067")
					|| state.equals("000058")) {
				deptStr = Setting.getString("JLDJSR") + ","
						+ Setting.getString("ZHCJSR") + ","
						+ Setting.getString("ZDJGID") + ","
						+ Setting.getString("YWCCBR"); //
				return deptStr;
			}
			// 业务处发送支队
			else if (state.equals("000085")) {
				deptStr = Setting.getString("ZDJGID"); //
				return deptStr;
			}
			// 局领导发送支队，指挥处和业务处
			else if (state.equals("000022") || state.equals("000070")) {
				deptStr = Setting.getString("YWCJSR") + ","
						+ Setting.getString("ZHCJSR") + ","
						+ Setting.getString("ZDJGID"); //
				return deptStr;
			}
			// // 支队给承办人，大队
			// else
			// if(state.equals("000034")||state.equals("000037")||state.equals("000082")){
			// deptStr=Setting.getString("YWCCBR"); //
			// return deptStr;
			// }
			// 支队给大队
			else if (state.equals("000034") || state.equals("000037")
					|| state.equals("000082")) {
				deptStr = ""; //
				return deptStr;
			}
			// 大队给支队
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
	 * 根据现状态和人员将人员插入到相应的流程字段中，同时修改状态；
	 * 
	 * @param personStr
	 * @param state
	 * @return
	 */
	public String getUpdateSql(String personStr, String state) {
		String sqlStr = "";
		try {
			String sendMsg = getSendType(personStr);// 下发标志

			// 值班员下发指挥科
			if (state.equals("000001")) {
				sqlStr = ",ZHKJSR='" + personStr + "',LZZT='000004'";
				return sqlStr;
			}
			// 如果是指挥科下发指挥处
			else if (state.equals("000004")) {
				sqlStr = ",ZHCJSR='" + personStr + "',LZZT='000007'";
				return sqlStr;
			}
			// 如果是指挥处下发业务处
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("YWCJSR")) {
				sqlStr = ",YWCJSR='" + personStr + "',LZZT='000067'";
				return sqlStr;
			}
			// 如果是指挥处给局领导
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("JLDJSR")) {
				sqlStr = ",JLDJSR='" + personStr + "',LZZT='000070'";
				return sqlStr;
			}
			// 业务处给指挥处
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZHCJSR")) {
				sqlStr = ",ZHCJSR='" + personStr + "',LZZT='000073'";
				return sqlStr;
			}
			// 业务处给局领导
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("JLDJSR")) {
				sqlStr = ",JLDJSR='" + personStr + "',LZZT='000022'";
				return sqlStr;
			}
			// 局领导给业务处
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("YWCJSR")) {
				sqlStr = ",YWCJSR='" + personStr + "',LZZT='000064'";
				return sqlStr;
			}
			// 局领导给指挥处
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZHCJSR")) {
				sqlStr = ",ZHCJSR='" + personStr + "',LZZT='000076'";
				return sqlStr;
			}
			// 局领导给支队
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZDJGID")) {
				sqlStr = ",ZDJGID='" + personStr + "',LZZT='000079'";
				return sqlStr;
			}
			// 业务处给支队
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZDJGID")) {
				sqlStr = ",ZDJGID='" + personStr + "',LZZT='000031'";
				return sqlStr;
			}
			// 支队给总队，大队
			else if ((state.equals("000049") || state.equals("000034")
					|| state.equals("000037") || state.equals("000082") || state
					.equals("000031"))) {
				// 支队给大队
				if (sendMsg.equals("DDJGID")) {
					sqlStr = ",DDJGID='" + personStr + "',LZZT='000040'";
					return sqlStr;
				} else { // 支队给总队
					sqlStr = ",LZZT='000052'";
					return sqlStr;
				}
			}
			// 大队给支队
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
	 * 根据现状态和所选人员得到下一个流程的状态
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
			String sendMsg = getSendType(personStr);// 下发标志

			// 值班员下发指挥科
			if (state.equals("000001")) {
				nextState = "000004";
				return nextState;
			}
			// 如果是指挥科下发指挥处
			else if (state.equals("000004")) {
				nextState = "000007";
				return nextState;
			}
			// 如果是指挥处下发业务处
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("YWCJSR")) {
				nextState = "000067";
				return nextState;
			}
			// 如果是指挥处给局领导
			else if ((state.equals("000007") || state.equals("000073") || state
					.equals("000076"))
					&& sendMsg.equals("JLDJSR")) {
				nextState = "000070";
				return nextState;
			}
			// 业务处给指挥处
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZHCJSR")) {
				nextState = "000073";
				return nextState;
			}
			// 业务处给局领导
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("JLDJSR")) {
				nextState = "000022";
				return nextState;
			}
			// 局领导给业务处
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("YWCJSR")) {
				nextState = "000064";
				return nextState;
			}
			// 局领导给指挥处
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZHCJSR")) {
				nextState = "000076";
				return nextState;
			}
			// 局领导给支队
			else if ((state.equals("000070") || state.equals("000022"))
					&& sendMsg.equals("ZDJGID")) {
				nextState = "000079";
				return nextState;
			}
			// 业务处给支队
			else if ((state.equals("000067") || state.equals("000064")
					|| state.equals("000058") || state.equals("000052"))
					&& sendMsg.equals("ZDJGID")) {
				nextState = "000031";
				return nextState;
			}
			// 支队给大队或总队
			else if ((state.equals("000049") || state.equals("000034")
					|| state.equals("000037") || state.equals("000082") || state
					.equals("000031"))) {
				// 支队给大队
				if (sendMsg.equals("DDJGID")) {
					nextState = "000040";
					return nextState;
				} else { // 支队给总队
					nextState = "000052";
					return nextState;
				}
			}
			// 大队给支队
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
	 * 根据所选人员的所属机构获得该人员属于的类型（指挥科，指挥处，局领导等）
	 * 
	 * @param personStr
	 * @param state
	 * @return
	 */
	public String getSendType(String personStr) {
		String sendMsg = "";// 下发标志
		try {
			String[] ZHKJSR = Setting.getString("ZHKJSR").split(","); // 指挥科
			String[] ZHCJSR = Setting.getString("ZHCJSR").split(","); // 指挥处
			String[] JLDJSR = Setting.getString("JLDJSR").split(","); // 局领导
			String[] YWCJSR = Setting.getString("YWCJSR").split(","); // 业务处
			String[] YWCCBR = Setting.getString("YWCCBR").split(","); // 承办人
			String[] ZDJGID = Setting.getString("ZDJGID").split(","); // 支队
			String[] DDJGID = Setting.getString("DDJGID").split(","); // 大队

			String[] persons = personStr.split("；");
			if (persons != null) {
				String deptSql = "select p.ssjg,d.jglx from t_sys_person p,t_sys_department d where d.jgid=p.ssjg and ryid='"
						+ persons[0] + "'";
				Object[] dept = DBHandler.getLineResult(deptSql);
				String deptId = StringHelper.obj2str(dept[0], "");
				String deptccbm = StringHelper.obj2str(dept[1], "");
				// 指挥科
				if (ZHKJSR != null) {
					for (int i = 0; i < ZHKJSR.length; i++) {
						if (deptId.equals(ZHKJSR[i].replaceAll("'", ""))) {
							sendMsg = "ZHKJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// 指挥处
				if (ZHCJSR != null) {
					for (int i = 0; i < ZHCJSR.length; i++) {
						if (deptId.equals(ZHCJSR[i].replaceAll("'", ""))) {
							sendMsg = "ZHCJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// 业务处
				if (YWCJSR != null) {
					for (int i = 0; i < YWCJSR.length; i++) {
						if (deptId.equals(YWCJSR[i].replaceAll("'", ""))) {
							sendMsg = "YWCJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// 局领导
				if (JLDJSR != null) {
					for (int i = 0; i < JLDJSR.length; i++) {
						if (deptId.equals(JLDJSR[i].replaceAll("'", ""))) {
							sendMsg = "JLDJSR";
							break;
						}
					}
					// return sendMsg;
				}
				// 业务处承办人
				/*
				 * if(YWCCBR!=null){ for(int i=0;i<YWCCBR.length;i++){
				 * if(deptId.equals(YWCCBR[i].replaceAll("'",""))){
				 * sendMsg="YWCCBR"; break; } } // return sendMsg; }
				 */
				// 支队
				if (ZDJGID != null) {
					for (int i = 0; i < ZDJGID.length; i++) {
						if (deptId.equals(ZDJGID[i].replaceAll("'", ""))) {
							sendMsg = "ZDJGID";
							break;
						}
					}
					// return sendMsg;
				}
				// 大队
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
	 * 得到同一级别的根节点
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
