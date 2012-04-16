MapUtils = Class.create();
MapUtils.prototype = 
{
    /**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container)
    {
        this.container = container;  
        this.intervalId = -1;
    },

	/**
	 * 闪烁显示绘制点
	 */
	flashPoint:function(container,pointArray,zoom,times)
	{
		if((pointArray == null) || (pointArray.length !=2))
	    {
	    	return false;
	    }
		var divFlash = document.getElementById("flashpoints");
		if(divFlash == null)
		{
			 if(typeof(document.createElementNS) != 'undefined')
			{
				divFlash = document.createElementNS('http://www.w3.org/1999/xhtml', "div");
			}
		    if(typeof(document.createElement)!= 'undefined') 
		    {
				divFlash = document.createElement("div");
		    }    
			divFlash.id = "flashpoints";
			divFlash.style.zIndex = 10001;		
			divFlash.position ="absolute";
			divFlash.style.visibility = "visible";
		}
		else
		{
			divFlash.parentNode.removeChild(divFlash);
		}
		
	    //移动到屏幕中心
		map.moveToCenter(new Point(pointArray[0] * 1e16, pointArray[1] * 1e16));
			
		//将经纬度坐标转化成桌面坐标	
		var infoCoord = new Coordinate(pointArray[0]*1e16 , pointArray[1]*1e16);
		var scoord = Util.getScreenPixel(infoCoord, zoom);
		var strContent = "<img src=\"" + ImageBaseDir + "flash/center.gif\" style=\"position: absolute; opacity: 0.8; left: " + scoord.x + "px; top:" + scoord.y + "px;\"/>";
		divFlash.innerHTML = strContent;
		container.appendChild(divFlash);		
			          
		//执行多次
		var intTimes = 0;
		if(this.intervalId != null && this.intervalId  != -1)
		{
			 clearInterval(this.intervalId);
		}
		this.intervalId = setInterval(function()
		             {
		                divFlash.style.background = (divFlash.style.background == "white" ? "red" : "white");
		           		intTimes ++;
						if(intTimes > times * 2 - 1)
						{
						   clearInterval(this.intervalId);  
						   this.intervalId = -1;					   
						   divFlash.parentNode.removeChild(divFlash);		
						}
		             },2000);   
	},

	/**
	 * 闪烁显示绘制线路
	 */
	flashLine : function(container,linepoints,zoom,times)
	{
	    if(linepoints == null)
	    {
	    	return false;
	    }
		var divFlash = document.getElementById("flashpoints");
		if(divFlash == null)
		{
			 if(typeof(document.createElementNS) != 'undefined')
			{
				divFlash = document.createElementNS('http://www.w3.org/1999/xhtml', "div");
			}
		    if(typeof(document.createElement)!= 'undefined') 
		    {
				divFlash = document.createElement("div");
		    }    
			divFlash.id = "flashpoints";
			divFlash.style.visibility = "visible";
			divFlash.zIndex = 4000;		
		}
		else
		{
			divFlash.parentNode.removeChild(divFlash);
		}
		
		var strContent = "";
		var strScreenPoints = "";
		var coord ;
		for(var i = 0 ; i < linepoints.length; i++)
		{	
			//将坐标转化为整形
			coord = linepoints[i].split(",");		
			
			var infoCoord = new Coordinate(coord[0]*1e16 , coord[1]*1e16);	
			var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.	
			if(i == 0)
			{	
				strScreenPoints = scoord.x + "," + scoord.y;
			}
			else
			{
				strScreenPoints += 	"," + scoord.x + "," + scoord.y;
			}
		}	
		
		var vmlLine = document.getElementById("flashline");
		if(vmlLine != null)
		{
		   vmlLine.parentNode.removeChild(vmlLine);
	    }       	 
	    
	    vmlLine = document.createElement("v:polyline");
	   	vmlLine.id = "flashline";
		vmlLine.strokecolor="green";
		vmlLine.strokeweight="2pt";
		vmlLine.filled = false;
	    vmlLine.points = strScreenPoints;      //加入地图后无法更改
	   	container.appendChild(vmlLine);  
			
		divFlash.innerHTML = strContent;
		divFlash.appendChild(vmlLine);
		container.appendChild(divFlash);
		
		//执行一次
		/*setTimeout(function()
		          {	             
		             divFlash.style.visibility = "hidden";
		          },2000);*/
		          
		//执行多次
		var intTimes = 0;
		if(this.intervalId != null && this.intervalId  != -1)
		{
			 clearInterval(this.intervalId);
		}
		this.intervalId = setInterval(function()
		             {
		                divFlash.style.visibility = (divFlash.style.visibility == "visible" ? "hidden" : "visible");
		           		vmlLine.visible = (vmlLine.visible == true ? false : true);
		           		intTimes ++;
						if(intTimes > times * 2 - 1)
						{
						   clearInterval(this.intervalId);  
						   this.intervalId = -1;
						   //vmlLine.parentNode.removeChild(vmlLine);					   
						   divFlash.parentNode.removeChild(divFlash);		
						}
		             },400);   
	},

	/**
	 * 闪烁显示绘制多边形
	 */
	flashPolygon:function(container,polygonpoints,zoom,times)
	{
		if(polygonpoints == null)
	    {
	    	return false;
	    }
		var divFlash = document.getElementById("flashpoints");
		if(divFlash == null)
		{
			 if(typeof(document.createElementNS) != 'undefined')
			{
				divFlash = document.createElementNS('http://www.w3.org/1999/xhtml', "div");
			}
		    if(typeof(document.createElement)!= 'undefined') 
		    {
				divFlash = document.createElement("div");
		    }    
			divFlash.id = "flashpoints";
			divFlash.style.visibility = "visible";
			divFlash.zIndex = 4000;		
		}
		else
		{
			divFlash.parentNode.removeChild(divFlash);
		}
		
		var strContent = "";
		var strScreenPoints = "";
		var coord ;
		for(var i = 0 ; i < polygonpoints.length; i++)
		{	
			if(polygonpoints[i] == null)
			{
				continue;
			}
			
			//将坐标转化为整形
			coord = polygonpoints[i].split(",");		
			
			var infoCoord = new Coordinate(coord[0]*1e16 , coord[1]*1e16);	
			var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.	
			strContent += "<img src=\"" + ImageBaseDir + "flash/node.gif\" style=\"position: absolute; opacity: 0.8; left: " + scoord.x + "px; top:" + scoord.y + "px;\"/>";
			if(i == 0)
			{	
				strScreenPoints = scoord.x + "," + scoord.y;
			}
			else
			{
				strScreenPoints += 	"," + scoord.x + "," + scoord.y;
			}
		}	
	
		var vmlLine = document.getElementById("flashline");
		if(vmlLine != null)
		{
		   vmlLine.parentNode.removeChild(vmlLine);
	    }       	 
	    
	    vmlLine = document.createElement("v:polyline");
	   	vmlLine.id = "flashline";
		vmlLine.stroked = false;
		vmlLine.filled = false;
	    vmlLine.points = strScreenPoints;      //加入地图后无法更改
	   	container.appendChild(vmlLine);  
			
		divFlash.innerHTML = strContent;
		divFlash.appendChild(vmlLine);
		container.appendChild(divFlash);		
		          
		//执行多次
		var intTimes = 0;
		if(this.intervalId != null && this.intervalId  != -1)
		{
			 clearInterval(this.intervalId);
		}
		this.intervalId = setInterval(function()
		             {
		                divFlash.style.visibility = (divFlash.style.visibility == "visible" ? "hidden" : "visible");
		           		vmlLine.visible = (vmlLine.visible == true ? false : true);
		           		intTimes ++;
						if(intTimes > times * 2 - 1)
						{
						   clearInterval(this.intervalId);  
						   intervalId = -1;					   
						   divFlash.parentNode.removeChild(divFlash);		
						}
		             },400);   
	},
	
    /**
	 * 所属辖区居中并放大显示.
	 * @param deptID 机构编码
	 */
	moveToAreaCenter: function(deptID)
	{
		if (deptID == null){
			deptID = "";	
		}
		
		var url = 'webgis.MapLabel.getAreaCenter.d';
		var params = "deptID=" + deptID;
		params = encodeURI(params);
		new Ajax.Request(url, 
		{
				method: 'post', 
				parameters: params, 
				onComplete: MapUtils.prototype.finishMoveCenter
		});
	},
	
	finishMoveCenter: function(xmlHttp)
	{
		var coordX = 0;
		var coordY = 0;
		var centPoint = "";
		var zoomLevel = 0;
		
		var txtData = xmlHttp.responseText;
		if (txtData != "null" && txtData.length > 0){
			var aTxtData = txtData.split(";");
			var aPoint = aTxtData[1].split(",");
			coordX = Number(aPoint[0]) * 1e16;
			coordY = Number(aPoint[1]) * 1e16;
			zoomLevel = Number(aTxtData[0]);
		}else{
			coordX = Number(getMapParameter("gisfactcenterx")) * 1e16;
			coordY = Number(getMapParameter("gisfactcentery")) * 1e16;
			zoomLevel = Number(getMapParameter("gisDeviceLevel"));		
		}
		
		centPoint = new Point(coordX, coordY);		
		map.moveToCenter(centPoint,zoomLevel);
	}
	
};
