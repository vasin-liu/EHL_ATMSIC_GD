/** 
    * desc:初始化管理页面
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function doOnLoad(){
	var jgbh = $("jgbh").value;
	var sj1 = $("sj1").value;
	var sj2 = $("sj2").value;
	var sql = "select BH,HPHM,CSDW,(select NAME from T_ATTEMPER_CODE where ID=WFZL),to_char(CCSJ,'yyyy-mm-dd'),CCLD,JSR,JSZ,JGMC from T_OA_ILLEGALVEICLE,T_SYS_DEPARTMENT where substr(BH,0,6)=substr(JGID,0,6)";
	sql += " and BH LIKE '" + jgbh + "%'";
	sql += " and CCSJ BETWEEN TO_DATE('" + sj1 + "','YYYY-MM-DD') AND TO_DATE('" + sj2 + "','YYYY-MM-DD')";
	sql += " order by case when HPHM like '粤%' THEN 1 ELSE 2 END,HPHM";
	//prompt("查询SQL",sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	
	var str = "<div id='block'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
		str += "<tr class='titleTopBack'>";
	    str += "<td width='7%' class='td_r_b td_font'>号牌号码</td>";
		str += "<td width='12%' class='td_r_b td_font'>车属单位（人）</td>";
		str += "<td width='11%' class='td_r_b td_font'>违法种类</td>";
		str += "<td width='11%' class='td_r_b td_font'>查处日期</td>";
		str += "<td width='7%' class='td_r_b td_font'>路段名</td>";
		str += "<td width='7%' class='td_r_b td_font'>驾驶人</td>";
		str += "<td width='8%' class='td_r_b td_font'>驾驶证号</td>";
		str += "<td width='14%' class='td_r_b td_font'>录入单位</td>";
		str += "<td width='8%' class='td_r_b td_font'>填表时间</td>";
		str += "<td width='5%' class='td_r_b td_font'>明细</td>";
		str += "<td width='5%' class='td_r_b td_font'>修改</td>";	
		//str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='12' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"&nbsp;":results[1].text)+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"&nbsp;":results[2].text)+"</td>";
			str += "<td width='11%' class='td_r_b td_font' align=\'center\'>"+(results[3].text=="null"?"&nbsp;":results[3].text)+"</td>";
			str += "<td width='11%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"&nbsp;":results[4].text)+"</td>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[5].text=="null"?"&nbsp;":results[5].text)+"</td>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[6].text=="null"?"&nbsp;":results[6].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[7].text=="null"?"&nbsp;":results[7].text)+"</td>";
			str += "<td width='14%' class='td_r_b td_font' align=\'center\'>"+(results[8].text=="null"?"&nbsp;":results[8].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[0].text=="null"?"&nbsp;":(results[0].text.substring(6,10)+"年"+results[0].text.substring(10,12)+"月"+results[0].text.substring(12,14)+"日"))+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			//str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			str += "</tr>";			
	        }
	      }
		str +="</table></div>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}

/** 
    * desc:初始化查询页面
    * param:
    * return:
    * author：wkz
    * date: 2010-1-29
    * version:
    */
