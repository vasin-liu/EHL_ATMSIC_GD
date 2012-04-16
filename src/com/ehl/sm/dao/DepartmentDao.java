package com.ehl.sm.dao;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class DepartmentDao {
	
	public static Logger logger = Logger.getLogger(DepartmentDao.class);
	/** 表名 */
	public final static String tname = "t_sys_department";
	/** 数据库表列名 */
	public final static String[] cnames = { "jgid", "jgmc",  "time", "title", "content","apath","ajgid" };
	/** 对象字段名称 */
	public final static String[] fnames = { "jgid", "jgmc", "jgccbh", "jglx"};
	
	public static String getSelect(){
		return "jgid,jgmc,jgccbm,jglx";
	}
	
	/**
	 * 获取机构信息根据机构编号
	 * @param jgid 机构编号
	 * @return 机构名称
	 */
	public static Object[] getByJgid(String jgid){
		String sql = Sql.select(tname, getSelect(),"jgid='"+jgid+"'");
		return FlowUtil.readLine(sql, logger, "获取机构信息根据机构编号");
	}
	
}
