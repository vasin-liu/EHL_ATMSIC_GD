/**
 * 
	* 方法名称：toolbarAddEvent<br>
	* 方法描述：  <br>
	* @param oEle
	* @param sEventName
	* @param fnHandler<br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:55  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:55   <br>
	* 修改备注：    <br>
 */
function toolbarAddEvent(oEle, sEventName, fnHandler)
{
	if(oEle.attachEvent)
	{
		oEle.attachEvent('on'+sEventName, fnHandler);
	}
	else
	{
		oEle.addEventListener(sEventName, fnHandler, false);
	}
}

/**
 * 
 */
function toolbarDiv()
{
	createToolbarDiv();
	var oDiv=document.getElementById('toolbar_float_layer');
	var oBtnMin=document.getElementById('btn_notices_min');
	var oDivContent=document.getElementById('notices_content');
	
	var iMaxHeight = 0;
	var iMaxWidth = 0;
	
	var isIE6=window.navigator.userAgent.match(/MSIE 6/ig) && !window.navigator.userAgent.match(/MSIE 7|8/ig);
	
	oDiv.style.display='block';
	iMaxWidth=oDivContent.offsetWidth;
	
	if(isIE6)
	{
		oDiv.style.position='absolute';
		repositionAbsolute();
		toolbarAddEvent(window, 'scroll', repositionAbsolute);
		toolbarAddEvent(window, 'resize', repositionAbsolute);
	}
	else
	{
		oDiv.style.position='fixed';
		repositionFixed();
		toolbarAddEvent(window, 'resize', repositionFixed);
	}
	
	oBtnMin.isMax=true;
	oBtnMin.onclick=function(){
		var width = oBtnMin.isMax?'0px':'320px';
		oBtnMin.isMax = oBtnMin.isMax==true?false:true;
		jQuery(oDivContent).animate({width:width},1000,
		function(){
			if(oBtnMin.isMax){
				jQuery("#btn_notices_min").attr('class','max');
				jQuery("#btn_notices_min").attr('src','notices/images/min.png');
				jQuery("#btn_notices_min").attr('alt','收缩');
			}else{
				jQuery("#btn_notices_min").attr('class','min');
				jQuery("#btn_notices_min").attr('src','notices/images/max.png');
				jQuery("#btn_notices_min").attr('alt','展开');
			}
		});
	}
};

/**
 * 
	* 方法名称：repositionAbsolute<br>
	* 方法描述：  <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:16  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:16   <br>
	* 修改备注：    <br>
 */
function repositionAbsolute()
{
	var oDiv=document.getElementById('toolbar_float_layer');
	var left=document.body.scrollLeft||document.documentElement.scrollLeft;
	var top=document.body.scrollTop||document.documentElement.scrollTop;
	var width=document.documentElement.clientWidth;
	var height=document.documentElement.clientHeight;
//	oDiv.style.left=left+width-oDiv.offsetWidth+'px';
//	oDiv.style.top=top+height-oDiv.offsetHeight+'px';
	oDiv.style.bottom = 0;
	oDiv.style.right = 0;
}

/**
 * 
	* 方法名称：repositionFixed<br>
	* 方法描述：  <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:05  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:05   <br>
	* 修改备注：    <br>
 */
function repositionFixed()
{
	var oDiv=document.getElementById('toolbar_float_layer');
	var width=document.documentElement.clientWidth;
	var height=document.documentElement.clientHeight;
//	oDiv.style.left=width-oDiv.offsetWidth+'px';
//	oDiv.style.top=height-oDiv.offsetHeight+'px';
	oDiv.style.bottom = 0;
	oDiv.style.right = 0;
}