function doViewOnLoad(){
	var jgbh = $("jgbh").value;
	var sj1 = $("sj1").value;
	var sj2 = $("sj2").value;
	var sql = "select BH,HPHM,CSDW,(select NAME from T_ATTEMPER_CODE where ID=WFZL),to_char(CCSJ,'yyyy-mm-dd'),CCLD,JSR,JSZ,JGMC from T_OA_ILLEGALVEICLE,T_SYS_DEPARTMENT where substr(BH,0,6)=substr(JGID,0,6)";
	sql += " and BH LIKE '" + jgbh + "%'";
	sql += " and CCSJ BETWEEN TO_DATE('" + sj1 + "','YYYY-MM-DD') AND TO_DATE('" + sj2 + "','YYYY-MM-DD')";
	sql += " order by case when HPHM like '粤%' THEN 1 ELSE 2 END,HPHM";
	//prompt("查询SQL",sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showViewResultsPage","10");
}
function showViewResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	
	var str = "<div id='block'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
		str += "<tr class='titleTopBack'>";
	    str += "<td width='7%' class='td_r_b td_font'>号牌号码</td>";
		str += "<td width='12%' class='td_r_b td_font'>车属单位（人）</td>";
		str += "<td width='11%' class='td_r_b td_font'>违法种类</td>";
		str += "<td width='11%' class='td_r_b td_font'>查处日期</td>";
		str += "<td width='7%' class='td_r_b td_font'>路段名</td>";
		str += "<td width='7%' class='td_r_b td_font'>驾驶人</td>";
		str += "<td width='8%' class='td_r_b td_font'>驾驶证号</td>";
		str += "<td width='14%' class='td_r_b td_font'>录入单位</td>";
		str += "<td width='8%' class='td_r_b td_font'>填表时间</td>";
		str += "<td width='5%' class='td_r_b td_font'>明细</td>";
		str += "<td width='5%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='12' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"&nbsp;":results[1].text)+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"&nbsp;":results[2].text)+"</td>";
			str += "<td width='11%' class='td_r_b td_font' align=\'center\'>"+(results[3].text=="null"?"&nbsp;":results[3].text)+"</td>";
			str += "<td width='11%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"&nbsp;":results[4].text)+"</td>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[5].text=="null"?"&nbsp;":results[5].text)+"</td>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[6].text=="null"?"&nbsp;":results[6].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[7].text=="null"?"&nbsp;":results[7].text)+"</td>";
			str += "<td width='14%' class='td_r_b td_font' align=\'center\'>"+(results[8].text=="null"?"&nbsp;":results[8].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[0].text=="null"?"&nbsp;":(results[0].text.substring(6,10)+"年"+results[0].text.substring(10,12)+"月"+results[0].text.substring(12,14)+"日"))+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			str += "</tr>";
	        }
	      }
		str +="</table></div>";
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
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("violationModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&bh=''", "", "dialogWidth:530px;dialogHeight:450px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("violationModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&bh='" + itemValue + "'", "", "dialogWidth:530px;dialogHeight:450px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("violationModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&bh='" + itemValue + "'", "", "dialogWidth:530px;dialogHeight:450px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "search") {
		doSearch(itemValue);
	}
	if (id == "viewSearch") {
		doViewSearch(itemValue);
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
	if (id == "excel") {
		doExcel(itemValue);
	}
	if (id == "print") {
		printScreen();
	}
}

/** 
    * desc:通过参数显示新增或修改的页面
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function doQuery(bh) {
	if (bh != "") {
		var url = "drpt.violationmanage.getDataById.d";
		url = encodeURI(url);
		var params = "bh=" + bh;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var BH 		= document.getElementById("BH");
	var HPHM 	= document.getElementById("HPHM");
	var WFZL 	= document.getElementById("WFZL");
	var CSDW 	= document.getElementById("CSDW");
	var CCSJ 	= document.getElementById("CCSJ");
	var CCLD 	= document.getElementById("CCLD");
	var JSR 	= document.getElementById("JSR");
	var JSZ 	= document.getElementById("JSZ");
	var CLQK 	= document.getElementById("CLQK");
	var HZRS 	= document.getElementById("HZRS");
	var SZRS 	= document.getElementById("SZRS");
	var JGMC 	= document.getElementById("JGMC");
	var deafultArea = document.getElementById("deafultArea");
	BH.value  	= results[0].text;
	var HPHM_AERA = results[1].text.substring(0,1);
	if(deafultArea.value==HPHM_AERA){
		fillListBox("HPHM_TD","HPHM_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"disabled()");
	}else{
		if($('insrtOrUpdt').value == "2"){
			fillListBox("HPHM_TD","HPHM_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"onChangeAndDisabled('HPHM_AERA','"+HPHM_AERA+"')");
		}else{
			fillListBox("HPHM_TD","HPHM_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"onChange('HPHM_AERA','"+HPHM_AERA+"')");
		}
	}
	HPHM.value 	= results[1].text.substring(1);
	if($('insrtOrUpdt').value == "2"){
		fillListBox("WFZL_TD","WFZL","270","select ID,NAME from T_ATTEMPER_CODE where ID LIKE '215%'","请选择","onChangeAndDisabled('WFZL','"+results[2].text+"')");
	}else{
		fillListBox("WFZL_TD","WFZL","270","select ID,NAME from T_ATTEMPER_CODE where ID LIKE '215%'","请选择","onChange('WFZL','"+results[2].text+"')");
	}
	//WFZL.value 	= results[2].text;
	CSDW.value 	= results[3].text;
	CCSJ.value 	= results[4].text;
	CCLD.value 	= results[5].text;
	JSR.value 	= results[6].text;
	JSZ.value 	= results[7].text;
	CLQK.value 	= results[8].text;
	HZRS.value 	= results[9].text;
	SZRS.value 	= results[10].text;
	JGMC.value 	= results[11].text;
}
function onChange(obj,value){
	$(obj).value = value;
}
function onChangeAndDisabled(obj,value){
	$(obj).value = value;
	$(obj).disabled=true;
}
function disabled(){
	if($('insrtOrUpdt').value == "2"){
		$('HPHM_AERA').disabled=true;
	}
}

/** 
    * desc:重置
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
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
	for (var i = 0; i < input.length; i++) {
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
    * desc:增加、编辑 --绑定前端数据到后端进行业务处理
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	var dataArray = new Array();
	var BH = $("BH").value;
	var HPHM = $("HPHM_AERA").options($("HPHM_AERA").selectedIndex).text+$("HPHM").value;
	HPHM = HPHM.toUpperCase( );
	var WFZL = $("WFZL").value;
	var CSDW = $("CSDW").value;
	var CCSJ = $("CCSJ").value;
	var CCLD = $("CCLD").value;
	var JSR = $("JSR").value;
	var JSZ = $("JSZ").value;
	var CLQK = $("CLQK").value;
	var HZRS = $("HZRS").value;
	var SZRS = $("SZRS").value;
	
	if ($("HPHM").value == "") {
		alert("请输入号牌号码！");
		document.getElementById("HPHM").focus();
		return;
	}
	
	if($("HPHM").value != ""){
		if(!isCarNumber($("HPHM").value)) {$("HPHM").focus();return;}
	}else{
		HPHM="";
	}
	if (WFZL == "") {
		alert("请选择违法事实！");
		document.getElementById("WFZL").focus();
		return;
	}
	if (CSDW == "") {
		alert("请输入车属单位（人）！");
		document.getElementById("CSDW").focus();
		return;
	}
	if (CCSJ == "") {
		alert("请输入查处日期！");
		document.getElementById("CCSJ").focus();
		return;
	}
	if (CCLD == "") {
		alert("请输入路段名！");
		document.getElementById("CCLD").focus();
		return;
	}
	if (JSR == "") {
		alert("请输入驾驶人！");
		document.getElementById("JSR").focus();
		return;
	}
	// add by lidq 增加核载人数，实载人数必填限制
	if (HZRS == "" ||HZRS==0) {
		alert("请输入核载人数！");
		document.getElementById("HZRS").focus();
		return;
	}
	if (SZRS == "" ||SZRS==0) {
		alert("请输入实载人数！");
		document.getElementById("SZRS").focus();
		return;
	}
	
	if(!(checkIdcard($("JSZ").value)=="为空或验证通过!")) {alert(checkIdcard($("JSZ").value));$("JSZ").focus();return};
	if (CLQK == "") {
		alert("请输入处理情况！");
		document.getElementById("CLQK").focus();
		return;
	}
	if(($("HZRS").value!="") && (isNaN($("HZRS").value))) {alert("请正确输入核载人数！");$("HZRS").focus();return;};
	if(($("SZRS").value!="") && (isNaN($("SZRS").value))) {alert("请正确输入实载人数！");$("SZRS").focus();return;};
	if(!validateInput()) return;
	
	dataArray.push(BH);
	dataArray.push(replaceChr(HPHM));
	dataArray.push(WFZL);
	dataArray.push(replaceChr(CSDW));
	dataArray.push(CCSJ);
	dataArray.push(replaceChr(CCLD));
	dataArray.push(replaceChr(JSR));
	dataArray.push(replaceChr(JSZ));
	dataArray.push(replaceChr(CLQK));
	dataArray.push(replaceChr(HZRS));
	dataArray.push(replaceChr(SZRS));
	
	var url = "drpt.violationmanage.addOrUpdate.d";
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
	if(text.indexOf('成功')>-1){
		alert(text);
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
    * date: 2010-01-13
    * version:
    */
function doDelete(bh) {                                
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "drpt.violationmanage.delete.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "bh=" + bh;
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
    * desc:查询
    * param:
    * return:
    * author：wkz
    * date: 2010-1-13
    * version:
    */
function doSearch(jgbh){
	if(!validateInput()) return;
	if(($("HPHM").value!="")&&(!isCarNumber($("HPHM").value))) {$("HPHM").focus();return;}
	var sj1 = $("sj1").value;
	var sj2 = $("sj2").value;
	if(!(checkIdcard($("JSZ").value)=="为空或验证通过!")) {alert(checkIdcard($("JSZ").value));return};
	var HPHM = $("HPHM_AERA").options($("HPHM_AERA").selectedIndex).value+$("HPHM").value;
	var sql = "select BH,HPHM,CSDW,(select NAME from T_ATTEMPER_CODE where ID=WFZL),to_char(CCSJ,'yyyy-mm-dd'),CCLD,JSR,JSZ,JGMC from T_OA_ILLEGALVEICLE,T_SYS_DEPARTMENT where substr(BH,0,6)=substr(JGID,0,6)";
		sql += " and BH LIKE '" + jgbh + "%'";
		//sql += $("HPHM").value==""?"":(" and HPHM like '%" + replaceChr($("HPHM").value.toUpperCase( )) + "%'");
		sql += " and HPHM like '%" + replaceChr(HPHM.toUpperCase( )) + "%'";
		//sql += $("CCSJ").value==""?"":(" and to_char(CCSJ,'yyyy-MM-dd')='" + $("CCSJ").value + "'");
		sql += $("CCLD").value==""?"":(" and CCLD like '%" + replaceChr($("CCLD").value) + "%'");
		sql += $("JSR").value==""?"":(" and JSR like '%" + replaceChr($("JSR").value) + "%'");
		sql += $("CSDW").value==""?"":(" and CSDW like '%" + replaceChr($("CSDW").value) + "%'");
		sql += $("JSZ").value==""?"":(" and JSZ='" + replaceChr($("JSZ").value) + "'");
		sql += $("ZD").value==""?"":(" and BH LIKE '" + $("ZD").value.substring(0,4) + "%'");
		sql += $("DD").value==""?"":(" and BH LIKE '" + $("DD").value.substring(0,6) + "%'");
		if(sj1&&sj2){
			sql += " AND CCSJ BETWEEN TO_DATE('" + sj1 + "','YYYY-MM-DD') AND TO_DATE('" + sj2 + "','YYYY-MM-DD')";
		}else if(sj1&&!sj2){
			sql += " AND CCSJ>=TO_DATE('" + sj1 + "','YYYY-MM-DD')";
		}else if(!sj1&&sj2){
			sql += " AND CCSJ<=TO_DATE('" + sj2 + "','YYYY-MM-DD')";
		}
		sql += "order by case when HPHM like '粤%' THEN 1 ELSE 2 END,HPHM";
	//prompt(sql,sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	if(rows.length<=0){
		var str = "<div id='block'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
	}else{
		var str = "<div id='block'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
	}
		str += "<tr class='titleTopBack'>";
	    str += "<td width='7%' class='td_r_b td_font'>号牌号码</td>";
		str += "<td width='12%' class='td_r_b td_font'>车属单位（人）</td>";
		str += "<td width='11%' class='td_r_b td_font'>违法种类</td>";
		str += "<td width='11%' class='td_r_b td_font'>查处日期</td>";
		str += "<td width='7%' class='td_r_b td_font'>路段名</td>";
		str += "<td width='7%' class='td_r_b td_font'>驾驶人</td>";
		str += "<td width='8%' class='td_r_b td_font'>驾驶证号</td>";
		str += "<td width='14%' class='td_r_b td_font'>录入单位</td>";
		str += "<td width='8%' class='td_r_b td_font'>填表时间</td>";
		str += "<td width='5%' class='td_r_b td_font'>明细</td>";
		str += "<td width='5%' class='td_r_b td_font'>修改</td>";	
		//str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='12' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"&nbsp;":results[1].text)+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"&nbsp;":results[2].text)+"</td>";
			str += "<td width='11%' class='td_r_b td_font' align=\'center\'>"+(results[3].text=="null"?"&nbsp;":results[3].text)+"</td>";
			str += "<td width='11%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"&nbsp;":results[4].text)+"</td>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[5].text=="null"?"&nbsp;":results[5].text)+"</td>";
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'>"+(results[6].text=="null"?"&nbsp;":results[6].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[7].text=="null"?"&nbsp;":results[7].text)+"</td>";
			str += "<td width='14%' class='td_r_b td_font' align=\'center\'>"+(results[8].text=="null"?"&nbsp;":results[8].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[0].text=="null"?"&nbsp;":(results[0].text.substring(6,10)+"年"+results[0].text.substring(10,12)+"月"+results[0].text.substring(12,14)+"日"))+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			//str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			str += "</tr>";			
	        }
	      }
		str +="</table></div>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}

