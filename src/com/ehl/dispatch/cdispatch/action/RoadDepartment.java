package com.ehl.dispatch.cdispatch.action;

import org.apache.log4j.Logger;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.sm.common.Constants;
import com.ehl.sm.sysmanage.LogManage;

import com.ehl.dispatch.cdispatch.core.RoadSectionUtil;
import com.ehl.dispatch.cdispatch.core.RoadDepartmentUtil;
import com.ehl.dispatch.cdispatch.dao.RoadDepartmentDao;


/**
 * @����: dxn
 * @�汾�ţ�1.0
 * @˵������·���������
 * @�������ڣ�2010-4-2
 * @�޸��ߣ�
 * @�޸����ڣ�
 */
public class RoadDepartment extends Controller {
	Logger logger = Logger.getLogger(RoadSectionCtrl.class);
	
	/**
	 * @����: dxn
	 * @�汾�ţ�1.0
	 * @����˵�����༭��·����
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doEditRoadDepartment(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadDepartmentUtil.setRoadDepartment(request);
		RoadDepartmentDao sct = new RoadDepartmentDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("success");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭��·Ͻ����Ϣ");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @����: dxn
	 * @�汾�ţ�1.0
	 * @����˵����ɾ��·����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doDelRoadDepartment(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadDepartmentUtil.delRoadDepartment(request);
		RoadDepartmentDao sct = new RoadDepartmentDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("ɾ���ɹ���");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭��·Ͻ����Ϣ");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @����: dxn
	 * @�汾�ţ�1.0
	 * @����˵������ȡ��·������Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doGetRoadDepartment(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String sql = RoadDepartmentUtil.getRoadDepartment(request);
		RoadDepartmentDao sct = new RoadDepartmentDao();
		out.write(sct.getRoadDepartment(sql));
		out.close();
		return null;
	}
}
