var caldt;
var calym;
var isFocus=false; //是否为焦点

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
 * 日期时间输入主控函数
 * @param obj 日期时间控件对象
 * @param lang 语言环境（0-中文;1-英文）
 */
function SelectDateTime(obj,lang)
{   
    var date = new Date();
    var by = date.getFullYear()-80;  //最小值 → 80 年前
    var ey = date.getFullYear()+5;  //最大值 → 5 年后
    var CalLan=GetLangID(lang); //初始化语言参数，允许传入	
	caldt = (caldt==null) ? new CalendarDT(by, ey, CalLan) : caldt;    //不用每次都初始化 2006-12-03 修正

    var CaldateFormatStyle="yyyy-MM-dd hh:mm";
    caldt.dateFormatStyle = CaldateFormatStyle;
    
    caldt.show(obj);
}  

function SelectDay(obj,lang)
{   
    var date = new Date();
    var by = date.getFullYear()-80;  //最小值 → 80 年前
    var ey = date.getFullYear()+5;  //最大值 → 5 年后
    var CalLan=GetLangID(lang); //初始化语言参数，允许传入	
	caldt = (caldt==null) ? new CalendarDT(by, ey, CalLan) : caldt;    //不用每次都初始化 2006-12-03 修正

    var CaldateFormatStyle="yyyy-MM-dd";
    caldt.dateFormatStyle = CaldateFormatStyle;
    
    caldt.show(obj);
}  

function SelectMonth(obj,lang)
{   
    var date = new Date();
    var by = date.getFullYear()-80;  //最小值 → 80 年前
    var ey = date.getFullYear()+5;  //最大值 → 5 年后
    var CalLan=GetLangID(lang); //初始化语言参数，允许传入	
	calym = (calym==null) ? new CalendarYM(by, ey, CalLan) : calym;    //不用每次都初始化 2006-12-03 修正
	
    var CaldateFormatStyle="yyyy-MM";
    calym.dateFormatStyle = CaldateFormatStyle;
     
    calym.show(obj);
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
};

/*
 * 日历类
 * @param   beginYear 1990
 * @param   endYear   2010
 * @param   lang      0(中文)|1(英语) 可自由扩充
 * @param   dateFormatStyle  "yyyy-MM-dd hh:mm";
 * @version 2006-04-01
 * @author  KimSoft (jinqinghua [at] gmail.com)
 * @update
 */
function CalendarDT(beginYear, endYear, lang, dateFormatStyle) {
   this.beginYear = 1990;
   this.endYear = 2010;
   this.lang = 0;            //0(中文) | 1(英文)
   this.dateFormatStyle = "yyyy-MM-dd hh:mm";

   if (beginYear != null && endYear != null){
	  this.beginYear = beginYear;
	  this.endYear = endYear;
   }
   if (lang != null){
	  this.lang = lang
   }

   if (dateFormatStyle != null){
	  this.dateFormatStyle = dateFormatStyle
   }

   this.dateControl = null;
   this.panel = this.getElementById("calendarPanelDT");
   this.container = this.getElementById("ContainerPanelDT");
   this.form  = null;

   //初始化时间
   this.date = new Date();
   this.year = this.date.getFullYear();
   this.month = this.date.getMonth();
  
   this.day=this.date.getDate();
   this.hour=this.date.getHours();
   this.minute=this.date.getMinutes();
 
   this.colors = {
   "cur_word"      : "#FFFFFF",  //当日日期文字颜色
   "cur_bg"        : "#00FF00",  //当日日期单元格背影色
   "sel_bg"        : "#FFCCCC",  //已被选择的日期单元格背影色
   "sun_word"      : "#FF0000",  //星期天文字颜色
   "sat_word"      : "#009F00",  //星期六文字颜色
   "td_word_light" : "#333333",  //单元格文字颜色
   "td_word_dark"  : "#CCCCCC",  //单元格文字暗色
   "td_bg_out"     : "#EFEFEF",  //单元格背影色
   "td_bg_over"    : "#FFCC00",  //单元格背影色
   "tr_word"       : "#FFFFFF",  //日历头文字颜色
   "tr_bg"         : "#666666",  //日历头背影色
   "input_border"  : "#CCCCCC",  //input控件的边框颜色
   "input_bg"      : "#EFEFEF"   //input控件的背影色
   }

   this.draw();
   this.bindYear(); 
   this.bindMonth();
   this.bindHour(); //初始化小时
   this.bindMin(); //初始化分钟
   this.changeSelect();
   this.bindData();
} 
	

