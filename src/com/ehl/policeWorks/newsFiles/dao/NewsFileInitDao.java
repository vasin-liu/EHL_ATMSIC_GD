package com.ehl.policeWorks.newsFiles.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class NewsFileInitDao {

	private static Logger logger = Logger.getLogger(NewsFileInitDao.class);
	/** ���� */
	public final static String tname = "T_OA_NEWSFILEINIT";
	/** ����� */
	public final static String otname = "";
	/** ���ݿ������ */
	public final static String[] cnames = { "jgid", "time", "type", "sbtype",
			"count" };

	public static String sift(Map<String, String> newsFile) {
		FlowUtil.encapMapSQ(newsFile);
		return Sql.join(newsFile, Sql.sepWhereItem);
	}

	/**
	 * ��ȡ��Ϣ�ļ���ʼ�����ݣ�����ǰδ¼������ݣ�
	 * 
	 * @param jgid
	 *            �������
	 * @return ��Ϣ�ļ���ʼ������
	 */
	public static Object[][] getCounts(String jgid) {
		if (jgid == null) {
			return null;
		}
		String select = "type,sbtype,count";
		String where = "jgid='" + jgid + "' and time is null";
		String sql = Sql.select(tname, select, where);
		return FlowUtil.readMilte(sql, logger, "��ȡ��Ϣ�ļ���ʼ�����ݣ�����ǰδ¼������ݣ�");
	}

	/**
	 * ��ȡ��Ϣ�ļ���ʼ�����ݣ�����ʱ��������ݣ�
	 * 
	 * @param jgid
	 *            �������
	 * @param stime
	 *            ��ʼʱ��
	 * @param etime
	 *            ����ʱ��
	 * @return ��Ϣ�ļ���ʼ������
	 */
	public static Object[][] getCounts(String jgid, String stime, String etime) {
		if (jgid == null || stime == null || etime == null) {
			return null;
		}
		String select = "type,sbtype,sum(count)";
		String where = "jgid='" + jgid + "' and time is not null and "
				+ Sql.getWhereDate("time", stime, etime, 2);
		String groupBy = "type,sbtype";
		String sql = Sql.select(tname, select, where, groupBy);
		return FlowUtil.readMilte(sql, logger, " ��ȡ��Ϣ�ļ���ʼ�����ݣ�����ʱ��������ݣ�");
	}

	/**
	 * ��ȡ��Ϣ�ļ���ʼ�����ݣ��������ݣ�
	 * 
	 * @param jgid
	 *            �������
	 * @param stime
	 *            ��ʼʱ��
	 * @param etime
	 *            ����ʱ��
	 * @return ��Ϣ�ļ���ʼ������
	 */
	public static Object[][] getTotalCounts(String jgid, String stime, String etime) {
		if (jgid == null) {
			return null;
		}
		String select = "type,sbtype,sum(count)";
		String whereTime = "";
		if(stime != null){
			whereTime += " to_char(time,'yyyy-mm-dd')>='" + stime + "'";
		}
		if(etime != null){
			if(!whereTime.equals("")){
				whereTime += " and";
			}
			whereTime += " to_char(time,'yyyy-mm-dd')<='"+etime+"'";
			whereTime = "("+whereTime+")";
		}
		if(!whereTime.equals("")){
			whereTime += " or";
		}
		whereTime += " time is null";
		whereTime = " and ("+whereTime+")";
		String where = "jgid='" + jgid + "' " + whereTime;
		String groupBy = "type,sbtype";
		String sql = Sql.select(tname, select, where, groupBy);
		return FlowUtil.readMilte(sql, logger, "��ȡ��Ϣ�ļ���ʼ�����ݣ��������ݣ�");
	}

	public static void main(String[] args) {
		
	}

}
