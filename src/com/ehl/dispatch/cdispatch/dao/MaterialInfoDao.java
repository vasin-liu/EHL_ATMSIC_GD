package com.ehl.dispatch.cdispatch.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.action.Jspsmart;
import com.ehl.dispatch.cdispatch.core.DepartmentUtil;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.notice.core.ContentCore;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.tira.util.Sql;

/**
 * ��·����������
 * 
 * @author wangxt
 * @date 2009-1-16
 * 
 */
public class MaterialInfoDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);

	/**
	 * �������
	 * 
	 * @param res
	 * @return
	 */
	public boolean addMaterialInfo(HashMap res) {
		boolean flag = false;
		try {
			String ALARMID = res.get("ALARMID").toString();// 0
			String ROADID = res.get("ROADID").toString();// 1
			String TELENUM = res.get("TELENUM").toString();// 1
			String EVENTSOURCE = res.get("EVENTSOURCE").toString();// 2
			String EVENTTYPE = res.get("EVENTTYPE").toString();// 3
			String ALARMUNIT = res.get("ALARMUNIT").toString();// 4
			String TITLE = res.get("TITLE").toString();// 5
			String ALARMREGIONID = res.get("ALARMREGIONID").toString();// 6
			String ALARMREGION = res.get("ALARMREGION").toString();// 7
			String ROADNAME = res.get("ROADNAME").toString();// 8
			String KMVALUE = res.get("KMVALUE").toString();// 9
			String CASEHAPPENTIME = res.get("CASEHAPPENTIME").toString();// 10
			String ALARMTIME = res.get("ALARMTIME").toString();// 10
			String REPORTUNIT = res.get("REPORTUNIT").toString();// 11
			String REPORTPERSON = res.get("REPORTPERSON").toString();// 12
			String RESPONSIBLEPERSON = res.get("RESPONSIBLEPERSON").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();// 13
			String ISBUS = res.get("ISBUS").toString();
			String ISSCHOOLBUS = res.get("ISSCHOOLBUS").toString();
			String ISDANAGERCAR = res.get("ISDANAGERCAR").toString();
			String ISFOREIGNAFFAIRS = res.get("ISFOREIGNAFFAIRS").toString();
			String ISPOLICE = res.get("ISPOLICE").toString();
			String ISMASSESCASE = res.get("ISMASSESCASE").toString();
			String ISCONGESTION = res.get("ISCONGESTION").toString();
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			String DEATHPERSONCOUNT = res.get("DEATHPERSONCOUNT").toString();
			String BRUISEPERSONCOUNT = res.get("BRUISEPERSONCOUNT").toString();
			String FLOW1 = res.get("FLOW1").toString();
			String ALARMDESC = res.get("ALARMDESC").toString();
			String FLOWKIND1 = res.get("FLOWKIND1").toString();
			String OPERATEID = res.get("OPERATEID").toString();
			String buttonFlag = res.get("buttonFlag").toString();
			String receivetime = res.get("receivetime").toString();
			String approver = res.get("buttonFlag").toString();

			// trafficInfoȡ���¹���ϸ����
			String trafficInfo = res.get("trafficInfo").toString();
			// ȡ���¹ʷ���ʱ��
			String info1 = res.get("caseHappenTime").toString();
			// ȡ���¹ʷ����ص�
			String caseHappenPlace = res.get("caseHappenPlace").toString();
			// ȡ���¹���������
			String info15 = res.get("info15").toString();
			// ȡ���¹ʽӾ���
			String RECEIVEPERSON = res.get("RECEIVEPERSON").toString();
			String SUPERUNIT = DepartmentUtil.getSuperUnit(REPORTUNIT);

			// 2010��7��19��׷�� "����","ʧ������","�ϱ��绰"
			String ISOthersItem = res.get("ISOthersItem").toString();
			String MISSINGPERSONCOUNT = res.get("MISSINGPERSONCOUNT")
					.toString();
			String TELNUMSB = res.get("TELNUMSB").toString();
			String ACCEPTUNIT = res.get("jgid_Zd").toString();

			if ("0".equals(buttonFlag)) {
				// ��ӱ���ʱ״̬���趨
				FLOWKIND1 = "004032";
			} else {
				// ����ϱ�ʱ״̬���趨
				FLOWKIND1 = "004033";
			}
			StringBuffer sql = new StringBuffer(
					"insert into t_attemper_alarm(ALARMID,ROADID,EVENTSOURCE,");
			sql.append(
					"EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION,ROADNAME,KMVALUE,CASEHAPPENTIME")
					.append(",REPORTUNIT,REPORTPERSON,REPORTTIME,ALARMTIME,ROADDIRECTION,EVENTSTATE,SUPERUNIT,ALARMDESC,TELENUM,ACCEPTUNIT,ALARMADDRESS,RECEIVETIME,DDAPPROVER,ZDAPPROVER,ZODAPPROVER)")
					.append(" values('").append(ALARMID).append("','")
					.append(ROADID).append("','").append(EVENTSOURCE)
					.append("','").append(EVENTTYPE).append("','")
					.append(ALARMUNIT).append("','").append(TITLE)
					.append("','").append(ALARMREGIONID).append("','")
					.append(ALARMREGION).append("','").append(ROADNAME)
					.append("','").append(KMVALUE).append("',to_date('")
					.append(info1).append("','yyyy-mm-dd hh24:mi'),'")
					.append(REPORTUNIT).append("','").append(REPORTPERSON)
					.append("',to_date('").append(REPORTTIME)
					.append("','yyyy-mm-dd hh24:mi'),to_date('")
					.append(ALARMTIME).append("','yyyy-mm-dd hh24:mi'),'")
					.append(ROADDIRECTION).append("','").append(FLOWKIND1)
					.append("','").append(SUPERUNIT).append("','")
					.append(trafficInfo).append("','").append(TELENUM)
					.append("','").append(ACCEPTUNIT).append("'").append(",'")
					.append(caseHappenPlace).append("',to_date('")
					.append(receivetime).append("','yyyy-mm-dd hh24:mi')")
					.append(",'").append(approver).append("','").append("")
					.append("','").append("").append("')");
			System.out.println("MaterialInfoDao.addMaterialInfo.sql1:" + sql);
			flag = DBHandler.execute(sql.toString());

			sql = new StringBuffer(
					"insert into t_attemper_accident(ALARMID,DEATHPERSONCOUNT,CASEHAPPENTIME");
			sql.append(
					",ISBUS,ISSCHOOLBUS,ISDANAGERCAR,ISFOREIGNAFFAIRS,ISPOLICE,ISMASSESCASE,ISCONGESTION,MISSINGPERSONCOUNT,ISOTHERSITEM")
					.append(",BRUISEPERSONCOUNT,RECEIVEPERSON,RECEIVETIME)values('")
					.append(ALARMID).append("','").append(DEATHPERSONCOUNT)
					.append("',to_date('").append(info1)
					.append("','yyyy-mm-dd hh24:mi'),'").append(ISBUS)
					.append("','").append(ISSCHOOLBUS).append("','")
					.append(ISDANAGERCAR).append("','")
					.append(ISFOREIGNAFFAIRS).append("','").append(ISPOLICE)
					.append("','").append(ISMASSESCASE).append("','")
					.append(ISCONGESTION).append("','")
					.append(MISSINGPERSONCOUNT).append("','")
					.append(ISOthersItem).append("','")
					.append(BRUISEPERSONCOUNT).append("','")
					.append(RECEIVEPERSON).append("',to_date('")
					.append(ALARMTIME).append("','yyyy-mm-dd hh24:mi'))");
			System.out.println("MaterialInfoDao.addMaterialInfo.sql2:" + sql);
			flag = DBHandler.execute(sql.toString());

			sql = new StringBuffer(
					"insert into t_attemper_alarm_zd (ALARMID,ROADID,EVENTSOURCE,");
			sql.append(
					"EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION,ROADNAME,KMVALUE,CASEHAPPENTIME")
					.append(",REPORTUNIT,REPORTPERSON,REPORTTIME,ALARMTIME,ROADDIRECTION,EVENTSTATE,SUPERUNIT,ALARMDESC,TELENUM,ACCEPTUNIT,ALARMADDRESS)")
					.append(" values('").append(ALARMID).append("','")
					.append(ROADID).append("','").append(EVENTSOURCE)
					.append("','").append(EVENTTYPE).append("','")
					.append(ALARMUNIT).append("','").append(TITLE)
					.append("','").append(ALARMREGIONID).append("','")
					.append(ALARMREGION).append("','").append(ROADNAME)
					.append("','").append(KMVALUE).append("',to_date('")
					.append(info1).append("','yyyy-mm-dd hh24:mi'),'")
					.append(REPORTUNIT).append("','").append(REPORTPERSON)
					.append("',to_date('").append(REPORTTIME)
					.append("','yyyy-mm-dd hh24:mi'),to_date('")
					.append(ALARMTIME).append("','yyyy-mm-dd hh24:mi'),'")
					.append(ROADDIRECTION).append("','").append(FLOWKIND1)
					.append("','").append(SUPERUNIT).append("','")
					.append(trafficInfo).append("','").append(TELENUM)
					.append("','").append(ACCEPTUNIT).append("'").append(",'")
					.append(caseHappenPlace).append("')");
			System.out.println("MaterialInfoDao.addMaterialInfo.sql3:" + sql);
			flag = DBHandler.execute(sql.toString());

			sql = new StringBuffer(
					"insert into t_attemper_accident_zd(ALARMID,DEATHPERSONCOUNT,CASEHAPPENTIME");
			sql.append(
					",ISBUS,ISSCHOOLBUS,ISDANAGERCAR,ISFOREIGNAFFAIRS,ISPOLICE,ISMASSESCASE,ISCONGESTION,MISSINGPERSONCOUNT,ISOTHERSITEM")
					.append(",BRUISEPERSONCOUNT,RECEIVEPERSON,RECEIVETIME)values('")
					.append(ALARMID).append("','").append(DEATHPERSONCOUNT)
					.append("',to_date('").append(info1)
					.append("','yyyy-mm-dd hh24:mi'),'").append(ISBUS)
					.append("','").append(ISSCHOOLBUS).append("','")
					.append(ISDANAGERCAR).append("','")
					.append(ISFOREIGNAFFAIRS).append("','").append(ISPOLICE)
					.append("','").append(ISMASSESCASE).append("','")
					.append(ISCONGESTION).append("','")
					.append(MISSINGPERSONCOUNT).append("','")
					.append(ISOthersItem).append("','")
					.append(BRUISEPERSONCOUNT).append("','")
					.append(RECEIVEPERSON).append("',to_date('")
					.append(ALARMTIME).append("','yyyy-mm-dd hh24:mi'))");
			System.out.println("MaterialInfoDao.addMaterialInfo.sql4:" + sql);
			flag = DBHandler.execute(sql.toString());
		} catch (Exception e) {
			flag = false;
			logger.error("�����ش�����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * ��ӱ༭������Ϣ
	 * 
	 * @param res
	 * @return
	 */
	public boolean editMaterialInfo(HashMap res) {
		boolean flag = false;
		try {
			String buttonPressed = res.get("buttonPressed").toString();
			String ALARMID = res.get("ALARMID").toString();// 0
			String ROADID = res.get("ROADID").toString();// 1
			String TELENUM = res.get("TELENUM").toString();// 1
			String EVENTSOURCE = res.get("EVENTSOURCE").toString();// 2
			String EVENTTYPE = res.get("EVENTTYPE").toString();// 3
			String ALARMUNIT = res.get("ALARMUNIT").toString();// 4
			String TITLE = res.get("TITLE").toString();// 5
			String ALARMREGIONID = res.get("ALARMREGIONID").toString();// 6
			String ALARMREGION = res.get("ALARMREGION").toString();// 7
			String ROADNAME = res.get("ROADNAME").toString();// 8
			String KMVALUE = res.get("KMVALUE").toString();// 9
			String MVALUE = res.get("MVALUE").toString();// 9
			String CASEHAPPENTIME = res.get("CASEHAPPENTIME").toString();// 10
			String REPORTUNIT = res.get("REPORTUNIT").toString();// 11
			String REPORTPERSON = res.get("REPORTPERSON").toString();// 12
			String RESPONSIBLEPERSON = res.get("RESPONSIBLEPERSON").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();// 13
			String ISBUS = res.get("ISBUS").toString();
			String ISSCHOOLBUS = res.get("ISSCHOOLBUS").toString();
			String ISDANAGERCAR = res.get("ISDANAGERCAR").toString();
			String ISFOREIGNAFFAIRS = res.get("ISFOREIGNAFFAIRS").toString();
			String ISPOLICE = res.get("ISPOLICE").toString();
			String ISMASSESCASE = res.get("ISMASSESCASE").toString();
			String ISCONGESTION = res.get("ISCONGESTION").toString();
			String ROADDIRECTION = res.get("ROADDIRECTION").toString();
			String DEATHPERSONCOUNT = res.get("DEATHPERSONCOUNT").toString();
			String BRUISEPERSONCOUNT = res.get("BRUISEPERSONCOUNT").toString();
			String ALARMDESC = res.get("ALARMDESC").toString();
			String zbrName = res.get("zbrName").toString();

			String FLOW1 = res.get("FLOW1").toString();
			String FLOWID1 = res.get("FLOWID1").toString();
			String FLOWKIND1 = res.get("FLOWKIND1").toString();
			String FLOW2 = res.get("FLOW2").toString();
			String FLOWID2 = res.get("FLOWID2").toString();
			String FLOWKIND2 = res.get("FLOWKIND2").toString();
			String FLOW3 = res.get("FLOW3").toString();
			String FLOWID3 = res.get("FLOWID3").toString();
			String FLOWKIND3 = res.get("FLOWKIND3").toString();
			String FLOW4 = res.get("FLOW4").toString();
			String FLOWID4 = res.get("FLOWID4").toString();
			String FLOWKIND4 = res.get("FLOWKIND4").toString();
			String FLOW5 = res.get("FLOW5").toString();
			String FLOWID5 = res.get("FLOWID5").toString();
			String FLOWKIND5 = res.get("FLOWKIND5").toString();
			String FLOW6 = res.get("FLOW6").toString();
			String FLOWID6 = res.get("FLOWID6").toString();
			String FLOWKIND6 = res.get("FLOWKIND6").toString();
			String FLOW7 = res.get("FLOW7").toString();
			String FLOWID7 = res.get("FLOWID7").toString();
			String FLOWKIND7 = res.get("FLOWKIND7").toString();
			String FLOW8 = res.get("FLOW8").toString();
			String FLOWID8 = res.get("FLOWID8").toString();
			String FLOWKIND8 = res.get("FLOWKIND8").toString();
			String FLOW9 = res.get("FLOW9").toString();
			String FLOWID9 = res.get("FLOWID9").toString();
			String FLOWKIND9 = res.get("FLOWKIND9").toString();

			String REPORTPERSONXM = res.get("REPORTPERSONXM").toString();

			String OPERATEID = res.get("OPERATEID").toString();
			String SUPERUNIT = DepartmentUtil.getSuperUnit(REPORTUNIT);

			// trafficInfoȡ���¹���ϸ����
			String trafficInfo = res.get("trafficInfo").toString();

			// ȡ���¹ʷ���ʱ��
			String info1 = res.get("caseHappenTime").toString();
			// ȡ���¹ʷ����ص�
			String caseHappenPlace = res.get("caseHappenPlace").toString();
			// ȡ���¹���������
			String info15 = res.get("info15").toString();
			// ȡ���¹ʽӾ���
			String RECEIVEPERSON = res.get("RECEIVEPERSON").toString();
			// �����
			String ddapprover = res.get("ddapprover").toString();
			String zdapprover = res.get("zdapprover").toString();
			String zodapprover = res.get("zodapprover").toString();
			// �Ӿ�ʱ��
			String receivetime = res.get("receivetime").toString();
			// buttonFlag("0"����,"1"�ϱ�)����id���ϱ��ˣ���ϵ�绰
			String buttonFlag = res.get("buttonFlag").toString();
			// �������2λ���ܶ�4λ��֧��6λ�����
			String jgbh = res.get("jgbh").toString();
			String saveFlag = res.get("saveFlag").toString();
			String reportunitId = res.get("reportunitId").toString();

			// 2010��7��19��׷�� "����","ʧ������","�ϱ��绰"
			String ISOthersItem = res.get("ISOthersItem").toString();
			String MISSINGPERSONCOUNT = res.get("MISSINGPERSONCOUNT")
					.toString();
			String TELNUMSB = res.get("TELNUMSB").toString();
			// ���յ�λid
			String ACCEPTUNIT = res.get("jgid_Zd").toString();

			// �����¹ʱ��
			String glAccNum = res.get("glAccNum").toString();
			// �����ͨ�¹�
			String ISARMYACC = res.get("ISARMYACC").toString();

			// ����û�������"�ϱ�"��ĸ��´���
			if (6 == jgbh.length() && "1".equals(buttonFlag)) {
				StringBuffer sql = new StringBuffer(
						"update t_attemper_alarm set ROADID='");
				sql.append(ROADID).append("',EVENTSOURCE='")
						.append(EVENTSOURCE).append("',EVENTTYPE='")
						.append(EVENTTYPE).append("',ALARMUNIT='")
						.append(ALARMUNIT).append("',TITLE='").append(TITLE)
						.append("',ACCEPTUNIT='").append(ACCEPTUNIT)
						.append("',TELENUM='").append(TELENUM)
						.append("',glAccNum='").append(glAccNum)
						.append("',ALARMADDRESS='").append(caseHappenPlace)
						.append("',ALARMREGIONID='").append(ALARMREGIONID)
						.append("',ALARMREGION='").append(ALARMREGION)
						.append("',ROADNAME='").append(ROADNAME)
						.append("',KMVALUE='").append(KMVALUE)
						.append("',MVALUE='").append(MVALUE)
						.append("',CASEHAPPENTIME=to_date('").append(info1)
						.append("','yyyy-mm-dd hh24:mi'),")
						.append(" REPORTPERSON='").append(REPORTPERSON)
						.append("',REPORTTIME=to_date('").append(REPORTTIME)
						.append("','yyyy-mm-dd hh24:mi'),ALARMTIME=to_date('")
						.append(REPORTTIME)
						.append("','yyyy-mm-dd hh24:mi'),ROADDIRECTION='")
						.append(ROADDIRECTION).append("',EVENTSTATE='")
						.append("004033").append("',ALARMDESC='")
						.append(trafficInfo).append("',RECEIVETIME=to_date('")
						.append(receivetime)
						.append("','yyyy-mm-dd hh24:mi'),DDAPPROVER='")
						.append(ddapprover).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("1:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer("update T_attemper_accident set ");
				sql.append(" ISBUS='").append(ISBUS).append("',ISSCHOOLBUS='")
						.append(ISSCHOOLBUS).append("',ISDANAGERCAR='")
						.append(ISDANAGERCAR).append("',ISFOREIGNAFFAIRS='")
						.append(ISFOREIGNAFFAIRS)
						.append("',MISSINGPERSONCOUNT='")
						.append(MISSINGPERSONCOUNT).append("',ISOTHERSITEM='")
						.append(ISOthersItem).append("',ISARMYACC='")
						.append(ISARMYACC).append("',ISPOLICE='")
						.append(ISPOLICE + "',ISMASSESCASE='")
						.append(ISMASSESCASE).append("',ISCONGESTION='")
						.append(ISCONGESTION).append("',DEATHPERSONCOUNT='")
						.append(DEATHPERSONCOUNT)
						.append("',BRUISEPERSONCOUNT='")
						.append(BRUISEPERSONCOUNT).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("2:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer(
						"update t_attemper_alarm_zd set ROADID='");
				sql.append(ROADID).append("',EVENTSOURCE='")
						.append(EVENTSOURCE).append("',EVENTTYPE='")
						.append(EVENTTYPE).append("',ACCEPTUNIT='")
						.append(ACCEPTUNIT).append("',glAccNum='")
						.append(glAccNum).append("',ALARMADDRESS='")
						.append(caseHappenPlace).append("',ALARMUNIT='")
						.append(ALARMUNIT).append("',TITLE='").append(TITLE)
						.append("',ALARMREGIONID='").append(ALARMREGIONID)
						.append("',ALARMREGION='").append(ALARMREGION)
						.append("',ROADNAME='").append(ROADNAME)
						.append("',KMVALUE='").append(KMVALUE)
						.append("',MVALUE='").append(MVALUE)
						.append("',CASEHAPPENTIME=to_date('").append(info1)
						.append("','yyyy-mm-dd hh24:mi'),")
						.append(" REPORTPERSON='").append(REPORTPERSON)
						.append("',REPORTTIME=to_date('").append(REPORTTIME)
						.append("','yyyy-mm-dd hh24:mi'),ALARMTIME=to_date('")
						.append(REPORTTIME)
						.append("','yyyy-mm-dd hh24:mi'),ROADDIRECTION='")
						.append(ROADDIRECTION).append("',EVENTSTATE='")
						.append("004033").append("',ALARMDESC='")
						.append(trafficInfo).append("',RECEIVETIME=to_date('")
						.append(receivetime)
						.append("','yyyy-mm-dd hh24:mi'),DDAPPROVER='")
						.append(ddapprover).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("1:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer("update T_attemper_accident_zd set ");
				sql.append(" ISBUS='").append(ISBUS).append("',ISSCHOOLBUS='")
						.append(ISSCHOOLBUS).append("',ISDANAGERCAR='")
						.append(ISDANAGERCAR).append("',ISFOREIGNAFFAIRS='")
						.append(ISFOREIGNAFFAIRS)
						.append("',MISSINGPERSONCOUNT='")
						.append(MISSINGPERSONCOUNT).append("',ISOTHERSITEM='")
						.append(ISOthersItem).append("',ISARMYACC='")
						.append(ISARMYACC).append("',ISPOLICE='")
						.append(ISPOLICE + "',ISMASSESCASE='")
						.append(ISMASSESCASE).append("',ISCONGESTION='")
						.append(ISCONGESTION).append("',DEATHPERSONCOUNT='")
						.append(DEATHPERSONCOUNT)
						.append("',BRUISEPERSONCOUNT='")
						.append(BRUISEPERSONCOUNT).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("2:" + sql);
				flag = DBHandler.execute(sql.toString());

			}

			// ֧���û�������"�ϱ�"��ĸ��´���
			if (4 == jgbh.length() && "1".equals(buttonFlag)) {
				StringBuffer sql = new StringBuffer(
						"update t_attemper_alarm_zd set EVENTSTATE='");
				sql.append("004034").append("',ALARMDESC='")
						.append(trafficInfo).append("', REPORTPERSON='")
						.append(REPORTPERSON).append("',TITLE='").append(TITLE)
						.append("',glAccNum='").append(glAccNum)
						.append("',ALARMADDRESS='").append(caseHappenPlace)
						.append("',CASEHAPPENTIME=to_date('").append(info1)
						.append("','yyyy-mm-dd hh24:mi'),")
						.append(" TELENUM='").append(TELENUM)
						.append("',RECEIVETIME=to_date('").append(receivetime)
						.append("','yyyy-mm-dd hh24:mi'), zdapprover='")
						.append(zdapprover).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("1:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer("update T_attemper_accident_zd set ");
				sql.append(" ISBUS='").append(ISBUS).append("',ISSCHOOLBUS='")
						.append(ISSCHOOLBUS).append("',ISDANAGERCAR='")
						.append(ISDANAGERCAR).append("',ISFOREIGNAFFAIRS='")
						.append(ISFOREIGNAFFAIRS).append("',ISOTHERSITEM='")
						.append(ISOthersItem).append("',ISARMYACC='")
						.append(ISARMYACC).append("',ISPOLICE='")
						.append(ISPOLICE + "',ISMASSESCASE='")
						.append(ISMASSESCASE).append("',ISCONGESTION='")
						.append(ISCONGESTION).append("',DEATHPERSONCOUNT='")
						.append(DEATHPERSONCOUNT)
						.append("',BRUISEPERSONCOUNT='")
						.append(BRUISEPERSONCOUNT).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("2:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer(
						"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON)values('");
				sql.append(getRandomId(REPORTUNIT)).append("','")
						.append(ALARMID).append("','").append(FLOW2)
						.append("',sysdate,'").append(REPORTUNIT).append("','")
						.append("004038").append("','").append("1")
						.append("','").append(REPORTPERSONXM).append("')");
				System.out.println("3:" + sql);
				flag = DBHandler.execute(sql.toString());
			}
			// �ܶ��û�������"�·�"��ĸ��´���
			if (2 == jgbh.length() && "2".equals(buttonFlag)) {
				String state = "";
				// ����ܶ�ת������
				Object adcode = res.get("adcode");
				if (adcode != null) {
					AccDeptCore core = new AccDeptCore();
					core.addAccDept(ALARMID, FlowUtil.rpadding(jgbh, "0", 10),
							adcode + "");
				}
				// ��ʾҪ�·�֧��
				if ("1".equals(saveFlag) && !buttonPressed.trim().equals("ת��")) {
					// �·�ʡ�������ͬʱת����״̬����Ϊ�ܶӴ������
					state = "004037";

					// �ܶ���ʾ����·�֧�ӵ�����ı���
					StringBuffer sqlBuffer = new StringBuffer(
							"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON )values( '");
					sqlBuffer.append(getRandomId(REPORTUNIT)).append("','")
							.append(ALARMID).append("','").append(FLOW6)
							.append("',sysdate,'").append(REPORTUNIT)
							.append("','").append("004041").append("','")
							.append("1").append("','").append(REPORTPERSONXM)
							.append("')");
					System.out.println("3:" + sqlBuffer);
					flag = DBHandler.execute(sqlBuffer.toString());

					// "1"����������ʾ��ǩ����ɡ�
				} else {
					if (buttonPressed.trim().equals("ת��")) {
						state = "004043";
					} else {
						state = "004031";
					}
				}

				StringBuffer sql = new StringBuffer(
						"update t_attemper_alarm_zd set EVENTSTATE='");
				sql.append(state).append("',RECEIVETIME=to_date('")
						.append(receivetime)
						.append("','yyyy-mm-dd hh24:mi'), zodapprover='")
						.append(zodapprover).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("1:" + sql);
				flag = DBHandler.execute(sql.toString());

				// �ܶ��쵼��ʾ�ı���
				sql = new StringBuffer(
						"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON)values('");
				sql.append(getRandomId(REPORTUNIT)).append("','")
						.append(ALARMID).append("','").append(FLOW7)
						.append("',sysdate,'").append(REPORTUNIT).append("','")
						.append("004039").append("','").append("1")
						.append("','").append(REPORTPERSONXM).append("')");
				System.out.println("3:" + sql);
				flag = DBHandler.execute(sql.toString());

				// �ܶӰ�������ı���
				sql = new StringBuffer(
						"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON)values('");
				sql.append(getRandomId(REPORTUNIT)).append("','")
						.append(ALARMID).append("','").append(FLOW8)
						.append("',sysdate,'").append(REPORTUNIT).append("','")
						.append("004040").append("','").append("1")
						.append("','").append(REPORTPERSONXM).append("')");
				System.out.println("3:" + sql);
				flag = DBHandler.execute(sql.toString());
			}

			// ֧��ת�����ϱ�
			if (jgbh.length() == 4 && "5".equals(buttonFlag)) {
				StringBuffer sql = new StringBuffer(
						"update t_attemper_alarm set ");
				sql.append("ALARMDESC='").append(trafficInfo)
						.append("', REPORTPERSON='").append(REPORTPERSON)
						.append("',TITLE='").append(TITLE)
						.append("',glAccNum='").append(glAccNum)
						.append("',ALARMADDRESS='").append(caseHappenPlace)
						.append("',CASEHAPPENTIME=to_date('").append(info1)
						.append("','yyyy-mm-dd hh24:mi'),")
						.append(" TELENUM='").append(TELENUM)
						.append("',RECEIVETIME=to_date('").append(receivetime)
						.append("','yyyy-mm-dd hh24:mi'), zdapprover='")
						.append(zdapprover).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("1:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer("update T_attemper_accident set ");
				sql.append(" ISBUS='").append(ISBUS).append("',ISSCHOOLBUS='")
						.append(ISSCHOOLBUS).append("',ISDANAGERCAR='")
						.append(ISDANAGERCAR).append("',ISFOREIGNAFFAIRS='")
						.append(ISFOREIGNAFFAIRS).append("',ISOTHERSITEM='")
						.append(ISOthersItem).append("',ISARMYACC='")
						.append(ISARMYACC).append("',ISPOLICE='")
						.append(ISPOLICE + "',ISMASSESCASE='")
						.append(ISMASSESCASE).append("',ISCONGESTION='")
						.append(ISCONGESTION).append("',DEATHPERSONCOUNT='")
						.append(DEATHPERSONCOUNT)
						.append("',BRUISEPERSONCOUNT='")
						.append(BRUISEPERSONCOUNT).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("2:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer("update t_attemper_alarm_zd set ");
				sql.append("ALARMDESC='").append(trafficInfo)
						.append("', REPORTPERSON='").append(REPORTPERSON)
						.append("',TITLE='").append(TITLE)
						.append("',glAccNum='").append(glAccNum)
						.append("',ALARMADDRESS='").append(caseHappenPlace)
						.append("',CASEHAPPENTIME=to_date('").append(info1)
						.append("','yyyy-mm-dd hh24:mi'),")
						.append(" TELENUM='").append(TELENUM)
						.append("',RECEIVETIME=to_date('").append(receivetime)
						.append("','yyyy-mm-dd hh24:mi'), zdapprover='")
						.append(zdapprover).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("3:" + sql);
				flag = DBHandler.execute(sql.toString());

				sql = new StringBuffer("update T_attemper_accident_zd set ");
				sql.append(" ISBUS='").append(ISBUS).append("',ISSCHOOLBUS='")
						.append(ISSCHOOLBUS).append("',ISDANAGERCAR='")
						.append(ISDANAGERCAR).append("',ISFOREIGNAFFAIRS='")
						.append(ISFOREIGNAFFAIRS).append("',ISOTHERSITEM='")
						.append(ISOthersItem).append("',ISARMYACC='")
						.append(ISARMYACC).append("',ISPOLICE='")
						.append(ISPOLICE + "',ISMASSESCASE='")
						.append(ISMASSESCASE).append("',ISCONGESTION='")
						.append(ISCONGESTION).append("',DEATHPERSONCOUNT='")
						.append(DEATHPERSONCOUNT)
						.append("',BRUISEPERSONCOUNT='")
						.append(BRUISEPERSONCOUNT).append("' where ALARMID='")
						.append(ALARMID).append("'");
				System.out.println("4:" + sql);
				flag = DBHandler.execute(sql.toString());

				// ����֧���������
				sql = new StringBuffer(
						"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON)values('");
				sql.append(getRandomId(REPORTUNIT)).append("','")
						.append(ALARMID).append("','").append(FLOW2)
						.append("',sysdate,'").append(REPORTUNIT).append("','")
						.append("004038").append("','").append("1")
						.append("','").append(REPORTPERSONXM).append("')");
				System.out.println("5:" + sql);
				flag = DBHandler.execute(sql.toString());

				String adcode = (String) res.get("adcode");
				String[] adcodes = adcode.split(",");

				if (adcode.indexOf("440000000000") != -1) {
					sql = new StringBuffer(
							"update t_attemper_alarm_zd set EVENTSTATE =");
					sql.append("'004034' ").append("where ALARMID='")
							.append(ALARMID).append("'");
					System.out.println("6:" + sql);
					flag = DBHandler.execute(sql.toString());

					sql = new StringBuffer(
							"select id from t_oa_acceptdept where aid ='");
					sql.append(ALARMID).append(
							"' and rpdcode='440000000000' and adid is null");
					System.out.println("7:" + sql);
					Object[] obj = DBHandler.getLineResult(sql.toString());

					// ����ѡ�����ܶӣ����ܵ�λ��Ϊ�ܶ�
					if (obj == null) {
						AccDeptDao adDao = new AccDeptDao();
						Map<String, String> adMap = new HashMap<String, String>();
						adMap.put("aid", ALARMID);
						adMap.put("atype", "1");
						adMap.put("mstate", "1");

						if (jgbh.length() == 4) {
							adMap.put("rpdcode", FlowUtil.rpadding(
									ALARMUNIT.substring(0, 2), "0", 10));
						}

						adDao.adds(adMap);
					}

				}
				//
				// for (String string : adcodes) {
				// AccDeptDao adDao = new AccDeptDao();
				// Map<String, String> adMap = new HashMap<String, String>();
				// adMap.put("aid", ALARMID);
				// adMap.put("atype", "1");
				// adMap.put("mstate", "1");
				// adMap.put("rpdcode", string);
				// adDao.adds(adMap);
				// }

				// ���뵽T_OA_content �ȴ�T_OA_ACCEPTDEPT �������id
				sql = new StringBuffer(
						"select id from t_oa_acceptdept where rpdcode = '");
				sql.append(jgbh + "00000000' and aid = '").append(ALARMID)
						.append("' and adid is null");
				System.out.println("8:" + sql);
				Object acdPk = DBHandler.getSingleResult(sql.toString());

				if (adcode.indexOf("440000000000") != -1
						&& adcode.split(",").length > 1) {
					adcode = adcode
							.substring(adcode.indexOf("440000000000") + 13);
				}

				ContentCore cc = new ContentCore();
				Map<String, String> ccMap = new HashMap<String, String>();
				ccMap.put("content", FLOW2);
				ccMap.put("spname", zbrName);
				ccMap.put("sid", (String) acdPk);
				// ID,CONTENT,APATH,SPNAME,STIME,SID
				cc.addContent(ccMap, adcode);
			}
		} catch (Exception e) {
			flag = false;
			logger.error("�༭�ش�����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ɾ���ش�����Ϣ
	 * 
	 * @author dxn
	 * @change rename: delteFlow --> delteMaterial
	 * @param ALARMID
	 * @return �Ƿ�ɾ���ɹ�
	 * @date 2010-05-17
	 */
	public boolean delteMaterialInfo(String id) {
		boolean flag = true;
		if (id != null) {
			String siftId = "alarmid='" + id + "'";
			String[] tnames = {"T_ATTEMPER_ALARM", "T_ATTEMPER_ALARM_ZD", "T_ATTEMPER_ACCIDENT", "T_ATTEMPER_ACCIDENT_ZD"};
			for (int i = 0; i < tnames.length; i++) {
				flag = FlowUtil.write(Sql.delete(tnames[i], siftId), logger, "ɾ���¹���Ϣ");
				if (flag == false) {
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * ǩ���ش�����Ϣ
	 * 
	 * @author dxn
	 * @param ALARMID
	 * @return �Ƿ�ǩ�ճɹ�
	 * @date 2010-05-17
	 */
	public boolean receivedMaterialInfo(String ALARMID, String RECIVEUNIT) {
		String sql = "update t_oa_process set OPERATEID='2',recivetime=sysdate where RECIVEUNIT='"
				+ RECIVEUNIT
				+ "' and OPERATEID='1' and YWID = '"
				+ ALARMID
				+ "'";
		return DBHandler.execute(sql);
	}

	/**
	 * ��ѯһ���ش�����Ϣ
	 * 
	 * @param bh
	 * @return
	 */
	public Object[] getMaterialInfo(String alarmId, String jgbh) {
		System.out.println("++++++++++++++++++++++++");
		Object[] obj = null;
		try {
			String para = "";
			if (jgbh.length() != 6)
				para = "_zd";
			StringBuffer sql = new StringBuffer(
					"select al.ALARMID,al.REPORTUNIT,al.REPORTPERSON,to_char");
			sql.append(
					"(al.REPORTTIME,'yyyy-mm-dd HH24:mi'),ac.ISBUS,ac.ISSCHOOLBUS,ac.ISDANAGERCAR,ac.")
					.append("ISFOREIGNAFFAIRS,ac.ISPOLICE,ac.ISMASSESCASE,ac.ISCONGESTION,ac.ISARMYACC,to_char(al.CASEHAP")
					.append("PENTIME,'yyyy-mm-dd HH24:mi'),al.ROADID,al.ROADNAME,al.KMVALUE,al.ROADDIRECTION")
					.append(",ac.DEATHPERSONCOUNT,ac.BRUISEPERSONCOUNT,al.TITLE,al.ALARMUNIT,al.ALARMREGIONID")
					.append(",al.ALARMREGION,al.EVENTSTATE,(select jgmc from t_sys_department where jgid=al")
					.append(".reportunit) as reportunit,al.reportperson,(select jgmc from t_sys_department w")
					.append("here jgid = al.reportunit) as RECIVEUNIT,(select roadname from t_oa_road_info w")
					.append("here roadid = al.ROADID) as roadname,"
							+ "al.ALARMDESC,to_char(al.ALARMTIME,'yyyy-mm-dd HH24:mi'),al.TELENUM,ac.RECEIVEPERSON "
							+ ",ac.MISSINGPERSONCOUNT ,ac.ISOTHERSITEM , al.ALARMADDRESS, al.GLACCNUM,"
							+ "(select roadlevel from t_oa_dictdlfx where dlmc=al.roadid and rownum = 1),al.roadid,al.kmvalue,al.mvalue,to_char(al.receivetime,'yyyy-mm-dd HH24:mi'),al.ddapprover,al.zdapprover,al.zodapprover,(select eventstate from t_attemper_alarm_zd zd where zd.alarmid = al.alarmid),(select jgmc from t_sys_department where jgid = GXDD) GXDD"
							+ " from t_attemper_alarm").append(para)
					.append(" al,t_at");
			if (jgbh.length() == 6) {
				sql.append("temper_accident")
						.append(para)
						.append(" ac where al.ALARMID=ac.ALARMID and al.ALARMID='")
						.append(alarmId).append("'");
			} else if (jgbh.length() == 4) {
				sql.append("temper_accident")
						.append(para)
						.append(" ac where al.ALARMID=ac.ALARMID and al.ALARMID='")
						.append(alarmId).append("'");
			} else if (jgbh.length() == 2) {
				sql.append("temper_accident")
						.append(para)
						.append(" ac where al.ALARMID=ac.ALARMID and al.ALARMID='")
						.append(alarmId).append("'");
			}
			System.out.println("getMaterialInfo.sql==********" + sql);
			obj = DBHandler.getLineResult(sql.toString());
		} catch (Exception e) {
			logger.error("��ѯһ���ش�����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	public Object[] getMaterialState(String alarmId) {
		Object[] obj = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select eventstate from t_attemper_alarm_zd where alarmid = '");
			sql.append(alarmId).append("'");
			System.out.println("getMaterialState.sql==>" + sql);
			obj = DBHandler.getLineResult(sql.toString());
		} catch (Exception e) {
			logger.error("��ѯһ���ش�����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	public Object[] getMaterialInfo0(String alarmId) {
		Object[] obj = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select al.ALARMID,al.REPORTUNIT,al.REPORTPERSON,to_char");
			sql.append(
					"(al.REPORTTIME,'yyyy-mm-dd HH24:mi'),ac.ISBUS,ac.ISSCHOOLBUS,ac.ISDANAGERCAR,ac.")
					.append("ISFOREIGNAFFAIRS,ac.ISPOLICE,ac.ISMASSESCASE,ac.ISCONGESTION,ac.ISARMYACC,to_char(al.CASEHAP")
					.append("PENTIME,'yyyy-mm-dd HH24:mi'),al.ROADID,al.ROADNAME,al.KMVALUE,al.ROADDIRECTION")
					.append(",ac.DEATHPERSONCOUNT,ac.BRUISEPERSONCOUNT,al.TITLE,al.ALARMUNIT,al.ALARMREGIONID")
					.append(",al.ALARMREGION,al.EVENTSTATE,(select jgmc from t_sys_department where jgid=al")
					.append(".reportunit) as reportunit,al.reportperson,(select jgmc from t_sys_department w")
					.append("here jgid = op.RECIVEUNIT) as RECIVEUNIT,(select roadname from t_oa_road_info w")
					.append("here roadid = al.ROADID) as roadname,op.RESPONSIBLEPERSON,op.operateid,(select p.OPERATEID from T_OA_PROCESS p where p.ywid = al.alarmid and p.reportkind = '004033'),ac.RECEIVEPERSON from t_attemper_alarm al,t_at")
					.append("temper_accident ac, t_oa_process op where al.ALARMID=ac.ALARMID and al.ALARMID = op.ywid and op.reportkind = '004032'  and al.ALARMID='")
					.append(alarmId).append("'");
			System.out.println("getMaterialInfo.sql==>" + sql);
			obj = DBHandler.getLineResult(sql.toString());
		} catch (Exception e) {
			logger.error("��ѯһ���ش�����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * ��ѯ�ش��齻����Ϣ
	 * 
	 * @param bh
	 * @return
	 */
	public Object[][] getAlarmFlow(String alarmId) {
		Object[][] obj = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select OPID,REPORTTEXT,to_char(REPORTTIME,'yyyy-mm-dd hh24:mi'),(select jgmc from t_sys_department where jgid = REPORTUNIT),REPORTKIND,OPERATEID,REPORTPERSON");
			sql.append(
					" from t_oa_process where REPORTKIND in ('004038','004039','004040','004041') and YWID='")
					.append(alarmId).append("' order by REPORTTIME");
			System.out.println("getAlarmFlow.sql==>" + sql);
			obj = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("��ѯ�����ش�����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * �����ش���ظ���Ϣ
	 * 
	 * @param res
	 * @return
	 */
	public boolean addFlow(HashMap res) {
		boolean flag = false;
		try {
			String ALARMID = res.get("ALARMID").toString();
			String RECIVEUNIT = res.get("RECIVEUNIT").toString();
			String REPORTKIND = res.get("REPORTKIND").toString();
			String RESPONSIBLEPERSON = res.get("RESPONSIBLEPERSON").toString();
			String REPORTPERSON = res.get("REPORTPERSON").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();
			String REPORTTEXT = res.get("REPORTTEXT").toString();
			String OPERATEID = res.get("OPERATEID").toString();

			String OPID = getRandomId(REPORTUNIT);

			StringBuffer sql = new StringBuffer(
					"insert into t_oa_process(OPID,YWID,YWLX,RECIVEUNIT,REPORT");
			sql.append(
					"KIND,RESPONSIBLEPERSON,REPORTPERSON,REPORTUNIT,REPORTTIME,REPORTTEXT,OPERATEID) ")
					.append("values('").append(OPID).append("','")
					.append(ALARMID).append("','����','").append(RECIVEUNIT)
					.append("','").append(REPORTKIND).append("','")
					.append(RESPONSIBLEPERSON).append("','")
					.append(REPORTPERSON).append("','").append(REPORTUNIT)
					.append("',to_date('").append(REPORTTIME)
					.append("','yyyy-mm-dd hh24:mi'),'").append(REPORTTEXT)
					.append("','").append(OPERATEID).append("')");
			System.out.println("addFlow.sql:" + sql);
			flag = DBHandler.execute(sql.toString());
		} catch (Exception e) {
			flag = false;
			logger.error("�����ش���ظ���Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * �༭�ش���ظ���Ϣ
	 * 
	 * @param res
	 * @return
	 */
	public boolean editFlow(HashMap res) {
		boolean flag = false;
		try {
			String OPID = res.get("OPID").toString();
			String RECIVEUNIT = res.get("RECIVEUNIT").toString();
			String REPORTKIND = res.get("REPORTKIND").toString();
			String RESPONSIBLEPERSON = res.get("RESPONSIBLEPERSON").toString();
			String REPORTPERSON = res.get("REPORTPERSON").toString();
			String REPORTUNIT = res.get("REPORTUNIT").toString();
			String REPORTTIME = res.get("REPORTTIME").toString();
			String REPORTTEXT = res.get("REPORTTEXT").toString();
			String OPERATEID = res.get("OPERATEID").toString();

			StringBuffer sql = new StringBuffer(
					"update into t_oa_process set RECIVEUNIT='");
			sql.append(RECIVEUNIT).append("',REPORTKIND='").append(REPORTKIND)
					.append("',RESPONSIBLEPERSON='").append(RESPONSIBLEPERSON)
					.append("',REPORTPERSON='").append(REPORTPERSON)
					.append("',REPORTUNIT='").append(REPORTUNIT)
					.append("',REPORTTIME=").append("to_date('")
					.append(REPORTTIME)
					.append("','yyyy-mm-dd hh24:mi'),REPORTTEXT='")
					.append(REPORTTEXT).append("',OPERATEID='")
					.append(OPERATEID).append("' where OPID='").append(OPID)
					.append("'");
			System.out.println("editFlow.sql:" + sql);
			flag = DBHandler.execute(sql.toString());

		} catch (Exception e) {
			flag = false;
			logger.error("�༭ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ��ѯ�¹ʳ�����Ϣ
	 * 
	 * @param alarmId
	 * @return
	 */
	public Object[][] getAccidentCar(String alarmId) {
		String sql = null;
		Object[][] obj = null;
		try {
			sql = "select CARID, ALARMID, (select dmsm from t_sys_code where dmlb = '011001' and dm = HPZL), HPZM from T_ATTEMPER_ACCCAR where ALARMID='"
					+ alarmId + "'";
			System.out.println("getMaterialInfo.sql==>" + sql);
			obj = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("��ѯ�¹ʳ�����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * �����¹ʳ�����Ϣ
	 * 
	 * @param bh
	 * @return
	 */
	public boolean addAccidentCar(String[] args) {
		boolean flag = false;
		try {
			StringBuffer sql = new StringBuffer(
					"insert into T_ATTEMPER_ACCCAR(carid,alarmid,hpzl,hpzm)");
			sql.append("values('").append(args[0]).append("','")
					.append(args[1]).append("','").append(args[2])
					.append("','").append(args[3]).append("')");
			flag = DBHandler.execute(sql.toString());
		} catch (Exception e) {
			logger.error("�����¹ʳ�����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ɾ���¹ʳ�����Ϣ
	 * 
	 * @param bh
	 * @return
	 */
	public boolean deleteAccidentCar(String carId) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "delete from T_ATTEMPER_ACCCAR where CARID='" + carId + "'";
			flag = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("ɾ���¹ʳ�����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ��ѯ������Ա��Ϣ
	 * 
	 * @param bh
	 * @return
	 */
	public Object[][] getAccidentForeigner(String alarmId) {
		String sql = null;
		Object[][] obj = null;
		try {
			sql = "select AFID, ALARMID, AFXM, AFGJ from t_oa_accidentforeigner where ALARMID='"
					+ alarmId + "'";
			System.out.println("getMaterialInfo.sql==>" + sql);
			obj = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("��ѯһ��ӵ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * ����������Ա��Ϣ
	 * 
	 * @param args
	 * @return
	 */
	public boolean addAccidentForeigner(String[] args) {
		boolean flag = false;
		try {
			StringBuffer sql = new StringBuffer(
					"insert into t_oa_accidentforeigner(afid,alarmid,afxm,afgj)");
			sql.append("values('").append(args[0]).append("','")
					.append(args[1]).append("','").append(args[2])
					.append("','").append(args[3]).append("')");
			flag = DBHandler.execute(sql.toString());
		} catch (Exception e) {
			logger.error("����������Ա��Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ɾ��������Ա��Ϣ
	 * 
	 * @param afId
	 * @return
	 */
	public boolean deleteAccidentForeigner(String afId) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "delete from t_oa_accidentforeigner where afid='" + afId
					+ "'";
			flag = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("ɾ��������Ա��Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public static String getRandomId(String jgId) {
		return (jgId.substring(0, 6)
				+ StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS")
				+ String.valueOf(Math.random()).substring(2) + String.valueOf(
				Math.random()).substring(2)).substring(0, 50);
	}

	public Object[][] getFlapperType() {
		String sql = null;
		Object[][] obj = null;
		try {
			sql = "select dm,dmsm from t_sys_code where dmlb = '011001' order by dm";
			System.out.println("getFlapperType.sql==>" + sql);
			obj = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("��ѯ����������Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * ȡ��������Ϣ<br/>
	 * ������Ϣ��ȡ�ô���
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[] getPromptCount(String reciveunit) {

		String jgbh;// �ܶ�(2λ),֧��(4λ),���(6λ)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// ֧�ӵ�¼�û�ʱ�����Ĵ���
		if (jgbh.length() == 4) {
			sql.append("select distinct count(1) from  T_ATTEMPER_ALARM_ZD a1 where a1.eventstate in ('004033','004037') and a1.eventtype = '001024' and a1.EVENTSOURCE='002022'  and subStr(a1.ACCEPTUNIT,0,4) = '"
					+ jgbh + "'");
			// �ܶӵ�¼�û�ʱ�����Ĵ���
		} else if (jgbh.length() == 2) {
			sql.append("select distinct count(1) from  T_ATTEMPER_ALARM_ZD a1 where a1.eventstate in ('004034') and a1.eventtype = '001024' and a1.EVENTSOURCE='002022' ");
			// ���ʱ�����Ĵ���
		} else if (jgbh.length() == 6) {
			sql.append("select distinct count(1) from  T_ATTEMPER_ALARM a1 where a1.eventtype = '001345'  and a1.EVENTSOURCE='007722' ");
		}

		logger.info("getPromptCount==>" + sql);
		return DBHandler.getLineResult(sql.toString());
	}

	/**
	 * ������ϸ��Ϣ��ȡ��<br/z>
	 * ������ϸ��Ϣ��ȡ�ô���
	 * 
	 * @param reportkind
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getPromptInfo(String reportkind, String reciveunit) {

		String jgbh;// �ܶ�(2λ),֧��(4λ),���(6λ)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// ֧�ӵ�¼�û�ʱ�����Ĵ���
		if (jgbh.length() == 4) {
			sql.append(" select a1.ALARMID ,a1.ALARMREGION, a1.title, ");
			sql.append(" a1.REPORTPERSON, ");
			sql.append(" a1.ALARMTIME, ");
			sql.append(" (select name from t_attemper_code where id=a1.EVENTSTATE) , a1.EVENTSTATE ");
			sql.append(" from");
			sql.append(" T_ATTEMPER_ALARM_ZD a1, ");
			sql.append(" T_ATTEMPER_ACCIDENT a2 ");
			sql.append(" where ");
			sql.append(" a2.ALARMID = a1.alarmid  and a1.EVENTSOURCE='002022' ");
			sql.append(" and a1.eventstate in ('004033', '004037') ");
			// sql.append(" and rownum<=10 ");
			sql.append(" and subStr(a1.ACCEPTUNIT,0,4) = '" + jgbh + "' ");
			sql.append(" and a1.eventtype = '001024'  order by a2.RECEIVETIME desc");

			// �ܶӵ�¼�û�ʱ�����Ĵ���
		} else if (jgbh.length() == 2) {
			sql.append(" select a1.ALARMID,a1.ALARMREGION,a1.title, ");
			sql.append(" a2.RECEIVEPERSON, ");
			sql.append(" a2.RECEIVETIME, ");
			sql.append(" (select name from t_attemper_code where id=a1.EVENTSTATE), a1.EVENTSTATE");
			sql.append(" from ");
			sql.append(" T_ATTEMPER_ALARM_ZD a1, ");
			sql.append(" T_ATTEMPER_ACCIDENT a2 ");
			sql.append(" where ");
			sql.append(" a2.ALARMID = a1.alarmid  and a1.EVENTSOURCE='002022' ");
			sql.append(" and a1.eventstate in ('004034') ");
			// sql.append(" and rownum<=10 ");
			sql.append(" and a1.eventtype = '001024'  order by a2.RECEIVETIME desc ");
			// ��ӵ�¼�û�ʱ�����Ĵ���
		} else if (jgbh.length() == 6) {
			sql.append("select distinct count(1) from t_oa_process op, T_ATTEMPER_ALARM_ZD a1 where op.ywid = a1.alarmid and a1.eventstate in ('004032', '004038') and op.ywlx in ('�������') and a1.eventtype = '001024'");
		}
		System.out.println("getPromptInfo.sql==>" + sql);
		Object[][] result = DBHandler.getMultiResult(sql.toString());
		return result;
	}

	public Object[][] getAlarmAttachment(String alarmId) {
		StringBuffer sql = new StringBuffer(
				"select fjid,fjmc,fjwz,fjms,ywid from t_oa_attachment where ywid='")
				.append(alarmId).append("' order by fjid");
		System.out.println("getAlarmAttachment.sql==>" + sql);
		Object[][] result = DBHandler.getMultiResult(sql.toString());
		return result;
	}

	public boolean deleteFile(String fjid) {
		String sql = null;
		boolean flag = false;
		try {
			sql = "select fjwz from t_oa_attachment where fjid='" + fjid + "'";
			String fjwz = Jspsmart.getPath() + DBHandler.getSingleResult(sql);
			File file = new File(fjwz);
			if (fjwz != null && file.exists()) {
				file.delete();
			}
			sql = "delete from t_oa_attachment where fjid='" + fjid + "'";
			flag = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("ɾ��������Ա��Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public Object[][] getNewXbInfo(String alarmId) {
		StringBuffer sql = new StringBuffer(
				"select t1.ID,t1.REPORTPERSON,to_char(REPORTTIME,'yyyy-mm-dd hh24:mi'),t1.REPORTINFO,t1.DEPARTUNIT,t1.fjid,t2.fjwz from T_OA_ALARM_XB t1,t_Oa_Attachment t2 where ID='")
				.append(alarmId)
				.append("' and t1.fjid = t2.fjid union select t1.ID, t1.REPORTPERSON, to_char(REPORTTIME, 'yyyy-mm-dd hh24:mi'), t1.REPORTINFO, t1.DEPARTUNIT, t1.fjid, t1.fjid from T_OA_ALARM_XB t1  where t1.ID = '")
				.append(alarmId).append("' and t1.fjid is null order by 3 asc");
		System.out.println("getNewXbInfo.sql==>" + sql);
		Object[][] result = DBHandler.getMultiResult(sql.toString());
		return result;
	}

	/**
	 * ȡ��������ʾ��Ϣ<br>
	 * 
	 * @param alarmId
	 * @return
	 */
	public Object[] getFlowInitInfo(String alarmId, String jgbh) {
		String sql = "";
		Object[] result = null;
		if (jgbh != null && !jgbh.equals("")) {
			if (jgbh.length() == 6) {

				sql = "select ALARMDESC,TITLE from T_ATTEMPER_ALARM where ALARMID = '"
						+ alarmId + "'";
				result = DBHandler.getLineResult(sql);
			} else {
				sql = "select ALARMDESC,TITLE from T_ATTEMPER_ALARM_ZD where ALARMID = '"
						+ alarmId + "'";
				result = DBHandler.getLineResult(sql);
			}
		}
		return result;

	}

	/**
	 * ȡ������ͨ����Ϣ����<br/>
	 * ����ͨ����Ϣ������ȡ�ô���
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[] getNoticeCount(String reciveunit) {

		String jgbh;// �ܶ�(2λ),֧��(4λ),���(6λ)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// ֧�ӵ�¼�û�ʱ�����Ĵ���
		if (jgbh.length() == 4) {
			sql.append("select count(1) from T_OA_NOTICE where VIEWSTATE = '0' ");
			// sql.append(reciveunit);
			// sql.append("'");
		}
		System.out.println("getNoticeCount==>" + sql);
		return DBHandler.getLineResult(sql.toString());
	}

	/**
	 * ����ͨ����ϸ��Ϣ��ȡ��<br/z>
	 * ����ͨ����ϸ��Ϣ��ȡ�ô���
	 * 
	 * @param reportkind
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getNoticeInfo(String reportkind, String reciveunit) {

		String jgbh;// �ܶ�(2λ),֧��(4λ),���(6λ)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// ֧�ӵ�¼�û�ʱ�����Ĵ���
		if (jgbh.length() == 4) {
			sql.append("select NOTICEID,NOTICEID,SENDTIME,SENDUNIT,SENDPEOPLE,RECEIVEUNIT,RECEIVEPEOPLE from T_OA_NOTICE where VIEWSTATE = '0' ");
			// sql.append(reciveunit);
			// sql.append("'");
		}
		System.out.println("getNoticeInfo.sql==>" + sql);
		Object[][] result = DBHandler.getMultiResult(sql.toString());
		return result;
	}

	/**
	 * ȡ������������Ϣ����<br/>
	 * ����������Ϣ������ȡ�ô���
	 * 
	 * @param reciveunit
	 * @return
	 */
	public int getPromptXBCount(String reciveunit) {

		String jgbh;// �ܶ�(2λ),֧��(4λ),���(6λ)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		Object[] obj = null;
		// ֧�ӵ�¼�û�ʱ�����Ĵ���
		if (jgbh.length() == 4) {
			sql.append("select DISTINCT B.ALARMID from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B ");
			sql.append(" where A.ID = B.ALARMID and A.Xbflg = '0' and B.EVENTSTATE in ('004033','004037','004034','004035','004036','004042','004043','004031') ");
			obj = DBHandler.getMultiResult(sql.toString());
			if (obj != null) {
				return obj.length;
			}
			// �ܶӵ�¼�û�ʱ�����Ĵ���
		} else if (jgbh.length() == 2) {
			sql.append("select DISTINCT B.ALARMID from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B ");
			sql.append(" where A.ID = B.ALARMID and A.ZDXBFLG = '0'  and B.EVENTSTATE in ('004034', '004035', '004036', '004037', '004043','004031') ");
			obj = DBHandler.getMultiResult(sql.toString());
			if (obj != null) {
				return obj.length;
			}
			// ���ʱ�����Ĵ���
		} else if (jgbh.length() == 6) {
			return 0;
		}

		logger.info("getPromptXBCount==>" + sql);
		return 0;
	}

	/**
	 * ������ϸ��Ϣ��ȡ��<br/z>
	 * ������ϸ��Ϣ��ȡ�ô���
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getPromptXBInfo(String reciveunit) {

		String jgbh;// �ܶ�(2λ),֧��(4λ),���(6λ)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		Object[][] result = null;
		// ֧�ӵ�¼�û�ʱ�����Ĵ���
		if (jgbh.length() == 4) {
			sql.append(" select DISTINCT B.ALARMID,B.ALARMREGION,B.TITLE,B.REPORTPERSON,B.REPORTTIME,(select name from t_attemper_code where id=B.EVENTSTATE), B.EVENTSTATE,'֧��'");
			sql.append(" from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B where A.ID = B.ALARMID and A.Xbflg = '0' and B.EVENTSTATE in ('004033','004037','004034','004035','004036','004042','004043','004031') ");
			// sql.append(" and rownum<=10 ");
			result = DBHandler.getMultiResult(sql.toString());
			// �ܶӵ�¼�û�ʱ�����Ĵ���
		} else if (jgbh.length() == 2) {
			sql.append(" select DISTINCT B.ALARMID,B.ALARMREGION,B.TITLE,B.REPORTPERSON,B.REPORTTIME,(select name from t_attemper_code where id=B.EVENTSTATE), B.EVENTSTATE,'�ܶ�'");
			sql.append(" from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B where A.ID = B.ALARMID and A.zdXbflg = '0'   and B.EVENTSTATE in ('004034', '004035', '004036', '004037', '004043','004031') ");
			// sql.append(" and rownum<=10 ");
			result = DBHandler.getMultiResult(sql.toString());
			// ��ӵ�¼�û�ʱ�����Ĵ���
		} else if (jgbh.length() == 6) {
			sql.append("select distinct OPID,XBBT,PERSONWRITE,TELEPHONE,XBSTATE,YWID from t_oa_process op, T_ATTEMPER_ALARM_ZD a1 where op.ywid = a1.alarmid and a1.eventstate in ('004099') and op.ywlx in ('�������') and a1.eventtype = '001024'");
			result = DBHandler.getMultiResult(sql.toString());
		}
		System.out.println("getPromptInfo.sql==>" + sql);
		return result;
	}

	/**
	 * ��ȡ�¹�ͨ������
	 * 
	 * @param colStr
	 * @param udcode
	 * @return
	 */
	public Object[][] getPromptTb(String colStr, String udcode) {
		// '���λ','�¹ʱ���','���','�ʱ��','״̬']
		Object[][] data = null;
		if (colStr != null && udcode != null) {
			String tname = "t_attemper_alarm_zd al, t_oa_acceptdept ad";
			String whereStr = "al.alarmid = ad.aid and ad.adid is not null and ad.state = '1' and ad.rpdcode !='440000000000' and ad.rpdcode='"
					+ udcode + "' ";
			String sql = "select " + colStr + " from " + tname + " where "
					+ whereStr + " order by al.reporttime";
			data = DBHandler.getMultiResult(sql);
		}
		return data;
	}

	/**
	 * ͨ����ˮ��ȡ��һ��������Ϣ<br/z>
	 * ͨ����ˮ��ȡ��һ��������Ϣ
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getXBInfoByOpid(String searchOpid) {

		StringBuffer sql = new StringBuffer();
		Object[][] result = null;
		sql.append(" select OPID,YWID,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,a1.EVENTSTATE from t_oa_process ,T_ATTEMPER_ALARM_ZD a1");
		sql.append(" where OPID ='").append(searchOpid)
				.append("' and a1.alarmid = YWID");
		result = DBHandler.getMultiResult(sql.toString());
		System.out.println("getXBInfoByOpid.sql==>" + sql);
		return result;
	}

}

// 2011-08-12���£�������