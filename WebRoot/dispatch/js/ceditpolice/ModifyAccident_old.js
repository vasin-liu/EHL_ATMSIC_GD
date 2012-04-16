/**
 * 事故事件操作类
 * 作者：lidq
 * 日期：2010-01-11
 */
//编辑事故信息
function modifyAccidnet(type){
	var params = makeParam(type);
	if(!params){
		return false;
	}
	var url = "dispatch.accident.editAccidentInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editAccidentInfo});
}
function editAccidentInfo(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("上报成功!");
		clearInfo();
		var accCarList = $("accCarList");
		var iCar = accCarList.rows.length;
		for(var i=0;i<iCar;i++){
			delCar();
		}
		//$("saveData_Accident").disabled=true;
		watching.readNoticedAffair();
	}else{
		alert("上报失败!");
	}
}

//根据id获取事故详细信息
function getAccInfo(alarmId){
	var params  = "alarmId="+alarmId;
	var url = "dispatch.accident.getAccidentInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showAccidentInfo});
}
//事故信息回调
function showAccidentInfo(xmlHttp){
	var xmlDoc = xmlHttp.responseXml;
	var res = xmlDoc.getElementsByTagName("row");
	if(res){
		var type = "_Accident";
		_setAccidentInfo(res[0].childNodes,type);
		var results = null;
		
		//事故车辆信息
		var carObj = null;
	    var hpzlObj = null;
	    var hphmObj = null;
	    var drvnameObj = null;
	    var accCarList = $("accCarList");
		var iCar = accCarList.rows.length;
		for(var i=1; i<res.length; i++) {
			results = res[i].childNodes;
			if(i==1){//
				
			}else{
				if(i>iCar){
					addCar();
				}
			}
			carObj = $((i)+"Car"+type);
	    	hpzlObj = $((i)+"hpzl"+type);
	    	hphmObj = $((i)+"hphm"+type);
	   		drvnameObj = $((i)+"drvname"+type);
	   		
	   		carObj.carId = results[0].text;
	   		hpzlObj.value = results[1].text;
	   		hphmObj.value = results[2].text;
	   		drvnameObj.value = results[3].text;
		}
	}
}

//根据机构类型和机构id判断操作权限
//参数：上报机构
function checkPrivOnDept(reportDept){
	if(depttype == "2"){//大队用户无接收权限
		$("ReceiveEvent_Accident").disabled = true;
	}
	if((deptcode.substring(0, 4)==reportDept.substring(0, 4) && depttype == "1")){
		$("ReceiveEvent_Accident").disabled = false;
	}
	if((deptcode.substring(0, 2)==reportDept.substring(0, 2) && depttype == "0")){
		$("ReceiveEvent_Accident").disabled = false;
	}
}

//事故上报及基本信息
function _setAccidentInfo(results,type){
	$("insertOrUpdate"+type).value = '1';
	$("alarmId"+type).value = results[0].text;
	$("flowId"+type).value = results[1].text;
	$("feedBackTime"+type).value = results[2].text;
	$("feedBackPerson"+type).value = results[3].text;
	$("pzr"+type).value = results[4].text;
	//$("feedBackType"+type).value = results[5].text;//上报方式 默认不处理
	$("feedBackUnitId"+type).value = results[6].text;
	
	checkPrivOnDept(results[6].text);
	
	$("feedBackUnit"+type).value = results[40].text; 
	
	$("alarmTime"+type).value = results[7].text;
	$("alarmDesc"+type).value = results[8].text;
	$("AlarmAddress"+type).value = results[9].text;
	$("ReceiveUnitId"+type).value = results[10].text;
	$("ReceiveUnit"+type).value = results[11].text;
	$("X"+type).value = results[12].text;
	$("Y"+type).value = results[13].text;
	if(parseInt(results[12].text) && parseInt(results[13].text)){//有经纬度信息
		$("signSite" + type).value="查看案发地点"
	}else{
		$("signSite" + type).value="定位案发地点"
	}
	var _alarmState = results[14].text;
	$("alarmState"+type).value = _alarmState;
	checkOnAlarmState(_alarmState);

	$("roadName"+type).value = results[15].text;//道路id
//	$("roadName"+type).value = results[16].text;//道路名称
	$("kmvalue"+type).value = results[17].text;
	$("mvalue"+type).value = results[18].text;
//	$("ReceiveUnitId"+type).value = results[19].text;
	$("eventType"+type).value = results[20].text;
	$("eventSource"+type).value = results[21].text;
	$("ReceiveTime"+type).value = results[22].text;
	$("ReceiveType"+type).value = results[23].text;
	$("ReceiveUnitId"+type).value = results[24].text;
	$("ReceiveUnit"+type).value = results[25].text;
	$("ReceivePerson"+type).value = results[26].text;
	$("sgdj"+type).value = results[27].text;
	$("sgyy"+type).value = results[28].text;
	$("sgxt"+type).value = results[29].text;
	$("xlbmrs"+type).value = results[30].text;
	$("qjwxswrs"+type).value = results[31].text;
	if(results[32].text == 'true'){
		$("sjjj"+type).checked = true
	}
	if(results[33].text == 'true'){
		$("swsg"+type).checked = true
	}
	if(results[34].text == 'true'){
		$("zcyd"+type).checked = true
	}
	$("ydtkm"+type).value = results[35].text;
	if(results[36].text == 'true'){
		$("yydksg"+type).checked = true
	}
	$("dcswrs"+type).value = results[37].text;
	$("ssrs"+type).value = results[38].text;
	if(results[39].text == 'true'){
		$("whsg"+type).checked = true
	}
	ctrlFrom(type,0);
	$("saveData"+type).value = "警情续报";
}

