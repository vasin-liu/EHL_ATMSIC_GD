var tgsInfoList = null;
var TGSFlowcount = 0;
var maxlength = 0;
var searchDayFly = "";
var TGSFlowtotalCount = 0;
//是否显示省际出入口查询结果,
//默认为显示，否则只刷新地图，不显示查询结果，
//每次执行完成将isShowTGSResult设为true
var isShowTGSResult = true;
/**
 * 卡口及视频信息获取<br/>
 */
 function getTGSFlowHtml(){
 	TGSFlowtotalCount = 0;
 	tgsInfoList = null;
 	TGSFlowcount = 0;
 	searchDayFly = "";
 	maxlength = 0;
 	var mydate = new Date().format("yyyy年MM月dd日");
 	var str = " <span > &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
/* 		str = str + "<a href=\"#\" onclick=\"getTgsInfo('0');\">今日</a> &nbsp&nbsp&nbsp";
		str = str + "<a href=\"#\" onclick=\"getTgsInfo('-1');\">前一日</a>&nbsp&nbsp&nbsp";
		str = str + "<a href=\"#\" onclick=\"getTgsInfo('-2');\">前二日</a>&nbsp&nbsp&nbsp";
		str = str + "<a href=\"#\" onclick=\"getTgsInfo('-3');\">前三日</a>&nbsp&nbsp&nbsp";*/
		str = str + "<div id=\"tgsDiv\" ></div>";
 	
 	$("daynameicInfo").innerHTML = str;
 	
 	if(tgsInfoList == null || tgsInfoList.length == 0){
		var url = 'dynamicinfo.tgs.getAllTgsInfo.d';
		var params = "";
		params = encodeURI(params);
		new Ajax.Request(url, {
			method : 'post', parameters : params,
			// 读取完成后，放入内存数组
			onComplete : finishAllTgsInfo
		});
	} else {
		showTgsResultsPage();
	}
	
	getTgsInfo('0');
}
//卡口&视频信息回调
function finishAllTgsInfo(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = "";
	var rowArray = null;
	tgsInfoList = new Array();
	for(var i = 0; i < results.length; i++){
		result = results[i].childNodes;							
    	rowArray = new Array(7);
    	rowArray[0] = result[0].text;// 设备id
	    rowArray[1] = result[1].text;//卡口名称
	    rowArray[2] = result[2].text;//经度
	    rowArray[3] = result[3].text;//纬度
	    rowArray[4] = result[4].text;//关联id
	    rowArray[5] = result[5].text;//设备类型
	    rowArray[6] = result[6].text;//使用状态
    	tgsInfoList[i] = rowArray; //一条卡口信息
	}
}

/**
 * 当天事故信息取得<br/>
 * 当天事故信息从警情表中取得。
 */
function getTgsInfo(dayFly) {
	searchDayFly = dayFly;
 	
	//设备id，卡口名称，经度，纬度,使用状态
	var sql = "SELECT SID,CPMC,LONGITUDE,LATITUDE,SYZT FROM ATMS_EQUIPMENT_ZB" + 
	 				" WHERE SSXT = 'TGS' AND LONGITUDE != 0 AND LATITUDE != 0 ORDER BY SID ASC ";
	PageCtrl.initPage(isShowTGSResult?"tgsDiv":"","pageData","pageNav",convertSql(sql),"showTgsResultsPage","10");
}

/**
 * 当天事故信息取得的回显处理<br/>
 * 当天事故信息取得的回显处理
 * @param {} xmldoc
 */
