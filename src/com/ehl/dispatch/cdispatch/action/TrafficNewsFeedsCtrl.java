package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.TrafficNewsFeedsDao;
import com.ehl.dispatch.cdispatch.util.CutInfoAdd;
import com.ehl.dispatch.common.DispatchUtil;
import com.ehl.dispatch.duty.dao.DutyDao;

/**
 * 交通新闻报料控制类
 */
public class TrafficNewsFeedsCtrl extends Controller{
	private Logger logger = Logger.getLogger(TrafficNewsFeedsCtrl.class);
	
	
	/**
	 * 查询交通报料详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	*/
	public ActionForward doAddNewsInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		
		String[] params = {"dlmc","ldmc","ldfx","qslc","qslcm","zzlc","zzlcm",
				"gxdd","qssj","lk","lkyy","bz","blr","lxfs","lrr","lrbm","lrsj"};
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < params.length; i++) {
			map.put(params[i], request.getParameter(params[i]));
		}
//		map.put("bh", DepartmentManage.getDeptInfo(request, "0").substring(0,6));
		TrafficNewsFeedsDao dao = new TrafficNewsFeedsDao();
		String result = String.valueOf(dao.addNewsInfo(map));
		out.write(result);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 查询交通报料详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	*/
	public ActionForward doGetNewsInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		
		String bh = StringHelper.obj2str(request.getParameter("bh"),"");
		if(!"".equals(bh)){
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			String deptid = (String)DispatchUtil.getCurrentUserData(request).get("BRANCHID");
			DutyDao dd = new DutyDao();
			//当日值班人员
			Object dutyPerson[] = dd.getPerson(deptid);
			String pname = "";
			if(dutyPerson !=null && dutyPerson.length>0){
				pname = StringHelper.obj2str(dutyPerson[0],"");
			}
			Object[] res = tcd.doGetNewsInfo(bh,pname);
			String str = DataToXML.objArrayToXml2(res);
			System.out.println("TNF:\n"+str);
			out.write(str);
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * 忽略交通报料详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpNewsInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		
		String bh = StringHelper.obj2str(request.getParameter("bh"),"");
		String clzt = StringHelper.obj2str(request.getParameter("clzt"),"");
		String sTag = "false";
		if(!"".equals(bh)&&!"".equals(clzt)){
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			boolean bTag = tcd.cancelNews(bh,clzt);
			if(bTag){
				sTag="true";
			}
		}
		out.write(sTag);
		out.close();
		return null;
	}
	
	/**
	 * 反馈交通报料详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doFeedbackNewsInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		
		boolean bTag = false;
		String sTag = "false";
		String bh = StringHelper.obj2str(request.getParameter("bh"),"");
		//if(!"".equals(bh)){
			String dlmc = StringHelper.obj2str(request.getParameter("dlmc"),"").trim();
			String ldmc = StringHelper.obj2str(request.getParameter("ldmc"),"").trim();
			String ldfx = StringHelper.obj2str(request.getParameter("ldfx"),"").trim();
			String qslc = StringHelper.obj2str(request.getParameter("qslc"),"").trim();
			String qslcm = StringHelper.obj2str(request.getParameter("qslcm"),"").trim();
			String zzlc = StringHelper.obj2str(request.getParameter("zzlc"),"").trim();
			String zzlcm = StringHelper.obj2str(request.getParameter("zzlcm"),"").trim();
			String lk = StringHelper.obj2str(request.getParameter("lk"),"").trim();
			String kssj = StringHelper.obj2str(request.getParameter("kssj"),"").trim();
			String zzsj = StringHelper.obj2str(request.getParameter("zzsj"),"").trim();
			String gxdd = StringHelper.obj2str(request.getParameter("gxdd"),"").trim();
			String wzms = StringHelper.obj2str(request.getParameter("wzms"),"").trim();
			String lkyy = StringHelper.obj2str(request.getParameter("lkyy"),"").trim();
			String sfjs = StringHelper.obj2str(request.getParameter("sfjs"),"").trim();
			String blr =  StringHelper.obj2str(request.getParameter("blr"),"").trim();
			String lxfs = StringHelper.obj2str(request.getParameter("lxfs"),"").trim();
			String lrr =  StringHelper.obj2str(request.getParameter("lrr"),"").trim();
			String lrsj = StringHelper.obj2str(request.getParameter("lrsj"),"").trim();
//			String ddfk = StringHelper.obj2str(request.getParameter("ddfk"),"").trim();
			String clzt = StringHelper.obj2str(request.getParameter("clzt"),"").trim();
			
			String hsr = StringHelper.obj2str(request.getParameter("hsr"),"").trim();
			String hssj = StringHelper.obj2str(request.getParameter("hssj"),"").trim();
			String hsqk = StringHelper.obj2str(request.getParameter("hsqk"),"").trim();
			
			String scr = StringHelper.obj2str(request.getParameter("scr"),"").trim();
			String scsj = StringHelper.obj2str(request.getParameter("scsj"),"").trim();
			String scyj = StringHelper.obj2str(request.getParameter("scyj"),"").trim();
			String pf = StringHelper.obj2str(request.getParameter("pf"),"-1").trim();
			String sfcswhs = StringHelper.obj2str(request.getParameter("sfcswhs"),"0").trim();
			
			List<String> feedback = new ArrayList<String>();//反馈信息
			feedback.add(bh);//bh
			feedback.add(dlmc);
			feedback.add(ldfx);
			feedback.add(qslc);
			feedback.add(zzlc);
			feedback.add(lk);
			feedback.add(kssj);
			feedback.add(zzsj);
			feedback.add(gxdd);
			feedback.add(wzms);
			feedback.add(lkyy);
			feedback.add(sfjs);
			feedback.add(blr);
			feedback.add(lxfs);
			feedback.add(lrr);
			feedback.add(lrsj);
			feedback.add(clzt);
			
			feedback.add(hsr);
			feedback.add(hssj);
			feedback.add(hsqk);
			
			feedback.add(scr);
			feedback.add(scsj);
			feedback.add(scyj);
			feedback.add(pf);
			feedback.add(sfcswhs);
			
			feedback.add(ldmc);
			feedback.add(qslcm);
			feedback.add(zzlcm);
			
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			bTag = tcd.feedbackNewsInfo(feedback);
			if(bTag){
				sTag="true";
			}
		//}
		out.write(sTag);
		out.close();
		return null;
	}
	
	/**
	 * 删除或废弃交通报料详细信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDelNewsInfo(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();

		boolean bTag = false;
		String sTag = "false";
		String bh = StringHelper.obj2str(request.getParameter("bh"),"");
		if(!"".equals(bh)){
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			bTag = tcd.deleteNews(bh);
			if(bTag){
				sTag="true";
			}
		}
		out.write(sTag);
		out.close();
		return null;
	}

	/**
	 * 取得提醒报料信息件数<br/>
	 * 提醒信息件数的取得处理
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
//		String getType = StringHelper.obj2str(request.getParameter("type"),"0");//0,待处理，1,待确认
		String deptid = (String)DispatchUtil.getCurrentUserData(request).get("BRANCHID");
		String deptname = (String)DispatchUtil.getCurrentUserData(request).get("BRANCHNAME");
		if(!"".equals(deptid)){
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			Object [] resoults = new Object[2];
			resoults[0] = tcd.getNewFeedsCount(deptid,deptname,"0");
			resoults[1] = tcd.getNewFeedsCount(deptid,deptname,"1");
			String str = DataToXML.objArrayToXml2(resoults);
			out.write(str);
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
	
	/**
	 * 获取拥堵提醒信息<br/>
	 * 拥堵提醒信息的取得
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public ActionForward doGetPromptInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String getType = StringHelper.obj2str(request.getParameter("type"),"0");//0,待处理，1,待确认
		Object[][] obj = null;
		String str = "";
		String deptcode = ""; //部门编码
		String deptname = ""; //部门名称
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			deptname = (String) prop.get("BRANCHNAME");
		}
		try {
			if(!"".equals(deptcode)){
				TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
				obj = tcd.getNewFeedsInfo(deptcode,deptname,getType);
				if(obj !=null && obj.length>0){
					str = DataToXML.objArrayToXml(obj);
				}
			}
		} catch (Exception e) {
			out.write("");
			logger.error("查询拥堵信息件数:" + e.getMessage());
			e.printStackTrace();
		}
		out.write(str);
		out.close();
		return null;
	}
	

	/**
	 * 通过道路名称取得道路方向<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetDlFxByRoadName(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String seasrchRoadName = StringHelper.obj2str(request.getParameter("seasrchRoadName"),"");//道路名称
		if(!"".equals(seasrchRoadName)){
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			 Object[] results = tcd.getdaoluFx(seasrchRoadName);
			String str = DataToXML.objArrayToXml(results);
			out.write(str);
		}else{
			out.write("");
		}
		out.close();
		return null;
	}
}
