var roadBuildList = null;
var roadBuildcount = 0;
var maxlength = 0;
var jgid = "";
var jgbh = "";
var roadBuildtotalCount = 0;
//是否显示施工占道查询结果,
//默认为显示，否则只刷新地图，不显示查询结果，
//每次执行完成将isShowRdbdResult设为true
var isShowRdbdResult = true;

function getroadBuildHtml() {
    roadBuildList = null;
    roadBuildcount = 0;
    maxlength = 0;
    roadBuildtotalCount = 0;
	jgid = $("jgid").value;
	jgbh = $("jgbh").value;
	if (roadBuildList == null || roadBuildList.length == 0) {
		var url = 'dynamicinfo.roadRepair.getroadBuildAllInfo.d';
		var params = "jgid=" + jgid + "&jgbh=" + jgbh;
		params = encodeURI(params);
		new Ajax.Request(url, {
					method : 'post',
					parameters : params,

					// 读取完成后，放入内存数组
					onComplete : finishAllroadBuildInfo
				});
	} else {
		showroadBuildResultsPage();
	}

} 

function finishAllroadBuildInfo(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = "";
	var rowArray = null;
	roadBuildList = new Array();
	for (var i = 0; i < results.length; i++)
	{
		result = results[i].childNodes;							
	    	rowArray = new Array(5);
	    	rowArray[0] = result[0].text;//警情编号
		    rowArray[1] = result[1].text;//道路名称
		    rowArray[2] = result[2].text;//施工方向
		    rowArray[3] = result[3].text;//经度
		    rowArray[4] = result[4].text;//纬度
	    	roadBuildList[i] = rowArray; //一条事故信息
	}
	
	// 警情编号,道路名称,施工方向,X,Y  增加道路方向判别wjh
	var sql = "  select B.ALARMID, B.roadid,decode(B.ROADDIRECTION,'1',(select tdd.end||'往'||tdd.begin from t_oa_dictdlfx tdd where tdd.dlmc=B.roadid),'0',(select tdd.begin||'往'||tdd.end from t_oa_dictdlfx tdd where tdd.dlmc=B.roadid),'双向'), B.x, B.y " + 
			  "    from t_attemper_alarm B, T_ATTEMPER_ROADBUILD C " + 
			  "  where " + 
			  "  B.ALARMID = C.ALARMID " + 
			  "  and B.EVENTSTATE = '570005' " + 
			  "  and B.Eventtype = '001023' " + 
			  "  and B.x is not null " + 
			  "  and b.y is not null " + 
			  "  order by B.ALARMTIME desc ";
	
	PageCtrl.initPage(getPCId(isShowRdbdResult),"pageData","pageNav",convertSql(sql),"showroadBuildResultsPage","10");

}

function showroadBuildResultsPage(xmldoc) {

	var results = xmldoc.getElementsByTagName("row");
	
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\" >";
	strTable += "<td class=\"scrollRowThead scrollCR\" align=center width=12% background=\"../../image/back/title_back.gif\">选择</td>";
	strTable += "<td align=\"center\" width=30% background=\"../../image/back/title_back.gif\">道路名称</td>";
	strTable += "<td align=\"center\" width=50% background=\"../../image/back/title_back.gif\">施工方向</td>";
	strTable += "</tr>";
	var result = "";
	var rowArray = null;
	roadBuildcount = 0;
	for (var i = 0; i < results.length; i++) {
		rowArray = new Array(6);
		result = results[i].childNodes;
		strTable += "<tr class=\"rowstyle\"  onclick=\"showroadBuildPoint('"+result[0].text+"','"+(i)+"','"+result[3].text+"','"+result[4].text+"');\" >";					    
	    strTable += "<td class=\"scrollRowThead\" align=\"center\"><input type=\"radio\" id=\""+i+"\" /></td>";
	    strTable += "<td align=\"center\" >" + result[1].text + "</td>";
	    strTable += "<td align=\"center\" >" + result[2].text  + "</td>";							    					    
	    strTable += "</tr>";
	    roadBuildcount = roadBuildcount + 1;
	    roadBuildtotalCount = roadBuildtotalCount + 1;
	    maxlength = i;
	    
	}
	strTable += "</table>";
	var tabOjb = document.getElementById("pageData");
//	alert("isOut:"+isShowRdbdResult)
	if(isShowRdbdResult){
//		alert("isIn:"+isShowRdbdResult)
		tabOjb.innerHTML = strTable;
	}else{
		isShowRdbdResult = true;
	}
	showAllroadBuildPoint();
}

/**
 * 在地图上显示车辆地点
 */
