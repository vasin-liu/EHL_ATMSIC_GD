/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Constants;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.output.SaveAsExcelCore;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.cdispatch.core.CrowdRemindCore;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.dispatch.cdispatch.dao.CrowdManageDao;
import com.ehl.dispatch.cdispatch.dao.CrowdRemindDao;
import com.ehl.dispatch.cdispatch.dao.TrafficNewsFeedsDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.dispatch.cdispatch.util.CutInfoAdd;
import com.ehl.dispatch.cdispatch.util.LcbPtMisUtil;
import com.ehl.dispatch.common.AlarmInfoJoin;
import com.ehl.dispatch.common.DispatchUtil;
import com.ehl.dispatch.common.PromptLog;
import com.ehl.dynamicinfo.policeRemind.core.PoliceRemindCore;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.sm.pcs.DepartmentManage;
import com.ehl.tira.util.XML;

/**
 * 拥堵控制
 * 
 * @author wkz
 * @date 2009-1-16
 * 
 */
public class CrowdManageAction extends Controller {
	
	private Logger logger = Logger.getLogger(CrowdManageAction.class);
	
	
	private CrowdManageDao cmdao = new CrowdManageDao();
	/**
	 * 编辑道路信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request.getParameter("insrtOrUpdt"), 0);
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),"");
		String ROADID = StringHelper.obj2str(request.getParameter("ROADID"), "");
		String EVENTSOURCE = StringHelper.obj2str(request.getParameter("EVENTSOURCE"), "");
		String EVENTTYPE = StringHelper.obj2str(request.getParameter("EVENTTYPE"), "");
		String ALARMUNIT = StringHelper.obj2str(request.getParameter("ALARMUNIT"), "");
		String TITLE = StringHelper.obj2str(request.getParameter("TITLE"), "");
		String ALARMREGIONID = StringHelper.obj2str(request.getParameter("ALARMREGIONID"), "");
		String ALARMREGION = StringHelper.obj2str(request.getParameter("ALARMREGION"), "");
		String ROADNAME = StringHelper.obj2str(request.getParameter("ROADNAME"), "");
		String KMVALUE = StringHelper.obj2str(request.getParameter("KMVALUE"),"");
		String MVALUE = StringHelper.obj2str(request.getParameter("MVALUE"),"");
		String EndKMVALUE = StringHelper.obj2str(request.getParameter("EndKMVALUE"), "");
		String EndMVALUE = StringHelper.obj2str(request.getParameter("EndMVALUE"), "");
		String CaseHappenTime = StringHelper.obj2str(request.getParameter("CaseHappenTime"), "");
		String CaseEndTime = StringHelper.obj2str(request.getParameter("CaseEndTime"), "");
		String REPORTUNIT = StringHelper.obj2str(request.getParameter("REPORTUNIT"), "");
		String REPORTPERSON = StringHelper.obj2str(request.getParameter("REPORTPERSON"), "");
		String REPORTTIME = StringHelper.obj2str(request.getParameter("REPORTTIME"), "");
		String CONGESTIONTYPE = StringHelper.obj2str(request.getParameter("CONGESTIONTYPE"), "");
		String CONGESTIONREASON = StringHelper.obj2str(request.getParameter("CONGESTIONREASON"), "");
		String ROADDIRECTION = StringHelper.obj2str(request.getParameter("RADIOTYPE"), "");
		String remindInfo = StringHelper.obj2str(request.getParameter("remindInfo"), "");
		String daduiid = StringHelper.obj2str(request.getParameter("daduiid"), "");
		String crowdTypeFlg = StringHelper.obj2str(request.getParameter("crowdTypeFlg"), "");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String acceptDept = StringHelper.obj2str(request.getParameter("acceptDept"), "");//总队指定拥堵接收单位
		//Modified by Liuwx 2011-08-08
		String ReceiveTime = StringHelper.obj2str(request.getParameter("ReceiveTime"), "");
		//Modification finished
		//Modified by Xiayx 2011-08-08
		//添加审核人和附件
		String apname = StringHelper.obj2str(request.getParameter("apname"), "");
		String apath = StringHelper.obj2str(request.getParameter("apath"), "");
		//Modification finished
		
		String baosongDepartMentId = ALARMUNIT;
		
		String baoSongFlg = "0";
		// 机构编号2位总队报送的拥堵信息
		if (jgbh.length() == 2) {
			baoSongFlg = "1";
			//Modified by Liuwx 2011-8-8
			//取消总队下发到大队
//			ALARMUNIT = daduiid;
			//Modification finished
		}
		
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
		hm.put("CONGESTIONTYPE", CONGESTIONTYPE);
		hm.put("CONGESTIONREASON", CONGESTIONREASON);
		hm.put("ROADDIRECTION", ROADDIRECTION);
		hm.put("EVENTSTATE", "570001");// 拥堵中
		hm.put("BLID", "");//爆料ID
		hm.put("remindInfo", remindInfo);// 交警提示
		hm.put("baoSongFlg", baoSongFlg);// 报送标志
		hm.put("baosongDepartMentId", baosongDepartMentId);// 报送标志
		hm.put("crowdTypeFlg", crowdTypeFlg);// 中断标志
		hm.put("acceptDept", acceptDept);
		//Modified by Liuwx 2011-8-8
		hm.put("ReceiveTime", ReceiveTime);
		//Modification finished
		//Modified by Xiayx 2011-8-8
		hm.put("apname", apname);
		hm.put("apath", apath);
		//Modification finished
		//Modify by Xiayx 2012-3-1
		//添加拥堵发布-获取参数
		String gzcs = request.getParameter("gzcs");
		String policeRemind = request.getParameter("policeremind");
		hm.put("gzcs", gzcs);
		hm.put("remind", policeRemind);
		//Modification finished
		
		String[] XYvalue = LcbPtMisUtil.getXYvalue(ROADID, KMVALUE, MVALUE);
		if(XYvalue != null) {
			hm.put("Xvalue", XYvalue[0]);
			hm.put("Yvalue", XYvalue[1]);
		} else {
			hm.put("Xvalue", "");
			hm.put("Yvalue", "");
		}
		
		String currentTime = Constant.getCurrentTime(false).substring(0,16);
		String result = "拥堵信息";
		boolean flag = false;
		if (insertOrUpdate == 0) {
			ALARMID = ALARMUNIT.substring(0, 6)+ StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
			hm.put("ALARMID", ALARMID);
			hm.put("REPORTTIME", currentTime);
			if(!(cmdao.isExist(hm))){
                flag = cmdao.addCrowdInfo(hm);
            }else{
                out.write("Exist");
            }
		} else {
			hm.put("currentUserName", request.getSession().getAttribute(Constant.ZBRXM_VAR));
			flag = cmdao.editCrowdInfo(hm);
		}
		if(flag){
			crcore.insert(CrowdRemindDao.formCrowdRemind(ALARMID, currentTime, ALARMREGION, REPORTPERSON,remindInfo,gzcs));
			try {
				String ptype = prcore.getModifyPublishType(currentTime.substring(11));
				prcore.insert(PoliceRemindDao.formPoliceRemind(ALARMID, currentTime, REPORTUNIT, ALARMREGION, REPORTPERSON, "1", ptype, policeRemind, "2"));
			} catch (Exception e) {
				logger.error("获取发布策略异常！",e);
			}
		}
		result += (insertOrUpdate==0?"新增":"更新") + (flag?"成功":"失败");
		result += "！";
		out.write(result);
		out.flush();
		out.close();
		return null;
	}

	private void updateForFw(String ROADID, String KMVALUE, String EndKMVALUE,
			String CaseHappenTime, String REPORTUNIT, String ROADDIRECTION,
			String daduiid, String alarmId) {
		boolean flag;
		//Modify by Xiayx 2011-11-2
		//取消方伟拥堵信息的更新
		String sql = "select gbdm,dlmc,begin,end from t_oa_dictdlfx where dlmc='"+ROADID+"'";
		Object[] roadInfo = DBHandler.getLineResult(sql);
		if(roadInfo != null){
			String dlmcFw = getFwDlbz(ROADID, String.valueOf(roadInfo[0]));
			String beginFw = String.valueOf(roadInfo[2]);
			String endFw = String.valueOf(roadInfo[3]);
			String dlfxFw = getFwDlfx(ROADDIRECTION, beginFw, endFw);
			String gxdd = daduiid.equals("")?REPORTUNIT:daduiid;
			sql = "select nvl(othername,jgmc) from t_sys_department where jgmc='" + gxdd + "'";
			gxdd = String.valueOf(DBHandler.getSingleResult(sql));
			String crowdId = addCrowdForFw(dlmcFw, KMVALUE, EndKMVALUE, CaseHappenTime, dlfxFw, gxdd);
			if (crowdId != null && crowdId.indexOf("-") == -1) {//添加方伟拥堵信息成功
				sql = "update t_attemper_congestion set fwid='"+crowdId+"' where alarmid='"+alarmId+"'";
				flag = DBHandler.execute(sql);
			}else{
				logger.error("更新方伟拥堵信息失败");
			}
		}
		//Modification finished
	}

	/**
	 * <pre>
	 * 操作方伟公司拥堵信息
	 * 调用webservice接口，添加或者删除方伟公司拥堵信息
	 * </pre>
	 * @param mname 方法名称
	 * @param crowdInfo 拥堵信息
	 * @return 操作结果
	 * @throws Exception
	 */
	public String operateFwCrowd(String mname, Map<String, String> crowdInfo ){
		String result = null;
		/*if(mname != null && crowdInfo != null && crowdInfo.size() > 0){
			Service service = new Service();
			Call call = null;
			try {
				call = (Call)service.createCall();
			} catch (ServiceException e) {
				System.err.println("service创建call异常！");
				e.printStackTrace();
			}
			if(call != null){
				String svurl = "http://10.40.30.31/blb/WS_BLB_Traffic_BizCtrlImpl.asmx?wsdl";//内外IP
				//svurl = "http://113.108.187.3/blb/WS_BLB_Traffic_BizCtrlImpl.asmx?wsdl";//外网IP
				String namespace = "http://tempuri.org/";
				String uri = namespace + mname;
				call.setTargetEndpointAddress(svurl);
				call.setUseSOAPAction(true);
				call.setSOAPActionURI(uri);
				call.setOperationName(new QName(namespace, mname));
				crowdInfo.put("compaySign", "fangwei");//方伟公司标志，是compaySign不是companySign
				Object[] colValues = new Object[crowdInfo.size()];
				int i = 0;
				for(Map.Entry<String, String> colInfo : crowdInfo.entrySet()){
					call.addParameter(new QName(namespace,colInfo.getKey()), XMLType.XSD_STRING, ParameterMode.IN);
					colValues[i++] = colInfo.getValue();
				}
				call.setReturnType(XMLType.XSD_STRING);
				try {
					result = String.valueOf(call.invoke(colValues));
				} catch (RemoteException e) {
					System.err.println("service调用"+mname+"异常！");
					e.printStackTrace();
				}
			}
		}*/
		return result;
	}
	
