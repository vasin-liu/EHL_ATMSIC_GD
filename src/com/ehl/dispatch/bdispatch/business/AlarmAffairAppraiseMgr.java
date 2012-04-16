package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.AlarmAffairAppraise;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;


/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairAppraiseMgr.java
 * 
 * 摘 要：警情事务处理评价信息管理类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-20
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class AlarmAffairAppraiseMgr
{
	//通过报警事件唯一标识获取事件的评价信息
	public String readAppraiseByAlarmId(String alarmId)
	{
		if( null == alarmId)
		{
			return null;
		}
		AlarmAffairAppraise appraiseDB = new AlarmAffairAppraise();
		Object [][]appraiseData = appraiseDB.getAffairAppraiseByAlarmId(alarmId);
		if( null == appraiseData)
		{
			return null;
		}
		return XmlDataCreator.buildXmlDocument(appraiseData, "row");	//构造xml数据
	}
}
