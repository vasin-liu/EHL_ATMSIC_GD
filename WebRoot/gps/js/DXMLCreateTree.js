/** 通过xml构造树
 *
 *	luchunqiang 2009-09-03
 */
function DXMLCreateTree()
{
	this.treeHtml = '';
	this.treeID = 'xml_tree' + "_" + (new Date()).valueOf();
	this.treeImgRelativePath = 'images/tree/';
	this.splitChar = '_';
	//this.splitChar = '_';
	
	this.treeHolderID = '';	
	this.m_rootData = {}; //new Array(); //根节点
	this.m_nodeData = {}; //除树叶节点外的节点
	this.m_leafData = {}; //叶结点 	

	this.leafClickHandler = null;	//叶结点点击事件
	this.midNodeClickHandler = null;	//中间结点点击事件(不包括叶结点)
	
	this.oActiveLINode = null;
	this.activeColor = 'blue';
	this.defaultColor = 'black';
	
	this.serverURI = '' ;   
	this.postParams = '';
	
	this.treeRootOpenImg = '1.gif'; //根
	this.treeRootCloseImg = '1.gif'; //根
	this.treeMinusImg = '2.gif'; //-
	this.treePlusImg = '3.gif'; //+
	this.treeNodeOpenImg = '4.gif'; //open
	this.treeNodeCloseImg = '5.gif';	//close
	this.treeLeafImg = '7.gif';	//叶子
	
	this.rootNodeAttr = '';
	this.filterNodeAttr = {}; //过滤属性
	this.nodeDisplayData = null; //显示的节点设置，根据ID匹配
}



DXMLCreateTree.prototype.initTreeImg = function()
{
	this.nodeMinuImg = this.treeImgRelativePath + this.treeMinusImg; 
	this.nodePlusImg = this.treeImgRelativePath + this.treePlusImg; 
	
	this.nodeOpenImg =  this.treeImgRelativePath + this.treeNodeOpenImg; 	// this.treeImgRelativePath + '4.gif'; //open
	this.nodeCloseImg =  this.treeImgRelativePath + this.treeNodeCloseImg;   	//this.treeImgRelativePath + '5.gif';	//close
 
	this.leafOpenImg = this.treeImgRelativePath +  this.treeLeafImg;	//叶子
	this.leafCloseImg = this.leafOpenImg;  //叶子关不掉
	 
	this.rootOpenImg = this.treeImgRelativePath + this.treeRootOpenImg;
	this.rootCloseImg = this.treeImgRelativePath + this.treeRootCloseImg; //根 
};

DXMLCreateTree.prototype.setTreeHolderID = function(holderID)
{
	this.treeHolderID = holderID;
 
};

DXMLCreateTree.prototype.setLeafClickHandler = function(handler)
{
	this.leafClickHandler = handler;
};

DXMLCreateTree.prototype.setMidNodeClickHandler = function(handler)
{
	this.midNodeClickHandler = handler;
};

DXMLCreateTree.prototype.getClassifyXML = function()
{
	var url = this.serverURI ;
	var params = this.postParams;
	new Ajax.Request(url, 
			{
				method: 'post', 
				parameters: params, 
				onComplete: this.parseClassifyXML.bindAsEventListener(this)
			}
		);
};

DXMLCreateTree.prototype.getConfigXMLTree = function(xmlConfigFile)
{ 
	this.initTreeImg();
	var xmlDoc = this.loadMenuXML(xmlConfigFile);
	
	if(xmlDoc.xml.length > 0)
	{ 
		this.createHTMLByXmlDoc(xmlDoc);
		if(this.treeHolderID && 0<this.treeHolderID.length)
		{
			$(this.treeHolderID).innerHTML = this.treeHtml;
			$(this.treeHolderID).onclick = this.nodeClickHandler.bindAsEventListener(this);
		}
		else
		{
			document.body.innerHTML = this.treeHtml;
			document.body.onclick = this.nodeClickHandler.bindAsEventListener(this);
		}
	}
};

