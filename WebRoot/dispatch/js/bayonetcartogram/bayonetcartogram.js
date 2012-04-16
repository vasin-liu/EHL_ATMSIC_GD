
/** 
 *作者duyq
 *函数功能:获取系统时间
 *参数:offset偏移分钟数
 */
function getSysdate(offset, ignoreTime) {
	var nowDate = new Date();
	var cNowDate = new Date(nowDate - offset * 60 * 1000);
	if (ignoreTime == true) {
		var strDateTime = cNowDate.getYear() + "-"
				+ getFull(cNowDate.getMonth() + 1) + "-"
				+ getFull(cNowDate.getDate());
	} else {
		var strDateTime = cNowDate.getYear() + "-"
				+ getFull(cNowDate.getMonth() + 1) + "-"
				+ getFull(cNowDate.getDate()) + ' '
				+ getFull(cNowDate.getHours()) + ":"
				+ getFull(cNowDate.getMinutes());
	}
	return strDateTime;
}

/**
 * 如果numVar为一位则左侧加零
 * @param {} numVar
 * @return {}
 */
function getFull(numVar) {
	if ((numVar + "").length >= 2) {
		return numVar;
	} else {
		return "0" + numVar;
	}
}

/** 
 *作者zhaoy
 *函数功能:生成年份下拉列表,并放入容器
 *参数:containerId-容器ID 
 *thisId-生成的Select下拉列表Id(和月份下拉列表Id逗号相隔)  
 *widthStr-列表宽度 onChangeFun-列表事件 ignoreMonth-是否忽略月份下拉列表
 */
function createYearSelect(containerId, thisId, widthStr, onChangeFun,
		ignoreMonth) {
	widthStr = 55;
	var container = document.getElementById(containerId);
	var yearSelectStr = "<select id=start" + thisId.split(",")[0] + " style='width:"
			+ widthStr + "' onchange='" + onChangeFun + "'>";
	var temYear = "";
	var monthSelectId = "";
	var currentYear = getSysdate(0, true).split("-")[0];
	var len = parseInt(currentYear) - 1980 + 1;
	for (var i = 0; i < len; i++) {
		temYear = 1980 + parseInt(getFull(i));
		if (temYear == currentYear) {
			yearSelectStr += "<option value=" + temYear + " selected> "
					+ temYear + " </option>";
		} else {
			yearSelectStr += "<option value=" + temYear + "> " + temYear
					+ "</option>";
		}
	}
	yearSelectStr += "</select>年";
	if (ignoreMonth == false) {
		if (thisId.split(",").length > 1) {
			monthSelectId = thisId.split(",")[1];
		}
		yearSelectStr += "&nbsp;"
				+ getMonthSelect("start"+monthSelectId, 40);
	}
	
	yearSelectStr += " - <select id=end" + thisId.split(",")[0] + " style='width:"
			+ widthStr + "' onchange='" + onChangeFun + "'>";
	var temYear = "";
	var monthSelectId = "";
	var currentYear = getSysdate(0, true).split("-")[0];
	var len = parseInt(currentYear) - 1980 + 1;
	for (var i = 0; i < len; i++) {
		temYear = 1980 + parseInt(getFull(i));
		if (temYear == currentYear) {
			yearSelectStr += "<option value=" + temYear + " selected> "
					+ temYear + " </option>";
		} else {
			yearSelectStr += "<option value=" + temYear + "> " + temYear
					+ "</option>";
		}
	}
	yearSelectStr += "</select>年";
	if (ignoreMonth == false) {
		if (thisId.split(",").length > 1) {
			monthSelectId = thisId.split(",")[1];
		}
		yearSelectStr += "&nbsp;"
				+ getMonthSelect("end"+monthSelectId, 40);
	}
	container.innerHTML = yearSelectStr;
}

/** 
 *作者zhaoy
 *函数功能:获取12个月的下拉列表
 *参数:
 */
function getMonthSelect(monthSelectId, widthStr) {
	var monthSelectStr = "<select id=" + monthSelectId + " style='width:"
			+ widthStr + "'>";
	var temMonth = "";
	var currentMonth = getSysdate(0, true).split("-")[1];
	for (var i = 0; i < 12; i++) {
		temMonth = getFull(i + 1);
		if (temMonth == currentMonth) {
			monthSelectStr += "<option value=" + temMonth + " selected> "
					+ temMonth + " </option>";
		} else {
			monthSelectStr += "<option value=" + temMonth + "> " + temMonth
					+ "</option>";
		}
	}
	monthSelectStr += "</select>&nbsp;月";
	return monthSelectStr;
}

/**
 *重设检索时间<br/> 
 */
