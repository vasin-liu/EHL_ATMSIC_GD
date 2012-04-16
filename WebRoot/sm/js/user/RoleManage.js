/**
    * Copyright (c) 2008, EHL Technologies, Inc.
    * All right reserved.
    * 说明：实现对角色数据表进行 新增、编辑、过滤、删除、刷新操作
    * date:   2880-03-19
    */
 var insrtOrUpdt = 0;
var treeArray = new Array();
var intI = 0;   
/** 
    * desc:  初始化编辑页面
    * param: did为角色编码
    * return:
    * author：zhaoyu
    * date:   2008-06-04
    * version:
    */
function doQuery(did,insertOrUpdate) {
	if (insertOrUpdate != 0) {
		var url = "common.RoleManage.getDataById.d?did=" + did;
		url = encodeURI(url);
		var params = "";
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}else{
	  var did =document.getElementById("codetr");
	      did.style.display = "none";
	}
}
	
function showGetXMLResponse(xmlHttp) {
	var xmldoc   = xmlHttp.responseXML;
	var jname  	 = document.getElementById("jname");
	var desc     = document.getElementById("desc");
	var remark   = document.getElementById("remark");
	var privbox  = document.getElementsByName("priv_id");
	var results   = xmldoc.getElementsByTagName("col");
	var privs   = xmldoc.getElementsByTagName("priv");
	jname.value  = results[1].text;
	desc.value   = results[2].text;
	remark.value = results[3].text;
	if(privs != null){
	
		for(var i = 0;i<privbox.length; i++){
		   for(var j = 0; j<privs.length; j++){
		      if(privbox[i].value == privs[j].text){
		         privbox[i].checked = true;
		      }
		   }
	     }
	} 
	
}
	
function doGetXMLFini() {

}
	
/** 
    * desc:  增加或编辑. 绑定前端数据,通过ajax传到后端进行相应的业务处理 
    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作
    * return:
    * author：ldq
    * date:   2008-03-19
    * version:
    */

function addOrUpdate(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	var rolecode = document.getElementById("did");
	var rolename = document.getElementById("jname");
	var roledesc = document.getElementById("desc");
	var remark   = document.getElementById("remark");
	
	var dataArray = new Array();
	var baxArray = new Array();
	var val = rolename.value;
	if (rolename.value.Trim()== "") {
		alert("\u8bf7\u8f93\u5165\u89d2\u8272\u540d\u79f0");
		return;
	}
	if(!checkNormalStr(rolename.value)){
		alert("请去除特殊字符输入");
		return;
	}
	//if(!checkNormalStr(roledesc.value)){
		//alert("请去除特殊字符输入");
		//return;
	//}
/**
	if(!checkNormalStr(remark.value)){
		alert("请去除特殊字符输入");
		return;
	}
*/
	
	var checkStr = "";
	var treeDiv = document.getElementById("role_id");
	var treeID = treeDiv.childNodes;
	for (var i=0;i<treeArray.length;i++){
	    var checkItems = treeArray[i].getAllCheckedBranches();
	    if(checkItems!=""){	
	      var strItems = checkItems.split(",");  
	      for(var j=0;j<strItems.length;j++){
	          if(strItems[j].length==6){//判断是否是6位编码
	             checkStr += strItems[j]+",";
	          }
	      }
 	    }		
	}

	if (checkStr=="") {
		alert("请选择权限项！");
		return; 
	}
	
	dataArray.push(checkStr);	
	dataArray.push(rolecode.value);
	dataArray.push(rolename.value);
	dataArray.push(roledesc.value);
	dataArray.push(remark.value);
	
	var url = "common.RoleManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	params = "xmlStr=" + fullbody + "&check=" +checkStr+"&insertOrUpdate=" + insrtOrUpdt ;
	params = encodeURI(params);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseSave, onSuccess:doFiniSave});
}

function showResponseSave(xmlHttp) {
    var text = xmlHttp.responseText;
    if(text=="数据录入成功！"){
        alert(text);
        window.close();
    }else if(text=="数据修改成功！"){
        alert(text);
        window.close();
    }else{
 	   alert(text);
	   return;   
    }
}

function doFiniSave() {
 //alert("u6570u636eu5f55u5165u6210u529f");
}
	
/** 
    * desc:   删除 .通过ajax将获取到的数据的id值（角色编码）传入后台
    * author：ldq
    * date:   2008-06-04
    * version:1.2
    */
function doDelete() {
	if (getDelId() != "") {
		if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) {
			var url = "common.RoleManage.delete.d?roleCodes=" + getDelId();
			url = encodeURI(url);
			var params = "";
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseDelete, onSuccess:doFiniDelete});
		} else {
			return;
		}
	} else {
		alert("\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u9879\u8bb0\u5f55\u8fdb\u884c\u6b64\u64cd\u4f5c");
	}
}
	
/** 
    * desc:   获取选中记录的唯一标识,并放入数组，用于删除记录
    * author：ldq
    * date:   2008-03-19
    * version:
    */
