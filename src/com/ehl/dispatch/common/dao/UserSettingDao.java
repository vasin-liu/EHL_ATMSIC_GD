
	/**   
	* 文件名：UserSettingDao.java   
	*   
	* 版本信息：   
	* 日期：2011-8-22   
	* Copyright 易华录 2011    
	* 版权所有   
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
	* 项目名称：EHL_ATMSIC_GD
	* 包路径：com.ehl.dispatch.common.dao  
	* 类名称：UserSettingDao   
	* 文件描述：用户默认设置DAO  
	*
	* @see
	* @since Ver 1.1
	* 版本信息：Ver 1.1 
	*********************************
	* 创建人：Vasin   
	* 创建日期：2011-8-25 下午01:51:07  
	************* 修改历史  *************
	* 修改人：Vasin   
	* 修改时间：2011-8-25 下午01:51:07   
	* 修改备注：
 */
public class UserSettingDao {

	
	public static String [] cols = {"id","user_code","function_id",
		"model_name","element_key_value","delete_state"};
	
	/**
	 * 
		* 方法名称：insertUserSetting
		* 方法描述： 保存用户默认设置
		* @param params
		* @return boolean
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:51:17  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:51:17   
		* 修改备注：
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
			System.out.println(this.getClass().getName()+":保存用户设置成功！");
			return true;
		} else {
			System.out.println(this.getClass().getName()+":保存用户设置失败！");
			return false;
		}	
	}
	
	/**
	 * 
		* 方法名称：updateUserSetting
		* 方法描述： 更新用户默认设置
		* @param params
		* @return boolean
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:51:31  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:51:31   
		* 修改备注：
	 */
	public boolean updateUserSetting(Map<String, String> params) {
		String id = params.get(cols[0]);
		String elementKeyValue = params.get(cols[4]);
		String sql = "UPDATE T_OA_USER_SETTING SET ELEMENT_KEY_VALUE='" + elementKeyValue + "' " +
				" WHERE ID='" + id + "'";
		boolean success = DBHandler.execute(sql);
		if (success) {
			System.out.println(this.getClass().getName() + ":更新用户设置成功！");
			return true;
		} else {
			System.out.println(this.getClass().getName() + ":更新用户设置失败！");
			return false;
		}
	}
	
	/**
	 * 
		* 方法名称：deleteUserSetting
		* 方法描述： 删除用户默认设置
		* @param params
		* @return boolean
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:51:47  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:51:47   
		* 修改备注：
	 */
	public boolean deleteUserSetting(Map<String, String> params) {
		String id = params.get(cols[0]);
		String sql = "UPDATE T_OA_USER_SETTING SET DELETE_STATE=1 WHERE ID='" + id + "')";
		boolean success = DBHandler.execute(sql);
		if (success) {
			System.out.println(this.getClass().getName() + ":删除用户设置成功！");
			return true;
		} else {
			System.out.println(this.getClass().getName() + ":删除用户设置失败！");
			return false;
		}	
	}
	
	/**
	 * 
		* 方法名称：getUserSettingId
		* 方法描述： 通过用户ID、功能模块ID和模块名称获取用户默认设置的ID
		* @param params
		* @return String
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:52:05  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:52:05   
		* 修改备注：
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
		* 方法名称：getUserSettingDefaultValue
		* 方法描述： 通过用户默认设定ID获取用户的默认设置
		* @param params
		* @return Object[][]
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:52:17  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:52:17   
		* 修改备注：
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
		* 方法名称：splitElement
		* 方法描述： 分割字符串
		* @param src
		* @return String
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:52:31  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:52:31   
		* 修改备注：
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
		* 方法名称：splitElementMap
		* 方法描述： 分割字符串返回MAP
		* @param src
		* @return
		* @return Map<String,String>
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午04:02:04  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午04:02:04   
		* 修改备注：
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
		* 方法名称：mapToXml
		* 方法描述： 将map转换为xml字符串
		* @param data
		* @return String
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-26 上午11:11:17  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-26 上午11:11:17   
		* 修改备注：
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
		* 方法名称：getSettingValueById
		* 方法描述： 根据设定ID获取设定值
		* @param setting_id
		* @return
		* @return String
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-26 下午02:27:06  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-26 下午02:27:06   
		* 修改备注：
	 */
	public String getSettingValueById(String setting_id) {
		String setting_value = "";
		String sql = "SELECT SETTING_VALUE FROM T_OA_SETTINGS WHERE SETTING_ID='" + setting_id + "'";
		setting_value = StringHelper.obj2str(DBHandler.getSingleResult(sql), "");
		return setting_value;
	}
	
	/**
	 * 
		* 方法名称：isExistValue
		* 方法描述：检查元素是否已经在Map中存放，如果已存在则取出，不存在则从数据库中读取。
		* @param settings
		* @param keySrc
		* @return String
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-30 下午02:24:30  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-30 下午02:24:30   
		* 修改备注：
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
		* 方法名称：getNextSettingId
		* 方法描述： 生成用户默认设置的主键ID
		* @param params
		* @return String
		* @see 
		* @since Ver 1.1 
		* 版本信息：Ver 1.1 
		*********************************
		* 创建人：Vasin   
		* 创建时间：2011-8-25 下午01:52:49  
		************* 修改历史  *************
		* 修改人：Vasin   
		* 修改时间：2011-8-25 下午01:52:49   
		* 修改备注：
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
