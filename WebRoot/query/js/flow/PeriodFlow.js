
   chartDataStr = "";
   timeStr = "";
   //初始化页面时间值
   function initTime(){
       var date = new Date();
       var startDateObj = document.getElementById("STARTTIME");
	   startDateObj.value = getSysdate(1440,true);
	   var endDateObj = document.getElementById("ENDTIME");
	   endDateObj.value = getSysdate(0,true);
   }
	/**
	* 函数功能:分时段统计流量
	*/
	 function statFlux(){
	   var startDateObj = document.getElementById("STARTTIME");
	   var startDate = startDateObj.value;
	   var endDateObj = document.getElementById("ENDTIME");
	   var endDate = endDateObj.value;
	   var radios = document.all.timemode;
		var timetype = "";//时间段标志 1：日 2：周 3：月 4：年
		for(var i = 0; i<radios.length ; i++){
			if(radios[i].checked == true){
				timetype = radios[i].value;
				break;
			}
		}
		
		if(flowstate.check(timetype)==false){
			return;
		}
	   
	   
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
	   //roadType值为“cross”为路口统计，“roadSeg”为路段统计
	   var roadType = "";
	   for(var i = 0; i < radioObj.length; i ++){
	      if(radioObj[i].checked == true){
	         roadType = radioObj[i].value;
	      }
	   }
	   var perRoadObj = document.getElementById("roadSelectId");
	   var url = 'tira.periodFlow.statPeriodFlow.d';
	   url = encodeURI(url);
	   var params = "roadType=" + roadType + "&perRoad=" + perRoadObj.value +"&timeType=" +timetype;
	   switch(timetype){
			case "1" : params +="&startDate=" + $("STARTTIME").value+"&endDate="+$("ENDTIME").value; timeStr = "(" + $("STARTTIME").value  +"---" + $("ENDTIME").value+")"; break;
			case "2" : params +="&startDate=" + $("STARTTIME").value+"&endDate="+$("ENDTIME").value; timeStr = "(" + $("STARTTIME").value  +"---" + $("ENDTIME").value+")";break;
			case "3" : params +="&monthYear=" + $("monthYear").value+"&monthMonth="+$("monthMonth").value; timeStr = "(" + $("monthYear").value  +"." + $("monthMonth").value+")"; break;
			case "4" : params +="&year=" + $("year").value; timeStr = "(" + $("year").value +")";break;
		}
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showVehResponse});
	 }
     //前24列为24时段流量，第25列为合计，第26列为路名
	 function showVehResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	   var tabObj = document.getElementById("tbl-container");
	   chartDataStr = "";//清空用于图表的数据载体
	   var lockedStyle = "";
	   //生成表头
	   	var str = "<table width=1000px class='scrollTable' width=100% cellSpacing=0 cellPadding=0>";
	   	for (var i = 0; i < rows.length; i+=1){
	   	  
	     	var results = rows[i].childNodes;
	     	
	     	   if(i == 0){
	     	       str += "<tr class='scrollColThead' valign='top' style='text-decoration: none;	background-color: #B4C1E2;line-height: 20px;'>";
	     	   }else{
	     	       str += "<tr>";
	     	   }
	     	   var boxStr = "";
	     	   for(var j =0; j < results.length; j++){
	     	      if(j == 0){
	     	          lockedStyle = "class='scrollRowThead' style=\"width:18%;text-align:center\"";
	     	          if(i != 0){
	     	              boxStr = "<input type='checkbox' name='boxname' />";
	     	              lockedStyle = "class='scrollRowThead' style=\"width:18%;\"";
	     	          }
	     	      }else if (j == results.length-1){
	     	          lockedStyle = "style=\"width:10%\"";  
	     	      }else{
	     	          lockedStyle = "style=\"width:3%\"";  
	     	      }
			      str += "<td    "+lockedStyle+" align='center' value="+results[j].text+">"+boxStr +results[j].text+"</td>";
			      boxStr = "";//清除checkbox痕迹
			   }	    
		    str += "</tr>";
		}
		
		str += "</table>";
	   //添加到结果面板上
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
 
/**
 * 函数功能:获取和组织页面数据，调用绘图函数绘图
 * 参数说明:title-标题;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);xDesc-横轴描述;yDesc-纵轴描述.
 * 调用实例:buildChart('重点车辆行为统计','line','卡口','频度')
 */
