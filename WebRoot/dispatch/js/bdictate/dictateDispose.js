/**
 * 
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 文件名称：dictate.js
 * 摘 要：接收领导指令指示。


 * 当前版本：1.0
 * 作 者：LChQ  2009-4-16
 * 修改人：
 * 修改日期：
 */
 //add by wxt 添加查询按钮事件
 //定义GRID对象
mygridt_dictate_query = new dhtmlXGridObject('t_dictate_query');
mygridt_dictate_query.setSkin('gray');
pagingObject = null;

//设置初始SQL
var selectSQL = "SELECT '0',t1.denoteid,t1.leadername,to_char(t1.denotetime,'yyyy-MM-dd HH24:mi'),";
selectSQL += "	 t1.denotedesc , ";
selectSQL += " tc1.name dSource,tc2.name dType,tc3.name cState,t1.createrecorder FROM ";
selectSQL += "	t_Attemper_LeaderDenote t1,T_Attemper_RunDenote t2, t_attemper_code tc1,t_attemper_code tc2,t_attemper_code tc3 ";
selectSQL += "	WHERE t1.denoteid=t2.denoteid AND t2.finishunit='" + userCountyCode + "' AND t1.denotesource=tc1.id(+) AND t1.denotetype=tc2.id ";
selectSQL += "	AND t2.state = tc3.id(+) AND t2.state <>'013001' ";
window.attachEvent('onload',initDictatePage);
var dictateObject;
function initDictatePage()
{   
	dictateObject = new DictateDisposal();
	$('hrefIncept').onclick = function()
	{
		dictateObject.setPageUI('incept');
	};
	$('hrefFullfill').onclick = function()
	{
		//dictateObject.setPageUI('fullfill');
		
		if( dictateObject.fullfillObject && dictateObject.fullfillObject.m_fullfillId)
		{
		    dictateObject.setPageUI('fullfill');
			dictateObject.fullfillObject.getFullfillDataByID();
		}
		else
		{
			alert('请选择具体的指令指示信息!');
			dictateObject.setPageUI('incept');
		}
	};
	
	$('btnQuery').onclick = function()
	{
		dictateObject.queryDictate();
	};
	
	$('btnFullfillSave').onclick = function()
	{
		dictateObject.fullfillObject.writeFullfillData();	//保存落实信息
	};
	
	mygridt_dictate_query.setOnRowSelectHandler(dictateObject.rowClickedHandler,true);
}


 function LeaderDictate()
 {
 	this.m_dictateData = null;
 	
 	this.dictateDataItems = ['dictateId','leaderText','dictateTime','dictateContent','dictateSource','dictateType','disposeState','recorder','remark','fullfillId','fullfillState'];
 }
 
 
LeaderDictate.prototype.getDictateDataByID = function(dictateId)
{
	if(! dictateId  || 0==dictateId.length )
	{
		return;
	}
	var url = 'dispatch.dictate.getDictateById.d';
	var params = "DICTATEID=" + dictateId;
	params += "&USERCOUNTYCODE=" + userCountyCode;
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
				if(xmlDoc.text == "")
				{
					alert("获取领导指令指示信息失败.");
					return ;
				}
				theCaller.parseDictateDetail(xmlDoc);
				theCaller.showDictateDetail();
			}
	});
};
 

//解析指令指示信息
//将相应信息保存到数据对象中
LeaderDictate.prototype.parseDictateDetail = function(xmlDoc)
{
	this.m_dictateData = null;
	this.m_dictateData = {};		//初始化视频详细信息数据对象
	var results = xmlDoc.getElementsByTagName("row");
	 
	if(0 < results.length)
	{	
		//循环获取各信息项
		results = results[0].getElementsByTagName("col");
		for (var i = 0; i < results.length; i+=1)
		{
		 	this.m_dictateData[this.dictateDataItems[i]] = results[i].text;
		}
	}
};

LeaderDictate.prototype.showDictateDetail = function()
{
	if( this.m_dictateData )
	{
		 $('txtDictateTime').innerHTML = this.m_dictateData.dictateTime;
		 $('txtDictateSource').innerHTML = this.m_dictateData.dictateSource;
		 $('txtLeaderName').innerHTML = this.m_dictateData.leaderText;
		 $('txtDictateType').innerHTML = this.m_dictateData.dictateType;
		 $('txtFullfillState').innerHTML = this.m_dictateData.disposeState;
		 $('txtDictateRecoder').innerHTML = this.m_dictateData.recorder;
		 $('tdDictateContent').innerHTML = this.m_dictateData.dictateContent;
		 
		 dictateObject.fullfillObject = new FullfillInfo(this.m_dictateData.fullfillId,this.m_dictateData.fullfillState);
	}
};




 function DictateDisposal()
 {
 	LeaderDictate.call(this);
 	this.m_dictateList = null; 
 	
 	this.fullfillObject = null; 
 }
 DictateDisposal.prototype = new LeaderDictate();
 


