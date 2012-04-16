/**
 * 预案定时提醒
 */
 
//每60秒刷新一次
var timeName = 60;

/**
 * 日期: 2009-03-08
 * 作用：设置定时器
 */
function prmplanAlert(){
	clearInterval(timeName); 
	timeName = setInterval("showPrmplan()", parseInt(timeName)*1000);
}

/**
 * 弹出预案提醒信息
 */
function showPrmplan(){
	var url = "prmplan.prmplanManage.getDataById.d";
	url = encodeURI(url);
	var params = "time="+getSysdate(0);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showHotsData});
}

/**
 * 热点消息提示
 */
function showHotsData(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	//获取需提醒的预案信息
	var results = xmlDoc.getElementsByTagName("row");
	
	if(results != null && results.length>0){
		var SXH,yamc,YYCJ,zyms,lsdw,lssj,txsj,qdsj,cjdw,cjry,cjrq,xgrq,shyj;
		var html = "";
		for (var i = 0; i < results.length; i++){
			html = "";
			rowResult = results[i].childNodes;
			rowArray = new Array();
			
			SXH = rowResult[0].text;
			if (SXH != null){
				rowArray[0] = SXH;
			}
			
			yamc = rowResult[1].text;
			if (yamc != null){
				rowArray[1] = yamc;			
			}
			
			YYCJ = rowResult[2].text;
			if (YYCJ != null){
				rowArray[2] = YYCJ;			
			}
			
			zyms = rowResult[3].text;
			if (zyms != null){
				rowArray[3] = zyms;			
			}
			
			lsdw = rowResult[4].text;
			if (lsdw != null){
				rowArray[4] = lsdw;			
			}
			
			lssj = rowResult[5].text;
			if (lssj != null){
				rowArray[5] = lssj;			
			}
			
			txsj = rowResult[6].text;
			if (txsj != null){
				rowArray[6] = txsj;			
			}
			
			qdsj = rowResult[7].text;
			if (qdsj != null){
				rowArray[7] = qdsj;			
			}
			
			shyj = rowResult[12].text;
			if (shyj != null){
				rowArray[12] = shyj;			
			}
			
			html += "<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>";
			html += "<tr style='display:none;'><td align='right'>预案编号：</td><td width='130'>" + SXH + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>预案名称：</td><td align='leftt'>" + yamc + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>应用场景：</td><td align='leftt'>" + YYCJ + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>摘要描述：</td><td align='leftt'><div style='width:100%;height:60;overflow:auto;' readonly=true class='text' align='left'>" + zyms + "</div></td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>落实单位：</td><td align='leftt'>" + lsdw + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>落实时间：</td><td align='leftt'>" + lssj + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>提醒时间：</td><td align='leftt'>" + txsj + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>启动时间：</td><td align='leftt'>" + qdsj + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'><td align='right'>审核意见：</td><td align='leftt'>" + shyj + "</td></tr>";
			html += "<tr style='padding:1px 1px 1px 1px'>";
			html += "</table>";
			
			var hotsDate = new xWin(SXH,300,250,100,150,"预案信息",html); 
			//30秒后关闭弹出消息框
			window.setTimeout("ShowHide('"+SXH+"')", 30000);
		}
	}
}