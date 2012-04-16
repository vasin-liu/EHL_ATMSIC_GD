package com.ehl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.appframe.data.sql.DBHandler;
import com.ehl.assist.dao.PrintDao;
import com.ehl.sm.base.Constant;

public class Print {
	
	
	/**
	 * 获取打印文件序列号
	 * @param application 应用场景
	 * @param id 警情编号
	 * @param jgid 机构编号
	 * @return 打印文件序列号
	 */
	public static String getSerialNumber(ServletContext application,String id, String jgid) {
		String snum = PrintDao.getSerialNum(id, jgid);
		if(snum == null){
			snum = getSerialNumber(application, jgid);
			if(id != null && snum != null ){
				PrintDao.insert(PrintDao.formPrint(id, jgid, snum));
			}
		}
		return snum;
	}
	
	/**
	 * 获取打印文件序列号
	 * @param application 应用场景
	 * @param jgid 机构编号
	 * @return 打印文件序列号
	 */
	@SuppressWarnings("unchecked")
	public static String getSerialNumber(ServletContext application, String jgid) {
		String snstr = null;
		if (application != null && jgid != null) {
			Object snobj = application.getAttribute(Constant.PFBH_VAR);
			Map<String, Integer> snmap = null;
			if (snobj != null) {
				try {
					snmap = (HashMap<String, Integer>) snobj;
				} catch (ClassCastException e) {
					snmap = new HashMap<String, Integer>();
				}
				snobj = snmap.get(jgid);
				if (snobj != null && snobj instanceof Integer) {
					Integer snint = (Integer) snobj;
					if (snint >= 1) {
						snstr = "00" + String.valueOf(snint);
						snstr = snstr.substring(snstr.length() - 3);
						snint = snint + 1;
						snmap.put(jgid, snint);
						application.setAttribute(Constant.PFBH_VAR, snmap);
					}
				}
			} else {
				snmap = new HashMap<String, Integer>();
			}
			if (snstr == null) {
				snmap.put(jgid, new Integer(2));
				application.setAttribute(Constant.PFBH_VAR, snmap);
				snstr = "001";
			}
		}
		return snstr;
	}
	
	/**
	 * <pre>
	 * 获取打印编号
	 * 打印编号=填报日期+序列号
	 * </pre>
	 * @param rtime 填报时间
	 * @param snum 序列号
	 * @return 打印编号
	 */
	public static String getBh(String rtime,String snum){
		if (rtime == null || rtime.length() < 10 || snum == null) {
			return null;
		}
		rtime = rtime.substring(0,10).replace("-", "");
		return rtime + snum;
	}
	
	/**
	 * <pre>
	 * 转换成打印时间
	 * 转换中不符合要求或者发生异常，取当前时间
	 * </pre>
	 * @param time 时间 
	 * @return 打印时间
	 */
	public static String toPrintTime(String time){
		Date timeDate;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(time == null || time.length() < 16){
			timeDate = new Date();
		}else{
			time = time.substring(0,16);
			try {
				timeDate = formater.parse(time);
			} catch (ParseException e) {
				System.err.println("日期转换异常！");
				timeDate = new Date();
			}
		}
		formater.applyPattern("yyyy/M/d H:m");
		return formater.format(timeDate);
	}
	
	/**
	 * <pre>
	 * 获取数据库接报时间
	 * </pre>
	 * @param time 时间 
	 * @return 打印时间
	 */
	public static String getPrintTimeFromBase(String id){
		String Sql = "select to_char(reportTime,'yyyy-mm-dd HH24:MI') from t_attemper_alarm_zd where alarmid = '"+ id +"'";
		Object[] reportTime = DBHandler.getLineResult(Sql);
		return String.valueOf(reportTime[0]);
	}
	
	/**
	 * <pre>
	 * 获取数据库接收人信息
	 * </pre>
	 * @param id 主键id 
	 * @return 打印时间
	 */
	public static String getAlarmTimeFromBase(String id){
		String Sql = "select to_char(rtime,'yyyy-mm-dd HH24:MI') from t_oa_acceptdept t where aid = '"+ id +"'";
		Object[] reportTime = DBHandler.getLineResult(Sql);
		if(reportTime == null) {
			return " ";
		} else {
			return String.valueOf(reportTime[0]);
		}
	}
	
	public static String getAlarmPersonFromBase(String id,String jgid){
		String Sql = "select rpname from t_oa_acceptdept where aid = '"+ id +"' and rpdcode= '"+ jgid +"' and rpname is not null";
		Object[] reportPerson = DBHandler.getLineResult(Sql);
		if(reportPerson == null) {
			return " ";
		} else {
			return String.valueOf(reportPerson[0]);
		}
	}
	
	/**
	 * <pre>
	 * 获取数据库接报时间
	 * </pre>
	 * @return 接报时间
	 */
	public static String getNoticeTimeFromBase(String id){
		String Sql = "select to_char(stime,'yyyy-mm-dd HH24:MI') from t_oa_notice t where id = '"+ id +"'";
		Object[] reportTime = DBHandler.getLineResult(Sql);
		if(reportTime == null) {
			return " ";
		} else {
			return String.valueOf(reportTime[0]);
		}
	}
	
	/**
	 * <pre>
	 * 获取数据库接收人信息
	 * </pre>
	 * @return 接收人
	 */
	public static String getNoticePerSonFromBase(String id, String jgid){
		String Sql = "select rpname from t_oa_acceptdept where aid = '"+ id +"' and rpdcode= '"+ jgid +"' and rpname is not null";
		Object[] reportperson = DBHandler.getLineResult(Sql);
		if(reportperson == null) {
			return " ";
		} else {
			return String.valueOf(reportperson[0]);
		}
	}
}
