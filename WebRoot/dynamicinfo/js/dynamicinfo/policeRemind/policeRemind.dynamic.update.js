/**
 * <pre>
 * 警情信息
 * 1.提示组成部分： 
 * 1.1.滚动信息 
 * 显示当前状态模板的最新信息
 * 1.2.发布信息 
 * 1.3.更多信息 
 * 1.4.详细信息
 * </pre>
 */
(function() {
	window.$policeRemind = $policeRemind = {};
	function RollInfo() {
	}
	RollInfo.prototype = {
		request : function(data,callBack) {
			var url = "dynamicinfo.policeRemind.getRollInfo.d";
			var _this = this;
			var request = {
				method : "post",
				parameters : data || _this.params,
				onComplete : function(xmlHttp) {
					var xmlDoc = xmlHttp.responseXML;
					_this.rows = xmlDoc.getElementsByTagName("remind");
					_this.datas = BaseObject.getDatas(_this.rows);
					callBack(xmlHttp, _this);
				}
			};
			new Ajax.Request(url, request);
		},
		htmlOne : function(index, data, click) {
			var attrs = [ "href='#'" ];
			// A标签设置缩进属性，此处显示不符合常理，用div代替
			// 去掉原属于A标签的样式，同时添加鼠标移入和移出属性
			// attrs.push("class='a01'");
			var style = [ "word-break:break-all" ];
			// style.push("text-indent:24px");
			style.push("cursor:hand");
			attrs.push("style='" + style.join(";") + "'");
			attrs.push("onclick='" + (click || "") + "'");
			attrs.push("onmouseover='this.style.color=\"#666666\"'");
			attrs.push("onmouseout='this.style.color=\"black\"'");
			var info = index + "、";
//			var time = data.remindtime.replace(/-/g,"/");
//			time = new Date(Date.parse(time)).pattern("M月d日H时m分");
//			info += time + "消息：";
			info += data.remindinfo;
			var inscribe = [ data.departmentname.replace("市公安局交通警察", "") ];
			inscribe.push(data.remindtime);
			inscribe.push(data.zbdh1);
			info += "　－【" + inscribe.join("&nbsp;&nbsp;") + "】";
			var link = "<div " + attrs.join(" ") + ">" + info + "</div>";
			return link;
		},
		getClick : function(data) {
			var clicks = [];
			clicks.push("stopSynchDynamicInfo()");
			clicks.push("window.isMoreInfoClick=false");
			var detail = "$policeRemind.detailInfo.show";
			detail += "(" + jQuery.toJSON(data) + ")";
			clicks.push(detail);
			return clicks.join(";");
		},
		setHtml : function(click) {
			var _this = this;
			var htmlOne = typeof click == "function" ? function(index, data) {
				return _this.htmlOne(index, data, click(data));
			} : function(index, data) {
				return _this.htmlOne(index, data);
			};
			var infos = [];
			for ( var i = 0; i < this.datas.length; i++) {
				infos.push(htmlOne((i + 1), this.datas[i]));
			}
			this.html = infos.join("<br>");
		},
		nodata : "目前暂无提示信息！"
	};
	$policeRemind.rollInfo = new RollInfo();

	function DetailInfo() {
	}
	DetailInfo.prototype = {
		show : function(data) {
			this.param = data;
			this.dialog = art.dialog({
				id : "policeremind_detailinfo",
				width : "450",
				left : "180",
				resize : false,
				title : "警情信息提示",
				close : function() {
					CloseLightOver();
					synchDynamicInfo();
					return true;
				},
				init : function() {
					stopSynchDynamicInfo();
					AddLightOver();
				}
			});
			var _this = this;
			this.request(function() {
				_this.setHtml();
				_this.dialog.content(_this.html);
			});
		},
		request : function(callback) {
			if (this.param.source == "1") {
				this.data = this.param;
				callback();
			} else if (this.param.source == "2") {
				var url = "dynamicinfo.policeRemind.getDetailInfo.d";
				var _this = this;
				var request = {
					method : "post",
					parameters : this.param,
					onComplete : function(xmlHttp) {
						var xmlDoc = xmlHttp.responseXML;
						_this.row = xmlDoc.getElementsByTagName("remind")[0];
						_this.data = BaseObject.getData(_this.row);
						callback();
					}
				};
				new Ajax.Request(url, request);
			}
		},
		htmlOne : function(info, time) {
			// div使原填报格式换行失效（使用textare则不会），不过介于换行会使文字显示极为难看，此处仍使用div
			// XX时间报，最好浮动在角落，而且时间格式也有待调整
			var inscribe = "";
			if(time){
				time = time.replace(/-/g, "/");
				time = new Date(Date.parse(time));
				time = time.pattern("M月d日H:m") + "报";
				inscribe = "<div style='text-align:right;'>" + time + "</div>";
			}
			var html = " class='copy_text copy_text_indent'";
			html += " style='margin:10 0;'";+ inscribe;
			html = "<div "+html+">" + info + inscribe + "</div>";
			return html;
		},
		setHtml : function() {
			//div使原填报格式换行失效（使用textare则不会），不过介于换行会使文字显示极为难看，此处仍使用div
			//XX时间报，最好浮动在角落，而且时间格式也有待调整
			var div = this.htmlOne(this.data.remindinfo);
			var xbs = this.data.crowdremind;
			if (xbs && xbs.length > 1) {
				xbs = xbs instanceof Array ? xbs : [ xbs ];
				for ( var i = 1; i < xbs.length; i++) {
					div += this.htmlOne(xbs[i].remindinfo,xbs[i].reminddate);
				}
			}
			this.html = div;
		}
	};
	$policeRemind.detailInfo = new DetailInfo();

	function MoreInfo() {
	}

	var moreInfoSql = function(isToday) {
		var fromAlarmRn = "select t.*"
				+ ",row_number() over(partition by decode(t.source,'1',t.pid,'2',t.alarmid) order by t.remindtime desc) rn";
		fromAlarmRn += " from t_oa_police_remind t";
		var fromAlarm = "select remindid id,to_char(remindtime,'yyyy\"年\"mm\"月\"dd\"日\" hh24:mi') time"
				+ ",departmentid jgid,departmentname jgmc,remindinfo info,username pname"
				+ ",to_char(remindtime,'yyyy\"年\"mm\"月\"dd\"日\"') time1"
				+ ",to_char(sysdate,'yyyy\"年\"mm\"月\"dd\"日\"')"
				+ ",row_number() over(order by t.remindtime desc) rn"
				+ ",t.source,t.alarmid,t.pid,T.PUBLISHTYPE,"  //新增发布类型查询
                + "(select taa.eventtype from t_attemper_alarm taa where taa.alarmid=t.alarmid) eventtype "; //新增信息类型
		fromAlarm += " from (" + fromAlarmRn + ") t";
		fromAlarm += " where trunc(t.remindtime,'dd')" + (isToday ? "=" : "<")
				+ "trunc(sysdate,'dd') and t.rn = 1";
		return fromAlarm;
	};
	MoreInfo.prototype = {
		show : function() {
			this.dialog = art.dialog({
				id : "policeremind_listinfo",
				width : "900",
				height : "450",
				resize : false,
				title : "交警提示信息列表",
				// content:this.getSql().replace(/</g,"&lt;"),
				close : function() {
					CloseLightOver();
                    //Modified by Liuwx 2012-4-13 10:34:19
                    //刷新交警提示信息
                    policeRemind();
                    //Modification finished
					synchDynamicInfo();
					return true;
				},
				init : function() {
					stopSynchDynamicInfo();
					AddLightOver();
					window.currPage = 1;
				}
			});
			this.request();
		},
		getSql : function() {
			var sql = moreInfoSql(true);
			sql += " union all ";
			sql += moreInfoSql(false);
			return sql;
		},
		// 分页效果，翻页后最好显示出第一条数据
		request : function() {
			var id = "policeremind_listinfo_data";
			var cntn = this.dialog.content();
			cntn.id = id;
			cntn.style.cssText += ";width:100%;height:100%;overflow:auto";
			var sql = convertSql(this.getSql());
			PageCtrl.initPage(id, "pageData", "pageNav", sql,
					"showAllPoliceRemindLst", "9");
		}
	};
	$policeRemind.moreInfo = new MoreInfo();

    //Modified by Liuwx at 2012-4-11 14:22:47
    //更新交警提示信息发布状态,包括两种：1、互联网发布；2、公安网发布；
    function updatePublishState() {
    }

    updatePublishState.prototype = {
        update : function(remindId) {
            var internetPublishId = remindId + "_2"; //互联网发布checkbox ID
            var internalPublishId = remindId + "_1"; //公安网发布checkbox ID
            var internetPublish = $(internetPublishId).checked?$(internetPublishId).value:"";
            var internalPublish = $(internalPublishId).checked?$(internalPublishId).value:"";
            var publishType = "";
            if(internalPublish == ""){
                publishType = internetPublish;
            }else if(internetPublish == ""){
                publishType = internalPublish;
            }else if(internalPublish != "" && internetPublish != ""){
                publishType = internalPublish + "," + internetPublish;
            }else{
                publishType = "";
            }
            data = "remindId=" + remindId + "&publishType="+publishType;
            this.request(data);
        },
        request : function(data,callBack) {
            var url = "dynamicinfo.policeRemind.updatePublishState.d";
            var _this = this;
            var request = {
                method : "post",
                parameters : data || _this.params,
                onComplete : function(xmlHttp) {
                    var xmlDoc = xmlHttp.responseXML;
                }
            };
            new Ajax.Request(url, request);
        }
    };
    $policeRemind.updatePublishState = new updatePublishState();
    //Modification finished

})();
