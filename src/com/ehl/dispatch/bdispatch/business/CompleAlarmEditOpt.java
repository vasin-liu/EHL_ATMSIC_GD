package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.AlarmInfoData;
import com.ehl.dispatch.bdispatch.dataAccess.CompleAlarmDate;

public class CompleAlarmEditOpt {
	/**
	 * 新增一条事故报警信息
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
	 * 新增一条拥堵报警信息
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
	 * 新增一条恶劣天气信息
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
	 * 新增一条治安信息
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
	 * 新增一条故障车信息
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
	 * 新增一条地质信息
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
	 * 新增一条市政信息
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
	 * 新增一条火灾信息
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
