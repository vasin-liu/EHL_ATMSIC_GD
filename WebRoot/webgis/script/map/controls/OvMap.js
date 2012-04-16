/**
 * 地图预览
 */
OvMap = Class.create();
OvMap.prototype = Object.extend(new Abstract.Control(), {
    /**
     * 初始化
     * @param {Object} container
     */
	initialize: function(container){
        this.container = container;
		this.deltaXAixs = 0;
		this.deltaYAixs = 0;
    	this.extX = 22;
	    this.drawOvContainer(container);
    },
    
    paint: function(model){
        this.model = model;
        this.model.setOvContainer(this.ovMapDiv, this.id);
        this.ovModel = this.model.getOvModel();
        var curZoom = this.ovModel.getZoom();
        var viewBound = curZoom.getViewBound(this.ovMapDiv).clone(model.getViewCenterCoord());
        var mapBound = curZoom.realMapBound;
        var deltaX = (mapBound.getMinX() - viewBound.getMinX()) * (curZoom.getTileCols() * TileSize / mapBound.getWidth());
        var deltaY = (viewBound.getMaxY() - mapBound.getMaxY()) * (curZoom.getTileRows() * TileSize / mapBound.getHeight());
        this.ovDiv.style.left = deltaX + "px";
        this.ovDiv.style.top = deltaY + "px";
        this.ovDiv.style.width = (curZoom.getTileCols() * TileSize) + "px";
        this.ovDiv.style.height = (curZoom.getTileRows() * TileSize) + "px";
        
        this.loadTiles(this.ovModel, this.ovMapDiv, this.ovDiv, false, false);
        this.container.appendChild(this.ovMapDiv);
    },
    
    drawOvContainer: function(container){
        var containerWidth = Util.getValueOfNoPX(container.style.width);
        var containerHeight = Util.getValueOfNoPX(container.style.height);
        //modify by wwj
        var width = Number(getMapParameter("gisAngleWidth"));//Util.getValueOfNoPX(container.style.width) / 5;
        var height = Number(getMapParameter("gisAngleHeight"));//Util.getValueOfNoPX(container.style.height) / 5;
        var x = width / 2 - width / 4 / 2;
        var y = height / 2 - height / 4 / 2;
        
        var left = containerWidth - width;
        var top = containerHeight - height;
        
		if (Util.isIE()){
			left = left + 1;
			top = top + 1;
		}
		else{
			left = left - 5;
			top = top - 5;
		}
		
        this.id = Util.createUniqueID('Ov_');
        this.ovMapDiv = Util.createDiv(this.id, left, top, width, height, null, 'absolute', '2px ridge blue');
        this.ovMapDiv.style.overflow = 'hidden';
        this.ovMapDiv.style.backgroundColor = 'white';
        this.ovDiv = Util.createDiv(Util.createUniqueID('Ov_Map_'));
        this.ovDiv.style.position = "relative";
        this.ovDiv.style.zIndex = 0;
        this.ovMapDiv.appendChild(this.ovDiv);
        this.registerEvent(this.ovDiv, 'ov_', 'mousedown,mousemove,mouseup,click');

        this.rectDiv = Util.createDiv(Util.createUniqueID('rect_'), x, y, width / 4, height / 4, null, "absolute", "2px solid blue");
        this.rectDiv.style.backgroundColor = "white";
        this.rectDiv.style.filter = "alpha(opacity=40)";
        this.rectDiv.style.opacity = "0.40";
        this.rectDiv.style.fontSize = "1px"
        this.rectDiv.style.zIndex = 10000;
        this.rectDiv.style.cursor = "move";
        this.ovMapDiv.appendChild(this.rectDiv);
        this.registerEvent(this.rectDiv, 'rect_', 'mousedown,mousemove,mouseup');
        
        this.panDiv = Util.createDiv(Util.createUniqueID('Ov_Pan_'), null, null, null, null, ImageBaseDir + 'panel/arrowright1.gif', 'absolute');
        this.panDiv.style.zIndex = 100000;
        this.panDiv.style.left = containerWidth - 21;
        this.panDiv.style.top = containerHeight - 21;
        this.panDiv.style.cursor = "hand";
        this.container.appendChild(this.panDiv);
        this.registerEvent(this.panDiv, 'pan_', 'click');
    },
	/**
	 * X轴方向的偏移量.
	 * @param {Object} deltaX
	 */
	moveX: function(deltaX, direct){
		this.deltaXAixs = deltaX;
		if (Util.isIE()){
			if (direct == "left"){
				deltaX = deltaX + this.extX;
			}
			else if (direct == "right"){
				deltaX = deltaX - this.extX;
			}	
		}
		
		this.ovMapDiv.style.left = Util.getValueOfNoPX(this.ovMapDiv.style.left) + deltaX;
		this.panDiv.style.left = Util.getValueOfNoPX(this.panDiv.style.left) + deltaX;		
	},
	/**
	 * Y轴方向的偏移量.
	 * @param {Object} deltaY
	 */
	moveY: function(deltaY, direct){
		this.deltaYAixs = deltaY;

		if (direct == "up"){
			deltaY = -deltaY;
		}
		else if (direct == "down"){
			deltaY = deltaY;
		}			

		
		this.ovMapDiv.style.top = Util.getValueOfNoPX(this.ovMapDiv.style.top) + deltaY;
		this.panDiv.style.top = Util.getValueOfNoPX(this.panDiv.style.top) + deltaY;
	},
    /**
     * 注册事件
     * @param {Object} source
     * @param {Object} prefix
     * @param {Object} param
     */
    registerEvent: function(source, prefix, param){
        var params = param.split(',');
        if (params) {
            for (var i = 0; i < params.length; i++) {
                Event.observe(source, params[i], eval('this.' + prefix + params[i]).bindAsEventListener(this));
            }
        }
    },
	/**
	 * 取得OV地图DIV
	 */
    getOvMapDiv: function(){
    	return this.ovMapDiv;
    },
	/**
	 * 取得当前的ZOOM.
	 */
    getCurrentZoom: function(){
    	return this.ovModel.getZoom();
    },
	
	/**
	 * 
	 * @param {Object} e
	 */
    pan_click: function(e){
//        setCurPos(Util.getValueOfNoPX(this.ovMapDiv.style.left), Util.getValueOfNoPX(this.ovMapDiv.style.top))
        this.slide();
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} layerId
	 * @param {Object} w
	 * @param {Object} h
	 * @param {Object} img
	 */
	slide: function (){
		var ovMapDivLeft = Util.getValueOfNoPX(this.ovMapDiv.style.left);
		var ovMapDivTop = Util.getValueOfNoPX(this.ovMapDiv.style.top);

//		var containerW = Util.getValueOfNoPX($(layerId).parentNode.style.width);
//	    var containerH = Util.getValueOfNoPX($(layerId).parentNode.style.height);
		
	    if (state == 1) {
	        state = 0;
//	        this.doExpand(ovMapDivLeft + w, ovMapDivTop + h);
	        this.panDiv.childNodes[0].src = ImageBaseDir + 'panel/arrowright2.gif';
			this.ovMapDiv.style.display = "none";
	    }
	    else {
	        state = 1;
//	        this.doExpand(ovMapDivLeft - w, ovMapDivTop - h);
	        this.panDiv.childNodes[0].src = ImageBaseDir + 'panel/arrowright1.gif';
			this.ovMapDiv.style.display = "";
	    }
	},
	/**
	 * 
	 * @param {Object} layerId
	 * @param {Object} left
	 * @param {Object} top
	 * @param {Object} speed
	 * @param {Object} speedRate
	 */
	moveOvMapDiv: function (){
		var ovMapDivLeft = Util.getValueOfNoPX(this.ovMapDiv.style.left);
		var ovMapDivTop = Util.getValueOfNoPX(this.ovMapDiv.style.top);		
		this.ovMapDiv.style.left = (ovMapDivLeft) + "px";
		this.ovMapDiv.style.top = (ovMapDivTop) + "px";
	},	
    /**
     * 
     * @param {Object} e
     */
    rect_mousedown: function(e){
        if (!this.isDragging) 
            this.isDragging = true;
        
        this.elm = Event.element(e);
        this.orgLeft = Util.getValueOfNoPX(this.elm.style.left);
        this.orgTop = Util.getValueOfNoPX(this.elm.style.top);
        this.orgMousePixel = Util.getMousePixel(e);
        var t_left = this.orgLeft;
        var t_top = this.orgTop;
        var t_width = this.elm.style.width;
        var t_height = this.elm.style.height;
        if (this.elm.setCapture) {
            this.elm.setCapture();
        }
        else 
            if (window.captureEvents) {
                window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
            }
        
        //create a new rect by lfcui on 23Mar2007
        //this.rectDiv = Util.createDiv(Util.createUniqueID('rect_temp_'),x,y,width/4,height/4,null,"absolute","2px solid green");
        this.rectTempDiv = Util.createDiv('rect_temp', t_left, t_top, t_width, t_height, null, "absolute", "2px solid green");
        this.rectTempDiv.style.backgroundColor = "lightyellow";
        this.rectTempDiv.style.filter = "alpha(opacity=40)";
        this.rectTempDiv.style.opacity = "0.40";
        this.rectTempDiv.style.fontSize = "1px";
        this.rectTempDiv.style.zIndex = 10000;
        this.rectTempDiv.style.cursor = "move";
        var symble_focus = document.createTextNode("+");
        this.rectTempDiv.appendChild(symble_focus);
        this.elm.parentNode.appendChild(this.rectTempDiv);
        
        this.ini_x = this.orgLeft;
        this.ini_y = this.orgTop;
        //this.registerEvent(this.rectDiv, 'rect_temp_', 'mousedown,mousemove,mouseup');
        //end create	    
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} e
	 */
    rect_mousemove: function(e){
        if (!this.isDragging || !this.orgMousePixel) 
            return;
        this.newMousePixel = Util.getMousePixel(e);
        var deltaX = this.newMousePixel.x - this.orgMousePixel.x;
        var deltaY = this.newMousePixel.y - this.orgMousePixel.y;
        this.elm.style.left = (this.orgLeft + deltaX) + "px";
        this.elm.style.top = (this.orgTop + deltaY) + "px";
        
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} e
	 */
    rect_mouseup: function(e){
        if (this.elm.releaseCapture) 
            this.elm.releaseCapture();
        else 
            if (window.captureEvents) 
                window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
        
        //adjust thumbnail to proper position                    
        var lastMousePixel = Util.getMousePixel(e);
        var deltaX = lastMousePixel.x - this.orgMousePixel.x;
        var deltaY = lastMousePixel.y - this.orgMousePixel.y;
        
        //move temp rect to the current rect
        var scale_use = Util.zoomScale(this.model.getOvModel().getZoom().getLevel()) / Util.zoomScale(this.model.getZoom().getLevel());
        
        this.call_glide(this.rectDiv.id, Number(deltaX), Number(deltaY), scale_use);
        
        document.onmousemove = null;
        document.onmouseup = null;
        this.isDragging = false;
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} e
	 */
    ov_mousedown: function(e){
        if (!this.isDragging) 
            this.isDragging = true;
        this.orgLeft = Util.getValueOfNoPX(this.ovDiv.style.left);
        this.orgTop = Util.getValueOfNoPX(this.ovDiv.style.top);
        this.orgMousePixel = Util.getMousePixel(e);
        if (this.ovDiv.setCapture) 
            this.ovDiv.setCapture();
        else 
            if (window.captureEvents) 
                window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} e
	 */
    ov_mousemove: function(e){
        if (!this.isDragging || !this.orgMousePixel) 
            return;
        this.newMousePixel = Util.getMousePixel(e);
        var deltaX = this.newMousePixel.x - this.orgMousePixel.x;
        var deltaY = this.newMousePixel.y - this.orgMousePixel.y;
        this.ovDiv.style.left = (this.orgLeft + deltaX) + "px";
        this.ovDiv.style.top = (this.orgTop + deltaY) + "px";
        
        var ini_main_map_x = Util.getValueOfNoPX(this.ovDiv.parentNode.parentNode.childNodes[0].style.left);
        var ini_main_map_y = Util.getValueOfNoPX(this.ovDiv.parentNode.parentNode.childNodes[0].style.top);
        //this.ovDiv.parentNode.parentNode.childNodes[0].style.left = (ini_main_map_x+deltaX*scale_test) + "px";
        //this.ovDiv.parentNode.parentNode.childNodes[0].style.top = (ini_main_map_y+deltaY*scale_test) + "px";
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} e
	 */
    ov_mouseup: function(e){
        if (this.ovDiv.releaseCapture){
			this.ovDiv.releaseCapture();
		} 
        else if (window.captureEvents){
			window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		} 
        
        var lastMousePixel = Util.getMousePixel(e);
        var deltaX = lastMousePixel.x - this.orgMousePixel.x;
        var deltaY = lastMousePixel.y - this.orgMousePixel.y;
        this.reLoadTiles(this.model, deltaX, deltaY, true);
        
        document.onmousemove = null;
        document.onmouseup = null;
        this.isDragging = false;
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} e
	 */    
    ov_click: function(e){
        this.curMousePixel = Util.getMouseRelativePixel(e, this.ovDiv);
        this.curCenterPixel = Util.getScreenPixel(this.model.getOvModel().getViewCenterCoord(), this.model.getOvModel().getZoom());
        var deltaX = this.curCenterPixel.x - this.curMousePixel.x;
        var deltaY = this.curCenterPixel.y - this.curMousePixel.y;
        this.ovDiv.style.left = (Util.getValueOfNoPX(this.ovDiv.style.left) + deltaX) + "px";
        this.ovDiv.style.top = (Util.getValueOfNoPX(this.ovDiv.style.top) + deltaY) + "px";
        this.reLoadTiles(this.model, deltaX, deltaY, true);
        
        Event.stop(e);
    },
	/**
	 * 
	 * @param {Object} model
	 * @param {Object} deltaX
	 * @param {Object} deltaY
	 * @param {Object} isPlus
	 */    
    reLoadTiles: function(model, deltaX, deltaY, isPlus){
        var scale = Util.zoomScale(model.getOvModel().getZoom().getLevel()) / Util.zoomScale(model.getZoom().getLevel());
        var orgCenterCoord = model.getViewCenterCoord();
        var curZoom = model.getZoom();
        var realDeltaX = deltaX * scale * curZoom.realMapBound.getWidth() / (curZoom.getTileCols() * TileSize);
        var realDeltaY = deltaY * scale * curZoom.realMapBound.getHeight() / (curZoom.getTileRows() * TileSize);
        if (isPlus) {
            x = orgCenterCoord.x - realDeltaX;
            y = orgCenterCoord.y + realDeltaY;
        }
        else {
            x = orgCenterCoord.x + realDeltaX;
            y = orgCenterCoord.y - realDeltaY;
        }
        var newCenterCoord = new Coordinate(x, y)
        if (!newCenterCoord.isSame(orgCenterCoord)) {
            model.setViewCenterCoord(newCenterCoord);
            this.loadTiles(model.getOvModel(), this.ovMapDiv, this.ovDiv, false);
            model.controls[this.container.childNodes[0].id].paint(model);
        }
    },
    /**
     * 
     * @param {Object} layerId
     * @param {Object} dest_x_t
     * @param {Object} dest_y_t
     * @param {Object} scale_t
     */
    call_glide: function(layerId, dest_x_t, dest_y_t, scale_t){
        this.reset();
        this.target = $(layerId);
        this.ini_x = Number(Util.getValueOfNoPX(this.target.style.left));
        this.ini_y = Number(Util.getValueOfNoPX(this.target.style.top));
        this.ini_x_map = Number(Util.getValueOfNoPX(this.target.parentNode.childNodes[0].style.left));
        this.ini_y_map = Number(Util.getValueOfNoPX(this.target.parentNode.childNodes[0].style.top));
        this.ini_x_main_map = Number(Util.getValueOfNoPX(this.target.parentNode.parentNode.childNodes[0].style.left));
        this.ini_y_main_map = Number(Util.getValueOfNoPX(this.target.parentNode.parentNode.childNodes[0].style.top));
        
        this.glide((-dest_x_t), (-dest_y_t), scale_t);
    },
    /**
     * 
     * @param {Object} dest_x
     * @param {Object} dest_y
     * @param {Object} scale_test
     */
    glide: function(dest_x, dest_y, scale_test){
        if (this.target) {
            //    		var scale_test=2.5;
            var coefficient = 12;
            var dist_x, dist_y;
            if (dest_y != 0 && this.y_moved != dest_y) {
            
                dist_y = (Math.max(Math.abs(this.y_moved), Math.abs(dest_y)) - Math.min(Math.abs(this.y_moved), Math.abs(dest_y)));
                
                if (dist_y < Math.abs(dest_y / coefficient)) {
                    this.y_moved = dest_y;
                    this.control_mark = 1;
                    
                }
                else {
                    this.y_moved = this.y_moved + dest_y / coefficient;
                    this.target.style.top = (this.ini_y + this.y_moved) + "px";
                    this.target.parentNode.childNodes[0].style.top = (this.ini_y_map + this.y_moved) + "px";
                    this.target.parentNode.parentNode.childNodes[0].style.top = (this.ini_y_main_map + this.y_moved * scale_test) + "px";
                    this.control_mark = 2;
                }
                
            }
            if (dest_x != 0 && this.x_moved != dest_x) {
            
                dist_x = (Math.max(Math.abs(this.x_moved), Math.abs(dest_x)) - Math.min(Math.abs(this.x_moved), Math.abs(dest_x)));
                
                if (this.control_mark == 1 || dist_x < Math.abs(dest_x / coefficient)) {
                    this.x_moved = dest_x;
                    this.control_mark = 1;
                    before_move = new Date();
                }
                else {
                    this.x_moved = this.x_moved + dest_x / coefficient;
                    this.target.style.left = (this.ini_x + this.x_moved) + "px";
                    this.target.parentNode.childNodes[0].style.left = (this.ini_x_map + this.x_moved) + "px";
                    this.target.parentNode.parentNode.childNodes[0].style.left = (this.ini_x_main_map + this.x_moved * scale_test) + "px";
                    this.control_mark = 2;
                }
                
            }
            
            //timer control
            if (this.control_mark == 2) {
                this.glide_timer = setTimeout(function(){
                    this.glide(dest_x, dest_y, scale_test);
                }.bind(this), 1);
            }
            else 
                if (this.control_mark == 1) {
                    this.target.style.left = (this.ini_x + dest_x) + "px";
                    this.target.style.top = (this.ini_y + dest_y) + "px";
                    this.target.parentNode.childNodes[0].style.left = (this.ini_x_map + this.x_moved) + "px";
                    this.target.parentNode.childNodes[0].style.top = (this.ini_y_map + this.y_moved) + "px";
                    this.target.parentNode.parentNode.childNodes[0].style.left = (this.ini_x_main_map + this.x_moved * scale_test) + "px";
                    this.target.parentNode.parentNode.childNodes[0].style.top = (this.ini_y_main_map + this.y_moved * scale_test) + "px";
                    this.target.parentNode.removeChild(document.getElementById("rect_temp"));
                    clearTimeout(this.glide_timer);
                    this.reset();
                }
                else {
                    if (document.getElementById("rect_temp")) 
                        this.target.parentNode.removeChild(document.getElementById("rect_temp"));
                }
            if (this.control_mark < 2) {
                setTimeout(function(){
                    this.reLoadTiles(this.model, -dest_x, -dest_y, false);
                }.bind(this), 1);
            }
            this.control_mark = 0;
            dist_x = 0;
            dist_y = 0;
        }
    },
    /**
     * 重置
     */
    reset: function(){
        this.x_moved = 0;
        this.y_moved = 0;
        this.ini_x = 0;
        this.ini_y = 0;
        this.ini_x_map = 0;
        this.ini_y_map = 0;
        this.ini_x_main_map = 0;
        this.ini_y_main_map = 0;
        this.target = null;
        this.control_mark = 0;
        this.glide_timer = null;
    }
});