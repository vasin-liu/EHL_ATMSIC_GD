//全局过滤参数，以便在其他地方调用
var G_Filter_params;
function getPoliceRemindHtml (G_Filter) {
	// 检索交警提醒信息件数
//	queryPoliceRemind(G_Filter);
	$policeRemind.rollInfo.request({publishtype:"1"},showPoliceRemindList);
}
function queryPoliceRemind (filter) {
	var url = "dynamicinfo.policeRemind.getRemindInfo.d";
	//Modified by Liuwx 2011-8-26
	//var params = "";	
	var params = {};
	if(filter){
		params = filter;
	}else{
		params["user_code"] = $("user_code")?$("user_code").value:"";
		params["function_id"] = "111111";
		params["model_name"] = "DynamicInfo.jsp";
	}
	
	//params = encodeURI(params);	
	//Modification finished
	new Ajax.Request(url, {
				method : 'post',
				parameters : params,
				// 读取完成后，放入内存数组
				onComplete : showPoliceRemindList
			});	
}

function getToday(index,time){
	time = time.replace(time.split(" ")[0],"今日");
	return index + "、" + time;
}

/**
 * 回显lst信息<br/>
 * @param {} xmlDoc
 */
function showPoliceRemindList(xmlHttp,rollInfo){
//	var showListInfo = getRollInfo(xmlHttp);
	rollInfo.setHtml(rollInfo.getClick);
	var showListInfo = rollInfo.html;
	if (showListInfo == "" ) {
		showListInfo = "<a class='a01' href='#' onclick='$policeRemind.moreInfo.show();' >" + rollInfo.nodata + "</a>";
	} 
	//Modified by Liuwx 2011-08-05
	//现在改为可选取具体某条记录
//	else {
//		showListInfo = "<a class='a01' href='#'style='word-break:break-all' onclick='AddLightOver();stopSynchDynamicInfo();showDetailRemindInfo()' >" + showListInfo + "</a>";
//	}
	//Modification finished

	/*var strTitle = "<table id=\"remindListTable\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=2 style=\"border:1px solid #97BECE;\"	>";
	//生成表头
	strTitle += "<tr class=\"scrollColThead\" >";
	strTitle += "<td class=\"scrollRowThead scrollCR\" style='text-align:left;' width=12% background=\"../../image/back/title_back.gif\"><b>今日路况提示</b> "
		//Modified by Liuwx 2011-8-18
        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        + "<a class='a01' href='#' onclick='AddLightOver();stopSynchDynamicInfo();showRemindInfoFilter();' style='visibility: hidden;'>" +"区域选择</a>"
        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		+ "<a class='a01' href='#' onclick='$policeRemind.moreInfo.show();' >" +'更多... </a></td>';
		//Modification finished
	strTitle += "</tr>";
	strTitle += "</table>";

	var strTable = '<div id="sdafafsd" style="background-color:#F0FFFF;" ><table width="100%" style=\"border:1px solid #97BECE;\"></table></div>';
	strTable = strTable + strTitle;*/
//    var strTable = "<table id='remindListTable' class='scrolltable' width='100%' cellspacing='0px' cellpadding='0px' border='0px'>" +
    var strTable = "<table id='remindListTable' width='100%' height='100%' cellspacing='0px' cellpadding='0px' border='0px'>" +
        "<tr style=\"height: 25px;background-image: url(../../image/back/title_back.gif);\"><td width='80%'>今日路况提示</td>" +
        "<td align='right'><a class='a01' href='#' onclick='$policeRemind.moreInfo.show();' >更多... </a></td></tr>" +
        "<tr id='marqueeRemindInfoListTr'><td colspan='2'>";
	if($('appid').value == "1001") {
		strTable += "<marquee height='425px' direction='up' scrolldelay='5' behavior='scroll' scrollamount='2' onMouseOut=this.start(); onMouseOver=this.stop();> <div id='remindListInfo' style='cursor:hand;background-color:#F0FFFF;'  ></div> </marquee>";
	} else {
		strTable += "<marquee direction='up'  scrolldelay='5' behavior='scroll' scrollamount='2' onMouseOut=this.start(); onMouseOver=this.stop();> <div id='remindListInfo' style='cursor:hand;background-color:#F0FFFF;'  ></div> </marquee>";
	}
    strTable += "</td></tr></table>";
    //Modified by Liuwx 2012年3月13日14:55:50
	/*strTable = strTable + "<div id='addInfo' style='background-color:#F0FFFF;'></div>";
	if($('appid').value == "1001") {
		var innerAddInfo = '<table style=\"border:1px solid #97BECE;\" bgcolor="#F0FFFF"> <tr style="height:25px;vertical-align:bottom"><td><b>编辑发布信息提示</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
			//+ "<a class='a01' href='#' onclick='showDetailRemindInfo()' >" +'查看全部提示信息 </a>
	        + '</td></tr><tr> <td >'
			+ '<textarea rows="4 name="S1" id = "remindInfo" cols="36" onfocus="stopSynchDynamicInfo()" onblur="synchDynamicInfo()">'
			+ deptNameForCenter
			+ "交警提示："
			+ '</textarea><br/> </td> </tr><tr><td><font color="red" >* 上述信息提示主要用于交通电台、微博向公众发布交通信息指引。</font></td></tr> <tr align="center"> <td align="center" >'
			+ '<input style="border: 1px #7B9EBD solid" type="button" value="提交" name="B1" onclick="savePoliceRemind();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
			+ '<input style="border: 1px #7B9EBD solid" type="button" onclick="resetValue();" value="重置" name="B2"></td> </tr> </table>';
	} else {
		var innerAddInfo = "";
	}*/
    //Modification finished

    //Modified by Liuwx 2012年3月13日14:21:59

    //Modification finished

	$('marqueeInfoDiv').innerHTML = strTable;
//	$('addInfo').innerHTML = innerAddInfo;
	$('remindListInfo').innerHTML = showListInfo;
//	$('remindInfo').focus();
}
function getRollInfo(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row"); 
	var result = null;
	var showListInfo = "";
	var content;
	for (var i = 0; i < results.length; i++) {
		result = results[i].childNodes;
		content = result[4].text;
		content = content.replace("（K0+0米至K0+0米）","");
		/*
		var length = 15;
		length = content.length > length ? length : content.length;
		var index = content.indexOf("提示：");
		if(index != -1){
			content = content.substring(index+3);
		}
		if(content.trim().substring(content.trim().length-1) != "。"){
			content += "。";
		}
		*/
		//Modified by Liuwx 2011-08-05
		showListInfo += "<a class='a01' href='#'style='word-break:break-all' onclick=\"$policeRemind.detailInfo.show('"+result[0].text+"')\" >"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ getToday(i+1,result[1].text) + "消息：" + content
			+ "　－【"+result[3].text+"】"
			+ "</a>";
		//添加查看附件链接,如果存在则显示附件链接，否则不显示
//		if(result[6]){
//			showListInfo += "<img src=\"../../image/button/btn_attachment.png\" alt=\"附件\" style=\"cursor:hand;margin-right:10px;\" onclick=\"$(\'msg\').remove();CloseLightOver();synchDynamicInfo();\" />"
//		}
		showListInfo += "<br/><br/>";
		//Modification finished
	}
	return showListInfo;
}

