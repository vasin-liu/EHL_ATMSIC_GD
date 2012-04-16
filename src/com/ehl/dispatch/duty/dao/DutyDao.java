package com.ehl.dispatch.duty.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.appframe.common.Setting;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.CreateSequence;
import com.incors.plaf.alloy.df;
import com.mapinfo.util.fo;

/**
 * 值班信息类
 * 
 * @author xulf
 * 
 */
public class DutyDao {

	/**
	 * 值班信息添加
	 * 
	 * @param duty[0] 部门Id, duty[1] 值班领导； duty[2] 值班人员;duty[3] 值班人员;
	 * @return 是否成功
	 */
	public  boolean insert(Object[] duty) {
		String sql = "insert into t_oa_duty(did,deptid,leader,person,dtime,phone)values('";
		sql += CreateSequence.getMaxForSeq("seq_t_oa_duty", 12) + "','";
		sql += duty[0] + "','" + duty[1] + "','" + duty[2] + "',";
		sql += "to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')";
		//Modified by Liuwx 2011-8-10
		sql += ",'" + duty[3] + "')";
		//Modification finished
		boolean result = DBHandler.execute(sql);
		return result;
	}

	/**
	 * 值班信息更新
	 * 
	 * @param duty[0]
	 *            值班id； duty[1] 部门Id, duty[2] 值班领导； duty[3] 值班人员; duty[4] 值班手机;
	 * @return 是否成功
	 */
	public  boolean update(Object[] duty) {
		String sql = "update t_oa_duty set deptId='" + duty[1] + "',person='"
				+ duty[3] + "',leader='" + duty[2] + "'";
		//Modified by Liuwx 2011-8-10
		sql += ",phone='" + duty[4] + "' ";
		//Modification finished
		sql += " where did='" + duty[0] + "'";
		boolean result = DBHandler.execute(sql);
		return result;
	}

	/**
	 * 根据部门ID和数据库系统时间得到数据
	 * 
	 * @param deptId
	 * @return
	 */
	public  Object[] getByDeptId(String deptId) {
		Object[] result = null;
		if(deptId != null && !deptId.equals("")){
			//Modified by Liuwx 2011-8-10
			String sql = "select did,deptId,leader,person,dtime,phone from t_oa_duty ";
			//Modification finished
			sql += " where deptId='" + deptId + "'";
			//Modified by Xiayx 2011-8-11
			sql += " order by dtime desc";
			sql = "("+sql+")";
			sql = "select * from " + sql + " where rownum = 1";
			//Modified by Liuwx 2011-8-11
//			sql += " and substr(dtime,0,10)='" + getSysTime() + "' ";
			//是否在值日时间区间内
			//Modified by liuwx  2011-06-16
//			sql += " and to_date(dtime,'yyyy-mm-dd hh24:mi:ss') between to_date('" + getDutyTime(isNextDay(),"begin") + "', 'yyyy-mm-dd hh24:mi:ss') ";
//			sql += " and to_date('" + getDutyTime(isNextDay(),"end") + "', 'yyyy-mm-dd hh24:mi:ss') ";
			result = DBHandler.getLineResult(sql);
		}
		return result;
	}
	
