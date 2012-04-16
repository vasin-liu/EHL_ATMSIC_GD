
var startTime="";
var endTime="";
var tempJgid="";//用于送到后台的jgid，将jgid按照总队支队大队分别选取前2，4，6位。
 /**
 ** 统计入口函数。
 **/
function initTime(jgid){
	// 默认选择年状态
	//$("OutExcelCaseInfo").disabled=true;
	//document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
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
	
	var url = "drpt.dailyRpt.stat.d";
	url = encodeURI(url);
	
	//edit by lidq 20100131-->>> start
	//修改根据日志编号查询数据为根据上报机构查询数据
	if(jgid.substring(2,6)=="0000"){        //总队用户
		//支队，大队都选择的是"全部",总队对应的只选择支队的数据，不选择总队、大队的数据。
		tempJgid = " AND TBDW LIKE '" + jgid.substring(0,2)+"__00%' AND TBDW NOT LIKE '" + jgid +"'";	
	}else if(jgid.substring(4,6)=="00"){	//支队用户
	  	//选择的为支队下的全部大队,但是不选自己录入的记录
	  	tempJgid = " AND TBDW LIKE '" + jgid.substring(0,4) + "%'" + " AND TBDW NOT LIKE '" + jgid + "'"; 
	}else{                                  //大队用户
		tempJgid = " AND TBDW LIKE '" + jgid + "'";     //默认选择自己
	}
	
	/**
	  *if(jgid.substring(2,6)=="0000"){        //总队用户
	  *	//支队，大队都选择的是“全部”，总队对应的只选择支队的数据，不选择大队的数据。	  *		tempJgid = " AND rzbh like '" + jgid.substring(0,2)+"__00%'";	
	  *}else if(jgid.substring(4,6)=="00"){	//支队用户
	  *		//选择的为支队下的全部大队,但是不选自己录入的记录
	  *		tempJgid = " AND rzbh like '" + jgid.substring(0,4) + "__%'" + " AND rzbh not like '" + jgid.substring(0,4) + "00%'"; 
	  *}else{                                  //大队用户
	  *		tempJgid = " AND rzbh like '" + jgid.substring(0,6) + "%'";     //默认选择自己
	  *}
	  */
	//edit by lidq <<<<-- ends
	var params= "?startTime="+startTime+"&endTime="+endTime+"&departmentId="+tempJgid;
	//alert('params='+params);
	params = encodeURI(params);
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showStat});
 }

/**
 ** 统计前24项
 **/
function stat(jgid){
	startTime=$("startTime").value;
	endTime=$("endTime").value;
	var zhidui=$("zhiduiId").value;
	var dadui=$("daduiId").value;
	
	confirmationAlarm(jgid);//

	if(jgid.substring(2,6)=="0000"){        //总队用户
		if(""==dadui && ""==zhidui){
			tempJgid = " AND rzbh like '" + jgid.substring(0,2)+"__00%'";	    //支队，大队都选择的是“全部”
		}else{
			if(dadui !=""){
				tempJgid = " AND rzbh like '" + dadui.substring(0,6)+"%'"; //选择的为支队下的某大队
			}else{
				tempJgid = " AND rzbh like '" + zhidui.substring(0,6)+"%'"; //选择的为某支队
			}
		}
	}else if(jgid.substring(4,6)=="00"){	//支队用户
		if(dadui == ""){
			//选择的为支队下的全部大队,但是不选自己录入的记录
			tempJgid = " AND rzbh like '" + jgid.substring(0,4) + "__%'" + " AND rzbh not like '" + jgid.substring(0,4) + "00%'"; 
		}else{
			tempJgid = " AND rzbh like '" + dadui.substring(0,6) + "%'"; //选择的为支队下的某大队
		}
	}else{                                  //大队用户
		tempJgid = " AND rzbh like '" + jgid.substring(0,6) + "%'";
	}
	
	var url = "drpt.dailyRpt.stat.d";
	url = encodeURI(url);
	
	var params= "?startTime="+startTime+"&endTime="+endTime+"&departmentId="+tempJgid;
	params = encodeURI(params);
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showStat});
}
/**
 * 显示流量数据。 
 */

function showStat(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	var textName="";
	//debugger;
 	if(rows.length<=0){
 		for(var attr in reportFields){
 			$(reportFields[attr]).value = 0;
 		}
		return;
   	}
   	
   	for (var i = 0; i < rows.length; i++){
//		for(j = 0; j < results.length; j++){
//		    textName = 'text'+(j+1);
//		    if(results[j].text!="　"){
//		    	document.getElementById(textName).value = results[j].text;
//		    }else{
//		    	document.getElementById(textName).value = 0;
//		    }
//		}
   		setReportValue(rows[i]);
    }
   	createCaption();
    initTfmStat();
}


