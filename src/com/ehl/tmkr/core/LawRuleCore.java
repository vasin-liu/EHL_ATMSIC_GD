package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.LawRuleDao;

public class LawRuleCore {
	/**
	 * ��ӷ��ɷ�����Ϣ
	 * @param order ���ɷ�����Ϣ
	 * @return �������
	 */
	public static String add(Map<String, String> order){
		return LawRuleDao.insert(order);
	}
	
	/**
	 * ��ȡ���ɷ�����Ϣ
	 * @param id �������
	 * @return ���ɷ�����Ϣ
	 */
	public static String get(String id) {
		String xml = null;
		Object[] lawRule = LawRuleDao.getById(id);
		if(lawRule != null){
			xml = XML.getNode("lawRule", LawRuleDao.fnames, lawRule, null);
		}
		xml = XML.getXML(xml);
		return xml;
	}
	
	/**
	 * �޸ķ��ɷ�����Ϣ
	 * @param lawRule ���ɷ�����Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> lawRule) {
		return LawRuleDao.modify(lawRule);
	}
	
	/**
	 * ɾ�����ɷ�����Ϣ<br/>
	 * @param id �������
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public static boolean delete(String id) {
		return LawRuleDao.delete(id);
	}
}
