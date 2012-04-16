
//初始化页面元素
function initPage() {

}

//初始化默认值
function initValue() {

}

//取得页面元素k->v
function getParams(params) {
	var paramj = {};
	for ( var i = 0; i < params.length; i++) {
		if ($(params[i]) && $(params[i]).value != undefined) {
			paramj[params[i]] = $(params[i]).value;
		} else {
			return null;
		}
	}
	return paramj;
}

//提交请求前的验证
function submitCheck(params) {
	return true;
}

//字符过滤
function filterChar(params) {

}

//提交请求
function submit() {
	var params = [ "jgid", "dateS", "dateE" ];
	var paramj = getParams(params);
	if (paramj == null) {
		alert("页面参数项缺失！");
		return;
	}
	var isSuccess = submitCheck(paramj);
	if (isSuccess == true) {
		filterChar(paramj);
		var types = [ 1, 2, 3, 4, 5];
		var typeDescs = [ "重特大交通事故", "交通拥堵", "施工占道", "拥堵报料","信息文件"];
		var selectType = "";
		for ( var i = 0; i < types.length; i++) {
			selectType += ",'" + types[i] + "','" + typeDescs[i] + "'";
		}
		selectType = "decode(type," + selectType.substring(1) + "),";
		var select = "deptid,";
		select += "replace(deptdesc,'市公安局交通警察','交警'),";
		select += "to_char(dayid,'yyyy-mm-dd hh24:mi:ss'),";
		select += selectType;
		select += "to_char(jfrq,'yyyy-mm-dd hh24:mi'),";
		select += "reason";//"decode(sign(length(reason)-30),1,substr(reason,1,30)||'...',substr(reason,1,30))";
		var from = "T_OA_SCORERECORD";
		var where = "1=1";
		if (paramj.jgid != "440000000000") {
			where += " and substr(deptid,1,4)='" + paramj.jgid.substring(0, 4)
					+ "'";
		}
		where += " and " + "dayid+1 >= to_date('" + paramj.dateS
				+ "','yyyy-mm-dd')";
		where += " and " + "dayid-1 <= to_date('" + paramj.dateE
				+ "','yyyy-mm-dd')";
		var orderby = "deptid,dayid";
		var sql = "select " + select + " from " + from + " where " + where
				+ " order by " + orderby;
		PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
				"submitResponse", "10");

	}
}

//全局存储查询信息
var rows_g;
var jgid;
//提交请求后的反馈结果
function submitResponse(xmlHttp) {
	jgid = jgid || $("jgid").value;
	var rows = xmlHttp.getElementsByTagName("row");
	var str = "<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>";
	str += "<div id='block'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='5%' class='td_r_b td_font'>序号</td>";
	//	str += "<td width='10%' class='td_r_b td_font'>机构编号</td>";
	str += "<td width='15%' class='td_r_b td_font'>机构名称</td>";
	str += "<td width='15%' class='td_r_b td_font'>计分项</td>";
	str += "<td width='18%' class='td_r_b td_font'>信息发生时间</td>";
	//	str += "<td width='25%' class='td_r_b td_font'>计分原因</td>";
	str += "<td width='18%' class='td_r_b td_font'>录入时间</td>";
	str += "<td width='5%' class='td_r_b td_font'>明细</td>";
	if (jgid.substring(2, 4) == "00") {
		str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	}
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack' height='25px' >";
		str += "<td class='td_r_b td_font' colspan='7' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		rows_g = rows;
		//机构编号，机构描述，录入时间，事件类型，事件时间，计分原因
		var showids = [ 1, 3, 4, 2 ];
		var cells;
		for ( var i = 0; i < rows.length; i++) {
			cells = rows[i].childNodes;
			str += "<tr align='center' height='25px'>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
					+ (i + 1) + "</td>";
			for ( var j = 0; j < showids.length; j++) {
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
						+ cells[showids[j]].text + "</td>";
			}
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
					+ "<input type=\"image\" src=\"../../image/button/btndetail1.gif\" "
					+ "onClick=\"detail('" + i + "')\" " + ">" + "</td>"
			if (jgid.substring(2, 4) == "00") {
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
						+ "<input type=\"image\" src=\"../../image/button/btndelete1.gif\" "
						+ "onClick=\"deleteu('"
						+ cells[0].text
						+ "','"
						+ cells[2].text + "')\" " + ">" + "</td>"
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

function detail(index) {
	var cells = rows_g[index].childNodes;
	var cols = [ "jgid", "jgmc", "dayid", "type", "jfrq", "reason" ];
	var param = "";
	for ( var i = 0; i < cols.length; i++) {
		param += "&" + cols[i] + "=" + cells[i].text;
	}
	param = param.substring(1);
	param += "&tmp=" + Math.random();
	var url = "ScoreRecordDetail.jsp" + "?" + param;
	url = encodeURI(url);
	window.showModalDialog(url, "", "dialogWidth:700px;dialogHeight:400px");
}

function deleteu(jgid, date) {
	var isSure = confirm("确认要删除计分信息吗？");
	if (isSure == true) {
		var url = "policeWorks.assess.scoreRecordDelete.d";
		var params = {
			jgid : jgid,
			date : date
		};
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : deleteResponse
		});
	}
}

function deleteResponse(xmlHttp) {
	var isSuccess = xmlHttp.responseText;
	if (isSuccess = "true") {
		alert("删除计分信息成功！");
		window.location.reload();
	} else if (isSuccess == "false") {
		alert("删除计分信息失败！");
	} else {
		alert("删除计分信息失败！");
	}
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