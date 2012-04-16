var address = "all";
//var _title_ = "<div style='display:inline'>" + 
//					"<div style='text-align:center;width:80%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span>" +
//					"</div>" +
//					"<div class='lsearch' style='float:right;'><a href='#' onclick=\"showExcelInfo('search','"+jQuery("#jgbh").val()+"')\" class='currentLocation'>" +
//					"<span class='lbl'>导出Excel</span></a></div>" +
//			  "</div>";

jQuery(document).ready(function() {
	doSearchAll();
	jQuery("input[name=eventType]").change(function() {
		if (jQuery(this).val() == "全部") {
			doSearchAll();
			document.getElementById("td1").style.display = "inline";
			document.getElementById("td2").style.display = "none";
			document.getElementById("td3").style.display = "none";
			document.getElementById("td4").style.display = "none";
			document.getElementById("td5").style.display = "none";
			document.getElementById("td6").style.display = "none";
		} else if (jQuery(this).val() == "重特大事故") {
			doSearchMaterialInfo();
			document.getElementById("td1").style.display = "none";
			document.getElementById("td2").style.display = "inline";
			document.getElementById("td3").style.display = "none";
			document.getElementById("td4").style.display = "none";
			document.getElementById("td5").style.display = "none";
			document.getElementById("td6").style.display = "none";
		} else if (jQuery(this).val() == "交通拥堵") {
			doSearchCrowdInfo();
			document.getElementById("td1").style.display = "none";
			document.getElementById("td3").style.display = "inline";
			document.getElementById("td2").style.display = "none";
			document.getElementById("td4").style.display = "none";
			document.getElementById("td5").style.display = "none";
			document.getElementById("td6").style.display = "none";
		} else if (jQuery(this).val() == "施工占道") {
			doSearchRoadInfo();
			document.getElementById("td1").style.display = "none";
			document.getElementById("td4").style.display = "inline";
			document.getElementById("td2").style.display = "none";
			document.getElementById("td3").style.display = "none";
			document.getElementById("td5").style.display = "none";
			document.getElementById("td6").style.display = "none";
		} else if (jQuery(this).val() == "协查通报") {
			doSearchXCBKInfo();
			document.getElementById("td1").style.display = "none";
			document.getElementById("td5").style.display = "inline";
			document.getElementById("td2").style.display = "none";
			document.getElementById("td3").style.display = "none";
			document.getElementById("td4").style.display = "none";
			document.getElementById("td6").style.display = "none";
		} else if (jQuery(this).val() == "其他重大情况/值班日志") {
			doSearchNoticeInfo();
			document.getElementById("td1").style.display = "none";
			document.getElementById("td6").style.display = "inline";
			document.getElementById("td2").style.display = "none";
			document.getElementById("td3").style.display = "none";
			document.getElementById("td4").style.display = "none";
			document.getElementById("td5").style.display = "none";
		}
	});
});

