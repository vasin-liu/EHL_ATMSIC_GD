/*
 * <pre>
 * 基本js 
 * 1.提供常用方法 
 * 2.模块常量定义
 * 3.操作常量定义
 * 4.基本业务对象 
 * 创建日期：2011-11-17
 * 完成日期：2011-11-29
 * 版本：1.0
 * 后续任务：负责文件引入
 * 注意事项：
 * 1.$(id).show()时，如果是在class样式中设置了隐藏，无法显示出来。
 * </pre>
 */

/**
 * 打开遮罩
 */
window.showShade = function() {
	var bgDiv = top.$('shade');
	if (bgDiv) {
		bgDiv.show();
	} else {
		var sWidth, sHeight;
		sWidth = top.document.body.offsetWidth;
		sHeight = screen.height;
		bgObj = top.document.createElement("iframe");
		bgObj.setAttribute('id', 'shade');
		bgObj.style.position = "absolute";
		bgObj.style.top = "0";
		bgObj.style.background = "#777";
		bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
		bgObj.style.opacity = "0.6";
		bgObj.style.left = "0";
		bgObj.style.width = sWidth + "px";
		bgObj.style.height = sHeight + "px";
		bgObj.style.zIndex = "200500";
		top.document.body.appendChild(bgObj);
	}
};

/**
 * 隐藏遮罩
 */
window.hideShade = function(opener) {
	if (opener) {
		var bgDiv = opener.top.$('shade');
		if (bgDiv) {
			bgDiv.hide();
		}
	}
};

/**
 * 打开窗口
 */
window.openCenter = function(url, feature) {
	if (url) {
		url += (url.indexOf("?") == -1) ? "?" : "&";
		url += "temp=" + Math.random();
	}
	url = encodeURI(url);
	feature = feature || {};
	feature.width = feature.width || 824;
	feature.height = feature.height || 500;
	var x = (screen.availWidth - feature.width) / 2;
	var y = (screen.availHeight - feature.height) / 2;
	feature.top = y;
	feature.left = x;
	feature.scrollbars = "1";
	feature.resizable = "1";
	var features = [];
	for ( var attr in feature) {
		features.push(attr + "=" + feature[attr]);
	}
	feature = features.join(",");
	return window.open(url, "", feature);
};

/**
 * <pre>
 * 重写打开窗口。打开子窗口前弹出遮罩，关闭子窗口时隐藏遮罩，
 * 当前窗口刷新或关闭时，关闭打开窗口
 * </pre>
 */
var _openCenter = window.openCenter;
window.openCenter = function(url, feature) {
	showShade();
	var child = _openCenter(url, feature);
	child.attachEvent("onunload", function() {
		hideShade(window);
	});
	top.attachEvent("onunload", function() {
		if (child) {
			child.close();
		}
	});
	return child;
};
/**
 * 模式窗口打开
 */
window.showCenter = function(url, feature) {
	if (url) {
		url += (url.indexOf("?") == -1) ? "?" : "&";
		url += "temp=" + Math.random();
	}
	feature = feature || {};
	feature.dialogWidth = feature.dialogWidth || 824;
	feature.dialogHeight = feature.dialogHeight || 500;
	var features = [];
	for ( var attr in feature) {
		features.push(attr + "=" + feature[attr]);
	}
	feature = features.join(";");
	window.child = window.showModalDialog(url, window, feature);
};
/**
 * 获取页面名称
 */
window.getPage = function(url) {
	url = url || window.location.href;
	var index = url.indexOf("?");
	if (index != -1) {
		url = url.substring(0, index);
	}
	index = url.lastIndexOf("/");
	var page = url.substring(index + 1);
	index = page.lastIndexOf(".");
	page = page.substring(0, index);
	return page;
};

window.baseInfo = window.baseInfo || {};
baseInfo.synTime = function(){
	if(baseInfo.time){
		baseInfo.syntime = new Date(Date.parse(baseInfo.time.replace(/-/g,"/")));
		setInterval(function(){
			baseInfo.syntime.setSeconds(baseInfo.syntime.getSeconds() + 1);
		},1000);
	}
};
baseInfo.synTime();


