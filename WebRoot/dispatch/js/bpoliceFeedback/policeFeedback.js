/**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：警情处理实现
    * 作者：ldq
    * 日期: 2009-04-13
    */
//处警单号
var strAlarmId;
//报警状态
function getDataByTime(){
	var timeName=35;
	clearInterval(timeName); 
//	timeName = setInterval("getNewProject()", 8*1000);
	timeName = setInterval("getArriveTime()", 35*1000);
}

function getArriveTime(){
	var chks=document.getElementsByName("feedBackListCheckbox");
	var feedbackId="";
	for(var n=0;n<chks.length;n++){
		var chk=chks[n];				
		if(chk.checked==true){
//			alert(chk.value);
			feedbackId=chk.value;
			break;
		}					
			
	}
	var params = encodeURI("feedbackId="+feedbackId);
	var url = "dispatch.policeEdit.getArriveTime.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getArriveTimeRes});
}
function getArriveTimeRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	
	if(res[0].text==""||res[0].text==null){
		return;
	}
	
	var eventtype=res[1].text;
	switch(eventtype){
		case "001001":
			$("djsj").value=res[0].text;
			break;			
		case "001002":
			$("djsj").value=res[0].text;
			break;	
		case policeEvent[1]:
			
			break;
		case policeEvent[2]:
			$("weatherdjsj").value=res[0].text;
			break;
		case policeEvent[3]:
			$("policedjsj").value=res[0].text;
			break;
		case policeEvent[4]:
			$("exceptioncardjsj").value=res[0].text;
			break;
		case policeEvent[5]:
			$("geologicdjsj").value=res[0].text;
			break;	
		case policeEvent[6]:
			$("townplandjsj").value=res[0].text;
			break;	
		case policeEvent[7]:
			$("firedjsj").value=res[0].text;
			break;	
					
	}
}




var strState;
//报警类别
var strType;
var policeEvent = new Array(9);
	policeEvent[0] = "001001,001002";
	policeEvent[1] = "001005";
	policeEvent[2] = "001006";
	policeEvent[3] = "001007";
	policeEvent[4] = "001008";
	policeEvent[5] = "001010";
	policeEvent[6] = "001011";
	policeEvent[7] = "001012";
	policeEvent[8] = "noEventType";

function initPages_FeedBack(deptname,pname){
	//添加行事件
	watching.setRowClickHandler(initFeedBack);
}

/** 
    * desc:  回馈警情初始化。

    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */
function initFeedBack (alarmId){
//	strAlarmId = alarmId;
	getDataByTime();
	var state = null;
	var data = watching.m_watchingData['affairData'];
	for(var i=0;i< data.length;i+=1) {
		if(data[i].alarmId == alarmId) {
			$('btnAffairProcess').strState = data[i].eventState_code;
			$('btnAffairProcess').strType = data[i].eventType_code;
			$('btnAffairProcess').strAlarmId = alarmId;
			 
//			alert(strType);
			break;	
		}
	}
}   

/** 
    * desc:   取一条报警时间，打开报警处理页面回馈警情。
    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */
//function feedBack (){
//	if(! $('btnAffairProcess').strAlarmId ){
//		alert("请单击选择需处理警情！");
//		return null;
//	}
//	var url = "../policeFeedback/feedbackEdit.jsp?alarmId=" + strAlarmId +"&type="+strType;
//	url = encodeURI(url);
//	var top=(screen.availHeight-600)/2;		
//	var left=(screen.availWidth-1000)/2;
//	var feedBackPage = window.open(url,"edit","width=1000;heigth=600,top="+top+",left="+left);
//}
/** 
    * desc:   初始化回馈警情页面。
    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */
