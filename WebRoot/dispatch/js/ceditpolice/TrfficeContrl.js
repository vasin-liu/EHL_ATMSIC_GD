/** 
* desc:新增或查询的跳转
* param:
* return:
* author：wkz
* date: 2010-1-12
* version:
*/
function onButtonClick(itemId, itemValue) {
	var jgbh = $('jgbh').value;
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("TrafficContrlEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0", "", "dialogWidth:800px;dialogHeight:470px");
		doSearch(jgbh);
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("TrafficContrlEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&alarmId='" + itemValue + "'", "", "dialogWidth:800px;dialogHeight:470px");
		doSearch(jgbh);
		
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("TrafficContrlView.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&alarmId='" + itemValue + "'", "", "dialogWidth:800px;dialogHeight:470px");
		doSearch(jgbh);
		
	}
	if (id == "search") {
		currPage = 1;
		doSearch(jgbh);
	}
	if (id == "delete") {
		doDelete(itemValue);
		doSearch(jgbh);
	}
}

/** 
    * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路信息
    * param:
    * return:
    * author：wkz
    * date: 2010-4-14
    * version:
    */
function modify(insrtOrUpdt) {
	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	if (ROADID.value == "") {
		alert("请选择道路！");
		ROADID.focus();
		return;
	}
	var EVENTSOURCE = $('EVENTSOURCE');
	var EVENTTYPE = $('EVENTTYPE');
	var ALARMUNIT = $('ALARMUNIT');
	var TITLE = $('TITLE');
	var ALARMREGIONID = $('ALARMREGIONID');
	var ALARMREGION = $('ALARMREGION');
	var ROADNAME = $('ROADNAME');
	if (ROADNAME.value == "") {
		alert("请录入路段名称！");
		ROADNAME.focus();
		return;
	}
	var KMVALUE = $('KMVALUE');
	if (KMVALUE.value == "") {
		alert("请录入管制起点千米值！");
		KMVALUE.focus();
		return;
	}
	var MVALUE = $('MVALUE');
	if (MVALUE.value == "") {
		alert("请录入管制起点米值！");
		MVALUE.focus();
		return;
	}
	var ENDKMVALUE = $('ENDKMVALUE');
	if (ENDKMVALUE.value == "") {
		alert("请录入管制终点千米值！");
		ENDKMVALUE.focus();
		return;
	}
	var ENDMVALUE = $('ENDMVALUE');
	if (ENDMVALUE.value == "") {
		alert("请录入管制终点米值！");
		ENDMVALUE.focus();
		return;
	}
	var regExp=/^\d+(\.\d+)?$/;
    if(regExp .test ($('KMVALUE').value)) {
    	
     } else {
     	alert("管制起点千米值必须是数字！");
		$('KMVALUE').focus();
		return;
     }
    if(regExp .test ($('MVALUE').value)) {
    	
     } else {
     	alert("管制起点米值必须是数字！");
		$('MVALUE').focus();
		return;
     }
     if(regExp .test ($('EndKMVALUE').value)) {
    	
     } else {
     	alert("管制终点千米值必须是数字！");
		$('EndKMVALUE').focus();
		return;
     }
     if(regExp .test ($('EndMVALUE').value)) {
    	
     } else {
     	alert("管制终点米值必须是数字！");
		$('EndMVALUE').focus();
		return;
     }
	var CaseHappenTime = $('CaseHappenTime');
	if (CaseHappenTime.value == "") {
		alert("请录入管制开始时间！");
		CaseHappenTime.focus();
		return;
	}
	var CaseEndTime = $('CaseEndTime');
	if (CaseEndTime.value == "") {
		alert("请录入管制结束时间！");
		CaseEndTime.focus();
		return;
	}
	var REPORTUNIT = $('REPORTUNIT');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	if (REPORTPERSON.value == "") {
		alert("请录入填报人！");
		REPORTPERSON.focus();
		return;
	}
	var REPORTTIME = $('REPORTTIME');
	var PLAN = $('PLAN');
	if (PLAN.value == "") {
		alert("请录入管制方案！");
		PLAN.focus();
		return;
	}
	//var RADIOTYPE = $('RADIOTYPE_1').checked?$('RADIOTYPE_1'):$('RADIOTYPE_2');
	var RADIOTYPE = $('RADIOTYPE_1').checked?"0":"1"; //第一行为下行 0 第二行为1
	
	var params = {};
	params["ALARMID"] = ALARMID.value;
	params["ROADID"] = ROADID.value;
	params["EVENTSOURCE"] = EVENTSOURCE.value;
	params["EVENTTYPE"] = EVENTTYPE.value;
	params["ALARMUNIT"] = ALARMUNIT.value;
	params["TITLE"] = TITLE.value;
	params["ALARMREGIONID"] = ALARMREGIONID.value;
	params["ALARMREGION"] = ALARMREGION.value;
	params["ROADNAME"] = ROADNAME.value;
	params["KMVALUE"] = KMVALUE.value;
	params["ENDKMVALUE"] = ENDKMVALUE.value;
	params["MVALUE"] = MVALUE.value;
	params["ENDMVALUE"] = ENDMVALUE.value;
	params["CaseHappenTime"] = CaseHappenTime.value;
	params["CaseEndTime"] = CaseEndTime.value;
	params["REPORTUNIT"] = REPORTUNIT.value;
	params["REPORTPERSON"] = REPORTPERSON.value;
	params["REPORTTIME"] = REPORTTIME.value;
	params["PLAN"] = PLAN.value;

	params["RADIOTYPE"] = RADIOTYPE;
	/*var roadDirections = document.getElementsByName('ROADDIRECTIONELE');
	var roadDirection = '';
	for(i=0;i<roadDirections.length;i++){
		if(roadDirections[i].checked){
			roadDirection = roadDirections[i].value;
			break;
		}
	}
	params["ROADDIRECTION"] = roadDirection;*/
	params["insrtOrUpdt"] = insrtOrUpdt;
	if(!validateInput()) return;
	var url = "contrl.contrlmanage.modifyContrlInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyTrfficeResult});
}
function showModifyTrfficeResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc.indexOf('成功')>-1){
		alert(xmlDoc);
		window.close();
	}else{
		alert('操作失败，请重试！');
	}
}