function periodFlowChart(){
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
        //组织一个路口段流量的数据，用于绘图
        for(var j = 1 ; j <tds.length; j++ ){
            if(j != tds.length-1 ){
                if(j == tds.length-2){
                    temStr += j + ","+tds[j].value +","+tds[0].value ;  
                }else{
                    temStr += j + ","+tds[j].value +","+tds[0].value +";"; 
                }
            }
        }
        if(i == boxs.length-1){
            DataForChartStr += temStr;
        }else{
            DataForChartStr += temStr + ";";
        }
    }
    //alert(DataForChartStr);
    var title = "交通流量统计图"+timeStr;
    var chartType = "line";
    var xDesc = "时段";
    var yDesc = "流量";
    doChart(title,chartType,xDesc,yDesc,DataForChartStr);
}

//根据单选值生成路段/路口下拉列表   
function doClick(radioValue){
   if(radioValue == "cross"){
      fillListBox("roadTdId","roadSelectId","212","SELECT CROSSINGID,CROSSINGNAME FROM T_ROAD_CROSSINGINFO","");
   }else{
      fillListBox("roadTdId","roadSelectId","212","SELECT ROADSEGID,ROADSEGNAME FROM T_ROAD_SEGINFO","");
   }
}

 function show(id){
    if($(id).style.display == "inline"){
    		return;
   	}
   	for(var i = 0;i<this.ids.length ;i ++){
   		if(id == this.ids[i]){
   			$(id).style.display = "inline";
   			continue;
   		}
   		$(this.ids[i]).style.display = "none";
   	}
 }

 /*
 *  事故信息统计
 */
Flowstat = Class.create();


Flowstat.prototype =
{	
	ids : ['hourAndDate','month','week'],
	
	
	/**
     * 构造函数.
     * @param {Object}
     */
    initialize: function()
    {
    },
    
    //初始化页面时间值
    initTime:function (){
       var date = new Date();
       var startDateObj = document.getElementById("STARTTIME");
	   startDateObj.value = getSysdate(1440,true);
	   var endDateObj = document.getElementById("ENDTIME");
	   endDateObj.value = getSysdate(0,true);
	   timeStr = "(" + startDateObj.value  +"---" + endDateObj.value+")";
    },
    /**
     * 
     */
    show : function(id){
    	if($(id).style.display == "inline"){
    		return;
    	}
    	for(var i = 0;i<this.ids.length ;i ++){
    		if(id == this.ids[i]){
    			$(id).style.display = "inline";
    			continue;
    		}
    		$(this.ids[i]).style.display = "none";
    	}
	},

   	check : function(timetype){
   		if(timetype == "1"||timetype == "2"){
			var start = $("STARTTIME").value;
			var end = $("ENDTIME").value;
			if(start == ""){
				alert("请选择开始日期");
				return false;
			}
			if(end == ""){
				alert("请选择结束日期");
				return false;
			}
			if(start>end){
				alert("开始日期必须小于结束日期");
				return false;
			}
			if(timetype == "2"){
				start = start.split('-');
				end = end.split('-');
				var date1 = new Date(parseInt(start[0]),parseInt(start[1])-1,parseInt(start[2]));
				var date2 = new Date(parseInt(end[0]),parseInt(end[1])-1,parseInt(end[2]));
				if((date2.getTime()-date1.getTime())>19*24*60*60*1000){
					alert("统计日期间隔不能大于20天");
					return false;
				}	
			}
		}
		if(timetype=="3"){
			var monthYear = $("monthYear").value;
			var monthMonth = $("monthMonth").value;
			if(monthYear == ""){
				alert("请填写年");
				return false;
			}
			
			if(monthMonth == ""){
				alert("请填写月");
				return false;
			}
			
			if(parseInt(monthMonth,10)>12||parseInt(monthMonth,10)==0){
				alert("月份只能输入1-12的数字");
				return false;
			}
		}
		if(timetype=="4"){
			var year = $("year").value;
			if(year == ""){
				alert("请填写年");
				return false;
			}
			
		}
		return true;
   	}
   
};

/*创建对象*/
var flowstate = new Flowstat();
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
	
	



 

 

