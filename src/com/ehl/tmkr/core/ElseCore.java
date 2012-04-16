package com.ehl.tmkr.core;

import java.util.Map;

import com.ehl.tira.util.XML;
import com.ehl.tmkr.dao.ElseDao;

public class ElseCore {
	/**
	 * 添加知识库其他信息
	 * @param elseInfo 知识库其他信息
	 * @return 主键编号
	 */
	public static String add(Map<String, String> elseInfo){
		return ElseDao.insert(elseInfo);
	}
	
	/**
	 * 获取知识库其他信息
	 * @param id 主键编号
	 * @return 知识库其他信息
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
	 * 修改知识库其他信息
	 * @param elseInfo 知识库其他信息
	 * @return 是否修改成功
	 */
	public static boolean modify(Map<String, String> elseInfo) {
		return ElseDao.modify(elseInfo);
	}
	
}
