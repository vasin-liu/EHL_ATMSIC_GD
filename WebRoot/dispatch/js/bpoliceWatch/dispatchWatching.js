/**
	版 权：北京易华录信息技术股份有限公司 2009
	文件名称：dispatchWatching.js
	摘 要：警情信息处理,警情信息的实时更新	当前版本：1.0
	作 者：LChQ  2009-4-9 
   */

var watching = new DispatchWatching();
var btnIDList = ["addPolice","btnAffairProcess","btnMonitor","btnAppraise","btnComplement"];

var appraiseObject = null; //评价信息对象

var radioList = [['radioAllAffair',''],['radioUnhandleAffair','unhandle'],['radioProcessingAffair','watching'],['radioCompleteAffair','complete']];

//初始化警情信息页面【左侧列表及按钮】
function initWatching(){
	
	watching.readNoticedAffair();	//读取未处理警情和需关注警情信息
	watching.setRefreshInterval(msgListRefreshInterval);
	$('btnMerge').onclick = function(){	//合并警情
		watching.mergeUnhandleAffairs();
	};
 
	$('ahrefAllAffair').onclick = function(){//全部
		watching.showAffairDetail();
		//11-01修改 start
		$("btnAffairProcess").style.display="";
		//11-01修改 end
		$('radioAllAffair').checked = true;
	};
	
	$('ahrefUnhandle').onclick = function(){ //未处理		watching.showAffairDetail('unhandle');
		$('radioUnhandleAffair').checked = true;
	};
	$('ahrefProcessing').onclick = function(){//正在处理
		watching.showAffairDetail('watching');
		$('radioProcessingAffair').checked = true;
	};
	$('ahrefComplete').onclick = function(){//处理完毕
		//11-01修改 start
		$("btnAffairProcess").style.display="none";
		//11-01修改 end
		watching.showAffairDetail('complete');
		$('radioCompleteAffair').checked = true;		
	};
	
	for(var i=0; i<btnIDList.length; i+=1){
		if( $(btnIDList[i]) ){
			$(btnIDList[i]).attachEvent('onclick',watching.btnClickUIsetting);
			$(btnIDList[i]).style.border ="1 black";
		 	$(btnIDList[i]).style.borderStyle = "solid";
		 	$(btnIDList[i]).style.backgroundColor = "transparent";
		   
		 	$(btnIDList[i]).style.color = "blue";
		 	$(btnIDList[i]).style.fontSize = "9pt";
		}
	}
	
	//定时刷新警情列表
	watching.o_AlarmInterval = setInterval("watching.readNoticedAffair('init')", watching.alarmInterval * 1000);
	watching.setRowClickHandler(rowClickSetting);

//	if(toolbar && toolbar.bar ){
//		toolbar.bar.style.cssText = toolbar.bar.style.cssText +  ";position:absolute";
//	}
}

//显示评价信息
function showAppraiseDetail(){
	if( appraiseObject )	{	
		appraiseObject.getAppraiseByAlarmId();
	}
}


function rowClickSetting(alarmId){
	watching.setButtonDisplay(alarmId);//设置功能按钮的可见性
	if( window.event ){
		//设置选中的背景颜色		watching.setRowBGColor(window.event.srcElement);
	}
	watching.setRowClickDefaultUI();	//设置默认的界面,各个div的可见性
	if($('divAlarmInfo')){
		$('divAlarmInfo').style.display ="";
	}
}

function DispatchWatching(){ 
	this.m_watchingData = null; //关注的信息列表	this.m_actvieRowId = null;
	
	this.watchItems = ["alarmId","alarmTime","eventState_code","eventSource","eventType",
					"eventLevel","eventState","eventType_code","eventSource_code","supCount",
					"longtitude","latitude","address","eventlevel_code","disposeperson"];
	
	this.appraisedCode = "004011";			//已评价编码	this.unhandleCode = '004012'; 			//未处理编码	this.handleCompleteCode = '004020'; 	//处理完毕编码
 	this.affairSRCTRIONECode = '002001';	//三台合一事件来源编码
	
	this.affairList = null;	
	this.affairClickHandlers = new Array();
		
	this.alarmInterval = 20;
	this.o_AlarmInterval = null;
	
	this.selectedBGColor = "#aac";
}

