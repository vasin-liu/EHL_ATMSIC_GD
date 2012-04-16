/**
 * 公共变量声明；
*/
	//日期方式
	var _timeType="2";
	//统计方式
	var _statMode="1";

	////////////////////////////////////////////////////////////////////////////////////////////

/**
 * 函数功能:日期形式被选择时的onclick事件
 **/
function onclickTimeType(){
	var radios = document.all.timeType;
	for(var i = 0; i<radios.length ; i++){
		if(radios[i].checked){
			_timeType = radios[i].value;
			break;
		}
	}
	switch(_timeType){
	// 选择年
	case "1" : $('descTD').innerHTML="开始年份：";
			   createYearSelect('startTimeTd','startTime','66','','1');
			   $('signTD').innerHTML="结束年份：";
			   createYearSelect('endTimeTd','endTime','67','','1');
			   break;
	// 选择月
	case "2" : $('descTD').innerHTML="开始月份：";
			   createYearSelectOneMonth('startTimeTd','startYear,startMonth','60','','0');
			   $('signTD').innerHTML="结束月份：";
			   createMonthSelect('endTimeTd','endTime','40');
			   break;
	// 选择日
	case "3" : timeTypeDay();
			   break;
	// 选择小时
	case "4" : timeTypeHour();
			   break;
	}
}

/**
 * 函数功能:统计方式被选择时的onclick事件
 **/
function onclickStatMode(){
	var radios = document.all.statMode;
	for(var i = 0; i<radios.length ; i++){
		if(radios[i].checked == true){
			_statMode = radios[i].value;
			break;
		}
	}
}

 /** 
*作者wuyl
*函数功能:生成年份下拉列表,并放入容器
*参数:containerId-容器ID thisId-生成的Select下拉列表Id(和月份下拉列表Id逗号相隔)  widthStr-列表宽度 onChangeFun-列表事件 ignoreMonth-是否忽略月份下拉列表
*/
   function createYearSelectOneMonth(containerId,thisId,widthStr,onChangeFun,ignoreMonth){
	  var container = document.getElementById(containerId);
	  var yearSelectStr = "<select id="+thisId.split(",")[0]+" style='width:"+widthStr+"' onchange='"+onChangeFun+"'>";
	  var temYear = "";
	  var monthSelectId = "";
	  var currentYear = getSysdate(0,true).split("-")[0];
	  var len = parseInt(currentYear)-1980+1;
	  for(var i = 0; i < len; i++){
	       temYear = 1980+parseInt(getFull(i));
	       if(temYear == currentYear){
	           yearSelectStr += "<option value="+temYear+" selected> "+temYear+" </option>";
	       }else{
	           yearSelectStr += "<option value="+temYear+"> "+temYear+"</option>";
	       }
	  }
	  yearSelectStr += "</select>&nbsp;年";
	  if(ignoreMonth == false){
	      if(thisId.split(",").length > 1){
	          monthSelectId =  thisId.split(",")[1]; 
	      }
	      yearSelectStr += "&nbsp;"+ getOneMonthSelect(monthSelectId,"40","1");
	  }
	  container.innerHTML = yearSelectStr;
   }
   
   /** 
   *作者wuyl
   *函数功能:获取12个月的下拉列表,并选择一月
   *参数:
   */
    function getOneMonthSelect(monthSelectId,widthStr,monthStr){
 	  var monthSelectStr = "<select id="+monthSelectId+" style='width:"+widthStr+"'>";
 	  var temMonth = "";
 	  var currentMonth = getSysdate(0,true).split("-")[1];
 	  for(var i = 0; i < 12; i++){
 	       temMonth = getFull(i+1);
 	       if(temMonth == monthStr){
 	           monthSelectStr += "<option value="+temMonth+" selected> "+temMonth+" </option>";
 	       }else{
 	           monthSelectStr += "<option value="+temMonth+"> "+temMonth+"</option>";
 	       }
 	  }
 	  monthSelectStr += "</select>&nbsp;月";
 	  return monthSelectStr;
    }
 /** 
*作者zhaoy
*函数功能:生成年份下拉列表,并放入容器
*参数:containerId-容器ID thisId-生成的Select下拉列表Id(和月份下拉列表Id逗号相隔)  widthStr-列表宽度 onChangeFun-列表事件 ignoreMonth-是否忽略月份下拉列表
*/
   function createYearSelect(containerId,thisId,widthStr,onChangeFun,ignoreMonth){
	  var container = document.getElementById(containerId);
	  var yearSelectStr = "<select id="+thisId.split(",")[0]+" style='width:"+widthStr+"' onchange='"+onChangeFun+"'>";
	  var temYear = "";
	  var monthSelectId = "";
	  var currentYear = getSysdate(0,true).split("-")[0];
	  var len = parseInt(currentYear)-1980+1;
	  for(var i = 0; i < len; i++){
	       temYear = 1980+parseInt(getFull(i));
	       if(temYear == currentYear){
	           yearSelectStr += "<option value="+temYear+" selected> "+temYear+" </option>";
	       }else{
	           yearSelectStr += "<option value="+temYear+"> "+temYear+"</option>";
	       }
	  }
	  yearSelectStr += "</select>&nbsp;年";
	  if(ignoreMonth == false){
	      if(thisId.split(",").length > 1){
	          monthSelectId =  thisId.split(",")[1]; 
	      }
	      yearSelectStr += "&nbsp;&nbsp;&nbsp;"+ getMonthSelect(monthSelectId,"40");
	  }
	  container.innerHTML = yearSelectStr;
   }
   

