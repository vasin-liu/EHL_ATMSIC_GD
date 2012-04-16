/**
    * Copyright (c) 2009, EHL Technologies, Inc.
    * All right reserved.
    * 说明：警情处理实现    * 作者：ldq
    * 日期: 2009-04-13
    */
/** 
    * desc:  交通事故警情处理    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */    
var strFeedType;
var EVENTSTATE = "";

function getFinishBtns(){
	var btnAry=new Array();	
	btnAry.push($("ajjssjFont"));
	btnAry.push($("ajjssj"));
	btnAry.push($("weatherajjssjFont"));
	btnAry.push($("weatherajjssj"));
	btnAry.push($("policeajjssjFont"));
	btnAry.push($("policeajjssj"));
	btnAry.push($("exceptioncarajjssjFont"));
	btnAry.push($("exceptioncarajjssj"));
	btnAry.push($("geologicajjssjFont"));
	btnAry.push($("geologicajjssj"));
	btnAry.push($("townplanajjssjFont"));
	btnAry.push($("townplanajjssj"));
	btnAry.push($("fireajjssjFont"));
	btnAry.push($("fireajjssj"));
	
	return btnAry;
}


function modifyAccident(feedType){
	
	strFeedType = feedType;
//	alert("test-->:"+strFeedType);
	var FEEDBACKID = $("FEEDBACKID");
	
	var fkdw = $("fkdw");
	var fkr = $("fkr");
	var cjdw = $("cjdw");
	var cjr = $("cjr");
	var cjsj = $("cjsj");
	//var djsj = $("jtsgdjsj");
	var djsj = $("djsj");
	var jdhb = $("jdhb");
	  
//	var ALARMID = $("ALARMID");
	var ajfssj = $("ajfssj");   
	var ajjssj = $("ajjssj");   
	var ajlx = $("ajlx_feedBack");     
	var alarmState = $("alarmState_feedBack");
	var sgdj = $("sgdj");      
	var jjss = $("jjss");      
	var qsrs = $("qsrs");      
	var zsrs = $("zsrs");      
	var swrs = $("swrs");      
	var sars = $("sars");      
	var zhrs = $("zhrs");      
	var jzrs = $("jzrs");      
	var cdcl = $("cdcl");      
	var cjrs = $("cjrs");      
	var phxsaj = $("phxsaj");    
	var cczaaj = $("cczaaj");    
	var jjjf = $("jjjf");      
	var fkzj = $("fkzj");
	var sjly = $("sjly");
	var cljg = $("cljg");
	
//	var eventType = $("eventType");      
	
	var params={};
	
	params["FEEDBACKID"] = FEEDBACKID.value;
	
	params["fkdw"] = $("fkdw").deptid;
	params["fkr"] = fkr.value;
	params["cjdw"] = $("cjdw").deptid;
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
	params["alarmState"] = alarmState.value;
	params["sgdj"] = sgdj.value;
	params["jjss"] = jjss.value;
	params["qsrs"] = qsrs.value;
	params["zsrs"] = zsrs.value;
	params["swrs"] = swrs.value;
	params["sars"] = sars.value;
	params["zhrs"] = zhrs.value;
	params["jzrs"] = jzrs.value;
	params["cdcl"] = cdcl.value;
	params["cjrs"] = cjrs.value;
	params["sjly"] = sjly.value;
	params["cljg"] = cljg.value;
	
	
	var qsrsflag;
	var zsrsflag;
	var swrsflag;
	var sarsflag;
	var zhrsflag;
	var jzrsflag;
	var cjrsflag;
	
	
	if(ajfssj.value==""){
		alert("请输入案发时间!");
		return;
	}
	
	if(cjrs.value.length>0){
	  cjrsflag=checkMath(cjrs.value)
	  if(!cjrsflag) {
		alert("请输入出警人数!");
	    return null;
	  }
	}
	if(qsrs.value.length>0){
	  qsrsflag=checkMath(qsrs.value)
	  if(!qsrsflag) {
		alert("请输入轻伤人数!");
	    return null;
	  }
	}
	
	if(zsrs.value.length>0){
	  zsrsflag=checkMath(zsrs.value)
	  if(!zsrsflag) {
		alert("请输入重伤人数!");
	    return null;
	  }
	}
	
	if(swrs.value.length>0){
	  swrsflag=checkMath(swrs.value)
	  if(!swrsflag) {
		alert("请输入死亡人数!");
	    return null;
	  }
	}
	
	if(sars.value.length>0){
	  sarsflag=checkMath(sars.value)
	  if(!sarsflag) {
		alert("请输入涉案人数!");
	    return null;
	  }
	}
	
	if(zhrs.value.length>0){
	  zhrsflag=checkMath(zhrs.value)
	  if(!zhrsflag) {
		alert("请输入抓获人数!");
	    return null;
	  }
	}
	
	if(jzrs.value.length>0){
	  jzrsflag=checkMath(jzrs.value)
	  if(!jzrsflag) {
		alert("请输入救助人数!");
	    return null;
	  }
	}

	var strfkzj = "false";
//	alert("strFeedType:"+strFeedType);
	switch (strFeedType) {
		case 'cj':
			if(cjsj.value == ""){
				alert("请填写出警时间！");
				$("cjsj").focus();
				return null;
			}
			if(cjr.value=="") {
		       $("cjr").focus();
		       alert("请输入出警人!");
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
				$("cjsj").focus();
				alert("请填写出警时间！");
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
//			}
//			else if (ajjssj.value < ajfssj.value&&ajjssj.value.length>0){
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
//			if (ajjssj.value < ajfssj.value){
//				alert("事件开始时间大于事件结束时间请检查！");
//				ajjssj.value = ""
//				$("ajjssj").focus();
//				return null;
//			}
//			if(djsj.value < cjsj.value){
//				alert("出警时间大于到警时间请检查！");
//				djsj.value = "";
//				$("djsj").focus();
//				return null;
//			}
//			if (ajjssj.value < djsj.value){
//				alert("到警时间大于事件结束时间请检查！");
//				ajjssj.value = ""
//				$("ajjssj").focus();
//				return null;
//			}
//			if (ajjssj.value < cjsj.value){
//				alert("出警时间大于事件结束时间请检查！");
//				ajjssj.value = ""
//				$("ajjssj").focus();
//				return null;
//			}
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
	if(phxsaj.checked == true){
		params["phxsaj"] = "true";
	}else{
		params["phxsaj"] = "false";
	}
	if(cczaaj.checked == true){
		params["cczaaj"] = "true";
	}else{
		params["cczaaj"] = "false";
	}
	if(jjjf.checked == true){
		params["jjjf"] = "true";
	}else{
		params["jjjf"] = "false";
	}
	var eventType = $('btnAffairProcess').strType ;
	params["eventType"] = eventType ;
	if(eventType == ("001001") || eventType == ("001002")){
		var url = "dispatch.feedBackModify.editForAccident.d";	
		url = encodeURI(url);
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:modifyAccidentResponse});
	}
	
	
}
function modifyAccidentResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseText;
	if(xmlDoc == "T"){
		//if(EVENTSTATE == "004013"){
			//$('btnAffairProcess').strType = EVENTSTATE;
		//}else if(EVENTSTATE == "004014"){
			//$('btnAffairProcess').strType = EVENTSTATE;
		//}
		//$("addComout").style.display= "none";
	   // $("addFinish").style.display= "none";
		alert("反馈成功！");
		//刷新列表
		initFeedBackPage();
		watching.readNoticedAffair();	
		switch (strFeedType) {
			case 'cj':
				$("cjbtn").style.display = "none";
				$("djsj").value = getSysdate(0);
				break;
			case 'ddxc':
				$("cjbtn").style.display = "none";
				$("ddxcbtn").style.display = "none";
				if($("ajjssj").value != ""){
					$("ajjssj").value = getSysdate(0);
				}
				break;
			case 'clwb':
			    $("addComout").style.display= "none";
				$("addFinish").style.display= "none";
				$("cjbtn").style.display = "none";
				$("ddxcbtn").style.display = "none";
				$("clwbbtn").style.display = "none";
				break;
			default:
				break;
		}
	    //if(strFeedType=='clwb') {
		//$("addComout").style.display= "none";
	   // $("addFinish").style.display= "none";
	    //}
		return true;
	}else{
		alert("反馈失败！");
		return true;
	}		
   }

