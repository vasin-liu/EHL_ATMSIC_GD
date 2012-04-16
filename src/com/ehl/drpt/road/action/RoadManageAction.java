/**
 * 
 */
package com.ehl.drpt.road.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.drpt.road.dao.RoadManageDao;

/**
 * 道路管理控制类
 * @author wangxt
 * @date 2009-1-16
 *
 */
public class RoadManageAction extends Controller{
	/**
	 * 编辑道路信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyRoadInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request.getParameter("insrtOrUpdt"), 0);
		String bh = StringHelper.obj2str(request.getParameter("BH"),"");
		String jgid = StringHelper.obj2str(request.getParameter("JGID"),"");
		String dlbh = StringHelper.obj2str(request.getParameter("DLBH"),"");
		String dlmc = StringHelper.obj2str(request.getParameter("DLMC"),"");
		
		HashMap hm = new HashMap();
		hm.put("BH", bh);
		hm.put("DLBH",dlbh);
		hm.put("DLMC", dlmc);
		hm.put("JGID",jgid);
		RoadManageDao rmd = new RoadManageDao();
		boolean flag = false;
		if(insertOrUpdate==0) {
			flag = rmd.addRoadInfo(hm);
			if(flag) {
				out.write("success");
			}else {
				out.write("fail");
			}
		} else {
			flag = rmd.editRoadInfo(hm);
			if(flag) {
				out.write("success");
			}else {
				out.write("fail");
			}
		}
		out.close();
		return null;
	} 
	/**
	 * 删除道路信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteRoadInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String bh = StringHelper.obj2str(request.getParameter("BH"),"");
		RoadManageDao rmd = new RoadManageDao();
		boolean flag = rmd.delteRoadInfo(bh);
		if(flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
		out.close();
		return null;
	}
	
	/**
	 * 查询道路信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetRoadInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String bh = StringHelper.obj2str(request.getParameter("BH"),"");
		RoadManageDao rmd = new RoadManageDao();
		Object[] obj = rmd.getRoadInfo(bh);
		String res = objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
	
	/**
	 * 一维数组转换为xml
	 * @param 一维数组
	 * @return
	 */
	public static String objArrayToXml(Object res[]){
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
				
		if (res != null){
			for (int j = 0; j < res.length; j++) {
				if(res[j] == null || "".equals(res[j].toString().trim())){
					xmlData.append("<col>　</col>");
				}else{
					xmlData.append("<col>" + StringHelper.obj2str(res[j]) + "</col>");
				}
			}
		}
		
		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}	
	
}