/**
 * 显示详细提示信息<br/>
 * @param {} detailRemindInfo
 * Modified by Liuwx 2011-08-15
 * 新增参数：提醒ID（REMINDID），可以选取单条记录。
 */
function showDetailRemindInfo (REMINDID) {
	
	if (!$('msg')) {
			var div = document.createElement('div');
			div.id = 'msg';
			document.body.appendChild(div);
		}
		$('msg').setStyle({
					position : 'absolute',
					zIndex : '20000',
					width : '650px',
					height : '150px',
					marginLeft : "-350px",
					marginTop : "-200px",
					background : '#F0FFFF',
					border : '1px solid #000',
					left : '50%',
					top : '50%'
				});
	function getSql(isToday,otname){
		var strSql = " select REMINDID,to_char(REMINDTIME,'yyyy\"年\"mm\"月\"dd\"日\" HH24:mi'),DEPARTMENTID," +
				"DEPARTMENTNAME,REMINDINFO,USERNAME,to_char(REMINDTIME,'yyyy\"年\"mm\"月\"dd\"日\"'),to_char(sysdate,'yyyy\"年\"mm\"月\"dd\"日\"')" ;
		strSql += " from V_POLICE_REMIND_DETAIL WHERE 1=1";
		//Modified by Liuwx 2011-8-15
		if(REMINDID != undefined && REMINDID != null && REMINDID != ""){
			strSql += " and REMINDID='"+REMINDID+"'";
		}
		//Modification finished
		var differ = "=";
		if(!isToday){
			differ = "<";
		}
		strSql += " and to_char(remindtime,'yyyy-mm-dd')"+differ+"to_char(sysdate,'yyyy-mm-dd')"
		strSql += " order by REMINDTIME desc,DEPARTMENTID";
		otname = otname || "t";
		strSql = "select "+otname+".*,rownum rn from ("+strSql+") "+ otname;
		return strSql;
	}
	var strSql = getSql(true);
	strSql += " union "+getSql(false);
	strSql += " order by 2 desc, 9";
	//Modify by Xiayx 2011-8-31
	window.toPageNum = window.currPage;
	//Modification finished
	//调用分页控制类
	PageCtrl.initPage("msg","pageData","pageNav",convertSql(strSql),"showAllPoliceRemindLst","8");
}


