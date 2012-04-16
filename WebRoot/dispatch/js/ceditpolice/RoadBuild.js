var group = "new";
/** 
 * desc:新增或查询的跳转
 * param:
 * return:
 * author：wkz
 * date: 2010-1-12
 * version:
 */
function onRoadClick(itemId, itemValue, buildState, rpu, stype, flag) {
	var jgbh = $("jgbh").value;
	var jgid = $("jgid").value;
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("RoadBuildAdd.jsp?tmp="
				+ Math.random() + "&insrtOrUpdt=0", "",
				"dialogWidth:800px;dialogHeight:600px");
		if(flag == "all") {
			doSearchAll();
		} else if(flag == "sg") {
			doSearchRoadInfo();
		} else {
			if (group == "search") {
				doSearch(jgbh);
			} else {
				doOnLoad();
			}
		}
	}   
	if (id == "edit") {
		if (buildState == "570006") {
			alert("施工占道已解除，不能进行修改！");
			return;
		} else if (buildState == "570005") {
			if (jgid.substring(2, 4) != "00" && jgid != rpu) {
				alert("不是总队或者施工占道填报单位不能修改！");
				return;
			}
		}
		var returnValuestr = window.showModalDialog("RoadBuildAdd.jsp?tmp="
				+ Math.random() + "&insrtOrUpdt=1&alarmId=" + itemValue
				+ "&buildState=" + buildState, "",
				"dialogWidth:800px;dialogHeight:600px");
		
		if(flag == "all") {
			doSearchAll();
		} else if(flag == "sg") {
			doSearchRoadInfo();
		} else {
			if (group == "search") {
				doSearch(jgbh);
			} else {
				doOnLoad();
			}
		}
	}
	if (id == "view") {
		//Modified by Liuwx 2011-07-10
		//查看后更新为已经查看状态
		updateRoadBuildState(itemValue, "2", stype);
		//Modification finished
		window.showModalDialog("RoadBuildAdd.jsp?tmp=" + Math.random()
				+ "&insrtOrUpdt=2&alarmId=" + itemValue + "&buildState="
				+ buildState, "", "dialogWidth:800px;dialogHeight:600px");

		if(flag == "all") {
			doSearchAll();
		} else if(flag == "sg") {
			doSearchRoadInfo();
		} else {
			if (group == "search") {
				doSearch(jgbh);
			} else {
				doOnLoad();
			}
		}
	}
	if (id == "search") {
		currPage = 1;
		doSearch(itemValue);
	}
	if (id == "update") {
		if (buildState == "570006") {
			alert("施工占道已解除，不能重复解除！");
			return;
		} else if (buildState == "570005") {
			if (jgid != rpu && jgid.substring(2,4)!='00') {
				alert("不是施工占道填报单位不能解除！");
				return;
			}
		}
		doUpdate(itemValue);
	}
}

/** 
 * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路信息
 * param:
 * return:
 * author：wkz
 * date: 2010-4-14
 * version:
 */
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	if (checkParam() == false) {
		return;
	}
	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	var EVENTSOURCE = $('EVENTSOURCE');
	var EVENTTYPE = $('EVENTTYPE');
	var ALARMUNIT = $('ALARMUNIT');
	var TITLE = $('TITLE');
	var ALARMREGIONID = $('ALARMREGIONID');
	var ALARMREGION = $('ALARMREGION');
	var ROADNAME = $('ROADNAME');
	var KMVALUE = $('KMVALUE');
	var MVALUE = $('MVALUE');
	var EndKMVALUE = $('EndKMVALUE');
	var EndMVALUE = $('EndMVALUE');
	var CaseHappenTime = $('CaseHappenTime');
	var CaseEndTime = $('CaseEndTime');
	var REPORTUNIT = $('REPORTUNIT');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	var REPORTTIME = $('REPORTTIME');
	var PLAN = $('PLAN');

	//var ISALLCLOSE = $('ISALLCLOSE_0').checked?$('ISALLCLOSE_0'):$('ISALLCLOSE_1');
	var ROLANENUM = $('ROLANENUM');
	var ISHAVEPLAN = $('ISHAVEPLAN').checked ? "1" : "0";

	var RADIOTYPE = "";
	if ($('RADIOTYPE_1').checked) {
		RADIOTYPE = "0";
	} else if ($('RADIOTYPE_2').checked) {
		RADIOTYPE = "1";
	} else {
		RADIOTYPE = "2";
	}
	//Modified by Liuwx 2011-07-23
	var closeAll = "";
	if ($('ISALLCLOSE_0').checked) {
		closeAll = "0";
	} else if ($('ISALLCLOSE_1').checked) {
		closeAll = "1";
	}
	//Modification finished
	//Modified by Liuwx 2011-06-24
	//转发机构ID
	var jgmcId = $("jgmcId").value;
	if (jgmcId != "") {
		jgmcId = jgmcId.replace(/；/g, ",");
		jgmcId = jgmcId.substring(0, jgmcId.length - 1);
	}
	//Modification finished

	var params = {};
	params["ALARMID"] = ALARMID.value;
	params["ROADID"] = ROADID.value;
	params["EVENTSOURCE"] = EVENTSOURCE.value;
	params["EVENTTYPE"] = EVENTTYPE.value;
	params["ALARMUNIT"] = ALARMUNIT.value;
	params["TITLE"] = TITLE.value;
	params["ALARMREGIONID"] = ALARMREGIONID.value;
	params["ALARMREGION"] = ALARMREGION.value;
	params["ROADNAME"] = ROADNAME.value;
	params["KMVALUE"] = KMVALUE.value;
	params["MVALUE"] = MVALUE.value;
	params["EndKMVALUE"] = EndKMVALUE.value;
	params["EndMVALUE"] = EndMVALUE.value;
	params["CaseHappenTime"] = CaseHappenTime.value;
	params["CaseEndTime"] = CaseEndTime.value;
	params["REPORTUNIT"] = REPORTUNIT.value;
	params["REPORTPERSON"] = REPORTPERSON.value;
	params["REPORTTIME"] = REPORTTIME.value;
	params["PLAN"] = PLAN.value;
	//Modified by Liuwx 2011-07-23
