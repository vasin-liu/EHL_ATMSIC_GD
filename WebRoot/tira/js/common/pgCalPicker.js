//Calendar Picker//
var gdCtrl = new Object();
var goSelectTag = new Array();
var gcGray = '#808080';	/*月份未到的日子的颜色*/
var gcToggle = '#FFFF00';	/*鼠标经过的颜色*/
var yqToggle = '#FFFFFF';	/*鼠标移去的颜色*/
var gcBG = '#dFECF8';	/*日历表的小表格背景色*/
var gdCurDate = new Date();
var giYear = gdCurDate.getFullYear();
var giMonth = gdCurDate.getMonth()+1;
var giDay = gdCurDate.getDate();
var isFormat = false;
var valObj = new Object();
function fPopCalendar(popCtrl, dateCtrl){
	event.cancelBubble = true;
	gdCtrl = dateCtrl;
	fSetYearMon(giYear,giMonth);
	var point = fGetXY(popCtrl);
	with (VicPopCal.style){
//		left = point.x ;
		left = point.x+popCtrl.offsetWidth-125;
		top  = point.y + popCtrl.offsetHeight + 1;
		width = VicPopCal.offsetWidth;
		height = VicPopCal.offsetHeight;
		fToggleTags(point);
		visibility = 'visible';
	}
	VicPopCal.focus();
}

function fPopCalendarFormat(popCtrl, dateObj, dateCtrl){
	event.cancelBubble = true;
	gdCtrl = dateCtrl;
	fSetYearMon(giYear,giMonth);
	var point = fGetXY(popCtrl);
	with (VicPopCal.style){
		left = point.x+popCtrl.offsetWidth-125;
		top  = point.y + popCtrl.offsetHeight + 1;
		width = VicPopCal.offsetWidth;
		height = VicPopCal.offsetHeight;
		fToggleTags(point);
		visibility = 'visible';
	}
	VicPopCal.focus();
	isFormat = true;
	valObj = dateObj;
}

function fSetDate(iYear, iMonth, iDay){
	var iMonthNew = new String(iMonth);
	var iDayNew = new String(iDay);
	if(iMonthNew.length < 2 ) iMonthNew = '0' + iMonthNew;
	if(iDayNew.length < 2 ) iDayNew = '0' + iDayNew;
	gdCtrl.value = iYear + '-' + iMonthNew + '-' + iDayNew;
	fHideCalendar();
	if (isFormat) {
		valObj.value = gdCtrl.value;
		formatDate(gdCtrl);
	}
}

