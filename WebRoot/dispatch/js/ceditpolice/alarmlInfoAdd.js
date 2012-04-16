var RADIOTYPE = null;

function setDescribe() {
	var caseHappenTime = $('caseHappenTime').value;
	var caseHappenPlace = $("caseHappenPlace").value;
	var DEATHPERSONCOUNT = $('DEATHPERSONCOUNT').value;
	var BRUISEPERSONCOUNT = $('BRUISEPERSONCOUNT').value;

	var content = $('eventModify').value;

	if (content != "") {
		return;
	}

	if (caseHappenTime != "") {
		caseHappenTime = caseHappenTime.replace("-", "年");
		caseHappenTime = caseHappenTime.replace("-", "月");
		caseHappenTime = caseHappenTime.replace(" ", "日");
		caseHappenTime = caseHappenTime.replace(":", "时");
		caseHappenTime += "分"
	}

	if (caseHappenTime != "" && caseHappenPlace != "") {
		content += ("   " + caseHappenTime);
		content += ("，在" + caseHappenPlace + "发生一宗");
		if (DEATHPERSONCOUNT != "") {
			if (BRUISEPERSONCOUNT != "") {
				content += ("死亡" + DEATHPERSONCOUNT + "人，");
			} else {
				content += ("死亡" + DEATHPERSONCOUNT + "人的");
			}
		}
		if (BRUISEPERSONCOUNT == "") {
			content += ""
		} else {
			content += "受伤" + BRUISEPERSONCOUNT + "人的"
		}
		content += "交通事故。"
		$('eventModify').value = content;
		$('eventModify').focus();
	}
	moveEnd($('eventModify'));
}

function moveEnd(obj) {
	obj.focus();
	var len = obj.value.length;
	if (document.selection) {
		var sel = obj.createTextRange();
		sel.moveStart('character', len);
		sel.collapse();
		sel.select();
	} else if (typeof obj.selectionStart == 'number'
			&& typeof obj.selectionEnd == 'number') {
		obj.selectionStart = obj.selectionEnd = len;
	}

}

