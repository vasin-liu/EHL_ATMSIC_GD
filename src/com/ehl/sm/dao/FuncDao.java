package com.ehl.sm.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

public class FuncDao extends BaseDao {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 实体对象名 */
	private String ename = "功能";

	/** 表名 */
	private String tname = "T_SYS_FUNC";

	/** 表别名 */
	private String otname = "func";

	/** 列名 */
	private String[] cnames = {"id","n_id", "n_parent_id", "text", "im0", "im1",
			"im2", "n_call", "n_select", "remark", "isdisplay", "isdefault"};

	/** 日期类型列名 */
	private String[] dcnames = {"time"};

	/** 序列名 */
	private String sname = "SEQ_HELP_LEAVE";

	public FuncDao() {
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
		return cnames.clone();
	}

	@Override
	public String getSelect() {
		return Array.join(getFields(), ",");
	}
	
	public Object[][] getById(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select id,n_id,text");
		sql.append(" from t_sys_func");
		sql.append(" where " + Sql.siftId(id));
		sql.append(" order by id");
		String msg = "通过主键编号获取功能信息";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}

	public Object[][] select(Map<String, Object> conditions) {
		String otname = tname + " " + this.otname;
		String sql = Sql.select(otname, getSelect(), null, null, "id");
		String msg = "查询" + ename + "信息";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}

}
