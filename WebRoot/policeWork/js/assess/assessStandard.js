/**
 * 初始化考核标准设置画面<br/>
 */
function initAssessStandardInfo() {
	$("acc_001024").focus();
	var url = "policeWorks.assess.getAssessStandardInfo.d";
	url = encodeURI(url);
	var params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showInitResult});
}

function showInitResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0].getElementsByTagName("col");
	// 事故的评分
	$("acc_001024").value = results[0].text;
	// 拥堵的评分
	$("crowd_001002").value = results[1].text;
	// 占道施工
	$("build_001023").value = results[2].text;
	// 新闻材料
	$("news_001004").value = results[3].text;
	// 新闻材料加分
	$("news_001006").value = results[4].text;
	// 报料采用
	$("baoliao_001001").value = results[5].text;
	// 报料未核实
	$("baoliao_001005").value = results[6].text;
	// 扣分
	$("kou_001003").value = results[7].text;
	// 报料不采用扣分
	$("baoliao_001007").value = results[8].text;
	// 调研信息材料
	$("news_001008").value = results[9].text;
	// 调研信息材料
	$("news_001009").value = results[10].text;
}
/**
 * 新增或更新考核标准<br/>
 */
function doModifyAssessStandard() {
	
	// 事故的评分
	var acc_001024 = $("acc_001024").value;
	// 拥堵的评分
	var crowd_001002 = $("crowd_001002").value;
	// 占道施工
	var build_001023 = $("build_001023").value;
	// 新闻材料
	var news_001004 = $("news_001004").value;
	// 新闻材料加分
	var news_001006 = $("news_001006").value;
	// 报料采用
	var baoliao_001001 = $("baoliao_001001").value;
	// 报料不采用
	var baoliao_001007 = $("baoliao_001007").value;
	// 报料未核实
	var baoliao_001005 = $("baoliao_001005").value;
	// 扣分
	var kou_001003 = $("kou_001003").value;
	// 调研信息材料被省厅采用
	var news_001008 = $("news_001008").value;
	// 调研信息材料被部局采用
	var news_001009 = $("news_001009").value;
	
	// 呼出录入项目check方法
	if(checkValue()) {
		return;
	}
	if (!confirm("是否确认提交？")) {
		return;
	}
	var url = "policeWorks.assess.modifyAssessStandard.d";
	url = encodeURI(url);
	var params = "acc_001024=" + acc_001024 + "&crowd_001002=" + crowd_001002 + "&build_001023=" + build_001023 +
		"&news_001004=" + news_001004 + "&news_001006=" + news_001006 + "&baoliao_001001=" + baoliao_001001
		+ "&baoliao_001005=" + baoliao_001005+ "&kou_001003=" + kou_001003 + "&baoliao_001007=" + baoliao_001007
 		+ "&news_001008=" + news_001008+"&news_001009="+news_001009;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyResult});
}

function checkValue() {
	if($('acc_001024')) {
		if(!$('acc_001024').value){
			alert("请填写交通事故考核分值！");
			$('acc_001024').focus();
			return true;
		}
	}
	if($('crowd_001002')) {
		if(!$('crowd_001002').value){
			alert("请填写交通拥堵考核分值！");
			$('crowd_001002').focus();
			return true;
		}
	}
	if($('build_001023')) {
		if(!$('build_001023').value){
			alert("请填写道路施工考核分值！");
			$('build_001023').focus();
			return true;
		}
	}
	if($('news_001004')) {
		if(!$('news_001004').value){
			alert("请填写新闻材料被省厅采用考核分值！");
			$('news_001004').focus();
			return true;
		}
	}
	if ($('news_001006')) {
		if (!$('news_001006').value) {
			alert("请填写新闻材料被部局采用考核分值！");
			$('news_001006').focus();
			return true;
		}
	}
	if($('news_001008')) {
		if(!$('news_001008').value){
			alert("请填写调研信息材料被省厅采用考核分值！");
			$('news_001008').focus();
			return true;
		}
	}
	if($('news_001009')) {
		if(!$('news_001009').value){
			alert("请填写调研信息材料被部局采用考核分值！");
			$('news_001009').focus();
			return true;
		}
	}
	
	if($('baoliao_001001')) {
		if(!$('baoliao_001001').value){
			alert("请填写拥堵报料信息采用考核分值！");
			$('baoliao_001001').focus();
			return true;
		}
	}
	if($('baoliao_001005')) {
		if(!$('baoliao_001005').value){
			alert("请填写报料超时未核实考核分值！");
			$('baoliao_001005').focus();
			return true;
		}
	}
	if($('baoliao_001007')) {
		if(!$('baoliao_001007').value){
			alert("请填写报料未准确核实考核分值！");
			$('baoliao_001007').focus();
			return true;
		}
	}
	if($('kou_001003')) {
		if(!$('kou_001003').value){
			alert("请填写漏报，错报考核分值！");
			$('kou_001003').focus();
			return true;
		}
	}
}

function showModifyResult (xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if(xmlDoc.indexOf('success')>-1) {
		alert("评估基准设置成功！");
	} else {
		alert("评估基准设置失败！");
	} 
}

/**
 * 是否数字见按下的检查<br/>
 * @param {} ele
 * @param {} event
 */
function checkNum(ele,event){
	if(event.keyCode < 48 || event.keyCode > 57){
		event.keyCode=0;
		return;	
	}
	$(ele).style.color = "ff0000";
}

/**
 * 重置提示内容<br/>
 */
function resetValue () {
	// 事故的评分
	$("acc_001024").value = "";
	// 拥堵的评分
	$("crowd_001002").value = "";
	// 占道施工
	$("build_001023").value = "";
	// 新闻材料
	$("news_001004").value = "";
	// 新闻材料加分
	$("news_001006").value = "";
	// 报料采用
	$("baoliao_001001").value = "";
	// 报料未核实
	$("baoliao_001005").value = "";
	// 报料未采用
	$("baoliao_001007").value = "";
	// 扣分
	var kou_001003 = $("kou_001003").value = "";
	// 调研信息材料
	$("news_001008").value = "";
	$("news_001009").value = "";
	$('acc_001024').focus();
}