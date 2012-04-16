package com.ehl.tfm.dao.impl;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class LineFromLCBMIS {
	static Logger  logger = Logger.getLogger(LineFromLCBMIS.class);
	private static Object[][] allPoints = null;
	/**
	 * <b>获取全部LCB_PT_MIS数据</b>
	 * param: 无
	 * @return res-二维数组:连线编号，连线名称，连线方向，路段编号，路段点集，路况,第一视频编号，第二视频编号
	 */
	public static Object[][] getRoadPoints(){
		try{
			String sql = "SELECT DLBH,DLMC,QMZ,BMZ,ZB,ZT FROM LCB_PT_MIS WHERE 1=1  ORDER BY DLBH,QMZ ";
		    allPoints = DBHandler.getMultiResult(sql);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("获取全部LCB_PT_MIS数据报错："+ex.getMessage());
		}
	    return allPoints;
	}
	
	/**
	 * <b>获取内存中LCB_PT_MIS数据</b>
	 * param: 无
	 * @return res-二维数组.
	 */
	public static Object[][] getStaticRoadPoints(){
	    return allPoints;
	}
}
