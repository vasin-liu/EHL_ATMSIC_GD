// Lee dialog 1.0 http://www.xij.cn/blog/?p=68

var dialogFirst = true;
function dialog(title, content, width, height, cssName) {
	if (dialogFirst == true) {
		var temp_float = new String;
		temp_float = "<div id=\"floatBoxBg\" style=\"height:" + jQuery(document).height() + "px;filter:alpha(opacity=0);opacity:0;\"></div>";
		temp_float += "<div id=\"floatBox\" class=\"floatBox\">";
		temp_float += "<div class=\"title\"><h4></h4><span></span></div>";
		temp_float += "<div class=\"content\"></div>";
		temp_float += "</div>";
		jQuery("body").append(temp_float);
		dialogFirst = false;
	}

	jQuery("#floatBox .title span").click(
					function() {
						jQuery(".mainbody").show();
						//ctrlDiv();//提示消息弹出框
						jQuery("#floatBoxBg").animate({opacity : "0"}, "normal", function(){jQuery(this).hide();});
						jQuery("#floatBox").animate(
										{
											top : (jQuery(document).scrollTop() - (height == "auto" ? 300: parseInt(height)))+ "px"
										}, "normal", function() {
											jQuery(this).hide();
										});
					});

	jQuery("#floatBox .title h4").html(title);
	contentType = content.substring(0, content.indexOf(":"));
	content = content.substring(content.indexOf(":") + 1, content.length);
	switch (contentType) {
		case "url":
			var content_array = content.split("?");
			jQuery("#floatBox .content").ajaxStart(function() {
				jQuery(this).html("loading...");
			});
			jQuery.ajax( {
				type : content_array[0],
				url : content_array[1],
				data : content_array[2],
				error : function() {
					jQuery("#floatBox .content").html("error...");
				},
				success : function(html) {
					jQuery("#floatBox .content").html(html);
				}
			});
			break;
		case "text":
			jQuery("#floatBox .content").html(content);
			break;
		case "id":
			jQuery("#floatBox .content").html(jQuery("#" + content + "").html());
			break;
		case "iframe":
			jQuery("#floatBox .content").html(
						"<iframe src=\""+ content+ "\" width=\"100%\" height=\"" + (parseInt(height) - 30) + "px"
								+ "\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
	}

	jQuery("#floatBoxBg").show();
	jQuery("#floatBoxBg").animate( {opacity : "0.5"}, "normal");
	jQuery("#floatBox").attr("class", "floatBox " + cssName);
	jQuery("#floatBox").css(
			{
				display : "block",
				left : ((jQuery(document).width()) / 2 - (parseInt(width) / 2)) + "px",
				top : (jQuery(document).scrollTop() - (height == "auto" ? 300 : parseInt(height))) + "px",
				width : width,
				height : height
			});
	jQuery("#floatBox").animate( {top : (jQuery(document).scrollTop() + 50) + "px" }, "normal");
}