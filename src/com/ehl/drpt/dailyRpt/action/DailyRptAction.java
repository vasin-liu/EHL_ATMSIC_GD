package com.ehl.drpt.dailyRpt.action;
/**
 * 
 * @======================================================================================================================================
 * @����˵��: ���˵�·��ͨ��ȫ�����ձ�����Action
 * @�����ߣ�Jason
 * @�������� 2010-01-11
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
		 * @����:Jason 
		 * @�汾�ţ�1.0
		 * @����˵�������˵�·��ͨ��ȫ�����ձ�ͳ��
		 * @������
		 * @���أ�
		 * @�������ڣ�2010-01-11
		 */
		public ActionForward doStat(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //��λid
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
			
			out.write(dao.statistics(department, startTime, endTime));
			out.close();
			return null;
		} 
		
	    /**
		 * @����:Jason
		 * @�汾�ţ�1.0
		 * @����˵�������˵�·��ͨ��ȫ�����ܱ�ͳ��
		 * �����ܶ��û��ϱ�����������
		 * @������
		 * @���أ�
		 * @�������ڣ�2010-01-11
		 */
		public ActionForward doWeekStat(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //��λid
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
			out.write(dao.weekStat(department, startTime, endTime));
			out.close();
			return null;
		} 
		/**
		 * @����:Jason
		 * @�汾�ţ�1.0
		 * @����˵�������˵�·��ͨ��ȫ��������ͳ��
		 * @������
		 * @���أ�
		 * @�������ڣ�2010-01-11
		 */	
		public ActionForward doTfmStat(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String department = StringHelper.obj2str(request.getParameter("departmentId"),""); //��λid
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��

			out.write(dao.tfmStat(department, startTime, endTime));
			out.close();
			return null;
		} 

		/**
		 * @����:wuyl
		 * @�汾�ţ�1.0
		 * @����˵�����ܱ�����Excel
		 * @������
		 * @���أ�
		 * @�������ڣ�2010-1-25 14:06
		 */	
		public ActionForward doWeekOutExcel(Action action, HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		//	response.setContentType("text/xml; charset=UTF-8");
			//PrintWriter out = response.getWriter();
			DailyRptDao  dao = new DailyRptDao();
			String fileName = StringHelper.obj2str(request.getParameter("fileName"),""); //����
			String title = StringHelper.obj2str(request.getParameter("title"),""); //�ļ����ݱ�˵��
			String tabHeader = StringHelper.obj2str(request.getParameter("tabHeader"),""); //��ͷ
			String tabDataStr = StringHelper.obj2str(request.getParameter("tabData"),""); //���ڲ�����
			String[] tabStr ="1,Ͷ�뾯�����˴Σ�,11,�������ݿۻ�������ʻ֤������,2,�������������Σ�,12,ж��ת�ˣ��ˣ�,3,����������վ������,13,�����ʻ�˼Ƿ֣��ˣ�,4,������ʱִ�ڵ㣨����,14,����רҵ������ҵ������,5,�����˳���������,15,�Ų���������������,6,���鴦��ͨΥ����Ϊ����,16,ͨ���㲥�����ӿ�չ�������Σ�,7,�鴦������ʻ����,17,�����������ϡ�����������ʾ�ƣ��ݡ��飩,8,�鴦�ͳ���Ա����,18,������������Ӧ��Ԥ�����Σ�,9,�鴦ƣ�ͼ�ʻ����,19,Ӧ���赼����������������,10,�鴦�ƺ��ʻ����,20,����Σ��·�Σ�����".split(",");
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
			
			tabHeader="���,��Ŀ,����,���,��Ŀ,����";
			title = "���˽�ͨ������ͳ���ձ�����������";
			wirteToExcel(response,tabHeader,"exportExcel.xls","sheet1",tabData,title);
			//����sm��Excel��������
//			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
//			saveAsExcelCore.setTabHeader(tabHeader);
//			//�����ļ����ƣ�sheetName��֧��
//			//fileName = new String(fileName.getBytes(),"ISO8859_1");
//			saveAsExcelCore.setFileName(fileName);
//			saveAsExcelCore.setTitleName(title);
//			saveAsExcelCore.wirteToExcel(fileName, title,tabHeader, tabData, response);
//			//out.close();
			return null;
		} 
		
		/**
		 * @�汾�ţ�1.0
		 * @����˵���������б���HTML����
		 * @������fileName ת�����ļ��� title �ļ����� tabHeader �ļ���ͷ dataSql ��ȡת�����ݵ�SQL
		 * @���أ�
		 * @���ߣ�Jason
		 * @�������ڣ�2010-01-25 
		 * */
		public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			//��������
			String fileName = ""; //�ļ�����
			String titleName = ""; //����		
			String tabHeader = "��·����,·������,��ͨ���������Σ�,�ͳ������Σ�,�Լݳ������Σ�"; //��ͷ
			String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
			String departmentID = StringHelper.obj2str(request.getParameter("department"),""); //����ʱ��
			
			if("".equals(sql)){
				logger.error("[����ͳ��]" + "����sqlΪ�մ���");
				return null;
			}

			System.out.println(sql);
			fileName ="Ͻ���ճ���������·����"+startTime+"����"+endTime+"�յ�ͳ��";
			titleName ="Ͻ���ճ���������·��ͳ�ƽ�������ڣ�"+startTime+"����"+endTime+"�գ�";
			if (tabHeader == null || sql == null)
			{
				return null;
			}
			String sheetName = startTime+"��"+endTime;
			DailyRptDao  dao = new DailyRptDao();
			Object[][] tabData= dao.tfmStatExcel(departmentID, startTime, endTime);
			
			//����д��Excel
			//����sm��Excel��������
			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
			saveAsExcelCore.setTabHeader(tabHeader);
			//�����ļ����ƣ�sheetName��֧��
			//fileName = new String(fileName.getBytes(),"ISO8859_1");
			saveAsExcelCore.setFileName(fileName);
			saveAsExcelCore.setTitleName(titleName);
			saveAsExcelCore.wirteToExcel(sheetName, titleName,tabHeader, tabData, response);
			//out.close();
			return null;
		}
		
		/**
		 * @�汾�ţ�1.0
		 * @����˵���������б���HTML����
		 * @������fileName ת�����ļ��� title �ļ����� tabHeader �ļ���ͷ dataSql ��ȡת�����ݵ�SQL
		 * @���أ�
		 * @���ߣ�Jason
		 * @�������ڣ�2010-01-25 
		 * */
		/*
		//�Լ���д�ķ���
		public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			//��������
			String fileName = ""; //�ļ�����
			String titleName = ""; //����		
			String tabHeader = "��·����,·������,��ͨ���������Σ�,�ͳ������Σ�,�Լݳ������Σ�"; //��ͷ
			String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
			String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
			String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
			if("".equals(sql)){
				logger.error("[����ͳ��]" + "����sqlΪ�մ���");
				return null;
			}

			System.out.println(sql);
			fileName ="Ͻ���ճ���������·����"+startTime+"����"+endTime+"�յ�ͳ��";
			titleName ="Ͻ���ճ���������·��ͳ�ƽ�������ڣ�"+startTime+"����"+endTime+"�գ�";
			if (tabHeader == null || sql == null)
			{
				return null;
			}
			String sheetName = startTime+"��"+endTime;
			//����д��Excel
			try{
				if(!wirteToExcel(response,tabHeader,fileName,sheetName,sql,titleName)){
					return null;
				}
			}
		    catch (Exception e) {
				Console.infoprintln("doFieldsToExcel fail:" + e.getMessage());
				logger.error("[����ͳ��]" + "ת��Excel�������б���HTML���������");
				return null;
	        }
			
			return null;
		}
        */
		/**
		 * @�汾�ţ�1.0
		 * @����˵����дExcel�ļ�
		 * @�������Ƿ�ִ�гɹ�.true���ɹ���false:ʧ��.
		 * @���أ��Ƿ���سɹ�,true-�ɹ�;false-ʧ��.
		 * @���ߣ�Jason
		 * @�������ڣ�2010-1-25 
		 * */
		public boolean wirteToExcel(HttpServletResponse response,String tabHeader,String fileName,String sheetName,String tabData,String titleName) throws Exception {
			boolean bResult = false; //ִ�н��
					
			List lstTabHeader = StringUtil.divide(tabHeader, ","); //��ͷת��ΪList
			short columnCount = (short) lstTabHeader.size(); //��ͷ����
			int titol=0;
			int titol1=0;
		    
			if (lstTabHeader == null || columnCount == 0 ) //û�б�ͷ
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
		  	    if (oResult == null || oResult.length == 0 || oResult[0].length==0 ) //ûͳ�ƽ��
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
			boolean bResult = false; //ִ�н��
					
			List lstTabHeader = StringUtil.divide(tabHeader, ","); //��ͷת��ΪList
			short columnCount = (short) lstTabHeader.size(); //��ͷ����
			int titol=0;
			int titol1=0;
		    
			if (lstTabHeader == null || columnCount == 0 ) //û�б�ͷ
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
		  	    if (oResult == null || oResult.length == 0 || oResult[0].length==0 ) //ûͳ�ƽ��
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
		 * @�汾�ţ�1.0
		 * @����˵��������ת��ִ�к���
		 * @������oResult �����. out OutputStream. sheetName Sheet�� titleName ������ lstTabHeader ��ֵ�б��ͷ���ơ�
		 * @���أ��Ƿ���سɹ�,true-�ɹ�;false-ʧ��.
		 * @���ߣ�Jason
		 * @�������ڣ�2010-1-25 
		 * */
		private boolean executeToExcel(Object[][] oResult,OutputStream out,String sheetName,String titleName,short columnCount,List lstTabHeader) throws Exception {
			final short ColumnWidth=4000; //�п�
			
			try {

				//����HSSFWorkBook����
			    HSSFWorkbook hwb = new HSSFWorkbook();
			    
			    //����HSSFSheet����
			    HSSFSheet sheet = hwb.createSheet();
			    hwb.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);
			    
			    //�����ں�ҳ��д��ҳ��
			    HSSFFooter footer = sheet.getFooter();
			    footer.setCenter("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
			    footer.setRight(HSSFFooter.font("Stencil-Normal", "Italic") + HSSFFooter.fontSize( (short) 10) + HSSFFooter.date());
			    
			    //������������
			    HSSFFont headerFont = hwb.createFont();
			    headerFont.setFontHeightInPoints( (short) 16);
			    headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    
			    //������ʽ
			    HSSFCellStyle headerStyle = hwb.createCellStyle();
			    headerStyle.setFont(headerFont);
			    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

				//�ù������һ��������
				HSSFRow row = sheet.createRow(0);
				row.setHeight( (short) 500);
				HSSFCell cel = row.createCell( (short) 0);  
				cel.setEncoding(HSSFCell.ENCODING_UTF_16);
				cel.setCellStyle(headerStyle);
				cel.setCellValue(titleName);
				
				//��������
			    HSSFFont columnNameFont = hwb.createFont();
			    columnNameFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    
			    //������Ԫ����ʽ
			    HSSFCellStyle style = hwb.createCellStyle();
			    style.setFont(columnNameFont);
			    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    
			    //������ڶ���������
			    row = sheet.createRow(1);
			    
			    //������д��ڶ��еĵ�Ԫ����,�ϲ����ⵥԪ��	    			    
			    sheet.addMergedRegion(new Region(0,(short)0,0,(short)(columnCount-1)));
			    String strColumnName = new String("");
			    for (short i = 0; i < columnCount; i++) {
			      strColumnName =(String) lstTabHeader.get(i);
			      if (strColumnName.equals("1")){
				      sheet.setColumnWidth( (short) i,(short)0); //�����п�		    	  
			      }else{
				      sheet.setColumnWidth( (short) i, (short)ColumnWidth); //�����п�		    	  		    	  
			      }
			      cel = row.createCell(i);
			      cel.setCellStyle(style);
			      cel.setEncoding(HSSFCell.ENCODING_UTF_16);
			      cel.setCellValue(strColumnName);
			    }
			    	    
			    //�����е�Ԫ����ʽ
			    HSSFCellStyle dbStyle = hwb.createCellStyle();
			    dbStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    dbStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    dbStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    dbStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    
			    //��������Ϊ�ַ�����ʽ
			    HSSFCellStyle strStyle = hwb.createCellStyle();
			    strStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			    strStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			    strStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    strStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    strStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    strStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    		    
			    //��sheet��д��������
				if (oResult != null)
				{
				    for (int i = 0; i < oResult.length; i++) {
						row = sheet.createRow(i+2);
						for (short j = 0; j < oResult[i].length; j++) {
							cel = row.createCell(j);
							//�����ַ�����
							cel.setEncoding(HSSFCell.ENCODING_UTF_16); //֧������
							cel.setCellStyle(dbStyle);//���õ�Ԫ����ʽ
							cel.setCellValue(StringHelper.obj2str(oResult[i][j],"")); //���õ�Ԫ��ֵ	
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
		
		/**�������ݷ�����*/
		private MaxRoadFlowCore flowCore = new MaxRoadFlowCore();
		
		/**
		 * ����ͳ��
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
