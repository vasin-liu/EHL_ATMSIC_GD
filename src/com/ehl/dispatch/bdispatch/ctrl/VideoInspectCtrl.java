package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.VideoInspectMgr;
import com.ehl.dispatch.bdispatch.business.*;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：VideoInspectCtrl.java
 * 
 * 摘 要：视频监控控制类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-14
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class VideoInspectCtrl extends Controller
{
	/**根据设备标识获取设备信息
	 * 
	 * 
	 */
	public ActionForward doGetVideoById(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		String videoId = request.getParameter("VIDEOID");	//获取视频设备标识
		String dataString = null;
		try
		{
			//查询相视频设备信息
			VideoInspectMgr readVideo = new VideoInspectMgr();
			dataString =  readVideo.readVideoBriefById(videoId);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("读取视频设备信息发生异常，GetVideoByIds-->" + ex.getMessage());
			dataString = "读取视频设备信息发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		out.close();
		return null;
	}
	/**根据设备标识获取设备信息
	 * 
	 * 
	 */
	public ActionForward doGetVideoList(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		
		PrintWriter out = response.getWriter();
		
		String countyCode = request.getParameter("countyCode");	//获取视频设备标识
		String dataString = null;
		try
		{
			//查询相视频设备信息
			VideoInspectMgr readVideo = new VideoInspectMgr();
			dataString =  readVideo.readVideosBrief(countyCode);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("读取视频设备信息发生异常，GetVideoList-->" + ex.getMessage());
			dataString = "读取视频设备信息发生异常！";
		}
		if(null != dataString )
		{
			out.write(dataString);		//向用户提供反馈信息
		}
		out.close();
		return null;
	}
	
}