/** 
    * desc:查询
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function doSearch(jgbh){
	var num = 1;
	num = currPage;
	var searchDepID = ""; 
	if($("depUnitId")) {
		searchDepID = $("depUnitId").value; 
	}

	var sql = "select taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION,";
	sql +=" to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi'),to_char(taa.CASEENDTIME,'yyyy-mm-dd HH24:mi'),taa.EVENTSTATE,"
	+ "dic.begin,dic.end";
	sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat,T_OA_DICTDLFX dic"
	
	if (searchDepID == "") {
		sql +=" where taa.AlarmUnit like '" + jgbh + "%' and taa.ALARMID=tat.ALARMID  and taa.roadid=dic.dlmc";
	} else {
		var depSub = "";
	    if("0000" == searchDepID.substring(2,6)){
	    	depSub = searchDepID.substring(0,2);
	    }else if("00" == searchDepID.substring(4,6)){
	    	depSub = searchDepID.substring(0,4);
	    }else{
	    	depSub = searchDepID.substring(0,6);
	    }
	    // 查询单位"总队"时的处理
	    if(depSub.length == 2 ) {
			sql +=" where taa.ALARMREGIONID like '" + depSub + "%' and taa.ALARMID=tat.ALARMID";
	    // 查询单位"支队"时的处理
	    } else if(depSub.length == 4 ) {
			sql +=" where taa.ALARMREGIONID like '" + depSub + "%' and taa.ALARMID=tat.ALARMID";
	    // 查询单位"大队"时的处理
	    } else {
			sql +=" where taa.ALARMREGIONID = '" + searchDepID + "' and taa.ALARMID=tat.ALARMID";
	    
	    }
	}

    if ($('sj1').value!="" && $('sj2').value!="") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value
			+ "' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + "'";
	}else if($('sj1').value!="" && $('sj2').value==""){
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + "'";
	}else if($('sj1').value=="" && $('sj2').value!=""){
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + "'";
	}
    sql +=$("ROADNAME").value==""?"":(" and taa.ROADNAME like '%" + $("ROADNAME").value + "%'");
    if($("trifficState")) {
    	if($("trifficState").value=="570003"){
    		sql += " AND taa.EVENTSTATE= '570003'";
    	}
    	if($("trifficState").value=="570004"){
    		sql += " AND taa.EVENTSTATE= '570004'";
    	}
    	
    }
    sql += " order by  taa.EVENTSTATE asc ,taa.CASEHAPPENTIME desc"
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showTrfficeResultsPage","10");
	if(num != 1 && num != 0) {
		sleep(250);
		onTurnToPage(num);
	}
}

function sleep(timeout) {
	window.showModalDialog("javascript:document.writeln('<script>window.setTimeout(function () { window.close(); }, "
					+ timeout + ");<\/script>');");
}

/** 
    * desc:删除
    * param:
    * return:
    * author：wxt
    * date: 2010-01-16
    * version:
    */