function initTfmStat(){
	var jgid = $("_jgid").value;
	var zhidui=$("zhiduiId").value;
	var dadui=$("daduiId").value;
	if(zhidui){
		jgid = zhidui;
	}
	if(dadui){
		jgid = dadui;
	}
	var url = "drpt.dailyRpt.tfmStat.d";
	url = encodeURI(url);
	var params= "?startTime="+startTime+"&endTime="+endTime+"&departmentId="+jgid;
	params = encodeURI(params);
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showTfmStatResponse});
}

/**
* 函数功能:显示流量统计信息回调函数
*/
/*
 function showTfmStatResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	var tfm = document.getElementById("tfm");
	//生成表头
    var str = "<table width=\"100%\" align=\"center\"  cellspacing=\"0\" cellpadding=\"0\"  id=\"tfmTable\">";
   	str += "<tr><td colspan=\"5\" class=\"td_r_tfm\" align=\"center\"><b>辖区日车流量最大的路段：</b></td></tr> ";
   	str += "<tr>";
   	str += "<td width=\"20%\" class=\"td_r_b\"  align='center'><b>道路名称</b></td>";
   	str += "<td width=\"20%\" class=\"td_r_b\"  align='center'><b>路段名称</b></td>";
   	str += "<td width=\"20%\" class=\"td_r_b\"  align='center'><b>交通流量（车次）</b></td>";
   	str += "<td width=\"20%\" class=\"td_r_b\"  align='center'><b>客车（车次）</b></td>";
   	str += "<td width=\"20%\" class=\"td_r_tfm\"  align='center'><b>自驾车（车次）</b></td>";
	str += "</tr>"

   	if(rows.length<=0){

	   	str += "<tr>"+"<td  colspan='5'  align=\"center\">对不起，没有数据</td>";
	   	str += "</tr> ";
		str += "</table>";
	   //添加到结果面板上
		//tfm.appendChild(str);
        tfm.innerHTML = str;
        tfm.style.display = 'block';
		return;
   	}

   	for (var i = 0; i < rows.length; i++){
   	   	
     	results = rows[i].childNodes;
		str += "<tr align='center'>";
		for(j = 0; j < results.length; j++){
		    if(i== rows.length-1){
			    if(j==results.length-1){
					str += "<td  width=\"20%\"  align=\"center\">";
				}else{
					str += "<td  width=\"20%\" class=\"td_r_lastline\" align=\"center\">";
				}
		    
		    }else{
			    if(j==results.length-1){
					str += "<td  width=\"20%\" class=\"td_r_zh\" align=\"center\">";
				}else{
					str += "<td  width=\"20%\" class=\"td_r_b\" align=\"center\">";
				}
		    }
			
			
			str += results[j].text;
			str += "</td>";
		}
        str += " </tr>";
    }
    str += "</table>";
	//添加到结果面板上
	tfm.innerHTML = str;
	tfm.style.display = 'block';
	//tfm.appendChild(str);
}
*/

 function showTfmStatResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	toOriRows(rows);
	sortRows(rows);
	var tfm = document.getElementById("tfm");
	//生成表头
    var str = "<table width=\"100%\" align=\"center\"  cellspacing=\"0\" cellpadding=\"0\"  id=\"tfmTable\">";
//    str += "<caption>辖区日车流量最大的路段：</caption>";
   	str += "<tr><td colspan=\"8\" class=\"td_r_tfm\" align=\"left\" style='text-align:left;'><b>辖区日车流量最大的路段：</b></td></tr> ";
   	str += "<tr>";
   	str += "<td width=\"10%\" class=\"td_r_b\"  align='center' colspan='2'><b>道路名称</b></td>";
   	str += "<td width=\"10%\" class=\"td_r_b\"  align='center' colspan='2'><b>路段名称</b></td>";
   	str += "<td width=\"10%\" class=\"td_r_b\"  align='center' colspan='2'><b>机构名称</b></td>";
   	str += "<td width=\"10%\" class=\"td_b\"  align='center' colspan='2'><b>交通流量（车次）</b></td>";
