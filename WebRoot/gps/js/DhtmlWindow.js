/**
*@Fun： 浮动div容器
*
*@Author：LChQ 2008-10-1
*
*@Version: 1.0 
*
*/


/**容器生成 
*@hTitle，容器标题
*@hWidth，容器宽
*@hHeight，容器高
*@closing，关闭前处理事件函数（不带参数）名称
*@baseImageSrc,相关图片所在文件路径 如：../images/holder
*/
function DivHolder(hTitle,hWidth,hHeight,hContent,closing,baseImageSrc,keeper)
{
	var divId = 'div_holder';
	var holder = document.getElementById(divId);
	var centerWidth  = 600-27;
	var centerHeight = 500-34;
	
	if( typeof(baseImageSrc)=="undefined" || null == baseImageSrc)
	{
		baseImageSrc = "../images/holder";
	}
	if(holder)
	{
		holder.parentNode.removeChild(holder);
	}
	
	//关闭时事件
	//默认空事件处理
	var closeHolderHandler = function()
	{
	};
	
	this.setCloseHandler = function(handler)
	{
		closeHolderHandler = handler;
	};
	
	var divHolder = document.createElement('div');
	divHolder.id = divId;
	
	//div样式 background-color: #FFF;border:1px solid black;
	var tempStyle = "position:absolute;left:100px;top:40px;";
	if(hWidth)
	{
		tempStyle += "width:"+hWidth + "px";
		try
		{
			centerWidth = parseInt(hWidth) - 27;
		}catch(e){}
	}
	else
	{
		tempStyle += "width:600;";
	}
	
	//高度定义
	if(hHeight)
	{
		tempStyle += "height:"+hHeight + "px";
		try
		{
			centerHeight = parseInt(hHeight) - 34;
		}
		catch(e){}
	}
	else
	{
		tempStyle += "height:500;";
	}
	divHolder.style.cssText = tempStyle;
	
	//设置标题
	var setHolderTitle = function()
	{
		if(hTitle)
		{
			var objhead = document.getElementById("dragHead");
			if( objhead )
			{
				hTitle="<div style=\"color:white;font-weight: bold \">"+hTitle+"</div>";
				objhead.innerHTML = hTitle;
			}
		}
	};
	//设置容器的内容
	var setHolderContent = function()
	{
		if(hContent)
		{
			var objContent = document.getElementById("tdContainer");
			if( objContent )
			{
				objContent.innerHTML = hContent;
			}
		}
	};
	
	/**建立容器padding:0px;background-color=\'#FFF\'
	"
	<img src=
	<img src=
	*/
	var buildContainer = function()
	{
		htmlTxt = '\
	    <table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">\
			<tbody>\
				<tr>\
    				<td width="6" align="left"  background="' + baseImageSrc + '/bubble_topleft.gif"  height="30" style="background-repeat:repeat-y;overflow:hidden;white-space: nowrap;" />&nbsp;</td>\
    				<td background="';
    				htmlTxt += baseImageSrc +  '/bubble_topcenter.gif" id="dragHead" width="' + centerWidth + '" ></td>\
    				<td width="25px"  align="center"  background="';
    				htmlTxt += baseImageSrc + '/bubble_topcenter.gif">';
    				if(closing)
					{
						htmlTxt += '<img src="'+ baseImageSrc + '/bubble_07.gif" width="21px" height="21" align="right" border="0px" onclick="javascript: eval(' + closing + '()); DivHolder.close();">';
					}
					else
					{
						htmlTxt += '<img src="'+ baseImageSrc + '/bubble_07.gif" width="21px" height="21" align="right" border="0px" onclick="javascript: DivHolder.close();" >';
					}
		htmlTxt += '</td>\
					<td background="'+ baseImageSrc + '/bubble_topright.gif" width="6" height="30" align="right" /></td>\
  				</tr>\
				<tr>\
					<td background="'+ baseImageSrc + '/bubble_left.gif" width="6" align="left" style="background-repeat:repeat-y;overflow:hidden; white-space: nowrap;"></td>\
				    <td colspan="2" valign="middle" align="center" id="tdContainer" style="background-color=\'#FFF\'" height="' + centerHeight +'"></td>\
				    <td background="'+ baseImageSrc + '/bubble_right.gif" style="background-repeat:repeat-y" width="6" align="right"></td>\
				</tr>\
				<tr>\
    				<td colspan="4" background="'+ baseImageSrc + '/bubble_bottom.gif"  height="4" align="center"></td>\
 				</tr>\
			</tbody>\
		  </table>';
		
		return htmlTxt;
	};
	
	/**设置拖动对象
	*@target，移动的容器ID
	*@focusObj，焦点所在的对象ID
	*@withScroll，当容器离开显示区域时，是否有滚动
	*/
	var  setDragObject = function(target, focusObj,withScroll) 
	{
		/*
		*取得容器对象
		*/
		if (typeof target == "string")
		{
			target = document.getElementById(target);
		}
		/*
		*取得焦点对象
		*/
		if (typeof focusObj == "string")
		{
			focusObj = document.getElementById(focusObj);
		}
		
		//初始位置
		target.orig_x = parseInt(target.style.left) - document.body.scrollLeft;
		target.orig_y = parseInt(target.style.top) - document.body.scrollTop;
		
		//z-index
		target.orig_index = target.style.zIndex;
		
		//鼠标按下事件处理
		focusObj.onmousedown = function (ev)
		{
			this.style.cursor = "move";
			this.style.zIndex = 10000;
			var d = document;
			
			ev = ev || window.event;
			
			var x = ev.clientX + d.body.scrollLeft - target.offsetLeft ;
			var y = ev.clientY + d.body.scrollTop - target.offsetTop ;
	
			d.ondragstart = "return false;";
			d.onselectstart = "return false;";
			d.onselect = "document.selection.empty();";
	
			if (target.setCapture) 
			{
				target.setCapture();
			} 
			else 
			{
				if (window.captureEvents) 
				{
					window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
				}
			}
			/**
			*鼠标移动事件处理
			*/
			d.onmousemove = function (ev)
			{
				ev = ev || window.event;
				
				if(ev.clientY > 0 && ( ev.clientY < document.body.clientHeight -3) 
					&& ev.clientX >0 && (ev.clientX < document.body.clientWidth-3) )
				{
					target.style.left = ev.clientX + document.body.scrollLeft - x;
					target.style.top = ev.clientY + document.body.scrollTop - y;
					target.orig_x = parseInt(target.style.left) - document.body.scrollLeft;
					target.orig_y = parseInt(target.style.top) - document.body.scrollTop;
				}
				else
				{
					d.onmouseup();
				}
			};
			
			/**
			*鼠标松开处理
			*/
			d.onmouseup = function () 
			{
				if (target.releaseCapture) 
				{
					target.releaseCapture();
				} 
				else 
				{
					if (window.captureEvents) 
					{
						window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
					}
				}
				//事件清理
				d.onmousemove = null;
				d.onmouseup = null;
				d.ondragstart = null;
				d.onselectstart = null;
				d.onselect = null;
				target.style.cursor = "normal";
				target.style.zIndex = target.orig_index;
			};
		};
		
		/*
		*滚动条处理
		*/
		if (withScroll) 
		{
			var orig_scroll = window.onscroll ? window.onscroll : function () 
			{
			};
			/*
			window.onscroll = function () 
			{
				orig_scroll();
				target.style.left = target.orig_x + document.body.scrollLeft;
				target.style.top = target.orig_y + document.body.scrollTop;
			};
			*/
		}
	};
	
	/**设置标题
	*
	*/
	this.setTitle = function(title)
	{
		hTitle = title;
		setHolderTitle();
	};
	
	/**设置容器内容
	*
	*/
	this.setContent = function(content)
	{
		hContent = content;
		setHolderContent();
	};
	
	divHolder.innerHTML = buildContainer();
	
	//"<table><tr><td><input type=button value=dragme id=dragbutton></td></tr><tr><td>第一行</td></tr><tr><td>第二行</td></tr><tr><td><input type=button value=domark id=domark></td></tr></table>" ;
	if(keeper)
	{
		keeper.document.body.appendChild(divHolder);
	}
	else
	{
		document.body.appendChild(divHolder);
	}
	setHolderTitle();
	setHolderContent();
	setDragObject(divId,'dragHead',0);
}

/**关闭divholder
*
*/
DivHolder.close = function()
{
	var divId = 'div_holder';
	var holder = document.getElementById(divId);
	if(holder)
	{
		holder.parentNode.removeChild(holder);
	}
}