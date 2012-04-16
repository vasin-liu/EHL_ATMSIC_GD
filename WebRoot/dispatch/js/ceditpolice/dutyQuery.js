//用户基本信息变量名称数组
//base info variable name
var bivnames = [ "jgid", "jgmc", "zbrxm" ];
//用户基本信息
var baseInfo
//页面元素信息ID数组
var piids = [ "dutyTime1", "dutyTime2", "dutyUnit", "dutyUCode" ];
//页面元素信息
var pageInfo;
//页面初始默认信息
var defaultInfo;
//数据库信息
var queryDataInfos;
//查询列名
var scnames;
//查询列名描述
var scndescs;

function getBaseInfo() {
	if (!baseInfo) {
		baseInfo = {};
		var vname, el;
		for ( var i = 0; i < bivnames.length; i++) {
			vname = bivnames[i];
			el = $(vname);
			baseInfo[vname] = (el ? el.value : "");
		}
	}
}

function getDefaultInfo() {
	if (!defaultInfo) {
		getBaseInfo();
		defaultInfo = {};
		var date = new Date();
		defaultInfo["dutyTime1"] = date.format_(3);
		defaultInfo["dutyTime2"] = date.format_(3);
		defaultInfo["dutyUnit"] = baseInfo.jgmc;
		defaultInfo["dutyUCode"] = baseInfo.jgid;
	}
}

function setPageInfo() {
	getDefaultInfo();
	var el;
	for ( var attr in defaultInfo) {
		el = $(attr);
		if (el && el.value != undefined) {
			el.value = defaultInfo[attr];
		}
	}
}

function query() {
	//搜索值班电话
	var sql = "select zbdh1 from t_sys_department where jgid=duty.deptid";
	sql = "(" + sql + ")";
	//搜索值班信息
	sql = "select distinct did,deptid,f_get_dept(deptid),leader,person," + sql
			+ ",phone,dtime from t_oa_duty duty where 1=1";
	scnames = [ "id", "jgid", "jgmc", "leader", "dutyer", "tel", "phone", "time" ];
	scndescs = [ "编号", "机构编号", "单位", "值班领导", "值班警员", "电话", "手机", "登记时间" ];
	function siftTime(value,cname,refer){
		if(value && cname && refer){
			var format = "yyyy-mm-dd";
			var time = " and to_date('" + value + "','"+format+"') ";
			time += refer;
			time += " to_date(substr("+cname+",1,"+format.length+"),'"+format+"')";
			return time;
		}
		return "";
	}
	var start = $("dutyTime1").value;
	sql += siftTime(start,"duty.dtime","<=");
	var end = $("dutyTime2").value;
	sql += siftTime(end,"duty.dtime",">=");
	
	var dutyUCode = window.G_jgID || $("dutyUCode").value;
	if (dutyUCode) {
		sql += " and " + Dept.siftChild(dutyUCode, "duty.deptid");
	}
	sql += " order by duty.deptid,duty.dtime";
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
}

function getQueryTd(text, width) {
	if (width) {
		width = "width='" + width + "%'";
	} else {
		width = "";
	}
	var td = "<td " + width + "  class='td_r_b td_font' align='center'>";
	td += text;
	td += "</td>";
	return td;
}

function getQueryTr(td, height) {
	return "<tr align='center' height='" + (height || 25) + "'>" + td + "</tr>";
}

function getQueryTitle() {
	var title = "<span class='currentLocationBold'>查询结果</span>";
	title = "<div style='text-align:center;width:100%;line-height:22px; float:left;'>"
			+ title + "</div>";
	return title;
}

function getQuerySubTitle(tindexs) {
	if (tindexs && scndescs) {
		var tds = getQueryTd("序号");
		for ( var i = 0; i < tindexs.length; i++) {
			tds += getQueryTd(scndescs[tindexs[i]]);
		}
		return getQueryTr(tds);
	}
	return null;
}

function getValueAttr(value, width) {
	var style = "width:" + width
			+ "px;overflow: hidden;white-space: nowrap;text-overflow:ellipsis";
	return "<span title=" + value + " style=" + style + ">" + value + "</span>";
}

function getDataInfo(xmldoc) {
	if (scnames && xmldoc) {
		queryDataInfos = [];
		var rows = xmldoc.getElementsByTagName("row");
		var cols;
		for ( var i = 0; i < rows.length; i++) {
			cols = rows[i].childNodes;
			var queryDataInfo = {};
			for ( var j = 0; j < scnames.length && j < cols.length; j++) {
				queryDataInfo[scnames[j]] = cols[j].text=="null"?"":cols[j].text;
			}
			queryDataInfos.push(queryDataInfo);
		}
	} else {
		if (!scnames) {
			alert("未获取到查询列名！");
		}
		if (!xmldoc) {
			alert("xmldoc无效！");
		}
	}
}

function getQueryNull(length) {
	var queryNull = "<td class='td_r_b td_font' colspan='" + (length || 0)
			+ "' align='center'>此条件下没有数据</td>";
	queryNull = "<tr class='titleTopBack'>" + queryNull + "</tr>";
	return queryNull;
}

function showResultsPage(xmldoc) {
	getDataInfo(xmldoc);
	var str = getQueryTitle();
	str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='5%' class='td_r_b td_font'>序号</td>";
	var tindexs = [ 2, 3, 4, 5, 6, 7 ];
	for ( var i = 0; i < tindexs.length; i++) {
		str += "<td class='td_r_b td_font'>" + scndescs[tindexs[i]] + "</td>";
	}
	str += "</tr>";
	
	if (!queryDataInfos || queryDataInfos.length <= 0) {
		str += getQueryNull(tindexs.length+1);
	} else {
		var queryDataInfo;
		var tds = "";
		for ( var i = 0; i < queryDataInfos.length; i++) {
			queryDataInfo = queryDataInfos[i];
			tds = getQueryTd(i+1);
			for ( var j = 0; j < tindexs.length; j++) {
				tds += getQueryTd(queryDataInfo[scnames[tindexs[j]]]);
			}
			str += getQueryTr(tds);
		}
	}
	str += "</table>";
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = str;
	Popup.prototype.hideTips();
}


window.onload = function() {
	setPageInfo();
	query();
}