	public boolean isNextDay() {
		String sql = "select distinct to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from T_SYS_CONFIG";
		Object[] result = DBHandler.getLineResult(sql);
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTimeString = StringHelper.obj2str(result[0], format.format(now));
		String dateString = dateTimeString.substring(0, 10);
		try {
			return format.parse(dateString + " " + com.appframe.common.Setting.getString("POLICE_DUTY_BEGIN_TIME"))
			.before(format.parse(dateTimeString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获得数据库当前时间，如果没有时间就返回本地当前时间，并且读取配置文件中设定的开始结束时间，作为值日的时间间隔
	 * @author liuwx
	 * @return
	 */
	public String getDutyTime(boolean isNextDate,String state) {
		String time = "";
		String sql = "select distinct to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from T_SYS_CONFIG";
		Object[] result = DBHandler.getLineResult(sql);
		Date now = new Date();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time1 = StringHelper.obj2str(result[0], format1.format(now));
		String time2 = time1.substring(0,10);
		if (isNextDate) {
			if (state == "begin") {
				time = time2 + " " + com.appframe.common.Setting.getString("POLICE_DUTY_BEGIN_TIME");
			}else if (state == "end") {
				Calendar nextDate = Calendar.getInstance();
				try {
					nextDate.setTime(format1.parse(time1));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextDate.add(Calendar.HOUR_OF_DAY, com.appframe.common.Setting.getInt("POLICE_DUTY_DURATION_TIME"));
				//设定为下一天的8:30
				time = format1.format(nextDate.getTime()).substring(0,10) + " " + com.appframe.common.Setting.getString("POLICE_DUTY_END_TIME");
			}
		}else {
			if (state == "begin") {
				Calendar nextDate = Calendar.getInstance();
				try {
					nextDate.setTime(format1.parse(time1));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextDate.add(Calendar.HOUR_OF_DAY, -(com.appframe.common.Setting.getInt("POLICE_DUTY_DURATION_TIME")));
				//设定为前一天的8:30
				time = format1.format(nextDate.getTime()).substring(0,10) + " " + com.appframe.common.Setting.getString("POLICE_DUTY_BEGIN_TIME");
			}else if (state == "end") {
				time = time2 + " " + com.appframe.common.Setting.getString("POLICE_DUTY_END_TIME");
			}
		}
		System.out.println(time);
		return time;
	}

	/**
	 * 获得数据库当前时间，如果没有时间就返回本地当前时间
	 * 
	 * @return
	 */
	public  String getSysTime() {
		String sql = "select distinct to_char(sysdate,'yyyy-mm-dd') from T_SYS_CONFIG";
		Object[] result = DBHandler.getLineResult(sql);
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = StringHelper.obj2str(result[0], format.format(now));
		//设定为当天的8:30
		time += " 08:30:00";
		return time;
	}
	
	/**
	 * 更新值班电话
	 * @param jgid
	 * @param tel
	 * @return
	 */
	public  boolean ModifyTel(String jgid,String tel){
		String sql="update t_sys_department set zbdh1='"+tel+"' where jgid = '"+jgid+"'";
		boolean flag = DBHandler.execute(sql);
		return flag;
		
	}
	
	/**
	 * 根据机构id得到值班电话
	 * @param jgid
	 * @return
	 */
	public  Object geTelByJgid(String jgid){
		String sql ="select zbdh1 from t_sys_department where jgid='"+jgid+"'";
		Object tel = DBHandler.getSingleResult(sql);
		return tel;
	}
	
	/**
	 * 得到所有值班电话
	 * @return
	 */
	public Object[][] getTelAll(String jgid){
		String sql = "select jgmc,zbdh1 from t_sys_department";
		if(!jgid.equals("")){
			sql+=" where jgid in("+jgid+")";
		}
		Object[][] result = DBHandler.getMultiResult(sql);
		return result;
	}
	
	/**
	 * 得到指定机构当前值班人
	 * @return
	 */
	public Object[] getPerson(String jgid){
		Object[] result = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT PERSON FROM T_OA_DUTY ");
		sb.append("WHERE TO_CHAR(TO_DATE(DTIME, 'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD')=TO_CHAR(SYSDATE, 'YYYY-MM-DD') ");
		//Modified by liuwx 2011-06-16
//		sb.append(" and to_date(dtime,'yyyy-mm-dd hh24:mi:ss') between to_date('" + getDutyBeginTime() + "', 'yyyy-mm-dd hh24:mi:ss') ");
//		sb.append(" and to_date('" + getDutyEndTime() + "', 'yyyy-mm-dd hh24:mi:ss') ");
		sb.append("AND DEPTID='"+jgid+"'");
		try {
			result = DBHandler.getLineResult(sb.toString());
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}
	
	/**
	 * 判断值班时间是否今日值班时间
	 * @param dutyTime 值班时间
	 * @return 是否今日值班时间
	 */
	public boolean isTodayInfo(String dutyTime){
		boolean isTodayInfo = false;
		Calendar calendar = Calendar.getInstance();
		//当前时间、设置时间、值班时间
		Date current = calendar.getTime(),setting,duty;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String settingHour = Setting.getString("POLICE_DUTY_BEGIN_TIME");
		//settingHour = "08:30:00";
		String settingDay = sdf.format(current);
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		try {
			setting = sdf.parse(settingDay + " " + settingHour);
			duty = sdf.parse(dutyTime);
			if(current.before(setting)){
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
				setting = calendar.getTime();
			}
			if(duty.after(setting)){
				isTodayInfo = true;
			}
		} catch (ParseException e) {
			System.err.println("日期转换异常！");
		}
		return isTodayInfo;
	}
	
	public static void main(String[] args) {
		DutyDao dao = new DutyDao();
		String dutyTime = "2011-08-11 08:39:55";
		System.out.println(dao.isTodayInfo(dutyTime));
	}
}
