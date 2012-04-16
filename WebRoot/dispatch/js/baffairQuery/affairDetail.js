/**
 * 
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 文件名称：affairDetail.js
 * 摘 要：警情信息查看。


 * 当前版本：1.0
 * 作 者：LChQ  2009-4-21
 * 修改人：
 * 修改日期：
 */
 
//事故信息
function AccidentDetail()
{
	this.affairItems = "alarmId,inceptCounty,inceptPerson,inceptTime,reportTime,inceptManner,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPerson,concatTel,reportTel,reportPersonSex,reportPersonAddress,phoneOwner,phoneOwnerAddress,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,vechileNumStyle,vechileRegNumber,isDangerCargo,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,caseHappen,completeTime,";
	this.affairItems += "economyLosing,fluencyDepth,dispatchCar,dispatchPersonCount,injuredCount,woundedCount,hurtCount,deadCount,";
	this.affairItems += "underarestCount,relatedCount,helpedCount,isLawCase,isSocialCase,isMistakeCase,";
	this.affairItems += "isFeedbackComplete,chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptPerson,tdInceptTime,tdCallTime,tdInceptManner,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdConcatTel,tdReportTel,tdReportPersonSex,tdReportPersonAddress,tdPhoneOwner,tdPhoneOwnerAddress,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,tdVechileNumStyle,tdVechileRegNumber,,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCaseHappen,tdCompleteTime,";
	this.htmlAffairIDList += "tdEconomyLosing,tdFluencyDepth,tdDispatchCar,tdDispatchPersonCount,tdInjuredCount,tdWoundedCount,tdHurtCount,tdDeadCount,";
	this.htmlAffairIDList += "tdUnderarestCount,tdRelatedCount,tdHelpedCount,,,,";
	this.htmlAffairIDList += "";
}

//拥堵详细信息
function TrafficJamDetail()
{
	this.affairItems = "alarmId,inceptCounty,inceptPerson,inceptTime,reportTime,inceptManner,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPerson,concatTel,reportTel,reportPersonSex,reportPersonAddress,phoneOwner,phoneOwnerAddress,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,vechileNumStyle,vechileRegNumber,isDangerCargo,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,caseHappen,completeTime,";
	this.affairItems += "economyLosing,fluencyDepth,dispatchCar,dispatchPersonCount,injuredCount,woundedCount,hurtCount,deadCount,";
	this.affairItems += "underarestCount,relatedCount,helpedCount,isLawCase,isSocialCase,isMistakeCase,";
	this.affairItems += "isFeedbackComplete,chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptPerson,tdInceptTime,tdCallTime,tdInceptManner,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdConcatTel,tdReportTel,tdReportPersonSex,tdReportPersonAddress,tdPhoneOwner,tdPhoneOwnerAddress,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,tdVechileNumStyle,tdVechileRegNumber,,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCaseHappen,tdCompleteTime,";
	this.htmlAffairIDList += "tdEconomyLosing,tdFluencyDepth,tdDispatchCar,tdDispatchPersonCount,tdInjuredCount,tdWoundedCount,tdHurtCount,tdDeadCount,";
	this.htmlAffairIDList += "tdUnderarestCount,tdRelatedCount,tdHelpedCount,,,,";
	this.htmlAffairIDList += "";
}

