/***********************************************************************

版 权：北京易华录信息技术股份有限公司 2009

文件名称：videoInspectCnfg.js
摘 要：	视频监控地图配置

当前版本：1.0
作 者：LChQ  2009-4-14 

***************************************************************************/

var videoMapObject = new VideoInspectMap();
videoMapObject.readVideoPointList();

/**==============================BEGIN内容面板配置=============================*/	

	var crpControl = new CRightPanelControl($('map'),ovmap, 450, 15, 400);
//	var cbpControl = new CBPanelControl($('map'), 150, ovmap, crpControl);
	
//	crpControl.setBottomControlPanel(cbpControl);
	
	crpControl.addItem(new CPanelItem("CPVideoInspect", "../../images/panel/sbd.gif", "../../images/panel/sbd_hover.gif", 95));
	
	crpControl.addContent(new CPanelContent("CPVideoInspect_content", "#77C7FC", videoMapObject.outputHTML()));

	map.addControl(crpControl);
//	map.addControl(cbpControl);
	
/**==============================END内容面板配置=============================*/	

addTileChangeListener("onMapScaleEvent", "videoMapObject.showVideoInfo()");


 


