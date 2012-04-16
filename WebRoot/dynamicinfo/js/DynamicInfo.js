window.onload = function(){
	loadFunc();
	showNode();
	initDynamicInfo();
	synchDynamicInfo();
};

var vidiconmap = new VidiconInfoMap(); 
function initDynamicInfo(){
//	$("chkTfm").checked = true;
//	getTfmHtml();   //省际卡口
//	TfmMap.prototype.readRoadFlow();
//	for(var i=0;i<1000;i++){
//		i = i;
//	}
//	//sleep(1000);
//   	//添加摄像机标注工具 
//   
//    $("chkTgsFlow").checked = true;
//    getTGSFlowHtml();  //拥堵信息
//    $("chkAcc").checked = true;
//    getAccNowHtml();  //当日事故
//    $("chkRoadBuild").checked = true;
//    getroadBuildHtml(); //施工占道
//    for(var i=0;i<1000;i++){
//		i = i;
//	}
//    $("chkForbid").click();
    //$("chkForbid").checked=true;
    //getPoliceRemindHtml(); //消息提示
   setDefaultChk();
   setTimeout(function(){$("full_Img").click();},1000);
   //初始进入10秒后自动打开列表界面
   setTimeout(openList, 1000*10);
   
        //读取摄像机信息    
    //vidiconmap.readVidiconPoints();
}



/** 打开右侧列表 */
function openList() {
	var tgs = $("tgs");
	if (tgs) {
		if (tgs.nodeName == "DIV") {
			tgs = tgs.firstChild;
		}
		var src = tgs.src;
		if (src) {
			var temp = src.split("/");
			src = temp[temp.length - 1];
			if (src == "deviceup.gif") {
				tgs.click();
			}
		}
	}
}



var isShowTfmResult = true;
function tfm() {
	if (isShowTfmResult) {
		getTfmHtml();
	} else {
		isShowTfmResult = true;
	}
	TfmMap.prototype.readRoadFlow();
}

var isShowPRResult = true;
function policeRemind() {
	if (isShowPRResult) {
		//Modified by Liuwx 2011-8-31
		getPoliceRemindHtml(G_Filter_params);
		//Modification finished
	} else {
		isShowPRResult = true;
	}
}
//获取分页容器ID，不显示时设置ID为""
function getPCId(isShow){
//	return isShow?"daynameicInfo":"";
	return isShow?"daynameicInfo":"daynameicInfo";
}
//chkAcc chkRoadBuild chkForbid chkTfm chkTgsFlow chkPCS
//accNow当日事故 roadBuild施工占道 forbid信息提示 tfm拥堵 tgsflow省际出入口 pcs巡逻警车
//路面动态6项的复选框ID
var itemCkbxs = [ "chkAcc", "chkRoadBuild", "chkForbid", "chkTfm", "chkTgsFlow", "chkPCS"];
var itemShows = ["isShowAccResult","isShowRdbdResult","isShowPRResult","isShowTfmResult","isShowTGSResult","isShowPCSResult"];
var itemMNames = [getAccNowHtml,getroadBuildHtml,policeRemind,tfm,getTGSFlowHtml,getPCSHtml];
//路面动态6项的描述ID、描述前缀、描述颜色标记
var itemLbls = [],itemLblPrfx="show",itemLblCm="#ff0000";

/**
 * 刷新路面动态信息
 * 获取所有选中的复选框
 */
function refreshDynamicInfo(){
	//当前复选框、当前描述、最后选中的复选框索引
	var itemCkbx,itemLbl,lastItemIndex=-1;
	for(var i=0;i<itemCkbxs.length;i++){
		itemCkbx = $(itemCkbxs[i]);
		if(itemCkbx && itemCkbx.checked){
			eval(itemShows[i] + " = false");
			itemLbl = $(itemLblPrfx+(i+1));
			if (itemLbl && itemLbl.style.color == itemLblCm) {
				lastItemIndex = i;
			}else{
				itemMNames[i]();
			}
		}
	}
	if(lastItemIndex!=-1){
		eval(itemShows[lastItemIndex]+" = true");
		setTimeout(function(){itemMNames[lastItemIndex]()},100);
	}
}

/**
 * 同步路面动态信息
 */
var timerId;//计时器ID
function synchDynamicInfo(){
	timerId = timerId || setInterval(function(){
        //Modified By Liuwx 2012年3月13日18:17:54
        //刷新交警提示
        policeRemind();
        //Modification finished
		refreshDynamicInfo();
	},1000*60);
}

function stopSynchDynamicInfo(){
	if(timerId){
		clearInterval(timerId);
		timerId = null;
	}
}

//功能编号到节点复选框id的映射
var funcToChk = {
	"590100" : "chkTgsFlow",
	"590200" : "chkPCS",
	"590300" : "chkAcc",
	"590400" : "chkTfm",
	"590500" : "chkRoadBuild",
	"590600" : "chkForbid"
};
// 加载用户的功能，根据功能设置对应的复选框id
var $funcs;
function loadFunc() {
	var operate = $("operate").value;
	var objects = {}, attr, func;
	$funcs = operate.split(";");
	for ( var i = 0; i < $funcs.length; i++) {
		func = $funcs[i];
		index = func.indexOf(",");
		attr = func.substring(0, index);
		objects[attr] = func.substring(index + 1);
	}
	$funcs = objects;
//	chks = [];
//	for ( var attr in funcToChk) {
//		chks.push(funcToChk[attr]);
//	}
//	chks = chks.join(";");
}
// 控制操作
function showNode() {
	var object = {
		defaultChks : defaultChks,
		itemCkbxs : itemCkbxs,
		funcToChk : funcToChk
	};
	var privi = PriviFactory.getPrivi("showNode");
	for ( var attr in funcToChk) {
		privi.func = attr;
		privi.ctrl(window.$funcs,object);
	}
}

var defaultChks = [ "chkTfm", "chkTgsFlow", "chkAcc","chkRoadBuild" , "chkForbid"];
function setDefaultChk() {
	for (var i = 0; i < defaultChks.length; i++) {
		$(defaultChks[i]).click();
	}

	setTimeout(function(){
//		$(defaultChks[defaultChks.length-1]).click();
        //Modified By Liuwx 2012年3月14日17:11:07
        //默认不显示信息
        $("show2").style.color = "";
        DynamicInfoMap.prototype.hideInfoList();
        //Modification finished
	},500);
}


function sleep(timeout) {
	window.showModalDialog("javascript:document.writeln('<script>window.setTimeout(function () { window.close(); }, "
			+ timeout + ");<\/script>');");
}

/**
 * 
	* 方法名称：showTodayInfo<br>
	* 方法描述： 显示更多专题图 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-1-5 下午8:49:34  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-1-5 下午8:49:34   <br>
	* 修改备注：    <br>
 */
function showTodayInfo(){
	window.showModalDialog("../../../general/ehl/alarmScanWord.jsp","", "dialogWidth:840px;dialogHeight:510px");
}