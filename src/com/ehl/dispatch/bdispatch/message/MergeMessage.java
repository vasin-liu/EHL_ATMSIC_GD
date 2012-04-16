package com.ehl.dispatch.bdispatch.message;

import java.net.Socket;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：MergeMessage.java
 * 
 * 摘 要：合并警情消息发送类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-17
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class MergeMessage
{
	//获取消息字符串
	private static String getMessageString(String mainAffairId,String subAffairIDs)
	{
		StringBuffer xmlMessage = new StringBuffer("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
		xmlMessage.append("<message System=\"ATMS\" Ver=\"1.0\">");
		xmlMessage.append("<systemtype>SYSTEM</systemtype>");   
		xmlMessage.append("<messagetype>NOTICE</messagetype>");
		xmlMessage.append("<sourceIP></sourceIP>");
		xmlMessage.append("<targetIP></targetIP>");
		xmlMessage.append("<user></user>");
		xmlMessage.append("<password></password>");
		xmlMessage.append("<notice>");
		xmlMessage.append("<maineventid>" + mainAffairId + "</maineventid>");
		xmlMessage.append("<subeventids>"); 
		String []subAffairArray = subAffairIDs.split(",");
		for(int i=0;i<subAffairArray.length;i++)
		{
			xmlMessage.append("<subeventid>" + subAffairArray[i] + "</subeventid>");
		}
		xmlMessage.append("</subeventids>");
		xmlMessage.append("<detail>警情合单消息通知</detail>");
		xmlMessage.append("</notice>");
		xmlMessage.append("</message>");
		
		return xmlMessage.toString();
	}
	
	/**发送合并信息
	 * 
	 * @param mainAffairId 主事件标识
	 * @param subAffairIDs 分事件标识串，以逗号隔开
	 */
	public static void sendMergeMessage(String mainAffairId,String subAffairIDs)
	{
		Socket socket = null;
		String serverIp = com.appframe.common.Setting.getString("msgServiceIp");
		String serverPort = com.appframe.common.Setting.getString("msgServicePort");
		try 
		{
			socket = Message.connectToServer(serverIp, Integer.parseInt(serverPort), null, null);
			Message.send(socket, getMessageString(mainAffairId,subAffairIDs)); 
		}
		catch (Exception e) 
		{

		} 
		finally 
		{
			if(null != socket)
			{
				Message.disConnectToServer(socket);
			}
		}
	}
}
