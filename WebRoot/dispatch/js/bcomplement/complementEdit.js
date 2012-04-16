/**
 * 补录警情信息
 */
 
function openComple(){
	getCompleNewInfo();
}	

function getCompleNewInfo(){
	markTool();
	$("complesaveData").disabled=false;
	removeAllPoint();
	var url = "dispatch.policeEdit.getNewInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getCompleNewInfoRes});
}
function getCompleNewInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	$("complealarmId").value=res[0].text;
	$("complealarmTime").value= res[1].text;
	$("complealarmDept").value= res[2].text;
	$("complealarmDept").deptid= res[3].text;
	$("compledisposeUnit").value= res[2].text;
	$("compledisposeUnit").deptid= res[3].text;
	$("compledisposeTime").value= res[1].text;
	
	$("complereportUnit").deptid= res[3].text;
	$("complereportUnit").value= res[2].text;
	$("complereportTime").value= res[1].text;
	$("complereportPerson").value= res[4].text;
		
	$("complefkdw").deptid= res[3].text;
	$("complefkdw").value= res[2].text;
	$("complecjdw").deptid= res[3].text;
	$("complecjdw").value= res[2].text;
	$("complefksj").value= res[1].text;
	$("complefkr").value= res[4].text;
	
	$("complesignSite").value="定位案发地点";
	$("complesaveData").style.display="";
	$("complesignSite").style.display="";
	$("complealarmSouce").value='002007';
	$("complealarmState").value='004013';
	$("complealarmState").disabled=true;
	
	
}

