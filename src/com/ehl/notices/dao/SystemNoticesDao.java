
	/** 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
	* �ļ�·����com.ehl.notices.dao <br> 
	* �ļ����ƣ�SystemNoticesDao.java <br>
	* �ļ���ţ�   <br>
	* �ļ�������  <br>
	*
	* �汾��Ϣ�� Ver 1.1 <br>  
	* �������ڣ�2012-2-7 <br>  
	* ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����    <br>
	**************************************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2012-2-7 ����10:39:43<br>  
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2012-2-7 ����10:39:43  <br> 
	* �޸ı�ע��   <br>
	*/
package com.ehl.notices.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
	* ��·����com.ehl.notices.dao  <br>
	* �����ƣ�SystemNoticesDao  <br> 
	* �ļ�������TODO   <br>
	*
	* @see <br>
	* �汾��Ϣ��Ver 1.1 <br>
	*********************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2012-2-7 ����10:39:43  <br>
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2012-2-7 ����10:39:43  <br> 
	* �޸ı�ע��     <br>
 */
public class SystemNoticesDao {
	
	Logger logger = Logger.getLogger(SystemNoticesDao.class);
	
	/**
	 * 
		* �������ƣ�getInfo<br>
		* ���������� ��ȡ��Ϣ <br>
		* @param sql
		* @return Object[][] <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����10:40:52 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����10:40:52  <br> 
		* �޸ı�ע��    <br>
	 */
	public Object[][] getInfo(String sql) {
		Object[][] result = null;
		if (sql != null && !("".equals(sql))) {
			result = DBHandler.getMultiResult(sql);
		}else {
			logger.error("��������=====>SQL:"+sql);
		}
		return result;
	}
	
	/**
	 * 
		* �������ƣ�getCount<br>
		* ���������� ��ȡ��Ϣ���� <br>
		* @param sql
		* @return Object <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����10:40:29 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����10:40:29  <br> 
		* �޸ı�ע��    <br>
	 */
	public Object getCount(String sql) {
		Object result = null;
		if (sql != null && !("".equals(sql))) {
			result = DBHandler.getSingleResult(sql);
		}else {
			logger.error("��������=====>SQL:"+sql);
		}
		return result;
	}
	
	/**
	 * 
		* �������ƣ�updateInfo<br>
		* ���������� �������� <br>
		* @param sql
		* @return boolean <br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2012-2-7 ����2:13:43 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2012-2-7 ����2:13:43  <br> 
		* �޸ı�ע��    <br>
	 */
	public boolean updateInfo(String sql) {
		if (sql != null && !("".equals(sql))) {
			return DBHandler.execute(sql);
		}else {
			logger.error("��������=====>SQL:"+sql);
			return false;
		}
	}
}