//	params["ISALLCLOSE"] = ($("ISALLCLOSE").checked == true ? 1 : 0);
	params["ISALLCLOSE"] = closeAll;
	if(closeAll == "0"){
		params["ROLANENUM"] = ROLANENUM.value;
	}else if(closeAll == "1"){
		params["ROLANENUM"] = "";
	}
	//Modification finished
	params["ISHAVEPLAN"] = ISHAVEPLAN;
	params["RADIOTYPE"] = RADIOTYPE;
	params["insrtOrUpdt"] = insrtOrUpdt;
	//Modified by Liuwx 2011-06-24
	//转发机构ID
	params["ADCODE"] = jgmcId;
	//Modification finished
	//Modify by xiayx 2012-3-5
	//添加近期路况-传递参数
	params["recentRoadState"] = $roadState.get();
	//Modification finished

	var url = "roadbuild.roadbuildmanage.modifyRoadBuildInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showModifyResult
	});
}
function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc.indexOf('成功') > -1) {
		alert(xmlDoc);
		if ($(page).value == "") {
			window.location.reload();
		} else {
			window.close();
		}
	}else if(xmlDoc.indexOf('Exist') > -1){
        alert('该记录已经存在，请不要填写重复的内容。')
    } else {
		alert('操作失败，请重试！');
	}

	/*if(xmlDoc=="success"){
	alert("操作成功!");
	window.close();
	}else{
	alert(xmlDoc);
	}*/
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
	var num = 1;
	num = currPage;
	group = "search";
	//obj.options[obj.selectedIndex].text
	var sql = "select taa.ALARMID,taa.ALARMREGION,"
			+ " taa.roadid,"
			+ "taa.ROADNAME,taa.ROADDIRECTION,to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') CASEHAPPENTIME,"
			+ "to_char(taa.CASEENDTIME,'yyyy-mm-dd HH24:mi') CASEENDTIME,taa.eventstate,"
			+ "dic.begin,dic.end,taa.reportunit,tar.BUILDSTATE,'2' as stype ";
	sql += " from T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar,T_OA_DICTDLFX dic "
	sql += " where taa.ALARMID like '" + jgbh
			+ "%' and taa.ALARMID=tar.ALARMID and taa.roadid=dic.dlmc";

	//Modifieb by Liuwx 2011-07-06
	var sql2 = " SELECT taa.ALARMID, taa.ALARMREGION, taa.roadid,taa.ROADNAME,taa.ROADDIRECTION,"
			+ "TO_CHAR (taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi') CASEHAPPENTIME,"
			+ "TO_CHAR (taa.CASEENDTIME, 'yyyy-mm-dd HH24:mi') CASEENDTIME,"
			+ "taa.eventstate,dic.begin,dic.end,taa.reportunit,tar.BUILDSTATE,DECODE(toa.adid,null,'2','3') as stype ";
	sql2 += "FROM T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar,T_OA_DICTDLFX dic,t_oa_acceptdept toa ";
	sql2 += " WHERE taa.ALARMID = toa.aid AND taa.ALARMID = tar.ALARMID AND taa.roadid = dic.dlmc "
			+ "AND toa.atype = '3' AND toa.rpdcode = '" + $("jgid").value + "'";
	//Modification finished

	//Modify by Xiayx 2011-9-15
	//机构筛选时，包含本单位及其所有子单位
	if(window.G_jgID){
		var length;
		if(G_jgID.substring(2,4)=="00"){
			length = 2;
		}else if(G_jgID.substring(4,6)=="00"){
			length = 4;
		}else{
			length = 6;
		}
		sql += " and '"+G_jgID.substring(0,length)+"'=substr(taa.reportunit,1,"+length+")";
		sql2 += " and '"+G_jgID.substring(0,length)+"'=substr(taa.reportunit,1,"+length+")";
	}
	//Modification finished

	//	sql += $("AlarmUnit").value == "" ? "" : (" and (taa.ALARMREGION like '%"
	//			+ $("AlarmUnit").value + "%'";

	if ($('sj1').value != "" && $('sj2').value != "") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value
				+ " 00:00' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
		//Modifieb by Liuwx 2011-07-06
		sql2 += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value
				+ " 00:00' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
		//Modification finished
	} else if ($('sj1').value != "" && $('sj2').value == "") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value + " 00:00'";
		//Modifieb by Liuwx 2011-07-06
		sql2 += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value + " 00:00'";
	} else if ($('sj1').value == "" && $('sj2').value != "") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
		//Modifieb by Liuwx 2011-07-06
		sql2 += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
		//Modification finished
	}
	sql += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%"
			+ $("ROADNAME").value + "%'");
	//Modifieb by Liuwx 2011-07-06
	sql2 += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%"
			+ $("ROADNAME").value + "%'");
	//Modification finished
	sql += $("ISALLCLOSE").value == "全部" ? "" : (" and tar.ISALLCLOSE = '"
			+ $("ISALLCLOSE").value + "'");
	//Modifieb by Liuwx 2011-07-06
	sql2 += $("ISALLCLOSE").value == "全部" ? "" : (" and tar.ISALLCLOSE = '"
			+ $("ISALLCLOSE").value + "'");
	sql += " UNION " + sql2;
	sql += " ORDER BY CASEHAPPENTIME DESC";
	//Modification finished
	//$("divId").innerText = sql;
	//sql +="order by tori.ROADNAME";
	//prompt(sql,sql);
	//    alert(sql);
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
	if (num != 1 && num != 0) {
		sleep(250);
		onTurnToPage(num);
	}
}
function sleep(timeout) {
	window
			.showModalDialog("javascript:document.writeln('<script>window.setTimeout(function () { window.close(); }, "
					+ timeout + ");<\/script>');");
}

function showExcel(jgbh) {
	group = "search";
//	if (!validateInput()) {
//		return;
//	}
		
	//obj.options[obj.selectedIndex].text

	//Modifieb by Liuwx 2011-07-10
	var sql = "select taa.ALARMID,taa.ALARMREGION,"
			+ " taa.roadid,"
			+ "taa.ROADNAME,taa.ROADDIRECTION,to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') CASEHAPPENTIME,"
			+ "to_char(taa.CASEENDTIME,'yyyy-mm-dd HH24:mi') CASEENDTIME,(select name from t_attemper_code where id = taa.eventstate) eventstate,"
			+ "dic.begin,dic.end,taa.reportunit,tar.BUILDSTATE,'2' as stype ";
	sql += " from T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar,T_OA_DICTDLFX dic "
	sql += " where taa.ALARMID like '" + jgbh
			+ "%' and taa.ALARMID=tar.ALARMID and taa.roadid=dic.dlmc";

	var sql2 = " SELECT taa.ALARMID, taa.ALARMREGION, taa.roadid,taa.ROADNAME,taa.ROADDIRECTION,"
			+ "TO_CHAR (taa.CASEHAPPENTIME, 'yyyy-mm-dd HH24:mi') CASEHAPPENTIME,"
			+ "TO_CHAR (taa.CASEENDTIME, 'yyyy-mm-dd HH24:mi') CASEENDTIME,"
			+ "(select name from t_attemper_code where id = taa.eventstate) eventstate,dic.begin,dic.end,taa.reportunit,tar.BUILDSTATE,DECODE(toa.adid,null,'2','3') as stype ";
	sql2 += "FROM T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar,T_OA_DICTDLFX dic,t_oa_acceptdept toa ";
	sql2 += " WHERE taa.ALARMID = toa.aid AND taa.ALARMID = tar.ALARMID AND taa.roadid = dic.dlmc "
			+ "AND toa.atype = '3' AND toa.rpdcode = '" + $("jgid").value + "'";

	var unit = $("AlarmUnit").value;
	if (unit != "") {
		if (typeof jgbh == "string" && jgbh.length == 6) {
			sql += " and instr('" + unit + "',taa.ALARMREGION)!=-1";
			sql2 += " and instr('" + unit + "',taa.ALARMREGION)!=-1";
		} else {
			sql += " and taa.ALARMREGION like '%" + unit + "%'";
			sql2 += " and taa.ALARMREGION like '%" + unit + "%'";
		}
	}

	if ($('sj1').value != "" && $('sj2').value != "") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value
				+ " 00:00' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
		sql2 += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value
				+ " 00:00' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
	} else if ($('sj1').value != "" && $('sj2').value == "") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value + " 00:00'";
		sql2 += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
				+ $('sj1').value + " 00:00'";
	} else if ($('sj1').value == "" && $('sj2').value != "") {
		sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
		sql2 += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
				+ $('sj2').value + " 23:59'";
	}
	sql += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%"
			+ $("ROADNAME").value + "%'");
	sql2 += $("ROADNAME").value == "" ? "" : (" and taa.roadid like '%"
			+ $("ROADNAME").value + "%'");
	sql += $("ISALLCLOSE").value == "全部" ? "" : (" and tar.ISALLCLOSE = '"
			+ $("ISALLCLOSE").value + "'");
	sql2 += $("ISALLCLOSE").value == "全部" ? "" : (" and tar.ISALLCLOSE = '"
			+ $("ISALLCLOSE").value + "'");
	sql += " UNION " + sql2;
	sql += " ORDER BY CASEHAPPENTIME DESC";
	//Modification finished

	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;
	var url = "roadbuild.roadbuildmanage.showExcel.d?" + "searchSql=" + sql;
	url = encodeURI(url);
	window.open(url, "", "height=500,width=800,top=" + top + ",left=" + left
			+ ",toolbar=yes,menubar=yes,"
			+ "scrollbars=yes,resizable=yes,location=no,status=no");
}

