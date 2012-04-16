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
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairAppraiseCtrl.java
 * 
 * ժ Ҫ�����鴦�����ۿ����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-20
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class AlarmAffairAppraiseCtrl extends Controller
{
	
	Logger logger=Logger.getLogger(AlarmAffairAppraiseCtrl.class);
	
	/**���ݾ��鴦��������Ϣ
	 * 
	 */
	public ActionForward doGetAppariseByAlarmId(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		
		PrintWriter out = response.getWriter();
		String alarmId = request.getParameter("ALARMID");	//��ȡ�����ʶ
		String dataString = null;
		try
		{
			//��ѯ����Ƶ�豸��Ϣ
			AlarmAffairAppraiseMgr readAppraise = new AlarmAffairAppraiseMgr();
			
			dataString =  readAppraise.readAppraiseByAlarmId(alarmId);
		}
		catch(Exception ex)
		{
			//com.appframe.Console.infoprintln("��ȡ���鴦��������Ϣ�����쳣��GetAppariseByAlarmId-->" + ex.getMessage());
			logger.error("[�ֿ�ָ�ӵ���]" + "��ȡ���鴦��������Ϣ�����쳣");
			System.out.println(ex.getMessage());
			dataString = "��ȡ���鴦��������Ϣ�����쳣��";
		}
		if(null != dataString )
		{
			out.write(dataString);		//���û��ṩ������Ϣ
		}
		out.close();
		return null;
	}
}