function doSearchAll() {
	var jgid = jQuery("#jgid").val();
	var jgbh = jQuery("#jgbh").val();
	var start = jQuery("#tbsjStart").val();
	var end = jQuery("#tbsjEnd").val();
	if(jQuery("#tbsjEnd").val() && !(jQuery("#tbsjStart").val())){
		alert("开始时间不能为空！");
		return;
	}
	if(!(jQuery("#tbsjEnd").val()) && jQuery("#tbsjStart").val()){
		alert("结束时间不能为空！");
		return;
	}
	if(jQuery("#tbsjStart").val()>jQuery("#tbsjEnd").val()){
		alert("开始时间不能大于结束时间！");
		return;
	}
	var jgmc = jQuery("#jgmc_all").val();
	var info_type = jQuery("#info_type_all").find("option:selected").text();
	info_type = info_type=="全部"?"":info_type;
	var info_title = jQuery("#info_title_all").val();
//	var alarmId = jQuery("#searchAlarmId").val();
	var urlParent;
	jQuery("#tdData").jqGrid("setLabel", "COUNT_TYPE", "全部信息", {
		"text-align" : "center"
	});
	urlParent = "dispatch.allMaterialInfo.getAllMaterialInfo.d?jgid=" + jgid
			+ "&jgbh=" + jgbh + "&jgmc=" + jgmc + "&info_type=" + info_type
			+ "&info_title=" + info_title + "&start=" + start + "&end=" + end;
	urlParent = encodeURI(urlParent);

	jQuery(".norecords").hide();
	jQuery("#tdData").jqGrid('setGridParam', {
		url : urlParent
	}).trigger("reloadGrid");

	jQuery("#tdData").jqGrid(
			{
				url : urlParent,
				datatype : "json",
				mtype : 'post',
				colNames : [ '序号', "标题", '填报单位', '时间', '状态', '类型', '查看', '处理',
						'alarmId', 'col9', 'col10', 'col11' ],
				colModel : [ {
					name : 'row_num',
					index : 'row_num',
					width : 30,
					align : "center"
				}, {
					name : 'col2',
					index : 'col2',
					width : 150,
					align : "center"
				}, {
					name : 'col3',
					index : 'col3',
					width : 120,
					align : "center"
				}, {
					name : 'col4',
					index : 'col4',
					width : 120,
					align : "center"
				}, {
					name : 'col5',
					index : 'col5',
					width : 55,
					align : "center",
					formatter : stateFormatter
				}, {
					name : 'col6',
					index : 'col6',
					width : 55,
					align : "center"
				}, {
					name : 'check',
					index : 'check',
					width : 30,
					align : "center",
					formatter : checkButtonFormatter,
					sortable : false
				}, {
					name : 'resolve',
					index : 'resolve',
					width : 30,
					align : "center",
					formatter : resolveButtonFormatter,
					sortable : false
				}, {
					name : 'alarmId',
					index : 'alarmId',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col9',
					index : 'col9',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col10',
					index : 'col10',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col11',
					index : 'col11',
					width : 55,
					align : "center",
					hidden : true
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				width : 800,
				height : 300,
				pager : '#div_pageId',
				sortname : '1',
				multiselect : false,
				caption : "查询结果",
				loadtext : "获取数据中...",
				viewrecords : true,
				loadComplete : noRecordsTips,
				gridComplete : function() {
					// jQuery(".ui-jqgrid-title").replaceWith(
					// '<div style="text-align: center; padding: .3em .2em .2em
					// .3em; ><span>' +
					// jQuery(".ui-jqgrid-title").text() + '</span></div>');
					var captionDiv = jQuery("#tdData")[0].grid.cDiv;
					var titleSpan = jQuery(".ui-jqgrid-title", captionDiv);
					titleSpan.css("float", "none");
					titleSpan.css("background", "none");
					titleSpan.parent().css("text-align", "center");
					var headerSpan = jQuery(".ui-widget-header");
					headerSpan.css("background", "none");
				},
				loadError : function(xhr, status, error) {
					alert('初始化表格失败！');
				}
			});
	// can not edit
	jQuery("#tdData").jqGrid('navGrid', '#div_pageId', {
		add : false,
		edit : false,
		del : false,
		search : false
	}, {}, {}, {}, {
		overlay : 0
	});
}

/**
 * 
 * 方法名称：noRecordsTips<br>
 * 方法描述： 无记录时的提示信息 <br>
 * <br>
 * 版本信息：Ver 1.1 <br>
 * **********************************<br>
 * 创建人：Vasin <br>
 * 创建时间：2011-12-28 下午5:36:37 <br>
 * ************ 修改历史 *************<br>
 * 修改人：Vasin <br>
 * 修改时间：2011-12-28 下午5:36:37 <br>
 * 修改备注： <br>
 */
function noRecordsTips(data) {
	if (data != null && data.records != null && data.records <= 0) {
		if (jQuery(".norecords").html() == null) {
			jQuery(this).parent().append(
					"<div class=\"norecords\">没有符合的数据！</div>");
		}
		jQuery(".norecords").show();
	} else {
		jQuery(".norecords").hide();
	}
}

function checkButtonFormatter(cellvalue, options, rowObject) {
	var jgbh = jQuery("#jgbh").val();
	if (cellvalue == "重特大事故view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onMaterialClick('view','"
				+ rowObject[10]
				+ "','"
				+ rowObject[8]
				+ "','"
				+ rowObject[4]
				+ "','all');\">";
	} else if (cellvalue == "交通拥堵view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onTrafficClick('view','"
				+ rowObject[10]
				+ "','"
				+ rowObject[8]
				+ "','"
				+ rowObject[9]
				+ "','2','all')\">";
	} else if (cellvalue == "施工占道view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onRoadClick('view','"
				+ rowObject[10]
				+ "','"
				+ rowObject[8]
				+ "','"
				+ rowObject[9]
				+ "','2','all')\">";
	} else if (cellvalue == "协查通报view") {
		if (rowObject[13] == "无") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showMD('2','"
					+ rowObject[10]
					+ "',"
					+ rowObject[12]
					+ ",'900','620','all')\">";
		} else {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showMD('2','"
					+ rowObject[13]
					+ "',"
					+ rowObject[12]
					+ ",'900','620','all')\">";
		}
	} else if (cellvalue == "其他重大情况view" || cellvalue == "值班日志view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showNotice('2','"
				+ rowObject[10] + "','2','900','620','all')\">";
	}

	return cellvalue;
}

function resolveButtonFormatter(cellvalue, options, rowObject) {
	if (jQuery("#appid").val() == '1001') {
		if (cellvalue == "重特大事故edit") {
			if (rowObject[4] == "支队下发") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('支队下发信息不能操作！')\">";
			} else if (rowObject[4] == "总队转发") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('总队转发信息不能操作！')\">";
			} else if (jQuery("#jgbh").val().length == 6) {
				if (rowObject[8] == "004032" || rowObject[8] == "004033") {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"getMaterialState('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','','all');\">";
				} else {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('不能对该消息进行处理！')\">";
				}
			} else if (jQuery("#jgbh").val().length == 4) {
				if (rowObject[8] == "004036" || rowObject[8] == "004043"
						|| rowObject[8] == "004037" || rowObject[8] == "004035"
						|| rowObject[8] == "004031") {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('不能对该消息进行处理！')\">";
				} else {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"getMaterialState('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','','all');\">";
				}
			} else {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"getMaterialState('edit','"
						+ rowObject[10]
						+ "','"
						+ rowObject[8]
						+ "','','all');\">";
			}
		} else if (cellvalue == "交通拥堵edit") {
			if (rowObject[8] == "570001") {
				if (jQuery("#jgid").val() == rowObject[9]) {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onTrafficClick('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','"
							+ rowObject[9] + "','','all')\">";
				} else if (jQuery("#jgid").val().substring(2, 4) == "00") {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onTrafficClick('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','"
							+ rowObject[9] + "','','all')\">";
				} else {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"onTrafficClick('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','"
							+ rowObject[9] + "','','all')\">";
				}
			} else if (rowObject[8] == "570002") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('拥堵已解除，不能处理！')\">";
			}
		} else if (cellvalue == "施工占道edit") {
			if (rowObject[8] == "570005") {
				if (jQuery("#jgid").val() == rowObject[9]) {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onRoadClick('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','"
							+ rowObject[9] + "','','all')\">";
				} else if (jQuery("#jgid").val().substring(2, 4) == "00") {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onRoadClick('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','"
							+ rowObject[9] + "','','all')\">";
				} else {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"onRoadClick('edit','"
							+ rowObject[10]
							+ "','"
							+ rowObject[8]
							+ "','"
							+ rowObject[9] + "','','all')\">";
				}
			} else if (rowObject[8] == "570006") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('施工占道已解除，不能进行修改！')\">";
			}
		} else if (cellvalue == "协查通报edit") {
			// 8是解除状态，11签收状态，12是是否转发
			var state = rowObject[8];
			var stype = rowObject[12];
			var dealImgPath;
			var methodName;
			var id;

			if (stype == "1") {
				id = rowObject[10];
			} else if (stype == "2" || stype == "3") {
				id = rowObject[13];
			}

			if (state == "1") {// 未解除
				if (jQuery("#jgbh").val().length != 2 && stype != "1"
						&& rowObject[11] != "1") {//
					dealImgPath = "update_hover.gif";
					methodName = "alert('已签收！')";
				} else {
					dealImgPath = "update.gif";
					methodName = "showMD('3','" + id + "','" + stype
							+ "','900','620','all')";
				}
			} else {
				dealImgPath = "update_hover.gif";
				methodName = "alert('协查通报已解除！')";
			}
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/"
					+ dealImgPath + "\" onclick=\"" + methodName + "\">";
		} else if (cellvalue == "其他重大情况edit" || cellvalue == "值班日志edit") {
			var stype = rowObject[11];
			var dealImgPath;
			var methodName;
			var id = rowObject[10];

			// 1,2 = 5,6
			if (stype == "3") {// 转发单位已签收，不能再签收.
				if (rowObject[8] == "2") {
					dealImgPath = "lock.png";
					methodName = "alert('已签收！')";
				} else {
					dealImgPath = "update.gif";
					methodName = "showNotice('3','" + id + "','" + stype
							+ "','820','620','all')";
				}
			} else {
				dealImgPath = "update.gif";
				methodName = "showNotice('3','" + id + "','" + stype
						+ "','820','620','all')";
			}
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/"
					+ dealImgPath + "\" onclick=\"" + methodName + "\">";
		}
	} else {
		if (cellvalue == "重特大事故edit") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('您没有权限处理该消息')\">";
		} else if (cellvalue == "交通拥堵edit") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('您没有权限处理该消息')\">";
		} else if (cellvalue == "施工占道edit") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('您没有权限处理该消息')\">";
		} else if (cellvalue == "协查通报edit") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('您没有权限处理该消息')\">";
		} else if (cellvalue == "其他重大情况edit" || cellvalue == "值班日志edit") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('您没有权限处理该消息')\">";
		}
	}
	return cellvalue;
}

