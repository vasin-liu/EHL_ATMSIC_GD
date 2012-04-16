/**
 *@说明：GPS 车辆信息显示和即时位置刷新
 *		2009-09-20  luchunqiang
 *
 */

var gpsInterId = null;	 //gps定时器

/**
 * GPS相关数据定义
 */
function InstantGroupMap()
{
	this.m_currentData = null;
	this.number="";
	this.person="";
 
	this.readURI = 'gps.GPSMap.readPoints.d';
	this.readByCodeURI = 'gps.GPSMap.getGPSInfoByPosition.d';
	this.prePageCount = 12;
	this.showLevel = 4;
	this.stopAlarmMinutes = 2;	//停止时间过长报警（分钟）
	this.bufferMulti = 2;		//缓存的倍数
	this.m_curBuffer = {};
	this.m_curBound = {};
	this.symbolPrefix = 'symGPS_';
	this.CarListTableID = 'tabGPSCarList';
	
	this.selectBGColor = "#DEEFF1"; //选中背景颜色
	this.unSelectBGColor = "white"; //未选中背景颜色
}

/**
 * 获取指定范围内的GPS信息
 */
InstantGroupMap.prototype.readGPSPoints = function()
{
		var url = this.readURI;
		var params = "minx=" + this.m_curBuffer.MinX/1e16 + "&maxy=" + this.m_curBuffer.MaxY/1e16;
		params += "&maxx=" + this.m_curBuffer.MaxX/1e16 + "&miny=" + this.m_curBuffer.MinY/1e16; 
		params = encodeURI(params);
		new Ajax.Request(url, 
		{
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: this.finishReadPoints.bindAsEventListener(this)
		});
};

/**
 * 定时 刷新 GPS 位置信息
 */
InstantGroupMap.prototype.refreshPoints = function()
{
    groupMap.readGPSPoints();
    if(!gpsInterId)
    {
   		gpsInterId = setInterval("groupMap.readGPSPoints()", gpsInterval * 1000);
   	}
};

/**
 * 完成服务端读取后，将获取到的数据存入到内存数组中
 */
InstantGroupMap.prototype.finishReadPoints = function(xmlHttp)
{
	var xmlDoc = xmlHttp.responseXML;
 	var results = xmlDoc.getElementsByTagName("row");
 	this.m_currentData = null;
	this.m_currentData = new Array();
	 
	for (var i = 0; i < results.length; i++)
	{
		this.m_currentData.push( this.getCarInfo(results[i]) ); 
	}	
	xmlDoc = null;
 	results = null;
	this.showAllCarPoint();
	
	//设置重点监控车辆居中显示 
	if (zdCarId != null )
	{
	  	this.centerCarInfo(zdCarId);
   	}
};

/**
 * 解析单条GPS数据
 */
InstantGroupMap.prototype.getCarInfo = function( rowXML )
{
	var rowResult = rowXML.childNodes;
	var rowArray = [
					 rowResult[0].text,
					 rowResult[1].text,
					 rowResult[2].text,
					 rowResult[3].text,
					 rowResult[4].text,
					 rowResult[5].text,
					 rowResult[6].text + ',' + rowResult[7].text,
					 '',
					 rowResult[9].text,
					 rowResult[10].text
					];
	var stopTime = rowXML.getElementsByTagName('stop');
	var crossTime = rowXML.getElementsByTagName('cross');
		
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
	return rowArray;
};

/**
 * 在地图上显示GPS车辆
 */
