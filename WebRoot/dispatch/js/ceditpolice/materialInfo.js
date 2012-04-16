/**
 *author:dxn
 *desc:加载路段管理查询页面信息
 *date:2010-4-2
 */
var buttonPressed;
var state;
var address;

function doOnLoad(jgbh) {
	$("jgmc").value = "";
	$("tbr").value = "";
	$("tbsj").value = "";
	$("dlmc").value = "";
	$("ldmc").value = "";
	$("sjlb").value = "";
	var jgid = $('jgid').value;
	jgbh = jgbh ? jgbh : $('jgbh').value;
	var str = '';

	if (jgbh.length == 4) {

		var sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al0.reportunit)";
		sql += " as reportunit, al0.reportperson, op.RESPONSIBLEPERSON, to_char(al0.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
		sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc,op.OPERATEID,(select p.OPERATEID from T_OA_PROCESS p where p.ywid = al.alarmid and p.reportkind = '004033') from T_ATTEMPER_ALARM_ZD";
		sql += " al, T_ATTEMPER_ALARM al0, T_ATTEMPER_ACCIDENT ac, T_OA_PROCESS op where al.alarmid = ";
		sql += "ac.alarmid and al.alarmid=al0.alarmid and al.alarmid = op.ywid and op.reportkind = '004032' and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
		sql += " = '001024' and al.reportperson is not null and al.REPORTUNIT = (case when ";
		sql += "substr('" + jgid
				+ "', 3, 4) = '0000' then al.REPORTUNIT when substr('" + jgid
		sql += "', 5, 2) = '00' and substr(al.REPORTUNIT, 1, 4) = substr('"
				+ jgid + "', 1, 4)";
		sql += " then al.REPORTUNIT else '" + jgid
				+ "' end) order by al.reporttime desc";

	} else {

		if (jgbh.length == 2)
			str = '_zd'

		var sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
		sql += " as reportunit, al.reportperson, op.RESPONSIBLEPERSON, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
		sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc,op.OPERATEID,(select p.OPERATEID from T_OA_PROCESS p where p.ywid = al.alarmid and p.reportkind = '004033') from T_ATTEMPER_ALARM";
		sql += str + " al, T_ATTEMPER_ACCIDENT" + str
				+ " ac, T_OA_PROCESS op where al.alarmid = ";
		sql += "ac.alarmid and al.alarmid = op.ywid and op.reportkind = '004032' and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
		sql += " = '001024' and al.reportperson is not null and al.REPORTUNIT = (case when ";
		sql += "substr('" + jgid
				+ "', 3, 4) = '0000' then al.REPORTUNIT when substr('" + jgid
		sql += "', 5, 2) = '00' and substr(al.REPORTUNIT, 1, 4) = substr('"
				+ jgid + "', 1, 4)";
		sql += " then al.REPORTUNIT else '" + jgid
				+ "' end) order by al.reporttime desc";
	}

	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
}
/** 
 * desc:查询
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function doSearch(jgbh) {
	var num = 1;
	num = currPage;
	var jgmc = $("jgmc") ? $("jgmc").value : null;
	var tbr = $("tbr") ? $("tbr").value : null;
	var tbsjStart = $("tbsjStart") ? $("tbsjStart").value : null;
	var tbsjEnd = $("tbsjEnd") ? $("tbsjEnd").value : null;
	var dlmc = $("dlmc") ? $("dlmc").value : null;
	var ldmc = $("ldmc") ? $("ldmc").value : null;
	var sjlb = $("sjlb") ? $("sjlb").value : null;
	var jgid = $('jgid') ? $('jgid').value : null;
	var searchAlarmId = $('searchAlarmId') ? $('searchAlarmId').value : null;
	var zhiduiID = jgid.substring(0, 4) + "00000000"
	var zongduiID = jgid.substring(0, 2) + "0000000000"
	//	jgbh = jgbh?jgbh:$('jgbh').value;
	var acdeptsql = "(select id from t_oa_acceptdept where aid=al.alarmid and rpdcode='440000000000' and adid is null)";
	var whereJg = " or (instr(f_get_accdept(al.alarmid,3,1),'" + jgid + "')!=0)";
	var sql = "";
	if (jgbh.length == 6) {
		var state = "decode(f_get_accsjgid(al.alarmid,'"
				+ jgid
				+ "'),null,(select name from t_attemper_code where id=al.EVENTSTATE),'"
				+ zhiduiID + "','支队下发','" + zongduiID + "','总队转发')";
		sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
		sql += " as reportunit, al.reportperson, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
		sql += state
				+ " as lc,al.EVENTSTATE, (select jgid from t_sys_department where jgid=al.reportunit) as reportunitID,al.GXDD "
				+ " from ";
		sql += "  T_ATTEMPER_ACCIDENT ac, T_ATTEMPER_ALARM al where ";
		sql += " al.alarmid = ac.alarmid and al.EVENTSTATE in('004032','004033','004042','004034')  and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
		sql += " = '001024' and (subStr(al.REPORTUNIT,0,6) = '" + jgbh + "'"
				+ whereJg + ")";
	} else {
		if (jgbh.length == 4) {
			sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
			sql += " as reportunit, al.reportperson, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
			sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc,al.EVENTSTATE,(select jgid from t_sys_department where jgid=al.reportunit) as reportunitID,al.GXDD from ";
			sql += "  T_ATTEMPER_ACCIDENT_ZD ac,  T_ATTEMPER_ALARM_zd al where ";
			sql += "  al.alarmid = ac.alarmid and al.EVENTSTATE in('004031','004033','004042','004043','004034','004035','004037','004036','004032') and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
			sql += " = '001024' and (subStr(al.ACCEPTUNIT,0,4) = '" + jgbh
					+ "'" + whereJg + ")";
		} else if (jgbh.length == 2) {
			sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
			sql += " as reportunit, al.reportperson, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
			sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc, al.EVENTSTATE,al.GXDD from ";
			sql += "   T_ATTEMPER_ACCIDENT_ZD ac,  T_ATTEMPER_ALARM_zd al where ";
			sql += " al.alarmid = ac.alarmid and al.EVENTSTATE in('004031','004034','004035','004036','004037','004043') and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
			sql += " = '001024' and (al.REPORTUNIT = al.REPORTUNIT" + whereJg
					+ ")";
		}
	}

	var searchDepID = "";
	if ($("depUnitId")) {
		searchDepID = $("depUnitId").value;
	}
	if (searchDepID != "") {
		var depSub = "";
		if ("0000" == searchDepID.substring(2, 6)) {
			depSub = searchDepID.substring(0, 2);
		} else if ("00" == searchDepID.substring(4, 6)) {
			depSub = searchDepID.substring(0, 4);
		} else {
			depSub = searchDepID.substring(0, 6);
		}
		// 查询单位"总队"时的处理
		if (depSub.length == 2) {
			sql += " and al.ALARMREGIONID like '" + depSub + "%' ";
			// 查询单位"支队"时的处理
		} else if (depSub.length == 4) {
			sql += " and al.ALARMREGIONID like '" + depSub + "%' ";
			// 查询单位"大队"时的处理
		} else {
			sql += " and al.ALARMREGIONID = '" + searchDepID + "' ";
		}
	}

	if (searchAlarmId) {
		sql += " and al.alarmid like '%" + searchAlarmId + "%'";
	}
	if (tbr) {
		sql += " and al.reportperson like '%" + tbr + "%'";
	}
	if ($('tbsjStart') && $('tbsjEnd')) {
		if ($('tbsjStart').value != "" && $('tbsjEnd').value != "") {
			sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') >= '"
					+ $('tbsjStart').value + " 00:00"
					+ "' AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') <= '"
					+ $('tbsjEnd').value + " 23:59'";
		} else if ($('tbsjStart').value != "" && $('tbsjEnd').value == "") {
			sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') >= '"
					+ $('tbsjStart').value + " 00:00'";
		} else if ($('tbsjStart').value == "" && $('tbsjEnd').value != "") {
			sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') <= '"
					+ $('tbsjEnd').value + "' 23:59";
		}
	}

	if (dlmc) {
		sql += " and al.TITLE like '%" + dlmc + "%'";
	}
	if (ldmc) {
		sql += " and al.EVENTSTATE = '" + ldmc + "'";
	}
	if (sjlb) {
		if (sjlb == '99') {
			sql += " and ac.DEATHPERSONCOUNT > 3 ";
		} else {
			sql += " and ac." + sjlb + " = '1'";
		}
	}

	sql += " order by al.reporttime desc";
	if (waitSearchFlg) {
		sql = waitSearchSql;
		PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
				"showResultsPage", "10");
	} else {
		PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
				"showResultsPage", "7");
	}
	if (num != 1 && num != 0) {
		sleep(250);
		onTurnToPage(num);
	}
}
function sleep(timeout) {
	window.showModalDialog("javascript:document.writeln('<script>window.setTimeout(function () { window.close(); }, "
					+ timeout + ");<\/script>');");
}

//function sleep(millis) {    var notifier = NjsRuntime.createNotifier();    setTimeout(notifier, millis);    notifier.wait->();} 

var waitSearchFlg = false;
var waitSearchSql = "";

function waitReceipt(jgbh) {
	var waitSql = "";
	waitSql = "select a1.alarmid, a1.title, (select jgmc from t_sys_department where jgid=a1.reportunit)";
	waitSql += " as reportunit, a1.reportperson, to_char(a1.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
	waitSql += "(select name from t_attemper_code where id=a1.EVENTSTATE) as lc,a1.EVENTSTATE "
			+ " from T_ATTEMPER_ALARM_zd a1";
	if (jgbh.length == 4) {
		waitSql = waitSql + " where a1.EVENTSTATE in('004033','004042') ";
		waitSql = waitSql
				+ " and a1.EVENTTYPE = '001024' and subStr(a1.reportunit,0,4) = '"
				+ jgbh + "'" + " order by reporttime desc";
	}
	if (jgbh.length == 2) {
		waitSql = waitSql + " where a1.EVENTSTATE in('004034','004043') ";
		waitSql = waitSql
				+ " and a1.EVENTTYPE = '001024'  order by reporttime desc ";
	}
	waitSearchFlg = true;
	waitSearchSql = waitSql;
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(waitSql),
			"showResultsPage", "10");
}
var message = "";
function showResultsPage(xmldoc) {
	var rows = xmldoc.getElementsByTagName("row");
	var results = null;
	var appid = $("appid").value;
	var jgbh = $('jgbh').value;
	var page = $('page');
	var str = page ? ""
			: "<div style='display:inline'>" + 
					"<div style='text-align:center;width:80%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span>" +
					"</div>" +
					"<div class='lsearch' style='float:right;'><a href='#' onclick=\"showExcelInfo('search','"+jgbh+"')\" class='currentLocation'>" +
					"<span class='lbl'>导出Excel</span></a></div>" +
			  "</div>";
	str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='4%' class='td_r_b td_font'>编号</td>";
	str += "<td width='12%' class='td_r_b td_font'>事故标题</td>";
	str += "<td width='12%' class='td_r_b td_font'>填报单位</td>";
	str += "<td width='6%' class='td_r_b td_font'>填报人</td>";
	//str += "<td width='6%' class='td_r_b td_font'>审批人</td>";
	str += "<td width='13%' class='td_r_b td_font'>填报时间</td>";
	str += "<td width='16%' class='td_r_b td_font'>状态</td>";
	str += "<td width='4%' class='td_r_b td_font'>查看</td>";

	if (appid == "1001") {
		if ((jgbh.length == 6) || (jgbh.length == 4)) {
			str += "<td width='8%' class='td_r_b td_font'>处理</td>";
		} else if (jgbh.length == 2) {
			str += "<td width='8%' class='td_r_b td_font'>签收/下发</td>";
		}
		// 只有大队和支队才能新增续报
		if ((jgbh.length == 6) || (jgbh.length == 4))
			str += "<td width='8%' class='td_r_b td_font'>新建续报</td>";
		// 大队，支队，总队能看续报版本
		//		if((jgbh.length == 4) || (jgbh.length == 2) || (jgbh.length == 6)) str += "<td width='8%' class='td_r_b td_font'>续报版本</td>";
		// if(jgbh.length == 6)	str += "<td width='4%' class='td_r_b td_font'>删除</td>";
		//Modify by xiayx 2012-1-17
		//事故新增删除功能
		if(jgbh.length == 6 || jgbh.length == 4){
			str += "<td width='5%' class='td_r_b td_font'>删除</td>";
		}
		//Modification finished
	}
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='"
				+ (jgbh.length == 6 ? 12 : (jgbh.length == 2 ? 10 : 12))
				+ "' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		for ( var i = 0; i < rows.length; i++) {
			flag2 = true;
			results = rows[i].childNodes;
			if (jgbh.length == 4) {
				if (results[7].text != (jgbh + "00000000")
						&& results[6].text == "004032") {
					continue;
				}
			}
			var showStr3 = "";
			var showStr4 = "";
			var showStr5 = "";
			if (results[3].text == "null" || results[3].text == "") {
				showStr3 = "-";
			} else {
				showStr3 = results[3].text;
			}
			if (results[4].text == "null" || results[4].text == "") {
				showStr4 = "-";
			} else {
				if (jgbh.length == 6) {
					showStr4 = "-";
				} else {
					showStr4 = results[4].text;
				}
			}
			if (results[5].text == "null" || results[5].text == "") {
				showStr5 = "-";
			} else {
				showStr5 = results[5].text;
			}

			str += "<tr align='center'>";
			str += "<td width='4%' class='td_r_b td_font'>" + (i + 1) + "</td>";
			str += "<td width='12%' class='td_r_b td_font'>" + results[1].text
					+ "</td>";
			str += "<td width='12%' class='td_r_b td_font'>" + results[2].text
					+ "</td>";
			str += "<td width='6%' class='td_r_b td_font'>" + showStr3
					+ "</td>";
			//str += "<td width='6%' class='td_r_b td_font'>"+showStr4+"</td>";
			str += "<td width='13%' class='td_r_b td_font'>" + results[4].text
					+ "</td>"; // 填报时间
			if (jgbh.length != 2) {
				if (results[7].text.substring(0, 4) != jgbh.substring(0, 4)) {
					str += "<td width='16%' class='td_r_b td_font'>" + "总队转发"
							+ "</td>"
					message = "总队转发"
				} else {
					str += "<td width='16%' class='td_r_b td_font'>"
							+ results[5].text + "</td>"
					message = results[5].text;
				}
			} else {
				str += "<td width='16%' class='td_r_b td_font'>"
						+ results[5].text + "</td>"
				message = results[5].text;
			}

			str += "<td width='4%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\""
					+ " onClick=\"onMaterialClick('view','"
					+ results[0].text
					+ "','"
					+ results[6].text
					+ "','"
					+ results[5].text
					+ "')\"" + "></td>"
			state = results[6].text;
			if (appid == "1001") {

				// 大队上报后的警情不准许修改
				if (jgbh.length == 6) {
					if (results[6].text == "004032"
							|| results[6].text == "004033") {
						// 大队保存/大队上报支队未签收 的警情准许修改
						str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\""
								+ " onClick=\"getMaterialState('edit','"
								+ results[0].text
								+ "','"
								+ results[6].text
								+ "')\"" + "></td>"
					} else if (results[5].text == '支队下发') {
						str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\""
								+ " onClick=\"alert('支队下发信息不能操作！');\" style=\"cursor:default;\""
								+ "></td>"
					} else {
						if (results[5].text == '总队转发') {
							str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\""
									+ " onClick=\"alert('总队转发信息不能操作！');\" style=\"cursor:default;\""
									+ "></td>"
						} else {
							str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\""
									+ " onClick=\"alert('不能对该消息进行处理！');\" style=\"cursor:default;\""
									+ "></td>"
						}
					}
				}

				// 支队上报后的警情不准许修改
				if (jgbh.length == 4) {
					if ($('jgid').value.substring(0, 4) != results[7].text
							.substring(0, 4)
							|| results[6].text == "004036"
							|| results[6].text == "004043"
							|| results[6].text == "004037"
							|| results[6].text == "004035"
							|| results[6].text == "004031") {
						if (message == '总队转发') {
							str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\""
									+ " onClick=\"alert('总队转发信息不能操作！');\" style=\"cursor:default;\""
									+ "></td>"
						} else {
							str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\""
									+ " onClick=\"alert('不能对该消息进行处理！');\" style=\"cursor:default;\""
									+ "></td>"
						}
					} else {
						str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\""
								+ " onClick=\"getMaterialState('edit','"
								+ results[0].text
								+ "','"
								+ results[6].text
								+ "')\"" + "></td>"
					}
				}

				// 总队下发后的警情不准许修改
				if (jgbh.length == 2) {
//					if (results[6].text == "004037"
//							|| results[6].text == "004035"
//							|| results[6].text == "004036") {
//						// 总队下发的警情不准许修改
//						str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\""
//								+ " onClick=\"onMaterialClick('view','"
//								+ results[0].text
//								+ "','"
//								+ results[6].text
//								+ "')\"" + "></td>"
//					} else {
						str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\""
								+ " onClick=\"getMaterialState('edit','"
								+ results[0].text
								+ "','"
								+ results[6].text
								+ "')\"" + "></td>"
//					}
				}
				if ((jgbh.length == 6) || (jgbh.length == 4)) {
					str += "<td width='8%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\""
							+ (!flag2 ? " style=\"cursor:default;\""
									: (" onClick=\"onMaterialClick('flow','"
											+ results[0].text + "')\""))
							+ "></td>"
				}
				
				//Modify by xiayx 2012-1-17
				//事故新增删除功能
				if(jgbh.length == 6 || jgbh.length == 4){
					var deleteImgName = "btndelete2.gif",deleteClick = "alert('草稿信息才能删除')";
					if($("jgid").value == results[7].text && results[6].text == "004032"){
						deleteImgName = "btndelete1.gif";
						deleteClick = "doMaterialDelete('"+results[0].text+"')";
					} 
					str += "<td width='5%' class='td_r_b td_font' align=\'center\'>" +
					"<img src=\"../../images/button/"+deleteImgName+"\" onClick=\""+deleteClick+"\"  style=\"cursor:hand\"/>" +
					"</td>";
				}
				//Modification finished
			}
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

var checkXbflg = "";

function getMaterialState(itemId, itemValue, eventState, messagem, flag) {
	if (itemValue != "") {
		var url = "dispatch.cmaterialInfo.getMaterialState.d";
		url = encodeURI(url);
		var params = "alarmId=" + itemValue + "&jgbh=" + $('jgbh').value
				+ "&itemId=" + itemId + "&oldState=" + eventState + "&flag=" + flag;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showState
		});
	}
}

function showState(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	materialStateXML = xmlDoc;
	var results = xmlDoc.getElementsByTagName("mist")[0]
			.getElementsByTagName("col");
	var itemid = results[0].text;
	var alarmid = results[1].text;
	var jgbh = results[2].text;
	var oldState = results[3].text;
	nowState = results[4].text;
	
	if(results[5].text != "undefined") {
		onMaterialClick(itemid, alarmid, oldState,'',results[5].text);
	} else {
		onMaterialClick(itemid, alarmid, oldState);
	}

}

/** 
 * desc:通过参数显示新增或修改的页面
 * param:
 * return:
 * author：dxn
 * date: 2010-4-2
 * version:
 */
