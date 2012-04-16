
/**
 * 作者duyq 函数功能:获取系统时间 参数:offset偏移分钟数
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
 * 
 * @param {}
 *            numVar
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
 * 作者zhaoy 函数功能:生成年份下拉列表,并放入容器 参数:containerId-容器ID
 * thisId-生成的Select下拉列表Id(和月份下拉列表Id逗号相隔) widthStr-列表宽度 onChangeFun-列表事件
 * ignoreMonth-是否忽略月份下拉列表
 */
function createYearSelect(containerId, thisId, widthStr, onChangeFun,
		ignoreMonth) {
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
 * 作者zhaoy 函数功能:获取12个月的下拉列表 参数:
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
	monthSelectStr += "</select>月";
	return monthSelectStr;
}

/**
 * 重设检索时间<br/>
 */
function setTimeSelect() {
	if($('alarmTotalRadio').value != '1'){
		$('timeRadio').options[0].selected = true;
		$('timeRadio').disabled = true;
		$("ResetTimeName").innerHTML = "起止日期："
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}else{
		$('timeRadio').disabled = false;
	}
}

/**
 * 重设检索时间<br/>
 */
function setTimeRadio() {
	//var radios = document.all.timeRadio;
	var radios = $('timeRadio');
	var timeRadioValue = $('timeRadio').value;// 时间段标志 1：时 2：日 3：周 4：月
	/*for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			timeRadioValue = radios[i].value;
			break;
		}
	}*/

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
		yearSelectStr += getMonthSelect("monthSelect",40)
		$("ResetTime").innerHTML = yearSelectStr;
	} else if (timeRadioValue == "1" || timeRadioValue == "2") {
		$("ResetTimeName").innerHTML = "起止日期："
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}else if (timeRadioValue == "5" || timeRadioValue == "6") {
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
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		$("ResetTime").innerHTML = yearSelectStr;
	} else if (timeRadioValue == "7") {
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
 * 统计单位类型不同时的重设处理<br/>
 * 统计单位类型不同时的重设处理
 */
function resetDepartType(jgid) {
	var depValue = $("departType").value;
	
	// 全部
	if(depValue == "1") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:285;" readOnly/>  ' + 
		'<img src="../../images/popup/btnselect.gif" alt="选择单位" align="absmiddle"  style="cursor:hand;" onClick="setTree('+jgid+',\'TSJGID\',\'125\',\'155\',\'400\',\'300\')">';
		
	// 支队统计
	} else if(depValue == "2") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:300;" value="所有支队" disabled />';
	// 大队统计
	} else if(depValue == "3") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:300;" value="所有大队" disabled />';
	// 总队统计
	} else if(depValue == "4") {
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:300;" value="全省" disabled />';
	}
}


/**
 * 画面的初期处理<br/>
 */
function initPageInfo(jgbh) {
	if(jgbh.length == 2){
		$('departType').options.length=0;
		$('departType').options.add(new Option("全部","1",true,true));
		$('departType').options.add(new Option("全省统计","4"));
		$('departType').options.add(new Option("支队统计","2"));
		$('departType').options.add(new Option("大队统计","3"));
	}
	if(jgbh.length == 4){
		$('departType').options.length=0;
		$('departType').options.add(new Option("全部","1",true,true));
		$('departType').options.add(new Option("全市统计","2"));
		$('departType').options.add(new Option("大队统计","3"));
	}
	if(jgbh.length == 6){
		$('departType').disabled = true;
		$("showDwInfo").innerHTML= '<input type="text" id="TSJGID" style="width:300;" value="本大队" disabled />';
	}
}
var timeStr = "";
var showType="";
/**
 * 统计信息取得的处理<br/>
 */
