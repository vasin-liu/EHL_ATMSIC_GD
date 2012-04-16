/**
 * 命令抽象类
 */
Abstract.Command = function(){
}
Abstract.Command.prototype = {
    /**
     * 
     * @param {Object} id
     * @param {Object} img1
     * @param {Object} img2
     * @param {Object} img3
     * @param {Object} pos
     * @param {Object} left
     * @param {Object} top
     * @param {Object} width
     * @param {Object} height
     */
	initialize: function(id, img1, img2, img3, pos, left, top, width, height){
        this.toolType = "Command";
        this.id = id;
        this.img_normal = img1;
        this.img_over = img2;
        this.img_down = img3;
        this.position = pos;
        this.left = parseInt(left);
        this.top = parseInt(top);
        this.width = parseInt(width);
        this.height = parseInt(height);
        this.div = Util.createDiv(this.id, this.left, this.top, this.width, this.height, this.img_normal, this.position, '0px solid #ccc');
        this.div.style.cursor = "pointer";
    },
    /**
     * 点击事件
     * @param {Object} e
     */
    cmdClickHandler: function(e){
        if (Event.element(e).childNodes.length > 0) 
            return;
        this.clearCurrentToolStatus();
        var cmd = this.tools[Event.element(e).parentNode.id];
        cmd.div.childNodes[0].src = cmd.img_down;
        if (!cmd.selected) 
            cmd.selected = true;
        
        this.currentTool = this.defaultTool;
        this.currentTool.div.childNodes[0].src = this.currentTool.img_normal;
        this.mapDiv.style.cursor = this.currentTool.cursorStyle;
        
        cmd.cmd_clickHandler(cmd, this.model, this.mapDiv);
        Event.stop(e);
    },
    /**
     * MOUSEOVER事件
     * @param {Object} e
     */
    cmdMouseOverHandler: function(e){
        var elm = Event.element(e)
        if (elm.childNodes.length > 0) 
            return;
        var cmd = this.tools[elm.parentNode.id];
        if (cmd.selected == true) 
            return;
        elm.alt = cmd.alt;
        elm.src = cmd.img_over;
        Event.stop(e);
    },
    /**
     * MOUSEOUT事件
     * @param {Object} e
     */
    cmdMouseOutHandler: function(e){
        var elm = Event.element(e)
        if (elm.childNodes.length > 0) 
            return;
        var cmd = this.tools[elm.parentNode.id];
        if (cmd.selected == true) 
            return;
        elm.src = cmd.img_normal;
        Event.stop(e);
    },
    /**
     * 
     * @param {Object} container
     * @param {Object} index
     */
    clearOrgDiv: function(container, index){
        var nodes = container.childNodes;
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].id.indexOf('search' + index + '_') > -1) {
                container.removeChild(nodes[i]);
            }
        }
    },
    /**
     * 注册事件
     * @param {Object} source
     * @param {Object} param
     */
    registerEvent: function(source, param){
        Event.observe(source, param.split(',')[0], eval('this.' + param.split(',')[0]).bindAsEventListener(this));
        Event.observe(source, param.split(',')[1], eval('this.' + param.split(',')[1]).bindAsEventListener(this));
        Event.observe(source, param.split(',')[2], eval('this.' + param.split(',')[2]).bindAsEventListener(this));
    },
    /**
     * MOUSEDOWN处理
     * @param {Object} e
     */
    mousedown: function(e){
        if (Event.element(e).childNodes.length == 0) 
            return;
        if (!this.dragged) 
            this.dragged = true;
        
        this.elm = Event.element(e);
        this.orgPixelX = Util.getValueOfNoPX(this.elm.parentNode.style.left);
        this.orgPixelY = Util.getValueOfNoPX(this.elm.parentNode.style.top);
        this.elm.style.cursor = 'move';
        this.orgMousePixel = Util.getMousePixel(e);
        
        if (this.elm.setCapture) {
            this.elm.setCapture();
        }
        else 
            if (window.captureEvents) {
                window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
            }
    },
    /**
     * MOUSEMOVE处理
     * @param {Object} e
     */
    mousemove: function(e){
        if (!this.dragged) 
            return;
        if (!Event.element(e)) 
            return;
        this.newMousePixel = Util.getMousePixel(e);
        
        var deltaX = this.newMousePixel.x - this.orgMousePixel.x;
        var deltaY = this.newMousePixel.y - this.orgMousePixel.y;
        this.elm.parentNode.style.left = (this.orgPixelX + deltaX) + "px";
        this.elm.parentNode.style.top = (this.orgPixelY + deltaY) + "px";
    },
    /**
     * MOUSEUP处理
     * @param {Object} e
     */
    mouseup: function(e){
        if (!this.elm) 
            return;
        if (this.elm.releaseCapture) 
            this.elm.releaseCapture();
        else 
            if (window.captureEvents) 
                window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
        document.onmousemove = null;
        document.onmouseup = null;
        this.dragged = false;
        this.elm.style.cursor = '';
    }
};

