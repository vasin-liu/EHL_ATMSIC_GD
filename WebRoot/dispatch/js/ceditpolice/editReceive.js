/**
 *author:wangxt
 *desc:编辑接报信息
 *date:2010-1-11
*/
var updatestate ="0";

function savaReceive(){
    if($("receive_time").value=="") {
       alert("填报时间不能为空！");
       $("receive_time").focus;
       return;
    }
     if($("duty_person").value=="") {
       alert("值班人员不能为空！");
       $("duty_person").focus;
       return;
    }
     if($("pass_person").value=="") {
       alert("上报批准人不能为空！");
       $("pass_person").focus;
       return;
    }
    if($("infoVerify_Accident").value=="") {
       alert("信息核实情况不能为空！");
       $("infoVerify_Accident").focus;
       return;
    }
    if($("reportHead_Accident").value=="") {
       alert("向领导汇报情况不能为空！");
       ("reportHead_Accident").focus;
       return;
    }
    var params ={};
    params["feedbackid"]  = feedbackid ;
	params["alarmid"] = alarmid;
	params["deptname"] = uname;
	params["receivetype"] = receivetype;
    params["receive_time"] = $("receive_time").value;
	params["duty_person"] = $("duty_person").value ;
	params["pass_person"] = $('pass_person').value;
	params["infoVerify_Accident"]  = $("infoVerify_Accident").value ;
	params["reportHead_Accident"]  = $("reportHead_Accident").value ;
	//
	params["depttype"] = depttype1;
	params["updatestate"] = "1";
	var url = "dispatch.creportEdit.addReceiveReport.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editReceiveReportRes});
}

function editReceiveReportRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("审核成功!");
		window.close();
		return;
	}else{
		alert("审核失败!");
		return;
	}
}
/**
  初始化页面信息
*/
function initData() {
    $("receive_time").value = nowdate;
    //更改状态 已接受
    updatestate = "0";
    //updateEventState();
    if(alarmid!='') {
       loadReport(alarmid)
    }
    return;
}
function loadReport(flowid) {
        var url = "dispatch.creportEdit.getReceiveReport.d";	
		url = encodeURI(url);
		var params = "flowid="+flowid;
		params = encodeURI(params);
		new Ajax.Request(url, {method:"post", parameters:params,onComplete:getReceiveReportRes})
}

function getReceiveReportRes(xmlHttp) {
    var xmldoc   = xmlHttp.responseXML;
	var rows = xmldoc.getElementsByTagName("row");
    var results = rows[0].childNodes;
    if(results.length>0){
		$("receive_time").value = results[4].text.substring(0,results[4].text.length-2);
		$("duty_person").value = results[5].text;
		$('pass_person').value = results[6].text;
		$("infoVerify_Accident").value = results[7].text;
		$("reportHead_Accident").value = results[8].text;
	}
}

function updateEventState() {
    var url = "dispatch.creportEdit.getReceiveReport.d";	
	url = encodeURI(url);
	//var params = "flowid="+flowid;
	//params = encodeURI(params);
	var params ={};
	params["feedbackid"]  = feedbackid ;
	params["alarmid"] = alarmid;
	params["depttype"] = depttype;
	params["updatestate"] = updatestate;
	var url = "dispatch.creportEdit.initReceiveReport.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editReceiveReportRes})
     
}

function updateEventStateRes(xmlHttp) {
    var xmlDoc = xmlHttp.responseText;	
    if(xmlDoc=="success"){
	    
	}else{
	
	}
}

function revalues() {
   $("receive_time").value="";
   $("duty_person").value="";
   $("pass_person").value="";
   $("infoVerify_Accident").value="";
   $("reportHead_Accident").value="";
}






