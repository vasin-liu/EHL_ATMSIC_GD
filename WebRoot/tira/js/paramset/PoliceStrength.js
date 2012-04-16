var roadLength;
var policeStrength;

/**
 * 根据道路等级，取得道路列表
 * @return {TypeName} 
 */
function getRoad() {
	if (!baseCheck( [ "deptId", "roadTypeCodeSelId" ], true)) {
		return;
	}
	var deptId = $("deptId").value;
	var roadLevel = $("roadTypeCodeSelId").value;
	var sql = "select roadid,roadname from T_SA_ROAD_DEPT_ACC_ALARM "
			+ "where dayid=to_date('1950-01-01','yyyy-mm-dd') and deptid='"
			+ deptId + "' and roadlevel=" + roadLevel;
	fillListBox("roadCodeTdId", "roadCodeSelId", "100", sql, "",
			"getPoliceStrength()", "", "");
}

/**
 * 根据机关编号和道路编号，取得今天的道路长度和警力部署
 * @return {TypeName} 
 */
function getPoliceStrength() {
	var roadCodeSel = $("roadCodeSelId");
	roadCodeSel.removeChild(roadCodeSel.firstChild);
	if (!baseCheck( [ "deptId", "roadCodeSelId" ], true)) {
		return;
	}

	var deptId = $("deptId").value;
	var roadId = $("roadCodeSelId").value;
	var params = "deptId=" + deptId + "&roadId=" + roadId;

	var url = "sa.PoliceStrengthAction.getPoliceStrength.d";
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showPoliceStrengthResponse
	});

}


/**
 * 将道路长度和部署警力显示在相应的文本框内
 * @param {Object} xmlHttp
 */
function showPoliceStrengthResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	roadLength = xmlDoc.split(",")[0];
	policeStrength = xmlDoc.split(",")[1];
	$("roadLengthCodeInputId").value = roadLength;
	$("policeStrengthCodeInputId").value = policeStrength;
}

/**
 * 设置警力
 * @param {Object} date
 * @return {TypeName} 
 */
function setPoliceStrength(date) {
	
	var roadLengthInner = $("roadLengthCodeInputId").value;
	var policeStrengthInner = $("policeStrengthCodeInputId").value;
	if (roadLengthInner == roadLength && policeStrengthInner == policeStrength) {
		alert("尚未进行设置")
		return;
	}
	var deptId = $("deptId").value;
	var roadId = $("roadCodeSelId").value;
	var params = "deptId=" + deptId + "&roadId=" + roadId + "&roadLength="
			+ roadLengthInner + "&policeStrength=" + policeStrengthInner + "&date="+date;
	var url = "sa.PoliceStrengthAction.setPoliceStrength.d";
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showSetResultResponse
	});
}

/**
 * 显示设置结果
 * @param {Object} xmlHttp
 */
function showSetResultResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	alert(xmlDoc);
}