function getMaterialInfo(alarmId, xbflg) {
	checkXbflg = xbflg;
	if (alarmId != "") {
		var url = "dispatch.cmaterialInfo.getMaterialInfo.d";
		url = encodeURI(url);
		var params = "alarmId=" + alarmId + "&jgbh=" + $('jgbh').value + "&zbrName=" + $('zbrName').value;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showGetXMLResponse
		});
		//总队转发单位，查看时自动签收
		var adinfo = {
			aid : alarmId,
			state : "2"
		};
		var pname = $("zbrName");
		if(pname && pname.value){
			adinfo.rpname = pname.value;
		}
//		Flow.updtState(adinfo);
	} else {
		// 警情新增时的画面显示
		var insrtOrUpdt = $('insrtOrUpdt').value;
		// 警情新增时画面不显示按钮的设置
		if (insrtOrUpdt == "0") {
			$("bt4").style.display = "none";
			//$("bt5").style.display = "none";
			$("bt6").style.display = "none";
			$("bt7").style.display = "none";
			$("bt10").remove();
			//alert(22);
			$("trafficInfo").innerHTML = '<textarea class="text" id="flow0_text" style="width:617px;height:100px;overflow-y:scroll;" >' + '</textarea>';

			var strUrl = "dispatch.cmaterialInfo.getDepatMentName.d"; //把参数传给后端的java
			strUrl = encodeURI(strUrl);
			var params = "";
			new Ajax.Request(strUrl, {
				method : "post",
				parameters : params,
				onComplete : showInitDepartMentName
			});
		}
	}
}

//Modified by Leisx 2011-07-21
function getMaterialInfoFlow(alarmId, jgbh) {
	if (alarmId != "") {
		var url = "dispatch.cmaterialInfo.getMaterialInfo.d";
		url = encodeURI(url);
		var params = "alarmId=" + alarmId + "&jgbh=" + jgbh;

		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showGetXMLResponseFlow
		});
	}
}
//Modification finished

function showInitDepartMentName(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	alert(xmlHttp.responseTEXT);
	var results = xmlDoc.getElementsByTagName("row")[0]
			.getElementsByTagName("col");
	$("reportunitId").value = results[0].text;
	$("reportunitName").value = results[1].text;
}

function showGetXMLResponseFlow(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0]
			.getElementsByTagName("col");

	//	for(var i = 0; i < results.length; i++) {
	//		alert(i + "----" + results[i].text)
	//	}

	if (results[13].text == "　") {
		$('roadInfo').style.display = "none";
		$('roadname').innerText = "";
	} else {
		$('roadInfo').style.display = "inline";
		$('roadname').innerText = results[13].text;
	}

	if (results[15].text == "　") {
		$('KMVALUE').value = "";
	} else {
		$('KMVALUE').value = results[15].text;
	}
	if (results[39].text == "　") {
		$('MVALUE').value = "0";
	} else {
		$('MVALUE').value = results[39].text;
	}
	$('title').innerText = results[19].text;
	$('DEATHPERSONCOUNT').value = results[17].text;
	$('BRUISEPERSONCOUNT').value = results[18].text;
	$('roaddirection').value = results[16].text;
	var roadStr = results[34].text;
	var reg = /\((.*?)\)/gi;
	var tmp = roadStr.match(reg);
	if (results[13].text == "　") {
		if (tmp == null) {
			$('roadNote').value = results[34].text;
		} else {
			for ( var i = 0; i < tmp.length; i++) {
				$('roadNote').value = tmp[i].replace(reg, "$1");
			}
		}
	} else {
		for ( var i = 0; i < tmp.length; i++) {
			$('roadNote').value = tmp[i].replace(reg, "$1");
		}
	}
}

var reportunit;
var materialInfoXML;
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	materialInfoXML = xmlDoc;
	var results = xmlDoc.getElementsByTagName("row")[0]
			.getElementsByTagName("col");
	reportunit = results[1].text;
	var fileResults;
	try {
		fileResults = xmlDoc.getElementsByTagName("jqfj");
	} catch (ex) {
		//		alert(ex.description);
		fileResults = null;
	}
	var xbflg = true;
	var xbInfo;
	try {
		xbInfo = xmlDoc.getElementsByTagName("xbinfo")[0]
				.getElementsByTagName("col");
	} catch (ex) {
		//		alert(ex.description);
		xbflg = false;
		xbInfo = null;
	}

	if (xbflg) {
		createXbInfo(xmlDoc);
	}

	var insrtOrUpdt = $('insrtOrUpdt').value;
	var jgbh = $('jgbh').value;

//		for(var i = 0; i < results.length; i ++) {
//			alert(i + ":" + results[i].text)
//		}

	// 明细画面时不显示按钮的设置
if(insrtOrUpdt=="2"){
		$("bt1").remove();
		$("bt2").remove();
		$("bt7").remove();
		$("bt10").remove();
		//$("bt5").remove();
//		$("trafficInfo").innerHTML='<textarea class="text" id="flow0_text" style="width:548px;height:100px;overflow-y:scroll;" readonly>' + results[30].text.replace(/^　$/,'') + '</textarea>';
		$("trafficInfo").innerHTML='<textarea class="text" id="flow0_text" readonly style="width:617px;height:100px;overflow-y:scroll;" >' + results[28].text.replace(/^　$/,'') + '</textarea>';
	} else if (insrtOrUpdt == "1") {
		// 编辑画面时不显示按钮的设置
		if (insrtOrUpdt == "1") {
			$("bt1").remove();
			$("bt4").remove();
			
			if(jgbh.length != 2) {
				$("bt7").remove();
			}
			if(jgbh.length == 2) {
				$("dispatch").style.display = "inline";
				$("bt2").remove();
			}
			if(jgbh.length == 2 || jgbh.length == 6) {
				$("bt10").remove();
			}
			$("trafficInfo").innerHTML = '<textarea class="text" id="flow0_text" style="width:617px;height:100px;overflow-y:scroll;" >'
					+ results[28].text.replace(/^　$/, '') + '</textarea>';
			if ($('jgbh').value.length == 2) {
				$("trafficInfo").innerHTML = '<textarea class="text" id="flow0_text" readonly style="width:617px;height:100px;overflow-y:scroll;" >'
				+ results[28].text.replace(/^　$/, '') + '</textarea>';
			}
			// 支队时显示
			if ($('jgbh').value.length == 4) {
				$("trafficInfo").innerHTML = '<textarea class="text" id="flow0_text" style="width:617px;height:100px;overflow-y:scroll;" >'
					+ results[28].text.replace(/^　$/, '') + '</textarea>';
				if (results[23].text == "004037") {
					$('bt8').show();
					$("bt2").remove();
				}
			}
		}
	}
	
    // 设置checkbox是否被check住
	$('ALARMIDVALUE').value = results[0].text;
    $('reportunitId').value = results[1].text;
    $('REPORTPERSONVALUE').value = results[2].text;
    $('reportPersonName').value = results[2].text;
    $('REPORTTIME').value = results[3].text.replace(/^　$/,'');
    $('ISBUS').checked = results[4].text == '0'?false:true;
    $('ISSCHOOLBUS').checked = results[5].text == '0'?false:true;
    $('ISDANAGERCAR').checked = results[6].text == '0'?false:true;
    $('ISFOREIGNAFFAIRS').checked = results[7].text == '0'?false:true;
    $('ISPOLICE').checked = results[8].text == '0'?false:true;
    $('ISMASSESCASE').checked = results[9].text == '0'?false:true;
    $('ISCONGESTION').checked = results[10].text == '0'?false:true;
    $('ISARMYACC').checked = results[11].text == '0'?false:true;
    $('ISOthersItem').checked = results[33].text == '0'?false:true;
    
   // if(results[7].text == '1')	//fCheck(true)
    
    if($('CASEHAPPENTIME'))	$('CASEHAPPENTIME').value = results[12].text.replace(/^　$/,'');
//    if($('ROADNAME'))	$('ROADNAME').value = results[13].text.replace(/^　$/,'');
//    if($('KMVALUE'))	$('KMVALUE').value = results[14].text.replace(/^　$/,'');
    if(results[17].text > 2)	$('ISTHREEUP').checked = true;
	if($('DEATHPERSONCOUNT'))	$('DEATHPERSONCOUNT').value = results[17].text.replace(/^　$/,'');
	if($('BRUISEPERSONCOUNT'))	$('BRUISEPERSONCOUNT').value = results[18].text.replace(/^　$/,'');
    $('TITLE').value = results[19].text;
    $('ALARMUNIT').value = results[20].text;
    $('ALARMREGIONID').value = results[21].text;
    $('ALARMREGION').value = results[22].text;
//    $('RESPONSIBLEPERSON').value = results[27].text.replace(/^　$/,'');
    $('ALARMTIME').value = results[29].text.replace(/^　$/,'');
    // 联系电话
    $('TELENUM').value = results[30].text.replace(/^　$/,'');
    // 接警人
    $('RECEIVEPERSON').value = results[31].text.replace(/^　$/,'');
    
    $('MISSINGPERSONCOUNT').value = results[32].text.replace(/^　$/,'');
    
    // 事故地点
    $('caseHappenPlace').value = results[34].text.replace(/^　$/,'');
    $('caseHappenPlace').title = results[34].text.replace(/^　$/,'');
    
    // 事故编号
//    $('GLACCNUM').value = results[35].text.replace(/^　$/,'');
    pageInit();
    //$("KMVALUE").value = results[38].text;
    //$("MVALUE").value = results[39].text;
    
    //管辖机构
    $('sobject_single_div').value = results[45].text;
    
    var jgid_ = $("jgid").value;
    if(fileResults != null && fileResults.length != 0) {
    	var show = "";
    	if(insrtOrUpdt=="2"){
    		show = "";
    	} else {
    		if(jgid_.substring(2,6) != "0000"){
		    	show = '' + 
			            '<input id="mfile0" type="file" name="mfile0" style="border: 1px #7B9EBD solid" size="62"  /> ' + 
						'<INPUT TYPE="button" id="addbtn" NAME="addbtn" style="border: 1px #7B9EBD solid;height: 20px;" value="增加附件" ' + 
						'	onclick="ocAddFile(\'uploadTable\',\'fileCounter\',5);">' + 
						'<input type="hidden" id="fileCounter" value="0">' + 
			            '<table id="uploadTable" class="uploadTable" border="0" cellpadding="0" cellspacing="0">' + 
						'</table>';
    		}
    	}
    	    for(var  i=0;i<fileResults.length;i++){
    	    	var rowFile = fileResults[i].getElementsByTagName("col");;
			    if (rowFile[2].text != "null" && rowFile[2].text != "" && rowFile[2].text != null) {
				    var filePath = rowFile[2].text.replace(/^　$/,'');
				    var str1 = filePath;
					var regstr = "/";
					var regresult = new RegExp(regstr)
					var sss = str1.split(regresult, '100');
					var need = sss[sss.length - 1];
					var a = need.split('.');
					if(a[0]==null || a[0] =="" || a[0]=="undefined" || a[1]==null || a[1] =="" || a[1]=="undefined") {
					} else {
						if(i==0) {
							show = show + " <a href=\"#\" style=\"border-bottom: 1 solid green \" title='查看附件' onclick=\"openWPS('" + filePath + "')\" >" + a[0] + "." + a[1] + "</a> ";
						} else {
							show = show + " <br/><a href=\"#\" style=\"border-bottom: 1 solid green \" title='查看附件' onclick=\"openWPS('" + filePath + "')\" >" + a[0] + "." + a[1] + "</a> ";
						}
					}
					// 大队(6位) 点击“修改”时追加“删除”按钮
				    if((jgbh.length == 6 || jgbh.length == 4 )&& insrtOrUpdt == "1" ) {
						show = show + "<input type=\"image\" src=\"../../images/button/btndelete1.gif\" title='删除附件' onclick=\"deleteAttachmentFile('"+rowFile[0].text+"','" + results[0].text + "')\" /> "
					}
			    }
    	    }
		$('fileList').innerHTML = show;
	} else {
		if (insrtOrUpdt == "2") {
			$('fileList').innerHTML = "没有上传附件!";
		}
	}
    
    