// 插入警情
function insertAlarmInfo(flg) {
	//管辖大队
	var	sobject_single;
	if($("sobject_single") == null) {
		sobject_single = $("jgid").value;
	} else {
		sobject_single = $("sobject_single").value;
	}

	// 事件类别checkbox值的取得
	var ISTHREEUP = $('ISTHREEUP').checked ? "1" : "0";
	var ISBUS = $('ISBUS').checked ? "1" : "0";
	var ISSCHOOLBUS = $('ISSCHOOLBUS').checked ? "1" : "0";
	var ISDANAGERCAR = $('ISDANAGERCAR').checked ? "1" : "0";
	var ISFOREIGNAFFAIRS = $('ISFOREIGNAFFAIRS').checked ? "1" : "0";
	var ISPOLICE = $('ISPOLICE').checked ? "1" : "0";
	var ISMASSESCASE = $('ISMASSESCASE').checked ? "1" : "0";
	var ISCONGESTION = $('ISCONGESTION').checked ? "1" : "0";
	// 追加"其他"的checkbox
	var ISOthersItem = $('ISOthersItem').checked ? "1" : "0";
	// 追加"涉军"的checkbox
	var ISARMYACC = $('ISARMYACC').checked ? "1" : "0";

	//alert(ISFOREIGNAFFAIRS);
	var params = {};
	params["alarmIdValue"] = $('alarmIdValue') ? $('alarmIdValue').value : '';

	// 事件类别checkbox值的取得
	params["ISTHREEUP"] = ISTHREEUP;
	params["ISBUS"] = ISBUS;
	params["ISSCHOOLBUS"] = ISSCHOOLBUS;
	params["ISDANAGERCAR"] = ISDANAGERCAR;
	params["ISFOREIGNAFFAIRS"] = ISFOREIGNAFFAIRS;
	params["ISPOLICE"] = ISPOLICE;
	params["ISMASSESCASE"] = ISMASSESCASE;
	params["ISCONGESTION"] = ISCONGESTION;
	params["ISOthersItem"] = ISOthersItem;
	params["ISARMYACC"] = ISARMYACC;

	var titleInfo = "";
	if ($('TITLE')) {
		titleInfo = convertSql($('TITLE').value);
	}
	params["jgbh"] = $('jgbh').value;
	params["TITLE"] = $('TITLE') ? titleInfo : '';
	params["REPORTTIME"] = $('REPORTTIME') ? $('REPORTTIME').value : '';
	params["REPORTUNITIDVALUE"] = $('REPORTUNITIDVALUE') ? $('REPORTUNITIDVALUE').value
			: '';
	params["reportPersonName"] = $('reportPersonName') ? $('reportPersonName').value
			: '';
	params["TELENUM"] = $('TELENUM') ? $('TELENUM').value : '';
	params["caseHappenTime"] = $('caseHappenTime') ? $('caseHappenTime').value
			: '';
	params["caseHappenPlace"] = $('caseHappenPlace') ? $('caseHappenPlace').value
			: '';
	params["DEATHPERSONCOUNT"] = $('DEATHPERSONCOUNT') ? $('DEATHPERSONCOUNT').value
			: '';
	params["BRUISEPERSONCOUNT"] = $('BRUISEPERSONCOUNT') ? $('BRUISEPERSONCOUNT').value
			: '';
	var eventModifyInfo = "";
	if ($('TITLE')) {
		eventModifyInfo = convertSql($('eventModify').value);
	}
	params["eventModify"] = $('eventModify') ? eventModifyInfo : '';
	params["EVENTSOURCE"] = $('EVENTSOURCE') ? $('EVENTSOURCE').value : '';
	params["EVENTTYPE"] = $('EVENTTYPE') ? $('EVENTTYPE').value : '';
	params["ALARMUNIT"] = $('ALARMUNIT') ? $('ALARMUNIT').value : '';
	params["ALARMREGIONID"] = $('ALARMREGIONID') ? $('ALARMREGIONID').value
			: '';
	params["ALARMREGION"] = $('ALARMREGION') ? $('ALARMREGION').value : '';
	params["REPORTUNIT"] = $('REPORTUNIT') ? $('REPORTUNIT').value : '';
	params["glAccNum"] = $('glAccNum') ? $('glAccNum').value : '';
	// 道路名称
	params["alarmRoad_TrafficCrowd"] = $('alarmRoad_TrafficCrowd') ? $('alarmRoad_TrafficCrowd').value
			: '';
	
	params["ldbz"] = $("roadNote") ? $("roadNote").value : "";
	
	// 事故位置
	params["KMVALUE"] = $('KMVALUE') ? $('KMVALUE').value : '';
	params["MVALUE"] = $('MVALUE') ? $('MVALUE').value : '';
	params["flg"] = flg;

	//事故方向(高速公路有方向)
	params["RADIOTYPE"];
	if (RADIOTYPE) {
		params["RADIOTYPE"] = RADIOTYPE.value;
	} else {
		params["RADIOTYPE"] = '';
	}
	
	//管辖大队
	params["sobject_single"] = sobject_single;

	if (!validateInput())
		return;
	var TITLE = $('TITLE').value;
	if (!TITLE) {
		alert('请填写标题！');
		$('TITLE').focus();
		return;
	}
	
	if(sobject_single == "") {
		alert('请选择管辖大队！');
		$("sobject_single").focus();
		return;
	}
	
	var reportPersonName = $('reportPersonName').value;
	if (!reportPersonName) {
		alert('请填写填报人姓名！');
		$('reportPersonName').focus();
		return;
	}
	if ($('TELENUM')) {
		if (!$('TELENUM').value) {
			alert("请填写联系电话！");
			$('TELENUM').focus();
			return;
		}
	}
	if ($('caseHappenTime')) {
		if (!$('caseHappenTime').value) {
			alert("请填写事故时间！");
			$('caseHappenTime').focus();
			return;
		}
	}
	if ($('receivetime')) {
		if (!$('receivetime').value) {
			alert("请填写接警时间！");
			$('receivetime').focus();
			return;
		}
	}
	if ($('caseHappenPlace')) {
		if (!$('caseHappenPlace').value) {
			alert("请填写事故地点！");
			$('caseHappenPlace').focus();
			return;
		}
	}
	/*
	var regExp = new RegExp("^(0|([1-9][0-9]*))$");
	if($('KMVALUE')){
		if(!$('KMVALUE').value){
			alert("请填写事故位置千米值！");
			$('KMVALUE').focus();
			return;
		}else if(regExp.test($('KMVALUE').value)==false){
			$('KMVALUE').focus();
			$('KMVALUE').value = "";
			alert("事故位置千米值请输入数字！");
			return;
		}
	}
	
	if($('MVALUE')){
		if(!$('MVALUE').value){
			alert("请填写事故位置百米值！");
			$('MVALUE').focus();
			return;
		}else if(regExp.test($('MVALUE').value)==false){
			$('MVALUE').focus();
			$('MVALUE').value = "";
			alert("事故位置百米值请输入数字！");
			return;
		}
	}
	 */

	if ($("caseHappenPlace") && $("caseHappenPlace").value) {
		var regExp = new RegExp("^(0|([1-9][0-9]*))$");
		if ($('KMVALUE')) {
			if ($('KMVALUE').value && regExp.test($('KMVALUE').value) == false) {
				alert("事故位置千米值请输入数字！");
				$("roadselect").style.display = "block";
				$("KMVALUE").focus();
				return;
			}
		}

		if ($('MVALUE')) {
			if ($('MVALUE').value && regExp.test($('MVALUE').value) == false) {
				alert("事故位置百米值请输入数字！");
				$("roadselect").style.display = "block";
				$("MVALUE").focus();
				return;
			}
		}
	}

	if ($('DEATHPERSONCOUNT')) {
		if (!$('DEATHPERSONCOUNT').value) {
			alert("请填写死亡人数！");
			$('DEATHPERSONCOUNT').focus();
			return;
		}
	}
	if ($('BRUISEPERSONCOUNT')) {
		if (!$('BRUISEPERSONCOUNT').value) {
			alert("请填写受伤人数！");
			$('BRUISEPERSONCOUNT').focus();
			return;
		}
	}

	var regExp = /^\d+(\.\d+)?$/;
	if (regExp.test($('DEATHPERSONCOUNT').value)) {

	} else {
		alert("死亡人数必须是数字！");
		$('DEATHPERSONCOUNT').focus();
		return;
	}
	if (regExp.test($('BRUISEPERSONCOUNT').value)) {

	} else {
		alert("受伤人数必须是数字！");
		$('BRUISEPERSONCOUNT').focus();
		return;
	}
	if ($('eventModify')) {
		if (!$('eventModify').value) {
			alert("请填写情况描述！");
			$('eventModify').focus();
			return;
		} else if ($('eventModify').value.length >= 4000) {
			alert("请填写事故情况描述在4000字内！");
			$('eventModify').focus();
			return;
		}
	}
	// 取得checkbox值的check
	if (ISTHREEUP == '0' && ISBUS == '0' && ISSCHOOLBUS == '0'
			&& ISDANAGERCAR == '0' && ISFOREIGNAFFAIRS == '0'
			&& ISPOLICE == '0' && ISMASSESCASE == '0' && ISCONGESTION == '0'
			&& ISOthersItem == '0') {
		alert("请至少选择一种类型！");
		return;
	}

	// 判断死亡人数
	var DEATHPERSONCOUNT = $('DEATHPERSONCOUNT') ? $('DEATHPERSONCOUNT').value
			: '';
	if (DEATHPERSONCOUNT) {
		if ((ISTHREEUP == '0' && DEATHPERSONCOUNT >= 3)
				|| (ISTHREEUP == '1' && DEATHPERSONCOUNT < 3)) {
			alert("死亡人数与所选类型不符！");
			$('DEATHPERSONCOUNT').focus();
			return;
		}
	}

	if ($('jgbh').value.length == 4) {
		if ($('flow2')) {
			if (!$('flow2').value) {
				alert("请填写支队处警情况！");
				$('flow2').focus();
				return;
			} else if ($('flow2').value.length >= 500) {
				alert("请填写支队处置情况在500字内！");
				$('flow2').focus();
				return;
			}
		}
	}
	if ($('ddshr').style.display == "inline") {
		if (!$('ddapprover').value) {
			alert("请填写大队审核人！");
			$('ddapprover').focus();
			return;
		}
	}
	if ($('zdshr').style.display == "inline") {
		if (!$('zdapprover').value) {
			alert("请填写支队审核人！");
			$('zdapprover').focus();
			return;
		}
	}
	
	if(!validateInput()) {
		return;
	}

	//支警处警情况
	params["zdcjqk"] = $('flow2').value;
	
	//审核人
	var ddapprover = "";
	var zdapprover = "";
	if($('ddshr').style.display == "inline") {
		ddapprover = $('ddapprover').value;
	} else {
		ddapprover = "";
	}
	if($('zdshr').style.display == "inline") {
		zdapprover = $('zdapprover').value;
	}else {
		zdapprover = "";
	}
	params["ddapprover"] = ddapprover;
	params["zdapprover"] = zdapprover;
	params["zodapprover"] = "";
	
	//接警时间
	params["receivetime"] = $('receivetime').value
	
	var formObj = document.getElementById("alarmFileUploadForm");
	var fileObj = document.getElementById("mfile0");
	if (fileObj != null && fileObj != "undefined" && fileObj != "") {
		var fileName = fileObj.value;
		if (fileName != null && fileName != "" && fileName != "undefined") {
			formObj.submit();
		}
	}
	
	var messageShow = "信息报送成功！";
	if(flg == "0"){
		messageShow = "信息保存成功！";	
	}
	var url = "dispatch.alarmInfo.insertAlarmInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : function(xmlHttp) {
			messageShow = xmlHttp.responseText;
			window.location.href = window.location.href;
		}
	});

	alert(messageShow);
	window.location.reload(true);
}

