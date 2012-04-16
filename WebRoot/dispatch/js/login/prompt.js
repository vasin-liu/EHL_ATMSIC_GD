//Modified by Liuwx 2011-9-6
//全局的错误计数器
var errorCounter = 0;
//Modification finished
/**
 * 注册事件
 * @param element 注册事件触发的元素对象，如：指定的div
 * @param eventType 事件类型,如：click
 * @param listener  事件函数,自定义
 * @return 注册结果
 * @since
 */
function addEvent(element, eventType, listener) {
	var argument = new Array();
	var fun = listener;
	if (!(element = $(element))) {
		return false;
	}
	if (typeof (element) == 'string') {
		return $(element);
	}
	if (arguments.length > 3) {
		for (i = 3; i < arguments.length; i++) {
			argument[i - 3] = arguments[i];
		}
		fun = function(event) {
			listener.apply(event, argument);
		}
	}
	if (element.addEventListener) {
		// W3C method
		element.addEventListener(eventType, fun, false);
		return true;
	} else if (element.attachEvent) {
		// MSIE method
		element['e' + eventType + listener] = fun;
		element[eventType + listener] = function() {
			element['e' + eventType + listener](window.event);
		}
		element.attachEvent('on' + eventType, element[eventType + listener]);
		return true;
	}
	return false;
}

/**
 * 中断事件流
 * @param e 事件产生时，自动生成的对象event
 * @since
 */
function stopEvent(e) {
	if (e.stopPropagation) {
		e.stopPropagation();
	} else {
		e.cancelBubble = true;
	}
	if (e.preventDefault) {
		e.preventDefault();
	} else {
		e.returnValue = false;
	}
}

/**
 * 取消注册事件
 * @param element 注册事件触发的元素对象，如：指定的div
 * @param eventType 事件类型,如：click
 * @param listener  事件函数,自定义
 * @return 取消注册结果
 * @since
 */
function removeEvent(element, eventType, listener) {
	if (!(element = $(element)))
		return false;
	if (element.removeEventListener) {
		element.removeEventListener(eventType, listener, false);
		return true;
	} else if (element.detachEvent) {
		// MSIE method
		element.detachEvent('on' + eventType, element[eventType + listener]);
		element[eventType + listener] = null;
		return true;
	}
	return false;
}

/**
 * 模拟实现拖拽功能
 * @author dxn
 * @param target 将拖动的对象id或对象
 * @param dragElement 鼠标事件触发的对象，可以为target或target上的某一部分
 * @return 初始化事件注册及初始值
 * @since 
 */
function drag(target, dragElement) {
	var preElementPoint = {};
	var mouseDownPoint = {};
	var dragAble = false;
	var _self = this;
	function init() {
		if (typeof (target) == 'string') {
			target = $(target);
		}
		if (!dragElement) {
			dragElement = target;
		} else if (typeof (dragElement) == 'string') {
			dragElement = $(dragElement);
		}
		addEvent(dragElement, 'mousedown', startDrag);
		addEvent(document, 'mousemove', dragging);
		addEvent(document, 'mouseup', endDrag);
		_self.destory = destoryDray; //注销事件，让不再能拖动
	}
	function startDrag(event) {
		target.style.position = "absolute";
		event = event || window.event;
		document.documentElement.style.cursor = 'move';
		var point = target.viewportOffset();
		preElementPoint.top = point.top;
		preElementPoint.left = point.left;
		mouseDownPoint.x = event.clientX;
		mouseDownPoint.y = event.clientY;
		dragAble = true;
	}
	function dragging(event) {
		event = event || window.event;
		if (!dragAble) {
			return;
		}
		if (event.clientX < 0 || event.clientY < 0) {
			return;
		}
		target.style.left = preElementPoint.left
				+ (event.clientX - mouseDownPoint.x) + 'px';
		target.style.top = preElementPoint.top
				+ (event.clientY - mouseDownPoint.y) + 'px';
	}
	function endDrag(event) {
		if (!dragAble) {
			return;
		}
		stopEvent(event);
		dragAble = false;
		document.documentElement.style.cursor = 'default';
	}
	function destoryDray() {
		removeEvent(dragElement, 'mousedown', startDrag);
		removeEvent(document, 'mousemove', dragging);
		removeEvent(document, 'mouseup', endDrag);
	}
	return init();
}

/*****************************************以上为工具类**********************************/
/********注意：需要prototype.js 1.6.1 以上版本支持，本js文件在prototype.js后加载*************/

/* 全局变量 */
var baseUrl = ""; //基础位置，文件目录改变会影响到链接的位置
var imgUrl = "dispatch/images/dispatch/bg_img.gif";//图片位置
var prompt_title = "交通警情系统消息提示";//标题
var plistArr = new Array();//以此数组保存各待办事项的名称和统计数字
var countActions = new Array();//以此数组配置各种用户需要弹出提示的详细信息
var feedNewsDesc = "需处理拥堵信息";
if (jglx == '1') {
	feedNewsDesc = "核实拥堵信息";
} else if (jglx == '2') {
	feedNewsDesc = "核实拥堵信息";
}
/**countActions 数组的对象说明：
 * url,param : 获取待办事项统计数字的action位置和参数
 * prj : 数字保存各种状态id和名称
 * info_url,info_param_k,info_param_v : 详细信息的action位置及参数，其中info_param_v 在打开详细列表的时候赋值。
 * info_td,info_td_width : 表头的名字和宽度，其中宽度总和为 80。
 **/
