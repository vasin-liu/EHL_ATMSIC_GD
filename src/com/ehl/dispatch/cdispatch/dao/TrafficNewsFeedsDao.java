package com.ehl.dispatch.cdispatch.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.cdispatch.action.alarmInfoActoin;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.policeWorks.assess.dao.ScoreRecordDao;

//交通报料信息数据处理类
public class TrafficNewsFeedsDao {

	Logger logger = Logger.getLogger(TrafficNewsFeedsDao.class);

	/**
	 * 反馈报料信息
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public boolean feedbackNewsInfo(List<String> feedback) {
		boolean bTag = false;
		try {
			StringBuffer sb = new StringBuffer();
			String bh = feedback.get(0);
			if (!"".equals(bh)) {
				// String clzt_0 = "0";//待总队处理
				String clzt_1 = "1";// 待大队核实
				String clzt_2 = "2";// 待总队确认
				String clzt_3 = "3";// 处理完成
				// String clzt_4 = "4";//已忽略

				String dlmc = feedback.get(1);
				String ldfx = feedback.get(2);
				String QSLC = feedback.get(3);
				String ZZLC = feedback.get(4);
				String lk = feedback.get(5);
				String kssj = feedback.get(6);
				String zzsj = feedback.get(7);
				String gxdd = feedback.get(8);
				String wzms = feedback.get(9);
				String blr = feedback.get(12);
				String lxfs = feedback.get(13);
				String lrr = feedback.get(14);
				String lrsj = feedback.get(15);
				String lkyy = feedback.get(10);
				String sfjs = feedback.get(11);
				String clzt = feedback.get(16);

				String hsr = feedback.get(17);
				String hssj = feedback.get(18);
				String hsqk = feedback.get(19);

				String scr = feedback.get(20);
				String scsj = feedback.get(21);
				String scyj = feedback.get(22);
				String pf = feedback.get(23);
				String sfcswhs = feedback.get(24);

				String ldmc = feedback.get(25);
				String qslcm = feedback.get(26);
				String zzlcm = feedback.get(27);

				sb.append("UPDATE EXCH_T_TRAFFICNEWSFEEDS_TEMP SET ");
				String liyou = "由" + lrr + "录入" + dlmc + " " + ldfx + QSLC
						+ "千米" + ZZLC + "千米处拥堵";
				sb.append("DLMC='" + dlmc + "',");// 道路名称
				sb.append("LDMC='" + ldmc + "',");// 路段名称
				sb.append("LDFX='" + ldfx + "',");// 路段方向
				sb.append("QSLC='" + QSLC + "',");// 起始里程
				sb.append("QSLCM='" + qslcm + "',");// 起始里程百米值
				sb.append("ZZLC='" + ZZLC + "',");// 终止里程
				sb.append("ZZLCM='" + zzlcm + "',");// 终止里程百米值
				sb.append("LK='" + lk + "',");// 路况
				sb.append("QSSJ=TO_DATE('" + kssj
						+ "','YYYY-MM-DD HH24:MI:SS'),");// 拥堵开始时间
				sb.append("ZZSJ=TO_DATE('" + zzsj
						+ "','YYYY-MM-DD HH24:MI:SS'),");// 拥堵终止时间
				sb.append("GXDD='" + gxdd + "',");// 管辖大队
				sb.append("BZ='" + wzms + "',");// 备注
				sb.append("BLR='" + blr + "',");// 报料人
				sb.append("LXFS='" + lxfs + "',");// 报料人联系方式
				sb.append("LRR='" + lrr + "',");// 录入人
				sb.append("LRSJ=TO_DATE('" + lrsj
						+ "','YYYY-MM-DD HH24:MI:SS'),");// 录入时间
				sb.append("LKYY='" + lkyy + "',");// 路况原因
				sb.append("CLZT='" + clzt + "',");

				Object res[] = getMsg(dlmc, gxdd);
				if (clzt_1.equals(clzt)) {
					sb.append("ZDXFSJ=SYSDATE,");
				} else if (clzt_2.equals(clzt)) {
					if ("-1".equals(sfcswhs)) {

					}
					// 大队核实 如果超时未核实
					sb
							.append("JF=(CASE WHEN ZDXFSJ>=(SYSDATE-1/24*2) THEN 1 WHEN ZDXFSJ<(SYSDATE-1/24*2) THEN -1 END),");
				}

				sb.append("HSR='" + hsr + "',");
				sb.append("HSSJ=TO_DATE('" + hssj
						+ "','YYYY-MM-DD HH24:MI:SS'),");
				sb.append("SFJS='" + sfjs + "',");// 是否结束
				sb.append("HSQK='" + hsqk + "',");

				sb.append("SCR='" + scr + "',");
				sb.append("SCSJ=TO_DATE('" + scsj
						+ "','YYYY-MM-DD HH24:MI:SS'),");
				sb.append("SCYJ='" + scyj + "' ");

				sb.append("WHERE BH='" + bh + "'");

				bTag = DBHandler.execute(sb.toString());
				// 如果报料属实
				if (!"-1".equals(sfjs) && bTag) {
					// <-- Modify by xiayx 2010-07-26 注释 取消大队核实添加拥堵信息
					/*
					 * ScoreRecordDao srd = new ScoreRecordDao(); //被总队采用 if
					 * (clzt_3.equals(clzt) && pf.equals("1")) { // 大队核实同时上报一条拥堵
					 * HashMap hm = new HashMap();
					 * 
					 * hm.put("ROADID", dlmc); if (res != null && res.length >
					 * 0) { hm.put("ALARMREGIONID", StringHelper.obj2str(
					 * res[1], "")); hm.put("ALARMREGION",
					 * StringHelper.obj2str(res[2], "")); hm.put("ALARMUNIT",
					 * StringHelper .obj2str(res[1], "")); hm.put("REPORTUNIT",
					 * StringHelper.obj2str(res[1], "")); // 报料编号 hm.put("BLID",
					 * bh);
					 * 
					 * hm.put("EVENTSOURCE", "002022"); hm.put("EVENTTYPE",
					 * "001002"); hm.put("TITLE", "发生 交通拥堵"); hm.put("ROADNAME",
					 * dlmc); hm.put("KMVALUE", QSLC); hm.put("MVALUE", "0");
					 * hm.put("EndKMVALUE", ZZLC); hm.put("EndMVALUE", "0");
					 * hm.put("CaseHappenTime", kssj); hm.put("CaseEndTime",
					 * ""); hm.put("REPORTPERSON", hsr); hm.put("REPORTTIME",
					 * new Date().toLocaleString()); hm.put("CONGESTIONTYPE",
					 * ""); hm.put("CONGESTIONREASON", lkyy);
					 * 
					 * if ("1".equals(sfjs)) { hm.put("EVENTSTATE", "570002");//
					 * 1是已经结束 } else if ("0".equals(sfjs)) {
					 * hm.put("EVENTSTATE", "570001");// 0是未结束 }
					 * 
					 * hm.put("Xvalue", ""); hm.put("Yvalue", "");
					 * hm.put("crowdTypeFlg", "");
					 * 
					 * boolean flag = false; try { CrowdManageDao cmd = new
					 * CrowdManageDao(); if (!"双方向".equals(ldfx)) {
					 * hm.put("ROADDIRECTION", ldfx); } else {
					 * hm.put("ROADDIRECTION", "2"); // //
					 * hm.put("ROADDIRECTION", "1"); flag = //
					 * cmd.addCrowdInfo(hm); // } flag = cmd.addCrowdInfo(hm);
					 * 
					 * } catch (Exception e) { } if (flag &&
					 * "-1".equals(sfcswhs)) {
					 * srd.add(StringHelper.obj2str(res[1], ""),
					 * StringHelper.obj2str(res[2], ""), lrsj, "5", "大队超时未核实信息:"
					 * + liyou); } } else { String remindInfo = "系统提示：" + dlmc +
					 * QSLC + "Km 至" + ZZLC + "Km 处" + ldfx +
					 * "方向发生交通拥堵，预计拥堵时间为30分钟。"; insertPoliceRemind("系统提示",
					 * remindInfo, "系统提示", "系统提示");
					 * logger.error("【警民互动】-->与方纬道路不匹配，未上报拥堵信息。编号：" + bh + ";" +
					 * liyou); } }
					 */
					// -->
					ScoreRecordDao srd = new ScoreRecordDao();
					if (clzt_3.equals(clzt)) {
						if (!"0".equals(pf)) {
							try {
								// 总队不采信大队核实信息
								srd.add(StringHelper.obj2str(res[1], ""),
										StringHelper.obj2str(res[2], ""), lrsj,bh,
										"4", "2","总队未采信大队核实信息:" + liyou);
								updateNews(bh, "1");
							} catch (Exception e) {
							}

						} else {
							// 总队采信大队核实信息
							updateNews(bh, "0");
							// <--Modify by xiayx 2011-07-26 添加 总队采信添加拥堵信息
							HashMap hm = new HashMap();
							hm.put("ROADID", dlmc);
							if (res != null && res.length > 0) {
								hm.put("ALARMREGIONID", StringHelper.obj2str(
										res[1], ""));
								hm.put("ALARMREGION", StringHelper.obj2str(
										res[2], ""));
								hm.put("ALARMUNIT", StringHelper.obj2str(
										res[1], ""));
								hm.put("REPORTUNIT", StringHelper.obj2str(
										res[1], ""));
								// 报料编号
								hm.put("BLID", bh);

								hm.put("EVENTSOURCE", "002022");
								hm.put("EVENTTYPE", "001002");
								hm.put("TITLE", "发生 交通拥堵");
								hm.put("ROADNAME", dlmc);
								hm.put("KMVALUE", QSLC);
								hm.put("MVALUE", "0");
								hm.put("EndKMVALUE", ZZLC);
								hm.put("EndMVALUE", "0");
								hm.put("CaseHappenTime", kssj);
								hm.put("CaseEndTime", "");
								hm.put("REPORTPERSON", hsr);
								hm.put("REPORTTIME", new Date()
										.toLocaleString());
								hm.put("CONGESTIONTYPE", "");
								hm.put("CONGESTIONREASON", lkyy);
								hm.put("remindInfo", wzms);
								if ("1".equals(sfjs)) {
									hm.put("EVENTSTATE", "570002");// 1是已经结束
								} else if ("0".equals(sfjs)) {
									hm.put("EVENTSTATE", "570001");// 0是未结束
								}

								hm.put("Xvalue", "");
								hm.put("Yvalue", "");
								hm.put("crowdTypeFlg", "");

								boolean flag = false;
								try {
									CrowdManageDao cmd = new CrowdManageDao();
									if (!"双方向".equals(ldfx)) {
										hm.put("ROADDIRECTION", ldfx);
									} else {
										hm.put("ROADDIRECTION", "2");
									}
									flag = cmd.addCrowdInfo(hm);

								} catch (Exception e) {
								}
								if (flag && "-1".equals(sfcswhs)) {
									srd.add(StringHelper.obj2str(res[1], ""),
											StringHelper.obj2str(res[2], ""),
											lrsj, bh,"4","1", "大队超时未核实信息:" + liyou);
								}
							}
							// -->
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【警民互动】-->反馈报料信息错误：" + e);
		}
		return bTag;
	}