function showResults(xmlHttp) {
	alert(xmlHttp.responseText);
	window.location.href = window.location.href;
}


/**
 * 打印生成Word文档<br/>
 * 
 */
function printWord1(daytime) {
	var bh = encodeURI($("ALARMIDVALUE") ? $("ALARMIDVALUE").value : " ");
	var jstime = encodeURI($("REPORTTIME") ? $("REPORTTIME").value : " ");
	var bsdw = encodeURI($("reportunitName") ? $("reportunitName").value : " ");
	var bsr = encodeURI($("reportPersonName") ? $("reportPersonName").value
			: " ");
	var jsdw = encodeURI($("REPORTUNITVALUE") ? $("REPORTUNITVALUE").value
			: " ");
	var zbrName = encodeURI($("zbrName") ? $("zbrName").value : " ");
	var TITLE = encodeURI($("TITLE") ? $("TITLE").value : " ");
	var jsr = encodeURI(" ");
	var ldps = encodeURI(" ");
	var desc = encodeURI($("eventModify") ? $("eventModify").value : " ");

	jsdw = "";
	window.open("printPattenDd.jsp?bh=" + bh + "&jstime=" + jstime + "&bsdw="
			+ bsdw + "&bsr=" + bsr + "&jsdw=" + jsdw + "&jsr=" + jsr + "&ldps="
			+ ldps + "&desc=" + desc + "&zbrName=" + zbrName + "&TITLE="
			+ TITLE);
}
function printWord() {
	var params = {};
	var jgmc = $("dname").value;
	var pjgmc = $("pjgmc").value;
	var name = $("zbrName").value;
	params.fsdw = jgmc;
	params.fszby = name;
	params.jsdw = pjgmc;
	params.jszby = "";
	params.title = $("TITLE").value;
	params.content = $("eventModify") ? $("eventModify").value : "";
	params.content = "<p style='margin:5 0;text-indent:24px;'>"
			+ params.content + "</p>";
	var url = "materialPrint.jsp";
	Submit.urlToForm(url, params);
}
function ocAddFile(tableId, counterId, maxNum) {
	var counter = $(counterId).value;
	var tableObj = $(tableId);
	var td = document.createElement("td");
	var tr = document.createElement("tr");
	var tbody = document.createElement("tbody");
	counter++;
	var html = "<INPUT TYPE=\"FILE\" size=\"50\" id=\"file\" style='border: 1px #7B9EBD solid' "
			+ counter
			+ "\" NAME=\"file"
			+ counter
			+ "\">"
			+ "&nbsp;<a href='#' onClick=\""
			+ "ocRemoveFile('"
			+ tableId
			+ "',this.parentNode.parentNode.parentNode);\"><input type='button' style='border: 1px #7B9EBD solid' value='删　　除'></a>";
	td.innerHTML = html;

	tr.appendChild(td);
	tbody.appendChild(tr);

	var tchild = tableObj.getElementsByTagName("tbody");
	var childNum = tchild.length;
	if (childNum <= maxNum) {
		//tableObj.insertBefore(tbody,tableObj.firstChild);
		tableObj.appendChild(tbody); //add file
		setValue(counterId, counter); //set counter++
	} else {
		return false;
	}
}

