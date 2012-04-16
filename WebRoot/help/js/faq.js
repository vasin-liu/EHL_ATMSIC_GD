/**
 * 疑难解答类
 */

function FAQ(metadata) {
	BaseObject.call(this);
}
(function() {
	var prototype = {
		addInit : function(params, name) {
			this.setData({
				jgid : baseInfo.jgid,
				jgmc : baseInfo.jgmc,
				time : baseInfo.time
			});
			this.setData({
				pname : "周朝荣",
				title : "疑难解答",
				question : "你的疑难问题",
				answer : "解答你的疑难问题"
			});
			$("tdApath").innerHTML = UDload.getForm(600);
			BaseObject.prototype.addInit.call(this, params, name);
		},
		add : function(isValid, params, name) {
			if (isValid == true || this.addCheck()) {
				var _this = this;
				if (!isValid) {
					UDload.cbfname = function() {
						_this.add(true, params, name);
					};
					UDload.upload();
				} else {
					var params = this.getData();
					params.apath = UDload.getUpapath();
					BaseObject.prototype.add.call(this, true, params, name);
				}
			}
		},
		addCheck : function() {
			var ids = [ "pname", "title", "question", "answer" ];
			return BaseObject.prototype.addCheck.call(this, ids);
		},
		queryInit : function(data) {
			this.stime = $("stime");
			this.etime = $("etime");
			// this.setData(data || baseInfo);
			return BaseObject.prototype.queryInit.call(this);
		},
		query : function() {
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
						+ ",pname,to_char(time,'yyyy-mm-dd hh24:mi'),title,question,answer,apath";
				sql += " from T_HELP_FAQ q";
				sql += where;
				sql += " order by time desc";
				BaseObject.prototype.query.call(this, sql);
			}
		},
		_queryShowResult : function() {
			var objects = this.queryDatas,object;
			var htmls = [];
			for ( var i = 0; i < objects.length; i++) {
				object = objects[i];
				var question = (i+1) + "：" + object.question;
				question = "<p class='item'>" + question + "</p>";
				var answer = "答案：" + object.answer;
				answer = "<p class='item'>" + answer + "</p>";
				answer += BaseObject.getOperate("",object.id,this.metadata.objName);
				var container = "<div class='data_query_full'>" + question + answer + "</div>";
				htmls.push(container);
			}
			if(htmls.length == 0){
				return "<div style='font-size:12px;text-align:center;'>此条件下没有数据</div>";
			}else{
				return "<div class='datas_query_full'>" + htmls.join("") + "</div>";
			}
		},
		queryShowResult : function(xmldoc) {
			var func = BaseObject.prototype.queryShowResult;
			func.call(this, xmldoc);
		},
		watchInit : function() {
			this.watchGetData();
			if (this.data) {
				this.setData(this.data);
				this.pname.disabled = true;
				this.title.disabled = true;
				this.question.disabled = true;
				this.answer.disabled = true;
				var html = UDload.getAttachHtmls(this.data.apath);
				$("tdApath").innerHTML = html || "没有上传附件";
			} else {
				window.close();
				alert($prompts.error.loadinfo);
			}
		},
		dealInit : function() {
			this.watchGetData();
			this.setData(this.data);
			this.modifyInit();
		},
		modifyInit : function() {
			if (baseInfo.jgid != this.data.jgid) {
				this.pname.disabled = true;
				this.title.disabled = true;
				this.question.disabled = true;
				this.answer.disabled = true;
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
				if (!isValid) {
					var _this = this;
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
			var ids = [ "pname", "title", "question", "answer" ];
			return BaseObject.prototype.modifyCheck.call(this, ids);
		}
	};
	FAQ.prototype = $.extend(BaseObject.prototype);
	FAQ.prototype = $.extend(FAQ.prototype, prototype);
})();

var faq = new FAQ();
faq.metadata = {
	name : "faq",
	desc : "常见问题解答",
	items : {
		"id" : "主键编号",
		"jgid" : "录入单位编号",
		"jgmc" : "录入单位",
		"pname" : "录入人",
		"time" : "录入时间",
		"title" : "标题",
		"question" : "问题",
		"answer" : "答案",
		"apath" : "附件"
	},
	request : {
		module : "help",
		node : "faq"
	},
	fileName : "faq",
	objName : "faq",
	modifyids : [ "id", "pname", "title", "question", "answer" ],
	pagingCount : 10
};

$events.insert(window, "onload", function() {
	faq.init();
	if (baseInfo.page == $operates.query.page) {
		var operates = baseInfo.operate || "";
		if (operates.indexOf($operates.add.code) != -1) {
			faq.btnOpenAdd = $("btnOpenAdd");
			faq.btnOpenAdd.style.display = "inline";
		}
	} else if (baseInfo.page == "4") {
		faq.addInit(null, leave.metadata.objName);
		leave.id = faq.id;
		leave.watchGetData();
		faq.question.value = leave.data.content;
		faq.answer.value = getContent(leave.data.reply);
	}
});

$events.init();

function getContent(infos) {
	if (typeof (infos) != "object") {
		return "";
	}
	var str = "";
	if (infos instanceof Array) {
		var strs = [];
		for ( var i = 0; i < infos.length; i++) {
			strs.push(getContent(infos[i]));
		}
		str = strs.join("\n");
	} else {
		str = "  " + infos.content + "\n";
		str += "[" + (infos.jgmc ? infos.jgmc + " " : "") + infos.pname + " "
				+ infos.time + "]";
	}
	return str;
}

function showSingle(index, object,objname) {
	var question = index + "：" + object.question;
	question = "<p class='item'>" + question + "</p>";
	var answer = "答案：" + object.answer;
	answer = "<p class='item'>" + answer + "</p>";
	answer += BaseObject.getOperate("",object.id,objname);
	var container = "<div class='data_query_full'>" + question + answer + "</div>";
	return container;
}

function showMulit(objects,objname) {
	var htmls = [];
	for ( var i = 0; i < objects.length; i++) {
		htmls.push(showSingle(i + 1, objects[i],objname));
	}
	if(htmls.length == 0){
		return null;
	}
	return "<div class='operate_query_fulldata'>" + htmls.join("") + "</div>";
}

BaseObject.getOperate = function(url,id,objname) {
	var operates = baseInfo.operate || "";
	var htmls = [], operate;
	for ( var attr in $operates) {
		operate = $operates[attr];
		if(attr == "add" || attr == "watch"){
			continue;
		}
		if (operates.indexOf(operate.code) != -1) {
			htmls.push(BaseObject.getButton(url,attr,id,objname));
		}
	}
	htmls = htmls.join("");
	var contain = "<div id='operate' class='operates_query_fulldata'>" + htmls + "</div>";
	return contain;
};

BaseObject.getButton = function(url, ope, id, objname) {
	url = url ? (url + "?") : "";
	url += "id=" + (id||"");
	var operate = $operates[ope];
	url += "&page="+operate.page;
	var attrs = " href='javascript:void(0)'";
	attrs += " class='operate_query_fulldata'";
	var attr = 'openCenter("'+url+'")';
	if(ope == "delete"){
		attr = objname+'["delete"]("'+id+'")';
	}
	attrs += ' onclick=\''+attr+'\'';
	return "<a" + attrs + ">" + operate.desc + "</a>";
};