/** 模块定义 */
var $modules = {
	prmplan : {
		id : "",
		name : "prmplan",
		desc : "警民互动",
		request : "prmplan",
		childNodes : {
			"blog" : {
				id : "",
				name : "blog",
				desc : "流量饱和值",
				request : "blog"
			}
		}
	},
	general : {
		id : "",
		name : "general",
		desc : "综合信息",
		request : "general",
		childNodes : {
			"satuValue" : {
				id : "",
				name : "satuValue",
				desc : "流量饱和值",
				request : "satuValue"
			}
		}
	},
	help : {
		id : "",
		name : "help",
		desc : "用户帮助",
		request : "help",
		childNodes : {
			"leave" : {
				id : "",
				name : "leave",
				desc : "留言",
				request : "leave"
			},
			"faq" : {
				id : "",
				name : "faq",
				desc : "常见问题",
				request : "faq"
			}
		}
	},
	current : {
		root : null,
		child : null
	}
};

/**
 * 获取请求
 */
$modules.getRequest = function(operate) {
	var module = $modules.current.root;
	var node = $modules.current.child;
	var request = module.request + "." + node.request;
	request += "." + operates[operate].request + ".d";
	return request;
};

/**
 * 操作（描述、页面编号、请求方法、图片、应用场景）
 */
var $operates = {
	add : {
		desc : "添加",
		code : "0_new",
		page : "0",
		request : "insert"
	},
	query : {
		desc : "查询",
		code : "0_query",
		page : "1"
	},
	watch : {
		desc : "查看",
		code : "0_detail",
		page : "2",
		request : "getById",
		image : {
			enable : "btnck.gif",
			disable : "btnck.gif"
		},
		context : "1002"
	},
	deal : {
		desc : "处理",
		code : "0_edit,0_reply",
		page : "3",
		request : "deal",
		image : {
			enable : "btnedit1.gif",
			disable : "btnedit2.gif"
		},
		context : "1001"
	},
	modify : {
		desc : "修改",
		code : "0_edit",
		page : "4",
		request : "modifyById",
		image : {
			enable : "btnedit1.gif",
			disable : "btnedit2.gif"
		}
	},
	sign : {
		desc : "签收",
		page : "5",
		request : "sign"
	},
	"delete" : {
		code : "0_delete",
		desc : "删除",
		request : "deleteById",
		image : {
			enable : "btndelete1.gif",
			disable : "btndelete2.gif"
		}
	},
	print : {
		desc : "打印",
		request : "print"
	},
	"export" : {
		desc : "导出",
		request : "export"
	},
	reply : {
		desc : "回复",
		code : "0_reply"
	},
	saveas : {
		desc : "转存",
		code : "0_saveas"
	}
};
/**
 * 获取图片路径
 */
$operates.getImage = function(operate, isEnable) {
	var basePath = baseInfo.contextPath+"base/image/button/";
	var image = $operates[operate].image;
	if (isEnable) {
		basePath += image.enable;
	} else {
		basePath += image.disable;
	}
	return basePath;
};

/** 对象 */
var $objects = {
	info : "信息",
	word : "word文档",
	excel : "excel文档"
};

/** 操作结果 */
var $results = {
	success : "成功",
	failed : "失败"
};

/** 消息提示，消息提示一般由操作+对象/明细+操作结果组成，其他自定义。 */
var $prompts = {
	error : {
		unknow : "未知异常！",
		internet : "网络异常！",
		loadinfo : "加载数据异常！"
	},
	"delete" : "确认删除吗？"
}; 
/**
 * 显示消息提示
 */
$prompts.showPrompt = function(operate, object, result) {
	var prompt = operate + (object || "") + $objects.info;
	if (result == "true") {
		prompt += $results.success;
	} else if (result == "false") {
		prompt += $results.failed;
	} else {
		prompt = $prompts.error.internet;
	}
	alert(prompt);
};
/**
 * 提示之后，[关闭窗户|重新加载|进行查询]
 */
