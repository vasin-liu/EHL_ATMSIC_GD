    tableTitle = "准驾车型";
	/**
	* 函数功能:统驾驶人员信息
	*/
	 function statDriver(){
	   var startDateObj = document.getElementById("STARTTIME");
	   var startDate = startDateObj.value;
	   var endDateObj = document.getElementById("ENDTIME");
	   var endDate = endDateObj.value;
	   
	   if(startDate > endDate){
	       alert("起止日期大于终止日期!");
	       return;
	   }
	   showMsg();
	   var XZXQObj = document.getElementById("XZXQ");
	   var radioObj = document.getElementsByName("rad");
	   var statType = "";
	   for(var i = 0; i < radioObj.length; i++){
	       if(radioObj[i].checked == true){
	          statType = radioObj[i].value;
	       }
	   }
	   
	   var url = 'tira.dirver.statDriver.d';
	   url = encodeURI(url);
	   var params = "XZXQ=" + XZXQObj.value +"&statType=" + statType +"&startDate=" +startDate +"&endDate="+endDate;
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showVehResponse});
	 }
 
	 function showVehResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	   chartDataStr = "";//清空图表数据载体
	   //生成表头
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='40%'  align='center' id='titleId'>"+tableTitle+"</td>";
	   	str += "<td width='30%' align='center'>数量</td>";
	   	str += "<td width='30%' align='center'>所占比例（%）</td>";
	   	str += "</tr>";
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
	     	var newArray = new Array();
	     	if(results.length == 2){
	     	   newArray.push(f_get_vehKindDesc(results[0].text));
	     	   newArray.push(results[1].text);
	     	   var persent = (results[1].text)/(f_get_vehSum(xmlDoc,1));
	     	   persent = (persent*100).toString();
	     	   persent = persent.substring(0,persent.indexOf(".")+3)
	     	   newArray.push(persent);
	     	   newArray[0] = results[1].text + "_" + persent +"%"; 
	     	}else if(results.length == 3){
	     	   newArray.push(results[1].text);
	     	    newArray.push(results[2].text);
	     	   var persent = (results[2].text)/(f_get_vehSum(xmlDoc,2));
	     	   persent = (persent*100).toString();
	     	   persent = persent.substring(0,persent.indexOf(".")+3)
	     	   newArray.push(persent);
	     	   newArray[0] = results[1].text + "_" + persent +"%"; 
	     	}else{
	     	   continue;
	     	}
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" >";
		    str += "<td width='40%'>" + newArray[0].split("_")[0]; + "</td>";
		    str += "<td width='30%'>" + newArray[1] + "</td>";	
		    str += "<td width='30%'>" + newArray[2] + "</td>";		    
		    str += "</tr>";
		    if(i == rows.length-1){
		       chartDataStr += newArray.toString();
		    }else{
		       chartDataStr +=  newArray.toString() +";";
		    }		
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
   
    
 function chart(){
    if(chartDataStr.length != 0 ){
       doChart('准驾车型','pie','准驾车型','数量',chartDataStr);
    }else{
       alert("没有可生成图表的数据！");
       return;
    }
 }   
  //根据统计方式单选按钮，改变数据头描述  
 function checkIt(radioValue){
    if(radioValue == "JL"){
       tableTitle = "驾龄（年）";
    }else if(radioValue == "NL"){
       tableTitle = "年龄（岁）";
    }else{
       tableTitle = "准驾车型"
    }
    document.getElementById("titleId").innerHTML = tableTitle;
 }