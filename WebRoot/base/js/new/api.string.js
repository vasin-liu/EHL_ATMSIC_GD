/**
 * 首字母大写
 */
String.prototype.upperInitial = function() {
	return this.replace(/(^|\s+)\w/g, function(s) {
		return s.toUpperCase();
	});
};
/**
 * 日期格式字符串转换为日期类型
 */
String.prototype.dateParse = function(){
	return new Date(Date.parse(this.replace(/-/g,"/")));
};

/**
 * 去掉首尾空格（包括中文和英文） 
 */
String.prototype.trim = function(){
	if(/^([ | ]*)(.*)([ | ]*)$/.test(this)){
		return RegExp.$2;
	}
	return this;
};

/**
 * 是否以指定字符开始
 * @param pre 前缀
 * @returns boolean
 */
String.prototype.startWith = function(pre){
	return new RegExp("^("+pre+")(.*)").test(this);
};