function fHideCalendar(){
	VicPopCal.style.visibility = 'hidden';
 	for (var i=0;i<goSelectTag.length;i++){
	goSelectTag[i].style.visibility = 'visible';
	}
	goSelectTag.length = 0;
}
function fSetSelected(aCell){
	var iOffset = 0;
	var iYear = parseInt(tbSelYear.value);
	var iMonth = parseInt(tbSelMonth.value);
	aCell.bgColor = gcBG;
	with (aCell.children['cellText']){
		var iDay = parseInt(innerText);
		if (color==gcGray)	iOffset = (Victor<10)?-1:1;
		iMonth += iOffset;
		if (iMonth<1){
			iYear--;
			iMonth = 12;
		}
		else if (iMonth>12){
			iYear++;
			iMonth = 1;
		}
	}
	fSetDate(iYear, iMonth, iDay);
}
function Point(iX, iY){
	this.x = iX;
	this.y = iY;
}
function fBuildCal(iYear, iMonth) {
	var aMonth = new Array();
	for (i = 1; i < 7; i++)
		aMonth[i] = new Array(i);
	var dCalDate = new Date(iYear, iMonth-1, 1);
	var iDayOfFirst = dCalDate.getDay();
	var iDaysInMonth = new Date(iYear,iMonth,0).getDate();
	var iOffsetLast = new Date(iYear,iMonth-1,0).getDate()-iDayOfFirst+1;
	var iDate = 1;
	var iNext = 1;
	for (d = 0; d < 7; d++)
	aMonth[1][d] = (d<iDayOfFirst)?-(iOffsetLast+d):iDate++;
	for (w = 2; w < 7; w++)
	for (d = 0; d < 7; d++)
		aMonth[w][d] = (iDate<=iDaysInMonth)?iDate++:-(iNext++);
	return aMonth;
}
function fDrawCal(iYear, iMonth, iCellHeight, iDateTextSize) {
	var WeekDay = new Array('日','一','二','三','四','五','六');
	var styleTD = " bgcolor='" + gcBG + "' bordercolor='" + gcBG + "' valign='middle' align='center' height='" + iCellHeight + "' style='font:bold " + iDateTextSize + " 宋体;color:#FFFFFF;";
	with (document){
		write("<tr>");
		for(i = 0; i < 7; i++)
			if (i == 0){
				write("<td " + styleTD + "background-color:#EB9797;' >" + WeekDay[i] + "</td>");
			}
			else if (i == 6){
				write("<td " + styleTD + "background-color:#EB9797;' >" + WeekDay[i] + "</td>");
			}
			else
				write("<td " + styleTD + "background-color:#C0C0C0;' >" + WeekDay[i] + "</td>");
		write("</tr>");
		for (w = 1; w < 7; w++){
			write("<tr>");
			for (d = 0; d < 7; d++){
				write("<td id=calCell " + styleTD + "cursor:hand;' onMouseOver='this.bgColor=gcToggle' onMouseOut='this.bgColor=gcBG' onclick='fSetSelected(this)'>");
				write("<font id=cellText Victor='Liming Weng'> </font>");
				write("</td>")
			}
			write("</tr>");
		}
	}
}
function fUpdateCal(iYear, iMonth){
	myMonth = fBuildCal(iYear, iMonth);
	var i = 0;
	for (w = 0; w < 6; w++)
		for (d = 0; d < 7; d++)
			with (cellText[(7*w)+d]){
				Victor = i++;
				if (myMonth[w+1][d]<0){
					color = gcGray;
					innerText = -myMonth[w+1][d];
				}
				else{
					color = ((d==0)||(d==6))?"red":"black";
					innerText = myMonth[w+1][d];
				}
			}
}
function fSetYearMon(iYear, iMon){
	tbSelMonth.options[iMon-1].selected = true;
	for (i = 0; i < tbSelYear.length; i++)
		if (tbSelYear.options[i].value == iYear)
			tbSelYear.options[i].selected = true;
	fUpdateCal(iYear, iMon);
}
function fPrevMonth(){
	var iMon = tbSelMonth.value;
	var iYear = tbSelYear.value;
	if (--iMon<1) {
		iMon = 12;
		iYear--;
	}
	fSetYearMon(iYear, iMon);
}
function fNextMonth(){
	var iMon = tbSelMonth.value;
	var iYear = tbSelYear.value;
	if (++iMon>12){
		iMon = 1;
		iYear++;
	}
	fSetYearMon(iYear, iMon);
}
function fToggleTags(){
	with (document.all.tags('select')){
	for (i = 0; i < length; i++)
		if ((item(i).Victor != 'Won')&&fTagInBound(item(i))){
			item(i).style.visibility = 'hidden';
			goSelectTag[goSelectTag.length] = item(i);
		}
	}
}
function fTagInBound(aTag){
	with (VicPopCal.style){
		var l = parseInt(left);
		var t = parseInt(top);
		var r = l+parseInt(width);
		var b = t+parseInt(height);
		var ptLT = fGetXY(aTag);
		return !((ptLT.x>r)||(ptLT.x+aTag.offsetWidth<l)||(ptLT.y>b)||(ptLT.y+aTag.offsetHeight<t));
	}
}
function fGetXY(aTag){
	var oTmp = aTag;
	var pt = new Point(0,0);
	do{
		pt.x += oTmp.offsetLeft;
		pt.y += oTmp.offsetTop;
		oTmp = oTmp.offsetParent;
	}while(oTmp.tagName != 'BODY');
	return pt;
}

function formatDate(obj) {
	if (obj.value.length != 0) {
		var thisDate = obj.value;
		obj.value = '';
		
		var year = thisDate.substring(0, thisDate.indexOf("-", 0));
		thisDate = thisDate.substring(thisDate.indexOf("-", 0) + 1);
		var month = thisDate.substring(0, thisDate.indexOf("-", 0));
		thisDate = thisDate.substring(thisDate.indexOf("-", 0) + 1);

		var day = thisDate;
		obj.value = year + '年' + month + '月' + day + '日';
	}	
}

function cleanup() {
	gdCtrl.value = '';
	fHideCalendar();
}

