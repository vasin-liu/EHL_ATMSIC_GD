/***********************************************************************

版 权：北京易华录信息技术股份有限公司 2009

文件名称：DhtmlBranchTree.js
摘 要：	构造t_sys_department里面的树结构。

当前版本：1.0
作 者：LChQ  2009-3-20 

修改：

***************************************************************************/

DhtmlBranchTree.divId = "DhtmlBranchTreeHtmlDiv_" + (new Date()).valueOf();	//该对象html元素容器的ID

//关闭该层
DhtmlBranchTree.close = function()
{
	var id = DhtmlBranchTree.divId;
	if($(id))
	{
		$(id).parentNode.removeChild($(id));
	}
};

//获取DhtmlBranchTree界面中选择的值
DhtmlBranchTree.getValue = function(htmlEleId)
{
	if(htmlEleId && $(htmlEleId))
	{
		var oHtmlEle = $(htmlEleId);
		
		//编码
		//名称
		if(oHtmlEle.nodeCode)
		{
			oHtmlEle.branchId = oHtmlEle.nodeCode;
			oHtmlEle.branchName = oHtmlEle.value = oHtmlEle.nodeText;
		}
		//		alert(oHtmlEle.nodeCode + ",名称：" + oHtmlEle.nodeText);
	}
	DhtmlBranchTree.close();
};

