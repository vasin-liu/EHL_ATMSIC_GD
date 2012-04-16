MapTips = Class.create();
MapTips.prototype =
{
    /**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container)
    {
        this.container = container;        
    },
    
	/*
	 *　显示加载提示
	 */
	showLoadTips:function(container,x,y){
		//先查询是否包含此弹出气泡
	    var divPopup = document.getElementById("popup");
	    if(divPopup != null){  
			divPopup.parentNode.removeChild(divPopup);
		}  
		
	    //然后创建显示DIV	
	    if(typeof(document.createElementNS) != 'undefined')
		{
			divPopup = document.createElementNS('http://www.w3.org/1999/xhtml', "div");
		}
	    if(typeof(document.createElement)!= 'undefined') 
	    {
			divPopup = document.createElement("div");
	    }    
		divPopup.id = "popup";
		divPopup.position ="absolute";
		divPopup.zIndex = 10001;
		container.appendChild(divPopup);		
		  
		//取得鼠标的绝对坐标
		x = x;
		y = y - 18;
						
		//显示正在查询图片
		divPopup.innerHTML='<img src="' + ImageBaseDir + 'bubble/loading.gif" alter="正在加载..." style="top:' + y  + 'px; left:' + x + 'px;border=0" class="popup">';
	},
	
	/**
	 ** 显示气泡窗体
	 */
	showPopup:function(container, title, contents, x, y){
	
		//先查询是否包含此弹出气泡
	    var divPopup = document.getElementById("popup");
	    if(divPopup != null){  
			divPopup.parentNode.removeChild(divPopup);
		}
		
	    //然后创建显示DIV	
		divPopup = document.createElement("div");
		divPopup.id = "popup";
		divPopup.position ="absolute";
		divPopup.zIndex = 10001;
		container.appendChild(divPopup);
		    
		//显示提示内容		
	    divPopup.innerHTML='\
	          <table id="poptable" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
	            <tbody>\
	              <tr>\
	                <td class="corner" id="topleft"></td>\
	                <td class="top">' + title + '</td>\
	                <td class="top" align="right" valign="middle"><img src="' + ImageBaseDir +'bubble/close.gif" border="0" onclick="MapTips.prototype.hidePopup(true);"></td>\
	                <td class="corner" id="topright"></td>\
	              </tr>\
	              <tr>\
	                <td class="left"></td>\
	                <td id="popup-contents" bgcolor="#C4E9FF" colspan="2">' + contents + '</td>\
	                <td class="right"></td>\
	              </tr>\
	              <tr>\
	                <td id="bottomleft" class="corner"></td>\
	                <td class="bottom" colspan="2"><img src="' + ImageBaseDir + 'bubble/bubble-tail2.gif"></td>\
	                <td class="corner" id="bottomright"></td>\
	              </tr>\
	            </tbody>\
	          </table>';

	    var contentTB = document.getElementById("poptable");   	
	    contentTB.style.top = ((y - contentTB.clientHeight || contentTB.offsetHeight)) + 'px';
	    contentTB.style.left = (x - (contentTB.clientWidth || contentTB.offsetWidth) /2 ) + 'px';
	},

	
	/**
	 ** 隐藏气泡窗体
	 */
	hidePopup:function(isClose){
		
		if(!isClose) return;

		var divPopup = document.getElementById("popup");
		if((typeof(divPopup) != "undefined") && (divPopup != null))
		{
	    	divPopup.parentNode.removeChild(divPopup);
	    }
	}
};

addTileChangeListener("onMapScaleEvent", "MapTips.prototype.hidePopup()");