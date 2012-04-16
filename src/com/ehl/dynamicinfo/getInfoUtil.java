package com.ehl.dynamicinfo;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.cdispatch.dao.RoadManageDao;

public class getInfoUtil {
	
	private Logger logger = Logger.getLogger(RoadManageDao.class);
	
	/**
	 * 当日事故信息取得sql
	 */
	private String accNowSql = "";
	/**
	 * 历史事故信息取得sql
	 */
	private String accaccHistorySql = "";
	
	/**
	 * 查询地图显示的经纬信息组<br/>
	 * 查询地图显示的经纬信息组
	 * @param bh
	 * @return
	 */
	public Object[][] getContrlInfo(String sql) {
		Object[][] obj=null;
		try {
			obj = DBHandler.getMultiResult(sql);
		}catch(Exception e) {
			logger.error("查询经纬坐标信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

}
