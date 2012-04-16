
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.notices.dao <br> 
	* 文件名称：SystemNoticesDao.java <br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1 <br>  
	* 创建日期：2012-2-7 <br>  
	* 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2012-2-7 上午10:39:43<br>  
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-7 上午10:39:43  <br> 
	* 修改备注：   <br>
	*/
package com.ehl.notices.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * 
	* 项目名称：EHL_ATMSIC_GD<br>
	* 包路径：com.ehl.notices.dao  <br>
	* 类名称：SystemNoticesDao  <br> 
	* 文件描述：TODO   <br>
	*
	* @see <br>
	* 版本信息：Ver 1.1 <br>
	*********************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2012-2-7 上午10:39:43  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-7 上午10:39:43  <br> 
	* 修改备注：     <br>
 */
public class SystemNoticesDao {
	
	Logger logger = Logger.getLogger(SystemNoticesDao.class);
	
	/**
	 * 
		* 方法名称：getInfo<br>
		* 方法描述： 获取信息 <br>
		* @param sql
		* @return Object[][] <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 上午10:40:52 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 上午10:40:52  <br> 
		* 修改备注：    <br>
	 */
	public Object[][] getInfo(String sql) {
		Object[][] result = null;
		if (sql != null && !("".equals(sql))) {
			result = DBHandler.getMultiResult(sql);
		}else {
			logger.error("参数错误=====>SQL:"+sql);
		}
		return result;
	}
	
	/**
	 * 
		* 方法名称：getCount<br>
		* 方法描述： 获取信息总数 <br>
		* @param sql
		* @return Object <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 上午10:40:29 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 上午10:40:29  <br> 
		* 修改备注：    <br>
	 */
	public Object getCount(String sql) {
		Object result = null;
		if (sql != null && !("".equals(sql))) {
			result = DBHandler.getSingleResult(sql);
		}else {
			logger.error("参数错误=====>SQL:"+sql);
		}
		return result;
	}
	
	/**
	 * 
		* 方法名称：updateInfo<br>
		* 方法描述： 更新数据 <br>
		* @param sql
		* @return boolean <br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2012-2-7 下午2:13:43 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2012-2-7 下午2:13:43  <br> 
		* 修改备注：    <br>
	 */
	public boolean updateInfo(String sql) {
		if (sql != null && !("".equals(sql))) {
			return DBHandler.execute(sql);
		}else {
			logger.error("参数错误=====>SQL:"+sql);
			return false;
		}
	}
}
