package com.ehl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Collections {
	
	/**
	 * ƴ�ӵ�����
	 * @param iterator ������
	 * @param sep �ָ�����Ϊnull���׳��쳣
	 * @return ƴ���ַ���
	 */
	public static String join(Iterator<?> iterator, Object sep) {
		StringBuffer str = new StringBuffer();
		if (iterator.hasNext()) {
			str.append(iterator.next());
		}
		while (iterator.hasNext()) {
			str.append(sep.toString() + iterator.next());
		}
		return str.toString();
	}
	
	/**
	 * �Զ���ƴ�ӵ�����
	 * @param iterator ������
	 * @return ƴ���ַ���
	 */
	public static String join(Iterator<?> iterator) {
		return join(iterator, ",");
	}
	
	/**
	 * ƴ�Ӽ�ֵ�Լ���
	 * @param map ��ֵ�Լ���
	 * @param kv ����ֵ֮��ķָ���
	 * @param item �����֮��ķָ���
	 * @return ƴ���ַ���
	 */
	public static String join(Map<?, ?> map, Object kv, Object item) {
		StringBuffer str = new StringBuffer();
		Iterator<?> iterator = map.keySet().iterator();
		Object key;
		if (iterator.hasNext()) {
			key = iterator.next();
			str.append(key.toString() + kv.toString() + map.get(key));
		}
		while (iterator.hasNext()) {
			key = iterator.next();
			str.append(item.toString() + key + kv.toString() + map.get(key));
		}
		return str.toString();
	}
	
	/**
	 * <pre>
	 * ת����ֵ���е������飨������ֻ��һ��Ԫ�أ�ֵΪ�����е�Ԫ��
	 * ���ڴ���request.getParameterMap()���صļ�ֵ��
	 * </pre>
	 * @param map ��ת����ֵ��
	 * @return ת����ļ�ֵ��
	 */
	public static Map<String,Object> changeSingleArray(Map<?,?> map){
		Map<String,Object> reMap = new HashMap<String,Object>();
		Iterator<?> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Object value = map.get(key);
			if(value instanceof Object[] ){
				Object[] values = (Object[])value;
				if(values.length == 1){
					value = values[0];
				}
			}
			reMap.put((String)key, value);
		}
		return reMap;
	}
	
	public static void main(String[] args) {
		
	}
}
