package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.DictateDisposalMgr;



/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：DictateDisposalCtrl.java
 * 
 * 摘 要：领导指令指示控制类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-16
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */


public class DictateDisposalCtrl 	extends Controller
{

		/**根据指令指示标识获取指令指示信息
		 * 
		 * 
		 */
		public ActionForward doGetDictateById(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml"); 	// 指定输出内容的格式
			
			PrintWriter out = response.getWriter();
			String dictateId = request.getParameter("DICTATEID");	//获取指令指示标识
			String userCountyCode = request.getParameter("USERCOUNTYCODE");	
			
			String dataString = null;
			try
			{
				//查询指令指示信息
				DictateDisposalMgr readDictate = new DictateDisposalMgr();
				dataString =  readDictate.getDictateDetailById(dictateId,userCountyCode);
			}
			catch(Exception ex)
			{
				com.appframe.Console.infoprintln("读取指令指示信息发生异常，GetDictateById-->" + ex.getMessage());
				dataString = "读取指令指示信息发生异常！";
			}
			if(null != dataString )
			{
				out.write(dataString);		//向用户提供反馈信息
			}
			out.close();
			return null;
		}
		
		/**根据落实标识获取落实信息
		 * 
		 * 
		 */
		public ActionForward doGetFullfillById(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml"); 	// 指定输出内容的格式
			
			PrintWriter out = response.getWriter();
			String fullfillId = request.getParameter("FULLFILLID");	//获取落实标识 
			
			String dataString = null;
			try
			{
				//查询相视频设备信息
				DictateDisposalMgr readDictate = new DictateDisposalMgr();
				dataString =  readDictate.getFullfillDetailById(fullfillId);
			}
			catch(Exception ex)
			{
				com.appframe.Console.infoprintln("读取落实信息发生异常，GetVideoByIds-->" + ex.getMessage());
				dataString = "读取落实信息发生异常！";
			}
			if(null != dataString )
			{
				out.write(dataString);		//向用户提供反馈信息
			}
			out.close();
			return null;
		}
		
		/**更新落实信息
		 * 
		 * 
		 */
		public ActionForward doWriteFullfillData(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml"); 	// 指定输出内容的格式
			
			PrintWriter out = response.getWriter();
			String fullfillId = request.getParameter("FULLFILLID");	//获取落实标识 
			
			boolean succeed = false;
			try
			{
				//查询相视频设备信息
				DictateDisposalMgr writeDictate = new DictateDisposalMgr();
				succeed =  writeDictate.writeFullfillData(fullfillId, getFullfillDataMap(request));
			}
			catch(Exception ex)
			{
				com.appframe.Console.infoprintln("保存落实信息发生异常，GetVideoByIds-->" + ex.getMessage());
				succeed = false;
			}
			if(! succeed )
			{
				out.write("保存落实信息失败！");		//向用户提供反馈信息
			}
			else
			{
				out.write("保存落实信息成功！");
			}
			out.close();
			return null;
		}
		
		//获取落实信息参数
		private  Map<String,String> getFullfillDataMap(HttpServletRequest request)
		{
			Map<String ,String>   fullfillMap = new HashMap<String,String>();
			fullfillMap.put("STATE", request.getParameter("STATE"));
			fullfillMap.put("COMPLETETIME", request.getParameter("COMPLETETIME"));
			fullfillMap.put("CONTENT", request.getParameter("CONTENT"));
			fullfillMap.put("RECORDER", request.getParameter("RECORDER"));
			fullfillMap.put("REMARK", request.getParameter("REMARK"));
			
			return fullfillMap;
		}
	}