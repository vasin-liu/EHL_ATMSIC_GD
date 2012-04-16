function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@#\$\^&]*$/;
	alert(2)
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	return true;
}