countActions
		.push( {
			title : "交通事故信息",
			url : "dispatch.cmaterialInfo.getPromptCount.d",
			params : "",
			prj : [ {
				id : '000000',
				name : '交通事故信息'
			}, {
				id : '000000',
				name : '事故续报信息'
			}, {
				id : '000000',
				name : '事故通报信息'
			} ],
			info_url : "dispatch.cmaterialInfo.getPromptInfo.d,dispatch.cmaterialInfo.getPromptXBInfo.d,dispatch.cmaterialInfo.getPromptTbInfo.d",
			info_param_k : "reportkind=",
			info_param_v : "",
			info_td : [ [ '填报单位', '事故标题', '填报人', '填报时间', '状态' ],
					[ '填报单位', '事故标题', '填报人', '填报时间', '状态' ],
					[ '填报单位', '事故标题', '填报人', '填报时间', '状态' ] ],
			info_td_width : [ [ 12, 12, 12, 10, 14, 10 ],
					[ 12, 12, 12, 10, 14, 10 ], [ 12, 12, 12, 10, 14, 10 ] ],
			dialog_url : "dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?insrtOrUpdt=1&alarmId=",
			dialog_params : "dialogWidth:780px;dialogHeight:570px"
		});
countActions
		.push( {
			title : "交通拥堵信息",
			url : "crowd.crowdmanage.getPromptCrowdCount.d",
			params : "",
			prj : [ {
				id : '',
				name : '拥堵待更新信息'
			}, {
				id : '',
				name : '交通拥堵信息'
			}, {
				id : '',
				name : '拥堵信息更新'
			}, {
				id : '',
				name : '拥堵解除'
			}, {
				id : '',
				name : '待核实拥堵信息'
			}, {
				id : '',
				name : '已核实拥堵信息'
			} , {//Modified by Liuwx 2012年3月16日18:01:34
                id : '',
                name : '拥堵超时未更新信息'
            }], //Modification finished
			info_url : "crowd.crowdmanage.getPromptCrowdInfo.d,crowd.crowdmanage.getPTroubCrowdInfo.d?mstate=1,crowd.crowdmanage.getPTroubCrowdInfo.d?mstate=2,"
					+ "crowd.crowdmanage.getPTroubCrowdInfo.d?mstate=3,dispatch.feedNews.getPromptInfo.d,dispatch.feedNews.getPromptInfo.d?type=1,"
                    + "crowd.crowdmanage.getOutToDateCrowdInfo.d",//Modified by Liuwx 2012年3月17日16:36:08
			info_param_k : "",
			info_param_v : "",
			info_td : [ [ '填报单位', '道路名称', '路段名称', '拥堵原因', '拥堵开始时间', '处理状态' ],
					[ '填报单位', '道路名称', '路段名称', '拥堵原因', '拥堵开始时间', '处理状态' ],
					[ '填报单位', '道路名称', '路段名称', '拥堵原因', '拥堵开始时间', '处理状态' ],
					[ '填报单位', '道路名称', '路段名称', '拥堵原因', '拥堵开始时间', '处理状态' ],
					[ '道路名称', '方向', '起止里程', '路况', '路况原因', '处理状态' ],
					[ '道路名称', '方向', '起止里程', '路况', '路况原因', '处理状态' ],
                    [ '道路名称','路段名称', '报送单位', '发生时间', '更新时间' ]  ],//Modified by Liuwx 2012年3月17日16:36:08
			info_td_width : [ [ 12, 12, 12, 10, 14, 10 ],
					[ 12, 12, 12, 10, 14, 10 ], [ 12, 12, 12, 10, 14, 10 ],
					[ 12, 12, 12, 10, 14, 10 ], [ 12, 15, 18, 10, 12, 14 ],
					[ 12, 15, 18, 10, 12, 14 ],[ 18, 14, 14, 18, 18 ] ], //Modified by Liuwx 2012年3月17日16:36:08
			dialog_url : "dispatch/ehl/cpoliceedit/TrafficCrowdInfoFree.jsp?insrtOrUpdt=1&alarmId=",
			dialog_params : "dialogWidth:850px;dialogHeight:400px"
		});
