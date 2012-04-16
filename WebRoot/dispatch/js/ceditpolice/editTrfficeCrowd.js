var group = "new";
// update consegion accept department read state
// 更新拥堵接收单位查看状态为已查看
function updateCADRS() {
	var rpdcode = $("reportUnit_").value;
	var jgid = $("ALARMUNIT").value;
	var stype = "3";
	if (jgid.substring(2, 4) == "00" || (jgid.substring(2, 4) == rpdcode.substring(2, 4) && jgid.substring(4, 6) == "00")) {
		stype = "2";
	}

	var params = {
		id : $("ALARMID").value,
		stype : stype,
		state : "2",
		rppname : $("pname").value
	};
	Flow.updtState(params);
}

function showAcceptDept() {
	// 转发单位，总队 查看、处理
	// 不显示，保留功能
	// Modified by Liuwx 2011-06-23
	$("dispatchTr").show();
	// Modification finished
	// $("adTr").show();
	// $("nadTr").show();
}

/**
 * 根据警情编号，事件类型查询详细信息
 */
function getCrowdInfo(alarmId, jgbh) {
	if ($('page').value == 1) {
		$('relieve').style.display = "inline";
	}
	if ($('page').value == 2 || $('page').value == 1) {
		showAcceptDept();
	}

	if (alarmId != "") {// 查看、处理
		$("crowdAddPrompt").style.display = "none";
		var url = "crowd.crowdmanage.getCrowdInfo.d"
		url = encodeURI(url);
		params = "alarmid=" + alarmId;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showGetXMLResponse
		});
	} else {// 新增
		if ($('saveData_Accident')) {
			if (jgbh.length == 2) {
				$('saveData_Accident').value = " 发 布 "
			}
		}
		pageInit();
		$("crowdAddPrompt").style.display = "block";
		if (jgbh.length != 2) {
			$("innerStr").innerHTML = "";
			$("daduiTdId").innerHTML = "";
		}
		// CrowdModules.init();
	}
}

/**
 * 设置页面元素的读写性
 * 
 * @param {Object}
 *            page
 */
function setReadOnly(page) {
	if (Road.init == true) {
		Road.init = false;
	} else {
		return;
	}
	var roadLevel = $("roadLevelValueId");
	var roadName = $("alarmRoad_TrafficCrowd");
	var roadSecName = $("ROADNAME");
	var directions = document.getElementsByName("direction");
	var crowST = $("CaseHappenTime");
	var crowET = $("CaseEndTime");
	var kmS = $("KMVALUE");
	var mS = $("MVALUE");
	var kmE = $("EndKMVALUE");
	var crowdTypeFlg = $("crowdTypeFlg");
	var mE = $("EndMVALUE");
	var crowReasons = document.getElementsByName("reason");
	var writeUName = $("REPORTUNITVALUE");
	var writePName = $("REPORTPERSONVALUE");
	var writeTime = $("REPORTTIME");
	var ReceiveTime = $("ReceiveTime");
	var apname = $("apname");
	if (page == "") {
		roadLevel.disabled = false;
		roadName.disabled = false;
		roadSecName.disabled = false;
		for ( var i = 0; i < directions.length; i++) {
			directions[i].disabled = false;
		}
		crowST.disabled = false;
		crowET.disabled = false;
		kmS.disabled = false;
		mS.disabled = false;
		kmE.disabled = false;
		mE.disabled = false;
		for ( var i = 0; i < crowReasons.length; i++) {
			crowReasons[i].disabled = false;
		}
		writeUName.disabled = true;
		writePName.disabled = false;
		crowdTypeFlg.disabled = false;
		writeTime.disabled = true;
		if (ReceiveTime) {
			ReceiveTime.disabled = false;
		}
	} else if (page == "2") {
		roadLevel.disabled = true;
		roadName.disabled = true;
		roadSecName.disabled = true;
		for ( var i = 0; i < directions.length; i++) {
			directions[i].disabled = true;
		}
		crowST.disabled = true;
		crowET.disabled = true;
		kmS.disabled = true;
		mS.disabled = true;
		kmE.disabled = true;
		mE.disabled = true;
		for ( var i = 0; i < crowReasons.length; i++) {
			crowReasons[i].disabled = true;
		}
		writeUName.disabled = true;
		writePName.disabled = true;
		writeTime.disabled = true;
		crowdTypeFlg.disabled = true;
		if (ReceiveTime) {
			ReceiveTime.disabled = true;
		}
		apname.disabled = true;
	} else if (page == "1") {
		var cjgid = $("jgid").value;
		var rjgid = $("reportUnit_").value;
		writeUName.disabled = true;
		writeTime.disabled = true;
		// 如果不是填报单位或者拥堵已解除，不能修改
		if (cjgid != rjgid || crowdState != "570001") {
			roadLevel.disabled = true;
			roadName.disabled = true;
			roadSecName.disabled = true;
			for ( var i = 0; i < directions.length; i++) {
				directions[i].disabled = true;
			}
			crowST.disabled = true;
			crowET.disabled = true;
			kmS.disabled = true;
			mS.disabled = true;
			kmE.disabled = true;
			mE.disabled = true;
			for ( var i = 0; i < crowReasons.length; i++) {
				crowReasons[i].disabled = true;
			}
			writeUName.disabled = true;
			writePName.disabled = true;
			writeTime.disabled = true;
			crowdTypeFlg.disabled = true;
			apname.disabled = true;
			// Modified by Liuwx 2011-8-8
			if (ReceiveTime) {
				ReceiveTime.disabled = true;
			}
			// Modification finished
		}

	} else {
		alert("e->page:" + page);
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

var ls, le;
function showSetCheckValue(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0].getElementsByTagName("col");
	maxRoadLength = results[0].text;
	minRoadLength = results[1].text;
	ls = minRoadLength;
	le = maxRoadLength;
	// 取得道路名称
	var roadid = $("alarmRoad_TrafficCrowd").value;
	var str = "广东省内" + roadid + "里程";
	if (maxRoadLength == "　" || minRoadLength == "　") {
		str += "数据未记录";
	} else {
		str += "为" + minRoadLength + "km 至" + maxRoadLength + "km";
	}
	$("showLength").innerHTML = str;
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
	if(window.$init != true){
		window.$init = true;
		window.Publish && (window.$publish = Publish());
		window.CrowdJoin && (window.$crowdJoin = CrowdJoin().init());
	}
}

/**
 * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路信息 param: return: author：wkz date: 2010-4-14
 * version:
 */
function modify(insrtOrUpdt) {

	returnValue = "insertOrUpdate";

	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	var EVENTSOURCE = $('EVENTSOURCE');
	var EVENTTYPE = $('EVENTTYPE');
	var ALARMUNIT = $('ALARMUNIT');
	var TITLE = $('TITLE');
	var ALARMREGIONID = $('ALARMREGIONID');
	var ALARMREGION = $('ALARMREGION');
	var ROADNAME = $('ROADNAME');
	var KMVALUE = $('KMVALUE');
	var MVALUE = $('MVALUE');
	var EndKMVALUE = $('EndKMVALUE');
	var EndMVALUE = $('EndMVALUE');
	var CaseHappenTime = $('CaseHappenTime');
	var CaseEndTime = $('CaseEndTime');
	var REPORTUNIT = $('REPORTUNIT');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	var REPORTTIME = $('REPORTTIME');
	// 交警提示
	var remindInfo = $('remindInfoValue');
	// Modified by Liuwx 2011-08-08
	var ReceiveTime = $('ReceiveTime');
	// Modification finished
	// var CONGESTIONTYPE =
	// $('CONGESTIONTYPE_1').checked?$('CONGESTIONTYPE_1'):$('CONGESTIONTYPE_2');
	var CONGESTIONREASON = '';
	for (i = 1; i < 8; i++) {
		CONGESTIONREASON += $("reason_" + i).checked ? $("reason_" + i).value + " " : "";
	}

	var crowdTypeFlg = "";
	// 拥堵级别 "1" 中断
	if ($("crowdTypeFlg").checked) {
		crowdTypeFlg = "1";
	}

	// var daduiid = "";
	// 总队时取得拥堵管辖单位
	// 取消该功能
	// Modified by Liuwx 2011-8-8
	// if ($('jgbh').value.length == 2) {
	// if (insrtOrUpdt == '0') {
	// if ($('daduiId')) {
	// daduiid = $('daduiId').value;
	// }
	// if (daduiid == "") {
	// alert("总队上报拥堵时，必须录入管辖机构！");
	// return;
	// }
	// }
	// }
	// Modification finished

	$("saveData_Accident").disabled = true;

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
	params["MVALUE"] = MVALUE.value;
	params["EndKMVALUE"] = EndKMVALUE.value;
	params["EndMVALUE"] = EndMVALUE.value;
	params["CaseHappenTime"] = CaseHappenTime.value;
	params["CaseEndTime"] = CaseEndTime.value;
	params["REPORTUNIT"] = REPORTUNIT.value;
	params["REPORTPERSON"] = REPORTPERSON.value;
	params["REPORTTIME"] = REPORTTIME.value;
	params["CONGESTIONTYPE"] = "005002";
	params["CONGESTIONREASON"] = CONGESTIONREASON;
	// Modify by Xiayx 2011-08-16
	// 道路等级为4时，不录入道路方向
	var rl = $("roadLevelValueId").value;
	if (rl != 4) {
		var RADIOTYPE = null;
		if ($('RADIOTYPE_1').checked) {
			RADIOTYPE = $('RADIOTYPE_1');
		} else if ($('RADIOTYPE_2').checked) {
			RADIOTYPE = $('RADIOTYPE_2');
		} else if ($('RADIOTYPE_3').checked) {
			RADIOTYPE = $('RADIOTYPE_3');
		}
		params["RADIOTYPE"] = RADIOTYPE.value;
	} else {
		params["RADIOTYPE"] = $("direction_nostandard").value;
	}
	// Modify finished
	params["remindInfo"] = remindInfo.value;
	params["insrtOrUpdt"] = insrtOrUpdt;
	// params["daduiid"] = daduiid;
	params["crowdTypeFlg"] = crowdTypeFlg;
	params["jgbh"] = $('jgbh').value;
	// Modified by Liuwx 2011-08-08
	params["ReceiveTime"] = ReceiveTime.value;
	// Modify by xiayx 2012-3-18
	// 添加交警提示发布
	params["gzcs"] = $("gzcs").value;
	params["policeremind"] = $("areaPublish").value;
	// Modification finished
	params["apname"] = $("apname").value;
	if (UDload.result) {
		params["apath"] = UDload.result;
	}
	var url = "crowd.crowdmanage.modifyCrowdInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showModifyResult
	});
}

