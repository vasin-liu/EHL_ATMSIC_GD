/**
 * 工具抽象类
 */
Abstract.Tool = function(){}
Abstract.Tool.prototype = {
    /**
     * 初始化
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
        this.toolType = "Tool";        
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
     * MOUSEDOWN
     * @param {Object} e
     * @param {Object} toolbar
     */
	mouseDownHandler: function(e, toolbar) {
    },
    /**
     * MOUSEMOVE
     * @param {Object} e
     * @param {Object} toolbar
     */  
    mouseMoveHandler: function(e, toolbar){
    },
    /**
     * MOUSEUP
     * @param {Object} e
     * @param {Object} toolbar
     */  
    mouseUpHandler: function(e, toolbar){
    },
    /**
     * CLICK
     * @param {Object} e
     * @param {Object} toolbar
     */
    clickHandler: function(e, toolbar){
       Event.stop(e);
    },
    /**
     * DBLCLICK
     * @param {Object} e
     * @param {Object} toolbar
     */
    dblClickHandler: function(e, toolbar){
    },
    /**
     * BARCLICK
     * @param {Object} e
     */   
    barClickHandler: function(e){
        var elm = Event.element(e);
        if(elm.childNodes.length > 0)  return; 
        this.clearCurrentToolStatus();
        var tool = this.tools[Event.element(e).parentNode.id];        
        tool.div.childNodes[0].src = tool.img_down;
        if(!tool.selected){
            tool.selected = true;            
            this.currentTool = tool;
            this.mapDiv.style.cursor = tool.cursorStyle;            
        }  
        Event.stop(e);
    },
    /**
     * BARMOUSEOVER
     * @param {Object} e
     */
    barMouseOverHandler: function(e){
        var elm = Event.element(e);
        if (elm.childNodes.length > 0) {
			return;
		}
        if(this.tools[elm.parentNode.id].selected == true) 
            return;
        elm.src = this.tools[elm.parentNode.id].img_over;
        elm.alt = this.tools[elm.parentNode.id].alt;
        
        Event.stop(e);
    },
    /**
     * BARMOUSEOUT
     * @param {Object} e
     */
    barMouseOutHandler: function(e){
        var elm = Event.element(e);
        if(elm.childNodes.length>0)  
            return;        
        if(this.tools[elm.parentNode.id].selected == true) 
            return;
        elm.src = this.tools[elm.parentNode.id].img_normal;
        Event.stop(e);
    },
    /**
     * 拉框放大
     * @param {Object} model
     * @param {Object} extent
     * @param {Object} container
     * @param {Object} direction
     */
    zoomToExtent: function(model, extent, container, direction){
        if(extent){
            var zoom = model.getZoom();
            var w1 = zoom.getViewBound(container).getPixelWidth(zoom);
            var h1 = zoom.getViewBound(container).getPixelHeight(zoom);
            var w2 = extent.getPixelWidth(zoom);
            var h2 = extent.getPixelHeight(zoom);
            var r1 = Math.sqrt(w1*w1 + h1*h1);
            var r2 = Math.sqrt(w2*w2 + h2*h2);
            
            var deltalLevel = Math.floor(r1/r2);
            if(w2<1 || h2<1)
                return;
            var orgLevel = zoom.getLevel();
            if(deltalLevel > 3) deltalLevel = 3;            
            switch(direction){
                case 'zoomin':
                    orgLevel += deltalLevel;
                    if(orgLevel >MaxZoomLevel) orgLevel = MaxZoomLevel;  
                    break;
                case 'zoomout':
                    orgLevel -= deltalLevel;
                    if(orgLevel < 1) orgLevel = 1;
                    break;
            }            
			model.setZoom(new Zoom(orgLevel));
			model.setViewCenterCoord(extent.getCenter());
			
			model.controls[container.childNodes[0].id].paint(model, true);
			
			if(typeof(ovmap) != "undefined")
			{
				model.controls[model.ovId].paint(model);
			}
			if(typeof(navControl) != "undefined")
			{
				$('sliderbar_'+model.getId()).parentNode.style.top=((MaxZoomLevel-orgLevel)*12+6)+"px";
			}
        }
    }
};

