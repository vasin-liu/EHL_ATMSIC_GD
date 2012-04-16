
function load() {
     onLoadToolbar();
     //doQuery();
     doQueryGroupInfo();
     $('btniQuery').onclick = doQueryGroupInfo;
}

function doQueryGroupInfo()
{
//	var sqlBuffer = 'SELECT a.gid,\
//update by jason 20091219 原因：采用包头版数据库的表结构，去除了t_gps_cargroup.gid字段
/* comment by jason 20091222 原因：由于carnumber有可能为null，所以采用后面修改后的sql，去掉为carnumber==null的数据
var sqlBuffer = 'SELECT \
       	b.groupid,\
       	b.groupname,\
       	(SELECT c.carnumber\
    	FROM t_gps_carinfo c\
      	WHERE a.carcode = c.carcode) carnumber,\
       	a.carcode\
  		FROM t_gps_groupinfo b, t_gps_cargroup a\
 		WHERE b.groupid = a.groupid(+)';
 	*/	
	/*去掉carnumber（车牌号码）为空的记录。*/
	var sqlBuffer = 'SELECT b.groupid,b.groupname,c.carnumber,a.carcode\
	  FROM t_gps_groupinfo b, t_gps_cargroup a,t_gps_carinfo c\
	  WHERE\
	  a.carcode=c.carcode and \
	  c.carnumber is not null and \
	  b.groupid = a.groupid';
		
	var groupid = '';
	if($('groupname') )
	{
		groupid = $('groupname').value;
	}
	
    var carnumber = $('carnumber').value;
	if(groupid && 0<groupid.length) {
	 	sqlBuffer += " and a.groupid='"+groupid+"' ";
	}
	if(carnumber && 0<carnumber.length) {
	  sqlBuffer += " and a.carcode = (SELECT d.carcode FROM t_gps_carinfo d WHERE d.carnumber='"+carnumber+"')" ;
	}
	//sqlBuffer += "  order by a.groupid ";
	//update by jason 20091222,修改原因：增加carnumber作为排序条件 
	sqlBuffer += "  order by a.groupid,carnumber"; 
	//alert("sqlBuffer1="+sqlBuffer.toString());
	//prompt('',sqlBuffer.toString());
	var perPageCount = 16;
	//pagingCarList = new DhtmlPaging(0,-1,perPageCount,sqlBuffer,$('divPagingEle'),showGpsGroup );
	//alert("sqlBuffer="+sqlBuffer);
	PageCtrl.initPage("divPagingEle","pageData","pageNav",sqlBuffer,"showGpsGroup","10","gps");
	//pagingCarList.getPageData();
	//pagingCarList.setDBConfig("gps");
}
/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function doQuery() {
		var url = "gps.groupctrl.ReadGpsGroup.d";
		url = encodeURI(url);
		var params = "";
	    params = encodeURI(params);  
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGpsGroup});

}

function doQueryByCon() {
      var groupid = $('groupname').value;
      var carnumber = $('carnumber').value;
      var url = "gps.groupctrl.ReadGpsGroupByCondition.d";
      var params = "";
      params = "groupid=" + groupid +"&carnumber="+carnumber;
      new Ajax.Request(url, {
      	method:"post", 
      	parameters:params, 
      	onComplete: showGpsGroup
      });
      
}

/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showGpsGroup(xmlHttp) {
        var xmlDoc = xmlHttp.responseXML;
	   	var rows = xmlDoc.getElementsByTagName("row");

	  	if(rows && 0 == rows.length )
	  	{
	  		$('group').innerHTML = '<span style="color:blue;font-size:8pt;">无相关数据!</span>';
	  		return;
	  	}
	  
	  
	 	//var title = ["选择","分组名称","车辆名称"];
	 	//update by jason 20091222 修改了表头的标题
	 	var title = ["选择","分组名称","车辆号码"];
	 	var strTab ="<table id=\"dataTable\" width=\"700\" cellspacing=\"0\" cellpadding=\"0\" class=\"Generaltable\" align=\"center\">";
	 	strTab += "<tbody>";
	 	strTab +="<tr bgcolor=\"#DDEEFF\" align=\"center\">";
	 	for(var i=0; i<title.length;i+=1)
	 	{
		 	strTab +=    "<td class=\"Generaltd\">" + title[i] + "</td>"; 
	 	}
	 	strTab +="</tr>"
	 	
		if(rows.length>0)
		{  
		  strTab += getGroupRowsHtml(rows);
		}
	    strTab+= "</tbody></table>";
	    $('group').innerHTML=strTab;
}

