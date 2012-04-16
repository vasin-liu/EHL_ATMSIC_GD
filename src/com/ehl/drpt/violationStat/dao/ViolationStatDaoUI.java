package com.ehl.drpt.violationStat.dao;

public interface ViolationStatDaoUI {
	/**
	 * @作者: wuyl
	 * @版本号：1.0
	 * @函数说明：总队统计严重车辆违法信息
	 * @参数：startTime 开始时间，  endTime 结束时间， depart 机构ID ，zhidui 支队ID， dadui 大队ID
	 * @返回：统计结果集
	 * @创建日期：2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart,String zhidui,String dadui) throws Exception ;
	/**
	 * @作者: wuyl
	 * @版本号：1.0
	 * @函数说明：支队统计严重车辆违法信息
	 * @参数：startTime 开始时间，  endTime 结束时间， depart 机构ID， dadui 大队ID
	 * @返回：统计结果集
	 * @创建日期：2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart,String dadui) throws Exception ;
	/**
	 * @作者: wuyl
	 * @版本号：1.0
	 * @函数说明：大队统计严重车辆违法信息
	 * @参数：startTime 开始时间，  endTime 结束时间， depart 机构ID
	 * @返回：统计结果集
	 * @创建日期：2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart) throws Exception ;
}
