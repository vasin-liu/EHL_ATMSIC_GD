/**
 * 警情定时提醒
 */
 
//每60秒刷新一次
var timerForShow = 60;

/**
 * 日期: 2009-03-08
 * 作用：根据查询数据设置定时器
 */
function timeShowAlarm(timeStr){
	if(timeStr == "" || timeStr == "null"){
		timeStr = 60;
	}
	//var tag = $("").value;
	clearInterval(timerForShow); 
	timerForShow = setInterval("showPolice()", parseInt(timeStr)*1000);
}

/**
 * 弹出警情提醒信息
 */
function showPolice(){
	if(!isRefresh){
		return false;
	}
	var url = "dispatch.accident.getPoliceOnTime.d";
	url = encodeURI(url);
	params = "depttype=" + depttype + "&deptcode="+deptcode;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPoliceData});
}

/**
 * 警情消息提示
 */
function showPoliceData(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row");
	
	if(results != null && results.length>0){
		var dayCount = "";
		var monCount = ""
		var html = "";
		for (var i = 0; i < results.length; i++){
			html = "";
			rowResult = results[i].childNodes;
			
			monCount = rowResult[0].text+"";
			dayCount = rowResult[1].text;
			
			html += "<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>";
			if(monCount>0){
				html += "<tr style='padding:1px 1px 1px 1px;cursor:pointer;' onClick=doQuery('check','MM')>";
			}else{
				html += "<tr style='padding:1px 1px 1px 1px'>";
			}
			html += "<td align='right' style='font-size:14px;'>当月需关注事故宗数：</td>";
			html += "<td align='left' style='font-size:14px;'>";
			if(monCount>0){
				html += "<a href=\"#\" style='color:#FF0000;'>" + monCount + "</a>";
			}else{
				html += "<a href=\"#\">" + monCount + "</a>";
			}
			html += "</td></tr>";
			if(dayCount>0){
				html += "<tr style='padding:1px 1px 1px 1px;cursor:pointer;' onClick=doQuery('check','DD')>";
			}else{
				html += "<tr style='padding:1px 1px 1px 1px'>";
			}
			html += "<td align='right' style='font-size:14px;'>最近24小时需关注事故宗数：</td>";
			html += "<td align='left' style='font-size:14px;'>";
			if(dayCount>0){
				html += "<a href=\"#\" style='color:#FF0000;'>" + dayCount + "</a>";
			}else{
				html += "<a href=\"#\">" + dayCount + "</a>";
			}
			html += "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'>" +
					"<td align='right' style='font-size:14px;'></td>" +
					"<td align='left' style=font-size:14px;'>" +
					"<a href=\"#\" onClick=isRefresh=false;ShowHide('acc')><span class='lbl' id=''>关闭提示</span></a></td></td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'>";
			html += "</table>";
			var leftNum = 510;
			var topNum = 400;
			if(i>0){
				leftNum = leftNum-(i*5);
				topNum = topNum-(i*5);
			}
			var PoliceDate = new xWin("acc",300,100,leftNum,topNum,"需关注报警情提醒",html); 
			//30秒后关闭弹出消息框
//			window.setTimeout("ShowHide('"+ALARMID+"')", 30000);
		}
	}
}