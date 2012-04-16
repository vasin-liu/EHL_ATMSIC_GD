function Flow() {

}

Flow.prototype = {
	init : function() {
		this.stime = $("stime");
		this.etime = $("etime");
		var time = baseInfo.time.substring(0, 10);
		this.stime.value = time.substring(0,4)+"-01-01";
		this.etime.value = time;
		this.btnQuery = $("btnQuery");
		var _this = this;
		this.btnQuery.onclick = function() {
			_this.statis();
		};
	},
	statis : function() {
		if (this.check()) {
			var _this = this;
			var request = {
				method : "post",
				parameters : {
					jgid : baseInfo.jgid,
					sdate : this.stime.value,
					edate : this.etime.value
				},
				onComplete : function(xmlHttp) {
					var xmldoc = xmlHttp.responseXML;
					var objects = _this.xmlToObject(xmldoc);
					objects = _this.objectProcess(objects);
					var table = _this.objectToTable(objects);
					_this.showResult(table);
				}
			};
			new Ajax.Request("drpt.dailyRpt.flowStatis.d", request);
		}
	},
	check : function() {
		return true;
	},
	xmlToObject : function(xmldoc) {
		var objects = [];
		var nodes = xmldoc.getElementsByTagName("flow");
		for ( var i = 0; i < nodes.length; i++) {
			objects.push(BaseObject.getData(nodes[i]));
		}
		return objects;
	},
	objectProcess : function(objects) {
		var object, _objects = [], _object = {
			accounts : []
		};
		var length = objects.length;
		var max = 4;
		for ( var i = 0; i < length; i++) {
			object = objects[i];
			_object.accounts.push({
				count : object.count,
				jgmc : object.jgmc.replace("省公安厅机场公安局交通管理支队", "机场市公安局交通警察支队").replace("市公安局交通警察支队", "段"),
				date : object.date
			});
			if (object.gbdm != (objects[i + 1] || {}).gbdm) {
				_object.accounts.length = max;
				_object.dlmc = object.dlmc;
				_objects.push(_object);
				_object = {
					accounts : []
				};
			}
		}
		return _objects;
	},
	objectToTable : function(objects) {
		var table = document.createElement("table");
		table.border = "0";
		table.cellPadding = "5";
		table.cellSpacing = "0";
		table.style.cssText = "border-bottom: 1px solid #b5d6e6;border-right: 1px solid #b5d6e6;width:100%;text-align:center;font-size:12px;";

		var caption = table.createCaption();
		caption.innerHTML = "统计结果";
		caption.style.cssText = "font-weight:bold;";

		var row = table.insertRow(table.rows.length);
		var style = "border-top: 1px solid #b5d6e6;border-left: 1px solid #b5d6e6;";
		var columns = [ "道路名称", "路段一", "路段二", "路段三", "路段四" ];
		for ( var i = 1; i < columns.length; i++) {
			columns[i] += "（车次）";
		}
		for ( var i = 0; i < columns.length; i++) {
			var th = document.createElement("th");
			th.style.cssText = style;
			th.innerText = columns[i];
			row.appendChild(th);
		}
		for ( var i = 0; i < objects.length; i++) {
			var row = table.insertRow(table.rows.length);
			this.insertCell(row, style, objects[i].dlmc);
			this.insertCount(row, style, objects[i].accounts);
		}
		if (objects.length <= 0) {
			var row = table.insertRow(table.rows.length);
			var cell = row.insertCell(row.cells.length);
			cell.style.cssText = style + ";text-align:center";
			cell.colSpan = table.rows[0].cells.length;
			cell.innerText = "没有可以展示的数据！";
		}
		DOM.Table.rowBgcolor(table.rows);
		DOM.Table.moveBgcolor(table.rows);
		return table;
	},
	htmlCell : function(object) {
		var text = "--";
		if (object && object.count) {
			text = object.count;
			text += "<br>" + object.jgmc;
			text += "," + object.date;
		}
		return text;
	},
	insertCell : function(row, style, html) {
		var cell = row.insertCell(row.cells.length);
		cell.style.cssText = style;
		cell.innerHTML = html;
	},
	insertCount : function(row, style, objects) {
		for ( var i = 0; i < objects.length; i++) {
			this.insertCell(row, style, this.htmlCell(objects[i]));
		}
	},
	showResult : function(element) {
		var container = $("tdData");
		container.innerHTML = "";
		setTimeout(function() {
			container.appendChild(element);
			var rows = element.rows;
			var maxHeight = DOM.Table.getMaxHeight(rows);
			maxHeight += DOM.Table.getBorderHeight(element.rows[0].cells[0]);
			DOM.Table.adjustToMaxHeight(rows, maxHeight);
		}, 100);
	}
};

window.attachEvent("onload", function() {
	var flow = new Flow();
	flow.init();
	flow.statis();
});