package com.ehl.tira.util;



/**
 * 辅助处理xml格式字符串
 * @author xiayx
 * @date 2010-12-13
 */
public class XML {
	
	/*
	1.XML文档
	2.头
	2.根节点
	2.节点
	3.属性
	3.1.属性名称
	3.2.属性值
	4.文本
	5.标签
	5.1.标签名称
	5.2.起始标签
	5.3.结束标签
	
	XML特殊字符
	<转化成&lt;
	>转化成&gt;
	‘转化成&apos;
	“转化成&quot;
	&转化成&amp;
	或者<![CDATA[]]>
	*/
	
	/**xml元素起始标签起始部分*/
	public final static String sms = "<";
	/**xml元素起始标签结束部分*/
	public final static String sme = ">";
	/**xml元素结束标签起始部分*/
	public final static String ems = "</";
	/**xml元素结束标签结束部分*/
	public final static String eme = ">";
	/**xml列级元素标签名称*/
	public final static String col = "col";
	/**xml行级元素标签名称*/
	public final static String row = "row";
	/**xml组件级元素标签名称*/
	public final static String com = "com";
	/**xml文档根节点*/
	public final static String head = "<?xml version='1.0' encoding='utf-8' ?>\n";
	
	/**
	 * 转换到CDATA内容
	 * @param content 内容
	 * @return CDATA内容
	 */
	public static String toCDATA(String content){
		 return "<![CDATA["+content+"]]>";
	}
	
	/**
	 * 过滤内容
	 * 
	 * @param content
	 *            内容
	 * @param specialChars
	 *            特殊字符
	 * @return 过滤后内容
	 */
	public static String filterContent(String content, String[] specialChars) {
		if(content == null || specialChars == null){
			return content;
		}
		for (int i = 0; i < specialChars.length; i++) {
			if(content.indexOf(specialChars[i]) != -1 ){
				content = toCDATA(content);
				break;
			}
		}
		return content;
	}
	
	/**
	 * 过滤内容
	 * 
	 * @param content
	 *            内容
	 * @return 过滤后内容
	 */
	public static String filterContent(String content) {
		return filterContent(content, new String[]{sme,"&"});
	}
	
	/**
	 * 过滤内容
	 * @param contentObj 内容
	 * @return 处理后的内容
	 */
	public static String filterContent(Object contentObj){
		String content;
		if(contentObj == null){
			content = "";
		}else{
			if(contentObj instanceof String){
				content = (String)contentObj;
			}else{
				content = "";
			}
		}
		return content;
	}
	
	/**
	 * 得到XML格式字符串
	 * @param content 包含内容
	 * @return XML格式字符串
	 */
	public static String getXML(String content) {
		return getXML("xml",content);
	}
	
	/**
	 * 得到XML格式字符串
	 * @param mname 名称
	 * @param content 包含内容
	 * @return XML格式字符串
	 */
	public static String getXML(String mname, String content) {
		return head + encapContent(mname, content);
	}
	
	/**
	 * 以标签封装内容
	 * @param mname 标签名称
	 * @param content 内容
	 * @return 
	 */
	public static String encapContent(String mname, Object content) {
		mname = mname == null ? col : mname;
		content = filterContent(content);
		if(mname.equals(col)){
			content = filterContent((String)content);
		}
		return sms + mname + sme + content + ems + mname + eme + "\n";
	}
	
	/**
	 * 以标签封装内容
	 * @param mname 标签名称
	 * @param contents 内容
	 * @return 
	 */
	public static String encapContent(String mname, Object[] contents) {
		String content;
		if (contents != null) {
			content = "";
			for (int i = 0; i < contents.length; i++) {
				content += encapContent(mname, contents[i]);
			}
		}else{
			content = encapContent(mname, "");
		}
		return content;
	}
	
	/**
	 * 以标签封装内容
	 * @param remark1 标签
	 * @param contents 内容
	 * @return 
	 */
	public static String encapContent(String remark1, String remark2,
			Object[] contents) {
		remark1 = remark1 == null ? row : remark1;
		return encapContent(remark1, encapContent(remark2, contents));
	}
	
	/**
	 * 以标签封装内容
	 * @param remark1 标签
	 * @param contents 内容
	 * @return 
	 */
	public static String encapContent(String remark1, String remark2,
			Object[][] contents) {
		String content;
		if (contents != null) {
			content = "";
			for (int i = 0; i < contents.length; i++) {
				content += encapContent(remark1, remark2, contents[i]);
			}
		}else{
			content = encapContent(remark1, encapContent(remark2, ""));
		}
		return content;
	}
	
