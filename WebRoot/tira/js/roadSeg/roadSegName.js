function changeType() {
	if ($('type').value == "1") {
		$('forDate').style.display = "inline";
		$('forMonth').style.display = "none";
		$('forYear').style.display = "none";

		$('startDay').value = "";
		$('endDay').value = "";
		$('startMonth').value = "";
		$('endMonth').value = "";
	} else if ($('type').value == "2") {
		$('forDate').style.display = "none";
		$('forMonth').style.display = "inline";
		$('forYear').style.display = "none";

		$('startHours').value = "";
		$('startMonth').value = "";
		$('endMonth').value = "";
	} else {
		$('forDate').style.display = "none";
		$('forMonth').style.display = "none";
		$('forYear').style.display = "inline";

		$('startHours').value = "";
		$('startDay').value = "";
		$('endDay').value = "";
	}
}

function init() {
	setSegName();
}

function setSegName() {
	var sql = "select roadsegname, roadsegname from t_road_seginfo where roadid = 'SJKK'";
	fillListBox("segName_div", "segName", "150", sql, "请选择", "setEmpty();");
}

function setEmpty() {
	var select = $("segName");
	
	if (select.length == 0) {
		var option = document.createElement("option");
		option.value = "";
		select.add(option);
	}
}

function doStatistics() {
	var startDate = "";
	var endDate = "";
	var sd;
	var ed;

	if ($('type').value == "1") {
		if (!$("startHours").value) {
			alert("请选择统计日期！");
			$('startHours').focus();
			return;
		} else {
			startDate = $('startHours').value;
		}
	} else if ($('type').value == "2") {
		if (!$("startDay").value) {
			alert("请选择统计开始日期！");
			$('startDay').focus();
			return;
		} else {
			startDate = $('startDay').value;
		}

		if (!$("endDay").value) {
			alert("请选择统计结束日期！");
			$('endDay').focus();
			return;
		} else {
			endDate = $('endDay').value;
		}
		sd = new Date(startDate.substring(0, 4), startDate.substring(5, 7) - 1,
				startDate.substring(8, 10));
		ed = new Date(endDate.substring(0, 4), endDate.substring(5, 7) - 1,
				endDate.substring(8, 10));
		if (((ed.getTime() - sd.getTime()) / 24 / 3600 / 1000) + 1 > 40) {
			alert("统计天数超过40天，请重新选择！");
			$('endDay').focus();
			return;
		}

		if (endDate < startDate) {
			alert("开始时间不能大于结束时间！");
			$('startDay').focus();
			return;
		}
		;
	} else {
		if (!$("startMonth").value) {
			alert("请选择统计开始日期！");
			$('startMonth').focus();
			return;
		} else {
			startDate = $('startMonth').value;
		}

		if (!$("endMonth").value) {
			alert("请选择统计结束日期！");
			$('endMonth').focus();
			return;
		} else {
			endDate = $('endMonth').value;
		}

		if (endDate < startDate) {
			alert("开始时间不能大于结束时间！");
			$('startMonth').focus();
			return;
		}
		;

		sd = new Date(startDate.substring(0, 4), startDate.substring(5, 7) - 1,
				"01");
		ed = new Date(endDate.substring(0, 4), endDate.substring(5, 7) - 1,
				"01");
		if (ed.getYear() > sd.getYear()) {
			var vMonths = (12 * (ed.getYear() - sd.getYear())
					- (sd.getMonth() + 1) + ed.getMonth() + 2);
			if (vMonths > 25) {
				alert("统计月数超过25个月，请重新选择！");
				$('endMonth').focus();
				return;
			}
		}
	}

	var segName = "";
	if (!$("segName").value) {
		alert("请选择要统计的卡口！");
		$('segName').focus();
		return;
	} else {
		segName = $('segName').value;
	}

	var type = $('type').value;

	var url = "tira.statistical.getSingleSegFlow.d";
	url = encodeURI(url);
	var params = "startDate=" + startDate + "&endDate=" + endDate + "&segName="
			+ segName + "&type=" + type + "&flowType=" + $('flowType').value;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showResult
	});
}

function showResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == "没有取到考核信息") {
		alert(xmlDoc);
	} else {
		showStr = xmlDoc.split("+");
		var chart = new FusionCharts("../../swf/StackedColumn3D.swf",
				"showDivId", "800", "380", "0", "0");
		chart.setTransparent(true);
		chart.setDataXML(showStr[0]);
		chart.render("showDivId");
		$("showTable").innerHTML = showStr[1];
	}
}

function showExcel() {
	var startDate = "";
	var endDate = "";
	var sd;
	var ed;
	if ($('type').value == "1") {
		if (!$("startHours").value) {
			alert("请选择统计日期！");
			$('startHours').focus();
			return;
		} else {
			startDate = $('startHours').value;
		}
	} else if ($('type').value == "2") {
		if (!$("startDay").value) {
			alert("请选择统计开始日期！");
			$('startDay').focus();
			return;
		} else {
			startDate = $('startDay').value;
		}

		if (!$("endDay").value) {
			alert("请选择统计结束日期！");
			$('endDay').focus();
			return;
		} else {
			endDate = $('endDay').value;
		}
		sd = new Date(startDate.substring(0, 4), startDate.substring(5, 7) - 1,
				startDate.substring(8, 10));
		ed = new Date(endDate.substring(0, 4), endDate.substring(5, 7) - 1,
				endDate.substring(8, 10));
		if (((ed.getTime() - sd.getTime()) / 24 / 3600 / 1000) + 1 > 40) {
			alert("统计天数超过40天，请重新选择！");
			$('endDay').focus();
			return;
		}

		if (endDate < startDate) {
			alert("开始时间不能大于结束时间！");
			$('startDay').focus();
			return;
		}
		;
	} else {
		if (!$("startMonth").value) {
			alert("请选择统计开始日期！");
			$('startMonth').focus();
			return;
		} else {
			startDate = $('startMonth').value;
		}

		if (!$("endMonth").value) {
			alert("请选择统计结束日期！");
			$('endMonth').focus();
			return;
		} else {
			endDate = $('endMonth').value;
		}

		if (endDate < startDate) {
			alert("开始时间不能大于结束时间！");
			$('startMonth').focus();
			return;
		}
		;

		sd = new Date(startDate.substring(0, 4), startDate.substring(5, 7) - 1,
				"01");
		ed = new Date(endDate.substring(0, 4), endDate.substring(5, 7) - 1,
				"01");
		if (ed.getYear() > sd.getYear()) {
			var vMonths = (12 * (ed.getYear() - sd.getYear())
					- (sd.getMonth() + 1) + ed.getMonth() + 2);
			if (vMonths > 25) {
				alert("统计月数超过25个月，请重新选择！");
				$('endMonth').focus();
				return;
			}
		}
	}

	var segName = "";
	if (!$("segName").value) {
		alert("请选择要统计的卡口！");
		$('segName').focus();
		return;
	} else {
		segName = $('segName').value;
	}

	var type = $('type').value;

	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;

	// 分析处理方法的呼出
	var url = "tira.statistical.showExcel.d";
//	var params = {};
//	params["startDate"] = startDate;
//	params["endDate"] = endDate;
//	params["segName"] = segName;
//	params["selectItems"] = "";
//	params["type"] = type;
//	params["flowType"] = $('flowType').value;
//	params["flag"] = "1";
	
	var paramsValue = startDate + "&" + endDate + "&" + segName + "&" + "" + "&" + type + "&" + $('flowType').value + "&" + "1";
	urlToForm(url, paramsValue);
}

function urlToForm(url,paramj){
	var params = paramj.split("&");
	var form = document.createElement("form");
	form.action = url;
	form.method = "post";
	form.style.display = "none";
	document.body.appendChild(form);
	for (var i = 0; i < params.length; i++) {
		var input = document.createElement("input");
		input.type = "hidden";
		input.name = i;
		input.value = params[i];
		form.appendChild(input);
	}
	form.submit();
}

//function urlToForm(url,paramj){
//	alert(paramj["startDate"])
////	var form = document.createElement("form");
////	form.action = url;
////	form.method = "post";
////	form.style.display = "none";
////	document.body.appendChild(form);
////	for(var attr in paramj){
////		var input = document.createElement("input");
////		input.type = "hidden";
////		input.name = attr;
////		input.value = paramj[attr];
////		alert(paramj[attr])
////		form.appendChild(input);
////	}
////	form.submit();
//}