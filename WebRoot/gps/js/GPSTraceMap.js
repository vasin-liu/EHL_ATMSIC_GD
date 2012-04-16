/*
 * GPS显示类
 */
GPSTraceMap = Class.create();
GPSTraceMap.prototype = { 

	// 定义存储GPS车辆坐标数组
	pointList : null,
	carCode:null,
	symbolPrefix:"lineSection_",
	
	sectArray:null,
	lineSymbols:null,  //保存轨迹线段对象
	
	drawInterval:1000,
	
	/**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container){
        this.container = container;       
    },
    /**
	 * 读取图层数据
	 */
	readPoints: function(carcode)
	{
		gpstracemap.showWaitingMsg();
		var url = 'gps.GPSMap.ReadTracePoints.d';
		GPSTraceMap.prototype.carcode = carcode;
		var params = "carcode=" + carcode;
		
		if(document.getElementById('txtStartQ'))
		{
			//轨迹时间
			params += "&start=" + $F('txtStartQ');
			params += "&terminal=" + $F('txtTreminalQ');
		}	
		params = encodeURI(params);
		new Ajax.Request(url, {
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: GPSTraceMap.prototype.finishReadPoints
		});
	},
	/**
     * 完成服务端读取后，将获取到的数据存入到内存数组中
     */
	finishReadPoints: function(xmlHttp)
	{
		var xmlDoc = xmlHttp.responseXML;
		var results = xmlDoc.getElementsByTagName("row");
		gpstracemap.pointList = new Array();
		//ID,CARCODE,CITYID,CARTELNUMBER,VELOCITY,
		var zoom =  map.getCurrentZoom();
		for (var i = 0; i < results.length; i++)
		{
			var rowResult = results[i].childNodes;
			var record = new Object();
			
			var id = "";					
			var temp = rowResult[0].firstChild;
			if (temp != null)
			{
				id = temp.nodeValue;
			}
			
			var phoneCode = "";					
			var temp = rowResult[3].firstChild;
			if (temp != null)
			{
				phoneCode = temp.nodeValue;
			}
			
			var speed = "";
			var temp = rowResult[4].firstChild;
			if (temp != null)
			{
				speed = temp.nodeValue;
			}
			var gpstime = "";
			var temp = rowResult[7].firstChild;
			if (temp != null)
			{
				gpstime = temp.nodeValue;
			}
			
			//经纬度坐标
			var coord = "";
			var coordLongObj = rowResult[5].firstChild;
			var coordLatObj = rowResult[6].firstChild;
			//取得坐标点
			if (coordLongObj != null && coordLatObj != null)
			{
				var coordPoint = coordLongObj.nodeValue + "," + coordLatObj.nodeValue;
				record.id = id;
				record.phoneCode = phoneCode;
				record.speed = speed;
				record.coord = coordPoint;
				record.gpstime = gpstime
				gpstracemap.pointList[i] = record;
			}
		}	
		MapTips.prototype.hidePopup(true);
		gpstracemap.showCarTracePoint();	//显示轨迹点
		//GPSTraceMap.prototype.centerCarInfo();	//以轨迹的第一个点居中
	},
	/**
 	*	显示等待提示
 	*/
 	showWaitingMsg : function(strMsg)
 	{
 		var mapmodel = map.getMapModel();
 		var centerCood = null;
 		if(mapmodel)
 		{
 			centerCood = mapmodel.getViewCenterCoord();
 		}
 		if(centerCood)
 		{
 			var currentZoom =  map.getCurrentZoom();
 			var infoCoord = new Coordinate( centerCood.x , centerCood.y);	
			var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.	
			 
 			var mapDiv = map.getVMLDiv();
 			MapTips.prototype.showLoadTips(mapDiv, scoord.x , scoord.y);
 		}
 	},
	showCarTracePoint:function()
	{
		//清除历史轨迹线
		if(this.lineSymbols && this.lineSymbols.length >0 )
		{
			for(var i=0; i<this.lineSymbols.length; i++)
			{
				this.lineSymbols[i].parentNode.removeChild(this.lineSymbols[i]);
			}
		}
				
		//判断是否为空或者对象未被实例化
    	if (typeof gpstracemap.pointList == "undefined" || gpstracemap.pointList == null)
    	{
    		
    		return;
    	}
    	
    	if(gpstracemap.sectIndex && 0 != gpstracemap.sectIndex)
    	{
    		gpstracemap.reDrawLinesect();
    		return;
    	}
    	
		
		
		//获取地图图层和当前显示范围
    	var mapDiv = map.getVMLDiv();
    	var zoom =  map.getCurrentZoom();
    	
    	//var pointCount = gpstracemap.pointList.length;				//记录点的数目
    	
    	gpstracemap.sectArray = new Array();
    	gpstracemap.traceBusiness();
    	gpstracemap.sectIndex = 0;
    	gpstracemap.showLinesects();
	}, 
	
	traceBusiness : function()
	{
		sectArray = null;
		//$('tttt4').innerHTML
		var zoom =  map.getCurrentZoom();
		var symbolId = "";
		var pointCount = gpstracemap.pointList.length;	//记录点的数目
		
		var sectionEnd = null;	//段结束点，下一段的起点
		var sectionID = 0;		//段编号
		
		var preGPSTime = null;
		var count = 0;
		
		var sectPoints = "";
		for(var i=0; i<pointCount; i+=count )
		{
			count = 0;
			preGPSTime = null;
			sectPoints = null;
			var curGPSTime = Date.parse( gpstracemap.pointList[i].gpstime );
			
		 	while( null == preGPSTime  || 60 > ( curGPSTime - preGPSTime )/1000  )
			{
				//时间间隔小于1分钟的轨迹点
				var index = i + count;
				if( index >= pointCount )
				{
					break;
				}
				if(0 == count)
				{
					sectPoints = new Array();
				}
				sectPoints.push( gpstracemap.pointList[index].coord );
								
				if( parseInt(traceInterval) < count )
				{
					sectionID = gpstracemap.pointList[ i + Math.floor(count/2) ].id;
					symbolId = gpstracemap.symbolPrefix + sectionID;
					gpstracemap.lineSectionObject(sectPoints,symbolId,sectionID);
					
					sectPoints = null;
					
					i += parseInt(traceInterval);
					count = 0;
					preGPSTime = null;
				}
				else
				{
					count += 1;
					preGPSTime = curGPSTime;
					
					if( index+1 < pointCount )
					{
						curGPSTime = Date.parse( gpstracemap.pointList[index + 1 ].gpstime );
					}
				}
			}
			if(count > 1 )
			{
				sectionID = gpstracemap.pointList[ i + Math.floor( count/2) ].id;
				symbolId = gpstracemap.symbolPrefix + sectionID;
				gpstracemap.lineSectionObject(sectPoints,symbolId,sectionID);
			}
			 
		}
		 
	},
	
	lineSectionObject: function(pointArray,symbolId,sectionID)
	{
		var oSect = {};
		oSect['pointArray'] = pointArray;
		oSect['symbolId'] = symbolId;
		oSect['sectionID'] = sectionID;
		gpstracemap.sectArray.push(oSect);
	},
	reDrawLinesect: function()
	{
		var sectLength = gpstracemap.sectIndex;
		if( gpstracemap.sectArray && 0<gpstracemap.sectArray.length)
		{
			if(sectLength == 0 && gpstracemap.drawFinished )
			{
				 sectLength = gpstracemap.sectArray.length;
			}
			 
			
			for(var j=0;j<sectLength;j+=1)
			{
				if(!gpstracemap.sectArray[j])
				{
					continue;
				}
				var pointArray = gpstracemap.sectArray[j].pointArray;
				var symbolId = gpstracemap.sectArray[j].symbolId;
				var sectionID = gpstracemap.sectArray[j].sectionID;
				var zoom =  map.getCurrentZoom();
				var strPoints = "";
				var startPoint = null;
			 	for(var i=0;i< pointArray.length;i+=1)
				{
					var longtitude = pointArray[i].split(",")[0];
					var latitude   = pointArray[i].split(",")[1];
				 
					var infoCoord = new Coordinate( longtitude * 1e16 , latitude*1e16);	
					var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.	
					scoord = scoord.x + "," + scoord.y;
					if("" == strPoints )
					{	
						strPoints += scoord;
						startPoint =  pointArray[i];
						 
					}
					else
					{
						strPoints += " " + scoord;
					}
				}
				var sectSymbol = document.getElementById(symbolId);
				if(sectSymbol)
				{
					sectSymbol.parentNode.removeChild(sectSymbol);
				}
				gpstracemap.buildLineSection(strPoints,symbolId,sectionID);
  			}
		}
	},
	
	showLinesects: function()
	{
		if( gpstracemap.sectArray && 0<gpstracemap.sectArray.length)
		{
			if(gpstracemap.sectIndex >=gpstracemap.sectArray.length)
			{
				gpstracemap.sectIndex = 0;
				gpstracemap.drawFinished = true;
				return;
			}
			var pointArray = gpstracemap.sectArray[ gpstracemap.sectIndex ].pointArray;
			var symbolId = gpstracemap.sectArray[ gpstracemap.sectIndex ].symbolId;
			var sectionID = gpstracemap.sectArray[ gpstracemap.sectIndex ].sectionID;
			var zoom =  map.getCurrentZoom();
			var strPoints = "";
			var startPoint = null;
		 	for(var i=0;i< pointArray.length;i+=1)
			{
				var longtitude = pointArray[i].split(",")[0];
				var latitude   = pointArray[i].split(",")[1];
			 
				var infoCoord = new Coordinate( longtitude * 1e16 , latitude*1e16);	
				var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.	
				scoord = scoord.x + "," + scoord.y;
				if("" == strPoints )
				{	
					strPoints += scoord;
					startPoint =  pointArray[i];
					 
				}
				else
				{
					strPoints += " " + scoord;
				}
			}
			gpstracemap.buildLineSection(strPoints,symbolId,sectionID);
//			if(startPoint && 5 == gpstracemap.sectIndex%6)
//			{
//				gpstracemap.centerCarInfo(startPoint);
//			}
			
			gpstracemap.sectIndex += 1;
			
			window.setTimeout("gpstracemap.showLinesects()",gpstracemap.drawInterval);
		}
	},
	/**
	 * 居中显示
	 * @id：道路编码
	 */
 	centerCarInfo : function(centerPoint)
 	{
 		if( centerPoint )
 		{
	    	//显示选中的禁行线路详细信息				
			var coord = centerPoint.split(",");
			var infoCoord = new Coordinate( coord[0]*1e16 ,coord[1]* 1e16);
			
			//设置居中位置
			map.moveToCenter(infoCoord,4);
		}
 	},
 	
	/**
	 ** 显示提示窗口窗体
	 */
	showMapTips: function(event)
	{	
	    var x = 0;
	    var y = 0;
	    var container;
	   
	   //获取当前对象的编程值：对象编号、经纬度坐标信息
	    var infoCoord ;
	    var sectionid = "";
	    
	    //取得鼠标的绝对坐标
	    container = map.getVMLDiv();
	    event = event ? event : (window.event ? window.event : null); 
	    if(event == null)
	    {	    	
	    	return;
	    }
			
		var source = event.srcElement || event.target;
	    x = Event.pointerX(event) - container.offsetLeft||container.scrollLeft;
		y = Event.pointerY(event) - container.offsetTop||container.scrollTop;
		
		sectionid = this.sectionid;
		var index =-1;
		for(var i=0 ;i < gpstracemap.pointList.length;i+=1)
		{
			if(sectionid == gpstracemap.pointList[i].id)
			{
				index = i;
				break;
			}
		} 
		if(-1 == index)
		{
			return ;
		}	   
		//显示正在查询提示		
		MapTips.prototype.showLoadTips(container,x,y);				
		
		var raRegExp = new RegExp("RFWin","g");
	 
		//查询到结果后显示在气泡中		
		var detailContent = "<table>"
							+ "<tr><td align=right>车牌号码:</td><td>" + regnumber + "</td></tr>" 
							+ "<tr><td align=right>执勤民警:</td><td>" + driver + "</td></tr>" 
							+ "<tr><td align=right>呼叫号码:</td><td>" + gpstracemap.pointList[i].phoneCode + "</td></tr>" 
							+ "<tr><td align=right>时间:</td><td>" + gpstracemap.pointList[i].gpstime.replace(raRegExp,"-") + "</td></tr>" 
							+ "<tr><td align=right>车速:</td><td>" + gpstracemap.pointList[i].speed + "</td></tr>" 
							+ "</table>";
     
	    //显示提示信息窗口			   
	    MapTips.prototype.showPopup(container,"车辆行驶信息",detailContent,x,y);
	},
	
	buildLineSection: function(points,symbolId,id)
 	{
 		var mapDiv = map.getVMLDiv();
   
 		var sLine = document.createElement("v:polyline");
   		sLine.id = symbolId;
   		sLine.strokecolor="blue";
   		sLine.strokeweight="2pt";
   		sLine.filled = false;
       
       	//设置样式
       	var vStroke1 = document.createElement("v:stroke");
	    vStroke1.dashstyle = "solid";
	    vStroke1.startarrow = "none";
	    vStroke1.endarrow = "Classic";
	   	sLine.sectionid = id;
		
		//添加点击处理事件，显示气泡窗体
		Event.observe(sLine, "click", this.showMapTips.bindAsEventListener(sLine));
		
		sLine.appendChild(vStroke1);    
      	sLine.points = points;      //加入地图后无法更改
      	mapDiv.appendChild(sLine);  
      	
      	if(! this.lineSymbols)
      	{
      		this.lineSymbols = new Array();
      	}
      	
      	this.lineSymbols.push(sLine);
      	
      	
      //	$('tttt4').innerHTML += id + "--->" + points + "<br/>";
 	},
	/**
	 * 构建事故点查询面板.
	 */
 	outputHTML: function()
	{
	  htmlTxt = '\
	    <div id="queryCond" class="queryDiv" style="offsetTop:0px">\
		  <table>\
			<tbody>\
				<tr>\
					<td align="right">&nbsp;&nbsp;开始时间：</td>\
					<td ><input type="text" id="txtStartQ" style="width:155px" onclick="SelectDateTime(this,0);" readOnly/></td>\
				</tr>\
				<tr>\
					<td align="right">&nbsp;&nbsp;结束时间：</td>\
					<td ><input type="text" id="txtTreminalQ" style="width:155px" onclick="SelectDateTime(this,0);" readOnly/></td>\
				</tr>\
				<tr>\
					<td valign="top" colspan="2" align="right"> \
						<input type="button" id="btnShowTrace" value="显示轨迹">\
					</td>\
				</tr>\
			</tbody>\
		  </table>\
		</div>\
		<hr />';
	  return htmlTxt;
	}
	
};