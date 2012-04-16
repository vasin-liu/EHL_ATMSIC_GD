var clzt_0 = "0";//待总队处理
var clzt_1 = "1";//待大队核实
var clzt_2 = "2";//待总队确认
var clzt_3 = "3";//处理完成
var clzt_4 = "4";//已忽略

var page;

/** 
 * desc:交通报料查询
 * param:
 * return:
 * author：ldq
 * date: 2011-3-16
 * version:
 */
function doSearch(tag) {
	var num = 1;
	var colQzlc = "";
	var sql = "SELECT BH,DLMC,LDFX,(select begin||','||end from t_oa_dictdlfx where dlmc = exch.dlmc),"
			+ "QSLC,QSLCM,ZZLC,ZZLCM,LK,LKYY,TO_CHAR(LRSJ,'YYYY-MM-DD HH24:MI'),CLZT,XXLY";
	sql += " FROM EXCH_T_TRAFFICNEWSFEEDS_TEMP exch WHERE 1=1 ";
	if (tag == 2 || !$("LK")) {
	} else {
		if ($("LK").value) {
			sql += " AND LK='" + $("LK").value + "'";
		}
		if ($("CLZT").value) {
			sql += " AND CLZT='" + $("CLZT").value + "'";
		}
		if ($("DLMC").value) {
			sql += " AND DLMC LIKE '%" + $("DLMC").value + "%'";
		}
		if ($("sj1").value != "" && $("sj2").value != "") {
			if ($("sj1").value > $("sj2").value) {
				alert("路况时间输入有误！");
				return false;
			}
			sql += " AND LRSJ>=TO_DATE('" + $("sj1").value
					+ "','YYYY-MM-DD HH24:MI:SS') AND LRSJ<=TO_DATE('"
					+ $("sj2").value + "','YYYY-MM-DD HH24:MI:SS')";
		} else if ($("sj1").value != "" && $("sj2").value == "") {
			sql += " AND LRSJ>=TO_DATE('" + $("sj1").value
					+ "','YYYY-MM-DD HH24:MI:SS')";
		} else if ($("sj1").value == "" && $("sj2").value != "") {
			sql += " AND LRSJ<=TO_DATE('" + $("sj2").value
					+ "','YYYY-MM-DD HH24:MI:SS')";
		}
	}
	switch ($("jglx").value) {
	case '0':
		if (!$("CLZT")) {
			sql += "AND (CLZT='" + clzt_0 + "' OR CLZT='" + clzt_2 + "') ";
		}
		break;
	case '1':
		sql += "AND GXDD LIKE '" + $("jgmc").value.substring(0, 2) + "%' ";
		if (!$("CLZT")) {
			sql += "AND (CLZT='" + clzt_1 + "' OR CLZT='" + clzt_2
					+ "' OR CLZT='" + clzt_3 + "') ";
		} else {
			if (!$("CLZT").value) {
				sql += "AND (CLZT='" + clzt_1 + "' OR CLZT='" + clzt_2
						+ "' OR CLZT='" + clzt_3 + "') ";
			}
		}
		break;
	case '2':
		sql += "AND ((GXDD=(SELECT OTHERNAME FROM T_SYS_DEPARTMENT WHERE JGID='"
				+ $("jgid").value + "')) ";
		sql += " or  (GXDD=(SELECT jgmc FROM T_SYS_DEPARTMENT WHERE JGID='"
				+ $("jgid").value + "')) ) ";
		if (!$("CLZT")) {
			sql += "AND CLZT='" + clzt_1 + "' ";
		} else {
			if (!$("CLZT").value) {
				sql += "AND (CLZT='" + clzt_1 + "' OR CLZT='" + clzt_2
						+ "' OR CLZT='" + clzt_3 + "') ";
			}
		}
		break;
	}
	sql += " ORDER BY LRSJ DESC,DLMC ASC";

	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
	if (num != 1 && num != 0) {
		sleep(250);
		onTurnToPage(num);
	}
}
function sleep(timeout) {
	window
			.showModalDialog("javascript:document.writeln('<script>window.setTimeout(function(){window.close();},"
					+ timeout + ");<\/script>');");
}

function getObjData(cols, dataRCols) {
	var data;
	//	alert(cols.length + ":" + dataRCols.length)
	if (cols && dataRCols) {
		data = {};
		for ( var i = 0; i < cols.length; i++) {
			data[cols[i]] = dataRCols[i].text;
		}
	}
	return data;
}

function getObjDatas(cols, dataRows) {
	var datas;
	if (dataRows) {
		datas = [];
		for ( var i = 0; i < dataRows.length; i++) {
			var data = getObjData(cols, dataRows[i].childNodes);
			if (data) {
				datas.push(data);
			}
		}
	}
	return datas;
}

