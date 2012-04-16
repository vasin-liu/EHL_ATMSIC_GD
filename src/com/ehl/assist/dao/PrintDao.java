package com.ehl.assist.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class PrintDao {
	public static Logger logger = Logger.getLogger(PrintDao.class);
	/** 表名前缀，主键ID前缀 */
	public final static String tnamePost = "print";
	/** 表名 */
	public final static String tname = "T_ASSIST_PRINT";
	/** 表别名 */
	public final static String otname = "print";
	/** 数据库表列名 */
	public final static String[] cnames = { "id", "rid", "jgid", "snum"};
	/** 对象字段名称 */
	public final static String[] fnames = { };
	/** 序列名 */
	public final static String sname = "SEQ_ASSIST_PRINT";
	
	
	/**
	 * 获取主键ID
	 * @return 主键ID
	 */
	public static String getId() {
		return CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * 添加打印数据
	 * @param print 打印对象
	 * @return 主键编号
	 */
	public static String insert(Map<String, String> print){
		if(print == null){
			return null;
		}
		String id = getId();
		print.put("id", id);
		FlowUtil.encapMapSQ(print);
		String sql = Sql.insert(tname, print);
		boolean isSuccess = FlowUtil.write(sql,logger,"添加打印数据");
		return isSuccess ? id : null;
	}
	
	/**
	 * 组成打印对象
	 * @param rid 关联对象主键编号
	 * @param jgid 机构编号
	 * @param snum 序列号
	 * @return 打印对象
	 */
	public static Map<String,String> formPrint(String rid, String jgid, String snum){
		Map<String,String> print = new HashMap<String, String>();
		print.put("rid",rid);
		print.put("jgid", jgid);
		print.put("snum", snum);
		return print;
	}
	
	/**
	 * 获取打印序列号
	 * @param rid 关联对象主键编号
	 * @param jgid 机构编号
	 * @return 序列号
	 */
	public static String getSerialNum(String rid, String jgid){
		if(rid == null || jgid == null){
			return null;
		}
		String sql = Sql.select(tname, "snum","rid='"+rid+"' and jgid='"+jgid+"'");
		Object snum = FlowUtil.readSingle(sql,logger,"获取打印序列号");
		return (String)snum;
	}
	
}
