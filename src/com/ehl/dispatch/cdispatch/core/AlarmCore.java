package com.ehl.dispatch.cdispatch.core;

import net.sf.json.JSONObject;

import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.util.Json;

public class AlarmCore {

	private AlarmDao dao = new AlarmDao();

	public JSONObject getById(String alarmId) {
		JSONObject jsonObject = null;
		Object[] object = dao.getById(alarmId);
		if (object != null) {
			jsonObject = Json.toJson(object, dao.getCnames());
		}
		return jsonObject;
	}

	public AlarmDao getDao() {
		return dao;
	}

}
