package com.ehl.dispatch.duty.core;

import com.ehl.dispatch.duty.dao.DutyDao;

public class DutyCore {
	private DutyDao dao = new DutyDao();
	
	public String getPhoneByJgid(String jgid) {
		return (String) dao.geTelByJgid(jgid);
	}

}
