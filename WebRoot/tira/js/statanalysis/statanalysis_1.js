/*
 *  1   2   3    4
 a  a1  a2  a3  a4
 b  a1  a2  a3  a4
 c  a1  a2  a3  a4
 d  a1  a2  a3  a4
 */
var statItemId = "statItemId";
var statItemDetailId = "statItemDetailId";
var floatItemId = "floatItemId";
var floatItemDetailId = "floatItemDetailId";
var dataItemId = "dataItemId";
var choices = [];

//function analysis() {

//	var floatItemDetail = document.getElementById("fxtype");
//	var selofxzqh = document.getElementById("xzqhId")
//	var dateStart = document.getElementById("dateStartId");
//	var dateEnd = document.getElementById("dateEndId");
//	var selofdate = dateStart.value + "," + (dateEnd==null?"null":dateEnd.value);
//	
//	var url = "tira.statAnalysis.analysis.d";
//	var params = "floatItemDetail="+floatItemDetail.value+"&selofxzqh="+selofxzqh.value+"&selofdate="+selofdate;
//	alert(params);
//	new Ajax.Request(url,{method:"post", parameters:params, onComplete:show});
//}

var barTitle = "barTitle";
var pieTitle = "pieTitle";
var tableTitle = "title";
var titleSpan = "titlespan";
var data = "data";
var col = "col";

/**
 * 显示统计数据
 * @param xmlhttp 异步请求
 */
function show(xmlhttp) {
	var xmldoc = xmlhttp.responseXML;
	if (xmldoc.xml == "") {
		alert("没有数据");
		return;
	}
	
	var barChart = createBarChart(xmldoc);
	var barChartId = "barChartId";
	var pies = createPieChart(xmldoc);
	var pieIds = [];
	var table = createTable(xmldoc);
	var tableId = "dataTableId";
	var showdata = "<table ><tr><td id=\""+barChartId+"\"></td></tr>";
	for (var i = 0; i < pies.length; i++) {
		pieIds[i] = "pieId" + (i+1);
		showdata += "<tr><td id=\""+pieIds[i]+"\"></td></tr>";
	}
	showdata += "<tr><td id=\""+tableId+"\"></td></tr>";
	showdata += "</table>";
	var show = document.getElementById("show");
	show.innerHTML = showdata;alert(show.innerHTML);
	var chart = new FusionCharts("../../swf/MSColumn3D.swf", "ChartId", "600", "350", "0", "0");	
    chart.setDataXML(barChart);
    chart.render(barChartId);
    for (var i = 0; i < pies.length; i++) {
    	chart = new FusionCharts("../../swf/Pie3D.swf", "ChartId", "600", "350", "0", "0");
	    chart.setDataXML(pies[i]);
	    chart.render(pieIds[i]);    
	}
    document.getElementById(tableId).innerHTML = table;
}

/**
 * 创建柱状图字符串
 * @param xmldoc xml数据
 * @return 符合报表显示格式的字符串
 */
function createBarChart(xmldoc) {
	var barTitle = xmldoc.getElementsByTagName("barTitle")[0].text;
	var datas = xmldoc.getElementsByTagName("data");
	
	var table = change(datas);
	var barChart = "<chart  caption=\""+barTitle+"\" shownames=\"1\" showvalues=\"0\" decimals=\"0\" baseFontSize='12'>";
	barChart += "<categories>";
	for (var i = 0; i < 1; i++) {
		for (var j = 1; j < table[i].length; j++) {
			barChart += "<category label=\""+table[i][j]+"\"/>";
		}
	}
	barChart += "</categories>";
	for (var i = 1; i < table.length; i++) {
		barChart += "<dataset seriesName=\""+table[i][0]+"\"  showValues=\"0\">";
		for (var j = 1; j < table[i].length; j++) {
			barChart += "<set value=\""+table[i][j]+"\"/>";
		}
		barChart += "</dataset>";
	}
	barChart += "</chart>";
	return barChart;
}

/**
 * 创建饼状图字符串
 * @param xmldoc xml数据
 * @return 符合报表显示格式的字符串
 */
function createPieChart(xmldoc) {
	//取得初始化数据
	//var xmldoc = xmlhttp.responseXML;
	var pieTitles = xmldoc.getElementsByTagName("pieTitle");
	var datas = xmldoc.getElementsByTagName("data");
	
	var table = change(datas);
	var pies = [];
	for (var i = 0; i < pieTitles.length; i++) {
		pies[i] = "<chart caption=\""+pieTitles[i].text+"\" palette=\"4\" decimals=\"0\" enableSmartLabels=\"1\" enableRotation=\"0\" " +
				"bgColor=\"99CCFF,FFFFFF\" bgAlpha=\"40,100\" bgRatio=\"0,100\" bgAngle=\"360\" " +
				"showBorder=\"0\" startingAngle=\"70\" baseFontSize='12'>";
		for (var j = 1; j < table[i+1].length; j++) {
			pies[i] += "<set label='"+table[0][j]+"' value='"+table[i+1][j]+"' />";
		}
		pies[i] += "</chart>";
	}
	return pies;
	
}