/*    var czzt1 = results[28].text.replace(/^　$/,'');
    czzt1 = czzt1?czzt1:'0';
    $('czzt1').value = czzt1;
    var czzt2 = results[28].text.replace(/^　$/,'');
    czzt2 = czzt2?czzt2:'0';
    $('czzt2').value = czzt2;*/

    var EVENTSTATE = results[23].text;

    $('reportunitName').value = results[24].text;
//    $('REPORTPERSON').value = results[24].text;
    
	if(results[17].text > 2)	$('ISTHREEUP').checked = true;
	//if($('jgbh').value.length == 4)	checkAlarmVersion(results[0].text);
	
	var flow0_str = '';
	var jgid = $('jgid').value;
    
    var xmlflow = xmlDoc.getElementsByTagName("flow");
	var xmlflow_length = xmlflow.length;
	if(xmlflow_length > 0){
		for(var i = 0; i < xmlflow_length; i ++){
			var flowResults = xmlflow[i].getElementsByTagName("col");
			var OPID_value = flowResults[0].text;
			var TEXT_value = flowResults[1].text.replace(/^　$/,'');
			var FSSJ_value = flowResults[2].text;
			var FSJG_value = flowResults[3].text;
			var FSLB_value = flowResults[4].text;
			var CZZT_value = flowResults[5].text;
			var FSRY_value = flowResults[6].text;
			flowValue.push({OPID: OPID_value, // 流水号
							TEXT: TEXT_value, // 发送文本
							FSSJ: FSSJ_value, // 发送时间
							FSJG: FSJG_value, // 发送机构
							FSLB: FSLB_value, // 发送类别，支队指挥协调和处置措施/领导批示/办理情况/总队批示意见下发支队
							CZZT: CZZT_value, // 操作ID，0-保存，1-上报，2-已签收
							FSRY: FSRY_value	}); // 信息发送人
		}
	}
	createFlow(insrtOrUpdt,jgid,EVENTSTATE);
//	
//	var adstates = xmlDoc.getElementsByTagName("adstate");
//	Flow.showAdstate(adstates,[0,1],"adstateTd");
//	var jginfo = xmlDoc.getElementsByTagName("jginfo");
//	var jginfos = "";
//	var zdzfStr = ""
//	var zdzfStr2 = ""
//	if($('adstateTd').innerHTML != "") {
//		zdzfStr = "<label> 总队转发（</label>"
//		zdzfStr2 = "<label>）</label>"
//	}
//	if(jginfo && jginfo.length == 1){
//		jginfo = jginfo[0];
//		jginfo = jginfo.childNodes;
//		jginfos = jginfo[0].text.split(";");
//		var sbdw = jginfos[1].split(",")
//		if(jginfos[1].split(",").length == 2) {
//			if(results[23].text == "004034") {
//				$('adstateTd').innerHTML = "<span style='color:red;'>"+sbdw[0]+"</span>" + "；" + zdzfStr  + $('adstateTd').innerHTML + zdzfStr2;
//			} else {
//				$('adstateTd').innerHTML = "<span style='color:green;'>"+sbdw[0]+"</span>" + "；" +zdzfStr + $('adstateTd').innerHTML + zdzfStr2;
//			}
//		}
//			
//		if(jginfos[1].split(",").length == 3) {
//			if(results[23].text == "004033") {
//				$('adstateTd').innerHTML = "<span style='color:red;'>"+sbdw[1]+"</span>"  + "；" + zdzfStr + $('adstateTd').innerHTML + zdzfStr2;
////				$('adstateTd').innerHTML = "<span style='color:red;'>"+sbdw[1]+"</span>" + "，" + "<span style='color:red;'>"+sbdw[0]+"</span>" + "；" + zdzfStr + $('adstateTd').innerHTML + zdzfStr2;
//			} else if(results[23].text == "004042"){
//				$('adstateTd').innerHTML = "<span style='color:green;'>"+sbdw[1]+"</span>" + "；" + zdzfStr + $('adstateTd').innerHTML + zdzfStr2;
//			} else if(results[23].text == "004034" || results[23].text == "004037"){
//				$('adstateTd').innerHTML = "<span style='color:green;'>"+sbdw[1]+"</span>" + "，" + "<span style='color:red;'>"+sbdw[2]+"</span>" + "；" + zdzfStr + $('adstateTd').innerHTML + zdzfStr2;
//			} else if(results[23].text == "004035" || results[23].text == "004036" || results[23].text == "004043"){
//				$('adstateTd').innerHTML = "<span style='color:green;'>"+sbdw[1]+"</span>"+ "，" + "<span style='color:green;'>"+sbdw[2]+"</span>" + "；" + zdzfStr + $('adstateTd').innerHTML + zdzfStr2;
//			} 
//		}
//		
//		
//	} 
	
	var n;
	
	if(jgbh.length == 2 || jgbh.length == 4) {
		n = 23;
	} else {
		n = 44;
	}
	
	if(results[23].text == "004032") {
		$('adstateTr').style.display = "none";
	} else {
		$('adstateTr').style.display = "inline";
	}
	
	var jginfo = xmlDoc.getElementsByTagName("jginfo");
	var zdxfname = xmlDoc.getElementsByTagName("zdxfname");
	var zodxfname = xmlDoc.getElementsByTagName("zodxfname");
	
	var firstStep = "";
	var stepTwo = "";
	var stepThree = "";
	var sbdw;
	var jginfos;
	
	if(jginfo && jginfo.length == 1){
		jginfo = jginfo[0];
		jginfo = jginfo.childNodes;
		jginfos = jginfo[0].text.split(";");
		sbdw = jginfos[1].split(",")
		firstStep = sbdw[0] + "<strong>上报</strong>" + sbdw[1];
	}
	
	if(zdxfname && zdxfname.length == 1){
		if(results[n].text == "004042") {
			stepTwo = sbdw[1] + "<strong>下发</strong>" + zdxfname[0].text;
		} else if(results[n].text == "004034" || results[n].text == "004036" || results[n].text == "004035" || results[n].text == "004037" || results[n].text == "004034" || results[n].text == "004043") {
			stepTwo = sbdw[1]+ "<strong>上报</strong>" + sbdw[2] + "<strong>并下发</strong>" + zdxfname[0].text;
		} 
	} else {
		if(jginfos[1].split(",").length == 3) {
			if(results[n].text == "004034" || results[n].text == "004036" || results[n].text == "004035" || results[n].text == "004037" || results[n].text == "004034" || results[n].text == "004043") {
				stepTwo = sbdw[1] + "<strong>上报</strong>" + sbdw[2];
			}
		}
	}
	
	if(zodxfname && zodxfname.length == 1){
		if(jginfos[1].split(",").length == 3) {
			if(results[n].text == "004037" || results[n].text == "004035") {
				stepThree = sbdw[2] + "<strong>下发</strong>" + sbdw[1] + "<strong>并转发</strong>" + zodxfname[0].text;
			} else if(results[n].text == "004043") {
				stepThree = sbdw[2] + "<strong>转发</strong>" + zodxfname[0].text;
			}
		} else {
			if(results[n].text == "004037" || results[n].text == "004035") {
				stepThree = sbdw[1] + "<strong>下发</strong>" + sbdw[0] + "<strong>并转发</strong>" + zodxfname[0].text;
			} else if(results[n].text == "004043") {
				stepThree = sbdw[1] + "<strong>转发</strong>" + zodxfname[0].text;
			}
		}
		
	} else {
		if(results[n].text == "004037" || results[n].text == "004035") {
			if(jginfos[1].split(",").length == 3) {
				stepThree = sbdw[2] + "<strong>下发</strong>" + sbdw[1];
			} else {
				stepThree = sbdw[1] + "<strong>下发</strong>" + sbdw[0];
			}
		}
	}
	var douhao1 = "";
	var douhao2 = "";
	if(stepTwo != "") {
		douhao1 = "；"
	}
	
	if(stepThree != "") {
		douhao2 = "；"
	}
	$('adstateTd').innerHTML = firstStep + douhao1 + stepTwo + douhao2 + stepThree
	
	if(jgbh.length == 2) {
		$('showCheckZd').style.display = '';
		$("showCheckZd").show();
	}
	if((jgbh.length == 4) && (results[23].text == '004037' || results[23].text == '004035')) {
		$('showCheckZd').style.display = '';
		$("showCheckZd").show();
	}
	
	if(jgbh.length == 4) {
		$('showCheckZd').style.display = '';
		$("showCheckZd").show();
		if($('flow6_box')) {
			if($('flow6').innerText == "") {
				$('zdMessage').style.display = 'none';
			}
		}
		
	} else if(jgbh.length == 6) {
		$('showCheckZd').style.display = '';
		$("showCheckZd").show();
		$('zdMessage').style.display = 'none';
		$('flow6_box').style.display = 'none';
	}
	
	var page = insrtOrUpdt;
	var level = jgbh.length / 2;
	if($("roadselectImg")){
		if(page == "1"){
			$("roadselectImg").style.display = "none";
		}else{
			if(level!=3){
				$("roadselectImg").style.display = "none";
			}
		}
	}
	
	$('receivetime').value = results[40].text.replace(/^　$/,'');
	
	if(insrtOrUpdt == 2) {
		$('receivetime').disabled = "true";
		$('ddapprover').readOnly = true;
		$('zdapprover').readOnly = true;
		$('zodapprover').readOnly = true;
	}
	
	if(jgbh.length == 4) {
		if(insrtOrUpdt == 1) {
			$('ddapprover').readOnly = true;
		}
	}
	if(jgbh.length == 2) {
		if(insrtOrUpdt == 1) {
			$('ddapprover').readOnly = true;
			$('zdapprover').readOnly = true;
		}
	}
	
	if($('flow2_box').style.display != 'none') {
		$('zdshr').style.display = "inline"
		$('zdapprover').value = results[42].text.replace(/^　$/,'')
	}
	
	if($('flow7_box').style.display != "none" || $('flow6_box').style.display != "none") {
		$('zodshr').style.display = "inline"
		$('zodapprover').value = results[43].text.replace(/^　$/,'')
	}
	
	if(results[41].text.replace(/^　$/,'') == "") {
		$('ddshr').style.display = "none"
	} 
	
	if(insrtOrUpdt == 1) {
		if(jgbh.length == 2) {
			$('ddapprover').innerHTML = "<label>" + results[41].text.replace(/^　$/,'') + "</label>"
			$('zdapprover').innerHTML = "<label>" + results[42].text.replace(/^　$/,'') + "</label>"
			$('zodapprover').innerHTML = "<input type='text' id='zodapprover_text' maxlength=20 style='border: 1px #7B9EBD solid;width:605px;' value='"+results[43].text.replace(/^　$/,'') +"'>"
		} else if(jgbh.length == 4) {
			$('ddapprover').innerHTML = "<label>" + results[41].text.replace(/^　$/,'') + "</label>"
			$('zdapprover').innerHTML = "<input type='text' id='zdapprover_text' maxlength=20 style='border: 1px #7B9EBD solid;width:605px;' value='"+results[42].text.replace(/^　$/,'') +"'>"
		} else {
			$('ddapprover').innerHTML = "<input type='text' id='ddapprover_text' maxlength=20 style='border: 1px #7B9EBD solid;width:605px;' value='"+results[41].text.replace(/^　$/,'') +"'>"
		}
	} else if(insrtOrUpdt == 2) {
		if(jgbh.length == 2) {
			$('ddapprover').innerHTML = "<label>" + results[41].text.replace(/^　$/,'') + "</label>"
			$('zdapprover').innerHTML = "<label>" + results[42].text.replace(/^　$/,'') + "</label>"
			$('zodapprover').innerHTML = "<label>" + results[43].text.replace(/^　$/,'') + "</label>"
		} else if(jgbh.length == 4) {
			$('ddapprover').innerHTML = "<label>" + results[41].text.replace(/^　$/,'') + "</label>"
			$('zdapprover').innerHTML = "<label>" + results[42].text.replace(/^　$/,'') + "</label>"
			$('zodapprover').innerHTML = "<label>" + results[43].text.replace(/^　$/,'') + "</label>"
		} else {
			$('ddapprover').innerHTML = "<label>" + results[41].text.replace(/^　$/,'') + "</label>"
			$('zdapprover').innerHTML = "<label>" + results[42].text.replace(/^　$/,'') + "</label>"
		}
	}
}
var noticeLD = "";
//Modified by Liuwx 2011-07-08
var noticeCL = "";
//Modification finished
var flowValue = new Array();

