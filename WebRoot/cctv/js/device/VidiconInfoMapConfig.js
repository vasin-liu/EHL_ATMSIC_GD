	//添加摄像机标注工具 
    var vidiconmap = new VidiconInfoMap(); 
    var imgPath = '../../image/map/';

	/**==============================BEGIN内容面板配置=============================*/	
	var crpControl = new CRightPanelControl($('map'),ovmap, 330, 15, 400);
	
	crpControl.addItem(new CPanelItem("vidicon", imgPath + "panel/vidiconup.gif", imgPath + "panel/vidicondown.gif", 95));
	
	crpControl.addContent(new CPanelContent("vidicon_content", "white", vidiconmap.outputHTML()));
	map.addControl(crpControl);
	/**==============================END内容面板配置=============================*/	


	/**==============================BEGIN卡口配置=============================*/	

    //读取摄像机信息    
    vidiconmap.readVidiconPoints();
    addTileChangeListener("onMapScaleEvent", "vidiconmap.showAllVidiconPoint()");
 
	fillListBox("vidiconName","vidiconSelect","120","SELECT DISTINCT GLID,CPMC FROM ATMS_EQUIPMENT_ZB A,T_SYS_DEPARTMENT B WHERE  A.SSJG=B.JGID AND B.JGCCBM LIKE '"+jgccbm+"%' AND A.SSXT='CCTV' AND LONGITUDE IS NOT NULL AND LATITUDE IS NOT NULL","");
	/**==============================END卡口配置=============================*/	
		