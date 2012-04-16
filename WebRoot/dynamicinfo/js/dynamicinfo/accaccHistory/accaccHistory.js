var accHistoryInfoList = null;
var count = 0;
var maxlength = 0;
var accHisTotalCount = 0;

/**
 * 历史事故信息取得<br/>
 * 历史事故信息从警情表中取得。
 */
function getAccHistoryHtml() {
	accHistoryInfoList = null;
	count = 0;
	maxlength = 0;
	accHisTotalCount = 0;
	getAccHistoryInfo();
}
 
/**
 * 历史事故信息取得<br/>
 * 历史事故信息从历史事故信息表中取得。
 */
function getAccHistoryInfo() {
	if(accHistoryInfoList==null||accHistoryInfoList.length==0){
		var url = 'dynamicinfo.accaccHistory.getAccInfo.d';
		var params = '';
	//	params = encodeURI(params);
		new Ajax.Request(url, {
				method: 'post', 
				parameters: params, 
				//读取完成后，放入内存数组
				onComplete: setAccHistoryResultsPage
		});
	}else{
		showHisAcc();
	}
}

/**
 * 历史事故信息取得的回显处理<br/>
 * 历史事故信息取得的回显处理
 * @param {} xmldoc
 */
function setAccHistoryResultsPage (xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row");
	
	var result = "";
	var rowArray = null;
	accHistoryInfoList = new Array();
	for (var i = 0; i < results.length; i++) {
		var rowResult = results[i].childNodes;
		var rowArray = new Array(5);
		
		var sgbh = "";					
		var sgbhObj = rowResult[0].firstChild;
		if (sgbhObj != null){
			sgbh = sgbhObj.nodeValue;
		}
		var sglx = "";
		var sglxObj = rowResult[1].firstChild;
		if (sglxObj != null){
			sglx = sglxObj.nodeValue;			
		}
		var sgfssj = "";
		var sgfssjObj = rowResult[2].firstChild;
		if (sgfssjObj != null){
			sgfssj = sgfssjObj.nodeValue;			
		}
	    var jd = "";
		var jdObj = rowResult[3].firstChild;
		if (jdObj != null){
			jd = jdObj.nodeValue;			
		}
		var wd = "";
		var wdObj = rowResult[4].firstChild;
		if (wdObj != null){
			wd = wdObj.nodeValue;			
		}
	    rowArray[0] = sgbh;//事故编号，新增区划
	    rowArray[1] = sglx;//事故类型
	    rowArray[2] = sgfssj;//事故发生时间
	    rowArray[3] = jd;//经度
	    rowArray[4] = wd;//纬度
    	accHistoryInfoList[i] = rowArray; //一条事故信息
	}
	showHisAcc();
}
/**
 * 历史事故信息<br/>
 */
function showHisAcc(){
	// 事故编号,路名,事故发生时间
	var sql = "SELECT XZQH||','||SGBH,F_TIRA_GET_NAME('300403',SGLX),TO_CHAR(SGFSSJ,'YYYYMMDDHH24MISS'),X,Y FROM T_TIRA_ACD_C_ACDFILE WHERE SGFSSJ >= (SYSDATE-2) AND X != 0 AND Y != 0";
	PageCtrl.initPage("daynameicInfo","pageData","pageNav",convertSql(sql),"showAccHistoryResultsPage","10");
}
/**
 * 历史事故信息取得的回显处理<br/>
 * 历史事故信息取得的回显处理
 * @param {} xmldoc
 */
function showAccHistoryResultsPage (xmldoc) {
	var results = xmldoc.getElementsByTagName("row");
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\">";
	strTable += "<td class=\"scrollRowThead scrollCR\" align=center width=12% background=\"../../image/back/title_back.gif\">选择</td>";
	strTable += "<td align=\"center\" width=30% background=\"../../image/back/title_back.gif\">事故类型</td>";
	strTable += "<td align=\"center\" width=50% background=\"../../image/back/title_back.gif\">事故发生时间</td>";
	strTable += "</tr>";
	var result = "";
	count = 0;
	for (var i = 0; i < results.length; i++) {
		result = results[i].childNodes;
		strTable += "<tr class=\"rowstyle\"  onclick=\"showAccHistoryPoint('"+result[0].text+"','"+(i)+"');\" >";			    
	    strTable += "<td class=\"scrollRowThead\" align=\"center\"><input type=\"checkbox\" id=\""+i+"\"  /></td>";
	    strTable += "<td >" + result[1].text + "</td>";
	    
	    var str = result[2].text;
	    var yyyy = str.substring(0,4);
	    var mm = str.substring(4,6);
	    var day = str.substring(6,8);
	    var hour = str.substring(8,10);
	    var fen = str.substring(10,12);
	    var showStr = yyyy + "年" + mm + "月" + day + "日" + hour + "时" + fen + "分";  
	    
	    strTable += "<td >" + showStr  + "</td>";							    					    
	    strTable += "</tr>";
	    
	    accHisTotalCount = accHisTotalCount + 1;
	    count = count + 1;
    	maxlength = i;
	}
	strTable += "</table>";
	
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = strTable;
	showAllAccHisPoint();
}

/**
 * 在地图上显示事故点
 */
