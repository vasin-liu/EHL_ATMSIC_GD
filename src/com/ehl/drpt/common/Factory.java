package com.ehl.drpt.common;

import com.ehl.drpt.violationStat.dao.ViolationStatDaoImpl;
import com.ehl.drpt.violationStat.dao.ViolationStatDaoUI;


public class Factory {
	/**
	 * <b>��ȡͳ����Ϣ���ݽӿ�.</b>
	 * @return ���ݽӿ�.
	 */
	public  static ViolationStatDaoUI getViolationStatDaoImpl(){
		return(new ViolationStatDaoImpl());	
		}
}
