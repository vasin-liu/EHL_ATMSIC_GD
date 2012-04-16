   /**
	* ------------------------------------------------ 
	* Copyright (c) 2007, EHL Technologies, Inc.
	* All right reserved.
	* 说明：预案管理所需js
	* 作者：lidq
	* 日期: 2009-9-5
	* ------------------------------------------------ 
	*/
    
   /** 
    * desc: 预案查询
    * 作者：lidq
    * 日期: 2009-9-5
    * version: 1.0
    */
function doFilter(param){
	if(param != "init"){
		var strYAMC = document.getElementById("YAMC");
		strYAMC = strYAMC.value;
		var strYYCJ = document.getElementById("YYCJ");
		strYYCJ = strYYCJ.value;
		var params = "strYAMC=" + strYAMC + "&strYYCJ=" + strYYCJ;	   //传入的参数 
	}else{
		var params = "";
	}
	
	var strUrl = "prmplan.prmplanManage.getPrmplanList.d";
	var url = encodeURI(strUrl);
	params = encodeURI(params);   //进行编码转换
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseRoadSegList});
}

   /** 
    * desc: 生成数据表格
    * 作者：lidq
    * 日期: 2009-9-5
    * version: 1.0
    */
function showResponseRoadSegList(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
   	var rows = xmlDoc.getElementsByTagName("row");
 	var tdObj = document.getElementById("preplanGrid");
 	
 	//生成表格
   	var strTab = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#FFFFFF\">";
    for(var i = 0 ; i <rows.length; i++){
	    var results = rows[i].childNodes;
	    strTab +=  "<tr class='tr_2'>";
	   	strTab +=    "<td width='%'  align='center' style='display:none'>" + results[0].text + "</td>";
	    strTab +=    "<td width='%' align='center' style='display:none'>" + results[1].text + "</td>";
	    strTab +=    "<td width='18%' align='center' >" + results[2].text + "</td>";
	    strTab +=    "<td width='15%' align='center' >" + results[3].text + "</td>";
	    strTab +=    "<td width='10%' align='center' >" + results[4].text + "</td>";
	    strTab +=    "<td width='15%' align='center' >" + results[5].text + "</td>";
	    strTab +=    "<td width='15%' align='center' >" + results[6].text + "</td>";
	    strTab +=    "<td width='15%' align='center' >" + results[7].text + "</td>";    	
	   	strTab +=    "<td width='12%' align='center'>";  	
	    strTab +=      "<a href=\"javascript:showMesPage('"+ results[0].text + "');\"><img style='border:0' src='../../image/button/btnck.gif' alt='查看预案'/></a>";	
	    strTab +=      "<a href=\"javascript:showEditPage('"+ results[0].text + "');\"><img style='border:0' src='../../image/button/btnedit1.gif' alt='编辑预案'/></a>";	
	   	strTab +=      "<a href=\"javascript:doDelete('"+ results[0].text + "');\"><img style='border:0' src='../../image/button/btndelete1.gif' alt='删除预案'/></a>"	;   	
    	strTab +=    "</td>";  		
     	strTab +=  "</tr> "  ;    
    }        	            	            	
    strTab+= "</table>";
    tdObj.innerHTML=strTab;
    Popup.prototype.hideTips();
}


   /**
    * desc: 将数据放入编辑框
    * 作者：lidq
    * 日期: 2009-9-5
    */