/**
 * 创建数据表
 * @param xmldoc xml数据
 * @return 数据表
 */
function createTable(xmldoc) {
	//取得初始化数据
	var barTitle = xmldoc.getElementsByTagName("barTitle")[0].text;
	var title = xmldoc.getElementsByTagName("dataTitle")[0].text;
	var datas = xmldoc.getElementsByTagName("data");
	var titlespan = datas[0].getElementsByTagName("col").length+1;
	//如果是自定义，浮动项为1，将其标题改为数量
	if(titlespan == "3") {
		datas[0].getElementsByTagName("col")[1].text = "数量";
	}
	
	var colnum = Number(titlespan);//列数
	var coltotal = [];//每列数据存放数组
	//初始化数组，排除列首和列尾
	for (var i = 0; i < colnum-2; i++) {
		coltotal[i] = [];
	}
	//拼table
	var table = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" "
		+" onmouseover=\"changeto()\" onmouseout=\"changeback()\" cellspacing=\"0\" "
		+" class=\"table\"  style=\"font-size:15px\">";
	table += "<tr  class=\"titleTopBack\"> "
		+"<td align=\"center\" height=\"22\" class=\"td_r_b\" colspan=\""+titlespan+"\">"+title+"</td>"
		+"</tr>";//标题
	//副标题
	table += "<tr>";
	var cols = datas[0].getElementsByTagName("col");
	for (var j = 0; j < cols.length; j++) {
		table += "<td class=\"td_r_b\">"+cols[j].text+"</td>";
	}
	table += "<td class=\"td_r_b\">总数</td>";//每行总计
	table += "</tr>";
	//主体
	for (var i = 1; i < datas.length; i++) {
		var cols = datas[i].getElementsByTagName("col");
		var rowtotal = 0;
		table += "<tr>";
		table += "<td class=\"td_r_b\">"+cols[0].text+"</td>";//列首
		//数据
		for (var j = 1; j < cols.length; j++) {
			table += "<td class=\"td_r_b\">"+cols[j].text+"</td>";
			rowtotal += Number(cols[j].text);
			coltotal[j-1][i-1] = Number(cols[j].text);
		}
		table += "<td class=\"td_r_b\">"+rowtotal+"</td>";//每行总计
		table += "</tr>";
	}
	//尾行
	table += "<tr>";
	table += "<td class=\"td_r_b\">总数</td>";
	var total = 0;
	//每列数据
	for (var i = 0; i < coltotal.length; i++) {
		var colTotal = 0;
		for (var j = 0; j < coltotal[i].length; j++) {
			colTotal += coltotal[i][j];
		}
		table += "<td class=\"td_r_b\">"+colTotal+"</td>";
		total += colTotal;
	}
	table += "<td class=\"td_r_b\">"+total+"</td>";//所有数据和
	table += "</tr>";
	table += "</table>";
	return table;
}

/**
 * 翻转表
 * @param datas xml数据
 * @return 数组
 */
function change(datas) {
	var temp = [];
	for (var i = 0; i < datas.length; i++) {
		var cols = datas[i].getElementsByTagName("col");
		for (var j = 0; j < cols.length; j++) {
			if (temp[j] == undefined) {
				temp[j] = [];
			}
			temp[j][i] = cols[j].text;
		}
	}
	return temp;
}

/**
 * ifValidPieChartDataXML<br/>
 * 检查饼状图的数据集是否全部为0
 * @param 饼状图dataxml
 * @return 布尔值
 */
function ifValidPCDX(sets) {
	var ifValid = false;
	var set;
	var attrs;
	for (var i = 0; i < sets.length; i++) {
		attrs = sets[i].attributes;
		if (attrs.length>1) {
			set = attrs.getNamedItem("value");
			if (set != null && set.nodeValue != "0") {
				ifValid = true;
				break;
			}
		}
	}
	return ifValid;
}

function ifValidDataXML(xmlDoc) {
	var nulldataxml = "";
	if (xmlDoc.xml == nulldataxml) {
		Popup.prototype.hideTips();//别忘了导入引用js
		setTimeout(showPromt,1);
		return false;
	}
	return true;
}

function showPromt() {
	alert("没有可以显示的数据！");
}

/**
 * 加载文件成xml数据
 * @param filepath 文件路径
 * @return xml数据 
 */
function loadxml(filepath) {
	if (filepath) {
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = false;
		//xmlDoc.loadXML("<?xml version='1.0' encoding='UTF-8'?><root>abc</root>");
	    xmlDoc.load(filepath);
	    return xmlDoc;
    }
	return null;
}