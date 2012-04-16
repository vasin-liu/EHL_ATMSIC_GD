/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.core.CrowdRemindCore;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.CrowdRemindDao;
import com.ehl.dispatch.cdispatch.dao.RoadBuildDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.cdispatch.util.LcbPtMisUtil;
import com.ehl.dispatch.common.AlarmInfoJoin;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.common.PromptLog;
import com.ehl.dynamicinfo.policeRemind.core.PoliceRemindCore;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.sm.pcs.DepartmentManage;
import com.ehl.tira.util.XML;

/**
 * ��·���������
 * @author wkz
 * @date 2009-1-16
 *
 */
public class RoadBuildAction extends Controller{
	
	private Logger logger = Logger.getLogger(RoadBuildAction.class);
	/**
	 * �༭��·��Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyRoadBuildInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request.getParameter("insrtOrUpdt"), 0);
		String ROADID = StringHelper.obj2str(request.getParameter("ROADID"),"");
		String EVENTSOURCE = StringHelper.obj2str(request.getParameter("EVENTSOURCE"),"");
		String EVENTTYPE = StringHelper.obj2str(request.getParameter("EVENTTYPE"),"");
		String ALARMUNIT = StringHelper.obj2str(request.getParameter("ALARMUNIT"),"");
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		String TITLE = StringHelper.obj2str(request.getParameter("TITLE"),"");
		String ALARMREGIONID = StringHelper.obj2str(request.getParameter("ALARMREGIONID"),"");
		String ALARMREGION = StringHelper.obj2str(request.getParameter("ALARMREGION"),"");
		String ROADNAME = StringHelper.obj2str(request.getParameter("ROADNAME"),"");
		String KMVALUE = StringHelper.obj2str(request.getParameter("KMVALUE"),"");
		String MVALUE = StringHelper.obj2str(request.getParameter("MVALUE"),"");
		String EndKMVALUE = StringHelper.obj2str(request.getParameter("EndKMVALUE"), "");
		String EndMVALUE = StringHelper.obj2str(request.getParameter("EndMVALUE"), "");		String CaseHappenTime = StringHelper.obj2str(request.getParameter("CaseHappenTime"),"");
		String CaseEndTime = StringHelper.obj2str(request.getParameter("CaseEndTime"),"");
		String REPORTUNIT = StringHelper.obj2str(request.getParameter("REPORTUNIT"),"");
		String REPORTPERSON = StringHelper.obj2str(request.getParameter("REPORTPERSON"),"");
		String REPORTTIME = StringHelper.obj2str(request.getParameter("REPORTTIME"),"");
		String PLAN = StringHelper.obj2str(request.getParameter("PLAN"),"");
		String ISALLCLOSE = StringHelper.obj2str(request.getParameter("ISALLCLOSE"),"");
		String ROLANENUM = StringHelper.obj2str(request.getParameter("ROLANENUM"),"");
		String ISHAVEPLAN = StringHelper.obj2str(request.getParameter("ISHAVEPLAN"),"");
		String ROADDIRECTION = StringHelper.obj2str(request.getParameter("RADIOTYPE"),"");
		HashMap hm = new HashMap();
		hm.put("ALARMID", ALARMID);
		hm.put("ROADID", ROADID);
		hm.put("EVENTSOURCE", EVENTSOURCE);
		hm.put("EVENTTYPE", EVENTTYPE);
		hm.put("ALARMUNIT", ALARMUNIT);
		hm.put("TITLE", TITLE);
		hm.put("ALARMREGIONID", ALARMREGIONID);
		hm.put("ALARMREGION", ALARMREGION);
		hm.put("ROADNAME", ROADNAME);
		hm.put("KMVALUE", KMVALUE);
		hm.put("MVALUE", MVALUE);
		hm.put("EndKMVALUE", EndKMVALUE);
		hm.put("EndMVALUE", EndMVALUE);
		hm.put("CaseHappenTime", CaseHappenTime);
		hm.put("CaseEndTime", CaseEndTime);
		hm.put("REPORTUNIT", REPORTUNIT);
		hm.put("REPORTPERSON", REPORTPERSON);
		hm.put("REPORTTIME", REPORTTIME);
		hm.put("PLAN", PLAN);
		hm.put("ISALLCLOSE", ISALLCLOSE);
		hm.put("ROLANENUM", ROLANENUM);
		hm.put("ISHAVEPLAN", ISHAVEPLAN);
		hm.put("ROADDIRECTION", ROADDIRECTION);
		//Modify by xiayx 2012-3-7
		//��ӵ�ǰ·��-���ݲ���
		String recentRoadState = request.getParameter("recentRoadState");
		hm.put("recentRoadState", recentRoadState);
		//Modification finished
		String[] XYvalue = LcbPtMisUtil.getXYvalue(ROADID, KMVALUE, MVALUE);
		if (XYvalue != null) {
			hm.put("Xvalue", XYvalue[0]);
			hm.put("Yvalue", XYvalue[1]);
		} else {
			hm.put("Xvalue", "");
			hm.put("Yvalue", "");
		}
		//Modify by xiayx 2012-3-8
		//�����ʱ��Ϊ��ǰϵͳʱ��
		String currentTime = Constant.getCurrentTime(false).substring(0,16);
		//Modification finished
		//Modified by Liuwx 2011-07-20
		//��Ҫ��þ��������ܷ�λ��������ʾ��·�涯̬����Ϣ��ʾ�С�
		RoadBuildDao rbd = new RoadBuildDao();
		boolean flag = false;
		if(insertOrUpdate==0) {
			//Modify by xiayx 2012-3-7
			//��ӵ�ǰ·��-�������ŵĴ�����Dao������Action�����ҽ��ʱ��Ĭ��Ϊ��ǰʱ��
			ALARMID = ALARMUNIT.substring(0,6)+StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
			hm.put("ALARMID", ALARMID);
			hm.put("REPORTTIME", currentTime);
			//Modification finished
			//Modified by Liuwx 2012��3��13��11:58:27
            if(rbd.isExist(hm)){
            	out.write("Exist");
            }else {
            	flag = rbd.addRoadBuildInfo(hm);
            	out.write("����ʩ��ռ����Ϣ"+(flag?"�ɹ�":"ʧ��")+"��");
            }
            //Modification finished
		} else {
			flag = rbd.editRoadBuildInfo(hm);
			out.write("�޸�ʩ��ռ����Ϣ"+(flag?"�ɹ�":"ʧ��")+"��");
		}
		
		if(flag){
			//Modify by xiayx 2012-3-5
			//��ӽ���·��-�������·������
			crcore.insert(CrowdRemindDao.formCrowdRemind(ALARMID,currentTime, ALARMREGION, REPORTPERSON, recentRoadState,recentRoadState));
			CaseHappenTime = AlarmInfoJoin.simplifyTime(currentTime, CaseHappenTime);
			CaseEndTime = AlarmInfoJoin.simplifyTime(currentTime, CaseEndTime);
			String remindInfo = AlarmInfoJoin.getStartBulid(CaseHappenTime, ALARMREGION, ROADID, ROADNAME, KMVALUE, MVALUE, EndKMVALUE, EndMVALUE, ROADDIRECTION,CaseEndTime, ROLANENUM,recentRoadState, PLAN);
			//Ĭ�Ϸ�������������������
			prcore.insert(PoliceRemindDao.formPoliceRemind(ALARMID, currentTime, REPORTUNIT, ALARMREGION, REPORTPERSON, "1", "1,2", remindInfo, "2"));
			//Modification finished
		}
		out.close();
		return null;
	} 
	/**
	 * ɾ����·��Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteRoadBuildInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		RoadBuildDao rbd = new RoadBuildDao();
		boolean flag = rbd.delteRoadBuildInfo(ALARMID);
		if(flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
		out.close();
		return null;
	}
	
	/**
	 * ���ռ��
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateRoadBuildInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		
		RoadBuildDao rbd = new RoadBuildDao();
		boolean flag = rbd.updateRoadBuildInfo(ALARMID);
		if(flag) {
			//Modify by Xiayx 2012-2-20
			//���ӵ�½���������ʾ
			String remindInfo = "��·ʩ���Ѿ���ɡ�";
			HttpSession session = request.getSession();
			String jgmc = (String)session.getAttribute(Constant.JGMC_VAR);
			String pname = (String)session.getAttribute(Constant.ZBRXM_VAR);
			String rtime = Constant.getCurrentTime(false).substring(0,16);
			flag = crcore.insert(CrowdRemindDao.formCrowdRemind(ALARMID,rtime, jgmc, pname, remindInfo,remindInfo)) != null;
			String ptype = StringHelper.obj2str(request.getParameter("publishtype"),prcore.getRelievePublishType(ALARMID));
			AlarmInfoJoin.updateOverRemind(ALARMID,ptype);
			//Modification finished
			out.write("success");
		}else {
			out.write("fail");
		}
		out.close();
		return null;
	}
	
	/**
	 * @author Liuwx 2011-07-03
	 * ��ѯ��·��Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetRoadBuildInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		String RPDCODE = StringHelper.obj2str(request.getParameter("RPDCODE"),"");
		RoadBuildDao rbd = new RoadBuildDao();
		AccDeptDao adDao = new AccDeptDao();
		
		StringBuffer result = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		Object[][] disData = null;
		String disXml = null;
		String res = null;
		Object[] obj = null;
		Object[][] rpdId = null;
		String id = null;
		obj = rbd.getRoadBuildInfo(ALARMID);
		if(!RPDCODE.equals("440000000000")) {
			RPDCODE = "440000000000";
		}
		rpdId = adDao.getByAidRpdcodeAdid("id", ALARMID, RPDCODE, null);
		if (rpdId != null) {
			id = StringHelper.obj2str(rpdId[0][0],"");
		}
		
//		res = DataToXML.objArrayToXml(obj);
		if (id != null && id != "") {
			disData = adDao.getAccDept(id, 2);
		}
		result.append("<rfXML>\n");
		result.append("<RFWin>\n");
		result.append("<rb>\n");
		if (obj != null) {
			res = XML.encapContent("row","col", obj);
			result.append(res);
		}
		result.append("</rb>\n");
		if (disData != null) {
			disXml = XML.encapContent("dis", "row","col", disData);
			result.append(disXml);
		}
		//Modify by xiayx 2012-3-5
		//��ӽ���·��-��ȡ·����Ϣ
		String recentRoadState = crcore.getStringByAlarmId(ALARMID);
		if(recentRoadState != null){
			result.append(recentRoadState);
		}
		//Modification finished
		result.append("</RFWin>\n");
		result.append("</rfXML>\n");
		logger.info("xml:\n"+result);
		out.write(result.toString());
		out.close();
		return null;
	}	
	
	
	public ActionForward doGetAddName(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String roadid = StringHelper.obj2str(request.getParameter("roadid"),"");
		String sql = "select begin,end  from T_OA_DICTDLFX d where d.dlmc='"+roadid+"'";
		Object[] addname = null;
		try {
			addname = DBHandler.getLineResult(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ʩ��ռ����ȡ��·�����쳣");
		}
		String addName = "";
		if(addname != null) {
			addName = addname[0]+","+addname[1];
		}
		out.write(addName);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward doShowExcel(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String searchSql = request.getParameter("searchSql");
		Object[][] tabData = DBHandler.getMultiResult(searchSql);
		Object[][] data = null;
		if(tabData != null) {
			data = new Object[tabData.length][8];
			for(int i=0; i<tabData.length; i++) {
				for(int j=0; j<8; j++) {
					data[i][j] = tabData[i][j];
				}
				if("0".equals(tabData[i][4])) {
					data[i][4] = tabData[i][8] +"->"+tabData[i][9];
				} else {
					data[i][4] = tabData[i][9] +"->"+tabData[i][8];
				}
			}
			
		} else {
			data = new Object[1][8];
		}
		String fileName = "ʩ��ռ����Ϣ";
		String titleName = "ʩ��ռ����Ϣ�б�";
		String tabHeader = "ʩ��ռ����Ϣ���,¼�뵥λ,��·����,·������,����,ʩ����ʼʱ��,ʩ������ʱ��,״̬";
		String sheetName = "ʩ��ռ����Ϣ�б�";
		try {
			CommonUtility comUtility = new CommonUtility();
			comUtility.wirteToExcel(response, tabHeader, fileName, sheetName, data, titleName);
			//CrowdManageAction.showExcel(fileName, sheetName, titleName, tabHeader, data, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	/**
	 * 
		* doSearchRoadBuild  
	  
		* TODO(��ȡʩ��ռ����Ϣ)     
	  
		* @param  @return    �趨�ļ�
	  
		* @return String    DOM����   
	  
		* @Exception �쳣����   
	  
		* @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public ActionForward doSearchRoadBuild(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String rpdcode = DepartmentManage.getDeptInfo(request, "0");;
		String state = request.getParameter("state");
		state = state == null ? "1" : state;
		String mstate = request.getParameter("mstate");
		RoadBuildDao rbd = new RoadBuildDao();
		String xml = rbd.searchRoadBuild(rpdcode, state, mstate);
		System.out.println("sgzdXML:\n"+xml);
		out.write(xml);
		return null;
	}
	
	public ActionForward doSearchCounts(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		AccDeptCore core = new AccDeptCore();
		RoadBuildDao rbd = new RoadBuildDao();
		String[] paramsIn = core.dao.cnames;
		Map<String, String> pimap = FlowUtil.getParams(request, paramsIn, true);
		pimap.put("rpdcode", DepartmentManage.getDeptInfo(request, "0"));
		String[] data = core.searchCount(pimap);//���������º�ɾ��ʩ��ռ����Ϣ����
		
		ArrayList<String> results = rbd.getRoadBuildCount(pimap);//ʩ��ռ����ʱ����
		
		ArrayList<String> arrayList = new ArrayList<String>();//�����
		
		if (data != null) {
			for (String element : data) {
				arrayList.add(element);
			}
		}
		if (results != null) {
			for (String result : results) {
				arrayList.add(result);
			}
		}
		String xml = DataToXML.objArrayToXml(arrayList.toArray());
		PromptLog.debug(request, "ʩ��ռ��",
				new String[]{"����ʩ��ռ����Ϣ","����ʩ��ռ����Ϣ","���ʩ��ռ����Ϣ","ʩ��ռ����Ϣ����"},
				arrayList.toArray(),xml);
		out.write(xml);
		return null;
	}
	
	public ActionForward doUpdateZodbz(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String aid = request.getParameter("aid");
		String zodbz = request.getParameter("zodbz");
		boolean isSuccess = new RoadBuildDao().updateZodbz(aid, zodbz);
		out.write(String.valueOf(isSuccess));
		return null;
	}
	
	private PoliceRemindCore prcore = new PoliceRemindCore();
	private CrowdRemindCore crcore = new CrowdRemindCore();
	
}

//2011-08-14���£���ά��