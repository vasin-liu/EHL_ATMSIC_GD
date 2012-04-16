var forbidInfoList = null;
var maxlength = 0;
var ForbidtotalCount = 0;
var Forbidcount = 0;

/**
 * 获取管制点列表
 */
function getForbidHtml()
{
    forbidInfoList = null;
    maxlength = 0;
    ForbidtotalCount = 0;
    Forbidcount = 0;
	//形成查询条件
	var queryCond = "WHERE taa.ALARMID=tat.ALARMID and taa.X is not null and taa.Y is not null "
	+ " and taa.EVENTSTATE = '570003' " ;
			//"AND taa.CASEENDTIME >SYSDATE " +
			//"AND (SYSDATE BETWEEN taa.CASEHAPPENTIME AND taa.CASEENDTIME or taa.CASEHAPPENTIME BETWEEN SYSDATE AND (SYSDATE + 12 / 24)) " //+
			//"AND taa.CASEHAPPENTIME < (SYSDATE+12/24)";
	
	//形成SQL语句
	var strSql = "SELECT taa.ALARMID,taa.ROADNAME,decode(TAA.ROADDIRECTION,'1',(select tdd.end||'至'||tdd.begin from t_oa_dictdlfx tdd where tdd.dlmc=taa.roadid),'0',(select tdd.begin||'至'||tdd.end from t_oa_dictdlfx tdd where tdd.dlmc=taa.roadid)),taa.X,taa.Y FROM T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat ";
	if (queryCond != "") {
		strSql += queryCond;
	}
	strSql += "ORDER BY taa.ALARMID DESC";
	//prompt(strSql,strSql);
	
	//调用分页控制类
	PageCtrl.initPage("daynameicInfo","pageData","pageNav",convertSql(strSql),"showForbidList","10");
	//equipmap.readEquipPoints(deptId,SSXT,labelStat);		
	if(forbidInfoList==null || forbidInfoList.length==0){
   		var url = 'dynamicinfo.forbid.getAllForbidInfo.d';
		var params = encodeURI(params);
		new Ajax.Request(url, 
		{
				method: 'post', 
				parameters: params, 
				
				//读取完成后，放入内存数组
				onComplete:finishAllForbidInfo
		}); 
	}else{
		showForbidOnMap();
	}
}

function finishAllForbidInfo(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = "";
	var rowArray = null;
	forbidInfoList = new Array();
	for (var i = 0; i < results.length; i++)
	{
		result = results[i].childNodes;							
		if(result[3].text!="null"&&result[4].text!="null"){
	    	rowArray = new Array(5);
	    	rowArray[0] = result[0].text;//管制ID
	    	rowArray[1] = result[1].text;//路段名称
		    rowArray[2] = result[2].text;//方向
		    rowArray[3] = result[3].text;//X经度
		    rowArray[4] = result[4].text;//Y纬度
	    	forbidInfoList[i] = rowArray; //一条事故信息
	    }
	} 	
	showForbidOnMap(); 
}

/**
 * 获取设备列表回调函数
 * @xmlDoc: Ajax调用的回传数据:顺序是ALARMID，路段名称，方向
 */
function showForbidList(xmlDoc)
{		
	var strTable = "<table id=\"tabList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\"	>";			
	
	//生成表头
	strTable += "<tr class=\"scrollColThead\">";
	//strTable += "<th class=\"scrollRowThead scrollCR\" align=center width=12% background=\"../../image/back/title_back.gif\">选择</th>";
	//strTable += "<th style=\"display:none;\" align=\"center\" width=0% background=\"../../image/back/title_back.gif\"></th>";
	strTable += "<td class=\"scrollRowThead scrollCR\" align=\"center\" width=12%     background=\"../../image/back/title_back.gif\">选择</td>";
	strTable += "<td align=\"center\" width=58% background=\"../../image/back/title_back.gif\">路段名称</td>";
	strTable += "<td align=\"center\" width=20% background=\"../../image/back/title_back.gif\">方向</td>";
	strTable += "</tr>";	
	
	//生成结果页面
	var results = xmlDoc.getElementsByTagName("row");
	var result = "";
	var ALARMID = "";
	var ROADNAME = "";
	var ROADDIRECTION = "";
	var rowArray = null;
		Forbidcount = 0;
	for (var i = 0; i < results.length; i++)
	{
		result = results[i].childNodes;							
		ALARMID = result[0].text;
		ROADNAME = result[1].text;
		ROADDIRECTION = result[2].text;
		strTable += "<tr class=\"rowstyle\"  onclick=\"setForbidInfo('"+result[0].text+"','"+(i)+"','"+result[3].text+"','"+result[4].text+"');\" >";					    
	    strTable += "<td class=\"scrollRowThead\" align=\"center\"><input type=\"radio\" id=\""+i+"\" /></td>";
	    strTable += "<td style=\"display:none;\">" + ALARMID + "</td>";
	    strTable += "<td align=\"center\" >" + ROADNAME + "</td>";
	    strTable += "<td align=\"center\" >" + ROADDIRECTION + "</td>";				    					    
	    strTable += "</tr>";
	    Forbidcount = Forbidcount + 1;
	    ForbidtotalCount = ForbidtotalCount + 1;
	    maxlength = i;
	}
	strTable += "</table>";

	//添加到结果面板上
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
    var conCtrl= document.getElementById("pageData");
    conCtrl.innerHTML = strTable;
}