	/**
	 * 更新是否采信标志<br/>
	 * 
	 * @param bh
	 * @param sfcy
	 * @return
	 */
	public boolean updateNews(String bh, String sfcy) {
		boolean bTag = false;
		if (!"".equals(bh)) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("UPDATE EXCH_T_TRAFFICNEWSFEEDS_TEMP SET sfcy='");
				sb.append(sfcy);
				sb.append("' WHERE BH='");
				sb.append(bh);
				sb.append("'");
				bTag = DBHandler.execute(sb.toString());
			} catch (Exception e) {
				logger.error("【警民互动】-->忽略消息错误：" + e);
			}
		}
		return bTag;
	}

	// 忽略消息
	public boolean cancelNews(String bh, String clzt) {
		boolean bTag = false;
		if (!"".equals(bh)) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("UPDATE EXCH_T_TRAFFICNEWSFEEDS_TEMP SET CLZT='");
				sb.append(clzt);
				sb.append("' WHERE BH='");
				sb.append(bh);
				sb.append("'");
				bTag = DBHandler.execute(sb.toString());
			} catch (Exception e) {
				logger.error("【警民互动】-->忽略消息错误：" + e);
			}
		}
		return bTag;
	}

	/**
	 * 根据bh查询交通报料详细信息
	 * 
	 * @param bh
	 * @return
	 */
	public Object[] doGetNewsInfo(String bh, String pname) {
		Object[] res = null;
		if (!"".equals(bh)) {
			try {
				StringBuffer sb = new StringBuffer();
				sb
						.append("SELECT BH,(select roadlevel from t_oa_dictdlfx where t_oa_dictdlfx.dlmc=EXCH_T_TRAFFICNEWSFEEDS_TEMP.dlmc) as roadlevel,DLMC,LDMC,LDFX,QSLC,QSLCM,ZZLC,ZZLCM,LK,TO_CHAR(QSSJ,'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(ZZSJ,'YYYY-MM-DD HH24:MI:SS')");
				sb
						.append(",REPLACE(GXDD,'市公安局交通警察支队',''),WZMS,LKYY,SFJS,BLR,LXFS,LRR,TO_CHAR(LRSJ,'YYYY-MM-DD HH24:MI:SS'),CLZT");
				sb
						.append(",HSR,TO_CHAR(HSSJ,'YYYY-MM-DD HH24:MI:SS'),HSQK,SCR,TO_CHAR(SCSJ,'YYYY-MM-DD HH24:MI:SS'),SCYJ,JF,TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),WZMS");
				sb
						.append(",'"
								+ pname
								+ "',TO_CHAR(ZDXFSJ,'YYYY-MM-DD HH24:MI:SS'),(CASE WHEN ZDXFSJ>=(SYSDATE-1/24*2) THEN 1 WHEN ZDXFSJ<(SYSDATE-1/24*2) THEN -1 END)");
				sb
						.append(", SFCY,bz, (select (case when roadlevel = 1 then gbdm || ':' || dlmc when roadlevel = 2 then dlmc when roadlevel = 3 then dlmc end) mc from T_OA_DICTDLFX where T_OA_DICTDLFX.dlmc = EXCH_T_TRAFFICNEWSFEEDS_TEMP.dlmc) FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP WHERE BH='"
								+ bh + "'");

				res = DBHandler.getLineResult(sb.toString());
			} catch (Exception e) {
				logger.error("【警民互动】-->查询报料信息错误：" + e);
			}
		}
		return res;
	}

	/**
	 * 删除交通报料信息
	 * 
	 * @param bh
	 * @return
	 * @throws Throwable
	 */
	public boolean deleteNews(String bh) {
		boolean flag = false;
		if (!"".equals(bh)) {
			try {
				String mainsql = "DELETE FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP WHERE BH='"
						+ bh + "'";
				flag = DBHandler.execute(mainsql);
			} catch (Exception e) {
				logger.error("【警民互动】-->查询报料信息错误：" + e);
			}
		}
		return flag;
	}
	
	/**
	 * 获取拥堵报料采用件数
	 * @return
	 */
	public int getYDBLCYCount(String jgid, String jgmc, String start, String end){
		int count = 0;
		if(jgid != null && jgmc != null && start != null && end != null){
			
		}
		return count;
	}
	/**
	 * 取得指定机构指定时间内反馈报料信息数<br/>
	 * 
	 * @param DeptId
	 *            机构id
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public HashMap<String, Integer> getFeedbackNum(String DeptId,
			String DeptName, String startDate, String endDate) {
		Object[][] deptNum = null;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT JF,COUNT(1) FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP NEWS WHERE JF='1' ");// 已核实
		//排除在核实时间范围内的拥堵报料信息
		//sb.append("(JF='1' or (JF IS NULL AND SYSDATE-ZDXFSJ>1/24/2))");
		// 在总队下发时间范围内考核
		if (startDate.length() == 10) {
			startDate += " 00:00:00";
		}
		if (endDate.length() == 10) {
			endDate += " 23:59:59";
		}
		sb.append(" AND ZDXFSJ>=TO_DATE('" + startDate
				+ "','YYYY-MM-DD HH24:MI:SS') ");
		sb.append("AND ZDXFSJ<=TO_DATE('" + endDate
				+ "','YYYY-MM-DD HH24:MI:SS') ");

		try {
			// 0总队 1支队 2大队
			int deptLevel = getDeptLevel(DeptId, DeptName);
			switch (deptLevel) {
			case 0:
				break;
			case 1:
				sb.append("AND GXDD LIKE '" + DeptName.substring(0, 2) + "%' ");
				break;
			case 2:
//				sb
//						.append("AND (GXDD=(SELECT OTHERNAME FROM T_SYS_DEPARTMENT WHERE JGID='"
//								+ DeptId + "'))");
				sb.append("AND '"+DeptId+"'="+getGetJgidByJgmcSQL("GXDD"));
				break;
			}
			sb.append(" GROUP BY JF ORDER BY JF ASC");
			deptNum = DBHandler.getMultiResult(sb.toString());
			if (deptNum != null && deptNum.length > 0) {
				int jf = -1;
				int num = 0;
				for (int i = 0; i < deptNum.length; i++) {
					jf = StringHelper.obj2int(deptNum[i][0], -1);
					num = StringHelper.obj2int(deptNum[i][1], 0);
					switch (jf) {
					case 1:
						map.put("cycount", num);
						break;
					case 2:
						map.put("wcycount", num);
						break;
					case -1:
						map.put("gqcount", num);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("【警民互动】-->取得反馈报料信息数：" + e);
		}
		return map;
	}

	/**
	 * 取得指定机构是否有新报料或者反馈信息<br/>
	 * 
	 * @param DeptId
	 *            机构id
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public Object[][] getNewFeeds(String DeptId, String DeptName) {
		Object[][] newFeeds = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT * FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP NEWS WHERE CLZT='待核实' ");
		int deptLevel = getDeptLevel(DeptId, DeptName);
		switch (deptLevel) {
		case 0:
			break;
		case 1:
			sb.append("AND GXDD LIKE '" + DeptName.substring(0, 2) + "' ");
			break;
		case 2:
			sb
					.append("AND GXDD=(SELECT OTHERNAME FROM T_SYS_DEPARTMENT WHERE JGID='"
							+ DeptId + "') ");
			break;
		}
		return newFeeds;
	}

	/**
	 * 根据bh查询交通报料详细信息
	 * 
	 * @param bh
	 * @return
	 */
	public Object getNewFeedsCount(String DeptId, String DeptName,
			String getType) {
		Object res = null;
		if (!"".equals(DeptId)) {
			try {
				StringBuffer sb = new StringBuffer();
				sb
						.append("SELECT COUNT(BH) FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP NEWS WHERE 1=1 ");

				int deptLevel = getDeptLevel(DeptId, DeptName);
				if ("1".equals(getType)) {
					if (0 != deptLevel) {
						sb.append("AND 1=2");
					} else {
						sb.append("AND CLZT='2'");
					}
				} else {
					switch (deptLevel) {
					case 0:
						sb.append("AND CLZT='0'");
						break;
					case 1:
						sb.append("AND GXDD LIKE '" + DeptName.substring(0, 2)
								+ "%' AND CLZT='1'");
						break;
					case 2:
						sb
								.append("AND ( GXDD=(SELECT OTHERNAME FROM T_SYS_DEPARTMENT WHERE JGID='"
										+ DeptId + "') ");
						sb
								.append(" or GXDD=(SELECT jgmc FROM T_SYS_DEPARTMENT WHERE JGID='"
										+ DeptId + "') )");
						sb.append(" AND CLZT='1' ");
						break;
					}
				}

				res = DBHandler.getSingleResult(sb.toString());
			} catch (Exception e) {
				logger.error("【警民互动】-->查询报料信息错误：" + e);
			}
		}

		return res;
	}

	/**
	 * 拥堵提醒信息
	 * 
	 * @param bh
	 * @return
	 */
	public Object[][] getNewFeedsInfo(String DeptId, String DeptName,
			String getType) {
		Object[][] res = null;
		if (!"".equals(DeptId)) {
			try {
				StringBuffer sb = new StringBuffer();
				sb
						.append("SELECT BH,DLMC,F_GET_DLFX(DLMC,LDFX),F_GET_QZLC(QSLC,QSLCM,ZZLC,ZZLCM),LK,LKYY,");
				sb
						.append("DECODE(CLZT,'0','待总队处理','1','待大队核实','2','待总队确认','3','处理完成','4','已忽略'),CLZT,XXLY");
				sb.append(" FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP WHERE 1=1 ");

				int deptLevel = getDeptLevel(DeptId, DeptName);
				if ("1".equals(getType)) {
					if (0 != deptLevel) {
						sb.append("AND 1=2");
					} else {
						sb.append("AND CLZT='2'");
					}
				} else {
					// 0总队 1支队 2大队
					switch (deptLevel) {
					case 0:
						sb.append("AND CLZT='0'");
						break;
					case 1:
						sb.append("AND GXDD LIKE '" + DeptName.substring(0, 2)
								+ "%' AND CLZT='1'");
						break;
					case 2:
						sb
								.append("AND INSTR((SELECT REPLACE(JGMC,'市公安局交通警察','交警')||','||OTHERNAME FROM T_SYS_DEPARTMENT WHERE JGID='"
										+ DeptId + "'),GXDD)!=0 AND CLZT='1'");
						break;
					}
				}

				res = DBHandler.getMultiResult(sb.toString());
			} catch (Exception e) {
				logger.error("【警民互动】-->查询报料信息错误：" + e);
			}
		}
		return res;
	}

	/**
	 * 根据当前登录用户获取人员姓名
	 * 
	 * @param bh
	 * @return
	 */
	public String getPname(String ucode) {
		String uname = "";
		if (!"".equals(ucode)) {
			try {
				StringBuffer sb = new StringBuffer();
				sb
						.append("SELECT XM FROM T_SYS_PERSON P,T_SYS_USER U WHERE P.RYID=U.PERSONCODE AND U.USERCODE='"
								+ ucode + "'");

				uname = StringHelper.obj2str(DBHandler.getSingleResult(sb
						.toString()), "");
			} catch (Exception e) {
				logger.error("【警民互动】-->查询报料信息错误：" + e);
			}
		}
		return uname;
	}

	/**
	 * 判断机构类型<br/>
	 * 
	 * @param DeptId
	 *            机构id
	 * @param DeptName
	 *            机构name
	 * @return
	 */
	private int getDeptLevel(String DeptId, String DeptName) {
		int deptLevel = -1;
		if (!"".equals(DeptName)) {
			if (DeptName.endsWith("总队")) {
				return 0;
			}
			if (DeptName.endsWith("支队")) {
				return 1;
			}
			if (DeptName.endsWith("大队")) {
				return 2;
			}
		}

		if (!"".equals(DeptId)) {
			if ("0000".equals(DeptId.substring(2, 6))) {
				return 0;
			} else if ("00".equals(DeptId.substring(2, 4))) {
				return 1;
			} else if (DeptId.length() == 12 && DeptId.endsWith("000000")) {
				return 2;
			}
		}
		return deptLevel;
	}

	/**
	 * 获取道路编号和管辖大队机构名称机构id<br/>
	 * 
	 * @param dlmc
	 *            道路名称
	 * @param gxdd
	 *            管辖大队
	 * @return
	 */
	private Object[] getMsg(String dlmc, String gxdd) {
		Object[] res = null;
		StringBuffer sb = new StringBuffer();
		// sb.append("SELECT * FROM ");
		// sb.append("(SELECT DISTINCT ROAD.gbdm FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP TEMP,T_OA_DICTDLFX ROAD WHERE TEMP.DLMC=ROAD.DLMC AND TEMP.DLMC='"+dlmc+"'),");
		// sb.append("(SELECT JGID,JGMC FROM T_SYS_DEPARTMENT WHERE OTHERNAME='"+gxdd.substring(0,2)+"市公安局交通警察支队"+gxdd.substring(2)+"')");
		sb.append("SELECT * FROM ");
		sb
				.append("(SELECT DISTINCT ROAD.gbdm FROM T_OA_DICTDLFX ROAD WHERE ROAD.DLMC='"
						+ dlmc + "'),");
		sb.append("(SELECT JGID,JGMC FROM T_SYS_DEPARTMENT WHERE (OTHERNAME='"
				+ gxdd + "'");
		sb.append(" or jgmc='" + gxdd + "') )");
		try {
			res = DBHandler.getLineResult(sb.toString());
		} catch (Exception e) {

		}
		return res;
	}

	/**
	 * 根据道路名称和方向描述获取方向编码<br/>
	 * 
	 * @param desc
	 *            方向描述
	 * @param dlmc
	 *            道路名称
	 * @return
	 */
	private String getDlFx(String desc, String dlmc) {
		desc = desc.replace("往", "");
		String fx = "-1";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT (CASE WHEN D.BEGIN='" + desc
				+ "' THEN 1 WHEN D.END='" + desc
				+ "' THEN 0 ELSE -1 END) FROM T_OA_DICTDLFX D ");
		sql.append("WHERE D.DLMC='" + dlmc + "' ");
		Object addname = null;
		try {
			addname = DBHandler.getSingleResult(sql.toString());
			if (addname != null) {
				fx = StringHelper.obj2str(addname, "-1");
			}
		} catch (Exception e) {
			logger.error("施工占道获取道路方向异常");
		}
		return fx;
	}

	/**
	 * 取得道路方向通过道路名称<br/>
	 * 
	 * @param roadName
	 * @return
	 * @throws Throwable
	 */
	public Object[] getdaoluFx(String roadName) {
		Object[] result = null;
		try {
			String mainsql = "SELECT DISTINCT BEGIN,END FROM T_OA_DICTDLFX WHERE dlmc='"
					+ roadName + "'";
			result = DBHandler.getLineResult(mainsql);
		} catch (Exception e) {
			logger.error("【警民互动】-->查询道路方向错误：" + e);
		}
		return result;
	}

	/**
	 * 插入交警提示<br/>
	 * 
	 * @param roadName
	 * @return
	 * @throws Throwable
	 */
	public boolean insertPoliceRemind(String departmentId, String remindInfo,
			String deptNameForCenter, String userName, String alarmId) {
		boolean isSuccess = false;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("select count(*) from T_OA_POLICE_REMIND where alarmid='"+alarmId+"' and rownum <= 1");
			Object remind = FlowUtil.readSingle(sql.toString());
			if(remind.toString().equals("0")){
				sql.setLength(0);
				String policeRemind = CreateSequence.getMaxForSeq(
						"SEQ_T_OA_POLICE_REMIND", 20);
				sql.append("insert into T_OA_POLICE_REMIND ( REMINDID, REMINDTIME, DEPARTMENTID, DEPARTMENTNAME, REMINDINFO, USERNAME, ALARMID) values (");
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
				sql.append("',");
				sql.append(" '");
				sql.append(alarmId);
				sql.append("' )");
				System.out.println("***********" + sql);
				isSuccess = DBHandler.execute(String.valueOf(sql));
			}else{
				isSuccess = updatePoliceRemind(remindInfo, alarmId);
			}
			if (!isSuccess){
				logger.error("插入警情提示信息出错。 sql:" + sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入警情提示信息出错。 sql:" + sql);
		}
		return isSuccess;
	}
	
	public boolean deletePoliceRemind(String alarmId){
		logger.info("根据警情ID删除交警提示信息");
		boolean isOK = false;
		if(alarmId != null){
			String sql = "delete from t_oa_police_remind where alarmid='"+alarmId+"'";
			try {
				isOK = DBHandler.execute(sql);
			} catch (Exception e) {
				isOK = false;
				logger.error("删除异常",e);
			}
		}else{
			logger.warn("警情ID为"+alarmId);
		}
		logger.info("删除"+(isOK?"成功":"失败"));
		return isOK;
	}
	
	//更新交警提示 Modified by leisx 2011/8/2
	public boolean updatePoliceRemind(String remindInfo, String alarmId) {
		boolean isSuccess = false;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("update T_OA_POLICE_REMIND set REMINDTIME = sysdate ,REMINDINFO = '").append(
					remindInfo).append("' where alarmId = '").append(alarmId)
					.append("'");
			isSuccess = DBHandler.execute(String.valueOf(sql));
			System.out.println("***********" + sql);
			if (isSuccess) {
				logger.error("更新警情提示信息出错。 sql:" + sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新警情提示信息出错。 sql:" + sql);
		}
		return isSuccess;
	}

	/**
	 * 添加交通信息爆料信息
	 * 
	 * @param map
	 * @return
	 */
	public boolean addNewsInfo(Map<String, String> map) {
		String seqName = "SEQ_T_TRAFFIC_NEWS_FEED";
		map.put("bh", "'44'||lpad(" + seqName + ".nextval,10,'0')");
		map.put("dlmc", "'" + map.get("dlmc") + "'");
		map.put("ldmc", "'" + map.get("ldmc") + "'");
		map.put("ldfx", "'" + map.get("ldfx") + "'");
		map.put("qslc", "'" + map.get("qslc") + "'");
		map.put("qslcm", "'" + map.get("qslcm") + "'");
		map.put("zzlc", "'" + map.get("zzlc") + "'");
		map.put("zzlcm", "'" + map.get("zzlcm") + "'");
		map.put("gxdd", "'" + map.get("gxdd") + "'");
		map.put("qssj", "to_date('" + map.get("qssj")
				+ "','yyyy-mm-dd HH24:mi')");
		map.put("lk", "'" + map.get("lk") + "'");
		map.put("lkyy", "'" + map.get("lkyy") + "'");
		map.put("sfjs", "'0'");
		map.put("bz", "'" + map.get("bz") + "'");
		map.put("blr", "'" + map.get("blr") + "'");
		map.put("lxfs", "'" + map.get("lxfs") + "'");
		map.put("xxly", "'警务协作平台'");
		map.put("clzt", "'0'");
		map.put("sffb", "'0'");
		map.put("zdxfsj", "sysdate");
		map.put("lrr", "'" + map.get("lrr") + "'");
		map.put("lrbm", "'" + map.get("lrbm") + "'");
		map.put("lrsj", "to_date('" + map.get("lrsj")
				+ "','yyyy-mm-dd HH24:mi')");
		String sql = alarmInfoActoin
				.insert("exch_t_trafficnewsfeeds_temp", map);
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.out.println("【警民互动】-->添加交通信息爆料失败。SQL:\n" + sql);
			logger.error("【警民互动】-->添加交通信息爆料失败。", e);
		}
		return isSuccess;
	}
	
	/**
	 * 获取拥堵报料信息筛选机构名称的SQL语句
	 * @param jgmc 机构名称
	 * @return SQL语句
	 */
	public String getGetJgidByJgmcSQL(String jgmc){
		String sql = null;
		if(jgmc != null){
			sql = "(select jgid from t_sys_department where jgmc="+jgmc+" or othername="+jgmc+")";
		}
		return sql;
	}
	

}
