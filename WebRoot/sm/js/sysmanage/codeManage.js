/**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：实现对数据字典表进行 新增、编辑、删除、刷新操作    * 作者：  李东强    * 日期:  2008-01-25
*/

/** 
    * desc:   删除 .通过ajax将获取到的数据的id值（人员编码）传入后台
    * 作者：  李东强    * 日期:  2008-01-25
    * version:
    */
function doDelete() {
	if (getDelId() != "") {                                         
		if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) {                                  
			var strUrl = "common.codemanage.delete.d?getparam=" + getDelId();
			strUrl = encodeURI(strUrl);
			var params = "postparam=" + getDelId()+"&idtype="+getDelIdType();
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete, onSuccess:doFiniDelete});
			fresh();
		} else {            
			return;
		}
	} else {

		alert("\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u9879\u8bb0\u5f55\u8fdb\u884c\u6b64\u64cd\u4f5c");
	}
}
/** 
    * desc:   获取选中记录的唯一标识（人员编码）,并放入数组，用于删除记录壹
    * author：ldq
    * date:   2008-01-25
    * version:
    */
function getDelId() {
    //获取表格总的行数
	var iRowsNum = mygridt_zd_s.getRowsNum();                    
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_zd_s.cells2(i, 0).getValue() == 1) {         
            //获取单元格数据
			var strId = mygridt_zd_s.cells2(i,4).getValue();    
			delArray.push(strId);
		}
	}
	return delArray;
}
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	fresh();
}
function doFiniDelete() {          
}
/** 
    * desc:   获取选中记录的唯一标识之一（代码类别）,并放入数组，用于删除记录
    * author：ldq
    * date:   2008-01-25
    * version:
    */
