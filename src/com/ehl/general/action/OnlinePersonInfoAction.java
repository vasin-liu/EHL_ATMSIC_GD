
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.general.action <br> 
	* 文件名称：OnlinePersonInfoAction.java <br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1 <br>  
	* 创建日期：2011-12-12 <br>  
	* 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2011-12-12 下午3:23:00<br>  
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-12 下午3:23:00  <br> 
	* 修改备注：   <br>
	*/
package com.ehl.general.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.general.service.OnlinePersonInfoService;

	/**   
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.general.action  <br>
 * 类名称：OnlinePersonInfoAction  <br> 
 * 文件描述：在线部门信息action   <br>
 *
 * @see <br>
 * 版本信息：Ver 1.1 <br>
 *********************************<br>
 * 创建人：Vasin  <br> 
 * 创建日期：2011-12-12 下午3:23:00  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2011-12-12 下午3:23:00  <br> 
 * 修改备注：     <br>
 */
public class OnlinePersonInfoAction extends Controller {

	private Logger logger = Logger.getLogger(OnlinePersonInfoAction.class);
	
	/**
	 * 
		* 方法名称：doGetOnlinePersonInfo<br>
		* 方法描述：获取在线人员信息 for jquery jqgrid <br>
		* @param action
		* @param request
		* @param response
		* @return
		* @throws Throwable<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-16 下午4:03:38 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-16 下午4:03:38  <br> 
		* 修改备注：    <br>
	 */
	public ActionForward doGetOnlinePersonInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
		PrintWriter out = response.getWriter();
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String iDisplayLength = StringHelper.obj2str(request.getParameter("rows"),"10");
		String iDisplayStart = StringHelper.obj2str(request.getParameter("page"),"0");
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"),"desc");
		String sSearch = StringHelper.obj2str(request.getParameter("sSearch"),"");
		String showAll = StringHelper.obj2str(request.getParameter("showAll"),"1");
		hashMap.put("iDisplayLength", iDisplayLength);
		hashMap.put("iDisplayStart", iDisplayStart);
		hashMap.put("sortIndex", sortIndex);
		hashMap.put("sord", sord);
		hashMap.put("sSearch", sSearch);
		hashMap.put("showAll", showAll);
		
		logger.debug(hashMap);
		
		OnlinePersonInfoService onlinePersonInfoService = new OnlinePersonInfoService();
		
		JSONObject json = onlinePersonInfoService.getOnlinePersonInfo(hashMap);
		
		out.print(json);
		
		logger.debug(json);
		
		out.close();
		return null;
	}
}
