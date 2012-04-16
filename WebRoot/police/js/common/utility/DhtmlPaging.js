
/**分页对象类
*  LChQ 2008-12
*/
function DhtmlPaging(pageIndex,pageCount,pageSize,selectSQL,holder,pagingHandler)
{
	//分页状态数据定义	var m_dataGrid = null;
	var m_pageIndex = pageIndex?pageIndex:0;
	var m_pageCount = pageCount?pageCount:1;
   	var m_pageSize  = pageSize?pageSize:10;

	var m_selectSQL = selectSQL;
	var m_holder    = holder;			//分页导航所在容器对象
   	var m_infoText  = "";
   	
   	//分页事件数据处理函数
   	var m_pagingHandler = pagingHandler;
   	
   	var mySelf = this;
   	
   	//页面分页对象的标识
   	var turnPagingId = "txtTurnPage";
   	var myTableId = "paging_table"
   	
   	var strDBConfig = null;	//数据配置名称
   	this.setDBConfig = function(dbConfig)
   	{
   		strDBConfig = dbConfig;
   	};
   	
   	//建立表
   	var createTable = function(tableId)
   	{
   		var table = document.createElement("table");
   		if(tableId)
   		{
   			table.id = tableId;
   		}
   		else
   		{
   			table.id = myTableId;
   		}
   		//align:center;width:100%;
   		table.style.cssText = "text-align:right";
   		return table;
   	};
   	
   	//建立行
   	var createTr = function(trId)
   	{
   		var tr = document.createElement("tr");
   		if(trId)
   		{
   			tr.id = trId;
   		}
   		tr.style.cssText = "width:100%";
   		return tr;
   	};
   	
   	//建立单元格
   	var createTd = function(tdId)
   	{
   		var td = document.createElement("td");
   		if(tdId)
   		{
   			td.id = tdId;
   		}
   		return td;
   	};
   	
   	//建立超链接
   	var createA = function(aId,text,fun)
   	{
   		var a = document.createElement("a");
   		if(aId)
   		{
   			a.id = aId;
   		}
   		a.herf = "javascript:void(0);";
   		a.onclick = fun;
   		a.innerText = text;
   		a.style.cssText = "color: #0066CC;text-decoration: underline;cursor:pointer;";
   		return a;
   	};
   	
   	//建立输入框
   	var createInput = function(inputId,type)
   	{
   		var input = document.createElement("input");
   		if(inputId)
   		{
   			input.id = inputId;
   		}
   		input.type = type;
   		return input;
   	};
   	
   	//设置分页数据处理函数
   	this.setPagingHandler = function(handler)
   	{
   		m_pagingHandler = handler;
   	};
   	
   	//设置分页执行的SQL
   	this.setSelectSQL = function(strSQL)
   	{
   		m_selectSQL = strSQL;
   	};
   	
   	//建立分页导航页面元素
   	this.buildNavigation = function()
   	{
   		if($(myTableId))
   		{
   			$(myTableId).parentNode.removeChild($(myTableId));
   		}
   		var vTable = createTable();
   		
   		var vTbody = document.createElement("tbody");//width:100%;
   		vTbody.style.cssText = "align:right;text-align:right;";
   		vTable.appendChild(vTbody);
   		var vTr = createTr();
   		
   		var vTd = createTd();
   		vTd.appendChild(createA('','首页',this.onFirstPage));
   		vTd.style.cssText = "align:center;width:28;font-size:9pt";
   		vTr.appendChild(vTd);
   		
   		vTd = createTd();
   		vTd.appendChild(document.createTextNode('|'));
   		vTd.style.cssText = 'align:center;width:1;font-size:9pt';
   		vTr.appendChild(vTd);
   		
   		vTd    = createTd();
   		vTd.appendChild(createA('','上页',this.onPrevPage));
   		vTd.style.cssText = "align:center;width:28;font-size:9pt";
   		vTr.appendChild(vTd);
   		
   		vTd = createTd();
   		vTd.appendChild(document.createTextNode('|'));
   		vTd.style.cssText = 'align:center;width:1;font-size:9pt';
   		vTr.appendChild(vTd);
   		
   		vTd    = createTd();
   		vTd.appendChild(createA('','下页',this.onNextPage));
   		vTd.style.cssText = "align:center;width:28;font-size:9pt";
   		vTr.appendChild(vTd);
   		
   		vTd = createTd();
   		vTd.appendChild(document.createTextNode('|'));
   		vTd.style.cssText = 'align:center;width:1;font-size:9pt';
   		vTr.appendChild(vTd);
   		
   		vTd    = createTd();
   		vTd.appendChild(createA('','末页',this.onLastPage));
   		vTd.style.cssText = "align:center;width:28;font-size:9pt";
   		vTr.appendChild(vTd);
   		
   		vTd    = createTd();
   		vTd.style.cssText = "width:70;font-size:9pt";
   		var vInput = createInput(turnPagingId,'text');
   		vInput.style.cssText = 'width:30;height:16;font-family:宋体;font-size:9pt';
   		vInput.onkeypress=function()
   		{
   			return (/[\d]/.test(String.fromCharCode(event.keyCode)))
   		};
   		vTd.appendChild(vInput);
   		vTd.appendChild(document.createTextNode(" "));
   		vTd.appendChild(createA('','跳转',this.onTurnToPage));
   		vTr.appendChild(vTd);
   		
   		vTd    = createTd();
   		var tnum = parseInt(m_pageIndex);
   		var num = 0;
   		if(! isNaN( tnum ))
   		{
   			num = tnum + 1;
   		}
   		if(! m_pageCount)
   		{	
   			m_pageCount = 0;
   		}
   		
   		var numInfo = "当前页:" + num + "/" + m_pageCount;
   		vTd.appendChild(document.createTextNode(numInfo));
   		vTd.style.cssText = "font-size:9pt;width:100;color: #0066CC;";
   		vTr.appendChild(vTd);
   		
   		if(!m_infoText)
   		{
   			m_infoText = "";
   		}
   		vTd    = createTd();
   		vTd.appendChild( document.createTextNode(m_infoText));
   		vTd.style.cssText = "align:right;font-size:9pt";
   		vTr.appendChild(vTd);
   		
   		vTbody.appendChild(vTr);
   	
   		if(m_holder)
   		{
   			m_holder.appendChild(vTable);
   		}
   	};
   	
   	//SQL特殊字符转化 
  	this.convertSql = function(sql) 
  	{
		if (sql == null) {
		 return sql;
		}
		sql = sql.replace(/=/g, "＝");
		sql = sql.replace(/%/g, "％");
		sql = sql.replace(/\+/g, "＋");
		return sql;
	};
	
	//获取页数据
	this.getPageData = function()
	{
		var recordsSum = null;
		if(m_infoText)
		{
			recordsSum = m_infoText.substr(m_infoText.indexOf(":") +1 );
		}
	
		var uri = 'dispatch.paging.dhtmlPaging.d?pageIndex='+m_pageIndex+'&pageSize='+m_pageSize ;
		uri += '&selectSQL=' +  this.convertSql(m_selectSQL) + '&pageCount=' +  m_pageCount;
		
		if(recordsSum)
		{
			uri += '&recordsSum=' + recordsSum;
		}
		if(strDBConfig && "" != strDBConfig)
		{
			uri += '&strDBConfig=' +  strDBConfig;
		}
		uri = encodeURI(uri);
		if(m_dataGrid)
		{
			m_dataGrid.clearAll();
			m_dataGrid.loadXML(uri,m_pagingHandler);
		}
		else
		{
			params = "";
			new Ajax.Request(uri, {method:"post", parameters:params,
							 onComplete: 
								function(xmlHttp)
								{
									mySelf.pagingComplete(xmlHttp); 
								}
			});
		}
	};
	
	//设置GRID对象
	this.setDataGrid = function( p_dataGrid )
	{
		m_dataGrid = p_dataGrid;
	};
	
	//设置分页参数
	this.setParams = function(pageIndex,pageCount,pageSize,selectSQL)
	{
		m_pageIndex = pageIndex;
   		m_pageCount = pageCount;
   		m_pageSize 	= pageSize;
   		m_selectSQL = selectSQL;
 
   		mySelf.buildNavigation();
	};
	
	//页数据获取完成
	this.pagingComplete = function(xmlHttp)
	{
		var xmlDoc = xmlHttp.responseXML;
		var pagingInfo = xmlDoc.getElementsByTagName("userdata");
    	if(pagingInfo.length >0 )
    	{
    		//getAttribute('pageIndex');
    		m_pageIndex = pagingInfo[0].text;
    		m_pageCount = pagingInfo[1].text;
    		m_pageSize = pagingInfo[2].text;
    		m_selectSQL = pagingInfo[3].text;
    		
    		if(m_pageCount > 1)
    		{
    			mySelf.buildNavigation();
    		}
    	}
    	else
    	{
    		m_pageCount = 0;
    		m_pageIndex = -1;
    		mySelf.buildNavigation();
    	
    	}
    	
    	//调用数据处理函数
		if(m_pagingHandler)
		{
			m_pagingHandler(xmlHttp);
		}
	};
	//重新获取当前也数据
	this.getCurrentPage = function()
	{
		mySelf.getPageData();
	};
	
	//获取第一页数据
	this.onFirstPage = function() 
	{
		m_pageIndex = 0;
		mySelf.getPageData();
	};
	//获取下一页数据
	this.onNextPage = function() 
	{
		if (m_pageIndex < (parseInt(m_pageCount)-1)) 
		{
			m_pageIndex  =  parseInt(m_pageIndex) + 1;
			mySelf.getPageData();
		}
	};
	//获取上一页数据
	this.onPrevPage = function() 
	{
		if (m_pageIndex > 0) 
		{
			m_pageIndex = parseInt(m_pageIndex) - 1 ;
			mySelf.getPageData();
		}
	};
	//获取最后一页数据
	this.onLastPage = function() 
	{
		m_pageIndex = parseInt(m_pageCount) -1;
		mySelf.getPageData();
	};
	
	//转到具体页
	this.onTurnToPage = function() 
	{
		pageIndex = $F(turnPagingId);
		try
		{
			pageIndex = parseInt(pageIndex);
		}
		catch(e)
		{
			alert(pageIndex + "不是有效的数字");
			return;
		}
		
		if(pageIndex<0)
		{
			m_pageIndex = 0;
		}
		else if(pageIndex > m_pageCount)
		{
		   m_pageIndex = parseInt(m_pageCount-1);
		}
		else
		{
			m_pageIndex = pageIndex-1;
		}
		mySelf.getPageData();
	};
}
