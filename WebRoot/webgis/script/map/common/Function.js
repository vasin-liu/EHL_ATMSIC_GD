function checkDate(year,month,date){
	var enddate = ["31","28","31","30","31","30","31","31","30","31","30","31"];
	var returnDate = "";
	if (year%4==0){enddate[1]="29"}
	if (date>enddate[month]){returnDate = enddate[month]}else{returnDate = date}
	return returnDate;
}

function WeekDay(date){
	var theDate;
	if (typeof(date)=="string"){theDate = new Date(date.split("-")[0],date.split("-")[1],date.split("-")[2]);}
	if (typeof(date)=="object"){theDate = date}
	return theDate.getDay();
}

/*
 * 格式化字符串
 * @param num 数字字符串

 * @param len 长度
 */
function FormatNumber(num,len)
{
   var strResult=num.toString();
   var srcLen=strResult.length;
	 
   for (var i = 1; i <= len -srcLen ; i++){
       strResult="0"+strResult;
   }
   return strResult;
}

/**
 * 获取语言包编号

 * @param langIn WEB页面传递的语言号

 * @return 语言编号 0-中文;1-英文
 */
function GetLangID(langIn)
{
	var result=0;//默认为中文

	if (langIn == "1" ||langIn == "0") {
	    result= Number(langIn);
  	}
  	
  	if (langIn == "zh_CN"){  //中文
  		result=0;
  	}
  	
  	if (langIn == "en"){  //英文
  		result=1;
  	}
  	
  	return result;
}

/*
 * 返回日期
 * @param d the delimiter
 * @param p the pattern of your date
 */
String.prototype.toDate = function(style) {
   var y = this.substring(style.indexOf('y'),style.lastIndexOf('y')+1);//年

   var M = this.substring(style.indexOf('M'),style.lastIndexOf('M')+1);//月

   var d = this.substring(style.indexOf('d'),style.lastIndexOf('d')+1);//日

  
   var h = this.substring(style.indexOf('h'),style.lastIndexOf('h')+1);//时

   var mi = this.substring(style.indexOf('m'),style.lastIndexOf('m')+1);//分

  
   if(isNaN(y)) y = new Date().getFullYear();
   if(isNaN(M)) M = new Date().getMonth();
   if(isNaN(d)) d = new Date().getDate();
  
   if(isNaN(h)) h = new Date().getHours(); 
   if(isNaN(mi)) d = new Date().getMinutes();
  
   var dt ;
   eval ("dt = new Date('"+ y+"', '"+(M-1)+"','"+ d +"','"+h+"','"+mi+"')");
   return dt;
}

/*
 * 格式化日期

 * @param   d the delimiter
 * @param   p the pattern of your date
 * @author  meizz
 */
Date.prototype.format = function(style) {
   var o = {
 	 "M+" : this.getMonth() + 1, //month
	 "d+" : this.getDate(),      //day
	 "h+" : this.getHours(),     //hour
	 "m+" : this.getMinutes(),   //minute
	 "s+" : this.getSeconds(),   //second
	 "w+" : "天一二三四五六".charAt(this.getDay()),   //week
	 "q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
	 "S"  : this.getMilliseconds() //millisecond
   }
   if(/(y+)/.test(style)) {
	 style = style.replace(RegExp.$1,
	 (this.getFullYear() + "").substr(4 - RegExp.$1.length));
   }
   for(var k in o){
	 if(new RegExp("("+ k +")").test(style)){
	    style = style.replace(RegExp.$1,
		RegExp.$1.length == 1 ? o[k] :
		("00" + o[k]).substr(("" + o[k]).length));
	 }
   }
   return style;
}

/*截取字符前后空格。
 *通过正则表达式，将前后空格用空字符串替代。
*/
String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}



	