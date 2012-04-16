package com.ehl.util;

import java.util.Arrays;

import com.appframe.utils.StringHelper;

/**
 * <pre>
 * 数组类
 * </pre>
 * 
 * @author xiayx
 * @date 2011-09-05
 * 
 */
public class Array {

	/**
	 * 获取数组中某个值的索引
	 * 
	 * @param array
	 *            数据
	 * @param value
	 *            值
	 * @return 索引。-1:数组中不存在指定值；-2:数组为null；-3:值为null
	 */
	public static int getIndex(Object[] array, Object value) {
		if (array == null || value == null) {
			if (array == null) {
				return -2;
			}
			return -3;
		}
		for (int i = 0; i < array.length; i++) {
			if (value.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 将数组以指定分隔符拼接起来
	 * 
	 * @param strs
	 *            数组
	 * @param sep
	 *            分隔符
	 * @return 拼接起来的字符串
	 */
	public static String join(String[] strs, String sep) {
		String joinStr = null;
		if (strs != null && strs.length > 0 && sep != null) {
			joinStr = "";
			for (int i = 0; i < strs.length; i++) {
				joinStr += sep + strs[i];
			}
			joinStr = joinStr.substring(sep.length());
		}
		return joinStr;
	}

	/**
	 * 在数组的指定索引前插入值
	 * 
	 * @param array
	 *            数组
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 * @return 插入值后的新数组
	 */
	public static String[] insert(Object[] array, int index, Object value) {
		String[] newArray = null;
		if (array != null && index >= 0 && index <= array.length) {
			newArray = new String[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, index);
			newArray[index] = String.valueOf(value);
			if (index != array.length) {
				System.arraycopy(array, index, newArray, index + 1,
						array.length - index);
			}
		}
		return newArray;
	}
	
	/**
	 * 将数组中指定索引处的值插入到另一个索引前
	 * @param array 数组
	 * @param fromIndex 起始索引
	 * @param toIndex 目的索引
	 */
	public static void insert(Object[] array, int fromIndex, int toIndex){
		if (array == null || fromIndex < 0 || toIndex < 0
				|| fromIndex > array.length || toIndex > array.length
				|| fromIndex == toIndex) {
			return;
		}
		Object from = array[fromIndex];
		if(fromIndex < toIndex){
			for (int i = fromIndex + 1; i <= toIndex; i++) {
				movePrivate(array, i, i - 1);
			}
		}else{
			for (int i = fromIndex - 1; i >= toIndex; i--) {
				movePrivate(array, i, i + 1);
			}
		}
		array[toIndex] = from;
	}

	/**
	 * 删除数据中指定索引处的值
	 * 
	 * @param array
	 *            数组
	 * @param index
	 *            索引
	 * @return 删除值后的数组
	 */
	public static Object[] delete(Object[] array, int index) {
		Object[] newArray = null;
		if (array != null && index >= 0 && index < array.length) {
			newArray = new Object[array.length - 1];
			System.arraycopy(array, 0, newArray, 0, index);
			System.arraycopy(array, index + 1, newArray, index, array.length
					- index - 1);
		}
		return newArray;
	}
	
	/**
	 * 移动数组中的元素
	 * @param array 数组
	 * @param fromIndex 移动出发索引
	 * @param toIndex 移动目的索引
	 */
	public static void move(Object[] array, int fromIndex, int toIndex) {
		if (array == null || fromIndex < 0 || toIndex < 0
				|| fromIndex > array.length || toIndex > array.length
				|| fromIndex == toIndex) {
			return;
		}
		movePrivate(array, fromIndex, toIndex);
	}
	
	private static void movePrivate(Object[] array, int fromIndex, int toIndex) {
		Object from = array[fromIndex];
		array[fromIndex] = array[toIndex];
		array[toIndex] = from;
	}
	
	/**
	 * 按照数组中指定索引处的值进行排序<br>
	 * 只能非负数字排序
	 * @param data 数组
	 * @param index 索引
	 * @param isAsc 是否升序
	 */
	public static void sort(Object[][] data, int index, boolean isAsc) {
		if (data == null || data.length == 0 || index < 0 || index > data[0].length - 1) {
			return;
		}
		float current, compare;
		for (int i = 0; i < data.length; i++) {
			current = toFloat(data[i][index]);
			for (int j = 0; j < i; j++) {
				compare = toFloat(data[j][index]);
				if ((isAsc && current < compare)
						|| (!isAsc && current > compare)) {
					insert(data, i, j);
					break;
				}
			}
		}
	}
	
	/**
	 * 按照数组中指定索引处的值进行排序<br>
	 * 只能非负数字排序
	 * @param data 数组
	 * @param index 索引
	 * @param isAsc 是否升序
	 */
	public static void sort2(Object[][] data, int index, boolean isAsc) {
		if (data == null || data.length == 0 || index < 0 || index > data[0].length - 1) {
			return;
		}
		int current, compare;
		for (int i = 0; i < data.length; i++) {
			current = StringHelper.obj2int(data[i][index], 0);
			for (int j = 0; j < i; j++) {
				compare = StringHelper.obj2int(data[j][index], 0);
				if ((isAsc && current < compare)
						|| (!isAsc && current > compare)) {
					insert(data, i, j);
					break;
				}
			}
		}
	}
	
	/**
	 * 将对象转换成浮点数
	 * @param obj 对象
	 * @return 浮点数
	 */
	private static float toFloat(Object obj){
		if(obj == null || !(obj instanceof String)){
			System.err.println("转换浮点数异常！");
			return -2;
		}
		float objFloat;
		try {
			objFloat = Float.parseFloat((String)obj);
		} catch (NumberFormatException e) {
			objFloat = -1;
		}
		return objFloat;
	}
	
	public static int[][] getCounts(Object[][] data, int i1, Object[] i1s,
			int i2, Object[] i2s, int i3) {
		if (i1s == null || i2s == null || i1 < 0 || i2 < 0 || i3 < 0) {
			return null;
		}
		int[][] counts = new int[i1s.length][i2s.length];
		if (data != null) {
			int i1v, i2v, i3v;
			for (int i = 0; i < data.length; i++) {
				if (data[i] != null && data[i].length > i1
						&& data[i].length > i2) {
					i1v = getIndex(i1s, data[i][i1]);
					i2v = getIndex(i2s, data[i][i2]);
					i3v = StringHelper.obj2int(data[i][i3], 0);
					if (i1v >= 0 && i2v >= 0) {
						counts[i1v][i2v] = i3v;
					}
				}
			}
		}
		return counts;
	}
	
	public static int[][] getCounts(Object[][] data, Object[] i1s, Object[] i2s) {
		return getCounts(data, 0, i1s, 1, i2s, 2);
	}
	
	/**
	 * 将数组转换为字符串
	 * @param array 数组
	 * @return 数组字符串表示
	 */
	public static String toString(Object[][] array) {
		if (array == null) {
			return "null";
		}
		StringBuffer show = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			show.append("," + Arrays.toString(array[i]));
		}
		if (show.length() != 0) {
			show.deleteCharAt(0);
		}
		return "[" + show.toString() + "]";
	}
	
	/**
	 * 将数组转换为字符串
	 * @param array 数组
	 * @return 数组字符串表示
	 */
	public static String toString(int[][] array) {
		if (array == null) {
			return "null";
		}
		StringBuffer show = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			show.append("," + Arrays.toString(array[i]));
		}
		if (show.length() != 0) {
			show.deleteCharAt(0);
		}
		return "[" + show.toString() + "]";
	}
	
	/**
	 * 获取两个数组的交集、差集<br>
	 * 交集放在数组前面，差集放在数组后面
	 * @param array1 数组1
	 * @param array2 数组2
	 * @return 交集和差集的分界索引
	 */
	public static int getSameAndDifferSet(Object[] array1, Object[] array2) {
		if (array1 == null || array2 == null || array1.length <= 0
				|| array2.length <= 0) {
			return -1;
		}
		int l1 = array1.length;
		int l2 = array2.length;
		boolean isFinded = false;
		Object temp;
		for (int i = 0; i < l1;) {
			isFinded = false;
			for (int j = i; j < l2; j++) {
				if ((array1[i] == null && array1[i] == array2[j])
						|| array1[i].equals(array2[j])) {
					temp = array2[j];
					array2[j] = array2[i];
					array2[i] = temp;
					isFinded = true;
					break;
				}
			}
			if (isFinded) {
				i++;
			} else {
				temp = array1[i];
				array1[i] = array1[--l1];
				array1[l1] = temp;
			}
		}
		return l1;
	}
	

	public static void testGetCounts() {
		Object[][] data = { { "11.1", "21", "1" }, { "11.2", "22", "1", "3" },
				{ "11.0", "23", "1" },{ "10", "23", "1" } };
		// Object[] i1s = {"11","12","13"};
		// Object[] i2s = {"21","22","23"};
		System.out.println(toString(data));
		sort(data, 0, true); 
		System.out.println(toString(data));
	}

	public static void main(String[] args) {
	testGetCounts();
		//String abc = "1000000000000000000000000000000000000000000000000000000000";
		
		//System.out.println(Float.parseFloat(abc));
	}
}
