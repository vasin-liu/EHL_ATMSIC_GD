//全局变量
//统计用SQL
var _sql="";
//打印用的统计数据
var _print=true;

//////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * 函数功能:初始化页面时间值
 **/
function initTime(){
	// 默认选择年状态
	var startTime="";
	var endTime="";
	$("OutExcelCaseInfo").disabled=true;
	document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
	var d = new Date(); 
	//d.setTime(d.getTime()-1000*60*60*24);
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
	var zhidui="";
	var dadui="";
	var wfzlId=""
	zhidui=$("zhiduiId").value;
	dadui=$("daduiId").value;
	wfzlId=$("wfzlId").value;
	 
	var sql = "select riqi,b.jgmc,c.name,count1 from ";
	sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
	sql += " where 1=1 ";
	if(zhidui!=""){
		sql += " and substr(bh,1,4)= '"+zhidui.substring(0,4)+"' ";
	}
	if(dadui!=""){
		sql += " and substr(bh,1,6)= '"+dadui.substring(0,6)+"' ";
	}
	sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
	sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
	sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b,T_ATTEMPER_CODE c";
	sql += " where a.bhid =substr(b.jgid, 1, 6) and c.id=a.wfzl1";
	if(wfzlId!=""){
		sql += " and a.wfzl1= '"+wfzlId+"' ";
	}
	 _sql=sql;
     showMsg(); 
     PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showStatisticResponse","12");
 }
////////////////////////////////////////////////////////////////////////////////////////////////////////
 /**
 * 函数功能: 按日期和辖区统计。
 */
  function statistics(jgid) {
	var startTime=$("startTime").value;
	var endTime=$("endTime").value;
	var sql = "";
	var zhidui="";
	var dadui="";
	var wfzlId=""
	zhidui=$("zhiduiId").value;
	dadui=$("daduiId").value;
	wfzlId=$("wfzlId").value;
	//alert(jgid.substring(4,6));return;
	//总队用户
	if(jgid.substring(2,6)=="0000"){
		sql = "select riqi,b.jgmc,c.name,count1 from ";
		sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
		sql += " where 1=1 ";
		if(""!=zhidui){
			sql += " and substr(bh,1,4)="+zhidui.substring(0, 4);
		}
		if(""!=dadui){
			sql += " and substr(bh,5,2)="+dadui.substring(4, 6);
		}
		sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
		sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
		sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b,T_ATTEMPER_CODE c";
		sql += " where a.bhid =substr(b.jgid, 1, 6) and c.id=a.wfzl1";
		if(wfzlId!=""){
			sql += " and a.wfzl1= '"+wfzlId+"' ";
		}
		
	//支队用户
	}else if(jgid.substring(4,6)=="00"){
	
		sql = "select riqi,b.jgmc,c.name,count1 from ";
		sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
		sql += " where 1=1 ";
		sql += " and substr(bh,1,4)="+jgid.substring(0, 4);
		if(""!=dadui){
			sql += " and substr(bh,5,2)="+dadui.substring(4, 6);
		}
		sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
		sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
		sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b,T_ATTEMPER_CODE c";
		sql += " where a.bhid =substr(b.jgid, 1, 6) and c.id=a.wfzl1";
		if(wfzlId!=""){
			sql += " and a.wfzl1= '"+wfzlId+"' ";
		}
	
	//大队用户
	}else{
	
	
		sql = "select riqi,b.jgmc,c.name,count1 from ";
		sql += " (select to_char(ccsj,'yyyy-mm-dd') as riqi,substr(bh,1,6) as bhid, wfzl as wfzl1,count(bh) as count1 from t_oa_illegalveicle ";
		sql += " where 1=1 ";
		sql += " and substr(bh,0,6)="+jgid.substring(0, 6);
		sql += " and to_char(ccsj,'yyyy-mm-dd')>= '"+startTime+"' ";
		sql += " and to_char(ccsj,'yyyy-mm-dd')<= '"+endTime+"' ";
		sql += " group by ccsj,substr(bh,1,6), wfzl) a, t_sys_department b,T_ATTEMPER_CODE c";
		sql += " where a.bhid =substr(b.jgid, 1, 6) and c.id=a.wfzl1";
		if(wfzlId!=""){
			sql += " and a.wfzl1= '"+wfzlId+"' ";
		}
	
	}
	 
	 _sql=sql;
	 //alert(sql);return;
     showMsg(); 
     PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showStatisticResponse","12");
	
  }
