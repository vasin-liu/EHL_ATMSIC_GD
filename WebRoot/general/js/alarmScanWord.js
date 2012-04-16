/**
 * 项目名称：EHL_ATMSIC_GD<br>
 * 文件路径：general/js<br>
 * 文件名称：alarmScanWord.js<br>
 * 文件编号： <br>
 * 文件描述： <br>
 * 
 * 版本信息： Ver 1.1<br>
 * 创建日期：2011-12-14 <br>
 * 公司名称： 北京易华录信息技术股份有限公司 2011 Copyright(C) 版权所有 <br>
 * *************************************************<br>
 * 创建人：Vasin <br>
 * 创建日期：2011-12-14 下午4:28:43 <br>
 * ************ 修改历史 *************<br>
 * 修改人：Vasin <br>
 * 修改时间：2011-12-14 下午4:28:43 <br>
 * 修改备注： <br>
 */
jQuery(document).ready(function() {
	jQuery("#startDate").val(new Date().format("yyyy-MM-dd"));
	jQuery("#endDate").val(new Date().format("yyyy-MM-dd"));
	loadGrid();
	jQuery("#startDate").bind("propertychange",function(){
		checkDate(this);
	});
	jQuery("#endDate").bind("propertychange",function(){
		checkDate(this);
	});
	jQuery("#generalInfoType").change(function(){
		loadGrid();
	});
}
);

/**
 * 
	* 方法名称：loadGrid<br>
	* 方法描述： 装载表格 <br>
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-19 下午5:45:01  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-19 下午5:45:01   <br>
	* 修改备注：    <br>
 */
function loadGrid(){
	var startDate = jQuery("#startDate").val();
	var endDate = jQuery("#endDate").val();
	var countType_value = $("#generalInfoType").find("option:selected").val();
	var urlParent;
	if(countType_value == "roadid"){
		jQuery("#tb_tableId").jqGrid("setLabel","COUNT_TYPE","道路等级",{"text-align":"center"});
		urlParent = "general.generalInfoAction.getGeneralInfoCount.d?countBy=ROADLEVEL&startDate="+startDate+"&endDate="+endDate;
	}else{
		jQuery("#tb_tableId").jqGrid("setLabel","COUNT_TYPE","地区名称",{"text-align":"center"});
		urlParent = "general.generalInfoAction.getGeneralInfoCount.d?countBy=ALARMAREA&startDate="+startDate+"&endDate="+endDate;
	}
	
	//encode for chinense
	urlSubGrid = encodeURI(urlParent);
	jQuery(".norecords").hide();
	jQuery("#tb_tableId").jqGrid('setGridParam',{url:urlParent}).trigger("reloadGrid");
//	var countType_Value = $("#generalInfoType").val();
	jQuery("#tb_tableId").jqGrid({
	   	url:urlParent,
		datatype: "json",
	   	colNames:['序号',"地区名称", '重大事故', '交通拥堵','施工占道'],
	   	colModel:[
	   		{name:'rn',index:'rn', width:55, align:"center"},
	   		{name:'COUNT_TYPE',index:'COUNT_TYPE', width:100, align:"center"},
	   		{name:'ACCIDENT',index:'ACCIDENT', width:100, align:"center"},
	   		{name:'TRAFFIC_CROW',index:'TRAFFIC_CROW', width:100, align:"center"},
	   		{name:'ROAD_BUILD',index:'ROAD_BUILD', width:100, align:"center"}	
	   	],
	   	rowNum:20,
	   	rowList:[20,40,60],
	   	width:820,
	   	height:350,
	   	pager: '#div_pageId',
	   	sortname: '1',
	    viewrecords: true,
//	    sortorder: "asc",
		multiselect: false,
		subGrid: true,
//		caption: "<b>搜  索: </b><input id='sSearch' type=text />",
		subGridRowExpanded: secondLevelGrid,
		subGridRowColapsed: function(subgrid_id, row_id) {
			// this function is called before removing the data
//			var subgrid_table_id;
//			subgrid_table_id = subgrid_id+"_t";
//			jQuery("#"+subgrid_table_id).remove();
		},
//		on cell selected event
//		onCellSelect:onCellSelect,
		loadComplete: noRecordsTips,
		gridComplete: function(){
        },
		loadError: function(xhr,status,error){alert('初始化表格失败！');}
	});
	//can not edit
	jQuery("#tb_tableId").jqGrid('navGrid','#div_pageId',{add:false,edit:false,del:false,search:false},{},{},{},{overlay:0});
}

