
function doAnalysis(isOnLoad) {
	var dateSE = $("dateSECodeSelId").value;
	var diedPersonNum = $("diedPersonNumCodeSelId").value;
	var deptId = dept.zdCode==null?dept.zodCode:dept.zdCode;
	var deptName = dept.getSelectOption(dept.zdSelId,deptId,true).innerText;
	var params = "dateSE="+dateSE+"&diedPersonNum="+diedPersonNum;
//	params += "&division="+divisionClone.divisionCode+"&divisionDesc="+divisionClone.divisionDesc;
	params += "&deptId="+deptId+"&deptName="+deptName;
	var url = "tira.AccountAction.analysis.d";
	showMsg();
	new Ajax.Request(
		url,
		{
			method:"post",
			asyn:!isOnLoad,
			parameters:params,
			onComplete:showAccidentResponse
		}
	);
}

function showAccidentResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	if (!chart.isValiedXMLDoc(xmlDoc)) {
		return;
	}

	var multipleBarCharts = statAnalysis.getMultipleBarChart(xmlDoc);
	var singleBarCharts = statAnalysis.getSingleBarChart(xmlDoc);
	var tables = xmlDoc.getElementsByTagName("table");
	var charts = [statAnalysis.multipleBarChart,statAnalysis.singleBarChart,statAnalysis.table];
	statAnalysis.createShowTable("showDivId",charts);
	if (multipleBarCharts != null) {
		chart.showMultipleBarChart(multipleBarCharts[0].xml,statAnalysis.multipleBarChart+"TdId");
	}
	if (singleBarCharts != null) {
		chart.showSingleBarChart(singleBarCharts[0].xml,statAnalysis.singleBarChart+"TdId");
	}
	if (tables != null) {
		$(statAnalysis.table+"TdId").innerHTML = tables[0].xml;
	}
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