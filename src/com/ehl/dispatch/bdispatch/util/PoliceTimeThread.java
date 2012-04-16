package com.ehl.dispatch.bdispatch.util;

import org.apache.log4j.Logger;


/**
 * @类型说明:出/到警时间计算线程.
 * @创建者：linbh
 * @创建日期 2009-07-21
 */
final class PoliceTimeThread extends Thread {

	Logger logger = Logger.getLogger(PoliceTimeThread.class);

	public final void run() {
		try {
			while (true) {
				Thread.sleep(PoliceTime.getInterval());
				if (PoliceTime.getUpdateFlag()){
					PoliceTime.calLeaveTime();
				}
				PoliceTime.calArriveTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[分控中心]" + e.getMessage());
		}
	}

}
