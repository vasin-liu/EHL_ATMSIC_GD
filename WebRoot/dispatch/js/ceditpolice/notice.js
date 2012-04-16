
var Notice = {
	jgid : "",
	jgbh : "",
	dname : "",
	name : "",
	page : "",
	id : "",
	stype : "",
	setParams : function(obj, eids) {
		if (obj && eids) {
			var el;
			for ( var i = 0; i < eids.length; i++) {
				el = $(eids[i]);
				if (el) {
					obj[eids[i]] = el.value;
				}
			}
		}
	},
	// 初始化全局变量
	initGVar : function() {
		var eids = [ "jgid", "jgbh", "dname", "name", "page", "id", "stype" ];
		Notice.setParams(Notice, eids);
	},
	noticeInfo : {
		jsdws : [],
		jszbys : []
	},
	ctntIds : [],
	accdepts : [],
	checkedRadios : [],
	add : {
		init : function() {
			Notice.initGVar();
//			$("stime").value = new Date().format_(5);
			var show = function(id) {
				var el = $(id);
				if (el) {
					el.style.display = "inline";
				}
			}
			var showAll = function(ids) {
				for ( var i = 0; i < ids.length; i++) {
					show(ids[i]);
				}
			}

			if (Notice.page == "1") {
				showAll([ "save1", "save2", "cancle" ]);
			} else {
				show("close");
				if (Notice.stype == "1") {
					// 发送单位，默认签收其他单位转发回来的信息。
					Flow.updtState({
						id : Notice.id,
						stype : "3",
						state : "2",
						rpname : Notice.name
					});
				}

				var url = "dispatch.notice.getNotice.d"
				url = encodeURI(url);
				var params = {
					id : Notice.id,
					stype : Notice.stype
				};
				new Ajax.Request(
						url,
						{
							method : "post",
							parameters : params,
							onComplete : function(xmlHttp) {
								var gets = function(p, c) {
									return p.getElementsByTagName(c);
								}
								var get = function(p, c) {
									var els = gets(p, c);
									if (els && els.length >= 1) {
										return els[0];
									}
									return null;
								}
								var showAdstate = function(adstate, cindexs, id) {
									var adscolors = [ "red", "green", "black" ];
									var sep = "，";
									var adstateStr = "";
									adstate = adstate.childNodes;
									for ( var i = 0; i < cindexs.length
											&& i < adstate.length; i++) {
										if (adstate[i + 1].text != "") {
											adstateStr += sep
													+ "<span style='color:"
													+ adscolors[cindexs[i]]
													+ ";'>"
													+ adstate[i + 1].text
													+ "</span>";
										}
									}
									if (adstateStr != "") {
										adstateStr = adstateStr
												.substring(sep.length);
									}
									if (id && $(id)) {
										$(id).innerHTML = adstateStr;
									}
									return adstateStr;
								}

								var getContent = function(ctnt, dname, isShow) {
									var adstateStr = "", adstateColor = "";
									var ddstates = gets(ctnt, "adstate");
									if (ddstates != null
											&& ddstates.length == 1
											&& ddstates[0].childNodes[0].text != "null") {
										adstateStr = "&nbsp;&nbsp;接收单位："
												+ showAdstate(ddstates[0], [ 0,
														1 ]);
									} else {
										var snode = ctnt.lastChild;
										if (snode.nodeName == "col") {
											if (snode.text == "1") {
												adstateColor = "color:red";
											}
										}
									}
									ctnt = ctnt.childNodes;
									var content = "";
									content += getContentPart(ctnt[1].text,
											dname, ctnt[3].text, ctnt[4].text);
									content += adstateStr;
									content = "<div style='width:100%;margin:0 0 10;clear: right;"
											+ adstateColor
											+ "'>"
											+ content
											+ "</div>";
									if (isShow) {
										// content +=
										// getRadioButton(null,Notice.accdepts.join(",")+","+ctnt[0].text,true);
										content = getRadio(Notice.accdepts
												.join(",")
												+ "," + ctnt[0].text, true)
												+ content;
									}
									return content;
								}

								function getContentPart(content, dname, pname,
										time, id) {
									// 显示录入的内容
									content = "<span style='text-indent:24px;'>"
											+ content + "<span>" + "<br>";
									// 单位名称、值班人名字、时间
									var str = "";
									if (dname) {
										str += dname + " ";
									}
									str += pname + " " + time + " ";
									// 接收单位
									str = "&nbsp;&nbsp;&nbsp;&nbsp;<span >["
											+ str + "]<span>";
									return content + str;
								}

								var getContents = function(ctnts, dname, isShow) {
									var ctntStr = "";
									for ( var i = 0; i < ctnts.length; i++) {
										ctntStr += getContent(ctnts[i], dname,
												isShow);
									}
									return ctntStr;
								}

								var getAdCtnt = function(adctnt, isShow) {
									var adctntStr = "";
									var ctnts = gets(adctnt, "content");
									if (ctnts != null && ctnts.length >= 1) {
										var ad = adctnt.childNodes;
										var jgmc = ad[2].text;
										Notice.accdepts.length = 0;
										Notice.accdepts.push([ ad[6].text,
												ad[7].text, ad[8].text ]);
										adctntStr += getContents(ctnts, jgmc,
												isShow);
									}
									return adctntStr;
								}
								var getAdCtnts = function(adctnts, isShow) {
									var adctntsStr = "";
									for ( var i = 0; i < adctnts.length; i++) {
										adctntsStr += getAdCtnt(adctnts[i],
												isShow);
									}
									Notice.adcontent = adctntsStr;
									return adctntsStr;
								}

								function getAttachHTML(apath) {
									var attach;
									if (apath) {
										var fnindex = apath.lastIndexOf("/");
										var fname = apath
												.substring(fnindex + 1);
										attach = "<a href='#' style='margin-right:10px;'";
										attach += " title='" + fname + "' ";
										attach += " onclick='openWPS(\""
												+ apath + "\")' >";
										attach += fname + "</a>";
									}
									return attach;
								}
								function getAttachHTMLs(apath) {
									var attachs = "";
									if (apath) {
										apath = apath.replace(/\\/g, "/");
										apath = apath.split(";");
										for ( var i = 0; i < apath.length; i++) {
											attachs += getAttachHTML(apath[i])
													|| "";
										}
									}
									return attachs;
								}
								function setReadOrDis(id, isRo) {
									var el = $(id);
									if (el) {
										if (isRo) {
											el.readOnly = true;
										} else {
											el.disabled = true;
										}
									}
								}
								function setReadOrDiss(ids, isRo) {
									if (ids) {
										for ( var i = 0; i < ids.length; i++) {
											setReadOrDis(ids[i], isRo);
										}
									}
								}

								var showNotice = function(noctnt, adstate) {
									var notice = gets(noctnt, "col");
									$("spdname").value = notice[5].text;
									$("stime").value = notice[7].text;
									$("spname").value = notice[6].text;
									if (adstate) {
										var accdepts = gets(adstate, "col");
										$("jgmc").value = accdepts[0].text;
										$("jgmc").title = accdepts[0].text;
										$("adTr").style.display = "inline";
										showAdstate(adstate, [ 0, 1, 2 ],
												"adTd");
										$("adcTr").style.display = "inline";
									}
									$("acceptDeptImg").style.display = "none";
									$("title").value = notice[1].text;
									$("contentTd").innerText = notice[2].text;
									var apath = notice[3].text;
									var attachs = UDload.getAttachHtmls(apath);
									if (attachs) {
										$("apathDescTd").innerText = "附件名称";
										$("fileRegion").innerHTML = attachs;
									} else {
										$("attachTr").style.display = "none";
									}
									var ctnts = gets(noctnt, "content");
									var ctntHTML = getContents(ctnts,
											notice[5].text);
									if (ctntHTML) {
										$("addContentTr").style.display = "inline";
										$("addContent").innerHTML = ctntHTML;
										Notice.noticeInfo.xb = ctntHTML;
									}
									setReadOrDiss([ "spdname", "stime",
											"spname", "jgmc", "title",
											"content", "addContent" ], true);
									$("print").focus();
									Notice.noticeInfo.id = notice[0].text;
									Notice.noticeInfo.title = notice[1].text;
									Notice.noticeInfo.ncontent = notice[2].text;
									Notice.noticeInfo.fsdw = notice[5].text;
									Notice.noticeInfo.fszby = notice[6].text;
									Notice.noticeInfo.rtime = notice[7].text;
								}

								var xmldoc = xmlHttp.responseXML;
								var noctnts = get(xmldoc, "notice");
								var adstate = get(xmldoc, "adstate");
								showNotice(noctnts, adstate);
								var adctnts, state;
								switch (Notice.stype) {
								case "1":
									state = noctnts.childNodes[8].text;
									var adctnts = gets(xmldoc, "adcontent");
									for ( var i = 0; i < adctnts.length; i++) {
										Notice.noticeInfo.jsdws
												.push(adctnts[i].childNodes[2].text);
										Notice.noticeInfo.jszbys
												.push(adctnts[i].childNodes[3].text);
									}
									adctnts = getAdCtnts(adctnts, false);
									if (adctnts) {
										$("fcontentTr").style.display = "inline";
										$("fcontentTd").innerHTML = adctnts;
										Notice.noticeInfo.acontent = adctnts;
									}
									var jginfo = get(xmldoc, "jgmc");
									if (jginfo) {
										Notice.printJgInfo = jginfo.text;
									}
									if (Notice.page == 3) {
										$("send").style.display = "inline";
									}
									break;
								case "2":
									var adctnts1 = gets(xmldoc, "adcontent");
									adctnts = getAdCtnts(adctnts1);
									if (adctnts) {
										$("fcontentTr").style.display = "inline";
										if (Notice.page == "2") {
											$("fcontentTd").innerHTML = adctnts;
										} else if (Notice.page == "3") {
											$("blqkOldTd").innerHTML = getAdCtnts(
													adctnts1, true);
											$("dispatch").style.display = "inline";
										}
										Notice.noticeInfo.acontent = adctnts;
									} else {
										if (Notice.page == "3") {
											$("fcontentTr").style.display = "inline";
											$("blqkOldTr").style.display = "none";
											$("dispatch").style.display = "inline";
											showSave(1);
										}
									}

									var accdept = gets(adctnts1[0], "col");
									state = accdept[5].text;
									break;
								case "3":
									var adctnts = gets(xmldoc, "adcontent");
									var adctnts1 = getAdCtnts(adctnts);
									$("fcontentTr").style.display = "inline";
									$("fcontentTd").innerHTML = adctnts1;
									Notice.noticeInfo.acontent = adctnts1;
									var dis = get(xmldoc, "dis").childNodes;
									state = dis[5].text;
									break;
								}

								if (Notice.page == "3") {// 未办结显示办结按钮
									if (state == "1") {
										show("signin");
									} else if (state == "2") {
										// show("finish");
									}
								}
							}
						});
			}
		},
		els : {},
		check : function(type) {
			var isOK = true;
			var eids = [ "title", "content", "apath", "spname", "spdcode" ];// ,"adcode"//jgmc:"接收单位",
			var cnames = [ "spdname", "stime", "spname", "title", "content" ];
			var cdescs = [ "发送人单位", "发送时间", "发送人姓名", "通知标题", "通知内容" ];
			if (type == 2) {
				cnames.splice(3, 0, "jgmc");
				cdescs.splice(3, 0, "接收单位");
			}
			var el;
			for ( var i = 0; i < cnames.length; i++) {
				el = $(cnames[i]);
				if (el) {
					if (el.value == "") {
						alert("请输入" + cdescs[i] + "！");
						el.focus();
						return false;
					} else {
						Notice.add.els[cnames[i]] = el.value;
					}
				}
			}

			if (!validateInput()) {
				return;
			}

			return true;
		},
		submit : function(type) {
			var els = Notice.add.els;
			if (els) {
				delete els.jgmc;
				var jgmcId = $("jgmcId").value;
				if (jgmcId && type == 2) {
					jgmcId = jgmcId.replace(/；/g, ",");
					jgmcId = jgmcId.substring(0, jgmcId.length - 1);
					els.rpdcode = jgmcId;
				}
				if (UDload.result) {
					els.apath = UDload.result;
				}
				var odesc = type == 1 ? "保存" : "发送";
				var url = "dispatch.notice.addNotice.d";
				new Ajax.Request(url, {
					method : "post",
					parameters : els,
					onComplete : function(xmlHttp) {
						Notice
								.showResponse(xmlHttp, odesc + "其他重大情况",
										"reload");
					}
				});
			}
		}
	},
	search : {
		init : function() {
			Notice.initGVar();
			window.G_jgID = $("jgid").value;
			var date = new Date();
			// $("dateE").value = date.format_(3);
			// date.setDate("1");
			// $("dateS").value = date.format_(3);
			Notice.search.submit();
		},
		getSql : function() {
			var sfrtime = $("dateS").value;
			var efrtime = $("dateE").value;
			var state = $("state").value;
			var title = $("title").value;
			var stype = $("stype").value;
			var whereStr = "where 1=1";
			var stime = "to_char(no.stime,'yyyy-mm-dd hh24:mi')";
			if (sfrtime != "") {
				whereStr += " and  " + stime + ">= '" + sfrtime + " 00:00'";
			}
			if (efrtime != "") {
				whereStr += " and " + stime + " <= '" + efrtime + " 23:59'";
			}
			if (state != "") {
				whereStr += " and no.state = '" + state + "'";
			}
			if (title != "") {
				whereStr += " and no.title like '%" + title + "%'";
			}
			if (window.G_jgID) {
				whereStr += " and " + Dept.siftChilds(G_jgID, "no.spdcode");
			}
			var sql = "";
			// Modified by Liuwx 2011-07-27
			if (stype == "0") {// 全部
				whereStr += " and no.spdcode='" + Notice.jgid + "'";
				sql = "select no.id,no.title,f_get_dept(no.spdcode),f_get_accdept(no.id,1,2),"
						+ stime
						+ ",no.state,'1'"
						+ " from t_oa_notice no "
						+ whereStr + " ";
				sql += " union ";
				whereStr2 = "where 1=1 and no.id = ad.aid and ad.rpdcode='"
						+ Notice.jgid
						+ "' and (select spdcode from t_oa_notice where id=ad.aid)!=ad.rpdcode"
				if (window.G_jgID) {
					whereStr2 += " and "
							+ Dept.siftChilds(G_jgID, "no.spdcode");
				}
				if (sfrtime != "") {
					whereStr2 += " and  " + stime + ">= '" + sfrtime
							+ " 00:00'";
				}
				if (efrtime != "") {
					whereStr2 += " and " + stime + " <= '" + efrtime
							+ " 23:59'";
				}
				sql += "select no.id,no.title,f_get_dept(no.spdcode),f_get_dept(ad.rpdcode),"
						+ stime
						+ ",ad.state,decode(ad.adid,null,'2','3')"
						+ " from t_oa_notice no, t_oa_acceptdept ad "
						+ whereStr2 + " order by 5 desc";
				sql = "select * from (" + sql + ")";
			} else if (stype == "1") {// 发送方
				// Modification finished
				whereStr += " and no.spdcode='" + Notice.jgid + "'";
				// 0 1 2 3 4 5 6
				sql = "select no.id,no.title,f_get_dept(no.spdcode),f_get_accdept(no.id,1,2),"
						+ stime
						+ ",no.state,'1'"
						+ " from t_oa_notice no "
						+ whereStr + " order by no.id desc";
			} else if (stype == "2") {// 接收方
				whereStr += " and no.id = ad.aid and ad.rpdcode='"
						+ Notice.jgid
						+ "' and (select spdcode from t_oa_notice where id=ad.aid)!=ad.rpdcode"
				// 0 1 2 3 4 5 6
				sql = "select no.id,no.title,f_get_dept(no.spdcode),f_get_dept(ad.rpdcode),"
						+ stime
						+ ",ad.state,decode(ad.adid,null,'2','3')"
						+ " from t_oa_notice no, t_oa_acceptdept ad "
						+ whereStr
						+ " order by no.spdcode,no.id desc,ad.id desc";
				sql = "select * from (" + sql + ") where rownum = 1";
			}
			return sql;
		},
		submit : function() {
			var sql = Notice.search.getSql();
			PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
					"Notice.search.showResultsPage", "10");
		},
		showResultsPage : function(xmldoc) {
			var appid = $("appid").value;
			var jgbh = $("jgbh").value;
			var states = [ "未签收", "已签收", "已处理" ];
			// var statesdis = ["未签收","已签收"];
			var stypes = [ "发送", "接收", "抄送" ];
			// var coldescfull =
			// ["重大情况编号","标题","发送单位","接收单位","转发单位","情况状态代码","签收状态代码","单位类型"];//10
			var colwidths = [ "15", "15", "15", "15", "8", "8" ];
			// 1 2 3 4 5 6
			var coldescs = [ "标题", "发送单位", "接收单位", "发送时间", "处理状态", "信息类型" ];

			var results = null;
//			var str = "<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>";
			var str = "<div style='display:inline'>" + 
					"<div style='text-align:center;width:80%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span>" +
					"</div>" +
					"<div class='lsearch' style='float:right;'><a href='#' onclick=\"Notice.search.showExcelInfo();\" class='currentLocation'>" +
					"<span class='lbl'>导出Excel</span></a></div>" +
			 		 "</div>";
			str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
			str += "<tr class='titleTopBack'>";
			str += "<td width='5%' class='td_r_b td_font'>序号</td>";
			for ( var i = 0; i < coldescs.length; i++) {
				str += "<td width='" + colwidths[i]
						+ "%' class='td_r_b td_font'>" + coldescs[i] + "</td>";
			}
			str += "<td width='5%' class='td_r_b td_font'>查看</td>";
			var infoType = $("stype").value;
			if (appid == "1001") {
				// //Modified by Liuwx 2011-07-27
				// if(infoType == "0"){
				// str += "<td width='10%' class='td_r_b td_font'>处理/续报</td>";
				// }else if(infoType == "1"){
				// str += "<td width='5%' class='td_r_b td_font'>续报</td>";
				// }else if(infoType == "2"){
				// str += "<td width='5%' class='td_r_b td_font'>处理</td>";
				// }
				// //Modification finished
				// Modified by Liuwx 2011-07-27
				str += "<td width='5%' class='td_r_b td_font'>处理</td>";
				if (infoType == "0" || infoType == "1") {
					str += "<td width='5%' class='td_r_b td_font'>续报</td>";
				}
				// Modification finished
			}
			str += "</tr>";

			var rows = xmldoc.getElementsByTagName("row");
			if (rows.length <= 0) {
				str += "<tr class='titleTopBack'>";
				str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
				str += "</tr>";
			} else {
				var text, length = 16;
				var id, stype, state;
				for ( var i = 0; i < rows.length; i++) {
					results = rows[i].childNodes;
					str += "<tr align='center' height='25'>";
					str += "<td width='5%'  class='td_r_b td_font' align=\'center\'>"
							+ (i + 1) + "</td>";
					for ( var j = 0; j < coldescs.length - 2; j++) {
						text = results[j + 1].text;
						text = text == "null" ? "无" : text;
						if (text.length > length) {
							text = "<label title='" + text + "'>"
									+ text.substring(0, length) + "</label>";
						}
						str += "<td class='td_r_b td_font'>" + text + "</td>";
					}
					str += "<td class='td_r_b td_font'>"
							+ states[parseInt(results[5].text) - 1] + "</td>";
					str += "<td class='td_r_b td_font'>"
							+ stypes[parseInt(results[6].text) - 1] + "</td>";

					id = results[0].text;
					stype = results[6].text;
					str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
							+ "<img src=\"../../images/button/para.gif\" onClick=\"showNotice('2','"
							+ id
							+ "','"
							+ stype
							+ "','820','620')\"  style=\"cursor:hand\"  />"
							+ "</td>";
					var dealImgPath = "", methodName = "";
					if (stype == "3") {// 转发单位已签收，不能再签收.
						if (results[5].text == "2") {
							dealImgPath = "lock.png";
							methodName = "alert('已签收！')";
						} else {
							dealImgPath = "update.gif";
							methodName = "showNotice('3','" + id + "','" + stype
									+ "','820','620')";
						}
					} else {
						dealImgPath = "update.gif";
						methodName = "showNotice('3','" + id + "','" + stype
								+ "','820','620')";
					}
					var xbImgPath, xbMethodName;
					if (stype == "1") {
						xbImgPath = "update.gif";
						xbMethodName = "showNoticeXB('3','" + id + "','"
								+ stype + "','700','280')";
					} else {
						xbImgPath = "lock.png";
						xbMethodName = "alert('不是填报单位不能续报！')";
					}
					if (appid == "1001") {
						str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
								+ "<img src=\"../../images/button/"
								+ dealImgPath
								+ "\" onClick=\""
								+ methodName
								+ "\"  style=\"cursor:hand\"/>" + "</td>";
						if (infoType == "0" || infoType == "1") {
							str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"
									+ "<img src=\"../../images/button/"
									+ xbImgPath
									+ "\" onClick=\""
									+ xbMethodName
									+ "\"  style=\"cursor:hand\"/>" + "</td>";
						}
					}
					str += "</tr>";
				}
			}
			str += "</table>";
			var pagingObj = $('pageData').up('tr').next('tr').down('td');
			pagingObj.style.textAlign = "center";
			var tabOjb = document.getElementById("pageData");
			tabOjb.innerHTML = str;
			Popup.prototype.hideTips();
		},
		showExcelInfo : function() {
			var sql = Notice.search.getSql();
			var url = "dispatch.notice.showExcel.d?" + "searchSql=" + sql;
			url = encodeURI(url);

			window.open(url, "", "height=500,width=800,top=" + 100 + ",left="
					+ 200 + ",toolbar=yes,menubar=yes,"
					+ "scrollbars=yes,resizable=yes,location=no,status=no");
		}
	},
	addAttachment : function(type) {
		if (Notice.add.check(type)) {
			UDload.cbfname = "Notice.add.submit(" + (type || "") + ")";
			UDload.upload();
		}
	},
	updateCheck : function() {
		var addctnt, desc;
		if (Notice.stype == "1") {
			addctnt = "addContent";
			desc = "内容";
		} else if (Notice.stype == "2") {
			addctnt = "fcontent";
			desc = "办理情况";
		}
		addctnt = $("fcontent");
		if (addctnt.value == "") {
			alert("请填写" + desc + "！");
			addctnt.focus();
			return false;
		}
		Notice.desc = desc;
		Notice.addctnt = addctnt.value;

		if (!validateInput()) {
			return;
		}

		return true;
	},
	updateSubmit : function(mdesc) {
		var url = "dispatch.notice.updateNotice.d";
		var params = {
			sid : Notice.id,
			stype : Notice.stype,
			content : Notice.addctnt,
			spname : Notice.name
		};
		if (selectContent && selectContent.length > 0 && mdesc != "保存") {
			params.cids = selectContent.join(";");
		}

		var adcode = $("adcode").value;
		if (adcode != "") {
			adcode = adcode.replace(/；/g, ",");
			adcode = adcode.substring(0, adcode.length - 1);
			params.adcode = adcode;
		}
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : function(xmlHttp) {
				var result = Notice.showResponse(xmlHttp, mdesc
						+ (Notice.desc || "办理情况"));
				if (result == "true") {
					window.close();
				}
			}
		});
	},
	update : function() {
		if (Notice.updateCheck()) {
			Notice.updateSubmit("保存");
		}
	},
	cancel : function() {
		$("spname").value = Notice.name;
		$("jgmc").value = "";
		$("title").value = "";
		$("content").value = "";
		var fulForm = $(UDload.formId);
		if (fulForm) {
			var items = fulForm.elements;
			fulForm.reset();
		}
	},
	finish : function(state) {
		var desc;
		if (state == "2") {
			desc = "签收";
		} else if (state == "3") {
			desc = "办结";
		}
		if (Notice.stype == "1") {
			var url = "dispatch.notice.modifyNotice.d";
			var params = {
				id : Notice.id,
				state : state,
				rpname : Notice.name
			};
			new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp) {
					var result = Notice.showResponse(xmlHttp, desc + "重大情况");
					if (result == "true") {
						window.close();
					}
				}
			});
		} else {
			Flow.updtState({
				id : Notice.id,
				stype : Notice.stype,
				state : state,
				name : $("name").value,
				jgid : $('jgid').value
			}, desc + "重大情况", true);
		}
	},
	dispatch : function() {
		var display = $("blqkNewTr").style.display;
		var cbmname = "Notice.updateSubmit(\\\'发送\\\')";
		if (display == "none") {
			var content = $("fcontent").value;
			if (content != "") {
				Notice.adcontent = content;
			}
			if (selectContent) {
				if (selectContent.length == 0) {
					alert("请选择一条办理情况！");
					return;
				}
			}
			Flow.showDispatch(Notice.id, Notice.jgid, "adcode", 140, 248,
					cbmname, 11);
		} else {
			if (Notice.updateCheck()) {
				Flow.showDispatch(Notice.id, Notice.jgid, "adcode", 140, 248,
						cbmname, 11);
			}
		}
	},
	showSend : function() {
		var cbmname = "Notice.send()";
		Flow.showDispatch(Notice.id, Notice.jgid, "adcode", 140, 248, cbmname,
				11);
	},
	send : function() {
		var params = {
			aid : Notice.id
		};
		var adcode = $("adcode").value;
		if (adcode != "") {
			adcode = adcode.replace(/；/g, ",");
			adcode = adcode.substring(0, adcode.length - 1);
			params.adcode = adcode;
		}
		var url = "dispatch.notice.send.d";
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : function(xmlHttp) {
				var result = Notice.showResponse(xmlHttp, "发送重大情况");
				if (result == "true") {
					window.close();
				}
			}
		});
	},
	showResponse : function(xmlHttp, msg, cb) {
		var result = xmlHttp.responseText;
		var rdesc;
		if (result == "true") {
			rdesc = "成功";
			alert(msg + rdesc + "！");
			if (cb == "reload") {
				window.location.reload();
			} else if (cb == "close") {
				window.close();
			}
		} else if (result == "false") {
			rdesc = "失败";
			alert(msg + rdesc + "！");
		}
		return result;
	},
	print : function() {
		var params = {};
		if (Notice.page == "1") {
			params.fsdw = Notice.dname;
			params.fszby = Notice.name;
			params.jsdw = $("jgmc").value;
			if (params.jsdw != "") {
				params.jsdw = params.jsdw.substr(0, params.jsdw.length - 1);
				params.jsdw = params.jsdw.replace("；", "、");
			}
			params.jszby = "";
			params.title = $("title").value;
			params.ncontent = $("content").value;
		} else {
			params.title = Notice.noticeInfo.title;
			params.ncontent = Notice.noticeInfo.ncontent;
			if (Notice.noticeInfo.xb) {
				params.xb = Notice.noticeInfo.xb;
			}
			params.fsdw = Notice.noticeInfo.fsdw || "";
			params.fszby = Notice.noticeInfo.fszby || "";
			if (Notice.stype == 1) {
				if (Notice.printJgInfo) {
					var printJgInfo = Notice.printJgInfo.split(",");
					var count = parseInt(printJgInfo[0]);
					var parent = printJgInfo[1];
					var jsdwJszby = getJsdwJszby(Notice.noticeInfo.jsdws,
							Notice.noticeInfo.jszbys, parent, count);
					params.jsdw = jsdwJszby[0];
					params.jszby = jsdwJszby[1];
				} else {
					params.jsdw = "";
					params.jszby = "";
				}
			} else {
				params.jsdw = Notice.dname;
				params.jszby = Notice.name;
			}
			if (Notice.noticeInfo.acontent) {
				params.acontent = Notice.noticeInfo.acontent;
			}
		}
		params.id = Notice.noticeInfo.id;
		params.rtime = Notice.noticeInfo.rtime;
		params.ncontent = "<p style='text-indent:24px;'>" + params.ncontent
				+ "</p>";
		params.cujgmc = Notice.dname;

		var url = "noticePrint.jsp";
		// var w = 400;
		// var h = 200;
		// var l = window.screenLeft+(document.body.offsetWidth-w)/2;
		// var t = window.screenTop + (document.body.offsetHeight-h)/2;
		// var print =
		// window.open("","print","left="+l+",top="+t+",width="+w+",height="+h+"");
		UDload.createTempIFRAME();
		Submit.urlToForm(url, params, UDload.TEMP_IFRAME_ID);
	},
	// Modified by Liuwx 2011-07-27
	insertNoticXB : function() {

		if (!validateInput()) {
			return;
		}

		if (checkNoticeXB() && confirm('是否确认报送?')) {
			var url = "dispatch.noticeFlow.addContent.d";
			var params = {};
			params["sid"] = $("id").value;
			params["spname"] = $("spname").value;
			params["stime"] = $("stime").value;
			params["content"] = $("content").value;
			new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp) {
					var result = Notice.showResponse(xmlHttp, "新增续报");
					if (result == "true") {
						window.close();
					}
				}
			});
		}
	}
