package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.DictateDisposal;
import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;
import java.util.Map;
import java.util.Date;
import com.ehl.dispatch.bdispatch.util.*;
import com.appframe.utils.StringHelper;
import com.appframe.utils.DateUtil;
/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�DictateDisposalMgr.java
 * 
 * ժ Ҫ��ָ��ָʾ��Ϣ�����ࡣ
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
public class DictateDisposalMgr
{
	//��ȡָ��ָʾ������Ϣ
	public String getDictateDetailById(String dictateId,String userCountyCode )
	{
		if(null == dictateId || 0>=dictateId.length() || null ==userCountyCode || 0>=userCountyCode.length() )
		{
			return null;
		}
		DictateDisposal dbDictate = new DictateDisposal();	//ͨ��ID��ȡ������Ϣ
		Object [][]dictateData = dbDictate.getDictateDataById(dictateId,userCountyCode);
		if( null == dictateData)
		{
			return null;
		}
		String fullfillId = StringHelper.obj2str( dictateData[0][9],"");	//���½���ʱ�䣬����ʵ״̬
		if( ConstDefine.DICTATE_DILIVER_CODE.equals(StringHelper.obj2str( dictateData[0][10],"") ))
		{
			dbDictate.inceptDictate(fullfillId, DateUtil.dateToString( new Date(), "yyyy-MM-dd HH:mm"));
		}
		return XmlDataCreator.buildXmlDocument(dictateData, "row");	//����xml����
	}
	
	//��ȡ��ʵ������Ϣ
	public String getFullfillDetailById(String fullfillId)
	{
		if(null == fullfillId || 0>=fullfillId.length())
		{
			return null;
		}
		DictateDisposal dbDictate = new DictateDisposal();
		
		Object [][]dictateData = dbDictate.getFullfillDataById(fullfillId);
		if( null == dictateData)
		{
			return null;
		}
		
		return XmlDataCreator.buildXmlDocument(dictateData, "row");	//����xml����
	}
	
	//������ʵ��Ϣ
	public boolean writeFullfillData(String fullfillId, Map<String,String> fullfillMap)
	{
		//�ж϶����Ƿ����
		if(null == fullfillId || 0>=fullfillId.length())
		{
			return false;
		}
		if(null == fullfillMap )
		{
			return false;
		}
		DictateDisposal dbDictate = new DictateDisposal();//ִ�и��²���
		return dbDictate.updateFullfillData(fullfillId, fullfillMap);
	}
	
}
