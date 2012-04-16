	/**==============================BEGIN地图初始化配置=============================*/

    //定义全局变量
    var parameters = new Array();
       
    var toolInterval = 25;
                
    //从配置中获取到地图加载参数
    var params = "";
	var url = "webgis.GIS.GetTileLoadParams.d";
	new Ajax.Request(url, 
	{
		method: 'post',  
        parameters: params,
		asynchronous: false,
		
		//读取完成后，放入内存数组
		onComplete: function(xmlHttp)
        {
         	var strReturn = xmlHttp.responseText;
         	   
         	//判断服务器输出的流是否为空
			if (strReturn == "")
			{
				alert("未获取到地图配置参数!");
				return;
			}			
							
			//判断返回的结果内容
			var arrResult= strReturn.split(",");
			for (var i = 0; i < arrResult.length; i++){
			    var arrTemp = arrResult[i].split("=");
			    arrTemp[0] = arrTemp[0].trim();
			    arrTemp[1] = arrTemp[1].trim();
			    parameters[i] = arrTemp;
			}			  				
        }
	});	     
	
	//获取地图参数函数.
	function getMapParameter(key){
		var resultValue = "";
		
		for (var i = 0; i < parameters.length; i++){
			if (parameters[i][0] == key){
				resultValue = parameters[i][1];
				break;
			}
		}
		
		return resultValue;
		
	}
	
	//是否使用地图检索功能
	var usingsearchpanel = getMapParameter("usingsearchpanel");

	//空间数据源名称
	var sdedatasource = getMapParameter("sdedatasource");
	
	//用户所属单位
	var userDepartment = getMapParameter("userDepartment");
	
	//WebGIS系统图片存放路径
    var ImageBaseDir = getMapParameter("ImageBaseDir"); 
	
	//定义切片大小
    var TileSize = getMapParameter("gistilesize");
	
	//定义最大缩放级别
    var MaxZoomLevel = getMapParameter("gismaxzoomlevel");
 	      		
	//定义地图边界（MINX, MAXX, MINY, MAXY）
    var FullExtent = new Bound(Number(getMapParameter("gisminx"))*1e16, Number(getMapParameter("gismaxx"))*1e16, Number(getMapParameter("gisminy"))*1e16, Number(getMapParameter("gismaxy"))*1e16);
    var Width = Util.distanceByLnglat(FullExtent.getMinX()/1e16,FullExtent.getMaxY()/1e16,FullExtent.getMaxX()/1e16,FullExtent.getMaxY()/1e16);

	var mapContainer = $('map');
	mapContainer.style.width  = mapContainer.style.pixelWidth;
	mapContainer.style.height = mapContainer.style.pixelHeight;
	
	//创建地图实例    
    var map = new Map($('map'));
	 	
    var mt = new SZMapType(getMapParameter("gismappath"),'map1.gif','map2.gif', 1, 1);	
	map.addMapType(mt, true);
	
    var FirstZoomTileRows = mt.firstRows;
    var FirstZoomTileCols = mt.firstCols;    

	map.setDefaultCenter(new Point(Number(getMapParameter("giscenterx")), Number(getMapParameter("giscentery"))), Number(getMapParameter("gisdefaultlevel")));	       
		 	
	/**==============================END地图初始化配置===============================*/