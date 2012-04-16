function init() {
	setSegName();
}

function setSegName() {
	var sql = "select roadsegname,roadsegname from t_road_seginfo where roadid = 'SJKK'";
	fillListBox("segName_div", "segName", "150", sql, "请选择", "setEmpty();");

	var url = "common.dropDown.createDropDown.d";
	url = encodeURI(url);
	var params = "sql=" + sql.replace(/=/g, "@");
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showDropDown
	});
}

function showDropDown(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	document.getElementById('dropDown_div').innerHTML = "<select id=\"dropDown\" name=\"dropDown\" multiple=\"multiple\" size=\"5\" style=\"width: 127;\">"
			+ xmlDoc + "</select>";
	$(document).ready(function() {
		// Options displayed in comma-separated list
		$("#dropDown").multiSelect({
			oneOrMoreSelected : '*',
			selectAllText : '全选',
			listHeight : 300
		});
	});
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
	var startDate = document.getElementById('startDate');
	var endDate = document.getElementById('endDate');
	var segName = $("#dropDown").selectedValuesString();
	if (segName != '') {
		segName = segName.replace(/\,/g, "','");
	}
	var flowType = document.getElementById('flowType').value;
	var sd;
	var ed;

	if (startDate.value == '') {
		alert("请选择统计开始日期！");
		startDate.focus();
		return;
	}

	if (endDate.value == '') {
		alert("请选择统计结束日期！");
		endDate.focus();
		return;
	}

	if (startDate.value > endDate.value) {
		alert("开始日期不能大于结束日期！");
		endDate.focus();
		return;
	}

	sd = new Date(startDate.value.substring(0, 4), startDate.value.substring(5,
			7) - 1, startDate.value.substring(8, 10));
	ed = new Date(endDate.value.substring(0, 4),
			endDate.value.substring(5, 7) - 1, endDate.value.substring(8, 10));
	if (((ed.getTime() - sd.getTime()) / 24 / 3600 / 1000) + 1 > 40) {
		alert("统计天数超过40天，请重新选择！");
		endDate.focus();
		return;
	}

	if (segName == '') {
		alert("请选择您要统计的卡口！");
		$("#dropDown").focus();
		return;
	}

	var url = "tira.statistical.getMutiSegFlow.d";
	url = encodeURI(url);
	var params = "startDate=" + startDate.value + "&endDate=" + endDate.value
			+ "&segName=" + segName + "&flowType=" + flowType;
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
		document.getElementById("showTable").innerHTML = showStr[1];
	}
}

function showExcel() {
	var startDate = document.getElementById('startDate');
	var endDate = document.getElementById('endDate');
	var segName = $("#dropDown").selectedValuesString();
	if (segName != '') {
		segName = segName.replace(/\,/g, "','");
	}
	var flowType = document.getElementById('flowType').value;
	var sd;
	var ed;

	if (startDate.value == '') {
		alert("请选择统计开始日期！");
		startDate.focus();
		return;
	}

	if (endDate.value == '') {
		alert("请选择统计结束日期！");
		endDate.focus();
		return;
	}

	if (startDate.value > endDate.value) {
		alert("开始日期不能大于结束日期！");
		endDate.focus();
		return;
	}

	sd = new Date(startDate.value.substring(0, 4), startDate.value.substring(5,
			7) - 1, startDate.value.substring(8, 10));
	ed = new Date(endDate.value.substring(0, 4),
			endDate.value.substring(5, 7) - 1, endDate.value.substring(8, 10));
	if (((ed.getTime() - sd.getTime()) / 24 / 3600 / 1000) + 1 > 40) {
		alert("统计天数超过40天，请重新选择！");
		endDate.focus();
		return;
	}

	if (segName == '') {
		alert("请选择您要统计的卡口！");
		$("#dropDown").focus();
		return;
	}

	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;
	// 分析处理方法的呼出
	var url = "tira.statistical.showExcel.d";
	var params = startDate.value + "&" + endDate.value + "&" + segName + "&"
			+ "" + "&" + "2" + "&" + flowType + "&" + "2";
	urlToForm(url, params);
}

function urlToForm(url, paramj) {
	var params = paramj.split("&");
	var form = document.createElement("form");
	form.action = url;
	form.method = "post";
	form.style.display = "none";
	document.body.appendChild(form);
	for ( var i = 0; i < params.length; i++) {
		var input = document.createElement("input");
		input.type = "hidden";
		input.name = i;
		input.value = params[i];
		form.appendChild(input);
	}
	form.submit();
}
