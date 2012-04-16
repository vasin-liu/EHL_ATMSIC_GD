/** 
 *作者duyq
 *函数功能:获取系统时间
 *参数:offset偏移分钟数
 */
function getSysdate(offset, ignoreTime) {
	var nowDate = new Date();
	var cNowDate = new Date(nowDate - offset * 60 * 1000);
	if (ignoreTime == true) {
		var strDateTime = cNowDate.getYear() + "-"
				+ getFull(cNowDate.getMonth() + 1) + "-"
				+ getFull(cNowDate.getDate());
	} else {
		var strDateTime = cNowDate.getYear() + "-"
				+ getFull(cNowDate.getMonth() + 1) + "-"
				+ getFull(cNowDate.getDate()) + ' '
				+ getFull(cNowDate.getHours()) + ":"
				+ getFull(cNowDate.getMinutes());
	}
	return strDateTime;
}

/**
 * 如果numVar为一位则左侧加零
 * @param {} numVar
 * @return {}
 */
function getFull(numVar) {
	if ((numVar + "").length >= 2) {
		return numVar;
	} else {
		return "0" + numVar;
	}
}

/** 
 *作者zhaoy
 *函数功能:生成年份下拉列表,并放入容器
 *参数:containerId-容器ID 
 *thisId-生成的Select下拉列表Id(和月份下拉列表Id逗号相隔)  
 *widthStr-列表宽度 onChangeFun-列表事件 ignoreMonth-是否忽略月份下拉列表
 */
function createYearSelect(containerId, thisId, widthStr, onChangeFun,
		ignoreMonth) {
	widthStr = 55;
	var container = document.getElementById(containerId);
	var yearSelectStr = "<select id=start" + thisId.split(",")[0] + " style='width:"
			+ widthStr + "' onchange='" + onChangeFun + "'>";
	var temYear = "";
	var monthSelectId = "";
	var currentYear = getSysdate(0, true).split("-")[0];
	var len = parseInt(currentYear) - 1980 + 1;
	for (var i = 0; i < len; i++) {
		temYear = 1980 + parseInt(getFull(i));
		if (temYear == currentYear) {
			yearSelectStr += "<option value=" + temYear + " selected> "
					+ temYear + " </option>";
		} else {
			yearSelectStr += "<option value=" + temYear + "> " + temYear
					+ "</option>";
		}
	}
	yearSelectStr += "</select>年";
	if (ignoreMonth == false) {
		if (thisId.split(",").length > 1) {
			monthSelectId = thisId.split(",")[1];
		}
		yearSelectStr += "&nbsp;"
				+ getMonthSelect("start"+monthSelectId, 40);
	}
	
	yearSelectStr += " - <select id=end" + thisId.split(",")[0] + " style='width:"
			+ widthStr + "' onchange='" + onChangeFun + "'>";
	var temYear = "";
	var monthSelectId = "";
	var currentYear = getSysdate(0, true).split("-")[0];
	var len = parseInt(currentYear) - 1980 + 1;
	for (var i = 0; i < len; i++) {
		temYear = 1980 + parseInt(getFull(i));
		if (temYear == currentYear) {
			yearSelectStr += "<option value=" + temYear + " selected> "
					+ temYear + " </option>";
		} else {
			yearSelectStr += "<option value=" + temYear + "> " + temYear
					+ "</option>";
		}
	}
	yearSelectStr += "</select>年";
	if (ignoreMonth == false) {
		if (thisId.split(",").length > 1) {
			monthSelectId = thisId.split(",")[1];
		}
		yearSelectStr += "&nbsp;"
				+ getMonthSelect("end"+monthSelectId, 40);
	}
	container.innerHTML = yearSelectStr;
}

/** 
 *作者zhaoy
 *函数功能:获取12个月的下拉列表
 *参数:
 */
function getMonthSelect(monthSelectId, widthStr) {
	var monthSelectStr = "<select id=" + monthSelectId + " style='width:"
			+ widthStr + "'>";
	var temMonth = "";
	var currentMonth = getSysdate(0, true).split("-")[1];
	for (var i = 0; i < 12; i++) {
		temMonth = getFull(i + 1);
		if (temMonth == currentMonth) {
			monthSelectStr += "<option value=" + temMonth + " selected> "
					+ temMonth + " </option>";
		} else {
			monthSelectStr += "<option value=" + temMonth + "> " + temMonth
					+ "</option>";
		}
	}
	monthSelectStr += "</select>&nbsp;月";
	return monthSelectStr;
}

