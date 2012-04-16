/**
 * 引入库文件
 * @param {Object} src
 */

function include(src){
    HTMLCode = '<script language="javascript" src="' + src + '"></script>';
    document.write(HTMLCode);
}

var showFlg = true;
function showOtherMap() {
	if(showFlg) {
		gismappathStr = "/maps/gzzd2/";
		showFlg = false;
	} else {
		gismappathStr = "/maps/gzzd/";
		showFlg = true;
	}
	var mt = new SZMapType(gismappathStr,'map1.gif','map2.gif', 1, 1);	
	map.addMapType(mt, true);
	
	var centPoint = "";
	var coordX = Number("113.484894499141") * 1e16;
	var coordY = Number("22.8715050004173") * 1e16;
	centPoint = new Point(coordX, coordY);		
	map.moveToCenter(centPoint,4);
	map.moveToCenter(centPoint,3);
}

function showfangweiMap() {
	//var des = new DES("a8e5d4f8");//创建对象，赋予密钥
	//var time = new Date();
	//var encryptResult = des.encrypt(userName + "__" + time.now().toString());//2010-10-01 22:12:03");

		//System.out.println(encryptResult + time.now().toString());
	var url = "http://10.40.30.31/SL_GIST/main.aspx?username=" + encryptResult;
	//alert(times);
	window.open (url, 'newwindow', 'toolbar=yes,menubar=yes, scrollbars=yes, resizable=yes,location=no, status=yes');
	//System.out.println(url);
}

Util = new Object();

/**
 * 取得一个ID
 * @param {Object} prefix
 */
Util.createUniqueID = function(prefix){
    if (prefix == null) {
        prefix = "id_";
    }
    return prefix + Math.round(Math.random() * 10000000);
};

/**
 * 取得像素值
 * @param {Object} valueString
 */
Util.getValueOfNoPX = function(valueString){
    if (!valueString) {
		return;
	}
	
    if (valueString.indexOf("px")) {
        var i = valueString.indexOf("px");
        return Number(valueString.substring(0, i));
    }
    return Number(valueString);
};

/**
 * 取得真正的地图宽度.
 * @param {Object} fullExtent
 */
Util.getRealMapWidth = function(fullExtent){
    return Util.distanceByLnglat(fullExtent.getMinX() / 1e16, fullExtent.getMaxY() / 1e16, fullExtent.getMaxX() / 1e16, fullExtent.getMaxY() / 1e16);
};

/**
 * 取得真正的地图高度.
 * @param {Object} fullExtent
 */
Util.getRealMapHeight = function(fullExtent){
    return Util.distanceByLnglat(fullExtent.getMinX() / 1e16, fullExtent.getMinY() / 1e16, fullExtent.getMinX() / 1e16, fullExtent.getMaxY() / 1e16);
};

/**
 * 计算两点间的距离.
 * @param {Object} lng1
 * @param {Object} lat1
 * @param {Object} lng2
 * @param {Object} lat2
 */
Util.distanceByLnglat = function(lng1, lat1, lng2, lat2){
    var radLat1 = Util.Rad(lat1);
    var radLat2 = Util.Rad(lat2);
    var a = radLat1 - radLat2;
    var b = Util.Rad(lng1) - Util.Rad(lng2);
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
    s = Math.round(s * 10000) / 10000;
    return s;
};

/**
 * 转换成弧度.
 * @param {Object} d
 */
Util.Rad = function(d){
    return d * Math.PI / 180.0;
}

/**
 * @param {Object} level
 */
Util.zoomScale = function(level){
    var scale;
    switch (level) {
        case 1:
            scale = getMapParameter("gisScaleLevel_1");
            break;
        case 2:
            scale = getMapParameter("gisScaleLevel_2");
            break;
        case 3:
            scale = getMapParameter("gisScaleLevel_3");
            break;
        case 4:
            scale = getMapParameter("gisScaleLevel_4");
            break;
        case 5:
            scale = getMapParameter("gisScaleLevel_5");
            break;
        case 6:
            scale = getMapParameter("gisScaleLevel_6");
            break;
        case 7:
            scale = getMapParameter("gisScaleLevel_7");
            break;
        case 8:
            scale = getMapParameter("gisScaleLevel_8");
            break;
        case 9:
            scale = getMapParameter("gisScaleLevel_9");
            break;
        case 10:
            scale = getMapParameter("gisScaleLevel_10");
            break;
        case 11:
            scale = getMapParameter("gisScaleLevel_11");
            break;
        case 12:
            scale = getMapParameter("gisScaleLevel_12");
            break;
        default:
            scale = -1;
            break;
    }
    return scale;
}
/**
 * 
 * @param {Object} id
 * @param {Object} left
 * @param {Object} top
 * @param {Object} width
 * @param {Object} height
 * @param {Object} img
 * @param {Object} position
 * @param {Object} border
 * @param {Object} opacity
 */
