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
function DivHolder(hTitle,hWidth,hHeight,hContent,closing,baseImageSrc )
{
	var divId = 'div_holder';
	var holder = document.getElementById(divId);
	var centerWidth  = 600-27;
	var centerHeight = 500-34;
	
	if( typeof(baseImageSrc)=="undefined" || null == baseImageSrc)
	{
		baseImageSrc = ImageBaseDir + "holder";
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
    				<td width="6" align="left"  background="'+ baseImageSrc +'/bubble_topleft.gif"  height="30" style="background-repeat:repeat-y;overflow:hidden;white-space: nowrap;" />&nbsp;</td>\
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
				
				target.style.left = ev.clientX + document.body.scrollLeft - x;
				target.style.top = ev.clientY + document.body.scrollTop - y;
				target.orig_x = parseInt(target.style.left) - document.body.scrollLeft;
				target.orig_y = parseInt(target.style.top) - document.body.scrollTop;
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
			window.onscroll = function () 
			{
				orig_scroll();
				target.style.left = target.orig_x + document.body.scrollLeft;
				target.style.top = target.orig_y + document.body.scrollTop;
			};
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
	document.body.appendChild(divHolder);
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


/**************************************************************************/
/**
*机构选择
*/
BranchChoose = Class.create();
BranchChoose.prototype =
{
	layerCode  : null,
	branchId : null,
	branchName: null,
	htmlElement : null,
	htmlTarget :null, 
	imageBaseSrc : null,
    /**
     * 构造函数
     * @param {String} layerCode 层次编码
     * @param {String} branchId 机构id
     * @param {String} branchName 机构名称
     * @param {String} htmlElement 机构页面输入框
     * @param {Number} top,left 显示位置
     */
    initialize: function(layerCode,branchId,branchName,htmlElement,top,left,width,height,imageBaseSrc)
    {
    	this._initializeBranchChoose(layerCode,branchId,branchName,htmlElement,top,left,width,height,imageBaseSrc);
    },
    //构造对象
	_initializeBranchChoose : function(layerCode,branchId,branchName,htmlElement,top,left,width,height,imageBaseSrc)
	{
		//设置成员
		this.layerCode = layerCode;
		this.branchId  = branchId;
		this.branchName = branchName;
		this.imageBaseSrc = imageBaseSrc;
		this.htmlElement = htmlElement;
		
		if($(htmlElement))
		{
			this.htmlTarget = $(htmlElement);
			this.buildContainer(top,left,width,height);
		}
		else
		{
			alert('不存在机构选择页面对象!');
		}
	},
	buildContainer: function(top,left,width,height)
	{
		//创建容器div
	 	var useDiv = document.createElement("div");
	 	useDiv.id="branchDivElement";
	 	
	 	//useDiv.className="deptDivClass";
	 	useDiv.style.cssText="z-index:800000;position:absolute";
	 	
	 	//给弹出窗口左右边距默认值20
	 	if(typeof(top)=="undefined" || null == top )
	 	{
	 		top = this.calculateOffsetTop(this.htmlTarget.offsetParent) + this.htmlTarget.offsetHeight;
	 	}
	 	if(typeof(left)=="undefined" || null == left)
	 	{
	 		left = this.calculateOffsetLeft(this.htmlTarget) ;
	 	}
	 	
	 	//容器大小定义
	 	if(typeof(width)=="undefined" || "" == width )
	 	{
	 		width = 220;
	 	}
	 	if(typeof(height)=="undefined" || height=="")
	 	{
	 		height = 320;
	 	}
	 	
	 	useDiv.style.width = width;
	 	useDiv.style.height = height;
	 	
	 	
	    document.body.appendChild(useDiv);
		
		var htmlTxt = "<span id=\""+ this.branchId +"\" style=\"color:black;cursor:hand\" ondblclick=\"BranchChoose.nodeDoubleClickHandler('"
					+ this.layerCode +"','"+ this.branchId +"','"+  this.branchName + "','"+ this.htmlElement  +  "')\" onclick=\"BranchChoose.nodeClickHandler('"
					+ this.layerCode +"','"+ this.branchId +"','"+  this.branchName + "','"+ this.htmlElement  +  "','"+ this.imageBaseSrc  +"')\"><img id=\""+"i"+ this.branchId 
					+ "\" src=\"" + this.imageBaseSrc + "/tree/plusOpen.gif\"/>"+ this.branchName
					+ "</span><br><div id=\""+ this.layerCode +"\" style=\"display:none\"></div>";
		
		this.showBranchChoose(useDiv.id , '请选择机构...', htmlTxt,this.imageBaseSrc,this.htmlElement,width,height);
		
		useDiv.style.top =top ;
	 	useDiv.style.left=left;
	 	
	},
	/**
	 ** 显示气泡窗体
	 */
	showBranchChoose: function(container,title,contents,imageBaseSrc,htmlElement,width,height)
	{
		var oContainer = document.getElementById(container);
		
		//创建弹出div
	    var divPopup = document.getElementById("branchchooseDiv");
	   
	    if(divPopup != null)
		{  
			divPopup.parentNode.removeChild(divPopup);
		}
		
		divPopup = document.createElement("div");
		divPopup.id = "branchchooseDiv";
		divPopup.style.position ="absolute";		
		divPopup.style.zIndex = 10000;
		divPopup.style.left="0";
		divPopup.style.top="0";
		divPopup.style.width= width;//"100%";
		divPopup.style.height= height;//"100%";
		//divPopup.style.overflow = "auto";
		
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
		coverFrame.style.width= width;   //"250";
		coverFrame.style.height= height; //"320";
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
                  <img src="'+ imageBaseSrc + '/popup/ok.gif" border="0" style="cursor:pointer"\
                  	 onclick="BranchChoose.saveSelect('+ htmlElement +');">\
                  <img src="'+ imageBaseSrc + '/popup/cancel.gif" border="0" style="cursor:pointer"\
                  	 onclick="BranchChoose.cancelSelect(' + htmlElement + ');">\
                </td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr height="92%" valign="top">\
                <td class="left"></td>\
                <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                   <div style="width:'+ width +';height:'+ height +';overflow:auto">' + contents + '</div>\
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
	/**计算坐标
     *
     */
     calculateOffset: function(field, attr) 
     {
         var offset = 0;
         while(field) 
         {
           offset += field[attr]; 
           field = field.offsetParent;
         }
         return offset;
     },
     calculateOffsetTop: function(field) 
     {
         return this.calculateOffset(field, "offsetTop");
     },
     calculateOffsetLeft : function(field) 
     {
   		return this.calculateOffset(field, "offsetLeft");
     }
};

/**双击事件
     *
     */
BranchChoose.nodeDoubleClickHandler = function(layerCode, branchId ,branchName, htmlElement) 
{
	var htmlTarget = null;
	if($(htmlElement))
	{
		htmlTarget = $(htmlElement);
	}
	htmlTarget.branchId = branchId; 	 
	htmlTarget.layerCode  = layerCode; 
	htmlTarget.branchName = branchName;   
	htmlTarget.value=htmlTarget.branchName;
	BranchChoose.close();
	
};

/**
 * 
 * @函数说明：查询数据库,如有子机构则进行展现
 * @创建日期：2008-08-12
 * @作者：郭亮亮

 */
BranchChoose.nodeClickHandler = function(layerCode, branchId ,branchName, htmlElement,imageBaseSrc) 
{
	var htmlTarget = null;
	if($(htmlElement))
	{
		htmlTarget = $(htmlElement);
	}
	htmlTarget.branchId = branchId; 	 //赋值机构ID
	htmlTarget.layerCode  = layerCode; //赋值层次ID
	htmlTarget.branchName = branchName;   //赋值机构名称

	BranchChoose.setSeleColor( branchId ); //设置选中颜色
    
    //根据 branchId 查询数据库,展现该机构的子机构
	var url = "pcs.departmentTree.getTreeListById.d";
	url = encodeURI(url);
	var params = "JGID=" + branchId + "&&Date=" + new Date().getTime();
	
	new Ajax.Request(url, 
						{	method:"post",
							parameters:params,
						 	onComplete:function(xmlHttp)
						 	{	
						 		//构造子机构项目
						 		var isORno=true;
								var divObj = document.getElementById(layerCode);
								if(divObj.hasChildNodes())
								{
								    var divChildren = divObj.getElementsByTagName("div");
									var divLength = divChildren.length;
									for (var i = 0; i<divLength; i++)
									{
									     if(divChildren[i].hasChildNodes())
									     {
									        isORno = false;
									     }
									}
									if(isORno)
									{
									  	//先删除子节点再写入
										var layerObj  = document.getElementById(layerCode);
										if(layerObj)
										{
										 	BranchChoose.deleteChildeElement(layerObj);
											//layerObj.parentNode.removeChild(layerObj);
										}
										BranchChoose.buildSubBranch(xmlHttp,layerCode,htmlElement,imageBaseSrc);
									}
								}
								else
								{
								   BranchChoose.buildSubBranch(xmlHttp,layerCode,htmlElement,imageBaseSrc);
								}
						 	}
						 }
					);
	
	//添加图标
	var img = document.getElementById("i" + branchId);
	
	if( null == document.all(layerCode))
	{
		return null;
	}
	
	//判断是否是叶节点，是则不进行图片转换，否则进行展开和非展开两种状态转换
	if(img.src.indexOf("blankLeaf.gif")>0)
	{
		if (document.all(layerCode).style.display == "") 
		{
			document.all(layerCode).style.display = "none";
		} 
		else 
		{
			document.all(layerCode).style.display = "";
		}
	}
	else
	{
		if (  document.all(layerCode).style.display == "")
		{
			document.all(layerCode).style.display = "none";	
			img.src = imageBaseSrc + "/tree/plusOpen.gif";
		} 
		else 
		{
			document.all(layerCode).style.display = "";
			img.src = imageBaseSrc + "/tree/minusClosed.gif";
		}
	}
};

BranchChoose.deleteChildeElement = function(divId)
{
	
	  var divObj = document.getElementById(divId);
	  if(divObj.hasChildNodes()){
	    //删除span
	    var spanChildren = divObj.getElementsByTagName("span");
	    var spanLength = spanChildren.length;
		for (var i = 0; i <spanLength; i++) {
			 divObj.removeChild(spanChildren[0]);
		}
		//删除br
		var brChildren = divObj.getElementsByTagName("br");
		var brLength = brChildren.length;
		for (var i = 0; i<brLength; i++) {
			 divObj.removeChild(brChildren[0]);
		   }
		//删除div
		var divChildren = divObj.getElementsByTagName("div");
		var divLength = divChildren.length;
		for (var i = 0; i<divLength; i++) {
			divObj.removeChild(divChildren[0]);
		}
	  }
}
/** 
* 功能：返回选中的机构
* 时间：2008-08-14
*/
BranchChoose.saveSelect = function(htmlElement)
{
	
	var htmlTarget = null;
	if($(htmlElement))
	{
		htmlTarget = $(htmlElement);
		if(htmlTarget.branchName)
		{
			htmlTarget.value = htmlTarget.branchName;
		}
		else
		{
			htmlTarget.value = "";
		}
	}
	BranchChoose.close();//弹出div关闭
};

/** 
* 功能：取消选择机构
* 时间：2008-08-14
*/
BranchChoose.cancelSelect = function(htmlElement)
{
	var htmlTarget = null;
	if($(htmlElement))
	{
		htmlTarget = $(htmlElement);
		htmlTarget.value = "";
		htmlTarget.branchId = null; 	 //赋值机构ID
		htmlTarget.layerCode  = null;    //赋值层次ID
		htmlTarget.branchName = null;   //赋值机构名称
	}
	BranchChoose.close();//弹出div关闭
};


BranchChoose.close = function()
{
	var branchObj = document.getElementById("branchDivElement");
	if(branchObj)
	{
		branchObj.parentNode.removeChild(branchObj);
	}
};



/** 
* 功能：写入子节点
*/
BranchChoose.buildSubBranch = function(xmlHttp,layerCode ,htmlElement, imageBaseSrc)
{
   	var xmldoc = xmlHttp.responseXML;
	
	var results = xmldoc.getElementsByTagName("col");
	var row = xmldoc.getElementsByTagName("row");
	
	for (var i = 0; i < (results.length / 3) ; i++) {
	    
	    //创建image子节点
		var ele_image = document.createElement("img");
		
		//判断子节点是否含有下级节点，确定节点图片链接
		if(results[3 * i+2].text>0)
		{
			ele_image.setAttribute("src", imageBaseSrc + "/tree/plusOpen.gif");

			//为图片添加id，用于之后删除
			ele_image.setAttribute("id", "i" + results[3 * i].text);
		}
		else
		{
			ele_image.setAttribute("src", imageBaseSrc + "/tree/blankLeaf.gif");

			//为图片添加id，用于之后删除
			ele_image.setAttribute("id", "i" + results[3 * i].text);
		}

		//控制图片宽度
		ele_image.setAttribute("width", "36");

	    //创建子节点span
		var ele_span = document.createElement("span");
		ele_span.setAttribute("id", results[3 * i].text); //添加id 与数据库 JGID 相对应

		ele_span.style.cursor = "hand"; //添加鼠标显示手型
		ele_span.style.color = "black"; //添加字体颜色
			    	    	    	   
	    //创建div子节点
		var ele_div = document.createElement("div");
		
		//div添加展现id 即doOnClick 方法第一个参数
	    ele_div.setAttribute("id", layerCode + ((i<10) ? "0" + (i + 1):(i + 1)));	    
		ele_div.style.display = "none";
		
		//添加onClick事件传div 的ID(用来展现) span 的ID 用来获得父节点,此ID与数据库中的JGID相对应
		//(layerCode, branchId ,branchName, htmlElement,imageBaseSrc)
		ele_span.onclick = new Function("BranchChoose.nodeClickHandler('" + ele_div.getAttribute("id") + "','" + results[3 * i].text + "','"+results[((3 * i) + 1)].text+ "','" + htmlElement+"','"+ imageBaseSrc+ "')");
	
		//添加ondblClick事件
		ele_span.ondblclick = new Function("BranchChoose.nodeDoubleClickHandler('" + ele_div.getAttribute("id") + "','" + results[3 * i].text + "','"+results[((3 * i) + 1)].text+ "','" + htmlElement+"')");
		
		//添加image子节点
		ele_span.appendChild(ele_image);
		
		//显示机构图片+名称	
		ele_span.innerHTML = BranchChoose.loadBlank(layerCode,ele_span.innerHTML + results[((3 * i) + 1)].text);
		
		var divObj = document.getElementById(layerCode);
		
		//添加span子节点
		divObj.appendChild(ele_span);
		
		//添加换行
		var ele_br = document.createElement("br");
		ele_br.setAttribute("id", layerCode + ((i<10) ? "0" + (i + 1):(i + 1))+results[3 * i].text);
		divObj.appendChild(ele_br);
		
		//添加div子节点
		divObj.appendChild(ele_div);
	}
};
/** 
* 功能：根据divid即机构层次编码添加空格方法
*/
BranchChoose.loadBlank = function(layerCode,s)
{  
  var length = layerCode.length;
  
  var s_0 =""; 
  for(var i=0;i<length;i++)
  {
    s_0 += "&nbsp;&nbsp;";
  }
  return  s_0+s;
}

/** 
* 功能：设置选中颜色方法
* 
*/
BranchChoose.setSeleColor = function(branchId)
{
	var span = document.getElementById(branchId);
	var divObj = document.getElementById("branchchooseDiv");
	var sArray = divObj.getElementsByTagName("span");
	for (i=0; i<sArray.length; i++) 
	{
		sArray[i].style.color= "black";
	}
	span.style.color= "blue";
};
