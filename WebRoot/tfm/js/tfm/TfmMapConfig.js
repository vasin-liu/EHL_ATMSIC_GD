
    var tfmmap = new TfmMap(); 

	/**==============================BEGIN路段配置===========================*/	
    //读取实时路段信息
    tfmmap.refreshList();
    tfmmap.readRoadFlow();
    addTileChangeListener("onMapScaleEvent", "tfmmap.redrawFlow('yes')");
    
	/**==============================END路段配置=============================*/	