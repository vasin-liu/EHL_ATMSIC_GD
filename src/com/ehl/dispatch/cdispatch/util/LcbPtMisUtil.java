package com.ehl.dispatch.cdispatch.util;

import com.appframe.data.sql.DBHandler;

public class LcbPtMisUtil {

	/**
	 * ���ݵ�·���ƣ���ʼ���ȡ�÷����¼��ľ�γ��<br/>
	 * ���ݵ�·���ƣ���ʼ���ȡ���¼��ľ�γ��
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
	 * ���ݵ�·���ƣ���ʼ����ֹ��̸���LCB_PT_MIS��ӵ��״̬<br/>
	 * ���ݵ�·���ƣ���ʼ����ֹ��̸���LCB_PT_MIS��ӵ��״̬��zb��
	 * 
	 * @param roadName
	 *            ��·����
	 * @param Kmz
	 *            ��ʼǧ��ֵ
	 * @param ZKmz
	 *            ��ֹǧ��ֵ
	 * @param ztFlg
	 *            ����״̬ ӵ��״̬0-��ͨ 1-ӵ�� 2-ӵ��
	 * @param updateTypeFlg
	 *            �������ͱ�־ �����ϱ�ϵͳ�ֶ�����0
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
