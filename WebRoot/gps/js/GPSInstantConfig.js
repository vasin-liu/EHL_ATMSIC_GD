
	//建立GPS车辆地图数据控制对象
    var gpsmap = new GPSInstantMap();    
     
    gpsmap.showGPS();
	/**==============================BEGIN配置=============================*/	
  
    //重点车辆监控.
    var zdCarId = null;
    function setZdCarId()
    {
    	var zdButton = document.getElementById("btnZDWatch");
    	//zdCarId = gpsmap.tid;
    	var jkID = zdButton.jkID;
    	if(jkID && jkID.length>0)
    	{
    		zdButton.jkID = null;
    		zdCarId = null;
    		zdButton.value = "重点监控"; 
    	}
    	else
    	{
    		zdCarId = gpsmap.tid;
    		if(zdCarId && zdCarId.length>0)
    		{
    			zdButton.jkID = zdCarId;
				zdButton.value = "取消监控";
			}
			else
			{
				alert("请选择GPS车辆.");
			}	 
    	}
    } 
	/**==============================END配置=============================*/	
	function panelIframe()
	{
		var htmlBuffer = '<iframe src=\"gpsClassify.jsp\" style="width:100%;height:100%;background-color:#77C7FC;scroll-x:no"></iframe';
		return htmlBuffer;
	}
	
	
	
	/************************* GPS 面板  *****************************/
	var crpControl = null;
  
 	if('0' == classify )
 	{
 		crpControl = new CRightPanelControl($('map'),ovmap, 400, 15, 400);
		crpControl.addItem(new CPanelItem("carGpsItem", "images/panel/gps.gif", "images/panel/gps_hover.gif", 95));
		crpControl.addContent(new CPanelContent("carGpsItem_content", "#77C7FC", gpsmap.outputHTML()));
		map.addControl(crpControl);
	}
	else
	{ 
		crpControl = new CRightPanelControl($('map'),ovmap, 300, 15, 400);
		crpControl.addItem(new CPanelItem("carGpsItem", "images/panel/cl.gif", "images/panel/cl_hover.gif", 95));
		crpControl.addContent(new CPanelContent("carGpsItem_content", "#77C7FC", panelIframe()));
		map.addControl(crpControl);
		
	
	}
	/*****************   结束GPS面板配置   ****************/
	
	addTileChangeListener("onMapScaleEvent", "gpsmap.showAllCarPoint()");
	  
function highwaySetting()
{
	var currentZoom =  map.getCurrentZoom();
	var coord = "113.688015,22.839029";
	var infoCoord = new Coordinate( 113.680318 *1e16 , 22.888289 * 1e16);
			
	var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
	var left = scoord.x;
	var top  = scoord.y;
	
	//设置居中位置
	map.moveToCenter(infoCoord,6);
}
fillListBox("txtgrouptd","group","100","SELECT distinct(groupid),groupname FROM t_gps_groupinfo order by groupid","","");   

	  
	  
	  
	  
	  
	  
	  