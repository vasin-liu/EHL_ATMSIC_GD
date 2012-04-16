
	//GPS轨迹 
    
    var gpstracemap = new GPSTraceMap();    
	
	/**==============================BEGIN GPS轨迹配置=============================*/	
    gpstracemap.readPoints(carCode);
	
	/************************* GPS 面板  *****************************/
	var crpControl = new CRightPanelControl($('map'),ovmap, 280, 15, 400);
 
	crpControl.addItem(new CPanelItem("gpsTraceItem", "images/panel/gps.gif", "images/panel/gps_hover.gif", 95));
	
	crpControl.addContent(new CPanelContent("gpsTraceItem_content", "#77C7FC", gpstracemap.outputHTML()));
	
	map.addControl(crpControl);
 	
	/*****************   结束GPS面板配置   ****************/

    
    addTileChangeListener("gpsTraceEvent", "gpstracemap.reDrawLinesect()"); //showCarTracePoint
	/**==============================END GPS轨迹配置=============================*/	
	
	for(var i=0; i < crpControl.itemArr.length; i++)
	{
		var tmpId = crpControl.itemArr[i].item.id;
		if(tmpId == 'gpsTraceItem')
		{
			var obj = new Object();
			obj.target = new Object();
			obj.target.id = tmpId;
			crpControl.Item_click(obj);	
		}
	}
			