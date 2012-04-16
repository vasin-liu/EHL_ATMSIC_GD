package com.ehl.drpt.common;

import com.appframe.utils.StringHelper;

public class CommonXML {

	/**
	 * @作者: 吴玉良
	 * @版本号：1.0
	 * @函数说明：解析一个Object[],生成可供前台解析的XML
	 * @参数：Object[]:一维数组
	 * @返回：String xmlStr:XML串
	 * @创建日期：2009-08-10
	 * @修改者：
	 * @修改日期：
	 */
	public static String getXML(Object[] obj){
		StringBuffer xmlData = new StringBuffer(
		"<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
		
		if (obj != null){
			for (int j = 0; j < obj.length; j++) {
				if(obj[j] == null || "".equals(obj[j].toString().trim())){
					xmlData.append("<cell>　</cell>");
				}else{
					xmlData.append("<cell>" + StringHelper.obj2str(obj[j]) + "</cell>");
				}
			}
		}
		
		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}
	
	/**
	 * @作者: 吴玉良
	 * @版本号：1.0
	 * @函数说明：解析一个String生成可供前台解析的XML
	 * @参数：str:返回信息
	 * @返回：String xmlStr:XML串
	 * @创建日期：2009-08-10
	 * @修改者：
	 * @修改日期：
	 */
	public static String getXML(String str){
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
		
		if (str == null || "".equals(str.trim())) {
			xmlData.append("<cell>　</cell>");
		} else {
			xmlData.append("<cell>" + str + "</cell>");
		}

		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}
	
	/**
	 * @作者: 吴玉良
	 * @版本号：1.0
	 * @函数说明：解析一个Object[][],生成可供前台解析的XML
	 * @参数：Object[][]:二维数组
	 * @返回：String xmlStr:XML串
	 * @创建日期：2009-08-10
	 * @修改者：
	 * @修改日期：
	 */
	public static String getXML(Object[][] obj) {
		StringBuffer xmlData = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		if (obj != null) {

			for (int i = 0; i < obj.length; i++) {
				xmlData.append("<row>\n");
				for (int j = 0; j < obj[i].length; j++) {
					if(obj[i][j]==null ||"".equals(obj[i][j].toString().trim())){
						xmlData.append("<cell>　</cell>");
					}else{
						xmlData.append("<cell>" + StringHelper.obj2str(obj[i][j])
								+ "</cell>");
					}
				}
				xmlData.append("\n</row>\n");
			}
		}
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		return xmlData.toString();
	}
}