function doSearchMaterialInfo() {
	var jgid = jQuery("#jgid").val();
	var jgbh = jQuery("#jgbh").val();
	var alarmId = jQuery("#searchAlarmId").val();
	var urlParent;
	jQuery("#jqData").jqGrid("setLabel", "COUNT_TYPE", "全部信息", {
		"text-align" : "center"
	});
	urlParent = "dispatch.allMaterialInfo.getMaterialInfo.d?jgid=" + jgid
			+ "&jgbh=" + jgbh + "&alarmId=" + alarmId;
	urlParent = encodeURI(urlParent);

	jQuery(".norecords").hide();
	jQuery("#jqData").jqGrid('setGridParam', {
		url : urlParent
	}).trigger("reloadGrid");

	jQuery("#jqData").jqGrid(
			{
				url : urlParent,
				datatype : "json",
				mtype : 'post',
				// 0 1 2 3 4 5 6 7 8 9 10 11 12 13
				colNames : [ '编号', '警情id', '事故标题', '填报单位', '填报人', '填报时间', '状态',
						'状态代码', '机构id', '所属机构id', '查看', '处理', '新建续报', '删除' ],
				colModel : [ {
					name : 'rownum',
					index : 'rownum',
					width : 55,
					align : "center"
				}, {
					name : 'col1',
					index : 'col1',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col2',
					index : 'col2',
					width : 100,
					align : "center"
				}, {
					name : 'col3',
					index : 'col3',
					width : 100,
					align : "center"
				}, {
					name : 'col4',
					index : 'col4',
					width : 100,
					align : "center"
				}, {
					name : 'col5',
					index : 'col5',
					width : 100,
					align : "center"
				}, {
					name : 'col6',
					index : 'col6',
					width : 100,
					align : "center",
					formatter : materialFormatter
				}, {
					name : 'col7',
					index : 'col7',
					width : 100,
					align : "center",
					hidden : true
				}, {
					name : 'col8',
					index : 'col8',
					width : 100,
					align : "center",
					hidden : true
				}, {
					name : 'col9',
					index : 'col9',
					width : 100,
					align : "center",
					hidden : true
				}, {
					name : 'view',
					index : 'view',
					width : 55,
					align : "center",
					sortable : false,
					formatter : materialFormatter
				}, {
					name : 'edit',
					index : 'edit',
					width : 55,
					align : "center",
					sortable : false,
					formatter : materialFormatter
				}, {
					name : 'xb',
					index : 'xb',
					width : 70,
					align : "center",
					sortable : false,
					formatter : materialFormatter
				}, {
					name : 'delete',
					index : 'delete',
					width : 55,
					align : "center",
					sortable : false,
					formatter : materialFormatter
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				width : 800,
				height : 320,
				pager : '#jqpage',
				sortname : '1',
				multiselect : false,
				caption : "查询结果",
				loadtext : "获取数据中...",
				viewrecords : true,
				loadComplete : noRecordsTips,
				gridComplete : function() {
					// jQuery(".ui-jqgrid-title").replaceWith(
					// '<div style="text-align: center; padding: .3em .2em .2em
					// .3em; ><span>' +
					// jQuery(".ui-jqgrid-title").text() + '</span></div>');
					var captionDiv = jQuery("#jqData")[0].grid.cDiv;
					var titleSpan = jQuery(".ui-jqgrid-title", captionDiv);
					titleSpan.css("float", "none");
					titleSpan.css("background", "none");
					titleSpan.parent().css("text-align", "center");
					var headerSpan = jQuery(".ui-widget-header");
					headerSpan.css("background", "none");
				},
				loadError : function(xhr, status, error) {
					alert('初始化表格失败！');
				}
			});
	jQuery("#jqData").jqGrid('navGrid', '#jqpage', {
		add : false,
		edit : false,
		del : false,
		search : false
	}, {}, {}, {}, {
		overlay : 0
	});
}

function stateFormatter(cellvalue, options, rowObject) {
	if (rowObject[5] == "重特大事故") {
		if (jQuery("#jgbh").val().length == 4) {
			if (rowObject[9].substring(0, 4) != jQuery("#jgid").val()
					.substring(0, 4)) {
				cellvalue = "总队转发";
			}
		}
	}
	return cellvalue;
}

function materialFormatter(cellvalue, options, rowObject) {
	var jgbh = jQuery("#jgbh").val();
	if (cellvalue == rowObject[6]) {
		if (jgbh.length == 4) {
			if (rowObject[9].substring(0, 4) != jQuery("#jgid").val()
					.substring(0, 4)) {
				cellvalue = "总队转发";
			}
		}
	}

	if (jQuery("#appid").val() == '1001') {
		if (cellvalue == "view") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onMaterialClick('view','"
					+ rowObject[1]
					+ "','"
					+ rowObject[7]
					+ "','"
					+ rowObject[6] + "','jq');\">";
		} else if (cellvalue == "edit") {
			if (rowObject[6] == "支队下发") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('支队下发信息不能操作！')\">";
			} else if (rowObject[6] == "总队转发") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('总队转发信息不能操作！')\">";
			} else if (jQuery("#jgbh").val().length == 6) {
				if (rowObject[7] == "004032" || rowObject[7] == "004033") {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"getMaterialState('edit','"
							+ rowObject[1]
							+ "','"
							+ rowObject[7]
							+ "','','jq');\">";
				} else {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('不能对该消息进行处理！')\">";
				}
			} else if (jQuery("#jgbh").val().length == 4) {
				if (rowObject[7] == "004036" || rowObject[7] == "004043"
						|| rowObject[7] == "004037" || rowObject[7] == "004035"
						|| rowObject[7] == "004031") {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('不能对该消息进行处理！')\">";
				} else {
					cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"getMaterialState('edit','"
							+ rowObject[1]
							+ "','"
							+ rowObject[7]
							+ "','','jq');\">";
				}
			} else {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"getMaterialState('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[7]
						+ "','','jq');\">";
			}
		} else if (cellvalue == "xb") {
			if ((jgbh.length == 6) || (jgbh.length == 4)) {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onMaterialClick('flow','"
						+ rowObject[1] + "','','','jq');\">";
			} else {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('没有权限操作')\">";
			}
		} else if (cellvalue == "delete") {
			if (jgbh.length == 6 || jgbh.length == 4) {
				var deleteImgName = "btndelete2.gif", deleteClick = "alert('草稿信息才能删除')";
				if (jQuery("#jgid").val() == rowObject[8]
						&& rowObject[7] == "004032") {
					deleteImgName = "btndelete1.gif";
					deleteClick = "doMaterialDelete('" + rowObject[1] + "')";
				}
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/"
						+ deleteImgName + "\" onclick=\"" + deleteClick + "\">";
			} else {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('没有权限操作')\">";
			}
		}
	} else {
		if (cellvalue == "view") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onMaterialClick('view','"
					+ rowObject[1]
					+ "','"
					+ rowObject[7]
					+ "','"
					+ rowObject[6] + "','jq');\">";
		} else if (cellvalue == "resolve") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('没有权限操作')\">";
		} else if (cellvalue == "xb") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('没有权限操作')\">";
		} else if (cellvalue == "delete") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('没有权限操作')\">";
		}
	}
	return cellvalue;
}

