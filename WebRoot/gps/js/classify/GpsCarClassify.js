
var staticdeptcode;


 

function showResponseDelete(xmlHttp) {
	var xmlText = xmlHttp.responseText;
	alert(xmlText);
	fresh();
}

 
/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function load() {
   // onLoadToolbar("0_new,0_edit,0_delete,0_fresh,0_excel,0_info");
    onLoadToolbar();
    doQuery();
}	
	
	
	
function doQuery() {
		var url = "gps.classifyctrl.ReadGpsClassify.d";
		url = encodeURI(url);
		var params = "";
	    params = encodeURI(params);  
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showClassify});

}


function showClassifyTest(xmlHttp) {
     var xmlDoc = xmlHttp.responseText;
     alert("xmldata=="+xmlDoc);
}


/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showClassify(xmlHttp) {
        var xmlDoc = xmlHttp.responseXML;
	   	var rows = xmlDoc.getElementsByTagName("row");
	 	var tdObj = document.getElementById("classify");
	 	var title = ["选择","分类编号","分类名称","描述"];
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
	        strTab += " <input type  =\"checkbox\" id=\"classify\" name=\"classify\" value=\"" + results[0].text+"\"/>";
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


function toModifyClassify(cataid) {
	  var returnValuestr = window.showModalDialog("ClassifyEdit.jsp?cataid="+cataid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:450px"); 
	  if(returnValuestr == "fresh") {
	       doQuery();
	  }
	  
}

function toAddClassify() {
      var returnValuestr = window.showModalDialog("ClassifyEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:450px"); 
	  if(returnValuestr == "fresh") {
	       doQuery();
	  }
}
//删除
	function doDeleteClassify(){
	    var gid = getPId();
	    if(gid.length>=1) {
		    if(confirm("确定删除该车辆分类？")){
		        var params = "cataid="+gid;
			    var url = "gps.classifyctrl.DeleteGpsClassify.d";
			    params = encodeURI(params);
			    url = encodeURI(url);
			    new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDeleteClassify});   
		    }else{
		       return;
		    }
	    }else {
	        alert("没有选中!");
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
function doQueryById(cataid) {
		var url = "gps.classifyctrl.ReadGpsClassifyById.d";
		url = encodeURI(url);
		var params = "cataid=" + cataid;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPerson});
	
}
/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showPerson(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
    var results = xmlDoc.getElementsByTagName("col");
	var cataid = document.getElementById("cataid");
	var cataname = document.getElementById("cataname");
	var bz = document.getElementById("bz");
	cataid.value = results[0].text;
	cataname.value = results[1].text;
	bz.value = results[2].text;
}




/** ---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 
    * 
    * 作者：wangxiaoting
    *
    * 时间：2009-04-08
    * 
    *
    */
function modify(insrtOrUpdt,CLASSID) {
    if(CLASSID == "null" || CLASSID == null){
       CLASSID = "";
    }
    window.returnValue = "fresh";
	//验证非法字符
	var ele_Array = document.getElementsByTagName("input");
	for(var i=0;i<ele_Array.length;i++){
	    if(!checkNormalStr(ele_Array[i].value)){
	      ("不允许输入非法字符");
	      ele_Array[i].focus();
	      return;
	    }
     }
	var dataArray = new Array();
  
    var aclass = document.getElementById("aclass");
	var orderid = document.getElementById("orderid");
	var pic = document.getElementById("filePic");
    
    if(aclass.value.Trim() == "") {
		alert("请输入栏目名称!");
		aclass.focus();
		return;
	}
    dataArray.push(CLASSID);
	dataArray.push(aclass.value);
	dataArray.push(orderid.value);
    dataArray.push(pic.value);
	var url = "news.NewsAManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseCar});
}
function showResponseCar(xmlHttp) {
	alert(xmlHttp.responseText);
}

  
/** 
    * desc:  新增与修改的重置函数
    * param:
    * return:
    * author：wangxiaoting
    * date:  2009-04-08
    * version:
    */
function reset() {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById("dataTable");
	if (queryTable != null) {
		input = queryTable.getElementsByTagName("input");
		select = queryTable.getElementsByTagName("select");
		textarea = queryTable.getElementsByTagName("textarea");
	} else {
		input = document.getElementsByTagName("input");
		select = document.getElementsByTagName("select");
		textarea = document.getElementsByTagName("textarea");
	}
	for (var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < select.length; i++) {
		if (select[i].type != "button" && !select[i].readOnly) {
			select[i].value = "-1";
		}
	}
}


 
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行save（保存）、New(增加)、Edit（修改）、Delete(删除)、Print（打印）操作
  */

	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
      	    
			var returnValue = window.showModalDialog("ClassifyEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:220px");
		
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
			var returnValuestr = window.showModalDialog("ClassifyEdit.jsp?cataid="+cataid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:220px"); 
	        if(returnValuestr == "fresh"){
	               doQuery();
	         }
		}
		if (id == "0_delete") {
			doDeleteClassify();
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
				window.showModalDialog("ClassifyEdit.jsp?cataid="+cataid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:300px"); 
			}
		}
	}
	
	function save(insertorupdate) {
	  var cataid = document.getElementById("cataid");
	  var cataname = document.getElementById("cataname");
	  var txtBZ = document.getElementById("bz");
	  var updateflag = insertorupdate;
	  
	  cataid = cataid.value;
	  cataname = cataname.value;
	  var url = "gps.classifyctrl.AddOrEditGpsClassify.d";
	  url = encodeURI(url);
	  var params = "cataid="+cataid+"&cataname=" + cataname+"&insertorupdate="+updateflag;
	  params += '&bz=' + txtBZ.value;
	 
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseCar1});
	
}


function showResponseCar1(xmlHttp) {
	alert(xmlHttp.responseText);
}

function getPId() {
    var name = document.getElementsByName("classify");
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

