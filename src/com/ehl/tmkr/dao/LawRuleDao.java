package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class LawRuleDao {
	
	private static Logger logger = Logger.getLogger(LawRuleDao.class);
	/** 表名前缀，主键ID前缀 */
	public final static String tnamePost = "LAWRULE";
	/** 表名 */
	public final static String tname = "T_TMKR_LAWRULE";
	/** 表别名 */
	public final static String otname = "lawrule";
	/** 数据库表列名 */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "etime","apath"};
	/** 对象字段名称 */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title", "etime", "apath" };
	/** 序列名 */
	public final static String sname = "SEQ_TMKR_LAWRULE";
	
	/**
	 * 获取主键ID
	 * @return 主键ID
	 */
	public static String getId() {
		return CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * 获取查询字符串
	 * @return 查询字符串
	 */
	public static String getSelect() {
		return "id, jgid, f_get_dept(jgid), pname,"+
				"to_char(time,'yyyy-mm-dd hh24:mi'),title," +
				"to_char(etime,'yyyy-mm-dd'), apath";
	}
	
	/**
	 * 添加法律法规信息
	 * @param planManage 法律法规信息
	 * @return 法律法规信息主键编号
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
			boolean isSuccess = FlowUtil.write(sql, logger, "添加法律法规信息");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * 获取法律法规信息通过主键编号
	 * @param id 主键编号
	 * @return 法律法规信息
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "获取法律法规信息通过主键编号");
		}
		return order;
	}
	
	
	/**
	 * 修改法律法规信息
	 * @param lawRule 法律法规信息
	 * @return 是否修改成功
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
			isSuccess = FlowUtil.write(sql, logger, "修改法律法规信息");
		}
		return isSuccess;
	}
	
	/**
	 * 删除法律法规信息
	 * @param lawRule 法律法规信息
	 * @return 是否删除成功
	 */
	public static boolean delete(String id) {
		boolean isSuccess = false;
		if(id != null){
			String sql = Sql.delete(tname, "id='"+id+"'");
			isSuccess = FlowUtil.write(sql, logger, "删除法律法规信息");
		}
		return isSuccess;
	}
	
	
}
