package com.ehl.dispatch.bdispatch.message;

import java.io.PrintWriter;
import java.net.Socket;

import com.appframe.utils.xml.XmlBuilder;
import com.appframe.utils.xml.XmlDocument;
import com.appframe.utils.xml.XmlFactory;
import com.appframe.utils.xml.XmlNode;
import java.lang.Runnable;
import java.lang.Thread; 

/**
 * <b>消息主类.</b>
 */
public class Message 
{
	public static MessageThread messageThread = null;
	
	/**
	 * <b>发送消息.</b>
	 * 
	 * @param Socket
	 *            socket Socket通道
	 * @param String
	 *            sendMsg 发送消息内容
	 */
	public static void send(Socket socket, String sendMsg) {
		if (socket != null) 
		{
			try {
				 
				// 发送消息
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(sendMsg);
				out.flush();
			}
			catch (Exception e) 
			{
				System.out.println("消息发送失败：" + e.getMessage());
			}
			finally
			{
			}
		}
	}

	/**
	 * <b>连接到消息服务器.</b>
	 * 
	 * @param String
	 *            serverIp 消息服务器IP.
	 * @param int
	 *            serverPort 消息服务器端口号.
	 * @param Class
	 *            triggerClass 触发消息类
	 * @param String
	 *            triggerClassMethod 触发消息类方法名
	 * @return Socket连接.
	 */
	public static Socket connectToServer(String serverIp, int serverPort, Class triggerClass, String triggerClassMethod ) {
		Socket socket = null;
		try {
			socket = new Socket(serverIp, serverPort);
			
			if (socket != null) 
			{
				messageThread = new MessageThread(socket, triggerClass, triggerClassMethod);
				messageThread.start();
			} else {
				System.out.println("连接到消息服务器失败！");
			}

		} catch (Exception e) {
			System.out.println("连接到消息服务器错误：" + e.getMessage());
		} finally {

		}

		return socket;
	}

	/**
	 * <b>断开到消息服务器的连接.</b>
	 * 
	 * @param Socket
	 *            socket 消息服务器连接.
	 * @return
	 */
	public static void disConnectToServer(Socket socket) {
		try {
			
//			messageThread.setDeath(true);
			socket.close();
			socket = null;
		} catch (Exception e) {
			System.out.println("断开消息服务器错误：" + e.getMessage());
		} finally {

		}
	}

	/**
	 * <b>XML解析.</b>
	 * 
	 * @param String
	 *            xmlStr XML字符串
	 * @param String
	 *            group 解析结点
	 * @return
	 */
	public static Object[][] parseXML(String xmlStr, String node) {
		Object[][] results = null;
		try {
			XmlBuilder xmlbuilder = XmlFactory.createXmlBuilder();
			XmlDocument xmldocument = xmlbuilder.buildDocument(xmlStr);

			XmlNode xmlNode[] = xmldocument.getNodesByXPath(node);
			if (xmlNode.length != 0) {
				results = new Object[xmlNode.length][];
				for (int i = 0; i < xmlNode.length; i++) {
					if (xmlNode[i] == null) {
						continue;
					}
					XmlNode xmlNodeChild[] = xmlNode[i].getChildren();
					if (xmlNodeChild == null) {
						continue;
					}
					Object[] tempRes = new Object[xmlNodeChild.length];
					for (int j = 0; j < xmlNodeChild.length; j++) {
						tempRes[j] = xmlNodeChild[j].getContent();
					}
					results[i] = tempRes;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return results;
	}
	
}