var pcsInfoList = null;
var PCScount = 0;
var maxlength = 0;
var jgid = "";
var jgbh = "";
var pcs_map_zoomlevel = "";//地图级数变量
var pcs_map_pcsPointList = new Array();//点对象数组
var pcs_map_div = ""; 
var PCStotalCount = 0;

//取所有车辆的所属部门
function getPCSHtml() {
	pcsInfoList = null;
	PCScount = 0;
	maxlength = 0;
	PCStotalCount = 0;
    pcs_map_zoomlevel = map.getCurrentZoom();//初始化地图级数
	jgid = $("jgid").value;
	jgbh = $("jgbh").value;
	
	if (pcsInfoList == null || pcsInfoList.length == 0) {
		//var url = 'dynamicinfo.gpsCar.getAllgpsCarInfo.d';
		var url = 'dynamicinfo.gpsCar.getAllgpsCarDepartInfo.d';
		var params = "jgid=" + jgid + "&jgbh=" + jgbh;
		params = encodeURI(params);
		new Ajax.Request(url, {
					method : 'post',
					parameters : params,

					// 读取完成后，放入内存数组
					onComplete : finishAllPcsDepartInfo
				});
	} else {
		showPcsResultsPage();
	}

} 

//取得指定部门的GPS车辆
function getCarHtml(departName) {
	pcsInfoList = null;
	PCScount = 0;
	maxlength = 0;
	PCStotalCount = 0;
    pcs_map_zoomlevel = map.getCurrentZoom();//初始化地图级数
	jgid = $("jgid").value;
	jgbh = $("jgbh").value;
	
	if (departName=="全部显示"){departName="";}
	if (pcsInfoList == null || pcsInfoList.length == 0) {
		//var url = 'dynamicinfo.gpsCar.getAllgpsCarInfo.d';
		var url = 'dynamicinfo.gpsCar.getAllgpsCarInfo.d';
		var params = "jgid=" + jgid + "&jgbh=" + jgbh + "&departName=" + departName;
		params = encodeURI(params);
		new Ajax.Request(url, {
					method : 'post',
					parameters : params,

					// 读取完成后，放入内存数组
					onComplete : finishAllPcsInfo
				});
	} else {
		showPcsResultsPage();
	}

} 

//取得指定车辆
function finishAllPcsInfo(xmlHttp){

	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = "";
	var rowArray = null;
	pcsInfoList = new Array();
    var whereStr2;
	
	for (var i = 0; i < results.length; i++)
	{
		result = results[i].childNodes;							
	    	rowArray = new Array(5);
	    	rowArray[0] = result[0].text;//车辆编号
		    rowArray[1] = result[1].text;//车牌号码
		    rowArray[2] = result[2].text;//部门名称
		    rowArray[3] = result[3].text;//车辆状态
		    rowArray[4] = result[4].text;//经度
		    rowArray[5] = result[5].text;//纬度
		    //alert(result[1].text);
	    	pcsInfoList[i] = rowArray; //一条事故信息
	    	if (i==0){
	    		whereStr2 = "'" + result[1].text + "'";
	    	}else{
	    		whereStr2 = "'" + result[1].text + "'," + whereStr2;
	    	}
	    	
	    	
	}
	var whereStr = " and to_char(GPSTIME, 'yyyymmdd') = to_char(sysdate, 'yyyymmdd') " + 
   			" and to_char(GPSTIME, 'hh24') <= to_char(sysdate, 'hh24') " + 
   			" and to_number(to_char(sysdate, 'hh24')) < (to_number(to_char(GPSTIME, 'hh24')) + 2)";
		
	// 车辆编号,车牌号码,部门名称,车辆状态,X,Y
	var sql = "select CARCODE,CARNUMBER,DEPARTMENT,CARSTATE,LONGTITUDE,LATITUDE from T_GPS_CARINFO where " +
			"LONGTITUDE != 0 and LATITUDE !=0  " + whereStr + " and carnumber in (" + whereStr2 + ")"+
			" order by CARNUMBER";
	PageCtrl.initPage("pcs_datalist", "pageData", "pageNav", 
		convertSql(sql),"showPcsResultsPage", "10","gps","select count(1) from T_GPS_CARINFO");

}

