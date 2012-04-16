

//绘制表格
function HtmlTableDrawer(holderId,columnTypeArray,tableStyle,thStyle,tdStyle)
{
	this.htmlHolder = document.getElementById(holderId);
	this.m_columnType = columnTypeArray;
	
	this.mtable_style = tableStyle;
	this.mth_style = thStyle;
	this.mtd_style = tdStyle;
	this.m_cellWidth = null;
	this.tableObject = new DhtmlTableObject(null,tableStyle);
	this.divt=null
	this.m_HtmlRowList = null;
	this.m_HtmlTitle = null;
	this.mouseoverhandler = function()
	{ 
		this.style.backgroundColor = "silver";
	};
	this.mouseouthandler = function()
	{
		this.style.backgroundColor = "";
	};
};

//设置列类别
HtmlTableDrawer.prototype.setColumnType = function(columnTypeArray)
{
	 this.m_columnType = columnTypeArray;
};
HtmlTableDrawer.prototype.setCellWidth = function(widthArray)
{
	this.m_cellWidth = widthArray;
};

//设置表头
HtmlTableDrawer.prototype.setTableHead = function(headArray)
{
 	var headRowHtml = document.createElement("tr");
	for(var i=0; i<headArray.length ; i+=1)
	{
		var thCell = document.createElement("th");
		thCell.appendChild( document.createTextNode(headArray[i]) ) ;
		if(this.mth_style)	//添加样式
		{
			thCell.style.cssText = this.mth_style;
		}
		headRowHtml.appendChild( thCell ) ;
	} 
//	headRowHtml.className="scrollColTableHeadTr";
	this.m_HtmlTitle = headRowHtml;
	
	this.tableObject.htmlTbody.appendChild(this.m_HtmlTitle);
};


HtmlTableDrawer.prototype.createTableRowDIV = function()
{
	
	var tableDIV = document.createElement("div");
	tableDIV.id="tabledivid_gun";
	tableDIV.style.overflow="auto";
	tableDIV.style.height="10";
	this.tableObject.htmlTbody.appendChild(tableDIV);
	
	this.divt=tableDIV;
	return tableDIV;
	
};

//添加数据项到表中
HtmlTableDrawer.prototype.addTableRow = function(dataArray,tag)
{
	
	var newHtmlRow = document.createElement("tr");
	newHtmlRow.id = 'db_row_' + tag ; //(new Date()).valueOf();
	newHtmlRow.info = tag;
	if(null == this.m_HtmlRowList)
	{
		this.m_HtmlRowList = new Array();
	}
	 
	for(var i=0 ; i< dataArray.length; i+=1)
	{
		var newCellHtml = this.getCellHtml(dataArray[i], this.m_columnType[i] );
		if( this.m_cellWidth  && this.m_cellWidth[i] )
		{
			newCellHtml.style.width = this.m_cellWidth[i];
		}
		if(this.mtd_style )
		{
			newCellHtml.style.cssText = this.mtd_style;
		}
		newHtmlRow.appendChild(newCellHtml);
	}
	
	this.tableObject.htmlTbody.appendChild(newHtmlRow);
//	this.divt.appendChild(newHtmlRow);
	
	this.m_HtmlRowList.push(newHtmlRow.id);	
	return newHtmlRow;
};

//构造单元格内数据
HtmlTableDrawer.prototype.getCellHtml = function(cellData,cellType)
{
	var cellHtml = document.createElement("td");
	switch (cellType)
	{
		  case "TEXT":	//文本信息
			cellHtml.appendChild(this.getTextCell(cellData));
			break;
		  case "HREF":	//链接信息
			cellHtml.appendChild(this.getHrefCell(cellData ));
			break;
		  case "LINKBUTTON":	//文本按钮
			cellHtml.appendChild(this.getLinkbuttonCell(cellData));
			break;
		  case "DELETEBUTTON":	//删除按钮，未用
//			cellHtml.appendChild( this.getDeleteButtonCell(cellData));
			break;
		  case "CHECKBOX":		//选择框
			cellHtml.appendChild( this.getCheckboxCell(cellData) );
			break;
		  case "IMAGEBUTTON":	//图片按钮
			cellHtml.appendChild( this.getImageButtonCell(cellData));
			break;
	}
	return cellHtml;
};

//构造文本单元格
HtmlTableDrawer.prototype.getTextCell = function(cellData)
{
	var txtCell = document.createTextNode( cellData);
	return txtCell;
};

//添加超链接 
HtmlTableDrawer.prototype.getHrefCell = function(cellData)
{
	var hrefCell = document.createElement("a");
	var target = "";
	if(cellData.target)
	{
		target = cellData.target;
	}
	var prop = "width=600,height=400";
	if(cellData.prop)
	{
		prop = cellData.prop;
	}
	hrefCell.href = "#" ;
	hrefCell.onclick = function()
	{
		window.open(cellData.url,target,prop);
	};
	hrefCell.innerHTML = cellData.text;
 	return hrefCell;
};

