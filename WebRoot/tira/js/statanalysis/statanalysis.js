









function analysis() {
	//var statobjname = document.getElementById("statobjname");//统计对象名称：机动车，注意不要有空格
	//var statItem = document.getElementById("statItem");//统计项：行政区划:1:300119:division
	var statItemDetail = document.getElementById("statItemDetail");// 
	//var floatItem  = document.getElementById("floatItem");
	var floatItemDetail = document.getElementById("floatItemDetail");
	//var dataItem = document.getElementById("dataItem");
//	var selList = document.getElementById("seltbl").getElementsByTagName("select");
	var xzqh = document.getElementById("xzqhId");
	var dateStart = document.getElementById("dateStartId");
	var dateEnd = document.getElementById("dateEndId");
	
//	var selItems = "";
//	for (var i = 0; i < selList.length; i++) {
//		selItems += "_"+selList[i].value;
//	}
//	selItems = selItems.substring(1);
//	selItems = selItems + "_" + "日期:" + dateStart.value + "~" + dateEnd.value;
	if (statobjname == null || statItem == null || statItemDetail == null 
			|| floatItem == null || floatItemDetail == null || dataItem == null ) {
		alert("有参数为null！");
	}
	
	
	var params = "statobjname="+statobjname.innerText+"&statItem="+statItem.value
			+"&statItemDetail="+statItemDetail.value+"&floatItem="+floatItem.value
			+"&floatItemDetail="+floatItemDetail.value+"&dataItem="+dataItem.value
			+"&xzqh="+xzqh.value+"&dateStart="+dateStart.value+"&dateEnd="+dateEnd.value;
	alert(params);
	var url = "tira.StatAnalysis.analysis.d";
	new Ajax.Request(url,{method:"post",parameters:params,onComplete:show});
}

//function show(xmlHttp) {

//	var xmlDoc = xmlHttp.responseXML;
//	alert(xmlDoc.xml);
//}