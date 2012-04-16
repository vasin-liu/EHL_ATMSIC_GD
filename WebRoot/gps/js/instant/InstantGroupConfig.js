
	//建立GPS车辆地图数据控制对象
    var groupMap = new InstantGroupMap();    
    groupMap.readGPSPoints();
    groupMap.refreshPoints();
	function panelIframe()
	{
		var htmlBuffer = '<iframe src=\"gpsClassify.jsp\" style="width:100%;height:100%;background-color:#77C7FC;scroll-x:no"></iframe';
		return htmlBuffer;
	}
	
	/************************* GPS 面板  *****************************/
// 	if('0' == classify )
// 	{
		var crpControl = new CRightPanelControl($('map'),ovmap, 400, 15, 400);
		
 			//crpControl = new CRightPanelControl($('map'),ovmap, 400, 15, 400);
		crpControl.addItem(new CPanelItem("carGpsItem2", "../../images/panel/gps.gif", "../../images/panel/gps_hover.gif", 95));
		crpControl.addContent(new CPanelContent("carGpsItem_content2", "white", groupMap.outputHTML()));
		map.addControl(crpControl);
//	}
//	else
//	{ 
//		crpControl = new CRightPanelControl($('map'),ovmap, 400, 15, 400);
//		crpControl.addItem(new CPanelItem("carGpsItem", "../../images/panel/gps.gif", "../../images/panel/gps_hover.gif", 95));
//		crpControl.addContent(new CPanelContent("carGpsItem_content", "white", gpsmap.outputHTML()));
//		map.addControl(crpControl);
////		crpControl = new CRightPanelControl($('map'),ovmap, 300, 15, 400);
////		crpControl.addItem(new CPanelItem("carGpsItem", "../../images/panel/cl.gif", "../../images/panel/cl_hover.gif", 95));
////		crpControl.addContent(new CPanelContent("carGpsItem_content", "white", panelIframe()));
////		map.addControl(crpControl);
//		
//	
//	}
	addTileChangeListener("onMapScaleEvent", "gpsmap.showAllCarPoint()");
	/*****************   结束GPS面板配置   ****************/
	//重点车辆监控.
    var zdCarId = null;
    function setZdCarId()
    {
    	var zdButton = document.getElementById("btnZDWatch");
   
    	var jkID = zdButton.jkID;
    	if(jkID && jkID.length>0)
    	{
    		zdButton.jkID = null;
    		zdCarId = null;
    		zdButton.value = "重点监控"; 
    	}
    	else
    	{
    		zdCarId = gpsmap.getSelectedCarCode();
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
fillListBox("txtgrouptd","group","100","SELECT distinct(groupid),groupname FROM t_gps_groupinfo order by groupid","","");	  
//fillListBox("txtClassifyQ","catalog","100","SELECT distinct(cataid),cataname FROM t_gps_carcatalog order by cataid","","");	  
fillListBox("txtClassifyQ","catalog","100","SELECT distinct(cataid),cataname FROM t_dict_gpscarcatalog order by cataid","","");	  