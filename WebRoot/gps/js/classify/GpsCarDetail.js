function doQuery1(classid,deptid) {
		//var url = "gps.classifyctrl.ReadGpsClassify.d";
		var url = "gps.classifycountctrl.ReadGpsCarDetail.d";
		url = encodeURI(url);
		var params = "classid="+classid+"&deptid="+deptid;
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
	 	var tdObj = document.getElementById("queryGPScar");
	 	var title = ["选择","车辆号牌","呼叫号码","是否在线","是否限速","是否限时","是否设定电子围栏","所属区域"];
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
     	 strTab +=    "<td class=\"Generaltd\">" + title[4] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[5] + "</td>"; 
     	 strTab +=    "<td class=\"Generaltd\">" + title[6] + "</td>";
     	 strTab +=    "<td class=\"Generaltd\">" + title[7] + "</td>";
     	 strTab +="</tr>"
		 
	   if(rows.length>0){  
	     for(var i=0;i<rows.length;i++){
	       strTab += "<tr align=\"center\">";
	       var results = rows[i].childNodes;
	        strTab += "<td class=\"Generaltd\">";
	        strTab += " <input type  =\"checkbox\" id=\"classify\" name=\"classify\" value=\"" + results[0].text+"\"/>";
	        strTab += "</td>";
	       for(var j=1;j<results.length;j++) {
	          strTab += "<td class=\"Generaltd\">" + results[j].text + "</td>";
	       }
	      
	       strTab += "</tr>";
	    }
	    }
	    strTab+= "</table>";
	    tdObj.innerHTML=strTab;
}

    function doQuery(classid,groupid,deptid)
 	{
 		var selectSQL = "";
 		if(deptid!='null' && classid!='null') {
 		    selectSQL +="select tgc.CARCODE,tgc.ID,tgc.CARNUMBER,tgc.CARTELNUMBER,tgc.ISONLINE,";
 		    selectSQL +=" tgc.ISLIMITEDVELOCITY,tgc.ISLIMITEDTIME,tgc.ISLIMITEDSCALE,";
 		    selectSQL +=" tgc.DEPARTMENT,tgc.LONGTITUDE,tgc.LATITUDE from T_GPS_CARINFO tgc";
 		    if(deptid!=null) {
 		       selectSQL +=" where tgc.DEPARTMENTID='"+deptid+"'";
 		    }
 		    if(classid!=null) {
 		       //selectSQL += " and tgc.CARCATALOG='"+classid+"'";
               //update by jason 20091220 原因：采用包头库，表结构有变化。 
 		       selectSQL += " and tgc.CATALOG='"+classid+"'";
 		    }
 		}
 		if(groupid!='null' && classid!='null') {
 		    selectSQL +="select tgc.CARCODE,tgc.ID,tgc.CARNUMBER,tgc.CARTELNUMBER,tgc.ISONLINE,";
 		    selectSQL +=" tgc.ISLIMITEDVELOCITY,tgc.ISLIMITEDTIME,tgc.ISLIMITEDSCALE,";
 		    selectSQL +=" tgc.DEPARTMENT,tgc.LONGTITUDE,tgc.LATITUDE from T_GPS_CARINFO tgc,T_GPS_CARGROUP tgcg";
 		    selectSQL +=" where tgc.carcode=tgcg.carcode";
 		    //selectSQL +=" and tgc.CARCATALOG='"+classid+"'";
 		    //update by jason 20091220 原因：采用包头库，表结构有变化。 
 		    selectSQL +=" and tgc.CATALOG='"+classid+"'";
 		    selectSQL +=" and tgcg.groupid='"+groupid+"'";
 		}
 		pagingCarList = new DhtmlPaging(0,-1,12,selectSQL,$('gpscarPaging1'),responseQueryA);
		//pagingCarList.setDBConfig("");
		pagingCarList.getPageData();
 	}
 	
    function responseQueryA(xmlHttp)
 	{
 	    var xmlDoc = xmlHttp.responseXML;
	   	var results = xmlDoc.getElementsByTagName("row");
	 	var tdObj = document.getElementById("gpscarQueryResult");
	 	var title = ["选择","车辆号牌","呼叫号码","是否在线","是否限速","是否限时","是否设定电子围栏","所属区域"];
	 	var strTab ="<table width=\"700\" cellspacing=\"0\" cellpadding=\"0\" class=\"Generaltable\" align=\"center\">";
	    //for(var i = 0 ; i <results.length; i++){
	//    var result = results[i].childNodes;
		//   for(var j = 0; j <result.length; j++){
	    // 	    if(result[j].text == 'null'||result[j].text == ""){
	     //	       result[j].text = '&nbsp;';   
	     //	    }   
	   //  	}
	  //   }
	     
     	 strTab +="<tr bgcolor=\"#DDEEFF\" align=\"center\">";
		 strTab +=    "<td class=\"Generaltd\">" + title[0] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[1] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[2] + "</td>"; 
     	 strTab +=    "<td class=\"Generaltd\">" + title[3] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[4] + "</td>";   
     	 strTab +=    "<td class=\"Generaltd\">" + title[5] + "</td>"; 
     	 strTab +=    "<td class=\"Generaltd\">" + title[6] + "</td>";
     	 strTab +=    "<td class=\"Generaltd\">" + title[7] + "</td>";
     	 //strTab +=    "<td class=\"Generaltd\">" + title[8] + "</td>";
     	 //strTab +=    "<td class=\"Generaltd\">" + title[9] + "</td>";
     	 strTab +="</tr>"
	   if(results.length>0){  
	     for(var i=0;i<results.length;i++){
	       	var rowResult = results[i].childNodes;
			var showCols = new Array();
			
			var gpscarID = "";					
			var tempObj = rowResult[0].firstChild;
			if (tempObj != null)
			{
				gpscarID = tempObj.data;
			}
			var carCode = "";
			var tempObj = rowResult[1].firstChild;
			if (tempObj != null)
			{
				carCode = tempObj.data;
			}
			
			//车牌号码
			tempObj = rowResult[2].firstChild;
			if (tempObj != null)
			{
				showCols.push(tempObj.nodeValue);
			}
			else
			{	
				showCols.push("&nbsp;");
			}
			//呼叫号码
			tempObj = rowResult[3].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			//是否在线
			tempObj = rowResult[4].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			
			//是否限速
			tempObj = rowResult[5].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			
			//是否限时
			tempObj = rowResult[6].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			//是否设定电子围栏
			tempObj = rowResult[7].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			
	        //所属辖区

			tempObj = rowResult[8].firstChild;
			if (tempObj != null){
				showCols.push(tempObj.nodeValue);
			}
			else
			{
				showCols.push("&nbsp;");
			}
			//坐标					
			var coord = "";
			var coordx= "";
			var coordy = "";
			tempObj = rowResult[9].firstChild;
			if (tempObj != null){
				coordx = tempObj.nodeValue;
			}
			tempObj = rowResult[10].firstChild;
			if (tempObj != null){
				coordy = tempObj.nodeValue;
			}
			coord = coordx + "," + coordy;
	       strTab += "<tr onClick=\"showGPSTip('"+gpscarID+"','"+carCode+"','"+coord+"','"+i+1+"')\" align=\"center\">";
	      // var results = rows[i].childNodes;
	        strTab += "<td class=\"Generaltd\">";
	        strTab += " <input type  =\"checkbox\" id=\"classify\" name=\"classify\" value=\"" +coord+"\"/>";
	        strTab += "</td>";
	       for(var j=0;j<showCols.length;j++) {
	          strTab += "<td class=\"Generaltd\">" + showCols[j] + "</td>";
	       }
	      
	       strTab += "</tr>";
	    }
	    }
	    strTab+= "</table>";
	    tdObj.innerHTML=strTab;
 		
		
		//添加到结果面板上
   }
   
   function showGPSTip(gpscarID,carCode,coord,i) {
     
       window.opener.parent.gpsmap.centerCarInfo( gpscarID);
       window.opener.parent.window.focus();
   }
