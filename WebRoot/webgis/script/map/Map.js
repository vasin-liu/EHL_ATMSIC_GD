/**
 * 定义一个地图类.
 */
Map = Class.create();
Map.prototype = {
    mapTypes: new Object(),
    currentMapType: null,
    /**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container){
        this.container = container;
        this.container.innerHTML = "";
        this.container.style.backgroundColor = 'white';
        this.mapId = Util.createUniqueID();
        this.containerWidth  = Util.getValueOfNoPX(this.container.style.width);
   	    this.containerHeight = Util.getValueOfNoPX(this.container.style.height);

		this.model = new MapModel(this.mapId);
        this.mapControl = new MapControl("map_" + this.mapId, this.container);
        this.model.controls[this.mapControl.id] = this.mapControl;
        var scale = new ScaleControl(container);
        this.model.controls[scale.id] = scale;
        this.searchWindow = null;
        this.bindMouseWheel = this.map_mousewheel.bindAsEventListener(this);
    },
    /**
     * 取得容器的宽度.
     */
    getMapWidth: function (){
    	return this.containerWidth;
    },
    /**
     * 取得容器的高度.
     */
    getMapHeight: function(){
    	return this.containerHeight;
    },
    getMapDiv: function(){
        return this.mapControl.getMapDiv();
    },
    
    getVMLDiv: function(){
    	return this.mapControl.getVMLDiv();
    },
    
    getCurrentZoom:function()
    {
    	return this.model.getZoom();
    },
    
    /**
     * 增加返回Model方法
     */
    getMapModel : function()
    {
    	return this.model;
    },
    
	/**
	 * 取得依附的容器
	 */    
    getContainer: function(){
        return this.container;
    },
    /**
     * 设置中心点
     * @param {Object} centerPoint
     * @param {Object} level
      */
    setDefaultCenter: function(centerPoint, level){
        this.model.defaultCenterPoint = centerPoint;
        this.model.defaultLevel = level;
        this.model.setViewCenterCoord(centerPoint.getCoord());
        this.model.setZoom(new Zoom(level));
		
        this.mapControl.paint(this.model, true);
        this.level = level;
        Event.observe(this.mapControl.mapDiv, "mousemove", this.map_mousemove.bindAsEventListener(this));
	    //Event.observe(this.mapControl.mapDiv, "mousewheel", this.map_mousewheel.bindAsEventListener(this));
    },

    /**
     * 绑定鼠标滚轮事件.
     * @param {Object} flag (true-绑定;false-取消绑定)
     */
    bindMouseWheelEvent: function(flag){
        if (flag){   
 	        Event.observe(this.mapControl.mapDiv, "mousewheel", this.bindMouseWheel);
        }else{
 	        Event.stopObserving(this.mapControl.mapDiv, "mousewheel", this.bindMouseWheel);
        }
    },
    
    /**
     * 设置特殊的中心点.
     * @param {Object} centerPoint
     * @param {Object} level
     */
    setSpecialCenter: function(centerPoint, level){
        this.model.setZoom(new Zoom(level));
		
        this.mapControl.paint(this.model, true);
        this.level = level;
        this.moveToCenter(centerPoint.getCoord());
    },
        
    /**
     * 移动中心点
     * @author wwj（linbh 2008.10.30 增加level参数 fengd 2009.5.20 增加level判断）
     * @param {Object} nowCenterCoord 中心点经纬度坐标
     * @param {Object} level 缩放级数
     */
	moveToCenter: function(nowCenterCoord, level){
        var orgCenterCoord = this.model.getViewCenterCoord();
        var curZoom = this.model.getZoom();

        var newCenterCoord = new Coordinate(nowCenterCoord.x, nowCenterCoord.y);
        if(!newCenterCoord.isSame(orgCenterCoord)){
			this.model.setViewCenterCoord(newCenterCoord);
		}
		
		if (level){
	        var orgLevel = curZoom.getLevel();
	        level = Number(level);
	        if(level != orgLevel){
	        	if(level > MaxZoomLevel)
	        	{
	        		level = MaxZoomLevel;
	        	}
		        this.model.setZoom(new Zoom(level));
		        if(typeof(navControl) != "undefined")
		        {
		       		navControl.paint(this.model);       
		       	}
			}
		}
        this.mapControl.paint(this.model, true);
        if(typeof(navControl) != "undefined")
        {
			this.model.controls[this.model.ovId].paint(this.model);
		}
    },
    
    /**
     * 获取中心点坐标
     * @author linbh
     * @param {Object} points 点集数组（经纬度之间以逗号分隔）
     */
    getCenterCoord: function(points){
        var minX = 180e16, maxX = 0, minY = 90e16, maxY = 0;        
        for (var i = 0; i < points.length; i++) {
            var coord = points[i].split(",");            
		    var point = new Point(coord[0], coord[1]);
            if (point.getCoord().x < minX){
				minX = point.getCoord().x;
			} 
            if (point.getCoord().x > maxX) {
				maxX = point.getCoord().x;
			}
            if (point.getCoord().y < minY) {
				minY = point.getCoord().y;
			}
            if (point.getCoord().y > maxY) {
				maxY = point.getCoord().y;
			}
        }
        
    	var bound = new Bound(minX, maxX, minY, maxY);

        return bound.getCenterCoord();
    },   
 
    /**
     * 鼠标滚轮
     * @param {Object} e
     */
    map_mousewheel: function(e){
        var level = this.model.getZoom().getLevel();
        if (window.event.wheelDelta == 120 && level < MaxZoomLevel) {
            level += 1;
            this.model.setZoom(new Zoom(level));
            this.mapControl.paint(this.model, true);
        }
        else {
            if (window.event.wheelDelta == -120 && level > 1) {
                level -= 1;
                this.model.setZoom(new Zoom(level));
                this.mapControl.paint(this.model, true);
            }
        }
 
        $('sliderbar_' + this.model.getId()).parentNode.style.top = ((MaxZoomLevel - level) * 12 + 6) + "px";
        this.model.controls[this.model.ovId].paint(this.model);
    },

    /**
     * 鼠标移动
     * @param {Object} e
     */
	map_mousemove: function(e){
        var nowMouseDownPixel = Util.getMouseRelativePixel(e, this.mapControl.getMapDiv());     
        var coord = Util.getCoordinateByPixel(nowMouseDownPixel, map.getCurrentZoom());
        coord.x = coord.x /1e16;
        coord.y = coord.y/1e16;
     	window.status = "当前位置:(经度:"+coord.x+",纬度:"+coord.y+")";
	},
    
    /**
     * 增加MapType
     * @param {Object} type
     * @param {Object} isCurrent
     */
    addMapType: function(type, isCurrent){
        if (isCurrent) {
            this.model.setCurrentMapType(type);
        }
        
        this.model.mapTypeIds.push(type.typeId);
        this.model.mapTypes[type.typeId] = type;
        type.paint(this.model, $('map'));
    },
    /**
     * 
     * @param {Object} layer
     */
    addOverLayer: function(layer){
    },
    /**
     * 
     * @param {Object} control
     */
    addControl: function(control, bToolbar){
        control.paint(this.model);
        this.model.controls[control.id] = control;
        
		if (bToolbar == true){
			control.setMapModel(this.model);
			control.registerEventToMap(this.mapControl.mapDiv);
		}        
    },
    /**
     * 加载工具条
     * @param {Object} imgPath
     */
    addToolBar: function(imgPath){
    	var toolbar = new ToolBarControl($('map'));
    	toolbar.addDefaultToolBar(imgPath);
    	this.addControl(toolbar,true);
    	return toolbar;
    },
    
    /**
     * 加载鹰眼
     */
    addEagle: function(){
	    ovmap = new OvMap($('map'));
	    this.addControl(ovmap);
	    return ovmap;
    },

    /**
     * 加载地图导航
     */
    addNavigation: function(){
	    navControl = new NavControl($('map'));
	    this.addControl(navControl);
	    return navControl;
    },
    
    /**
     * 取得当前地图的显示级别.
     */
    getCurrLevel: function(){
    	return this.model.getZoom().getLevel();
    },
    /**
	 * 重新画
	 */
	repaint: function(){
		var mapContainer = $('map');
		mapContainer.style.width  = mapContainer.style.pixelWidth;
		mapContainer.style.height = mapContainer.style.pixelHeight;	

        this.container = mapContainer;
        this.container.innerHTML = "";
        this.container.style.backgroundColor = 'white';
        this.mapId = Util.createUniqueID();

        this.containerWidth  = Util.getValueOfNoPX(this.container.style.width);
   	    this.containerHeight = Util.getValueOfNoPX(this.container.style.height);

		this.model = new MapModel(this.mapId);
        this.mapControl = new MapControl("map_" + this.mapId, this.container);
        this.model.controls[this.mapControl.id] = this.mapControl;
        var scale = new ScaleControl(this.container);
        this.model.controls[scale.id] = scale;
    }    
};