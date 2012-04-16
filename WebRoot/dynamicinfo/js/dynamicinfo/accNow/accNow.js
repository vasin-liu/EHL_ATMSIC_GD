var accInfoList = null;
var accCount = 0;
var maxlength = 0;
var accTotalCount = 0;
//是否显示当日事故查询结果,
//默认为显示，否则只刷新地图，不显示查询结果，
//每次执行完成将isShowAccResult设为true
var isShowAccResult = true;
/**
 * 当天事故信息取得<br/>
 * 当天事故信息从警情表中取得。
 */
 function getAccNowHtml() {
    accInfoList = null;
    accCount = 0;
    maxlength = 0;
    accTotalCount = 0;
 	getAccInfo();
}
/**
 * 当天事故信息取得<br/>
 * 当天事故信息从警情表中取得。
 */
 function getAccInfo() {
 	
 	if (accInfoList == null || accInfoList.length == 0) {
		var url = 'dynamicinfo.accNow.getAllAccNowInfo.d';
		var params = "";
		params = encodeURI(params);
		new Ajax.Request(url, {
					method : 'post',
					parameters : params,

					// 读取完成后，放入内存数组
					onComplete : finishAllAccInfo
				});
	} else {
		showResultsPage();
	}
}

function finishAllAccInfo(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = "";
	var rowArray = null;
	accInfoList = new Array();
	for (var i = 0; i < results.length; i++)
	{
		result = results[i].childNodes;							
	    	rowArray = new Array(5);
	    	rowArray[0] = result[0].text;//报警单号
		    rowArray[1] = result[1].text;//报警时间
		    rowArray[2] = result[2].text;//事件状态
		    rowArray[3] = result[3].text;//经度
		    rowArray[4] = result[4].text;//纬度
	    	accInfoList[i] = rowArray; //一条事故信息
	}

 	// 报警单号,事件类型,事件状态,X,Y
	var sql = " select ALARMID," +
       			" (select name from t_attemper_code where id = '001024') EVENTTYPE," +
       			" (select name from t_attemper_code where id = EVENTSTATE) EVENTSTATE, x,Y" +
				" from T_ATTEMPER_ALARM_ZD" +
				" where to_char(ALARMTIME,'yyyy-mm-dd') = to_char(SYSDATE,'yyyy-mm-dd') " +
				" and x is not null and y is not null and EVENTSTATE != '004032' " +
				" order by ALARMTIME desc";
	PageCtrl.initPage(getPCId(isShowAccResult),"pageData","pageNav",convertSql(sql),"showAccResultsPage","10");
}


/**
 * 当天事故信息取得的回显处理<br/>
 * 当天事故信息取得的回显处理
 * @param {} xmldoc
 */
function showAccResultsPage(xmldoc) {
	 	//alert(2);
	var results = xmldoc.getElementsByTagName("row");
	
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\" >";
	strTable += "<td class=\"scrollRowThead scrollCR\" align=center width=12% background=\"../../image/back/title_back.gif\">选择</td>";
	strTable += "<td align=\"center\" width=30% background=\"../../image/back/title_back.gif\">事件类型</td>";
	strTable += "<td align=\"center\" width=50% background=\"../../image/back/title_back.gif\">事故状态</td>";
	strTable += "</tr>";
	var result = "";
	var rowArray = null;
	accCount = 0;
	for (var i = 0; i < results.length; i++) {
		rowArray = new Array(5);
		result = results[i].childNodes;
		strTable += "<tr class=\"rowstyle\"  onclick=\"showAccPoint('"+result[0].text+"','"+(i)+"','"+result[3].text+"','"+result[4].text+"');\" >";					    
	    strTable += "<td class=\"scrollRowThead\" align=\"center\"><input type=\"radio\" id=\""+i+"\" /></td>";
	    strTable += "<td align=\"center\" >" + result[1].text + "</td>";
	    strTable += "<td align=\"center\" >" + result[2].text  + "</td>";							    					    
	    strTable += "</tr>";
	    accTotalCount = accTotalCount + 1;
	    accCount = accCount + 1;
	    maxlength = i;
	}
	strTable += "</table>";
	var tabOjb = document.getElementById("pageData");
	if(isShowAccResult){
		tabOjb.innerHTML = strTable;
	}else{
		isShowAccResult = true;
	}
	showAllAccPoint();
}

