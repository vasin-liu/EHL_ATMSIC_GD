	/**
	 * 函数功能:按号牌种类，号牌号码，机动车所有人对所有车辆信息进行查询
	 */
	 function vehicleInfoQuery() {
		 var hpzl = document.getElementById("HPZL");
		 var hphm = document.getElementById("HPHM");
	     showMsg();
		 var jdcsyr = document.getElementById("JDCSYR");
		 var url = 'tira.vehicleQuery.vehicleInfoQuery.d';
		 url = encodeURI(url);
		 var params = "HPZL=" + hpzl.value +"&HPHM=" + hphm.value + "&JDCSYR=" + jdcsyr.value;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:page});
	 }
		/**
		 * 函数功能:实现分页
		 */
	 function page(xmlHttp){
		   var xmlDoc = xmlHttp.responseXML;
		   var rows = xmlDoc.getElementsByTagName("row");
		   var results = rows[0].childNodes;
		   var pageSQL = results[0].text;
		   var dbSource = results[1].text;
		   	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(pageSQL),"showQueryResponse","13",dbSource);
		 }
 
	 function showQueryResponse(xmlDoc){
	   var rows = xmlDoc.getElementsByTagName("row");
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += " <td width='14%'  align='center'>机动车序号</td> \
                 <td width='10%' align='center'>号牌种类</td> \
                 <td width='10%' align='center'>号牌号码</td> \
                 <td width='10%' align='center'>中文品牌</td> \
                 <td width='13%' align='center'>车辆类型</td> \
                 <td width='8%' align='center'>车身颜色</td> \
                 <td width='35%' align='center'>机动车所有人</td>";
	   	str += "</tr>";
	   	
	   	// 表格数据显示
	   	var j=0;
	   	var results = null;
	   	for (var i = 0; i < rows.length; i++){
	     	results = rows[i].childNodes;
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
					onclick=\"vehicleDetails("+ results[0].text +")\">";

	     	for(j=0;j<results.length-1;j++){
	     	    if(results[j].text == "null"){
	     	        results[j].text = "&nbsp";
	     	    }
			    str += "<td >" + results[j].text + "</td>";
	     	}
		    str += "</tr>";
		}	

		str += "</table>";
	   // 添加到结果面板上
		var tabObj = document.getElementById("pageData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
 
   function checkState(checkId){
      // 控制选中状态

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
	/**********************************车辆详细信息查询*********************************************************/
	 // 跳转到车辆详细信息
	function vehicleDetails(xh){
	 	var left = (screen.availWidth-800)/2;
	    var top = (screen.availHeight-590)/2;
		window.open("VehicleDetailQuery.jsp?XH=" + xh,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	}
	 // 车辆详细信息查询
	function vehicleDetailsQuery(xh){
	     showMsg();
		 var url = 'tira.vehicleQuery.VehicleDetailInfoQuery.d';
		 url = encodeURI(url);
		 var params = "XH=" + xh;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showDetailResponse});
		
	}
	 
	 function showDetailResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["机动车序号", "号牌种类", "号牌号码"],
		               ["中文品牌", "英文品牌", "车辆型号"],
		               ["国产进口", "制造国", "制造厂名称"],
		               ["发动机号", "车辆类型", "车身颜色"],
		               ["使用性质", "机动车所有人", "身份证明名称"],
		               ["身份证明号码", "住所行政区划", "住所详细地址"],
		               ["住所邮政编码", "联系电话", "所有权"],
		               ["初次登记日期", "最近定检日期", "检验有效期止"],
		               ["强制报废期止", "发证机关", "管理部门"],
		               ["发牌日期", "发行驶证日期", "发登记证书日期"],
		               ["发合格证日期", "保险终止日期", "补领号牌次数"],
		               ["补领行驶证次数", "补换领证书次数", "登记证书编号"],
		               ["制登记证书行数", "档案编号", "管理辖区"],
		               ["机动车状态", "是否抵押", "经办人"],
		               ["业务类别", "注册流水号", "发动机型号"],
		               ["燃料种类", "排量", "功率"],
		               ["转向形式", "出厂日期", "获得方式"],
		               ["来历凭证1", "凭证编号1", "　"]];
		var results = null;
		var names = null;
		var rowflg = true;

	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=97% cellSpacing=0 cellPadding=0  align='center' >";
		     	str += "<tr class=\"rowstyle\" >"
			    str += "<td align=\"center\" width=\"100%\" style=\"font-weight: bold\" height=\"20px\" valign=\"bottom\" colspan=\"6\">";
			    str += "车辆详细信息";
			    str += "</td>";
			    str += "</tr>";
			for ( var i = 0; i < rows.length; i++) {
				results = rows[i].childNodes;
				names = strName[i];
				str += "<tr class=\"rowstyle\" >"
				for ( var j = 0, k = 0; j < results.length || k < names.length;) {
					if (rowflg) {
						if ("　" != names[k]) {
							names[k] += "：";
						}
						str += "<td width='15%' align='right' style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">"
								+ names[k] + "</td>";
						k++;
						rowflg = false;
					} else {
						if ("" == results[j].text) {
							results[j].text = "　";
						}
						str += "<td width='18%'>" + results[j].text + "</td>";
						j++;
						rowflg = true;
					}
				}
				str += "</tr>";
			}
			str += "</table>";
	   // 添加到结果面板上
		var tabObj = document.getElementById("detailData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}

	 /** *************************************违法相关车辆信息*********************************************************** */

	 // 违法相关车辆信息
	 function vehicleLinkQuery(hpzl,hphm){
	     showMsg();
		 var url = 'tira.vehicleQuery.vehicleLinkInfoQuery.d';
		 url = encodeURI(url);
		 var params = "HPZL=" + hpzl +"&HPHM=" +hphm;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showvehicleLinkQueryResponse});
	 }

	 function showvehicleLinkQueryResponse(xmlHttp){
		 var xmlDoc = xmlHttp.responseXML;
		 var rows = xmlDoc.getElementsByTagName("row");
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += " <td width='15%' align='center'>机动车序号</td> \
                 <td width='10%' align='center'>号牌种类</td> \
                 <td width='10%' align='center'>号牌号码</td> \
                 <td width='10%' align='center'>中文品牌</td> \
                 <td width='15%' align='center'>车辆类型</td> \
                 <td width='10%' align='center'>车身颜色</td> \
                 <td width='30%' align='center'>机动车所有人</td>";
	   	str += "</tr>";
	   	// 表格数据显示
     	var results = null;
	   	for (var i = 0; i < rows.length; i++){
	     	results = rows[i].childNodes;
	     	if(results[0].text.substring(0,results[0].text.indexOf("=")) == "没有号牌种类"){
			str += "<tr>";
		    str += "<td colspan='7'>" + results[0].text + "</td>";
		    str += "</tr>";
	     	}else if(results[0].text.substring(0,results[0].text.indexOf("=")) == "号牌种类"){
	    			str += "<tr>";
	    		    str += "<td colspan='7'>" + results[0].text + "</td>";
	    		    str += "</tr>";
	     	}else{
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
					onclick=\"vehicleLinkDetailsQuery('" + results[7].text +"','" + results[2].text +"')\">";
		    str += "<td >" + results[0].text + "</td>";
		    str += "<td >" + results[1].text + "</td>";
		    str += "<td >" + results[2].text + "</td>";
		    str += "<td >" + results[3].text + "</td>";
		    str += "<td >" + results[4].text + "</td>";
		    str += "<td >" + results[5].text + "</td>";
		    str += "<td >" + results[6].text + "</td>";
		    str += "</tr>";
	     	}
		}	

		str += "</table>";
	   // 添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}

	 /** ***********************************************违法相关车辆详细信息****************************************** */
	 function vehicleLinkDetailsQuery(hpzl,hphm){
	     showMsg();
		 var url = 'tira.vehicleQuery.VehicleDetailInfoQuery.d';
		 url = encodeURI(url);
		 var params = "HPZL=" + hpzl +"&HPHM=" +hphm;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showvehicleLinkDetailsResponse});
	 }
	 
	 function showvehicleLinkDetailsResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["机动车序号", "号牌种类", "号牌号码"],
		               ["中文品牌", "英文品牌", "车辆型号"],
		               ["国产进口", "制造国", "制造厂名称"],
		               ["发动机号", "车辆类型", "车身颜色"],
		               ["使用性质", "机动车所有人", "身份证明名称"],
		               ["身份证明号码", "住所行政区划", "住所详细地址"],
		               ["住所邮政编码", "联系电话", "所有权"],
		               ["初次登记日期", "最近定检日期", "检验有效期止"],
		               ["强制报废期止", "发证机关", "管理部门"],
		               ["发牌日期", "发行驶证日期", "发登记证书日期"],
		               ["发合格证日期", "保险终止日期", "补领号牌次数"],
		               ["补领行驶证次数", "补换领证书次数", "登记证书编号"],
		               ["制登记证书行数", "档案编号", "管理辖区"],
		               ["机动车状态", "是否抵押", "经办人"],
		               ["业务类别", "注册流水号", "发动机型号"],
		               ["燃料种类", "排量", "功率"],
		               ["转向形式", "出厂日期", "获得方式"],
		               ["来历凭证1", "凭证编号1", "　"]];
		var results = null;
		var names = null;
		var rowflg = true;

	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0  align='center' >";
     		str += "<tr class=\"rowstyle\" >"
		    str += "<td align=\"center\" width=\"100%\" height=\"20px\" style=\"font-weight: bold\"  valign=\"bottom\" colspan=\"6\">";
		    str += "相关车辆详细信息";
		    str += "</td>";
		    str += "</tr>";
			for ( var i = 0; i < rows.length; i++) {
				results = rows[i].childNodes;
				names = strName[i];
				str += "<tr class=\"rowstyle\" >"
				for ( var j = 0, k = 0; j < results.length || k < names.length;) {
					if (rowflg) {
						if ("　" != names[k]) {
							names[k] += "：";
						}
						str += "<td width='15%' align='right' style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">"
								+ names[k] + "</td>";
						k++;
						rowflg = false;
					} else {
						if ("" == results[j].text) {
							results[j].text = "　";
						}
						str += "<td width='18%'>" + results[j].text + "</td>";
						j++;
						rowflg = true;
					}
				}
				str += "</tr>";
			}
			str += "</table>";
	   // 添加到结果面板上
		var tabObj = document.getElementById("detailData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}

	 
	 /** *********************************************车辆相关违法信息**************************************************** */

	 // 车辆相关违法信息
	 function violationLinkQuery(xh){
		 var url = 'tira.vehicleQuery.ViolationLinkInfoQuery.d';
		 url = encodeURI(url);
		 var params = "XH=" + xh;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showViolationLinkQueryResponse});
	 }

	 function showViolationLinkQueryResponse(xmlHttp){
			   var xmlDoc = xmlHttp.responseXML;
			   var rows = xmlDoc.getElementsByTagName("row");
			  
		     	var results = rows[0].childNodes;
		     	var HPZL =results[0].text;
		     	var HPHM =results[2].text+results[1].text;
			 	var left = (screen.availWidth-800)/2;
			    var top = (screen.availHeight-590)/2;
				window.open("../violation/ViolationLinkQuery.jsp?HPZL=" +HPZL+"&HPHM=" +HPHM,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
			}