function send() {
	if (checkParam()) {
		UDload.cbfname = "modify('0')";
		UDload.upload();
	}
}

/**
 * 填报单位在处理界面，如果拥堵未解除则显示解除按钮
 */
function showRelieve() {
	var crowd = baseInfo.data.crowd;
	var isShow = baseInfo.pageType == "1" && crowd.state == "570001" && baseInfo.jgid == crowd.jgid;
	var rel = $("relieve");
	rel && (rel.style.display = isShow ? "inline" : "none");
}

function relieve() {
	doDeleteCrowd($("ALARMID").value);
}

function showDispatch(frpdcode, state, id) {
	var jgbh = $("jgbh").value;
	var page = $("page").value;
	if (page == "1" && state == "570001" && jgbh.length == 2 && frpdcode.substring(2, 4) != "00") {
		$("dispatch").style.display = "inline";
	}
}

function dispatch() {
	var rpdcode = $("reportUnit_").value;
	var parents = Dept.getParents(rpdcode);
	if (parents) {
		rpdcode += "," + parents;
	}
	Flow.showDispatch($("ALARMID").value, rpdcode, "jgmcId", 180, 224, null, "12");
}

var crowdState;
// -----------------------------------------------------------------
// 查看
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	// Modify by xiayx 2012-3-14
	// 解析XML格式数据成JSON格式，并存放在baseInfo的data属性中，
	// 然后初始化拥堵各新增组件
	baseInfo.parseXml(xmlDoc);
	// CrowdModules.init();
	// Modification finished
	var results = xmlDoc.getElementsByTagName("row")[0].getElementsByTagName("col");

	if ("0000" == results[20].text.substring(2, 6)) {
		$('tbdw').value = results[20].text.substring(0, 2);
	} else if ("00" == results[20].text.substring(4, 6)) {
		$('tbdw').value = results[20].text.substring(0, 4);
	} else {
		$('tbdw').value = results[20].text.substring(0, 6);
	}

	var ALARMID = $('ALARMID');

	// Modify by Xiayx 2011-10-18
	// 设置道路方向值
	var direction = results[3].text;
	if (direction == "0" || direction == "1" || direction == "2") {
		$("direction_standard_div").show();
		$("direction_nostandard_div").hide();
		$("RADIOTYPE_" + (parseInt(direction) + 1)).checked = true;
	} else {
		$("direction_standard_div").hide();
		$("direction_nostandard_div").show();
		$("direction_nostandard").value = direction;
	}
	// Modification finished

	var ROADNAME = $('ROADNAME');
	var KMVALUE = $('KMVALUE');
	var MVALUE = $('MVALUE');
	var EndKMVALUE = $('EndKMVALUE');
	var EndMVALUE = $('EndMVALUE');
	var CaseHappenTime = $('CaseHappenTime');
	// var crowETDesc = $("crowETDesc");
	var CaseEndTime = $('CaseEndTime');
	var CONGESTIONREASON;
	var REPORTUNIT = $('REPORTUNITVALUE');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	var REPORTTIME = $('REPORTTIME');
	// var RELIEVETIME = $('RELIEVETIME');
	// 拥堵级别 “中断”
	var crowdTypeFlg = $('crowdTypeFlg');

	ROADNAME.value = results[4].text;
	KMVALUE.value = results[5].text;
	EndKMVALUE.value = results[6].text;
	CaseHappenTime.value = results[7].text;
	CaseEndTime.value = results[8].text;
	CONGESTIONREASON = results[9].text;
	if (results[18].text == '1') {
		crowdTypeFlg.checked = true;
	} else {
		crowdTypeFlg.checked = false;
	}

	for (i = 1; i < 8; i++) {
		if (CONGESTIONREASON.indexOf($("reason_" + i).value) > -1) {
			$("reason_" + i).checked = true;
		}
	}

	// 填报单位名称
	if (results[16].text == "1") {
		REPORTUNIT.value = results[10].text;
		$("innerStr").innerHTML = "管辖机构";
		$("daduiTdId").innerHTML = '<input type="text" id="dsad" style="border: 1px #7B9EBD solid" disabled value=' + results[17].text
				+ '>';
	} else {
		REPORTUNIT.value = results[17].text;
		$("innerStr").innerHTML = "";
		$("daduiTdId").innerHTML = "";
	}
	REPORTPERSON.value = results[11].text;
	REPORTTIME.value = results[12].text;
	crowdState = results[13].text;
	// Modified by Liuwx 2011-08-05
	// Modified by Liuwx 2011-08-05
	var tnRelieveTime = $('tnRelieveTime');
	var tvRelieveTime = $("tvRelieveTime");
	var ReceiveTime = $('ReceiveTime');
	// Modification finished
	if (results[13].text == "570001") {
		// crowETDesc.innerText = "拥堵预计结束时间";
		// tnRelieveTime.innerText = "拥堵预计结束时间";
	} else if (results[13].text == "570002") {
		// crowETDesc.innerText = "拥堵结束时间";
		if (tnRelieveTime && tvRelieveTime) {
			$("tvReciveTime").colSpan = 1;
			tnRelieveTime.style.display = "inline";
			tnRelieveTime.innerText = "拥堵结束时间";
			tvRelieveTime.style.display = "inline";
		}
		if ($("saveData_Accident")) {
			$("saveData_Accident").style.display = "none";
		}
	} else {
		alert("e_p(eventstate):" + results[13].text);
	}
	// Modification finished
	// Modified by Liuwx 2011-08-05

	ReceiveTime.value = results[21].text.replace(/^　$/, '');
	var apath = results[16].text;
	var attachs = getAttachHTMLs(apath);
	if (attachs) {
		$("apathDescTd").innerText = "附件名称";
		$("fileRegion").innerHTML = attachs;
	} else {
		$("attachTr").style.display = "none";
	}
	// Modification finished

	MVALUE.value = results[14].text;
	EndMVALUE.value = results[15].text;

	var showInfo = "";
	var state = results[13].text;
	var remindResults;
	try {
		remindResults = xmlDoc.getElementsByTagName("crowdremind");
	} catch (ex) {
		remindResults = null;
	}

	if (remindResults != null) {
        try {
            showInfo = CrowdRemind().toTxts();
        } catch (e) {
            alert(e);
        }
		// 处理时，控制录入框和更新按钮
		if (baseInfo.pageType == "1") {
			// 拥堵中，填报单位可更新
			if (state == "570001" && results[20].text == baseInfo.jgid) {
				$("remindInfoAdd").style.display = "inline";
				$("saveData_Accident").style.display = "inline";
			} else {
				$("remindInfoAdd").style.display = "none";
				$("saveData_Accident").style.display = "none";
			}
		}
		$('remindInfo').innerHTML = showInfo;
	}
	// Modify by xiayx 2012-3-1
	// 添加后续情况-调整列宽
	var gzcs = $("gzcs");
	gzcs.cols = "72";
	gzcs.value = baseInfo.data.crowd.gzcs;
	if(baseInfo.jgid != baseInfo.data.crowd.jgid || baseInfo.pageType != "1"){
		gzcs.readOnly=true;
		gzcs.style.border="none";
		gzcs.style.overflow = "visible";
	}
	// Modification finished

	$("reportUnit_").value = results[20].text;

	pageInit(results[19].text, results[1].text);

	var showAdstate = function(adstate, cindexs, id) {
		var adscolors = [ "red", "green", "blue" ];
		var sep = "，";
		var adstateStr = "";
		adstate = adstate.childNodes;
		for ( var i = 0; i < cindexs.length && i < adstate.length; i++) {
			if (adstate[i + 1].text != "") {
				adstateStr += sep + "<span style='color:" + adscolors[cindexs[i]] + ";'>" + adstate[i + 1].text + "</span>";
			}
		}
		if (adstateStr != "") {
			adstateStr = adstateStr.substring(sep.length);
		}
		if (id) {
			$(id).innerHTML = adstateStr;
		}
		return adstateStr;
	}

	var adstates = xmlDoc.getElementsByTagName("adstate");
	if (adstates.length != 0) {
		showAdstate(adstates[0], [ 0, 1 ], "dispatchTd");
	} else {
		$("dispatchTd").innerText = "无";
	}

	/**
	 * Modified by Liuwx 2011-06-23 var cacptdepts =
	 * xmlDoc.getElementsByTagName("cacptdept"); if(cacptdepts != null &&
	 * cacptdepts.length == 1){ cacptdepts =
	 * cacptdepts[0].getElementsByTagName("col"); $("dispatchTd").innerText =
	 * cacptdepts[1].text||"没有转发单位"; $("nadTd").innerText =
	 * cacptdepts[3].text||"没有未接收单位"; $("adTd").innerText =
	 * cacptdepts[5].text||"没有已接收单位"; }else{ $("dispatchTd").innerText =
	 * "没有转发单位"; $("nadTd").innerText = "没有未接收单位"; $("adTd").innerText =
	 * "没有已接收单位"; } Modification finished
	 */

	// Modified by Liuwx 2011-06-23
	// 不显示，保留功能
	// $("dispatchTr").hide();
	// Modification finished
	showRelieve(results[20].text, results[13].text, results[0].text);
	// Modified by Xiayx 2011-08-08

	$("apname").value = results[22].text;
	var attachHtml = UDload.getAttachHtmls(results[23].text.replace(/　/g, ""));
	if (attachHtml) {
		$("apathDescTd").innerText = "附件名称";
		$("fileRegion").innerHTML = attachHtml;
	} else {
		$("attachTr").style.display = "none";
	}
	// Modification finished
	showDispatch(results[20].text, results[13].text, results[0].text);
	// Modify by xiayx 2012-3-14
	// 总队和填报单位不默认进行签收，仅支队及转发单位在处理界面签收
	// updateCADRS();
	if(baseInfo.pageType == "1" && baseInfo.jglx != "0" && baseInfo.jgid != baseInfo.data.crowd.jgid){
		updateCADRS();
	} 
	// Modification finished
	if(baseInfo.data.crowd.rlevel == "4"){
		if(window.$init != true){
			window.$init = true;
			window.Publish && (window.$publish = Publish());
			window.CrowdJoin && (window.$crowdJoin = CrowdJoin().init());
		}
	}
	$("close").focus();
}

