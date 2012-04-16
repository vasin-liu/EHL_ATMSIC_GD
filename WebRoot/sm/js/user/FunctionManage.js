/**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：加载前端页面的数据表格功能表T_FUNC，实现对数据表进行 新增、编辑、过滤、删除、刷新操作

    * 作者：  LDQ
    * 日期:  2008-03-23
    */
/** 
    * desc:  编辑前端数据
    * param: did为功能编码，parentid为：父节点编码

    * return:
    * author：fengda
    * date:   2007-10-14
    * version:
    */
function doQuery(did) {
	if (did != "") {
		var url = "common.functionmanager.getDataById.d";
		url = encodeURI(url);
		var params = "did=" + did;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponseEdit});
	}
}
function showGetXMLResponseEdit(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var oldid = document.getElementById("oldid");
	var id = document.getElementById("fcode");
	var didText = document.getElementById("did");
	var parentText = document.getElementById("parent");
	var textText = document.getElementById("text");
	var im0Text = document.getElementById("im0");
	var im1Text = document.getElementById("im1");
	var im2Text = document.getElementById("im2");
	var n_callText = document.getElementById("n_call");
	var n_selectText = document.getElementById("n_select");
	var remarkText = document.getElementById("remark");
	var IsDisplayText = document.getElementById("IsDisplay");	
	var results = xmldoc.documentElement;
	results = results.childNodes;
	didText.value = results[9].text;
	parentText.value = results[0].text;
	textText.value = results[1].text;
	im0Text.value = results[2].text;
	im1Text.value = results[3].text;
	im2Text.value = results[4].text;
	n_callText.value = results[5].text;
	n_selectText.value = results[6].text;
	remarkText.value = results[7].text;
	id.value = results[8].text;
	oldid.value = results[8].text;

		 //下拉列表中的值与查询的值相同则选中
	for (var i = 0; i < IsDisplayText.length; i++) {	        
	    if (IsDisplayText.options[i].value == results[10].text) {				
		   IsDisplayText.options[i].selected = true;
		}
     }
	 didText.focus();
}
/** 
    * desc:  增加或编辑. 绑定前端数据,通过ajax传到后端进行相应的业务处理 
    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作

    * return:
    * author：fengda
    * date:   2007-10-14
    * version:
    */
