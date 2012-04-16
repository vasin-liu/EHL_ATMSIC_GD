function getAlarmInfo(alarmId,alarmType){
	var url = "dispatch.alarmQuery.queryAlarmInfo.d";	
	var params = "alarmId=" + alarmId;
	params += "&&alarmType=" + alarmType;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getAlarmInfoRes});
}
function getAlarmInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");	
	document.getElementById("alarmid").innerHTML=("" ==  res[0].text?"&nbsp;": res[0].text);//alarmid
	document.getElementById("title").innerHTML=("" ==  res[1].text?"&nbsp;": res[1].text);//title
	document.getElementById("alarmregion").innerHTML=("" ==  res[2].text?"&nbsp;": res[2].text);//alarmregion
	document.getElementById("alarmtime").innerHTML=("" ==  res[3].text?"&nbsp;": res[3].text);//alarmtime
	document.getElementById("alarmaddress").innerHTML=("" ==  res[4].text?"&nbsp;": res[4].text);//alarmaddress
	document.getElementById("eventsource").innerHTML=("" ==  res[5].text?"&nbsp;": res[5].text);//eventsource
	document.getElementById("eventtype").innerHTML=("" ==  res[6].text?"&nbsp;": res[6].text);//eventtype
	document.getElementById("eventthintype").innerHTML=("" ==  res[7].text?"&nbsp;": res[7].text);//eventthintype
	document.getElementById("eventlevel").innerHTML=("" ==  res[8].text?"&nbsp;": res[8].text);//eventlevel
	document.getElementById("roadtype").innerHTML=("" ==  res[9].text?"&nbsp;": res[9].text);//roadtype
	document.getElementById("weather").innerHTML=("" ==  res[10].text?"&nbsp;": res[10].text);//weather
	document.getElementById("FLESHWOUNDPERSONCOUNT").innerHTML=("" ==  res[11].text?"&nbsp;": res[11].text);//FLESHWOUNDPERSONCOUNT
	document.getElementById("DEATHPERSONCOUNT").innerHTML=("" ==  res[12].text?"&nbsp;": res[12].text);//DEATHPERSONCOUNT
	document.getElementById("disposeresult").innerHTML=("" ==  res[13].text?"&nbsp;": res[13].text);//disposeresult
	document.getElementById("alarmdesc").innerHTML=("" ==  res[14].text?"&nbsp;": res[14].text);//alarmdesc
	document.getElementById("attemper").innerHTML=("" ==  res[15].text?"&nbsp;": res[15].text);//attemper
	document.getElementById("comeoutperson").innerHTML=("" ==  res[16].text?"&nbsp;": res[16].text);//comeoutperson
	document.getElementById("comeoutcar").innerHTML=("" ==  res[17].text?"&nbsp;": res[17].text);//comeoutcar
	document.getElementById("assigntime").innerHTML=("" ==  res[18].text?"&nbsp;": res[18].text);//assigntime
	document.getElementById("comeouttime").innerHTML=("" ==  res[19].text?"&nbsp;": res[19].text);//comeouttime
	document.getElementById("comeoutarrivetime").innerHTML=("" ==  res[20].text?"&nbsp;": res[20].text);//comeoutarrivetime
	document.getElementById("feedbacktime").innerHTML=("" ==  res[21].text?"&nbsp;": res[21].text);//feedbacktime
	document.getElementById("finishtime").innerHTML=("" ==  res[22].text?"&nbsp;": res[22].text);//finishtime
	document.getElementById("trafficreverttime").innerHTML=("" ==  res[23].text?"&nbsp;": res[23].text);//trafficreverttime
	document.getElementById("disposeperson").innerHTML=("" ==  res[24].text?"&nbsp;": res[24].text);//disposeperson
	document.getElementById("feedbackdesc").innerHTML=getFeedbackDesc(res[25].text);//feedbackdesc
	document.getElementById("alarmperson").innerHTML=("" ==  res[26].text?"&nbsp;": res[26].text);//alarmperson
	document.getElementById("alarmphone").innerHTML=("" ==  res[27].text?"&nbsp;": res[27].text);//alarmphone
}	

function getFeedbackDesc(infoStr){
	var htmlStr="";
	var feedbacks=infoStr.split("*NextRow*");
	if(feedbacks!=null){
		for(var i=0;i<feedbacks.length;i++){
			htmlStr+=feedbacks[i]+"<br>"	;
		}	
	}else{
		htmlStr="&nbsp;";
	}
	return htmlStr;
}