//   	str += "<td width=\"5%\" class=\"td_r_b\"  align='center'><b>客车<br>（车次）</b></td>";
//   	str += "<td width=\"5%\" class=\"td_r_b\"  align='center'><b>自驾车<br>（车次）</b></td>";
//   	str += "<td width=\"5%\" class=\"td_r_b\"  align='center'><b>3日平均流量<br>（车次）</b></td>";
//   	str += "<td width=\"5%\" class=\"td_r_tfm\"  align='center'><b>环比增减<br>（%）</b></td>";
	str += "</tr>";

   	if(rows.length<=0){

	   	str += "<tr>"+"<td  colspan='8'  align=\"center\">对不起，没有数据</td>";
	   	str += "</tr> ";
		str += "</table>";
	   //添加到结果面板上
		//tfm.appendChild(str);
        tfm.innerHTML = str;
        tfm.style.display = 'block';
		return;
   	}

   	for (var i = 0; i < rows.length; i++){
   	   	
     	results = rows[i].childNodes;
		str += "<tr align='center'>";
		for(j = 0; j < results.length; j++){
		    if(i== rows.length-1){          //最后一行
			    if(j==results.length-1){    //最后一行的最后一列
					str += "<td  width=\"15%\"  align=\"center\">";
				}else{ 
				    //最后一行的其他列
//				    if (j==0||j==1){
//						str += "<td  width=\"15%\" class=\"td_r_lastline\" align=\"center\" colspan='3'>";
//					}else if(j==2){
    					str += "<td  width=\"15%\" class=\"td_r_lastline\" align=\"center\" colspan='2'>";
//					}	
				}
		    
		    }else{
			    if(j==results.length-1){    //非最后一行的最后一列
					str += "<td  width=\"15%\" class=\"td_r_zh\" align=\"center\">";
				}else{ 
				    //非最后一行的其他列
				     //最后一行的其他列
//				    if (j==0||j==1){
//						str += "<td  width=\"15%\" class=\"td_r_b\" align=\"center\"  colspan='3'>";
//					}else if(j==2){
    					str += "<td  width=\"15%\" class=\"td_r_b\" align=\"center\" colspan='2'>";
//					}	
					
				}
		    }
			
			
			str += results[j].text;
			str += "</td>";
		}
        str += " </tr>";
    }
    str += "</table>";
	//添加到结果面板上
	tfm.innerHTML = str;
	tfm.style.display = 'block';
	//tfm.appendChild(str);
}

//////////////////////////////////////////导出流量成EXCEL  开始//////////////////////////////////////////////////////////////
/*
* 判断是否有数据，有就导出Excel数据。没有报前台无数据。
*/
function confirmOutExcel(jgid) {

	//$("OutExcelCaseInfo").disabled=true;
	//document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
	var startTime=$("startTime").value;
	var endTime=$("endTime").value;
	var zhidui="";
	var dadui="";
	zhidui=$("zhiduiId").value;
	if($("daduiId")!=null){
		dadui=$("daduiId").value;
	}
	var url = "drpt.dailyRpt.outExcel.d";
	url = encodeURI(url);
//	var params= "?startTime="+startTime+"&endTime="+endTime+"&depart="+jgid+"&zhidui="+zhidui+"&dadui="+dadui;
	var params= "?startTime="+startTime+"&endTime="+endTime+"&depart="+tempJgid;
	params = encodeURI(params);
	//alert(params);return;
	showMsg(); 
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showStatisticOutDataResponse});
}

/**
* 函数功能:判断是否有数据回调函数
*/
 function showStatisticOutDataResponse(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
	   	if(rows.length<=0 ){
			Popup.prototype.hideTips();	
			$("OutExcelCaseInfo").disabled=true;
			document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
			alert("统计结果没有数据！\n请重新选择统计条件进行统计！");
			return;
			
			//有数据可调用输出Ecxel文件函数
	   	}else{
			outExcel();
	   	}
		Popup.prototype.hideTips();	
	}


function outExcel() {
    var _sql="select t.dlmc,t.ldmc,t.carnum,t.kc,t.zjc from ("
    	+"select  distinct mrf.bh,"
    	+" dr.dlmc,mrf.ldmc,mrf.carnum,mrf.kc,mrf.zjc"
    	+" from"
    	+" t_oa_maxroadflow mrf,"
    	+" t_oa_dict_road   dr"
    	+" where mrf.dlbh=dr.dlbh"
    	+" and mrf.rzbh in("
    	+" select drpt.rzbh from t_oa_dayreport drpt"
    	+" WHERE 1=1 "
    	+tempJgid;
      
     if(startTime!=""){
     	_sql += " AND to_char(drpt.TJRQ,'yyyy-mm-dd') >= "+"'"+startTime+"'";
     }
     if(endTime!=""){
     	_sql += " AND to_char(drpt.TJRQ,'yyyy-mm-dd') <= "+"'"+endTime+"'";
     }
      
    _sql +=")"+" order by mrf.carnum desc "+") t"+" WHERE rownum < 21";
    //prompt('',_sql);
	$("OutExcelCaseInfo").disabled=true;
	document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
	var url = "drpt.dailyRpt.outExcel.d?sql="+_sql+"&startTime="+startTime+"&endTime="+endTime+"&department="+tempJgid;
	url = encodeURI(url);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
     
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");   
}

