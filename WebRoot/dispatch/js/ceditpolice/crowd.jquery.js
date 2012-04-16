(function($) {
	
	$(function() {
		baseInfo.pageType = baseInfo.params.insrtOrUpdt;
		baseInfo.pageType == "1" && $("#ROADNAME").focus();
//		defaults();
	});
	
	var indent = "&nbsp;&nbsp;&nbsp;&nbsp;";
	function getInscribe(jgmc, pname, time, phone) {
		var msg = [];
		for ( var i = 0; i < arguments.length; i++) {
			arguments[i] && msg.push(arguments[i]);
		}
		return "[" + msg.join(indent) + "]";
	}

	window.Publish = function(){
		return Publish.prototype.init();
	};
	
	//新增时，显示发布框，框内根据填写值自动插入
	//填报单位更新时，显示发布框，框内根据填写值自动插入，显示发布历史记录
	//总队更新时，显示发布框，框内显示填报单位填写尚未发布的信息，显示发布历史记录，显示发布类型，显示确定按钮
	Publish.prototype = {
		init : function(){
			$("#areaPublish").attr("cols", baseInfo.pageType ? "72" : "67");
//			var showTemplate = function() {
//				var msg = "23:13清连高速媒合西（K1+2米至K3+4米）清远往连州方向，" 
//					+ "因车流量大造成交通拥堵。" 
//					+ "目前交警正在该路段进行交通疏导，在小河西路口分流。"
//					+ "预计在23:23恢复正常交通。" 
//					+ "交警提示：请各位车主听从指挥，耐心等候，勿随意更换车道",
//				copyMsg = "<textarea class='copy_text'>"+msg+"</textarea>",
//				id = "div_publishtemplate", dialog = "<div id='" + id + "' title='模板实例'>" + copyMsg + "</div>";
//				$("#"+id).length == 0 ? $(dialog).dialog({ bgiframe : false,resizable:false,width:"400" }).css("overflow","visible")
//					.parent().css({ "font-size" : "12px" }):$("#"+id).dialog("open");
//			};
//			$("#a_publishtemplate").click(showTemplate);
			this.ptypes = {"1":"公安网","2":"互联网"};
			this.states = {"1":"未处理","2":"已处理"};
			if(baseInfo.pageType){
				this.showHistory({
					active : ((baseInfo.jgid == baseInfo.data.crowd.jgid && baseInfo.pageType == "1") ? false : 0),
					collapsible : baseInfo.pageType == "1"
				});
				if(baseInfo.pageType == "1"){
					if(baseInfo.jglx == "0"){
						this.showPublishType().showRelieveCrowd(baseInfo.data.crowd.state == "570002");
						$("#ul_ptype,#ck_relievecrowd_1").each(function(index,el){
							index != 0 && $(this).css("margin-left","30");
						});
						baseInfo.data.accdept.state == "1" && this.showCurrent();
						$("#saveData_Accident").css("display", "inline").val(" 确 定 ")
						.attr("onclick", "").click($.proxy(this, "publish"));
					} else if(baseInfo.jgid != baseInfo.data.crowd.jgid){
						$("#areaPublish,#fontPublish").css("display","none");
					}
				}else if(baseInfo.pageType == "2"){
					$("#areaPublish,#fontPublish").css("display","none");
					if(baseInfo.jglx == "0" && baseInfo.data.accdept.state == "1"){
						this.showCurrent();
						$("#areaPublish,#fontPublish").css("display","inline").eq(0).attr("disabled",true);
					}
				}
			}
			return this;
		},
		showPublishType : function(){
			var data = $.extend({},this.ptypes);
			$.each(data, function(index, value) {
				data[index] = value + "发布";
			});
			$.ehl.widget.list({
				id : "ptype",
				data : data,
				parent : "#tdPublishValue"
			}).css({"display":"inline","white-space":"nowrap"});
			$("#ck_ptype_1").attr("checked", true);
			return this;
		},
		showRelieveCrowd : function(disabled){
			$.ehl.widget.item({
				id : "relievecrowd",
				value : "1",
				html : "解除拥堵",
				parent : "#tdPublishValue"
			}).css({"white-space":"nowrap"})
			  .eq(0).attr("disabled",disabled).attr("checked",disabled);
			!disabled && $("#ck_relievecrowd_1").click(function() {
				!window.$crowdJoin && (window.$crowdJoin = CrowdJoin());
				var publish = $("#areaPublish");
				this.checked && publish.data("ori", publish.val());
				publish.val(this.checked ? $crowdJoin.toString() : publish.data("ori"));
			});
			return this;
		},
		showCurrent : function(){
			var reminds = baseInfo.data.reminds.slice();
			if(reminds.length > 0){
				var remind = reminds.pop();
				$("#areaPublish").val(remind.remindinfo);
				this.type(remind.publishtype);
			}
			return this;
		},
		showHistory : function(options){
			var reminds = baseInfo.data.reminds.slice();
			baseInfo.data.accdept.state == "1" && baseInfo.jglx == "0" && reminds.pop();
			if(reminds.length > 0){
				var txts = [];
				for ( var i = 0; i < reminds.length; i++) {
					//只显示已处理过的信息
//					if(reminds[i].state != "1" || reminds[i].publishtype){
						txts.push(this.txtOne(reminds[i]));
//					}
				}
				var attrs = [ "class='copy_text'" ];
				attrs.push("readonly");
				attrs.push("col='67'");
				attrs.push("style='display:block;'");
				var html = "<textarea " + attrs.join(" ") + " >" + txts.join("\n\n") + "</textarea>";
				var item = {
					head : "<h3><a href='#'>历史记录</a></h3>",
					content : "<div style='overflow:visible;'>" + html + "</div>"
				};
				var accordion = "<div id='divHistoryAccordion' style='font-size:12px;'>" + item.head + item.content + "</div>";
				txts.length > 0 && $(accordion).prependTo("#tdPublishValue").accordion($.extend({animated:false,header:"h3"},options));
				$("#divHistoryAccordion,#areaPublish").each(function(index, value){
					index != 0 && $(this).css("margin-top","10");
				});
			}
			return this;
		},
		txtOne : function(remind){
			var indent = "&nbsp;&nbsp;&nbsp;&nbsp;";
			var info = [];
			var content = indent + remind.remindinfo;
			info.push(content);
			info.push("\n"+getInscribe(remind.departmentname,remind.username,remind.remindtime));
			var state = this.states[remind.state];state && (state = indent+state);
			info.push(state);
			var ptype = remind.publishtype;
			var ptypeMsg = "不发布";
			if(ptype){
				var ptypes = this.ptypes;
				ptype = $.map(ptype.split(","),function(value){ return ptypes[value]; }).join("、");
				ptypeMsg = "发布到" + ptype;
			}
			info.push(indent + ptypeMsg);
			return info.join("");
		},
		type : function(value){
			var sget = value != undefined ? function(el) {
				value.indexOf(el.value) != -1 && (el.checked = true);
			} : function(el, ptypes) {
				el.checked = true && ptypes.push(el);
			};
			var ptypes = [];
			$("input[name='ck_ptype']").each(function() { sget(this, ptypes); });
			return value == undefined ? ptypes.join(",") : this;
		},
		check : function(){
			var area = $("#areaPublish");
			if(!area.val()){
				alert("请输入发布内容！");
				area.focus();
				return false;
			}
			return true;
		},
		publish : function() {
			if (this.check()) {
				var ptype = [];
				$("input[name='ck_ptype']:checked").each(function() { ptype.push(this.value); });
				ptype = ptype.join(",");
				
				if(baseInfo.data.crowd.state == "570001" && $("#ck_relievecrowd_1").attr("checked")){
					var url = "crowd.crowdmanage.deleteCrowdInfo.d";
					var data = {
						"alarmid":baseInfo.data.crowd.alarmid,
						"crowdremind.remindinfo":"交通拥堵已清除，路面恢复正常交通。",
						"policeremind.remindinfo":$("#areaPublish").val(),
						"publishtype":ptype
					};
					$.post(url,data,function(xmlDoc){
						var code = xmlDoc.text;
						var results = {
							"true" : "成功",
							"false" : "失败"
						};
						var result = results[code];
						alert(result ? "交警提示信息发布" + result + "！" : "网络异常！");
						result && window.close();
//						callback && callback(this,result);
					});
					return;
				}
				var reminds = baseInfo.data.reminds;
				var data = $.extend({},reminds[reminds.length - 1]);
				//将remindid设置成pid，发布最初的信息，更新其发布状态
				data.remindid = data.pid;
				//传入接收单位编号和状态，未签收则进行签收
				data["accdept.id"] = baseInfo.data.accdept.id;
				var state = baseInfo.data.accdept.state;
				data["accdept.state"] = state;
				data.state = state;//设置为大队上报交警提示的状态或者签收状态皆可
				data.remindinfo = $("#areaPublish").val();
				//如果尚未处理，则只更新原交警提示的发布状态、内容及处理状态；
				//否则新增一条交警提示信息，同时更新原交警提示的发布状态。
				//然后，签收该信息的提醒
				if(state != "1" ){
					data.departmentid = baseInfo.jgid;
					data.departmentname = baseInfo.jgmc;
					data.username = baseInfo.pname;
				}
				data.publishtype = ptype;
				var url = "dynamicinfo.policeRemind.publish.d";
				$.post(url, data, function(result) {
					alert("交警提示信息发布" + (result ? "成功" : "失败") + "！");
					result && window.close();
//					callback && callback(this,result);
				});
			}
		}
	};
	
	/**
	 * <pre>
	 * 1.在所有异步请求后，进行初始化。
	 * 1.1.加载拥堵信息
	 * 1.2.加载道路名称
	 * 1.3.加载道路方向
	 * 在道路方向加载完成后，进行初始化，并且不受其onchange事件影响
	 * 道路等级改变，异步加载道路名称，导致拼接时无法设置道路名称
	 * 2.获取道路方向
	 * </pre>
	 */
	window.CrowdJoin = function(options){
		return CrowdJoin.prototype.setAllField(options);
	};
	CrowdJoin.prototype = {
		/**
		 * 1.获取拼接所需元素，并按元素的name属性将其值保存在对象中
		 * 1.1.文本框、文本域可以直接进行设置，但是复选框、单选框需要设置的是其选中的值（多项顿号分隔）
		 * 2.为拼接所需元素绑定value改变事件，当元素的值改变时，将其值重新设置在对象中
		 * 2.1.并没有直接的值改变事件，只能通过propertychange事件触发，并在函数中进行判断是否改变了值
		 * 2.2.在页面逻辑中，会删除一些元素，在新增一些元素，导致绑定失效，需要重新绑定
		 * 2.3.一些元素是由回调函数异步请求生成的，
		 * 2.4.道路方向的触发
		 * 2.5.由A事件导致B元素的新值或者替换，此时需要在B元素生成后，手动调用valPublish函数，同时为该元素添加值改变触发事件
		 * 3.根据对象中保存的各个拼接所需值，进行格式拼接，并将拼接值设置在发布文本框中
		 */
		init : function(){
			//填报单位上报或者进行更新处理
			if(!baseInfo.pageType || (baseInfo.pageType == "1" && baseInfo.jgid == baseInfo.data.crowd.jgid)){
				//绑定事件
				this.manual.listener();
				var _this = this;
				$("#remindInfoValue").blur(function(){
					$(this).unbind("blur");
					_this.setAllField().valPublish();
					//进行属性值判断、进行手动修改提示
					var valPublish = function(){
						var names = [ "value", "checked", "innerText" ].join(",");
						if(names.indexOf(event.propertyName) != -1){
							 _this.manual.prompt(function(){
								_this.valPublish(event.srcElement);
							});
						}
						return false;
					};
					// 如果是文本框则通过setField方法设值，单选或者复选框通过set[this.name.upperInitial()]方法来设值
					$("#tblCrowd :input:visible:enabled[name]").bind("propertychange", valPublish);
					// 初始化绑定change事件，在变成文本框时，将会失效，而文本框本身具有propertychange事件
					$("#alarmRoad_TrafficCrowd_td :first:enabled").live("change",valPublish);
					$("span[name='direction_desc']").bind("propertychange", valPublish);
					$("#direction_nostandard").bind("propertychange", valPublish);
				});
				return this;
			}
		},
		manual : {
			listener : function(){
				$("#areaPublish").data({
					"manual" : false,
					"history" : []
				}).focus(function() {
					var $this = $(this);
					$this.data("focus", $this.val());
				}).blur(function() {
					var $this = $(this);
					$this.val() != $this.data("focus") && $this.data("manual", true);
				});
				this.showHistory();
			},
			showHistory : function(){
				$("<a href='#aHistoryModify' name='aHistoryModify'>修改记录</a>")
				.css({"display":"block","width":"0px","white-space":"nowrap"})
				.click(function(){
					var msg = $("#areaPublish").data("history").join("\n\n") || "尚未进行修改";
					var cmsg = "<textarea class='copy_text'>"+msg+"</textarea>";
					var id = "divHistoryModify";
					var dialog = "<div id='"+id+"' title='修改记录'>" + cmsg + "</div>";
					if($("#"+id).length == 0){
						$(dialog).dialog({ bgiframe : false,resizable:false,width:"400" })
							.css("overflow","visible").parent().css({ "font-size" : "12px" });
					}else{
						$("#"+id).dialog("open").html(cmsg);
					}
				}).appendTo("#tdPublishDesc");
			},
			prompt : function(callback){
				var msg = "已经手动修改了发布信息内容，此时再修改其他表单项，" +
				"系统自动生成的发布信息内容，将替换您手动输入的信息，是否确认修改？" +
				"如果确认后信息被覆盖又需要查看手动输入的信息，您可以通过\"修改记录\"来查看。";
				var area = $("#areaPublish"),isManual = area.data("manual");
				if(isManual){
					alert(msg);
					area.data("manual",false);
					area.data("history").push("&nbsp;&nbsp;&nbsp;&nbsp;"+area.val());
				}
				callback();
			}
		},
		setAllField : function(options){
			$.extend(this, options);
			var _this = this;
			$("#tblCrowd :input:visible[name!='reason']").each(function(){
				_this.setTextField(this);
			});
			return this.setDirection().setReason();
		},
		setField : function(el) {
			var name = (el.type == "radio" || el.type == "checkbox") ? el.name : "TextField";
			el.name == "direction_desc" && (name = "direction");
			el.name != "ck_relievecrowd" && this["set" + name.upperInitial()](el);
			return this;
		},
		setTextField : function(el) {
			el.name && (this[el.name] = el.value);
			return this;
		},
		setDirection : function(){
			var dir = $("input[name='direction']:checked:visible").val();
			if (dir == "0" || dir == "1") {
				dir = parseInt(dir);
				var dirs = [ "rdForward","rdBack" ];
				this.direction = $("#" + dirs[dir]).text().replace("—>","往") + "方向";
			} else if(dir == "2"){
				this.direction = "双向";
			}
			return this;
		},
		setReason : function(){
			var reasons = [];
			$("input[name='reason']:checked").each(function(){
				reasons.push(this.value == "事故" ? "交通事故" : this.value);
			});
			this.reasons = reasons.join("、");
			return this;
		},
		valPublish : function(el) {
			el && this.setField(el);
			$("#areaPublish").val(this.toString());
			return this;
		},
		getLocation : function(km,m){
			return "K" + (km ? parseInt(km, 10) : "") + "+" + (m ? parseInt(m, 10) : "") + "米";
		},
		getLArea : function() {
			if (this.km || this.m || this.ekm || this.em) {
				var locs = [ this.getLocation(this.km, this.m) ];
				locs.push(this.getLocation(this.ekm, this.em));
				return "（" + locs.join("至") + "）";
			}
			return "";
		},
		getLength : function(){
			if (this.km && this.m && this.ekm && this.em) {
				var areas = [ 1000, 2000, 3000 ], descs = [ "一公里", "二公里", "三公里" ];
				var s = parseInt(this.km, 10) * 1000 + parseInt(this.m, 10);
				var e = parseInt(this.ekm, 10) * 1000 + parseInt(this.em, 10);
				var length = Math.abs(s - e);
				for ( var i = 0; i < areas.length; i++) {
					if (length < areas[i]) {
						return i == 0 ? descs[i] + "以下" : descs[i - 1].replace("公里", "") + "至" + descs[i];
					} else if (length == areas[i]) {
						return descs[i];
					} else {
						if (i == areas.length - 1) {
							return descs[i] + "以上";
						}
					}
				}
			}
			return "";
		},
		getDuration : function() {
			if (this.happenTime) {
				var s = this.happenTime.dateParse().getTime();
				var e = (baseInfo.syntime || new Date()).getTime();
				var differ = Math.abs(e - s) / 1000 / 60;
				var values = [ parseInt(differ / 60)+"小时", parseInt(differ % 60)+"分钟" ];
				return  $.map(values, function(v) { return /^0/.test(v) ? null : v; }).join("");
			}
			return "";
		},
		getRemark : function(){
			var length = this.getLength(); length && (length = "长度" + length);
			var duration = this.getDuration(); duration && (duration = "已持续" + duration);
			var remark = [ length, duration ];
			remark = $.map(remark, function(v){return v?v:null;}).join("、");
			remark && (remark = "（" + remark + "）");
			return remark;
		},
		toString : function(isRelieve) {
			var fmt = "M月d日H:m";
			var htime = this.happenTime.dateParse().pattern(fmt),otime = "";
			if(this.overTime){
				this.happenTime.substring(0, 10) == this.happenTime.substring(0, 10) && (fmt = fmt.substring(4));
				otime = this.overTime.dateParse().pattern(fmt);
			}
			var dir1 = "", dir2 = "";
			this.direction.indexOf("往") != -1 ? (dir1 = this.direction) : (dir2 = this.direction);
			var info = htime + "，"
				+ this.rname + this.rsname + "路段" 
				+ this.getLArea() + dir1;
			if(isRelieve || $("#ck_relievecrowd_1").attr("checked")){
				info += "的" + dir2 + "交通拥堵已消除，路面恢复正常交通。";
			} else {
				info +=	"，因" + this.reasons
				+ "造成" + dir2 + "交通拥堵" + this.getRemark() + "。"
				+ "目前辖区交警" + this.gzcs 
				+ "预计" + otime + "恢复正常交通。"
				+ "交警提示：" + this.remind;
			}
			return info;
		}
	};
	
	window.CrowdRemind = function() {
		return CrowdRemind.prototype.init();
	};
	CrowdRemind.prototype = {
		init : function() {
			return this;
		},
		toTxt : function(obj) {
			var txt = indent + obj.remindinfo + "\n";
			txt += getInscribe(obj.reminddepartment, obj.remindperson,
					obj.reminddate);
			return txt;
		},
		toTxts : function() {
			var objs = baseInfo.data.crowdreminds;
			var txts = [];
			for ( var i = 0; i < objs.length; i++) {
				txts.push(this.toTxt(objs[i]));
			}
			var txt = txts.join("\n\n");
			return "<textarea class='copy_text'>" + txt + "</textarea>";
		}
	};
	
	function defaults() {
		$("#ROADNAME").val("媒合西");
		$("#KMVALUE").val("1");
		$("#MVALUE").val("2");
		$("#EndKMVALUE").val("3");
		$("#EndMVALUE").val("4");
		 document.getElementsByName("reason")[1].checked = true;
		$("#CaseEndTime").val(new Date().changeTime_(5, -10, 5));
		$("#gzcs").val("在小河西路口分流。");
		$("#remindInfoValue").val("请各位车主听从指挥，耐心等候，勿随意更换车道");
		$("#rdRunStateSlow").click();
		$("#ckGzcsJtsd").click();
	}
	
	
	//目前只在填报界面引入base.js文件，通过缺省赋值，解决引用异常
	window.baseInfo = window.baseInfo || {};
	baseInfo.parseXml = function(xmlDoc) {
		var data = this.data = this.data || {};
		var crowd = xmlDoc.getElementsByTagName("row")[0];
		var crowdAttrs = crowd.childNodes;
		var attrNames = [ "alarmid","rname", "congestiontype","rdiction","rsname", "kmvalue", "endkmvalue", "casehappentime", "caseendtime", "reason", "region",
				"pname", "rtime", "state", "mvalue", "endmvalue", "baosongflg", "jgmc", "crowdtype", "rlevel", "jgid", "retime",
				"ddapprover", "apath", "gzcs" ];
		data.crowd = {};
		for ( var i = 0; i < crowdAttrs.length && i < attrNames.length; i++) {
			data.crowd[attrNames[i]] = crowdAttrs[i].text == "　" ? "" : crowdAttrs[i].text;
		}
		var accdept = xmlDoc.getElementsByTagName("accdept");
		data.accdept = BaseObject.getData(accdept[0]);
		var crowdreminds = xmlDoc.getElementsByTagName("crowdremind");
		data.crowdreminds = BaseObject.getDatas(crowdreminds);
		var reminds = xmlDoc.getElementsByTagName("remind");
		data.reminds = BaseObject.getDatas(reminds);
	};
})(jQuery);