//设置页面状态
DictateDisposal.prototype.setPageUI = function(stage)
{
	switch(stage)
	{
		case "incept": 
			$('hrefIncept').style.backgroundColor = "#99A";
			$('hrefFullfill').style.backgroundColor = "transparent";
			$('tableDictateContent').style.display = "";
			$('trFullfillContent').style.display = "none";
			
			break;
		case "fullfill":
			$('hrefIncept').style.backgroundColor = "transparent";
			$('hrefFullfill').style.backgroundColor = "#99A";
			$('tableDictateContent').style.display = "none";
			$('trFullfillContent').style.display = "";
			break;
		default:
			$('hrefIncept').style.backgroundColor = "#99A";
			$('hrefFullfill').style.backgroundColor = "transparent";
			$('tableDictateContent').style.display = "";
			$('trFullfillContent').style.display = "none";
			break;
	}
		
};



//查询指令指示
DictateDisposal.prototype.queryDictate = function()
{   
	var sqlBuffer = "";
	if(0 < $('txtBgIncept').value.length)
	{
		sqlBuffer += "AND t2.accepttime>=to_date('" + $('txtBgIncept').value + "','yyyy-MM-dd HH24:mi')";
	}
	if(0 < $('txtTmnIncept').value.length)
	{
		sqlBuffer += " AND t2.accepttime<=to_date('" + $('txtTmnIncept').value + "','yyyy-MM-dd HH24:mi')";
	}
	pagingObject.setParams(0,-1,12,selectSQL + sqlBuffer);
	pagingObject.getPageData();
};


DictateDisposal.prototype.rowClickedHandler = function(rowId,cellId)
{
	var grid = mygridt_dictate_query;
	var dictateId = null;
//	alert(grid.getSelectedId());
//	alert(rowId  + " ,rowIndex=" + grid.rowsCol[rowId].rowIndex +" ,getRowById=" )
	
	if(0 != cellId  )
	{
		dictateId = grid.getRowById(rowId).cells[1].innerHTML;
		 
		var rowCount = grid.getRowsNum();          
		for (var i = 0; i < rowCount; i+=1) 
		{
			var idd = grid.getRowById(i).idd;
			var gIndex = dictateObject.getGridRowIndex(idd,rowCount);
			
			if(null == gIndex )
			{
				continue;
			}
			if ( grid.getRowById(i).cells[1].innerHTML == dictateId )
			{ 
				
			 	grid.cells2(gIndex, 0).setValue(1);
			}
			else
			{
				grid.cells2(gIndex, 0).setValue(0);
			}
		}
	}
	if( null != dictateId && 0<dictateId.length)
	{
		dictateObject.getDictateDataByID(dictateId);
	}
	dictateObject.setPageUI('incept');
};

DictateDisposal.prototype.getGridRowIndex = function( idd,rowCount)
{
	for (var j = 0; j < rowCount; j+=1) 
	{
		if( mygridt_dictate_query.rowsCol[j].idd == idd )
		{
			return j;
		}
	}
	return null;
};




//落实信息
function FullfillInfo(p_fullfillId,p_stateCode)
{
	this.m_fullfillId = p_fullfillId;	//落实信息的标识
	this.m_stateCode  = p_stateCode;
	
	this.m_completeCode = '013004';
	this.m_fullfillData = null;	//已经落实信息
	this.p_fullfillData	= null; //页面获取落实信息
 	this.fullfillDataItems = ['fullfillId','dictateId','inceptTime','fullfillState','fullfillTime','fullfillContent','recorder'];
}

//通过落实记录ID获取,落实信息
FullfillInfo.prototype.getFullfillDataByID = function()
{
	if(! this.m_fullfillId  || 0== this.m_fullfillId.length )
	{
		alert('请选择一条指令指示信息!');
		return;
	}
	var url = 'dispatch.dictate.getFullfillById.d';
	var params = {};
	params['FULLFILLID'] = this.m_fullfillId;
	params['USERCOUNTYCODE'] = userCountyCode;
	 
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
				if(xmlDoc.text == "")
				{
					alert("获取指令指示落实信息失败.");
					return ;
				}
				theCaller.parseFullfillDetail(xmlDoc);
				theCaller.showFullfillDetail();
			}
	});
};
 

FullfillInfo.prototype.parseFullfillDetail = function(xmlDoc)
{
	this.m_fullfillData = null;
	this.m_fullfillData = {};		//初始化落实信息数据对象
	var results = xmlDoc.getElementsByTagName("row");
	 
	if(0 < results.length)
	{	
		//循环获取各信息项
		results = results[0].getElementsByTagName("col");
		for (var i = 0; i < results.length; i+=1)
		{
		 	this.m_fullfillData[this.fullfillDataItems[i]] = results[i].text;
		}
	}
};

