package com.ehl.dynamicinfo.policeRemind.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.common.Setting;
import com.ehl.dispatch.cdispatch.core.AlarmCore;
import com.ehl.dispatch.cdispatch.core.CrowdRemindCore;
import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.dispatch.duty.core.DutyCore;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.tira.util.XML;
import com.ehl.util.Collections;
import com.ehl.util.Json;

/**
 * 交警提示业务逻辑类
 * 
 * @author xiayouxue
 * 
 */
public class PoliceRemindCore {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 对象名称 */
	private String oname = "remind";

	/** 交警提示数据访问类 */
	private PoliceRemindDao dao = new PoliceRemindDao();

	/** 拥堵交警提示业务逻辑类 */
	private CrowdRemindCore crcore = new CrowdRemindCore();

	/** 警情业务逻辑类 */
	private AlarmCore alarmCore = new AlarmCore();

	/** 值班业务逻辑类 */
	private DutyCore dutyCore = new DutyCore();
	/**
	 * 获取滚动信息
	 * 
	 * @return xml格式滚动数据
	 */
	public String getRollInfo(String ptype) {
		Object[][] objects = dao.getRollInfo(ptype);
		String xml = XML.getNodes(oname, dao.getRollInfoFields(), objects);
		xml = XML.getXML(xml);
		return xml;
	}

	/**
	 * <pre>
	 * 获取详细信息
	 * 如果是拥堵，则获取最初信息的初始拼接描述+拥堵提示记录
	 * 如果是其他，则直接获取最新信息的当前状态拼接描述
	 * </pre>
	 * 
	 * @param alarmId
	 *            警情编号
	 * @return xml格式详细信息
	 */
	public String getDetailInfo(String alarmId) {
		String xml = "";
		Object[][] objects = dao.getByAlarmId(alarmId);
		if (objects != null && objects.length > 0) {
			Object[] alarm = alarmCore.getDao().getById(alarmId);
			String alarmXml = XML.getNode("alarm", alarmCore.getDao()
					.getCnames(), alarm, null);
			Object[] object = objects[objects.length - 1];
			if (!alarm[1].equals(AlarmDao.ACC_CODE)) {
				object = objects[0];
				xml = crcore.getStringByAlarmId(alarmId);
			}
			xml = XML.getNode(oname, dao.getCnames(), object, alarmXml
					+ Constant.nvl(xml));
		}
		xml = XML.getXML(xml);
		return xml;
	}

	public String getById(String remindId) {
		Object[] object = dao.getById(remindId);
		String xml = XML.getNode(oname, dao.getCnames(), object, null);
		xml = XML.getXML(xml);
		return xml;
	}

	/**
	 * 获取人工改正信息
	 * 
	 * @param source
	 *            来源类型
	 * @param id
	 *            警情提示父编号或者警情编号
	 * @param jgid
	 *            机构编号
	 * @return JSON对象信息
	 */
	public JSONObject getCorrectInfo(String source, String id, String jgid) {
		JSONObject object = new JSONObject();
		Object[][] objects = dao.getBySource(source, id);
		JSONArray reminds = Json.toJson(objects, dao.getCnames());
		object.put("remind", reminds);
		if (source.equals("2")) {
			object.put("alarm", alarmCore.getById(id));
			object.put("phone", dutyCore.getPhoneByJgid(jgid));
			object.put("xb", crcore.getByAlarmId(id));
		}
		return object;
	}
	
	/**
	 * 获取信息，通过警情编号
	 * @param alarmId  警情编号
	 * @return xml格式数据
	 */
	public String getStringByAlarmId(String alarmId){
		Object[][] objects = dao.getByAlarmId(alarmId);
		String xml = XML.getNodes(oname, dao.getCnames(), objects);
		return xml;
	}
	
	/**
	 * 获取信息，通过警情编号
	 * 
	 * @param alarmId
	 *            警情编号
	 * @return JSON对象信息
	 */
	public JSONObject getByAlarmId(String alarmId) {
		Object[][] objects = dao.getByAlarmId(alarmId);
		JSONArray reminds = Json.toJson(objects, dao.getCnames());
		JSONArray xbs = crcore.getByAlarmId(alarmId);
		JSONObject object = new JSONObject();
		object.put("reminds", reminds);
		object.put("xb", xbs);
		return object;
	}

