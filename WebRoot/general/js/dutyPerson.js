/**
 * 项目名称：EHL_ATMSIC_GD<br>
 * 文件路径：general/js<br>
 * 文件名称：dutyPerson.js<br>
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
//	jQuery("#endDate").val(new Date().format("yyyy-MM-dd"));
	loadGrid();
	
	jQuery("#enableAutoSearch").bind("change",function(){
		autoSearch();
	});
}
);

/**
 * 
	* 方法名称：autoSearch<br>
	* 方法描述： 启用自动搜索 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-28 下午2:57:19  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-28 下午2:57:19   <br>
	* 修改备注：    <br>
 */
function autoSearch(){
	var isAutoSearch = jQuery("#enableAutoSearch").attr("checked")=="checked"?true:false;
	if(isAutoSearch){
		jQuery("#searchDivId").css("display","none");
		jQuery("#startDate").bind('propertychange',function(){
			reloadGrid();
		});
		jQuery("#sSearch").bind("propertychange",function(){
			reloadGrid();
		});
		jQuery("#deptType_select").bind("change",function(){
			reloadGrid();
		});
	}else{
		jQuery("#searchDivId").css("display","inline");
		jQuery("#startDate").unbind('propertychange');
		jQuery("#sSearch").unbind('propertychange');
		jQuery("#deptType_select").unbind('change');
	}
}

/**
 * 
	* 方法名称：reloadGrid<br>
	* 方法描述： 重新装载表格 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2011-12-28 下午2:57:25  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2011-12-28 下午2:57:25   <br>
	* 修改备注：    <br>
 */
function reloadGrid(){
	var startDate = jQuery("#startDate").val();
	var endDate = jQuery("#startDate").val();
	var sSearch = jQuery("#sSearch").val();
	var showLevel = jQuery("#deptType_select").val();
	var urlParent = "general.dutyPersonInfoAction.getDutyPersonInfo.d?showLevel="+showLevel+"&startDate="+startDate+"&endDate="+endDate+"&sSearch="+sSearch;
	
	//encode for chinense
	urlParent = encodeURI(urlParent);
	jQuery("#tb_tableId").jqGrid('setGridParam',{url:urlParent}).trigger("reloadGrid");
}

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
	var endDate = jQuery("#startDate").val();
	var showLevel = jQuery("#deptType_select").val();
	var urlParent = "general.dutyPersonInfoAction.getDutyPersonInfo.d?showLevel="+showLevel+"&startDate="+startDate+"&endDate="+endDate;
	
	var colNames = ['序号','值班时间',"部门编号","部门名称", '值班领导', '值班员','值班电话'];
	var colModel = [
	    	   		{name:'rn',index:'rn', width:30, align:"center"},
	    	   		{name:'duty_time',index:'duty_time', width:70, align:"center"},
	    	   		{name:'jgid',index:'jgid', width:30, align:"center",hidden:true},
	    	   		{name:'jgmc',index:'jgmc', width:150, align:"center"},
	    	   		{name:'duty_leader',index:'duty_leader', width:50, align:"center"},
	    	   		{name:'duty_person',index:'duty_person', width:100, align:"center"},
	    	   		{name:'duty_phone',index:'duty_phone', width:100, align:"center"}	
	    	   	];
	//encode for chinense
	urlSubGrid = encodeURI(urlParent);
	
	var title = "";
	
	jQuery("#tb_tableId").jqGrid({
	   	url:urlParent,
		datatype: "json",
	   	colNames:colNames,
	   	colModel:colModel,
	   	rowNum:20,
	   	rowList:[20,40,80],
	   	width:820,
	   	height:350,
	   	pager: '#div_pageId',
	   	sortname: 'jgid',
	    viewrecords: true,
//	    sortorder: "asc",
		multiselect: false,
		subGrid: false,
		caption: title,
		subGridRowExpanded: secondLevelGrid,
		subGridRowColapsed: function(subgrid_id, row_id) {
			// this function is called before removing the data
//			var subgrid_table_id;
//			subgrid_table_id = subgrid_id+"_t";
//			jQuery("#"+subgrid_table_id).remove();
		},
		gridComplete: changeRowStyle,
        loadComplete: noRecordsTips,
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
	var endDate = jQuery("#startDate").val();
	var showLevel = "2";
	cellValue = jQuery('#tb_tableId').jqGrid('getCell',row_id,'jgid');
	var urlSubGrid;
	urlSubGrid = "general.dutyPersonInfoAction.getDutyPersonInfo.d?showLevel="+showLevel+"&jgid="+cellValue+"&startDate="+startDate+"&endDate="+endDate+"&id="+row_id;
	//encode for chinense
	urlSubGrid = encodeURI(urlSubGrid);
	
	//subgrid parameters
	var colNM = ['序号','值班时间',"部门编号","部门名称", '值班领导', '值班员','值班电话'];
	var colMD = [
	    	   		{name:'rn',index:'rn', width:55, align:"center"},
	    	   		{name:'duty_time',index:'duty_time', width:70, align:"center"},
	    	   		{name:'jgid',index:'jgid', width:50, align:"center",hidden:true},
	    	   		{name:'jgmc',index:'jgmc', width:150, align:"center"},
	    	   		{name:'duty_leader',index:'duty_leader', width:70, align:"center"},
	    	   		{name:'duty_person',index:'duty_person', width:100, align:"center"},
	    	   		{name:'duty_phone',index:'duty_phone', width:100, align:"center"}	
	    	   	];
	
	//sub grid
	var subgrid_table_id, pager_id;
	subgrid_table_id = subgrid_id+"_t";
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
	    sortorder: "asc",
	    height: '100%',
//		onCellSelect:onCellSelect,
		gridComplete: function(){
        },
        loadComplete: noRecordsTips,
		loadError: function(xhr,status,error){alert('初始化表格失败！');}
	});
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
	* 方法名称：changeRowStyle<br>
	* 方法描述： 更改特定行的样式 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-1-8 下午10:22:58  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-1-8 下午10:22:58   <br>
	* 修改备注：    <br>
 */
function changeRowStyle() {
	var grid = jQuery("#tb_tableId");
	var ids = grid.getDataIDs();
	var cellValue = null;
	for ( var i = 0; i < ids.length; i++) {
		cellValue = jQuery("#tb_tableId").jqGrid('getCell', ids[i], 'jgid');
		var showLevel = jQuery("#deptType_select").val();
		if (cellValue != null && cellValue.substr(4, 2) == "00" && showLevel == "") {
			jQuery("#tb_tableId").jqGrid('setRowData', ids[i], false, 'ui-jqgrid-specrows');
		}
	}
}