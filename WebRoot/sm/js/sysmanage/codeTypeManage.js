/**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：实现对数据字典类别进行 新增、编辑、删除、刷新操作
    * 作者：  李东强
    * 日期:  2008-01-25
*/

/** 
    * desc:   删除 .通过ajax将获取到的数据的代码类别传入后台
    * 作者：  李东强
    * 日期:  2008-01-25
    * version:
    */
function doDelete() {
	if (getDelId() != "") {                                        
		if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) {        
			var strUrl = "common.codeTypeManage.delete.d?getparam=" + getDelId();  
			strUrl = encodeURI(strUrl);
			var params = "idtype=" + getDelId();
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete, onSuccess:doFiniDelete});
			fresh();
		} else {            
			return;
		}
	} else {                                                
		alert("\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u9879\u8bb0\u5f55\u8fdb\u884c\u6b64\u64cd\u4f5c");
	}
}
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	fresh();
}
function doFiniDelete() {          
	 //alert("此记录被成功删除");
}

/** 
    * desc:   获取选中记录的唯一标识之一（代码类别）,并放入数组，用于删除记录
    * author：ldq
    * date:   2008-01-25
    * version:
    */
function getDelId() {
   //获取表格总的行数
	var iRowsNum = mygridt_zdlb_s.getRowsNum();                    
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	   //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_zdlb_s.cells2(i, 0).getValue() == 1) {         
            //获取单元格数据
			var strId = mygridt_zdlb_s.cells2(i,2).getValue();    
			delArray.push(strId);
		}
	}
	return delArray;
}
/** ---功能 ：编辑 --将数据放入编辑框 * 时间：2007-09-26*/
function doQuery(idtype) {
	if (idtype != "") {
		var url = "common.codeTypeManage.getDataById.d";
		url = encodeURI(url);
		var params = "idtype="+idtype;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var XTLB = document.getElementById("XTLB");
	var DMLB = document.getElementById("DMLB");
	var DMLBSM = document.getElementById("DMLBSM");
	var BZ = document.getElementById("BZ");
	var results = xmldoc.documentElement;
	results = results.childNodes;
	if (results[0].text != "") {
		XTLB.value = results[0].text;
	}
	if (results[1].text != "") {
		DMLB.value = results[1].text;
	}
	if (results[2].text != "") {
		DMLBSM.value = results[2].text;
	}
	if (results[3].text != "") {
		BZ.value = results[3].text;
	}
	DMLBSM.focus(); 
	DMLBSM.value += "";
}
function doGetXMLFini() {
}
/** 

---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 时间：2007-09-26*/
function modify(insrtOrUpdt) {
   	window.returnValue="fresh";
   	var XTLB = document.getElementById("XTLB");
	var DMLB = document.getElementById("DMLB");
	var DMLBSM = document.getElementById("DMLBSM");
	var BZ = document.getElementById("BZ");
	if (XTLB.value == ""){
		alert("请选择系统类别！");
		return;
	}
	if(DMLB.value == ""){
		alert("请选择代码类别标准！");
		return;
	}
	if(DMLBSM.value == ""){
		alert("请填写代码类别！");
		return;
	}
	if(!checkNormalStr(DMLBSM.value)){
		alert("请去除特殊字符输入");
		return;
	}
	if(!checkNormalStr(BZ.value)){
		alert("请去除特殊字符输入");
		return;
	}
	var dataArray = new Array();
	dataArray.push(XTLB.value);
	dataArray.push(DMLB.value);
	dataArray.push(DMLBSM.value);
	dataArray.push(BZ.value);
//		alert(dataArray);
	
	var url = "common.codeTypeManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseWin, onSuccess:doFiniWin});
}
function doFiniWin() {
}
function showResponseWin(xmlHttp) {
	alert(xmlHttp.responseText);
	window.close();
}
/** 
/** 
   * desc:  获得数据表格中加亮显示的记录的代码类别,限制一条数据
   * author：zhaoyu
   * date:   2008-03-18
   * version:
   */
function getIdType() {
    //获取表格总的行数
	var iRowsNum = mygridt_zdlb_s.getRowsNum();          
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	   //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态		if (mygridt_zdlb_s.cells2(i, 0).getValue() == 1) { 
            //获取单元格数据			var strId = mygridt_zdlb_s.cells2(i, 2).getValue();
			array.push(strId);
		}
	}
	if (array.length >= 2 || array.length < 1) {
		alert("请选择一条记录");
		return;
	}
	return array[0];
}
/** 
    * desc:  重置
    * author：LDQ
    * date:   2008--01--25
    * version:
    */
