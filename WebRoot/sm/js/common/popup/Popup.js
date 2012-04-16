Popup = Class.create();
//机构选择基本路径，定义与机构选择DepartmentSelect.js文件中
//在本js之前引入DepartmentSelect.js文件
window.$DEPT_BASE_PATH = window.$DEPT_BASE_PATH || "../../../sm/";
Popup.prototype =
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
	showTips:function(container,msg)
	{
		//查询是否包含加载提示图层
	    var divTips = document.getElementById("showTips");
	    if(divTips != null)
		{  
			divTips.parentNode.removeChild(divTips);
		}  
		
	    //创建显示DIV	
		divTips = document.createElement("div");
		divTips.id = "showTips";
		divTips.style.position ="absolute";
		divTips.style.zIndex = 10001;
		divTips.style.left = 300;
		divTips.style.top = 250;
		  						
		//显示加载信息
		var title = "信息提示";
		var contents="";
		if(msg&&msg!=""){
			contents = msg;
		}else{
			contents = "正在加载数据！请稍候．．．";
		}	
	    divTips.innerHTML='\
          <table id="poptable" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
            <tbody>\
              <tr>\
                <td class="corner" id="topleft"></td>\
                <td class="top">' + title + '</td>\
                <td class="top" align="right" valign="middle"></td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr>\
                <td class="left"></td>\
                <td id="popup-contents" height="50" bgcolor="#DFEAF7" colspan="2">&nbsp;' + contents + '&nbsp;</td>\
                <td class="right"></td>\
              </tr>\
              <tr>\
                <td id="bottomleft" class="corner"></td>\
                <td class="bottom" colspan="2"></td>\
                <td class="corner" id="bottomright"></td>\
              </tr>\
            </tbody>\
          </table>';	       
		container.appendChild(divTips);		
	},

	/**
	 ** 隐藏加载提示
	 */
	hideTips:function()
	{
		var divTips = document.getElementById("showTips");
		if((typeof(divTips) != "undefined") && (divTips != null))
		{
	    	divTips.parentNode.removeChild(divTips);
	    }
	},
    	
	/**
	 ** 显示气泡窗体
	 */
	showPopup:function(container,title,contents)
	{
		var oContainer = document.getElementById(container);
		
		//创建弹出div
	    var divPopup = document.getElementById("popup");
	   
	    if(divPopup != null)
		{  
			divPopup.parentNode.removeChild(divPopup);
		}
		
		divPopup = document.createElement("div");
		divPopup.id = "popup";
		divPopup.style.position ="absolute";		
		divPopup.style.zIndex = 10000;
		divPopup.style.left="0";
		divPopup.style.top="0";
		divPopup.style.width="100%";
		divPopup.style.height="100%";

		//创建弹出iframe
	    var coverFrame = document.getElementById("cover");
	   
	    if(coverFrame != null)
		{  
			coverFrame.parentNode.removeChild(coverFrame);
		}
		
		coverFrame = document.createElement("iframe");
		coverFrame.id = "cover";
		coverFrame.position ="absolute";
		coverFrame.zIndex = -1;
		coverFrame.src = "#";
		coverFrame.frameborder = 0;
		coverFrame.style.width="250";
		coverFrame.style.height="320";
		coverFrame.style.top="0";
		coverFrame.style.left="0";
		coverFrame.style.scrolling="no";
		coverFrame.style.display="inline";
				
		oContainer.appendChild(coverFrame);
		oContainer.appendChild(divPopup);

	    divPopup.innerHTML='\
          <table id="poptable" width="100%" height="100%" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
            <tbody>\
              <tr height="5%" class="scrollColThead">\
                <td class="corner" id="topleft"></td>\
                <td class="top">' + title + '</td>\
                <td class="top" align="right" valign="middle">\
                	<img src="'+$DEPT_BASE_PATH+'image/popup/ok.gif" border="0" style="cursor:pointer" onclick="saveSelect();">\
                	<img src="'+$DEPT_BASE_PATH+'image/popup/cancel.gif" border="0" style="cursor:pointer" onclick="cancelSelect();">\
                </td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr height="92%" valign="top">\
                <td class="left"></td>\
                <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                   <div style="weight:240;height:298;overflow:auto">' + contents + '</div>\
                </td>\
                <td class="right"></td>\
              </tr>\
              <tr height="3%">\
                <td id="bottomleft" class="corner"></td>\
                <td class="bottom" colspan="2"></td>\
                <td id="bottomright" class="corner"></td>\
              </tr>\
            </tbody>\
          </table>';
	},

	/**
	 ** 隐藏气泡窗体
	 */
	hidePopup:function(oContainer)
	{
		var divPopup = document.getElementById("popup");
		if((typeof(divPopup) != "undefined") && (divPopup != null))
		{
	    	divPopup.parentNode.removeChild(divPopup);
	    }

		var coverFrame = document.getElementById("cover");
		if((typeof(coverFrame) != "undefined") && (coverFrame != null))
		{
	    	coverFrame.parentNode.removeChild(coverFrame);
	    }
	  
	    var deptDiv = document.getElementById("deptDiv")
	    if((typeof(deptDiv) != "undefined") && (deptDiv != null))
		 {
	    	deptDiv.parentNode.removeChild(deptDiv);

	     }
	}
};
Popup = Class.create();
//机构选择基本路径，定义与机构选择DepartmentSelect.js文件中
//在本js之前引入DepartmentSelect.js文件
window.$DEPT_BASE_PATH = window.$DEPT_BASE_PATH || "../../../sm/";
Popup.prototype =
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
	showTips:function(container,msg)
	{
		//查询是否包含加载提示图层
	    var divTips = document.getElementById("showTips");
	    if(divTips != null)
		{  
			divTips.parentNode.removeChild(divTips);
		}  
		
	    //创建显示DIV	
		divTips = document.createElement("div");
		divTips.id = "showTips";
		divTips.style.position ="absolute";
		divTips.style.zIndex = 10001;
		divTips.style.left = 300;
		divTips.style.top = 250;
		  						
		//显示加载信息
		var title = "信息提示";
		var contents="";
		if(msg&&msg!=""){
			contents = msg;
		}else{
			contents = "正在加载数据！请稍候．．．";
		}	
	    divTips.innerHTML='\
        <table id="poptable" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
          <tbody>\
            <tr>\
              <td class="corner" id="topleft"></td>\
              <td class="top">' + title + '</td>\
              <td class="top" align="right" valign="middle"></td>\
              <td class="corner" id="topright"></td>\
            </tr>\
            <tr>\
              <td class="left"></td>\
              <td id="popup-contents" height="50" bgcolor="#DFEAF7" colspan="2">&nbsp;' + contents + '&nbsp;</td>\
              <td class="right"></td>\
            </tr>\
            <tr>\
              <td id="bottomleft" class="corner"></td>\
              <td class="bottom" colspan="2"></td>\
              <td class="corner" id="bottomright"></td>\
            </tr>\
          </tbody>\
        </table>';	       
		container.appendChild(divTips);		
	},

	/**
	 ** 隐藏加载提示
	 */
	hideTips:function()
	{
		var divTips = document.getElementById("showTips");
		if((typeof(divTips) != "undefined") && (divTips != null))
		{
	    	divTips.parentNode.removeChild(divTips);
	    }
	},
  	
	/**
	 ** 显示气泡窗体
	 */
	showPopup:function(container,title,contents)
	{
		var oContainer = document.getElementById(container);
		
		//创建弹出div
	    var divPopup = document.getElementById("popup");
	   
	    if(divPopup != null)
		{  
			divPopup.parentNode.removeChild(divPopup);
		}
		
		divPopup = document.createElement("div");
		divPopup.id = "popup";
		divPopup.style.position ="absolute";		
		divPopup.style.zIndex = 10000;
		divPopup.style.left="0";
		divPopup.style.top="0";
		divPopup.style.width="100%";
		divPopup.style.height="100%";

		//创建弹出iframe
	    var coverFrame = document.getElementById("cover");
	   
	    if(coverFrame != null)
		{  
			coverFrame.parentNode.removeChild(coverFrame);
		}
		
		coverFrame = document.createElement("iframe");
		coverFrame.id = "cover";
		coverFrame.position ="absolute";
		coverFrame.zIndex = -1;
		coverFrame.src = "#";
		coverFrame.frameborder = 0;
		coverFrame.style.width="250";
		coverFrame.style.height="320";
		coverFrame.style.top="0";
		coverFrame.style.left="0";
		coverFrame.style.scrolling="no";
		coverFrame.style.display="inline";
				
		oContainer.appendChild(coverFrame);
		oContainer.appendChild(divPopup);

	    divPopup.innerHTML='\
        <table id="poptable" width="100%" height="100%" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
          <tbody>\
            <tr height="5%" class="scrollColThead">\
              <td class="corner" id="topleft"></td>\
              <td class="top">' + title + '</td>\
              <td class="top" align="right" valign="middle">\
              	<img src="'+$DEPT_BASE_PATH+'image/popup/ok.gif" border="0" style="cursor:pointer" onclick="saveSelect();">\
              	<img src="'+$DEPT_BASE_PATH+'image/popup/cancel.gif" border="0" style="cursor:pointer" onclick="cancelSelect();">\
              </td>\
              <td class="corner" id="topright"></td>\
            </tr>\
            <tr height="92%" valign="top">\
              <td class="left"></td>\
              <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                 <div style="weight:240;height:298;overflow:auto">' + contents + '</div>\
              </td>\
              <td class="right"></td>\
            </tr>\
            <tr height="3%">\
              <td id="bottomleft" class="corner"></td>\
              <td class="bottom" colspan="2"></td>\
              <td id="bottomright" class="corner"></td>\
            </tr>\
          </tbody>\
        </table>';
	},

	/**
	 ** 隐藏气泡窗体
	 */
	hidePopup:function(oContainer)
	{
		var divPopup = document.getElementById("popup");
		if((typeof(divPopup) != "undefined") && (divPopup != null))
		{
	    	divPopup.parentNode.removeChild(divPopup);
	    }

		var coverFrame = document.getElementById("cover");
		if((typeof(coverFrame) != "undefined") && (coverFrame != null))
		{
	    	coverFrame.parentNode.removeChild(coverFrame);
	    }
	  
	    var deptDiv = document.getElementById("deptDiv")
	    if((typeof(deptDiv) != "undefined") && (deptDiv != null))
		 {
	    	deptDiv.parentNode.removeChild(deptDiv);

	     }
	}
};
