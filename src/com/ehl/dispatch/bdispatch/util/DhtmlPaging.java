package com.ehl.dispatch.bdispatch.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.Console;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;
import com.appframe.data.sql.MultiDBHandler;


/**��ҳ����
 * 
 * @author LChQ 2009-4-13
 *
 */
public class DhtmlPaging extends Controller
{
	private int pageSize = 12;
	private int pageIndex  = 0; 	//ҳ��������0��ʼ
	private int pageCount = -1;
	private String selectSQL = "";   //�û�����SQL
	private String strDBConfig = ""; //���ݿ���������
	private String executeSQL = ""; 	//�ַ�ת�����SQL
	
	private Object [][] record = null;
	
	public ActionForward doDhtmlPaging(Action action, HttpServletRequest request,
										HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		strDBConfig = StringHelper.obj2str(request.getParameter("strDBConfig"),""); 
		if(null != action )
		{
			this.getParaments(request);
		}
		try
		{
			//if(-1 == pageCount)
			//{
				pageCount = this.countingPageCount();
			//}
			//��ȡ��ҳSQL���
			String strPageSQL = this.getPerPageSql();
			
			//��ȡ��ǰҳ���м�¼                  
			//record = DBHandler.getMultiResult(strPageSQL);
			
			if("".equals(strDBConfig))
			{
				record = DBHandler.getMultiResult(strPageSQL);
			}
			else
			{
				record = MultiDBHandler.getMultiResult(strDBConfig,strPageSQL);
			}
			
		 	//XML���ݸ�ʽ��
			StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>");
			xmlData.append("<rows>");
			 
			if(null != record && 0<record.length)
			{
				xmlData.append(this.getPagingInfo());
				xmlData.append(createDataInfo());
			}
			
			xmlData.append("</rows>");
			out.write(xmlData.toString());
		}
		catch(Exception ex)
		{
			Console.infoprintln(" error occured doDhtmlPaging --->" + ex.getLocalizedMessage());
			out.write(ex.getMessage());
		}
		out.close();
		return null;
	}
	
	public void setSelectSQL(String strSQL)
	{
		this.selectSQL = strSQL;
	}
	
	
	private void getParaments(HttpServletRequest request)
	{
		pageSize = StringHelper.obj2int(request.getParameter("pageSize"),12);		 //��ȡҳ��С
		pageIndex = StringHelper.obj2int(request.getParameter("pageIndex"),0);		//��ȡ��ǰҳ��
		System.out.println("pageIndex=="+pageIndex);
		selectSQL = StringUtil.convertSql(request.getParameter("selectSQL")); 	//��ȡSQL���
		this.resumeSQLscript();
		Console.infoprintln("executeSQL---->>" + executeSQL) ;
		pageCount = StringHelper.obj2int(request.getParameter("pageCount"),-1); 	//��ȡ��ҳ��
	}
	
	private void  resumeSQLscript()
	{
		if( null!= selectSQL && !"".equals(selectSQL))
		{
			executeSQL = selectSQL.replaceAll("��", "=");
			executeSQL = executeSQL.replaceAll("��", "%");
			executeSQL = executeSQL.replaceAll("��", "+");
			executeSQL = executeSQL.replaceAll("��", "&");
		}
	}
	
	private int countingPageCount()
	{
		int count = 0;
		Object [][]temp = null;
		if("".equals(strDBConfig))
		{
			temp = DBHandler.getMultiResult(executeSQL);
		}
		else
		{
			temp = MultiDBHandler.getMultiResult(strDBConfig,executeSQL);
		}
		if(null != temp)
		{
			int sum =  temp.length;
			count = sum/pageSize ;
			
			if( sum%pageSize != 0)
			{
				count += 1;
			}
		}
		return count;
	}
	
	private String createDataInfo()
	{
		StringBuffer  data = new  StringBuffer();
//		if(record.length == 0) {
//			data.append("<row id=0>");
//			data.append("<cell></cell>");
//			data.append("</row>\n");
//		}
		for(int j= 0;j< record.length; j++)
		{
			data.append("<row id=\"" + j + "\" >");
			for (int i = 0; i < record[j].length; i++) {
				data.append("<cell>" + StringHelper.obj2str(record[j][i],"") + "</cell>");
			}
			data.append("</row>\n");
		}
		return data.toString();
	}
	
	
	private String getPagingInfo()
	{
		StringBuffer  pagingData = new  StringBuffer();
		
		pagingData.append("<userdata name=\"pageIndex\">" + Integer.toString(pageIndex) + "</userdata>" );
		pagingData.append("<userdata name=\"pageCount\">" + Integer.toString(pageCount)  + "</userdata>" );
		pagingData.append("<userdata name=\"pageSize\">" + Integer.toString(pageSize)  + "</userdata>" );
		pagingData.append("<userdata name=\"selectSQL\">" + StringUtil.xmlDataFilter(selectSQL) + "</userdata>" );
		
		return pagingData.toString();
	}
	
	private  String getPerPageSql() 
	{
		//add by wangxt 2009-5-12
		if(pageIndex < 0)
			pageIndex = 0;
		long beginRow = (pageIndex) * pageSize + 1; //��ʼ��¼��
		long endRow = (pageIndex+1) * pageSize;     //������¼��
		String perPageSql = "SELECT * FROM (SELECT a.*,rownum row_num FROM (" + executeSQL + ")a WHERE rownum<=" + endRow + ")b WHERE b.row_num>="+beginRow;
        System.out.println("sql=="+perPageSql);
		return perPageSql;
	}
}
