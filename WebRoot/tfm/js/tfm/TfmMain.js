 //初始化页面变量（机构编号，机构名称和机构层次编码）
 deptId = "";
 deptName = "";
 function setDeptInfo(paramDeptId,paramDeptName){
     deptId = paramDeptId;
     deptName = paramDeptName;
 }
 

 
 
 //按条件路况信息
 function filter(){
     var lineObj = document.getElementById("lineId");
     var statusObj = document.getElementById("statusId");
     var sql =" select linkid,REGIONNAME,linkname,direction,roadstatus,'拥堵原因' FROM t_tfm_lineflow WHERE 1=1 ";
     
	 if(lineObj.value != ""){
		sql += " AND LINKDIRID = '"+ lineObj.value +"'"; 
	 }
	 if(statusObj.value != ""){
	 	sql += " AND ROADSTATUS = '"+ statusObj.value +"'"; 
	 }
    PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showFilterResponse","13");
 }
 
 function showFilterResponse(xmlDoc){

	   var rows = xmlDoc.getElementsByTagName("row");
	   //生成表头
	    var str = "<table id=\"tabVeh\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">";
	   	str += "<tr class=\"scrollColThead\" valign=\"top\" style=\"text-decoration: none;	background-color: #B4C1E2;line-height: 20px;\">\
	   	  <td width='15%'   align='center'>警用辖区</td>\
	   	  <td width='20%' align='center'>连线名称</td>\
	   	   <td width='5%' align='center'>方向</td>\
	      <td width='7%'  align='center'>道路路况</td>\
	      <td width='40%' align='center'>拥堵原因</td>\
	      <td width='5%' align='center'>修改</td>\
	    </tr>";
	   	for (var i = 0; i < rows.length; i++){
	     	var results = rows[i].childNodes;
	     	for(var j = 0; j <results.length; j++){
	     	    if(results[j].text == 'null'){
	     	        results[j].text = '未记录';   
	     	    }   
	     	}
			str += "<tr  >";
			str += " <td width='15%' nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" title="+results[1].text+" align='center'>"+ results[1].text+"</td>";	
		    str += " <td width='20%' nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" title="+results[2].text+" align='center'>" + results[2].text+"</td>";
		   str += " <td width='5%' align='center'>"+results[3].text+"</td>";;
		    str += " <td width='7%' nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" title="+results[4].text+" align='center'>"+ results[4].text+"</td>";
		    str += " <td width='40%' nowrap style=\"overflow:hidden; text-overflow:ellipsis;\" title="+results[5].text+" align='center'>" + results[5].text+"</td>";
			str += " <td width='5%' align='center'>";
			str += " <a href='#' onclick=\"toModifyTfm('"+results[0].text+"')\"><img  alt='修改设备'  border='0'src='../../image/button/update.gif' ></a>";
			str += "</td>";
		    str += "</tr>";						
		}	
		str += "</table>";
	   //添加到结果面板上
		var tdObj = document.getElementById("pageData");
		tdObj.className = "scrollTable";
		tdObj.innerHTML = str;	
		Popup.prototype.hideTips();	

	    timer = setInterval(filter,parseInt(5)*1000*60);	
	}
	
	//获取一条连线信息展示到编辑页面
	function getLinkById(linkDirId){
	     if(SID != ""){
	         var params = "linkDirId="+linkDirId;
		     var url = "tfm.tfmManag.getLinkById.d";
		     url = encodeURI(url);
		     new Ajax.Request(url, {method:"post", parameters:params, onComplete:showEquipmentOnPage});
	     }
	 }
	 function showEquipmentOnPage(xmlHttp){
	    document.getElementById("SSXTId").onchange = eval("selectChange");
	    var xmlDoc = xmlHttp.responseXML;
	    var cols = xmlDoc.getElementsByTagName("col");
	    if(cols.length == 0){
	        return;
	    }
        document.getElementById("CPMC").value   = cols[0].text;
		document.getElementById("SSXTId").value = cols[1].text;   
		document.getElementById("SBBM").value   = cols[2].text;    
		//document.getElementById("SBLXId").value = cols[3].text;
		document.getElementById("XH").value     = cols[4].text;      
		document.getElementById("SBYT").value   = cols[5].text; 
		document.getElementById("GG").value     = cols[6].text;      
		document.getElementById("SBCC").value   = cols[7].text;     
		document.getElementById("IPDZ").value   = cols[8].text; 
		document.getElementById("SLDW").value   = cols[9].text;    
		document.getElementById("RKRQ").value   = formatDateStr(cols[10].text);    
		document.getElementById("SCRQ").value   = formatDateStr(cols[11].text);    
		document.getElementById("RJBB").value   = cols[12].text;   
		document.getElementById("SCSMC").value  = cols[13].text;    
		document.getElementById("SCSLXFS").value= cols[14].text;  
		document.getElementById("GYSMC").value  = cols[15].text;  
		document.getElementById("GYSLXFS").value= cols[16].text;
		document.getElementById("FZR").value    = cols[17].text;    
		document.getElementById("SBZTId").value = cols[18].text; 
		document.getElementById("LRY").value    = cols[19].text;      
		document.getElementById("AZDD").value   = cols[20].text;     
		document.getElementById("SYZTId").value = cols[21].text;   
		document.getElementById("TXFS").value   = cols[22].text;     
		document.getElementById("SSXQ").value   = cols[23].text;     
		document.getElementById("SFJK").value   = cols[24].text;     
		document.getElementById("BZ1").value    = cols[25].text;
		
        fillListBox("SBLXTdId","SBLXId","155","SELECT ID,VALUE FROM ATMS_EQUIPMENT_DICT  WHERE TYPE='"+cols[1].text+"_SBLX'","","checkSBLX('"+cols[3].text+"')");
	  
	  
	}
	
	//打开修改页面
	function toModifyTfm(linkDirId){
	     var returnValuestr = window.showModalDialog("tfmEdit.jsp?linkDirId="+linkDirId, "", "dialogWidth:578px;dialogHeight:580px");  
	     if(returnValuestr == "fresh"){
	        filter();
	     }      
	}
	
  
 