function initFeedBackPage(){
	var strState = $('btnAffairProcess').strState ;
	if(strState == "004013" || strState == "004011"){
		var outbtn=document.getElementsByName("feedBackForOut_btn");
		var arraybtn=document.getElementsByName("feedBackForArray_btn");
		var finishbtn=document.getElementsByName("feedBackForFinish_btn");
		$("addComout").style.display="none";
		$("addFinish").style.display="none";
		
		for(var i=0;i<outbtn.length;i++){
			outbtn[i].style.display="none";
			arraybtn[i].style.display="none";
			finishbtn[i].style.display="none";
		}		
		$("trclwbbtn").style.display="none";	
		$("trddxcbtn").style.display="none";
	}else{
		$("addComout").style.display="";
		$("addFinish").style.display="";
		$("trclwbbtn").style.display="none";	
		$("trddxcbtn").style.display="";
	}
	var strType = $('btnAffairProcess').strType;
	var alarmId = $('btnAffairProcess').strAlarmId;
	checkFeedBack(strType);//初始化页面
	//reset("fedBackGrid");
	getDataByCondition(alarmId,strType,"");
	if(strType == '001001'){
		fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='006' ORDER BY ID","未选择","");
	}else if(strType == '001002'){
		fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='005' ORDER BY ID","未选择","");
	}else{
		fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='011' ORDER BY ID","未选择","");
	}
	
	contorlBtn();
	var url = "dispatch.feedBack.getEventListById.d";	
	url = encodeURI(url);
	var params = {};
	params["alarmId"] = alarmId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:initPageResponse});
}
function initPageResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var res=xmlDoc.getElementsByTagName("feedback");
	createFeedBackList(res);
}