/**
 * 移屏
 */
PanTool = Class.create(); 
PanTool.prototype = Object.extend(new Abstract.Tool(), {
      cursorStyle: 'move',
      selected: false,
      alt: '移屏',      
      /**
       * MOUSEDOWN
       * @param {Object} e
       * @param {Object} toolbar
       */
	  mouseDownHandler: function(e, toolbar) {
        this.mapDiv = toolbar.mapDiv;
        if(!this.mapDiv)
            return;
        if(!this.isDrag)
            this.isDrag = true;
        this.orgPixelX = Util.getValueOfNoPX(this.mapDiv.style.left);
	    this.orgPixelY = Util.getValueOfNoPX(this.mapDiv.style.top);
	    
	    //add by wwj
		this.vmlDiv = map.getVMLDiv();
        this.orgVMLPixelX = Util.getValueOfNoPX(this.vmlDiv.style.left);
	    this.orgVMLPixelY = Util.getValueOfNoPX(this.vmlDiv.style.top);
		
		this.orgMousePixel = Util.getMousePixel(e);
	    if(this.mapDiv.setCapture)
		    this.mapDiv.setCapture();
	    else if (window.captureEvents) 
		    window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
	    document.onselectstart = function(){return false};  
	    Event.stop(e);
      },
      /**
       * MOUSEMOVE
       * @param {Object} e
       * @param {Object} toolbar
       */
      mouseMoveHandler: function(e, toolbar){
        if(this.orgMousePixel == null || this.isDrag == false || !this.mapDiv)
            return;
        this.newMousePixel = Util.getMousePixel(e);	    
	    var deltaX = this.newMousePixel.x - this.orgMousePixel.x;
	    var deltaY = this.newMousePixel.y - this.orgMousePixel.y;
		this.mapDiv.style.left = (this.orgPixelX + deltaX) + "px";
		this.mapDiv.style.top = (this.orgPixelY + deltaY) + "px";	
		
		//add by wwj
		this.vmlDiv.style.left = (this.orgVMLPixelX + deltaX) + "px";
		this.vmlDiv.style.top = (this.orgVMLPixelY + deltaY) + "px";
		
		Event.stop(e);
      },
      /**
       * MOUSEUP
       * @param {Object} e
       * @param {Object} toolbar
       */
      mouseUpHandler: function(e, toolbar){
        if(!this.isDrag) return;
        if(!this.mapDiv)
            return;
        if(this.mapDiv.releaseCapture) 
			this.mapDiv.releaseCapture();
		else if(window.captureEvents) 
			window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		var lastMousePixel = Util.getMousePixel(e);
		var deltaX = lastMousePixel.x - this.orgMousePixel.x;
		var deltaY = lastMousePixel.y - this.orgMousePixel.y;	
	
		if(deltaX != 0 || deltaY != 0){
			this.reLoadTiles(toolbar.model, deltaX, deltaY, this.mapDiv);		
		}	
		
		document.onmousemove = null;
		document.onmouseup = null;
		this.isDrag = false;
		Event.stop(e);
      },
      /**
       * 重新加载文件
       * @param {Object} model
       * @param {Object} deltaX
       * @param {Object} deltaY
       * @param {Object} mapDiv
       */
      reLoadTiles: function(model, deltaX, deltaY, mapDiv){
        var orgCenterCoord = model.getViewCenterCoord();
        var curZoom = model.getZoom();
        var x = orgCenterCoord.x - deltaX*curZoom.realMapBound.getWidth()/(curZoom.getTileCols()*TileSize);
        var y = orgCenterCoord.y + deltaY*curZoom.realMapBound.getHeight()/(curZoom.getTileRows()*TileSize);
        var newCenterCoord = new Coordinate(x, y);
        if(!newCenterCoord.isSame(orgCenterCoord))
            model.setViewCenterCoord(newCenterCoord);
 		
		var control = new Abstract.Control();
		control.loadTiles(model, mapDiv.parentNode, mapDiv, true, true);
		
		//add by wwj 2008-7-22
		if(typeof(ovmap) != "undefined")
		{
        	model.controls[model.ovId].paint(model);
        }
      },
      /**
       * CLICK事件
       * @param {Object} e
       * @param {Object} toolbar
       */
      clickHandler: function(e, toolbar){
        Event.stop(e);
      },
      /**
       * DBLCLICK
       * @param {Object} e
       * @param {Object} toolbar
       */
      dblClickHandler: function(e, toolbar){
        var point = Util.getCoordinateByPixel(Util.getMousePixel(e),toolbar.model.zoom).getPoint();
		//增加一个点标注:
		var infoCoord = Util.getMouseRelativePixel(e, this.mapDiv);
		Event.stop(e);	
      }
});

