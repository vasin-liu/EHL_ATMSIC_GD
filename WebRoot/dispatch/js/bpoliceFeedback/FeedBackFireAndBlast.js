/**
    * Copyright (c) 2009, EHL Technologies, Inc.
    * All right reserved.
    * 说明：火灾爆炸警情处理实现
    * 作者：ldq
    * 日期: 2009-04-13
    */
/** 
    * desc:  火灾爆炸警情处理
    * author：ldq
    * date:   2009-04-13
    * version: 1.0
    */    
var strFeedType;
var EVENTSTATE = "";
function modifyFireAndBlast(feedType){
	
	strFeedType = feedType;
//	alert("test-->:"+feedType);
	var FEEDBACKID = $("FEEDBACKID");
	var fkdw = $("fkdw");
	var fkr = $("fkr");
	var cjdw = $("cjdw");
	var cjr = $("cjr");
	var cjsj = $("cjsj");
	var djsj = $("firedjsj");
	var jdhb = $("jdhb");
	  
//	var ALARMID = $("ALARMID");
	var ajfssj = $("ajfssj");   
	var ajjssj = $("fireajjssj");   
	var ajlx = $("ajlx_feedBack");     
	
	var yxdl_FireAndBlast = $("yxdl_FireAndBlast");        
	var jjss_FireAndBlast = $("jjss_FireAndBlast");      
	var qsrs_FireAndBlast = $("qsrs_FireAndBlast");      
	var zsrs_FireAndBlast = $("zsrs_FireAndBlast");      
	var swrs_FireAndBlast = $("swrs_FireAndBlast");      

	var cdcl = $("cdcl");      
	var cjrs = $("cjrs");      
	var sjly = $("sjly");
	var cljg = $("cljg");
	
//	var eventType = $("eventType");      
	
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
	
	params["yxdl"] = yxdl_FireAndBlast.value;
	params["jjss"] = jjss_FireAndBlast.value;
	params["qsrs"] = qsrs_FireAndBlast.value;
	params["zsrs"] = zsrs_FireAndBlast.value;
	params["swrs"] = swrs_FireAndBlast.value;
	
	params["cdcl"] = cdcl.value;
	params["cjrs"] = cjrs.value;
	params["sjly"] = sjly.value;
	params["cljg"] = cljg.value;
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
		alert("请输入出警人数数字!");
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
	params["time"] = getSysdate(0);
	params["EVENTSTATE"] = EVENTSTATE;
	
	var eventType = $('btnAffairProcess').strType ;
	params["eventType"] = eventType ;
//	alert(eventType);
	var url = "dispatch.feedBackModify.editForAccident.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:modifyResponse});
	
}
function modifyResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseText;
	if(xmlDoc == "T"){
		alert("反馈成功！");
		watching.readNoticedAffair();	
		initFeedBackPage();
		//if(EVENTSTATE == "004013"){
		//	$('btnAffairProcess').strType = EVENTSTATE;
		//}else if(EVENTSTATE == "004014"){
		//	$('btnAffairProcess').strType = EVENTSTATE;
		//}
		switch (strFeedType) {
			case 'cj':
				$("cjbtn_FireAndBlast").style.display = "none";
				$("djsj").value = getSysdate(0);
				break;
			case 'ddxc':
				$("cjbtn_FireAndBlast").style.display = "none";
				$("ddxcbtn_FireAndBlast").style.display = "none";
				if($("ajjssj").value != ""){
					$("ajjssj").value = getSysdate(0);
				}
				break;
			case 'clwb':
			    $("addComout").style.display= "none";
	            $("addFinish").style.display= "none";
				$("cjbtn_FireAndBlast").style.display = "none";
				$("ddxcbtn_FireAndBlast").style.display = "none";
				$("clwbbtn_FireAndBlast").style.display = "none";
				
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
