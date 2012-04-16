/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.dispatch.cdispatch.dao.CrowdManageDao;
import com.ehl.dispatch.cdispatch.dao.TrafficNewsFeedsDao;
import com.ehl.dispatch.common.AlarmInfoJoin;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dynamicinfo.policeRemind.core.PoliceRemindCore;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.common.util.StringUtil;

/**
 * 重大警情控制类
 * 
 * @author dxn
 * @date 2010-05-13
 * 
 */
public class alarmInfoActoin extends Controller {
	
	private PoliceRemindCore prcore = new PoliceRemindCore();
	/**
	 * 编辑重大警情信息
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 * 
	 */
	public ActionForward doInsertAlarmInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"), "");
		String insertOrUpdate = StringHelper.obj2str(request
				.getParameter("flg"), "");
		String ISTHREEUP = StringHelper.obj2str(request
				.getParameter("ISTHREEUP"), "");
		String ISBUS = StringHelper.obj2str(request.getParameter("ISBUS"), "");
		String ISSCHOOLBUS = StringHelper.obj2str(request
				.getParameter("ISSCHOOLBUS"), "");
		String ISDANAGERCAR = StringHelper.obj2str(request
				.getParameter("ISDANAGERCAR"), "");
		String ISFOREIGNAFFAIRS = StringHelper.obj2str(request
				.getParameter("ISFOREIGNAFFAIRS"), "");
		String ISPOLICE = StringHelper.obj2str(
				request.getParameter("ISPOLICE"), "");
		String ISMASSESCASE = StringHelper.obj2str(request
				.getParameter("ISMASSESCASE"), "");
		String ISCONGESTION = StringHelper.obj2str(request
				.getParameter("ISCONGESTION"), "");
		String ISOthersItem = StringHelper.obj2str(request
				.getParameter("ISOthersItem"), "");
		// 涉军
		String ISARMYACC = StringHelper.obj2str(request
				.getParameter("ISARMYACC"), "");