/////////////////////////////////////////////////导出流量成EXCEL 结束////////////////////////////////////////////////////

/////////////////////////////////////////////////打印功能 开始////////////////////////////////////////////////////
function print()
{
//    document.all("printno").style.display="none";
//	document.all("printno2").style.display="none";
	//document.all("print").style.display="block";
	//prompt("",document.all("print").innerHTML);
   
	//document.all("print").style.display="none";	
   	window.print();
//	document.all("printno").style.display="";
//	document.all("printno2").style.display="";

}
/////////////////////////////////////////////////打印功能 结束////////////////////////////////////////////////////


/////////////////////////////////////////////////导出春运日报表成EXCEL 开始//////////////////////////////////////
function input2test()
{
	inputs = document.getElementsByTagName("input");
	var tmpInputs = new Array();
	var count = 0;
	
	for(i=0;i<inputs.length;i++)
	{
		if(inputs[i].className == "input1" )
		{
			tmpInputs[count]=inputs[i];
			count++;
		}
	}
	//count = 0;
	
	for(i=(tmpInputs.length-1);i>=0;i--)
	{
		tmpInputs[i].parentNode.innerText=tmpInputs[i].value;
	}
}

function addTitle()
{
	removeCaption();
        var zhidui = document.getElementById('zhiduiId').options(document.getElementById('zhiduiId').selectedIndex).text;
        var dadui =  document.getElementById('daduiId').options(document.getElementById('daduiId').selectedIndex).text;
        var danwei ="";
        if (dadui == '全部'){
        	danwei = zhidui;
        }else{
        	danwei = dadui;
        }
        var tbsj = "";
        if (startTime==endTime){
        	tbsj = startTime;
        }else{
        	tbsj = "从"+startTime+"到"+endTime;
        }
 		
		var queryHTML = "<TABLE  id=titleTable cellSpacing=0 cellPadding=0 width=\99%\" align=center>";
		queryHTML +="<p><TR>"+
			"<td colspan=\"8\" align=\"center\"  style=\"font-size:17px\"><b>"+endTime.substring(0,4)+"年春运道路交通安全管理日报表</b></td>"+
			"</TR></p>"+
			"<p>"+
			"<p>"+
			"<tr><td colspan='8'></td></tr>"+
			"<tr><td colspan='8'></td></tr>"+
			"<TR>"+
				"<td width='10%'>填报单位：</td>"+
				"<td width='40%' colspan='2' align=\"left\">"+danwei+"</td>"+
				"<td width='50%' colspan=\"4\" align=\"right\">统计日期："+tbsj+"</td>"+
			"</TR></table>";
			
	otable = document.getElementById("dataTable");
	titleRow = otable.insertRow(0); 
	sTitleRowo = otable.insertRow(0); 
	titleRow.insertCell(0);
	sTitleRowo.insertCell(0);

	otable.rows(0).cells(0).colSpan = "8";
	otable.rows(1).cells(0).colSpan = "8";
    
    //otable.rows(0).style.display="none";
    //otable.rows(1).style.display="none";
    //otable.rows(0).style="display:none";
    //otable.rows(1).style="display:none";
    

	otable.rows(0).cells(0).innerText = endTime.substring(0,4)+"年春运道路交通安全日统计报表";
	//otable.rows(1).cells(0).innerText = " 填报单位：　　　市公安局交通警察支队   　　      统计日期：";
	otable.rows(1).cells(0).innerText = " 填报单位："+danwei + "   　　         　　      统计日期："+tbsj;

}

/*
function AllAreaExcel() 
{
	input2test();
	addTitle()
	var oXL = new ActiveXObject("Excel.Application"); 
	var oWB = oXL.Workbooks.Add(); 
	var oSheet = oWB.ActiveSheet; 
	var sel=document.body.createTextRange();
	

			
	sel.moveToElementText(dataTable);
	prompt(dataTable);
	sel.select();
	sel.execCommand("Copy");
	oSheet.Paste();
	oXL.Visible = true;
}
*/

