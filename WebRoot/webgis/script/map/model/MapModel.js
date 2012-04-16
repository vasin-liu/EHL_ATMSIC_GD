MapModel = Class.create();
MapModel.prototype = {
    /**成员变量*/
	OvContainer: null,
    controls: new Object(),
    ovId: null,
    defaultCenterPoint: null,
    defaultLevel: null,
    overlays: null,
    traceIndex: 0,
    traces: new Object(),
    curIndex: -1,
    mapTypeIds: new Array(),
    /**
     * 初始化
     * @param {Object} id
     */
    initialize: function(id){
        this.modelId = id;
        this.mapTypes = new Object();
    },
    /**
     * 取得ZOOM
     */
    getZoom: function(){
        return this.zoom;
    },
    /**
     * 设置ZOOM
     * @param {Object} zoom
     */
    setZoom: function(zoom){
        this.zoom = zoom;
    },
    /**
     * 设置可视区域的中心坐标
     * @param {Object} centerCoord
     */
    setViewCenterCoord: function(centerCoord){
        this.viewCenterCoord = centerCoord;
    },
    /**
     * 取得可视区域的中心坐标
     */
    getViewCenterCoord: function(){
        return this.viewCenterCoord;
    },
    /**
     * 取得可视区域
     */
    getViewBound: function(){
        return this.viewBound;
    },
    /**
     * 设置可视区域
     * @param {Object} bound
     */
    setViewBound: function(bound){
        this.viewBound = bound;
    },
    /**
     * 设置当前的地图类型
     * @param {Object} type
     */
    setCurrentMapType: function(type){
        this.currentMapType = type;
    },
    /**
     * 取得当前的地图类型
     */
    getCurrentMapType: function(){
        return this.currentMapType;
    },
    /**
     * 取得ID
     */
    getId: function(){
        return this.modelId;
    },
    /**
     * 取得鹰眼地图容器
     */
    getOvContainer: function(){
        return this.OvContainer;
    },
    /**
     * 取得鹰眼地图DIV
     */
    getOvMapDiv: function(){
        return this.OvContainer.childNodes[0];
    },
    /**
     * 设置鹰眼地图容器
     * @param {Object} ovContainer
     * @param {Object} id
     */
    setOvContainer: function(ovContainer, id){
        this.OvContainer = ovContainer;
        this.ovId = id;
    },
    /**
     * 取得鹰眼地图MODEL
     */
    getOvModel: function(){
        var newModel = new MapModel(Util.createUniqueID());
        newModel.setViewCenterCoord(this.getViewCenterCoord());
		//MapModel_wwj        
		ovLevel = 1;
		//鹰眼的相对比例_wwj
        //if (this.getZoom().getLevel() - 2 <= 1) {
        //    ovLevel = 1;
		//}
        //else {
		//	ovLevel = this.getZoom().getLevel() - 3;
		//}
        var zoom = new Zoom(ovLevel);
        newModel.setZoom(zoom);
        newModel.setCurrentMapType(this.getCurrentMapType());
        newModel.setViewBound(zoom.getViewBound(this.OvContainer));
        return newModel;
    },
	/**
	 * 
	 * @param {Object} mapDiv
	 * @param {Object} elm
	 */    
    reset: function(mapDiv, elm){
        this.setViewCenterCoord(this.defaultCenterPoint.getCoord());
        this.setZoom(new Zoom(this.defaultLevel));
        this.controls[mapDiv.id].paint(this, true);
        this.controls[this.ovId].paint(this);
        elm.style.top = ((MaxZoomLevel - this.defaultLevel) * 12 + 6) + "px";
    },
    /**
     * 
     * @param {Object} mapDiv
     */
    clearOverLayers: function(mapDiv){
        if (this.overlays) {
            for (var i = 0; i < this.overlays.length; i++) {
                this.overlays[i].remove();
            }
            this.overlays.clear();
        }
    }
};