InstantGroupMap.prototype.showAllCarPoint =  function()
{
  	if(this.showLevel > map.getCurrLevel()  || (! this.m_currentData ) )
  	{
  	    
  		return;
  	}
  	// 判断是否在缓存范围内
  	// 获取当前的显示范围，与之前的缓存区域
  	// 如果不在缓存范围内，将重新从后端读取信息，否则进行展示
  	if(! this.isBoundInBuffer())
  	{
  		//alert('read');
  		this.readBoundPoints();
  		return;
  	}
  	
	//获取地图图层和当前显示范围
  	var mapDiv = map.getVMLDiv();
  	var currentZoom =  map.getCurrentZoom();
	  	
	for(var i = 0; i < this.m_currentData.length; i+=1 )
	{			
		var coordxyArr = this.m_currentData[i][6].split(",");//转化为坐标对
		if(coordxyArr.length ==2 && coordxyArr[0] && coordxyArr[0].length>0 && coordxyArr[1] && coordxyArr[1].length>0 )
		{
			if( (coordxyArr[0]*1e16 >= this.m_curBound['MinX'] && coordxyArr[0]*1e16 <= this.m_curBound['MaxX']) && 
						coordxyArr[1]*1e16 >= this.m_curBound['MinY'] && coordxyArr[1]*1e16 <=this.m_curBound['MaxY'])
			{
				var infoCoord = new Coordinate((coordxyArr[0]*1e16), (coordxyArr[1]*1e16));//将坐标转化为整形
				this.showCarSymbol(mapDiv, infoCoord, currentZoom, i,  this.m_currentData[i]);//在地图上绘制出当前的坐标
			}
		}
	}
};

/**
 * 判断是否在缓冲区域范围内
 */
InstantGroupMap.prototype.isBoundInBuffer = function()
{
	this.readCurrentBound();
	if(! this.m_curBuffer.MinX )
	{ 
		return false;
	}
	if( (this.m_curBuffer.MinX <= this.m_curBound.MinX && this.m_curBuffer.MaxX  >= this.m_curBound.MaxX ) && 
					this.m_curBuffer.MinY <= this.m_curBound.MinY && this.m_curBuffer.MaxY >= this.m_curBound.MaxY )
	{
		return true;
	}
	else
	{
		return false;
	}
};

/**
 * 更新缓冲区域，获取新区域上GPS数据
 */
InstantGroupMap.prototype.readBoundPoints = function()
{
	var thisZoom = map.getCurrentZoom();
	var width = thisZoom.getViewBound(map.container).getWidth();
	var height = thisZoom.getViewBound(map.container).getHeight();
//	this.m_curBuffer['MaxX'] = thisZoom.getViewBound(map.container).getCenterCoord().x + width/2 * this.bufferMulti ;
//	this.m_curBuffer['MinX'] = thisZoom.getViewBound(map.container).getCenterCoord().x - width/2 * this.bufferMulti;
//	this.m_curBuffer['MaxY'] = thisZoom.getViewBound(map.container).getCenterCoord().y + height/2 * this.bufferMulti;
//	this.m_curBuffer['MinY'] = thisZoom.getViewBound(map.container).getCenterCoord().y - height/2 * this.bufferMulti;
	this.m_curBuffer['MaxX'] = map.model.getViewCenterCoord().x + width/2 * this.bufferMulti ;
	this.m_curBuffer['MinX'] = map.model.getViewCenterCoord().x - width/2 * this.bufferMulti;
	this.m_curBuffer['MaxY'] = map.model.getViewCenterCoord().y + height/2 * this.bufferMulti;
	this.m_curBuffer['MinY'] = map.model.getViewCenterCoord().y - height/2 * this.bufferMulti;
	
	this.readGPSPoints();
};

/**
 * 获取地图上当前显示区域范围
 */
InstantGroupMap.prototype.readCurrentBound = function()
{
	var thisZoom = map.getCurrentZoom();
	
	var width = thisZoom.getViewBound(map.container).getWidth();
	var height = thisZoom.getViewBound(map.container).getHeight();
	this.m_curBound['MaxX'] = map.model.getViewCenterCoord().x + width/2;
	this.m_curBound['MinX'] = map.model.getViewCenterCoord().x - width/2;
	this.m_curBound['MaxY'] = map.model.getViewCenterCoord().y + height/2;
	this.m_curBound['MinY'] = map.model.getViewCenterCoord().y - height/2;
};

/**
 * 构造GPS标注 图标 元素
 */
