//var editPage;
var amid="";
var temp="";
//初始化页面
 function initPages(){

/*luchunqiang 2009-4-17 事件绑定给弄到前台 jsp中弄了	var addbtn=$("addPolice");
	addbtn.onclick = openAddPolice;
*/
	toEditPage();
	watching.setRowClickHandler(getAlarmInfo);

}


//打开编辑页面
function openAddPolice(){
	clearInfo();
	//生成AlarmID
	getNewInfo();
//	getAlarmId();
//	disableObj(false);
	
}

//快速定位道路上的点
function markOnRoad(type){
	if(!type){
		fillListBox("dlmc_td","dlmc","111","SELECT DISTINCT DLBH,DLMC FROM LCB_PT WHERE DLBH IS NOT NULL AND DLBH != ' '","请选择","","","sde");
		//$('markRoad').style.display = '';
	}
	
	if(type == 'info'){
		$('markRoadinfo').style.display = '';
		if($('dlmcinfo').value == ''){
			alert("请选择道路！");
			return;
		}
		if($('kmvalueinfo').value == ''){
			alert("请输入千米值！");
			return;
		}
		var params={};
		//快速定位 add by lidq 20091216
		params["alarmId"] = $("alarmIdInfo").innerText
		params["dlmc"] = $('dlmcinfo').value ;
		params["kmz"]  = $('kmvalueinfo').value ;
		params["bmz"]  = $('mvalueinfo').value ;
		var url = "dispatch.policeEdit.getPointOnRoad.d";	
		url = encodeURI(url);
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:getPointRes});
	}else{
		
	}
}
/**
 * 快速定位反馈
 */
function getPointRes(xmlHttp){
	var Res = xmlHttp.responsetext;	
	if(Res != 'false'){
		var points = Res.split(",");
		if(points && points.length == 2){
			alert("定位成功！");
		}
		$("XInfo").value = points[0];
		$("YInfo").value = points[1];
	}else{
		alert("定位失败！");
		return;
	}
}
//得到新增事件id和时间function getNewInfo(){
	markTool();
	$("saveData").disabled=false;
	$("divEditAlarm").style.display="";
	$("divAlarmInfo").style.display="none";
	removeAllPoint();
	var url = "dispatch.policeEdit.getNewInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getNewInfoRes});
}


function getNewInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	$("alarmId").value=res[0].text;
	$("alarmTime").value= res[1].text;
	$("alarmDept").value= res[2].text;
	$("alarmDept").deptid= res[3].text;
	$("disposeUnit").value= res[2].text;
	$("disposeUnit").deptid= res[3].text;
	$("disposeTime").value= res[1].text;
	
	$("reportUnit").deptid= res[3].text;
	$("reportUnit").value= res[2].text;
	$("reportTime").value= res[1].text;
	$("reportPerson").value= res[4].text;
	
	$("signSite").value="定位案发地点"
	$("saveData").style.display="";
	$("signSite").style.display="";
	$("alarmSouce").value='002007';
	$("alarmState").value='004001';
	$("alarmState").disabled=true;
	MapUtils.prototype.moveToAreaCenter(deptcode);
}

