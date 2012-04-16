

var dept = new Department();

/**
 * 机关
 * @memberOf {TypeName} 
 */
function Department() {
	//页面元素ID
	this.zdTdId = "zdTdId";
	this.ddTdId = "ddTdId";
	this.zdSelId = "zdSelId";
	this.ddSelId = "ddSelId";
	//
	this.zodCode = null;
	this.zdCode = null;
	this.ddCode = null;
	//
	this.isContainZod = false;
	this.isContainZd = false;
	//
	this.zdCB = null;
	this.ddCB = null;
	//
	this.defZdCode = null;
	this.defDdCode = null;
}

/**
 * 创建支队或者支队和大队机关列表
 * @param {Object} zdPId 包含支队列表元素id
 * @param {Object} isContainZod 列表中是否包含总队
 * @param {Object} ddPId 包含大队列表元素id
 * @param {Object} isContainZd 列表是否包含支队
 * @memberOf {TypeName} 
 */
Department.prototype.createZdSel = function(zdPId,isContainZod,ddPId,isContainZd) {
	if (baseCheck([zdPId],true)){
		this.zdTdId = zdPId;
	}else {
		if (!baseCheck([this.zdTdId],true)) {
			return;
		}
	}
	this.ddTdId = ddPId;
	this.isContainZod = isContainZod==true?true:false;
	this.isContainZd = isContainZd==true?true:false;
	
		
	var whereZod = isContainZod?"":" and substr(jgid,3,2)!='00'";

	var sql = 
		"select \
			substr(jgid,1,6),\
			replace(jgmc,'公安局交通警察支队','交警支队')\
		 from \
			t_sys_department dept \
		 where substr(jgid,1,2)='44' and substr(jgid,1,6) < '446000' and substr(jgid,7,6)='000000' and \
		 	   substr(jgid,5,2)='00' "+whereZod+" \
		 order by substr(jgid,1,6)";
	
	fillListBox(this.zdTdId,this.zdSelId,"150",sql,"总队","dept.zdCallBack()","dept.zdOnChange","");
    
}

/**
 * 支队回调函数
 * @param {Object} ddPId 包含大队列表元素id
 * @param {Object} isContainZd 大队列表是否包含支队
 * @memberOf {TypeName} 
 */
Department.prototype.zdCallBack = function() {
	Department.prototype.removeItem(this.zdSelId, "", true);//移除默认添加的一项
	dept.setSelectOption(dept.zdSelId,dept.defZdCode);
	dept.zdCode = $(dept.zdSelId).value;
	if (dept.zdCode.substr(2)=='0000') {
		dept.zodCode = dept.zdCode;
		dept.zdCode = null;
		if (baseCheck([this.zdCB],false)) {//执行回调函数
			eval(this.zdCB);
		}
	}else {
		if (baseCheck([dept.ddTdId],true)) {//控制显示大队
			dept.createDdSel(dept.ddTdId,dept.isContainZd,dept.zdCode);
		}else {
			if (baseCheck([this.zdCB],false)) {//执行回调函数
				eval(this.zdCB);
			}
		}
	}
	
}

/**
 * 支队onChange事件
 * @memberOf {TypeName} 
 */
Department.prototype.zdOnChange = function() {
	dept.zdCode = this.value;
	if (dept.zdCode.substr(2)=='0000') {
		dept.zodCode = dept.zdCode;
		dept.zdCode = null;
		if (baseCheck([this.ddTdId],true)) {
			$(dept.ddTdId).innerHTML = "";
		}
	} else {
		dept.createDdSel(dept.ddTdId,dept.isContainZd,dept.zdCode);
	}
}


Department.prototype.createDdSel = function(pId,isContainZd,zdCode) {
	if (baseCheck([pId],true)){
		this.ddTdId = pId;
	}else {
		if (!baseCheck([this.ddTdId],true)) {
			return;
		}
	}
	
	var whereZd = " AND substr(jgid,1,4)||'00'='"+zdCode+"'";
	if (zdCode == undefined) {
		whereZd = " and substr(jgid,3,2)!='00' ";
	}
	whereZd += (isContainZd==undefined?"":"and substr(jgid,5,2)!='00'");
	
	var sql = 
		"select \
			substr(jgid,1,6), \
			replace(jgmc,'公安局交通警察支队','交警支队')\
		 from \
			t_sys_department dept \
		 where substr(jgid,1,2)='44' and substr(jgid,1,6) < '446000'  and substr(jgid,7,6)='000000' and \
		 	   substr(jgid,3,2)!='00'  "+whereZd+"  \
		 order by substr(jgid,1,6)";
	
	fillListBox(this.ddTdId,this.ddSelId,"150",sql,"支队","dept.ddCallBack()","dept.ddOnChange","");
}

Department.prototype.ddCallBack = function() {
	Department.prototype.removeItem(dept.ddSelId, "", true);
	dept.setCode(this.value);
}

Department.prototype.ddOnChange = function() {
	Department.prototype.removeItem(dept.ddSelId, "", true);
	dept.setCode(this.value);
	if (baseCheck([this.ddCB],false)) {//执行回调函数
		eval(this.ddCB);
	}
}

Department.prototype.setSelectOption = function(selId,deptCode)  {
	if (!baseCheck([selId],true) || !baseCheck([deptCode],false)) {
		return;
	}
	var option = Department.prototype.getSelectOption(selId, deptCode, true);
	option.selected = true;
	dept.setCode(deptCode);
}

Department.prototype.setCode = function(deptId) {
	if (baseCheck([deptId],false)) {
		return;
	}
	if (deptId.substr(4) != "00") {
		dept.ddCode = deptId;
		dept.zdCode = null;
		dept.zodCode = null;
	}else if (deptId.substr(2,4) != "00") {
		dept.zdCode = deptId;
		dept.ddCode = null;
		dept.zodCode = null;
	}else {
		dept.zodCode = deptId;
		dept.ddCode = null;
		dept.zdCode = null;
	}
}

Department.prototype.getCode = function() {
	if (dept.ddCode == null) {
		if (dept.zdCode == null) {
			if (dept.zodCode == null) {
				return null;
			}else {
				return dept.zodCode;
			}
		}else {
			return dept.zdCode;
		}
	}else {
		return dept.ddCode;
	}
}

Department.prototype.removeItem = function(selId, value, isValue) {
	if (!baseCheck([selId],true)) {
		return;
	}
	var sel = $(selId);
	var option = Department.prototype.getSelectOption(selId,value,isValue);
	sel.remove(option);
	
}

Department.prototype.getSelectOption = function(selId, value, isValue) {
	if (!baseCheck([selId],true)) {
		return null;
	}
	var sel = $(selId);
	var options = sel.options;
	if (isValue) {
		for (var i = 0; i < options.length; i++) {
			var option = options[i].value;
			if (option == value) {
				return options[i];
			}
		}
	}else {
		for (var i = 0; i < options.length; i++) {
			var option = options[i].innerText;
			if (option == value) {
				return options[i];
			}
		}
	}	
	return "";
}

