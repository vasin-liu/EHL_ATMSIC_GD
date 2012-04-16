package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.AlarmAffairQueryMgr;
import com.ehl.dispatch.bdispatch.business.*;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairQueryCtrl.java
 * 
 * 摘 要：指挥调度监控警情事件查询控制类。
 * 			查询未处理警情，需关注警情
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-8
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */


public class AlarmAffairQueryCtrl extends Controller
{
	/**通过用户机构信息，查询需关注警情
	 * 
	 * 
	 */
	public ActionForward doQueryNoticedAffair(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String branchCode = request.getParameter("branchCode");	//获取用户所在机构编码信息
		String dataString = null;
		try
		{
			//通过警情信息查询类，查询相关警情信息
			AlarmAffairQueryMgr mgrQuery = new AlarmAffairQueryMgr();
			dataString =  mgrQuery.queryNoticeAffair(branchCode);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("读取关注警情信息发生异常，QueryNoticedAffair-->" + ex.getMessage());
			dataString = "读取关注警情信息发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		
		out.close();
		return null;
	}	
	
	/**通过警情单号，和警情类别获取警情详细信息
	 * 
	 * 
	 */
	public ActionForward doGetAffairDetail(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String alarmId = request.getParameter("ALARMID");	//获取警情单编号
		String alarmType = request.getParameter("ALARMTYPE");	//获取警情类型
		String dataString = null;
		try
		{
			//通过警情信息查询类，查询相关警情信息
			AlarmAffairQueryMgr mgrQuery = new AlarmAffairQueryMgr();
			dataString =  mgrQuery.getAffairDetail(alarmId,alarmType);
			//System.out.println("警情数据字符串="+dataString);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("读取关注警情信息发生异常，QueryNoticedAffair-->" + ex.getMessage());
			dataString = "读取关注警情信息发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		
		out.close();
		return null;
	}	
}