function reset() {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById("dataTable");
	if (queryTable != null) {
		input = queryTable.getElementsByTagName("input");
		select = queryTable.getElementsByTagName("select");
		textarea = queryTable.getElementsByTagName("textarea");
	} else {
		input = document.getElementsByTagName("input");
		select = document.getElementsByTagName("select");
		textarea = document.getElementsByTagName("textarea");
	}
	for (var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.data != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.data != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < select.length; i++) {
		if (select[i].data != "button" && !select[i].readOnly) {
			select[i].value = "-1";
		}
	}
}
//系统类别与代码类别的联动
function doOnChange(){
	var XTLB = document.getElementById("XTLB");
	var DMLB = document.getElementById("DMLB");
	if (document.activeElement == XTLB) {
		if(mygridt_zdlb_s != null && mygridt_zdlb_s != undefined){
			fillListBox("tdDmlb","DMLB","100","SELECT dmlb,dmlbsm FROM t_sys_codetype where dmlb like '"+XTLB.value+"%'","");
		}
	}
}
/** 
    * desc:  过滤数据.
    * author：lDQ
    * date:   2008--01--28
    * version:
    */
var mygridt_zdlb_s = null;
function Filter(param) {
	var XTLB = document.getElementById("XTLB").value;
	//if(XTLB == "" && param != "init"){
		//alert("请选择系统类别！");
		//$("XTLB").focus();
		//return false;
	//}
	var DMLB = document.getElementById("DMLB").value;
	var strUrl = "common.codeTypeManage.filter.d";
	strUrl = encodeURI(strUrl);
	var params = "XTLB=" + XTLB + "&DMLB=" + DMLB; 
	params = encodeURI(params); 
	mygridt_zdlb_s.clearAll();//把表格的内容全部清空
	mygridt_zdlb_s.loadXML(strUrl + "?" + params,pageLoaded);
}
/** 函数功能 ：刷新页面*/
function fresh() {
	var XTLB = document.getElementById("XTLB");
//	var DMLB = document.getElementById("DMLB");
	XTLB.value="";
//	DMLB.value="";SELECT dmlb,dmlbsm FROM t_sys_codetype where dmlb is null
	fillListBox("tdDmlb","DMLB","100","SELECT dmlb,dmlbsm FROM t_sys_codetype","");
	var strUrl = "common.codeTypeManage.filter.d";
	strUrl = encodeURI(strUrl);
	var params = ""; 
	params = encodeURI(params); 
	mygridt_zdlb_s.clearAll();  //把表格的内容全部清空
	mygridt_zdlb_s.loadXML(strUrl + "?" + params,pageLoaded);    
}
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行增加、修改、删除、查询操作
  */ 
  function disitem(){
    aToolBar.disableItem("0_excel");
	aToolBar.disableItem("0_chart");
	aToolBar.disableItem("0_info");
  }
  
    var optstat = "0";
	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {//触发新增事件，打开新增页面	
			var returnvalue = window.showModalDialog("codeTypeAdd.jsp?insrtOrUpdt=0&did=''", "", "dialogWidth:350px;dialogHeight:320px");
		    if(returnvalue == "fresh"){
		       Filter('init');
		    }
		}
		if (id == "0_edit") {//触发编辑事件，打开编辑页面
			if (getIdType() == undefined) {
			    return;
			} else {
				var returnvalue = window.showModalDialog("codeTypeEdit.jsp?insrtOrUpdt=1&idtype="+getIdType(), "", "dialogWidth:350px;dialogHeight:300px");
			   	if(returnvalue == "fresh"){
		       		Filter('init');
		    	}
			}
		}
		if (id == "0_delete") {
			doDelete();//触发删除事件
		}
		if (id == "0_filter") {
			Filter();//触发过滤事件
		}
		if (id == "0_fresh") {
			fresh();//触发刷新事件
		}
	}


