package com.ehl.tira.util;

import org.apache.log4j.Logger;

/**
 * ��������������XML��ʽ�ַ���
 * 
 * @author Owner
 * @date 2010-12-15
 */
public class Chart {
	/*
	 * public static String caption = "������"; public static String subCaption =
	 * "������"; public static String width = "���"; public static String height =
	 * "�߶�"; public final static String chartBgColor = "ͼ������ɫ"; public final
	 * static String canvasBgColor = "����������ɫ"; public final static String
	 * baseFont = "������ʽ"; public final static String baseFontSize = "�����С";
	 * public final static String baseFontColor = "������ɫ"; public final static
	 * String outCnvBaseFont = "������������ʽ"; public final static String
	 * outCnvBaseFontSize = "�����������С"; public final static String
	 * outCnvBaseFontColor = "������������ɫ"; public final static String xAxisName =
	 * "x������"; public final static String yAxisName = "y������"; public final
	 * static String decimalPrecision = "��ȷ��"; public final static String
	 * formatNumberScale = "�Ƿ��ʽ��"; public static String numberPrefix = "����ǰ׺";
	 * public static String numberSuffix = "���ֺ�׺"; public static String
	 * showNames = "�Ƿ���ʾ��ǩ"; public static String showValues = "�Ƿ���ʾ����ֵ";
	 */
	private static Logger logger = Logger.getLogger(Chart.class);

	public final static String singleBarChart = "singleBarChart";
	public final static String multipleBarChart = "multipleBarChart";
	public final static String pieChart = "pieChart";

	/**
	 * ȡ����״ͼ<chart>��ǩ�ַ���
	 * 
	 * @param caption
	 *            ����
	 * @param is
	 *            �Ƿ�������ʾ��ǩ
	 * @return
	 */
	public static String getBarChart(String caption, int isRotate) {
		return "<chart caption='"
				+ caption
				+ "' baseFont='����' baseFontSize='12' rotateNames='"
				+ isRotate
				+ "'"
				+ " showValues='0' showNames='1' decimalPrecision='0' formatNumberScale='0'>";
	}

	/**
	 * ȡ�ñ�״ͼchart��ǩ�ַ���
	 * 
	 * @param caption
	 *            ����
	 * @return
	 */
	public static String getPieChart(String caption) {
		return "<chart caption='" + caption
				+ "' baseFont='����' baseFontSize='12' "
				+ " showValues='1' decimalPrecision='0' formatNumberScale='0'>";
	}

	/**
	 * ȡ��<set>��ǩ�ַ��������ڵ�����״ͼ
	 * 
	 * @param data
	 *            ���ж���
	 * @param colDescs
	 *            ����������
	 * @return
	 */
	public static String getSets(Object[] data, String[] colDescs) {
		if (data == null || colDescs == null) {
			logger.error("�����ȡset��ǩ������Ϊnull");
			return null;
		}
		if (data.length != colDescs.length) {
			logger.error("�����ȡset��ǩ��������������������鳤�Ȳ�һ��");
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
	 * ȡ��<set>��ǩ�ַ��������ڱ�״ͼ
	 * 
	 * @param data
	 *            ��������
	 * @return
	 */
	public static String getSets(Object[][] data) {
		if (data == null || data.length == 0) {
			logger.error("�����ȡset��ǩ������Ϊnull����������");
			return null;
		}

		if (data[0].length != 2) {
			logger.error("�����ȡset��ǩ���������鳤�Ȳ���");
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
	 * ȡ������<categories>��ǩ�ַ���
	 * 
	 * @param rowDescs
	 *            ����������
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
	 * ȡ������<datasets>��ǩ�ַ���
	 * 
	 * @param data
	 *            ���ݿ����ݣ���������ת��
	 * @param colDescs
	 *            ����������
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
	 * ȡ�õ�ϵ��״ͼ����XML��ʽ�ַ���
	 * 
	 * @param caption
	 *            ����
	 * @param data
	 *            ����
	 * @param colDescs
	 *            ����������
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
	 * ȡ�ñ�״ͼ����XML��ʽ�ַ���
	 * 
	 * @param caption
	 *            ����
	 * @param data
	 *            ����
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
	 * ȡ�ö�ϵ��״ͼ����XML��ʽ�ַ���
	 * 
	 * @param caption
	 *            ����
	 * @param data
	 *            ���ݣ���������ת��
	 * @param colDescs
	 *            ����������
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
