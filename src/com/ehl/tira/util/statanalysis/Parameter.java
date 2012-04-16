package com.ehl.tira.util.statanalysis;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * �������߳Ʋ�����֤
 * 
 * @author xiayx
 * @date 2010-12-23
 */
public class Parameter {

	/*
	 * ����ֵ ��������
	 */
	private static Logger logger = Logger.getLogger(Parameter.class);

	public final static Object checkObj = null;
	public final static String checkStr = "";
	public final static int checkArray = 0;

	/**
	 * �Ƿ�ǿ�(��Ϊnull)����
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonObj(Object param) {
		if (param == checkObj) {
			logger.error("�������Ϊnull");
			return false;
		}
		return true;
	}

	/**
	 * �Ƿ�ǿ�(��Ϊ"")�ַ���
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonStr(String param) {
		if (isNonObj(param) == false) {
			return false;
		}
		if (param.equals(checkStr)) {
			logger.error("�ַ�������Ϊ\"\"");
			return false;
		}
		return true;
	}

	/**
	 * �Ƿ�ǿ�(���Ȳ�Ϊ0)����
	 * 
	 * @param param
	 * @return
	 */
	public final static boolean isNonArray(Object[] param) {
		if (isNonObj(param) == false) {
			return false;
		}
		if (param.length == checkArray) {
			logger.error("�����������Ϊ0");
			return false;
		}
		return true;
	}

	/**
	 * �Ƿ�ǿ�(���ȴ���0)������û�зǿ�(Ϊnull)Ԫ�ص�����
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
				logger.error("��������д���Ϊnull��Ԫ��");
				return false;
			}
		}
		return true;
	}

	/**
	 * �Ƿ�ǿ�(���ȴ���0)Map
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
	 * �Ƿ�ǿ�(���ȴ���0)������û�зǿ�(Ϊnull)Ԫ�ص�Map
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
