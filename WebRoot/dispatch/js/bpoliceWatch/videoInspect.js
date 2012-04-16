/***********************************************************************

版 权：北京易华录信息技术股份有限公司 2009

文件名称：videoInspect.js
摘 要：	视频监控信息处理
		视频基本信息处理

当前版本：1.0
作 者：LChQ  2009-4-14 

***************************************************************************/


function VideoDevice()
{
	this.m_videoData = null;
	this.videoDataItems = ["videoId","equipText","ip","serverId","longtitude","latitude","equipSite","branch","equipStatus", "equipType"];
	
	this.DETACHMENT = '00000000';	//支队判断字符串
}

VideoDevice.prototype.getVideoDataByID = function(videoID)
{
	
	if(! videoID  || 0==videoID.length )
	{
		
		return;
	}
	var url = 'dispatch.videoInspect.getVideoById.d';
	var params = "VIDEOID=" + videoID;
	
	params = encodeURI(params);
	
	var theCaller = this;
	
	//读取数据
	new Ajax.Request(url, 
	{
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: function(xmlHttp)
			{
				var xmlDoc = xmlHttp.responseXML;
				if(xmlDoc.text == "")
				{
					alert("获取视频信息失败.");
					return ;
				}
				theCaller.parseVideoDetail(xmlDoc);
				theCaller.showVideoDetail();
			}
	});
};

//显示视频设备信息
VideoDevice.prototype.showVideoDetail = function()
{
	 
};

//解析设施相信信息
//将相信信息保存到数据中
VideoDevice.prototype.parseVideoDetail = function(xmlDoc)
{
	this.m_videoData = null;
	this.m_videoData = {};		//初始化视频详细信息数据对象
	var results = xmlDoc.getElementsByTagName("row");
	 
	if(0 < results.length)
	{	
		//循环获取各信息项
		results = results[0].getElementsByTagName("col");
		for (var i = 0; i < results.length; i+=1)
		{
		 	this.m_videoData[this.videoDataItems[i]] = results[i].text;
		}
	}
};


function VideoInspect()
{
	VideoDevice.call(this);
	this.m_videoList = null;
	
	this.listDataItems =  ["videoId","cpmc","ip","serverId","longtitude","latitude", "equipType","symbolId"];
}

VideoInspect.prototype = new VideoDevice();

//读取辖区内的视频设备信息列表
VideoInspect.prototype.readCountyVideo = function()
{
	var url = 'dispatch.videoInspect.getVideoList.d';
	var params = "countyCode=" + userCountyCode ;
	params = encodeURI(params);
	var theCaller = this;
	//读取数据
	new Ajax.Request(url, 
	{
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: function(xmlHttp)
			{
				var xmlDoc = xmlHttp.responseXML;
				if(xmlDoc.text == "")
				{
					//alert("获取视频信息失败.");
					return ;
				}
				theCaller.parseVideoList(xmlDoc);
				theCaller.showVideoInfo();
			}
	});
};

VideoInspect.prototype.parseVideoList = function(xmlDoc)
{
	this.m_videoList = null;
	this.m_videoList = new Array();		//视频信息列表 
	var results = xmlDoc.getElementsByTagName("row");
	
	MapUtils.prototype.moveToAreaCenter(userCountyCode) 
	if(0 < results.length)
	{	
		//循环获取各信息项
		for (var i = 0; i < results.length; i+=1)
		{
			
			
			
			var newRecord = results[i].getElementsByTagName("col");
			var videoData = {};
			for (var j = 0; j < newRecord.length; j+=1)
			{
		 		videoData[this.listDataItems[j]] = newRecord[j].text;
			}
			this.m_videoList.push(videoData);
		}
	}
	
};


