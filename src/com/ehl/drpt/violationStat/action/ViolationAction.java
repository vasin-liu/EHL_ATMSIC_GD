package com.ehl.drpt.violationStat.action;
/**
 * 
 * @======================================================================================================================================
 * @����˵��: ���˿��˳������ؽ�ͨΥ����Ϣ�������Action
 * @�����ߣ�wkz
 * @�������� 2010-01-13
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
	 * @����:wkz
	 * @�汾�ţ�1.0
	 * @����˵�������˿��˳������ؽ�ͨΥ����Ϣ�������޸�
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-11
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
			String[] strObj = DepartmentManage.getDeptInfo(request,"1").split(",");//��ȡ��λ��Ϣ��
			if(insertOrUpdate == 0){
				out.write(violationdao.add(xmlStr,strObj));
				LogManage.writeEvent(LCODE, "��ӿ��˳�Υ����¼");
			}else{
				out.write(violationdao.update(xmlStr));
				LogManage.writeEvent(LCODE, "�༭���˳�Υ����¼");
			}
		} catch (Exception ex) {
			out.write("����ʧ�ܣ������ԣ�");
			out.close();
			return null;
		}
		out.close();
		return null;
	}
	
	/**
	 * @����:wkz
	 * @�汾�ţ�1.0
	 * @����˵�������������Ҷ�ӦΥ����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
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
	 * @���ߣ�wkz
	 * @�汾�ţ�
	 * @����˵����ɾ��
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doDelete(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml"); // ָ�������XML��ʽ
		String bh = request.getParameter("bh") == null ? "" : request.getParameter("bh");
		ViolationDao violationdao = new ViolationDao();
		out.write(violationdao.delete(bh)?"��¼���ɹ�ɾ����":"����ʧ�ܣ������ԣ�");
		LogManage.writeEvent(LCODE, "ɾ�����˳�Υ����¼");
		out.close();
		return null;
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @���ߣ�wkz
	 * @�汾�ţ�
	 * @����˵���������б���HTML����
	 * @������fileName ת�����ļ��� title �ļ����� tabHeader �ļ���ͷ dataSql ��ȡת�����ݵ�SQL
	 * @���أ�
	 * @�������ڣ�2010-01-29
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("content-disposition","attachment;filename=exportExcel.xls");

		//��������
		String fileName = ""; //�ļ�����
		String titleName = ""; //����		
		//String tabHeader = "���ƺ�,Υ����ʵ,������λ���ˣ�,�鴦����,·��,��ʻ��,��ʻ֤��,�������"; //��ͷ
		
		//wujh edit
		String tabHeader = "���ƺ�,Υ����ʵ,��������,ʵ������,��������,������λ���ˣ�,�鴦����,·��,��ʻ��,��ʻ֤��,�������"; //��ͷ
		String sql = StringHelper.obj2str(request.getParameter("sql"),""); //SQL
		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //��ʼʱ��
		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //����ʱ��
		
		if (tabHeader == null || sql == null)
		{
			return null;
		}
		if("".equals(sql)){
			logger.error("[���˳�Υ����¼��ѯ����EXCEL]" + "����sqlΪ�մ���");
			return null;
		}

		System.out.println(sql);
//		if(startTime.equals(endTime)){
//			fileName ="���˳�Υ����"+startTime+"�յļ�¼";
//		}else{
//			fileName ="���˳�Υ����"+startTime+"����"+endTime+"�յļ�¼";
//		}
		fileName = "excelExport";
		titleName = "���˿��˳������ؽ�ͨΥ��ͳ�Ʊ�";
		String sheetName = "���˿��˳������ؽ�ͨΥ��ͳ�Ʊ�";
		
		wirteToExcel(response,tabHeader,fileName,sheetName,sql,titleName);
//		
//		ViolationDao  dao = new ViolationDao();
//		Object[][] tabData= dao.SearchExcel(sql);
//		
//		
//		//����д��Excel
//		//����sm��Excel��������
//		SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
//		saveAsExcelCore.setTabHeader(tabHeader);
//		//�����ļ����ƣ�sheetName��֧��
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