//显示落实信息
FullfillInfo.prototype.showFullfillDetail = function()
{
	 if(this.m_fullfillData)
	 {
	 //['fullfillId','dictateId','infoRecorder','inceptTime','fullfillState','fullfillTime','fullfillContent','recorder']
	 
	 	$('txtInceptTime').value = this.m_fullfillData.inceptTime;
	 	$('txtFullfilltime').value = this.m_fullfillData.fullfillTime;
	 	
	 	$('txtFullfillCounty').value = userCountyText;
	 	
	 	if(this.m_completeCode == this.m_fullfillData.fullfillState)
	 	{
	 		$('ckFullfillComplete').checked = true;
	 	}
	 	else
	 	{
	 		$('ckFullfillComplete').checked = false;
	 	}
	 	if($('ckFullfillComplete').checked)
	 	{
	 		$('btnFullfillSave').style.display = "none";
	 	}
	 	else
	 	{
	 		$('btnFullfillSave').style.display = "";
	 	}
	 	
	 	
	 	$('txtFullfillContent').value = this.m_fullfillData.fullfillContent;
	 	if("" == this.m_fullfillData.recorder)
	 	{ 
	 		$('txtFullfillRecorder').value = userSocialName;
	 	}
	 	else
	 	{
	 		if(-1 == this.m_fullfillData.recorder.indexOf(userSocialName))
	 		{
	 			$('txtFullfillRecorder').value = this.m_fullfillData.recorder ; //+ "," +  userSocialName;
	 		}
	 		else
	 		{
	 			$('txtFullfillRecorder').value = this.m_fullfillData.recorder;
	 		}
	 	}
	 }
};

//获取页面数据
FullfillInfo.prototype.getPageData = function()
{
	this.p_fullfillData	= null;
	this.p_fullfillData	= {};
	
	this.p_fullfillData['COMPLETETIME'] = $('txtFullfilltime').value;
	if($('ckFullfillComplete').checked )
	{
		this.p_fullfillData['STATE'] = this.m_completeCode;
	}
	else
	{
		this.p_fullfillData['STATE'] = this.m_stateCode;
	}
	
	this.p_fullfillData['CONTENT'] = $('txtFullfillContent').value;
	this.p_fullfillData['RECORDER'] = $('txtFullfillRecorder').value;
};

//保存落实信息
FullfillInfo.prototype.writeFullfillData = function()
{
	this.getPageData();
	var url = 'dispatch.dictate.writeFullfillData.d';
	var params = this.p_fullfillData;
	params['FULLFILLID'] = this.m_fullfillId;
//	params['USERCOUNTYCODE'] = userCountyCode;
	 
	//读取数据
	new Ajax.Request(url, 
	{
			method: 'post', 
			parameters: params, 
			
			//读取完成后，放入内存数组
			onComplete: function(xmlHttp)
			{
				if(xmlHttp.responseText != "")
				{
					alert(xmlHttp.responseText);
					return ;
				}
			}
	});
};


 
function showDictateInfo(linkObject)
{
	alert(linkObject.innerHTML);
}


//Grid构造
function getT_dictate_query()
{
	mygridt_dictate_query.setImagePath('../../../sm/image/table/');
	mygridt_dictate_query.setHeader('选中,1,领导姓名,指示时间,指示内容,指示来源,指示类型,落实状态,记录人姓名');
	mygridt_dictate_query.setColSorting('int,str,str,str,str,str,str,str,str');
	mygridt_dictate_query.setColTypes('ch,ro,ro,ro,ro,ro,ro,ro,ro');
	mygridt_dictate_query.setInitWidths('50,0,110,120,180,180,90,90,90');
	mygridt_dictate_query.setColAlign('center,center,center,center,center,center,center,center,center');
	mygridt_dictate_query.init();
	 
	pagingObject = new DhtmlPaging(0,-1,12,selectSQL,$('divPagingButton'),pagingSetting);
	pagingObject.setDataGrid(mygridt_dictate_query);
 	pagingObject.getPageData();
}

//设置换页信息
function pagingSetting()
{
	var pageIndex = mygridt_dictate_query.getUserData('','pageIndex');
	var pageCount = mygridt_dictate_query.getUserData('','pageCount');
	var pageSize = mygridt_dictate_query.getUserData('','pageSize');
	var selectSQL =  mygridt_dictate_query.getUserData('','selectSQL');
	pagingObject.setParams(pageIndex,pageCount,pageSize,selectSQL);
};
getT_dictate_query();