InstantGroupMap.prototype.creatCarImgElement = function(carCode,carType,isOnline)
{
	var imageName = "car.gif";
	for(var ele in GPSHelper.GPSTypeList)
	{
		if(	GPSHelper.GPSTypeList[ele][0] == carType)
		{
			if("是" == isOnline)
			{	
				imageName = GPSHelper.GPSTypeList[ele][1];
			}
			else
			{
				imageName = GPSHelper.GPSTypeList[ele][2];
			}
		}
	} 
	var imgPath = "../../images/gps/" + imageName;
	var image =	Util.createImg("GPS", 0, 0, 15, 12, imgPath);	
	image.carCode = carCode;
	image.style.textAlign = "center";
	image.style.cursor ="pointer";
	image.id = "img_" + carCode;
	
	//添加双击处理事件和鼠标移开事件，双击显示气泡窗体，移开隐藏显示.
	Event.observe(image, "click", this.showMapTips.bindAsEventListener(image));
	return image;
};

/**
 * 在地图上绘制GPS点元素
 */
InstantGroupMap.prototype.paintCarPoint = function(symbolID, screenCoord ,carData)
{
	var pointSymbol = document.getElementById(symbolID);
	if(pointSymbol != null)
	{
	   	pointSymbol.style.left = (screenCoord.x - 20) + "px";
		pointSymbol.style.top = (screenCoord.y - 28)+ "px"; 
	}
	else
	{
		pointSymbol = document.createElement("div");
		pointSymbol.id = symbolID;
		pointSymbol.style.cssText = "position:absolute; border:0; font-size:12px; padding: 0; z-index:11;text-align:center";
 		pointSymbol.style.left = (screenCoord.x - 20) + "px";
		pointSymbol.style.top = (screenCoord.y - 28)+ "px"; 
		
		var oImgEle = this.creatCarImgElement(carData[0],carData[3],carData[9]);
		
	 
		var brElement = window.document.createElement("br");
	 	brElement.style.cssText = "line-height:2px";
		
		//添加车牌号码行
 
		var oCarPlate = window.document.createElement("span");
		if("是" == carData[9])
		{
			oCarPlate.style.cssText = "color:blue;font-size:8pt";
		}
		else
		{
			oCarPlate.style.cssText = "color:gray;font-size:8pt";
		}
		
		oCarPlate.appendChild(document.createTextNode(carData[2]));
	
	 	oCarPlate.appendChild(brElement);
	 	oCarPlate.appendChild(oImgEle);
	 	
		pointSymbol.appendChild(oCarPlate);
 
		pointSymbol.plate = oCarPlate;
		pointSymbol.carImg = oImgEle;
		
		var mapDiv = map.getVMLDiv();
		mapDiv.appendChild(pointSymbol);	
	}
	return pointSymbol;
};

/**
  * 在地图上绘制GPS信息
  * @param {Object} mapDiv 地图图层对象
  * @param {Object} infoCoord 经纬度坐标信息
  * @param {Object} zoom 当前屏幕显示区域
  * @param {Integer} order 标注顺序号
  * @param {Integer} currCar GPS信息
  */
InstantGroupMap.prototype.showCarSymbol = function(mapDiv, infoCoord, zoom, order, currCar)
{
 	//由经纬度转屏幕坐标.
  	var scoord = Util.getScreenPixel(infoCoord, zoom);		
	var strSymbolId = this.symbolPrefix + currCar[0];
	
	var oCarEle = this.paintCarPoint(strSymbolId,scoord,currCar);
	oCarEle.carImg.dataIndex = order;
	this.m_currentData[order][7] = strSymbolId;
	
	var flag = false;
	try
	{
		//停止时间过长
		var bLimit = this.checkStopLimit( currCar[10], oCarEle);
		//越辖区
		var bOut = this.checkOutOfRegion(currCar[11], oCarEle);
		if( ! (bLimit || bOut) )
		{ 
			oCarEle.plate.style.backgroundColor = "";
		}
	}
	catch(e)
	{
		//alert(e);
	} 
};


