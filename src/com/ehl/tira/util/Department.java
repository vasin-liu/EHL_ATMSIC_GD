package com.ehl.tira.util;

/**
 * 获取与机关相关sql语句
 * 
 * @author xiayx
 * @date 2010-12-19
 */
public class Department {

	public final static int s = 1;// 起始位
	public final static String lm = "00";// 等级标志
	
	// 获取指定机构范围内的数据，包括该机构、其下所有子机构
	public static String getArea(String col, String jgid) {
		int level = (jgid + lm).indexOf(lm);
		return "substr(" + col + ",1," + level + ")='"
				+ (jgid + lm).substring(0, level) + "'";
	}

	// 获取一个分组语句，包括该机构、其下所有子机构的数据
	// 例如：按总队分类，将包括总队数据、支队数据和大队数据
	// gb:2->总队；4->支队；6->大队
	public static String getGB(String col, int gb) {
		return "rpad(substr(" + col + ",1," + gb + "),12,'0')";
	}

	// 筛选出总队、支队、大队
	// gb:2->总队；4->支队；6->大队
	public static String getWhere(String col, int gb) {
		return "substr(" + col + "," + (gb - 1) + ",2) != '00' and substr("
				+ col + "," + (gb + 1) + ",2) = '00'";
	}

	/**
	 * 取得机构等级，2：总队；4：支队；6：大队<br>
	 * 根据00所在位置判定
	 * 
	 * @param jgid
	 * @return
	 */
	public static int getLevelValue(String jgid) {
		return (jgid + lm).indexOf(lm);
	}

	/**
	 * 取得获取机构等级的函数字符串并-1与2、4、6匹配<br>
	 * 根据列名获得
	 * 
	 * @param col
	 * @return
	 */
	public static String getLevelCol(String col) {
		return "(instr(" + col + ",'" + lm + "')-1)";
	}
	
	/**
	 * 排除当前机构
	 * 
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String removeCurrent(String col, String jgid) {
		return col + " != '" + jgid + "'";
	}

	/**
	 * 筛选出该机构和该机构下一级子机构<br>
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
	 * 替换
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
	 * 筛选
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
	 * 机构等级
	 * @param jgid
	 * @return
	 */
	public static int levelv(String jgid) {
		return (jgid + lm).indexOf(lm);
	}
	
	/**
	 * 机构等级
	 * @param col
	 * @return
	 */
	public static String levelc(String col) {
		return "(instr(" + col + "," + lm + ") - 1)";
	}
	
	/**
	 * 父机构
	 * @param jgid
	 * @return
	 */
	public static String parentv(String jgid) {
		return replace(jgid, levelv(jgid) - lm.length(), lm.length(), lm);
	}
	
	/**
	 * 父机构<br>
	 * 循环取出父机构
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
	 * 父机构
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
	 * 筛选父机构<br>
	 * 列编号在jgid的所有父机构中
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String siftParents(String col, String jgid){
		return siftin(col, join(toString(parentsv(jgid)), ","));
	}
	
	/**
	 * 筛选非父机构<br>
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String siftUnParents(String col, String jgid){
		int level = levelv(jgid);
		return "substr("+col+","+s+","+level+")="+jgid.substring(0, level);
	}
	
	/**
	 * 筛选子机构<br>
	 * 子一级
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
	 * 筛选子机构<br>
	 * 所有子机构
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
		String replace = replace("440101", 1, 5, "我--");
		String siftParent = siftParents("jgid", "44010101");
		String siftUnParent = siftChild("jgid","440000");
		System.out.println(siftUnParent);
	}

}
