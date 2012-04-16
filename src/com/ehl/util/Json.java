package com.ehl.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Json {
	
	public static Map<String,Object> getConfig(){
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("id", "id");
		config.put("root", "rows");
		config.put("cell", "cell");
		config.put("index", 0);
		return config;
	}
	
	public static JSONObject toJson(Object[] object, String[] names) {
		JSONObject json = new JSONObject();
		for (int i = 0; i < names.length && i < object.length; i++) {
			json.put(names[i], object[i]);
		}
		return json;
	}

	public static JSONArray toJson(Object[][] objects, String[] names) {
		JSONArray array = new JSONArray();
		if(objects != null){
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] != null) {
					array.add(toJson(objects[i], names));
				}
			}
		}
		return array;
	}
	
	public static JSONObject fromData(Object[][] objects,Map<String,Object> config){
		JSONObject result = new JSONObject();
		if(objects != null){
			JSONArray rows = new JSONArray();
			for (int i = 0; i < objects.length; i++) {
				Object[] object = objects[i];
				if(object != null){
					JSONObject row = new JSONObject();
					row.put(config.get("id"), object[(Integer)config.get("index")]);
					row.put(config.get("cell"), JSONArray.fromObject(object));
					rows.add(row);
				} 
			}
			result.put(config.get("root"), rows);
		}
		return result;
	}
	
	public static JSONObject fromData(Object[][] objects){
		return fromData(objects, getConfig());
	}
	
	public static void main(String[] args) {
		Object[][] objects = {{"1","2"},{"2"}};
		System.err.println(JSONArray.fromObject(objects));
	}
	
}
