package com.ehl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期扩展方法
 * @author xiayx
 *
 */
public class Datex {
	
	/**
	 * 求两个日期之间相隔多少天
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 相隔天数
	 */
	public static int dayDiffer(Date date1, Date date2) {
		int differ = 0;
		if (date1 != null && date2 != null) {
			long sm1 = date1.getTime();
			long sm2 = date2.getTime();
			differ = (int) ((sm1 - sm2) / 1000 / 60 / 60 / 24);
		}else{
			throw new RuntimeException("日期为null");
		}
		return differ;
	}
	
	/**
	 * 求两个日期之间相隔多少天
	 * @param strDate1 日期1
	 * @param strDate2 日期2
	 * @param format 格式
	 * @return 相隔天数
	 */
	public static int dayDiffer(String strDate1, String strDate2, String format) {
		int differ = 0;
		if (strDate1 != null && strDate2 != null && format != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date date1 = null;
			Date date2 = null;
			try {
				date1 = dateFormat.parse(strDate1);
				date2 = dateFormat.parse(strDate2);
			} catch (ParseException e) {
				e.printStackTrace();
				return differ;
			}
			differ = dayDiffer(date1, date2);
		}
		return differ;
	}
	
	/**
	 * 求两个日期之间相隔多少天
	 * @param strDate1 日期1
	 * @param strDate2 日期2
	 * @return 相隔天数，-1表示异常
	 */
	public static int dayDiffer(String strDate1, String strDate2) {
		return dayDiffer(strDate1,strDate2,"yyyy-MM-dd");
	}
	
}