function showResultsPage(xmldoc) {
	function getShowValue(value) {
		value = value || "";
		return value == "null" ? "" : value;
	}
	function getClztDesc(clzt) {
		var clztDescs = [ "待总队处理", "待大队核实", "待总队确认", "处理完成", "已忽略" ];
		clzt = parseInt(clzt);
		return clztDescs[clzt];
	}
	function getRDirection(rd, qzdm) {
		var rdR;
		if (rd && qzdm) {
			qzdm = qzdm.split(",");
			var rdDescs = [ qzdm[0] + "->" + qzdm[1], qzdm[1] + "->" + qzdm[0],
					"双向拥堵" ];
			if (rd == "0" || rd == "1" || rd == "2") {
				rd = parseInt(rd);
				rdR = rdDescs[rd];
			} else if (rd.substring(0, 1) == "往") {
				rd = rd.substring(1);
				if (rd == qzdm[0]) {
					rdR = rdDescs[1];
				} else if (rd == qzdm[1]) {
					rdR = rdDescs[0];
				}
			} else if (rd == "双向") {
				rdR = rdDescs[2];
			}
		}
		return rdR;
	}
	function getQzlc(qslc, qslcm, zzlc, zzlcm) {
		var qzlc = "";
		qslc = getShowValue(qslc);
		qslcm = getShowValue(qslcm);
		zzlc = getShowValue(zzlc);
		zzlcm = getShowValue(zzlcm);
		if (qslc != "") {
			qslc += "KM";
		}
		if (qslcm != "") {
			qslcm += "M";
		}
		if (zzlc != "") {
			zzlc += "KM";
		}
		if (zzlcm != "") {
			zzlcm += "M";
		}
		qzlc += qslc + qslcm;
		if (qzlc != "" && (zzlc + zzlcm)) {
			qzlc += "至";
		}
		qzlc += zzlc + zzlcm;
		return qzlc;
	}

	var rows = xmldoc.getElementsByTagName("row");
	var cols = [ "bh", "dlmc", "ldfx", "qzdm", "qslc", "qslcm", "zzlc",
			"zzlcm", "lk", "lkyy", "lrsj", "clzt", "xxly" ];
	var datas = getObjDatas(cols, rows);
	var str = "<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>";
	str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='13%' class='td_r_b td_font'>道路名称</td>";
	str += "<td width='10%' class='td_r_b td_font'>方向</td>";
	str += "<td width='13%' class='td_r_b td_font'>起止里程</td>";
	str += "<td width='8%' class='td_r_b td_font'>交通路况</td>";
	str += "<td width='9%' class='td_r_b td_font'>拥堵原因</td>";
	str += "<td width='15%' class='td_r_b td_font'>报料时间</td>";
	str += "<td width='10%' class='td_r_b td_font'>信息来源</td>";
	str += "<td width='10%' class='td_r_b td_font'>处理状态</td>";
	if ($(hdcount).value == "1") {
		str += "<td width='4%' class='td_r_b td_font'>查看</td>";
	} else if ($(hdcount).value == "2") {
		str += "<td width='4%' class='td_r_b td_font'>处理</td>";
	}

	switch ($("jglx").value) {
	case '0':
		str += "<td width='4%' class='td_r_b td_font'>删除</td>";
		break;
	case '1':
		break;
	case '2':
		//			str += "<td width='5%' class='td_r_b td_font'>处理</td>";
		break;
	}
	str += "</tr>";
	if (datas.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		var data;
		for ( var i = 0; i < datas.length; i++) {
			data = datas[i];
			str += "<tr align='center'>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getShowValue(data.dlmc) + "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getShowValue(getRDirection(data.ldfx, data.qzdm))
					+ "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getQzlc(data.qslc, data.qslcm, data.zzlc, data.zzlcm)
					+ "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getShowValue(data.lk) + "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getShowValue(data.lkyy) + "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getShowValue(data.lrsj) + "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getShowValue(data.xxly) + "</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"
					+ getClztDesc(data.clzt) + "</td>";
			var uIName = "update.gif";
			var dIName = "btndelete1.gif";
			if (1 == 2) {
				uIName = "update.gif";
				dIName = "btndelete1.gif";
			}
			str += "<td class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" "
					+ "onClick=\"showView('"
					+ data.bh
					+ "','"
					+ $("jglx").value
					+ "','"
					+ data.clzt
					+ "','"
					+ $("hdcount").value + "')\" alt='查看详细信息'></td>"
			switch ($("jglx").value) {
			case '0':
				if (data.clzt == clzt_0 || data.clzt == clzt_4) {
					str += "<td class='td_r_b td_font' align=\'center\'><input alt='删除信息' type=\"image\" src=\"../../images/button/"
							+ dIName
							+ "\" onClick=\"doDelete('"
							+ data.bh
							+ "')\"></td>";
				} else {
					dIName = "btndelete2.gif";
					str += "<td class='td_r_b td_font' align=\'center\'><input alt='删除信息' type=\"image\" src=\"../../images/button/"
							+ dIName + "\" onClick=\"disDelete()\"></td>";
				}
				break;
			case '1':
				break;
			case '2':
				//					str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input alt='处理交通报料信息' type=\"image\" src=\"../../images/button/"
				//					+ dIName + "\" onClick=\"feedbackNews('" + results[0].text + "')\"></td>";
				break;
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

//展示报料信息
function showView(bh, lglx, clzt, insrtOrUpdt) {
	var herght = '720px';
	if (clzt == clzt_0 || clzt == clzt_4) {
		herght = '480px';
	}
	if (clzt == clzt_1) {
		if ($("jglx").value == '2') {
			herght = '600px';
		} else {
			herght = '480px';
		}
	} else if (clzt == clzt_2) {
		if ($("jglx").value != '0') {
			herght = '600px';
		}
	}

	var returnValue = window.showModalDialog("TrafficNewsFeeds.jsp?tmp="
			+ Math.random() + "&bh=" + bh + "&jglx=" + lglx + "&insrtOrUpdt="
			+ insrtOrUpdt, "", "dialogWidth:660px;dialogHeight:" + herght + "");
	if (returnValue == "1") {
		doSearch();
	}
}

//删除报料信息
function doDelete(bh) {
	returnValue = "1";
	if (confirm("是否确定删除？")) {
		var url = "dispatch.feedNews.delNewsInfo.d";
		var params = "bh=" + bh;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showDelResponse
		});
	}
}
function showDelResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == 'true') {
		alert("删除成功！");
		doSearch();
	} else if (xmlDoc == 'false') {
		alert("删除失败！");
	}
}
function disDelete() {
	alert("已经处理完成，不能删除！");
}