function createFlow(insrtOrUpdt,jgid,EVENTSTATE){
	// 0:新增 1:编辑 2:明细insrtOrUpdt
	var flowValue_length = flowValue.length;
	var jgbh = $('jgbh').value;
	var flow1_str = '';
	var flow2_str = '';
	var flow3_str = '';
	var flow4_str = '';
	var flow5_str = '';
	var flow6_str = '';
	var flow7_str = '';
	var flow8_str = '';
	var flow9_str = '';
	var showFlg = false;
	// 支队时显示的支队输入内容
	if ($('jgbh').value.length == 4) {
		$('flow2_box').show();
		// 总队时显示的总队输入内容
	} else if ($('jgbh').value.length == 2) {
		$('flow2_box').show();
		$('flow6_box').show();
		$('flow7_box').show();
		$('flow8_box').show();
		var page = $("insrtOrUpdt").value;
		if(page == "1" && $("dobox").checked){
			$("dispatch").style.display = "inline";
		}
//		if(page == "2"){
//			$('adstateTr').style.display = "inline";
//		}
	} 
	var showStr12 = "";
	//Modified by Liuwx 2011-07-08
	var showStr13 = "";
	//Modification finished
	for (var i = 0; i < flowValue_length; i++) {
		var flowValue_i = flowValue[i];
		// 支队指挥协调和处置措施 支队上报
		if (flowValue_i.FSLB == '004038') {
			//因为显示多条数据，getContent()是覆盖了上一条，只显示一条。所以这里为 +=
			flow2_str += getContent(flowValue_i.TEXT, flowValue_i.FSJG, flowValue_i.FSRY, flowValue_i.FSSJ)+"<br>";//这里加了个换行，页面漂亮些。
//			flow2_str += '<div>'
//				+ flowValue_i.TEXT
//				+ '</div><div style="border-bottom: 0 solid #a5d3ef;text-align:right;padding-right:20px">'
//				+ flowValue_i.FSJG + ' ' + flowValue_i.FSRY + ' '
//				+ flowValue_i.FSSJ + '</div>';
		}

		// 总队批示意见下发支队 总队下发
		if (flowValue_i.FSLB == '004041') {
//			flow6_str += '<div>'
//					+ flowValue_i.TEXT
//					+ '</div><div style="border-bottom: 0 solid #a5d3ef;text-align:right;padding-right:20px">'
//					+ flowValue_i.FSJG + ' ' + flowValue_i.FSRY + ' '
//					+ flowValue_i.FSSJ + '</div>';
			flow6_str += getContent(flowValue_i.TEXT, flowValue_i.FSJG, flowValue_i.FSRY, flowValue_i.FSSJ);
			showFlg = true;
		}
		
		// 领导批示 总队下发
		if (flowValue_i.FSLB == '004039') {
//			flow7_str += '<div>'
//					+ flowValue_i.TEXT
//					+ '</div><div style="border-bottom: 0 solid #a5d3ef;text-align:right;padding-right:20px">'
//					+ flowValue_i.FSJG + ' ' + flowValue_i.FSRY + ' '
//					+ flowValue_i.FSSJ + '</div>';
			flow7_str += getContent(flowValue_i.TEXT, flowValue_i.FSJG, flowValue_i.FSRY, flowValue_i.FSSJ);
			showStr12 = flowValue_i.TEXT;
		}
		// 办理情况 总队下发
		if (flowValue_i.FSLB == '004040') {
//			flow8_str += '<div>'
//					+ flowValue_i.TEXT
//					+ '</div><div style="border-bottom: 0 solid #a5d3ef;text-align:right;padding-right:20px">'
//					+ flowValue_i.FSJG + ' ' + flowValue_i.FSRY + ' '
//					+ flowValue_i.FSSJ + '</div>';
			flow8_str += getContent(flowValue_i.TEXT, flowValue_i.FSJG, flowValue_i.FSRY, flowValue_i.FSSJ);
			//Modified by Liuwx 2011-07-08
			showStr13 = flowValue_i.TEXT;
			//Modification finished
		}
	}
	// 总队有意见下发支队时显示总队下发意见
	if(showFlg == true) {
		$('flow6_box').show();
	}
	
	if (jgbh.length == 6 && insrtOrUpdt == "2") {
		if($('message').value == '1') {
			$('flow2_box').style.display = "inline";
			$('flow2').innerHTML = flow2_str;
		}

		if($('message').value == '0') {
			$('flow2_box').style.display = "none";
		}
	}
	
	// 支队明细时画面显示的设置
	if (jgbh.length == 4 && insrtOrUpdt == "2") {
		if ($('flow2_box').style.display != 'none') {
				$('flow2').innerHTML = flow2_str;
		}

		if ($('flow6_box').style.display != 'none') {
				$('flow6').innerHTML = flow6_str;
		}
	}

	// 支队编辑时画面显示的设置
	if (jgbh.length == 4 && insrtOrUpdt == "1") {
		if ($('flow2_box').style.display != 'none') {
				flow2_str += '<textarea rows="4" id="flow2_text"  REPORTKIND="004038" style="width:617px;height:60px;overflow-y:scroll;"></textarea>'
				$('flow2').innerHTML = flow2_str;
		}
		if ($('flow6_box').style.display != 'none') {
				$('flow6').innerHTML = flow6_str;
		}
	}
	
	// 总队明细画面时画面显示的设置
	if (jgbh.length == 2 && insrtOrUpdt == "2") {
		if ($('flow2_box').style.display != 'none') {
			$('flow2').innerHTML = flow2_str;
		}
		if ($('flow6_box').style.display != 'none') {
			$('flow6').innerHTML = flow6_str;
		}

		if ($('flow7_box').style.display != 'none') {
			$('flow7').innerHTML = flow7_str;
		}

		if ($('flow8_box').style.display != 'none') {
			$('flow8').innerHTML = flow8_str;
		}
	}
		
	// 总队编辑画面时画面显示的设置
	if (jgbh.length == 2 && insrtOrUpdt == "1") {
			if ($('flow2_box').style.display != 'none') {
			$('flow2').innerHTML = flow2_str;
		}

		if ($('flow6_box').style.display != 'none') {
//			flow6_str += flow6_str + '<textarea class="text" id="flow6_text" REPORTKIND="004038" style="width:650px;height:36px;overflow-y:scroll;"></textarea>';
			$('flow6').innerHTML = flow6_str + '<textarea class="text" id="flow6_text" REPORTKIND="004041" style="width:617px;height:36px;overflow-y:scroll;display: none"></textarea>';
		}

		if ($('flow7_box').style.display != 'none') {
//			flow7_str += flow7_str + '<textarea class="text" id="flow7_text" REPORTKIND="004034" style="width:650px;height:36px;overflow-y:scroll;"></textarea>';
			$('flow7').innerHTML = flow7_str + '<textarea class="text" id="flow7_text" REPORTKIND="004039" style="width:617px;height:36px;overflow-y:scroll;"></textarea>';
		}

		if ($('flow8_box').style.display != 'none') {
//			flow8_str += flow8_str + '<textarea class="text" id="flow8_text" REPORTKIND="004035" style="width:650px;height:36px;overflow-y:scroll;"></textarea>';
			$('flow8').innerHTML = flow8_str +  '<textarea class="text" id="flow8_text" REPORTKIND="004040" style="width:617px;height:36px;overflow-y:scroll;"></textarea>';
		}
	}

	 if($('jgbh').value.length == 6) {
		$('flow6_box').hide();
		$('flow7_box').hide();
		$('flow8_box').hide();
	}

	// 总队用户进入编辑画面时的处理
	if($('jgbh').value.length == 2 && (insrtOrUpdt == 1) ) {
		$('flow6_box').style.display = 'none';
		$("chooseCheck").show();
	}
	
	// 
	if ($('flow6_box').style.display != 'none') {
		noticeLD = showStr12;
	} else {
		noticeLD = "";
	}
	
	//Modified by Liuwx 2011-07-08
	if ($('flow8_box').style.display != 'none') {
		noticeCL = showStr13;
	} else {
		noticeCL = "";
	}
	//Modification finished

	// 总队有意见下发支队时显示总队下发意见
	if(showFlg == true) {
		$('flow6_box').show();
	} else {
		$('flow6_box').hide();
	}
		if(checkXbflg == "1") {
		$('chooseCheck').hide();
		$('flow6_box').hide();
		$('flow7_box').hide();
		$('flow8_box').hide();
		}
}

/**
 * flow6_box显示不显示的控制<br/>
 * 
 */
var xfck = false;
function showFlow6Box() {
	if($("doBox")) {
		if($("doBox").checked) {
			if(!xfck){
				xfck = true;
				$("flow6_text").style.display = "inline";
				var flow6_text = $("flow6_text");
				var flow7_text = $("flow7_text");
				flow6_text.value = flow7_text.value;
			}
			$('flow6_box').style.display = '';		
			$("RADIOTYPE_2").checked = false;	
			
			$("bt7").firstChild.firstChild.innerText = "下发";
		}
	}
	if($("RADIOTYPE_2")) {
		if($("RADIOTYPE_2").checked) {
			xfck = false;
			$('flow6_text').style.display = 'none';		
			$("doBox").checked = false;
			$("bt7").firstChild.firstChild.innerText = "保存";
		}
	}
	/*if($("doBox")){
		if ($("doBox").checked) {
			$('flow6_box').style.display = '';
		} else {
			$('flow6_box').style.display = 'none';
		}
	}*/
}



/** 
 * 增加、编辑 --绑定前端数据到后端进行业务处理<br/>
 * @param {} st
 * @param {} buttonFlag
 */

function setButtonPressed(btn) {
	buttonPressed = btn;
	return buttonPressed;
}

function modify(st,buttonFlag) {
	if(!validateInput())	return;
	if ($('reportPersonName')) {
		if (!$('reportPersonName') || $('reportPersonName').value == "") {
			alert("请录入填报人姓名！");
			$('reportPersonName').focus();
			return;
		}
	}
	if ($('CHECKPERSON_SB')) {
		if (!$('CHECKPERSON_SB') || $('CHECKPERSON_SB').value == "") {
			alert("请录入审批人姓名！");
			$('CHECKPERSON_SB').focus();
			return;
		}
	}
	
	// 事件类别checkbox值的取得
	var ISTHREEUP = $('ISTHREEUP').checked?"1":"0";
	var ISBUS = $('ISBUS').checked?"1":"0";
	var ISSCHOOLBUS = $('ISSCHOOLBUS').checked?"1":"0";
	var ISDANAGERCAR = $('ISDANAGERCAR').checked?"1":"0";
	var ISFOREIGNAFFAIRS = $('ISFOREIGNAFFAIRS').checked?"1":"0";
	var ISPOLICE = $('ISPOLICE').checked?"1":"0";
	var ISMASSESCASE = $('ISMASSESCASE').checked?"1":"0";
	var ISCONGESTION = $('ISCONGESTION').checked?"1":"0";
	// 追加"其他"的checkbox
	var ISOthersItem = $('ISOthersItem').checked?"1":"0";
	// 追加"涉军交通事故"的checkbox
	var ISARMYACC = $('ISARMYACC').checked?"1":"0";
	
	var saveFlag = "";
	if ($('doBox')) {
		if($('doBox').checked) {
			// 表示$('doBox')被checked
			saveFlag = "1";
		} else {
			saveFlag = "0";
			
		}
	}
	
//	var buttonPressed = $('dispatch').innerText
	// 判断死亡人数
	var DEATHPERSONCOUNT = $('DEATHPERSONCOUNT')?$('DEATHPERSONCOUNT').value:'';
	
	var params = {};
	// buttonFlag("0"保存,"1"上报,"3"支队签收,"4"总队签收,"5"支队转发和上报)警情id，上报人，联系电话
	params["buttonFlag"] = buttonFlag;
	params["zbrName"] = $('zbrName').value;
	params["buttonPressed"] = buttonPressed;
	params["ALARMID"] = $('ALARMIDVALUE').value;
	params["REPORTPERSON"] = $('reportPersonName')?$('reportPersonName').value:'';
	params["TELENUM"] = $('TELENUM').value;
//	params["adcode"] = $('adcode').value
	// 审批人，填报时间
	params["RESPONSIBLEPERSON"] = $('CHECKPERSON_SB')?$('CHECKPERSON_SB').value:'';
	if($('REPORTTIME_SB')) {
		params["REPORTTIME"] = $('REPORTTIME_SB')?$('REPORTTIME_SB').value:'';		
	} else {
		params["REPORTTIME"] = $('REPORTTIME')?$('REPORTTIME').value:'';
	}
	// 事件类别checkbox值的取得
	params["ISBUS"] = ISBUS;
	params["ISSCHOOLBUS"] = ISSCHOOLBUS;
	params["ISDANAGERCAR"] = ISDANAGERCAR;
	params["ISFOREIGNAFFAIRS"] = ISFOREIGNAFFAIRS;
	params["ISPOLICE"] = ISPOLICE;
	params["ISMASSESCASE"] = ISMASSESCASE;
	params["ISCONGESTION"] = ISCONGESTION;
	params["ISOthersItem"] = ISOthersItem;
	params["ISARMYACC"] = ISARMYACC;

	// 事故描述
	var aLARMDESCInfo = "";
	if($('TITLE')) {
		aLARMDESCInfo = convertSql($('flow0_text').value);
	}
	params["ALARMDESC"] = $('flow0_text')?aLARMDESCInfo:'';

	// 接警时间
	params["ALARMTIME"] = $('REPORTTIME')?$('REPORTTIME').value:'';
	params["ROADID"] = $('ROADID')?$('ROADID').value:'';
	params["ROADNAME"] = $('ROADNAME')?$('ROADNAME').value:'';
	params["KMVALUE"] = $('KMVALUE')?$('KMVALUE').value:'';
	params["MVALUE"] = $('MVALUE')?$('MVALUE').value:'';
	params["ROADDIRECTION"] = $('ROADDIRECTIONELE')?$('ROADDIRECTIONELE').value:'';
	// 死亡人数
	params["DEATHPERSONCOUNT"] = DEATHPERSONCOUNT;
	params["BRUISEPERSONCOUNT"] = $('BRUISEPERSONCOUNT')?$('BRUISEPERSONCOUNT').value:'';
	// 失踪人数
	params["MISSINGPERSONCOUNT"] = $('MISSINGPERSONCOUNT')?$('MISSINGPERSONCOUNT').value:'';
	// 上报电话
	params["TELNUMSB"] = $('TELNUMSB')?$('TELNUMSB').value:'';
	params["TITLE"] = $('TITLE')?$('TITLE').value:'';
	
//	params["glAccNum"] = $('glAccNum')?$('glAccNum').value:'';
	
	// 追加事故详细描述
	params["trafficInfo"] = $('flow0_text')?aLARMDESCInfo:'';
	// 追加事故发生时间
	params["info1"] = $('ALARMTIME')?$('ALARMTIME').value:'';
	// 追加受伤人数
	params["info15"] = $('BRUISEPERSONCOUNT')?$('BRUISEPERSONCOUNT').value:'';
	// 追加接警人
	params["RECEIVEPERSON"] = $('RECEIVEPERSON')?$("RECEIVEPERSON").value:'';
	// 事故时间
	params["caseHappenTime"] = $('caseHappenTime')?$("caseHappenTime").value:'';
	// 事故地点
	params["caseHappenPlace"] = $('caseHappenPlace')?$("caseHappenPlace").value:'';

	params["FLOW1"] = $('flow1_text')?convertSql($('flow1_text').value):'';
	params["FLOWID1"] = $('flow1_text')?$('flow1_text').OPID:'';
 	params["FLOWKIND1"] = $('flow1_text')?$('flow1_text').REPORTKIND:'';
	params["FLOW2"] = $('flow2_text')?convertSql($('flow2_text').value):'';
	params["FLOWID2"] = $('flow2_text')?$('flow2_text').OPID:'';
	params["FLOWKIND2"] = $('flow2_text')?$('flow2_text').REPORTKIND:'';
	params["FLOW3"] = $('flow3_text')?convertSql($('flow3_text').value):'';
	params["FLOWID3"] = $('flow3_text')?$('flow3_text').OPID:'';
	params["FLOWKIND3"] = $('flow3_text')?$('flow3_text').REPORTKIND:'';
	params["FLOW4"] = $('flow4_text')?convertSql($('flow4_text').value):'';
	params["FLOWID4"] = $('flow4_text')?$('flow4_text').OPID:'';
	params["FLOWKIND4"] = $('flow4_text')?$('flow4_text').REPORTKIND:'';
	params["FLOW5"] = $('flow5_text')?convertSql($('flow5_text').value):'';
	params["FLOWID5"] = $('flow5_text')?$('flow5_text').OPID:'';
	params["FLOWKIND5"] = $('flow5_text')?$('flow5_text').REPORTKIND:'';
	params["FLOW6"] = $('flow6_text')?convertSql($('flow6_text').value):'';
	params["FLOWID6"] = $('flow6_text')?$('flow6_text').OPID:'';
	params["FLOWKIND6"] = $('flow6_text')?$('flow6_text').REPORTKIND:'';
	params["FLOW7"] = $('flow7_text')?convertSql($('flow7_text').value):'';
	params["FLOWID7"] = $('flow7_text')?$('flow7_text').OPID:'';
	params["FLOWKIND7"] = $('flow7_text')?$('flow7_text').REPORTKIND:'';
	params["FLOW8"] = $('flow8_text')?convertSql($('flow8_text').value):'';
	params["FLOWID8"] = $('flow8_text')?$('flow8_text').OPID:'';
	params["FLOWKIND8"] = $('flow8_text')?$('flow8_text').REPORTKIND:'';
	params["FLOW9"] = $('flow9_text')?convertSql($('flow9_text').value):'';
	params["FLOWID9"] = $('flow9_text')?$('flow9_text').OPID:'';
	
	params["EVENTSOURCE"] = $('EVENTSOURCE').value;
	params["EVENTTYPE"] = $('EVENTTYPE').value;
	params["ALARMUNIT"] = $('ALARMUNIT').value;
	params["ALARMREGIONID"] = $('ALARMREGIONID').value;
	params["ALARMREGION"] = $('ALARMREGION').value;
	// 0新增以外都是更新操作
	params["insrtOrUpdt"] = $('insrtOrUpdt').value;
	// 填报时的机构id
	params["reportunitId"] = $('reportunitId').value;
	params["REPORTUNITIDVALUE"] = $('REPORTUNITIDVALUE').value;
	params["REPORTPERSONXM"] = $('REPORTPERSONXM').value;
	
	//接警时间
	params["receivetime"] = $('receivetime').value;
	
	//审核人
	params["ddapprover"] = ($('ddapprover_text') ? $('ddapprover_text').value : "");
	params["zdapprover"] = ($('zdapprover_text') ? $('zdapprover_text').value : "");
	params["zodapprover"] = ($('zodapprover_text') ? $('zodapprover_text').value : "");
	
//	params["glAccNum"] = $('glAccNum').value;
	//alert($('REPORTPERSONXM').value);
	// 发送时的机构id
	params["OPERATEID"] = st;
	params["saveFlag"] = saveFlag;
	
	// 机构编号
	params["jgbh"] = $("jgbh").value;
	params["jgid"] = $("jgid").value;
	
	var formObj = document.getElementById("alarmFileUploadForm");
	var fileObj = document.getElementById("mfile0");
	if (fileObj != null && fileObj != "undefined" && fileObj != "") {
		var fileName = fileObj.value;
		if (fileName != null && fileName != "undefined" && fileName != "") {
			formObj.submit();
		}
	}
	//总队转发
	var adcode = $("adcode");
	if(adcode && adcode.value){
		adcode = adcode.value;
		adcode = adcode.replace(/；/g,",");
		adcode = adcode.substring(0,adcode.length-1);
		params.adcode = adcode;
	}
	
	var url = "dispatch.cmaterialInfo.modifyMaterialInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyResult});
}
function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc.indexOf('成功')>-1){
		alert(xmlDoc);
		window.close();
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
function doMaterialDelete(alarmId) {                           
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "dispatch.cmaterialInfo.deleteMaterialInfo.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "alarmId=" + alarmId;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showSGResponseDelete});
	} else {
		return;
	}
}

