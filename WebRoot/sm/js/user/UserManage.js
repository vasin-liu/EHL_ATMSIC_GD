/**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：实现对人员数据表进行 新增、编辑、过滤、删除、刷新操作
    * 作者：  ldq
    * 日期:  2008-3-17
    */
  function onload() {
	var strUrl = "common.usermanage.onload.d";
	strUrl = encodeURI(strUrl);	
	mygridt_yh_s.clearAll();                                       //把表格的内容全部清空
	mygridt_yh_s.loadXML(strUrl,pageLoaded);                      //重新加载参数
}   
/** 
    * desc:   退出 .通过ajax传入后台清空用户session，写日志
    * author：ldq
    * date:   2008--3--17
    * version:
    */
var insrtOrUpdt;
var forEditPersonCode;
function doLogout(header) {
	if(header == "header" && header!=""){
		if(confirm("确认退出系统？")) {
			var strUrl = "common.userlogin.logout.d?update=1"; 
			strUrl = encodeURI(strUrl);
			var params = "" ;
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
		}
	}else{
		var strUrl = "common.userlogin.logout.d?update=1"; 
		strUrl = encodeURI(strUrl);
		var params = "" ;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
	}
}
function showResponseLogout() {
	//跳转
	parent.document.location.href="./login.jsp";
}

/** 
    * desc:   删除 .通过ajax将获取到的数据的id值（人员编码）传入后台
    * author：ldq
    * date:   2008--3--17
    * version:
    */
function doDelete() {
	if (getDelId() != "") {                                       
		if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) {                                      
			var strUrl = "common.usermanage.delete.d?getparam=" + getDelId(); 
			strUrl = encodeURI(strUrl);
			var params = "postparam=" + getDelId();
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
			onload();
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
    * desc:   获取选中记录的唯一标识（人员编码）,并放入数组，用于删除记录
    * 作者：  ldq
    * 日期:  2008-3-17
    * version:
    */
function getDelId() {
    //获取表格总的行数
	var iRowsNum = mygridt_yh_s.getRowsNum();                   
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_yh_s.cells2(i, 0).getValue() == 1) {         
            //获取单元格数据
			var strId = mygridt_yh_s.cells2(i, 1).getValue();    
			delArray.push(strId);
		}
	}
	return delArray;
}
/** 
 * desc:   获取选中部门id，依据部门生成人员检查站下拉列表框
 * 作者：  ldq
 * 日期:  2008-3-17
 * version:
 */
function doChange(insrtOrUpdt){
		if (G_jgID == ""){
			fillListBox("tdPreson","PERSONCODE","280","select ryid,xm from t_sys_person","未选择","showEidtPerson();");
		}else{
			fillListBox("tdPreson","PERSONCODE","280","select ryid,xm from t_sys_person where SSJG='"+G_jgID+"'","未选择","showEidtPerson();");
		}
	
}
function showPage(){
	var returnValuestr = window.showModalDialog("../sm/ehl/user/userModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2", "", "dialogWidth:300px;dialogHeight:250px");
	if (returnValuestr == "insertOrUpdate") {
		onload();
	}
}

function editPwd(insrtOrUpdt){
	var dataArray = new Array();
	if ($("Opassword").value.Trim() == ""){
			alert("请输入原密码");
			return;
	}
	if ($("password").value.Trim()=="" || $("password").value.Trim().length < 6){
		alert("请输入六位以上非空格字符！");
		return;
	}
	var pass    = document.getElementById("password");
	var Rpass 	= document.getElementById("Rpassword");
	if(Rpass.value!=pass.value){
		alert("您输入的密码不一致！");
		Rpass="";
		pass="";
		return false;
	}
	if(!checkNormalStr(pass.value)){
		alert("请去除特殊字符输入！");
		return;
	}
	var ucode = $("USERCODE").value;
	var PWD = pass.value;
	dataArray.push(ucode);
	dataArray.push(PWD);
	var url = "common.usermanage.updateData.d";
	
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	
	var params = "";
	params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt ;
	params = params+"&oldPass="+$("Opassword").value;
	params = encodeURI(params);
	
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponsePwd});
}
function showResponsePwd(xmlHttp) {
	var text = xmlHttp.responseText;
	alert(text);
}
/** 
    * desc:  过滤(查询)数据.
    * 作者：  ldq
    * 日期:  2008-3-17
    * version:
    */
