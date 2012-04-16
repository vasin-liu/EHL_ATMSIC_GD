/**警情项目*/
var items = ["交通事故","交通拥堵","施工占道","拥堵报料","信息文件","调研文件"];
/**报送类型*/
var bstypes = ["未上报","已上报"];
/**评分类型*/
var pftypes = ["扣分","加分"];
/**原因类型*/
var rtypes = ["漏报","瞒报","迟报","错报","超时未更新","超时未核实"];
/**关系表格*/
function Relation(item,bstypes,pftypes,rtypes){
	this.item = item;
	this.bstypes = bstypes;
	this.pftypes = pftypes;
	this.rtypes = rtypes;
}

var accident = new Relation(items[0],bstypes,pftypes,rtypes);



var data = {
	alarmType :[
			[ [ 4, "报送信息被部交管局采用" ] ],
			[ [ 1, "错报漏报重特大交通事故信息" ], [ 2, "错报漏报交通拥堵信息" ], [ 3, "错报漏报施工占道信息" ],// [ 5, "超时未核实报料" ],
					[ 6, "未准确回应报料信息" ] ] ]
}


function setOptions(select, options){
	for ( var i = 0; i < options.length; i++) {
		var option = document.createElement("option");
		option.value = i+1;
		option.label = options[i];
		select.add(option);
	}
	options = select.options;
	for ( var i = 0; i < options.length; i++) {
		options[i].innerHTML = options[i].label;
	}
}

function setReason(){
	var reasons = $("reasons");
	var reasonLabel = reasons.options[reasons.selectedIndex].label;
	var items = $("items");
	var itemLabel = items.options[items.selectedIndex].label;
	$("reason").innerHTML = reasonLabel + itemLabel + "扣分！";
}

function initItems(value){
	var items = ["交通事故","交通拥堵","施工占道"];
	var itemsEl = $("items");
	setOptions(itemsEl,items);
	itemsEl.value = value || "1";
	itemsEl.onchange = function(){
		initReasons(this.value);
		setReason();
	}
}

function initReasons(value) {
	var reasons = [ "漏报", "错报" ];
	if(value == "4"){
		reasons = ["未准确回应"];
	}
	var reasonsEl = $("reasons");
	reasonsEl.options.length = 0;
	setOptions(reasonsEl, reasons);
	reasonsEl.onchange = function(){
		setReason();
	}
}

window.onload = function(){
	initPage();
	setDefValue();
}

//初始化页面元素
function initPage() {
	var value = "1";
	initItems(value);
	initReasons(value);
	setReason();
}

//初始化默认值
function setDefValue() {
	var date = new Date();
	$("date").value = date.format_(5);
}

function showAlarmTypeDesc(){
	return;
	var typeData = data.alarmType;//警情加减分类型分为加分类型、减分类型；加分类型包括.....些项，减分类型包括......些项。
	var typeDescs = ["加分类型","减分类型"];//警情类型描述
	var descPost = "包括：";//拼接字符串描述后缀
	var typeStr = "";//警情类型描述字符串
	var typeSep = "；";//类型分隔符
	var itemSep = "、";//类型中项目分隔符
	for(var i=0;i<typeDescs.length;i++){
		typeStr += typeSep + typeDescs[i] + descPost;
		var itemStr = "";
		for(var j=0;j<typeData[i].length;j++){
			itemStr += itemSep + typeData[i][j][1];
		}
		if(itemStr != ""){
			itemStr = itemStr.substr(itemSep.length);
		}
		typeStr += itemStr;
	}
	typeStr = typeStr.substr(typeSep.length);
	$("alarmTypeDesc").innerText = typeStr; 
}

function showAddMinus(e){
	var type = Number(e.value)-1;
	var name = e.name;
	var label = $(e.id+"Label").innerText;
	$(name + "Span").innerText = label;
	var addMinus = data.alarmType;
	var select = $(name + "Select");
	select.length = 0;
	var options = addMinus[type];
	for ( var i = 0; i < options.length; i++) {
		var option = document.createElement("option");
		option.value = options[i][0];
		option.label = options[i][1];
		select.add(option);
	}
	var options = select.options;
	for(var i=0;i<options.length;i++){
		options[i].innerHTML = options[i].label;
	}
	$("type").value = select.value;
	showDefReason(select,e.value);
}

//取得页面元素k->v
function getParams() {

	var params = ["jgid", "jgmc", "items","reasons", "reason", "date" ];
	var paramj = {};
	for ( var i = 0; i < params.length; i++) {
		if ($(params[i]) && $(params[i]).value != undefined) {
			paramj[params[i]] = $(params[i]).value;
		} else {
			return null;
		}
	}
	return paramj;

}

//提交请求前的验证
function submitCheck(params) {
	if (params.jgid.substring(2,4) == "00") {
		alert("机构不能是总队！");
		$("jgmc").focus();
		return false;
	}

	if (params.reason == "") {
		alert("扣分理由不能为空！");
		$("reason").focus();
		return false;
	}
	return true;
}

//字符过滤
function filterChar(params) {
	return;
	var specialChars = [[/ /g,"&nbsp;"],[/\n/g,"<br>"]];
	params.reason = params.reason.replace(/'/g, "''");
	for(var i=0;i<specialChars.length;i++){
		params.reason = params.reason.replace(specialChars[i][0],specialChars[i][1]);
	}
}

//提交请求
function submit() {
	var params = getParams();
	if (params == null) {
		alert("页面参数项缺失！");
		return;
	}
	
	var isSuccess = submitCheck(params);
	if (isSuccess == true) {
		filterChar(params);
		var url = "policeWorks.assess.scoreRecordEdit.d";
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : submitResponse
		});
	}
}

//提交请求后的反馈结果
function submitResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if (xmlDoc == "success") {
		alert("添加扣分成功！");
		window.location.reload();
	} else if (xmlDoc == "failed") {
		alert("添加扣分失败！");
	} else {
		window.location.href = "";//action找不到链接error页面
	}
}


function showDefReason(e){
	var label = e.options[e.selectedIndex].label;
	var profix = "文件名：X X X";
	var reline = "\n";
	function add(type){
		return profix + reline
		+ "信息被部交管局网站采用";
	}
	function minus(type){
		return "漏报"+type+"信息";
	}
	function def(type){
		return type;
	}
	var shows = [add,minus,def];
	var sindexs = [1,1,1,0,2,2];
	$("reason").innerText = shows[sindexs[Number(e.value)-1]](label);
}

var Radio = {
	get : function(name){
		var radio = null;
		var radios = document.getElementsByName(name);
		if(radios != null){
			for(var i=0;i<radios.length;i++){
				if(radios[i].checked == true){
					radio = radios[i];
					break;
				}
			}
			if(radio == null){
				//alert("Radio.get(name):单选按钮组中没有选中的任何一个！");
			}
		}else{
			//alert("Radio.get(name):没有指定名称的单选按钮！");
		}
		return radio;
	},
	getValue : function(name){
		var value = null;
		var radio = Radio.get(name);
		if(radio != null){
			value = radio.value;
		}
		return value;
	}
}