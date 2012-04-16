function SpringTransport() {

}

SpringTransport.prototype = {
	init : function() {
		this.stime = $("stime");
		this.etime = $("etime");
		var time = $("time").value.substring(0, 10);
		this.stime.value = time;
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
					stime : this.stime.value,
					etime : this.etime.value
				},
				onComplete : function(xmlHttp) {
					var xmldoc = xmlHttp.responseXML;
					var objects = _this.xmlToObject(xmldoc);
					var table = _this.objectToTable(objects);
					_this.showResult(table);
				}
			};
			new Ajax.Request("policeWorks.newsFiles.springStatis.d", request);
		}
	},
	check : function() {
		return true;
	},
	xmlToObject : function(xmldoc) {
		var objects = [];
		var nodes = xmldoc.getElementsByTagName("spring");
		for ( var i = 0; i < nodes.length; i++) {
			objects.push(BaseObject.getData(nodes[i]));
		}
		return objects;
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
		var columns = [ "上报单位", "省厅采用工作信息（篇）" ];
		for ( var i = 0; i < columns.length; i++) {
			var th = document.createElement("th");
			th.style.cssText = style;
			th.innerText = columns[i];
			row.appendChild(th);
		}
		var bgs = [ "", "background-color: #CCCCFF" ];// #b5d6e6
		for ( var i = 0; i < objects.length; i++) {
			var row = table.insertRow(table.rows.length);
			for ( var attr in objects[i]) {
				if ("jgid".indexOf(attr) == -1) {
					var cell = row.insertCell(row.cells.length);
					cell.style.cssText = style + bgs[i % 2];
					cell.innerText = objects[i][attr] || "0";
				}
			}
		}
		if (objects.length <= 0) {
			var row = table.insertRow(table.rows.length);
			var cell = row.insertCell(row.cells.length);
			cell.style.cssText = style + ";text-align:center";
			cell.colSpan = table.rows[0].cells.length;
			cell.innerText = "没有可以展示的数据！";
		}
		return table;
	},
	showResult : function(element) {
		var container = $("tdData");
		container.innerHTML = "";
		setTimeout(function() {
			container.appendChild(element);
		}, 100);
	}
};

window.attachEvent("onload", function() {
	var spring = new SpringTransport();
	spring.init();
	spring.statis();
});