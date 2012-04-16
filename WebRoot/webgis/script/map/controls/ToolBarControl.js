ToolBarControl = Class.create();
ToolBarControl.prototype = {
    EVENT_TYPES: ["mouseover", "mouseout", "mousemove", "mousedown", "mouseup", "dblclick", "click"],
    
    initialize: function(container){
    	this.barlength = 0;
        this.container = container;
        this.bar = Util.createDiv(Util.createUniqueID('Tool_'));
        this.tools = new Object();
        this.currentTool = null;
		this.model = null;
    },
	
    //画工具条.
	paint: function(model){
		this.model = model;
        this.bar.style.position = "relative";
		this.bar.style.zIndex = 100;		
        this.bar.style.left = -30;
		this.bar.style.top  = -40;
		
		this.container.appendChild(this.bar);
	},
	
    addTool: function(tool, isDefault){
        if (!tool){
			return;
		} 
        this.tools[tool.id] = tool;
        this.bar.appendChild(tool.div);
        if (isDefault) {
            this.currentTool = tool;
            this.defaultTool = tool;
        }
        
        //注册工具栏按钮事件
        Event.observe(tool.div, "mouseout", tool.barMouseOutHandler.bindAsEventListener(this));
        Event.observe(tool.div, "mouseover", tool.barMouseOverHandler.bindAsEventListener(this));
        Event.observe(tool.div, "click", tool.barClickHandler.bindAsEventListener(this));
        this.barlength = this.barlength + 1;
    },
    
    removeTool: function(divid)
    {
    	
    	if(divid != null && Util.trim(divid) != '')
    	{
    		if(this.barlength > 0)
    		{
    			this.bar.removeChild(this.tools[divid].div);
    			this.barlength = this.barlength - 1;
    		}
    	}
    	return null;
    },
    
    addCommand: function(cmd){
        if (!cmd){ 
            return;
        }
            
        this.tools[cmd.id] = cmd;
        this.bar.appendChild(cmd.div);
        
        //注册工具栏按钮事件；
        Event.observe(cmd.div, "mouseout", cmd.cmdMouseOutHandler.bindAsEventListener(this));
        Event.observe(cmd.div, "mouseover", cmd.cmdMouseOverHandler.bindAsEventListener(this));
        Event.observe(cmd.div, "click", cmd.cmdClickHandler.bindAsEventListener(this));
        this.barlength = this.barlength + 1;
    },
    
    setMapModel: function(model){
        this.model = model;
    },
    
    clearCurrentToolStatus: function(){
        var toolDivs = this.bar.childNodes;
        for (var i = 0; i < toolDivs.length; i++) {
            var tool = this.tools[toolDivs[i].id];
            if (tool.selected == true) {
                tool.selected = false;
                toolDivs[i].childNodes[0].src = tool.img_normal;
            }
        }
    },
    
    registerEventToMap: function(mapDiv){
        this.mapDiv = mapDiv;
        Event.observe(mapDiv, "mousedown", this.mapMouseDownHandler.bindAsEventListener(this));
        Event.observe(mapDiv, "mousemove", this.mapMouseMoveHandler.bindAsEventListener(this));
        Event.observe(mapDiv, "mouseup", this.mapMouseUpHandler.bindAsEventListener(this));
        Event.observe(mapDiv, "dblclick", this.mapDblclickHandler.bindAsEventListener(this));
        Event.observe(mapDiv, "click", this.mapClickHandler.bindAsEventListener(this));       
     },
    checkNav:function(e){
    	var currElement = Event.element(e);
    	var currId = currElement.id;
    	if (currId == "" || currId == "map"  || currId == "vmlDiv" || currId == "zoomBox"){
	   		return false;
    	}

		if (currId != null){
			var tmpIdArr = currId.split("_");
	    	if (tmpIdArr[0] == "sliderbar"){
	    		return true;
	    	}
    	}

    	return true;
    },

    mapMouseDownHandler: function(e){
        if (this.currentTool == null || this.tools[this.currentTool.id].toolType == "Command"){ 
            return;
        }    
        this.tools[this.currentTool.id].mouseDownHandler(e, this);
    },
    
    mapMouseMoveHandler: function(e){
        if (this.currentTool == null || this.tools[this.currentTool.id].toolType == "Command"){ 
            return;
        }
        this.tools[this.currentTool.id].mouseMoveHandler(e, this);
    },
    
    mapMouseUpHandler: function(e){
        if (this.currentTool == null || this.tools[this.currentTool.id].toolType == "Command"){ 
            return;
        }    
        this.tools[this.currentTool.id].mouseUpHandler(e, this);
    },
    
    mapClickHandler: function(e){
        if (this.currentTool == null || this.tools[this.currentTool.id].toolType == "Command"){ 
            return;
        }    
        this.tools[this.currentTool.id].clickHandler(e, this);
    },
    
    mapDblclickHandler: function(e){
        if (this.currentTool == null || this.tools[this.currentTool.id].toolType == "Command"){ 
            return;
        }    
        this.tools[this.currentTool.id].dblClickHandler(e, this);
    },
    
    /**
     * 加载缺省工具条.
     * add by lbh
     */
    addDefaultToolBar:function(imgPath)
	{
	    imgPath = imgPath + 'mapTools/';
	    this.addTool(new ZoominTool('zoomin',imgPath + 'zoomin1.gif',imgPath + 'zoomin2.gif',imgPath + 'zoomin3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    this.addTool(new ZoomoutTool('zoomout',imgPath + 'zoomout1.gif',imgPath + 'zoomout2.gif',imgPath + 'zoomout3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    this.addTool(new PanTool('pan',imgPath + 'pan1.gif',imgPath + 'pan2.gif',imgPath + 'pan3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25), true)
	    this.addTool(new MeasureTool('measure',imgPath + 'measure1.gif',imgPath + 'measure2.gif',imgPath + 'measure3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
		this.addTool(new LabelTool('labeltool',imgPath + 'label1.gif',imgPath + 'label2.gif',imgPath + 'label3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
		this.addCommand(new ClearCmd('clear',imgPath + 'clear1.gif',imgPath + 'clear2.gif',imgPath + 'clear3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    this.addCommand(new FullCmd('full',imgPath + 'full1.gif',imgPath + 'full2.gif',imgPath + 'full3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    this.addCommand(new SearchCmd('browse',imgPath + 'browse1.gif',imgPath + 'browse2.gif',imgPath + 'browse3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    this.addCommand(new PrevCmd('back',imgPath + 'back1.gif',imgPath + 'back2.gif',imgPath + 'back3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    this.addCommand(new NextCmd('forward',imgPath + 'forward1.gif',imgPath + 'forward2.gif',imgPath + 'forward3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
	    //Modified by Liuwx 2012-1-5
	    //新增一个“更多专题图”的按钮
		this.addCommand(new ShowMoreMap('showMore',imgPath + 'browse1.gif',imgPath + 'browse2.gif',imgPath + 'browse3.gif','absolute',1+toolInterval*(this.barlength+1),35,28,25))
		//Modification finished
	},
    
    /**
     * 转向移屏.
     * add by wwj
     */
    toPan: function(){
        this.clearCurrentToolStatus();
        var tool = this.tools['pan'];        
        tool.div.childNodes[0].src = tool.img_down;
        if(!tool.selected){
            tool.selected = true;            
            this.currentTool = tool;
            this.mapDiv.style.cursor = tool.cursorStyle;            
        }  
    }    
};