function getDelId() {
	//var iColumnCount = mygridt_js_s.getColumnCount();
	 //获取表格总的行数
	var iRowsNum = mygridt_js_s.getRowsNum();                     
	//var rowid = mygridt_js_s.getSelectedId();
	var delArray = new Array();
	//循环遍历数据表的行
	for (var i = 0; i < iRowsNum; i++) {                        
         //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_js_s.cells2(i, 0).getValue() == 1) {          
            //获取单元格数据
			var strId = mygridt_js_s.cells2(i, 1).getValue();      
			delArray.push(strId);
		}
	}
	return delArray;
}


function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	freshs();
}

function doFiniDelete() {
	//alert("u6b64u8bb0u5f55u88abu6210u529fu5220u9664");
}
/** 
    * desc:  过滤数据.
    * author：ldq
    * date:   2008-03-19
    * version:
    */
var mygridt_js_s = null;
function Filter() {
	var jcode = document.getElementById("jcode").value;
	var jname = document.getElementById("jname").value;
	if(!checkTextDataForNORMAL(jcode) || !checkTextDataForNORMAL(jname)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var url = "common.RoleManage.filter.d";
	url = encodeURI(url);
	var params = "jcode=" + jcode + "&&jname=" + jname;
	params = encodeURI(params);
	mygridt_js_s.clearAll();//把表格的内容全部清空
	mygridt_js_s.loadXML(url + "?" + params,pageLoaded);
}

/** 
    * desc:  权限树
    * author：  wyl
    * date:   2009-10-16
    * version:
    */	
	
	function loadPrivTree(RoleCode,insrtOrUpdt){	
	var treeNo = new dhtmlXTreeObject("role_id","85%","",0);
	//将生成的树对象放入数组
	treeArray[intI] = treeNo;
	intI++;
	treeNo.setImagePath("../../image/tree/");
	//enable checkboxes
	treeNo.enableCheckBoxes(1);
	treeNo.enableThreeStateCheckboxes(true);
	treeNo.loadXML("common.RoleManage.loadTree.d?RoleCode="+ RoleCode);
	treeNo.closeAllItems(0);
}	
/** 
    * desc:  重置
    * author：ldq
    * date:   2008-03-19
    * version:
    */
function reset() {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById("dataTable");
	var privObj = document.getElementsByName("priv_id");
	for(var i = 0; i < privObj.length; i++){
	    privObj[i].checked = false;
	}
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
    * author：ldq
    * date:   2008-03-19
    * version:
    */
function freshs() {

	var jcode = document.getElementById("jcode");
	var jname = document.getElementById("jname");	
	jcode.value = "";
	jname.value = "";
	var url = "common.RoleManage.load.d";
	url = encodeURI(url);
	var params = "";
	params = encodeURI(params);
	mygridt_js_s.clearAll();//把表格的内容全部清空
	mygridt_js_s.loadXML(url + "?" + params,pageLoaded);
}
/** 
    * desc:  获得数据表格中选中的记录的id(即角色编码)
    * author：ldq
    * date:   2008-03-19
    * version:
    */

function getPId() {
    //获取表格总的行数
	var iRowsNum = mygridt_js_s.getRowsNum();         
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_js_s.cells2(i, 0).getValue() == 1) { 
            //获取单元格数据
			var strId = mygridt_js_s.cells2(i, 1).getValue();
		    array.push(strId);
		}
	}
	if (array.length >= 2 || array.length < 1) {
		alert("请选择一条数据");
		return;
	}
	return array[0];
}
/** 
    * desc:  工具栏的按钮单击事件（按钮的id,按钮的值）
    * author：ldq
    * date:   2008-03-19
    * version:
    */
function onButtonClick(itemId, itemValue) { 
	var id = itemId;
	if (id == "0_new") { //触发新增事件，打开新增页面	
		var returnValuestr = window.showModalDialog("role.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&did=''", "", "dialogWidth:480px;dialogHeight:550px");
		if (returnValuestr == "insertOrUpdate") {
			freshs();
		}
	}
	if (id == "0_edit") {//触发编辑事件，打开编辑页面
		if (getPId() == undefined) {
		} else {
			var returnValuestr = window.showModalDialog("role.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&did='" +getPId() + "'", "", "dialogWidth:480px;dialogHeight:550px");
			if (returnValuestr == "insertOrUpdate") {
				freshs();
			}
		}
	}
	if (id == "0_delete") {
		doDelete();  //触发删除事件		                      
	}
	if (id == "0_filter") {
		Filter();  //触发过滤事件
	}
	if (id == "0_fresh") {
		freshs(); //触发刷新事件
	}
	if (id == "0_excel") {
		doExcel(); //触发转存事件
	}
}
function doExcel(){
    var dataSql = "SELECT ROLECODE, ROLENAME, ROLEDESC, f_get_priv(ROLECODE) FROM T_SYS_ROLE";
	    var header = "角色编号,角色名称,角色描述,所有权限";
		saveFieldsAsExcel("\u4eba\u5458\u57fa\u672c\u4fe1\u606f", "user", header,dataSql);
}