	public String getByExample(Map<String, String> object) {
		Object[][] objects = dao.getByExample(object);
		String xml = XML.getNodes(oname, dao.getCnames(), objects);
		xml = XML.getXML(xml);
		return xml;
	}
	
	public String insert(Map<String, String> object) {
		return dao.insert(object);
	}

	/**
	 * <pre>
	 * 发布策略
	 * 1.填报时间在发布起止时间内（包括），默认不发布
	 * 2.填报时间在发布起止时间外，默认发布到公安网
	 * 3.如果要解除警情，发布类型应该为历史发布类型总和（取唯一）
	 * </pre>
	 * 
	 * @param rtime
	 *            填报时间
	 * @return 发布策略代码值
	 * @throws ParseException
	 */
	public String getModifyPublishType(String rtime) throws ParseException {
		String ptype = "";
		String stime = Setting.getString("policeremind_publish_stime");
		String etime = Setting.getString("policeremind_publish_etime");
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
		Calendar crtime = Calendar.getInstance();
		crtime.setTime(formater.parse(rtime));
		Calendar cstime = (Calendar) crtime.clone();
		cstime.setTime(formater.parse(stime));
		Calendar cetime = (Calendar) crtime.clone();
		cetime.setTime(formater.parse(etime));
		if (crtime.before(cstime) || crtime.after(cetime)) {
			ptype = "1";
		}
		return ptype;
	}
	
	/**
	 * <pre>
	 * 发布交警提示信息
	 * 大队或者支队填报在填报拥堵信息时，
	 * 自动发布 的交警提示信息，由总队进行发布
	 * </pre>
	 * @param id 交警提示信息主键编号
	 * @param ptype 发布类型
	 * @return 是否发布成功
	 */
	public boolean publish(String id,String ptype, String remindinfo){
		Map<String,String> object = new HashMap<String,String>();
		object.put("remindid", id);
		object.put("state","2");
		object.put("publishtype", ptype);
		object.put("remindinfo", remindinfo);
		return dao.modifyById(object);
	}
	
	/**
	 * <pre>
	 * 同步交警提示信息
	 * 在总队发布了交警提示信息之后，再对该交警提示进行修改时，
	 * 需要同步最初由大队或支队上报的交警提示信息，使其发布状态
	 * 和本次更新的发布状态一致
	 * </pre>
	 * @param id 交警提示信息主键编号
	 * @param ptype 发布类型
	 * @return 是否同步成功
	 */
	public boolean synch(String id,String ptype){
		Map<String,String> object = new HashMap<String,String>();
		object.put("remindid", id);
		object.put("publishtype", ptype);
		return dao.modifyById(object);
	}

    /**
     * Modified by Liuwx 2012-4-11 14:50:27
     * <pre>
     *     更新信息发布状态（更新至公安网或者互联网，或者取消发布状态）
     * </pre>
     * @param params  参数列表
     * @return boolean
     */
    public boolean updatePublishState(Map<String,String> params){
        Map<String,String> object = new HashMap<String, String>();
        object.put("remindid",params.get("remindId"));
        object.put("publishtype",params.get("publishType"));
        return dao.modifyById(object);
    }
	
	public boolean modifyById(Map<String, String> object) {
		return dao.modifyById(object);
	}

	/**
	 * <pre>
	 * 获取解除发布类型
	 * 填报单位解除警情时，默认发布类型为历史发布类型总和（不重复）
	 * 总队解除警情时，默认发布类型同填报单位，但同时可由重新设置值进行更改
	 * </pre>
	 * @param alarmId 警情编号
	 * @return 解除发布类型
	 */
	public String getRelievePublishType(String alarmId) {
		Object[][] objects = dao.getByAlarmId(alarmId);
		Set<String> setPtype = new HashSet<String>();
		if (objects != null) {
			for (Object[] object : objects) {
				String strPtypes = (String) object[6];
				if (strPtypes != null) {
					String[] ptypes = strPtypes.split(",");
					for (String ptype : ptypes) {
						setPtype.add(ptype);
					}
				}
			}
		}
		return Collections.join(setPtype.iterator());
	}

	public PoliceRemindDao getDao() {
		return dao;
	}

	public void setDao(PoliceRemindDao dao) {
		this.dao = dao;
	}

	public static void main(String[] args) throws ParseException {
		Set<String> setPtype = new HashSet<String>();
		setPtype.add("1");
		setPtype.add("1");
		System.out.println(setPtype.size());
	}
}
