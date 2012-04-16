/** 
 * 项目名称：EHL_ATMSIC_GD <br> 
 * 文件路径：com.ehl.tira.dao  <br>  
 * 文件名称：StatisticalDao.java <br> 
 * 文件编号：    <br> 
 * 文件描述：   <br> 
 *
 * 版本信息： Ver 1.1  <br>  
 * 创建日期：2011-12-14   <br>  
 * 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有     <br> 
 ************************************************** <br> 
 * 创建人：lenove <br>    
 * 创建日期：2011-12-14 上午10:57:11 <br>   
 ************* 修改历史  ************* <br> 
 * 修改人：lenove <br>    
 * 修改时间：2011-12-14 上午10:57:11 <br>    
 * 修改备注： <br>    
 */
package com.ehl.tira.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.appframe.data.sql.DBHandler;

/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 包路径：com.ehl.tira.dao <br>
 * 类名称：StatisticalDao <br>
 * 文件描述： <br>
 * 
 * @see <br>
 * @since Ver 1.1 <br>
 *        版本信息：Ver 1.1 <br>
 ********************************* <br>
 *        创建人：lenove <br>
 *        创建日期：2011-12-14 上午10:57:11 <br>
 *************        修改历史 ************* <br>
 *        修改人：lenove <br>
 *        修改时间：2011-12-14 上午10:57:11 <br>
 *        修改备注： <br>
 */

public class StatisticalDao {

	public Object[][] querySingleSeg(Map res) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String type = res.get("type").toString();
		String flowType = res.get("flowType").toString();

		String sql = "";
		Object[][] results = null;

