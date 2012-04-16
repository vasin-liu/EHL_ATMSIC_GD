package com.ehl.tira.util.statanalysis;

public class NumberUser {

	public static String roundFloat(String floatStr) {
		return removeZero(floatStr);
	}

	/**
	 * 去除浮点型字符串小数点后带的零和小数点
	 * 
	 * @param floatStr
	 *            浮点型字符串
	 * @return
	 */
	public static String removeZero(String floatStr) {
		if (Parameter.isNonStr(floatStr) == false) {
			return null;
		}
		int index = floatStr.indexOf('.');
		if (index == -1) {
			return floatStr;
		} else {
			for (int i = index + 1; i < floatStr.length(); i++) {
				if (floatStr.charAt(i) != '0') {
					return floatStr;
				}
			}
			return floatStr.substring(0, index);
		}
	}
	
	/**
	 * 对单精度浮点数进行四舍五入<br>
	 * 保留几位有效数值
	 * @param num 单精度浮点数
	 * @param round 有效数值位数
	 * @return
	 */
	public static String roundFloat(float num, int round) {
		String numStr = String.valueOf(num);
		int pointIndex = numStr.indexOf('.');
		int roundIndex = pointIndex + round + 1;
		if (numStr.length() > roundIndex){
			int roundNum = Integer.parseInt(numStr.substring(roundIndex,roundIndex+1));
			num = Float.parseFloat(numStr.substring(0,roundIndex));
			if(roundNum >= 5){
				float add = 1;
				for(int i = 0; i < round; i++){
					add /= 10;
				}
				num += add;
			}
		}else {
			int length = roundIndex - numStr.length();
			for(int i = 0; i < length; i++){
				numStr += "0";
			}
			return removeZero(numStr);
		}
		return removeZero(String.valueOf(num));
	}
	
	public static void main(String[] args) {
		System.out.println(roundFloat(1.234f,5));
	}
}
