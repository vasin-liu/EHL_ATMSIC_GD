package com.ehl.dispatch.xcbk.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.StringUtil;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.xcbk.core.XcbkCore;
import com.ehl.dispatch.xcbk.dao.XcbkDao;
import com.ehl.sm.pcs.DepartmentManage;



public class XcbkAction extends Controller {
	
	private Logger logger = Logger.getLogger(XcbkAction.class);
	private XcbkCore xcbkCore = new XcbkCore();
	
	/**
	 * 添加协查布控信息<br>
	 * carnumber、cartype、carcolor、content、
	 * lpdname、lpname、lpphone、frpdcode、frpname、adcode
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doAddXcbk(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[] paramsIn = XcbkDao.cols;
		Map<String,String> parammIn = FlowUtil.getParams(request, paramsIn,true);
		String result = xcbkCore.addXcbk(parammIn);
		out.write(result);
		return null;
	}
	
	
	/**
	 * <pre>
	 * 查询协查布控信息
	 * 1.查看
	 * 	1.发送信息
	 * 	2.接收信息
	 * 2.提醒
	 * 	1.新信息          0  jgid,state,mstate,
	 * 	2.更新信息     1
	 * 	3.解除信息     2
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSearchXcbk(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String rpdcode = DepartmentManage.getDeptInfo(request, "0");;
		String state = request.getParameter("state");
		state = state == null ? "1" : state;
		String mstate = request.getParameter("mstate");
		String xml = xcbkCore.searchXcbk(rpdcode, state, mstate);
		System.out.println("xcbkXML:\n"+xml);
		out.write(xml);
		return null;
	}


	/**
	 * 查看协查布控信息<br>
	 * 协查通报ID、用户机构代码
	 * 1.发送单位
	 * 2.接收单位
	 * 3.转发单位
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetXcbk(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String result;
		String id = request.getParameter("id");
		int stype;
		try {
			stype = Integer.parseInt(request.getParameter("stype"));
			result = xcbkCore.getXcbk(id, stype);
		} catch (Exception e) {
			result = "false";
		}
		System.out.println("xml:\n"+result);
		out.write(result);
		return null;
	}
	
	/**
	 * 修改协查布控信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyXcbk(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[] paramsIn = XcbkDao.cols;
		Map<String,String> parammIn = FlowUtil.getParams(request, paramsIn,true);
		boolean isOK = xcbkCore.modifyXcbk(parammIn);
		out.write(String.valueOf(isOK));
		return null;
	}


	/**
	 * 更新协查布控信息<br>
	 * carnumber、cartype、carcolor、content、
	 * lpdname、lpname、lpphone、frpdcode、frpname、adcode
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateXcbk(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[] paramsIn = XcbkDao.cols;
		Map<String,String> parammIn = FlowUtil.getParams(request, paramsIn,true);
		String  result = xcbkCore.updateXcbk(parammIn);
		out.write(result);
		return null;
	}
	
	/**
	 * 解除协查布控信息<br>
	 * carnumber、cartype、carcolor、content、
	 * lpdname、lpname、lpphone、frpdcode、frpname、adcode
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doCancleXcbk(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String  result = xcbkCore.cancelXcbk(id);
		out.write(result);
		return null;
	}
	
	/**
	 * 导出Excel<br/>
	 * 导出Excel
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doShowExcel(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String fileName = "车辆协查通报信息";
		String titleName = "车辆协查通报信息";
		String tabHeader = "编号,号牌号码,发送单位,发送时间,接收单位,转发单位,状态";
		titleName = "车辆协查通报信息";
		String sheetName = "车辆协查通报信息表";
		String sql = StringHelper.obj2str(request.getParameter("searchSql"), "");
		sql = StringUtil.convertSql(sql);
		Object[][] tabData;
		try {
			tabData = DBHandler.getMultiResult(sql);
			if(tabData == null){
				tabData = new Object[1][7];
			}else{
				for (int i = 0; i < tabData.length; i++) {
					Object[] object = new Object[7];
					System.arraycopy(tabData[i],0,object, 0, object.length);
					tabData[i] = object;
				}
			}
		} catch(Exception e) {
			tabData = new Object[1][7];
		}
		CommonUtility comUtility = new CommonUtility();
		comUtility.wirteToExcel(response, tabHeader, fileName, sheetName, tabData, titleName);
		return null;
	}
	
	
	
	
	
}
