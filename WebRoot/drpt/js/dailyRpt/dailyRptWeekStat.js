/**
 * 统计入口函数。 */
var startTime="";
var endTime="";
var weekDatas=null;
var tempJgid="";//用于送到后台的jgid，将jgid按照总队支队大队分别选取前2，4，6位。
/////////////////////////////////////////////周报表 begin///////////////////////////////////////////////////////////
function initWeekTime(jgid){
	var d = new Date(); 
	d.setTime(d.getTime()-1000*60*60*24);   
	var year = d.getYear(); 
	var month = d.getMonth()+1; 
	var date = d.getDate(); 
	if(month<10){
		month="0"+month;
	}
	if(date<10){
		date="0"+date;
	}
	$("startTime").value=year+"-"+month+"-"+date;
	$("endTime").value=year+"-"+month+"-"+date;
	startTime=$("startTime").value;
	endTime=$("endTime").value;
	
	var url = "drpt.dailyRpt.weekStat.d";
	url = encodeURI(url);

    tempJgid = " AND rzbh like '" + jgid.substring(0,2)+"__00%'";	    //统计所有支队	var params= "?startTime="+startTime+"&endTime="+endTime+"&departmentId="+tempJgid;
	params = encodeURI(params);
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showWeekStat});
 }

/**
 * 统计周报表的20项 */
function weekStat(jgid){
	startTime=$("startTime").value;
	endTime=$("endTime").value;

	tempJgid = " AND rzbh like '" + jgid.substring(0,2)+"__00%'";	    //统计所有支队
	var url = "drpt.dailyRpt.weekStat.d";
	url = encodeURI(url);

	var params= "?startTime="+startTime+"&endTime="+endTime+"&departmentId="+tempJgid;
	params = encodeURI(params);
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showWeekStat});
}

function showWeekStat(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	//alert(xmlHttp.responseText);
	var rows = xmlDoc.getElementsByTagName("row");
	var textName="";

 	weekDatas = new Array();
 	if(rows.length<=0){
		for(i=1;i<21;i++){
			textName = 'text'+i;
			weekDatas[i]=results[i].text;
		    document.getElementById(textName).value = 0;
		}
		return;
   	}
   	for (var i = 0; i < rows.length; i++){
     	var results = rows[i].childNodes;
		for(j = 0; j < results.length; j++){
		    textName = 'text'+(j+1);
		    if(results[j].text!="　"){
		    	weekDatas[j]=results[j].text;
		    	document.getElementById(textName).value = results[j].text;
		    }else{
		    	weekDatas[j]=0;
		    	document.getElementById(textName).value = 0;
		    }
		}
    }
}

////////////////////////////////////////////////////////周报表 end///////////////////////////////////////////////////

////////////////////////////////////////////////////////导成Excel begin//////////////////////////////////////////////
function outExcel() {
//	var startTime=$("startTime").value;
//	var endTime=$("endTime").value;
//	
//	var gridObj = $("dataTable");
//	var rowsNum =gridObj.rows.length;//总行数
//	var colsNum=gridObj.rows.item(0).cells.length;//总列数
//
//	var tabHeader = new Array();//表头
//	var tabData = new Array();//表数据
//	var col = 0;
//    for (col = 0; col < colsNum; col++) {
//	   tabHeader.push(gridObj.rows.item(0).cells(col).innerHTML);
//    }
//    for(var row = 1; row < rowsNum;row++){
//	    for (col = 0; col < colsNum; col++) {
//    		if(col==2){
//    			tabData.push($("text"+row).value);
//    		}else if(col==5){
//	    		if(row==10){
//	    			tabData.push($("text20").value);
//	    		}else{
//	    			tabData.push($("text1"+row).value);
//	    		}
//	    	}
//	    }
//    }
//	
//	var url = "drpt.dailyRpt.weekOutExcel.d?startTime=" + startTime + "&endTime=" +endTime + "&tabData=" + tabData;
	var startTime = document.getElementById('startTime').value;
	var endTime = document.getElementById('endTime').value;
	if (startTime==endTime){
    	tbsj = startTime;
    }else{
    	tbsj = "从"+startTime+"到"+endTime;
    }
	var url="printRptWeek.jsp?weekDatas="+weekDatas+"&tbsj="+tbsj+"&tbdw="+jgmc;
	url = encodeURI(url);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
     
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");   
}
////////////////////////////////////////////////////////导成Excel end///////////////////////////////////////////////////
	