function showResultsPage(xmldoc) {
	var jgid = $("jgid").value;
	var rows = xmldoc.getElementsByTagName("row");
	var results = null;
	var appid = $("appid").value;
	var jgbh = $("jgbh").value;
//	var str = "<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>";
	var str = "<div style='display:inline'>" + 
					"<div style='text-align:center;width:80%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span>" +
					"</div>" +
					"<div class='lsearch' style='float:right;'><a href='#' onclick=\"showExcel('"+jgbh+"')\" class='currentLocation'>" +
					"<span class='lbl'>导出Excel</span></a></div>" +
			  "</div>";
	str += "<div id='block'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='5%' class='td_r_b td_font'>序号</td>";
	str += "<td width='15%' class='td_r_b td_font'>填报单位</td>";
	str += "<td width='15%' class='td_r_b td_font'>道路名称</td>";
	str += "<td width='15%' class='td_r_b td_font'>路段名称</td>";
	str += "<td width='15%' class='td_r_b td_font'>方向</td>";
	str += "<td width='15%' class='td_r_b td_font'>施工开始时间</td>";
	str += "<td width='7%' class='td_r_b td_font'>施工状态</td>";
	str += "<td width='5%' class='td_r_b td_font'>查看</td>";
	if (appid == "1001") {
		str += "<td width='5%' class='td_r_b td_font'>处理</td>";
		//		str += "<td width='10%' class='td_r_b td_font'>解除占道</td>";
	}
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='9' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {

		for ( var i = 0; i < rows.length; i++) {
			results = rows[i].childNodes;
			var uIName = "";
			var dIName = "";
			if (results[7].text == "570005") {
				//
				if (jgid == results[10].text || jgid.substring(2,4)=="00") {
					uIName = "update.gif";
					dIName = "btndelete1.gif";
				} else {
					uIName = "lock.png";
					dIName = "lock.png";
				}
			} else if (results[7].text == "570006") {
				uIName = "lock.png";
				dIName = "lock.png";
			}

			str += "<tr align='center'>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
					+ (i + 1) + "</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"
					+ (results[1].text == "null" ? "" : results[1].text)
					+ "</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"
					+ (results[2].text == "null" ? "" : results[2].text)
					+ "</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"
					+ (results[3].text == "null" ? "" : results[3].text)
					+ "</td>";
			//			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"
					+ (results[4].text == "0" ? (results[8].text + "->" + results[9].text)
							: (results[9].text + "->" + results[8].text))
					+ "</td>";
			+(results[4].text == "null" ? "" : results[4].text) + "</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"
					+ (results[5].text == "null" ? "" : results[5].text)
					+ "</td>";
			//Modified by Liuwx 2011-06-22
			var stateImage = "";
			if (results[7].text == "570005") {
				stateImage = "busyState.png";
			} else if (results[7].text == "570006") {
				stateImage = "workState.png";
			}
			str += "<td width='7%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/state/"
					+ stateImage + "\"></td>"
			//Modification finished

			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
					+ "<input type=\"image\" src=\"../../images/button/para.gif\" "
					+ "onClick=\"onRoadClick('view','" + results[0].text
					+ "','" + results[7].text + "','" + results[10].text
					+ "','" + results[12].text + "')\"></td>"
			if (appid == "1001") {
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
						+ "<input type=\"image\" src=\"../../images/button/"
						+ uIName
						+ "\" onClick=\"onRoadClick('edit','"
						+ results[0].text
						+ "','"
						+ results[7].text
						+ "','"
						+ results[10].text + "')\"></td>"
				//				str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"
				//						+ "<input type=\"image\" src=\"../../images/button/"
				//						+ dIName + "\" " + "onClick=\"onRoadClick('update','"
				//						+ results[0].text + "','" + results[7].text + "','"+results[10].text+"')\"></td>"
			}
			str += "</tr>";
		}
	}
	str += "</table></div>";
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = str;
	Popup.prototype.hideTips();
}

/** 
 * desc:删除
 * param:
 * return:
 * author：wxt
 * date: 2010-01-16
 * version:
 */