/**
 * desc:查询 param: return: author：wxt date: 2010-1-16 v ersion:
 */
function doSearch(jgbh) {
	var num = 1;
	num = currPage;
	group = "search";
	var getDDddSql = function(dd) {
		return "(select " + dd + " from t_oa_dictdlfx where dlmc=taa.roadid)"
	};
	var sql = "select taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION," + "taa.roadid,tcc.CONGESTIONREASON,"
			+ "to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') REPORTTIME," + "taa.eventstate," + getDDddSql("begin") + ","
			+ getDDddSql("end") + ",taa.reportunit,'2' as stype, " + getDDddSql("roadLevel")
			// Modified by Liuwx 2011-8-8
			+ ", taa.ReceiveTime ";
	// Modification finished
	sql += " from T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc "
	sql += " where taa.ALARMID like '" + jgbh + "%' and taa.ALARMID=tcc.ALARMID ";
	// Modifieb by Liuwx 2011-07-06
	var sql2 = "SELECT taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION,taa.roadid,"
			+ "tcc.CONGESTIONREASON,TO_CHAR (taa.REPORTTIME, 'yyyy-mm-dd HH24:mi') REPORTTIME," + "taa.eventstate,"
			+ getDDddSql("begin") + "," + getDDddSql("end") + ",taa.reportunit,DECODE(ad.adid,null,'2','3') as stype, "
			+ getDDddSql("roadLevel")
			// Modified by Liuwx 2011-8-8
			+ ", taa.ReceiveTime ";
	// Modification finished
	sql2 += " FROM T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc,t_oa_acceptdept ad ";
	sql2 += " WHERE taa.alarmid = tcc.ALARMID AND taa.alarmid = ad.aid  " + " AND ad.rpdcode = '" + $("jgid").value + "' ";
	// Modification finished
	if ($('sj1').value != "" && $('sj2').value != "") {
		sql += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + " 00:00"
				+ "' AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modifieb by Liuwx 2011-07-06
		sql2 += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + " 00:00"
				+ "' AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modification finished
	} else if ($('sj1').value != "" && $('sj2').value == "") {
		sql += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + " 00:00'";
		// Modifieb by Liuwx 2011-07-06
		sql2 += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + " 00:00'";
		// Modification finished
	} else if ($('sj1').value == "" && $('sj2').value != "") {
		sql += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modifieb by Liuwx 2011-07-06
		sql2 += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modification finished
	}
	// Modify by Xiayx 2011-9-15
	// 机构筛选时，包含本单位及其所有子单位
	// var unit = $("AlarmUnit").value;
	// if (unit != "") {
	// if (typeof jgbh == "string" && jgbh.length == 6) {
	// sql += " and instr('" + unit + "',taa.ALARMREGION)!=-1";
	// //Modifieb by Liuwx 2011-07-06
	// sql2 += " and instr('" + unit + "',taa.ALARMREGION)!=-1";
	// //Modification finished
	// } else {
	// sql += " and taa.ALARMREGION like '%" + unit + "%'";
	// //Modifieb by Liuwx 2011-07-06
	// sql2 += " and taa.ALARMREGION like '%" + unit + "%'";
	// //Modification finished
	// }
	// }
	if (window.G_jgID) {
		var length;
		if (G_jgID.substring(2, 4) == "00") {
			length = 2;
		} else if (G_jgID.substring(4, 6) == "00") {
			length = 4;
		} else {
			length = 6;
		}
		sql += " and '" + G_jgID.substring(0, length) + "'=substr(taa.reportunit,1," + length + ")";
		sql2 += " and '" + G_jgID.substring(0, length) + "'=substr(taa.reportunit,1," + length + ")";
	}
	// Modification finished
	// sql += $("AlarmUnit").value == "" ? "" : (" and taa.ALARMREGION like '%"
	// + $("AlarmUnit").value + "%'");
	sql += $("CONGESTIONTYPE").value == "全部" ? "" : (" and taa.eventstate = '" + $("CONGESTIONTYPE").value + "'");
	// Modifieb by Liuwx 2011-07-06
	sql2 += $("CONGESTIONTYPE").value == "全部" ? "" : (" and taa.eventstate = '" + $("CONGESTIONTYPE").value + "'");
	// Modification finished
	sql += $("reason").value == "全部" ? "" : (" and tcc.CONGESTIONREASON like '%" + $("reason").value + "%'");
	// Modifieb by Liuwx 2011-07-06
	sql2 += $("reason").value == "全部" ? "" : (" and tcc.CONGESTIONREASON like '%" + $("reason").value + "%'");
	// Modification finished
	sql += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%" + $("ROADNAME").value + "%'");
	// Modifieb by Liuwx 2011-07-06
	sql2 += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%" + $("ROADNAME").value + "%'");
	sql += " UNION " + sql2;
	sql += " ORDER BY REPORTTIME DESC";
	// Modification finished
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql), "showResultsPage", "10");
	if (num != 1 && num != 0) {
		sleep(250);
		onTurnToPage(num);
	}
}