function showTgsResultsPage(xmldoc) {	
	var results = xmldoc.getElementsByTagName("row");
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\" >";
	strTable += "<td class=\"scrollRowThead scrollCR\" align=center width=12% background=\"../../image/back/title_back.gif\">选择</td>";
	strTable += "<td align=\"center\" width=40% background=\"../../image/back/title_back.gif\">卡口名称</td>";
	//Modify by Xiayx 2011-11-3
	//隐藏统计分析
//	strTable += "<td align=\"center\" width=40% background=\"../../image/back/title_back.gif\">统计分析</td>";
	//Modification finished
	strTable += "</tr>";
	var result = "";

	TGSFlowcount = 0;
	for (var i = 0; i < results.length; i++) {
		result = results[i].childNodes;
		strTable += "<tr >";					    
	    strTable += "<td class=\"rowstyle\" class=\"scrollRowThead\" align=\"center\"><input type=\"radio\" id=\""+i+"\" " +
	    "onclick=\"showTgsPoint('"+result[0].text+"','"+(i) + "','"+ result[1].text +"','"+ result[2].text+"','"+
	    result[3].text +"');\" /></td>";
	    strTable += "<td align=\"center\">" + result[1].text  + "</td>";
	    //Modify by Xiayx 2011-11-3
		//隐藏统计分析
//	    strTable += "<td align=\"center\">" + '<a href="#" onclick="showTotalInfo( \''+ result[1].text +'\');">查看统计信息</a>' + "</td>";
	    //Modification finished
	    strTable += "</tr>";

	    TGSFlowcount = TGSFlowcount + 1;
	    TGSFlowtotalCount = TGSFlowtotalCount + 1;
	    maxlength = i;
	}
	strTable += "</table>";
	strTable += "<div id='deatailInfo' > </div>";
	var tabOjb = document.getElementById("pageData");
	if(isShowTGSResult){
		tabOjb.innerHTML = strTable;
	}else{
		isShowTGSResult = true;
	}
	//在地图标绘卡口
	showAllTgsFlowPoint();
}

/**
 * 在地图上显示事故点
 */
