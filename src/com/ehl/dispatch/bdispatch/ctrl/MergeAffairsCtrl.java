package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.bdispatch.business.MergeAffairMgr;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�MergeAffairsCtrl.java
 * 
 * ժ Ҫ���ϲ����鵥�����ࡣ
 * 			��ѯ���ϲ����鵥��Ϣ���ϲ����鵥
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-10
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class MergeAffairsCtrl extends Controller
{
		
	/**���鵥��ʶ����ȡ���鵥ժҪ��Ϣ
	 * ���鵥��ʶ�Զ��ŷָ�
	 * 
	 */
	public ActionForward doReadMergeAffairs(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		String affairIDs = request.getParameter("AFFAIRIDS");	//��ȡ���鵥��ʶ����Ϣ
		String dataString = null;
		try
		{
			//ͨ��������Ϣ��ѯ�࣬��ѯ��ؾ�����Ϣ
			MergeAffairMgr mgrQuery = new MergeAffairMgr();
			dataString =  mgrQuery.readAffairBrief(affairIDs);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("��ȡ������Ϣ�����쳣��ReadMergeAffairs-->" + ex.getMessage());
			dataString = "��ȡ������Ϣ�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		
		out.close();
		return null;
	}
	
	/**�ϲ����鵥
	 * ��ȡ�����鵥��ʶ�ͷ־��鵥��ʶ���Զ��ŷָ�Ծ��鵥���и���
	 * 
	 */
	public ActionForward doMergeAffairs(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		String mainAffairId = request.getParameter("MAINAFFAIRID");	//��ȡ�����鵥��ʶ
		String subAffairIDs = request.getParameter("SUBAFFAIRIDS"); //��ȡ�־��鵥��ʶ
		String dataString = null;
		try
		{
			//ͨ��������Ϣ��ѯ�࣬��ѯ��ؾ�����Ϣ
			MergeAffairMgr mgrUpdate = new MergeAffairMgr();
			dataString =  mgrUpdate.mergeAffairs(mainAffairId, subAffairIDs);
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("���ϲ����鵥�����쳣��MergeAffairs-->" + ex.getMessage());
			dataString = "�ϲ����鵥�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		
		out.close();
		return null;
	}
	
}