function showSGResponseDelete(xmlHttp) {
	var text = xmlHttp.responseText;
	if(text == "true"){
		alert("删除成功！");
		if(address == "all") {
			doSearchMaterialInfo();
		} else {
			doSearch($("jgbh").value);
		}
	}else if(text == "false"){
		alert("删除失败！");
	}
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
var nowState = "";

function onMaterialClick(itemId, itemValue,eventState,message, flag) {
	var id = itemId;
	if (id == "new") {
		window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0", "", "dialogWidth:900px;dialogHeight:570px");
		if(flag == "all") {
			doSearchAll();
		} else if(flag == "jq") {
			doSearchMaterialInfo();
		} else {
			doSearch(itemValue);
		}
		
	}
	if (id == "delete") {
		doMaterialDelete(itemValue);
	}
	if (id == "edit") {
		// 支队用户和总队用户在点击“修改/上报”时的处理
		if($("jgbh").value.length == 4 || $("jgbh").value.length == 2) {
			if($("jgbh").value.length == 4 && nowState != eventState && nowState == "004043") {
				alert("总队已签收，不能再对该消息进行处理！");
				if(flag == "all") {
					doSearchAll();
				} else {
					doSearch($("jgbh").value);
				}
			} else if($("jgbh").value.length == 4 && nowState != eventState && (nowState == "004036" || nowState == "004031")) {
				alert("总队已处理，不能再对该消息进行处理！");
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			} else if($("jgbh").value.length == 4 && nowState != eventState && (nowState == "004035" || nowState =="004037")) {
				alert("总队下发消息，不能再对该消息进行处理！");
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			} else {
				// 支队用户并且已经上报的
				if ($("jgbh").value.length == 4) {
					if (eventState != "004034") {
						if(eventState != "004032") {
							doUpdateEventState(itemValue);
						}
					}
					window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&updateFlag=1&readContol=11&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
				// 总队用户
				} else {
					if($("jgbh").value.length == 2) {
						if(eventState == "004034") {
							doUpdateEventState(itemValue);
						}
					}
					window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&readContol=99&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
				}
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			}
			
		} else {
			if(nowState != eventState) {
				alert("支队已签收，不能对该消息进行处理！");
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			} else {
				window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			}
		}
	}
	if (id == "view") {
		if($("jgbh").value.length == 2) {
			if(eventState == "004037" || eventState == "004035" || eventState == "004036") {
				window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&hideBtn=false&readContol=99&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			} else {
				updateMaterialInfo(itemValue,eventState);
				window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&updateFlag=1&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
				if(flag == "all") {
					doSearchAll();
				} else if(flag == "jq") {
					doSearchMaterialInfo();
				} else {
					doSearch($("jgbh").value);
				}
			} 
		} else if($("jgbh").value.length == 4) {
			if(eventState == "004033" || eventState == "004037"){
				updateMaterialInfo(itemValue,eventState);
			}
			window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&updateFlag=1&insrtOrUpdt=2&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
			if(flag == "all") {
				doSearchAll();
			} else if(flag == "jq") {
				doSearchMaterialInfo();
			} else {
				doSearch($("jgbh").value);
			}
		} else {
			if(message == "支队下发") {
				window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&message=1&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
			} else {
				window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&message=0&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
			}
			if(flag == "all") {
				doSearchAll();
			} else if(flag == "jq") {
				doSearchMaterialInfo();
			} else {
				doSearch($("jgbh").value);
			}
		}
		
	}
	if (id == "flow") {
		// 新增续报画面
		window.showModalDialog("materialFlow.jsp?tmp=" + Math.random()+"&insrtOrUpdt=3&alarmId=" + itemValue, "", "dialogWidth:700px;dialogHeight:430px");
		if(flag == "all") {
			doSearchAll();
		} else if(flag == "jq") {
			doSearchMaterialInfo();
		} else {
			doSearch($("jgbh").value);
		}
	}
	if (id == "flow1") {
		window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=5&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
	}
	if (id == "flow2") {
		window.showModalDialog("materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=4&alarmId=" + itemValue, "", "dialogWidth:900px;dialogHeight:570px");
	}
	if (id == "search") {
		if(flag == "all") {
			doSearchAll();
		} else if(flag == "jq") {
			doSearchMaterialInfo();
		} else {
			doSearch(itemValue);
		}
	}
}

function checkNum(ele,event){
	if(event.keyCode < 48 || event.keyCode > 57){
		event.keyCode=0;
		return;	
	}
}
//function validateInput() {
//	var input;
//	var select;
//	var textarea;
//	var reg =  /^[^~@\'\;#\$\^&]*$/;
//	input = document.getElementsByTagName("input");
//	textarea = document.getElementsByTagName("textarea");
//	for (var i = 0; i < input.length; i++) {
//		var item = input[i];
//		if (item.type == "text" && !item.readOnly) {
//			if(!reg.test(item.value)){
//				alert("不可输入特殊字符,包括:'、;、~、@、#、$、^、&");
//				item.focus();
//				return false;
//			}
//		}
//	}
//	for (var i = 0; i < textarea.length; i++) {
//		var item = textarea[i];
//		if (item.type != "button" && !item.readOnly) {
//			if(!reg.test(item.value)){
//				alert("不可输入特殊字符,包括:'、;、~、@、#、$、^、&");
//				item.focus();
//				return false;	
//			}
//		}
//	}
//	return true;
//}

function CheckFile(filepath){
	var filename = filepath.substring(filepath.lastIndexOf('.')+1,filepath.length).toLowerCase();

	switch(filename){
		case "":
			alert("上传文件不可为空!");
			return false;
		case "zip":
		case "rar":
		case "xls":
		case "txt":
		case "jpg":
		case "gif":
		case "doc":
		case "ppt":
		case "pdf":
		case "docx":
		case "xlsx":
		case "pptx":
		case "cad":
			return true;
		default:
			alert("对不起!您选择的文件格式不对，可上传的文件类型有：jpg,gif,txt,doc,docx,ppt,pptx,xls,xlsx,pdf,zip,rar,cad");
			return false;
	}
}
function timeShow(ele){
	var d = new Date();
	var year = d.getYear(); 
	var month = d.getMonth()+1; 
	month = month > 9 ? month : ('0' + month);
	var date = d.getDate();
	date = date > 9 ? date : ('0' + date);
	var hour = d.getHours();
	hour = hour > 9 ? hour : ('0' + hour);
	var min = d.getMinutes();
	min = min > 9 ? min : ('0' + min);
	
	ele.value = year + "-" + month + "-" + date + " " + hour + ":" + min;
	SelectDateTime(ele);
}

/**
 * flagXZ为0时为新增
 * @param {} daytime
 * @param {} flagXZ
 */
function showDiv(daytime,buttonFlag){
	// 事件类别checkbox值的取得
	var ISTHREEUP = $('ISTHREEUP').checked?"1":"0";
	var ISBUS = $('ISBUS').checked?"1":"0";
	var ISSCHOOLBUS = $('ISSCHOOLBUS').checked?"1":"0";
	var ISDANAGERCAR = $('ISDANAGERCAR').checked?"1":"0";
	var ISFOREIGNAFFAIRS = $('ISFOREIGNAFFAIRS').checked?"1":"0";
	var ISPOLICE = $('ISPOLICE').checked?"1":"0";
	var ISMASSESCASE = $('ISMASSESCASE').checked?"1":"0";
	var ISCONGESTION = $('ISCONGESTION').checked?"1":"0";
	var doBox = $('doBox').checked?"1":"0";
	var ISOthersItem = $('ISOthersItem').checked?"1":"0";
	
	if($('TELENUM')){
		if(!$('TELENUM').value){
			alert("请填写联系电话！");
			$('TELENUM').focus();
			return;
		}
	}
	// 取得checkbox值的check
	if(ISTHREEUP == '0' && ISBUS== '0' && ISSCHOOLBUS=='0' && ISDANAGERCAR=='0' && ISFOREIGNAFFAIRS=='0'
		 && ISPOLICE=='0' && ISMASSESCASE=='0' && ISCONGESTION=='0' && ISOthersItem=='0'){
		alert("请至少选择一种类型！")	;
		return;
	}
	
	// 判断死亡人数
	var DEATHPERSONCOUNT = $('DEATHPERSONCOUNT') ? $('DEATHPERSONCOUNT').value : '';
	if (DEATHPERSONCOUNT) {
		if ((ISTHREEUP == '0' && DEATHPERSONCOUNT >= 3)
				|| (ISTHREEUP == '1' && DEATHPERSONCOUNT < 3)) {
			alert("死亡人数与所选类型不符！");
			$('DEATHPERSONCOUNT').focus();
			return;
		}
	}
	
	var TITLE = $('TITLE').value;
	if(!TITLE){
		alert('请填写标题！');
	$('TITLE').focus();
		return;
	}
/*	if(!$('ALARMTIME') || $('ALARMTIME').value == ""){
		alert("请填写接警时间！");
		return;	
	}
	if(!$('RECEIVEPERSON') || $('RECEIVEPERSON').value == ""){
		alert("请填写接警人！");
		$('RECEIVEPERSON').focus();
		return;	
	}*/
	
	
	
	if($('DEATHPERSONCOUNT')){
		if(!$('DEATHPERSONCOUNT').value){
			alert("请填写死亡人数！");
			$('DEATHPERSONCOUNT').focus();
			return;
		}
	}
	if($('BRUISEPERSONCOUNT')){
		if(!$('BRUISEPERSONCOUNT').value){
			alert("请填写受伤人数！");
			$('BRUISEPERSONCOUNT').focus();
			return;
		}
	}
	var regExp=/^\d+(\.\d+)?$/;
    if(regExp .test ($('DEATHPERSONCOUNT').value)) {
    	
     } else {
     	alert("死亡人数必须是数字！");
		$('DEATHPERSONCOUNT').focus();
		return;
     }
     if(regExp .test ($('BRUISEPERSONCOUNT').value)) {
    	
     } else {
     	alert("受伤人数必须是数字！");
		$('BRUISEPERSONCOUNT').focus();
		return;
     }
	if($('flow0_text')){
		if(!$('flow0_text').value) {
			alert("请填写情况描述！");
			$('flow0_text').focus();
			return;
		} else if($('flow0_text').value.length >= 4000) {
			alert("请填写事故情况描述在4000字内！");
			$('flow0_text').focus();
			return;
		}
	}
	if ($('ddapprover_text')) {
		if (!$('ddapprover_text').value) {
			alert("请填写大队审核人！");
			$('ddapprover_text').focus();
			return;
		}
	}
	if(buttonFlag != "3") {
		if($('flow2_text')){
			if(!$('flow2_text').value){
				alert("请填写支队处警情况！");
				$('flow2_text').focus();
				return;
			} else if($('flow2_text').value.length >= 4000) {
				alert("请填写支队处置情况在4000字内！");
				$('flow2_text').focus();
				return;
			}
		}
	}
	if ($('zdapprover_text')) {
		if (!$('zdapprover_text').value) {
			alert("请填写支队审核人！");
			$('zdapprover_text').focus();
			return;
		}
	}
	
	if(buttonFlag != "4") {
		if($('flow7_text')){
			if(!$('flow7_text').value){
				alert("请填写领导批示！");
				$('flow7_text').focus();
				return;
			} else if($('flow7_text').value.length >= 500) {
				alert("请填写领导批示在500字内！");
				$('flow7_text').focus();
				return;
			}
		}
		if($('flow8_text')){
			if(!$('flow8_text').value){
				alert("请填写办理情况！");
				$('flow8_text').focus();
				return;
			} else if($('flow8_text').value.length >= 500) {
				alert("请填写办理情况在500字内！");
				$('flow8_text').focus();
				return;
			}
		}
		
		if($("doBox")){
			if ($("doBox").checked) {
				if($('flow6_text')){
					if(!$('flow6_text').value){
						alert("请填写总队转发领导批示意见！");
						$('flow6_text').focus();
						return;
					} else if($('flow6_text').value.length >= 500) {
						alert("请填写总队转发领导批示意见在500字内！");
						$('flow6_text').focus();
						return;
					}
				}
			}
		}
		if ($('zodapprover_text')) {
			if (!$('zodapprover_text').value) {
				alert("请填写总队审核人！");
				$('zodapprover_text').focus();
				return;
			}
		}
	}
	
	if(!validateInput()) {
		return;
	}
	
	var ISTHREEUP = $('ISTHREEUP').checked?"1":"0";

	var insrtOrUpdt = $("insrtOrUpdt").value;
	var str = '<div style="height:80%;"><table class="table2" width="100%" id="block"><tr><td class="tdtitle">上报时间</td><td class="tdvalue"><input  ID="REPORTTIME_SB" name="REPORTTIME_SB" type="text" value="'+daytime+'" readonly></td></tr>' +
				'<tr><td class="tdtitle">填报人</td><td class="tdvalue"><input  ID="REPORTPERSON_SB" name="REPORTPERSON_SB" maxlength=30 type="text" ></td></tr>' +
				'<tr><td class="tdtitle">电话</td><td class="tdvalue"><input  ID="TELNUMSB" name="TELNUMSB" maxlength=30 type="text" ></td></tr>' +
				'<tr><td class="tdtitle">审批领导</td><td class="tdvalue"><input  ID="CHECKPERSON_SB" name="CHECKPERSON_SB" maxlength=30 type="text"></td></tr>' +
				'</table></div>';
	var jgbh = $("jgbh").value;		
	if (buttonFlag != '0' && insrtOrUpdt != "0" && (jgbh.length != 6) && (buttonFlag != '2') && (buttonFlag != '4')) {
		if(buttonFlag == '5') {
			modify(3, buttonFlag);
		} else {
			if (confirm("是否确认报送？")) {
				modify(1, buttonFlag);
			}
		}
		
		/*if (!$('msg')) {
			var div = document.createElement('div');
			div.id = 'msg';
			document.body.appendChild(div);
		}
		$('msg').setStyle({
					position : 'absolute',
					zIndex : '20000',
					width : '300px',
					height : '120px',
					marginLeft : "-150px",
					marginTop : "-100px",
					background : 'white',
					border : '1px solid #000',
					left : '50%',
					top : '50%'
				})
		str = str
				+ '<div style="text-align:center;"><span class="lsearch" style="margin-right:20px;"><a href="#" onclick="modify(1,'
				+ buttonFlag + ');">';
		str = str
				+ '<span class="lbl">确认</span></a></span>\
		<span class="lsearch" style="margin-right:20px;"><a href="#" onclick="$(\'msg\').remove();">\
		<span class="lbl">返回</span></a></span></div>';
		$('msg').innerHTML = str;*/
	} else {
		if ("2" == buttonFlag) {
			if($("doBox")){
				if ($("doBox").checked) {
					if (confirm("是否确认下发？")) {
						modify(1, buttonFlag);
					}
				} else {
					if (confirm("是否确认处理？")) {
						modify(1, buttonFlag);
					}
				}
			}
		} else if ("3" == buttonFlag || "4" == buttonFlag) {
			if (confirm("是否确认签收？")) {
				modify(1, buttonFlag);
			}
		} else {
			if (confirm("是否确认上报？")) {
				if (buttonFlag != '0') {
					modify(0, buttonFlag);
				} else {
					modify(1, buttonFlag);
				}
			}
		}
	}
}

/**
 * * 显示附件信息的链接<br>
 */
function openWPS(fileName) {

	UDload.createTempIFRAME();
	Submit.urlToForm(UDload.DOWNLOAD_URL,{fileName:fileName,isOpen:"false"},UDload.TEMP_IFRAME_ID);
	return;
//	var widthv = 400;
//	var heightv = 200;
//	var xposition = (screen.availWidth - widthv)/2;
//	var yposition = (screen.availHeight - heightv)/2;
//	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';	
//	var param= "../FileDownload.jsp?fileName="+fileName;
//	param=encodeURI(param);
//	window.open(param, "",feature);
 }

function writeAble () {
	if(confirm("确认编辑补充？")) {
		$("trafficInfo").innerHTML = '<textarea id="flow0_text" style="width:617px;height:100px;overflow-y:scroll;" >'
			+ $("flow0_text").value + '</textarea>';
		var flow2_str = '<textarea class="text" rows="4" id="flow2_text" REPORTKIND="004038" style="width:617px;height:50px;overflow-y:scroll;"></textarea>'
			$('flow2').innerHTML = flow2_str;
	}
}

function doUpdateEventState(alarmId) {
		
	var params = {};
	params["jgbh"] = $("jgbh").value;
	params["alarmId"] = alarmId; 
	var url = "dispatch.cmaterialInfo.updateEventState.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp) {}});

}
function updateMaterialInfo(searchAlarmId,searchEventState) {
	// 警情状态的更新方法的呼出
	var url = "dispatch.cmaterialInfo.updateMaterialInfo.d";
	url = encodeURI(url);
	var params = "searchAlarmId="+searchAlarmId + "&searchEventState=" + searchEventState;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp) {}});
}

