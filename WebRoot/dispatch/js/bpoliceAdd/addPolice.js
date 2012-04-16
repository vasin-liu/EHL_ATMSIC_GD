//得到新增事件id和时间
function addNewInfo(){
	var url = "dispatch.policeEdit.getNewInfo.d";	
	url = encodeURI(url);
	var params="";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:addNewInfoRes});
}


function addNewInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	$("newalarmId").value=res[0].text;
	$("newalarmTime").value= res[1].text;
	$("newalarmDept").value= res[2].text;
	$("newalarmDept").deptid= res[3].text;
	$("newRegion").deptid= res[3].text;
	$("newRegion").value= res[2].text;
	$("newreportUnit").deptid= res[3].text;
	$("newreportUnit").value= res[2].text;
	$("newreportTime").value= res[1].text;
	$("newreportPerson").value= res[4].text;	
	
	$("newalarmState").value='004012';
	$("newalarmState").disabled=true;
}

function saveNewPolice(){
	var url = "dispatch.policeEdit.SavePoliceInfo.d";	
	url = encodeURI(url);
	if($("newalarmId").value==""){
		alert("报警单号不能为空");
		return;
	}
	if($("newalarmType").value==""){
		alert("请选择报警类型");
		return;
	}
	var params=saveNewParams();
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:saveNewPoliceRes});
}
function saveNewPoliceRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	
	if(xmlDoc=="true"){
		alert("新增警情成功！");
		watching.readNoticedAffair();
		cleanAddInfo();
	}else{
		alert("新增警情失败！")
	}
}

function cleanAddInfo(){
	var inputs=document.getElementsByName("editinput");
	for(var i=0;i<inputs.length;i++){
		inputs[i].value="";
	}
	$("newsubEventSource").value="";
	$("newalarmType").value="";
	$("newalarmThinType").value="";
	$("newalarmDesc").value="";
}

function saveNewParams(){		
	var alarmId=$("newalarmId").value;
	var	alarmTime=$("newalarmTime").value;
	var	alarmSite=$("newalarmSite").value;
	var	alarmDept=$("newalarmDept").value;
	var	alarmDeptId=$("newalarmDept").deptid;
	var	alarmType=$("newalarmType").value;
	var	subEventSource=$("newsubEventSource").value;
	var	region=$("newRegion").value;
	var	regionId=$("newRegion").deptid;
	var	alarmState=$("newalarmState").value;
	var	alarmThinType=$("newalarmThinType").value;
	var	alarmperson=$("newalarmperson").value;
	var	alarmphone=$("newalarmphone").value;
	var	alarmaddres=$("newalarmaddres").value;
	var	reportTime=$("newreportTime").value;
	var	reportPerson=$("newreportPerson").value;
	var	reportUnit=$("newreportUnit").value;
	var	reportUnitId=$("newreportUnit").deptid;
	var	alarmDesc=$("newalarmDesc").value;	
	
	var params={};
	params["alarmId"]=alarmId ;
	params["alarmTime"]=alarmTime ;
	params["alarmSite"]=alarmSite ;
	params["alarmDept"]=alarmDept ;
	params["alarmDeptId"]=alarmDeptId ;
	params["alarmType"]=alarmType ;
	params["subEventSource"]=subEventSource ;
	params["region"]=region ;
	params["regionId"]=regionId ;
	params["alarmState"]=alarmState ;
	params["alarmThinType"]=alarmThinType ;
	params["alarmperson"]=alarmperson ;
	params["alarmphone"]=alarmphone ;
	params["alarmaddres"]=alarmaddres ;
	params["reportTime"]=reportTime ;
	params["reportPerson"]=reportPerson ;
	params["reportUnit"]=reportUnit ;
	params["reportUnitId"]=reportUnitId ;
	params["alarmDesc"]=alarmDesc ;	
	params["alarmSouce"]="002007" ;	//事件来源	
	return params;
}

	