Util.createDiv = function(id, left, top, width, height, img, position, border, opacity){
    if (document.getElementById(id)) {
        return document.getElementById(id);
    }
    var e = document.createElement('div');
    if (id) 
        e.id = id;
    
    if (left) 
        e.style.left = parseInt(left) + "px";
    if (top) 
        e.style.top = parseInt(top) + "px";
    
    if (width && height) {
        e.style.width = parseInt(width) + "px";
        e.style.height = parseInt(height) + "px";
    }
    if (img) 
        e.appendChild(Util.createImg(id + '_Img', 5, 5, null, null, img, 'relative'));
    
    if (position) 
        e.style.position = position;
    if (border) 
        e.style.border = border;
    
    if (opacity) {
        e.style.opacity = opacity;
        e.style.filter = 'alpha(opacity=' + (opacity * 100) + ')';
    }
    
    return e;
};
/**
 * 
 * @param {Object} id
 * @param {Object} left
 * @param {Object} top
 * @param {Object} width
 * @param {Object} height
 * @param {Object} imgurl
 * @param {Object} position
 * @param {Object} border
 * @param {Object} opacity
 * @param {Object} delayDisplay
 */
Util.createImg = function(id, left, top, width, height, imgurl, position, border, opacity, delayDisplay){

    image = document.createElement("img");
    
    if (delayDisplay) {
        image.style.display = "none";
        Event.observe(image, "load", Util.onImageLoad.bindAsEventListener(image));
        Event.observe(image, "error", Util.onImageLoadError.bindAsEventListener(image));
    }
    
    image.style.alt = id;
    image.galleryImg = "no";
    if (imgurl) 
        image.src = imgurl;
    
    if (!position) 
        position = "relative";
    
    if (id) 
        image.id = id;
    
    if (left) 
        image.style.left = parseInt(left) + "px";
    if (top) 
        image.style.top = parseInt(top) + "px";
    
    if (width && height) {
        image.style.width = parseInt(width) + "px";
        image.style.height = parseInt(height) + "px";
    }
    
    if (position) 
        image.style.position = position;
    
    if (border) 
        image.style.border = border;
    
    if (opacity) {
        image.style.opacity = opacity;
        image.style.filter = 'alpha(opacity=' + (opacity * 100) + ')';
    }
    //image.onClick = test;
    return image;
}
/**
 * 
 * @param {Object} element
 * @param {Object} id
 * @param {Object} left
 * @param {Object} top
 * @param {Object} width
 * @param {Object} height
 * @param {Object} position
 * @param {Object} border
 * @param {Object} overflow
 * @param {Object} opacity
 */
Util.setElementStyle = function(element, id, left, top, width, height, position, border, overflow, opacity){

    if (id) {
        element.id = id;
    }
    
    if (left) 
        element.style.left = parseInt(left) + "px";
    if (top) 
        element.style.top = parseInt(top) + "px";
    
    if (width && height) {
        element.style.width = parseInt(width) + "px";
        element.style.height = parseInt(height) + "px";
    }
    if (position) {
        element.style.position = position;
    }
    if (border) {
        element.style.border = border;
    }
    if (overflow) {
        element.style.overflow = overflow;
    }
    if (opacity) {
        element.style.opacity = opacity;
        element.style.filter = 'alpha(opacity=' + (opacity * 100) + ')';
    }
};
/**
 * 
 */
Util.onImageLoad = function(){
    this.style.backgroundColor = null;
    this.style.display = "";
};
/**
 * 
 */
Util.onImageLoadError = function(){
    this.style.backgroundColor = "pink";
    this.style.display = "";
};
/**
 * 
 * @param {Object} e
 */
Util.getMousePixel = function(e){
    if (!e) 
        e = window.event;
    if (!e.pageX) 
        e.pageX = e.clientX;
    if (!e.pageY) 
        e.pageY = e.clientY;
    return {
        x: e.pageX,
        y: e.pageY
    };
};
/**
 * 
 * @param {Object} e
 * @param {Object} mapDiv
 */
Util.getMouseRelativePixel = function(e, mapDiv){
    var pixel = Util.getMousePixel(e);
    var relDeltaX = pixel.x - Util.getLeft(mapDiv.parentNode) - Util.getValueOfNoPX(mapDiv.style.left);
    var relDeltaY = pixel.y - Util.getTop(mapDiv.parentNode) - Util.getValueOfNoPX(mapDiv.style.top);
    return {
        x: relDeltaX,
        y: relDeltaY
    }
};
/**
 * 
 * @param {Object} obj
 */
