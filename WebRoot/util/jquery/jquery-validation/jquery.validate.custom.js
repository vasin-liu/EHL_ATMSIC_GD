(function($) {
	// 特殊字符验证
	$.validator.addMethod("specialchar", function(value, element, params) {
		params === true && (params = [ "'", "\\^", "&", "<", ">" ]);
		params = "[" + params.join("") + "]";
		return this.optional(element) || !(new RegExp(params).test(value));
	}, function(params) {
		params === true && (params = [ "'", "^", "&", "<", ">" ]);
		params = params.join("、");
		return "此处不允许输入特殊字符" + params;
	});
	// 解决IE6层无法遮盖下拉列表bug
	var showLabel = $.validator.prototype.showLabel;
	$.extend($.validator.prototype, {
		showLabel : function(element, message) {
			showLabel.apply(this, arguments);
			$.browser.msie && $.fn.bgiframe && this.errorsFor(element).bgiframe();
		}
	});
	// 设置默认值
	$.validator.setDefaults({
		ignoreTitle : true,
		showErrors : function(errorMap, errorList) {
			var selector = this.settings.errorElement+"."+this.settings.errorClass;
			alert(selector)
			for ( var i = 0; i < errorList.length; i++) {
				var $item = $(item.element);
				if ($item.data("hover")) {
					$item.data("hover", true);
					$item.hover(function() {
						
					}, function() {
					});
				}
			}
			this.defaultShowErrors();
		},
		errorPlacement : function(error, element) {
			var pos = element.offset();
			error.appendTo(element.parent()).css({
				left : pos.left,
				top : pos.top - error.outerHeight() - 6
			});
		}
	});
})(jQuery);