//事件类型变化
function typeChage(){
	var eventType=$("complealarmType").value;
	if(isAccident(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '006%'","请选择");
		fillListBox("complealarmThinTypeTd","complealarmThinType","111","select id,name from t_attemper_code where id like '007%'","请选择");
		
		$("compleaccidentTbody").style.display="";
		$("complebadWeatherTbody").style.display="none";
		$("complepoliceEventTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
		$("completownPlanTbody").style.display="none"
		$("complefireAndBlastTbody").style.display="none";
//		$("complecongestionTbody").style.display="none";
	}
	else if(isCongestion(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '005%'","请选择");
		$("complealarmThinTypeTd").innerHTML='<input type="text" id="complealarmThinType" name="compleeditinput" class="textwidth" >';

		$("compleaccidentTbody").style.display="";
		$("complebadWeatherTbody").style.display="none";
		$("complepoliceEventTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("completownPlanTbody").style.display="none"
		$("complegeoLogicDisasterTbody").style.display="none";
		$("complefireAndBlastTbody").style.display="none";
	}
	
	else if(isWeather(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
		$("complealarmThinTypeTd").innerHTML='<input type="text" id="complealarmThinType"  class="textwidth" readonly>';
				
		$("complebadWeatherTbody").style.display="";
		$("complepoliceEventTbody").style.display="none";
		$("compleaccidentTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
		$("completownPlanTbody").style.display="none";
		$("complefireAndBlastTbody").style.display="none";
	}	
	else if(isPoliceEvent(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
		$("complealarmThinTypeTd").innerHTML='<input type="text" id="complealarmThinType"  class="textwidth" readonly>';
		
		$("complepoliceEventTbody").style.display="";
		$("complebadWeatherTbody").style.display="none";		
		$("compleaccidentTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
		$("completownPlanTbody").style.display="none"
		$("complefireAndBlastTbody").style.display="none";
	}			
	else if(isExceptionCar(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
		$("complealarmThinTypeTd").innerHTML='<input type="text" id="complealarmThinType"  class="textwidth" readonly>';
		
		$("compleexceptionCarTbody").style.display="";
		$("complepoliceEventTbody").style.display="none";
		$("complebadWeatherTbody").style.display="none";		
		$("compleaccidentTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
		$("completownPlanTbody").style.display="none"
		$("complefireAndBlastTbody").style.display="none";
	}			
	else if(isGeoLogicDisaster(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
		fillListBox("complealarmThinTypeTd","complealarmThinType","111","select id,name from t_attemper_code where id like '016%'","请选择");
				
		$("complegeoLogicDisasterTbody").style.display="";
		$("compleexceptionCarTbody").style.display="none";
		$("complepoliceEventTbody").style.display="none";
		$("complebadWeatherTbody").style.display="none";		
		$("compleaccidentTbody").style.display="none";
		$("completownPlanTbody").style.display="none"
		$("complefireAndBlastTbody").style.display="none";
	}			
	else if(isTownPlan(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
		fillListBox("complealarmThinTypeTd","complealarmThinType","111","select id,name from t_attemper_code where id like '017%'","请选择");
		$("completownPlanTbody").style.display=""
		$("compleaccidentTbody").style.display="none";
		$("complebadWeatherTbody").style.display="none";
		$("complepoliceEventTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
		$("complefireAndBlastTbody").style.display="none";
		
	}
	else if(isFireAndBlast(eventType)){
		fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
		fillListBox("complealarmThinTypeTd","complealarmThinType","111","select id,name from t_attemper_code where id like '019%'","请选择");
		$("complefireAndBlastTbody").style.display="";
		$("completownPlanTbody").style.display="none"
		$("compleaccidentTbody").style.display="none";
		$("complebadWeatherTbody").style.display="none";
		$("complepoliceEventTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
	}	
	else{
		$("compleaccidentTbody").style.display="none";
		$("complebadWeatherTbody").style.display="none";
		$("complepoliceEventTbody").style.display="none";
		$("compleexceptionCarTbody").style.display="none";
		$("complegeoLogicDisasterTbody").style.display="none";
		$("completownPlanTbody").style.display="none"
		$("complefireAndBlastTbody").style.display="none";
	}
}
//编辑一条报警基本信息

function editCompleAlarmInfo(){
	var complealarmTime=$("complealarmTime").value;
	var complealarmSite=$("complealarmSite").value;
	var complealarmDeptName=$("complealarmDept").value;
	var complealarmDept=$("complealarmDept").deptid;
	
	var complesubEventSource =$("complesubEventSource").value;
	var complealarmSouce=$("complealarmSouce").value;
	var complealarmType=$("complealarmType").value;
	var complealarmLevel=$("complealarmLevel").value;
	var complealarmThinType=$("complealarmThinType").value;
	var complealarmState=$("complealarmState").value;
	var complealarmDesc=$("complealarmDesc").value;
	var complealarmId=$("complealarmId").value;	
	var compleroadId=$("compleroadId").value;	
	var compleroadName=$("compleroadName").value;	
	var compleX=$("compleX").value;
	var compleY=$("compleY").value;
		
	var compledisposeTime=$("compledisposeTime").value;	
	var compledisposePerson=$("compledisposePerson").value;	
	var compledisposeUnitName=$("compledisposeUnit").value;	
	var compledisposeUnit=$("compledisposeUnit").deptid;	
	var compledisposeIdea=$("compledisposeIdea").value;		
	
	var complereportUnit=$("complereportUnit").deptid;
	var complereportUnitName=$("complereportUnit").value;
	var complereportTime=$("complereportTime").value;
	var complereportPerson=$("complereportPerson").value;
	
	var compleLevel=$("complealarmLevel");
	var compleThinType=$("complealarmThinType");
	var complealarmLevelValue=compleLevel[compleLevel.selectedIndex].text;
	var complealarmThinTypeValue="";
	if(compleThinType.tagName=="SELECT"){
		complealarmThinTypeValue=compleThinType[compleThinType.selectedIndex].text;
	}	
	
		
	var complefksj=$("complefksj").value; 
	var complefkdwName= $("complefkdw").value;
	var complefkdw= $("complefkdw").deptid;;
	var complefkr= $("complefkr").value;
	var complecjdwName= $("complecjdw").value;
	var complecjdw= $("complecjdw").deptid;;
	var complecjsj= $("complecjsj").value;
	var compledjsj= $("compledjsj").value;
	var complecjr= $("complecjr").value;
	var compleajfssj= $("compleajfssj").value; //事件发生时间
	var compleajjssj= $("compleajjssj").value;
	var complecjrs= $("complecjrs").value;
	var complecdcl= $("complecdcl").value;
	var complecljg= $("complecljg").value;
	
		
	if(complealarmTime==""){
		alert("请输入报警时间");
		return;
	}
	if(complealarmDept==""){
		alert("请输入报警机构");
		return;
	}
	if(complealarmSouce==""){
		alert("请输入事件来源");
		return;
	}
	if(complealarmType==""){
		alert("请输入事件类型");
		return;
	}
	if(complealarmLevel==""){
		alert("请输入事件程度");
		return;
	}
	if(complealarmState==""){
		alert("请输入事件状态");
		return;
	}
	
	if(complecjsj==""){
		alert("请输入出警时间");
		return;
	}
	
	if(compleajjssj==""){
		alert("请输入结束时间");
		return;
	}
	
	
	var params={};
	params["complealarmTime"]=complealarmTime;
	params["complealarmSite"]=complealarmSite;
	params["complealarmDept"]=complealarmDept;
	params["complesubEventSource"]=complesubEventSource;
	params["complealarmSouce"]=complealarmSouce ;
	params["complealarmType"]=complealarmType ;
	params["complealarmLevel"]=complealarmLevel ;
	params["complealarmThinType"]=complealarmThinType ;
	params["complealarmState"]=complealarmState ;
	params["complealarmDesc"]=complealarmDesc ;
	params["complealarmId"]=complealarmId ;
	params["compleroadId"]=compleroadId ;
	params["compleroadName"]=compleroadName ;
	params["compleX"]=compleX ;
	params["compleY"]=compleY ;
	
	params["complealarmLevelValue"]=complealarmLevelValue;
	params["complealarmThinTypeValue"]=complealarmThinTypeValue;
	
	params["compledisposeTime"]=compledisposeTime ;
	params["compledisposePerson"]=compledisposePerson ;
	params["compledisposeUnit"]=compledisposeUnit ;
	params["compledisposeIdea"]=compledisposeIdea ;
	
	params["complereportUnit"]=complereportUnit ;
	params["complereportUnitName"]=complereportUnitName ;
	params["complereportTime"]=complereportTime ;
	params["complereportPerson"]=complereportPerson ;
	
	params["complefksj"]=complefksj ;	
	params["complefkdw"]=complefkdw ;
	params["complefkr"]=complefkr ;
	params["complecjdw"]=complecjdw ;
	params["complecjsj"]=complecjsj ;
	params["compledjsj"]=compledjsj ;
	params["complecjr"]=complecjr ;
	params["compleajfssj"]=compleajfssj ; ////事件发生时间
	params["compleajjssj"]=compleajjssj ;
	params["complecjrs"]=complecjrs ;
	params["complecdcl"]=complecdcl ;
	params["complecljg"]=complecljg ;	
	params["complefkdwName"]=complefkdwName ;
	params["complecjdwName"]=complecjdwName ;
	
	//事故
	if(isAccident(complealarmType)){
		editCompleAccidentInfo(params);
	}
	//拥堵
	if(isCongestion(complealarmType)){
		editCompleCongestionInfo(params);
	}
	//故障车	
	if(isExceptionCar(complealarmType)){
		editCompleExceptionCarInfo(params);
	}
	//治安
	if(isPoliceEvent(complealarmType)){
		editComplePoliceEventInfo(params);
	}
	//恶劣天气
	if(isWeather(complealarmType)){
		editCompleBadWeatherInfo(params);
	}
	//市政
	if(isTownPlan(complealarmType)){
		editCompleTownplanInfo(params);
	}
	
	//火灾
	if(isFireAndBlast(complealarmType)){
		editCompleFireAndBlastInfo(params);
	}
	//地质灾害
	if(isGeoLogicDisaster(complealarmType)){
		editCompleGeoLogicDisasterInfo(params);
	}

}	


function reportSucess(){
	clearInfo();
	watching.readNoticedAffair();
}
//编辑一条事故信息

function editCompleAccidentInfo(params){	
	params["compleaccidentAlarmCarType"]=$("compleaccidentAlarmCarType").value;
	params["compleaccidentAlarmCarCodeDept"]=$("compleaccidentAlarmCarCodeDept").value;
	params["compleaccidentAlarmCarCode"]=$("compleaccidentAlarmCarCode").value;
	params["compleaccidentAlarmCarGenre"]=$("compleaccidentAlarmCarGenre").value;
	params["compleaccidentremark"]=$("compleaccidentremark").value;
	params["compleaccidentAffectLevel"]=$("compleaccidentAffectLevel").value;
	
	params["complejjss"]=$("complejjss").value;
	params["compleqsrs"]=$("compleqsrs").value;
	params["complezsrs"]=$("complezsrs").value;
	params["compleswrs"]=$("compleswrs").value;
	params["complesars"]=$("complesars").value;
	params["complezhrs"]=$("complezhrs").value;
	params["complejzrs"]=$("complejzrs").value;
	if($("complejjss").checked == true){
		params["complephxsaj"]="true";
	}else{
		params["complephxsaj"]="false";
	}
	if($("complecczaaj").checked == true){		
		params["complecczaaj"]="true";
	}else{
		params["complecczaaj"]="false";
	}
	if($("complejjjf").checked == true){
		params["complejjjf"]="true";
	}else{
		params["complejjjf"]="false";
	}
	
	var url = "dispatch.compleEdit.editCompleAccidentInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCompleAccidentInfoRes});
}
function editCompleAccidentInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}



//编辑一条拥堵信息

function editCompleCongestionInfo(params){	
	params["compleaccidentAlarmCarType"]=$("compleaccidentAlarmCarType").value;
	params["compleaccidentAlarmCarCodeDept"]=$("compleaccidentAlarmCarCodeDept").value;
	params["compleaccidentAlarmCarCode"]=$("compleaccidentAlarmCarCode").value;
	params["compleaccidentAlarmCarGenre"]=$("compleaccidentAlarmCarGenre").value;
	params["compleaccidentremark"]=$("compleaccidentremark").value;
	params["compleaccidentAffectLevel"]=$("compleaccidentAffectLevel").value;
	
	params["complejjss"]=$("complejjss").value;
	params["compleqsrs"]=$("compleqsrs").value;
	params["complezsrs"]=$("complezsrs").value;
	params["compleswrs"]=$("compleswrs").value;
	params["complesars"]=$("complesars").value;
	params["complezhrs"]=$("complezhrs").value;
	params["complejzrs"]=$("complejzrs").value;
	if($("complejjss").checked == true){
		params["complephxsaj"]="true";
	}else{
		params["complephxsaj"]="false";
	}
	if($("complecczaaj").checked == true){		
		params["complecczaaj"]="true";
	}else{
		params["complecczaaj"]="false";
	}
	if($("complejjjf").checked == true){
		params["complejjjf"]="true";
	}else{
		params["complejjjf"]="false";
	}
	
	var url = "dispatch.compleEdit.editCompleCongestionInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCompleCongestionInfoRes});
}
function editCompleCongestionInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}

//编辑一条恶劣天气信息

function editCompleBadWeatherInfo(params){
	params["compleweathersituation"]=$("compleweathersituation").value;
	params["compleweatherRoadStatus"]=$("compleweatherRoadStatus").value;
	params["compleweatherAffectRoad"]=$("compleweatherAffectRoad").value;
	params["compleweatheAffectDept"]=$("compleweatheAffectDept").value;	
	params["compleweatherAlarmPhone"]=$("compleweatherAlarmPhone").value;	
	params["compleweatherAlarmPerson"]=$("compleweatherAlarmPerson").value;	
	params["compleweatherAffectextent"]=$("compleweatherAffectextent").value;	
	params["compleweatherRemark"]=$("compleweatherRemark").value;	
	
	var url = "dispatch.compleEdit.editCompleBadWeatherInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCompleBadWeatherInfoRes});
}
function editCompleBadWeatherInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}

//编辑一条治安信息

function editComplePoliceEventInfo(params){
	params["complePoliceEventAffectTrafficLevel"]=$("complePoliceEventAffectTrafficLevel").value;
	params["complePoliceEventAffectRoad"]=$("complePoliceEventAffectRoad").value;
	params["complePoliceEventRemark"]=$("complePoliceEventRemark").value;
	params["complePoliceEventTime"]=$("complePoliceEventTime").value;	
	params["complePoliceEventAffectextent"]=$("complePoliceEventAffectextent").value;	
	
	var url = "dispatch.compleEdit.editComplePoliceEventInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editComplePoliceEventInfoRes});
}
function editComplePoliceEventInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}

//编辑一条故障车信息
function editCompleExceptionCarInfo(params){
	params["compleexceptionCarAddress"]=$("compleexceptionCarAddress").value;
	params["compleexceptionCause"]=$("compleexceptionCause").value;
	params["compleexceptioCarShape"]=$("compleexceptioCarShape").value;
	params["compleexceptioAffectLevel"]=$("compleexceptioAffectLevel").value;
	params["compleexceptionRemark"]=$("compleexceptionRemark").value;
	params["compleexceptionDriver"]=$("compleexceptionDriver").value;
	params["compleexceptionCarDept"]=$("compleexceptionCarDept").value;
	
	var url = "dispatch.compleEdit.editCompleExceptionCarInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCompleExceptionCarInfoRes});
}
function editCompleExceptionCarInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}


//编辑地质灾害
function editCompleGeoLogicDisasterInfo(params){
	params["compleGeoLogicDisasterAffectRoad"]=$("compleGeoLogicDisasterAffectRoad").value;
	params["compleGeoLogicDisasterLevel"]=$("compleGeoLogicDisasterLevel").value;
	params["compleGeoLogicDisasterAffectExent"]=$("compleGeoLogicDisasterAffectExent").value;
	params["compleGeoLogicDisasterAlarmPerson"]=$("compleGeoLogicDisasterAlarmPerson").value;	
	params["compleGeoLogicDisasterRemark"]=$("compleGeoLogicDisasterRemark").value;			
	var url = "dispatch.compleEdit.editCompleGeoLogicDisasterInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCompleGeoLogicDisasterInfoRes});
}
function editCompleGeoLogicDisasterInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}

//编辑一条市政信息

function editCompleTownplanInfo(params){
	params["completownPlanPerson"]=$("completownPlanPerson").value;
	params["completownPlanAffectRoad"]=$("completownPlanAffectRoad").value;
	params["completownPlanAffectExent"]=$("completownPlanAffectExent").value;	
	params["completownPlanLevel"]=$("completownPlanLevel").value;	
	params["completownPlanRemark"]=$("completownPlanRemark").value;	
	
	var url = "dispatch.compleEdit.editCompleTownplanInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCompleTownplanInfoRes});
}
function editCompleTownplanInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();		
	}else{
		alert("上报失败!");
	}
}
//编辑火灾信息
function editCompleFireAndBlastInfo(params){
	
	params["complefireAffectLevel"]=$("complefireAffectLevel").value;
	params["complefireHaveCasualty"]=$("complefireHaveCasualty").value;
	params["complefireAlarmPerson"]=$("complefireAlarmPerson").value;	
	params["complefireRemark"]=$("complefireRemark").value;			
	
	params["compleaffectRoad"]=$("compleaffectRoad").value;			
	params["complecompleFLESHWOUNDPERSONCOUNT"]=$("complecompleFLESHWOUNDPERSONCOUNT").value;			
	params["compleGBHPERSONCOUNT"]=$("compleGBHPERSONCOUNT").value;	
	params["compleDEATHPERSONCOUNT"]=$("compleDEATHPERSONCOUNT").value;	
	params["compleeconomyloss"]=$("compleeconomyloss").value;			
	
	var url = "dispatch.compleEdit.editCompleFireAndBlastInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editFireAndBlastInfoRes});
}
function editFireAndBlastInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("上报成功!");
		reportSucess();
	}else{
		alert("上报失败!");
	}
}