function getGroupRowsHtml(rows)
{
	var htmlBuffer = '';
	var count = 0;
	for(var i=0;i<rows.length;i+=count )
	{
	    var results =  rows[i].getElementsByTagName("cell");

	    /* comment by jason 20091220 原因：采用包头库，表结构有变化。
	    var gID = results[0].text
	    var groupID = results[1].text;
	    var groupName = results[2].text;
	     */
	     //added by jason 20091220 原因：采用包头库，表结构有变化。begin
	    //var gID = results[0].text
	    var groupID = results[0].text;
	    var groupName = results[1].text;
	     //added by jason 20091220 原因：采用包头库，表结构有变化。end
	     	     
	    var count = 0;
	    var groupHtml = '';
        for(var j=i;j<rows.length;j+=1) 
        {
        	var rowData =  rows[j].getElementsByTagName("cell");
           	//if(groupID == rowData[1].text ) //组ID相同
           	if(groupID == rowData[0].text ) //组ID相同
           	{
           		count += 1;
           		if(count > 1)
           		{
           			groupHtml += " <tr align=\"center\" >"
           		}
           		/*comment by jason 20091220 原因：采用包头库，表结构有变化。
           		//var carPlate = rowData[3].text;
           		if(gID == 'null' || gID == '')
           		{
           			carPlate = '无本组车辆';
           		}
           		*/
           		//update by jason 20091220 原因：采用包头库，表结构有变化。begin
           		var carPlate = rowData[2].text;
           		if(groupID == 'null' || groupID == '')
           		{
           			carPlate = '无本组车辆';
           		}
           		 //added by jason 20091220 原因：采用包头库，表结构有变化。end
	        	groupHtml += "<td class=\"Generaltd\">" + carPlate + " &nbsp;</td>";
	        	groupHtml += "</tr>";
           	}
            else
            {
            	break;
            }
		 }
		 if( 0<count )
		 {
		 	var temp = '';
			temp += '<tr align=\"center\">';
		    temp += "<td class = 'Generaltd'"+" PLACEHOLDER "+" align=\"center\" bgcolor=\"#FFFFFF\" rowspan=" + count + ">";
	        var para = groupID + "," + groupName ;
	        temp += " <input type=\"checkbox\" id=\"ticlassify\" name=\"ticlassify\" value=\""+ para +"\"/>";
			temp += "</td>"; 
			temp += "<td class=\"Generaltd\" rowspan=" + count + ">" + groupName + "</td>";
			htmlBuffer += temp + groupHtml;
	    }
	    else
	    {
	    	break;
	    }
	}
	return htmlBuffer;
}

function uniteTdCells(){
       var table = document.getElementById("dataTable");
       for(i=0;i<table.rows.length;i++){
         for(c=0;c<table.rows[i].cells.length;c++){
           if(c==1){            //选择要合并的列序数，去掉默认全部合并
            for(j=i+1;j<table.rows.length;j++){
              var cell1=table.rows[i].cells[c].innerText;
              var cell2=table.rows[j].cells[c].innerText;
              if(cell1==cell2){
                   table.rows[j].cells[c].style.display='none';
                   table.rows[i].cells[c].rowSpan++;
              } else break;                        
           }
           }
       }       
	}    
}


function toModifyGroup(gid,groupid) {
	var returnValuestr = window.showModalDialog("GpsGroupEdit.jsp?groupid="+groupid, "groupedit", "dialogWidth:420px;dialogHeight:330px"); 
	if(returnValuestr == "fresh"){
	      doQuery();
	}
}


//删除
function doDeleteGroup(){
    var gid = getPId();
    if(confirm("确定删除该车辆分组？")){
        var params = "gid="+gid; 
	    var url = "gps.groupctrl.DeleteGpsGroup.d";
	    params = encodeURI(params);
	    url = encodeURI(url);
	    new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDeleteGroup});   
    }
}
	function showDeleteGroup(xmlHttp){
    	alert(xmlHttp.responseText);
    	doQuery();
	}
	
  function toAddGroup() {
       var returnValuestr = window.showModalDialog("GpsCarGroupEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:330px"); 
	   if(returnValuestr == "fresh"){
	      doQuery();
	   }
  }	
	

