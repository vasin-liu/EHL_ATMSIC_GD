package com.ehl.dispatch.bdispatch.util;

import org.apache.log4j.Logger;


/**
 * @����˵��:��/����ʱ������߳�.
 * @�����ߣ�linbh
 * @�������� 2009-07-21
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
			logger.error("[�ֿ�����]" + e.getMessage());
		}
	}

}
