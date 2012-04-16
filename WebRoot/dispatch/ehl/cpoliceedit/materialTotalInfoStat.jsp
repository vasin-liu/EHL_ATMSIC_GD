<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

String alarmTotalType = request.getParameter("alarmTotalType");
String timeRadioType = request.getParameter("timeRadioType");
String STARTTIME = request.getParameter("STARTTIME");
String ENDTIME = request.getParameter("ENDTIME");
String startyearId = request.getParameter("startyearId");
String endyearId = request.getParameter("endyearId");
String startmonthId = request.getParameter("startmonthId");
String endmonthId = request.getParameter("endmonthId");
String monthSelect = request.getParameter("monthSelect");
String yearSelect = request.getParameter("yearSelect");
String depId = request.getParameter("depId");
String depName = request.getParameter("depName");
String jgbh = request.getParameter("jgbh");
String jgid = request.getParameter("jgid");
String departType = request.getParameter("departType");
String chartType = request.getParameter("chartType");
String title = request.getParameter("title");
String xDesc = request.getParameter("xDesc");
String yDesc = request.getParameter("yDesc");
String jgmcs = request.getParameter("jgmcs");

%>

<html>
  <head>
    <title></title>
	<link rel="stylesheet" type="text/css" href="css/Global.css"> 
	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
	<script type="text/javascript" src="../../../FusionChartsV3.1/JSClass/FusionCharts.js"></script>
	<link href="../../../FusionChartsV3.1/Style.css" rel="stylesheet" type="text/css" />
 	
  </head> 
  <body class="FootText" onload="getData()">
	<table width="100%" border="0" cellspacing="0" class="ContentText">
	  <tr>
		<td height="2%">&nbsp;
		</td>
	  </tr>
	  <tr>
		<td id="chartTdId" align="center">
		<script type="text/javascript">
			var title = "<%=title%>";
			var xDesc = "<%=xDesc%>";
			var yDesc = "<%=yDesc%>";
			var chartType = "<%=chartType%>";
			var jgmcs = "<%=jgmcs%>".split(",");
			function getData(){
				var params = {};
				params["alarmTotalType"] = "<%=alarmTotalType%>";
				params["timeRadioType"] = "<%=timeRadioType%>";
				params["STARTTIME"] = "<%=STARTTIME%>";
				params["ENDTIME"] = "<%=ENDTIME%>";
				params["startyearId"] = "<%=startyearId%>";
				params["startmonthId"] = "<%=startmonthId%>";
				params["endyearId"] = "<%=endyearId%>";
				params["endmonthId"] = "<%=endmonthId%>";
				params["monthSelect"] = "<%=monthSelect%>";
				params["yearSelect"] = "<%=yearSelect%>";
				params["depId"] = "<%=depId%>"=="undefined"?undefined:"<%=depId%>";
				params["depName"] = "<%=depName%>";
				params["jgbh"] = "<%=jgbh%>";
				params["jgid"] = "<%=jgid%>";
				params["departType"] = "<%=departType%>";
				if(params["departType"] == "3"){
					if(params["depId"] != undefined 
							&& params["depId"] != "null" 
							&& params["depId"] != "" ){
						params["depId"] = params["depId"].split(";").join("；");
						params["depName"] = params["depName"].split(";").join("；");
					}
				}
			  //var pstr = "";
			//	for(var attr in params){
			//		pstr += ";"+attr+":"+params[attr];
			//	}
			//	$("showDivId").innerText = pstr.substring(1);
				var url = "dispatch.cmaterialInfo.searchTotalInfo.d";
				url = encodeURI(url);
				new Ajax.Request(url, {method:"post", parameters:params, onComplete:showSearchTotalInfo});
			}
			function showSearchTotalInfo(xmlHttp) {
				var xmlDoc = xmlHttp.responseXML;
				if(xmlDoc == undefined || xmlDoc == null || xmlDoc.xml == ""){
					alert("没有可以显示的数据！");
					return;
				}
				//$("chartTdId").innerText = xmlDoc.xml;
				//return;
				var chartXML = "<chart caption='"+title+"' baseFont='宋体'"
					+ "xAxisName='"+xDesc+"' yAxisName='"+yDesc+"'"
					+ "baseFontSize='12' rotateNames='0' showValues='0' showNames='1'"
					+ "decimalPrecision='0' formatNumberScale='0'>";
				chartXML += "<categories>";
				var rows = xmlDoc.getElementsByTagName("row");
				var cols = rows[0].getElementsByTagName("col");
				for(var j = 1; j < cols.length - 1; j++){
					chartXML += "<category label='" + cols[j].text + "' />";
				}
				chartXML += "</categories>"
				for(var i = 1; i < rows.length - 1; i++){
					var cols = rows[i].getElementsByTagName("col");
					///*
					var isContain = false;
					for(var j=0;j<jgmcs.length;j++){
						if(jgmcs[j] == cols[0].text){
							isContain = true;
							jgmcs.splice(j,1);
							break;
						}
					}
					if(isContain == false){
						continue;
					}//*/
					chartXML += "<dataset seriesName='" + cols[0].text + "'>";
					for(var j = 1; j < cols.length - 1; j++){
						chartXML += "<set value='" + cols[j].text + "' />";
					}
					chartXML += "</dataset>";
				}
				chartXML += "</chart>";
				$("chartTdId").innerText = chartXML;
				var swfPath = "../../../FusionChartsV3.1/swf/";
				if(chartType == "bar"){
					swfPath += "MSColumn3D.swf";
				}else if(chartType == "line"){
					swfPath += "MSLine.swf";
				}else{
					swfPath += "MSLine.swf";
				}
				var chart = new FusionCharts(swfPath,"chartId","800","500","0","0");
				chart.setDataXML(chartXML);		   
    			chart.render("chartTdId");
			}
			/*
			function showChart(title,xDesc,yDesc,data,chartType){
				data = data.split(";");
				for(var i=0;i<data.length;i++){
					var item = data[i].split(",");
					data[i]=item;
				}
				var chartXML = "<chart caption='"+title+"' baseFont='宋体'"
					+ "xAxisName='"+xDesc+"' yAxisName='"+yDesc+"'"
					+ "baseFontSize='12' rotateNames='0' showValues='0' showNames='1'"
					+ "decimalPrecision='0' formatNumberScale='0'>";
				chartXML += "<categories>";
				var start = data[0][0];
				for (var i = 0; i < data.length; i++) {
					chartXML += "<category label='" + data[i][0] + "' />";
					if (i == data.length - 1 || start == data[i + 1][0]) {
						break;
					}
				}
				chartXML += "</categories>";
				start = data[0][0];
				var datasets = "";
				for ( var i = 0; i < data.length; i++) {
					if (data[i][0] == start) {
						datasets += "</dataset>";
						datasets += "<dataset seriesName='" + data[i][2] + "'>";
						datasets += "<set value='" + data[i][1] + "' />";
					} else {
						datasets += "<set value='" + data[i][1] + "' />";
					}
				}
				chartXML += datasets.substring("</dataset>".length);
				chartXML += "</dataset>";
				chartXML += "</chart>";
				
				var swfPath = "../../../FusionChartsV3.1/swf/";
				if(chartType == "bar"){
					swfPath += "MSColumn3D.swf";
				}else if(chartType == "line"){
					swfPath += "FCF_MSLine.swf";
					chartXML = chartXML.replace("chart","graph").replace("chart","graph");
				}
				var chart = new FusionCharts(swfPath,"chartId","800","500","0","0");
				chart.setDataXML(chartXML);		   
    			chart.render("chartTdId");
			}
			*/
		</script>
		</td>
	  </tr>
	  <tr>
		<td align="right">
			<a href="#" onclick="javascript:window.print();">打印</a>
		</td>		
	  </tr>
	</table>
	<div id="showDivId"></div>
  </body>
</html>