function showAllTgsFlowPoint() {
	// 判断是否为空或者对象未被实例化
	if (typeof tgsInfoList == "undefined" || tgsInfoList == null || tgsInfoList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	var currentZoom = map.getCurrentZoom();
	// 坐标数据
	for (var i = 0; i < tgsInfoList.length; i++) {
		var infoCoord = new Coordinate((tgsInfoList[i][2] * 1e16), (tgsInfoList[i][3] * 1e16));// 将坐标转化为整形
		this.showTgsSymbol(mapDiv, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
	}
}
	
/**
 * 标注方法<br/> 
 * @param {Object}  mapDiv 地图图层对象
 * @param {Object}  infoCoord 经纬度坐标信息
 * @param {Object}  zoom 当前屏幕显示区域
 * @param {Integer} order equipList行号
 */
function showTgsSymbol(mapDiv, infoCoord, zoom, order) {
	var scoord = Util.getScreenPixel(infoCoord, zoom);// 由经纬度转屏幕坐标.
	var strSymbolId = "'" + tgsInfoList[order][0] + "'";
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
	var image = null;
	if(tgsInfoList[order][6]==1){
		image = Util.createImg(tgsInfoList[order][0],0, 0, 16, 16, "../../image/checkPoint/tgs1.gif");
	}else{
		image = Util.createImg(tgsInfoList[order][0],0, 0, 16, 16, "../../image/checkPoint/tgs2.gif");
	}
	image.style.cursor = "pointer";
	image.alt = tgsInfoList[order][1];
	// 显示标注点
	Event.observe(image, "click",this.responseTgsClick.bindAsEventListener(image,tgsInfoList[order][0],tgsInfoList[order][1],tgsInfoList[order][4],tgsInfoList[order][5]));

	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	mapDiv.appendChild(pointSymbol);
}

/**
 * 标注点闪烁显示处理<br/>
 * 标注点闪烁显示处理
 */
function showTgsPoint(checkId,checkInfo,tgsName,X,Y) {
	for (var i = 0; i < maxlength+1; i++) {
		var tableObj = document.getElementById("" + i + "");
		tableObj.checked = false;
	}
	var checkObj = document.getElementById(""+checkInfo+"");
	checkObj.checked = true;
		var showNum = 0;
	if (TGSFlowtotalCount > 10) {
		showNum = TGSFlowtotalCount - 10 - (- checkInfo);
	}else{
		showNum = checkInfo;
	}
		
	var mapDiv = map.getVMLDiv();
	var model = map.getMapModel();
	var currentZoom =  map.getCurrentZoom();
	var pointArray = new Array(2);
	pointArray[0] = X;
	pointArray[1] = Y;	
	
	if ((typeof pointArray[0] == "undefined" || pointArray[0] == "null" || pointArray[0] == null || pointArray[0] == "")
			|| (typeof pointArray[1] == "undefined" || pointArray[1] == "null" || pointArray[1] == null || pointArray[1] == "")) {
		alert("该信息没有定位！");
	} else {
		//alert(tgsInfoList[showNum]);
		//闪烁显示卡口点
		MapUtils.prototype.flashPoint(mapDiv, pointArray,currentZoom,2);
		//显示选中的卡口点详细信息				
		var infoCoord = new Coordinate(X*1e16 , Y*1e16);
		var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
		var left = scoord.x;
		var top =  scoord.y;	
		//alert(X+"||"+Y);
		try{
			this.responseTgsClick(this,tgsInfoList[showNum][0],tgsInfoList[showNum][1],tgsInfoList[showNum][4],tgsInfoList[showNum][5]);
		}catch(e){
			//alert(e);
		}
	}
}

function showTotalInfo (tgsName){
	var str1 = "tgsName=" + tgsName;
	str1 = encodeURI(str1);
	window.showModalDialog("../../../dispatch/ehl/bayonetcartogram/BayonetCartogram.jsp?" + str1, "", "dialogWidth:800px;dialogHeight:600px");
}

/**
 * 清除标注点<br/>
 * 清除标注点处理
 */
function removeTGSFlowOnMap() {
	// 判断是否为空或者对象未被实例化
	if (typeof tgsInfoList == "undefined" || tgsInfoList == null || tgsInfoList.length == 0) {
		tgsInfoList = null;
        //Modified by Liuwx 2012年3月14日18:27:42
        //var conCtrl= document.getElementById("daynameicInfo");
        //conCtrl.innerHTML = "";
        //Modification finished
		return;
	}
	for (var i = 0; i < tgsInfoList.length; i++) {
		var obj = document.getElementById(tgsInfoList[i][0]);
		obj && obj.parentNode.removeChild(obj);
	}

	tgsInfoList = null;
    //Modified by Liuwx 2012年3月14日18:27:42
    //var conCtrl= document.getElementById("daynameicInfo");
    //conCtrl.innerHTML = "";
    //Modification finished
}

/**
 * 点击标注点显示详细信息<br/>
 * 点击标注点显示详细信息的处理
 */
function responseTgsClick(event, tgsId, tgsName,GLID,SSXT) {
	var x = 0;
	var y = 0;
	var container;

	// 取得鼠标的绝对坐标
	container = map.getVMLDiv();
	if (event != null) {
		event = event ? event : (window.event ? window.event : null);
		var source = event.srcElement || event.target;
		x = Event.pointerX(event) - container.offsetLeft || container.scrollLeft;
		y = Event.pointerY(event) - container.offsetTop || container.scrollTop;
	} else {
//		x = left + 8;
//		y = top;
	}
	// 显示正在查询提示
	MapTips.prototype.showLoadTips(container, x, y);
	// 执行查询获取当前选中的设备的信息
	var url = 'dynamicinfo.tgs.getTgsInfo.d';
//	alert(searchDayFly);
	var params = "tgsId=" + tgsId + "&tgsName=" + tgsName + "&searchDayFly=" + searchDayFly + "&GLID=" + GLID + "&SSXT=" + SSXT;
	// 查询到结果后显示在气泡中
	new Ajax.Request(url, {
		method : 'post',
		parameters : params,
		onComplete : function(xmlHttp) {
			var strResponseText = xmlHttp.responseText;
			if (strResponseText == null) {
				strResponseText="";
			}
			var privi = PriviFactory.getPrivi("showVidicon");
			privi.func = "590100";
			strResponseText = privi.ctrl(window.$funcs,strResponseText);
			// 显示提示信息窗口
			MapTips.prototype.showPopup(container, tgsName,strResponseText, x-10, y-20)
		}
	});
}



