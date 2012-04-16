package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.MergeAffairMgr;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：MergeAffairsCtrl.java
 * 
 * 摘 要：合并警情单控制类。
 * 			查询待合并警情单信息，合并警情单
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-10
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class MergeAffairsCtrl extends Controller
{
		
	/**警情单标识，获取警情单摘要信息
	 * 警情单标识以逗号分割
	 * 
	 */
	public ActionForward doReadMergeAffairs(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String affairIDs = request.getParameter("AFFAIRIDS");	//获取警情单标识串信息
		String dataString = null;
		try
		{
			//通过警情信息查询类，查询相关警情信息
			MergeAffairMgr mgrQuery = new MergeAffairMgr();
			dataString =  mgrQuery.readAffairBrief(affairIDs);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("读取警情信息发生异常，ReadMergeAffairs-->" + ex.getMessage());
			dataString = "读取警情信息发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		
		out.close();
		return null;
	}
	
	/**合并警情单
	 * 获取主警情单标识和分警情单标识（以逗号分割）对警情单进行更新
	 * 
	 */
	public ActionForward doMergeAffairs(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String mainAffairId = request.getParameter("MAINAFFAIRID");	//获取主警情单标识
		String subAffairIDs = request.getParameter("SUBAFFAIRIDS"); //获取分警情单标识
		String dataString = null;
		try
		{
			//通过警情信息查询类，查询相关警情信息
			MergeAffairMgr mgrUpdate = new MergeAffairMgr();
			dataString =  mgrUpdate.mergeAffairs(mainAffairId, subAffairIDs);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("读合并警情单发生异常，MergeAffairs-->" + ex.getMessage());
			dataString = "合并警情单发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		
		out.close();
		return null;
	}
	
}