/**
 * 
	* 方法名称：secondLevelGrid<br>
	* 方法描述： 第二级表格 <br>
	* @param subgrid_id
	* @param row_id<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-20 上午11:45:20  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-20 上午11:45:20   <br>
	* 修改备注：    <br>
 */
var g_second_subgrid_id;
var g_second_row_id;
function secondLevelGrid(subgrid_id, row_id) {
	// we pass two parameters
	// subgrid_id is a id of the div tag created whitin a table data
	// the id of this elemenet is a combination of the "sg_" + id of the row
	// the row_id is the id of the row
	// If we wan to pass additinal parameters to the url we can use
	// a method getRowData(row_id) - which returns associative array in type name-value
	// here we can easy construct the flowing
	//get cell's value of current selected
	var startDate = jQuery("#startDate").val();
	var endDate = jQuery("#endDate").val();
	cellValue = jQuery('#tb_tableId').jqGrid('getCell',row_id,'COUNT_TYPE');
	//get text of selected
//	countType_Text = jQuery("#generalInfoType").find("option:selected").text();
	//is show sub grid
	var isShowSG = false;
	var countType_value = jQuery("#generalInfoType").find("option:selected").val();
	if(countType_value == "roadid"){
		isShowSG = true;
	}
	var road_level = 1;
	if(cellValue == "高速"){
		road_level = 1;
	}else if(cellValue == "国道"){
		road_level = 2;
	}else if(cellValue == "省道"){
		road_level = 3;
	}else if(cellValue == "其他"){
		road_level = 4;
	}
	var urlSubGrid;
	var _column_name_;
	if(countType_value == "roadid"){
		_column_name_ = "道路名称";
		urlSubGrid = "general.generalInfoAction.getGeneralInfoCount.d?countBy=ROADID&startDate="+startDate+"&endDate="+endDate+"&id="+row_id + "&ROADLEVEL=" + road_level;
	}else{
		_column_name_ = "管辖机构";
		urlSubGrid = "general.generalInfoAction.getGeneralInfoCount.d?countBy=ALARMREGIONID&startDate="+startDate+"&endDate="+endDate+"&id="+row_id + "&ALARMREGIONID="+cellValue;
	}
	//encode for chinense
	urlSubGrid = encodeURI(urlSubGrid);
	
	//subgrid parameters
	var colMD;
	var colNM = ['序号',_column_name_, '重大事故', '交通拥堵','施工占道'];
	if(!isShowSG){
		colMD =  [
					{name:'rn',index:'rn', width:50, align:"center"},
					{name:'COUNT_TYPE',index:'COUNT_TYPE', width:90, align:"center"},
					{name:'ACCIDENT',index:'ACCIDENT', width:90, align:"center",formatter:myformatter},
					{name:'TRAFFIC_CROW',index:'TRAFFIC_CROW', width:90, align:"center",formatter:myformatter},
					{name:'ROAD_BUILD',index:'ROAD_BUILD', width:90, align:"center",formatter:myformatter}
				];
	}else{
		colMD =  [
					{name:'rn',index:'rn', width:50, align:"center"},
					{name:'COUNT_TYPE',index:'COUNT_TYPE', width:90, align:"center"},
					{name:'ACCIDENT',index:'ACCIDENT', width:90, align:"center"},
					{name:'TRAFFIC_CROW',index:'TRAFFIC_CROW', width:90, align:"center"},
					{name:'ROAD_BUILD',index:'ROAD_BUILD', width:90, align:"center"}
				];
	}
	
	//sub grid
//	g_second_subgrid_id = subgrid_id;
//	g_second_row_id = row_id;
	var subgrid_table_id, pager_id;
	subgrid_table_id = subgrid_id+"_t";
	g_second_subgrid_id = subgrid_table_id;
	pager_id = "p_"+subgrid_table_id;
	jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
	jQuery("#"+subgrid_table_id).jqGrid({
		url:urlSubGrid,
		datatype: "json",
		colNames:colNM,
		colModel:colMD,
	   	rowNum:10,
	   	pager: pager_id,
	   	width:760,
	   	sortname: '1',
	    sortorder: "desc",
	    height: '100%',
	    //=================third level grid---start ========================
	    subGrid: isShowSG,
        subGridRowExpanded: thirdLevelGrid,
		subGridRowColapsed: function(subgrid_id, row_id) {
			// this function is called before removing the data
//			var subgrid_table_id;
//			subgrid_table_id = subgrid_id+"_t";
//			jQuery("#"+subgrid_table_id).remove();
		},
//		onCellSelect:onCellSelect,
		gridComplete: function(){
        },
		loadError: function(xhr,status,error){alert('初始化表格失败！');}
	    //===============third level grid---end================
	});
	//can not edit
	jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false});
}