Util.getTop = function(obj){
    var t = obj.offsetTop;
    while (obj = obj.offsetParent) {
        t += obj.offsetTop;
    }
    return t;
};
/**
 * 
 * @param {Object} obj
 */
Util.getLeft = function(obj){
    var t = obj.offsetLeft;
    while (obj = obj.offsetParent) {
        t += obj.offsetLeft;
    }
    return t;
};
/**
 * 
 * @param {Object} coord
 * @param {Object} zoom
 */
Util.getScreenPixel = function(coord, zoom){
    var sx = (coord.x - zoom.realMapBound.getMinX()) * ((zoom.getTileCols() * TileSize) / zoom.realMapBound.getWidth());
    var sy = (zoom.realMapBound.getMaxY() - coord.y) * ((zoom.getTileRows() * TileSize) / zoom.realMapBound.getHeight());
    return {
        x: sx,
        y: sy
    }
}

/**
 * 
 * @param {Object} pixel
 * @param {Object} zoom
 */
Util.getCoordinateByPixel = function(pixel, zoom){
    var x = zoom.realMapBound.getMinX() + pixel.x * (zoom.realMapBound.getWidth() / (zoom.getTileCols() * TileSize));
    var y = zoom.realMapBound.getMaxY() - pixel.y * (zoom.realMapBound.getHeight() / (zoom.getTileRows() * TileSize));
    return new Coordinate(x, y);
};
/**
 * 
 * @param {Object} mapDiv
 * @param {Object} infoCoord
 * @param {Object} zoom
 */
Util.CreateOnlyFrontPointSymbol = function(mapDiv, infoCoord, zoom){
	//TODO:由经纬度转屏幕坐标.
	var scoord = Util.getScreenPixel(infoCoord, zoom);
	var pointSymbol = document.createElement("div");
	pointSymbol.id = "pointSymbolDiv" + Util.createUniqueID();
	pointSymbol.onselect = null;
	pointSymbol.style.position = "absolute";
	pointSymbol.style.background = "#FFFFFF";
	pointSymbol.style.border = "1px solid #999999";
	pointSymbol.style.fontSize = "12px";
	pointSymbol.style.padding = "1px";
	pointSymbol.style.zIndex = 11;
	pointSymbol.style.left = (scoord.x - 10) + "px";
	pointSymbol.style.top = (scoord.y - 20) + "px";	
	var image =	Util.createImg("ps", 0, 0, 12, 20, "images/marker_small.png");

	Event.observe(image, "click", test.bindAsEventListener(image));
	pointSymbol.appendChild(image);
	pointSymbol.style.display = "";
	
	mapDiv.appendChild(pointSymbol);
};
/**
 * 判断是否是IE浏览器
 */
Util.isIE = function(){
	if(document.all){
		return true;
	}
	return false;
};

Util.trim = function(str)
{
	return str.replace(/\s*/g, "");
};

