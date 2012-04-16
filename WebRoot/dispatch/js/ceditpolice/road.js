/** 
 * desc:初始化管理页面
 * param:
 * return:
 * author：wxt
 * date: 2010-1-16
 * version:
 */
function doOnLoad() {
	document.getElementById("newroadhref").disabled = false
	doSearch();
//	var roadid = $("ROADID").value;
//	var roadname = $("ROADNAME").value;
//	var whereRoadid = (roadid == "" ? "" : " and tori.roadid='" + roadid + "'");
//	var whereRoadname = (roadname == "" ? "" : " and tori.roadname='"
//			+ roadname + "'");
//	var sql = "select tori.ROADID,tori.ROADNAME from T_OA_ROAD_INFO tori"
//			+ whereRoadid + whereRoadname + " order by tori.ROADNAME";
//	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
//			"showResultsPage", "10");
}
function showResultsPage(xmldoc) {
	var rows = xmldoc.getElementsByTagName("row");
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' onmouseover='changeto()' onmouseout='changeback()' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='12%' class='td_r_b td_font'>道路编号</td>";
	str += "<td width='13%' class='td_r_b td_font'>道路名称</td>";
	str += "<td width='5%' class='td_r_b td_font'>明细</td>";
	str += "<td width='5%' class='td_r_b td_font'>修改</td>";
	str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='5' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		for ( var i = 0; i < rows.length; i++) {
			results = rows[i].childNodes;
			if (results[0].text == 'null') {
				results[0].text = '&nbsp;'
			}
			if (results[1].text == 'null') {
				results[1].text = '&nbsp;'
			}
			str += "<tr align='center'>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"
					+ results[1].text + "</td>";
			str += "<td width='13%' class='td_r_b td_font' align=\'center\'>"
					+ results[2].text + "</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"
					+ results[0].text + "')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"
					+ results[0].text + "')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"
					+ results[0].text + "')\"></td>"
			str += "</tr>";
		}
	}
	str += "</table>";
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
	if (id == "new_road") {
		var returnValuestr = window.showModalDialog("roadAdd.jsp?tmp="
				+ Math.random() + "&insrtOrUpdt=0&roadid=''", "",
				"dialogWidth:480px;dialogHeight:280px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("roadAdd.jsp?tmp="
				+ Math.random() + "&insrtOrUpdt=1&roadid='" + itemValue + "'",
				"", "dialogWidth:480px;dialogHeight:280px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("roadAdd.jsp?tmp="
				+ Math.random() + "&insrtOrUpdt=2&roadid='" + itemValue + "'",
				"", "dialogWidth:480px;dialogHeight:280px");
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
function doQuery(roadid) {
	if (roadid != "") {
		var url = "road.roadmanage.getRoadInfo.d";
		url = encodeURI(url);
		var params = "ID=" + roadid;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showGetXMLResponse
		});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var ID = document.getElementById("ID");
	var ROADID = document.getElementById("ROADID");
	var OLDROADID = document.getElementById("OLDROADID");
	var ROADNAME = document.getElementById("ROADNAME");
	var START = document.getElementById("START");
	var END = document.getElementById("END");
	var ROADLEVEL = document.getElementById("ROADLEVEL");
	ID.value = results[0].text;
	ROADID.value = results[1].text;
	OLDROADID.value = results[1].text;
	ROADNAME.value = results[2].text;
	START.value = results[3].text;
	END.value = results[4].text;
	ROADLEVEL.value = results[5].text;
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
	for ( var i = 0; i < input.length; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for ( var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for ( var i = 0; i < select.length; i++) {
		if (select[i].type != "button" && !select[i].readOnly) {
			select[i].value = "";
		}
	}
}

function checkParam(){
	if($("ROADID") && $("ROADID").value == ""){
		alert("请输入道路编号！");
		$("ROADID").focus();
		return false;
	}
	if($("ROADNAME") && $("ROADNAME").value == ""){
		alert("请输入道路名称！");
		$("ROADNAME").focus();
		return false;
	}
	if($("ROADLEVEL") && $("ROADLEVEL").value == ""){
		alert("请选择道路等级！");
		$("ROADLEVEL").focus();
		return false;
	}
	var reg = new RegExp("^0|[1-9][0-9]*");
	if($("START") && $("START").value == ""){
		alert("请输入道路起点！");
		$("START").focus();
		return false;
	}
	if($("END") && $("END").value == ""){
		alert("请输入道路终点！");
		$("END").focus();
		return false;
	}
	return true;
}

/** 
 * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路信息
 * param:
 * return:
 * author：wxt
 * date: 2010-1-16
 * version:
 */
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	
	if(!checkParam()){
		return;
	}
	
	var params = {};
	params["ID"] = $("ID").value;
	params["ROADID"] = $("ROADID").value;
	params["OLDROADID"] = $("OLDROADID").value;
	params["ROADNAME"] = $("ROADNAME").value;
	params["ROADLEVEL"] = $("ROADLEVEL").value;
	params["START"] = $("START").value;
	params["END"] = $("END").value;
	params["insrtOrUpdt"] = insrtOrUpdt;
	if (!validateInput())
		return;
	var url = "road.roadmanage.modifyRoadInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showModifyResult
	});
}
function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == "success") {
		alert("操作成功!");
		window.close();
	} else {
		alert(xmlDoc);
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
function doDelete(roadid) {
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "road.roadmanage.deleteRoadInfo.d"; //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "ID=" + roadid;
		new Ajax.Request(strUrl, {
			method : "post",
			parameters : params,
			onComplete : showResponseDelete
		});
	} else {
		return;
	}
}
function showResponseDelete(xmlHttp) {
	// alert(11);
	if (xmlHttp.responseText == 'success') {
		alert("删除成功！");
		// alert("start");
	} else {
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
function doSearch(jgbh) {
	if (!validateInput())
		return;
	var sql = "select ID,GBDM,DLMC from t_oa_dictdlfx where 1=1";
	//prompt(sql,sql);DD
	sql += $("ROADID").value == "" ? "" : (" and GBDM like '%"
			+ $("ROADID").value + "%'");
	sql += $("ROADNAME").value == "" ? "" : (" and DLMC like '%"
			+ $("ROADNAME").value + "%'");
	sql += "order by DLMC";
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
}

function validateInput() {
	var input;
	var select;
	var textarea;
	var reg = /^[^~@#\$\^&]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for ( var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			if (!reg.test(item.value)) {
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;
			}
		}
	}
	for ( var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if (!reg.test(item.value)) {
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;
			}
		}
	}
	return true;
}