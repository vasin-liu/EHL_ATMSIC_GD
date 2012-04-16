function Privilege(func, operate, doHas, doNotHas) {
	this.func = func;
	this.operate = operate;
	if (typeof doHas === "function") {
		this.doHas = doHas;
	}
	if (typeof doNotHas === "function") {
		this.doNotHas = doNotHas;
	}
}

Privilege.prototype = {
	has : function(funcs) {
		funcs = funcs || {};
		var operate = funcs[this.func];
		return (operate && operate.indexOf(this.operate) != -1);
	},
	ctrl : function(funcs, object) {
		if (this.has(funcs)) {
			return this.doHas(object);
		} else {
			return this.doNotHas(object);
		}
	},
	doHas : function(object) {
		return object;
	},
	doNotHas : function(object) {
		return object;
	}
};

function PriviFactory() {

}

PriviFactory.getPrivi = function(privi, is) {
	var en = null;
	var dis = PriviFactory.privis[PriviFactory.operates[privi]];
	if (is) {
		en = dis;
		dis = null;
	}
	if (!en && !dis) {
		alert("无效的权限！");
		return;
	}
	return new Privilege("", privi, en, dis);
};

PriviFactory.operates = {
	"showNode" : "disableNode",
	"showVidicon" : "disableVidicon"
};

PriviFactory.privis = {
	// 禁用模块，即禁用复选框
	disableNode : function(object) {
		object = object || {};
		var attr = this.func;
		var defaultChks = object.defaultChks || [];
		var itemCkbxs = object.itemCkbxs || [];
		var funcToChk = object.funcToChk || {};
		// 移除默认选中项
		for ( var i = 0; i < defaultChks.length; i++) {
			if (defaultChks[i] == attr) {
				defaultChks.splice(i, 1);
				break;
			}
		}
		// 移除自动刷新项
		for ( var i = 0; i < itemCkbxs.length; i++) {
			if (itemCkbxs[i] == attr) {
				itemCkbxs[i].splice(i, 1);
				itemShows[i].splice(i, 1);
				itemMNames[i].splice(i, 1);
				break;
			}
		}
		// 设置不能操作
		var chk = $(funcToChk[attr]);
		if (chk.checked) {
			chk.click();
		}
		for ( var i = 0; i < itemCkbxs.length; i++) {
			if (itemCkbxs[i] == funcToChk[attr]) {
				$("show" + (i + 1)).className = "dischecked";
				break;
			}
		}
		chk.disabled = true;
	},
	// 禁用摄像头，即从html语句中消除掉摄像头的语句
	//控制位置在tgsAll.js(298~303)
	disableVidicon : function(object) {
		object = object || "";
		var reg = /<tr id='tdVidicon' .*>.*<\/tr>/;
		var match = reg.exec(object);
		object = object.replace(match, "");
		return object;
	}
};
