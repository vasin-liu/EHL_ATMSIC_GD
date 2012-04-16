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

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** �������� */
	private String oname = "leave";

	/** ���ݷ����� */
	private FuncDao dao = new FuncDao();


	public FuncCore() {
		super.setLogger(logger);
		super.setDao(dao);
		super.setOname(oname);
	}
	
	/**
	 * ��ѯ������Ϣ
	 * @param conditions ��ѯ����
	 * @return JSONArrayʵ��
	 */
	public JSONArray select(Map<String,Object> conditions){
		Object[][] objects = dao.select(conditions);
		return Json.toJson(objects, dao.getFields());
	}
	
	/**
	 * ��ȡ�ڵ����
	 * @param id �ڵ���
	 * @return �ڵ���⼯��
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
	 * ��ȡ��־λ��������
	 * @param id ���
	 * @param mark ��־ֵ
	 * @return ��־λ��������
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
	 * ��ȡ���ڵ���
	 * @param id �������
	 * @param mark ��־ֵ
	 * @return ���ڵ��б�
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
	 * ������ת�����ַ���
	 * @param list ��ת������
	 * @return �����ַ���
	 */
	public static String toString(List<String> list){
		String str = list.toString();
		str = str.replace("[", "").replace("]", "");
		str = str.replaceAll(", ", ",");
		return str;
	}
	
}
