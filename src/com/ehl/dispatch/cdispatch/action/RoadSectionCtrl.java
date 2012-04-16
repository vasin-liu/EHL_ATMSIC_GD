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
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.RoadSectionDao;


/**
 * @����: wkz
 * @�汾�ţ�1.0
 * @˵�����ϱ���Ϣ������
 * @�������ڣ�2010-4-2
 * @�޸��ߣ�
 * @�޸����ڣ�
 */
public class RoadSectionCtrl extends Controller {
	Logger logger = Logger.getLogger(RoadSectionCtrl.class);
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵�����༭·����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doEditRoadSection(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadSectionUtil.setRoadSectionInfo(request);
		RoadSectionDao sct = new RoadSectionDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("success");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭·����Ϣ");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵����ɾ��·����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doDelRoadSection(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadSectionUtil.delRoadSectionInfo(request);
		RoadSectionDao sct = new RoadSectionDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("ɾ���ɹ���");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "�༭·����Ϣ");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵������ȡΨһ·����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doGetRoadSection(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String sql = RoadSectionUtil.getRoadSectionInfo(request);
		RoadSectionDao sct = new RoadSectionDao();
		out.write(sct.getRoadSectionInfo(sql));
		out.close();
		return null;
	}
}
