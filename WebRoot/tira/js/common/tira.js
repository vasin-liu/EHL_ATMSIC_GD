/** 
*作者zhaoy
*函数功能:生成年份下拉列表,并放入容器
*参数:containerId-容器ID thisId-生成的Select下拉列表Id(和月份下拉列表Id逗号相隔)  widthStr-列表宽度 onChangeFun-列表事件 ignoreMonth-是否忽略月份下拉列表
*/
   function createYearSelect(containerId,thisId,widthStr,onChangeFun,ignoreMonth,offset){
	  var container = document.getElementById(containerId);
	  var yearOffset = 20;
	  if(typeof offset != "undefined"){
	      yearOffset = offset;
	  }
	  var yearSelectStr = "<select id="+thisId.split(",")[0]+" style='width:"+widthStr+"' onchange='"+onChangeFun+"'>";
	  var temYear = "";
	  var monthSelectId = "";
	  var currentYear = getSysdate(0,true).split("-")[0];
	  var len = yearOffset+1;
	  for(var i = 0; i < len; i++){
	       temYear = currentYear-yearOffset+parseInt(i,10);
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
   *作者:luoqiang
   *描述:生成日期选择框
   */
   function createDateSelect(containerId){
   		var container = document.getElementById(containerId);
   		var today = new Date();       
       	var day = today.getDate(); 
       	if(day.toString().length<2)  
       	  day='0'+day;    
       	var month = today.getMonth() + 1;
       	if(month.toString().length<2)  
       	  month='0'+month;
       	var year = today.getYear();       
       	var date = year + "-" + month + "-" + day;    		
		var inner = "<input id='dateStart' style=\"width:70px;\" value='"+date+"' onClick=\"SelectDate(this,0);\" readOnly/>";
		inner += "<span>--</span>";
		inner += "<span><input id='dateEnds' style=\"width:70px;\" value='"+date+"' onClick=\"SelectDate(this,0);\" readOnly/></span>";
		container.innerHTML = inner;
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