$prompts.afterPrompt = function(operate, result, object) {
	if (result == "true") {
		var parent = window.opener || window.dialogArguments;
		if (parent) {
			window.close();
		} else {
			if (operate == "add") {
				window.location.reload();
			}
		}
		if (parent) {
			object = "parent['" + object + "']";
		}
		eval(object + ".query("+parent.window.currPage+")");
	}
};
/**
 * 显示提示并进行提示之后该进行的操作
 */
$prompts.show = function(operate, object, result, callObject) {
	var opedesc = $operates[operate].desc;
	var objdesc = eval(object + ".metadata.desc")
			|| $modules.current.child.desc;
	callObject = callObject || object;
	$prompts.showPrompt(opedesc, objdesc, result);
	$prompts.afterPrompt(operate, result, callObject);
};
/**
 * 基本业务对象
 */
function BaseObject(metadata) {
	this.metadata = metadata;
}
BaseObject.prototype = {
	/** 获取界面元素的值 */
	getData : function(ids) {
		var object = this;
		var data = {}, el;
		if (ids instanceof Array) {
			for ( var i = 0; i < ids.length; i++) {
				el = object[ids[i]];
				if (el && el.value != undefined) {
					data[ids[i]] = el.value;
				}
			}
		} else {
			for ( var attr in object) {
				el = object[attr];
				if (el && el.value != undefined) {
					data[attr] = el.value;
				}
			}
		}
		return data;
	},
	/** 设置界面元素的值 */
	setData : function(data) {
		var object = this, el;
		for ( var attr in data) {
			el = object[attr];
			if (el && el.value != undefined) {
				el.value = data[attr];
			}
		}
	},
	/** 添加初始化 */
	addInit : function(params, name) {
		this.btnAdd = $("btnAdd");
		var _this = this;
		this.btnAdd.attachEvent("onclick", function() {
			_this.add(false, params, name);
		});
		this.btnAdd.style.display = "inline";
	},
	/** 进行添加 */
	add : function(isValid, params, name) {
		if (isValid || this.addCheck()) {
			var url = this.getRequest("add");
			params = params || this.getData();
			delete params.id;
			var _this = this;
			new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp) {
					_this.showPrompt("add", xmlHttp.responseText, name);
				}
			});
		}
	},
	/** 添加验证 */
	addCheck : function(ids) {
		if (ids) {
			var is = false;
			var el;
			for ( var i = 0; i < ids.length; i++) {
				el = this[ids[i]];
				if (el) {
					is = validates.check(el, this.metadata.items[ids[i]]);
					if (!is) {
						return false;
					}
				}
			}
		}
		return validateInput();
	},
	/** 查询初始化 */
	queryInit : function() {
		var _this = this;
		this.btnQuery = $("btnQuery");
		this.btnQuery.attachEvent("onclick", function() {
			_this.query();
		});
	},
	/** 进行查询 */
	query : function(sql,pageNum) {
		if (sql) {
			window.currPage = pageNum || 1;
			var name = this.metadata.objName;
			name += ".queryShowResult.call(" + name + ",xmlDoc)";
			PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
					name, this.metadata.pagingCount);
		}
	},
	/** 查询验证 */
	queryCheck : function() {
		return validateInput();
	},
	_queryShowResult : function(tindexs, operates, fnames, fdescs, change) {
		var queryDatas = this.queryDatas;
		// 设置默认值
		tindexs = tindexs || [ 1 ];
		operates = operates || [];
		change = change || function() {
		};
		var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='content'>";
		str += "<tr class='head'>";
		str += "<td width='5%' class='head'>序号</td>";
		for ( var i = 0; i < tindexs.length; i++) {
			str += BaseObject.getQueryThead(fdescs[tindexs[i]]);
		}
		for ( var i = 0; i < operates.length; i++) {
			str += BaseObject.getQueryThead(operates[i].name);
		}
		str += "</tr>";
		if (!queryDatas || queryDatas.length <= 0) {
			var length = tindexs.length + operates.length + 1;
			str += BaseObject.getQueryNull(length);
		} else {
			var queryData, tds, tdtext, fname;
			for ( var i = 0; i < queryDatas.length; i++) {
				queryData = queryDatas[i];
				tds = [ BaseObject.getQueryTd(i + 1) ];
				for ( var j = 0; j < tindexs.length; j++) {
					fname = fnames[tindexs[j]];
					tdtext = queryData[fname];
					tdtext = change(fname, queryData) || tdtext;
					tds.push(BaseObject.getQueryTd(tdtext));
				}
				
				for ( var j = 0; j < operates.length; j++) {
					tdtext = operates[j].show(queryData);
					tds.push(BaseObject.getQueryTd(tdtext));
				}
				str += BaseObject.getQueryTr(tds.join(""), i % 2 == 0);
			}
		}
		str += "</table>";
		return str;
	},
	/** 显示查询界面 */
	queryShowResult : function(xmldoc, tindexs, newOperates, fnames, fdescs,
			change) {
		fnames = fnames || this.metadata.columns;
		fdescs = fdescs || this.metadata.comments;
		this.querySetData(xmldoc, fnames);
		var str = this._queryShowResult(tindexs, newOperates, fnames, fdescs,
				change);
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = $("pageData");
		if(typeof str === "string"){
			str = BaseObject.getQueryTitle() + str;
			tabOjb.innerHTML = str;
		}
		Popup.prototype.hideTips();
	},
	/** 将查询数据格式由xml转换为json */
	querySetData : function(xmldoc, fnames) {
		fnames = fnames || this.metadata.columns;
		if (fnames && xmldoc) {
			this.queryDatas = [];
			var rows = xmldoc.getElementsByTagName("row");
			var cols;
			for ( var i = 0; i < rows.length; i++) {
				cols = rows[i].childNodes;
				var queryData = {};
				for ( var j = 0; j < fnames.length && j < cols.length; j++) {
					queryData[fnames[j]] = cols[j].text;
					if (queryData[fnames[j]] == "null") {
						queryData[fnames[j]] = "";
					}
				}
				this.queryDatas.push(queryData);
			}
		} else {
			if (!fnames) {
				alert("未获取到查询列名！");
			}
			if (!xmldoc) {
				alert("xmldoc无效！");
			}
		}
	},
	/** 查看初始化 */
	watchInit : function() {
		this.watchGetData();
		if (this.data) {
			this.setData(this.data);
		} else {
			window.close();
			alert($prompts.error.loadinfo);
		}
	},
	/** 获取查看初始化值 */
	watchGetData : function(params) {
		var url = this.getRequest("watch");
		params = params || {
			id : this.id.value
		};
		var _this = this;
		var name = this.metadata.name;
		new Ajax.Request(url, {
			method : "post",
			asynchronous : false,
			parameters : params,
			onComplete : function(xmlHttp) {
				var xmlDoc = xmlHttp.responseXML;
				var object = xmlDoc.getElementsByTagName(name);
				if (object && object.length == 1) {
					_this.data = BaseObject.getData(object[0]);
				}
			}
		});
	},
	/** 处理初始化 */
	dealInit : function() {
		this.modifyInit();
	},
	/** 修改初始化 */
	modifyInit : function() {
		var _this = this;
		this.btnModify = $("btnModify");
		this.btnModify.attachEvent("onclick", function() {
			_this.modify();
		});
		this.btnModify.style.display = "inline";
		;
	},
	/** 进行修改 */
	modify : function(params, name) {
		if (this.modifyCheck()) {
			var url = this.getRequest("modify");
			var ids = this.metadata.modifyids;
			params = params || this.getData(ids);
			var _this = this;
			new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp) {
					_this.showPrompt("modify", xmlHttp.responseText, name);
				}
			});
		}
	},
	/** 修改验证 */
	modifyCheck : function(ids) {
		return this.addCheck(ids) && validateInput();
	},
	/** 删除 */
	"delete" : function(id) {
		if (window.confirm($prompts["delete"])) {
			var url = this.getRequest("delete");
			var _this = this;
			new Ajax.Request(url, {
				method : "post",
				asynchronous : false,
				parameters : {
					id : id
				},
				onComplete : function(xmlHttp) {
					_this.showPrompt("delete", xmlHttp.responseText);
				}
			});
		}
	},
	/** 初始化元数据 */
	initMetadata : function() {
		var data = this.metadata;
		if (!data.name) {
			alert("初始化异常！请设置对象名称");
			return false;
		}
		if (data.request && !data.request.node) {
			data.request.node = data.name;
		}
		data.fileName = data.fileName || data.name;
		data.objName = data.objName || data.name;
		data.pagingCount = data.pagingCount || 10;
		return true;
	},
	/** 初始化界面元素 */
	initElement : function(isFullId) {
		this.metadata.columns = [];
		this.metadata.comments = [];
		var name = "";
		if (isFullId) {
			name = this.metadata.name + "_";
		}
		var items = this.metadata.items;
		for ( var attr in items) {
			this[attr] = $(name + attr);
			this.metadata.columns.push(attr);
			this.metadata.comments.push(items[attr]);
		}
	},
	/** 业务对象初始化 */
	init : function() {
		if (!this.initMetadata()) {
			return;
		}
		
		this.initElement();
		// 如果是弹出窗口显示关闭按钮
		if (window.opener || window.dialogArguments != undefined) {
			this.btnClose = $("btnClose");
			this.btnClose.style.display = "inline";
		}
		
		switch (baseInfo.pageType) {
		case $operates.add.page:
			this.addInit();
			break;
		case $operates.query.page:
			this.queryInit();
			this.query();
			break;
		case $operates.watch.page:
			this.watchInit();
			break;
		case $operates.deal.page:
			this.dealInit();
			break;
		}
	},
	/** 获取请求 */
	getRequest : function(operate) {
		var url;
		var request = this.metadata.request;
		if (request) {
			url = request.module + ".";
			url += request.node + ".";
			url += $operates[operate].request + ".d";
		} else {
			url = $modules.getRequest(operate);
		}
		return url;
	},
	/** 显示提示 */
	showPrompt : function(operate, result, name) {
		$prompts.show(operate, this.metadata.objName, result, name);
	}
};