/**
 * 全图命令
 */
FullCmd = Class.create();
FullCmd.prototype = Object.extend(new Abstract.Command(), {
    alt: '全图显示',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
        model.reset(mapDiv, $('sliderbar_' + model.getId()).parentNode);
    }
});
/**
 * 清除
 */
ClearCmd = Class.create();
ClearCmd.prototype = Object.extend(new Abstract.Command(), {
    alt: '清除操作痕迹',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
        //model.clearOverLayers(mapDiv);
        map.getVMLDiv().innerHTML = "";
    }
});

/**
 * 前一屏
 */  
PrevCmd = Class.create();
PrevCmd.prototype = Object.extend(new Abstract.Command(), {
    alt: '移到上一屏',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
        if (model.curIndex == -1) 
            model.curIndex = model.traceIndex - 1;
        if (model.curIndex > 0 && model.curIndex <= model.traceIndex - 1) {
            var obj = model.traces[--model.curIndex];
            model.setViewCenterCoord(obj.coord);
            model.setZoom(new Zoom(obj.level));
            model.controls[mapDiv.id].paint(model, false);
            model.controls[model.ovId].paint(model);
            $('sliderbar_' + model.getId()).parentNode.style.top = ((MaxZoomLevel - obj.level) * 12 + 6) + "px";
        }
    }
});

/**
 * 后一屏
 */  
NextCmd = Class.create();
NextCmd.prototype = Object.extend(new Abstract.Command(), {
    alt: '移到下一屏',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
        if (model.curIndex == -1) 
            model.curIndex = model.traceIndex - 1;
        if (model.curIndex >= 0 && model.curIndex < model.traceIndex - 1) {
            var obj = model.traces[++model.curIndex];
            model.setViewCenterCoord(obj.coord);
            model.setZoom(new Zoom(obj.level));
            model.controls[mapDiv.id].paint(model, false);
            model.controls[model.ovId].paint(model);
            $('sliderbar_' + model.getId()).parentNode.style.top = ((MaxZoomLevel - obj.level) * 12 + 6) + "px";
        }
    }
});

/**
 * 地图检索
 */
SearchCmd = Class.create();
SearchCmd.prototype = Object.extend(new Abstract.Command(), {
    alt: '地图检索',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
		var objSearch = document.getElementById("SearchPanelDiv");
		var objImage = document.getElementById("ImageButtonDiv");
		if (objSearch){
		    objSearch.parentNode.removeChild(objSearch);
		}
		if (objImage){
		    objImage.parentNode.removeChild(objImage);
		}
		
		var searchPanel = new SearchPanel($('map'));
    }
});

/**
 * 辖区定位
 */
LocateCmd = Class.create();
LocateCmd.prototype = Object.extend(new Abstract.Command(), {
    alt: '辖区定位',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
        this.clearOrgDiv(mapDiv.parentNode, 2);
        this.areaLocate(mapDiv, model);
    },
    /**
     * 区域定位
     * @param {Object} mapDiv
     * @param {Object} model
     */
    areaLocate: function(mapDiv, model){
        var left = Util.getValueOfNoPX(mapDiv.parentNode.style.width) - 220;
        this.areaDiv = Util.createDiv(Util.createUniqueID('search2_'), left, 10, 220, 300, null, "absolute", "1px solid blue");
        this.areaDiv.style.backgroundColor = "white";
        this.areaDiv.style.filter = "alpha(opacity=80)";
        this.areaDiv.style.opacity = "0.80";
        this.areaDiv.style.fontSize = "1px";
        this.areaDiv.style.zIndex = 10000;
        this.areaDiv.innerHTML = '<div align="right" style="background:blue;padding:1px;cursor:default"><img onclick="hideInfoWindown(event, \'' + this.areaDiv.id + '\')" src="' + ImageBaseDir + 'infowindow_close.gif"></div>';
        this.areaDiv.innerHTML += '<iframe id="searchFrame" name="searchFrame" style="width:100%; height:400px;" src="../point.jsf?flag=1" scrolling="no" frameborder="0"></iframe>';
        this.registerEvent(this.areaDiv.childNodes[0], "mousedown,mousemove,mouseup");
        mapDiv.parentNode.appendChild(this.areaDiv);
    }
});

//Modified by Liuwx 2012-1-5
/**
 * 更多专题图
 */
ShowMoreMap = Class.create();
ShowMoreMap.prototype = Object.extend(new Abstract.Command(), {
    alt: '更多专题图',
    selected: false,
    /**
     * CLICK事件
     * @param {Object} cmd
     * @param {Object} model
     * @param {Object} mapDiv
     */
	cmd_clickHandler: function(cmd, model, mapDiv){
		showfangweiMap();
		var showMoreMap = new ShowMoreMap($('map'));
    }
});
//Modification finished