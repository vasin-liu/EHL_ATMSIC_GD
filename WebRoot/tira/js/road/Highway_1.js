
function doAnalysis(isOnLoad) {
	var dateStart = $("dateStartInputId").value;
	var dateEnd = $("dateEndInputId").value;
	var highway = $("highwaySelId");
	var highwayCode = highway.value;
	var highwayDesc = highway.options[highway.selectedIndex].innerText;
	var analysisTarget = $("analysisTargetCodeSelId");
	var analysisTargetCode = analysisTarget.value;
	var analysisTargetDesc = analysisTarget.options[analysisTarget.selectedIndex].innerText;
	var params = "dateStart="+dateStart+"&dateEnd="+dateEnd+"&highwayCode="+highwayCode
				+"&highwayDesc="+highwayDesc+"&analysisTargetCode="+analysisTargetCode
				+"&analysisTargetDesc="+analysisTargetDesc;
	
	var url = "sa.HighwayAction.analysis.d";
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











