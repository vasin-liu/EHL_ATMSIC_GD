package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.data.db.*;
import com.appframe.data.DBFactory;
import com.ehl.dispatch.bdispatch.message.MergeMessage;
import com.ehl.dispatch.bdispatch.util.*;
import com.ehl.dispatch.bdispatch.message.*;
/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：MergeAffairsCtrl.java
 * 
 * 摘 要：合并警情单数据访问类。
 * 			查询待合并警情单信息，合并警情单
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-10
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class MergeAffairs
{
	private static final Logger logger = Logger.getLogger(MergeAffairs.class);
	/**通过警情标识信息获取警情摘要信息
	 * 
	 * @param affairIDs 以逗号分割的警情标识字符串
	 * @return
	 */
	public Object [][] readAffairByIDs(String affairIDs)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT t.alarmid,to_char(t.alarmtime,'HH24:mi'),t.eventstate,tc1.name esource, tc2.name etype,tc3.name elevel,tc4.name estate");
		selectSQL.append(" FROM t_attemper_alarm t, t_attemper_code tc1,t_attemper_code tc2,t_attemper_code tc3,t_attemper_code tc4");
		selectSQL.append(" WHERE tc1.id(+)=t.eventsource AND tc2.id(+)=t.eventtype AND tc3.id(+)=t.eventlevel AND tc4.id=t.eventstate ");
		 
		selectSQL.append(" AND t.alarmid in( " + affairIDs + ") " );
		 
		selectSQL.append(" ORDER BY t.alarmtime ");
		com.appframe.Console.infoprintln(" SQL --->" + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取警情摘要信息数据库错误!");
		}
		return result;
	}
	
	/**更新和单信息
	 * 更新主单的MergedState为主单
	 * 更新子单的MergedState为分单，填写mainAlarmId为主单的ID
	 * 
	 * @param mainAffairId 主单的ID
	 * @param subAffairIDs 次单的ID串（以逗号分割）
	 * @return
	 */
	public boolean updateMergeAffairs(String mainAffairId,String subAffairIDs)
	{
		boolean succeed = false;	//操作结果，只有在正确执行更新，该结果才为ture
		StringBuffer updateSQL = new StringBuffer("UPDATE  t_attemper_alarm t ");
		updateSQL.append(" SET t.mergedstate='" + ConstDefine.MAIN_ALARM_STATE + "' ");
		updateSQL.append(" WHERE t.alarmid='" +  mainAffairId + "'");		//更新主单
		
		StringBuffer subUpdateSQL = new StringBuffer("UPDATE  t_attemper_alarm t ");
		subUpdateSQL.append(" SET t.mergedstate='" + ConstDefine.SUB_ALARM_STATE + "', ");
		subUpdateSQL.append(" t.mainalarmid='" + mainAffairId + "' " );
		subUpdateSQL.append(" WHERE t.alarmid in (" +  subAffairIDs + ")");	//更新分单
		
		Database db = null;
		try
		{
			db = DBFactory.newDatabase();	//构造数据访问对象，执行更新事务
			db.beginTrans();
			if( 0 < db.executeUpdate(updateSQL.toString() ))
			{
				if( 0 < db.executeUpdate(subUpdateSQL.toString()))
				{
					succeed = true;	//只有成功执行2条更新语句succeed才为true
				}
			}
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("执行警情单合并发生异常 ----->" + ex.getMessage());
			logger.error("执行警情单合并数据库错误!");
		}
		finally
		{
			if(null != db)
			{
				if(succeed)	//提交事务
				{
					db.commitTrans(false);
				}
				else
				{
					db.commitTrans(true);
				}
				//关闭数据对象
				DBFactory.closeDatabase(db);
			}
		}
		MergeMessage.sendMergeMessage(mainAffairId, subAffairIDs);
		
		return succeed;
	}
	
	
	
	
	
}
