package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.AlarmAffairQuery;
import com.ehl.dispatch.bdispatch.util.AlarmAffairType;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;
import com.ehl.dispatch.bdispatch.util.*;
import com.appframe.utils.StringHelper ;
import com.ehl.dispatch.bdispatch.util.*;
/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairQueryMgr.java
 * 
 * ժ Ҫ��ָ�ӵ��ȼ�ؾ����¼���ѯ�����ࡣ
 * 		 ���ݲ�ѯ��ҵ���߼���ִ�ж����ݿ����Ϣ��ѯ��
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-9
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class AlarmAffairQueryMgr
{
	/**��ѯ��Ҫ��ע�ľ�����Ϣ
	 * 
	 * @param branchCode ��������
	 * @return
	 */
	public String queryNoticeAffair(String branchCode)
	{ 		
		String msgWatchingTimeRange  = com.appframe.common.Setting.getString("msgWatchingTimeRange");
		
		//�������ݷ�����
		//��ѯ���ĳ�����������þ����עʱ����ڵľ�����Ϣ
		AlarmAffairQuery dbQuery  = new AlarmAffairQuery();	
		Object [][] records = dbQuery.queryNoticeAffair(branchCode, msgWatchingTimeRange);
	  	
		//��ȡ���ݣ�����õ������������ݽṹ�����أ�
		//���û�õ������򷵻�null
		if(null != records)
	  	{
	  		//����xml���ݴ�
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
		
		String subAlarmString = this.getSubAlarmIDs(alarmId);	//��ȡ���¼���ʶ
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
			case accident:	//�¹�
				records = affairQuery.getAccidentDetailById(alarmId,subAlarmString);
				break;
			case trafficjam:	//ӵ��
				records = affairQuery.getTrafficjamDetailById(alarmId,subAlarmString);
				break;
			case troubleCargo:	//���͹��ϳ�
				records = affairQuery.getTroubleCargoDetailById(alarmId, subAlarmString);
				break;
			case disasterWeather:	//�ֺ�����
				records = affairQuery.getDisasterWeatherDetailById(alarmId, subAlarmString);
				break;
			case citizenCase:	//����й¶,ú��,����ˮ�������¼�
				records = affairQuery.getCitizenCaseDetailById(alarmId, subAlarmString);
				break;
			case geologicalDisaster:	//����,��ʯ���ȵ����ֺ�
				records = affairQuery.getGeologicDetailById(alarmId, subAlarmString);
				break;
			case suspicionCar:	//���ڲ���,���ɳ���
				records = affairQuery.getSuspiconCarDetailById(alarmId, subAlarmString);
				break;
			case socialCase:	//�ΰ��¼�
				records = affairQuery.getSocialCaseDetailById(alarmId, subAlarmString);
				break;
			case fireAndBoom:	//���ֱ�ը
				records = affairQuery.getFireAndBoomDetailById(alarmId, subAlarmString);
				break;
			case euqipment: //�豸����
				records = affairQuery.getEuqpmentDetailById(alarmId, subAlarmString);
				break;	
			
		}
	  
		
		//��ȡ���ݣ�����õ������������ݽṹ�����أ�
		//���û�õ������򷵻�null
		if(null != records)
	  	{
			
	  		//����xml���ݴ�
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
