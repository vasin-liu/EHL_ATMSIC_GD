/** 
 * 项目名称：EHL_ATMSIC_GD <br> 
 * 文件路径：com.ehl.util  <br>  
 * 文件名称：DropDown.java <br> 
 * 文件编号：    <br> 
 * 文件描述：   <br> 
 *
 * 版本信息： Ver 1.1  <br>  
 * 创建日期：2011-12-27   <br>  
 * 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有     <br> 
 ************************************************** <br> 
 * 创建人：lenove <br>    
 * 创建日期：2011-12-27 下午1:30:46 <br>   
 ************* 修改历史  ************* <br> 
 * 修改人：lenove <br>    
 * 修改时间：2011-12-27 下午1:30:46 <br>    
 * 修改备注： <br>    
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
 * 项目名称：EHL_ATMSIC_GD <br>
 * 包路径：com.ehl.util <br>
 * 类名称：DropDown <br>
 * 文件描述： <br>
 * 
 * @see <br>
 * @since Ver 1.1 <br>
 *        版本信息：Ver 1.1 <br>
 ********************************* <br>
 *        创建人：lsx <br>
 *        创建日期：2011-12-27 下午1:30:46 <br>
 *************        修改历史 ************* <br>
 *        修改人：lsx <br>
 *        修改时间：2011-12-27 下午1:30:46 <br>
 *        修改备注： <br>
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
