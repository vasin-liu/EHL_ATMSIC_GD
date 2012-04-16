package com.ehl.tira.util;



/**
 * ��������xml��ʽ�ַ���
 * @author xiayx
 * @date 2010-12-13
 */
public class XML {
	
	/*
	1.XML�ĵ�
	2.ͷ
	2.���ڵ�
	2.�ڵ�
	3.����
	3.1.��������
	3.2.����ֵ
	4.�ı�
	5.��ǩ
	5.1.��ǩ����
	5.2.��ʼ��ǩ
	5.3.������ǩ
	
	XML�����ַ�
	<ת����&lt;
	>ת����&gt;
	��ת����&apos;
	��ת����&quot;
	&ת����&amp;
	����<![CDATA[]]>
	*/
	
	/**xmlԪ����ʼ��ǩ��ʼ����*/
	public final static String sms = "<";
	/**xmlԪ����ʼ��ǩ��������*/
	public final static String sme = ">";
	/**xmlԪ�ؽ�����ǩ��ʼ����*/
	public final static String ems = "</";
	/**xmlԪ�ؽ�����ǩ��������*/
	public final static String eme = ">";
	/**xml�м�Ԫ�ر�ǩ����*/
	public final static String col = "col";
	/**xml�м�Ԫ�ر�ǩ����*/
	public final static String row = "row";
	/**xml�����Ԫ�ر�ǩ����*/
	public final static String com = "com";
	/**xml�ĵ����ڵ�*/
	public final static String head = "<?xml version='1.0' encoding='utf-8' ?>\n";
	
	/**
	 * ת����CDATA����
	 * @param content ����
	 * @return CDATA����
	 */
	public static String toCDATA(String content){
		 return "<![CDATA["+content+"]]>";
	}
	
	/**
	 * ��������
	 * 
	 * @param content
	 *            ����
	 * @param specialChars
	 *            �����ַ�
	 * @return ���˺�����
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
	 * ��������
	 * 
	 * @param content
	 *            ����
	 * @return ���˺�����
	 */
	public static String filterContent(String content) {
		return filterContent(content, new String[]{sme,"&"});
	}
	
	/**
	 * ��������
	 * @param contentObj ����
	 * @return ����������
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
	 * �õ�XML��ʽ�ַ���
	 * @param content ��������
	 * @return XML��ʽ�ַ���
	 */
	public static String getXML(String content) {
		return getXML("xml",content);
	}
	
	/**
	 * �õ�XML��ʽ�ַ���
	 * @param mname ����
	 * @param content ��������
	 * @return XML��ʽ�ַ���
	 */
	public static String getXML(String mname, String content) {
		return head + encapContent(mname, content);
	}
	
	/**
	 * �Ա�ǩ��װ����
	 * @param mname ��ǩ����
	 * @param content ����
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
	 * �Ա�ǩ��װ����
	 * @param mname ��ǩ����
	 * @param contents ����
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
	 * �Ա�ǩ��װ����
	 * @param remark1 ��ǩ
	 * @param contents ����
	 * @return 
	 */
	public static String encapContent(String remark1, String remark2,
			Object[] contents) {
		remark1 = remark1 == null ? row : remark1;
		return encapContent(remark1, encapContent(remark2, contents));
	}
	
	/**
	 * �Ա�ǩ��װ����
	 * @param remark1 ��ǩ
	 * @param contents ����
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
	 * �Ա�ǩ��װ����
	 * @param remark2 ��ǩ
	 * @param contents ����
	 * @return 
	 */
	public static String encapContent(String remark1, String remark2,
			String remark3, Object[][] contents) {
		remark1 = remark1 == null ? com : remark1;
		return encapContent(remark1, encapContent(remark2, remark3, contents));
	}
	
	/**
	 * ��ȡ��ʼ��ǩ
	 * @param mname ��ǩ����
	 * @param attrs ����
	 * @return ��ʼ��ǩ
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
	 * ��ȡ��ʼ��ǩ
	 * @param mname ��ǩ����
	 * @return ��ʼ��ǩ
	 */
	public static String getSMark(String mname) {
		return getSMark(mname, "");
	}
	
	/**
	 * ��ȡ������ǩ
	 * @param mname ��ǩ����
	 * @return ������ǩ
	 */
	public static String getEMark(String mname){
		String remark = null;
		if(mname != null){
			remark = ems + mname + eme;
		}
		return remark;
	}
	
	/**
	 * ��ȡ��������
	 * @param attrName ��������
	 * @param attrValue ����ֵ
	 * @return ����
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
	 * ��ȡ�������
	 * @param attrNames ��������
	 * @param attrValues ����ֵ
	 * @return ����
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
	 * ��ȡ�����ڵ㣬������
	 * @param mname ��ǩ����
	 * @param attrs ����
	 * @param content ����
	 * @return xmlԪ��
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
	 * ��ȡ�����ڵ㣬������
	 * @param mname ��ǩ����
	 * @param attrs ����
	 * @param content ����
	 * @return xmlԪ��
	 */
	public static String getNode(String mname, String[] attrNames, Object[] attrValues, String content){
		return getNode(mname, getAttrs(attrNames, attrValues), content);
	}
	
	/**
	 * ��ȡ����ڵ㣬������
	 * @param mname ��ǩ����
	 * @param attrs ����
	 * @param content ����
	 * @return xmlԪ��
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