/**
 * 打印生成Word文档<br/>
 * 
 */
function printWord1 (daytime) {
	
	var bh = encodeURI($("ALARMIDVALUE")?$("ALARMIDVALUE").value:" ");
	var jstime = encodeURI($("REPORTTIME")?$("REPORTTIME").value:" ");
	var bsdw = encodeURI($("reportunitName")?$("reportunitName").value:" ");
	var bsr = encodeURI($("RECEIVEPERSON")?$("RECEIVEPERSON").value:" ");
	var jsdw = encodeURI($("REPORTUNITVALUE")?$("REPORTUNITVALUE").value:" ");
	var zbrName = encodeURI($("zbrName")?$("zbrName").value:" ");
	var TITLE = encodeURI($("TITLE")?$("TITLE").value:" ");
	// 机构编号
	var jgbh = encodeURI($("jgbh")?$("jgbh").value:" ");
	var jsr = encodeURI(" ");
	var ldps = encodeURI(noticeLD);
	//Modifieb by Liuwx 2011-07-08
	var clqk = noticeCL;
	var printjgmc = encodeURI($("printjgmc")?$("printjgmc").value:" ");
	//Modification finished
	var desc = encodeURI($("flow0_text")?$("flow0_text").value:" ");
	if(jgbh.length == 2) {
		window.open("printPatten.jsp?bh=" + bh +"&jstime=" + jstime + "&bsdw=" + bsdw + 
		"&bsr=" + bsr + "&jsdw=" + jsdw + "&jsr=" + jsr + "&ldps=" 
		+ ldps + "&desc=" + desc+ "&zbrName=" + zbrName+ "&TITLE=" + TITLE + "&clqk=" + clqk
		+"&printjgmc="+printjgmc);
	} else if (jgbh.length == 4) {
		window.open("printPattenZd.jsp?bh=" + bh +"&jstime=" + jstime + "&bsdw=" + bsdw + 
		"&bsr=" + bsr + "&jsdw=" + jsdw + "&jsr=" + jsr + "&ldps=" 
		+ ldps + "&desc=" + desc+ "&zbrName=" + zbrName+ "&TITLE=" + TITLE+ "&clqk=" + clqk
		+"&printjgmc="+printjgmc);
	} else if (jgbh.length == 6) {
		jsdw = "";
		window.open("printPattenDd.jsp?bh=" + bh +"&jstime=" + jstime + "&bsdw=" + bsdw + 
		"&bsr=" + bsr + "&jsdw=" + jsdw + "&jsr=" + jsr + "&desc=" + desc
		+ "&zbrName=" + zbrName+ "&TITLE=" + TITLE+"&printjgmc="+printjgmc);
	}
}

function getContent_(content,jgmc,pname,time){
	return "<p style='margin:5 0;text-indent:24px;'>"+content+"</p>&nbsp;&nbsp;&nbsp;&nbsp;[ "+jgmc+" "+pname+" "+time+" ]";
}
	