/**
 * 判断停止限制，展示不同样式 
 */
InstantGroupMap.prototype.checkStopLimit = function(stoptime,oCarEle)
{	
	if(stoptime &&  stoptime.length >0)
	{
		var stop = parseFloat(stoptime);
	 	if(! isNaN(stop) && stop > groupMap.stopAlarmMinutes)
		{
			oCarEle.plate.style.backgroundColor = "green";
			oCarEle.plate.style.color="white"
			
			return true;
		}
	}
	return false;
};

/**
 * 判断越辖区限制，展示不同样式 
 */
InstantGroupMap.prototype.checkOutOfRegion = function( outInfo ,oCarEle )
{
	if( outInfo &&  outInfo.length >0)
	{
		oCarEle.plate.style.backgroundColor = "red";
		oCarEle.plate.style.color="white"
		return true;
	}
	return false;
};


/**
 * 获取GPS数据展示html字符串
 */
InstantGroupMap.prototype.getCarSummery = function( carData )
{
	var strHtml = '<table>';
	//strHtml =  "所在城镇: "+ carData[1]+"<br/>";
	
	strHtml += "<tr><td align=right>车辆号牌:</td><td> "+ carData[2]+"</td></tr>";
	strHtml += "<tr><td align=right>GPS类型:</td><td> "+ carData[3]+"</td></tr>";
	strHtml += "<tr><td align=right>所属部门:</td><td> "+ carData[4]+"</td></tr>";
	strHtml += "<tr><td align=right>执勤民警:</td><td> "+ carData[5]+"</td></tr>";
	strHtml += "<tr><td align=right>呼叫号码:</td><td> "+ carData[8]+"</td></tr>";
	strHtml += "</table>";
	strHtml += "<hr/>";
	if( carData[10] && 0<carData[10].length)
	{
		strHtml += "<br/><span style='color:red' >车辆停止了： "+ carData[10] + "分钟</span>";
	}
	if( carData[11] && 0<carData[11].length)
	{
		strHtml += "<br/><span style='color:red' >车辆： "+ carData[11] + "越辖区</span>";
	}
	
	return strHtml;
};


/**
 * 显示信息窗体
*/
InstantGroupMap.prototype.showMapTips = function(event, carData)
{		
    var x = 0;
    var y = 0;
   
    //取得鼠标的绝对坐标
    var vmlDiv = map.getVMLDiv();
    if(event != null)
    {	    	
		event = event ? event : (window.event ? window.event : null); 
		var source = event.srcElement || event.target;
	    x = Event.pointerX(event) - vmlDiv.offsetLeft||vmlDiv.scrollLeft;
		y = Event.pointerY(event) - vmlDiv.offsetTop||vmlDiv.scrollTop;
		carData = groupMap.m_currentData[this.dataIndex] ;
    }	
    else
    {
    	//获取当前对象的编程值：对象编号、经纬度坐标信息
   		var infoCoord = '';
    	var coordxyArr = carData[6].split(",");//转化为坐标对
	 	infoCoord = new Coordinate((coordxyArr[0]*1e16), (coordxyArr[1]*1e16));//将坐标转化为整形
    	var scoord = Util.getScreenPixel(infoCoord, map.getCurrentZoom());
     	x = scoord.x + 12;
     	y = scoord.y ;
    }	
    
	//显示正在查询提示		
	var strHtml = groupMap.getCarSummery(carData);
	MapTips.prototype.showPopup(vmlDiv, "GPS车辆信息", strHtml, x, y);	        
};

/**
 * 居中显示
 * 
 */
InstantGroupMap.prototype.centerCarInfo = function(carCode)
{
	if( ! this.m_currentData ) 
	{
		return;
	}
	var findIndex = -1;
	for(var i = 0; i < this.m_currentData.length; i+=1 )
	{				
	    if(this.m_currentData[i][0] == carCode)
	    {
	    	findIndex = i;
	    	break;
	    }	
	}
	if(findIndex != -1)
	{	
		this.carChoiceHandler(this.m_currentData[findIndex]);
	}
	else
	{
		/**
		*/
		this.readInfoByCarCode(carCode);
	}
};