//建立反馈记录列表
function createFeedBackList(res){
	var strState = $('btnAffairProcess').strState ;
	var strType = $('btnAffairProcess').strType;
	var alarmId = $('btnAffairProcess').strAlarmId;
	
	var table=document.createElement("table");
	$("tableList").innerHTML="";
	table.setAttribute("className","table2");
	table.style.width="100%";
	//table.style.height="5";
	table.style.overflowY="scroll";
	var tbody=document.createElement("tbody");	
	var title=["选 择","出警单位","出警人","出警时间","到警时间","状态","状态编码"];
	var widths=["6%","21%","25%","18%","18%","10%","0"];
	var trHead=document.createElement("tr");
	trHead.setAttribute("id","trHead");
	trHead.style.backgroundColor="#9abcff";
	for(var i=0;i<title.length;i++){
		var tdTitle=document.createElement("td");
		tdTitle.innerText=title[i];
		tdTitle.setAttribute("className","td2");
		tdTitle.style.width=widths[i];
		if(i==title.length-1){
			tdTitle.style.display="none";
		}
		trHead.appendChild(tdTitle);		
		
	}
	tbody.appendChild(trHead);
	
	
  	
	
	if(res.length==0){		
		getDefaultInfo();
		$("outTr").style.display="";
		$("outCarTr").style.display=""
		
		var ary=getFinishBtns();
		for(var i=0;i<ary.length;i++){
			ary[i].style.display="none";
		}
	}else{
		$("feedbackInfo").style.display="none";
		
		var ary=getFinishBtns();
		for(var i=0;i<ary.length;i++){
			ary[i].style.display="";
		}
	}
	for(var i=0;i<res.length;i++){
		var tr=document.createElement("tr");		
		tr.setAttribute("id","tr_"+i);
		var objs=res[i].childNodes;
		var td0=document.createElement("td");
		td0.setAttribute("id",i+"_td_check");
		td0.setAttribute("className","td2");
//		var checkbox=document.createElement("input");
//		checkbox.type="checkbox";
//		checkbox.setAttribute("value",objs[0].text);
//		checkbox.setAttribute("id","feedBackListChk_"+i);
//		checkbox.name="feedBackListCheckbox";
		var checkbox="<input type='checkbox' name='feedBackListCheckbox' id='feedBackListChk_"+i+"' value='"+objs[0].text+"' />";
	
		td0.innerHTML=checkbox;
		tr.appendChild(td0);
		
		
			
		
		
		tr.onclick=function(){
			
			var ary=getFinishBtns();
			for(var i=0;i<ary.length;i++){
				ary[i].style.display="";
			}
			
			$("feedbackInfo").style.display="";
			checkFeedBack(strType);
//			alert(this.childNodes[6].innerText)
			if(strType == '001001'){
		       fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='006' ORDER BY ID","未选择","");
	        }else if(strType == '001002'){
		      fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='005' ORDER BY ID","未选择","");
	       }else{
		      fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='011' ORDER BY ID","未选择","");
	       }
			var tag=this.id.split("_")[1];
			var chks=document.getElementsByName("feedBackListCheckbox");
			for(var n=0;n<chks.length;n++){
				var chk=chks[n];				
				if(chk.id==("feedBackListChk_"+tag)){
					chk.checked=true;					
					getDataByCondition(alarmId,strType,chk.value);
				}else{
					chk.checked=false;
				}
			}
		}
		
		for(var j=2;j<objs.length-2;j++){
			var td=document.createElement("td");
			td.setAttribute("id",i+"_td_"+j);
			td.innerText=objs[j].text;
			td.setAttribute("className","td2");
			if(j==objs.length-3){
				td.style.display="none";
			}
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	
	table.appendChild(tbody);
	$("tableList").appendChild(table);
	//alert("TABLE="+$("tableList").innerHTML);
	
	if(res.length>0){
//		$("feedbackInfo").style.display="";
//		getDataByCondition(alarmId,strType,res[i].childNodes[0]);

		$("feedbackInfo").style.display="";
		checkFeedBack(strType);
		
//			alert(this.childNodes[6].innerText)
		if(strType == '001001'){
	       fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='006' ORDER BY ID","未选择","");
        }else if(strType == '001002'){
	      fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='005' ORDER BY ID","未选择","");
       }else{
	      fillListBox("sgdj_td","sgdj","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='011' ORDER BY ID","未选择","");
       }
		var tag="0";
		var chks=document.getElementsByName("feedBackListCheckbox");
		for(var n=0;n<chks.length;n++){
			var chk=chks[n];				
			if(chk.id==("feedBackListChk_"+tag)){
				chk.checked=true;					
				getDataByCondition(alarmId,strType,res[0].childNodes[0].text);
			}else{
				chk.checked=false;
			}
		}		
	}
}


function contorlBtn(){
	var outbtn=document.getElementsByName("feedBackForOut_btn");
	var arraybtn=document.getElementsByName("feedBackForArray_btn");
	var finishbtn=document.getElementsByName("feedBackForFinish_btn");
	var ary=getFinishBtns();	
	$("addFinish").onclick=function(){
		$("FEEDBACKID").value = "";
		$("feedbackInfo").style.display="";		
		$("outTr").style.display="none";
		$("outCarTr").style.display="none";
		$("trddxcbtn").style.display="none";
		$("trclwbbtn").style.display="";
		getDefaultInfo("finish");
		for(var i=0;i<ary.length;i++){
			ary[i].style.display="";
		}
		for(var i=0;i<outbtn.length;i++){			
			finishbtn[i].style.display="";			
			outbtn[i].style.display="none";
			arraybtn[i].style.display="none";			
		}		
	}
	$("addComout").onclick=function(){
		$("outTr").style.display="";
		$("outCarTr").style.display="";
		$("trclwbbtn").style.display="none";
		$("trddxcbtn").style.display="";
		getDefaultInfo();
			
		for(var i=0;i<ary.length;i++){
			ary[i].style.display="none";
		}
		
		for(var i=0;i<outbtn.length;i++){
			outbtn[i].style.display="";
			arraybtn[i].style.display="";
			finishbtn[i].style.display="none";
		}
		$("FEEDBACKID").value = "";
	}
	//$("clwbbtn").onclick=function() {
		//$("addComout").style.display= "none";
	    //$("addFinish").style.display= "none";
	//}
}

//根据反馈信息的所有信息
function getDataByCondition(alarmId,strType,feedbackid){
	var url = "dispatch.feedBack.getEventById.d";	
	url = encodeURI(url);
	var params = {};
	$("FEEDBACKID").value=feedbackid;
	params["FEEDBACKID"]=feedbackid;
	params["eventType"]=strType;
	params["alarmId"] = alarmId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getDataByConditionResponse});
}
function getDataByConditionResponse(xmlHttp){
	var strState = $('btnAffairProcess').strState ;
	var xmlDoc = xmlHttp.responseXML;
	var res=xmlDoc.getElementsByTagName("col");
	for(var i=0;i<res.length;i++){
		if(i==9) {
			//alert("事件类型="+strType);
			if($('btnAffairProcess').strType==001001) {
				//alert("djsj="+res[i].text);
				$("djsj").value=res[i].text;
			}
			if($('btnAffairProcess').strType==001002) {
				$("djsj").value=res[i].text;
			}
			if($('btnAffairProcess').strType==001006) {
				//alert("weatherdjsj="+res[i].text);
				$("weatherdjsj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType==001007) {
				
				$("policedjsj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType==001008) {
				$("exceptioncardjsj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType=="001010") {
				//alert("地质灾害到警时间="+$("geologicdjsj").value);
				$("geologicdjsj").value=res[i].text;
				//alert("地质灾害到警时间="+res[i].text);
				
			}
			if($('btnAffairProcess').strType==001011) {
				$("townplandjsj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType==001012) {
				$("firedjsj").value=res[i].text;
				
			}
			
		}
		if(i==1) {
			//alert("事件类型="+$('btnAffairProcess').strType);
			if($('btnAffairProcess').strType==001001) {
				//alert("djsj="+res[i].text);
				$("ajjssj").value=res[i].text;
				//alert(""+$("ajjssj").value);
			}
			if($('btnAffairProcess').strType==001002) {
				$("ajjssj").value=res[i].text;
			}
			if($('btnAffairProcess').strType==001006) {
				//alert("weatherdjsj="+res[i].text);
				$("weatherajjssj").value=res[i].text;
				alert(""+$("weatherajjssj").value);
				
			}
			if($('btnAffairProcess').strType==001007) {
				
				$("policeajjssj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType==001008) {
				$("exceptioncarajjssj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType=="001010") {
				//alert("地质灾害到警时间="+$("geologicdjsj").value);
				$("geologicajjssj").value=res[i].text;
				//alert("地质灾害到警时间="+res[i].text);
				
			}
			if($('btnAffairProcess').strType==001011) {
				$("townplanajjssj").value=res[i].text;
				
			}
			if($('btnAffairProcess').strType==001012) {
				$("fireajjssj").value=res[i].text;
				
			}
			
		}
		
		
		if($(res[i].getAttribute("id"))){
			$(res[i].getAttribute("id")).value=res[i].text;
		}
		
	}
	//checkFeedBack(strType);
	var outbtn=document.getElementsByName("feedBackForOut_btn");
	var arraybtn=document.getElementsByName("feedBackForArray_btn");
	var finishbtn=document.getElementsByName("feedBackForFinish_btn");
	if(strState=="004013"||strState=="004011"){
		for(var i=0;i<outbtn.length;i++){
			outbtn[i].style.display="none";
			arraybtn[i].style.display="none";
			finishbtn[i].style.display="none";
		}
	}else{	
		if(res[6].text=="004006"){		
			for(var i=0;i<outbtn.length;i++){
				outbtn[i].style.display="none";
				arraybtn[i].style.display="";
				finishbtn[i].style.display="none";
			}
		}
		if(res[6].text=="004014"){
			for(var i=0;i<outbtn.length;i++){
				outbtn[i].style.display="none";
				arraybtn[i].style.display="none";
				finishbtn[i].style.display="none";
			}
		}
		if(res[6].text=="004013"){
			for(var i=0;i<outbtn.length;i++){
				outbtn[i].style.display="none";
				arraybtn[i].style.display="none";
				finishbtn[i].style.display="none";
			}
		}
	}
}

//
function getDefaultInfo(state){
	var url = "dispatch.policeEdit.getNewInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var res=xmlDoc.getElementsByTagName("col");
	$("feedbackInfo").style.display="";				
	var outbtn=document.getElementsByName("feedBackForOut_btn");
	var arraybtn=document.getElementsByName("feedBackForArray_btn");
	var finishbtn=document.getElementsByName("feedBackForFinish_btn");
	if(state!="finish"){
		for(var i=0;i<outbtn.length;i++){
			outbtn[i].style.display="";
			arraybtn[i].style.display="";
			finishbtn[i].style.display="none";
		}
	}else{
		for(var i=0;i<outbtn.length;i++){
			outbtn[i].style.display="none";
			arraybtn[i].style.display="none";
			finishbtn[i].style.display="";
		}
	}
	
	
	$("fkdw").value=res[2].text;
	$("fkdw").deptid=res[3].text;
	$("fkr").value=pname;	
	$("cjdw").value=res[2].text;
	$("cjdw").deptid=res[3].text;
	$("cjsj").value=res[1].text;
	
	//$("djsj").value="";
	$("cjr").value="";
	//$("ajjssj").value="";
	$("cdcl").value="";
	$("cjrs").value="";
	$("cljg").value="";	
	
}
});
}

//根据事件类型调用不同的事件方法 add by wangxt
function eventtype(feedType) {
	var strType = $('btnAffairProcess').strType;
	switch(strType) {
		case '001001':
		   //交通事故
		   modifyAccident(feedType);
		   break;
		case '001002':
		   // 交通拥堵
		   modifyAccident(feedType);
		   break;
		case '001005':
		   //嫌疑车辆
		  // modifyExceptionCar(feedType);
		case '001006':
		   //灾害天气
		   modifyWeather(feedType);  
		   break; 
		case '001007':
		   //治安事件
		   modifyPoliceEvent(feedType); 
		   break;
		case '001008':
		   //大型车故障
		   modifyExceptionCar(feedType); 
		   break;
		case '001010':
		   //地质灾害
		   modifyGeoLogicDisaster(feedType); 
		   break;
		case '001011':
		   //市政事件
		   modifyTownPlan(feedType);    
		   break;
		case '001012':
		   //火灾爆炸
		   modifyFireAndBlast(feedType);    
		   break;                  
	}
}

//根据事件类型控制tbody隐藏显示
function checkFeedBack(strType){
	if($("ajlx_feedBack") != null){
		$("ajlx_feedBack").disabled = true;
	}
	if(strType != ""){
		for(var i = 0;i < policeEvent.length;i++){
			
			if(policeEvent[i].indexOf(strType) >= 0){
				if($(policeEvent[i]) != null){
					$(policeEvent[i]).style.display="";
				}
			}else{
				if($(policeEvent[i]) != null){
					$(policeEvent[i]).style.display="none";
					//$(policeEvent[i]).style.display="";
				}
			}
		}
	}else{
//		alert("根据事件类型控制tbody隐藏显示<" + strType + ">");
		for(var i = 0;i < (policeEvent.length-1);i++){
			if($(policeEvent[i]) != null){
				$(policeEvent[i]).style.display="none";
			}
		}
		$(policeEvent[8]).style.display="";
//		alert($(policeEvent[8]).style.display);
//		alert($(policeEvent[8]).innerHTML);
	}
}

//
function displayBtn(){
	
}
/** 
 * desc:控制键盘输入:回车换行.
 * date:2009-02-16
 */
function keyDown(){
	if(event.keyCode==13)event.keyCode=9;
}

/** 
 * desc:控制键盘输入数字.
 * date:2009-02-16
 */
function keyPress(){
	return true;//暂时要求人数等以字符串处理
	if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false;
}
/** 
 * desc:控制数字输入范围.
 * date:2009-02-16
 */
function isValidate(obj,select){
	return true;//暂时要求人数等以字符串处理

	var inputValue = parseInt(obj.value);
	if(!checkMath(obj.value)){
			alert("请输入数字！");
			obj.value="";
		    obj.focus();
			return;
	}
	if (inputValue > 99 && select != "jjss"){
		if(confirm("输入值大于预期，确认继续？")) {
			
		}else{
			obj.value="";
		    obj.focus();
		}
	}
}
