(function() {
	String.prototype.endWith = function(value) {
		if (value && typeof (value) == "string") {
			if (this.substring(this.length - value.length) == value) {
				return true;
			}
		}
		return false;
	}
	//fr报表服务名称
	var frsname = "WebReport";
	//转换成fr报表链接，request action name
	window.toFRUrl = function(raname) {
		if (window.basePath && raname) {
			return basePath + frsname + "/" + raname;
		}
		return null;
	}

	//初始化点击节点，根据节点名称点击
	//有多个节点都含有label的文本
	//慎用
	window.initClick = function(label) {
		var all = document.all;
		for ( var i = 0; i < all.length; i++) {
			if (all[i].innerText == label) {
				var pnode = all[i];
				while (!pnode.onclick && pnode != document.body) {
					pnode = pnode.parentNode;
				}
				if (pnode != document.body) {
					pnode.click();
					break;
				}
			}
		}
	}

})();
