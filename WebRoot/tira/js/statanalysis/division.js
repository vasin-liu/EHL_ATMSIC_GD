/*
 
 */
var cityTdId = "cityTdId";
var zoneTdId = "zoneTdId";
var citySelectId = "citySelectId";
var zoneSelectId = "zoneSelectId";
var xzqhInputId = "xzqhId";
var cityComplete = false;
var zoneComplete = false;
var select = "all";



function showCitySelect(username) {
	var citySql = "select code.dmz, code.dmsm1 "+
				"from (select * from t_tira_code where dmlb='300119' and substr(dmz,1,2)='44') code "+
				"inner join t_sys_department dept "+
				"on dept.jgid=code.dmz||'000000' "+ 
				"inner join t_sys_user useru "+
				"on useru.username='"+username+"' and dept.jgid!='440000000000' and "+
				"((useru.deptcode='440000000000' and substr(dept.jgid,5,2)='00') or "+
				"(useru.deptcode!='440000000000' and substr(useru.deptcode,1,4)||'00000000'=dept.jgid)) "+
				"order by code.dmz";
	fillListBox(cityTdId,citySelectId,"80",citySql,"","cityCallBack()","cityOnChange","");
}

/**
 *显示区或县的下拉列表框
 */
function showZoneSelect(username) {
	var zoneSql = "select code.dmz, code.dmsm1 "+
				"from (select * from t_tira_code where dmlb='300119' and substr(dmz,1,2)='44') code "+
				"inner join t_sys_department dept "+
				"on dept.jgid=code.dmz||'000000' "+ 
				"inner join t_sys_user useru "+
				"on useru.username='"+username+"' and dept.jgid!='440000000000' and substr(dept.jgid,5,2)!='00' and "+
				"((substr(useru.deptcode,5,2)!='00' and useru.deptcode=dept.jgid) or "+
				"(substr(useru.deptcode,5,2)='00' and useru.deptcode=substr(dept.jgid,1,4)||'00000000')) " +
				"order by code.dmz";
	fillListBox(zoneTdId,zoneSelectId,"120",zoneSql,"","zoneCallBack()","zoneOnChange","");
}

/**
 *市的下拉列表框显示完成后的回调函数
 */
function cityCallBack() {
	
	removeFirstItem(citySelectId);
	filterOption(citySelectId);
	
	var city = document.getElementById(citySelectId);
	if (!zoneComplete) {
		document.getElementById(xzqhInputId).value = document.getElementById(citySelectId).value;
	}
	/*if (select == "one") {
		if (document.getElementById(citySelectId).value == "") {
			document.getElementById(zoneSelectId).style.display = "none";
		}
	}*/
}

/**
 *区或县的下拉列表框显示完成后的回调函数
 */
function zoneCallBack() {
	
	if (document.getElementById(zoneSelectId).options.length == 0) {
		document.getElementById(zoneTdId).innerHTML = "";
		return;
	}
	removeFirstItem(zoneSelectId);
	filterOption(zoneSelectId);
	
	
	if (document.getElementById(zoneSelectId).value != "") {
		document.getElementById(xzqhInputId).value = document.getElementById(zoneSelectId).value;
		zoneComplete = true;
	}
	
	if (select == "one") {
		if (document.getElementById(zoneSelectId).value == "") {
			document.getElementById(zoneSelectId).style.display = "none";
		}else {
			document.getElementById(citySelectId).style.display = "none";
		}
	}
	
}

/**
 *市的下拉列表框的onchange事件对应的函数
 */
function cityOnChange() {
	if (this.value == "") {
		document.getElementById(zoneTdId).innerHTML = "";
		document.getElementById(xzqhInputId).value = "";
		return;
	}
	document.getElementById(xzqhInputId).value = this.value;
	showSubItems(this.value);
}

/**
 *区或县的下拉列表框的onchange事件对应的函数
 */
function zoneOnChange() {
	var xzqh = document.getElementById(xzqhInputId);
	if (this.value == "") {
		xzqh.value = document.getElementById(citySelectId).value;
	} else {
		xzqh.value = this.value;
	}

}

function showSubItems(value) {
 	var sql = "select dmz, dmsm1 from t_tira_code where dmlb='300119' and "+
 	 		  "substr(dmz,1,4)||'00'='"+value+"' and substr(dmz,5,2)!='00' order by dmz";
	fillListBox(zoneTdId,zoneSelectId,"120",sql,"","zoneCallBack()","zoneOnChange","");
}

function removeFirstItem(selectId) {
	var options = document.getElementById(selectId).childNodes;
	if(options.length == 2) {
		options[0].parentNode.removeChild(options[0]);
	}
}

function filterOption(selectId, movestr) {
	if (!movestr) {
		movestr = "（*）";//此处字符不是英文键盘的字符
	}
	
	var options = document.getElementById(selectId).childNodes;
	for (var i = 0; i < options.length; i++) {
		var option = options[i].innerText;
		if (option.substr(option.length-movestr.length,movestr.length)==movestr) {
			options[i].innerText = option.replace(movestr,"");
		}
	}
}


function findOptionText(value) {
	if (value == "") return "全省";
	return findOptionText2(value,value.substr(4,2)=="00"?citySelectId:zoneSelectId);
}

function findOptionText2(value, id) {
	var options = document.getElementById(id).options;
	for (var i = 0; i < options.length; i++) {
		if (options[i].value == value) {
			return options[i].text;
		}
	}
}

function changeXZQHId(id) {
	if (id == "") return;
	document.getElementById(xzqhInputId).id = id;
	xzqhInputId = id;
}

function changeSelect(sel) {
	select = sel;
}
























