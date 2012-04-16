package com.ehl.dispatch.bdispatch.message;

import java.net.Socket;

/**
 * <b>消息测试类.</b>
 */
public class MessageTest {

	/**
	 * <b>消息测试</b>
	 */
	public static void main(String args[]) {
		String serverIp = "192.168.1.12";
		int serverPort = 8836;
		Class triggerClass = MessageTest.class;
		String triggerClassMethod = "msgReceive";
		StringBuffer subData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		subData.append("<subscription System='ATMS' Ver='1.0'>");
		subData.append("<systemtype>SYS</systemtype>");
		subData.append("<messagetype>ALARM</messagetype>");
		subData.append("<sourceIP></sourceIP>");
		subData.append("<targetIP></targetIP>");
		subData.append("<user></user>");
		subData.append("<password></password>");
		subData.append("</subscription>");

		StringBuffer msgData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		msgData.append("<message System='ATMS' Ver='1.0'>");
		msgData.append("<systemtype>SYSTEM</systemtype>");
		msgData.append("<messagetype>ALARM</messagetype>");
		msgData.append("<sourceIP></sourceIP>");
		msgData.append("<targetIP></targetIP>");
		msgData.append("<user></user>");
		msgData.append("<password></password>");
		msgData.append("<alarm>2</alarm>");
		msgData.append("</message>");
		
//		System.err.print(subData.toString());
		System.err.print(msgData.toString());
		Socket socket = null;
		try {
			socket = Message.connectToServer(serverIp, serverPort, triggerClass, triggerClassMethod);
			Message.send(socket, msgData.toString());
//			Message.send(socket, subData.toString());
		} catch (Exception e) {

		} finally {
			 Message.disConnectToServer(socket);
		}
	}

	/**
	 * <b>接收消息方法实例.</b>
	 * 
	 * @param msg
	 *            消息内容
	 */
	public static void msgReceive(String msg) {
		Object[][] oResult = Message.parseXML(msg, "/message/alarm");
		
		System.out.println("\n" + msg);
	}
}