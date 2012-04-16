	/**
	* 函数功能:根据条件获取超速车辆名单
	*/
	 function queryDriver(){
	   showMsg();
	   var cardNameObj = document.getElementById("cardNameId");
	   var pnameObj = document.getElementById("pname");
	   var cardNoObj = document.getElementById("cardNo");sexSelectId
	   var sexObj = document.getElementById("sexSelectId");
		 var url = 'tira.dirverquery.DriverQuery.d';
		 url = encodeURI(url);
		 var params = "SFZMMC="+cardNameObj.value+"&XM="+pnameObj.value + "&SFZMHM="+cardNoObj.value+"&XB="+sexObj.value;
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
		   var dbSoure = results[1].text;
		   	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(pageSQL),"showDriverResponse","13");
		 }
 
	 function showDriverResponse(xmlDoc){
	   var rows = xmlDoc.getElementsByTagName("row");
	   //生成表头
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='18%'  align='center'>姓名</td>";
	    str += " <td width='8%' align='center'>性别</td>";
	    str += " <td width='20%'  align='center'>身份证明名称</td>";
	    str += " <td width='20%' align='center'>身份证明号码</td>";
	    str += " <td width='16%'  align='center'>住所行政区划</td>";
	    str += " <td width='18%' align='center'>联系电话</td>";
	   	str += "</tr>";
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
	     	
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" onclick=\"openInfoPage('"+results[3].text+"')\">";	
		    str += "<td  width='18%' align=\"center\">"+results[0].text+"</td>";
		    str += "<td width='8%'>" + results[1].text + "</td>";
		    str += "<td width='20%'>" + results[2].text + "</td>";		 
		    str += "<td width='20%'>" + results[3].text + "</td>";
		    str += "<td width='16%'>" + results[4].text + "</td>";		 
		    str += "<td width='18%'>" + results[5].text + "</td>";    
		    str += "</tr>";						
		}	
		str += "</table>";
	   //添加到结果面板上
		var tdObj = document.getElementById("pageData");
		tdObj.className = "scrollTable";
		tdObj.innerHTML = str;	
		Popup.prototype.hideTips();	
	}
   
  
  //控制选中状态

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
	 
   //打开驾驶员相关信息页面
   function openInfoPage(SFZMHM){
      window.open("DriverDtailQuery.jsp?SFZMHM=" +SFZMHM+"&flag=relateToLisence","","width=900,height=590,left="+eval(screen.Width-900)/2+",top="+eval(screen.Height-590)/2+"");
    
   }
  
   //查询并展示驾驶员详细信息
   function showPersonInfo(SFZMHM){
      var url = 'tira.dirverquery.queryPerson.d';
	   url = encodeURI(url);
	   var params = "SFZMHM="+SFZMHM;
	   params = encodeURI(params);
	   new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponsePerson});
   }
   // 姓名，身份证明名称，身份证明号码，性别，出生日期，国籍，登记住所行政区，登记住所邮政编码，联系住所行政区，联系住所邮政编码，
   // * 联系电话，居住暂住证明，暂住地行政区划，暂住地邮政编码，登记住所详细地址，暂住地详细地址，联系住所详细地址
	function showResponsePerson(xmlHttp) {
		var xmlDoc = xmlHttp.responseXML;
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["姓名","身份证明名称","身份证明号码"],
		               ["性别","出生日期","国籍"],
		               ["登记住所行政区","登记住所邮政编码","联系住所行政区"],
		               ["联系住所邮政编码","联系电话","居住暂住证明"],
		               ["暂住地行政区划","暂住地邮政编码","登记住所详细地址"],
		               ["暂住地详细地址","联系住所详细地址","　"]];
		var results = null;
		var names = null;
		var rowflg = true;
		
	    var str = "<table id=\"personTab\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0  align='center' border='0'>";
		str += "<tr class=\"rowstyle\" >"
		str += "<td align=\"center\" width=\"100%\" height=\"20px\" style=\"font-weight: bold\"  valign=\"bottom\" colspan=\"6\">";
		str += "驾驶员详细信息";
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
		str += "</table>";
		// 添加到结果面板上
		var tabObj = document.getElementById("detailData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
   function valueTdTextById(tdId,tdText){
      document.getElementById(tdId).innerHTML = tdText;
   }
   /**********************************相关驶证详细信息***********************************************/
   
   //打开驾驶证详细信息页面
   function showDrivingLicenseDtail(SFZMHM){
       window.open("../driverlicense/LicenseDtailQuery.jsp?SFZMHM=" +SFZMHM,"","width=900,height=590,left="+eval(screen.Width-900)/2+",top="+eval(screen.Height-590)/2+"");
    
   }