var mygridt_yh_s = null;
function Filter() {
	var Username    = document.getElementById("USERNAME");
	var Deptcode 	= document.getElementById("DEPTCODE_F");
	
	var url = "pcs.tree.setTree.d";
		url = encodeURI(url);
		var params = "deptId=370102010000";
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	
	
	
	
	var strUsername = Username.value;
	var strDeptcode = Deptcode.value
	if(!checkTextDataForNORMAL(strUsername)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	if(!checkTextDataForNORMAL(strDeptcode)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var strUrl = "common.usermanage.filter.d";
	if(strDeptcode == null||strDeptcode == ""){
		G_jgID="";
	}
	strUsername = strUsername.toLowerCase();//转换成小写
	strUrl = encodeURI(strUrl);
	var params = "USERNAME=" + strUsername + "&DEPTCODE=" + G_jgID ; 
	params = encodeURI(params);//进行编码转换
	mygridt_yh_s.clearAll();//把表格的内容全部清空
	mygridt_yh_s.loadXML(strUrl + "?" + params,pageLoaded);//重新加载参数
}
/** ---功能 ：编辑 --将数据放入编辑框 * 时间：2007-09-26*/
function doQuery(did) {
	if (did !== "") {
		var url = "common.usermanage.getDataById.d";
		url = encodeURI(url);
		var params = "did=" + did;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var usercode 	= document.getElementById("did");
	var USERNAME 	= document.getElementById("USERNAME");
	var DEPTCODE 	= document.getElementById("DEPTCODE");
	var PWD = document.getElementById("PWD");
	var REMARK 		= document.getElementById("REMARK");
	var userIP 		= document.getElementById("userIP");
	var APPIDObj = document.getElementById("APPID");
	var results		= xmldoc.getElementsByTagName("col");
	var rols 		= xmldoc.getElementsByTagName("rol");
	USERNAME.value 	= results[1].text;
	userIP.value 	= results[8].text;
	G_jgID = results[7].text;
	if (results[2].text != "") {
		DEPTCODE.value = results[2].text;
	}
	if (results[3].text != "") {	
		forEditPersonCode = results[3].text;
	}
	PWD.value = results[4].text;
	REMARK.value = results[5].text;
	if(typeof APPIDObj != "undefined"){
	   APPIDObj.value = results[6].text;
	}
	var roles = document.getElementsByName("role_id");
	for (var i = 0; i < roles.length; i++) {
		for (var k = 0; k < rols.length; k++) {
			if (roles[i].value == rols[k].text) {
				roles[i].checked = true;
				break;
			}
		}
	}
}
 //展示编辑数据，人员列表生成后为其赋值
 function showEidtPerson(){
    var PERSONCODE 	= document.getElementById("PERSONCODE");
    PERSONCODE.value = forEditPersonCode;
    
 }
/** ---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 时间：2007-09-26*/
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	var dataArray = new Array();
	
	var USERCODE = $("USERCODE").value;
	var USERNAME = $("USERNAME").value;
	var DEPTCODE ;
	var PERSONCODE  = $("PERSONCODE").value;
	var PWD = $("PWD").value;
	var strRePwd = "";
	var REPWD = document.getElementById("REPWD");
	var APPIDObj = document.getElementById("APPID");
	var userIP = $("userIP").value;	
	if(REPWD != null){
		strRePwd = REPWD.value;
	}
	var REMARK   = $("REMARK").value;
	
	if (USERNAME.Trim()== "") {
		alert("请输入用户名");
		document.getElementById("USERNAME").focus();
		return;
	}
	if(G_jgID.Trim()==""||G_jgID == "undefined"){
		alert("请选择所属单位！");
		return;
	}
	var REPWD_Desc = document.getElementById("REPWD_Desc");

	//if(PWD.length<6){
		//alert("密码太短，请重输入！【密码长度在6~16位，字母区分大小写】");
		//return;	
	//}
	if (PWD.Trim()!="" && PWD.Trim().length < 6){
		alert("密码太短，密码长度在6位以上，字母区分大小写！");
		document.getElementById("PWD").focus();
		return;
	}else{
		if(PWD != "" && REPWD_Desc.innerHTML !="" ){
			if(strRePwd == ""){
				alert("请再次输入密码");
				return;
			}
			if(PWD != strRePwd){
				alert("两次输入的密码不一致！");
				return;
			}
		}
	}	
	if(!checkNormalStr(USERNAME)){
		alert("请去除特殊字符输入！");
		return;
	}
	if(G_jgID!=""){
		DEPTCODE = G_jgID;
	}
	
	if(!checkNormalStr(PWD)){
		alert("请去除特殊字符输入！");
		return;
	}
	if(!checkNormalStr(REMARK)){
		alert("请去除特殊字符输入！");
		return;
	}
	USERNAME = USERNAME.toLowerCase();//转换成小写
	dataArray.push(USERCODE);
	dataArray.push(USERNAME);
	dataArray.push(DEPTCODE);
	dataArray.push(PERSONCODE);
	dataArray.push(PWD);
	dataArray.push(REMARK);
	dataArray.push(APPIDObj.value);
	dataArray.push(userIP);
	/*2008-03-24*/
	var roles = document.getElementsByName("role_id");
	var role = "";
	for (var i = 0; i < roles.length; i++) {
		if (roles[i].checked == true) {
			if (i == roles.length - 1) {
				role = role + roles[i].value;
			} else {
				role = role + roles[i].value + ":";
			}
		}
	}
	if(role==""){
		alert("请选择角色！");
		return;
	}
	/*2008-03-24*/
	var url = "common.usermanage.updateData.d";
	
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	
	var params = "";
	params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt + "&role=" + role;
	if(insrtOrUpdt == "1"){
		params = params+"&oldPass="+document.getElementById("PWD").value;
		params = encodeURI(params);
	}
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseWin});
}
function doFiniWin() {
}
function showResponseWin(xmlHttp) {
	var text = xmlHttp.responseText;
    if(text=="用户修改成功!"){
        alert(text);
        window.close();
    }else if(text=="用户添加成功!"){
        alert(text);
        window.close();
    }else{
 	    alert(text);
	    return;   
    }
}
/** 
    * desc:  获得数据表格中加亮显示的记录的id(布控编码),限制一条数据
    * author：zhaoyu
    * date:   2008-03-18
    * version:
    */
