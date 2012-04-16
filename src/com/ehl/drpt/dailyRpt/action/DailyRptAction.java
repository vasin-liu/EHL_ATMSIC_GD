package com.ehl.drpt.dailyRpt.action;
/**
 * 
 * @======================================================================================================================================
 * @类型说明: 春运道路交通安全管理日报操作Action
 * @创建者：Jason
 * @创建日期 2010-01-11
 * @======================================================================================================================================
 */
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.appframe.Console;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.output.SaveAsExcelCore;
import com.ehl.drpt.dailyRpt.core.MaxRoadFlowCore;
import com.ehl.drpt.dailyRpt.dao.DailyRptDao;
import com.ehl.drpt.violationStat.action.ViolationStatAction;
import com.ehl.sm.common.util.StringUtil;

public class DailyRptAction extends Controller{
	Logger logger = Logger.getLogger(DailyRptAction.class);
	
	   /**
		 * @作者:Jason 
		 * @版本号：1.0
		 * @函数说明：春运道路交通安全管理日报统计
		 * @参数：
		 * @返回：
		 * @创建日期：2010-01-11
		 */
		public ActionForward doStat(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //单位id
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
			
			out.write(dao.statistics(department, startTime, endTime));
			out.close();
			return null;
		} 
		
	    /**
		 * @作者:Jason
		 * @版本号：1.0
		 * @函数说明：春运道路交通安全管理周报统计
		 * 用于总队用户上报给公安部。
		 * @参数：
		 * @返回：
		 * @创建日期：2010-01-11
		 */
		public ActionForward doWeekStat(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //单位id
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
			out.write(dao.weekStat(department, startTime, endTime));
			out.close();
			return null;
		} 
		/**
		 * @作者:Jason
		 * @版本号：1.0
		 * @函数说明：春运道路交通安全管理流量统计
		 * @参数：
		 * @返回：
		 * @创建日期：2010-01-11
		 */	
		public ActionForward doTfmStat(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //单位id
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间

			out.write(dao.tfmStat(department, startTime, endTime));
			out.close();
			return null;
		} 

		/**
		 * @作者:wuyl
		 * @版本号：1.0
		 * @函数说明：周报表导出Excel
		 * @参数：
		 * @返回：
		 * @创建日期：2010-1-25 14:06
		 */	
		public ActionForward doWeekOutExcel(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		//	response.setContentType("text/xml; charset=UTF-8");
			//PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String fileName = StringHelper.obj2str(request.getParameter("fileName"),""); //名称
			String title = StringHelper.obj2str(request.getParameter("title"),""); //文件数据表说明
			String tabHeader = StringHelper.obj2str(request.getParameter("tabHeader"),""); //表头
			String tabDataStr = StringHelper.obj2str(request.getParameter("tabData"),""); //表内部数据
			String[] tabStr ="1,投入警力（人次）,11,吊销或暂扣机动车驾驶证（本）,2,出动警车（辆次）,12,卸客转运（人）,3,启动检查服务站（个）,13,清理驾驶人记分（人）,4,设置临时执勤点（个）,14,深入专业运输企业（个）,5,检查客运车辆（辆）,15,排查隐患车辆（辆）,6,共查处交通违法行为（起）,16,通过广播、电视开展宣传（次）,7,查处超速行驶（起）,17,发放宣传材料、设置宣传提示牌（份、块）,8,查处客车超员（起）,18,启动恶劣天气应急预案（次）,9,查处疲劳驾驶（起）,19,应急疏导、分流车辆（辆）,10,查处酒后驾驶（起）,20,整治危险路段（处）".split(",");
			if("".equals(tabDataStr)){
				return null;
			}
			String[] tabDataSplit=tabDataStr.split(",");
			String[][] tabData= new String[10][6];
			for(int i=0,j=0,k=0,m=0;i<tabData.length;i++){
				for(j=0;j<tabData[0].length;j++){
					if(j==2 || j==5){
						tabData[i][j]=tabDataSplit[k++];
					}else{
						tabData[i][j]=tabStr[m++];
					}
				}
			}
			
			tabHeader="序号,项目,数量,序号,项目,数量";
			title = "春运交通管理工作统计日报表（公安部）";
			wirteToExcel(response,tabHeader,"exportExcel.xls","sheet1",tabData,title);
			//调用sm的Excel导出方法
//			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
//			saveAsExcelCore.setTabHeader(tabHeader);
//			//中文文件名称，sheetName不支持
//			//fileName = new String(fileName.getBytes(),"ISO8859_1");
//			saveAsExcelCore.setFileName(fileName);
//			saveAsExcelCore.setTitleName(title);
//			saveAsExcelCore.wirteToExcel(fileName, title,tabHeader, tabData, response);
//			//out.close();
			return null;
		} 
		