function AutoScroll(obj){
    jQuery(obj).find("ul:first").animate({
        marginTop:"-28px"
    },1000,function(){
        jQuery(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
    });
}

jQuery(document).ready(function(){
	setInterval('AutoScroll("#notices_scrolling_div")',3000);
});

/**
 * 
 * 方法名称：createNoticesDiv<br>
 * 方法描述： <br>
 * <br>
 * 版本信息：Ver 1.1 <br>
 * **********************************<br>
 * 创建人：Vasin <br>
 * 创建时间：2012-2-6 下午1:43:05 <br>
 * ************ 修改历史 *************<br>
 * 修改人：Vasin <br>
 * 修改时间：2012-2-6 下午1:43:05 <br>
 * 修改备注： <br>
 */
function createToolbarDiv() {
	var root_Container, toolbar_ctrl_button,toolbar_container, toolbar_content;
	root_Container = document.getElementById('toolbar_float_layer');
	toolbar_container = document.createElement("div");
	toolbar_container.id = "toolbar_container";
	toolbar_container.className = "toolbar_container";

	notices_content = document.createElement("div");
	notices_content.id = "notices_content";
	notices_content.className = "notices_content";
	notices_content.innerHTML = "<div class='notices_list' id='notices_scrolling_div'>" +
			"<span style='font-size:12px;'>暂无公告信息。</span>" +
			"</div>";

	notices_button = document.createElement("div");
	notices_button.id = "notices_button";
	notices_button.className = "notices_button";
	notices_button.innerHTML = "<img id=\"btn_notices_min\" class=\"min\" src='notices/images/min.png' alt='收缩'></img>" +
			"<img id=\"btn_notices_img\" class=\"notices_img\" src='notices/images/notices.png' alt='系统公告'></img>" +
			"<span style='margin-left:52px;font-size:12px;'>公告:</span>";
			
	toolbar_container.appendChild(notices_button);
	toolbar_container.appendChild(notices_content);
	root_Container.appendChild(toolbar_container);

	getCurrentNoticesList();
}

/**
 * 
	* 方法名称：getCurrentNoticesList<br>
	* 方法描述：  <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:05  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:05   <br>
	* 修改备注：    <br>
 */
function getCurrentNoticesList(){
	var today = new Date();
	var loginName = jQuery("#loginName").val();
	var params = "noticeDay=" + today + "&userName=" + loginName;
	var url = 'common.systemNotices.getSystemNoticesList.d';
	params = encodeURI(params);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseNotices});
}

/**
 * 
	* 方法名称：showResponseNotices<br>
	* 方法描述：  <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:05  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:05   <br>
	* 修改备注：    <br>
 */
function showResponseNotices(xmlHttp) {
	var text = xmlHttp.responseText;
	var json;
	try {
		json = jQuery.parseJSON(text);
	} catch (e) {
		alert("JSON对象转换异常!");
	}
	if (!json) {
		alert("获取系统公告列表数据异常。");
	}
	var loginName = jQuery("#loginName").val();
	var notice_link = "<ul>";
	var un_Read_Notices = new Array();
	// 没有公告则最小化
	var oDivContent = jQuery('#notices_content');
	var oBtnMin = jQuery('#btn_notices_min').get(0);
	if (!(json.rows) || (json.rows.length == 0)) {
		jQuery(oDivContent).animate({
					width : 0
				}, 1000, function() {
					oBtnMin.isMax = false;
					jQuery("#btn_notices_min").attr('class','max');
					jQuery("#btn_notices_min").attr('src','notices/images/max.png');
					jQuery("#btn_notices_min").attr('alt','展开');
				});
	} else {
		for (var i = 0; i < json.rows.length; i++) {
			var title_str = json.rows[i].cell[1];
			if (title_str.length > 15) {
				title_str = title_str.substring(0, 15) + "……";
			}
			notice_link += "<li><a href='javascript:' onclick='showCurrentNoticeDetail("
					+ json.rows[i].cell[0] + ",1);'>" + title_str + "</a>";
			// 将未读的公告推入un_Read_Notices数组
			if (!(json.rows[i].cell[2])
					|| (json.rows[i].cell[2].indexOf(loginName) == -1)) {
				un_Read_Notices.push(json.rows[i]);
			}
		}

		notice_link += "</ul>";
		var content = document.getElementById("notices_scrolling_div");
		content.innerHTML = notice_link;
		if (un_Read_Notices.length > 0) {
			addNotification(un_Read_Notices);
		} else if (jQuery("#latest_notices_div")) {
			jQuery("#latest_notices_div").slideUp("slow");
		}
	}
}

/**
 * 
	* 方法名称：addNotification<br>
	* 方法描述： 弹出最新未读系统公告 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:05  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:05   <br>
	* 修改备注：    <br>
 */
function addNotification(un_Read_Notices){
	var latest_notices;
	if(document.getElementById("latest_notices_div")){
		jQuery("#latest_notices_div").remove();
	}
	latest_notices = document.createElement("div");
	latest_notices.id = "latest_notices_div";
	latest_notices.className = "latest_notices_div";
	
	var title_div = document.createElement("div");
	title_div.className = "title";
	title_div.innerHTML = "<span style='margin-top:3px;margin-left:3px;width:275px;'><b>最新未读系统公告</b></span>";
	latest_notices.appendChild(title_div);
	
	var close_img = document.createElement("img");
	close_img.id = "btn_notices_close";
	close_img.src = "notices/images/close.png";
	close_img.className = "close";
	close_img.onclick = function(){
		jQuery("#latest_notices_div").slideUp("slow");
	};
	title_div.appendChild(close_img);
	
	var content_div = document.createElement("div");
	content_div.id = "latest_notices_content_div";
	content_div.className = "content";
	var un_read_notices_html = "<ul style='font-size:12px;'>";
	for (var i = 0; i < un_Read_Notices.length; i++) {
		var title_str = un_Read_Notices[i].cell[1];
		if(title_str.length > 15){
			title_str = title_str.substring(0,15)+"……";
		}
		un_read_notices_html += "<li><a href='javascript:' onclick='showCurrentNoticeDetail(" 
						     + un_Read_Notices[i].cell[0] + ",1);'><span  style='margin-right:2px;'><b>"
						     + (i+1) + " . </b></span><span> " + title_str + "</span></a>"
						     + "</li>";
	}
	un_read_notices_html += "</ul>";//未读公告内容
	content_div.innerHTML = un_read_notices_html;
	latest_notices.appendChild(content_div);
	
	document.body.appendChild(latest_notices);
	jQuery("#latest_notices_div").slideDown("slow");
}