	/**
	 * 添加方伟拥堵信息
	 * @param dlmc 道路名称或者编号，新路传编号旧路传名称。京港澳高速或者G4
	 * @param qslc 起始里程。100
	 * @param jslc 结束里程。110
	 * @param sj 拥堵开始时间。2011-07-18 12:12:12
	 * @param fx 拥堵方向。北京
	 * @param gxdd 管辖大队。广州高速公路一大队
	 * @return 结果。成功返回拥堵主键编号，错误返回错误标志。<br>
	 * 错误标志值和含义说明：-1:验证错误，-2:找不到对应路名，-3:方向不匹配，-5:其他错误；
	 */
	public String addCrowdForFw(String dlmc, String qslc, String jslc, String sj,String fx, String gxdd){
		String result = null;
		if(dlmc != null && qslc != null && jslc != null && sj!= null && fx!=null && gxdd!=null){
			String mname = "AddTraffic";
			Map<String,String> crowdInfo = new HashMap<String, String>();
			crowdInfo.put("dlmc", dlmc);
			crowdInfo.put("qslc", qslc);
			crowdInfo.put("jslc", jslc);
			crowdInfo.put("sj", sj);
			crowdInfo.put("fx", fx);
			crowdInfo.put("ghdd", gxdd);//注意是ghdd不是gxdd
			result = operateFwCrowd(mname, crowdInfo);
		}
		return result;
	}
	