	/**
	 * 以标签封装内容
	 * @param remark2 标签
	 * @param contents 内容
	 * @return 
	 */
	public static String encapContent(String remark1, String remark2,
			String remark3, Object[][] contents) {
		remark1 = remark1 == null ? com : remark1;
		return encapContent(remark1, encapContent(remark2, remark3, contents));
	}
	
	/**
	 * 获取起始标签
	 * @param mname 标签名称
	 * @param attrs 属性
	 * @return 起始标签
	 */
	public static String getSMark(String mname, String attrs) {
		String remark = null;
		if (mname != null) {
			attrs = attrs == null ? "" : attrs;
			remark = sms + mname + " " + attrs + " " + sme;
		}
		return remark;
	}
	
	/**
	 * 获取起始标签
	 * @param mname 标签名称
	 * @return 起始标签
	 */
	public static String getSMark(String mname) {
		return getSMark(mname, "");
	}
	
	/**
	 * 获取结束标签
	 * @param mname 标签名称
	 * @return 结束标签
	 */
	public static String getEMark(String mname){
		String remark = null;
		if(mname != null){
			remark = ems + mname + eme;
		}
		return remark;
	}
	
	/**
	 * 获取单个属性
	 * @param attrName 属性名称
	 * @param attrValue 属性值
	 * @return 属性
	 */
	public static String getAttr(String attrName, Object attrValue){
		String attr = null;
		if (attrName != null && !attrName.equals("")) {
			String attrValueStr = filterContent(attrValue);
			attrValueStr = attrValueStr.replace("<", "&lt;");
			attrValueStr = attrValueStr.replace("'", "&#39;");
			attr = " " + attrName + "='" + attrValueStr + "' ";
		}
		return attr;
	}
	
	/**
	 * 获取多个属性
	 * @param attrNames 属性名称
	 * @param attrValues 属性值
	 * @return 属性
	 */
	public static String getAttrs(String[] attrNames, Object[] attrValues) {
		String attrs = null;
		if (attrNames != null && attrValues != null) {
			attrs = "";
			int cnLength = attrNames.length;
			int diLength = attrValues.length;
			String attr;
			for (int i = 0; i < cnLength && i < diLength; i++) {
				attr = getAttr(attrNames[i], attrValues[i]);
				if (attr != null) {
					attrs += attr;
				}
			}
			attrs = attrs.equals("") ? null : attrs;
		}
		return attrs;
	}
	
	/**
	 * 获取单个节点，带内容
	 * @param mname 标签名称
	 * @param attrs 属性
	 * @param content 内容
	 * @return xml元素
	 */
	public static String getNode(String mname, String attrs,
			String content) {
		String smark = getSMark(mname, attrs);
		String emark = getEMark(mname);
		if (smark != null && emark != null) {
			if(content == null){
				smark = smark.substring(0,smark.lastIndexOf(sme));
				smark += "/" + sme;
				content = smark;
			}else{
				content = smark + content + emark;
			}
		}
		return content;
	}
	
	/**
	 * 获取单个节点，带内容
	 * @param mname 标签名称
	 * @param attrs 属性
	 * @param content 内容
	 * @return xml元素
	 */
	public static String getNode(String mname, String[] attrNames, Object[] attrValues, String content){
		return getNode(mname, getAttrs(attrNames, attrValues), content);
	}
	
	/**
	 * 获取多个节点，带内容
	 * @param mname 标签名称
	 * @param attrs 属性
	 * @param content 内容
	 * @return xml元素
	 */
	public static String getNodes(String mname, String[] attrNames, Object[][] attrValuess){
		String nodes = null;
		if(attrValuess != null){
			nodes = "";
			String node;
			for (int i = 0; i < attrValuess.length; i++) {
				node = getNode(mname, attrNames, attrValuess[i],null);
				if(node != null){
					nodes += node;
				}
			}
		}
		return nodes;
	}
	
	public static void main(String[] args) {
		String mname = "mname";
		String[] attrNames = {"qq"};
		String[] attrValues = {"value"};
		String node = getNode(mname, attrNames, attrValues, null);
		System.out.println("node:"+node);
		String test = "aa<notice>bb";
		System.out.println(filterContent(test));
		
	}
	
	
}
