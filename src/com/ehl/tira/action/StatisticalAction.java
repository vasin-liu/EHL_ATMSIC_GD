/** 
 * 项目名称：EHL_ATMSIC_GD <br> 
 * 文件路径：com.ehl.tira.action  <br>  
 * 文件名称：StatisticalAction.java <br> 
 * 文件编号：    <br> 
 * 文件描述：   <br> 
 *
 * 版本信息： Ver 1.1  <br>  
 * 创建日期：2011-12-13   <br>  
 * 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有     <br> 
 ************************************************** <br> 
 * 创建人：lenove <br>    
 * 创建日期：2011-12-13 下午5:20:44 <br>   
 ************* 修改历史  ************* <br> 
 * 修改人：lenove <br>    
 * 修改时间：2011-12-13 下午5:20:44 <br>    
 * 修改备注： <br>    
 */
package com.ehl.tira.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.policeWorks.assess.AssessUtil;
import com.ehl.tira.dao.StatisticalDao;
import com.ehl.util.Array;
import com.ehl.util.HomePageCtrl;

/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 包路径：com.ehl.tira.action <br>
 * 类名称：StatisticalAction <br>
 * 文件描述： <br>
 * 
 * @see <br>
 * @since Ver 1.1 <br>
 *        版本信息：Ver 1.1 <br>
 ********************************* <br>
 *        创建人：lenove <br>
 *        创建日期：2011-12-13 下午5:20:44 <br>
 *************        修改历史 ************* <br>
 *        修改人：lenove <br>
 *        修改时间：2011-12-13 下午5:20:44 <br>
 *        修改备注： <br>
 */

public class StatisticalAction extends Controller {

	private Logger logger = Logger.getLogger(StatisticalAction.class);

