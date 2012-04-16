package com.ehl.tira.util;

import org.apache.log4j.Logger;

/**
 * 辅助处理报表数据XML格式字符串
 * 
 * @author Owner
 * @date 2010-12-15
 */
public class Chart {
	/*
	 * public static String caption = "主标题"; public static String subCaption =
	 * "副标题"; public static String width = "宽度"; public static String height =
	 * "高度"; public final static String chartBgColor = "图表背景颜色"; public final
	 * static String canvasBgColor = "画布背景颜色"; public final static String
	 * baseFont = "字体样式"; public final static String baseFontSize = "字体大小";
	 * public final static String baseFontColor = "字体颜色"; public final static
	 * String outCnvBaseFont = "画布外字体样式"; public final static String
	 * outCnvBaseFontSize = "画布外字体大小"; public final static String
	 * outCnvBaseFontColor = "画布外字体颜色"; public final static String xAxisName =
	 * "x轴名称"; public final static String yAxisName = "y轴名称"; public final
	 * static String decimalPrecision = "精确度"; public final static String
	 * formatNumberScale = "是否格式化"; public static String numberPrefix = "数字前缀";
	 * public static String numberSuffix = "数字后缀"; public static String
	 * showNames = "是否显示标签"; public static String showValues = "是否显示数据值";
	 */
	private static Logger logger = Logger.getLogger(Chart.class);

	public final static String singleBarChart = "singleBarChart";
	public final static String multipleBarChart = "multipleBarChart";
	public final static String pieChart = "pieChart";

	/**
	 * 取得柱状图<chart>标签字符串
	 * 
	 * @param caption
	 *            标题
	 * @param is
	 *            是否纵向显示标签
	 * @return
	 */
	public static String getBarChart(String caption, int isRotate) {
		return "<chart caption='"
				+ caption
				+ "' baseFont='宋体' baseFontSize='12' rotateNames='"
				+ isRotate
				+ "'"
				+ " showValues='0' showNames='1' decimalPrecision='0' formatNumberScale='0'>";
	}

	/**
	 * 取得饼状图chart标签字符串
	 * 
	 * @param caption
	 *            标题
	 * @return
	 */
	public static String getPieChart(String caption) {
		return "<chart caption='" + caption
				+ "' baseFont='宋体' baseFontSize='12' "
				+ " showValues='1' decimalPrecision='0' formatNumberScale='0'>";
	}

	/**
	 * 取得<set>标签字符串，用于单项柱状图
	 * 
	 * @param data
	 *            单行多列
	 * @param colDescs
	 *            列描述数组
	 * @return
	 */
	public static String getSets(Object[] data, String[] colDescs) {
		if (data == null || colDescs == null) {
			logger.error("报表获取set标签，参数为null");
			return null;
		}
		if (data.length != colDescs.length) {
			logger.error("报表获取set标签，数据数组和列描述数组长度不一致");
			return "";
		}
		String sets = "";
		for (int i = 1; i < data.length; i++) {
			sets += "<set label='" + colDescs[i] + "' value='" + data[i]
					+ "' />";
		}
		return sets;
	}

	/**
	 * 取得<set>标签字符串，用于饼状图
	 * 
	 * @param data
	 *            多行两列
	 * @return
	 */
	public static String getSets(Object[][] data) {
		if (data == null || data.length == 0) {
			logger.error("报表获取set标签，参数为null或者无数据");
			return null;
		}

		if (data[0].length != 2) {
			logger.error("报表获取set标签，数据数组长度不符");
			return "";
		}
		String sets = "";
		for (int i = 0; i < data.length; i++) {
			sets += "<set label='" + data[i][0] + "' value='" + data[i][1]
					+ "' />";
		}

		return sets;
	}

	/**
	 * 取得数据<categories>标签字符串
	 * 
	 * @param rowDescs
	 *            行描述数组
	 * @return
	 */
	public static String getCategories(Object[] rowDescs) {
		if (rowDescs == null) {
			return null;
		}

		String categories = "<categories>";
		for (int i = 0; i < rowDescs.length; i++) {
			categories += "<category label='" + rowDescs[i] + "' />";
		}
		categories += "</categories>";
		return categories;
	}

	/**
	 * 取得数据<datasets>标签字符串
	 * 
	 * @param data
	 *            数据库数据，经过行列转换
	 * @param colDescs
	 *            列描述数组
	 * @return
	 */
	public static String getDatasets(Object[][] data, String[] colDescs) {
		if (data == null || colDescs == null) {
			return null;
		}

		if (data.length != colDescs.length) {
			return "";
		}

		String datasets = "";
		for (int i = 1; i < data.length; i++) {
			datasets += "<dataset seriesName='" + colDescs[i] + "'>";
			for (int j = 0; j < data[i].length; j++) {
				datasets += "<set value='" + (data[i][j] == null ? 0
						: data[i][j]) + "' />";
			}
			datasets += "</dataset>";
		}
		return datasets;
	}
	
	/**
	 * 取得单系柱状图数据XML格式字符串
	 * 
	 * @param caption
	 *            标题
	 * @param data
	 *            数据
	 * @param colDescs
	 *            列描述数组
	 * @return
	 */
	public static String getSingleBarChartData(String caption, Object[] data,
			String[] colDescs) {

		String chartData = getBarChart(caption, 0);
		chartData += getSets(data, colDescs);
		chartData += "</chart>";
		chartData = XML.encapContent(singleBarChart, chartData);
		return chartData;
	}

	/**
	 * 取得饼状图数据XML格式字符串
	 * 
	 * @param caption
	 *            标题
	 * @param data
	 *            数据
	 * @return
	 */
	public static String getPieChartData(String caption, Object[][] data) {

		String chartData = getPieChart(caption);
		chartData += getSets(data);
		chartData += "</chart>";
		chartData = XML.encapContent(pieChart, chartData);
		return chartData;
	}

	/**
	 * 取得多系柱状图数据XML格式字符串
	 * 
	 * @param caption
	 *            标题
	 * @param data
	 *            数据，经过行列转换
	 * @param colDescs
	 *            列描述数组
	 * @return
	 */
	public static String getMultiBarChartData(String caption, Object[][] data,
			String[] colDescs) {

		String chartData = getBarChart(caption, 0);
		chartData += getCategories(data[0]);
		chartData += getDatasets(data, colDescs);
		chartData += "</chart>";
		chartData = XML.encapContent(multipleBarChart, chartData);
		return chartData;
	}

	public static void main(String[] args) {

	}

}
