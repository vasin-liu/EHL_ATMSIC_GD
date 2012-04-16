/** 
    * desc:初始化管理页面
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function doOnLoad(){
   document.getElementById("newhref").disabled  = false;
   document.getElementById("newroadhref").disabled  = false;

	var jgbh = $("jgbh").value;
	var sql = "select tod.BH,tod.DLBH,tod.DLMC,(select jgmc from t_sys_department tsd where tsd.jgid=tod.JGID) from T_OA_DICT_ROAD tod ";
	sql += " where tod.jgid like '"+jgbh+"%' order by tod.JGID";
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' onmouseover='changeto()' onmouseout='changeback()' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='12%' class='td_r_b td_font'>所属机构</td>";
	    str += "<td width='12%' class='td_r_b td_font'>道路编号</td>";
		str += "<td width='13%' class='td_r_b td_font'>道路名称</td>";
		str += "<td width='5%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			if(results[3].text=='null') {
			   results[3].text = '&nbsp;'
			}
			if(results[1].text=='null') {
			   results[1].text = '&nbsp;'
			}
			if(results[2].text=='null') {
			   results[2].text = '&nbsp;'
			}
			str += "<tr align='center'>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+results[3].text+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+results[1].text+"</td>";
			str += "<td width='13%' class='td_r_b td_font' align=\'center\'>"+results[2].text+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			str += "</tr>";			
	        }
	      }
		str +="</table>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}

/** 
    * desc:新增或查询的跳转
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("roadModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&bh=''", "", "dialogWidth:480px;dialogHeight:250px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "new_road") {
		var returnValuestr = window.showModalDialog("roadAdd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&bh=''", "", "dialogWidth:480px;dialogHeight:250px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("roadModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&bh='" + itemValue + "'", "", "dialogWidth:480px;dialogHeight:250px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("roadModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&bh='" + itemValue + "'", "", "dialogWidth:480px;dialogHeight:250px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "search") {
		doSearch(itemValue);
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
}

/** 
    * desc:通过参数显示新增或修改的页面
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function doQuery(bh) {
     if(username=='admin') {
       //document.getElementById("newhref").disabled  = false;
       //alert(111);
      // document.getElementById("DLBH").disabled  = false;
     //  document.getElementById("DLMC").disabled  = false;
    }
   // alert("bh=="+bh);
	if (bh != "") {
		var url = "road.roadmanage.getRoadInfo.d";
		url = encodeURI(url);
		var params = "BH=" + bh;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	//var BH 		= document.getElementById("BH");
	//var DLBH 	= document.getElementById("DLBH");
	var DLMC 	= document.getElementById("DLMC");
	var JGID 	= document.getElementById("ZD1");
	//alert(DLMC.value);
	//alert(JGID.value);
	//BH.value  	= results[0].text;
	//DLBH.value 	= results[1].text;
	//fillListBox("WFZL_TD","WFZL","200","select ID,NAME from T_ATTEMPER_CODE where ID LIKE '024%'","请选择","onChange('WFZL','"+results[2].text+"')");
	//WFZL.value 	= results[2].text;
	DLMC.value = results[1].text;
	JGID.value 	= results[3].text;

	
}
function onChange(obj,value){
	$(obj).value = value;
}

/** 
    * desc:重置
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
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
			select[i].value = "";
		}
	}
}

/** 
    * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路,机构信息
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	if (DLMC == "") {
		alert("请输入道路名称！");
		document.getElementById("DLMC").focus();
		return;
	}
	var params = {};
	var strsel = $("DLMC").options[$("DLMC").selectedIndex].text;
	params["BH"] = $("BH").value;
	params["DLBH"] = $("DLMC").value.toUpperCase();
	params["DLMC"] = strsel;
	params["JGID"] = $("ZD1").value;
    params["insrtOrUpdt"] = insrtOrUpdt;
	if(!validateInput()) return;
	var url = "road.roadmanage.modifyRoadInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyResult});
}
function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("操作成功!");
		window.close();
	}else{
		alert("操作失败!");
	}
}

/** 
    * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路信息
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function modify1(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	if (DLMC == "") {
		alert("请输入道路名称！");
		document.getElementById("DLMC").focus();
		return;
	}
	var params = {};
	params["BH"] = $("BH").value;
	//params["DLBH"] = $("DLBH").value;
	params["DLMC"] = $("DLMC").value.toUpperCase( );
    params["insrtOrUpdt"] = insrtOrUpdt;
	if(!validateInput()) return;
	var url = "road.roadmanage.modifyRoadInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyResult1});
}
function showModifyResult1(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("操作成功!");
		window.close();
	}else{
		alert("操作失败!");
	}
}

/** 
    * desc:删除
    * param:
    * return:
    * author：wxt
    * date: 2010-01-16
    * version:
    */
