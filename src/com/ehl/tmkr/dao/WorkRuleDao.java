package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class WorkRuleDao {
	
	private static Logger logger = Logger.getLogger(WorkRuleDao.class);
	/** 表名前缀，主键ID前缀 */
	public final static String tnamePost = "WORKRULE";
	/** 表名 */
	public final static String tname = "T_TMKR_WORKRULE";
	/** 表别名 */
	public final static String otname = "workrule";
	/** 数据库表列名 */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "apath" };
	/** 对象字段名称 */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title",  "apath" };
	/** 序列名 */
	public final static String sname = "SEQ_TMKR_WORKRULE";
	
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
				" apath";
	}
	
	/**
	 * 添加工作规定信息
	 * @param workRule 工作规定信息
	 * @return 工作规定信息主键编号
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
			boolean isSuccess = FlowUtil.write(sql, logger, "添加工作规定信息");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * 获取工作规定信息通过主键编号
	 * @param id 主键编号
	 * @return 工作规定信息
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "获取工作规定信息通过主键编号");
		}
		return order;
	}
	
	
	/**
	 * 修改工作规定信息
	 * @param workRule 工作规定信息
	 * @return 是否修改成功
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
			isSuccess = FlowUtil.write(sql, logger, "修改工作规定信息");
		}
		return isSuccess;
	}
}