function doUpdate(alarmid) {
	//Modified by Liuwx 2011-06-24
	//传输参数，以便在路面动态页面中显示动态的消息提示信息
	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	var EVENTSOURCE = $('EVENTSOURCE');
	var EVENTTYPE = $('EVENTTYPE');
	var ALARMUNIT = $('ALARMUNIT');
	var TITLE = $('TITLE');
	var ALARMREGIONID = $('ALARMREGIONID');
	var ALARMREGION = $('ALARMREGION');
	var ROADNAME = $('ROADNAME');
	var KMVALUE = $('KMVALUE');
	var MVALUE = $('MVALUE');
	var EndKMVALUE = $('EndKMVALUE');
	var EndMVALUE = $('EndMVALUE');
	var CaseHappenTime = $('CaseHappenTime');
	var CaseEndTime = $('CaseEndTime');
	var REPORTUNIT = $('REPORTUNIT');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	var REPORTTIME = $('REPORTTIME');
	var PLAN = $('PLAN');
	var insrtOrUpdt = $('insrtOrUpdt');
	//var ISALLCLOSE = $('ISALLCLOSE_0').checked?$('ISALLCLOSE_0'):$('ISALLCLOSE_1');
	var ROLANENUM = $('ROLANENUM');
	var ISHAVEPLAN = $('ISHAVEPLAN').checked ? "1" : "0";

	var RADIOTYPE = "";
	if ($('RADIOTYPE_1').checked) {
		RADIOTYPE = "0";
	} else if ($('RADIOTYPE_2').checked) {
		RADIOTYPE = "1";
	} else {
		RADIOTYPE = "2";
	}
	//Modification finished

	//Modified by Liuwx 2011-07-23
	var closeAll = "";
	if ($('ISALLCLOSE_0').checked) {
		closeAll = "0";
	} else if ($('ISALLCLOSE_1').checked) {
		closeAll = "1";
	}
	//Modification finished
	if (confirm("您确定要解除占道吗？")) { //提示用户是否删除记录                                       
		var strUrl = "roadbuild.roadbuildmanage.updateRoadBuildInfo.d"; //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = {};
		params["ALARMID"] = ALARMID.value;
		//Modified by Liuwx 2011-06-24
		//传输参数，以便在路面动态页面中显示动态的消息提示信息
		params["ROADID"] = ROADID.value;
		params["EVENTSOURCE"] = EVENTSOURCE.value;
		params["EVENTTYPE"] = EVENTTYPE.value;
		params["ALARMUNIT"] = ALARMUNIT.value;
		params["TITLE"] = TITLE.value;
		params["ALARMREGIONID"] = ALARMREGIONID.value;
		params["ALARMREGION"] = ALARMREGION.value;
		params["ROADNAME"] = ROADNAME.value;
		params["KMVALUE"] = KMVALUE.value;
		params["MVALUE"] = MVALUE.value;
		params["EndKMVALUE"] = EndKMVALUE.value;
		params["EndMVALUE"] = EndMVALUE.value;
		params["CaseHappenTime"] = CaseHappenTime.value;
		params["CaseEndTime"] = CaseEndTime.value;
		params["REPORTUNIT"] = REPORTUNIT.value;
		params["REPORTPERSON"] = REPORTPERSON.value;
		params["REPORTTIME"] = REPORTTIME.value;
		params["PLAN"] = PLAN.value;
		//Modified by Liuwx 2011-07-23
//		params["ISALLCLOSE"] = ($("ISALLCLOSE").checked == true ? 1 : 0);
		params["ISALLCLOSE"] = closeAll;
		params["ISALLCLOSE"] = closeAll;
		if(closeAll == "0"){
			params["ROLANENUM"] = ROLANENUM.value;
		}else if(closeAll == "1"){
			params["ROLANENUM"] = "";
		}
//		params["ROLANENUM"] = ROLANENUM.value;
		params["ISHAVEPLAN"] = ISHAVEPLAN;
		params["RADIOTYPE"] = RADIOTYPE;
		params["insrtOrUpdt"] = insrtOrUpdt;
		//Modification finished

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
	if (xmlHttp.responseText == 'success') {
		alert("解除占道成功！");
		window.close();
		if (group == "search") {
			doSearch($("jgbh").value);
		} else {
			doOnLoad();
		}
	} else {
		alert("解除占道失败!");
	}
}

/**
 *author:wkz
 *desc:编辑交通拥堵信息
 *date:2010-3-15
 */
function doOnLoad() {
	group = "new";
	var jgbh = $("jgbh").value;
	var sql = "select taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION,"
			+ "to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi'),"
			+ "to_char(taa.CASEENDTIME,'yyyy-mm-dd HH24:mi')";
	sql += " from T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tar"
	sql += " where taa.ALARMID like '" + jgbh
			+ "%' and taa.ALARMID=tar.ALARMID"
	sql += " AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') >= '"
			+ $('sj1').value
			+ " 00:00' AND to_char(taa.CASEHAPPENTIME,'yyyy-mm-dd HH24:mi') <= '"
			+ $('sj2').value + " 23:59'";
	sql += " order by taa.CASEHAPPENTIME desc";
	//prompt(sql,sql);
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
}

/**
 根据警情编号，事件类型查询详细信息
 */
function getRoadBuildInfo(alarmId, jgbh) {
	if ($('page').value == 1 || $('page').value == 2) {
		$("dispatchTr").show();
	}
	if (alarmId != "") {
		var url = "roadbuild.roadbuildmanage.getRoadBuildInfo.d"
		url = encodeURI(url);
		var params = {};
		params["ALARMID"] = alarmId;
		params["RPDCODE"] = $("jgid").value;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showGetXMLResponse
		});
	} else {
		pageInit();
	}
}

function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var ALARMID = $('ALARMID');
	var ROADID = $('alarmRoad_TrafficCrowd');
	var PLAN = $('PLAN');
	var ROADNAME = $('ROADNAME');
	var KMVALUE = $('KMVALUE');
	var MVALUE = $('MVALUE');
	var EndKMVALUE = $('EndKMVALUE');
	var EndMVALUE = $('EndMVALUE');
	var CaseHappenTime = $('CaseHappenTime');
	var CaseEndTime = $('CaseEndTime');
	var REPORTUNIT = $('REPORTUNITVALUE');
	var REPORTPERSON = $('REPORTPERSONVALUE');
	var REPORTTIME = $('REPORTTIME');
	//Modified by Liuwx 2011-07-23
	var ISALLCLOSE = $('ISALLCLOSE_0');
	//Modification finished
	var ROLANENUM = $('ROLANENUM');
	var ISHAVEPLAN = $('ISHAVEPLAN');
	var RADIOTYPE = $('RADIOTYPE_1');
	if (results[3].text == '0') {
		var RADIOTYPE = $('RADIOTYPE_1');
	}
	if (results[3].text == '1') {
		var RADIOTYPE = $('RADIOTYPE_2');
	}
	if (results[3].text == '2') {
		var RADIOTYPE = $('RADIOTYPE_3');
	}

	ALARMID.value = results[0].text;
	//ROADID.value = results[1].text;
	PLAN.value = results[2].text == " " ? "" : results[2].text;
	//ROADDIRECTION.value = results[3].text;
	RADIOTYPE.checked = true;
	ROADNAME.value = results[4].text;
	KMVALUE.value = results[5].text;
	EndKMVALUE.value = results[6].text;
	CaseHappenTime.value = results[7].text;
	CaseEndTime.value = results[8].text;
	REPORTUNIT.value = results[9].text;
	REPORTPERSON.value = results[10].text;
	REPORTTIME.value = results[11].text;
	//Modified by Liuwx 2011-07-23
//	ISALLCLOSE.checked = results[12].text == "1" ? true : false;
	if(results[12].text == '0'){
		var ISALLCLOSE = $('ISALLCLOSE_0');
		ROLANENUM.value = results[13].text;
	}else if(results[12].text == '1'){
		var ISALLCLOSE = $('ISALLCLOSE_1');
		ROLANENUM.value = "";
	}
	ISALLCLOSE.checked = true;
	//Modification finished
	ISHAVEPLAN.checked = results[14].text == "1" ? true : false;
	if (results[15].text == "570005") {
		$("caseETDesc").innerText = "施工预计结束时间";
	} else if (results[15].text == "570006") {
		$("caseETDesc").innerText = "施工结束时间";
	} else {
		//alert("e_p(eventstate:"+$("caseETDesc").innerText);
	}
	MVALUE.value = results[16].text;
	EndMVALUE.value = results[17].text;
//	Modify by Xiayx 2011-08-23
//	控制总队备注的显示
	var jgid = $("jgid").value;
	if(jgid.substring(2,4)=="00"){
		var page = $("page").value;
		var zodbzData = results[19].text;
		var zodbzTr = $("zodbzTr");
		var zodbz = $("zodbz");
		if (page == "1") {
			zodbzTr.style.display = "inline";
			zodbz.value = zodbzData;
		} else {
			if (zodbzData) {
				zodbzTr.style.display = "inline";
				var zodbzTd = $("zodbzTd");
				if(zodbzTd){
					zodbzTd.innerHTML = zodbzData;
				}
			}
		}
	}
	
//	Modify finished
	pageInit(results[18].text, results[1].text);
	var relieve = $("relieve");
	if (relieve) {
		if (($("jgid").value.substring(0, 6) == results[0].text.substring(0, 6) && results[15].text == "570005")
				|| $("jgid").value == "440000000000") {
			relieve.show();
			$("update").show();
		}
	}
