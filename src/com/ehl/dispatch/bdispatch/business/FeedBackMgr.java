package com.ehl.dispatch.bdispatch.business;

import java.net.Socket;

import com.appframe.common.Setting;
import com.ehl.sm.common.message.Message;

public class FeedBackMgr {
	static String serverIp = Setting.getString("msgServiceIp");
	static int serverPort = Setting.getInt("msgServicePort");

	static Class fedBackMgr = FeedBackMgr.class;
	
	public static void SendFeedBackMsg(String alarmId,String state) {
		Socket socket=null;
		try {
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version='1.0' encoding='gb2312'?>\n");

			xml.append("<message System='ATMS' Ver='1.0'>\n");
			xml.append("<systemtype>SYSTEM</systemtype>\n");
			xml.append("<messagetype>NOTICE</messagetype>\n");
			xml.append("<sourceIP></sourceIP>\n");
			xml.append("<targetIP></targetIP>\n");
			xml.append("<user></user>\n");
			xml.append("<password></password>\n");

			xml.append("<notice>\n");
			xml.append("<alarmid>" + alarmId + "</alarmid>\n");
			xml.append("<state>" + state + "</state>\n");
			xml.append("<detail>DS反馈消息</detail>\n");
			xml.append("</notice>\n");

			xml.append("</message>\n");
			System.out.println(xml.toString());
			socket = Message.connectToServer(serverIp, serverPort, fedBackMgr, "backmsg");
			Message.send(socket, xml.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			 Message.disConnectToServer(socket);
		}
	}
	public static void SendFeedBackMsg(String alarmId,String eventsource ,String eventtype,String eventunit,String state,String time) {
		Socket socket=null;
		try {
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version='1.0' encoding='gb2312'?>\n");

			xml.append("<message System='ATMS' Ver='1.0'>\n");
			xml.append("<systemtype>SYSTEM</systemtype>\n");
			xml.append("<messagetype>NOTICE</messagetype>\n");
			xml.append("<sourceIP></sourceIP>\n");
			xml.append("<targetIP></targetIP>\n");
			xml.append("<user></user>\n");
			xml.append("<password></password>\n");
			
			xml.append("<notice>\n");
			xml.append("<eventid>" + alarmId + "</eventid>\n");
			xml.append("<eventsource>" + eventsource + "</eventsource>\n");
			xml.append("<eventtype>" + eventtype + "</eventtype>\n");
			xml.append("<eventunit>" +eventunit + "</eventunit>\n");
			xml.append("<state>" + state +"</state>\n");
			xml.append("<time>" + time + "</time>\n");
			xml.append("<detail>警情处理消息通知</detail>\n");
			xml.append("</notice>\n");

			xml.append("</message>\n");

			socket = Message.connectToServer(serverIp, serverPort, fedBackMgr, "backmsg");
			Message.send(socket, xml.toString());
			System.out.println(xml.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			 Message.disConnectToServer(socket);
		}
	}

	
	public static void SendDisposeMsg(String alarmId,String eventsource ,String eventtype,String eventunit,String state,String time,String attemper,String title,String eventlevel,String isquit,String persons,String cars) {
		Socket socket=null;
		try {
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version='1.0' encoding='gb2312'?>\n");

			xml.append("<message System='ATMS' Ver='1.0'>\n");
			xml.append("<systemtype>SYSTEM</systemtype>\n");
			xml.append("<messagetype>NOTICE</messagetype>\n");
			xml.append("<sourceIP></sourceIP>\n");
			xml.append("<targetIP></targetIP>\n");
			xml.append("<user></user>\n");
			xml.append("<password></password>\n");
			
			xml.append("<notice>\n");
			xml.append("<eventid>" + alarmId + "</eventid>\n");
			xml.append("<eventsource>" + eventsource + "</eventsource>\n");
			xml.append("<eventtype>" + eventtype + "</eventtype>\n");
			xml.append("<eventunit>" +eventunit + "</eventunit>\n");
			xml.append("<Attemper>" +attemper + "</Attemper>\n");
			xml.append("<state>" + state +"</state>\n");
			xml.append("<time>" + time + "</time>\n");
			xml.append("<Title>" + title + "</Title>\n");
			xml.append("<EventLevel>" + eventlevel + "</EventLevel>\n");
			xml.append("<IsQuitControl>" + isquit + "</IsQuitControl>\n");
			xml.append("<Persons>" + persons + "</Persons>\n");
			xml.append("<Cars >" + cars + "</Cars >\n");
			xml.append("<detail>警情处理消息通知</detail>\n");
			xml.append("</notice>\n");

			xml.append("</message>\n");

			socket = Message.connectToServer(serverIp, serverPort, fedBackMgr, "backmsg");
			Message.send(socket, xml.toString());
			System.out.println(xml.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			 Message.disConnectToServer(socket);
		}
	}
	
	
	public  void backmsg(String msg) {
		
	}
	public static void main(String[] args) {
//		SendFeedBackMsg("20090420155356171");
	}
}
