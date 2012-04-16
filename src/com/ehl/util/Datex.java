package com.ehl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������չ����
 * @author xiayx
 *
 */
public class Datex {
	
	/**
	 * ����������֮�����������
	 * @param date1 ����1
	 * @param date2 ����2
	 * @return �������
	 */
	public static int dayDiffer(Date date1, Date date2) {
		int differ = 0;
		if (date1 != null && date2 != null) {
			long sm1 = date1.getTime();
			long sm2 = date2.getTime();
			differ = (int) ((sm1 - sm2) / 1000 / 60 / 60 / 24);
		}else{
			throw new RuntimeException("����Ϊnull");
		}
		return differ;
	}
	
	/**
	 * ����������֮�����������
	 * @param strDate1 ����1
	 * @param strDate2 ����2
	 * @param format ��ʽ
	 * @return �������
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
	 * ����������֮�����������
	 * @param strDate1 ����1
	 * @param strDate2 ����2
	 * @return ���������-1��ʾ�쳣
	 */
	public static int dayDiffer(String strDate1, String strDate2) {
		return dayDiffer(strDate1,strDate2,"yyyy-MM-dd");
	}
	
}
