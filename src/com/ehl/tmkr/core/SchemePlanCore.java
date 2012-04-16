package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.SchemePlanDao;

public class SchemePlanCore {
	/**
	 * 添加预案方案管理信息
	 * @param order 预案方案管理信息
	 * @return 主键编号
	 */
	public static String add(Map<String, String> order){
		return SchemePlanDao.insert(order);
	}
	
	/**
	 * 获取预案方案管理信息
	 * @param id 主键编号
	 * @return 预案方案管理信息
	 */
	public static String get(String id) {
		String orderXml = null;
		Object[] order = SchemePlanDao.getById(id);
		if(order != null){
			orderXml = XML.getNode("plan", SchemePlanDao.fnames, order, null);
		}
		orderXml = XML.getXML(orderXml);
		return orderXml;
	}
	
	/**
	 * 修改预案方案管理信息
	 * @param order 预案方案管理信息
	 * @param ajgids 接收单位信息
	 * @return 是否修改成功
	 */
	public static boolean modify(Map<String, String> order) {
		return SchemePlanDao.modify(order);
	}
	
	public static void main(String[] args) {
		
	}
}
