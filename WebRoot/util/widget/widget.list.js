(function($) {
	$.ehl = {};
	$.ehl.core = {
		markabbr : {
			"checkbox" : "ck",
			"radio" : "rd",
			"label" : "lbl",
			"li" : "li",
			"ul" : "ul"
		},
		name : function() {
			var parts = [];
			for ( var i = 0; i < arguments.length; i++) {
				arguments[i] && parts.push(arguments[i]);
			}
			return parts.join("_");
		},
		tname : function(){
			arguments[0] = this.markabbr[arguments[0]] || arguments[0];
			return this.name.apply(this, arguments);
		},
		context : function(id,markabbr) {
			markabbr = markabbr || this.markabbr;
			var name = function(attr, id) {
				return function(value) {
					return $.ehl.core.name(attr, id, value);
				};
			};
			var ctxt = {};
			for ( var mark in markabbr) {
				var attr = markabbr[mark];
				ctxt[attr] = name(attr, id);
			}
			return ctxt;
		}
	};
	$.ehl.widget = {};
	$.ehl.widget.item = function(options) {
		var opts = $.extend({}, this.item.defaults, options);
		var core = $.ehl.core;
		var iid = core.tname(opts.type, opts.id, opts.value);
		var iattrs = {
			id : iid,
			type : opts.type,
			name : core.tname(opts.type, opts.id),
			value : opts.value
		}, lattrs = {
			id : core.tname("label", opts.id),
			"for" : iid
		};
		var striattrs = [];
		$.each(iattrs, function(attr, value) {
			value && striattrs.push(attr + "='" + value + "'");
		});
		var input = "<input " + striattrs.join(" ") + " />";
		var strlattrs = [];
		$.each(lattrs, function(attr, value) {
			value && strlattrs.push(attr + "='" + value + "'");
		});
		var label = "<label " + strlattrs.join(" ") + ">" + opts.html + "</label>";
		var item = $(eval(opts.order[0]) + eval(opts.order[1]));
		opts.parent && item.appendTo(opts.parent);
		return item;
	};
	$.extend($.ehl.widget.item, {
		context : function(id){
			return $.ehl.core.context(id, this.defaults.markabbr);
		},
		defaults : {
			type : "checkbox",
			order : [ "input","label" ],
			markabbr : {
				"checkbox" : "ck",
				"radio" : "rd",
				"lable" : "lbl"
			}
		}
	});
	/**
	 * <pre>
	 * 生成列表
	 * bug:
	 * 1.命名规则，元素id的生成策略，被固定，应提供函数生成
	 * 2.标题样式名可能和某一项的值名相同，应提供可选
	 * 3.元素访问麻烦，ck_publishtype_gaw太长，应提供简单的访问方式，
	 * 根据函数名确定元素标签，根据上下文默认将id赋值，如此只用输入value即可
	 * </pre>
	 * 
	 * @return jQuery
	 */
	$.ehl.widget.list = function(options) {
		var opts = $.extend({}, this.list.defaults, options);
		var type = opts.type, iabbr = opts.markabbr[type], clz = opts.className;
		var input = "<input id='" + iabbr + "_#id_#value' name='" + iabbr + "_#id' type='" + type + "' value='#value' />";
		var label = "<label id='lbl_#id_#value' for='" + iabbr + "_#id_#value' >#text</label>";
		var item = input + label;
		var li = "<li id='li_#id_#value' class='#class'>" + item + "</li>";
		var lis = [], index = 1;
		opts.title && lis.push("<li id='li_#id_title' class='" + clz + "_title'>" + opts.title + "</li>");
		for ( var attr in opts.data) {
			var temp = li.replace(/#value/g, attr);
			temp = temp.replace(/#text/g, opts.data[attr]);
			temp = temp.replace(/#class/g, clz + "_" + (index++));
			lis.push(temp);
		}
		var ul = "<ul id='ul_#id' class='" + clz + "'>" + lis.join("") + "</ul>";
		opts.id && (ul = ul.replace(/#id/g, opts.id));
		ul = $(ul);
		opts.parent && ul.appendTo(opts.parent);
		return ul;
	};
	$.extend($.ehl.widget.list, {
		defaults : {
			title : "",
			className : "ehl_list_line",
			type : "checkbox",
			markabbr : {
				"checkbox" : "ck",
				"radio" : "rd",
				"lable" : "lbl",
				"li" : "li",
				"ul" : "ul"
			}
		},
		context : function(id) {
			var markabbr = this.defaults.markabbr;
			var name = function(attr, id) {
				return function(value) {
					return $.ehl.core.name(attr, id, value);
				};
			};
			var ctxt = {};
			for ( var mark in markabbr) {
				var attr = markabbr[mark];
				ctxt[attr] = name(attr, id);
			}
			return ctxt;
		}
	});
	$.ehl.core.markabbr = $.extend({}, $.ehl.core.markabbr, $.ehl.widget.list.marrkabbr);
})(jQuery);