function showAllPoliceRemindLst (xmlDoc) {
	//生成结果页面
	var results = xmlDoc.getElementsByTagName("row");
	//Modified by Liuwx 2011-07-20
	var jgid = $("JGID").value;
	var tdTitle = "";
	var tdImage = "";
	var insrtOrUpdt = "";
//	var msgTitle = "交警提示信息列表";
//	var title = '<div style="width:100%;height:12px;">' +
//		'<table class="titleTopBack" style="width:100%;height:10px;" border="0" cellpadding="0" cellspacing="0">' +
//		'<TBODY><tr style="width:100%;height:10px;"><td align="left" width="85%"><span style="margin-left:10px;"><big><b>'+msgTitle+
//		'</b></big></span></td><td align="right" width="10%">'+
//		'<img src="../../image/button/btnclose.gif" alt="关闭" style="cursor:hand;margin-right:10px;" onclick="$(\'msg\').remove();CloseLightOver();synchDynamicInfo();\" />'+
//		'</td></tr></TBODY></table></div>';
	var strTable = "<div style=\"width:100%;height:420px;overflow-x:hidden;overflow:auto;\">";
	strTable = "<table id=\"tabList\" class=\"titleTopBack\" width=100% cellSpacing=0 cellPadding=0 border='1' borderColor='#a5d1ec'>";			
	//生成表头
	strTable += "<tr class=\"scrollColThead\">";
	strTable += "<td align=\"center\" width=4% background=\"../../image/back/title_back.gif\" style='word-break:keep-all;'>序号</td>";
	strTable += "<td align=\"center\" width=20% background=\"../../image/back/title_back.gif\">发布时间</td>";
	strTable += "<td align=\"center\" width=20% background=\"../../image/back/title_back.gif\">发布单位</td>";
	strTable += "<td align=\"center\" width=40% background=\"../../image/back/title_back.gif\">交警提示信息</td>";
	//Modify by xiayx 2012-3-21
	//暂时取消修改权限
//	if(baseInfo.jglx=="0" && baseInfo.appid=="1001"){
//		tdTitle = "修改";
//		tdImage = "btnedit.gif";
//		insrtOrUpdt = "3";
//	}else{
	tdTitle = "查看";
	tdImage = "info.gif";
	insrtOrUpdt = "2";
//	}
	//Modification finished
	strTable += "<td align=\"center\" width=4% background=\"../../image/back/title_back.gif\" >"+tdTitle+"</a></td>";
    //Modified by Liuwx 2012-4-11 13:35:29
    if($('jgbh').value.length == 2){
        strTable +="<td align=\"center\" width='12%' background=\"../../image/back/title_back.gif\" >发布类型</td>"
    }
    //Modification finished
	strTable += "</tr>";
	for (var i = 0; i < results.length; i++) {
		result = results[i].childNodes;
		result[4].text = result[4].text.replace("（K0+0米至K0+0米）","");
		if(result[6].text == result[7].text) {
			strTable += "<tr class=\"rowstyle\" style='background-color:#F0BBFF;' title='"+ result[4].text +"'>";					    
		} else {
			strTable += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this)\" onmouseout=\"mouseout(this)\" title='"+ result[4].text +"'>";					    
		}
	    strTable += "<td align=\"center\"  >"+ result[8].text +"</td>";
	    strTable += "<td align=\"center\"  >"+ result[1].text +"</td>";
	    strTable += "<td align=\"center\" >"+ result[3].text +"</td>";
	    //Modified by Liuwx 2011-08-05
	    //添加附件标识
//	    var showAttachment = "<img src=\"../../image/button/btn_attachment.png\" alt=\"附件\" style=\"cursor:hand;margin-right:10px;\" onclick=\"$(\'msg\').remove();CloseLightOver();synchDynamicInfo();\" />";
//		strTable += "<td align=\"center\" colspan=\"1\"style='word-break:break-all' >"+ result[4].text + showAttachment +"</td>";
	    strTable += "<td style='text-align:left;padding:0px;' colspan=\"1\" ><textarea class='copy_text copy_text_indent' readonly>"+ result[4].text +"</textarea></td>";
	    //Modification finished
		strTable += "<td align=\'center\'><input type=\"image\" src=\"../../image/button/"+tdImage+"\"" +
				" onClick=\"modifySelectRowData('" 
				+ result[9].text + "','"+result[10].text+"','"+result[11].text+"','" + insrtOrUpdt
				+ "')\"></td>";

        //Modified by Liuwx 2012-4-11 13:35:29
        if($('jgbh').value.length == 2){
            var internetPublish = "",internalPublish = "";
            var isDisabled = "";
            if(result[12] && result[12].text != null && result[12].text != ""){
                if(result[12].text.indexOf('1') >= 0){
                    internalPublish = "checked";
                }
                if (result[12].text.indexOf('2') >= 0){
                    internetPublish = "checked";
                }
            }
            if(!(result[6].text == result[7].text)) {
                isDisabled = "disabled";
            }
            if(result[13].text == "001023"){
                isDisabled = "disabled";
            }
            var internetPublishId = result[0].text + "_2"; //互联网发布checkbox ID
            var internalPublishId = result[0].text + "_1"; //公安网发布checkbox ID
            strTable +="<td align='center'><input type='checkbox' value='2' id='"+internetPublishId+"' " +
                "onclick=\"$policeRemind.updatePublishState.update('"+ result[0].text + "');\" "
                + internetPublish + " " + isDisabled +">互联网发布<br>" +
                "<input type='checkbox' value='1' id='"+internalPublishId+"' " +
                "onclick=\"$policeRemind.updatePublishState.update('"+ result[0].text + "');\" "
                + internalPublish + " " + isDisabled +">公安网发布</td>"
        }
        //Modification finished
		strTable += "</tr>";
	}
	strTable += "</tr><td colspan='3'></td></tr>";
	strTable += "</table></div>";
	var tabOjb = document.getElementById("pageData");
//	tabOjb.innerHTML = title + strTable;
	tabOjb.innerHTML = strTable;
}

/**
 * 保存交警提示信息<br/>
 * 保存交警提示信息到T_OA_POLICE_REMIND表
 */
function savePoliceRemind() {
	// 取得交警提示的信息
	var remindInfo = $("remindInfo").value;
	remindInfo = remindInfo.replace(deptNameForCenter + "交警提示：","");
	if(remindInfo == ""){
		alert("请输入交警提示信息！");
		$("remindInfo").focus();
		return;
	}
	if (!confirm("是否确认提交？")) {
		return;
	}
	
	// 调用敏感字符验证方法
	if(!validateInput()) { 
		return;
	}
	// 调用后台存储方法
	var url = "dynamicinfo.policeRemind.insertPoliceRemindInfo.d";
	url = encodeURI(url);
	var params = "deptidForCenter=" + deptidForCenter + "&remindInfo=" + 
		remindInfo + "&deptNameForCenter=" + deptNameForCenter + "&userName=" + userName;
	new Ajax.Request(url, {
				method: 'post', 
				parameters: params, 
				//读取完成后，放入内存数组
				onComplete:savePoliceRemindResult }); 
}
/**
 * 交警提示后的回显处理<br/>
 */