function printWord(){
	if(materialInfoXML){
		var params = {};
		var jgid = $("jgid").value;
		var jgmc = $("dname").value;
		var name = $("zbrName").value;
		//事故信息
		var materialInfo = materialInfoXML.getElementsByTagName("row")[0].childNodes;
		var id = materialInfo[0].text;
		var title = materialInfo[19].text;
		var content = materialInfo[28].text;
		var state = materialInfo[23].text;
		var rpdcode = materialInfo[1].text;
		var rtime = materialInfo[3].text;
		var rpname = materialInfo[25].text;
		var xbInfos = materialInfoXML.getElementsByTagName("xbinfo");
		var flowInfos = materialInfoXML.getElementsByTagName("flow");
		var clxxzts=["004038","004040","004041","004039"],clxxzt;
		var clxxnrs = {};
		for(var i=0;i<flowInfos.length;i++){
			clxxzt = flowInfos[i].childNodes[4].text;
			if(clxxnrs[clxxzt]){
				clxxnrs[clxxzt].push(flowInfos[i].childNodes);
			}else{
				clxxnrs[clxxzt] = [];
				clxxnrs[clxxzt].push(flowInfos[i].childNodes);
			}
		}
		//相关机构信息
		var jginfo = materialInfoXML.getElementsByTagName("jginfo")[0];
		jginfo = jginfo.text.split(";");
		var jgids = jginfo[0].split(",");
		var jgmcs = jginfo[1].split(",");
		//发送机构信息
		var sendInfo = materialInfoXML.getElementsByTagName("sendInfo");
		if(sendInfo && sendInfo.length == 1){
			sendInfo = sendInfo[0].text;
			sendInfo = sendInfo.split(",");
		}
		//赋值
		params.id = id;
		params.cujgmc = jgmc;
		params.rtime = rtime;
		params.title = title;
		params.content = "<p style='margin:5 0;text-indent:24px;'>"+content+"</p>";
		if(xbInfos.length >= 1){
			params.xb = "";
			var cols;
			for(var i=0;i<xbInfos.length;i++){
				//xb = "<p style='margin:5 0;text-indent:24px;'>"+xbInfos[i].childNodes[3].text+"</p>";
				cols = xbInfos[i].childNodes;
				params.xb += getContent_(cols[3].text, cols[4].text, cols[1].text, cols[2].text);
			}
		}
		//总队值班员、支队值班员
		var zodzby,zdzby;
		//支队处警情况
		var zdcjqks = clxxnrs["004038"];
		if(zdcjqks){
			params.zdcjqk = "";
			var cols;
			for(var i=0;i<zdcjqks.length;i++){
				cols = zdcjqks[i];
				//params.zdcjqk += "<p style='margin:3 0;text-indent:24px;'>"+zdcjqks[i][1].text+"</p>";
				params.zdcjqk += getContent_(cols[1].text,cols[3].text,cols[6].text,cols[2].text).replace(/<\/?.+?>/g,"");
			}
			zdzby = zdcjqks[zdcjqks.length-1][6].text;
		}
		
		if(jgid.substring(2,4)=="00"){
			var ldpss = clxxnrs["004039"];
			if(ldpss){
				params.ldps = "";
				for(var i=0;i<ldpss.length;i++){
					params.ldps += "<p style='margin:3 0;text-indent:24px;'>"+ldpss[i][1].text+"</p>";
				}
			}
			var blqks = clxxnrs["004040"];
			if(blqks){
				params.blqk = "";
				for(var i=0;i<blqks.length;i++){
					params.blqk += "<p style='margin:3 0;text-indent:24px;'>"+blqks[i][1].text+"</p>";
				}
				zodzby = blqks[blqks.length-1][6].text;
				params.ctime = blqks[blqks.length-1][2].text;
			}
			
			params.fsdw = jgmcs[jgmcs.length-2];
			params.fszby = zdcjqks[zdcjqks.length-1][6].text;
			params.jsdw = jgmc;
			params.jszby = name;
		}else if(jgid.substring(4,6)=="00"){
			var stpsyjs = clxxnrs["004041"];
			if(stpsyjs){
				params.stpsyj = "";
				for(var i=0;i<stpsyjs.length;i++){
					params.stpsyj += "<p style='margin:3 0;text-indent:24px;'>"+stpsyjs[i][1].text+"</p>";
				}
				zodzby = stpsyjs[stpsyjs.length-1][6].text;
			}
			if(jgid == rpdcode){
				params.fsdw = jgmc;
				params.fszby = name;
				params.jsdw = jgmcs[jgmcs.length-1];
				params.jszby = zodzby||"";
			}
			else {
				params.jsdw = jgmc;
				params.jszby = name;
				if(jgid.substring(0,4)==rpdcode.substring(0,4)){
					params.fsdw = jgmcs[0];
					params.fszby = rpname;
				}
				else{
					if(sendInfo && sendInfo.length == 2){
						params.fsdw = sendInfo[0];
						params.fszby = sendInfo[1];
					}else{
						params.fsdw = jgmcs[jgmcs.length-1];
						params.fszby = zodzby||"";
					}
				}
			}
		}else{
			if(jgid == rpdcode){
				params.fsdw = jgmc;
				params.fszby = name;
				params.jsdw = jgmcs[1];
				params.jszby = zdzby||"";
			}
			else{
				if(sendInfo && sendInfo.length == 2){
					params.fsdw = sendInfo[0];
					params.fszby = sendInfo[1];
				}else{
					params.fsdw = jgmcs[message];
					params.fszby = "";
				}
				params.jsdw = jgmc;
				params.jszby = name;
			}
		}
		var lzjl = $("adstateTd");
		if(lzjl){
			lzjl = lzjl.innerText;
			if(lzjl.replace(/ /,"")){
				params.lzjl = lzjl;
			}
		}
		var url = "materialPrint.jsp";
		var w = 400;
		var h = 200;
		var l = window.screenLeft+(document.body.offsetWidth-w)/2;
		var t = window.screenTop + (document.body.offsetHeight-h)/2;
		//var print = window.open("","print","left="+l+",top="+t+",width="+w+",height="+h+"");
		UDload.createTempIFRAME();
		Submit.urlToForm(url,params,UDload.TEMP_IFRAME_ID);
		//setTimeout(function(){print.close()},2000);
	}else{
		alert("获取打印信息出错！");
	}
}
/*
function printWord(){
	if(materialInfoXML){
		var params = {};
		var jgid = $("jgid").value;
		var jgmc = $("dname").value;
		var name = $("zbrName").value;
		var materialInfo = materialInfoXML.getElementsByTagName("row")[0].childNodes;
		var title = materialInfo[19].text;
		var content = materialInfo[28].text;
		var state = materialInfo[23].text;
		var infoName = materialInfo[25].text;
		var xbInfos = materialInfoXML.getElementsByTagName("xbinfo");
		var flowInfos = materialInfoXML.getElementsByTagName("flow");
		var clxxzts=["004038","004040","004041","004039"],clxxzt;
		var clxxnrs = {};
		for(var i=0;i<flowInfos.length;i++){
			clxxzt = flowInfos[i].childNodes[4].text;
			if(clxxnrs[clxxzt]){
				clxxnrs[clxxzt].push(flowInfos[i].childNodes);
			}else{
				clxxnrs[clxxzt] = [];
				clxxnrs[clxxzt].push(flowInfos[i].childNodes);
			}
		}
		var jginfo = materialInfoXML.getElementsByTagName("jginfo")[0];
		jginfo = jginfo.text.split(";");
		var jgids = jginfo[0].split(",");
		var jgmcs = jginfo[1].split(",");
		params.title = title;
		params.content = "<p style='margin:5 0;text-indent:24px;'>"+content+"</p>";
		if(xbInfos.length >= 1){
			params.xb = "";
			for(var i=0;i<xbInfos.length;i++){
				params.xb += "<p style='margin:5 0;text-indent:24px;'>"+xbInfos[i].childNodes[3].text+"</p>";
			}
		}
		if(jgid.substring(2,4)=="00"){
			params.zdcjqk = "";
			var zdcjqks = clxxnrs["004038"];
			for(var i=0;i<zdcjqks.length;i++){
				params.zdcjqk += "<p style='margin:3 0;text-indent:24px;'>"+zdcjqks[i][1].text+"</p>";
			}
			if(state == "004034" || state == "004043" || state == "004036"){//支队报送，未签收或者已签收或者处理完成
				params.fsdw = jgmcs[1];
				var zdcjqks = clxxnrs["004038"];
				params.fszby = zdcjqks[zdcjqks.length-1][6].text;
				params.jsdw = jgmc;
				params.jszby = name;
			}else if(state == "004037" || state == "004035"){//总队下发，未签收或者已签收
				params.fsdw = jgmc;
				params.fszby = name;
				params.jsdw = jgmcs[1];
				var zdcjqks = clxxnrs["004038"];
				params.jszby = zdcjqks[zdcjqks.length-1][6].text;
			}
			if(state == "004036" || state == "004037" || state == "004035"){//处理完成或者下发
				params.ldps = "";
				var ldpss = clxxnrs["004039"];
				for(var i=0;i<ldpss.length;i++){
					params.ldps += "<p style='margin:3 0;text-indent:24px;'>"+ldpss[i][1].text+"</p>";
				}
				params.blqk = "";
				var blqks = clxxnrs["004040"];
				for(var i=0;i<blqks.length;i++){
					params.blqk += "<p style='margin:3 0;text-indent:24px;'>"+blqks[i][1].text+"</p>";
				}
				if(state != "004036"){
					var stpsyjs = clxxnrs["004041"];
					params.fszby = stpsyjs[stpsyjs.length-1][6].text;
					params.stpsyj = "";
					for(var i=0;i<stpsyjs.length;i++){
						params.stpsyj += "<p style='margin:3 0;text-indent:24px;'>"+stpsyjs[i][1].text+"</p>";
					}
				}
				//总队值班员
				zodzby = stpsyjs[stpsyjs.length-1][6].text;
			}
			//如果是支队填报
			if(jgid == rpdcode){
				params.fsdw = jgmc;
				params.fszby = name;
				params.jsdw = jgmcs[jgmcs.length-1];
				params.jszby = zodzby||"";
			}
		}else if(jgid.substring(4,6)=="00"){
			if(state == "004033" || state == "004042" || state == "004037" || state == "004035"){
				params.jsdw = jgmc;
				params.jszby = name;
				if(state == "004033" || state == "004042" ){//大队上报，未签收或者已签收
					params.fsdw = jgmcs[2];
					params.fszby = infoName;
				}else if(state == "004037" || state == "004035"){//总队下发，未签收或者已签收
					params.fsdw = jgmcs[0];
					var stpsyjs = clxxnrs["004041"];
					params.fszby = stpsyjs[stpsyjs.length-1][6].text;
					params.stpsyj = "";
					for(var i=0;i<stpsyjs.length;i++){
						params.stpsyj += "<p style='margin:3 0;text-indent:24px;'>"+stpsyjs[i][1].text+"</p>";
					}
					params.zdcjqk = "";
					var zdcjqks = clxxnrs["004038"];
					for(var i=0;i<zdcjqks.length;i++){
						params.zdcjqk += "<p style='margin:3 0;text-indent:24px;'>"+zdcjqks[i][1].text+"</p>";
					}
				}
			}else if(state == "004034" || state == "004043" || state == "004036"){//支队报送，未签收或者已签收或者处理完成
				params.fsdw = jgmc;
				params.fszby = name;
				params.jsdw = jgmcs[0];
				if(state == "004036"){
					params.jszby = clxxnrs["004040"][clxxnrs["004040"].length-1][6].text;
				}else{
					params.jszby = "";
				}
				params.zdcjqk = "";
				var zdcjqks = clxxnrs["004038"];
				for(var i=0;i<zdcjqks.length;i++){
					params.zdcjqk += "<p style='margin:3 0;text-indent:24px;'>"+zdcjqks[i][1].text+"</p>";
				}
			}
		}else{
			params.fsdw = jgmc;
			params.fszby = name;
			params.jsdw = jgmcs[1];
			if(state == "004032" || state == "004033"){
				params.jszby = "";
			}else{
				var zdcjqks = clxxnrs["004038"];
				if(zdcjqks){
					params.jszby = zdcjqks[zdcjqks.length-1][6].text;
				}else{
					params.jszby = "";
				}
			}
		}
		var url = "materialPrint.jsp";
		Submit.urlToForm(url,params);
	}else{
		alert("获取打印数据出错");
	}
	
}
*/
/*function showPrintFile ( xmlHttp ) {
	var xmldoc = xmlHttp.responseXML;
	rows = xmldoc.getElementsByTagName("row");
	cells = rows[0].getElementsByTagName("cell");
	var wordPatten = "";
	for (i=0;i<cells.length;i++) {
		wordPatten += cells[i].text + "/"
	}
	wordPatten = wordPatten + "广东省公安厅交通管理局值班日志.doc";
	wordPatten = "../广东省公安厅交通管理局值班日志.doc";
	alert(wordPatten);
	toWordFile(wordPatten);
}

function toWordFile(wordPatten) {
	var bh = '20081223';
	var jstime = '2008/12/23 15:50';
	var bsdw = '部局';
	var bsr = '王  兵';
	var jsdw = '局值班室';
	var jsr = '毕敬立';
	var ldps = '同意';
	var desc = '如果在重大警情路程内，此出信息是在点击“打印”按钮后，打印出来的警情的详细信息（模板中的内容）。\n'+ 
	'如果为总队直接下发的，此出为总队自己填写的详细信息。';
	var bh = $("ALARMIDVALUE")?$("ALARMIDVALUE").value:"";
	var jstime = $("REPORTTIME")?$("REPORTTIME").value:"";
	var bsdw = $("reportunitName")?$("reportunitName").value:"";
	var bsr = $("RECEIVEPERSON")?$("RECEIVEPERSON").value:"";
	var jsdw = $("REPORTUNITVALUE")?$("REPORTUNITVALUE").value:"";
	var jsr = "";
	var ldps = "";
	var desc = $("flow0_text")?$("flow0_text").value:"";
	
	if($("flow0_text")) {
		desc = $("flow0_text").value;
	}
	var wdapp=new ActiveXObject("Word.Application");
	wdapp.visible=true;
	wddoc=wdapp.Documents.Open(wordPatten); //打开指定的文档
	//输出编号
    range =wdapp.ActiveDocument.Bookmarks("bh").Range;
    range.Text=bh; 
    //输出报送时间
    range =wdapp.ActiveDocument.Bookmarks("bssj").Range;
    range.Text=jstime; 
    //输出报送单位
    range =wdapp.ActiveDocument.Bookmarks("bsdw").Range;
    range.Text=bsdw; 
    //输出报送人
    range =wdapp.ActiveDocument.Bookmarks("bsr").Range;
    range.Text=bsr;
	//输出接收单位
    range =wdapp.ActiveDocument.Bookmarks("jsdw").Range;
    range.Text=jsdw;
	//输出接收人
    range =wdapp.ActiveDocument.Bookmarks("jsr").Range;
    range.Text=jsr;
	//输出领导批示
    range =wdapp.ActiveDocument.Bookmarks("ldps").Range;
    range.Text=ldps;
    //输出会议内容
    range =wdapp.ActiveDocument.Bookmarks("xxxx").Range;
    range.Text=desc;    
    wddoc.Application.Printout();
    wdapp=null;
}*/

/**
 * 续报内容的保存<br/>
 * @param {} alarmId
 */
function insertNewXb(insertId,deptname,jgbh) {
	if(!validateInput())	return;
	// 录入项目的非空检查
	var PERSONWRITE = $("PERSONWRITE").value;
	var REPORTTIME = $("REPORTTIME").value;
	var XBNR = $("XBNR").value;
	
	if(PERSONWRITE==null || PERSONWRITE==""){
		alert("必须录入填报人！");
		return;
	}

	if ($('DEATHPERSONCOUNT')) {
		if (!$('DEATHPERSONCOUNT').value) {
			alert("请填写死亡人数！");
			$('DEATHPERSONCOUNT').focus();
			return;
		}
	}
	
	var regExp1 = /^\d+(\.\d+)?$/;
	if (!regExp1.test($('DEATHPERSONCOUNT').value)) {
		alert("死亡人数必须是数字！");
		$('DEATHPERSONCOUNT').focus();
		return;
	}
	
	
	if ($('BRUISEPERSONCOUNT')) {
		if (!$('BRUISEPERSONCOUNT').value) {
			alert("请填写受伤人数！");
			$('BRUISEPERSONCOUNT').focus();
			return;
		}
	}
	
	if (regExp1.test($('BRUISEPERSONCOUNT').value)) {
	
	} else {
		alert("受伤人数必须是数字！");
		$('BRUISEPERSONCOUNT').focus();
		return;
	}
	
	var regExp = new RegExp("^(0|([1-9][0-9]*))$");
	if ($('KMVALUE')) {
		if (regExp.test($('KMVALUE').value) == false) {
			if($('KMVALUE').value != "") {
				alert("事故位置千米值请输入数字！");
				$("KMVALUE").focus();
				return;
			}
		}
	}

	if ($('MVALUE')) {
		if ($('MVALUE').value && regExp.test($('MVALUE').value) == false) {
			alert("事故位置百米值请输入数字！");
			$("MVALUE").focus();
			return;
		}
	}
	
	if($('roadname').innerText != "") {
		if($('KMVALUE').value == "" && $('roadNote').value == "") {
			alert("事故位置或者路段必须填写其中一个！");
			$('KMVALUE').focus;
			return;
		} 
	} else {
		if($('roadNote').value == "") {
			alert("请填写路段！");
			$('roadNote').focus;
			return;
		}
	}
	
	if(XBNR == null || XBNR == ""){
		alert("必须录入续报内容！");
		return;
	} else if($("XBNR").value.length > 4000) {
		  alert("输入的续报内容长度不能超过4000个字符!");
		  return ;
	}
	
	var DEATHPERSONCOUNT = $('DEATHPERSONCOUNT').value;
	var BRUISEPERSONCOUNT = $('BRUISEPERSONCOUNT').value;
	var roadNote = $('roadNote').value;
	var KMVALUE = $('KMVALUE').value;
	var MVALUE = $('MVALUE').value;
	var roadname = $('roadname').innerText;
	var roaddirection = $('roaddirection').value;
	//附件id
	var fjid = $('fjid').value;
	
	var url = "dispatch.cmaterialInfo.insertNewXb.d";
		url = encodeURI(url);
		var params = "insertId=" + insertId + "&PERSONWRITE=" + PERSONWRITE  +
		"&REPORTTIME=" + REPORTTIME  + "&XBNR=" + XBNR+ "&deptname=" + deptname+ "&jgbh=" + jgbh
		+ "&DEATHPERSONCOUNT=" + DEATHPERSONCOUNT + "&BRUISEPERSONCOUNT=" + BRUISEPERSONCOUNT 
		+ "&roadNote=" + roadNote + "&KMVALUE=" + KMVALUE + "&MVALUE=" + MVALUE + "&roadname=" + roadname + "&roaddirection=" + roaddirection + "&fjid=" + fjid + "&fjlocal=" + fjlocal;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showAfterXb});
}

function showAfterXb(xmlHttp) {
	var result = xmlHttp.responseText;
	if(result == "true"){
		$('resFlag').value = result;
		alert("续报成功！");
		window.close();
	}else if(result == "false"){
		alert("续报失败！");		
	}
}

function createXbInfo (xmlDoc) {
	var xbinfos = xmlDoc.getElementsByTagName("xbinfo");
	var str = "";
	for(i=0;i<xbinfos.length;i++){
		var cols = xbinfos[i].getElementsByTagName("col");
//		str += '<div>'+ cols[3].text+ '</div><div style="border-bottom: 0 solid #a5d3ef;text-align:right;padding-right:20px">'
//		+ cols[4].text +"     "+ cols[1].text +"      "+ cols[2].text + '</div>';
		str += getXBContent(cols[3].text, cols[4].text, cols[1].text, cols[2].text, cols[5].text, cols[6].text);
	}
	$('xbTr').show();
	$("InfoRegionXB").innerHTML = str;
}

function getXBContent(TEXT, FSJG, FSRY, FSSJ, FJID, FJPath) {
	var xbStr = '<div>' + getXBContentPart(TEXT, FSJG, FSRY, FSSJ, FJID, FJPath);
	+'</div>';
	return xbStr;
}

function getXBContentPart(content,dname,pname,time,fjid,fjpath){
	var fjStr;
	//显示录入的内容
	content = "<textarea cols='69' style='overflow:visible;border: 0;text-indent:24px;line-height:100%' readonly='readonly'>"+content +"</textarea>" + "\n";
	//单位名称、值班人名字、时间
	var str = "";
	if(dname){
		str += dname + " ";
	}
	str += pname + " " + time + " ";
	
	//附件链接
	var filePath = fjpath.replace(/^　$/,'');
	if(filePath != "") {
		var str1 = filePath;
		var regstr = "/";
		var regresult = new RegExp(regstr)
		var sss = str1.split(regresult, '100');
		var need = sss[sss.length - 1];
		var a = need.split('.');
		fjStr = "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;附件：<a href=\"#\" style=\"border-bottom: 1 solid green \" title='查看附件' onclick=\"openWPS('" + fjpath + "')\" >" + a[0] + "." + a[1] + "</a><br>";
	} else {
		fjStr = "";
	}
	
	//接收单位
	str = "&nbsp;&nbsp;&nbsp;&nbsp;<textarea cols='69' style='overflow:visible;border: 0;text-indent:10px;line-height:200%' readonly='readonly'>["+str +"]</textarea>";
	return content + fjStr + str;
}

// 删除附件
function deleteAttachmentFile(fileId,alarmIdDelete) {
	var url = "dispatch.cmaterialInfo.deleteAttachmentFile.d";
	url = encodeURI(url);
	var params = "fileId=" + fileId + "&alarmIdDelete=" + alarmIdDelete;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDeleteFile});
}