//Modified by Liuwx 2011-06-24									
//新增施工占道的提醒功能									
countActions.push( {
	title : "施工占道",
	url : "roadbuild.roadbuildmanage.searchCounts.d?atype=3&state=1",
	params : "",
	prj : [ {
		id : '',
		name : '新增施工占道信息'
	}, {
		id : '',
		name : '更新施工占道信息'
	}, {
		id : '',
		name : '解除施工占道信息'
	}, {
		id : '',
		name : '施工占道信息更新提醒'
	} ],
	info_url : "roadbuild.roadbuildmanage.searchRoadBuild.d?mstate=1,"
			+ "roadbuild.roadbuildmanage.searchRoadBuild.d?mstate=2,"
			+ "roadbuild.roadbuildmanage.searchRoadBuild.d?mstate=3,"
			+ "roadbuild.roadbuildmanage.searchRoadBuild.d",
	info_param_k : "",
	info_param_v : "",
	info_td : [ [ '填报单位', '道路名称', '路段名称', '方向', '施工开始时间', '状态' ],
			[ '填报单位', '道路名称', '路段名称', '方向', '施工开始时间', '状态' ],
			[ '填报单位', '道路名称', '路段名称', '方向', '施工开始时间', '状态' ],
			[ '填报单位', '道路名称', '路段名称', '方向', '施工开始时间', '状态' ] ],
	info_td_width : [ [ 15, 15, 15, 15, 15, 8 ], [ 15, 15, 15, 15, 15, 8 ], [ 15, 15, 15, 15, 15, 8 ], [ 15, 15, 15, 15, 15, 8 ] ],
	dialog_url : "dispatch/ehl/cpoliceedit/RoadBuildAdd.jsp?alarmId=",
	dialog_params : "dialogWidth:700px;dialogHeight:550px"
});
//Modification finished									
countActions.push( {
	title : "协查通报",
	url : "dispatch.accdept.searchCount.d?atype=4&state=1",
	params : "",
	prj : [ {
		id : '',
		name : '协查通报新增信息'
	}, {
		id : '',
		name : '协查通报更新信息'
	}, {
		id : '',
		name : '协查通报解除信息'
	} ],
	info_url : "dispatch.xcbk.searchXcbk.d?mstate=1,"
			+ "dispatch.xcbk.searchXcbk.d?mstate=2,"
			+ "dispatch.xcbk.searchXcbk.d?mstate=3",
	info_param_k : "",
	info_param_v : "",
	info_td : [ [ '车牌号码', '填报单位', '接收单位', '转发单位', '消息类型', '状态' ],
			[ '车牌号码', '填报单位', '接收单位', '转发单位', '消息类型', '状态' ],
			[ '车牌号码', '填报单位', '接收单位', '转发单位', '消息类型', '状态' ] ],
	info_td_width : [ [ 12, 15, 15, 12, 10, 8 ], [ 12, 15, 15, 12, 10, 8 ],
			[ 12, 15, 15, 12, 10, 8 ] ],
	dialog_url : "dispatch/ehl/cpoliceedit/xcbkAdd.jsp?ptype=3&id=",
	dialog_params : "dialogWidth:800px;dialogHeight:600px"
});
countActions.push( {
	title : "其他重大情况",
	url : "dispatch.notice.getPromptCount.d?",
	params : "",
	prj : [ {
		id : '',
		name : '收到新信息',
		param_v : ''
	}, {
		id : '',
		name : '超时未签收提醒',
		param_v : ''
	}, {
		id : '',
		name : '超时未办结提醒',
		param_v : ''
	} ],
	info_url : "dispatch.notice.getPromptInfo.d?ptype=1,"
			+ "dispatch.notice.getPromptInfo.d?ptype=2,"
			+ "dispatch.notice.getPromptInfo.d?ptype=3",
	info_param_k : "",
	info_param_v : "",
	info_td : [ [ '标题', '发送时间', '发送单位', '接收单位', '处理状态' ],
			[ '标题', '发送时间', '发送单位', '接收单位', '处理状态' ],
			[ '标题', '发送时间', '发送单位', '接收单位', '处理状态' ] ],
	info_td_width : [ [ 12, 20, 15, 15, 12 ], [ 12, 20, 15, 15, 12 ],
			[ 12, 20, 15, 15, 12 ] ],
	dialog_url : "dispatch/ehl/cpoliceedit/noticeAdd.jsp?ptype=3&id=",
	dialog_params : "dialogWidth:800px;dialogHeight:600px"
});
countActions.push( {
	title : "总队约稿",
	url : "dispatch.accdept.searchCount.d?atype=6&state=1&mstate=1",
	params : "",
	prj : [ {
		id : '',
		name : '收到新信息',
		param_v : ''
	}],
	info_url : "policeWorks.orderContribution.searchPromptInfo.d",
	info_param_k : "",
	info_param_v : "",
	info_td : [ [ '约稿单位', '约稿人', '约稿时间', '约稿内容', '处理状态' ]],
	info_td_width : [ [ 18, 18, 18, 18, 10 ] ],
	dialog_url : "policeWork/ehl/newsFiles/orderContribution.jsp?page=3",
	dialog_params : "dialogWidth:800px;dialogHeight:600px"
});

/**
 * 初始化msg，提示框div.
 * @author dxn
 * @param 
 * @return 
 * @since 
 */
function ctrlDiv() {
	if (!$('msg')) {
		var div = document.createElement('div');
		div.id = 'msg';
		document.body.appendChild(div);
	}

	$('msg').setStyle( {
		position : 'absolute',
		zIndex : '20000',
		width : '300px',
		height : '220px',
		background : 'white',
		border : '1px dotted #000',
		right : '0',
		bottom : '0',
		display : 'none'
	});
	var str
= '\
		<div id="prompt_nav" style="width:298px;height:16px;text-align:left;">\
			<span style="background:url(' + imgUrl + ') no-repeat -129px -66px;width:5px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:38px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:38px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:38px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:38px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:38px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:38px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -131px -66px;width:5px;"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -169px -66px;width:20px;cursor:hand;" onclick="promptCtrl()" id="prompt_min"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -209px -66px;width:35px;cursor:hand;" onclick="$(\'msg\').hide()"></span>\
			<span style="background:url(' + imgUrl + ') no-repeat -244px -66px;width:5px;"></span>\
		</div>\
		<div id="prompt_box" style="height:182px;overflow-y:auto;text-align:left;color:#FF6633;">\
			<legend style="border:0px;font-size:13px;border-bottom:1px solid #076B9F;width:100%;padding-left:70px;margin-bottom:10px;font-weight:900;" align="center"><span>' + prompt_title + '</span></legend>\
			<div id="prompt_text"></div>\
		</div>';
	$('msg').innerHTML = str;
	//drag('msg','prompt_nav');
	getPromptCount();
}
/**
 * 缩放功能.
 * @author dxn
 * @param 
 * @return 
 * @since 
 */
function promptCtrl(){
	if($('prompt_max')){
		$('prompt_max').style.background = 'url('+imgUrl+') no-repeat -189px -66px';
		$('prompt_box').hide();
		$('msg').style.height = '18px';
		$('prompt_max').id = 'prompt_min';
	}else{
		$('prompt_min').style.background = 'url('+imgUrl+') no-repeat -169px -66px';
		$('prompt_box').show();
		$('msg').style.height = '200px';
		$('prompt_min').id = 'prompt_max';
	}
}
/**
 * 打开提示框
 * @author dxn
 * @param 
 * @return 
 * @since 
 */
function showPrompt(){
	var cid = $('prompt_max')?'prompt_max':'prompt_min';
	$(cid).style.background = 'url(' + imgUrl + ') no-repeat -169px -66px';
	$('msg').show();
	$('prompt_box').show();
	$('msg').style.height = '200px';
	$(cid).id = 'prompt_max';
}
/**
 * 获取后台统计数据，并显示
 * @author dxn
 * @param 
 * @return 
 * @since 
 */
