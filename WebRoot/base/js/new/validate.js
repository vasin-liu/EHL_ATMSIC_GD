(function() {
	if (!window.validateInput) {
		window.validateInput = function() {
			var reg = /[\^&<>']/;
			var message = '不可输入特殊字符："^"、"&"、"<",">","\'"';
			var inputs = document.getElementsByTagName("input"), input;
			for ( var i = 0; i < inputs.length; i++) {
				input = inputs[i];
				if ((input.type == "text" || input.type == "file") && input.value && !input.readOnly
						&& !input.disabled) {
					if (reg.test(input.value)) {
						alert(message);
						input.focus();
						return false;
					}
				}
			}
			var textareas = document.getElementsByTagName("textarea"), textarea;
			for ( var i = 0; i < textareas.length; i++) {
				textarea = textareas[i];
				if (textarea.value && !textarea.readOnly && !textarea.disabled) {
					if (reg.test(textarea.value)) {
						alert(message);
						textarea.focus();
						return false;
					}
				}
			}
			return true;
		};
	}
})();

/**
 * 过滤oracle字符串中特殊字符
 * 
 * @param {Object}
 *            object
 * @return {TypeName}
 */
function filterOracle(object) {
	var type = typeof (object);
	if (type == "string") {
		return object.replace(/'/g, "''");
	} else if (type == "object") {
		if (object instanceof Array) {
			for ( var i = 0; i < object.length; i++) {
				object[i] = filterOracle(object[i]);
			}
		} else {
			for ( var attr in object) {
				object[attr] = filterOracle(object[attr]);
			}
		}
	}

	return object;
}

var validates = {
	empty : function(value) {
		if (value) {
			return "";
		}
		return "不能为空";
	},
	length : function(value, min, max) {
		if (value) {
			var length = value.length;
			if (length >= min && max <= max) {
				return "";
			}
		}
		return "长度应该在" + min + "到" + max + "之间";
	},
	show : {
		alert : function(msg) {
			alert(msg);
		},
		div : function(msg, el) {
			alert("div:" + msg);
		}
	},
	check : function(el, desc, vali, show) {
		el = $(el) || el;
		var value = el.value;
		vali = vali || "empty";
		var type = null, args = null;
		if (vali instanceof Array) {
			type = vali[0];
			args = vali;
			args[0] = value;
		} else if (typeof vali == "string") {
			type = vali;
			args = [ value ];
		}
		if (!type) {
			alert("无效验证！");
			return false;
		}
		var validate = validates[type];
		if (!validate) {
			alert("无效验证类型！");
			return false;
		}
		var msg = validate.apply(window, args);
		if (msg) {
			msg = desc + msg;
			show = show || "alert";
			if (show instanceof Array) {
				type = show[0];
				args = show;
				args[0] = msg;
			} else if (typeof show == "string") {
				type = show;
				args = [ msg ];
			}
			show = validates.show[type];
			if (show) {
				show.apply(window, args);
			} else {
				alert("无效显示类型！");
			}
			el.focus();
			return false;
		}
		return true;
	},
	checks : function(el, desc, valis, show) {
		var is = false;
		for ( var i = 0; i < valis.length; i++) {
			is = validates.check(el, desc, valis[i], show);
			if (!is) {
				return is;
			}
		}
		return true;
	}
};