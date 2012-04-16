package com.ehl.dispatch.cdispatch.util;

import com.appframe.data.sql.DBHandler;

public class LcbPtMisUtil {

	/**
	 * 根据道路名称，起始里程取得发生事件的经纬度<br/>
	 * 根据道路名称，起始里程取得事件的经纬度
	 * 
	 * @param roadid
	 */
	public static String[] getXYvalue(String roadName, String Kmz, String mz) {
		String sql = " select zb from LCB_PT_MIS where dlmc like '%" + roadName
				+ "%'  and qmz = '" + Kmz + "'";
		String returnValue[] = new String[2];
		String x = "";
		String y = "";
		try {
			Object[] object = DBHandler.getLineResult(sql);
			String xy;
			if(object != null) {
				xy = (String) object[0];
				if (xy != null && !xy.equals("")) {
					String[] str = xy.split(",");
					if (str != null && str.length == 2) {
						x = str[0];
						y = str[1];
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		returnValue[0] = x;
		returnValue[1] = y;
		return returnValue;
	}

	/**
	 * 根据道路名称，起始，终止里程更新LCB_PT_MIS的拥堵状态<br/>
	 * 根据道路名称，起始，终止里程更新LCB_PT_MIS的拥堵状态（zb）
	 * 
	 * @param roadName
	 *            道路名称
	 * @param Kmz
	 *            开始千米值
	 * @param ZKmz
	 *            终止千米值
	 * @param ztFlg
	 *            更新状态 拥堵状态0-畅通 1-拥挤 2-拥堵
	 * @param updateTypeFlg
	 *            更新类型标志 警情上报系统手动更新0
	 * @return
	 */
	public static void updateLcbptmisZt(String roadName, String Kmz,
			String ZKmz, String ztFlg, String forward, String updateTypeFlg) {

		String sql = " update LCB_PT_MIS" + " set ZT =  '" + ztFlg + "', UPDATE_TYPE_FLG =  '" + updateTypeFlg + "',DLFX='"
				+ forward + "'" + " where to_Number(QMZ) >= " + Kmz
				+ " and to_Number(QMZ) <=" + ZKmz + " and dlmc like '%"
				+ roadName + "%'";
		try {
			System.out.println(sql);
			DBHandler.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
