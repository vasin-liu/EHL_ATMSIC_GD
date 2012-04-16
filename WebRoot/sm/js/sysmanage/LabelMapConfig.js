	var labelMap = new LabelMap(); 
      
	/**==============================BEGIN内容面板配置=============================*/	
	var crpControl = new CRightPanelControl($('map'),ovmap, 300, 15, 400);
		
	crpControl.addItem(new CPanelItem("label", "../../image/webgis/panel1.gif", "../../image/webgis/panel2.gif", 95));
	crpControl.addContent(new CPanelContent("label_content", "#EFEFEF", labelMap.outputHTML()));
	map.addControl(crpControl);
	/**==============================END内容面板配置=============================*/	

    //增加了施工占道标注工具
	labelMap.getLabels();
	//addTileChangeListener("onMapScaleEvent", "lcMap.onScaleRepaint()");