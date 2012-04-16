package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairAppraise.java
 * 
 * ժ Ҫ�����鴦��������Ϣ�ࡣ
 * 			
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

public class AlarmAffairAppraise
{
	
	private static final Logger logger = Logger.getLogger(AlarmAffairAppraise.class);
	/**��ѯ���徯���������Ϣ
	 * 
	 * @return
	 */
	public Object [][] getAffairAppraiseByAlarmId(String alarmId)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		   selectSQL.append("SELECT t.evaluateid,to_char(t.evaluatetime,'yyyy-MM-dd HH24:mi'),t.evaluateperson,t.evaluateunits,");
		   selectSQL.append("t.evaluatedesc,t.remark,t.eventefficiency,ts1.jgmc,tc1.name");
		   selectSQL.append(" FROM T_ATTEMPER_EVALUATE t, t_sys_department ts1, t_attemper_code tc1 ");
		   selectSQL.append(" where tc1.id(+) = t.eventefficiency AND t.evaluateunits = ts1.jgid(+) ");
		   selectSQL.append(" AND  t.alarmid='" + alarmId + "'");
		   com.appframe.Console.infoprintln(" SQL --->" + selectSQL.toString());
		   result = DBHandler.getMultiResult(selectSQL.toString());
		}
		catch(Exception e) {
			logger.error("��ѯ���徯���������Ϣ���ݿ����!");
		}
		return result;
	}
}
