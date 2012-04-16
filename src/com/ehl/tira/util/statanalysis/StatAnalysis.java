package com.ehl.tira.util.statanalysis;

import java.util.HashMap;
import java.util.Map;

/**
 * 辅助统计分析数据处理
 * @author xiayx
 * @date 2010-12-21
 */
public class StatAnalysis {
	
	/*
	 * 1.初始化数据表格架构
	 * 	1.1.表格架构包含表头
	 * 	1.2.表格架构不包含表头
	 * 	1.3.初始化内容设置为0
	 * 	1.4.初始化内容设置为未记录
	 * 2.填充数据表格内容
	 * 	
	 */

	/**
	 * 初始化数据表格
	 * 
	 * @param statItemValues
	 *            统计项值列表
	 * @param floatItemValues
	 *            浮动项值列表
	 * @return 初始化数据表格
	 */
	public static String[][] initDataTable(Object[][] statItemValues,
			String[][] floatItemValues) {
		String[][] table = new String[statItemValues.length + 1][floatItemValues.length + 2];
		for (int i = 0; i < statItemValues.length; i++) {
			table[i + 1][0] = statItemValues[i][0].toString();
			table[i + 1][1] = statItemValues[i][1].toString();
			for (int j = 0; j < floatItemValues.length; j++) {
				table[i + 1][j + 2] = "0";
			}
		}
		for (int i = 0; i < floatItemValues.length; i++) {
			table[0][i + 2] = floatItemValues[i][1];
		}

		return table;
	}

	/**
	 * 初始化数据表格
	 * 
	 * @param statItemValues
	 *            统计项值列表
	 * @param floatItemValues
	 *            浮动项值列表
	 * @return 初始化数据表格
	 */
	public static String[][] initDataTable(Object[][] statItemValues,
			String[] floatItemValues) {
		if (statItemValues == null || floatItemValues == null) {
			return null;
		}
		String[][] table = new String[statItemValues.length + 1][floatItemValues.length + 1];
		for (int i = 0; i < statItemValues.length; i++) {
			table[i + 1][0] = statItemValues[i][0] + "";
			table[i + 1][1] = statItemValues[i][1] + "";
			for (int j = 0; j < floatItemValues.length; j++) {
				table[i + 1][j + 2] = "0";
			}
		}
		for (int i = 0; i < floatItemValues.length; i++) {
			table[0][i + 1] = floatItemValues[i];
		}

		return table;
	}

	/**
	 * 填充数据表格
	 * 
	 * @param dataTable
	 * @param data
	 * @param adj
	 * @return
	 */
	public final static String[][] fillDataTable(String[][] dataTable,
			Object[][] data, Map<String, Integer> map) {
		if (dataTable == null || data == null || map == null) {
			return null;
		}
		for (int i = 0; i < data.length; i++) {
			String statItemValue = data[i][0] + "";
			String floatItemValue = data[i][1] + "";
			String dataItemValue = data[i][2] + "";
			for (int j = 1; j < dataTable.length; j++) {
				if (statItemValue.equals(dataTable[j][0])) {
					dataTable[j][map.get(floatItemValue)] = dataItemValue;
					break;
				}
			}
		}
		return dataTable;
	}
	
	public static String[][] fillDataTable (String[][] dataTable,
			Object[][] data) {
		for (int i = 0; i < data.length; i++) {
			String statItemValue = data[i][0] + "";
			for (int j = 1; j < dataTable.length; j++) {
				if (statItemValue.equals(dataTable[j][0])) {
					for (int k = 1; k < data[i].length; k++) {
						dataTable[j][k+1] = data[i][k] + "";
					}
					break;
				}
			}
		}
		return null;
	}

	/**
	 * 组织项的值列表
	 * 
	 * @param itemValues
	 * @return
	 */
	public static Map<String, Integer> organizeItemValues(Object[][] itemValues) {
		if (itemValues == null) {
			return null;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < itemValues.length; i++) {
			map.put(itemValues[i][0] + "", i + 2);
		}
		return map;
	}

	/**
	 * 将二维数组转换成Map
	 * 
	 * @param itemValues
	 * @return
	 */
	public static Map<String, String> changeArrayMap(Object[][] itemValues) {
		if (itemValues == null || itemValues.length == 0
				|| itemValues[0].length != 2) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < itemValues.length; i++) {
			map.put(itemValues[i][0] + "", itemValues[i][1] + "");
		}
		return map;
	}

}