function getDelIdType() {
    //获取表格总的行数
	var iRowsNum = mygridt_zd_s.getRowsNum();                    
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_zd_s.cells2(i, 0).getValue() == 1) {         
            //获取单元格数据
			var strId = mygridt_zd_s.cells2(i,1).getValue();    
			delArray.push(strId);
		}
	}
	return delArray;
}
/** ---功能 ：编辑 --将数据放入编辑框 * 时间：2007-09-26*/
function doQuery(did,idtype) {
	if (did != "") {
		var url = "common.codemanage.getDataById.d";
		url = encodeURI(url);
		var params = "did=" + did+"&idtype="+idtype;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var XTLB = document.getElementById("XTLB");
	var DMLB = document.getElementById("DMLB");
	var codetype = document.getElementById("codetype");
	var DM = document.getElementById("DM");
	var DMSM = document.getElementById("DMSM");
	var BZ = document.getElementById("BZ");
	var results = xmldoc.documentElement;
	results = results.childNodes;
	if (results[0].text != "") {
		XTLB.value = results[0].text;
	}
	DMLB.value = results[1].text;
	DM.value = results[2].text;
	DMSM.value = results[3].text;
	BZ.value = results[4].text;
	codetype.value = results[5].text;
	DMSM.focus(); 
	DMSM.value += "";
}
function doGetXMLFini() {
}
/** 功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 时间：2007-09-26*/
function modify(insrtOrUpdt) {
   	window.returnValue="fresh";
   	var XTLB = $("XTLB");
	var DMLB = document.getElementById("DMLB");
	var DM = document.getElementById("DM");
	var DMSM = document.getElementById("DMSM");
	var BZ = document.getElementById("BZ");
	
	if(XTLB.value == ""){
		alert("请选择系统类别！");
		XTLB.focus();
		return;
	}
	if (DMLB.value == "") {
		alert("请选择代码类别！");
		DMLB.focus();
		return;
	}
	if (DM.value == "") {
		alert("请输入代码！");
		DM.focus();
		return;
	}
	if (DM.value.length > 100) {
		alert("请输入100位以下代码说明");
		DM.focus();
		return;
	}
	if(!checkNormalStr(DMSM.value)){
		alert("请去除特殊字符输入");
		DMSM.focus();
		return;
	}
	if(!checkNormalStr(BZ.value)){
		alert("请去除特殊字符输入");
		BZ.focus();
		return;
	}
	var dataArray = new Array();
	dataArray.push(DMLB.value);
	dataArray.push(DM.value);
	dataArray.push(DMSM.value);
	dataArray.push(BZ.value);
	dataArray.push(XTLB.value);
	
	var url = "common.codemanage.updateData.d";
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
    * desc:  获得数据表格中加亮显示的记录代码,限制一条数据    * author：zhaoyu
    * date:   2008-03-18
    * version:
    */
function getRFId() {
    //获取表格总的行数
	var iRowsNum = mygridt_zd_s.getRowsNum();          
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态		if (mygridt_zd_s.cells2(i, 0).getValue() == 1) {
		    //获取单元格数据			var strId = mygridt_zd_s.cells2(i, 4).getValue();
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
   * desc:  获得数据表格中加亮显示的记录的代码类别,限制一条数据   * author：zhaoyu
   * date:   2008-03-18
   * version:
   */
function getIdType() {
    //获取表格总的行数
	var iRowsNum = mygridt_zd_s.getRowsNum();          
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态		if (mygridt_zd_s.cells2(i, 0).getValue() == 1) {
		  //获取单元格数据 
			var strId = mygridt_zd_s.cells2(i, 1).getValue();
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
function doOnChange(){
	var XTLB = document.getElementById("XTLB");
	var DMLB = document.getElementById("DMLB");
	if (document.activeElement == XTLB) {
		if(DMLB.value == "" && XTLB.value != ""){
			if(mygridt_zd_s == null && mygridt_zd_s == undefined){
				fillListBox("tdDmlb","DMLB","100%","SELECT dmlb,dmlbsm FROM t_sys_codetype where dmlb like '"+XTLB.value+"%'","");
			}else {
				fillListBox("tdDmlb","DMLB","100","SELECT dmlb,dmlbsm FROM t_sys_codetype where dmlb like '"+XTLB.value+"%'","");
			}
		}
	}
}
/** 
    * desc:  过滤数据.
    * author：lDQ
    * date:   2008--01--28
    * version:
    */
var mygridt_zd_s = null;
function Filter() {
	var XTLB = document.getElementById("XTLB").value;
	var DMLB = document.getElementById("DMLB").value;
	var DM = document.getElementById("DM").value;
	var DMSM = document.getElementById("DMSM").value;
	if(!checkTextDataForNORMAL(DM) || !checkTextDataForNORMAL(DMSM)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var strUrl = "common.codemanage.filter.d";
	strUrl = encodeURI(strUrl);
	var params = "XTLB=" + XTLB + "&DMLB=" + DMLB + "&DM=" + DM + "&DMSM=" + DMSM;
	params = encodeURI(params); //进行编码转换;
	mygridt_zd_s.clearAll();//把表格的内容全部清空
	mygridt_zd_s.loadXML(strUrl + "?" + params,pageLoaded); 
}
/** 函数功能 ：刷新页面*/
function fresh() {
	var DMLB = document.getElementById("DMLB");
	var DM = document.getElementById("DM");
	var DMSM = document.getElementById("DMSM");
	DM.value="";
	DMSM.value="";
	DMLB.value="";
	var strUrl = "common.codemanage.filter.d";
	strUrl = encodeURI(strUrl);
	var params = "";//传入的参数  
	params = encodeURI(params); 
	mygridt_zd_s.clearAll();//把表格的内容全部清空
	mygridt_zd_s.loadXML(strUrl + "?" + params,"");    
}
/** 
    * desc: 显示顺序设置页面
    * author：wyl
    * date:  2008-12-31
    */
  function showCodeList(){
  	var XTLB = document.getElementById("XTLB").value;
	var DMLB = document.getElementById("DMLB").value;
	var DM = document.getElementById("DM").value;
	var DMSM = document.getElementById("DMSM").value;	
	var params = XTLB + "," + DMLB + "," + DM + "," + DMSM; 
	params = encodeURI(params);  
     var top=(screen.availHeight-460)/2;		
	 var left=(screen.availWidth-735)/2;
     var returnValues=window.showModalDialog("codeList.jsp?queryTerm="+params, "", "dialogWidth:650px;dialogHeight:520px");	 
     if(returnValues=="fresh"){
	   Filter();
	  }
  }
 /** 
    * desc: 显示顺序设置页面加载数据表格
    * author：wyl
    * date:  2008-12-31
    */
 var codeListGrid;
 function getCodeGrid(queryTerm) {
    var termArr=queryTerm.split(",");	
    var XTLB = termArr[0];
	var DMLB = termArr[1];
	var DM = termArr[2];
	var DMSM = termArr[3];
    XTLB = encodeURI(XTLB); 
    DMLB = encodeURI(DMLB); 
    DM = encodeURI(DM); 
    DMSM = encodeURI(DMSM); 
	codeListGrid = new dhtmlXGridObject("codeTab");
	codeListGrid.setImagePath("../../image/table/");
	codeListGrid.setHeader("1,序号,代码,代码说明,1,代码类别");
	codeListGrid.setInitWidths("0,55,100,320,0,120");
	codeListGrid.setColAlign("center,center,center,center,center,center");
	codeListGrid.setColTypes("ch,ed,ro,ro,ro,ro");
	//codeListGrid.setColSorting("str,str,str,str,str,str");//无排序
	codeListGrid.setColOrder("int,str,str,str,str,str");//可排序
	codeListGrid.init();
	codeListGrid.loadXML("common.codemanage.getCodeList.d?XTLB=" + XTLB + "&DMLB=" + DMLB + "&DM=" + DM + "&DMSM=" + DMSM);
} 
 /** 
    * desc: 显示顺序设置页面保存
    * author：wyl
    * date:  2008-12-31
    */
function saveCodeList() {
  	var strUrl = "common.codemanage.editCodeList.d?";
	strUrl = encodeURI(strUrl);
	var params = "xh=" + getNewXH()+"&dmlbID=" + getDMLB()+"&dm=" + getDM();
	new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showRepUpdate, onSuccess:doFiniUpdate});
}
function showRepUpdate(xmlHttp) {
	alert(xmlHttp.responseText);
}
function doFiniUpdate() {          
}
 /** 获取显示顺序设置页面序号值 */
function getNewXH() {
	var iRowsNum = codeListGrid.getRowsNum();      
	var xhArray = new Array();
	for (var i = 0; i < iRowsNum; i++) { 
		var strId = codeListGrid.cells2(i,1).getValue();    
		xhArray.push(strId);
	}

	return xhArray;
}
 /** 获取显示顺序设置页面代码值 */
function getDM() {
	var iRowsNum = codeListGrid.getRowsNum();         
	var dmArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
		var strId = codeListGrid.cells2(i,2).getValue();  
		dmArray.push(strId);
	}

	return dmArray;
}
 /** 获取显示顺序设置页面代码类别值 */
function getDMLB() {
	var iRowsNum = codeListGrid.getRowsNum(); 
	var dmlbArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
		var strId = codeListGrid.cells2(i,4).getValue();  
		dmlbArray.push(strId);
	}
	return dmlbArray;
}
/** 
    * desc:   关闭子页面
    * author：wangyl
    * date:   2008-12-31
    */
function doClose(){
	window.returnValue ="fresh";
 	window.close();
}
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行增加、修改、删除、转存Excel、查询操作
  */
  
  function disitem(){
    aToolBar.disableItem("0_excel");
	aToolBar.disableItem("0_chart");
	aToolBar.disableItem("0_info");
  }
  
    var optstat = "0";
	function onButtonClick(itemId, itemValue) {//工具栏的按钮单击事件（按钮的id,按钮的值）
		var id = itemId;
		if (id == "0_new") {//触发新增事件，打开新增页面	
			var XTLB = document.getElementById("XTLB").value;
			var DMLB = document.getElementById("DMLB").value;
			if(DMLB != "" && XTLB == ""){
				XTLB = DMLB.substring(0,2)
			}
			var returnvalue = window.showModalDialog("code.jsp?insrtOrUpdt=0&XTLB="+ XTLB +"&DMLB="+ DMLB, "", "dialogWidth:350px;dialogHeight:320px");
		    if(returnvalue == "fresh"){
		       Filter();
		    }
		}
		if (id == "0_edit") {//触发编辑事件，打开编辑页面
			if (getRFId() == undefined) {
			    return;
			} else {
			var returnvalue = window.showModalDialog("codeEdit.jsp?insrtOrUpdt=1&did=" + getRFId() + "&idtype="+getIdType(), "", "dialogWidth:350px;dialogHeight:320px");
			   if(returnvalue == "fresh"){
		       Filter();
		    }
			}
		}
		if (id == "0_delete") {
			doDelete();//触发删除事件
		}
		if (id == "0_excel") {
			saveAsExcel("字典管理信息表", "code", mygridt_zd_s);
		}
		
		if (id == "0_filter") {
			Filter();//触发过滤事件
		}
		if (id == "0_fresh") {
			fresh();//触发刷新事件
		}
	}