function savePoliceRemindResult (xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if(xmlDoc.indexOf('success')>-1) {
		alert("交警提示保存成功！");
		resetValue();
		queryPoliceRemind();
	} else {
		alert("交警提示保存失败！");
	} 
}
/**
 * 重置提示内容<br/>
 */
function resetValue () {
	$('remindInfo').value = deptNameForCenter + "交警提示：";	
	$('remindInfo').focus();
}
/**
 * 输入项目敏感字符的监测<br/>
 * @return {Boolean}
 */
function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@\'#\$\^&]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length; i++) {
		var item = input[i];
		if (item.type == "text" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括:'、~、@、#、$、^、&");
				item.focus();
				return false;
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括:'、~、@、#、$、^、&");
				item.focus();
				return false;	
			}
		}
	}
	return true;
}

/**
 * 设置onmouseover背景色<br/>
 * add by zhaoyu 2010-12-30
 */
function mouseover(trobj){
    if(trobj.style.background != "#8ed9ee"){
       trobj.style.background = "#C8FFFF";//"#6CA6D4";
    }
}

/**
 * 设置onmouseout背景色<br/>
 * add by zhaoyu 2010-12-30
 */
function mouseout(trobj){
    if(trobj.style.background != "#8ed9ee"){
       trobj.style.background = "";
    }
}

/**
 * 选中行时的高亮颜色</br>
 * 设置onmouseclick背景色<br/>
 * Modified by Liuwx 2011-07-20
 */
var temp = null;
function mouseclick(trobj){
	if (temp != null && temp != ""){
		temp.style.background = "#F0BBFF";
	}
	if(trobj.style.background != "#8ed9ee"){
		trobj.style.background = "";
		temp = trobj;
	}
}

////选择行时传入参数
//var parameters = null;
//function setParameters(params){
//	parameters = params;
//}

// 修改选中行数据
function modifySelectRowData(source, alarmid, pid, insrtOrUpdt) {
	var param = [];
	param.push("tmp="+Math.random());
	param.push("source="+source);
	param.push("alarmid="+alarmid);
	param.push("pid="+pid);
	param.push("pageType="+insrtOrUpdt);
	window.showModalDialog("policeRemind.jsp?"+param.join("&"), window,
			"dialogWidth:1000px;dialogHeight:500px");
}

function getPoliceRemindInfo(remindId){
	setReadOnly(page);
	if (page == 2) {
		$("bt2").show();
	}
	if (remindId != "") {
		var url = "dynamicinfo.policeRemind.getRemindInfoById.d"
		url = encodeURI(url);
		var params = {};
		params["remindId"] = remindId;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showGetXMLResponse
		});
	} 
}

function showGetXMLResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var REMINDID = $('REMINDID');
	var REMINDTIME = $('REMINDTIME');
	var DEPARTMENTID = $('DEPARTMENTID');
	var DEPARTMENTNAME = $('DEPARTMENTNAME');
	var REMINDINFO = $('REMINDINFO');
	var USERNAME = $('USERNAME');

	REMINDID.value = results[0].text;
	REMINDTIME.value = results[1].text;
	DEPARTMENTID.value = results[2].text;
	DEPARTMENTNAME.value = results[3].text;
	REMINDINFO.value = results[4].text.replace("（K0+0米至K0+0米）","");
	USERNAME.value = results[5].text;
}

function updatePoliceRemind(){
	var remindId = $("remindId").value;
	var DEPARTMENTID = $("DEPARTMENTID").value;
	var DEPARTMENTNAME = $("DEPARTMENTNAME").value;
	var REMINDINFO = $("REMINDINFO").value;
	
	var url = "dynamicinfo.policeRemind.updateRemindInfo.d"
		url = encodeURI(url);
		var params = {};
		params["remindId"] = remindId;
		params["DEPARTMENTID"] = DEPARTMENTID;
		params["DEPARTMENTNAME"] = DEPARTMENTNAME;
		params["REMINDINFO"] = REMINDINFO;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : function(){
				window.close();
				}
		});
}
//设置页面只读
function setReadOnly(page){
	var DEPARTMENTNAME = $("DEPARTMENTNAME");
	var REMINDINFO = $("REMINDINFO");
	var REMINDTIME = $("REMINDTIME");
	
	if (page == "") {
		DEPARTMENTNAME.disabled = true;
		REMINDINFO.disabled = true;
		REMINDTIME.disabled = true;
	} else if (page == "2") {
		DEPARTMENTNAME.disabled = false;
		REMINDINFO.disabled = false;
		REMINDTIME.disabled = true;
	} else if (page == "1") {
		DEPARTMENTNAME.disabled = true;
		REMINDINFO.disabled = true;
		REMINDTIME.disabled = true;
	} else if (page == "3") {
		DEPARTMENTNAME.disabled = true;
		REMINDINFO.disabled = true;
		REMINDTIME.disabled = true;
	} 
	else {
		alert("e_p(page):" + page);
	}
}

//遮罩效果实现代码
var LightOverDivId = "LightOverDivId";
function checkElement(divId){
	return document.getElementById(divId) || false;
}

