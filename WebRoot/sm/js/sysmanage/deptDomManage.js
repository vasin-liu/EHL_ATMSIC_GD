  /**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：警用辖区权限分配
    * 作者：  wangyali
    * 日期:  2009-06-10
    */

    //查询
	 function doQuery(strJgid){
	    var strDepartmentName  = document.getElementById("tiDepartmentName").value;
	    var strDepartmentID="";
	    if(G_jgID == null || typeof G_jgID =="undefined"){
	    	G_jgID ="";
	    	strDepartmentID=strJgid;
	    }else{
	    	strDepartmentID=G_jgID;
	    }	    
		var strUrl = "common.deptdommanage.query.d";
		var params = "strDepartmentID=" + strDepartmentID;
		strUrl = encodeURI(strUrl);
		params = encodeURI(params); //进行编码转换;
		mygridt_deptdom_s .clearAll();//把表格的内容全部清空
		mygridt_deptdom_s .loadXML(strUrl + "?" + params,pageLoaded); 
	    
	 } 

	 //新增、修改页面 保存数据tiXqbh
 	 function doSave(strJgid,strFlag){
	    var strDepartmentName  = document.getElementById("tiDepartmentName");	
	    var xqbhObj  = document.getElementsByName("xqbh");
        var xqArray = new Array();
        var xqmcArray = new Array();
        var strDepartmentID="";
  	    if(G_jgID == null || typeof G_jgID =="undefined"){
	    	G_jgID ="";
	    }
	    if(strFlag=="1"){
	    	strDepartmentID=strJgid;
	    }else{
	    	strDepartmentID=G_jgID;
	    }
		if(strFlag=="0"&&strDepartmentName.value ==""){
			alert("请选择机构名称！");
		   	return;
		}      
		for (var i = 0; i<xqbhObj.length; i++){
			 if(xqbhObj[i].checked == true){
			     var xqObj=xqbhObj[i].value;
			     var strArr=xqObj.split("-");
			     xqArray.push(strArr[0]);
			     xqmcArray.push(strArr[1]);
			 }
		}
		if(xqArray.length <= 0){
			alert("请选择辖区名称！");
			return;
		}	
	    var params = "strDepartmentID="+strDepartmentID+"&strXqbhArr="+xqArray +"&strXqmcArr="+xqmcArray+"&strFlag="+strFlag;
	    var strUrl = "common.deptdommanage.save.d";
	    strUrl = encodeURI(strUrl);
	    params = encodeURI(params);
	    new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseSave});
	}
	
	function showResponseSave(xmlHttp){
	    alert(xmlHttp.responseText);
	}  
	 //获取一条记录信息  
	 function getDeptdomById(strDeptID){
		 if(strDeptID == ""){
			return;
		 }else{
	        var params = "strDepartmentID="+strDeptID;
		    var strUrl = "common.deptdommanage.getDeptdomById.d";
			strUrl = encodeURI(strUrl);
			params = encodeURI(params); 
		    new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showDeptdomById});   
		 } 
	 } 
	 function showDeptdomById(xmlHttp) {
		var xmldoc   = xmlHttp.responseXML;
	    var rows = xmldoc.getElementsByTagName("row");
	    var strDepartmentName  = document.getElementById("tiDepartmentName");
	    var xqbhObj  = document.getElementsByName("xqbh");
        var res = rows[0].childNodes;
		strDepartmentName.value=res[1].text;				
		for(var i = 0;i<xqbhObj.length; i++){
		   	for (var j = 0; j < rows.length; j++){
     	      var results = rows[j].childNodes;
     	      var xqObj=xqbhObj[i].value;
		      var strArr=xqObj.split("-");
		      if(strArr[0] == results[2].text){
		         xqbhObj[i].checked = true;
		      }
		    }
	     } 
	 }
	 //删除      
	 function doDelete(){
		 var strDeptID = getDeptIDArr();
		 var strXqbh = getXqbhArr();
		 if(strDeptID == ""||strXqbh==""){
			return;
		 }else{
		    if(confirm("确定删除该数据？")){
		        var params = "strDepartmentIDArr="+strDeptID+"&strXqbhArr="+strXqbh;
			    var strUrl = "common.deptdommanage.delete.d";
					strUrl = encodeURI(strUrl);
					params = encodeURI(params); 
			    new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});   
	        }
		 } 
	 } 
	 function showResponseDelete(xmlHttp) {
		 alert(xmlHttp.responseText);
		 fresh();
	 }
	 //获取选中的一条或多记录信息的 机构编码组
	function getDeptIDArr(){
	    //获取表格总的行数
		var iRowsNum = mygridt_deptdom_s.getRowsNum();                    
		var deptArray = new Array();
		for (var i = 0; i < iRowsNum; i++) {
		    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态	
			if (mygridt_deptdom_s.cells2(i, 0).getValue() == 1) {         
	            //获取单元格数据	
				var strId = mygridt_deptdom_s.cells2(i,1).getValue();    
				deptArray.push(strId);
			}
	     }
		return deptArray;
    }
	 //获取选中的一条或多记录信息的 辖区编号组
	function getXqbhArr(){
	    //获取表格总的行数
		var iRowsNum = mygridt_deptdom_s.getRowsNum();                    
		var xqbhArray = new Array();
		for (var i = 0; i < iRowsNum; i++) {
		    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态	
			if (mygridt_deptdom_s.cells2(i, 0).getValue() == 1) {         
	            //获取单元格数据	
				var strId = mygridt_deptdom_s.cells2(i,3).getValue();    
				xqbhArray.push(strId);
			}
	     }
		return xqbhArray;
    }

    //获取选中的一条记录信息  机构编码
	 function getDeptID(){
		var iRowsNum = mygridt_deptdom_s.getRowsNum();          
		var array = new Array();
		for (var i = 0; i < iRowsNum; i++) {
		    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			if (mygridt_deptdom_s.cells2(i, 0).getValue() == 1) {
			    //获取单元格数据
				var strId = mygridt_deptdom_s.cells2(i, 1).getValue();
				array.push(strId);				
			}
		}
		if (array.length >= 2 || array.length < 1) {
			alert("请选择一条记录");
			return ;
		    }
		return array[0];
    }
	 /** 函数功能 ：刷新页面*/
	function fresh() {
		var strUrl = "common.deptdommanage.query.d";
		var params = "";//传入的参数  
		strUrl = encodeURI(strUrl);
		params = encodeURI(params); 
		mygridt_deptdom_s.clearAll();//把表格的内容全部清空
		mygridt_deptdom_s.loadXML(strUrl + "?" + params, pageLoaded); 
	} 
    // 重置
	function doReset() {  
	    var xqbhObj  = document.getElementsByName("xqbh");
		for(var i = 0; i < xqbhObj.length; i++){
		    xqbhObj[i].checked = false;
		}
	}	
	// 关闭子页面
	function doClose(){
		window.returnValue ="fresh";
	 	window.close();
	}   
	/**
	  *功能 ： 点击工具条触发事件
	  */
	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
			var returnValue = window.showModalDialog("deptDomEdit.jsp?strFlag=0", "", "dialogWidth:420px;dialogHeight:450px");
			if(returnValue == "fresh")
			{
			   fresh();
			}
 		}
		if (id == "0_edit") {
		   var strJgid = getDeptID();
		    if (strJgid == undefined) {
			    return;
			} else{
			   var returnValue = window.showModalDialog("deptDomEdit.jsp?strFlag=1&strJgid=" + strJgid , "", "dialogWidth:420px;dialogHeight:450px");
			   if(returnValue == "fresh"){
				  fresh();
			   }			
			}

		}
		if (id == "0_delete") {
			doDelete();
		}
		if (id == "0_filter") {
			doQuery();
		}
		if (id == "0_fresh") {
			fresh();
		}
	}