DXMLCreateTree.prototype.parseClassifyXML = function(xmlHttp)
{
	this.initTreeImg();
	var xmlDoc = xmlHttp.responseXML;
	if(xmlDoc.xml.length > 0)
	{ 
		this.createHTMLByXmlDoc(xmlDoc);
		$(this.treeHolderID).innerHTML = this.treeHtml;
		$(this.treeHolderID).onclick = this.nodeClickHandler.bindAsEventListener(this);
	}
};

DXMLCreateTree.prototype.createHTMLByXmlDoc = function(xmlDoc)
{
	this.xmlDoc = xmlDoc;
	//解析xml数据
	var docEle = this.xmlDoc.documentElement;
	this.creatRootNode(docEle);
	this.xmlDoc = null;
};

DXMLCreateTree.prototype.creatRootNode = function(docEle)
{
	for(var i=0; i<docEle.childNodes.length; i+=1)
	{
		var curNodeEle = docEle.childNodes[i];
		var curNode = this.getNodeAttribute(curNodeEle);
		if( this.rootNodeAttr && 0<this.rootNodeAttr.length )
		{
			if( this.rootNodeAttr != curNode.attr )
			{
				continue;
			}
		}
		if( this.isNodeFiltered(curNode) )
		{
			continue;
		}
			
		this.treeHtml += this.rootNodeHtml(curNode,curNode.id);
		curNode['isRoot'] = true;
		this.addChildNodes(curNodeEle,curNode.id,curNode.isRoot);
	 
		this.treeHtml += this.rootEndHtml();
		this.m_rootData[curNode.id] = curNode; //curNode.text;
	}
};



DXMLCreateTree.prototype.getClassifyNode = function(xmlObj , tagName) 
{
	var z = null;
	if ( xmlObj )
	{
		var temp = xmlObj.getElementsByTagName(tagName);
		z = temp[0];
	} 
	return null;
};


DXMLCreateTree.prototype.getNodeAttribute = function(oNode)
{
	var node = {};
	for(var i=0;i< oNode.attributes.length;i+=1 )
	{
		node[oNode.attributes[i].nodeName ] = oNode.attributes[i].value;
	}
//	node['text'] = oNode.getAttribute('text');	
//	node['id'] = oNode.getAttribute('id');
//	node['attr'] = oNode.getAttribute('attr');	
//	node['stat'] = oNode.getAttribute('stat');
//	node['display'] = oNode.getAttribute('display');		
	
	return node;
};

DXMLCreateTree.prototype.isNodeFiltered = function(curNode)
{
	return false;
};

DXMLCreateTree.prototype.addChildNodes = function( nodeEle ,nodeID,isRoot)
{
	if(nodeEle.childNodes && 0< nodeEle.childNodes.length)
	{
		if(! this.m_nodeData[nodeID] ) //保存子节点的信息
		{
			this.m_nodeData[nodeID] = new Array();
		}
		this.m_nodeData[nodeID].nodeData = this.getNodeAttribute(nodeEle);
		
		for(var i=0; i<nodeEle.childNodes.length; i+=1)
		{
			var hasChild = false;
			var curNodeEle = nodeEle.childNodes[i];
			var curNode = this.getNodeAttribute(curNodeEle);
			if( curNode.attr && 0<curNode.attr.length )
			{	//判断过滤属性
				if(this.filterNodeAttr[curNode.attr] )
				{
					continue;
				}
			}
			
			if( this.isNodeFiltered(curNode) )
			{
				continue;
			}
			
			var id = nodeID + this.splitChar + curNode.id;
			if(curNodeEle.childNodes && 0< curNodeEle.childNodes.length)
			{
				hasChild = true;
			}
			
			if( hasChild ) 
			{
				this.treeHtml += this.midNodeHtml(curNode,id,hasChild);
			}
			else
			{
				this.treeHtml +=  this.nodeHtml(curNode,id,hasChild);
			}
			this.m_nodeData[nodeID].push(id);
			 
			if(curNodeEle.childNodes && 0< curNodeEle.childNodes.length)
			{
				this.addChildNodes( curNodeEle,id,false);
			}
			else
			{
				curNode['parentNodeID'] = nodeID;
				this.m_leafData[id] = curNode; // nodeID;	//叶节点	 
			}
			if( hasChild   )
			{
				this.treeHtml += this.nodeEndHtml();
			}			
		}
	}
};