///////////////////////////////////////////////////////////////////////////////////////////////////////
/**
* 函数功能:显示统计信息回调函数
*/
 function showStatisticResponse(xmlDoc){
		var rows = xmlDoc.getElementsByTagName("row");
		var results=null;
	   //生成表头
	    var str = "<table id=\"tabReturn\" class=\"table\" border='1' width='100%' cellSpacing=0 cellPadding=0 >";
	   	str += "<tr  align='center'> ";
	   	str += "<td  class=\"wTableTopCenter\" width='15%' align='center'>查处日期</td>";
	   	str += "<td  class=\"wTableTopCenter\" width='35%' align='center'>所属辖区</td>";
	   	str += "<td  class=\"wTableTopCenter\" width='35%' align='center'>违法类型</td>";
	   	str += "<td  class=\"wTableTopCenter\" width='15%' align='center'>违法数量</td>";
	   	str += "</tr> ";
	   	if(rows.length<=0){
			$("OutExcelCaseInfo").disabled=true;
			document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
		   	str += "<tr  align='center'> ";
		   	str += "<td  class=\"wTableCenterCenter\" colspan='4'>这个条件下没有统计数据！</td>";
		   	str += "</tr> ";
			str += "</table>";
		   //添加到结果面板上
			var tdObj = document.getElementById("tdData");
			tdObj.className = "scrollTable";
			tdObj.innerHTML = str;	
			Popup.prototype.hideTips();	
	   		_print=true;
			return;
	   	}else{
			_print=false;
			$("OutExcelCaseInfo").disabled=false;
			document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel.gif";
	   	}
	   	var j = 0;
	   	//页面显示用
	   	for (var i = 0; i < rows.length; i++){
	     	results = rows[i].childNodes;
			str += "<tr align='center'>";
			for(j = 0; j < results.length-1; j++){
				if(results[j].text=="" || results[j].text=="null"){
					results[j].text=="　";
				}
	            str += " <td  class=\"wTableCenterCenter\" >";
	            str += results[j].text;
	            str += " </td>";
            }

		    str += "</tr>";
		}
		str += "</table>";
	   //添加到结果面板上
	     var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tdObj = document.getElementById("pageData");
		tdObj.className = "scrollTable";
		tdObj.innerHTML = str;	
		Popup.prototype.hideTips();	
		return;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
* 判断是否有数据，有就导出Excel数据。没有报前台无数据。
*/
function confirmOutExcel(jgid) {

	$("OutExcelCaseInfo").disabled=true;
	document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
	var startTime=$("startTime").value;
	var endTime=$("endTime").value;
	var zhidui="";
	var dadui="";
	zhidui=$("zhiduiId").value;
	if($("daduiId")!=null){
		dadui=$("daduiId").value;
	}
	var url = "drpt.violationStat.statistic.d?wyl=wyl";
	url = encodeURI(url);
	var params= "&startTime="+startTime+"&endTime="+endTime+"&depart="+jgid+"&zhidui="+zhidui+"&dadui="+dadui;
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

	var startTime=$("startTime").value;
	var endTime=$("endTime").value;
	$("OutExcelCaseInfo").disabled=true;
	document.all.OutExcelCaseInfo.src="../../images/button/btnOutExcel_disabled.gif";
	var url = "drpt.violationStat.outExcel.d?sql="+_sql+"&startTime="+startTime+"&endTime="+endTime;
	url = encodeURI(url);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
     
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");   
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
//所有下拉列表回调函数

//大队用户
function daduiCallback(jgid){
	$("daduiId").value=jgid;
	$("daduiId").disabled=true;
	initTime();
}
function daduiCallbackzhidui(jgid){
	$("zhiduiId").value=jgid;
	$("zhiduiId").disabled=true;
}

//支队用户
function zhiduiCallback(jgid){
	$("zhiduiId").value=jgid;
	$("zhiduiId").disabled=true;
	var zhidui=jgid.substring(0,4);
	fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+zhidui+"' and substr(jgid,5,2)<>'00'","","initTime()");
}

//总队用户
function zongduiCallback(){
	fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00'","全部","","zongduiOnChange");
}

//总队用户选择支队显示相应大队
function zongduiOnChange(){
	var zhidui=$("zhiduiId").value;
	if(zhidui==""){
		fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","","initTime()");
	}else{
		zhidui=zhidui.substring(0,4);
		var sql="select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+zhidui+"'"
		fillListBox("daduiTdId","daduiId","170",sql,"","");
	}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////
