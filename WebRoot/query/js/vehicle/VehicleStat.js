   
   //车辆大类对照表
   kindSelectValue = "'',全部;K,客车;H,货车;Q,牵引车;Z,专项作业车;D,电车;M,摩托车;N,三轮汽车;T,拖拉机;J,轮式机械;G,全挂车;B,半挂车;X,其它";
   chartDataStr = "";
	

	/**
	* 函数功能:统计机动车信息
	*/
	 function statVeh(){
	   
	   showMsg();
	   var stateObj =  document.getElementById("stateId");
	   var vehKindObj = document.getElementById("vehKindId");
	   var statTypeObj = document.getElementById("statTypeId");
	   var XZQHObj = document.getElementById("XZXQ");
	   var url = 'tira.vehicle.statVeh.d';
	   url = encodeURI(url);
	   var params = "vehKind=" + vehKindObj.value +"&statType=" + statTypeObj.value +"&XZQH="+XZQHObj.value;
	   params += "&vehState="+stateObj.value;
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showVehResponse});
	 }
 
	 function showVehResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	   chartDataStr = "";//清空用于图表的数据载体
	   //生成表头
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='40%'  align='center'>车辆分类</td>";
	   	str += "<td width='30%' align='center'>数量</td>";
	   	str += "<td width='30%' align='center'>所占比例（%）</td>";
	   	str += "</tr>";
	   	var sumVeh = getSumVeh(rows);
	   	var total = sumVeh.split("_")[0];
	   	var otherSum = sumVeh.split("_")[1];
	   	for (var i = 1; i < rows.length; i++){
	    	//组织每行显示结果：组名，数量，百分比
	     	var results = rows[i].childNodes;
	     	var newArray = new Array();//用于存放处理后大类数据 results[1].text
//	     	if(results[0].text == "" && i == 0){
//	     	    newArray.push("合计");  
//	     	    newArray.push(results[1].text);  
//	     	    newArray.push("100");        
//	     	}
     	    if(results[0].text != ""){
     	        newArray.push(f_get_vehKindDesc(results[0].text));
	     	    newArray.push(results[1].text);
	     	    var persent = (results[1].text)/total;
	     	    persent = (persent*100).toString();
	     	    if(persent.indexOf(".") > -1){
	     	        persent = persent.substring(0,persent.indexOf(".")+3)
	     	    }
	     	    newArray.push(persent);
	     	    newArray[0] = newArray[0] +"_" +persent +"%";
     	        str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" >";
			    str += "<td width='40%'>" + newArray[0].split("_")[0] + "</td>";
			    str += "<td width='30%'>" + newArray[1] + "</td>";	
			    str += "<td width='30%'>" + newArray[2] + "</td>";		    
			    str += "</tr>";
     	    }
     	    var otherArray = new Array();
     	    if(i == rows.length-1){
//     	        otherArray.push("其它");
//	     	    otherArray.push(otherSum);
//	     	    var persent = (otherSum)/total;
//	     	    persent = (persent*100).toString();
//	     	    if(persent.indexOf(".") > -1){
//	     	        persent = persent.substring(0,persent.indexOf(".")+3)
//	     	    }
//	     	    otherArray.push(persent);
//	     	    otherArray[0] = newArray[0] +"_" +persent +"%";
     	    	var resultsAll = rows[0].childNodes;
     	        str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" >";
			    str += "<td width='40%'>合计</td>";
			    str += "<td width='30%'>" + resultsAll[1].text + "</td>";	
			    str += "<td width='30%'>100%</td>";		    
			    str += "</tr>";
//			    chartDataStr +=  otherArray.toString() +";";
     	    }
		    if(results[0].text != ""){
		       chartDataStr +=  newArray.toString() +";";
		    }	
		    if(otherArray.length >0){
		        chartDataStr +=  otherArray.toString() +";";
		    }	
		}
		str += "</table>";
	   //添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
 //获取机动车总和
    function getSumVeh(rows){
       if(rows.length == 0){
           return 0 +"_"+ 0;
       }
       var temSum = 0;
       var otherSum = 0;
       for(var i = 0; i < rows.length; i++){
           var results = rows[i].childNodes;
           if(i!=0 && results[0].text == ""){
               otherSum += parseInt(results[1].text,10);
           }
           if(i == 0){
               temSum = results[1].text;
           }
       }
       return temSum + "_" + otherSum;
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
      createSelect('vehKindId','kindTdId',kindSelectValue);
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
  

   /*功能：联动控制车辆类型下拉列表
   * 参数：
   *
   */
   function selectChange(selectId){
       var selectObj = document.getElementById(selectId);
       var cllxObj = document.getElementById("vehKindId");
       if(selectObj.value == "HPZL"){
            cllxObj.disabled = true;
       }else{
           cllxObj.disabled = false;;
       }
    }
    
 function chart(){
    if(chartDataStr.length != 0 ){
       doChart('机动车统计','pie','车辆类别','数量',chartDataStr);
    }else{
       alert("没有可生成图表的数据！");
       return;
    }
 }   
    
/**
 * 函数功能：创建下拉列表，下拉列表子项的值由使用者传递
 * 参数说明：selectId-准备生成的下拉列表的id;containerId-下拉列表父容器ID;selectValue
 * 调用示例： var selectValue = "K,客车;H,货车;Q,牵引车;Z,专项作业车;D,电车;M,摩托车;N,三轮汽车;T,拖拉机;J,轮式机械;G,全挂车;B,半挂车;X,其它";
 *          createSelect('','',selectValue);
 */
 function createSelect(selectId,containerId,selectValue,width){
     
     var valueArray = selectValue.split(";");
     var inner = "<select id='"+selectId+"' class='text' style='width:"+width+"' >";
     for(var i = 0; i < valueArray.length; i++){
         var perOptionArray = valueArray[i].split(",");
         inner += "<option value='"+perOptionArray[0]+"' />"+perOptionArray[1];
     }
     inner += "</select>";
	 document.getElementById(containerId).innerHTML = inner;
} 