/**
 * 将查询数据格式由xml转换为json
 */
BaseObject.getData = function(node, data) {
	data = data || {};
	var attrs = node.attributes;
	for ( var i = 0; i < attrs.length; i++) {
		data[attrs[i].nodeName] = attrs[i].nodeValue;
	}
	var childNodes = node.childNodes;
	var nodeName, attr;
	for ( var i = 0; i < childNodes.length; i++) {
		nodeName = childNodes[i].nodeName;
		attr = data[nodeName];
		if (attr) {
			if (attr instanceof Array) {
				attr.push(BaseObject.getData(childNodes[i]));
			} else {
				data[nodeName] = [ attr ];
				data[nodeName].push(BaseObject.getData(childNodes[i]));
			}
		} else {
			data[nodeName] = BaseObject.getData(childNodes[i]);
		}
	}
	return data;
};

BaseObject.getDatas = function(nodes) {
	var datas = [];
	for ( var i = 0; i < nodes.length; i++) {
		datas.push(BaseObject.getData(nodes[i]));
	}
	return datas;
};

/**
 * 获取查询标题
 */
BaseObject.getQueryTitle = function() {
	var title = "<span class='currentLocationBold'>查询结果</span>";
	// title = BaseObject.getOperate() + title;
	title = "<div class='title' >" + title + "</div>";
	return title;
};
/**
 * 获取查询表格行
 */
