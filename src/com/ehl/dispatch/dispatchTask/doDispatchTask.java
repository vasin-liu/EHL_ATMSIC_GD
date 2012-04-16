package com.ehl.dispatch.dispatchTask;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.dispatchTask.bean.TrafficnewsfeedsBean;
import com.ehl.dispatch.dispatchTask.util.TaskUtil;
import com.ehl.webservice.fw.TFPM_MergeShowResult_Ety;
import com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub;

public class doDispatchTask extends TimerTask {

	Logger logger = Logger.getLogger(doDispatchTask.class);
	
	
	public static void updateFwTrafficNewsFeed(){
		Timer timer = new Timer();
		Calendar c = Calendar.getInstance();
		//int hour24 = com.appframe.common.Setting.getInt("ACC_TASK_HOUR"); 
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.SECOND, 10);
		Date tmp = c.getTime();
		System.out.println("����ӵ����Ϣ��ʼ������" + tmp.toString());
		new doDispatchTask().run();
		timer.scheduleAtFixedRate(new doDispatchTask(), tmp, 1000*60*20);
		System.out.println("����ӵ����Ϣ������" + Calendar.getInstance().getTime().toString());
	}
	@Override
	public void run() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TrafficnewsfeedsBean trafficnewsfeedsBean = new TrafficnewsfeedsBean();
			TaskUtil taskUtil = new TaskUtil();
			java.net.URL url = new java.net.URL("http://10.40.30.38/ri/WS_TrafficTransportationInfo_BizCtrlImpl.asmx");
			Service service = new Service();
			WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub bizCtrlImplSoap12Stub = 
				new WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub(url,service);
			// ���÷�ΰ�ķ���ȡ��ӵ��·����Ϣ·��
			TFPM_MergeShowResult_Ety[] eFPM_MergeShowResult_Ety = bizCtrlImplSoap12Stub.getRealTimeTrafficInfo(0, "001");
			if (eFPM_MergeShowResult_Ety != null) {
				// �������ֶ��ϱ���ӵ����Ϣ����Ϊ'0'��ͨ
				//task.update_LcbPtMis_Table();
				String lk = "";
				String lkValue = "";
				String startMileage = "";
				String endMileage = "";
				String direction = "";
				// ����ʵ�� ��ȡ��ΰ��ӵ����Ϣ����  EXCH_T_TRAFFICNEWSFEEDS_TEMP ��
				for(int i=0; i<eFPM_MergeShowResult_Ety.length; i++) {
					// ��ͨ״̬
					lk = String.valueOf(eFPM_MergeShowResult_Ety[i].getTRAFFICSTATUS());
					// �ų�"ӵ������"����������
					if ("0".equals(lk)) {

						// ������ŵ��趨
						trafficnewsfeedsBean.setBH(CreateSequence.getMaxForSeq("SEQ_EXCH_T_TRAFFICNEWSFEEDS", 20));
						// ��·����
						if (eFPM_MergeShowResult_Ety[i].getROADNAME() == null
								|| "".equals(eFPM_MergeShowResult_Ety[i]
										.getROADNAME())) {
							trafficnewsfeedsBean = new TrafficnewsfeedsBean();
							continue;
						}
						trafficnewsfeedsBean.setDLMC(eFPM_MergeShowResult_Ety[i].getROADNAME());
						// ·�η���
						direction = String.valueOf(eFPM_MergeShowResult_Ety[i].getDIRECTION());
						if(direction != null ) {
							if("3".equals(direction) || "4".equals(direction)) {
								// ������Ϊ 0 :����
								trafficnewsfeedsBean.setLDFX("����");
							} else {
								// ������Ϊ 1 :����
								trafficnewsfeedsBean.setLDFX("����");
							}
						}
						// ��ʼ���
						startMileage = eFPM_MergeShowResult_Ety[i].getSTARTMILEAGE().split("\\+")[0].replace("K", "");
						trafficnewsfeedsBean.setQSLC(startMileage);
						// ��ֹ���
						endMileage = eFPM_MergeShowResult_Ety[i].getENDMILEAGE().split("\\+")[0].replace("K", "");
						trafficnewsfeedsBean.setZZLC(endMileage);
						// ·�� 0:ӵ�� 1:��ͨ 2:����)
						if("0".equals(lk)) {
							lkValue = "ӵ��";
						} else if ("1".equals(lk)) {
							lkValue = "��ͨ";
						} else {
							lkValue = "����";
						}
						trafficnewsfeedsBean.setLK(lkValue);
						// ��ʼʱ��
						trafficnewsfeedsBean.setQSSJ(String.valueOf(format.format(eFPM_MergeShowResult_Ety[i].getBEGINTIME().getTime())));
						// ��ֹʱ��
						trafficnewsfeedsBean.setZZSJ(String.valueOf(format.format(eFPM_MergeShowResult_Ety[i].getENDTIME().getTime())));
						// ·���Ƿ����(1���Ѿ�������0��δ����)
						trafficnewsfeedsBean.setSFJS("0");
						// ·��ԭ��
						trafficnewsfeedsBean.setLKYY("�������ɼ�·��");
						// λ�û��������
						trafficnewsfeedsBean.setWZMS("�������ɼ�·��");
						// ��Ͻ���
						String departMnet = taskUtil.getDepartmentNameByRoadname(eFPM_MergeShowResult_Ety[i].getROADNAME(), startMileage, endMileage);
						
						if("δȡ������Ͻ��".equals(departMnet)) {
							trafficnewsfeedsBean = new TrafficnewsfeedsBean();
							continue;
						}
						trafficnewsfeedsBean.setGXDD(departMnet);
						// ��Ϣ��Դ (���������)
						trafficnewsfeedsBean.setXXLY("�������ɼ�·��");
						// ����״̬  ����״̬ 0:���ܶӴ��� 1:����Ӻ�ʵ 2�����ܶ�ȷ�� 3��������� 4���Ѻ���
						trafficnewsfeedsBean.setCLZT("0");
						// �Ƿ񷢲�(0 :���� 1��������)
						trafficnewsfeedsBean.setSFFB("1");
						
						// �ж��Ƿ�Ϊ��Ҫ���������
						if(taskUtil.getInsertFlg(trafficnewsfeedsBean)) {
							// ����·�����ϱ���Ϣ
							taskUtil.insertTrafficnewsfeedsInfo(trafficnewsfeedsBean);
						}
						trafficnewsfeedsBean = new TrafficnewsfeedsBean();
					}
				}
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			logger.error("ȡ��webService���� ����");
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.error("����webService�� ����");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�÷�ΰӵ����Ϣ����");
		}
	}
	
	/**
	 * ����LCB_PT_MIS��<br/>
	 * 
	 */
	public void update_LcbPtMis_Table () {
		String sqlBuffer = "";
		try {
			sqlBuffer = " update LCB_PT_MIS set ZT='0' where UPDATE_TYPE_FLG !='0' and UPDATE_TYPE_FLG !='1' ";
			DBHandler.execute(sqlBuffer);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("���� LCB_PT_MIS ����� sql:" + sqlBuffer);
		}
	}

}