//设置具体事件选中后背景色
DispatchWatching.prototype.setRowBGColor = function(srcElement){
	var rowObject = null;
	if(srcElement.tagName.toUpperCase() == "TD" ){
		rowObject = srcElement.parentElement;
	}else if(srcElement.tagName.toUpperCase() == "TR" ){
		rowObject = srcElement;
	}
	if( rowObject ){ 	//设置当前选择的行
		if(watching.m_actvieRowId && $(watching.m_actvieRowId) ){ 
			$(watching.m_actvieRowId).style.backgroundColor = "";
		}
		watching.m_actvieRowId = rowObject.id;
		rowObject.style.backgroundColor = this.selectedBGColor;
	}
};
//设置刷新时间间隔 
DispatchWatching.prototype.setRefreshInterval = function(p_interval){
	if(msgListRefreshInterval){
		this.alarmInterval = p_interval;
	}else {
		this.alarmInterval = 30;
	}
};

//设置界面，显示状态DispatchWatching.prototype.btnClickUIsetting = function(){	
	event.srcElement.style.backgroundColor = '#99A';
	for( var i=0;i<btnIDList.length; i+=1 ){
		var divId = $(btnIDList[i]).associateDiv;
		var mapHolderId =  $(btnIDList[i]).mapDiv;
		if( $(btnIDList[i]) == event.srcElement ){
			if($(divId)){				
				$(divId).style.display = "";
			}
		}else{
			$(btnIDList[i]).style.backgroundColor = "transparent";
			if($(divId)){				
				$(divId).style.display = "none";
			}
		}
		if( mapHolderId && $(mapHolderId) ){
			$(mapHolderId).style.display = "none";
		}
	}
	if($('divAlarmInfo')){
		$('divAlarmInfo').style.display ="none";
	}
};

//设置按钮的可见性
//没有督办的不显示督办按钮
//没有评价的不显示评价信息按钮
DispatchWatching.prototype.setButtonDisplay = function(alarmId){
	var data = watching.m_watchingData['affairData'];
	for(var i=0;i< data.length;i+=1){
		if(data[i].alarmId == alarmId){  //设置督办按钮的可见性			var supcount = 0;
			if(! isNaN(parseInt( data[i].supCount ) )){
				 supcount = parseInt( data[i].supCount );
			}
			if(supcount >0 && $('btnMonitor')){ 	//督办按钮
				$('btnMonitor').style.display = "";
			}else{ 
				if($('btnMonitor')){
					$('btnMonitor').style.display = "none";
				}
			}
			if($('btnAppraise')){ 
				if( this.appraisedCode ==  data[i].eventState_code ){ 
					//查看评价按钮
					$('btnAppraise').style.display = "";
					appraiseObject = new AffairApparise(alarmId);
				}else{
					$('btnAppraise').style.display = "none";
				}
			}
			break;	
		}
	}
};

//设置各个按钮的默认状态DispatchWatching.prototype.setRowClickDefaultUI = function(){
	for( var i=0;i<btnIDList.length; i+=1 )	{
		if($(btnIDList[i])){
			$(btnIDList[i]).style.backgroundColor = "transparent";
			for( var i=0;i<btnIDList.length; i+=1 ){
				var divId = $(btnIDList[i]).associateDiv;
				var mapHolderId =  $(btnIDList[i]).mapDiv;
		
				$(btnIDList[i]).style.backgroundColor = "transparent";
				if( $(divId) ){				
					$(divId).style.display = "none";
				}
		
				if( mapHolderId && $(mapHolderId) ){
					$(mapHolderId).style.display = "none";
				}
			}
		}
	}
};

//读取关注的警情信息DispatchWatching.prototype.readNoticedAffair = function(){
	
	var url = 'dispatch.affairWatch.queryNoticedAffair.d';
	var params = "branchCode=" + deptcode ;
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
//				alert(xmlDoc.text)
				if(xmlDoc.text == "" && 'null' != xmlHttp.responseText)	{
//					alert("获取警情信息失败.");
					return ;
				}
				 
				theCaller.parseAffairDetail(xmlDoc);
				
				var listClassify = '';
				for(var i=0;i<radioList.length;i++){
					if($(radioList[i][0]).checked == true){
						listClassify = radioList[i][1];
					}else{
						listClassify = "";
					}
				}
				theCaller.showAffairDetail(listClassify);
				
				if( typeof alarmWatchMap == 'object'){
					alarmWatchMap.m_watchingList = theCaller.m_watchingData['affairData'];
					alarmWatchMap.clearAlarmPoints();
					alarmWatchMap.showAlarmPoint();
				}
			}
	});
};

