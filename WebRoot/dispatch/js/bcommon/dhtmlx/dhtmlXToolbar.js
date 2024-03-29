﻿
/*
Copyright Scand LLC http://www.scbr.com
This version of Software is free for using in non-commercial applications. For commercial use please contact info@scbr.com to obtain license
*/
function dhtmlXToolbarObject(htmlObject, width, height, name, vMode) {
	if (_isIE) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		}
		catch (e) {
		}
	}
	this.width = width;
	this.height = height;
	if (typeof (htmlObject) != "object") {
		this.parentObject = document.getElementById(htmlObject);
	} else {
		this.parentObject = htmlObject;
	}
	this.xmlDoc = 0;
	this.topNod = 0;
	this.dividerCell = 0;
	this.firstCell = 0;
	this.nameCell = 0;
	this.crossCell = 0;
	this.items = new Array();
	this.itemsCount = 0;
	this.defaultAction = 0;
	this.extraMode = convertStringToBoolean(vMode);
	this.onShow = 0;
	this.onHide = 0;
	this.oldMouseMove = 0;
	this.tname = name;
	this.tableCSS = "toolbarTable";
	this.titleCSS = "toolbarName";
	if (!this.extraMode) {
		this._create_self();
	} else {
		this._create_self_vertical();
	}
	if (this._extendedInit) {
		this._extendedInit();
	}
	this.xmlLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this);
	return this;
}
dhtmlXToolbarObject.prototype = new dhtmlXProtobarObject;
dhtmlXToolbarObject.prototype.addItem = function (item, pos) {
	if ((!pos) || (pos > this.itemsCount)) {
		pos = this.itemsCount;
	}
	if (this.extraMode) {
		var tr = document.createElement("tr");
		tr.style.verticalAlign = "top";
		tr.appendChild(item.getTopNode());
		this.firstCell.parentNode.parentNode.insertBefore(tr, (pos == this.itemsCount) ? this.firstCell.parentNode : this.items[pos].getTopNode().parentNode);
		item.getTopNode().style.width = "100%";
	} else {
		this.firstCell.parentNode.insertBefore(item.getTopNode(), (pos == this.itemsCount) ? this.firstCell : this.items[pos].getTopNode());
		item.getTopNode().style.height = "100%";
	}
	item.parentNod = this;
	if (this.defaultAction) {
		item.setAction(this.defaultAction);
	}
	for (var i = pos; i < this.itemsCount + 1; i++) {
		var a = this.items[i];
		this.items[i] = item;
		item = a;
	}
	this.itemsCount++;
};
dhtmlXToolbarObject.prototype._getItemIndexByPosition = function (position) {
	var j = 0;
	for (var i = 0; i < this.itemsCount; i++) {
		if (this.items[i].hide != 1) {
			j++;
		}
		if (j == position) {
			return i;
		}
	}
	return -1;
};
dhtmlXToolbarObject.prototype.getItemByPosition = function (position) {
	var z = this._getItemIndexByPosition(position);
	if (z >= 0) {
		return this.items[z];
	}
};
dhtmlXToolbarObject.prototype.removeItemById = function (itemId) {
	var z = this._getItemIndex(itemId);
	if (z >= 0) {
		if (this.items[z].removeItem) {
			this.items[z].removeItem();
		}
		this.firstCell.parentNode.removeChild(this.items[z].getTopNode());
		this.items[z] = 0;
		this.itemsCount--;
		for (var i = z; i < this.itemsCount; i++) {
			this.items[i] = this.items[i + 1];
		}
	}
};
dhtmlXToolbarObject.prototype.removeItemByPosition = function (position) {
	var z = this._getItemIndexByPosition(position);
	if (z) {
		if (this.items[z].removeItem) {
			this.items[z].removeItem();
		}
		this.firstCell.parentNode.removeChild(this.items[z].getTopNode());
		this.items[z] = 0;
		this.itemsCount--;
		for (var i = z; i < this.itemsCount; i++) {
			this.items[i] = this.items[i + 1];
		}
	}
};
dhtmlXToolbarObject.prototype.hideItemByPosition = function (position) {
	var z = this.getItemByPosition(position);
	if (z) {
		z.getTopNode().style.display = "none";
		z.hide = 1;
	}
};
dhtmlXToolbarObject.prototype._attributes = function (n) {
	var nAtr = new Object();
	var atr = n.attributes;
	for (var a = 0; a < atr.length; a++) {
		nAtr[atr[a].name] = atr[a].value;
	}
	if (n.childNodes) {
		for (var a = 0; a < n.childNodes.length; a++) {
			if (n.childNodes[a].nodeType == 1) {
				var tag = n.childNodes[a];
				if (!nAtr[tag.tagName]) {
					nAtr[tag.tagName] = new Array();
				}
				var tog = nAtr[tag.tagName];
				tog[tog.length] = this._attributes(tag);
			}
			if (n.childNodes[a].nodeType == 3) {
				nAtr.content = n.childNodes[a].data;
			}
		}
	}
	return nAtr;
};
dhtmlXToolbarObject.prototype._parseXMLTree = function (that, node) {
	if (!node) {
		node = that.xmlLoader.getXMLTopNode("toolbar");
	}
	var nAtr = that._attributes(node);
	if (!nAtr.globalTextCss) {
		nAtr.globalTextCss = that.globalTextCss;
	}
	if (!nAtr.globalCss) {
		nAtr.globalCss = that.globalCss;
	}
	if (nAtr.toolbarAlign) {
		that.setBarAlign(nAtr.toolbarAlign);
	}
	if (nAtr.absolutePosition == "yes") {
		that.topNod.style.position = "absolute";
		that.topNod.style.top = nAtr.left || 0;
		that.topNod.style.left = nAtr.top || 0;
	}
	if ((nAtr.absolutePosition != "auto") && (nAtr.absolutePosition != "yes")) {
		that.dividerCell.style.display = "none";
	}
	if (nAtr.name) {
		that.setTitleText(nAtr.name);
	}
	if (nAtr.width) {
		that.setBarSize(nAtr.width, nAtr.height);
	}
	for (var i = 0; i < node.childNodes.length; i++) {
		var localItem = node.childNodes[i];
		if (localItem.nodeType == 1) {
			var lAtr = that._attributes(localItem);
			if ((!lAtr.className) && (nAtr.globalCss)) {
				lAtr.className = nAtr.globalCss;
			}
			if ((!lAtr.textClassName) && (nAtr.globalTextCss)) {
				lAtr.textClassName = nAtr.globalTextCss;
			}
			if (localItem.tagName == "divider") {
				if (that.extraMode) {
					lAtr.button = "DividerY";
				} else {
					lAtr.button = "DividerX";
				}
			} else {
				lAtr.button = localItem.tagName;
			}
			if ((lAtr.src) && (that.sysGfxPath)) {
				lAtr.src = that.sysGfxPath + lAtr.src;
			}
			var TempNode = dhxButtonFactory(lAtr);
			if (TempNode) {
				that.addItem(TempNode);
			}
			if (localItem.getAttribute("disabled")) {
				TempNode.disable();
			}
		}
	}
	if (that.dhx_xml_end) {
		that.dhx_xml_end(that);
	}
};
dhtmlXToolbarObject.prototype.setOnLoadingEnd = function (func) {
	if (typeof (func) == "function") {
		this.dhx_xml_end = func;
	} else {
		this.dhx_xml_end = eval(func);
	}
};
dhtmlXToolbarObject.prototype.setToolbarCSS = function (table, title, button, text) {
	this.tableCSS = table;
	this.titleCSS = title;
	this.globalCss = button;
	this.globalTextCss = title;
	this.topNod.className = this.tableCSS;
	this.preNameCell.className = this.titleCSS;
	this.nameCell.className = this.titleCSS;
};
dhtmlXToolbarObject.prototype._create_self = function () {
	if (!this.width) {
		this.width = 1;
	}
	if (!this.height) {
		this.height = 1;
	}
	var div = document.createElement("div");
	div.innerHTML = "<table cellpadding=\"0\" cellspacing=\"1\" class=\"" + this.tableCSS + "\" style=\"display:none\" width=\"" + this.width + "\" height=\"" + this.height + "\"><tbody>" + "<tr>" + "<td width=\"" + (_isFF ? 5 : 3) + "px\"><div class=\"toolbarHandle\" " + (_isIE ? "style='width:3px'" : "") + ">&nbsp;</div></td>" + "<td class=\"" + this.titleCSS + "\" style=\"display:none\">" + this.name + "</td>" + "<td></td>" + "<td align=\"right\" width=\"100%\" class=\"" + this.titleCSS + "\" style=\"display:none\">" + this.name + "</td>" + "<td></td>" + "</tr></tbody></table>";
	var table = div.childNodes[0];
	table.setAttribute("UNSELECTABLE", "on");
	table.onselectstart = this.badDummy;
	this.topNod = table;
	this.dividerCell = table.childNodes[0].childNodes[0].childNodes[0];
	this.dividerCell.toolbar = this;
	this.preNameCell = this.dividerCell.nextSibling;
	this.firstCell = this.preNameCell.nextSibling;
	this.nameCell = this.firstCell.nextSibling;
	this.crossCell = this.nameCell.nextSibling;
	this.parentObject.appendChild(table);
};
dhtmlXToolbarObject.prototype._create_self_vertical = function () {
	if (!this.width) {
		this.width = 1;
	}
	if (!this.height) {
		this.height = 1;
	}
	var div = document.createElement("div");
	div.innerHTML = "<table cellpadding=\"0\" cellspacing=\"1\" class=\"" + this.tableCSS + "\" style=\"display:none\" width=\"" + this.width + "\" height=\"" + this.height + "\"><tbody>" + "<tr><td heigth=\"" + (_isFF ? 5 : 3) + "px\"><div class=\"vtoolbarHandle\" style=\"width:100%;" + (_isIE ? "height:3px" : "") + " overflow:hidden\"></div></td></tr>" + "<tr><td height=\"100%\" class=\"" + this.titleCSS + "\" style=\"display:none\">" + this.name + "</td></tr>" + "<tr><td></td></tr>" + "<tr><td align=\"right\" height=\"100%\" class=\"" + this.titleCSS + "\" style=\"display:none\">" + this.name + "</td></tr>" + "<tr><td></td></tr>" + "</tbody></table>";
	var table = div.childNodes[0];
	table.onselectstart = this.badDummy;
	table.setAttribute("UNSELECTABLE", "on");
	this.topNod = table;
	this.dividerCell = table.childNodes[0].childNodes[0].childNodes[0];
	this.dividerCell.toolbar = this;
	this.preNameCell = table.childNodes[0].childNodes[1].childNodes[0];
	this.firstCell = table.childNodes[0].childNodes[2].childNodes[0];
	this.nameCell = table.childNodes[0].childNodes[3].childNodes[0];
	this.crossCell = table.childNodes[0].childNodes[4].childNodes[0];
	this.parentObject.appendChild(table);
};
function dhxButtonFactory(tag, zone) {
	var name = "dhtmlX" + tag.button + "Object";
	if (arguments.length == 2) {
		tag._td = "DIV";
	}
	if (window[name]) {
		var z = new window[name](tag);
	}
	if (arguments.length == 2) {
		if (zone) {
			zone.appendChild(z.topNod);
		} else {
			document.write("<div id='button_factory_temp'></div>");
			var x = document.getElementById("button_factory_temp");
			x.appendChild(z.topNod);
			x.id = "";
		}
	}
	return z;
}
function dhtmlXImageButtonObject(src, width, height, action, id, tooltip, className, disableImage) {
	if (typeof (src) != "object") {
		src = this._arg2obj(arguments, ["src", "width", "height", "action", "id", "tooltip", "className", "disableImage"]);
	}
	if (src.action) {
		this.setSecondAction(src.action);
	}
	this.id = src.id || 0;
	this.className = src.className || "defaultButton";
	this.src = src.src;
	this.disableImage = src.disableImage;
	this.tooltip = src.tooltip || "";
	td = document.createElement(src._td || "TD");
	this.topNod = td;
	td.style.height = src.height;
	td.style.width = src.width;
	td.align = "center";
	td.vAlign = "middle";
	td.innerHTML = "<img src='" + this.src + "' border='0' title='" + this.tooltip + "' style='padding-left:2px;padding-right:2px;'>";
	td.className = this.className;
	td.objectNode = this;
	if (src.mouseover) {
		this._mvImage = src.mouseover;
		this._mnImage = src.src;
	}
	this.imageTag = td.childNodes[0];
	this.enable();
}
dhtmlXImageButtonObject.prototype = new dhtmlXButtonPrototypeObject;
function dhtmlXDividerYObject(id) {
	if (typeof (id) == "object") {
		id = id.id;
	}
	if (id) {
		this.id = id;
	} else {
		this.id = 0;
	}
	td = document.createElement(id._td || "td");
	this.topNod = td;
	td.align = "center";
	td.style.paddingRight = "2";
	td.style.paddingLeft = "2";
	td.innerHTML = "<div class='toolbarDividerY'>&nbsp;</div>";
	if (!document.all) {
		td.childNodes[0].style.height = "0px";
	}
	return this;
}
dhtmlXDividerYObject.prototype = new dhtmlXButtonPrototypeObject;
dhtmlXToolbarDividerYObject = dhtmlXDividerYObject;
function dhtmlXDividerXObject(id) {
	if (typeof (id) == "object") {
		id = id.id;
	}
	if (id) {
		this.id = id;
	} else {
		this.id = 0;
	}
	td = document.createElement(id._td || "td");
	this.topNod = td;
	td.align = "center";
	td.style.paddingRight = "2";
	td.style.paddingLeft = "2";
	td.width = "4px";
	td.innerHTML = "<div class='toolbarDivider'></div >";
	if (!document.all) {
		td.childNodes[0].style.width = "0px";
		td.style.padding = "0 0 0 0";
		td.style.margin = "0 0 0 0";
	}
	return this;
}
dhtmlXDividerXObject.prototype = new dhtmlXButtonPrototypeObject;
dhtmlXToolbarDividerXObject = dhtmlXDividerXObject;
function dhtmlXImageTextButtonObject(src, content, width, height, action, id, tooltip, className, textClassName, disableImage) {
	if (typeof (src) != "object") {
		src = this._arg2obj(arguments, ["src", "content", "width", "height", "action", "id", "tooltip", "className", "textClassName", "disableImage"]);
	}
	if (src.action) {
		this.setSecondAction(src.action);
	}
	this.src = src.src;
	this.disableImage = src.disableImage;
	this.tooltip = src.tooltip || "";
	this.id = src.id || 0;
	this.className = src.className || "defaultButton";
	td = document.createElement(src._td || "td");
	this.topNod = td;
	td.style.height = src.height;
	td.style.width = src.width;
	td.align = "center";
	td.noWrap = true;
	if (!src.textmode) {
		this.textClassName = src.textClassName || "defaultButtonText";
		td.innerHTML = "<table title='" + this.tooltip + "' width='100%' height='100%' cellpadding='0' cellspacing='0'><tr><td valign='center' align='center' valign='middle' style='padding-left:4px;padding-right:4px;'><img src='" + this.src + "' border='0'></td><td width='100%' style='padding-left:5px' align='left' valign='center' class='" + this.textClassName + "'>" + src.content + "</td></tr></table>";
		this.textTag = td.childNodes[0].rows[0].cells[1];
	} else {
		this.textClassName = src.textClassName || "defaultButtonTextBottom";
		td.innerHTML = "<table title='" + this.tooltip + "' width='100%' height='100%' cellpadding='0' cellspacing='0'><tr><td align='center' valign='middle'><img src='" + this.src + "' border='0' style='padding-left:2px;padding-right:2px;'></td></tr><tr><td width='100%' align='center' class='" + this.textClassName + "'>" + src.content + "</td></tr></table>";
		this.textTag = td.childNodes[0].rows[1].cells[0];
	}
	td.className = this.className;
	td.objectNode = this;
	this.imageTag = td.childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0];
	this.enable();
	if (src.mouseover) {
		this._mvImage = src.mouseover;
		this._mnImage = src.src;
	}
	return this;
}
dhtmlXImageTextButtonObject.prototype = new dhtmlXButtonPrototypeObject;
dhtmlXImageTextButtonObject.prototype.setText = function (newText) {
	this.textTag.innerHTML = newText;
};
function dhtmlXImageTextButtonXPObject(src, content, width, height, action, id, tooltip, className, textClassName, disableImage) {
	if (typeof (src) != "object") {
		src = this._arg2obj(arguments, ["src", "content", "width", "height", "action", "id", "tooltip", "className", "textClassName", "disableImage"]);
	}
	src.textmode = "bottom";
	return (new dhtmlXImageTextButtonObject(src));
}
dhtmlXImageTextButtonXPObject.prototype = new dhtmlXButtonPrototypeObject;
function dhtmlXSelectButtonObject(id, valueList, displayList, action, width, height, className) {
	if (typeof (id) != "object") {
		id = this._arg2obj(arguments, ["id", "valueList", "displayList", "action", "width", "height", "className"]);
	}
	if (id.action) {
		this.setSecondAction(id.action);
	}
	this.id = id.id;
	td = document.createElement(id._td || "td");
	this.topNod = td;
	td.align = "center";
	td.style.width = id.width;
	var sel = document.createElement("select");
	this.selElement = sel;
	sel.onchange = this._onclickX;
	sel.objectNode = this;
	if (id.className) {
		sel.className = id.className;
	}
	if (id.width) {
		sel.style.width = "100%";
	}
	if (typeof (valueList) == "string") {
		var temp1 = valueList.split(",");
		if (displayList) {
			var temp2 = displayList.split(",");
		} else {
			var temp2 = valueList.split(",");
		}
		for (var i = 0; i < temp1.length; i++) {
			sel.options[sel.options.length] = new Option(temp2[i], temp1[i]);
		}
	} else {
		if (id.option) {
			for (var i = 0; i < id.option.length; i++) {
				sel.options[sel.options.length] = new Option(id.option[i].content, id.option[i].value);
			}
		}
	}
	td.appendChild(sel);
	td.className = "toolbarNormalButton";
	td.objectNode = this;
	return this;
}
dhtmlXSelectButtonObject.prototype = new dhtmlXButtonPrototypeObject;
dhtmlXSelectButtonObject.prototype.clearOptions = function () {
	var cnt = this.selElement.options.length;
	for (var i = 0; i < cnt; i++) {
		this.selElement.removeChild(this.selElement.options[0]);
	}
};
dhtmlXSelectButtonObject.prototype.disable = function () {
	this.selElement.disabled = true;
};
dhtmlXSelectButtonObject.prototype.enable = function () {
	this.selElement.disabled = false;
};
dhtmlXSelectButtonObject.prototype._onclickX = function () {
	if ((!this.objectNode.persAction) || (this.objectNode.persAction(this.objectNode.selElement.value))) {
		if (this.objectNode.action) {
			this.objectNode.action(this.objectNode.id, this.objectNode.selElement.value);
		}
	}
};
dhtmlXSelectButtonObject.prototype.addOption = function (value, display) {
	this.selElement.options[this.selElement.options.length] = new Option(display, value);
};
dhtmlXSelectButtonObject.prototype.removeOption = function (value) {
	var z = this.getIndexByValue(value);
	if (z >= 0) {
		this.selElement.removeChild(this.selElement.options[z]);
	}
};
dhtmlXSelectButtonObject.prototype.setOptionValue = function (oldValue, newValue) {
	var z = this.getIndexByValue(oldValue);
	if (z >= 0) {
		this.selElement.options[z].value = newValue;
	}
};
dhtmlXSelectButtonObject.prototype.setOptionText = function (value, newText) {
	var z = this.getIndexByValue(value);
	if (z >= 0) {
		this.selElement.options[z].text = newText;
	}
};
dhtmlXSelectButtonObject.prototype.setSelected = function (value) {
	var z = this.getIndexByValue(value);
	if (z >= 0) {
		this.selElement.options[z].selected = true;
	}
};
dhtmlXSelectButtonObject.prototype.getIndexByValue = function (value) {
	for (var i = 0; i < this.selElement.options.length; i++) {
		if (this.selElement.options[i].value == value) {
			return i;
		}
	}
	return -1;
};
function dhtmlXTwoStateButtonObject(id, src, content, width, height, action, tooltip, className, textClassName, disableImage, pressedState) {
	if (typeof (id) != "object") {
		id = this._arg2obj(arguments, ["id", "src", "content", "width", "height", "action", "tooltip", "className", "textClassName", "disableImage", "pressedState"]);
	}
	if (id.action) {
		this.setSecondAction(id.action);
	}
	this.state = 0;
	this.className = id.className || "defaultButton";
	this.textClassName = id.textClassName || "defaultButtonText";
	this.disableImage = id.disableImage;
	this.tooltip = id.tooltip || "";
	this.id = id.id || 0;
	if (id.content) {
		this.textP = id.content.split(",");
	} else {
		this.textP = ",".split(",");
	}
	if (id.src) {
		this.srcA = id.src.split(",");
	} else {
		this.srcA = ",".split(",");
	}
	this.src = this.srcA[0];
	td = document.createElement(id._td || "td");
	this.topNod = td;
	td.style.height = id.height;
	td.style.width = id.width;
	td.align = "center";
	td.noWrap = true;
	td.innerHTML = "<table title='" + this.tooltip + "' width='100%' height='100%' cellpadding='0' cellspacing='0'><tr><td align='center' valign='middle'><img src='" + this.srcA[0] + "' border='0' style='padding-left:2px;padding-right:2px;'></td><td width='100%' style='padding-left:5px' align='left' class='" + this.textClassName + "'>" + this.textP[0] + "</td></tr></table>";
	td.className = this.className;
	td.objectNode = this;
	this.imageTag = td.childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0];
	this.textTag = td.childNodes[0].childNodes[0].childNodes[0].childNodes[1];
	if (!id.content) {
		this.textTag.style.display = "none";
	}
	if (!id.src) {
		this.imageTag.style.display = "none";
	}
	this.enable();
	if (convertStringToBoolean(pressedState)) {
		this.state = 1;
		this.topNod.className = this.className + "down";
		if (this.textP[1]) {
			this.textTag.innerHTML = this.textP[1];
		}
		if (this.srcA[1]) {
			this.imageTag.src = this.srcA[1];
		}
	}
	return this;
}
dhtmlXTwoStateButtonObject.prototype = new dhtmlXButtonPrototypeObject;
dhtmlXTwoStateButtonObject.prototype._onclickX = function (e, that) {
	if (!that) {
		that = this.objectNode;
	}
	if (that.topNod.dstatus) {
		return;
	}
	if (that.state == 0) {
		that.state = 1;
		this.className = that.className + "down";
	} else {
		that.state = 0;
		this.className = that.className;
	}
	if (that.textP[that.state]) {
		that.textTag.innerHTML = that.textP[that.state];
	}
	if (that.srcA[that.state]) {
		that.imageTag.src = that.srcA[that.state];
	}
	if ((!that.persAction) || (that.persAction())) {
		if (that.action) {
			that.action(that.id, that.state);
		}
	}
};
dhtmlXTwoStateButtonObject.prototype._onmouseoutX = function (e) {
};
dhtmlXTwoStateButtonObject.prototype._onmouseoverX = function (e) {
};
dhtmlXTwoStateButtonObject.prototype.getState = function () {
	return this.state;
};
dhtmlXTwoStateButtonObject.prototype.setState = function (state) {
	this.state = state;
	if (state == 0) {
		this.topNod.className = this.className;
	} else {
		this.topNod.className = this.className + "down";
	}
	if (this.textP[this.state]) {
		this.textTag.innerHTML = this.textP[this.state];
	}
	if (this.srcA[this.state]) {
		this.imageTag.src = this.srcA[this.state];
	}
};
function dhtmlXSliderButtonObject(id, size, skin, vertical, step, limit, init_pos, shift, className) {
	if (typeof (id) != "object") {
		var conf = this._arg2obj(arguments, ["id", "size", "skin", "vertical", "limit", "init_pos", "shift", "className"]);
	} else {
		var conf = id;
	}
	var td = document.createElement(conf._td || "TD");
	td.style[(conf.vertical) ? "height" : "width"] = conf.size + "px";
	var z = new dhtmlxSlider(td, conf);
	this.slider = z;
	this.id = conf.id;
	this.topNod = td;
	td.className = conf.className || "defaultButton";
	td.objectNode = this;
	z.setOnChangeHandler(function () {
		td.objectNode._onclickX.apply(td);
	});
	return this;
}
dhtmlXSliderButtonObject.prototype = new dhtmlXButtonPrototypeObject;
dhtmlXSliderButtonObject.prototype._onclickX = function () {
	if ((!this.objectNode.persAction) || (this.objectNode.persAction(this.objectNode.getValue()))) {
		if (this.objectNode.action) {
			this.objectNode.action(this.objectNode.id, this.objectNode.getValue());
		}
	}
};
dhtmlXSliderButtonObject.prototype.getValue = function () {
	return this.slider.getValue();
};
dhtmlXSliderButtonObject.prototype.setValue = function (val) {
	return this.slider.setValue(val);
};
function dhtmlXLabelButtonObject(width, content, className) {
	if (typeof (width) != "object") {
		var conf = this._arg2obj(arguments, ["width", "content", "className"]);
	} else {
		var conf = width;
	}
	var td = document.createElement(conf._td || "TD");
	td.style.width = parseInt(conf.width) + "px";
	td.innerHTML = conf.content;
	this.topNod = td;
	td.className = conf.className || "defaultButton";
	return this;
}
dhtmlXLabelButtonObject.prototype = new dhtmlXButtonPrototypeObject;