function getPromptCount(){
	if(!$('msg')){
		ctrlDiv();
	}
	$('msg').setStyle = {right:'0',
							bottom:'0'}	
	$('prompt_text').innerHTML = '';
	var str = '';
	var flag = 0;
	var k = 0;
	plistArr = new Array();
	try{
		var getPromptCountInfo = function(k){
			new Ajax.Request(encodeURI(countActions[k].url), {method:"post", parameters:countActions[k].params, onComplete:function(xmlHttp) {
				//Modified by Liuwx 2011-9-6
				//当出现异常时跳转至登录界面
				var xmlText = xmlHttp.responseText;
				if((xmlText.indexOf('Fail')>-1)||(xmlText == undefined || xmlText == "" || xmlText == null)){
					errorCounter++;
					if(errorCounter > 3){
						var strURL = location.href;
						var arrURL=strURL.split("EHL_ATMSIC_GD"); 
						window.location.href = arrURL[0]+"EHL_ATMSIC_GD/";						
					}
				}else{
					errorCounter = 0;
				//Modification finished
					var xmlDoc = xmlHttp.responseXML;
					var results = xmlDoc.getElementsByTagName("col");
					var ids = xmlDoc.getElementsByTagName("id");
					for(var i = 0; i < countActions[k].prj.length; i ++){
						if(results[i].text != 0){
							flag ++;
							plistArr.push({cindex:k,id:countActions[k].prj[i].id,name:countActions[k].prj[i].name,count:results[i].text,pindex:i})
						}
						if(ids.length == results.length){
							countActions[k].prj[i].param_v = ids[i].text;
						}
					}
					k ++;
					if(k == countActions.length){
						if(flag == 0){
							$('msg').hide();
							return;
						}
						var _index = -1;
						for(var i=0;i<plistArr.length;i++){
							if(_index != plistArr[i].cindex){
								k ++;
								countActions[plistArr[i].cindex].info_param_v = plistArr[i].id;
								if(i > 0) str += '</ul>'
								str += '<div height="1pt">&nbsp;</div>';
								str += '<div style="font-size:13px;font-weight:bold;padding-left:20px;">' + countActions[plistArr[i].cindex].title + '</span></div>\
									<ul style="font-size:12px;padding-left:38px;">\
										<li style="color:blue;"><a href="#" onclick="gotoPage(' + plistArr[i].cindex + ',' + plistArr[i].pindex + ')">'+ plistArr[i].name +'[' + plistArr[i].count + ']</a></li>';
								_index = plistArr[i].cindex;
							}else{
								str += '<li style="color:blue;"><a href="#" onclick="gotoPage(' + plistArr[i].cindex + ',' + plistArr[i].pindex + ')">'+ plistArr[i].name +'[' + plistArr[i].count + ']</a></li>';
							}
						}
						str += '</ul>';
						showPrompt();
						var s = location.href;  
		            	var arr=s.split("EHL_ATMSIC_GD");
		            	var srcUrl = arr[0] +  "EHL_ATMSIC_GD/dispatch/alarm.wav";
						str +='<embed id="soundControl" src="'+srcUrl+'" mastersound hidden="true" loop="false" autostart="true" ></embed>';
						$('prompt_text').innerHTML = str;
						return;
					}else{
						getPromptCountInfo(k);
					}
				
				}
			}});
		}
	}catch(e){
		alert(e)
	}
	getPromptCountInfo(k);
}
var bgObj;
/**
 * 打开详细页面
 * @author dxn
 * @param index ：countActions 数组下标
 * @return 
 * @since 
 */
function gotoPage(index,prjindex){
	if(!$('moreInfo')) {
		var div = document.createElement('div');
		div.id = 'moreInfo';
		document.body.appendChild(div);
	}
	if(!$('bgDiv')) {
		var sWidth,sHeight;
		sWidth=document.body.offsetWidth;
		sHeight=screen.height;
		bgObj=document.createElement("iframe");
		bgObj.setAttribute('id','bgDiv');
		bgObj.style.position="absolute";
		bgObj.style.top="0";
		bgObj.style.background="#777";
		bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
		bgObj.style.opacity="0.6";
		bgObj.style.left="0";
		bgObj.style.width=sWidth + "px";
		bgObj.style.height=sHeight + "px";
		bgObj.style.zIndex = "200500";
		document.body.appendChild(bgObj);
	}
	$('moreInfo').setStyle({
		position:'absolute',
		overflow:'visible',
		zIndex:'300000',
		width:'600px',
		height:'0px',
		background:'#F0F8FF',
		border:'1px solid #088',
		right:'200px',
		bottom:'1px',
		display:''	})
	getMoreInfo(index,prjindex);
}