//解析警情信息
//将信息保存到数据中DispatchWatching.prototype.parseAffairDetail = function(xmlDoc){ 
	this.m_watchingData = null;
	var results = xmlDoc.getElementsByTagName("row");
	if(0 < results.length){	
		this.m_watchingData = {};	
		this.m_watchingData['affairData'] = null;
		this.m_watchingData['affairData'] = new Array(); 
		//循环获取各信息项
		for (var i = 0; i < results.length; i+=1){
			var columnResults = results[i].getElementsByTagName("col");
			
			var dataItem = {};
			for(var j=0; j<columnResults.length; j+=1){
		 		dataItem[this.watchItems[j]] = columnResults[j].text;
			}
			this.m_watchingData['affairData'].push(dataItem);
		}
		this.classifyAffairs();	
	}else{
//		alert("aaaaaa:");
		$('spanAllAffair').innerHTML =  0;   	
		//处理完毕
		$('spanCompleteAffair').innerHTML = 0;     
	}
};


//根据事件状态进行事件分类DispatchWatching.prototype.classifyAffairs = function(){
	
//	this.appraisedCode = "004011";			//已评价编码
//	this.unhandleCode = '004012'; 			//未处理编码
//	this.handleCompleteCode = '004013'; 	//处理完毕编码
// 	this.affairSRCTRIONECode = '002001';	//三台合一事件来源编码
	this.m_watchingData['unhandleData'] = null;
	this.m_watchingData['completeData'] = null;
	this.m_watchingData['watchingData'] = null;
	this.m_watchingData['noCompleteData'] = null;
	this.m_watchingData['unhandleData'] = new Array();
	this.m_watchingData['completeData'] = new Array();
	this.m_watchingData['watchingData'] = new Array();
	this.m_watchingData['noCompleteData'] = new Array();
	for(var i=0; i<this.m_watchingData['affairData'].length; i+=1){
		var dataItem = this.m_watchingData['affairData'][i];
		
		if(dataItem.eventState_code  == this.handleCompleteCode || dataItem.eventState_code == this.appraisedCode ){	
			//处理完毕事件和已评价信息，不做任何处理			this.m_watchingData['completeData'].push(dataItem);
		}else{
			this.m_watchingData['noCompleteData'].push(dataItem);//需关注警情信息
			if(dataItem.eventState_code == this.unhandleCode ){
				//未处理事件
				this.m_watchingData['unhandleData'].push(dataItem);
			}else{
				//正在处理警情信息
				this.m_watchingData['watchingData'].push(dataItem);
			}
		}
	}
	this.updateSumAbstract();
};

//更新总计摘要
DispatchWatching.prototype.updateSumAbstract = function(){
	//需要关注的警情
	$('spanAllAffair').innerHTML =  (this.m_watchingData['affairData'].length-this.m_watchingData['completeData'].length);    
	//未处理	$('spanUnhandleAffair').innerHTML = this.m_watchingData['unhandleData'].length;     
	//正在处理
	$('spanProcessingAffair').innerHTML = this.m_watchingData['watchingData'].length;     
	//处理完毕
	$('spanCompleteAffair').innerHTML = this.m_watchingData['completeData'].length;     
};

//显示警情摘要信息
DispatchWatching.prototype.showAffairDetail = function(listClassify){
//	alert("listClassify1:" + listClassify);
	
	if( this.m_watchingData ){	//判断当前警情信息是否存在
		switch(listClassify){
			case 'unhandle':
				if( this.m_watchingData['unhandleData']){
			 		this.affairDataTable(this.m_watchingData['unhandleData']);
				}
			 	break;
			case 'watching':
				if( this.m_watchingData['watchingData']){
			 		this.affairDataTable(this.m_watchingData['watchingData']);
			 	}
			 	break;
			case 'complete':
				if( this.m_watchingData['completeData']){
					this.affairDataTable(this.m_watchingData['completeData']);
				}
				break;	
			default:
		 		if( this.m_watchingData['affairData'] ){
			 		this.affairDataTable(this.m_watchingData['noCompleteData']);
//			 		$('radioAllAffair').checked = true;
			 	}
		}
 	}else{
 		this.affairDataTable();
 	}
};

//未处理警情表格定义DispatchWatching.prototype.affairDataTable = function(affairData){			
	this.affairList = null;
	this.affairList = new HtmlTableDrawer('divUnhandleList');
	this.affairList.mouseouthandler = watching.mouseoutListHandler;
	this.affairList.mouseoverhandler = watching.mouseoverListHandler;
	this.affairList.setTableStyle(table2Style);
	this.affairList.setTdStyle(td2Style);
	this.affairList.setThStyle( td2Style + ";background-color:#9abcff;");
	this.affairList.setColumnType(["CHECKBOX","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT"]);
	this.affairList.setTableHead( ["选择","案件内容","报警时间","事件类型","事件来源","事件状态","接警员"] );  //"报警单号",
	this.affairList.setCellWidth(['3%','33%','6%','14%','14%','14%','14%']);