// Modification finished
}

function showNotice(page, id, stype, width, height, flag) {
	var params = "&ptype=" + page + "&id=" + id + "&stype=" + stype;
	window.showModalDialog("noticeAdd.jsp?tmp=" + Math.random() + params, "",
			"dialogWidth:" + width + "px;dialogHeight:" + height + "px");
	if(flag == 'all') {
		doSearchAll();
	} else if(flag == 'qt') {
		doSearchNoticeInfo();
	} else {
		if (page == "3") {
			Notice.search.submit();
		}
	}
}

// Modified by Liuwx 2011-07-27
function showNoticeXB(page, id, stype, width, height, flag) {
	var params = "&ptype=" + page + "&sid=" + id + "&stype=" + stype;
	window.showModalDialog("noticeFlowAdd.jsp?tmp=" + Math.random() + params,
			"", "dialogWidth:" + width + "px;dialogHeight:" + height + "px");
	if(flag == "qt") {
		doSearchNoticeInfo();
	} else {
		if (page == "3") {
			Notice.search.submit();
		}
	}
}

function checkNoticeXB() {
	var writePName = $("spname");
	if (writePName.value == "") {
		alert("请输入续报人姓名！");
		writePName.focus();
		return false;
	}
	var writeContent = $("content");
	if (writeContent.value == "") {
		alert("请输入续报内容！");
		writeContent.focus();
		return false;
	}
	return true;
}
// Modification finished