/**
 *重设检索时间<br/> 
 */
function setTimeRadio() {
	var timeType = $("timeType").value;
	// 日
	if (timeType == "1") {
		$("ResetTimeName").innerHTML = "起止日期："
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
		DateUser.setDateSE('STARTTIME','ENDTIME',1);
	// 周
	} else if (timeType == "2") {
		var yearSelectStr = "<select id='yearSelect' >";
		$("ResetTimeName").innerHTML = "统计年月："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		yearSelectStr = yearSelectStr + getMonthSelect("monthSelect",40);
		$("ResetTime").innerHTML = yearSelectStr;
	// 月
	} else if (timeType == "3") {
		// 设置年月
		createYearSelect("ResetTime", "yearId,monthId", 60, "", false);
		// 设置lable
		$("ResetTimeName").innerHTML = "起止年月："
	// 季度
	} else if (timeType == "4" || timeType == "5") {
		var yearSelectStr = "<select id='yearSelect' >";
		$("ResetTimeName").innerHTML = "统计年份："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear + "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		$("ResetTime").innerHTML = yearSelectStr;
	// 年
	} else if (timeType == "6") {
		var yearSelectStr = "<select id='startyearId' >";
		$("ResetTimeName").innerHTML = "起止年份："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		
		var endSelectStr = "<select id='endyearId' >";

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				endSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				endSelectStr += "<option value=" + temYear + "> " + temYear + "</option>";
			}
		}
		endSelectStr += "</select>&nbsp;年";
		
		yearSelectStr = yearSelectStr + " - " +  endSelectStr; 
		$("ResetTime").innerHTML = yearSelectStr;
		
		
	}
	/*
	var radios = document.all.timeRadio;
	var timeRadioValue = "";// 时间段标志 1：时 2：日 3：周 4：月
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			timeRadioValue = radios[i].value;
			break;
		}
	}

	if (timeRadioValue == "4") {
		// 设置年月
		createYearSelect("ResetTime", "yearId,monthId", 60, "", false);
		// 设置lable
		$("ResetTimeName").innerHTML = "起止年月："
	} else if (timeRadioValue == "3") {
		var yearSelectStr = "<select id='yearSelect' >";
		$("ResetTimeName").innerHTML = "统计年月："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		$("ResetTime").innerHTML = yearSelectStr;
	} else if (timeRadioValue == "1" || timeRadioValue == "2") {
		$("ResetTimeName").innerHTML = "起止日期："
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}
*/}

/**
 * 画面的初期处理<br/>
 * 画面的初期的设定处理
 */
function initPageInfo(jgid,jgbh,jgmc) {
	if(jgbh.length == 2){
		$("TSJGID").value = "全省";
		$("departType").style.display = "inline";
		$("showDepInfo").style.display = "none";
	}else if(jgbh.length == 4){
		$("TSJGID").value = "所有大队";
		$("showDepartType").innerHTML = 
			'<select style="width:74" name="departType"  id="departType"  onChange="resetDepartType(\'<%=jgmc %>\');">' + 
			    '<option value="4" selected>全市统计</option>' +
			    '<option value="3">大队统计</option>' +
		    '</select>';
		$('departType').options[0].selected = true;
		$("showDepInfo").style.display = "none";
	}else if(jgbh.length == 6){
		$("TSJGID").value = "本大队";
		$("showDepInfo").style.display = "none";
		// 大队用户时的处理
		$("showDepartType").innerHTML = '<select style="width:74" name="departType"  id="departType"  onChange="resetDepartType(\'<%=jgmc %>\');">' + 
										    '<option value="3">大队统计</option>' +
									    '</select>';
		$('departType').options[0].selected = true;
		$('departType').disabled = true; 
		$("showDepInfo").disabled = true;
	}
	$("TSJGID").disabled = true;
	return;
	if (jgbh.length == 2) {
		// 总队用户时的处理
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="全省" disabled />' + 
			 								'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
		$("showDepartType").innerHTML = '<select style="width:74" name="departType"  id="departType"  onChange="resetDepartType(\'<%=jgmc %>\');">' + 
										    '<option value="1" selected>全省统计</option>' +
										    // '<option value="4" selected>全市统计</option>' +
										    '<option value="2">支队统计</option>' +
										    '<option value="3">大队统计</option>' +
									    '</select>';
		
	} else if (jgbh.length == 4) {
		// 支队用户时的处理
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="所有大队" disabled />' + 
		'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
		
		$("showDepartType").innerHTML = '<select style="width:74" name="departType"  id="departType"  onChange="resetDepartType(\'<%=jgmc %>\');">' + 
										    '<option value="4" selected>全市统计</option>' +
										    '<option value="3">大队统计</option>' +
									    '</select>';
			
//		$('departType').options[0].selected = true;
//		$('departType').disabled = true;
	// 大队用户时的处理
	} else if (jgbh.length == 6) { 
		// 大队用户时的处理
		$("showDepartType").innerHTML = '<select style="width:74" name="departType"  id="departType"  onChange="resetDepartType(\'<%=jgmc %>\');">' + 
										    '<option value="3">大队统计</option>' +
									    '</select>';
		$('departType').options[0].selected = true;
		$('departType').disabled = true; 
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="本大队" disabled />' + 
		'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
	}
}