function turnTFM()
{
	oTable = document.getElementById("tfmTable");
	oTable.deleteRow(1);
	oTable.setAttribute("border",1);
//	oTable.rows(0).insertCell(1);
	oTable.rows(0).cells(0).colSpan = "8";
//	oTable.rows(0).cells(1).colSpan = "7";
//	oTable.rows(0).cells(1).innerText = oTable.rows(0).cells(0).innerText;
//	oTable.rows(0).cells(0).innerText = "34";
	 for (i=1; i < oTable.rows.length; i++)
    {
    	
    	/*
    	oTable.rows(i).insertCell(4);
    	oTable.rows(i).cells(4).innerText= "  车次、自驾车 "
    	oTable.rows(i).insertCell(3);
    	oTable.rows(i).cells(3).innerText= "   车次,其中：客车 "
    	oTable.rows(i).insertCell(2);
    	oTable.rows(i).cells(2).innerText= " 路段共 "*/
		/*    
    	oTable.rows(i).cells(0).innerText= oTable.rows(i).cells(0).innerText + oTable.rows(i).cells(1).innerText+ "路段共 "  + oTable.rows(i).cells(2).innerText + "  车次,其中：客车 "  +oTable.rows(i).cells(3).innerText + "  车次、自驾车 "  +  oTable.rows(i).cells(4).innerText  + "   车次。";
    		//oTable.rows(i).cells(0).innerText = "34";
    		oTable.rows(i).cells(0).colSpan = "8";
    	//oTable.rows(i).cells(1).colSpan = "7";
    	oTable.rows(i).deleteCell(4);
    	oTable.rows(i).deleteCell(3);
    	oTable.rows(i).deleteCell(2);
    	oTable.rows(i).deleteCell(1);
    	*/
        for (j=0; j <oTable.rows(i).cells.length; j++)
        {
        	if(j==0) //        国道       路段共       车次，其中：客车       车次、自驾车       车次。
        	{}
        	else if(j==1)
        	{
        		//oTable.rows(i).cells(j).innerText=  oTable.rows(i).cells(j).innerText + "路段共 ";
        	}
        	else if(j==2)
        	{
        		//oTable.rows(i).cells(j).innerText=  oTable.rows(i).cells(j).innerText + "  车次,其中：客车 ";
        	}
        	else if(j==3)
        	{
        		//oTable.rows(i).cells(j).innerText=  oTable.rows(i).cells(j).innerText + "  车次、自驾车 ";
        	}
        	else if(j==4)
        	{
        		oTable.rows(i).cells(j).innerText=  oTable.rows(i).cells(j).innerText + "   车次。";
        	}
        }
    }
}

