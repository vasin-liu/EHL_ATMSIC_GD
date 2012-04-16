	//添加卡口标注工具 
    var dynamicInfomap = new DynamicInfoMap(); 
    var imgPath = '../../image/map/';

 	          
	/**==============================BEGIN内容面板配置=============================*/	
	var crpControl = new CRightPanelControl($('map'),ovmap, 350, 15, 400);
	//var cbpControl = new CBPanelControl($('map'), 150, ovmap, crpControl);
	
	//crpControl.setBottomControlPanel(cbpControl);
	crpControl.addItem(new CPanelItem("tgs", imgPath + "panel/deviceup.gif", imgPath + "panel/devicedown.gif", 95));
	
	
	crpControl.addContent(new CPanelContent("tgs_content", "white", dynamicInfomap.outputHTML()));
	
	map.addControl(crpControl);
	//crpControl.doExpand();//展开右侧面板
	//map.addControl(cbpControl);
	/**==============================END内容面板配置=============================*/	


	/**==============================BEGIN卡口配置=============================*/	
    //读取卡口信息
   
    
     addTileChangeListener("onMapScaleEvent", "dynamicInfomap.showAllDynamicInfoPoint()");
     
     //alert(document.getElementById("queryCond").);
	
	/**==============================END卡口配置=============================*/	
		