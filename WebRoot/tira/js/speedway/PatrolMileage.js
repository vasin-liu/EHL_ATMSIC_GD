
//初始化页面元素
function initPage() {
	
}


//初始化赋值
function initValue() {
	var areas = {
		yd : "粤东",
		yx : "粤西",
		yb : "粤北",
		zsj : "珠三角"
	};
	fillSelect("area", areas);
	fillSelect("dept", areas);
	setDateSE('dateS', 'dateE', 1);
}

function getParams1(params) {
	var paramj = null;
	var forms = document.forms;
	if (forms.length == 1) {
		var elements = forms[0].elements;
		if (elements.length > 0) {
			paramj = {};
			var element;
			for ( var i = 0; i < elements.length; i++) {
				paramj[elements[i].id] = elements[i].value;
			}
		}
	}
	return paramj;
}

//取得页面元素k->v
function getParams(params) {
	var paramj = {};
	for ( var i = 0; i < params.length; i++) {
		if ($(params[i]) && $(params[i]).value != undefined) {
			paramj[params[i]] = $(params[i]).value;
		} else {
			return null;
		}
	}
	return paramj;
}

//提交请求前的验证
function submitCheck(params) {
	return true;
}

//字符过滤
function filterChar(params) {

}

//提交请求
function submit(isAvg) {
	var params = [ "area", "dept", "dateS", "dateE" ];
	var params = getParams(params);
	if (params == null) {
		alert("页面参数项缺失！");
		return;
	}
	var isSuccess = submitCheck(params);
	if (isSuccess == true) {
		filterChar(params);
		var url;
		if(isAvg == "true"){
			if(params.dept == ""){
				url = "tira.patrolmileage.avgDept.d";
			}else{
				url = "tira.patrolmileage.avgTime.d";
			}
		}else if(isAvg == "false"){
			if(params.dept == ""){
				url = "tira.patrolmileage.totalDept.d";
			}else{
				url = "tira.patrolmileage.totalTime.d";
			}
		}else{
			alert("日你大爷！");
		}
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : submitResponse
		});
	}
}

//提交请求后的反馈结果
function submitResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var isSuccess = xmlDoc.getElementsByTagName("success");
	alert(xmlHttp.responseText);
	if(isSuccess.length == 1){
		var chartXML = xmlDoc.getElementsByTagName("columnMulti");
		if(chartXML.length == 0){
			chartXML = xmlDoc.getElementsByTagName("columnSingle");
			if(chartXML.length == 0){
				alert("未找到指定类型的报表数据！");
				return;
			}
		}
		chartXML = chartXML[0].firstChild.xml;
		
		var chart = new FusionCharts("../../../FusionChartsV3.1/swf/MSColumn3D.swf","mid","750","250","0","0");
	    chart.setDataXML(chartXML);		   
	    chart.render("showDivId");
	}else{
		alert("没有数据！");
	}
	
}


function createOption(label, value) {
	var option = document.createElement("option");
	option.label = label;
	option.value = value;
	return option;
}

function clearSelect(id) {
	var select = document.getElementById(id);
	if (select != null) {
		while(select.length != 0){
			select.remove(0);
		}
	}
}

function fillSelect(id, data) {
	var select = document.getElementById(id);
	if (select != null) {
		select.add(createOption("全部", ""), select.length);
		for ( var attr in data) {
			var option = createOption(data[attr], attr);
			select.add(option, select.length);
		}
	}else{
		logger.error();
	}
}
