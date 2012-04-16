var policetype = "";
var roadalarmid = "";
//初始化页面,点击左侧列表内警情出发事件 function initPages(){
	watching.setRowClickHandler(getAlarmInfo);
}

//打开编辑页面
function openAddPolice(type){
	clearInfo();
	
	var ctrlObj = $("ctrlList");
	var ctrlList = ctrlObj.getElementsByTagName("input");
	
	for(var i=0; i<ctrlList.length; i++) {
		var div = "div" + type;
		var btn = "btn" + type;
		if(ctrlList[i].id == btn){
			$(div).style.display="";
		}else{
			$(div).style.display="none";
		}
	}
	if(type=='RoadBuild') {
	  getTrafficNewInfo(type);
	  return;
	}
	if(type=='TrafficRestrain') {
	  getTrafficContrlInfo(type);
	  return;
	}
	getNewInfo(type);
}
function getTrafficContrlInfo(type) {
    policetype = type;
	//markTool();
	//$("saveData").disabled=false;
	
	//removeAllPoint();
	var params = "";
	var url = "dispatch.policeEdit.getNewInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getTrafficContrlInfoRes});
}

function getTrafficContrlInfoRes(xmlHttp) {
    var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	$("alarmId"+"_"+policetype).value=res[0].text;
	
	$("alarmTime"+"_"+policetype).value= res[1].text;
	$("reportPerson"+"_"+policetype).value= res[4].text;
	$("alarmDept"+"_"+policetype).value= res[2].text;
	$("alarmState"+"_"+policetype).value='004001';
}
//得到新增事件id和时间function getNewInfo(type){
	policetype = type;
	//markTool();
	//$("saveData").disabled=false;
	var params = "";
	//removeAllPoint();
	var url = "dispatch.caccident.getNewInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getNewInfoRes});
}
//根据后台得到的数据，初始化页面数据项
function getNewInfoRes(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	$("insertOrUpdate"+"_"+policetype).value = "0";
	$("alarmState"+"_"+policetype).value = "004001";//已上报
	$("alarmId"+"_"+policetype).value=res[0].text;
	
	$("alarmTime"+"_"+policetype).value= res[1].text;
	$("feedBackTime"+"_"+policetype).value= res[1].text;
	$("ReceiveTime"+"_"+policetype).value= res[1].text;
	
	$("feedBackUnit"+"_"+policetype).value= res[2].text;
	$("feedBackUnitId"+"_"+policetype).value= res[3].text;
	$("ReceiveUnit"+"_"+policetype).value= res[2].text;
	$("ReceiveUnitId"+"_"+policetype).value= res[3].text;
	
	$("feedBackPerson"+"_"+policetype).value= res[4].text;
	$("ReceivePerson"+"_"+policetype).value= res[4].text;
	
	$("alarmDesc"+"_"+policetype).value= res[1].text +"发生交通事故；";
	
	$("signSite"+"_"+policetype).value="定位案发地点"
	$("saveData"+"_"+policetype).style.display="";
	$("signSite"+"_"+policetype).style.display="";
	
	ctrlFrom("_"+policetype,1);

//	MapUtils.prototype.moveToAreaCenter(deptcode);
}



//得到新增事件id和时间
function getTrafficNewInfo(type){
	policetype = type;
	//markTool();
	//$("saveData").disabled=false;
	
	//removeAllPoint();
	var url = "dispatch.policeEdit.getNewInfo.d";	
	url = encodeURI(url);
	var params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getTrafficNewInfoRes});
}

//根据后台得到的数据，初始化页面数据项
function getTrafficNewInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	roadalarmid = res[0].text;
	$("alarmId"+"_"+policetype).value=res[0].text;
	$("alarmTime"+"_"+policetype).value= res[1].text;
	$("reportPerson"+"_"+policetype).value= res[4].text;
	
	$("alarmDept"+"_"+policetype).value= res[2].text;
	$("alarmState").value='004001';
}
//清空
function clearInfo(){
	var input=document.getElementsByTagName("input");	
//	for(var i=0;i<input.length;i++){
//		var obj=input[i];
//		var type=obj.getAttribute("type");
//		if(type=="text"){
//			obj.value="";
//		}else{
//			obj.checked=false;	
//		}
//	}
	
	var textarea=document.getElementsByTagName("textarea");	
	for(var i=0;i<textarea.length;i++){
		var obj=textarea[i];
		obj.value="";
	}
	
	var input=document.getElementsByName("editinput");	
	for(var i=0;i<input.length;i++){
		var obj=input[i];
		var type=obj.getAttribute("type");
		if(type=="text"){
			obj.value="";
		}else{
			obj.checked=false;	
		}
	}

	var select=document.getElementsByTagName("select");	
	for(var i=0;i<select.length;i++){
		var obj=select[i];
		obj.value="";
	}
}

