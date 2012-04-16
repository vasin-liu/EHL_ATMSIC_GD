package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.ElseDao;

public class ElseCore {
	/**
	 * ���֪ʶ��������Ϣ
	 * @param elseInfo ֪ʶ��������Ϣ
	 * @return �������
	 */
	public static String add(Map<String, String> elseInfo){
		return ElseDao.insert(elseInfo);
	}
	
	/**
	 * ��ȡ֪ʶ��������Ϣ
	 * @param id �������
	 * @return ֪ʶ��������Ϣ
	 */
	public static String get(String id) {
		String xml = null;
		Object[] elseInfo = ElseDao.getById(id);
		if(elseInfo != null){
			xml = XML.getNode("else", ElseDao.fnames, elseInfo, null);
		}
		xml = XML.getXML(xml);
		return xml;
	}
	
	/**
	 * �޸�֪ʶ��������Ϣ
	 * @param elseInfo ֪ʶ��������Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modify(Map<String, String> elseInfo) {
		return ElseDao.modify(elseInfo);
	}
	
}
