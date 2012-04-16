package com.ehl.dispatch.bdispatch.message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;


/**
 * <b>消息接收线程类.</b>
 */
public class MessageThread extends Thread {
	private Socket socket = null;
	private Class triggerClass = null;
	private String triggerClassMethod = null;
	private boolean death = false;
	
	public MessageThread(Socket socket, Class triggerClass, String triggerClassMethod) 
	{
		this.socket = socket;
		this.triggerClass = triggerClass;
		this.triggerClassMethod = triggerClassMethod;
		
	}
	
	public void setDeath(boolean death){
		this.death = death;
	}
	/**
	 * <b>消息接收主线程.</b>
	 */
	public void run() {
		try {
			Thread.sleep(100);
			if (this.socket != null) 
			{
				// 接收消息
				BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				
				String str = "";
				while (true) 
				{
					Thread.sleep(1000);
					int iMsg = in.read();
					while(-1 != iMsg)
					{
						str += (char)iMsg + "";
 
						if (str.endsWith("</message>")) 
						{
							sendMSGArrive(str, this.triggerClass, this.triggerClassMethod);
							str = "";
							Thread.sleep(100);
						}
					}
					
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println("消息接收通道已关闭！" + e.getLocalizedMessage());
		}
	}

	/**
	 * <b>触发消息事件.</b>
	 * 
	 * @param String
	 *            msg 消息内容
	 * @param Class
	 *            triggerClass 触发消息类
	 * @param String
	 *            triggerClassMethod 触发消息类方法名
	 */
	public void sendMSGArrive(String msg, Class triggerClass, String triggerClassMethod) {
		try 
		{
			 
			Method method = triggerClass.getMethod(triggerClassMethod, new Class[]{String.class});
			method.invoke(triggerClass, new Object[]{msg});
			
		}
		catch (Exception ex) 
		{
			System.out.println("获取消息接收方法错误：" + ex.getMessage());
		}
	}
}