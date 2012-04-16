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
 * @作者: dxn
 * @版本号：1.0
 * @说明：道路方向操作类
 * @创建日期：2010-4-2
 * @修改者：
 * @修改日期：
 */
public class RoadPath extends Controller {
	Logger logger = Logger.getLogger(RoadSectionCtrl.class);
	
	/**
	 * @作者: dxn
	 * @版本号：1.0
	 * @函数说明：编辑道路方向
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doEditRoadPath(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadPathUtil.setRoadPath(request);
		RoadPathDao sct = new RoadPathDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("success");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "编辑道路方向信息");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @作者: dxn
	 * @版本号：1.0
	 * @函数说明：删除路段信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doDelRoadPath(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();		
		List<String> sqlList = RoadPathUtil.delRoadPath(request);
		RoadPathDao sct = new RoadPathDao();
		boolean bRes = sct.excuteSql(sqlList);
		if(bRes){
			out.write("删除成功！");
			HttpSession session = request.getSession();
			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
			LogManage.writeEvent(LCODE, "编辑道路方向信息");
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * @作者: dxn
	 * @版本号：1.0
	 * @函数说明：获取道路方向信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public ActionForward doGetRoadPath(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
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
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String sql = RoadPathUtil.getRoadPaths(request);
		RoadPathDao sct = new RoadPathDao();
		out.write(sct.getRoadPaths(sql));
		out.close();
		return null;
	}
}