//总队下达给大队
function cancelNews(bh) {
	returnValue = "1";
	var url = "dispatch.feedNews.upNewsInfo.d";
	var params = "bh=" + bh + "&clzt=" + clzt_4;
	params = encodeURI(params);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showUploadResponse
	});
}
function showUploadResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == 'true') {
		alert("处理成功！");
		window.close();
	} else if (xmlDoc == 'false') {
		alert("处理失败！");
	}
}

//查询报料详细信息
function doQueryInfo(bh, jglx) {
	$("lrr").readOnly = true;
	var ctime = new Date().format_(5);
	$("qssj").value = ctime;
	$("hssj").value = ctime;
	$("scsj").value = ctime;
	var url = "dispatch.feedNews.getNewsInfo.d";
	var params = "bh=" + bh;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showQueryResponse
	});
}

function getRDCode(rddesc) {
	var rdcode;
	if (rddesc) {
		var reg = /^往(.+)$/;
		var rdend = reg.exec(rddesc);
		if (rdend) {
			rdend = rdend[1];
			var rds = [ "rdForward", "rdBack", "double" ];
			for ( var i = 0; i < rds.length; i++) {
				if ($(rds[i]).innerText == rdend) {
					rdcode = $("RADIOTYPE_" + (i + 1)).value;
					break;
				}
			}
		} else {
			rdcode = rddesc;
		}
	}
	return rdcode
}

function setRDirection(value) {
	if (value) {
		var reg = /^往(.+)$/;
		var rddesc = reg.exec(value);
		if (rddesc) {
			rddesc = rddesc[1];
			var rds = [ "rdForward", "rdBack", "double" ];
			for ( var i = 0; i < rds.length; i++) {
				if ($(rds[i]).innerText == rddesc) {
					$("RADIOTYPE_" + (i + 1)).checked = true;
					break;
				}
			}
		} else {
			var rd;
			for ( var i = 0; i < 3; i++) {
				rd = $("RADIOTYPE_" + (i + 1));
				if (value == rd.value) {
					rd.checked = true;
					break;
				}
			}
		}

	}
}

function getData(xmlHttp) {
	var data = null;
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var cols = [ "bh", "roadlevel", "dlmc", "ldmc", "ldfx", "qslc", "qslcm", "zzlc",
			"zzlcm", "lk", "qssj", "zzsj", "gxdd", "wzms", "lkyy", "sfjs",
			"blr", "lxfs", "lrr", "lrsj", "clzt", "hsr", "hssj", "hsqk", "scr",
			"scsj", "scyj", "jf", "ctime", "wzms", "pname", "zdxfsj", "iscs",
			"sfcy", "bz" ];
	if (results != null && results.length > 0) {
		data = {};
		for ( var i = 0; i < cols.length; i++) {
			data[cols[i]] = results[i].text;
		}
	}
	return data;
}

function setReadState(ids, rtype) {
	for ( var i = 0; i < ids.length; i++) {
		eval("if($(ids[i]))$(ids[i])." + rtype + "=true;");
	}
}

function showQueryResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	materialInfoXML = xmlDoc;
	var results = xmlDoc.getElementsByTagName("row")[0]
			.getElementsByTagName("col");

	if ($('jgbh').value.length != 2) {
		$('searchDept').style.display = "none";
		$('deptList').style.display = "none";
		$('gxdd').style.width = "160px";
	}
	
	var data = getData(xmlHttp);
	if (data) {
		for ( var attr in data) {
			if ($(attr) && data[attr]) {
				$(attr).value = data[attr];
			}
		}
		//alert(data.sfjs)
		$("zdxfsj").innerHTML = data.zdxfsj;
		$("sfcswhs").value = data.iscs;
		$("ldfx").value = getRDCode(data.ldfx);
		setRDirection(data.ldfx);
		var jglx = $("jglx").value
		var clzt = data.clzt;
		p_clzt = clzt;
		pageInit(results[1].text, results[2].text);
		function setGxddOR() {
			$("deptList").style.display = "none";
			$("searchDept").style.display = "none";
			$("gxdd").style.width = "160px";
			$("gxdd").ondblclick = "";
		}
		function setCrowdOR() {
			var dids = [ "roadLevel", "dlmc", "lkyy", "RADIOTYPE_1",
					"RADIOTYPE_2", "RADIOTYPE_3" ];
			setReadState(dids, "disabled");
			var rids = [ "ldmc", "qslc", "qslcm", "qssj", "zzlc", "zzlcm",
					"gxdd", "lk", "lkyy", "bz", "blr", "lxfs" ];
			setReadState(rids, "readOnly");
			setGxddOR();
			$("qssj").onclick = "";
		}
		function setDdhsOR() {
			setReadState( [ "sfjs" ], "disabled");
			setReadState( [ "hsr", "hssj", "hsqk" ], "readOnly");
			$("hssj").onclick = "";
		}
		function setZdhsOR() {
			setReadState( [ "scr", "scsj", "scyj" ], "readOnly");
			setReadState( [ "cy", "wcy" ], "disabled");
			$("scsj").onclick = "";
		}

		
		if (clzt == clzt_0) {
			if(jglx == "0"){
				if ($("page").value == "1") {
					$("zdqdxf").style.display = "none";
					$("ignore").style.display = "none";
				} else {
					$("zdqdxf").style.display = "inline";
					$("ignore").style.display = "inline";
				}
			}
		} else if (clzt == clzt_1) {
			$("zdsm").style.display = "inline";
			if (jglx == "0" || jglx == "1") {
				setCrowdOR();
			} else if (jglx == "2") {
				var rids = [ "blr", "bz", "lrr", "lrsj", "lxfs" ];
				setReadState(rids, "readOnly");
				setGxddOR();
				if($("page").value== "1"){
					$("ddqdhs").style.display = "none";
					setCrowdOR();
				}if($("page").value== "2"){
					$("ddhc").style.display = "inline";
					$("ddqdhs").style.display = "inline";
				}
			}
		} else if (clzt == clzt_2) {
			$("zdsm").style.display = "inline";
			setCrowdOR();
			$("ddhc").style.display = "inline";
			setDdhsOR();
			if (jglx == "0") {
				if($("page").value == "1"){
					$("zdhc").style.display = "none";
					$("zdclwb").style.display = "none";
					$("ddqdhs").style.display = "none";
				}else if ($("page").value == "2") {
					$("zdhc").style.display = "inline";
					$("zdclwb").style.display = "inline";
					$("ddqdhs").style.display = "none";
					$("ignore").style.display = "none";
					$("zdqdxf").style.display = "none";
				}
			}
		} else if (clzt == clzt_3) {
			$("zdsm").style.display = "inline";
			setCrowdOR();
			$("ddhc").style.display = "inline";
			setDdhsOR();
			$("zdhc").style.display = "inline";
			setZdhsOR();
			if (data.sfcy == "0") {
				$("cy").checked = true;
			} else {
				$("wcy").checked = true;
			}
		} else if (clzt == clzt_4) {
			setCrowdOR();
		}
	} else {
		alert("查询失败");
	}
}
//大队反馈报料信息
function feedbackNews(bh, type) {
	returnValue = "1";
	var clzt;
	if (type == "1") {
		clzt = clzt_0;
	} else if (type == "2") {
		clzt = clzt_1;
	} else if (type == "3") {
		clzt = clzt_2;
	}

	if (!checkParam(clzt)) {
		return;
	}

	var dlmc = $("dlmc").value;
	var ldmc = $("ldmc").value;
	var ldfx = $("ldfx").value;
	var qslc = $("qslc").value;
	var qslcm = $("qslcm").value;
	var zzlc = $("zzlc").value;
	var zzlcm = $("zzlcm").value;
	var lk = $("lk").value;
	var kssj = $("qssj").value;
	var zzsj = $("zzsj").value;
	var gxdd = $("gxdd").value;
	var wzms = $("bz").value;
	var lkyy = $("lkyy").value;
	var sfjs = $("sfjs").value;
	var blr = $("blr").value;
	var lxfs = $("lxfs").value;
	var lrr = $("lrr").value;
	var lrsj = $("lrsj").value;

	var sfcswhs = $("sfcswhs").value;//是否超时未核实
	//大队核实
	var hsr = $("hsr").value;
	var hssj = $("hssj").value;
	var hsqk = $("hsqk").value;

	var params = {};
	params["bh"] = bh;
	params["dlmc"] = dlmc;
	params["ldmc"] = ldmc;
	params["ldfx"] = ldfx;
	params["qslc"] = qslc;
	params["qslcm"] = qslcm;
	params["zzlc"] = zzlc;
	params["zzlcm"] = zzlcm;
	params["lk"] = lk;
	params["kssj"] = kssj;
	params["zzsj"] = zzsj;
	params["gxdd"] = gxdd;
	params["wzms"] = wzms;
	params["lkyy"] = lkyy;
	params["blr"] = blr;
	params["lxfs"] = lxfs;
	params["lrr"] = lrr;
	params["lrsj"] = lrsj;
	params["clzt"] = type;
	if (type == '2') {
		if (sfjs != "-1" && lkyy == "") {
			alert("请填写拥堵原因！");
			$("sfjs").focus();
			return false;
		}
		if (!hsr) {
			alert("请填写核实人！");
			$("hsr").focus();
			return false;
		} else {
			if (!checkChineseName(hsr)) {
				alert("核实人姓名请填写中文！");
				$("hsr").focus();
				return false;
			}
		}
		if (!checkNormalStr(hssj)) {
			alert("情况描述中包含非法字符！");
			$("hssj").focus();
			return false;
		}
		params["hsr"] = hsr;
		params["hssj"] = hssj;
		params["hsqk"] = hsqk;
		params["sfjs"] = sfjs;
		params["sfcswhs"] = sfcswhs;
	}

	//总队审查评分
	var scr = $("scr").value;
	var scsj = $("scsj").value;
	var scyj = $("scyj").value;
	if (type == '3') {
		if (!scr) {
			alert("请填写审查人！");
			$("scr").focus();
			return false;
		} else {
			if (!checkChineseName(scr)) {
				alert("审查人姓名请填写中文！");
				$("scr").focus();
				return false;
			}
		}
		if (!checkNormalStr(scyj)) {
			alert("审查意见中包含非法字符！");
			$("scyj").focus();
			return false;
		}
		var pf = '';
		var radios = document.getElementsByName("radios");
		for ( var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				pf = radios[i].value;
				break;
			}
		}
		params["hsr"] = hsr;
		params["hssj"] = hssj;
		params["hsqk"] = hsqk;
		params["sfjs"] = sfjs;
		params["sfcswhs"] = sfcswhs;
		params["scr"] = scr;
		params["scsj"] = scsj;
		params["scyj"] = scyj;
		params["pf"] = pf;
	}
	var url = "dispatch.feedNews.feedbackNewsInfo.d";
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showFeedbackResponse
	});
}

function showFeedbackResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == 'true') {
		if ($("jglx").value == '0') {
			alert("处理成功！");
			window.close();
		} else {
			alert("处理成功！");
			window.close();
		}
	} else if (xmlDoc == 'false') {
		if ($("jglx").value == '0') {
			alert("处理失败！");
		} else {
			alert("处理失败！");
		}
	}
}

function showDlNameLst() {
	var daoluType = $("sjlb").value;
	var sql = " select dlmc A, GBDM||' '||'：'||dlmc B,GBDM from T_OA_DICTDLFX "
			+ " where ROADLEVEL = '" + daoluType + "'"
			+ "group by dlmc, GBDM||' '||'：'||dlmc,GBDM order by GBDM ";
	fillListBox("daoluNameTd", "roadNameLst", "160", sql, "请选择", "",
			"onChangeDlValue");
}
function updateRoad() {
	if (!$('msg')) {
		var div = document.createElement('div');
		div.id = 'msg';
		document.body.appendChild(div);
	}
	$('msg').setStyle( {
		position : 'absolute',
		zIndex : '20000',
		width : '500px',
		height : '100px',
		marginLeft : "-220px",
		marginTop : "-10px",
		background : '#E6E6FA',
		border : '1px solid #000',
		left : '50%',
		top : '30%'
	})

	var strTable = "<table id=\"tabList\" class=\"table3\" width=100% border='0' borderColor='#a5d1ec'>";
	//生成表头
	strTable = strTable
			+ "<tr> "
			+ '<td class="tdtitle" bgcolor="#E6E6FA">道路类型：</td> '
			+ '<td class="tdvalue"> '
			+ '	<select style="width:155;border: 1px #7B9EBD solid" id="sjlb" onchange="showDlNameLst()"  > '
			+ '		<option value="1" >高速公路</option> '
			+ '		<option value="2" >国道</option> '
			+ '		<option value="3" >省道</option> ' + '	</select>' + '</td> '
			+ '<td class="tdtitle" bgcolor="#E6E6FA">道路名称：</td> '
			+ '<td class="tdvalue" id="daoluNameTd" > ' + "</td> " + "</tr>"
	strTable = strTable + "<tr> "
			+ '<td class="tdtitle" bgcolor="#E6E6FA">方向：</td> '
			+ '<td class="tdvalue" colspan=3 id="resetFxTd"> '
			+ '<input type="radio" name="RADIOTYPE" id="RADIOTYPE_1" > 上行 '
			+ '<input type="radio" name="RADIOTYPE" id="RADIOTYPE_2" > 下行 '
			+ '<input type="radio" name="RADIOTYPE" id="RADIOTYPE_3" > 双方向 '
			+ "</td> " + "</tr>"
	strTable = strTable
			+ "<tr> "
			+ '<td class="tdtitle" bgcolor="#E6E6FA">起始里程：</td> '
			+ '<td class="tdvalue"> '
			+ '	<input type="text" id="startLc" size="6" name="editinput" style="border: 1px #7B9EBD solid" >KM（数字）'
			+ '</td> '
			+ '<td class="tdtitle" bgcolor="#E6E6FA">终止里程：</td> '
			+ '<td class="tdvalue"> '
			+ '<input type="text" id="endLc" size="6" name="editinput" style="border: 1px #7B9EBD solid" >KM（数字）'
			+ "</td> " + "</tr>"

	strTable += "</table>";
	strTable += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	strTable += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	strTable += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	strTable += "<span class='lsearch' ><a href='#' onclick=\"resetRoadName();\" ><span class='lbl'>更新</span></a></span>";
	strTable += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	strTable += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	strTable += "<span class='lsearch' ><a href='#' onclick=\"$(\'msg\').remove();\" ><span class='lbl'>关闭</span></a></span>";

	var tabOjb = document.getElementById("msg");
	tabOjb.innerHTML = strTable;
	var sql = " select dlmc A, GBDM||' '||'：'||dlmc B,GBDM from T_OA_DICTDLFX "
			+ " where ROADLEVEL = '" + "1" + "'"
			+ "group by dlmc, GBDM||' '||'：'||dlmc,GBDM order by GBDM ";
	fillListBox("daoluNameTd", "roadNameLst", "160", sql, "请选择", "",
			"onChangeDlValue");
}