/** 
    * desc:View查询
    * param:
    * return:
    * author：wkz
    * date: 2010-1-29
    * version:
    */
function doViewSearch(jgbh){
	if(!validateInput()) return;
	if(($("HPHM").value!="")&&(!isCarNumber($("HPHM").value))) {$("HPHM").focus();return;}
	var sj1 = $("sj1").value;
	var sj2 = $("sj2").value;
	if(!(checkIdcard($("JSZ").value)=="为空或验证通过!")) {alert(checkIdcard($("JSZ").value));return};
	var HPHM = $("HPHM_AERA").options($("HPHM_AERA").selectedIndex).value+$("HPHM").value;
	var sql = "select BH,HPHM,CSDW,(select NAME from T_ATTEMPER_CODE where ID=WFZL),to_char(CCSJ,'yyyy-mm-dd'),CCLD,JSR,JSZ,JGMC from T_OA_ILLEGALVEICLE,T_SYS_DEPARTMENT where substr(BH,0,6)=substr(JGID,0,6)";
		sql += " and BH LIKE '" + jgbh + "%'";
		//sql += $("HPHM").value==""?"":(" and HPHM like '%" + replaceChr($("HPHM").value.toUpperCase( )) + "%'");
		sql += " and HPHM like '%" + replaceChr(HPHM.toUpperCase( )) + "%'";
		//sql += $("CCSJ").value==""?"":(" and to_char(CCSJ,'yyyy-MM-dd')='" + $("CCSJ").value + "'");
		sql += $("CCLD").value==""?"":(" and CCLD like '%" + replaceChr($("CCLD").value) + "%'");
		sql += $("JSR").value==""?"":(" and JSR like '%" + replaceChr($("JSR").value) + "%'");
		sql += $("CSDW").value==""?"":(" and CSDW like '%" + replaceChr($("CSDW").value) + "%'");
		sql += $("JSZ").value==""?"":(" and JSZ='" + replaceChr($("JSZ").value) + "'");
		sql += $("ZD").value==""?"":(" and BH LIKE '" + $("ZD").value.substring(0,4) + "%'");
		sql += $("DD").value==""?"":(" and BH LIKE '" + $("DD").value.substring(0,6) + "%'");
		if(sj1&&sj2){
			sql += " AND CCSJ BETWEEN TO_DATE('" + sj1 + "','YYYY-MM-DD') AND TO_DATE('" + sj2 + "','YYYY-MM-DD')";
		}else if(sj1&&!sj2){
			sql += " AND CCSJ>=TO_DATE('" + sj1 + "','YYYY-MM-DD')";
		}else if(!sj1&&sj2){
			sql += " AND CCSJ<=TO_DATE('" + sj2 + "','YYYY-MM-DD')";
		}
		sql += "order by case when HPHM like '粤%' THEN 1 ELSE 2 END,HPHM";
	//prompt(sql,sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showViewResultsPage","10");
}

