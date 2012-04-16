/**
 * 地图控件
 */
MapControl = Class.create();
MapControl.prototype = Object.extend(new Abstract.Control(), {
    /**
     * 初始化
     * @param {Object} id
     * @param {Object} container
     */
	initialize: function(id, container){
        this.id = id;
        this.mapDiv = Util.createDiv(id);
        this.mapDiv.style.position = "absolute";
        this.mapDiv.style.zIndex = 0;
        this.mapDiv.style.cursor = "move";
		
        this.vmlDiv = Util.createDiv("vmlDiv");
        this.vmlDiv.style.position = "absolute";
        this.vmlDiv.style.zIndex = 0;

		this.container = container;
        this.container.style.border = "1px solid #666666";
        this.container.style.overflow = "hidden";
        this.container.style.position = "relative";
        
        this.container.appendChild(this.mapDiv);
        this.container.appendChild(this.vmlDiv);
    },
	//画    
    paint: function(model, isTracing){
        var curZoom = model.getZoom();
        var viewBound = curZoom.getViewBound(this.container).clone(model.getViewCenterCoord());
        var mapBound = curZoom.realMapBound;
        
		var deltaX = (mapBound.getMinX() - viewBound.getMinX()) * (curZoom.getTileCols() * TileSize / mapBound.getWidth());
        var deltaY = (viewBound.getMaxY() - mapBound.getMaxY()) * (curZoom.getTileRows() * TileSize / mapBound.getHeight());

        this.mapDiv.style.left = deltaX + "px";
        this.mapDiv.style.top = deltaY + "px";
        this.mapDiv.style.width = (curZoom.getTileCols() * TileSize) + "px";
        this.mapDiv.style.height = (curZoom.getTileRows() * TileSize) + "px";
                
        this.vmlDiv.style.left = deltaX + "px";
        this.vmlDiv.style.top = deltaY + "px";
        this.vmlDiv.style.width = (curZoom.getTileCols() * TileSize) + "px";
        this.vmlDiv.style.height = (curZoom.getTileRows() * TileSize) + "px";

        
        this.loadTiles(model, this.container, this.mapDiv, isTracing, true);
    },
    /**
     * add by wwj
     */
    getMapDiv: function(){
    	return this.mapDiv;
    },
    getVMLDiv: function(){
    	return this.vmlDiv;
    }
});