// 	this.affairList.createTableRowDIV();
	if(affairData!=null){
		for(var i=0;i<affairData.length;i+=1){ 
			this.addAffairRow(affairData[i]);
		}
	}
	this.affairList.submitTableHtml();
	this.affairList.setRowClickEvent(this.rowClickHandler , true);
	this.setAffairRowColor();	//设置报警行的颜色
	
};

DispatchWatching.prototype.mouseoutListHandler = function(){ 
	var rowObject = null;
	if(window.event.srcElement.tagName.toUpperCase() == "TD" ){ 
		rowObject = window.event.srcElement.parentElement;
	}else if(window.event.srcElement.tagName.toUpperCase() == "TR" ){ 
		rowObject = window.event.srcElement;
	}
	if( rowObject ){ 	//设置当前选择的行
		if(watching.m_actvieRowId != rowObject.id ){ 
			rowObject.style.backgroundColor = "";
		}
	}
};

DispatchWatching.prototype.mouseoverListHandler = function(){ 
	var rowObject = null;
	if(window.event.srcElement.tagName.toUpperCase() == "TD" ){
		rowObject = window.event.srcElement.parentElement;
	}else if(window.event.srcElement.tagName.toUpperCase() == "TR" ){ 
		rowObject = window.event.srcElement;
	}
	if( rowObject ){ 	//设置当前选择的行
		if(watching.m_actvieRowId != rowObject.id ){ 
			rowObject.style.backgroundColor = "silver";
		}
	}
};


//设置报警行的字体颜色
DispatchWatching.prototype.setAffairRowColor = function(){
	if(! this.affairList.m_HtmlRowList )	{
		return;
	}
	var tag=false;
	for(var j=0;j<this.affairList.m_HtmlRowList.length;j+=1){
		//循环设置每行的事件		var htmlRow = $(this.affairList.m_HtmlRowList[j]);
		if( htmlRow && htmlRow.info ){
			var supCount = this.getSupCountByAlarmId(htmlRow.info);
			var srcCode = this.getAffairSRCCodeByAlarmId(htmlRow.info);
			var cellFontColor = "black";
			if(srcCode == this.affairSRCTRIONECode ){	//三台合一来的信息，显示为蓝色
				cellFontColor = "blue";
			}
			if( 0 < supCount){ //含有督办信息的显示为红色（如果来自三台合一而且含有督办的则显示为督办的颜色）				cellFontColor = "red";
			}
			var stateCode=this.getAffairSTATECodeByAlarmId(htmlRow.info);//事件状态
			if(stateCode=="004001"||stateCode=="004012"){
//				alert("aaa")
				tag=true;
			}
		 
			for( var k=0; k<htmlRow.cells.length;k+=1){
				htmlRow.cells[k].style.color = cellFontColor;
			}
			
			if(htmlRow.id == this.m_actvieRowId ){ 	//设置选中行				var ckBox = htmlRow.cells[0].childNodes[0];
				if(ckBox.tagName && ckBox.tagName.toUpperCase() == "INPUT" && ckBox.type.toUpperCase() == "CHECKBOX" )
				{
					ckBox.checked = true;
				}
				htmlRow.style.backgroundColor = this.selectedBGColor;
			}
		}
	}
	if(tag){		
		document.getElementById("soundDiv").innerHTML='<embed src="http://'+path+'/CmdDispatch/dispatch/alarm.wav" id="" hidden=true autostart=true loop=2> ';
	}else{
		
		document.getElementById("soundDiv").innerHTML='';
	}
};

function playsound(){
	document.getElementById("soundDiv").innerHTML='<embed src="http://'+path+'/CmdDispatch/dispatch/alarm.wav" id="" hidden=true autostart=true loop=true> ';
}

//通过报警单号，从列表中获取 该事件的来源
DispatchWatching.prototype.getAffairSRCCodeByAlarmId = function(alarmId){
	var srcCode;
	if( alarmId ){
		var data = watching.m_watchingData['affairData'];
		for(var i=0;i< data.length;i+=1){
			if( data[i].alarmId == alarmId ){ 
				return data[i].eventSource_code;
			}
		}
	}
	return srcCode;
};


