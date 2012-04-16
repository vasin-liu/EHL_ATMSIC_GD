
/***********************************************************************

版 权：北京易华录信息技术股份有限公司 2009

文件名称：alarmWatchMap.js
摘 要：	警情监控信息,地图点的显示，刷新。
		 

当前版本：1.0
作 者：LChQ  2009-4-22 

***************************************************************************/

var alarmWatchMap = new AlarmWatchMap(); 

addTileChangeListener("onMapScaleEvent", "alarmWatchMap.showAlarmPoint()");


//选中列表中警情事件处理
function rowClickCenterPoint(alarmId)
{
	var data = watching.m_watchingData['affairData'];
	var alarmType = null;
	var longtitude = null;
	var latitude = null;
	
	//获取经纬度和警情类别
	for(var i=0;i< data.length;i+=1) 
	{
		if( data[i].alarmId == alarmId )
		{ 
			alarmType = data[i].eventType_code;
			latitude = data[i].latitude;
			longtitude = data[i].longtitude;
		}
	}
	if( longtitude && 0<longtitude.length)
	{	
		//居中含有坐标的具体警情
		var mapDiv = map.getVMLDiv();
  		var model = map.getMapModel();
  		var currentZoom =  map.getCurrentZoom();
		var coord = new Array();
		coord.push(longtitude);
		coord.push(latitude);
		
		//闪烁显示点
		MapUtils.prototype.flashPoint(mapDiv,coord ,currentZoom,2);	
	}
	else
	{
		alert("该警情信息未定位!");
	}
}



function AlarmWatchMap()
{
	this.showLevel = 4;	//显示级别
	
	this.m_watchingList = null;
}

//显示警情点
AlarmWatchMap.prototype.showAlarmPoint = function()
{
	//判断是否处在需要显示的级别上.
	var currLevel = map.getCurrLevel();
	if (currLevel < this.showLevel)
	{
		return;
	}
			
	//判断是否为空或者对象未被实例化
   	if (! this.m_watchingList )
   	{
   		return;
   	}
   	
	//获取地图图层和当前显示范围
   	var mapDiv = map.getVMLDiv();
   	var currentZoom =  map.getCurrentZoom();
     
	for(var i = 0; i < this.m_watchingList.length; i+=1)
	{			
		var longtitude = this.m_watchingList[i].longtitude;
		var latitude   = this.m_watchingList[i].latitude;
		if("" == longtitude || ""==latitude)
		{
			continue;
		}
		var infoCoord = new Coordinate((longtitude *1e16 ), (latitude * 1e16));//将坐标转化为整形
		
		//在地图上当前的坐标绘制出该视频设备点
		this.showWatchingPoint(mapDiv, infoCoord, currentZoom, i,this.m_watchingList[i].alarmId,this.m_watchingList[i].eventType_code,this.m_watchingList[i].symbolId);
	}
};

//显示视频信息点到指定位置
AlarmWatchMap.prototype.showWatchingPoint = function(mapDiv, infoCoord, zoom, index,alarmId,alarmType ,symbolId)
{
	var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.		
  	var strSymbolId = this.symbolPrefix + alarmId;
  		
	//判断是否已经存在
	var pointSymbol = document.getElementById(strSymbolId);
	if(pointSymbol != null)
	{
		pointSymbol.style.left = (scoord.x) + "px";
		pointSymbol.style.top =  (scoord.y) + "px";	
	}
	else
	{	
		pointSymbol = document.createElement("div");
		pointSymbol.id = strSymbolId;
		pointSymbol.style.position = "absolute";
		pointSymbol.style.border = "0px solid #999999";
		pointSymbol.style.fontSize = "12px";
		pointSymbol.style.padding = "1px";
		pointSymbol.style.zIndex = 11;
		pointSymbol.style.left = (scoord.x) + "px";
		pointSymbol.style.top =  (scoord.y) + "px";	
		
		var image = null;
		image = Util.createImg("ps", 0, 0, 12, 13, '../../images/alarm/alarm.gif');
		 
		image.style.cursor ="pointer";
		image.position = infoCoord;
		image.alarmId = alarmId;
		image.alarmType = alarmType;
		//添加图片点击处理事件，显示气泡窗体
		Event.observe(image, "click", this.showMapTips.bindAsEventListener(image));
		
		pointSymbol.appendChild(image);
		pointSymbol.style.display = "";
		mapDiv.appendChild(pointSymbol);
		
		//设置标图ID
		alarmWatchMap.m_watchingList[index].symbolId = strSymbolId;
	}
};


