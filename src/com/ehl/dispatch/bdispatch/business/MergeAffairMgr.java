package com.ehl.dispatch.bdispatch.business;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.bdispatch.dataAccess.MergeAffairs;
import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairQueryMgr.java
 * 
 * ժ Ҫ��ָ�ӵ��ȼ�ؾ����¼��ϲ������ࡣ
 * 		 ��ѯ�ľ����ժҪ���ϲ�������Ϣ��
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

public class MergeAffairMgr
{
	/**��ѯҪ�ϲ��ľ�����Ϣ
	 * 
	 * @param affairIDs �����ʶ�����Զ��ŷָ
	 * @return
	 */
	public String readAffairBrief(String affairIDs)
	{ 
		if(null == affairIDs || "".equals(affairIDs) || affairIDs.split(",").length<2 )
		{
			return null;
		}
		
		//�������ݷ�����
		//��ѯ���徯���ʶ�ľ�����Ϣ
		MergeAffairs dbQuery  = new MergeAffairs();		
		Object [][] records = dbQuery.readAffairByIDs(affairIDs);
	  	
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
	
	/**�ϲ����鵥
	 * 
	 * @param mainAffairId
	 * @param subAffairIDs
	 * @return ���ؿգ���ʾ�����ɹ������򷵻ز���ʧ����������
	 */
	public String mergeAffairs(String mainAffairId ,String subAffairIDs)
	{
		String message = null;
		if(null == subAffairIDs || "".equals(subAffairIDs) || null == mainAffairId || "".equals(mainAffairId) )
		{
			message = "������Ϣ��ε���Ϣ��ʧ���ϵ�����ʧ�ܣ�";
		}
		else
		{
			MergeAffairs dbUpdate  = new MergeAffairs();	
			subAffairIDs=checkSub(subAffairIDs)+subAffairIDs;
			if(! dbUpdate.updateMergeAffairs(mainAffairId,subAffairIDs) )
			{
				message = "ִ�кϵ�����ʧ�ܣ�";
			}
		}
		return message;
	}
	
	/**
	 * �����ѡ�ӵ��Ƿ����ӵ�������У�����ѡ�ӵ����е��ӵ�����ѡ�ӵ��ϲ�Ϊ�ӵ�
	 * @param subAffairIDs
	 * @return
	 */
	public String checkSub(String subAffairIDs){
		String sid="";
		if(!subAffairIDs.equals("")){
			String[] ids=subAffairIDs.split(",");
			for(int i=0;i<ids.length;i++){
				String sql="select MERGEDSTATE from T_ATTEMPER_ALARM where ALARMID='"+ids[i]+"'";
				Object state=DBHandler.getSingleResult(sql);
				if(state!=null&&state.toString().equals(ConstDefine.MAIN_ALARM_STATE)){
					String serchsql="select ALARMID from T_ATTEMPER_ALARM where MAINALARMID='"+ids[i]+"'";
					Object[][] sids=DBHandler.getMultiResult(serchsql);
					if(sids!=null){
						for(int j=0;j<sids.length;j++){
							sid+=sids[j][0]+",";
						}
					}
				}
			}
			
		}
		return sid;
	}
}
