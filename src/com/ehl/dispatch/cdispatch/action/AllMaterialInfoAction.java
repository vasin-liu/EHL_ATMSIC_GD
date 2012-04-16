/** 
 * 项目名称：EHL_ATMSIC_GD <br> 
 * 文件路径：com.ehl.dispatch.cdispatch.action  <br>  
 * 文件名称：AllMaterialInfoAction.java <br> 
 * 文件编号：    <br> 
 * 文件描述：   <br> 
 *
 * 版本信息： Ver 1.1  <br>  
 * 创建日期：2012-2-22   <br>  
 * 公司名称： 北京易华录信息技术股份有限公司  2012 Copyright(C) 版权所有     <br> 
 ************************************************** <br> 
 * 创建人：lenove <br>    
 * 创建日期：2012-2-22 下午2:51:44 <br>   
 ************* 修改历史  ************* <br> 
 * 修改人：lenove <br>    
 * 修改时间：2012-2-22 下午2:51:44 <br>    
 * 修改备注： <br>    
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.ehl.dispatch.cdispatch.dao.AllMaterialInfoDao;
import com.ehl.dispatch.cdispatch.dao.MaterialInfoDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.common.DispatchUtil;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.general.dao.GeneralInfoDao;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.output.BuildChart;
import com.ehl.sm.common.util.CreateSequence;

/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 包路径：com.ehl.dispatch.cdispatch.action <br>
 * 类名称：AllMaterialInfoAction <br>
 * 文件描述： <br>
 * 
 * @see <br>
 * @since Ver 1.1 <br>
 *        版本信息：Ver 1.1 <br>
 ********************************* <br>
 *        创建人：leisx <br>
 *        创建日期：2012-2-22 下午2:51:44 <br>
 *************        修改历史 ************* <br>
 *        修改人：leisx <br>
 *        修改时间：2012-2-22 下午2:51:44 <br>
 *        修改备注： <br>
 */

