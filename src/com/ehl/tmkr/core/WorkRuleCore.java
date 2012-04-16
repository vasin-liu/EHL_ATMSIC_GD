package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.WorkRuleDao;

public class WorkRuleCore {
	/**
	 * 添加工作规定信息
	 * @param workRule 工作规定信息
	 * @return 主键编号
	 */
	public static String add(Map<String, String> workRule){
		return WorkRuleDao.insert(workRule);
	}
	
	/**
	 * 获取工作规定信息
	 * @param id 主键编号
	 * @return 工作规定信息
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
	 * 修改工作规定信息
	 * @param workRule 工作规定信息
	 * @param ajgids 接收单位信息
	 * @return 是否修改成功
	 */
	public static boolean modify(Map<String, String> workRule) {
		return WorkRuleDao.modify(workRule);
	}
	
	public static void main(String[] args) {
		
	}
}
