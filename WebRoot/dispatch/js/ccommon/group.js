Array.prototype.getIndex = function(object) {
	var length = this.length;
	for ( var i = 0; i < length; i++) {
		if (this[i] == object) {
			return i;
		}
	}
	return -1;
};

function Group(label, group, defaultSelectedIndex, otherInfo) {
	this.label = label;
	this.group = group || [];
	this.defaultSelectedIndex = defaultSelectedIndex || 0;
	this.selectedIndex = -1;
	this.elements = [];
	this.otherInfo = otherInfo;
}

Group.style = {
	container : "group_container",
	label : "group_label",
	item : "group_item",
	selected : "group_item_selected",
	unselected : "group_item_unselected"
};

Group.prototype = {
	addItem : function(text, isSelected) {
		var _this = this;
		var el = document.createElement("td");
		el.innerHTML = "<input type='radio' name='rdGroup'/>" + text;
		el.className = Group.style.item + " " + Group.style.unselected;
		el.attachEvent("onclick", function() {
			_this.select(el);
		});
		this.elements.push(el);
		if (isSelected) {
			this.select(el);
		}
		return el;
	},
	setContainer : function(el) {
		if (el && el.nodeName == "TABLE") {
			var tr = document.createElement("tr");
			var td = document.createElement("td");
			td.innerHTML = this.label;
			td.className = Group.style.label;
			tr.appendChild(td);
			var length = this.group.length;
			for ( var i = 0; i < length; i++) {
				tr.appendChild(this.addItem(this.group[i]));
			}
			el.className = Group.style.container;
			el.cellPadding = "0";
			el.cellSpacing = "0";
			el.border = "1";
			el.borderColor = "#a5d1ec";
			el.firstChild.appendChild(tr);
			this.select(this.elements[this.defaultSelectedIndex]);
		}
	},
	select : function(el) {
		var selectedElement = this.elements[this.selectedIndex];
		if (el && el.nodeName && el != selectedElement) {
			el.className = Group.style.item + " " + Group.style.selected;
			var _selectedIndex = this.selectedIndex;
			this.selectedIndex = this.elements.getIndex(el);
			if (selectedElement) {
				selectedElement.className = Group.style.item + " "
						+ Group.style.unselected;
			}
			el.firstChild.checked = true;
			if (_selectedIndex != -1) {
				this.doOtherThing(el);
			}
		}
	},
	doOtherThing : function(el) {
		if (typeof (this.otherInfo) == "object") {
			var pages = this.otherInfo.pages;
			if (pages instanceof Array) {
				window.open(pages[this.selectedIndex], this.otherInfo.target);
			}
		}
	}
};

window.attachEvent("onload", function() {
	var items = [ "全部", "重特大交通事故", "交通拥堵", "协查通报", "施工占道信息", "其他重大情况" ];
	var otherInfo = {
		pages : ["allMaterialInfo.jsp", "materialInfoQuery.jsp", "TrafficCrowdQuery.jsp",
				"xcbkSearch.jsp", "RoadBuildQuery.jsp", "NoticeQuery.jsp" ],
		target : "moduletarget"
	};
	var page = getPage();
	if(document.getElementById("jgbh").value.length == "2"){
		items[5] = "其他重大情况/值班日志";
	}
	
	if(!ShowNodePrivi.ctrl(otherInfo,items,page)){
		return;
	}else{
		ShowNodePrivi.appendFuncIds(otherInfo.pages);
		page = ShowNodePrivi.appendFuncId(page);
	}
	var defaultSelectedIndex = otherInfo.pages.getIndex(page);
	var group = new Group("事件类别：", items, defaultSelectedIndex, otherInfo);
	group.setContainer(document.getElementById("group"));
});

window.getPage = function(url) {
	url = url || window.location.href;
	var index = url.indexOf("?");
	if (index != -1) {
		url = url.substring(0, index);
	}
	index = url.lastIndexOf("/");
	var page = url.substring(index + 1);
	return page;
};