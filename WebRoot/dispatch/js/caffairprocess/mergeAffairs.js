/***********************************************************************

版 权：北京易华录信息技术股份有限公司 2009

文件名称：mergeAffairs.js
摘 要：	合并警情单

当前版本：1.0
作 者：LChQ  2009-4-10 

***************************************************************************/

function MergeAffairs(affairs)
{
	this.m_mergeAffairIDs = affairs;	//待合并警情标识
	this.m_mergeAffairData = null;		//待合并警情摘要信息
	this.mergeAffairList  = null;
	this.briefItems = ["alarmId","alarmTime","eventStateCode","eventSource","eventType","eventLevel","eventState"];
}

//读取警情摘要信息
MergeAffairs.prototype.readAffairBrief = function()
{
	if( ! this.m_mergeAffairIDs || "" == this.m_mergeAffairIDs || "null" == this.m_mergeAffairIDs || this.m_mergeAffairIDs.split(",").length<2 )
	{
		alert('警情信息丢失，请重试...');
		return;
	}
	
	var url = 'dispatch.mergeAffair.readMergeAffairs.d';
	var params = "AFFAIRIDS=" + this.m_mergeAffairIDs ;
	params = encodeURI(params);
	var theCaller = this;
	//读取数据
	new Ajax.Request(url, 
	{
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: function(xmlHttp)
			{
				var xmlDoc = xmlHttp.responseXML;
				if(xmlDoc.text == "" && 'null' != xmlHttp.responseText)
				{
					alert("获取警情信息失败.");
					return ;
				}
				theCaller.parseMergeAffairBrief(xmlDoc);
				theCaller.showMergeAffairBrief();
			}
	});
	
};
//解析警情信息
//将信息保存到数据中
MergeAffairs.prototype.parseMergeAffairBrief = function(xmlDoc)
{
	this.m_mergeAffairData = null;
	var results = xmlDoc.getElementsByTagName("row");
	if(0 < results.length)
	{	
		this.m_mergeAffairData = new Array();
		//循环获取各信息项
		for (var i = 0; i < results.length; i+=1)
		{
			var columnResults = results[i].getElementsByTagName("col");
			var dataItem = {};
			for(var j=0; j<columnResults.length; j+=1)
			{
		 		dataItem[this.briefItems[j]] = columnResults[j].text;
			}
			this.m_mergeAffairData.push(dataItem);
		}
	}
};

//显示警情摘要信息
MergeAffairs.prototype.showMergeAffairBrief = function()
{
	if( this.m_mergeAffairData )	//判断是否成功得到警情信息
	{
	 	if( this.m_mergeAffairData.length > 0)
	 	{
	 		this.mergeAffairList = new HtmlTableDrawer('divMergeAffairList');
			this.mergeAffairList.setTableStyle(table2Style);
			this.mergeAffairList.setTdStyle(td2Style);
			this.mergeAffairList.setThStyle( td2Style + ";background-color:#9abcff;");
			this.mergeAffairList.setColumnType(["CHECKBOX","TEXT","TEXT","TEXT","TEXT"]);
			this.mergeAffairList.setTableHead( ["选 择","报警单号","报警时间","事件来源","事件类型"] );  //,
			this.mergeAffairList.setCellWidth(['8%','25%','20%','24%','24%']);
		 	
			for(var i=0;i<this.m_mergeAffairData.length;i+=1)
			{
				this.addMergeAffairRow(this.m_mergeAffairData[i]);
			}
			this.mergeAffairList.submitTableHtml();
			this.mergeAffairList.setRowClickEvent(null,false);
	 	}
	}
};

//添加待合并警情行信息
MergeAffairs.prototype.addMergeAffairRow = function(data)
{
	var rowdata = new Array();
	rowdata.push("");
	rowdata.push(data['alarmId']);
	rowdata.push(data['alarmTime']);
	rowdata.push(data['eventSource']); 
 	rowdata.push(data['eventType']);
	this.mergeAffairList.addTableRow(rowdata,data['alarmId']);		//添加表格行数据
};

//合单事件处理
MergeAffairs.prototype.mergeHandler = function()
{
	var affairObject = this.getAffairInfo();
	var url = 'dispatch.mergeAffair.mergeAffairs.d';
 	var theCaller = this;
	//读取数据
	new Ajax.Request(url, 
	{
			method: 'post', 
			parameters: affairObject, 
			
			//读取完成后，放入内存数组
			onComplete: function(xmlHttp)
			{ 
				if(xmlHttp.responseText == "" || 'null' == xmlHttp.responseText)
				{
					window.returnValue = true;	//合单成功
					window.close();	
				}
				else
				{
					alert(xmlHttp.responseText);	//合单失败。
					return ;
				}
			}
	});
};

//获得主次单配置信息
MergeAffairs.prototype.getAffairInfo = function()
{
	var subAffairs = "";
	var mainAffair = "";
	if( this.mergeAffairList && this.mergeAffairList.m_HtmlRowList)
	{
		for(var i=0; i<this.mergeAffairList.m_HtmlRowList.length; i+=1)
		{
			var htmlRow = $(this.mergeAffairList.m_HtmlRowList[i]);	//获取数据行
			if( htmlRow)
			{
				var htmlCkBox = htmlRow.cells[0].childNodes[0];
				if( htmlCkBox.checked )	//判断选中状态，得到警情单编号
				{
					 mainAffair = htmlRow.info;
				}
				else
				{
					if("" == subAffairs )
					{
						subAffairs += htmlRow.info;
					}
					else
					{
						subAffairs += "," + htmlRow.info;
					}
				} 
			}
		}
	}
	
	return  {'SUBAFFAIRIDS':subAffairs,'MAINAFFAIRID':mainAffair};
};
//表格样式
var table2Style = 'background:#ffffff;border-top: 1 solid #000000;\
					border-right: 0 solid #000000;border-bottom: 0 solid #000000;\
									border-left: 1 solid #000000;border-collapse:collapse;\
									font-size:11px;align: center;text-align: center;width:100%;cursor:hand';
var td2Style = 'border-top: 0 solid #000000;border-right: 1 solid #000000;\
									border-bottom: 1 solid #000000;border-left: 0 solid #000000;\
									line-height: 16px;color: #000000;border-collapse : separate;\
									align: center;empty-cells:show;text-align: center;';	
									
									