/**
 * 
	* 方法名称：thirdLevelGrid<br>
	* 方法描述： 第三级表格 <br>
	* @param subgrid_id
	* @param row_id<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-20 上午11:45:58  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-20 上午11:45:58   <br>
	* 修改备注：    <br>
 */
function thirdLevelGrid(subgrid_id, row_id) { //third level grid
	g_second_row_id = row_id;
	//get cell's value of current selected
//	cellValue = jQuery('#' + subgrid_id).jqGrid('getCell',row_id,'COUNT_TYPE');
	var startDate = jQuery("#startDate").val();
	var endDate = jQuery("#endDate").val();
	cellValue = jQuery(this).jqGrid('getCell',row_id,'COUNT_TYPE');
//	alert("subgrid_id:"+subgrid_id);
//	alert("cellValue:"+cellValue);
	//get text of selected
//	countType_Text = jQuery("#generalInfoType").find("option:selected").text();
	countType_value = jQuery("#generalInfoType").find("option:selected").val();
	var _column_name_ = "管辖机构";
	if(countType_value == "roadid"){
		urlSubGrid = "general.generalInfoAction.getGeneralInfoCount.d?countBy=ROADREGION&startDate="+startDate+"&endDate="+endDate+"&id="+row_id + "&ROADID=" + cellValue;
	}else{
		urlSubGrid = "general.generalInfoAction.getGeneralInfoCount.d?countBy=ROADREGION&startDate="+startDate+"&endDate="+endDate+"&id="+row_id + "&ROADID=" + cellValue;
	}
	//encode for chinense
	urlSubGrid = encodeURI(urlSubGrid);
	
	//subgrid parameters
	var colNM = ['序号',_column_name_, '重大事故', '交通拥堵','施工占道'];
	var colMD = [
	 				{name:'rn',index:'rn', width:50, align:"center"},
	 				{name:'COUNT_TYPE',index:'COUNT_TYPE', width:100, align:"center"},
	 				{name:'ACCIDENT',index:'ACCIDENT', width:100, align:"center",formatter:myformatter},
	 				{name:'TRAFFIC_CROW',index:'TRAFFIC_CROW', width:100, align:"center",formatter:myformatter},
	 				{name:'ROAD_BUILD',index:'ROAD_BUILD', width:100, align:"center",formatter:myformatter}
				];
	
    var subgrid_table_id, pager_id;
    subgrid_table_id = subgrid_id+"_tl";
//    alert(subgrid_table_id +" --- Third level");
    pager_id = "tl_"+subgrid_table_id;
    $("#"+subgrid_id).append("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>"); 
    jQuery("#"+subgrid_table_id).jqGrid({ 
        url: urlSubGrid,//Level 3 grid
        datatype: "json",
        width:700,
        height:"auto",
        multiselect: false,
        colNames:colNM,
		colModel: colMD,
		rowNum:10,
		pager: pager_id,
        sortname: '1',
        sortorder: "asc", 
        pager: pager_id,
        gridComplete: function(){
        },
        loadError: function(xhr,status,error){alert('初始化表格失败！');}
    });
    //change the header label
    jQuery("#" + subgrid_table_id).jqGrid("setLabel","COUNT_TYPE","管辖机构",{"text-align":"center"});
    //can not edit
    jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false});
}

/**
 * 
	* 方法名称：noRecordsTips<br>
	* 方法描述： 无记录时的提示信息 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-28 下午5:36:37  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-28 下午5:36:37   <br>
	* 修改备注：    <br>
 */
function noRecordsTips( data ) {
	if(data != null && data.records != null && data.records <= 0){
		if (jQuery(".norecords").html() == null) {
			jQuery(this).parent().append(
					"<div class=\"norecords\">没有符合的数据！</div>");
		}
		jQuery(".norecords").show();
	}else{
		jQuery(".norecords").hide();
	}
}