/**
 * 拉框放大
 */
ZoominTool = Class.create();
ZoominTool.prototype = Object.extend(new Abstract.Tool(), {
    cursorStyle:'url("images/zoomin.cur")',
    selected: false,
    alt: '拉框放大',
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
	mouseDownHandler: function(e, toolbar) {        
        this.mapDiv = toolbar.mapDiv;
        this.mouseDownPixel = Util.getMouseRelativePixel(e, this.mapDiv);
        
        this.zoomBox = Util.createDiv('zoomBox',this.mouseDownPixel.x,this.mouseDownPixel.y, null,null,null,"absolute","1px solid red");
        this.zoomBox.style.backgroundColor = "white";
        this.zoomBox.style.filter = "alpha(opacity=50)"; 
        this.zoomBox.style.opacity = "0.50";
        this.zoomBox.style.fontSize = "1px";
        this.mapDiv.appendChild(this.zoomBox);        
        Event.stop(e);
    },
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseMoveHandler: function(e, toolbar){
        this.mapDiv = toolbar.mapDiv;
        this.mouseMovePixel = Util.getMouseRelativePixel(e, this.mapDiv);
        
        if (this.mouseDownPixel) {
            var deltaX = Math.abs(this.mouseDownPixel.x - this.mouseMovePixel.x);
            var deltaY = Math.abs(this.mouseDownPixel.y - this.mouseMovePixel.y);
            this.zoomBox.style.width = Math.max(1, deltaX) + "px";
            this.zoomBox.style.height = Math.max(1, deltaY) + "px";
            if (this.mouseMovePixel.x < this.mouseDownPixel.x)
                this.zoomBox.style.left = this.mouseMovePixel.x+"px";
            if (this.mouseMovePixel.y < this.mouseDownPixel.y)
                this.zoomBox.style.top = this.mouseMovePixel.y+"px";
        }        
        Event.stop(e);   
    },
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseUpHandler: function(e, toolbar){
        if (this.mouseDownPixel && this.mouseMovePixel) {              
            var top = Math.min(this.mouseDownPixel.y, this.mouseMovePixel.y);
            var bottom = Math.max(this.mouseDownPixel.y, this.mouseMovePixel.y);
            var left = Math.min(this.mouseDownPixel.x, this.mouseMovePixel.x);
            var right = Math.max(this.mouseDownPixel.x, this.mouseMovePixel.x);
            
            var leftTop = Util.getCoordinateByPixel({x:left,y:top}, toolbar.model.getZoom());
            var rightbottom = Util.getCoordinateByPixel({x:right,y:bottom}, toolbar.model.getZoom());
            var rect = new Rectangle(leftTop.x/1e16, rightbottom.x/1e16, leftTop.y/1e16, rightbottom.y/1e16);  
            this.removeZoomBox(this.zoomBox);
            this.zoomToExtent(toolbar.model, rect, this.mapDiv.parentNode, "zoomin");
        }
        document.onselectstart = function(){return false};
        this.coord = null;
        this.newCoord = null;
        
        //add by wwj
        //toolbar.toPan();
        Event.stop(e);
	},
    /**
     * 
     * @param {Object} zoom
     */  
	removeZoomBox: function(zoom){
		if(!zoom) return;
		zoom.parentNode.removeChild(zoom);
		zoom = null;
	},
	/**
	 * 
	 * @param {Object} e
	 * @param {Object} movel
	 */
	clickHandler: function(e, movel){
		Event.stop(e);
	},
	/**
	 * 
	 * @param {Object} e
	 * @param {Object} movel
	 */
	dblClickHandler: function(e, movel){
		Event.stop(e);
	}   
});

