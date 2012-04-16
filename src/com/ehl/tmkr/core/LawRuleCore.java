package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.LawRuleDao;

public class LawRuleCore {
	/**
	 * 添加法律法规信息
	 * @param order 法律法规信息
	 * @return 主键编号
	 */
	public static String add(Map<String, String> order){
		return LawRuleDao.insert(order);
	}
	
	/**
	 * 获取法律法规信息
	 * @param id 主键编号
	 * @return 法律法规信息
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
	 * 修改法律法规信息
	 * @param lawRule 法律法规信息
	 * @return 是否修改成功
	 */
	public static boolean modify(Map<String, String> lawRule) {
		return LawRuleDao.modify(lawRule);
	}
	
	/**
	 * 删除法律法规信息<br/>
	 * @param id 主键编号
	 * @return 是否删除成功
	 */
	public static boolean delete(String id) {
		return LawRuleDao.delete(id);
	}
}
