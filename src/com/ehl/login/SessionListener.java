package com.ehl.login;

import java.util.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.appframe.Console;
import com.ehl.loginstate.StateListener;
import com.ehl.sm.sysmanage.LogManage;
import com.ehl.sm.user.UserManage;

public class SessionListener implements HttpSessionBindingListener{
	private String id;//主键编号
	private String userCode; //登录用户编码
    private String userName; //登录用户名称
    private String logCode;  //登录日志编码
    private String clientIP;  //登录IP
    
    public SessionListener(String id,String userCode, String userName, String logCode,String clientIP) {
        this.id = id;
    	this.userCode = userCode;
        this.userName = userName;
        this.logCode = logCode;
        this.clientIP = clientIP;
    }

    public void valueBound(HttpSessionBindingEvent event){
    	StateListener.addUserState(this.id,this.userName, "", "",this.clientIP);//记录该用户登录状态
    	Console.infoprintln("Session Begin:" + new Date() + " <Login User:" + this.userName + ">");
    }
    public void valueUnbound(HttpSessionBindingEvent event){
    	StateListener.deleteUserState(this.id,this.userName,this.clientIP);//清空该用户登录信息
    	UserManage.updateUser(this.userCode,this.userName,"","0");
		LogManage.updateData(this.logCode,"","","","1"); //写登出日志
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