//只读属性设置function disableObj(is){
//	var input=document.getElementsByTagName("input");	
//	for(var i=0;i<input.length;i++){
//		var obj=input[i];
//		var type=obj.getAttribute("type");
//		if(type=="text"){
//			obj.readOnly=is;
//		}
//	}
//	
//	var select=document.getElementsByTagName("select");	
//	for(var i=0;i<select.length;i++){
//		var obj=select[i];
//		obj.disabled=is;
//	}
//	
//	var textarea=document.getElementsByTagName("textarea");	
//	for(var i=0;i<textarea.length;i++){
//		var obj=textarea[i];
//		obj.readOnly=is;
//	}
	
	var input=document.getElementsByName("editinput");
	
	for(var i=0;i<input.length;i++){
		var obj=input[i];
		obj.readOnly=is;		
	}	
}


//展示警情详细信息时页面设置
function backInfoPage(){
	$("mapTd").style.display="none";
}

//展示警情详细信息
function getAlarmInfo(alarmId){
	backInfoPage();//页面设置
	var data = watching.m_watchingData['affairData'];//得到警情列表数据集
	var eventState = "";//警情状态
	var eventType = "";//警情类别

	for(var i=0;i< data.length;i+=1) {
		if(data[i].alarmId == alarmId) {
			eventState = data[i].eventState_code;
			eventType = data[i].eventType_code;
			break;	
		}
	}
	switch (eventType) {
		case '001001'://交通事故
			showPoliceDiv("divAccident");
			$("insertOrUpdate_Accident").value = "1";
			policetype = "_Accident";
			getAccInfo(alarmId);
		   	break;
		case '001002':// 交通拥堵
			showPoliceDiv("divCongestion");
		   	break;
		case '001022': //交通管制
		 	showPoliceDiv("divRoadBuild");
			break;
		case '001023'://施工占道
		    showPoliceDiv("divTrafficRestrain");
		    getTrafficInfo(alarmId);
		    break; 
	}

	
	$("signSiteInfo").value="查看案发地点"
	$("signSiteInfo").style.display="";
//	MapUtils.prototype.moveToAreaCenter(deptcode);
}

//控制各事件div的隐藏
function showPoliceDiv(id){
	var divList = ["divAccident","divCongestion","divRoadBuild","divTrafficRestrain"];
	for(var i=0; i<divList.length; i++) {
		if(divList[i] == id){
			$(divList[i]).style.display = "";
		}else{
			$(divList[i]).style.display = "none";
		}
	}
}

//根据警情id得到信息
function getAlarmInfoById(alarmId){
	clearInfo();
	removeAllPoint();
	if(alarmId!=""){	
		var url = "dispatch.policeEdit.getAlarmInfo.d";	
		params="alarmid="+alarmId;
		url = encodeURI(url);		
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:getAlarmInfoRes});
	}
}
function getAlarmInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("col");
	$("alarmIdInfo").innerText=res[0].text;	
	$("alarmTimeInfo").innerText=res[1].text;
	$("alarmSiteInfo").innerText=res[2].text;
	$("alarmDeptInfo").innerText=res[14].text;
	$("roadNameInfo").innerText=res[3].text;
    //$("alarmSouceInfo").innerText=res[4].text;
	$("alarmTypeInfo").innerText=res[5].text;
	$("alarmLevelInfo").innerText=res[6].text;
	$("alarmThinTypeInfo").innerText=res[7].text;
	$("alarmStateInfo").innerText=res[8].text;
	$("alarmDescInfo").innerText=res[9].text;
	$("XInfo").value=res[10].text;
	$("YInfo").value=res[11].text;
	$("roadIdInfo").innerText=res[12].text;	
	$("roadNameInfo").innerText=res[13].text;	
	
	$("disposeTimeInfo").innerText=res[17].text;	
	$("disposePersonInfo").innerText=res[18].text;	
	$("disposeUnitInfo").innerText=res[19].text;	
	$("disposeIdeaInfo").innerText=res[20].text;	
}

/** 
 * desc:数字验证
 * date:2009-02-16
 */
function isValidate(obj){
	var inputValue = parseInt(obj.value);
	if(!checkMath(obj.value)){
			alert("请输入数字！");
			obj.value="";
		    obj.focus();
			return;
	}
	if (inputValue>1000 ||inputValue<0 ){
	    alert("输入值超出范围！");
		obj.value="";
	    obj.focus();
	}
}

/**
 *author:wangxt
 *desc:编辑接报信息
 *date:2010-1-11
*/
function receiveReport(res,feedbackid,alarmid) {
   var returnValuestr = window.showModalDialog("../cpoliceedit/receiveReport.jsp?feedbackid="+feedbackid+"&alarmid="+alarmId+"&depttype="+depttype+"&deptcode="+deptcode, "", "dialogWidth:400px;dialogHeight:350px"); 
   //window.open("../policeEdit/receiveReport.jsp?feedbackid='"+feedbackid+"'&alarmid='"+alarmid+"'","","height=300,width=350,left="+eval(screen.Width-800)/2+",top="+eval(screen.Height-560)/2+"");
}



