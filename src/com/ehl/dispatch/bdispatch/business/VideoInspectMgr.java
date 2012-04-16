package com.ehl.dispatch.bdispatch.business;

import com.ehl.dispatch.bdispatch.dataAccess.VideoInspect;
import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.XmlDataCreator;
import com.ehl.dispatch.bdispatch.dataAccess.*;
import com.ehl.dispatch.bdispatch.util.*;
/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�VideoInspectMgr.java
 * 
 * ժ Ҫ����Ƶ��ع����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-14
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */

public class VideoInspectMgr
{
	VideoInspect videoDataAccess = null;	//��Ƶ�豸���ݷ��ʶ���
	
	//ͨ����Ƶ�豸Ψһ��ʶ��ȡ��Ƶ�豸ժҪ��Ϣ
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
		return XmlDataCreator.buildXmlDocument(videoData, "row");	//����xml����
	}
	
	//��ȡ��Ƶ�豸��ժҪ��Ϣ
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
		
		if(-1 != countyCode.indexOf(ConstDefine.DETACHMENT))	//�Ƿ�Ϊ֧���ж�
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