	/**
	 * 删除方伟公司拥堵信息
	 * @param id 拥堵主键编号
	 * @return 结果。结果标志值和含义说明：1 成功、0 记录不存在、-1 验证错误、5 其他错误
	 */
	public String deleteCrowdForFw(String id){
		String result = null;
		if(id != null){
			String mname = "DelTraffic";
			Map<String,String> crowdInfo = new HashMap<String, String>();
			crowdInfo.put("id", id);
			result = operateFwCrowd(mname, crowdInfo);
		}
		return result;
	}

	/**
	 * <pre>
	 * 获取方伟道路标志
	 * 道路标志
	 * 1.道路名称
	 * 2.道路国标代码
	 * </pre>
	 * @param dlmc 方伟道路名称
	 * @param gbdm 方伟
	 * @return
	 */
	public String getFwDlbz(String dlmc, String gbdm){
		if(gbdm != null && !gbdm.equals("z_old")){//如果不是旧路
			dlmc = gbdm;
		}
		return dlmc;
	}
	
	/**
	 * 获取方伟道路方向
	 * @param dlfx 本系统道路方向，值为：0或1或2。
	 * @param begin 方伟道路起点
	 * @param end 方伟道路终点
	 * @return 方伟道路方向
	 */
	public String getFwDlfx(String dlfx, String begin, String end) {
		if (dlfx != null) {
			if (dlfx.equals("0")) {
				dlfx = begin;
			} else if (dlfx.equals("1")) {
				dlfx = end;
			} else if (dlfx.equals("2")) {
				dlfx = "双方向";
			}
		}
		return dlfx;
	}
	
