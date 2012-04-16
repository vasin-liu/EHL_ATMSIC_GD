
var chart = new Chart();

function Chart() {
	
	this.swfPath = "../../../FusionChartsV3.1/swf/";
	
	this.singleBarChart;
	this.singleBarChartId = "singleBarChartId";
	this.singleBarChartWidth = "750";
	this.singleBarChartHeight = "400";
	this.singleBarChartSWF = this.swfPath+"Column3D.swf";
	this.singleBarChartData = "";
	this.singleBarChartPId = "";
	
	this.multipleBarChart;
	this.multipleBarChartId = "multipleBarChartId";
	this.multipleBarChartSWF = this.swfPath+"MSColumn3D.swf";//MSBar3D.swf
	this.multipleBarChartWidth = "750";
	this.multipleBarChartHeight = "400";
	this.multipleBarChartData = "";
	this.multipleBarChartPId = "";
	
	this.singleLineChart;
	this.singleLineId = "singleLineId";
	this.singleLineSWF = this.swfPath+"Line.swf";
	this.singleLineWidth = "750";
	this.singleLineHeight = "400";
	this.singleLineData = "";
	this.singleLinePId = "";
	
	this.multipleLineChart;
	this.multipleLineId = "";
	this.multipleLineSWF = "";
	this.multipleLineWidth = "";
	this.multipleLineHeight = "";
	this.multipleLineData = "";
	this.multipleLinePId = "";

	this.pieChart;
	this.pieChartId = "pieChartId";
	this.pieChartSWF = this.swfPath+"Pie3D.swf";
	this.pieChartWidth = "750";
	this.pieChartHeight = "250";
	this.pieChartData = "";
	this.pieChartPId = "";

}

Chart.prototype.getSingleBarData = function(xmlDoc) {
	
}

Chart.prototype.getData = function() {
	
}

/**
 * 是否是有效的xml数据格式<br/>
 * 只能验证xml文件的格式，不能排除格式正确，但全部数据都为0的情况
 * @param {Object} xmlDoc
 * @return {TypeName} 
 */
Chart.prototype.isValiedXMLDoc = function(xmlDoc) {
	var nulldataxml = "";
	if (xmlDoc == undefined || xmlDoc == null || 
			xmlDoc.xml == undefined || xmlDoc.xml == nulldataxml) {
		Popup.prototype.hideTips();//别忘了导入引用js
		setTimeout(showPrompt,1);//避免提示框和进度加载条显示在一起
		return false;
	}
	return true;
}

Chart.prototype.showSingleBarChart = function(data,pId) {
	
	this.singleBarChartData = data;
	this.singleBarChartPId = pId;
	this.singleBarChart = Chart.prototype.showChart(this.singleBarChartId,this.singleBarChartSWF,
		this.singleBarChartWidth,this.singleBarChartHeight,this.singleBarChartData,
		this.singleBarChartPId);
}

Chart.prototype.showMultipleBarChart = function(data,pId) {
	this.multipleBarChartData = data;
	this.multipleBarChartPId = pId;
	this.multipleBarChart = Chart.prototype.showChart(this.multipleBarChartId,this.multipleBarChartSWF,
		this.multipleBarChartWidth,this.multipleBarChartHeight,this.multipleBarChartData,
		this.multipleBarChartPId);
}

Chart.prototype.showPieChart = function(data,pId) {
	this.pieChartData = data;
	this.pieChartPId = pId;
	this.pieChart = Chart.prototype.showChart(this.pieChartId,this.pieChartSWF,
		this.pieChartWidth,this.pieChartHeight,this.pieChartData,
		this.pieChartPId);
}


Chart.prototype.showChart = function(id,swf,width,height,data,pId) {
	if(baseCheck([data],false) == false) {
		return null;
	}
	var chart = new FusionCharts(swf,id,width,height,"0","0");
    chart.setDataXML(data);		   
    chart.render(pId);
    return chart;
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


function showPrompt() {
	alert("没有可以显示的数据！");
}














