function ocRemoveFile(tableId, node) {
	var tableObj = $(tableId);
	tableObj.removeChild(node);
}
function setValue(objId, value) {
	try {
		$(objId).value = value;
	} catch (e) {
	}
}

function checkedBoxProcess() {
	var regExp = /^\d+(\.\d+)?$/;
	if (regExp.test($('DEATHPERSONCOUNT').value)) {
		if ($('DEATHPERSONCOUNT').value >= 3) {
			$("ISTHREEUP").checked = true;
		} else {
			$("ISTHREEUP").checked = false;
		}
	}
}

function initPage() {
	if ($('jgbh').value.length == 4) {
		$('flow2_box').style.display = "inline"
		$('flow2').value = ""
		$('zdshr').style.display = "inline";
	} else {
		if ($('jgbh').value.length == 6) {
			$('ddshr').style.display = "inline";
		}
		$('flow2_box').style.display = "none"
		$('flow2').value = ""
	}
	pageInit();
}

function onChangeValue() {
	if ($('alarmRoad_TrafficCrowd').value == "") {
		$('AcDir').style.display = "none";
	} else {
		$('AcDir').style.display = "inline";
	}

	if ($('roadLevelValueId').options[$('roadLevelValueId').selectedIndex].text != '高速公路') {
		$('AcDir').style.display = "none";
	}
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

var maxRoadLength = "0";
var minRoadLength = "0";

function showSetCheckValue(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0]
			.getElementsByTagName("col");
	maxRoadLength = results[0].text;
	minRoadLength = results[1].text;
	var roadid = $("alarmRoad_TrafficCrowd").value;
	var str = "广东省内" + roadid + "里程";
	if (maxRoadLength == "　" || minRoadLength == "　") {
		str += "数据未记录";
	} else {
		str += "为" + minRoadLength + "km 至" + maxRoadLength + "km";
	}
	$("showLength").innerHTML = str;
	//$("caseHappenPlace").value=roadid+ $("KMVALUE").value+ "k+" + $("MVALUE").value + "米处";
}

