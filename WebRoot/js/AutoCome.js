//自动进入系统
function autoCome() {
	adjustLct();
	setModel();
	timerStart();
	window.onresize = adjustLct;
}
//调整提示信息位置
function adjustLct() {
	var aci = $("autoComeIndex");
	//function px(v){return Number(v.replace("px",""))};
	var wf = 1280, hf = 636;
	var w = document.body.offsetWidth;
	var h = document.body.offsetHeight;
	var b = 65, r = 50;
	with (aci) {
		bottom = b / wf * w;
		right = r / hf * h;
	}
}
//设置模块名
//var appid = "<%=appid%>";
function setModel() {
	//$("subSystem").innerText = appid=="1001"?"交通警情":"路面动态";
	$("subSystem").innerText = "路面动态";
}
//进入子系统
function comeToIndex() {
	//var subsys = appid=="1001"?"dispatch":"sm";
	var subsys = "sm";
	subsys = $(subsys);
	if (subsys) {
		subsys.click();
	}
}
//倒计时
var waitSec = 30;
function countDown() {
	if (waitSec == 0) {
		comeToIndex();
		return;
	}
	$("waitSec").innerText = waitSec;
	waitSec--;
	window.timer = setTimeout("countDown()", 1000);
}

//启动计时器
function timerStart(){
	window.timerState = "start";
	countDown();
}

//暂停计时器
function timerStop(){
	window.timerState = "stop";
	window.clearTimeout(window.timer);
}

//控制计时器
function timerControl(){
	if(window.timerState == "start"){
		timerStop();
	}else if(window.timerState == "stop"){
		timerStart();
	}
}
