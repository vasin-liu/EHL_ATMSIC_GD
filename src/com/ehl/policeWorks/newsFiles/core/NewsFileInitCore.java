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
	 * ��ȡ�����õ���Ϣ�ļ�ƪ��������ǰ2011-01-01~2011-08-10��
	 * @param jgid �������
	 * @return ��Ϣ�ļ�ƪ��
	 */
	public static int[][] getCounts(String jgid){
		Object[][] data = NewsFileInitDao.getCounts(jgid);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * ��ȡ�����õ���Ϣ�ļ�ƪ��������ʱ�����2011-08-11~2011-09-01��
	 * @param jgid �������
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @return ��Ϣ�ļ�ƪ��
	 */
	public static int[][] getCounts(String jgid, String stime, String etime){
		Object[][] data = NewsFileInitDao.getCounts(jgid, stime, etime);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * ��ȡ�����õ���Ϣ�ļ�ƪ������
	 * @param jgid �������
	 * @return ��Ϣ�ļ�ƪ��
	 */
	public static int[][] getTotalCounts(String jgid,String stime, String etime){
		Object[][] data = NewsFileInitDao.getTotalCounts(jgid, stime, etime);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * ��֤�Ƿ�ʹ�ó�ʼ������
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @return �Ƿ�ʹ�ó�ʼ������
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
//		��ʼʱ��С�ڷ���ʱ�䲢�ҽ���ʱ����ڳ�ʼ��ʱ��
		if (stimeDate.before(ptimeDate)) {
			if (etimeDate.after(itimeDate)) {
				isUseInit = true;
			}
		}
		return isUseInit;
	}
	
	/**
	 * ��ȡ�Ʒ�ͳ�Ƶ���ʼʱ�䣬��ʼʱ��Ϊ��Ϣ��ʼʱ��ĺ�һ��
	 * @param initTime ��ʼ��ʱ��
	 * @return ��ʼʱ��
	 */
	public static String getStime(){
		String initTime = Setting.getString("newsfile_init_time");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date itimeDate;
		try {
			itimeDate = format.parse(initTime);
		} catch (ParseException e) {
			System.out.println("����ת���쳣");
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(itimeDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		return format.format(calendar.getTime());
	}

}
