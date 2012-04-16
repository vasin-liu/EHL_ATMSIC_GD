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
	loadGrid();
	jQuery("input[name='filtingType']").bind("change",function(){
		reloadGrid();
	});
});

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
	var showAll = jQuery("input[name='filtingType']:checked").val(); 
	var sSearch = jQuery("#sSearch").val();
	var urlParent = "general.onlinePersonInfoAction.getOnlinePersonInfo.d?showAll="+showAll+"&sSearch="+sSearch;
	
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
	var showAll = jQuery("input[name='filtingType']:checked").val(); 
	var sSearch = jQuery("#sSearch").val();
	var urlParent = "general.onlinePersonInfoAction.getOnlinePersonInfo.d?showAll="+showAll+"&sSearch="+sSearch;
	
	var colNames = ['序号',"部门编号","部门名称", '在线人数'];
	var colModel = [
	    	   		{name:'rn',index:'rn', width:30, align:"center"},
	    	   		{name:'jgid',index:'jgid', width:30, align:"center",hidden:true},
	    	   		{name:'jgmc',index:'jgmc', width:150, align:"center"},
	    	   		{name:'online_num',index:'online_num', width:100, align:"center"}	
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
		caption: title,
		gridComplete: changeRowStyle,
        loadComplete: noRecordsTips,
		loadError: function(xhr,status,error){alert('初始化表格失败！');}
	});
	//can not edit
	jQuery("#tb_tableId").jqGrid('navGrid','#div_pageId',{add:false,edit:false,del:false,search:false},{},{},{},{overlay:0});
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
		if (cellValue != null && cellValue.substr(4, 2) == "00") {
			jQuery("#tb_tableId").jqGrid('setRowData', ids[i], false, 'ui-jqgrid-specrows');
		}
	}
}