package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.SchemePlanDao;

public class SchemePlanCore {
	/**
	 * ���Ԥ������������Ϣ
	 * @param order Ԥ������������Ϣ
	 * @return �������
	 */
	public static String add(Map<String, String> order){
		return SchemePlanDao.insert(order);
	}
	
	/**
	 * ��ȡԤ������������Ϣ
	 * @param id �������
	 * @return Ԥ������������Ϣ
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
	 * �޸�Ԥ������������Ϣ
	 * @param order Ԥ������������Ϣ
	 * @param ajgids ���յ�λ��Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> order) {
		return SchemePlanDao.modify(order);
	}
	
	public static void main(String[] args) {
		
	}
}