//通过报警单号，从列表中获取 该事件的状态
DispatchWatching.prototype.getAffairSTATECodeByAlarmId = function(alarmId){
	var srcCode;
	if( alarmId ){
		var data = watching.m_watchingData['affairData'];
		for(var i=0;i< data.length;i+=1){
			if( data[i].alarmId == alarmId ){ 
				return data[i].eventState_code;
			}
		}
	}
	return srcCode;
};


//通过报警单号，从列表中获取 该事件的督办信息记录数DispatchWatching.prototype.getSupCountByAlarmId = function(alarmId){
	var supcount;
	if( alarmId ){
		var data = watching.m_watchingData['affairData'];
		for(var i=0;i< data.length;i+=1){
			if( data[i].alarmId == alarmId ){ 
				if(! isNaN(parseInt( data[i].supCount ) )){
					 supcount = parseInt( data[i].supCount );
				}else{
					supcount = 0;
				}
				break;
			}
		}
	}
	return supcount;
};

//行点击事件处理DispatchWatching.prototype.rowClickHandler = function(alarmId){
	try{
		if( 'undefined'!= typeof watching &&  0<watching.affairClickHandlers.length)		{
			for(var i=0;i< watching.affairClickHandlers.length;i+=1)			{
				watching.affairClickHandlers[i](alarmId);
			}
		}
	}
	catch(e){//ignor error
	}
};


//设置行点击事件处理方法DispatchWatching.prototype.setRowClickHandler = function(handler){
	if( 'function' ==  typeof handler ){
		var isRepeat = false;		//判断重复添加事件处理
		for(var i=0;i< this.affairClickHandlers.length;i+=1){
			if(this.affairClickHandlers[i] == handler ){
				isRepeat = true;
				break;
			}
		}
		if(!isRepeat){
			this.affairClickHandlers.push(handler);
		}
	}
};

//添加未处理警情行信息
DispatchWatching.prototype.insertNewAlarmRow = function(dataItem){
	if(this.m_watchingData['affairData'] && dataItem){
		this.m_watchingData['affairData'].push( dataItem );
		
		this.classifyAffairs();		//重新分类警情信息
		this.showAffairDetail();	//显示列表
	}
};

//添加未处理警情行信息
DispatchWatching.prototype.addAffairRow = function(data){
	var rowdata = new Array();
	rowdata.push("");	
	rowdata.push(data['address']);
	rowdata.push(data['alarmTime']);
	rowdata.push(data['eventType']);	
	rowdata.push(data['eventSource']);
	rowdata.push(data['eventState']);	
	rowdata.push(data['disposeperson']);
	return	this.affairList.addTableRow(rowdata,data['alarmId']);		//添加表格行数据};

//合并警情信息
DispatchWatching.prototype.mergeUnhandleAffairs = function(){
	var selectedAffairs = this.getUnhandleAffairs();	//获取选择的警情信息标识
	if("" == selectedAffairs || selectedAffairs.split(",").length < 2){
		alert("请至少选择两条警情信息，再选择合单操作!");
		return;
	}
	window.showModalDialog("../affairProcess/mergeAffairs.jsp?AFFAIRIDS=" + selectedAffairs,'','dialogWidth:540px;dialogHeight:260px' );
};


//获取被选中的警情//返回警情单编号，各个警情单编号以逗号隔开。//如果没选择任何警情单则返回空字符串
DispatchWatching.prototype.getUnhandleAffairs = function(){
	var affairs = "";
	if( this.affairList && this.affairList.m_HtmlRowList){
		for(var i=0; i<this.affairList.m_HtmlRowList.length; i+=1) {
			var htmlRow = $(this.affairList.m_HtmlRowList[i]);	//获取数据行
			if( htmlRow){
				var htmlCkBox = htmlRow.cells[0].childNodes[0];
				if( htmlCkBox.checked ){	//判断选中状态，得到警情单编号					if("" == affairs ){
						affairs += htmlRow.info;
					}else{
						affairs += "," + htmlRow.info;
					}
				}
			}
		}
	}
	return affairs;
};

//警情处理按钮事件处理
DispatchWatching.prototype.AffairProcess = function(){
};

//表格样式
var table2Style = 'background:#ffffff;border-top: 1 solid #000000;\
					border-right: 0 solid #000000;border-bottom: 0 solid #000000;\
									border-left: 1 solid #000000;border-collapse:collapse;\
									font-size:11px;align: center;text-align: center;width:100%;cursor:hand';
var td2Style = 'border-top: 0 solid #000000;border-right: 1 solid #000000;\
									border-bottom: 1 solid #000000;border-left: 0 solid #000000;\
									line-height: 16px;color: #000000;border-collapse : separate;\
									align: center;empty-cells:show;text-align: left;';	

