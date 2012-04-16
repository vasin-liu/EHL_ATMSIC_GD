package com.ehl.drpt.violationStat.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.drpt.common.CommonXML;

/**
 * @======================================================================================================================================
 * @类型说明:统计车辆严重违法信息
 * @创建者：吴玉良
 * @创建日期 2010-1-13 16:23
 * @======================================================================================================================================
 */
public class ViolationStatDaoImpl implements ViolationStatDaoUI{
	Logger logger = Logger.getLogger(ViolationStatDaoImpl.class);	

	/**
	 * @作者: wuyl
	 * @版本号：1.0
	 * @函数说明：总队统计车辆严重违法信息
	 * @参数：startTime 开始时间，  endTime 结束时间， depart 机构ID ，zhidui 支队ID， dadui 大队ID
	 * @返回：统计结果集
	 * @创建日期：2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart,String zhidui,String dadui) throws Exception {

		if("".equals(startTime) || "".equals(endTime) || "".equals(depart)){
			logger.error("[统计车辆违法]"+ "总队统计车辆严重违法信息" + "startTime || endTime || depart错误");
			return null;
		}

		String sql = "";
		sql = "select riqi,b.jgmc,wfzl1,count1 from ";
		sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
		sql += " where 1=1 ";
		if(!"".equals(zhidui)){
			sql += " and substr(bh,1,4)="+zhidui.substring(0, 4);
		}
		if(!"".equals(dadui)){
			sql += " and substr(bh,5,2)="+dadui.substring(4, 6);
		}
		sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
		sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
		sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b";
		sql += " where a.bhid =substr(b.jgid, 1, 6)";
		Object[][] rsObj=null;
		try{
			rsObj = DBHandler.getMultiResult(sql);
		}catch(Exception exp){
			logger.error("[统计车辆违法]" + "总队统计车辆严重违法信息时的SQL错误： \n"+sql);
			exp.printStackTrace();
			return null;
		}
		if(rsObj==null || rsObj.length<1){
			return null;
		}else{
			return CommonXML.getXML("success");
		}
	}
	/**
	 * @作者: wuyl
	 * @版本号：1.0
	 * @函数说明：支队统计车辆严重违法信息
	 * @参数：startTime 开始时间，  endTime 结束时间， depart 机构ID， dadui 大队ID
	 * @返回：统计结果集
	 * @创建日期：2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart,String dadui) throws Exception {

		if("".equals(startTime) || "".equals(endTime) || "".equals(depart)){
			logger.error("[统计车辆违法]"+ "支队统计车辆严重违法信息" + "startTime || endTime || depart错误");
			return null;
		}

		String sql = "";
		sql = "select riqi,b.jgmc,wfzl1,count1 from ";
		sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
		sql += " where 1=1 ";
		sql += " and substr(bh,1,4)="+depart.substring(0, 4);
		if(!"".equals(dadui)){
			sql += " and substr(bh,5,2)="+dadui.substring(4, 6);
		}
		sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
		sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
		sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b";
		sql += " where a.bhid =substr(b.jgid, 1, 6)";
		Object[][] rsObj=null;
		try{
			rsObj = DBHandler.getMultiResult(sql);
		}catch(Exception exp){
			logger.error("[统计车辆违法]" + "支队统计车辆严重违法信息时的SQL错误： \n"+sql);
			exp.printStackTrace();
			return null;
		}
		if(rsObj==null || rsObj.length<1){
			return null;
		}else{
			return CommonXML.getXML("success");
		}
	}
	/**
	 * @作者: wuyl
	 * @版本号：1.0
	 * @函数说明：大队统计车辆严重违法信息
	 * @参数：startTime 开始时间  endTime 结束时间 depart 机构ID
	 * @返回：统计结果集
	 * @创建日期：2010-1-13 16:21
	 */
	public String getStatistics(String startTime,String endTime,String depart) throws Exception {

		if("".equals(startTime) || "".equals(endTime) || "".equals(depart)){
			logger.error("[统计车辆违法]"+ "大队统计车辆严重违法信息" + "startTime || endTime || depart错误");
			return null;
		}

		String sql = "";
		sql = "select riqi,b.jgmc,wfzl1,count1 from ";
		sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
		sql += " where 1=1 ";
		sql += " and substr(bh,1,6)="+depart.substring(0, 6);
		sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
		sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
		sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b";
		sql += " where a.bhid =substr(b.jgid, 1, 6)";
		Object[][] rsObj=null;
		try{
			rsObj = DBHandler.getMultiResult(sql);
		}catch(Exception exp){
			logger.error("[统计车辆违法]" + "大队统计车辆严重违法信息时的SQL错误： \n"+sql);
			exp.printStackTrace();
			return null;
		}
		if(rsObj==null || rsObj.length<1){
			return null;
		}else{
			return CommonXML.getXML("success");
		}
	}
}