		try {
			if (type.equals("1")) {
				sql = " select datetime,flow from( select t.datetime, decode(grouping(bianma),1,'3',bianma)bianma,sum(t.flow) flow";
				sql += " from (select distinct to_char(c.datetime, 'hh24') || '时' datetime,substr(b.bianma,0,1)bianma,";
				sql += " c.volume flow from t_road_seginfo a, t_tfm_driveway b, t_tfm_driveway_1h_flow c where a.roadsegid = b.roadsegid";
				sql += " and b.id = c.id and a.roadsegname like '%' || '"
						+ segName + "' || '%' and ((c.datetime between";
				sql += " to_date('" + startDate
						+ " 00:30:00', 'yyyy-mm-dd hh24:mi:ss') and (to_date('"
						+ startDate
						+ "  00:30:00', 'yyyy-mm-dd hh24:mi:ss') + 1)))";
				sql += " union all select datetime,bianma,flow from (select to_char(hours.everyHour, 'hh24') || '时' datetime";
				sql += " from (select to_date('"
						+ startDate
						+ " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + (level - 1) / 24 as everyHour";
				sql += " from dual connect by level <= 24) hours), (select 0 as flow from dual connect by level <= 2), (select to_char(level) bianma from dual connect by level <= 2)";
				sql += " ) t group by t.datetime,rollup(bianma)) where bianma = '"
						+ flowType + "' order by datetime asc";
				results = DBHandler.getMultiResult(sql);
				System.out.println("querySingleSegforTimes-------" + sql);
			} else if (type.equals("2")) {
				if(flowType.equals("3")) {
					flowType = "1','2";
					sql += " select roadsegname,everyDay,'3' as bianma, sum(allflow)allflow,replace(strcat(lackHours),',','&nbsp;<br>')lackHours from (";
					sql += " select roadsegname,everyday,bianma, sum(flow) allflow, decode(bianma,'1','入省，','出省，') || to_char(decode(bianma,'3', trunc((48 - sum(dutyhours)) / 2), 24 - sum(dutyhours))) || 'h' lackhours from (";
				} else {
					sql += " select roadsegname,everyday,bianma, sum(flow) allflow, to_char(decode(bianma,'3', trunc((48 - sum(dutyhours)) / 2), 24 - sum(dutyhours))) || 'h' lackhours from (";
				}
				
				sql += " select roadsegid,roadsegname, decode(grouping(bianma), 1, '3', bianma) bianma, substr(everyday,0,4) || '年' || substr(everyday,6,2) || '月' || substr(everyday,9,2) || '日' everyday,";
				sql += " sum(flow) flow, count(datetime) dutyhours";
				sql += " from (select a.roadsegid, a.roadsegname, substr(b.bianma, 0, 1) bianma, to_char(trunc(c.datetime - 1 / 24, 'dd'), 'yyyy-mm-dd') everyDay,";
				sql += " to_char((c.datetime - 1 / 24), 'hh24') datetime, c.volume flow from t_road_seginfo a, t_tfm_driveway b, t_tfm_driveway_1h_flow c";
				sql += " where a.roadsegid = b.roadsegid and a.roadid = 'SJKK' and b.id = c.id and c.datetime between";
				sql += " to_date('" + startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') and to_date('"
						+ endDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1";
				sql += " group by c.volume, a.roadsegid, a.roadsegname, b.bianma, c.datetime) group by roadsegid, roadsegname, everyday, rollup(bianma)";
				sql += " union all select roadsegid, roadsegname,bianma,everyday , flow, 0 as dutyhours from (select 0 as flow, roadsegname, roadsegid";
				sql += " from (select substr(b.bianma, 0, 1) bianma, a.roadsegname, a.roadsegid, b.id from t_road_seginfo a, t_tfm_driveway b";
				sql += " where a.roadsegid = b.roadsegid and a.roadid = 'SJKK')), (select to_char(days.everyDay, 'yyyy') || '年' || to_char(days.everyDay, 'mm') || '月' ||";
				sql += " to_char(days.everyDay, 'dd') || '日' everyDay from (select to_date('"
						+ startDate
						+ "', 'yyyy-mm-dd') + level - 1 as everyDay from dual";
				sql += " connect by level <= (to_date('" + endDate
						+ "', 'yyyy-mm-dd') - to_date('" + startDate
						+ "', 'yyyy-mm-dd')) + 1) days),";
				sql += " (select to_char(level) as bianma from dual connect by level <= 3) group by roadsegid, roadsegname,everyday, flow, bianma";
				sql += " ) where roadsegname like '%"
						+ segName
						+ "%' and bianma in ('"
						+ flowType
						+ "') group by roadsegname,everyday,bianma order by everyDay,bianma";
				if(flowType.equals("1','2")) {
					sql += ")group by roadsegname,everyDay order by everyday";
				}
				results = DBHandler.getMultiResult(sql);
				System.out.println("querySingleSegforDays-------" + sql);
			} else {
				if(flowType.equals("3")) {
					flowType = "1','2";
					sql += " select roadsegname,everyDay,'3' as bianma, sum(allflow)allflow,replace(strcat(lackHours),',','&nbsp;<br>')lackHours from (";
					sql += " select roadsegname, subStr(everyDay,0,8)everyDay, bianma, sum(allflow) allflow,";
					sql += " decode(bianma,'1','入省:','出省:') || decode(bianma,'3',sum(days) * 24 - trunc(sum(dutyDate)/2),sum(days) * 24 - sum(dutyDate)) || 'h' lackhours";
				} else {
					sql += " select roadsegname, subStr(everyDay,0,8)everyDay, bianma, sum(allflow) allflow,";
					sql += " decode(bianma,'3',sum(days) * 24 - trunc(sum(dutyDate)/2),sum(days) * 24 - sum(dutyDate)) || 'h' lackhours";
				}
				
				sql += " from (select roadsegname, subStr(everyDay,0,8) everyday, bianma, sum(flow) allflow, sum(dutyDate) dutyDate,";
				sql += " sum(days) days  from (select roadsegid, roadsegname, decode(grouping(bianma), 1, '3', bianma) bianma,";
				sql += " substr(everyday, 0, 4) || '年' || substr(everyday, 6, 2) || '月' || substr(everyday, 9, 2) || '日' everyday,";
				sql += " sum(flow) flow, count(datetime) dutyDate, 0 as days from (select a.roadsegid, a.roadsegname,";
				sql += " substr(b.bianma, 0, 1) bianma, to_char(trunc(c.datetime - 1 / 24, 'dd'), 'yyyy-mm-dd') everyDay,";
				sql += " to_char((c.datetime - 1 / 24), 'hh24') datetime, c.volume flow from t_road_seginfo a, t_tfm_driveway b,";
				sql += " t_tfm_driveway_1h_flow c where a.roadsegid = b.roadsegid and a.roadid = 'SJKK' and b.id = c.id";
				sql += " and c.datetime between to_date('" + startDate
						+ "-01 00:30:00', 'yyyy-MM-DD HH24:MI:SS') and";
				sql += " add_months(to_date('"
						+ endDate
						+ "-01 00:30:00', 'yyyy-mm-dd HH24:mi:ss') - 1, 1) + 1 group by c.volume,";
				sql += " a.roadsegid, a.roadsegname, b.bianma, c.datetime) group by roadsegid, roadsegname, everyday, rollup(bianma)";
				sql += " union all select roadsegid, roadsegname, bianma, everyday, flow, 0 as lackHours, 1 as days";
				sql += " from (select 0 as flow, roadsegname, roadsegid from (select substr(b.bianma, 0, 1) bianma, a.roadsegname,";
				sql += " a.roadsegid, b.id from t_road_seginfo a, t_tfm_driveway b where a.roadsegid = b.roadsegid and a.roadid = 'SJKK')),";
				sql += " (select to_char(days.everyDay, 'yyyy') || '年' || to_char(days.everyDay, 'mm') || '月' ||to_char(days.everyDay, 'dd') || '日'everyDay";
				sql += " from (select to_date('"
						+ startDate
						+ "-01', 'yyyy-mm-dd') + level - 1 as everyDay from dual";
				sql += " connect by level <= add_months(to_date('" + endDate
						+ "-01 00:30:00', 'yyyy-mm-dd HH24:mi:ss') - 1,";
				sql += " 1) + 1 - to_date('"
						+ startDate
						+ "-01 00:30:00', 'yyyy-MM-DD HH24:MI:SS')) days), (select to_char(level) as bianma";
				sql += " from dual connect by level <= 3) group by roadsegid, roadsegname, everyday, flow, bianma)";
				sql += " where roadsegname like '%" + segName
						+ "%'  and bianma in ('" + flowType
						+ "') group by roadsegname, everyday, bianma )";
				sql += "  group by roadsegname, bianma, everyday order by everyday, bianma";
				if(flowType.equals("1','2")) {
					sql += ")group by roadsegname,everyDay order by everyday";
				}
				results = DBHandler.getMultiResult(sql);
				System.out.println("querySingleSegforMonth-------" + sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public Object[][] getMutiSegFlow(Map res) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String flowType = res.get("flowType").toString();

		String sql = "";
		Object[][] results = null;
		try {
			if(flowType.equals("3")) {
				flowType = "1','2";
				sql += " select roadsegname,'3' as bianma, sum(flow)allflow,replace(strcat(lackHours),',','&nbsp;<br>')lackHours from (";
				sql += " select roadsegname, bianma, flow, decode(bianma,'1','入省，','出省，') || decode(bianma,'3',trunc(((to_date('" + endDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1 - to_date('" + startDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS'))*24*2 - dutyHours)/2),(to_date('" + endDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1 - to_date('" + startDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS'))*24 - dutyHours) || '小时' lackHours";
			} else {
				sql += " select roadsegname, bianma, flow, decode(bianma,'3',trunc(((to_date('" + endDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1 - to_date('" + startDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS'))*24*2 - dutyHours)/2),(to_date('" + endDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1 - to_date('" + startDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS'))*24 - dutyHours) || '小时' lackHours";
			}
			sql += " from (select roadsegname, decode(grouping(bianma), 1, 3, bianma) bianma, sum(flow) flow,";
			sql += " sum(dutyHours) dutyHours from (select roadsegid, roadsegname, everyday, sum(flow) flow, bianma, (count(datetime)) dutyHours";
			sql += " from (select temp1.roadsegid, temp1.roadsegname, to_char(trunc(temp2.datetime, 'dd'), 'yyyy-mm-dd') everyDay,";
			sql += " to_char(temp2.datetime, 'hh24') datetime, temp2.volume flow, substr(temp1.bianma, 0, 1) bianma";
			sql += " from (select substr(b.bianma, 0, 1) bianma, a.roadsegname, a.roadsegid, b.id from t_road_seginfo a, t_tfm_driveway b";
			sql += " where a.roadsegid = b.roadsegid and a.roadid = 'SJKK') temp1 left join (select id, (datetime - 1 / 24) datetime,";
			sql += " volume from t_tfm_driveway_1h_flow where datetime between to_date('"
					+ startDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') and";
			sql += " to_date('"
					+ endDate
					+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') + 1) temp2 on temp1.id = temp2.id";
			sql += " group by temp1.roadsegid, temp1.roadsegname, temp1.bianma, temp2.datetime, temp2.volume)";
			sql += " group by roadsegid, roadsegname, everyday, bianma union all select roadsegid,";
			sql += " roadsegname, everyday, flow, bianma, 0 as dutyHours from (select to_char(days.everyDay, 'yyyy-mm-dd') everyDay";
			sql += " from (select to_date('"
					+ endDate
					+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - level + 1 as everyDay from dual";
			sql += " connect by level <= to_date('" + endDate
					+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1 - to_date('"
					+ startDate + " 00:30:00',";
			sql += " 'yyyy-MM-DD HH24:MI:SS')) days), (select bianma, 0 as flow, roadsegname, roadsegid from (select substr(b.bianma, 0, 1) bianma,";
			sql += " a.roadsegname,  a.roadsegid, b.id from t_road_seginfo a, t_tfm_driveway b where a.roadsegid = b.roadsegid";
			sql += " and a.roadid = 'SJKK'))) group by roadsegid, roadsegname, rollup(bianma)) where roadsegname in ('"
					+ segName + "')";
			sql += " and bianma in ('" + flowType + "') order by roadsegname,bianma";
			if(flowType.equals("1','2")) {
				sql += " ) group by roadsegname";
			}
			
			results = DBHandler.getMultiResult(sql);
			System.out.println("getMutiSegFlow:---------" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public Object[][] getMutiSegRatio(Map res) {
		String startDate = res.get("startDate").toString();
		String segName = res.get("segName").toString();
		String flowType = res.get("flowType").toString();
		String selectItems = res.get("selectItems").toString();

		String sql = getMutiSegRatioSql(segName, flowType, selectItems,
				startDate);

		Object[][] results = null;
		try {
			results = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public String getSingleShowChartStr(HashMap res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String type = res.get("type").toString();
		String flowType = res.get("flowType").toString();

		StringBuffer showXml = new StringBuffer();
		StringBuffer categoriesXml = new StringBuffer();
		StringBuffer datasetXml = new StringBuffer();
		StringBuffer setXml = new StringBuffer();

		try {
			if (results != null) {
				if ("".equals(endDate)) {
					showXml.append("<chart caption='"
							+ segName
							+ startDate
							+ "流量统计分析图' shownames='1' showToolTip='1' showvalues='0' decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0' >");
				} else {
					showXml.append("<chart caption='"
							+ segName
							+ startDate
							+ "至"
							+ endDate
							+ "流量统计分析图' shownames='1' showToolTip='1'  showvalues='0'  decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'>");
				}
			}
			showXml.append(" <categories> ");

			if (results != null) {
				if (type.equals("1")) {
					for (int i = 0; i < results.length; i++) {
						categoriesXml.append("<category label='");
						categoriesXml.append(String.valueOf(results[i][0]));
						categoriesXml.append("' /> ");
					}
				} else {
					for (int i = 0; i < results.length; i++) {
						categoriesXml.append("<category label='");
						categoriesXml.append(String.valueOf(results[i][1]));
						categoriesXml.append("' /> ");
					}
				}
			}

			showXml.append(categoriesXml);
			showXml.append(" </categories> ");

			Object[][] averageValue = getSingleAverageFlow(res);

			List<Integer> list = new ArrayList<Integer>();

			if (averageValue != null) {
				if (type.equals("1")) {
					for (int i = 0; i < results.length; i++) {
						if (Integer.parseInt(String.valueOf(results[i][1])) > Integer
								.parseInt(String.valueOf(averageValue[i][0]))) {
							setXml.append("<set value = '");
							setXml.append(averageValue[i][0]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][1] + "辆");
							setXml.append("'/>");
							list.add(Integer.parseInt(String
									.valueOf(results[i][1]))
									- Integer.parseInt(String
											.valueOf(averageValue[i][0])));
						} else {
							setXml.append("<set value = '");
							setXml.append(results[i][1]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][1] + "辆");
							setXml.append("'/>");
							list.add(null);
						}
					}
				} else {
					for (int i = 0; i < results.length; i++) {
						if (Integer.parseInt(String.valueOf(results[i][3])) > Integer
								.parseInt(String.valueOf(averageValue[0][0]))) {
							setXml.append("<set value = '");
							setXml.append(averageValue[0][0]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][3] + "辆");
							setXml.append("'/>");
							list.add(Integer.parseInt(String
									.valueOf(results[i][3]))
									- Integer.parseInt(String
											.valueOf(averageValue[0][0])));
						} else {
							setXml.append("<set value = '");
							setXml.append(results[i][3]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][3] + "辆");
							setXml.append("'/>");
							list.add(null);
						}
					}
				}
			}
			if (flowType.equals("2")) {
				datasetXml
						.append(" <dataset  color='00FF00' showValues='0' > ");
				datasetXml.append(setXml.toString());
				datasetXml.append(" </dataset> ");
			} else if (flowType.equals("1")) {
				datasetXml.append(" <dataset  color='00FF00' showValues='0'> ");
				datasetXml.append(setXml.toString());
				datasetXml.append(" </dataset> ");
			} else if (flowType.equals("3")) {
				datasetXml.append(" <dataset  color='00FF00' showValues='0'> ");
				datasetXml.append(setXml.toString());
				datasetXml.append(" </dataset> ");
			}
			datasetXml.append("<dataset  color='00FF00' showValues='0'>");
			if (type.equals("1")) {
				for (int i = 0; i < list.size(); i++) {
					datasetXml.append("<set color='FFFF00' value='");
					datasetXml.append(list.get(i));
					datasetXml.append("' toolText='");
					datasetXml.append(results[i][0] + ",");
					datasetXml.append(results[i][1] + "辆");
					datasetXml.append("'/>");
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					datasetXml.append("<set color='FFFF00' value='");
					datasetXml.append(list.get(i));
					datasetXml.append("' toolText='");
					datasetXml.append(results[i][0] + ",");
					datasetXml.append(results[i][3] + "辆");
					datasetXml.append("'/>");
				}
			}
			datasetXml.append(" </dataset> ");
			showXml.append(datasetXml);

			if (type.equals("2")) {
				showXml.append("<trendlines><line startValue='");
				showXml.append(averageValue[0][0]);
				showXml.append("' color='FF0000' displayValue='日平均流量' thickness='1' valueOnRight='1' toolText='");
				showXml.append(averageValue[0][0] + "辆");
				showXml.append("' showOnTop='1' /></trendlines>");
			} else if (type.equals("3")) {
				showXml.append("<trendlines><line startValue='");
				showXml.append(averageValue[0][0]);
				showXml.append("' color='FF0000' displayValue='月平均流量' thickness='1' valueOnRight='1' showOnTop='1' toolText='");
				showXml.append(averageValue[0][0] + "辆");
				showXml.append("'/></trendlines>");
			}

			showXml.append(" </chart>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return showXml.toString();
	}

	public static String getSingleShowTableStr(HashMap res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String type = res.get("type").toString();
		String flowType = res.get("flowType").toString();

		StringBuffer showXml = new StringBuffer();
		StringBuffer titleStr = new StringBuffer();

		if ("".equals(endDate)) {
			titleStr.append(segName + startDate + "流量统计分析表");
		} else {
			titleStr.append(segName + startDate + "至" + endDate + "流量统计分析表");
		}

		showXml.append("<table width=\"800\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px;overflow: auto;\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='left' height='20'  colspan='"
				+ results.length + 1 + "'>");
		showXml.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		showXml.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");
		showXml.append("<tr>");
		showXml.append("<td align='center' height='20' class='td_r_b' style=\"white-space:nowrap\"> 卡口/");

		if (type.equals("1")) {
			showXml.append("时间");
		} else if (type.equals("2")) {
			showXml.append("日期");
		} else {
			showXml.append("月份");
		}
		showXml.append("</td>");

		if (type.equals("1")) {
			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' height='20' class='td_r_b'>");
				showXml.append(results[i][0]);
				showXml.append("</td>");
			}
			showXml.append("</tr>");

			showXml.append("<tr>");
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			if (flowType.equals("2")) {
				showXml.append("出省流量");
			} else if (flowType.equals("1")) {
				showXml.append("入省流量");
			} else {
				showXml.append("双向流量");
			}
			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' height='20' class='td_r_b'>");
				showXml.append(results[i][1] + "辆");
				showXml.append("</td>");
			}
			showXml.append("</tr>");
		} else {
			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' height='20' class='td_r_b' style=\"white-space:nowrap\">");
				showXml.append(results[i][1]);
				showXml.append("</td>");
			}
			showXml.append("</tr>");

			showXml.append("<tr>");
			showXml.append("<td align='center' height='20' class='td_r_b' style=\"white-space:nowrap\">");
			if (flowType.equals("2")) {
				showXml.append("出省流量");
			} else if (flowType.equals("1")) {
				showXml.append("入省流量");
			} else {
				showXml.append("双向流量");
			}
			showXml.append("</td>");

			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' class='td_r_b' style=\"white-space:nowrap;line-height:18px;\">");
				if (String.valueOf(results[i][4]).equals("0")) {
					showXml.append("&nbsp;" + results[i][3] + "辆(完整)");
				} else {
					showXml.append("&nbsp;" + results[i][3] + "辆(缺失:" + results[i][4] + ")");
				}
				showXml.append("</td>");
			}
			showXml.append("</tr>");
		}

		showXml.append("</table>");
		return String.valueOf(showXml);
	}

