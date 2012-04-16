
	/** 
	* 项目名称：EHL_ATMSIC_GD <br>
	* 文件路径：com.ehl.general.dao <br> 
	* 文件名称：DutyPersonInfoDao.java <br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1 <br>  
	* 创建日期：2011-12-12 <br>  
	* 公司名称： 北京易华录信息技术股份有限公司  2011 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin  <br> 
	* 创建日期：2011-12-12 下午2:47:03<br>  
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-12 下午2:47:03  <br> 
	* 修改备注：   <br>
	*/
package com.ehl.general.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

	/**   
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.general.dao  <br>
 * 类名称：DutyPersonInfoDao  <br> 
 * 文件描述：值班人员信息dao   <br>
 *
 * @see <br>
 * 版本信息：Ver 1.1 <br>
 *********************************<br>
 * 创建人：Vasin  <br> 
 * 创建日期：2011-12-12 下午2:47:03  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2011-12-12 下午2:47:03  <br> 
 * 修改备注：     <br>
 */
public class DutyPersonInfoDao {
	
	Logger logger = Logger.getLogger(DutyPersonInfoDao.class);
	
	/**
	 * 
		* 方法名称：getInfo<br>
		* 方法描述： 获取分类警情信息列表 <br>
		* @param sql
		* @return Object[][]<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-23 上午11:39:27 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-23 上午11:39:27  <br> 
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
		* 方法描述： 获取警情信息总数 <br>
		* @param sql
		* @return Object<br>
		* 版本信息：Ver 1.1 <br>
		*********************************<br>
		* 创建人：Vasin  <br> 
		* 创建时间：2011-12-23 上午11:39:40 <br> 
		************* 修改历史  *************<br>
		* 修改人：Vasin   <br>
		* 修改时间：2011-12-23 上午11:39:40  <br> 
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
}
