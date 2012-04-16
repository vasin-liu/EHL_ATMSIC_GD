package com.ehl.dispatch.bdispatch.dataAccess;

import com.appframe.data.sql.DBHandler;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.*;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�DictateDisposal.java
 * 
 * ժ Ҫ��ָ��ָʾ��Ϣ���ݿ�����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-16
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class DictateDisposal
{
	
	private static final Logger logger = Logger.getLogger(DictateDisposal.class);
	/**����ָ��ָʾ��ʶ��ȡָ��ָʾ��ϸ
	 * 
	 */
	public Object [][] getDictateDataById(String dictateId,String userCountyCode)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT t1.denoteid,t1.leadername,to_char(t1.denotetime,'yyyy-MM-dd HH24:mi'),t1.denotedesc ,");
		selectSQL.append(" tc1.name dSource,tc2.name dType,tc3.name cState,t1.createrecorder,t1.remark,t2.id,t2.state ");
		selectSQL.append(" FROM ");
		selectSQL.append(" t_Attemper_LeaderDenote t1,T_Attemper_RunDenote t2, t_attemper_code tc1,t_attemper_code tc2,t_attemper_code tc3 ");
		selectSQL.append(" WHERE t1.denoteid=t2.denoteid  AND t1.denotesource=tc1.id(+) AND t1.denotetype=tc2.id(+) AND t2.state = tc3.id(+) ");
		selectSQL.append("	AND  t1.denoteid='" + dictateId +  "'  AND t2.finishunit='" + userCountyCode + "'" );
	 
		
		com.appframe.Console.infoprintln("getDictateDataById  SQL -------->"  + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString());
		}
		catch(Exception e) {
			logger.error("��ѯ���ע������Ϣ���ݿ����!");
		}
		return result;
	}
	
	/**������ʵ��ʶ��ȡ��ʵ��Ϣ
	 * 
	 */
	public Object [][] getFullfillDataById(String fullfillId)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT t.id,t.denoteid,to_char( t.accepttime,'yyyy-MM-dd HH24:mi'),t.state,");
		selectSQL.append(" to_char( t.finishtime,'yyyy-MM-dd HH24:mi'),t.finishdesc,t.finishrecorder ");
		selectSQL.append(" FROM t_attemper_rundenote t WHERE t.id='" + fullfillId + "'"); 
		com.appframe.Console.infoprintln("getFullfillDataById  SQL -------->"  + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("������ʵ��ʶ��ȡ��ʵ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	
	/**������ʵ��ʶ��ȡ��ʵ��Ϣ
	 * 
	 */
	public boolean inceptDictate(String fullfillId,String inceptTime)
	{
		StringBuffer updateSQL = new StringBuffer();
		boolean result = false;
		try {
		updateSQL.append("UPDATE t_attemper_rundenote t SET "  );
		updateSQL.append(" t.accepttime=to_date('" + inceptTime + "','yyyy-MM-dd HH24:mi'), ");
		updateSQL.append(" t.state='" + ConstDefine.DICTATE_PROCESSING_CODE + "' ");
		
		updateSQL.append("  WHERE t.id='" + fullfillId + "'"); 
	 
		
		com.appframe.Console.infoprintln("inceptDictate  SQL -------->"  + updateSQL.toString());
		result = DBHandler.execute(updateSQL.toString());
		}catch(Exception e) {
			logger.error("������ʵ��ʶ��ȡ��ʵ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	
	/**������ʵ��Ϣ
	 * 
	 */
	public boolean updateFullfillData(String fullfillId,Map<String,String> fullfillData)
	{
		StringBuffer updateSQL = new StringBuffer();
		boolean result = false;
		try {
		updateSQL.append("UPDATE t_attemper_rundenote t SET "  );	//����������
		updateSQL.append(" t.state='" + StringHelper.obj2str(fullfillData.get("STATE"),"") + "',t.finishtime=");
		if( "".equals(StringHelper.obj2str(fullfillData.get("COMPLETETIME"),"")))
		{
			updateSQL.append( "'',t.finishdesc='");
		}
		else
		{
			updateSQL.append("to_date('" + fullfillData.get("COMPLETETIME") + "','yyyy-MM-dd HH24:mi'),t.finishdesc='");
		}
		updateSQL.append( StringHelper.obj2str( fullfillData.get("CONTENT"),"") + "',t.finishrecorder='" );
//		updateSQL.append( fullfillData.get("RECORDER") + "',t.accepttime='" );
		updateSQL.append( StringHelper.obj2str(fullfillData.get("RECORDER"),"") + "',t.remark='" );
		updateSQL.append( StringHelper.obj2str(fullfillData.get("REMARK"),"") + "'");
		updateSQL.append("  WHERE t.id='" + fullfillId + "'"); 
		com.appframe.Console.infoprintln("getFullfillDataById  SQL -------->"  + updateSQL.toString());
		result = DBHandler.execute(updateSQL.toString());	//���ظ��½��
		}catch(Exception e) {
			logger.error("������ʵ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	
	
}
