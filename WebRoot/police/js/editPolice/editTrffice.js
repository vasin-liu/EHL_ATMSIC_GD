/**
 *author:wangxt
 *desc:编辑施工占道信息
 *date:2010-1-11
*/
function editTrfficeRoadBuildInfo() {
    var params={};
    params["alarmId"] = $("alarmId_RoadBuild").value;
	params["alarmTime_RoadBuild"] = $('alarmTime_RoadBuild').value ;
	params["reportPerson_RoadBuild"]  = $('reportPerson_RoadBuild').value ;
	params["alarmDept_RoadBuild"]  = $('alarmDept_RoadBuild').value ;
	params["fzr_RoadBuild"]  = $('fzr_RoadBuild').value ;
	params["phone_RoadBuild"] = $('phone_RoadBuild').value;
	params["alarmRoad_RoadBuild_td"] = $('alarmRoad_RoadBuild').value; 
    params["alarmFiled_RoadBuild"] = $('alarmFiled_RoadBuild').value;
    params["rotype_RoadBuild"] = $('rotype_RoadBuild').value;
    params["permittype_RoadBuild"] = $('permittype_RoadBuild').value;
    params["starttime_RoadBuild"] = $('starttime_RoadBuild').value;
    params["endtime_RoadBuild"] = $('endtime_RoadBuild').value;
    params["alarmState"] = $('alarmState_RoadBuild').value;
    //params["alarmDept"]=$("alarmDept").deptid;
    params["alarmDesc_RoadBuild"] = $('alarmDesc_RoadBuild').value;
    var url = "dispatch.trafficEdit.editTrafficRoadInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editTrfficeRoadBuildInfoRes});
}

function editTrfficeRoadBuildInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("上报成功!");
		clearInfo();
		$("saveData").disabled=true;
		watching.readNoticedAffair();
	}else{
		alert("上报失败!");
	}
}

function editTrfficeContrl() {
    var params={};
    params["alarmId_TrafficRestrain"] = $("alarmId_TrafficRestrain").value;
	params["alarmTime_TrafficRestrain"] = $('alarmTime_TrafficRestrain').value ;
	params["alarmDept_TrafficRestrain"]  = $('alarmDept_TrafficRestrain').value ;
	params["reportUnit_TrafficRestrain"]  = $('reportUnit_TrafficRestrain').value ;
	params["reportDept_TrafficRestrain"]  = $('reportDept_TrafficRestrain').value ;
	params["telpone_TrafficRestrain"] = $('telpone_TrafficRestrain').value;
	params["reson_TrafficRestrain"] = $('reson_TrafficRestrain').value; 
    params["accsection_TrafficRestrain"] = $('accsection_TrafficRestrain').value;
    params["mvalue_TrafficRestrain"] = $('mvalue_TrafficRestrain').value;
    params["address_TrafficRestrain"] = $('address_TrafficRestrain').value;
    params["direction_TrafficRestrain"] = $('direction_TrafficRestrain').value;
    params["type_TrafficRestrain"] = $('type_TrafficRestrain').value;
    params["starttime_TrafficRestrain"] = $('starttime_TrafficRestrain').value;
    params["endtime_TrafficRestrain"]=$("endtime_TrafficRestrain").value;
    params["desc_TrafficRestrain"] = $('desc_TrafficRestrain').value;
    var url = "dispatch.trafficEdit.editTrafficControlInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editTrfficeContrlRes});
}

function editTrfficeContrlRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("上报成功!");
		clearInfo();
		$("saveData").disabled=true;
		watching.readNoticedAffair();
	}else{
		alert("上报失败!");
	}
}

/**
    根据警情编号，事件类型查询详细信息
*/
function getTrafficInfo(alarmid) {
   clearInfo();
	//removeAllPoint();
	if(alarmid!=""){	
		var url = "dispatch.trafficEdit.getTrafficControlInfo.d";	
		var params="alarmid="+alarmid+"";
		url = encodeURI(url);		
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:getTrafficInfoRes});
	}
}
/**
   
*/
function getTrafficInfoRes(xmlHttp) {
    var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("col");
    $("alarmId_TrafficRestrain").value = res[0].text;
	$('alarmTime_TrafficRestrain').value = res[1].text;
	
	$('alarmDept_TrafficRestrain').value =res[2].text ;
	$('reportPerson_TrafficRestrain').value = res[3].text ;
	$('reportDept_TrafficRestrain').value = res[4].text ;
	$('telpone_TrafficRestrain').value = res[5].text;
	$('reson_TrafficRestrain').value = res[6].text; 
    $('accsection_TrafficRestrain').value = res[7].text;
    $('mvalue_TrafficRestrain').value = res[8].text;
    $('address_TrafficRestrain').value = res[9].text;
    $('direction_TrafficRestrain').value = res[10].text;
    $('type_TrafficRestrain').value = res[11].text;
    $('starttime_TrafficRestrain').value = res[12].text;
    $("endtime_TrafficRestrain").value = res[13].text;
    $('desc_TrafficRestrain').value = res[14].text;
   
}


function getTrafficRoadInfo(alarmid) {
     clearInfo();
	//removeAllPoint();
	if(alarmId!=""){	
		var url = "dispatch.trafficEdit.getTrafficRoadInfo.d";	
		var params="alarmid="+alarmid+"";
		url = encodeURI(url);		
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:getTrafficRoadInfoRes});
	}
}

function getTrafficRoadInfoRes(xmlHttp) {
    var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("col");
    $("alarmId_RoadBuild").value =res[0].text ;
	$('alarmTime_RoadBuild').value=res[1].text ;
	$('reportPerson_RoadBuild').value=res[2].text ;
    $('alarmDept_RoadBuild').value=res[3].text ;
    $('fzr_RoadBuild').value=res[4].text ;
	$('phone_RoadBuild').value = res[5].text;
	$('alarmRoad_RoadBuild').value = res[6].text; 
    $('alarmFiled_RoadBuild').value = res[7].text;
    $('rotype_RoadBuild').value = res[8].text;
    $('permittype_RoadBuild').value = res[9].text;
    $('starttime_RoadBuild').value =res[10].text;
    $('endtime_RoadBuild').value = res[11].text;
    //$('alarmState_RoadBuild').value = res[12].text;
    //params["alarmDept"]=$("alarmDept").deptid;
    $('alarmDesc_RoadBuild').value = res[12].text;
    
}

function doDeleteRoad(alarmid) {                                
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "dispatch.trafficEdit.delTrafficRoadInfo.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "RZBH=" + bh;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
	}
}
function showResponseDelete(xmlHttp) {
	var text = xmlHttp.responseText;
	if(text=='success') {
	   alert("删除成功!");
	}else {
	   alert("删除失败!");
	}
	//doOnLoad();
}












