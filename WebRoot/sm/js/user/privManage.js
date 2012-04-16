var insrtOrUpdt = 0;
var treeArray = new Array();
var intI = 0;

/** 
    * desc:  编辑页面onload方法 查数据库得到操作记录明细
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function doQuery(PrivCode) {
	if (PrivCode !="") {
		var url = "common.PrivManage.getDataById.d";
		url = encodeURI(url);
		var params = "PrivCode=" + PrivCode;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showXMLData});
	}
}

/** 
    * desc:  数据写入页面
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function showXMLData(xmlHttp) {
	var xmldoc   = xmlHttp.responseXML;
	var PrivCode  	 = document.getElementById("PrivCode");
	var PrivName  	 = document.getElementById("PrivName");
	var Remark   = document.getElementById("Remark");
	//var privID = document.getElementById("PrivID");
	var sysID = document.getElementById("XTLB");
	var sys = document.getElementById("sysID");
	var results  = xmldoc.getElementsByTagName("col");
	var str = PrivCode.value ;
	var strSysID = str.substring(0,2);
	var strPrivID = str.substring(2,6);
	PrivName.value  = results[1].text;	
	Remark.value   = results[2].text;
	if(document.getElementById("trid").style.display == "none"){
	    check = "";
	}else{}
	 //下拉列表中的值与查询的值相同则选中
	for (var i = 0; i < sysID.length; i++) {	        
	    if (sysID.options[i].value == strSysID) {				
		   sysID.options[i].selected = true;
		   sys.value = sysID.options[i].text;
		}
     }
   // privID.value = strPrivID;		
	PrivCode.readOnly=true;
	PrivName.readOnly=true;
	sys.readOnly=true;
	PrivName.focus();
	getMenu();
}
/** 
    * desc:  根据不同子系统生成功能树
    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作;PrivCode 权限项的编号；sysid 子系统编号
    * return:
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function loadFuncTree(PrivCode,sysid){	
	var treeNo = PrivCode+"tree";
	var div = document.getElementById(sysid);
	if(!div.hasChildNodes()){
		treeNo = new dhtmlXTreeObject(sysid,"85%","",0);
		//将生成的树对象放入数组
		treeArray[intI] = treeNo;
		intI++;
		treeNo.setImagePath("../../image/tree/");
		//enable checkboxes
		treeNo.enableCheckBoxes(1);
		treeNo.enableThreeStateCheckboxes(true);
		//alert("tree2:"+PrivCode);
		treeNo.loadXML("common.PrivManage.load.d?PrivCode='"+ PrivCode+"'&sysid='"+sysid+"'");
		treeNo.closeAllItems(0);
	}
	
}
/** 
    * desc:  增加或编辑. 绑定前端数据,通过ajax传到后端进行相应的业务处理 
    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作    * return:
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function addOrUpdate(insrtOrUpdt) {
	window.returnValue = "fresh";
	var PrivCode = document.getElementById("PrivCode");
	var PrivName = document.getElementById("PrivName");
	var Remark   = document.getElementById("Remark");
	//var privID = document.getElementById("PrivID");
	var sysID = document.getElementById("XTLB");
	var strSysID = sysID.value;
	var dataArray = new Array();
	if (PrivCode.value == "") {
		PrivCode.value="000000";
	}
	if (sysID.value == "") {
		alert("请选择系统");
		return; 
	}
	//if(checkMath(privID.value)==false||privID.value.length<4){   
		//alert("权限编码只能输入数字，并且长度为4，请重输入！");
		//privID.focus();
		//return;
   // } 		
	if (PrivName.value.Trim() == "") {
		alert("请输入权限名称");
		return; 
	}
	if(!checkNormalStr(PrivName.value)){
		alert("请去除特殊字符输入");
		return;
	}
	if(!checkNormalStr(PrivCode.value)){
		alert("请去除特殊字符输入");
		return;
	}
	//if(!checkNormalStr(Remark.value)){
		//alert("请去除特殊字符输入");
		//return;
	//}
	
	dataArray.push(PrivCode.value);
	dataArray.push(PrivName.value);
	dataArray.push(Remark.value);

	var check = "";
	if(document.getElementById("trid").style.display == "none"){
	    check = "";
	}else{
		var treeDiv = document.getElementById("menu");
		var treeID = treeDiv.childNodes;
		for (var i=0;i<treeArray.length;i++){
		    if(treeArray[i].getAllCheckedBranches()!=""){
		      check += treeArray[i].getAllCheckedBranches()+",";
		    }		
		}
		var strFuncItem = check.split(",");	
		if (strFuncItem[0]=="") {
			alert("请选择功能项！");
			return; 
		}
	}
	var url = "common.PrivManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	if (check == "") {
		params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt+"&&Date="+new Date().getTime()+"&&strSysID="+strSysID;
	} else {
		params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt + "&check=" + check+"&&Date="+new Date().getTime()+"&&strSysID="+strSysID;
	}
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseSave});
}
function showResponseSave(xmlHttp) {
    var text = xmlHttp.responseText;
    if(text=="数据录入成功!"){
        alert(text);
        window.close();
    }else if(text=="数据修改成功!"){
        alert(text);
        window.close();
    }else{
 	   alert(text);
	   return;   
    }
}

/** 
    * desc:   获取选中记录的唯一标识,并放入数组，用于删除记录
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function getDelId() {
	//获取表格总的列数
	//var iColumnCount = mygridt_qx_s.getColumnCount();
	//获取表格总的行数
	var iRowsNum = mygridt_qx_s.getRowsNum();                      
	//var rowid = mygridt_qx_s.getSelectedId();
	var delArray = new Array();
	//循环遍历数据表的行
	for (var i = 0; i < iRowsNum; i++) { 
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态                       
		if (mygridt_qx_s.cells2(i, 0).getValue() == 1) { 
		    //获取单元格数据         
			var strId = mygridt_qx_s.cells2(i, 1).getValue();      
			delArray.push(strId);
		}
	}
	return delArray;
}
/** 
    * desc:   删除 .通过ajax将获取到的数据的id值（角色编码）传入后台
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function doDelete() {
	if (getDelId() != "") {
		if (confirm("此操作不可恢复，确认要删除吗?")){
			var url = "common.PrivManage.delete.d?getparam=" + getDelId();
			url = encodeURI(url);
			var params = "postparam=" + getDelId();
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseDelete});
		} else {
			return;
		}
	} else {
		alert("请至少选择一条记录!");
	}
}
/** 
    * desc:  返回操作返回信息
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	Filter();
	//fresh();
}
/** 
    * desc:  过滤
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
var mygridt_qx_s = null;
function Filter() {
	var sysID = document.getElementById("XTLB").value;
	var PrivName = document.getElementById("PrivName").value;
	if(!checkTextDataForNORMAL(PrivName)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var url = "common.PrivManage.filter.d";
	url = encodeURI(url);
	var params = "sysID=" + sysID + "&&PrivName=" + PrivName;
	params = encodeURI(params);
	mygridt_qx_s.clearAll();//把表格的内容全部清空
	mygridt_qx_s.loadXML(url + "?" + params,pageLoaded);
}
/** 
    * desc:  刷新
    * author：郭亮亮
    * date:   2008-06-04
    * version:
    */