/**
 * 拉框缩小
 */
ZoomoutTool = Class.create();
ZoomoutTool.prototype = Object.extend(new Abstract.Tool(), {
    cursorStyle:'url("images/zoomout.cur")',
    selected: false,
    alt: '拉框缩小',
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
	mouseDownHandler: function(e, toolbar) {        
        this.mapDiv = toolbar.mapDiv;
        this.mouseDownPixel = Util.getMouseRelativePixel(e, this.mapDiv);
        
        this.zoomBox = Util.createDiv('zoomBox',this.mouseDownPixel.x,this.mouseDownPixel.y, null,null,null,"absolute","1px solid red");
        this.zoomBox.style.backgroundColor = "white";
        this.zoomBox.style.filter = "alpha(opacity=50)"; 
        this.zoomBox.style.opacity = "0.50";
        this.zoomBox.style.fontSize = "1px";
        this.mapDiv.appendChild(this.zoomBox);        
        Event.stop(e);
    },
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseMoveHandler: function(e, toolbar){
        this.mapDiv = toolbar.mapDiv;
        this.mouseMovePixel = Util.getMouseRelativePixel(e, this.mapDiv);
        
        if (this.mouseDownPixel) {
            var deltaX = Math.abs(this.mouseDownPixel.x - this.mouseMovePixel.x);
            var deltaY = Math.abs(this.mouseDownPixel.y - this.mouseMovePixel.y);
            this.zoomBox.style.width = Math.max(1, deltaX) + "px";
            this.zoomBox.style.height = Math.max(1, deltaY) + "px";
            if (this.mouseMovePixel.x < this.mouseDownPixel.x)
                this.zoomBox.style.left = this.mouseMovePixel.x+"px";
            if (this.mouseMovePixel.y < this.mouseDownPixel.y)
                this.zoomBox.style.top = this.mouseMovePixel.y+"px";
        }        
        Event.stop(e);
    },
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseUpHandler: function(e, toolbar){
        if (this.mouseDownPixel && this.mouseMovePixel) {              
            var top = Math.min(this.mouseDownPixel.y, this.mouseMovePixel.y);
            var bottom = Math.max(this.mouseDownPixel.y, this.mouseMovePixel.y);
            var left = Math.min(this.mouseDownPixel.x, this.mouseMovePixel.x);
            var right = Math.max(this.mouseDownPixel.x, this.mouseMovePixel.x);
            
            var leftTop = Util.getCoordinateByPixel({x:left,y:top}, toolbar.model.getZoom());
            var rightbottom = Util.getCoordinateByPixel({x:right,y:bottom}, toolbar.model.getZoom());
            var rect = new Rectangle(leftTop.x/1e16, rightbottom.x/1e16, leftTop.y/1e16, rightbottom.y/1e16);  
            this.removeZoomBox(this.zoomBox);
            this.zoomToExtent(toolbar.model, rect, this.mapDiv.parentNode, "zoomout");
        }
        document.onselectstart = function(){return false};
        this.coord = null;
        this.newCoord = null;
        
        //add by wwj
        //toolbar.toPan();
        Event.stop(e);
	},
	/**
	 * 
	 * @param {Object} zoom
	 */	
	removeZoomBox: function(zoom){
		if(!zoom) return;
		this.mapDiv.removeChild(zoom);
		zoom = null;
	},
	/**
	 * 
	 * @param {Object} e
	 * @param {Object} movel
	 */
	clickHandler: function(e, movel){
		Event.stop(e);
	},
	/**
	 * 
	 * @param {Object} e
	 * @param {Object} movel
	 */
	dblClickHandler: function(e, movel){
		Event.stop(e);
	}
});
/**
 * 测量距离
 */