function setTimeRadio() {
	var timeType = $("timeType").value;
	// 日
	if (timeType == "1") {
		$("ResetTimeName").innerHTML = "起止日期："
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	// 周
	} else if (timeType == "2") {
		var yearSelectStr = "<select id='yearSelect' >";
		$("ResetTimeName").innerHTML = "统计年月："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		yearSelectStr = yearSelectStr + getMonthSelect("monthSelect",40);
		$("ResetTime").innerHTML = yearSelectStr;
	// 月
	} else if (timeType == "3") {
		// 设置年月
		createYearSelect("ResetTime", "yearId,monthId", 60, "", false);
		// 设置lable
		$("ResetTimeName").innerHTML = "起止年月："
	// 季度
	} else if (timeType == "4" || timeType == "5") {
		var yearSelectStr = "<select id='yearSelect' >";
		$("ResetTimeName").innerHTML = "统计年份："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear + "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		$("ResetTime").innerHTML = yearSelectStr;
	// 年
	} else if (timeType == "6") {
		var yearSelectStr = "<select id='startyearId' >";
		$("ResetTimeName").innerHTML = "起止年份："

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		
		var endSelectStr = "<select id='endyearId' >";

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				endSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				endSelectStr += "<option value=" + temYear + "> " + temYear + "</option>";
			}
		}
		endSelectStr += "</select>&nbsp;年";
		
		yearSelectStr = yearSelectStr + " - " +  endSelectStr; 
		$("ResetTime").innerHTML = yearSelectStr;
		
		
	}else if(timeType=="7"){//小时
		$("ResetTimeName").innerHTML = "起止时间："
		$("ResetTime").innerHTML = '<input name="hours" type="text" style="width:100" '
				+ 'class="text" id="hours" onClick="SelectDate(this,0);" /> '
	}else {//按年统计节日
		iSYear();
	}
}
//选择节日统计时，显示的时间
function iSYear(){
	if($("CountType").value=="1"){
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}else{
		$("countvalue").innerHTML = '输入日期：<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}
}
var statTypeDesc = "";
var timeStr = "";
var timetype1 = "";
var timetype2 = "";
var alarmTotalType2 = "";
var timeTypeq = "";
/**
 * 统计信息取得的处理<br/>
 */
function searchTotalInfo() {
	var alarmTotalType = "";// 统计方式 1：时间 2：节假日
	var alarmTotalType = $("alarmTotalRadio").value;
	
	
	var timeRadioType = $("timeType").value;// 时间 1：日 2：周 3：月 4：季度5：半年6：年7：时
	timeTypeq = timeRadioType;
	// 检索条件的check
	/*if (checkSeachInfo(timeRadioType) == false) {
		return;
	}*/
	
	// 起止日期的取得
	var STARTTIME = $("STARTTIME")?$("STARTTIME").value:"";
	var ENDTIME = $("ENDTIME")?$("ENDTIME").value:"";
	// 年月的取得
	var startyearId = $("startyearId")?$("startyearId").value:"";
	
	var startmonthId = $("startmonthId")?$("startmonthId").value:"";
	
	var endyearId = $("endyearId")?$("endyearId").value:"";
	
	var endmonthId = $("endmonthId")?$("endmonthId").value:"";
	
	// 年份的取得
	var yearSelect = $("yearSelect")?$("yearSelect").value:"";
	// 月份的取得
	var monthSelect = $("monthSelect")?$("monthSelect").value:"";
	//按小时取得
	var hours = $("hours")?$("hours").value:"";
	// 单位id
	var depId = $("TSJGID")?$("TSJGID").personId:"";
	var depName = $("TSJGID")?$("TSJGID").value:"";
	
	var jgbh = $("jgbh")?$("jgbh").value:"";
	var jgid = $("jgid")?$("jgid").value:"";
	var departType = $("departType")?$("departType").value:"";
	//卡口名称
	var roadname = $("roadname").value;
	if(roadname==""){
		alert("请选择卡口");
		return false;
	}
	//统计类型
	var CountType = $("CountType").value;
	var str ;
	var str1;
	var str2;
	var params = {};
	timetype1 = timeRadioType;
	params["CountType"] = CountType; // 统计类型
	params["alarmTotalType"] = alarmTotalType;
	params["timeRadioType"] = timeRadioType;
	params["STARTTIME"] = STARTTIME;//按天计算的开始时间
	params["ENDTIME"] = ENDTIME;//按天计算的结束时间
	params["startyearId"] = startyearId;//按月取得开始年时间
	params["startmonthId"] = startmonthId;//按月取得的开始月时间
	params["endyearId"] = endyearId;//按月取得的结束年
	params["endmonthId"] = endmonthId;//按月取得的结束月
	params["monthSelect"] = monthSelect;
	params["yearSelect"] = yearSelect;
	params["hours"] = hours;//按小时计算的时间
	params["roadname"] = roadname;
	params["depId"] = depId;
	params["depName"] = depName;
	params["jgbh"] = jgbh;
	params["jgid"] = jgid;
	params["departType"] = departType;
	
	switch (timeRadioType) {
		case "1" : // 日
					if(CountType=="2" && alarmTotalType=="1"&&$("checkbox").checked==false){
						str = STARTTIME.substr(5,STARTTIME.length);
						timeStr =startyearId+"-"+endyearId+"("+str+"）" ;
						break;
					}else if(CountType=="2" && alarmTotalType=="1"&&$("checkbox").checked==true){
						str = STARTTIME.substr(5,STARTTIME.length);
						str1 = ENDTIME.substr(5,ENDTIME.length);
						timeStr = startyearId+"-"+endyearId+"("+str+"--"+endmonthId+"月)";
						break;
					}else{
						timeStr = "(" + STARTTIME + "--" + ENDTIME + ")";
						break;
					}
		case "2" :// 周
					timeStr = "("  +yearSelect+"年"+monthSelect+"月"+ ")";
					break;
		case "3" :// 月
					if(CountType=="2" && alarmTotalType=="1"&&$("checkbox").checked==false){
					
						timeStr =startyearId+"-"+endyearId+"("+startmonthId+"月）" ;
						break;
					}else if(CountType=="2" && alarmTotalType=="1"&&$("checkbox").checked==true){
						
						timeStr = startyearId+"-"+endyearId+"("+startmonthId+"月--"+endmonthId+"月)";
						break;
					}else{
						timeStr = "(" + startyearId+"年"+"-"+startmonthId +"月"+ "--" + endyearId+"年"+"-"+endmonthId+"月"+")";
						break;
					}
		/*case "4" :// 季度
			timeStr = "("  +yearSelect+"年"+"季度"+ ")";
			break;*/
		case "5" :// 半年
			timeStr = "("  +yearSelect+"年"+"半年"+ ")";
			break;
		case "6" :// 年
			timeStr = "(" + startyearId + "年" + "--" + endyearId +"年"+ ")";
			break;
		case "7" ://小时
			timeStr = "("+hours+")";
			break;
		default:
			if(CountType=="2" && alarmTotalType=="2"&&$("checkbox").checked==true){
				var str2 = document.getElementById("timeType");
				timeStr = "("+startyearId+"--"+endyearId+")"+str2.options[str2.selectedIndex].text;
				break;
			}else{
				timeStr = "(" + STARTTIME + "--" + ENDTIME + ")";
				break;
			}
	}
	params["timeStr"] = timeStr;
	var url = "dispatch.login.getData.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:show});
}
function Loading(roadname){
	var params = {};
	params["roadname"] = roadname;
	var url = "dispatch.login.load.d";
	url = encodeURI(url);
	new Ajax.Request(url,{method:"post",parameters:params, onComplete:show});
	
}

function getRoadxml(){
	var params = "";
	var url = "dispatch.login.getRoadDate.d";
	url = encodeURI(url);
	new Ajax.Request(url,{method:"post",parameters:params, onComplete:showRoad});
}

function show(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	xmlDoc  = xmlDoc.documentElement;
	datas = xmlDoc.childNodes;
	if(datas.length==0){
		alert("没有可以生成的数据");
		return false;
	}
	
	showSearchViews(xmlDoc);
	showSearchRoad(xmlDoc);
	showSearchView(xmlDoc);
	
}

/**
@作者： 罗军礼
@函数说明： 将后台返回的数据，拼接成统计报表
@时间：2010-11-1
**/

function showSearchRoad(xmlDoc){
	
	var datas = xmlDoc.childNodes;
	if(datas.length==0){
		alert("没有可生成的数据");
		return false;
	}
	var roadnames = $("roadname").value;
	///$('msg').innerHTML = '<span class="currentLocationBold">'
		///			+timeStr+roadnames+'等卡口流量环比统计</span>';
	var roadname = roadnames.split("、");
	var conuttypes = document.getElementById("CountType");
	var datetime = "";
	var datetimes = {};
	var count = 0;//标识符
	var timecount = 0;//标识符
	var leng = datas[0].childNodes;
	var len="800px";//表格宽度
	var datasSum = 0;//统计横值
	var datasSums = new Array(roadname.length);//总流量集合
	var colSum = new Array(leng.length+1);//统计列值
	var span = leng.length+2;//合并第一行所有列、作为标题
	if(leng.length>10){
		len="1000px";
	}
	var xmlStr = "<table id=\"tabVeh\" class=\"scrollTable\""
	+ " cellSpacing=0 cellPadding=0 width="+len+">"
	+"<tr class=\"titleTopBack\"  align=\"center\"><td colspan=\""+span+"\">"+timeStr+roadnames+"等卡口流量"+conuttypes.options[conuttypes.selectedIndex].text+"</td></tr>"
	+"<tr align=\"center\" width=\"20%\">"
	+"<td>卡口名称</td>";
	
	for(var i=1;i<datas.length;i++){ //获得查询到的时间
		var col = datas[i].childNodes;
		if(datetime!=col[1].text){
			datetime = col[1].text;
			datetimes[timecount] = col[1].text;
			xmlStr +="<td>"+col[1].text+"</td>";
			colSum[count] = 0;
			count++;
			timecount++;
		}
	}
	colSum[leng.length]=0;
	xmlStr+="<td>总计</td>";
	xmlStr+="</tr>";
	for(var j=0;j<roadname.length;j++){
		datasSum = 0;
		count=0;
		xmlStr+="<tr class=\"rowstyle\" align=\"center\">";
		xmlStr+="<td >"+roadname[j]+"</td>";
			for(var n=1;n<datas.length;n++){
				var col = datas[n].childNodes;	
				if(roadname[j]==col[0].text){
					xmlStr +="<td>"+col[2].text+"</td>";
					datasSum+=parseInt(col[2].text);
					colSum[count]=colSum[count]+parseInt(col[2].text);
					
					count++;
				}
			}
		
		xmlStr+="<td>"+datasSum+"</td>";
		datasSums[j]=datasSum;
		colSum[leng.length] = colSum[leng.length]+datasSum;
		xmlStr+="</tr>";
	}
	xmlStr+="<tr align=\"center\">";
	xmlStr+="<td>总计</td>";
	for(var a=0;a<colSum.length;a++){
		xmlStr+="<td>"+colSum[a]+"</td>";
	}
	xmlStr+="</tr>";
	xmlStr+="</table>";
	$("overSpeedList").innerHTML = xmlStr;
	
}
//生成柱状图（单卡口总流量）
function showSearchViews(xmlDoc){

	var datas = xmlDoc.childNodes;
	var roadnames = $("roadname").value;
	var roadname = roadnames.split("、");
	var conuttypes = document.getElementById("CountType");
	var strXML = "<chart palette='1' caption='"+timeStr+roadnames+"等卡口总流量"+conuttypes.options[conuttypes.selectedIndex].text+""+
	"' shownames='1' showvalues='0'  numberPrefix='' showSum='1' formatNumberScale='0' decimals='0' overlapColumns='0'>";

	var cols = datas[0].childNodes;
	strXML+="<categories>";
	for(var i=0;i<cols.length;i++){
		strXML+="<category label=\""+cols[i].text+"\" />";
	}
	strXML+="</categories>";
	for(var j=0;j<roadname.length;j++){
		strXML+="<dataset seriesName=\""+roadname[j]+"\" showValues=\"0\">";
			for(var n=1;n<datas.length;n++){
				var col = datas[n].childNodes;	
				if(roadname[j]==col[0].text){
					strXML +="<set value=\""+col[2].text+"\"/>";
				}
			}
		strXML+="</dataset>";
	}
	strXML+="</chart>";
	var chart = new FusionCharts("../../swf/StackedColumn3D.swf", "barIds", "750", "400", "0", "0");
	chart.setDataXML(strXML);
	chart.render("barIds");
	
}
//生成柱状图
function showSearchView(xmlDoc){
	var datas = xmlDoc.childNodes;
	var roadnames = $("roadname").value;
	var conuttypes = document.getElementById("CountType");
	var roadname = roadnames.split("、");
	var strXML = "<chart caption='"+timeStr+roadnames+"等卡口流量"+conuttypes.options[conuttypes.selectedIndex].text+"' shownames='1' showvalues='0' formatNumberScale='0' decimals='0'>";
	var cols = datas[0].childNodes;
	strXML+="<categories>";
	for(var i=0;i<cols.length;i++){
		strXML+="<category label=\""+cols[i].text+"\" />";
	}
	strXML+="</categories>";
	for(var j=0;j<roadname.length;j++){
		strXML+="<dataset seriesName=\""+roadname[j]+"\" showValues=\"0\">";
			for(var n=1;n<datas.length;n++){
				var col = datas[n].childNodes;	
				if(roadname[j]==col[0].text){
					strXML +="<set value=\""+col[2].text+"\"/>";
				}
			}
		strXML+="</dataset>";
	}
	strXML+="</chart>";
	var chart = new FusionCharts("../../swf/MSColumn3D.swf", "ChartId", "750", "400", "0", "0");
	chart.setDataXML(strXML);
	chart.render("barId");
	
}
//显示卡口
function showRoad(xmlHttp){
  var xmlDoc = xmlHttp.responseXML;
  
  xmlDoc = xmlDoc.documentElement;
  var rows = xmlDoc.childNodes;
  var roadxml = "<table width=\"100%\" bgcolor=\"#B4C1E2\" cellspacing=\"0\"cellpadding=\"5\" >"+
  "<tr><td width=\"80%\" height=\"30px\" colspan=\"2\"><font face=\"Arial\" color=\"#FFFFFF\">选择卡口</font></td>"+
  "<td align=\"right\"><a href=\"#\" class=\"currentLocation\" onclick=\"hideMe(),getCheckValue(),showView();\" width=\"20%\">确认</a></td></tr>"+
  "<tr><td align=\"left\" colspan=\"3\"><input type=\"checkbox\" name=\"checkbox\" id=\"box\" onclick=\"clickAllCheckBox();\">全选</td></tr>";
 
  for(var i=0;i<rows.length;i++){
  
  	 var cols = rows[i].childNodes;
  	  
	 roadxml+="<tr class=\"scrollColThead\"  style=\"text-decoration: none;background-color: #B2C1E1;line-height: 30px;\">"
	 
	 for(var j=0;j<cols.length;j++){
		 
		 roadxml+="<td width=\"33%\"><input type=\"checkbox\" name=\"checkbox\" id=\"bok\" value=\""+cols[j].text+"\">"+cols[j].text+"</td>";
	 }
	 roadxml+="</tr>";
  }
  roadxml+="</table>";
  
  $("theLayer").innerHTML = roadxml;
				
}
/**
*@作者： 罗军礼
*@函数说明：生成折线图
*@时间：2010-11-12
**/

//全选按钮
function clickAllCheckBox() {

    var allBoxs = document.getElementsByName("checkbox");	
    
	for(var  k = 0; k <allBoxs.length; k++){
	
		if($("box").checked) {
		
			allBoxs[k].checked = true;
			
		} else {
		
			allBoxs[k].checked = false;
			
		}
    }
}
//获得被选择卡口名称
function getCheckValue(){

	var allboxs = document.getElementsByName("checkbox");
	var textValue = "";
	
	for(var i=1;i<allboxs.length;i++){
	
		if(allboxs[i].checked){
		
			textValue=textValue+allboxs[i].value+"、";
			
		}
	}
	$("roadname").value = textValue.substr(0,textValue.length-1).replace("on、","");
}

/**
 * 鼠标移入<br/>
 * @param {} obj
 * @param {} color
 */
function mouseover(obj,color){ 
   if(obj.bgColor != color){
	 obj.bgColor = "#117FC9"; 
   }
}

/**
 * 鼠标移出<br/>
 * @param {} obj
 * @param {} color
 */
function mouseout(obj,color){
   if(obj.bgColor != color.toLowerCase()){
	  obj.bgColor = "#FFFFFF"; 
   }
}

/**
 * 统计图取得的处理<br/>
 */
function searchGraphic() {
	
}

function DateDiff(sDate1,sDate2){
   var aDate,oDate1,oDate2,iDays ;
   aDate =sDate1.split('-');
   oDate1 = new Date(aDate[1]+'-'+aDate[2]+'-'+aDate[0]) ;
   //转换为04-19-2007格式
   aDate = sDate2.split('-');
   oDate2 = new Date(aDate[1]+'-'+ aDate[2] +'-'+aDate[0]);
   iDays = parseInt(Math.abs(oDate1 -oDate2)/1000/60/60/24);//把相差的毫秒数转换为天数
   return iDays ;
   }

/**
 * 检索条件的check
 * 
 * @return {Boolean}
 */
function checkSeachInfo(timeRadioType) {
	
	if ($("CountType").value=="2"){
		var startyearId = $("startyearId")?$("startyearId").value:"";
		var endyearId = $("endyearId")?$("endyearId").value:"";
		if (startyearId > endyearId) {
			alert("输入的起始年不能大于结束年！");
			return false;
		}
		//日
		if(timeRadioType == "1"){
			var start = "";
			var end = "";
			if ($("STARTTIME")) {
				start = $("STARTTIME").value;
			}
			if ($("ENDTIME")) {
				end = $("ENDTIME").value;
			}
			if (start == "") {
				alert("请选择开始日期");
				return false;
			}
			if (end == "") {
				alert("请选择结束日期");
				return false;
			}
			if (start > end) {
				alert("开始日期必须小于结束日期");
				return false;
			}
		}
		//节日
		if($("alarmTotalRadio").value=="2"){
			var start = $("STARTTIME").value;
			var end = $("ENDTIME").value;
			if (start == "") {
				alert("请选择开始日期");
				return false;
			}
			if (end == "") {
				alert("请选择结束日期");
				return false;
			}
			if (start > end) {
				alert("开始日期必须小于结束日期");
				return false;
			}
		}
		// 月
		if(timeRadioType=="3"){
			
			var startmonthId = $("startmonthId")?$("startmonthId").value:"";
			
			var endmonthId = $("endmonthId")?$("endmonthId").value:"";
		
			if (startmonthId > endmonthId) {
				alert("输入的起始月不能大于结束月！");
				return false;
			}
		}
	}
	else {
	// 日
	if(timeRadioType == "1"){
		var start = $("STARTTIME").value;
		var end = $("ENDTIME").value;
		if (start == "") {
			alert("请选择开始日期");
			return false;
		}
		if (end == "") {
			alert("请选择结束日期");
			return false;
		}
		if (start > end) {
			alert("开始日期必须小于结束日期");
			return false;
		}
		start = start.split('-');
		end = end.split('-');
		var date1 = new Date(parseInt(start[0]), parseInt(start[1]) - 1,
				parseInt(start[2]));
		var date2 = new Date(parseInt(end[0]), parseInt(end[1]) - 1,
				parseInt(end[2]));
		var ttdateTime = DateDiff($("STARTTIME").value,$("ENDTIME").value) + 1;
		timetype2 = date2.getTime() - date1.getTime();
		if (((date2.getTime() - date1.getTime()) > 30 * 24 * 60 * 60 * 1000) || ttdateTime > 31) {
			alert("统计日期间隔不能大于31天");
			return false;
		}
		}
	// 月
	if(timeRadioType=="3"){
		// 报警年月的取得
		var startyearId = $("startyearId")?$("startyearId").value:"";
		var startmonthId = $("startmonthId")?$("startmonthId").value:"";
		var endyearId = $("endyearId")?$("endyearId").value:"";
		var endmonthId = $("endmonthId")?$("endmonthId").value:"";
		var startYM =startyearId+startmonthId; 
		var endYM =endyearId+endmonthId; 
		if (startYM >endYM) {
			alert("输入的起始年月不能大于结束年月！");
			return false;
		}
	}
	// 季度
	if(timeRadioType=="4"){
		var year = $("yearSelect").value;
		if(year == ""){
			alert("请填写年");
			return false;
		}
		
	}
	// 半年
	if( timeRadioType == "5"){
	}
	// 年
	if( timeRadioType == "6"){
		var startyearId = $("startyearId")?$("startyearId").value:"";
		var endyearId = $("endyearId")?$("endyearId").value:"";
		if (startyearId >endyearId) {
			alert("输入的起始年不能大于结束年！");
			return false;
		}
	}
	if(timeRadioType=="7"){
		var hours = $("hours").value;
		if(hours == ""){
			alert("请选择日期");
			return false;		
		}
	}
	}
	return true;
}
/**
*@作者： 罗军礼
*@函数说明：更新默认的节日起至时间
*@完成时间：2010-11-11
**/
function reSetTime(){
	var timeRadioType = $("timeType").value;
	var start = "";	
	var end = "";
	var yearSelect = "";
	if ($("startyearId")) {
		yearSelect = $("startyearId").value;
	}
	if(timeRadioType=="8"){
	//春节
		start = yearSelect-1+"-12-30";
		end = yearSelect+"-01-15";
		
		searchTime(start,end);
		
	}else if(timeRadioType=="10"){
		//中秋
		start = yearSelect+"-08-15";
		end = yearSelect+"-08-18";
		
		searchTime(start,end);
		
	}else if(timeRadioType=="12"){
		//端午
		start = yearSelect+"-05-05";
		end = yearSelect+"-05-08";
		
		searchTime(start,end);
		
	}else if(timeRadioType=="9"){
		//五一
		start = yearSelect+"-05-01";
		end = yearSelect+"-05-07";
		
		showDiv(start,end);
		
	}else if(timeRadioType=="13"){
		//国庆
		start = yearSelect+"-10-01";
		end = yearSelect+"-10-07";
		
		showDiv(start,end);
	}else if(timeRadioType=="11"){
		//元旦
		start = yearSelect+"-01-01";
		end = yearSelect+"-01-03";
		
		showDiv(start,end);
	}
	
}

function searchTime(start,end){
	var params = {};
	params["start"]=start;
	params["end"]=end;
	var url = "dispatch.login.GetTime.d";
	url = encodeURI(url);
	new Ajax.Request(url,{method:"post",parameters:params, onComplete: showSaerchTime});
}
function showSaerchTime(xmlHttp){
	
	var doc = xmlHttp.responseXML;
  	doc = doc.documentElement;
  	timeStr = doc.childNodes;
  	col = timeStr[0].childNodes;
  	showDiv(col[0].text,col[1].text);
}

function showDiv(start,end){
	if($("CountType").value=="1"){
	$('ResetTime').innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" value="'+start+'" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" value="'+end+'"'
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}else{
		$("countvalue").innerHTML ='<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" value="'+start+'" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" value="'+end+'"'
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}
	
}
/**
 * 
 * 根据统计条件设置查询时间的处理
 */
function reSetTimeShow() {
	if($('alarmTotalRadio').value == '1' && $("CountType").value=='1'){
		///$('timeType').options[0].selected = true;
		
		$('timeType').disabled = true;
		$('alarmTotalRadio').disabled = false;
		$("counttypename").innerHTML = '';
		$("checkbok").innerHTML = '';
		$("countname").innerHTML ='';
		$("countvalue").innerHTML='';
		$('typename').innerHTML = "时&nbsp;&nbsp;&nbsp;&nbsp;间：";
		$('datename').innerHTML = '<select style="width: 50" id="timeType" name="timeType" onChange="setTimeRadio();">'
				+'<option value="1" selected>日</option>'
				+ '<option value="7">时</option><option value="3">月</option>'
				+ '<option value="6">年</option>'
				+'</select>';
		$("ResetTimeName").innerHTML = "起止日期：";
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}else if($('alarmTotalRadio').value=='2' && $("CountType").value=='1'){
		$('timeType').disabled = true;
		$('alarmTotalRadio').disabled = false;
		$("counttypename").innerHTML = '';
		$("checkbok").innerHTML = '';
		$("countname").innerHTML ='';
		$("countvalue").innerHTML='';
		var yearSelectStr = "<select id='startyearId' onchange=\"reSetTime();\">";
		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>&nbsp;年";
		$('timeType').options[0].selected = true;
		//$('timeType').disabled = false;
		$('typename').innerHTML = "选择节日：";	
		$('datename').innerHTML = '<select style="width: 50" id="timeType" name="timeType" onchange="setTimeRadio(),reSetTime();">'
				+'<option value="8" selected>春节</option>'
				+ '<option value="9">五一</option><option value="10">中秋</option>'
				+ '<option value="11">元旦</option>'
				+'<option value="12">端午</option><option value="13">国庆</option>'
				+'</select>';	
		$('ResetTimeName').innerHTML =	yearSelectStr;	
		
	}else if($('alarmTotalRadio').value == '1' && $("CountType").value=='2'){
		$('alarmTotalRadio').disabled = true;
		$("CountType").disabled = false;
		$('typename').innerHTML = "时&nbsp;&nbsp;&nbsp;&nbsp;间：";
		$('datename').innerHTML = '<select style="width: 50" id="timeType" name="timeType" onChange="showDayorMonth();">'
				+'<option value="1" selected>日</option>'
				+ '<option value="3">月</option>'
				+'</select>';
				
		$("ResetTimeName").innerHTML = '周期同比：';
		
		$("ResetTime").innerHTML = '<input type="checkbox" id="checkbox" onclick="showDay();"/>';
		
		$("counttypename").innerHTML = '起&nbsp;止&nbsp;年：';
		
		$("checkbok").innerHTML = isYear();
		
		$("countname").innerHTML ='输入日期：';
		
		$("countvalue").innerHTML='<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> ';
	///getMonthSelect("startmonth",40 )+' - '+getMonthSelect("endmonth", 40);
		/*+'<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';*/
	}else if($('alarmTotalRadio').value == '2' && $("CountType").value=='2'){
		
		$('typename').innerHTML = "选择节日：";	
		
		$('datename').innerHTML = '<select style="width: 50" id="timeType" name="timeType" onchange="setTimeRadio(),reSetTime();">'
				+'<option value="8" selected>春节</option>'
				+ '<option value="9">五一</option><option value="10">中秋</option>'
				+ '<option value="11">元旦</option>'
				+'<option value="12">端午</option><option value="13">国庆</option>'
				+'</select>';
				
		$("ResetTimeName").innerHTML = '周期同比：';
		
		$("ResetTime").innerHTML = '<input type="checkbox" id="checkbox" onclick="showDay();" checked="true"/>';
				
				
		$("counttypename").innerHTML = '起&nbsp;止&nbsp;年：';
		
		$("checkbok").innerHTML = startYear()+' - '+endYear();
		
		$("countname").innerHTML ='起止日期：';
		
		$("countvalue").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" />'
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
		
		
	}
	
}
function showDayorMonth(){
	if($("timeType").value=="1"){
		if($("checkbox").checked==true){
			$("countname").innerHTML ='起止日期：';
			$("countvalue").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" />'
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
		}else{
			$("countname").innerHTML ='输入日期：';
			$("countvalue").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" />';
		}
	}else{
		if($("checkbox").checked==true){
			$("countname").innerHTML = '起止月份：';	
			$("countvalue").innerHTML = getMonthSelect("startmonthId",40 )+' - '+getMonthSelect("endmonthId", 40);
		}else{
			$("countname").innerHTML = '选择月份：';	
			$("countvalue").innerHTML = getMonthSelect("startmonthId",40 );
		}
	}
}

function showDay(){
	if($("checkbox").checked){
		//$("checkbox").checked=true;
		if($("timeType").value=="1"){
			$('alarmTotalRadio').disabled = false;
			$("CountType").disabled = true;
			$("countname").innerHTML ='起止日期：';
			$("countvalue").innerHTML = '<input name="text2" type="text" style="width:80" '
					+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" />'
					+ '- '
					+ '<input name="text" type="text" style="width:80" '
					+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
		}else{
			$('alarmTotalRadio').disabled = false;
			$("CountType").disabled = true;
			$("countname").innerHTML = '起止月份：';	
			$("countvalue").innerHTML = getMonthSelect("startmonthId",40 )+' - '+getMonthSelect("endmonthId", 40);
		}
	}else{
		//$("checkbox").checked=false;
		$('alarmTotalRadio').disabled = true;
		$("CountType").disabled = false;
		$("alarmTotalRadio").value="1";
		reSetTimeShow();
		/*$("countname").innerHTML ='输入日期：';
		$("countvalue").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" />';*/
	}
}
//开始年
function startYear(){
	var yearSelectStr = "<select id='startyearId' onchange=\"reSetTime();\">";
		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>";
		return yearSelectStr;
}
//结束年
function endYear(){
	var endSelectStr = "<select id='endyearId' >";

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				endSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				endSelectStr += "<option value=" + temYear + "> " + temYear + "</option>";
			}
		}
		endSelectStr += "</select>";
		return endSelectStr;
}
//起止年
function isYear(){
	var yearSelectStr = "<select id='startyearId' >";
		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				yearSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				yearSelectStr += "<option value=" + temYear + "> " + temYear
						+ "</option>";
			}
		}
		yearSelectStr += "</select>";
		
		var endSelectStr = "<select id='endyearId' >";

		var currentYear = getSysdate(0, true).split("-")[0];
		var len = parseInt(currentYear) - 1980 + 1;
		for (var i = 0; i < len; i++) {
			temYear = 1980 + parseInt(getFull(i));
			if (temYear == currentYear) {
				endSelectStr += "<option value=" + temYear + " selected> "
						+ temYear + " </option>";
			} else {
				endSelectStr += "<option value=" + temYear + "> " + temYear + "</option>";
			}
		}
		endSelectStr += "</select>";
		
		yearSelectStr = yearSelectStr + " - " +  endSelectStr; 
		return yearSelectStr;
}
/**
function reSetCountType(){
	if($("CountType").value=='1'){
		$('alarmTotalRadio').disabled=false;
		reSetTimeShow();
	}else{
		$('timeType').options[0].selected = true;
		$('alarmTotalRadio').disabled=true;
		//$('timeType').disabled = false;
		$('typename').innerHTML = "时&nbsp;&nbsp;&nbsp;&nbsp;间：";
		$('datename').innerHTML = '<select style="width: 50" id="timeType" name="timeType" onChange="setTimeRadio();">'
				+'<option value="1" selected>日</option>'
				+ '<option value="3">月</option>'
				+'</select>';
		$("ResetTimeName").innerHTML = "起止日期：";
		$("ResetTime").innerHTML = '<input name="text2" type="text" style="width:80" '
				+ 'class="text" id="STARTTIME" onClick="SelectDate(this,0);" /> '
				+ '- '
				+ '<input name="text" type="text" style="width:80" '
				+ 'class="text" id="ENDTIME" onClick="SelectDate(this,0);" />';
	}
}*/

//隐藏卡口选择菜单
function hideMe(){
	document.getElementById("theLayer").style.visibility="hidden";
}
//隐藏统计图
function hideView(){
	document.getElementById("barId").style.visibility="hidden";
	document.getElementById("barIds").style.visibility="hidden";
}	
//显示卡口选择菜单		
function showMe(){
	document.getElementById("theLayer").style.visibility="visible";
}
//显示统计图
function showView(){
	document.getElementById("barId").style.visibility="visible";
	document.getElementById("barIds").style.visibility="visible";
	document.getElementById("overSpeedList").style.visibility="visible";
}