function setValue222() {
	var acplace = "";
	var roadNote = "";
	var roadfx = null;

	var regExp = new RegExp("^(0|([1-9][0-9]*))$");

	if ($('MVALUE')) {
		if ($('MVALUE').value && regExp.test($('MVALUE').value) == false) {
			alert("事故位置百米值请输入数字！");
			$("MVALUE").focus();
			return;
		}
	}

	//	if($("KMVALUE").value != "") {
	acplace = $("KMVALUE").value + "k+" + $("MVALUE").value + "米处";
	//	}
	if ($("roadNote").value != "") {
		roadNote = "(" + $("roadNote").value + ")"
	}
	if (Road.roadLevel.def != 4) {
		if ($('roadLevelValueId').options[$('roadLevelValueId').selectedIndex].text != "高速公路") {
			roadfx = "";
			RADIOTYPE = null;
			$("caseHappenPlace").value = $("alarmRoad_TrafficCrowd").value
					+ acplace + roadNote;
		} else {
			if ($('RADIOTYPE_1').checked) {
				roadfx = $('rdForward').innerText
				RADIOTYPE = $('RADIOTYPE_1')
			} else if ($('RADIOTYPE_2').checked) {
				roadfx = $('rdBack').innerText
				RADIOTYPE = $('RADIOTYPE_2')
			}
			roadfx = roadfx.replace("—>", "往")
			roadfx += "方向"
			$("caseHappenPlace").value = $("alarmRoad_TrafficCrowd").value
					+ roadfx + acplace + roadNote;
		}
	} else {
		roadfx = "";
		RADIOTYPE = null;
		$("KMVALUE").value = ""
		$("MVALUE").value = ""
		$("caseHappenPlace").value = $("roadNote").value;
	}
}

