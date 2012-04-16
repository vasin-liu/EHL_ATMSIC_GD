package com.ehl.dispatch.cdispatch.dao;
import java.io.File;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.action.Jspsmart;
import com.ehl.dispatch.cdispatch.core.DepartmentUtil;
import com.ehl.sm.common.util.StringUtil;

/**
 * 通知管理数据类
 * @author wkz
 * @date 2010-7-1
 *
 */
public class NoticeInfoDao {
	private Logger logger = Logger.getLogger(NoticeInfoDao.class);
		
	/**
	 * 取得提醒信息<br/>
	 * 提醒信息的取得处理
	 * @param reciveunit
	 * @return
	 */
	public Object[] getNoticeCount(String reciveunit) {
		
		String jgbh;//总队(2位),支队(4位),大队(6位)
		String deptcode = reciveunit;
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct count(1) from t_oa_notice where ISSEND='1' and VIEWSTATE='0' and receiveunit like '%"+reciveunit+"%'");
		
		/*
		// 支队登录用户时检索的处理
		if(jgbh.length() == 4) {
			sql.append("select distinct count(1) from t_oa_process op,T_ATTEMPER_ALARM_ZD a1 where  op.ywid = a1.alarmid and  a1.eventstate in ('004032','004038') and  op.ywlx in ('大队上报','总队下发') and a1.eventtype = '001024'");
		// 总队登录用户时检索的处理
		} else if(jgbh.length() == 2) {
			sql.append("select distinct count(1) from t_oa_process op, T_ATTEMPER_ALARM_ZD a1 where op.ywid = a1.alarmid and a1.eventstate in ('004033') and op.ywlx in ('支队上报') and a1.eventtype = '001024'");
		// 大队时检索的处理
		} else if(jgbh.length() == 6) {
			sql.append("select distinct count(1) from t_oa_process op, T_ATTEMPER_ALARM a1 where op.ywid = a1.alarmid and a1.eventstate in ('004032', '004038') and op.ywlx in ('大队提醒') and a1.eventtype = '001024'");
		}
		*/
		System.out.println("getNoticeCount==>" + sql);
		return DBHandler.getLineResult(sql.toString());
	}
	
	/**
	 * 提醒详细信息的取得<br/z>
	 * 提醒详细信息的取得处理
	 * @param reciveunit
	 * @return
	 */
	public Object[][] getNoticeList(String reciveunit) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select NOTICEID,to_char(sendtime,'yyyy-MM-dd'),sendunit,sendpeople,INSTRUCTIONS,NOTICECONTENT from t_oa_notice where ISSEND='1' and VIEWSTATE='0' and receiveunit like '%"+reciveunit+"%'");
		System.out.println("getNoticeList.sql==>"+sql);
		Object[][] result = DBHandler.getMultiResult(sql.toString());
		return result;
	}
	
	/** 
	 * 查询唯一通知信息
	 * @param noticeId
	 * @return
	 */
	public Object[] getNoticeInfo(String noticeId) {
		String sql = null;
		Object[] obj=null;
		try {
			String updateSql ="update t_oa_notice set VIEWSTATE='1' where NOTICEID='"+noticeId+"'";
			System.out.println("editNoticeInfo.sql==>"+updateSql);
			DBHandler.execute(updateSql);
			
			sql ="select to_char(sendtime,'yyyy-MM-dd hh24:mm:ss'),sendunit,sendpeople,INSTRUCTIONS,NOTICECONTENT,RECEIVEUNIT,RECEIVEPEOPLE,to_char(UPDATETIME,'yyyy-MM-dd hh24:mm:ss'),UPDATEUSER,VIEWSTATE,ISSEND from t_oa_notice";
			sql +=" where NOTICEID='"+noticeId+"'";
			System.out.println("getNoticeInfo.sql==>"+sql);
			obj = DBHandler.getLineResult(sql);
			obj[5] = getDeptNameString(StringHelper.obj2str(obj[5]));
		}catch(Exception e) {
			logger.error("查询唯一通知信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
	
	public String getDeptNameString(String deptId) {
		StringBuffer sql = null;
		Object obj=null;
		StringBuffer result= new StringBuffer();
		System.out.println("deptId："+deptId);
		String deptid[] = deptId.split("；");
		System.out.println("deptid[].length："+deptid.length);
		try {
			for(int i=0;i<deptid.length;i++){
				sql = new StringBuffer("select jgmc from t_sys_department where jgid='"+deptid[i]+"'");
				System.out.println("getDeptNameString.sql==>"+sql);
				obj = DBHandler.getSingleResult(sql.toString());
				System.out.println("StringHelper.obj2str(obj)："+StringHelper.obj2str(obj));
				result.append(StringHelper.obj2str(obj)+"；");
				System.out.println("result.toString()："+result.toString());
			}
		}catch(Exception e) {
			System.out.println("getDeptNameString.Exception==>"+e);
		}
		return result.toString();
	}
}