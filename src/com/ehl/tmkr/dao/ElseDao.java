package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class ElseDao {
	
	private static Logger logger = Logger.getLogger(ElseDao.class);
	/** ����ǰ׺������IDǰ׺ */
	public final static String tnamePost = "else";
	/** ���� */
	public final static String tname = "T_TMKR_ELSE";
	/** ����� */
	public final static String otname = "else";
	/** ���ݿ������ */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "type","apath"};
	/** �����ֶ����� */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title", "type","apath" };
	/** ������ */
	public final static String sname = "SEQ_TMKR_ELSE";
	
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
				"to_char(time,'yyyy-mm-dd hh24:mi'),title,type," +
				"apath";
	}
	
	/**
	 * ���������Ϣ
	 * @param elseInfo ������Ϣ
	 * @return ������Ϣ�������
	 */
	public static String insert(Map<String, String> elseInfo) {
		String id = null;
		if (elseInfo != null) {
			String time = elseInfo.get("time");
			if (!elseInfo.containsKey("id")) {
				elseInfo.put("id", getId());
			}
			id = elseInfo.get("id");
			FlowUtil.encapMapSQ(elseInfo);
			if (time == null) {
				time = "sysdate";
			} else {
				time = Sql.toDate(time);
			}
			elseInfo.put("time", time);
			String sql = Sql.insert(tname, elseInfo);
			boolean isSuccess = FlowUtil.write(sql, logger, "���������Ϣ");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * ��ȡ������Ϣͨ���������
	 * @param id �������
	 * @return ������Ϣ
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "��ȡ������Ϣͨ���������");
		}
		return order;
	}
	
	
	/**
	 * �޸�������Ϣ
	 * @param elseInfo ������Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> elseInfo) {
		boolean isSuccess = false;
		if(elseInfo != null){
			String time = elseInfo.get("time");
			FlowUtil.encapMapSQ(elseInfo);
			if (time != null) {
				time = Sql.toDate(time);
				elseInfo.put("time", time);
			}
			String id = elseInfo.get("id");
			elseInfo.remove("id");
			String sql = Sql.update(tname, elseInfo, "id="+id);
			isSuccess = FlowUtil.write(sql, logger, "�޸�������Ϣ");
		}
		return isSuccess;
	}
}