function doSearchCrowdInfo() {
	var jgid = jQuery("#jgid").val();
	var jgbh = jQuery("#jgbh").val();
	var alarmId = jQuery("#searchAlarmId").val();
	var urlParent;
	jQuery("#ydData").jqGrid("setLabel", "COUNT_TYPE", "全部信息", {
		"text-align" : "center"
	});
	urlParent = "dispatch.allMaterialInfo.getCrowdInfo.d?jgid=" + jgid
			+ "&jgbh=" + jgbh + "&alarmId=" + alarmId;
	urlParent = encodeURI(urlParent);

	jQuery(".norecords").hide();
	jQuery("#ydData").jqGrid('setGridParam', {
		url : urlParent
	}).trigger("reloadGrid");

	jQuery("#ydData").jqGrid(
			{
				url : urlParent,
				datatype : "json",
				mtype : 'post',
				colNames : [ '编号', 'id', '填报单位', '路段名称', '方向', '道路名称', '拥堵原因',
						'报告时间', '拥堵状态', 'col9', 'col10', '机构id', 'col12',
						'col13', 'col14', '查看', '处理' ],
				colModel : [ {
					name : 'rownum',
					index : 'rownum',
					width : 55,
					align : "center"
				}, {
					name : 'col1',
					index : 'col1',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col2',
					index : 'col2',
					width : 100,
					align : "center"
				}, {
					name : 'col3',
					index : 'col3',
					width : 100,
					align : "center"
				}, {
					name : 'col4',
					index : 'col4',
					width : 100,
					align : "center",
					formatter : crowdFormatter
				}, {
					name : 'col5',
					index : 'col5',
					width : 100,
					align : "center"
				}, {
					name : 'col6',
					index : 'col6',
					width : 100,
					align : "center"
				}, {
					name : 'col7',
					index : 'col7',
					width : 70,
					align : "center"
				}, {
					name : 'col8',
					index : 'col8',
					width : 70,
					align : "center",
					formatter : crowdFormatter
				}, {
					name : 'col9',
					index : 'col9',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col10',
					index : 'col10',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col11',
					index : 'col11',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col12',
					index : 'col12',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col13',
					index : 'col13',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col14',
					index : 'col14',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'view',
					index : 'view',
					width : 55,
					align : "center",
					sortable : false,
					formatter : crowdFormatter
				}, {
					name : 'edit',
					index : 'edit',
					width : 55,
					align : "center",
					sortable : false,
					formatter : crowdFormatter
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				width : 800,
				height : 320,
				pager : '#ydpage',
				sortname : '1',
				multiselect : false,
				caption : "查询结果",
				loadtext : "获取数据中...",
				viewrecords : true,
				loadComplete : noRecordsTips,
				gridComplete : function() {
					// jQuery(".ui-jqgrid-title").replaceWith(
					// '<div style="text-align: center; padding: .3em .2em .2em
					// .3em; ><span>' +
					// jQuery(".ui-jqgrid-title").text() + '</span></div>');
					var captionDiv = jQuery("#ydData")[0].grid.cDiv;
					var titleSpan = jQuery(".ui-jqgrid-title", captionDiv);
					titleSpan.css("float", "none");
					titleSpan.css("background", "none");
					titleSpan.parent().css("text-align", "center");
					var headerSpan = jQuery(".ui-widget-header");
					headerSpan.css("background", "none");
				},
				loadError : function(xhr, status, error) {
					alert('初始化表格失败！');
				}
			});
	jQuery("#ydData").jqGrid('navGrid', '#ydpage', {
		add : false,
		edit : false,
		del : false,
		search : false
	}, {}, {}, {}, {
		overlay : 0
	});
}

function crowdFormatter(cellvalue, options, rowObject) {
	if (cellvalue == rowObject[4]) {
		cellvalue = getDlfx(rowObject[13], rowObject[4], rowObject[9],
				rowObject[10]);
	}

	if (cellvalue == "570001") {
		cellvalue = "<input type=\"image\" src=\"../../images/state/busyState.png\">";
	} else if (cellvalue == "570002") {
		cellvalue = "<input type=\"image\" src=\"../../images/state/workState.png\">";
	}

	if (cellvalue == "view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onTrafficClick('view','"
				+ rowObject[1]
				+ "','"
				+ rowObject[8]
				+ "','"
				+ rowObject[11]
				+ "','" + rowObject[12] + "','yd')\">";
	}

	if (cellvalue == "edit") {
		if (rowObject[8] == "570001") {
			if (jQuery("#jgid").val() == rowObject[11]) {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onTrafficClick('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[8]
						+ "','"
						+ rowObject[11] + "','','yd')\">";
			} else if (jQuery("#jgid").val().substring(2, 4) == "00") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onTrafficClick('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[8]
						+ "','"
						+ rowObject[11] + "','','yd')\">";
			} else {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"onTrafficClick('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[8]
						+ "','"
						+ rowObject[11] + "','','yd')\">";
			}
		} else if (rowObject[8] == "570002") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('拥堵已解除，不能处理！')\">";
		}
	}

	return cellvalue;
}

function doSearchRoadInfo() {
	var jgid = jQuery("#jgid").val();
	var jgbh = jQuery("#jgbh").val();
	var alarmId = jQuery("#searchAlarmId").val();
	var urlParent;
	jQuery("#sgData").jqGrid("setLabel", "COUNT_TYPE", "全部信息", {
		"text-align" : "center"
	});
	urlParent = "dispatch.allMaterialInfo.getRoadBuildInfo.d?jgid=" + jgid
			+ "&jgbh=" + jgbh + "&alarmId=" + alarmId;
	urlParent = encodeURI(urlParent);

	jQuery(".norecords").hide();
	jQuery("#sgData").jqGrid('setGridParam', {
		url : urlParent
	}).trigger("reloadGrid");

	jQuery("#sgData").jqGrid(
			{
				url : urlParent,
				datatype : "json",
				mtype : 'post',
				colNames : [ '编号', 'id', '填报单位', '道路名称', '路段', '方向', '开始时间',
						'结束时间', '施工状态', 'col9', 'col10', '机构id', 'col12',
						'col13', '查看', '处理' ],
				colModel : [ {
					name : 'rownum',
					index : 'rownum',
					width : 55,
					align : "center"
				}, {
					name : 'col1',
					index : 'col1',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col2',
					index : 'col2',
					width : 100,
					align : "center"
				}, {
					name : 'col3',
					index : 'col3',
					width : 100,
					align : "center"
				}, {
					name : 'col4',
					index : 'col4',
					width : 100,
					align : "center"
				}, {
					name : 'col5',
					index : 'col5',
					width : 100,
					align : "center",
					formatter : roadFormatter
				}, {
					name : 'col6',
					index : 'col6',
					width : 100,
					align : "center"
				}, {
					name : 'col7',
					index : 'col7',
					width : 100,
					align : "center"
				}, {
					name : 'col8',
					index : 'col8',
					width : 70,
					align : "center",
					formatter : roadFormatter
				}, {
					name : 'col9',
					index : 'col9',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col10',
					index : 'col10',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col11',
					index : 'col11',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col12',
					index : 'col12',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col13',
					index : 'col13',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'view',
					index : 'view',
					width : 55,
					align : "center",
					sortable : false,
					formatter : roadFormatter
				}, {
					name : 'edit',
					index : 'edit',
					width : 55,
					align : "center",
					sortable : false,
					formatter : roadFormatter
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				width : 800,
				height : 320,
				pager : '#ydpage',
				sortname : '1',
				multiselect : false,
				caption : "查询结果",
				loadtext : "获取数据中...",
				viewrecords : true,
				loadComplete : noRecordsTips,
				gridComplete : function() {
					// jQuery(".ui-jqgrid-title").replaceWith(
					// '<div style="text-align: center; padding: .3em .2em .2em
					// .3em; ><span>' +
					// jQuery(".ui-jqgrid-title").text() + '</span></div>');
					var captionDiv = jQuery("#sgData")[0].grid.cDiv;
					var titleSpan = jQuery(".ui-jqgrid-title", captionDiv);
					titleSpan.css("float", "none");
					titleSpan.css("background", "none");
					titleSpan.parent().css("text-align", "center");
					var headerSpan = jQuery(".ui-widget-header");
					headerSpan.css("background", "none");
				},
				loadError : function(xhr, status, error) {
					alert('初始化表格失败！');
				}
			});
	jQuery("#sgData").jqGrid('navGrid', '#sgpage', {
		add : false,
		edit : false,
		del : false,
		search : false
	}, {}, {}, {}, {
		overlay : 0
	});
}

function roadFormatter(cellvalue, options, rowObject) {
	if (cellvalue == rowObject[5]) {
		cellvalue = getDlfx(rowObject[13], rowObject[5], rowObject[9],
				rowObject[10]);
	}

	if (cellvalue == "570005") {
		cellvalue = "<input type=\"image\" src=\"../../images/state/busyState.png\">";
	} else if (cellvalue == "570006") {
		cellvalue = "<input type=\"image\" src=\"../../images/state/workState.png\">";
	}

	if (cellvalue == "view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"onRoadClick('view','"
				+ rowObject[1]
				+ "','"
				+ rowObject[8]
				+ "','"
				+ rowObject[11]
				+ "','" + rowObject[13] + "','sg')\">";
	}

	if (cellvalue == "edit") {
		if (rowObject[8] == "570005") {
			if (jQuery("#jgid").val() == rowObject[11]) {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onRoadClick('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[8]
						+ "','"
						+ rowObject[11] + "','','sg')\">";
			} else if (jQuery("#jgid").val().substring(2, 4) == "00") {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/update.gif\" onclick=\"onRoadClick('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[8]
						+ "','"
						+ rowObject[11] + "','','sg')\">";
			} else {
				cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"onRoadClick('edit','"
						+ rowObject[1]
						+ "','"
						+ rowObject[8]
						+ "','"
						+ rowObject[11] + "','','sg')\">";
			}
		} else if (rowObject[8] == "570006") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/lock.png\" onclick=\"alert('施工占道已解除，不能进行修改！')\">";
		}
	}

	return cellvalue;
}

function doSearchXCBKInfo() {
	var jgid = jQuery("#jgid").val();
	var jgbh = jQuery("#jgbh").val();
	var alarmId = jQuery("#searchAlarmId").val();
	var urlParent;
	jQuery("#xcData").jqGrid("setLabel", "COUNT_TYPE", "全部信息", {
		"text-align" : "center"
	});
	urlParent = "dispatch.allMaterialInfo.getXCBKInfo.d?jgid=" + jgid
			+ "&jgbh=" + jgbh + "&alarmId=" + alarmId;
	urlParent = encodeURI(urlParent);

	jQuery(".norecords").hide();
	jQuery("#xcData").jqGrid('setGridParam', {
		url : urlParent
	}).trigger("reloadGrid");

	jQuery("#xcData").jqGrid(
			{
				url : urlParent,
				datatype : "json",
				mtype : 'post',
				colNames : [ '编号', 'id', '车牌号码', '发送单位', '发送时间', '接收单位',
						'转发单位', '状态', 'col8', 'col9', 'col10', 'col11', '查看',
						'处理' ],
				colModel : [ {
					name : 'rownum',
					index : 'rownum',
					width : 55,
					align : "center"
				}, {
					name : 'col1',
					index : 'col1',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col2',
					index : 'col2',
					width : 100,
					align : "center"
				}, {
					name : 'col3',
					index : 'col3',
					width : 100,
					align : "center"
				}, {
					name : 'col4',
					index : 'col4',
					width : 100,
					align : "center"
				}, {
					name : 'col5',
					index : 'col5',
					width : 100,
					align : "center"
				}, {
					name : 'col6',
					index : 'col6',
					width : 100,
					align : "center"
				}, {
					name : 'col7',
					index : 'col7',
					width : 100,
					align : "center"
				}, {
					name : 'col8',
					index : 'col8',
					width : 70,
					align : "center",
					hidden : true
				}, {
					name : 'col9',
					index : 'col9',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col10',
					index : 'col10',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col11',
					index : 'col11',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'view',
					index : 'view',
					width : 55,
					align : "center",
					sortable : false,
					formatter : xcbkFormatter
				}, {
					name : 'edit',
					index : 'edit',
					width : 55,
					align : "center",
					sortable : false,
					formatter : xcbkFormatter
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				width : 800,
				height : 320,
				pager : '#xcpage',
				sortname : '1',
				multiselect : false,
				caption : "查询结果",
				loadtext : "获取数据中...",
				viewrecords : true,
				loadComplete : noRecordsTips,
				gridComplete : function() {
					// jQuery(".ui-jqgrid-title").replaceWith(
					// '<div style="text-align: center; padding: .3em .2em .2em
					// .3em; ><span>' +
					// jQuery(".ui-jqgrid-title").text() + '</span></div>');
					var captionDiv = jQuery("#xcData")[0].grid.cDiv;
					var titleSpan = jQuery(".ui-jqgrid-title", captionDiv);
					titleSpan.css("float", "none");
					titleSpan.css("background", "none");
					titleSpan.parent().css("text-align", "center");
					var headerSpan = jQuery(".ui-widget-header");
					headerSpan.css("background", "none");
				},
				loadError : function(xhr, status, error) {
					alert('初始化表格失败！');
				}
			});
	jQuery("#xcData").jqGrid('navGrid', '#xcpage', {
		add : false,
		edit : false,
		del : false,
		search : false
	}, {}, {}, {}, {
		overlay : 0
	});
}

function xcbkFormatter(cellvalue, options, rowObject) {
	if (cellvalue == "view") {
		if (rowObject[11] == "无") {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showMD('2','"
					+ rowObject[1]
					+ "',"
					+ rowObject[10]
					+ ",'900','620','xcbk')\">";
		} else {
			cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showMD('2','"
					+ rowObject[11]
					+ "',"
					+ rowObject[10]
					+ ",'900','620','xcbk')\">";
		}
	} else if (cellvalue == "edit") {
		// 8是解除状态，11签收状态，12是是否转发
		var state = rowObject[8];
		var stype = rowObject[10];
		var dealImgPath;
		var methodName;
		var id;

		if (stype == "1") {
			id = rowObject[1];
		} else if (stype == "2" || stype == "3") {
			id = rowObject[11];
		}

		if (state == "1") {// 未解除
			if (jQuery("#jgbh").val().length != 2 && stype != "1"
					&& rowObject[9] != "1") {//
				dealImgPath = "update_hover.gif";
				methodName = "alert('已签收！')";
			} else {
				dealImgPath = "update.gif";
				methodName = "showMD('3','" + id + "','" + stype
						+ "','900','620','xcbk')";
			}
		} else {
			dealImgPath = "update_hover.gif";
			methodName = "alert('协查通报已解除！')";
		}
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/"
				+ dealImgPath + "\" onclick=\"" + methodName + "\">";
	}

	return cellvalue;
}

function doSearchNoticeInfo() {
	var jgid = jQuery("#jgid").val();
	var jgbh = jQuery("#jgbh").val();
	var alarmId = jQuery("#searchAlarmId").val();
	var urlParent;
	jQuery("#qtData").jqGrid("setLabel", "COUNT_TYPE", "全部信息", {
		"text-align" : "center"
	});
	urlParent = "dispatch.allMaterialInfo.getNoticeInfo.d?jgid=" + jgid
			+ "&jgbh=" + jgbh + "&alarmId=" + alarmId;
	urlParent = encodeURI(urlParent);

	jQuery(".norecords").hide();
	jQuery("#qtData").jqGrid('setGridParam', {
		url : urlParent
	}).trigger("reloadGrid");

	jQuery("#qtData").jqGrid(
			{
				url : urlParent,
				datatype : "json",
				mtype : 'post',
				colNames : [ '编号', 'id', '标题', '发送单位', '接收单位', '发送时间', '处理状态',
						'信息类型', '查看', '处理', '续报' ],
				colModel : [ {
					name : 'rownum',
					index : 'rownum',
					width : 55,
					align : "center"
				}, {
					name : 'col1',
					index : 'col1',
					width : 55,
					align : "center",
					hidden : true
				}, {
					name : 'col2',
					index : 'col2',
					width : 100,
					align : "center"
				}, {
					name : 'col3',
					index : 'col3',
					width : 100,
					align : "center"
				}, {
					name : 'col4',
					index : 'col4',
					width : 100,
					align : "center",
					formatter : noticeFormatter
				}, {
					name : 'col5',
					index : 'col5',
					width : 100,
					align : "center"
				}, {
					name : 'col6',
					index : 'col6',
					width : 100,
					align : "center",
					formatter : noticeStateFormatter
				}, {
					name : 'col7',
					index : 'col7',
					width : 100,
					align : "center",
					formatter : noticeStateFormatter
				}, {
					name : 'view',
					index : 'view',
					width : 55,
					align : "center",
					sortable : false,
					formatter : noticeFormatter
				}, {
					name : 'edit',
					index : 'edit',
					width : 55,
					align : "center",
					sortable : false,
					formatter : noticeFormatter
				}, {
					name : 'xb',
					index : 'xb',
					width : 55,
					align : "center",
					sortable : false,
					formatter : noticeFormatter
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				width : 800,
				height : 320,
				pager : '#qtpage',
				sortname : '1',
				multiselect : false,
				caption : "查询结果",
				loadtext : "获取数据中...",
				viewrecords : true,
				loadComplete : noRecordsTips,
				gridComplete : function() {
					// jQuery(".ui-jqgrid-title").replaceWith(
					// '<div style="text-align: center; padding: .3em .2em .2em
					// .3em; ><span>' +
					// jQuery(".ui-jqgrid-title").text() + '</span></div>');
					var captionDiv = jQuery("#qtData")[0].grid.cDiv;
					var titleSpan = jQuery(".ui-jqgrid-title", captionDiv);
					titleSpan.css("float", "none");
					titleSpan.css("background", "none");
					titleSpan.parent().css("text-align", "center");
					var headerSpan = jQuery(".ui-widget-header");
					headerSpan.css("background", "none");
				},
				loadError : function(xhr, status, error) {
					alert('初始化表格失败！');
				}
			});
	jQuery("#qtData").jqGrid('navGrid', '#qtpage', {
		add : false,
		edit : false,
		del : false,
		search : false
	}, {}, {}, {}, {
		overlay : 0
	});
}

function noticeFormatter(cellvalue, options, rowObject) {
	var appid = jQuery('#appid').val();
	var stype = rowObject[7];
	if (cellvalue == rowObject[4]) {
		if(rowObject[4] == "null") {
			cellvalue = "无";
		}
		if(rowObject[4].split("，").length > 2) {
			cellvalue = rowObject[4].split("，")[0] + "," + rowObject[4].split("，")[1];
		}
	} 
	if (cellvalue == "view") {
		cellvalue = "<input type=\"image\" src=\"../../../dispatch/images/button/para.gif\" onclick=\"showNotice('2','"
				+ rowObject[1] + "','"
				+ rowObject[7] + "','900','620','qt')\">";
	} else if (cellvalue == "edit") {
		var dealImgPath = "", methodName = "";
		if (stype == "3") {
			if (rowObject[6] == "2") {
				dealImgPath = "lock.png";
				methodName = "alert('已签收！')";
			} else {
				dealImgPath = "update.gif";
				methodName = "showNotice('3','" + rowObject[1] + "','" + stype
						+ "','820','620','qt')";
			}
		} else {
			dealImgPath = "update.gif";
			methodName = "showNotice('3','" + rowObject[1] + "','" + stype
					+ "','820','620','qt')";
		}
		if (appid == "1001") {
			cellvalue = "<img src=\"../../images/button/" + dealImgPath + "\" onClick=\"" + methodName + "\"/>";
		} else {
			cellvalue = "<img src=\"../../images/button/lock.png\" onClick=\"alert('您没有处理消息的权限！')\"/>";
		}
	} else if (cellvalue == "xb") {
		var xbImgPath, xbMethodName;
		if (stype == "1") {
			xbImgPath = "update.gif";
			xbMethodName = "showNoticeXB('3','" + rowObject[1] + "','"
					+ stype + "','700','280')";
		} else {
			xbImgPath = "lock.png";
			xbMethodName = "alert('不是填报单位不能续报！')";
		}
		if (appid == "1001") {
			cellvalue = "<img src=\"../../images/button/" + xbImgPath + "\" onClick=\"" + xbMethodName + "\"/>";
		} else {
			cellvalue = "<img src=\"../../images/button/lock.png\" onClick=\"alert('您没有处理消息的权限！')\"/>";
		}
	}
	return cellvalue;
}

function noticeStateFormatter(cellvalue, options, rowObject) {
	if(cellvalue == rowObject[6] && rowObject[6] == "1") {
		cellvalue = "未接收";
	} else if(cellvalue == rowObject[6] && rowObject[6] == "2") {
		cellvalue = "已签收";
	} else if(cellvalue == rowObject[6] && rowObject[6] == "3") {
		cellvalue = "已处理";
	} 
	
	if(cellvalue == rowObject[7] && rowObject[7] == "1") {
		cellvalue = "发送";
	} else if(cellvalue == rowObject[7] && rowObject[7] == "2") {
		cellvalue = "接收";
	} else if(cellvalue == rowObject[7] && rowObject[7] == "3") {
		cellvalue = "抄送";
	} 
	
	return cellvalue;
}
//Modified by Liuwx
function showMoreConditionAll(){
	if(jQuery('#moreConditionTr_all').is(":visible")){
		jQuery('#defalutTitleTr_all').show();
		jQuery('#moreConditionTr_all').hide();
        jQuery('#simpleButtomTd_all').show();
        jQuery('#moreButtomTr_all').hide();
		jQuery('#jqbt_lable_all').hide();
		jQuery('#info_title_all').hide();
		jQuery('#xxlx_lable_all').hide();
		jQuery('#info_type_all').hide();
//		jQuery('#moreLable_all').html("高级查询");
		clearElementValueAll();
	}else{
		jQuery('#defalutTitleTr_all').hide();
		jQuery('#moreConditionTr_all').show();
        jQuery('#simpleButtomTd_all').hide();
        jQuery('#moreButtomTr_all').show();
		jQuery('#jqbt_lable_all').show();
		jQuery('#info_title_all').show();
		jQuery('#xxlx_lable_all').show();
		jQuery('#info_type_all').show();
//		jQuery('#moreLable_all').html("简单查询");
	}
}

function clearElementValueAll(){
	jQuery("#jgmc_all").val("");
	jQuery('#info_title_all').val("");
	jQuery('#info_type_all').get(0).selectedIndex = 0;
}
//Modification finished