BaseObject.getQueryTr = function(td, isOdd) {
	var style = isOdd ? "odd" : "even";
	var move = " onmouseover='this.style.backgroundColor=\"#CCCCFF\"'";
	move += " onmouseout='this.style.backgroundColor=\"white\"'";
	return "<tr class='common " + style + "' " + move + ">" + td + "</tr>";
};
/**
 * 获取查询表头单元格
 */
BaseObject.getQueryThead = function(text) {
	return "<td class='head'>" + text + "</td>";
};
/**
 * 获取查询普通单元格
 */
BaseObject.getQueryTd = function(text) {
	return "<td class='common'>" + text + "</td>";
	;
};
/**
 * 获取空值时的表格行
 */
BaseObject.getQueryNull = function(length) {
	var queryNull = "<td class='empty' colspan='" + (length || 1)
			+ "' >此条件下没有数据</td>";
	queryNull = "<tr class='empty'>" + queryNull + "</tr>";
	return queryNull;
};

var queryResult = {
	getWatch : function(isDisable) {
		return {
			name : $operates.watch.desc,
			show : function(queryData) {
				var click = "";
				if(isDisable || queryData.jgid == baseInfo.jgid){
					isDisable = true;
					var param = "id=" + queryData.id;
					param += "&pageType=" + $operates.watch.page;
					var titles = baseInfo.titles.slice(0);
					titles.splice(titles.length-1,1,$operates.watch.desc);
					titles = "&title="+titles.join("&title=");
					param += titles;
					click = 'openCenter("' + param + '")';
				}
				var img = {
					src : $operates.getImage("watch",isDisable),
					click : click,
					alt : $operates.watch.desc
				};
				return queryResult.toImg(img);
			}
		};
	},
	getDeal : function(isDisable) {
		return {
			name : $operates.deal.desc,
			show : function(queryData) {
				var click = "";
				if(isDisable || queryData.jgid == baseInfo.jgid){
					isDisable = true;
					var param = "id=" + queryData.id;
					param += "&pageType=" + $operates.deal.page;
					var titles = baseInfo.titles.slice(0);
					titles.splice(titles.length-1,1,$operates.watch.desc);
					titles = "&title="+titles.join("&title=");
					param += titles;
					click = 'openCenter("' + param + '")';
				}
				var img = {
					src : $operates.getImage("deal",isDisable),
					click : click,
					alt : $operates.deal.desc
				};
				return queryResult.toImg(img);
			}
		};
	},
	toImg : function(attr) {
		var img = [];
		img.push("src='" + attr.src + "'");
		img.push("onclick='" + attr.click + "'");
		img.push("alt='" + attr.alt + "'");
		img.push("style='cursor:hand;'");
		return "<img " + img.join(" ") + "/>";
	}
};

