package com.ehl.drpt.common;

import com.ehl.drpt.violationStat.dao.ViolationStatDaoImpl;
import com.ehl.drpt.violationStat.dao.ViolationStatDaoUI;


public class Factory {
	/**
	 * <b>获取统计信息数据接口.</b>
	 * @return 数据接口.
	 */
	public  static ViolationStatDaoUI getViolationStatDaoImpl(){
		return(new ViolationStatDaoImpl());	
		}
}
