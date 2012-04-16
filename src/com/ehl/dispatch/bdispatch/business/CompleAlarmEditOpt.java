package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.AlarmInfoData;
import com.ehl.dispatch.bdispatch.dataAccess.CompleAlarmDate;

public class CompleAlarmEditOpt {
	/**
	 * ����һ���¹ʱ�����Ϣ
	 */
	public String editAccidentInfo(Object[] res) throws Throwable {
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertAccidentInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}

	}
	
	/**
	 * ����һ��ӵ�±�����Ϣ
	 */
	public String editCongestionInfo(Object[] res) throws Throwable {
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertCongestionInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
	
	/**
	 * ����һ������������Ϣ
	 */
	public String editBadWeatherInfo(Object[] res) throws Throwable {	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertBadWeatherInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
	
	/**
	 * ����һ���ΰ���Ϣ
	 */
	public String editPoliceEventInfo(Object[] res) throws Throwable {
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertPoliceEventInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
	
	/**
	 * ����һ�����ϳ���Ϣ
	 */
	public String editExceptionCarInfo(Object[] res) throws Throwable {
		
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertExceptionCarInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
	/**
	 * ����һ��������Ϣ
	 */
	public String editGeoLogicDisasterInfo(Object[] res) throws Throwable {	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertGeoLogicDisasterInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
	
	/**
	 * ����һ��������Ϣ
	 */
	public String editTownplanInfo(Object[] res) throws Throwable {	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertTownplanInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
	
	/**
	 * ����һ��������Ϣ
	 */
	public String editFireAndBlastInfo(Object[] res) throws Throwable {	
		AlarmInfoOpt infoOpt=new AlarmInfoOpt();
		res=infoOpt.setTitle(res);
		CompleAlarmDate alarmInfoData = new CompleAlarmDate();
		boolean msg = alarmInfoData.insertTFireAndBlastInfo(res);
		AlarmInfoMsg infoMsg = new AlarmInfoMsg();		
		infoMsg.SendAlarmMsg(res);
		if (msg) {
			return "success";
		} else {
			return "error";
		}
	}
}
