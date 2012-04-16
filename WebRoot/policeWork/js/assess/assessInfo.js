function resetListValue(jgid) {
	if(jgid.substring(4,6) == "00"){
		$("zhiduiId").value = "";
	}else{
		$("zhiduiId").value = jgid.substring(0,4)+"00"+jgid.substring(6);
	}
}
/**
 * 开始分析处理<br/>
 */
function doAssessInfo(jgid) {
	var startDate = $("startDate").value;
	var endDate = $("endDate").value;
	if (!$("startDate").value) {
		alert("请填写查询开始日期！");
		$('startDate').focus();
		return;
	}
	if (!$("endDate").value) {
		alert("请填写查询结束日期！");
		$('endDate').focus();
		return;
	}
	if (startDate > endDate) {
		alert("起始时间不能大于结束时间！");
		$("startDate").focus();
		return;
	}
	if(jgid){
		$("zhiduiId").value = jgid;
	}else{
		jgid = $("zhiduiId").value;
	}
	// 分析处理方法的呼出
	var url = "policeWorks.assess.getAssessInfo.d";
	url = encodeURI(url);
	var params = "zhiduiId=" + jgid + "&startDate=" + startDate
			+ "&endDate=" + endDate + "&sortItem="+$("assessItem").value;
	//Modify by Xiayx 2011-10-10
	//添加考核类型
	var type = $("type").value;
	if(type){
		params += "&type="+type;
	}
	//Modification finished
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showAssessChartResult
	});

}

/**
 * 回显考核分析结果<br/>
 */
function showAssessChartResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == "没有取到考核信息") {
		alert(xmlDoc);
	} else {
		showStr = xmlDoc.split("+");
		var chart = new FusionCharts("../../swf/MSColumn3D.swf", "showDivId",
				"800", "320", "0", "0");
		chart.setTransparent(true);
		chart.setDataXML(showStr[0]);
		chart.render("showDivId");
		$("showTable").innerHTML = showStr[1];
	}
}

function showExcel() {
	var startDate = $("startDate").value;
	var endDate = $("endDate").value;
	if (!$("startDate").value) {
		alert("请填写查询开始日期！");
		$('startDate').focus();
		return;
	}
	if (!$("endDate").value) {
		alert("请填写查询结束日期！");
		$('endDate').focus();
		return;
	}
	if (startDate > endDate) {
		alert("起始时间不能大于结束时间！");
		$("startDate").focus();
		return;
	}
	var zhiduiId = $("zhiduiId").value;
	var left = (screen.availWidth - 800) / 2;
	var top = (screen.availHeight - 500) / 2;
	
	// 分析处理方法的呼出
	var url = "policeWorks.assess.showExcel.d";
	var params = "zhiduiId=" + zhiduiId + "&startDate=" + startDate
			+ "&endDate=" + endDate+ "&sortItem="+$("assessItem").value;
	//Modify by Xiayx 2011-10-10
	//添加考核类型
	var type = $("type").value;
	if(type){
		params += "&type="+$("type").value;
	}
	//Modification finished
	url = encodeURI(url + "?" + params);
	window.open(url, "", "height=500,width=800,top=" + top + ",left="
			+ left + ",toolbar=yes,menubar=yes,"
			+ "scrollbars=yes,resizable=yes,location=no,status=no");

}
/**
 * 取得减分详细信息<br/>
 * @param {} showJgid
 * @param {} startDate
 * @param {} endDate
 */
function showOtherInfo(showJgid, startDate, endDate, showJgmc) {
	var url = "policeWorks.assess.getCutInfo.d";
	url = encodeURI(url);
	var params = "showJgid=" + showJgid + "&startDate=" + startDate
			+ "&endDate=" + endDate + "&showJgmc=" + showJgmc;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showOtherInfoResult
	});
}
/**
 * 回显减分详细信息<br/>
 */
function showOtherInfoResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
if(xmlDoc == "没有取到考核信息") {
		alert("没有取到考核信息");
	} else {
		if (!$('msg')) {
			var div = document.createElement('div');
			div.id = 'msg';
			document.body.appendChild(div);
		}
		$('msg').setStyle({
					position : 'absolute',
					zIndex : '20000',
					width : '680px',
					height : '150px',
					marginLeft : "-300px",
					marginTop : "-100px",
					background : 'white',
					border : '1px solid #000',
					left : '50%',
					top : '50%'
				})
		var str = '<div style="text-align:center;">' + xmlDoc;
		str = str + '<br/><span class="lsearch" style="margin-right:20px;"><a href="#" onclick="$(\'msg\').remove();">\
		<span class="lbl">关闭</span></a></span></div>';
		$('msg').innerHTML = str;
	}
}

window.onload = function(){
	var cjgid = $("cjgid").value;
	setAssessItems();
	fillListBox("zhiduiTdId", "zhiduiId",
					"170",
					"select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00' and substr(jgid,3,2)!='00' and jgid < '446000000000'  order by jgid",
					"全省", "resetListValue('" + cjgid + "');doAssessInfo()");
	
}


function limitStartTime(el){
	var publishTime = $("publishTime").value;
	var pstime = publishTime.substring(0,4)+"-01-01";
	if(el.value && el.value != pstime && el.value < publishTime ){
		var ptime = publishTime.replace("-","年").replace("-","月").replace("-","日");
		alert("系统提示：只提供本系统启用之后（"+ptime+"之后）的详细数据统计，您选择的时间范围不符合。");
		el.value = publishTime.substring(0,4)+"-01-01";
		el.focus();
	}
}

function typeChange(){
	setAssessItems();
}

function setAssessItems(){
	var items = {"0":"交通事故","1":"交通拥堵","2":"占道施工",
		"3":"省厅采用普遍信息","4":"部局采用工作信息","5":"省厅采用调研信息",
		"6":"部局采用调研信息","7":"拥堵报料核实","8":"信息扣分","9":"总得分"};
	var type = $("type").value;
	if(type == "1"){
		items = {"0":"交通事故","1":"交通拥堵","2":"占道施工","7":"拥堵报料核实","8":"信息扣分","9":"总得分"};
	}else if(type == "2"){
		items = {"3":"省厅采用工作信息","4":"部局采用工作信息","5":"省厅采用调研信息", "6":"部局采用调研信息","9":"总得分"};
	}
	var assessItem = $("assessItem");
	assessItem.options.length = 0;
	var option;
	for(var attr in items){
		option = document.createElement("option");
		option.text = items[attr];
		option.value = parseInt(attr) + 1;
		assessItem.add(option);
	}
	var options = assessItem.options;
	options[options.length-1].selected = true;
	assessItem.onchange = function(){
		doAssessInfo();
		$("statis").focus();
	}
}



//2011-08-12更新，刘维兴
//2011-08-14更新，刘维兴
//2011-08-31更新，刘维兴