/**
 * 设置管制标注信息.
 * @id：ALARMID
 * @name：设备名称

 * @index：列表序号

 * @glid: 关联编号
 */
function setForbidInfo(checkId,checkInfo,X,Y)
{
	for (var i = 0; i < maxlength+1; i++) {
		var tableObj = document.getElementById("" + i + "");
		tableObj.checked = false;
	}
	var checkObj = document.getElementById(""+checkInfo+"");
	checkObj.checked = true;
	
	var showNum = 0;
	if (ForbidtotalCount > 10) {
		showNum = ForbidtotalCount - 10 - (- checkInfo);
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
 * 在地图上显示事故点
 */
function showForbidOnMap() {
	
	// 判断是否为空或者对象未被实例化
	if (typeof forbidInfoList == "undefined" || forbidInfoList == null || forbidInfoList.length == 0) {
		return;
	}
	// 获取地图图层和当前显示范围
	var mapDiv = map.getVMLDiv();
	var currentZoom = map.getCurrentZoom();
	
	// 坐标数据
	for (var i = 0; i < forbidInfoList.length; i++) {
		// 判断是否为空或者对象未被实例化
		if ((typeof forbidInfoList[i][3] == "undefined"
				|| forbidInfoList[i][3] == "null"
				|| forbidInfoList[i][3] == null || forbidInfoList[i][3] == "")
				|| (typeof forbidInfoList[i][4] == "undefined"
						|| forbidInfoList[i][4] == "null"
						|| forbidInfoList[i][4] == null || forbidInfoList[i][4] == "")) {
		} else {
			var infoCoord = new Coordinate((forbidInfoList[i][3] * 1e16), (forbidInfoList[i][4] * 1e16));// 将坐标转化为整形
			this.showForbidSymbol(mapDiv, infoCoord, currentZoom, i);// 在地图上绘制出当前的坐标
		}
	}
}


	
/**
 * 标注方法<br/> 
 * @param {Object}  mapDiv 地图图层对象
 * @param {Object}  infoCoord 经纬度坐标信息
 * @param {Object}  zoom 当前屏幕显示区域
 * @param {Integer} order forbidList行号
 */
function showForbidSymbol(mapDiv, infoCoord, zoom, order) {
	var scoord = Util.getScreenPixel(infoCoord, zoom);// 由经纬度转屏幕坐标.
	var strSymbolId = "forbidSymbolDiv_" + forbidInfoList[order][0];
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
	var image = Util.createImg(forbidInfoList[order][0],0, 0, 16, 16, "../../image/checkPoint/jtgz.gif");
//	image.title = name;
	image.style.cursor = "pointer";
//	image.position = infoCoord;
	// 添加双击处理事件和鼠标移开事件，双击显示气泡窗体，移开隐藏显示
//	Event.observe(image, "click");

	// 显示标注点
	Event.observe(image, "click",this.responseForbidClick.bindAsEventListener(image,forbidInfoList[order][0]));
	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	mapDiv.appendChild(pointSymbol);
}

/**
 * 点击标注点显示详细信息<br/>
 * 点击标注点显示详细信息的处理
 */
function responseForbidClick(event,alarmId) {
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
	var url = 'dynamicinfo.forbid.getForbidInfo.d';
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
					MapTips.prototype.showPopup(container, "管制信息",
							strResponseText, x-10, y-20)
				}
			});
}

function removeForbidOnMap() {
	// 判断是否为空或者对象未被实例化
	if (typeof forbidInfoList == "undefined" || forbidInfoList == null
			|| forbidInfoList.length == 0) {
		return;
	}

	for(var k=0;k<forbidInfoList.length;k+=1){
		// 判断是否为空或者对象未被实例化
			var tempdiv = document.getElementById("forbidSymbolDiv_" + forbidInfoList[k][0]);
			if(tempdiv != undefined){
				tempdiv.parentNode.removeChild(tempdiv);
			}
	
	}
	forbidInfoList = null;
	var conCtrl= document.getElementById("daynameicInfo");
  conCtrl.innerHTML = "";
}