package com.ehl.dispatch.bdispatch.business;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.bdispatch.dataAccess.MergeAffairs;
import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairQueryMgr.java
 * 
 * 摘 要：指挥调度监控警情事件合并管理类。
 * 		 查询的警情的摘要，合并警情信息。
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-10
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class MergeAffairMgr
{
	/**查询要合并的警情信息
	 * 
	 * @param affairIDs 警情标识串（以逗号分割）
	 * @return
	 */
	public String readAffairBrief(String affairIDs)
	{ 
		if(null == affairIDs || "".equals(affairIDs) || affairIDs.split(",").length<2 )
		{
			return null;
		}
		
		//构造数据访问类
		//查询具体警情标识的警情信息
		MergeAffairs dbQuery  = new MergeAffairs();		
		Object [][] records = dbQuery.readAffairByIDs(affairIDs);
	  	
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
	
	/**合并警情单
	 * 
	 * @param mainAffairId
	 * @param subAffairIDs
	 * @return 返回空，表示操作成功，否则返回操作失败文字描述
	 */
	public String mergeAffairs(String mainAffairId ,String subAffairIDs)
	{
		String message = null;
		if(null == subAffairIDs || "".equals(subAffairIDs) || null == mainAffairId || "".equals(mainAffairId) )
		{
			message = "主单信息或次单信息丢失，合单操作失败！";
		}
		else
		{
			MergeAffairs dbUpdate  = new MergeAffairs();	
			subAffairIDs=checkSub(subAffairIDs)+subAffairIDs;
			if(! dbUpdate.updateMergeAffairs(mainAffairId,subAffairIDs) )
			{
				message = "执行合单操作失败！";
			}
		}
		return message;
	}
	
	/**
	 * 检查所选子单是否还有子单，如果有，则将所选子单所有的子单与所选子单合并为子单
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
