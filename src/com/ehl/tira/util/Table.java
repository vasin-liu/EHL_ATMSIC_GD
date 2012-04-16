package com.ehl.tira.util;

import java.math.BigDecimal;

import com.ehl.tira.util.statanalysis.NumberUser;
import com.ehl.tira.util.statanalysis.Parameter;

/**
 * ����HTMLҳ��TableԪ���ַ���
 * 
 * @author xiayx
 * @date 2010-12-13
 */
public class Table {

	/*
	 * 2010�공������ͳ�����ݱ�������⣩
	 * �������\���� ��һ���� �ڶ����� �������� ���ļ��� �ܼƣ������⣩ 
	 * �ͳ� 1 2 3 4 10
	 * �γ� 1 2 3 4 10
	 * �ܼ� 2 4 6 8 20
	 */

	/**
	 * ȡ�ñ���TABLEԪ�صĿ�ʼ����
	 * 
	 * @return
	 */
	public static String getTable() {
		return "<table width=\"100%\" border=\"0\" cellpadding=\"0\" "
				+ " onmouseover=\"changeto()\" onmouseout=\"changeback()\" cellspacing=\"0\" "
				+ " class=\"table\"  style=\"font-size:12px\">";
	}

	/**
	 * ȡ�����ݱ�����
	 * 
	 * @param caption
	 *            ��������
	 * @param num
	 *            ��ռ����
	 * @param isContainColTotal
	 *            �Ƿ����һ���ܼ���
	 * @return ���ݱ�����HTMLҳ��Ԫ��
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
	 * ȡ�����ݱ�񸱱���
	 * 
	 * @param subCaptions
	 *            �����������б�
	 * @param isContainColTotal
	 *            �Ƿ����һ���ܼ���
	 * @return ���ݱ�񸱱���HTMLҳ��Ԫ��
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
			subCaptionTr += "<td align='center' height='20' class='td_r_b' >�ܼ�</td>";
		}
		subCaptionTr += "</tr>";

		return subCaptionTr;
	}

	/**
	 * ȡ�����ݱ��������
	 * 
	 * @param data
	 *            �ޱ������ݸ�ʽ��ά����
	 * @param isContainColTotal
	 *            �Ƿ����һ���ܼ���
	 * @return ���ݱ��������ҳ��Ԫ��
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
					data[i][j] = "δ��¼";
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
	 * ȡ��һ���ܼ���
	 * 
	 * @param data
	 *            �ޱ������ݸ�ʽ��ά���飬��������ת��
	 * @param isContainColTotal
	 *            �Ƿ����һ���ܼ���
	 * @return �ܼ���HTMLҳ��Ԫ��
	 */
	public static String getRowTotal(Object[][] data, boolean isContainColTotal) {
		if (data == null) {
			return null;
		}
		float[] acounts = getRowTotalAcount(data);
		String row = "<tr><td align='center' height='20' class='td_r_b' >�ܼ�</td>";
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
	 * ȡ��һ���ܼ��У�����һ���ܼ���
	 * 
	 * @param data
	 *            �ޱ������ݸ�ʽ��ά���飬��������ת��
	 * @return �ܼ��еĶ�ά����
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
	 * ȡ��Table������HTML�ַ���
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
	 * ��ά��������ת��(changeRowCol)
	 * 
	 * @param data
	 *            ��ά����
	 * @return ת����Ķ�ά����
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