	public static Object[][] getSingleAverageFlow(Map res) {
		String segName = res.get("segName").toString();
		String flowType = res.get("flowType").toString();
		String type = res.get("type").toString();
		String sql = "";
		if (type.equals("1")) {
			sql += " select flow from (select decode(grouping(direction), 1, '3', direction) direction,hour,";
			sql += " decode(decode(grouping(direction), 1, '3', direction),'3',trunc(sum(flow)/2),sum(flow)) flow";
			sql += " from T_TFM_FLOWHISTORYAVG t, (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma";
			sql += " from t_road_seginfo t1, t_tfm_driveway t2 where t1.roadid = 'SJKK' and t1.roadsegname like '%"
					+ segName + "%'";
			sql += " and t1.roadsegid = t2.roadsegid) tt  where t.bid = tt.roadsegid and t.direction = tt.bianma";
			sql += " and to_char(sysdate, 'yyyy') = year group by roadsegid, roadsegname,hour, rollup(direction))  where direction = '"
					+ flowType + "'";

		} else if (type.equals("2")) {
			sql += " select flow from (select decode(grouping(direction), 1, '3', direction) direction,sum(flow) flow";
			sql += " from T_TFM_FLOWHISTORYAVG t, (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma from t_road_seginfo t1, t_tfm_driveway t2";
			sql += " where t1.roadid = 'SJKK'  and t1.roadsegname like '%"
					+ segName + "%'";
			sql += " and t1.roadsegid = t2.roadsegid) tt where t.bid = tt.roadsegid and t.direction = tt.bianma and to_char(sysdate, 'yyyy') = year";
			sql += " group by roadsegid,roadsegname,rollup(direction))  where direction = '"
					+ flowType + "'";
		} else if (type.equals("3")) {
			sql += " select flow * 30 from (select decode(grouping(direction), 1, '3', direction) direction,sum(flow) flow";
			sql += " from T_TFM_FLOWHISTORYAVG t, (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma from t_road_seginfo t1, t_tfm_driveway t2";
			sql += " where t1.roadid = 'SJKK'  and t1.roadsegname like '%"
					+ segName + "%'";
			sql += " and t1.roadsegid = t2.roadsegid) tt where t.bid = tt.roadsegid and t.direction = tt.bianma and to_char(sysdate, 'yyyy') = year";
			sql += " group by roadsegid,roadsegname,rollup(direction))  where direction = '"
					+ flowType + "'";
		}

		Object[][] results = DBHandler.getMultiResult(sql);
		return results;
	}

	public static Object[][] getMutiAverageFlow(Map res) {
		String segName = res.get("segName").toString();
		String flowType = res.get("flowType").toString();
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();

		String[] sd = startDate.split("-");
		String[] ed = endDate.split("-");

		Date start = new Date(Integer.parseInt(sd[0]),
				Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[2]));
		Date end = new Date(Integer.parseInt(ed[0]),
				Integer.parseInt(ed[1]) - 1, Integer.parseInt(ed[2]));
		int days = (int) ((end.getTime() - start.getTime()) / 1000 / 3600 / 24 + 1);