//	$('zodbzTd').diabaled = true; 
	
	if(results[15].text == "570006") {
		if($('zodbzTd').innerText == "") {
			$('zodbzTr').style.display = "none";
			$('relieve').style.display = "none";
			$('update').style.display = "none";
		} else {
			$('zodbzTd').innerHTML = $('zodbz').value;
			$('relieve').style.display = "none";
			$('update').style.display = "none";
		}
	}	//Modified by Liuwx 2011-06-24
	//remark:xml节点
	//nodeId:需要显示内容的节点
	
	function showAccDept(remark, nodeId) {
		var accdeptstrs = [];
		for ( var i = 0; i <= 2; i++) {
			accdeptstrs[i] = "";
		}
		var accdepts = xmlDoc.getElementsByTagName(remark);
		if (accdepts != null && accdepts.length == 1) {
			accdepts = accdepts[0].childNodes;
			if (accdepts != null) {
				var sepName = "，";
				for ( var i = 0; i < accdepts.length; i++) {
					accdept = accdepts[i].childNodes;
					accdeptstrs[0] += sepName + accdept[2].text;
					if (accdept[5].text == "1") {
						accdeptstrs[1] += sepName + accdept[2].text;
					} else if (accdept[5].text == "2") {
						accdeptstrs[2] += sepName + accdept[2].text;
					} else if (accdept[5].text == "3") {//对于施工占道来说,只有查看,没有办结,故状态3亦视作查看。
						accdeptstrs[2] += sepName + accdept[2].text;
					}
				}
				for ( var i = 0; i < accdeptstrs.length; i++) {
					if (accdeptstrs[i] != "") {
						accdeptstrs[i] = accdeptstrs[i]
								.substring(sepName.length);
					}
				}
			}
		}
		var adstateStr = "";
		var adscolors = [ "red", "green", "blue" ];
		var cindexs = [ 0, 1 ];
		for ( var i = 0; i <= 1; i++) {
			if (accdeptstrs[i + 1] != "") {
				adstateStr += sepName + "<span style='color:"
						+ adscolors[cindexs[i]] + ";'>" + accdeptstrs[i + 1]
						+ "</span>";
			}
		}
		if (adstateStr != "") {
			adstateStr = adstateStr.substring(sepName.length);
		}
		if(adstateStr){
			$(nodeId).innerHTML = adstateStr;
		}else{
			$("dispatchTr").style.display = "none";
		}
	}
	//Modify by xiayx 2012-3-5
	//添加近期路况-显示近期路况
	showRecentRoadState(xmlDoc.getElementsByTagName("crowdremind"));
	//Modification finished
	showAccDept("dis", "dispatchTd");

	showDispatchButton(results[15].text, results[0].text);
	//Modification finished

}

function showRoadPath() {
	var roadId = $('alarmRoad_TrafficCrowd').value;
	/*
	if (!roadId)
		return;
	var jgId = $('REPORTUNIT').value;
	var url = "dispatch.croadPath.getRoadPaths.d";
	url = encodeURI(url);
	var params = "roadId=" + roadId + "&jgId=" + jgId;
	new Ajax.Request(
			url,
			{
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp) {
					var xmlDoc = xmlHttp.responseXML;
					var results = xmlDoc.getElementsByTagName("RFWin");
					var str = '';
					for (i = 0; i < results.length; i++) {
						var cols = results[i].getElementsByTagName("col");
						str += '<div><input type="radio" name="ROADDIRECTIONELE" id="'
						str += cols[0].text + '" style="margin-left:10px;" value="'
						str += ((cols[3].text == '0' ? '上行：' : '下行：') + cols[4].text) + '" />'
						str += (cols[3].text == '0' ? '上行：' : '下行：')
								+ cols[4].text + '</div>'
					}
					str += '<div><input type="radio" name="ROADDIRECTIONELE" value="双向" style="margin-left:10px;" />双向</div>'
					$('ROADDIRECTION').innerHTML = str;
					roadDirections = document
							.getElementsByName('ROADDIRECTIONELE');
					if (roadDirections)
						roadDirections[0].checked = true;
				}
			});
	 */
}

function selectRoad(roadId, roadDirection) {

	$('alarmRoad_TrafficCrowd').value = roadId;
	/*
	if (!roadId)
		return;

	var jgId = $('REPORTUNIT').value;
	var url = "dispatch.croadPath.getRoadPaths.d";
	url = encodeURI(url);
	var params = "roadId=" + roadId + "&jgId=" + jgId;
	new Ajax.Request(
			url,
			{
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp) {
					var xmlDoc = xmlHttp.responseXML;
					var results = xmlDoc.getElementsByTagName("RFWin");
					var str = '';
					//alert(xmlDoc.text)
				for (i = 0; i < results.length; i++) {
					var cols = results[i].getElementsByTagName("col");
					str += '<div><input type="radio" name="ROADDIRECTIONELE" id="'
					str += cols[0].text + '" style="margin-left:30px;" value="'
					str += ((cols[3].text == '0' ? '上行：' : '下行：') + cols[4].text) + '" />'
					str += (cols[3].text == '0' ? '上行：' : '下行：') + cols[4].text
							+ '</div>'
				}
				str += '<div><input type="radio" name="ROADDIRECTIONELE" value="双向" style="margin-left:30px;" />双向</div>'
				$('ROADDIRECTION').innerHTML = str;

				roadDirections = document.getElementsByName('ROADDIRECTIONELE');
				if (roadDirections)
					roadDirections[0].checked = true;

				for (i = 0; i < roadDirections.length; i++) {
					if (roadDirections[i].value == roadDirection) {
						roadDirections[i].checked = true;
						break;
					}
				}

			}
			});
	 * */
}

function DDCallBack() {
	var jgbh = $('jgbh').value;
	//alert(jgbh+"字符串长度: "+jgbh.length);
	//alert("字符串长度: "+jgbh.length);
	if (jgbh.length == 4) {
		//当前登录用户属于支队
		fillListBox("DD_DIV", "DD", "170",
				"select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"
						+ jgbh + "' and substr(jgid,0,6)<>'" + jgbh
						+ "00' order by jgid", "全部");
	}
	if (jgbh.length == 6) {
		//当前登录用户属于大队
		$("DD").value = $('jgid').value;
		$("DD").disabled = true;
	}
}

function isBulidOver() {
	var isOver = false;
	var page = $("page").value;
	if (page == "2") {
		if (p_bulidState == "570006") {
			isOver = true;
		}
	}
	return isOver;
}
function printWord() {
	var alarmId = p_alarmId;
	var time = p_time;
	var alarmTime = $("CaseHappenTime").value + "，";
	var alarmEndTime = isBulidOver() ? "已于" : "预计在";
	alarmEndTime += $("CaseEndTime").value + "结束，";
	var alarmAddress = $("alarmRoad_TrafficCrowd").options[$("alarmRoad_TrafficCrowd").selectedIndex].innerText
			+ $("ROADNAME").value
			+ "路段，从"
			+ $("KMVALUE").value
			+ "千米"
			+ $("MVALUE").value
			+ "米到"
			+ $("EndKMVALUE").value
			+ "千米"
			+ $("MVALUE").value + "米处，";
	var directions = document.getElementsByName("RADIOTYPE");//方向
	var direction = "";
	if (directions[0].checked == true) {
		direction = "rdForward";
	} else {
		direction = "rdBack";
	}
	direction = $(direction).innerText.replace("—>", "到") + "方向，";

	var alarmType = "施工占道";
	var content = isBulidOver() ? "进行过道路施工，道路施工" : "正在进行道路施工，";
	//Modified by Liuwx 2011-07-23
	var closeRoadTypes = document.getElementsByName("ISALLCLOSE");//是否全封闭
	var isAllClose = "";
	if (closeRoadTypes[0].checked == true) {
		isAllClose = "未全封闭";
	} else {
		isAllClose = "已全封闭";
	}
//	var isAllClose = $("ISALLCLOSE").checked ? "已全封闭" : "未全封闭";
	//Modification finished
	var useLaneNum = "占用" + $("ROLANENUM").value?$("ROLANENUM").value:"全部" + "车道，" + isAllClose + "，";
	var plan = $("ISHAVEPLAN").checked == true ? "已采取分流管制措施，措施如下："
			+ $("PLAN").value : "没有采取分流管制措施。";
	var fromUnit = $("REPORTUNITVALUE").value;
	var fromPerson = $("REPORTPERSONVALUE").value;
	var detailContent = alarmTime + alarmAddress + direction + content
			+ alarmEndTime + useLaneNum + plan;
	var params = "alarmId=" + alarmId + "&time=" + time + "&fromUnit="
			+ fromUnit + "&fromPerson=" + fromPerson + "&alarmType="
			+ alarmType + "&detailContent=" + detailContent;
	var form = document.createElement("form");
	form.action = "RoadBuildPrint.jsp";
	form.method = "post";
	form.style.display = "none";
	document.body.appendChild(form);
	params = params.split("&");
	for ( var i = 0; i < params.length; i++) {
		var item = params[i].split("=");
		var input = document.createElement("input");
		input.name = item[0];
		input.type = "text";
		input.value = item[1];
		form.appendChild(input);
	}
	form.submit();
}

