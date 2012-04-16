/**
 * 权限类
 * 
 * @param func
 *            功能编号
 * @param operate
 *            操作编号
 */
function Privilege(func, operate) {
	this.func = func;
	this.operate = operate;
}

Privilege.prototype = {
	/** 功能上是否具有该操作 */
	has : function(funcs) {
		funcs = funcs || {};
		var operate = funcs[this.func];
		return (operate && operate.indexOf(this.operate) != -1);
	},
	/** 进行操作控制 */
	ctrl : function() {
		// 删除第一个参数
		var funcs = [].shift.apply(arguments);
		if (this.has(funcs)) {
			return this.doHas.apply(this, arguments);
		} else {
			return this.doNotHas.apply(this, arguments);
		}
	},
	/** 如果该功能上具有该操做，所调用的函数 */
	doHas : function() {

	},
	/** 如果该功能上具有该操做，所调用的函数 */
	doNotHas : function() {

	}
};
/** 将功能字符串转换为对象 */
Privilege.toObject = function(operate) {
	var objects = {}, attr, func;
	var funcs = operate.split(";");
	for ( var i = 0; i < funcs.length; i++) {
		func = funcs[i];
		index = func.indexOf(",");
		attr = func.substring(0, index);
		objects[attr] = func.substring(index + 1);
	}
	return objects;
};
/**
 * <pre>
 * 显示节点权限 
 * 查看交通警情时，控制5类交通警情的显示
 * 通过控制group.js中节点的显示来实现
 * </pre>
 */
function ShowNodePrivi(func) {
	Privilege.apply(this, arguments);
	this.operate = "showNode";
}
ShowNodePrivi.prototype = Privilege.prototype;
ShowNodePrivi.prototype.doNotHas = function(items, pages) {
	var no = parseInt(this.func.substring(4)) - 1;
	items[no] = "";
	pages[no] = "";
};
/** 为url追加功能节点 */
ShowNodePrivi.appendFuncId = function(url) {
	return url + "?funcId=5707";
};
/** 为url追加功能节点 */
ShowNodePrivi.appendFuncIds = function(items) {
	for ( var i = 0; i < items.length; i++) {
		items[i] = ShowNodePrivi.appendFuncId(items[i]);
	}
};
/** 为index_dispatch.jsp中，左树界面和主界面的src属性添加功能参数 */
ShowNodePrivi.setIndexUrl = function() {
	window.attachEvent("onload", function() {
		var leftTree = $("sidebar");
		leftTree.src = ShowNodePrivi.appendFuncId("lefttree.jsp");
		var target = $("moduletarget");
		target.src = ShowNodePrivi.appendFuncId("ehl/cpoliceedit/materialInfoQuery.jsp");
	});
};
/** 隐藏左树上警情信息查询下的5类警情节点 */
ShowNodePrivi.deleteChilds = function(tree) {
	tree.deleteChildItems(tree.sidToId["570700"]);
};
/** 进行权限控制后，为查询节点重新设置url */
ShowNodePrivi.changeUrl = function(sidToId) {
	var operate = $("operate").value;
	var funcId = operate.substring(0, operate.indexOf(","));
	return sidToId[funcId];
};
/** 与group.js中items、page排序相同 */
ShowNodePrivi.funcs = [ "570701", "570702", "570703", "570704", "570705" ,"570706"];
/** 显示节点权限控制 */
ShowNodePrivi.ctrl = function(otherInfo,items,page){
	var pages = otherInfo.pages;
	var operates = $("operate").value;
	operates = Privilege.toObject(operates);
	var node = new ShowNodePrivi();
	var funcs = ShowNodePrivi.funcs;
	for(var i=0;i<funcs.length;i++){
		node.func = funcs[i];
		node.ctrl(operates,items,pages);
	}
	
	for(var i=pages.length-1;i>=0;i--){
		if(!pages[i]){
			pages.splice(i,1);
			items.splice(i, 1);
		}
	}
	
	if(pages.join(",").indexOf(page) == -1){
		var url = "";
		if(pages[0]){
			url = ShowNodePrivi.appendFuncId(pages[0]);
		}else{
			url = "about:blank";
			ShowNodePrivi.showNoPriviMsg();
		}
		window.open(url,otherInfo.target);
		return false;
	}
	return true;
};
/**显示没有权限提示*/
ShowNodePrivi.showNoPriviMsg = function(){
	alert("没有警情信息查询权限！");
};
/**改变功能到父编号*/
ShowNodePrivi.changeToParent = function(funcId) {
	return funcId.substring(0, 4) == "5707" ? "570700" : funcId;
};