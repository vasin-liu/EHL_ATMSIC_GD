
var statAnalysis = new StatAnalysis();

function StatAnalysis() {
	this.singleBarChart = "singleBarChart";
	this.multipleBarChart = "multipleBarChart";
	this.singleLineChart = "singleLineChart";
	this.multipleLineChart = "multipleLineChart";
	this.pieChart = "pieChart";
	this.table = "table";
}

StatAnalysis.prototype.getPageParams = function(pageIdParams){
	if (baseCheck(pageIdParams,true) == false) {
		return null;
	}
	
}

StatAnalysis.prototype.createShowTable = function(pId,charts) {
	if (pId == undefined || pId == null || charts == undefined || !(charts instanceof Array)) {
		return null;
	}
	var table = "<table> "
					+"<tr>" +
						"<td class='height'>" +
						"</td>" +
					 "</tr>";
	for (var i = 0; i < charts.length; i++) {
		table += "<tr>" +
						"<td id='"+charts[i]+"TdId' align='center' > " +
						
						"</td>" +
				 "</tr>" +
				 "<tr>" +
						"<td class='height'>" +
							//"&nbsp;"+
						"</td>" +
				 "</tr>";
	}
	table += "</table>";
	$(pId).innerHTML = table;			
}

StatAnalysis.prototype.getMultipleBarChart = function(xmlDoc) {
	return this.getChart(xmlDoc,this.multipleBarChart);
}

StatAnalysis.prototype.getSingleBarChart = function(xmlDoc) {
	return this.getChart(xmlDoc,this.singleBarChart);
}

StatAnalysis.prototype.getPieChart = function(xmlDoc) {
	return this.getChart(xmlDoc,this.pieChart);
}

StatAnalysis.prototype.getChart = function(xmlDoc,remark) {
	if (remark == undefined || remark == null) {
		return null;
	}
	var chartEncaps = xmlDoc.getElementsByTagName(remark);
	
	if (chartEncaps.length != 1) {
		return null;
	}
	var chartEncap = chartEncaps[0];
	
	var charts = chartEncap.getElementsByTagName("chart");
	if (charts.length == 0) {
		return null;
	}
	return charts;
}






























