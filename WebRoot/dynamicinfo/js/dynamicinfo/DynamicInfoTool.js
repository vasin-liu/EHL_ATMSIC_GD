/**
 *函数功能：标注设备工具类.
 */
EquipTool = Class.create(); 
EquipTool.prototype = Object.extend(new Abstract.Tool(), {
	cursorStyle: 'point',
	selected: false,
	alt: '标注设备',
		
	clickHandler: function(e, toolbar){
		this.mapDiv = toolbar.mapDiv;
		var infoCoord = Util.getMouseRelativePixel(e, this.mapDiv);
		var zoom = toolbar.model.getZoom();
		if(equipmap.getEquipID() == "" )
		{
			 alert("请选择要标注的设备!");
			 Event.stop(e);
			 return;
		}
		
		equipmap.writeEquipPoint(toolbar.model.getId(),infoCoord, zoom);
		
		Event.stop(e);	
	}
});