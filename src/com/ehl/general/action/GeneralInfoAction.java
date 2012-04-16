
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

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.general.service.GeneralInfoService;


	/**   
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.general.action  <br>
 * 类名称：DutyPersonInfoAction  <br> 
 * 文件描述：警情信息概览action   <br>
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
//@org.springframework.context.annotation.Scope("prototype")
//@org.springframework.stereotype.Controller("generalInfoAction")
public class GeneralInfoAction extends Controller{

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GeneralInfoAction.class);
	
//	@javax.annotation.Resource(name="generalInfoService")
//	public GeneralInfoService generalInfoService;
	
	/**
	 * 
		* 方法名称：doGetGeneralInfoCount<br>
		* 方法描述： for jquery jqgrid <br>
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
	public ActionForward doGetGeneralInfoCount(Action action, HttpServletRequest request,
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
		String infoType = StringHelper.obj2str(request.getParameter("infoType"),"001024");
		String countBy = StringHelper.obj2str(request.getParameter("countBy"),"alarmregionid");
		String iDisplayLength = StringHelper.obj2str(request.getParameter("rows"),"10");
		String iDisplayStart = StringHelper.obj2str(request.getParameter("page"),"0");
		String sortIndex = StringHelper.obj2str(request.getParameter("sidx"),"1");
		String sord = StringHelper.obj2str(request.getParameter("sord"),"asc");
		String sSearch = StringHelper.obj2str(request.getParameter("sSearch"),"");
		String ALARMREGIONID = StringHelper.obj2str(request.getParameter("ALARMREGIONID"),"");
		String ROADLEVEL = StringHelper.obj2str(request.getParameter("ROADLEVEL"),"1");
		String ROADID = StringHelper.obj2str(request.getParameter("ROADID"),"");
		String detailType = StringHelper.obj2str(request.getParameter("detailType"),"");
		String detailID = StringHelper.obj2str(request.getParameter("detailID"),"");
		String detailParentID = StringHelper.obj2str(request.getParameter("detailParentID"),"");
		hashMap.put("startDate", startDate);
		hashMap.put("endDate", endDate);
		hashMap.put("infoType", infoType);
		hashMap.put("countBy", countBy);
		hashMap.put("iDisplayLength", iDisplayLength);
		hashMap.put("iDisplayStart", iDisplayStart);
		hashMap.put("sortIndex", sortIndex);
		hashMap.put("sord", sord);
		hashMap.put("sSearch", sSearch);
		hashMap.put("ALARMREGIONID", ALARMREGIONID);
		hashMap.put("ROADLEVEL", ROADLEVEL);
		hashMap.put("ROADID", ROADID);
		hashMap.put("detailType", detailType);
		hashMap.put("detailID", detailID);
		hashMap.put("detailParentID", detailParentID);
		
		logger.debug(hashMap);
		
		GeneralInfoService generalInfoService = new GeneralInfoService();
		
		JSONObject json = generalInfoService.getGeneralInfoCount(hashMap);
		out.print(json);
		
		out.close();
		return null;
	}
}
