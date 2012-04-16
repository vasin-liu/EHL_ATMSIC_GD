package com.ehl.dispatch.cdispatch.util;

import java.util.ArrayList;

import com.ehl.dispatch.cdispatch.dao.BayonetCartogramDao;

public class BayonetCartogramUtil {

	/**
	 * @������ �޾���
	 * @����˵���� ����ѯ�����Ľ��ƴ�ӳ�xml��ʽ���ַ�����������ҳ������ݴ���
	 * @ʱ�䣺 2010-11-2
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
	 * @���ߣ� �޾��� @����˵���� ����ѯ������ת�ɷ���ҳ���xml��ʽ���ݡ� @ʱ�䣺 2010-11-2 @return
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
	 * @���ߣ��޾���
	 * @����˵��������alarmTotalType�ֶ��жϻ���ʱ���ѯ����
	 * @ʱ�䣺2010-11-2
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

		if (timeRadioType.equals("1")) {// ���ջ���ͳ��

			if (CountType.equals("1")) {

				sqlwhere.append(" where e1.datetime >= '" + STARTTIME
						+ "' and e1.datetime <= '" + ENDTIME + "' "
						+ "group by e1.datetime, e1.name "
						+ "order by e1.datetime");

			} else {

				sqlwhere.append(this.createDaywheresql(startyearId, endyearId,
						STARTTIME));

			}

		} else if (timeRadioType.equals("7")) {// ��Сʱ����ͳ��

			sqlwhere
					.append(" and e.datetime >= to_date('"
							+ hours
							+ " 00:00','yyyy-mm-dd HH24:mi') and e.datetime <= to_date('"
							+ hours
							+ " 23:00','yyyy-mm-dd HH24:mi') "
							+ "group by to_char(e.datetime, 'HH24:mi'), d.name "
							+ " order by to_char(e.datetime,'HH24:mi')");

		} else if (timeRadioType.equals("3")) {// ���»���ͳ��

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

		} else if (timeRadioType.equals("6")) {// ���껷��ͳ��

			sqlwhere
					.append(" where e1.datetime>= '" + startyearId
							+ "' and e1.datetime <= '" + endyearId + "' "
							+ "group by e1.datetime, e1.name "
							+ "order by e1.datetime");

		} else {// �����ջ���ͳ��

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
			// �������ݵĸ�ʽ--����ͳ��
			result = "to_char(e.datetime, 'yyyy-mm-dd')";

		} else if (timeRadioType.equals("7")) {
			// �������ݵĸ�ʽ--��Сʱͳ��
			result = "to_char(e.datetime, 'HH24:mi')";

		} else if (timeRadioType.equals("3")) {
			// �������ݵĸ�ʽ--����ͳ��
			result = "to_char(e.datetime, 'yyyy-mm')";

		} else if (timeRadioType.equals("6")) {
			// �������ݵĸ�ʽ--����ͳ��
			result = "to_char(e.datetime, 'yyyy')";
		} else {
			// �������ݵĸ�ʽ--��������
			result = "to_char(e.datetime, 'yyyy-mm-dd')";
		}

		return result;

	}

	/**
	 * @���ߣ� �޾���
	 * @����˵���� ����roadname��Ӱ��������Ʋ�ѯͳ��������
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
	 * @���ߣ� �޾���
	 * @����˵���� ����departType�жϿ��ڰ�ȫ�����ʡ����ʡ��ѯ��sql����
	 * @param departType
	 *            ����Ƿ��ǳ�ʡ����ʡ��ȫ������
	 * @return
	 */
	public String getRoadType(String departType) {

		StringBuffer typesql = new StringBuffer();

		if (departType.equals("1")) {
			// ��ʡ��ʡ�ܼ�ͳ��
			typesql.append("");

		} else if (departType.equals("2")) {
			// ��ʡͳ��
			typesql.append(" where g.bianma = '01' ");

		} else if (departType.equals("3")) {
			// ��ʡͳ��
			typesql.append(" where g.bianma = '02' ");

		}

		return typesql.toString();
	}

