package com.ehl.policeWorks.assess.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.TrafficNewsFeedsDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.policeWorks.assess.AssessUtil;
import com.ehl.policeWorks.assess.dao.ScoreRecordDao;
import com.ehl.util.Array;

/**
 * 考核信息处理<br/>
 * @date 2011-3-21
 */
public class AssessStandardAction extends Controller {

	private Logger logger = Logger.getLogger(AssessStandardAction.class);
	
	private ScoreRecordDao dao = new ScoreRecordDao();

	/**
	 * 考核标准的录入<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyAssessStandard(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 事故的评分
		String acc_001024 = StringHelper.obj2str(request.getParameter("acc_001024"),"");
		// 拥堵的评分
		String crowd_001002 = StringHelper.obj2str(request.getParameter("crowd_001002"),"");
		// 占道施工
		String build_001023 = StringHelper.obj2str(request.getParameter("build_001023"),"");
		// 信息文件
		String news_001004 = StringHelper.obj2str(request.getParameter("news_001004"),"");
		// 信息文件加分
		String news_001006 = StringHelper.obj2str(request.getParameter("news_001006"),"");
		// 报料采用
		String baoliao_001001 = StringHelper.obj2str(request.getParameter("baoliao_001001"),"");
		// 报料不采用
		String baoliao_001007 = StringHelper.obj2str(request.getParameter("baoliao_001007"),"");
		// 报料未核实
		String baoliao_001005 = StringHelper.obj2str(request.getParameter("baoliao_001005"),"");
		// 扣分
		String kou_001003 = StringHelper.obj2str(request.getParameter("kou_001003"),"");
		// 调研信息材料
		String news_001008 = StringHelper.obj2str(request.getParameter("news_001008"),"");
		// 调研信息材料
		String news_001009 = StringHelper.obj2str(request.getParameter("news_001009"),"");
		
		StringBuffer sql = new StringBuffer(); 
	 	try {
	
			// 事故评分,拥堵的评分,施工的评分，信息文件省厅采用加分 ，信息文件公安部采用加分 ，
	 		// 拥堵报料采用 ，拥堵报料超时未核实，漏报 错报扣分
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(acc_001024);
			sql.append("' where ASSESS_STANDARD_ID = '001024' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(crowd_001002);
			sql.append("' where ASSESS_STANDARD_ID = '001002' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(build_001023);
			sql.append("' where ASSESS_STANDARD_ID = '001023' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001004);
			sql.append("' where ASSESS_STANDARD_ID = '001004' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001006);
			sql.append("' where ASSESS_STANDARD_ID = '001006' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(baoliao_001001);
			sql.append("' where ASSESS_STANDARD_ID = '001001' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(baoliao_001005);
			sql.append("' where ASSESS_STANDARD_ID = '001005' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(kou_001003);
			sql.append("' where ASSESS_STANDARD_ID = '001003' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(baoliao_001007);
			sql.append("' where ASSESS_STANDARD_ID = '001007' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001008);
			sql.append("' where ASSESS_STANDARD_ID = '001008' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001009);
			sql.append("' where ASSESS_STANDARD_ID = '001009' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			out.write("success");	
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("插入考核标准信息出错。 sql:" + sql);
	 	}
		return null;
	}

	/**
	 * 取得考核标准信息<br/>
	 * 取得考核标准信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAssessStandardInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer();
		Object[] result = null;
		try {
			sql.append("select distinct (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001024'),");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001002') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001023') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001004') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001006') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001001') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001005') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001003') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001007') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001008') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001009')  ");
			sql.append(" from T_OA_ASSESS_CODE  ");
			result = DBHandler.getLineResult(sql.toString());
			System.out.println("getAssessStandardInfo.sql==>"+sql);
			out.write(DataToXML.objArrayToXml(result));
			System.out.println(DataToXML.objArrayToXml(result));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得考核标准信息出错。 sql:" + sql);
		}
		out.close();
		return null;
	}

	/**
	 * 取得考核信息<br/>
	 * 取得考核信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAssessInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		// 部门评分
		String zhiduiId = StringHelper.obj2str(request.getParameter("zhiduiId"),"");
		// 评分开始日期
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),"");
		// 评分结束日期
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),"");
		// 考核类型
		String type = StringHelper.obj2str(request.getParameter("type"),"");
		//排序项目
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),9);
		PrintWriter out = response.getWriter();
		String [][] toXmlInfo = null; 
		try {
			String[] returnStr = null;
			// 支队id为空 检索所有支队的考核成绩
			if("".equals(zhiduiId)) {
				Object[][] zhiDuiInfo = AssessUtil.getZhiDuiIdName();
				// 遍历所有的支队
				if(zhiDuiInfo != null) {
					toXmlInfo = new String [zhiDuiInfo.length][12]; 
					for( int i=0;i<zhiDuiInfo.length;i++) {
						// 支队id
						toXmlInfo[i][0] = String.valueOf( zhiDuiInfo[i][0]);
						// 支队名称
						toXmlInfo[i][1] = String.valueOf( zhiDuiInfo[i][1]);
						returnStr = AssessUtil.getOneZhiduiAssess(String.valueOf(zhiDuiInfo[i][0]),String.valueOf(zhiDuiInfo[i][1]),startDate,endDate,type);
						if(returnStr != null) {
							// 事故件数
							toXmlInfo[i][2] = returnStr[0]; 
							// 拥堵件数
							toXmlInfo[i][3] = returnStr[1]; 
							// 施工件数
							toXmlInfo[i][4] = returnStr[2]; 
							// 信息文件省厅采用件数
							toXmlInfo[i][5] = returnStr[3]; 
							// 信息文件公安部采用件数
							toXmlInfo[i][6] = returnStr[4]; 
							// 调研信息材料省厅采用
							toXmlInfo[i][7] = returnStr[5]; 
							// 调研信息材料部局采用
							toXmlInfo[i][8] = returnStr[6]; 
							// 拥堵报料采用件数
							toXmlInfo[i][9] = returnStr[7]; 
							// 信息扣分
							toXmlInfo[i][10] = returnStr[8]; 
							// 总得分
							toXmlInfo[i][11] = returnStr[9]; 
						}
					}
				}
			} else {
				Object[][] daDuiInfo = AssessUtil.getAllDaDuiByZhiDuiId(zhiduiId);
				// 遍历所有的支队
				if(daDuiInfo != null) {
					toXmlInfo = new String [daDuiInfo.length][12]; 
					for( int i=0;i<daDuiInfo.length;i++) {
						// 大队id
						toXmlInfo[i][0] = String.valueOf( daDuiInfo[i][0]);
						// 大队名称
						toXmlInfo[i][1] = String.valueOf( daDuiInfo[i][1]);
						returnStr = AssessUtil.getOneDaduiAssess(String.valueOf(daDuiInfo[i][0]),String.valueOf(daDuiInfo[i][1]),startDate, endDate,type);
						if(returnStr != null) {
							// 事故件数
							toXmlInfo[i][2] = returnStr[0]; 
							// 拥堵件数
							toXmlInfo[i][3] = returnStr[1]; 
							// 施工件数
							toXmlInfo[i][4] = returnStr[2]; 
							// 信息文件省厅采用件数
							toXmlInfo[i][5] = returnStr[3]; 
							// 信息文件公安部采用件数
							toXmlInfo[i][6] = returnStr[4]; 
							// 调研信息材料省厅采用
							toXmlInfo[i][7] = returnStr[5]; 
							// 调研信息材料部局采用
							toXmlInfo[i][8] = returnStr[6]; 
							// 拥堵报料采用件数
							toXmlInfo[i][9] = returnStr[7]; 
							// 信息扣分
							toXmlInfo[i][10] = returnStr[8]; 
							// 总得分
							toXmlInfo[i][11] = returnStr[9]; 
						}
					}
				}
			}
			StringBuffer showXml = new StringBuffer(); 
			String showChart = "";
			String showTable = "";
			if(toXmlInfo != null) {
				sortItem++;
				Array.sort(toXmlInfo, sortItem, false);//sortItem == 10
				showChart = AssessUtil.getShowChartStr(toXmlInfo, zhiduiId, startDate, endDate);
				showTable = AssessUtil.getShowTableStr(toXmlInfo, zhiduiId, startDate, endDate, type);
			} else {
				showXml.append("没有取到考核信息");
			}
			showXml.append(showChart);
			showXml.append("+");
			showXml.append(showTable);
			out.write(String.valueOf(showXml));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得考核信息出错。");
		}
		out.close();
		return null;
	}
	
	/**
	 * 取得考核信息Excel文件<br/>
	 * 取得考核信息
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
		response.setContentType("text/xml; charset=UTF-8");
		// 部门评分
		String zhiduiId = StringHelper.obj2str(request.getParameter("zhiduiId"),"");
		// 评分开始日期
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),"");
		// 评分结束日期
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),"");
		// 考核类型
		String type = StringHelper.obj2str(request.getParameter("type"),"");
		//排序项目
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),9);
		PrintWriter out = response.getWriter();
		String [][] toXmlInfo = null; 
		try {
			String[] returnStr = null;
			String fileName = "考核信息";
			// 支队id为空 检索所有支队的考核成绩
			if("".equals(zhiduiId)) {
				Object[][] zhiDuiInfo = AssessUtil.getZhiDuiIdName();
				fileName = "各市交警支队考核信息";
				// 遍历所有的支队
				if(zhiDuiInfo != null) {
					int length = 11;
					if(type.equals("1")){
						length = 7;
					}else if(type.equals("2")){
						length = 6;
					}
					toXmlInfo = new String [zhiDuiInfo.length][length];  
					for( int i=0;i<zhiDuiInfo.length;i++) {
//						// 支队id
//						toXmlInfo[i][0] = String.valueOf( zhiDuiInfo[i][0]);
						// 支队名称
						toXmlInfo[i][0] = String.valueOf( zhiDuiInfo[i][1]);
						returnStr = AssessUtil.getOneZhiduiAssess(String.valueOf(zhiDuiInfo[i][0]),String.valueOf(zhiDuiInfo[i][1]),startDate, endDate,type);
						if(returnStr != null) {
							if(type.equals("")){
								// 事故件数
								toXmlInfo[i][1] = returnStr[0]; 
								// 拥堵件数
								toXmlInfo[i][2] = returnStr[1]; 
								// 施工件数
								toXmlInfo[i][3] = returnStr[2]; 
								// 信息文件省厅采用件数
								toXmlInfo[i][4] = returnStr[3]; 
								// 信息文件公安部采用件数
								toXmlInfo[i][5] = returnStr[4]; 
								// 调研信息材料省厅采用
								toXmlInfo[i][6] = returnStr[5]; 
								// 调研信息材料部局采用
								toXmlInfo[i][7] = returnStr[6]; 
								// 拥堵报料采用件数
								toXmlInfo[i][8] = returnStr[7]; 
								// 信息扣分
								toXmlInfo[i][9] = returnStr[8]; 
								// 总得分
								toXmlInfo[i][10] = returnStr[9]; 
							}else if(type.equals("1")){
								// 事故件数
								toXmlInfo[i][1] = returnStr[0]; 
								// 拥堵件数
								toXmlInfo[i][2] = returnStr[1]; 
								// 施工件数
								toXmlInfo[i][3] = returnStr[2]; 
								// 拥堵报料采用件数
								toXmlInfo[i][4] = returnStr[7]; 
								// 信息扣分
								toXmlInfo[i][5] = returnStr[8]; 
								// 总得分
								toXmlInfo[i][6] = returnStr[9];
							}else if(type.equals("2")){
								// 信息文件省厅采用件数
								toXmlInfo[i][1] = returnStr[3]; 
								// 信息文件公安部采用件数
								toXmlInfo[i][2] = returnStr[4]; 
								// 调研信息材料省厅采用
								toXmlInfo[i][3] = returnStr[5]; 
								// 调研信息材料部局采用
								toXmlInfo[i][4] = returnStr[6]; 
								// 总得分
								toXmlInfo[i][5] = returnStr[9];
							}
						}
					}
				}
			} else {
				Object[][] daDuiInfo = AssessUtil.getAllDaDuiByZhiDuiId(zhiduiId);
				// 遍历所有的支队
				if(daDuiInfo != null) {
					toXmlInfo = new String [daDuiInfo.length][11]; 
					fileName = daDuiInfo[0][1] + "各大队考核信息";
					for( int i=0;i<daDuiInfo.length;i++) {
//						//大队编号
//						toXmlInfo[i][0] = String.valueOf( daDuiInfo[i][0]);
						// 大队名称
						toXmlInfo[i][0] = String.valueOf( daDuiInfo[i][1]);
						returnStr = AssessUtil.getOneDaduiAssess(String.valueOf(daDuiInfo[i][0]),String.valueOf(daDuiInfo[i][1]),startDate, endDate, type);
						if(returnStr != null) {
							if(type.equals("")){
								// 事故件数
								toXmlInfo[i][1] = returnStr[0]; 
								// 拥堵件数
								toXmlInfo[i][2] = returnStr[1]; 
								// 施工件数
								toXmlInfo[i][3] = returnStr[2]; 
								// 信息文件省厅采用件数
								toXmlInfo[i][4] = returnStr[3]; 
								// 信息文件公安部采用件数
								toXmlInfo[i][5] = returnStr[4]; 
								// 调研信息材料省厅采用
								toXmlInfo[i][6] = returnStr[5]; 
								// 调研信息材料部局采用
								toXmlInfo[i][7] = returnStr[6]; 
								// 拥堵报料采用件数
								toXmlInfo[i][8] = returnStr[7]; 
								// 信息扣分
								toXmlInfo[i][9] = returnStr[8]; 
								// 总得分
								toXmlInfo[i][10] = returnStr[9]; 
							}else if(type.equals("1")){
								// 事故件数
								toXmlInfo[i][1] = returnStr[0]; 
								// 拥堵件数
								toXmlInfo[i][2] = returnStr[1]; 
								// 施工件数
								toXmlInfo[i][3] = returnStr[2]; 
								// 拥堵报料采用件数
								toXmlInfo[i][4] = returnStr[7]; 
								// 信息扣分
								toXmlInfo[i][5] = returnStr[8]; 
								// 总得分
								toXmlInfo[i][6] = returnStr[9];
							}else if(type.equals("2")){
								// 信息文件省厅采用件数
								toXmlInfo[i][1] = returnStr[3]; 
								// 信息文件公安部采用件数
								toXmlInfo[i][2] = returnStr[4]; 
								// 调研信息材料省厅采用
								toXmlInfo[i][3] = returnStr[5]; 
								// 调研信息材料部局采用
								toXmlInfo[i][4] = returnStr[6]; 
								// 总得分
								toXmlInfo[i][5] = returnStr[9];
							}
						}
					}
				}
			}
			if(toXmlInfo != null) {
				Array.sort(toXmlInfo, sortItem, false);
				CommonUtility comUtility = new CommonUtility();
				AssessUtil assessUtil = new AssessUtil();
				// 考核基准的取得
				assessUtil.getStandardInfo();
				StringBuffer tabHeader = new StringBuffer("机构名称,");
//				tabHeader.append("交通事故（"+ assessUtil.Point_001024+"分/条）,");
//				tabHeader.append("交通拥堵（"+ assessUtil.Point_001002+"分/条）,");
//				tabHeader.append("占道施工（"+ assessUtil.Point_001023+"分/条）,");
//				tabHeader.append("省厅采用工作信息（"+ assessUtil.Point_001004+"分/篇）,");
//				tabHeader.append("部局采用工作信息（"+ assessUtil.Point_001006+"分/篇）,");
//				tabHeader.append("省厅采用调研信息（"+ assessUtil.Point_001008+"分/篇）,");
//				tabHeader.append("部局采用调研信息（"+ assessUtil.Point_001009+"分/篇）,");
//				tabHeader.append("拥堵报料核对（"+ assessUtil.Point_001001+"分/条）,");
//				tabHeader.append("信息扣分,");
//				tabHeader.append("总得分");
				if(type.equals("")){
					tabHeader.append("交通事故（"+ assessUtil.Point_001024+"分/条）,");
					tabHeader.append("交通拥堵（"+ assessUtil.Point_001002+"分/条）,");
					tabHeader.append("占道施工（"+ assessUtil.Point_001023+"分/条）,");
					tabHeader.append("省厅采用工作信息（"+ assessUtil.Point_001004+"分/篇）,");
					tabHeader.append("部局采用工作信息（"+ assessUtil.Point_001006+"分/篇）,");
					tabHeader.append("省厅采用调研信息（"+ assessUtil.Point_001008+"分/篇）,");
					tabHeader.append("部局采用调研信息（"+ assessUtil.Point_001009+"分/篇）,");
					tabHeader.append("拥堵报料核对（"+ assessUtil.Point_001001+"分/条）,");
					tabHeader.append("信息扣分,");
					tabHeader.append("总得分");
				}else if(type.equals("1")){
					tabHeader.append("交通事故（"+ assessUtil.Point_001024+"分/条）,");
					tabHeader.append("交通拥堵（"+ assessUtil.Point_001002+"分/条）,");
					tabHeader.append("占道施工（"+ assessUtil.Point_001023+"分/条）,");
					tabHeader.append("拥堵报料核对（"+ assessUtil.Point_001001+"分/条）,");
					tabHeader.append("信息扣分,");
					tabHeader.append("总得分");
				}else if(type.equals("2")){
					tabHeader.append("省厅采用工作信息（"+ assessUtil.Point_001004+"分/篇）,");
					tabHeader.append("部局采用工作信息（"+ assessUtil.Point_001006+"分/篇）,");
					tabHeader.append("省厅采用调研信息（"+ assessUtil.Point_001008+"分/篇）,");
					tabHeader.append("部局采用调研信息（"+ assessUtil.Point_001009+"分/篇）,");
					tabHeader.append("总得分");
				}
				
				String sheetName = "考核信息";
				String titleName = fileName+startDate+"至"+endDate+"考核表";
				comUtility.wirteToExcel(response, String.valueOf(tabHeader), fileName, sheetName, toXmlInfo, titleName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得考核信息出错。");
		}
		out.close();
		return null;
	}

	/**
	 * 取得减分详细信息的html串·<br/>
	 * 取得减分详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetCutInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 减分部门id
		String showJgid = StringHelper.obj2str(request.getParameter("showJgid"),"");
		// 减分开始日期
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),"");
		// 减分结束日期
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),"");
		// 机构名称
		String showJgmc = StringHelper.obj2str(request.getParameter("showJgmc"),"");
		AssessUtil assessUtil = new AssessUtil();
		ScoreRecordDao scoreDao = new ScoreRecordDao();
		TrafficNewsFeedsDao trafficNewsFeedsDao = new TrafficNewsFeedsDao();
		Integer gqCount = 0;
		Integer wcycount = 0;
		Integer accCount = 0;
		Integer crowdcount = 0;
		Integer buildCount = 0;
		int[][] kfcounts;
		try {
			// 考核基准的取得
			assessUtil.getStandardInfo();
			int deptLevel = assessUtil.getDeptLevel(showJgid, showJgmc);
			// 支队的情况
			boolean isSelf;
			if(deptLevel == 1) {
				isSelf = showJgid.substring(2,4).equals("00");
				
//				// 事故减分件数
//				accCount = assessUtil.getZhiduiCutCnt("1","1", showJgid, startDate, endDate);
//				accCount = assessUtil.getZhiduiCutCnt("1","2", showJgid, startDate, endDate);
//				// 拥堵减分件数
//				crowdcount = assessUtil.getZhiduiCutCnt("2","1", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getZhiduiCutCnt("2","2", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getZhiduiCutCnt("2","3", showJgid, startDate, endDate);
//				// 占道减分件数
//				buildCount = assessUtil.getZhiduiCutCnt("3","1", showJgid, startDate, endDate);
//				buildCount = assessUtil.getZhiduiCutCnt("3","2", showJgid, startDate, endDate);
			} else {
				isSelf = showJgid.substring(4,6).equals("00");
				// 事故减分件数
//				accCount = assessUtil.getDaduiCutCnt("1","1", showJgid, startDate, endDate);
//				accCount = assessUtil.getDaduiCutCnt("1", "2",showJgid, startDate, endDate);
//				// 拥堵减分件数
//				crowdcount = assessUtil.getDaduiCutCnt("2","1", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getDaduiCutCnt("2","2", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getDaduiCutCnt("2","3", showJgid, startDate, endDate);
//				// 占道减分件数
//				buildCount = assessUtil.getDaduiCutCnt("3", "1", showJgid, startDate, endDate);
//				buildCount = assessUtil.getDaduiCutCnt("3", "2", showJgid, startDate, endDate);
			}
			// 取得报料采用件数 和 不采用件数
			HashMap<String,Integer> countMap = trafficNewsFeedsDao.getFeedbackNum(showJgid, showJgmc, startDate, endDate);
			if(countMap != null) {
				wcycount = countMap.get("wcycount");
				gqCount = countMap.get("gqcount");
				if(gqCount == null) {
					gqCount = 0;
				}
				if(wcycount == null) {
					wcycount = 0;
				}
			}
			Object[][] kfdata = scoreDao.getItemCounts(showJgid, startDate, endDate, isSelf);
			kfcounts = scoreDao.changeItemCounts(kfdata);
			accCount = kfcounts[0][0]+kfcounts[0][1];
			crowdcount = kfcounts[1][0]+kfcounts[1][1]+kfcounts[1][2];
			buildCount = kfcounts[2][0]+kfcounts[2][1];
			gqCount = kfcounts[3][0];
			wcycount = kfcounts[3][1];
			String [] toHtmlStr = new String [8];  
			toHtmlStr[0] = showJgid;
			toHtmlStr[1] = showJgmc;
			toHtmlStr[2] = String.valueOf(accCount);
			toHtmlStr[3] = String.valueOf(crowdcount);
			toHtmlStr[4] = String.valueOf(buildCount);
			toHtmlStr[5] = String.valueOf(gqCount);
			toHtmlStr[6] = String.valueOf(wcycount);
			float zongCut = (accCount+crowdcount+buildCount)*assessUtil.Point_001003 + gqCount*assessUtil.Point_001005;
			toHtmlStr[7] = String.valueOf(zongCut);
			out.write(assessUtil.getShowCutStr(toHtmlStr, startDate, endDate, deptLevel));
			System.out.println(assessUtil.getShowCutStr(toHtmlStr, startDate, endDate, deptLevel));
		} catch (Exception e) {
			out.write("没有取到考核信息s");
			e.printStackTrace();
			logger.error("取得考核减分信息出错。" );
			out.close();
			return null;
		}
		out.close();
		return null;
	}
	
	/**
	 * 额外计分信息录入
	 */
	public ActionForward doScoreRecordEdit(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jgid = request.getParameter("jgid");
		String jgmc = request.getParameter("jgmc");
		String date = request.getParameter("date");
		String aid = request.getParameter("aid");
		String items = request.getParameter("items");
		String reason = request.getParameter("reason");
		String reasons = request.getParameter("reasons");
		
		if(jgid == null || jgmc == null || date == null || items == null || reasons == null || reason == null){
			logger.error("计分信息录入->页面参数传递缺失！" );
			return null;
		}
		
		boolean isSuccess = dao.add(jgid, jgmc, date, aid, items, reasons, reason);
		
		String responseStr = "";
		if(isSuccess){
			responseStr = "success";
		}else{
			responseStr = "failed";
		}
		out.write(responseStr);
		return null;
	}

	
	/**
	 * 额外计分信息录入
	 */
	public ActionForward doScoreRecordDelete(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String jgid = request.getParameter("jgid");
		String date = request.getParameter("date");
		
		if(jgid == null || date == null){
			logger.error("计分信息录入->页面参数传递缺失！" );
			return null;
		}
		
		boolean isSuccess = dao.delete(jgid, date);
		
		out.write(isSuccess+"");
		return null;
	}

}