function showHingRoad() {
	if ($('highRoad').style.display == 'none') {
		$("highRoad").show();
	} else {
		$("highRoad").hide();
	}
}

function resetAllValue() {
	$("glAccNum").value = "";
	$("TITLE").value = "";
	$("caseHappenTime").value = "";
	$("caseHappenPlace").value = "";
	$("KMVALUE").value = "";
	$("MVALUE").value = "";
	$("DEATHPERSONCOUNT").value = "";
	$("BRUISEPERSONCOUNT").value = "";
	$("mfile0").value = "";
	$("eventModify").value = "";
	// 事件类别checkbox值的取得
	var ISTHREEUP = $('ISTHREEUP').checked = false;
	var ISBUS = $('ISBUS').checked = false;
	var ISSCHOOLBUS = $('ISSCHOOLBUS').checked = false;
	var ISDANAGERCAR = $('ISDANAGERCAR').checked = false;
	var ISFOREIGNAFFAIRS = $('ISFOREIGNAFFAIRS').checked = false;
	var ISPOLICE = $('ISPOLICE').checked = false;
	var ISMASSESCASE = $('ISMASSESCASE').checked = false;
	var ISCONGESTION = $('ISCONGESTION').checked = false;
	// 追加"其他"的checkbox
	var ISOthersItem = $('ISOthersItem').checked = false;
	// 追加"涉军"的checkbox
	var ISARMYACC = $('ISARMYACC').checked = false;
}
function onchanged() {
	var alarmRoadValue = $('alarmRoad_TrafficCrowd').options[$('alarmRoad_TrafficCrowd').selectedIndex].text;
	var roadLevelValueId = document.getElementById('roadLevelValueId');
	var roadLevelValue = roadLevelValueId.options[roadLevelValueId.selectedIndex].text;
	if ($('roadLevelValueId').options[$('roadLevelValueId').selectedIndex].text == "其他") {
		$('AcDir').style.display = "none";
	}
	if (roadLevelValue == "其他") {
		$('tips').style.display = "inline"
		$('roadNote').value = "";
		document.getElementById("accidentPlace").style.display = "none";
		document.getElementById("rn").style.display = "none";
		document.getElementById("ldbz").innerText = "事故地点：";
//		Modify by Xiayx 2011-08-25
//		清空事故千米值，后台会根据事故事故千米值来判断是否选择了“其他”
		var km = $("KMVALUE");
		if (km) {
			km.value = "";
		}
//		Modification finished
	} else {
		$('tips').style.display = "none"
		$('roadNote').value = "";
		$('KMVALUE').value = "";
		$('MVALUE').value = "0";
		document.getElementById("rn").style.display = "inline";
		document.getElementById("ldbz").innerText = "路        段：";
		document.getElementById("accidentPlace").style.display = "inline";
		document.getElementById("alarmRoad_TrafficCrowd_td").outerHTML;
	}
}

function checkNull() {
	var right = "true"
	if (document.getElementById("accidentPlace").style.display == "inline") {
		if ($('alarmRoad_TrafficCrowd_td')) {
			var alarmRoadId = document.getElementById('alarmRoad_TrafficCrowd');
			var alarmRoadValue = alarmRoadId.options[alarmRoadId.selectedIndex].text;
			if (alarmRoadValue == "请选择") {
				alert("请填写道路名称！");
				$('alarmRoad_TrafficCrowd').focus();
				right = "false"
				return;
			} else {
				right = "true"
			}
		}
	}

	if ($("accidentPlace").style.display == "inline") {
		if ($('KMVALUE') && $('roadNote')) {
			if ($('KMVALUE').value == "" && $('roadNote').value == "") {
				alert("事故位置或者路段必须填写其中一个！");
				right = "false"
				return;
			} else {
				right = "true"
			}
		}
	}

	if (document.getElementById("accidentPlace").style.display == "inline") {
		//		if ($('roadNote')) {
		//			if (!$('roadNote').value) {
		//				alert("请填写路段备注！");
		//				$('roadNote').focus();
		//				right = "false"
		//				return;
		//			} else {
		//				right = "true"
		//			}
		//		}
	} else {
		if ($('roadNote')) {
			if (!$('roadNote').value) {
				alert("请填写事故地点！");
				$('roadNote').focus();
				right = "false"
				return;
			} else {
				right = "true"
			}
		}
	}

	if (right == "true") {
		setValue222();
		RoadSelect.close('roadselect');
	}
}