function showAllAccHisPoint() {
	// 判断是否为空或者对象未被实例化
	if (typeof accHistoryInfoList == "undefined" || accHistoryInfoList == null || accHistoryInfoList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	var currentZoom = map.getCurrentZoom();
	// 坐标数据
	for (var i = 0; i < accHistoryInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		if ((typeof accHistoryInfoList[i][3] == "undefined" || accHistoryInfoList[i][3] == "null"
				|| accHistoryInfoList[i][3] == null || accHistoryInfoList[i][3] == "")
				|| (typeof accHistoryInfoList[i][4] == "undefined" || accHistoryInfoList[i][4] == "null"
						|| accHistoryInfoList[i][4] == null || accHistoryInfoList[i][4] == "")) {
		} else {
			var infoCoord = new Coordinate((accHistoryInfoList[i][3] * 1e16), (accHistoryInfoList[i][4] * 1e16));// 将坐标转化为整形
			this.showAccHistorySymbol(mapDiv, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
		}
	}
}

/**
 * 标注方法<br/> 
 * @param {Object}  mapDiv 地图图层对象
 * @param {Object}  infoCoord 经纬度坐标信息
 * @param {Object}  zoom 当前屏幕显示区域
 * @param {Integer} order equipList行号
 */
function showAccHistorySymbol(mapDiv, infoCoord, zoom, order) {
	var scoord = Util.getScreenPixel(infoCoord, zoom);// 由经纬度转屏幕坐标.
	var strSymbolId = "'" +  accHistoryInfoList[order][0] + "'";

	// 判断是否已经存在
	var pointSymbol = document.getElementById(strSymbolId);
	if (pointSymbol != null) {
		pointSymbol.parentNode.removeChild(pointSymbol);
	}
	// 判断是否处在需要显示的级别上.
	var currLevel = map.getCurrLevel();
	if(currLevel < Number(getMapParameter("gisDeviceLevel"))){
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
	var image = Util.createImg(accHistoryInfoList[order][0],0, 0, 16, 16, "../../image/checkPoint/accHis.gif");
	image.style.cursor = "pointer";
	
	Event.observe(image, "click",this.responseAccHisClick.bindAsEventListener(image,accHistoryInfoList[order][0]));
	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	mapDiv.appendChild(pointSymbol);
}

/**
 * 标注点闪烁显示处理<br/>
 * 标注点闪烁显示处理
 */
function showAccHistoryPoint(checkId,checkInfo) {
	for (var i = 0; i < maxlength+1; i++) {
		var tableObj = document.getElementById(""+i+"");	
		tableObj.checked = false;
	}
	var checkObj = document.getElementById(""+checkInfo+"");
	checkObj.checked = true;
	
	var showNum = 0;
	if (accHisTotalCount > 10) {
		showNum = accHisTotalCount - 10 - (- checkInfo);
	} else {
		showNum = checkInfo;
	}
	
	var mapDiv = map.getVMLDiv();
	var model = map.getMapModel();
	var currentZoom =  map.getCurrentZoom();
	var pointArray = new Array(2);
	
	pointArray[0] = accHistoryInfoList[checkInfo][3];
	pointArray[1] = accHistoryInfoList[checkInfo][4];
	
	if ((typeof pointArray[0] == "undefined" || pointArray[0] == "null"
			|| pointArray[0] == null || pointArray[0] == "")
			|| (typeof pointArray[1] == "undefined" || pointArray[1] == "null"
					|| pointArray[1] == null || pointArray[1] == "")) {
		alert("该信息没有定位！");
	} else {
		//闪烁显示事故点
		MapUtils.prototype.flashPoint(mapDiv, pointArray,currentZoom,2);
	}
}

/**
 * 清除标注点<br/>
 * 清除标注点处理
 */
function removeAccHistoryOnMap() {

		// 判断是否为空或者对象未被实例化
	if (typeof accHistoryInfoList == "undefined" || accHistoryInfoList == null
			|| accHistoryInfoList.length == 0) {
			accHistoryInfoList = null;
        //Modified by Liuwx 2012年3月14日18:27:42
			//var conCtrl= document.getElementById("daynameicInfo");
	        //conCtrl.innerHTML = "";
        //Modification finished
			return;
	}
	for (var i = 0; i < accHistoryInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		if ((typeof accHistoryInfoList[i][3] == "undefined"
				|| accHistoryInfoList[i][3] == "null"
				|| accHistoryInfoList[i][3] == null || accHistoryInfoList[i][3] == "")
				|| (typeof accHistoryInfoList[i][4] == "undefined"
						|| accHistoryInfoList[i][4] == "null"
						|| accHistoryInfoList[i][4] == null || accHistoryInfoList[i][4] == "")) {
		} else {
			var obj = document.getElementById(accHistoryInfoList[i][0]);
			obj && obj.parentNode.removeChild(obj);
		}
	}
		accHistoryInfoList = null;
		var conCtrl= document.getElementById("daynameicInfo");
    conCtrl.innerHTML = "";
}

/**
 * 点击标注点显示详细信息<br/>
 * 点击标注点显示详细信息的处理
 */
function responseAccHisClick(event, sgid) {
	// alert(11);
	var x = 0;
	var y = 0;
	var container;

	// 取得鼠标的绝对坐标

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
	// 显示正在查询提示
	MapTips.prototype.showLoadTips(container, x, y);
	// 执行查询获取当前选中的设备的信息
	var url = 'dynamicinfo.accaccHistory.getAccHistoryInfo.d';
	var conditionId = "";
	var params = "sgid=" + sgid;
	// 查询到结果后显示在气泡中
	new Ajax.Request(url, {
				method : 'post',
				parameters : params,

				onComplete : function(xmlHttp) {
					var strResponseText = xmlHttp.responseText;
					if (strResponseText == null) {
						return;
					}
					// 显示提示信息窗口
					MapTips.prototype.showPopup(container, "事故信息", strResponseText, x-10, y-20)
				}
			});
}

