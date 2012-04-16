/**
 * 项目名称：EHL_ATMSIC_GD<br>
 * 文件路径：policeWork/js/released<br>
 * 文件名称：releasedInfoQuery.js<br>
 * 文件编号： <br>
 * 文件描述： 查询所有已经发布的信息，包括施工占道和交通拥堵。<br>
 *
 * 版本信息： Ver 1.1<br>
 * 创建日期：2012-3-26 <br>
 * 公司名称： 北京易华录信息技术股份有限公司 2012 Copyright(C) 版权所有 <br>
 * *************************************************<br>
 * 创建人：Vasin <br>
 * 创建日期：2012年3月26日16:03:40 <br>
 * ************ 修改历史 *************<br>
 * 修改人：Vasin <br>
 * 修改时间：2012年3月26日16:03:45 <br>
 * 修改备注： <br>
 */
jQuery(document).ready(function () {
//        jQuery("#startDate").val(new Date().format("yyyy-MM-dd"));
//	      jQuery("#endDate").val(new Date().format("yyyy-MM-dd"));
        loadGrid();
    }
);

/**
 *
 * 方法名称：reloadGrid<br>
 * 方法描述： 重新装载表格 <br><br>
 * 版本信息：Ver 1.1 <br>
 ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:05:19 <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:05:23   <br>
 * 修改备注：    <br>
 */
function reloadGrid() {
    checkDate();
    var startDate = jQuery("#startDate").val();
    var endDate = jQuery("#endDate").val();
    var deptId = jQuery("#depUnitId").val();
    var flowState = jQuery("#flowState").val();
    var infoType = jQuery("#infoType").val();
    var sSearch = "";
    var urlParent = "policeWorks.released.getReleasedInfo.d?startDate=" + startDate
        + "&endDate=" + endDate + "&sSearch='" + sSearch + "'&deptId=" + deptId
        + "&infoType=" + infoType + "&flowState=" + flowState;

    //encode for chinense
    urlParent = encodeURI(urlParent);
    jQuery("#tb_tableId").jqGrid('setGridParam', {url:urlParent}).trigger("reloadGrid");
}

/**
 *
 * 方法名称：loadGrid<br>
 * 方法描述： 装载表格 <br>
 * 版本信息：Ver 1.1 <br>
 ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:05:28  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:05:31   <br>
 * 修改备注：    <br>
 */
function loadGrid() {
    checkDate();
    var startDate = jQuery("#startDate").val();
    var endDate = jQuery("#endDate").val();
    var deptId = jQuery("#depUnitId").val();
    var flowState = jQuery("#flowState").val();
    var infoType = jQuery("#infoType").val();
    var sSearch = "";
    var urlParent = "policeWorks.released.getReleasedInfo.d?startDate=" + startDate
        +"&endDate" + endDate + "&deptId=" +deptId + "&sSearch='" + sSearch + "'"
        + "&infoType=" + infoType + "&flowState=" + flowState;;

    var colNames = ['序号', "警情编号",'标题', '发布时间', "信息类型", '发布状态','计分状态', '查看', '处理'];
    var colModel = [
        {name:'ROW_NUM', index:'ROW_NUM', width:30, align:"center",sortable:false},
        {name:'alarmid', index:'alarmid', width:30, align:"center", hidden:true,sortable:false},
        {name:'title',index:'title',width:150,align:'center',sortable:false},
        {name:'releasedDate', index:'releasedDate', width:120, align:"center"},
        {name:'INFO_TYPE', index:'INFO_TYPE', width:70, align:"center",sortable:false},
        {name:'PUBLISHTYPE', index:'PUBLISHTYPE', width:70, align:"center",sortable:false,formatter:releaseStatusTextChange},
        {name:'SCORING', index:'SCORING', width:70, align:"center",sortable:false,formatter:scoringStatusTextChange},
        {name:'STATE', index:'STATE', width:30, align:"center",sortable:false, formatter:viewOrEditRecord},
        {name:'EVENTSTATE', index:'EVENTSTATE', width:30, align:"center",sortable:false, formatter:viewOrEditRecord}
    ];
    //encode for chinense
    urlSubGrid = encodeURI(urlParent);

    var title = "";

    jQuery("#tb_tableId").jqGrid({
        url:urlParent,
        datatype:"json",
        colNames:colNames,
        colModel:colModel,
        rowNum:10,
        rowList:[10, 20, 40],
        width:810,
        height:320,
        pager:'#div_pageId',
        sortname:'releasedDate',
        viewrecords:true,
//	    sortorder: "asc",
        multiselect:false,
        subGrid:false,
        caption:title,
        /*subGridRowExpanded:secondLevelGrid,
        subGridRowColapsed:function (subgrid_id, row_id) {
            // this function is called before removing the data
			var subgrid_table_id;
			subgrid_table_id = subgrid_id+"_t";
			jQuery("#"+subgrid_table_id).remove();
        },*/
//        gridComplete:changeRowStyle,
        loadComplete:noRecordsTips,
        loadError:function (xhr, status, error) {
            alert('初始化表格失败！');
        }
    });
    //can not edit
    jQuery("#tb_tableId").jqGrid('navGrid', '#div_pageId', {add:false, edit:false, del:false, search:false}, {}, {}, {}, {overlay:0});
}

