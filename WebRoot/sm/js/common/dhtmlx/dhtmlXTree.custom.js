dhtmlXTreeObject.prototype.initUserData = function() {
	var doc = this.XMLLoader.xmlDoc.responseXML.documentElement;
	var items = doc.getElementsByTagName("item"), item, sid, id;
	this.sidToId = {}, this.idToSid = {};
	for ( var i = 0; i < items.length; i++) {
		item = items[i];
		id = item.getAttribute("id");
		sid = item.getAttribute("sid");
		this.idToSid[id] = sid;
		this.sidToId[sid] = id;
	}
};

var leftTree = {
	funcId : function(itemId, idToSid) {
		return "funcId=" + idToSid[itemId];
	},
	title : function(itemId, tree) {
		var title = "titleSelf=" + tree.getItemText(itemId);
		var sid = tree.idToSid[itemId];
		if (sid.substring(4, 6) != "00") {
			sid = sid.substring(0, 4) + "00";
			title += "&titleParent=" + tree.getItemText(tree.sidToId[sid]);
		}
		return title;
	},
	title1 : function(itemId, tree){
		var title = "";
		var sid = tree.idToSid[itemId];
		if (sid.substring(4, 6) != "00") {
			sid = sid.substring(0, 4) + "00";
			title += "title=" + tree.getItemText(tree.sidToId[sid])+"&";
		}
		title += "title=" + tree.getItemText(itemId);
		return title;
	},
	url : function(itemId, tree) {
		var url = itemId;
		if(!(/\.d$/.test(itemId))){
			url += ".jsp";
		}
		url += "?" + this.funcId(itemId, tree.idToSid);
		url += "&" + this.title1(itemId, tree);
		url = encodeURI(url);
		return url;
	}
};