function doDelete(alarmid) {                                
	if (confirm("您确定解除此项管制信息吗?")) { //提示用户是否删除记录                                       
		var strUrl = "contrl.contrlmanage.deleteContrlInfo.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "ALARMID=" + alarmid;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
	} else {  
		return;
	}
}
function showResponseDelete(xmlHttp) {
	if(xmlHttp.responseText=='success') {
	  alert("管制解除成功！");
	}
	else {
	  alert("管制解除失败!");
	}
}

function showTrfficeResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	var str = "<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>"; 
		str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='5%' class='td_r_b td_font'>序号</td>";
		str += "<td width='15%' class='td_r_b td_font'>录入单位</td>";
	    str += "<td width='15%' class='td_r_b td_font'>路段名称</td>";
	    	str += "<td width='5%' class='td_r_b td_font'>方向</td>";
		str += "<td width='15%' class='td_r_b td_font'>管制开始时间</td>";
		//str += "<td width='15%' class='td_r_b td_font'>管制结束时间</td>";
		str += "<td width='15%' class='td_r_b td_font'>管制状态</td>";
		str += "<td width='5%' class='td_r_b td_font'>查看</td>";
		str += "<td width='5%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='10%' class='td_r_b td_font'>解除管制</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='9' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			var showStr = "";
			if(results[6].text == "570003") {
				showStr = "管制中";
			} else {
				showStr = "管制结束";
			}
			str += "<tr align='center'>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"+(i+1)+"</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"":results[1].text)+"</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"":results[2].text)+"</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>" + (results[3].text == "0" ? (results[7].text+"->"+results[8].text):(results[8].text+"->"+results[7].text)) + "</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"":results[4].text)+"</td>";
			//str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+(results[5].text=="null"?"":results[5].text)+"</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+showStr+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			
			if(showStr == "管制结束" ) {
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\" onClick=\"alert('解除后的管制信息不可以修改！');\"></td>"
				str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\" onClick=\"alert('解除后的管制信息不可以再解除！');\"></td>"
			} else {
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
				str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			}
			str += "</tr>";			
	        }
	      }
		str +="</table>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}
