
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
//				alert("不可输入特殊字符,包括:',;, ~、@、#、$、^、&");
//				item.focus();
//				return false;
//			}
//		}
//	}
//	for (var i = 0; i < textarea.length; i++) {
//		var item = textarea[i];
//		if (item.type != "button" && !item.readOnly) {
//			if(!reg.test(item.value)){
//				alert("不可输入特殊字符,包括:',;, ~、@、#、$、^、&");
//				item.focus();
//				return false;	
//			}
//		}
//	}
//	return true;
//}

//2011-08-17更新，雷适兴