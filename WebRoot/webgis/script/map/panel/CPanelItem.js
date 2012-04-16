/**
 * 内容面板按钮条目
 */
CPanelItem = Class.create();

CPanelItem.prototype = {
	/**
	 * 初始化
	 * @param {Object} id
	 * @param {Object} name
	 * @param {Object} bgColor
	 */
	initialize: function(id, imgsrcD, imgsrcA, height){
		this.id   = id;
		this.name = name;
//		this.bgColor = bgColor;
		this.imgsrcD = imgsrcD;
		this.imgsrcA = imgsrcA;
		this.height = height;
	},

	/**
	 * 创建
	 */	
	create: function(){
		this.item = Util.createDiv(this.id);
		this.item.align = "center";
		var html = "<img id='"+this.id+"' src='"+this.imgsrcD+"' align='middle' height='"+this.height+"' width='15' />";
		this.item.innerHTML = html;
//		this.item.style.background = "url(images/panel/rightbtn.gif)";
//		this.item.style.backgroundColor = this.bgColor;
		this.item.style.cursor = "pointer";
	},
	
	activeItem: function(){
		var html = "<img id='"+this.id+"' src='"+this.imgsrcA+"' align='middle' height='"+this.height+"' width='15' />";
		this.item.innerHTML = html;
	},
	deActiveItem: function(){
		var html = "<img id='"+this.id+"' src='"+this.imgsrcD+"' align='middle' height='"+this.height+"' width='15' />";
		this.item.innerHTML = html;
	},
	/**
	 * 获取当前的内容容器.
	 */
	getItem: function(){
		return this.item;
	},
	/**
	 * 
	 */
	getId: function(){
		return this.id;
	}
}