/**
 *author:dxn
 *desc:加载路段管理查询页面信息
 *date:2010-4-2
 */
function doOnLoad() {
	doSearch();
//	var whereDlmc = ($("dlmc").value == "" ? "" : " and dlmc="
//			+ $("dlmc").value);
//	var whereJgmc = ($("jgmc").value == "" ? "" : " and jgmc="
//			+ $("jgmc").value);
//	var sql = "select rd.rdId,road.ROADNAME,dpt.JGMC from t_oa_roaddepartment rd,t_oa_road_info";
//	sql += " road,T_SYS_DEPARTMENT dpt where rd.ROADID=road.ROADID and rd.jgid=dpt.jgid "
//			+ whereDlmc + whereJgmc + "order by rd.rdid";
//	
//	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
//			"showResultsPage", "10");
}
/** 
 * desc:查询
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function doSearch() {
	var dlmc = $("dlmc") ? $("dlmc").value : null;
	var jgmc = $("jgmc") ? $("jgmc").value : null;
	var sql = "select id,dlmc,xzqhmc from t_oa_roadmanage_code where 1=1 ";

	if (dlmc != null && dlmc != "") {
		sql += " and dlmc LIKE '%" + dlmc + "%'";
	}
	if (jgmc != null && jgmc != "") {
		sql += " and xzqh = '" + jgmc + "'";
	}
	sql += " order by dldj,dlmc";
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
}
function showResultsPage(xmldoc) {
	var rows = xmldoc.getElementsByTagName("row");
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='35%' class='td_r_b td_font'>道路名称</td>";
	str += "<td width='35%' class='td_r_b td_font'>辖区名称</td>";
	str += "<td width='10%' class='td_r_b td_font'>明细</td>";
	str += "<td width='10%' class='td_r_b td_font'>修改</td>";
	str += "<td width='10%' class='td_r_b td_font'>删除</td>";
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		for ( var i = 0; i < rows.length; i++) {
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='35%' class='td_r_b td_font' align=\'center\'>"
					+ results[1].text + "</td>";
			str += "<td width='35%' class='td_r_b td_font' align=\'center\'>"
					+ results[2].text + "</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"
					+ results[0].text + "')\"></td>"
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"
					+ results[0].text + "')\"></td>"
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"
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
 * desc:通过参数显示新增或修改的页面
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function getRoadDepartmentInfo(id) {
	if (id != "") {
		var url = "dispatch.croadDepartment.getRoadDepartment.d";
		url = encodeURI(url);
		var params = "id=" + id;
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
	$("id").value = results[0].text;
	fillListBox("roadIds", "gbbm", "200",
			"select gbdm,dlmc from t_oa_dictdlfx order by roadlevel,dlmc",
			"请选择", "onChange('gbbm','" + results[1].text + "')");
	fillListBox("jgIds", "xzqh", "200",
			"select jgid,jgmc from t_sys_department order by jgid", "请选择",
			"onChange('xzqh','" + results[2].text + "')");
	$("qslc").value = results[3].text;
	$("jslc").value = results[4].text;
	$("xqlc").value = results[5].text;
	$("xqldmc").value = results[6].text;
	$("lmjl").value = results[7].text;
	var insrtOrUpdt = $("insrtOrUpdt").value;
	if (insrtOrUpdt == "2") {
		$("gbbm").disabled = true;
		$("xzqh").disabled = true;
	}
}
function onChange(obj, value) {
	$(obj).value = value;
	var insrtOrUpdt = $("insrtOrUpdt").value;
	if (insrtOrUpdt == "2") {
		$("gbbm").disabled = true;
		$("xzqh").disabled = true;
	}
}

function checkParam(){
	if($("gbbm") && $("gbbm").value == ""){
		alert("请选择道路！");
		$("gbbm").focus();
		return false;
	}
	if($("xzqh") && $("xzqh").value == ""){
		alert("请选择机构！");
		$("xzqh").focus();
		return false;
	}
	var reg = new RegExp("^0|[1-9][0-9]*$");
	if($("qslc")){
		if( $("qslc").value == ""){
			alert("请输入起始里程！");
			$("qslc").focus();
			return false;
		}else if(!reg.test($("qslc").value)){
			alert("起始里程请输入数字！");
			$("qslc").focus();
			return false;
		}
	}
	if($("jslc") && $("jslc").value == ""){
		if( $("jslc").value == ""){
			alert("请输入结束里程！");
			$("jslc").focus();
			return false;
		}else if(!reg.test($("jslc").value)){
			alert("结束里程请输入数字！");
			$("jslc").focus();
			return false;
		}
	}
	if($("qslc").value > $("jslc").value){
		alert("起始里程不能大于结束里程！");
		$("qslc").focus();
		return false;
	}
	
	
	
	return true;
}


/** 
 * desc:增加、编辑 --绑定前端数据到后端进行业务处理
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function modify(insrtOrUpdt) {
	var insrtOrUpdt = $('insrtOrUpdt').value;
	
	if(!checkParam()){
		return;
	}
	
	var dataArray = new Array();
	dataArray.push($("id").value);
	dataArray.push($("gbbm").value);
	dataArray.push($("xzqh").value);
	dataArray.push($("qslc").value);
	dataArray.push($("jslc").value);
	dataArray.push($("xqlc").value);
	dataArray.push($("xqldmc").value);
	dataArray.push($("lmjl").value);
	var url = "dispatch.croadDepartment.editRoadDepartment.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "";
	params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	if (insrtOrUpdt == "1") {
		params = encodeURI(params);
	}
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showModifyResult
	});
}
function showModifyResult(xmlHttp) {
	var text = xmlHttp.responseText;
	if (text.indexOf('success') > -1) {
		alert("成功！");
		window.close();
		doOnLoad();
	} else {
		alert('操作失败，请重试！');
		window.close();
		doOnLoad();
	}
}

/** 
 * desc:删除
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function doDelete(rdId) {
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "dispatch.croadDepartment.delRoadDepartment.d"; //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "id=" + rdId;
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
	alert(xmlHttp.responseText);
	doOnLoad();
}

/** 
 * desc:  重置
 * param:
 * return:
 * author：dxn
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
	for ( var i = 0; i < input.length - 3; i++) {
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
			select[i].value = "-1";
		}
	}
}

/** 
 * desc:增删改查的跳转
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "new") {
		window.showModalDialog("roadDepartmentEdit.jsp?tmp=" + Math.random()
				+ "&insrtOrUpdt=0", "", "dialogWidth:400px;dialogHeight:350px");
		doOnLoad();
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
	if (id == "edit") {
		window.showModalDialog("roadDepartmentEdit.jsp?tmp=" + Math.random()
				+ "&insrtOrUpdt=1&rdId=" + itemValue, "",
				"dialogWidth:400px;dialogHeight:350px");
		doOnLoad();
	}
	if (id == "view") {
		window.showModalDialog("roadDepartmentEdit.jsp?tmp=" + Math.random()
				+ "&insrtOrUpdt=2&rdId=" + itemValue, "",
				"dialogWidth:400px;dialogHeight:350px");
		doOnLoad();
	}
	if (id == "search") {
		doSearch();
	}
}

function reSetInfo() {
	var insrtOrUpdt = $("insrtOrUpdt").value;
	if (insrtOrUpdt == "2") {
		$("gbbm").disabled = true;
		$("xzqh").disabled = true;
	}
}

function doOnChange() {
	reSetInfo();
}

function reSetInfo1() {
}
