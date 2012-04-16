var jgid;
var jgbh;
var page;
var id;
var stype;
var zbrxm;


function setGlobel(){
	jgid = $("jgid").value;
	jgbh = $("jgbh").value;
	page = $("ptype").value;
	id = $("id").value;
	stype = $("stype").value;
	zbrxm = $("zbrxm").value;
}

//根据页面类型，设置值。根据页面初始化值来设置值
function initPage(){
	$("frtime").value = new Date().format_(6);
	initOpeBtn();
	$("print").style.display = "none";
	if(page == "1"){//添加界面
		$("adTr").style.display = "inline";
		$("send").style.display="inline";
		$("cancle").style.display="inline";
	}else{
//		if(jgid=="440000000000"){
//			$("forwardTr").style.display="inline";
//		}
		$("close").style.display="inline";
	}
	CarNumber.init();
	CarType.init();
	CarColor.init();
}

var SynchFlag = {
	carNumber : false,
	carType : false,
	carColor : false
}
function setSynchFlag(attr){
	SynchFlag[attr] = true;
}

function setCarNumber(){
	$("carnumber").value = $("carnumberPre").value + $("carnumberPost").value;
}

var CarNumber = {
	init : function(){
		var sql = "select substr(dm,1,1),substr(dm,1,1) from t_sys_code where dmlb = '011006' group by substr(dm,1,1)";
		var carNumber = "carnumberPre";
		fillListBox("carnumberPreDiv", carNumber, "50", sql,"", 
			"CarNumber.callBack('"+carNumber+"')","CarNumber.onchange");
	},
	callBack:function(id){
		setSynchFlag("carNumber");
		var el = $(id);
		el.remove(el.firstChild);
		el.value = "粤";
		if(page != "1"){
			el.disabled = true;
		}
		initSetValue();
	},
	onchange : function(){
		setCarNumber();
	}
}


var CarType = {
	init : function(){
			var sql = "select dm ,dmsm　from t_sys_code where DMlb ='011001'";
			var carType = "cartype";
			fillListBox("cartypeTd", carType, "145", sql, "请选择","CarType.callBack('"+carType+"')","");
	},
	callBack:function(id){
		$("cartypeTd").innerHTML = $("carType").outerHTML
					+ "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
		setSynchFlag("carType");
		var el = $(id);
		el.remove(el.firstChild);
		if(page != "1"){
			el.disabled = true;
		}
		initSetValue();
	},
	onchange : function(){
		
	}
}

var CarColor = {
	init : function(){
			var sql = "select dm ,dmsm　from t_sys_code where DMlb ='011007'";
			var carColor = "carcolor";
			fillListBox("carColorTd", carColor, "80", sql, "", "CarColor.callBack('"+carColor+"')","CarColor.onchange");	
	},
	callBack:function(id){
		$("carColorTd").innerHTML = $("carColor").outerHTML
					+ "&nbsp;&nbsp;<font size='1' color='red'>※</font>"
		setSynchFlag("carColor");
		var el = $(id);
		el.remove(el.firstChild);
		if(page != "1"){
			el.disabled = true;
		}
		initSetValue();
	},
	onchange : function(){
		
	}
}

function initOpeBtn(){
	function getButton(id,value, method){
		return "<input id='"+id+"' type='button'  value=' "+value+" ' class='btn' style='display:none;margin:20;'  onclick='"+method+"'>";
	}
	var print = getButton("print","打 印","printWord()");
	var add = getButton("send","报 送","insert()");
	var cancel = getButton("cancle","取 消","cancle()");
	var update = getButton("update","更 新","updateXcbk()");
	var signin = getButton("signin","签 收","signin()");
	var relieve = getButton("relieve","解 除","relieve()");
	var dispatch = getButton("dispatch","转 发","showDispatch()");
	var close = getButton("close","关 闭","window.close()");
	var buttons = print+add+cancel+update+signin + relieve+dispatch+close;
	$("buttons").innerHTML = buttons;
}

function initSetValue(){
	for(var attr in SynchFlag){
		if(!SynchFlag[attr]){
			return;
		}
	}
	if(id){
		var url = "dispatch.xcbk.getXcbk.d"
		url = encodeURI(url);
		var params = {id:id,stype:stype};
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : function(xmlHttp){
				setValue(xmlHttp);
			}
		});
	}
}