//事件图标选择事件处理
AlarmWatchMap.prototype.showMapTips = function(event,alarmId,left,top,position)
{
	var x = 0;
	var y = 0;
	//获取当前对象的编程值：对象编号、经纬度坐标信息
	var infoCoord ;
	    
	//取得鼠标的绝对坐标
	var   container = map.getVMLDiv();
	if(event != null)
	{	    	
		//获取信息
		event = event ? event : (window.event ? window.event : null); 
		var source = event.srcElement || event.target;
   		x = Event.pointerX(event) - container.offsetLeft||container.scrollLeft;
		y = Event.pointerY(event) - container.offsetTop||container.scrollTop;
		alarmId = this.alarmId;
	 	alarmType = this.alarmType;
	 	alarmWatchMap.signClickHandler(event.offsetX,event.offsetY,this,alarmId,alarmType);
	}
};

//清除所有的标注点
AlarmWatchMap.prototype.clearAlarmPoints = function()
{
	if( this.m_watchingList)
	{
		//事件图层中事件的坐标数据
		for(var i=0; i < this.m_watchingList.length; i+=1)
		{			
			var strSymbolId = this.m_watchingList[i].symbolId;
	  		//判断是否已经存在
	  		var pointSymbol = document.getElementById(strSymbolId);
	  		if(pointSymbol != null)
	  		{
	 			pointSymbol.parentNode.removeChild(pointSymbol);
	 		}
		}
	}
};

//显示浮动div信息
AlarmWatchMap.prototype.signClickHandler = function(x,y,srcTarget,alarmId,alarmType)
{
	var dataItems = new Array();
	var  item01 = {};
	item01["itemId"] = alarmId;
	item01["itemType"] = alarmType;
	item01["itemName"] = "<span style='color:blue;cursor:hand'>" +  alarmId + "</span>";
	dataItems.push(item01);
	var tipObject = new DhtmlTipDataList(x,y,srcTarget,dataItems);
	tipObject.setCellClickHandler( openAlarmDetailWin );
};

//显示明细
function openAlarmDetailWin(cellTarget)
{
	window.open("../affairQuery/affairDetail.jsp?ALARMID=" + cellTarget.itemId  + "&ALARMTYPE=" + cellTarget.itemType, 'alarmInfo','width=800,height=600,resizable=1,scrollbars=1');
}

//居中警情点信息
//AlarmWatchMap.prototype.centerAlarmPoint = function(alarmId,alarmType,longtitude,latitude)
//{
// 
//	var getIndex = -1;
//	if(typeof(alarmWatchMap.m_watchingList)=="undefined" || alarmWatchMap.m_watchingList == null) 
//	{
//		return;
//	}
//	//查找当前选中的信息
//	for(var i = 0; i < alarmWatchMap.m_watchingList.length; i++)
//	{				
//	    if(alarmWatchMap.m_watchingList[i].alarmId == alarmIdId)
//	    {
//	    	getIndex = i;
//	    	break;
//	    }	
//	}
//	if( -1 == getIndex )
//	{
//		alart("该警情未在地图上定位!");
//		return;
//	}
//	this.showMapTips(null,alarmId);
//	
//};




