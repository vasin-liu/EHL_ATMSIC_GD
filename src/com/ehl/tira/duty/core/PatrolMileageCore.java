package com.ehl.tira.duty.core;

import org.apache.log4j.Logger;


import com.ehl.tira.Tira;
import com.ehl.tira.duty.PatrolUtil;
import com.ehl.tira.duty.dao.PatrolMileageDao;

/**
 * PatrolMileageCore(Ѳ��,���)Ѳ�����
 * 
 * @author xiayouxue
 * 
 */
public class PatrolMileageCore {

	private Logger logger = Logger.getLogger(PatrolMileageCore.class);
	private PatrolMileageDao pmDao = new PatrolMileageDao();

	private String getLocation(String location) {
		return Tira.root + "->" + Tira.duty + "->" + location + ":" + Tira.core
				+ "��";
	}
	
	//��Ѳ����̣�����������
	public String totalDept(String area, String dateS, String dateE) {

		String location = getLocation(Tira.dutyPotralTotal) + "��ȡ"
				+ Tira.dutyPotralTotal + "���������౨��XML���ݣ�" + Tira.error + "��\n";
		
		String chartXML = null;
		Object[][] data = pmDao.totalDept(area, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "���ٴ��", "��Ͻ���", "Ѳ�����", "Ѳ�߳���" };
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
				logger.error(location + "��ϸ��Ϣ:δ֪�쳣��");
			}
			
		} else {
			isSuccess = false;
			logger.error(location + "��ϸ��Ϣ:dao�㴫�����ݸ�ʽ������");
		}

		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		
		return chartXML;
	}
	
	//��Ѳ����̣������ڷ���
	public String totalTime(String dept, String dateS, String dateE) {
		String location = getLocation(Tira.dutyPotralTotal) + "��ȡ"
				+ Tira.dutyPotralTotal + "��ʱ����౨��XML���ݣ�" + Tira.error + "��\n";
		String chartXML = null;
		Object[][] data = pmDao.totalTime(dept, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "����", "��Ͻ���", "Ѳ�����", "Ѳ�߳���" };
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
			logger.error(location + "��ϸ��Ϣ:dao�㴫�����ݸ�ʽ������");
		}

		if (chartXML == null) {
			isSuccess = false;
			logger.error(location + "��ϸ��Ϣ:δ֪�쳣��");
		}
		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		return chartXML;
	}
	
	//ƽ��Ѳ����̣�����������
	public String avgDept(String area, String dateS, String dateE) {
		String location = getLocation(Tira.dutyPotralTotal) + "��ȡ"
				+ Tira.dutyPotralAvg + "���������౨��XML���ݣ�" + Tira.error + "��\n";
		String chartXML = null;
		Object[][] data = pmDao.avgDept(area, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "���ٴ��", "��Ͻ���", "ƽ��Ѳ�����" };
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
			logger.error(location + "��ϸ��Ϣ:dao�㴫�����ݸ�ʽ������");
		}

		if (chartXML == null) {
			isSuccess = false;
			logger.error(location + "��ϸ��Ϣ:δ֪�쳣��");
		}
		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		return chartXML;
	}
	
	//ƽ��Ѳ����̣������ڷ���
	public String avgTime(String dept, String dateS, String dateE) {
		String location = getLocation(Tira.dutyPotralTotal) + "��ȡ"
				+ Tira.dutyPotralAvg + "�����ڷ��౨��XML���ݣ�" + Tira.error + "��\n";
		String chartXML = null;
		Object[][] data = pmDao.avgTime(dept, dateS, dateE);
		String[][] dataStr = PatrolUtil.changeData(data);
		boolean isSuccess = true;
		String chartType = "";
		if (dataStr != null) {
			String title = "";
			String[] titles = { "����", "��Ͻ���", "ƽ��Ѳ�����" };
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
			logger.error(location + "��ϸ��Ϣ:dao�㴫�����ݸ�ʽ������");
		}

		if (chartXML == null) {
			isSuccess = false;
			logger.error(location + "��ϸ��Ϣ:δ֪�쳣��");
		}
		chartXML = PatrolUtil.chart(isSuccess, chartXML);
		return chartXML;
	}

	/**
	 * createPatrolTotalMileageChartXML<br>
	 * ����Ѳ����̱���XML����
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
				titles = new String[] { "���ٴ��", "��Ͻ���", "ƽ��Ѳ�����" };
				if ("".equals(dept)) {
					titles = new String[] { "ʱ��", "��Ͻ���", "ƽ��Ѳ�����" };
				}
			} else {
				titles = new String[] { "���ٴ��", "��Ͻ���", "Ѳ�����", "Ѳ�߳���" };
				if ("".equals(dept)) {
					titles = new String[] { "ʱ��", "��Ͻ���", "Ѳ�����", "Ѳ�߳���" };
				}
			}
			datas = PatrolUtil.changeData(datas, titles);
			chartXML = PatrolUtil.columnMultipleCol(datas, title);
		} else {
			logger.error("��������->��Ͻ�����ٹ�·����->Ѳ��" + (isAvg ? "��" : "ƽ��")
					+ "��̷���:core�㣬��ȡѲ��" + (isAvg ? "��" : "ƽ��")
					+ "����XML���ݣ�dao�㴫�����ݸ�ʽ������");
		}

		if (chartXML == null) {
			logger.error("��������->��Ͻ�����ٹ�·����->Ѳ��" + (isAvg ? "��" : "ƽ��")
					+ "��̷���:core�㣬��ȡѲ��" + (isAvg ? "��" : "ƽ��")
					+ "����XML���ݣ�δ֪�쳣��");
		}

		return chartXML;
	}

}
