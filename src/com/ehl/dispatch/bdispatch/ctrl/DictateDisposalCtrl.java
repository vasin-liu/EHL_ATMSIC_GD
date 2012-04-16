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
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�DictateDisposalCtrl.java
 * 
 * ժ Ҫ���쵼ָ��ָʾ�����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-16
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */


public class DictateDisposalCtrl 	extends Controller
{

		/**����ָ��ָʾ��ʶ��ȡָ��ָʾ��Ϣ
		 * 
		 * 
		 */
		public ActionForward doGetDictateById(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
			
			PrintWriter out = response.getWriter();
			String dictateId = request.getParameter("DICTATEID");	//��ȡָ��ָʾ��ʶ
			String userCountyCode = request.getParameter("USERCOUNTYCODE");	
			
			String dataString = null;
			try
			{
				//��ѯָ��ָʾ��Ϣ
				DictateDisposalMgr readDictate = new DictateDisposalMgr();
				dataString =  readDictate.getDictateDetailById(dictateId,userCountyCode);
			}
			catch(Exception ex)
			{
				com.appframe.Console.infoprintln("��ȡָ��ָʾ��Ϣ�����쳣��GetDictateById-->" + ex.getMessage());
				dataString = "��ȡָ��ָʾ��Ϣ�����쳣��";
			}
			if(null != dataString )
			{
				out.write(dataString);		//���û��ṩ������Ϣ
			}
			out.close();
			return null;
		}
		
		/**������ʵ��ʶ��ȡ��ʵ��Ϣ
		 * 
		 * 
		 */
		public ActionForward doGetFullfillById(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
			
			PrintWriter out = response.getWriter();
			String fullfillId = request.getParameter("FULLFILLID");	//��ȡ��ʵ��ʶ 
			
			String dataString = null;
			try
			{
				//��ѯ����Ƶ�豸��Ϣ
				DictateDisposalMgr readDictate = new DictateDisposalMgr();
				dataString =  readDictate.getFullfillDetailById(fullfillId);
			}
			catch(Exception ex)
			{
				com.appframe.Console.infoprintln("��ȡ��ʵ��Ϣ�����쳣��GetVideoByIds-->" + ex.getMessage());
				dataString = "��ȡ��ʵ��Ϣ�����쳣��";
			}
			if(null != dataString )
			{
				out.write(dataString);		//���û��ṩ������Ϣ
			}
			out.close();
			return null;
		}
		
		/**������ʵ��Ϣ
		 * 
		 * 
		 */
		public ActionForward doWriteFullfillData(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
			
			PrintWriter out = response.getWriter();
			String fullfillId = request.getParameter("FULLFILLID");	//��ȡ��ʵ��ʶ 
			
			boolean succeed = false;
			try
			{
				//��ѯ����Ƶ�豸��Ϣ
				DictateDisposalMgr writeDictate = new DictateDisposalMgr();
				succeed =  writeDictate.writeFullfillData(fullfillId, getFullfillDataMap(request));
			}
			catch(Exception ex)
			{
				com.appframe.Console.infoprintln("������ʵ��Ϣ�����쳣��GetVideoByIds-->" + ex.getMessage());
				succeed = false;
			}
			if(! succeed )
			{
				out.write("������ʵ��Ϣʧ�ܣ�");		//���û��ṩ������Ϣ
			}
			else
			{
				out.write("������ʵ��Ϣ�ɹ���");
			}
			out.close();
			return null;
		}
		
		//��ȡ��ʵ��Ϣ����
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