package com.ehl.dispatch.bdispatch.util;

import org.apache.log4j.Logger;
import com.appframe.data.sql.DBHandler;
import com.appframe.data.sql.MultiDBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.BaseUtil;
import com.ehl.webgis.util.GeoUtil;

/**
 * <b>出/到警时间计算类. </b>
 * @author linbh.
 * @version 1.0 [2009-07-21].
 */
public class PoliceTime{

	private static long priInterval = 1*1000; //刷新间隔 1秒
	private static boolean priUpdateFlag = true; //是否更新出警时间标志
	private static String priHphm = null; //号牌号码
	private static String priFeedbackID = null; //反馈编号
	private static double priLongtitude = 0; //经度
	private static double priLatitude = 0; //纬度
	private static PoliceTimeThread policeTimeThread;
	private static String priGPSDataSource = Constants.GPSDATASOURCE_KEY; //GPS连接数据源
	private static Logger logger = Logger.getLogger(PoliceTime.class);

	/**
	 * <b>启动GPS车辆定位主线程.</b>
	 * @para String hphm 号牌号码
	 * @para String longLat 经纬度(经度和纬度之间以逗号分隔)
	 * @para String feedbackID 反馈编号
	 */
	public static void start(String hphm,String longLat,String feedbackID) {		
		
		if (hphm.length() == 0 || longLat.length() == 0){
			return;
		}
		
		priHphm = hphm;
		priFeedbackID = feedbackID;
		String[] strLongLat = longLat.split(",");
		priLongtitude = Double.parseDouble(strLongLat[0]);
		priLatitude = Double.parseDouble(strLongLat[1]);
		
		stop();

		if (priInterval > 0L) {
			//启动时间计算线程
			policeTimeThread = new PoliceTimeThread();
			policeTimeThread.start();
		}
	}

	/**
	 * <b>停止时间计算线程.</b>
	 */
	public static void stop() {
		if (policeTimeThread != null){
			policeTimeThread.interrupt();
		}
	}

	/**
	 * <b>计算出警时间.</b>
	 */
	public static void calLeaveTime(){
		
		try{
			String strSQL = "SELECT to_char(gpstime,'yyyy-mm-dd hh24:mi:ss'),velocity FROM (SELECT gpstime,velocity FROM t_gps_carinfo WHERE carnumber='" + priHphm + "' ORDER BY gpstime DESC) WHERE ROWNUM=1";			
			Object[][] oResult = MultiDBHandler.getMultiResult(priGPSDataSource, strSQL);
			if (oResult != null){
				String strGpstime = StringHelper.obj2str(oResult[0][0],"");
				double dbVelocity = Double.parseDouble(StringHelper.obj2str(oResult[0][1],"0"));
				
				if (dbVelocity > 0){
					strSQL = "UPDATE T_ATTEMPER_FEEDBACK SET COMEOUTTIME=TO_DATE('" + strGpstime + "','yyyy-mm-dd hh24:mi:ss') WHERE FEEDBACKID='" + priFeedbackID + "'";
					DBHandler.execute(strSQL);
					priUpdateFlag = false;					
				}					
			}
			
		}catch (Throwable e){
			e.printStackTrace();
			logger.error("[分控中心]" + e.getMessage());		
		}
	}

	/**
	 * <b>计算到警时间.</b>
	 */
	public static void calArriveTime(){
		
		try{
			String gpsDataSource = Constants.GPSDATASOURCE_KEY;
			String strSQL = "SELECT longtitude,latitude,to_char(gpstime,'yyyy-mm-dd hh24:mi:ss') FROM (SELECT longtitude,latitude,gpstime FROM t_gps_carinfo WHERE carnumber='" + priHphm + "' ORDER BY gpstime DESC) WHERE ROWNUM=1";			
			Object[][] oResult = MultiDBHandler.getMultiResult(gpsDataSource, strSQL);
			if (oResult != null){
				double dbLongtitude = Double.parseDouble(StringHelper.obj2str(oResult[0][0],"0"));
				double dbLatitude = Double.parseDouble(StringHelper.obj2str(oResult[0][1],"0"));
				String strGpstime = StringHelper.obj2str(oResult[0][2],"");
				double dbDistance = GeoUtil.calcuDistance(dbLongtitude,dbLatitude,priLongtitude,priLatitude);
				double dbParaDistance = Double.parseDouble(BaseUtil.getSysOption("57","57003"));
				
				if (dbDistance <= dbParaDistance){
					strSQL = "UPDATE T_ATTEMPER_FEEDBACK SET COMEOUTARRIVETIME=TO_DATE('" + strGpstime + "','yyyy-mm-dd hh24:mi:ss') WHERE FEEDBACKID='" + priFeedbackID + "'";
					DBHandler.execute(strSQL);
					stop();
				}
			}			
		}catch (Throwable e){
			e.printStackTrace();
			logger.error("[分控中心]" + e.getMessage());			
		}
	}

	/**
	 * <b>获取时间间隔.</b>
	 */
	public static long getInterval() {
		return priInterval;
	}

	/**
	 * <b>获取是否更新出警时间标志.</b>
	 */
	public static boolean getUpdateFlag() {
		return priUpdateFlag;
	}

}
