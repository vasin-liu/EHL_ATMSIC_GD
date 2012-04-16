/**
 * 警情地图操作类
 */
var amid="";
var tag = null;

/**
 * 说明：显示地图
 * 参数：【tag div标签】，【type 操作类型。0-查看，1-标注 】
 */
function showMap(tag,type){
	tag = tag;
	$("div"+tag).style.display = "none";  
	$('mapTd').style.display = ""; 
	if(type=="0"){
		showPoint(tag);
	}
	$("backInfobtn").onclick = function(){$('mapTd').style.display = "none";$("div"+tag).style.display = "";};
}

//定位地图
function markMap()
{         
	//地图标注
	var mapDiv =toolbar.mapDiv;
	Event.observe(mapDiv, "click", markAccident.bindAsEventListener());
}

//定位地图
function markAccident(ev)
{
		var alarmid=$("alarmId").value;
		var mapDiv = toolbar.mapDiv;
		var infoCoord =Util.getMouseRelativePixel(ev, mapDiv);
		var zoom = toolbar.model.getZoom();
		var x=Util.getCoordinateByPixel(infoCoord, zoom).x/1e16;
		var y=Util.getCoordinateByPixel(infoCoord, zoom).y/1e16;
		
		var offsetX = ev.offsetX;
		var offsetY = ev.offsetY;
		var srcTarget = ev.srcElement || ev.srcTarget;
		var url="dispatch.policeEdit.getNearRoadByMouse.d";
		var params = encodeURI("x="+x+"&&y="+y);
		
		new Ajax.Request(url,
			 {	method: 'post', 
            	parameters: params,
            	onComplete:function(xmlHttp)
            	{
            		
					 var xmlDoc = xmlHttp.responseXML;
   					
					 var results = xmlDoc.getElementsByTagName("row")
					 var size = results.length;
					 
					 if( 0 == size)
					 {			
//					 	alert("未获取地图信息,请您重新定位。");						 										 	
//					 	return ;
						if(temp=="comple"){	
							setRoadInfo("","");
							$("compleX").value=x;
							$("compleY").value=y;
						}else{
						 	$("divEditAlarm").style.display="";
							$("mapTd").style.display="none"	;
							$("x").value=x;
							$("y").value=y;		
						}
						writeMap(infoCoord,zoom,alarmid);
					 }
					 if( 1 == size )
					 {					 	
					 	var cols = results[0].getElementsByTagName("col");
						setRoadInfo(cols[0].text,cols[1].text);
						
						$("x").value=x;
						$("y").value=y;
						$("compleX").value=x;
						$("compleY").value=y;
						writeMap(infoCoord,zoom,alarmid);
					 	 
					 }
					 if( 1 < size )
					 {
					 	//多项结果，提供用户选择
						var chose = new ListChose(offsetX,offsetY,srcTarget,xmlDoc);												
						chose.setCellClickHandler(getRoadInfo);
						$("x").value=x;
						$("y").value=y;
						$("compleX").value=x;
						$("compleY").value=y;
						writeMap(infoCoord,zoom,alarmid);
					 }					
				}
			});
		
		Event.stop(ev);		
}

function getRoadInfo(cell){	
	
	setRoadInfo(cell.itemId ,cell.firstChild.nodeValue);
	ListChose.close();	
}

//id:路段编号，name：路段名称 
function setRoadInfo(id,name){			
		if(temp=="comple"){			
			$("compleroadId").value=id;
			$("compleroadName").value=name;
			$("divComplement").style.display="";
			$("mapTd").style.display="none";
		}else{
			$("roadId").value=id;
			$("roadName").value=name;
			$("divEditAlarm").style.display="";
			$("mapTd").style.display="none";
		}
}

function writeMap(infoCoord,zoom,alarmid){
	
//	var mapDiv = $("map_" + toolbar.model.getId());
	var mapDiv = map.getVMLDiv();
	var pointSymbol = document.createElement("div");
	var divID =Util.createUniqueID("accidentDiv_"+alarmid);
	var pointid ="accidentDiv_"+alarmid;
	
	var realCoord = Util.getCoordinateByPixel(infoCoord, zoom);	
	
	var delDiv = document.getElementById(pointid);
	if(delDiv!=null){
		delDiv.parentNode.removeChild(delDiv);
	}
	//创建图形符号
	amid=pointid;
	pointSymbol.id = pointid;
	pointSymbol.onselect = null;
	pointSymbol.style.position = "absolute";
	pointSymbol.style.border = "0px solid #999999";
	pointSymbol.style.fontSize = "12px";
	pointSymbol.style.padding = "1px";
	pointSymbol.style.zIndex = 11;
	pointSymbol.style.left = (infoCoord.x) + "px";
	pointSymbol.style.top = (infoCoord.y) + "px";	
	var img = Util.createImg("ps", 0, 0, 11, 11, "../../images/alarm/alarm.gif");
	pointSymbol.appendChild(img);
	pointSymbol.style.display = "";
	img.tag = pointid;
	img.style.cursor ="hand";
	img.position = realCoord;
	mapDiv.appendChild(pointSymbol);
}