/**
 * 
	* 方法名称：myformatter<br>
	* 方法描述： 格式化单元格 <br>
	* @param cellvalue - is the value to be formatted
	* @param options - is an object containing the following element ; options : { rowId: rid, colModel: cm} where rowId - is the id of the row colModel is the object of the properties for this column getted from colModel array of jqGrid
	* @param rowObject - is a row data represented in the format determined from datatype option. If we have datatype: xml/xmlstring - the rowObject is xml node,provided according to the rules from xmlReader If we have datatype: json/jsonstring - the rowObject is array, provided according to the rules from jsonReader
	* @returns string - new_formated_cellvalue <br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-20 上午11:47:16  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-20 上午11:47:16   <br>
	* 修改备注：    <br>
 */
function myformatter ( cellvalue, options, rowObject ){
	// format the cellvalue to new format
	var new_formated_cellvalue;
	if(cellvalue != null && parseInt(cellvalue) > 0){
		new_formated_cellvalue = "<a style='text-decoration:underline' href='#' onclick='showDetail(\""+rowObject[1]+"\",this);'>"+cellvalue + "</a>";
	}else{
		new_formated_cellvalue = cellvalue;
	}
	return new_formated_cellvalue;
}

/**
 * 
	* 方法名称：onCellSelect<br>
	* 方法描述： 获取选择的单元格 <br>
	* @param id
	* @param cellidx
	* @param cellvalue<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-20 下午3:56:24  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-20 下午3:56:24   <br>
	* 修改备注：    <br>
 */
//function onCellSelect(id,cellidx,cellvalue){
//	var cm = jQuery("#tb_tableId").jqGrid("getGridParam", "colModel");
//	var colName = cm[cellidx];
//}

/**
 * 
	* 方法名称：showDetail<br>
	* 方法描述： 显示详细信息主方法 <br>
	* @param _count_name_
	* @param obj<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-22 下午3:44:12  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-22 下午3:44:12   <br>
	* 修改备注：    <br>
 */
function showDetail( _count_name_, obj ){
	var parentCellVaule = jQuery("#"+g_second_subgrid_id).jqGrid('getCell',g_second_row_id,'COUNT_TYPE');
	
	var index = obj.parentNode.cellIndex;
	var eventType = "";
	var type_name = "";
	if(index == 2){
		eventType = "001024";
		type_name = "重特大事故";
	}else if(index == 3){
		eventType = "001002";
		type_name = "交通拥堵";
	}else if(index == 4){
		eventType = "001023";
		type_name = "施工占道";
	}
	
	var title = "<span><big><b>"+_count_name_+"</b> 辖区 <b>"+type_name+"</b> 简略信息</big></span>";
	
	var content = "<table border='0' cellpadding='0' cellspacing='0' class='titleTopBack' style='width:800px,height:12px'>" +
				"<TBODY><tr style='width:100%,height:100%'>" +
				"<td><table id='generalInfo_showDetail_table'></table></td>" +
				"</tr>" +
				"<tr style='width:100%,height:100%'>" +
				"<td><div id='generalInfo_showDetail_div'></div></td>" +
				"</tr>" +
				"</TBODY></table>";
	
	 $.blockUI({ 
         theme:true, 
         title:"<table border='0' cellpadding='0' cellspacing='0'><tr><td align='left' width='690px'>"+title+"</td><td align='right' width='10px'><img src='../image/button/close.png' alt='关闭' style='cursor:hand' onClick='jQuery.unblockUI();' /></td></tr></table>", 
         message:content,
         applyPlatformOpacityRules:false,
         overlayCSS:{ 
             backgroundColor: '#00f', 
             opacity:0.6 
         },
         themedCSS: { 
             top:  ($(window).height() - 750) /2 + 'px', 
             left: ($(window).width() - 750) /2 + 'px', 
             width: '750px'
         }
     }); 
	 showDetailGrid(_count_name_,eventType,parentCellVaule);
}

/**
 * 
	* 方法名称：showDetailGrid<br>
	* 方法描述： 显示详细信息表格 <br>
	* @param _count_name_
	* @param eventType
	* @param parentCellVaule<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-22 下午3:43:52  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-22 下午3:43:52   <br>
	* 修改备注：    <br>
 */
