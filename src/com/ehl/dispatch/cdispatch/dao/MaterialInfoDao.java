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
 * 道路管理数据类
 * 
 * @author wangxt
 * @date 2009-1-16
 * 
 */
public class MaterialInfoDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);

	/**
	 * 大队新增
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

			// trafficInfo取得事故详细描述
			String trafficInfo = res.get("trafficInfo").toString();
			// 取得事故发生时间
			String info1 = res.get("caseHappenTime").toString();
			// 取得事故发生地点
			String caseHappenPlace = res.get("caseHappenPlace").toString();
			// 取得事故受伤人数
			String info15 = res.get("info15").toString();
			// 取得事故接警人
			String RECEIVEPERSON = res.get("RECEIVEPERSON").toString();
			String SUPERUNIT = DepartmentUtil.getSuperUnit(REPORTUNIT);

			// 2010年7月19日追加 "其他","失踪人数","上报电话"
			String ISOthersItem = res.get("ISOthersItem").toString();
			String MISSINGPERSONCOUNT = res.get("MISSINGPERSONCOUNT")
					.toString();
			String TELNUMSB = res.get("TELNUMSB").toString();
			String ACCEPTUNIT = res.get("jgid_Zd").toString();

			if ("0".equals(buttonFlag)) {
				// 大队保存时状态的设定
				FLOWKIND1 = "004032";
			} else {
				// 大队上报时状态的设定
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
			logger.error("新增重大警情信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 大队编辑警情信息
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

			// trafficInfo取得事故详细描述
			String trafficInfo = res.get("trafficInfo").toString();

			// 取得事故发生时间
			String info1 = res.get("caseHappenTime").toString();
			// 取得事故发生地点
			String caseHappenPlace = res.get("caseHappenPlace").toString();
			// 取得事故受伤人数
			String info15 = res.get("info15").toString();
			// 取得事故接警人
			String RECEIVEPERSON = res.get("RECEIVEPERSON").toString();
			// 审核人
			String ddapprover = res.get("ddapprover").toString();
			String zdapprover = res.get("zdapprover").toString();
			String zodapprover = res.get("zodapprover").toString();
			// 接警时间
			String receivetime = res.get("receivetime").toString();
			// buttonFlag("0"保存,"1"上报)警情id，上报人，联系电话
			String buttonFlag = res.get("buttonFlag").toString();
			// 机构编号2位：总队4位：支队6位：大队
			String jgbh = res.get("jgbh").toString();
			String saveFlag = res.get("saveFlag").toString();
			String reportunitId = res.get("reportunitId").toString();

			// 2010年7月19日追加 "其他","失踪人数","上报电话"
			String ISOthersItem = res.get("ISOthersItem").toString();
			String MISSINGPERSONCOUNT = res.get("MISSINGPERSONCOUNT")
					.toString();
			String TELNUMSB = res.get("TELNUMSB").toString();
			// 接收单位id
			String ACCEPTUNIT = res.get("jgid_Zd").toString();

			// 关联事故编号
			String glAccNum = res.get("glAccNum").toString();
			// 涉军交通事故
			String ISARMYACC = res.get("ISARMYACC").toString();

			// 大队用户进入点击"上报"后的更新处理
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

			// 支队用户进入点击"上报"后的更新处理
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
			// 总队用户进入点击"下发"后的更新处理
			if (2 == jgbh.length() && "2".equals(buttonFlag)) {
				String state = "";
				// 添加总队转发机构
				Object adcode = res.get("adcode");
				if (adcode != null) {
					AccDeptCore core = new AccDeptCore();
					core.addAccDept(ALARMID, FlowUtil.rpadding(jgbh, "0", 10),
							adcode + "");
				}
				// 表示要下发支队
				if ("1".equals(saveFlag) && !buttonPressed.trim().equals("转发")) {
					// 下发省厅意见，同时转发，状态设置为总队处理完成
					state = "004037";

					// 总队批示意见下发支队的意见的保存
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

					// "1"以外的情况表示”签收完成“
				} else {
					if (buttonPressed.trim().equals("转发")) {
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

				// 总队领导批示的保存
				sql = new StringBuffer(
						"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON)values('");
				sql.append(getRandomId(REPORTUNIT)).append("','")
						.append(ALARMID).append("','").append(FLOW7)
						.append("',sysdate,'").append(REPORTUNIT).append("','")
						.append("004039").append("','").append("1")
						.append("','").append(REPORTPERSONXM).append("')");
				System.out.println("3:" + sql);
				flag = DBHandler.execute(sql.toString());

				// 总队办理情况的保存
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

			// 支队转发或上报
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

				// 插入支警处理情况
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

					// 假如选择了总队，接受单位改为总队
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

				// 插入到T_OA_content 先从T_OA_ACCEPTDEPT 里的主键id
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
			logger.error("编辑重大警情信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除重大警情信息
	 * 
	 * @author dxn
	 * @change rename: delteFlow --> delteMaterial
	 * @param ALARMID
	 * @return 是否删除成功
	 * @date 2010-05-17
	 */
	public boolean delteMaterialInfo(String id) {
		boolean flag = true;
		if (id != null) {
			String siftId = "alarmid='" + id + "'";
			String[] tnames = {"T_ATTEMPER_ALARM", "T_ATTEMPER_ALARM_ZD", "T_ATTEMPER_ACCIDENT", "T_ATTEMPER_ACCIDENT_ZD"};
			for (int i = 0; i < tnames.length; i++) {
				flag = FlowUtil.write(Sql.delete(tnames[i], siftId), logger, "删除事故信息");
				if (flag == false) {
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 签收重大警情信息
	 * 
	 * @author dxn
	 * @param ALARMID
	 * @return 是否签收成功
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
	 * 查询一条重大警情信息
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
			logger.error("查询一条重大警情信息:");
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
			logger.error("查询一条重大警情信息:");
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
			logger.error("查询一条重大警情信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 查询重大警情交互信息
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
			logger.error("查询所有重大警情信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 新增重大警情回复信息
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
					.append(ALARMID).append("','警情','").append(RECIVEUNIT)
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
			logger.error("新增重大警情回复信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 编辑重大警情回复信息
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
			logger.error("编辑拥堵信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查询事故车辆信息
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
			logger.error("查询事故车辆信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 新增事故车辆信息
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
			logger.error("新增事故车辆信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除事故车辆信息
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
			logger.error("删除事故车辆信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查询涉外人员信息
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
			logger.error("查询一条拥堵信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 新增涉外人员信息
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
			logger.error("新增涉外人员信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除涉外人员信息
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
			logger.error("删除涉外人员信息出错:");
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
			logger.error("查询号牌种类信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 取得提醒信息<br/>
	 * 提醒信息的取得处理
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[] getPromptCount(String reciveunit) {

		String jgbh;// 总队(2位),支队(4位),大队(6位)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// 支队登录用户时检索的处理
		if (jgbh.length() == 4) {
			sql.append("select distinct count(1) from  T_ATTEMPER_ALARM_ZD a1 where a1.eventstate in ('004033','004037') and a1.eventtype = '001024' and a1.EVENTSOURCE='002022'  and subStr(a1.ACCEPTUNIT,0,4) = '"
					+ jgbh + "'");
			// 总队登录用户时检索的处理
		} else if (jgbh.length() == 2) {
			sql.append("select distinct count(1) from  T_ATTEMPER_ALARM_ZD a1 where a1.eventstate in ('004034') and a1.eventtype = '001024' and a1.EVENTSOURCE='002022' ");
			// 大队时检索的处理
		} else if (jgbh.length() == 6) {
			sql.append("select distinct count(1) from  T_ATTEMPER_ALARM a1 where a1.eventtype = '001345'  and a1.EVENTSOURCE='007722' ");
		}

		logger.info("getPromptCount==>" + sql);
		return DBHandler.getLineResult(sql.toString());
	}

	/**
	 * 提醒详细信息的取得<br/z>
	 * 提醒详细信息的取得处理
	 * 
	 * @param reportkind
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getPromptInfo(String reportkind, String reciveunit) {

		String jgbh;// 总队(2位),支队(4位),大队(6位)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// 支队登录用户时检索的处理
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

			// 总队登录用户时检索的处理
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
			// 大队登录用户时检索的处理
		} else if (jgbh.length() == 6) {
			sql.append("select distinct count(1) from t_oa_process op, T_ATTEMPER_ALARM_ZD a1 where op.ywid = a1.alarmid and a1.eventstate in ('004032', '004038') and op.ywlx in ('大队提醒') and a1.eventtype = '001024'");
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
			logger.error("删除涉外人员信息出错:");
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
	 * 取得续报显示信息<br>
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
	 * 取得提醒通告信息件数<br/>
	 * 提醒通告信息件数的取得处理
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[] getNoticeCount(String reciveunit) {

		String jgbh;// 总队(2位),支队(4位),大队(6位)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// 支队登录用户时检索的处理
		if (jgbh.length() == 4) {
			sql.append("select count(1) from T_OA_NOTICE where VIEWSTATE = '0' ");
			// sql.append(reciveunit);
			// sql.append("'");
		}
		System.out.println("getNoticeCount==>" + sql);
		return DBHandler.getLineResult(sql.toString());
	}

	/**
	 * 提醒通告详细信息的取得<br/z>
	 * 提醒通告详细信息的取得处理
	 * 
	 * @param reportkind
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getNoticeInfo(String reportkind, String reciveunit) {

		String jgbh;// 总队(2位),支队(4位),大队(6位)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		// 支队登录用户时检索的处理
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
	 * 取得提醒续报信息件数<br/>
	 * 提醒续报信息件数的取得处理
	 * 
	 * @param reciveunit
	 * @return
	 */
	public int getPromptXBCount(String reciveunit) {

		String jgbh;// 总队(2位),支队(4位),大队(6位)
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
		// 支队登录用户时检索的处理
		if (jgbh.length() == 4) {
			sql.append("select DISTINCT B.ALARMID from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B ");
			sql.append(" where A.ID = B.ALARMID and A.Xbflg = '0' and B.EVENTSTATE in ('004033','004037','004034','004035','004036','004042','004043','004031') ");
			obj = DBHandler.getMultiResult(sql.toString());
			if (obj != null) {
				return obj.length;
			}
			// 总队登录用户时检索的处理
		} else if (jgbh.length() == 2) {
			sql.append("select DISTINCT B.ALARMID from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B ");
			sql.append(" where A.ID = B.ALARMID and A.ZDXBFLG = '0'  and B.EVENTSTATE in ('004034', '004035', '004036', '004037', '004043','004031') ");
			obj = DBHandler.getMultiResult(sql.toString());
			if (obj != null) {
				return obj.length;
			}
			// 大队时检索的处理
		} else if (jgbh.length() == 6) {
			return 0;
		}

		logger.info("getPromptXBCount==>" + sql);
		return 0;
	}

	/**
	 * 提醒详细信息的取得<br/z>
	 * 提醒详细信息的取得处理
	 * 
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getPromptXBInfo(String reciveunit) {

		String jgbh;// 总队(2位),支队(4位),大队(6位)
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
		// 支队登录用户时检索的处理
		if (jgbh.length() == 4) {
			sql.append(" select DISTINCT B.ALARMID,B.ALARMREGION,B.TITLE,B.REPORTPERSON,B.REPORTTIME,(select name from t_attemper_code where id=B.EVENTSTATE), B.EVENTSTATE,'支队'");
			sql.append(" from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B where A.ID = B.ALARMID and A.Xbflg = '0' and B.EVENTSTATE in ('004033','004037','004034','004035','004036','004042','004043','004031') ");
			// sql.append(" and rownum<=10 ");
			result = DBHandler.getMultiResult(sql.toString());
			// 总队登录用户时检索的处理
		} else if (jgbh.length() == 2) {
			sql.append(" select DISTINCT B.ALARMID,B.ALARMREGION,B.TITLE,B.REPORTPERSON,B.REPORTTIME,(select name from t_attemper_code where id=B.EVENTSTATE), B.EVENTSTATE,'总队'");
			sql.append(" from T_OA_ALARM_XB A, T_ATTEMPER_ALARM_ZD B where A.ID = B.ALARMID and A.zdXbflg = '0'   and B.EVENTSTATE in ('004034', '004035', '004036', '004037', '004043','004031') ");
			// sql.append(" and rownum<=10 ");
			result = DBHandler.getMultiResult(sql.toString());
			// 大队登录用户时检索的处理
		} else if (jgbh.length() == 6) {
			sql.append("select distinct OPID,XBBT,PERSONWRITE,TELEPHONE,XBSTATE,YWID from t_oa_process op, T_ATTEMPER_ALARM_ZD a1 where op.ywid = a1.alarmid and a1.eventstate in ('004099') and op.ywlx in ('大队提醒') and a1.eventtype = '001024'");
			result = DBHandler.getMultiResult(sql.toString());
		}
		System.out.println("getPromptInfo.sql==>" + sql);
		return result;
	}

	/**
	 * 获取事故通报提醒
	 * 
	 * @param colStr
	 * @param udcode
	 * @return
	 */
	public Object[][] getPromptTb(String colStr, String udcode) {
		// '填报单位','事故标题','填报人','填报时间','状态']
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
	 * 通过流水号取得一条续报信息<br/z>
	 * 通过流水号取得一条续报信息
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

// 2011-08-12更新，雷适兴