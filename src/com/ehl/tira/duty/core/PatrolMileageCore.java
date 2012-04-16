package com.ehl.tira.duty.core;

import org.apache.log4j.Logger;


import com.ehl.tira.Tira;
import com.ehl.tira.duty.PatrolUtil;
import com.ehl.tira.duty.dao.PatrolMileageDao;

/**
 * PatrolMileageCore(巡逻,里程)巡逻里程
 * 
 * @author xiayouxue
 * 
 */
public class PatrolMileageCore {

	private Logger logger = Logger.getLogger(PatrolMileageCore.class);
	private PatrolMileageDao pmDao = new PatrolMileageDao();

	private String getLocation(String location) {
		return Tira.root + "->" + Tira.duty + "->" + location + ":" + Tira.core
				+ "，";
	}
	
	//总巡逻里程，按机构分类
	public String totalDept(String area, String dateS, String dateE) {

		String location = getLocation(Tira.dutyPotralTotal) + "获取"
				+ Tira.dutyPotralTotal + "按机构分类报表XML数据，" + Tira.error + "。\n";
		
		String chartXML = null;
		Object[][] data = pmDao.totalDept(area, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "高速大队", "管辖里程", "巡逻里程", "巡逻车辆" };
			dataStr = PatrolUtil.changeData(dataStr, titles);
			if(dataStr.length == 2){
				chartType = "columnSingle";
				chartXML = PatrolUtil.columnSingleCol(dataStr, title);
			}else{
				chartType = "columnMulti";
				chartXML = PatrolUtil.columnMultipleCol(dataStr, title);
			}
			
			if (chartXML == null) {
				isSuccess = false;
				logger.error(location + "详细信息:未知异常！");
			}
			
		} else {
			isSuccess = false;
			logger.error(location + "详细信息:dao层传递数据格式不符！");
		}

		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		
		return chartXML;
	}
	
	//总巡逻里程，按日期分类
	public String totalTime(String dept, String dateS, String dateE) {
		String location = getLocation(Tira.dutyPotralTotal) + "获取"
				+ Tira.dutyPotralTotal + "按时间分类报表XML数据，" + Tira.error + "。\n";
		String chartXML = null;
		Object[][] data = pmDao.totalTime(dept, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "日期", "管辖里程", "巡逻里程", "巡逻车辆" };
			dataStr = PatrolUtil.changeData(dataStr, titles);
			if(dataStr.length == 2){
				chartType = "columnSingle";
				chartXML = PatrolUtil.columnSingleCol(dataStr, title);
			}else{
				chartType = "columnMulti";
				chartXML = PatrolUtil.columnMultipleCol(dataStr, title);
			}
		} else {
			isSuccess = false;
			logger.error(location + "详细信息:dao层传递数据格式不符！");
		}

		if (chartXML == null) {
			isSuccess = false;
			logger.error(location + "详细信息:未知异常！");
		}
		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		return chartXML;
	}
	
	//平均巡逻里程，按机构分类
	public String avgDept(String area, String dateS, String dateE) {
		String location = getLocation(Tira.dutyPotralTotal) + "获取"
				+ Tira.dutyPotralAvg + "按机构分类报表XML数据，" + Tira.error + "。\n";
		String chartXML = null;
		Object[][] data = pmDao.avgDept(area, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "高速大队", "管辖里程", "平均巡逻里程" };
			dataStr = PatrolUtil.changeData(dataStr, titles);
			if(dataStr.length == 2){
				chartType = "columnSingle";
				chartXML = PatrolUtil.columnSingleCol(dataStr, title);
			}else{
				chartType = "columnMulti";
				chartXML = PatrolUtil.columnMultipleCol(dataStr, title);
			}
		} else {
			isSuccess = false;
			logger.error(location + "详细信息:dao层传递数据格式不符！");
		}

		if (chartXML == null) {
			isSuccess = false;
			logger.error(location + "详细信息:未知异常！");
		}
		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		return chartXML;
	}
	
	//平均巡逻里程，按日期分类
	public String avgTime(String dept, String dateS, String dateE) {
		String location = getLocation(Tira.dutyPotralTotal) + "获取"
				+ Tira.dutyPotralAvg + "按日期分类报表XML数据，" + Tira.error + "。\n";
		String chartXML = null;
		Object[][] data = pmDao.avgTime(dept, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "日期", "管辖里程", "平均巡逻里程" };
			dataStr = PatrolUtil.changeData(dataStr, titles);
			if(dataStr.length == 2){
				chartType = "columnSingle";
				chartXML = PatrolUtil.columnSingleCol(dataStr, title);
			}else{
				chartType = "columnMulti";
				chartXML = PatrolUtil.columnMultipleCol(dataStr, title);
			}
		} else {
			isSuccess = false;
			logger.error(location + "详细信息:dao层传递数据格式不符！");
		}

		if (chartXML == null) {
			isSuccess = false;
			logger.error(location + "详细信息:未知异常！");
		}
		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		return chartXML;
	}

	/**
	 * createPatrolTotalMileageChartXML<br>
	 * 创建巡逻里程报表XML数据
	 * 
	 * @return
	 */
	public String createPMCX(String area, String dept, String dateS,
			String dateE, boolean isAvg) {
		String chartXML = null;
		Object[][] data = pmDao.getPatrolMileageData(area, dept, dateS, dateE,
				isAvg);
		String[][] datas = PatrolUtil.changeData(data);
		if (datas != null) {
			String title = "";
			String[] titles;
			if (isAvg) {
				titles = new String[] { "高速大队", "管辖里程", "平均巡逻里程" };
				if ("".equals(dept)) {
					titles = new String[] { "时间", "管辖里程", "平均巡逻里程" };
				}
			} else {
				titles = new String[] { "高速大队", "管辖里程", "巡逻里程", "巡逻车辆" };
				if ("".equals(dept)) {
					titles = new String[] { "时间", "管辖里程", "巡逻里程", "巡逻车辆" };
				}
			}
			datas = PatrolUtil.changeData(datas, titles);
			chartXML = PatrolUtil.columnMultipleCol(datas, title);
		} else {
			logger.error("分析研判->各辖区高速公路分析->巡逻" + (isAvg ? "总" : "平均")
					+ "里程分析:core层，获取巡逻" + (isAvg ? "总" : "平均")
					+ "报表XML数据，dao层传递数据格式不符！");
		}

		if (chartXML == null) {
			logger.error("分析研判->各辖区高速公路分析->巡逻" + (isAvg ? "总" : "平均")
					+ "里程分析:core层，获取巡逻" + (isAvg ? "总" : "平均")
					+ "报表XML数据，未知异常！");
		}

		return chartXML;
	}

}