function setReadOnly(page) {
	var jgid = $("jgid").value;
	var roadLevel = $("roadLevelValueId");
	var roadName = $("alarmRoad_TrafficCrowd");//道路名称
	var roadSecName = $("ROADNAME");//路段名称
	var directions = document.getElementsByName("RADIOTYPE");//方向
	//Modified by Liuwx 2011-07-23
//	var closeAll = $("ISALLCLOSE");//是否占用全部车道
	var closeAll = document.getElementsByName("ISALLCLOSE");
	//Modification finished
	var useLaneNum = $("ROLANENUM");//占用车道数
	var kmS = $("KMVALUE");//占用起点
	var mS = $("MVALUE");
	var kmE = $("EndKMVALUE");//占道终点
	var mE = $("EndMVALUE");
	var step = $("ISHAVEPLAN");//分流措施
	var stepDesc = $("PLAN");//分流措施描述
	var timeS = $("CaseHappenTime");//施工开始时间
	var timeE = $("CaseEndTime");//施工结束时间
	var writeUName = $("REPORTUNITVALUE");//填报单位名称
	var writePName = $("REPORTPERSONVALUE");//填报人姓名
	var writeTime = $("REPORTTIME");//填报时间
	var forwardDepts = $("jgmc"); //转发单位

	if (page == "") {
		roadLevel.disabled = false;
		roadName.disabled = false;
		roadSecName.disabled = false;
		for ( var i = 0; i < directions.length; i++) {
			directions[i].disabled = false;
		}
		//Modified by Liuwx 2011-07-23
		for ( var i = 0; i < closeAll.length; i++) {
			closeAll[i].disabled = false;
		}
//		closeAll.disabled = false;
		//Modification finished
		useLaneNum.disabled = false;
		kmS.disabled = false;
		mS.disabled = false;
		kmE.disabled = false;
		mE.disabled = false;
		step.disabled = false;
		stepDesc.disabled = false;
		timeS.disabled = false;
		timeE.disabled = false;
		writeUName.disabled = true;
		writePName.disabled = false;
		writeTime.disabled = true;
		if (forwardDepts) {
			forwardDepts.disabled = true;
		}
	} else if (page == "2") {
		roadLevel.disabled = true;
		roadName.disabled = true;
		roadSecName.disabled = true;
		for ( var i = 0; i < directions.length; i++) {
			directions[i].disabled = true;
		}
		//Modified by Liuwx 2011-07-23
		for ( var i = 0; i < closeAll.length; i++) {
			closeAll[i].disabled = true;
		}
//		closeAll.disabled = false;
		//Modification finished
		useLaneNum.disabled = true;
		kmS.disabled = true;
		mS.disabled = true;
		kmE.disabled = true;
		mE.disabled = true;
		step.disabled = true;
		stepDesc.disabled = true;
		timeS.disabled = true;
		timeE.disabled = true;
		writeUName.disabled = true;
		writePName.disabled = true;
		writeTime.disabled = true;
		if (forwardDepts) {
			forwardDepts.disabled = true;
		}
		//		if (closeAll.checked == false) {
		//			useLaneNum.style.display = "inline";
		//		} else if (closeAll.checked == true) {
		//			useLaneNum.style.display = "none";
		//		}
		//		var isShow = document.getElementById("planDescTrId");
		//		if (step.checked == true) {
		//			isShow.style.display = "inline";
		//		} else if (step.checked == false) {
		//			isShow.style.display = "none";
		//		}
		
	} else if (page == "1") {
		roadLevel.disabled = true;
		roadName.disabled = true;
		roadSecName.disabled = true;
		for ( var i = 0; i < directions.length; i++) {
			directions[i].disabled = true;
		}
		//Modified by Liuwx 2011-07-23
		for ( var i = 0; i < closeAll.length; i++) {
			closeAll[i].disabled = true;
		}
//		closeAll.disabled = false;
		//Modification finished
		useLaneNum.disabled = true;
		kmS.disabled = true;
		mS.disabled = true;
		kmE.disabled = true;
		mE.disabled = true;
		step.disabled = false;
		stepDesc.disabled = false;
		timeS.disabled = true;
		timeE.disabled = false;
		writeUName.disabled = true;
		writePName.disabled = true;
		writeTime.disabled = true;
		if (forwardDepts) {
			forwardDepts.disabled = true;
		}
		//		if (closeAll.checked == false) {
		//			useLaneNum.style.display = "inline";
		//		} else if (closeAll.checked == true) {
		//			useLaneNum.style.display = "none";
		//		}
		//		var isShow = document.getElementById("planDescTrId");
		//		if (step.checked == true) {
		//			isShow.style.display = "inline";
		//		} else if (step.checked == false) {
		//			isShow.style.display = "none";
		//		}
//		Modify by Xiayx 2011-08-23
//		总队可以填写总队备注
		if (jgid.substring(2, 4) == "00") {
			timeE.disabled = true;
			step.disabled = true;
			stepDesc.disabled = true;
			$("zodbzTr").style.display = "inline";
		}
//		Modify finished
	} else {
		alert("e_p(page):" + page);
	}

}

function getAddName() {
	var roadid = $("alarmRoad_TrafficCrowd").value;
	if (roadid == "") {
		return;
	}
	var url = "roadbuild.roadbuildmanage.getAddName.d";
	var params = "roadid=" + roadid;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showRoadDirection
	});
}

function showRoadDirection(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	var s = "";
	var e = "";
	if (xmlDoc != "") {
		var roadSEAdd = xmlDoc.split(",");
		s = roadSEAdd[0];
		e = roadSEAdd[1];
	} else {
		s = "不明确起点";
		e = "不明确终点";
	}
	$("rdForward").innerText = s + "—>" + e;
	$("rdBack").innerText = e + "—>" + s;
}

function onChangeValue() {
	// 取得道路名称
	var roadid = $("alarmRoad_TrafficCrowd").value;
	if (roadid == "") {
		return;
	}
	var url = "crowd.crowdmanage.getLcbptmisZt.d";
	var params = "roadName=" + roadid;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showSetCheckValue
	});
	
	getAddName();
}

