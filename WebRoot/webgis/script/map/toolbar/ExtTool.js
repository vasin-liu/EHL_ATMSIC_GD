/**
 * 扩展工具抽象类
 */
Abstract.ExtTool = function(){};
Abstract.ExtTool.prototype = {
    /**
     * 初始化
     */
	initialize: function(id){
        this.toolType = "ExtTool";        
        this.id = id;
        this.MARK_FLAG = false;
        
        this.mapDiv = map.getMapDiv();
        this.vmlDiv = map.getVMLDiv();
        this.registerEventToMap();
    },
    
    /**
     * 注册事件.
     */
    registerEventToMap: function(){
        this.mapDiv = map.getMapDiv();
        Event.observe(this.mapDiv, "mousedown", this.mouseDownHandler.bindAsEventListener(this));
        Event.observe(this.mapDiv, "mousemove", this.mouseMoveHandler.bindAsEventListener(this));
        Event.observe(this.mapDiv, "mouseup", this.mouseUpHandler.bindAsEventListener(this));
        Event.observe(this.mapDiv, "dblclick", this.dblClickHandler.bindAsEventListener(this));
        Event.observe(this.mapDiv, "click", this.clickHandler.bindAsEventListener(this));
    },    

    /**
     * MOUSEDOWN
     */
	mouseDownHandler: function(e) {
    },

    /**
     * MOUSEMOVE
     */  
    mouseMoveHandler: function(e){
    },

    /**
     * MOUSEUP
     */  
    mouseUpHandler: function(e){
    },

    /**
     * CLICK
     */
    clickHandler: function(e){
       Event.stop(e);
    },

    /**
     * DBLCLICK
     */
    dblClickHandler: function(e){
    },

    /**
     * BUTTONCLICK
     */   
    extClickHandler: function(e){
        map.getMapDiv().style.cursor = this.cursorStyle;            
        Event.stop(e);
    },
    
    /**
     * 转向移屏.
     * add by wwj
     */
    toPan: function(bPan){
        toolbar.clearCurrentToolStatus();
        var tool = toolbar.tools['pan'];        
        tool.div.childNodes[0].src = tool.img_down;
        tool.selected = bPan;            
        toolbar.currentTool = tool;
        this.mapDiv.style.cursor = tool.cursorStyle;            
    }    
};

function testtt(){
	alert("test");
}
/**
 * 画线
 */
ExtMeasureTool = Class.create();
ExtMeasureTool.prototype = Object.extend(new Abstract.ExtTool(), {
    isDrag: false,
    selected: false,        
    cursorStyle:'url("images/mea.cur")',
    alt: '画多义线',
    measure: new Array(),
    /**
     * 
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseDownHandler: function(e){
    	if (this.MARK_FLAG){
	        this.mapDiv = map.getMapDiv();
	        this.mouseDownPixel = Util.getMouseRelativePixel(e, this.mapDiv);
	        
	        if(!this.isDrag){
				this.isDrag = true;
			}
	            
	        this.lastX = this.mouseDownPixel.x;   
	        this.lastY = this.mouseDownPixel.y;
	        
	        this.nowName = "virLine";
			
	        this.line  = '<v:line to="0,0" strokecolor="red" strokeweight="5pt" style="position:absolute;" onmousedown="testtt()"></v:line>';
	        this.vLine = document.createElement(this.line);
			//this.vLine.onmousedown = null;
			//this.vLine.onmouseup = null;
			//this.vLine.onmousemove = null;
//			this.vLine.style.left = "-1px";   
//			this.vLine.style.top  = "-1px";   
			
			this.vLine.from = this.lastX + "," + this.lastY;
			this.vLine.to = this.mouseDownPixel.x + "," + this.mouseDownPixel.y;
			
   	        this.vLine.name = this.nowName;
			
	        this.mapDiv.appendChild(this.vLine);
			
	        var coord = Util.getCoordinateByPixel(this.mouseDownPixel, map.getCurrentZoom());
	        this.measure.push(new Point(coord.x/1e16, coord.y/1e16));
    	}
    	Event.stop(e);
    }, 
	clickwwjHandler:function(e){
		alert("wwj");
		//window.status = this.id;
	},

    /**
     * @param {Object} e
     * @param {Object} toolbar
     */
    mouseMoveHandler: function(e){
		if (this.MARK_FLAG){
	        if(!this.isDrag){
				return;
			}
			
	        this.mouseMovePixel = Util.getMouseRelativePixel(e, this.mapDiv);
	        if (this.mouseMovePixel.x > this.lastX && this.mouseMovePixel.y > this.lastY){
	        	this.vLine.style.left = "-3px";   
				this.vLine.style.top = "-3px"; 
	        }
	        else if (this.mouseMovePixel.x > this.lastX && this.mouseMovePixel.y < this.lastY){
	        	this.vLine.style.left = "-1px";   
				this.vLine.style.top = "1px"; 
	        }
	        else if (this.mouseMovePixel.x < this.lastX && this.mouseMovePixel.y < this.lastY){
	        	this.vLine.style.left = "1px";   
				this.vLine.style.top = "1px"; 
	        }
	        else if (this.mouseMovePixel.x < this.lastX && this.mouseMovePixel.y > this.lastY){
	        	this.vLine.style.left = "1px";   
				this.vLine.style.top = "-1px"; 
	        }
	         
	        this.vLine.to = this.mouseMovePixel.x + "," + this.mouseMovePixel.y;
        }
        Event.stop(e);
    },
    /**
     * @param {Object} e
     * @param {Object} toolbar
     */
    dblClickHandler: function(e){
	    if (this.MARK_FLAG){
	        
	        if(!this.isDrag){
	            return;  
	        }   
	        this.toPan(false);
	        var virLine = document.getElementsByName(this.nowName);
	        for (var k=0; k<virLine.length; k++){
		        this.mapDiv.removeChild(virLine[k]);	
	        }
        
	        var pline = new Polyline("measure", this.measure, "blue", 1, null, null, null, null, true);
	        var vvLine = pline.setToMap(this.vmlDiv, map.getMapModel());
	        Event.observe(vvLine, "click", this.clickwwjHandler.bindAsEventListener(vvLine));
	        
	        this.measure = new Array();
	        
	        this.isDrag = false;        
	        
	        this.MARK_FLAG = false;
	        this.toPan(true);
	    } 
	    Event.stop(e);   
    }
});