//Modified by Leisx
function setSobjectSingle(){
	var jgid = $('jgid').value;
	var sql = "select jgid,jgmc from t_sys_department where substr(jgid,0,4)=substr(" + jgid + ",0,4) and jgid != '440000000000' and substr(jgid,5,2) != '00' order by jgid";
	fillListBox("sobject_single_div", "sobject_single", "200", sql, "请选择","setEmpty();");
}

function setEmpty(){
	var select = $("sobject_single");
	
//	if(select.options.length > 0){
//		select.removeChild(select.options[0]);
//	}
	
	if(select.length == 0){
		var option = document.createElement("option");
		option.value = "";
		select.add(option);
	}
	$("sobject_single_div").innerHTML = $("sobject_single_div").innerHTML + "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
//	$("sobject_single").options[$("sobject_single").selectedIndex].innerText = "";
//	alert($("sobject_single").options[$("sobject_single").selectedIndex].innerText)
}
//Modification finished

var Road = {
	init : true,
	roadLevel : {
		def : "1",
		init : function(def) {
			var labels = [ "高速公路", "国道", "省道", "其他" ];
			var codes = [ 1, 2, 3, 4 ];
			var select = $("roadLevelValueId");
			for ( var i = 0; i < labels.length; i++) {
				var option = document.createElement("option");
				option.label = labels[i];
				option.value = codes[i];
				select.add(option);
			}
			var options = select.options;
			for ( var i = 0; i < options.length; i++) {
				options[i].innerText = options[i].label;
			}
			if (def) {
				select.value = def;
			} else if (Road.roadLevel.def) {
				select.value = Road.roadLevel.def;
			}
		},
		onchange : function(e) {
			Road.roadLevel.def = e.value;
			Road.roadName.init(e.value);
		}
	},
	roadName : {
		level : "1",
		defs : [ "", "", "" ],
		init : function(level, def) {

			if (level != "4") {
				var cid = "alarmRoad_TrafficCrowd_td";
				var roadName = "alarmRoad_TrafficCrowd";
				var dlmc = [ "gbdm||':'||dlmc", "dlmc", "dlmc", "dlmc" ];
				if (!level) {
					level = Road.roadName.level;
				}
				var sql = "select dlmc id, " + dlmc[Number(level) - 1]
						+ " mc  " + "from T_OA_DICTDLFX where roadlevel='"
						+ level + "' order by mc";
				fillListBox(cid, roadName, "160", sql, "请选择",
						"Road.roadName.callBack('" + roadName + "','" + def
								+ "')", "onChangeValue");
			}
		},
		callBack : function(id, def) {
			$("alarmRoad_TrafficCrowd_td").innerHTML = $("alarmRoad_TrafficCrowd").outerHTML
					+ "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
			var select = $(id);
			if (select != null) {
				var options = select.options;
				if (options.length > 0) {
					//select.removeChild(select.options[0]);
				}
				if (def && def != "undefined") {
					select.value = def;
				} else {
					var def = Road.roadName.defs[Number(Road.roadName.level) - 1];
					if (def && def != "") {
						select.value = def;
					}
				}
				onChangeValue();
			}
		}
	}
	
}

function pageInit(level, def) {
	setSobjectSingle();
	Road.roadLevel.init(level);
	Road.roadName.init(level, def);
}

var RoadSelect = {
	show : function(sid, tid) {
		var rs = $(sid);
		if (rs) {
			if (rs.style.display == "none") {
				var tg = $(tid);
				if (tg) {
					var l = Event.getXY(tg, true);
					with (rs.style) {
						top = l.y + tg.offsetHeight;
						left = l.x;
					}
				}
				rs.style.display = "block";
			} else {
				rs.style.display = "none";
			}
		}
	},
	close : function(id) {
		var el = $(id);
		if (el) {
			el.style.display = "none";
		}
	}
}
//2011-08-31更新，雷适兴