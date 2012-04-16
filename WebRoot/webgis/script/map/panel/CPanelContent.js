/**
 * 内容面板内容区
 */
CPanelContent = Class.create();

CPanelContent.prototype = {

	/**
	 * 初始化
	 * @param {Object} id
	 * @param {Object} bgcolor
	 * @param {Object} text
	 */
	initialize: function(id, bgcolor, text){
		this.id   = id;
		this.bgcolor = bgcolor;
		this.text = text;
	},

	/**
	 * 创建
	 */
	create: function(){
		this.contentDiv = Util.createDiv(this.id);
		this.contentDiv.innerHTML = this.text;
		this.contentDiv.style.backgroundColor = this.bgcolor;
		
		this.contentDiv.style.width = "100%";
		this.contentDiv.style.height = "100%";
	},
	
	/**
	 * 获取当前的内容容器.
	 */
	getContent: function(){
		return this.contentDiv;
	}
}