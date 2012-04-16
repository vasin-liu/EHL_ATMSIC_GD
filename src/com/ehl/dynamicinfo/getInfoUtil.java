package com.ehl.dynamicinfo;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.cdispatch.dao.RoadManageDao;

public class getInfoUtil {
	
	private Logger logger = Logger.getLogger(RoadManageDao.class);
	
	/**
	 * �����¹���Ϣȡ��sql
	 */
	private String accNowSql = "";
	/**
	 * ��ʷ�¹���Ϣȡ��sql
	 */
	private String accaccHistorySql = "";
	
	/**
	 * ��ѯ��ͼ��ʾ�ľ�γ��Ϣ��<br/>
	 * ��ѯ��ͼ��ʾ�ľ�γ��Ϣ��
	 * @param bh
	 * @return
	 */
	public Object[][] getContrlInfo(String sql) {
		Object[][] obj=null;
		try {
			obj = DBHandler.getMultiResult(sql);
		}catch(Exception e) {
			logger.error("��ѯ��γ������Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

}