/**
 * <pre>
 * 简单请求 
 * 不支持数据中包含[,;]字符，
 * 如需支持
 * 1.在java类和js类中编写对应的编码和解码程序 
 * 2.采用XML格式数据传输
 * </pre>
 */
function SimpleRequest(sql) {
	this.sql = sql;
}
SimpleRequest.url = "base.simpleRequest.getSomeInfo.d";
SimpleRequest.prototype = {
	load : function(isParse) {
		var objects = "";
		var _this = this;
		var request = {
			method : "post",
			asynchronous : false,
			parameters : {
				sql : this.sql
			},
			onComplete : function(xmlHttp) {
				objects = xmlHttp.responseText;
				if(isParse){
					objects = _this.parseResult(objects);
				}
			}
		};
		new Ajax.Request(SimpleRequest.url, request);
		return objects;
	},
	parseResult : function(result) {
		var objects = "";
		if (result != "null") {
			if (result.indexOf(";") != -1 && result.indexOf(",") != -1) {
				objects = result.split(";");
				for ( var i = 0; i < objects.length; i++) {
					objects[i] = objects[i].split(",");
				}
			} else if (result.indexOf(";") == -1 && result.indexOf(",") == -1) {
				objects = result;
			} else {
				var char = ",";
				if (result.indexOf(";") != -1) {
					char = ";";
				}
				objects = result.split(char);
			}
		}
		return objects;
	}
};