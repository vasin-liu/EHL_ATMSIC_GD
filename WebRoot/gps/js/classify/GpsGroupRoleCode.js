function load() {
     onLoadToolbar("0_new,0_edit,0_delete,0_fresh,0_excel,0_info");
     doQuery();
}


/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function doQuery() {
		var url = "gps.grouprolecodectrl.ReadGpsGroupRoleCode.d";
		url = encodeURI(url);
		var params = "";
	    params = encodeURI(params);  
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showClassify});

}
/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showClassify(xmlHttp) {
        var xmlDoc = xmlHttp.responseXML;
	   	var rows = xmlDoc.getElementsByTagName("row");
	 	var tdObj = document.getElementById("grouprolecode");
	 	var title = ["&nbsp;","代码编号","规则代码值"];
	 	var strTab ="<table width=\"700\" cellspacing=\"0\" cellpadding=\"0\" class=\"Generaltable\" align=\"center\">";
	    for(var i = 0 ; i <rows.length; i++){
		    var results = rows[i].childNodes;
		    for(var j = 0; j <results.length; j++){
	     	    if(results[j].text == null||results[j].text == ""){
	     	       results[j].text = '&nbsp;';   
	     	    }   
	     	}
	     }
	
     	 strTab +="<tr bgcolor=\"#DDEEFF\" align=\"center\">";
		 strTab +=    "<td class=\"Generaltd\">" + title[0] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[1] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[2] + "</td>"; 
     	 //strTab +=    "<td class=\"Generaltd\">" + title[3] + "</td>"; 
     	 strTab +="</tr>"
		 
	   if(rows.length>0){  
	     for(var i=0;i<rows.length;i++){
	       strTab += "<tr align=\"center\">";
	       var results = rows[i].childNodes;
	        strTab += "<td class=\"Generaltd\">";
	        strTab += " <input type  =\"checkbox\" id=\"ticlassify\" name=\"ticlassify\" value=\"" + results[0].text+"\"/>";
	        strTab += "</td>";
	       for(var j=0;j<results.length;j++) {
	          strTab += "<td class=\"Generaltd\">" + results[j].text + "</td>";
	       }
	       strTab += "</tr>";
	    }
	    }
	    strTab+= "</table>";
	    tdObj.innerHTML=strTab;
}


function toModifyRoleCode(codeid) {
     //var returnValuestr = window.showModalDialog("ClassifyEdit.jsp?catidid="+strConfID+"&strJGID="+strJGID+"&strDutyPost="+strDutyPost+"&strFlag=1", "", "dialogWidth:420px;dialogHeight:420px"); 
	   // if(returnValuestr == "fresh"){
	  //       doQueryByID(strjgid);
	   //}  
	   var returnValuestr = window.showModalDialog("GpsGroupRoleCodeEdit.jsp?codeid="+codeid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:550px"); 
	   if(returnValuestr == "fresh"){
	         doQuery();
	   }  
}

function toAddRoleCode() {
      var returnValuestr = window.showModalDialog("GpsGroupRoleCodeEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:550px"); 
	   if(returnValuestr == "fresh"){
	         doQuery();
	   }  
}

    //删除
	function doDeleteRoleCode(){
	    var codeid = getPId();
	    if(confirm("确定删除该分组规则代码？")){
	        var params = "codeid="+codeid;
		    var url = "gps.grouprolecodectrl.DeleteGpsGroupRoleCode.d";
		    params = encodeURI(params);
		    url = encodeURI(url);
		    new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDeleteClassify});   
	    }
	}
	function showDeleteClassify(xmlHttp){
    	alert(xmlHttp.responseText);
    	doQuery();
	}
	
  	
	

/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function doQueryById(codeid) {
		var url = "gps.grouprolecodectrl.ReadGpsGroupRoleCodeById.d";
		url = encodeURI(url);
		var params = "codeid=" + codeid;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPerson});
	
}
/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showPerson(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
    var results = xmlDoc.getElementsByTagName("col");
	var codeid = document.getElementById("codeid");
	var codename = document.getElementById("codename");
	var bz = document.getElementById("bz");
	codeid.value = results[0].text;
	codename.value = results[1].text;
	bz.value = results[2].text;
}

/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行save（保存）、New(增加)、Edit（修改）、Delete(删除)、Print（打印）操作
  */

	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
      	    
			var returnValue = window.showModalDialog("GpsGroupRoleCodeEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:420px");
		
			if(returnValue == "fresh")
			{
			  // fresh();
			   doQuery();
			}
 		}
		if (id == "0_edit") {
		   var cataid = getPId();
			if(cataid.length >= 2 || cataid.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			var returnValuestr = window.showModalDialog("GpsGroupRoleCodeEdit.jsp?codeid="+cataid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:420px"); 
	        if(returnValuestr == "fresh"){
	               doQuery();
	         }
		}
		if (id == "0_delete") {
			doDeleteRoleCode();
		}
	
	    if (id == "0_fresh") {
			 doQuery();
		}
		
		
		if (id == "0_excel") {
		
		    var dataSql = "SELECT OrderID,Class,LastUpdate from t_news_aclass";
		      
		    var header = "分组名称，车辆名称名称，备注";
		       
			saveFieldsAsExcel("\u4eba\u5458\u57fa\u672c\u4fe1\u606f", "news", header,dataSql);
		}
		
		if (id == "0_info") {
		     var gid = getPId();
			if(gid.length >= 2 || gid.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			else {
				window.showModalDialog("GpsGroupRoleCodeEdit.jsp?codeid="+cataid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:420px"); 
			}
		}
	}
	
function save(insertorupdate) {
	  var codeid = document.getElementById("codeid");
	  var codename = document.getElementById("codename");
	  //var bz = document.getElementById("bz");
	  var insertorupdate = insertorupdate;
	  
	  codeid = codeid.value;
	  codename = codename.value;
	//  bz = bz.value;
	  var url = "gps.grouprolecodectrl.AddOrEditGpsGroupRoleCode.d";
	  url = encodeURI(url);
	  //var params = "groupid="+groupid+"&groupname=" + groupname+"&carname="+carname+"&insertorupdate="+updateflag;
	  var params = "codeid=" + codeid+"&codename="+codename+"&insertorupdate="+insertorupdate;
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseCar1});
	
}


function showResponseCar1(xmlHttp) {
	alert(xmlHttp.responseText);
	
}

function getPId() {
    var name = document.getElementsByName("ticlassify");
    var pid = new Array(); 
    for(var i=0;i<name.length;i++) {
       if(name[i].checked==true) {
          pid.push(name[i].value);
       }
    }
    return pid;
}
// 关闭子页面

	function doClose(){
		window.returnValue ="fresh";
	 	window.close();
	} 



