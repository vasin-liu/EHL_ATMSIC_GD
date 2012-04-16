package com.ehl.drpt.violationStat.action;
/**
 * 
 * @======================================================================================================================================
 * @类型说明: 春运客运车辆严重交通违法信息管理操作Action
 * @创建者：wkz
 * @创建日期 2010-01-13
 * @======================================================================================================================================
 */
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.output.SaveAsExcelCore;
import com.ehl.drpt.dailyRpt.dao.DailyRptDao;
import com.ehl.drpt.violationStat.dao.ViolationDao;
import com.ehl.sm.common.Constants;
import com.ehl.sm.common.tree.PageCustomTree;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.sm.sysmanage.LogManage;
import com.ehl.sm.pcs.DepartmentManage;
import com.ehl.sm.sysmanage.LogManage;

public class ViolationAction extends Controller{
	Logger logger = Logger.getLogger(ViolationStatAction.class);
	/**
	 * @作者:wkz
	 * @版本号：1.0
	 * @函数说明：春运客运车辆严重交通违法信息新增或修改
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-11
	 */
	public ActionForward doAddOrUpdate(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		HttpSession session = request.getSession();
		String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
		PrintWriter out = response.getWriter();
		ViolationDao violationdao = new ViolationDao();
		try {
			int insertOrUpdate = StringHelper.obj2int(request.getParameter("insertOrUpdate"), 0);
			String xmlStr = request.getParameter("xmlStr");
			String[] strObj = DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
			if(insertOrUpdate == 0){
				out.write(violationdao.add(xmlStr,strObj));
				LogManage.writeEvent(LCODE, "添加客运车违法记录");
			}else{
				out.write(violationdao.update(xmlStr));
				LogManage.writeEvent(LCODE, "编辑客运车违法记录");
			}
		} catch (Exception ex) {
			out.write("操作失败，请重试！");
			out.close();
			return null;
		}
		out.close();
		return null;
	}
	
	/**
	 * @作者:wkz
	 * @版本号：1.0
	 * @函数说明：按参数查找对应违法信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	public ActionForward doGetDataById(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ViolationDao violationdao = new ViolationDao();
		String bh = request.getParameter("bh");
		out.write(violationdao.getDateById(bh));
		out.close();
		return null;
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：wkz
	 * @版本号：
	 * @函数说明：删除
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doDelete(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml"); // 指定输出的XML格式
		String bh = request.getParameter("bh") == null ? "" : request.getParameter("bh");
		ViolationDao violationdao = new ViolationDao();
		out.write(violationdao.delete(bh)?"记录被成功删除！":"操作失败，请重试！");
		LogManage.writeEvent(LCODE, "删除客运车违法记录");
		out.close();
		return null;
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：wkz
	 * @版本号：
	 * @函数说明：生成列表框的HTML代码
	 * @参数：fileName 转存后的文件名 title 文件标题 tabHeader 文件表头 dataSql 获取转存内容的SQL
	 * @返回：
	 * @创建日期：2010-01-29
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("content-disposition","attachment;filename=exportExcel.xls");

		//接受数据
		String fileName = ""; //文件名称
		String titleName = ""; //标题		
		//String tabHeader = "车牌号,违法事实,车属单位（人）,查处日期,路段,驾驶人,驾驶证号,处理情况"; //表头
		
		//wujh edit
		String tabHeader = "车牌号,违法事实,核载人数,实载人数,超载人数,车属单位（人）,查处日期,路段,驾驶人,驾驶证号,处理情况"; //表头
		String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
		
		if (tabHeader == null || sql == null)
		{
			return null;
		}
		if("".equals(sql)){
			logger.error("[客运车违法记录查询导出EXCEL]" + "参数sql为空错误");
			return null;
		}

		System.out.println(sql);
//		if(startTime.equals(endTime)){
//			fileName ="客运车违法在"+startTime+"日的记录";
//		}else{
//			fileName ="客运车违法在"+startTime+"日至"+endTime+"日的记录";
//		}
		fileName = "excelExport";
		titleName = "春运客运车辆严重交通违法统计表";
		String sheetName = "春运客运车辆严重交通违法统计表";
		
		wirteToExcel(response,tabHeader,fileName,sheetName,sql,titleName);
//		
//		ViolationDao  dao = new ViolationDao();
//		Object[][] tabData= dao.SearchExcel(sql);
//		
//		
//		//数据写入Excel
//		//调用sm的Excel导出方法
//		SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
//		saveAsExcelCore.setTabHeader(tabHeader);
//		//中文文件名称，sheetName不支持
//		fileName = new String(fileName.getBytes(),"ISO8859_1");
//		//saveAsExcelCore.setFileName(fileName);
//
//		System.out.println(fileName);
//		saveAsExcelCore.setTitleName(titleName);
//		System.out.println(titleName);
//		saveAsExcelCore.wirteToExcel(sheetName, titleName,tabHeader, tabData, response);
//		
		return null;
	}
	
	/**
	 * @版本号：1.0
	 * @函数说明：写Excel文件
	 * @参数：是否执行成功.true：成功；false:失败.
	 * @返回：是否加载成功,true-成功;false-失败.
	 * @作者：吴玉良
	 * @创建日期：2009-12-8 
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
		//fileName = new String(fileName.getBytes(),"ISO8859_1");
		fileName = new String(fileName.getBytes(),"UTF-8");
		try {
			response.reset();
	  	    response.setContentType("application/vnd.ms-excel; charset=UTF-8");
	  	   // response.setHeader("content-disposition","filename=" + fileName +".xls");
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

	/**
	 * @版本号：1.0
	 * @函数说明：数据转换执行函数
	 * @参数：oResult 结果集. out OutputStream. sheetName Sheet名 titleName 标题名 lstTabHeader 数值列表表头名称。
	 * @返回：是否加载成功,true-成功;false-失败.
	 * @作者：吴玉良
	 * @创建日期：2009-12-8 
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
}
