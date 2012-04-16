   timeStr = "";
   tableTitle = "";
   //初始化页面时间值
   function initTime(){
       var date = new Date();
       var startDateObj = document.getElementById("STARTTIME");
	   startDateObj.value = getSysdate(1440,false);
	   var endDateObj = document.getElementById("ENDTIME");
	   endDateObj.value = getSysdate(0,false);
   }
	/**
	* 函数功能:统计流量信息
	*/
	 function statFlux(){
	   var startDateObj = document.getElementById("STARTTIME");
	   var startDate = startDateObj.value;
	   var endDateObj = document.getElementById("ENDTIME");
	   var endDate = endDateObj.value;
	   
	   
	   if(startDate.Trim().length <1 ||endDate.Trim().length<1){
	       alert("请输入统计时间!");
	       return;
	   }
	   if(startDate > endDate){
	       alert("起止日期大于终止日期!");
	       return;
	   }
	   showMsg();
	   timeStr = "("+startDateObj.value.split(" ")[0]+"---"+endDateObj.value.split(" ")[0]+")";
	   var radioObj = document.getElementsByName("returnTag");
	   var perRoadObj = document.getElementById("roadSelectId");
	   //roadType值为“cross”为路口统计，“roadSeg”为路段统计
	   var roadType = "";
	   for(var i = 0; i < radioObj.length; i ++){
	      if(radioObj[i].checked == true){
	         roadType = radioObj[i].value;
	      }
	   }
	   //动态生成表格第一行说明，存入全局变量
	   if(roadType == "cross"){
	       tableTitle = "路口名称";    
	   }else{
	       tableTitle = "路段名称";   
	   }
	   if(perRoadObj.value != ""){
	       tableTitle =   perRoadObj[perRoadObj.selectedIndex].text;
	   }
	   
	   var url = 'tira.trafficflux.statFlux.d';
	   url = encodeURI(url);
	   var params = "roadType=" + roadType +"&perRoad=" + perRoadObj.value ;
	   params += "&startDate=" +startDate +"&endDate="+endDate ;
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showVehResponse});
	 }
