package com.ehl.dispatch.bdispatch.business;

import java.net.Socket;

import com.appframe.common.Setting;
import com.ehl.sm.common.message.Message;

;

/**
 * 新增警情发生消息类
 * 
 *  @author JC
 */
public class AlarmInfoMsg {

	static String serverIp = Setting.getString("msgServiceIp");

	static int serverPort = Setting.getInt("msgServicePort");

	static Class alarm = AlarmInfoMsg.class;

	public static void SendAlarmMsg(Object[] res) {
		Socket socket=null;
		try {
			
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version='1.0' encoding='gb2312'?>\n");

			xml.append("<message System='ATMS' Ver='1.0'>\n");
			xml.append("<systemtype>SYSTEM</systemtype>\n");
			xml.append("<messagetype>ALARM</messagetype>\n");
			xml.append("<sourceIP></sourceIP>\n");
			xml.append("<targetIP></targetIP>\n");
			xml.append("<user></user>\n");
			xml.append("<password></password>\n");

			xml.append("<alarm>\n");
			xml.append("<alarmtype>下发警情单消息通知</alarmtype>\n");
			xml.append("<eventid>"+ res[0] +"</eventid>\n");
			xml.append("<eventtype>"+ res[5] +"</eventtype>\n");
			xml.append("<eventsource>"+ res[4] +"</eventsource>\n");
			xml.append("<eventthintype>"+ res[7] +"</eventthintype>\n");
			xml.append("<eventlevel>"+ res[6] +"</eventlevel>\n");
			xml.append("<eventstate>"+ res[8] +"</eventstate>\n");
			xml.append("<eventtitle>"+ res[19] +"</eventtitle>\n");
			xml.append("<eventtime>"+ res[1] +"</eventtime>\n");
			xml.append("<eventdesc>"+ res[9] +"</eventdesc>\n");
			xml.append("<eventunit>"+ res[3] +"</eventunit>\n");
			xml.append("<comeoutunit>"+ res[3] +"</comeoutunit>\n");
			xml.append("<detail></detail>\n");
			xml.append("</alarm>\n");
			xml.append("</message>\n");
			
			System.out.println("send message ----------->" + xml.toString());
			socket = Message.connectToServer(serverIp, serverPort,
					alarm, "backmsg");
			Message.send(socket, xml.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			 Message.disConnectToServer(socket);
		}
	}

	public static void backmsg(String msg) {
		Object[][] oResult = Message.parseXML(msg, "/message/alarm");

		System.out.println("\n" + msg);
	}

//	public static void main(String[] args) {
//		SendAlarmMsg();
//	}
}
