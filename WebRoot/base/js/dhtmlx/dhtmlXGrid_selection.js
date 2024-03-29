﻿
/*dhtmlxGrid v.1.4 build 70813 Standard Edition
Copyright Scand LLC http://www.scbr.com
This version of Software is free for using in GPL applications. For commercial use please contact info@scbr.com to obtain license*/
dhtmlXGridObject.prototype.enableBlockSelection = function () {
	var self = this;
	this.obj.onmousedown = function (e) {
		e = e || event;
		self._OnSelectionStart(e, this);
		return true;
	};
	this._CSVRowDelimiter = "\n";
	this.setOnResize(function () {
		self._HideSelection();
		return true;
	});
};
dhtmlXGridObject.prototype.disableBlockSelection = function () {
	this.obj.onmousedown = null;
};
dhtmlXGridObject.prototype._OnSelectionStart = function (event, obj) {
	var self = this;
	if (!self.isActive) {
		self.setActive(true);
	}
	var pos = this.getPosition(this.obj);
	var x = event.clientX - pos[0] + document.body.scrollLeft;
	var y = event.clientY - pos[1] + document.body.scrollTop;
	this._CreateSelection(x - 4, y - 4);
	var src = event.srcElement || event.target;
	if (src == this._selectionObj) {
		this._HideSelection();
		this._startSelectionCell = null;
	} else {
		while (src.tagName.toLowerCase() != "td") {
			src = src.parentNode;
		}
		this._startSelectionCell = src;
	}
	this.obj.onmousedown = null;
	this.obj[_isIE ? "onmouseleave" : "onmouseout"] = function (e) {
		if (self._blsTimer) {
			window.clearTimeout(self._blsTimer);
		}
	};
	this.obj.onmmold = this.obj.onmousemove;
	this.obj.onmousemove = function (e) {
		e = e || event;
		e.returnValue = false;
		self._OnSelectionMove(e);
	};
	this._oldDMP = document.body.onmouseup;
	document.body.onmouseup = function (e) {
		e = e || event;
		self._OnSelectionStop(e, this);
		return true;
	};
};
dhtmlXGridObject.prototype._OnSelectionMove = function (event) {
	if (this._startSelectionCell == null) {
		this._endSelectionCell = this._startSelectionCell = this.getFirstParentOfType(event.srcElement || event.target, "TD");
	} else {
		if (event.srcElement) {
			this._endSelectionCell = this.getFirstParentOfType(event.srcElement || event.target, "TD");
		}
	}
	var self = this;
	this._ShowSelection();
	var pos = this.getPosition(this.obj);
	var X = event.clientX - pos[0] + document.body.scrollLeft;
	var Y = event.clientY - pos[1] + document.body.scrollTop;
	var prevX = this._selectionObj.startX;
	var prevY = this._selectionObj.startY;
	var diffX = X - prevX;
	var diffY = Y - prevY;
	if (diffX < 0) {
		this._selectionObj.style.left = this._selectionObj.startX + diffX + 1;
		diffX = 0 - diffX;
	} else {
		this._selectionObj.style.left = this._selectionObj.startX - 3;
	}
	if (diffY < 0) {
		this._selectionObj.style.top = this._selectionObj.startY + diffY + 1;
		diffY = 0 - diffY;
	} else {
		this._selectionObj.style.top = this._selectionObj.startY - 3;
	}
	this._selectionObj.style.width = (diffX > 4 ? diffX - 4 : 0) + "px";
	this._selectionObj.style.height = (diffY > 4 ? diffY - 4 : 0) + "px";
	var BottomRightX = this.objBox.scrollLeft + this.objBox.clientWidth;
	var BottomRightY = this.objBox.scrollTop + this.objBox.clientHeight;
	var TopLeftX = this.objBox.scrollLeft;
	var TopLeftY = this.objBox.scrollTop;
	var nextCall = false;
	if (this._blsTimer) {
		window.clearTimeout(this._blsTimer);
	}
	if (X + 20 >= BottomRightX) {
		this.objBox.scrollLeft = this.objBox.scrollLeft + 20;
		nextCall = true;
	} else {
		if (X - 20 < TopLeftX) {
			this.objBox.scrollLeft = this.objBox.scrollLeft - 20;
			nextCall = true;
		}
	}
	if (Y + 20 >= BottomRightY) {
		this.objBox.scrollTop = this.objBox.scrollTop + 20;
		nextCall = true;
	} else {
		if (Y - 20 < TopLeftY) {
			this.objBox.scrollTop = this.objBox.scrollTop - 20;
			nextCall = true;
		}
	}
	if (nextCall) {
		var a = event.clientX;
		var b = event.clientY;
		this._blsTimer = window.setTimeout(function () {
			self._OnSelectionMove({clientX:a, clientY:b});
		}, 100);
	}
};
dhtmlXGridObject.prototype._OnSelectionStop = function (event) {
	var self = this;
	if (this._blsTimer) {
		window.clearTimeout(this._blsTimer);
	}
	this.obj.onmousedown = function (e) {
		e = e || event;
		self._OnSelectionStart(e, this);
		return true;
	};
	this.obj.onmousemove = this.obj.onmmold || null;
	document.body.onmouseup = this._oldDMP || null;
	if (parseInt(this._selectionObj.style.width) < 2 && parseInt(this._selectionObj.style.height) < 2) {
		this._HideSelection();
	} else {
		var src = this.getFirstParentOfType(event.srcElement || event.target, "TD");
		if ((!src) || (!src.parentNode.idd)) {
			src = this._endSelectionCell;
		}
		while (src.tagName.toLowerCase() != "td") {
			src = src.parentNode;
		}
		this._stopSelectionCell = src;
		this._selectionArea = this._RedrawSelectionPos(this._startSelectionCell, this._stopSelectionCell);
		this.callEvent("onBlockSelected", []);
	}
};
dhtmlXGridObject.prototype._RedrawSelectionPos = function (LeftTop, RightBottom) {
	var pos = {};
	pos.LeftTopCol = LeftTop._cellIndex;
	pos.LeftTopRow = this.getRowIndex(LeftTop.parentNode.idd);
	pos.RightBottomCol = RightBottom._cellIndex;
	pos.RightBottomRow = this.getRowIndex(RightBottom.parentNode.idd);
	var LeftTop_width = LeftTop.offsetWidth;
	var LeftTop_height = LeftTop.offsetHeight;
	LeftTop = this.getPosition(LeftTop, this.obj);
	var RightBottom_width = RightBottom.offsetWidth;
	var RightBottom_height = RightBottom.offsetHeight;
	RightBottom = this.getPosition(RightBottom, this.obj);
	if (LeftTop[0] < RightBottom[0]) {
		var Left = LeftTop[0];
		var Right = RightBottom[0] + RightBottom_width;
	} else {
		var foo = pos.RightBottomCol;
		pos.RightBottomCol = pos.LeftTopCol;
		pos.LeftTopCol = foo;
		var Left = RightBottom[0];
		var Right = LeftTop[0] + LeftTop_width;
	}
	if (LeftTop[1] < RightBottom[1]) {
		var Top = LeftTop[1];
		var Bottom = RightBottom[1] + RightBottom_height;
	} else {
		var foo = pos.RightBottomRow;
		pos.RightBottomRow = pos.LeftTopRow;
		pos.LeftTopRow = foo;
		var Top = RightBottom[1];
		var Bottom = LeftTop[1] + LeftTop_height;
	}
	var Width = Right - Left;
	var Height = Bottom - Top;
	this._selectionObj.style.left = Left + "px";
	this._selectionObj.style.top = Top + "px";
	this._selectionObj.style.width = Width + "px";
	this._selectionObj.style.height = Height + "px";
	return pos;
};
dhtmlXGridObject.prototype._CreateSelection = function (x, y) {
	if (this._selectionObj == null) {
		var div = document.createElement("div");
		div.style.position = "absolute";
		div.style.display = "none";
		div.className = "dhtmlxGrid_selection";
		this._selectionObj = div;
		this.obj.appendChild(this._selectionObj);
	}
	this._selectionObj.style.width = "0px";
	this._selectionObj.style.height = "0px";
	this._selectionObj.style.left = x + "px";
	this._selectionObj.style.top = y + "px";
	this._selectionObj.startX = x;
	this._selectionObj.startY = y;
};
dhtmlXGridObject.prototype._ShowSelection = function () {
	if (this._selectionObj) {
		this._selectionObj.style.display = "";
	}
};
dhtmlXGridObject.prototype._HideSelection = function () {
	if (this._selectionObj) {
		this._selectionObj.style.display = "none";
	}
	this._selectionArea = null;
};
dhtmlXGridObject.prototype.copyBlockToClipboard = function () {
	if (this._selectionArea != null) {
		var serialized = new Array();
		this._agetm = this._mathSerialization ? "getMathValue" : "getValue";
		for (var i = this._selectionArea.LeftTopRow; i <= this._selectionArea.RightBottomRow; i++) {
			var data = this._serializeRowToCVS(this.rowsCol[i], null, this._selectionArea.LeftTopCol, this._selectionArea.RightBottomCol + 1);
			serialized[serialized.length] = data.substr(data.indexOf(this._csvDelim) + 1);
		}
		serialized = serialized.join(this._CSVRowDelimiter);
		this.toClipBoard(serialized);
	}
};
dhtmlXGridObject.prototype.pasteBlockFromClipboard = function () {
	var serialized = this.fromClipBoard();
	if (this._selectionArea != null) {
		var startRow = this._selectionArea.LeftTopRow;
		var startCol = this._selectionArea.LeftTopCol;
	} else {
		if (this.cell != null) {
			var startRow = this.getRowIndex(this.cell.parentNode.idd);
			var startCol = this.cell._cellIndex;
		} else {
			return false;
		}
	}
	serialized = serialized.split(this._CSVRowDelimiter);
	for (var i = 0; i < serialized.length; i++) {
		serialized[i] = serialized[i].split(this._csvDelim);
	}
	var endRow = startRow + serialized.length;
	var endCol = startCol + serialized[0].length;
	if (endCol > this._cCount) {
		endCol = this._cCount;
	}
	var k = 0;
	for (var i = startRow; i < endRow; i++) {
		var row = this.rowsCol[i];
		if (!row) {
			continue;
		}
		var l = 0;
		for (var j = startCol; j < endCol; j++) {
			var ed = this.cells3(row, j);
			ed[ed.setImage ? "setLabel" : "setValue"](serialized[k][l++]);
		}
		k++;
	}
};