var statTypeDesc = "";
var timeStr = "";
var timetype1 = "";
var timetype2 = "";
var alarmTotalType2 = "";
var timeTypeq = "";
/**
 * 统计信息取得的处理<br/>
 */
function searchTotalInfo() {
	/*var radios = document.all.alarmTotalRadio;
	var alarmTotalType = "";// 统计方式 1：报警单位 2：报警类别
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			alarmTotalType = radios[i].value;
			break;
		}
	}*/
	var alarmTotalType = "";// 统计方式 1：时间 2：报警类别
	alarmTotalType = $("alarmTotalRadio").value;
	alarmTotalType2 = alarmTotalType;
	
//	var radios = document.all.timeRadio;
	var timeRadioType = $("timeType").value;;// 时间 1：日 2：周 3：月 4：季度5：半年6：年
	timeTypeq = timeRadioType;
	/*for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			timeRadioType = radios[i].value;
			break;
		}
	}*/
	// 检索条件的check
	if (checkSeachInfo(timeRadioType) == false) {
		return;
	}
	
	// 报警起止日期的取得
	var STARTTIME = $("STARTTIME")?$("STARTTIME").value:null;
	var ENDTIME = $("ENDTIME")?$("ENDTIME").value:null;
	
	// 统计方式 1：时间时检查起止日期
	if(alarmTotalType == "1") {
		if(ENDTIME != null && STARTTIME != null) {
			var dateCount = DateDiff(ENDTIME,STARTTIME);
			if(dateCount >31) {
				alert("起止日期间隔不能超过31日！");
				return;
			}
		}
	}
	// 报警年月的取得
	var startyearId = $("startyearId")?$("startyearId").value:null;
	var startmonthId = $("startmonthId")?$("startmonthId").value:null;
	var endyearId = $("endyearId")?$("endyearId").value:null;
	var endmonthId = $("endmonthId")?$("endmonthId").value:null;
	// 报警年份的取得
	var yearSelect = $("yearSelect")?$("yearSelect").value:null;
	// 报警月份的取得
	var monthSelect = $("monthSelect")?$("monthSelect").value:null;
	// 单位id
	var depId = $("TSJGID")?$("TSJGID").personId:null;
	var depName = $("TSJGID")?$("TSJGID").value:null;
	
	var jgbh = $("jgbh")?$("jgbh").value:null;
	var jgid = $("jgid")?$("jgid").value:null;
	var departType = $("departType")?$("departType").value:null;
	
	var params = {};
	timetype1 = timeRadioType;
	params["alarmTotalType"] = alarmTotalType;
	params["timeRadioType"] = timeRadioType;
	params["STARTTIME"] = STARTTIME;
	params["ENDTIME"] = ENDTIME;
	params["startyearId"] = startyearId;
	params["startmonthId"] = startmonthId;
	params["endyearId"] = endyearId;
	params["endmonthId"] = endmonthId;
	params["monthSelect"] = monthSelect;
	params["yearSelect"] = yearSelect;
	params["depId"] = depId;
	params["depName"] = depName;
	params["jgbh"] = jgbh;
	params["jgid"] = jgid;
	params["departType"] = departType;
	Params.alarmTotalType = alarmTotalType;
	Params.timeRadioType = timeRadioType;
	Params.STARTTIME = STARTTIME;
	Params.ENDTIME = ENDTIME;
	Params.startyearId = startyearId;
	Params.startmonthId = startmonthId;
	Params.endmonthId = endmonthId;
	Params.endyearId = endyearId;
	Params.monthSelect = monthSelect;
	Params.yearSelect = yearSelect;
	Params.depId = depId;
	Params.depName = depName;
	Params.jgbh = jgbh;
	Params.jgid = jgid;
	Params.departType = departType;
	Params.chartType = $("chartType").value;
	if(departType == 3){
		if(depId != undefined && depId != null && depId != "" ){
			Params.depId = Params.depId.split("；").join(";");
			Params.depName = Params.depName.split("；").join(";");
		}
	}
	// 定义并赋值公用变量（统计类型-statTypeDesc）
	switch (parseInt(alarmTotalType)) {
		case 1 :
			statTypeDesc = "上报警情单位";
			break;
		case 2 :
			statTypeDesc = "上报警情单位";
			break;
		default :
			statTypeDesc = "";
			break;
	}
	switch (timeRadioType) {
		case "1" : // 日
			timeStr = "(" + STARTTIME + "---" + ENDTIME + ")";
			break;
		case "2" :// 周
			timeStr = "("  +yearSelect+"年"+monthSelect+"月"+ ")";
			break;
		case "3" :// 月
			timeStr = "(" + startyearId+"年"+"-"+startmonthId +"月"+ "---" + endyearId+"年"+"-"+endmonthId+"月"+")";
			break;
		case "4" :// 季度
			timeStr = "("  +yearSelect+"年"+"季度"+ ")";
			break;
		case "5" :// 半年
			timeStr = "("  +yearSelect+"年"+"半年"+ ")";
			break;
		case "6" :// 年
			timeStr = "(" + startyearId + "年" + "---" + endyearId +"年"+ ")";
			break;
	}
	//--<
//	var pstr = "";
//	for(var attr in params){
//		pstr += ";"+attr+":"+params[attr];
//	}
//	$("showDivId").innerText = pstr.substring(1);
//	pstr = "";
//	for(var attr in Params){
//		pstr += ";"+attr+":"+Params[attr];
//	}
//	$("showDiv1Id").innerText = pstr.substring(1);
	//-->
	var url = "dispatch.cmaterialInfo.searchTotalInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showSearchTotalInfo});
}

