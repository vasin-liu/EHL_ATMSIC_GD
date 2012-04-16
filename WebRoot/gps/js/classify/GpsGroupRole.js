/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function load() {
    //onLoadToolbar("0_new,0_edit,0_delete,0_fresh,0_excel,0_info");
    onLoadToolbar();
    doQuery();
}	
function doQuery() {
		var url = "gps.grouprolectrl.ReadGpsGroupRole.d";
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
	 	var tdObj = document.getElementById("grouprole");
	 	var title = ["选择","分组编号","分组名称","描述"];
	 	var strTab ="<table width=\"700\" cellspacing=\"0\" cellpadding=\"0\" class=\"Generaltable\" align=\"center\">";
	    for(var i = 0 ; i <rows.length; i++){
		    var results = rows[i].childNodes;
		    for(var j = 0; j <results.length; j++){
	     	    if(results[j].text == 'null'||results[j].text == ""){
	     	       results[j].text = '&nbsp;';   
	     	    }   
	     	}
	     }
	
     	 strTab +="<tr bgcolor=\"#DDEEFF\" align=\"center\">";
		 strTab +=    "<td class=\"Generaltd\">" + title[0] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[1] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[2] + "</td>"; 
     	 strTab +=    "<td class=\"Generaltd\">" + title[3] + "</td>"; 
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


function toModifyGroup(groupid) {
	   var returnValuestr = window.showModalDialog("GpsGroupRoleEdit.jsp?groupid="+groupid+"&insertorupdate=1", "", "dialogWidth:360px;dialogHeight:180px"); 
	   if(returnValuestr=="fresh") {
	       doQuery();
	   }
}
function toAddGroup() {
       var returnValuestr = window.showModalDialog("GpsGroupRoleEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:420px"); 
	   if(returnValuestr=="fresh") {
	       doQuery();
	   }
}

    //删除
	function doDeleteGroupRole(){
	    var groupid = getPId();
	    if(confirm("确定删除该分组规则？")){
	        var params = "groupids="+groupid;
		    var url = "gps.grouprolectrl.DeleteGpsGroupRole.d";
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
function doQueryById(groupid) {
		var url = "gps.grouprolectrl.ReadGpsGroupRoleById.d";
		url = encodeURI(url);
		if(groupid!='null'||groupid!=''){
		   var params = "groupid=" + groupid;
		 } 
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPerson});
	
}
/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showPerson(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
    var results = xmlDoc.getElementsByTagName("col");
	var groupid = document.getElementById("groupid");
	var groupname = document.getElementById("groupname");
	var grouprole = document.getElementById("grouprole");
	var remark = document.getElementById("remark");
	groupid.value = results[0].text;
	groupname.value = results[1].text;
	//grouprole.value = results[3].text;
	remark.value = results[2].text;
}

/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行save（保存）、New(增加)、Edit（修改）、Delete(删除)、Print（打印）操作
  */

	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
			var returnValue = window.showModalDialog("GpsGroupRoleEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:220px");
			if(returnValue == "fresh")
			{
			  // fresh();
			   doQuery();
			}
 		}
		if (id == "0_edit") {
		   var groupid = getPId();
			if(groupid.length >= 2 || groupid.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			var returnValuestr = window.showModalDialog("GpsGroupRoleEdit.jsp?groupid="+groupid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:220px"); 
	        if(returnValuestr == "fresh"){
	               doQuery();
	         }
		}
		if (id == "0_delete") {
			doDeleteGroupRole();
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
		     var groupid = getPId();
			if(groupid.length >= 2 || groupid.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			else {
				var returnValue = window.showModalDialog("GpsGroupRoleEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:300px");
			}
		}
	}
	
function save(insertorupdate) {
	  var groupid = document.getElementById("groupid");
	  var groupname = document.getElementById("groupname");
	  var grouprole = document.getElementById("grouprole");
	  var remark = document.getElementById("remark");
	  var insertorupdate = insertorupdate;
	  
	 // groupid = groupid.value;
	  groupid = groupid.value;
	  groupname = groupname.value;
	 // grouprole = grouprole.value;
	  remark = remark.value;
	  var url = "gps.grouprolectrl.AddOrEditGpsGroupRole.d";
	  url = encodeURI(url);
	  //var params = "groupid="+groupid+"&groupname=" + groupname+"&carname="+carname+"&insertorupdate="+updateflag;
	  var params = "groupid=" + groupid+"&groupname="+groupname+"&remark="+remark+"&insertorupdate="+insertorupdate;
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




