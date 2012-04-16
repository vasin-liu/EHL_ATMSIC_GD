package com.ehl.cctv;

import org.apache.log4j.Logger;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.appframe.common.Setting;

public class CctvTools{
	Logger logger = Logger.getLogger(CctvTools.class);
//	String datesql = "SELECT DISTINCT SERVERID FROM T_CCTV_CHANNEL WHERE CHLID = (SELECT CHANNELID FROM T_CCTV_CAM WHERE CAMID='" + camid + "')";
	//视频模式:1,视频(默认);2,只展示卡口视频;3加载卡口视频和视频-
	public static  final int MODEL_CCTV = 1;
	public static  final int MODEL_TGS = 2;
	public static  final int MODEL_ALL = 3;
	public static  final int MODELTYPE = Setting.getInt("modelType");
	
	/**
	 * @版本号：1.0
	 * @函数说明：根据camid得到视频设备厂家
	 * @创建日期：2009-07-17
	 */
	public int getVideoBase(String camid){
		Object baseRes = null;
		int base = -1;
		try {
//			String datesql = "SELECT DISTINCT SERVERID FROM T_CCTV_CHANNEL WHERE DEFAULTCAM ='" + camid + "'";
			String datesql = "SELECT DISTINCT SERVERID FROM T_CCTV_CHANNEL WHERE CHLID = (SELECT CHANNELID FROM T_CCTV_CAM WHERE CAMID='" + camid + "')";
			baseRes = DBHandler.getSingleResult(datesql);
		} catch (Exception e) {
			logger.error("[CCTV]" + "获取相关视频厂商出错:");
			logger.error(e.getMessage());
		}
		if(baseRes != null){
			base = StringHelper.obj2int(baseRes,-1);
		}

		return base;
	}
}