/**
 * 在地图上显示事故点
 */
function showAllAccPoint() {
	
	// 判断是否为空或者对象未被实例化
	if (typeof accInfoList == "undefined" || accInfoList == null
			|| accInfoList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	var currentZoom = map.getCurrentZoom();
	
	// 坐标数据
	for (var i = 0; i < accInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		var infoCoord = new Coordinate((accInfoList[i][3] * 1e16), (accInfoList[i][4] * 1e16));// 将坐标转化为整形
		this.showAccSymbol(mapDiv, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
		
	}
}
	
/**
 * 标注方法<br/> 
 * @param {Object}  mapDiv 地图图层对象
 * @param {Object}  infoCoord 经纬度坐标信息
 * @param {Object}  zoom 当前屏幕显示区域
 * @param {Integer} order equipList行号
 */
function showAccSymbol(mapDiv, infoCoord, zoom, order) {
	var scoord = Util.getScreenPixel(infoCoord, zoom);// 由经纬度转屏幕坐标.
	var strSymbolId = "'" + accInfoList[order][0] + "'";
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
	var image = Util.createImg(accInfoList[order][0],0, 0, 16, 16, "../../image/checkPoint/accNow.gif");
	image.style.cursor = "pointer";
	// 显示标注点
	Event.observe(image, "click",this.responseAccClick.bindAsEventListener(image,accInfoList[order][0]));

	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	mapDiv.appendChild(pointSymbol);
}

/**
 * 标注点闪烁显示处理<br/>
 * 标注点闪烁显示处理
 */
function showAccPoint(checkId,checkInfo,X,Y) {
	//alert("AA" +"     " + checkInfo + "      " + count);
	for (var i = 0; i < maxlength+1; i++) {
		var tableObj = document.getElementById("" + i + "");
		tableObj.checked = false;
	}
	var checkObj = document.getElementById(""+checkInfo+"");
	checkObj.checked = true;
	var showNum = 0;
	if (accTotalCount > 10) {
		showNum = accTotalCount - 10 - (- checkInfo);
	} else {
		showNum = checkInfo;
		}
	
	var mapDiv = map.getVMLDiv();
	var model = map.getMapModel();
	var currentZoom =  map.getCurrentZoom();
	var pointArray = new Array(2);
	pointArray[0] = X;
	pointArray[1] = Y;	
	
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
function removeAccNowOnMap() {
	// 判断是否为空或者对象未被实例化
	if (typeof accInfoList == "undefined" || accInfoList == null
			|| accInfoList.length == 0) {
		accInfoList = null;
        //Modified by Liuwx 2012年3月14日18:27:42
		//var conCtrl= document.getElementById("daynameicInfo");
        //conCtrl.innerHTML = "";
        //Modification finished
		return;
	}
	for (var i = 0; i < accInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		var obj = document.getElementById(accInfoList[i][0]);
		obj && obj.parentNode.removeChild(obj);
	}
		accInfoList = null;
		var conCtrl= document.getElementById("daynameicInfo");
    	conCtrl.innerHTML = "";
}

/**
 * 点击标注点显示详细信息<br/>
 * 点击标注点显示详细信息的处理
 */
function responseAccClick(event,alarmId) {
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
	var url = 'dynamicinfo.accNow.getAccNowInfo.d';
	var conditionId = "";
	var params = "alarmId=" + alarmId;
	//查询到结果后显示在气泡中		
	new Ajax.Request(url, {
				method : 'post',
				parameters : params,

				onComplete : function(xmlHttp) {
					var strResponseText = xmlHttp.responseText;
					if (strResponseText == null) {
						return;
					}
					//显示提示信息窗口			   
					MapTips.prototype.showPopup(container, "重特大事故信息",
							strResponseText, x-10, y-20)
				}
			});
}