//总队用户选择支队显示相应大队
function ZDOnChange(){
	var zdjgid=$("ZD").value;
	if(zdjgid==""){
		fillListBox("DD_DIV","DD","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00' order by jgid","全部");
	}else{
		zdjgid=zdjgid.substring(0,4);
		fillListBox("DD_DIV","DD","170","select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+zdjgid+"' and substr(jgid,0,6)<>'"+zdjgid+"00' order by jgid","全部");
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

function DDCallBack(){
	var jgbh = $('jgbh').value;
	//alert(jgbh+"字符串长度: "+jgbh.length);
	//alert("字符串长度: "+jgbh.length);
	if(jgbh.length==4){
		//当前登录用户属于支队
		fillListBox("DD_DIV","DD","170","select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+jgbh+"' and substr(jgid,0,6)<>'"+jgbh+"00' order by jgid","全部");
	}
	if(jgbh.length==6){
		//当前登录用户属于大队
		$("DD").value=$('jgid').value;
		$("DD").disabled=true;
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

//驾驶证号码检验函数 
function checkIdcard(idcard){
	var Errors=new Array(
		"为空或验证通过!",
		"驾驶证号码位数不对!",
		"驾驶证号码出生日期超出范围或含有非法字符!",
		"驾驶证号码校验错误!",
		"驾驶证地区非法!"
	);
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
	var idcard,Y,JYM;
	var S,M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//为空校验
	if(idcard=="") return Errors[0];
	//地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4];
	//身份号码位数及格式检验
	switch(idcard.length){
		case 15:
		if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
			ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
		} else {
			ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
		}
		if(ereg.test(idcard)) return Errors[0];
		else return Errors[2];
		break;
		case 18:
		//18位身份号码检测
		//出生日期的合法性检查 
		//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
		//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
		if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
		} else {
			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
		}
		if(ereg.test(idcard)){//测试出生日期的合法性
			//计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
			+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
			+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
			+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
			+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
			+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
			+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
			+ parseInt(idcard_array[7]) * 1 
			+ parseInt(idcard_array[8]) * 6
			+ parseInt(idcard_array[9]) * 3 ;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y,1);//判断校验位
			if(M.toLowerCase() == idcard_array[17].toLowerCase()) return Errors[0]; //检测ID的校验位
			else return Errors[3];
		}
		else return Errors[2];
		break;
		default:
		return Errors[1];
		break;
	}
}

function isCarNumber(value){
	var reg = /^[0-9a-zA-Z]+$/; 
	if(!reg.test(value)){ 
		alert("号牌号码只能输入字母和数字！"); 
		return false;
	}
	if(value.length!=6){
		alert("号牌号码为6位字母或数字的组合！"); 
		return false;
	}
	return true;
}

function replaceChr(str){
	//alert(str);
	str = str.replace(/'/g, "'||chr(38)||'apos;'||'");
	str = str.replace(/"/g, "'||chr(38)||'quot;'||'");
	str = str.replace(/</g, "'||chr(38)||'lt;'||'");
	str = str.replace(/>/g, "'||chr(38)||'gt;'||'");
	str = str.replace(/%/g, "'||chr(37)||'");
	str = str.replace(/#/g, "'||chr(35)||'");
	str = str.replace(/@/g, "'||chr(64)||'");
	str = str.replace(/&/g, "'||chr(38)||'amp;'||'");
	//str = str.replace(/$/g, "'||chr(36)||'");
	//alert(str);
	return str;
}

function checkNum(event){
	if(event.keyCode < 48 || event.keyCode > 57){
		event.keyCode=0;
		return;	
	}
}

function doExcel(jgbh) {
	//---$("OutExcelCaseInfo").disabled=true;
	//document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
	if(!validateInput()) return;
	if(($("HPHM").value!="")&&(!isCarNumber($("HPHM").value))) {$("HPHM").focus();return;}
	var sj1 = $("sj1").value;
	var sj2 = $("sj2").value;
	if(!(checkIdcard($("JSZ").value)=="为空或验证通过!")) {alert(checkIdcard($("JSZ").value));return};
	var HPHM = $("HPHM_AERA").options($("HPHM_AERA").selectedIndex).value+$("HPHM").value;
	var sql = "select HPHM,(select NAME from T_ATTEMPER_CODE where ID=WFZL)," ;
		//wujh add start
		
		sql +=	"hzrs, ";
		sql +=	"szrs, ";
		sql +=	" decode(sign(to_number(szrs)-to_number(hzrs)),0,'0',1,(to_number(szrs)-to_number(hzrs)),-1,'0'), ";
		//wujh add end
		sql +=	"CSDW,to_char(CCSJ,'yyyy-mm-dd'),CCLD,JSR,JSZ,CLQK from T_OA_ILLEGALVEICLE,T_SYS_DEPARTMENT where substr(BH,0,6)=substr(JGID,0,6)";
		sql += " and BH LIKE '" + jgbh + "%'";
		//sql += $("HPHM").value==""?"":(" and HPHM like '%" + replaceChr($("HPHM").value.toUpperCase( )) + "%'");
		sql += " and HPHM like '%" + replaceChr(HPHM.toUpperCase( )) + "%'";
		//sql += $("CCSJ").value==""?"":(" and to_char(CCSJ,'yyyy-MM-dd')='" + $("CCSJ").value + "'");
		sql += $("CCLD").value==""?"":(" and CCLD like '%" + replaceChr($("CCLD").value) + "%'");
		sql += $("JSR").value==""?"":(" and JSR like '%" + replaceChr($("JSR").value) + "%'");
		sql += $("CSDW").value==""?"":(" and CSDW like '%" + replaceChr($("CSDW").value) + "%'");
		sql += $("JSZ").value==""?"":(" and JSZ='" + replaceChr($("JSZ").value) + "'");
		sql += $("ZD").value==""?"":(" and BH LIKE '" + $("ZD").value.substring(0,4) + "%'");
		sql += $("DD").value==""?"":(" and BH LIKE '" + $("DD").value.substring(0,6) + "%'");
		if(sj1&&sj2){
			sql += " AND CCSJ BETWEEN TO_DATE('" + sj1 + "','YYYY-MM-DD') AND TO_DATE('" + sj2 + "','YYYY-MM-DD')";
		}else if(sj1&&!sj2){
			sql += " AND CCSJ>=TO_DATE('" + sj1 + "','YYYY-MM-DD')";
		}else if(!sj1&&sj2){
			sql += " AND CCSJ<=TO_DATE('" + sj2 + "','YYYY-MM-DD')";
		}
		sql += "order by case when HPHM like '粤%' THEN 1 ELSE 2 END,HPHM";
	//prompt(sql,sql);
	var url = "drpt.violationmanage.excel.d?sql="+sql+"&startTime="+sj1+"&endTime="+sj2;
	url = encodeURI(url);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");   
}

function printScreen(block,PrintOrExcel,jgbh)
{
	var valueOld = document.all.block.innerHTML;
	var otable = document.getElementById("table1");
	try{
		for(i=0;i<otable.rows.length;i++){
			otable.rows[i].deleteCell(10);
			otable.rows[i].deleteCell(9);
		}
	}
	catch(err){
	}
	var value = document.all.block.innerHTML;
    var printdetail = window.open("","printDetail","");
	printdetail.document.open();
	printdetail.document.write("<HTML><head></head><script language=javascript>function preview() {window.clipboardData.setData('Text',document.all('table1').outerHTML);try{var ExApp = new ActiveXObject('Excel.Application'); var ExWBk = ExApp.workbooks.add(); var ExWSh = ExWBk.worksheets(1); ExApp.DisplayAlerts = false; ExApp.visible = true}catch(e){alert('您的电脑没有安装Microsoft Excel软件！');return false}ExWBk.worksheets(1).Paste;}<"+"/script>  <style type='text/css'>body { background-color: #FFFFFF;margin-left:0;margin-right:0;}.table{ border-top:1px solid #000;border-left:1px solid #000;font-size:11px}.table tr th{border-right:1px solid #000;border-bottom:1px solid #000;text-align:center;}.table tr td{border-right:1px solid #000;border-bottom:1px solid #000;text-align:center;}td.kd   {width:11%;}.titleTopBack{font-weight:900}</style><BODY onload='"+PrintOrExcel+"'>"); 
	printdetail.document.write("<PRE>"); 
	printdetail.document.write("<div style='width:100%;height:30px;font-size:20px;font-weight:900;line-height:30px;' align='center'>客运车违法记录</div>");
	printdetail.document.write(value); 
	printdetail.document.write("</PRE>");
	printdetail.document.close("</BODY></HTML>");
	document.all.block.innerHTML = valueOld; 
}