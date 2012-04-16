package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.AlarmAffairQuery;
import com.ehl.dispatch.bdispatch.util.AlarmAffairType;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;
import com.ehl.dispatch.bdispatch.util.*;
import com.appframe.utils.StringHelper ;
import com.ehl.dispatch.bdispatch.util.*;
/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairQueryMgr.java
 * 
 * 摘 要：指挥调度监控警情事件查询管理类。
 * 		 根据查询的业务逻辑，执行对数据库的信息查询。
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-9
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class AlarmAffairQueryMgr
{
	/**查询需要关注的警情信息
	 * 
	 * @param branchCode 机构编码
	 * @return
	 */
	public String queryNoticeAffair(String branchCode)
	{ 		
		String msgWatchingTimeRange  = com.appframe.common.Setting.getString("msgWatchingTimeRange");
		
		//构造数据访问类
		//查询针对某机构的在配置警情关注时间段内的警情信息
		AlarmAffairQuery dbQuery  = new AlarmAffairQuery();	
		Object [][] records = dbQuery.queryNoticeAffair(branchCode, msgWatchingTimeRange);
	  	
		//获取数据，如果得到数据则构造数据结构并返回；
		//如果没得到数据则返回null
		if(null != records)
	  	{
	  		//生成xml数据串
	  		return XmlDataCreator.buildXmlDocument(records, "row");
	  	}
	  	else
	  	{
	  		return null;
	  	}
	}
	
	public String getAffairDetail(String alarmId,String alarmType)
	{
		if(null == alarmId || "".equals(alarmId) || null==alarmType || "".equals(alarmType))
		{
			return null;
		}
		
		AlarmAffairQuery affairQuery = new AlarmAffairQuery();
		
		String subAlarmString = this.getSubAlarmIDs(alarmId);	//获取子事件标识
		Object [][] records = null;
		AlarmAffairType [] affairTypes = AlarmAffairType.values();
		AlarmAffairType affairType = AlarmAffairType.otherElse; 
		
		for(int i=0;i<affairTypes.length;i++)
		{
			if(alarmType.equals( affairTypes[i].getTypeValue() ) )
			{
				affairType = affairTypes[i];
				break;
			}
		}
		switch(affairType)
		{
			case accident:	//事故
				records = affairQuery.getAccidentDetailById(alarmId,subAlarmString);
				break;
			case trafficjam:	//拥堵
				records = affairQuery.getTrafficjamDetailById(alarmId,subAlarmString);
				break;
			case troubleCargo:	//大型故障车
				records = affairQuery.getTroubleCargoDetailById(alarmId, subAlarmString);
				break;
			case disasterWeather:	//灾害天气
				records = affairQuery.getDisasterWeatherDetailById(alarmId, subAlarmString);
				break;
			case citizenCase:	//热力泄露,煤气,自来水等市政事件
				records = affairQuery.getCitizenCaseDetailById(alarmId, subAlarmString);
				break;
			case geologicalDisaster:	//塌方,泥石流等地质灾害
				records = affairQuery.getGeologicDetailById(alarmId, subAlarmString);
				break;
			case suspicionCar:	//卡口布控,嫌疑车辆
				records = affairQuery.getSuspiconCarDetailById(alarmId, subAlarmString);
				break;
			case socialCase:	//治安事件
				records = affairQuery.getSocialCaseDetailById(alarmId, subAlarmString);
				break;
			case fireAndBoom:	//火灾爆炸
				records = affairQuery.getFireAndBoomDetailById(alarmId, subAlarmString);
				break;
			case euqipment: //设备故障
				records = affairQuery.getEuqpmentDetailById(alarmId, subAlarmString);
				break;	
			
		}
	  
		
		//获取数据，如果得到数据则构造数据结构并返回；
		//如果没得到数据则返回null
		if(null != records)
	  	{
			
	  		//生成xml数据串
	  		return XmlDataCreator.buildXmlDocument(records, "row");
	  	}
	  	else
	  	{
//	  		StringBuilder xmlDoc = new StringBuilder();
//			xmlDoc.append("<?xml version='1.0' encoding='UTF-8'?>\n");
//			xmlDoc.append("<rfXML>\n");
//			xmlDoc.append("<RFWin>\n");
//			xmlDoc.append("</RFWin>\n");
//			xmlDoc.append("</rfXML>\n");
//			return xmlDoc.toString();
	  		return null;
	  	}
	}
	
	
	
	
	protected String getSubAlarmIDs(String alarmId)
	{
		String subAlarms = "";
		AlarmAffairQuery affairQuery = new AlarmAffairQuery();
		Object [][]subAlarmRecords =  affairQuery.getSubAffairIDs(alarmId);
//		System.out.println(subAlarmRecords.length);
		if( null != subAlarmRecords)
		{
			for(int i=0; i<subAlarmRecords.length;i++)
			{
				if("".equals(subAlarms))
				{
					subAlarms += StringHelper.obj2str(subAlarmRecords[i][0]) + ";" +  StringHelper.obj2str(subAlarmRecords[i][1]);
				}
				else
				{
					subAlarms += "," +  StringHelper.obj2str(subAlarmRecords[i][0]) + ";" +  StringHelper.obj2str(subAlarmRecords[i][1]);
				}
			}
		}
		return subAlarms;
	}
}
