package com.ehl.dispatch.cdispatch.dao;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.base.Constant;

public class AlarmPublishDao {
	
	public Logger logger = Logger.getLogger(AlarmPublishDao.class);
	/**支队机构名称后缀*/
	public final String ZDJGMC_POST = "市公安局交通警察支队";
	
	/**
	 * 获取所有支队年初至今发生的警情次数
	 * @return 机构警情次数信息
	 */
	public Object[][] getAllZdAllAlarmCount(){
		String alarmCountSql = "select rpad(substr(reportunit,1,4),12,'0') jgid, count(*) count";
		alarmCountSql += " from t_attemper_alarm alarm";
		alarmCountSql += " where reporttime >= trunc(sysdate,'yyyy')";
		alarmCountSql += " and reporttime <= sysdate";
		//警情类型为事故时，该事故必须是总队已处理过的
		//警情类型为拥堵或者施工占道时，必须是高速公路、国省道的
		alarmCountSql +=" and ((eventtype in ('"+AlarmDao.BUILD_CODE+"','"+AlarmDao.CROWD_CODE+"') and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null )" +
				" or (eventtype = '"+AlarmDao.ACC_CODE+"' and (select eventstate from t_attemper_alarm_zd where alarmid=alarm.alarmid) in ('004031','004036','004037','004035')))";
		alarmCountSql += " group by substr(reportunit,1,4)";
		String sql = "select dept.jgid,replace(dept.jgmc,'"+ZDJGMC_POST+"',''),nvl(alarm.count,'0') count";
		sql += " from t_sys_department dept";
		sql += " left outer join ("+alarmCountSql+") alarm";
		sql += " on dept.jgid=alarm.jgid";
		sql += " where dept.jglx = '1' and " + Constant.defaultSift("dept.jgid");
		sql += " order by count desc,dept.jgid";
		Object[][] data = FlowUtil.readMilte(sql,logger,"获取所有支队年初至今发生的警情次数");
		return data;
	}
	
	/**
	 * 获取一个支队及其辖区所有大队年初至今发生的不同类型的警情次数
	 * @param jgid 机构编号
	 * @return 机构警情次数
	 */
	public Object[][] getZdAllDdAlarmCount(String jgid){
		//警情数量sql语句
		StringBuffer alarmCountSql = new StringBuffer();
		alarmCountSql.append("select reportunit jgid")
		.append(",decode(eventtype,'"+AlarmDao.ACC_CODE+"',count(alarmid),0) acc")
		.append(",decode(eventtype,'"+AlarmDao.CROWD_CODE+"',count(alarmid),0) crowd")
		.append(",decode(eventtype,'"+AlarmDao.BUILD_CODE+"',count(alarmid),0) build")
		.append(",count(alarmid) total");
		alarmCountSql.append(" from t_attemper_alarm alarm");
		alarmCountSql.append(" where reporttime >= trunc(sysdate,'yyyy')")
		.append(" and reporttime <= sysdate")
		.append(" and " + Constant.getSiftSelfChildSql("reportunit", jgid))
		//警情类型为事故时，该事故必须是总队已处理过的
		//警情类型为拥堵或者施工占道时，必须是高速公路、国省道的
		.append(" and ((eventtype in ('"+AlarmDao.BUILD_CODE+"','"+AlarmDao.CROWD_CODE+"') and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null )" +
				" or (eventtype = '"+AlarmDao.ACC_CODE+"' and (select eventstate from t_attemper_alarm_zd where alarmid=alarm.alarmid) in ('004031','004036','004037','004035')))")
		.append(" group by reportunit,eventtype");
		alarmCountSql.insert(0, "select jgid,sum(acc) acc,sum(crowd) crowd,sum(build) build,sum(total) total from (");
		alarmCountSql.append(") alarm group by jgid");
		alarmCountSql.insert(0, "(");
		alarmCountSql.append(") alarm");
		//机构sql语句
		StringBuffer deptSql = new StringBuffer();
		deptSql.append("select jgid,jgmc");
		deptSql.append(" from t_sys_department");
		deptSql.append(" where 1=1")
		.append(" and " + Constant.getSiftSelfChildSql("jgid", jgid))
		.append(" and " + Constant.defaultSift());
		deptSql.insert(0, " (");
		deptSql.append(") dept");
		//机构和警情数量左外连接sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select dept.jgid,dept.jgmc,nvl(alarm.acc,0),nvl(alarm.crowd,0),nvl(alarm.build,0),nvl(alarm.total,0) total");
		sql.append(" from"+deptSql.toString());
		sql.append(" left outer join " + alarmCountSql.toString());
		sql.append(" on dept.jgid=alarm.jgid");
		sql.append(" order by total desc,dept.jgid");
		Object[][] data = FlowUtil.readMilte(sql.toString(),logger,"获取所有支队年初至今发生的警情次数");
		return data;
	}
	
	/**
	 * 获取交警提示发布信息
	 * @return 交警提示发布信息
	 */
	public Object[][] getPoliceRemind(){
		StringBuffer sql = new StringBuffer();
		sql.append("select remindid,departmentid,departmentname,username,to_char(remindtime,'yyyy-mm-dd hh24:mi'),remindinfo,alarmid")
		.append(" from t_oa_police_remind")
		.append(" where to_char(remindtime,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')")
		.append(" order by remindtime desc");
		Object[][] data = FlowUtil.readMilte(sql.toString(),logger,"获取交警提示发布信息");
		return data;
	}

}
