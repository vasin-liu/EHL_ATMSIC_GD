(function() {
	// 两个字符缩进
	var indent = "&nbsp;&nbsp;&nbsp;&nbsp;";
	// 内容格式器
	var ContentFormatter = cf = {
		indent : function(info) {
			return indent + info;
		},
		record : function(jgmc, pname, time) {
			var info = [];
			for ( var i = 0; i < arguments.length; i++) {
				arguments[i] && info.push(arguments[i]);
			}
			return "[" + info.join(indent) + "]";
		},
		textarea : function(info, clzname) {
			clzname = clzname || "";
			clzname = " " + clzname;
			var attrs = " class='" + clzname + " copy_text'";
			attrs += " style='height:auto;' readonly ";
			var html = "<textarea " + attrs + ">" + info + "</textarea>";
			return html;
		}
	};
	window.ContentFormatter = ContentFormatter;
	function eleRecord(info, jgmc, pname, time, phone, clzname) {
		var div = document.createElement("div");
		div.className = "record";
		div.innerHTML = cf.textarea(info, clzname);
		div.appendChild(eleUl(jgmc, pname, time, phone));
		return div;
	}
//	function eleUl(jgmc, pname, time, phone) {
//		var msg = [];
//		var descs = [ "报送单位", "报送人", "报送时间", "值班电话" ];
//		for ( var i = 0; i < arguments.length; i++) {
//			if (arguments[i]) {
//				msg.push(descs[i] + "：" + arguments[i]);
//			}
//		}
//		return indent + msg.join(indent);
//	}
	
	function eleUl(jgmc, pname, time, phone) {
		var ul = document.createElement("ul");
		ul.className="line";
		var descs = [ "报送单位", "报送人", "报送时间", "值班电话" ];
		for ( var i = 0; i < arguments.length; i++) {
			if (arguments[i]) {
				var li = document.createElement("li");
				if(i == 0){
					li.className = "first";
				}
				li.innerHTML = (descs[i] + "：<span>" + arguments[i] + "</span>");
				ul.appendChild(li);
			}
		}
		return ul;
	}
	
	var RecordFormatter = rf = {
		singletext : function(remind, renewals) {
			var html = [];
			var piece = cf.indent(remind.remindinfo) + "\n";
			piece += cf.indent(cf.record(remind.departmentname,
					remind.username, remind.remindtime));
			html.push(piece);
			for ( var i = 0; i < renewals.length; i++) {
				piece = cf.indent(renewals[i].remindinfo) + "\n";
				piece += cf.indent(cf.record(renewals[i].reminddepartment,
						renewals[i].remindperson, renewals[i].reminddate));
				html.push(piece);
			}
			return cf.textarea(html.join("\n\n"));
		},
		table : function(remind, renewals) {
			var data = formTable(remind, renewals);
			DOM.Table.matedata.showThead = false;
			var table = DOM.Table.simpleTable(data);
			return table;
		}
	};
	window.RecordFormatter = RecordFormatter;

	function PoliceRemind() {
	}
	PoliceRemind.prototype = {
		init : function() {
			var _this = this;
			this.get(function(data) {
				_this.parseData(data);
				_this.setEditInfo();
				_this.setTabsInfo();
				_this.setReadOnly();
				_this.setOperate();
			});
		},
		get : function(callback) {
			var url = "dynamicinfo.policeRemind.getCorrectInfo.d";
			$.getJSON(url, baseInfo.params, callback);
		},
		parseData : function(data) {
			this.reminds = data.remind;
			this.newestRemind = this.reminds[this.reminds.length - 1];
			this.initRemind = this.reminds[0];
			this.xbs = data.xb;
			this.alarm = data.alarm;
			this.phone = data.phone;
		},
		setEditInfo : function() {
			var newest = this.newestRemind;
			$("#departmentname").val(newest.departmentname);
			$("#departmentjgid").val(newest.departmentjgid);
			$("#remindtime").val(newest.remindtime);
			$("#remindinfo").val(newest.remindinfo);
		},
		setTabsInfo : function() {
			$("#tdRecord").outerWidth($("#tblForm").innerWidth());
			this.tabs = $("#tabsRecord").tabs();
			if (this.alarm && this.alarm.eventtype != "001024") {
				var html = this.reportTable(this.switchTdata()).outerHTML;
				this.tabsAdd("report", "填报记录", html);
			}
			this.tabsAdd("history", "发布记录", this.htmlHistory());
		},
		switchTdata : function() {
			var map = {
				"001002" : this.crowdTdata(),
				"001023" : this.buildTdata()
			};
			return map[this.alarm.eventtype];
		},
		crowdTdata : function(){
			return {
				caption : "交通拥堵信息表",
				titles : {
					desc : "交通拥堵发生的时间、地点、原因、目前的路面状况、预计何时恢复正常交通",
					action:"交警部门采取交通管制措施",
					renewal:"该路段每半小时的路面状况及交警交通安全提示",
					over : "恢复正常交通的时间"
				}
			};
		},
		buildTdata : function(){
			return {
				caption : "道路施工信息表",
				titles : {
					desc : "道路施工发生的时间、地点、最近的路面状况、预计何时施工完成",
					action:"交警部门采取交通管制措施",
					renewal:"该路段近期路况提示的后续更新",
					over : "施工完成的日期"
				}
			};
		},
		reportTable : function(tdata) {
			var table = {
				caption : tdata.caption,
				head : {
					name : "",
					value : ""
				},
				data : []
			};
			var overTime = baseInfo.crowdAutoOver;
			var overState = "570002";
			if(this.alarm.eventtype=="001023"){
				overTime = baseInfo.buildAutoOver;
				overState = "570006";
			}
			var remind = this.initRemind, renewals = this.xbs;
			table.data.push({
				name : cf.textarea(tdata.titles.desc),
				value : this.recordRemind(remind)
			});
			table.data.push({
				name : cf.textarea(tdata.titles.action),
				value : cf.textarea(this.alarm.gzcs||"","copy_text_indent")
			});
			
			var elements = "&nbsp;";
			if(renewals && renewals.length>1){
				var length = renewals.length;
				//在overTime之前，警情信息没有插入结束的拥堵交警提示信息，在overTime之后，系统默认插入了结束的拥堵交警提示信息
				//那么之后的信息，隐藏默认插入的结束语句
				this.alarm.eventstate == overState && this.alarm.caseendtime >= overTime && length--;
				length > 1 && (elements = []);
				for ( var i = 1; i < length; i++) {
					var html = this.recordRenewal(renewals[i]);
					if(i != length - 1){
						html.className = html.className + " bottomborder";
					}
					elements.push(html);
				}
			}
			table.data.push({
				name : cf.textarea(tdata.titles.renewal),
				value : elements
			});
			
			var record = "&nbsp;";
			if (this.alarm.eventstate == overState) {
				if(this.alarm.caseendtime >= overTime && renewals){
					var last = renewals[renewals.length - 1];
					record = this.recordOver(last.reminddate,last.remindperson);
				}else{
					record = this.recordOver(this.alarm.caseendtime,this.alarm.reportperson);
				}
			}
			table.data.push({
				name : cf.textarea(tdata.titles.over),
				value : record
			});
			DOM.Table.matedata.showThead = false;
			return DOM.Table.simpleTable(table);
		},
		recordRemind : function(remind){
			return  eleRecord(remind.remindinfo, remind.departmentname,
					remind.username, remind.remindtime, this.phone);
		},
		recordRenewal : function(renewal){
			return eleRecord(renewal.remindinfo,
					renewal.reminddepartment, renewal.remindperson,
					renewal.reminddate, this.phone);
		},
		recordOver : function(etime, pname){
			pname = pname ? "报送人：" + pname : "";
			var info = etime + indent + pname;
			return cf.textarea(info,"copy_text_indent");
		},
		tabsAdd : function(id, label, html) {
			var div = "id='" + id + "' style='width:100%;padding-bottom:10px;'";
			div = "<div " + div + ">" + html + "</div>";
			this.tabs.append(div);
			this.tabs.tabs("add", "#" + id, label);
		},
		htmlHistory : function(infos) {
			var _this = this;
			infos = infos || this.reminds;
			var html = [];
			$.each(infos, function(index, value) {
				html.push(ContentFormatter.indent(_this.htmlRemind(value)));
			});
			return ContentFormatter.textarea(html.join("\n\n"));
		},
		htmlRemind : function(remind) {
			var format = {
				"-" : "年",
				"-" : "月",
				" " : "日",
				":" : "时",
				" " : "分"
			};
			var time = remind.remindtime + " ";
			$.each(format, function(index, value) {
				time = time.replace(index, value);
			});
			var info = time + "消息：" + remind.remindinfo;
			info += " －【" + remind.departmentname + "】";
			return info;
		},
		insert : function() {
			var url = "dynamicinfo.policeRemind.insert.d";
			var data = baseInfo.params;
			var param = {
				departmentid : baseInfo.jgid,
				departmentname : baseInfo.jgmc,
				remindinfo : $("#remindinfo").val(),
				source : data.source,
				pid : data.pid
			};
			if (data.alarmid) {
				param.alarmid = data.alarmid;
			}
			if (baseInfo.pname) {
				param.username = baseInfo.pname;
			}
			$.post(url, param, function(xmlDoc) {
				var result = xmlDoc.text;
				var descs = {
					"true" : "成功",
					"false" : "失败"
				};
				alert("修改交警提示信息" + (descs[result] || "出现未知错误") + "！");
				if (result == "true") {
					window.close();
					window.dialogArguments.callPageCtrl();
				}
			});
		},
		setReadOnly : function() {
			if (baseInfo.pageType == "3") {
				$("#remindinfo").removeClass("copy_text");
				$("#remindinfo").attr("readonly", false);
			}else if(baseInfo.pageType == "2"){
				$("#trJgTime,#trInfo").css("display","none");
			}
		},
		setOperate : function() {
			var _this = this;
			if (baseInfo.pageType == "3") {
				$("#btnModify").css({
					display : "inline"
				});
				$("#btnModify").bind("click", function(event) {
					_this.insert();
				});
			}
		}
	};
	window.onload = function() {
		var remind = new PoliceRemind();
		remind.init();
	};
})();