function getDlfx(dldj, fx, begin, end) {
	fx = fx == "null" ? "" : fx;
	if (dldj == "null") {
		return fx;
	} else {
		if (fx == "0") {
			return begin + "->" + end;
		} else if (fx == "1") {
			return end + "->" + begin;
		} else if (fx == "2") {
			return "双向";
		}
	}
	return fx;
}

function showResultsPage(xmldoc) {
	var jgid = $("jgid").value;
	var rows = xmldoc.getElementsByTagName("row");
	var results = null;
	var appid = $("appid").value;
	var jgbh = $('jgbh').value;
	// var str = "<div style='text-align:center;width:100%;line-height:22px;
	// float:left;'><span class='currentLocationBold'>查询结果</span></div>";
	var str = "<div style='display:inline;'>"
			+ "<div style='text-align:center;width:80%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span>"
			+ "</div>" + "<div class='lsearch' style='float:right;'><a href='#' onclick=\"showExcel('" + jgbh
			+ "')\" class='currentLocation'>" + "<span class='lbl'>导出Excel</span></a></div>" + "</div>";
	str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='5%' class='td_r_b td_font'>序号</td>";
	str += "<td width='15%' class='td_r_b td_font'>填报单位</td>";
	str += "<td width='10%' class='td_r_b td_font'>道路名称</td>";
	str += "<td width='10%' class='td_r_b td_font'>路段名称</td>";
	str += "<td width='5%' class='td_r_b td_font'>方向</td>";
	// str += "<td width='10%' class='td_r_b td_font'>交通状况</td>";
	str += "<td width='10%' class='td_r_b td_font'>拥堵原因</td>";
	// str += "<td width='10%' class='td_r_b td_font'>起止里程</td>";
	str += "<td width='15%' class='td_r_b td_font'>报告时间</td>";
	// Modified by Liuwx 2011-06-22
	str += "<td width='7%' class='td_r_b td_font'>拥堵状态</td>";
	// Modification finishe
	str += "<td width='5%' class='td_r_b td_font'>查看</td>";
	if (appid == "1001") {
		str += "<td width='5%' class='td_r_b td_font'>处理</td>";
		// str += "<td width='10%' class='td_r_b td_font'>解除拥堵</td>";
	}
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		for ( var i = 0; i < rows.length; i++) {
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>" + (i + 1) + "</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>" + (results[1].text == "null" ? "" : results[1].text)
					+ "</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>" + (results[4].text == "null" ? "" : results[4].text)
					+ "</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>" + (results[2].text == "null" ? "" : results[2].text)
					+ "</td>";
			// str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"
			// + (results[3].text == "null" ? "" : results[3].text) + "</td>";
			// str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"
			// + (results[3].text == "0" ?
			// (results[8].text+"->"+results[9].text):(results[9].text+"->"+results[8].text))
			// + "</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"
					+ getDlfx(results[12].text, results[3].text, results[8].text, results[9].text) + "</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>" + (results[5].text == "null" ? "" : results[5].text)
					+ "</td>";
			// str += "<td width='10%' class='td_r_b td_font'
			// align=\'center\'>"+(results[6].text=="null"?"":results[6].text)+"</td>";
			// str += "<td width='10%' class='td_r_b td_font'
			// align=\'center\'>"+(results[7].text=="null"?"":results[7].text)+"</td>";
			// str += "<td width='10%' class='td_r_b td_font'
			// align=\'center\'>"+(results[8].text=="null"?"":results[8].text)+(results[9].text=="null"?"":results[9].text)+"</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>" + (results[6].text == "null" ? "" : results[6].text)
					+ "</td>";
			var uIName = "";
			if (results[7].text == "570001") {
				if (jgid == results[10].text) {
					uIName = "update.gif";
				} else if (jgid.substring(2, 4) == "00") {
					uIName = "update.gif";
				} else {
					uIName = "lock.png";
				}
			} else if (results[7].text == "570002") {
				uIName = "lock.png";
			}
			// Modified by Liuwx 2011-06-22
			var stateImage = "";
			if (results[7].text == "570001") {
				stateImage = "busyState.png";
			} else if (results[7].text == "570002") {
				stateImage = "workState.png";
			}
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/state/"
					+ stateImage + "\"></td>"
			// Modification finished

			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onTrafficClick('view','"
					+ results[0].text + "','" + results[7].text + "','" + results[10].text + "','" + results[11].text + "')\"></td>"
			if (appid == "1001") {
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/"
						+ uIName + "\" onClick=\"onTrafficClick('edit','" + results[0].text + "','" + results[7].text + "','"
						+ results[10].text + "')\"></td>"
				// str += "<td width='10%' class='td_r_b td_font'
				// align=\'center\'><input type=\"image\"
				// src=\"../../images/button/"
				// + dIName
				// + "\" onClick=\"onTrafficClick('delete','"
				// + results[0].text + "','" + results[7].text +
				// "','"+results[10].text+"')\"></td>";
			}
			str += "</tr>";
		}
	}
	str += "</table>";
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = str;
	Popup.prototype.hideTips();
}

/**
 * desc:新增或查询的跳转 param: return: author：wkz date: 2010-1-12 version:
 */
function onTrafficClick(itemId, itemValue, crowState, rpu, stype, flag) {
	var width = "900", height = "600";
	var jgbh = $('jgbh').value;
	var jgid = $("jgid").value;
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("TrafficCrowdInfoAdd.jsp?tmp=" + Math.random() + "&insrtOrUpdt=0", "",
				"dialogWidth:" + width + "px;dialogHeight:" + height + "px");

		if (flag == "all") {
			doSearchAll();
		} else if (flag == "yd") {
			doSearchCrowdInfo();
		} else {
			if (group == "search") {

				doSearch(jgbh);
			} else {
				doOnLoad();
			}
		}
	}
	if (id == "edit") {
		if (crowState == "570002") {
			alert("拥堵已解除，不能处理！");
			return;
		} else {
			if (jgid != rpu && jgid.substring(2, 4) != "00") {
				alert("不是拥堵填报单或者总队不能处理！");
				return;
			}
		}
		var returnValuestr = window.showModalDialog("TrafficCrowdInfoAdd.jsp?tmp=" + Math.random() + "&insrtOrUpdt=1&alarmId='"
				+ itemValue + "'" + "&trfficeCrowState=" + crowState, "", "dialogWidth:" + width + "px;dialogHeight:" + height + "px");

		if (flag == "all") {
			doSearchAll();
		} else if (flag == "yd") {
			doSearchCrowdInfo();
		} else {
			if (group == "search") {

				doSearch(jgbh);
			} else {
				doOnLoad();
			}
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("TrafficCrowdInfoAdd.jsp?tmp=" + Math.random() + "&insrtOrUpdt=2&alarmId='"
				+ itemValue + "'" + "&trfficeCrowState=" + crowState, "", "dialogWidth:" + width + "px;dialogHeight:" + height + "px");
		if (flag == "all") {
			doSearchAll();
		} else if (flag == "yd") {
			doSearchCrowdInfo();
		} else {
			if (group == "search") {

				doSearch(jgbh);
			} else {
				doOnLoad();
			}
		}

		// Modified by Liuwx 2011-07-10
		// 查看后更新为已经查看状态
		var params = {
			id : itemValue,
			state : "2",
			stype : stype
		};
		var pname = $("pname");
		if (pname && pname.value) {
			params.rpname = pname.value;
		}
		Flow.updtState(params);
		// Modification finished
	}
	if (id == "search") {
		currPage = 1;
		doSearch(itemValue);
	}
	if (id == "delete") {
		if (crowState == "570002") {
			alert("拥堵已解除，不能重复解除！");
			return;
		} else {
			if (jgid != rpu) {
				alert("不是拥堵填报单位不能解除！");
				return;
			}
		}
		doCrowdDelete(itemValue);

	}
}

