package com.ehl.dispatch.bdispatch.util;

import org.apache.log4j.Logger;
import com.appframe.data.sql.DBHandler;
import com.appframe.data.sql.MultiDBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.BaseUtil;
import com.ehl.webgis.util.GeoUtil;

/**
 * <b>��/����ʱ�������. </b>
 * @author linbh.
 * @version 1.0 [2009-07-21].
 */
public class PoliceTime{

	private static long priInterval = 1*1000; //ˢ�¼�� 1��
	private static boolean priUpdateFlag = true; //�Ƿ���³���ʱ���־
	private static String priHphm = null; //���ƺ���
	private static String priFeedbackID = null; //�������
	private static double priLongtitude = 0; //����
	private static double priLatitude = 0; //γ��
	private static PoliceTimeThread policeTimeThread;
	private static String priGPSDataSource = Constants.GPSDATASOURCE_KEY; //GPS��������Դ
	private static Logger logger = Logger.getLogger(PoliceTime.class);

	/**
	 * <b>����GPS������λ���߳�.</b>
	 * @para String hphm ���ƺ���
	 * @para String longLat ��γ��(���Ⱥ�γ��֮���Զ��ŷָ�)
	 * @para String feedbackID �������
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
			//����ʱ������߳�
			policeTimeThread = new PoliceTimeThread();
			policeTimeThread.start();
		}
	}

	/**
	 * <b>ֹͣʱ������߳�.</b>
	 */
	public static void stop() {
		if (policeTimeThread != null){
			policeTimeThread.interrupt();
		}
	}

	/**
	 * <b>�������ʱ��.</b>
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
			logger.error("[�ֿ�����]" + e.getMessage());		
		}
	}

	/**
	 * <b>���㵽��ʱ��.</b>
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
			logger.error("[�ֿ�����]" + e.getMessage());			
		}
	}

	/**
	 * <b>��ȡʱ����.</b>
	 */
	public static long getInterval() {
		return priInterval;
	}

	/**
	 * <b>��ȡ�Ƿ���³���ʱ���־.</b>
	 */
	public static boolean getUpdateFlag() {
		return priUpdateFlag;
	}

}