function queryMes(strID,insrtOrUpdt) {
	//如果是编辑到数据库查询信息，插入则初始化页面信息
	if (strID != "") {
		var url = "prmplan.prmplanManage.getDataById.d";
		url = encodeURI(url);
		var params = "SXH="+strID;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}else{
		var lsdwmc = $("DEPTCODE2");//创建单位
		lsdwmc.value = deptname;
		var lsdwid = $("cjdw");//落实单位id
		lsdwid.value = deptid;
		var cjrq = $("cjrq");
		cjrq.value = creatTime;
		var cjry = $("cjry");
		cjry.value = pname;
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmldoc   = xmlHttp.responseXML;
	var results = xmldoc.getElementsByTagName("col");
	if(results!=null){
		var SXH = $("SXH");
		var yamc = $("yamc");
		var YYCJ = $("YYCJ");
		var zyms = $("zyms");
		var lsdw = $("DEPTCODE1");//创建单位
		var lssj = $("lssj");
		var txsj = $("txsj");
		var qdsj = $("qdsj");
		var cjdw = $("DEPTCODE2");//落实单位
		var cjry = $("cjry");
		var cjrq = $("cjrq");
		var xgrq = $("xgrq");
		var shyj = $("shyj");
	//	var filelist = $("filelist");
	
		SXH.value = results[0].text;
		yamc.value = results[1].text;
		YYCJ.value = results[2].text;
		zyms.value = results[3].text;
		lsdw.value = results[4].text;
		lssj.value = results[5].text;
		txsj.value = results[6].text;
		qdsj.value = results[7].text;
		cjdw.value = results[8].text;
		cjry.value = results[9].text;
		cjrq.value = results[10].text;
		xgrq.value = results[11].text;
		shyj.value = results[12].text;
		
		$("YYCJ_SEC").value = results[2].text;
		$("cjdw").value = results[13].text;//创建单位id
		$("lsdw").value = results[14].text;//落实单位id
		
//		G_jgID = results[13].text;
	//	filelist = results[5].text;
	}
}

/** 
  * desc:   删除数据 
  * 作者：lidq
  * 日期: 2009-9-5
  * version:
  */
function doDelete(strID) {
	if (strID!= "") {                                       
		if (confirm("您确定删除此项记录吗？")) {                                      
			var strUrl = "prmplan.prmplanManage.delete.d"; 
			strUrl = encodeURI(strUrl);
			var params = "SXH=" + strID;
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
		} else {  
			return;
		}
	} else {                 
		alert("记录不能被删除！");
	}
}
function showResponseDelete(xmlHttp) {
	if(xmlHttp.responseText == "数据删除成功！"){
		doFilter('init');
	}
	alert(xmlHttp.responseText);	
}

   /** 
    * desc:  重置
    * 作者：lidq
    * 日期: 2009-9-5
    * version:
    */
function doReset() {
	var SXH = $("SXH");
	var yamc = $("yamc");
	var YYCJ = $("YYCJ");
	var zyms = $("zyms");
	var lsdw = $("lsdw");
	var lssj = $("lssj");
	var txsj = $("txsj");
	var qdsj = $("qdsj");
	var cjdw = $("cjdw");
	var cjry = $("cjry");
	var cjrq = $("cjrq");
	var xgrq = $("xgrq");
	var shyj = $("shyj");
	var filelist = $("filelist");
	
	SXH.value = "";
	yamc.value = "";
	YYCJ.value = "";
	zyms.value = "";
	lsdw.value = "";
	lssj.value = "";
	txsj.value = "";
	qdsj.value = "";
	cjdw.value = "";
	cjry.value = "";
	cjrq.value = "";
	xgrq.value = "";
	shyj.value = "";
	var flist = filelist.getElementsByTagName("input");
	if(flist != null && flist.length > 0){
		for(var i=0; i<flist.length; i++) {
			flist[i].value = "";
		}
	}
}

/**
 * 添加文件节点
 */
function addFileItem(){
	var filelist = $("filelist");
	var str = "";//filelist.innerHTML;
	var objId = Math.random();
	
	var useSpan = document.createElement("span");
	useSpan.id = objId;
	
//	str += "<span id='"+objId+"'>";
	str += "<input type=\"file\" name=\"fname\" id=\"fname\" width=\"80%\">";
//	str += "<input type=\"button\" name=\"fname\" id=\"fname\" onclick=\"removeObj('"+ objId + "');\" value=\"删除\">";
	str += "<a href=\"javascript:removeObj('"+ objId + "','0');\">";
	str += "<img style='border:0' src='../../image/button/btndelete1.gif' alt='删除文件'/></a>";
//	str += "<span><br>";
	useSpan.innerHTML = str;
	filelist.appendChild(useSpan);
}

/** 
    * desc:   关闭子页面    * 作者：lidq
    * 日期: 2009-9-5
    * version:
    */
function doClose(){
	window.returnValue ="fresh";
 	window.close();
}
   /** 
    * desc:   打开信息新增页面
    * 作者：lidq
    * 日期: 2009-9-5
    * version:
    */
function showInsertPage(){
	var top=(screen.availHeight-610)/2;		
    var left=(screen.availWidth-440)/2;
	window.open("Prmplan.jsp?insrtOrUpdt=0", "","height=610px,width=440px,top="+top+",left="+left);
}
/** 
    * desc:   打开编辑页面
    * 作者：lidq
    * 日期: 2009-9-5
    * version:
    */
function showEditPage(strID){
	var top=(screen.availHeight-610)/2;		
    var left=(screen.availWidth-440)/2;
	window.open("Prmplan.jsp?insrtOrUpdt=1&strID="+strID, "","height=610px,width=440px,top="+top+",left="+left);
}

/** 
    * desc: 打开明细页面
    * 作者：lidq
    * 日期: 2009-9-5
    * version:
    */
function showMesPage(strID){
	var top=(screen.availHeight-550)/2;		
    var left=(screen.availWidth-440)/2;
	window.open("showDetail.jsp?insrtOrUpdt=1&strID="+strID, "","height=550px,width=440px,top="+top+",left="+left);
}

/**
 * 
 */
function checkType(){
//	var SXH = $("SXH");
	var yamc = $("yamc");
	var YYCJ = $("YYCJ");
	var zyms = $("zyms");
	var lsdw = $("lsdw");
	var lssj = $("lssj");
	var txsj = $("txsj");
	var qdsj = $("qdsj");
	var cjdw = $("cjdw");
	var cjry = $("cjry");
	var cjrq = $("cjrq");
	var xgrq = $("xgrq");
	var shyj = $("shyj");
	
	if(document.userform.yamc.value == ""){
		alert("请输入预案名称！");
		document.userform.yamc.focus();
		return false;
	}
	if(insertOrUpdate==0 && document.userform.cjrq.value < creatTime){
		alert("创建时间不能为空且不小于当前时间！");
		document.userform.cjrq.focus();
		return false;
	}
	
//	if(document.userform.lssj.value != "" && (document.userform.lssj.value < document.userform.cjrq.value)){
//		alert("落实时间不能小于创建时间！");
//		document.userform.lssj.focus();
//		return false;
//	}
//	
//	if(document.userform.txsj.value != "" && (document.userform.txsj.value > document.userform.lssj.value)){
//		alert("提醒时间不能在落实时间之后！");
//		document.userform.lssj.focus();
//		return false;
//	}
	
//	YYCJ.value = "";
//	zyms.value = "";
//	lsdw.value = "";
//	lssj.value = "";
//	txsj.value = "";
//	qdsj.value = "";
//	cjdw.value = "";
//	cjry.value = "";
//	cjrq.value = "";
//	xgrq.value = "";
//	shyj.value = "";
	
//	var filelist = $("filelist");
//	var flist = filelist.getElementsByTagName("input");
//	if(flist != null && flist.length > 0){
//		var fileType = "";
//		for(var i=0; i<flist.length; i++) {
//			fileType = flist[i].value.split(".");
//			fileType = fileType[fileType.length-1];
//			if(fileType != "doc"){
//				alert("文件类型错误，请重试！");
//				break;
//				return false;
//			}
//		}
//	}
  	
	return true;
}

function doOnChange(){
	if($("YYCJ_SEC").value != undefined){
		$("YYCJ").value = $("YYCJ_SEC").value;
//		alert($("YYCJ").value)
	}
}

/**
 * 编辑时删除文件
 * objID:生成的html对象id;type:(0,未上传文件),(1,已上传文件);
 */
function removeObj(objID,type){
	var obj  = document.getElementById(objID);
	if(obj != null){
//		obj.style.display = "none";
//		obj.innerHTML = "";
   		obj.parentNode.removeChild(obj);
	}
	var brObj = document.getElementById(objID+"br");
	if(brObj != null){
   		brObj.parentNode.removeChild(brObj);
	}
	var delFile = "";
	if(type=='1'){
		delFile = $("delFile").value;
		if(delFile == ""){
			delFile = objID;
		}else{
			delFile += ("," + objID);
		}
		$("delFile").value = delFile;
	}
}