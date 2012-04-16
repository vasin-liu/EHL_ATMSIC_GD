package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.WorkRuleDao;

public class WorkRuleCore {
	/**
	 * ��ӹ����涨��Ϣ
	 * @param workRule �����涨��Ϣ
	 * @return �������
	 */
	public static String add(Map<String, String> workRule){
		return WorkRuleDao.insert(workRule);
	}
	
	/**
	 * ��ȡ�����涨��Ϣ
	 * @param id �������
	 * @return �����涨��Ϣ
	 */
	public static String get(String id) {
		String xml = null;
		Object[] workRule = WorkRuleDao.getById(id);
		if(workRule != null){
			xml = XML.getNode("workRule", WorkRuleDao.fnames, workRule, null);
		}
		xml = XML.getXML(xml);
		return xml;
	}
	
	/**
	 * �޸Ĺ����涨��Ϣ
	 * @param workRule �����涨��Ϣ
	 * @param ajgids ���յ�λ��Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> workRule) {
		return WorkRuleDao.modify(workRule);
	}
	
	public static void main(String[] args) {
		
	}
}