//添加遮罩效果
function AddLightOver(){
	var checkDivId = checkElement(LightOverDivId);		
	if(checkDivId) {
		document.body.removeChild(LightOverDivId); 
	}
			
	var newDiv = document.createElement("div");
	newDiv.id = LightOverDivId;
	newDiv.style.position = "absolute";
	newDiv.style.zIndex = "1";
	_scrollWidth = Math.ceil(document.body.clientWidth);
	_scrollHeight =Math.ceil(document.body.clientHeight);
	newDiv.style.width = _scrollWidth + "px";
	newDiv.style.height = _scrollHeight + "px";
	newDiv.style.top = "0px";
	newDiv.style.left = "0px";
	newDiv.style.background = "#33393C";
	newDiv.style.filter = "alpha(opacity=20)";
	newDiv.style.opacity = "0.20";
	document.body.appendChild(newDiv);
}
//移除遮罩效果
function CloseLightOver(){
	document.body.removeChild(checkElement(LightOverDivId));
}

/**
 * 显示区域选择信息（过滤选项）<br/>
 */
function showRemindInfoFilter(userId) {
	var containDiv;
	if (!$('remindInfo_filter')) {
		containDiv = document.createElement('div');
		containDiv.id = 'remindInfo_filter';
		document.body.appendChild(containDiv);	
		$('remindInfo_filter').setStyle({
			position : 'absolute',
			zIndex : '20000',
			width : '480px',
			height : '170px',
			marginLeft : "-350px",
			marginTop : "-200px",
			background : '#F0FFFF',
			border : '1px solid #000'
//			left : '50%',
//			top : '50%'
		})
		var pos = getPosition(480,190);
		containDiv.style.left = pos.left;
		containDiv.style.top = pos.top;
		
		var titleDiv = getTitleDiv();
		var bottomDiv = getBottomDiv();
		bottomDiv.style.marginTop = '5px';
		
		containDiv.appendChild(titleDiv);
		var middleDiv;
		middleDiv = document.createElement('div');
		middleDiv.id = 'middleDiv_filter';

		containDiv.appendChild(middleDiv);
		containDiv.appendChild(bottomDiv);
		
		var content_area = getAreaDiv();
		//暂时取消该功能
//		var content_road = getRoadLevelDiv();
//		var content_type = getRimindTypeDiv();	
		
		var content = '<div style="width:100%;height:100%;">' +
		'<hr style="text-align: center; margin-left =0px; margin-top = 0px; border: 1px dashed; height: 1px; width: 100%;">'+
		'<table class="titleTopBack" style="width:100%;height:100%;" border="1" cellpadding="0" cellspacing="0">'+
		'<TBODY><tr style="width:100%;height:100%;"><td valign="top">'+
		content_area+
		'</td>'+
//		'<td valign="top">'+
//		content_road+
//		'</td><td valign="top">'+
//		content_type+
//		'</td>'+
		'<tr></tbody></table>'+
		'</div>';
		middleDiv.innerHTML=content;	
		getDefaultSetting();
	}else{
		$('remindInfo_filter').style.display = "inline";
	}
}

/**
 * Created by Liuwx 
 * 2011-08-17
 * 创建标题层
 * @returns {___titleDiv14}
 */
function getTitleDiv(){
	var titleDiv = null;
	if (!$('titleDiv_filter')) {
		titleDiv = document.createElement('div');
		titleDiv.id = 'titleDiv_filter';
	}else{
		titleDiv = $('titleDiv_filter');
	}
	titleDiv.style.width = "100%";
	titleDiv.style.height = "12px";
	titleDiv.style.background = "#a5d3ef";
	
	var titleTable = document.createElement('table');
	titleTable.setAttribute("class", "titleTopBack");
	titleTable.style.width = "100%";
	titleTable.style.height = "10px";
	titleTable.setAttribute("border", "0");
	titleTable.setAttribute("cellpadding", "0");
	titleTable.setAttribute("cellspacing", "0");
	titleDiv.appendChild(titleTable);
	
	var titleTBODY = document.createElement('TBODY');
	titleTable.appendChild(titleTBODY);
	
	var titleTR = document.createElement('tr');
	titleTR.style.width = "100%";
	titleTR.style.height = "10px";
	titleTBODY.appendChild(titleTR);
	
	var titleTD = document.createElement('td');
	titleTD.setAttribute("align","left");
	titleTD.setAttribute("width","85%");
	titleTR.appendChild(titleTD);
	
	var titleSpan = document.createElement('span');
//	titleSpan.style.marginLeft = "10px";
	titleTD.appendChild(titleSpan);
	
	var titleBig = document.createElement('big');
	titleSpan.appendChild(titleBig);
	
	var titleB = document.createElement('b');
//	titleB.innerText = "过滤条件选择";
	titleB.innerText = "区域选择";
	titleBig.appendChild(titleB);
	
	var titleTD2 = document.createElement('td');
	titleTD2.setAttribute("align","right");
	titleTD2.setAttribute("width","10%");
	titleTR.appendChild(titleTD2);
	
	var titleImg = document.createElement('img');
	titleImg.setAttribute("src","../../image/button/btnClose.png");
	titleImg.setAttribute("alt","关闭");
	titleImg.style.cursor = "hand";
//	titleImg.style.marginRight = "10px";
	titleImg.onclick = function(){restore();$('remindInfo_filter').style.display = "none";CloseLightOver();synchDynamicInfo();};
	titleTD2.appendChild(titleImg);
	return titleDiv;
}

/**
 * Created by Liuwx 
 * 2011-08-17
 * 创建底部层
 * @returns {___bottomDiv19}
 */
