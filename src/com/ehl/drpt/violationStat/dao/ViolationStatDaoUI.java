package com.ehl.drpt.violationStat.dao;

public interface ViolationStatDaoUI {
	/**
	 * @����: wuyl
	 * @�汾�ţ�1.0
	 * @����˵�����ܶ�ͳ�����س���Υ����Ϣ
	 * @������startTime ��ʼʱ�䣬  endTime ����ʱ�䣬 depart ����ID ��zhidui ֧��ID�� dadui ���ID
	 * @���أ�ͳ�ƽ����
	 * @�������ڣ�2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart,String zhidui,String dadui) throws Exception ;
	/**
	 * @����: wuyl
	 * @�汾�ţ�1.0
	 * @����˵����֧��ͳ�����س���Υ����Ϣ
	 * @������startTime ��ʼʱ�䣬  endTime ����ʱ�䣬 depart ����ID�� dadui ���ID
	 * @���أ�ͳ�ƽ����
	 * @�������ڣ�2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart,String dadui) throws Exception ;
	/**
	 * @����: wuyl
	 * @�汾�ţ�1.0
	 * @����˵�������ͳ�����س���Υ����Ϣ
	 * @������startTime ��ʼʱ�䣬  endTime ����ʱ�䣬 depart ����ID
	 * @���أ�ͳ�ƽ����
	 * @�������ڣ�2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart) throws Exception ;
}