/**
 * * 显示附件信息的链接<br>
 */
function openWPS(fileName) {
	var widthv = 400;
	var heightv = 200;
	var xposition = (screen.availWidth - widthv) / 2;
	var yposition = (screen.availHeight - heightv) / 2;
	var feature = 'height='
			+ heightv
			+ ',width='
			+ widthv
			+ ',top='
			+ yposition
			+ ',left='
			+ xposition
			+ ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';
	var param = "../FileDownload.jsp?fileName=" + fileName;
	param = encodeURI(param);
	window.open(param, "", feature);
}

function showSave(type) {
	var types = [ 1, 0 ];
	var descs = [ " 添 加 ", " 取 消 " ];
	var displays = [ "none", "inline" ];
	var addBlqk = $("addBlqk");
	addBlqk.innerHTML = "<span class='lbl'>" + descs[type] + "</span>";
	var blqkNew = $("blqkNewTr");
	blqkNew.style.display = displays[type];
	if (type == 1) {
		$("fcontent").focus();
	}
	var save = $("update");
	save.style.display = displays[type];
	addBlqk.onclick = function() {
		showSave(types[type]);
	}
}

function getJsdwJszby(jsdws, jszbys, pjgmc, count) {
	var jsdw = "", jszby = "";
	if (jsdws && jszbys) {
		var ddJsdw, ddJszby, zdJsdw, zdJszby;
		var zdjgmc = pjgmc;
		var est = "等";
		var fh = "；";
		if (pjgmc) {
			for ( var i = 0; i < jsdws.length; i++) {
				if (jsdws[i] == pjgmc) {
					jsdws.splice(i, 1);
					zdJsdw = jsdws[i];
					zdJszby = jszbys[i];
					break;
				}
			}
		}
		if (zdJsdw) {
			jsdw += zdJsdw + fh;
			jszby += zdJszby + fh;
		}
		var l = jsdws.length;
		if (l > 0) {
			if (l == 1) {
				ddJsdw = jsdws[0];
				ddJszby = jszbys[0];
			} else {
				for ( var i = 0; i < jsdws.length; i++) {
					if (jszbys[i] && jszbys[i] != "null") {
						ddJsdw = jsdws[i];
						ddJszby = jszbys[i];
						break;
					}
				}
				if (!ddJsdw) {
					ddJsdw = jsdws[0];
					ddJszby = jszbys[0];
				}
				if (ddJszby) {
					ddJszby += est;
				}
				if (l < count) {
					ddJsdw += est;
				} else if (l == count) {
					var l = Notice.jgbh.length;
					if (l == 2) {
						ddJsdw = "全省各支队";
						// ddJszby = "全省各支队值班员";
					} else if (l == 4) {
						ddJsdw = "全市各大队";
						// ddJszby = "全市各大队值班员";
					}
				}
			}
		}
		if (zdJsdw) {
			jsdw += zdJsdw;
			jszby += zdJszby;
		}
		if (ddJsdw) {
			jsdw += ddJsdw;
			jszby += ddJszby;
		}
	}
	return [ jsdw, jszby ];
}

function showMoreCondition(){
	if($('moreConditionTr').visible()){
		$('defalutTitleTr').show();
		$('moreConditionTr').hide();
        $('simpleButtomTd').show();
        $('moreButtomTr').hide();
		$('clzt_lable').hide();
		$('state').hide();
		$('xxlx_lable').hide();
		$('stype').hide();
//		$('moreLable').innerHTML = "高级查询";
		clearElementValue();
	}else{
		$('defalutTitleTr').hide();
		$('moreConditionTr').show();
        $('simpleButtomTd').hide();
        $('moreButtomTr').show();
		$('clzt_lable').show();
		$('state').show();
		$('xxlx_lable').show();
		$('stype').show();
//		$('moreLable').innerHTML = "简单查询";
	}
}

function clearElementValue(){
    G_jgID = "";
	$('title').value = "";
	$('AlarmUnit').value = "";
	$('state').options[0].selected = true;
	$('stype').options[0].selected = true;
}