/**
 *
 * 方法名称：noRecordsTips<br>
 * 方法描述： 无记录时的提示信息 <br><br>
 * 版本信息：Ver 1.1 <br>
 ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:06:06  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:06:12   <br>
 * 修改备注：    <br>
 */
function noRecordsTips(data) {
    if (data != null && data.records != null && data.records <= 0) {
        if (jQuery(".norecords").html() == null) {
            jQuery(this).parent().append(
                "<div class=\"norecords\">没有符合的数据！</div>");
        }
        jQuery(".norecords").show();
    } else {
        jQuery(".norecords").hide();
    }
}

/**
 *
 * 方法名称：viewOrEditRecord<br>
 * 方法描述： 格式化单元格 <br>
 * @param cellvalue - is the value to be formatted
 * @param options - is an object containing the following element ; options : { rowId: rid, colModel: cm} where rowId - is the id of the row colModel is the object of the properties for this column getted from colModel array of jqGrid
 * @param rowObject - is a row data represented in the format determined from datatype option. If we have datatype: xml/xmlstring - the rowObject is xml node,provided according to the rules from xmlReader If we have datatype: json/jsonstring - the rowObject is array, provided according to the rules from jsonReader
 * @returns string - new_formated_cellvalue <br>
 * 版本信息：Ver 1.1 <br>
 ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:07:33  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:07:36   <br>
 * 修改备注：    <br>
 */
function viewOrEditRecord(cellvalue, options, rowObject) {
    // format the cellvalue to new format
    var new_formated_cellvalue;
    if (cellvalue != null && cellvalue != "") {
        if(cellvalue.length < 2){
            new_formated_cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showDetail('" + rowObject[1] + "','" + rowObject[8] + "','" + rowObject[4] + "',1);\" \>";
        }else if(cellvalue.length > 2 && rowObject[6] != null && rowObject[6] == 1){
            new_formated_cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"showDetail('" + rowObject[1] + "','" + rowObject[8] + "','" + rowObject[4] + "',2);\" \>";
        }else if(cellvalue.length > 2 && rowObject[6] != null && rowObject[6] == 2){
            new_formated_cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('该信息已经处理完成！')\" \>";
        }
    } else {
        new_formated_cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showDetail('" + rowObject[1] + "','" + rowObject[8] + "','" + rowObject[4] + "',1);\" \>";
    }
    return new_formated_cellvalue;
}

/**
 *
 * 方法名称：releaseStatusTextChange<br>
 * 方法描述： 格式化单元格 <br>
 * @param cellvalue - is the value to be formatted
 * @param options - is an object containing the following element ; options : { rowId: rid, colModel: cm} where rowId - is the id of the row colModel is the object of the properties for this column getted from colModel array of jqGrid
 * @param rowObject - is a row data represented in the format determined from datatype option. If we have datatype: xml/xmlstring - the rowObject is xml node,provided according to the rules from xmlReader If we have datatype: json/jsonstring - the rowObject is array, provided according to the rules from jsonReader
 * @returns string - new_formated_cellvalue <br>
 * 版本信息：Ver 1.1 <br>
 ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:07:33  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:07:36   <br>
 * 修改备注：    <br>
 */
