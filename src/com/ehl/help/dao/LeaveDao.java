package com.ehl.help.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.base.Constant;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * 留言数据访问类
 * 
 * @author Xiayx
 * 
 */
public class LeaveDao extends BaseDao {
	
	public static final String FKLX = "940501";
	public static final String JJCD = "940502";
	public static final String STATE = "940503";

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 实体对象名 */
	private String ename = "留言";

	/** 表名 */
	private String tname = "T_HELP_LEAVE";
	
	/** 表别名 */
	private String otname = "leave";

	/** 列名 */
	private String[] cnames = {"id", "jgid", "pname", "time", "title",
			"content", "apath", "fklx", "func", "jjcd", "state"};

	/** 日期类型列名 */
	private String[] dcnames = { "time" };

	/** 序列名 */
	private String sname = "SEQ_HELP_LEAVE";
	
	public LeaveDao(){
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
		return (String[])Array.insert(cnames, 2, "jgmc");
	}

	@Override
	public String getSelect() {
		String[] fileds = getFields();
		fileds[2] = "(select jgmc from t_sys_department where jgid="+otname+".jgid) jgmc";
		fileds[4] = Sql.toChar(fileds[4], 4);
		return Array.join(fileds, ",");
	}
	
	public String selectSql(String select,Map<String,Object> conditions){
		StringBuffer sift = new StringBuffer(" 1=1");
		String time = (String) conditions.get("time");
		if (time != null && !time.equals("") && !time.equals(",")) {
			sift.append(" and " + Sql.siftDate("time", time));
		}
		String jgid = (String) conditions.get("jgid");
		if (jgid != null && !jgid.equals("")) {
			sift.append(" and " + Constant.getSiftSelfChildsSql("jgid", jgid));
		}
		String title = (String) conditions.get("title");
		if (title != null && !title.equals("")) {
			sift.append(" and title like '%" + title + "%'");
		}
		String state = (String)conditions.get("state");
		if(state != null && !state.equals("")){
			sift.append(" and state='" + state + "'");
		}
		String fklx = (String)conditions.get("fklx");
		if(fklx != null && !fklx.equals("")){
			sift.append(" and fklx='" + fklx + "'");
		}
		String func = (String)conditions.get("func");
		if(func != null && !func.equals("")){
			sift.append(" and func='" + func + "'");
		}
		return Sql.select(tname + " " + otname, select, sift.toString());
	}
	
	
	public List<Object> select(Map<String,Object> params){
		List<Object> list = new ArrayList<Object>();
		String orderBy = params.get("sidx") + " " + params.get("sord");
		String select = getSelect() + ", row_number() over(order by "+ orderBy+") rn";
		StringBuffer sql = new StringBuffer(selectSql(select, params));
		int pnum = Integer.parseInt((String)params.get("page"));
		int rowNum = Integer.parseInt((String)params.get("rows"));
		int srn = (pnum - 1) * rowNum + 1;
		int ern = pnum * rowNum;
		sql.insert(0, "select t.* from (").append(") t");
		sql.append(" where t.rn between " + srn + " and " + ern);
		String msg = "查询留言信息";
		list.add(FlowUtil.readMilte(sql.toString(), logger, msg));
		msg = "查询留言信息数据总条数";
		sql = new StringBuffer(selectSql("to_char(count(id))", params));
		list.add(FlowUtil.readSingle(sql.toString(), logger, msg));
		return list;
	}

}