//点击事件处理
DXMLCreateTree.prototype.nodeClickHandler = function(event)	
{
	event = event?event:window.event;
	var o = event.srcElement ? event.srcElement: event.target;
	
	var oNodeEle  = this.getClickNodeEle(o);
	var liEle = oNodeEle.liEle;
	var nodeID = oNodeEle.nodeID;
	
	if(nodeID && '' != nodeID  )
	{
		this.setActiveColor(liEle);
		var nodes = nodeID.split(this.splitChar);
		if(this.m_nodeData[nodeID] )
		{
			var closing = true;
			for(var i=0; i<this.m_nodeData[nodeID].length; i+=1)
			{
				var child = $(this.m_nodeData[nodeID][i]);
				if(child )
				{
					if(this.nodeDisplayData )
					{
						var lastIndex = this.m_nodeData[nodeID][i].lastIndexOf(this.splitChar);
						if( this.nodeDisplayData[ this.m_nodeData[nodeID][i].substr(lastIndex+ 1) ] )
						{
							child.style.display = child.style.display=="none"?"":"none";
						}
						else
						{
							child.style.display = "none";
						}
					} 
					else
					{
						child.style.display = child.style.display=="none"?"":"none";
					}
					if("" == child.style.display)
					{
						closing = false;
					}
				}
			}
			
			if(closing && oNodeEle.img0 && oNodeEle.img1)
			{
				if( this.m_rootData[nodeID] )
				{	//根节点
					oNodeEle.img0.src = this.nodePlusImg;
					oNodeEle.img1.src = this.rootCloseImg;
				}
				else
				{
					oNodeEle.img0.src = this.nodePlusImg;
					oNodeEle.img1.src = this.nodeCloseImg;
				}
			}
			else
			{
				if(	 oNodeEle.img0 && oNodeEle.img1)
				{
					if( this.m_rootData[nodeID] )
					{//根节点
						oNodeEle.img0.src = this.nodeMinuImg;
						oNodeEle.img1.src = this.rootOpenImg;
					}
					else
					{
						oNodeEle.img0.src = this.nodeMinuImg;
						oNodeEle.img1.src = this.nodeOpenImg;
					}
				}
			}
			if(this.midNodeClickHandler)
			{
				this.midNodeClickHandler(nodes,this.m_nodeData[nodeID].nodeData);
			}
		}
		else
		{
			if( this.m_leafData[nodeID] && this.leafClickHandler)
			{
				this.leafClickHandler(nodes,this.m_leafData[nodeID]);
			}
		}
	}
};

//获取被点击节点的参数
DXMLCreateTree.prototype.getClickNodeEle = function(oNode)
{
	var strTagName = oNode.tagName.toUpperCase();
	var strPTagName = oNode.parentNode.tagName.toUpperCase();
	var nodeEle = {};
	if(strTagName == 'IMG')
	{
		if(strPTagName == 'TD')
		{
			nodeEle['img0'] = oNode;
			nodeEle['img1'] = oNode.parentNode.parentNode.childNodes[1].getElementsByTagName('IMG')[0];
			var liEle = oNode.parentNode.parentNode.childNodes[1].getElementsByTagName('LI')[0];
			nodeEle['liEle'] = liEle;
			nodeEle['nodeID'] = liEle.id.substr(3);
		}
		else
		{//LI
			nodeEle['img0'] = oNode.parentNode.parentNode.parentNode.parentNode.childNodes[0].childNodes[0];
			nodeEle['img1'] = oNode;
			nodeEle['liEle'] = oNode.parentNode;
			nodeEle['nodeID'] = oNode.parentNode.id.substr(3);
		}
	}
	else
	{	
		if(strTagName == 'LI')
		{
			nodeEle['nodeID'] = oNode.id.substr(3);
			nodeEle['liEle'] = oNode;
			nodeEle['img0'] = oNode.parentNode.parentNode.parentNode.childNodes[0].childNodes[0];
			nodeEle['img1'] = oNode.childNodes[0];
		}
		
		if(strTagName == 'SPAN')
		{
			nodeEle['nodeID'] = oNode.childNodes[0].id.substr(3);
			nodeEle['liEle'] = oNode.childNodes[0];
			nodeEle['img0'] = oNode.parentNode.parentNode.childNodes[0].childNodes[0];
			nodeEle['img1'] = oNode.getElementsByTagName('IMG')[0];
		}
	}
	return nodeEle;
};

