/**
 * 内容面板控制类.
 */
ContentPanel = Class.create();

ContentPanel.prototype = {
	expand: false,
	yOffset: 0,
	menuWidth: 300,
	
	/**
	 * 初始化
	 */
	initialize: function(){
		
	},
	/**
	 * 创建	
	 */
	create: function(){
		var cp = document.getElementById("contentPanel");
		//如果是IE,需要减去20,FF不需要.
		cp.style.left = (document.body.clientWidth - 22) + "px";
		cp.style.width = "0px";
		cp.style.visibility = "visible";
		document.getElementById("middle").style.display = "none";		
	},
	
	/**
	 * 展开
	 */
	doExpand: function(){
		//取得内容面板的左值.
		var leftpx = document.getElementById("contentPanel").style.left;
		var leftnopx = Util.getValueOfNoPX(leftpx);
		
		var cw = document.body.clientWidth;
		document.getElementById("contentPanel").style.width = this.menuWidth + "px";
		document.getElementById("contentPanel").style.left = (cw - this.menuWidth) + "px";
		document.getElementById("middle").style.display = "";
	},
	
	/**
	 * 缩进
	 */
	doDisExpand: function(){
		var leftpx = document.getElementById("contentPanel").style.left;
		var leftnopx = Util.getValueOfNoPX(leftpx);
		
		document.getElementById("contentPanel").style.left = (document.body.clientWidth - 22) + "px";
		document.getElementById("contentPanel").style.width = "0px";
		document.getElementById("middle").style.display = "none";		
	},
	
	/**
	 * 响应事件
	 * @param {Object} bResize
	 */
	fexpand: function(bResize){
		if (this.expand == false){
			if (bResize){
				this.doDisExpand();
				this.expand = false;
			}
			else{
				this.doExpand();
				this.expand = true;
			}
		}
		else{
			if (bResize){
				this.doExpand();
				this.expand = true;
			}
			else{
				this.doDisExpand();
				this.expand = false;
			}
		}
	}
}