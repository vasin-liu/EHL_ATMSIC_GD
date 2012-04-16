package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class WorkRuleDao {
	
	private static Logger logger = Logger.getLogger(WorkRuleDao.class);
	/** ����ǰ׺������IDǰ׺ */
	public final static String tnamePost = "WORKRULE";
	/** ���� */
	public final static String tname = "T_TMKR_WORKRULE";
	/** ����� */
	public final static String otname = "workrule";
	/** ���ݿ������ */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "apath" };
	/** �����ֶ����� */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title",  "apath" };
	/** ������ */
	public final static String sname = "SEQ_TMKR_WORKRULE";
	
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
				" apath";
	}
	
	/**
	 * ��ӹ����涨��Ϣ
	 * @param workRule �����涨��Ϣ
	 * @return �����涨��Ϣ�������
	 */
	public static String insert(Map<String, String> workRule) {
		String id = null;
		if (workRule != null) {
			String time = workRule.get("time");
			if (!workRule.containsKey("id")) {
				workRule.put("id", getId());
			}
			id = workRule.get("id");
			FlowUtil.encapMapSQ(workRule);
			if (time == null) {
				time = "sysdate";
			} else {
				time = Sql.toDate(time);
			}
			workRule.put("time", time);
			String sql = Sql.insert(tname, workRule);
			boolean isSuccess = FlowUtil.write(sql, logger, "��ӹ����涨��Ϣ");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * ��ȡ�����涨��Ϣͨ���������
	 * @param id �������
	 * @return �����涨��Ϣ
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "��ȡ�����涨��Ϣͨ���������");
		}
		return order;
	}
	
	
	/**
	 * �޸Ĺ����涨��Ϣ
	 * @param workRule �����涨��Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> workRule) {
		boolean isSuccess = false;
		if(workRule != null){
			String time = workRule.get("time");
			FlowUtil.encapMapSQ(workRule);
			if (time != null) {
				time = Sql.toDate(time);
				workRule.put("time", time);
			}
			String id = workRule.get("id");
			workRule.remove("id");
			String sql = Sql.update(tname, workRule, "id="+id);
			isSuccess = FlowUtil.write(sql, logger, "�޸Ĺ����涨��Ϣ");
		}
		return isSuccess;
	}
}