function AllAreaExcel(){
	//添加标题
	input2test();
    addTitle();
	//turnTFM();
    //创建工作簿
	var oXL = new ActiveXObject("Excel.Application"); 
	var oWB = oXL.Workbooks.Add(); 
	var osheet = oWB.ActiveSheet; 
	//拷贝页面
	var sel=document.body.createTextRange();
	sel.moveToElementText(dataTable);
	sel.select();
	sel.execCommand("Copy");
	osheet.Paste();
	
	//设置标题样式
	osheet.Range("A1:H1").Font.Size=24;
	osheet.Range("A1:H1").HorizontalAlignment=3;
    //调整列宽
    osheet.Columns("B").columnwidth=30;
    osheet.Columns("E").columnwidth=4;
    osheet.Columns("F").columnwidth=20;
    osheet.Columns("G").columnwidth=17;
    osheet.Columns("H").columnwidth=9;
    //总行数和总列数
    var rowCount= osheet.UsedRange.Cells.Rows.Count;
    var columnCount = osheet.UsedRange.Cells.Columns.Count;
    //合并交通流量数值单元格
    for(var i=25;i<=rowCount;i++){
    	osheet.Range("G"+i+":H"+i).MergeCells = true;
    }
    //添加一列
    rowCount++;
    osheet.Range(osheet.Cells(rowCount, 1),osheet.Cells(rowCount,columnCount)).Select(); //选择该列
    oXL.Selection.MergeCells = true; 
    oXL.Selection.HorizontalAlignment = 2;                          //居中
    osheet.Cells(rowCount, 1).value = "   填表人                           审核人                           联系电话";
    //设置行宽
    for(var i=2;i<=rowCount;i++){
    	osheet.Rows(i).RowHeight = 28.5;
    }
    //设置流量标题的字体和跨2行的边框样式
    osheet.UsedRange.Borders.LineStyle = 1;
    osheet.Range("D25:F25").Borders(3).LineStyle = 1;
    osheet.Range("G25:H25").MergeCells = true;
    osheet.Range("G25:H25").Borders(3).LineStyle = 1;
    osheet.Range("G26:H26").MergeCells = true;
    osheet.Range("A2",osheet.Cells(rowCount,columnCount)).Font.Size=12;
    //未知
    oXL.ActiveWorkBook.ActiveSheet.PageSetup.Zoom = false; //缩放打印
    oXL.ActiveWorkBook.ActiveSheet.PageSetup.FitToPagesWide = 1; //'页面缩放：调整为1页高 
    oXL.ActiveWorkBook.ActiveSheet.PageSetup .FitToPagesTall = 1;//'页面缩放：调整为1页宽
    oXL.Visible = true;
    window.location.reload();
    return;
	osheet.Range(osheet.Cells(1, 1),osheet.Cells(1,1)).Select(); //选择第一个单元格列
    //osheet.Columns.AutoFit();   //自动列宽
	osheet.Columns("A").columnwidth=6.13;  // 设置 A 列宽度为40px
	osheet.Columns("B").columnwidth=26.5;  // 设置 A 列宽度为40px
	osheet.Columns("C").columnwidth=11;    // 设置 A 列宽度为40px
	osheet.Columns("D").columnwidth=4.38;  // 设置 A 列宽度为40px
	osheet.Columns("E").columnwidth=3.63;  // 设置 A 列宽度为40px
	osheet.Columns("F").columnwidth=10;  // 设置 A 列宽度为40px
	osheet.Columns("G").columnwidth=18; // 设置 A 列宽度为40px
	osheet.Columns("H").columnwidth=13;   // 设置 A 列宽度为40px
	osheet.Columns("I").columnwidth=9.5;   // 设置 A 列宽度为40px
	osheet.Columns("A").HorizontalAlignment =3;//中 1左 2左 3居中 4右 
	osheet.Columns("B").HorizontalAlignment =2;
	osheet.Columns("C").HorizontalAlignment =3;
	osheet.Columns("D").HorizontalAlignment =3;
	osheet.Columns("E").HorizontalAlignment =3;
	osheet.Columns("F").HorizontalAlignment =2;
	osheet.Columns("G").HorizontalAlignment =3;
	osheet.Columns("H").HorizontalAlignment =3;
	osheet.Columns("I").HorizontalAlignment =3;
	osheet.Range("F4:H15").HorizontalAlignment =2;
	osheet.Range("F20:H20").HorizontalAlignment =2;

	osheet.Rows("3").HorizontalAlignment =3;//中 1左 2左 3居中 4右 
	osheet.Range("A1:I1").Font.Size=24;
	osheet.Range("A1:I1").HorizontalAlignment=3;
	
	osheet.Range("F5:H5").WrapText = true; // 设置自动换行
//	osheet.Range("F4:H26").WrapText = true; // 设置自动换行
	osheet.Range("E10:E19").WrapText = true; // 设置自动换行
	osheet.Range("E21:F23").WrapText = true; // 设置自动换行
	osheet.Range("A1:I2").Borders.LineStyle = 0;
	osheet.Range("A3:I30").Borders.LineStyle = 1; //1实线 2虚线 3 细虚线	
	//osheet.Rows("29").Delete;  // 设置 A 列宽度为40px
	xlrow = osheet.UsedRange.Cells.Rows.Count+1;
	//alert(xlrow);
	for(var i=1;i<xlrow+1;i++){
        //osheet.Rows(i).RowHeight = osheet.Rows(i).RowHeight + 6;   //自动大小后上下无边距，需要增加高度，要不太挤。
		osheet.Rows(i).RowHeight = 28.5;   //自动大小后上下无边距，需要增加高度，要不太挤。
		//osheet.Range("A3:H"+i).Borders.LineStyle = 1; //1实线 2虚线 3 细虚线
		//if (i>xlrow-3){
		//    osheet.Range("A"+i+":H"+i).Borders.LineStyle = 0;   
		//}
		/*
		if(i>28 && i<xlrow){
			 osheet.Range("A"+i+":H"+(xlrow-1)).Borders.LineStyle = 0;   
    	}
    	*/
    }
    osheet.Range("A"+29+":I"+(xlrow-1)).Borders.LineStyle = 1;
    osheet.Range("A2:I"+xlrow).Font.Size=12;
    
    oXL.ActiveWorkBook.ActiveSheet.PageSetup.Orientation = 1; //正常，2横向
 
    //oXL.ActiveWorkBook.ActiveSheet.PageSetup.PrintArea = "A1:H29";  //打印区域
    oXL.ActiveWorkBook.ActiveSheet.PageSetup.Zoom = false; //缩放打印
    oXL.ActiveWorkBook.ActiveSheet.PageSetup.FitToPagesWide = 1; //'页面缩放：调整为1页高 
    oXL.ActiveWorkBook.ActiveSheet.PageSetup .FitToPagesTall = 1;//'页面缩放：调整为1页宽
    // 
    osheet.Range(osheet.Cells(xlrow, 1),osheet.Cells(xlrow,osheet.UsedRange.Cells.Columns.Count)).Select(); //选择该列
    oXL.Selection.HorizontalAlignment = 2;                          //居中
    oXL.Selection.MergeCells = true; 
	osheet.Cells(xlrow, 1) = "   填表人                           审核人                           联系电话";
   
   
	oXL.Visible = true;
	//debugger;
	otable = document.getElementById("dataTable");
	otable.deleteRow(1);
    otable.deleteRow(0);
	
	/*
	titleRow = otable.insertRow(0); 
	sTitleRowo = otable.insertRow(0); 
	titleRow.insertCell(0);
	sTitleRowo.insertCell(0);
    */
    
    //导出excel后处理
    window.location.reload();
}