// 删除附件后的显示处理
function showDeleteFile (xmlHttp) {
	
	var xmlDoc = xmlHttp.responseXML;
	var rows;
	try {
		rows = xmlDoc.getElementsByTagName("row");
	} catch (ex) {
		rows = null;
	}
	var show = "";
	var insrtOrUpdt = $('insrtOrUpdt').value;
	    if(rows != null) {
	    	show = '' + 
		            '<input id="mfile0" type="file" name="mfile0" style="border: 1px #7B9EBD solid" size="62"  /> ' + 
					'<INPUT TYPE="button" id="addbtn" NAME="addbtn" style="border: 1px #7B9EBD solid;height: 20px;" value="增加附件" ' + 
					'	onclick="ocAddFile(\'uploadTable\',\'fileCounter\',5);">' + 
					'<input type="hidden" id="fileCounter" value="0">' + 
		            '<table id="uploadTable" class="uploadTable" border="0" cellpadding="0" cellspacing="0">' + 
					'</table>';
		    for(var  i=0;i<rows.length;i++){
		    	var rowFile = rows[i].getElementsByTagName("col");;
			    if (rowFile[2].text != "null" && rowFile[2].text != "" && rowFile[2].text != null) {
				    var filePath = rowFile[2].text.replace(/^　$/,'');
				    var str1 = filePath;
					var regstr = "/";
					var regresult = new RegExp(regstr)
					var sss = str1.split(regresult, '100');
					var need = sss[sss.length - 1];
					var a = need.split('.');
					if(a[0]==null || a[0] =="" || a[0]=="undefined" || a[1]==null || a[1] =="" || a[1]=="undefined") {
										
					} else {
						if(i==0) {
							show = show + " <a href=\"#\" style=\"border-bottom: 1 solid green \" onclick=\"openWPS('" + filePath + "')\" >" + a[0] + "." + a[1] + "</a> ";
						} else {
							show = show + " <br/><a href=\"#\" style=\"border-bottom: 1 solid green \" onclick=\"openWPS('" + filePath + "')\" >" + a[0] + "." + a[1] + "</a> ";
						}
					}
					// 大队(6位) 点击“修改”时追加“删除”按钮
					show = show + "<input type=\"image\" src=\"../../images/button/btndelete1.gif\"  value='删除附件' onclick=\"deleteAttachmentFile('"+rowFile[0].text+"','" + rowFile[4].text + "')\" style=\"border: 1px #7B9EBD solid\" /> "
			    }
		    }
		}
		$('fileList').innerHTML = show;
		alert('删除成功！');
}

/** 
    * desc:查询
    * param:
    * return:
    * author：dxn
    * date: 2010-4-2
    * version:
    */
function showExcelInfo(str,jgbh) {
	var jgmc = $("jgmc")?$("jgmc").value:null;
	var tbr = $("tbr")?$("tbr").value:null;
	var tbsjStart = $("tbsjStart")?$("tbsjStart").value:null;
	var tbsjEnd = $("tbsjEnd")?$("tbsjEnd").value:null;
	var dlmc = $("dlmc")?$("dlmc").value:null;
	var ldmc = $("ldmc")?$("ldmc").value:null;
	var sjlb = $("sjlb")?$("sjlb").value:null;
	var jgid = $('jgid')?$('jgid').value:null;
	var searchAlarmId = $('searchAlarmId')?$('searchAlarmId').value:null;
//	jgbh = jgbh?jgbh:$('jgbh').value;
	var acdeptsql = "(select id from t_oa_acceptdept where aid=al.alarmid and rpdcode='440000000000' and adid is null)";
	var whereJg = " or (instr(f_get_accdept(al.alarmid,3,1)," + jgid
	var sql = "";
	if(jgbh.length == 6) {
		sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
		sql += " as reportunit, al.reportperson, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
		sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc from";
		sql += "  T_ATTEMPER_ACCIDENT ac, T_ATTEMPER_ALARM al where ";
		sql += " al.alarmid = ac.alarmid and al.EVENTSTATE in('004032','004033','004042')  and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
		sql += " = '001024' and (subStr(al.REPORTUNIT,0,6) = '" + jgbh + "')";
	} else {
		if(jgbh.length == 4) {
			    sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
				sql += " as reportunit, al.reportperson, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
				sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc from ";
				sql += "  T_ATTEMPER_ACCIDENT_ZD ac,  T_ATTEMPER_ALARM_zd al where ";
				sql += "  al.alarmid = ac.alarmid and al.EVENTSTATE in('004033','004042','004043','004034','004035','004037','004036','004031') and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
				sql += " = '001024' and (subStr(al.REPORTUNIT,0,4) = '" + jgbh + "')";
		} else if(jgbh.length == 2) {
				sql = "select al.alarmid, al.title, (select jgmc from t_sys_department where jgid=al.reportunit)";
				sql += " as reportunit, al.reportperson, to_char(al.reporttime, 'yyyy-mm-dd HH24:mi') as reporttime, ";
				sql += "(select name from t_attemper_code where id=al.EVENTSTATE) as lc from ";
				sql += "   T_ATTEMPER_ACCIDENT_ZD ac,  T_ATTEMPER_ALARM_zd al where ";
				sql += " al.alarmid = ac.alarmid and al.EVENTSTATE in('004034','004035','004036','004037','004043','004031') and al.EVENTSOURCE = '002022' and al.EVENTTYPE";
				sql += " = '001024' ";
		}
	}
	var searchDepID = ""; 
	if($("depUnitId")) {
		searchDepID = $("depUnitId").value; 
	}
	if (searchDepID != "") {
		var depSub = "";
	    if("0000" == searchDepID.substring(2,6)){
	    	depSub = searchDepID.substring(0,2);
	    }else if("00" == searchDepID.substring(4,6)){
	    	depSub = searchDepID.substring(0,4);
	    }else{
	    	depSub = searchDepID.substring(0,6);
	    }
	    // 查询单位"总队"时的处理
	    if(depSub.length == 2 ) {
			sql +=" and al.ALARMREGIONID like '" + depSub + "%' ";
	    // 查询单位"支队"时的处理
	    } else if(depSub.length == 4 ) {
			sql +=" and al.ALARMREGIONID like '" + depSub + "%' ";
	    // 查询单位"大队"时的处理
	    } else {
			sql +=" and al.ALARMREGIONID = '" + searchDepID + "' ";
	    }
	}

	if(searchAlarmId) {
		sql += " and al.alarmid like '%" + searchAlarmId + "%'";
	}
	if(tbr) {
		sql += " and al.reportperson like '%" + tbr + "%'";
	}
	if ($('tbsjStart') && $('tbsjEnd')) {
		if ($('tbsjStart').value!="" && $('tbsjEnd').value!="") {
			sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') >= '" + $('tbsjStart').value
				+ " 00:00' AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') <= '" + $('tbsjEnd').value + " 23:59'";
		}else if($('tbsjStart').value!="" && $('tbsjEnd').value==""){
			sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') >= '" + $('tbsjStart').value + " 00:00'";
		}else if($('tbsjStart').value=="" && $('tbsjEnd').value!=""){
			sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') <= '" + $('tbsjEnd').value + " 23:59'";
		}
	}

	if(dlmc) {
		sql += " and al.TITLE like '%" + dlmc + "%'";
	}
	if(ldmc) {
		sql += " and al.EVENTSTATE = '" + ldmc + "'";
	}
	if(sjlb) {
		if(sjlb == '99') {
			sql += " and ac.DEATHPERSONCOUNT > '3' ";
		} else {
			sql += " and ac." + sjlb + " = '1'";
		}
	}
	
	sql += " order by al.reporttime desc";

 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
	var url = "dispatch.cmaterialInfo.showExcel.d?" + "searchSql=" + sql;
	url = encodeURI(url);
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");
}


function ocAddFile(tableId, counterId, maxNum){
	var counter = $(counterId).value;
	var tableObj = $(tableId);
	var td = document.createElement("td");
	var tr = document.createElement("tr");
	var tbody = document.createElement("tbody");
	counter ++;
	var html = "<INPUT TYPE=\"FILE\" size=\"62\" id=\"file\" style='border: 1px #7B9EBD solid' "
		 + counter + "\" NAME=\"file"
		 + counter + "\">"
		 + "&nbsp;<a href='#' onClick=\""
		 + "ocRemoveFile('" + tableId 
		 + "',this.parentNode.parentNode.parentNode);\"><input type='button' style='border: 1px #7B9EBD solid;height: 20px;' value='删　　除'></a>";
	td.innerHTML = html;

	tr.appendChild(td);
	tbody.appendChild(tr);

	var tchild = tableObj.getElementsByTagName("tbody");
	var childNum = tchild.length;
	if(childNum <= maxNum){
		//tableObj.insertBefore(tbody,tableObj.firstChild);
		tableObj.appendChild(tbody);	//add file
		setValue(counterId, counter);	//set counter++
	}else{
		return false;
	}
}

function ocRemoveFile(tableId, node){
	var tableObj = $(tableId);
	tableObj.removeChild(node);
}
function setValue(objId, value){
	try{
		$(objId).value = value;
	}catch(e){}
}

var Road = {
	init : true,
	roadLevel : {
		def : "1",
		init : function(def){
			var labels = ["高速公路","国道","省道"];
			var codes = [1,2,3]; 
			var select = $("roadLevelValueId");
			for(var i=0;i<labels.length; i++){
				var option = document.createElement("option");
				option.label = labels[i];
				option.value = codes[i];
				select.add(option);
			}
			var options = select.options;
			for(var i=0;i<options.length;i++){
				options[i].innerText = options[i].label;
			}
			if(def){
				select.value = def;
			}else if(Road.roadLevel.def){
				select.value = Road.roadLevel.def;
			}
		},
		onchange : function(e){
			Road.roadName.init(e.value);
		}
	},
	roadName : {
		level : "1",
		defs : ["","",""],
		init : function(level, def){
			var cid = "alarmRoad_TrafficCrowd_td";
			var roadName = "ROADID";
			var dlmc = ["gbdm||':'||dlmc","dlmc","dlmc"];
			if(!level){
				level = Road.roadName.level;
			}
			var sql = "select dlmc id, "+dlmc[Number(level)-1]+" mc  " +
				"from T_OA_DICTDLFX where roadlevel='"+level+"' order by mc";
			fillListBox(cid,roadName,"160",sql,"请选择", "Road.roadName.callBack('"+roadName+"','"+def+"')");
		},
		callBack : function(id, def){
			var select = $(id);
			if(select != null){
				var options = select.options;
				if(options.length > 0){
					//select.removeChild(select.options[0]);
				}
				if(def && def != "undefined"){
					select.value = def;
				}else {
					var def = Road.roadName.defs[Number(Road.roadName.level)-1];
					if(def && def != ""){
						select.value = def;
					}
				}
				var jgid = $("jgid").value;
				var insrtOrUpdt = $("insrtOrUpdt").value;
				var rw = false;
				if(!(jgid.substring(4,6)!="00" && insrtOrUpdt=="1")){
					rw = true;
				}
				select.disabled = rw;
				//setValue222();
			}
		}
	}
}

function pageInit(level, def){
	Road.roadLevel.init(level);
	Road.roadName.init(level, def);
}

function setValue222() {
	$("caseHappenPlace").value=$("ROADID").value + $("KMVALUE").value+ "k+" + $("MVALUE").value + "米处";
}

function dispatch(){
	function replace(ori,rep,is,ie){
		var nchar = ori.substring(0,is);
		nchar += rep;
		nchar += ori.substring(ie);
		return nchar;
	}
	var jgids = reportunit;
	jgids += "," + replace(reportunit,"00",4,6);
	jgids += "," + replace(reportunit,"0000",2,6);
	Flow.showDispatch($("ALARMID").value,jgids,"adcode",357, 285);
}
var RoadSelect = {
	show : function(sid,tid){
		var rs = $(sid);
		if(rs){
			if(rs.style.display == "none"){
				var tg = $(tid);
				if(tg){
    				var l = Event.getXY(tg,true);
					with(rs.style){
    					top = l.y  + tg.offsetHeight;
    					left = l.x;
					}
				}
				rs.style.display = "block";
			}else{
				rs.style.display = "none";
			}
		}
	},
	close : function(id){
		var el = $(id);
		if(el){
			el.style.display = "none";
		}
	}
}

/**
 * 设置onmouseover背景色<br/>
 * add by zhaoyu 2010-12-30
 */
function mouseover(trobj){
    if(trobj.style.background != "#8ed9ee"){
       trobj.style.background = "#C8FFFF";//"#6CA6D4";
    }
}

/**
 * 设置onmouseout背景色<br/>
 * add by zhaoyu 2010-12-30
 */
function mouseout(trobj){
    if(trobj.style.background != "#8ed9ee"){
       trobj.style.background = "";
    }
}

function materialInfoTree() {

	if($('flow2_text')){
		if(!$('flow2_text').value){
			alert("请填写支队处警情况！");
			$('flow2_text').focus();
			return;
		} else if($('flow2_text').value.length >= 4000) {
			alert("请填写支队处置情况在4000字内！");
			$('flow2_text').focus();
			return;
		}
	}
	
	function replace(ori,rep,is,ie){
		var nchar = ori.substring(0,is);
		nchar += rep;
		nchar += ori.substring(ie);
		return nchar;
	}
	var jgids = $('REPORTUNIT').value;
	jgids = replace(reportunit,"00",4,6);
	var daytime = $('daytime').value;
	var dmname = "showDiv(\\\'"+daytime+"\\\',\\\'5\\\')";
	Flow.showDispatch($("ALARMID").value,jgids,"adcode",357,285,dmname,11);
}

function uploadAttachment(alarmID) {
	var k = window.showModalDialog("materialFlowUpload.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&readContol=11&alarmId=" + alarmID, window, "dialogWidth:700px;dialogHeight:150px");
}

function clearData() {
	if($('fjid').value != "") {
		deleteAttachmentFile($('fjid').value, $('insertYwid').value);
	}
	$('fjlocal').value = "";
	$('fjid').value = "";
	$('filebutton').value = " 新 增 ";
}

function showMoreCondition(){
	if($('moreConditionTr').visible()){
		$('defalutTitleTr').show();
		$('moreConditionTr').hide();
        $('simpleButtomTd').show();
        $('moreButtomTr').hide();
		$('sgbt_lable').hide();
		$('dlmc').hide();
		$('tbr_lable').hide();
		$('tbr').hide();
//		$('moreLable').innerHTML = "高级查询";
		clearElementValue();
	}else{
		$('defalutTitleTr').hide();
		$('moreConditionTr').show();
        $('simpleButtomTd').hide();
        $('moreButtomTr').show();
		$('sgbt_lable').show();
		$('dlmc').show();
		$('tbr_lable').show();
		$('tbr').show();
//		$('moreLable').innerHTML = "简单查询";
	}
}

function clearElementValue(){
	$('dlmc').value = "";
	$('tbr').value = "";
	$('jgmc').value = "";
    $('depUnitId').value = "";
	$('sjlb').options[0].selected = true;
	$('ldmc').options[0].selected = true;
}