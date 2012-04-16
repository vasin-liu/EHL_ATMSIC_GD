/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.RoadManageDao;

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
		String ID = StringHelper.obj2str(request.getParameter("ID"),"");
		String ROADID = StringHelper.obj2str(request.getParameter("ROADID"),"");
		String OLDROADID = StringHelper.obj2str(request.getParameter("OLDROADID"),"");
		String ROADNAME = StringHelper.obj2str(request.getParameter("ROADNAME"),"");
		String START = StringHelper.obj2str(request.getParameter("START"),"");
		String END = StringHelper.obj2str(request.getParameter("END"),"");
		String ROADLEVEL = StringHelper.obj2str(request.getParameter("ROADLEVEL"),"");
		
		// 道路等级
		HashMap hm = new HashMap();
		hm.put("ID", ID);
		hm.put("ROADID", ROADID);
		hm.put("ROADNAME",ROADNAME);
		hm.put("START", START);
		hm.put("END", END);
		hm.put("ROADLEVEL", ROADLEVEL);
		RoadManageDao rmd = new RoadManageDao();
		boolean flag = false;
		if(insertOrUpdate==0) {
			if(rmd.checkSingleRoad(ROADID)){
				flag = rmd.addRoadInfo(hm);
				if(flag) {
					out.write("success");
				}else {
					out.write("新增道路信息失败！");
				}
			}else{
				out.write("新增道路编号重复！");
			}
		} else {
			if(ROADID.equals(OLDROADID)){
				flag = rmd.editRoadInfo(hm);
				if(flag) {
					out.write("success");
				}else {
					out.write("fail");
				}
			}else{
				if(rmd.checkSingleRoad(ROADID)){
					flag = rmd.editRoadInfo(hm);
					if(flag) {
						out.write("success");
					}else {
						out.write("fail");
					}
				}else{
					out.write("修改道路编号重复！");
				}
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
		String ID = StringHelper.obj2str(request.getParameter("ID"),"");
		RoadManageDao rmd = new RoadManageDao();
		boolean flag = rmd.delteRoadInfo(ID);
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
		String ID = StringHelper.obj2str(request.getParameter("ID"),"");
		RoadManageDao rmd = new RoadManageDao();
		Object[] obj = rmd.getRoadInfo(ID);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
	
	
	
}