	/**
	 * 设置模块主页<br/>
	 * 假如该用户没有权限查看默认页，则跳到左树第一个字节点 Modified by Leisx 2011-11-22
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetSingleSegFlow(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[][] toXmlInfo = null;

		String startDate = StringHelper.obj2str(
				request.getParameter("startDate"), "");
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),
				"");
		String segName = StringHelper.obj2str(request.getParameter("segName"),
				"");
		String type = StringHelper.obj2str(request.getParameter("type"), "");
		String flowType = StringHelper.obj2str(
				request.getParameter("flowType"), "");

		HashMap hm = new HashMap();
		hm.put("startDate", startDate);
		hm.put("endDate", endDate);
		hm.put("segName", segName);
		hm.put("type", type);
		hm.put("flowType", flowType);

		StatisticalDao sd = new StatisticalDao();
		Object[][] results = sd.querySingleSeg(hm);

		StringBuffer showXml = new StringBuffer();
		StringBuffer xmlData = new StringBuffer();

		if (results != null) {
			xmlData.append(sd.getSingleShowChartStr(hm, results));
			xmlData.append("+");
			xmlData.append(sd.getSingleShowTableStr(hm, results));
		} else {
			showXml.append("没有取到考核信息");
		}
		System.out.println(xmlData.toString());
		out.write(xmlData.toString());
		out.close();
		return null;
	}

	public ActionForward doGetMutiSegFlow(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		String startDate = StringHelper.obj2str(
				request.getParameter("startDate"), "");
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),
				"");
		String segName = StringHelper.obj2str(request.getParameter("segName"),
				"");
		String flowType = StringHelper.obj2str(
				request.getParameter("flowType"), "");
		Map hm = new HashMap();
		hm.put("startDate", startDate);
		hm.put("endDate", endDate);
		hm.put("segName", segName);
		hm.put("flowType", flowType);

		StatisticalDao sd = new StatisticalDao();
		Object[][] results = sd.getMutiSegFlow(hm);
		String toXmlStr = sd.getMutileShowChartStr(hm, results);
		toXmlStr += "+" + sd.getMutileShowTableStr(hm, results);
		out.write(toXmlStr);
		out.close();
		return null;
	}

	public ActionForward doGetMutiSegFlowRatio(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		String startDate = StringHelper.obj2str(
				request.getParameter("startDate"), "");
		String segName = StringHelper.obj2str(request.getParameter("segName"),
				"");
		String flowType = StringHelper.obj2str(
				request.getParameter("flowType"), "");
		String selectItems = StringHelper.obj2str(
				request.getParameter("selectItems"), "");

		Map hm = new HashMap();
		hm.put("startDate", startDate);
		hm.put("segName", segName);
		hm.put("flowType", flowType);
		hm.put("selectItems", selectItems);

		StatisticalDao sd = new StatisticalDao();
		Object[][] results = sd.getMutiSegRatio(hm);

		String toXmlStr = sd.getMutiRatioTableStr(hm, results);
		out.write(toXmlStr);
		out.close();
		return null;
	}
	
	public ActionForward doGetCitySegFlow(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[][] toXmlInfo = null;

		String startDate = StringHelper.obj2str(
				request.getParameter("startDate"), "");
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),
				"");
		String segName = StringHelper.obj2str(request.getParameter("segName"),
				"");
		String type = StringHelper.obj2str(request.getParameter("type"), "");
		String flowType = StringHelper.obj2str(
				request.getParameter("flowType"), "");

		HashMap hm = new HashMap();
		hm.put("startDate", startDate);
		hm.put("endDate", endDate);
		hm.put("segName", segName);
		hm.put("type", type);
		hm.put("flowType", flowType);

		StatisticalDao sd = new StatisticalDao();
		Object[][] results = sd.getCitySeg(hm);

		StringBuffer showXml = new StringBuffer();
		StringBuffer xmlData = new StringBuffer();

		if (results != null) {
			xmlData.append(sd.getCitySegShowChartStr(hm, results));
			xmlData.append("+");
			xmlData.append(sd.getCitySegShowTableStr(hm, results));
		} else {
			showXml.append("没有取到考核信息");
		}
		System.out.println(xmlData.toString());
		out.write(xmlData.toString());
		out.close();
		return null;
	}

	public ActionForward doShowExcel(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		
		String startDate = request.getParameter("0");
		String endDate = request.getParameter("1");
		String segName = request.getParameter("2");
		String selectItems = request.getParameter("3");
		String type = request.getParameter("4");
		String flowType = request.getParameter("5");
		String flag = request.getParameter("6");

		PrintWriter out = response.getWriter();
		Map map = new HashMap();

		map.put("segName", segName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("type", type);
		map.put("flowType", flowType);
		map.put("flag", flag);
		map.put("selectItems", selectItems);

		StatisticalDao sd = new StatisticalDao();
		CommonUtility comUtility = new CommonUtility();
		Object[][] results = null;
		String[][] toXmlInfo = null;
		StringBuffer titleName = new StringBuffer();
		StringBuffer sheetName = new StringBuffer("卡口流量");
		StringBuffer fileName = new StringBuffer("exportExcel");
		StringBuffer tabHeader = new StringBuffer();

		if (flag.equals("1")) {
			if (type.equals("1")) {
				titleName.append(segName + startDate + "卡口流量统计表");
			} else {
				titleName.append(segName + startDate + "至" + endDate
						+ "卡口流量统计表");
			}

			results = sd.querySingleSeg(map);
			toXmlInfo = new String[1][results.length + 1];
			try {
				if (results != null) {
					toXmlInfo[0][0] = segName;
					if(type.equals("1")) {
						for (int i = 0; i < results.length; i++) {
							toXmlInfo[0][i + 1] = String.valueOf(results[i][1] + "辆");
						}
					} else {
						for (int i = 0; i < results.length; i++) {
							toXmlInfo[0][i + 1] = String.valueOf(results[i][3] + "辆(缺失:" + results[i][4] + "h)");
						}
					}
					
				}

				if (type.equals("1")) {
					tabHeader.append("卡口名称/时间,");
					for (int i = 0; i < results.length; i++) {
						tabHeader.append(results[i][0]);
						if (i + 1 == results.length) {
							break;
						} else {
							tabHeader.append(",");
						}
					}
				} else if (type.equals("1")) {
					tabHeader.append("卡口名称/日期,");
					for (int i = 0; i < results.length; i++) {
						tabHeader.append(results[i][1]);
						if (i + 1 == results.length) {
							break;
						} else {
							tabHeader.append(",");
						}
					}
				} else {
					tabHeader.append("卡口名称/月份,");
					for (int i = 0; i < results.length; i++) {
						tabHeader.append(results[i][1]);
						if (i + 1 == results.length) {
							break;
						} else {
							tabHeader.append(",");
						}
					}
				}

				comUtility.wirteToExcel(response, tabHeader.toString(),
						fileName.toString(), sheetName.toString(), toXmlInfo,
						titleName.toString());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("取得考核信息出错。");
			}
		} else if (flag.equals("2")) {
			titleName.append("广东省" + startDate + "至" + endDate + "卡口流量统计表");
			results = sd.getMutiSegFlow(map);
			toXmlInfo = new String[results.length][3];
			try {
				if (results != null) {
					for (int i = 0; i < results.length; i++) {
						toXmlInfo[i][0] = String.valueOf(results[i][0]).replaceAll(
								"\\(.*?\\)", "");
						toXmlInfo[i][1] = String.valueOf(results[i][2]);
						if(String.valueOf(results[i][3]).equals("0")) {
							toXmlInfo[i][2] = "数据完整";
						} else {
							
							toXmlInfo[i][2] = "缺失" + String.valueOf(results[i][3]) + "小时";
						}
					}
				}

				tabHeader.append("卡口名称,");

				if (flowType.equals("1")) {
					tabHeader.append("入省流量(单位：辆),");
				} else if (flowType.equals("2")) {
					tabHeader.append("出省流量(单位：辆),");
				} else if (flowType.equals("3")) {
					tabHeader.append("双向流量(单位：辆),");
				}
				tabHeader.append("数据完整情况");

				comUtility.wirteToExcel(response, tabHeader.toString(),
						fileName.toString(), sheetName.toString(), toXmlInfo,
						titleName.toString());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("取得考核信息出错。");
			}
		} else if (flag.equals("3")) {
			if (flowType.equals("1")) {
				titleName.append("广东省" + startDate + "卡口日均入省流量统计表");
			} else if (flowType.equals("2")) {
				titleName.append("广东省" + startDate + "卡口日均出省流量统计表");
			} else if (flowType.equals("3")) {
				titleName.append("广东省" + startDate + "卡口日均总流量统计表");
			}
			results = sd.getMutiSegRatio(map);
			toXmlInfo = new String[results.length][(selectItems.split(",").length * 2) + 1];

			try {
				if (results != null) {
					for (int i = 0; i < results.length; i++) {
						toXmlInfo[i][0] = String.valueOf(results[i][0]);
						for (int j = 1; j < toXmlInfo[i].length; j++) {
							toXmlInfo[i][j] = String.valueOf(results[i][j + 1]);
						}
					}
				}

				tabHeader.append("卡口名称,");

				if (selectItems.indexOf("1") != -1) {
					tabHeader.append("3日#平均流量,3日#环比增减,");
				}
				if (selectItems.indexOf("2") != -1) {
					tabHeader.append("7日#平均流量,7日#环比增减,");
				}
				if (selectItems.indexOf("3") != -1) {
					tabHeader.append("15日#平均流量,15日#环比增减,");
				}
				if (selectItems.indexOf("4") != -1) {
					tabHeader.append("30日#平均流量,30日#环比增减,");
				}

				comUtility.wirteToExcelBySegRatio(response, tabHeader.toString(),
						fileName.toString(), sheetName.toString(), toXmlInfo,
						titleName.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("取得考核信息出错。");
			}
		} else if(flag.equals("4")) {
			if (type.equals("1")) {
				titleName.append(segName + startDate + "卡口流量统计表");
			} else {
				titleName.append(segName + startDate + "至" + endDate
						+ "卡口流量统计表");
			}

			results = sd.getCitySeg(map);
			toXmlInfo = new String[1][results.length + 1];
			try {
				if (results != null) {
					toXmlInfo[0][0] = segName;
					if(type.equals("1")) {
						for (int i = 0; i < results.length; i++) {
							toXmlInfo[0][i + 1] = String.valueOf(results[i][1] + "辆");
						}
					} else {
						for (int i = 0; i < results.length; i++) {
							toXmlInfo[0][i + 1] = String.valueOf(results[i][3] + "辆(缺失:" + results[i][4] + "h)");
						}
					}
					
				}

				if (type.equals("1")) {
					tabHeader.append("卡口名称/时间,");
					for (int i = 0; i < results.length; i++) {
						tabHeader.append(results[i][0]);
						if (i + 1 == results.length) {
							break;
						} else {
							tabHeader.append(",");
						}
					}
				} else if (type.equals("1")) {
					tabHeader.append("卡口名称/日期,");
					for (int i = 0; i < results.length; i++) {
						tabHeader.append(results[i][1]);
						if (i + 1 == results.length) {
							break;
						} else {
							tabHeader.append(",");
						}
					}
				} else {
					tabHeader.append("卡口名称/月份,");
					for (int i = 0; i < results.length; i++) {
						tabHeader.append(results[i][1]);
						if (i + 1 == results.length) {
							break;
						} else {
							tabHeader.append(",");
						}
					}
				}

				comUtility.wirteToExcel(response, tabHeader.toString(),
						fileName.toString(), sheetName.toString(), toXmlInfo,
						titleName.toString());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("取得考核信息出错。");
			}
		}
		out.close();
	
		return null;
	}
}
