package com.ehl.login;

import java.util.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.appframe.Console;
import com.ehl.loginstate.StateListener;
import com.ehl.sm.sysmanage.LogManage;
import com.ehl.sm.user.UserManage;

public class SessionListener implements HttpSessionBindingListener{
	private String id;//�������
	private String userCode; //��¼�û�����
    private String userName; //��¼�û�����
    private String logCode;  //��¼��־����
    private String clientIP;  //��¼IP
    
    public SessionListener(String id,String userCode, String userName, String logCode,String clientIP) {
        this.id = id;
    	this.userCode = userCode;
        this.userName = userName;
        this.logCode = logCode;
        this.clientIP = clientIP;
    }

    public void valueBound(HttpSessionBindingEvent event){
    	StateListener.addUserState(this.id,this.userName, "", "",this.clientIP);//��¼���û���¼״̬
    	Console.infoprintln("Session Begin:" + new Date() + " <Login User:" + this.userName + ">");
    }
    public void valueUnbound(HttpSessionBindingEvent event){
    	StateListener.deleteUserState(this.id,this.userName,this.clientIP);//��ո��û���¼��Ϣ
    	UserManage.updateUser(this.userCode,this.userName,"","0");
		LogManage.updateData(this.logCode,"","","","1"); //д�ǳ���־
		Console.infoprintln("Session End:" + new Date() + " <Login User:" + this.userName +">");
    }

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogCode() {
		return logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    
}