function getBottomDiv(){
	var bottomDiv = null;
	if (!$('bottomDiv_filter')) {
		bottomDiv = document.createElement('div');
		bottomDiv.id = 'bottomDiv_filter';
	}else{
		bottomDiv = $('bottomDiv_filter');
	}
	bottomDiv.style.width = "100%";
	bottomDiv.style.height = "12px";
//	bottomDiv.style.background = "#a5d3ef";
	
	var bottomable = document.createElement('table');
	bottomable.setAttribute("class", "titleTopBack");
	bottomable.style.width = "100%";
	bottomable.style.height = "10px";
	bottomDiv.appendChild(bottomable);
	
	var bottomTBODY = document.createElement('TBODY');
	bottomable.appendChild(bottomTBODY);
	
	var bottomTR = document.createElement('tr');
	bottomTR.style.width = "100%";
	bottomTR.style.height = "10px";
	bottomTR.style.display = "none";
	bottomTBODY.appendChild(bottomTR);
	
	var bottomTD = document.createElement('td');
	bottomTD.setAttribute("align","left");
	bottomTD.setAttribute("width","85%");
	bottomTR.appendChild(bottomTD);

	var bottomInput = document.createElement('input');
	bottomInput.setAttribute("id","isDefault");
	bottomInput.setAttribute("type","checkbox");
	bottomInput.setAttribute("name","isDefault");
	bottomTD.appendChild(bottomInput);
	
	var bottomSpan = document.createElement('span');
	bottomSpan.innerText = "设为默认";
	bottomTD.appendChild(bottomSpan);
	
	var bottomTR2 = document.createElement('tr');
	bottomTR2.style.width = "100%";
	bottomTR2.style.height = "10px";
	bottomTBODY.appendChild(bottomTR2);
	
	var bottomTD2 = document.createElement('td');
	bottomTD2.setAttribute("align","center");
	bottomTD2.setAttribute("width","100%");
	bottomTD2.style.borderStyle = "solid";
	bottomTD2.style.borderWidth = "2px 0px 0px 0px";
	bottomTR2.appendChild(bottomTD2);
	
	var bottomImgOk = document.createElement('img');
	bottomImgOk.setAttribute("src","../../image/button/btnOK.gif");
	bottomImgOk.setAttribute("alt","确认");
	bottomImgOk.style.cursor = "hand";
//	bottomImgOk.style.marginRight = "60%";
	bottomImgOk.onclick = function(){saveDefaultSetting();};
	bottomTD2.appendChild(bottomImgOk);
	
	var bottomImgCancle = document.createElement('img');
	bottomImgCancle.setAttribute("src","../../image/button/btncancle.gif");
	bottomImgCancle.setAttribute("alt","取消");
	bottomImgCancle.style.cursor = "hand";
	bottomImgCancle.style.marginLeft = "50px";
	bottomImgCancle.onclick = function(){restore();$('remindInfo_filter').style.display = "none";CloseLightOver();synchDynamicInfo();};
	bottomTD2.appendChild(bottomImgCancle);

	return bottomDiv;
}

/**
 * Created by Liuwx 2011-8-17
 * 创建区域层
 * @returns {String}
 */
function getAreaDiv(){
	var City = {
//			"440000000000":"广东省交通管理局",
			"440100000000":"广州", 
			"440300000000":"深圳",
			"440600000000":"佛山",
			"441900000000":"东莞",
			"442000000000":"中山",
			"441300000000":"惠州",
			"440700000000":"江门",
			"440900000000":"茂名",
			"440800000000":"湛江",
			"440500000000":"汕头",
			"440400000000":"珠海",
			"441800000000":"清远",
			"441200000000":"肇庆",
			"445200000000":"揭阳",
			"440200000000":"韶关",
			"441700000000":"阳江",
			"441400000000":"梅州",
			"445100000000":"潮州",
			"441600000000":"河源",
			"441500000000":"汕尾",
			"445300000000":"云浮"
	};
	var area = "<div id='AreaContentDivId' style='width:100%;height:30px;'>";
	var areaContent = "<table style='border-style: solid;border-width:2px 0px 0px 0px;' cellpadding='1' cellspacing='1'>" +
			"<tr><td colspan=7 align='right' style='border-style: solid;border-width:0px 0px 2px 0px'>" +
//			"<b>区域选择：</b>" +
//			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			"<input type='checkbox' id='selectAllArea' onclick='selectAllCheckBox(this,\"AreaContentDivId\");'/>全省</td><tr><td>";
	var index = 1;
	for(var key in City){
		if(index == 0){
			areaContent += "<input type='checkbox' id='"+key+"' />"+City[key]+"</td></tr><tr><td>";
		}else{
			areaContent += "<input type='checkbox' id='"+key+"' />"+City[key]+"&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		if(index%7 == 0 && index != 21){
			areaContent += "</td></tr><tr><td>";
		}
		index++;
	}
	
	area += areaContent + "</td></tr></table></div>";
	return area;
}

/**
 * Created by Liuwx 2011-8-17
 * 创建道路类型层
 * //暂时取消该功能
 * @return {String}
 */
function getRoadLevelDiv(){
	var Road = {
			"1":"高速", 
			"2":"国道",
			"3":"省道",
			"4":"其他"
	};
	var area = "<div id='RoadLevelDivId' style='width:100%;height:10px;'>";
	var areaContent = "<table style='border-style: solid;border-width:2px 0px 0px 0px;' cellpadding='1' cellspacing='1'>" +
			"<tr><td style='border-style: solid;border-width:0px 0px 2px 0px'><b>道路类型：</b></td><tr><td>";
	var index = 1;
	for(var key in Road){
		areaContent += "<input type='checkbox' id='"+key+"' />"+Road[key]+"";
		if(index%1 == 0 && index != 4){
			areaContent += "</td></tr><tr><td>";
		}
		index++;
	}
	
	area += areaContent + "</td></tr></table></div>";
	return area;
}