/*
//指定页面区域“单元格”内容导入Excel
function CellAreaExcel() 
{
	var oXL = new ActiveXObject("Excel.Application"); 
	var oWB = oXL.Workbooks.Add(); 
	var oSheet = oWB.ActiveSheet; 
	//var Lenr = PrintA.rows.length;
	var Lenr = 200;
	for (i=0;i<Lenr;i++) 
	{ 
	   //var Lenc = PrintA.rows(i).cells.length; 
	   for (j=0;j<Lenc;j++) 
	   {
	    oSheet.Cells(i+1,j+1).value = PrintA.rows(i).cells(j).innerText; 
	   } 
	} 
	oXL.Visible = true; 
}


//指定页面区域内容导入Word
function AllAreaWord()
{
	var oWD = new ActiveXObject("Word.Application");
	var oDC = oWD.Documents.Add("",0,1);
	var oRange =oDC.Range(0,1);
	var sel = document.body.createTextRange();
	sel.moveToElementText(PrintA);
	sel.select();
	sel.execCommand("Copy");
	oRange.Paste();
	oWD.Application.Visible = true;
	//window.close();
}
*/
///////////////////////////////////////////导出春运日报表成EXCEL 结束//////////////////////////////
///////////////////////////////////////////提示  开始////////////////////////////////////////////
function confirmationAlarm(jgid){

	var zhidui = document.getElementById('zhiduiId').options(document.getElementById('zhiduiId').selectedIndex).text;
	var dadui =  document.getElementById('daduiId').options(document.getElementById('daduiId').selectedIndex).text;
	if(dadui != '全部'){
		return;
	}
	if (jgid.substring(2,6)=="0000"){ //总队
		if (zhidui == '全部'){
        	zongduiAlarm();
        }else{
        	zhiduiAlarm($("zhiduiId").value);
        }
	}else if("00"==jgid.substring(4,6)){
		zhiduiAlarm(jgid);
	}
}

function zongduiAlarm(){
	var widthv = 400;
	var heightv = 480;
	var xposition = (screen.availWidth - widthv)/2;
	var yposition = (screen.availHeight - heightv)/2;
	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no';	
	//提示总队现在各个支队上报情况。
	window.open('../../zongduiPrompter.jsp',"",feature);
}

function zhiduiAlarm(jgid){
	var widthv = 400;
	var heightv = 480;
	var xposition = (screen.availWidth - widthv)/2;
	var yposition = (screen.availHeight - heightv)/2;
	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no';	
	//提示支队现在各个大队上报情况。
	window.open('../../zhiduiPrompter.jsp?jgid='+jgid,"",feature);
}
///////////////////////////////////////////提示 结束/////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////
//大队用户
function daduiCallback(jgid){
	$("daduiId").value=jgid;
	$("daduiId").disabled=true;
	initTime(jgid);
}

//支队用户
function zhiduiCallback(jgid){
	$("zhiduiId").value=jgid;
	$("zhiduiId").disabled=true;
	var zhidui=jgid.substring(0,4);
	fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+zhidui+"'","","initTime('"+jgid+"')");
}

//总队用户
function zd(){
	fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00'","全部","","zongduiOnChange");
}
//大队用户角色进入时回显支队

function daduiCallbackzhidui(jgid){
	$("zhiduiId").value=jgid;
	$("zhiduiId").disabled=true;
	var temp = $("daduiId").value;
	//alert('jgid='+jgid);
	initTime(temp);
}

//总队用户选择支队显示相应大队
function zongduiOnChange(){
	var zhidui=$("zhiduiId").value;
	if(zhidui==""){
		fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","","initTime('"+jgid+"')");
	}else{
		zhidui=zhidui.substring(0,4);
		var sql="select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+zhidui+"'"
		fillListBox("daduiTdId","daduiId","170",sql,"","");
	}
}

function filterText(text){
	return text == "　"?"0":text;
}