MeasureTool = Class.create();
MeasureTool.prototype = Object.extend(new Abstract.Tool(), {
    isDrag: false,
    selected: false,        
    cursorStyle:'url("images/mea.cur")',
    alt: '测量距离',
    measure: new Array(),
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseDownHandler: function(e, toolbar){
        if(!this.lineDiv){
			this.lineDiv = Util.createDiv('lineDiv');
		}
        this.mapDiv = toolbar.mapDiv;
        this.mapDiv.appendChild(this.lineDiv);                
        
        this.mouseDownPixel = Util.getMouseRelativePixel(e, this.mapDiv);
        
        if(!this.isDrag){
			this.isDrag = true;
		}
            
        this.lastX=this.mouseDownPixel.x;   
        this.lastY=this.mouseDownPixel.y;        
        this.line='<v:line from="'+this.lastX+','+this.lastY+'" to="'+this.mouseDownPixel.x+','+this.mouseDownPixel.y+'" strokecolor="red" strokeweight="2pt" style="position:absolute;left:-3px;top:-3px;"></v:line>';
        this.vLine = document.createElement(this.line);
        this.lineDiv.appendChild(this.vLine);           
        
        var coord = Util.getCoordinateByPixel(this.mouseDownPixel, toolbar.model.getZoom());
        this.measure.push(new Point(coord.x/1e16, coord.y/1e16));
        Event.stop(e);
    }, 
    /**
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseMoveHandler: function(e, toolbar){
        if(!this.isDrag){
			return;
		}
            
        this.mouseMovePixel = Util.getMouseRelativePixel(e, this.mapDiv);
        this.vLine.to = this.mouseMovePixel.x + "," + this.mouseMovePixel.y;                 
        Event.stop(e);
    },
    /**
     * @param {Object} e
     * @param {Object} toolbar
     */
    dblClickHandler: function(e, toolbar){
        if(!this.isDrag || !this.lineDiv)
           return;  
        this.lineDiv.innerHTML = "";
        this.mapDiv.removeChild(this.lineDiv);
        var pline = new Polyline("measure", this.measure, "blue", 2, null, null, null, null, true);
        pline.setToMap(toolbar.mapDiv, toolbar.model);
        this.measure = new Array();
        
        this.isDrag=false;        
        
        var len = pline.getLength();
        var unit = '';        
        if(len != null && len.toString().indexOf(".")){
            var i = len.toString().indexOf(".");
            if(i<4){
                unit = "米";
                len = Number(len.toString().substring(0, i+3));
            }                
            else{
                len = len/1000;
                i = len.toString().indexOf(".");
                len = Number(len.toString().substring(0, i+4));
                unit = "千米";
            }
        }
        
        var infoCoord = Util.getMouseRelativePixel(e, this.mapDiv);
        var mCoord = Util.getMousePixel(e);
        this.CreateMeasureInfo(toolbar.model.getId(), infoCoord, mCoord, "<br>本次总测量距离：<br>"+len+unit);
        
        //add by wwj
        //toolbar.toPan();
        
        Event.stop(e);
    },
    /**
     * 
     * @param {Object} modelId
     * @param {Object} infoCoord
     * @param {Object} result
     */
    CreateMeasureInfo: function(modelId, infoCoord, mCoord, result){
        var div = $("measureResultDiv");
        var resultDivId = Util.createUniqueID("measureResult_");
        if(!div){
            var mapDiv = $("map_"+modelId);
		    this.measureResult = document.createElement("div");
		    this.measureResult.id = Util.createUniqueID("measureResultDiv_");
		    this.measureResult.onselect = null;
		    this.measureResult.style.position = "absolute";
		    this.measureResult.style.background = "#FFFFFF";
		    this.measureResult.style.border = "1px solid #999999";
		    this.measureResult.style.fontSize = "12px";
		    this.measureResult.style.padding = "1px";
		    this.measureResult.innerHTML = '<div style="background:#EEEEEE;"><table style="width:150px;"><tr><td align=left>测量结果</td><td align=right><img onmousedown="hideWindown(event, \'' + this.measureResult.id + '\')" src="' + ImageBaseDir + 'infowindow_close.gif"></td></tr></table></div>';
		    this.measureResult.innerHTML += '<div id="'+resultDivId+'" align="center" style="padding:2px;height:50px;width:150px;z-index:10"></div>';
	        map.getVMLDiv().appendChild(this.measureResult);
	    }	    
	    this.measureResult.style.zIndex = 11;
	    var mw = map.getMapWidth();
	    if (mCoord.x + 150 > mw){
	    	infoCoord.x  = infoCoord.x - 150;
	    }
	    var mh = map.getMapHeight();
	    if (mCoord.y + 60 > mh){
	    	infoCoord.y  = infoCoord.y - 60;
	    }

	    this.measureResult.style.left = infoCoord.x + "px";
	    this.measureResult.style.top  = infoCoord.y + "px";	    
	    $(resultDivId).innerHTML = result;
	    this.measureResult.style.display = "";
    },
    /**
     * 
     * @param {Object} e
     * @param {Object} model
     */  
    clickHandler: function(e, model) {
        Event.stop(e);
    },
  	/**
  	 * 
  	 * @param {Object} e
  	 * @param {Object} model
  	 */
    mouseUpHandler: function(e, model){
        Event.stop(e);
    }
});
/**
 * 标签定位
 */