/**
 * 根据事件状态控制流程细节
 */
function checkOnAlarmState(_alarmState){
	switch (_alarmState){//事件状态处理
		case '004031'://已上报
			
			break;
		case '004032'://支队已接收
			
			break;
		case '004033'://支队已处理
			
			break;
		case '004034'://总队已接收
			
			break;
		case '004035'://总队已处理
			
			break;
		case '004036'://处理完毕
			
			break;
		default:
			break;
	}
}

/**
 * 初始上报信息和接警信息不允许修改
 */
function ctrlFrom(type,flag){
	if(flag == 0){
		$("feedBackTime"+type).onclick = function(event){return false;};
		$("ReceiveTime"+type).onclick = "";
		$("ReceiveType"+type).disabled = true;
		
		$("feedBackPerson"+type).readOnly = true;
		$("pzr"+type).readOnly = true;
		$("feedBackUnit"+type).readOnly = true;
		$("ReceiveUnit"+type).readOnly = true;
		$("ReceivePerson"+type).readOnly = true;
	}else{
		$("feedBackTime"+type).onclick = function(){SelectDateTime(this);};
		$("ReceiveTime"+type).onclick = function(){SelectDateTime(this);};
		$("ReceiveType"+type).disabled = false;
		
		$("feedBackPerson"+type).readOnly = false;
		$("pzr"+type).readOnly = false;
		$("feedBackUnit"+type).readOnly = false;
		$("ReceiveUnit"+type).readOnly = false;
		$("ReceivePerson"+type).readOnly = false;
	}
}

