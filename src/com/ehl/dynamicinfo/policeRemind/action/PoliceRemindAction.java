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
	 * ������ʾ��Ϣ����Ĵ���<br/>
	 * ������ʾ��Ϣ����Ĵ���
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
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer(); 
	 	try {
			String departmentId = StringHelper.obj2str(request.getParameter("deptidForCenter"), "");
			String remindInfo = StringHelper.obj2str(request.getParameter("remindInfo"), "");
			String deptNameForCenter = StringHelper.obj2str(request.getParameter("deptNameForCenter"), "");
			
			String userName = StringHelper.obj2str(request.getSession().getAttribute("zbrName"), "");
	
			String policeRemind = CreateSequence.getMaxForSeq("SEQ_T_OA_POLICE_REMIND", 20);
			// ������,��·����,ʩ������,X,Y
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
				logger.error("���뾯����ʾ��Ϣ���� sql:" + sql);
				out.write("false");
			}
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("���뾯����ʾ��Ϣ���� sql:" + sql);
	 	}
		return null;
	}
	
	/**
	 * ȡ�ý�����ʾ��Ϣ<br/>
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
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String strSql = "";
		//Modified by Liuwx 2011-8-26
		//���ڴ���ͼ�л�ȡ������ʾ��Ϣ�������û�����ѡ�����������	
		String areaParams = StringHelper.obj2str(request.getParameter("areaParams"), "");
		String levelParams = StringHelper.obj2str(request.getParameter("levelParams"), "");
		String typeParams = StringHelper.obj2str(request.getParameter("typeParams"), "");
		Map<String, String> paramsMap = FlowUtil.getParams(request, UserSettingDao.cols, false);
		
		UserSettingDao usd = new UserSettingDao();
		String id = null;
		if (paramsMap != null) {
			//���ݿ��Ƿ��Ѿ������û�������
			id = usd.getUserSettingId(paramsMap);
		}
		//Ĭ�ϲ�ѯ���
		strSql = " SELECT REMINDID,TO_CHAR(REMINDTIME,'yyyy\"��\"mm\"��\"dd\"��\" HH24\"ʱ\"mi\"��\"')," +
		"DEPARTMENTID,DEPARTMENTNAME,REMINDINFO,USERNAME FROM V_POLICE_REMIND_DETAIL WHERE TO_CHAR(REMINDTIME,'yyyy-mm-dd') = " +
		"TO_CHAR(SYSDATE,'yyyy-mm-dd')";
		
		if (areaParams == "" && levelParams == "" && typeParams == "") {//ҳ�����ʱ�Ĺ���
			if (id != null && id.length() > 0) {//���ݿ��д����û���Ĭ������ֵʱȡ������Ĭ��ֵ��ѯ
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
						//��ȡ���������ǰ��λ����������ѡ֧�Ӻ����������
						strSql += " AND SUBSTR(DEPARTMENTID,0,4) IN(" + areaInString + ")";
					}
					if (levelInString != null && levelInString.length() > 0) {
						strSql += " AND ROADLEVEL IN(" + levelInString + ")";
					}
					if (typeInString != null && typeInString.length() > 0) {
						strSql += " AND EVENTTYPE IN(" + typeInString + ")";
					}
				}
			}else {//���ݿ��в������û���Ĭ������ֵʱ��ѯ���е�������Ϣ
				strSql +=  " ORDER BY REMINDTIME DESC";
			}
		}else {//������ʱѡ�����������
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
				//��ȡ���������ǰ��λ����������ѡ֧�Ӻ����������
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
//	 		strSql = " select REMINDID,to_char(REMINDTIME,'yyyy\"��\"mm\"��\"dd\"��\" HH24\"ʱ\"mi\"��\"')," +
//	 				"DEPARTMENTID,DEPARTMENTNAME,REMINDINFO,USERNAME from T_OA_POLICE_REMIND where to_char(REMINDTIME,'yyyy-mm-dd') = " +
//	 				"to_char(SYSDATE,'yyyy-mm-dd') order by REMINDTIME desc";
	 		//Modification finished
	 		Object[][] objectInfoLst = DBHandler.getMultiResult(strSql);
			String str = DataToXML.objArrayToXml(objectInfoLst);
			out.write(str);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("��ѯ������ʾ��Ϣ���� sql:" + strSql);
	 		out.close();
	 	}
	 	out.close();
		return null;
	}
	
	
	/**
	 * ���½�����ʾ��Ϣ<br/>
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
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		String REMINDID = StringHelper.obj2str(request.getParameter("remindId"), "");
		String DEPARTMENTID = StringHelper.obj2str(request.getParameter("DEPARTMENTID"), "");
		String DEPARTMENTNAME = StringHelper.obj2str(request.getParameter("DEPARTMENTNAME"), "");
		String REMINDINFO = StringHelper.obj2str(request.getParameter("REMINDINFO"), "");
		PrintWriter out = response.getWriter();
		if (REMINDID == "" || DEPARTMENTID == "" || DEPARTMENTNAME == "" || REMINDINFO == "") {
			System.out.println("����ȱʧ�����½�����ʾ��Ϣʧ�ܡ�");
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
	 			out.write("���½�����ʾ��Ϣ�ɹ���");
	 		}else{
	 			out.write("���½�����ʾ��Ϣʧ�ܡ�");
	 		}
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("���¾�����ʾ��Ϣ���� sql:" + strSql);
	 		out.close();
	 	}
	 	out.close();
		return null;
	}
	
	/**
	 * ͨ������IDȡ�ý�����ʾ��Ϣ<br/>
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
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		String remindId = StringHelper.obj2str(request.getParameter("remindId"), "");
		if ( remindId == "" ) {
			System.out.println("����ȱʧ����ȡ������ʾ��Ϣʧ�ܡ�");
			return null;
		}
		PrintWriter out = response.getWriter();
		String strSql = "";
	 	try {
	 		strSql = " select REMINDID,to_char(REMINDTIME,'yyyy\"��\"mm\"��\"dd\"��\" HH24\"ʱ\"mi\"��\"')," +
	 				"DEPARTMENTID,DEPARTMENTNAME,REMINDINFO,USERNAME FROM T_OA_POLICE_REMIND WHERE REMINDID = " 
	 				+ remindId +" order by REMINDTIME desc";
	 		Object[] objectInfoLst = DBHandler.getLineResult(strSql);
			String str = DataToXML.objArrayToXml(objectInfoLst);
			System.out.println("PoliceRemindXML:"+str);
			out.write(str);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("��ѯ������ʾ��Ϣ���� sql:" + strSql);
	 		out.close();
	 	}
	 	out.close();
		return null;
	}
	
	private PoliceRemindCore core = new PoliceRemindCore();
	
	/**
	 * ��ȡ������Ϣ��
	 * ������Ϣ��ʾ��������½�����ʾ
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
	 * ��ȡ��ϸ��Ϣ��
	 * ��ϸ��Ϣ��ʾ���þ������������ʾƴ����Ϣ�ͺ����ĸ�����Ϣ��
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
	 * ��ȡ�˹�������Ϣ��
	 * ������Ϣ��ʾ���þ������������ʾ��Ϣ�������ĸ�����Ϣ�Լ����еĸ�����Ϣ
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
	 *  ��ȡĳ������Ľ�����ʾ��Ϣ
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
	 * ����������ʾ��Ϣ
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
	 * ����������ʾ��Ϣ
	 * �����δ��������û���޸���ʾ���ݣ���ֻ����ԭ������ʾ�ķ���״̬��
	 * ��������һ��������ʾ��Ϣ��ͬʱ����ԭ������ʾ�ķ���״̬��
	 * Ȼ��ǩ�ո���Ϣ������
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
     * ������Ϣ����״̬
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
            logger.error("ȱ�ٱ�Ҫ�Ĳ����������Ƿ��Ѿ���ȷ�������������");
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
