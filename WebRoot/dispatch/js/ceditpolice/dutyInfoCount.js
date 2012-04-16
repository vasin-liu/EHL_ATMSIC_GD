/**基本信息*/
var baseInfo;
/**设置基本信息*/
function setBaseInfo() {
	baseInfo = {
		jgid : $("cjgid").value,
		jgmc : $("cjgmc").value,
		jglx : $("cjglx").value,
		pname : $("cpname").value,
		appid : $("appid").value,
		time : $("ctime").value
	};
}
/**页面信息*/
var pageInfo;
//atype警情类型,sobject统计对象,stime起始时间,etime终止时间,area统计范围,sostyle统计对象筛选方式

function setQueryValue(){
	setDate();
	setSobjectStyle();
	setSobjectSingle();
	setSobjectMulti();
}

function queryCheck(){
	pageInfo = {};
	//日期
	var stime = $("stime");
	var etime = $("etime");
	if(stime.value > etime.value){
		alert("起始日期不能大于结束日期！");
		stime.focus();
		return false;
	}
	//统计方式
	var sostyle = $("sostyle");
	var sobject_multi = $("sobject_multi_jgid");
	if(sostyle.value == "6"){
		if(sobject_multi.value == ""){
			alert("请选择统计单位！");
			$("sobject_multi_img").focus();
			return false;
		}
	}
	
	pageInfo.sostyle = sostyle.value;
	if(pageInfo.sostyle == "4"){
		pageInfo.sobject = $("sobject_single").value||baseInfo.jgid;
		if(pageInfo.sobject.substring(2,4)!="00"){
			pageInfo.sostyle = "6";
		}
	}else if(pageInfo.sostyle == "6"){
		pageInfo.sobject = sobject_multi.value;
		pageInfo.sobject = pageInfo.sobject.substring(0,pageInfo.sobject.length-1);
		pageInfo.sobject = pageInfo.sobject.replace(/；/g,"','");
		pageInfo.sobject = "'" + pageInfo.sobject + "'";
	}
	
	var areas = document.getElementsByName("area"),area;
	for(var i=0;i<areas.length;i++){
		if(areas[i].checked){
			area = areas[i];
			break;
		}
	}
	if(area.value){
		pageInfo.area = area.value;
	}
	pageInfo.stime = $("stime").value;
	pageInfo.etime = $("etime").value;
	//Modified by Liuwx 2011-10-17
	pageInfo.sortItem = $("sortItem")?$("sortItem").value:"0"
	//Modification finished
	return true;
}

function query(){
	if(queryCheck()){
		var mname = "alarmType";
		if($("atype").value == "1"){
			mname = "accType";
		}
		var url = "dispatch.dutyInfo."+mname+".d";
		url = encodeURI(url);
		new Ajax.Request(url, {
			method : "post",
			parameters : pageInfo,
			onComplete : function(xmlHttp){
				parseChart(xmlHttp,"barId");
			}
		});
	}
}



/**统计对象筛选方式改变事件*/
function sostyleOnchange(el){
	if(el.value == "4"){
		$("sobject_single_div").show();
		$("sobject_multi_div").hide();
	}else if(el.value == "6"){
		$("sobject_single_div").hide();
		$("sobject_multi_div").show();
	}
}

/**设置日期*/
function setDate(){
	var date = new Date();
	$("etime").value= date.format_(3);
	date.setDate(1);
	$("stime").value = date.format_(3);
}

/**设置统计对象（单选方式）*/
function setSobjectSingle(){
	var jglx = baseInfo.jglx;
	var dvalue;
	if(jglx == "0"){
		dvalue = "所有支队";
	}else if(jglx == "1"){
		dvalue = "所有大队";
	}else{
		dvalue = "本大队";
	}
	jglx = parseInt(jglx)+1;
	var sql = "select jgid,jgmc from t_sys_department where jglx='"+jglx+"' and " + Department.siftChild(baseInfo.jgid);
	fillListBox("sobject_single_div", "sobject_single", "270", sql, dvalue,"setEmpty();query();");
}

function setEmpty(){
	var select = $("sobject_single");
	if(select.length == 0){
		var option = document.createElement("option");
		option.value = "";
		option.text = "本大队";
		select.add(option);
	}
}


/**设置统计对象（复选方式）*/
function setSobjectMulti(){
	Tree.jgid = "sobject_multi_jgid";
	Tree.jgmc = "sobject_multi_jgmc";
}

/**设置统计对象方式*/
function setSobjectStyle(){
	$("sobject_multi_div").hide();
}

function parseChart(xmlHttp, containerId) {
	var xmlDoc = xmlHttp.responseXML;
	var isSuccess = xmlDoc.getElementsByTagName("success");
	if (isSuccess != null && isSuccess.length == 1) {
		isSuccess = isSuccess[0].text;
		if (isSuccess == "true") {
			var charts = xmlDoc.getElementsByTagName("charts");
			if (charts != null && charts.length == 1) {
				charts = charts[0].childNodes;
				for ( var i = 0; i < charts.length && i < 1; i++) {
					var type = charts[i].getAttribute("chartType");
					showTable(charts[i],"datatab");
					showChart(type, charts[i].xml, "barId");
				}
			} else {
				alert("返回xml格式出错");//返回xml格式出错
			}
		} else if (isSuccess == "false") {
			alert("数据转换出错！");//数据转换出错
		} else {
			alert("返回xml格式出错");//返回xml格式出错
		}
	} else {
		alert("action取参数出错");//action取参数出错
	}
}


