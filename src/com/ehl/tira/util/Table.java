package com.ehl.tira.util;

import java.math.BigDecimal;

import com.ehl.tira.util.statanalysis.NumberUser;
import com.ehl.tira.util.statanalysis.Parameter;

/**
 * 处理HTML页面Table元素字符串
 * 
 * @author xiayx
 * @date 2010-12-13
 */
public class Table {

	/*
	 * 2010年车辆类型统计数据表格（主标题）
	 * 车辆类别\季度 第一季度 第二季度 第三季度 第四季度 总计（副标题） 
	 * 客车 1 2 3 4 10
	 * 轿车 1 2 3 4 10
	 * 总计 2 4 6 8 20
	 */

	/**
	 * 取得表格的TABLE元素的开始部分
	 * 
	 * @return
	 */
	public static String getTable() {
		return "<table width=\"100%\" border=\"0\" cellpadding=\"0\" "
				+ " onmouseover=\"changeto()\" onmouseout=\"changeback()\" cellspacing=\"0\" "
				+ " class=\"table\"  style=\"font-size:12px\">";
	}

	/**
	 * 取得数据表格标题
	 * 
	 * @param caption
	 *            标题内容
	 * @param num
	 *            所占列数
	 * @param isContainColTotal
	 *            是否包含一个总计列
	 * @return 数据表格标题HTML页面元素
	 */
	public static String getCaption(String caption, int num,
			boolean isContainColTotal) {
		num = isContainColTotal ? ++num : num;
		String captionTr = "<tr class='titleTopBack'>"
				+ "<td align='center' height='20' class='td_r_b' colspan='"
				+ num + "'>" + caption + "</td>" + "</tr>";
		return captionTr;
	}

	/**
	 * 取得数据表格副标题
	 * 
	 * @param subCaptions
	 *            副标题内容列表
	 * @param isContainColTotal
	 *            是否包含一个总计列
	 * @return 数据表格副标题HTML页面元素
	 */
	public static String getSubCaption(String[] subCaptions,
			boolean isContainColTotal) {
		if (subCaptions == null) {
			return null;
		}
		String subCaptionTr = "<tr>";
		for (int i = 0; i < subCaptions.length; i++) {
			subCaptionTr += "<td align='center' height='20' class='td_r_b' >"
					+ subCaptions[i] + "</td>";
		}
		if (isContainColTotal) {
			subCaptionTr += "<td align='center' height='20' class='td_r_b' >总计</td>";
		}
		subCaptionTr += "</tr>";

		return subCaptionTr;
	}

	/**
	 * 取得数据表格数据体
	 * 
	 * @param data
	 *            无标题数据格式二维数组
	 * @param isContainColTotal
	 *            是否包含一个总计列
	 * @return 数据表格数据体页面元素
	 */
	public static String getDataBody(Object[][] data, boolean isContainColTotal) {
		if (data == null) {
			return null;
		}
		float total = 0;
		String dataBody = "";
		for (int i = 0; i < data.length; i++) {
			dataBody += "<tr>";
			dataBody += "<td align='center' height='20' class='td_r_b' >"
					+ data[i][0] + "</td>";
			for (int j = 1; j < data[i].length; j++) {
				if (data[i][j] == null) {
					total += 0;
					data[i][j] = "未记录";
				} else {
					total += Float.parseFloat(data[i][j] + "");
				}
				dataBody += "<td align='center' height='20' class='td_r_b' >"
						+ data[i][j] + "</td>";
			}
			if (isContainColTotal) {
				dataBody += "<td align='center' height='20' class='td_r_b' >"
						+ total + "</td>";
			}
			dataBody += "</tr>";
		}
		return dataBody;
	}

	/**
	 * 取得一个总计行
	 * 
	 * @param data
	 *            无标题数据格式二维数组，经过行列转换
	 * @param isContainColTotal
	 *            是否包含一个总计列
	 * @return 总计行HTML页面元素
	 */
	public static String getRowTotal(Object[][] data, boolean isContainColTotal) {
		if (data == null) {
			return null;
		}
		float[] acounts = getRowTotalAcount(data);
		String row = "<tr><td align='center' height='20' class='td_r_b' >总计</td>";
		for (int i = 0; i < acounts.length - 1; i++) {
			row += "<td align='center' height='20' class='td_r_b' >"
					+ NumberUser.roundFloat(acounts[i],3) + "</td>";
		}
		if (isContainColTotal) {
			row += "<td align='center' height='20' class='td_r_b' >"
					+ NumberUser.roundFloat(acounts[acounts.length - 1],3)
					+ "</td>";
		}
		row += "</tr>";
		return row;
	}
	
	/**
	 * 取得一个总计行，包含一个总计列
	 * 
	 * @param data
	 *            无标题数据格式二维数组，经过行列转换
	 * @return 总计行的二维数组
	 */
	public static float[] getRowTotalAcount(Object[][] data) {
		if (data == null) {
			return null;
		}
		float[] rowTotals = new float[data.length];
		float rowTotal = 0;
		float total = 0;
		int i;
		for (i = 1; i < data.length; i++) {
			rowTotal = 0;
			for (int j = 0; j < data[i].length; j++) {
				if (data[i][j] == null) {
					rowTotal += 0;
				} else {
					rowTotal += Float.parseFloat(data[i][j].toString());
				}
			}
			total += rowTotal;
			rowTotals[i - 1] = rowTotal;
		}
		rowTotals[i - 1] = total;
		return rowTotals;
	}

	/**
	 * 取得Table的所有HTML字符串
	 * 
	 * @param caption
	 * @param subCaption
	 * @param data
	 * @param dataChange
	 * @param isContainColTotal
	 * @return
	 */
	public static String getTableData(String caption, String[] subCaption,
			Object[][] data, String[][] dataChange, boolean isContainColTotal) {
		if (data == null || dataChange == null) {
			return null;
		}
		if (data.length == 0) {
			return "";
		}

		int colNum = data[0].length;
		String table = getTable();
		table += getCaption(caption, colNum, isContainColTotal);
		table += getSubCaption(subCaption, isContainColTotal);
		table += getDataBody(data, isContainColTotal);
		if (data.length > 1) {
			table += getRowTotal(dataChange, isContainColTotal);
		}
		table += "</table>";
		return table;
	}
	
	/**
	 * 二维数组行列转换(changeRowCol)
	 * 
	 * @param data
	 *            二维数组
	 * @return 转换后的二维数组
	 */
	public static String[][] changeRC(Object[][] data) {
		
		String[][] datas;
		int rowNum = data.length;
		if (rowNum == 0) {
			return new String[][] {};
		}
		int colNum = data[0].length;
		datas = new String[colNum][rowNum];

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				datas[j][i] = (data[i][j] == null ? null : String
						.valueOf(data[i][j]));
			}
		}

		return datas;
	}

	public static void main(String[] args) {
		java.math.BigDecimal a = new BigDecimal(12);
		String b = String.valueOf(a);
		System.out.println(b);
	}

}