/**
 * author:wkz desc:编辑交通拥堵信息 date:2010-3-15
 */
function doOnLoad() {
	group = "new";
	var jgbh = $("jgbh").value;
	var sql = "select taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION,";
	sql += " (select name from t_attemper_code where id=taa.eventstate),"
			+ "tcc.CONGESTIONREASON,to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi')";
	sql += " from T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc"
	sql += " where taa.ALARMID like '" + jgbh + "%' and taa.ALARMID=tcc.ALARMID";
	sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value
			+ " 00:00' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
	sql += " order by taa.REPORTTIME desc";
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql), "showResultsPage", "10");
}

function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc.indexOf('成功') > -1) {
		alert(xmlDoc);
		if ($(page.value == "")) {
			window.location.reload();
		} else {
			window.close();
		}
	}else if(xmlDoc.indexOf('Exist') > -1){
        alert('该记录已经存在，请不要填写重复的内容。');
        $("saveData_Accident").disabled = false;
    } else {
		alert('操作失败，请重试！');
	}
}

/**
 * desc:删除 param: return: author：wxt date: 2010-01-16 version:
 */
function doCrowdDelete(alarmid) {
	if (confirm("是否解除拥堵?")) { // 提示用户是否删除记录
		var relieve = $("relieve");
		if (relieve) {
			relieve.disabled = true;
		}
		var strUrl = "crowd.crowdmanage.deleteCrowdInfo.d"; // 把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "ALARMID=" + alarmid;
		new Ajax.Request(strUrl, {
			method : "post",
			parameters : params,
			onComplete : showResponseDelete
		});
	} else {
		return;
	}
}

function showResponseDelete(xmlHttp) {
	if (xmlHttp.responseText == 'true') {
		alert("解除成功！");
	} else {
		alert("解除失败!");
	}
	window.close();
}

function sleep(timeout) {
	window.showModalDialog("javascript:document.writeln('<script>window.setTimeout(function () { window.close(); }, " + timeout
			+ ");<\/script>');");
}
function showExcel(jgbh) {

	var sql = "select taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION," + "taa.roadid,tcc.CONGESTIONREASON,"
			+ "to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') REPORTTIME," + "taa.eventstate,dic.begin,dic.end,taa.reportunit "
			// Modified by Liuwx 2011-8-8
			+ ",taa.ReceiveTime ";
	// Modification finished
	sql += " from T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc,T_OA_DICTDLFX dic"
	sql += " where taa.ALARMID like '" + jgbh + "%' and taa.ALARMID=tcc.ALARMID and taa.roadid=dic.dlmc";
	// Modifieb by Leisx 2011-07-10
	var sql2 = "SELECT taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION,taa.roadid,"
			+ "tcc.CONGESTIONREASON,to_char (taa.REPORTTIME, 'yyyy-mm-dd HH24:mi') REPORTTIME,"
			+ "taa.eventstate,dic.begin,dic.end,taa.reportunit "
			// Modified by Liuwx 2011-8-8
			+ ",taa.ReceiveTime ";
	// Modification finished
	sql2 += " FROM T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc,t_oa_acceptdept ad,T_OA_DICTDLFX dic ";
	sql2 += " WHERE taa.alarmid = tcc.ALARMID AND taa.alarmid = ad.aid AND taa.roadid = dic.dlmc " + " AND ad.rpdcode = '"
			+ $("jgid").value + "' ";
	// Modification finished
	if ($('sj1').value != "" && $('sj2').value != "") {
		sql += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value
				+ " 00:00' AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modifieb by Leisx 2011-07-10
		sql2 += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value
				+ " 00:00' AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modification finished
	} else if ($('sj1').value != "" && $('sj2').value == "") {
		sql += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + " 00:00'";
		// Modifieb by Leisx 2011-07-10
		sql2 += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value + " 00:00'";
		// Modification finished
	} else if ($('sj1').value == "" && $('sj2').value != "") {
		sql += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modifieb by Leisx 2011-07-10
		sql2 += " AND to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value + " 23:59'";
		// Modification finished
	}

	var unit = $("AlarmUnit").value;
	if (unit != "") {
		if (typeof jgbh == "string" && jgbh.length == 6) {
			sql += " and instr('" + unit + "',taa.ALARMREGION)!=-1";
			// Modifieb by Leisx 2011-07-10
			sql2 += " and instr('" + unit + "',taa.ALARMREGION)!=-1";
			// Modification finished
		} else {
			sql += " and taa.ALARMREGION like '%" + unit + "%'";
			// Modifieb by Leisx 2011-07-10
			sql2 += " and taa.ALARMREGION like '%" + unit + "%'";
			// Modification finished
		}
	}
	// sql += $("AlarmUnit").value == "" ? "" : (" and taa.ALARMREGION like '%"
	// + $("AlarmUnit").value + "%'");
	sql += $("CONGESTIONTYPE").value == "全部" ? "" : (" and taa.eventstate = '" + $("CONGESTIONTYPE").value + "'");
	// Modifieb by Leisx 2011-07-10
	sql2 += $("CONGESTIONTYPE").value == "全部" ? "" : (" and taa.eventstate = '" + $("CONGESTIONTYPE").value + "'");
	// Modification finished
	sql += $("reason").value == "全部" ? "" : (" and tcc.CONGESTIONREASON like '%" + $("reason").value + "%'");
	// Modifieb by Leisx 2011-07-10
	sql2 += $("reason").value == "全部" ? "" : (" and tcc.CONGESTIONREASON like '%" + $("reason").value + "%'");
	// Modification finished
	sql += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%" + $("ROADNAME").value + "%'");
	// Modifieb by Leisx 2011-07-10
	sql2 += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%" + $("ROADNAME").value + "%'");
	sql += " UNION " + sql2;
	sql += " ORDER BY REPORTTIME DESC";
	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;

	var url = "crowd.crowdmanage.showExcel.d?" + "searchSql=" + sql;

	url = encodeURI(url);
	window.open(url, "", "height=500,width=800,top=" + top + ",left=" + left + ",toolbar=yes,menubar=yes,"
			+ "scrollbars=yes,resizable=yes,location=no,status=no");
}

function DDCallBack() {
	var jgbh = $('jgbh').value;
	// alert(jgbh+"字符串长度: "+jgbh.length);
	// alert("字符串长度: "+jgbh.length);
	if (jgbh.length == 4) {
		// 当前登录用户属于支队
		fillListBox("DD_DIV", "DD", "170", "select jgid,jgmc from t_sys_department where substr(jgid,0,4)='" + jgbh
				+ "' and substr(jgid,0,6)<>'" + jgbh + "00' order by jgid", "全部");
	}
	if (jgbh.length == 6) {
		// 当前登录用户属于大队
		$("DD").value = $('jgid').value;
		$("DD").disabled = true;
	}
}

function doCancelCrowd(alarmid) {
	if (confirm("\u60A8\u786E\u5B9A\u89E3\u9664\u6B64\u9879\u62E5\u5835\u5417?")) { // 提示用户是否删除记录
		var strUrl = "crowd.crowdmanage.cancelCrowdInfo.d"; // 把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "ALARMID=" + alarmid;
		new Ajax.Request(strUrl, {
			method : "post",
			parameters : params,
			onComplete : showResponseDelete
		});
	} else {
		return;
	}
}

function isCrowdOver() {
	var desc = $("tnRelieveTime").innerText;
	if (desc.indexOf("预计") == -1) {
		return false;
	}
	return true;
}