var insrtOrUpdt = "1";
/**
    根据警情编号，事件类型查询详细信息
*/
function getContrlInfo(alarmId,flg) {
	insrtOrUpdt = flg;
	if(alarmId!=""){
		var url = "contrl.contrlmanage.getContrlInfo.d"
		url = encodeURI(url);	
		params="alarmid="+alarmId;		
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	 
	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	var PLAN = $('PLAN');

	fillListBox("alarmRoad_TrafficCrowd_td","alarmRoad_TrafficCrowd","185",
		" select dlmc A, dlmc from LCB_PT_MIS group by dlmc, dlmc order by A, dlmc ","请选择",
		"selectRoad('" + results[1].text + "','" + results[3].text + "');getAddName();","onChangeValue");
	
	var ROADNAME = $('ROADNAME');
	var KMVALUE = $('KMVALUE');
	var EndKMVALUE = $('EndKMVALUE');
	var MVALUE = $('MVALUE');
	var EndMVALUE = $('EndMVALUE');
	var CaseHappenTime = $('CaseHappenTime');
	var CaseEndTime = $('CaseEndTime');
	var REPORTUNIT = $('REPORTUNITVALUE');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	var REPORTTIME = $('REPORTTIME');
	/*var inroadName = results[14].text==""?"":results[14].text;
	var outroadName = results[15].text==""?"":results[15].text;
	if((inroadName != "" && inroadName != "　" )&& (outroadName != "" && outroadName != "　" )) {
		var setWay = '<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked>&nbsp;' + inroadName + "&nbsp;&nbsp;—>&nbsp;&nbsp;" + outroadName + '<br/>' +
					'<input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_2">&nbsp;' + outroadName + "&nbsp;&nbsp;—>&nbsp;&nbsp;" + inroadName;
		$("setWay").innerHTML = setWay;
	} else {
		$("setWay").innerHTML = '<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked>上行 <input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_2">下行';
	}*/
	var RADIOTYPE = results[3].text=='0'?$('RADIOTYPE_1'):$('RADIOTYPE_2');
	ALARMID.value = results[0].text;
	PLAN.value = results[2].text;
	ROADNAME.value = results[4].text;
	KMVALUE.value = results[5].text;
	EndKMVALUE.value = results[6].text;
	MVALUE.value = results[12].text;
	EndMVALUE.value = results[13].text;
	CaseHappenTime.value = results[7].text;
	CaseEndTime.value = results[8].text;
	REPORTUNIT.value = results[9].text;
	REPORTPERSON.value = results[10].text;
	REPORTTIME.value = results[11].text;
	RADIOTYPE.checked = true;
}

function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@\'\;#\$\^&]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length; i++) {
		var item = input[i];
		if (item.type == "text" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括:',;, ~、@、#、$、^、&");
				item.focus();
				return false;
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括:',;, ~、@、#、$、^、&");
				item.focus();
				return false;	
			}
		}
	}
	return true;
}


function selectRoad(roadId,roadDirection){
	if(insrtOrUpdt == "2") {
		$('alarmRoad_TrafficCrowd').value = roadId;
		$('alarmRoad_TrafficCrowd').disabled = true;
	} else {
		$('alarmRoad_TrafficCrowd').value = roadId;
		$('alarmRoad_TrafficCrowd').disabled = false;
	}
	
}

function onChangeValue() {
	// 取得道路名称
	var roadid = $("alarmRoad_TrafficCrowd").value;
	if (roadid == "") {
		return;
	}
	var url = "crowd.crowdmanage.getLcbptmisZt.d";
	var params = "roadName=" + roadid;
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : showSetCheckValue
			});
	getAddName();
}

