	
	/**
	    * Copyright (c) 2007, EHL Technologies, Inc.
	    * All right reserved.
	    * 说明：实现对操作数据表进行 新增、编辑、过滤、删除、刷新操作	
	    * author：zhaoyu
	    * date:   2008-06-04
	    *
	    * 修改：2009-09-23 luchunqiang
	    * 修改说明：获取叶结点，而非仅仅是最后两位不是00的结点
	    * 原来SQL：select id,text from t_sys_func where substr(id,5,2)<>'00'
	    */
	
	function doOnChange(){
		var sysobj = document.getElementById("sysname");	
		var funcobj = document.getElementById("funcSelect");
		
		var sqlLeafNode = 'select t.id,t.text from ';
		sqlLeafNode += '(select id,text, count(id) over( partition by substr(id,0,4)) nodecount  from t_sys_func ) ';
		sqlLeafNode += ' t  where ( substr(t.id,5,2)<>\'00\' OR (  t.nodecount = 1 AND substr(t.id,3,4)<>\'0000\' ))';
		
		if(sysobj!=null&&sysobj!="undefined"&&funcobj!=null&&funcobj!="undefined"){
			if (document.activeElement == sysobj)
			{
				funcobj.style.display=""
				var strsys = sysobj.value;			
				fillListBox("tdfunc","funcname","180",sqlLeafNode + " and substr(id,1,2)='"+strsys.substring(0,2)+"' order by id","请选择","");
			}
		}	
		
		var selectsystemobj = document.getElementById("selectSystemname");	
		var selectfuncobj = document.getElementById("selectFuncname");
		if(selectsystemobj!=null&&selectsystemobj!="undefined"&&selectfuncobj!=null&&selectfuncobj!="undefined"){
			if (document.activeElement == selectsystemobj)
			{
				var strsystem = selectsystemobj.value;		
				if(strsystem!=null&&strsystem!=""){
					fillListBox("tdfuncname",sqlLeafNode + " and substr(id,1,2)='"+strsystem.substring(0,2)+"' order by id","请选择","");	
				}else{
					fillListBox("tdfuncname", sqlLeafNode ,"请选择","");
				}
				
			}
		}
	}

	
	function Filter() {
		var sysname    = document.getElementById("selectSystemname");
		var funcname 	= document.getElementById("selectFuncname");
		
		var strSysname = sysname.value;
		var strFuncname = funcname.value;
		if(!checkTextDataForNORMAL(strSysname) || !checkTextDataForNORMAL(strFuncname)){
		    alert("查询条件含有特殊字符，请检查！");
		    return;
		}
		var strUrl = "common.operationmanage.filter.d";
		
		strUrl = encodeURI(strUrl);
		var params = "sysname=" + strSysname + "&funcname=" + strFuncname ;    
		params = encodeURI(params);
		mygridt_operation_s.clearAll();                                       
		mygridt_operation_s.loadXML(strUrl + "?" + params,pageLoaded);                     
	}
	
	function doQuery(did,insrtOrUpdt) {
	    var optname = document.getElementById("optname");
	        optname.focus();
	    
	    if(insrtOrUpdt == 0){
	      var hid = document.getElementById("hid");
	          
	          hid.style.display = "none";
	    }
	    
	    if(insrtOrUpdt == 1){
	    	var codeobj = document.getElementById("code");
	    	codeobj.disabled=true;
	    	
	     	var funcobj = document.getElementById("funcSelect");
	          
	         funcobj.style.display = "";
	    }
	    
	    
		if (did !== "") {
			
			var url = "common.operationmanage.getDataById.d";
			url = encodeURI(url);
			var params = "did=" + did;
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
		}
	}
	function showGetXMLResponse(xmlHttp) {
		var xmldoc   = xmlHttp.responseXML;
		var optCode  	 = document.getElementById("code");
		var optName     = document.getElementById("optname");
		var btnCode  	 = document.getElementById("btncode");
		var remark     = document.getElementById("remark");
		var sysname     = document.getElementById("sysname");
		var funcname     = document.getElementById("funcname");
		
		var results   = xmldoc.getElementsByTagName("col");
		optCode.value  = results[0].text;
		optName.value   = results[1].text;
		btnCode.value  = results[2].text;
		remark.value  = results[3].text;
		sysname.value  = results[0].text.substring(0,2)+"0000";
		funcname.value  = results[0].text.substring(0,6);
		sysname.disabled=true;
		funcname.disabled=true;
//		sysname.onbeforeactivate="return false"
//		sysname.onfocus="this.blur()"; 
//		sysname.onmouseover="this.setCapture()" 
//		sysname.onmouseout="this.releaseCapture()"


	}
	function doGetXMLFini() {
	}
	/** 
	    * desc:  增加或编辑. 绑定前端数据,通过ajax传到后端进行相应的业务处理 
	    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作
	    * return:
	    * author：zhaoyu
	    * date:   2008-06-04
	    * version:
	    */
	function addOrUpdate(insrtOrUpdt) {
		window.returnValue = "insertOrUpdate";	
		var optcode = document.getElementById("code");
		var optname = document.getElementById("optname");
		var btncode = document.getElementById("btncode");
		var remark = document.getElementById("remark");
		var sysname  = document.getElementById("sysname");
		var funcname  = document.getElementById("funcname");
		
		var dataArray = new Array();
		if (optname.value.Trim() == "") {
			alert("请输入操作名称！");
			return;
		}
		if (btncode.value.Trim() == "") {
			alert("请输入实体编号！");
			return;
		}
		if(!checkNormalStr(optname.value)){
			alert("请去除特殊字符输入");
			return;
		}
		if(!checkNormalStr(remark.value)){
			alert("请去除特殊字符输入");
			return;
		}
		
		if (sysname.value == "") {
			alert("请选择系统名称！");
			return;
		}
		
		if (funcname.value == "") {
			alert("请选择功能名称！");
			return;
		}
		
		var url = "common.operationmanage.updateData.d?did=" + optcode.value + "&insertOrUpdate=" + insrtOrUpdt+"&optname=" + optname.value;
		url += "&btncode=" + btncode.value;
		url += "&remark=" + remark.value;
		url += "&sysname=" + sysname.value;
		url += "&funcname=" + funcname.value;
		url = encodeURI(url);
		params = "";
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseSave, onSuccess:doFiniSave});
	}
	function showResponseSave(xmlHttp) {
		alert(xmlHttp.responseText);
		Filter();
	}
	function doFiniSave() {
	 //alert("u6570u636eu5f55u5165u6210u529f");
	}
	
	/** 
	    * desc:   删除 .通过ajax将获取到的数据的id值（角色编码）传入后台
	    * author：zhaoyu
	    * date:   2008-06-04
	    * version:
	    */
	function doDelete() {	  
		if (getDelId() != "") {
			if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) {
				var url = "common.operationmanage.delete.d?idArray=" + getDelId();
				url = encodeURI(url);
				var params = "" ;
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
	    * author：zhaoyu
	    * date:   2008-06-04
	    * version:
	    */
	function getDelId() {	
	    //获取表格总的行数
		var iRowsNum = mygridt_operation_s.getRowsNum();                      	
		var delArray = new Array();
		//循环遍历数据表的行
		for (var i = 0; i < iRowsNum; i++) {                        
            //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			if (mygridt_operation_s.cells2(i, 0).getValue() == 1) {           
                //获取单元格数据
				var strId = mygridt_operation_s.cells2(i, 1).getValue();      
				delArray.push(strId);
			}
		}
		return delArray;
	}
	function showResponseDelete(xmlHttp) {
		alert(xmlHttp.responseText);
		Filter();
	}
	function doFiniDelete() {
		//alert("u6b64u8bb0u5f55u88abu6210u529fu5220u9664");
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
	    * desc:  重新加载数据
	    * author：zhaoyu
	    * date:   2008-06-04
	    * version:
	    */
	function freshs() {
		var sysnameObj = document.getElementById("selectSystemname");
		var funObj = document.getElementById("selectFuncname");
		sysnameObj.value = "";
		funObj.value  = "";
		var url = "common.operationmanage.loadFilter.d";
		url = encodeURI(url);
		var params = "";
		//把表格的内容全部清空
		mygridt_operation_s.clearAll();
		mygridt_operation_s.loadXML(url + "?" + params,pageLoaded);
	}
	
	/** 
	    * desc:  获得数据表格中选中的记录的id(即操作编码)
	    * author：zhaoyu
	    * date:   2008-06-04
	    * version:
	    */	
	function getPId() {
	    //获取表格总的行数
		var iRowsNum = mygridt_operation_s.getRowsNum();         
		var array = new Array();
		for (var i = 0; i < iRowsNum; i++) {
		    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			if (mygridt_operation_s.cells2(i, 0).getValue() == 1) { 
	           //获取单元格数据
			   var strId = mygridt_operation_s.cells2(i, 1).getValue();
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
	var sysID = document.getElementById("sysname");
	if(sysid==""){
	}else{
		//下拉列表中的值与查询的值相同则选中
		for (var i = 0; i < sysID.length; i++) {	        
		    if (sysID.options[i].value == sysid) {				
			   sysID.options[i].selected = true;
			}
	     }
	     doOnChange();
     }
}	
/** 
    * desc:  加载工具栏组件 ，可对当前页面进行保存、增加、编辑、删除、过滤、刷新操作
    * author：ldq
    * date:   2008-03-19
    * version:
    */
    function disbutton(){
	        aToolBar.disableItem("0_excel");
			aToolBar.disableItem("0_chart");
			aToolBar.disableItem("0_info");
	    }

    //工具栏的按钮单击事件（按钮的id,按钮的值）
	function onButtonClick(itemId, itemValue) {                   
		var id = itemId;
		if (id == "0_new") {	//触发新增事件，打开新增页面	  
			var sysID = document.getElementById("selectSystemname");
	        var strSysID = sysID.value;
			var returnValuestr = window.showModalDialog("operation.jsp?insrtOrUpdt=0&did=''&sysCode='" +strSysID + "'","", "dialogWidth:330px;dialogHeight:300px");
			if (returnValuestr == "insertOrUpdate") {			
				Filter();
			}
		}
		if (id == "0_edit") {//触发编辑事件，打开编辑页面	  
			if (getPId() == undefined) {
			} else {
				var returnValuestr = window.showModalDialog("operation.jsp?insrtOrUpdt=1&did='" +getPId() + "'", "", "dialogWidth:330px;dialogHeight:320px");
				if (returnValuestr == "insertOrUpdate") {
						Filter();
				}
			}
		}
		if (id == "0_delete") {
			doDelete(); //触发删除事件
		}
		if (id == "0_filter") {
			Filter(); //触发过滤事件
		}
		if (id == "0_fresh") {
			freshs();//触发刷新事件
		}
	}


