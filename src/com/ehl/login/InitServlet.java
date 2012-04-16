package com.ehl.login;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.ehl.loginstate.StateListener;
import com.ehl.sm.base.Constant;

public class InitServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(InitServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String mnameDesc = "程序初始化";
		boolean isOK = StateListener.clearUserState();
		logger.info(mnameDesc+"清除在线人数"+(isOK?"成功":"失败"));
		//doDispatchTask.updateFwTrafficNewsFeed();
		logger.info(mnameDesc+"更新方伟拥堵爆料信息");
		initPFileSNumberByDay(config.getServletContext());
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		boolean isOK = StateListener.clearUserState();
		logger.info("程序结束清除在线人数"+(isOK?"成功":"失败"));
	}
	
	/**
	 * 每天00:00:01初始化打印文件序列号为1
	 */
	public void initPFileSNumberByDay(ServletContext cntxt){
		if(cntxt != null){
			//一天的毫秒数
			long msnum = 1000 * 60 * 60 * 24;
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 1);
			new Timer().scheduleAtFixedRate(new PrintFileSerialNumber(cntxt), calendar.getTime(),msnum);
		}
	}
	
	private class PrintFileSerialNumber extends TimerTask{
		
		public ServletContext cntxt;
		public PrintFileSerialNumber(ServletContext cntxt){
			this.cntxt = cntxt;
		}
		
		public void run() {
			if(this.cntxt != null){
				cntxt.setAttribute(Constant.PFBH_VAR, new HashMap<String, String>());
			}
		}
	}
	
	public static void main(String[] args) {
		
		
	}

}
