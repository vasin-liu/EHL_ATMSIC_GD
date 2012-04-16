package com.ehl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Collections {
	
	/**
	 * 拼接迭代器
	 * @param iterator 迭代器
	 * @param sep 分隔符，为null会抛出异常
	 * @return 拼接字符串
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
	 * 以逗号拼接迭代器
	 * @param iterator 迭代器
	 * @return 拼接字符串
	 */
	public static String join(Iterator<?> iterator) {
		return join(iterator, ",");
	}
	
	/**
	 * 拼接键值对集合
	 * @param map 键值对集合
	 * @param kv 键和值之间的分隔符
	 * @param item 项和项之间的分隔符
	 * @return 拼接字符串
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
	 * 转换键值对中单个数组（数组中只有一个元素）值为数组中的元素
	 * 用于处理request.getParameterMap()返回的键值对
	 * </pre>
	 * @param map 欲转换键值对
	 * @return 转换后的键值对
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
