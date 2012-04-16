/**GPS 分组
*	luchunqiang 2009-09-03
*/

function GPSClassify()
{
	this.actionUrl = "";
	this.classifyCode = "";
	
	this.rootNodeAttr = "classify"; 
	
	this.treeHtml = '';
	
	this.treeID = 'tree' + "_" + (new Date()).valueOf();
	this.treeImgRelativePath = 'images/tree/';
	
	this.treeHolderID = '';
	
	this.m_rootData = {}; //new Array(); //根节点
	this.m_nodeData = {}; //除树叶节点外的节点
	this.m_leafData = {}; //叶结点 	
	
	this.splitChar = '_';

	this.leafClickHandler = null;	//叶结点点击事件
	this.midNodeClickHandler = null;	//中间结点点击事件(不包括叶结点)
	
	this.oActiveLINode = null;
	this.activeColor = 'blue';
	this.defaultColor = 'black';
	
	this.nodeDisplayData = null;
	
	this.serverURI = 'gps.classifycountctrl.readGpsClassifyCount.d' ;  //'gps.gpstest.TestClassify.d';	//
	
	this.treeRootOpenImg = '1.gif'; //根
	this.treeRootCloseImg = '1.gif'; //根
	this.treeMinusImg = '2.gif'; //-
	this.treePlusImg = '3.gif'; //+
	this.treeNodeOpenImg = '4.gif'; //open
	this.treeNodeCloseImg = '5.gif';	//close
	this.treeLeafImg = '7.gif';	//叶子
}



GPSClassify.prototype.initTreeImg = function()
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

GPSClassify.prototype.setTreeHolderID = function(holderID)
{
	this.treeHolderID = holderID;
 
};

GPSClassify.prototype.setLeafClickHandler = function(handler)
{
	this.leafClickHandler = handler;
};

GPSClassify.prototype.setMidNodeClickHandler = function(handler)
{
	this.midNodeClickHandler = handler;
};

GPSClassify.prototype.getClassifyXML = function()
{
	var url = this.serverURI ;
	var params = "";
	new Ajax.Request(url, 
			{
				method: 'post', 
				parameters: params, 
				onComplete: this.parseClassifyXML.bindAsEventListener(this)
			}
		);
};

GPSClassify.prototype.getConfigXMLTree = function(xmlConfigFile)
{ 
	this.initTreeImg();
	var xmlDoc = this.loadMenuXML(xmlConfigFile);
	
	if(xmlDoc.xml.length > 0)
	{ 
		this.createHTMLByXmlDoc(xmlDoc);
		$(this.treeHolderID).innerHTML = this.treeHtml;
		$(this.treeHolderID).onclick = this.nodeClickHandler.bindAsEventListener(this);
	}
};

GPSClassify.prototype.parseClassifyXML = function(xmlHttp)
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

GPSClassify.prototype.createHTMLByXmlDoc = function(xmlDoc)
{
	this.xmlDoc = xmlDoc;
	//解析xml数据
	var docEle = this.xmlDoc.documentElement;
	for(var i=0; i<docEle.childNodes.length; i+=1)
	{
		var curNodeEle = docEle.childNodes[i];
		var curNode = this.getNodeAttribute(curNodeEle);
		if(this.rootNodeAttr == curNode.attr )
		{
			this.treeHtml += this.rootNodeHtml(curNode,curNode.id);
			curNode['isRoot'] = true;
			this.addChildNodes(curNodeEle,curNode.id,curNode.isRoot);
		 
			this.treeHtml += this.rootEndHtml();
			
			this.m_rootData[curNode.id] = curNode; //curNode.text;
	// 		this.m_rootData.push(curNode.id);
		}
	}
	
	
	this.xmlDoc = null;
};


GPSClassify.prototype.getClassifyNode = function (xmlObj , tagName) 
{
	var z = null;
	if ( xmlObj )
	{
		var temp = xmlObj.getElementsByTagName(tagName);
		z = temp[0];
	} 
	return null;
};


GPSClassify.prototype.getNodeAttribute = function(oNode)
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


GPSClassify.prototype.addChildNodes = function( nodeEle ,nodeID,isRoot)
{
	if(nodeEle.childNodes && 0< nodeEle.childNodes.length)
	{
		for(var i=0; i<nodeEle.childNodes.length; i+=1)
		{
			var hasChild = false;
			var curNodeEle = nodeEle.childNodes[i];
			var curNode = this.getNodeAttribute(curNodeEle);
						
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
			if( this.m_nodeData[nodeID] ) //保存子节点的信息
			{
				this.m_nodeData[nodeID].push(id);
			}
			else
			{
				this.m_nodeData[nodeID] = new Array();
				this.m_nodeData[nodeID].push(id);
			}
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
GPSClassify.prototype.nodeClickHandler = function(event)	
{
	event = event?event:window.event;
	
	var o = event.srcElement ? event.srcElement: event.target;
	
	var oNodeEle  = this.getClickNodeEle(o);
	var liEle = oNodeEle.liEle;
	var nodeID = oNodeEle.nodeID;
	
	if(nodeID && '' != nodeID  )
	{
		this.setActiveColor(liEle);
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
				this.midNodeClickHandler(nodeID);
			}
			
		}
		else
		{
			var nodes = nodeID.split(this.splitChar);
//			var leafID = nodes[nodes.length-1];
			if( this.m_leafData[nodeID] && this.leafClickHandler)
			{
				this.leafClickHandler(nodes,this.m_leafData[nodeID]);
			}
		}
	}
};

//获取被点击节点的参数
GPSClassify.prototype.getClickNodeEle = function(oNode)
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
GPSClassify.prototype.setActiveColor = function(liEle)
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

GPSClassify.prototype.getNodeFaceTxt = function(node)
{
	var faceTxt =  node.text ;
	if( node.stat &&  0<node.stat.length)
	{
		faceTxt += '(' + node.stat + ') ' 
	}
	
	return faceTxt;
};

GPSClassify.prototype.loadMenuXML = function(xmlFile)
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

GPSClassify.prototype.nodeHtml = function(node,nodeID,hasChild)
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
	//	var htmlBuffer = '<TR style="height: 22px" id=' + nodeID + '>
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


GPSClassify.prototype.midNodeHtml = function(node,nodeID,hasChild)
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

GPSClassify.prototype.nodeEndHtml = function()
{
	var htmlBuffer = '</TBODY>	</TABLE> </TD>	</TR> ';
	return htmlBuffer;
};
GPSClassify.prototype.rootEndHtml = function()
{
	var htmlBuffer = '</TBODY>	</TABLE> </TD>	</TR> </TBODY>	</TABLE>';
	return htmlBuffer;
};

GPSClassify.prototype.rootNodeHtml = function (node,nodeID)
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










