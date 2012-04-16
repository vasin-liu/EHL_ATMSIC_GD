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
	 * ��ȡ��Ϣ�ļ�������ƪ����ȫ��Ϊ0�Ŀսṹ��
	 * 
	 * @return ��Ϣ�ļ�������ƪ��
	 */
	public static int[][] getCounts() {
		return new int[NewsFileDao.types.length][NewsFileDao.sbtypes.length];
	}

	/**
	 * ��ȡָ����λ��һ��ʱ�䷶Χ�ڱ����õ���Ϣ�ļ�ƪ������ϵͳ��¼������ݣ�
	 * 
	 * @param jgid
	 *            �������
	 * @param stime
	 *            ��ʼʱ��
	 * @param etime
	 *            ����ʱ��
	 * @return ��Ϣ�ļ�ƪ��
	 */
	public static int[][] getSelfSystemCounts(String jgid, String stime,
			String etime) {
		Object[][] data = NewsFileDao.getCounts(jgid, stime, etime);
		return Array.getCounts(data, NewsFileDao.types, NewsFileDao.sbtypes);
	}
	
	/**
	 * ��ȡָ����λ��һ��ʱ�䷶Χ�ڱ����õ���Ϣ�ļ�ƪ��
	 * 
	 * @param jgid
	 *            �������
	 * @param stime
	 *            ��ʼʱ��
	 * @param etime
	 *            ����ʱ��
	 * @return ��Ϣ�ļ�ƪ��
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
			System.out.println("��ȡָ����λ��һ��ʱ�䷶Χ�ڱ����õ���Ϣ�ļ�ƪ��:����ת���쳣��");
			return getCounts();
		}
		int[][] counts = null;
		//��ʼʱ����ڳ�ʼ����ֹ����
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
     * ��ȡ��Ϣ�ļ��б����õ����ڴ��˵�ͳ������
     * 
     * @param stime
     *            ��ʼ����
     * @param etime
     *            ��������
     * @return xml��ʽ
     */
    public static String springStatis(String stime, String etime) {
	Object[][] objects = NewsFileDao.springStatis(stime, etime);
	String xml = XML.getNodes("spring", new String[] {"jgid","jgmc","count"}, objects);
	xml = XML.getXML(xml);
	return xml;
    }

}
