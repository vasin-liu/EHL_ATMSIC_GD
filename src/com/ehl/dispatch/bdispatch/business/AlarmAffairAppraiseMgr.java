package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.AlarmAffairAppraise;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;


/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairAppraiseMgr.java
 * 
 * ժ Ҫ������������������Ϣ�����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-20
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class AlarmAffairAppraiseMgr
{
	//ͨ�������¼�Ψһ��ʶ��ȡ�¼���������Ϣ
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
		return XmlDataCreator.buildXmlDocument(appraiseData, "row");	//����xml����
	}
}