var reX="";
var reY="";
var reAlarmId="";
//定位地图
function reMarkPoint(ev,alarmId)
{		
		var mpDv = toolbar.mapDiv;
		var infoCoord =Util.getMouseRelativePixel(ev, mapDiv);
		var zoom = toolbar.model.getZoom();
		var x=Util.getCoordinateByPixel(infoCoord, zoom).x/1e16;
		var y=Util.getCoordinateByPixel(infoCoord, zoom).y/1e16;
		
		var offsetX = ev.offsetX;
		var offsetY = ev.offsetY;
		var srcTarget = ev.srcElement || ev.srcTarget;
		var url="dispatch.policeEdit.getNearRoadByMouse.d";
		var params = encodeURI("x="+x+"&&y="+y);
		
		var roadId="";
		var roadName="";
		
		new Ajax.Request(url,
			 {	method: 'post', 
            	parameters: params,
            	onComplete:function(xmlHttp)
            	{
					 var xmlDoc = xmlHttp.responseXML;
   					
					 var results = xmlDoc.getElementsByTagName("row")
					 var size = results.length;
				
					 if( 0 == size )
					 {	
					 	reX=x;
					 	reY=y;
						writeMap(infoCoord,zoom,alarmId);			
						rePointInfo(alarmId,roadId,roadName);								
					 }
				
					 if( 1 == size )
					 {					 	
					 	var cols = results[0].getElementsByTagName("col");
					 	roadId=cols[0].text;
					 	roadName=cols[1].text;
					 	reX=x;
					 	reY=y;
						writeMap(infoCoord,zoom,alarmId);			
						rePointInfo(alarmId,roadId,roadName);								
					 }
					 if( 1 < size )
					 {
					 	//多项结果，提供用户选择
						var chose = new ListChose(offsetX,offsetY,srcTarget,xmlDoc);	
						reX=x;
					 	 reY=y;
					 	 reAlarmId=alarmId;											
						chose.setCellClickHandler(reGetRoadInfo);
						 
						 writeMap(infoCoord,zoom,alarmId);			
						 //rePointInfo(alarmId,roadId,roadName);
					 }
				}
			});
		
		Event.stop(ev);		
}

function reGetRoadInfo(cell){
	rePointInfo(reAlarmId,cell.itemId ,cell.firstChild.nodeValue);
	ListChose.close();	
}


function rePointInfo(alarmId,roadId,roadName){	
	var params={};
	params["roadId"]=roadId;
	params["roadName"]=roadName;
	params["X"]=reX;
	params["Y"]=reY;	
	params["alarmId"]=alarmId;			
	var url = "dispatch.policeEdit.reMarkPoint.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:rePointInfoRes});
}
function rePointInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;		
	if(xmlDoc=="success"){
		alert("标注成功！")
		reX="";
		reY="";
		reAlarmId="";
	}else{
		
	}
}

function markTool(){
	AlarmTool.prototype.clickHandler=function(e, toolbar){
		markAccident(e);
	}	
}

//地图居中显示警情
function centerAlarm(x,y,aid,tag){
	
    	var mapDiv = map.getVMLDiv();
    	var model = map.getMapModel();
    	var currentZoom =  map.getCurrentZoom();
    	var zoom = toolbar.model.getZoom();
		var p=x+","+y;
		if(tag!="1"){
		//闪烁显示
			MapUtils.prototype.flashPoint(mapDiv, p.split(","),currentZoom,0);	
		}				
				
		var infoCoord = new Coordinate(x*1e16 , y*1e16);
		
		var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.		
		
//		map.moveToCenter(p,Number(getMapParameter(5)));
		
		var left = scoord.x;
		var top =  scoord.y;		
		var pointid ="accidentDiv_"+aid;
		
		amid=pointid;
		var pointSymbol = document.createElement("div");
		
		var realCoord = Util.getCoordinateByPixel(scoord, zoom);	
		var delDiv = document.getElementById(pointid);
		if(delDiv!=null){
			delDiv.parentNode.removeChild(delDiv);
		}
		//创建图形符号
		pointSymbol.id = pointid;
		pointSymbol.onselect = null;
		pointSymbol.style.position = "absolute";
		pointSymbol.style.border = "0px solid #999999";
		pointSymbol.style.fontSize = "12px";
		pointSymbol.style.padding = "1px";
		pointSymbol.style.zIndex = 11;
		
		pointSymbol.style.left = left + "px";
		pointSymbol.style.top = top + "px";	
		var img = Util.createImg("ps", 0, 0, 11, 11, "../../images/alarm/alarm.gif");
		pointSymbol.appendChild(img);
		pointSymbol.style.display = "";
		img.tag = pointid;
		img.style.cursor ="hand";
		img.position = realCoord;
		mapDiv.appendChild(pointSymbol);
		
}

//
function showPoint(){	
	var x="";
	var y="";
	if($("alarmId"+"_"+tag) && $("alarmId"+"_"+tag).value!=null && $("alarmId"+"_"+tag).value!=""){
		aid=$("alarmId").value;
		x=$("X"+"_"+type).value;	
		y=$("Y"+"_"+type).value;
		centerAlarm(x,y,aid,"1");
	}else{
		MapUtils.prototype.moveToAreaCenter(deptcode);
	}
}

function removeAllPoint(){
	var delDiv = document.getElementById(amid);
	if(delDiv!=null){
		delDiv.parentNode.removeChild(delDiv);
	}
}