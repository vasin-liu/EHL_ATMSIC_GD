	/**
	 * 函数功能:按号牌种类，号牌号码，机动车所有人对所有车辆信息进行查询
	 */
	 function violationInfoQuery() {
		 var wfbh = document.getElementById("WFBH");
		 var jszh = document.getElementById("JSZH");
		 var hpzl = document.getElementById("HPZL");
		 var hphm = document.getElementById("HPHM");
	     showMsg();
		 var url = 'tira.violationQuery.violationInfoQuery.d';
		 url = encodeURI(url);
		 var params = "HPZL=" + hpzl.value +"&HPHM=" + hphm.value + "&JSZH=" + jszh.value + "&WFBH=" + wfbh.value;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:page});
	 }
	 
	 function page(xmlHttp){
		   var xmlDoc = xmlHttp.responseXML;
		   var rows = xmlDoc.getElementsByTagName("row");
		   var results = rows[0].childNodes;
		   var pageSQL = results[0].text;
		   //var dbSource = results[1].text;
		   	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(pageSQL),"showQueryResponse","13");
		 }

	 function showQueryResponse(xmlDoc){
	   var rows = xmlDoc.getElementsByTagName("row");
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += " <td width='18%'  align='center'>违法编号</td> \
                 <td width='15%' align='center'>驾驶证号</td> \
                 <td width='10%' align='center'>号牌种类</td> \
                 <td width='10%' align='center'>号牌号码</td> \
                 <td width='13%' align='center'>交通方式</td> \
                 <td width='12%' align='center'>违法时间</td> \
                 <td width='22%' align='center'>违法行为</td>";
	   	str += "</tr>";
	   	for (var i = 0; i < rows.length; i++){

	     	var results = rows[i].childNodes;
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
					onclick=\"violationDetails('"+ results[0].text +"')\">";
		    str += "<td>" + results[0].text + "</td>";
		    str += "<td>" + results[1].text + "</td>";
		    str += "<td>" + results[2].text + "</td>";
		    str += "<td>" + results[3].text + "</td>";
		    str += "<td>" + results[4].text + "</td>";
		    str += "<td>" + results[5].text + "</td>";
		    str += "<td nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" nowrap title='"+ results[6].text +"'>" +  results[6].text + "</td>";
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
	/*******************************************************************************************/
	 // 跳转到违法详细信息
	function violationDetails(wfbh){
	 	var left = (screen.availWidth-800)/2;
	    var top = (screen.availHeight-590)/2;
		window.open("ViolationDetailQuery.jsp?WFBH=" + wfbh,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	}
	 // 违法详细信息查询
	function violationDetailsQuery(wfbh){
	     showMsg();
		 var url = 'tira.violationQuery.ViolationDetailInfoQuery.d';
		 url = encodeURI(url);
		 var params = "WFBH=" + wfbh;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showDetailResponse});
		
	}

	 function showDetailResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["违法编号", "决定书类别", "决定书编号"],
		               ["驾驶证号", "档案编号", "发证机关"],
		               ["当事人", "住所行政区划", "住所行政地址"],
		               ["联系电话", "联系方式", "机动车所有人"],
		               ["号牌种类", "号牌号码", "交通方式"],
		               ["违法时间", "违法行为", "违法地址"],
		               ["违法记分数", "执勤民警", "罚款金额"],
		               ["滞纳金", "缴款方式", "发现机关"],
		               ["处理机关", "处罚种类", "处理时间"],
		               ["是否处理", "是否缴款", "录入人"]];
		var results = null;
		var names = null;
		var rowflg = true;

	    var str = "<table id=\"tabVeh\"  border=\"0\" class=\"scrollTable\" width=97% cellSpacing=0 cellPadding=0 align='center' >";
		     	str += "<tr class=\"rowstyle\" >"
			    str += "<td align=\"center\" width=\"100%\" style=\"font-weight: bold\" height=\"20px\" valign=\"bottom\" colspan=\"6\">";
			    str += "违法详细信息";
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
	 /** ***********************违法相关车辆信息************************************** */

	 // 跳转到车辆信息页
		function vehicle(wfbh){
			 var url = 'tira.violationQuery.VehicleLinkInfoQuery.d';
			 url = encodeURI(url);
			 var params = "WFBH=" + wfbh;
			 params = encodeURI(params);
			 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showVehicleLinkResponse});
		}

		 function showVehicleLinkResponse(xmlHttp){
		   var xmlDoc = xmlHttp.responseXML;
		   var rows = xmlDoc.getElementsByTagName("row");
	     	var results = rows[0].childNodes;
	     	var hpzl =results[0].text;
	     	var hphm =results[1].text;
		 	var left = (screen.availWidth-800)/2;
		    var top = (screen.availHeight-590)/2;
			window.open("../../ehl/vehicle/VehicleLinkQuery.jsp?HPZL=" + hpzl+"&HPHM="+hphm,"hh","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
		}

	 /** ***********************相关驾驶证信息************************************** */
	 // 打开人员驾驶证
	function drivingLicense(wfbh){
	 var url = 'tira.violationQuery.DrivingLicenseLinkInfoQuery.d';
		 url = encodeURI(url);
		 var params = "WFBH=" + wfbh;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showdrivingLicenseLinkResponse});
		
	}

	 function showdrivingLicenseLinkResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=97% cellSpacing=0 cellPadding=0 align='center' >";
     	var results = rows[0].childNodes;
     	var SFZMHM =results[0].text;
	 	var left = (screen.availWidth-800)/2;
	    var top = (screen.availHeight-590)/2;
		window.open("../driverlicense/LicenseDtailQuery.jsp?SFZMHM=" +SFZMHM,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	}

	/** ***********************车辆相关违法信息************************************** */
	function violationLinkQuery(hpzl,hphm){
		 var url = 'tira.violationQuery.violationLinkInfoQuery.d';
		 url = encodeURI(url);
		 var params = "HPZL=" + hpzl+"&HPHM=" + hphm;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showViolationLinkResponse});
	}

	 function showViolationLinkResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += " <td width='18%'  align='center'>违法编号</td> \
                 <td width='15%' align='center'>驾驶证号</td> \
                 <td width='10%' align='center'>号牌种类</td> \
                 <td width='10%' align='center'>号牌号码</td> \
                 <td width='13%' align='center'>交通方式</td> \
                 <td width='12%' align='center'>违法时间</td> \
                 <td width='22%' align='center'>违法行为</td>";
	   	str += "</tr>";
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
	     	if(results[0].text.substring(0,results[0].text.indexOf("=")) == "号牌种类"){
				str += "<tr class=\"rowstyle\">";
		    str += "<td colspan ='67'>" + results[0].text + "</td>";
		    str += "</tr>";
	     	}else{
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
						onclick=\"violationLinkDetailsQuery('"+ results[0].text +"')\">";
		    str += "<td>" + results[0].text + "</td>";
		    str += "<td>" + results[1].text + "</td>";
		    str += "<td>" + results[2].text + "</td>";
		    str += "<td>" + results[3].text + "</td>";
		    str += "<td>" + results[4].text + "</td>";
		    str += "<td>" + results[5].text + "</td>";
		    str += "<td nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" nowrap title='"+ results[6].text +"'>" +  results[6].text + "</td>";
		    str += "</tr>";
	     	}
		}	

		str += "</table>";
	   // 添加到结果面板上
		var tabObj = document.getElementById("tdData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
 
		 /** *************************************相关违法详细信息*********************************** */
	 // 违法详细信息查询
	function violationLinkDetailsQuery(wfbh){
	     showMsg();
		 var url = 'tira.violationQuery.ViolationDetailInfoQuery.d';
		 url = encodeURI(url);
		 var params = "WFBH=" + wfbh;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showviolationLinkDetailsResponse});
		
	}

	 function showviolationLinkDetailsResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["违法编号", "决定书类别", "决定书编号"],
		               ["驾驶证号", "档案编号", "发证机关"],
		               ["当事人", "住所行政区划", "住所行政地址"],
		               ["联系电话", "联系方式", "机动车所有人"],
		               ["号牌种类", "号牌号码", "交通方式"],
		               ["违法时间", "违法行为", "违法地址"],
		               ["违法记分数", "执勤民警", "罚款金额"],
		               ["滞纳金", "缴款方式", "发现机关"],
		               ["处理机关", "处罚种类", "处理时间"],
		               ["是否处理", "是否缴款", "录入人"]];
		var results = null;
		var names = null;
		var rowflg = true;

	    var str = "<table id=\"tabVeh\"  border=\"0\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 align='center' >";
    		str += "<tr class=\"rowstyle\" >"
   		    str += "<td align=\"center\" width=\"100%\" height=\"20px\" style=\"font-weight: bold\"  valign=\"bottom\" colspan=\"6\">";
   		    str += "相关违法详细信息";
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