Date.prototype.getTimes_ = function() {
	var encap = function(date) {
		date = "0" + date;
		return date.substr(date.length - 2);
	};
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
};
// 时间格式化类型，1年、2月、3日、4时、5分、6秒，默认为3
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
};
// 1年2月3日4小时5分6秒
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
};

Date.day = {
	min : "00:00:00",
	max : "23:59:59"
};
Date.oracle = {
	format : {
		date : "yyyy-mm-dd",
		time : "hh24:mi:ss",
		common : "yyyy-mm-dd hh24:mi:ss"
	},
	formatting : function(time) {
		if (time) {
			var format = Date.oracle.format.common;
			return "to_date('" + time + "','" + format + "')";
		}
		return null;
	},
	formattingStart : function(time) {
		if (time) {
			time += " " + Date.day.min;
			return Date.oracle.formatting(time);
		}
		return null;
	},
	formattingEnd : function(time) {
		if (time) {
			time += " " + Date.day.max;
			return Date.oracle.formatting(time);
		}
		return null;
	}
};

Array.prototype.getIndex = function(object) {
	var length = this.length;
	for ( var i = 0; i < length; i++) {
		if (this[i] == object) {
			return i;
		}
	}
	return -1;
};
