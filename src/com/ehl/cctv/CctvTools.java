package com.ehl.cctv;

import org.apache.log4j.Logger;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.appframe.common.Setting;

public class CctvTools{
	Logger logger = Logger.getLogger(CctvTools.class);
//	String datesql = "SELECT DISTINCT SERVERID FROM T_CCTV_CHANNEL WHERE CHLID = (SELECT CHANNELID FROM T_CCTV_CAM WHERE CAMID='" + camid + "')";
	//��Ƶģʽ:1,��Ƶ(Ĭ��);2,ֻչʾ������Ƶ;3���ؿ�����Ƶ����Ƶ-
	public static  final int MODEL_CCTV = 1;
	public static  final int MODEL_TGS = 2;
	public static  final int MODEL_ALL = 3;
	public static  final int MODELTYPE = Setting.getInt("modelType");
	
	/**
	 * @�汾�ţ�1.0
	 * @����˵��������camid�õ���Ƶ�豸����
	 * @�������ڣ�2009-07-17
	 */
	public int getVideoBase(String camid){
		Object baseRes = null;
		int base = -1;
		try {
//			String datesql = "SELECT DISTINCT SERVERID FROM T_CCTV_CHANNEL WHERE DEFAULTCAM ='" + camid + "'";
			String datesql = "SELECT DISTINCT SERVERID FROM T_CCTV_CHANNEL WHERE CHLID = (SELECT CHANNELID FROM T_CCTV_CAM WHERE CAMID='" + camid + "')";
			baseRes = DBHandler.getSingleResult(datesql);
		} catch (Exception e) {
			logger.error("[CCTV]" + "��ȡ�����Ƶ���̳���:");
			logger.error(e.getMessage());
		}
		if(baseRes != null){
			base = StringHelper.obj2int(baseRes,-1);
		}

		return base;
	}
}
