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
 * @����˵��:����Υ����Ϣͳ��
 * @�����ߣ�������
 * @�������� 2009-5-23 11:16:24
 * @======================================================================================================================================
 */
public class ViolationStatAction extends Controller{
	Logger logger = Logger.getLogger(ViolationStatAction.class);

	/**
	 * @�汾�ţ�1.0
	 * @����˵����ͳ������Υ����Ϣ
	 * @������
	 * @���أ�ͳ�ƽ��
	 * @���ߣ�������
	 * @�������ڣ�2010-1-13 
	 * */
	public ActionForward doStatistic(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
		String depart = StringHelper.obj2str(request.getParameter("depart"),""); //����ID
		String zhidui = StringHelper.obj2str(request.getParameter("zhidui"),""); //֧��ID
		String dadui = StringHelper.obj2str(request.getParameter("dadui"),""); //���ID
		if("0000".equals(depart.substring(2, 6))){
			//�ܶ��û�
			out.write(Factory.getViolationStatDaoImpl().getStatistics(startTime, endTime, depart,zhidui,dadui));
    	}else if("00".equals(depart.substring(4,6))){
        	//֧���û�
			out.write(Factory.getViolationStatDaoImpl().getStatistics(startTime, endTime, depart,dadui));
    	}else {
            //����û�
			out.write(Factory.getViolationStatDaoImpl().getStatistics(startTime, endTime, depart));
    	}
		out.close();
		return null;
	}

	/**
	 * @�汾�ţ�1.0
	 * @����˵���������б���HTML����
	 * @������fileName ת�����ļ��� title �ļ����� tabHeader �ļ���ͷ dataSql ��ȡת�����ݵ�SQL
	 * @���أ�
	 * @���ߣ�������
	 * @�������ڣ�2009-12-8 
	 * */
	public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//��������
		String fileName = ""; //�ļ�����
		String titleName = ""; //����		
		String tabHeader = "�鴦����,����Ͻ��,Υ������,Υ������"; //��ͷ
		String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
		if("".equals(sql)){
			logger.error("[����ͳ��]" + "����sqlΪ�մ���");
			return null;
		}

		System.out.println(sql);
		fileName ="����Υ����"+startTime+"����"+endTime+"�յ�ͳ��";
		titleName ="����Υ��ͳ�ƽ�������ڣ�"+startTime+"����"+endTime+"�գ�";
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

	/**
	 * @�汾�ţ�1.0
	 * @����˵����дExcel�ļ�
	 * @�������Ƿ�ִ�гɹ�.true���ɹ���false:ʧ��.
	 * @���أ��Ƿ���سɹ�,true-�ɹ�;false-ʧ��.
	 * @���ߣ�������
	 * @�������ڣ�2009-12-8 
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
		//fileName = new String(fileName.getBytes(),"ISO8859_1");
		fileName = new String(fileName.getBytes(),"UTF-8");
		try {
			response.reset();
	  	    response.setContentType("application/vnd.ms-excel; charset=UTF-8");
	  	   // response.setHeader("content-disposition","filename=" + fileName +".xls");
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

	/**
	 * @�汾�ţ�1.0
	 * @����˵��������ת��ִ�к���
	 * @������oResult �����. out OutputStream. sheetName Sheet�� titleName ������ lstTabHeader ��ֵ�б��ͷ���ơ�
	 * @���أ��Ƿ���سɹ�,true-�ɹ�;false-ʧ��.
	 * @���ߣ�������
	 * @�������ڣ�2009-12-8 
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

}
