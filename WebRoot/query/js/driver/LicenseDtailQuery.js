   function checkState(checkId){
      //控制选中状态

      var checkObj = document.getElementById(checkId);
	  var checkObjList = document.getElementsByName("checkObj");
	  for(var i=0; i < checkObjList.length; i++){
	      checkObjList[i].checked = false;
	  }
	  checkObj.checked = true;
   }
	
	// 鼠标移入
	function mouseover(obj,color){ 
	   if(obj.bgColor != color){
		 obj.bgColor = "#117FC9"; 
	   }
	}
	
	 // 鼠标移出
	function mouseout(obj,color){
	   if(obj.bgColor != color.toLowerCase()){
		  obj.bgColor = "#FFFFFF"; 
	   }
	}
  
   //查询并展示驾驶证详细信息
   function showLicenseDtailInfo(SFZMHM){
	   var url = 'tira.dirverLicense.queryLicense.d';
	   url = encodeURI(url);
	   var params = "SFZMHM='" + SFZMHM+"'";
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponseLicense});
   }
//   档案编号，驾驶证号，准驾车型，原准驾车型，初次领证日期，初次发证机关，下一清分日期，下一审验日期，
//	 * 驾证期限，有效期始，有效期止，累计积分，超分日期，学习通知日期，补证次数，驾驶证状态，驾驶人来源，行政区划，发证日期，管理部门，发证机关，经办人
   function showResponseLicense(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["档案编号","驾驶证号","准驾车型"],
		               ["原准驾车型","初次领证日期","初次发证机关"],
		               ["下一清分日期","下一审验日期","驾证期限"],
		               ["有效期始","有效期止","累计积分"],
		               ["超分日期","学习通知日期","补证次数"],
		               ["驾驶证状态","驾驶人来源","行政区划"],
		               ["发证日期","管理部门","发证机关"],
		               ["经办人","　","　"]];
		var results = null;
		var names = null;
		var rowflg = true;
		
	    var str = "<table id=\"LicenseTab\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0  align='center' border='0'>";
		str += "<tr class=\"rowstyle\" >"
		str += "<td align=\"center\" width=\"100%\" height=\"20px\" style=\"font-weight: bold\"  valign=\"bottom\" colspan=\"6\">";
		str += "驾驶证详细信息";
		str += "</td>";
		str += "</tr>";
		if (rows==null || rows.length == 0) {
			str += "<tr class=\"rowstyle\" >"
			str += "<td align=\"center\" width=\"100%\" height=\"20px\" valign=\"bottom\" colspan=\"6\">";
			str += "没有驾驶证详细信息";
			str += "</td>";
			str += "</tr>";
		} else {
			for ( var i = 0; i < rows.length; i++) {
				results = rows[i].childNodes;
				names = strName[i];
				str += "<tr class=\"rowstyle\" >"
				for ( var j = 0, k = 0; j < results.length || k < names.length;) {
					if (rowflg) {
						if ("　" != names[k]) {
							names[k] += "：";
						}
						str += "<td width='13%' align='right' style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">"
								+ names[k] + "</td>";
						k++;
						rowflg = false;
					} else {
						if ("" == results[j].text) {
							results[j].text = "　";
						}
						str += "<td width='20%'>" + results[j].text + "</td>";
						j++;
						rowflg = true;
					}
				}
				str += "</tr>";
			}
		}
		str += "</table>";
		// 添加到结果面板上
		var tabObj = document.getElementById("detailData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
   function valueTdTextById(tdId,tdText){
      document.getElementById(tdId).innerHTML = tdText;
   }