		String sql = "";
		sql += " select roadsegName, flow, direction from (select roadsegName, decode(decode(grouping(direction),1,'3',direction),'3',trunc(sum(flow) * "
				+ days + " / 2),sum(flow) * " + days + ") flow,";
		sql += " decode(grouping(direction),1,'3',direction)direction from T_TFM_FLOWHISTORYAVG t,";
		sql += " (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma from t_road_seginfo t1, t_tfm_driveway t2";
		sql += " where t1.roadid = 'SJKK' and t1.roadsegname in ('" + segName
				+ "') and t1.roadsegid = t2.roadsegid) tt";
		sql += " where t.bid = tt.roadsegid and t.direction = tt.bianma and to_char(sysdate, 'yyyy') = year  group by roadsegname ,rollup(direction)";
		sql += " ) where direction = '" + flowType + "' order by roadsegName";

		Object[][] results = DBHandler.getMultiResult(sql);

		return results;
	}

	public static String getMutileShowChartStr(Map res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String flowType = res.get("flowType").toString();

		String titleStr = "广东省" + startDate + "至" + endDate + "省际卡口流量统计图";
		StringBuffer toXmlStr = new StringBuffer();

		toXmlStr.append("<chart caption='"
				+ titleStr
				+ "' shownames='1' showToolTip='1'  showvalues='0'  decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'>");
		toXmlStr.append(" <categories> ");

		for (int i = 0; i < results.length; i++) {
			toXmlStr.append(" <category label = '");
			toXmlStr.append(String.valueOf(results[i][0]));
			toXmlStr.append("'/>");
		}
		toXmlStr.append(" </categories> ");

		List<Integer> list = new ArrayList<Integer>();
		Object[][] averFlows = getMutiAverageFlow(res);

		if (flowType.equals("1")) {
			toXmlStr.append("<dataset color='00FF00' showValues='0' seriesname='入省流量' > ");
		} else if (flowType.equals("2")) {
			toXmlStr.append("<dataset color='00FF00' showValues='0' seriesname='出省流量' > ");
		} else if (flowType.equals("3")) {
			toXmlStr.append("<dataset color='00FF00' showValues='0' seriesname='双向流量' > ");
		}

		for (int i = 0; i < results.length; i++) {
			if (Integer.parseInt(String.valueOf(results[i][2])) > Integer
					.parseInt(String.valueOf(averFlows[i][1]))) {
				toXmlStr.append("<set value = '");
				toXmlStr.append(averFlows[i][1]);
				toXmlStr.append("' toolText='");
				toXmlStr.append(results[i][0] + ",");
				toXmlStr.append(results[i][2] + "辆");
				toXmlStr.append("'/>");
				list.add(Integer.parseInt(String.valueOf(results[i][2]))
						- Integer.parseInt(String.valueOf(averFlows[i][1])));
			} else {
				toXmlStr.append("<set value = '");
				toXmlStr.append(results[i][2]);
				toXmlStr.append("' toolText='");
				toXmlStr.append(results[i][0] + ",");
				toXmlStr.append(results[i][2] + "辆");
				toXmlStr.append("'/>");
				list.add(null);
			}
		}
		toXmlStr.append("</dataset>");

		toXmlStr.append("<dataset  color='00FF00' showValues='0'>");
		for (int i = 0; i < list.size(); i++) {
			toXmlStr.append("<set color='FFFF00' value='");
			toXmlStr.append(list.get(i));
			toXmlStr.append("' toolText='");
			toXmlStr.append(results[i][0] + ",");
			toXmlStr.append(results[i][2] + "辆");
			toXmlStr.append("'/>");
		}
		toXmlStr.append(" </dataset> ");

		toXmlStr.append("</chart>");
		return toXmlStr.toString();
	}

	public static String getMutileShowTableStr(Map res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String flowType = res.get("flowType").toString();

		StringBuffer showXml = new StringBuffer();
		StringBuffer titleStr = new StringBuffer();

		titleStr.append("广东省" + startDate + "至" + endDate + "卡口日均流量统计表");

		showXml.append("<table width=\"800\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='center' height='20'  colspan='14'>");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");

		showXml.append("<tr>");
		showXml.append("<td align='center' height='20' class='td_r_b'>");
		showXml.append("卡口名称");
		showXml.append("</td>");

		if (flowType.equals("1")) {
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append("入省流量(单位：辆)");
			showXml.append("</td>");
		} else if (flowType.equals("2")) {
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append("出省流量(单位：辆)");
			showXml.append("</td>");
		} else if (flowType.equals("3")) {
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append("双向流量(单位：辆)");
			showXml.append("</td>");
		}

		showXml.append("<td align='center' height='20' class='td_r_b'>");
		showXml.append("数据完整情况");
		showXml.append("</td>");

		showXml.append("</tr>");

		for (int i = 0; i < results.length; i++) {
			showXml.append("<tr>");
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append(String.valueOf(results[i][0]).replaceAll(
					"\\(.*?\\)", ""));
			showXml.append("</td>");

			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append(results[i][2]);
			showXml.append("</td>");

			showXml.append("<td align='center' class='td_r_b' style='line-height:18px;'>");
			if (String.valueOf(results[i][3]).equals("0")) {
				showXml.append("完整");
			} else {
				showXml.append("缺失:" + results[i][3]);
			}
			showXml.append("</td>");
			showXml.append("</tr>");
		}

		showXml.append("</table>");
		return showXml.toString();
	}

	public static String getMutiRatioTableStr(Map res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String flowType = res.get("flowType").toString();
		String selectItems = res.get("selectItems").toString();
		String[] items = selectItems.split(",");

		StringBuffer showXml = new StringBuffer();
		StringBuffer titleStr = new StringBuffer();

		titleStr.append("广东省" + startDate + "卡口日均流量统计表");

		showXml.append("<table width=\"800\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='center' height='20'  colspan='14'>");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");

		showXml.append("<tr>");
		showXml.append("<td align='center' height='20' class='td_r_b' rowspan='2' width = '20%''>卡口名称");
		showXml.append("</td>");
		if (selectItems.indexOf("1") != -1) {
			showXml.append("<td align='center' height='20' class='td_r_b' colspan='2'>");
			showXml.append("3日");
			showXml.append("</td>");
		}

		if (selectItems.indexOf("2") != -1) {
			showXml.append("<td align='center' height='20' class='td_r_b' colspan='2'>");
			showXml.append("7日");
			showXml.append("</td>");
		}

		if (selectItems.indexOf("3") != -1) {
			showXml.append("<td align='center' height='20' class='td_r_b' colspan='2'>");
			showXml.append("15日");
			showXml.append("</td>");
		}

		if (selectItems.indexOf("4") != -1) {
			showXml.append("<td align='center' height='20' class='td_r_b' colspan='2'>");
			showXml.append("30日");
			showXml.append("</td>");
		}

		showXml.append("</tr>");

		showXml.append("<tr>");

		for (int i = 0; i < items.length; i++) {
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append("平均流量");
			showXml.append("</td>");
			showXml.append("<td align='center' height='20' class='td_r_b'>");
			showXml.append("环比增减%");
			showXml.append("</td>");
		}

		showXml.append("</tr>");

		for (int i = 0; i < results.length; i++) {
			showXml.append("<tr>");
			showXml.append("<td align='center' class='td_r_b'>");
			showXml.append(String.valueOf(results[i][0]).replaceAll(
					"\\(.*?\\)", ""));
			showXml.append("</td>");

			for (int j = 2; j < results[i].length; j++) {
				showXml.append("<td align='center' class='td_r_b' style='line-height:18px;'> ");
				showXml.append("&nbsp;" + results[i][j]);
				showXml.append("</td>");
			}
			showXml.append("</tr>");
		}

		showXml.append("</table>");
		return showXml.toString();
	}

	public static String getMutiSegRatioSql(String segName, String flowType,
			String selectItems, String startDate) {

		StringBuffer selectSql = new StringBuffer();
		String leftJoin = " left join ";
		String orderBy = " order by roadsegname,bianma ";
		String threeDaysRow = "";
		String sevenDaysRow = "";
		String fifteenDaysRow = "";
		String thirtyDaysRow = "";
		StringBuffer bothwaySql = new StringBuffer("select roadsegName, '3' as bianma,");
		StringBuffer bothwaySql1 = new StringBuffer();
		StringBuffer bothwaySql2 = new StringBuffer();
		StringBuffer bothwaySql3 = new StringBuffer();
		StringBuffer bothwaySql4 = new StringBuffer();

		String[] days = selectItems.split(",");
		String[] threeDays = new String[2];
		String[] sevenDays = new String[2];
		String[] fifteenDays = new String[2];
		String[] thirtyDays = new String[2];

		String threeMainStr = "";
		String threeRatioStr = "";
		String sevenMainStr = "";
		String sevenRatioStr = "";
		String fifteenMainStr = "";
		String fifteenRatioStr = "";
		String thirtyMainStr = "";
		String thirtyRatioStr = "";

		String temp1 = "temp1 on temp.roadsegname = temp1.roadsegname and temp.bianma = temp1.bianma";
		String temp2 = "temp2 on temp.roadsegname = temp2.roadsegname and temp.bianma = temp2.bianma";
		String temp3 = "temp3 on temp.roadsegname = temp3.roadsegname and temp.bianma = temp3.bianma";
		String temp4 = "temp4 on temp.roadsegname = temp4.roadsegname and temp.bianma = temp4.bianma";
		String temp5 = "temp5 on temp.roadsegname = temp5.roadsegname and temp.bianma = temp5.bianma";
		String temp6 = "temp6 on temp.roadsegname = temp6.roadsegname and temp.bianma = temp6.bianma";
		String temp7 = "temp7 on temp.roadsegname = temp7.roadsegname and temp.bianma = temp7.bianma";
		String temp8 = "temp8 on temp.roadsegname = temp8.roadsegname and temp.bianma = temp8.bianma";

		String fromStr = "";
		
		if(flowType.equals("3")) {
			flowType = "1','2";
		}
		
		fromStr += " from (select a.roadsegname, b.bianma";
		fromStr += " from t_road_seginfo a, (select to_char(level) bianma from dual connect by level <= 3) b";
		fromStr += " where a.roadid = 'SJKK' and roadsegName in ('" + segName
				+ "') and bianma in ('" + flowType + "')) temp";
		selectSql.append("select temp.roadsegname, temp.bianma, ");

		String mainStr1 = "";
		mainStr1 += " (select roadsegid, roadsegname, decode(grouping(bianma), 1, '3', bianma) bianma, sum(lackHours) lackHours,";
		mainStr1 += " trunc(decode(decode(grouping(bianma), 1, '3', bianma), '3', sum(averflow) / 2, sum(averflow))) averflow";
		mainStr1 += " from (select roadsegid, roadsegname, bianma, sum(flow) allflow, (24 * ";

		String mainStr2 = "";
		mainStr2 += " - sum(dutyHours)) lackHours,";
		mainStr2 += " trunc(decode(sum(dutyHours), 0, 0,  sum(flow) / sum(dutyHours) * 24)) averflow from (select roadsegid,";
		mainStr2 += " roadsegname, bianma, sum(flow) flow, count(datetime) dutyHours from (select a.roadsegid, a.roadsegname,";
		mainStr2 += " substr(b.bianma, 0, 1) bianma, to_char(trunc(c.datetime - 1 / 24, 'dd'), 'yyyy-mm-dd') everyDay,";
		mainStr2 += " to_char((c.datetime - 1 / 24), 'hh24') datetime, c.volume flow from t_road_seginfo a, t_tfm_driveway b,";
		mainStr2 += " t_tfm_driveway_1h_flow c  where a.roadsegid = b.roadsegid  and a.roadid = 'SJKK' and b.id = c.id";
		mainStr2 += " and c.datetime between ";

		String secondStr = " group by c.volume, a.roadsegid, a.roadsegname, b.bianma, c.datetime) group by roadsegid, roadsegname, bianma";
		secondStr += " union all select roadsegid, roadsegname, bianma, flow, 0 as dutyHours from (select 0 as flow, roadsegname, roadsegid";
		secondStr += " from (select substr(b.bianma, 0, 1) bianma,  a.roadsegname, a.roadsegid, b.id from t_road_seginfo a,";
		secondStr += " t_tfm_driveway b where a.roadsegid = b.roadsegid  and a.roadid = 'SJKK')), (select to_char(level) as bianma";
		secondStr += " from dual connect by level <= 2) group by roadsegid, roadsegname, flow, bianma)  group by roadsegid, roadsegname, bianma) group by roadsegid, roadsegname, rollup(bianma))";

		for (int i = 0; i < days.length; i++) {
			if (days[i].equals("1")) {
				if(flowType.equals("1','2")) {
					bothwaySql1.append(" sum(three) || decode(replace(strcat(threelackhours), ',', ';'),'入省:0h;出省:0h','(完整','(缺失:' || replace(strcat(threelackhours), ',', '<br>'))  || ')' three,decode(sum(threeround),0,0,trunc(sum(three) / sum(threeround) * 100)) || '%' threeround");
					threeDaysRow = " temp1.averFlow three, temp2.averflow threeround, decode(temp.bianma,'1','入省:','出省:') || temp1.lackHours || 'h' threelackHours";
				} else {
					threeDaysRow = " temp1.averFlow || decode(temp1.lackHours,0,'(完整)','(缺失:'|| decode(temp.bianma,3,trunc(temp1.lackHours/2),temp1.lackHours) || 'h)') as \"3日平均流量\",";
					threeDaysRow += " decode(temp2.averflow,0,0,trunc((1 - temp1.averflow/temp2.averflow)*100)) || '%' as \"3日环比增减\"";
				}
				
				threeDays[0] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 2 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') + 1";
				threeDays[1] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 5 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') - 2";
				threeMainStr = mainStr1 + " 3 " + mainStr2 + threeDays[0]
						+ secondStr + temp1;
				threeRatioStr = mainStr1 + " 3 " + mainStr2 + threeDays[1]
						+ secondStr + temp2;
			} else if (days[i].equals("2")) {
				if(flowType.equals("1','2")) {
					bothwaySql2.append(" sum(seven) || decode(replace(strcat(sevenlackhours), ',', ';'),'入省:0h;出省:0h','(完整','(缺失:' || replace(strcat(sevenlackhours), ',', '<br>'))  || ')' seven,decode(sum(sevenround),0,0,trunc(sum(seven) / sum(sevenround) * 100)) || '%' sevenround");
					sevenDaysRow = " temp3.averFlow seven,temp4.averflow sevenround,decode(temp.bianma,'1','入省:','出省:') || temp3.lackHours || 'h' sevenlackHours";
				} else {
					sevenDaysRow = " temp3.averFlow || decode(temp3.lackHours,0,'(完整)','(缺失:'|| decode(temp.bianma,3,trunc(temp3.lackHours/2),temp3.lackHours) || 'h)')as \"7日平均流量\", ";
					sevenDaysRow += " decode(temp4.averflow,0,0,trunc((1 - temp3.averflow/temp4.averflow)*100)) || '%' as \"7日环比增减\"";
				}
				
				sevenDays[0] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 6 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') + 1";
				sevenDays[1] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 13 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') - 6";
				sevenMainStr += mainStr1 + " 7 " + mainStr2 + sevenDays[0]
						+ secondStr + temp3;
				sevenRatioStr += mainStr1 + " 7 " + mainStr2 + sevenDays[1]
						+ secondStr + temp4;
			} else if (days[i].equals("3")) {
				if(flowType.equals("1','2")) {
					bothwaySql3.append(" sum(fifteen) || decode(replace(strcat(fifteenlackhours), ',', ';'),'入省:0h;出省:0h','(完整','(缺失:' || replace(strcat(fifteenlackhours), ',', '<br>'))  || ')' fifteen, decode(sum(fifteenround), 0, 0, trunc(sum(fifteen) / sum(fifteenround) * 100)) || '%' fifteenround");
					fifteenDaysRow = " temp5.averFlow fifteen,temp6.averflow fifteenround,decode(temp.bianma,'1','入省:','出省:') || temp5.lackHours || 'h' fifteenlackHours";
				} else {
					fifteenDaysRow = " temp5.averFlow || decode(temp5.lackHours,0,'(完整)','(缺失:'|| decode(temp.bianma,3,trunc(temp5.lackHours/2),temp5.lackHours) || 'h)')  as \"15日平均流量\",";
					fifteenDaysRow += " decode(temp6.averflow,0,0,trunc((1 - temp5.averflow/temp6.averflow)*100)) || '%' as \"15日环比增减\"";
				}
				fifteenDays[0] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 14 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') + 1";
				fifteenDays[1] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 29 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') - 14";
				fifteenMainStr += mainStr1 + " 15 " + mainStr2 + fifteenDays[0]
						+ secondStr + temp5;
				fifteenRatioStr += mainStr1 + " 15 " + mainStr2
						+ fifteenDays[1] + secondStr + temp6;
			} else if (days[i].equals("4")) {
				if(flowType.equals("1','2")) {
					bothwaySql4.append(" sum(thirty) || decode(replace(strcat(thirtylackhours), ',', ';'),'入省:0h;出省:0h','(完整','(缺失:' || replace(strcat(thirtylackhours), ',', '<br>'))  || ')' thirty,decode(sum(thirtyround), 0, 0, trunc(sum(thirty) / sum(thirtyround) * 100)) || '%' thirtyround");
					thirtyDaysRow = " temp7.averFlow thirty, temp8.averflow thirtyround, decode(temp.bianma,'1','入省:','出省:') || temp7.lackHours || 'h' thirtylackHours";
				} else {
					thirtyDaysRow = " temp7.averFlow || decode(temp7.lackHours,0,'(完整)','(缺失:'|| decode(temp.bianma,3,trunc(temp7.lackHours/2),temp7.lackHours) || 'h)') as \"30日平均流量\", ";
					thirtyDaysRow += " decode(temp8.averflow,0,0,trunc((1 - temp7.averflow/temp8.averflow)*100)) || '%' as \"30日环比增减\"";
				}
				thirtyDays[0] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 29 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') + 1";
				thirtyDays[1] = " to_date('"
						+ startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') - 59 and to_date('"
						+ startDate
						+ " 00:30:00',  'yyyy-MM-DD HH24:MI:SS') - 29";
				thirtyMainStr += mainStr1 + " 30 " + mainStr2 + thirtyDays[0]
						+ secondStr + temp7;
				thirtyRatioStr += mainStr1 + " 30 " + mainStr2 + thirtyDays[1]
						+ secondStr + temp8;
			}

		}

		if (selectItems.indexOf("1") != -1) {
			bothwaySql.append(bothwaySql1);
			selectSql.append(threeDaysRow);
			if (selectItems.indexOf("2") == -1
					&& selectItems.indexOf("3") == -1
					&& selectItems.indexOf("4") == -1) {
			} else {
				bothwaySql.append(",");
				selectSql.append(",");
			}
		}

		if (selectItems.indexOf("2") != -1) {     
			bothwaySql.append(bothwaySql2);
			selectSql.append(sevenDaysRow);
			if (selectItems.indexOf("3") == -1
					&& selectItems.indexOf("4") == -1) {
			} else {
				bothwaySql.append(",");
				selectSql.append(",");
			}
		}

		if (selectItems.indexOf("3") != -1) {
			bothwaySql.append(bothwaySql3);
			selectSql.append(fifteenDaysRow);
			if (selectItems.indexOf("4") == -1) {
			} else {
				bothwaySql.append(",");
				selectSql.append(",");
			}
		}

		if (selectItems.indexOf("4") != -1) {
			bothwaySql.append(bothwaySql4);
			selectSql.append(thirtyDaysRow);
		}

		selectSql.append(fromStr).append(leftJoin);

		if (selectItems.indexOf("1") != -1) {
			selectSql.append(threeMainStr).append(leftJoin)
					.append(threeRatioStr);
			if (selectItems.indexOf("2") == -1
					&& selectItems.indexOf("3") == -1
					&& selectItems.indexOf("4") == -1) {
			} else {
				selectSql.append(leftJoin);
			}
		}

		if (selectItems.indexOf("2") != -1) {
			selectSql.append(sevenMainStr).append(leftJoin)
					.append(sevenRatioStr);
			if (selectItems.indexOf("3") == -1
					&& selectItems.indexOf("4") == -1) {
			} else {
				selectSql.append(leftJoin);
			}
		}

		if (selectItems.indexOf("3") != -1) {
			selectSql.append(fifteenMainStr).append(leftJoin)
					.append(fifteenRatioStr);
			if (selectItems.indexOf("4") == -1) {
			} else {
				selectSql.append(leftJoin);
			}
		}

		if (selectItems.indexOf("4") != -1) {
			selectSql.append(thirtyMainStr).append(leftJoin)
					.append(thirtyRatioStr);
		}
		
		if(flowType.equals("1','2")) {
			selectSql = bothwaySql.append(" from (").append(selectSql);
			selectSql.append(" ) group by roadsegname");
		}
		
		return selectSql.toString();
	}

	public Object[][] getCitySeg(Map res) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String type = res.get("type").toString();
		String flowType = res.get("flowType").toString();

		String sql = "";
		Object[][] results = null;

		try {
			if (type.equals("1")) {
				sql = " select datetime,flow from( select t.datetime, decode(grouping(bianma),1,'3',bianma)bianma,sum(t.flow) flow";
				sql += " from (select distinct to_char(c.datetime, 'hh24') || '时' datetime,substr(b.bianma,0,1)bianma,";
				sql += " c.volume flow from t_road_seginfo a, t_tfm_driveway b, t_tfm_driveway_1h_flow c where a.roadsegid = b.roadsegid";
				sql += " and b.id = c.id and a.roadsegname like '%' || '"
						+ segName + "' || '%' and ((c.datetime between";
				sql += " to_date('" + startDate
						+ " 00:30:00', 'yyyy-mm-dd hh24:mi:ss') and (to_date('"
						+ startDate
						+ "  00:30:00', 'yyyy-mm-dd hh24:mi:ss') + 1)))";
				sql += " union all select datetime,bianma,flow from (select to_char(hours.everyHour, 'hh24') || '时' datetime";
				sql += " from (select to_date('"
						+ startDate
						+ " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + (level - 1) / 24 as everyHour";
				sql += " from dual connect by level <= 24) hours), (select 0 as flow from dual connect by level <= 2), (select to_char(level) bianma from dual connect by level <= 2)";
				sql += " ) t group by t.datetime,rollup(bianma)) where bianma = '"
						+ flowType + "' order by datetime asc";
				results = DBHandler.getMultiResult(sql);
				System.out.println("getCityFlowforTimes-------" + sql);
			} else if (type.equals("2")) {
				if(flowType.equals("3")) {
					flowType = "1','2";
					sql += " select roadsegname,everyDay,'3' as bianma, sum(allflow)allflow,replace(strcat(lackHours),',','<br>')lackHours from (";
					sql += " select roadsegname,everyday,bianma, sum(flow) allflow, decode(bianma,'1','入城，','出城，') || to_char(decode(bianma,'3', trunc((48 - sum(dutyhours)) / 2), 24 - sum(dutyhours))) || 'h' lackhours from (";
				} else {
					sql += " select roadsegname,everyday,bianma, sum(flow) allflow, to_char(decode(bianma,'3', trunc((48 - sum(dutyhours)) / 2), 24 - sum(dutyhours))) || 'h' lackhours from (";
				}
				
				sql += " select roadsegid,roadsegname, decode(grouping(bianma), 1, '3', bianma) bianma, substr(everyday,0,4) || '年' || substr(everyday,6,2) || '月' || substr(everyday,9,2) || '日' everyday,";
				sql += " sum(flow) flow, count(datetime) dutyhours";
				sql += " from (select a.roadsegid, a.roadsegname, substr(b.bianma, 0, 1) bianma, to_char(trunc(c.datetime - 1 / 24, 'dd'), 'yyyy-mm-dd') everyDay,";
				sql += " to_char((c.datetime - 1 / 24), 'hh24') datetime, c.volume flow from t_road_seginfo a, t_tfm_driveway b, t_tfm_driveway_1h_flow c";
				sql += " where a.roadsegid = b.roadsegid and a.roadid != 'SJKK' and b.id = c.id and c.datetime between";
				sql += " to_date('" + startDate
						+ " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') and to_date('"
						+ endDate + " 00:30:00', 'yyyy-MM-DD HH24:MI:SS') + 1";
				sql += " group by c.volume, a.roadsegid, a.roadsegname, b.bianma, c.datetime) group by roadsegid, roadsegname, everyday, rollup(bianma)";
				sql += " union all select roadsegid, roadsegname,bianma,everyday , flow, 0 as dutyhours from (select 0 as flow, roadsegname, roadsegid";
				sql += " from (select substr(b.bianma, 0, 1) bianma, a.roadsegname, a.roadsegid, b.id from t_road_seginfo a, t_tfm_driveway b";
				sql += " where a.roadsegid = b.roadsegid and a.roadid != 'SJKK')), (select to_char(days.everyDay, 'yyyy') || '年' || to_char(days.everyDay, 'mm') || '月' ||";
				sql += " to_char(days.everyDay, 'dd') || '日' everyDay from (select to_date('"
						+ startDate
						+ "', 'yyyy-mm-dd') + level - 1 as everyDay from dual";
				sql += " connect by level <= (to_date('" + endDate
						+ "', 'yyyy-mm-dd') - to_date('" + startDate
						+ "', 'yyyy-mm-dd')) + 1) days),";
				sql += " (select to_char(level) as bianma from dual connect by level <= 3) group by roadsegid, roadsegname,everyday, flow, bianma";
				sql += " ) where roadsegname like '%"
						+ segName
						+ "%' and bianma in ('"
						+ flowType
						+ "') group by roadsegname,everyday,bianma order by everyday,bianma";
				if(flowType.equals("1','2")) {
					sql += " )group by roadsegname,everyDay order by everyday";
				}
				results = DBHandler.getMultiResult(sql);
				System.out.println("getCityFlowforDays-------" + sql);
			} else {
				if(flowType.equals("3")) {
					flowType = "1','2";
					sql += " select roadsegname,everyDay,'3' as bianma, sum(allflow)allflow,replace(strcat(lackHours),',','<br>')lackHours from (";
					sql += " select roadsegname, subStr(everyDay,0,8)everyDay, bianma, sum(allflow) allflow,";
					sql += " decode(bianma,'1','入省:','出省:') || decode(bianma,'3',sum(days) * 24 - trunc(sum(dutyDate)/2),sum(days) * 24 - sum(dutyDate)) || 'h' lackhours";
				} else {
					sql += " select roadsegname, subStr(everyDay,0,8)everyDay, bianma, sum(allflow) allflow,";
					sql += " decode(bianma,'1','入省:','出省:') || decode(bianma,'3',sum(days) * 24 - trunc(sum(dutyDate)/2),sum(days) * 24 - sum(dutyDate)) || 'h' lackhours";
				}
				sql += " from (select roadsegname, subStr(everyDay,0,8) everyday, bianma, sum(flow) allflow, sum(dutyDate) dutyDate,";
				sql += " sum(days) days  from (select roadsegid, roadsegname, decode(grouping(bianma), 1, '3', bianma) bianma,";
				sql += " substr(everyday, 0, 4) || '年' || substr(everyday, 6, 2) || '月' || substr(everyday, 9, 2) || '日' everyday,";
				sql += " sum(flow) flow, count(datetime) dutyDate, 0 as days from (select a.roadsegid, a.roadsegname,";
				sql += " substr(b.bianma, 0, 1) bianma, to_char(trunc(c.datetime - 1 / 24, 'dd'), 'yyyy-mm-dd') everyDay,";
				sql += " to_char((c.datetime - 1 / 24), 'hh24') datetime, c.volume flow from t_road_seginfo a, t_tfm_driveway b,";
				sql += " t_tfm_driveway_1h_flow c where a.roadsegid = b.roadsegid and a.roadid != 'SJKK' and b.id = c.id";
				sql += " and c.datetime between to_date('" + startDate
						+ "-01 00:30:00', 'yyyy-MM-DD HH24:MI:SS') and";
				sql += " add_months(to_date('"
						+ endDate
						+ "-01 00:30:00', 'yyyy-mm-dd HH24:mi:ss') - 1, 1) + 1 group by c.volume,";
				sql += " a.roadsegid, a.roadsegname, b.bianma, c.datetime) group by roadsegid, roadsegname, everyday, rollup(bianma)";
				sql += " union all select roadsegid, roadsegname, bianma, everyday, flow, 0 as lackHours, 1 as days";
				sql += " from (select 0 as flow, roadsegname, roadsegid from (select substr(b.bianma, 0, 1) bianma, a.roadsegname,";
				sql += " a.roadsegid, b.id from t_road_seginfo a, t_tfm_driveway b where a.roadsegid = b.roadsegid and a.roadid != 'SJKK')),";
				sql += " (select to_char(days.everyDay, 'yyyy') || '年' || to_char(days.everyDay, 'mm') || '月' ||to_char(days.everyDay, 'dd') || '日'everyDay";
				sql += " from (select to_date('"
						+ startDate
						+ "-01', 'yyyy-mm-dd') + level - 1 as everyDay from dual";
				sql += " connect by level <= add_months(to_date('" + endDate
						+ "-01 00:30:00', 'yyyy-mm-dd HH24:mi:ss') - 1,";
				sql += " 1) + 1 - to_date('"
						+ startDate
						+ "-01 00:30:00', 'yyyy-MM-DD HH24:MI:SS')) days), (select to_char(level) as bianma";
				sql += " from dual connect by level <= 3) group by roadsegid, roadsegname, everyday, flow, bianma)";
				sql += " where roadsegname like '%" + segName
						+ "%'  and bianma in ('" + flowType
						+ "') group by roadsegname, everyday, bianma )";
				sql += "  group by roadsegname, bianma, everyday order by everyday, bianma";
				if(flowType.equals("1','2")) {
					sql += " )group by roadsegname,everyDay order by everyday";
				}
				results = DBHandler.getMultiResult(sql);
				System.out.println("getCityFlowforMonth-------" + sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public String getCitySegShowChartStr(HashMap res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String type = res.get("type").toString();
		String flowType = res.get("flowType").toString();

		StringBuffer showXml = new StringBuffer();
		StringBuffer categoriesXml = new StringBuffer();
		StringBuffer datasetXml = new StringBuffer();
		StringBuffer setXml = new StringBuffer();

		try {
			if (results != null) {
				if ("".equals(endDate)) {
					showXml.append("<chart caption='"
							+ segName
							+ startDate
							+ "流量统计分析图' shownames='1' showToolTip='1' showvalues='0' decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0' >");
				} else {
					showXml.append("<chart caption='"
							+ segName
							+ startDate
							+ "至"
							+ endDate
							+ "流量统计分析图' shownames='1' showToolTip='1'  showvalues='0'  decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'>");
				}
			}
			showXml.append(" <categories> ");

			if (results != null) {
				if (type.equals("1")) {
					for (int i = 0; i < results.length; i++) {
						categoriesXml.append("<category label='");
						categoriesXml.append(String.valueOf(results[i][0]));
						categoriesXml.append("' /> ");
					}
				} else {
					for (int i = 0; i < results.length; i++) {
						categoriesXml.append("<category label='");
						categoriesXml.append(String.valueOf(results[i][1]));
						categoriesXml.append("' /> ");
					}
				}
			}

			showXml.append(categoriesXml);
			showXml.append(" </categories> ");

			Object[][] averageValue = getCitySegAverageFlow(res);

			List<Integer> list = new ArrayList<Integer>();

			if (type.equals("1")) {
				if (averageValue != null) {
					for (int i = 0; i < results.length; i++) {
						if (Integer.parseInt(String.valueOf(results[i][1])) > Integer
								.parseInt(String.valueOf(averageValue[i][0]))) {
							setXml.append("<set value = '");
							setXml.append(averageValue[i][0]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][1] + "辆");
							setXml.append("'/>");
							list.add(Integer.parseInt(String
									.valueOf(results[i][1]))
									- Integer.parseInt(String
											.valueOf(averageValue[i][0])));
						} else {
							setXml.append("<set value = '");
							setXml.append(results[i][1]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][1] + "辆");
							setXml.append("'/>");
							list.add(null);
						}
					}
				} else {
					for (int i = 0; i < results.length; i++) {
						setXml.append("<set value = '");
						setXml.append(results[i][1]);
						setXml.append("' toolText='");
						setXml.append(results[i][0] + ",");
						setXml.append(results[i][1] + "辆");
						setXml.append("'/>");
						list.add(null);
					}
				}
			} else {
				if (averageValue != null) {
					for (int i = 0; i < results.length; i++) {
						if (Integer.parseInt(String.valueOf(results[i][3])) > Integer
								.parseInt(String.valueOf(averageValue[0][0]))) {
							setXml.append("<set value = '");
							setXml.append(averageValue[0][0]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][3] + "辆");
							setXml.append("'/>");
							list.add(Integer.parseInt(String
									.valueOf(results[i][3]))
									- Integer.parseInt(String
											.valueOf(averageValue[0][0])));
						} else {
							setXml.append("<set value = '");
							setXml.append(results[i][3]);
							setXml.append("' toolText='");
							setXml.append(results[i][0] + ",");
							setXml.append(results[i][3] + "辆");
							setXml.append("'/>");
							list.add(null);
						}
					}
				} else {
					for (int i = 0; i < results.length; i++) {
						setXml.append("<set value = '");
						setXml.append(results[i][3]);
						setXml.append("' toolText='");
						setXml.append(results[i][0] + ",");
						setXml.append(results[i][3] + "辆");
						setXml.append("'/>");
						list.add(null);
					}
				}
			}

			if (flowType.equals("2")) {
				datasetXml
						.append(" <dataset  color='00FF00' showValues='0' > ");
				datasetXml.append(setXml.toString());
				datasetXml.append(" </dataset> ");
			} else if (flowType.equals("1")) {
				datasetXml.append(" <dataset  color='00FF00' showValues='0'> ");
				datasetXml.append(setXml.toString());
				datasetXml.append(" </dataset> ");
			} else if (flowType.equals("3")) {
				datasetXml.append(" <dataset  color='00FF00' showValues='0'> ");
				datasetXml.append(setXml.toString());
				datasetXml.append(" </dataset> ");
			}
			datasetXml.append("<dataset  color='00FF00' showValues='0'>");
			if (type.equals("1")) {
				for (int i = 0; i < list.size(); i++) {
					datasetXml.append("<set color='FFFF00' value='");
					datasetXml.append(list.get(i));
					datasetXml.append("' toolText='");
					datasetXml.append(results[i][0] + ",");
					datasetXml.append(results[i][1] + "辆");
					datasetXml.append("'/>");
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					datasetXml.append("<set color='FFFF00' value='");
					datasetXml.append(list.get(i));
					datasetXml.append("' toolText='");
					datasetXml.append(results[i][0] + ",");
					datasetXml.append(results[i][3] + "辆");
					datasetXml.append("'/>");
				}
			}
			datasetXml.append(" </dataset> ");
			showXml.append(datasetXml);

			if (averageValue != null) {
				if (type.equals("2")) {
					showXml.append("<trendlines><line startValue='");
					showXml.append(averageValue[0][0]);
					showXml.append("' color='FF0000' displayValue='日平均流量' thickness='1' valueOnRight='1' toolText='");
					showXml.append(averageValue[0][0] + "辆");
					showXml.append("' showOnTop='1' /></trendlines>");
				} else if (type.equals("3")) {
					showXml.append("<trendlines><line startValue='");
					showXml.append(averageValue[0][0]);
					showXml.append("' color='FF0000' displayValue='月平均流量' thickness='1' valueOnRight='1' showOnTop='1' toolText='");
					showXml.append(averageValue[0][0] + "辆");
					showXml.append("'/></trendlines>");
				}
			}

			showXml.append(" </chart>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return showXml.toString();
	}

	public static String getCitySegShowTableStr(HashMap res, Object[][] results) {
		String startDate = res.get("startDate").toString();
		String endDate = res.get("endDate").toString();
		String segName = res.get("segName").toString();
		String type = res.get("type").toString();
		String flowType = res.get("flowType").toString();

		StringBuffer showXml = new StringBuffer();
		StringBuffer titleStr = new StringBuffer();

		if ("".equals(endDate)) {
			titleStr.append(segName + startDate + "流量统计分析表");
		} else {
			titleStr.append(segName + startDate + "至" + endDate + "流量统计分析表");
		}

		showXml.append("<table width=\"800\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px;overflow: inherit;\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='left' height='20'  colspan='"
				+ results.length + 1 + "'>");
		showXml.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		showXml.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");
		showXml.append("<tr>");
		showXml.append("<td align='center' height='20' class='td_r_b' style=\"white-space:nowrap\"> 卡口/");

		if (type.equals("1")) {
			showXml.append("时间");
		} else if (type.equals("2")) {
			showXml.append("日期");
		} else {
			showXml.append("月份");
		}
		showXml.append("</td>");

		if (type.equals("1")) {
			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' height='20' class='td_r_b'>");
				showXml.append(results[i][0]);
				showXml.append("</td>");
			}
			showXml.append("</tr>");

			showXml.append("<tr>");
			showXml.append("<td align='center' height='20' class='td_r_b'  style=\"white-space:nowrap\">");
			if (flowType.equals("2")) {
				showXml.append("出省流量");
			} else if (flowType.equals("1")) {
				showXml.append("入省流量");
			} else {
				showXml.append("双向流量");
			}
			showXml.append("</td>");
			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' height='20' class='td_r_b' style=\"white-space:nowrap\">");
				showXml.append(results[i][1] + "辆");
				showXml.append("</td>");
			}
			showXml.append("</tr>");
		} else {
			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' height='20' class='td_r_b' style=\"white-space:nowrap\">");
				showXml.append(results[i][1]);
				showXml.append("</td>");
			}
			showXml.append("</tr>");

			showXml.append("<tr>");
			showXml.append("<td align='center' class='td_r_b'  style=\"white-space:nowrap\">");
			if (flowType.equals("2")) {
				showXml.append("出省流量");
			} else if (flowType.equals("1")) {
				showXml.append("入省流量");
			} else {
				showXml.append("双向流量");
			}
			showXml.append("</td>");

			for (int i = 0; i < results.length; i++) {
				showXml.append("<td align='center' class='td_r_b'  style=\"white-space:nowrap;line-height:18px\">");
				if (String.valueOf(results[i][4]).equals("0")) {
					showXml.append("&nbsp;" + results[i][3] + "辆(完整)");
				} else {
					showXml.append("&nbsp;" + results[i][3] + "辆(缺失:" + results[i][4]
							+ ")");
				}
				showXml.append("</td>");
			}
			showXml.append("</tr>");
		}

		showXml.append("</table>");
		return String.valueOf(showXml);
	}

	public static Object[][] getCitySegAverageFlow(Map res) {
		String segName = res.get("segName").toString();
		String flowType = res.get("flowType").toString();
		String type = res.get("type").toString();
		String sql = "";
		if (type.equals("1")) {
			sql += " select flow from (select decode(grouping(direction), 1, '3', direction) direction,hour,";
			sql += " decode(decode(grouping(direction), 1, '3', direction),'3',trunc(sum(flow)/2),sum(flow)) flow";
			sql += " from T_TFM_FLOWHISTORYAVG t, (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma";
			sql += " from t_road_seginfo t1, t_tfm_driveway t2 where t1.roadid != 'SJKK' and t1.roadsegname like '%"
					+ segName + "%'";
			sql += " and t1.roadsegid = t2.roadsegid) tt  where t.bid = tt.roadsegid and t.direction = tt.bianma";
			sql += " and to_char(sysdate, 'yyyy') = year group by roadsegid, roadsegname,hour, rollup(direction))  where direction = '"
					+ flowType + "'";

		} else if (type.equals("2")) {
			sql += " select flow from (select decode(grouping(direction), 1, '3', direction) direction,sum(flow) flow";
			sql += " from T_TFM_FLOWHISTORYAVG t, (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma from t_road_seginfo t1, t_tfm_driveway t2";
			sql += " where t1.roadid != 'SJKK'  and t1.roadsegname like '%"
					+ segName + "%'";
			sql += " and t1.roadsegid = t2.roadsegid) tt where t.bid = tt.roadsegid and t.direction = tt.bianma and to_char(sysdate, 'yyyy') = year";
			sql += " group by roadsegid,roadsegname,rollup(direction))  where direction = '"
					+ flowType + "'";
		} else if (type.equals("3")) {
			sql += " select flow * 30 from (select decode(grouping(direction), 1, '3', direction) direction,sum(flow) flow";
			sql += " from T_TFM_FLOWHISTORYAVG t, (select t1.roadsegid, t1.roadsegname, substr(t2.bianma, 0, 1) bianma from t_road_seginfo t1, t_tfm_driveway t2";
			sql += " where t1.roadid != 'SJKK'  and t1.roadsegname like '%"
					+ segName + "%'";
			sql += " and t1.roadsegid = t2.roadsegid) tt where t.bid = tt.roadsegid and t.direction = tt.bianma and to_char(sysdate, 'yyyy') = year";
			sql += " group by roadsegid,roadsegname,rollup(direction))  where direction = '"
					+ flowType + "'";
		}

		Object[][] results = DBHandler.getMultiResult(sql);
		return results;
	}
}