var CNT = 1;

/**
 * 检索结果的回显设置<br/>
 * @param {} xmlHttp
 */
function showSearchTotalInfo(xmlHttp) {
	$('msg').innerHTML = "<span class='currentLocationBold'>" + timeStr
					+ "各单位警情量统计</span>";
	showType="";
	var xmlDoc = xmlHttp.responseXML;
	xmlDoc = xmlDoc.documentElement
	var rows = xmlDoc.childNodes;
	var zongji = 0;

	// var colPersent = parseInt(85/(colsNum+1),10);//每列所占百分比,首列占15%

	var len;
	var shortLen = "1000px";
	if(timetype1 == "1") {
		if(timetype2 > (15 * 24 * 60 * 60 * 1000)) {
			shortLen = "2500px";
		} else {
			if(timetype2 > (10 * 24 * 60 * 60 * 1000)) {
				shortLen = "1500px";
			} else {
				if(timetype2 > (5 * 24 * 60 * 60 * 1000)) {
					shortLen = "1000px";
				} else {
					shortLen = "800px";
				}
			}
		}
	}
	if(alarmTotalType2 == "2") {
		shortLen = "1400px";
	}
	if(timeTypeq == "5" || timeTypeq == "4" || timeTypeq == "3" || timeTypeq == "6") {
		shortLen = "800px";
	} else if(timeTypeq == "2") {
		shortLen = "1000px";
	}
	var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=" + shortLen + " cellSpacing=0 cellPadding=0>";
	for (var i = 0; i < rows.length; i++) {
		if (i == 0) {
			strTab = strTab
					+ "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
		} else {
			strTab = strTab
					+ "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" >";
		}
		var cols = rows[i].childNodes;
		var alignStyle = "";
		for (var k = 0; k < cols.length; k++) {
			if (i == 0) {// 第一行居中
				alignStyle = " style='text-align:center '";
			} else {
				alignStyle = "style='text-align:left' ";
				if (k == 0) {
					alignStyle += " width='200px' class='scrollRowThead'";
				}
			}
			if (k == 0) {
					alignStyle += " width='200px' class='scrollRowThead'";
				}
			strTab = strTab + "<td " + alignStyle + " value='" + cols[k].text
					+ "'>";
			showType = showType+cols[k].text+",";
			if (i == rows.length - 1 && k == cols.length - 1 && i != 0) {// --最后统计行
				strTab = strTab + "&nbsp;";
				zongji = cols[k].text;// 事故总数
				len = cols.length - 1;
			} else {
				if (i == 0) {// --表头
					if (k == 0) {
						strTab = strTab  + "<input name='checkboxAll' id='checkboxAll' type='checkbox' onclick='clickAllCheckBox()'  />全选   " +statTypeDesc;// 首行首列
					} else {
						strTab = strTab + cols[k].text.replace('周', '周<br>');
					}
				} else {
					if (k == 0 && i != 0 && i != rows.length - 1) {// 除去首行和末行的，每行第一列加checkbox
						strTab += "<input name='boxname' id='boxname' value='1' type='checkbox' checked />";
					}
					if (cols[k].text == "0") {// --数据为0不显示

						strTab = strTab + "&nbsp;";
					} else {
						strTab = strTab + cols[k].text;
					}

				}
			}
			strTab = strTab + "</td>";
		}
		strTab = strTab + "</tr>";
	}

	if (rows.length != 1) {
		strTab = strTab + "<tr>";
		strTab = strTab + "<td align= 'right' colspan = '" + len + "'>";
		strTab = strTab + "总计";
		strTab = strTab + "</td>";
		strTab = strTab + "<td>";
		strTab = strTab + zongji;
		strTab = strTab + "</td>";
		strTab = strTab + "</tr>";
	}
	strTab = strTab + "</table>";
	$("overSpeedList").innerHTML = strTab;
	Popup.prototype.hideTips();
}