function printWord() {
	var alarmId = p_id;
	var time = p_time;
	var alarmTime = $("CaseHappenTime").value + "，";
	var alarmEndTime = isCrowdOver() ? "预计在" : "已于";
	alarmEndTime += $("CaseEndTime").value + "结束，";
	var alarmAddress = $("alarmRoad_TrafficCrowd").options[$("alarmRoad_TrafficCrowd").selectedIndex].innerText + $("ROADNAME").value
			+ "路段，从" + $("KMVALUE").value + "千米" + $("MVALUE").value + "米到" + $("EndKMVALUE").value + "千米" + $("MVALUE").value + "米处，";
	var rl = $("roadLevelValueId").value;
	if (rl != "4") {
		var directions = document.getElementsByName("direction");// 方向
		var direction = "";
		if (directions[0].checked == true) {
			direction = "rdForward";
		} else {
			direction = "rdBack";
		}
		direction = $(direction).innerText.replace("—>", "到") + "方向，";
	} else {
		direction = $("direction_nostandard").value;
	}
	var crowReason = "";
	var crowReasons = document.getElementsByName("reason");
	for ( var i = 0; i < crowReasons.length; i++) {
		if (crowReasons[i].checked == true) {
			crowReason += "、" + crowReasons[i].value;
		}
	}
	if (crowReason == "") {
		crowReason = "拥堵原因未明。";
	} else {
		crowReason = "拥堵原因包括" + crowReason.substring(1) + "。";
	}

	var alarmType = "交通拥堵";
	var content = isCrowdOver() ? "发生" + alarmType + "，" : "发生" + alarmType + "，交通拥堵";
	var fromUnit = $("REPORTUNITVALUE").value;
	var fromPerson = $("REPORTPERSONVALUE").value;
	var detailContent = alarmTime + alarmAddress + direction + content + alarmEndTime + crowReason;
	var params = "alarmId=" + alarmId + "&time=" + time + "&fromUnit=" + fromUnit + "&fromPerson=" + fromPerson + "&alarmType="
			+ alarmType + "&detailContent=" + detailContent;
	var form = document.createElement("form");
	form.action = "RoadBuildPrint.jsp";
	form.method = "post";
	form.style.display = "none";
	document.body.appendChild(form);
	params = params.split("&");
	for ( var i = 0; i < params.length; i++) {
		var item = params[i].split("=");
		var input = document.createElement("input");
		input.name = item[0];
		input.type = "text";
		input.value = item[1];
		form.appendChild(input);
	}
	form.submit();
}

function checkParam() {
	var roadName = $("alarmRoad_TrafficCrowd");
	if (roadName.value == "") {
		if ($("roadLevelValueId").value != "4") {
			alert("请选择道路！");
		} else {
			alert("请输入道路！");
		}
		roadName.focus();
		return false;
	}

	var roadSecName = $("ROADNAME");
	if (roadSecName.value == "") {
		alert("请输入路段备注！");
		roadSecName.focus();
		return false;
	}
	var regExp = new RegExp("^(0|([1-9][0-9]*))$");
	var kmS = $("KMVALUE");
	var mS = $("MVALUE");
	var kmE = $("EndKMVALUE");
	var mE = $("EndMVALUE");
	if ($('roadLevelValueId').value == "1") {

		if (kmS.value == "") {
			alert("请输入拥堵开始里程千米数！");
			kmS.focus();
			return false;
		}
		if (regExp.test(kmS.value) == false) {
			alert("拥堵开始里程千米数，请输入数字");
			kmS.value = "";
			kmS.focus();
			return false;
		}
		if(checkLocation(ls,le,"KMVALUE","拥堵开始里程千米数") == false){
			return false;
		}
		if (mS.value == "") {
			alert("请输入拥堵开始里程百米数！");
			mS.focus();
			return false;
		}
		if (regExp.test(mS.value) == false) {
			alert("拥堵开始里程百米数，请输入数字！");
			mS.value = "";
			mS.focus();
			return false;
		}
		if (kmE.value == "") {
			alert("请输入拥堵结束里程千米数！");
			kmE.focus();
			return false;
		}
		if (regExp.test(kmE.value) == false) {
			alert("拥堵结束里程千米数，请输入数字！")
			kmE.value = "";
			kmE.focus();
			return false;
		}
		if(checkLocation(ls,le,"EndKMVALUE","拥堵结束里程千米数") == false){
			return false;
		}
		if (mE.value == "") {
			alert("请输入拥堵结束里程百米数！");
			mE.focus();
			return false;
		}
		if (regExp.test(mE.value) == false) {
			alert("拥堵结束里程百米数，请输入数字！")
			mE.value = "";
			mE.focus();
			return false;
		}
        //Modified by Liuwx at 2012年3月16日19:56:59
		/*if ((Number(kmS.value)*1000 + Number(mS.value)) > (Number(kmE.value)*1000 + Number(mE.value))) {
			alert("拥堵开始里程不得大于拥堵结束里程！");
			kmS.focus();
			return false;
		}*/
        //Modification finished
	} else {
		if (regExp.test(kmS.value) == false) {
			if (kmS.value != "") {
				alert("拥堵开始里程千米数，请输入数字");
				kmS.value = "";
				kmS.focus();
				return false;
			}
		}
		if(checkLocation(ls,le,"KMVALUE","拥堵开始里程千米数") == false){
			return false;
		}
		if (regExp.test(mS.value) == false) {
			if (mS.value != "") {
				alert("拥堵开始里程百米数，请输入数字！");
				mS.value = "";
				mS.focus();
				return false;
			}

		}
		if (regExp.test(kmE.value) == false) {
			if (kmE.value != "") {
				alert("拥堵结束里程千米数，请输入数字！")
				kmE.value = "";
				kmE.focus();
				return false;
			}

		}
		if(checkLocation(ls,le,"EndKMVALUE","拥堵结束里程千米数") == false){
			return false;
		}
		if (regExp.test(mE.value) == false) {
			if (mE.value != "") {
				alert("拥堵结束里程百米数，请输入数字！");
				mE.value = "";
				mE.focus();
				return false;
			}
		}
        //Modified by Liuwx at 2012年3月16日19:56:59
		/*if ((Number(kmS.value)*1000 + Number(mS.value)) > (Number(kmE.value)*1000 + Number(mE.value))) {
			alert("拥堵开始里程不得大于拥堵结束里程！");
			kmS.focus();
			return false;
		}*/
        //Modification finished
	}

    //Modified by Liuwx at 2012年3月16日19:56:59
    if($("roadLevelValueId").value != "4"){
        if ((Number(kmS.value) * 1000 + Number(mS.value)) == (Number(kmE.value) * 1000 + Number(mE.value))) {
            alert("拥堵开始里程数不能与拥堵结束里程数相同！");
            kmE.focus();
            return false;
        }
    }
    if($('RADIOTYPE_1').checked){
        if ((Number(kmS.value) * 1000 + Number(mS.value)) > (Number(kmE.value) * 1000 + Number(mE.value))) {
            alert("该方向拥堵开始里程数不得大于拥堵结束里程数！");
            kmE.focus();
            return false;
        }
    }else if($('RADIOTYPE_2').checked){
        if ((Number(kmS.value) * 1000 + Number(mS.value)) < (Number(kmE.value) * 1000 + Number(mE.value))) {
            alert("该方向拥堵开始里程数不得小于拥堵结束里程数！");
            kmS.focus();
            return false;
        }
    }
    //Modification finished
	
//	Modify by Xiayx 2011-08-16
//	道路等级为4时，取消道路方向必填验证
	var rl = $("roadLevelValueId").value;
	if (rl != "4") {
		var directions = document.getElementsByName("direction");
		var isChecked = false;
		for ( var i = 0; i < directions.length; i++) {
			if (directions[i].checked == true) {
				isChecked = true;
				break;
			}
		}
		if (isChecked == false) {
			alert("请选择路段方向！");
			directions[0].focus();
			return false;
		}
		var crowST = $("CaseHappenTime");
		if (crowST.value == "") {
			alert("请输入拥堵开始时间！");
			crowST.focus();
			return false;
		}
	}
	// Modify finished
	var crowET = $("CaseEndTime");
	if (crowET.value == "") {
		alert("请输入拥堵预计结束时间！");
		crowET.focus();
		return false;
	} else if (crowET.value <= $("CaseHappenTime").value) {
		alert("拥堵预计结束时间小于或等于拥堵开起始时间！");
		crowET.focus();
		return false;
	}

	var crowReasons = document.getElementsByName("reason");
	isChecked = false;
	for ( var i = 0; i < crowReasons.length; i++) {
		if (crowReasons[i].checked == true) {
			isChecked = true;
			break;
		}
	}
	if (isChecked == false) {
		alert("请选择拥堵原因！");
		crowReasons[0].focus();
		return false;
	}
	var writeUName = $("REPORTUNITVALUE");
	if (writeUName.value == "") {
		alert("请输入填报单位！");
		writeUName.focus();
		return false;
	}
	var writePName = $("REPORTPERSONVALUE");
	if (writePName.value == "") {
		alert("请输入填报人姓名！");
		writePName.focus();
		return false;
	}
	var writeTime = $("REPORTTIME");
	if (writeTime.value == "") {
		alert("请输入填报时间！");
		writeTime.focus();
		return false;
	}

	// Modify by xiayx 2012-3-8
	//添加后续情况-参数验证
	var gzcs = $("gzcs");
	 if(gzcs.value == ""){
		 alert("请输入管制措施！");
		 gzcs.focus();
		 return false;
	 }
	// Modification finished

	// Modified by Liuwx 2011-07-27
	// 取消交警提示为必填项
	if ($("remindInfoValue")) {
		if ($("remindInfoValue").value == "") {
			alert("请输入交警提示！");
			$("remindInfoValue").focus();
			return false;
		}

		if ($("remindInfoValue").value.length > 500) {
			alert("输入的交警提示长度不能超过500个字符!");
			return false;
		}
	}

	// Modification finished

	if ($("jgmcId") && $("jgmcId").style.readOnly == false) {// 总队指定拥堵下发机构
		if ($("jgmcId").value == "") {
			alert("请选择拥堵下发机构！");
			$("remindInfoValue").focus();
			return false;
		} else if ($("jgmcId").value.indexOf("440000000000") != -1) {
			alert("请不要选择总队为接收机构！");
			$("acceptDeptImg").focus();
			return false;
		} else if ($("jgmcId").value.indexOf($("reportUnit_").value) != -1) {
			alert("请不要选择拥堵填报单位为接收机构！");
			$("acceptDeptImg").focus();
			return false;
		}
	}

	if (!validateInput()) {
		return;
	}

	return true;

}

