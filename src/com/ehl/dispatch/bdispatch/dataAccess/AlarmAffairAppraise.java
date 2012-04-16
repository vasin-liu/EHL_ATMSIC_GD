package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairAppraise.java
 * 
 * 摘 要：警情处理评价信息类。
 * 			
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

public class AlarmAffairAppraise
{
	
	private static final Logger logger = Logger.getLogger(AlarmAffairAppraise.class);
	/**查询具体警情的评价信息
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
			logger.error("查询具体警情的评价信息数据库错误!");
		}
		return result;
	}
}
