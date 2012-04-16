/**
 * 
 */
Marker = Class.create();
Marker.prototype = Object.extend(new Abstract.OverLayer(), {
    /**
     * 初始化
     */
	initialize: function(){
        this.id = Util.createUniqueID("Over_Marker_");
    },
    /**
     * 取得坐标
     */
    getCoord: function(){
        return this.coord;
    },
	/**
	 * 设置坐标
	 * @param {Object} point
	 */
    setCoord: function(point){
        this.coord = point.getCoord();
    },
    /**
     * 取得图标
     */
    getIcon: function(){
        return this.icon;
    },
	/**
	 * 设置图标
	 * @param {Object} icon
	 */
    setIcon: function(icon){
        this.icon = icon;
    },
    /**
     * 取得信息
     */
    getInfo: function(){
        return this.info;
    },
	/**
	 * 设置信息
	 * @param {Object} info
	 */
    setInfo: function(info){
        this.info = info;
    },
    /**
     * 图标
     */
    getShadowIcon: function(){
        return this.shadowIcon;
    },
	/**
	 * 
	 * @param {Object} sIcon
	 */
    setShadowIcon: function(sIcon){
        this.shadowIcon = sIcon;
    },
    /**
     * 设置到地图上
     * @param {Object} mapDiv
     * @param {Object} model
     * @param {Object} overLayDiv
     */
    setToMap: function(mapDiv, model, overLayDiv){
        this.mapDiv = mapDiv;
        this.model = model;
        this.sPoint = Util.getScreenPixel(this.coord, model.getZoom());
        var deltaX = this.sPoint.x - this.icon.width / 2;
        var deltaY = this.sPoint.y - this.icon.height;
        var markerDiv = $(this.id);
        var markerDiv = Util.createDiv(this.id, deltaX, deltaY, this.icon.width, this.icon.height, null, 'absolute');
        markerDiv.style.zIndex = 10;
        markerDiv.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + this.icon.src + ", sizingmethod=scale);";
        
        if (overLayDiv) {
            this.div = overLayDiv;
            overLayDiv.style.left = deltaX + 'px';
            overLayDiv.style.top = deltaY + 'px';
        }
        else {
            this.model.clearOverLayers();
            this.div = markerDiv;
            this.insert();
            this.div.style.cursor = "hand";
        }
        this.showInfoWindow();
        Event.observe(markerDiv, "click", this.reLoadInfo.bindAsEventListener(this));
    },
    /**
     * 重新加载信息
     * @param {Object} e
     */
    reLoadInfo: function(e){
        if (this.info && this.infoDiv) {
            this.infoDiv.style.display = "";
        }
    },
    /**
     * 显示信息窗口
     */
    showInfoWindow: function(){
        var newDeltaX = this.sPoint.x + this.icon.width - 8;
        var newDeltaY = this.sPoint.y - this.icon.height - 60;
        var mapDiv = $("map_" + this.model.getId());
        this.infoDiv = $("Over_Marker_Info");
        this.infoDiv.style.left = newDeltaX;
        this.infoDiv.style.top = newDeltaY;
        this.infoDiv.style.cursor = "default";
        this.infoDiv.style.display = "";
        $('Info').innerHTML = this.info;
        mapDiv.appendChild(this.infoDiv);
    }
});

/**
 * 图标类
 */
Icon = Class.create();
Icon.prototype = {
    /**
     * 初始化
     * @param {Object} w
     * @param {Object} h
     * @param {Object} src
     */
	initialize: function(w, h, src){
        this.width = w;
        this.height = h;
        this.src = src;
    }
};
/**
 * 隐藏图标窗口
 * @param {Object} e
 * @param {Object} windowId
 */
function hideInfoWindown(e, windowId){
    var infoWindow = $(windowId);
    infoWindow.style.display = "none";
    Event.stop(e);
}