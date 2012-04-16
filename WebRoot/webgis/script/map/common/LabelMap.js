
LabelMap = Class.create();
LabelMap.prototype =
{
	/**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container)
    {
        this.xmlDoc = null;
        this.currentLabel = new Array();
        this.actionname = "";
    },
    
    /**
	 * 构建标签编辑功能面板.
	 */
 	outputHTML: function() 
 	{
 		htmlTxt = '\
   		<div style="width:100%" id="labeldiv" name="labeldiv" style="offsetTop:0px">\
   			<form id="lbform" name="lbform">\
				<table>\
						<tr height="5">\
							<td colspan="4"></td>\
						</tr>\
						<tr>\
							<td style="width:80x;text-align:right;font-size:12px">标签列表：</td>\
							<td style="width:100px" id="LTD" name="LTD">\
								<select style="width:100%;font-size:12px" id="LABELS" name="LABELS">\
									<option value="">请选择...</option>\
								</select>\
							</td>\
							<td style="width:20px;text-align:right">\
								<input style="font-size:12px" type="button" id="ADDLABEL" name="ADDLABEL" value="添加标签" onclick="LabelMap.prototype.addlabel();" readOnly="true"/>\
							</td>\
						</tr>\
						<tr>\
							<td style="width:80px;text-align:right;font-size:12px">标签名：</td>\
							<td style="width:100px">\
								<input style="width:100%;font-size:12px" type="text" id="LABELTITLE" name="LABELTITLE" readonly="true" />\
							</td>\
							<td style="width:20px;text-align:right">\
								<input style="font-size:12px" type="button" id="EDITLABEL" name="EDITLABEL" value="编辑标签" onclick="LabelMap.prototype.editlabel();" disabled="true"/>\
							</td>\
						</tr>\
						<tr>\
							<td style="width:80px;text-align:right;font-size:12px">X坐标：</td>\
							<td style="width:100px">\
								<input style="width:100%;font-size:12px" type="text" id="LABELX" name="LABELX" readonly="true" />\
							</td>\
							<td style="width:20px;text-align:right">\
								<input style="font-size:12px" type="button" id="DELETELABEL" name="DELETELABEL" value="删除标签" onclick="LabelMap.prototype.deletelabel();" disabled="true"/>\
							</td>\
						</tr>\
						<tr>\
							<td style="width:80px;text-align:right;font-size:12px">Y坐标：</td>\
							<td style="width:100px">\
								<input style="width:100%;font-size:12px" type="text" id="LABELY" name="LABELY" readonly="true" />\
							</td>\
							<td style="width:20px;text-align:right">\
								<input type="hidden" id="LABELLEVEL" name="LABELLEVEL" value="" />\
								<input style="font-size:12px" type="button" id="PRESERVELABEL" name="PRESERVELABEL" value="保存标签" onclick="LabelMap.prototype.preservelabel();" disabled="true"/>\
							</td>\
						</tr>\
				</table>\
			</form>\
		</div>';
	  return htmlTxt;
 	},
	
	/**
	 * 标签保存按钮对应功能函数.
	 */
	preservelabel: function()
	{
		if(labelMap.actionname == "adding")
		{
			this.newlabel();
		}
		if(labelMap.actionname == "editing")
		{
			this.updatelabel();
		}
	},
	/**
	 * 标签新增函数
	 */
	newlabel: function()
	{
		var labeltitle = Util.trim(document.getElementById("LABELTITLE").value);
    	if(labeltitle == "")
    	{
    		alert("请填写标签名！");
    		return false;
    	}
    	if(!Util.isNormalText(labeltitle))
    	{
    		alert("标签名中含有特殊字符！");
    		return false;
    	}
    	var labelx = Util.trim(document.getElementById("LABELX").value);
    	var labely = Util.trim(document.getElementById("LABELY").value);
    	if(labelx == "" || labely == "")
    	{
    		alert("错误的标签位置！");
    		return false;
    	}
    	var labellevel = document.getElementById("LABELLEVEL").value;
    	var url = "webgis.MapLabel.newLabel.d";
    	params = "?labeltitle=" + labeltitle;
    	params += "&labelx=" + labelx;
    	params += "&labely=" + labely;
    	params += "&labellevel=" + labellevel;
    	url = encodeURI(url);
		params = encodeURI(params);
		new Ajax.Request(url,
		{
			method: 'post', 
			parameters: params, 
			onComplete: function(xmlHttp)
			{
				var text = xmlHttp.responseText;
				if(text == "true")
				{
					document.getElementById("LABELTITLE").value = "";
					document.getElementById("LABELX").value = "";
					document.getElementById("LABELY").value = "";
					document.getElementById("LABELLEVEL").value = "";
					document.getElementById("PRESERVELABEL").disabled = true;
					document.getElementById("LABELTITLE").readOnly = true;
					alert("地图标签录入成功");
					LabelTool.prototype.getLabels();
					LabelMap.prototype.getLabels();
					labelMap.actionname = "";
				}
				else if(text == "false")
				{
					alert("数据录入失败");
				}
				else if(text == "dupe")
				{
					alert("已有同名标签存在！请更改标签名！");
				}
				else if(text == "error")
				{
					alert("异常错误！数据录入失败！");
				}
			}
		});
	},
	/**
	 * 标签更新函数
	 */
	updatelabel: function()
	{
		var labeltitle = Util.trim(document.getElementById("LABELTITLE").value);
    	if(labeltitle == "")
    	{
    		alert("请填写标签名！");
    		return false;
    	}
    	if(!Util.isNormalText(labeltitle))
    	{
    		alert("标签名中含有特殊字符！");
    		return false;
    	}
    	var labelx = Util.trim(document.getElementById("LABELX").value);
    	var labely = Util.trim(document.getElementById("LABELY").value);
    	if(labelx == "" || labely == "")
    	{
    		alert("错误的标签位置！");
    		return false;
    	}
    	var labellevel = document.getElementById("LABELLEVEL").value;
    	var url = "webgis.MapLabel.updateLabel.d";
    	params = "?labeltitle=" + labeltitle;
    	params += "&labelx=" + labelx;
    	params += "&labely=" + labely;
    	params += "&labellevel=" + labellevel;
    	params += "&labelid=" + document.getElementById("LABELS").value;
    	url = encodeURI(url);
		params = encodeURI(params);
		new Ajax.Request(url,
		{
			method: 'post', 
			parameters: params, 
			onComplete: function(xmlHttp)
			{
				var text = xmlHttp.responseText;
				if(text == "true")
				{
					document.getElementById("LABELTITLE").value = "";
					document.getElementById("LABELX").value = "";
					document.getElementById("LABELY").value = "";
					document.getElementById("LABELLEVEL").value = "";
					document.getElementById("PRESERVELABEL").disabled = true;
					alert("地图标签更新成功");
					document.getElementById("LABELTITLE").readOnly = true;
					LabelTool.prototype.getLabels();
					LabelMap.prototype.getLabels();
					labelMap.actionname = "";
				}
				else if(text == "false")
				{
					alert("数据更新失败");
				}
				else if(text == "error")
				{
					alert("异常错误！数据更新失败！");
				}
			}
		});
	},
	/**
	 * 获取当前用户所属机构的标签
	 */
	getLabels: function()
    {
    	var url = "webgis.MapLabel.getLabels.d";
    	url = encodeURI(url);
    	new Ajax.Request(url,
		{
			method: 'post', 
			parameters: '', 
			onComplete: function(xmlHttp)
			{
				var text = xmlHttp.responseText;
				if(text != "null")
				{
					var xmlDoc = xmlHttp.responseXML;
					LabelMap.prototype.showLabelList(xmlDoc);
				}
			}
		});
    },
    /**
	 * 将查到的标签填入页面下拉列表中
	 */
    showLabelList: function(xmlDoc)
    {
    	var lbls = xmlDoc.getElementsByTagName("lblinfo");
		if(lbls != null && lbls.length > 0)
		{
			var lbltd = document.getElementById("LTD");
			var lblsel = "<select style=\"width:100%;font-size:12px\" id=\"LABELS\" name=\"LABELS\" onchange=\"LabelMap.prototype.getLabelInfo(this);\">";
			lblsel += "<option value=\"\">请选择...</option>";
			lblsel += "</select>";
			lbltd.innerHTML = lblsel;
			var sel = document.getElementById("LABELS");
			for(var i = 0 ; i < lbls.length ; i++)
			{
				var lbl = lbls[i].childNodes;
				var id = lbl[0].text;		
				var title = lbl[1].text;
				var tempOption = document.createElement("OPTION");
				sel.options.add(tempOption);
				tempOption.innerText = title;
				tempOption.value = id;
			}
			return null;
		}
		return null;
    },
    /**
	 * 获取单个标签的信息
	 */
    getLabelInfo: function(obj)
    {
    	labelMap.actionname = "";
    	document.getElementById("PRESERVELABEL").disabled = true;
    	var index = obj.options.selectedIndex;
    	if(index == 0)
		{
			document.getElementById("EDITLABEL").disabled = true;
			document.getElementById("DELETELABEL").disabled = true;
			document.getElementById("LABELTITLE").value = "";
			document.getElementById("LABELX").value = "";
			document.getElementById("LABELY").value = "";
			document.getElementById("LABELLEVEL").value = "";
			document.getElementById("LABELTITLE").readOnly = true;
			return null;
		}
		else
		{
			var id = obj.options[index].value;
			var url = "webgis.MapLabel.getLabelInfoById.d";
			var params = "?labelid=" + id;
			url = encodeURI(url);
			params = encodeURI(params);
			new Ajax.Request(url,
			{
				method: 'post', 
				parameters: params, 
				onComplete: function(xmlHttp)
				{
					var text = xmlHttp.responseText;
					if(text != "null")
					{
						document.getElementById("EDITLABEL").disabled = false;
						document.getElementById("DELETELABEL").disabled = false;
						var xmlDoc = xmlHttp.responseXML;
						LabelMap.prototype.showSingleLabel(xmlDoc);
					}
				}
			});
			return null;
		}
    },
    /**
	 * 地图居中缩放到单个标签所在位置
	 */
    showSingleLabel: function(xmlDoc)
    {
    	var lblinfo = xmlDoc.getElementsByTagName("lblinfo")[0].childNodes;
    	var lbltitle = lblinfo[0].text;	
		var lblx = lblinfo[1].text;
		var lbly = lblinfo[2].text;
		var lbllevel = lblinfo[3].text;
		document.getElementById("LABELTITLE").value = lbltitle;
		document.getElementById("LABELX").value = lblx;
		document.getElementById("LABELY").value = lbly;
		var ctX = parseFloat(lblx) * 1e16;
		var ctY = parseFloat(lbly) * 1e16;
		var ctPt = new Point(ctX,ctY);
		map.moveToCenter(ctPt,parseFloat(lbllevel));
    },
    /**
	 * “添加标签”按钮触发函数
	 */
	addlabel: function()
	{
		toolbar.clearCurrentToolStatus();
		toolbar.currentTool = null;
		document.getElementById("LABELS").value = "";
		document.getElementById("DELETELABEL").disabled = true;
		document.getElementById("EDITLABEL").disabled = true;
		document.getElementById("LABELTITLE").readOnly = false;
		document.getElementById("LABELTITLE").value = "";
   		var mapDiv = toolbar.mapDiv;
		var mapModel = map.getMapModel();
		mapDiv.style.cursor = "pointer";
		labelMap.actionname = "adding";
		Event.observe(mapDiv, "click", LabelMap.prototype.dolabel.bindAsEventListener());
	},
	/**
	 * “编辑标签”按钮触发函数
	 */
	editlabel: function()
	{
		toolbar.clearCurrentToolStatus();
		toolbar.currentTool = null;
		labelMap.actionname = "editing";
		document.getElementById("LABELTITLE").readOnly = false;
		document.getElementById("DELETELABEL").disabled = true;
		document.getElementById("PRESERVELABEL").disabled = false;
		var mapDiv = toolbar.mapDiv;
		var mapModel = map.getMapModel();
		mapDiv.style.cursor = "pointer";
		Event.observe(mapDiv, "click", LabelMap.prototype.dolabel.bindAsEventListener());
	},
	/**
	 * “删除标签”按钮触发函数
	 */
	deletelabel: function()
	{
		if(window.confirm("确定删除当前标签吗？"))
		{
			var url = "webgis.MapLabel.deleteById.d";
			var params = "?labelid=" + document.getElementById("LABELS").value;
			url = encodeURI(url);
			params = encodeURI(params);
			new Ajax.Request(url,
			{
				method: 'post', 
				parameters: params, 
				onComplete: function(xmlHttp)
				{
					var result = xmlHttp.responseText;
					if(result == "true")
					{
						alert("标签删除成功！");
						document.getElementById("LABELTITLE").value = "";
						document.getElementById("LABELX").value = "";
						document.getElementById("LABELY").value = "";
						document.getElementById("LABELLEVEL").value = "";
						document.getElementById("EDITLABEL").disabled = true;
						document.getElementById("DELETELABEL").disabled = true;
						document.getElementById("PRESERVELABEL").disabled = true;
						LabelTool.prototype.getLabels();
						LabelMap.prototype.getLabels();
						labelMap.actionname = "";
					}
					else
					{
						alert("标签删除失败！");
					}
					
				}
			});
		}
	},
	/**
	 * 地图单击事件触发函数
	 */
	dolabel: function(e)
	{
		if(labelMap.currentLabel != null && labelMap.currentLabel.length > 0)
 		{
 			var circleToDel = labelMap.currentLabel[0];
 			circleToDel.parentNode.removeChild(circleToDel);
 			labelMap.currentLabel = new Array();
 		}
 		if((labelMap.actionname == "adding" || labelMap.actionname == "editing") && toolbar.currentTool == null)
 		{
			var mapDiv = map.getVMLDiv();
			var mapModel = map.getMapModel();
			var currentPos = Util.getMouseRelativePixel(e, mapDiv);
			var center = Util.getCoordinateByPixel({x:currentPos.x,y:currentPos.y}, mapModel.getZoom());
			document.getElementById("LABELX").value = center.x/1e16;
			document.getElementById("LABELY").value = center.y/1e16;
			document.getElementById("LABELLEVEL").value = mapModel.getZoom().getLevel();
			document.getElementById("PRESERVELABEL").disabled = false;
			var circleID = "新标签";
			var tempcircle = new Circle(circleID, center.x+","+center.y, 4, "red", 1, "red");
			var tempcircle = tempcircle.setToMap(mapDiv,mapModel);
			tempcircle.title = tempcircle.id;
			labelMap.currentLabel.push(tempcircle);		//记号的vml对象
			labelMap.currentLabel.push(circleID);		//记号ID
			labelMap.currentLabel.push(center.x+","+center.y);		//经纬度换算坐标（乘以1e16的结果）串
			setInterval(function()
            {
            	tempcircle.parentNode.removeChild(tempcircle);
            },4000);
			Event.stop(e);
		}
	}
};

