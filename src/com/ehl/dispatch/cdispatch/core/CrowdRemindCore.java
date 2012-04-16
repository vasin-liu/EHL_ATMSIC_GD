package com.ehl.dispatch.cdispatch.core;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.dispatch.cdispatch.dao.CrowdRemindDao;
import com.ehl.tira.util.XML;
import com.ehl.util.Json;

/**
 * 拥堵提醒业务逻辑类
 * 
 * @author xiayouxue
 * 
 */
public class CrowdRemindCore extends BaseCore {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 对象名称 */
	private String oname = "crowdremind";

	/** 拥堵提醒数据访问类 */
	private CrowdRemindDao dao = new CrowdRemindDao();

	public CrowdRemindCore() {
		super.setLogger(logger);
		super.setDao(dao);
		super.setOname(oname);
	}

	public String getStringByAlarmId(String alarmId){
		Object[][] objects = dao.getByAlarmId(alarmId);
		String xml = XML.getNodes(oname, dao.getCnames(), objects);
		return xml;
	}

	public JSONArray getByAlarmId(String alarmId) {
		JSONArray array = null;
		Object[][] objects = dao.getByAlarmId(alarmId);
		if(objects != null){
			array = Json.toJson(objects, dao.getCnames());
		}
		return array;
	}

}
