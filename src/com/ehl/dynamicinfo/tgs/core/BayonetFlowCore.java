package com.ehl.dynamicinfo.tgs.core;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ehl.dynamicinfo.tgs.dao.BayonetFlowDao;
import com.ehl.general.service.GeneralInfoService;

/**
 * ��������ҵ���߼���
 * 
 * @author xiayx
 * 
 */
public class BayonetFlowCore {

	private BayonetFlowDao dao = new BayonetFlowDao();
	private GeneralInfoService json = new GeneralInfoService();

	/**
	 * ͳ��һ�������ڣ���������δ��ȡ������������
	 * @param id ���ڱ��
	 * @param date ���ڷ�Χ
	 * @param state ����״̬
	 *  
	 */
	public JSONObject statisWrongDays(String id,String date, String state) {
		Object[][] objects = dao.statisWrongDays(id, date, state);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 *  ͳ��һ�������ڣ�ĳ������ÿ��δ��ȡ��������Сʱ��
	 * @param state 
	 *  
	 */
	public JSONObject statisWrongHours(String id,String date) {
		Object[][] objects = dao.statisWrongHours(id,date);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 * ��ȡĳ������ĳ�����Сʱ������ֵ
	 * @param state 
	 * 
	 */
	public JSONObject getFlow(String id, String date) {
		Object[][] objects = dao.getFlow(id,date);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 *  �鿴����������Ƶ״̬���ܷ�鿴��Ƶ
	 * @param state 
	 */
	public JSONObject watchVideoState(String state){
		Object[][] objects = dao.getVideoState(state);
		Map<String,String> page = new HashMap<String, String>();
		return json.getJsonObject(objects, page);
	}
	
	/**
	 *  ��ȡ������Ϣ
	 */
	public JSONArray getBayonet(){
		Object[][] objects = dao.getBayonet();
		return toJson(objects, new String[]{"id","name"});
	}
	
	public static JSONObject toJson(Object[] object, String[] names){
		if(object == null || names == null){
			String param = object == null ? "object":"names";
			throw new RuntimeException("����"+param+"Ϊnull��");
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
			throw new RuntimeException("����"+param+"Ϊnull��");
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
