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
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：DictateDisposalMgr.java
 * 
 * 摘 要：指令指示信息管理类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-16
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */
public class DictateDisposalMgr
{
	//获取指令指示基本信息
	public String getDictateDetailById(String dictateId,String userCountyCode )
	{
		if(null == dictateId || 0>=dictateId.length() || null ==userCountyCode || 0>=userCountyCode.length() )
		{
			return null;
		}
		DictateDisposal dbDictate = new DictateDisposal();	//通过ID获取基本信息
		Object [][]dictateData = dbDictate.getDictateDataById(dictateId,userCountyCode);
		if( null == dictateData)
		{
			return null;
		}
		String fullfillId = StringHelper.obj2str( dictateData[0][9],"");	//更新接收时间，和落实状态
		if( ConstDefine.DICTATE_DILIVER_CODE.equals(StringHelper.obj2str( dictateData[0][10],"") ))
		{
			dbDictate.inceptDictate(fullfillId, DateUtil.dateToString( new Date(), "yyyy-MM-dd HH:mm"));
		}
		return XmlDataCreator.buildXmlDocument(dictateData, "row");	//构造xml数据
	}
	
	//读取落实基本信息
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
		
		return XmlDataCreator.buildXmlDocument(dictateData, "row");	//构造xml数据
	}
	
	//更新落实信息
	public boolean writeFullfillData(String fullfillId, Map<String,String> fullfillMap)
	{
		//判断对象是否存在
		if(null == fullfillId || 0>=fullfillId.length())
		{
			return false;
		}
		if(null == fullfillMap )
		{
			return false;
		}
		DictateDisposal dbDictate = new DictateDisposal();//执行更新操作
		return dbDictate.updateFullfillData(fullfillId, fullfillMap);
	}
	
}
