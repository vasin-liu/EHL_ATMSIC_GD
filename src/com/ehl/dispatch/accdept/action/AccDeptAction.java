package com.ehl.dispatch.accdept.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.common.PromptLog;
import com.ehl.sm.pcs.DepartmentManage;

public class AccDeptAction extends Controller{
	
	private Logger logger = Logger.getLogger(AccDeptAction.class);
	private AccDeptCore core = new AccDeptCore();
	
	/**
	 * ���ת����λ<br>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doAddAccDept(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String aid = request.getParameter("aid");
		String adcode = request.getParameter("adcode");
		String udcode = DepartmentManage.getDeptInfo(request, "0");
		String result = core.addAccDept(aid,udcode,adcode);
		out.write(result);
		
		return null;
	}
	
	
	/**
	 * ���Ҿ�����������<br>
	 * jgid������š�atype�������͡�state����״̬��mstate��Ϣ״̬
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSearchCount(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[] paramsIn = AccDeptDao.cnames;
		Map<String, String> pimap = FlowUtil.getParams(request, paramsIn, true);
		pimap.put("rpdcode", DepartmentManage.getDeptInfo(request, "0"));
		String[] data = core.searchCount(pimap);
		String xml = DataToXML.objArrayToXml(data);
		try {
			int atype = Integer.parseInt(request.getParameter("atype")) - 1;
			String[] descs = AccDeptDao.mstates.clone();
			descs[2] = "���";
			String mstate = request.getParameter("mstate");
			if(mstate != null){
				int intMstate = Integer.parseInt(mstate) - 1;
				descs = new String[]{descs[intMstate]};
			}
			PromptLog.debug(request, AccDeptDao.atypes[atype], descs, data, xml);
		} catch (Exception e) {
			logger.error("��¼��Ϣ������־", e);
		}
		out.write(xml);
		return null;
	}
	
	/**
	 * ���½��յ�λ����״̬<br>
	 * ���ջ�������ID������״̬��ֵ��������������ʱ��
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateState(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		String aid = request.getParameter("aid");
		String udcode = DepartmentManage.getDeptInfo(request, "0");
		boolean isOK = false;
		if(id == null && aid != null){
			Map<String, String> paramMap = FlowUtil.getParams(request,
					AccDeptDao.cnames, true);
			paramMap.put("rpdcode", udcode);
			isOK = core.signIn(paramMap);
		}else if(id != null && aid == null){
			String state = request.getParameter("state");
			state = state == null ? "2" : state;
			String[] params = {"rpname","rtime"};
			Map<String,String> paramMap = FlowUtil.getParams(request, params, true);
			String stype = request.getParameter("stype");
			String name = request.getParameter("name");
			String jgid = request.getParameter("jgid");
			isOK = core.updateState(id, stype, udcode, state,paramMap);
			
			Object[] result = DBHandler.getLineResult("select rpdcode,rpname,state from T_OA_ACCEPTDEPT where aid = '"+ id +"'and rpdcode = "+ jgid +"");
			if(name != null) {
				if(jgid.equals(String.valueOf(result[0]))) {
					isOK = DBHandler.execute("update T_OA_ACCEPTDEPT set RPNAME = '" + name + "' where  aid= '"+ id +"' and rpname is null and rpdcode = "+ jgid +"");
				}
			}
		}
		
		out.write(String.valueOf(isOK));
		return null;
	}
	
	/**
	 * ǩ��<br>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSignIn(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String rpname = request.getParameter("rpname");
		String rtime = request.getParameter("rtime");
		String[] params = {"id","rpdcode","aid","adid"};
		Map<String,String> whereMap = FlowUtil.getParams(request, params, true);
		boolean isOK = core.signIn(rpname, rtime, whereMap);
		out.write(String.valueOf(isOK));
		return null;
	}
	
	/**
	 * ���<br>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doFinish(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String rpname = request.getParameter("rpname");
		String rtime = request.getParameter("rtime");
		String[] params = {"id","rpdcode","aid","adid"};
		Map<String,String> whereMap = FlowUtil.getParams(request, params, true);
		boolean isOK = core.finish(rpname, rtime, whereMap);
		
		out.write(String.valueOf(isOK));
		out.flush();
		out.close();
		return null;
	}
	
	
	
	
	
	
	
	
}