/** 
*函数功能:获取选择月份的下拉列表
*参数:containerId-容器ID monthSelectId-下拉列表Id  widthStr-列表宽度
*/
 function createMonthSelect(containerId,monthSelectId,widthStr){
	  var container = document.getElementById(containerId);
	  var monthSelectStr = "<select id="+monthSelectId+" style='width:"+widthStr+"'>";
	  var temMonth = "";
	  // 获取当前月
	  var currentMonth = getSysdate(0,true).split("-")[1];
	  for(var i = 1; i <= 12; i++){
	       temMonth = getFull(i);
	       if(temMonth == currentMonth){
	           monthSelectStr += "<option value="+temMonth+" selected> "+temMonth+" </option>";
	       }else{
	           monthSelectStr += "<option value="+temMonth+"> "+temMonth+"</option>";
	       }
	  }
	  monthSelectStr += "</select>&nbsp;月";
	  container.innerHTML = monthSelectStr;
 }

/**
 * 函数功能:日期形式＝日
 */
function timeTypeDay(){
	$('descTD').innerHTML="开始日期：";
	$('signTD').innerHTML="结束日期：";
	var container = document.getElementById("startTimeTd");
	container.innerHTML  = "<input name=\"text\" style=\"width :81px;height :20px\"  type=\"text\" class=\"text\" id=\"startTime\" onClick=\"SelectDate(this,0);\" />";
    var date = new Date();
    var startDateObj = document.getElementById("startTime");
    startDateObj.value = getSysdate(0,true).split("-")[0]+"-"+ getSysdate(0,true).split("-")[1]+"-"+"01";
	var container = document.getElementById("endTimeTd");
	container.innerHTML  = "<input name=\"text\" style=\"width :81px;height :20px\"  type=\"text\" class=\"text\" id=\"endTime\" onClick=\"SelectDate(this,0);\" />";
    var date = new Date();
    var endDateObj = document.getElementById("endTime");
    endDateObj.value = getSysdate(0,true);
}
/**
 * 函数功能:日期形式＝小时
 */
function timeTypeHour(){
	$('descTD').innerHTML="选择日期：";
	var now= new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	$('startTimeTd').innerHTML  = "<input type=\"text\" style=\"width :81px;height :20px\"  id=\"startTime\" size=\"12\" onclick=\"SelectDate(this,0);\"   value=\""+year+"-"+month+"-"+day+"\" readonly>";
	$('endTimeTd').innerHTML  = "";
	$('signTD').innerHTML="";
	
}

/**
 * 函数功能:日期形式＝日时，检测日期是否在同一个月。
 * 返回警告信息："请将起止日期选择在同一个月"
 */
function checkDayInOneMonth(){
	var container = document.getElementById("startTime");
    var endDateObj = document.getElementById("endTime");
    var startTime =container.value.split("-")[0] + container.value.split("-")[1];
    var endTime =endDateObj.value.split("-")[0] + endDateObj.value.split("-")[1];
    if(endTime != startTime){
    	alert("请将起止日期选择在同一个月");
    	return  true;
    }
    return false;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////