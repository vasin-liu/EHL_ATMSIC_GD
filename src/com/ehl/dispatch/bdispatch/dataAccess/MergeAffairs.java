package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.data.db.*;
import com.appframe.data.DBFactory;
import com.ehl.dispatch.bdispatch.message.MergeMessage;
import com.ehl.dispatch.bdispatch.util.*;
import com.ehl.dispatch.bdispatch.message.*;
/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�MergeAffairsCtrl.java
 * 
 * ժ Ҫ���ϲ����鵥���ݷ����ࡣ
 * 			��ѯ���ϲ����鵥��Ϣ���ϲ����鵥
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-10
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class MergeAffairs
{
	private static final Logger logger = Logger.getLogger(MergeAffairs.class);
	/**ͨ�������ʶ��Ϣ��ȡ����ժҪ��Ϣ
	 * 
	 * @param affairIDs �Զ��ŷָ�ľ����ʶ�ַ���
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
			logger.error("��ȡ����ժҪ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	/**���º͵���Ϣ
	 * ����������MergedStateΪ����
	 * �����ӵ���MergedStateΪ�ֵ�����дmainAlarmIdΪ������ID
	 * 
	 * @param mainAffairId ������ID
	 * @param subAffairIDs �ε���ID�����Զ��ŷָ
	 * @return
	 */
	public boolean updateMergeAffairs(String mainAffairId,String subAffairIDs)
	{
		boolean succeed = false;	//���������ֻ������ȷִ�и��£��ý����Ϊture
		StringBuffer updateSQL = new StringBuffer("UPDATE  t_attemper_alarm t ");
		updateSQL.append(" SET t.mergedstate='" + ConstDefine.MAIN_ALARM_STATE + "' ");
		updateSQL.append(" WHERE t.alarmid='" +  mainAffairId + "'");		//��������
		
		StringBuffer subUpdateSQL = new StringBuffer("UPDATE  t_attemper_alarm t ");
		subUpdateSQL.append(" SET t.mergedstate='" + ConstDefine.SUB_ALARM_STATE + "', ");
		subUpdateSQL.append(" t.mainalarmid='" + mainAffairId + "' " );
		subUpdateSQL.append(" WHERE t.alarmid in (" +  subAffairIDs + ")");	//���·ֵ�
		
		Database db = null;
		try
		{
			db = DBFactory.newDatabase();	//�������ݷ��ʶ���ִ�и�������
			db.beginTrans();
			if( 0 < db.executeUpdate(updateSQL.toString() ))
			{
				if( 0 < db.executeUpdate(subUpdateSQL.toString()))
				{
					succeed = true;	//ֻ�гɹ�ִ��2���������succeed��Ϊtrue
				}
			}
		}
		catch(Exception ex)
		{
			com.appframe.Console.infoprintln("ִ�о��鵥�ϲ������쳣 ----->" + ex.getMessage());
			logger.error("ִ�о��鵥�ϲ����ݿ����!");
		}
		finally
		{
			if(null != db)
			{
				if(succeed)	//�ύ����
				{
					db.commitTrans(false);
				}
				else
				{
					db.commitTrans(true);
				}
				//�ر����ݶ���
				DBFactory.closeDatabase(db);
			}
		}
		MergeMessage.sendMergeMessage(mainAffairId, subAffairIDs);
		
		return succeed;
	}
	
	
	
	
	
}
