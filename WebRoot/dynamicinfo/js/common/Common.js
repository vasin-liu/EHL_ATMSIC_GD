
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
	var url = 'equipment.equipBuildChart.buildChart.d';
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
//   var chartInner = "<P ALIGN='CENTER'>";
//		chartInner	+="<img src='"+graphURL+"' width=365 height=200 border=0 usemap='"+fileName+"'>";
//		chartInner  += "</P>";
  var strlink = "../../../sm/ChartView.jsp?fileName="+fileName+"&graphURL="+graphURL;
//   var imgContainer = document.getElementById("chartTd");
//   imgContainer.innerHTML = chartInner;
   Popup.prototype.hideTips();
   window.showModalDialog(strlink,"","dialogWidth:790px;dialogHeight:500px");
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
   
   /** 
   *作者wuyl
   *函数功能:获取月的最后一天
   *参数:
   */
   function getMonthLastDay(iYear, iMonth) {
   	var dateArray = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
   	var days = dateArray[iMonth - 1];
   	if ((((iYear % 4 == 0) && (iYear % 100 != 0)) || (iYear % 400 == 0))
   			&& iMonth == 2) {
   		days = 29;
   	}
   	return days;
   }
   

   /** 
   *作者 zhaoy
   *函数功能:“yyyy-mm-dd”转换成“yyyymmdd”格式
   *参数:
   */
	function formatDateStr(target){
	    if(target == ""){
	        return target;
	    }
	    return target.substr(0,4) +"-"+ target.substr(4,2) +"-"+ target.substr(6,2);
	}
	
	/** 
   *作者 zhaoy
   *函数功能:去除尾部“0”
   *参数:
   */
	function removeEndZero(targetStr){
        while(targetStr.substr(targetStr.length-1,1) == "0"){
            targetStr = targetStr.substr(0,targetStr.length-1);
        }
        return targetStr;
	}
		//鼠标选中
	function whenDown (container,obj){
		var tabObj = document.getElementById(obj);
	      var allTr = tabObj.getElementsByTagName("tr");
	      for(var i = 0; i < allTr.length; i++){
	         allTr[i].bgColor = "#FFFFFF"; 
	      }
	      container.bgColor = "#EFEFFE";
	}
	// 鼠标移入#EFEFFE
	function mouseover(obj,color){
	   if(obj.bgColor != color){
		 obj.bgColor = "#5f9ea0"; 
	   }
	}
	
	 // 鼠标移出#E8F2FE
	function mouseout(obj,color){
	   if(obj.bgColor != color.toLowerCase()){
		  obj.bgColor = "#FFFFFF"; 
	   }
	}