function clickAllCheckBox() {
    var allBoxs = document.getElementsByName("boxname");	
	for(var  k = 0; k <allBoxs.length; k++){
		if($("checkboxAll").checked) {
			allBoxs[k].checked = true;
		} else {
			allBoxs[k].checked = false;
		}
    }
}

/**
 * 鼠标移入<br/>
 * @param {} obj
 * @param {} color
 */
function mouseover(obj,color){ 
   if(obj.bgColor != color){
	 obj.bgColor = "#117FC9"; 
   }
}

/**
 * 鼠标移出<br/>
 * @param {} obj
 * @param {} color
 */
function mouseout(obj,color){
   if(obj.bgColor != color.toLowerCase()){
	  obj.bgColor = "#FFFFFF"; 
   }
}

/**
 * 统计图取得的处理<br/>
 */
function searchGraphic() {
	
}

/**
 * 检索条件的check
 * 
 * @return {Boolean}
 */
function checkSeachInfo(timeRadioType) {

	// 日
	if (timeRadioType == "1") {
		var start = $("STARTTIME").value;
		var end = $("ENDTIME").value;
		if (start == "") {
			alert("请选择开始日期");
			return false;
		}
		if (end == "") {
			alert("请选择结束日期");
			return false;
		}
		if (start > end) {
			alert("开始日期必须小于结束日期");
			return false;
		}
		start = start.split('-');
		end = end.split('-');
		var date1 = new Date(parseInt(start[0]), parseInt(start[1]) - 1,
				parseInt(start[2]));
		var date2 = new Date(parseInt(end[0]), parseInt(end[1]) - 1,
				parseInt(end[2]));
		var ttdateTime = DateDiff($("STARTTIME").value,$("ENDTIME").value) + 1;
		timetype2 = date2.getTime() - date1.getTime();
//		if (((date2.getTime() - date1.getTime()) > 30 * 24 * 60 * 60 * 1000) || ttdateTime > 31) {
//			alert("统计日期间隔不能大于31天");
//			return false;
//		}
	}
	// 月
	if(timeRadioType=="3"){
		// 报警年月的取得
		var startyearId = $("startyearId")?$("startyearId").value:null;
		var startmonthId = $("startmonthId")?$("startmonthId").value:null;
		var endyearId = $("endyearId")?$("endyearId").value:null;
		var endmonthId = $("endmonthId")?$("endmonthId").value:null;
		var startYM =startyearId+startmonthId; 
		var endYM =endyearId+endmonthId; 
		if (startYM >endYM) {
			alert("输入的起始年月不能大于结束年月！");
			return false;
		}
	}
	// 季度
	if(timeRadioType=="4"){/*
		var year = $("yearSelect").value;
		if(year == ""){
			alert("请填写年");
			return false;
		}
		
	*/}
	// 半年
	if( timeRadioType == "5"){
	}
	// 年
	if( timeRadioType == "6"){
		var startyearId = $("startyearId")?$("startyearId").value:null;
		var endyearId = $("endyearId")?$("endyearId").value:null;
		if (startyearId >endyearId) {
			alert("输入的起始年不能大于结束年！");
			return false;
		}
	}
	return true;
}