//显示所有拥有GPS车辆的部门
function finishAllPcsDepartInfo(xmlHttp){

	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = "";
	var rowArray = null;
	

	var str ="<div id='pcs_div' style='overflow:hidden; cursor:hand;width:308px;'> " +
	            "<table id='all_tb' width='200%' height='100%' style='margin:0px 0px 0px 0px; padding:0px 0px 0px 0px; border:0px 0px 0px 0px;'> " + 
	               "<tr><td width='50%'><table id='left_tb' width='100%' >" +
	               "<tr><td title='点击显示明细'  height='20px' width='100%' align='center' onmouseover='tb_mouseover(this);' onmouseout='tb_mouseout(this);'>" +
	               "全部显示</td></tr>";
	for (var i = 0; i < results.length; i++)
	{
		result = results[i].childNodes;	
		str += "<tr id='list'> " +
		"<td title='点击显示明细' height='20px' align='center' style='cursor:pointer;' onmousedown='tb_mousedown(this);' onmouseover='tb_mouseover(this);' onmouseout='tb_mouseout(this);'>"
		+ result[0].text + "</td> </tr> ";
	    	//result[0].text;//地区名称
		    //alert(result[0].text);
	    	
	}
	str += "</table > </td> <td>" +
	"<table id='center_tb' width='100%' height='100%' >" +
	"<tr> <td align='top' height='20px' onmousedown='right_tb_mousedown();'>返回</td></tr>" +
	"<tr><td style='verticalAlign:top;'>" +
	"<div id='pcs_datalist' height='100%' width='100%' style='verticalAlign:top;'></div>" +
	"</tr></td></table> </td> </tr> </table></div> ";
    document.getElementById("daynameicInfo").innerHTML = str;
    //document.document.getElementById("pcs_div").style.width=document.getElementById("daynameicInfo").offsetWidth
}

function showPcsResultsPage(xmldoc) {

	var results = xmldoc.getElementsByTagName("row");
	
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 " +
			"cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\" >";
	strTable += "<td class=\"scrollRowThead scrollCR\" align=center width=12% background=\"../../image/back/title_back.gif\">选择</td>";
	strTable += "<td align=\"center\" width=30% background=\"../../image/back/title_back.gif\">车牌号码</td>";
	strTable += "<td align=\"center\" width=50% background=\"../../image/back/title_back.gif\">车辆状态</td>";
	strTable += "</tr>";
	var result = "";
	var rowArray = null;
	PCScount = 0;
	for (var i = 0; i < results.length; i++) {
		rowArray = new Array(6);
		result = results[i].childNodes;
		strTable += "<tr class=\"rowstyle\"  onclick=\"showPcsPoint('"+result[0].text+"','"+(i)+"','"+result[4].text+"','"+result[5].text+"');\" >";					    
	    strTable += "<td class=\"scrollRowThead\" align=\"center\"><input type=\"radio\" id=\""+i+"\" /></td>";
	    strTable += "<td  align=\"center\" >" + result[1].text + "</td>";
	    strTable += "<td  align=\"center\" >" + result[3].text  + "</td>";							    					    
	    strTable += "</tr>";
	    PCScount = PCScount + 1;
	    PCStotalCount = PCStotalCount + 1;
	    maxlength = i;
	    
	}
	strTable += "</table>";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = strTable;
	showAllPcsPoint();
}

/**
 * 在地图上显示车辆地点
 */