		/**
		 * @版本号：1.0
		 * @函数说明：生成列表框的HTML代码
		 * @参数：fileName 转存后的文件名 title 文件标题 tabHeader 文件表头 dataSql 获取转存内容的SQL
		 * @返回：
		 * @作者：Jason
		 * @创建日期：2010-01-25 
		 * */
		public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			//接受数据
			String fileName = ""; //文件名称
			String titleName = ""; //标题		
			String tabHeader = "道路名称,路段名称,交通流量（车次）,客车（车次）,自驾车（车次）"; //表头
			String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
			String departmentID = StringHelper.obj2str(request.getParameter("department"),""); //结束时间
			
			if("".equals(sql)){
				logger.error("[春运统计]" + "参数sql为空错误");
				return null;
			}

			System.out.println(sql);
			fileName ="辖区日车流量最大的路段在"+startTime+"日至"+endTime+"日的统计";
			titleName ="辖区日车流量最大的路段统计结果（日期："+startTime+"日至"+endTime+"日）";
			if (tabHeader == null || sql == null)
			{
				return null;
			}
			String sheetName = startTime+"至"+endTime;
			DailyRptDao  dao = new DailyRptDao();
			Object[][] tabData= dao.tfmStatExcel(departmentID, startTime, endTime);
			
			//数据写入Excel
			//调用sm的Excel导出方法
			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
			saveAsExcelCore.setTabHeader(tabHeader);
			//中文文件名称，sheetName不支持
			//fileName = new String(fileName.getBytes(),"ISO8859_1");
			saveAsExcelCore.setFileName(fileName);
			saveAsExcelCore.setTitleName(titleName);
			saveAsExcelCore.wirteToExcel(sheetName, titleName,tabHeader, tabData, response);
			//out.close();
			return null;
		}
		
		/**
		 * @版本号：1.0
		 * @函数说明：生成列表框的HTML代码
		 * @参数：fileName 转存后的文件名 title 文件标题 tabHeader 文件表头 dataSql 获取转存内容的SQL
		 * @返回：
		 * @作者：Jason
		 * @创建日期：2010-01-25 
		 * */
		/*
		//自己重写的方法
		public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			//接受数据
			String fileName = ""; //文件名称
			String titleName = ""; //标题		
			String tabHeader = "道路名称,路段名称,交通流量（车次）,客车（车次）,自驾车（车次）"; //表头
			String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
			if("".equals(sql)){
				logger.error("[春运统计]" + "参数sql为空错误");
				return null;
			}

			System.out.println(sql);
			fileName ="辖区日车流量最大的路段在"+startTime+"日至"+endTime+"日的统计";
			titleName ="辖区日车流量最大的路段统计结果（日期："+startTime+"日至"+endTime+"日）";
			if (tabHeader == null || sql == null)
			{
				return null;
			}
			String sheetName = startTime+"至"+endTime;
			//数据写入Excel
			try{
				if(!wirteToExcel(response,tabHeader,fileName,sheetName,sql,titleName)){
					return null;
				}
			}
		    catch (Exception e) {
				Console.infoprintln("doFieldsToExcel fail:" + e.getMessage());
				logger.error("[春运统计]" + "转存Excel：生成列表框的HTML代码出错误");
				return null;
	        }
			
			return null;
		}
        */
		/**
		 * @版本号：1.0
		 * @函数说明：写Excel文件
		 * @参数：是否执行成功.true：成功；false:失败.
		 * @返回：是否加载成功,true-成功;false-失败.
		 * @作者：Jason
		 * @创建日期：2010-1-25 
		 * */
		public boolean wirteToExcel(HttpServletResponse response,String tabHeader,String fileName,String sheetName,String tabData,String titleName) throws Exception {
			boolean bResult = false; //执行结果
					
			List lstTabHeader = StringUtil.divide(tabHeader, ","); //表头转换为List
			short columnCount = (short) lstTabHeader.size(); //表头列数
			int titol=0;
			int titol1=0;
		    
			if (lstTabHeader == null || columnCount == 0 ) //没有表头
			{	
				return false;
			}
			fileName = new String(fileName.getBytes(),"ISO8859_1");
			try {
				response.reset();
		  	    response.setContentType("application/vnd.ms-excel; charset=utf-8");
		  	    response.setHeader("content-disposition","attachment;filename=exportExcel.xls");
		  	    OutputStream out = new BufferedOutputStream(response.getOutputStream());
				
		  	    Object[][] oResult = DBHandler.getMultiResult(tabData);
		  	    if (oResult == null || oResult.length == 0 || oResult[0].length==0 ) //没统计结果
				{	
					return false;
				}
		  	    
		  	    bResult=executeToExcel(oResult,out,sheetName,titleName,columnCount, lstTabHeader);
		  	    out.close();
		  	    	  	    
			}
		    catch (Exception e) {
		    	Console.infoprintln("wirteToExcel Fail:"+e.getMessage());
		    	return false;
	        }
		    
		    return  bResult;

		}

		
		public boolean wirteToExcel(HttpServletResponse response,String tabHeader,String fileName,String sheetName,Object[][] oResult,String titleName) throws Exception {
			boolean bResult = false; //执行结果
					
			List lstTabHeader = StringUtil.divide(tabHeader, ","); //表头转换为List
			short columnCount = (short) lstTabHeader.size(); //表头列数
			int titol=0;
			int titol1=0;
		    
			if (lstTabHeader == null || columnCount == 0 ) //没有表头
			{	
				return false;
			}
			fileName = new String(fileName.getBytes(),"ISO8859_1");
			try {
				response.reset();
		  	    response.setContentType("application/vnd.ms-excel; charset=utf-8");
		  	    response.setHeader("content-disposition","attachment;filename=exportExcel.xls");
		  	    OutputStream out = new BufferedOutputStream(response.getOutputStream());
				
		  	    //Object[][] oResult = DBHandler.getMultiResult(tabData);
		  	    if (oResult == null || oResult.length == 0 || oResult[0].length==0 ) //没统计结果
				{	
					return false;
				}
		  	    
		  	    bResult=executeToExcel(oResult,out,sheetName,titleName,columnCount, lstTabHeader);
		  	    out.close();
		  	    	  	    
			}
		    catch (Exception e) {
		    	Console.infoprintln("wirteToExcel Fail:"+e.getMessage());
		    	return false;
	        }
		    
		    return  bResult;

		}
		
		/**
		 * @版本号：1.0
		 * @函数说明：数据转换执行函数
		 * @参数：oResult 结果集. out OutputStream. sheetName Sheet名 titleName 标题名 lstTabHeader 数值列表表头名称。
		 * @返回：是否加载成功,true-成功;false-失败.
		 * @作者：Jason
		 * @创建日期：2010-1-25 
		 * */
		private boolean executeToExcel(Object[][] oResult,OutputStream out,String sheetName,String titleName,short columnCount,List lstTabHeader) throws Exception {
			final short ColumnWidth=4000; //列宽
			
			try {

				//创建HSSFWorkBook对象
			    HSSFWorkbook hwb = new HSSFWorkbook();
			    
			    //创建HSSFSheet对象
			    HSSFSheet sheet = hwb.createSheet();
			    hwb.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);
			    
			    //将日期和页数写入页脚
			    HSSFFooter footer = sheet.getFooter();
			    footer.setCenter("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
			    footer.setRight(HSSFFooter.font("Stencil-Normal", "Italic") + HSSFFooter.fontSize( (short) 10) + HSSFFooter.date());
			    
			    //创建标题字体
			    HSSFFont headerFont = hwb.createFont();
			    headerFont.setFontHeightInPoints( (short) 16);
			    headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    
			    //标题样式
			    HSSFCellStyle headerStyle = hwb.createCellStyle();
			    headerStyle.setFont(headerFont);
			    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

				//用工作表第一行做标题
				HSSFRow row = sheet.createRow(0);
				row.setHeight( (short) 500);
				HSSFCell cel = row.createCell( (short) 0);  
				cel.setEncoding(HSSFCell.ENCODING_UTF_16);
				cel.setCellStyle(headerStyle);
				cel.setCellValue(titleName);
				
				//定义字体
			    HSSFFont columnNameFont = hwb.createFont();
			    columnNameFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    
			    //列名单元格样式
			    HSSFCellStyle style = hwb.createCellStyle();
			    style.setFont(columnNameFont);
			    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    
			    //工作表第二行作列名
			    row = sheet.createRow(1);
			    
			    //将列名写入第二行的单元格中,合并标题单元格	    			    
			    sheet.addMergedRegion(new Region(0,(short)0,0,(short)(columnCount-1)));
			    String strColumnName = new String("");
			    for (short i = 0; i < columnCount; i++) {
			      strColumnName =(String) lstTabHeader.get(i);
			      if (strColumnName.equals("1")){
				      sheet.setColumnWidth( (short) i,(short)0); //设置列宽		    	  
			      }else{
				      sheet.setColumnWidth( (short) i, (short)ColumnWidth); //设置列宽		    	  		    	  
			      }
			      cel = row.createCell(i);
			      cel.setCellStyle(style);
			      cel.setEncoding(HSSFCell.ENCODING_UTF_16);
			      cel.setCellValue(strColumnName);
			    }
			    	    
			    //数据列单元格样式
			    HSSFCellStyle dbStyle = hwb.createCellStyle();
			    dbStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    dbStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    dbStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    dbStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    
			    //数据类型为字符的样式
			    HSSFCellStyle strStyle = hwb.createCellStyle();
			    strStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			    strStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			    strStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    strStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    strStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    strStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    		    
			    //向sheet中写入数据列
				if (oResult != null)
				{
				    for (int i = 0; i < oResult.length; i++) {
						row = sheet.createRow(i+2);
						for (short j = 0; j < oResult[i].length; j++) {
							cel = row.createCell(j);
							//按照字符处理
							cel.setEncoding(HSSFCell.ENCODING_UTF_16); //支持中文
							cel.setCellStyle(dbStyle);//设置单元格样式
							cel.setCellValue(StringHelper.obj2str(oResult[i][j],"")); //设置单元格值	
						}
					}
				}
			    hwb.write(out);
		    }
		    catch (Exception e) {
		    	Console.infoprintln("executeToExcel Fail:"+e.getMessage());
		    	return false;
	        }
		    return true;
		}
		
		/**流量数据访问类*/
		private MaxRoadFlowCore flowCore = new MaxRoadFlowCore();
		
		/**
		 * 流量统计
		 */
		public ActionForward doFlowStatis(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			String jgid = request.getParameter("jgid");
			String sdate = request.getParameter("sdate");
			String edate = request.getParameter("edate");
			String xml = flowCore.statis(jgid, sdate, edate);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			logger.info("xml:\n"+xml);
			out.write(xml);
			out.close();
			return null;
		} 
}