function DhtmlTipDataList(clientX,clientY,srcObject,p_dataItems)
{
  
    //div 容器
    var popupDiv = null;
   
    var nameTable = null; 
    var nameTableBody = null;
    
    //超过该数目时设定固定的高度

    var scrollSize = 6;

	var holderHeight = "160px";
	
	var holderWidth = "150px";
    /**清除该div
     *
     */
	var clearNames = function() 
	{
		
		var ind = nameTableBody.childNodes.length;
		for (var i = ind - 1; i >= 0 ; i--) 
		{
		     nameTableBody.removeChild(nameTableBody.childNodes[i]);
		}
		
		//popupDiv.style.border = "none";
		
		popupDiv.parentNode.removeChild(popupDiv);
	};
	
	/**初始化设定各个元素

	*
	*/
	var  initVars = function() 
	{
		popupDiv = document.getElementById('popup_div');
		nameTableBody = document.getElementById('name_table_body');
		if(popupDiv && nameTableBody)
		{
			clearNames();
		}
		
		//div 容器定义
		popupDiv = document.createElement("div");
		popupDiv.id = "popup_div";
		popupDiv.style.cssText = "position:absolute;overflow-y:hidden;z-index:5000";
		
		popupDiv.onmouseout = function() 
	    {
	    	if( event.srcElement.tagName.toLocaleLowerCase() == "div" )
	    	{
	    		//alert("div");
	    		this.parentNode.removeChild(this);
	    		//popupDiv.parentNode.removeChild(popupDiv);
	    	}
		};
		
		//表框架定义
		nameTable = document.createElement("table");
		nameTable.id = "name_table";
		nameTable.style.cssText = "background-color:#FFFAFA;border:0;cellspacing:0;cellpadding:0";
		
		nameTableBody = document.createElement("tbody");
		nameTableBody.id = "name_table_body";
	 	nameTable.appendChild(nameTableBody);
	
		popupDiv.appendChild(nameTable);
		document.body.appendChild(popupDiv);
	};
     
    var poulateName = function(target)
    {
    	ListChose.close();
    };
    
    var cellClickHandler = poulateName;
    
    /**设置名称选择事件处理
    **
    *
    */
    this.setCellClickHandler = function( handler)
    {
    	cellClickHandler = handler;
    };
     /**计算坐标
     *
     */
     var calculateOffset = function(field, attr) 
     {
         var offset = 0;
         while(field) 
         {
           offset += field[attr]; 
           field = field.offsetParent;
         }
         return offset;
     };
     
     var calculateOffsetTop = function(field) 
     {
         return calculateOffset(field, "offsetTop");
     };
     
     var calculateOffsetLeft = function(field) 
     {
   		return calculateOffset(field, "offsetLeft");
     };
     
     var  setOffsets = function() 
     {
         //var end = inputField.offsetWidth;
         
         //设置框度
         var width = holderWidth;
       	 var field =  srcObject; //document.body;
         var left = calculateOffsetLeft(field)+  clientX; //field.offsetWidth;// +
         
         var top = calculateOffsetTop(field) + clientY; // field.offsetHeight;//

         popupDiv.style.border = "black 1px solid";
         popupDiv.style.left = left + "px";
         popupDiv.style.top = top + "px";
         nameTable.style.width = width;
     };
     
     
    /**得到行数据，解析并生成表格元素

    *
    *@修改： 2008-1-08 添加项的 supplement 属性作为补充数据

    *
    */
	var  setNames = function(items) 
	{            
		var size = items.length;
		var row, cell, txtNode;
		var itemId;
		var itemName;
		var itemType;
		
		//大小设置
		if(scrollSize < size )
		{
			popupDiv.style.height = holderHeight;
		}
		
		for (var i = 0; i < size; i+=1) 
		{
			itemId   = items[i].itemId;
        	itemType = items[i].itemType;
        	itemName = items[i].itemName;
			
			 
		    var nextNode = itemName;
		    row = document.createElement("tr");
		    cell = document.createElement("td");
		    
		    //设置鼠标事件发生时的样式
		    cell.onmouseout = function() 
		    {
		    	this.style.cssText = "background: #FFFAFA; color: #000000;";
		    	//this.className='mouseOver';
		    };
		    cell.onmouseover = function() 
		    {
		    	this.style.cssText =" background: #708090; color: #FFFAFA;";
		    	//this.className='mouseOut';
		    };
		    cell.itemId = itemId;
			cell.itemType = itemType;
		    cell.setAttribute("bgcolor", "#FFFAFA");
		    cell.setAttribute("border", "0");
		    cell.onclick = function() 
		    { 
		    	cellClickHandler(this); 
		    };                             
			//添加节点
			cell.innerHTML = nextNode;
		    row.appendChild(cell);
		    nameTableBody.appendChild(row);
		}
	};
     
   /**解析xmldoc 
    *
    * 生成名称列表
    */
    var buildNameList = function() 
    {
     	//设置显示位置
    	setOffsets();
    	setNames(p_dataItems);
    };
	
	/**
	*	初始化对象，建立结构
	*
	*/
	initVars();
	
	/**
	*	建立名称列表
	*/
   	buildNameList();
}
