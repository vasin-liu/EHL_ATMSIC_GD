
	/**   
	* �ļ�����UserSettingDao.java   
	*   
	* �汾��Ϣ��   
	* ���ڣ�2011-8-22   
	* Copyright �׻�¼ 2011    
	* ��Ȩ����   
	*   
	*/
package com.ehl.dispatch.common.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;

/**
 * 
	* ��Ŀ���ƣ�EHL_ATMSIC_GD
	* ��·����com.ehl.dispatch.common.dao  
	* �����ƣ�UserSettingDao   
	* �ļ��������û�Ĭ������DAO  
	*
	* @see
	* @since Ver 1.1
	* �汾��Ϣ��Ver 1.1 
	*********************************
	* �����ˣ�Vasin   
	* �������ڣ�2011-8-25 ����01:51:07  
	************* �޸���ʷ  *************
	* �޸��ˣ�Vasin   
	* �޸�ʱ�䣺2011-8-25 ����01:51:07   
	* �޸ı�ע��
 */
public class UserSettingDao {

	
	public static String [] cols = {"id","user_code","function_id",
		"model_name","element_key_value","delete_state"};
	
	/**
	 * 
		* �������ƣ�insertUserSetting
		* ���������� �����û�Ĭ������
		* @param params
		* @return boolean
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:51:17  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:51:17   
		* �޸ı�ע��
	 */
	public boolean insertUserSetting(Map<String, String> params) {
		String id = "";
		String userId = params.get(cols[1]);
		String functionId = params.get(cols[2]);
		String modelName = params.get(cols[3]);
		String elementKeyValue = params.get(cols[4]);
		
		String sql = "";
		boolean success = false;

		id = this.getNextSettingId(params);
		sql = "INSERT INTO T_OA_USER_SETTING " +
			"(ID,USER_CODE,FUNCTION_ID,MODEL_NAME,ELEMENT_KEY_VALUE,DELETE_STATE)" +
			" VALUES('"+ id + "','" +userId + "','" + functionId + "','" + modelName +
			"','" + elementKeyValue + "','0')";
		
		success = DBHandler.execute(sql);	
		if (success) {
			System.out.println(this.getClass().getName()+":�����û����óɹ���");
			return true;
		} else {
			System.out.println(this.getClass().getName()+":�����û�����ʧ�ܣ�");
			return false;
		}	
	}
	
	/**
	 * 
		* �������ƣ�updateUserSetting
		* ���������� �����û�Ĭ������
		* @param params
		* @return boolean
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:51:31  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:51:31   
		* �޸ı�ע��
	 */
	public boolean updateUserSetting(Map<String, String> params) {
		String id = params.get(cols[0]);
		String elementKeyValue = params.get(cols[4]);
		String sql = "UPDATE T_OA_USER_SETTING SET ELEMENT_KEY_VALUE='" + elementKeyValue + "' " +
				" WHERE ID='" + id + "'";
		boolean success = DBHandler.execute(sql);
		if (success) {
			System.out.println(this.getClass().getName() + ":�����û����óɹ���");
			return true;
		} else {
			System.out.println(this.getClass().getName() + ":�����û�����ʧ�ܣ�");
			return false;
		}
	}
	
	/**
	 * 
		* �������ƣ�deleteUserSetting
		* ���������� ɾ���û�Ĭ������
		* @param params
		* @return boolean
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:51:47  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:51:47   
		* �޸ı�ע��
	 */
	public boolean deleteUserSetting(Map<String, String> params) {
		String id = params.get(cols[0]);
		String sql = "UPDATE T_OA_USER_SETTING SET DELETE_STATE=1 WHERE ID='" + id + "')";
		boolean success = DBHandler.execute(sql);
		if (success) {
			System.out.println(this.getClass().getName() + ":ɾ���û����óɹ���");
			return true;
		} else {
			System.out.println(this.getClass().getName() + ":ɾ���û�����ʧ�ܣ�");
			return false;
		}	
	}
	
	/**
	 * 
		* �������ƣ�getUserSettingId
		* ���������� ͨ���û�ID������ģ��ID��ģ�����ƻ�ȡ�û�Ĭ�����õ�ID
		* @param params
		* @return String
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:52:05  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:52:05   
		* �޸ı�ע��
	 */
	public String getUserSettingId(Map<String, String> params) {
		String id = "";
		String userId = params.get(cols[1]);
		String functionId = params.get(cols[2]);
		String modelName = params.get(cols[3]);
		String sql = "SELECT ID FROM T_OA_USER_SETTING WHERE USER_CODE='" + userId + "' AND FUNCTION_ID='"+
				functionId + "' AND MODEL_NAME='" + modelName +"'";
		Object result = DBHandler.getSingleResult(sql);
		id = StringHelper.obj2str(result, "");
		return id;
	}
	
