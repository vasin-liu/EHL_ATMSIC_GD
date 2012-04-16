/** 
 * ��Ŀ���ƣ�EHL_ATMSIC_GD <br> 
 * �ļ�·����com.ehl.util  <br>  
 * �ļ����ƣ�DropDown.java <br> 
 * �ļ���ţ�    <br> 
 * �ļ�������   <br> 
 *
 * �汾��Ϣ�� Ver 1.1  <br>  
 * �������ڣ�2011-12-27   <br>  
 * ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����     <br> 
 ************************************************** <br> 
 * �����ˣ�lenove <br>    
 * �������ڣ�2011-12-27 ����1:30:46 <br>   
 ************* �޸���ʷ  ************* <br> 
 * �޸��ˣ�lenove <br>    
 * �޸�ʱ�䣺2011-12-27 ����1:30:46 <br>    
 * �޸ı�ע�� <br>    
 */
package com.ehl.util;

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
import com.ehl.tira.action.StatisticalAction;

/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
 * ��·����com.ehl.util <br>
 * �����ƣ�DropDown <br>
 * �ļ������� <br>
 * 
 * @see <br>
 * @since Ver 1.1 <br>
 *        �汾��Ϣ��Ver 1.1 <br>
 ********************************* <br>
 *        �����ˣ�lsx <br>
 *        �������ڣ�2011-12-27 ����1:30:46 <br>
 *************        �޸���ʷ ************* <br>
 *        �޸��ˣ�lsx <br>
 *        �޸�ʱ�䣺2011-12-27 ����1:30:46 <br>
 *        �޸ı�ע�� <br>
 */

public class DropDown extends Controller {

	private Logger logger = Logger.getLogger(DropDown.class);

	public ActionForward doCreateDropDown(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		String sql = StringHelper.obj2str(request.getParameter("sql"), "");
		sql = sql.replace('@', '=');

		Object[][] results = DBHandler.getMultiResult(sql);
		System.out.println("CreateDropDown:-----" + sql);

		StringBuffer xmlData = new StringBuffer();

		for (int i = 0; i < results.length; i++) {
			xmlData.append("<option value = '" + results[i][0] + "'>"
					+ String.valueOf(results[i][1]).replaceAll("\\(.*?\\)", "")
					+ "</option>");
		}

		out.write((xmlData.toString()));
		out.close();
		return null;
	}
}
