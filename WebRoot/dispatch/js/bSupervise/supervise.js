  /** 
    * ------------------------------------------------------------------------------------------------------------------
    * Copyright (c) 2009, EHL Technologies, Inc.
    * All right reserved.
    * 说明：展示督办信息
    * 作者：ldq
    * 日期: 2009-1-13
    * ------------------------------------------------------------------------------------------------------------------------
    */
function initSupervise(){
	//添加行事件
	watching.setRowClickHandler(doViewSupervise);
}
function querySupervise(){
	$("divEditAlarm").style.display = "none";
	$("divSupervise").style.display = "";
}
function showWinSupervise(dbbh,dbsj,dbr,dbdw,dbnr,sjbh,bz){
	var url = "../supervise/showSupervise.jsp";
	
	if(dbbh != "" && dbbh != undefined){
		var url = "../supervise/viewSupervise.jsp?dbbh="+dbbh+"&dbsj="+dbsj+"&dbr="+dbr+"&dbdw="+dbdw+"&dbnr="+dbnr+"&sjbh="+sjbh+"&bz="+bz;
		url = encodeURI(url);
		var top=(screen.availHeight-300)/2;		
		var left=(screen.availWidth-350)/2;
		window.open(url,"new","height=300,width=350,top="+top+",left="+left);
		return;
	}
	url = encodeURI(url);
	var top=(screen.availHeight-600)/2;		
	var left=(screen.availWidth-1000)/2;    
	var supervisePage = window.open(url,"edit","width=1000,heigth=600,top="+top+",left="+left);

}    
/** 
    * desc:  展示督办信息
    * author：ldq
    * date:   2009-4-15
    * version: 1.0
    */
function doViewSupervise(alarmId){
//	var alarmId = $('btnMonitor').strSuperviseAlarmId;
	var strUrl = "dispatch.supervise.getSupervise.d?alarmId=" + alarmId;
	var url = encodeURI(strUrl);
	var params = "";	   //传入的参数 
	params = encodeURI(params);   //进行编码转换
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseView});
}

/** 
    * desc:  生成数据表格
    * author：ldq
    * date:   2009-1-13
    * version: 1.0
    */
function showResponseView(xmlHttp) {
//	alert(xmlHttp.responsetext);
	var xmlDoc = xmlHttp.responseXML;
   	var results = xmlDoc.getElementsByTagName("row");
 	var tdObj = document.getElementById("superviseGrid");
 	
 	//生成表格
   	var strTab = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#FFFFFF\">";
    for(var i = 0 ; i <results.length; i++){
	    var res = results[i].getElementsByTagName("col");
		
	    var dbbh = res[0].text;//督办编号
	    var dbsj = res[1].text;//督办时间
	    var dbr = res[2].text;//督办人
	    var dbdw = res[3].text;//督办目标单位
	    var dbnr = res[4].text;//督办内容
	    var sjbh = res[5].text;//对应事件编号
	    var bz = res[6].text;//备注
	    
	    strTab += "<tr class='vcastr_2'>";
	   	strTab += "<td width='0%'  align='center' style='display:none'>" + dbbh + "</td>";
	    strTab += "<td width='20%' align='center' >" + dbsj + "</td>";
	    strTab += "<td width='14%' align='center' >" + dbr + "</td>";
	    strTab += "<td width='18%' align='center' >" + dbdw + "</td>";
	    strTab += "<td width='23%' align='center' >" + dbnr + "</td>";
	    strTab += "<td width='20%' align='center' >" + sjbh + "</td>";
	    strTab += "<td width='5%' align='center' >";
	    strTab += 	"<a href=\"javascript:\" onclick=\"showWinSupervise('"+ dbbh + "','"+ dbsj + "','"+ dbr + "','"+ dbdw + "','"+ dbnr + "','"+ sjbh + "','"+ bz + "');\">" +
	    		"<img style='border:0' src='../../images/button/btnck.gif' alt='查看明细'/></a>";	
	    strTab += "</td>";    	  	
     	strTab += "</tr>";    
    }        	            	            	
    strTab+= "</table>";
    tdObj.innerHTML=strTab;
}
