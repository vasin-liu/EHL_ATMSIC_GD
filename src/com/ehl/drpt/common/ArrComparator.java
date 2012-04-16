package com.ehl.drpt.common;

import java.util.Comparator;

import com.appframe.utils.StringHelper;
/**
 * @======================================================================================================================================
 * @类型说明:数组比较器
 * @创建者：luhaifu
 * @创建日期 2009-04-17
 * @======================================================================================================================================
 */
public class ArrComparator implements Comparator{
	public int compare(Object o1, Object o2) {
		Object[] a = (Object[])o1;
		Object[] b = (Object[])o2;
		if(StringHelper.obj2int(a[a.length-2], 0)<StringHelper.obj2int(b[a.length-2], 0)){
			return 1;
		}else if(StringHelper.obj2int(a[a.length-2], 0)>StringHelper.obj2int(b[a.length-2], 0)){
			return -1;
		}else{
			return 0;
		}
	}
	
}
