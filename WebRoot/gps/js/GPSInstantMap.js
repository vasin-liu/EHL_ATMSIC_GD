
var gpsInterId = null;	 //gps定时器
/*
 * GPS显示类 */
GPSInstantMap = Class.create();
GPSInstantMap.prototype = { 
	/**
	 * 定义存储GPS车辆坐标数组
	 */
	carList : null,
	perPageCount: 12, 	//每页的记录数量	showLevel:4,	 	//在地图的第几级显示gps车辆
	stopAlarmMinutes: 2,	//停止时间过长报警（分钟）
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
	readPoints: function(){
		var url = 'gps.GPSMap.ReadPoints.d';
		var params = "minx=" + FullExtent.getMinX()/1e16 + "&maxy=" + FullExtent.getMaxY()/1e16 + "&maxx=" + FullExtent.getMaxX()/1e16 + "&miny=" + FullExtent.getMinY()/1e16;
//		alert(params);
		params = encodeURI(params);
		new Ajax.Request(url, {
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: GPSInstantMap.prototype.finishReadPoints
		});
	},
	//显示GPS车辆
	showGPS:function()
	{
	    gpsmap.readPoints();
	    if(!gpsInterId)
	    {
	   		gpsInterId = setInterval("gpsmap.readPoints()", gpsInterval * 1000);
	   	}
	},  
	
	//删除GPS车辆
	clearGPSPoint:function()
	{
		if(gpsInterId)
		{
			clearInterval(gpsInterId);
			gpsInterId = null;
		}
		if(carList)
		{
			for(var i=0;i< carList.length; i++)
			{
				if(carList[i].length > 7)
				{
					var strSymbolId = carList[i][7];
					//判断是否已经存在
			  		var pointSymbol = document.getElementById(strSymbolId);
			  		if(pointSymbol != null)
			  		{
		  		   		pointSymbol.parentNode.removeChild(pointSymbol);
		  		    }
				}
			}
		}
	},
	
    /**
     ** 完成服务端读取后，将获取到的数据存入到内存数组中
     */
	finishReadPoints: function(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
	//	alert(xmlHttp.responseText);
		var results = xmlDoc.getElementsByTagName("row");
		carList = new Array();
		//cityid,carnumber,cartype,department,driver,longtitude,latitude
		for (var i = 0; i < results.length; i++){
			var rowResult = results[i].childNodes;
			var rowArray = new Array(8);

			var id = "";					
			var idObj = rowResult[0].firstChild;
			if (idObj != null){
				id = idObj.nodeValue;
			}
						
			var cityid = "";					
			var cityidObj = rowResult[1].firstChild;
			if (cityidObj != null){
				cityid = cityidObj.nodeValue;
			}
			
			var carnumber = "";					
			var carnumberObj = rowResult[2].firstChild;
			if (carnumberObj != null){
				carnumber = carnumberObj.nodeValue;
			}
			
			var cartype = "";					
			var cartypeObj = rowResult[3].firstChild;
			if (cartypeObj != null){
				cartype = cartypeObj.nodeValue;
			}
			
			var department = "";					
			var departmentObj = rowResult[4].firstChild;
			if (departmentObj != null){
				department = departmentObj.nodeValue;
			}
			
			var driver = "";					
			var driverObj = rowResult[5].firstChild;
			if (driverObj != null){
				driver = driverObj.nodeValue;
			}

			
			
			var coord = "";
			var coordLongObj = rowResult[6].firstChild;
			var coordLatObj = rowResult[7].firstChild;
			if (coordLongObj != null && coordLatObj != null){
				coord = coordLongObj.nodeValue + "," + coordLatObj.nodeValue;			
			}
			
			var phoneCode = "";					
			var driverObj = rowResult[9].firstChild;
			if (driverObj != null){
				phoneCode = driverObj.nodeValue;
			}
			
			var onlineFlag = "";					
			var driverObj = rowResult[10].firstChild;
			if (driverObj != null){
				onlineFlag = driverObj.nodeValue;
			}
			var stopTime = results[i].getElementsByTagName('stop');
			var crossTime = results[i].getElementsByTagName('cross');
			
			
			
			rowArray[0] = id;
			rowArray[1] = cityid;
			rowArray[2] = carnumber;
			rowArray[3] = cartype;
			rowArray[4] = department;
			rowArray[5] = driver;
			rowArray[6] = coord;
			rowArray[7] = "";
			rowArray[8] = phoneCode;
			rowArray[9] = onlineFlag;
			//停止时间
			if( 0 < stopTime.length)
			{
				stopTime = stopTime[0].text;
				rowArray[10] = stopTime;
			}
			//越辖区时间
			if( 0 < crossTime.length )
			{
				crossTime = crossTime[0].text;
				rowArray[11] = crossTime;	
			}
			
			carList[i] = rowArray; 
		}	
		
		GPSInstantMap.prototype.showAllCarPoint();
		
		//设置重点监控车辆居中显示 
		 if (zdCarId != null ){
	    	GPSInstantMap.prototype.centerCarInfo(zdCarId);
	    }
	},
	
    /**
     * 在地图上显示所有GPS车辆
     */
    showAllCarPoint: function()
    {
    	if(GPSInstantMap.prototype.showLevel > map.getCurrLevel())
    	{
    		return;
    	}
    	
    	//判断是否为空或者对象未被实例化
    	if (typeof carList == "undefined" || carList == null){
    		return;
    	}
    	var multiplier = 2;
 		var thisZoom = map.getCurrentZoom();
		var width = thisZoom.getViewBound(map.container).getWidth();
		var height = thisZoom.getViewBound(map.container).getHeight();
		var maxx = thisZoom.getViewBound(map.container).getCenterCoord().x + width*(multiplier/2);
		var minx = thisZoom.getViewBound(map.container).getCenterCoord().x - width*(multiplier/2);
		var maxy = thisZoom.getViewBound(map.container).getCenterCoord().y + height*(multiplier/2);
		var miny = thisZoom.getViewBound(map.container).getCenterCoord().y - height*(multiplier/2);
    	//var params = "minx=" + minx/1e16 + "&maxy=" + maxy/1e16 + "&maxx=" + maxx/1e16 + "&miny=" + miny/1e16;
		//alert(params);
    	
		//获取地图图层和当前显示范围    	var mapDiv = map.getVMLDiv();
    	var currentZoom =  map.getCurrentZoom();
    	
    	//停车场图层中停车场的坐标数据
		for(var i = 0; i < carList.length; i++){			
			//alert(carList[i][6]);
			var coordxyArr = carList[i][6].split(",");//转化为坐标对
			if( (coordxyArr[0]*1e16 >= minx && coordxyArr[0]*1e16 <= maxx) && coordxyArr[1]*1e16 >=miny && coordxyArr[1]*1e16 <=maxy)
 			{
				var infoCoord = new Coordinate((coordxyArr[0]*1e16), (coordxyArr[1]*1e16));//将坐标转化为整形
				this.showCarSymbol(mapDiv, infoCoord, currentZoom, i, carList[i]);//在地图上绘制出当前的坐标
			}
		}
   	},  
	
	/**
     * 构造函数.
     * @param {Object} mapDiv 地图图层对象
     * @param {Object} infoCoord 经纬度坐标信息     * @param {Object} zoom 当前屏幕显示区域
     * @param {Integer} order 标注顺序号     */
 	showCarSymbol:function(mapDiv, infoCoord, zoom, order, currCar)
 	{
  		var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.		
  		var strSymbolId = "SymbolGPS_" + currCar[0];
  		
  		//判断是否已经存在
  		var pointSymbol = document.getElementById(strSymbolId);
  		if(pointSymbol != null){
  		   pointSymbol.parentNode.removeChild(pointSymbol);
  		}
   		
		pointSymbol = document.createElement("div");
		pointSymbol.id = strSymbolId;
		pointSymbol.style.position = "absolute";
		
		pointSymbol.style.border = "0px solid #999999";
		pointSymbol.style.fontSize = "12px";
		pointSymbol.style.padding = "1px";
		pointSymbol.style.zIndex = 11;
		pointSymbol.style.left = (scoord.x - 20) + "px";
		pointSymbol.style.top = (scoord.y - 28)+ "px"; 
		var carNumber = currCar[2];
		var carType = currCar[3];
		var imageName = "car.gif";
		for(var ele in GPSHelper.GPSTypeList)
		{
			if(	GPSHelper.GPSTypeList[ele][0] == carType)
			{
				if("是" == currCar[9])
				{	
					imageName = GPSHelper.GPSTypeList[ele][1];
				}
				else
				{
					imageName = GPSHelper.GPSTypeList[ele][2];
				}
			}
		} 
		
		var imgPath = "images/gps/" + imageName;
		var image =	Util.createImg("GPS", 0, 0, 15, 12, imgPath);	
		image.title = carNumber;
		image.style.cursor ="pointer";
		image.position = infoCoord;
		image.order = order;
		image.id = currCar[0];
		//添加双击处理事件和鼠标移开事件，双击显示气泡窗体，移开隐藏显示.
		Event.observe(image, "click", this.showMapTips.bindAsEventListener(image));
		
		//建立表
		var tableElement = window.document.createElement("table");
		var bodyElement = window.document.createElement("tbody");
		tableElement.appendChild(bodyElement);
		
		//添加车牌号码行
		var  row = window.document.createElement("tr");
		var  cell = window.document.createElement("td");
		var txtCarNumber = window.document.createElement("span");
		if("是" == currCar[9])
		{
			txtCarNumber.style.cssText = "color:blue;font-size:8pt";
		}
		else
		{
			txtCarNumber.style.cssText = "color:gray;font-size:8pt";
		}
		
		txtCarNumber.appendChild(document.createTextNode(carNumber));
	
		row.appendChild(cell);
		cell.appendChild(txtCarNumber);
		bodyElement.appendChild(row);
		
		//添加图片
		row = window.document.createElement("tr");
		cell = window.document.createElement("td");
		cell.setAttribute("align","center");
		cell.appendChild(image);
		row.appendChild(cell);
		bodyElement.appendChild(row);
		
		pointSymbol.appendChild(tableElement);
		
		pointSymbol.style.display = "";
		mapDiv.appendChild(pointSymbol);
		carList[order][7] = strSymbolId;
		
		var flag = false;
		//停止时间过长
		if( currCar[10] &&  currCar[10].length >0)
		{
		 	try
			{
				var stop = parseFloat(currCar[10]);
			 	if(! isNaN(stop) && stop > gpsmap.stopAlarmMinutes)
				{
					txtCarNumber.style.backgroundColor = "green";
					txtCarNumber.style.color="white"
					flag = true;
				}
				else
				{
					txtCarNumber.style.backgroundColor = "";
				}
			}
			catch(e)
			{
				alert(e);
			} 
		}
		//越辖区
		if( currCar[11] &&  currCar[11].length >0)
		{
			txtCarNumber.style.backgroundColor = "red";
			txtCarNumber.style.color="white"
		}
		else
		{
			if( ! flag )
			{
				txtCarNumber.style.backgroundColor = ""; 
			}
		}
	},
	/**
	 ** 显示提示窗口窗体
	*/
	showMapTips: function(event, left, top, position){		
	    var x = 0;
	    var y = 0;
	   
	   //获取当前对象的编程值：对象编号、经纬度坐标信息
	    var infoCoord ;
	    
	    //取得鼠标的绝对坐标
	    var vmlDiv = map.getVMLDiv();
	    if(event != null){	    	
			event = event ? event : (window.event ? window.event : null); 
			var source = event.srcElement || event.target;
		    x = Event.pointerX(event) - vmlDiv.offsetLeft||vmlDiv.scrollLeft;
			y = Event.pointerY(event) - vmlDiv.offsetTop||vmlDiv.scrollTop;
	    }	
	    else
	    {
	     	x = left + 12;
	     	y = top ;
	    }	
	    
	    if(typeof(position) == "undefined" || position == null){	    	
			infoCoord = this.position;
	    }
	    else{
	    	infoCoord = position;
	    }	    
		
		//显示正在查询提示		
		//MapTips.prototype.showLoadTips(vmlDiv,x,y);				
		//id,cityid,carnumber,cartype,department,driver,longtitude,latitude
		var strRes = "所在城镇: "+carList[this.order][1]+"<br/>";
		strRes += "车辆号牌: "+carList[this.order][2]+"<br/>";
		strRes += "GPS类型: "+carList[this.order][3]+"<br/>";
		strRes += "所属部门: "+carList[this.order][4]+"<br/>";
		strRes += "执勤民警: "+carList[this.order][5]+"<br/>";
		strRes += "呼叫号码: "+carList[this.order][8];
		if( carList[this.order][10] && 0<carList[this.order][10].length)
		{
			strRes += "<br/><span style='color:red' >车辆停止了： "+ carList[this.order][10] + "分钟</span>";
		}
		if( carList[this.order][11] && 0<carList[this.order][11].length)
		{
			strRes += "<br/><span style='color:red' >车辆： "+ carList[this.order][11] + "越辖区</span>";
		}
		
		//alert(strRes);
		MapTips.prototype.showPopup(vmlDiv, "GPS车辆信息", strRes, x, y);	        
	},
	/**
	 * 居中显示
	 * @id：道路编码
	 */
 	centerCarInfo : function(id){
 		var blnFind = false;
 		var index = -1;
 		if(typeof(carList)=="undefined" || carList == null) {
 			return;
 		}
 		
 		for(var i = 0; i < carList.length; i++){				
		    if(carList[i][0] == id){
		    	blnFind = true;
		    	index = i;
		    	break;
		    }	
		}
		if(blnFind == true){
		   	var mapDiv = map.getVMLDiv();
	    	var model = map.getMapModel();
	    	var currentZoom =  map.getCurrentZoom();

			//显示选中的禁行线路详细信息				
			var coordArray = carList[index][6].split(",");
			var infoCoord = new Coordinate(coordArray[0]*1e16 , coordArray[1]*1e16);
			
			this.order = index;
			
			var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
			var left = scoord.x;
			var top  = scoord.y;
			
			//设置居中位置
			map.moveToCenter(infoCoord);
			this.showMapTips(null,left,top,infoCoord);

		}
 	},
 		/**
	 * 设置视频点标注信息.
	 * @id：视频点编码
	 * @coord：视频点坐标
	 */
	setGpsCarInfo: function(id,carcode,coord,index)
	{		
		//控制选中状态		var tableObj = document.getElementById("tabGPSCarList");	
		var checkboxObj = tableObj.rows[index].cells[0].firstChild;

		if (index == gpsmap.rowID)
		{			
		    if (this.isClickedCheckBox){
				this.isClickedCheckBox = false;					
		        if (checkboxObj.checked){
					tableObj.rows[index].bgColor = this.selectBGColor;			
				}else{
					tableObj.rows[index].bgColor = this.unSelectBGColor;			
				}
			}else{	
		        if (checkboxObj.checked){
					checkboxObj.checked = false;
					tableObj.rows[index].bgColor = this.unSelectBGColor;			
				}else{
					checkboxObj.checked = true;	
					tableObj.rows[index].bgColor = this.selectBGColor;			
				}
			}			
		}else{	
			checkboxObj.checked = true;
			tableObj.rows[index].bgColor = this.selectBGColor;			
		}
		
		for(var i=1; i<tableObj.rows.length; i++)
        {
           if (i != index){
           	  tableObj.rows[i].cells[0].firstChild.checked = false;
			  tableObj.rows[i].bgColor = this.unSelectBGColor;			
           }
 		}

        //置视频点标注信息
        if (checkboxObj.checked)
        {
			this.tid = id;       
			this.coord = coord;
			this.rowID = index;
			this.carCode = carcode;
			//居中，并显示详细信息
 			this.centerCarInfo(id);
		}
		else
		{
			this.tid = null;
			this.carCode = null;
		}
 			
 	},
 	/**
	 * 获取GPS车辆列表
	 * @queryContainer: 容器控件名
	 */
	getGpscarQuery: function(queryContainer)
 	{
 		//形成查询条件
		var regnumber = $('txtregisternumberQ').value;        //车牌号码
		var countyCode  = $('txtgpscarCounty').branchId;		//辖区
		var txtClassifyQ = $('txtClassifyQ').value;
		
		var queryCond = "";
		if (regnumber != null && regnumber.length>0)
		{
		   queryCond +=  " AND t1.CARNUMBER like '%" + regnumber  + "%' ";
		}
		if(countyCode && countyCode.length>0)	//辖区信息查询控制
		{
			queryCond +=  " AND t1.DEPARTMENTID='" + countyCode + "' ";
		}
		if(txtClassifyQ && txtClassifyQ.length>0) 
		{ 
		    queryCond += " AND t1.CARCATALOG='"+txtClassifyQ+"'";
		}
		var group = $('group').value;
		if(group && group.length>0) {
		    
		    queryCond += " AND t2.GROUPID='"+group+"'";
		}
		
//		if(countyCode && countyCode.length>0)	//辖区信息查询控制
//		{
//			if("00000000" == branch_code.substring(4,12))	
//			{
//				//支队可查询所有辖区
//				 queryCond += " AND DEPARTMENTID='" + countyCode + "'";
//			}
//			else
//			{
//				//不是支队，只能查询本辖区
//				 queryCond += " AND DEPARTMENTID='" + branch_code + "'";
//			}
//		}
//		else
//		{
//			if("00000000" != branch_code.substring(4,12))	
//			{
//				//不是支队，只能查询本辖区
//				 queryCond += " AND DEPARTMENTID='" + branch_code + "'";
//			}
//		}
		var selectSQL = "SELECT  t1.CARCODE,t1.ID,t1.CARNUMBER,t1.CARTELNUMBER,t1.ISONLINE, t1.DEPARTMENT ,t1.LONGTITUDE,t1.LATITUDE ";
 		selectSQL += " FROM t_gps_carinfo t1,T_GPS_CARGROUP t2  ";
 		if( queryCond && queryCond.length>0 )
 		{
 			selectSQL += " WHERE t1.CARCODE=t2.CARCODE" + queryCond;
 		}
 		selectSQL += " ORDER BY t1.departmentid ";
 		pagingCarList = new DhtmlPaging(0,-1,this.perPageCount,selectSQL,$('gpscarPaging'),this.responseQuery);
		pagingCarList.setDBConfig("gps");
		pagingCarList.getPageData();
 	}, 	
 	
 	responseQuery : function(xmlHttp)
 	{
 		var xmlDoc = xmlHttp.responseXML;
 		var strTable = "<table id=\"tabGPSCarList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0>";
					
		//生成表头
		var heads  = ['车牌号码','呼叫号码','在 线','所属辖区'];
		var colWidths =[70,70,40,90];
		//var heads = xmlDoc.getElementsByTagName("head");
		strTable += "<tr class=\"scrollColThead\"><th class=\"title\" align=center width=15%>选择</th>";
		for(var i = 0 ; i < heads.length; i+=1)
		{
			var subHead = heads[i];
			strTable += "<th align=\"center\" class=\"title\" width=" + colWidths[i] + ">";
			strTable += subHead;
			strTable += "</th>";
		}											
		strTable += "</tr>";
					
		//生成结果页面
		var results = xmlDoc.getElementsByTagName("row");
		for (var i = 0; i < results.length; i+=1)
		{
			var rowResult = results[i].childNodes;
			var showCols = new Array();
			
			var gpscarID = "";					
			var tempObj = rowResult[0].firstChild;
			if (tempObj != null)
			{
				gpscarID = tempObj.data;
			}
			var carCode = "";
			var tempObj = rowResult[1].firstChild;
			if (tempObj != null)
			{
				carCode = tempObj.data;
			}
			
			//车牌号码
			tempObj = rowResult[2].firstChild;
			if (tempObj != null)
			{
				showCols.push(tempObj.nodeValue);
			}
			else
			{	
				showCols.push("&nbsp;");
			}
			//呼叫号码
			tempObj = rowResult[3].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			
			//驾驶员
			tempObj = rowResult[4].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			
			//所属辖区
			tempObj = rowResult[5].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			//坐标					
			var coord = "";
			var coordx= "";
			var coordy = "";
			tempObj = rowResult[6].firstChild;
			if (tempObj != null){
				coordx = tempObj.nodeValue;
			}
			tempObj = rowResult[7].firstChild;
			if (tempObj != null){
				coordy = tempObj.nodeValue;
			}
			coord = coordx + "," + coordy;
			
			//class=\"scrollRowThead\" 
		    strTable += "<tr class=\"rowstyle\" nowrap onclick=\"gpsmap.setGpsCarInfo('"+ gpscarID+ "','" + carCode + "','"+ coord +"',"+(i+1)+");\">";	
		    strTable += "<td align=\"center\">";
		    strTable += "<input type=\"checkbox\" id=\"chkSel"+i+"\" value=\""+coord+"\" onclick=\"gpsmap.isClickedCheckBox = true;\"/> </td>";
		    for(var j=0;j<showCols.length;j+=1)
		    {
		        strTable += "<td>" + showCols[j] + "</td>";
		    }					    					    
		    strTable += "</tr>";						
		}
		strTable += "</table>";
		
		//添加到结果面板上
         var conCtrl= $("gpscarQueryResult");
         conCtrl.innerHTML = strTable;	
   },
 	/**
	 * 获取GPS车辆列表
	 * @queryContainer: 容器控件名
	 */
 	getGpscarQuery_odl: function(queryContainer)
 	{
 		//形成查询条件
		var regnumber = document.getElementById('txtregisternumberQ').value;        //车牌号码
		var county = document.getElementById('txtgpscarCounty').value;   //所属辖区
		var countyCode  = $('txtgpscarCounty').branchId;
		
		var queryCond = "regnumber=" + regnumber;
		
		//判断输入，得到查询参数
		if (countyCode != null && countyCode.length>0)
		{
		  	 queryCond = "&county="+ countyCode ;
		}
		//准备AJAX调用参数
		var params = encodeURI(queryCond);
		
		//准备AJAX调用URL
		var url = encodeURI('gps.GPSMap.QueryGPSCar.d');
		
		//执行AJAX调用		
		new Ajax.Request(url,
			 {	method: 'post', 
               	parameters: params,
               	onComplete: function(xmlHttp)
               	{
               	    var xmlDoc = xmlHttp.responseXML;
					var strTable = "<table id=\"tabGPSCarList\" class=\"scrollTable\" width=98% cellSpacing=0 cellPadding=0 border=0>";
					
					//生成表头
					var heads  = ['车牌号码','呼叫号码','驾驶员','所属辖区'];
					var colWidths =[90,70,70,70];
					//var heads = xmlDoc.getElementsByTagName("head");
					strTable += "<tr class=\"scrollColThead\"><th class=\"scrollRowThead scrollCR\" align=center width=15%>选择</th>";
					for(var i = 0 ; i < heads.length; i+=1)
					{
						var subHead = heads[i];
						strTable += "<th align=\"center\" width=" + colWidths[i] + ">";
						strTable += subHead;
						strTable += "</th>";
					}											
					strTable += "</tr>";
								
					//生成结果页面
					var results = xmlDoc.getElementsByTagName("row");
					for (var i = 0; i < results.length; i+=1)
					{
						var rowResult = results[i].childNodes;
						var showCols = new Array();
						
						var gpscarID = "";					
						var tempObj = rowResult[0].firstChild;
						if (tempObj != null)
						{
							gpscarID = tempObj.data;
						}
						var carCode = "";
						var tempObj = rowResult[1].firstChild;
						if (tempObj != null)
						{
							carCode = tempObj.data;
						}
						
						//车牌号码
						tempObj = rowResult[2].firstChild;
						if (tempObj != null)
						{
							showCols.push(tempObj.nodeValue);
						}
						else
						{	
							showCols.push("&nbsp;");
						}
						//呼叫号码
						tempObj = rowResult[3].firstChild;
						if (tempObj != null){
							showCols.push(tempObj.nodeValue);
						}
						else
						{
							showCols.push("&nbsp;");
						}
						
						//驾驶员
						tempObj = rowResult[4].firstChild;
						if (tempObj != null){
							showCols.push(tempObj.nodeValue);
						}
						else
						{
							showCols.push("&nbsp;");
						}
						
						//所属辖区
						tempObj = rowResult[5].firstChild;
						if (tempObj != null){
							showCols.push(tempObj.nodeValue);
						}
						else
						{
							showCols.push("&nbsp;");
						}
						//坐标					
						var coord = "";
						var coordx= "";
						var coordy = "";
						tempObj = rowResult[6].firstChild;
						if (tempObj != null){
							coordx = tempObj.nodeValue;
						}
						tempObj = rowResult[7].firstChild;
						if (tempObj != null){
							coordy = tempObj.nodeValue;
						}
						coord = coordx + "," + coordy;
								
					    strTable += "<tr class=\"rowstyle\" nowrap ";	
					     strTable += "	 onclick=\"gpsmap.setGpsCarInfo('"+ gpscarID+ "','" + carCode + "','"+ coord +"',"+(i+1)+");\" >" ;
					    strTable += "<td class=\"scrollRowThead\" align=\"center\">";
					    strTable += "<input type=\"checkbox\" id=\"chkSel"+i+"\" value=\""+coord+"\" onclick=\"gpsmap.isClickedCheckBox = true;\"/> </td>";
					    for(var j=0;j<showCols.length;j+=1)
					    {
					        strTable += "<td>" + showCols[j] + "</td>";
					    }					    					    
					    strTable += "</tr>";						
					}
					strTable += "</table>";

					//添加到结果面板上
		            var conCtrl= document.getElementById(queryContainer);
		            conCtrl.innerHTML = strTable;	
               	}
             });
 	
 	},
 	 
 	/**打开查看历史轨迹页面
 	*
 	*/
 	OpenHistoryTrack:function()
	{
		//取得选中的车辆
		var gpscarCode = gpsmap.carCode;
		if(typeof gpscarCode == 'undefined' || null == gpscarCode || ""== gpscarCode)
		{
			alert('请选择要查看历史轨迹的车辆!');
			return;
		}
		var regnumber = "";
		var driver    = "";
		if(typeof  carList != 'undefined' && null !=  carList )
		for(var i=0;i< carList.length;i+=1)
		{
			if( carList[i][0] == gpsmap.tid)
			{
				regnumber = carList[i][2];
				driver	  = carList[i][5];
			}
		}
		var uri = "gpsTrace.jsp?carCode=" + gpscarCode + "&regnumber=" + regnumber + "&driver=" + driver;
		uri = encodeURI(uri)
		window.open(uri,'trace_gps','width=1000,height=620');
		
	},

 	/**
	 * 构建GPS车辆查询面板.
	 */
 	outputHTML: function()
	{
	  htmlTxt = '\
	    <div id="queryGPScar" class="queryDiv" style="offsetTop:0px">\
		  <table class="top_table">\
			<tbody>\
				<tr>\
					<td align="right" nowrap>&nbsp;车牌号码:</td>\
					<td ><input type="text" id="txtregisternumberQ" style="width:100px" /></td>\
					<td nowrap align="right">&nbsp;车辆分类:</td>\
					<td>\
					<select id="txtClassifyQ">\
					     <option selected>全部</option>\
					     <option value="0001">警用车辆</option>\
					     <option value="0002">危化品运输车辆</option>\
					      <option value="0003">长途客运车辆</option>\
					</select>\
					</td>\
					<td></td>\
				</tr>\
				<tr>\
					<td>&nbsp;车辆分组:</td>\
					<td id="txtgrouptd" style="width:100px"></td>\
					<td align="right">&nbsp;所属辖区:</td>\
					<td colspan=2><input type="text" id="txtgpscarCounty" style="width:100px" readOnly/>\
						<img src="../sm/image/popup/btnselect.gif" style="cursor:hand;"  alt="选择机构"  onclick="raiseCountyChoice(\'txtgpscarCounty\')">\
						</td>\
				</tr>\
				<tr>\
					<td valign="top" colspan=5  align="center"> \
					<input id=\"btnSearch\" type=\"button\" value=\" 查 询 \"   onclick=\"gpsmap.getGpscarQuery(\'gpscarQueryResult\')\" />\
						 &nbsp;&nbsp;\
					<input type="button"   id="btnZDWatch" value="重点监控" onClick="setZdCarId();">\
							 &nbsp;&nbsp;\
						<input type="button"    value="历史轨迹" onClick="gpsmap.OpenHistoryTrack();">\
						&nbsp;&nbsp;\
					</td> \
				</tr>\
			</tbody>\
		  </table>\
		</div>\
		<div id="gpscarPaging"  style="offsetTop:0px;"></div>\
		<div id="gpscarQueryResult" class="scrollDiv" style="text-align:center"></div>';
	  return htmlTxt;
	}
};