var ls, le;
function showSetCheckValue(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0]
			.getElementsByTagName("col");
	maxRoadLength = results[0].text;
	minRoadLength = results[1].text;
	ls = minRoadLength;
	le = maxRoadLength;
	// 取得道路名称
	var roadid = $("alarmRoad_TrafficCrowd").value;
	var str = "广东省内" + roadid + "里程";
	if (maxRoadLength == "　" || minRoadLength == "　") {
		str += "数据未记录";
	} else {
		str += "为" + minRoadLength + "km 至" + maxRoadLength + "km";
	}
	$("showLength").innerHTML = str;
}

function checkParam() {
	var roadName = $("alarmRoad_TrafficCrowd");//道路名称
	if (roadName.value == "") {
		alert("请选择道路！");
		roadName.focus();
		return false;
	}

	var roadSecName = $("ROADNAME");//路段名称
	if (roadSecName.value == "") {
		alert("请输入路段名称！");
		roadSecName.focus();
		return false;
	}

	var regExp = new RegExp("^(0|([1-9][0-9]*))$");
	var kmS = $("KMVALUE");//占道起点
	if (kmS.value == "") {
		alert("请输入占道起点千米数！");
		kmS.focus();
		return false;
	}
	if (regExp.test(kmS.value) == false) {
		alert("占道起点千米数，请输入数字");
		kmS.value = "";
		kmS.focus();
		return false;
	}

	if (checkLocation(ls, le, "KMVALUE", "占道终点千米数") == false) {
		return false;
	}

	var mS = $("MVALUE");
	if (mS.value == "") {
		alert("请输入占道起点百米数！");
		mS.focus();
		return false;
	}
	if (regExp.test(mS.value) == false) {
		alert("占道起点百米数，请输入数字！");
		mS.value = "";
		mS.focus();
		return false;
	}
	var kmE = $("EndKMVALUE");//占道终点
	if (kmE.value == "") {
		alert("请输入占道终点千米数！");
		kmE.focus();
		return false;
	}
	if (regExp.test(kmE.value) == false) {
		alert("占道终点千米数，请输入数字！")
		kmE.value = "";
		kmE.focus();
		return false;
	}
	if (checkLocation(ls, le, "EndKMVALUE", "占道终点千米数") == false) {
		return false;
	}
	var mE = $("EndMVALUE");
	if (mE.value == "") {
		alert("请输入占道终点百米数！");
		mE.focus();
		return false;
	}
	if (regExp.test(mE.value) == false) {
		alert("占道终点百米数，请输入数字！")
		mE.value = "";
		mE.focus();
		return false;
	}
    //Modified by Liuwx at 2012年3月9日16:08:15
    if($('RADIOTYPE_1').checked){
        if ((Number(kmS.value) * 1000 + Number(mS.value)) > (Number(kmE.value) * 1000 + Number(mE.value))) {
            alert("该方向占道起点里程数不得大于占道终点里程数！");
            kmE.focus();
            return false;
        }
    }else if($('RADIOTYPE_2').checked){
        if ((Number(kmS.value) * 1000 + Number(mS.value)) < (Number(kmE.value) * 1000 + Number(mE.value))) {
            alert("该方向占道起点里程数不得小于占道终点里程数！");
            kmS.focus();
            return false;
        }
    }
    if ((Number(kmS.value) * 1000 + Number(mS.value)) == (Number(kmE.value) * 1000 + Number(mE.value))) {
        alert("占道起点里程数不能与占道终点里程数相同！");
        kmE.focus();
        return false;
    }
    //Modification finished

	var directions = document.getElementsByName("RADIOTYPE");//方向
	var isChecked = false;
	for ( var i = 0; i < directions.length; i++) {
		if (directions[i].checked == true) {
			isChecked = true;
			break;
		}
	}
	if (isChecked == false) {
		alert("请选择施工方向！")
		directions[0].focus();
		return false;
	}

	var regExp = new RegExp("^0|([1-9][0-9]*)$");
	//Modified by Liuwx 2011-07-23
	var rbt = document.getElementById("ISALLCLOSE_0");
	var isCloseAll = false;
	if(rbt.checked == true){
		isCloseAll = true;
	}
//	var closeAll = $("ISALLCLOSE");//是否占用全部车道
	//Modification finished
	var useLaneNum = $("ROLANENUM");//占用车道数
	//if (closeAll.checked == false) {
	if (useLaneNum.value == "" && isCloseAll == true) {
		alert("请输入占用车道数！");
		useLaneNum.focus();
		return false;
	}
	if (regExp.test(useLaneNum.value) == false && isCloseAll == true) {
		alert("占用车道数，请输入数字");
		useLaneNum.value = "";
		useLaneNum.focus();
		return false;
	}
	//}

	var timeS = $("CaseHappenTime");//施工时间
	if (timeS.value == "") {
		alert("请输入施工开始时间！");
		timeS.focus();
		return false;
	}

	var timeE = $("CaseEndTime");//施工时间
	if (timeE.value == "") {
		alert("请输入施工结束时间！");
		timeE.focus();
		return false;
	}
	
	if(timeS.value > timeE.value){
		alert("施工开始时间不能大于施工预计结束时间！");
		timeE.focus();
		return false;
	}

	var step = $("ISHAVEPLAN");//分流措施
	var stepDesc = $("PLAN");//分流措施描述
	if (stepDesc.innerText == "") {
		alert("请输入分流管制措施！");
		stepDesc.focus();
		return false;
	}
	
	//Modify by xiayx 2012-3-5
	//添加近期路况-参数验证
	//0：新增；1：处理；2：查看
	if(!$roadState.check()){
		return false;
	}
	//Modification finished

	var writeUName = $("REPORTUNITVALUE");//填报单位名称
	if (writeUName.value == "") {
		alert("请输入填报单位名称！");
		writeUName.focus();
		return false;
	}
	var writePName = $("REPORTPERSONVALUE");//填报人姓名
	if (writePName.value == "") {
		alert("请输入填报人姓名！");
		writePName.focus();
		return false;
	}
	var writeTime = $("REPORTTIME");//填报时间
	if (writeTime.value == "") {
		alert("请输入填报时间！");
		writeTime.focus();
		return false;
	}
	
	if(!validateInput()) {
		return false;
	}
	
	return true;

}

function resetAllValue() {
	$("alarmRoad_TrafficCrowd").value = "";
	$("ROADNAME").value = "";
	$("KMVALUE").value = "";
	$("MVALUE").value = "";
	$("EndKMVALUE").value = "";
	$("EndMVALUE").value = "";
	$("ROLANENUM").value = "";
	$("ISALLCLOSE_1").checked = false;
	$("PLAN").value = "";
	$("REPORTPERSONVALUE").value = "";
}

function checkLocation(s, e, lid, desc) {
	return true;
	var iscs = true;
	var k = Number($(lid).value);
	if (s != "　") {
		s = Number(s);
		if (k < s) {
			$(lid).focus();
			alert(desc + "小于" + s);
			return false;
		}
	}
	if (e != "　") {
		e = Number(e);
		if (k > e) {
			$(lid).focus();
			alert(desc + "大于" + e);
			return false;
		}
	}
	return iscs;
}

