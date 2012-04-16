	/**
	* 函数功能:按备编码，设备类型，设备状态对所有设备信息进行查询
	*/
	 function equipmentInfoQuery() {
		 var sbbm = document.getElementById("SBBM");
		 var sblx = document.getElementById("SBLX");
	     showMsg();
		 var sbzt = document.getElementById("SBZT");
		 var url = "tira.equipmentQuery.equipmentInfoQuery.d";
		 url = encodeURI(url);
		 var params = "SBBM=" + sbbm.value +"&SBLX=" + sblx.value + "&SBZT=" + sbzt.value;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:page});
	 }

	 function page(xmlHttp){
		   var xmlDoc = xmlHttp.responseXML;
		   var rows = xmlDoc.getElementsByTagName("row");
		   var results = rows[0].childNodes;
		   var pageSQL = results[0].text;
		   try{
		   	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(pageSQL),"showQueryResponse","13");
		   	}catch(e){
		   	return null;
		   	}
		 }
	 
	 function showQueryResponse(xmlDoc){
	   var rows = xmlDoc.getElementsByTagName("row");

	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0>";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">";
	   	str += "<td width='10%'  align='center'>主键</td> \
             <td width='15%' align='center'>设备类型</td> \
             <td width='10%' align='center'>设备编码</td> \
             <td width='30%' align='center'>产品名称</td> \
             <td width='35%' align='center'>所属机构</td>";
	   	str += "</tr>";
	   	var j = 0;
	   	for (var i = 0; i < rows.length; i++){
	   	  
	     	var results = rows[i].childNodes;
			str += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,'#5f9ea0')\" onmouseout=\"mouseout(this,'#5f9ea0')\" \
					onclick=\"equipmentDetails("+ results[0].text +")\">";

	     	for(j=0;j<results.length-1;j++){
	     		if(results[j].text=="null" || results[j].text=="" || results[j].text==null){
	     			results[j].text="　";
	     		}
			    str += "<td >" + results[j].text + "</td>";
	     	}
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

	/**
	* 函数功能:跳转到设备详细信息
	* 传递参数:主键SID
	*/
	function equipmentDetails(sid){
	 	var left = (screen.availWidth-800)/2;
	    var top = (screen.availHeight-590)/2;
		window.open("EquipmentDetailQuery.jsp?SID=" + sid,"","height=590,width=800,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	}

	/**
	* 函数功能:根据主键SID查询设备详细详细信息查询
	*/
	function equipmentDetailsQuery(sid){
	     showMsg();
		 var url = 'tira.equipmentQuery.EquipmentDetailInfoQuery.d';
		 url = encodeURI(url);
		 var params = "SID=" + sid;
		 params = encodeURI(params);
		 new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showDetailResponse});
		
	}

	/**
	* 函数功能:设备详细详细信息查询回调函数
	*/
	 function showDetailResponse(xmlHttp){
	   var xmlDoc = xmlHttp.responseXML;
	   var rows = xmlDoc.getElementsByTagName("row");
		var strName = [["主键", "设备用途", "设备类型"],
		               ["设备编码", "产品名称", "型号"],
		               ["规格",	"单位", "ip地址"],
		               ["设备通讯方式", "生产日期", "软件版本"],
		               ["生产商名称", "生产商联系方式", "供应商名称"],
		               ["供应商联系方式", "所属机构", "责任人"],
		               ["设备状态", "录入员", "备注1"],
		               ["备注2", "备用1", "备用2"],
		               ["入库日期", "设备CC", "使用状态"],
		               ["故障出现时间", "故障说明", "是否监控"],
		               ["关联id", "安装地点", "所属系统"],
		               ["所属辖区", "十七位编码", "X坐标"],
		               ["Y坐标", "经度", "纬度"],
		               ["是否删除","　","　"]];
		var results = null;
		var names = null;
		var rowflg = true;
		
		var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=97% cellSpacing=0 cellPadding=0 align='center' >";
		str += "<tr class=\"rowstyle\" >"
		str += "<td align=\"center\" width=\"100%\" height=\"20px\" style=\"font-weight: bold\"  valign=\"bottom\" colspan=\"6\">";
		str += "设备详细信息";
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
	   //添加到结果面板上
		var tabObj = document.getElementById("detailData");
		tabObj.innerHTML = str;
		Popup.prototype.hideTips();	
	}