/*
1.本js引用prototype.js。
2.如果是省显示市的列表框，如果是市显示区县的列表框，如果是区县，到了最末位，则显示自己。
通过onchange事件来显示其子项，如果没有子项，则不显示或者隐藏显示。
把行政区划代码值放入隐藏域内保存，由父项和子项（如果存在）共同确定。
用户名不满足，处理情况
机构权限不满足，处理情况
*/

//由于异步请求后的回调函数中的this对象改变，所以在此建立该 对象，需要设置dmlb。
var department = new Department();
/**
 * 行政区划
 *  
 */
function Department(dmlb,localProvince,loginPageUrl,departmentList,citySelWidth,zoneSelWidth) {
	
	this.cityTdId = "cityTdId";
	this.zoneTdId = "zoneTdId";
	this.citySelId = "citySelId";
	this.zoneSelId = "zoneSelId";
	this.departmentId = "departmentId";
	this.citySelWidth = citySelWidth==undefined?"120":citySelWidth;
	this.zoneSelWidth = zoneSelWidth==undefined?"150":citySelWidth;
	
	this.isEndNode = false;
	this.cityCode = "";
	this.cityDesc = "";
	this.deptLevel = "";
	
	this.dmlb = dmlb;
	this.departmentList = departmentList instanceof Array ? departmentList : ["00","00","00","00"];
	this.localProvince = localProvince==undefined?"440000":localProvince;
	
	this.loginPageUrl = loginPageUrl==undefined?"http://localhost:8080/EHL_TIRA_1.4/tira/login.jsp":loginPageUrl;
	this.nullResponse = "null";
	
};

Department.prototype.createTable = function(id) {
	var table = "<table>\
			    	<tr>\
			    		<td class=\"td_2\" align=\"right\" width=\"0\">\
			    			<input id=\""+this.departmentId+"\" type=\"hidden\" value=\"\" />\
			    		</td>\
			    		<td id=\""+this.cityTdId+"\" align=\"right\"  class=\"td_2\" width=\""+this.citySelWidth+"\">\
			    		</td>\
			    		<td id=\""+this.zoneTdId+"\" align=\"left\"  class=\"td_2\" width=\""+this.zoneSelWidth+"\">\
			    		</td>\
			    	</tr>\
			     </table>";
	$(id).innerHTML = table;	
}

/**
 * 通过用户名，得到机构表中的机构id
 * @param {String} uname 用户名
 * @return {String} jgid 机构id
 */
Department.prototype.getJgidByUname = function(uname) {
	if(!Department.prototype.checkParam(uname)) {
		return null;
	}
	var url = "sa.DivisionAction.getJgidByUname.d";
	var params = "uname="+uname;
	new Ajax.Request(url, 
		{
				method: "post", 
				asyn: false,
				parameters: params, 
				onComplete: Department.prototype.gjbuCallback
		});
};

/**
 * 通过用户名得到机构id的回调函数
 * @param {Object} xmlHttp
 * @return {TypeName} 
 */
Department.prototype.gjbuCallback = function(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (!Department.prototype.checkParam(xmlDoc) || xmlDoc == department.nullResponse) {
		window.location.href = department.loginPageUrl;//用户权限不足，返回登录页面
		return;
	}
	var sql = 
		"select \
			substr(jgid,1,6),\
			(select replace(jgmc,'公安局交通警察支队','交警支队') from t_sys_department where jgid=substr(dept.jgid,1,6)||'000000')\
		from \
			t_sys_department dept";
	
	var whereSql = " where substr(jgid,1,2)='44' and substr(jgid,1,6) < '446000' and ";
	var province = xmlDoc.substr(0,2);
	var city = xmlDoc.substr(2,2);
	var zone = xmlDoc.substr(4,2);
	if (zone != '00') {//大队
		whereSql += "substr(jgid,1,6)='"+xmlDoc+"'";
		sql += whereSql;
		fillListBox(department.zoneTdId,department.zoneSelId,department.zoneSelWidth,sql,"全省","department.zoneCallBack()","department.zoneOnChange","");
	}else if(city != '00'){//支队
		whereSql += "substr(jgid,3,2)=substr('"+xmlDoc+"',3,2)";
		sql += whereSql;
		fillListBox(department.zoneTdId,department.zoneSelId,department.zoneSelWidth,sql,"全省","department.zoneCallBack()","department.zoneOnChange","");
	}else {//总队
		whereSql += "substr(jgid,5,2)='00'";
		sql += whereSql;
		fillListBox(department.cityTdId,department.citySelId,department.citySelWidth,sql,"全省","department.cityCallBack()","department.cityOnChange","");
	}
	
	var sql = department.createDepartmentSqlByJgid(xmlDoc);
	sql = Department.prototype.filterSql("dmsm1",sql);
	
}

