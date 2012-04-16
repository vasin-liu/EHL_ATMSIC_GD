
/*  功能：系统公用脚本, 版本 1.0
 *  作者：linbh 
 *  日期：2008-3-27
/*--------------------------------------------------------------------------*/
/**
 * 函数功能：重置机构选择
 * 返回值：
 */
function reset_dept(SSJG){
	var deptcode = document.getElementById(SSJG);
	if(deptcode.value != ""){
		deptcode.value = ""
	}
	G_jgID="";
}
/**
 * 函数功能：有效高度调整函数.
 * 参数说明：ctrlNames 控件名称组,多个控件之间以逗号分隔.
 * 返回值：
 */
function adjustHeight(ctrlNames) {
	
	if (ctrlNames == null || ctrlNames == "") return;	
	var ctrlArray = ctrlNames.split(",");
	if (screen.availHeight > 733){
		for (i = 0; i < ctrlArray.length; i++){
			var obj = document.getElementById(ctrlArray[i]);
			var iHeight = 0;
			switch (obj.tagName){
   			  case "TD": 
   			    iHeight = obj.height;
   			    break;
   			  case "DIV": 
   			    iHeight = obj.style.height;
   			    iHeight = iHeight.substr(0,iHeight.length - 2);
   			    break;
    		  default: 
    		    iHeight = obj.height;
    		    break;
			}
			obj.style.height =screen.availHeight -230 ;
			
			
		}
	}
}

function ladjustHeight(ctrlNames) {
	
	if (ctrlNames == null || ctrlNames == "") return;	
	var ctrlArray = ctrlNames.split(",");
	if (screen.availHeight > 733){
		
		for (i = 0; i < ctrlArray.length; i++){
			var obj = document.getElementById(ctrlArray[i]);
			var iHeight = 0;
			switch (obj.tagName){
   			  case "TD": 
   			    iHeight = obj.height;
   			    break;
   			  case "DIV": 
   			    iHeight = obj.style.height;
   			    iHeight = iHeight.substr(0,iHeight.length - 2);
   			    break;
    		  default: 
    		    iHeight = obj.height;
    		    break;
			}
			obj.style.height =screen.availHeight -254 ;
			
			
		}
	}
}

/**
 * 函数功能：有效宽度调整函数.
 * 参数说明：ctrlNames 控件名称组,多个控件之间以逗号分隔.
 * 返回值：
 */
function adjustWidth(ctrlNames) {
	if (ctrlNames == null || ctrlNames == "") return;
	var ctrlArray = ctrlNames.split(",");
	if (screen.availHeight > 734){
		for (i = 0; i < ctrlArray.length; i++){
			var obj = document.getElementById(ctrlArray[i]);
			var iWidth = 0;
			switch (obj.tagName){
   			  case "TD": 
   			    iWidth = obj.width;
   			    break;
   			  case "DIV": 
   			    iWidth = obj.style.width;
   			    iWidth = iWidth.substr(0,iWidth.length - 2);
   			    break;
    		  default: 
    		    iWidth = obj.width;
    		    break;
			}
			obj.style.width = screen.availWidth -223 ;
		}
	}
}

/**
 * 函数功能：SQL语句置换函数.
 * 参数说明：sql-标准SQL语句.
 * 返回值：  处理后的SQL语句.
 */
function convertSql(sql) {
	if (sql == null) {
		return sql;
	}
	sql = sql.replace(/=/g, "@");
	sql = sql.replace(/%/g, "~");
	return sql;
}
/**
 * 函数功能：建立一个由指定字符填充的字符串.
 * 参数说明：fillStr-填充字符;len-生成字符串长度.        
 * 返回值：   
 */
function fill(fillStr, len) {
	var retval = "";
	for (i = 0; i < len; i++) {
		retval = retval + fillStr;
	}
	return retval;
}
/**
 * 函数功能：根据正则表达式验证，提示用户只能输入符合格式的内容
 * 参数说明：inputValue 输入框内容      
 * 返回值：   
 */
 //校验手机号码：必须以数字开头，除数字外，可含有“-”  by-->libh 2008-5-25
function isMobil(s) {
	var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	if (!patrn.exec(s)) {
		return false;
	}
	return true;
}
/**
 * 验证普通字串，只要字串中不包含特殊字符即可
 .by--> ldq 2008-5-16
*/
function checkTextDataForNORMAL(strValue) {
	// 特殊字符验证格式
	var regTextChar = /([\#\&\?\*\<>])+/;
	return !regTextChar.test(strValue);
}
/*验证只能输入数字
.by--> gll 2008-5-16
*/
function checkMath(inputValue) {
	if (inputValue.replace(/[\d+]/ig, "").length > 0) {
		return false;
	}
	return true;
}
/*验证固定电话：只能是数字.并且有相应的格式//028-67519441 或者 0839-8777222或者 0228-65451244
.by--> 郭亮亮 2008-5-16 */
function checkImmobilityPhone(inputValue) {
	if (inputValue == "") {
		return true;
	} else {
		var reg = /^([0-9]|[\-])+$/g;
		if (inputValue.length < 7 || inputValue.length > 13) {
			return false;
		} else {
			return reg.exec(inputValue);
		}
	}
}
/*验证身份证
.by--> 郭亮亮 2008-5-16
*/
function isIdCardNo(num) {
	if (num == "") {
		return true;
	}
	if (isNaN(num)) {
		alert("\u8eab\u4efd\u8bc1\u53f7\u7801\u8f93\u5165\u7684\u4e0d\u662f\u6570\u5b57\uff01 ");
		return false;
	}
	var len = num.length, re;
	if (len == 15) {
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	} else {
		if (len == 18) {
			re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
		} else {
			alert("\u8eab\u4efd\u8bc1\u53f7\u7801\u8f93\u5165\u7684\u6570\u5b57\u4f4d\u6570\u4e0d\u5bf9\uff01 ");
			return false;
		}
	}
	var a = num.match(re);
	if (a != null) {
		if (len == 15) {
			var D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
		} else {
			var D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
		}
		if (!B) {
			alert(" \u8f93\u5165\u7684\u8eab\u4efd\u8bc1\u53f7 " + a[0] + " \u91cc\u51fa\u751f\u65e5\u671f\u4e0d\u5bf9\uff01 ");
			return false;
		}
	}
	return true;
}
/**
  * 验证中文
  .by--> jc 2008-5-16
*/
function checkChineseName(inputValue) {
	if (inputValue != "") {
		var nameReg = /^[\u0391-\uFFE5]+$/;
		if (!nameReg.exec(inputValue)) {
			return false;
		} else {
			return true;
		}
	} else {
		return true;
	}
}
//验证数据是否只含有数字,字母,汉字,下划线,逗号有则返回false by linbh -->2008-5-16
function checkNormalStr(inputVal) {
	var pattern = /^(?:[\u4e00-\u9fa5-[\/\\:.-\]]*\w*\s*)+$/;
	if (pattern.exec(inputVal)) {
		return true;
	}
	return false;
}



//显示进程提示信息
function showMsg(){
	var arrBody = document.getElementsByTagName("body");
	Popup.prototype.showTips(arrBody[0]); 
}

//获取字符串的字节数
function lengthB(str){
	return str.replace(/[^\x00-\xff]/g,"**").length;
}   

//为字符串添加Trim方法，去除首尾空格
String.prototype.Trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}