function searchTotalInfo(jgbh) {
	showType = "";
	var radios = document.all.alarmTotalRadio;
	var alarmTotalType = "";// 统计方式 1：被投诉机构 2：流转状态 3：投诉业务类别
	/*
	 * for (var i = 0; i < radios.length; i++) { if (radios[i].checked == true) {
	 * alarmTotalType = radios[i].value; break; } }
	 */
	alarmTotalType = $("alarmTotalRadio").value;

	radios = document.all.timeRadio;
	var timeRadioType = "";// 时间 2：日 3：周 4：月 5：季度 6：半年 7：年
	/*for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			timeRadioType = radios[i].value;
			break;
		}
	}*/
	timeRadioType = $("timeRadio").value;
	// 报警起止日期的取得
	var STARTTIME = $("STARTTIME") ? $("STARTTIME").value : null;
	var ENDTIME = $("ENDTIME") ? $("ENDTIME").value : null;
	// 报警年月的取得
	var startyear = $("startyearId") ? $("startyearId").value : null;
	var startmonth = $("startmonthId") ? $("startmonthId").value : null;
	var endyear = $("endyearId") ? $("endyearId").value : null;
	var endmonth = $("endmonthId") ? $("endmonthId").value : null;
	// 报警年份的取得
	var yearSelect = $("yearSelect") ? $("yearSelect").value : null;
	// 报警月份的取得
	var monthSelect = $("monthSelect")?$("monthSelect").value:null;
	// 定义并赋值公用变量（各时段类型划分的结果组数-colsNum）
	switch (parseInt(timeRadioType)) {
		case 1 :
			colsNum = "24";
			break;
		case 2 :
			colsNum = "5";
			break;
		case 3 :
			colsNum = "31 ";
			break;
		case 4 :
			colsNum = "12 ";
			break;
		default :
			colsNum = "31";
			break;
	}
	// 定义并赋值公用变量（统计类型-statTypeDesc）
	switch (parseInt(alarmTotalType)) {
		case 1 :
			statTypeDesc = "被投诉机构";
			break;
		case 2 :
			statTypeDesc = "被投诉机构";
			break;
		case 3 :
			statTypeDesc = "被投诉机构";
			break;
		default :
			statTypeDesc = "被投诉机构";
			break;
	}
	switch (parseInt(timeRadioType)) {
		case 1 ://时：暂时未用到
			timeStr = "(" + STARTTIME + "---" + ENDTIME + ")";
			break;
		case 2 ://日
			timeStr = "(" + STARTTIME + "---" + ENDTIME + ")";
			break;
		case 3 ://周
			timeStr = "(" + yearSelect + "." + monthSelect + ")";
			break;
		case 4 ://月
			timeStr = "(" + startyear+"-"+startmonth + "---" + endyear+"-"+endmonth + ")";
			//timeStr = "(" + yearSelect + ")";
			break;
		case 5 :// 季度
			timeStr = "("  +yearSelect+"年"+"季度"+ ")";
			break;
		case 6 :// 半年
			timeStr = "("  +yearSelect+"年"+"半年"+ ")";
			break;
		case 7 :// 年
			timeStr = "(" + startyear + "年" + "---" + endyear +"年"+ ")";
			break;
	}
	// 检索条件的check
	if (checkSeachInfo(timeRadioType) == false) {
		return;
	}

	var params = {};
	params["alarmTotalType"] = alarmTotalType;
	params["timeRadioType"] = timeRadioType;
	params["STARTTIME"] = STARTTIME;
	params["ENDTIME"] = ENDTIME;
	params["startyear"] = startyear;
	params["startmonth"] = startmonth;
	params["endyear"] = endyear;
	params["endmonth"] = endmonth;
	params["monthSelect"] = monthSelect;
	params["yearSelect"] = yearSelect;
	params["jgbh"] = jgbh;
	params["departType"] = $('departType').value;
	if($("TSJGID").personId==null){
		params["TSJGID"] = "%' || TSJG || '%";
	}else{
		params["TSJGID"] = $("TSJGID").personId;
	}

	// 
	var url = "complaint.complaintmanage.statInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : function(xmlHttp) {
			$('msg').innerHTML = "<span class='currentLocationBold'>" + timeStr
					+ "各单位投诉量统计</span>";
			var xmlDoc = xmlHttp.responseXML;
			xmlDoc = xmlDoc.documentElement
			var rows = xmlDoc.childNodes;
			var zongji = 0;

			// var colPersent = parseInt(85/(colsNum+1),10);//每列所占百分比,首列占15%

			var len;
			if (alarmTotalType == 1) {
				switch (parseInt(timeRadioType)) {
					case 1 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1000px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 2 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=2300px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 3 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=800px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 4 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=760px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 5 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=760px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 6 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=760px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 7 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=760px cellSpacing=0 cellPadding=0>";
						;
						break;
					default :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1600px cellSpacing=0 cellPadding=0>";
						break;
				}
			}
			if (alarmTotalType == 2) {
				switch (parseInt(timeRadioType)) {
					case 1 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1000px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 2 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1600px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 3 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=800px cellSpacing=0 cellPadding=0>";
						;
						break;
					case 4 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1600px cellSpacing=0 cellPadding=0>";
						;
						break;
					default :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1600px cellSpacing=0 cellPadding=0>";
						break;
				}
			}
			if (alarmTotalType == 3) {
				switch (parseInt(timeRadioType)) {
					// case 1:var strTab = "<table id=\"tabVeh\"
					// class=\"scrollTable\" width=1000px cellSpacing=0
					// cellPadding=0>";;break;
					case 2 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1600px cellSpacing=0 cellPadding=0>";
						;
						break;
					// case 3:var strTab = "<table id=\"tabVeh\"
					// class=\"scrollTable\" width=800px cellSpacing=0
					// cellPadding=0>";;break;
					case 4 :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1200px cellSpacing=0 cellPadding=0>";
						;
						break;
					default :
						var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1600px cellSpacing=0 cellPadding=0>";
						break;
				}
			}
			// var strTab = "<table id=\"tabVeh\" class=\"scrollTable\"
			// width=1600px
			// cellSpacing=0 cellPadding=0>";
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
					strTab = strTab + "<td " + alignStyle + " value='"
							+ cols[k].text + "'>";
							showType = showType+cols[k].text+",";
							
					if (i == rows.length - 1 && k == cols.length - 1 && i != 0) {// --最后统计行
						strTab = strTab + "&nbsp;";
						zongji = cols[k].text;// 事故总数
						len = cols.length - 1;
					} else {
						if (i == 0) {// --表头
							if (k == 0) {
								strTab = strTab + "<input name='checkboxAll' id='checkboxAll' type='checkbox' onclick='clickAllCheckBox()' />全选   " + statTypeDesc;// 首行首列
							} else {
								strTab = strTab
										+ cols[k].text.replace('周', '周<br>');
							}
						} else {
							if (k == 0 && i != 0 && i != rows.length - 1) {// 除去首行和末行的，每行第一列加checkbox
								strTab += "<input name='boxname' type='checkbox' />";
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
	});
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
 * 
 * @param {}
 *            obj
 * @param {}
 *            color
 */
function mouseover(obj, color) {
	if (obj.bgColor != color) {
		obj.bgColor = "#117FC9";
	}
}

/**
 * 鼠标移出<br/>
 * 
 * @param {}
 *            obj
 * @param {}
 *            color
 */
function mouseout(obj, color) {
	if (obj.bgColor != color.toLowerCase()) {
		obj.bgColor = "#FFFFFF";
	}
}

/**
 * 检索结果的回显设置<br/>
 * 
 * @param {}
 *            xmlHttp
 */
function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	xmlDoc = xmlDoc.documentElement
	var rows = xmlDoc.childNodes;
	var zongji = 0;
	// var colPersent = parseInt(85/(colsNum+1),10);//每列所占百分比,首列占15%

	var len;
	var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1000px cellSpacing=0 cellPadding=0>";
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
				alignStyle = "style='text-align:center' ";
			} else {
				alignStyle = "style='text-align:left' ";
			}
			if (k == 0) {
				alignStyle += " class='scrollRowThead'";
			}
			strTab = strTab + "<td " + alignStyle + " value='" + cols[k].text
					+ "'>";
			if (i == rows.length - 1 && k == cols.length - 1 && i != 0) {// --最后统计行
				strTab = strTab + "&nbsp;";
				zongji = cols[k].text;// 事故总数
				len = cols.length - 1;
			} else {
				if (i == 0) {// --表头
					if (k == 0) {
						strTab = strTab + statTypeDesc;// 首行首列
					} else {
						strTab = strTab + cols[k].text.replace('周', '周<br>');
					}
				} else {
					if (k == 0 && i != 0 && i != rows.length - 1) {// 除去首行和末行的，每行第一列加checkbox
						strTab += "<input name='boxname' type='checkbox' />";
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
	}
	if (timeRadioType == "2") {
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
		/*if (datediff(end, start) > 31) {
			alert("统计日期间隔不能大于31天");
			return false;
		}*/

	}
	if (timeRadioType == "3") {
		/*
		var monthYear = $("yearId").value;
		var monthMonth = $("monthId").value;
		if (monthYear == "") {
			alert("请填写年");
			return false;
		}

		if (monthMonth == "") {
			alert("请填写月");
			return false;
		}

		if (parseInt(monthMonth, 10) > 12 || parseInt(monthMonth, 10) == 0) {
			alert("月份只能输入1-12的数字");
			return false;
		}
		*/
	}
	if (timeRadioType == "4") {
		/*
		var year = $("yearSelect").value;
		if (year == "") {
			alert("请填写年");
			return false;
		}
		*/
	}
	return true;
}
/**
 * 函数功能：判断两个日期间隔时间
 */
function datediff(d1, d2) {
	r1 = d1.split('-');
	r2 = d2.split('-');
	return parseInt(((new Date(r1[0], r1[1], r1[2])).valueOf() - (new Date(
			r2[0], r2[1], r2[2])).valueOf())
			/ (24 * 3600 * 1000));
}
/**
 * 函数功能:获取和组织页面数据，调用绘图函数绘图
 * 参数说明:title-标题;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);xDesc-横轴描述;yDesc-纵轴描述.
 * 调用实例:buildChart('重点车辆行为统计','line','卡口','频度')
 */
function periodFlowChart() {
	var allBoxs = document.getElementsByName("boxname");
	var boxs = new Array();
	for (var k = 0; k < allBoxs.length; k++) {
		if (allBoxs[k].checked == true) {
			boxs.push(allBoxs[k]);
		}
	}
	if (boxs.length == 0) {
		alert("请选择要生成图表的数据！");
		return;
	}
	var tds = "";
	var trObj = "";
	var DataForChartStr = "";
	var temStr = "";
	for (var i = 0; i < boxs.length; i++) {
		var trObj = boxs[i].parentNode.parentNode;
		tds = trObj.childNodes;
		temStr = "";// 清空上一个路口段流量载体
		// 组织一个路口段流量的数据，用于绘图
		for (var j = 1; j < tds.length; j++) {
			if (j != tds.length - 1) {
				var changeJ = j; // 临时变量，表示绘图的X轴上的值
            	changeJ = changeState(j); 

				if (j == tds.length - 2) {
					temStr += changeJ + "," + tds[j].value + "," + tds[0].value;
				} else {
					temStr += changeJ + "," + tds[j].value + "," + tds[0].value
							+ ";";
				}

			}
		}
		if (i == boxs.length - 1) {
			DataForChartStr += temStr;
		} else {
			DataForChartStr += temStr + ";";
		}
	}
	// alert(DataForChartStr);
	var title = "投诉信息统计图" + " " + timeStr;
	switch (parseInt($('alarmTotalRadio').value)) {
		case 1 :
			var chartType = "line";
			var xDesc = "时段";
			break;
		case 2 :
			var chartType = "bar";
			var xDesc = "流转状态";
			break;
		case 3 :
			var chartType = "bar";
			var xDesc = "投诉类别";
			break;
		default :
			var chartType = "line";
			var xDesc = "时段";
			break;
	}
	var yDesc = "投诉次数";
	chartType = $('chartType').value;
	doChart(title, chartType, xDesc, yDesc, DataForChartStr);
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
/**
 * 函数功能:显示统计图表,发送Ajax请求数据.
 * 参数说明:title-标题;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);xDesc-横轴描述;yDesc-纵轴描述;chartArray-统计图表数据.
 * 调用实例:buildChart('重点车辆行为统计','line','卡口','频度')
 */
function doChart(title, chartType, xDesc, yDesc, chartDataStr) {
	var title = title;
	var chartType = chartType;
	var xDesc = xDesc;
	var yDesc = yDesc;

	if (chartDataStr.length == 0) {
		alert("没有数据！");
		return;
	}
	var url = 'dispatch.cmaterialInfo.buildChart.d';
	url = encodeURI(url);
	var params = "title=" + title + "&chartType=" + chartType + "&xDesc="
			+ xDesc + "&yDesc=" + yDesc + "&chartDataStr=" + chartDataStr;
	params = encodeURI(params);
	new Ajax.Request(url, {
				method : 'post',
				parameters : params,
				onComplete : showImage
			});
}

/**
 * 函数功能：Ajax回调,解析请求返回的数据并展示出来.
 */
function showImage(xmlHttp) {
	var strText = xmlHttp.responseText;
	if (strText == "null") {
		Popup.prototype.hideTips();
		alert("没有可生成图表的数据！");
		return;
	}
	var splitLoc = strText.indexOf("|");
	var fileName = strText.substring(0, splitLoc);
	var graphURL = strText.substring(splitLoc + 1);
	var strlink = "../../../base/ChartView.jsp?fileName=" + fileName
			+ "&graphURL=" + graphURL;
	Popup.prototype.hideTips();
	window.showModalDialog(strlink, "", "dialogWidth:790px;dialogHeight:500px");
}