var g_event_type;
function showDetailGrid(_count_name_,eventType,parentCellVaule){
	var startDate = jQuery("#startDate").val();
	var endDate = jQuery("#endDate").val();
	var urlParent,colNames,colModel;
	var countType_value = $("#generalInfoType").find("option:selected").val();
	if(parentCellVaule && countType_value == 'roadid'){
		urlParent = "general.generalInfoAction.getGeneralInfoCount.d?countBy=showDetail&startDate="+startDate+"&endDate="+endDate+"&detailType="+eventType+"&detailID="+_count_name_+"&detailParentID="+parentCellVaule;
	}else{
		urlParent = "general.generalInfoAction.getGeneralInfoCount.d?countBy=showDetail&startDate="+startDate+"&endDate="+endDate+"&detailType="+eventType+"&detailID="+_count_name_;
	}
	
	g_event_type = eventType;
	
	if(eventType == "001002"){//交通拥堵
		colNames = ['序号',"拥堵时间",'拥堵描述','拥堵原因','拥堵状态','操作'];
		colModel = [
			   		{name:'rn',index:'rn', width:30, align:"center"},
			   		{name:'DTIME',index:'DTIME', width:70, align:"center"},
			   		{name:'DESCRIPTION',index:'DESCRIPTION', width:200, align:"center"},
			   		{name:'CAUSATION',index:'CAUSATION', width:70, align:"center"},
			   		{name:'STATUS',index:'STATUS', width:50, align:"center",formatter:statusFormatter},
			   		{name:'ALARMID',index:'ALARMID', width:30, align:"center",formatter:detailFormatter}
			   	];
	}else if(eventType == "001023"){//施工占道
		colNames = ['序号',"开始时间",'结束时间','占道描述','占用车道数','施工状态','操作'];
		colModel = [
			   		{name:'rn',index:'rn', width:50, align:"center"},
			   		{name:'DTIME',index:'DTIME', width:70, align:"center"},
			   		{name:'ETIME',index:'ETIME', width:70, align:"center"},
			   		{name:'DESCRIPTION',index:'DESCRIPTION', width:190, align:"center"},
			   		{name:'ROLANENUM',index:'ROLANENUM', width:70, align:"center"},
			   		{name:'STATUS',index:'STATUS', width:50, align:"center",formatter:statusFormatter},
			   		{name:'ALARMID',index:'ALARMID', width:30, align:"center",formatter:detailFormatter}
			   	];
	}else{//重大事故
		colNames = ['序号',"事故时间", '事故地址', '事故原因','操作'];
		colModel = [
			   		{name:'rn',index:'rn', width:35, align:"center"},
			   		{name:'DTIME',index:'DTIME', width:100, align:"center"},
			   		{name:'ALARMADDRESS',index:'ALARMADDRESS', width:200, align:"center"},
			   		{name:'REASON',index:'REASON', width:70, align:"center"},
			   		{name:'ALARMID',index:'ALARMID', width:30, align:"center",formatter:detailFormatter}
			   	];
	}
	
	//encode for chinense
	urlSubGrid = encodeURI(urlParent);
	
//	jQuery("#generalInfo_showDetail_table").jqGrid('setGridParam',{url:urlParent}).trigger("reloadGrid");
//	var countType_Value = $("#generalInfoType").val();
	jQuery("#generalInfo_showDetail_table").jqGrid({
	   	url:urlSubGrid,
		datatype: "json",
	   	colNames:colNames,
	   	colModel:colModel,
	   	rowNum:30,
	   	rowList:[30,50,100],
	   	width:700,
	   	height:290,
	   	pager: '#generalInfo_showDetail_div',
	   	sortname: '1',
	    viewrecords: true,
	    sortorder: "asc",
		multiselect: false,
//		caption: "<b>搜  索: </b><input id='sSearch' type=text />",
//		on cell selected event
//		onCellSelect:onCellSelect,
		gridComplete: function(){
        },
		loadError: function(xhr,status,error){alert('初始化表格失败！');}
	});
	//can not edit
	jQuery("#generalInfo_showDetail_table").jqGrid('navGrid','#generalInfo_showDetail_div',{add:false,edit:false,del:false,search:false});
}

/**
 * 
	* 方法名称：statusFormatter<br>
	* 方法描述： 格式化单元格 <br>
	* @param cellvalue - is the value to be formatted
	* @param options - is an object containing the following element ; options : { rowId: rid, colModel: cm} where rowId - is the id of the row colModel is the object of the properties for this column getted from colModel array of jqGrid
	* @param rowObject - is a row data represented in the format determined from datatype option. If we have datatype: xml/xmlstring - the rowObject is xml node,provided according to the rules from xmlReader If we have datatype: json/jsonstring - the rowObject is array, provided according to the rules from jsonReader
	* @returns string - new_formated_cellvalue <br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-20 上午11:47:16  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-20 上午11:47:16   <br>
	* 修改备注：    <br>
 */
