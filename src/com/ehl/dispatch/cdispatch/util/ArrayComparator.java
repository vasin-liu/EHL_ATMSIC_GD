package com.ehl.dispatch.cdispatch.util;

import java.util.Comparator;

import com.appframe.utils.StringHelper;
/**
 * 数组比较器
 * @author luhaifu
 *
 */
public class ArrayComparator implements Comparator{
	public int compare(Object o1, Object o2) {
		Object[] a = (Object[])o1;
		Object[] b = (Object[])o2;
		if(StringHelper.obj2int(a[a.length-1], 0)<StringHelper.obj2int(b[a.length-1], 0)){
			return 1;
		}else if(StringHelper.obj2int(a[a.length-1], 0)>StringHelper.obj2int(b[a.length-1], 0)){
			return -1;
		}else{
			return 0;
		}
	}
	
}
