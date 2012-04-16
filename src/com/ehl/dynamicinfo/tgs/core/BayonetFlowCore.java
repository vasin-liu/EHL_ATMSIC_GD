package com.ehl.dynamicinfo.tgs.core;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ehl.dynamicinfo.tgs.dao.BayonetFlowDao;
import com.ehl.general.service.GeneralInfoService;

/**
 * 卡口流量业务逻辑类
 * 
 * @author xiayx
 * 
 */
public class BayonetFlowCore {

	private BayonetFlowDao dao = new BayonetFlowDao();
	private GeneralInfoService json = new GeneralInfoService();

	/**
	 * 统计一段日期内，各个卡口未获取到流量的天数
	 * @param id 卡口编号
	 * @param date 日期范围
	 * @param state 卡口状态
	 *  
	 */
	public JSONObject statisWrongDays(String id,String date, String state) {
		Object[][] objects = dao.statisWrongDays(id, date, state);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 *  统计一段日期内，某个卡口每天未获取到流量的小时数
	 * @param state 
	 *  
	 */
	public JSONObject statisWrongHours(String id,String date) {
		Object[][] objects = dao.statisWrongHours(id,date);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 * 获取某个卡口某天各个小时的流量值
	 * @param state 
	 * 
	 */
	public JSONObject getFlow(String id, String date) {
		Object[][] objects = dao.getFlow(id,date);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 *  查看各个卡口视频状态，能否查看视频
	 * @param state 
	 */
	public JSONObject watchVideoState(String state){
		Object[][] objects = dao.getVideoState(state);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 *  获取卡口信息
	 */
	public JSONArray getBayonet(){
		Object[][] objects = dao.getBayonet();
		return toJson(objects, new String[]{"id","name"});
	}
	
	public static JSONObject toJson(Object[] object, String[] names){
		if(object == null || names == null){
			String param = object == null ? "object":"names";
			throw new RuntimeException("参数"+param+"为null！");
		}
		JSONObject json = new JSONObject();
		for (int i = 0; i < names.length && i < object.length; i++) {
			json.put(names[i], object[i]);
		}
		return json;
	}
	
	public static JSONArray toJson(Object[][] objects, String[] names){
		if(objects == null || names == null){
			String param = objects == null ? "objects":"names";
			throw new RuntimeException("参数"+param+"为null！");
		}
		JSONArray array = new JSONArray();
		for (int i = 0; i < objects.length; i++) {
			if(objects[i] == null){
				continue;
			}
			array.add(toJson(objects[i], names));
		}
		return array;
	}
	
}
