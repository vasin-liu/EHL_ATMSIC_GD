/**
    * Copyright (c) 2009, EHL Technologies, Inc.
    * All right reserved.
    * 说明：地址灾害警情处理
    * 作者：ldq
    * 日期: 2009-04-13
    */
/** 
    * desc:  地质灾害事件警情处理实现
    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */    
var strFeedType;
var EVENTSTATE = "";
function modifyBlackList(feedType){
	  
	strFeedType = feedType;
//	alert("test-->:"+strFeedType);
	var FEEDBACKID = $("FEEDBACKID");
//	var fkdw = $("fkdw");
	var fkr = $("fkr");
//	var cjdw = $("cjdw");
	var cjr = $("cjr");
	var cjsj = $("cjsj");
	var djsj = $("djsj");
	var jdhb = $("jdhb");
	  
//	var ALARMID = $("ALARMID");
	var ajfssj = $("ajfssj");   
	var ajjssj = $("ajjssj");   
	var ajlx = $("ajlx_feedBack");     
//	 alarmSBlackList
	var cdcl = $("cdcl");      
	var cjrs = $("cjrs");     
//	var fkzj = $("fkzj");
	var sjly = $("sjly");
	var cljg = $("cljg");
	var jzrs_BlackList = $("jzrs_BlackList");
	
	var params={};
	
	params["FEEDBACKID"] = FEEDBACKID.value;
	
	//params["fkdw"] = deptcode;
	//add by wangxt 取反馈单位id
	params["fkdw"] = $("fkdw").deptid;
	params["fkr"] = fkr.value;
	//params["cjdw"] = deptcode;
	//出警单位id
	params["cjdw"] = $("cjdw").deptid
	params["cjr"] = cjr.value;
	params["cjsj"] = cjsj.value;
	params["djsj"] = djsj.value;
	if(jdhb.checked == true){
		params["jdhb"] = "true";
	}else{
		params["jdhb"] = "false";
	}
	
	params["ALARMID"] = $('btnAffairProcess').strAlarmId; //ALARMID.value;
	params["ajfssj"] = ajfssj.value;
	params["ajjssj"] = ajjssj.value;
	params["ajlx"] = ajlx.value;
//	params["alarmState_Exception"] = alarmState_Exception.value;
	params["cdcl"] = cdcl.value;
	params["cjrs"] = cjrs.value;
	params["sjly"] = sjly.value;
	params["cljg"] = cljg.value;
	params["jzrs"] = jzrs_BlackList.value;
	//add by wangxt 09-5-20
	params["EVENTSTATE_NOW"] = $('btnAffairProcess').strState;
	
	
	if(ajfssj.value==""){
		alert("请输入案发时间!");
		return;
	}
	
	var cjrsflag;
	if(cjrs.value.length>0){
	  cjrsflag=checkMath(cjrs.value)
	  if(!cjrsflag) {
		alert("请输入出警人数!");
	    return null;
	  }
	}
	var strfkzj = "false";
//		alert("strFeedType:"+strFeedType);
	switch (strFeedType) {
		case 'cj':
		    if(cjr.value=="") {
		       $("cjr").focus();
		       alert("请输入出警人!");
	           return null;
	         }
			if(cjsj.value == ""){
				alert("请填写出警时间！");
				$("cjsj").focus();
				return null;
			}
			params["djsj"] = '';
			params["ajjssj"] = '';
			EVENTSTATE = "004006";//已派警
		case 'ddxc':
		    if(cjr.value=="") {
		       $("cjr").focus();
		       alert("请输入出警人!");
	           return null;
	         }
			if(djsj.value == ""){
				alert("请填写到警时间！");
				$("djsj").focus();
				return null;
//			}else if(djsj.value < cjsj.value){
//				alert("出警时间大于到警时间请检查！");
//				djsj.value = "";
//				$("djsj").focus();
//				return null;
//			}else if (ajjssj.value < ajfssj.value&&ajjssj.value.length>0){
//				alert("事件开始时间大于事件结束时间请检查！");
//				ajjssj.value = ""
//				$("ajjssj").focus();
//				return null;
//			}else if (ajjssj.value < cjsj.value&&ajjssj.value.length>0){
//				alert("出警时间大于事件结束时间请检查！");
//				ajjssj.value = ""
//				$("ajjssj").focus();
//				return null;
//			}else if (ajjssj.value < djsj.value&&ajjssj.value.length>0){
//				alert("到警时间大于事件结束时间请检查！");
//				ajjssj.value = ""
//				$("ajjssj").focus();
//				return null;
			}
			params["ajjssj"] = '';
			EVENTSTATE = "004014";//派警察到现场	
			break;
		case 'clwb':
			if(ajjssj.value == ""){
				alert("请填写事件结束时间！");
				$("ajjssj").focus();
				return null;
			}
			if (ajjssj.value < ajfssj.value){
				alert("事件开始时间大于事件结束时间请检查！");
				ajjssj.value = ""
				$("ajjssj").focus();
				return null;
			}
			if (ajjssj.value < djsj.value){
				alert("到警时间大于事件结束时间请检查！");
				ajjssj.value = ""
				$("ajjssj").focus();
				return null;
			}
			EVENTSTATE = "004013";//处理完毕	
			strfkzj = "true";
			break;
		default:
			break;
	}
	params["fkzj"] = strfkzj;
	params["time"] = getSysdate(0);
	params["EVENTSTATE"] = EVENTSTATE;
	
	var eventType = $('btnAffairProcess').strType ;
	params["eventType"] = eventType ;
//		alert(eventType);
	var url = "dispatch.feedBackModify.editForAccident.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:modifyBlackListResponse});
	
}
//完成后返回处理<按钮的隐藏>
function modifyBlackListResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseText;
//	alert("xmlDoc:"+xmlDoc);
	if(xmlDoc == "T"){
		alert("反馈成功！");
		//if(EVENTSTATE == "004013"){
		//	$('btnAffairProcess').strType = EVENTSTATE;
		//}else if(EVENTSTATE == "004014"){
		//	$('btnAffairProcess').strType = EVENTSTATE;
		//}
		switch (strFeedType) {
			case 'cj':
				$("cjbtn_BlackList").style.display = "none";
				$("djsj").value = getSysdate(0);
				break;
			case 'ddxc':
				$("cjbtn_BlackList").style.display = "none";
				$("ddxcbtn_BlackList").style.display = "none";
				if($("ajjssj").value != ""){
					$("ajjssj").value = getSysdate(0);
				}
				break;
			case 'clwb':
			    $("addComout").style.display= "none";
	            $("addFinish").style.display= "none";
				$("cjbtn_BlackList").style.display = "none";
				$("ddxcbtn_BlackList").style.display = "none";
				$("clwbbtn_BlackList").style.display = "none";
				
				break;
			default:
				break;
		}
		initFeedBackPage();
		watching.readNoticedAffair();	
		
		return true;
	}else{
		alert("反馈失败！");
		return true;
	}		
}