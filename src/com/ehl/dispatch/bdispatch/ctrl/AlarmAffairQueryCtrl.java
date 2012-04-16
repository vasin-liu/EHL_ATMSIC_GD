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
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairQueryCtrl.java
 * 
 * ժ Ҫ��ָ�ӵ��ȼ�ؾ����¼���ѯ�����ࡣ
 * 			��ѯδ�����飬���ע����
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-8
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */


public class AlarmAffairQueryCtrl extends Controller
{
	/**ͨ���û�������Ϣ����ѯ���ע����
	 * 
	 * 
	 */
	public ActionForward doQueryNoticedAffair(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		String branchCode = request.getParameter("branchCode");	//��ȡ�û����ڻ���������Ϣ
		String dataString = null;
		try
		{
			//ͨ��������Ϣ��ѯ�࣬��ѯ��ؾ�����Ϣ
			AlarmAffairQueryMgr mgrQuery = new AlarmAffairQueryMgr();
			dataString =  mgrQuery.queryNoticeAffair(branchCode);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("��ȡ��ע������Ϣ�����쳣��QueryNoticedAffair-->" + ex.getMessage());
			dataString = "��ȡ��ע������Ϣ�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		
		out.close();
		return null;
	}	
	
	/**ͨ�����鵥�ţ��;�������ȡ������ϸ��Ϣ
	 * 
	 * 
	 */
	public ActionForward doGetAffairDetail(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		String alarmId = request.getParameter("ALARMID");	//��ȡ���鵥���
		String alarmType = request.getParameter("ALARMTYPE");	//��ȡ��������
		String dataString = null;
		try
		{
			//ͨ��������Ϣ��ѯ�࣬��ѯ��ؾ�����Ϣ
			AlarmAffairQueryMgr mgrQuery = new AlarmAffairQueryMgr();
			dataString =  mgrQuery.getAffairDetail(alarmId,alarmType);
			//System.out.println("���������ַ���="+dataString);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("��ȡ��ע������Ϣ�����쳣��QueryNoticedAffair-->" + ex.getMessage());
			dataString = "��ȡ��ע������Ϣ�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		
		out.close();
		return null;
	}	
}