function fresh() {
	var sysID = document.getElementById("XTLB");
	var PrivName = document.getElementById("PrivName");
	sysID.value = "";
	PrivName.value = "";
	var url = "common.PrivManage.filter.d";
	url = encodeURI(url);
	var params = "";
	params = encodeURI(params);
	mygridt_qx_s.clearAll();//把表格的内容全部清空
	mygridt_qx_s.loadXML(url + "?" + params,pageLoaded);
}
/** 
    * desc:  重置
    * author：郭亮亮
    * date:   2008-06-04
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
    * desc:  获得数据表格中选中的记录的id(即角色编码)
    * author：guoll
    * date:   2008-06-04
    * version:
    */
function getPId() {
    //获取表格总的行数
	var iRowsNum = mygridt_qx_s.getRowsNum();         
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_qx_s.cells2(i, 0).getValue() == 1) { 
		    //获取单元格数据			var strId = mygridt_qx_s.cells2(i, 1).getValue();
			array.push(strId);
		}
	}
	if (array.length >= 2 || array.length < 1) {
		alert("请选择一条数据");
		return;
	}
	return array[0];
}
function setValue(sysid){
	var sysID = document.getElementById("XTLB");
	if(sysid==""){
	}else{
		//下拉列表中的值与查询的值相同则选中
		for (var i = 0; i < sysID.length; i++) {	        
		    if (sysID.options[i].value == sysid) {				
			   sysID.options[i].selected = true;
			}
	     }
     }
     getMenu();
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
		var sysID = document.getElementById("XTLB");
	    var strSysID = sysID.value;
		var returnValuestr = window.showModalDialog("priv.jsp?&insrtOrUpdt=0&PrivCode=''&sysCode='" +strSysID + "'", "", "dialogWidth:440px;dialogHeight:460px");
		if (returnValuestr == "fresh") {
			Filter();
			//fresh();
		}
	}
	if (id == "0_edit") {//触发编辑事件，打开编辑页面
		if (getPId() == undefined) {
		} else {
			var returnValuestr = window.showModalDialog("priv.jsp?&insrtOrUpdt=1&PrivCode='" +getPId() + "'", "", "dialogWidth:440px;dialogHeight:460px");
			if (returnValuestr == "fresh") {
				Filter();
				//fresh();
			}
		}
	}
	if (id == "0_delete") {
		doDelete();  //触发删除事件
	}
	if (id == "0_filter") {
		Filter();//触发过滤事件
	}
	if (id == "0_fresh") {
		fresh(); //触发刷新事件
	}
}