LabelTool = Class.create();
LabelTool.prototype = Object.extend(new Abstract.Tool(), {
    isDrag: false,
    selected: false,
    currentLabel: new Array(),
    cursorStyle:'url("images/mea.cur")',
    alt: '标签定位',
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    barClickHandler: function(e)
    {
    	var elm = Event.element(e);
        if(elm.childNodes.length > 0)  return; 
        this.clearCurrentToolStatus();
        var tool = this.tools[Event.element(e).parentNode.id];        
        tool.div.childNodes[0].src = tool.img_down;
        if(!tool.selected){
            tool.selected = true;            
            this.currentTool = tool;
            this.mapDiv.style.cursor = tool.cursorStyle;            
        }
        if(typeof(CBPControl) != "undefined")
        {
	    	var cbpcontent = '<div id="LABELDIV" name="LABELDIV" style="width:400px">';
	    	cbpcontent += '<table style="width:100%">';
	    	cbpcontent += '<tr>';
	    	cbpcontent += '<td style="width:20%;text-align:right;font-size:12px">';
	    	cbpcontent += '标签列表：';
	    	cbpcontent += '</td>';
	    	cbpcontent += '<td id="LABELTD" name="LABELTD" style="width:60%;font-size:12px">';
	    	cbpcontent += '<select style="width:100%;font-size:12px" id="SELELABELID" onchange="LabelTool.prototype.getLabelInfo(this);">';
	    	cbpcontent += '<option value="">请选择……</option>';
	    	cbpcontent += '</select>';
	    	cbpcontent += '</td>';
	    	cbpcontent += '<td>';
	    	cbpcontent += '<input style="font-size:12px" type="button" id="LblManage" value="定义标签" onclick="LabelTool.prototype.labelMaintenance();" readOnly="true"/>';
	    	cbpcontent += '</td>';
	    	cbpcontent += '</tr>';
	    	cbpcontent += '</table>';
	    	cbpcontent += '</div>';
	    	CBPControl.addHtml(cbpcontent);
	       	LabelTool.prototype.getLabels();
	        CBPControl.doShowExpand();
	        CBPControl.panDiv.childNodes[0].src = ImageBaseDir + "panel/arrow1.gif";
	    }
        Event.stop(e);
    },
    getLabels: function()
    {
    	var url = "webgis.MapLabel.getLabels.d";
    	url = encodeURI(url);
    	new Ajax.Request(url,
		{
			method: 'post', 
			parameters: '', 
			onComplete: function(xmlHttp)
			{
				var text = xmlHttp.responseText;
				if(text != "null")
				{
					var xmlDoc = xmlHttp.responseXML;
					LabelTool.prototype.showLabelList(xmlDoc);
				}
			}
		});
    },
    showLabelList: function(xmlDoc)
    {
    	var lbls = xmlDoc.getElementsByTagName("lblinfo");
		if(lbls != null && lbls.length > 0)
		{
			var lbltd = document.getElementById("LABELTD");
			var lblsel = "<select style=\"width:100%;font-size:12px\" id=\"SELELABELID\" onchange=\"LabelTool.prototype.getLabelInfo(this);\">";
			lblsel += "<option value=\"\">请选择...</option>";
			lblsel += "</select>";
			lbltd.innerHTML = lblsel;
			var sel = document.getElementById("SELELABELID");
			for(var i = 0 ; i < lbls.length ; i++)
			{
				var lbl = lbls[i].childNodes;
				var id = lbl[0].text;		
				var title = lbl[1].text;
				var tempOption = document.createElement("OPTION");
				sel.options.add(tempOption);
				tempOption.innerText = title;
				tempOption.value = id;
			}
			return null;
		}
		return null;
    },
    getLabelInfo: function(obj)
    {
    	var index = obj.options.selectedIndex;
    	if(index == 0)
		{
			return null;
		}
		else
		{
			var id = obj.options[index].value;
			var url = "webgis.MapLabel.getLabelInfoById.d";
			var params = "?labelid=" + id;
			url = encodeURI(url);
			params = encodeURI(params);
			new Ajax.Request(url,
			{
				method: 'post', 
				parameters: params, 
				onComplete: function(xmlHttp)
				{
					var text = xmlHttp.responseText;
					if(text != "null")
					{
						var xmlDoc = xmlHttp.responseXML;
						LabelTool.prototype.showSingleLabel(xmlDoc);
					}
				}
			});
			return null;
		}
    },
    showSingleLabel: function(xmlDoc)
    {
    	var lblinfo = xmlDoc.getElementsByTagName("lblinfo")[0].childNodes;	
		var lblx = lblinfo[1].text;
		var lbly = lblinfo[2].text;
		var lbllevel = lblinfo[3].text;
		var ctX = parseFloat(lblx) * 1e16;
		var ctY = parseFloat(lbly) * 1e16;
		var ctPt = new Point(ctX,ctY);
		map.moveToCenter(ctPt,parseFloat(lbllevel));
    	
    },   
    labelMaintenance: function()
    {
    	var objLabel = document.getElementById("LablePanelDiv");
		var objImage = document.getElementById("LableImageDiv");
		if (objLabel){
		    objLabel.parentNode.removeChild(objLabel);
		}
		if (objImage){
		    objImage.parentNode.removeChild(objImage);
		}
		
		var labelPanel = new LabelPanel($('map'));
    }
    
});


/**
 * 
 * @param {Object} e
 * @param {Object} id
 */
function hideWindown(e, id) {
    var obj = $(id);
    obj.style.display = "none";
    Event.stop(e);
}   