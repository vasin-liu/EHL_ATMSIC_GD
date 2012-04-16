package com.ehl.policeWorks.assess.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.base.Constant;

public class ScoreRecordDao {
	
	private Logger logger = Logger.getLogger(ScoreRecordDao.class); 
	/**�������ͣ�1���¹ʣ�2��ӵ�£�3��ʩ��ռ����4��ӵ�±���*/
	public static int[] atypes = {1,2,3,4};
	/**ԭ�����ͣ�1��©��|��ʱδ��ʵ��2����|δ׼ȷ��ʵ��3����ʱδ����*/
	public static int[] rtypes = {1,2,3};
	/**��ͬ�������Ͷ�Ӧ��ԭ����������*/
	public static int[] ator = {2,3,2,2};
	
	/**
	 * ��Ӷ���Ʒ���Ϣ
	 * @param jgid �������
	 * @param jgmc ��������
	 * @param date �Ʒ־��鷢��ʱ��
	 * @param items ��������
	 * @param reasons �۷�ԭ������
	 * @param reason �Ʒ�ԭ�����
	 * @return
	 */
	public boolean add(String jgid, String jgmc, String date,String aid, String items,
			String reasons, String reason) {
		String aidcname = "aid,";
		if (aid == null) {
			aidcname = "";
			aid = "";
		} else {
			aid = "'" + aid + "',";
		}
		String sql = " insert into T_OA_SCORERECORD(deptid,deptdesc,jfrq,dayid,"+aidcname+"type,rtype,reason) " +
				"values('"+jgid+"','"+jgmc+"',to_date('"+date+"','yyyy-mm-dd HH24:mi:ss'),sysdate,"+aid+"'"+items+"','"+reasons+"','"+reason+"')";
		
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.err.println("����Ʒ�,sql->\n"+sql);
			isSuccess = false;
			logger.error("�������Ʒ���Ϣʧ�ܣ�",e);
		}
		return isSuccess;
	}
	
	/**
	 * ��ȡ�Ʒֱ��в�ͬ���͡���ͬԭ��Ŀ۷���Ϣ����
	 * @param jgid
	 * @param start
	 * @param end
	 * @return
	 */
	public Object[][] getItemCounts(String jgid, String start, String end, boolean isSelf){
		Object[][] items = null;
		if(jgid != null && start != null && end != null){
			String sql = "select type, rtype , count(1) from t_oa_scorerecord ";
			sql += " where ";
			if(isSelf){
				sql += " deptid='" + jgid + "'";
			}else{
				sql += Constant.getSiftSelfChildsSql("deptid", jgid);
			}
			sql += " and " + Constant.defaultSift("deptid");
			if (start.length() == 10) {
				start += " 00:00:00";
			}
			if (end.length() == 10) {
				end += " 23:59:59";
			}
			sql += " and dayid>=to_date('" + start
					+ "','yyyy-mm-dd hh24:mi:ss') ";
			sql += "and dayid<=to_date('" + end
					+ "','yyyy-mm-dd hh24:mi:ss') ";
			sql += " group by type,rtype";
			sql += " order by type,rtype";
			items = FlowUtil.readMilte(sql,logger,"��ȡ�Ʒֱ��в�ͬ���͡���ͬԭ��Ŀ۷���Ϣ����");
		}
		return items;
	}
	
	/**
	 * <pre>
	 * ����������ת��Ϊ��������
	 * ����ͬ���͡���ͬԭ��Ŀ۷���Ϣ��������ת����������ʽ
	 * </pre>
	 * @param data �۷���Ϣ��������
	 * @return ��������
	 */
	public int[][] changeItemCounts(Object[][] data){
		int[][] counts = new int[atypes.length][];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = new int[ator[i]];
			for (int j = 0; j < counts[i].length; j++) {
				counts[i][j] = 0;
			}
		}
		if(data != null){
			int atype;
			int rtype;
			try {
				for (int i = 0; i < data.length; i++) {
					atype = Integer.parseInt(data[i][0].toString())-1;
					rtype = Integer.parseInt(data[i][1].toString())-1;
					counts[atype][rtype] = Integer.parseInt(data[i][2].toString());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return counts;
	}
	
	/**
	 * ɾ������Ʒ���Ϣ
	 * @param jgid �������
	 * @param date ¼��Ʒ�����
	 * @return
	 */
	public boolean delete(String jgid, String date) {
		
		String sql = "delete from T_OA_SCORERECORD where deptid='" + jgid
				+ "' and dayid=to_date('" + date + "','yyyy-mm-dd HH24:mi:ss')";
		
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.err.println("ɾ���Ʒ�,sql->\n"+sql);
			isSuccess = false;
			logger.error("ɾ������Ʒ���Ϣʧ�ܣ�",e);
		}
		
		return isSuccess;
	}

}
