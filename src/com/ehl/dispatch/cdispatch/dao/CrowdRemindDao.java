package com.ehl.dispatch.cdispatch.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * 拥堵提醒数据访问类
 * 
 * @author xiayx
 *
 */
public class CrowdRemindDao extends BaseDao {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 实体对象名 */
	private String ename = "拥堵提醒";

	/** 表名 */
	private String tname = "T_OA_CROWD_REMINDINFO";
	
	/** 表别名 */
	private String otname = "crowdremind";

	/** 列名 */
	private String[] cnames = { "id", "alarmid", "remindinfo","renewal", "reminddate",
			"reminddepartment", "remindperson" };
	
	/** 日期类型列名 */
	private String[] dcnames = { "reminddate" };

	/** 序列名 */
	private String sname = "SEQ_CROWD_REMINDINFO";
	
	public CrowdRemindDao(){
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
		String[] fileds = getFields();
		fileds[4] = Sql.toChar(fileds[4], 4);
		return Array.join(fileds, ",");
	}
	
	public Object[][] getByAlarmId(String alarmId){
		String _tname = tname + " " + otname;
		String sql = Sql.select(_tname, getSelect(), "alarmid='" + alarmId + "'", null, "id");
		String msg = "获取" + ename + "通过主键编号";
		return FlowUtil.readMilte(sql, logger, msg);
	}
	
	public static Map<String, String> formCrowdRemind(String ALARMID,
			String reminddate, String ALARMREGION, String REPORTPERSON,
			String remindInfo, String renewal) {
		Map<String,String> crowdRemind = new HashMap<String, String>();
		crowdRemind.put("alarmid", ALARMID);
		crowdRemind.put("remindinfo", remindInfo);
		crowdRemind.put("renewal", renewal);
		crowdRemind.put("reminddate", reminddate);
		crowdRemind.put("reminddepartment", ALARMREGION);
		crowdRemind.put("remindperson", REPORTPERSON);
		return crowdRemind;
	}

}
