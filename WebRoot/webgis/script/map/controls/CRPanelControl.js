/**
 * 描述:内容面板控制类 
 * 作者:王文江

 */
CRightPanelControl = Class.create();
CRightPanelControl.prototype = Object.extend(new Abstract.Control(), {
	expand: false,
	contentArr: null,
	currWidth:0,
	currExpand:null,
	
    /**
     * 初始化

     * @param {Object} container
     * @param {Object} pwidth
     * @param {Object} btnWidth
     * @param {Object} btnHeight
     */
	initialize: function(container, ovMap, pwidth, btnWidth, btnHeight){
        this.contentArr = new Array();
		this.itemArr = new Array();
		
		this.container = container;
		this.pWidth = pwidth;
		this.btnWidth = btnWidth;
		this.btnHeight = btnHeight;

		this.ovMap = ovMap;//鹰眼图框.

        this.containerWidth  = Util.getValueOfNoPX(container.style.width);
        this.containerHeight = Util.getValueOfNoPX(container.style.height);

        this.drawCRightPanelContainer(container);
    },

    /**
     * 画

     * @param {Object} model
     */
    paint: function(model){
        this.model = model;
		
		this.cPanelDiv.style.left = (this.containerWidth - 22) + "px";
		this.cPanelDiv.style.width = "0px";
		
		this.cPanelDiv.style.zIndex = "10";
		this.cPanelDiv.style.visibility = "visible";
		this.cPanelContentDiv.style.display = "none";	
		this.cPanelDiv.style.height = this.containerHeight - 1;
        this.container.appendChild(this.cPanelDiv);
    },

    /**
     * 画CPANEL容器.
     * @param {Object} container
     */
    drawCRightPanelContainer: function(container){
        var width = this.pWidth;
		var height = this.containerHeight - 1;
		
        var left = this.containerWidth - this.pWidth + 1;
        var top = 0;
        
        //内容面板DIV
		this.id = "rightContentPanel";
		this.cPanelDiv = Util.createDiv(this.id, left, top, width, height, null, 'absolute');
		this.cPanelDiv.style.zIndex = "0";

		//按钮控制区

		this.cPanelButtonDiv = Util.createDiv("CPButton");        
        this.cPanelButtonDiv.style.cssFloat = "left";
		this.cPanelButtonDiv.style.styleFloat = "left";
		this.cPanelButtonDiv.style.padding = "0px 0px 0px 0px";
		this.cPanelButtonDiv.style.margin = "0px";
		this.cPanelButtonDiv.style.width = this.btnWidth;
		this.cPanelButtonDiv.style.height = this.btnHeight;
		this.cPanelButtonDiv.overflow = "hidden";
		this.cPanelButtonDiv.zIndex = "10000";
		
		this.addCtrlBtn(); 
		this.cPanelDiv.appendChild(this.cPanelButtonDiv);
		
		//内容区域., left, top, width, height, null, 'absolute', '3px ridge red');
		this.cPanelContentDiv = Util.createDiv("CPContent", 0, 0, 0, 0, null, null, '1px ridge black');
		this.cPanelContentDiv.style.padding = "0px 0px 0px 0px";
		this.cPanelContentDiv.style.margin = "0px";
		this.cPanelContentDiv.style.height = "100%";
		this.cPanelContentDiv.style.width = "100%";

		this.cPanelDiv.appendChild(this.cPanelContentDiv);
    },
	/**
	 * 画CPANEL容器.
	 * @param {Object} cbpControl
	 */
	setBottomControlPanel:function(cbpControl){
		this.cbpControl = cbpControl;	
	},
	/**
	 * 
	 */
	getContentPanelDiv:function(){
		return this.cPanelDiv;
	},
	
	/**
	 * 增加控制按钮.
	 */
	addCtrlBtn: function(){
		this.ctrlBtnDiv = Util.createDiv("ctrlBtn", 0, 0, 15, 15);
		//this.ctrlBtnDiv.style.backgroundColor = "white";
		var imgsrc = ImageBaseDir + "panel/left.gif";
		this.ctrlImg = Util.createImg('ctrlBtn_Img', 0, 0, 15, 15, imgsrc, 'relative');
		this.ctrlImg.style.cursor = "pointer";
		this.ctrlBtnDiv.appendChild(this.ctrlImg);
		
		this.registerEvent(this.ctrlImg, 'img_', 'click');
		
		this.cPanelButtonDiv.appendChild(this.ctrlBtnDiv);
	},
		
	/**
	 * 增加内容条目.
	 * @param {Object} cpItem
	 */
	addItem: function(cpItem){
		cpItem.create();
		var nowItem = cpItem.getItem();
		this.itemArr[this.itemArr.length] = cpItem;
		
		this.registerEvent(nowItem, 'Item_', 'click');
		
		this.cPanelButtonDiv.appendChild(nowItem);
	},

	/**
	 * 增加内容条目.
	 * @param {Object} cpContent
	 */
	addContent: function(cpContent){
		cpContent.create();
		var nowContent = cpContent.getContent();
		this.contentArr[this.contentArr.length] = nowContent;
		this.cPanelContentDiv.appendChild(nowContent);
		
//		this.ctrlHRDiv = Util.createDiv(null, 0, 0, 20, 2);
//		this.ctrlHRDiv.innerHTML = "<hr />";
//		this.cPanelContentDiv.appendChild(this.ctrlHRDiv);
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
	 * 响应TAB鼠标点击事件
	 * @param {Object} e
	 */
	Item_click: function(e){
		var itemId = "";
		if (e.target){
			itemId = e.target.id;
		}
		else{
			itemId = e.srcElement.id;
		}
		
		this.currItemID = itemId;
		
		this.setStatus(itemId, -1);				
		this.fireExpand(itemId);
		Event.stop(e);
	},
	
	setStatus:function(itemId, index){
		if (this.contentArr != null && this.contentArr != ""){
			for(var i=0; i<this.contentArr.length; i++){
				var tmpId = this.contentArr[i].id;
				if ((tmpId == (itemId + "_content")) || i == index){
					this.contentArr[i].style.display = "";
					this.itemArr[i].activeItem();
				}
				else{
					this.contentArr[i].style.display = "none";
					this.itemArr[i].deActiveItem();
				}
			}
		}
	},
	/**
	 * 触发缩进缩出.
	 */
	fireImgBtnExpand: function(contentItemId){
		if (this.expand == false){
			if (this.contentArr != null && this.contentArr != ""){
				this.setStatus(null, 0);
				var tmpId = this.contentArr[0].id;
				var itemidArr = tmpId.split("_");
				this.currExpand = itemidArr[0];
				this.doExpand();
				this.expand = true;
			}
		}
		else {
			this.setStatus(null, -1);
			this.doDisExpand();
			this.expand = false;
			this.currExpand = null;
		}
	},
	/**
	 * 响应按钮事件
	 * @param {Object} e
	 */
	img_click: function(e){

		this.currItemID = this.itemArr[0].id;

		this.fireImgBtnExpand();
		Event.stop(e);
	},	
	/**
	 * 触发缩进缩出.
	 */
	fireExpand: function(itemId){
		if (this.expand == false){
			this.doExpand();
			this.currExpand = itemId;
			this.expand = true;
		}
		else {
			if (this.currExpand == itemId){
				this.doDisExpand();
				this.expand = false;
			}
			else{
				this.currExpand = itemId;
				this.expand = true;
			}
		}
	},
	/**
	 * 展开
	 */
	doExpand: function(){
		//取得内容面板的左值.
		var leftpx = this.cPanelDiv.style.left;
		var leftnopx = Util.getValueOfNoPX(leftpx);

	
		this.ctrlImg.src = ImageBaseDir+"panel/right.gif";
		this.ctrlImg.style.width = "15";
		this.ctrlImg.style.height = "15";
		this.ctrlImg.align = "middle";
				
		this.cPanelDiv.style.width = this.pWidth + "px";
		this.cPanelDiv.style.left = (this.containerWidth - this.pWidth) + "px";
		this.cPanelContentDiv.style.display = "";
		
		this.ovMap.moveX(-this.pWidth, "left");
		this.currWidth = this.pWidth;
		if(this.cbpControl)
		{
			this.cbpControl.fireDynamicWidth();
		}
	},
	/**
	 * 缩进
	 */
	doDisExpand: function(){
		var leftpx = this.cPanelDiv.style.left;
		var leftnopx = Util.getValueOfNoPX(leftpx);
		

		this.ctrlImg.src = ImageBaseDir+"panel/left.gif";


		this.cPanelDiv.style.left = (this.containerWidth - 22) + "px";
		this.cPanelDiv.style.width = "0px";
		this.cPanelContentDiv.style.display = "none";		

		this.ovMap.moveX(this.pWidth, "right");
		this.currWidth = 0;
		if(this.cbpControl)
		{
			this.cbpControl.fireDynamicWidth();
		}
	},
	/**
	 * 取得当前右侧内容区域的宽度.
	 */
	getCurrWidth: function(){
		return this.currWidth;
	},
	
	/**
	 * 获取当前活动条目.
	 */
	getCurrItemID: function(){
		return this.currItemID;
	}
	
});