//设置活动状态
DXMLCreateTree.prototype.setActiveColor = function(liEle)
{
	if( this.oActiveLINode)
	{
	 	this.oActiveLINode.style.color = this.defaultColor;
	}
	if(liEle )
	{
		liEle.style.color = this.activeColor;
		this.oActiveLINode = liEle;
	}
};

DXMLCreateTree.prototype.getNodeFaceTxt = function(node)
{
	var faceTxt =  node.text ;
	if( node.stat &&  0<node.stat.length)
	{
		faceTxt += '(' + node.stat + ') ' 
	}
	return faceTxt;
};

DXMLCreateTree.prototype.loadMenuXML = function(xmlFile)
{
	var xmlDoc;
	if (window.ActiveXObject) 
	{
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = false;
		xmlDoc.load(xmlFile);
	}
	else
	{
		if (document.implementation && document.implementation.createDocument) 
		{
			xmlDoc = document.implementation.createDocument("", "", null);
			xmlDoc.load(xmlFile);
		} 
		else 
		{
			return null;
		}
	}
	return xmlDoc;
};

DXMLCreateTree.prototype.nodeHtml = function(node,nodeID,hasChild)
{
	var img0 = '';
	var img1 = '';
	if(hasChild)
	{
		img0 = this.nodeMinuImg ;
		img1 = this.nodeOpenImg;
	}
	else
	{
		img0 = this.nodeMinuImg ;
		img1 = this.leafOpenImg;
	}
	
	var strDisplay = "";//display:block
	if(node.display  && 'none' == node.display)
	{
		strDisplay = "display:none";
	}
	var htmlBuffer = '<TR style="height: 22px;'+ strDisplay +'" id=' + nodeID + '>\
				<TD></TD>\
				<TD nowrap="true">\
					<TABLE\
						style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px"\
						cellSpacing=0 cellPadding=0 border=0>\
						<TBODY>\
							<TR style="height: 22px">\
								<TD style="WIDTH: 16px;" align=center>\
									<IMG height=19 src="' + img0 + '" width=15>\
								</TD>\
								<TD style="FONT-SIZE: 10pt; CURSOR: hand"\
									nowrap="true">\
									<SPAN\
										style="PADDING-RIGHT: 0px; DISPLAY: block; PADDING-LEFT: 0px">\
										<LI\
											style="FLOAT: left; LIST-STYLE-TYPE: none; TEXT-ALIGN: left"\
											id="li_' + nodeID + '">\
											<IMG height=12 src="' + img1 + '" width=15>' +
													 this.getNodeFaceTxt( node)  +					
										'</LI> </SPAN>\
								</TD>\
							</TR>\
						</TBODY>\
					</TABLE>\
				</TD>\
			</TR>';
	 
	return htmlBuffer;
};


DXMLCreateTree.prototype.midNodeHtml = function(node,nodeID,hasChild)
{
	var img0 = '';
	var img1 = '';
	if(hasChild)
	{
		img0 = this.nodeMinuImg ;
		img1 = this.nodeOpenImg;
	}
	else
	{
		img0 = this.nodeMinuImg ;
		img1 = this.leafOpenImg;
	}
	var strDisplay = "display:block";
	if(node.display  && 'none' == node.display)
	{
		strDisplay = "display:none";
	}
	var htmlBuffer = '<TR style="height: 22px;'+ strDisplay +'" id=' + nodeID + '>\
						<TD  style="WIDTH: 16px;"></TD> \
						<TD nowrap="true">\
							<TABLE \
								style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px"\
								cellSpacing=0 cellPadding=0 border=0>\
								<TBODY>\
									<TR>\
										<TD style="WIDTH: 16px;" align=center>\
											<IMG height=19 src="' + img0 + '" width=15>\
										</TD>\
										<TD style="FONT-SIZE: 10pt; CURSOR: hand" nowrap="true">\
											<SPAN\
												style="PADDING-RIGHT: 0px; DISPLAY: block; PADDING-LEFT: 0px">\
												<LI\
													style="FLOAT: left; LIST-STYLE-TYPE: none; TEXT-ALIGN: left"\
													id="li_' + nodeID + '">\
													<IMG height=12 src="' + img1 + '" width=15>' +
													  this.getNodeFaceTxt( node)  +					
												'</LI> </SPAN>\
										</TD>\
									</TR>';
	return htmlBuffer;
};