function showSetCheckValue(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0].getElementsByTagName("col");
	maxRoadLength = results[0].text;
	minRoadLength = results[1].text;
	// 取得道路名称
	var roadid = $("alarmRoad_TrafficCrowd").value;
	var str = "广东省内" + roadid + "里程为" + minRoadLength + "km 至" + maxRoadLength + "km"; 
	$("showLength").innerHTML=str;
}
function insertTrafficContrl() {

	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	if (ROADID.value == "") {
		alert("请选择道路！");
		ROADID.focus();
		return;
	}
	var EVENTSOURCE = $('EVENTSOURCE');
	var EVENTTYPE = $('EVENTTYPE');
	var ALARMUNIT = $('ALARMUNIT');
	var TITLE = $('TITLE');
	var ALARMREGIONID = $('ALARMREGIONID');
	var ALARMREGION = $('ALARMREGION');
	var ROADNAME = $('ROADNAME');
	if (ROADNAME.value == "") {
		alert("请录入路段名称！");
		ROADNAME.focus();
		return;
	}
	var KMVALUE = $('KMVALUE');
	if (KMVALUE.value == "") {
		alert("请录入管制起点千米值！");
		KMVALUE.focus();
		return;
	}
	var MVALUE = $('MVALUE');
	if (MVALUE.value == "") {
		alert("请录入管制起点米值！");
		MVALUE.focus();
		return;
	}
	var ENDKMVALUE = $('ENDKMVALUE');
	if (ENDKMVALUE.value == "") {
		alert("请录入管制终点千米值！");
		ENDKMVALUE.focus();
		return;
	}
	var ENDMVALUE = $('ENDMVALUE');
	if (ENDMVALUE.value == "") {
		alert("请录入管制终点米值！");
		ENDMVALUE.focus();
		return;
	}
	var regExp=/^\d+(\.\d+)?$/;
    if(regExp .test ($('KMVALUE').value)) {
    	
     } else {
     	alert("管制起点千米值必须是数字！");
		$('KMVALUE').focus();
		return;
     }
    if(regExp .test ($('MVALUE').value)) {
    	
     } else {
     	alert("管制起点米值必须是数字！");
		$('MVALUE').focus();
		return;
     }
     if(regExp .test ($('EndKMVALUE').value)) {
    	
     } else {
     	alert("管制终点千米值必须是数字！");
		$('EndKMVALUE').focus();
		return;
     }
     if(regExp .test ($('EndMVALUE').value)) {
    	
     } else {
     	alert("管制终点米值必须是数字！");
		$('EndMVALUE').focus();
		return;
     }
	var CaseHappenTime = $('CaseHappenTime');
	if (CaseHappenTime.value == "") {
		alert("请录入管制开始时间！");
		CaseHappenTime.focus();
		return;
	}
	var CaseEndTime = $('CaseEndTime');
	if (CaseEndTime.value == "") {
		alert("请录入管制结束时间！");
		CaseEndTime.focus();
		return;
	}
	var REPORTUNIT = $('REPORTUNIT');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	if (REPORTPERSON.value == "") {
		alert("请录入填报人！");
		REPORTPERSON.focus();
		return;
	}
	var REPORTTIME = $('REPORTTIME');
	var PLAN = $('PLAN');
	if (PLAN.value == "") {
		alert("请录入管制方案！");
		PLAN.focus();
		return;
	}
	
	var RADIOTYPE = $('RADIOTYPE_1').checked?"0":"1";
	
	var params = {};
	params["ALARMID"] = ALARMID.value;
	params["ROADID"] = ROADID.value;
	params["EVENTSOURCE"] = EVENTSOURCE.value;
	params["EVENTTYPE"] = EVENTTYPE.value;
	params["ALARMUNIT"] = ALARMUNIT.value;
	params["TITLE"] = TITLE.value;
	params["ALARMREGIONID"] = ALARMREGIONID.value;
	params["ALARMREGION"] = ALARMREGION.value;
	params["ROADNAME"] = ROADNAME.value;
	params["KMVALUE"] = KMVALUE.value;
	params["ENDKMVALUE"] = ENDKMVALUE.value;
	params["MVALUE"] = MVALUE.value;
	params["ENDMVALUE"] = ENDMVALUE.value;
	params["CaseHappenTime"] = CaseHappenTime.value;
	params["CaseEndTime"] = CaseEndTime.value;
	params["REPORTUNIT"] = REPORTUNIT.value;
	params["REPORTPERSON"] = REPORTPERSON.value;
	params["REPORTTIME"] = REPORTTIME.value;
	params["PLAN"] = convertSql(PLAN.value);
	
	params["RADIOTYPE"] = RADIOTYPE;

	params["insrtOrUpdt"] = '0';
	if(!validateInput()) return;
	var url = "contrl.contrlmanage.modifyContrlInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showAfterResult});
}

function showAfterResult(xmlHttp) {
	alert(xmlHttp.responseText);
	window.location.reload(true);
}
/** 
    * desc:查询
    * param:
    * return:
    * author：dxn
    * date: 2010-4-2
    * version:
    */
