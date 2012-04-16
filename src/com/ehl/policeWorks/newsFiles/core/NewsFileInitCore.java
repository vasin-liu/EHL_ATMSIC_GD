package com.ehl.policeWorks.newsFiles.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.appframe.common.Setting;
import com.ehl.policeWorks.newsFiles.dao.NewsFileDao;
import com.ehl.policeWorks.newsFiles.dao.NewsFileInitDao;
import com.ehl.util.Array;

public class NewsFileInitCore {
	
	/**
	 * 获取被采用的信息文件篇数（上线前2011-01-01~2011-08-10）
	 * @param jgid 机构编号
	 * @return 信息文件篇数
	 */
	public static int[][] getCounts(String jgid){
		Object[][] data = NewsFileInitDao.getCounts(jgid);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * 获取被采用的信息文件篇数（试用时间段内2011-08-11~2011-09-01）
	 * @param jgid 机构编号
	 * @param stime 起始时间
	 * @param etime 结束时间
	 * @return 信息文件篇数
	 */
	public static int[][] getCounts(String jgid, String stime, String etime){
		Object[][] data = NewsFileInitDao.getCounts(jgid, stime, etime);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * 获取被采用的信息文件篇数（）
	 * @param jgid 机构编号
	 * @return 信息文件篇数
	 */
	public static int[][] getTotalCounts(String jgid,String stime, String etime){
		Object[][] data = NewsFileInitDao.getTotalCounts(jgid, stime, etime);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * 验证是否使用初始化数据
	 * @param stime 起始时间
	 * @param etime 结束时间
	 * @return 是否使用初始化数据
	 */
	public static boolean validUseInit(String publishTime,String initTime,String stime, String etime) {
		if(publishTime == null || initTime == null){
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ptimeDate;
		Date itimeDate;
		Date stimeDate;
		Date etimeDate;
		try {
			ptimeDate = format.parse(publishTime);
			itimeDate = format.parse(initTime);
			stimeDate = format.parse(stime);
			etimeDate = format.parse(etime);
		} catch (ParseException e) {
			return false;
		}
		boolean isUseInit = false;
//		起始时间小于发布时间并且结束时间大于初始化时间
		if (stimeDate.before(ptimeDate)) {
			if (etimeDate.after(itimeDate)) {
				isUseInit = true;
			}
		}
		return isUseInit;
	}
	
	/**
	 * 获取计分统计的起始时间，起始时间为信息初始时间的后一天
	 * @param initTime 初始化时间
	 * @return 起始时间
	 */
	public static String getStime(){
		String initTime = Setting.getString("newsfile_init_time");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date itimeDate;
		try {
			itimeDate = format.parse(initTime);
		} catch (ParseException e) {
			System.out.println("日期转换异常");
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(itimeDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		return format.format(calendar.getTime());
	}

}
