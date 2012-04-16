package com.ehl.sm.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.sm.dao.FuncDao;
import com.ehl.util.Json;

public class FuncCore extends BaseCore{

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 对象名称 */
	private String oname = "leave";

	/** 数据访问类 */
	private FuncDao dao = new FuncDao();


	public FuncCore() {
		super.setLogger(logger);
		super.setDao(dao);
		super.setOname(oname);
	}
	
	/**
	 * 查询功能信息
	 * @param conditions 查询条件
	 * @return JSONArray实例
	 */
	public JSONArray select(Map<String,Object> conditions){
		Object[][] objects = dao.select(conditions);
		return Json.toJson(objects, dao.getFields());
	}
	
	/**
	 * 获取节点标题
	 * @param id 节点编号
	 * @return 节点标题集合
	 */
	public List<String> getTitles(String id){
		ArrayList<String> parents = getParents(id, "00");
		if(parents.size() >= 1){
			parents.remove(parents.size() - 1);
		}
		parents.add(0, id);
		Collections.reverse(parents);
		id = toString(parents);
		Object[][] objects = dao.getById(id);
		List<String> objectL = new ArrayList<String>();
		for (int i = 0; i < objects.length; i++) {
			objectL.add(String.valueOf(objects[i][2]));
		}
		if(objectL.size() >= 1){
			objectL.remove(0);
		}
		return objectL;
	}
	
	/**
	 * 获取标志位所在索引
	 * @param id 编号
	 * @param mark 标志值
	 * @return 标志位所在索引
	 */
	public static int findMark(String id, String mark){
		int length = mark.length();
		int index = id.length() + length;
		for (int i = id.length(); i > 0; i = i - length) {
			if (id.substring(i - length, i).equals(mark)) {
				index = i;
				break;
			}
		}
		return index - length;
	}

	/**
	 * 获取父节点编号
	 * @param id 主键编号
	 * @param mark 标志值
	 * @return 父节点列表
	 */
	public static ArrayList<String> getParents(String id, String mark) {
		ArrayList<String> list = new ArrayList<String>();
		int index = findMark(id, mark);
		int length = mark.length();
		for (int i = index; i > 0; i = i - length) {
			id = new StringBuffer(id).replace(i - length, i, mark).toString();
			list.add(id);
		}
		return list;
	}
	
	/**
	 * 将集合转换成字符串
	 * @param list 欲转换集合
	 * @return 返回字符串
	 */
	public static String toString(List<String> list){
		String str = list.toString();
		str = str.replace("[", "").replace("]", "");
		str = str.replaceAll(", ", ",");
		return str;
	}
	
}
