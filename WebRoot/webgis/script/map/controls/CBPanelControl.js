/**
 * 底部的控制面板,用来加载内容.
 **/
CBPanelControl = Class.create();

CBPanelControl.prototype = Object.extend(new Abstract.Control(), {
    /**
     * 
     * @param {Object} container
     * @param {Object} pHeight
     * @param {Object} ovMap
     * @param {Object} cpControl
     */
	initialize: function(container, pWidth, pHeight, ovMap, crpControl){
        this.container = container;
        this.pWidth = pWidth;
		this.pHeight = pHeight;
		this.ovMap = ovMap;
		this.crpControl = crpControl;
		this.extX = 22;
		this.ovMapDiv = this.ovMap.getOvMapDiv();
        this.drawCBPContainer(container);
    },
    /**
     * 
     * @param {Object} model
     */
    paint: function(model){
        this.model = model;
        this.container.appendChild(this.bPanelDiv);
    },
    /**
     * 
     * @param {Object} container
     */
    drawCBPContainer: function(container){
        this.containerWidth = Util.getValueOfNoPX(container.style.width);
        this.containerHeight = Util.getValueOfNoPX(container.style.height);
        
        var width = this.pWidth;
        var height = this.pHeight;
        
        var left = 0;
        var top  = this.containerHeight + 1;
        
        this.id = Util.createUniqueID('Bottom_');
        this.bPanelDiv = Util.createDiv(this.id, left, top, width, height, null, 'absolute', '1px ridge black');
        this.bPanelDiv.style.overflow = 'hidden';
        this.bPanelDiv.style.display = 'none';        
        this.panDiv = Util.createDiv(Util.createUniqueID('Bottom_Pan_'), null, null, null, null, ImageBaseDir + 'panel/arrow2.gif', 'absolute');
        this.panDiv.style.zIndex = 100000;
        this.panDiv.style.left = 0;//this.containerWidth - 21;
        this.panDiv.style.top = this.containerHeight - 25;
        this.panDiv.style.cursor = "hand";
        this.container.appendChild(this.panDiv);
        this.registerEvent(this.panDiv, 'pan_', 'click');
    },
	/**
	 * 
	 */
    getCurrHeight:function(){
		return 	Util.getValueOfNoPX(this.bPanelDiv.style.height);	
	},
	/**
	 * 
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
     * 
     * @param {Object} e
     */
    pan_click: function(e){
 		this.fExpand(Util.getValueOfNoPX(this.bPanelDiv.style.width) - 1, Util.getValueOfNoPX(this.bPanelDiv.style.height) - 1, this.panDiv.childNodes[0]);
		Event.stop(e);
    },

	/**
	 * 
	 * @param {Object} w
	 * @param {Object} h
	 * @param {Object} img
	 */
	fExpand: function (w, h, img){
	    if (this.bPanelDiv.style.display == "inline") {
	        this.doExpand(0, this.containerHeight);
	        img.src = ImageBaseDir + 'panel/arrow2.gif';
	    }
	    else {
	        this.doExpand(0, this.containerHeight - h);
	        img.src = ImageBaseDir + 'panel/arrow1.gif';
	    }
	},

	/**
	 * 
	 * @param {Object} left
	 * @param {Object} top
	 */
	doExpand: function (left, top){
		this.bPanelDiv.style.left = left;
		this.bPanelDiv.style.top = top;
		
		
		if(Util.isIE()){
			//如果有右侧面板则在右侧面板展开时减去右侧面板宽度 by fengd 2009.5.25
			if(this.crpControl != null)
			{
				this.bPanelDiv.style.width = this.pWidth - this.crpControl.getCurrWidth() + this.extX + 1;
			}
			else
			{
				this.bPanelDiv.style.width = this.pWidth;
			}
		}
		else{
			//如果有右侧面板则在右侧面板展开时减去右侧面板宽度 by fengd 2009.5.25
			if(this.crpControl != null)
			{
				this.bPanelDiv.style.width = this.pWidth - this.crpControl.getCurrWidth() - 5;	
			}
			else
			{
				this.bPanelDiv.style.width = this.pWidth - 5;
			}
		}
		
		if (this.bPanelDiv.style.display == "none"){
			this.bPanelDiv.style.display = "inline";
		}
		else {
			this.bPanelDiv.style.display = "none";
		}			
	},	
	
	doShowExpand: function()
	{
		this.bPanelDiv.style.left = 0;
		this.bPanelDiv.style.top = this.containerHeight - this.pHeight;
		
		
		if(Util.isIE()){
			//如果有右侧面板则在右侧面板展开时减去右侧面板宽度 by fengd 2009.5.25
			if(this.crpControl != null)
			{
				this.bPanelDiv.style.width = this.pWidth - this.crpControl.getCurrWidth() + this.extX + 1;
			}
			else
			{
				this.bPanelDiv.style.width = this.pWidth;
			}
		}
		else{
			//如果有右侧面板则在右侧面板展开时减去右侧面板宽度 by fengd 2009.5.25
			if(this.crpControl != null)
			{
				this.bPanelDiv.style.width = this.pWidth - this.crpControl.getCurrWidth() - 5;	
			}
			else
			{
				this.bPanelDiv.style.width = this.pWidth - 5;
			}
		}
		
		if (this.bPanelDiv.style.display == "none"){
			this.bPanelDiv.style.display = "inline";
		}
	},
	
	/**
	 * 增加内容条目.
	 * @param {Object} cpContent
	 */
	addContent: function(cpContent){
		cpContent.create();
		this.cpContentDiv = cpContent.getContent();
		this.bPanelDiv.appendChild(this.cpContentDiv);
	},
	
	addHtml: function(html){
		this.cpContentDiv.innerHTML = html;
	},
	
	/**
	 * 设置自增宽度.
	 * @param {Object} deltaW
	 */
	fireDynamicWidth: function(){
		if(Util.isIE()){
			this.bPanelDiv.style.width = this.containerWidth - this.crpControl.getCurrWidth() + this.extX + 1;
		}
		else{
			this.bPanelDiv.style.width = this.containerWidth - this.crpControl.getCurrWidth() - 5;	
		}
	}		
});