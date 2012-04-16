
	/** 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
	* �ļ�·����com.ehl.general.dao <br> 
	* �ļ����ƣ�DutyPersonInfoDao.java <br>
	* �ļ���ţ�   <br>
	* �ļ�������  <br>
	*
	* �汾��Ϣ�� Ver 1.1 <br>  
	* �������ڣ�2011-12-12 <br>  
	* ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2011 Copyright(C) ��Ȩ����    <br>
	**************************************************<br>
	* �����ˣ�Vasin  <br> 
	* �������ڣ�2011-12-12 ����2:47:03<br>  
	************* �޸���ʷ  *************<br>
	* �޸��ˣ�Vasin   <br>
	* �޸�ʱ�䣺2011-12-12 ����2:47:03  <br> 
	* �޸ı�ע��   <br>
	*/
package com.ehl.general.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

	/**   
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.general.dao  <br>
 * �����ƣ�DutyPersonInfoDao  <br> 
 * �ļ�������ֵ����Ա��Ϣdao   <br>
 *
 * @see <br>
 * �汾��Ϣ��Ver 1.1 <br>
 *********************************<br>
 * �����ˣ�Vasin  <br> 
 * �������ڣ�2011-12-12 ����2:47:03  <br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2011-12-12 ����2:47:03  <br> 
 * �޸ı�ע��     <br>
 */
public class DutyPersonInfoDao {
	
	Logger logger = Logger.getLogger(DutyPersonInfoDao.class);
	
	/**
	 * 
		* �������ƣ�getInfo<br>
		* ���������� ��ȡ���ྯ����Ϣ�б� <br>
		* @param sql
		* @return Object[][]<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-23 ����11:39:27 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-23 ����11:39:27  <br> 
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
		* ���������� ��ȡ������Ϣ���� <br>
		* @param sql
		* @return Object<br>
		* �汾��Ϣ��Ver 1.1 <br>
		*********************************<br>
		* �����ˣ�Vasin  <br> 
		* ����ʱ�䣺2011-12-23 ����11:39:40 <br> 
		************* �޸���ʷ  *************<br>
		* �޸��ˣ�Vasin   <br>
		* �޸�ʱ�䣺2011-12-23 ����11:39:40  <br> 
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
}
