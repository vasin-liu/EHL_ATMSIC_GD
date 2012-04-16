package com.ehl.policeWorks.newsFiles.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class OrderContributionDao {
	
	private static Logger logger = Logger.getLogger(OrderContributionDao.class);
	/** 表名前缀，主键ID前缀 */
	public final static String tnamePost = "ORDER";
	/** 表名 */
	public final static String tname = "T_OA_ORDERCONTRIBUTION";
	/** 表别名 */
	public final static String otname = "order";
	/** 数据库表列名 */
	public final static String[] cnames = { "id", "jgid", "rtime", "pname",  "time", "content","apath" };
	/** 对象字段名称 */
	public final static String[] fnames = { "id", "jgid", "jgmc", "rtime", "pname", "time", "content", "apath", "ajginfos" };
	/** 序列名 */
	public final static String sname = "SEQ_OA_ORDERCONTRIBUTION";
	
	/**
	 * 获取主键ID
	 * @return 主键ID
	 */
	public static String getId() {
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * 获取查询字符串
	 * @return 查询字符串
	 */
	public static String getSelect() {
		return "id, jgid, f_get_dept(jgid), pname,"+
				"to_char(rtime,'yyyy-mm-dd hh24:mi')," +
				"to_char(time,'yyyy-mm-dd hh24:mi')," +
				"content, apath";//,f_get_accdept(id,2,4)
	}
	
	/**
	 * 添加约稿信息
	 * @param order 约稿信息
	 * @return 约稿信息主键编号
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
			boolean isSuccess = FlowUtil.write(sql, logger, "添加约稿信息");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * 查询未签收的约稿信息
	 * @param jgid 机构编号
	 * @return 未签收的约稿信息
	 */
	public static Object[][] searchUnSign(String jgid){
		Object[][] datas = null;
		if(jgid != null){
			String sql = "select oc.id,(select jgmc from t_sys_department where jgid=oc.jgid)," +
					"oc.pname,to_char(oc.time,'yyyy-mm-dd hh24:mi'),substr(oc.content,1,15),decode(ad.state,'1','未签收','2','已签收','..'),ad.state";
			sql += " from t_oa_ordercontribution oc, t_oa_acceptdept ad";
			sql += " where oc.id = ad.adid and ad.state = '1' and ad.rpdcode = '440100000000'";
			sql += " order by oc.time";
			datas = FlowUtil.readMilte(sql,logger,"查询未签收的约稿信息");
		}
		return datas;
	}
	
	/**
	 * 获取约稿信息通过主键编号
	 * @param id 主键编号
	 * @return 约稿信息
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "获取约稿信息通过主键编号");
		}
		return order;
	}
	
	
	/**
	 * 修改约稿信息
	 * @param order 约稿信息
	 * @return 是否修改成功
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
			isSuccess = FlowUtil.write(sql, logger, "修改约稿信息");
		}
		return isSuccess;
	}
	
	
	
	
}