/*
* 日历类属性（语言包，可自由扩展）
*/
CalendarDT.language = {
	  "year"   : [[""], [""]],
	  "months" : [["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
			["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"]
			 ],
	  "weeks"  : [["日","一","二","三","四","五","六"],
			["SUN","MON","TUR","WED","THU","FRI","SAT"]
			 ],
	  "clear"  : [["取消"], ["CLS"]],
	  "today"  : [["今天"], ["TODAY"]],
	  "close"  : [["关闭"], ["CLOSE"]],
	  "timetip"  : [["时间"], ["Time"]]  //提示输入时间
}

/*
* 在页面上绘制日历
*/
CalendarDT.prototype.draw = function() {
   calendarDT = this;
   var mvAry = [];

   mvAry[mvAry.length]  = '  <div name="calendarFormDT" style="margin: 0px;">';
   mvAry[mvAry.length]  = '    <table width="100%" border="0" cellpadding="0" cellspacing="1" >';
   mvAry[mvAry.length]  = '      <tr>';
   mvAry[mvAry.length]  = '        <th align="left" width="1%"><input style="border: 1px solid ' + calendarDT.colors["input_border"] + ';background-color:' + calendarDT.colors["input_bg"] + ';width:16px;height:20px;" name="prevMonthDT" type="button" id="prevMonthDT" value="&lt;" /></th>';
   mvAry[mvAry.length]  = '        <th align="center" width="98%" nowrap="nowrap"><select name="calendarYearDT" id="calendarYearDT" style="font-size:12px;"></select><select name="calendarMonthDT" id="calendarMonthDT" style="font-size:12px;"></select></th>';
  
   mvAry[mvAry.length]  = '        <th align="right" width="1%"><input style="border: 1px solid ' + calendarDT.colors["input_border"] + ';background-color:' + calendarDT.colors["input_bg"] + ';width:16px;height:20px;" name="nextMonthDT" type="button" id="nextMonthDT" value="&gt;" /></th>';
   mvAry[mvAry.length]  = '      </tr>';
   mvAry[mvAry.length]  = '    </table>';
   mvAry[mvAry.length]  = '    <table id="calendarTableDT" width="100%" style="font-size:12px;border:0px solid #CCCCCC;background-color:#FFFFFF" border="0" cellpadding="2" cellspacing="1">';
   mvAry[mvAry.length]  = '      <tr>';
  
   for(var i = 0; i < 7; i++) {
	  mvAry[mvAry.length]  = '      <th style="font-weight:normal;background-color:' + calendarDT.colors["tr_bg"] + ';color:' + calendarDT.colors["tr_word"] + ';">' + CalendarDT.language["weeks"][this.lang][i] + '</th>';
   }
   mvAry[mvAry.length]  = '      </tr>';
   for(var i = 0; i < 6;i++){
	  mvAry[mvAry.length]  = '    <tr align="center">';
	  for(var j = 0; j < 7; j++) {
	     if (j == 0){
		    mvAry[mvAry.length]  = '  <td style="cursor:default;color:' + calendarDT.colors["sun_word"] + ';"></td>';
	     } else if(j == 6) {
		    mvAry[mvAry.length]  = '  <td style="cursor:default;color:' + calendarDT.colors["sat_word"] + ';"></td>';
	     } else {
		    mvAry[mvAry.length]  = '  <td style="cursor:default;"></td>';
	     }
	   }
	   mvAry[mvAry.length]  = '    </tr>';
   }
   mvAry[mvAry.length]  = '      <tr style="background-color:' + calendarDT.colors["input_bg"] + ';">';

   //时间输入
   mvAry[mvAry.length]  = '        <th colspan="5" 	width="71.4%" style="fontsize:12px;font-weight: normal;">'+CalendarDT.language["timetip"][this.lang]+'<select name="calendarHourDT" id="calendarHourDT" style="font-size:12px;"></select>:<select name="calendarMinDT" id="calendarMinDT" style="font-size:12px;"></select></th>';
  
   //关闭按钮
   mvAry[mvAry.length]  = '        <th colspan="2"><input name="calendarCloseDT" type="button" id="calendarCloseDT" value="' + CalendarDT.language["close"][this.lang] + '" style="border: 1px solid ' + calendarDT.colors["input_border"] + ';background-color:' + calendarDT.colors["input_bg"] + ';width:100%;height:20px;font-size:12px;"/></th>';
   mvAry[mvAry.length]  = '      </tr>';
   mvAry[mvAry.length]  = '    </table>';
   mvAry[mvAry.length]  = '  </div>';
   this.panel.innerHTML = mvAry.join("");
   var obj = this.getElementById("prevMonthDT");
   obj.onclick = function () {calendarDT.goPrevMonth(calendarDT);}
   obj.onblur = function () {calendarDT.onblur();}
   this.prevMonthDT= obj;
  
   obj = this.getElementById("nextMonthDT");
   obj.onclick = function () {calendarDT.goNextMonth(calendarDT);}
   obj.onblur = function () {calendarDT.onblur();}
   this.nextMonthDT= obj;
  
   obj = this.getElementById("calendarCloseDT");
   obj.onclick = function () {calendarDT.hide();}
   this.calendarCloseDT = obj;
  
   obj = this.getElementById("calendarYearDT");
   obj.onchange = function () {calendarDT.update(calendarDT);}
   obj.onblur = function () {calendarDT.onblur();}
   this.calendarYearDT = obj;
 
   obj = this.getElementById("calendarMonthDT");
   with(obj)
   {
	  onchange = function () {calendarDT.update(calendarDT);}
	  onblur = function () {calendarDT.onblur();}
    }this.calendarMonthDT = obj;
	  
	//设置小时事件
	obj = this.getElementById("calendarHourDT");
	obj.onchange = function () {calendarDT.updateTime(calendarDT);}
	obj.onblur = function () {calendarDT.onblur();}
	this.calendarHourDT = obj;

	//设置分钟事件
	obj = this.getElementById("calendarMinDT");
	obj.onchange = function () {calendarDT.updateTime(calendarDT);}
	obj.onblur = function () {calendarDT.onblur();}
	this.calendarMinDT = obj;
   } 
  
   //年份下拉框绑定数据
   CalendarDT.prototype.bindYear = function() {
   //var cy = this.form.calendarYear;
   var cy = this.calendarYearDT;
   cy.length = 0;
   for (var i = this.beginYear; i <= this.endYear; i++){
       cy.options[cy.length] = new Option(i + CalendarDT.language["year"][this.lang], i);
   }
} 

//月份下拉框绑定数据
CalendarDT.prototype.bindMonth = function() {
   var cm = this.calendarMonthDT;
   cm.length = 0;
   for (var i = 0; i < 12; i++){
	  cm.options[cm.length] = new Option(CalendarDT.language["months"][this.lang][i], i);
   }
}

//小时下拉框绑定数据
CalendarDT.prototype.bindHour = function() {
   var ch = this.calendarHourDT;
   ch.length = 0;
   for (var i = 0; i <= 23; i++){
  	 strItem=FormatNumber(i,2);
     ch.options[ch.length] = new Option(strItem, strItem);
   }
}

//分钟下拉框绑定数据
CalendarDT.prototype.bindMin = function() {
   var cmi = this.calendarMinDT;
   cmi.length = 0;
   for (var i = 0; i <= 59; i++){
  	 strItem=FormatNumber(i,2);
     cmi.options[cmi.length] = new Option(strItem, strItem);
   }
}

//向前一月
CalendarDT.prototype.goPrevMonth = function(e){
   if (this.year == this.beginYear && this.month == 0){return;}
   this.month--;
   if (this.month == -1) {
     this.year--;
     this.month = 11;
   }
   this.date = new Date(this.year, this.month, 1);
   this.changeSelect();
   this.bindData();
}

//向后一月
CalendarDT.prototype.goNextMonth = function(e){
   if (this.year == this.endYear && this.month == 11){return;}
   this.month++;
   if (this.month == 12) {
     this.year++;
     this.month = 0;
   }
   this.date = new Date(this.year, this.month, 1);
   this.changeSelect();
   this.bindData();
}

//改变SELECT选中状态
CalendarDT.prototype.changeSelect = function() {
   var cy = this.calendarYearDT;
   var cm = this.calendarMonthDT;
   var ch=this.calendarHourDT;
   var cmi=this.calendarMinDT;
  
   for (var i= 0; i < cy.length; i++){
	 if (cy.options[i].value == this.date.getFullYear()){
	    cy[i].selected = true;
	    break;
	 }
   }
   for (var i= 0; i < cm.length; i++){
	 if (cm.options[i].value == this.date.getMonth()){
	    cm[i].selected = true;
	    break;
	 }
   }
  
   for (var i= 0; i < ch.length; i++){
	 if (ch.options[i].value == this.hour){
	    ch[i].selected = true;
	    break;
	 }
   }
  
   for (var i= 0; i < cmi.length; i++){
	 if (cmi.options[i].value == this.minute){
	    cmi[i].selected = true;
	    break;
	 }
   }
	  
}

//更新年、月触发的动作
CalendarDT.prototype.update = function (e){
   this.year  = e.calendarYearDT.options[e.calendarYearDT.selectedIndex].value;
   this.month = e.calendarMonthDT.options[e.calendarMonthDT.selectedIndex].value;
  
   this.day=1;
  
   this.date = new Date(this.year, this.month, this.day);
   this.changeSelect();
   this.bindData();
}

//更新时间触发的动作
CalendarDT.prototype.updateTime = function (e){

   this.hour=e.calendarHourDT.options[e.calendarHourDT.selectedIndex].value;
   this.minute=e.calendarMinDT.options[e.calendarMinDT.selectedIndex].value;

   this.RefreshData();
}

/**
填充输入框
*/
CalendarDT.prototype.RefreshData = function () {
   var calendarDT = this;
   var strdate=new Date(calendarDT.date.getFullYear(),calendarDT.date.getMonth(),this.day,this.hour,this.minute).format(calendarDT.dateFormatStyle);
   calendarDT.dateControl.value = strdate;
}


//绑定数据到月视图
CalendarDT.prototype.bindData = function () {
   var calendarDT = this;
 
   var dateArray = this.getMonthViewArray(this.date.getYear(), this.date.getMonth());
   var tds = this.getElementById("calendarTableDT").getElementsByTagName("td");
  
   for(var i = 0; i < tds.length; i++) { 	
      tds[i].style.backgroundColor = calendarDT.colors["td_bg_out"];
	  tds[i].onclick = function () {return;}
	  tds[i].onmouseover = function () {return;}
	  tds[i].onmouseout = function () {return;}
	  if (i > dateArray.length - 1) break;
	  tds[i].innerHTML = dateArray[i];
	  if (dateArray[i] != "&nbsp;"){
		 tds[i].onclick = function () {//点击日 事件
			if (calendarDT.dateControl != null){
					calendarDT.day=this.innerHTML;
					calendarDT.RefreshData();
			}
			calendarDT.dateControl.focus(); //重新获得焦点,以便能触发blur事件
		 }

	     tds[i].onmouseover = function () {
		    this.style.backgroundColor = calendarDT.colors["td_bg_over"];
	     }
	     tds[i].onmouseout = function () {
		    this.style.backgroundColor = calendarDT.colors["td_bg_out"];
	     }
		
	     if (new Date().format(calendarDT.dateFormatStyle.substring(0,10)) ==
		    new Date(calendarDT.date.getFullYear(),calendarDT.date.getMonth(),dateArray[i]).format(calendarDT.dateFormatStyle.substring(0,10))) {
		    tds[i].style.backgroundColor = calendarDT.colors["cur_bg"];
		    tds[i].onmouseover = function () {
		       this.style.backgroundColor = calendarDT.colors["td_bg_over"];
		    }
		    tds[i].onmouseout = function () {
		       this.style.backgroundColor = calendarDT.colors["cur_bg"];
		    }
	     }
	  
	     //设置已被选择的日期单元格背影色
	     if (calendarDT.dateControl != null && calendarDT.dateControl.value == new Date(calendarDT.date.getFullYear(),
				   calendarDT.date.getMonth(),
				   dateArray[i]).format(calendarDT.dateFormatStyle.substring(0,10))) {
		    tds[i].style.backgroundColor = calendarDT.colors["sel_bg"];
		    tds[i].onmouseover = function () {
		       this.style.backgroundColor = calendarDT.colors["td_bg_over"];
		    }

		    tds[i].onmouseout = function () {
		       this.style.backgroundColor = calendarDT.colors["sel_bg"];
		    }
	     }
      }
   }
}

//根据年、月得到月视图数据(数组形式)
CalendarDT.prototype.getMonthViewArray = function (y, m) {
   var mvArray = [];
   var dayOfFirstDay = new Date(y, m, 1).getDay();
   var daysOfMonth = new Date(y, m + 1, 0).getDate();
   for (var i = 0; i < 42; i++) {
	  mvArray[i] = "&nbsp;";
   }
   for (var i = 0; i < daysOfMonth; i++){
	  mvArray[i + dayOfFirstDay] = i + 1;
   }
   return mvArray;
}

//扩展 document.getElementById(id) 多浏览器兼容性 from meizz tree source
CalendarDT.prototype.getElementById = function(id){
   if (typeof(id) != "string" || id == "") return null;
   if (document.getElementById) return document.getElementById(id);
   if (document.all) return document.all(id);
   try {return eval(id);} catch(e){ return null;}
}

//扩展 object.getElementsByTagName(tagName)
CalendarDT.prototype.getElementsByTagName = function(object, tagName){
   if (document.getElementsByTagName) return document.getElementsByTagName(tagName);
   if (document.all) return document.all.tags(tagName);
}

//取得HTML控件绝对位置
CalendarDT.prototype.getAbsPoint = function (e){
   var x = e.offsetLeft;
   var y = e.offsetTop;
   while(e = e.offsetParent){
	  x += e.offsetLeft;
	  y += e.offsetTop;
   }
   return {"x": x, "y": y};
}


//显示日历
CalendarDT.prototype.show = function (dateObj, popControl) {

   if (dateObj == null){
	  throw new Error("arguments[0] is necessary")
   }
   this.dateControl = dateObj;
  	 
   this.date = (dateObj.value.length > 0) ? new Date(dateObj.value.toDate(this.dateFormatStyle)) : new Date() ;
 
   this.year = this.date.getFullYear();
   this.month = this.date.getMonth();
  
   this.day = this.date.getDate();
   this.hour = this.date.getHours();
   this.minute = this.date.getMinutes();
  
   this.changeSelect();
   this.bindData();

   if (popControl == null){
	   popControl = dateObj;
   }
   var xy = this.getAbsPoint(popControl);
   this.panel.style.left = xy.x  + "px";  //弹出left位置（相对于输入框）
   this.panel.style.top = (xy.y + dateObj.offsetHeight) + "px"; //弹出top位置（相对于输入框）

   this.panel.style.display = "";
   this.container.style.display = "";
  
   dateObj.onblur = function(){calendarDT.onblur();} //失去焦点不自动关闭，因为还要选时间
   this.container.onmouseover = function(){isFocus=true;}
   this.container.onmouseout = function(){isFocus=false;}
}

//隐藏日历
CalendarDT.prototype.hide = function() {
   this.panel.style.display = "none";
   this.container.style.display = "none";
   isFocus=false;
}

//焦点转移时隐藏日历 
CalendarDT.prototype.onblur = function() {
   if(!isFocus){this.hide();}
}

document.write('<div id="ContainerPanelDT" style="display:none"><div id="calendarPanelDT" style="position: absolute;display: none;z-index: 9999;');
document.write('background-color: #FFFFFF;border: 1px solid #CCCCCC;width:175px;font-size:12px;"></div>');
if(document.all)
{
document.write('<iframe style="position:absolute;z-index:2000;width:expression(this.previousSibling.offsetWidth);');
document.write('height:expression(this.previousSibling.offsetHeight);');
document.write('left:expression(this.previousSibling.offsetLeft);top:expression(this.previousSibling.offsetTop);');
document.write('display:expression(this.previousSibling.style.display);" scrolling="no" frameborder="no"></iframe>');
}
document.write('</div>');

/*
 * 日历类

 * @param   beginYear 1990
 * @param   endYear   2010
 * @param   lang      0(中文)|1(英语) 可自由扩充

 * @param   dateFormatStyle  "yyyy-MM-dd hh:mm";
 * @version 2006-04-01
 * @author  KimSoft (jinqinghua [at] gmail.com)
 * @update
 */
function CalendarYM(beginYear, endYear, lang, dateFormatStyle) {
   this.beginYear = 1990;
   this.endYear = 2010;
   this.lang = 0;            //0(中文) | 1(英文)
   this.dateFormatStyle = "yyyy-MM-dd hh:mm";

   if (beginYear != null && endYear != null){
	  this.beginYear = beginYear;
	  this.endYear = endYear;
   }
   if (lang != null){
	  this.lang = lang
   }

   if (dateFormatStyle != null){
	  this.dateFormatStyle = dateFormatStyle
   }

   this.dateControl = null;
   this.panel = this.getElementById("calendarPanelYM");
   this.container = this.getElementById("ContainerPanelYM");
   this.form  = null;

   //初始化时间

   this.date = new Date();
   this.year = this.date.getFullYear();
   this.month = this.date.getMonth();
  
   this.day=this.date.getDate();
   this.hour=this.date.getHours();
   this.minute=this.date.getMinutes();
 
   this.colors = {
   "cur_word"      : "#FFFFFF",  //当日日期文字颜色
   "cur_bg"        : "#00FF00",  //当日日期单元格背影色
   "sel_bg"        : "#FFCCCC",  //已被选择的日期单元格背影色

   "sun_word"      : "#FF0000",  //星期天文字颜色

   "sat_word"      : "#009F00",  //星期六文字颜色

   "td_word_light" : "#333333",  //单元格文字颜色

   "td_word_dark"  : "#CCCCCC",  //单元格文字暗色

   "td_bg_out"     : "#EFEFEF",  //单元格背影色
   "td_bg_over"    : "#FFCC00",  //单元格背影色
   "tr_word"       : "#FFFFFF",  //日历头文字颜色

   "tr_bg"         : "#666666",  //日历头背影色
   "input_border"  : "#CCCCCC",  //input控件的边框颜色

   "input_bg"      : "#EFEFEF"   //input控件的背影色
   }

   this.draw();
   this.bindYear(); 
   this.bindMonth();
//   this.bindHour(); //初始化小时
//   this.bindMin(); //初始化分钟
   this.changeSelect();
   this.bindData();
} 
	

/*
* 日历类属性（语言包，可自由扩展）
*/
CalendarYM.language = {
	  "year"   : [[""], [""]],
	  "months" : [["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
			["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"]
			 ],
	  "weeks"  : [["日","一","二","三","四","五","六"],
			["SUN","MON","TUR","WED","THU","FRI","SAT"]
			 ],
	  "clear"  : [["取消"], ["CLS"]],
	  "today"  : [["今天"], ["TODAY"]],
	  "close"  : [["确定"], ["CLOSE"]],
	  "timetip"  : [["时间"], ["Time"]]  //提示输入时间
}

/*
* 在页面上绘制日历
*/
CalendarYM.prototype.draw = function() {
   calendarYM = this;
   var mvAry = [];

   mvAry[mvAry.length]  = '  <div name="calendarFormYM" style="margin: 0px;">';
   mvAry[mvAry.length]  = '    <table width="100%" border="0" cellpadding="0" cellspacing="1" >';
   mvAry[mvAry.length]  = '      <tr>';
   mvAry[mvAry.length]  = '        <th align="left" width="1%"><input style="border: 1px solid ' + calendarYM.colors["input_border"] + ';background-color:' + calendarYM.colors["input_bg"] + ';width:16px;height:20px;" name="prevMonthYM" type="button" id="prevMonthYM" value="&lt;" /></th>';
   mvAry[mvAry.length]  = '        <th align="center" width="98%" nowrap="nowrap"><select name="calendarYearYM" id="calendarYearYM" style="font-size:12px;"></select><select name="calendarMonthYM" id="calendarMonthYM" style="font-size:12px;"></select></th>';
  
   mvAry[mvAry.length]  = '        <th align="right" width="1%"><input style="border: 1px solid ' + calendarYM.colors["input_border"] + ';background-color:' + calendarYM.colors["input_bg"] + ';width:16px;height:20px;" name="nextMonthYM" type="button" id="nextMonthYM" value="&gt;" /></th>';
   mvAry[mvAry.length]  = '      </tr>';
   mvAry[mvAry.length]  = '    </table>';
   mvAry[mvAry.length]  = '    <table id="calendarTableYM" width="100%" style="font-size:12px;border:0px solid #CCCCCC;background-color:#FFFFFF" border="0" cellpadding="2" cellspacing="1">';
//   mvAry[mvAry.length]  = '      <tr>';
//  
//   for(var i = 0; i < 7; i++) {
//	  mvAry[mvAry.length]  = '      <th style="font-weight:normal;background-color:' + calendarYM.colors["tr_bg"] + ';color:' + calendarYM.colors["tr_word"] + ';">' + CalendarYM.language["weeks"][this.lang][i] + '</th>';
//   }
//   mvAry[mvAry.length]  = '      </tr>';
//   for(var i = 0; i < 6;i++){
//	  mvAry[mvAry.length]  = '    <tr align="center">';
//	  for(var j = 0; j < 7; j++) {
//	     if (j == 0){
//		    mvAry[mvAry.length]  = '  <td style="cursor:default;color:' + calendarYM.colors["sun_word"] + ';"></td>';
//	     } else if(j == 6) {
//		    mvAry[mvAry.length]  = '  <td style="cursor:default;color:' + calendarYM.colors["sat_word"] + ';"></td>';
//	     } else {
//		    mvAry[mvAry.length]  = '  <td style="cursor:default;"></td>';
//	     }
//	   }
//	   mvAry[mvAry.length]  = '    </tr>';
//   }
   mvAry[mvAry.length]  = '      <tr style="background-color:' + calendarYM.colors["input_bg"] + ';">';

   //时间输入
   mvAry[mvAry.length]  = '        <th colspan="5" 	width="71.4%" style="fontsize:12px;font-weight: normal;display:none">'+CalendarYM.language["timetip"][this.lang]+'<select name="calendarHourYM" id="calendarHourYM" style="font-size:12px;"></select>:<select name="calendarMinYM" id="calendarMinYM" style="font-size:12px;display:none"></select></th>';
  
   //确定按钮
   mvAry[mvAry.length]  = '        <th colspan="2"><input name="calendarCloseYM" type="button" id="calendarCloseYM" value="' + CalendarYM.language["close"][this.lang] + '" style="border: 1px solid ' + calendarYM.colors["input_border"] + ';background-color:' + calendarYM.colors["input_bg"] + ';width:100%;height:20px;font-size:12px;"/></th>';
   mvAry[mvAry.length]  = '      </tr>';
   mvAry[mvAry.length]  = '    </table>';
   mvAry[mvAry.length]  = '  </div>';
   this.panel.innerHTML = mvAry.join("");
   var obj = this.getElementById("prevMonthYM");
   obj.onclick = function () {calendarYM.goPrevMonth(calendarYM);}
   obj.onblur = function () {calendarYM.onblur();}
   this.prevMonthYM= obj;
  
   obj = this.getElementById("nextMonthYM");
   obj.onclick = function () {calendarYM.goNextMonth(calendarYM);}
   obj.onblur = function () {calendarYM.onblur();}
   this.nextMonthYM= obj;
  
   obj = this.getElementById("calendarCloseYM");
   obj.onclick = function () {
	   calendarYM.RefreshData();
	   calendarYM.hide();
   }
   this.calendarCloseYM = obj;
  
   obj = this.getElementById("calendarYearYM");
   obj.onchange = function () {calendarYM.update(calendarYM);}
   obj.onblur = function () {calendarYM.onblur();}
   this.calendarYearYM = obj;
 
   obj = this.getElementById("calendarMonthYM");
   with(obj)
   {
	  onchange = function () {calendarYM.update(calendarYM);}
	  onblur = function () {calendarYM.onblur();}
    }this.calendarMonthYM = obj;
	  
	//设置小时事件
	obj = this.getElementById("calendarHourYM");
	obj.onchange = function () {calendarYM.updateTime(calendarYM);}
	obj.onblur = function () {calendarYM.onblur();}
	this.calendarHourYM = obj;

	//设置分钟事件
	obj = this.getElementById("calendarMinYM");
	obj.onchange = function () {calendarYM.updateTime(calendarYM);}
	obj.onblur = function () {calendarYM.onblur();}
	this.calendarMinYM = obj;
   } 
  
   //年份下拉框绑定数据

   CalendarYM.prototype.bindYear = function() {
   //var cy = this.form.calendarYear;
   var cy = this.calendarYearYM;
   cy.length = 0;
   for (var i = this.beginYear; i <= this.endYear; i++){
       cy.options[cy.length] = new Option(i + CalendarYM.language["year"][this.lang], i);
   }
} 

//月份下拉框绑定数据

CalendarYM.prototype.bindMonth = function() {
   var cm = this.calendarMonthYM;
   cm.length = 0;
   for (var i = 0; i < 12; i++){
	  cm.options[cm.length] = new Option(CalendarYM.language["months"][this.lang][i], i);
   }
}

//小时下拉框绑定数据

CalendarYM.prototype.bindHour = function() {
   var ch = this.calendarHourYM;
   ch.length = 0;
   for (var i = 0; i <= 23; i++){
  	 strItem=FormatNumber(i,2);
     ch.options[ch.length] = new Option(strItem, strItem);
   }
}

//分钟下拉框绑定数据

CalendarYM.prototype.bindMin = function() {
   var cmi = this.calendarMinYM;
   cmi.length = 0;
   for (var i = 0; i <= 59; i++){
  	 strItem=FormatNumber(i,2);
     cmi.options[cmi.length] = new Option(strItem, strItem);
   }
}

//向前一月

CalendarYM.prototype.goPrevMonth = function(e){
   if (this.year == this.beginYear && this.month == 0){return;}
   this.month--;
   if (this.month == -1) {
     this.year--;
     this.month = 11;
   }
   this.date = new Date(this.year, this.month, 1);
   this.changeSelect();
   this.bindData();
}

//向后一月

CalendarYM.prototype.goNextMonth = function(e){
   if (this.year == this.endYear && this.month == 11){return;}
   this.month++;
   if (this.month == 12) {
     this.year++;
     this.month = 0;
   }
   this.date = new Date(this.year, this.month, 1);
   this.changeSelect();
   this.bindData();
}

//改变SELECT选中状态

CalendarYM.prototype.changeSelect = function() {
   var cy = this.calendarYearYM;
   var cm = this.calendarMonthYM;
   var ch=this.calendarHourYM;
   var cmi=this.calendarMinYM;
  
   for (var i= 0; i < cy.length; i++){
	 if (cy.options[i].value == this.date.getFullYear()){
	    cy[i].selected = true;
	    break;
	 }
   }
   for (var i= 0; i < cm.length; i++){
	 if (cm.options[i].value == this.date.getMonth()){
	    cm[i].selected = true;
	    break;
	 }
   }
   
  
   for (var i= 0; i < ch.length; i++){
	 if (ch.options[i].value == this.hour){
	    ch[i].selected = true;
	    break;
	 }
   }
  
   for (var i= 0; i < cmi.length; i++){
	 if (cmi.options[i].value == this.minute){
	    cmi[i].selected = true;
	    break;
	 }
   }
	  
}

//更新年、月触发的动作

CalendarYM.prototype.update = function (e){
   this.year  = e.calendarYearYM.options[e.calendarYearYM.selectedIndex].value;
   this.month = e.calendarMonthYM.options[e.calendarMonthYM.selectedIndex].value;
   this.day=1;
  
   this.date = new Date(this.year, this.month, this.day);
   this.changeSelect();
   this.bindData();
   this.RefreshData();
}

//更新时间触发的动作

CalendarYM.prototype.updateTime = function (e){

   this.hour=e.calendarHourYM.options[e.calendarHourYM.selectedIndex].value;
   this.minute=e.calendarMinYM.options[e.calendarMinYM.selectedIndex].value;

   this.RefreshData();
}

/**
填充输入框

*/
CalendarYM.prototype.RefreshData = function () {
   var calendarYM = this;
   var strdate=new Date(calendarYM.date.getFullYear(),calendarYM.date.getMonth(),this.day,this.hour,this.minute).format(calendarYM.dateFormatStyle);
   calendarYM.dateControl.value = strdate;
}


//绑定数据到月视图
CalendarYM.prototype.bindData = function () {
   var calendarYM = this;
 
   var dateArray = this.getMonthViewArray(this.date.getYear(), this.date.getMonth());
   var tds = this.getElementById("calendarTableYM").getElementsByTagName("td");
  
   for(var i = 0; i < tds.length; i++) { 	
      tds[i].style.backgroundColor = calendarYM.colors["td_bg_out"];
	  tds[i].onclick = function () {return;}
	  tds[i].onmouseover = function () {return;}
	  tds[i].onmouseout = function () {return;}
	  if (i > dateArray.length - 1) break;
	  tds[i].innerHTML = dateArray[i];
	  if (dateArray[i] != "&nbsp;"){
		 tds[i].onclick = function () {//点击日 事件
			if (calendarYM.dateControl != null){
					calendarYM.day=this.innerHTML;
					calendarYM.RefreshData();
			}
			calendarYM.dateControl.focus(); //重新获得焦点,以便能触发blur事件
		 }

	     tds[i].onmouseover = function () {
		    this.style.backgroundColor = calendarYM.colors["td_bg_over"];
	     }
	     tds[i].onmouseout = function () {
		    this.style.backgroundColor = calendarYM.colors["td_bg_out"];
	     }
		
	     if (new Date().format(calendarYM.dateFormatStyle.substring(0,10)) ==
		    new Date(calendarYM.date.getFullYear(),calendarYM.date.getMonth(),dateArray[i]).format(calendarYM.dateFormatStyle.substring(0,10))) {
		    tds[i].style.backgroundColor = calendarYM.colors["cur_bg"];
		    tds[i].onmouseover = function () {
		       this.style.backgroundColor = calendarYM.colors["td_bg_over"];
		    }
		    tds[i].onmouseout = function () {
		       this.style.backgroundColor = calendarYM.colors["cur_bg"];
		    }
	     }
	  
	     //设置已被选择的日期单元格背影色

	     if (calendarYM.dateControl != null && calendarYM.dateControl.value == new Date(calendarYM.date.getFullYear(),
				   calendarYM.date.getMonth(),
				   dateArray[i]).format(calendarYM.dateFormatStyle.substring(0,10))) {
		    tds[i].style.backgroundColor = calendarYM.colors["sel_bg"];
		    tds[i].onmouseover = function () {
		       this.style.backgroundColor = calendarYM.colors["td_bg_over"];
		    }

		    tds[i].onmouseout = function () {
		       this.style.backgroundColor = calendarYM.colors["sel_bg"];
		    }
	     }
      }
   }
}

//根据年、月得到月视图数据(数组形式)
CalendarYM.prototype.getMonthViewArray = function (y, m) {
   var mvArray = [];
   var dayOfFirstDay = new Date(y, m, 1).getDay();
   var daysOfMonth = new Date(y, m + 1, 0).getDate();
   for (var i = 0; i < 42; i++) {
	  mvArray[i] = "&nbsp;";
   }
   for (var i = 0; i < daysOfMonth; i++){
	  mvArray[i + dayOfFirstDay] = i + 1;
   }
   return mvArray;
}

//扩展 document.getElementById(id) 多浏览器兼容性 from meizz tree source
CalendarYM.prototype.getElementById = function(id){
   if (typeof(id) != "string" || id == "") return null;
   if (document.getElementById) return document.getElementById(id);
   if (document.all) return document.all(id);
   try {return eval(id);} catch(e){ return null;}
}

//扩展 object.getElementsByTagName(tagName)
CalendarYM.prototype.getElementsByTagName = function(object, tagName){
   if (document.getElementsByTagName) return document.getElementsByTagName(tagName);
   if (document.all) return document.all.tags(tagName);
}

//取得HTML控件绝对位置
CalendarYM.prototype.getAbsPoint = function (e){
   var x = e.offsetLeft;
   var y = e.offsetTop;
   while(e = e.offsetParent){
	  x += e.offsetLeft;
	  y += e.offsetTop;
   }
   return {"x": x, "y": y};
}


//显示日历
CalendarYM.prototype.show = function (dateObj, popControl) {

   if (dateObj == null){
	  throw new Error("arguments[0] is necessary")
   }
   this.dateControl = dateObj;
  	 
   this.date = (dateObj.value.length > 0) ? new Date(dateObj.value.toDate(this.dateFormatStyle)) : new Date() ;
 
   this.year = this.date.getFullYear();
   this.month = this.date.getMonth();
  
   this.day = this.date.getDate();
   this.hour = this.date.getHours();
   this.minute = this.date.getMinutes();
  
   this.changeSelect();
   this.bindData();

   if (popControl == null){
	   popControl = dateObj;
   }
   var xy = this.getAbsPoint(popControl);
   this.panel.style.left = xy.x  + "px";  //弹出left位置（相对于输入框）
   this.panel.style.top = (xy.y + dateObj.offsetHeight) + "px"; //弹出top位置（相对于输入框）

   this.panel.style.display = "";
   this.container.style.display = "";
  
   dateObj.onblur = function(){calendarYM.onblur();} //失去焦点不自动关闭，因为还要选时间

   this.container.onmouseover = function(){isFocus=true;}
   this.container.onmouseout = function(){isFocus=false;}
}

//隐藏日历
CalendarYM.prototype.hide = function() {
   this.panel.style.display = "none";
   this.container.style.display = "none";
   isFocus=false;
}

//焦点转移时隐藏日历 
CalendarYM.prototype.onblur = function() {
   if(!isFocus){this.hide();}
}

document.write('<div id="ContainerPanelYM" style="display:none"><div id="calendarPanelYM" style="position: absolute;display: none;z-index: 9999;');
document.write('background-color: #FFFFFF;border: 1px solid #CCCCCC;width:175px;font-size:12px;"></div>');
if(document.all)
{
document.write('<iframe style="position:absolute;z-index:2000;width:expression(this.previousSibling.offsetWidth);');
document.write('height:expression(this.previousSibling.offsetHeight);');
document.write('left:expression(this.previousSibling.offsetLeft);top:expression(this.previousSibling.offsetTop);');
document.write('display:expression(this.previousSibling.style.display);" scrolling="no" frameborder="no"></iframe>');
}
document.write('</div>');
