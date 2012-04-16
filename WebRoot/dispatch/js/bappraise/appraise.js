/**
 * 
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 文件名称：appraise.js
 * 摘 要：事件评价信息前台处理。


 * 当前版本：1.0
 * 作 者：LChQ  2009-4-20
 * 修改人：
 * 修改日期：
 */

function AffairApparise(alarmId)
{
	this.alarmId = alarmId;
	this.m_appraiseData = null;	//数据存储对象
	
	//数据项定义
	this.AppraiseItems = ["appraiseId","appraiseTime","appraiser","appraiseCountyCode",
							"appraiseContent","remark","efficiencyCode","appraiseCountyText","efficiencyText"];
}

//通过事件的标识获取事件的评价信息
AffairApparise.prototype.getAppraiseByAlarmId = function()
{
	if(! this.alarmId  || 0== this.alarmId.length )
	{
		return;
	}
	var url = 'dispatch.appraise.getAppariseByAlarmId.d';
	var params = "ALARMID=" + this.alarmId;
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
					alert("获取事件评价信息失败.");
					return ;
				}
				theCaller.parseAppraiseDetail(xmlDoc);
				theCaller.showAppraiseDetail();
			}
	});
};

//解析后台得到的评价信息
AffairApparise.prototype.parseAppraiseDetail = function(xmlDoc)
{
	this.m_appraiseData = null;
	
	var results = xmlDoc.getElementsByTagName("row");
	if(0 < results.length)
	{	
		this.m_appraiseData = {};
		//循环获取各信息项
		var columnResults = results[0].getElementsByTagName("col");
		for(var j=0; j<columnResults.length; j+=1)
		{
	 		this.m_appraiseData[this.AppraiseItems[j]] = columnResults[j].text;
		}
	}
};


//在界面显示评价内容
AffairApparise.prototype.showAppraiseDetail = function()
{
	if( this.m_appraiseData )
	{
		 $('txtAppraiseTime').innerHTML = this.m_appraiseData.appraiseTime; //评价时间 
		 $('txtAppraiser').innerHTML = this.m_appraiseData.appraiser; //评价人
		 
		 $('txtAppraiserCounty').innerHTML = this.m_appraiseData.appraiseCountyText; //评价单位
		 $('txtEfficiency').innerHTML = this.m_appraiseData.efficiencyText;			//处理效率
		 $('tdAppraiseContent').innerHTML = this.m_appraiseData.appraiseContent; 		//评价内容
	}
};
 