function getRFId() {
    //获取表格总的行数
	var iRowsNum = mygridt_yh_s.getRowsNum();          
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_yh_s.cells2(i, 0).getValue() == 1) { 
		    //获取单元格数据
			var strId = mygridt_yh_s.cells2(i, 1).getValue();
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
    * desc:  重置
    * author：LDQ
    * date:   2008-03-17
    * version:
    */
function reset() {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById("dataTable");
	var deptObj = document.getElementById("DEPTCODE");
	deptObj.value = "";
	var checkObj = document.getElementsByName("role_id");
	
	
	for(var i = 0; i < checkObj.length; i ++){
	    checkObj[i].checked = false;
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
			select[i].value = "";
		}
	}
}
/** 函数功能 ：刷新页面*/
function fresh() {
	var strUsername = document.getElementById("USERNAME");
	var strDeptcode = document.getElementById("DEPTCODE_F");
	strUsername.value = "";
	strDeptcode.value = "";
	var strUrl = "common.usermanage.filter.d";
	strUrl = encodeURI(strUrl);
	var params = "";    //传入的参数    
	params = encodeURI(params);
	mygridt_yh_s.clearAll();//把表格的内容全部清空
	mygridt_yh_s.loadXML(strUrl + "?" + params);//重新加载参数
}

