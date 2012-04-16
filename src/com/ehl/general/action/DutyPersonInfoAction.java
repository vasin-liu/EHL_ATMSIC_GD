
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.general.action <br> 
	* 文件名称：DutyPersonInfoAction.java <br>
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.general.service.DutyPersonInfoService;

	/**   
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.general.action  <br>
 * 类名称：DutyPersonInfoAction  <br> 
 * 文件描述：值班人员信息action   <br>
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
public class DutyPersonInfoAction extends Controller {

	private Logger logger = Logger.getLogger(DutyPersonInfoAction.class);
	
	/**
	 * 
		* 方法名称：doGetDutyPersonInfo<br>
		* 方法描述：获取值班人员信息 for jquery jqgrid <br>
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
	public ActionForward doGetDutyPersonInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
		PrintWriter out = response.getWriter();
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),sdf.format(now));
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),sdf.format(now));
		String iDisplayLength = StringHelper.obj2str(request.getParameter("rows"),"10");
		String iDisplayStart = StringHelper.obj2str(request.getParameter("page"),"0");
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"),"desc");
		String sSearch = StringHelper.obj2str(request.getParameter("sSearch"),"");
		String showLevel = StringHelper.obj2str(request.getParameter("showLevel"),"1");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		hashMap.put("startDate", startDate);
		hashMap.put("endDate", endDate);
		hashMap.put("iDisplayLength", iDisplayLength);
		hashMap.put("iDisplayStart", iDisplayStart);
		hashMap.put("sortIndex", sortIndex);
		hashMap.put("sord", sord);
		hashMap.put("sSearch", sSearch);
		hashMap.put("showLevel", showLevel);
		hashMap.put("jgid", jgid);
		logger.debug(hashMap);
		
		DutyPersonInfoService dutyPersonInfoService = new DutyPersonInfoService();
		
		JSONObject json = dutyPersonInfoService.getDutyPersonInfo(hashMap);
		out.print(json);
		
		logger.debug(json);
		
		out.close();
		return null;
	}
}
