//-----------------------------------------------------------------------------//
/**
 * 过滤oracle字符串中特殊字符
 * @param {Object} object
 * @return {TypeName} 
 */
function filterOracle(object){
	var type = typeof(object);
	if(type == "string"){
		return object.replace(/'/g,"''");
	}else if(object instanceof Array){
		for(var i=0;i<object.length;i++){
			object[i] = filterOracle(object[i]);
		}
	}else if(type == "object"){
		for(var attr in object){
			object[attr] = filterOracle(object[attr]);
		}
	}
	return object;
}

(function(){
	if(!window.validateInput){
		window.validateInput = function(){
			var reg = /[\^&<]/;
			var inputs = document.getElementsByTagName("input"),input;
			for(var i = 0;i<inputs.length;i++){
				input = inputs[i];
				if(input.type == "text" && input.value && !input.readOnly && !input.disabled){
					if(reg.test(input.value)){
						alert('不可输入特殊字符："^"、"&"、"<"');
						input.focus();
						return false;
					}
				}
			}
			var textareas = document.getElementsByTagName("textarea"),textarea;
			for(var i = 0;i<textareas.length;i++){
				textarea = textareas[i];
				if(textarea.value && !textarea.readOnly && !textarea.disabled){
					if(reg.test(textarea.value)){
						alert('不可输入特殊字符："^"、"&"、"<"');
						textarea.focus();
						return false;
					}
				}
			}
			return true;
		}
	}
})();

//-----------------------------------------------------------------------------//
function getQueryTd(text, width) {
	width = width ? "width='" + width + "%'" : "";
	var td = "<td " + width + "  class='td_r_b td_font' align='center' >";
	td += text;
	td += "</td>";
	return td;
}

function getQueryThead(text) {
	var td = "<td class='td_r_b td_font thead' align='center'>";
	td += text;
	td += "</td>";
	return td;
}

function getQueryTr(td, height) {
	return "<tr align='center' height='" + (height || 25) + "'>" + td + "</tr>";
}

function getQueryTitle() {
	var title = "<span class='currentLocationBold'>查询结果</span>";
	title = "<div style='text-align:center;width:100%;line-height:22px; float:left;'>"
			+ title + "</div>";
	return title;
}

function getQuerySubTitle(tindexs,scndescs) {
	if (tindexs && scndescs) {
		var tds = getQueryTd("序号");
		for ( var i = 0; i < tindexs.length; i++) {
			tds += getQueryTd(scndescs[tindexs[i]]);
		}
		return getQueryTr(tds);
	}
	return null;
}

function getValueAttr(value, width) {
	var style = "width:" + width + "px;";
	style += "overflow: hidden;white-space: nowrap;text-overflow:ellipsis;";
	return "<span title='" + value + "' style='" + style + "'>" + value + "</span>";
}

function getAttrByLimitLength(value, length) {
	var attrValue = value;
	if (value.length > length) {
		var attrMark = "..";
		attrValue = value.substring(0, length - attrMark.length) + attrMark;
	}
	return "<span title='" + value + "'>" + attrValue + "</span>";
}

function getQueryNull(length) {
	var queryNull = "<td class='td_r_b td_font' colspan='" + (length || 0)
			+ "' align='center'>此条件下没有数据</td>";
	queryNull = "<tr class='titleTopBack'>" + queryNull + "</tr>";
	return queryNull;
}

//-----------------------------------------------------------------------------//
(function(){
	var operates = {
		"0" : "添加",
		"1" : "查询",
		"2" : "查看",
		"3" : "处理",
		"4" : "删除"
	};
	var topic;
	var setTopic = function(){
		topic = document.title;
		for(var attr in operates){
			if(topic.indexOf(operates[attr]) != -1){
				topic = topic.replace(operates[attr],"");
				break;
			}
		}
	}
	
	var title;
	var setTitle = function(){
		 title = (operates[baseInfo.page]||"") + topic;
	}
	var showTitle = function(){
		var pageTitle = $("pageTitle");
		if(pageTitle && window.baseInfo){
			pageTitle.innerText = title;
		}
	}
	
	var openDetail_ = function(url, index, pindex) {
		if(!window.queryDatas ||!window.query || !title){
			alert("没有预定义全局变量");
			return;
		}
		if (index >= 0 && index < queryDatas.length) {
			url = url + "?page=" + pindex + "&title="+title;
			var feature = "dialogWidth:800px;";
			var pwin = window.showModalDialog(url, queryDatas[index], feature);
			if (pindex == 3) {
				query();
			}
		} else {
			alert("指定打开页面的索引无效！");
		}
	}
	
//	window.attachEvent("onload",function(){
//		//setTopic();
//		//setTitle();
//		/*
//		if(window.baseInfo){
//			var page = baseInfo.page;
//			if(page != "0" && page != "1"){
//				showTitle();
//			}
//		}
//		if(window.openDetail){
//			openDetail = function(index,pindex){
//				openDetail_(window.baseInfo.url,index,pindex);
//			}
//		}
//		*/
//	});
})()





























