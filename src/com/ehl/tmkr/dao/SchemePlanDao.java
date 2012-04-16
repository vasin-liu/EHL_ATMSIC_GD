package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class SchemePlanDao {
	
	private static Logger logger = Logger.getLogger(SchemePlanDao.class);
	/** 表名前缀，主键ID前缀 */
	public final static String tnamePost = "SCHEMEPLAN";
	/** 表名 */
	public final static String tname = "T_TMKR_SCHEMEPLAN";
	/** 表别名 */
	public final static String otname = "manage";
	/** 数据库表列名 */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "content","apath","ajgid" };
	/** 对象字段名称 */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title", "content", "apath", "ajgid","ajgmc" };
	/** 序列名 */
	public final static String sname = "SEQ_TMKR_SCHEMEPLAN";
	
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
				"content, apath, ajgid,f_get_dept(ajgid)";//,f_get_accdept(id,2,4)
	}
	
	/**
	 * 添加预案方案管理信息
	 * @param planManage 预案方案管理信息
	 * @return 预案方案管理信息主键编号
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
			boolean isSuccess = FlowUtil.write(sql, logger, "添加预案方案管理信息");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * 获取预案方案管理信息通过主键编号
	 * @param id 主键编号
	 * @return 预案方案管理信息
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "获取预案方案管理信息通过主键编号");
		}
		return order;
	}
	
	
	/**
	 * 修改预案方案管理信息
	 * @param plan 预案方案管理信息
	 * @return 是否修改成功
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
			isSuccess = FlowUtil.write(sql, logger, "修改预案方案管理信息");
		}
		return isSuccess;
	}
}