function showChart(type, xml, barid) {
	var swfPath = "../../../FusionChartsV3.1/swf/";
	var swfPost = ".swf";
	//图表类型到swf文件名的映射关联
	var typeSwf = {
		pie : "Pie3D",
		columnSingle : "Column3D",
		columnMulti : "MSColumn3D"
	};
	swfPath = swfPath + typeSwf[type] + swfPost;
	var chart = new FusionCharts(swfPath, "ChartId",
			"750", "450", "0", "0");
	chart.setTransparent(true);
	chart.setDataXML(xml);
	chart.render(barid);
}

function showTable(chart, id) {
	var caption = chart.getAttribute("caption");
	var dataset = chart.getElementsByTagName("dataset");
	var categories = chart.getElementsByTagName("category");
	var res = chart.getElementsByTagName("set");
	if (1 == 1) {
		var tableStr = "<tr class='titleTopBack'><td height='22' class='td_r_b' colspan='9'>"
				+ caption.replace("统计图","统计表") + "</td></tr>";
		if (dataset && dataset.length > 0) {
			tableStr += "<tr><td height='22' class='td_r_b' width=20%><span class='lbl'>单位</span></td>";
			var request = ($("atype").value=="0" && pageInfo.area=="1")?"<br>（高速公路、国省道）":"";
			var sname;
			for ( var i = 0; i < dataset.length; i++) {
				sname = dataset[i].getAttribute("seriesName");
				if(sname.indexOf("拥堵") != -1 || sname.indexOf("施工") != -1){
					sname += request;
				}
				tableStr += "<td height='22' class='td_r_b'><span class='lbl'>"
						+ sname
						+ "</span></td>";
			}
			tableStr += "<td height='22' class='td_r_b'><span class='lbl' style='width:50px;'> 总计 </span></td>";
			tableStr += "</tr>";
			var str1 = "";
			var str2 = "";
			var temp = null;
			var rowsum = 0;
			var colnum = new Array(dataset.length + 1);
			for ( var i = 0; i < categories.length; i++) {
				str1 = categories[i].getAttribute("label");
				tableStr += "<tr><td height='22' class='td_r_b'><span class='lbl'>"
						+ str1 + "</span></td>";
				for ( var j = 0; j < dataset.length; j++) {
					temp = res[(j * categories.length) + i]
							.getAttribute("value");
					tableStr += "<td height='22' class='td_r_b'><span class='lbl'>"
							+ temp + "</span></td>";
					rowsum += parseInt(temp)
					if (i == 0) {
						colnum[j] = parseInt(temp);
					} else {
						colnum[j] += parseInt(temp);
					}
				}
				if (colnum[colnum.length - 1]) {
					colnum[colnum.length - 1] += rowsum;
				} else {
					colnum[colnum.length - 1] = rowsum;
				}

				//总计
				tableStr += "<td height='22' class='td_r_b'><span class='lbl'>"
						+ rowsum + "</span></td>";
				rowsum = 0;
				tableStr += "</tr>";
			}
			//合计
			tableStr += "<tr><td height='22' class='td_r_b'><span class='lbl'>合计</span></td>";
			for ( var j = 0; j < colnum.length; j++) {
				tableStr += "<td height='22' class='td_r_b'><span class='lbl'>"
						+ colnum[j] + "</span></td>";
			}
			tableStr += "</tr>";
			var innerTable = "<table width='100%' border='0' cellpadding='0' onmouseover=\"changeto()\" onmouseout=\"changeback()\"  cellspacing='0'  class='table'>";
			innerTable += tableStr;
		} else {
			tableStr += "<tr><td height='22' class='td_r_b' colspan='5'><span class='lbl'>未查询到相关数据</span></td>";
		}
		innerTable += "</table>";
	}
	document.getElementById(id).innerHTML = innerTable;
}



window.attachEvent("onload",function(){
	setSortItems();
	setBaseInfo();
	setQueryValue();
});

//Modified by Liuwx 2011-10-17

function setSortItems(){
	var items = {"0":"交通事故","1":"交通拥堵","2":"占道施工","3":"其他","4":"总计"};
	if($("atype").value == "1"){
		items = {"0":"死亡3人及以上","1":"营运大客车","2":"校车","3":"危化品运输车","4":"涉港澳台及涉外","5":"总计"};
	}
	var sortItem = $("sortItem");
	sortItem.options.length = 0;
	var option;
	for(var attr in items){
		option = document.createElement("option");
		option.text = items[attr];
		option.value = parseInt(attr) + 1;
		sortItem.add(option);
	}
	var options = sortItem.options;
	options[0].selected = true;
	sortItem.onchange = function(){
		$("query").focus();
		query();
	}
}
//Modification finished
















