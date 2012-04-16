package com.ehl.dispatch.bdispatch.message;

import java.net.Socket;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�MergeMessage.java
 * 
 * ժ Ҫ���ϲ�������Ϣ�����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-17
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class MergeMessage
{
	//��ȡ��Ϣ�ַ���
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
		xmlMessage.append("<detail>����ϵ���Ϣ֪ͨ</detail>");
		xmlMessage.append("</notice>");
		xmlMessage.append("</message>");
		
		return xmlMessage.toString();
	}
	
	/**���ͺϲ���Ϣ
	 * 
	 * @param mainAffairId ���¼���ʶ
	 * @param subAffairIDs ���¼���ʶ�����Զ��Ÿ���
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
