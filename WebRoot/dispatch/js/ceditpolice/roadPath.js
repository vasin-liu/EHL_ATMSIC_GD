/**
 *author:dxn
 *desc:加载路段管理查询页面信息
 *date:2010-4-2
*/
function doOnLoad(){
	doSearch();
//	$("dlmc").value = "";
//	$('jgmc').value = "";
//	var sql = "select r.rpid, ri.roadname, d.jgmc, r.sxxx, r.fxmc from t_oa_roadpath r, t_oa_roaddepartment";
//		sql += " rd, t_sys_department d, t_road_info ri where rd.jgid = d.jgid and r.rdid = rd.rdid";
//		sql += " and ri.roadid = rd.roadid order by r.rpid";
//		
//	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
/** 
    * desc:查询
    * param:
    * return:
    * author：dxn
    * date: 2010-4-2
    * version:
    */
function doSearch(){
	var dlmc = $("dlmc").value;
	var jgmc = $("jgmc").value;
	var sxxx = $("sxxx").value;
	var sql = "select r.rpid, ri.roadname, d.jgmc, r.sxxx, r.fxmc from t_oa_roadpath r, t_oa_roaddepartment";
		sql += " rd, t_sys_department d, t_road_info ri where rd.jgid = d.jgid and r.rdid = rd.rdid";
		sql += " and ri.roadid = rd.roadid";
	if(dlmc){
		sql += " and ri.ROADNAME LIKE '%" + dlmc + "%'";
	}
	if(jgmc){
		sql += " and d.jgmc = '" + jgmc + "'";
	}
	if(sxxx){
		sql += " and r.sxxx='" + sxxx + "'";
	}
	sql += " order by r.rpid";
	
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='20%' class='td_r_b td_font'>道路名称</td>";
	    str += "<td width='25%' class='td_r_b td_font'>辖区名称</td>";
	    str += "<td width='12%' class='td_r_b td_font'>上下行</td>";
	    str += "<td width='25%' class='td_r_b td_font'>方向名称</td>";
		str += "<td width='6%' class='td_r_b td_font'>明细</td>";
		str += "<td width='6%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='6%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='20%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"":results[1].text)+"</td>";
			str += "<td width='25%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"":results[2].text)+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+(results[3].text!="1"?"上行":"下行")+"</td>";
			str += "<td width='25%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"":results[4].text)+"</td>";
			str += "<td width='6%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			str += "<td width='6%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			str += "<td width='6%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
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
    * author：dxn
    * date: 2010-4-2
    * version:
    */
function getRoadPathInfo(rpId) {
	if (rpId != "") {
		var url = "dispatch.croadPath.getRoadPath.d";
		url = encodeURI(url);
		var params = "rpId=" + rpId;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	//alert(results[0].text+":"+results[1].text+":"+results[2].text+":"+results[3].text);
	$("rpId").value = results[0].text;
	fillListBox("roadIds","roadId","180","select roadId,roadname from t_oa_road_info order by roadid","请选择","onChange('roadId','"+results[1].text+"')");
	fillListBox("jgIds","jgId","180","select rd.rdid,d.jgmc from t_oa_roaddepartment rd,t_sys_department d where rd.jgid=d.jgid and rd.roadid='" + results[1].text + "' order by d.jgid","请选择","onChange('jgId','"+results[2].text+"')");
	var sxxxid = results[3].text == "0"?'sxxx0':'sxxx1';
	$(sxxxid).checked = true;
	$('fxmc').value = results[4].text;
}
function onChange(obj,value){
	//alert(obj+":"+value);
	$(obj).value = value;
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
	
	var dataArray = new Array();
	
	var rpId = $("rpId").value;
	
	var roadId = $("roadId").value;
	if (roadId == "") {
		alert("请选择道路名称！");
		$("roadId").focus();
		return;
	}
	
	var rdId = $("jgId").value;
	if (rdId == "") {
		alert("请选择辖区！");
		$("jgId").focus();
		return;
	}
	
	var sxxx = $("sxxx0").checked?"0":"1";

	var fxmc = $("fxmc").value;
	if (fxmc == "") {
		alert("请填写方向名称！");
		$("fxmc").focus();
		return;
	}
	
	dataArray.push(rpId);
	dataArray.push(rdId);
	dataArray.push(sxxx);
	dataArray.push(fxmc);
	
	var url = "dispatch.croadPath.editRoadPath.d";
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
	}else{
		alert('操作失败，请重试！');
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
function doDelete(rpId) {                                
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "dispatch.croadPath.delRoadPath.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "rpId=" + rpId;
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
    * author：dxn
    * date: 2010-4-2
    * version:
    */
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("roadPathEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0", "", "dialogWidth:400px;dialogHeight:180px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("roadPathEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&rpId=" + itemValue, "", "dialogWidth:400px;dialogHeight:180px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("roadPathEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&rpId=" + itemValue, "", "dialogWidth:400px;dialogHeight:180px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "search") {
		doSearch();
	}
}

function onRoadIdChange(){
	var roadId = $('roadId').value;
	fillListBox("jgIds","jgId","180","select rd.rdid,d.jgmc from t_oa_roaddepartment rd,t_sys_department d where rd.jgid=d.jgid and rd.roadid='" + roadId + "' order by d.jgid","请选择","noResult()");
}

function noResult(){
	if($('jgId').options.length == 0){
		var str = '<select id="jgId" disabled style="width:180px;"><option selected>请选择道路</option></select>'
		$('jgIds').innerHTML=str
	}
}