function addOrUpdate(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	var fcode 	= document.getElementById("fcode");
	var oldid   = document.getElementById("oldid");
	var didText = document.getElementById("did");
	var strId   = fcode.value;
	if (strId.Trim() == "") {
		alert("功能编码不能为空！");
		return;
	}else if (strId != ""){
		if(!checkMath(strId)){
			alert("功能编码只能是数字！");
			return;
		}
		if(strId.length!=6){
			alert("功能编码只能是6位数字！");
			fcode.value = "";
			fcode.focus();
			return;
		}
	}
//	if (didText.value.Trim() == "") {
//		alert("\u8282\u70b9\u4e0d\u8bb8\u4e3a\u7a7a\uff01");
//		return false;
//	}
	var parentText = document.getElementById("parent");
	
	
	var textText 		= document.getElementById("text");
	if (textText.value.Trim() == "") {
		alert("功能名称不能为空！");
		return;
	}
	var im0Text 		= document.getElementById("im0");
	var im1Text 		= document.getElementById("im1");
	var im2Text 		= document.getElementById("im2");
	var n_callText 		= document.getElementById("n_call");
	var n_selectText	= document.getElementById("n_select");
	var remarkText 		= document.getElementById("remark");
	var IsDisplayText 		= document.getElementById("IsDisplay");	
	var url = "common.functionmanager.updateData.d?";
	var params = "insertOrUpdate=" + insrtOrUpdt;
	params = params + "&idText=" + strId;
	
	params = params + "&didText=" + didText.value;
	params = params + "&parentText=" + parentText.value;
	params = params + "&textText=" + textText.value;
	params = params + "&IsDisplayText=" + IsDisplayText.value;
	if(!checkNormalStr(textText.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&im0Text=" + im0Text.value;
	if(!checkNormalStr(im0Text.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&im1Text=" + im1Text.value;
	if(!checkNormalStr(im1Text.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&im2Text=" + im2Text.value;
	if(!checkNormalStr(im2Text.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&n_callText=" + n_callText.value;
	if(!checkNormalStr(n_callText.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&n_selectText=" + n_selectText.value;
	if(!checkNormalStr(n_selectText.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&remarkText=" + remarkText.value;
	if(remarkText.value!=""&&!checkNormalStr(remarkText.value)){
		alert("请去除特殊字符输入");
		return;
	}
	params = params + "&oldid=" + oldid.value;
	
	url = encodeURI(url + params);
	
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseModify});
}
function showResponseModify(xmlHttp) {
	alert(xmlHttp.responseText);
	Filter();
}
/** 
    * desc:   删除 .通过ajax将获取到的数据的id值传入后台

    * param:
    * return:
    * author：wangyali
    * date:   2007-10-14
    * version:
    */
function doDelete() {
	if (getDelId() != "") {
		if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) {
			var url = "common.functionmanager.delete.d?getparam=" + getDelId();
			url = encodeURI(url);
			var params = "postparam=" + getDelId();
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseDelete});
		} else {
			return;
		}
	} else {
		alert("\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u9879\u8bb0\u5f55\u8fdb\u884c\u6b64\u64cd\u4f5c");
	}
}
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	Filter();
}
/** 
    * desc:   获取选中记录的唯一标识,并放入数组，用于删除记录
    * param:
    * return:
    * author：wangyali
    * date:   2007-10-14
    * version:
    */
function getDelId() {
	var iRowsNum = mygridt_gn_s.getRowsNum();             //获取表格总的行数
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {           //循环遍历数据表的行

		if (mygridt_gn_s.cells2(i, 0).getValue() == 1) {//判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态

				var strId = mygridt_gn_s.cells2(i, 1).getValue();//获取i 行 1 列单元格数据
			delArray.push(strId);
		}
	}
	return delArray;
}
/** 
    * desc:  过滤数据.
    * param:
    * return:
    * author：luozhimei
    * date:   2007-10-14
    * version:
    */
var mygridt_gn_s = null;
function Filter() {
	var xtlb = document.getElementById("XTLB").value;
	var funcid = document.getElementById("funcid").value;
	var fname = document.getElementById("funcname").value;
	if(!checkTextDataForNORMAL(xtlb) || !checkTextDataForNORMAL(funcid) || !checkTextDataForNORMAL(fname)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var url = "common.functionmanager.filter.d";
	url = encodeURI(url);
	var params = "xtlb="+ xtlb +"&funcid=" + funcid + "&funcname=" + fname;
	params = encodeURI(params);
	mygridt_gn_s.clearAll();  //清空功能表����

	mygridt_gn_s.loadXML(url + "?" + params, pageLoaded);
}
/** 
    * desc:  重置
    * param:
    * return:
    * author：wangyali
    * date:   2007-10-31
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
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < select.length; i++) {
		if (select[i].type != "button" && !select[i].readOnly) {
			select[i].value = "-1";
		}
	}
}
/** 
    * desc:  刷新
    * param:
    * return:
    * author：wangyali
    * date:   2007-10-14
    * version:
    */
function fresh() {
	var xtlb = document.getElementById("XTLB");
	var funcid = document.getElementById("funcid");
	var fname = document.getElementById("funcname");
	xtlb.value = "";
	funcid.value = "";
	fname.value = "";
	var url = "common.functionmanager.fresh.d";
	url = encodeURI(url);
	var params = "";
	mygridt_gn_s.clearAll();  //清空功能表����

	mygridt_gn_s.loadXML(url + "?" + params, pageLoaded);
}
/** 
   * desc:  获得数据表格中加亮显示的记录的id(布控编码),限制一条数据

   * param:
   * return:
   * author：zhaoyu
   * date:   2008-03-18
   * version:
   */
function getRFId() {
	var iRowsNum = mygridt_gn_s.getRowsNum();          //获取表格总的行数
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
		if (mygridt_gn_s.cells2(i, 0).getValue() == 1) { //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态


			var strId = mygridt_gn_s.cells2(i, 1).getValue();//获取i 行 2 列单元格数据
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
    * desc:  获得数据表格中选中的记录的id(即父节点编码)
    * param:
    * return:
    * author：wangyali
    * date:   2007-10-14
    * version:
    */
function getParentid() {
	var iRowsNum = mygridt_gn_s.getRowsNum();          //获取表格总的行数
	//var rowid = mygridt_gn_s.getSelectedId();
	//var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
		if (mygridt_gn_s.cells2(i, 0).getValue() == 1) { //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			var strParentid = mygridt_gn_s.cells2(i, 7).getValue();//获取i 行 7 列单元格数据
			//alert(rfId);
		}
	}
	return strParentid;
}
/** 
    * desc:  加载工具栏组件 ，可对当前页面进行保存、增加、编辑、删除、过滤、刷新操作
    * param:
    * return:
    * author：wangyali
    * date:   2007-10-27
    * version:
    */
function onButtonClick(itemId, itemValue) {                        //工具栏的按钮单击事件（按钮的id,按钮的值）
	var strId = itemId;
	var did='';
	if (strId == "0_new") {
		var returnValuestr = window.showModalDialog("function.jsp?insrtOrUpdt=0&did=" + did, "", "dialogWidth:360px;dialogHeight:400px");
		if (returnValuestr == "insertOrUpdate") {
			Filter();
		}
	}
	if (strId == "0_edit") {
		if(getRFId() == undefined){
		    return;
		}
		var returnValuestr = window.showModalDialog("function.jsp?insrtOrUpdt=1&did=" + getRFId() + "", "", "dialogWidth:360px;dialogHeight:400px");
		if (returnValuestr == "insertOrUpdate") {
			Filter();
		}
		}
	if (strId == "0_delete") {
		doDelete();                                               //当按钮的id值为"0_delete"时，调用函数doDelete()，触发删除事件

	}
	if (strId == "0_filter") {
		Filter();                                                //当按钮的id值为"0_filter"时，调用函数getDataId()，触发过滤事件

	}
	if (strId == "0_fresh") {
		fresh();                                                //当按钮的id值为"0_fresh"时，调用函数fresh()，触发刷新事件

	}
}
/*2008-04-02 luhaifu 禁用toolbar*/
function disable(bar, arr) {
	var barr = arr;
	var str = barr.split(":");
	for (var i = 0; i < str.length; i++) {
		bar.disableItem(str[i]);
	}
}
/*2008-04-02 luhaifu 获取图片名字*/
function getLast(str) {
	var index = str.lastIndexOf("/");
	//alert(str.substring(index+1));
	return str.substring(index + 1);
}

