SearchPanel = Class.create();

SearchPanel.prototype = 
{
	wholeDiv : null,
	
	initialize : function(container)
	{
        this.container = container;
        wholeDiv = this.createWholeDiv(container);
        this.initFill(wholeDiv);
        this.currentTimeoutAction = null;
	},
	
	createWholeDiv : function(obj)
	{
		var tempdiv = Util.createDiv('SearchPanelDiv');
		tempdiv.style.position="absolute";
		tempdiv.style.top = obj.style.pixelTop + 24;
		tempdiv.style.left = obj.style.pixelLeft;
		this.divleft = tempdiv.style.left;
		tempdiv.style.width = 396;
		tempdiv.style.height = 254;
		tempdiv.style.backgroundColor = "#0A9AF3";
		tempdiv.style.zIndex = navControl.navToolDiv.style.zIndex + 1;
		obj.appendChild(tempdiv);

		var imgdiv = Util.createDiv('ImageButtonDiv');
		imgdiv.style.position="absolute";
		imgdiv.style.top = obj.style.pixelTop + 24;
		imgdiv.style.left = obj.style.pixelLeft;
		this.divleft = imgdiv.style.left;
		imgdiv.style.zIndex = navControl.navToolDiv.style.zIndex + 1;
		var image =	Util.createImg("ps", 0, 0, 12, 12, ImageBaseDir + "button/btnShow.gif");	
		image.title = "显示检索面板";
		image.style.cursor ="pointer";
		Event.observe(image, "click", showSerachDiv.bindAsEventListener(image));		
		imgdiv.appendChild(image);
		imgdiv.style.display = "none";
		
		obj.appendChild(imgdiv);

		var timact = null;

		function showSerachDiv()
		{
			tempdiv.style.display = "";
			imgdiv.style.display = "none";
			clearTimeout(timact);
			if(tempdiv.style.pixelLeft < obj.style.pixelLeft - 5)
			{
				tempdiv.style.pixelLeft += 10;
				timact = setTimeout(showSerachDiv,10);
			}	
		}
				
		return tempdiv;
	},
	
	hideSerachDiv : function()
	{
		var objSearch = document.getElementById("SearchPanelDiv");
		var objImage = document.getElementById("ImageButtonDiv");
		
		objSearch.style.display = "none";
		objImage.style.display = "";
	},
	
	closeSerachDiv : function()
	{
		var objLabel = document.getElementById("SearchPanelDiv");
		var objImage = document.getElementById("ImageButtonDiv");
		
 		if (objLabel){
		    objLabel.parentNode.removeChild(objLabel);
		}
		if (objImage){
		    objImage.parentNode.removeChild(objImage);
		}
	},

	initFill : function(obj)
	{
		var content = '<div align="center" style="border:1px solid #97BECE;width:100%">\n';
		content += '<table style="width:100%">\n';
		content += '<td style="width:40%">\n';
		content += '<input style="width:100%;font-size:12px" type="text" id="SEARCHKEY" name="SEARCHKEY" value="" maxlength="10" onkeyup="SearchPanel.prototype.dotxtlist(this);"/>\n';
		content += '</td>\n';
		content += '<td style="width:40%">\n';
		content += '<div>\n';
		content += '<table style="width:100%">\n';
		content += '<tr>\n';
		content += '<td style="width:10%">\n';
		content += '<input style="width:100%" type="radio" id="CHECKTYPE" name="CHECKTYPE" checked onclick="SearchPanel.prototype.showGeneral()"/>\n';
		content += '</td>\n';
		content += '<td style="width:30%;font-size:12px;text-align:left">\n';
		content += '通用检索\n';
		content += '</td>\n';
		content += '<td style="width:10%">\n';
		content += '<input style="width:100%" type="radio" id="CHECKTYPE" name="CHECKTYPE" onclick="SearchPanel.prototype.showProfessional()"/>\n';
		content += '</td>\n'
		content += '<td style="width:30%;font-size:12px;text-align:left">\n';
		content += '分类检索\n';
		content += '</td>\n';
		content += '</tr>\n';
		content += '</table>\n';
		content += '</div>\n';
		content += '</td>\n';
		content += '<td style="width:18%">\n';
		content += '<input type="image" style="width:47;height:17" src="' + ImageBaseDir + 'button/btnQuery.gif" onclick="SearchPanel.prototype.search()" />\n';
		content += '<input type="hidden" id="sql" name="sql" value="" />\n';
		content += '<input type="hidden" id="gsql" name="gsql" value="" />\n';
		content += '<input type="hidden" id="searchtype" name="searchtype" value="g"/>\n';
		content += '</td>\n';
		content += '<td style="width:2%;" valign="top" align="right">\n';
		content += '<input type="image" style="width:12;height:12" src="' + ImageBaseDir + 'button/btnClose.gif" title="关闭检索面板" onclick="SearchPanel.prototype.closeSerachDiv()" />\n';
		content += '<input type="image" style="width:12;height:12" src="' + ImageBaseDir + 'button/btnHide.gif" title="隐藏检索面板" onclick="SearchPanel.prototype.hideSerachDiv()" />\n';
		content += '</td>\n';
		content += '</tr>\n';
		content += '</table>\n';
		content += '</div>\n';
		
		content += '<div id="profdiv" name="profdiv" style="width:100%;height:220px;display:none;background-color:white">\n';
		content += '<div id="ntdiv" name="ntdiv" style="border:1px solid #97BECE;float:left;width:30%;height:100%;overflow:scroll"></div>\n';
		content += '<div style="border:1px solid #97BECE;float:right;width:70%;height:100%">\n';
		content += '<div id="searchResult" name="searchResult"></div>\n';
		content += '<div id="pageData" name="pageData"></div>\n';
		content += '<div id="pageNav" name="pageNav"></div>\n';
		content += '</div>\n';
		content += '</div>\n';
		content += '<div id="generaldiv" name="generaldiv" align="center" style="width:100%;height:220px;background-color:white">\n';
		content += '<div style="border:1px solid #97BECE;float:right;width:100%;height:100%">\n';
		content += '<div id="gsearchResult" name="gsearchResult"></div>\n';
		content += '<div id="gpageData" name="gpageData"></div>\n';
		content += '<div id="gpageNav" name="gpageNav"></div>\n';
		content += '</div>\n';
		content += '</div>\n';

		obj.innerHTML = content;
		var url = "webgis.MapSearch.getSearchTypes.d";
	   	url = encodeURI(url);
	   	new Ajax.Request(url,
		{
			method: 'post', 
			parameters: '', 
			onComplete: function(xmlHttp)
			{
				var xmlDoc = xmlHttp.responseXML;
				if(xmlDoc)
				{
					var types = xmlDoc.getElementsByTagName("typeinfo");
					if(types != null && types.length > 0)
					{
						var ntliststr = "<table style=\"width:100%\">";
						for(var i = 0 ; i < types.length ; i ++)
						{
							var type = types[i].childNodes;
							var ntk = type[0].text;
							var title = type[1].text;
							ntliststr += "<tr style=\"width:100%;heigh:18px;font-size:12px\"><td valign=top>";
							ntliststr += "<input type=\"checkbox\" id=\"NAMENTYPENKEY\" name=\"NAMENTYPENKEY\" value=\"" + ntk + "\" />";
							ntliststr += title;
							ntliststr += "</td></tr>";
						}
						ntliststr += "</table>";
						document.getElementById("ntdiv").innerHTML = ntliststr;
					}
				}
			}
		});
	},
	
	/**
	*@函数说明：禁止页面对象中输入非中文或者非字母的内容，监控事件，在事件发生500ms后触发showtxtlist方法
	*@函数参数：obj：页面对象
	*/
	dotxtlist : function(obj)
	{
		obj.value = obj.value.replace(/[^\u4e00-\u9fa5|^\a-zA-Z|]|\||\^|/g,'');
		clearTimeout(this.currentTimeoutAction);
		
		if(document.getElementById("searchtype").value == "g")
		{
			this.currentTimeoutAction = setTimeout(this.showtxtlist,500);
		}
		
	},
	
	/**
	*@函数说明：在ID为“SEARCHKEY”的页面对象下方生成div，并填充预选项列表
	*/
	showtxtlist : function()
	{
		var obj = document.getElementById("SEARCHKEY");
		var sdivs = wholeDiv.getClientRects();
		//var rects = obj.getClientRects();
		var currenttxt = Util.trim(obj.value);
		if(currenttxt != "")
		{
			var tempdiv = document.getElementById("preDIV");
			if(tempdiv)
			{
				wholeDiv.removeChild(tempdiv);
			}
			var rects = obj.getClientRects();
			tempdiv = document.createElement("<DIV id=\"preDIV\" name=\"preDIV\">");
			tempdiv.style.position="absolute";
			tempdiv.style.top=(rects[0].bottom -sdivs[0].top) + "px";
			tempdiv.style.left=(rects[0].left - sdivs[0].left) + "px";
			tempdiv.style.border = "1px solid #97BECE";
			tempdiv.style.backgroundColor = "white";
			tempdiv.style.width = (rects[0].right - rects[0].left) + "px";
			tempdiv.style.zIndex = wholeDiv.style.zIndex + 1;
			var url = "webgis.MapSearch.preSearch.d";
			var params = "?currentkey=" + currenttxt;
			if(Util.isLetter(currenttxt))
			{
				params += "&keytype=en";
			}
			else if(Util.isChinese(currenttxt))
			{
				params += "&keytype=ch";
			}
			params += "&numofrows=8";
			url = encodeURI(url);
			params = encodeURI(params);
			new Ajax.Request(url,
			{
				method: 'post', 
				parameters: params, 
				onComplete: function(xmlHttp)
				{
					var xmldoc = xmlHttp.responseXML;
					if(xmldoc)
					{
						SearchPanel.prototype.fillDropdownDiv(tempdiv,xmldoc);
					}
				}
			});
		}
		else
		{
			var tempdiv = document.getElementById("preDIV");
			if(tempdiv)
			{
				wholeDiv.removeChild(tempdiv);
			}
		}
	},
	
	/**
	*@函数说明：根据xml在页面对象中填充预选项
	*@函数参数：obj：页面对象
	*@函数参数：xmldoc：xml实例
	*/
	fillDropdownDiv : function(obj,xmldoc)
	{
		var prechoices = xmldoc.getElementsByTagName("tempPre");
		if(prechoices != null && prechoices.length > 0)
		{
			var ntliststr = "<table style=\"width:100%\">";
			for(var i = 0 ; i < prechoices.length ; i ++)
			{
				var tempchoice = prechoices[i].childNodes;
				var cvalue = tempchoice[0].text;
				ntliststr += "<tr style=\"width:100%;font-size:12px\" onmouseover=\"SearchPanel.prototype.mouseover(this,'#609ca0')\" onmouseout=\"SearchPanel.prototype.mouseout(this,'#5f9ea0')\" onclick=\"SearchPanel.prototype.dochoose('" + cvalue +"')\"><td>";
				ntliststr += cvalue;
				ntliststr += "</td></tr>";
			}
			ntliststr += "<tr style=\"width:100%;font-size:12px;text-align:right\"><td><a href=\"javascript:SearchPanel.prototype.closeDList()\">关闭</a></td></tr>"
			ntliststr += "</table>";
			obj.innerHTML = ntliststr;
		}
		wholeDiv.appendChild(obj);
	},
	
	/**
	*@函数说明：在id为“SEARCHKEY”的文本框中填入字符串
	*@函数参数：str：字符串
	*/
	dochoose : function(str)
	{
		document.getElementById("SEARCHKEY").value = str;
		this.closeDList();
	},

	/**
	*@函数说明：移除名为“preDIV”的页面对象
	*/
	closeDList : function()
	{
		var tempdiv = document.getElementById("preDIV");
		if(tempdiv)
		{
			wholeDiv.removeChild(tempdiv);
		}
	},
	
	mouseover : function(obj,color)
	{
		obj.style.cursor = "hand";
		if(obj.bgColor != color && obj.bgColor != "#9ece6e")
		{
			obj.bgColor = "#609ca0"; 
		}
	},
	
	mouseout : function(obj,color)
	{
		if(obj.bgColor != "#9ece6e")
		{
	   		obj.bgColor = "#FFFFFF";
	   	}
	},
	
	search : function()
	{
		var searchtype = document.getElementById("searchtype").value;
		if(searchtype == "p")
		{
			this.doProfSearch();
		}
		else
		{
			this.doGeneralSearch();
		}
	},
	
	doGeneralSearch : function()
	{
		var searchkey = document.getElementById("SEARCHKEY").value;
		var url = "webgis.MapSearch.genSql.d";
		var params = "?searchkey=" + searchkey;
		params += "&searchtype=" + document.getElementById("searchtype").value;
		url = encodeURI(url);
		params = encodeURI(params);
		new Ajax.Request(url,
		{
			method: 'post', 
			parameters: params, 
			onComplete: function(xmlHttp)
			{
				var sql = xmlHttp.responseText;
				PageCtrl.initPage("gsearchResult","gpageData","gpageNav",convertSql(sql),"SearchPanel.prototype.showGList",10);
			}
		});
	},
	
	showGList : function(xmlDoc)
	{
		var tstr = "<table id=\"tleList\" width=100% cellSpacing=0 cellPadding=0 style=\"border:1px solid #97BECE;\">";
		//生成表头
		tstr += "<tr bordercolor=\"black\">";
		tstr += "<th style=\"align:center;font-size:12px;width:100%;background-color:#b4c1e4\">地点名称</th>";
		tstr += "</tr>";
		
		var results = xmlDoc.getElementsByTagName("row");
		for(var i = 0 ; i < results.length; i++)
		{
			var rowResult = results[i].childNodes;
			var locName = rowResult[0].firstChild;
			if(locName != null)
			{
				locName = locName.nodeValue;
			}
			var longitude = rowResult[1].firstChild;
			if(longitude != null)
			{
				longitude = longitude.nodeValue;
			}
			var latitude = rowResult[2].firstChild;
			if(latitude != null)
			{
				latitude = latitude.nodeValue;
			}
			tstr += "<tr style=\"font-size:12px\" onmouseover=\"SearchPanel.prototype.mouseover(this,'#609ca0')\" onmouseout=\"SearchPanel.prototype.mouseout(this,'#5f9ea0')\">";
		    tstr += "<td style=\"border-left: 1px solid #CCCCCC;border-top: 1px solid #CCCCCC;padding: 1px;\" align=\"center\" onclick=\"SearchPanel.prototype.doGGISshow(this,'#9ece6e','" + longitude + "','" + latitude + "');\">" + locName + "</td>";									    					    
		    tstr += "</tr>";
		}
		tstr += "</table>";
		document.getElementById("gpageData").innerHTML = tstr;
	},
	
	doGGISshow : function(obj,color,longitude,latitude)
	{
		var currentLoc = obj.innerText;
		if(obj.bgColor != color){
			 obj.bgColor = color; 
		   }
		var ctX = parseFloat(longitude) * 1e16;
		var ctY = parseFloat(latitude) * 1e16;
		var ctPt = new Point(ctX,ctY);
		var coord = new Coordinate(ctX,ctY);
		map.moveToCenter(ctPt,MaxZoomLevel);
		var spoint = Util.getScreenPixel(coord,map.getMapModel().getZoom());
		MapTips.prototype.showPopup(map.getVMLDiv(),currentLoc,"",spoint.x,spoint.y);
	},
	
	/**
	*@函数说明：隐藏分类检索div，显示通用检索div
	*/
	showGeneral : function()
	{
		document.getElementById("profdiv").style.display = "none";
		var obj = document.getElementById("generaldiv");
		if(obj.style.display == "none")
		{
			obj.style.display = "inline";
		}
		document.getElementById("searchtype").value = "g";
	},

	/**
	*@函数说明：隐藏通用检索div，显示分类检索div
	*/
	showProfessional : function()
	{
		document.getElementById("generaldiv").style.display = "none";
		var obj = document.getElementById("profdiv");
		if(obj.style.display == "none")
		{
			obj.style.display = "inline";
		}
		document.getElementById("searchtype").value = "p";
	},
	
	doProfSearch : function()
	{
		var ntk = document.getElementsByName("NAMENTYPENKEY");
		var ntkstr = "";
		for(var i = 0 ; i < ntk.length ; i ++)
		{
			if(ntk[i].checked)
			{
				ntkstr += ntk[i].value;
				ntkstr += ";";
			}
		}
		ntkstr = ntkstr.substr(0,ntkstr.length-1);
		if(ntkstr == "")
		{
			alert("请先选择搜索类型！");
			return false;
		}
		var searchkey = Util.trim(document.getElementById("SEARCHKEY").value);
		if(searchkey == "")
		{
			alert("请输入地点名称！");
			return false;
		}
		
		var url = "webgis.MapSearch.genSql.d";
		var params = "?ntk=" + ntkstr;
		params += "&searchkey=" + searchkey;
		params += "&searchtype=" + document.getElementById("searchtype").value;
		url = encodeURI(url);
		params = encodeURI(params);
		new Ajax.Request(url,
		{
			method: 'post', 
			parameters: params, 
			onComplete: function(xmlHttp)
			{
				var sql = xmlHttp.responseText;
				PageCtrl.initPage("searchResult","pageData","pageNav",convertSql(sql),"SearchPanel.prototype.showList",10,sdedatasource);
			}
		});
	},
	
	showList : function(xmlDoc)
	{
		var tstr = "<table id=\"tleList\" width=100% cellSpacing=0 cellPadding=0 style=\"border:1px solid #97BECE;\">";
		//生成表头
		tstr += "<tr bordercolor=\"black\">";
		tstr += "<th style=\"align:center;font-size:12px;width:100%;background-color:#b4c1e4\">地点名称</th>";
		tstr += "</tr>";
		
		var results = xmlDoc.getElementsByTagName("row");
		for (var i = 0; i < results.length; i++)
		{
			var rowResult = results[i].childNodes;
			var locName = rowResult[0].firstChild;
			if(locName != null)
			{
				locName = locName.nodeValue;
			}			
			var locId = rowResult[1].firstChild;
			if(locId != null)
			{
				locId = locId.nodeValue;
			}
			var layerName = rowResult[2].firstChild;
			if(layerName != null)
			{
				layerName = layerName.nodeValue;
			}
			var layerType = rowResult[3].firstChild;
			if(layerType != null)
			{
				layerType = layerType.nodeValue;
			}
			var layerKeyFld = rowResult[4].firstChild;
			if(layerKeyFld != null)
			{
				layerKeyFld = layerKeyFld.nodeValue;
			}
			tstr += "<tr style=\"font-size:12px\" onmouseover=\"SearchPanel.prototype.mouseover(this,'#609ca0')\" onmouseout=\"SearchPanel.prototype.mouseout(this,'#5f9ea0')\">";
		    tstr += "<td style=\"border-left: 1px solid #CCCCCC;border-top: 1px solid #CCCCCC;padding: 1px;\" align=\"center\"  onclick=\"SearchPanel.prototype.doGISshow(this,'#9ece6e','" + locId + "','" + layerType + "','" + layerName + "','" + layerKeyFld + "');\">" + locName + "</td>";									    					    
		    tstr += "</tr>";						
		}
		tstr += "</table>";
		document.getElementById("pageData").innerHTML = tstr;
		
	},
	
	doGISshow : function(obj,color,id,type,layer,keyname)
	{
		var currentLoc = obj.innerText;
		if(obj.bgColor != color){
			 obj.bgColor = color; 
		   }
		var url = "webgis.MapSearch.gISShow.d";
		var params = "?layername=" + layer;
		params += "&keyid=" + id;
		params += "&layertype=" + type;
		params += "&keyname=" + keyname;
		url = encodeURI(url);
		params = encodeURI(params);
		new Ajax.Request(url,
		{
			method: 'post', 
			parameters: params, 
			onComplete: function(xmlHttp)
			{
				var result = xmlHttp.responseText;
				result = result.split(";");
				var zlvl = result[0];
				var ctX = parseFloat(result[1].split(",")[0]) * 1e16;
				var ctY = parseFloat(result[1].split(",")[1]) * 1e16;
				var ctPt = new Point(ctX,ctY);
				var llx = parseFloat(result[1].split(",")[0]);
				var lly = parseFloat(result[1].split(",")[1]);
				var coord = new Coordinate(ctX,ctY);
				map.moveToCenter(ctPt,zlvl);
				var spoint = Util.getScreenPixel(coord,map.getMapModel().getZoom());
				MapTips.prototype.showPopup(map.getVMLDiv(),currentLoc,"",spoint.x,spoint.y);
			}
		});
		
	}
	
};