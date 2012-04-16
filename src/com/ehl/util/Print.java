package com.ehl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.appframe.data.sql.DBHandler;
import com.ehl.assist.dao.PrintDao;
import com.ehl.sm.base.Constant;

public class Print {
	
	
	/**
	 * ��ȡ��ӡ�ļ����к�
	 * @param application Ӧ�ó���
	 * @param id ������
	 * @param jgid �������
	 * @return ��ӡ�ļ����к�
	 */
	public static String getSerialNumber(ServletContext application,String id, String jgid) {
		String snum = PrintDao.getSerialNum(id, jgid);
		if(snum == null){
			snum = getSerialNumber(application, jgid);
			if(id != null && snum != null ){
				PrintDao.insert(PrintDao.formPrint(id, jgid, snum));
			}
		}
		return snum;
	}
	
	/**
	 * ��ȡ��ӡ�ļ����к�
	 * @param application Ӧ�ó���
	 * @param jgid �������
	 * @return ��ӡ�ļ����к�
	 */
	@SuppressWarnings("unchecked")
	public static String getSerialNumber(ServletContext application, String jgid) {
		String snstr = null;
		if (application != null && jgid != null) {
			Object snobj = application.getAttribute(Constant.PFBH_VAR);
			Map<String, Integer> snmap = null;
			if (snobj != null) {
				try {
					snmap = (HashMap<String, Integer>) snobj;
				} catch (ClassCastException e) {
					snmap = new HashMap<String, Integer>();
				}
				snobj = snmap.get(jgid);
				if (snobj != null && snobj instanceof Integer) {
					Integer snint = (Integer) snobj;
					if (snint >= 1) {
						snstr = "00" + String.valueOf(snint);
						snstr = snstr.substring(snstr.length() - 3);
						snint = snint + 1;
						snmap.put(jgid, snint);
						application.setAttribute(Constant.PFBH_VAR, snmap);
					}
				}
			} else {
				snmap = new HashMap<String, Integer>();
			}
			if (snstr == null) {
				snmap.put(jgid, new Integer(2));
				application.setAttribute(Constant.PFBH_VAR, snmap);
				snstr = "001";
			}
		}
		return snstr;
	}
	
	/**
	 * <pre>
	 * ��ȡ��ӡ���
	 * ��ӡ���=�����+���к�
	 * </pre>
	 * @param rtime �ʱ��
	 * @param snum ���к�
	 * @return ��ӡ���
	 */
	public static String getBh(String rtime,String snum){
		if (rtime == null || rtime.length() < 10 || snum == null) {
			return null;
		}
		rtime = rtime.substring(0,10).replace("-", "");
		return rtime + snum;
	}
	
	/**
	 * <pre>
	 * ת���ɴ�ӡʱ��
	 * ת���в�����Ҫ����߷����쳣��ȡ��ǰʱ��
	 * </pre>
	 * @param time ʱ�� 
	 * @return ��ӡʱ��
	 */
	public static String toPrintTime(String time){
		Date timeDate;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(time == null || time.length() < 16){
			timeDate = new Date();
		}else{
			time = time.substring(0,16);
			try {
				timeDate = formater.parse(time);
			} catch (ParseException e) {
				System.err.println("����ת���쳣��");
				timeDate = new Date();
			}
		}
		formater.applyPattern("yyyy/M/d H:m");
		return formater.format(timeDate);
	}
	
	/**
	 * <pre>
	 * ��ȡ���ݿ�ӱ�ʱ��
	 * </pre>
	 * @param time ʱ�� 
	 * @return ��ӡʱ��
	 */
	public static String getPrintTimeFromBase(String id){
		String Sql = "select to_char(reportTime,'yyyy-mm-dd HH24:MI') from t_attemper_alarm_zd where alarmid = '"+ id +"'";
		Object[] reportTime = DBHandler.getLineResult(Sql);
		return String.valueOf(reportTime[0]);
	}
	
	/**
	 * <pre>
	 * ��ȡ���ݿ��������Ϣ
	 * </pre>
	 * @param id ����id 
	 * @return ��ӡʱ��
	 */
	public static String getAlarmTimeFromBase(String id){
		String Sql = "select to_char(rtime,'yyyy-mm-dd HH24:MI') from t_oa_acceptdept t where aid = '"+ id +"'";
		Object[] reportTime = DBHandler.getLineResult(Sql);
		if(reportTime == null) {
			return " ";
		} else {
			return String.valueOf(reportTime[0]);
		}
	}
	
	public static String getAlarmPersonFromBase(String id,String jgid){
		String Sql = "select rpname from t_oa_acceptdept where aid = '"+ id +"' and rpdcode= '"+ jgid +"' and rpname is not null";
		Object[] reportPerson = DBHandler.getLineResult(Sql);
		if(reportPerson == null) {
			return " ";
		} else {
			return String.valueOf(reportPerson[0]);
		}
	}
	
	/**
	 * <pre>
	 * ��ȡ���ݿ�ӱ�ʱ��
	 * </pre>
	 * @return �ӱ�ʱ��
	 */
	public static String getNoticeTimeFromBase(String id){
		String Sql = "select to_char(stime,'yyyy-mm-dd HH24:MI') from t_oa_notice t where id = '"+ id +"'";
		Object[] reportTime = DBHandler.getLineResult(Sql);
		if(reportTime == null) {
			return " ";
		} else {
			return String.valueOf(reportTime[0]);
		}
	}
	
	/**
	 * <pre>
	 * ��ȡ���ݿ��������Ϣ
	 * </pre>
	 * @return ������
	 */
	public static String getNoticePerSonFromBase(String id, String jgid){
		String Sql = "select rpname from t_oa_acceptdept where aid = '"+ id +"' and rpdcode= '"+ jgid +"' and rpname is not null";
		Object[] reportperson = DBHandler.getLineResult(Sql);
		if(reportperson == null) {
			return " ";
		} else {
			return String.valueOf(reportperson[0]);
		}
	}
}