/**提供树结构的对象选择功能 
*
*	LChQ 2008-1-1
*/
function DhtmlBranchTree(treeCode,rootCode,rootName,htmlEleId)
{
	var m_imgUri = '';
	var m_left = null, m_top = null;		//显示位置
	var m_width = null, m_height = null;	//显示大小
	
	var m_divId = DhtmlBranchTree.divId;		//标识
	
	var oHtmlEle = $(htmlEleId);
	
	var m_leafImgPath = '/tree/blankLeaf.gif'; 		//叶结点图片
	var m_nodeOpenImg = '/tree/plusOpen.gif';		//打开结点图片
	var m_nodeCloseImg = '/tree/minusClosed.gif';	//关闭结点图片
	
	
	var m_activeNode = null;	//选中项
	var m_activeNodeId = "";	
	var m_title = "请选择机构...";
	var mySelf = this;
	if(!oHtmlEle)
	{
		throw new Error('数据对象不存在。');
	}
	
	//设置标题信息
	this.setTitle = function(strTitle)
	{
		m_title = strTitle;
	};
	
	//获取ID
	this.getId = function()
	{
		return m_divId;
	};
	
	//设置图片相对路径信息
	this.setImgRelativePath = function(uri)
	{
		m_imgUri = uri;
	};
	
	//构造html信息
	this.createHtml = function()
	{
		DhtmlBranchTree.close();
		var strDiv = "<div style='z-index:20000;border:1 solid #000;position:absolute;background-color:#DFEAF7;overflow:auto;font-size:9pt'>";
		var oDiv = document.createElement(strDiv);
		oDiv.id = m_divId;
		
		this.getRegion();	//获取显示大小
		this.getLocation();	//获取显示位置
		
		this.adjustLocation(); //调整位置
		
		oDiv.style.width = m_width;
		oDiv.style.height = m_height;
		
		oDiv.style.left = m_left;
		oDiv.style.top = m_top;
		oDiv.style.display="inline";
		
		oDiv.appendChild(this.createTitleHtml()); 	//构造标题对象
		//建立根节点
		oDiv.appendChild(this.createNode(rootCode,rootName,null,1));
		document.body.appendChild(oDiv);
	};
	
	//关闭该层
	this.close = function()
	{
		if($(m_divId))
		{
			$(m_divId).parentNode.removeChild($(m_divId));
		}
	};
	
	//构造标题
	this.createTitleHtml = function()
	{
		var tableHtml = document.createElement('<table>');
		tableHtml.style.cssText = 'width:100%;border:0;cellpadding:0;cellspacing:0;border-collapse:collapse;';
		var tbodyHtml = document.createElement('<tbody>');
		
		tableHtml.appendChild(tbodyHtml);
		
		var trHtml = document.createElement('<tr style="height:12px;background-color:#666688;" >');
		
		var tdHtml = document.createElement('<td style="color:white;text-align:left">');
		//var titleHtml = document.createTextNode(m_title);
		tdHtml.innerHTML = "&nbsp;" +  m_title;
		trHtml.appendChild(tdHtml);
		
        tdHtml = document.createElement('<td  align="right" valign="middle">');
        
       
        var setImg = document.createElement('<img border="0" style="cursor:pointer">');
        setImg.src = m_imgUri + '/popup/ok.gif';
        setImg.onclick =  function() 
        { 
        	DhtmlBranchTree.getValue( htmlEleId );
        };
        
        var returnImg =  document.createElement('<img border="0" style="cursor:pointer">');
        returnImg.src = m_imgUri + '/popup/cancel.gif';
       	returnImg.onclick = function()
       	{
       		oHtmlEle.branchId = null;
       		oHtmlEle.branchName = null;
       		oHtmlEle.value = "";
       		DhtmlBranchTree.close();
       	};
        tdHtml.appendChild(setImg);
        var spanBlank = document.createElement('span');
        spanBlank.innerHTML = '&nbsp;';
        tdHtml.appendChild(spanBlank);
        tdHtml.appendChild(returnImg);
        
        var blank = document.createElement('<td>');
        blank.innerHTML = '&nbsp;';
        
        trHtml.appendChild(tdHtml);
        trHtml.appendChild(blank);
        tbodyHtml.appendChild(trHtml);
		//构造div对象
        var oDiv = document.createElement('<div  style="height:14px">');
        oDiv.appendChild(tableHtml);
        oDiv.id = m_divId + 'title';
      
        return oDiv; 
	};

	//构造节点
	this.createNode = function(nodeCode,nodeName,father,childCount)
	{
		var oFatherHtml = null;		//得到父节点对象
		if(father)
		{
			oFatherHtml = $(father);
		}
		
		if(!oFatherHtml)
		{
			//父节点不存在
			if($(treeCode))
			{
				oFatherHtml = $(treeCode).father;
			}
			else
			{
				oFatherHtml = new Object();
				oFatherHtml.nodeCode = treeCode;
				oFatherHtml.floor = 0;
			}
		}
		var node = null;
		if(childCount > 0)
		{
			node = this.createHtmlNode(nodeCode,nodeName,oFatherHtml);	//构造节点
		}
		else
		{
			node = this.createHtmlLeaf(nodeCode,nodeName,oFatherHtml)	//构造叶结点
		}
		return node;
	};
	
	//构造html结点
	this.createHtmlNode = function(nodeCode,nodeName,oFatherHtml)
	{
		var floor = oFatherHtml.floor;
		var spaces = this.getSpaceByFloor(floor);
		var oDiv = document.createElement("<div id='" + oFatherHtml.nodeCode + "'>");
		
		var node = document.createElement('<li style="list-style-type:none" id="s_' + nodeCode + '">' );
		node.onmouseover = mySelf.mouseoverHandler;
		node.onmouseout = mySelf.mouseoutHandler;
		var nodeImg = document.createElement('<img src="' + m_imgUri + m_nodeOpenImg + '">');
		node.appendChild(nodeImg);
		node.appendChild(document.createTextNode(nodeName));
		oDiv.innerHtml = spaces ;
		oDiv.appendChild( node );
		node.nodeImg = nodeImg;
		node.onclick = mySelf.nodeClickHandler;
		nodeImg.onclick = function()
		{
			var o = new Object();
			o.srcElement = node;
			mySelf.nodeClickHandler(o);
		};
		node.ondblclick = mySelf.leafDBClickHandler;
		node.nodeCode = nodeCode;
		node.nodeText = nodeName;
		node.keeper = oDiv;
		node.father = oFatherHtml;
		node.floor = floor + 1;
		node.style.cursor = "hand";
		return oDiv;
	};
	
	//创建叶结点
	this.createHtmlLeaf = function(nodeCode,nodeName,oFatherHtml)
	{
		var floor = oFatherHtml.floor;
		var spaces = this.getSpaceByFloor(floor);
		var oDiv = document.createElement("<div id='" + oFatherHtml.nodeCode + "'>");
		
		var leaf = document.createElement('<li style="list-style-type:none" id="s_' + nodeCode + '">' );
		leaf.onmouseover = mySelf.mouseoverHandler;
		leaf.onmouseout = mySelf.mouseoutHandler;
		var leafImg = document.createElement('<img src="' + m_imgUri + m_leafImgPath + '">');
		leaf.appendChild(leafImg);
		leaf.appendChild(document.createTextNode(nodeName));
		oDiv.innerHtml = spaces ;
		oDiv.appendChild( leaf );
		leaf.onclick = mySelf.leafClickHandler;
		leafImg.onclick = function()
		{
			var a = new Object();
			a.srcElement = leaf;
			mySelf.leafClickHandler(a);
		};
		leaf.ondblclick = mySelf.leafDBClickHandler;
		leaf.nodeCode = nodeCode;
		leaf.nodeText = nodeName;
		leaf.father = oFatherHtml;
		leaf.style.cursor = "hand";
		return leaf;
	};
	//页节点选择事件
	this.leafClickHandler = function(event)
	{
		event = event ? event : window.event; 
		var node = event.srcElement || event.target;
		mySelf.setActiveCss(node);
		//alert(node.nodeCode + node.nodeText);
		
	};
	
	//页节点选择事件
	this.leafDBClickHandler = function(event)
	{
		event = event ? event : window.event; 
		var node = event.srcElement || event.target;
		 
		if(node.nodeCode)
		{
			oHtmlEle.branchId = node.nodeCode;
			oHtmlEle.branchName = oHtmlEle.value = node.nodeText;
		}
		DhtmlBranchTree.close();
	};
	
	
	
	//中间节点选择事件
	this.nodeClickHandler = function(event)
	{
		event = event ? event : window.event; 
		var node = event.srcElement || event.target;
		mySelf.setActiveCss(node);
		if(node.childCreated)
		{
			if(node.sonDisplay && "none" == node.sonDisplay)
			{
				mySelf.setNodeDisplay(node,"");
				node.sonDisplay = "";
				if(node.nodeImg)
				{
					node.nodeImg.src= m_imgUri + m_nodeCloseImg;
				}
			} 
			else
			{
				mySelf.setNodeDisplay(node,"none");
				node.sonDisplay = "none"; 
				if(node.nodeImg)
				{
					node.nodeImg.src=  m_imgUri + m_nodeOpenImg ;
				}
			}
		}
		else
		{
			mySelf.readData(node);
			node.sonDisplay = "";
			if(node.nodeImg)
			{
				node.nodeImg.src= m_imgUri + m_nodeCloseImg ;
			}
		}
	};
	//显示或隐藏节点
	this.setNodeDisplay = function(node,display)
	{
		if(node.keeper)
		{
			for(var i=0; i<node.keeper.childNodes.length; i++)
			{
				if(node.keeper.childNodes(i) && node != node.keeper.childNodes(i))
				{
					node.keeper.childNodes(i).style.display = display;
				}
			}
		}
	};
	
	//设置活动项的样式
	this.setActiveCss = function(node)
	{
		if( m_activeNode )
		{
			//m_activeNode.style.background = "#DFEAF7";
			m_activeNode.style.color = "black";
		}
		m_activeNode = node;
		m_activeNode.style.color = "blue";
		//m_activeNode.style.background = "#FFFAF7";
		oHtmlEle.nodeCode = node.nodeCode;
		oHtmlEle.nodeText = node.nodeText;
	};
	
	//读取数据
	this.readData = function(node)
	{
		var strUri = "pcs.departmentTree.getTreeListById.d";
		//编码数据参数
		var params = "JGID=" + node.nodeCode + "&&Date=" + new Date().getTime();
		new Ajax.Request(strUri, 
							{	method:"post",
								parameters:params,
							 	onComplete:function(xmlHttp)
							 	{
									var xmldoc = xmlHttp.responseXML;
									if('' != xmldoc.text )
									{
										var records = mySelf.assignData(xmldoc); //匹配数据
										
										for(var i=0; i<records.length; i++)
										{
											//构造节点
											var newNode = mySelf.createNode(records[i].nodeCode,
													records[i].nodeName,node.id,records[i].childCount);
											node.keeper.appendChild(newNode);
											
										}
										node.childCreated = true;
									}						 		
							 	}
							 }
						);
	};
	
	//匹配数据
	this.assignData = function(xmldoc)
	{
		var rows = xmldoc.getElementsByTagName("row");
		var records = new Array();
		for (var i = 0; i < rows.length ; i++) 
		{
			var record = {};
		    var results = rows[i].getElementsByTagName("col");
		    
			//设置数据
			record['nodeCode'] = results[0].text;
			record['nodeName'] = results[1].text;
			record['childCount'] = results[2].text;		//子节点数量
			records.push(record);
		}
		return records;
	};
	
	//根据层次获得空格数量
	this.getSpaceByFloor = function(floor)
	{
		var strSpace = "";
		for(var i=0; i<floor; i++)
		{
		  strSpace += "&nbsp;&nbsp;";
		}
		return strSpace;
	};
	
	//设置div标识
	this.setHtmlDiv = function(strDivId)
	{
		m_divId = strDivId;
	};
	
	//设置位置
	this.setLocation = function(strLeft,strTop)
	{
		m_left = strLeft;
		m_top = strTop;
	};
	
	//获取位置
	this.getLocation = function()
	{
		//给弹出窗口左右边距默认值20
	 	if(! m_top )
	 	{
	 		m_top = this.calculateOffsetTop(oHtmlEle) + oHtmlEle.offsetHeight; //.offsetParent
	 	}
	 	if(! m_left)
	 	{
	 		m_left = this.calculateOffsetLeft(oHtmlEle)  ;
	 	}
	};
	//容器大小定义
	this.setRegion = function(strWith,strHeight)
	{
		m_width = strWith;
		m_height = strHeight;
	};
	
	//获取容器大小
	this.getRegion = function()
	{
		if(! m_width )
	 	{
	 		m_width = 180;
	 	}
	 	if(! m_height )
	 	{
	 		m_height = 220;
	 	}
	};
	
	//调整位置
	this.adjustLocation = function()
	{
		var docbody = document.body;
		var docWidth = docbody.scrollLeft + docbody.clientWidth;
		var docHeight = docbody.scrollTop + docbody.clientHeight;
		if( ( m_width + m_left ) > docWidth)
		{
			m_left = docWidth - ( m_width  );
		}
		
		if( ( m_height + m_top ) > docHeight )
		{
			m_top = docHeight -   m_height;
		}
		
	};
	
	
	//计算坐标 
    this.calculateOffset = function(field, attr) 
     {
         var offset = 0;
         while(field) 
         {
           offset += field[attr]; 
           field = field.offsetParent;
         }
         return offset;
     };
     
     //top坐标
     this.calculateOffsetTop = function(field) 
     {
         return this.calculateOffset(field, "offsetTop");
     };
     
     //left坐标
     this.calculateOffsetLeft = function(field) 
     {
   		return this.calculateOffset(field, "offsetLeft");
     };
     this.mouseoverHandler = function(event)
	{
		event = event?event:window.event;
		var src = event.srcElement ? event.srcElement:event.target;
		
		src.style.backgroundColor = '#DDD4DD';
	};
	this.mouseoutHandler = function(event)
	{
		event = event?event:window.event;
		var src = event.srcElement ? event.srcElement:event.target;
		src.style.backgroundColor = '';
	};
}