/**
 * Created by Liuwx 2011-8-17
 * 创建警情类型层
 * //暂时取消该功能
 * @returns {String}
 */
function getRimindTypeDiv(){
	var Type = {
			"001024":"重特大事故", 
			"001002":"交通拥堵",
			"001023":"施工占道"
	};
	var area = "<div id='RimindTypeDivId' style='width:100%;height:10px;'>";
	var areaContent = "<table style='border-style: solid;border-width:2px 0px 0px 0px;' cellpadding='1' cellspacing='1'>" +
			"<tr><td style='border-style: solid;border-width:0px 0px 2px 0px'><b>警情类型：</b></td><tr><td>";
	var index = 1;
	for(var key in Type){
		areaContent += "<input type='checkbox' id='"+key+"' />"+Type[key]+"";
		if(index%1 == 0 && index != 3){
			areaContent += "</td></tr><tr><td>";
		}
		index++;
	}
	
	area += areaContent + "</td></tr></table></div>";
	return area;
}

/**
 * Created by Liuwx 2011-8-17
 * 获取用户默认设置
 */
function getDefaultSetting() {
	var url = "dynamicinfo.userSetting.getUserSetting.d";
	url = encodeURI(url);
	var params = {};
	params["user_code"] = $("user_code")?$("user_code").value:"";
	params["function_id"] = "111111";
	params["model_name"] = "DynamicInfo.jsp";
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showUserDefaultSettingResponseXML
	});	
}

/**
 * Created by Liuwx 2011-8-17
 * 获取用户默认设置返回的XML文档，并且设置页面元素值
 */
function showUserDefaultSettingResponseXML(xmlHttp){
	var settings = new Array();
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row");
	for(var i = 0 ; i < results.length ; i++){
		var value = false;
		if(results[i].getAttribute("value") == "1"){
			value = true;
		}else if(results[i].getAttribute("value") == "0"){
			value = false;
		}
		settings[results[i].getAttribute("id")] = value;
	}
	//用户保存有默认值时复选框勾上
	//现取消默认值保存数据库的复选框，暂时不开放此功能。
//	if(settings != null && settings != undefined && settings.length > 0){
//		$("isDefault").checked = true;
//	}
	
	// 区域默认设置
	var areaCheckbox = getElements("AreaContentDivId", "input", "checkbox");
	for(var i = 0;i < areaCheckbox.length ;i++){
		for(var key in settings){
			if(key == areaCheckbox[i].id){
				areaCheckbox[i].checked = settings[key];
			}
		}
	}
	
	// 道路等级默认设置
	var roadLevelCheckbox = getElements("RoadLevelDivId", "input", "checkbox");
	for(var i = 0;i < roadLevelCheckbox.length ;i++){
		for(var key in settings){
			if(key == roadLevelCheckbox[i].id){
				roadLevelCheckbox[i].checked = settings[key];
			}
		}
	}
	
	// 警情类型默认设置
	var remindTypeCheckbox = getElements("RimindTypeDivId", "input", "checkbox");
	for(var i = 0;i < remindTypeCheckbox.length ;i++){
		for(var key in settings){
			if(key == remindTypeCheckbox[i].id){
				remindTypeCheckbox[i].checked = settings[key];
			}
		}
	}
}

/**
 * Created by Liuwx 2011-8-17
 * 保存用户默认设置
 */
function saveDefaultSetting() {
	var insertOrUpdate = 0;
	var isDefault = false;
	if (document.getElementById("isDefault")) {
		$("isDefault").checked = false;
		isDefault = document.getElementById("isDefault").checked;
	}
	
	if (isDefault) {//设为默认
		var params = {};
		var key_value = "";
		// 区域默认设置
		var areaCheckbox = getElements("AreaContentDivId", "input", "checkbox");
		for ( var i = 0; i < areaCheckbox.length; i++) {
			key_value += areaCheckbox[i].id + "=" + (areaCheckbox[i].checked?"US10001":"US10002") + ","
		}
		// 道路等级默认设置
		var roadLevelCheckbox = getElements("RoadLevelDivId", "input",
				"checkbox");
		for ( var i = 0; i < roadLevelCheckbox.length; i++) {
			key_value += roadLevelCheckbox[i].id + "=" + (roadLevelCheckbox[i].checked?"US10001":"US10002") + ","
		}
		// 警情类型默认设置
		var remindTypeCheckbox = getElements("RimindTypeDivId", "input",
				"checkbox");
		for ( var i = 0; i < remindTypeCheckbox.length; i++) {
			key_value += remindTypeCheckbox[i].id + "=" + (remindTypeCheckbox[i].checked?"US10001":"US10002") + ","
		}
		
		// 保存设置
		var url = "dynamicinfo.userSetting.saveUserSetting.d";
		url = encodeURI(url);
		var params = {};
		params["setting_id"] = "";
		params["user_code"] = $("user_code")?$("user_code").value:"";
		params["function_id"] = "111111";
		params["model_name"] = "DynamicInfo.jsp";
		params["element_key_value"] = key_value;
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : showUserSettingResponseXML
		});
	}else{//仅用于此次过滤
		//保存参数为全局参数，以便在其他地方调用
		G_Filter_params = getParameters();
		getPoliceRemindHtml(getParameters());
		G_array_resotre = getVaulesBeforeChange();//保存复选框改变之前的值
		$('remindInfo_filter').style.display = "none";
		CloseLightOver();
		synchDynamicInfo();
	}
}

/**
 * Created by Liuwx 2011-08-24
 * @param xmlHttp
 */
