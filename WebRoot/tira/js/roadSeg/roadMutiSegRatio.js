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
	var checkBoxs = document.getElementsByName("days");
	var selectItems = "";
	for ( var i = 0; i < checkBoxs.length; i++) {
		if (checkBoxs[i].checked) {
			selectItems += checkBoxs[i].value;
			selectItems += ",";
		}
	}

	if (selectItems.substring(selectItems.length - 1, selectItems.length) == ',') {
		selectItems = selectItems.substring(0, selectItems.length - 1);
	}

	var startDate = document.getElementById('startDate').value;
	var segName = $("#dropDown").selectedValuesString();
	if (segName != '') {
		segName = segName.replace(/\,/g, "','");
	}
	var flowType = document.getElementById('flowType').value;

	if (startDate == '') {
		alert("请选择统计开始日期！");
		$('startDate').focus();
		return;
	}

	if (segName == '') {
		alert("请选择您要统计的卡口！");
		$("#dropDown").focus();
		return;
	}

	if (selectItems == "") {
		alert("请选择环比的天数！");
		return;
	}

	var url = "tira.statistical.getMutiSegFlowRatio.d";
	url = encodeURI(url);
	var params = "startDate=" + startDate + "&segName=" + segName
			+ "&flowType=" + flowType + "&selectItems=" + selectItems;
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
		document.getElementById("showTable").innerHTML = xmlDoc;
	}
}

function showExcel() {
	var checkBoxs = document.getElementsByName("days");
	var selectItems = "";
	for ( var i = 0; i < checkBoxs.length; i++) {
		if (checkBoxs[i].checked) {
			selectItems += checkBoxs[i].value;
			selectItems += ",";
		}
	}

	if (selectItems.substring(selectItems.length - 1, selectItems.length) == ',') {
		selectItems = selectItems.substring(0, selectItems.length - 1);
	}

	var startDate = document.getElementById('startDate').value;
	var segName = $("#dropDown").selectedValuesString();
	if (segName != '') {
		segName = segName.replace(/\,/g, "','");
	}
	var flowType = document.getElementById('flowType').value;

	if (startDate == '') {
		alert("请选择统计日期！");
		$('startDate').focus();
		return;
	}

	if (segName == '') {
		alert("请选择您要统计的卡口！");
		$("#dropDown").focus();
		return;
	}

	if (selectItems == "") {
		alert("请选择环比的天数！");
		return;
	}

	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;
	// 分析处理方法的呼出
	var url = "tira.statistical.showExcel.d";
	var params = startDate + "&" + "" + "&" + segName + "&" + selectItems
			+ "&" + "" + "&" + flowType + "&" + "3";
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