/**
 * 
	* 方法名称：showCurrentNoticeDetail<br>
	* 方法描述：  <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:05  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:05   <br>
	* 修改备注：    <br>
 */
function showCurrentNoticeDetail(noticeId, insrtOrUpdt) {
	if (noticeId != null && noticeId != undefined && noticeId != "") {
		var url = "notices/jsp/systemNotices.jsp?tmp=" + Math.random()
				+ "&insrtOrUpdt=" + insrtOrUpdt + "&noticeId=" + noticeId;
		getCurrentNoticeDetail(noticeId,insrtOrUpdt);
	} else {
		alert("传入参数有误，请检查是否传入正确参数！");
	}
}

/**
 * 
 * 方法名称：getCurrentNoticeDetail<br>
 * 方法描述： <br>
 * <br>
 * 版本信息：Ver 1.1 <br>
 * **********************************<br>
 * 创建人：Vasin <br>
 * 创建时间：2012-2-6 下午1:43:05 <br>
 * ************ 修改历史 *************<br>
 * 修改人：Vasin <br>
 * 修改时间：2012-2-6 下午1:43:05 <br>
 * 修改备注： <br>
 */
function getCurrentNoticeDetail(noticeId,insrtOrUpdt){
	var loginName = jQuery("#loginName").val();
	var params = "noticeId=" + noticeId+"&insrtOrUpdt="+insrtOrUpdt+"&userName="+loginName;
	var url = 'common.systemNotices.getSystemNoticesDetail.d';
	params = encodeURI(params);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseNoticesDetail});
}

/**
 * 
	* 方法名称：showResponseNoticesDetail<br>
	* 方法描述：  <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-6 下午1:43:05  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-6 下午1:43:05   <br>
	* 修改备注：    <br>
 */
function showResponseNoticesDetail(xmlHttp){
	var text = xmlHttp.responseText;
	var json;
	try{
		json = jQuery.parseJSON(text);
	}catch(e){
		alert("JSON对象转换异常!");
	}
	if(!json){
		alert("获取数据异常。");
	}
	var title_html = "<div class='notice_detail_title'>" +
			"<span style='font-size:15px;margin-top:3px;margin-left:3px;width:625px;'>" +
			"<b>系统公告</b></span>" +
			"<img class='close' src='notices/images/close.png' alt='关闭' " +
			"onclick='getCurrentNoticesList();jQuery.unblockUI();'></div>";
	var content_html = "<div style='text-align:center;'>" +
			"<table id='current_notice_detail_table'><tr><td class='lableTd'>发布单位：</td>" +
			"<td>" +
			"<input class='input' type='text' id='release_department' value='"+
			json.rows[0].cell[5]+"' readonly /></td>" +
			"<td class='lableTd'>发布时间：</td><td>" +
			"<input class='input' type='text' id='release_time' value='"+
			json.rows[0].cell[3]+"' readonly /></td></tr>" +
			"<tr><td class='lableTd'>公告类型：</td><td>" +
			"<input class='input' type='text' id='notice_type' value='"+
			json.rows[0].cell[4]+"' readonly /></td></tr>" +
			"<tr><td class='lableTd'>公告标题：</td><td colspan='3'>" +
			"<input style='font-size:14px;width:520px;height:30px;line-height:30px;vertical-align:middle;' " +
			"type='text' id='notice_title' value='"+json.rows[0].cell[1]+"' readonly /></tr>" +
			"<tr><td class='lableTd'>公告内容：</td><td colspan='3'>" +
			"<textarea id='notice_content' style='font-size:14px;' rows='12' cols='67' readonly>"+
			json.rows[0].cell[2]+"</textarea>" +
			"</td></tr></table></div>";
	
	jQuery.blockUI({
				message : title_html+content_html,
				css : {
					padding : 0,
					margin : 0,
					width : '650px',
					height : '350px',
					top : (jQuery(window).height()-320) / 2 + 'px',
					left : (jQuery(window).width()-650) / 2 + 'px',
					textAlign : 'center',
					color : '#000',
					border : '3px solid #aaa',
					backgroundColor : '#fff',
					cursor : 'auto'
				}
			});
}