//校验并封装上报信息
function makeParam(type){
	var insertOrUpdate = $("insertOrUpdate"+type).value;
	//上报信息
	var alarmId = $("alarmId"+type).value;
	var feedBackId = $("flowId"+type).value;//上报流程id
	var feedBackTime = $("feedBackTime"+type).value;
	var feedBackPerson = $("feedBackPerson"+type).value;
	var feedBackType = '023003';//上报方式默认为公安网上报
	var responsiblePerson = $("pzr"+type).value;//上报批准人
	if(responsiblePerson == ""){
		alert("请填写上报批准人！");
		$("pzr"+type).focus();
		return false;
	}
	var feedBackUnit = $("feedBackUnitId"+type).value;
	//接警信息	
	var ReceiveType = $("ReceiveType"+type).value;
	var ReceiveUnitId = $("ReceiveUnitId"+type).value;
	var ReceiveUnit = $("ReceiveUnit"+type).value;
	var ReceiveTime = $("ReceiveTime"+type).value;
	var ReceivePerson = $("ReceivePerson"+type).value;
	
	//事故简要信息	
	var X=$("X"+type).value;
	var Y=$("Y"+type).value;
	var alarmState=$("alarmState"+type).value;
	var alarmTime = $("alarmTime"+type).value;
	var AlarmAddress = $("AlarmAddress"+type).value;
	var roadID = $("roadName"+type).value;
	var roadName = $("roadName"+type).options[$("roadName"+type).selectedIndex].text;
	if(roadName=="请选择"){
		roadName = "";
	}
	var kmvalue = $("kmvalue"+type).value;
	var mvalue = $("mvalue"+type).value;
	if((kmvalue != "" || mvalue != "") && roadID == ""){
		alert("请选择道路！");
		$("roadName"+type).focus();
		return false;
	}
	
	var eventType = $("eventType"+type).value;
	var eventSource = $("eventSource"+type).value;
	
	var sjjj = 'false';
	if($("sjjj"+type).checked == true){
		sjjj = 'true';
	}
	var swsg = 'false';
	if($("swsg"+type).checked == true){
		swsg = 'true';
	}
	var whsg = 'false';
	if($("whsg"+type).checked == true){
		whsg = 'true';
	}
	var zcyd = 'false';
	if($("zcyd"+type).checked == true){
		zcyd = 'true';
	}
	var ydtkm = $("ydtkm"+type).value;
	
	var yydksg = 'false';
	if($("yydksg"+type).checked == true){
		yydksg = 'true';
	}
	
	//事故车辆
	var accCarList = $("accCarList");
    var len = accCarList.rows.length;
	var carId = "";
	var hpzl = "";
	var hphm = "";
	var drvname = "";
    
    var carObj = null;
    var hpzlObj = null;
    var hphmObj = null;
    var drvnameObj = null;
    for(var i=0;i<len;i++){
    	carObj = $((i+1)+"Car"+type);
    	hpzlObj = $((i+1)+"hpzl"+type);
    	hphmObj = $((i+1)+"hphm"+type);
   		drvnameObj = $((i+1)+"drvname"+type);
   		if(hpzlObj.value == "" || hphmObj.value == "" ||drvnameObj.value == ""){
   			if(hpzlObj.value == "" && hphmObj.value == "" && drvnameObj.value == ""){
   				return true;
   			}else{
	   			alert("请完整填写事故车辆和驾驶人信息！");
	   			drvnameObj.focus();
	   			return false;
   			}
   		}
   		if(carObj.carId == ""){
   			carId += "0,";
   		}else{
	   		carId += carObj.carId+",";
   		}
   		hpzl += hpzlObj.value+",";
		hphm += hphmObj.value+",";
		drvname += drvnameObj.value+",";
    }
	//事故详细信息
	var sgdj = $("sgdj"+type).value;
	var sgyy = $("sgyy"+type).value;
	var sgxt = $("sgxt"+type).value;
	var dcswrs = $("dcswrs"+type).value;
	var ssrs = $("ssrs"+type).value;
	var xlbmrs = $("xlbmrs"+type).value;
	var qjwxswrs = $("qjwxswrs"+type).value;
	
	var	alarmDesc = $("alarmDesc"+type).value;
	
	var params = {};
	params["insertOrUpdate"]=insertOrUpdate;
	params["alarmId"]=alarmId;
	params["feedBackId"]=feedBackId;
	params["feedBackTime"]=feedBackTime;
	params["feedBackPerson"]=feedBackPerson;
	params["feedBackType"]=feedBackType;
	params["feedBackUnit"]=feedBackUnit;
	params["responsiblePerson"]=responsiblePerson;
	params["ReceiveType"]=ReceiveType;
	params["ReceiveUnitId"]=ReceiveUnitId;
	params["ReceiveUnit"]=ReceiveUnit;
	params["ReceiveTime"]=ReceiveTime;
	params["ReceivePerson"]=ReceivePerson;
	params["X"]=X;
	params["Y"]=Y;
	params["alarmState"]=alarmState;
	params["alarmTime"]=alarmTime;
	params["AlarmAddress"]=AlarmAddress;
	params["roadID"]=roadID;
	params["roadName"]=roadName;
	params["kmvalue"]=kmvalue;
	params["mvalue"]=mvalue;
	params["eventType"]=eventType;
	params["eventSource"]=eventSource;
	
	params["sjjj"]=sjjj;
	params["swsg"]=swsg;
	params["whsg"]=whsg;
	params["zcyd"]=zcyd;
	params["ydtkm"]=ydtkm;
	params["yydksg"]=yydksg;
	
	params["carId"]=carId;
	params["hpzl"]=hpzl;
	params["hphm"]=hphm;
	params["drvname"]=drvname;
	
	params["sgdj"]=sgdj;
	params["sgyy"]=sgyy;
	params["sgxt"]=sgxt;
	params["dcswrs"]=dcswrs;
	params["ssrs"]=ssrs;
	params["xlbmrs"]=xlbmrs;
	params["qjwxswrs"]=qjwxswrs;
	params["alarmDesc"]=alarmDesc;
	
	return params;
}

//添加事故车辆
function addCar(){
	var accCarList = $("accCarList");
    var len = accCarList.rows.length;
    var last_row = accCarList.rows[len-1];  
    var clone_node = last_row.cloneNode(true);  
    accCarList.appendChild(clone_node)
    
    //修改新增车辆的相关id
    clone_node.id = (len +1)+"Car_Accident"; 
    var selectObj = clone_node.getElementsByTagName("SELECT")[0];
    selectObj.id = (len +1)+"hpzl_Accident";
    selectObj.value = "";
    var inputObj = clone_node.getElementsByTagName("INPUT");
    inputObj[0].id = (len +1)+"hphm_Accident";
    inputObj[0].value = "";
    inputObj[1].id = (len +1)+"drvname_Accident";
    inputObj[1].value = "";
}
//删除事故车辆
function delCar(){
	var accCarList = $("accCarList");
	var iCar = accCarList.rows.length;
	if(iCar>0){
		if(iCar == 1){
			return false;
			$("delCar_Accident").disabled = true;
		}
		accCarList.deleteRow(iCar-1);
	}else{
		$("delCar_Accident").disabled = true;
	}
}