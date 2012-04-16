/**
 *author:wkz
 *desc:加载路段管理查询页面信息
 *date:2010-4-2
*/
function doOnLoad(){
	$("roadname").value = "";
	$("sectionname").value = "";
	var sql = "select sct.SECTIONID,sct.SECTIONNAME,sct.ROADID,road.ROADNAME";
	sql +=" from T_OA_ROADSECTION sct,T_OA_ROAD_INFO road"
	sql +=" where sct.ROADID=road.ROADID";
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
/** 
    * desc:查询
    * param:
    * return:
    * author：wkz
    * date: 2010-4-2
    * version:
    */
function doSearch(){
	var roadname = $("roadname").value;
	var sectionname = $("sectionname").value;
	var sql = "select sct.SECTIONID,sct.SECTIONNAME,sct.ROADID,road.ROADNAME from T_OA_ROADSECTION sct,T_OA_ROAD_INFO road where sct.ROADID=road.ROADID";
	if(roadname&&sectionname){
		sql += " and road.ROADNAME LIKE '%" + roadname + "%'";
		sql += " and sct.SECTIONNAME LIKE '%" + sectionname + "%'";
	}else if(roadname&&!sectionname){
		sql += " and road.ROADNAME LIKE '%" + roadname + "%'";
	}else if(!roadname&&sectionname){
		sql += " and sct.SECTIONNAME LIKE '%" + sectionname + "%'";
	}
	//prompt(sql,sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='35%' class='td_r_b td_font'>路段名称</td>";
	    str += "<td width='35%' class='td_r_b td_font'>道路名称</td>";
		str += "<td width='10%' class='td_r_b td_font'>明细</td>";
		str += "<td width='10%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='10%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='35%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"":results[1].text)+"</td>";
			str += "<td width='35%' class='td_r_b td_font' align=\'center\'>"+(results[3].text=="null"?"":results[3].text)+"</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
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
    * desc:通过参数显示新增或修改的页面
    * param:
    * return:
    * author：wkz
    * date: 2010-4-2
    * version:
    */
function getRoadSectionInfo(sectionid) {
	if (sectionid != "") {
		var url = "dispatch.csectionEdit.getRoadSection.d";
		url = encodeURI(url);
		var params = "sectionid=" + sectionid;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var SECTIONID = $("SECTIONID");
	var SECTIONNAME = $("SECTIONNAME");
	//var ROADID = $("ROADID");
	//var ROADNAME = $("ROADNAME");
	//alert(results[0].text+":"+results[1].text+":"+results[2].text+":"+results[3].text);
	SECTIONID.value = results[0].text;
	SECTIONNAME.value = results[1].text;
	fillListBox("ROAD_td","ROADID","111","select roadid,roadname from T_OA_ROAD_INFO order by roadid","请选择","onChange('ROADID','"+results[2].text+"')");
}
function onChange(obj,value){
	//alert(obj+":"+value);
	$(obj).value = value;
}

/** 
    * desc:增加、编辑 --绑定前端数据到后端进行业务处理
    * param:
    * return:
    * author：wkz
    * date: 2010-4-2
    * version:
    */
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	var dataArray = new Array();
	var SECTIONID = $("SECTIONID").value;
	var SECTIONNAME = $("SECTIONNAME").value;
	var ROADID = $("ROADID").value;
	if (SECTIONNAME == "") {
		alert("请输入路段名称！");
		document.getElementById("SECTIONNAME").focus();
		return;
	}
	if (ROADID == "") {
		alert("请选择道路名称！");
		document.getElementById("ROADID").focus();
		return;
	}
	dataArray.push(SECTIONID);
	dataArray.push(SECTIONNAME);
	dataArray.push(ROADID);
	var url = "dispatch.csectionEdit.editRoadSection.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "";
	params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	if(insrtOrUpdt == "1"){
		params = encodeURI(params);
	}
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyResult});
}
function showModifyResult(xmlHttp) {
	var text = xmlHttp.responseText;
	if(text.indexOf('success')>-1){
		alert("成功！");
		window.close();
	}else{
		alert('操作失败，请重试！');
	}
}

/** 
    * desc:删除
    * param:
    * return:
    * author：wkz
    * date: 2010-4-2
    * version:
    */
function doDelete(sectionid) {                                
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "dispatch.csectionEdit.delRoadSection.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "sectionid=" + sectionid;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
	} else {  
		return;
	}
}
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	doOnLoad();
}

/** 
    * desc:  重置
    * param:
    * return:
    * author：wkz
    * date:   2010-04-02
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
    * desc:增删改查的跳转
    * param:
    * return:
    * author：wkz
    * date: 2010-4-2
    * version:
    */
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("RoadSectionEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0", "", "dialogWidth:400px;dialogHeight:120px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("RoadSectionEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&sectionid=" + itemValue, "", "dialogWidth:400px;dialogHeight:120px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("RoadSectionEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&sectionid=" + itemValue, "", "dialogWidth:400px;dialogHeight:120px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "search") {
		doSearch();
	}
}