	/**
	 * 解除拥堵信息<br/>
	 * 拥堵信息的解除
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String alarmid = StringHelper.obj2str(request.getParameter("alarmid"),"");
		boolean flag = cmdao.updateCrowdInfo(alarmid);
		//Modify by Xiayx 2012-2-20
		//添加结束拥堵交警提示和结束交警提示
		if(flag){
			String rtime = StringHelper.obj2str(request.getParameter("rtime"),Constant.getCurrentTime(false).substring(0,16));
			String dmsg = "交通拥堵已清除，路面恢复正常交通。";
			String crm = StringHelper.obj2str(request.getParameter("crowdremind.remindinfo"),dmsg);
			HttpSession session = request.getSession();
			String jgid = (String)session.getAttribute(Constant.JGID_VAR);
			String jgmc = (String)session.getAttribute(Constant.JGMC_VAR);
			String pname = (String)session.getAttribute(Constant.ZBRXM_VAR);
			String jglx = (String)session.getAttribute(Constant.JGLX_VAR);
			Map<String, String> object = CrowdRemindDao.formCrowdRemind( alarmid, rtime, jgmc, pname, crm, crm);
			flag = crcore.insert(object) != null;
			
			String prm = request.getParameter("policeremind.remindinfo");
			String ptype = request.getParameter("publishtype");
			if(ptype == null){
				ptype = prcore.getRelievePublishType(alarmid);
			}
			String state = jglx.equals("0") ? null : "1";
			flag = prcore.insert(PoliceRemindDao.formPoliceRemind(alarmid, rtime, jgid, jgmc, pname,state, ptype, prm, "2")) != null;
			//如果是总队用户解除了拥堵信息，则自动签收，不再提醒
			if(jglx.equals("0")){
				adcore.signIn(pname, rtime, "aid='"+alarmid+"' and rpdcode='"+jgid+"' and atype='2'");
			}
		}
		//Modification finished
		out.write(XML.getXML(String.valueOf(flag)));
		out.close();
		return null;
	}

	/**
	 * 查询道路拥堵信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer xmlData = new StringBuffer();
		try{
			String alarmid = StringHelper.obj2str(request.getParameter("alarmid"),"");
			CrowdManageDao cmd = new CrowdManageDao();
			Object[] obj0 = cmd.getCrowdInfo(alarmid);
			xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rfXML>\n");
			xmlData.append("<RFWin>\n");
			if (obj0 != null) {
				xmlData.append("<row>\n");
				for (int i = 0; i < obj0.length; i++) {
					if (obj0[i] == null || "".equals(obj0[i].toString().trim())) {
						xmlData.append("<col>　</col>");
					} else {
						xmlData.append("<col>" + StringHelper.obj2str(obj0[i]) + "</col>");
					}
				}
				xmlData.append("\n</row>\n");
			}
			//Modify by xiayx 2012-3-14
			//获取拥堵交警提示和交警提示
			String xml = crcore.getStringByAlarmId(alarmid);
			if(xml != null){
				xmlData.append(xml);
			}
			xml = prcore.getStringByAlarmId(alarmid);
			if(xml != null){
				xmlData.append("\n"+xml);
			}
			String jgid = (String)request.getSession().getAttribute(Constant.JGID_VAR);
			//获取总队的处理状况
			xml = adcore.getCurrent(Constant.getRootParent(jgid), alarmid, "2");
			if(xml != null){
				xmlData.append("\n"+xml);
			}
			//Modification finished
			
			if(jgid != "440000000000") {
				jgid = "440000000000";
			}
			if (jgid != null && jgid.substring(2, 6).equals("0000")) {
				Object[][] data = adcore.dao.getAccDept(alarmid, jgid, 2);
				if(data != null){
					String adstate = adcore.getDisDept(data[0][0]+"",new String[]{"1","2"});
					if(adstate != null){
						xmlData.append("\n"+adstate);
					}
				}
			}
			
		}catch (Exception e) {
			logger.error("获取拥堵信息异常！",e);
		}
		xmlData.append("\n</RFWin>\n");
		xmlData.append("</rfXML>\n");
		logger.info("getCrowdInfo XML:\n" + xmlData);
		out.write(String.valueOf(xmlData));
		out.close();
		return null;
	}

	/**
	 * 解除拥堵信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doCancelCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"),
				"");
		CrowdManageDao cmd = new CrowdManageDao();
		boolean flag = cmd.cancelCrowdInfo(ALARMID);
		if (flag) {
			out.write("success");
		} else {
			out.write("fail");
		}
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
		String sql = "select begin,end  from T_OA_DICTDLFX d, LCB_PT_MIS l where d.dlmc=l.dlmc and d.dlmc='"+roadid+"'";
		Object[] addname = null;
		try {
			addname = DBHandler.getLineResult(sql);
		} catch (Exception e) {
			logger.error("拥堵信息获取道路方向异常:"+e.getMessage());
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
		String fileName = "拥堵信息";
		String titleName = "拥堵信息列表";
		String tabHeader = "标题,发生时间,结束时间,道路名称,路段名称,起始里程千米值,起始里程百米值,终止里程千米值,终止里程百米值,道路方向,事件状态,填报单位编号,填报单位名称,填报人,填报时间,大队审核人,支队审核人";
		String sheetName = "拥堵信息列表";
		String searchSql = request.getParameter("searchSql");
		try {
			Object[][] data = DBHandler.getMultiResult(searchSql);
			if(data == null){
				data = new Object[1][tabHeader.split(",").length];
			}
			showExcel(fileName, sheetName, titleName, tabHeader, data, response);
		} catch (Exception e) {
			logger.error("拥堵信息导出Excel时异常:"+e.getMessage());
		}
		return null;
	}
	
	public static void showExcel(String fileName,String sheetName,String titleName, String tabHeader,Object[][] data,HttpServletResponse response){
		try {
			SaveAsExcelCore saveAsExcelCore = new SaveAsExcelCore();
			saveAsExcelCore.setTabHeader(tabHeader);
			fileName = new String(fileName.getBytes(), "ISO8859_1");
			
			CommonUtility comUtl = new CommonUtility();
			comUtl.wirteToExcel(response, tabHeader, fileName, sheetName, data, titleName);
			//saveAsExcelCore.setFileName(fileName);
			//saveAsExcelCore.setTitleName(titleName);
			//saveAsExcelCore.wirteToExcel(sheetName, titleName, tabHeader, data, response);
		} catch(Exception e) {
			
		}
	}
	/**
	 * 获取拥堵提醒信息件数<br/>
	 * 拥堵提醒信息件数的取得
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPromptCrowdCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String sql = null;
		Object[] resoults = new Object[7];//Modified by Liuwx add out to date crowd info.
		String deptcode = ""; //部门编码
		String deptname = ""; //部门名称
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			deptname = (String) prop.get("BRANCHNAME");
		}
		try {
			// 取得拥堵信息为拥堵中且为本单位报送的拥堵信息的且为报送20分钟后任然没有解除的拥堵信息的件数
			sql = "select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +
					" where taa.EVENTTYPE = '001002' " +
					" and taa.EVENTSTATE = '570001' " +
					" and tcc.UPDATE_DATE+20/24/60 < sysdate " +
					" and taa.alarmid = tcc.ALARMID and taa.ALARMUNIT = '" + deptcode + "'";
			Object obj = DBHandler.getSingleResult(sql);
			resoults[0] = obj; 
//			jgid机构编号、atype警情类型、state处理状态、mstate信息状态
			AccDeptCore adCore = new AccDeptCore();
			Map<String, String> adMap = new HashMap<String, String>();
			adMap.put("rpdcode", deptcode);
			adMap.put("atype", "2");
			adMap.put("state", "1");
			String[]crowdState = adCore.searchCount(adMap);
			for (int i = 0; i < crowdState.length; i++) {
				resoults[i+1] = crowdState[i];
			}
//			resoults[1] = getTroubCrowdCount(deptcode);
			TrafficNewsFeedsDao tcd = new TrafficNewsFeedsDao();
			resoults[4] = tcd.getNewFeedsCount(deptcode,deptname,"0");
			resoults[5] = tcd.getNewFeedsCount(deptcode,deptname,"1");

            //Modified by liuwx 2012年3月17日23:55:53
            //获取超时未更新拥堵信息数量
            String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
            String jgid=strObj[0];//单位编码
//            String jgmc=strObj[1];//机构名称
//            String ccbm=strObj[2];//机构层次编码
            String jgbh;//总队(2位),支队(4位),大队(6位)
            Map<String,String> hm = new HashMap<String, String>();
            CrowdManageDao cmd = new CrowdManageDao();
            if("0000".equals(jgid.substring(2,6))){
                jgbh = jgid.substring(0,2);
            }else if("00".equals(jgid.substring(4,6))){
                jgbh = jgid.substring(0,4);
            }else{
                jgbh = jgid.substring(0,6);
            }
//            hm.put("deptId",jgid);
            hm.put("jgbh",jgbh);
            resoults[6] = cmd.getOutToDateCrowInfoCount(hm);
            //Modification finished
			CutInfoAdd cutInfoAdd = new CutInfoAdd();
			// 三十分钟以上未更新的信息减分处理的呼出
			cutInfoAdd.cutInfoAdd();
			//三十分钟以上未核实，拥堵报料扣分
			CutInfoAdd.ydblCutInfoAdd();
			String res = DataToXML.objArrayToXml(resoults);
			PromptLog.debug(request, "交通拥堵",
					new String[]{"拥堵待更新信息","交通拥堵信息","拥堵信息更新","拥堵解除","待核实拥堵信息","已核实拥堵信息","拥堵超时未更新信息"},
					resoults,res);
			out.write(res);
		} catch (Exception e) {
			logger.error("查询拥堵信息件数:" + e.getMessage());
			e.printStackTrace();
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
	public ActionForward doGetPromptCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String sql = null;
		Object[][] obj = null;
		String deptcode = ""; //部门编码
		//String deptname = ""; //部门名称
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			//deptname = (String) prop.get("BRANCHNAME");
		}
		try {
			// 取得拥堵信息为拥堵中且为本单位报送的拥堵信息的且为报送20分钟后任然没有解除的拥堵信息
			sql = " select taa.ALARMID, " +
				" taa.ALARMREGION, " +
				" taa.ROADID, " +
				" taa.ROADNAME, " +
				" tcc.CONGESTIONREASON, " +
				" to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'), " +
				" (select ATTEMPER.NAME from T_ATTEMPER_CODE ATTEMPER where taa.eventstate = ATTEMPER.ID)  " +
				//Modified by Liuwx 2011-8-8
				" ,TAA.EVENTSTATE " +
				//Modification finished
				" from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +
				" where taa.EVENTTYPE = '001002'  " +
				" and taa.EVENTSTATE = '570001' " +
				" and tcc.UPDATE_DATE+20/24/60 < sysdate " +
				" and taa.alarmid = tcc.ALARMID and taa.ALARMUNIT = '" + deptcode + "' order by taa.CASEHAPPENTIME desc ";
			System.out.println("3:" + sql);
			obj = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("查询拥堵信息件数:" + e.getMessage());
			e.printStackTrace();
		}
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
	
	public ActionForward doGetLcbptmisZt(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String roadName = request.getParameter("roadName");
		String res = "";
		try {
			String sql = "  select max(to_number(qmz)),min(to_number(qmz)) from LCB_PT_MIS where dlmc like '%"
					+ roadName + "%'";
			Object[] object = DBHandler.getLineResult(sql);
			res = DataToXML.objArrayToXml(object);
		} catch (Exception e) {
			logger.error("查询道路最大千米值最小千米值失败:" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println(res);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * 获取严重拥堵提醒信息件数<br/>
	 * 严重拥堵提醒信息件数的取得
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTroubCrowdCount(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String sql = null;
		Object[] obj = null;
		String deptcode = ""; //部门编码
		//String deptname = ""; //部门名称
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			//deptname = (String) prop.get("BRANCHNAME");
		}
		String jgbh = "";//总队(2位),支队(4位),大队(6位)
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		try {
			if(jgbh.length() == 2) {
				// 取得拥堵信息为拥堵中,拥堵一小时以上 或拥堵里程3公里以上的严重拥堵事件
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
				" and taa.alarmid = tcc.ALARMID ";
				System.out.println("3:" + sql);
			} else if(jgbh.length() == 4) {
				// 取得拥堵信息为拥堵中,拥堵一小时以上 或拥堵里程3公里以上的严重拥堵事件
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
				" and taa.alarmid = tcc.ALARMID and substr(taa.reportunit,1,4) = '"+jgbh+"'";
				System.out.println("3:" + sql);
			} else {
				// 取得拥堵信息为拥堵中且为本单位报送的拥堵信息的件数
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				// '9'表示不取任何信息
				" where taa.EVENTTYPE = '9' "; 
				System.out.println("3:" + sql);
			}
			obj = DBHandler.getLineResult(sql);
		} catch (Exception e) {
			logger.error("查询拥堵信息件数:" + e.getMessage());
			e.printStackTrace();
		}
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
	
	/**
	 * 获取严重拥堵提醒信息<br/>
	 * 严重拥堵提醒信息的取得
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetPTroubCrowdInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String mstate = StringHelper.obj2str(request.getParameter("mstate"),"");
		String sql = null;
		Object[][] obj = null;
		String deptcode = ""; //部门编码
		//String deptname = ""; //部门名称
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			//deptname = (String) prop.get("BRANCHNAME");
		}
		String jgbh = "";//总队(2位),支队(4位),大队(6位)
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		try {
			//取得拥堵信息为拥堵中且本单位可以接收的未查看过的拥堵信息件数
			sql = " select taa.ALARMID, " +
				" taa.ALARMREGION, " +
				" taa.ROADID, " +
				" taa.ROADNAME, " +
				" tcc.CONGESTIONREASON, " +
				" to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'), " +
				" (select ATTEMPER.NAME from T_ATTEMPER_CODE ATTEMPER where taa.eventstate = ATTEMPER.ID)" +
				//Modified by Liuwx 2011-8-8
				" ,TAA.EVENTSTATE " +
				//Modification finished
				" from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc, t_oa_acceptdept ad" +  
			" where taa.EVENTTYPE = '001002' " + 
//			" and taa.EVENTSTATE = '570001' " + 
//			" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
			" and taa.alarmid = tcc.ALARMID and taa.alarmid=ad.aid and ad.rpdcode='"+deptcode+"' and ad.state='1' and ad.mstate='"+mstate+"'";
			obj = DBHandler.getMultiResult(sql);
			/*
			if(jgbh.length() == 2) {
				// 取得拥堵信息为拥堵中且为本单位报送的拥堵信息的件数
				sql = " select taa.ALARMID, " +
				" taa.ALARMREGION, " +
				" taa.ROADID, " +
				" taa.ROADNAME, " +
				" tcc.CONGESTIONREASON, " +
				" to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'), " +
				" (select ATTEMPER.NAME from T_ATTEMPER_CODE ATTEMPER where taa.eventstate = ATTEMPER.ID)  " +
				" from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +
				" " +   
				" and taa.alarmid = tcc.ALARMID  order by taa.CaseHappenTime desc ";
				System.out.println("3:" + sql);
				obj = DBHandler.getMultiResult(sql);
			} else {
				// 取得拥堵信息为拥堵中且为本单位报送的拥堵信息的件数
				sql = " select taa.ALARMID, " +
				" taa.ALARMREGION, " +
				" taa.ROADID, " +
				" taa.ROADNAME, " +
				" tcc.CONGESTIONREASON, " +
				" to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'), " +
				" (select ATTEMPER.NAME from T_ATTEMPER_CODE ATTEMPER where taa.eventstate = ATTEMPER.ID)  " +
				" from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +
				// '9'表示不取任何信息
				" where taa.EVENTTYPE = '9' "; 
				System.out.println("3:" + sql);
				obj = DBHandler.getMultiResult(sql);
			}
			*/
		} catch (Exception e) {
			logger.error("查询拥堵信息件数:" + e.getMessage());
			e.printStackTrace();
		}
		String res = DataToXML.objArrayToXml(obj);
		
		out.write(res);
		out.close();
		return null;
	}
	
	private CrowdManageDao dao = new CrowdManageDao();
	public ActionForward doUpdateCADRS(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		//PrintWriter out = response.getWriter();
		
		String alarmId = request.getParameter("alarmId");
		String jgid = request.getParameter("jgid");
		dao.updateCADRState(alarmId, jgid);
		
		return null;
	}
	
	private Object getTroubCrowdCount(String deptcode){
		String jgbh = "";//总队(2位),支队(4位),大队(6位)
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		String sql = null;
		Object obj = null;
		try {
			// 取得拥堵信息为拥堵中,拥堵一小时以上 或拥堵里程3公里以上的严重拥堵事件
			//并且机构未查看过该信息
			sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc, t_attemper_cacptdept ad" +  
			" where taa.EVENTTYPE = '001002' " + 
			" and taa.EVENTSTATE = '570001' " + 
			" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
			" and taa.alarmid = tcc.ALARMID and taa.alarmid=ad.alarmid and ad.jgid='"+deptcode+"' and ad.state='0' ";
			
			/*
			if(jgbh.length() == 2) {
				// 取得拥堵信息为拥堵中,拥堵一小时以上 或拥堵里程3公里以上的严重拥堵事件
				//并且总队为
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc, t_attemper_cacptdept ad" +  
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
				" and taa.alarmid = tcc.ALARMID and taa.alarmid=ad.alarmid and ad.state='0'";
				System.out.println("3:" + sql);
			} else {
				// 取得拥堵信息为拥堵中且为本单位报送的拥堵信息的件数
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				// '9'表示不取任何信息
				" where taa.EVENTTYPE = '9' "; 
			}
			*/
			obj = DBHandler.getSingleResult(sql);
		} catch (Exception e) {
			logger.error("查询拥堵信息件数:" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
	
	private PoliceRemindCore prcore = new PoliceRemindCore();
	private CrowdRemindCore crcore = new CrowdRemindCore();
	private AccDeptCore adcore = new AccDeptCore();

    /**
     * Modified by liuwx 2012年3月17日16:26:09
     * 获取超时未更新拥堵提醒信息
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doGetOutToDateCrowdInfo(Action action,
                                              HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String deptId = StringHelper.obj2str(request.getParameter("deptId"), "");
        String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");

        HashMap hm = new HashMap();
        hm.put("deptId", deptId);
        hm.put("jgbh", jgbh);
        String xml = null;
        CrowdManageDao cmd = new CrowdManageDao();
        xml = DataToXML.objArrayToXml(cmd.getOutToDateCrowInfo(hm));
        logger.debug("doGetOutToDateCrowdInfo-->resultXML:"+xml);
        out.write(xml);
        out.flush();
        out.close();
        return null;
    }
}