function onChangeDlValue() {
	var roadName = $('roadNameLst').value;
	var url = "dispatch.feedNews.getDlFxByRoadName.d";
	var params = "seasrchRoadName=" + roadName;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showResponseByRoadName
	});

}

var startRoadName = "";
var endRoadName = "";

function showResponseByRoadName(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var start = results[0].text;
	var end = results[1].text;
	startRoadName = start;
	endRoadName = end;
	var innerStr = '<input type="radio" name="RADIOTYPE" id="RADIOTYPE_1" >'
			+ start + "—>" + end
			+ '<input type="radio" name="RADIOTYPE" id="RADIOTYPE_2" > ' + end
			+ "—>" + start
			+ '<input type="radio" name="RADIOTYPE" id="RADIOTYPE_3" > 双方向 ';

	$("resetFxTd").innerHTML = innerStr;

}

function resetRoadName() {
	if ($('roadNameLst')) {
		if (!$('roadNameLst').value) {
			alert("请选择道路名称！");
			$('roadNameLst').focus();
			return;
		}
	}
	if ($('startLc')) {
		if (!$('startLc').value) {
			alert("请录入起始里程！");
			$('startLc').focus();
			return;
		}
	}
	if ($('endLc')) {
		if (!$('endLc').value) {
			alert("请录入终止里程！");
			$('endLc').focus();
			return;
		}
	}
	var roadFx = "";
	if ($("RADIOTYPE_1") && $("RADIOTYPE_2" && $("RADIOTYPE_3"))) {
		if ($("RADIOTYPE_1").checked) {
			roadFx = "往" + endRoadName;
		} else if ($("RADIOTYPE_2").checked) {
			roadFx = "往" + startRoadName;
		} else if ($("RADIOTYPE_3").checked) {
			roadFx = "双方向";
		} else {
			alert("请选择方向！");
			return;
		}
	}
	var roadName = $("roadNameLst").value;
	var startLc = $("startLc").value;
	var endLc = $("endLc").value;
	$("dlmc").value = roadName;
	$("ldfx").value = roadFx;
	$("qzlc").value = startLc + "KM至" + endLc + "KM";
	$('msg').remove();
}

function getRMileage() {
	var skm = $("skm").value;
	var sm = $("sm").value;
	var ekm = $("ekm").value;
	var em = $("em").value;
	var rm = skm + "KM" + sm + "M至" + ekm + "KM" + em + "M";
	return rm;
}

function changeRMileage(rmstr) {
	var rmjson = {
		skm : "",
		sm : "",
		ekm : "",
		em : ""
	};
	if (rmstr) {
		var reg = /^(\d+)KM((\d+)M|)至(\d+)KM((\d)+M|)$/;
		var rmarray = reg.exec(rmstr);
		$("a").innerHTML = jsonToString(rmarray)
		if (rmarray) {
			var indexs = [ 1, 3, 4, 6 ];
			var i = 0;
			for ( var attr in rmjson) {
				rmjson[attr] = rmarray[indexs[i]];
				i++;
			}
		}
	}
	return rmjson;
}

//-----------------------------------------------------------------

