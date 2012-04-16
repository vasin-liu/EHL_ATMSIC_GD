package com.ehl.dispatch.cdispatch.util;

import java.util.ArrayList;

import com.ehl.dispatch.cdispatch.dao.BayonetCartogramDao;

public class BayonetCartogramUtil {

	/**
	 * @姓名： 罗军礼
	 * @函数说明： 将查询出来的结果拼接成xml格式的字符串。用来与页面的数据传输
	 * @时间： 2010-11-2
	 * @param object
	 */

	public String CreateRoadlXMl(Object[][] object) {

		StringBuffer bf = new StringBuffer(

		"<?xml version='1.0' encoding='UTF-8'?>");

		try {

			bf.append("<rows>");

			if (object != null) {

				for (int i = 0; i < object.length - 1; i++) {

					bf.append("<row>");

					for (int j = 0; j < 3; j++) {

						bf.append("<col>");

						bf.append(object[i][0]);

						bf.append("</col>");

						if (j < 2) {

							i++;

						}

					}

					bf.append("</row>");

					System.out.println(bf.toString());

				}

				bf.append("</rows>");

				System.out.println(bf.toString());
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return bf.toString();
	}

	/*
	 * @作者： 罗军礼 @函数说明： 将查询的数据转成返回页面的xml格式数据。 @时间： 2010-11-2 @return
	 */
	/*
	 * public String getRoadXml(Object[][] objects) { StringBuffer sBuffer = new
	 * StringBuffer( "<?xml version='1.0' encoding='UTF-8'?>");
	 * sBuffer.append("<root>"); try { for (int i = 0; i < objects.length; i++) {
	 * sBuffer.append("<datas>"); for (int j = 0; j < objects[i].length; j++) {
	 * sBuffer.append("<col>"); sBuffer.append(objects[i][j]); sBuffer.append("</col>"); }
	 * sBuffer.append("</datas>"); } sBuffer.append("</root>");
	 * System.out.println(sBuffer.toString()); } catch (Exception ex) {
	 * ex.printStackTrace(); } return sBuffer.toString(); }
	 */

	/**
	 * @作者：罗军礼
	 * @函数说明：根据alarmTotalType字段判断环比时间查询条件
	 * @时间：2010-11-2
	 * @param alarmTotalType
	 * @param STARTTIME
	 * @param ENDTIME
	 * @param startyearId
	 * @param startmonthId
	 * @param endyearId
	 * @param endmonthId
	 * @param hours
	 * @return
	 */
	public String getDateSql(String timeRadioType, String STARTTIME,
			String ENDTIME, String startyearId, String startmonthId,
			String endyearId, String endmonthId, String hours, String timeStr,
			String CountType) {

		String timeStrsql = "";

		StringBuffer sqlwhere = new StringBuffer();

		if (timeRadioType.equals("1")) {// 按日环比统计

			if (CountType.equals("1")) {

				sqlwhere.append(" where e1.datetime >= '" + STARTTIME
						+ "' and e1.datetime <= '" + ENDTIME + "' "
						+ "group by e1.datetime, e1.name "
						+ "order by e1.datetime");

			} else {

				sqlwhere.append(this.createDaywheresql(startyearId, endyearId,
						STARTTIME));

			}

		} else if (timeRadioType.equals("7")) {// 按小时环比统计

			sqlwhere
					.append(" and e.datetime >= to_date('"
							+ hours
							+ " 00:00','yyyy-mm-dd HH24:mi') and e.datetime <= to_date('"
							+ hours
							+ " 23:00','yyyy-mm-dd HH24:mi') "
							+ "group by to_char(e.datetime, 'HH24:mi'), d.name "
							+ " order by to_char(e.datetime,'HH24:mi')");

		} else if (timeRadioType.equals("3")) {// 按月环比统计

			if (CountType.equals("1")) {

				sqlwhere.append(" where e1.datetime >= '" + startyearId + "-"
						+ startmonthId + "' and e1.datetime <= '" + endyearId
						+ "-" + endmonthId + "' "
						+ "group by e1.datetime, e1.name "
						+ "order by e1.datetime");

			} else {

				sqlwhere.append(this.createMonthwheresql(startyearId,
						endyearId, startmonthId));

			}

		} else if (timeRadioType.equals("6")) {// 按年环比统计

			sqlwhere
					.append(" where e1.datetime>= '" + startyearId
							+ "' and e1.datetime <= '" + endyearId + "' "
							+ "group by e1.datetime, e1.name "
							+ "order by e1.datetime");

		} else {// 按节日环比统计

			//timeStrsql = ChineseCalendar.sCalendarLundarToSolar(timeStr);

			sqlwhere.append(" where e1.datetime >= '" + STARTTIME + "' "
					+ "and e1.datetime <= '" + ENDTIME + "' "
					+ " group by e1.datetime, e1.name "
					+ "order by e1.datetime");

		}

		return sqlwhere.toString();

	}

	public String getResult(String timeRadioType) {

		String result = null;

		if (timeRadioType.equals("1")) {
			// 返回数据的格式--按天统计
			result = "to_char(e.datetime, 'yyyy-mm-dd')";

		} else if (timeRadioType.equals("7")) {
			// 返回数据的格式--按小时统计
			result = "to_char(e.datetime, 'HH24:mi')";

		} else if (timeRadioType.equals("3")) {
			// 返回数据的格式--按月统计
			result = "to_char(e.datetime, 'yyyy-mm')";

		} else if (timeRadioType.equals("6")) {
			// 返回数据的格式--按年统计
			result = "to_char(e.datetime, 'yyyy')";
		} else {
			// 返回数据的格式--其他节日
			result = "to_char(e.datetime, 'yyyy-mm-dd')";
		}

		return result;

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 根据roadname添加按卡口名称查询统计条件，
	 * @param roadname
	 * @return
	 */
	public String getRoadNameSql(Object[] objects) {

		StringBuffer roadnamesql = new StringBuffer("where");

		try {

			roadnamesql.append(" b.name = '" + objects[0].toString() + "' ");

			for (int i = 1; i < objects.length; i++) {

				roadnamesql.append(" or b.name = '");

				roadnamesql.append(objects[i].toString() + "'");

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return roadnamesql.toString();
	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 根据departType判断卡口按全部或出省、入省查询的sql条件
	 * @param departType
	 *            标记是否是出省或入省或全部流量
	 * @return
	 */
	public String getRoadType(String departType) {

		StringBuffer typesql = new StringBuffer();

		if (departType.equals("1")) {
			// 出省入省总计统计
			typesql.append("");

		} else if (departType.equals("2")) {
			// 出省统计
			typesql.append(" where g.bianma = '01' ");

		} else if (departType.equals("3")) {
			// 入省统计
			typesql.append(" where g.bianma = '02' ");

		}

		return typesql.toString();
	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 将数据库查询出来的结果拼接成xml格式字符串。并且将数据为空的补0；主要用于报表数据的展现和统计图的展现
	 * @param objects
	 * @param objects2
	 * @return
	 */
	public String getRoadXMl(Object[][] objects, Object[] objects2) {

		StringBuffer sb = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?><root>");

		String datetime = "";

		ArrayList arrayList = new ArrayList();

		ArrayList roadnameList = new ArrayList();

		ArrayList valueList = new ArrayList();

		int count = 0;

		int count1 = 0;

		Boolean flog = true;

		try {

			if (objects == null) {

				sb.append("</root>");

				return sb.toString();

			} else {

				sb.append("<date>");

				for (int i = 0; i < objects.length; i++) {

					if (!datetime.equals(objects[i][1].toString())) {

						datetime = objects[i][1].toString();

						sb.append("<cetegories>" + objects[i][1].toString()
								+ "</cetegories>");

						arrayList.add(count1, datetime);

						count1++;

						continue;

					}

				}

				sb.append("</date>");

				for (int i = 0; i < arrayList.size(); i++) {

					count = 0;

					for (int j = 0; j < objects.length + 1; j++) {

						if (j < objects.length) {

							if (arrayList.get(i).toString().equals(
									objects[j][1].toString())) {

								roadnameList.add(count, objects[j][0]
										.toString());

								valueList.add(count, objects[j][2].toString());

								count++;

							}

						} else {

							for (int j2 = 0; j2 < objects2.length; j2++) {

								flog = true;

								for (int k = 0; k < roadnameList.size() + 1; k++) {

									if (k < roadnameList.size()) {

										if (objects2[j2].toString().equals(
												roadnameList.get(k).toString())) {

											sb.append("<date>");

											sb.append("<col>"
													+ objects2[j2].toString()
													+ "</col>");

											sb.append("<col>"
													+ arrayList.get(i)
															.toString()
													+ "</col>");

											sb.append("<col>"
													+ valueList.get(k)
													+ "</col>");

											sb.append("</date>");

											flog = false;

										}

									} else if (k >= roadnameList.size()
											&& flog == true) {

										sb.append("<date>");

										sb.append("<col>"
												+ objects2[j2].toString()
												+ "</col>");

										sb.append("<col>"
												+ arrayList.get(i).toString()
												+ "</col>");

										sb.append("<col>0</col>");

										sb.append("</date>");

									}

								}

							}

							roadnameList.clear();

							valueList.clear();

						}

					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception

			sb.append("</root>");

			e.printStackTrace();

			return sb.toString();

		}

		sb.append("</root>");

		return sb.toString();

	}

	/**
	 * @作者：罗军礼
	 * @时间：2010-11-11
	 * @函数说明：将指定的节日转换为农历节日日期
	 * @param start：开始时间
	 * @param end:结束时间
	 * @return
	 */
	public String sCalendarLundarToSolar(String start, String end) {

		StringBuffer sBuffer = new StringBuffer(

		"<?xml version='1.0' encoding='UTF-8'?><root>");

		String starString = ChineseCalendar.sCalendarLundarToSolar(start);

		String endString = ChineseCalendar.sCalendarLundarToSolar(end);

		sBuffer.append("<dates><col>" + starString + "</col><col>" + endString

		+ "</col></dates></root>");

		return sBuffer.toString();

	}

	/**
	 * public String dayCountSql(String start,String end){ int
	 * startInt,endInt,count; Object[] startOJ,endOJ; startOJ =
	 * start.split("-"); endOJ = end.split("-"); startInt =
	 * Integer.parseInt(startOJ[0].toString()); endInt =
	 * Integer.parseInt(endOJ[0].toString()); count = (endInt-startInt)+1;
	 * String YEAR,ENDTIME; ENDTIME =
	 * 
	 * return ""; }
	 */
	/**
	 * @作者： 罗军礼
	 * @函数说明： 拼接按月周期同比Case后sql语句所需要的条件。实现月周期同比功能
	 * @时间： 2010-11-16
	 */
	public String createMonthCaseString(String startYear, String endYear,
			String startmonth, String endmonth) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);

		eyear = Integer.parseInt(endYear);

		count = eyear - syear;

		for (int i = 0; i < count + 1; i++) {

			sBuffer.append(" WHEN to_char(datetime,'yyyy-mm') >= '" + syear + "-"
					+ startmonth + "' AND ");

			sBuffer.append(" to_char(datetime,'yyyy-mm') <= '" + syear + "-" + endmonth
					+ "' THEN '");

			sBuffer.append(syear + "-" + startmonth + "--" + syear + "-"
					+ endmonth + "'");

			syear++;

		}

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明：拼接按日周期同比Case后sql语句所需条件。实现日周期同比功能。
	 * @时间： 2010-11-16
	 * @param startYear
	 *            开始年
	 * @param endYear
	 *            终止年
	 * @param startday
	 *            开始日期
	 * @param endday
	 *            结束日期
	 * @return
	 */
	public String createDayCaseString(String startYear, String endYear,
			String startday, String endday) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		String STARTDAY, ENDDAY;

		syear = Integer.parseInt(startYear);

		eyear = Integer.parseInt(endYear);

		STARTDAY = startday.substring(4, startday.length());

		ENDDAY = endday.substring(4, endday.length());

		count = eyear - syear;

		for (int i = 0; i < count + 1; i++) {

			sBuffer.append(" WHEN e.datetime >= to_date('" + syear + STARTDAY
					+ "','yyyy-mm-dd') AND ");

			sBuffer.append(" e.datetime <= to_date('" + syear + ENDDAY
					+ "', 'yyyy-mm-dd') THEN '");

			sBuffer.append(syear + STARTDAY + "--" + syear + ENDDAY + "'");

			syear++;

		}

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 创建按月查询周期同比条件sql语句。
	 * @时间：2010-11-16
	 * @param startYear
	 *            开始年
	 * @param endYear
	 *            结束年
	 * @param startmonth
	 *            开始月
	 * @param endmonth
	 *            结束月
	 * @return
	 */
	public String createMonthSaerchString(String startYear, String endYear,
			String startmonth, String endmonth) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);// 开始年

		eyear = Integer.parseInt(endYear);// 结束年

		count = eyear - syear;

		sBuffer.append(" e1.datetime='" + syear + "-" + startmonth + "--"
				+ syear + "-" + endmonth + "'");

		for (int i = 0; i < count; i++) {

			syear++;

			sBuffer.append(" or e1.datetime = '" + syear + "-" + startmonth
					+ "--" + syear + "-" + endmonth + "'");

		}

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 拼接按日周期同比sql条件。
	 * @时间： 2010-11-16
	 * @param startYear
	 *            开始年
	 * @param endYear
	 *            结束年
	 * @param startday
	 *            开始日周期
	 * @param endday
	 *            结束日周期
	 * @return
	 */
	public String createDaySaerchString(String startYear, String endYear,
			String startday, String endday) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);// 开始年

		eyear = Integer.parseInt(endYear);// 结束年

		count = eyear - syear;

		startday = startday.substring(4, startday.length());// 开始日月

		endday = endday.substring(4, endday.length());// 结束日月

		sBuffer.append("e1.datetime='" + syear + startday + "--" + syear
				+ endday + "' ");

		for (int i = 1; i < count + 1; i++) {

			syear++;

			sBuffer.append("or e1.datetime='" + syear + startday + "--" + syear
					+ endday + "'");

		}

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明: 处理周期同比的业务逻辑
	 * @时间： 2010-11-16
	 * @param startYear
	 * @param endYear
	 * @param startday
	 * @param endday
	 * @param startmonth
	 * @param endmonth
	 * @param objects2
	 * @param departType
	 * @param timeRadioType
	 * @return
	 */
	public String getCreateCountXML(String startYear, String endYear,
			String startday, String endday, String startmonth, String endmonth,
			Object[] objects2, String departType, String timeRadioType) {

		BayonetCartogramDao bcd = new BayonetCartogramDao();

		String casesql, wheresql, roadnamesql, typesql, xmlstr;

		Object[][] objects;

		if (timeRadioType.equals("1")) {

			casesql = this.createDayCaseString(startYear, endYear, startday,
					endday);

			wheresql = this.createDaySaerchString(startYear, endYear, startday,
					endday);

		} else if (timeRadioType.equals("8") || timeRadioType.equals("9")
				|| timeRadioType.equals("10") || timeRadioType.equals("11")
				|| timeRadioType.equals("12") || timeRadioType.equals("13")) {
			
			casesql = this.createFestivalCaseString(startYear, endYear, startday, endday, timeRadioType);
			
			wheresql = this.createFestivalSaerchString(startYear, endYear, startday, endday, timeRadioType);

		} else {

			casesql = this.createMonthCaseString(startYear, endYear,
					startmonth, endmonth);

			wheresql = this.createMonthSaerchString(startYear, endYear,
					startmonth, endmonth);

		}

		roadnamesql = this.getRoadNameSql(objects2);

		typesql = this.getRoadType(departType);

		objects = bcd.getDates(casesql, wheresql, roadnamesql, typesql);

		xmlstr = this.getRoadXMl(objects, objects2);
		
		return xmlstr;

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 根据不同的节日转换日期格式，并拼接成周期同比需要的sql查询语句的where条件
	 * @时间： 2010-11-17
	 * @param startYear
	 * @param endYear
	 * @param startmonth
	 * @param endmonth
	 * @param timeRadioType
	 * @return
	 */
	public String createFestivalCaseString(String startYear, String endYear,
			String startday, String endday, String timeRadioType) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		String start, end;

		syear = Integer.parseInt(startYear);

		eyear = Integer.parseInt(endYear);

		startday = startday.substring(4, startday.length());

		endday = endday.substring(4, endday.length());

		count = eyear - syear;

		if (timeRadioType.equals("8")) {// 春节

			sBuffer.append(" WHEN e.datetime >= to_date('" + syear
					+ startday + "','yyyy-mm-dd') AND ");

			sBuffer.append(" e.datetime <= to_date('" + syear + endday
					+ "', 'yyyy-mm-dd') THEN '");

			sBuffer.append(syear + startday + "--" + syear + endday
					+ "'");

			for (int i = 0; i < count; i++) {

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-12-28");

				syear++;

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-01-15");

				sBuffer.append(" WHEN e.datetime >= to_date('" + start
						+ "','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + end
						+ "', 'yyyy-mm-dd') THEN '");

				sBuffer.append(start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("9")) {// 五一

			for (int i = 0; i < count + 1; i++) {

				sBuffer.append(" WHEN e.datetime >= to_date('" + syear
						+ "-05-01','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + syear
						+ "-05-04', 'yyyy-mm-dd') THEN '");

				sBuffer.append(syear + "-05-01--" + syear + "-05-04'");

				syear++;

			}

		} else if (timeRadioType.equals("10")) {// 中秋

			sBuffer.append(" WHEN e.datetime >= to_date('" + syear + startday
					+ "','yyyy-mm-dd') AND ");

			sBuffer.append(" e.datetime <= to_date('" + syear + endday
					+ "', 'yyyy-mm-dd') THEN '");

			sBuffer.append(syear + startday + "--" + syear + endday + "'");

			for (int i = 0; i < count; i++) {

				syear++;

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-08-15");

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-08-18");

				sBuffer.append(" WHEN e.datetime >= to_date('" + start
						+ "','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + end
						+ "', 'yyyy-mm-dd') THEN '");

				sBuffer.append(start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("11")) {// 元旦

			for (int i = 0; i < count + 1; i++) {

				sBuffer.append(" WHEN e.datetime >= to_date('" + syear
						+ "-01-01','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + syear
						+ "-01-07', 'yyyy-mm-dd') THEN '");

				sBuffer.append(syear + "-01-01--" + syear + "-01-03'");

				syear++;

			}

		} else if (timeRadioType.equals("12")) {// 端午

			sBuffer.append(" WHEN e.datetime >= to_date('" + syear + startday
					+ "','yyyy-mm-dd') AND ");

			sBuffer.append(" e.datetime <= to_date('" + syear + endday
					+ "', 'yyyy-mm-dd') THEN '");

			sBuffer.append(syear + startday + "--" + syear + endday + "'");

			for (int i = 0; i < count; i++) {

				syear++;

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-05-05");

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-05-08");

				sBuffer.append(" WHEN e.datetime >= to_date('" + start
						+ "','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + end
						+ "', 'yyyy-mm-dd') THEN '");

				sBuffer.append(start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("13")) {// 国庆

			for (int i = 0; i < count + 1; i++) {

				sBuffer.append(" WHEN e.datetime >= to_date('" + syear
						+ "-10-01','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + syear
						+ "-10-07', 'yyyy-mm-dd') THEN '");

				sBuffer.append(syear + "-10-01--" + syear + "-10-07'");

				syear++;

			}

		}

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 根据农历节日转换成阳历时间，并拼接成周期同比查询语句查询字段。
	 * @时间：2010-11-17
	 * @param startYear
	 * @param endYear
	 * @param startday
	 * @param endday
	 * @param timeRadioType
	 * @return
	 */
	public String createFestivalSaerchString(String startYear, String endYear,
			String startday, String endday, String timeRadioType) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		String start, end;

		syear = Integer.parseInt(startYear);

		eyear = Integer.parseInt(endYear);

		startday = startday.substring(4, startday.length());// 得到开始月日

		endday = endday.substring(4, endday.length());// 得到结束月日

		count = eyear - syear;// 间隔年限

		if (timeRadioType.equals("8")) {// 春节

			sBuffer.append(" e1.datetime = '" + syear + startday + "--" + syear
					+ endday + "'");

			for (int i = 0; i < count; i++) {

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-12-28");

				syear++;

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-01-15");

				sBuffer.append(" or e1.datetime = '" + start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("9")) {// 五一

			sBuffer.append(" e1.datetime = '" + syear + "-05-01--" + syear
					+ "-05-04'");

			for (int i = 0; i < count; i++) {

				syear++;

				sBuffer.append(" or e1.datetime = '" + syear + "-05-01--"
						+ syear + "-05-04'");

			}

		} else if (timeRadioType.equals("10")) {// 中秋

			sBuffer.append(" e1.datetime = '" + syear + startday + "--" + syear
					+ endday + "'");

			for (int i = 0; i < count; i++) {

				syear++;

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-08-15");

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-08-18");

				sBuffer.append(" or e1.datetime = '" + start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("11")) {// 元旦

			sBuffer.append(" e1.datetime = '" + syear + "-01-01--" + syear
					+ "-01-03'");

			for (int i = 0; i < count; i++) {

				syear++;

				sBuffer.append(" or e1.datetime = '" + syear + "-01-01--" + syear
						+ "-1-03'");

			}

		} else if (timeRadioType.equals("12")) {// 端午

			sBuffer.append(" e1.datetime = '" + syear + startday + "--" + syear
					+ endday + "'");

			for (int i = 0; i < count; i++) {

				syear++;

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-05-05");

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-05-08");

				sBuffer.append(" or e1.datetime = '" + start + "--" + end + "'");
			}

		} else if (timeRadioType.equals("13")) {// 国庆

			sBuffer.append(" e1.datetime = '" + syear + "-10-01--" + syear
					+ "-10-07'");

			for (int i = 0; i < count; i++) {

				syear++;

				sBuffer.append(" or e1.datetime = '" + syear + "-10-01--"
						+ syear + "-10-07'");

			}

		}

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明：
	 * @时间： 2010-11-16
	 * @param startYear
	 * @param endYear
	 * @param startday
	 * @return
	 */
	public String createDaywheresql(String startYear, String endYear,
			String startday) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);

		eyear = Integer.parseInt(endYear);

		count = eyear - syear;

		startday = startday.substring(4, startday.length());

		sBuffer.append(" where e1.datetime = '" + syear + startday + "'");

		for (int i = 0; i < count; i++) {

			syear++;

			sBuffer.append(" or e1.datetime = '" + syear + startday + "'");

		}

		sBuffer.append("group by e1.datetime, e1.name "

		+ "order by e1.datetime");

		return sBuffer.toString();

	}

	/**
	 * @作者： 罗军礼
	 * @函数说明：
	 * @时间：2010-11-16
	 * @param startYear
	 * @param endYear
	 * @param startmonth
	 * @return
	 */
	public String createMonthwheresql(String startYear, String endYear,
			String startmonth) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);

		eyear = Integer.parseInt(endYear);

		count = eyear - syear;

		sBuffer.append(" where e1.datetime = '" + syear + "-" + startmonth
				+ "'");

		for (int i = 0; i < count; i++) {

			syear++;

			sBuffer.append(" or e1.datetime = '" + syear + "-" + startmonth
					+ "'");
			
		}

		sBuffer.append("group by e1.datetime, e1.name "

		+ "order by e1.datetime");

		return sBuffer.toString();
	}

}
