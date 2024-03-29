﻿
/*
Copyright Scand LLC http://www.scbr.com
This version of Software is free for using in non-commercial applications. For commercial use please contact info@scbr.com to obtain license
*/
function dhtmlXProtobarObject() {
	return this;
}
dhtmlXProtobarObject.prototype.setOnShowHandler = function (func) {
	if (typeof (func) == "function") {
		this.onShow = func;
	} else {
		this.onShow = eval(func);
	}
};
dhtmlXProtobarObject.prototype._getItemIndex = function (id) {
	for (var i = 0; i < this.itemsCount; i++) {
		if (this.items[i].id == id) {
			return i;
		}
	}
	return -1;
};
dhtmlXProtobarObject.prototype.setGfxPath = function (path) {
	this.sysGfxPath = path;
};
dhtmlXProtobarObject.prototype.setOnHideHandler = function (func) {
	if (typeof (func) == "function") {
		this.onHide = func;
	} else {
		this.onHide = eval(func);
	}
};
dhtmlXProtobarObject.prototype.setItemAction = function (id, action) {
	var z = this._getItemIndex(id);
	if (z >= 0) {
		this.items[z].setSecondAction(action);
	}
};
dhtmlXProtobarObject.prototype.getItem = function (itemId) {
	var z = this._getItemIndex(itemId);
	if (z >= 0) {
		return this.items[z];
	}
};
dhtmlXProtobarObject.prototype.hideButtons = function (idList) {
	if (!idList) {
		for (var i = 0; i < this.itemsCount; i++) {
			var z = this.items[i].getTopNode();
			z.style.display = "none";
			if (this.extraMode) {
				z.parentNode.style.display = "none";
			}
			this.items[i].hide = 1;
		}
		return 0;
	}
	var temp = idList.split(",");
	for (var i = 0; i < temp.length; i++) {
		this.hideItem(temp[i]);
	}
};
dhtmlXProtobarObject.prototype.showButtons = function (idList) {
	if (!idList) {
		for (var i = 0; i < this.itemsCount; i++) {
			var w = this.items[i].getTopNode();
			w.style.display = "";
			if (this.extraMode) {
				w.parentNode.style.display = "";
			}
			this.items[i].hide = 0;
		}
		return 0;
	}
	var temp = idList.split(",");
	for (var i = 0; i < temp.length; i++) {
		this.showItem(temp[i]);
	}
};
dhtmlXProtobarObject.prototype.disableItem = function (itemId) {
	var z = this.getItem(itemId);
	if (z) {
		if (z.disable) {
			z.disable();
		}
	}
};
dhtmlXProtobarObject.prototype.enableItem = function (itemId) {
	var z = this.getItem(itemId);
	if (z) {
		if (z.enable) {
			z.enable();
		}
	}
};
dhtmlXProtobarObject.prototype.hideItem = function (itemId) {
	var z = this.getItem(itemId);
	if (z) {
		var w = z.getTopNode();
		w.style.display = "none";
		if (this.extraMode) {
			w.parentNode.style.display = "none";
		}
		z.hide = 1;
		if (z.parentPanel) {
			this._scrollClear(z.parentPanel);
			this._scrollCheck(z.parentPanel);
		}
	}
};
dhtmlXProtobarObject.prototype.showItem = function (id) {
	var z = this.getItem(id);
	if (z) {
		var w = z.getTopNode();
		w.style.display = "";
		if (this.extraMode) {
			w.parentNode.style.display = "";
		}
		z.hide = 0;
		if (z.parentPanel) {
			this._scrollClear(z.parentPanel);
			this._scrollCheck(z.parentPanel);
		}
	}
};
dhtmlXProtobarObject.prototype.setOnClickHandler = function (func) {
	if (typeof (func) == "function") {
		this.defaultAction = func;
	} else {
		this.defaultAction = eval(func);
	}
};
dhtmlXProtobarObject.prototype.setTitleText = function (newText) {
	this.tname = newText;
	this.nameCell.innerHTML = newText;
	this.preNameCell.innerHTML = newText;
};
dhtmlXProtobarObject.prototype.setBarSize = function (width, height) {
	if (width) {
		this.topNod.width = width;
	}
	if (height) {
		this.topNod.height = height;
	}
};
dhtmlXProtobarObject.prototype.resetBar = function (idList) {
	for (var i = 0; i < this.itemsCount; i++) {
		this.hideItem(this.items[i].id);
	}
	var temp = idList.split(",");
	for (var i = 0; i < temp.length; i++) {
		this.showItem(temp[i]);
	}
};
dhtmlXProtobarObject.prototype.loadXMLFor = function (file, itemId) {
	var z = this._getItemIndex(itemId);
	if (z >= 0) {
		this._awaitXML = this.gitems[z];
	}
	this.xmlLoader.loadXML(file);
};
dhtmlXProtobarObject.prototype.loadXML = function (file, afterCall) {
	this.waitCall = afterCall || 0;
	this.xmlLoader.loadXML(file);
};
dhtmlXProtobarObject.prototype.loadXMLString = function (xmlString, afterCall) {
	this.waitCall = afterCall || 0;
	this.xmlLoader.loadXMLString(xmlString);
};
dhtmlXProtobarObject.prototype.showBar = function () {
	this.topNod.style.display = "";
	if ((this.topNod.ieFix) && (this.topNod.style.position == "absolute")) {
		this.topNod.ieFix.style.display = "";
		this.topNod.ieFix.style.position = "absolute";
		this.topNod.ieFix.style.top = this.topNod.style.top;
		this.topNod.ieFix.style.left = this.topNod.style.left;
		this.topNod.ieFix.style.width = this.topNod.offsetWidth + "px";
		this.topNod.ieFix.style.height = this.topNod.offsetHeight + "px";
	}
	if (this.onShow) {
		this.onShow();
	}
};
dhtmlXProtobarObject.prototype.hideBar = function () {
	this.topNod.style.display = "none";
	if (this.topNod.ieFix) {
		this.topNod.ieFix.style.display = "none";
	}
	if (this.onHide) {
		this.onHide();
	}
};
dhtmlXProtobarObject.prototype.setBarAlign = function (align) {
	if ((align == "left") || (align == "top")) {
		this.preNameCell.innerHTML = "";
		this.preNameCell.style.display = "none";
		this.nameCell.style.display = "";
		this.nameCell.width = "100%";
		this.nameCell.innerHTML = this.tname;
	}
	if ((align == "center") || (align == "middle")) {
		this.preNameCell.style.display = "";
		this.preNameCell.width = "50%";
		this.nameCell.style.display = "";
		this.nameCell.width = "50%";
		this.nameCell.innerHTML = this.tname;
		this.preNameCell.innerHTML = this.tname;
	}
	if ((align == "right") || (align == "bottom")) {
		this.nameCell.innerHTML = "";
		this.nameCell.style.display = "none";
		this.preNameCell.style.display = "";
		this.preNameCell.width = "100%";
		this.preNameCell.innerHTML = this.tname;
	}
};
dhtmlXProtobarObject.prototype.dummyFunc = function () {
	return true;
};
dhtmlXProtobarObject.prototype.badDummy = function () {
	return false;
};
function dhtmlXButtonPrototypeObject() {
	return this;
}
dhtmlXButtonPrototypeObject.prototype.setAction = function (func) {
	if (typeof (func) == "function") {
		this.action = func;
	} else {
		this.action = eval(func);
	}
};
dhtmlXButtonPrototypeObject.prototype.setSecondAction = function (func) {
	if (typeof (func) == "function") {
		this.persAction = func;
	} else {
		this.persAction = eval(func);
	}
};
dhtmlXButtonPrototypeObject.prototype.enable = function () {
	if (this.disableImage) {
		this.imageTag.src = this.src;
	} else {
		if (!this.className) {
			this.topNod.className = this.objectNode.className;
		} else {
			this.topNod.className = this.className;
		}
	}
	if (this.textTag) {
		this.textTag.className = this.textClassName;
	}
	this.topNod.onclick = this._onclickX;
	this.topNod.onmouseover = this._onmouseoverX;
	this.topNod.onmouseout = this._onmouseoutX;
	this.topNod.onmousedown = this._onmousedownX;
	this.topNod.onmouseup = this._onmouseupX;
};
dhtmlXButtonPrototypeObject.prototype.disable = function () {
	if (this.disableImage) {
		this.imageTag.src = this.disableImage;
	} else {
		this.topNod.className = "iconGray";
	}
	if (this.textTag) {
		this.textTag.className = "buttonTextDisabled";
	}
	this.topNod.onclick = this.dummy;
	this.topNod.onmouseover = this.dummy;
	this.topNod.onmouseout = this.dummy;
	this.topNod.onmousedown = this.dummy;
	this.topNod.onmouseup = this.dummy;
};
dhtmlXButtonPrototypeObject.prototype._onclickX = function (e, that) {
	if (!that) {
		that = this.objectNode;
	}
	if (that.topNod.dstatus) {
		return;
	}
	if ((!that.persAction) || (that.persAction())) {
		if (that.action) {
			that.action(that.id);
		}
	}
};
dhtmlXButtonPrototypeObject.prototype.setHTML = function (htmlText) {
	this.topNod.innerHTML = htmlText;
};
dhtmlXButtonPrototypeObject.prototype.setAltText = function (imageText) {
	this.imageTag.alt = imageText;
};
dhtmlXButtonPrototypeObject.prototype.setImage = function (imageSrc, disabledImageSrc) {
	this.src = imageSrc;
	if (disabledImageSrc) {
		this.disableImage = disabledImageSrc;
	}
	if (this.topNod.onclick == this.dummy) {
		if (disabledImageSrc) {
			this.imageTag.src = disabledImageSrc;
		}
	} else {
		this.imageTag.src = imageSrc;
	}
};
dhtmlXButtonPrototypeObject.prototype.dummy = function () {
};
dhtmlXButtonPrototypeObject.prototype.getTopNode = function () {
	return this.topNod;
};
dhtmlXButtonPrototypeObject.prototype._onmouseoverY = function () {
	if (this._mvImage) {
		this.imageTag.src = this._mvImage;
	} else {
		this.topNod.className = this.className + "Over";
	}
};
dhtmlXButtonPrototypeObject.prototype._onmouseoutY = function () {
	if (this._mnImage) {
		this.imageTag.src = this._mnImage;
	} else {
		this.topNod.className = this.className;
	}
};
dhtmlXButtonPrototypeObject.prototype._onmousedownX = function () {
	this.className = this.objectNode.className + "Down";
	return true;
};
dhtmlXButtonPrototypeObject.prototype._onmouseupX = function () {
	this.className = this.objectNode.className;
	return true;
};
dhtmlXButtonPrototypeObject.prototype._onmouseoutX = function (e) {
	if (!e) {
		e = event;
	}
	if (this.timeoutop) {
		clearTimeout(this.timeoutop);
	}
	this.timeoutop = setTimeout(this.objectNode._delayedTimerCall(this.objectNode, "_onmouseoutY"), 100);
};
dhtmlXButtonPrototypeObject.prototype._onmouseoverX = function (e) {
	if (!e) {
		e = event;
	}
	if (this.timeoutop) {
		clearTimeout(this.timeoutop);
	}
	this.timeoutop = setTimeout(this.objectNode._delayedTimerCall(this.objectNode, "_onmouseoverY"), 50);
};
dhtmlXButtonPrototypeObject.prototype._delayedTimerCall = function (object, functionName, time) {
	this.callFunc = function () {
		eval("object." + functionName + "();");
	};
	return this.callFunc;
};
dhtmlXButtonPrototypeObject.prototype._arg2obj = function (n, list) {
	var nAtr = new Object();
	for (var i = 0; i < n.length; i++) {
		nAtr[list[i]] = n[i];
	}
	return nAtr;
};