/**
 * 函数功能:获取和组织页面数据，调用绘图函数绘图
 * 参数说明:title-标题;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);xDesc-横轴描述;yDesc-纵轴描述.
 * 调用实例:buildChart('重点车辆行为统计','line','卡口','频度')
 */
function periodFlowChart(){
    var allBoxs = document.getElementsByName("boxname");
    var boxs = new Array();
    for(var  k = 0; k <allBoxs.length; k++){
        if(allBoxs[k].checked == true){
            boxs.push(allBoxs[k]);
        }
    }
    if(boxs.length == 0){
       alert("请选择要生成图表的数据！");
       return;
    }
    if(Params.depId != undefined 
    		&& Params.depId != null 
    		&& Params.depId != ""){
    	var depIds = Params.depId.split(";");
    	if(depIds.length > 20){
    		alert("对比大队不能超过20个！");
    		return;
    	}
    }
    
    //--<
    Params.jgmcs = "";
    for(var i=0; i < boxs.length; i++){
    	Params.jgmcs += ","+boxs[i].parentNode.parentNode.childNodes[0].value;
    }
    Params.jgmcs = Params.jgmcs.substring(1);
    //-->
    var tds = "";
    var trObj = "";
    var DataForChartStr = "";
    var temStr = "";
    for(var i = 0 ; i < boxs.length; i ++){
        var trObj = boxs[i].parentNode.parentNode;
        tds = trObj.childNodes;
        temStr ="";//清空上一个路口段流量载体
        //组织一个路口段流量的数据，用于绘图
        for(var j = 1 ; j <tds.length; j++ ){
            if(j != tds.length-1 ){
            	var changeJ = j; // 临时变量，表示绘图的X轴上的值
            	changeJ = changeState(j); 
            
                if(j == tds.length-2){
                    temStr += changeJ + ","+tds[j].value +","+tds[0].value ;  
                }else{
                    temStr += changeJ + ","+tds[j].value +","+tds[0].value +";"; 
                }
            }
        }
        if(i == boxs.length-1){
            DataForChartStr += temStr;
        }else{
            DataForChartStr += temStr + ";";
        }
    }
    //alert(DataForChartStr);
    var title = "警情信息统计图" +" "+timeStr;
     var chartType = "";
    if(parseInt($('alarmTotalRadio').value)==1){
    	chartType="line";
    }else if(parseInt($('alarmTotalRadio').value)==2){
    	chartType="bar";
    }
   
    var xDesc = "";
    if(parseInt($('alarmTotalRadio').value)==1){
    	xDesc = "时段";
    }else if(parseInt($('alarmTotalRadio').value)==2){
    	xDesc = "报警类别";
    }
    var yDesc = "警情发生次数";
    chartType = $('chartType').value;
    doChart(title,chartType,xDesc,yDesc,DataForChartStr);
}

// 判断统计类型
function changeState(j) {
    var changeJ = "";
    arrType = showType.split(",");
		if(j>=1&&j<arrType.length-1){
		 changeJ = arrType[j];
		}
	return changeJ;
}

var Params = {
	alarmTotalType:"",
	timeRadioType:"",
	STARTTIME:"",
	ENDTIME:"",
	startyearId:"",
	startmonthId:"",
	endyearId:"",
	endmonthId:"",
	monthSelect:"",
	yearSelect:"",
	depId:"",
	depName:"",
	jgbh:"",
	jgid:"",
	departType:"",
	chartType:"",
	jgmcs:""
}
/**
 * 函数功能:显示统计图表,发送Ajax请求数据.
 * 参数说明:title-标题;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);xDesc-横轴描述;yDesc-纵轴描述;chartArray-统计图表数据.
 * 调用实例:buildChart('重点车辆行为统计','line','卡口','频度')
 */