	/**
	 * @���ߣ� �޾���
	 * @����˵���� �����ݿ��ѯ�����Ľ��ƴ�ӳ�xml��ʽ�ַ��������ҽ�����Ϊ�յĲ�0����Ҫ���ڱ������ݵ�չ�ֺ�ͳ��ͼ��չ��
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
	 * @���ߣ��޾���
	 * @ʱ�䣺2010-11-11
	 * @����˵������ָ���Ľ���ת��Ϊũ����������
	 * @param start����ʼʱ��
	 * @param end:����ʱ��
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
	 * @���ߣ� �޾���
	 * @����˵���� ƴ�Ӱ�������ͬ��Case��sql�������Ҫ��������ʵ��������ͬ�ȹ���
	 * @ʱ�䣺 2010-11-16
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
	 * @���ߣ� �޾���
	 * @����˵����ƴ�Ӱ�������ͬ��Case��sql�������������ʵ��������ͬ�ȹ��ܡ�
	 * @ʱ�䣺 2010-11-16
	 * @param startYear
	 *            ��ʼ��
	 * @param endYear
	 *            ��ֹ��
	 * @param startday
	 *            ��ʼ����
	 * @param endday
	 *            ��������
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
	 * @���ߣ� �޾���
	 * @����˵���� �������²�ѯ����ͬ������sql��䡣
	 * @ʱ�䣺2010-11-16
	 * @param startYear
	 *            ��ʼ��
	 * @param endYear
	 *            ������
	 * @param startmonth
	 *            ��ʼ��
	 * @param endmonth
	 *            ������
	 * @return
	 */
	public String createMonthSaerchString(String startYear, String endYear,
			String startmonth, String endmonth) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);// ��ʼ��

		eyear = Integer.parseInt(endYear);// ������

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
	 * @���ߣ� �޾���
	 * @����˵���� ƴ�Ӱ�������ͬ��sql������
	 * @ʱ�䣺 2010-11-16
	 * @param startYear
	 *            ��ʼ��
	 * @param endYear
	 *            ������
	 * @param startday
	 *            ��ʼ������
	 * @param endday
	 *            ����������
	 * @return
	 */
	public String createDaySaerchString(String startYear, String endYear,
			String startday, String endday) {

		StringBuffer sBuffer = new StringBuffer();

		int syear, eyear, count;

		syear = Integer.parseInt(startYear);// ��ʼ��

		eyear = Integer.parseInt(endYear);// ������

		count = eyear - syear;

		startday = startday.substring(4, startday.length());// ��ʼ����

		endday = endday.substring(4, endday.length());// ��������

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
	 * @���ߣ� �޾���
	 * @����˵��: ��������ͬ�ȵ�ҵ���߼�
	 * @ʱ�䣺 2010-11-16
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
	 * @���ߣ� �޾���
	 * @����˵���� ���ݲ�ͬ�Ľ���ת�����ڸ�ʽ����ƴ�ӳ�����ͬ����Ҫ��sql��ѯ����where����
	 * @ʱ�䣺 2010-11-17
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

		if (timeRadioType.equals("8")) {// ����

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

		} else if (timeRadioType.equals("9")) {// ��һ

			for (int i = 0; i < count + 1; i++) {

				sBuffer.append(" WHEN e.datetime >= to_date('" + syear
						+ "-05-01','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + syear
						+ "-05-04', 'yyyy-mm-dd') THEN '");

				sBuffer.append(syear + "-05-01--" + syear + "-05-04'");

				syear++;

			}

		} else if (timeRadioType.equals("10")) {// ����

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

		} else if (timeRadioType.equals("11")) {// Ԫ��

			for (int i = 0; i < count + 1; i++) {

				sBuffer.append(" WHEN e.datetime >= to_date('" + syear
						+ "-01-01','yyyy-mm-dd') AND ");

				sBuffer.append(" e.datetime <= to_date('" + syear
						+ "-01-07', 'yyyy-mm-dd') THEN '");

				sBuffer.append(syear + "-01-01--" + syear + "-01-03'");

				syear++;

			}

		} else if (timeRadioType.equals("12")) {// ����

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

		} else if (timeRadioType.equals("13")) {// ����

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
	 * @���ߣ� �޾���
	 * @����˵���� ����ũ������ת��������ʱ�䣬��ƴ�ӳ�����ͬ�Ȳ�ѯ����ѯ�ֶΡ�
	 * @ʱ�䣺2010-11-17
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

		startday = startday.substring(4, startday.length());// �õ���ʼ����

		endday = endday.substring(4, endday.length());// �õ���������

		count = eyear - syear;// �������

		if (timeRadioType.equals("8")) {// ����

			sBuffer.append(" e1.datetime = '" + syear + startday + "--" + syear
					+ endday + "'");

			for (int i = 0; i < count; i++) {

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-12-28");

				syear++;

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-01-15");

				sBuffer.append(" or e1.datetime = '" + start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("9")) {// ��һ

			sBuffer.append(" e1.datetime = '" + syear + "-05-01--" + syear
					+ "-05-04'");

			for (int i = 0; i < count; i++) {

				syear++;

				sBuffer.append(" or e1.datetime = '" + syear + "-05-01--"
						+ syear + "-05-04'");

			}

		} else if (timeRadioType.equals("10")) {// ����

			sBuffer.append(" e1.datetime = '" + syear + startday + "--" + syear
					+ endday + "'");

			for (int i = 0; i < count; i++) {

				syear++;

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-08-15");

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-08-18");

				sBuffer.append(" or e1.datetime = '" + start + "--" + end + "'");

			}

		} else if (timeRadioType.equals("11")) {// Ԫ��

			sBuffer.append(" e1.datetime = '" + syear + "-01-01--" + syear
					+ "-01-03'");

			for (int i = 0; i < count; i++) {

				syear++;

				sBuffer.append(" or e1.datetime = '" + syear + "-01-01--" + syear
						+ "-1-03'");

			}

		} else if (timeRadioType.equals("12")) {// ����

			sBuffer.append(" e1.datetime = '" + syear + startday + "--" + syear
					+ endday + "'");

			for (int i = 0; i < count; i++) {

				syear++;

				start = ChineseCalendar
						.sCalendarLundarToSolar(syear + "-05-05");

				end = ChineseCalendar.sCalendarLundarToSolar(syear + "-05-08");

				sBuffer.append(" or e1.datetime = '" + start + "--" + end + "'");
			}

		} else if (timeRadioType.equals("13")) {// ����

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
	 * @���ߣ� �޾���
	 * @����˵����
	 * @ʱ�䣺 2010-11-16
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
	 * @���ߣ� �޾���
	 * @����˵����
	 * @ʱ�䣺2010-11-16
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
