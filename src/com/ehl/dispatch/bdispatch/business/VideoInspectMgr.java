package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.VideoInspect;
import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;
import com.ehl.dispatch.bdispatch.util.*;
/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：VideoInspectMgr.java
 * 
 * 摘 要：视频监控管理类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-14
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */

public class VideoInspectMgr
{
	VideoInspect videoDataAccess = null;	//视频设备数据访问对象
	
	//通过视频设备唯一标识获取视频设备摘要信息
	public String readVideoBriefById(String videoId)
	{
		if( null == videoId)
		{
			return null;
		}
	 	if(null == videoDataAccess )
		{
			videoDataAccess = new VideoInspect();
		}
		Object [][]videoData = videoDataAccess.getVideoDataById(videoId);
		if( null == videoData)
		{
			return null;
		}
		return XmlDataCreator.buildXmlDocument(videoData, "row");	//构造xml数据
	}
	
	//读取视频设备的摘要信息
	public String readVideosBrief(String countyCode)
	{
		if( null == countyCode)
		{
			return null;
		}
	 	if(null == videoDataAccess )
		{
			videoDataAccess = new VideoInspect();
		}
	 	
		Object [][]videoData = null;
		
		if(-1 != countyCode.indexOf(ConstDefine.DETACHMENT))	//是否为支队判断
		{
			videoData = videoDataAccess.getVideoDataList();
		}
		else
		{
			videoData = videoDataAccess.getVideoDataList(countyCode);
		}
	
		if( null == videoData)
		{
			return null;
		}
		return XmlDataCreator.buildXmlDocument(videoData, "row");
	}
}