		String TITLE = StringHelper.obj2str(request.getParameter("TITLE"), "");
		String REPORTTIME = StringHelper.obj2str(request
				.getParameter("REPORTTIME"), "");
		String REPORTUNITIDVALUE = StringHelper.obj2str(request
				.getParameter("REPORTUNITIDVALUE"), "");
		String reportPersonName = StringHelper.obj2str(request
				.getParameter("reportPersonName"), "");
		String TELENUM = StringHelper.obj2str(request.getParameter("TELENUM"),
				"");
		String caseHappenTime = StringHelper.obj2str(request
				.getParameter("caseHappenTime"), "");
		String caseHappenPlace = StringHelper.obj2str(request
				.getParameter("caseHappenPlace"), "");
		String DEATHPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("DEATHPERSONCOUNT"), "");
		String BRUISEPERSONCOUNT = StringHelper.obj2str(request
				.getParameter("BRUISEPERSONCOUNT"), "");
		String eventModify = StringHelper.obj2str(request
				.getParameter("eventModify"), "");
		String EVENTSOURCE = StringHelper.obj2str(request
				.getParameter("EVENTSOURCE"), "");
		String EVENTTYPE = StringHelper.obj2str(request
				.getParameter("EVENTTYPE"), "");
		String ALARMUNIT = StringHelper.obj2str(request
				.getParameter("ALARMUNIT"), "");
		String ALARMREGIONID = StringHelper.obj2str(request
				.getParameter("ALARMREGIONID"), "");
		String ALARMREGION = StringHelper.obj2str(request
				.getParameter("ALARMREGION"), "");
		String REPORTUNIT = StringHelper.obj2str(request
				.getParameter("REPORTUNIT"), "");

		String alarmIdValue = StringHelper.obj2str(request
				.getParameter("alarmIdValue"), "");
		// 关联事故编号
		String glAccNum = StringHelper.obj2str(
				request.getParameter("glAccNum"), "");
		// 事故发生道路名称
		String alarmRoad_TrafficCrowd = StringHelper.obj2str(request
				.getParameter("alarmRoad_TrafficCrowd"), "");
		String ldbz = StringHelper.obj2str(request.getParameter("ldbz"), "");
		String KMVALUE = StringHelper.obj2str(request.getParameter("KMVALUE"),
				"");
		
		String MVALUE = StringHelper
				.obj2str(request.getParameter("MVALUE"), "");
		String RADIOTYPE = StringHelper.obj2str(request
				.getParameter("RADIOTYPE"), "");

		// 支警处警情况
		String zdcjqk = StringHelper
				.obj2str(request.getParameter("zdcjqk"), "");

		String zb = alarmInfoActoin.getZbInfo(alarmRoad_TrafficCrowd, KMVALUE,
				MVALUE);

		// 审核人
		String ddapprover = StringHelper.obj2str(request
				.getParameter("ddapprover"), "");
		String zdapprover = StringHelper.obj2str(request
				.getParameter("zdapprover"), "");
		String zodapprover = StringHelper.obj2str(request
				.getParameter("zodapprover"), "");

		// 接警时间
		String receivetime = StringHelper.obj2str(request
				.getParameter("receivetime"), "");
		
		//管辖大队
		String GXDD = StringHelper.obj2str(request
				.getParameter("sobject_single"), "").trim();

		String x = "";
		String y = "";
		String[] zbArray = null;
		if (zb != null && !"".equals(zb)) {
			zbArray = zb.split(",");
		}

		if (zbArray != null) {
			x = zbArray[0];
			y = zbArray[1];
		}

		String state = "";
		String str = "";
		if ("0".equals(insertOrUpdate)) {
			// 大队保存时状态的设定
			state = "004032";
			str = "事故信息保存成功！";
		} else {
			// 大队上报时状态的设定
			if (jgbh.length() == 4) {
				state = "004034";
				str = "事故信息上报成功！";
			} else {
				state = "004033";
				str = "事故信息上报成功！";
			}
		}
		String jgid_Zd = "";
		jgid_Zd = ALARMUNIT.substring(0, 4);
		try {
			StringBuffer sql = new StringBuffer(
					"insert into t_attemper_alarm(ALARMID,ROADID,EVENTSOURCE,");
			sql
					.append(
							"EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION,ROADNAME,KMVALUE,CASEHAPPENTIME")
					.append(
							",REPORTUNIT,REPORTPERSON,REPORTTIME,ALARMTIME,ROADDIRECTION,EVENTSTATE,")
					.append(
							"SUPERUNIT,ALARMDESC,TELENUM,ACCEPTUNIT,ALARMADDRESS,GLACCNUM,MVALUE,x,y,RECEIVETIME,DDAPPROVER,ZDAPPROVER,ZODAPPROVER,GXDD)")
					.append(" values('").append(alarmIdValue).append("','")
					.append(alarmRoad_TrafficCrowd).append("','").append(
							EVENTSOURCE).append("','").append(EVENTTYPE)
					.append("','").append(ALARMUNIT).append("','")
					.append(TITLE).append("','").append(ALARMREGIONID).append(
							"','").append(ALARMREGION).append("','").append("")
					.append("','").append(KMVALUE).append("',to_date('")
					.append(caseHappenTime).append("','yyyy-mm-dd hh24:mi'),'")
					.append(REPORTUNIT).append("','").append(reportPersonName)
					.append("',to_date('").append(REPORTTIME).append(
							"','yyyy-mm-dd hh24:mi'),to_date('").append(
							REPORTTIME).append("','yyyy-mm-dd hh24:mi'),'")
					.append(RADIOTYPE).append("','").append(state)
					.append("','").append("").append("','").append(eventModify)
					.append("','").append(TELENUM).append("','")
					.append(jgid_Zd).append("','").append(caseHappenPlace)
					.append("','").append(glAccNum).append("','")
					.append(MVALUE).append("','").append(x).append("','")
					.append(y).append("',to_date('").append(receivetime)
					.append("','yyyy-mm-dd hh24:mi')").append(",'").append(
							ddapprover).append("','").append(zdapprover)
					.append("','").append(zodapprover).append("','").append(GXDD).append("')");

			System.out.println("警情填报alarmInfoActoin:" + sql);
			boolean isOK = DBHandler.execute(sql.toString());

			sql = new StringBuffer(
					"insert into t_attemper_accident(ALARMID,DEATHPERSONCOUNT,CASEHAPPENTIME");
			sql
					.append(
							",ISBUS,ISSCHOOLBUS,ISDANAGERCAR,ISFOREIGNAFFAIRS,ISPOLICE,ISMASSESCASE,ISCONGESTION,MISSINGPERSONCOUNT,ISOTHERSITEM")
					.append(
							",BRUISEPERSONCOUNT,RECEIVEPERSON,RECEIVETIME,ISARMYACC)values('")
					.append(alarmIdValue).append("','")
					.append(DEATHPERSONCOUNT).append("',to_date('").append(
							caseHappenTime).append("','yyyy-mm-dd hh24:mi'),'")
					.append(ISBUS).append("','").append(ISSCHOOLBUS).append(
							"','").append(ISDANAGERCAR).append("','").append(
							ISFOREIGNAFFAIRS).append("','").append(ISPOLICE)
					.append("','").append(ISMASSESCASE).append("','").append(
							ISCONGESTION).append("','").append("")
					.append("','").append(ISOthersItem).append("','").append(
							BRUISEPERSONCOUNT).append("','").append(
							reportPersonName).append("',to_date('").append(
							REPORTTIME).append("','yyyy-mm-dd hh24:mi'),")
					.append("'").append(ISARMYACC).append("')");
			System.out.println("警情填报alarmInfoActoin:" + sql);
			isOK = isOK && DBHandler.execute(sql.toString());

			sql = new StringBuffer(
					"insert into t_attemper_alarm_ZD(ALARMID,ROADID,EVENTSOURCE,");
			sql
					.append(
							"EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID,ALARMREGION,ROADNAME,KMVALUE,CASEHAPPENTIME")
					.append(
							",REPORTUNIT,REPORTPERSON,REPORTTIME,ALARMTIME,ROADDIRECTION,EVENTSTATE,SUPERUNIT,")
					.append(
							"ALARMDESC,TELENUM,ACCEPTUNIT,ALARMADDRESS,GLACCNUM,MVALUE,x,y,RECEIVETIME,DDAPPROVER,ZDAPPROVER,ZODAPPROVER,GXDD)")
					.append(" values('").append(alarmIdValue).append("','")
					.append(alarmRoad_TrafficCrowd).append("','").append(
							EVENTSOURCE).append("','").append(EVENTTYPE)
					.append("','").append(ALARMUNIT).append("','")
					.append(TITLE).append("','").append(ALARMREGIONID).append(
							"','").append(ALARMREGION).append("','").append("")
					.append("','").append(KMVALUE).append("',to_date('")
					.append(caseHappenTime).append("','yyyy-mm-dd hh24:mi'),'")
					.append(REPORTUNIT).append("','").append(reportPersonName)
					.append("',to_date('").append(REPORTTIME).append(
							"','yyyy-mm-dd hh24:mi'),to_date('").append(
							REPORTTIME).append("','yyyy-mm-dd hh24:mi'),'")
					.append(RADIOTYPE).append("','").append(state)
					.append("','").append("").append("','").append(eventModify)
					.append("','").append(TELENUM).append("','")
					.append(jgid_Zd).append("','").append(caseHappenPlace)
					.append("','").append(glAccNum).append("','")
					.append(MVALUE).append("','").append(x).append("','")
					.append(y).append("',to_date('").append(receivetime)
					.append("','yyyy-mm-dd hh24:mi')").append(",'").append(
							ddapprover).append("','").append(zdapprover)
					.append("','").append(zodapprover).append("','").append(GXDD).append("')");;
			System.out.println("警情填报alarmInfoActoin:" + sql);
			isOK = isOK && DBHandler.execute(sql.toString());

			sql = new StringBuffer(
					"insert into t_attemper_accident_zd(ALARMID,DEATHPERSONCOUNT,CASEHAPPENTIME");
			sql
					.append(
							",ISBUS,ISSCHOOLBUS,ISDANAGERCAR,ISFOREIGNAFFAIRS,ISPOLICE,ISMASSESCASE,ISCONGESTION,MISSINGPERSONCOUNT,ISOTHERSITEM")
					.append(
							",BRUISEPERSONCOUNT,RECEIVEPERSON,RECEIVETIME,ISARMYACC)values('")
					.append(alarmIdValue).append("','")
					.append(DEATHPERSONCOUNT).append("',to_date('").append(
							caseHappenTime).append("','yyyy-mm-dd hh24:mi'),'")
					.append(ISBUS).append("','").append(ISSCHOOLBUS).append(
							"','").append(ISDANAGERCAR).append("','").append(
							ISFOREIGNAFFAIRS).append("','").append(ISPOLICE)
					.append("','").append(ISMASSESCASE).append("','").append(
							ISCONGESTION).append("','").append("")
					.append("','").append(ISOthersItem).append("','").append(
							BRUISEPERSONCOUNT).append("','").append(
							reportPersonName).append("',to_date('").append(
							REPORTTIME).append("','yyyy-mm-dd hh24:mi'),")
					.append("'").append(ISARMYACC).append("')");
			System.out.println("警情填报alarmInfoActoin:" + sql);
			isOK = isOK && DBHandler.execute(sql.toString());

			// 插入支队处警情况
			if (state == "004034" || state == "004032" ) {
				StringBuffer insertZJSql = new StringBuffer(
						"insert into t_oa_process (OPID,YWID,REPORTTEXT,REPORTTIME,REPORTUNIT,REPORTKIND,OPERATEID,REPORTPERSON)values('");
				insertZJSql.append(getRandomId(REPORTUNIT)).append("','")
						.append(alarmIdValue).append("','").append(zdcjqk)
						.append("',sysdate,'").append(REPORTUNIT).append("','")
						.append("004038").append("','").append("1").append(
								"','").append(reportPersonName).append("')");
				System.out.println("警情填报alarmInfoActoin:" + sql);
				isOK = isOK && DBHandler.execute(insertZJSql.toString());
			}

			if (isOK) {
				// 默认添加父机构为接收单位
				// 改为为上一级为接受单位 by leisx 2011/8/4
				AccDeptDao adDao = new AccDeptDao();
				Map<String, String> adMap = new HashMap<String, String>();
				adMap.put("aid", alarmIdValue);
				adMap.put("atype", "1");
				adMap.put("mstate", "1");
				if (jgbh.length() == 6) {
					adMap.put("rpdcode", FlowUtil.rpadding(ALARMUNIT.substring(
							0, 4), "0", 8));
				} else if (jgbh.length() == 4) {
					adMap.put("rpdcode", FlowUtil.rpadding(ALARMUNIT.substring(
							0, 2), "0", 10));
				}

				adDao.adds(adMap);
			}

			if (isOK && !"0".equals(insertOrUpdate)) {
				insertPoliceRemind(reportPersonName, caseHappenTime,
						caseHappenPlace, DEATHPERSONCOUNT, BRUISEPERSONCOUNT,
						ALARMREGION, REPORTUNIT, alarmIdValue,
						alarmRoad_TrafficCrowd, ldbz, KMVALUE, MVALUE,
						RADIOTYPE);

				// 插入拥堵信息
				if ("1".equals(ISCONGESTION)
						&& !alarmRoad_TrafficCrowd.equals("")) {
					String endKm = "";
					try {
						endKm = String.valueOf((Integer.parseInt(KMVALUE) + 2));
					} catch (Exception e) {

					}
					
					if (!TITLE.endsWith("事故")) {
						TITLE += "事故";
					}
					TITLE += "造成严重拥堵";

					HashMap<String, String> kv = new HashMap<String, String>();
					kv.put("EVENTSOURCE", "002022");// 警情上报系统
					kv.put("EVENTTYPE", "001002");// 交通拥堵
					kv.put("EVENTSTATE", "570001");// 拥堵中
					// kv.put("ALARMID", alarmId);
					kv.put("TITLE", "发生交通拥堵");
					kv.put("ALARMUNIT", ALARMUNIT);
					kv.put("ALARMREGIONID", ALARMREGIONID);
					kv.put("ALARMREGION", ALARMREGION);
					// kv.put("ALARMTIME",
					// "to_date("+REPORTTIME)+",'yyyy-mm-dd HH24:mi')";
					kv.put("caseHappenPlace", caseHappenPlace);
					kv.put("ROADID", alarmRoad_TrafficCrowd);// 道路名称
					kv.put("ROADNAME", "");// 路段名称
					kv.put("KMVALUE", KMVALUE);
					kv.put("MVALUE", MVALUE);
					kv.put("EndKMVALUE", endKm);
					kv.put("EndMVALUE", kv.get("MVALUE"));
					kv.put("Xvalue", x);
					kv.put("Yvalue", y);
					kv.put("ROADDIRECTION", "0");// 下行
					kv.put("CaseHappenTime", caseHappenTime);
					kv.put("CaseEndTime", "");
					kv.put("REPORTPERSON", reportPersonName);
					kv.put("REPORTUNIT", REPORTUNIT);
					kv.put("REPORTTIME", REPORTTIME);
					kv.put("CONGESTIONTYPE", "005002");
					kv.put("CONGESTIONREASON", "事故");
					kv.put("crowdTypeFlg", "");
					kv.put("BLID", "");
					kv.put("remindInfo", TITLE);
					kv.put("baoSongFlg", "0");
					kv.put("baosongDepartMentId", "");
					CrowdManageDao crowdManageDao = new CrowdManageDao();
					isOK = crowdManageDao.addCrowdInfo(kv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(str);
		out.close();
		return null;
	}

	private void insertPoliceRemind(String reportPersonName,
			String caseHappenTime, String caseHappenPlace,
			String DEATHPERSONCOUNT, String BRUISEPERSONCOUNT,
			String ALARMREGION, String REPORTUNIT, String alarmIdValue,
			String alarmRoad_TrafficCrowd, String ldbz, String KMVALUE,
			String MVALUE, String RADIOTYPE) {
		// 2010-01-01，在广深高速10千米处，发生一起死亡3人、受伤5人的交通事故
		String currentTime = Constant.getCurrentTime(false).substring(0,16);
		caseHappenTime = AlarmInfoJoin.simplifyTime(currentTime, caseHappenTime);
		String remindInfo; 
		if(!KMVALUE.equals("")){
			remindInfo = AlarmInfoJoin.getStartAcc(caseHappenTime,
					ALARMREGION, alarmRoad_TrafficCrowd, ldbz, KMVALUE,
					MVALUE, "", "", RADIOTYPE, DEATHPERSONCOUNT,
					BRUISEPERSONCOUNT);
		}else{
			remindInfo = AlarmInfoJoin.getTimePlace(caseHappenTime, caseHappenPlace, AlarmDao.ACC_CODE);
			remindInfo += AlarmInfoJoin.getStartDesc(AlarmInfoJoin.getDieWound(DEATHPERSONCOUNT,BRUISEPERSONCOUNT ),  AlarmDao.ACC_CODE);
		}
		prcore.insert(PoliceRemindDao.formPoliceRemind(alarmIdValue,Constant.getCurrentTime(false).substring(0,16), REPORTUNIT, ALARMREGION, reportPersonName,"1",null, remindInfo,"2"));
	}

	/**
	 * 上传文件操作<br>
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUploadFile(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		final int size = 50 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();

		String uploadPath = realPath + "EHL_Upload\\";
		String tempPath = realPath + "EHL_Upload\\temp\\";

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}

		String databasePath = "EHL_Upload/";
		String databaseFileName = null;
		// 编号
		String alarmId = "";
		String fileModify = "";

		String strRerutn = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>";
		strRerutn += "<body onload='window.close();'></body></html>";

		request.setCharacterEncoding(encoding);

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 1024 * 1024);
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			factory.setRepository(tempFile);

			FileUpload upload = new FileUpload(factory);
			upload.setSizeMax(size);
			List list = null;
			try {
				upload.setHeaderEncoding("utf-8");
				list = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件上传失败");
				out
						.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body><script type=\"text/javascript\">"
								+ "alert('上传附件不能超过50兆！');  window.close();"
								+ "parent.returnValue = \"ok\";</script></body><html>");
				out.close();
				return null;
			}
			String databaseFileNames = ""; // request.getSession().setAttribute("databaseFileName",
			// databaseFileName);
			Iterator ite = list.iterator();

			int fileCount = 0;

			try {
				// 依次处理每一个文件：
				while (ite.hasNext()) {

					FileItem fi = (FileItem) ite.next();
					if (fi == null) {
						continue;
					}
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("fileInfo" + fileCount)) {
							fileCount = fileCount + 1;
							fileModify = fi.getString(encoding);
						} else if (fi.getFieldName().equals("insertYwid")) {
							alarmId = fi.getString(encoding);
						}
					} else if (!fi.isFormField()) {

						// 取得文件名
						String fileName = fi.getName();
						// 获得文件名，这个文件名包括路径：
						if (fileName.length() != 0 && fileName != null
								&& fileName != "") {
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1, fileName
									.length());

							databaseFileName = databasePath + fileName;

							// 在这里可以记录用户和文件信息
							// ...
							// 写入文件，暂定文件名为a.txt，可以从fileName中提取文件名：
							String newFileName = uploadPath + fileName;
							// 判断是否有相同文件名文件存在
							if (new File(newFileName).exists()
									&& new File(newFileName).isFile()) {
								double doubleRandom = Math.random() * 100000;
								databaseFileName = databasePath + doubleRandom
										+ "/" + fileName;
								uploadPath = uploadPath + doubleRandom + "\\";
								newFileName = uploadPath + fileName;
								File file = new File(uploadPath);

								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									// 续报信息插入的sql
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql);
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql);
								}
							} else {
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									StringBuffer sql = new StringBuffer(
											"insert into t_oa_process(OPID,YWID,YWLX,PERSONWRITE,TELEPHONE,XBBT,XBNR,XBFILEMODFIY,XBFILEPATH,XBSTATE,XBDEPARTMENTID) values('");
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql1 = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql1);
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									// 取得最大squence
									String maxSequence = CreateSequence
											.getMaxForSeq(
													"SEQ_T_OA_ATTACHMENT", 12);
									String sql = "insert into T_OA_ATTACHMENT values ('"
											+ StringHelper.obj2str(maxSequence,
													"")
											+ "','"
											+ StringHelper.obj2str(alarmId, "")
											+ "','"
											+ "重大警情"
											+ "','"
											+ ""
											+ "','"
											+ ""
											+ "','"
											+ StringHelper.obj2str(
													databaseFileName, "")
											+ "','"
											+ StringHelper.obj2str(fileModify,
													"") + "')";
									DBHandler.execute(sql);
								}
							}
							databaseFileNames += "," + databaseFileName;
						}
					}
				}
				if (!databaseFileNames.equals("")) {
					databaseFileNames = databaseFileNames.substring(1);
					request.getSession().setAttribute("databaseFileNames",
							databaseFileNames);
				}
			} catch (Exception e) {
				System.out.println("文件上传失败");
				out
						.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body><script type=\"text/javascript\">"
								+ "alert('文件上传失败！');  window.close();"
								+ "parent.returnValue = \"ok\";</script></body><html>");
				out.close();
				return null;
			}
		}
		out
				.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body onload=' window.close();'></body></html>");
		out.close();
		return null;
	}

	public static String getRandomId(String jgId) {
		return (jgId.substring(0, 6)
				+ StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS")
				+ String.valueOf(Math.random()).substring(2) + String.valueOf(
				Math.random()).substring(2)).substring(0, 50);
	}

	/***
	 * 通过道路名称和公里桩取得坐标位置<br/>
	 * 
	 * @param dlmc
	 * @param KMVALUE
	 * @param MVALUE
	 * @return
	 */
	public static String getZbInfo(String dlmc, String KMVALUE, String MVALUE) {
		String sql = "select zb from LCB_PT_MIS where dlmc = '" + dlmc
				+ "' and qmz= '" + KMVALUE + "'";
		Object[][] ObjectResult = DBHandler.getMultiResult(sql);

		if (ObjectResult == null) {
			return "";
		} else {
			return String.valueOf(ObjectResult[0][0]);
		}
	}

	public static String encapSq(String str) {
		return "'" + str + "'";
	}

	public static String insert(String tname, Map<String, String> kv) {
		String sql = null;
		if (kv != null && kv.size() > 0) {
			String sepItem = ",";
			String columnStr = "";
			String valueStr = "";
			for (Map.Entry<String, String> entry : kv.entrySet()) {
				columnStr += sepItem + entry.getKey();
				valueStr += sepItem + entry.getValue();
			}
			if (!columnStr.equals("") && !valueStr.equals("")) {
				columnStr = columnStr.substring(sepItem.length());
				valueStr = valueStr.substring(sepItem.length());
			}
			sql = "insert into " + tname + "(" + columnStr + ") values ("
					+ valueStr + ")";
		}
		return sql;
	}
}