function releaseStatusTextChange(cellvalue, options, rowObject) {
    // format the cellvalue to new format
    var new_formated_cellvalue;
    if (cellvalue != null && parseInt(cellvalue) > 0) {
        if(parseInt(cellvalue) == 1){
            new_formated_cellvalue = "<span>公安网发布</span>";
        }else if(parseInt(cellvalue) == 2){
            new_formated_cellvalue = "<span>互联网发布</span>";
        }
    } else {
        new_formated_cellvalue = "<span>公安网发布</span>";
    }
    return new_formated_cellvalue;
}

/**
 *
 * 方法名称：scoringStatusTextChange<br>
 * 方法描述： 格式化单元格 <br>
 * @param cellvalue - is the value to be formatted
 * @param options - is an object containing the following element ; options : { rowId: rid, colModel: cm} where rowId - is the id of the row colModel is the object of the properties for this column getted from colModel array of jqGrid
 * @param rowObject - is a row data represented in the format determined from datatype option. If we have datatype: xml/xmlstring - the rowObject is xml node,provided according to the rules from xmlReader If we have datatype: json/jsonstring - the rowObject is array, provided according to the rules from jsonReader
 * @returns string - new_formated_cellvalue <br>
 * 版本信息：Ver 1.1 <br>
 ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:07:33  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:07:36   <br>
 * 修改备注：    <br>
 */
function scoringStatusTextChange(cellvalue, options, rowObject) {
    // format the cellvalue to new format
    var new_formated_cellvalue;
    if (cellvalue != null && parseInt(cellvalue) > 0) {
        if(parseInt(cellvalue) == 1){
            new_formated_cellvalue = "<span>已计分</span>";
        }else if(parseInt(cellvalue) == 2){
            new_formated_cellvalue = "<span>未计分</span>";
        }
    } else {
        new_formated_cellvalue = "<span>未计分</span>";
    }
    return new_formated_cellvalue;
}

/**
 *点击查看或者修改显示详细信息窗口
 * @param alarmid   警情编号
 * @param state     信息状态
 * @param type      信息类型
 * @param viewOrUpdate     查看或修改状态
 *  ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:07:33  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:07:36   <br>
 * 修改备注：    <br>
 */
function showDetail(alarmid,state,type,viewOrUpdate){
   if(type == "交通拥堵"){
       window.showModalDialog(
           "../../../dispatch/ehl/cpoliceedit/TrafficCrowdInfoAdd.jsp?tmp=" + Math.random()
               + "&insrtOrUpdt=2"+"&alarmId='" + alarmid + "'"
               + "&trfficeCrowState=" + state + "&isScoring=" + viewOrUpdate, "",
                "dialogWidth:800px;dialogHeight:600px");
   }else{
       window.showModalDialog("../../../dispatch/ehl/cpoliceedit/RoadBuildAdd.jsp?tmp="
           + Math.random() + "&insrtOrUpdt=2"+"&alarmId=" + alarmid
           + "&buildState=" + state + "&isScoring=" + viewOrUpdate, "",
           "dialogWidth:800px;dialogHeight:600px");
   }
}

/**
 *检查日期是否合法
 * ***********************************<br>
 * 创建人：Vasin   <br>
 * 创建时间：2012年3月26日17:07:33  <br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日17:07:36   <br>
 * 修改备注：    <br>
 */
function checkDate(){
    var startDate = jQuery('#startDate').val();
    var endDate = jQuery('#endDate').val();
    if(startDate && endDate){
        if(startDate > endDate){
            alert("开始时间不能大于结束时间！");
        }
    }else if( !startDate && endDate  ){
        alert("开始时间不能为空!");
    }else if( startDate && !endDate  ){
        alert("结束时间不能为空!");
    }
}


