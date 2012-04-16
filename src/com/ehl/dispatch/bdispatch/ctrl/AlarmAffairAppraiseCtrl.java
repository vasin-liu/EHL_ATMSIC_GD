package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.AlarmAffairAppraiseMgr;
import com.ehl.dispatch.bdispatch.business.*;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairAppraiseCtrl.java
 * 
 * 摘 要：警情处理评价控制类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-20
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class AlarmAffairAppraiseCtrl extends Controller
{
	
	Logger logger=Logger.getLogger(AlarmAffairAppraiseCtrl.class);
	
	/**根据警情处理评价信息
	 * 
	 */
	public ActionForward doGetAppariseByAlarmId(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String alarmId = request.getParameter("ALARMID");	//获取警情标识
		String dataString = null;
		try
		{
			//查询相视频设备信息
			AlarmAffairAppraiseMgr readAppraise = new AlarmAffairAppraiseMgr();
			
			dataString =  readAppraise.readAppraiseByAlarmId(alarmId);
		}
		catch(Exception ex)
		{
			//com.appframe.Console.infoprintln("读取警情处理评价信息发生异常，GetAppariseByAlarmId-->" + ex.getMessage());
			logger.error("[分控指挥掉度]" + "读取警情处理评价信息发生异常");
			System.out.println(ex.getMessage());
			dataString = "读取警情处理评价信息发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		out.close();
		return null;
	}
}