function doChart(title,chartType,xDesc,yDesc,chartDataStr){
    var title = title;
    var chartType = chartType;
    var xDesc =xDesc;
    var yDesc = yDesc;
    
    if(chartDataStr.length == 0){
       alert("没有数据！");
       return;
    }
//	var url = 'dispatch.cmaterialInfo.buildChart.d';

//	url = encodeURI(url);
//	var params = "title=" + title + "&chartType="+chartType+"&xDesc=" + xDesc + "&yDesc=" + yDesc +"&chartDataStr="+chartDataStr;
//	params = encodeURI(params);
//  new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showImage});
  
	var params = "alarmTotalType="+Params.alarmTotalType
				+"&timeRadioType="+Params.timeRadioType
				+"&STARTTIME="+Params.STARTTIME
				+"&ENDTIME="+Params.ENDTIME
				+"&startyearId="+Params.startyearId
				+"&startmonthId="+Params.startmonthId
				+"&endyearId="+Params.endyearId
				+"&endmonthId="+Params.endmonthId
				+"&monthSelect="+Params.monthSelect
				+"&yearSelect="+Params.yearSelect
				+"&jgbh="+Params.jgbh
				+"&jgid="+Params.jgid
				+"&departType="+Params.departType
				+"&title="+title
				+"&xDesc="+xDesc
				+"&yDesc="+yDesc
				+"&chartType="+Params.chartType
				+"&jgmcs="+Params.jgmcs
				+"&depId="+Params.depId
				+"&depName="+Params.depName;
	$("showDiv2Id").innerText = params;
	var url = "materialTotalInfoStat.jsp?"+params;
	url = encodeURI(url);//alert(url)
  window.showModalDialog(url, "", "dialogWidth:900px;dialogHeight:600px");
  
//	var left = (screen.availWidth - 800) / 2;
//	var top = (screen.availHeight - 500) / 2;
//    window.open(url, "", "height=600,width=900,top=" + top + ",left=" + left
//			+ ",toolbar=yes,menubar=yes,"
//			+ "scrollbars=yes,resizable=yes,location=no,status=no");
}

/**
 * 函数功能：Ajax回调,解析请求返回的数据并展示出来.
 */
function showImage(xmlHttp){
   var strText = xmlHttp.responseText;
   if(strText == "null"){
      Popup.prototype.hideTips();
      alert("没有可生成图表的数据！");
      return;
   }
   var splitLoc = strText.indexOf("|");
   var fileName = strText.substring(0,splitLoc);
   var graphURL = strText.substring(splitLoc+1);
   var strlink = "../../../base/ChartView.jsp?fileName="+fileName+"&graphURL="+graphURL;
   Popup.prototype.hideTips();
   window.showModalDialog(strlink,"","dialogWidth:790px;dialogHeight:500px");
}

function DateDiff(sDate1,sDate2){
   var aDate,oDate1,oDate2,iDays ;
   aDate =sDate1.split('-');
   oDate1 = new Date(aDate[1]+'-'+aDate[2]+'-'+aDate[0]) ;
   //转换为04-19-2007格式
   aDate = sDate2.split('-');
   oDate2 = new Date(aDate[1]+'-'+ aDate[2] +'-'+aDate[0]);
   iDays = parseInt(Math.abs(oDate1 -oDate2)/1000/60/60/24);//把相差的毫秒数转换为天数
   return iDays ;
   }
   
function resetTree(jgmc) {
	var leng = $("jgbh").value.length;
	if(leng == 2) {
		setTree('440000000000','TSJGID','135','95','400','300');
	}
	if(leng == 4) {
		var temp = $("jgbh").value.substring(0,4) + "00000000";  
		setTree(temp,'TSJGID','135','95','400','300');
	}
	if(leng == 6) {
		$("showDepInfo").display = "none";
		$("TSJGID").value = jgmc; 
	}
}

/**
 * 统计单位类型不同时的重设处理<br/>
 * 统计单位类型不同时的重设处理
 */