//大型故障车详细信息
function TroubleCargoDetail()
{
	this.affairItems = "alarmId,inceptCounty,incepttime,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPerson,callerTel,callerUnit,troubleCause,troubleLocal,vechileStyle,driverName,fluencyDepth,cargoCounty,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,caseHappen,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdCallTime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdCallerTel,tdCallerUnit,tdTroubleCause,tdTroubleLocal,tdVechileStyle,tdDriverName,tdFluencyDepth,tdCargoCounty,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCaseHappen,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}
//恶劣天气详细信息
function DisasterWeatherDetail()
{
	this.affairItems = "alarmId,inceptCounty,incepttime,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPerson,reportPhone,callerUnit,fluencyCounty,fluencyArea,weatherDesc,fluencyRoad,roadDesc,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,caseHappen,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdReporttime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdReportPhone,tdCallerUnit,tdFluencyCounty,tdFluencyArea,tdWeatherDesc,tdFluencyRoad,tdRoadDesc,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCaseHappen,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}

//煤气、热力泄露、自来水等事件详细信息
function CitizenCaseDetail()
{
	this.affairItems = "alarmId,inceptCounty,incepttime,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPerson,reportPhone,callerUnit,caseHappen,fluencyArea,fluencyDepth,fluencyRoad,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdCallerTime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdReportPhone,tdCallerUnit,tdCaseHappen,tdFluencyArea,tdFluencyDepth,tdFluencyRoad,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}


//公路桥梁、泥石流塌方等地质事件详细信息
function GeologicalDisasterDetail()
{
	this.affairItems = "alarmId,inceptCounty,incepttime,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPerson,reportPhone,callerUnit,fluencyArea,fluencyDepth,fluencyRoad,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,caseHappen,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdCallTime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdReportPhone,tdCallerUnit,tdFluencyArea,tdFluencyDepth,tdFluencyRoad,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCaseHappen,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}

//查缉布控（嫌疑车辆）详细信息
function SuspicionCarDetail()
{
	this.affairItems = "alarmId,inceptCounty,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "regNumber,cardStyle,helpPersonCount,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "dispatchTime,onsignTime,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdRegNumber,tdCardStyle,tdHelpPersonCount,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdDispatchTime,tdOnsignTime,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}

//治安事件详细信息
function SocialCaseDetail()
{
	this.affairItems = "alarmId,inceptCounty,incepttime,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPreson,callerTel,callerUnit,caseHappen,fluencyArea,fluencyRoad,fluencyDepth,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "cmdCenterIncepter,dispatchTime,onsignTime,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdCallTime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdCallerTel,tdCallerUnit,tdCaseHappen,tdFluencyArea,tdFluencyRoad,tdFluencyDepth,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdCmdCenterIncepter,tdDispatchTime,tdOnsignTime,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}

//火灾,爆炸事件详细信息
function FireAndBoomDetail()
{
	this.affairItems = "alarmId,inceptCounty,incepttime,reportTime,alarmContent,affairSRC,affairRank,affairState,";
	this.affairItems += "reportPreson,callerTel,callerUnit,caseHappen,fluencyRoad,fluencyDepth,personLoss,woundedCount,hurtCount,deadCount02,";
	this.affairItems += "economyLosing,helpPersonCount,";
	this.affairItems += "occurLocation,occurCounty,alarmType,alarmSubType,";
	this.affairItems += "handleTime,handlePerson,handleCounty,handleOpinion,dispatchCounty,dispatchPerson,";
	this.affairItems += "cmdCenterIncepter,dispatchTime,onsignTime,completeTime,";
	this.affairItems += "dispatchCar,dispatchPersonCount,";
	this.affairItems += "chiefAffairId,subAffairs";
	
	this.htmlAffairIDList = "tdAlarmId,tdInceptCounty,tdInceptTime,tdCallTime,tdAlarmContent,,tdAffairRank,,";  
	this.htmlAffairIDList += "tdReportPerson,tdCallerTel,tdCallerUnit,tdCaseHappen,tdFluencyRoad,tdFluencyDepth,tdPersonLoss,tdWoundedCount,tdHurtCount,tdDeadCount02,";
	this.htmlAffairIDList += "tdEconomyLosing,tdHelpPersonCount,";
	this.htmlAffairIDList += "tdOccurLocation,tdOccurCounty,tdAlarmType,tdAlarmSubType,";
	this.htmlAffairIDList += "tdHandleTime,tdHandlePerson,,tdHandleOpinion,tdDispatchCounty,tdDispatchPerson,";
	this.htmlAffairIDList += "tdCmdCenterIncepter,tdDispatchTime,tdOnsignTime,tdCompleteTime,";
	this.htmlAffairIDList += "tdDispatchCar,tdDispatchPersonCount,";
	this.htmlAffairIDList += "";
}

var affairSortList = { 
	"accident" : "001001",	// 事故
	"trafficjam" : "001002",  // 拥堵
	"breakoutCase" : "001003",	//突发事件
	"assembleCase" : "001004",	//群体性事件
	"suspicionCar" : "001005",		//嫌疑车辆
	"disasterWeather" : "001006",	//灾害天气
	"socialCase" : "001007",	//治安事件
	"troubleCargo" : "001008",	//大型车故障
	"otherElse" : "001009",		//其他事件
	"geologicalDisaster" : "001010",		//地质灾害
	"citizenCase" : "001011",		//市政事件
	"fireAndBoom" : "001012"	//火灾爆炸
};

var affairDetail = null;
function AffairDetail(p_alarmId,p_alarmType)
{
	this.alarmId = p_alarmId;		  //报警单号
	this.alarmType = p_alarmType;	 //报警类型
	this.m_affairData = null;
	
	this.affairObject = null;
	
	switch(this.alarmType)
	{
		case affairSortList.accident:
			this.affairObject = new AccidentDetail();
			break;
		case affairSortList.trafficjam :
			this.affairObject = new TrafficJamDetail();
			break;
		case affairSortList.troubleCargo :
			this.affairObject = new TroubleCargoDetail();
			break;
		case affairSortList.disasterWeather :
			this.affairObject = new DisasterWeatherDetail();
			break;
		case affairSortList.citizenCase :
			this.affairObject = new CitizenCaseDetail();
			break;
		case affairSortList.geologicalDisaster :
			this.affairObject = new GeologicalDisasterDetail();
			break;
		case affairSortList.suspicionCar :
			this.affairObject = new SuspicionCarDetail();
			break;
		case affairSortList.socialCase :	//治安事件
			this.affairObject = new SocialCaseDetail();
			break;
		case affairSortList.fireAndBoom :	//火灾爆炸事件
			this.affairObject = new FireAndBoomDetail();
			break;
			
	}
	

}

function initPageWithData()
{ 
	affairDetail = new AffairDetail(alarmId,alarmType);
	affairDetail.getAffairDetail();
}

//获取警情详细信息
AffairDetail.prototype.getAffairDetail = function()
{
	var url = 'dispatch.affairWatch.getAffairDetail.d';
	var params = "ALARMID=" + this.alarmId;
	params += "&ALARMTYPE=" + this.alarmType;
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
					alert("获取警情详细信息失败.");
					return ;
				}
				theCaller.parseAffairDetail(xmlDoc);
				theCaller.showAffairDetail(); 
				theCaller.getFeedBackInfo(); 
				 //判断数据是否存在
				 //然后构造html文本显示
				if(theCaller.m_affairData && theCaller.m_affairData.subAffairs && 0<theCaller.m_affairData.subAffairs.length)
				{
					var hrefHtml = theCaller.createSubAffairsHref(theCaller.m_affairData.subAffairs);
					if(hrefHtml )
					{
						$('tdRelatedAlarms').innerHTML = hrefHtml;
					}
				}
			}
	});
};

AffairDetail.prototype.parseAffairDetail = function(xmlDoc)
{	
	this.m_affairData = null;
	var results = xmlDoc.getElementsByTagName("row");
	if(0 < results.length)
	{	
		this.m_affairData = {};
		var itemArray = this.affairObject.affairItems.split(",");
		//循环获取各信息项
		var columnResults = results[0].getElementsByTagName("col");
		for(var j=0; j<columnResults.length; j+=1)
		{
	 		this.m_affairData[itemArray[j]] = columnResults[j].text;
		}
	}
};

//在界面显示评价内容
AffairDetail.prototype.showAffairDetail = function()
{
	if( this.m_affairData )
	{
		var htmlArray = this.affairObject.htmlAffairIDList.split(",");
		var i=0;
		for(var ele in this.m_affairData)
		{
			if(htmlArray[i] && 0<htmlArray[i].length && $(htmlArray[i]))
			{
				$(htmlArray[i]).innerHTML = ("" == this.m_affairData[ele]?"&nbsp;":this.m_affairData[ele]);
			}
			i+=1;
		}
		if(this.m_affairData.deadCount )
		{								//死亡人数
			$('tdDeadCount02').innerHTML =("" == this.m_affairData.deadCount?"&nbsp;":this.m_affairData.deadCount); 
		}
		if($('tdIsLawCase'))		//是否为刑事案件
		{
			$('tdIsLawCase').innerHTML = this.getBooleanDesc( this.m_affairData.isLawCase );
		}
		 
		if($('tdIsSocialCase'))		//是否为治安案件 
		{
			$('tdIsSocialCase').innerHTML = this.getBooleanDesc( this.m_affairData.isSocialCase );
		}
		
		if($('tdIsFeedbackComplete'))	//是否反馈终结
		{
			$('tdIsFeedbackComplete').innerHTML = this.getBooleanDesc( this.m_affairData.isFeedbackComplete );
		}
		 
		if($('tdIsMistakeCase'))		
		{
			$('tdIsMistakeCase').innerHTML = this.getBooleanDesc( this.m_affairData.isMistakeCase );
		}
		if($('tdIsDangerCargo'))	//是否危化车
		{
			$('tdIsDangerCargo').innerHTML = this.getBooleanDesc( this.m_affairData.isDangerCargo );
		}
//		if(	this.m_affairData.handlePerson && $('tdCmdIncepter'))
//		{
//			$('tdCmdIncepter').innerHTML =  ("" == this.m_affairData.handlePerson?"&nbsp;":this.m_affairData.handlePerson);   
//		}
		if( this.m_affairData.dispatchPerson )
		{
			$('tdDispatchPerson02').innerHTML =  ("" == this.m_affairData.dispatchPerson?"&nbsp;":this.m_affairData.dispatchPerson);  
		}
		if(this.m_affairData.alarmType)
		{
			$('tdCaseType').innerHTML =  ("" == this.m_affairData.alarmType?"&nbsp;":this.m_affairData.alarmType);  
		}
		if(this.m_affairData.alarmSubType)
		{
			$('tdCaseSubType').innerHTML =  ("" == this.m_affairData.alarmSubType?"&nbsp;":this.m_affairData.alarmSubType); 
	 	}
	 	
	 	//是否与其它警情单合并
	 	if( this.m_affairData.chiefAffairId  && 0<this.m_affairData.chiefAffairId.length)
	 	{
	 		$('tdHasMergeFile').innerHTML = "是";
	 	}
	 	else
	 	{
	 		$('tdHasMergeFile').innerHTML = "否";
	 	}
	}
};

AffairDetail.prototype.getFeedBackInfo = function()
{
	var params = "alarmId=" + this.alarmId;
	params += "&ALARMTYPE=" + this.alarmType;
	var url = "dispatch.feedBack.getEventListById.d";	
	url = encodeURI(url);
	params = encodeURI(params);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var res=xmlDoc.getElementsByTagName("feedback");
		
		affairDetail.createFeedBackList(res);
	}	
	});
}
AffairDetail.prototype.createFeedBackList = function(res){

	var table=document.createElement("table");
//	table.style="border-left: 1 #caa solid; border-top: 1 #caa solid;"
//	table.style.borderLeft="1 #caa solid";
//	table.style.borderTop="1 #caa solid";
	table.setAttribute("className","tableList");
	table.style.width="100%";
	var tbody=document.createElement("tbody");	
	var title=["记录","出警单位","出警人","出警时间","到警时间","出警车辆","状态","状态编码","出动人数"];
	var widths=["3%","14%","25%","14%","14%","14%","14%","0","0","15"];
	var trHead=document.createElement("tr");
	trHead.setAttribute("id","trHead");
//	trHead.style.backgroundColor="#9abcff";
	for(var i=0;i<title.length;i++){
		var tdTitle=document.createElement("td");
		tdTitle.innerText=title[i];
		tdTitle.setAttribute("className","tdList");
		tdTitle.style.width=widths[i];
		if(i==6||i==7){
			tdTitle.style.display="none";
		}
		trHead.appendChild(tdTitle);		
		
	}
	tbody.appendChild(trHead);
	
	
	for(var i=0;i<res.length;i++){
		var tr=document.createElement("tr");		
		tr.setAttribute("id","tr_"+i);
		var objs=res[i].childNodes;
				
		var td0=document.createElement("td");
//		td0.setAttribute("id",i+"_td_"+j);
		td0.innerText=i+1;
		td0.setAttribute("className","tdList");
		tr.appendChild(td0);
		for(var j=2;j<objs.length;j++){
			var td=document.createElement("td");
			td.setAttribute("id",i+"_td_"+j);
			
			if(objs[j].text==""){
				td.innerHTML="&nbsp;"
			}else{
				td.innerText=objs[j].text;
			}	
			td.setAttribute("className","tdList");
			if(j==6||j==7){
				td.style.display="none";
			}
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	
	table.appendChild(tbody);
	$("feedbackTableDiv").appendChild(table);
}


AffairDetail.prototype.getBooleanDesc = function( booleanValue )
{
	if( booleanValue &&  "true" == booleanValue.toLowerCase() )
	{
		return "是";
	}
	return "否";
};


AffairDetail.prototype.createSubAffairsHref = function(subAffairs)
{
	if(0<subAffairs.length)
	{
		var subArray = subAffairs.split(",");
		var htmlBuffer = "";
		for(var i=0; i<subArray.length;i+=1)
		{	
			var alarmId = subArray[i].split(";")[0];
			var alarmType = subArray[i].split(";")[1];
			
			if("" == htmlBuffer)
			{
				htmlBuffer += "<a href=# onclick=\"javascript:showAlarmDetail('" + alarmId + "','" + alarmType + "')\">" + alarmId + "</a> "
			}
			else
			{
				htmlBuffer += "&nbsp;,&nbsp;<a href=# onclick=\"javascript:showAlarmDetail('" + alarmId + "','" + alarmType + "')\">" + alarmId + "</a> "
			}
		}
		return htmlBuffer;
	}
};


function showAlarmDetail(alarmId,alarmType)
{ 
		window.open("affairDetail.jsp?ALARMID=" + alarmId  + "&ALARMTYPE=" + alarmType, '','width:800;height:600');
	 
}