Date.prototype.getTimes_ = function() {
	var encap = function(date) {
		date = "0" + date;
		return date.substr(date.length - 2);
	}
	var year = this.getFullYear();
	var month = this.getMonth() + 1;
	var day = this.getDate();
	var hour = this.getHours();
	var minute = this.getMinutes();
	var second = this.getSeconds();
	var times = [ year, month, day, hour, minute, second ];
	for ( var i = 1; i < times.length; i++) {
		times[i] = encap(times[i]);
	}
	return times;
}

// 时间格式化类型，1年、2月、3日、4时、5分、6秒，默认为3
Date.prototype.format_ = function(ft) {
	var time = null;
	if (ft >= 1 && ft <= 6) {
		var times = this.getTimes_();
		var sepItems = [ "", "-", "-", " ", ":", ":" ];
		var time = "";
		for ( var i = 0; i < ft; i++) {
			time += sepItems[i] + times[i];
		}
		time = time.substring();
	}
	return time;
}

// 1年2月3日4小时5分6秒
Date.prototype.changeTime_ = function(tunit, number, ft) {
	if (typeof number == "number" && tunit >= 1 && tunit <= 6) {
		var gettunits = [ this.getFullYear, this.getMonth, this.getDate, this.getHours, this.getMinutes, this.getSeconds ];
		var settunits = [ this.setFullYear, this.setMonth, this.setDate, this.setHours, this.setMinutes, this.setSeconds ];
		tunit--;
		this.getCurrent = gettunits[tunit];
		this.setCurrent = settunits[tunit];
		this.setCurrent(this.getCurrent() - number);
	}
	return this.format_(ft);
}

var DateUser = {
	setDateSE : function(dateStartId, dateEndId, differ) {
		var date = new Date();
		$(dateEndId).value = date.format_(5);
		$(dateStartId).value = date.changeTime_(3, differ, 5);
	}
}

/**
 * desc:删除 param: return: author：wxt date: 2010-01-16 version:
 */
function doDeleteCrowd(alarmid) {
	if (confirm("是否解除拥堵?")) { // 提示用户是否删除记录
		$("relieve").disabled = true;
		var strUrl = "crowd.crowdmanage.deleteCrowdInfo.d"; // 把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = {
			alarmid:alarmid,
			rtime : baseInfo.syntime.pattern("yyyy-MM-dd HH:mm"),
			"crowdremind.remindinfo":"交通拥堵已清除，路面恢复正常交通。",	
			"policeremind.remindinfo":$crowdJoin.toString(true)
		};
		new Ajax.Request(strUrl, {
			method : "post",
			parameters : params,
			onComplete : showDeleteCrowd
		});
	}
}
function showDeleteCrowd(xmlHttp) {
	var code = xmlHttp.responseXML.text;
	var results = {
		"true" : "成功",
		"false" : "失败"
	};
	var result = results[code];
	alert(result ? "解除" + result + "！" : "网络异常！");
	code == "true" ? window.close() : ($("relieve").disabled = false);
}

function onChange() {
	var zhiduiId = $('zhiduiId').value;
	if (zhiduiId != null && zhiduiId != "") {
		fillListBox(
				"daduiTdId",
				"daduiId",
				"163",
				"select jgid,jgmc from t_sys_department where substr(jgid,1,4)=substr('"
						+ zhiduiId
						+ "',1,4)  and jgid not in (\'446010000000\','"
						+ zhiduiId
						+ "',\'446011000000\',\'446012000000\',\'446013000000\',\'446014000000\',\'446110000000\',\'446111000000\',\'446112000000\',\'446113000000\',\'446114000000\',\'446210000000\',\'446211000000\',\'446212000000\',\'446310000000\',\'446311000000\',\'446312000000\',\'446313000000\',\'446314000000\') order by jgid",
				"请选择", "");
	}
	if (zhiduiId == "") {
		fillListBox(
				"daduiTdId",
				"daduiId",
				"163",
				"select jgid,jgmc from t_sys_department where substr(jgid,1,4)<>\'00\'  and jgid not in (\'440000000000\',\'446000000000\',\'446100000000\',\'446200000000\',\'446300000000\',\'446010000000\',\'446011000000\',\'446012000000\',\'446013000000\',\'446014000000\',\'446110000000\',\'446111000000\',\'446112000000\',\'446113000000\',\'446114000000\',\'446210000000\',\'446211000000\',\'446212000000\',\'446310000000\',\'446311000000\',\'446312000000\',\'446313000000\',\'446314000000\') order by jgid",
				"请选择", "");
	}
}

function resetPageValue() {
	$("alarmRoad_TrafficCrowd").value = "";
	$("ROADNAME").value = "";
	$("KMVALUE").value = "";
	$("MVALUE").value = "";
	$("EndKMVALUE").value = "";
	$("EndMVALUE").value = "";
	for (i = 1; i < 8; i++) {
		$("reason_" + i).checked = false;
	}
	$("remindInfoValue").value = "";
	$("crowdTypeFlg").checked = false;
}

// s:locationStart,e:locationEnd,lid:locationId,
// desc:description警情类型描述,事故、拥堵、施工占道
function checkLocation(s, e, lid, desc) {
	return true;
	var iscs = true;
	var k = Number($(lid).value);
	if (s != "　") {
		s = Number(s);
		if (k < s) {
			$(lid).focus();
			alert(desc + "小于" + s);
			return false;
		}
	}
	if (e != "　") {
		e = Number(e);
		if (k > e) {
			$(lid).focus();
			alert(desc + "大于" + e);
			return false;
		}
	}
	return iscs;
}

