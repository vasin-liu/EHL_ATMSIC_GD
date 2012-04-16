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
 * ӵ�¿���
 * 
 * @author wkz
 * @date 2009-1-16
 * 
 */
public class CrowdManageAction extends Controller {
	
	private Logger logger = Logger.getLogger(CrowdManageAction.class);
	
	
	private CrowdManageDao cmdao = new CrowdManageDao();
	/**
	 * �༭��·��Ϣ
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
		String acceptDept = StringHelper.obj2str(request.getParameter("acceptDept"), "");//�ܶ�ָ��ӵ�½��յ�λ
		//Modified by Liuwx 2011-08-08
		String ReceiveTime = StringHelper.obj2str(request.getParameter("ReceiveTime"), "");
		//Modification finished
		//Modified by Xiayx 2011-08-08
		//�������˺͸���
		String apname = StringHelper.obj2str(request.getParameter("apname"), "");
		String apath = StringHelper.obj2str(request.getParameter("apath"), "");
		//Modification finished
		
		String baosongDepartMentId = ALARMUNIT;
		
		String baoSongFlg = "0";
		// �������2λ�ܶӱ��͵�ӵ����Ϣ
		if (jgbh.length() == 2) {
			baoSongFlg = "1";
			//Modified by Liuwx 2011-8-8
			//ȡ���ܶ��·������
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
		hm.put("EVENTSTATE", "570001");// ӵ����
		hm.put("BLID", "");//����ID
		hm.put("remindInfo", remindInfo);// ������ʾ
		hm.put("baoSongFlg", baoSongFlg);// ���ͱ�־
		hm.put("baosongDepartMentId", baosongDepartMentId);// ���ͱ�־
		hm.put("crowdTypeFlg", crowdTypeFlg);// �жϱ�־
		hm.put("acceptDept", acceptDept);
		//Modified by Liuwx 2011-8-8
		hm.put("ReceiveTime", ReceiveTime);
		//Modification finished
		//Modified by Xiayx 2011-8-8
		hm.put("apname", apname);
		hm.put("apath", apath);
		//Modification finished
		//Modify by Xiayx 2012-3-1
		//���ӵ�·���-��ȡ����
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
		String result = "ӵ����Ϣ";
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
				logger.error("��ȡ���������쳣��",e);
			}
		}
		result += (insertOrUpdate==0?"����":"����") + (flag?"�ɹ�":"ʧ��");
		result += "��";
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
		//ȡ����ΰӵ����Ϣ�ĸ���
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
			if (crowdId != null && crowdId.indexOf("-") == -1) {//��ӷ�ΰӵ����Ϣ�ɹ�
				sql = "update t_attemper_congestion set fwid='"+crowdId+"' where alarmid='"+alarmId+"'";
				flag = DBHandler.execute(sql);
			}else{
				logger.error("���·�ΰӵ����Ϣʧ��");
			}
		}
		//Modification finished
	}

	/**
	 * <pre>
	 * ������ΰ��˾ӵ����Ϣ
	 * ����webservice�ӿڣ���ӻ���ɾ����ΰ��˾ӵ����Ϣ
	 * </pre>
	 * @param mname ��������
	 * @param crowdInfo ӵ����Ϣ
	 * @return �������
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
				System.err.println("service����call�쳣��");
				e.printStackTrace();
			}
			if(call != null){
				String svurl = "http://10.40.30.31/blb/WS_BLB_Traffic_BizCtrlImpl.asmx?wsdl";//����IP
				//svurl = "http://113.108.187.3/blb/WS_BLB_Traffic_BizCtrlImpl.asmx?wsdl";//����IP
				String namespace = "http://tempuri.org/";
				String uri = namespace + mname;
				call.setTargetEndpointAddress(svurl);
				call.setUseSOAPAction(true);
				call.setSOAPActionURI(uri);
				call.setOperationName(new QName(namespace, mname));
				crowdInfo.put("compaySign", "fangwei");//��ΰ��˾��־����compaySign����companySign
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
					System.err.println("service����"+mname+"�쳣��");
					e.printStackTrace();
				}
			}
		}*/
		return result;
	}
	
	/**
	 * ��ӷ�ΰӵ����Ϣ
	 * @param dlmc ��·���ƻ��߱�ţ���·����ž�·�����ơ����۰ĸ��ٻ���G4
	 * @param qslc ��ʼ��̡�100
	 * @param jslc ������̡�110
	 * @param sj ӵ�¿�ʼʱ�䡣2011-07-18 12:12:12
	 * @param fx ӵ�·��򡣱���
	 * @param gxdd ��Ͻ��ӡ����ݸ��ٹ�·һ���
	 * @return ������ɹ�����ӵ��������ţ����󷵻ش����־��<br>
	 * �����־ֵ�ͺ���˵����-1:��֤����-2:�Ҳ�����Ӧ·����-3:����ƥ�䣬-5:��������
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
			crowdInfo.put("ghdd", gxdd);//ע����ghdd����gxdd
			result = operateFwCrowd(mname, crowdInfo);
		}
		return result;
	}
	
	/**
	 * ɾ����ΰ��˾ӵ����Ϣ
	 * @param id ӵ���������
	 * @return ����������־ֵ�ͺ���˵����1 �ɹ���0 ��¼�����ڡ�-1 ��֤����5 ��������
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
	 * ��ȡ��ΰ��·��־
	 * ��·��־
	 * 1.��·����
	 * 2.��·�������
	 * </pre>
	 * @param dlmc ��ΰ��·����
	 * @param gbdm ��ΰ
	 * @return
	 */
	public String getFwDlbz(String dlmc, String gbdm){
		if(gbdm != null && !gbdm.equals("z_old")){//������Ǿ�·
			dlmc = gbdm;
		}
		return dlmc;
	}
	
	/**
	 * ��ȡ��ΰ��·����
	 * @param dlfx ��ϵͳ��·����ֵΪ��0��1��2��
	 * @param begin ��ΰ��·���
	 * @param end ��ΰ��·�յ�
	 * @return ��ΰ��·����
	 */
	public String getFwDlfx(String dlfx, String begin, String end) {
		if (dlfx != null) {
			if (dlfx.equals("0")) {
				dlfx = begin;
			} else if (dlfx.equals("1")) {
				dlfx = end;
			} else if (dlfx.equals("2")) {
				dlfx = "˫����";
			}
		}
		return dlfx;
	}
	
	/**
	 * ���ӵ����Ϣ<br/>
	 * ӵ����Ϣ�Ľ��
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
		//��ӽ���ӵ�½�����ʾ�ͽ���������ʾ
		if(flag){
			String rtime = StringHelper.obj2str(request.getParameter("rtime"),Constant.getCurrentTime(false).substring(0,16));
			String dmsg = "��ͨӵ���������·��ָ�������ͨ��";
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
			//������ܶ��û������ӵ����Ϣ�����Զ�ǩ�գ���������
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
	 * ��ѯ��·ӵ����Ϣ
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
						xmlData.append("<col>��</col>");
					} else {
						xmlData.append("<col>" + StringHelper.obj2str(obj0[i]) + "</col>");
					}
				}
				xmlData.append("\n</row>\n");
			}
			//Modify by xiayx 2012-3-14
			//��ȡӵ�½�����ʾ�ͽ�����ʾ
			String xml = crcore.getStringByAlarmId(alarmid);
			if(xml != null){
				xmlData.append(xml);
			}
			xml = prcore.getStringByAlarmId(alarmid);
			if(xml != null){
				xmlData.append("\n"+xml);
			}
			String jgid = (String)request.getSession().getAttribute(Constant.JGID_VAR);
			//��ȡ�ܶӵĴ���״��
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
			logger.error("��ȡӵ����Ϣ�쳣��",e);
		}
		xmlData.append("\n</RFWin>\n");
		xmlData.append("</rfXML>\n");
		logger.info("getCrowdInfo XML:\n" + xmlData);
		out.write(String.valueOf(xmlData));
		out.close();
		return null;
	}

	/**
	 * ���ӵ����Ϣ
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
			logger.error("ӵ����Ϣ��ȡ��·�����쳣:"+e.getMessage());
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
		String fileName = "ӵ����Ϣ";
		String titleName = "ӵ����Ϣ�б�";
		String tabHeader = "����,����ʱ��,����ʱ��,��·����,·������,��ʼ���ǧ��ֵ,��ʼ��̰���ֵ,��ֹ���ǧ��ֵ,��ֹ��̰���ֵ,��·����,�¼�״̬,���λ���,���λ����,���,�ʱ��,��������,֧�������";
		String sheetName = "ӵ����Ϣ�б�";
		String searchSql = request.getParameter("searchSql");
		try {
			Object[][] data = DBHandler.getMultiResult(searchSql);
			if(data == null){
				data = new Object[1][tabHeader.split(",").length];
			}
			showExcel(fileName, sheetName, titleName, tabHeader, data, response);
		} catch (Exception e) {
			logger.error("ӵ����Ϣ����Excelʱ�쳣:"+e.getMessage());
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
	 * ��ȡӵ��������Ϣ����<br/>
	 * ӵ��������Ϣ������ȡ��
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
		String deptcode = ""; //���ű���
		String deptname = ""; //��������
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			deptname = (String) prop.get("BRANCHNAME");
		}
		try {
			// ȡ��ӵ����ϢΪӵ������Ϊ����λ���͵�ӵ����Ϣ����Ϊ����20���Ӻ���Ȼû�н����ӵ����Ϣ�ļ���
			sql = "select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +
					" where taa.EVENTTYPE = '001002' " +
					" and taa.EVENTSTATE = '570001' " +
					" and tcc.UPDATE_DATE+20/24/60 < sysdate " +
					" and taa.alarmid = tcc.ALARMID and taa.ALARMUNIT = '" + deptcode + "'";
			Object obj = DBHandler.getSingleResult(sql);
			resoults[0] = obj; 
//			jgid������š�atype�������͡�state����״̬��mstate��Ϣ״̬
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

            //Modified by liuwx 2012��3��17��23:55:53
            //��ȡ��ʱδ����ӵ����Ϣ����
            String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//��ȡ��λ��Ϣ��
            String jgid=strObj[0];//��λ����
//            String jgmc=strObj[1];//��������
//            String ccbm=strObj[2];//������α���
            String jgbh;//�ܶ�(2λ),֧��(4λ),���(6λ)
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
			// ��ʮ��������δ���µ���Ϣ���ִ���ĺ���
			cutInfoAdd.cutInfoAdd();
			//��ʮ��������δ��ʵ��ӵ�±��Ͽ۷�
			CutInfoAdd.ydblCutInfoAdd();
			String res = DataToXML.objArrayToXml(resoults);
			PromptLog.debug(request, "��ͨӵ��",
					new String[]{"ӵ�´�������Ϣ","��ͨӵ����Ϣ","ӵ����Ϣ����","ӵ�½��","����ʵӵ����Ϣ","�Ѻ�ʵӵ����Ϣ","ӵ�³�ʱδ������Ϣ"},
					resoults,res);
			out.write(res);
		} catch (Exception e) {
			logger.error("��ѯӵ����Ϣ����:" + e.getMessage());
			e.printStackTrace();
		}
		out.close();
		return null;
	}
	
	/**
	 * ��ȡӵ��������Ϣ<br/>
	 * ӵ��������Ϣ��ȡ��
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
		String deptcode = ""; //���ű���
		//String deptname = ""; //��������
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			//deptname = (String) prop.get("BRANCHNAME");
		}
		try {
			// ȡ��ӵ����ϢΪӵ������Ϊ����λ���͵�ӵ����Ϣ����Ϊ����20���Ӻ���Ȼû�н����ӵ����Ϣ
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
			logger.error("��ѯӵ����Ϣ����:" + e.getMessage());
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
			logger.error("��ѯ��·���ǧ��ֵ��Сǧ��ֵʧ��:" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println(res);
		out.write(res);
		out.close();
		return null;
	}

	/**
	 * ��ȡ����ӵ��������Ϣ����<br/>
	 * ����ӵ��������Ϣ������ȡ��
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
		String deptcode = ""; //���ű���
		//String deptname = ""; //��������
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			//deptname = (String) prop.get("BRANCHNAME");
		}
		String jgbh = "";//�ܶ�(2λ),֧��(4λ),���(6λ)
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		try {
			if(jgbh.length() == 2) {
				// ȡ��ӵ����ϢΪӵ����,ӵ��һСʱ���� ��ӵ�����3�������ϵ�����ӵ���¼�
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
				" and taa.alarmid = tcc.ALARMID ";
				System.out.println("3:" + sql);
			} else if(jgbh.length() == 4) {
				// ȡ��ӵ����ϢΪӵ����,ӵ��һСʱ���� ��ӵ�����3�������ϵ�����ӵ���¼�
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
				" and taa.alarmid = tcc.ALARMID and substr(taa.reportunit,1,4) = '"+jgbh+"'";
				System.out.println("3:" + sql);
			} else {
				// ȡ��ӵ����ϢΪӵ������Ϊ����λ���͵�ӵ����Ϣ�ļ���
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				// '9'��ʾ��ȡ�κ���Ϣ
				" where taa.EVENTTYPE = '9' "; 
				System.out.println("3:" + sql);
			}
			obj = DBHandler.getLineResult(sql);
		} catch (Exception e) {
			logger.error("��ѯӵ����Ϣ����:" + e.getMessage());
			e.printStackTrace();
		}
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
	
	/**
	 * ��ȡ����ӵ��������Ϣ<br/>
	 * ����ӵ��������Ϣ��ȡ��
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
		String deptcode = ""; //���ű���
		//String deptname = ""; //��������
		
		Hashtable prop = DispatchUtil.getCurrentUserData(request);
		if (prop != null) {
			deptcode = (String) prop.get("BRANCHID");
			//deptname = (String) prop.get("BRANCHNAME");
		}
		String jgbh = "";//�ܶ�(2λ),֧��(4λ),���(6λ)
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		try {
			//ȡ��ӵ����ϢΪӵ�����ұ���λ���Խ��յ�δ�鿴����ӵ����Ϣ����
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
				// ȡ��ӵ����ϢΪӵ������Ϊ����λ���͵�ӵ����Ϣ�ļ���
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
				// ȡ��ӵ����ϢΪӵ������Ϊ����λ���͵�ӵ����Ϣ�ļ���
				sql = " select taa.ALARMID, " +
				" taa.ALARMREGION, " +
				" taa.ROADID, " +
				" taa.ROADNAME, " +
				" tcc.CONGESTIONREASON, " +
				" to_char(taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi'), " +
				" (select ATTEMPER.NAME from T_ATTEMPER_CODE ATTEMPER where taa.eventstate = ATTEMPER.ID)  " +
				" from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +
				// '9'��ʾ��ȡ�κ���Ϣ
				" where taa.EVENTTYPE = '9' "; 
				System.out.println("3:" + sql);
				obj = DBHandler.getMultiResult(sql);
			}
			*/
		} catch (Exception e) {
			logger.error("��ѯӵ����Ϣ����:" + e.getMessage());
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
		String jgbh = "";//�ܶ�(2λ),֧��(4λ),���(6λ)
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
			// ȡ��ӵ����ϢΪӵ����,ӵ��һСʱ���� ��ӵ�����3�������ϵ�����ӵ���¼�
			//���һ���δ�鿴������Ϣ
			sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc, t_attemper_cacptdept ad" +  
			" where taa.EVENTTYPE = '001002' " + 
			" and taa.EVENTSTATE = '570001' " + 
			" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
			" and taa.alarmid = tcc.ALARMID and taa.alarmid=ad.alarmid and ad.jgid='"+deptcode+"' and ad.state='0' ";
			
			/*
			if(jgbh.length() == 2) {
				// ȡ��ӵ����ϢΪӵ����,ӵ��һСʱ���� ��ӵ�����3�������ϵ�����ӵ���¼�
				//�����ܶ�Ϊ
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc, t_attemper_cacptdept ad" +  
				" where taa.EVENTTYPE = '001002' " + 
				" and taa.EVENTSTATE = '570001' " + 
				" and ((taa.CaseHappenTime+1/24 < sysdate) or to_number(taa.ENDKMVALUE) - to_number(taa.KMVALUE) >= 3) " +   
				" and taa.alarmid = tcc.ALARMID and taa.alarmid=ad.alarmid and ad.state='0'";
				System.out.println("3:" + sql);
			} else {
				// ȡ��ӵ����ϢΪӵ������Ϊ����λ���͵�ӵ����Ϣ�ļ���
				sql = " select count(1) from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc " +  
				// '9'��ʾ��ȡ�κ���Ϣ
				" where taa.EVENTTYPE = '9' "; 
			}
			*/
			obj = DBHandler.getSingleResult(sql);
		} catch (Exception e) {
			logger.error("��ѯӵ����Ϣ����:" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
	
	private PoliceRemindCore prcore = new PoliceRemindCore();
	private CrowdRemindCore crcore = new CrowdRemindCore();
	private AccDeptCore adcore = new AccDeptCore();

    /**
     * Modified by liuwx 2012��3��17��16:26:09
     * ��ȡ��ʱδ����ӵ��������Ϣ
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