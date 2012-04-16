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
		System.out.println("更新拥堵信息开始运行于" + tmp.toString());
		new doDispatchTask().run();
		timer.scheduleAtFixedRate(new doDispatchTask(), tmp, 1000*60*20);
		System.out.println("更新拥堵信息结束于" + Calendar.getInstance().getTime().toString());
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
			// 调用方伟的方法取得拥堵路况信息路况
			TFPM_MergeShowResult_Ety[] eFPM_MergeShowResult_Ety = bizCtrlImplSoap12Stub.getRealTimeTrafficInfo(0, "001");
			if (eFPM_MergeShowResult_Ety != null) {
				// 将不是手动上报的拥堵信息更新为'0'畅通
				//task.update_LcbPtMis_Table();
				String lk = "";
				String lkValue = "";
				String startMileage = "";
				String endMileage = "";
				String direction = "";
				// 遍历实体 获取方伟的拥堵信息插入  EXCH_T_TRAFFICNEWSFEEDS_TEMP 表
				for(int i=0; i<eFPM_MergeShowResult_Ety.length; i++) {
					// 交通状态
					lk = String.valueOf(eFPM_MergeShowResult_Ety[i].getTRAFFICSTATUS());
					// 排除"拥堵以外"的其他数据
					if ("0".equals(lk)) {

						// 主键编号的设定
						trafficnewsfeedsBean.setBH(CreateSequence.getMaxForSeq("SEQ_EXCH_T_TRAFFICNEWSFEEDS", 20));
						// 道路名称
						if (eFPM_MergeShowResult_Ety[i].getROADNAME() == null
								|| "".equals(eFPM_MergeShowResult_Ety[i]
										.getROADNAME())) {
							trafficnewsfeedsBean = new TrafficnewsfeedsBean();
							continue;
						}
						trafficnewsfeedsBean.setDLMC(eFPM_MergeShowResult_Ety[i].getROADNAME());
						// 路段方向
						direction = String.valueOf(eFPM_MergeShowResult_Ety[i].getDIRECTION());
						if(direction != null ) {
							if("3".equals(direction) || "4".equals(direction)) {
								// 西，北为 0 :上行
								trafficnewsfeedsBean.setLDFX("上行");
							} else {
								// 东，南为 1 :下行
								trafficnewsfeedsBean.setLDFX("下行");
							}
						}
						// 起始里程
						startMileage = eFPM_MergeShowResult_Ety[i].getSTARTMILEAGE().split("\\+")[0].replace("K", "");
						trafficnewsfeedsBean.setQSLC(startMileage);
						// 终止里程
						endMileage = eFPM_MergeShowResult_Ety[i].getENDMILEAGE().split("\\+")[0].replace("K", "");
						trafficnewsfeedsBean.setZZLC(endMileage);
						// 路况 0:拥堵 1:畅通 2:缓慢)
						if("0".equals(lk)) {
							lkValue = "拥堵";
						} else if ("1".equals(lk)) {
							lkValue = "畅通";
						} else {
							lkValue = "缓慢";
						}
						trafficnewsfeedsBean.setLK(lkValue);
						// 起始时间
						trafficnewsfeedsBean.setQSSJ(String.valueOf(format.format(eFPM_MergeShowResult_Ety[i].getBEGINTIME().getTime())));
						// 终止时间
						trafficnewsfeedsBean.setZZSJ(String.valueOf(format.format(eFPM_MergeShowResult_Ety[i].getENDTIME().getTime())));
						// 路况是否结束(1是已经结束，0是未结束)
						trafficnewsfeedsBean.setSFJS("0");
						// 路况原因
						trafficnewsfeedsBean.setLKYY("浮动车采集路况");
						// 位置或情况描述
						trafficnewsfeedsBean.setWZMS("浮动车采集路况");
						// 管辖大队
						String departMnet = taskUtil.getDepartmentNameByRoadname(eFPM_MergeShowResult_Ety[i].getROADNAME(), startMileage, endMileage);
						
						if("未取得所属辖区".equals(departMnet)) {
							trafficnewsfeedsBean = new TrafficnewsfeedsBean();
							continue;
						}
						trafficnewsfeedsBean.setGXDD(departMnet);
						// 信息来源 (浮动车监测)
						trafficnewsfeedsBean.setXXLY("浮动车采集路况");
						// 处理状态  处理状态 0:待总队处理 1:待大队核实 2：待总队确认 3：处理完成 4：已忽略
						trafficnewsfeedsBean.setCLZT("0");
						// 是否发布(0 :发布 1：不发布)
						trafficnewsfeedsBean.setSFFB("1");
						
						// 判断是否为需要插入的数据
						if(taskUtil.getInsertFlg(trafficnewsfeedsBean)) {
							// 插入路况报料表信息
							taskUtil.insertTrafficnewsfeedsInfo(trafficnewsfeedsBean);
						}
						trafficnewsfeedsBean = new TrafficnewsfeedsBean();
					}
				}
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			logger.error("取得webService连接 错误");
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.error("调用webService出 错误");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得方伟拥堵信息出错");
		}
	}
	
	/**
	 * 更新LCB_PT_MIS表<br/>
	 * 
	 */
	public void update_LcbPtMis_Table () {
		String sqlBuffer = "";
		try {
			sqlBuffer = " update LCB_PT_MIS set ZT='0' where UPDATE_TYPE_FLG !='0' and UPDATE_TYPE_FLG !='1' ";
			DBHandler.execute(sqlBuffer);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("更新 LCB_PT_MIS 表出错 sql:" + sqlBuffer);
		}
	}

}
