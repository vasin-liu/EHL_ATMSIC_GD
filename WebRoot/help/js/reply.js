/**
 * 回复
 */
function Reply(metadata) {
	BaseObject.call(this);
}

(function() {
	var prototype = {
		addCheck : function() {
			var is = validates.check($("reply_content"),"回复内容");
			if(!is){
				return false;
			}
			return BaseObject.prototype.addCheck.call(this);
		}
	};
	Reply.prototype = $.extend(BaseObject.prototype);
	Reply.prototype = $.extend(Reply.prototype, prototype);
})();

var reply = new Reply();
reply.metadata = {
	name : "reply",
	desc : "回复",
	items : {
		"id" : "主键编号",
		"jgid" : "机构编号",
		"jgmc" : "录入单位",
		"pname" : "录入人",
		"time" : "录入时间",
		"content" : "内容"
	},
	objName : "reply",
	request : {
		module : "help",
		node : "reply"
	}
};