var Road = {
	init : true,
	roadLevel : {
		def : "1",
		init : function(def) {
			var labels = [ "高速公路", "国道", "省道" ];
			var codes = [ 1, 2, 3 ];
			var select = $("roadLevelValueId");
			for ( var i = 0; i < labels.length; i++) {
				var option = document.createElement("option");
				option.label = labels[i];
				option.value = codes[i];
				select.add(option);
			}
			var options = select.options;
			for ( var i = 0; i < options.length; i++) {
				options[i].innerText = options[i].label;
			}
			if (def) {
				select.value = def;
			} else if (Road.roadLevel.def) {
				select.value = Road.roadLevel.def;
			}
		},
		onchange : function(e) {
			Road.roadName.init(e.value);
		}
	},
	roadName : {
		level : "1",
		defs : [ "", "", "" ],
		init : function(level, def) {
			var cid = "alarmRoad_TrafficCrowd_td";
			var roadName = "alarmRoad_TrafficCrowd";
			var dlmc = [ "gbdm||':'||dlmc", "dlmc", "dlmc" ];
			if (!level) {
				level = Road.roadName.level;
			}
			var sql = "select dlmc id, " + dlmc[Number(level) - 1] + " mc  "
					+ "from T_OA_DICTDLFX where roadlevel='" + level
					+ "' order by mc";
			fillListBox(cid, roadName, "160", sql, "请选择",
					"Road.roadName.callBack('" + roadName + "','" + def + "')",
					"onChangeValue");
		},
		callBack : function(id, def) {
			$("alarmRoad_TrafficCrowd_td").innerHTML = $("alarmRoad_TrafficCrowd").outerHTML
					+ "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
			var select = $(id);
			if (select != null) {
				var options = select.options;
				if (options.length > 0) {
					select.removeChild(select.options[0]);
				}
				if (def && def != "undefined") {
					select.value = def;
				} else {
					var def = Road.roadName.defs[Number(Road.roadName.level) - 1];
					if (def && def != "") {
						select.value = def;
					}
				}
				onChangeValue();
				if (Road.init == true) {
					setReadOnly($("page").value);
					Road.init = false;
				}
			}
		}
	}
}

function pageInit(level, def) {
	Road.roadLevel.init(level);
	Road.roadName.init(level, def);
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

//Modified by Liuwx 2011-06-24
function dispatch() {
	Flow.showDispatch($("ALARMID").value, $("jgid").value, "jgmcId", 135, 270);
}

function showDispatchButton(state, id, rpu) {
	var jgbh = $("jgbh").value;
	var page = $("page").value;
	if (page == "1" && state == "570005" && jgbh.length == 2) {
		$("dispatch").style.display = "inline";
	}
}

/**
 * 施工占道状态的更新<br/>
 */
function updateRoadBuildState(alarmId, state, stype) {
	// 警情状态的更新方法的呼出
	var url = "dispatch.accdept.updateState.d";
	url = encodeURI(url);
	var params = {};
	params["id"] = alarmId;
	params["state"] = state;
	params["stype"] = stype;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : function(xmlHttp) {
		}
	});
}
//Modified by Liuwx 2011-07-23
function onRadioSelectChange(){
	var obj = $("ISALLCLOSE_1");
	if(obj !=undefined && obj != null){
		if(obj.checked){
			$("ROLANENUM").value = "";
			$("ROLANENUM").readOnly = true;
			$("ROLANENUM").disabled = true;
		}else{
			$("ROLANENUM").readOnly = false;
			$("ROLANENUM").disabled = false;
		}
	}
}

function updateZodbz(){
	var zodbz = $("zodbz");
	if(zodbz.value == ""){
		alert("请填写总队备注！");
		zodbz.focus();
		return false;
	} else if(zodbz.value.length >= 2000){
		alert("总队备注字符长度超过限制！");
		zodbz.focus();
		return false;
	}
	var aid = $("ALARMID").value;
	var url = "roadbuild.roadbuildmanage.updateZodbz.d";
	new Ajax.Request(url,{
		method:"post",
		parameters:{aid:aid,zodbz:zodbz.value},
		onComplete:function(xmlHttp){
			var result = xmlHttp.responseText;
			if(result == "true"){
				alert("保存总队备注成功！");
				window.close();
			}else if(result == "false"){
				alert("保存总队备注失败！");
			}
		}
	});
}

function showMoreCondition(){
	if($('moreConditionTr').visible()){
		$('defalutTitleTr').show();
		$('moreConditionTr').hide();
        $('simpleButtomTd').show();
        $('moreButtomTr').hide();
		$('dlmc_lable').hide();
		$('ROADNAME').hide();
//		$('moreLable').innerHTML = "高级查询";
		clearElementValue();
	}else{
		$('defalutTitleTr').hide();
		$('moreConditionTr').show();
        $('simpleButtomTd').hide();
        $('moreButtomTr').show();
		$('dlmc_lable').show();
		$('ROADNAME').show();
//		$('moreLable').innerHTML = "简单查询";
	}
}

function clearElementValue(){
    G_jgID = "";
	$('ROADNAME').value = "";
	$('AlarmUnit').value = "";
	$('ISALLCLOSE').options[0].selected = true;
}

function showRecentRoadState(reminds){
	if(reminds && reminds.length > 0){
		var remindObjects = BaseObject.getDatas(reminds);
		var history = $("recentRoadStateHistory");
		for(var i=0;i<remindObjects.length;i++){
			var object = remindObjects[i];
			var textarea = textareaRecentRoadState(object.remindinfo,object.reminddepartment,object.remindperson,object.reminddate);
			history.appendChild(textarea);
		}
		history.style.display = "block";
	}
	var page = $("page").value;
	var newest = $("recentRoadState");
	if(page == "2"){
		if(!reminds || reminds.length == 0){
			newest.parentNode.parentNode.style.display = "none";
		}else{
			newest.style.display = "none";
			$("ul_roadstate").style.display = "none";
		}
	}
}

function textareaRecentRoadState(info,jgmc,pname,time){
	var textarea = document.createElement("textarea");
	textarea.className = "copy_text copy_text_indent";
	textarea.style.marginTop = "10px";
	textarea.value = info + "\n" + textRecord(jgmc,pname,time);
	return textarea;
}

function textRecord(jgmc,pname,time){
	var infos = [];
	for(var i=0;i<arguments.length;i++){
		if(arguments[i]){
			infos.push(arguments[i]);
		}
	}
	return "["+infos.join("  ")+"]";
}

function getRecentRoadState(){
	var desc = "近期"+$("recentRoadStateDesc").innerText;
	var select = $("recentRoadState");
	if(select.value != "4"){
		desc += select.options[select.selectedIndex].innerText;
	}else{
		var rrsElse = $("recentRoadStateElse").value;
		if(rrsElse){
			desc += rrsElse;
		}else{
			desc = "";
		}
	}
	return desc;
}

//Modified by Liuwx 2012-3-29 10:54:12
/*
 * 是否显示计分栏及计分按钮
 * @param isScoring
 */
function showScoring(isScoring){
    if(isScoring == '1' || isScoring == '2'){
        $('scoringTR').style.display = 'inline';
        if(isScoring == '2'){
            $('scoringValueTD').disabled = false;
            $('scoringBtn').style.display = 'inline';
        }else{
            $('scoringValueTD').disabled = true;
            $('scoringBtn').style.display = 'none';
        }
    }else{
        $('scoringTR').style.display = 'none';
    }
}
/**
 * 信息计分或者不计分
 * @param isScoring
 */
function scoring(isScoring){
//do scoring...
}
//Modification finished