function resetDepartType() {
	var depValue = $("departType").value;
	var leng = $("jgbh").value.length;
	$("TSJGID").personId = undefined;
	if(depValue == 1){
		$("TSJGID").value = "全省";
		$("TSJGID").disabled = true;
		$("showDepInfo").style.display = "none";
	}else if(depValue == 2){
		$("TSJGID").value = "所有支队";
		$("TSJGID").disabled = true;
		$("showDepInfo").style.display = "none";
	}else if(depValue == 3){
		if(leng == 2){
			$("TSJGID").value = "";
		}else if(leng == 4){
			$("TSJGID").value = "";
		}else if(leng == 6){
			$("TSJGID").value = "本大队";
		}
		$("TSJGID").readOnly = true;
		$("showDepInfo").style.display = "inline";
	}
	return;
	// 全省统计
	if(depValue == "1") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="全省" disabled />' + 
		'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
	// 支队统计
	} else if(depValue == "2") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="所有支队" disabled />' + 
		'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
	// 大队统计
	} else if(depValue == "3") {
		if(leng == 2) {
			$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" readOnly/>' + 
			'<img id="showDepInfo" src="../../images/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" onClick="resetTree(\'<%=jgmc %>\');">' + 
			'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
		} else if(leng == 4) {
			$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" readOnly/>' + 
			'<img id="showDepInfo" src="../../images/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" onClick="resetTree(\'<%=jgmc %>\');">' + 
			'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
		} else {
			$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="所有大队" disabled />' + 
			'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
		}
	// 全市统计
	} else if (depValue == "4") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:270;" value="所有大队" disabled />' + 
		'    图形：' +
											'<select style="width:60" id="chartType">' +
											   ' <option value="line" selected>折线图</option>' +
											   ' <option value="bar">柱状图</option>' +
											'</select>';
	}
}

/**
 * 根据统计条件设置查询时间<br/>
 * 根据统计条件设置查询时间的处理
 */
function reSetTimeShow() {
	if($('alarmTotalRadio').value != '1'){
		$('timeType').options[0].selected = true;
		$('timeType').disabled = true;
		$("ResetTimeName").innerHTML = "起止日期："
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
		$("chartType").value = "bar";
		DateUser.setDateSE('STARTTIME','ENDTIME',1);
	}else{
		$('timeType').disabled = false;
		$("chartType").value = "line";
	}
}
//计算天数差的函数，通用 
function DateDiff(sDate1, sDate2){    //sDate1和sDate2是2006-12-18格式 
	var aDate, oDate1, oDate2, iDays 
	aDate = sDate1.split("-") 
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])    //转换为12-18-2006格式 
	aDate = sDate2.split("-") 
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) 
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24)    //把相差的毫秒数转换为天数
	return iDays;
}

Date.prototype.getTimes_ = function() {
	var encap = function(date) {
		date = "0" + date;
		return date.substr(date.length - 2);
	}
	var year = this.getFullYear();
	var month = this.getMonth() + 1;
	var day = this.getDate();
	var hour = this.getHours();
	var minute = this.getMinutes();
	var second = this.getSeconds();
	var times = [ year, month, day, hour, minute, second ];
	for ( var i = 1; i < times.length; i++) {
		times[i] = encap(times[i]);
	}
	return times;
}

//时间格式化类型，1年、2月、3日、4时、5分、6秒，默认为3
Date.prototype.format_ = function(ft) {
	var time = null;
	if (ft >= 1 && ft <= 6) {
		var times = this.getTimes_();
		var sepItems = [ "", "-", "-", " ", ":", ":" ];
		var time = "";
		for ( var i = 0; i < ft; i++) {
			time += sepItems[i] + times[i];
		}
		time = time.substring();
	}
	return time;
}

//1年2月3日4小时5分6秒
Date.prototype.changeTime_ = function(tunit, number, ft) {
	if (typeof number == "number" && tunit >= 1 && tunit <= 6) {
		var gettunits = [ this.getFullYear, this.getMonth, this.getDate,
				this.getHours, this.getMinutes, this.getSeconds ];
		var settunits = [ this.setFullYear, this.setMonth, this.setDate,
				this.setHours, this.setMinutes, this.setSeconds ];
		tunit--;
		this.getCurrent = gettunits[tunit];
		this.setCurrent = settunits[tunit];
		this.setCurrent(this.getCurrent() - number);
	}
	return this.format_(ft);
}

var DateUser = {
	setDateSE : function(dateStartId, dateEndId, differ) {
		var date = new Date();
		$(dateEndId).value = date.format_(3);
		$(dateStartId).value = date.changeTime_(2, differ, 3);
	}
}