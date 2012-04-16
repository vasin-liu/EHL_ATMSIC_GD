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
 * @作者: wkz
 * @版本号：1.0
 * @说明：上报信息操作类
 * @创建日期：2010-4-2
 * @修改者：
 * @修改日期：
 */
public class RoadSectionCtrl extends Controller {
	Logger logger = Logger.getLogger(RoadSectionCtrl.class);
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：编辑路段信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doEditRoadSection(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadSectionUtil.setRoadSectionInfo(request);
		RoadSectionDao sct = new RoadSectionDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("success");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "编辑路段信息");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：删除路段信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doDelRoadSection(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadSectionUtil.delRoadSectionInfo(request);
		RoadSectionDao sct = new RoadSectionDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("删除成功！");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "编辑路段信息");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：获取唯一路段信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doGetRoadSection(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String sql = RoadSectionUtil.getRoadSectionInfo(request);
		RoadSectionDao sct = new RoadSectionDao();
		out.write(sct.getRoadSectionInfo(sql));
		out.close();
		return null;
	}
}
