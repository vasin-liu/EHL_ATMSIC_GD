package com.ehl.policeWorks.newsFiles.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class OrderContributionDao {
	
	private static Logger logger = Logger.getLogger(OrderContributionDao.class);
	/** ����ǰ׺������IDǰ׺ */
	public final static String tnamePost = "ORDER";
	/** ���� */
	public final static String tname = "T_OA_ORDERCONTRIBUTION";
	/** ����� */
	public final static String otname = "order";
	/** ���ݿ������ */
	public final static String[] cnames = { "id", "jgid", "rtime", "pname",  "time", "content","apath" };
	/** �����ֶ����� */
	public final static String[] fnames = { "id", "jgid", "jgmc", "rtime", "pname", "time", "content", "apath", "ajginfos" };
	/** ������ */
	public final static String sname = "SEQ_OA_ORDERCONTRIBUTION";
	
	/**
	 * ��ȡ����ID
	 * @return ����ID
	 */
	public static String getId() {
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * ��ȡ��ѯ�ַ���
	 * @return ��ѯ�ַ���
	 */
	public static String getSelect() {
		return "id, jgid, f_get_dept(jgid), pname,"+
				"to_char(rtime,'yyyy-mm-dd hh24:mi')," +
				"to_char(time,'yyyy-mm-dd hh24:mi')," +
				"content, apath";//,f_get_accdept(id,2,4)
	}
	
	/**
	 * ���Լ����Ϣ
	 * @param order Լ����Ϣ
	 * @return Լ����Ϣ�������
	 */
	public static String insert(Map<String, String> order) {
		String id = null;
		if (order != null) {
			String rtime = order.get("rtime");
			String time = order.get("time");
			if (!order.containsKey("id")) {
				order.put("id", getId());
			}
			id = order.get("id");
			FlowUtil.encapMapSQ(order);
			if (rtime == null) {
				rtime = "sysdate";
			} else {
				rtime = Sql.toDate(rtime);
			}
			if (time == null) {
				time = "sysdate";
			} else {
				time = Sql.toDate(time);
			}
			order.put("time", time);
			order.put("rtime", rtime);
			String sql = Sql.insert(tname, order);
			boolean isSuccess = FlowUtil.write(sql, logger, "���Լ����Ϣ");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * ��ѯδǩ�յ�Լ����Ϣ
	 * @param jgid �������
	 * @return δǩ�յ�Լ����Ϣ
	 */
	public static Object[][] searchUnSign(String jgid){
		Object[][] datas = null;
		if(jgid != null){
			String sql = "select oc.id,(select jgmc from t_sys_department where jgid=oc.jgid)," +
					"oc.pname,to_char(oc.time,'yyyy-mm-dd hh24:mi'),substr(oc.content,1,15),decode(ad.state,'1','δǩ��','2','��ǩ��','..'),ad.state";
			sql += " from t_oa_ordercontribution oc, t_oa_acceptdept ad";
			sql += " where oc.id = ad.adid and ad.state = '1' and ad.rpdcode = '440100000000'";
			sql += " order by oc.time";
			datas = FlowUtil.readMilte(sql,logger,"��ѯδǩ�յ�Լ����Ϣ");
		}
		return datas;
	}
	
	/**
	 * ��ȡԼ����Ϣͨ���������
	 * @param id �������
	 * @return Լ����Ϣ
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "��ȡԼ����Ϣͨ���������");
		}
		return order;
	}
	
	
	/**
	 * �޸�Լ����Ϣ
	 * @param order Լ����Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> order) {
		boolean isSuccess = false;
		if(order != null){
			String time = order.get("time");
			FlowUtil.encapMapSQ(order);
			if (time != null) {
				time = Sql.toDate(time);
				order.put("time", time);
			}
			String id = order.get("id");
			order.remove("id");
			String sql = Sql.update(tname, order, "id="+id);
			isSuccess = FlowUtil.write(sql, logger, "�޸�Լ����Ϣ");
		}
		return isSuccess;
	}
	
	
	
	
}