//用户角色 ‘全选’ wangyali --2009-9-28
function checkAll(){
	var roles = document.getElementsByName("role_id");
	for (var i = 0; i < roles.length; i++) {
	    if(roles[i].checked == false){
	      roles[i].checked = true;
	    }		
	}
}
//取消选择
function unCheckAll(){
	var roles = document.getElementsByName("role_id");
	for (var i = 0; i < roles.length; i++) {
	    if(roles[i].checked == true){
	      roles[i].checked = false;
	    }		
	}
}
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行增加、修改、删除、操作
  */
	function loadToolbar(){
	   onLoadToolbar();
	}    
    var optstat = "0";
	function onButtonClick(itemId, itemValue) {//工具栏的按钮单击事件（按钮的id,按钮的值）
		var id = itemId;
		if (id == "0_new") {//触发新增事件，打开新增页面	
			var returnValuestr = window.showModalDialog("user.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&did=''", "", "dialogWidth:480px;dialogHeight:570px");
			if (returnValuestr == "insertOrUpdate") {
				Filter();
			}
		}
		if (id == "0_edit") {//触发编辑事件，打开编辑页面
			if (getRFId() == undefined) {
				return;
			} else {
				var returnValuestr = window.showModalDialog("user.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&did='" + getRFId() + "'", "", "dialogWidth:480px;dialogHeight:570px");
				if (returnValuestr == "insertOrUpdate") {
					Filter();
				}
			}
		}
		if (id == "0_info") {
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
		if(id == "0_excel"){
		    doExcel();
		}
	}
	function doExcel(){
	   var dataSql = "SELECT USERCODE,";
				dataSql += "USERNAME,";
				dataSql += "(select xm from t_sys_person where ryid = PERSONCODE) as PERSONCODE,";
				dataSql += "F_GET_FULLDEPT(DEPTCODE),";
				dataSql += "f_get_role(usercode)";
				dataSql += "  FROM T_SYS_USER";
		    var header = "用户编号,用户名,对应人员,所属机构,所有角色";
			saveFieldsAsExcel("\u4eba\u5458\u57fa\u672c\u4fe1\u606f", "user", header,dataSql);
	}
/**
 * 说明：判断密码强度 --------------------------------------->
 */
 //CharMode函数 测试某个字符是属于哪一类.  
	function CharMode(iN){  
		if (iN>=48 && iN <=57){ //数字  
			return 1;  
		}
		if (iN>=65 && iN <=90){ //大写字母  
			return 2; 
		} 
		if (iN>=97 && iN <=122){ //小写  
			return 4;  
		}else{
			return 8; //特殊字符 
		} 
	}  
	
	//bitTotal函数  计算出当前密码当中一共有多少种模式  
	function bitTotal(num){  
		var modes = 0;  
		for (i=0;i<4;i++){  
			if (num & 1) modes++;  
			num>>>=1;  
		}  
		return modes;  
	}  
	
	//checkStrong函数  返回密码的强度级别  
	function checkStrong(sPW){  
		if (sPW.length<=4)  
			return 0; //密码太短  
		Modes=0;  
		for (i=0;i<sPW.length;i++){  
			//测试每一个字符的类别并统计一共有多少种模式.  
			Modes|=CharMode(sPW.charCodeAt(i));  
		}  
		return bitTotal(Modes);  
	}  
	
	//pwStrength函数  当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色  
	function pwStrength(pwd){  
		O_color="#eeeeee";  
		L_color="#FF0000";  
		M_color="#FF9900";  
		H_color="#33CC00";  
		if (pwd==null||pwd==''){  
			Lcolor=Mcolor=Hcolor=O_color;  
		}else{  
			S_level=checkStrong(pwd);  
			switch(S_level){  
				case 0:  
				Lcolor=Mcolor=Hcolor=O_color;  
				case 1:  
				Lcolor=L_color;  
				Mcolor=Hcolor=O_color;  
				break;  
				case 2:  
				Lcolor=Mcolor=M_color;  
				Hcolor=O_color;  
				break;  
				default:  
				Lcolor=Mcolor=Hcolor=H_color;  
			}  
		}
		
		document.getElementById("strength_L").style.background=Lcolor;  
		document.getElementById("strength_M").style.background=Mcolor;  
		document.getElementById("strength_H").style.background=Hcolor;  
		return;  
	}  
 
 /**
 * 说明：判断密码强度 <---------------------------------------
 */
