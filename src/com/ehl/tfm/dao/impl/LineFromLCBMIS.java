package com.ehl.tfm.dao.impl;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class LineFromLCBMIS {
	static Logger  logger = Logger.getLogger(LineFromLCBMIS.class);
	private static Object[][] allPoints = null;
	/**
	 * <b>��ȡȫ��LCB_PT_MIS����</b>
	 * param: ��
	 * @return res-��ά����:���߱�ţ��������ƣ����߷���·�α�ţ�·�ε㼯��·��,��һ��Ƶ��ţ��ڶ���Ƶ���
	 */
	public static Object[][] getRoadPoints(){
		try{
			String sql = "SELECT DLBH,DLMC,QMZ,BMZ,ZB,ZT FROM LCB_PT_MIS WHERE 1=1  ORDER BY DLBH,QMZ ";
		    allPoints = DBHandler.getMultiResult(sql);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("��ȡȫ��LCB_PT_MIS���ݱ���"+ex.getMessage());
		}
	    return allPoints;
	}
	
	/**
	 * <b>��ȡ�ڴ���LCB_PT_MIS����</b>
	 * param: ��
	 * @return res-��ά����.
	 */
	public static Object[][] getStaticRoadPoints(){
	    return allPoints;
	}
}
