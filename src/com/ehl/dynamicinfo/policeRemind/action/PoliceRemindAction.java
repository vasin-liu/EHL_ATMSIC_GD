package com.ehl.dynamicinfo.policeRemind.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.common.dao.UserSettingDao;
import com.ehl.dynamicinfo.policeRemind.core.PoliceRemindCore;
import com.ehl.sm.base.Constant;
import com.ehl.tira.util.XML;
import com.ehl.util.Collections;

public class PoliceRemindAction extends Controller{
	private Logger logger = Logger.getLogger(PoliceRemindAction.class);
	private AccDeptCore adcore = new AccDeptCore();
	/**
	 * 警情提示信息插入的处理<br/>
	 * 警情提示信息插入的处理
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doInsertPoliceRemindInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer(); 
	 	try {
			String departmentId = StringHelper.obj2str(request.getParameter("deptidForCenter"), "");
			String remindInfo = StringHelper.obj2str(request.getParameter("remindInfo"), "");
			String deptNameForCenter = StringHelper.obj2str(request.getParameter("deptNameForCenter"), "");
			
			String userName = StringHelper.obj2str(request.getSession().getAttribute("zbrName"), "");
	
			String policeRemind = CreateSequence.getMaxForSeq("SEQ_T_OA_POLICE_REMIND", 20);
			// 警情编号,道路名称,施工方向,X,Y
			sql.append("insert into T_OA_POLICE_REMIND ( REMINDID, REMINDTIME, DEPARTMENTID, DEPARTMENTNAME, REMINDINFO, USERNAME,SOURCE,PID) values (");
			sql.append(" '");
			sql.append(policeRemind);
			sql.append("',");
			sql.append("sysdate,");
			sql.append(" '");
			sql.append(departmentId);
			sql.append("',");
			sql.append(" '");
			sql.append(deptNameForCenter);
			sql.append("',");
			sql.append(" '");
			sql.append(remindInfo);
			sql.append("',");
			sql.append(" '");
			sql.append(userName);
			sql.append("'");
			sql.append(",'1'");
			sql.append(",'"+policeRemind+"'");
			sql.append(")");
			System.out.println("***********"+sql);
			boolean flg = DBHandler.execute(String.valueOf(sql));
			if(flg) {
				out.write("success");	
			} else {
				logger.error("插入警情提示信息出错。 sql:" + sql);
				out.write("false");
			}
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("插入警情提示信息出错。 sql:" + sql);
	 	}
		return null;
	}
	
	/**
	 * 取得交警提示信息<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetRemindInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String strSql = "";
		//Modified by Liuwx 2011-8-26
		//现在从视图中获取警情提示信息，并且用户可以选择过滤条件。	
		String areaParams = StringHelper.obj2str(request.getParameter("areaParams"), "");
		String levelParams = StringHelper.obj2str(request.getParameter("levelParams"), "");
		String typeParams = StringHelper.obj2str(request.getParameter("typeParams"), "");
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		
		UserSettingDao usd = new UserSettingDao();
		String id = null;
		if (paramsMap != null) {
			//数据库是否已经存在用户的设置
			id = usd.getUserSettingId(paramsMap);
		}
		//默认查询语句
		strSql = " SELECT REMINDID,TO_CHAR(REMINDTIME,'yyyy\"年\"mm\"月\"dd\"日\" HH24\"时\"mi\"分\"')," +
		"DEPARTMENTID,DEPARTMENTNAME,REMINDINFO,USERNAME FROM V_POLICE_REMIND_DETAIL WHERE TO_CHAR(REMINDTIME,'yyyy-mm-dd') = " +
		"TO_CHAR(SYSDATE,'yyyy-mm-dd')";
		
		if (areaParams == "" && levelParams == "" && typeParams == "") {//页面加载时的过滤
			if (id != null && id.length() > 0) {//数据库中存在用户的默认设置值时取出并按默认值查询
				paramsMap.remove("id");
				paramsMap.put("id", id);
				String sql = "SELECT ELEMENT_KEY_VALUE FROM T_OA_USER_SETTING WHERE ID='" + id + "'";
				String result = StringHelper.obj2str(DBHandler.getSingleResult(sql), "");
				Map<String, String> filter = usd.splitElementMap(result);
				String areaInString = "" , levelInString = "" , typeInString = "";
				ArrayList<String> areaKey = new ArrayList<String>();
				ArrayList<String> levelKey = new ArrayList<String>();
				ArrayList<String> typeKey = new ArrayList<String>();
				if (filter != null) {
					Iterator<Map.Entry<String,String>> iterator = filter.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String,String> entry = (Map.Entry<String,String>) iterator.next();
						String key = StringHelper.obj2str(entry.getKey(), "");
						String value = StringHelper.obj2str(entry.getValue(), "");
						if (key != null && key.length() == 12 && value !=null && value.equals("US10001")) {
							areaKey.add(key);
						}
						if (key != null && key.length() == 1 && value !=null && value.equals("US10001")) {
							levelKey.add(key);
						}
						if (key != null && key.length() == 6 && value !=null && value.equals("US10001")) {
							typeKey.add(key);
						}
					}
					
					for (int i = 0; i < areaKey.size(); i++) {
						if (i < (areaKey.size() -1)) {
							areaInString += "'" + areaKey.get(i).substring(0, 4) + "',";
						}else {
							areaInString += "'" + areaKey.get(i).substring(0, 4) + "'";
						}
					}
					
					for (int i = 0; i < levelKey.size(); i++) {
						if (i < (levelKey.size() -1)) {
							levelInString += "'" + levelKey.get(i) + "',";
						}else {
							levelInString += "'" + levelKey.get(i) + "'";
						}
					}
					
					for (int i = 0; i < typeKey.size(); i++) {
						if (i < (typeKey.size() -1)) {
							typeInString += "'" + typeKey.get(i) + "',";
						}else {
							typeInString += "'" + typeKey.get(i) + "'";
						}
					}
					
					if (areaInString != null && areaInString.length() > 0) {
						//截取机构编码的前四位，即包括所选支队和其下属大队
						strSql += " AND SUBSTR(DEPARTMENTID,0,4) IN(" + areaInString + ")";
					}
					if (levelInString != null && levelInString.length() > 0) {
						strSql += " AND ROADLEVEL IN(" + levelInString + ")";
					}
					if (typeInString != null && typeInString.length() > 0) {
						strSql += " AND EVENTTYPE IN(" + typeInString + ")";
					}
				}
			}else {//数据库中不存在用户的默认设置值时查询所有的提醒信息
				strSql +=  " ORDER BY REMINDTIME DESC";
			}
		}else {//弹出层时选择的条件过滤
			String areaInString = null,levelInString = null,typeInString = null;
			if (areaParams != "" && areaParams.length() > 0) {
				areaInString = usd.splitElement(areaParams);
			}
			if (levelParams != "" && levelParams.length() > 0) {
				levelInString = usd.splitElement(levelParams);
			}
			if (typeParams != "" && typeParams.length() > 0) {
				typeInString = usd.splitElement(typeParams);
			}
			if (areaInString != null && areaInString.length() > 0) {
				//截取机构编码的前四位，即包括所选支队和其下属大队
				strSql += " AND SUBSTR(DEPARTMENTID,0,4) IN(" + areaInString + ")";
			}
			if (levelInString != null && levelInString.length() > 0) {
				strSql += " AND ROADLEVEL IN(" + levelInString + ")";
			}
			if (typeInString != null && typeInString.length() > 0) {
				strSql += " AND EVENTTYPE IN(" + typeInString + ")";
			}
			strSql +=  " ORDER BY REMINDTIME DESC";
		}		
		
	 	try {
//	 		strSql = " select REMINDID,to_char(REMINDTIME,'yyyy\"年\"mm\"月\"dd\"日\" HH24\"时\"mi\"分\"')," +
//	 				"DEPARTMENTID,DEPARTMENTNAME,REMINDINFO,USERNAME from T_OA_POLICE_REMIND where to_char(REMINDTIME,'yyyy-mm-dd') = " +
//	 				"to_char(SYSDATE,'yyyy-mm-dd') order by REMINDTIME desc";
	 		//Modification finished
	 		Object[][] objectInfoLst = DBHandler.getMultiResult(strSql);
			String str = DataToXML.objArrayToXml(objectInfoLst);
			out.write(str);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("查询警情提示信息出错。 sql:" + strSql);
	 		out.close();
	 	}
	 	out.close();
		return null;
	}
	
	
	/**
	 * 更新交警提示信息<br/>
	 * Modified by Liuwx 2011-07-20
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateRemindInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		String REMINDID = StringHelper.obj2str(request.getParameter("remindId"), "");
		String DEPARTMENTID = StringHelper.obj2str(request.getParameter("DEPARTMENTID"), "");
		String DEPARTMENTNAME = StringHelper.obj2str(request.getParameter("DEPARTMENTNAME"), "");
		String REMINDINFO = StringHelper.obj2str(request.getParameter("REMINDINFO"), "");
		PrintWriter out = response.getWriter();
		if (REMINDID == "" || DEPARTMENTID == "" || DEPARTMENTNAME == "" || REMINDINFO == "") {
			System.out.println("参数缺失，更新交警提示信息失败。");
			return null;
		}
		String strSql = "";
	 	try {
	 		strSql = " UPDATE T_OA_POLICE_REMIND SET DEPARTMENTID='"+DEPARTMENTID
	 			+"',DEPARTMENTNAME='"+DEPARTMENTNAME+"',REMINDINFO='"
	 			+ REMINDINFO + "'"
	 			+" WHERE REMINDID=" + REMINDID;
	 		boolean flag = DBHandler.execute(strSql);
	 		if(flag){
	 			out.write("更新交警提示信息成功。");
	 		}else{
	 			out.write("更新交警提示信息失败。");
	 		}
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("更新警情提示信息出错。 sql:" + strSql);
	 		out.close();
	 	}
	 	out.close();
		return null;
	}
	
	/**
	 * 通过提醒ID取得交警提示信息<br/>
	 * Modified by Liuwx 2011-07-20
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetRemindInfoById(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // 指定输出内容的格式
		String remindId = StringHelper.obj2str(request.getParameter("remindId"), "");
		if ( remindId == "" ) {
			System.out.println("参数缺失，获取交警提示信息失败。");
			return null;
		}
		PrintWriter out = response.getWriter();
		String strSql = "";
	 	try {
	 		strSql = " select REMINDID,to_char(REMINDTIME,'yyyy\"年\"mm\"月\"dd\"日\" HH24\"时\"mi\"分\"')," +
	 				"DEPARTMENTID,DEPARTMENTNAME,REMINDINFO,USERNAME FROM T_OA_POLICE_REMIND WHERE REMINDID = " 
	 				+ remindId +" order by REMINDTIME desc";
	 		Object[] objectInfoLst = DBHandler.getLineResult(strSql);
			String str = DataToXML.objArrayToXml(objectInfoLst);
			System.out.println("PoliceRemindXML:"+str);
			out.write(str);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("查询警情提示信息出错。 sql:" + strSql);
	 		out.close();
	 	}
	 	out.close();
		return null;
	}
	
	private PoliceRemindCore core = new PoliceRemindCore();
	
	/**
	 * 获取滚动信息，
	 * 滚动信息显示当天的最新交警提示
	 * 
	 */
	public ActionForward doGetRollInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String ptype = request.getParameter("publishtype");
		String xml = core.getRollInfo(ptype);
		logger.info("xml:\n" + xml);
		out.write(xml);
		out.close();
		return null;
	}
	
	/**
	 * 获取详细信息，
	 * 详细信息显示出该警情最初交警提示拼接信息和后续的更新信息，
	 * 
	 */
	public ActionForward doGetDetailInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		logger.info("request_params:\n"+JSONObject.fromObject(Collections.changeSingleArray(request.getParameterMap())));
		PrintWriter out = response.getWriter();
		String alarmId = request.getParameter("alarmid");
		String xml = core.getDetailInfo(alarmId);
		logger.info("xml:\n" + xml);
		out.write(xml);
		out.close();
		return null;
	}
	
	/**
	 * 获取人工改正信息，
	 * 改正信息显示出该警情最初交警提示信息、后续的更新信息以及所有的改正信息
	 * 
	 */
	public ActionForward doGetCorrectInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		String source = request.getParameter("source");
		String pid = request.getParameter("pid");
		String alarmid = request.getParameter("alarmid");
		String id = source.equals("1") ? pid : alarmid;
		String jgid = (String)request.getSession().getAttribute(Constant.JGID_VAR);
		JSONObject json = core.getCorrectInfo(source, id, jgid);
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}

	/**
	 *  获取某条警情的交警提示信息
	 */
	public ActionForward doGetByAlarmId(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter(core.getDao().getPk());
		JSONObject json = core.getByAlarmId(id);
		logger.debug("json:\n"+json);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
        out.close();
		return null;
	}
	
	/**
	 * 新增交警提示信息
	 */
	public ActionForward doInsert(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String[] cnames = core.getDao().getCnames();
		Map<String, String> object = FlowUtil.getParams(request, cnames, true);
		boolean isSuccess = core.insert(object) != null;
		out.write(XML.getXML(String.valueOf(isSuccess)));
		out.close();
		return null;
	}
	
	/**
	 * <pre>
	 * 发布交警提示信息
	 * 如果尚未发布，又没有修改提示内容，则只更新原交警提示的发布状态；
	 * 否则新增一条交警提示信息，同时更新原交警提示的发布状态。
	 * 然后，签收该信息的提醒
	 * </pre>
	 */
	public ActionForward doPublish(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		Map<String, Object> params = Collections.changeSingleArray(request.getParameterMap());
		logger.debug("params:"+JSONObject.fromObject(params));
		boolean isSuccess = true;
//		String type = request.getParameter("other.type");
		String ctime = Constant.getCurrentTime(false).substring(0,16);
		String id = request.getParameter("remindid");
		String ptype = request.getParameter("publishtype");
		if(params.get("state").equals("1")){
			String remindinfo = request.getParameter("remindinfo");
			isSuccess = core.publish(id, ptype, remindinfo);
		}else{
			String[] cnames = core.getDao().getCnames();
			Map<String, String> object = FlowUtil.getParams(request, cnames, true);
			object.remove(core.getDao().getPk());
			object.put("remindtime", ctime);
			object.remove("state");
			isSuccess = core.insert(object) != null;
			if(isSuccess){
				isSuccess = core.synch(id, ptype);
			}
		}
		
		String state = request.getParameter("accdept.state");
		if(isSuccess && state.equals("1")){
			String pname = request.getParameter("username");
			String accid = request.getParameter("accdept.id");
			isSuccess = adcore.signIn(pname, ctime, "id='" + accid + "'");
		}
		PrintWriter out = response.getWriter();
        out.write(String.valueOf(isSuccess));
        out.flush();
        out.close();
		return null;
	}

    /**
     * Modified by Liuwx 2012-4-11 14:52:17
     * <pre>
     * 更新信息发布状态
     * </pre>
     */
    public ActionForward doUpdatePublishState(Action action,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        request.setCharacterEncoding("UTF-8");
        String remindId = StringHelper.obj2str(request.getParameter("remindId"), "");
        String publishType = StringHelper.obj2str(request.getParameter("publishType"),"");

        Map<String,String> params = new HashMap<String, String>();
        params.put("remindId",remindId);
        params.put("publishType",publishType);

        if("".equals(remindId)){
            logger.error("缺少必要的参数，请检查是否已经正确传入所需参数！");
        }
        boolean isSuccess = false;
        isSuccess = core.updatePublishState(params);
        PrintWriter out = response.getWriter();
        out.write(String.valueOf(isSuccess));
        out.flush();
        out.close();
        return null;
    }
}
