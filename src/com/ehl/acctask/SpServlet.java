package com.ehl.acctask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SpServlet extends HttpServlet {
	
	public SpServlet() {
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
		Timer t = new Timer();
		Calendar c = Calendar.getInstance();
		int hour24 = com.appframe.common.Setting.getInt("ACC_TASK_HOUR"); 
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.SECOND, 10);
		Date tmp = c.getTime();
		System.out.println("事故定位开始运行于" + tmp.toString());
		t.scheduleAtFixedRate(new SpTask(), tmp, 1000*60*60*12);
		System.out.println("事故定位结束于" + Calendar.getInstance().getTime().toString());
	}


}