	/**
	 * 
		* �������ƣ�getUserSettingDefaultValue
		* ���������� ͨ���û�Ĭ���趨ID��ȡ�û���Ĭ������
		* @param params
		* @return Object[][]
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:52:17  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:52:17   
		* �޸ı�ע��
	 */
	public String getUserSettingDefaultValue(Map<String, String> params) {
		String xml = "";
		String id = params.get(cols[0]);
		String sql = "SELECT ELEMENT_KEY_VALUE FROM T_OA_USER_SETTING WHERE ID='" + id + "'";
		String result = StringHelper.obj2str(DBHandler.getSingleResult(sql), "");
		if (result != null && result.length() > 0) {
			xml = this.mapToXml(this.splitElementMap(result));
		}
		return xml;
	}
	
	/**
	 * 
		* �������ƣ�splitElement
		* ���������� �ָ��ַ���
		* @param src
		* @return String
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:52:31  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:52:31   
		* �޸ı�ע��
	 */
	public String splitElement(String src) {
		String dest = "";
		String[] result = src.split(",");
		if (result != null && result.length > 0) {
			dest += "'" + result[0] + "'";
			if (result.length > 1) {
				for (int i = 1; i < result.length; i++) {
					dest += ",'" + result[i] + "'";
				}
			}
		}
		return dest;
	}
	
	/**
	 * 
		* �������ƣ�splitElementMap
		* ���������� �ָ��ַ�������MAP
		* @param src
		* @return
		* @return Map<String,String>
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����04:02:04  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����04:02:04   
		* �޸ı�ע��
	 */
	public Map<String, String> splitElementMap(String src) {
		Map<String,String> elements = new HashMap<String, String>();
		String[] result = src.split(",");
		String[] temp = null;
		if (result != null && result.length > 0) {
			for (String res : result) {
				temp = res.split("=");
				if (temp != null && temp.length > 1) {
					elements.put(temp[0], temp[1]);
				}
			}
		}
		return elements;
	}
	
	/**
	 * 
		* �������ƣ�mapToXml
		* ���������� ��mapת��Ϊxml�ַ���
		* @param data
		* @return String
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-26 ����11:11:17  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-26 ����11:11:17   
		* �޸ı�ע��
	 */
	public String mapToXml(Map<String, String> data) {
//		Map<String, String> existElements = new HashMap<String, String>();
		String xml = "<?xml version='1.0' encoding='UTF-8'?>\n";
		xml += "<mapToXml>\n";
		for (Iterator<Map.Entry<String,String>> iterator = data.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>) iterator.next();
			String key = StringHelper.obj2str(entry.getKey(), "");
			String value = StringHelper.obj2str(entry.getValue(), "");
			xml += "<row id='" + key + "' value='"+ this.getSettingValueById(value) +"'></row>\n";
//			xml += "<row id='" + key + "' value='"+ this.isExistValue(existElements, value) +"'></row>\n";
		}
		xml += "</mapToXml>";
		return xml;
	}
	
	/**
	 * 
		* �������ƣ�getSettingValueById
		* ���������� �����趨ID��ȡ�趨ֵ
		* @param setting_id
		* @return
		* @return String
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-26 ����02:27:06  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-26 ����02:27:06   
		* �޸ı�ע��
	 */
	public String getSettingValueById(String setting_id) {
		String setting_value = "";
		String sql = "SELECT SETTING_VALUE FROM T_OA_SETTINGS WHERE SETTING_ID='" + setting_id + "'";
		setting_value = StringHelper.obj2str(DBHandler.getSingleResult(sql), "");
		return setting_value;
	}
	
	/**
	 * 
		* �������ƣ�isExistValue
		* �������������Ԫ���Ƿ��Ѿ���Map�д�ţ�����Ѵ�����ȡ����������������ݿ��ж�ȡ��
		* @param settings
		* @param keySrc
		* @return String
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-30 ����02:24:30  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-30 ����02:24:30   
		* �޸ı�ע��
	 */
	public String isExistValue(Map<String, String> settings,String keySrc) {
		String settingValue = "";
		if (settings != null && keySrc != null) {
			for (Iterator<Map.Entry<String,String>> iterator = settings.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String,String> entry = (Map.Entry<String,String>) iterator.next();
				String key = StringHelper.obj2str(entry.getKey(), "");
				if (keySrc.equals(key)) {
					settingValue = settings.get(keySrc);
				}else {
					settingValue = getSettingValueById(keySrc);
					settings.put(keySrc, settingValue);
				}
			}
		}
		return settingValue;
	}
	
	/**
	 * 
		* �������ƣ�getNextSettingId
		* ���������� �����û�Ĭ�����õ�����ID
		* @param params
		* @return String
		* @see 
		* @since Ver 1.1 
		* �汾��Ϣ��Ver 1.1 
		*********************************
		* �����ˣ�Vasin   
		* ����ʱ�䣺2011-8-25 ����01:52:49  
		************* �޸���ʷ  *************
		* �޸��ˣ�Vasin   
		* �޸�ʱ�䣺2011-8-25 ����01:52:49   
		* �޸ı�ע��
	 */
	public String getNextSettingId(Map<String, String> params) {
		String id = null;
		String userId = params.get(cols[2]);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String nowString = sdf.format(now);
		Random rd = new Random();
		String randomNumString = String.valueOf(Math.abs(rd.nextInt(3)));
		id = "US" + userId + nowString+randomNumString;
		return id;
	}
}