function showUserSettingResponseXML(xmlHttp) {
	if (xmlHttp.responseText == 'success') {
		alert("用户默认设置保存成功！");
		getPoliceRemindHtml(getParameters());
		$('remindInfo_filter').remove();CloseLightOver();synchDynamicInfo();
	} else {
		alert("用户默认设置保存失败！");
		$('remindInfo_filter').remove();CloseLightOver();synchDynamicInfo();
	}
}

/**
 * Created by Liuwx 2011-8-30
 * 获取默认设置的参数值
 * @returns G_Filter
 */
function getParameters(){
	var G_Filter = {};
	// 区域默认设置
	var areaCheckbox = getElements("AreaContentDivId", "input", "checkbox");
	var areaParams = "";
	for ( var i = 0; i < areaCheckbox.length; i++) {
		if(areaCheckbox[i].checked){
			areaParams += areaCheckbox[i].id.substring(0,4) + ",";//默认是采用支队编码，但是选取是包括其下属大队
		}
	}
	
	G_Filter["areaParams"] = areaParams;
	//暂时取消该功能
//	// 道路等级默认设置
//	var roadLevelCheckbox = getElements("RoadLevelDivId", "input",
//			"checkbox");
//	var levelParams = "";
//	for ( var i = 0; i < roadLevelCheckbox.length; i++) {
//		if(roadLevelCheckbox[i].checked){
//			levelParams += roadLevelCheckbox[i].id + ",";
//		}
//	}
//	G_Filter["levelParams"] = levelParams;
//	// 警情类型默认设置
//	var remindTypeCheckbox = getElements("RimindTypeDivId", "input",
//			"checkbox");
//	var typeParams = "";
//	for ( var i = 0; i < remindTypeCheckbox.length; i++) {
//		if(remindTypeCheckbox[i].checked){
//			typeParams += remindTypeCheckbox[i].id + ",";
//		}
//	}
//	G_Filter["typeParams"] = typeParams;
	return G_Filter;
}

/**
 * Created by Liuwx 2011-8-17
 * 遍历元素
 * @param parentId 需遍历查找的元素的父节点——如div
 * @param childrenType 需遍历查找的元素——如input
 * @param elementType //元素类型属性——如checkbox
 * @return
 */
function getElements(parentId, childrenType,elementType) {
	var elements = new Array();
	var parent,children;
	if(parentId && childrenType){
		parent = document.getElementById(parentId);
		children = parent.getElementsByTagName(childrenType); // array
	}
	
	if(elementType && children){
		for( var i = 0 ; i < children.length ; i++ ){
			if( children[i].type == elementType ){
				elements.push(children[i]);
			}
		}
	}
	return elements;
}

/**
 * Created by Liuwx 2011-8-30
 * 还原层中的复选框改变之前的值
 */
var G_array_resotre = new Array();//全局变量，保存复选框改变之前的值。
function restore(){
	var values = G_array_resotre;
	var elements = getElements("middleDiv_filter", "input", "checkbox");
	for(var i = 0;i < elements.length ;i++){
		for(var id in values){
			if(id == elements[i].id){
				elements[i].checked = values[id];
			}
		}
	}
}

/**
 * Created by Liuwx 2011-8-30
 * 备份层中的复选框改变之前的值
 * @returns values
 */
function getVaulesBeforeChange(){
	var values = new Array();
	var elements = getElements("middleDiv_filter", "input", "checkbox");
	for ( var i = 0; i < elements.length; i++) {
		values[elements[i].id] = elements[i].checked;
	}
	return values;
}

/**
 * Created by Liuwx 2011-8-30
 * 全选事件触发方法
 * @param obj //事件源对象
 * @param id //元素ID
 * @return
 */
function selectAllCheckBox(obj,id){
	var elements = getElements(id,'input','checkbox');
	var checked = this.checked = !(this.checked);
	if(checked){
		for ( var i = 0; i < elements.length; i++) {
			elements[i].checked = true;
		}
	}else{
		for ( var i = 0; i < elements.length; i++) {
			elements[i].checked = false;
		}
	}
}

/**
 * Created by Liuwx 2011-8-17
 * 获取鼠标事件点击位置
 * @param objWidth
 * @param objHight
 * @returns {___anonymous25499_25517}
 */
function getPosition(objWidth,objHight) {
	var ev = window.event || arguments[0]; //获取事件	
	var src = ev.srcElement || ev.target;//获取事件源对象
	var srcWidth = parseInt(src.offsetWidth);//对象自身的宽度
	var srcHeight = parseInt(src.offsetHeight);//对象自身的高度
	var eleTop = parseInt(src.offsetTop);//事件源绝对坐标Top
	var eleLeft = parseInt(src.offsetLeft);//事件源绝对坐标left
	var docWidth = parseInt(document.body.offsetWidth);//文档宽度
	var docHeight = parseInt(document.body.offsetHeight);  //文档高度

	//事件源对象绝对坐标
    while(src=src.offsetParent) 
    { 
    	eleTop += parseInt(src.offsetTop);  
    	eleLeft += parseInt(src.offsetLeft); 
    };
  
    var left = eleLeft;
    var top = eleTop;
    
    if(objWidth && objHight){
    	left = eleLeft-srcWidth;
    	top = eleTop + objHight;
    }

    return {left:left,top:top};
}

//Modification finished

function removePoliceRemid () {
    //Modified by Liuwx 2012年3月14日18:27:42
	//var conCtrl= window.document.getElementById("daynameicInfo");
    //conCtrl.innerHTML = "";
    //Modification finished
}

//2011-08-31更新，刘维兴