//清空
function clearInfo(){
//	var input=document.getElementsByTagName("input");	
//	for(var i=0;i<input.length;i++){
//		var obj=input[i];
//		var type=obj.getAttribute("type");
//		if(type=="text"){
//			obj.value="";
//		}
//	}
	
//	var textarea=document.getElementsByTagName("textarea");	
//	for(var i=0;i<textarea.length;i++){
//		var obj=textarea[i];
//		obj.value="";
//	}
	
	var input=document.getElementsByName("editinput");	
	for(var i=0;i<input.length;i++){
		var obj=input[i];
		obj.value="";		
	}
	
	var compleinput=document.getElementsByName("compleeditinput");	
	for(var i=0;i<compleinput.length;i++){
		var obj=compleinput[i];
		obj.value="";		
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

//显示地图
function showMap(tag){
	$('divEditAlarm').style.display = "none";   
	$('divAlarmInfo').style.display = "none";   
	$('divComplement').style.display="none";	
	$('mapTd').style.display = ""; 
	if(tag=="1"){		
		$('backEditbtn').style.display = "";   
		$('backInfobtn').style.display = "none";   
		$('backCompleEditbtn').style.display = "none"; 
	}
	else if(tag=="3"){
		$('backEditbtn').style.display = "none";   
		$('backInfobtn').style.display = "none";   
		$('backCompleEditbtn').style.display = ""; 
		temp="comple";
	}
	else if(tag=="2"){
		$('backEditbtn').style.display = "none";   
		$('backInfobtn').style.display = "";   
		$('backCompleEditbtn').style.display = "none"; 
		temp="";		
		showPoint();
	}
	else{
		$('backEditbtn').style.display = "none";   
		$('backInfobtn').style.display = "";   
		$('backCompleEditbtn').style.display = "none"; 
		temp="";		
		showPoint();
	}
}

//定位地图
function markMap()
{         
	//地图标注
	var mapDiv =toolbar.mapDiv;
	Event.observe(mapDiv, "click", markAccident.bindAsEventListener());
}


//定位地图
function markAccident(ev)
{
		var alarmid=$("alarmId").value;
		var mapDiv = toolbar.mapDiv;
		var infoCoord =Util.getMouseRelativePixel(ev, mapDiv);
		var zoom = toolbar.model.getZoom();
		var x=Util.getCoordinateByPixel(infoCoord, zoom).x/1e16;
		var y=Util.getCoordinateByPixel(infoCoord, zoom).y/1e16;
		
		var offsetX = ev.offsetX;
		var offsetY = ev.offsetY;
		var srcTarget = ev.srcElement || ev.srcTarget;
		var url="dispatch.policeEdit.getNearRoadByMouse.d";
		var params = encodeURI("x="+x+"&&y="+y);
		
		new Ajax.Request(url,
			 {	method: 'post', 
            	parameters: params,
            	onComplete:function(xmlHttp)
            	{
            		
					 var xmlDoc = xmlHttp.responseXML;
   					
					 var results = xmlDoc.getElementsByTagName("row")
					 var size = results.length;
					 
					 if( 0 == size)
					 {			
//					 	alert("未获取地图信息,请您重新定位。");						 										 	
//					 	return ;
						if(temp=="comple"){	
							setRoadInfo("","");
							$("compleX").value=x;
							$("compleY").value=y;
						}else{
						 	$("divEditAlarm").style.display="";
							$("mapTd").style.display="none"	;
							$("x").value=x;
							$("y").value=y;		
						}
						writeMap(infoCoord,zoom,alarmid);
					 }
					 if( 1 == size )
					 {					 	
					 	var cols = results[0].getElementsByTagName("col");
						setRoadInfo(cols[0].text,cols[1].text);
						
						$("x").value=x;
						$("y").value=y;
						$("compleX").value=x;
						$("compleY").value=y;
						writeMap(infoCoord,zoom,alarmid);
					 	 
					 }
					 if( 1 < size )
					 {
					 	//多项结果，提供用户选择
						var chose = new ListChose(offsetX,offsetY,srcTarget,xmlDoc);												
						chose.setCellClickHandler(getRoadInfo);
						$("x").value=x;
						$("y").value=y;
						$("compleX").value=x;
						$("compleY").value=y;
						writeMap(infoCoord,zoom,alarmid);
					 }					
				}
			});
		
		Event.stop(ev);		
}

function getRoadInfo(cell){	
	
	setRoadInfo(cell.itemId ,cell.firstChild.nodeValue);
	ListChose.close();	
}

//id:路段编号，name：路段名称 
function setRoadInfo(id,name){			
		if(temp=="comple"){			
			$("compleroadId").value=id;
			$("compleroadName").value=name;
			$("divComplement").style.display="";
			$("mapTd").style.display="none";
		}else{
			$("roadId").value=id;
			$("roadName").value=name;
			$("divEditAlarm").style.display="";
			$("mapTd").style.display="none";
		}
}

function writeMap(infoCoord,zoom,alarmid){
	
//	var mapDiv = $("map_" + toolbar.model.getId());
	var mapDiv = map.getVMLDiv();
	var pointSymbol = document.createElement("div");
	var divID =Util.createUniqueID("accidentDiv_"+alarmid);
	var pointid ="accidentDiv_"+alarmid;
	
	var realCoord = Util.getCoordinateByPixel(infoCoord, zoom);	
	
	var delDiv = document.getElementById(pointid);
	if(delDiv!=null){
		delDiv.parentNode.removeChild(delDiv);
	}
	//创建图形符号
	amid=pointid;
	pointSymbol.id = pointid;
	pointSymbol.onselect = null;
	pointSymbol.style.position = "absolute";
	pointSymbol.style.border = "0px solid #999999";
	pointSymbol.style.fontSize = "12px";
	pointSymbol.style.padding = "1px";
	pointSymbol.style.zIndex = 11;
	pointSymbol.style.left = (infoCoord.x) + "px";
	pointSymbol.style.top = (infoCoord.y) + "px";	
	var img = Util.createImg("ps", 0, 0, 11, 11, "../../images/alarm/alarm.gif");
	pointSymbol.appendChild(img);
	pointSymbol.style.display = "";
	img.tag = pointid;
	img.style.cursor ="hand";
	img.position = realCoord;
	mapDiv.appendChild(pointSymbol);
	
}

//返回编辑页面
function toEditPage(){
	$("backEditbtn").onclick=function(){
		pageChange();
	}
	$("backInfobtn").onclick=function(){
		backInfoPage();
	}
	$("backCompleEditbtn").onclick=function(){
		backCompleChange();
	}
}

function pageChange(){
	if($('divEditAlarm')&&$('mapTd')){
		$('divEditAlarm').style.display = "";   
		$('mapTd').style.display = "none";   
	}
}

function backCompleChange(){
	if($('divComplement')&&$('mapTd')){
		$('divComplement').style.display = "";   
		$('mapTd').style.display = "none";   
	}
}

function backInfoPage(){
	$("divAlarmInfo").style.display="";
	$("divEditAlarm").style.display="none";
	$("mapTd").style.display="none";
}

//编辑一条报警基本信息function editAlarmInfo(){
	var alarmTime=$("alarmTime").value;
	var alarmSite=$("alarmSite").value;
	var alarmDeptName=$("alarmDept").value;
	var alarmDept=$("alarmDept").deptid;
	
	// add by wangxt 090516
	var subEventSource =$("subEventSource").value;
	var alarmSouce=$("alarmSouce").value;
	var alarmType=$("alarmType").value;
	var alarmLevel=$("alarmLevel").value;
	var alarmThinType=$("alarmThinType").value;
	var alarmState=$("alarmState").value;
	var alarmDesc=$("alarmDesc").value;
	var alarmId=$("alarmId").value;	
	var roadId=$("roadId").value;	
	var roadName=$("roadName").value;	
	var X=$("X").value;
	var Y=$("Y").value;
		
	var disposeTime=$("disposeTime").value;	
	var disposePerson=$("disposePerson").value;	
	var disposeUnitName=$("disposeUnit").value;	
	var disposeUnit=$("disposeUnit").deptid;	
	var disposeIdea=$("disposeIdea").value;		
	
	var reportUnit=$("reportUnit").deptid
	var reportUnitName=$("reportUnit").value
	var reportTime=$("reportTime").value
	var reportPerson=$("reportPerson").value
	
	var Level=$("alarmLevel");
	var ThinType=$("alarmThinType");
	var alarmLevelValue=Level[Level.selectedIndex].text;
	var alarmThinTypeValue="";
	if(ThinType.tagName=="SELECT"){
		alarmThinTypeValue=ThinType[ThinType.selectedIndex].text;
	}
	
	
	var disposeSelfChk=$("disposeSelfChk");
	if(disposeSelfChk.checked){
		alarmState="004005";
	}
	
		
	if(alarmTime==""){
		alert("请输入报警时间");
		return;
	}
	if(alarmDept==""){
		alert("请输入报警机构");
		return;
	}
	if(alarmSouce==""){
		alert("请输入事件来源");
		return;
	}
	if(alarmType==""){
		alert("请输入事件类型");
		return;
	}
//	if(alarmLevel==""){
//		alert("请输入事件程度");
//		return;
//	}
	if(alarmState==""){
		alert("请输入事件状态");
		return;
	}
   
	if(subEventSource==""){
		alert("请输入分中心事件来源");
		return;
	}
	
	if($('markRoad').style.display == ''){
		if($('dlmc').value == ''){
			alert("请选择道路！");
			return;
		}
		if($('kmvalue').value == ''){
			alert("请输入千米值！");
			return;
		}
	}
	var params={};
	params["alarmTime"]=alarmTime;
	params["alarmSite"]=alarmSite;
	params["alarmDept"]=alarmDept;
	// add by wangxt 090515
	params["subEventSource"]=subEventSource;
	params["alarmSouce"]=alarmSouce ;
	params["alarmType"]=alarmType ;
	params["alarmLevel"]=alarmLevel ;
	params["alarmThinType"]=alarmThinType ;
	params["alarmState"]=alarmState ;
	params["alarmDesc"]=alarmDesc ;
	params["alarmId"]=alarmId ;
	params["roadId"]=roadId ;
	params["roadName"]=roadName ;
	params["X"]=X ;
	params["Y"]=Y ;
	
	params["alarmLevelValue"]=alarmLevelValue;
	params["alarmThinTypeValue"]=alarmThinTypeValue;
	
	params["disposeTime"]=disposeTime ;
	params["disposePerson"]=disposePerson.substring(0,disposePerson.length-1) ;
	params["disposeUnit"]=disposeUnit ;
	params["disposeIdea"]=disposeIdea ;
	
	params["reportUnit"]=reportUnit ;
	params["reportUnitName"]=reportUnitName ;
	params["reportTime"]=reportTime ;
	params["reportPerson"]=reportPerson ;
	
	//快速定位 add by lidq 20091216
	if($('markRoad').style.display = ''){
		params["dlmc"] = $('dlmc').value ;
		params["kmz"]  = $('kmvalue').value ;
		params["bmz"]  = $('mvalue').value ;
	}
	
	//事故
	if(isAccident(alarmType)){
		editAccidentInfo(params);
	}
	//拥堵
	if(isCongestion(alarmType)){
		editCongestionInfo(params);
	}
	//故障车	
	if(isExceptionCar(alarmType)){
		editExceptionCarInfo(params);
	}
	//治安
	if(isPoliceEvent(alarmType)){
		editPoliceEventInfo(params);
	}
	//恶劣天气
	if(isWeather(alarmType)){
		editBadWeatherInfo(params);
	}
	//市政
	if(isTownPlan(alarmType)){
		editTownplanInfo(params);
	}
	
	//火灾
	if(isFireAndBlast(alarmType)){
		editFireAndBlastInfo(params);
	}
	//地质灾害
	if(isGeoLogicDisaster(alarmType)){
		editGeoLogicDisasterInfo(params);
	}
	
//	if(isBlackList(alarmType)){
//		$("signSite").style.display="none";
//		$("saveData").style.display="none";
//	}
}	

//编辑一条事故信息
function editAccidentInfo(params){	
	params["accidentAlarmCarType"]=$("accidentAlarmCarType").value;
	params["accidentAlarmCarCodeDept"]=$("accidentAlarmCarCodeDept").value;
	params["accidentAlarmCarCode"]=$("accidentAlarmCarCode").value;
	params["accidentAlarmCarGenre"]=$("accidentAlarmCarGenre").value;
	params["accidentremark"]=$("accidentremark").value;
	params["accidentAffectLevel"]=$("accidentAffectLevel").value;
	
	var url = "dispatch.policeEdit.editAccidentInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editAccidentInfoRes});
}
function editAccidentInfoRes(xmlHttp){	
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

//编辑一条拥堵信息
function editCongestionInfo(params){
	
	params["congestionAffectLevel"]=$("congestionAffectLevel").value;
	params["congestionRemark"]=$("congestionRemark").value;
	
	var url = "dispatch.policeEdit.editCongestionInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editCongestionInfoRes});
}
function editCongestionInfoRes(xmlHttp){	
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

//编辑一条故障车信息
function editExceptionCarInfo(params){
	params["exceptionCarAddress"]=$("exceptionCarAddress").value;
	params["exceptionCause"]=$("exceptionCause").value;
	params["exceptioCarShape"]=$("exceptioCarShape").value;
	params["exceptioAffectLevel"]=$("exceptioAffectLevel").value;
	params["exceptionRemark"]=$("exceptionRemark").value;
	
	var url = "dispatch.policeEdit.editExceptionCarInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editExceptionCarInfoRes});
}
function editExceptionCarInfoRes(xmlHttp){	
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

//编辑一条治安信息
function editPoliceEventInfo(params){
	params["PoliceEventAffectTrafficLevel"]=$("PoliceEventAffectTrafficLevel").value;
	params["PoliceEventAffectRoad"]=$("PoliceEventAffectRoad").value;
	params["PoliceEventRemark"]=$("PoliceEventRemark").value;
	params["PoliceEventTime"]=$("PoliceEventTime").value;	
	
	var url = "dispatch.policeEdit.editPoliceEventInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editPoliceEventInfoRes});
}
function editPoliceEventInfoRes(xmlHttp){	
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
//编辑一条恶劣天气信息
function editBadWeatherInfo(params){
	params["weathersituation"]=$("weathersituation").value;
	params["weatherRoadStatus"]=$("weatherRoadStatus").value;
	params["weatherAffectRoad"]=$("weatherAffectRoad").value;
	params["weatheAffectDept"]=$("weatheAffectDept").value;	
	params["weatherAlarmPhone"]=$("weatherAlarmPhone").value;	
	params["weatherAlarmPerson"]=$("weatherAlarmPerson").value;	
	params["weatherAffectextent"]=$("weatherAffectextent").value;	
	params["weatherRemark"]=$("weatherRemark").value;	
	
	var url = "dispatch.policeEdit.editBadWeatherInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editBadWeatherInfoRes});
}
function editBadWeatherInfoRes(xmlHttp){	
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

//编辑一条市政信息
function editTownplanInfo(params){
	params["townPlanHappenTime"]=$("townPlanHappenTime").value;
	params["townPlanPerson"]=$("townPlanPerson").value;
	params["townPlanAffectRoad"]=$("townPlanAffectRoad").value;
	params["townPlanAffectExent"]=$("townPlanAffectExent").value;	
	params["townPlanLevel"]=$("townPlanLevel").value;	
	params["townPlanRemark"]=$("townPlanRemark").value;	
	
	var url = "dispatch.policeEdit.editTownplanInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editTownplanInfoRes});
}
function editTownplanInfoRes(xmlHttp){	
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
//编辑火灾信息
function editFireAndBlastInfo(params){	
	params["fireTime"]=$("fireTime").value;
	params["fireAffectLevel"]=$("fireAffectLevel").value;
	params["fireHaveCasualty"]=$("fireHaveCasualty").value;
	params["fireAlarmPerson"]=$("fireAlarmPerson").value;	
	params["fireRemark"]=$("fireRemark").value;			
	var url = "dispatch.policeEdit.editFireAndBlastInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editFireAndBlastInfoRes});
}
function editFireAndBlastInfoRes(xmlHttp){	
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

//编辑地质灾害
function editGeoLogicDisasterInfo(params){
	params["GeoLogicDisasterTime"]=$("GeoLogicDisasterTime").value;
	params["GeoLogicDisasterLevel"]=$("GeoLogicDisasterLevel").value;
	params["GeoLogicDisasterAffectExent"]=$("GeoLogicDisasterAffectExent").value;
	params["GeoLogicDisasterAlarmPerson"]=$("GeoLogicDisasterAlarmPerson").value;	
	params["GeoLogicDisasterRemark"]=$("GeoLogicDisasterRemark").value;			
	var url = "dispatch.policeEdit.editGeoLogicDisasterInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editGeoLogicDisasterInfoRes});
}
function editGeoLogicDisasterInfoRes(xmlHttp){	
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


////编辑一条布控信息
//
//function editBlackListInfo(params){
//	params["blackListCarNumber"]=$("blackListCarNumber").value;
//	params["blackListCarSort"]=$("blackListCarSort").value;
//	params["blackListRemark"]=$("blackListRemark").value;
//	
//	var url = "dispatch.policeEdit.editBlackListInfo.d";	
//	url = encodeURI(url);
//	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editBlackListInfoRes});
//}
//function editBlackListInfoRes(xmlHttp){	
//	var xmlDoc = xmlHttp.responseText;		
//	if(xmlDoc=="success"){
//		alert("添加成功!");
//		clearInfo();
//	}else{
//		alert("添加失败!");
//	}
//}

//打开子单页面
function openSonList(id){
	window.open("../policeEdit/SonList.jsp?id="+id,"","width=860px;height=600px;")
}


function getAlarmInfo(alarmId){
	backInfoPage();
	$("signSiteInfo").value="查看案发地点"
	$("signSiteInfo").style.display="";
	getAlarmInfoById(alarmId);
	MapUtils.prototype.moveToAreaCenter(deptcode);
}


var reX="";
var reY="";
var reAlarmId="";
//定位地图
function reMarkPoint(ev,alarmId)
{		
		var mpDv = toolbar.mapDiv;
		var infoCoord =Util.getMouseRelativePixel(ev, mapDiv);
		var zoom = toolbar.model.getZoom();
		var x=Util.getCoordinateByPixel(infoCoord, zoom).x/1e16;
		var y=Util.getCoordinateByPixel(infoCoord, zoom).y/1e16;
		
		var offsetX = ev.offsetX;
		var offsetY = ev.offsetY;
		var srcTarget = ev.srcElement || ev.srcTarget;
		var url="dispatch.policeEdit.getNearRoadByMouse.d";
		var params = encodeURI("x="+x+"&&y="+y);
		
		var roadId="";
		var roadName="";
		
		new Ajax.Request(url,
			 {	method: 'post', 
            	parameters: params,
            	onComplete:function(xmlHttp)
            	{
            		
					 var xmlDoc = xmlHttp.responseXML;
   					
					 var results = xmlDoc.getElementsByTagName("row")
					 var size = results.length;
				
					 if( 0 == size )
					 {	
					 	reX=x;
					 	reY=y;
						writeMap(infoCoord,zoom,alarmId);			
						rePointInfo(alarmId,roadId,roadName);								
					 }
				
					 if( 1 == size )
					 {					 	
					 	var cols = results[0].getElementsByTagName("col");
					 	roadId=cols[0].text;
					 	roadName=cols[1].text;
					 	reX=x;
					 	reY=y;
						writeMap(infoCoord,zoom,alarmId);			
						rePointInfo(alarmId,roadId,roadName);								
					 }
					 if( 1 < size )
					 {
					 	//多项结果，提供用户选择
						var chose = new ListChose(offsetX,offsetY,srcTarget,xmlDoc);	
						reX=x;
					 	 reY=y;
					 	 reAlarmId=alarmId;											
						chose.setCellClickHandler(reGetRoadInfo);
						 
						 writeMap(infoCoord,zoom,alarmId);			
						 //rePointInfo(alarmId,roadId,roadName);
					 }
					 
							
				}
			});
		
		Event.stop(ev);		
}

function reGetRoadInfo(cell){
	rePointInfo(reAlarmId,cell.itemId ,cell.firstChild.nodeValue);
	ListChose.close();	
}


function rePointInfo(alarmId,roadId,roadName){	
	var params={};
	params["roadId"]=roadId;
	params["roadName"]=roadName;
	params["X"]=reX;
	params["Y"]=reY;	
	params["alarmId"]=alarmId;			
	var url = "dispatch.policeEdit.reMarkPoint.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:rePointInfoRes});
}
function rePointInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("标注成功！")
		reX="";
		reY="";
		reAlarmId="";
	}else{
		
	}
}

function markTool(){
	AlarmTool.prototype.clickHandler=function(e, toolbar){
		markAccident(e);
	}	
}


//根据警情id得到信息
function getAlarmInfoById(alarmId){
	clearInfo();
	removeAllPoint();
	if(alarmId!=""){	
					
		//点击获取子单
		$("showsonListInfo").onclick=function (){
			openSonList(alarmId);
		}
		
		//所有元素为只读
	//	disableObj(true);
		
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
	
	//add by wangxt 
	if(res[4].text == "分中心上报") {
		$("alarmSouceInfo").innerText = res[21].text +"("+ res[4].text + ")";
	}else{
		$("alarmSouceInfo").innerText=res[4].text;
	}
	
	//显示子单按钮	
	if(res[22].text=="1"){
		$("showsonListInfo").style.display="";
	}else{
		$("showsonListInfo").style.display="none";
	}	
		
	//如果有经纬度
	if($("map")&&res[10].text&&res[11].text){
		$("markRoadinfo").style.display = "none";
		$("signSiteInfo").value="查看案发地点";
		markTool();
		centerAlarm(res[10].text,res[11].text,res[0].text);	
	}else{
		$("markRoadinfo").style.display = "";
		$("markPointinfo").style.display = "";
		$("markPointinfo").value = "快速定位";
		
		$("signSiteInfo").style.display = "";
		$("signSiteInfo").value = "定位案发地点"
		AlarmTool.prototype.clickHandler=function(e, toolbar){
//			alert("<><>")
			reMarkPoint(e,res[0].text)
		}		
	}
	
	if(isAccident(res[15].text)){
		showAccidentInfo(res);	
	}
	else if(isCongestion(res[15].text)){
		showCongestionInfo(res);
	}
	else if(isExceptionCar(res[15].text)){
		showExceptionCarInfo(res);
	}
	else if(isPoliceEvent(res[15].text)){
		showPoliceEventInfo(res);
	}
	else if(isBlackList(res[15].text)){
		showBlackListInfo(res);
	}
	else if(isWeather(res[15].text)){
		showBadWeatherInfo(res);	
	}
	else if(isTownPlan(res[15].text)){
		showTownPlanInfo(res);
	}
	else if(isFireAndBlast(res[15].text)){
		showFireAndBlastInfo(res);
	}	
	else if(isGeoLogicDisaster(res[15].text)){
		showGeoLogicDisasterInfo(res);
	}
	
	else{
		showBaseInfo(res);
	}
}

function getLevel(res6,res7){
	$("alarmLevel").value=res6;
	$("alarmLevel").disabled=true;	
	$("alarmThinType").value=res7;
	$("alarmThinType").disabled=true;
}

//显示没有事件类型的事件信息
function showBaseInfo(res){
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
}	

//显示事故信息
function showAccidentInfo(res){
	
	//ALARMID,ALARMPERSON,AlarmUnit,ALARMPHONE,PHONENAME,PHONEADDRESS,ALARMPERSONSEX,ALARMPERSONADDRESS,ALARMCARTYPE,ALARMCARNUMBER,
	// FLESHWOUNDPERSONCOUNT,DEATHPERSONCOUNT,ISDANAGERCAR,REMARK,GBHPERSONCOUNT,GRAPPLEPERSONCOUNT,DEALWITHPERSONCOUNT,SALVATIONPERSONCOUNT,
	// ECONOMYLOSS,ISUNCOVERCRIMINALCASE,ISCHECKORDERCASE,ISRESOLVEDISSENSION,ISFEEDBACKEND,ALARMCARGENRE,ALARMMANNER
	$("accidentInfoTbody").style.display="";
	$("congestionInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
	
	var accidentres=new Array();	
	accidentres.push("accidentAlarmPersonInfo");
	accidentres.push("accidentAlarmBrachInfo");
	accidentres.push("accidentAlarmPhoneInfo");
	accidentres.push("accidentPersonNameInfo");
	accidentres.push("accidentPersonAddressInfo");
	accidentres.push("accidentAlarmPersonSexInfo");
	accidentres.push("accidentAlarmPersonAddressInfo");
	accidentres.push("accidentAlarmCarTypeInfo");
	accidentres.push("accidentAlarmCarCodeInfo");
	accidentres.push("accidentInjuredNumInfo");
	accidentres.push("accidentDiedNumInfo");
	accidentres.push("accidentIsBadCarInfo");
	accidentres.push("accidentRemarkInfo");
	accidentres.push("accidentGBHPersonCountInfo");
	accidentres.push("accidentGrapplePersonCountInfo");
	accidentres.push("accidentDealwithPersonCountInfo");
	accidentres.push("accidentSalvationPersonCountInfo");
	accidentres.push("accidentEconomyLossInfo");
	accidentres.push("accidentIsUncoverCriminalCaseInfo");
	accidentres.push("accidentIsCheckOrderCaseInfo");
	accidentres.push("accidentIsResolveDissensionInfo");
	accidentres.push("accidentIIsFeedbackEndInfo");	
	accidentres.push("accidentCarGenreInfo");
	accidentres.push("acciedntAlarmMannerInfo");
	accidentres.push("accidentAffectLevelInfo");
	for(var i=0;i<accidentres.length;i++){
	
		$(accidentres[i]).innerText=res[i+23].text;
	}

}

//显示拥堵信息
function showCongestionInfo(res){	
//	fillListBox("alarmLevelTd","alarmLevel","105","select id,name from t_attemper_code where id like '005%'","请选择","getLevel('"+res[6].text+"');");
	$("congestionInfoTbody").style.display="";
	$("accidentInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
	
	var congestionres=new Array();	
	congestionres.push("congestionAlarmPersonInfo");
	congestionres.push("congestionAlarmBrachInfo");
	congestionres.push("congestionAlarmPhoneInfo");
	congestionres.push("congestionPersonNameInfo");
	congestionres.push("congestionPersonAddressInfo");
	congestionres.push("congestionAlarmPersonSexInfo");
	congestionres.push("congestionAlarmPersonAddressInfo");
	congestionres.push("congestionAlarmCarTypeInfo");
	congestionres.push("congestionAlarmCarCodeInfo");
	congestionres.push("congestionInjuredNumInfo");
	congestionres.push("congestionDiedNumInfo");
	congestionres.push("congestionIsBadCarInfo");
	congestionres.push("congestionAffectLevelInfo");
	congestionres.push("congestionRemarkInfo");
	congestionres.push("congestionEconomyLossInfo");
	congestionres.push("CongestionIsFeedbackEndInfo");
	
	for(var i=0;i<congestionres.length;i++){
		$(congestionres[i]).innerText=res[i+23].text;
	}
	

}

//显示故障车信息
function showExceptionCarInfo(res){	
//	$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" readonly>';
//	fillListBox("alarmLevelTd","alarmLevel","105","select id,name from t_attemper_code where id like '011%'","请选择","getLevel('"+res[6].text+"','"+res[7].text+"');");
	
	$("exceptionCarInfoTbody").style.display="";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
	
	var exceptionCarres=new Array();	
	exceptionCarres.push("exceptionCarAddressInfo");
	exceptionCarres.push("exceptionCauseInfo");
	exceptionCarres.push("exceptioCarShapeInfo");
	exceptionCarres.push("exceptionDriverNameInfo");
	exceptionCarres.push("exceptionCarUnitInfo");
	exceptionCarres.push("exceptioAffectLevelInfo");
	exceptionCarres.push("exceptionRemarkInfo");
	for(var i=0;i<exceptionCarres.length;i++){
		$(exceptionCarres[i]).innerText=res[i+23].text;
	}
		
}

//显示治安信息
function showPoliceEventInfo(res){	
//	$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" >';
//	fillListBox("alarmLevelTd","alarmLevel","105","select id,name from t_attemper_code where id like '011%'","请选择","getLevel('"+res[6].text+"','"+res[7].text+"');");
//	fillListBox("alarmLevelTd","alarmLevel","105","select id,name from t_attemper_code where id like '011%'","请选择","getLevel('"+res[6].text+"','"+res[7].text+"');");
	
	$("exceptionCarInfoTbody").style.display="none";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="";
	$("blackListInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
	
	var policeEventres=new Array();	
	policeEventres.push("PoliceEventAffectTrafficLevelInfo");
	policeEventres.push("PoliceEventAffectRoadInfo");
	policeEventres.push("PoliceEventRemarkInfo");
	policeEventres.push("PoliceEventTimeInfo");
	for(var i=0;i<policeEventres.length;i++){
		$(policeEventres[i]).innerText=res[i+23].text;
	}	
	
}

//显示恶劣天气
function showBadWeatherInfo(res){
	$("badWeatherInfoTbody").style.display="";
	$("exceptionCarInfoTbody").style.display="none";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
	
	var badWeatherres=new Array();
	badWeatherres.push("weathersituationInfo");
	badWeatherres.push("weatherRoadStatusInfo");
	badWeatherres.push("weatherAffectRoadInfo");
	badWeatherres.push("weatheAffectDeptInfo");
	badWeatherres.push("weatherAlarmPhoneInfo");
	badWeatherres.push("weatherAlarmPersonInfo");
	badWeatherres.push("weatherAffectextentInfo");
	badWeatherres.push("weatherRemarkInfo");
	for(var i=0;i<badWeatherres.length;i++){
		$(badWeatherres[i]).innerText=res[i+23].text;
	}	
}

//显示布控
function showBlackListInfo(res){
//	$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" readonly>';
//	fillListBox("alarmLevelTd","alarmLevel","105","select id,name from t_attemper_code where id like '011%'","请选择","getLevel('"+res[6].text+"','"+res[7].text+"');");
	
	$("blackListInfoTbody").style.display="";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
	
	var blackListres=new Array();	
	blackListres.push("blackListCarNumberInfo");
	blackListres.push("blackListCarSortInfo");
	blackListres.push("blackListRemarkInfo");
	for(var i=0;i<blackListres.length;i++){
		$(blackListres[i]).innerText=res[i+23].text;
	}
}

//显示市政
function showTownPlanInfo(res){	
	$("townPlanInfoTbody").style.display="";
	$("blackListInfoTbody").style.display="none";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
	$("fireAndBlastInfoTbody").style.display="none";
		
	var townPlanres=new Array();	
	townPlanres.push("townPlanHappenTimeInfo");
	townPlanres.push("townPlanPersonInfo");
	townPlanres.push("townPlanAffectRoadInfo");
	townPlanres.push("townPlanAffectExentInfo");
	townPlanres.push("townPlanLevelInfo");
	townPlanres.push("townPlanRemarkInfo");
	for(var i=0;i<townPlanres.length;i++){
		$(townPlanres[i]).innerText=res[i+23].text;
	}
}

//显示火灾
function showFireAndBlastInfo(res){
	$("fireAndBlastInfoTbody").style.display="";
	$("townPlanInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";
		
	var fireAndBlastRes=new Array();	
	fireAndBlastRes.push("fireTimeInfo");
	fireAndBlastRes.push("fireAffectLevelInfo");
	fireAndBlastRes.push("fireHaveCasualtyInfo");
	fireAndBlastRes.push("fireAlarmPersonInfo");
	fireAndBlastRes.push("fireRemarkInfo");
	for(var i=0;i<fireAndBlastRes.length;i++){
		$(fireAndBlastRes[i]).innerText=res[i+23].text;
	}
}

//显示地质
function showGeoLogicDisasterInfo(res){
	$("GeoLogicDisasterInfoTbody").style.display="";
	$("fireAndBlastInfoTbody").style.display="none";
	$("townPlanInfoTbody").style.display="none";
	$("blackListInfoTbody").style.display="none";
	$("accidentInfoTbody").style.display="none";
	$("congestionInfoTbody").style.display="none";
	$("exceptionCarInfoTbody").style.display="none";
	$("policeEventInfoTbody").style.display="none";
	$("badWeatherInfoTbody").style.display="none";	
		
	var GeoLogicDisasterRes=new Array();	
	GeoLogicDisasterRes.push("GeoLogicDisasterTimeInfo");
	GeoLogicDisasterRes.push("GeoLogicDisasterLevelInfo");
	GeoLogicDisasterRes.push("GeoLogicDisasterAffectExentInfo");
	GeoLogicDisasterRes.push("GeoLogicDisasterAlarmPersonInfo");
	GeoLogicDisasterRes.push("GeoLogicDisasterRemarkInfo");
	for(var i=0;i<GeoLogicDisasterRes.length;i++){
		$(GeoLogicDisasterRes[i]).innerText=res[i+23].text;
	}
}

//地图居中显示警情
function centerAlarm(x,y,aid,tag){
	
    	var mapDiv = map.getVMLDiv();
    	var model = map.getMapModel();
    	var currentZoom =  map.getCurrentZoom();
    	var zoom = toolbar.model.getZoom();
		var p=x+","+y;
		if(tag!="1"){
		//闪烁显示
			MapUtils.prototype.flashPoint(mapDiv, p.split(","),currentZoom,0);	
		}				
				
		var infoCoord = new Coordinate(x*1e16 , y*1e16);
		
		var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.		
		
//		map.moveToCenter(p,Number(getMapParameter(5)));
		
		var left = scoord.x;
		var top =  scoord.y;		
		var pointid ="accidentDiv_"+aid;
		
		amid=pointid;
		var pointSymbol = document.createElement("div");
		
		var realCoord = Util.getCoordinateByPixel(scoord, zoom);	
		var delDiv = document.getElementById(pointid);
		if(delDiv!=null){
			delDiv.parentNode.removeChild(delDiv);
		}
		//创建图形符号
		pointSymbol.id = pointid;
		pointSymbol.onselect = null;
		pointSymbol.style.position = "absolute";
		pointSymbol.style.border = "0px solid #999999";
		pointSymbol.style.fontSize = "12px";
		pointSymbol.style.padding = "1px";
		pointSymbol.style.zIndex = 11;
		
		pointSymbol.style.left = left + "px";
		pointSymbol.style.top = top + "px";	
		var img = Util.createImg("ps", 0, 0, 11, 11, "../../images/alarm/alarm.gif");
		pointSymbol.appendChild(img);
		pointSymbol.style.display = "";
		img.tag = pointid;
		img.style.cursor ="hand";
		img.position = realCoord;
		mapDiv.appendChild(pointSymbol);
		
}

function showPoint(){	
//	alert(deptcode)
	
	var x="";
	var y="";
	if($("alarmId").value!=null&&$("alarmId").value!=""){
		aid=$("alarmId").value;
		x=$("X").value;	
		y=$("Y").value;
	}else{		
		aid=$("alarmIdInfo").innerText;
		x=$("XInfo").value;	
		y=$("YInfo").value;
	}
	
//	alert("new:"+aid)
	centerAlarm(x,y,aid,"1");
}

function removeAllPoint()
{
	var delDiv = document.getElementById(amid);
	if(delDiv!=null){
		delDiv.parentNode.removeChild(delDiv);
	}
				
}

function doOnChange(){			
	var typeobj=$("alarmType");
	var alarmType ="";
	if(typeobj){
		alarmType = typeobj.value;	
	}	
	if(alarmType!=null&&alarmType!=""){	
		if (document.activeElement == typeobj)	{
			if(isAccident(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '006%'","请选择");
				fillListBox("alarmThinTypeTd","alarmThinType","111","select id,name from t_attemper_code where id like '007%'","请选择");
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("accidentTbody").style.display="";
				$("congestionTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("policeEventTbody").style.display="none";
				$("blackListTbody").style.display="none";			
				$("badWeatherTbody").style.display="none";
				$("townPlanTbody").style.display="none";
				$("fireAndBlastTbody").style.display="none";
				$("geoLogicDisasterTbody").style.display="none";	
			}
			else if(isCongestion(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '005%'","请选择");
				$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType" name="editinput" class="textwidth" readonly >';
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("congestionTbody").style.display="";
				$("accidentTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("policeEventTbody").style.display="none";
				$("blackListTbody").style.display="none";		
				$("badWeatherTbody").style.display="none";	
				$("townPlanTbody").style.display="none";
				$("fireAndBlastTbody").style.display="none";
				$("geoLogicDisasterTbody").style.display="none";	
			}
			else if(isExceptionCar(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" readonly>';
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("exceptionCarTbody").style.display="";
				$("accidentTbody").style.display="none";
				$("congestionTbody").style.display="none";
				$("policeEventTbody").style.display="none";
				$("blackListTbody").style.display="none";	
				$("badWeatherTbody").style.display="none";	
				$("townPlanTbody").style.display="none";	
				$("fireAndBlastTbody").style.display="none";
				$("geoLogicDisasterTbody").style.display="none";	
				
			}else if(isPoliceEvent(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" readonly>';
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("policeEventTbody").style.display="";
				$("exceptionCarTbody").style.display="none";
				$("accidentTbody").style.display="none";
				$("congestionTbody").style.display="none";	
				$("blackListTbody").style.display="none";	
				$("badWeatherTbody").style.display="none";		
				$("townPlanTbody").style.display="none";
				$("fireAndBlastTbody").style.display="none";
				$("geoLogicDisasterTbody").style.display="none";	
			}
 			else if(isWeather(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" readonly>';
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("badWeatherTbody").style.display="";
				$("blackListTbody").style.display="none";	
				$("policeEventTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("accidentTbody").style.display="none";
				$("congestionTbody").style.display="none";		
				$("townPlanTbody").style.display="none";	
				$("fireAndBlastTbody").style.display="none";
				$("geoLogicDisasterTbody").style.display="none";	
			}
			else if(isTownPlan(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				fillListBox("alarmThinTypeTd","alarmThinType","111","select id,name from t_attemper_code where id like '017%'","请选择");
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("townPlanTbody").style.display="";
				$("badWeatherTbody").style.display="none";
				$("blackListTbody").style.display="none";	
				$("policeEventTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("accidentTbody").style.display="none";
				$("congestionTbody").style.display="none";	
				$("fireAndBlastTbody").style.display="none";	
				$("geoLogicDisasterTbody").style.display="none";		
				
			}
			else if(isFireAndBlast(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				fillListBox("alarmThinTypeTd","alarmThinType","111","select id,name from t_attemper_code where id like '019%'","请选择");
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("fireAndBlastTbody").style.display="";
				$("townPlanTbody").style.display="none";
				$("badWeatherTbody").style.display="none";
				$("blackListTbody").style.display="none";	
				$("policeEventTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("accidentTbody").style.display="none";
				$("congestionTbody").style.display="none";	
				$("geoLogicDisasterTbody").style.display="none";	
			}
			else if(isGeoLogicDisaster(alarmType)){
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				fillListBox("alarmThinTypeTd","alarmThinType","111","select id,name from t_attemper_code where id like '016%'","请选择");
				$("signSite").style.display="";
				$("saveData").style.display="";
				
				$("geoLogicDisasterTbody").style.display="";	
				$("fireAndBlastTbody").style.display="none";
				$("townPlanTbody").style.display="none";
				$("badWeatherTbody").style.display="none";
				$("blackListTbody").style.display="none";	
				$("policeEventTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("accidentTbody").style.display="none";
				$("congestionTbody").style.display="none";					
			}
			else{
				fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
				$("alarmThinTypeTd").innerHTML='<input type="text" id="alarmThinType"  class="textwidth" readonly>';
				$("signSite").style.display="none";
				$("saveData").style.display="none";
				
				$("congestionTbody").style.display="none";
				$("accidentTbody").style.display="none";
				$("exceptionCarTbody").style.display="none";
				$("policeEventTbody").style.display="none";
				$("badWeatherTbody").style.display="none";
				$("blackListTbody").style.display="none";	
				$("townPlanTbody").style.display="none";
				$("fireAndBlastTbody").style.display="none";
				$("geoLogicDisasterTbody").style.display="none";	
			}	
		}		
	}	
	var newtypeobj=$("newalarmType");
	var newalarmType = "";	
	if(newtypeobj){
		newalarmType=newtypeobj.value;	
	}
	if(newalarmType!=null&&newalarmType!=""){	
		if (document.activeElement == newtypeobj)	{
			if(isAccident(newalarmType)){
				fillListBox("newalarmThinTypeTd","newalarmThinType","111","select id,name from t_attemper_code where id like '007%'","请选择");				
			}
			else{
				var html='<input type="text" id="newalarmThinType" class="textwidth" name="editinput" disabled="disabled">';
				$("newalarmThinTypeTd").innerHTML=html;
			}
		}
	}		
	
	var zydy=$("zydy");
	var zydyvalue = "";	
	if(zydy){
		zydyvalue=zydy.value;	
	}
	if(zydyvalue!=null&&zydyvalue!=""){	
		if (document.activeElement == zydy)	{
			$("disposeDesc").value=$("disposeDesc").value+zydy[zydy.selectedIndex].text+"\n";
		}
	}		
	
	var tydy=$("tydy");
	var tydyvalue = "";	
	if(tydy){
		tydyvalue=tydy.value;	
	}
	if(tydyvalue!=null&&tydyvalue!=""){	
		if (document.activeElement == tydy)	{
			$("disposeDesc").value=$("disposeDesc").value+tydy[tydy.selectedIndex].text+"\n";
		}
	}		
}


//判断事件类型是否是事故
function isAccident(type){
	if(type=="001001"){
		return true;
	}else{
		return false;
	}
}
//判断事件类型是否是拥堵
function isCongestion(type){
	if(type=="001002"){
		return true;
	}else{
		return false;
	}
}
//判断事件类型是否是故障车
function isExceptionCar(type){
	if(type=="001008"){
		return true;
	}else{
		return false;
	}
}

//判断事件类型是否是天气信息
function isWeather(type){
	if(type=="001006"){
		return true;
	}else{
		return false;
	}
}


//判断事件类型是否是煤气、热力泄露、自来水跑水停电等信息
function isTownPlan(type){
	if(type=="001011"){
		return true;
	}else{
		return false;
	}
}
//判断事件类型是否是公路地质灾害
function isGeoLogicDisaster(type){
	if(type=="001010"){
		return true;
	}else{
		return false;
	}
}
//判断事件类型是否是协查布控信息	
function isBlackList(type){
	if(type=="001005"){
		return true;
	}else{
		return false;
	}
}
//判断事件类型是否是治安事件
function isPoliceEvent(type){
	if(type=="001007"){
		return true;
	}else{
		return false;
	}
}
//判断事件类型是否是火灾、爆炸
function isFireAndBlast(type){
	if(type=="001012"){
		return true;
	}else{
		return false;
	}
}

function isNoType(type){
	if(type==""){
		return true;
	}else{
		return false;
	}
}


//得到子单列表
function getSonList(id){
	
	var url = "dispatch.policeEdit.getSonList.d";	
	params="id="+id;
	url = encodeURI(url);		
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getSonListRes});
}
function getSonListRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;
	
	var res=xmlDoc.getElementsByTagName("row");
	
	var tbody=$("sonListTbody");
	
	var trtitle=document.createElement("tr");
	trtitle.style.backgroundColor="#9abcff";
	var tdvalue=['选择','报警时间','事件来源','事件类型'];
	
	for(var i=0;i<tdvalue.length;i++){
		var tdtitle=document.createElement("td");
		tdtitle.className="td2";
		tdtitle.innerText=tdvalue[i];
		trtitle.appendChild(tdtitle);
	}
	tbody.appendChild(trtitle);
	
	for(var i=0;i<res.length;i++){
		var cols=res[i].childNodes;
		
		var tr=document.createElement("tr");
		tr.id="tr_"+cols[0].text;
		tr.index=(i+1);
		tr.onmouseover=function(){
			this.style.backgroundColor="silver";
		}
		tr.onmouseout=function(){
			this.style.backgroundColor="#FFFFFF";
		}		
		tr.onclick=function (){
			var id=this.id.split("_")[1];
			var chks=document.getElementsByName("chkname");			
			for(var n=0;n<chks.length;n++){
				var chk=chks[n];				
				if(chk.id==("chk_"+id)){
					chk.checked=true;
				}else{
					chk.checked=false;
				}
			}
			
			getAlarmInfoById(id);
		}
		
		var td0=document.createElement("td");
		var checkbox="<input type='checkbox' name='chkname' id='chk_"+cols[0].text+"' value='"+cols[0].text+"' />";
		
		td0.className="td2";
		td0.innerHTML=checkbox;
		tr.appendChild(td0);
		for(var j=1;j<cols.length-2;j++){
			var td=document.createElement("td");
			td.className="td2"
			td.innerText=cols[j].text;
			tr.appendChild(td);
		}	
		
		tbody.appendChild(tr);	
	}
		
}



function chkPerson(method,top,left){
	var div=document.createElement("div");
	div.style.textAlign="center";
	var radioStr="<input type=radio name=personradio id=dutyPerson onclick=getDutyPersonList(); checked>&nbsp;值勤人员&nbsp;<input type=radio name=personradio id=pcsPerson onclick=getPcsPersonList();>&nbsp;机构人员"
	div.innerHTML=radioStr;
	var personDiv=document.createElement("div");
	personDiv.id="personDiv";
	
	div.appendChild(personDiv);
	openDivWindow(div,top,left,256,330,method+'closeDivWindow();','closeDivWindow();','请选择人员');
	
	getDutyPersonList();
}

function getDutyPersonList(){
	
	var url = "dispatch.policeEdit.getDutyPerosn.d";
	url = encodeURI(url);
	var deptId=deptcode;
	var params = {};
	params["deptId"] = deptId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getDutyPersonListRes})
}
function getDutyPersonListRes(xmlHttp){	
	$("personDiv").innerHTML="";
	
	var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("row");
	var table=document.createElement("table");	
	table.className="list_table";
	table.style.width="100%";
	table.style.backgroundColor="#DFEAF7";
	var tbody=document.createElement("tbody");	
	
	var titles=["选择","姓名","执勤岗位"];
	var widths=["20%","35%","43%"];
	
	var trtitle=document.createElement("tr");
	for(var i=0;i<titles.length;i++){
		var th=document.createElement("td");
		th.setAttribute("className","list_thead");
		th.style.width=widths[i];
		th.innerText=titles[i];
		trtitle.appendChild(th);
	}
	tbody.appendChild(trtitle);
	
	for(var i=0;i<res.length;i++){
		var tds=res[i].childNodes;
		var tr=document.createElement("tr");
		var tdcheck=document.createElement("td");
		tdcheck.setAttribute("className","list_td");
		var check="<input type='checkbox' id='' value='"+tds[0].text+"' xm='"+tds[1].text+"' name='personListchk'>";
		tdcheck.innerHTML=check;
		tr.appendChild(tdcheck);
		
		for(var j=1;j<tds.length;j++){
			var td=document.createElement("td");
			td.setAttribute("className","list_td");
			td.innerText=tds[j].text;
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}

	table.appendChild(tbody);
	
	$("personDiv").appendChild(table);
	//openDivWindow(table,180,560,206,330,'','','请选择人员');
}	


function getPcsPersonList(){
	
	var url = "dispatch.policeEdit.getPcsPerosn.d";
	url = encodeURI(url);
	var deptId=deptcode;
	var params = {};
	params["deptId"] = deptId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getPcsPersonListRes})
}
function getPcsPersonListRes(xmlHttp){	
	$("personDiv").innerHTML="";
	
	var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("row");
	var table=document.createElement("table");	
	table.className="list_table";
	table.style.width="100%";
	table.style.backgroundColor="#DFEAF7";
	var tbody=document.createElement("tbody");	
	
	var titles=["选择","姓名","所属机构"];
	var widths=["20%","35%","43%"];
	
	var trtitle=document.createElement("tr");
	for(var i=0;i<titles.length;i++){
		var th=document.createElement("td");
		th.setAttribute("className","list_thead");
		th.style.width=widths[i];
		th.innerText=titles[i];
		trtitle.appendChild(th);
	}
	tbody.appendChild(trtitle);
	
	for(var i=0;i<res.length;i++){
		var tds=res[i].childNodes;
		var tr=document.createElement("tr");
		var tdcheck=document.createElement("td");
		tdcheck.setAttribute("className","list_td");
		var check="<input type='checkbox' id='' value='"+tds[0].text+"' xm='"+tds[1].text+"' name='personListchk'>";
		tdcheck.innerHTML=check;
		tr.appendChild(tdcheck);
		
		for(var j=1;j<tds.length;j++){
			var td=document.createElement("td");
			td.setAttribute("className","list_td");
			td.innerText=tds[j].text;
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}

	table.appendChild(tbody);
	
	$("personDiv").appendChild(table);
	//openDivWindow(table,180,560,206,330,'','','请选择人员');
}


function okAddPersonClick(flag){
	var n=0;
	var xmStr="";
	var chks=document.getElementsByName("personListchk");
	for(var i=0;i<chks.length;i++){
		if(chks[i].checked){
			xmStr+=chks[i].xm+";";
			n++;
		}
	}
	if(flag=="complete"){
		$("complecjr").value=xmStr;
		$("complecjrs").value=n;
	}else{
		$("cjr").value=xmStr;
		$("cjrs").value=n;
	}
}


function okDisposePersonClick(){
	var xmStr="";
	var chks=document.getElementsByName("personListchk");
	for(var i=0;i<chks.length;i++){
		if(chks[i].checked){
			xmStr+=chks[i].xm+";";
		}
	}
	$("disposePerson").value=xmStr;
}


function okCarClick(flag){
	var xmStr="";
	var chks=document.getElementsByName("carListchk");
	for(var i=0;i<chks.length;i++){
		if(chks[i].checked){
			xmStr+=chks[i].xm+";";
		}
	}
	if(flag=="comple"){
		$("complecdcl").value=xmStr;
	}else{
		$("cdcl").value=xmStr;
	}
}

function chkCar(flag){
	var div=document.createElement("div");
	div.style.textAlign="center";
	var radioStr="<input type=radio name=carradio id=dutyCar onclick=getDutyCarList(); checked>&nbsp;值勤车辆&nbsp;<input type=radio name=carradio id=pcsCar onclick=getPcsCarList();>&nbsp;机构车辆"
	div.innerHTML=radioStr;
	var carDiv=document.createElement("div");
	carDiv.id="carDiv";
	
	div.appendChild(carDiv);
	openDivWindow(div,180,250,256,330,"okCarClick('"+flag+"');closeDivWindow();","closeDivWindow();","请选择车辆");
	
	getDutyCarList();
}


function getDutyCarList(){
	
	var url = "dispatch.policeEdit.getDutyCar.d";
	url = encodeURI(url);
	var deptId=deptcode;
	var params = {};
	params["deptId"] = deptId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getDutyCarListRes})
}
function getDutyCarListRes(xmlHttp){	
	$("carDiv").innerHTML="";
	
	var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("row");
	var table=document.createElement("table");	
	table.className="list_table";
	table.style.width="100%";
	table.style.backgroundColor="#DFEAF7";
	var tbody=document.createElement("tbody");	
	
	var titles=["选择","车牌"];
	var widths=["20%","78%"];
	
	var trtitle=document.createElement("tr");
	for(var i=0;i<titles.length;i++){
		var th=document.createElement("td");
		th.setAttribute("className","list_thead");
		th.style.width=widths[i];
		th.innerText=titles[i];
		trtitle.appendChild(th);
	}
	tbody.appendChild(trtitle);
	
	for(var i=0;i<res.length;i++){
		var tds=res[i].childNodes;
		var tr=document.createElement("tr");
		var tdcheck=document.createElement("td");
		tdcheck.setAttribute("className","list_td");
		var check="<input type='checkbox' id='' value='"+tds[0].text+"' xm='"+tds[1].text+"' name='carListchk'>";
		tdcheck.innerHTML=check;
		tr.appendChild(tdcheck);
		
		for(var j=1;j<tds.length;j++){
			var td=document.createElement("td");
			td.setAttribute("className","list_td");
			td.innerText=tds[j].text;
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}

	table.appendChild(tbody);
	
	$("carDiv").appendChild(table);
	//openDivWindow(table,180,560,206,330,'','','请选择人员');
}	


function getPcsCarList(){
	
	var url = "dispatch.policeEdit.getPcsCar.d";
	url = encodeURI(url);
	var deptId=deptcode;
	var params = {};
	params["deptId"] = deptId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getPcsCarListRes})
}
function getPcsCarListRes(xmlHttp){	
	$("carDiv").innerHTML="";
	
	var xmlDoc = xmlHttp.responseXML;	
	var res=xmlDoc.getElementsByTagName("row");
	var table=document.createElement("table");	
	table.className="list_table";
	table.style.width="100%";
	table.style.backgroundColor="#DFEAF7";
	var tbody=document.createElement("tbody");	
	
	var titles=["选择","车牌"];
	var widths=["20%","78%"];
	
	var trtitle=document.createElement("tr");
	for(var i=0;i<titles.length;i++){
		var th=document.createElement("td");
		th.setAttribute("className","list_thead");
		th.style.width=widths[i];
		th.innerText=titles[i];
		trtitle.appendChild(th);
	}
	tbody.appendChild(trtitle);
	
	for(var i=0;i<res.length;i++){
		var tds=res[i].childNodes;
		var tr=document.createElement("tr");
		var tdcheck=document.createElement("td");
		tdcheck.setAttribute("className","list_td");
		var check="<input type='checkbox' id='' value='"+tds[0].text+"' xm='"+tds[1].text+"' name='carListchk'>";
		tdcheck.innerHTML=check;
		tr.appendChild(tdcheck);
		
		for(var j=1;j<tds.length;j++){
			var td=document.createElement("td");
			td.setAttribute("className","list_td");
			td.innerText=tds[j].text;
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}

	table.appendChild(tbody);
	
	$("carDiv").appendChild(table);
	//openDivWindow(table,180,560,206,330,'','','请选择人员');
}


function openDivWindow(obj,top,left,width,height,okmethod,canclemethod,title){
	var useDiv = document.createElement("div");
	useDiv.id="usedivs";
//	useDiv.style.backgroundColor="#DFEAF7";
	useDiv.style.width=width;
	useDiv.style.height=height;
	useDiv.style.textAlign="center";
	useDiv.style.zIndex = 1000;
	useDiv.style.position="absolute";
 	if(top==null||top==""){
 		top=0;
 	}
 	if(left==null||left==""){
 		left=0;
 	}
 	useDiv.style.top=top;
 	useDiv.style.left=left; 	
 	 	
 	useDiv.innerHTML='\
           <div >\
           <table id="poptable" width="100%" height="100%" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
            <tbody>\
              <tr height="5%" class="scrollColThead">\
                <td class="corner" id="topleft"></td>\
                <td class="top">' + title + '</td>\
                <td class="top" align="right" valign="middle">\
                  <img src="../../../sm/image/popup/ok.gif" border="0" style="cursor:pointer" onclick="' + okmethod + '">\
                  <img src="../../../sm/image/popup/cancel.gif" border="0" style="cursor:pointer" onclick="' + canclemethod + '">\
                </td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr height="92%" valign="top">\
                <td class="left"></td>\
                <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                   <div style="weight:180;height:298;overflow-y:scroll;" id="tdcontain"></div>\
                </td>\
                <td class="right"></td>\
              </tr>\
              <tr height="3%">\
                <td id="bottomleft" class="corner"></td>\
                <td class="bottom" colspan="2"></td>\
                <td id="bottomright" class="corner"></td>\
              </tr>\
            </tbody>\
            </table>\
          </div>';	
	document.body.appendChild(useDiv);
	$("tdcontain").style.scrollbarBaseColor="#DFEAF7";
	$("tdcontain").appendChild(obj);	
}	


function closeDivWindow(){
	var divPopup = document.getElementById("useDivs");
	if((typeof(divPopup) != "undefined") && (divPopup != null)){
    	divPopup.parentNode.removeChild(divPopup);
    }
}

function getDictInfo(){
	var url = "dispatch.policeEdit.getDictInfo.d";	
	params="id="+id;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getDictInfoRes});
}
function getDictInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;
	
	var res=xmlDoc.getElementsByTagName("row");
}


/** 
 * desc:控制千米输入范围.
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