function pageInit(level, def) {
	page = $("page").value;
	Road.roadLevel.init(level);
	Road.roadName.init(level, def);
}
var p_clzt;
var Road = {
	init : true,
	roadLevel : {
		def : "1",
		init : function(def) {
			var labels = [ "高速公路", "国道", "省道" ];
			var codes = [ 1, 2, 3 ];
			var select = $("roadLevel");
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
			var dlmc = "dlmc";
			var dlmcs = [ "gbdm||':'||dlmc", "dlmc", "dlmc" ];
			if (!level) {
				level = Road.roadName.level;
			}
			var sql = "select dlmc id, " + dlmcs[Number(level) - 1] + " mc  "
					+ "from T_OA_DICTDLFX where roadlevel='" + level
					+ "' order by mc";
			fillListBox(cid, dlmc, "160", sql, "请选择",
					"Road.roadName.callBack('" + dlmc + "','" + def + "')",
					"onChangeValue");
		},
		callBack : function(id, def) {
			$("alarmRoad_TrafficCrowd_td").innerHTML = $("dlmc").outerHTML + "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
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
					if(p_clzt == clzt_2 || p_clzt == clzt_3 || p_clzt==clzt_4 || $("page").value == "1"){
						select.disabled = true;
					}
					Road.init = false;
				}
			}
		}
	}
}

function onChangeValue() {
	// 取得道路名称
	var roadid = $("dlmc").value;
	if (roadid == "") {
		return;
	}
	var url = "crowd.crowdmanage.getLcbptmisZt.d";
	var params = "dlmc=" + roadid;
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
	return;
	// 取得道路名称
	var roadid = $("dlmc").value;
	var str = "广东省内" + roadid + "里程";
	if (maxRoadLength == "　" || minRoadLength == "　") {
		str += "数据未记录";
	} else {
		str += "为" + minRoadLength + "km 至" + maxRoadLength + "km";
	}
	var showLength = $("showLength");
	if (showLength) {
		showLength.innerHTML = str;
	}
}

function getAddName() {
	var roadid = $("dlmc").value;
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
	if (xmlDoc != "null") {
		var roadSEAdd = xmlDoc.split(",");
		s = roadSEAdd[0];
		e = roadSEAdd[1];
	} else {
		s = "不明确起点";
		e = "不明确终点";
	}
	$("rdForward").innerText = s + "—>" + e;
	$("rdBack").innerText = e + "—>" + s;
	//	$("RADIOTYPE_1").value = "往" + e;
	//	$("RADIOTYPE_2").value = "往" + s;
	//	$("RADIOTYPE_3").value = "双向";
	//	$("RADIOTYPE_1").checked = true;
	//	$("ldfx").value = $("RADIOTYPE_1").value;
}

function checkParam(clzt) {

	if (clzt == clzt_1) {
		var sfjs = $("sfjs").value
		if (sfjs == "-1") {
			return true;
		}
	}

	var ldmc = $("ldmc");
	if (ldmc.value == "") {
		alert("请输入路段名称！");
		ldmc.focus();
		return false;
	}

	var qslc, zzlc;
	var regExp = new RegExp("^(0|([1-9][0-9]*))$");
	var kmS = $("qslc");
	if (kmS.value == "") {
		if (clzt == clzt_1) {
			alert("请输入起始里程千米值！");
			kmS.focus();
			return false;
		}
	} else {
		if (regExp.test(kmS.value) == false) {
			alert("起始里程千米值，请输入数字");
			kmS.focus();
			return false;
		} else {
			qslc = kmS.value;
		}
	}
	var ms = $("qslcm");
	if (ms.value == "") {
		if (clzt == clzt_1) {
			alert("请输入起始里程百米值！");
			ms.focus();
			return false;
		}
	} else {
		if (regExp.test(ms.value) == false) {
			alert("起始里程百米值，请输入数字");
			ms.focus();
			return false;
		} else {
			if (qslc) {
				qslc += "." + ms.value;
			}
		}
	}

	var kmE = $("zzlc");
	if (kmE.value == "") {
		if (clzt == clzt_1) {
			alert("请输入终止里程千米值！");
			kmE.focus();
			return false;
		}
	} else {
		if (regExp.test(kmE.value) == false) {
			alert("终止里程千米值，请输入数字！")
			kmE.focus();
			return false;
		} else {
			zzlc = kmE.value;
		}
	}
	var me = $("zzlcm");
	if (me.value == "") {
		if (clzt == clzt_1) {
			alert("请输入终止里程百米值！");
			me.focus();
			return false;
		}
	} else {
		if (regExp.test(me.value) == false) {
			alert("终止里程百米值，请输入数字！")
			me.focus();
			return false;
		} else {
			if (zzlc) {
				zzlc += "." + me.value;
			}
		}
	}
	
	if ((Number(kmS.value)*1000 + Number(ms.value)) > (Number(kmE.value)*1000 + Number(me.value))) {
		alert("起始里程不得大于终止里程！");
		kmS.focus();
		return false;
	}

	var roadDirect = $("ldfx");
	if (roadDirect.value == "") {
		alert("请输入道路方向！");
		roadDirect.focus();
		return false;
	}

	var gxdd = $("gxdd");
	if (gxdd.value == "") {
		alert("请输入管辖机构！");
		gxdd.focus();
		return false;
	}

	var roadStatus = $("lk");
	if (roadStatus.value == "") {
		alert("请输入路况！");
		roadStatus.focus();
		return false;
	}

	var lkyy = $("lkyy");
	if (lkyy.value == "") {
		alert("请输入拥堵原因！");
		lkyy.foucs();
		return false;
	}
	var pname = $("lrr");
	if (pname.value == "") {
		alert("请输入填报人姓名！");
		pname.focus();
		return false;
	}
	var remindInfo = $("bz");
	if (remindInfo.value == "") {
		alert("请输入备注情况！");
		remindInfo.focus();
		return false;
	}
	return true;
}