/**
 * 通过机构id，创建获取行政区划列表的sql语句
 * @param {String} jgid 机构id
 * @return {String} 
 */
Department.prototype.createDepartmentSqlByJgid = function(jgid) {
	var sql = 
		"select \
			substr(jgid,1,6),\
			(select replace(jgmc,'公安局交通警察支队','交警支队') from t_sys_department where jgid=substr(dept.jgid,1,6)||'000000')\
		from \
			t_sys_department dept";
	
	var whereSql = " where substr(jgid,1,2)='44' and substr(jgid,1,6) < '446000' and ";
	var province = jgid.substr(0,2);
	var city = jgid.substr(2,2);
	var zone = jgid.substr(4,2);
	if (zone != '00') {//大队
		this.deptLevel = "大队";
		whereSql += "substr(jgid,1,6)='"+jgid+"'";
	}else if(city != '00'){//支队
		this.deptLevel = "支队";
		whereSql += "substr(jgid,3,2)=substr('"+jgid+"',3,2)";
	}else {//总队
		this.deptLevel = "总队";
		whereSql += "substr(jgid,5,2)='00'";
	}
	sql += whereSql;
	return sql;
};



Department.prototype.cityCallBack = function() {
	Department.prototype.removeFirstItem(department.citySelId);
	$(department.departmentId).value = $(department.citySelId).value;
};

Department.prototype.zoneCallBack = function() {
	Department.prototype.removeFirstItem(department.zoneSelId);
	$(department.departmentId).value = $(department.zoneSelId).value;
};

Department.prototype.cityOnChange = function() {
	if (this.value == department.localProvince) {
		$(department.zoneTdId).innerHTML = "";
	}else {
		department.createSubItems(this.value);
	}
	department.cityCode = this.value;
	department.cityDesc = this.options[this.selectedIndex].innerText;
	$(department.departmentId).value = this.value;
	
};

Department.prototype.zoneOnChange = function() {

	$(department.departmentId).value = this.value;
	
};

Department.prototype.createSubItems = function(value) {
	var sql = 
	"select \
		substr(jgid,1,6),\
		decode(instr(jgmc,'支队'),0,jgmc,'支队') \
	from \
		t_sys_department \
	where \
		substr(jgid,1,4)=substr('"+value+"',1,4) and \
		substr(jgid,1,6) < '446000' \
	order by substr(jgid,1,6) \
		";
	fillListBox(department.zoneTdId,department.zoneSelId,department.zoneSelWidth,sql,"全市","department.zoneCallBack()","department.zoneOnChange","");
};

Department.prototype.removeFirstItem = function(selectId) {
	var options = $(selectId).childNodes;
	options[0].parentNode.removeChild(options[0]);
};
/**
 * 移除市辖区选项<br>
 * MD:municipal district
 * @param {Object} selectId
 */
Department.prototype.removeMDItem = function(selectId) {
	var select = $(selectId);
	var options = select.childNodes;
	for (var i = 0; i < options.length; i++) {
		if(options[i].innerText == "市辖区") {
			select.removeChild(options[i]);
		}
	}
	
};

Department.prototype.filterSql = function(col, sql) {
	if (!Department.prototype.checkParam(col) || !Department.prototype.checkParam(sql)) {
		return sql;
	}
	return sql.replace(col,"rtrim("+col+",'（*）')");
};

Department.prototype.filterOption = function(selectId, movestr) {
	if (!movestr) {
		movestr = "（*）";//此处字符不是英文键盘的字符
	}
	
	var options = $(selectId).childNodes;
	for (var i = 0; i < options.length; i++) {
		var option = options[i].innerText;
		if (option.substr(option.length-movestr.length,movestr.length)==movestr) {
			options[i].innerText = option.replace(movestr,"");
		}
	}
};

Department.prototype.findOptionText = function(value) {
	if (value == "") return "全省";
	var id = value.substr(4,2)=="00"?this.citySelectId:this.zoneSelectId;
	var options = $(id).options;
	for (var i = 0; i < options.length; i++) {
		if (options[i].value == value) {
			return options[i].text;
		}
	}
};

/**
 * 验证参数
 * @param {Object} param
 * @return {boolean} 通过：true；通不过：false 
 */
Department.prototype.checkParam = function(param) {
	if(param == undefined || param == null || param.trim().length == 0) {
		return false;
	}
	return true;
};











