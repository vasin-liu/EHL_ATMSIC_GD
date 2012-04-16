
	/** 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
	* �ļ�·����com.ehl.general.action <br> 
	* �ļ����ƣ�DutyPersonInfoAction.java <br>
	* �ļ���ţ�   <br>
	* �ļ�������  <br>
	*
	* �汾��Ϣ�� Ver 1.1 <br>  
	* �������ڣ�2011-12-12 <br>  
	* ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����    <br>
	**************************************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2011-12-12 ����3:23:00<br>  
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2011-12-12 ����3:23:00  <br> 
	* �޸ı�ע��   <br>
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
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.general.action  <br>
 * �����ƣ�DutyPersonInfoAction  <br> 
 * �ļ�������ֵ����Ա��Ϣaction   <br>
 *
 * @see <br>
 * �汾��Ϣ��Ver 1.1 <br>
 *********************************<br>
 * �����ˣ�Vasin  <br> 
 * �������ڣ�2011-12-12 ����3:23:00  <br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2011-12-12 ����3:23:00  <br> 
 * �޸ı�ע��     <br>
 */
public class DutyPersonInfoAction extends Controller {

	private Logger logger = Logger.getLogger(DutyPersonInfoAction.class);
	
	/**
	 * 
		* �������ƣ�doGetDutyPersonInfo<br>
		* ������������ȡֵ����Ա��Ϣ for jquery jqgrid <br>
		* @param action
		* @param request
		* @param response
		* @return
		* @throws Throwable<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-16 ����4:03:38 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-16 ����4:03:38  <br> 
		* �޸ı�ע��    <br>
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