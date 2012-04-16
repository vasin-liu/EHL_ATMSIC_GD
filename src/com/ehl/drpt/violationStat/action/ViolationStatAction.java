package com.ehl.drpt.violationStat.action;

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
import com.ehl.drpt.common.Factory;
import com.ehl.sm.common.util.StringUtil;

/**
 * @======================================================================================================================================
 * @类型说明:严重违法信息统计
 * @创建者：吴玉良
 * @创建日期 2009-5-23 11:16:24
 * @======================================================================================================================================
 */
public class ViolationStatAction extends Controller{
	Logger logger = Logger.getLogger(ViolationStatAction.class);

	/**
	 * @版本号：1.0
	 * @函数说明：统计严重违法信息
	 * @参数：
	 * @返回：统计结果
	 * @作者：吴玉良
	 * @创建日期：2010-1-13 
	 * */
	public ActionForward doStatistic(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
		String depart = StringHelper.obj2str(request.getParameter("depart"),""); //机构ID
		String zhidui = StringHelper.obj2str(request.getParameter("zhidui"),""); //支队ID
		String dadui = StringHelper.obj2str(request.getParameter("dadui"),""); //大队ID
		if("0000".equals(depart.substring(2, 6))){
			//总队用户
			out.write(Factory.getViolationStatDaoImpl().getStatistics(startTime, endTime, depart,zhidui,dadui));
    	}else if("00".equals(depart.substring(4,6))){
        	//支队用户
			out.write(Factory.getViolationStatDaoImpl().getStatistics(startTime, endTime, depart,dadui));
    	}else {
            //大队用户
			out.write(Factory.getViolationStatDaoImpl().getStatistics(startTime, endTime, depart));
    	}
		out.close();
		return null;
	}

	/**
	 * @版本号：1.0
	 * @函数说明：生成列表框的HTML代码
	 * @参数：fileName 转存后的文件名 title 文件标题 tabHeader 文件表头 dataSql 获取转存内容的SQL
	 * @返回：
	 * @作者：吴玉良
	 * @创建日期：2009-12-8 
	 * */
	public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//接受数据
		String fileName = ""; //文件名称
		String titleName = ""; //标题		
		String tabHeader = "查处日期,所属辖区,违法类型,违法数量"; //表头
		String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
		if("".equals(sql)){
			logger.error("[春运统计]" + "参数sql为空错误");
			return null;
		}

		System.out.println(sql);
		fileName ="车辆违法在"+startTime+"日至"+endTime+"日的统计";
		titleName ="车辆违法统计结果（日期："+startTime+"日至"+endTime+"日）";
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
