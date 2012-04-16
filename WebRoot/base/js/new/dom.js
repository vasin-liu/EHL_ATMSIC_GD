(function() {
	function DOM() {
	}
	window.DOM = DOM;
	/** 获取元素 */
	DOM.element = function(name, attrs) {
		var element = document.createElement(name);
		if (element && attrs) {
			var style = attrs.style;
			if (style) {
				element.style.cssText = style;
				delete attrs.style;
			}
			for ( var attr in attrs) {
				element.setAttribute(attr, attrs[attr]);
			}
		}
		return element;
	};
	/** 列表选项 */
	DOM.Option = {
		/** 获取选项元素 */
		element : function(attrs) {
			return DOM.element("OPTION", attrs);
		},
		/** 获取提示选项 */
		prompt : function(lable) {
			return DOM.Option.element(DOM.Option.emptyAttrs(lable));
		},
		/** 获取提示选项数据 */
		emptyAttrs : function(lable) {
			return {
				value : "",
				innerHTML : "请选择" + (lable || "")
			};
		}
	};
	/** 下拉列表 */
	DOM.Select = {
		/** 获取列表元素 */
		element : function(attrs, options) {
			var select = DOM.element("SELECT", attrs);
			if (options instanceof Array) {
				for ( var i = 0; i < options.length; i++) {
					select.appendChild(options[i]);
				}
			}
			return select;
		},
		/** 获取列表元素便捷方式 */
		elementSpeedy : function(attrs, objects, value) {
			var options = DOM.Select.options(objects);
			var select = DOM.Select.element(attrs, options);
			DOM.Select.value(select, value);
			return select;
		},
		/** 获取选项元素 */
		options : function(objects) {
			if (objects instanceof Array) {
				var options = [];
				for ( var i = 0; i < objects.length; i++) {
					options.push(DOM.Option.element(objects[i]));
				}
				return options;
			}
		},
		/** 获取空列表元素 */
		empty : function(attrs, label) {
			return DOM.Select.element(attrs, [ DOM.Option.prompt(label) ]);
		},
		/** 设置列表值 */
		value : function(select, value) {
			if (select && select.nodeName === "SELECT"
					&& (value || value === "")) {
				var index = select.selectedIndex;
				select.value = value;
				if (select.selectedIndex === -1) {
					select.selectedIndex = index;
				}
			}
		}
	};

	function createTable() {
		var attrs = {
			className : DOM.Table.matedata.className,
			border : "0",
			cellSpacing : "0",
			cellPadding : "0"
		};
		return DOM.Table.element(attrs);
	}

	function fullContent(table, data) {
		if(data.caption){
			var caption = table.createCaption();
			caption.innerHTML = data.caption;
		}
		if(data.head){
			appendCols(table,data.head);
		}
		if(DOM.Table.matedata.showThead){
			var thead = createThead(data.head);
			table.appendChild(thead);
		}
		var infos = data.data;
		for ( var i = 0; i < infos.length; i++) {
			var info = infos[i];
			var row = table.insertRow();
			row.className = i + 1;
			for ( var attr in data.head) {
				var cell = row.insertCell();
				append(cell, info[attr]);
			}
		}
		return table;
	}

	function createThead(head) {
		var thead = document.createElement("thead");
		var tr = document.createElement("tr");
		for ( var attr in head) {
			var td = document.createElement("td");
			td.innerHTML = head[attr];
			append(tr, td);
		}
		thead.appendChild(tr);
		return thead;
	}

	function appendCols(table, head) {
		for ( var attr in head) {
			var col = document.createElement("col");
			col.className = attr;
			table.appendChild(col);
		}
	}

	function append(el, value) {
		value = value || "&nbsp;";
		if (value instanceof Array) {
			for ( var i = 0; i < value.length; i++) {
				append(el, value[i]);
			}
		} else if (value.nodeName) {
			el.appendChild(value);
		} else {
			el.innerHTML = value;
		}
	}

	DOM.Table = {
		matedata : {
			order : {
				desc : "序号",
				className : "index"
			},
			className : "solid",
			showThead : true
		},
		element : function(attrs) {
			return DOM.element("TABLE", attrs);
		},
		simpleTable : function(data) {
			var table = createTable();
			fullContent(table, data);
			DOM.Table.orderNum(table);
			return table;
		},
		getMaxHeight : function(rows) {
			var length = (rows || {}).length || 0;
			var max = -1;
			for ( var i = 0; i < length; i++) {
				max = Math.max(max, (rows[i] || {}).clientHeight || -1);
			}
			return max;
		},
		adjustToMaxHeight : function(rows, maxHeight) {
			maxHeight = maxHeight || DOM.Table.getMaxHeight(rows);
			var length = (rows || {}).length || 0;
			for ( var i = 0; i < length; i++) {
				if (rows[i] && rows[i].style) {
					rows[i].style.height = maxHeight;
				}
			}
		},
		getBorderHeight : function(cell) {
			var height = -1;
			if (cell) {
				height = (cell.offsetHeight || 0) - (cell.clientHeight || 0);
			}
			return height;
		},
		rowBgcolor : function(rows, bgs) {
			var length = (rows || {}).length || 0;
			bgs = bgs || [ "", "#EEEEEE" ];
			for ( var i = 0; i < length; i++) {
				rows[i].style.backgroundColor = bgs[i % bgs.length];
			}
		},
		moveBgcolor : function(rows, bgcolor) {
			var length = rows.length, row;
			bgcolor = bgcolor || "#CCCCCC";
			for ( var i = 0; i < length; i++) {
				row = rows[i];
				// var ori = row.style.backgroundColor;
				// row.attachEvent("onmouseover",function() {
				// ori = row.style.backgroundColor;
				// row.style.backgroundColor = bgcolor;
				// });
				// row.attachEvent("onmouseout",function() {
				// row.style.backgroundColor = ori;
				// });
				// 理应使用attachEvent，但未能获取this对象，待解决
				var ori = row.style.backgroundColor;
				row.onmouseover = function() {
					ori = this.style.backgroundColor;
					this.style.backgroundColor = bgcolor;
				};
				row.onmouseout = function() {
					this.style.backgroundColor = ori;
				};
			}
		},
		orderNum : function(table) {
			var order = DOM.Table.matedata.order;
			var col = document.createElement("col");
			col.className = "index";
			var caption = table.firstChild;
			for ( var i = 0; i < table.childNodes.length; i++) {
				if (table.childNodes[i] == table.createCaption()) {
					caption = table.childNodes[i + 1];
					break;
				}
			}
			table.insertBefore(col,caption);
			if(DOM.Table.matedata.showThead){
				var desc = document.createElement("td");
				desc.innerHTML = order.desc;
				var tr = table.tHead.firstChild;
				tr.insertBefore(desc, tr.firstChild);
			}
			var rows = table.tBodies[0].childNodes;
			for ( var i = 0; i < rows.length; i++) {
				var cell = rows[i].insertCell(0);
				cell.className = order.className;
				cell.innerText = i + 1;
			}
		}
	};
})();