package com.ehl.tira.util;

/**
 * ��ȡ��������sql���
 * 
 * @author xiayx
 * @date 2010-12-19
 */
public class Department {

	public final static int s = 1;// ��ʼλ
	public final static String lm = "00";// �ȼ���־
	
	// ��ȡָ��������Χ�ڵ����ݣ������û��������������ӻ���
	public static String getArea(String col, String jgid) {
		int level = (jgid + lm).indexOf(lm);
		return "substr(" + col + ",1," + level + ")='"
				+ (jgid + lm).substring(0, level) + "'";
	}

	// ��ȡһ��������䣬�����û��������������ӻ���������
	// ���磺���ܶӷ��࣬�������ܶ����ݡ�֧�����ݺʹ������
	// gb:2->�ܶӣ�4->֧�ӣ�6->���
	public static String getGB(String col, int gb) {
		return "rpad(substr(" + col + ",1," + gb + "),12,'0')";
	}

	// ɸѡ���ܶӡ�֧�ӡ����
	// gb:2->�ܶӣ�4->֧�ӣ�6->���
	public static String getWhere(String col, int gb) {
		return "substr(" + col + "," + (gb - 1) + ",2) != '00' and substr("
				+ col + "," + (gb + 1) + ",2) = '00'";
	}

	/**
	 * ȡ�û����ȼ���2���ܶӣ�4��֧�ӣ�6�����<br>
	 * ����00����λ���ж�
	 * 
	 * @param jgid
	 * @return
	 */
	public static int getLevelValue(String jgid) {
		return (jgid + lm).indexOf(lm);
	}

	/**
	 * ȡ�û�ȡ�����ȼ��ĺ����ַ�����-1��2��4��6ƥ��<br>
	 * �����������
	 * 
	 * @param col
	 * @return
	 */
	public static String getLevelCol(String col) {
		return "(instr(" + col + ",'" + lm + "')-1)";
	}
	
	/**
	 * �ų���ǰ����
	 * 
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String removeCurrent(String col, String jgid) {
		return col + " != '" + jgid + "'";
	}

	/**
	 * ɸѡ���û����͸û�����һ���ӻ���<br>
	 * 
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String getChild(String col, String jgid) {
		int level = getLevelValue(jgid);
		return "substr(" + col + "," + s + "," + level + ")||'" + lm
				+ "'||substr(" + col + "," + (s + level + lm.length())
				+ ") = '" + jgid + "'";
	}

	public static String getParent(String jgid) {
		int level = getLevelValue(jgid);// instr(jgid,'00')-1
		return "substr(" + jgid + "," + s + "," + (level - lm.length())
				+ ")||'" + lm + "'||substr(" + jgid + "," + (s + level) + ")";
	}

	public static String getParentValue(String jgid) {
		String parent = null;
		int level = getLevelValue(jgid);
		if (jgid != null && level >= lm.length()) {
			parent = jgid.substring(0, level - lm.length()) + lm
					+ jgid.substring(level);
		}
		return parent;
	}

	public static String getParents(String jgid) {
		int level = getLevelValue(jgid);// 6-2
		return "substr(jgid," + (level - lm.length()) + "," + lm.length() + ")";
	}

	public static String getParentCol(String col) {
		String level = getLevelCol(col);
		return "substr(" + col + "," + s + ",(" + level + "-" + lm.length()
				+ "))||'" + lm + "'||substr(" + col + "," + s + "+" + level
				+ ")";
	}
	
	
	
	
	
	
	
	
	
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
	
	
	
	public static String toString(String jgid){
		return "'" + jgid + "'";
	}
	
	public static String[] toString(String[] array){
		if(array != null){
			for (int i = 0; i < array.length; i++) {
				array[i] = toString(array[i]);
			}
		}
		return array;
	}
	
	/**
	 * �滻
	 * @param ori
	 * @param start
	 * @param length
	 * @param newstr
	 * @return
	 */
	public static String replace(String ori, int start, int length,
			String newstr) {
		String replace = null;
		if (ori != null && start >= 0 && length >= 0
				&& start + length <= ori.length()) {
			replace = ori.substring(0, start);
			replace += newstr;
			replace += ori.substring(start + length);
		}
		return replace;
	}
	
	/**
	 * ɸѡ
	 * @param col
	 * @param value
	 * @return
	 */
	public static String sift(String col, String value) {
		return col + " = '" + value + "'";
	}
	
	public static String siftin(String col, String value){
		return col + " in (" + value + ")";
	}
	
	public static String siftJg(String col){
		return col + " < 446000000000"; 
	}
	
	//public static String siftSelf
	/**
	 * �����ȼ�
	 * @param jgid
	 * @return
	 */
	public static int levelv(String jgid) {
		return (jgid + lm).indexOf(lm);
	}
	
	/**
	 * �����ȼ�
	 * @param col
	 * @return
	 */
	public static String levelc(String col) {
		return "(instr(" + col + "," + lm + ") - 1)";
	}
	
	/**
	 * ������
	 * @param jgid
	 * @return
	 */
	public static String parentv(String jgid) {
		return replace(jgid, levelv(jgid) - lm.length(), lm.length(), lm);
	}
	
	/**
	 * ������<br>
	 * ѭ��ȡ��������
	 * @param jgid
	 * @return
	 */
	public static String[] parentsv(String jgid){
		String[] parents = null;
		int parentsNum = levelv(jgid)/lm.length()-1;
		if(parentsNum > 0){
			parents = new String[parentsNum];
			parents[0] = parentv(jgid);
			for (int i = 1; i < parents.length; i++) {
				parents[i] = parentv(parents[i-1]);
			}
		}
		return parents;
	}
	
	/**
	 * ������
	 * @param col
	 * @return
	 */
	public static String parentc(String col){
		String level = levelc(col);
		String parent = "substr(" + col + "," + s + ",(" + level + "-"
				+ lm.length() + "))";
		parent += "||'" + lm + "'";
		parent += "||substr(" + col + "," + s + "+" + level + ")";
		return parent;
	}
	
	/**
	 * ɸѡ������<br>
	 * �б����jgid�����и�������
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String siftParents(String col, String jgid){
		return siftin(col, join(toString(parentsv(jgid)), ","));
	}
	
	/**
	 * ɸѡ�Ǹ�����<br>
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String siftUnParents(String col, String jgid){
		int level = levelv(jgid);
		return "substr("+col+","+s+","+level+")="+jgid.substring(0, level);
	}
	
	/**
	 * ɸѡ�ӻ���<br>
	 * ��һ��
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String siftChild(String col, String jgid){
		int level = levelv(jgid);
		String child = "substr(" + col + "," + s + "," + level + ")";
		child += "||'" + lm + "'";
		level = s + level + lm.length();
		child += "||substr(" + col + "," + level + ","
				+ (jgid.length() - level + 1) + ")='" + jgid + "'";
		child += " and " + col + "!='"+jgid+"'";
		return child;
	}
	
	/**
	 * ɸѡ�ӻ���<br>
	 * �����ӻ���
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String siftChilds(String col, String jgid){
		return siftUnParents(col, jgid) + " and " + col + "!=" + jgid;
	}
	
	
	
	

	public static void main(String[] args) {
		String area = getParentCol("jgid");
		String parent = parentv("440101");
		String replace = replace("440101", 1, 5, "��--");
		String siftParent = siftParents("jgid", "44010101");
		String siftUnParent = siftChild("jgid","440000");
		System.out.println(siftUnParent);
	}

}