/**设置报表值*/
var reportFields = {
	TRJL : "TRJL",
	CDJC : "CDJC",
	CSSB : "CSSB",
	GDCSD : "GDCSD",
	LDCSD : "LDCSD",
	ZQFWD : "ZQFWD",
	JTWFHJ : "JTWFHJ",
	CSXS : "CSXS",
	KCJY : "KCJY",
	PLJS : "PLJS",
	JHJS : "JHJS",
	WZJS : "WZJS",
	NYCWFZK : "NYCWFZK",
	DXJDCJSZ : "DXJDCJSZ",
	ZKJTWFCL : "ZKJTWFCL",
	JLJTWFJSR : "JLJTWFJSR",
	JCKCCL : "JCKYCL",
	TBKCQYZGBM : "TBKYBM",
	SRZYYSQY : "SRYSQY",
	JYYSQYJSR : "JYYSJSR",
	QDELTQYJYA : "ELTQYA",
	YJSDFLCL : "YJSDFL",
	ZZWXLD : "ZZWXLD",
	XZZYCK : "XZZYCK",
	YDTB : "YDTB",
	PCAQYHC : "PCAQYHC",
	JYTZKCJSR : "JYTZKCJSR",
	QZPLJSRXX : "QZPLJSRXX",
	ZHCFLB : "ZHCFLB",
	JXXCHD : "JXXCHD",
	BFXCGP : "BFXCGP",
	KDXCL : "KDXCL",
	XCH : "XCH",
	XCZL : "XCZL",
	SJY : "SJY",
	DSXC : "DSXC",
	DTXC : "DTXC",
	BZXC : "BZXC",
	WLXC : "WLXC",
	ZHS : "ZHS",
	SWSGZS : "SWSG",
	SWSGSWRS : "SWRS",
	SWSGSSRS : "SSRS",
	TDSGZS : "TDSG",
	TDSGSWRS : "TDSWRS",
	TDSGSSRS : "TDSSRS",
	SZLSZQD : "LSZQD",
	CSD : "CSD",
	ZKJDCJSZ : "ZKJDCJSZ"
};
function setReportValue(row){
	if(row){
		var cells = row.childNodes;
		var i = 0;
		for(var attr in reportFields){
			$(reportFields[attr]).value = filterText(cells[i++].text);
		}
	}
}



/** 将流量xml格式转换成json形式 */
var flowFields = ["rzbh","tbdw","dwmc","tjrq", "bh", "dlbh", "dlmc", "level", "ldmc", 
		"carnum", "kc", "zjc", "avg", "lrratio"];
function flowRowsToObjects(rows) {
	if (rows) {
		var objects = [];
		var rlength = rows.length;
		var flength = flowFields.length;
		for ( var i = 0; i < rlength; i++) {
			var object = {};
			var cells = rows[i].childNodes;
			flength = flength <= cells.length ? flength : cells.length;
			for ( var i = 0; i < flength; i++) {
				object[flowFields[i]] = filterText(cells[i].text);
			}
			objects.push(object);
		}
		return objects;
	}
}

//筛选出机构名称、道路、路段和流量
var siftIndexs = [2,5,6,8,9];
function toOriRows(rows){
	if(rows){
		var indexs = ","+siftIndexs.join(",")+",";
		var rlength = rows.length,row,cells,clength;
		for ( var i = 0; i < rlength; i++) {
			row = rows[i];
			cells = row.childNodes;
			clength = cells.length;
			for ( var j = clength - 1; j >= 0; j--) {
				if(indexs.indexOf(","+j+",") == -1){
					row.removeChild(cells[j]);
				}
			}
		}
	}
}

//将机构名称放在流量前面
function sortRows(rows){
	if(rows){
		var row,cells,text;
		for ( var i = rows.length - 1; i >= 0; i--) {
			row = rows[i], cells = row.childNodes;
			text = row.removeChild(cells[1]).text;
			text = text=="z_old"?"":(text+"_");
			cells[1].text = text+cells[1].text;
			row.insertBefore(row.removeChild(cells[0]),cells[2]);
		}
	}
}

function createCaption(){
	var sdate=$("startTime").value;
	var edate=$("endTime").value;
	var date = sdate;
	if(sdate != edate){
		date += "~" + edate;
	}
	var table = $("dataTable");
   	var caption = table.caption || table.createCaption();
   	caption.style.cssText = "font-size:15px;font-weight:bold;padding:10 0;";
   	caption.innerText = "春运道路交通安全管理日报表（"+date+"）";	
}

function removeCaption(){
	var table = $("dataTable");
	table.removeChild(table.caption);
}