function showExcelInfo1(str,jgbh) {
	var searchDepID = ""; 
	if($("depUnitId")) {
		searchDepID = $("depUnitId").value; 
	}

	var sql = "select taa.ALARMID,taa.ALARMREGION,taa.roadid,taa.ROADNAME,taa.ROADDIRECTION,";
	sql +=" to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi'),to_char(taa.CASEENDTIME,'yyyy-mm-dd HH24:mi')," +
			" (select name from T_ATTEMPER_CODE where taa.EVENTSTATE = id), "
	+ "dic.begin,dic.end";
	sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat,T_OA_DICTDLFX dic"
	
	if (searchDepID == "") {
		sql +=" where taa.AlarmUnit like '" + jgbh + "%' and taa.ALARMID=tat.ALARMID  and taa.roadid=dic.dlmc";
	} else {
		var depSub = "";
	    if("0000" == searchDepID.substring(2,6)){
	    	depSub = searchDepID.substring(0,2);
	    }else if("00" == searchDepID.substring(4,6)){
	    	depSub = searchDepID.substring(0,4);
	    }else{
	    	depSub = searchDepID.substring(0,6);
	    }
	    // 查询单位"总队"时的处理
	    if(depSub.length == 2 ) {
			sql +=" where taa.ALARMREGIONID like '" + depSub + "%' and taa.ALARMID=tat.ALARMID";
	    // 查询单位"支队"时的处理
	    } else if(depSub.length == 4 ) {
			sql +=" where taa.ALARMREGIONID like '" + depSub + "%' and taa.ALARMID=tat.ALARMID";
	    // 查询单位"大队"时的处理
	    } else {
			sql +=" where taa.ALARMREGIONID = '" + searchDepID + "' and taa.ALARMID=tat.ALARMID";
	    
	    }
	}

    if ($('sj1').value!="" && $('sj2').value!="") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value
			+ "' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + "'";
	}else if($('sj1').value!="" && $('sj2').value==""){
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + "'";
	}else if($('sj1').value=="" && $('sj2').value!=""){
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + "'";
	}
    sql +=$("ROADNAME").value==""?"":(" and taa.ROADNAME like '%" + $("ROADNAME").value + "%'");
    if($("trifficState")) {
    	if($("trifficState").value=="570003"){
    		sql += " AND taa.EVENTSTATE= '570003'";
    	}
    	if($("trifficState").value=="570004"){
    		sql += " AND taa.EVENTSTATE= '570004'";
    	}
    	
    }
    sql += " order by  taa.EVENTSTATE asc ,taa.CASEHAPPENTIME desc"
//    alert(sql);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
	var url = "contrl.contrlmanage.showExcel.d?" + "searchSql=" + sql;
	url = encodeURI(url);
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");
}

function resetDriect() {
	var roadId = $("alarmRoad_TrafficCrowd").value;
	if (roadId != null) { //提示用户是否删除记录                                       
		var strUrl = "contrl.contrlmanage.getRoadWayInfo.d";  //把参数传给后端的java
		var params = "roadId=" + roadId;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showRoadWayInfo});
	}
}

function showRoadWayInfo (xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var inroadName = results[0].text==""?"":results[0].text;
	var outroadName = results[1].text==""?"":results[1].text;
	if((inroadName != "" && inroadName != "　" )&& (outroadName != "" && outroadName != "　" )) {
		var setWay = '<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked>&nbsp;' + inroadName+ "&nbsp;&nbsp;—>&nbsp;&nbsp;" + outroadName + '<br/>' +
					'<input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_2">&nbsp;' + outroadName + "&nbsp;&nbsp;—>&nbsp;&nbsp;" + inroadName;
		$("setWay").innerHTML = setWay;
	} else {
		$("setWay").innerHTML = '<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked>上行 <input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_2">下行';
	}
}


function getAddName() {
	var roadid = $("alarmRoad_TrafficCrowd").value;
	if (roadid == "") {
		return;
	}
	var url = "roadbuild.roadbuildmanage.getAddName.d";
	var params = "roadid=" + roadid;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showRoadDirection
	});
}

function showRoadDirection(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	var s = "";
	var e = "";
	if (xmlDoc != "") {
		var roadSEAdd = xmlDoc.split(",");
		s = roadSEAdd[0];
		e = roadSEAdd[1];
	} else {
		s = "不明确起点";
		e = "不明确终点";
	}
	$("rdForward").innerText = s + "—>" + e;
	$("rdBack").innerText = e + "—>" + s;
}