package com.ehl.dispatch.bdispatch.util;

import com.appframe.utils.StringHelper;
import java.util.Map;
 
/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：XmlDataCreator.java
 * 
 * 摘 要：构造数据信息的xml信息串。
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-9
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class XmlDataCreator
{	
	/**
	 * @author LChQ 2008-11-28
	 * <b>生成XML文档</b>
	 * 
	 * @param originString
	 * @return
	 */
	public static String buildXmlDocument(Map<String,Object> mapData)
	{
		StringBuilder xmlDoc = new StringBuilder();
		xmlDoc.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlDoc.append("<rfXML>\n");
		xmlDoc.append("<RFWin>\n");
		
		if(null != mapData.keySet())
		{
			//添加数据
			Object []dataType = mapData.keySet().toArray();
			for(int i=0; i<mapData.size(); i++)
			{
				Object data = mapData.get(dataType[i]);
				if(null != buildXmlRows( (Object[][])data, dataType[i].toString()))
				{
					xmlDoc.append(buildXmlRows( (Object[][])data, dataType[i].toString()));
				}
			}
		}
		
		xmlDoc.append("</RFWin>\n");
		xmlDoc.append("</rfXML>\n");
		return xmlDoc.toString();
	}
	
	
	/**
	 * @author LChQ 2008-11-28
	 * <b>生成XML文档</b>
	 * 
	 * @param originString
	 * @return
	 */
	public static String buildXmlDocument(Object[][]rowData, String rowName) 
	{
		StringBuilder xmlDoc = new StringBuilder();
		xmlDoc.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlDoc.append("<rfXML>\n");
		xmlDoc.append("<RFWin>\n");
		
		if( null == rowName || "".equals(rowName))
		{
			return null;
		}
		
		String rowXML = buildXmlRows(rowData,rowName);
		if(null != rowXML )
		{
			xmlDoc.append(rowXML);
		}
		xmlDoc.append("</RFWin>\n");
		xmlDoc.append("</rfXML>\n");
		return xmlDoc.toString();
	}
	
	
	/**
	 * @author LChQ 2008-10-18
	 * <b>生成XML文档</b>
	 * 
	 * @param 
	 * @return
	 */
	public static String buildXmlDocument(String rowData, String rowName) 
	{
		StringBuilder xmlDoc = new StringBuilder();
		xmlDoc.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlDoc.append("<rfXML>\n");
		xmlDoc.append("<RFWin>\n");
		
		if( null == rowName || "".equals(rowName))
		{
			return null;
		}
		
		String rowXML ="<" + rowName +">";
		rowXML += rowData;
		rowXML += "</"+ rowName +">\n";
		
		if(null != rowXML )
		{
			xmlDoc.append(rowXML);
		}
		xmlDoc.append("</RFWin>\n");
		xmlDoc.append("</rfXML>\n");
		return xmlDoc.toString();
	}
	
	/**
	 * @author LChQ 2008-10-18
	 * <b>生成XML行</b>
	 * 
	 * @param originString
	 * @return
	 */
	public static String buildXmlRows(Object[][]rowData, String rowName) 
	{
		StringBuilder xmlRow = new StringBuilder();
		if(null ==  rowData || null == rowName || "".equals(rowName))
		{
			return null;
		}
		
		for(int i=0; i< rowData.length;i++)
		{
			xmlRow.append("<" + rowName +"  id='");
			xmlRow.append(i);
			xmlRow.append("'>");
			for (int j = 0; j < rowData[i].length; j++) 
			{
				xmlRow.append("<col>");
				xmlRow.append(StringHelper.obj2str(rowData[i][j],""));
				xmlRow.append("</col>");
			}
			xmlRow.append("</"+ rowName +">\n");
		}
		return xmlRow.toString();
	}
}