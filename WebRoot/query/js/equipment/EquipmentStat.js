
   chartDataStr = "";

	/**
	* 函数功能:设备信息统计
	*/
	 function statEquipment(){
	   
	   showMsg();
	   var statTypeObj = document.getElementById("statTypeId");
	   var SBZTObj = document.getElementById("SBZT");
	   var SBSYZTObj = document.getElementById("SBSYZT");
	   var url = 'tira.equipment.statEquipment.d';
	   url = encodeURI(url);
	   var params = "statType="+statTypeObj.value + "&SBZT="+SBZTObj.value + "&SBSYZT="+SBSYZTObj.value;
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showVehResponse});
	 }

	 function showVehResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	   var statType = xmlDoc.getElementsByTagName("statType");
	   chartDataStr = "";//清空用于图表的数据载体
	   //生成表头
	   
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	
	   	if(statType[0].text == "XZXQ"){
	   	   	str += "<td width='40%'  align='center'>所属辖区</td>";
	        str += "<td width='30%' align='center'>数量</td>";
	        str += "<td width='30%' align='center'>百分比</td>";
		   	str += "</tr>";
	   	}else if(statType[0].text == "SSXT"){
	   	   	str += "<td width='40%'  align='center'>所属系统</td>";
	        str += "<td width='30%' align='center'>数量</td>";
	        str += "<td width='30%' align='center'>百分比</td>";
		   	str += "</tr>";
	   	}else{
	   	    str += "<td width='40%'  align='center'>设备状态</td>";
	        str += "<td width='30%' align='center'>数量</td>";
	        str += "<td width='30%' align='center'>百分比</td>";
		   	str += "</tr>";
	   	}
	   	var sumNum = getSumFlow(rows);
	   	
	   	var persentStr = "";
	   	var otherSum = 0;
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
	     	persentStr = (parseFloat(parseInt(results[1].text)/sumNum.split(",")[0])*100).toString();
	     	if(persentStr.indexOf(".") > -1){
	     	    persentStr = persentStr.substring(0,persentStr.indexOf(".")+3);
	     	}
	     	//结果第一列为空是合计
	     	if(i==0 && results[0].text == ""){
	     	    results[0].text ="合计";
	     	    persentStr = "100";
	     	}
	     	if(i!=0 && results[0].text == ""){
	     	    continue;
	     	}
	     	
			str += "<tr class=\"rowstyle\"  >";
            str += "<td width='40%' >"+results[0].text+ "</td>";
		    str += "<td width='30%'>" + results[1].text + "</td>";	
		    str += "<td width='30%'>" + persentStr + "</td>";
		    str += "</tr>";
		    if(results[0].text !="合计"){
		       chartDataStr +=  results[0].text+"__"+persentStr+"%," +persentStr+","+results[0].text+";";
		    }
		    if(i == rows.length-1){
		        persentStr = (parseFloat(parseInt(sumNum.split(",")[1])/sumNum.split(",")[0])*100).toString();
		        if(persentStr.indexOf(".") > -1){
		     	    persentStr = persentStr.substring(0,persentStr.indexOf(".")+3);
		     	}
	     	    str += "<tr class=\"rowstyle\"  >";
	            str += "<td width='40%' >"+"其它"+ "</td>";
			    str += "<td width='30%'>" + sumNum.split(",")[1] + "</td>";	
			    str += "<td width='30%'>" + persentStr + "</td>";
			    str += "</tr>";  
			    chartDataStr +=  "其它__"+persentStr+"%," +persentStr+",其它;"; 
	     	}
		    		
		}	
		
		str += "</table>";
	   //添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
    //获取总和和类型为“其它”的数量
    function getSumFlow(rows){
       if(rows.length == 0){
           return;
       }
       var otherSum = 0;
       for(var i = 0; i < rows.length; i++){
           var results = rows[i].childNodes;
           if(i != 0 && results[0].text == ""){
               otherSum += parseInt(results[1].text,10);
           }
       }
       return rows[0].childNodes[1].text +"," +otherSum;
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
    
 function chart(){
    if(chartDataStr.length != 0 ){
       doChart('设备信息统计','pie','','数量',chartDataStr);
    }else{
       alert("没有可生成图表的数据！");
       return;
    }
 }   
 


/** 
 * desc:初始化统计方式下拉列表.
 * date:2009-04-21
 */
function changeTableTitle(valueStr){
    if(valueStr =="SSXT"){
        document.getElementById("SBZT").disabled = false;
        document.getElementById("SBSYZT").disabled = false;
       	var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
        str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='40%'  align='center'>所属系统</td>";
        str += "<td width='30%' align='center'>数量</td>";
        str += "<td width='30%' align='center'>百分比</td>";
	   	str += "</tr>";
	   	str += "</table>";
    }
    if(valueStr ==  "XZXQ"){
       document.getElementById("SBZT").disabled = false;
       document.getElementById("SBSYZT").disabled = false;
       var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='40%'  align='center'>所属辖区</td>";
        str += "<td width='30%' align='center'>数量</td>";
        str += "<td width='30%' align='center'>百分比</td>";
	   	str += "</tr>";
	    str += "</table>";
    }
    if(valueStr ==  "SBSYZT"){
       document.getElementById("SBSYZT").disabled = true;
       document.getElementById("SBZT").disabled = false;
       var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='40%'  align='center'>设备使用状态</td>";
        str += "<td width='30%' align='center'>数量</td>";
        str += "<td width='30%' align='center'>百分比</td>";
	   	str += "</tr>";
	    str += "</table>";
    }
    if(valueStr ==  "SBZT"){
       document.getElementById("SBZT").disabled = true;
       document.getElementById("SBSYZT").disabled = false;
       var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='40%'  align='center'>设备状态</td>";
        str += "<td width='30%' align='center'>数量</td>";
        str += "<td width='30%' align='center'>百分比</td>";
	   	str += "</tr>";
	    str += "</table>";
    }
   
	   
	   //添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
}