/**
 * 获取指定GPS的信息
 */
InstantGroupMap.prototype.readInfoByCarCode = function(carCode)
{
	// this.readByCodeURI = 'gps.GPSMap.readPoints.d';
 	var url =  this.readByCodeURI;
	var params = "carCode=" + carCode; 
	params = encodeURI(params);
	new Ajax.Request(url, 
	{
		method: 'post', 
		parameters: params, 
		
		//读取完成后，放入内存数组
		onComplete: this.finishGetCarInfo.bindAsEventListener(this)
	});
};

/**
 * 解析单个GPS的数据
 */
InstantGroupMap.prototype.finishGetCarInfo = function(xmlHttp)
{
	var xmlDoc = xmlHttp.responseXML;
 	var results = xmlDoc.getElementsByTagName("row");
  	var carData =  null;
	for (var i = 0; i < results.length; i+=1)
	{
		carData = this.getCarInfo(results[i]) ; 
	}	
	xmlDoc = null;
 	results = null;
	this.carChoiceHandler(carData);
};


/**
 * GPS选择事件处理
 */
InstantGroupMap.prototype.carChoiceHandler = function(carData)
{
	//显示选中的禁行线路详细信息				
	var coordArray = carData[6].split(",");
	var infoCoord = new Coordinate(coordArray[0]*1e16 , coordArray[1]*1e16);
	
	var currentZoom =  map.getCurrentZoom();
	var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
	var left = scoord.x;
	var top  = scoord.y;
	
	//设置居中位置
	map.moveToCenter(infoCoord);
	this.showMapTips(null,carData);
};
	
/**
 * 设置选中GPS相关数据
 */ 
InstantGroupMap.prototype.setGpsCarInfo = function(carcode,index)
{		
	//控制选中状态
	var tableObj = document.getElementById( this.CarListTableID );	
	tableObj.selectCarCode = carcode;
	var checkboxObj = tableObj.rows[index].cells[0].firstChild;
	if( tableObj.selectedRowIndex && tableObj.rows[ tableObj.selectedRowIndex ] )
	{
		var orginalCheckBox = tableObj.rows[ tableObj.selectedRowIndex ].cells[0].firstChild;
		
		if (index == tableObj.selectedRowIndex)
		{			
			orginalCheckBox.checked = ! orginalCheckBox.checked;
		    			
	        if (orginalCheckBox.checked)
	        {
				tableObj.rows[index].bgColor = this.selectBGColor;			
			}
			else
			{
				tableObj.rows[index].bgColor = this.unSelectBGColor;			
			}
		}
		else
		{	
			orginalCheckBox.checked = false;
			tableObj.rows[tableObj.selectedRowIndex].bgColor = this.unSelectBGColor;			
			 
			checkboxObj.checked = true;	
			tableObj.rows[index].bgColor = this.selectBGColor;			
		}			
	}
	else
	{	
		checkboxObj.checked = true;
		tableObj.rows[index].bgColor = this.selectBGColor;			
	}
	
	
    if (checkboxObj.checked)
    {
		//居中，并显示详细信息
		this.centerCarInfo(carcode);
		tableObj.selectedRowIndex = index;
    	tableObj.selectCarCode = carcode;
	} 
	else
	{
		tableObj.selectedRowIndex = null;
    	tableObj.selectCarCode = null;
	}
};
 
/**
 * 获取GPS车辆列表
 * @queryContainer: 容器控件名

 */
