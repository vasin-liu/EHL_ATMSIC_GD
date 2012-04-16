package com.ehl.help.dao;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * 回复数据访问类
 * 
 * @author xiayouxue
 * 
 */
public class ReplyDao extends BaseDao {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 实体对象名 */
	private String ename = "回复";

	/** 表名 */
	private String tname = "T_HELP_REPLY";

	/** 表别名 */
	private String otname = "reply";

	/** 列名 */
	private String[] cnames = { "id", "jgid", "pname", "time", "content", "lid" };

	/** 日期类型列名 */
	private String[] dcnames = { "time" };

	/** 序列名 */
	private String sname = "SEQ_HELP_REPLY";

	public ReplyDao() {
		super.setLogger(logger);
		super.setEname(ename);
		super.setTname(tname);
		super.setOtname(otname);
		super.setCnames(cnames);
		super.setDcnames(dcnames);
		super.setSname(sname);
	}

	@Override
	public String[] getFields() {
		return (String[]) Array.insert(cnames, 2, "jgmc");
	}

	@Override
	public String getSelect() {
		String[] fileds = getFields();
		fileds[2] = "(select jgmc from t_sys_department where jgid="+otname+".jgid)";
		fileds[4] = Sql.toChar(fileds[4], 4);
		return Array.join(fileds, ",");
	}

	/**
	 * 获取回复信息，通过留言编号
	 * 
	 * @param lid
	 *            留言编号
	 * @return 回复信息
	 */
	public Object[][] getByLid(String lid) {
		String _tname = tname + " " + otname;
		String where = "lid='" + lid + "'";
		String sql = Sql.select(_tname, getSelect(), where, null, "id");
		return FlowUtil.readMilte(sql, logger, "获取" + ename + "信息，通过问题编号");
	}

}