var gMonths = new Array('&nbsp;&nbsp;一月','&nbsp;&nbsp;二月','&nbsp;&nbsp;三月','&nbsp;&nbsp;四月','&nbsp;&nbsp;五月','&nbsp;&nbsp;六月','&nbsp;&nbsp;七月','&nbsp;&nbsp;八月','&nbsp;&nbsp;九月','&nbsp;&nbsp;十月','十一月','十二月');
with (document){
	write("<div id='VicPopCal' onclick='event.cancelBubble=true' style='position:absolute;visibility:hidden;border:1px ridge;width:10;z-index:100;'>");
	write("<table border='0' bgcolor='#2397C8'>");
	write("<tr><td valign='middle' align='center'><input class='Button' type='button' name='PrevMonth' value='<' style='width:16;' onClick='fPrevMonth()'>");
	write("&nbsp;<select name='tbSelYear' onChange='fUpdateCal(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");
	for(i = 1900; i < 2020; i++)
		write("<option value='" + i + "'>" + i + "年</option>");
	write("</select>");
	write("&nbsp;<select name='tbSelMonth' onChange='fUpdateCal(tbSelYear.value, tbSelMonth.value)' Victor='Won'>");
	for (i = 0; i < 12; i++)
		write("<option value='" + (i + 1) + "'>" + gMonths[i] + "</option>");
	write("</select>");
	write("&nbsp;<input class='Button' type='button' name='PrevMonth' value='>' style='width:16;' onclick='fNextMonth()'>");
	write("</td></tr>");
	write("<tr><td align='center'>");
	write("<div style='background-color:teal'><table width='100%' border='0'>");
	fDrawCal(giYear,giMonth,20,12);
	write("</table></div></td></tr>");
	write("<tr><td align='center'>");
	write("<b style='cursor:hand;font:bold 15 Default Sans Serif;color:#FFFFFF;' onclick='fSetDate(giYear,giMonth,giDay)' onMouseOver='this.style.color=gcToggle' onMouseOut='this.style.color=yqToggle'>今天：" + giYear + "年" + giMonth + "月" + giDay + "日</b><input type=button value=清除 onclick=cleanup() />");
	write("</td></tr></table></div>");
	write("<script event=onclick() for=document>fHideCalendar()</script>");
}

function rollDays(theDate, rollDay) {
	if (rollDay > 27) {
		alert("对不起！函数只支持27天以内的运算！");
		return;
	}
	//获取年、月、日
	var strDay = theDate;
	var year = strDay.substring(0, 4);
	var month = strDay.substring(strDay.indexOf("-") + 1, strDay.lastIndexOf("-"));
	var day = strDay.substring(strDay.lastIndexOf("-") + 1);

	var thisDate = year + "-" + month + "-" + day;

	//将年、月、日转为整型，以便运算
	year = parseInt(year);
	if (month.charAt(0) == '0')
		month = month.substring(1, 2)
	month = parseInt(month);
	if (day.charAt(0) == '0')
		day = day.substring(1, 2)
	day = parseInt(day);

	//构造全年每月最大天数数组
	var solarMonth = null;
	if (isLeapYear(year))
		solarMonth = new Array(0,31,29,31,30,31,30,31,31,30,31,30,31);
	else
		solarMonth = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);

	//alert("before modify: " + thisDate);

	//开始运算
	var maxDay = solarMonth[month];	//当月最大天数
	var stepDay = day + parseInt(rollDay);	//跳转天数差
	if (stepDay > maxDay) {			//跳入下个月
		if (month == 12) {			//跳入下一年
			year = year + 1;
			month = 1;
			day = stepDay - maxDay
			//thisDate = (year + 1) + "-" + 1 + "-" + (stepDay - maxDay);			
		}
		else {
			month = month + 1;
			day = stepDay - maxDay
			//thisDate = year + "-" + (month + 1) + "-" + (stepDay - maxDay);
		}
	}
	else if (stepDay < 1) {		//跳入上个月
		if (month == 1) {		//跳入上一年年
			year = year - 1;
			month = 12;
			day = 31 + stepDay;
			//thisDate = (year - 1) + "-" + 12 + "-" + (31 + stepDay);	//12月一定是31天
		}
		else {
			var theMaxDay = parseInt(solarMonth[month - 1]);
			month = month - 1;
			day = theMaxDay + stepDay;
			//thisDate = year + "-" + (month - 1) + "-" + (theMaxDay + stepDay);
		}
	}
	else {
		day = stepDay;		
	}

	//补零
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}

	thisDate = year + "-" + month + "-" + day;

	//alert("after modify: " + thisDate);
	return thisDate;
}

function isLeapYear(intYear) { 
   if (intYear % 100 == 0) { 
      if (intYear % 400 == 0) { return true; } 
   } 
   else { 
      if ((intYear % 4) == 0) { return true; } 
   } 
   return false; 
}