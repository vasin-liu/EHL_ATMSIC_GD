package com.ehl.tira.util.statanalysis;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 参数或者称参数验证
 * 
 * @author xiayx
 * @date 2010-12-23
 */
public class Parameter {

	/*
	 * 参数值 参数内容
	 */
	private static Logger logger = Logger.getLogger(Parameter.class);

	public final static Object checkObj = null;
	public final static String checkStr = "";
	public final static int checkArray = 0;

	/**
	 * 是否非空(不为null)对象
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonObj(Object param) {
		if (param == checkObj) {
			logger.error("对象参数为null");
			return false;
		}
		return true;
	}

	/**
	 * 是否非空(不为"")字符串
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonStr(String param) {
		if (isNonObj(param) == false) {
			return false;
		}
		if (param.equals(checkStr)) {
			logger.error("字符串参数为\"\"");
			return false;
		}
		return true;
	}

	/**
	 * 是否非空(长度不为0)数组
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonArray(Object[] param) {
		if (isNonObj(param) == false) {
			return false;
		}
		if (param.length == checkArray) {
			logger.error("数组参数长度为0");
			return false;
		}
		return true;
	}

	/**
	 * 是否非空(长度大于0)，并且没有非空(为null)元素的数组
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonArrayWithNonObj(Object[] param) {
		if (isNonArray(param) == false) {
			return false;
		}
		for (int i = 0; i < param.length; i++) {
			if (isNonObj(param[i]) == false) {
				logger.error("数组参数中存在为null的元素");
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否非空(长度大于0)Map
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static boolean isNonMap(Map param) {
		if (isNonObj(param) == false) {
			return false;
		}
		
		if (param.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 是否非空(长度大于0)，并且没有非空(为null)元素的Map
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static boolean isNonMapWithNonObj(Map param) {
		if (isNonMap(param) == false) {
			return false;
		}
		
		Iterator ite = param.keySet().iterator();
		Object key = null;
		Object value = null;
		while (ite.hasNext()) {
			key = ite.next();
			value = param.get(key);
			if (isNonObj(value) == false) {
				return false;
			}
		}
		
		return true;
	}

	
	

}
