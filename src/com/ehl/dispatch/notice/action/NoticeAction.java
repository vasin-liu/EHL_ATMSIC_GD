
/**
 * 
 */
package com.ehl.dispatch.notice.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.StringUtil;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.common.PromptLog;
import com.ehl.dispatch.notice.core.NoticeCore;
import com.ehl.sm.pcs.DepartmentManage;

/**
 * �������ش����
 * @author xiayx
 * @date 2011-06-24
 *
 */
public class NoticeAction extends Controller{
//	private Logger logger = Logger.getLogger(NoticeAction.class);
	private NoticeCore core = new NoticeCore();
	
	/**
	 * <pre>
	 * ����ش����
	 * t_oa_notice(title,spdcode)
	 * t_oa_content(content,apath,spname,stime)
	 * t_oa_acceptdept(rpdcode)
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doAddNotice(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String udcode = DepartmentManage.getDeptInfo(request, "0");
		String[] noticeParams = {"id","title","content","apath","spname","stime"};
		Map<String,String> noticeMap = FlowUtil.getParams(request, noticeParams, true);
		noticeMap.put("spdcode", udcode);
		String[] accdeptParams = {"rpdcode"};
		Map<String,String> accdeptMap = FlowUtil.getParams(request, accdeptParams, true);
		String result = core.addNotice(noticeMap,accdeptMap);
		out.write(result);
		out.close();
		return null;
	}
	
	public ActionForward doSend(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String aid = request.getParameter("aid");
		String adcode = request.getParameter("adcode");
		
		boolean isOK = core.send(aid, adcode);
		
		out.write(String.valueOf(isOK));
		out.close();
		return null;
	}
	
	
	
	/**
	 * <pre>
	 * ��ȡ�ش������Ϣ
	 * �Զ���ȡ�û���������
	 * 1.��ȡ��������Ϣ id=����ID stype=1
	 *  1.1 ��ȡnotice��Ϣ notice(id)=id
	 *  1.2 ��ȡcontent��Ϣ content(sid)=id
	 *  1.3 ��ȡaccdept��Ϣ acceptdept(aid)=id and acid is null
	 *  1.4 ��ȡcontent��Ϣ content(sid)=acceptdept(id)
	 *  1.5 ��ȡaccdept��Ϣ acceptdept(acid)=content(id)
	 *  1.6 accdept��Ϣ�а�����ǰ����
	 * 2.��ȡ��������Ϣ id=���յ�λID/����ID stype=2
	 *  2.1 ��ȡaccdept��Ϣ acceptdept(id)=id/rpdcode=udcode and  aid=id and acid is null
	 *  2.2 ��ȡnotice��Ϣ  
	 *  2.3 ��ȡcontent��Ϣ
	 *  2.4 ��ȡaccdept��Ϣ
	 * 3.��ȡת������Ϣ id=���յ�λID/����ID stype=3
	 *  3.1 ��ȡaccdept��Ϣ
	 *  3.2 ��ȡnotice��Ϣ
	 *  
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNotice(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String result;
		boolean isExp = false;
		int stype = 1;
		try {
			stype = Integer.parseInt(request.getParameter("stype"));
		} catch (Exception e) {
			isExp = true;
		}
		String id = request.getParameter("id");
		String udcode = DepartmentManage.getDeptInfo(request, "0");
		if(isExp == false){
			result = core.getNotice(id,udcode,stype);
		}else{
			result = null;
		}
		System.out.println("xml:\n"+result);
		out.write(result);
		return null;
	}


	/**
	 * <pre>
	 * �����ش�������������
	 * 1.��ӷ��ͷ�����
	 *  1.1 notice(id)��content(content,spname,stime) sid=����ID��stype=1
	 * 2.��ӽ��շ�����
	 *  2.1 accdept(id)��content(content,spname,stime) sid=���յ�λID��stype=2
	 *  2.2 accdept(aid,rpdcode)��content(content,spname,stime) sid=����ID��stype=2
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateNotice(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";
		boolean isExp = false;
		int stype = 1;
		try {
			stype = Integer.parseInt(request.getParameter("stype"));
		} catch (Exception e) {
			isExp = true;
		}
		if(isExp == false){
			String[] params = core.ctcore.dao.cols;
			Map<String,String> contentMap = FlowUtil.getParams(request, params, true);
			String udcode = DepartmentManage.getDeptInfo(request, "0");
			String adcode = request.getParameter("adcode");
			//Modified by Liuwx 2011-07-29
			String cids = StringHelper.obj2str(request.getParameter("cids"), "");
			contentMap.put("cids", cids);
			result = core.updateNotice(stype,udcode,adcode,contentMap);
			//Modified by Liuwx 2011-07-29
		}else{
			result = "false";
		}
		out.write(result);
		return null;
	}
	
	/**
	 * �޸��ش����
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyNotice(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String[] params = core.dao.cnames;
		Map<String,String> noticeMap = FlowUtil.getParams(request, params, true);
		String udcode = DepartmentManage.getDeptInfo(request, "0");
		boolean isOK = core.finish(udcode,noticeMap);
		out.write(String.valueOf(isOK));
		out.close();
		return null;
			
	 }
	
	/**
	 * ɾ�����ش���Ϣ
	 */
	public ActionForward doDeleteById(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		boolean isOK = core.deleteById(id);
		out.write(String.valueOf(isOK));
		out.close();
		return null;
	}
	
	/**
	 * �õ��ش����������Ŀ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptCount(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String udcode = DepartmentManage.getDeptInfo(request, "0");
		String result = core.getPromptCount(udcode);
		PromptLog.debug(request, "ֵ����־", new String[]{"�յ�����Ϣ", "��ʱδǩ������",
		"��ʱδ�������"}, null, result);
		out.write(result);
		out.flush();
		return null;
	}
	
	/**
	 * �õ��ش����������Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ptype = request.getParameter("ptype");
		String ids = request.getParameter("id");
		String result = core.getPromptInfo(ids, ptype);
		out.write(result);
		out.flush();
		return null;
	}
	
	/**
	 * ����Excel<br/>
	 * ����Excel
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
		String fileName = "�������ش������Ϣ";
		String titleName = "�������ش������Ϣ";
		String tabHeader = "���,����,���͵�λ,���յ�λ,����ʱ��,����״̬,��Ϣ����";
		String sheetName = "�������ش������Ϣ��";
		String sql = StringHelper.obj2str(request.getParameter("searchSql"), "");
		Object[][] tabData;
		try {
			sql = StringUtil.convertSql(sql);
			tabData = DBHandler.getMultiResult(sql);
			if(tabData == null){
				tabData = new Object[1][7];
			}else{
				String[] states = {"δǩ��","��ǩ��","�Ѵ���"};
				String[] types = {"����","����","ת��"};
				int sindex,tindex;
				for (int i = 0; i < tabData.length; i++) {
					sindex = Integer.parseInt(String.valueOf(tabData[i][5]));
					tindex = Integer.parseInt(String.valueOf(tabData[i][6]));
					tabData[i][5] = states[sindex-1];
					tabData[i][6] = types[tindex-1];
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