function showAllroadBuildPoint() {
	
	// 判断是否为空或者对象未被实例化
	if (typeof roadBuildList == "undefined" || roadBuildList == null
			|| roadBuildList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	var currentZoom = map.getCurrentZoom();
	
	// 坐标数据
// 	alert(55555555555);
	for (var i = 0; i < roadBuildList.length; i++) {
		// 判断是否为空或者对象未被实例化
		var infoCoord = new Coordinate((roadBuildList[i][3] * 1e16), (roadBuildList[i][4] * 1e16));// 将坐标转化为整形
		this.showroadBuildSymbol(mapDiv, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
	}
}

/**
 * 标注点闪烁显示处理<br/>
 * 标注点闪烁显示处理
 */
function showroadBuildPoint(checkId,checkInfo,X,Y) {
//	alert("AA" +"     " + checkInfo + "      " + count);
	for (var i = 0; i < maxlength+1; i++) {
		var tableObj = document.getElementById("" + i + "");
		tableObj.checked = false;
	}
	var checkObj = document.getElementById(""+checkInfo+"");
	checkObj.checked = true;
		var showNum = 0;
	if (roadBuildtotalCount > 10) {
		showNum = roadBuildtotalCount - 10 - (- checkInfo);
	} else {
		showNum = checkInfo;
		}
		//alert(showNum);
	var mapDiv = map.getVMLDiv();
	var model = map.getMapModel();
	var currentZoom =  map.getCurrentZoom();
	var pointArray = new Array(2);
	pointArray[0] = X;
	pointArray[1] = Y;	
	//闪烁显示事故点
	MapUtils.prototype.flashPoint(mapDiv, pointArray,currentZoom,2);
}

/**
 * 标注方法<br/> 
 * @param {Object}  mapDiv 地图图层对象
 * @param {Object}  infoCoord 经纬度坐标信息
 * @param {Object}  zoom 当前屏幕显示区域
 * @param {Integer} order equipList行号
 */
function showroadBuildSymbol(mapDiv, infoCoord, zoom, order) {
	var scoord = Util.getScreenPixel(infoCoord, zoom);// 由经纬度转屏幕坐标.
	var strSymbolId = "'" + roadBuildList[order][0] + "'";
//	var strSymbolId = "'" + "accPoint" + "'";
	// 判断是否已经存在
	var pointSymbol = document.getElementById(strSymbolId);
	if (pointSymbol != null) {
		pointSymbol.parentNode.removeChild(pointSymbol);
	}
	// 判断是否处在需要显示的级别上.
	var currLevel = map.getCurrLevel();
	if (currLevel < Number(getMapParameter("gisDeviceLevel"))) {
		return;
	}
	pointSymbol = document.createElement("div");
	pointSymbol.id = strSymbolId;
	pointSymbol.style.position = "absolute";
	pointSymbol.style.border = "0px solid #999999";
	pointSymbol.style.fontSize = "12px";
	pointSymbol.style.padding = "1px";
	pointSymbol.style.zIndex = 11;
	pointSymbol.style.left = scoord.x + "px";
	pointSymbol.style.top = scoord.y + "px";
	var image = Util.createImg(roadBuildList[order][0],0, 0, 16, 16, "../../image/checkPoint/sgzd.gif");
	image.style.cursor = "pointer";
	// 显示标注点
	Event.observe(image, "click",this.responseroadBuildClick.bindAsEventListener(image,roadBuildList[order][0]));

	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	mapDiv.appendChild(pointSymbol);
}

/**
 * 点击标注点显示详细信息<br/>
 * 点击标注点显示详细信息的处理
 */
function responseroadBuildClick(event,alarmId) {
	var x = 0;
	var y = 0;
	var container;

	//取得鼠标的绝对坐标
	container = map.getVMLDiv();
	if (event != null) {
		event = event ? event : (window.event ? window.event : null);
		var source = event.srcElement || event.target;
		x = Event.pointerX(event) - container.offsetLeft
				|| container.scrollLeft;
		y = Event.pointerY(event) - container.offsetTop || container.scrollTop;
	} else {
		x = left + 8;
		y = top;
	}
	//显示正在查询提示		
	MapTips.prototype.showLoadTips(container, x, y);
	//执行查询获取当前选中的设备的信息
	var url = 'dynamicinfo.roadRepair.getroadBuild.d';
	var conditionId = "";
	var params = "jgid=" + jgid + "&jgbh=" + jgbh + "&alarmId=" + alarmId;
	params = encodeURI(params);
	//查询到结果后显示在气泡中		
	new Ajax.Request(url, {
				method : 'post',
				parameters : params,

				onComplete : function(xmlDoc) {
					var strResponseText = xmlDoc.responseText;
					if (strResponseText == null) {
						return;
					}
					//显示提示信息窗口			   
					MapTips.prototype.showPopup(container, "施工占道信息", strResponseText, x-10, y-20)
				}
			});
}
/**
 * 清除标注点<br/>
 * 清除标注点处理
 */
function removeroadBuildOnMap() {
	// 判断是否为空或者对象未被实例化
	if (typeof roadBuildList == "undefined" || roadBuildList == null
			|| roadBuildList.length == 0) {
		return;
	}
	for (var i = 0; i < roadBuildList.length; i++) {
		// 判断是否为空或者对象未被实例化
		var obj = document.getElementById(roadBuildList[i][0]);
		obj && obj.parentNode.removeChild(obj);
	}
	roadBuildList = null;
    //Modified by Liuwx 2012年3月14日18:27:42
	//var conCtrl= document.getElementById("daynameicInfo");
    //conCtrl.innerHTML = "";
    //Modification finished
}