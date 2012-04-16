
	/**   
	* �ļ�����UserSettingAction.java   
	*   
	* �汾��Ϣ��   
	* ���ڣ�2011-8-22   
	* Copyright EHL 2011    
	* ��Ȩ����   
	*   
	*/
package com.ehl.dispatch.common.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.common.dao.UserSettingDao;


/**
 * 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD
	* ��·����com.ehl.dispatch.common.action  
	* �����ƣ�UserSettingAction   
	* �ļ��������û�Ĭ������Action 
	*
	* @see
	* @since Ver 1.1
	* �汾��Ϣ��Ver 1.1 
	*********************************
	* �����ˣ�Vasin   
	* �������ڣ�2011-8-25 ����01:49:55  
	************* �޸���ʷ  *************
	* �޸��ˣ�Vasin   
	* �޸�ʱ�䣺2011-8-25 ����01:49:55   
	* �޸ı�ע��
 */

public class UserSettingAction extends Controller {

	/**
	 * 
		* �������ƣ�doSaveUserSetting
		* ���������� �����û�Ĭ������
		* @param action
		* @param request
		* @param response
		* @throws Throwable
		* @return ActionForward
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:46:44  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:46:44   
		* �޸ı�ע��
	 */
	public ActionForward doSaveUserSetting(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		if ( paramsMap.get("user_code") == null || paramsMap.get("user_code") == "" ) {
			System.out.println("�û�ID����ȱʧ�������û�Ĭ������ʧ�ܡ�");
			return null;
		}
		PrintWriter out = response.getWriter();
		UserSettingDao usd = new UserSettingDao();
		String id = usd.getUserSettingId(paramsMap);
		boolean flag = false;
		if (id != null && id.length() > 0) {
			paramsMap.remove("id");
			paramsMap.put("id", id);
			flag = usd.updateUserSetting(paramsMap);
		} else {
			flag = usd.insertUserSetting(paramsMap);
		}
		if (flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
	 	out.close();
		return null;
	}
	
	/**
	 * 
		* �������ƣ�doDeleteUserSetting
		* ���������� ɾ���û�Ĭ������
		* @param action
		* @param request
		* @param response
		* @return
		* @throws Throwable
		* @return ActionForward
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����02:14:53  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����02:14:53   
		* �޸ı�ע��
	 */
	public ActionForward doDeleteUserSetting(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		if ( paramsMap.get("user_code") ==null || paramsMap.get("user_code") == "" ) {
			System.out.println("�û�ID����ȱʧ��ɾ���û�Ĭ������ʧ�ܡ�");
			return null;
		}
		PrintWriter out = response.getWriter();
		UserSettingDao usd = new UserSettingDao();
		String id = usd.getUserSettingId(paramsMap);
		boolean flag = false;
		if (id != null && id.length() > 0) {
			paramsMap.remove("id");
			paramsMap.put("id", id);
			flag = usd.deleteUserSetting(paramsMap);
		}
		if (flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
	 	out.close();
		return null;
	}
	
	/**
	 * 
		* �������ƣ�doGetUserSetting
		* ���������� ��ȡ�û�Ĭ������
		* @param action
		* @param request
		* @param response
		* @throws Throwable
		* @return ActionForward
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:50:46  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:50:46   
		* �޸ı�ע��
	 */
	public ActionForward doGetUserSetting(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		if ( paramsMap.get("user_code") ==null || paramsMap.get("user_code") == "" ) {
			System.out.println("�û�ID����ȱʧ����ȡ�û�Ĭ������ʧ�ܡ�");
			return null;
		}
		PrintWriter out = response.getWriter();
		UserSettingDao usd = new UserSettingDao();
		String id = usd.getUserSettingId(paramsMap);
		paramsMap.put("id", id);
		String xmlResult = usd.getUserSettingDefaultValue(paramsMap);
		out.write(xmlResult);
	 	out.close();
		return null;
	}
}
