/**
    * All right reserved.
    * 说明：table组件清空
    * 作者：wangxt
    * 日期: 2009-05-20
    */
function reset(tabId) {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById(tabId);
	
	if (queryTable != null) {
		input = queryTable.getElementsByTagName("input");
		select = queryTable.getElementsByTagName("select");
		textarea = queryTable.getElementsByTagName("textarea");
		checkbox = queryTable.getElementsByTagName("checkbox");
	} else {
		input = document.getElementsByTagName("input");
		select = document.getElementsByTagName("select");
		textarea = document.getElementsByTagName("textarea");
		checkbox = document.getElementsByTagName("checkbox");
	}
	for (var i = 0; i < input.length; i++) {
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
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].type != "button" && !checkbox[i].readOnly) {
			checkbox[i].checked = false;
		}
	}
}