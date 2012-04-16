/**
    * Copyright (c) 2009, EHL Technologies, Inc.
    * All right reserved.
    * 说明：市政警情处理
    * 作者：ldq
    * 日期: 2009-04-13
    */
/** 
    * desc:  市政事件警情处理实现
    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */    
var strFeedType;
var EVENTSTATE = "";
function modifyTownPlan(feedType){
	strFeedType = feedType;
//	alert("test-->:"+strFeedType);
	var FEEDBACKID = $("FEEDBACKID");
	var fkdw = $("fkdw");
	var fkr = $("fkr");
	var cjdw = $("cjdw");
	var cjr = $("cjr");
	var cjsj = $("cjsj");
	var djsj = $("townplandjsj");
	var jdhb = $("jdhb");
	  
//	var ALARMID = $("ALARMID");
	var ajfssj = $("ajfssj");   
	var ajjssj = $("townplanajjssj");   
	var ajlx = $("ajlx_feedBack");     
	var yxdl_TownPlan = $("yxdl_TownPlan");   
	var bjfw_TownPlan = $("bjfw_TownPlan");
//	 alarmState_Exception 
	var cdcl = $("cdcl");      
	var cjrs = $("cjrs");     
//	var fkzj = $("fkzj");
	var sjly = $("sjly");
	var cljg = $("cljg");
	
	var params={};
	
	params["FEEDBACKID"] = FEEDBACKID.value;
	
	//params["fkdw"] = deptcode;
	params["fkdw"] = $("fkdw").deptid;
	params["fkr"] = fkr.value;
	//params["cjdw"] = deptcode;
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
	
	params["yxdl"] = yxdl_TownPlan.value;
	params["bjfw"] = bjfw_TownPlan.value;
	
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
			break;
		case 'ddxc':
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
	params["EVENTSTATE_NOW"] = $('btnAffairProcess').strState;
	var eventType = $('btnAffairProcess').strType ;
	params["eventType"] = eventType ;
//		alert(eventType);
	var url = "dispatch.feedBackModify.editForAccident.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:modifyTownPlanResponse});
	
}
//完成后返回处理<按钮的隐藏>
function modifyTownPlanResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseText;
//	alert("xmlDoc:"+xmlDoc);
	if(xmlDoc == "T"){
		alert("反馈成功！");
		//刷新列表
		initFeedBackPage();
		watching.readNoticedAffair();	
		//if(EVENTSTATE == "004013"){
			//$('btnAffairProcess').strType = EVENTSTATE;
		//}else if(EVENTSTATE == "004014"){
			//$('btnAffairProcess').strType = EVENTSTATE;
		//}
		switch (strFeedType) {
			case 'cj':
				$("cjbtn_TownPlan").style.display = "none";
				$("djsj").value = getSysdate(0);
				break;
			case 'ddxc':
				$("cjbtn_TownPlan").style.display = "none";
				$("ddxcbtn_TownPlan").style.display = "none";
				if($("ajjssj").value != ""){
					$("ajjssj").value = getSysdate(0);
				}
				break;
			case 'clwb':
			    $("addComout").style.display= "none";
	            $("addFinish").style.display= "none";
				$("cjbtn_TownPlan").style.display = "none";
				$("ddxcbtn_TownPlan").style.display = "none";
				$("clwbbtn_TownPlan").style.display = "none";
				
				break;
			default:
				break;
		}
		
		return true;
	}else{
		alert("反馈失败！");
		return true;
	}		
}