var flag1 = true;
function resetRadio() {
	$("crowdTypeFlg").checked = flag1;
	flag1 = !flag1;
}

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
			Road.roadName.init(e.value);
		}
	},
	roadName : {
		level : "1",
		defs : [ "", "", "", "" ],
		init : function(level, def) {
			var cid = "alarmRoad_TrafficCrowd_td";
			var roadName = "alarmRoad_TrafficCrowd";
			var dlmc = [ "gbdm||':'||dlmc", "dlmc", "dlmc", "dlmc" ];
			if (!level) {
				level = Road.roadName.level;
			}
			if (level != 4) {
				var sql = "select dlmc id, " + dlmc[Number(level) - 1] + " mc  " + "from T_OA_DICTDLFX where roadlevel='" + level
						+ "' order by mc";
				fillListBox(cid, roadName, "160", sql, "请选择", "Road.roadName.callBack('" + roadName + "','" + def + "')",
						"onChangeValue");
				if (!$("KMVALUE").value) {
					$("KMVALUE").value = "0";
				}
				if (!$("MVALUE").value)
					$("MVALUE").value = "0";
				if (!$('EndKMVALUE').value)
					$('EndKMVALUE').value = "0";
				if (!$('EndMVALUE').value)
					$('EndMVALUE').value = "0";
				$('trLocation').style.display = "inline";
				// $('fxycId').style.display = "inline";
				$("direction_standard_div").style.display = "inline";
				$("direction_nostandard_div").style.display = "none";
			} else {
				$("alarmRoad_TrafficCrowd_td").innerHTML = "<input id='" + roadName + "' name='rname' type='text' value='"
						+ (def || "") + "' onpropertychange='event.propertyName==\"value\" && window.$crowdJoin && $crowdJoin.valPublish(this)'>";
				$("KMVALUE").value = "";
				$("MVALUE").value = "";
				$('EndKMVALUE').value = "";
				$('EndMVALUE').value = "";
				$('trLocation').style.display = "none";
				// $('fxycId').style.display = "none";
				$("direction_standard_div").style.display = "none";
				$("direction_nostandard_div").style.display = "inline";
				setReadOnly($("page").value);
				window.$crowdJoin && $crowdJoin.setTextField($(roadName)).setTextField($("direction_nostandard")).valPublish();
			}
		},
		callBack : function(id, def) {
			$("alarmRoad_TrafficCrowd_td").innerHTML = $("alarmRoad_TrafficCrowd").outerHTML
					+ "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
			var select = $(id);
			if (select != null) {
				var options = select.options;
				if (options.length > 0) {
					select.removeChild(select.options[0]);
				}
				if (def && def != "undefined") {
					select.value = def;
				} else {
					var def = Road.roadName.defs[Number(Road.roadName.level) - 1];
					if (def) {
						select.value = def;
					}
				}
				onChangeValue();
				setReadOnly($("page").value);
			}
			// Modify by xiayx 2012-3-18
			// 在回调函数中设置发布内容
			!select.name && (select.name = "rname");
			window.$crowdJoin && $crowdJoin.valPublish(select);
			// Modification finished
		}
	}
}

function pageInit(level, def) {
	Road.roadLevel.init(level);
	Road.roadName.init(level, def);
}

// Modified by Liuwx 2011-08-05
function getAttachHTML(apath) {
	var attach;
	if (apath) {
		var fnindex = apath.lastIndexOf("/");
		var fname = apath.substring(fnindex + 1);
		attach = "<a href='#' style='margin-right:10px;'";
		attach += " title='" + fname + "' ";
		attach += " onclick='openWPS(\"" + apath + "\")' >";
		attach += fname + "</a>";
	}
	return attach;
}
function getAttachHTMLs(apath) {
	var attachs = "";
	if (apath) {
		apath = apath.replace(/\\/g, "/");
		apath = apath.split(";");
		for ( var i = 0; i < apath.length; i++) {
			attachs += getAttachHTML(apath[i]) || "";
		}
	}
	return attachs;
}
/**
 * * 显示附件信息的链接<br>
 */
function openWPS(fileName) {
	var widthv = 400;
	var heightv = 200;
	var xposition = (screen.availWidth - widthv) / 2;
	var yposition = (screen.availHeight - heightv) / 2;
	var feature = 'height=' + heightv + ',width=' + widthv + ',top=' + yposition + ',left=' + xposition
			+ ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';
	var param = "../FileDownload.jsp?fileName=" + fileName;
	param = encodeURI(param);
	window.open(param, "", feature);
}
// Modification finished

/**
 * 导出Excel
 */
function exportExcel() {
	var jgid = $("jgid").value;
	var sql = "select alarm.title,to_char(alarm.casehappentime, 'yyyy-mm-dd hh24:mi')"
			+ ",to_char(alarm.caseendtime, 'yyyy-mm-dd hh24:mi')"
			+ ",alarm.roadid,alarm.roadname,alarm.kmvalue,alarm.mvalue"
			+ ",alarm.endkmvalue,alarm.endmvalue"
			+ ",(select decode(alarm.roaddirection,'0',begin||'->'||end,'1',end||'->'||begin,'2','双向',alarm.roaddirection) from t_oa_dictdlfx where dlmc=alarm.roadid)"
			+ ",decode(alarm.eventstate,'570001','拥堵中','拥堵已结束')" + ",alarm.reportunit,alarm.alarmregion,alarm.reportperson"
			+ ",to_char(alarm.reporttime, 'yyyy-mm-dd hh24:mi'),alarm.ddapprover,alarm.zdapprover";
	sql += " from t_attemper_alarm alarm";
	sql += " where alarm.eventtype = '001002'" + " and (" + Department.siftChilds(jgid, "alarm.reportunit") + " or '" + jgid
			+ "' in (select rpdcode from t_oa_acceptdept where (aid=alarm.alarmid and adid is null) or adid = alarm.alarmid))";
	var sjgid = window.G_jgID;
	if (sjgid) {
		sql += " and " + Department.siftChilds(sjgid, "alarm.reportunit");
	}
	var stime = $("sj1").value;
	if (stime) {
		stime += " 00:00:00";
		sql += " and alarm.reporttime >= to_date('" + stime + "','yyyy-mm-dd hh24:mi:ss')"
	}
	var etime = $("sj2").value;
	if (etime) {
		etime += " 23:59:59";
		sql += " and alarm.reporttime <= to_date('" + etime + "','yyyy-mm-dd hh24:mi:ss')"
	}
	var state = $("CONGESTIONTYPE").value;
	if (state != "全部") {
		sql += " and alarm.eventstate='" + state + "'";
	}
	var rname = $("ROADNAME").value;
	if (rname) {
		sql += " and instr(alarm.roadid,'" + rname + "')!=0 ";
	}
	var reason = $("reason").value;
	if (reason != "全部") {
		sql += " and instr((select congestionreason from t_attemper_congestion where alarmid=alarm.alarmid),'" + reason + "')!=0";
	}
	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;
	var url = "crowd.crowdmanage.showExcel.d?" + "searchSql=" + sql;
	url = encodeURI(url);
	window.open(url, "", "height=500,width=800,top=" + top + ",left=" + left
			+ ",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");
}

function showMoreCondition() {
	if ($('moreConditionTr').visible()) {
		$('defalutTitleTr').show();
		$('moreConditionTr').hide();
		$('simpleButtomTd').show();
		$('moreButtomTr').hide();
		$('dlmc_lable').hide();
		$('ROADNAME').hide();
		$('tbdw_lable').hide();
		$('AlarmUnit').hide();
		$('tbdw_img').hide();
		// $('moreLable').innerHTML = "高级查询";
		clearElementValue();
	} else {
		$('defalutTitleTr').hide();
		$('moreConditionTr').show();
		$('simpleButtomTd').hide();
		$('moreButtomTr').show();
		$('dlmc_lable').show();
		$('ROADNAME').show();
		$('tbdw_lable').show();
		$('AlarmUnit').show();
		$('tbdw_img').show();
		// $('moreLable').innerHTML = "简单查询";
	}
}

function clearElementValue() {
	G_jgID = "";
	$('ROADNAME').value = "";
	$('AlarmUnit').value = "";
	$('CONGESTIONTYPE').options[1].selected = true;
	$('reason').options[0].selected = true;
}

//Modified by Liuwx 2012-3-29 10:54:12
/*
 * 是否显示计分栏及计分按钮
 * @param isScoring
 */
function showScoring(isScoring){
    if(isScoring == '1' || isScoring == '2'){
        $('scoringTR').style.display = 'inline';
        if(isScoring == '2'){
            $('scoringValueTD').disabled = false;
            $('scoringBtn').style.display = 'inline';
        }else{
            $('scoringValueTD').disabled = true;
            $('scoringBtn').style.display = 'none';
        }
    }else{
        $('scoringTR').style.display = 'none';
    }
}
/**
 * 信息计分或者不计分
 * @param isScoring
 */
function scoring(isScoring){
//do scoring...
}
//Modification finished