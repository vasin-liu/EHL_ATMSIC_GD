$.fn.extend({
	ehluploadify : function(selfOpts, options) {
		var baseInfo = window.baseInfo || {};
		var id = this.attr("id");
		var sopts = $.extend({
			id : id + "_select",
			hiddenId : id.replace("Desc", ""),
			dataName : "filePath",
			width : "600px",
			inputWidth : "480px",
			basePath : (baseInfo.contextPath || "") + "util/jquery/uploadify/"
		}, selfOpts);
		var opts = $.extend({
			"uploader" : sopts.basePath + "uploadify.swf",
			"buttonImg" : sopts.basePath + "brower.jpg",
			"cancelImg" : sopts.basePath + "cancel.png",
			"width" : "73",
			"height" : "20",
			"queueID" : id + "_queue",
			"auto" : true,
			"multi" : true,
			"script" : "dispatch.fileUpDownLoad.upload.d",
			"folder" : "/" + baseInfo.jgid + "/" + (baseInfo.time || "").replace(/[^\d]/g, ""),
			"removeCompleted" : false,
			onComplete : function(event, ID, fileObj, response, data) {
				var $this = $("#" + id).data(ID, fileObj);
				$this.data(sopts.dataName).push(fileObj.filePath.substring(1));
				$this.val([ $this.val(), $this.val() ? "、" : "", fileObj.name ].join(""));
				var $hidden = $("#"+sopts.hiddenId);
				$hidden.val([ $hidden.val(), $hidden.val() ? "," : "", fileObj.filePath.substring(1) ].join(""));
			},
			onCancel : function(event, ID, fileObj, data) {
				var $this = $("#" + id);
				fileObj = $this.data(ID);
				$.map($this.data(sopts.dataName), function(item) {
					if (item == fileObj.filePath.substring(1)) {
						return null;
					}
				});
				$this.val(("、" + $this.val()).replace("、" + fileObj.name, "").substring(1));
				var $hidden = $("#"+sopts.hiddenId);
				$hidden.val(("," + $this.val()).replace("," + fileObj.filePath.substring(1), "").substring(1));
			}
		}, options);
		var parent = this.parent();
		this.remove().data(sopts.dataName, []);
		$("<div></div>").css({
			position : "relative",
			width : sopts.width
		}).append($("<div id='" + opts.queueID + "'></div>").css({
			width : "100%",
			"line-height" : "0px"
		})).append(this.css({
			width : sopts.inputWidth
		})).append($("<div></div>").css({
			position : "absolute",
			bottom : "0px",
			right : "0px",
			width : opts.width,
			height : opts.height
		}).append($("<div id='" + sopts.id + "'></div>"))).appendTo(parent);
		return $("#" + sopts.id).uploadify(opts);
	}
});