InstantGroupMap.prototype.getGpscarQuery = function(queryContainer)
{
	//形成查询条件
	var regnumber = $('txtregisternumberQ').value;        //车牌号码
	var number = regnumber;
	var countyCode  = $('txtgpscarCounty').branchId;		//辖区
//	var txtClassifyQ = $('txtClassifyQ').value;
	var queryCond = "";
	if (regnumber != null && regnumber.length>0)
	{
	   queryCond +=  "t1.CARNUMBER like '%" + regnumber  + "%' ";
	}
	if(countyCode && countyCode.length>0)	//辖区信息查询控制
	{
		if(regnumber != null && regnumber.length>0){
		queryCond +=  " and t1.DEPARTMENTID='" + countyCode + "'";
		}
		else{	
		queryCond +=  " t1.DEPARTMENTID='" + countyCode + "'";
		}
	}
	var groupID = $('group').value;
	var catalogID = $('catalog').value;
	var selectSQL = "SELECT  t1.CARCODE,t1.CARNUMBER,t1.CARTELNUMBER,t1.ISONLINE, t1.DEPARTMENT ,t1.LONGTITUDE,t1.LATITUDE ";
	selectSQL += " FROM t_gps_carinfo t1 " ;
		if(groupID && groupID.length > 0 && catalogID && catalogID.length > 0){
		    //selectSQL += ",T_GPS_CARGROUP t2,T_GPS_CARCATALOG t3  WHERE t2.GROUPID='" + groupID+ "' and ";
		    //update by jason 20091219 修改原因：采用包头库，表结构有变化
		    selectSQL += ",T_GPS_CARGROUP t2,t_dict_gpscarcatalog t3  WHERE t2.GROUPID='" + groupID+ "' and ";
		    selectSQL += " t1.CARCODE = t2.CARCODE and" ;
			selectSQL += " t3.CATAID='" + catalogID+ "' and" ;
			//selectSQL += " t1.CARCATALOG = t3.CATAID AND" + queryCond;
		    //update by jason 20091219 修改原因：采用包头库，表结构有变化
			selectSQL += " t1.CATALOG = t3.CATAID AND" + queryCond;		
		}
		else if(groupID && groupID.length > 0 && (!catalogID || catalogID.length <= 0)){
			selectSQL += ",T_GPS_CARGROUP t2  WHERE t2.GROUPID='" + groupID+ "' and ";
			selectSQL += " t1.CARCODE = t2.CARCODE AND"+ queryCond ;
		
		}
		else if(catalogID && catalogID.length > 0 && (!groupID || groupID.length <= 0)){
			//selectSQL += ",T_GPS_CARCATALOG t3  WHERE t3.CATAID='" + catalogID+ "' and ";
			//update by jason 20091219 修改原因：换表
			selectSQL += ",t_dict_gpscarcatalog t3  WHERE t3.CATAID='" + catalogID+ "' and ";
			//selectSQL += " t1.CARCATALOG = t3.CATAID AND " + queryCond;	
			selectSQL += " t1.CATALOG = t3.CATAID AND " + queryCond;	
		
		}
		else if(!groupID && groupID.length <= 0 && !catalogID && catalogID.length<= 0){
			if( queryCond && queryCond.length>0 )
				{
					selectSQL += " WHERE " + queryCond;
				}		
		}	
	selectSQL += " ORDER BY t1.departmentid ";
	pagingCarList = new DhtmlPaging(0,-1,this.perPageCount,selectSQL,$('gpscarPaging'),this.responseQuery);
	pagingCarList.setDBConfig("gps");
	pagingCarList.getPageData();
}; 	
 
/**
 * 展现查询得到GPS数据列表
 */ 