var frpdcode;
function setValue(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("xcbk")[0].childNodes;
	var cols = ["id","carnumber","cartype","ct","carcolor","cc","content","lpdname",
		"lpname","lpphone","frpdcode","frpdname","frpname","apname","frtime","state","mstate",
		"adcode","adname"];
	var el;
	for(var i=0;i<results.length&&cols.length;i++){
		el = $(cols[i]);
		if(el && el.value != undefined){
			el.value = results[i].text;
		}
	}
	$("carnumberPre").value = results[1].text.substring(0,1);
	$("carnumberPost").value = results[1].text.substring(1);
	frpdcode = results[10].text;
	//remark:xml节点
	//nodeId:需要显示内容的节点
	function showAccDept(remark,nodeId){
		var accdeptstrs = [];
		for(var i=0;i<=2;i++){
			accdeptstrs[i] = "";
		}
		var accdepts = xmlDoc.getElementsByTagName(remark);
		if(accdepts != null && accdepts.length == 1){
			accdepts = accdepts[0].childNodes;
			if(accdepts != null){
				var sepName = "，";
				for(var i=0;i<accdepts.length;i++){
					accdept = accdepts[i].childNodes;
					accdeptstrs[0] += sepName + accdept[2].text;
					if(accdept[5].text == "1"){
						accdeptstrs[1] += sepName + accdept[2].text;
					}else if(accdept[5].text == "2"){
						accdeptstrs[2] += sepName + accdept[2].text;
					}
				}
				for(var i=0;i<accdeptstrs.length;i++){
					if(accdeptstrs[i] != ""){
						accdeptstrs[i] = accdeptstrs[i].substring(sepName.length);
					}
				}
			}
		}
		var adstateStr = "";
		var adscolors = ["red","green","blue"];
		var cindexs = [0,1];
		for(var i=0;i<=1;i++){
			if(accdeptstrs[i+1] != ""){
				adstateStr += sepName + "<span style='color:"+adscolors[cindexs[i]]+";'>"+accdeptstrs[i+1]+"</span>";
			}
		}
		if(adstateStr != ""){
			adstateStr = adstateStr.substring(sepName.length);
		}
		$(nodeId).innerHTML = adstateStr||"无";
	}
	
	var dstate;
	if(stype == "1"){
		$("adTr").style.display="inline";
		showAccDept("ad","adTd");
		showAccDept("dis","forwardTd");
	}else if(stype == "2"){
		$("adTr").style.display="inline";
		var rows = xmlDoc.getElementsByTagName("ad")[0].childNodes;
		dstate = rows[0].childNodes[5].text;
		showAccDept("ad","adTd");
		showAccDept("dis","forwardTd");
	}else if(stype == "3"){
		$("adTr").style.display="inline";
		showAccDept("ad","adTd");
		showAccDept("dis","forwardTd");
		dstate = xmlDoc.getElementsByTagName("dis")[0].childNodes[0].childNodes[5].text;
	}
	
	if(jgid == frpdcode && stype != "3"){
		$("adTr").style.display="inline";
		$("forwardTr").style.display="inline";
		if(page == "3"){
			$("update").style.display="inline";
			$("relieve").style.display="inline";
		}
	}else{
		$("adTr").style.display="none";
		if(page == "3"){
			$("content").disabled = true;
			if(dstate == "1"){
				$("signin").style.display="inline";
			}
		}
		$("forwardTr").style.display="inline";
		if(jgbh.length == 2){
			$("adTr").style.display="inline";
			if(page == "3"){
				$("dispatch").style.display = "inline";
			}
		}
	}
}

function insert(){
	if(!insertCheck() || !validateInput()){
		return;		
	}
	var params = {
		carnumber :"carnumber",
		cartype :"cartype",
		carcolor :"carcolor",
		content : "content",
		lpdname :"lpdname",
		lpname :"lpname",
		lpphone :"lpphone",
		frpdcode : "frpdcode",
		frpname :"frpname",
		apname : "apname",
		frtime : "frtime",
		adcode:"adcode"
	}
	
	var sparams = {};
	for(var attr in params){
		if($(params[attr]) && $(params[attr]).value){
			sparams[attr] = $(params[attr]).value;
		}else{
			sparams[attr] = "";
		}
	}
	
	var jgmcId = $("jgmcId").value;
	if(jgmcId != ""){
		jgmcId = jgmcId.replace(/；/g,",");
		jgmcId = jgmcId.substring(0,jgmcId.length-1);
	}
	sparams["adcode"] = jgmcId;
	
	var url = "dispatch.xcbk.addXcbk.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : sparams,
		onComplete : function(xmlHttp){
			var result = showOpeResponse(xmlHttp,"添加协查通报信息");
			if(result == "true"){
				window.location.reload();
			}
		}
	});
}