function getMoreInfo(index,prjindex){
	var err = '已无待处理事项';
	var infoUrl = countActions[index].info_url;
	var urlArr = infoUrl.split(",");
	var thisUrl = urlArr[prjindex];
	//Modified by Liuwx 2011-07-01
	//显示提醒的标题信息
	var msgTitle = "";
	//Modification finished
	if(thisUrl){
		var params = countActions[index].info_param_k + countActions[index].info_param_v;
		params += "&id=" + countActions[index].prj[prjindex].param_v||"";
		new Ajax.Request(encodeURI(thisUrl), {method:"post", parameters:params, onComplete:function(xmlHttp) {
			var xmlDoc = xmlHttp.responseXML;
			var rows = xmlDoc.getElementsByTagName("row");
			//Modified by Liuwx 2011-07-01
			msgTitle = countActions[index].prj[prjindex].name;
			var title = '<div style="width:100%;height:12px;">' +
			'<table class="titleTopBack" style="width:100%;height:10px;" border="0" cellpadding="0" cellspacing="0">' +
			'<TBODY><tr style="width:100%;height:10px;"><td align="left" width="85%"><span style="margin-left:10px;">'+msgTitle+
			'</span></td><td align="right" width="10%">'+
			'<img src="dispatch/images/holder/gb.gif" alt="关闭" style="cursor:hand;margin-right:10px;" onclick="closeMoreInfo()" />'+
			'</td></tr></TBODY></table></div>';
			//Modification finished
			var str = '<div style="width:100%;height:420px;overflow-x:hidden;overflow:auto;">' +
			'<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">' +
			'<tr class="titleTopBack" id="moreInfo_top">' +
			'<td width="6%" class="td_r_b td_font">编号</td>';
			for(var i = 0; i < countActions[index].info_td[prjindex].length; i ++){
				str += '<td width="' + countActions[index].info_td_width[prjindex][i] + '%" class="td_r_b td_font">' + countActions[index].info_td[prjindex][i] + '</td>';
			}
			str += '<td width="24%" class="td_r_b td_font" >操作</td></tr>';
			var text,length=16;
			for(var i=0;i<rows.length;i++){
				var cols = rows[i].getElementsByTagName("col");
				if(cols.length > countActions[index].info_td.length){
					str += '<tr><td class="td_r_b td_font">' + (i+1) + '</td>'
					for(var j = 0; j < countActions[index].info_td[prjindex].length; j ++){
						text = cols[j+1].text;
						if(text.length > length){
							text = "<label title='"+text+"'>"+text.substring(0,length)+"</lable>";
						}
						str += '<td class="td_r_b td_font">' + text + '</td>';
					}
					// 警情时的点击进入处理
					if(index == 0) {
						if(prjindex == 0){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onClickJP(' + index + ',' + prjindex + ',\'' + cols[0].text + '\'' + ',\''+cols[6].text+'\')">签收并进入处理流程</a></td></tr>'
						}else if(prjindex == 1){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onClickShowXb(' + index + ',' + prjindex + ',\'' + cols[0].text + '\',\'' + cols[6].text  + '\',\'' + cols[7].text + '\')">查看警情续报</a></td></tr>'
						}else if(prjindex == 2){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onClickTb(' + index + ',' + prjindex + ',\'' + cols[0].text + '\',\'' + cols[6].text + '\',\'' + cols[7].text + '\')">签收</a></td></tr>'
						}
					// 拥堵到期提醒的点击进入处理
					} else if(index == 1) {
						if(prjindex==0){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onCrowdClick('+index+',' + prjindex + ',\'' + cols[0].text + '\',\'' + cols[7].text + '\''  + ')">查看/处理</a></td></tr>'
						}else if(prjindex>=1 && prjindex <= 3){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onCrowdClickZD('+index+',' + prjindex + ',\'' + cols[0].text + '\',\'' + cols[7].text + '\''  + ')">查看拥堵信息</a></td></tr>'
						}else if(prjindex==4){
							var ctrlDesc = "处理";
							if(jglx=='1'){
								ctrlDesc = "查看";
							}
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="showTrafficNewsFeedsView('+index+',' + prjindex +',\''+cols[0].text+'\',\''+jglx+'\',\''+cols[7].text+'\''+')">'+ ctrlDesc + '</a></td></tr>'
						}else if(prjindex==5){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="showTrafficNewsFeedsView('+index+',' + prjindex +',\''+cols[0].text+'\',\''+jglx+'\',\''+cols[7].text+'\''+')">处理</a></td></tr>'
						}else if(prjindex==6){
                            str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onOutToDateCrowdClick('+index+',' + prjindex + ',\'' + cols[0].text + '\',\'' + cols[6].text + '\''  + ')">查看</a></td></tr>'
                        }
					}
					//施工占道
					else if(index == 2) {
						if($("jgid").value == "440000000000"){
							if(cols[8].text == "570005"){
								str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onButtonClick2(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[7].text+ '\',\''+1+ '\',\''+cols[8].text+ '\')">查看/处理</a></td></tr>'
							}else{
								str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onButtonClick2(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[7].text+ '\',\''+2+ '\',\''+cols[8].text+ '\')">查看</a></td></tr>'
							}
						}else if($("jgid").value != cols[9].text){
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onButtonClick2(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[7].text+ '\',\''+2+ '\',\''+cols[8].text+ '\')">查看</a></td></tr>'
						}else{
							str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onButtonClick2(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[7].text+ '\',\''+1+ '\',\''+cols[8].text+ '\')">处理</a></td></tr>'
						}
					}
					//车辆协查布控的点击进入处理
					else if(index == 3){
						str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onButtonClick(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[7].text+ '\')">签收/处理</a></td></tr>'
					}
					//其他重大情况
					else if(index == 4){
						str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="onButtonClick(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[6].text+ '\')">签收/处理</a></td></tr>'
					}
					//约稿信息
					else if(index == 5){
						str += '<td class="td_r_b td_font"><a style="text-decoration: underline;" href="#" onclick="showOrderContribution(' + index + ','+prjindex+',\'' + cols[0].text + '\',\''+cols[6].text+ '\')">签收</a></td></tr>'
					}
				}else{
					err = '数据项不足';
					break;
				}
			}
			if (rows.length == 0) {
					str += '<tr><td colspan="6" align="center" style="color:black;">'
							+ err + '，1分钟后自动关闭页面</td></tr>';
					setTimeout(function() {
								if($('moreInfo')){
									$('moreInfo').hide();
								}
								if (bgObj) {
									try{
										document.body.removeChild(bgObj);
									}catch(e){
//										alert("error");
									}
								}
							}, 60000)
				}
			str += "</table></div>"
			$('moreInfo').innerHTML = title + str;
			//new NeatDialog("", "", false);
	//		drag('moreInfo','moreInfo_top');
		}})
	}
}

function closeMoreInfo(){
	if(bgObj) {
		document.body.removeChild(bgObj);
	}
	$('moreInfo').hide();
//	$('bgDiv').hide();
//	window.neatDialog.close();
//	drag('msg','prompt_nav');
}

function showOrderContribution(index,pindex,id,state){
	window.showModalDialog(countActions[index].dialog_url +"&tmp=" + Math.random(), {id:id,state:state}, countActions[index].dialog_params);
	getPromptCount();
	setTimeout(function(){getMoreInfo(index,pindex)},500);
}

function onButtonClick(index,pindex,para1,para2){
	window.showModalDialog(countActions[index].dialog_url + para1 + "&stype="+para2+"&tmp=" + Math.random(), "", countActions[index].dialog_params);
	getPromptCount();
	setTimeout(function(){getMoreInfo(index,pindex)},500);
}

/**
 * Modified by Liuwx 2011-06-29
 * @param index
 * @param pindex
 * @param para1 alarmID
 * @param para2 state
 * @param para3 insrtOrUpdt
 * @param para4 stype
 * @return
 */
function onButtonClick2(index,pindex,para1,para2,para3,para4){
	//更新消息的状态为：已查看
	Flow.updtState({id:para1,state:"2",stype:para2,rpname:$("zbrxm").value},null,false);
	window.showModalDialog(countActions[index].dialog_url + para1 + "&stype="+para2+"&tmp=" + Math.random()+"&insrtOrUpdt="+para3+"&buildState="+para4, "", countActions[index].dialog_params);
	getPromptCount();
	getMoreInfo(index,pindex);
}
//Modification finished

/**
 * 
 * 警情时的点击进入时的处理
 */
function onClickJP(index,pindex,alarmId,eventState){
	// 大队上报支队时的处理
	if("004033" == eventState) {
		// 大队上报支队支队已签收的更新处理
		updateMaterialInfo(alarmId,eventState);
		window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&readContol=11&updateFlag=1&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	// 总队下发支队时的处理
	} else if ("004037" == eventState) {
		// 支队签收完成的更新处理
		updateMaterialInfo(alarmId,eventState);
		window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&readContol=99&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	// 支队上报时的处理
	} else if ("004034" == eventState) {
		updateMaterialInfo(alarmId,eventState);
		window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&readContol=99&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	} else {
		window.showModalDialog(countActions[index].dialog_url + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	}
	getPromptCount();
	getMoreInfo(index,pindex);
}

/**
 * 警情时点击进入（事故通报提醒）
 * @param {Object} index
 * @param {Object} pindex
 * @param {Object} alarmId
 * @param {Object} eventState
 */
//message为当前是否为转发信息或下发信息
function onClickTb(index,pindex,alarmId,eventState,message){
	if(message == "支队下发") {
		window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&readContol=99&message=1&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	} else {
		window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&readContol=99&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	}
	getPromptCount();
	getMoreInfo(index,pindex);
}

/**
 * 警情状态的更新<br/>
 */
function updateMaterialInfo(searchAlarmId,searchEventState) {
	// 警情状态的更新方法的呼出
	var url = "dispatch.cmaterialInfo.updateMaterialInfo.d";
	url = encodeURI(url);
	var params = "searchAlarmId="+searchAlarmId + "&searchEventState=" + searchEventState;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp) {}});
}
/**
 * 
 * 警情续报时的点击进入时的处理
 */
function onClickShowXb(index,pindex,alarmId,EventState,userFlg) {

	updateXbInfoByOpid(alarmId);
	if (userFlg == "支队") {
		if (EventState == "004033" || EventState == "004034") {
			window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()
							+ "&insrtOrUpdt=2&readContol=99&xbflg=1&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
		} else {
			window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()
							+ "&insrtOrUpdt=2&readContol=99&xbflg=1&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
		}
	} else if (userFlg == "总队") {
			window.showModalDialog("dispatch/ehl/cpoliceedit/materialInfoEdit.jsp?tmp=" + Math.random()
							+ "&insrtOrUpdt=2&readContol=99&xbflg=1&alarmId=" + alarmId, "", "dialogWidth:900px;dialogHeight:570px");
	}
	getPromptCount();
	getMoreInfo(index,pindex);
}
/**
 * 
 * 拥堵到期提醒时的点击进入时的处理
 */
function onCrowdClick(index,pindex,alarmId,trfficeCrowState) {
	window.showModalDialog("dispatch/ehl/cpoliceedit/TrafficCrowdInfoAdd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&alarmId=" + alarmId+"&trfficeCrowState="+trfficeCrowState, "", "dialogWidth:850px;dialogHeight:600px");
	getPromptCount();
	getMoreInfo(index,pindex);
}
function onCrowdClickZD(index,pindex,alarmId,trfficeCrowState) {
	window.showModalDialog("dispatch/ehl/cpoliceedit/TrafficCrowdInfoAdd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&alarmId=" + alarmId+"&trfficeCrowState="+trfficeCrowState, "", "dialogWidth:850px;dialogHeight:600px");
	getPromptCount();
	getMoreInfo(index,pindex);
}
/**
 * Modified by Liuwx 2012年3月18日14:17:53
 * 拥堵超时提醒
 * @param index
 * @param pindex
 * @param alarmId
 * @param trfficeCrowState
 */
function onOutToDateCrowdClick(index,pindex,alarmId,trfficeCrowState) {
    window.showModalDialog("dispatch/ehl/cpoliceedit/TrafficCrowdInfoAdd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&alarmId=" + alarmId+"&trfficeCrowState="+trfficeCrowState, "", "dialogWidth:850px;dialogHeight:600px");
    getPromptCount();
    getMoreInfo(index,pindex);
}

//展示报料信息
function showTrafficNewsFeedsView(index,pindex,bh,jglx,clzt){
	var clzt_0 = "0";//待总队处理
	var clzt_1 = "1";//待大队核实
	var clzt_2 = "2";//待总队确认
	var clzt_3 = "3";//处理完成
	var clzt_4 = "4";//已忽略
	var herght = '720px';
	if(clzt==clzt_0 || clzt==clzt_4){
		herght = '470px';
	}
	if(clzt==clzt_1){
		if(jglx=='2'){
			herght = '600px';
		}else{
			herght = '470px'; 
		}
	}else if(clzt==clzt_2){
		if(jglx!='0'){
			herght = '720px';
		}
	}
	
	var returnValue = window.showModalDialog("dispatch/ehl/cpoliceedit/TrafficNewsFeeds.jsp?tmp=" + Math.random()
		+ "&bh="+bh+"&jglx="+jglx+"&insrtOrUpdt=2","","dialogWidth:660px;dialogHeight:"+herght+"");
	
	getPromptCount();
	getMoreInfo(index,pindex);
}
/**
 * 更新续报状态<br/>
 * @param {} opid
 * @param {} kbn 区分 '01':"支队已签收续报" '02'"总队已签收续报"
 */
function updateXbInfoByOpid(alarmId) {
	// 警情状态的更新方法的呼出
	var url = "dispatch.cmaterialInfo.updateXbInfoByOpid.d";
	url = encodeURI(url);
	var params = "alarmId="+alarmId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp) {}});
}



/*
获取值班切换时间
 计时器定时执行，
 遇到切换时间，
 停止计时器，
 弹出提示框，
 保存成功后，
 移除提示框，
 隐藏背景层
*/
var dutyBTime = $("dutyBTime").value;
var rterId;//recordtimeer id计时器ID
function recordTime(time){
	//watchPPTime(dutyBTime,time);
	rterId = setInterval( function(){
			getPromptCount();
			getCurrentNoticesList(loginName);
			//watchPPTime(dutyBTime,time);
		},time*1000);
}
var isOtherDutyPromptOpen = false;
var otherFrame;
//监视时间，弹出“今日值班表”提醒框
//watchPopupPromptTime
function watchPPTime(dutyBTime,time){
	var dutyPartTimes = dutyBTime.split(":");
	function getIntTime(time){
		time = time.substring(0,1)=="0"?time.substring(1):time;
		return parseInt(time);
	}
	var hour = getIntTime(dutyPartTimes[0]);
	var minute = getIntTime(dutyPartTimes[1]);
	var second = getIntTime(dutyPartTimes[2]);
	var curTime = new Date();
	var dutyTime = new Date();
	dutyTime.setHours(hour,minute,second);
	var delayTime = new Date();
	second += time;
	delayTime.setHours(hour,minute,second);
	if(curTime >= dutyTime && curTime < delayTime){
		if(!isOtherDutyPromptOpen){//另一个“今日值班表”打开，当前不打开
			otherFrame = window.frames["_main"];
			if(!otherFrame.isOtherDutyPromptOpen){
				otherFrame.isOtherDutyPromptOpen = true;
			}
			//clearInterval(rterId);
			var curTimeStr = curTime.format_(6);
			var yesTimeStr = curTime.changeTime_(3,1,6);
			getYDayDutyInfo(yesTimeStr,curTimeStr);
		}
	}
}

//值班信息数据库数据
var dutyInfo = {};
//值班信息页面数据 duty infomation page data 
var dutyInfoPD={};
//获取昨天的值班信息
function getYDayDutyInfo(stime,etime){
	var url = "dispatch.shortcut.getSimpleResult.d";
	var jgid = $("jgid").value;
	var sql = "select duty.leader,duty.person,(select zbdh1 from t_sys_department where jgid=duty.deptid),phone";
	sql += " from t_oa_duty duty";
	sql += " where duty.deptid = '" + jgid + "' and dtime between '" + stime + "' and '"+etime+"' and rownum=1";
	new Ajax.Request(url,{
		method:"post",
		parameters:{sql:sql},
		onComplete:function(xmlHttp){
			var result = xmlHttp.responseText;
			if(result != "null"){
				result = result.split(",");
				var cols = ["leader","dutyer","tel","phone"];
				if(result.length == cols.length){
					for(var i=0;i<cols.length;i++){
						dutyInfo[cols[i]] = result[i];
					}
				}
			}else{
				dutyInfo.tel = "";
				dutyInfo.leader="";
				dutyInfo.dutyer="";
				dutyInfo.phone="";
			}
			popupRTPrompt(getRTPContent());
		}
	});
}

//得到“今日录入值班信息”内容
function getRTPContent(){
	var tel = dutyInfo.tel||"";
	var telZone = "";
	if(tel){
		var sepIndex = tel.indexOf("-");
		if(sepIndex == -1){
			sepIndex = 3;
		}
		telZone = tel.substring(0,sepIndex);
		tel = tel.substring(sepIndex+1);
	}
	var titleRmk = "<tr><td colspan='4' style='height:25px;padding-left:5px;text-align:left;font-size:15px;font-weight:bolder;vertical-align: bottom;background-color:#a5d1ec'>今日值班人员表<td><td></tr>";
	var promptRmk = "<tr height='40px'><td colspan='4' style='text-align:center;color:red;font-size:15px;'>请更新今日值班人员信息<td><td></tr>";
	var msgRmk = "<tr height='40px'><td id='dutyInputMsg' colspan='4' style='text-align:center;color:red;font-size:15px;'><td><td></tr>";
	//underline style attribute下滑线样式属性
	var ulstlattr = "border:0;border-bottom:1px solid black;text-align:center;font-size:13px;";
	var ulstl = " style='"+ulstlattr+";width:143px;' ";
	var descstl = " style='padding-left:10px;text-align:right' ";
	var vlstl = " style='padding-right:10px;text-align:left' ";
	var btnstl = " style='width:40px;height:20px;background-color:gray;color:#D3E0E7' ";
	var leaderRmk = "<tr><td "+descstl+">值班领导：<td><td "+vlstl+"><input type='text' id='leader' value='"+(dutyInfo.leader||"")+"' "+ulstl+" /></td></tr>";
	var dutyerRmk = "<tr><td "+descstl+">值班人员：<td><td "+vlstl+"><input type='text' id='dutyer' value='"+(dutyInfo.dutyer||"")+"' "+ulstl+" /></td></tr>";
	var telRmk = "<tr><td "+descstl+">值班电话：<td><td "+vlstl+"><input type='text' id='telZone' value='"+telZone+"' style='"+ulstlattr+";width:45px;' />" +
				"-<input type='text' id='tel' value='"+tel+"' style='"+ulstlattr+";width:90px;' /></td></tr>";
	var phoneRmk = "<tr><td "+descstl+">值班手机：<td><td "+vlstl+"><input type='text' id='phone' value='"+(dutyInfo.phone||"")+"' "+ulstl+" maxlength='11' /> (选填)</td></tr>";
	var saveRmk = "<tr height='60px'><td colspan='4' style='text-align:center'><input type='button' "+btnstl+" value='确认' onclick='addDutyInfo()' /><td><td></tr>";
	var content = titleRmk + promptRmk + msgRmk + leaderRmk + dutyerRmk + telRmk + phoneRmk + saveRmk;
	content = "<table border='0' cellspadding='0' cellspacing='0' style='width:380px;height:220px;'>" + content + "</table>";
	return content;
}


//弹出“今日录入值班表”计时提醒
function popupRTPrompt(content){
	function showRTPrompt(content){
		var rtprompt = $("rtprompt");
		if(rtprompt){
			rtprompt.show();
		}else{
			var div = document.createElement('div');
			div.id = 'rtprompt';
			document.body.appendChild(div);
			rtprompt = $("rtprompt");
			rtprompt.setStyle({
			position:'absolute',
			overflow:'visible',
			zIndex:'300000',
			background:'white',
			border:'1px solid green',
			left:'450px',
			top:'122px',
			border:'#a5d1ec 5px solid'
			});
		}
		rtprompt.innerHTML = content;
	}
	function showBgDiv(){
		var bgDiv = $('bgDiv');
		if(bgDiv){
			bgDiv.show();
		}else{
			var sWidth,sHeight;
			sWidth=document.body.offsetWidth;
			sHeight=screen.height;
			bgObj=document.createElement("iframe");
			bgObj.setAttribute('id','bgDiv');
			bgObj.style.position="absolute";
			bgObj.style.top="0";
			bgObj.style.background="#777";
			bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
			bgObj.style.opacity="0.6";
			bgObj.style.left="0";
			bgObj.style.width=sWidth + "px";
			bgObj.style.height=sHeight + "px";
			bgObj.style.zIndex = "200500";
			document.body.appendChild(bgObj);
		}
	}
	showRTPrompt(content);
	showBgDiv();
}

function hideRTPrompt(){
	var rtprompt = $("rtprompt");
	if(rtprompt){
		rtprompt.hide();
	}
	var bgDiv = $('bgDiv');
	if(bgDiv){
		bgDiv.hide();
	}
	if(otherFrame){
		otherFrame.isOtherDutyPromptOpen = false;
	}
}

function checkDutyInfo(){
	var msgCntn = $("dutyInputMsg");
	var msg;
	var reg = /^[\u0391-\uFFE5]+$/;
	var leader = $("leader");
	var leaderVl = leader.value.replace(/ /g,"");
	if(leaderVl == "" || !reg.test(leaderVl)){
		if(leaderVl == ""){
			msg = "请输入值班领导";
		}else{
			msg = "值班领导必须为中文";
		}
		msgCntn.innerText = msg;
		leader.focus();
		return false;
	}else{
		dutyInfoPD["leader"] = leaderVl;
	}
	
	var dutyer = $("dutyer");
	var dutyerVl = dutyer.value.replace(/ /g,"");
	if(dutyerVl == "" || !reg.test(dutyerVl)){
		if(dutyerVl == ""){
			msg = "请输入值班人员";
		}else{
			msg = "值班人员必须为中文";
		}
		msgCntn.innerText = msg;
		dutyer.focus();
		return false;
	}else{
		dutyInfoPD["person"] = dutyerVl;
	}
	
	var reg = /^\d+(\.\d+)?$/;
	var telZone = $("telZone");
	var telZoneVl = telZone.value.replace(/ /g,"");
	if(telZoneVl == "" || !reg.test(telZoneVl)){
		if(telZoneVl == ""){
			msg = "请输入电话号码区号！";
		}else{
			msg = "电话号码区号必须为数字";
		}
		msgCntn.innerText = msg;
		telZone.focus();
		return false;
	}else{
		dutyInfoPD["tel1"] = telZoneVl;
	}
	
	var tel = $("tel");
	var telVl = tel.value.replace(/ /g,"");
	if(telVl == "" || !reg.test(telVl)){
		if(telVl == ""){
			msg = "请输入电话号码";
		}else{
			msg = "电话号码必须为数字";
		}
		msgCntn.innerText = msg;
		tel.focus();
		return false;
	}else{
		dutyInfoPD["tel2"] = telVl;
	}
	
	var phone = $("phone");
	dutyInfoPD["mobilePhone"]=phone.value.replace(/ /,"");
	
	return true;
}

function addDutyInfo(){
	if(checkDutyInfo()){
		var jgid = $("jgid").value;
		var url = "dispatch.duty.insert.d";
		var params = dutyInfoPD;
		params["deptId"] = jgid;
		new Ajax.Request(url,{
			method:"post",
			parameters:params,
			onComplete:function(xmlHttp){
				var msgCntn = $("dutyInputMsg");
				var result = xmlHttp.responseText;
				if(result == "true"){
					var tel = dutyInfoPD.tel1 + "-" +dutyInfoPD.tel2;
					if(tel != dutyInfo.tel){
						modifyTel(jgid,tel);
					}else{
						msgCntn.innerText="保存值班信息成功！";
						setTimeout(hideRTPrompt,1000);
					}
				}else if(result == "false"){
					msgCntn.innerText = "添加值班信息失败！"
				}else{
					msgCntn.innerText = "未知异常！";
				}
			}
		});
	}
}

function modifyTel(jgid,tel){
	var url = "dispatch.duty.modifyTel.d";
	new Ajax.Request(url,{
		method :"post",
		parameters :{jgid:jgid,tel:tel},
		onComplete:function(xmlHttp){
			var msgCntn = $("dutyInputMsg");
			result = xmlHttp.responseText;
			if(result == "true"){
				msgCntn.innerText="保存值班信息成功！";
				setTimeout(hideRTPrompt,1000);
			}else if(result == "false"){
				msgCntn.innerText="修改电话号码失败！";
			}else{
				msgCntn.innerText="未知异常！";
			}
		}
	});
}

