function getCrowdData() {
	var params;
	if (checkParam()) {
		var es = [ "dlmc", "ldmc", "ldfx", "qslc", "qslcm", "zzlc", "zzlcm",
				"gxdd", "qssj", "lk", "lkyy", "bz", "blr", "lxfs", "lrr",
				"lrbm", "lrsj" ];
		params = {};
		for ( var i = 0; i < es.length; i++) {
			params[es[i]] = $(es[i]).value;
		}
	}
	return params;
}
function submitTNFAdd() {
	var params = getCrowdData();
	if($('gxdd').value.indexOf("支队") != -1) {
		alert("管辖机构不能是支队！");
		$('gxdd').focus();
		return;
	}
	if (params) {
		var url = "dispatch.feedNews.addNewsInfo.d";
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showAddResponse
		});
	}
}

function showAddResponse(xmlHttp) {
	var text = xmlHttp.responseText;
	if (text == "true") {
		alert("添加拥堵报料信息成功！");
		window.location.reload();
	} else {
		alert("添加拥堵报料信息失败！");
	}
}

//Modified by Liuwx 2011-07-26
function printWord(daytime) {
	window.print();
}
//Modification finished

function setReadOnly(hdcount) {
	var roadLevel = $("roadLevel");//拥堵道路等级
	var roadName = $("alarmRoad_TrafficCrowd_td");//拥堵道路名称
	var ldmc = $("ldmc");//拥堵路段 
	var qslc = $("qslc");//起始
	var qslcm = $("qslcm");
	var zzlc = $("zzlc");//终止
	var zzlcm = $("zzlcm");
	var directions = document.getElementsByName("RADIOTYPE");//拥堵方向
	var gxdd = $("gxdd");//管辖机构
	var qssj = $("qssj");//起始时间
	var zzsj = $("zzsj");//终止时间
	var lk = $("lk");//路况
	var lkyy = $("lkyy");//拥堵原因简述
	var blr = $("blr");//报料人
	var lxfs = $("lxfs");//报料人联系方式
	var lrr = $("lrr");//填报人
	var lrsj = $("lrsj");//报料时间
	var bz = $("bz");//备注情况
	var scr = $("scr");//审核人
	var scsj = $("scsj");//审核时间
	var cy = $("cy");//采用和发布
	var wcy = $("wcy");//不采用（不发布）
	var scyj = $("scyj");//备注
	if($("jglx").value == "0"){
		if (hdcount == "1") {
			roadLevel.disabled = true;
			roadName.disabled = true;
			ldmc.disabled = true;
			for ( var i = 0; i < directions.length; i++) {
				directions[i].disabled = true;
			}
			qslc.disabled = true;
			qslcm.disabled = true;
			zzlc.disabled = true;
			zzlcm.disabled = true;
			gxdd.disabled = true;
			qssj.disabled = true;
			zzsj.disabled = true;
			lk.disabled = true;
			lkyy.disabled = true;
			blr.disabled = true;
			lxfs.disabled = true;
			lrr.disabled = true;
			lrsj.disabled = true;
			bz.disabled = true;
			scr.disabled = true;
			scsj.disabled = true;
			cy.disabled = true;
			wcy.disabled = true;
			scyj.disavled = true;
			
			$('searchDept').style.display = "none";
			$('deptList').style.display = "none";
			$('gxdd').style.width = "160px";
		}else if (hdcount == "2") {
//			roadLevel.disabled = false;
//			roadName.disabled = false;
//			ldmc.disabled = false;
//			for ( var i = 0; i < directions.length; i++) {
//				directions[i].disabled = false;
//			}
//			qslc.disabled = false;
//			qslcm.disabled = false;
//			zzlc.disabled = false;
//			zzlcm.disabled = false;
//			gxdd.disabled = false;
//			qssj.disabled = false;
//			zzsj.disabled = false;
//			lk.disabled = false;
//			lkyy.disabled = false;
//			blr.disabled = false;
//			lxfs.disabled = false;
//			lrr.disabled = false;
//			lrsj.disabled = false;
//			bz.disabled = false;
//			if(clzt == clzt_0){
				$("zdqdxf").style.display = "inline";
				$("ignore").style.display = "inline";
//			} else if (clzt == clzt_2){
//				$("ddqdhs").style.display = "inline";
//				$("ignore").style.display = "inline";
//			}
		}
	}
}