function insertCheck() {
	var reg = /^[a-zA-Z][0-9a-zA-Z\*]{5}$/;
	var cnpost = $("carnumberPost");
	if (cnpost.value == "") {
		alert("请输入车牌号！");
		cnpost.focus();
		return false;
	} else if (!reg.test(cnpost.value)) {
		alert("车牌号码格式不正确！");
		cnpost.focus();
		return false;
	}
	
	var content = $("content");
	if (content.value == "") {
		alert("请输入协查通报内容！");
		content.focus();
		return false;
	}
	
	var lpdname = $("lpdname");
	if (lpdname.value == "") {
		alert("请输入协查联系人单位名称！");
		lpdname.focus();
		return false;
	}
	var lpname = $("lpname");
	if (lpname.value == "") {
		alert("请输入协查联系人姓名 ！");
		lpname.focus();
		return false;
	}
	var reg1 = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(^[0-9]{11}$)/;
	var lpphone = $("lpphone");
	if (lpphone.value == "") {
		alert("请输入协查联系人电话！");
		lpphone.focus();
		return false;
	} else if(!reg1.test(lpphone.value)) {
		alert("联系人电话格式不正确！");
		lpphone.focus();
		return false;
	}
	var frpname = $("frpname");
	if (frpname.value == "") {
		alert("请输入填报人姓名 ！");
		frpname.focus();
		return false;
	}
	
	var apname = $("apname");
	if (apname.value == "") {
		alert("请输入审核人姓名 ！");
		apname.focus();
		return false;
	}
	
	var jgid = $("jgmcId");
	if(jgid.value==""){
		alert("请选择接收单位！");
		$("adImg").focus();
		return false;
	}
	
	if(!validateInput()) {
		return;
	}
	
	return true;
}

function updateXcbk(){
	if(!updateCheck() || !validateInput()){
		return;
	}
	
	var params = {
		id :"id",
		content : "content"
	}
	
	var sparams = {};
	for(var attr in params){
		if($(params[attr]) && $(params[attr]).value){
			sparams[attr] = $(params[attr]).value;
		}else{
			sparams[attr] = "";
		}
	}
	
	var url = "dispatch.xcbk.updateXcbk.d";
	url = encodeURI(url);
	new Ajax.Request(url, {
		method : "post",
		parameters : sparams,
		onComplete : function(xmlDoc){
			var result = showOpeResponse(xmlDoc,"更新协查通报信息");
			if(result == "true"){
				window.close();
			}
		}
	});
}

function updateCheck(){
	var content = $("content");
	if (content.value == "") {
		alert("请输入协查通报内容！");
		content.focus();
		return false;
	}
	return true;
}

function signin(){
	Flow.updtState({id:id,state:"2",rpname:zbrxm},"签收协查通报",true);
}

function showDispatch(){
	var parents = Dept.getParents(frpdcode);
	if(parents){
		frpdcode += "," + parents;
	}
	Flow.showDispatch(id,(frpdcode||jgid),"jgmcId",185,187);
}

function relieve(){
	if (confirm("确定解除协查通报?")) {                                       
		var strUrl = "dispatch.xcbk.cancleXcbk.d";
		strUrl = encodeURI(strUrl);
		var params = "id=" + id;
		new Ajax.Request(strUrl, {
			method : "post",
			parameters : params,
			onComplete : function(xmlHttp){
				var result = showOpeResponse(xmlHttp,"解除协查通报");
				if(result == "true"){
					window.close();
				}
			}
		});
	}
}

function cancle(){
	document.forms[0].reset();
	$("frtime").value = new Date().format_(6);
}

function showOpeResponse(xmlHttp,msg){
	var result = xmlHttp.responseText;
	var rdesc;
	if(result == "true"){
		rdesc = "成功";
	}else if(result == "false"){
		rdesc = "失败";
	}
	alert(msg+rdesc+"！");
	return result;
}


function printWord(){
	window.print();
}

//2011-11-2更新，雷适兴

