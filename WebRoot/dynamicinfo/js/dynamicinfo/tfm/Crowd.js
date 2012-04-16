/**
  * modify by zhaoyu 2010-12-30 增加查询信息：经纬度
  * modify by lidq 2011-02-22 取消sde连接，改为根据道路名称从mis表获取点
  */
function getTfmHtml() {
	var sql = "SELECT TAA.ALARMID,       TAA.ALARMREGION,       TAA.Roadid ||TAA.ROADNAME, ";
	sql +=" decode(TAA.ROADDIRECTION,'1',(select tdd.end||'往'||tdd.begin from t_oa_dictdlfx tdd where tdd.dlmc=taa.roadid),'0',(select tdd.begin||'至'||tdd.end from t_oa_dictdlfx tdd where tdd.dlmc=taa.roadid),'双向'),";
	sql +=" '55',       '44',       TO_CHAR(TAA.REPORTTIME, 'YYYY-MM-DD HH24:MI'),       TAA.X,       TAA.Y,       ROADID,       KMVALUE,       ENDKMVALUE  FROM T_ATTEMPER_ALARM TAA" +
	" WHERE (select count(code) from lcb_pt_mis lcb where lcb.dlmc = taa.roadid and to_number(qmz) >= to_number(kmvalue) and to_number(qmz) <= to_number(endkmvalue)) != 0 and EVENTTYPE = '001002'   AND EVENTSTATE = '570001'   ";//事件类型是拥堵,事件状态是拥堵中 and taa.casehappentime<=sysdate    and taa.caseendtime >=sysdate 正在拥堵中表示未解除，都展示
  
	PageCtrl.initPage("daynameicInfo","pageData","pageNav",convertSql(sql),"showTfmResultsPage","10");
} 
function showTfmResultsPage(xmldoc) {
	var results = xmldoc.getElementsByTagName("row");
	
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\" >";
	strTable += "<td  style='display:none'></td>";
	strTable += "<td align=\"center\" width=30% background=\"../../image/back/title_back.gif\">拥堵路段</td>";
	strTable += "<td align=\"center\" width=30% background=\"../../image/back/title_back.gif\">拥堵方向</td>";
	strTable += "<td align=\"center\" width=50% background=\"../../image/back/title_back.gif\">查看</td>";
	strTable += "</tr>";
	var result = "";
	var rowArray = null;
	count = 0;
	for (var i = 0; i < results.length; i++) {
		rowArray = new Array(6);
		result = results[i].childNodes;
		if(result[2].text == 'null' || result[2].text == null){
		    result[2].text = "&nbsp;";
		}
		if(result[3].text == 'null' || result[3].text == null){
		    result[3].text = "&nbsp;";
		}
		strTable += "<tr class=\"rowstyle\"   onclick=\"setRoadChecked(this)\" onmouseover=\"mouseover(this)\" onmouseout=\"mouseout(this)\"  >";		
		strTable += "<td  style='display:none'>"+result[7].text+"</td>";	
		strTable += "<td  style='display:none'>"+result[8].text+"</td>";
		strTable += "<td  style='display:none'>"+result[9].text+"</td>";
		strTable += "<td  style='display:none'>"+result[10].text+"</td>";	
		strTable += "<td  style='display:none'>"+result[11].text+"</td>";			    
	    strTable += "<td  align=\"center\" >" + result[2].text + "</td>";
	    strTable += "<td  align=\"center\" >" + result[3].text  + "</td>";							    					    
	    strTable += "<td align=\"center\">" + '<a href="#" onclick="showMoreInfo( \''+ result[0].text +'\',this);">查看详细信息</a>' + "</td>";						    					    
	    strTable += "</tr>";
	}
	strTable += "</table>";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = strTable;
}

/**
 * 清除list信息<br/>
 * 清除list信息
 */
function removeTfmOnMap() {
    //Modified by Liuwx 2012年3月14日18:27:42
	//var conCtrl= document.getElementById("daynameicInfo");
    //conCtrl.innerHTML = "";
    //Modification finished
}

function showMoreInfo (alarmId,aObj) {
    var trObj = aObj.parentNode.parentNode;
    setRoadChecked(trObj);
	window.showModalDialog("../../../dispatch/ehl/cpoliceedit/TrafficCrowdInfoAdd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&trfficeCrowState=570001&alarmId='" + alarmId + "'", "", "dialogWidth:850px;dialogHeight:600px");
}

/**
 * 设置onmouseover背景色<br/>
 * add by zhaoyu 2010-12-30
 */
function mouseover(trobj){
    if(trobj.style.background != "#8ed9ee"){
       trobj.style.background = "#C8FFFF";//"#6CA6D4";
    }
}

/**
 * 设置onmouseout背景色<br/>
 * add by zhaoyu 2010-12-30
 */
function mouseout(trobj){
    if(trobj.style.background != "#8ed9ee"){
       trobj.style.background = "";
    }
}

/**
 * 设置道路选中状态<br/>
 * add by zhaoyu 2010-12-30
 */
function setRoadChecked(trobj){
    var trs = trobj.parentNode.childNodes;
    for(var i = 0 ; i < trs.length; i ++){
        trs[i].style.background = "";
    }
    trobj.style.background = "#8ed9ee";//#8ed9ee
    centerRoad(trobj);
}

/**
 * 设置道路居中<br/>
 * add by zhaoyu 2010-12-30
 */
function centerRoad(trobj){
    var roadid = trobj.childNodes[2].innerHTML;
    var kmvalue = trobj.childNodes[3].innerHTML;
    var endkmvalue = trobj.childNodes[4].innerHTML;
    var params = "roadid=" + roadid+"&kmvalue="+kmvalue+"&endkmvalue="+endkmvalue;
    var url = 'dynamicinfo.crowd.getRoadPoint.d';
    params = encodeURI(params);
    new Ajax.Request(url, {method:"post", parameters:params, onComplete:moveToRoad});
}

function moveToRoad(xmlHttp){
    var roadPointStr = xmlHttp.responseText;
    if(roadPointStr == 'null' ||roadPointStr.length == 0){
        alert("尚未关联地图路网");
        return;
    }
    var polypoints = roadPointStr.split(";");
    var mapDiv = map.getVMLDiv();   	
   	var model = map.getMapModel();
   	var x = polypoints[0].split(",")[0];
    var y = polypoints[0].split(",")[1];
    var centPoint = "";
	var coordX = Number(x) * 1e16;
	var coordY = Number(y) * 1e16;
	centPoint = new Point(coordX, coordY);	
   	map.moveToCenter(centPoint,6);
   	var zoom =  map.getCurrentZoom();
    MapUtils.prototype.flashPolygon(mapDiv, polypoints,zoom,6);
}

