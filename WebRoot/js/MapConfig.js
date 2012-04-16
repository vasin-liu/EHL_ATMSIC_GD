	/**==============================BEGIN地图初始化配置=============================*/
    //Global constants    
    var ScaleParameter = 2;
    var TileSize = 512;
    var MaxZoomLevel = 6;
    var DefaultLevel = 2;
    var ImageBaseDir = "images/"; 
    var toolInterval = 25;
    var MapDir = "";
    
    var MapMinX = 0.0;
    var MapMinY = 0.0;
    var MapMaxX = 0.0;
    var MapMaxY = 0.0;
    var MapCenterX = 0.0;
    var MapCenterY = 0.0;
    
	var gisScaleLevel_1 =  0;
	var gisScaleLevel_2 =  0;
	var gisScaleLevel_3 =  0;
	var gisScaleLevel_4 =  0;
	var gisScaleLevel_5 =  0;
	var gisScaleLevel_6 =  0;
	var gisScaleLevel_7 =  0;
	var gisScaleLevel_8 =  0;
	var gisScaleLevel_9 =  0;
    
    //动态要素最小显示级别
    var featureLevel = 1;
    
    //鹰眼的宽高设置
    var gisAngleWidth = 200;
    var gisAngleHeight = 200;
    
    var gpsInterval = 0;
    //从配置中获取到地图切片图片加载参数
	url = 'webgis.GIS.GetTileLoadParams.d';
	new Ajax.Request(url, 
	{
			method: 'post',  
			asynchronous: false,
			
			//读取完成后，放入内存数组
			onComplete: function(xmlHttp)
            {
          	    var strReturn = xmlHttp.responseText;
          	   
          	    //判断服务器输出的流是否为空
				if (strReturn == null || strReturn == "null" || strReturn == "")
				{
					alert("未获取地图配置参数!");
					return;
				}			
				
				//判断结果是否出现错误
				var strPrefix = strReturn.substring(0,3);
				if((strPrefix != "MS-") && (strPrefix != "RS-"))
				{
					alert("从服务器端返回地图配置参数失败，\r\n请确认服务器是否有响应!");
					return;
				}
				if(strPrefix == "MS-")
				{
					alert("无法读取地图配置参数，返回错误内容如下：" + strReturn.substring(3));
					return;			
				}
				
				//判断返回的结果内容
				var strResultValue = strReturn.substring(3).split("|");
				ScaleParameter = Number(strResultValue[0]);
    			TileSize = Number(strResultValue[1]);
  				MaxZoomLevel = Number(strResultValue[2]);
  				DefaultLevel = Number(strResultValue[3]);
  				
  				MapMaxX = Number(strResultValue[4]); 
   				MapMaxY = Number(strResultValue[5]);   
    			MapMinX = Number(strResultValue[6]);
    			MapMinY = Number(strResultValue[7]);
    			MapCenterX = Number(strResultValue[8]);
    			MapCenterY = Number(strResultValue[9]);	      			
  				MapDir = strResultValue[10];  
  				
  				//切图比例
  				gisScaleLevel_1 =  Number(strResultValue[11]);
  				gisScaleLevel_2 =  Number(strResultValue[12]);
  				gisScaleLevel_3 =  Number(strResultValue[13]);
  				gisScaleLevel_4 =  Number(strResultValue[14]);
  				gisScaleLevel_5 =  Number(strResultValue[15]);
  				gisScaleLevel_6 =  Number(strResultValue[16]);
  				gisScaleLevel_7 =  Number(strResultValue[17]);
  				gisScaleLevel_8 =  Number(strResultValue[18]);
  				gisScaleLevel_9 =  Number(strResultValue[19]);

  				featureLevel =  Number(strResultValue[20]);
  				
  				gisAngleWidth = Number(strResultValue[21]);
  				gisAngleHeight = Number(strResultValue[22]);
  				
  				gpsInterval = Number(strResultValue[23]);
           }
	});	     
	
	//MINX, MAXX, MINY, MAXY
    var FullExtent = new Bound(MapMinX*1e16, MapMaxX*1e16, MapMinY*1e16, MapMaxY*1e16);
    var Width = Util.distanceByLnglat(FullExtent.getMinX()/1e16,FullExtent.getMaxY()/1e16,FullExtent.getMaxX()/1e16,FullExtent.getMaxY()/1e16);
  
	
	var mapContainer = $('map');
	mapContainer.style.width  = mapContainer.style.pixelWidth;
	mapContainer.style.height = mapContainer.style.pixelHeight;
	
    //Create toolbar    
    var toolbar = new ToolBarControl($('map'));
    toolbar.addTool(new ZoominTool('zoomin','zoomin1.gif','zoomin2.gif','zoomin3.gif','absolute',1+toolInterval*1,35,28,25))
    toolbar.addTool(new ZoomoutTool('zoomout','zoomout1.gif','zoomout2.gif','zoomout3.gif','absolute',1+toolInterval*2,35,28,25))
    toolbar.addTool(new PanTool('pan','pan1.gif','pan2.gif','pan3.gif','absolute',1+toolInterval*3,35,28,25), true)
    toolbar.addTool(new MeasureTool('measure','measure1.gif','measure2.gif','measure3.gif','absolute',1+toolInterval*4,35,28,25))

	toolbar.addCommand(new ClearCmd('clear','clear1.gif','clear2.gif','clear3.gif','absolute',1+toolInterval*5,35,28,25))
    toolbar.addCommand(new FullCmd('full','full1.gif','full2.gif','full3.gif','absolute',1+toolInterval*6,35,28,25))
    toolbar.addCommand(new PrevCmd('back','back1.gif','back2.gif','back3.gif','absolute',1+toolInterval*7,35,28,25))
    toolbar.addCommand(new NextCmd('forward','forward1.gif','forward2.gif','forward3.gif','absolute',1+toolInterval*8,35,28,25))
 
    var map = new Map($('map'));
	
	var ovmap = new OvMap($('map'));
 	
    var mt = new SZMapType(MapDir,'map1.gif','map2.gif', 1, 1);	
	map.addMapType(mt, true);
	
    var FirstZoomTileRows = mt.firstRows;
    var FirstZoomTileCols = mt.firstCols;    

	map.setDefaultCenter(new Point(MapCenterX, MapCenterY), DefaultLevel);	       
	map.addControl(toolbar, true);	
    map.addControl(ovmap);
    map.addControl(new NavControl($('map')));

	/**==============================END地图初始化配置=============================*/