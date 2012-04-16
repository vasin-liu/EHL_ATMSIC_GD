package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class SchemePlanDao {
	
	private static Logger logger = Logger.getLogger(SchemePlanDao.class);
	/** ����ǰ׺������IDǰ׺ */
	public final static String tnamePost = "SCHEMEPLAN";
	/** ���� */
	public final static String tname = "T_TMKR_SCHEMEPLAN";
	/** ����� */
	public final static String otname = "manage";
	/** ���ݿ������ */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "content","apath","ajgid" };
	/** �����ֶ����� */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title", "content", "apath", "ajgid","ajgmc" };
	/** ������ */
	public final static String sname = "SEQ_TMKR_SCHEMEPLAN";
	
	/**
	 * ��ȡ����ID
	 * @return ����ID
	 */
	public static String getId() {
		return CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * ��ȡ��ѯ�ַ���
	 * @return ��ѯ�ַ���
	 */
	public static String getSelect() {
		return "id, jgid, f_get_dept(jgid), pname,"+
				"to_char(time,'yyyy-mm-dd hh24:mi'),title," +
				"content, apath, ajgid,f_get_dept(ajgid)";//,f_get_accdept(id,2,4)
	}
	
	/**
	 * ���Ԥ������������Ϣ
	 * @param planManage Ԥ������������Ϣ
	 * @return Ԥ������������Ϣ�������
	 */
	public static String insert(Map<String, String> planManage) {
		String id = null;
		if (planManage != null) {
			String time = planManage.get("time");
			if (!planManage.containsKey("id")) {
				planManage.put("id", getId());
			}
			id = planManage.get("id");
			FlowUtil.encapMapSQ(planManage);
			if (time == null) {
				time = "sysdate";
			} else {
				time = Sql.toDate(time);
			}
			planManage.put("time", time);
			String sql = Sql.insert(tname, planManage);
			boolean isSuccess = FlowUtil.write(sql, logger, "���Ԥ������������Ϣ");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * ��ȡԤ������������Ϣͨ���������
	 * @param id �������
	 * @return Ԥ������������Ϣ
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "��ȡԤ������������Ϣͨ���������");
		}
		return order;
	}
	
	
	/**
	 * �޸�Ԥ������������Ϣ
	 * @param plan Ԥ������������Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> plan) {
		boolean isSuccess = false;
		if(plan != null){
			String time = plan.get("time");
			FlowUtil.encapMapSQ(plan);
			if (time != null) {
				time = Sql.toDate(time);
				plan.put("time", time);
			}
			String id = plan.get("id");
			plan.remove("id");
			String sql = Sql.update(tname, plan, "id="+id);
			isSuccess = FlowUtil.write(sql, logger, "�޸�Ԥ������������Ϣ");
		}
		return isSuccess;
	}
}
