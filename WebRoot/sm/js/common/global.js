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
 *         selfParam 自定义调整高度值 默认为“240”
 * 返回值：
 */
function adjustHeight(ctrlNames,selfParam) {
	
	if (ctrlNames == null || ctrlNames == "") return;
	if(typeof selfParam == "undefined"){
	   selfParam = 240;
	}	
	var ctrlArray = ctrlNames.split(",");
	for (i = 0; i < ctrlArray.length; i++){
		var obj = document.getElementById(ctrlArray[i]);
		obj.style.height =screen.availHeight -selfParam ; //原有适合单独系统高度设定
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
	sql = sql.replace(/=/g, "＝");
	sql = sql.replace(/%/g, "％");
	sql = sql.replace(/\+/g, "＋");
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
	var regTextChar = /([\#^\&\?%\*\<>])+/;
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
  * 验证字符串是否全是中文 是返回TRUE
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
//是否包含中文--zhaoy 2009-08-04
function isChinese(password){
var pattern=/[^\x00-\xff]/g;
if(pattern.test(password)){
   //包含中文
   return true;
}else{
   //不包含中文
   return false;
}
} 


//显示进程提示信息
function showMsg(msg){
	var arrBody = document.getElementsByTagName("body");
	Popup.prototype.showTips(arrBody[0],msg); 
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
//验证ＩＰ地址
function isip(ip){
	var check=function(v){try{return (v<=255 && v>=0)}catch(x){return false}};
	var re = ip.split(".")
	return (re.length==4)?(check(re[0]) && check(re[1]) && check(re[2]) && check(re[3])):false
}

/*
 * 功能：比较时间

 * 参数：开始--sDate1，结束时间--sDate2，

 */
function DateDiff(sDate1,sDate2){ //sDate1和sDate2是年-月-日格式 
	if(sDate1 == "" && sDate2 == ""){
		alert("请输入开始时间和结束时间！");
		return false;
	}
	var aDate,oDate1,oDate2,iDays;
	aDate  = sDate1.split("-");
	oDate1 = new Date(aDate[1]+'-'+aDate[2]+'-'+aDate[0]);//转换为月-日-年格式 
	aDate  = sDate2.split("-");
	oDate2 = new Date(aDate[1] + '-'+aDate[2]+'-'+aDate[0]);
	if((oDate2 - oDate1)<0){
		alert("开始时间大于结束时间，请检查!");
		return false;
	}
	return true;
//	iDays  = parseInt(Math.abs(oDate2-oDate1)/1000/60/60/24); //把相差的毫秒数转换为天数 
//	alert(iDays);
//	if(iDays > 30){
//		alert("开始和结束时间的间隔超过30天！");
//		return false;
//	}else{
//		return true;
//	}
}

  /** 
   *作者linbh
   *函数功能:获取系统时间
   *参数:offset偏移分钟数

   */
   function getSysdate(offset,ignoreTime){
	  var nowDate = new Date();	
	  var cNowDate = new Date(nowDate-offset*60*1000);
	  if(ignoreTime == true){
         var strDateTime = cNowDate.getYear() + "-" + getFull(cNowDate.getMonth()+1) + "-" + getFull(cNowDate.getDate());
      }else{
         var strDateTime = cNowDate.getYear() + "-" + getFull(cNowDate.getMonth()+1) + "-" + getFull(cNowDate.getDate()) + ' ' + getFull(cNowDate.getHours()) + ":" + getFull(cNowDate.getMinutes()); 
      }
      return strDateTime;
   }
   //如果numVar为一位则左侧加零
   function getFull(numVar){
	  if((numVar+"").length >=2){
	     return numVar;
	  }else{
	     return "0"+numVar;
	  }
   }
   //获取当前月份第一天日期	
    function getMonthFirstDay(ignoreTime){
		var now = new Date();
	    var year = now.getYear();
	    var month = now.getMonth();
	    month = month+1;
	    if(month<10)month="0"+month;			
		if(ignoreTime== true){
			var startDate = year+"-"+month+"-01";	//例如：2009-05-01
		}else{
			var startDate = year+"-"+month+"-01 00:00";	//例如：2009-05-01 00:00
		}
	    return startDate;
   }  
   
  /** 
   *作者:wangxiaoting
   *函数功能:身份证到出生日期的转换
   *参数:身份证号
   */  
function getBirthdatByIdNo(iIdNo){
  var tmpStr="";
  var idDate="";
  var tmpInt=0;
  var strReturn = "";
  iIdNo = iIdNo.Trim(iIdNo);
  if((iIdNo.length!=15) &&(iIdNo.length!=18))
  {
    tmpStr = "输入的身份证号位数错误";
  }

  if(iIdNo.length==15)
  {
    tmpStr=iIdNo.substring(6,12);
    tmpStr= "19" + tmpStr;
    tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6)
  }
  else if(iIdNo.length==18)
  {   
      tmpStr=iIdNo.substring(6,14);
      tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6)
  }
  return tmpStr;
  
} 
  /** 
   *作者:wangxiaoting
   *函数功能:转换后的出生日期值赋到文本框中
   *参数:
   */  
function setBirthdate(str,str1) {
    var brithdate = getBirthdatByIdNo(str.value);
    if(brithdate.length>10) {
        alert("身份证号格式输入错误，请检查！");
        return;
    }
    if(brithdate>getSysdate(1,true)) {
        alert("身份证号输入错误，请检查！")
        return;
    }
    document.getElementById(str1).value = brithdate;
} 
 
 /** 
    * desc: 全选  
    * param:
    * return:
    * author：zhaoyu
    * date:   2009-2-18
    * version:
    */
  function checkAll(gridObj){
   var obj = document.getElementById("box");
   var iRowsNum = gridObj.getRowsNum(); 
	   if(obj.checked == true){
	      for (var i = 0; i < iRowsNum; i+=1) {                       //循环遍历数据表的行
			 gridObj.cells2(i, 0).setValue("1");  
			 gridObj.selectAll();
	      }
	   }else{
	      for (var i = 0; i < iRowsNum; i+=1) {                       //循环遍历数据表的行
			 gridObj.cells2(i, 0).setValue("0");  
			 gridObj.clearSelection();
	      }
	  }
  } 
   
   