//构造linkbutton
HtmlTableDrawer.prototype.getLinkbuttonCell = function(cellData)
{
	var linkbuttonCell = document.createElement("a");
	linkbuttonCell.id = "utable_" + (new Date()).valueOf();
	linkbuttonCell.href = "#" ;
	
	var btnHtml = null;
	if(cellData.text.indexOf("<")!=-1 && cellData.text.indexOf(">")!=-1)
	{
		btnHtml =  document.createElement(cellData.text);
	 
		btnHtml.onclick = cellData.clickHandler;
		linkbuttonCell.appendChild( btnHtml );
	}
	else
	{
		linkbuttonCell.style.color="blue";
		linkbuttonCell.innerHTML = cellData.text;
		linkbuttonCell.onclick = cellData.clickHandler;
	}
	return linkbuttonCell;
};
//图片按钮构造
HtmlTableDrawer.prototype.getImageButtonCell = function(cellData)
{
	var img = document.createElement("img");
	img.src = cellData.src;
	img.style.cursor = "hand";
 	img.onclick = cellData.clickHandler;
	return img;
};

//checkbox构造
HtmlTableDrawer.prototype.getCheckboxCell = function(cellData)
{
 	//checked="checked" value="w" name="checkbox3"> 
	var inputCell = document.createElement("input");
	inputCell.type = "checkbox";
	if(cellData.checked)
	{
		inputCell.checked = "checked";
	}
	if(cellData.clickHandler)
	{
 		inputCell.onclick = cellData.clickHandler;
 	}
 	if(cellData.value)
	{
 		inputCell.value = cellData.value;
 	}
	return inputCell;
};

//设置table样式
HtmlTableDrawer.prototype.setTableStyle = function(pStyle)
{
	this.tableObject.htmlTable.style.cssText = pStyle;
};
	
//设置单元格样式
HtmlTableDrawer.prototype.setTdStyle = function(pStyle)
{
	this.mtd_style = pStyle;
};
	
HtmlTableDrawer.prototype.setThStyle = function(pStyle)
{
	this.mth_style = pStyle;
};

//提交表格信息
HtmlTableDrawer.prototype.submitTableHtml = function()
{
	this.htmlHolder.innerHTML = this.tableObject.htmlTable.outerHTML;
 
};

//设置行点击事件
HtmlTableDrawer.prototype.setRowClickEvent = function(rowClickHandler,canMuliti)
{
	if(! this.m_HtmlRowList )
	{
		return;
	}
	for(var j=0;j<this.m_HtmlRowList.length;j+=1)
	{
		//循环设置每行的事件
		var htmlRow = $(this.m_HtmlRowList[j]);
		if( htmlRow )
		{
			htmlRow.onmouseover = this.mouseoverhandler;
			htmlRow.onmouseout = this.mouseouthandler;
			htmlRow.onclick = function()
			{
				var target = event.srcElement;
				if(target.tagName.toUpperCase() == "INPUT" && target.type.toUpperCase() == "CHECKBOX" )
				{
					if(canMuliti )
					{
						return;
					}
				}
				var t = 0;
				while(target.tagName.toUpperCase() != "TR" || t>5)
				{
					target = target.parentNode;
					t += 1;
				}
				var rowParent =  target.parentNode;
				
				for(var k=1; k<rowParent.rows.length; k+=1 )	//设置选中状态
				{
					var ckBox = rowParent.rows[k].cells[0].childNodes[0];
					if(ckBox.tagName && ckBox.tagName.toUpperCase() == "INPUT" && ckBox.type.toUpperCase() == "CHECKBOX" )
					{
						if(target.info == rowParent.rows[k].info )
						{ 
							ckBox.checked = true;
						}
						else
						{
							ckBox.checked = false;
						}
					}
				}
				
				if(rowClickHandler)	//调用行选择事件
				{
					rowClickHandler(target.info);
			 	}
			};
		}
	}
};
function DhtmlTableObject(tableStyleClass,tableStyle)
{
	this.htmlTable = document.createElement("table");
	this.htmlTableObjectId = "nnd_myid" + (new Date()).valueOf();
	this.htmlTable.id= this.htmlTableObjectId ;
	if(tableStyleClass)
	{
		this.htmlTable.className = tableStyleClass;
	}
	
	if(tableStyle)
	{
		this.htmlTable.style.cssText = tableStyle;
	}
	 
	this.htmlTbody = document.createElement("tbody");
	this.htmlTbodyObjectId = "nnd_mybid" + (new Date()).valueOf()
	this.htmlTbody.id = this.htmlTbodyObjectId;
	 
	this.htmlTable.appendChild(this.htmlTbody);
//	this.htmlTbody.style.align="center";
}


