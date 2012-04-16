package com.ehl.dispatch.cdispatch.core;

import java.util.List;

import com.appframe.utils.StringHelper;

/**
 * @author wxt
 *
 */
public class DataToXML {
	
	/**
	 * ˽�л���ֹ��ʼ������
	 */
    private DataToXML() {
    	
    }
    
	/**
	 * һ������ת��XML���� 
	 * @param list
	 * @return
	 */
	public static String ListDataToXml(List<String> list) {
		StringBuffer xmlRow = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlRow.append("<rfXML>\n");
		xmlRow.append("<RFWin>\n");
		xmlRow.append("<row>");
		if(list!=null&&list.size()>0) {
				for(int i=0;i<list.size();i++) {
				xmlRow.append("<cell>");
				xmlRow.append(list.get(i));
				xmlRow.append("</cell>");
			}
		}else {
			xmlRow.append("<cell></cell>");
			
		}
		xmlRow.append("</row>");
		xmlRow.append("</RFWin>\n");
		xmlRow.append("</rfXML>\n");
		return xmlRow.toString();
	}
	
	/**
	 * һά����ת��Ϊxml
	 * @param һά����
	 * @return
	 */
	public static String objArrayToXml(Object res[]){
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
				
		if (res != null){
			for (int j = 0; j < res.length; j++) {
				if(res[j] == null || "".equals(res[j].toString().trim())){
					xmlData.append("<col>��</col>");
				}else{
					xmlData.append("<col>" + StringHelper.obj2str(res[j]) + "</col>");
				}
			}
		}
		
		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}
	
	/**
	 * һά����ת��Ϊxml
	 * @param һά����
	 * @return
	 */
	public static String objArrayToXml2(Object res[]){
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
				
		if (res != null && res.length>0){
			for (int j = 0; j < res.length; j++) {
				xmlData.append("<col>");
				xmlData.append(StringHelper.obj2str(res[j],""));
				xmlData.append("</col>");
			}
		}
		
		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}
	
	/**
	 * ��ά����ת��Ϊxml
	 * @param ��ά����
	 * @return
	 */
	public static String objArrayToXml(Object res[][]){
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		if (res != null) {
		
			for (int i = 0; i < res.length; i++) {
				xmlData.append("<row>\n");
				for (int j = 0; j < res[i].length; j++) {
					if(res[i][j]==null ||"".equals(res[i][j].toString().trim())){
						xmlData.append("<col>��</col>");
					}else{
						xmlData.append("<col>" + StringHelper.obj2str(res[i][j])
								+ "</col>");
					}
				}
				xmlData.append("\n</row>\n");
			}
		}
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}
	
	/**
	 * ��ά����ת��Ϊxml
	 * @param res,��ά����;elementsName �ڵ�����
	 * @return
	 */
	public static String objArrayToXml(Object res[][],String elementsName){
		StringBuffer xmlRow = new StringBuffer("");
		
		if(res != null && res.length > 0) {
			for(int i = 0;i < res.length;i++) {
				xmlRow.append("<" + elementsName + ">");
				for(int j = 0;j < res[i].length;j++){
					xmlRow.append("<col>");
					xmlRow.append(StringHelper.obj2str(res[i][j],""));
					xmlRow.append("</col>");
				}
				xmlRow.append("</" + elementsName + ">");
			}
		}
		return xmlRow.toString();
	}
}
