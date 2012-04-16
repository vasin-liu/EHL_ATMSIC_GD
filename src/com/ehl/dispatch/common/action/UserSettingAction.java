
	/**   
	* 文件名：UserSettingAction.java   
	*   
	* 版本信息：   
	* 日期：2011-8-22   
	* Copyright EHL 2011    
	* 版权所有   
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
	* 项目名称：EHL_ATMSIC_GD
	* 包路径：com.ehl.dispatch.common.action  
	* 类名称：UserSettingAction   
	* 文件描述：用户默认设置Action 
	*
	* @see
	* @since Ver 1.1
	* 版本信息：Ver 1.1 
	*********************************
	* 创建人：Vasin   
	* 创建日期：2011-8-25 下午01:49:55  
	************* 修改历史  *************
	* 修改人：Vasin   
	* 修改时间：2011-8-25 下午01:49:55   
	* 修改备注：
 */

public class UserSettingAction extends Controller {

	/**
	 * 
		* 方法名称：doSaveUserSetting
		* 方法描述： 保存用户默认设置
		* @param action
		* @param request
		* @param response
		* @throws Throwable
		* @return ActionForward
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:46:44  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:46:44   
		* 修改备注：
	 */
	public ActionForward doSaveUserSetting(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		if ( paramsMap.get("user_code") == null || paramsMap.get("user_code") == "" ) {
			System.out.println("用户ID参数缺失，保存用户默认设置失败。");
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
		* 方法名称：doDeleteUserSetting
		* 方法描述： 删除用户默认设置
		* @param action
		* @param request
		* @param response
		* @return
		* @throws Throwable
		* @return ActionForward
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午02:14:53  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午02:14:53   
		* 修改备注：
	 */
	public ActionForward doDeleteUserSetting(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		if ( paramsMap.get("user_code") ==null || paramsMap.get("user_code") == "" ) {
			System.out.println("用户ID参数缺失，删除用户默认设置失败。");
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
		* 方法名称：doGetUserSetting
		* 方法描述： 获取用户默认设置
		* @param action
		* @param request
		* @param response
		* @throws Throwable
		* @return ActionForward
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:50:46  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:50:46   
		* 修改备注：
	 */
	public ActionForward doGetUserSetting(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		if ( paramsMap.get("user_code") ==null || paramsMap.get("user_code") == "" ) {
			System.out.println("用户ID参数缺失，获取用户默认设置失败。");
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
