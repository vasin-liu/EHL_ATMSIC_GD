/**构造机构GPS车辆信息树
*
＊　2009-2-17  LChQ
*/
function Atms45GPSTree()
{
	var mySelf = this;
	
	this.m_imagePath = "images/tree";	//图片路径
	this.idpull = {};		//对象列表 
	this.pullSize = 0;		//对象列表中对象数量
	this.xmlDoc = null;
	this.treeObject = null;	
	this.m_rootID = null;
	this.m_gpsActionHandler = null;  //gps车辆点击事件
	this.m_countyActionHandler = null;  //辖区击事件
	
	this.m_gpsCarSwitch = true; //设置是否显示gps车辆
	this.m_gpscarPrifix = "gpsCar_";
	//设置图片基础路径
	this.setImagePath = function(imagePath)
	{
		this.m_imagePath = imagePath;
	};
	
	//设置图片基础路径
	this.setGPSCarSwitch = function(gpsSwitch)
	{
		this.m_gpsCarSwitch = gpsSwitch;
	};
	
	//初始化调用
	this.init = function(htmlObject,rootID,gpsActionHandler,countyActionHandler)
	{
		if (typeof (htmlObject) != "object") 
		{
			this.treeObject = document.getElementById(htmlObject);
		} 
		else 
		{
			this.treeObject = htmlObject;
		}
		this.m_rootID = rootID; 
		this.m_gpsActionHandler = gpsActionHandler;
		this.m_countyActionHandler = countyActionHandler;
		//构造树结构保存容器
		this.htmlNode = new treeItemObject(this.m_rootID, "", 0, this);
		this.theTree = this.createSelf();
		this.theTree.appendChild(this.htmlNode.htmlNode);
	};
	
	
	//加载数据
	//url为访问的路径
	//params为访问参数
	this.loadXmlData = function(url,params)
	{
		var url = url ; //;
		that = this;
		new Ajax.Request(url, 
			{
				method: 'post', 
				parameters: params, 
				onComplete: function( xmlHttp)
							{	
								//读取完成后，解析xml
								mySelf.finishLoadRequest(xmlHttp);
							}	
			}
		);
	};
	
	//处理得到的树xml数据
	this.finishLoadRequest = function(xmlHttp)
	{
		this.xmlDoc = xmlHttp.responseXML;
		
		if(this.xmlDoc.xml.length > 0)
		{ 
			//解析xml数据
			this.parseXMLTree(this,null,this.m_rootID,0);
		}
	};
	//构造树结构保存容器
	this.createSelf = function () 
	{
		var div = document.createElement("div");
		div.style.width = "100%";
		div.style.height = "100%";
	 	this.treeObject.appendChild(div);
		return div;
	};
	
	//解析xml树
	this.parseXMLTree = function(dhtmlObject, node, parentId, level, start) 
	{
		var nodeAskingCall = "";	//请求的结点
		if (!dhtmlObject.parsCount) 
		{
			dhtmlObject.parsCount = 1;
		} 
		else 
		{
			dhtmlObject.parsCount++;
		}
		if (!node) 
		{
			//<tree>结点解析
			node = mySelf.getXMLTopNode("tree");
			parentId = node.getAttribute("id");
			dhtmlObject.parsingOn = parentId;
			dhtmlObject.parsedArray = new Array();
		}
		//获取结点容器(父结点对象)
		var temp = dhtmlObject.glIdStorageFind(parentId);
		if ((temp.childsCount) && (!start)  ) 
		{
			var preNode = temp.childNodes[temp.childsCount - 1];
		} 
		else
		{
			var preNode = 0;
		}
		for (var i = start || 0; i < node.childNodes.length; i++) 
		{
			if(node.childNodes[i].nodeType == 1) 
			{
				var nodx = node.childNodes[i];
				var name = nodx.getAttribute("text");
				var cId = nodx.getAttribute("id");
				var hasChild  = 0;
				if(this.m_gpsCarSwitch)
				{
					hasChild = nodx.childNodes.length;
				}
				else
				{
					for(var ii=0 ;ii<nodx.childNodes.length;ii++)
					{
						if( "node" == nodx.childNodes[ii].tagName )
						{
							hasChild++;
						}
					}
				}
				if( "node" == node.childNodes[i].tagName )
				{
					var chd = 1;
					//parentObject, itemId, itemText, itemActionHandler, childs, beforeNode
					var newNode = dhtmlObject.attachChildNode(temp, cId, name, 0, chd, level,0,hasChild);
				}
				
				if( "gpscar" == node.childNodes[i].tagName && this.m_gpsCarSwitch)
				{
					var chd = 0;
					var cOnline = nodx.getAttribute("online");
					cId = this.m_gpscarPrifix + cId;
					name = name + '(';
					if('是' == cOnline)
					{
						name += '在线';
					}
					else
					{
						name += '离线';
					}
					name += ')';
					var cGPSType = nodx.getAttribute("gpstype");
					//(parentObject, itemId, itemText, itemActionHandler, childs, level,txtType,hasChild,gpsType)
					dhtmlObject.attachChildNode(temp, cId, name, 0, chd, level,cGPSType,0);
				}
				
				if( "node" == node.childNodes[i].tagName )
				{	
					var nextLevel =  level + 1;
					dhtmlObject.parseXMLTree(dhtmlObject, node.childNodes[i], cId, nextLevel);
				}
				
			}
		}
		if (!level) 
		{
			 
			var parsedNodeTop = dhtmlObject.glIdStorageFind(dhtmlObject.parsingOn);
			for (var i = 0; i < dhtmlObject.parsedArray.length; i++) 
			{
				parsedNodeTop.htmlNode.childNodes[0].appendChild(dhtmlObject.parsedArray[i]);
			}
			dhtmlObject.lastLoadedXMLId = parentId;
		}
		if (dhtmlObject.parsCount == 1) 
		{
			dhtmlObject.parsingOn = null;
		}
		dhtmlObject.parsCount--;
	};

	//添加子结点项
	this.attachChildNode = function (parentObject, itemId, itemText, itemActionHandler, childs, level,txtType,hasChild) 
	{	 
		var Count = parentObject.childsCount;
		var Nodes = parentObject.childNodes;
		 
		Nodes[Count] = new treeItemObject(itemId, itemText, parentObject, this,level,txtType,hasChild);
		itemId = Nodes[Count].id;
		 
		parentObject.childsCount++;
		var tr = this.drawNewTr(Nodes[Count].htmlNode,level);
		
		 
		if (this.parsingOn == parentObject.id) 
		{
			this.parsedArray[this.parsedArray.length] = tr;
		}
		else 
		{
			parentObject.htmlNode.childNodes[0].appendChild(tr);
		}
		Nodes[Count].tr = tr;
		tr.nodem = Nodes[Count];
		if (parentObject.itemId == 0) {
			tr.childNodes[0].className = "hiddenRow";
		}		 
		return Nodes[Count];
	};
	 
	this.drawNewTr = function (htmlObject,level)
	{
		var tr = document.createElement("tr");
		var td1 = document.createElement("td");
		var td2 = document.createElement("td");
 		
		td2.nowrap = true; 
		td2.appendChild(htmlObject);
		tr.appendChild(td1);
		tr.appendChild(td2);
		return tr;
	};
	
	this.createItem = function (itemObject) 
	{
		var table = document.createElement("table");
		table.cellSpacing = 0;
		table.cellPadding = 0;
		table.border = 0;
		 
		table.style.margin = 0;
		table.style.padding = 0;
		var tbody = document.createElement("tbody");
		
		var tr = document.createElement("tr");
		
		var td1 = document.createElement("td");
		td1.style.width="10px";
		 
		var td2 = document.createElement("td");
		td2.style.cursor = "hand";
		itemObject.span = document.createElement("span");
		if(this.m_rootID != itemObject.itemId )
		{
			if(itemObject.hasChild && itemObject.hasChild>0)
			{
				//包含子元素
				var liObject = document.createElement("li");
//				liObject.style.listStyleType = "none";
				liObject.style.cssText = "float:left;list-style:none;text-align:left";
//				liObject.style.textAlign = "left";
				var img = new Image();
				img.src = this.m_imagePath + "/folderOpen.gif"; 
				liObject.appendChild(img);
				liObject.appendChild(document.createTextNode(itemObject.label) );
				itemObject.span.appendChild(liObject);
				liObject.closeable = true;
				liObject.that = this;
				liObject.txtLabel = itemObject.label;
				liObject.itemId = itemObject.itemId;
				Event.observe(liObject, "click", this.nodeItemClick.bindAsEventListener(liObject) );
			 
			}
			else
			{
				var that = this;
				//不包含子元素
				var liObject = document.createElement("li");
				liObject.itemId = itemObject.itemId;
				liObject.style.cssText = "float:left;list-style:none;text-align:left";
				var img = new Image(); 
				if(! itemObject.txtType )
				{
					img.src = this.m_imagePath + "/book_titel.gif"; 
					liObject.onclick = function()
					{
						if(that.m_countyActionHandler)
						{
							that.m_countyActionHandler(itemObject.itemId,itemObject.label);
						}
					};
					
				}
				else
				{ 
//					GPSHelper.GPSTypeList
					var imageName = "car.gif";
					for(var ele in GPSHelper.GPSTypeList)
					{
						if(	GPSHelper.GPSTypeList[ele][0] == itemObject.txtType)
						{
							imageName = GPSHelper.GPSTypeList[ele][2];
						}
					}
					img.src = this.m_imagePath + "/" + imageName; 
					liObject.onclick = function()
					{
						var idWithPrefix = liObject.itemId;
						var strCarCode = idWithPrefix.substring(that.m_gpscarPrifix.length);
						if(that.m_gpsActionHandler)
						{
							that.m_gpsActionHandler(strCarCode);
						}
					};
				}
				liObject.appendChild(img);
				liObject.appendChild(document.createTextNode(itemObject.label) );
				itemObject.span.appendChild(liObject);
				liObject.that = this;
				
				
				
	 		}
	  	 }
 		itemObject.span.style.display = "block";
		itemObject.span.style.paddingLeft = "0px";
		itemObject.span.style.paddingRight = "0px";
		
		td2.appendChild(itemObject.span);
		td2.parentObject = itemObject;
 
		td2.style.verticalAlign = "";
		td2.style.fontSize = "10pt";
		td2.nowrap = true;
		
		tr.appendChild(td1);
		tr.appendChild(td2);
		tbody.appendChild(tr);
		table.appendChild(tbody);
		return table;
	};
	
	
	//获取顶部的结点
	this.getXMLTopNode = function (tagName) 
	{
		if (this.xmlDoc.responseXML)
		{
			var temp = this.xmlDoc.responseXML.getElementsByTagName(tagName);
			var z = temp[0];
		} 
		else 
		{
			var z = this.xmlDoc.documentElement;
		}
		if (z) 
		{
			this._retry = false;
			return z;
		}
		else
		{
			return null;
		}
	};
	
	
	this.glIdStorageAdd = function (itemId, itemObject) 
	{
		if (this.glIdStorageFind(itemId)) 
		{
			itemId = itemId + "_" + (new Date()).valueOf();
			return this.glIdStorageAdd(itemId, itemObject);
		}
		this.idpull[itemId] = itemObject;
		this.pullSize++;
		return itemId;
	};
	
	//查找对象
	this.glIdStorageFind = function (itemId)
	{
		var z = this.idpull[itemId];
		if (z) 
		{
			return z;
		}
		return null;
	};
	
	this.nodeItemClick = function()
	{
		if(this.closeable)
		{
			this.that.closeNodeItem(this.itemId);
			this.childNodes[0].src = this.that.m_imagePath + "/folderClosed.gif";
		}
		else
		{
			this.that.openNodeItem(this.itemId);
			this.childNodes[0].src = this.that.m_imagePath + "/folderOpen.gif";
		}
		this.closeable = ! this.closeable;
		
		if(this.that.m_countyActionHandler)
		{
			this.that.m_countyActionHandler(this.itemId,this.txtLabel);
		}
		
	};
	
	this.openNodeItem = function (itemId) 
	{
		if (this.rootId == itemId) 
		{
			return 0;
		}
		var temp = this.glIdStorageFind(itemId);
		if (!temp) 
		{
			return 0;
		}
		this.hideChildNodes(temp,2);
	};
	this.closeNodeItem = function (itemId) 
	{
		if (this.rootId == itemId) 
		{
			return 0;
		}
		var temp = this.glIdStorageFind(itemId);
		if (!temp) 
		{
			return 0;
		}
		this.hideChildNodes(temp,1);
	};
	
	this.hideChildNodes = function(itemObject, mode) 
	{
	 	var Nodes = itemObject.htmlNode.childNodes[0].childNodes;
		var Count = Nodes.length;
		if (Count > 1) 
		{
			if (((Nodes[1].style.display != "none") || (mode == 1)) && (mode != 2)) 
			{
				nodestyle = "none";
			} 
			else 
			{
				nodestyle = "";
			}
			for (var i = 1; i < Count; i++) 
			{
				Nodes[i].style.display = nodestyle;
			}
		}
	};
 
	//项选中颜色设置
	this.setItemColor = function (itemId, defaultColor, selectedColor) 
	{
		if ((itemId) && (itemId.span)) 
		{
			var temp = itemId;
		} 
		else 
		{
			var temp = this.glIdStorageFind(itemId);
		}
		if (!temp) 
		{
			return 0;
		}
		else 
		{
			if (temp.i_sel) 	//选中标志
			{
				if (selectedColor) 
				{
					temp.span.style.color = selectedColor;
				}
			} 
			else
			{
				if (defaultColor) 
				{
					temp.span.style.color = defaultColor;
				}
			}
			if (selectedColor) 
			{
				temp.scolor = selectedColor;
			}
			if (defaultColor) 
			{
				temp.acolor = defaultColor;
			}
		}
	};
	
}
//树结点项信息
function treeItemObject(itemId, itemText, parentObject, treeObject,level,txtType,hasChild,actionHandler) 
{
	this.htmlNode = "";
	this.acolor = "";
	this.scolor = "";
	this.tr = 0;
	this.childsCount = 0;
	this.span = 0;
	this.closeble = 1;
	this.childNodes = new Array();
	this.treeNod = treeObject;
	this.label = itemText;
	this.parentObject = parentObject;
	this.level = level;
	this.txtType = txtType;
	this.actionHandler = actionHandler;
	this.hasChild = hasChild;
	this.itemId = itemId;
	 
//	this.imgs = new Array(treeObject.imageArray[0], treeObject.imageArray[1], treeObject.imageArray[2]);
	this.id = treeObject.glIdStorageAdd(itemId, this);
	 
	this.htmlNode = this.treeNod.createItem(this);
	
	this.htmlNode.objBelong = this;
	return this;
}