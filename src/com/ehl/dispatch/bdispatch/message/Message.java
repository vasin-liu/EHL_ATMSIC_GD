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
 * <b>��Ϣ����.</b>
 */
public class Message 
{
	public static MessageThread messageThread = null;
	
	/**
	 * <b>������Ϣ.</b>
	 * 
	 * @param Socket
	 *            socket Socketͨ��
	 * @param String
	 *            sendMsg ������Ϣ����
	 */
	public static void send(Socket socket, String sendMsg) {
		if (socket != null) 
		{
			try {
				 
				// ������Ϣ
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(sendMsg);
				out.flush();
			}
			catch (Exception e) 
			{
				System.out.println("��Ϣ����ʧ�ܣ�" + e.getMessage());
			}
			finally
			{
			}
		}
	}

	/**
	 * <b>���ӵ���Ϣ������.</b>
	 * 
	 * @param String
	 *            serverIp ��Ϣ������IP.
	 * @param int
	 *            serverPort ��Ϣ�������˿ں�.
	 * @param Class
	 *            triggerClass ������Ϣ��
	 * @param String
	 *            triggerClassMethod ������Ϣ�෽����
	 * @return Socket����.
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
				System.out.println("���ӵ���Ϣ������ʧ�ܣ�");
			}

		} catch (Exception e) {
			System.out.println("���ӵ���Ϣ����������" + e.getMessage());
		} finally {

		}

		return socket;
	}

	/**
	 * <b>�Ͽ�����Ϣ������������.</b>
	 * 
	 * @param Socket
	 *            socket ��Ϣ����������.
	 * @return
	 */
	public static void disConnectToServer(Socket socket) {
		try {
			
//			messageThread.setDeath(true);
			socket.close();
			socket = null;
		} catch (Exception e) {
			System.out.println("�Ͽ���Ϣ����������" + e.getMessage());
		} finally {

		}
	}

	/**
	 * <b>XML����.</b>
	 * 
	 * @param String
	 *            xmlStr XML�ַ���
	 * @param String
	 *            group �������
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