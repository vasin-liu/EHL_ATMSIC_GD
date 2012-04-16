
/*
 *  事故信息统计
 */
Viostat = Class.create();


Viostat.prototype =
{	
    timeStr: "",
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
	/**
	 * 
	 */
	doClick : function(){
		var radios = document.all.timemode;
		var timetype = "";//时间段标志 1：日 2：周 3：月 4：年
		for(var i = 0; i<radios.length ; i++){
			if(radios[i].checked == true){
				timetype = radios[i].value;
				break;
			}
		}
		if(this.check(timetype)==false){
			return;
		}
		radios = document.all.statmode;
		var stattype = "";//统计方式标志 1：事故地点 2：车辆类型 
		for(var i = 0; i<radios.length ; i++){
			if(radios[i].checked == true){
				stattype = radios[i].value;
				break;
			}
		}
		//定义并赋值公用变量（各时段类型划分的结果组数-colsNum）
		switch(parseInt(timetype)){
		    case 1:colsNum = "24";break;
		    case 2:colsNum = "5";break;
		    case 3:colsNum = "31";break;
		    case 3:colsNum = "12 ";break;
		    default:colsNum = "";break;
		}
		//定义并赋值公用变量（statTypeDesc-统计方式）
		switch(parseInt(stattype)){
		    case 1:statTypeDesc = "行政辖区";break;
		    case 2:statTypeDesc = "违法地点";break;
		    case 3:statTypeDesc = "违法行为";break;
		    default:statTypeDesc = "";break;
		}
		var url = "tira.VioStatAction.getData.d";
		url = url + "?timetype="+timetype+"&stattype="+stattype;
		switch(timetype){
			case "1" : url = url + "&starttime=" + $("STARTTIME").value+"&endtime="+$("ENDTIME").value; timeStr = "(" + $("STARTTIME").value  +"---" + $("ENDTIME").value+")";break;
			case "2" : url = url + "&starttime=" + $("STARTTIME").value+"&endtime="+$("ENDTIME").value; timeStr = "(" + $("STARTTIME").value  +"---" + $("ENDTIME").value+")";break;
			case "3" : url = url + "&monthYear=" + $("monthYear").value+"&monthMonth="+$("monthMonth").value; timeStr = "(" + $("monthYear").value  +"." + $("monthMonth").value+")";break;
			case "4" : url = url + "&year=" + $("year").value; timeStr = "(" + $("year").value +")"; break;
		}
		var arrBody = document.getElementsByTagName("body");
		Popup.prototype.showTips(arrBody[0]); 
		new Ajax.Request(url, {method: 'post', parameters: "", onComplete:this.showtable});
	},
	showtable : function(xmlHttp){
   		var xmlDoc = xmlHttp.responseXML;
   		xmlDoc = xmlDoc.documentElement
   		var rows = xmlDoc.childNodes;
   		var zongji = 0;
   		//var perColPersent = parseInt(85/(colsNum+1),10);//每列所占百分比,首列占15%;
   		var len;
   		var strTab = "<table id=\"tabVeh\" class=\"scrollTable\" width=1000 cellSpacing=0 cellPadding=0>";
   		for(var i =0;i<rows.length;i++){
   			if(i==0){
   				strTab = strTab + "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
   			}else{
   				strTab = strTab + "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" >";
   			}
   			var cols = rows[i].childNodes;
   			var alignStyle = "";
   			for(var k =0;k<cols.length;k++){
   			
   			    if(i ==0){//第一行居中
   			        alignStyle = "style='text-align:center' ";
   			    }else{
   			        alignStyle = "style='text-align:left' " ;
   			    }
   			    if(k == 0){
   			        alignStyle +=" class='scrollRowThead'";
   			    }
   				strTab = strTab + "<td "+alignStyle+" value='"+cols[k].text+"'>";
   				if(i == rows.length-1 && k== cols.length-1 && i!=0){//--最后统计行
   					strTab = strTab + "&nbsp;";
   					zongji = cols[k].text;//事故总数
   					len = cols.length-1;
   				}else{
   					if(i==0){//--表头
   					    if(k == 0){
   					        strTab =  strTab +statTypeDesc;
   					    }else{
   					        strTab = strTab + cols[k].text.replace('周','周<br>');
   					    }
   					}else{
   					    if(k == 0 && i != 0 && i != rows.length-1){//除去首行和末行的，每行第一列加checkbox
   					        strTab += "<input name='boxname' type='checkbox' />";
   					    }
   						if(cols[k].text == "0"){//--数据为0不显示
   							strTab = strTab + "&nbsp;";
   						}else{
   							strTab = strTab + cols[k].text;
   						}
   						
   					}
   				}
   				strTab = strTab + "</td>";
   			}
   			strTab = strTab + "</tr>";
   		}
   		if(rows.length!=1){
   			strTab = strTab + "<tr>";
   			strTab = strTab + "<td align= 'right' colspan = '"+len+"'>";
   			strTab = strTab + "总计";
   			strTab = strTab + "</td>";
   			strTab = strTab + "<td>";
   			strTab = strTab + zongji;
   			strTab = strTab + "</td>";
   			strTab = strTab + "</tr>";
   		}
   		strTab = strTab + "</table>";
   		$("overSpeedList").innerHTML = strTab;
   		Popup.prototype.hideTips();	
   	},
   	viewImage : function (){
   		var radios = document.all.timemode;
		var timetype = "";//时间段标志 1：日 2：周 3：月 4：年
		for(var i = 0; i<radios.length ; i++){
			if(radios[i].checked == true){
				timetype = radios[i].value;
				break;
			}
		}
		if(this.check(timetype)==false){
			return;
		}
		radios = document.all.statmode;
		var stattype = "";//统计方式标志 1：事故地点 2：车辆类型 
		for(var i = 0; i<radios.length ; i++){
			if(radios[i].checked == true){
				stattype = radios[i].value;
				break;
			}
		}
		var url = "violinechart.jsp";
		url = url + "?timetype="+timetype+"&stattype="+stattype;
		switch(timetype){
			case "1" : url = url + "&starttime=" + $("STARTTIME").value+"&endtime="+$("ENDTIME").value; break;
			case "2" : url = url + "&starttime=" + $("STARTTIME").value+"&endtime="+$("ENDTIME").value; break;
			case "3" : url = url + "&monthYear=" + $("monthYear").value+"&monthMonth="+$("monthMonth").value; break;
			case "4" : url = url + "&year=" + $("year").value; break;
		}
		
		window.open(url);
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
var viostat = new Viostat();
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
    var title = "违法信息统计图"+ timeStr;
    var chartType = "line";
    var xDesc = "时段";
    var yDesc = "违法次数";
    doChart(title,chartType,xDesc,yDesc,DataForChartStr);
}