function showAllPcsPoint() {
    pcs_map_div = document.createElement("div");
    pcs_map_div.setAttribute("id","pcsdiv");
    
	// 判断是否为空或者对象未被实例化
	if (typeof pcsInfoList == "undefined" || pcsInfoList == null
			|| pcsInfoList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	mapDiv.appendChild(pcs_map_div);
	var currentZoom = map.getCurrentZoom();
	
	// 坐标数据
	for (var i = 0; i < pcsInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		var infoCoord = new Coordinate((pcsInfoList[i][4] * 1e16), (pcsInfoList[i][5] * 1e16));// 将坐标转化为整形
		this.showPcsSymbol(pcs_map_div, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
	}
}

/**
 * 在地图状态变化，根据情况加载车辆
 */
function listenPcsMapChange() {
	if(pcs_map_zoomlevel == map.getCurrentZoom()){
	    pcs_map_zoomlevel = map.getCurrentZoom();
	    return;
	}
	pcs_map_zoomlevel = map.getCurrentZoom();
	// 判断是否为空或者对象未被实例化
	if (typeof pcsInfoList == "undefined" || pcsInfoList == null
			|| pcsInfoList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	var currentZoom = map.getCurrentZoom();
	
	// 坐标数据
	for (var i = 0; i < pcsInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		var infoCoord = new Coordinate((pcsInfoList[i][4] * 1e16), (pcsInfoList[i][5] * 1e16));// 将坐标转化为整形
		var pcspoint = pcs_map_pcsPointList[i];
		if(pcspoint){
		
		    var scoord = Util.getScreenPixel(infoCoord, map.getCurrentZoom());// 由经纬度转屏幕坐标.
		    pcspoint.style.left = scoord.x + "px";
		    pcspoint.style.top = scoord.y + "px";
		}else{
		    this.showPcsSymbol(pcs_map_div, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
		}
	}
}

/**
 * 标注点闪烁显示处理<br/>
 * 标注点闪烁显示处理
 */
function showPcsPoint(checkId,checkInfo,X,Y) {
 
	for (var i = 0; i < maxlength+1; i++) {
		var tableObj = document.getElementById("" + i + "");
		tableObj.checked = false;
	}
	var checkObj = document.getElementById(""+checkInfo+"");
	checkObj.checked = true;
	var showNum = 0;
	if (PCStotalCount > 10) {
		showNum = PCStotalCount - 10 - (- checkInfo);
	} else {
		showNum = checkInfo;
		}
	var mapDiv = map.getVMLDiv();
	var model = map.getMapModel();
	var currentZoom =  map.getCurrentZoom();
	var pointArray = new Array(2);
	//alert(showNum);
	pointArray[0] = X;
	pointArray[1] = Y;	
	//alert(pointArray.join("....."));
	if ((typeof pointArray[0] == "undefined" || pointArray[0] == "null"
			|| pointArray[0] == null || pointArray[0] == "")
			|| (typeof pointArray[1] == "undefined" || pointArray[1] == "null"
					|| pointArray[1] == null || pointArray[1] == "")) {
		alert("该信息没有定位！");
	} else {
		//闪烁显示事故点
		 //移动并缩放地图
		var centPoint = "";
		var coordX = Number(pointArray[0]) * 1e16;
		var coordY = Number(pointArray[1]) * 1e16;
		centPoint = new Point(coordX, coordY);		
		map.moveToCenter(centPoint,6);
		MapUtils.prototype.flashPoint(mapDiv, pointArray,map.getCurrentZoom(),2);
	}
}

/**
 * 标注方法<br/> 
 * @param {Object}  mapDiv 地图图层对象
 * @param {Object}  infoCoord 经纬度坐标信息
 * @param {Object}  zoom 当前屏幕显示区域
 * @param {Integer} order equipList行号
 */
function showPcsSymbol(mapDiv, infoCoord, zoom, order) {

	var scoord = Util.getScreenPixel(infoCoord, zoom);// 由经纬度转屏幕坐标.
	var strSymbolId = pcsInfoList[order][0];
//	var strSymbolId = "'" + "accPoint" + "'";
	// 判断是否已经存在
	var pointSymbol = document.getElementById(strSymbolId);
	if (pointSymbol != null) {
		pointSymbol.parentNode.removeChild(pointSymbol);
	}
	// 判断是否处在需要显示的级别上.
//	var currLevel = map.getCurrLevel();
//	if (currLevel < Number(getMapParameter("gisDeviceLevel"))) {
//		return;
//	}
	pointSymbol = document.createElement("div");
	pointSymbol.id = strSymbolId;
	pointSymbol.style.position = "absolute";
	pointSymbol.style.border = "0px solid #999999";
	pointSymbol.style.fontSize = "12px";
	pointSymbol.style.padding = "1px";
	pointSymbol.style.zIndex = 11;
	pointSymbol.style.left = scoord.x + "px";
	pointSymbol.style.top = scoord.y + "px";
	var image = Util.createImg(pcsInfoList[order][0],0, 0, 16, 16, "../../image/checkPoint/jl.gif");
	image.style.cursor = "pointer";
	// 显示标注点
	Event.observe(image, "click",this.responsePcsClick.bindAsEventListener(image,pcsInfoList[order][0]));

	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	mapDiv.appendChild(pointSymbol);
	pcs_map_pcsPointList.push(pointSymbol);
}

/**
 * 点击标注点显示详细信息<br/>
 * 点击标注点显示详细信息的处理
 */
function responsePcsClick(event,carCode) {
	var x = 0;
	var y = 0;
	var container;

	//取得鼠标的绝对坐标

	container = map.getVMLDiv();
	if (event != null) {
		event = event ? event : (window.event ? window.event : null);
		var source = event.srcElement || event.target;
		x = Event.pointerX(event) - container.offsetLeft
				|| container.scrollLeft ;
		y = Event.pointerY(event) - container.offsetTop || container.scrollTop;
	} else {
		x = left;
		y = top;
	}
	x -= 10;
	y-=20;
	//显示正在查询提示		
	MapTips.prototype.showLoadTips(container, x, y);
	//执行查询获取当前选中的设备的信息
	var url = 'dynamicinfo.gpsCar.getGpscarInfo.d';
	var conditionId = "";
	var params = "jgid=" + jgid + "&jgbh=" + jgbh + "&carCode=" + carCode;
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
					MapTips.prototype.showPopup(container, "GPS车辆信息", strResponseText, x, y)
				}
			});
}
/**
 * 清除标注点<br/>
 * 清除标注点处理
 */
function removePCSOnMap() {
     //删除点div
     var mapDiv = map.getVMLDiv(); 
     var divs = mapDiv.getElementsByTagName("div");
     var len = divs.length;
     while(len > 0){
        if(divs[len-1].id == "pcsdiv"){
            mapDiv.removeChild(divs[len-1]);
        }
        len--;
     }
     //释放资源
     pcs_map_zoomlevel = "";
     pcs_map_pcsPointList = new Array();
     pcs_map_div = "";
	// 判断是否为空或者对象未被实例化
	
	if (typeof pcsInfoList == "undefined" || pcsInfoList == null
			|| pcsInfoList.length == 0) {
		return;
	}
//	for (var i = 0; i < pcsInfoList.length; i++) {
//		// 判断是否为空或者对象未被实例化
//		var obj = document.getElementById(pcsInfoList[i][0]);
//		obj.parentNode.removeChild(obj);
//	}
	pcsInfoList = null;
	var conCtrl= document.getElementById("daynameicInfo");
    conCtrl.innerHTML = "";
    
    
    
}

//   ============================================================
//   ===========================效果函数==========================
//   ============================================================

	var IsLoop =false;
	var StepNum,edge;
	var thi ;
	function tb_mouseover(sender) {
		sender.style.backgroundColor="#FFEDEC";
		sender.style.verticalAlign="top";	
	}
	
	function tb_mouseout(sender) {
		sender.style.backgroundColor="";
		sender.style.verticalAlign="middle";	
		
	}
	
	function tb_mousedown(sender) {
		StepNum=20;
		edge=document.getElementById("left_tb").offsetWidth + 6;
		thi = document.getElementById("pcs_div");
		IsLoop=true;
		loops();
		getCarHtml(sender.innerText)
	}
	
	function right_tb_mousedown() {
		IsLoop=true;
		StepNum=-20;
		loops();
	}
	
	function loops() {
		if (IsLoop){
			if(thi.scrollLeft > edge || thi.scrollLeft < 0) {
				thi.scrollLeft = edge;
				return;
				}
			thi.scrollLeft = thi.scrollLeft + StepNum;
			setTimeout("loops()",20);
		}
	}
	
//   ======================================================================	