function statusFormatter( cellvalue, options, rowObject ){
	// format the cellvalue to new format
	var new_formated_cellvalue;
	if(cellvalue != null && cellvalue != ""){
		if(cellvalue == '570001'){//拥堵中
			new_formated_cellvalue = "<span style='color:red;font-weight:bold'>拥堵中</span>";
		}else if(cellvalue == '570002'){//拥堵结束
			new_formated_cellvalue = "<span style='color:green;font-weight:bold'>拥堵结束</span>";
		}else if(cellvalue == '570005'){//占道中
			new_formated_cellvalue = "<span style='color:red;font-weight:bold'>占道中</span>";
		}else if(cellvalue == '570006'){//占道结束
			new_formated_cellvalue = "<span style='color:green;font-weight:bold'>占道结束</span>";
		}else{
			new_formated_cellvalue = cellvalue;
		}
	}else{
		new_formated_cellvalue = cellvalue;
	}
	return new_formated_cellvalue;
}

/**
 * 
	* 方法名称：detailFormatter<br>
	* 方法描述： 格式化单元格 <br>
	* @param cellvalue - is the value to be formatted
	* @param options - is an object containing the following element ; options : { rowId: rid, colModel: cm} where rowId - is the id of the row colModel is the object of the properties for this column getted from colModel array of jqGrid
	* @param rowObject - is a row data represented in the format determined from datatype option. If we have datatype: xml/xmlstring - the rowObject is xml node,provided according to the rules from xmlReader If we have datatype: json/jsonstring - the rowObject is array, provided according to the rules from jsonReader
	* @returns string - new_formated_cellvalue <br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-20 上午11:47:16  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-20 上午11:47:16   <br>
	* 修改备注：    <br>
 */
function detailFormatter ( cellvalue, options, rowObject ){
	// format the cellvalue to new format
	var new_formated_cellvalue;
	var info_status;
	if(g_event_type == '001002'){//拥堵
		info_status = rowObject[4];
	}else if(g_event_type == '001023'){//施工
		info_status = rowObject[5];
	}else{//事故
		info_status = "";
	}
	if(cellvalue != null && cellvalue != ""){
		new_formated_cellvalue = "<img src='../image/button/btn_detail.png' alt='显示详细信息' style='cursor:hand' onClick='showMoreDetail(\""+cellvalue+"\",\""+info_status+"\",this);' />";
	}else{
		new_formated_cellvalue = cellvalue;
	}
	return new_formated_cellvalue;
}

function showMoreDetail(alarmId,info_status){
	if(g_event_type == '001002'){//拥堵
		window.showModalDialog("../../dispatch/ehl/cpoliceedit/TrafficCrowdInfoAdd.jsp?tmp=" + Math.random()
						+ "&insrtOrUpdt=2&alarmId='" + alarmId + "'"
						+ "&trfficeCrowState=" + info_status, "", "dialogWidth:900px;dialogHeight:600px");
	}else if(g_event_type == '001023'){//施工
		window.showModalDialog("../../dispatch/ehl/cpoliceedit/RoadBuildAdd.jsp?tmp=" + Math.random()
				+ "&insrtOrUpdt=2&alarmId=" + alarmId + "&buildState="
				+ info_status, "", "dialogWidth:800px;dialogHeight:600px");
	}else{//事故
		window.showModalDialog("../../dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()+
				"&insrtOrUpdt=2&hideBtn=false&readContol=99&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	}
}

/**
 * 
	* 方法名称：checkDate<br>
	* 方法描述： 时间检查 <br>
	* @param obj<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-1-4 下午2:42:07  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-1-4 下午2:42:07   <br>
	* 修改备注：    <br>
 */
function checkDate( obj ){
	var startDate = jQuery("#startDate").val();
	var endDate = jQuery("#endDate").val();
	if(startDate > endDate){
		if(obj.id == "startDate"){
			alert("开始日期不能大于结束日期！");
		}
		if(obj.id == "endDate"){
			alert("结束日期不能小于开始日期！");
		}
		jQuery("#"+obj.id).val(new Date().format("yyyy-MM-dd"));
	}
}