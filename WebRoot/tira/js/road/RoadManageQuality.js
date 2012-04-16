
function doAnalysis(isOnLoad) {
	var statType = $("statTypeSelectId").value;
	var dateStart = $("dateStartInputId").value;
	var dateEnd = $("dateEndInputId").value;
	var road = $("roadSelId");
	var roadCode = road.value;
	var roadDesc = road.options[road.selectedIndex].innerText;
	var analysisTarget = $("analysisTargetCodeSelId");
	var analysisTargetCode = analysisTarget.value;
	var roadLevel = $("roadLevelInputId").value;
	var analysisTargetDesc = analysisTarget.options[analysisTarget.selectedIndex].innerText;
	
	var params = "dateStart="+dateStart+"&dateEnd="+dateEnd+"&roadCode="+roadCode
				+"&roadDesc="+roadDesc+"&analysisTargetCode="+analysisTargetCode
				+"&analysisTargetDesc="+analysisTargetDesc+"&roadLevel="+roadLevel
				+"&statType="+statType;
	
	var url = "tira.RoadManageQualityAction.analysis.d";
	if(isOnLoad == undefined) {
		isOnLoad = false;
	}
	showMsg();
	new Ajax.Request(
		url,
		{
			method:"post",
			asyn:!isOnLoad,
			parameters:params,
			onComplete:showRoadResponse
		}
	);
}


function showRoadResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	if (!chart.isValiedXMLDoc(xmlDoc)) {
		return;
	}

	var multipleBarCharts = statAnalysis.getMultipleBarChart(xmlDoc);
	var tables = xmlDoc.getElementsByTagName("table");
	if (tables == null || tables.length == 0) {
		return;
	}
	var charts = [statAnalysis.multipleBarChart,statAnalysis.table];
	statAnalysis.createShowTable("showDivId",charts);
    chart.showMultipleBarChart(multipleBarCharts[0].xml,statAnalysis.multipleBarChart+"TdId");
	$(statAnalysis.table+"TdId").innerHTML = tables[0].xml;
}











