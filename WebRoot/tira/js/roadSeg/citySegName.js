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
    //Modified by Liuwx 2012-4-5 16:51:49
    //添加城市选择
    setCityName();
    getForward();
    setSegName();
    //Modification finished
}

function setSegName() {
//    var cityName = $('cityName');
//    if(cityName && cityName.value != ""){
//        var sql = "select distinct t1.roadsegName, t1.roadsegName from t_road_seginfo t1,t_tfm_driveway t2 where roadid != 'SJKK' and t1.roadsegid = t2.roadsegid and subStr(bianma,0,1) in('1','2') AND roadid = '" + cityName.value + "'";
//        fillListBox("segName_div", "segName", "250", sql, "请选择", "setEmpty();");
//    }else{
        var inner = "<select id='segName' class='text' style='width:250px'>";
        inner += "<option value=''>请选择</option>";
        inner += "</select>";
        document.getElementById("segName_div").innerHTML = inner;
//    }
}

function setEmpty() {
	var select = $("segName");
	
	if (select.length == 0) {
		var option = document.createElement("option");
		option.value = "";
        option.text = "请选择";
		select.add(option);
	}
    //Modified by Liuwx 2012-4-6 13:42:29
    var select = $('segName');
    select.attachEvent('onchange',getForward);
    //Modification finished
}

/**
 * Modified by Liuwx 2012-4-5 15:50:47
 * 添加城市选择
 */
function setCityName(){
    var sql = "SELECT TRI.ROADID, SUBSTR (tri.roadname, 0, 3) FROM t_road_info tri WHERE TRI.ROADID IN(SELECT T1.ROADID FROM t_road_seginfo t1, t_tfm_driveway t2 WHERE roadid != 'SJKK' AND t1.roadsegid = t2.roadsegid AND SUBSTR (bianma, 0, 1) IN ('1', '2'))";
    fillListBox("cityName_div","cityName","80",sql,"请选择","clearValue();");
}

function clearValue(){
    var select = $('cityName');

    if(select.length == 0){
        var option = document.createElement("option");
        option.value = "";
        select.add(option);
    }
    var select = $('cityName');
    select.attachEvent('onchange',changeSelectedValue);
}

function changeSelectedValue(){
    var sql = "select distinct t1.roadsegName, t1.roadsegName from t_road_seginfo t1,t_tfm_driveway t2 where roadid != 'SJKK' and t1.roadsegid = t2.roadsegid and subStr(bianma,0,1) in('1','2')"
    var select = $('cityName');
    var roadLevel = $('roadLevel');
    if(select && select.value != ""){
        sql += " AND roadid = '" + select.value + "'";
    }
    if(roadLevel && roadLevel.value != ""){
        sql += " AND t1.roadlevel = '" + roadLevel.value + "'";
    }
    fillListBox("segName_div", "segName", "250", sql, "请选择", "setEmpty();");
}

//获取方向列表
function getForward(){
    var select = $('segName');
    if(select && select.value != ""){
        var sql = "select distinct substr(t2.bianma,0,1) forwardValue, (case substr(t2.bianma,0,1) when '1' then '入城流量' when '2' then '出城流量' end) forwardText from t_road_seginfo t1,t_tfm_driveway t2 where roadid != 'SJKK' and t1.roadsegid = t2.roadsegid and subStr(bianma,0,1) in('1','2') "
        sql += " AND t1.roadsegName like '%" + select.value + "%'";
        sql += " order by 1 asc ";
        fillListBox("forwardName_div", "flowType", "80", sql, "请选择", "finalCheck();");
    }else{
        var inner = "<select id='flowType' class='text' style='width:80px'>";
        inner += "<option value=''>请选择</option>";
        inner += "</select>";
        document.getElementById("forwardName_div").innerHTML = inner;
    }
}

function finalCheck(){
    var select = $('flowType');
    if(select.length == 0){
        var option = document.createElement("option");
        option.value = "";
        select.add(option);
    }else if(select.length == 3){
        var option = document.createElement("option");
        option.value = "3";
        option.text = "双向流量"
        select.add(option);
    }
    select.attachEvent('onchange',showValue);
}

//not used now.
function showValue(){
    var select = $('flowType');
//    alert(select.value);
}
//Modification finished

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

    var flowType = "";
	if (!$("flowType").value) {
		alert("请选择要统计的卡口方向！");
		$('flowType').focus();
		return;
	} else {
        flowType = $('flowType').value;
	}

	var type = $('type').value;

	var url = "tira.statistical.getCitySegFlow.d";
	url = encodeURI(url);
	var params = "startDate=" + startDate + "&endDate=" + endDate + "&segName="
			+ segName + "&type=" + type + "&flowType=" + flowType;
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
	
	var paramsValue = startDate + "&" + endDate + "&" + segName + "&" + "" + "&" + type + "&" + $('flowType').value + "&" + "4";
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