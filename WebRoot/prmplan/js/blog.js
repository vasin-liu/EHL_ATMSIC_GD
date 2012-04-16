/*
 * 疑难解答类
 */

function Blog(metadata) {
	this.metadata = metadata;
}

(function() {
	$.extend = function(obj1, obj2) {
		if (!obj2) {
			obj2 = obj1;
			obj1 = {};
		}
		for ( var attr in obj2) {
			obj1[attr] = obj2[attr];
		}
		return obj1;
	};
	var prototype = {
		addInit : function() {
			this.setData({
				jgid : baseInfo.jgid,
				jgmc : baseInfo.jgmc,
				time : baseInfo.time
			});
//			this.setData({
//				pname : "张山",
//				title : "交通警情信息统计如何使用",
//				content : "  有没有这种功能，有的话在哪里进行统计？"
//			});
			$("tdApath").innerHTML = UDload.getForm(600);
			BaseObject.prototype.addInit.call(this);
		},
		add : function(isValid) {
			if (isValid == true || this.addCheck()) {
				if (!isValid) {
					var _this = this;
					UDload.cbfname = function() {
						_this.add(true);
					};
					UDload.upload();
				} else {
					var params = this.getData();
					params.apath = UDload.getUpapath();
					BaseObject.prototype.add.call(this, true, params);
				}
			}
		},
		addCheck : function(ids) {
			var ids = [ "pname", "title", "content" ];
			return BaseObject.prototype.addCheck.call(this, ids);
		},
		queryInit : function() {
			this.stime = $("stime");
			this.etime = $("etime");
			if(baseInfo.jglx == "1"){
				$("imgJgid").style.display = "none";
				$("jgid").value = baseInfo.jgid;
				var jgmc = $("jgmc");
				jgmc.disabled = true;
				jgmc.value = baseInfo.jgmc;
			}
			BaseObject.prototype.queryInit.call(this);
		},
		query : function(pageNum) {
			if (this.queryCheck()) {
				var parts = [];
				var jgid = window.G_jgID || this.jgid.value;
				if (jgid) {
					parts.push("jgid='" + jgid + "'");
				}
				var stime = this.stime.value;
				stime = Date.oracle.formattingStart(stime);
				if (stime) {
					parts.push("time>=" + stime);
				}
				var etime = this.etime.value;
				etime = Date.oracle.formattingEnd(etime);
				if (etime) {
					parts.push("time<=" + etime);
				}
				var title = this.title.value;
				if (title) {
					parts.push("title like '%" + title + "%'");
				}
				var where = parts.join(" and ");
				if (where) {
					where = " where " + where;
				}
				
				var sql = " select id,jgid,(select jgmc from t_sys_department where jgid = q.jgid)"
						+ ",pname,to_char(time,'yyyy-mm-dd hh24:mi'),title,content,apath";
				sql += " from T_PRMPLAN_BLOG q";
				sql += where;
				sql += " order by time desc";
				
				BaseObject.prototype.query.call(this, sql,pageNum);
			}
		},
		queryCheck : function() {
			return BaseObject.prototype.queryCheck.call(this);
		},
		queryShowResult : function(xmldoc) {
			var tindexs = [ 2, 3, 4, 5];
//			var fnames = this.metadata.columns.slice(0);
//			var fdescs = this.metadata.comments.slice(0);
			var operates = [queryResult.getWatch(true)];
			var operate = baseInfo.operate || "";
			if(operate.indexOf($operates.modify.code) != -1){
				operates.push(queryResult.getDeal(false));
			}
			var func = BaseObject.prototype.queryShowResult;
			func.call(this, xmldoc, tindexs, operates);
		},
		watchInit : function() {
			this.watchGetData();
			if (this.data) {
				this.pname.disabled = true;
				this.setData(this.data);
				$("tdTitle").innerHTML = this.data.title;
				$("tdContent").innerHTML = this.data.content;
				var html = UDload.getAttachHtmls(this.data.apath);
				$("tdApath").innerHTML = html || "没有上传附件";
			} else {
				window.close();
				alert($prompts.error.loadinfo);
			}
		},
		dealInit : function() {
			this.modifyInit();
		},
		modifyInit : function() {
			this.watchGetData();
			this.setData(this.data);
			if (baseInfo.jgid != this.data.jgid) {
				this.pname.disabled = true;
				this.title.disabled = true;
				this.content.disabled = true;
				$("tdApath").innerHTML = UDload.getAttachHtmls(this.data.apath)
						|| "没有上传附件";
			} else {
				UDload.setDownloadModel("clear", 520, 5);
				$("tdApath").innerHTML = UDload.getAttachHtmls(this.data.apath);
				BaseObject.prototype.modifyInit.call(this);
			}
		},
		modify : function(isValid) {
			if (isValid || this.modifyCheck()) {
				var _this = this;
				if (!isValid) {
					UDload.cbfname = function() {
						_this.modify(true);
					};
					UDload.upload();
				} else {
					var ids = this.metadata.modifyids;
					var params = this.getData(ids);
					params.apath = UDload.getUpapath();
					BaseObject.prototype.modify.call(this, params);
				}
			}
		},
		modifyCheck : function() {
			return true;
		}
	};
	Blog.prototype = $.extend(BaseObject.prototype);
	Blog.prototype = $.extend(Blog.prototype, prototype);
})();

var blog = new Blog();
blog.metadata = {
	name : "blog",
	desc : "交宣微博",
	items : {
		"id" : "主键编号",
		"jgid" : "录入单位编号",
		"jgmc" : "录入单位",
		"pname" : "录入人",
		"time" : "录入时间",
		"title" : "标题",
		"content" : "内容",
		"apath" : "附件"
	},
	request : {
		module : "prmplan",
		node : "blog"
	},
	modifyids : [ "id", "pname", "title", "content" ]
};
window.attachEvent("onload",function(){
	blog.init();
});
