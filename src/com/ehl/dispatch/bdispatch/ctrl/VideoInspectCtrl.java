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
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�VideoInspectCtrl.java
 * 
 * ժ Ҫ����Ƶ��ؿ����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-14
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class VideoInspectCtrl extends Controller
{
	/**�����豸��ʶ��ȡ�豸��Ϣ
	 * 
	 * 
	 */
	public ActionForward doGetVideoById(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		String videoId = request.getParameter("VIDEOID");	//��ȡ��Ƶ�豸��ʶ
		String dataString = null;
		try
		{
			//��ѯ����Ƶ�豸��Ϣ
			VideoInspectMgr readVideo = new VideoInspectMgr();
			dataString =  readVideo.readVideoBriefById(videoId);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("��ȡ��Ƶ�豸��Ϣ�����쳣��GetVideoByIds-->" + ex.getMessage());
			dataString = "��ȡ��Ƶ�豸��Ϣ�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		out.close();
		return null;
	}
	/**�����豸��ʶ��ȡ�豸��Ϣ
	 * 
	 * 
	 */
	public ActionForward doGetVideoList(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		
		String countyCode = request.getParameter("countyCode");	//��ȡ��Ƶ�豸��ʶ
		String dataString = null;
		try
		{
			//��ѯ����Ƶ�豸��Ϣ
			VideoInspectMgr readVideo = new VideoInspectMgr();
			dataString =  readVideo.readVideosBrief(countyCode);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("��ȡ��Ƶ�豸��Ϣ�����쳣��GetVideoList-->" + ex.getMessage());
			dataString = "��ȡ��Ƶ�豸��Ϣ�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		out.close();
		return null;
	}
	
}