DXMLCreateTree.prototype.nodeEndHtml = function()
{
	var htmlBuffer = '</TBODY>	</TABLE> </TD>	</TR> ';
	return htmlBuffer;
};

DXMLCreateTree.prototype.rootEndHtml = function()
{
	var htmlBuffer = '</TBODY>	</TABLE> </TD>	</TR> </TBODY>	</TABLE>';
	return htmlBuffer;
};

DXMLCreateTree.prototype.rootNodeHtml = function (node,nodeID)
{
	var htmlBuffer = '<TABLE style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px" \
				cellSpacing=0 cellPadding=0 border=0> \
				<TBODY>\
					<TR id='+ nodeID +'>\
						<TD class=hiddenRow></TD> \
						<TD nowrap="true"> \
							<TABLE	style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px" \
								cellSpacing=0 cellPadding=0 border=0> \
								<TBODY>\
									<TR style="height: 22px">\
										<TD style="WIDTH: 10px"><IMG height=19 src="' + this.nodeMinuImg + '" width=15></TD>\
										<TD style="FONT-SIZE: 10pt; CURSOR: hand" nowrap="true">\
											<SPAN	style="PADDING-RIGHT: 0px; DISPLAY: block; PADDING-LEFT: 0px">\
												<LI	style="FLOAT: left; LIST-STYLE-TYPE: none; TEXT-ALIGN: left" _extended="true" closeable="true" \
													id="li_' + nodeID + '"> \
													<IMG height=16 src="'+ this.rootOpenImg +'" width=16>' +
													  this.getNodeFaceTxt( node)  +											
											'</LI> </SPAN>\
										</TD>\
									</TR>';
		return htmlBuffer; 
};





/**提供树结构的对象选择功能 
*
*	LChQ 2008-1-1
*/
function ObjectHolderDiv(htmlEleId)
{
	var m_imgUri = '';
	var m_left = null, m_top = null;		//显示位置
	var m_width = null, m_height = null;	//显示大小
	
	var m_divId = 'divHolder_' + (new Date()).valueOf();		//标识
	var oHtmlEle = $(htmlEleId);
	
	var m_title = "请选择机构..";
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
	 
	//构造html信息
	this.createHtml = function()
	{
		this.close();
		var strDiv = "<div style='z-index:20000;border:1 solid #000;position:absolute;background-color:#DFEAF7;overflow:auto'>";
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
		var oHolderDiv = document.createElement('<div  style="height:' + m_height - 14 + 'px">');
        oHolderDiv.id =  'contentdiv_' + (new Date()).valueOf();
		oDiv.appendChild(oHolderDiv);
		document.body.appendChild(oDiv);
		return oHolderDiv.id ;
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
		tdHtml.innerHTML = "<span style='fontsize:9pt'>" +  m_title + "</span>";
		trHtml.appendChild(tdHtml);
		
        tdHtml = document.createElement('<td  align="right" valign="middle">');
        var setImg = document.createElement('<img border="0" style="cursor:pointer">');
        setImg.src = m_imgUri + '/popup/ok.gif';
        setImg.onclick =  this.okClickHandler.bindAsEventListener(this); 
        
        var returnImg =  document.createElement('<img border="0" style="cursor:pointer">');
        returnImg.src = m_imgUri + '/popup/cancel.gif';
       	returnImg.onclick = this.cancleClickHandler.bindAsEventListener(this);
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
	this.okClickHandler = function()
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
			 
		}
		this.close();
	};
	this.cancleClickHandler = function()
   	{
   		oHtmlEle.branchId = null;
   		oHtmlEle.branchName = null;
   		oHtmlEle.value = "";
   		this.close();
   	};
	
 	//设置图片相对路径信息
	this.setImgRelativePath = function(uri)
	{
		m_imgUri = uri;
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
}







