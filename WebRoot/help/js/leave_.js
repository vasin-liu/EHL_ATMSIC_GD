/*
 * 疑难解答类
 */

function Leave(metadata) {
	this.metadata = metadata;
}

(function() {
	var prototype = {
		addInit : function() {
			this.setData({
				jgid : baseInfo.jgid,
				jgmc : baseInfo.jgmc,
				time : baseInfo.time
			});
			this.setData({
				title : "交通警情信息统计如何使用",
				content : "  有没有这种功能，有的话在哪里进行统计？"
			});
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
		queryInit : function(data) {
			this.stime = $("stime");
			this.etime = $("etime");
			BaseObject.prototype.queryInit.call(this);
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
						+ ",pname,to_char(time,'yyyy-mm-dd hh24:mi'),title,content,apath"
						+ ",decode((select count(id) from t_help_reply where qid=q.id),0,'未回复','已回复')";
				sql += " from T_HELP_Leave q";
				sql += where;
				sql += " order by time desc";
				BaseObject.prototype.query.call(this, sql);
			}
		},
		queryCheck : function() {
			return BaseObject.prototype.queryCheck.call(this);
		},
		_queryShowResult : function(tindexs, operates, fnames, fdescs, change) {
			var tabOjb = $("pageData");
			tabOjb.innerHTML = BaseObject.getQueryTitle() + "<table id='list4' style='font-size:12px;'></table>";
			var el = jQuery("#list4");
			var colNames = ["序号","机构名称","录入人","日期","标题"];
			var colModel = [ {
				name : 'id',
				width : 80,
				sorttype : "int"
			},{
				name : 'jgmc',
				width : 100,
				sorttype : "string"
			},{
				name : 'pname',
				width : 100,
				sorttype : "string"
			},{
				name : 'time',
				width : 100,
				sorttype : "date"
			},{
				name : 'title',
				sorttype : "string"
			}];
			var watch = queryResult.getWatch(false);
			colNames.push(watch.name);
			colModel.push({name:"query",width:50,align:"center"});
			var mydata = this.queryDatas;
			for(var i=0;i<mydata.length;i++){
				mydata[i]["query"] = watch.show(mydata[i]);
			}
			//
			var tableWidth = $("pageData").clientWidth;
			var num = 0,rowsWidth=0;
			for(var i=0;i<colModel.length;i++){
				if(colModel[i].width){
					rowsWidth += colModel[i].width;
				}else{
					num++;
				}
			}
			
			var rest = tableWidth - rowsWidth - 20;
			if(num!=0 && rest > 0){
				var avg = rest/num;
				for(var i=0;i<colModel.length;i++){
					colModel[i].width = colModel[i].width || avg;
				}
			}
			//
			el.jqGrid(
					{
						datatype : "local",
						height : 280,
						colNames :colNames,
						colModel : colModel
					});
			
			for ( var i = 0; i <= mydata.length; i++)
				el.jqGrid('addRowData', i + 1, mydata[i]);
			
			
		},
		queryShowResult : function(xmldoc) {
			var tindexs = [ 2, 3, 4, 5, 8 ];
			var fnames = this.metadata.columns.slice(0);
			fnames.push("state");
			var fdescs = this.metadata.comments.slice(0);
			fdescs.push("状态");
			var operates = [ queryResult.getWatch(true) ];
			var operate = baseInfo.operate || "";
			if (operate.indexOf($operates.modify.code) != -1
					|| operate.indexOf($operates.reply.code) != -1) {
				operates.push(queryResult.getDeal(true));
			}
			if (operate.indexOf($operates.saveas.code) != -1) {
				operates.push({
					name : $operates.saveas.desc,
					show : function(queryData) {
						var param = "faq.jsp?id=" + queryData.id + "&page=4";
						var img = {
							src : $operates.getImage("deal", true),
							click : 'openCenter("' + param + '")',
							alt : $operates.saveas.desc
						};
						return queryResult.toImg(img);
					}
				});
			}
			var func = BaseObject.prototype.queryShowResult;
			func.call(this, xmldoc, tindexs, operates, fnames, fdescs);
		},
		watchInit : function() {
			this.watchGetData();
			if (this.data) {
				this.pname.disabled = true;
				this.setData(this.data);
				$("tdTitle").innerHTML = this.data.title;
				$("tdContent").innerHTML = this.data.content;
				if (this.data.reply) {
					$("trReply").style.display = "inline";
					var history = $("reply_content_");
					history.style.display = "inline";
					history.innerHTML = getContent(this.data.reply);
				}
				var html = UDload.getAttachHtmls(this.data.apath);
				$("tdApath").innerHTML = html || "没有上传附件";
			} else {
				window.close();
				alert($prompts.error.loadinfo);
			}
		},
		dealInit : function() {
			this.watchInit();
			this.replyInit();
		},
		modifyInit : function() {
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
						_this.modify(true, url, ids);
					};
					UDload.upload();
				} else {
					var ids = this.metadata.modifyids;
					var params = this.getData(ids);
					if (UDload.result) {
						params.apath = UDload.result.join(",");
					}
					BaseObject.prototype.modify.call(this, params);
				}
			}
		},
		modifyCheck : function() {
			return true;
		},
		replyInit : function() {
			$("trReply").style.display = "inline";
			if (this.data.reply) {
				var history = $("reply_content_");
				history.style.display = "inline";
				history.innerHTML = getContent(this.data.reply);
			}
			if (baseInfo.appid == "1001") {
				var current = $("reply_content");
				current.style.display = "inline";
				var name = this.metadata.objName;
				var id = this.id.value;
				this.btnReply = $("btnReply");
				this.btnReply.attachEvent("onclick", function() {
					var params = {
						jgid : baseInfo.jgid,
						pname : baseInfo.pname,
						time : baseInfo.time,
						content : $("reply_content").value,
						qid : id
					};
					reply.add(false, params, name);
				});
				this.btnReply.style.display = "inline";
			}
		}
	};
	Leave.prototype = $.extend(BaseObject.prototype);
	Leave.prototype = $.extend(Leave.prototype, prototype);
})();

var leave = new Leave();
leave.metadata = {
	name : "leave",
	desc : "留言",
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
	objName : "leave",
	fileName : "leave",
	request : {
		module : "help",
		node : "leave"
	},
	modifyids : [ "id", "title", "content" ]
};
$events.insert(window, "onload", function() {
	leave.init();
});
$events.init();

function getContent_(content) {
	if (content) {
		return "<div style='text-indent:24px;width:100%;display:block;'>"
				+ content + "</div>";
	}
	return content;
}

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
		str = strs.join("");
	} else {
		str = getContent_(infos.content);
		var desc = "[" + (infos.jgmc ? infos.jgmc + " " : "") + infos.pname
				+ " " + infos.time + "]";
		str += getContent_(desc);
		str = "<div style='margin:0 0 10;'>" + str + "</div>";
	}
	return str;
}