/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function doQueryById(gid) {
		var url = "gps.groupctrl.ReadGpsGroupById.d";
		url = encodeURI(url);
		var params = "gid=" + gid;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPerson1});
	
}
/** 
	* 功能 ：编辑页面带值


	* 时间：2008-04-16
	*/
function showPerson1(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
    var results = xmlDoc.getElementsByTagName("col");
	var gid = document.getElementById("gid");
	var groupid =document.getElementById("groupid");
	var group = document.getElementById("group");
	var carcode = document.getElementById("carcode");
	var HPZL = document.getElementById("HPZL");
	 
	gid.value = results[0].text;
	 
	group.value = results[1].text;
 
	HPZL.value = results[4].text;
	carcode.value = results[4].text;
}


function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
      	    
			var returnValue = window.showModalDialog("GpsCarGroupEdit.jsp?insertorupdate=0", "", "dialogWidth:420px;dialogHeight:330px");
		
			if(returnValue == "fresh")
			{
			  // fresh();
			   doQuery();
			}
 		}
		if (id == "0_edit") {
		   var gid = getPId();
			if(gid.length >= 2 || gid.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
		 
		   var group = gid[0].split(",");
		   var groupid = group[0];
		   var groupname = group[1];
		   var url = "GpsGroupEdit.jsp?groupid="+groupid+"&groupname="+groupname;
		 
		   url = encodeURI(url);
		 	var returnValuestr = window.showModalDialog(url, 'gpsList', "dialogWidth:550px;dialogHeight:330px"); 
		  
	        if(returnValuestr == "fresh"){
	        	 doQueryGroupInfo();
	        }
		}
		if (id == "0_delete") {
			doDeleteGroup();
		}
	
	    if (id == "0_fresh") {
			 doQuery();
		}
		
		
		if (id == "0_excel") {
		
		    var dataSql = "SELECT OrderID,Class,LastUpdate from t_news_aclass";
		      
		    var header = "分组名称，车辆名称名称，更新日期";
		       
			saveFieldsAsExcel("\u4eba\u5458\u57fa\u672c\u4fe1\u606f", "news", header,dataSql);
		}
		
		if (id == "0_info") {
		     var gid = getPId();
			if(gid.length >= 2 || gid.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			else {
				window.showModalDialog("GpsCarGroupEdit.jsp?gid="+gid+"&insertorupdate=1", "", "dialogWidth:420px;dialogHeight:330px"); 
				 if(returnValuestr == "fresh"){
	               doQuery();
	         }
			}
		}
	}
/** ---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 
    * 
    * 作者：wangxiaoting
    *
    * 时间：2009-04-08
    * 
    *
    */
function modify(insrtOrUpdt,groupid) {
    if(groupid == "null" || groupid == null){
       groupid = "";
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
  
    var groupname = document.getElementById("groupname");
	var carname = document.getElementById("carname");
	var bz = document.getElementById("bz");
    
    if(groupname.value.Trim() == "") {
		alert("请输入分组名称!");
		aclass.focus();
		return;
	}
    dataArray.push(groupname.value);
	dataArray.push(carname.value);
	dataArray.push(orderid.value);
    dataArray.push(bz.value);
	var url = "gps.classifyctrl.AddOrEditGpsGroup.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "groupid="+groupid+"groupname=" + groupname.value +"carname="+carname.value+"bz="+bz.value+"&insertOrUpdate=" + insrtOrUpdt;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseCar});
}

function showResponseCar(xmlHttp) {
	alert(xmlHttp.responseText);
}

function getPId() {
    var name = document.getElementsByName("ticlassify");
    var pid;
    for(var i=0;i<name.length;i++) {
       if(name.checked==true) {
          pid.push(name.value);
       }
    }
    return pid;
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


function save(insertorupdate) {
	  var gid = document.getElementById("gid").value;
	  var groupid = document.getElementById("group").value;
	  var carcode = document.getElementById("HPZL").value;
	  
	  var updateflag = insertorupdate;
	  var url = "gps.groupctrl.AddOrEditGpsGroup.d";
	  url = encodeURI(url);
	  var params = "gid=" + gid+"&groupid="+groupid+"&carcode="+carcode+"&insertorupdate="+updateflag;
	  params = encodeURI(params);
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



