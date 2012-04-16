package com.ehl.policeWorks.newsFiles.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.appframe.common.Setting;
import com.ehl.policeWorks.newsFiles.dao.NewsFileDao;
import com.ehl.tira.util.XML;
import com.ehl.util.Array;

public class NewsFileCore {

	public static Logger logger = Logger.getLogger(NewsFileCore.class);

	/**
	 * 获取信息文件被采用篇数（全部为0的空结构）
	 * 
	 * @return 信息文件被采用篇数
	 */
	public static int[][] getCounts() {
		return new int[NewsFileDao.types.length][NewsFileDao.sbtypes.length];
	}

	/**
	 * 获取指定单位在一定时间范围内被采用的信息文件篇数（本系统中录入的数据）
	 * 
	 * @param jgid
	 *            机构编号
	 * @param stime
	 *            起始时间
	 * @param etime
	 *            结束时间
	 * @return 信息文件篇数
	 */
	public static int[][] getSelfSystemCounts(String jgid, String stime,
			String etime) {
		Object[][] data = NewsFileDao.getCounts(jgid, stime, etime);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * 获取指定单位在一定时间范围内被采用的信息文件篇数
	 * 
	 * @param jgid
	 *            机构编号
	 * @param stime
	 *            起始时间
	 * @param etime
	 *            结束时间
	 * @return 信息文件篇数
	 */
	public static int[][] getCounts(String jgid, String stime, String etime) {
		String publishTime = Setting.getString("publish_time");
		String useTime = Setting.getString("newsfile_use_time");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ptimeDate;
		Date utimeDate;
		Date stimeDate;
		Date etimeDate;
		try {
			ptimeDate = format.parse(publishTime);
			utimeDate = format.parse(useTime);
			stimeDate = format.parse(stime);
			etimeDate = format.parse(etime);
		} catch (ParseException e) {
			System.out.println("获取指定单位在一定时间范围内被采用的信息文件篇数:日期转换异常！");
			return getCounts();
		}
		int[][] counts = null;
		//起始时间大于初始化截止日期
		if (stimeDate.after(utimeDate)) {
			counts = NewsFileCore.getSelfSystemCounts(jgid, stime, etime);
		} else {
			if (stimeDate.before(ptimeDate)) {
				stimeDate = ptimeDate;
				counts = NewsFileInitCore.getTotalCounts(jgid, stime, etime);
			} else {
				counts = NewsFileInitCore.getCounts(jgid, stime, etime);
			}
			if (!etimeDate.before(utimeDate)) {
//				stimeDate = utimeDate;
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(stimeDate);
//				calendar.add(Calendar.DAY_OF_MONTH, 1);
//				stimeDate = calendar.getTime();
				int[][] selfCounts = NewsFileCore.getSelfSystemCounts(jgid,
						format.format(utimeDate), etime);
				for (int i = 0; i < counts.length; i++) {
					for (int j = 0; j < counts[i].length; j++) {
						counts[i][j] += selfCounts[i][j];
					}
				}
			}
		}
		return counts;
	}
	
    /**
     * 获取信息文件中被采用的属于春运的统计数据
     * 
     * @param stime
     *            起始日期
     * @param etime
     *            结束日期
     * @return xml格式
     */
    public static String springStatis(String stime, String etime) {
	Object[][] objects = NewsFileDao.springStatis(stime, etime);
	String xml = XML.getNodes("spring", new String[] {"jgid","jgmc","count"}, objects);
	xml = XML.getXML(xml);
	return xml;
    }

}