function doDelete(bh) {                                
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "road.roadmanage.deleteRoadInfo.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "BH=" + bh;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
	} else {  
		return;
	}
}
function showResponseDelete(xmlHttp) {
   // alert(11);
	if(xmlHttp.responseText=='success') {
	  alert("删除成功！");
	 // alert("start");
	}
	else {
	  alert("删除失败!");
	}
	//alert(22);
	doOnLoad();
}

/** 
    * desc:查询
    * param:
    * return:
    * author：wxt
    * date: 2010-1-16
    * version:
    */
function doSearch(jgbh){
	if(!validateInput()) return;
    var sql="select tod.BH,tod.DLBH,tod.DLMC,(select jgmc from t_sys_department tsd where tsd.jgid=tod.JGID) from T_OA_DICT_ROAD tod where 1=1"
	//prompt(sql,sql);DD
	    sql +=$("ZD").value==""?"":(" and JGID='" + $("ZD").value + "'");
	    if( $("CCLD").value != ""){
		    sql +=" and DLMC like '%" +  $("CCLD").value.toUpperCase( ) + "%'";
	    }
	    
	    sql +="order by jgid";
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPages","10");
}

function showResultsPages(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' onmouseover='changeto()' onmouseout='changeback()' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='12%' class='td_r_b td_font'>所属机构</td>";
	    str += "<td width='12%' class='td_r_b td_font'>道路编号</td>";
		str += "<td width='13%' class='td_r_b td_font'>道路名称</td>";
		str += "<td width='5%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			if(results[3].text=='null') {
			   results[3].text = '&nbsp;'
			}
			if(results[1].text=='null') {
			   results[1].text = '&nbsp;'
			}
			if(results[2].text=='null') {
			   results[2].text = '&nbsp;'
			}
			str += "<tr align='center'>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+results[3].text+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+results[1].text+"</td>";
			str += "<td width='13%' class='td_r_b td_font' align=\'center\'>"+results[2].text+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			str += "</tr>";			
	        }
	      }
		str +="</table>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}

//总队用户选择支队显示相应大队
function ZDOnChange(){
	var zdjgid=$("ZD1").value;
	if(zdjgid==""){
		fillListBox("DD_DIV","DD","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","全部");
	}else{
		zdjgid=zdjgid.substring(0,4);
		fillListBox("DD_DIV","DD","170","select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+zdjgid+"'","全部");
	}
}

function ZDCallBack(){
	var jgbh = $('jgbh').value;
	//alert("字符串长度: "+jgbh.length);
	if(jgbh.length==4){
		//当前登录用户属于支队
		$("ZD").value=$('jgid').value;
		$("ZD").disabled=true;
		ZDOnChange();
	}
	if(jgbh.length==6){
		//当前登录用户属于大队
		$("ZD").value=$('jgid').value.substring(0,4)+"00000000";
		$("ZD").disabled=true;
	}
}

//总队用户选择支队显示相应大队
function ZD1OnChange(){
	var zdjgid=$("ZD1").value;
	if(zdjgid==""){
		fillListBox("DD1_DIV","DD1","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","全部");
	}else{
		zdjgid=zdjgid.substring(0,4);
		fillListBox("DD1_DIV","DD1","170","select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+zdjgid+"'","全部");
	}
}

function ZD1CallBack(){
	var jgbh = $('jgbh').value;
	//alert("字符串长度: "+jgbh.length);
	if(jgbh.length==4){
		//当前登录用户属于支队
		$("ZD1").value=$('jgid').value;
		$("ZD1").disabled=true;
		ZD1OnChange();
	}
	if(jgbh.length==6){
		//当前登录用户属于大队
		$("ZD1").value=$('jgid').value.substring(0,4)+"00000000";
		$("ZD1").disabled=true;
	}
}


function DDCallBack(){
	var jgbh = $('jgbh').value;
	//alert("字符串长度: "+jgbh.length);
	if(jgbh.length==4){
		//当前登录用户属于支队
		fillListBox("DD_DIV","DD","170","select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+jgbh+"'","全部");
	}
	if(jgbh.length==6){
		//当前登录用户属于大队
		$("DD").value=$('jgid').value;
		$("DD").disabled=true;
	}
}

function DD1CallBack(){
	var jgbh = $('jgbh').value;
	//alert("字符串长度: "+jgbh.length);
	if(jgbh.length==4){
		//当前登录用户属于支队
		fillListBox("DD1_DIV","DD1","170","select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+jgbh+"'","全部");
	}
	if(jgbh.length==6){
		//当前登录用户属于大队
		$("DD1").value=$('jgid').value;
		$("DD1").disabled=true;
	}
}

function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@#\$\^&]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	return true;
}

function isNumber(number){
	var reg = /^[1-9]d*$/;
	if(!reg.exec(number)) return false;
	return true;
	
}