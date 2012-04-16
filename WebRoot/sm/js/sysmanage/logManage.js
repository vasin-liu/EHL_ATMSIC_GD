
/**
    * Copyright (c) 2007, EHL Technologies, Inc.
    * All right reserved.
    * 说明：实现对员数据表进行 过滤、刷新操作
    * 作者：  ldq
    * 日期:  2008-03-25
    */
/** ---功能 ：编辑 --将数据放入编辑框 * 时间：2007-09-26*/
function doQuery(did) {
	if (did !== "") {
		var url = "common.log.getDataById.d";
		url = encodeURI(url);
		var params = "did=" + did;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmldoc 		= xmlHttp.responseXML;	
	var pname 		= document.getElementById("pname");
	var etime 		= document.getElementById("etime");
	var qtime 		= document.getElementById("qtime");
	var ipaddress 	= document.getElementById("ipaddress");
	var event 		= document.getElementById("event");	
	var results 	= xmldoc.getElementsByTagName("col");
	
	pname.value 	= results[1].text;
	etime.value 	= results[2].text;
	qtime.value 	= results[3].text;
	ipaddress.value = results[4].text;
	event.innerHTML = Replace(results[5].text);
}
function doGetXMLFini() {
}

/** 
    * desc:  字符串替换
    * 作者：  ldq
    * 日期:  2008-03-25
    * version:
    */
function Replace(ss){
   var r, re;                    	// 声明变量。
   re = /。/g;                   	// 创建正则表达式模式。
   r = ss.replace(re, "<br>");  	// 用 "<br>" 替换 "。"。
   return(r);                   	// 返回替换后的字符串。
}

/** 
    * desc:  获得数据表格中选中的记录的id（日志编码）
    * 作者：  ldq
    * 日期:  2008-03-25
    * version:
    */
function getPId() {
    //获取表格总的行数
	var iRowsNum = mygridt_rz_s.getRowsNum();          
	var array = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_rz_s.cells2(i, 0).getValue() == 1) { 
		   //获取单元格数据
			var strId = mygridt_rz_s.cells2(i, 1).getValue();
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
    * desc:  过滤
    * 作者：  ldq
    * 日期:  2008-03-25
    * version:
    */
var mygridt_rz_s = null;
function Filter() {
	var strIp  = document.getElementById("ip").value;      
	var strPname  = document.getElementById("pname").value;	    //得到jsp页面pname的值赋给变量strPname 
	var strEtime  = document.getElementById("etime").value;      
	var strEtime2 = document.getElementById("etime2").value;    //得到jsp页面etime的值赋给变量strEtime
	var strQtime  = document.getElementById("qtime").value;    //得到jsp页面qtime的值赋给变量strQtime
	var strQtime2 = document.getElementById("qtime2").value;
	if(!checkTextDataForNORMAL(strIp) || !checkTextDataForNORMAL(strPname)|| !checkTextDataForNORMAL(strEtime)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	if(strEtime!=""||strEtime2!=""){
		if(strEtime.Trim().length <1 ||strEtime2.Trim().length<1){
	       alert("请选择完整的登录时间段! ");
	       return;
	    }
	    if(strEtime > strEtime2){
	       alert("选择的时间段有误，请重新选择! ");
	       return;
	    }
	}
	if(strQtime!=""||strQtime2!=""){
	   if(strQtime.Trim().length <1 ||strQtime2.Trim().length<1){
	       alert("请选择完整的退出时间段! ");
	       return;
	   }
	   if(strQtime > strQtime2){
	       alert("选择的时间段有误，请重新选择! ");
	       return;
	   }
	 }
	if(!checkTextDataForNORMAL(strEtime2) || !checkTextDataForNORMAL(strQtime)|| !checkTextDataForNORMAL(strQtime2)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var strUrl    = "common.log.filter.d";
	strUrl = encodeURI(strUrl);
	var params = "ip=" + strIp + "&&pname=" + strPname + "&&etime=" + strEtime + "&&etime2=" + strEtime2 
						  +"&&qtime=" + strQtime+"&&qtime2=" + strQtime2;                         //传入的参数    
	params = encodeURI(params); //进行编码转换
	mygridt_rz_s.clearAll(); //把表格的内容全部清空
	mygridt_rz_s.loadXML(strUrl + "?" + params,pageLoaded); //重新加载参数
}
/** 函数功能 ：刷新页面*/
function fresh() {
	var strLcode  = document.getElementById("ip");      //得到jsp页面lcode的值赋给变量strPcode
	var strPname  = document.getElementById("pname");	    //得到jsp页面pname的值赋给变量strPname 
	var strEtime  = document.getElementById("etime");      
	var strEtime2 = document.getElementById("etime2");    //得到jsp页面etime的值赋给变量strEtime
	var strQtime  = document.getElementById("qtime");    //得到jsp页面qtime的值赋给变量strQtime
	var strQtime2 = document.getElementById("qtime2");
	strLcode.value="";
	strPname.value="";
	strEtime.value="";
	strEtime2.value="";
	strQtime.value="";
	strQtime2.value="";
	var strUrl    = "common.log.filter.d";
	strUrl = encodeURI(strUrl);
	var params = "";    
	params = encodeURI(params); //进行编码转换
	mygridt_rz_s.clearAll(); //把表格的内容全部清空
	mygridt_rz_s.loadXML(strUrl + "?" + params,pageLoaded);//重新加载参数
}

/**
  *名称：工具栏组件
  *功能 ：可对当前文件进行查询操作查询、明细、刷新、转存Excel操作
  */ 
	var optstat = "0";
	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_filter") {
			Filter();//触发过滤事件
		}
		if (id == "0_info") {//触发明细事件，打开明细页面
			if (getPId() == undefined) {
			} else {
				window.showModalDialog("log.jsp?did='" + getPId() + "'", "", "dialogWidth:350px;dialogHeight:500px");
			}
		}
		if (id == "0_fresh") {
			fresh();//触发刷新事件
		}
		if (id == "0_excel") {
			saveAsExcel("日志信息表", "log", mygridt_rz_s);//触发转存Excel事件
		}
	}