Util.isNormalText = function(str)
{
	// 特殊字符验证格式
	var regTextChar = /([\#\&\?\*\<>])+/;
	return !regTextChar.test(str);
};

/**
*@函数说明：判断一个字符串是否为纯英文字母串
*@函数参数：str：字符串
*@返回：true：该字符串完全由英文字母构成；false：该字符串包含非英文字母
*/
Util.isLetter = function(str)
{
	var letterform = /^[A-Za-z]+$/;
	return letterform.test(str);
};

/**
*@函数说明：判断一个字符串是否为纯中文串
*@函数参数：str：字符串
*@返回：true：该字符串完全由中文构成；false：该字符串包含非中文
*/
Util.isChinese = function(str)
{
	var chineseform = /[^\u4e00-\u9fa5]/g;
	return !(chineseform.test(str));
}

Util.showSearchResult = function(locname,id,type,layer,keyname)
{
	//alert(id + "," + type + "," + layer);
	var url = "webgis.MapSearch.gISShow.d";
	var params = "?layername=" + layer;
	params += "&keyid=" + id;
	params += "&layertype=" + type;
	params += "&keyname=" + keyname;
	url = encodeURI(url);
	params = encodeURI(params);
	new Ajax.Request(url,
	{
		method: 'post', 
		parameters: params, 
		onComplete: function(xmlHttp)
		{
			var result = xmlHttp.responseText;
			result = result.split(";");
			var zlvl = result[0];
			var ctX = parseFloat(result[1].split(",")[0]) * 1e16;
			var ctY = parseFloat(result[1].split(",")[1]) * 1e16;
			var ctPt = new Point(ctX,ctY);
			var llx = parseFloat(result[1].split(",")[0]);
			var lly = parseFloat(result[1].split(",")[1]);
			var coord = new Coordinate(ctX,ctY);
			map.moveToCenter(ctPt,zlvl);
			window.focus();
			var spoint = Util.getScreenPixel(coord,map.getMapModel().getZoom());
			MapTips.prototype.showPopup(map.getVMLDiv(),locname,"",spoint.x,spoint.y);
		}
	});
};

Util.showGSearchResult = function(locname,longi,lati)
{	
	var ctX = parseFloat(longi) * 1e16;
	var ctY = parseFloat(lati) * 1e16;
	var ctPt = new Point(ctX,ctY);
	var coord = new Coordinate(ctX,ctY);
	map.moveToCenter(ctPt,MaxZoomLevel);
	window.focus();
	var spoint = Util.getScreenPixel(coord,map.getMapModel().getZoom());
	//alert(coord.x + "," + coord.y);
	MapTips.prototype.showPopup(map.getVMLDiv(),locname,"",spoint.x,spoint.y);
};

var state = 1;
var orgLeft = 0;
var orgTop = 0;
var deltaX = 0;
var deltaY = 0;
var timer = null;

function setCurPos(left, top){
}


function slide(layerId, w, h, img){
    
	var containerW = Util.getValueOfNoPX($(layerId).parentNode.style.width);
    var containerH = Util.getValueOfNoPX($(layerId).parentNode.style.height);
	
    if (state == 1) {
        state = 0;
        fly(layerId, containerW - w, containerH);
        img.src = ImageBaseDir + '2.GIF'
    }
    else {
        state = 1;
        fly(layerId, containerW - w, containerH - h);
        img.src = ImageBaseDir + '1.GIF';
    }
}
function fly(layerId, left, top, speed, speedRate){
	$(layerId).style.left = left;
	$(layerId).style.top = top;
}

function move(layerId, wSpeed, hSpeed, left, top){
    clearTimeout(timer);
    if (orgLeft != left) {
        if ((Math.max(orgLeft, left) - Math.min(orgLeft, left)) < wSpeed) 
            orgLeft = left;
        else 
            if (orgLeft < left) 
                orgLeft = orgLeft + wSpeed;
            else 
                if (orgLeft > left) 
                    orgLeft = orgLeft - wSpeed;
        $(layerId).style.left = orgLeft;
    }
    if (orgTop != top) {
        if ((Math.max(orgTop, top) - Math.min(orgTop, top)) < hSpeed) 
            orgTop = top
        else 
            if (orgTop < top) 
                orgTop = orgTop + hSpeed
            else 
                if (orgTop > top) 
                    orgTop = orgTop - hSpeed
        $(layerId).style.top = orgTop
    }
    
    timer = setTimeout('move("' + layerId + '",' + wSpeed + ',' + hSpeed + ',' + left + ',' + top + ')', 30);
};
/**
 * 
 */
function MM_swapImgRestore(){
    var i, x, a = document.MM_sr;
    for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) {
        x.src = x.oSrc;
    }
}
/**
 * 
 */
function MM_swapImage(){
    var i, j = 0, x, a = MM_swapImage.arguments;
    document.MM_sr = new Array;
    for (i = 0; i < (a.length - 2); i += 3) {
        if ((x = $(a[i])) != null) {
            document.MM_sr[j++] = x;
            if (!x.oSrc) 
                x.oSrc = x.src;
            x.src = a[i + 2];
        }
    }
}

/**============================================================**/
/**
 * 加入地图监听.
 */
var onTileChange = new Array();
/**
 * 
 * @param {Object} eventName
 * @param {Object} funHandler
 */
function addTileChangeListener(eventName, funHandler){
	var arrEvent = new Array(2);
	arrEvent[0] = eventName;
	arrEvent[1] = funHandler;
	
	onTileChange[onTileChange.length] = arrEvent;
}    
/**
 * 
 */
function fireTileChangeListener(){
 	for (var i=0; i<onTileChange.length; i++){
		eval(onTileChange[i][1]);
	}	
}
/**
 * 
 * @param {Object} eventName
 */
function removeTileChangeListener(eventName){
	var arrRow = new Array(2);
	var arrRowCol = new Array();
	
 	for (var i=0; i<onTileChange.length; i++){
 		if (onTileChange[i][0] != eventName){
			arrRow[0] = onTileChange[i][0];
			arrRow[1] = onTileChange[i][1];
			arrRowCol[i] = arrRow;
		}
	}
	
	onTileChange = arrRowCol;
}



/**=============================================================**/