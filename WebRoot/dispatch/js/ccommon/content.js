/**
 * 说明:支队处警情况统一管理 作者：Lzy 日期:2011-07-07
 */
var selectContent = new Array();

function getContent(TEXT, FSJG, FSRY, FSSJ) {
	var flow2_str = '<div>' + getContentPart(TEXT, FSJG, FSRY, FSSJ);
	+'</div>';
	return flow2_str;
}

function getContentPart(content,dname,pname,time,id){
	//显示录入的内容
	content = "<textarea cols='69' style='overflow:visible;border: 0;text-indent:24px;line-height:100%' readonly='readonly'>"+content +"</textarea>" + "\n";
	//单位名称、值班人名字、时间
	var str = "";
	if(dname){
		str += dname + " ";
	}
	str += pname + " " + time + " ";
	//接收单位
	str = "&nbsp;&nbsp;&nbsp;&nbsp;<textarea cols='69' style='overflow:visible;border: 0;text-indent:10px;line-height:200%' readonly='readonly'>["+str +"]</textarea>";
	return content + str;
}

// Modified by Liuwx
function getRadioButton(id, value, likeCB, name, group, groupName, select) {
	var rb = "";
	var rbId = "chkRB";
	var rbName = "chkRB";
	var isSelect = true;
	if (id != undefined && id != null && id != "") {
		rbId = id;
	}
	if (name != undefined && name != null && name != "") {
		rbName = name;
	}
	if (select) {
		isSelect = select;
	}
	if (isSelect) {
		selectContent.push(value);
	}
	if (likeCB && group) {
		rb = "<input type='radio' value='"
				+ value
				+ "' name='"
				+ groupName
				+ "' id='"
				+ rbId
				+ "' onMouseOver='chk=checked;' onclick='getSelectValue(this,this.value);' checked></input>";
	} else if (likeCB && !group) {
		rb = "<input type='radio' value='"
				+ value
				+ "' id='"
				+ rbId
				+ "' onMouseOver='chk=checked;' onclick='getSelectValue(this,this.value);' ></input>";
	} else if (!likeCB && group) {
		rb = "<input type='radio' value='"
				+ value
				+ "' name='"
				+ rbName
				+ "' id='"
				+ rbId
				+ "' onclick='getSelectValue(this,this.value);' checked></input>";
	} else {
		rb = "<input type='radio' value='"
				+ value
				+ "' name='"
				+ rbName
				+ "' id='"
				+ rbId
				+ " ' onclick='getSelectValue(this,this.value);' checked></input>";
	}
	return rb;
}

function getSelectValue(obj, value) {
	var chk = obj.checked;
	if (chk == true) {
		obj.checked = false;
		for ( var i = 0; i <= selectContent.length; i++) {
			if (selectContent[i] == value) {
				delete selectContent[i];
			}
		}
	} else {
		obj.checked = true;
		for ( var i = 0; i <= selectContent.length; i++) {
			if (selectContent[i] == value) {
				delete selectContent[i];
			}
		}
		selectContent.push(value);
	}
	alert(selectContent.length)
}

function getRadio(value, checked) {
	if (checked) {
		selectContent.push(value);
		checked = "checked";
	} else {
		checked = "";
	}
	return "<input type='radio' value='" + value
			+ "' style='float:left;' onclick='checkOrNot(this)' " + checked
			+ " />";
}

function checkOrNot(el) {
	if (el) {
		el.checked = (el.defaultChecked = !el.defaultChecked);
		var values = selectContent;
		if (values instanceof Array) {
			if (el.checked) {
				values.push(el.value);
			} else {
				for ( var i = 0; i < values.length; i++) {
					if (values[i] == el.value) {
						values.splice(i, 1);
						break;
					}
				}
			}
		}
	}
}

// Modification finished
function Radio(name, values, checked) {
	this.name = name;
	this.values = values;
	this.checked = checked || false;
}

Radio.prototype = {
	getRadio : function(value, checked) {
		var this_ = this;
		var radio = document.createElement("input");
		radio.type = "radio";
		radio.name = this_.name;
		radio.value = value || "";
		radio.checked = checked || this_.checked;
		with (radio.style) {
			styleFloat = "left";
		}
		radio.onclick = function() {
			this_.checkOrNot(radio);
		}
		return radio;
	},
	getRadioHTML : function(value, checked) {
		var radio = this.getRadio(value, checked);
		return radio.outerHTML;
	},
	checkOrNot : function(el) {
		el.checked = (el.defaultChecked = !el.defaultChecked);
		var values = this.values;
		if (values instanceof Array) {
			if (el.checked) {
				values.push(el.value);
			} else {
				for ( var i = 0; i < values.length; i++) {
					if (values[i] == el.value) {
						values.splice(i, 1);
						break;
					}
				}
			}
		}
	}
}

// 获取必填标志
function getRequiredMark() {
	return "<span style='color:red;margin-left:2px;'>※</span>";
}

// 追加标志，为一个元素
function appendMark(id, mark) {
	if (id) {
		var el = $(id) || id;
		el.innerHTML = el.innerHTML + mark || "";
	}
}
// 追加标志，为多个元素
// 默认标志为必填标志
function appendMarks(ids, mark) {
	if (ids instanceof Array) {
		var requiredMark = getRequiredMark();
		if (mark instanceof Array && mark.length >= 1) {
			for ( var i = 0; i < ids.length; i++) {
				appendMark(ids[i], mark[i] || requiredMark);
			}
		} else {
			mark = mark || requiredMark;
			for ( var i = 0; i < ids.length; i++) {
				appendMark(ids[i], mark);
			}
		}
	}
}
//2011-08-16更新，雷适兴