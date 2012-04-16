package com.ehl.dispatch.dispatchTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class dispatchTaskServlet extends HttpServlet {
	
	public dispatchTaskServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	public void init() throws ServletException {
		Timer timer = new Timer();
		Calendar c = Calendar.getInstance();
		//int hour24 = com.appframe.common.Setting.getInt("ACC_TASK_HOUR"); 
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.SECOND, 10);
		Date tmp = c.getTime();
		System.out.println("����ӵ����Ϣ��ʼ������" + tmp.toString());
		timer.scheduleAtFixedRate(new doDispatchTask(), tmp, 1000*60*20);
		System.out.println("����ӵ����Ϣ������" + Calendar.getInstance().getTime().toString());
	}

}
