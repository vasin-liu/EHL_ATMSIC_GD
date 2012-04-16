    
    //初始化页面时间值
    function loadTime(){
       var date = new Date();
       var startDateObj = document.getElementById("START_SGFSSJ");
	   startDateObj.value = getSysdate(1440,true);
	   var endDateObj = document.getElementById("END_SGFSSJ");
	   endDateObj.value = getSysdate(0,true);
    }
	/**
	* 函数功能:按事故编号，行政区划，事故类型，事故发生时间，事故认定原因对所有事故信息进行查询
	*/
	 function accidentInfoQuery() {
		 var sgbh = document.getElementById("SGBH");
		 var xzqh = document.getElementById("XZQH");
		 var sglx = document.getElementById("SGLX");
		 var start_sgfssj = document.getElementById("START_SGFSSJ");
		 var end_sgfssj = document.getElementById("END_SGFSSJ");
		 var sgrdyy = document.getElementById("SGRDYY");
	     showMsg();
		 var url = 'tira.accidentQuery.accidentInfoQuery.d';
		 url = encodeURI(url);
		 var params = "SGBH=" + sgbh.value +"&XZQH=" + xzqh.value +"&SGLX=" + sglx.value +
		 			  "&START_SGFSSJ=" + start_sgfssj.value +"&END_SGFSSJ=" + end_sgfssj.value + "&SGRDYY=" + sgrdyy.value;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:page});
	 }
	/**
	* 函数功能:分页
	*/
	 function page(xmlHttp){
		   var xmlDoc = xmlHttp.responseXML;
		   var rows = xmlDoc.getElementsByTagName("row");
		   var results = rows[0].childNodes;
		   var pageSQL = convertSql(results[0].text);
		   var dbSource = results[1].text;
		   try{
		   	PageCtrl.initPage("tdData","pageData","pageNav",pageSQL,"showQueryResponse","13",dbSource);
		   	}catch(e){
		   	return null;
		   	}
		 }

	/**
	* 函数功能:分页回调函数，分页显示事故主要信息。
	*/
	 function showQueryResponse(xmlDoc){
	   var rows = xmlDoc.getElementsByTagName("row");
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += " <td width='10%'  align='center'>事故编号</td> \
                 <td width='10%' align='center'>行政区划</td> \
                 <td width='12%' align='center'>事故类型</td> \
                 <td width='13%' align='center'>事故发生时间</td> \
                 <td width='20%' align='center'>事故发生地点</td> \
                 <td width='35%' align='center'>事故认定原因</td>";
	   	str += "</tr>";
	   	for (var i = 0; i < rows.length; i++){
	   	   
	     	var results = rows[i].childNodes;
	     	if(results[5].text == "null" || results[5].text == null){
	     	    continue;
	     	}
	     	if(results[9].text == null || results[9].text == 'null'){
	     	    results[9].text = "--";
	     	}
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
					onclick=\"accidentDetails("+ results[0].text +","+ results[1].text +")\">";
		    str += "<td width='10%'>" + results[0].text + "</td>";
		    str += "<td width='10%'>" + results[9].text + "</td>";
		    str += "<td width='12%'>" + results[2].text + "</td>";
		    str += "<td width='13%'>" + results[3].text + "</td>";
		    str += "<td width='20%' nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" nowrap title='"+ results[4].text +"'>" + results[4].text + "</td>";
		    str += "<td width='35%' nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" nowrap title='"+ results[5].text +"'>" + results[5].text + "</td>";
		    str += "</tr>";
		}	

		str += "</table>";
	   //添加到结果面板上
		var tabObj = document.getElementById("pageData");
		tabObj.innerHTML = str;
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
	 /************************事故详细信息****************************************/

	/**
	* 函数功能:跳转到事故详细信息。
	*/
	function accidentDetails(sgbh,xzqh){
	 	var left = (screen.availWidth-800)/2;
	    var top = (screen.availHeight-590)/2;
		window.open("AccidentDetailQuery.jsp?SGBH=" + sgbh+"&XZQH="+xzqh,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	}
	/**
	* 函数功能:事故详细信息查询。
	* 参数:事故编号,行政区划
	*/
	function accidentDetailsQuery(sgbh,xzqh){
	     showMsg();
		 var url = 'tira.accidentQuery.AccidentDetailInfoQuery.d';
		 url = encodeURI(url);
		 var params = "SGBH=" + sgbh;
		 params += "&XZQH=" + xzqh;
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showDetailResponse});
		
	}
	 
	/**
	* 函数功能:事故详细信息查询回调函数,返回页面表示事故详细信息的表格数据。
	*/
	 function showDetailResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["事故编号", "行政区划", "事故发生时间"],
		               ["事故发生地点", "在道路横断面位置", "死亡人数"],
		               ["受伤人数", "直接财产损失", "事故类型"],
		               ["事故初查原因", "事故认定原因", "天气"],
		               ["能见度", "现场", "事故形态"],
		               ["地形", "逃逸事故侦破", "交通信号方式"],
		               ["路侧防护设施类型", "公路行政等级", "道路物理隔离"],
		               ["路面状况", "路表情况", "路面结构"],
		               ["路口路段类型", "道路线型", "道路类型"],
		               ["照明条件", "是否运载危险品", "运载危险品事故形态"],
		               ["经办人","　","　"]];
		var results = null;
		var names = null;
		var rowflg = true;

	    var str = "<table id='tabVeh' class='scrollTable' width=97% cellSpacing=0 cellPadding=0  align='center' >";
		str += "<tr class=\"rowstyle\" >"
		str += "<td align=\"center\" width=\"100%\" height=\"20px\" style=\"font-weight: bold\"  valign=\"bottom\" colspan=\"6\">";
		str += "事故详细信息";
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
					str += "<td width='16%' align='right' style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">"
							+ names[k] + "</td>";
					k++;
					rowflg = false;
				} else {
					if ("" == results[j].text) {
						results[j].text = "　";
					}
					str += "<td width='17%'>" + results[j].text + "</td>";
					j++;
					rowflg = true;
				}
			}
			str += "</tr>";
		}
		str += "</table>";
	   //添加到结果面板上
		var tabObj = document.getElementById("detailData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}
	 /*************************当事人信息***************************************/

	 // 跳转到当事人信息页
		function person(sgbh,xzqh){
		 	var left = (screen.availWidth-800)/2;
		    var top = (screen.availHeight-590)/2;
			window.open("PersonLinkQuery.jsp?SGBH=" + sgbh+"&XZQH="+xzqh,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
		}

	 // 相关当事人信息查询
	 function personQuery(sgbh,xzqh){
	     showMsg();
		 var url = 'tira.accidentQuery.personInfoQuery.d';
		 url = encodeURI(url);
		 var params = "SGBH=" + sgbh +"&XZQH=" + xzqh;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showPersonQueryResponse});
	 }

	 function showPersonQueryResponse(xmlHttp){
		 var xmlDoc = xmlHttp.responseXML;
		 var rows = xmlDoc.getElementsByTagName("row");

	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += " <td width='17%' align='center'>事故编号</td> \
                 <td width='13%' align='center'>行政区划</td> \
                 <td width='13%' align='center'>人员编号</td> \
                 <td width='13%' align='center'>姓名</td> \
                 <td width='18%' align='center'>身份证号码</td> \
                 <td width='13%' align='center'>地址</td> \
                 <td width='13%' align='center'>电话</td>";
	   	str += "</tr>";
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
		   	if(results != null && results[0].text.substring(0,results[0].text.indexOf("=")) == "没有事故编号"){
				str += "<tr>";
			    str += "<td colspan='7'>" + results[0].text + "</td>";
			    str += "</tr>";
		   	}else{
				str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
						onclick=\"personDetailsQuery('"+ results[0].text+"','" + results[1].text +"','" + results[2].text +"')\">";
			    str += "<td >" + results[0].text + "</td>";
			    if(results[7].text == null || results[7].text == 'null'){
			        str += "<td >" + results[1].text + "</td>";
			    }else{
			        str += "<td >" + results[7].text + "</td>"; 
			    }
			    
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

	 /*************************当事人信息***************************************/
		 // 当事人详细信息查询
		function personDetailsQuery(sgbh,xzqh,rybh){
		     showMsg();
			 var url = 'tira.accidentQuery.PersonDetailInfoQuery.d';
			 url = encodeURI(url);
			 var params = "SGBH=" + sgbh +"&XZQH=" + xzqh+"&RYBH=" + rybh;
			 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showPersonDetailResponse});
		}
		 
		 function showPersonDetailResponse(xmlHttp){
		   var xmlDoc = xmlHttp.responseXML;
		   var rows = xmlDoc.getElementsByTagName("row");
			var strName = [["事故编号", "行政区划", "人员编号"],
			               ["姓名", "性别", "身份证明号码"],
			               ["年龄", "住址", "电话"],
			               ["邮政编码", "管理行政区划", "人员类型"],
			               ["所属行业", "交通方式", "驾驶员类型"],
			               ["驾驶证种类", "档案编号", "驾龄"],
			               ["事故责任", "号牌种类", "号牌号码"],
			               ["车身颜色", "机动车所有人", "核载量"],
			               ["实载量", "车辆合法状态", "车辆安全状况"],
			               ["是否运载危险品", "危险品种类", "危险品运输许可"],
			               ["运载危险品事故后果", "所有权", "第三者责任保险"]];
			var results = null;
			var names = null;
			var rowflg = true;
	
		    var str = "<table id='tabDtail' class='scrollTable' width=100% cellSpacing=0 cellPadding=0  align='center' >";
		     	str += "<tr class=\"rowstyle\" >"
			    str += "<td align=\"center\" width=\"100%\" style=\"font-weight: bold\" height=\"20px\" valign=\"bottom\" colspan=\"6\">";
			    str += "当事人详细信息";
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
						str += "<td width='16%' align='right' style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">"
								+ names[k] + "</td>";
						k++;
						rowflg = false;
					} else {
						if ("" == results[j].text) {
							results[j].text = "　";
						}
						str += "<td width='17%'>" + results[j].text + "</td>";
						j++;
						rowflg = true;
					}
				}
				str += "</tr>";
			}
			str += "</table>";
		   //添加到结果面板上
			var tabObj = document.getElementById("detailData");
			tabObj.innerHTML = str;
			Popup.prototype.hideTips();	
		}