public class AllMaterialInfoAction extends Controller {
	/**
	 * 查询重大警情信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllMaterialInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),
				"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"), "asc");
		
		String start = StringHelper.obj2str(request.getParameter("start"),"");
		String end = StringHelper.obj2str(request.getParameter("end"),"");
		String jgmc = StringHelper.obj2str(request.getParameter("jgmc"),"");
		String info_type = StringHelper.obj2str(request.getParameter("info_type"), "");
		String info_title = StringHelper.obj2str(request.getParameter("info_title"), "");

		Map<String, String> map = new HashMap<String, String>();
		map.put("jgid", jgid);
		map.put("jgbh", jgbh);
		map.put("alarmId", alarmId);
		map.put("start", start);
		map.put("end", end);
		map.put("jgmc", jgmc);
		map.put("info_type", info_type);
		map.put("info_title", info_title);
		map.put("sord", sord);
		map.put("sortIndex", sortIndex);

		AllMaterialInfoDao amid = new AllMaterialInfoDao();
		Object[][] results = amid.queryAllMaterialInfo(map);

		try {
			// 以下模拟构造JSON数据对象 ,该对象是jqgrid的默认返回对象
			String json = "";

			if (results != null) {
				int totalRecord = results.length; // 总记录数(应根据数据库取得，在此只是模拟)
				int totalPage = totalRecord % Integer.parseInt(rows) == 0 ? totalRecord
						/ Integer.parseInt(rows)
						: totalRecord / Integer.parseInt(rows) + 1; // 计算总页数
				int index = (Integer.parseInt(page) - 1)
						* Integer.parseInt(rows); // 开始记录数
				int pageSize = Integer.parseInt(rows);

				json += "{\"page\": \"" + page + "\", \"total\": \""
						+ totalPage + "\", \"records\": \"" + totalRecord
						+ "\", \"rows\": [";
				for (int i = index; i < pageSize + index && i < totalRecord; i++) {
					if (String.valueOf(results[i][5]).equals("其他重大情况/值班日志")) {
						if (jgbh.length() == 2) {
							results[i][5] = "值班日志";
						} else {
							results[i][5] = "其他重大情况";
						}
					}

					json += "{\"id\":" + (i + 1) + ",\"cell\":[\""
							+ results[i][0] + "\",\"" + results[i][1] + "\",\""
							+ results[i][2] + "\",\"" + results[i][3] + "\",\""
							+ results[i][4] + "\",\"" + results[i][5] + "\",\""
							+ results[i][5] + "view\"" + ",\"" + results[i][5]
							+ "edit\"" + ",\"" + results[i][6] + "\",\""
							+ results[i][7] + "\",\"" + results[i][8] + "\",\""
							+ results[i][9] + "\",\"" + results[i][10]
							+ "\",\"" + results[i][11] + "\"]}";
					if (i != pageSize + index - 1 && i != totalRecord - 1) {
						json += ",";
					}
				}
				json += "]}";
			} else {
				json = "{\"page\": \"1\", \"records\": \"0\"}";
			}
			System.out.println("json:" + json);
			out.write(json); // 将JSON数据返回页面
		} catch (Exception ex) {
		}
		return null;
	}

	public ActionForward doGetMaterialInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),
				"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"), "asc");

		Map<String, String> map = new HashMap<String, String>();
		map.put("jgid", jgid);
		map.put("jgbh", jgbh);
		map.put("alarmId", alarmId);
		map.put("sord", sord);
		map.put("sortIndex", sortIndex);

		AllMaterialInfoDao amid = new AllMaterialInfoDao();
		Object[][] results = amid.queryMaterialInfo(map);

		try {
			// 以下模拟构造JSON数据对象 ,该对象是jqgrid的默认返回对象
			String json = "";

			if (results != null) {
				int totalRecord = results.length; // 总记录数(应根据数据库取得，在此只是模拟)
				int totalPage = totalRecord % Integer.parseInt(rows) == 0 ? totalRecord
						/ Integer.parseInt(rows)
						: totalRecord / Integer.parseInt(rows) + 1; // 计算总页数
				int index = (Integer.parseInt(page) - 1)
						* Integer.parseInt(rows); // 开始记录数
				int pageSize = Integer.parseInt(rows);

				json += "{\"page\": \"" + page + "\", \"total\": \""
						+ totalPage + "\", \"records\": \"" + totalRecord
						+ "\", \"rows\": [";
				for (int i = index; i < pageSize + index && i < totalRecord; i++) {
					json += "{\"id\":" + (i + 1) + ",\"cell\":[\""
							+ results[i][0] + "\",\"" + results[i][1] + "\",\""
							+ results[i][2] + "\",\"" + results[i][3] + "\",\""
							+ results[i][4] + "\",\"" + results[i][5] + "\",\""
							+ results[i][6] + "\",\"" + results[i][7] + "\",\""
							+ results[i][8] + "\",\"" + results[i][9]
							+ "\",\"view\",\"edit\",\"xb\",\"delete\"]}";
					if (i != pageSize + index - 1 && i != totalRecord - 1) {
						json += ",";
					}
				}
				json += "]}";
			} else {
				json = "{\"page\": \"1\", \"records\": \"0\"}";
			}
			System.out.println("json:" + json);
			out.write(json); // 将JSON数据返回页面
		} catch (Exception ex) {
		}
		return null;
	}

	public ActionForward doGetCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),
				"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"), "asc");

		Map<String, String> map = new HashMap<String, String>();
		map.put("jgid", jgid);
		map.put("jgbh", jgbh);
		map.put("alarmId", alarmId);
		map.put("sord", sord);
		map.put("sortIndex", sortIndex);

		AllMaterialInfoDao amid = new AllMaterialInfoDao();
		Object[][] results = amid.queryCrowdInfo(map);

		try {
			// 以下模拟构造JSON数据对象 ,该对象是jqgrid的默认返回对象
			String json = "";

			if (results != null) {
				int totalRecord = results.length; // 总记录数(应根据数据库取得，在此只是模拟)
				int totalPage = totalRecord % Integer.parseInt(rows) == 0 ? totalRecord
						/ Integer.parseInt(rows)
						: totalRecord / Integer.parseInt(rows) + 1; // 计算总页数
				int index = (Integer.parseInt(page) - 1)
						* Integer.parseInt(rows); // 开始记录数
				int pageSize = Integer.parseInt(rows);

				json += "{\"page\": \"" + page + "\", \"total\": \""
						+ totalPage + "\", \"records\": \"" + totalRecord
						+ "\", \"rows\": [";
				for (int i = index; i < pageSize + index && i < totalRecord; i++) {
					json += "{\"id\":" + (i + 1) + ",\"cell\":[\""
							+ results[i][0] + "\",\"" + results[i][1] + "\",\""
							+ results[i][2] + "\",\"" + results[i][3] + "\",\""
							+ results[i][4] + "\",\"" + results[i][5] + "\",\""
							+ results[i][6] + "\",\"" + results[i][7] + "\",\""
							+ results[i][8] + "\",\"" + results[i][9] + "\",\""
							+ results[i][10] + "\",\"" + results[i][11]
							+ "\",\"" + results[i][12] + "\",\""
							+ results[i][13] + "\",\"" + results[i][14]
							+ "\",\"view\",\"edit\"]}";
					if (i != pageSize + index - 1 && i != totalRecord - 1) {
						json += ",";
					}
				}
				json += "]}";
			} else {
				json = "{\"page\": \"1\", \"records\": \"0\"}";
			}
			System.out.println("json:" + json);
			out.write(json); // 将JSON数据返回页面
		} catch (Exception ex) {
		}
		return null;
	}

	public ActionForward doGetRoadBuildInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),
				"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"), "asc");

		Map<String, String> map = new HashMap<String, String>();
		map.put("jgid", jgid);
		map.put("jgbh", jgbh);
		map.put("alarmId", alarmId);
		map.put("sord", sord);
		map.put("sortIndex", sortIndex);

		AllMaterialInfoDao amid = new AllMaterialInfoDao();
		Object[][] results = amid.queryRoadBuildInfo(map);

		try {
			// 以下模拟构造JSON数据对象 ,该对象是jqgrid的默认返回对象
			String json = "";

			if (results != null) {
				int totalRecord = results.length; // 总记录数(应根据数据库取得，在此只是模拟)
				int totalPage = totalRecord % Integer.parseInt(rows) == 0 ? totalRecord
						/ Integer.parseInt(rows)
						: totalRecord / Integer.parseInt(rows) + 1; // 计算总页数
				int index = (Integer.parseInt(page) - 1)
						* Integer.parseInt(rows); // 开始记录数
				int pageSize = Integer.parseInt(rows);

				json += "{\"page\": \"" + page + "\", \"total\": \""
						+ totalPage + "\", \"records\": \"" + totalRecord
						+ "\", \"rows\": [";
				for (int i = index; i < pageSize + index && i < totalRecord; i++) {
					json += "{\"id\":" + (i + 1) + ",\"cell\":[\""
							+ results[i][0] + "\",\"" + results[i][1] + "\",\""
							+ results[i][2] + "\",\"" + results[i][3] + "\",\""
							+ results[i][4] + "\",\"" + results[i][5] + "\",\""
							+ results[i][6] + "\",\"" + results[i][7] + "\",\""
							+ results[i][8] + "\",\"" + results[i][9] + "\",\""
							+ results[i][10] + "\",\"" + results[i][11]
							+ "\",\"" + results[i][12] + "\",\""
							+ results[i][13] + "\",\"view\",\"edit\"]}";
					if (i != pageSize + index - 1 && i != totalRecord - 1) {
						json += ",";
					}
				}
				json += "]}";
			} else {
				json = "{\"page\": \"1\", \"records\": \"0\"}";
			}
			System.out.println("json:" + json);
			out.write(json); // 将JSON数据返回页面
		} catch (Exception ex) {
		}
		return null;
	}

	public ActionForward doGetXCBKInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),
				"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"), "asc");

		Map<String, String> map = new HashMap<String, String>();
		map.put("jgid", jgid);
		map.put("jgbh", jgbh);
		map.put("alarmId", alarmId);
		map.put("sord", sord);
		map.put("sortIndex", sortIndex);

		AllMaterialInfoDao amid = new AllMaterialInfoDao();
		Object[][] results = amid.queryXCBKInfo(map);

		try {
			// 以下模拟构造JSON数据对象 ,该对象是jqgrid的默认返回对象
			String json = "";

			if (results != null) {
				int totalRecord = results.length; // 总记录数(应根据数据库取得，在此只是模拟)
				int totalPage = totalRecord % Integer.parseInt(rows) == 0 ? totalRecord
						/ Integer.parseInt(rows)
						: totalRecord / Integer.parseInt(rows) + 1; // 计算总页数
				int index = (Integer.parseInt(page) - 1)
						* Integer.parseInt(rows); // 开始记录数
				int pageSize = Integer.parseInt(rows);

				json += "{\"page\": \"" + page + "\", \"total\": \""
						+ totalPage + "\", \"records\": \"" + totalRecord
						+ "\", \"rows\": [";
				for (int i = index; i < pageSize + index && i < totalRecord; i++) {
					json += "{\"id\":" + (i + 1) + ",\"cell\":[\""
							+ results[i][0] + "\",\"" + results[i][1] + "\",\""
							+ results[i][2] + "\",\"" + results[i][3] + "\",\""
							+ results[i][4] + "\",\"" + results[i][5] + "\",\""
							+ results[i][6] + "\",\"" + results[i][7] + "\",\""
							+ results[i][8] + "\",\"" + results[i][9] + "\",\""
							+ results[i][10] + "\",\"" + results[i][11]
							+ "\",\"view\",\"edit\"]}";
					if (i != pageSize + index - 1 && i != totalRecord - 1) {
						json += ",";
					}
				}
				json += "]}";
			} else {
				json = "{\"page\": \"1\", \"records\": \"0\"}";
			}
			System.out.println("json:" + json);
			out.write(json); // 将JSON数据返回页面
		} catch (Exception ex) {
		}
		return null;
	}

	public ActionForward doGetNoticeInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),
				"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),
				"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"), "asc");

		Map<String, String> map = new HashMap<String, String>();
		map.put("jgid", jgid);
		map.put("jgbh", jgbh);
		map.put("alarmId", alarmId);
		map.put("sord", sord);
		map.put("sortIndex", sortIndex);

		AllMaterialInfoDao amid = new AllMaterialInfoDao();
		Object[][] results = amid.queryNoticeInfo(map);

		try {
			// 以下模拟构造JSON数据对象 ,该对象是jqgrid的默认返回对象
			String json = "";

			if (results != null) {
				int totalRecord = results.length; // 总记录数(应根据数据库取得，在此只是模拟)
				int totalPage = totalRecord % Integer.parseInt(rows) == 0 ? totalRecord
						/ Integer.parseInt(rows)
						: totalRecord / Integer.parseInt(rows) + 1; // 计算总页数
				int index = (Integer.parseInt(page) - 1)
						* Integer.parseInt(rows); // 开始记录数
				int pageSize = Integer.parseInt(rows);
				
				json += "{\"page\": \"" + page + "\", \"total\": \""
						+ totalPage + "\", \"records\": \"" + totalRecord
						+ "\", \"rows\": [";
				for (int i = index; i < pageSize + index && i < totalRecord; i++) {
					json += "{\"id\":" + (i + 1) + ",\"cell\":[\""
							+ results[i][0] + "\",\"" + results[i][1] + "\",\""
							+ results[i][2] + "\",\"" + results[i][3] + "\",\""
							+ results[i][4] + "\",\"" + results[i][5] + "\",\""
							+ results[i][6] + "\",\"" + results[i][7] + "\",\"view\",\"edit\",\"xb\"]}";
					if (i != pageSize + index - 1 && i != totalRecord - 1) {
						json += ",";
					}
				}
				json += "]}";
			} else {
				json = "{\"page\": \"1\", \"records\": \"0\"}";
			}
			System.out.println("json:" + json);
			out.write(json); // 将JSON数据返回页面
		} catch (Exception ex) {
		}
		return null;
	}
}
