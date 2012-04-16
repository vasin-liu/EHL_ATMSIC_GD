package com.ehl.tmkr.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class ElseDao {
	
	private static Logger logger = Logger.getLogger(ElseDao.class);
	/** 表名前缀，主键ID前缀 */
	public final static String tnamePost = "else";
	/** 表名 */
	public final static String tname = "T_TMKR_ELSE";
	/** 表别名 */
	public final static String otname = "else";
	/** 数据库表列名 */
	public final static String[] cnames = { "id", "jgid", "pname",  "time", "title", "type","apath"};
	/** 对象字段名称 */
	public final static String[] fnames = { "id", "jgid", "jgmc", "pname", "time", "title", "type","apath" };
	/** 序列名 */
	public final static String sname = "SEQ_TMKR_ELSE";
	
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
				"to_char(time,'yyyy-mm-dd hh24:mi'),title,type," +
				"apath";
	}
	
	/**
	 * 添加其他信息
	 * @param elseInfo 其他信息
	 * @return 其他信息主键编号
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
			boolean isSuccess = FlowUtil.write(sql, logger, "添加其他信息");
			id = isSuccess ? id : null;
		}
		return id;
	}
	
	/**
	 * 获取其他信息通过主键编号
	 * @param id 主键编号
	 * @return 其他信息
	 */
	public static Object[] getById(String id) {
		Object[] order = null;
		if (id != null) {
			String sql = Sql.select(tname, getSelect(), "id='" + id + "'");
			order = FlowUtil.readLine(sql, logger, "获取其他信息通过主键编号");
		}
		return order;
	}
	
	
	/**
	 * 修改其他信息
	 * @param elseInfo 其他信息
	 * @return 是否修改成功
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
			isSuccess = FlowUtil.write(sql, logger, "修改其他信息");
		}
		return isSuccess;
	}
}