InstantGroupMap.prototype.responseQuery = function(xmlHttp)
{
 	var xmlDoc = xmlHttp.responseXML;
 	var strTable = "<table id=\"tabGPSCarList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0>";
 				
	//生成表头
	var heads  = ['车牌号码','呼叫号码','在 线','所属辖区'];
	var colWidths =[70,70,40,90];
	 
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
		 
		var carCode = rowResult[0].text;					
		 	
		//车牌号码
		var plateCode = rowResult[1].text;
		showCols.push(plateCode.length >0 ? plateCode: '&nbsp;');
		 
		//呼叫号码
		var telPhone = rowResult[2].text;
		showCols.push(telPhone.length >0 ? telPhone: '&nbsp;');
		
		//驾驶员
		var driver = rowResult[3].text;
		person = driver;
		showCols.push(driver.length >0 ? driver: '&nbsp;');
		
		//所属辖区
		var dept = rowResult[4].text;
		showCols.push(dept.length >0 ? dept: '&nbsp;');
		 
		//坐标					
		var coord = rowResult[5].text + "," + rowResult[6].text;
		
		//class=\"scrollRowThead\" 
	    strTable += "<tr class=\"rowstyle\" nowrap onclick=\"groupMap.setGpsCarInfo('"+  carCode + "','"+ (i+1) +"',"+ coord +");\">";	
	    strTable += "<td align=\"center\">";
	    strTable += "<input type=\"checkbox\" id=\"chk_sel_"+i+"\"  /> </td>";
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
};
 
/**
 * 获取当前选中GPS的数据
 */ 
InstantGroupMap.prototype.getSelectedCarCode = function()
{
	var tableObj = document.getElementById( this.CarListTableID );	
 	var selectCarCode = tableObj.selectCarCode ;
	return selectCarCode;
};


/**打开查看历史轨迹页面
 *
 */
InstantGroupMap.prototype.showTraceWindow = function()
{
	//取得选中的车辆
 	var selectCarCode = groupMap.getSelectedCarCode();
 	var regnumber = groupMap.number;
 	var driver = groupMap.person;		
	if(typeof selectCarCode == 'undefined' || null == selectCarCode || ""== selectCarCode )
	{
		alert('请选择要查看历史轨迹的车辆!');
		return;
	}
	var uri = "gpsTrace.jsp?carCode=" + selectCarCode + "&regnumber=" + regnumber + "&driver=" + driver;
	uri = encodeURI(uri)
	window.open(uri,'trace_gps','width=1000,height=620');
};

/**
 * 构建GPS车辆查询面板html字符串.
 */
InstantGroupMap.prototype.outputHTML = function()
{
	  htmlTxt = '\
	  <div id="queryGPScar" class="queryDiv" style="offsetTop:0px">\
	  <table class="top_table">\
		<tbody>\
			<tr>\
				<td align="right" nowrap>&nbsp;车牌号码:</td>\
				<td ><input type="text" id="txtregisternumberQ" style="width:100px" /></td>\
				<td nowrap align="right">&nbsp;车辆分类:</td>\
				<td id="txtClassifyQ" style="width:100px"></td>\
				<td></td>\
			</tr>\
			<tr>\
				<td>&nbsp;车辆分组:</td>\
				<td id="txtgrouptd" style="width:100px"></td>\
				<td align="right">&nbsp;所属辖区:</td>\
				<td colspan=2><input type="text" id="txtgpscarCounty" style="width:100px" readOnly/>\
					<img src="../../../sm/image/popup/btnselect.gif" style="cursor:hand;"  alt="选择机构"  onclick="raiseCountyChoice(\'txtgpscarCounty\')">\
					</td>\
			</tr>\
			<tr>\
				<td valign="top" colspan=5  align="center"> \
				<input id=\"btnSearch\" type=\"button\" value=\" 查 询 \"   onclick=\"groupMap.getGpscarQuery(\'gpscarQueryResult\')\" />\
					 &nbsp;&nbsp;\
				<input type="button"   id="btnZDWatch" value="重点监控" onClick="setZdCarId();">\
						 &nbsp;&nbsp;\
					<input type="button"    value="历史轨迹" onClick="groupMap.showTraceWindow();">\
					&nbsp;&nbsp;\
				</td> \
			</tr>\
		</tbody>\
	  </table>\
	</div>\
	<div id="gpscarPaging"  style="offsetTop:0px;"></div>\
	<div id="gpscarQueryResult" class="scrollDiv" style="text-align:center"></div>';
	 return htmlTxt;
};
 
 
 
 