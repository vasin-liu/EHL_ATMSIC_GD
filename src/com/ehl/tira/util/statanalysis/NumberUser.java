package com.ehl.tira.util.statanalysis;

public class NumberUser {

	public static String roundFloat(String floatStr) {
		return removeZero(floatStr);
	}

	/**
	 * ȥ���������ַ���С�����������С����
	 * 
	 * @param floatStr
	 *            �������ַ���
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
	 * �Ե����ȸ�����������������<br>
	 * ������λ��Ч��ֵ
	 * @param num �����ȸ�����
	 * @param round ��Ч��ֵλ��
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
