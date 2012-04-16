package com.ehl.dispatch.cdispatch.action;

import org.apache.log4j.Logger;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.utils.StringHelper;
import com.appframe.action.mapping.ActionForward;
import com.ehl.sm.common.Constants;
import com.ehl.sm.sysmanage.LogManage;

import com.ehl.dispatch.cdispatch.core.RoadSectionUtil;
import com.ehl.dispatch.cdispatch.core.RoadPathUtil;
import com.ehl.dispatch.cdispatch.dao.RoadPathDao;


/**
 * @����: dxn
 * @�汾�ţ�1.0
 * @˵������·���������
 * @�������ڣ�2010-4-2
 * @�޸��ߣ�
 * @�޸����ڣ�
 */
public class RoadPath extends Controller {
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
	public ActionForward doEditRoadPath(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadPathUtil.setRoadPath(request);
		RoadPathDao sct = new RoadPathDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("success");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭��·������Ϣ");
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
	public ActionForward doDelRoadPath(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadPathUtil.delRoadPath(request);
		RoadPathDao sct = new RoadPathDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("ɾ���ɹ���");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭��·������Ϣ");
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
	public ActionForward doGetRoadPath(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String sql = RoadPathUtil.getRoadPath(request);
		RoadPathDao sct = new RoadPathDao();
		out.write(sct.getRoadPath(sql));
		out.close();
		return null;
	}
	
	public ActionForward doGetRoadPaths(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String sql = RoadPathUtil.getRoadPaths(request);
		RoadPathDao sct = new RoadPathDao();
		out.write(sct.getRoadPaths(sql));
		out.close();
		return null;
	}
}
