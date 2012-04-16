package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class LawRuleDao {
	
	private static Logger logger = Logger.getLogger(LawRuleDao.class);
	/** ����ǰ׺������IDǰ׺ */
	public final static String tnamePost = "LAWRULE";
	/** ���� */
	public final static String tname = "T_TMKR_LAWRULE";
	/** ����� */
	public final static String otname = "lawrule";
	/** ���ݿ������ */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "etime","apath"};
	/** �����ֶ����� */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title", "etime", "apath" };
	/** ������ */
	public final static String sname = "SEQ_TMKR_LAWRULE";
	
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
				"to_char(etime,'yyyy-mm-dd'), apath";
	}
	
	/**
	 * ��ӷ��ɷ�����Ϣ
	 * @param planManage ���ɷ�����Ϣ
	 * @return ���ɷ�����Ϣ�������
	 */
	public static String insert(Map<String, String> planManage) {
		String id = null;
		if (planManage != null) {
			String time = planManage.get("time");
			String etime = planManage.get("etime");
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
			if (etime != null) {
				etime = Sql.toDate(etime);
				planManage.put("etime", etime);
			} 
			String sql = Sql.insert(tname, planManage);
			boolean isSuccess = FlowUtil.write(sql, logger, "��ӷ��ɷ�����Ϣ");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * ��ȡ���ɷ�����Ϣͨ���������
	 * @param id �������
	 * @return ���ɷ�����Ϣ
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "��ȡ���ɷ�����Ϣͨ���������");
		}
		return order;
	}
	
	
	/**
	 * �޸ķ��ɷ�����Ϣ
	 * @param lawRule ���ɷ�����Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> lawRule) {
		boolean isSuccess = false;
		if(lawRule != null){
			String time = lawRule.get("time");
			String etime = lawRule.get("etime");
			FlowUtil.encapMapSQ(lawRule);
			if (time != null) {
				time = Sql.toDate(time);
				lawRule.put("time", time);
			}
			if (etime != null) {
				etime = Sql.toDate(etime);
				lawRule.put("etime", etime);
			}
			String id = lawRule.get("id");
			lawRule.remove("id");
			String sql = Sql.update(tname, lawRule, "id="+id);
			isSuccess = FlowUtil.write(sql, logger, "�޸ķ��ɷ�����Ϣ");
		}
		return isSuccess;
	}
	
	/**
	 * ɾ�����ɷ�����Ϣ
	 * @param lawRule ���ɷ�����Ϣ
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public static boolean delete(String id) {
		boolean isSuccess = false;
		if(id != null){
			String sql = Sql.delete(tname, "id='"+id+"'");
			isSuccess = FlowUtil.write(sql, logger, "ɾ�����ɷ�����Ϣ");
		}
		return isSuccess;
	}
	
	
}
