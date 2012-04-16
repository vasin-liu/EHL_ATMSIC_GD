package com.ehl.dispatch.cdispatch.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class BayonetCartogramDao {

	Logger logger = Logger.getLogger(BayonetCartogramDao.class);

	private Object[][] object;

	/**
	 * @姓名： 罗军礼
	 * @函数说明： 查询全部省际卡口
	 * @时间： 2010-11-2
	 * @return
	 */
	public Object[][] getDate() {

		try {
			String sqlstr = "select c.name from t_tfm_detector c where c.roadid = 'SJKK'";

			object = DBHandler.getMultiResult(sqlstr);
			logger.debug(sqlstr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return object;
	}

	/**
	 * @作者： 罗军礼
	 * @函数说明：按时间查询统计卡口流量
	 * @时间：2010-11-2
	 * @return
	 */
	public Object[][] getYearDate(String roadnamesql, String sqlwhere,
			String typesql, String result) {
		String sqlstr = "select d.name,"
				+ result
				+ ",sum(e.volume)"
				+ " from (select c.driveid,a.name from "
				+ "(select b.bianma,b.name from t_tfm_detector b "
				+ roadnamesql
				+ ") a, t_tfm_drive_and_channel c where c.detectorid = a.bianma "
				+ "and c.driveid in (select g.id from t_tfm_driveway g "
				+ typesql
				+ ")) d, t_tfm_driveway_1h_flow e where e.id = d.driveid "
				+ sqlwhere;

		try {
			System.out.println(sqlstr);
			object = DBHandler.getMultiResult(sqlstr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return object;
	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 按时间条件查询出环比，定期同比数据。
	 * @param roadnamesql
	 * @param sqlwhere
	 * @param typesql
	 * @param result
	 * @return
	 */
	public Object[][] getDate(String roadnamesql, String sqlwhere,
			String typesql, String result) {

		String sqlString = "select e1.name, e1.datetime, sum(e1.volume) from ("
				+ "select d.name, "
				+ result
				+ " datetime,e.volume"
				+ " from (select c.driveid,a.name from "
				+ "(select b.bianma,b.name from t_tfm_detector b "
				+ roadnamesql
				+ ") a, t_tfm_drive_and_channel c where c.detectorid = a.bianma "
				+ "and c.driveid in (select g.id from t_tfm_driveway g "
				+ typesql
				+ ")) d, t_tfm_driveway_1h_flow e where e.id = d.driveid )e1 "
				+ sqlwhere;
		try {
			System.out.println(sqlString);
			object = DBHandler.getMultiResult(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * @作者： 罗军礼
	 * @函数说明： 按时间条件查询周期同比基数
	 * @param casesql
	 * @param wheresql
	 * @param roadnamesql
	 * @param typesql
	 * @return
	 */
	public Object[][] getDates(String casesql, String wheresql,
			String roadnamesql, String typesql) {

		String sqlString = "select e1.name,e1.datetime,sum(e1.volume) from (select d.name,"
				+ "(CASE "
				+ casesql
				+ " end) datetime,e.volume "
				+ "from (select c.driveid, a.name "
				+ " from (select b.bianma, b.name from t_tfm_detector b "
				+ roadnamesql
				+ ") a,"
				+ " t_tfm_drive_and_channel c"
				+ " where c.detectorid = a.bianma"
				+ " and c.driveid in (select g.id from t_tfm_driveway g "
				+ typesql
				+ ")) d,"
				+ " t_tfm_driveway_1h_flow e"
				+ " where e.id = d.driveid) e1 where "
				+ wheresql
				+ " group by e1.name,e1.datetime" + " order by e1.datetime";
		try {
			System.out.println(sqlString);
			object = DBHandler.getMultiResult(sqlString);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object;
	}

}