// res-第一列为路口/路段/车道名称，第二列为流量，第三列为时间占有率，第四列为速度，第五列为记录总数，第六列为饱和度
	 function showVehResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	   var tabObj = document.getElementById("tdData");
	   //生成表头
	   	var str = "";
	    // str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	    str += "<table id='tabVeh' class='scrollTable' width=100% cellSpacing=0 cellPadding=0>";
	    str += "<tr class='scrollColThead'  style='text-decoration: none;	background-color: #B4C1E2;line-height: 20px;'>\
				 <td width='40%'  align='center'>"+tableTitle+"</td>\
                      <td width='20%' align='center'>流量</td>\
                      <td width='20%' align='center'>占有率（%）</td>\
                      <td width='20%' align='center'>平均速度</td>\
				 </tr>";
		
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" >";
			for(var j = 0; j < results.length; j++){
			    if(j == 0){
			         str += "<td width='40%' value='"+ results[j].text+"'><input type='checkbox' name='boxname' />" + results[j].text + "</td>";
			    }else{
			         str += "<td width='15%' value='"+ results[j].text+"'>" + results[j].text + "</td>";	
			    }
			}			    
		    str += "</tr>";
		}	
		
		str += "</table>";
	    //添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
 
   
  //控制选中状态

   function checkState(checkId){
      //控制选中状态

      var checkObj = document.getElementById(checkId);
	  var checkObjList = document.getElementsByName("checkObj");
	  for(var i=0; i < checkObjList.length; i++){
	      checkObjList[i].checked = false;
	  }
	  checkObj.checked = true;
   }
	
	// 鼠标移入
	function mouseover(obj,color){ 
	   if(obj.bgColor != color){
		 obj.bgColor = "#117FC9"; 
	   }
	}
	
	 // 鼠标移出
	function mouseout(obj,color){
	   if(obj.bgColor != color.toLowerCase()){
		  obj.bgColor = "#FFFFFF"; 
	   }
	}
  //生成车辆大类数据下拉列表
  function initKindSelect(){
      createSelect('vehKindId','kindTdId',kindSelectValue,'typeList');
  }
  //车辆大类根据代码获取描述
  function f_get_vehKindDesc(code){
    var kindArray = kindSelectValue.split(";");
     for(var k = 0; k < kindArray.length; k++){
        var perKindArray = kindArray[k].split(",");
        if(code == perKindArray[0].substr(0,1)){
           return perKindArray[1];
        }
     }
     return code;
  }
  
  /*功能：各行数量总和
   *参数：xmlDoc-返回数据Dom对象,colNum-累加列号(从0开始)
   *
   */
  function f_get_vehSum(xmlDoc,colNum){
    var rows = xmlDoc.getElementsByTagName("row");
    var sum = 0;
    for(var i = 0; i < rows.length; i ++){
       results = rows[i].childNodes;
       if(results.length <= colNum){
          continue;
       }
       sum += parseInt(results[colNum].text,10);
    }
    return sum;
  }
   
 //获取和组织页面数据进行绘图   
 function chart(){
    var allBoxs = document.getElementsByName("boxname");
    var boxs = new Array();
    for(var  k = 0; k <allBoxs.length; k++){
        if(allBoxs[k].checked == true){
            boxs.push(allBoxs[k]);
        }
    }
    if(boxs.length == 0){
       alert("请选择要生成图表的数据！");
       return;
    }
    var tds = "";
    var trObj = "";
    var DataForChartStr = "";
    var temStr = "";
    for(var i = 0 ; i < boxs.length; i ++){
        var trObj = boxs[i].parentNode.parentNode;
        tds = trObj.childNodes;
        temStr ="";//清空上一个路口段流量载体
        //组织一个路口段流量的数据，以x,y,z轴数据顺序
        temStr += tds[0].value +","+ tds[1].value +"," +tds[0].value;
        if(i == boxs.length-1){
            DataForChartStr += temStr;
        }else{
            DataForChartStr += temStr + ";";
        }
    }
    if(DataForChartStr.length != 0 ){
       doChart('交通流量统计'+timeStr,'bar','','流量',DataForChartStr);
    }else{
       alert("没有可生成图表的数据！");
       return;
    }
 }   
 //根据单选值生成路段/路口下拉列表   
function doClick(radioValue){
   if(radioValue == "cross"){
      fillListBox("roadTdId","roadSelectId","246","SELECT CROSSINGID,CROSSINGNAME FROM T_ROAD_CROSSINGINFO","");
   }else{
      fillListBox("roadTdId","roadSelectId","246","SELECT ROADSEGID,ROADSEGNAME FROM T_ROAD_SEGINFO","");
   }
   var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
   	str += "<td width='40%'  align='center'>路段/路口</td>";
    str += "<td width='15%' align='center'>流量</td>";
    str += "<td width='15%' align='center'>占有率（%）</td>";
    str += " <td width='15%' align='center'>平均速度</td>";
    str += "<td width='15%' align='center'>饱和度（%）</td>";
   	str += "</tr>";
   	str += "</table>";
   //添加到结果面板上
	var tabObj = document.getElementById("tdData");
	tabObj.innerHTML = str;
}

 

//根据下拉列表值不同，动态设置数据表格第一行
function doOnChange(){
	var roadsObj = document.getElementById("roadSelectId");
	if (roadsObj.value != "") {
		 var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	 str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	 str += "<td width='40%'  align='center'>车道</td>";
         str += "<td width='15%' align='center'>流量</td>";
         str += "<td width='15%' align='center'>占有率（%）</td>";
         str += " <td width='15%' align='center'>平均速度</td>";
         str += "<td width='15%' align='center'>饱和度（%）</td>";
	   	 str += "</tr>";
	   	 str += "</table>";
	   //添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
	}else{
	     var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	 str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	 str += "<td width='40%'  align='center'>路段/路口</td>";
         str += "<td width='15%' align='center'>流量</td>";
         str += "<td width='15%' align='center'>占有率（%）</td>";
         str += " <td width='15%' align='center'>平均速度</td>";
         str += "<td width='15%' align='center'>饱和度（%）</td>";
	   	 str += "</tr>";
	   	 str += "</table>";
	   //添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
	}
}