/**
 * 函数功能:显示统计图表,发送Ajax请求数据.
 * 参数说明:title-标题;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);xDesc-横轴描述;yDesc-纵轴描述;chartArray-统计图表数据.
 * 调用实例:buildChart('重点车辆行为统计','line','卡口','频度')
 */
function doChart(title,chartType,xDesc,yDesc,chartDataStr){
    var title = title;
    var chartType = chartType;
    var xDesc =xDesc;
    var yDesc = yDesc;
    
    if(chartDataStr.length == 0){
       alert("没有数据！");
       return;
    }
	var url = 'tira.tiraBuildChart.buildChart.d';
	url = encodeURI(url);
	var params = "title=" + title + "&chartType="+chartType+"&xDesc=" + xDesc + "&yDesc=" + yDesc +"&chartDataStr="+chartDataStr;
	params = encodeURI(params);
    new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showImage});
}

/**
 * 函数功能：Ajax回调,解析请求返回的数据并展示出来.
 */
function showImage(xmlHttp){
   var strText = xmlHttp.responseText;
   if(strText == "null"){
      Popup.prototype.hideTips();
      alert("没有可生成图表的数据！");
      
      return;
   }
   var splitLoc = strText.indexOf("|");
   var fileName = strText.substring(0,splitLoc);
   var graphURL = strText.substring(splitLoc+1);
   var strlink = "../../../sm/ChartView.jsp?fileName="+fileName+"&graphURL="+graphURL;
   Popup.prototype.hideTips();
   window.showModalDialog(strlink,"","dialogWidth:790px;dialogHeight:500px");
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
	      yearSelectStr += "&nbsp;&nbsp;&nbsp;"+ getMonthSelect(monthSelectId,widthStr);
	  }
	  container.innerHTML = yearSelectStr;
   }
   
  /** 
  *作者zhaoy
  *函数功能:获取12个月的下拉列表
  *参数:
  */
   function getMonthSelect(monthSelectId,widthStr){
	  var monthSelectStr = "<select id="+monthSelectId+" style='width:"+widthStr+"'>";
	  var temMonth = "";
	  var currentMonth = getSysdate(0,true).split("-")[1];
	  for(var i = 0; i < 12; i++){
	       temMonth = getFull(i+1);
	       if(temMonth == currentMonth){
	           monthSelectStr += "<option value="+temMonth+" selected> "+temMonth+" </option>";
	       }else{
	           monthSelectStr += "<option value="+temMonth+"> "+temMonth+"</option>";
	       }
	  }
	  monthSelectStr += "</select>&nbsp;月";
	  return monthSelectStr;
   }
   

