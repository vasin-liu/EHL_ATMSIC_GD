
/**
 * @作者：郭亮亮
 * @函数说明：查询数据库,如有子机构则进行展现
 * @创建日期：2008-04-08
 */
function doOnClick(divID, jgID) {
	
	G_jgID = jgID; //赋值机构ID全局变量
	G_divID = divID; //赋值层次ID全局变量
	doDepartInfo(jgID); //显示选中机构基本信息
	setSeleColor(jgID); //设置选中颜色
    
    //根据JGID查询数据库,展现该机构的子机构
	var url = "pcs.departmentTree.getTreeListById.d";
	url = encodeURI(url);
	var params = "JGID=" + jgID+"&&Date="+new Date().getTime();
	
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showSubDepart});
	    //添加图标
	var img = document.getElementById("i"+jgID);
	
	//判断是否是叶节点，是则不进行图片转换，否则进行展开和非展开两种状态转换
	if(img.src.indexOf("blankLeaf.gif")>0)
	{
		  if (document.all(divID).style.display == "") {
			document.all(divID).style.display = "none";
			
		} else {
			document.all(divID).style.display = "";
			
		}
	}else{
		 if (document.all(divID).style.display == "") {
			document.all(divID).style.display = "none";
			
			img.src = "../../image/tree/plusOpen.gif";
			
		} else {
			document.all(divID).style.display = "";
			img.src = "../../image/tree/minusClosed.gif";
		}
	}
}
/**
 * @作者：郭亮亮
 * @函数说明：从数据库取出子机构进行页面展现
 * @创建日期：2008-04-08
 */
function showSubDepart(xmlHttp) {
	
	var isORno=true;
	var divObj = document.getElementById(G_divID);
	
	if(divObj.hasChildNodes()){
	 
	    var divChildren = divObj.getElementsByTagName("div");
		var divLength = divChildren.length;
		for (var i = 0; i<divLength; i++){
		     if(divChildren[i].hasChildNodes())
		     {
		        isORno = false;
		     }
		}
		if(isORno)
		{
		  	//先删除子节点再写入
			deleteElement(G_divID);
			
			writeElement(xmlHttp);
		
		}
	}else{
	   writeElement(xmlHttp);
	}
}

//删除子节点
function deleteElement(divId){
	
	  var divObj = document.getElementById(divId);
	  if(divObj.hasChildNodes()){
	    //删除span
	    var spanChildren = divObj.getElementsByTagName("span");
	    var spanLength = spanChildren.length;
		for (var i = 0; i <spanLength; i++) {
			 divObj.removeChild(spanChildren[0]);
		}
		//删除br
		var brChildren = divObj.getElementsByTagName("br");
		var brLength = brChildren.length;
		for (var i = 0; i<brLength; i++) {
			 divObj.removeChild(brChildren[0]);
		   }
		//删除div
		var divChildren = divObj.getElementsByTagName("div");
		var divLength = divChildren.length;
		for (var i = 0; i<divLength; i++) {
			divObj.removeChild(divChildren[0]);
		}
	  }
}

//写入子节点
function writeElement(xmlHttp)
{
   
    var xmldoc = xmlHttp.responseXML;
	var rows = xmldoc.getElementsByTagName("row");
	for(var i = 0; i < rows.length; i++){
	   var results = rows[i].getElementsByTagName("col");
	    //创建image子节点
		var ele_image = document.createElement("img");
		
		//判断子节点是否含有下级节点，确定节点图片链接
		if(results[2].text>0)
		{
			ele_image.setAttribute("src", "../../image/tree/plusOpen.gif");
			//为图片添加id，用于之后删除
			ele_image.setAttribute("id", "i" + results[0].text);
		}else{
			ele_image.setAttribute("src", "../../image/tree/blankLeaf.gif");
			//为图片添加id，用于之后删除
			ele_image.setAttribute("id", "i" + results[0].text);
		}
		//控制图片宽度
		ele_image.setAttribute("width", "36");

	    //创建子节点span
		var ele_span = document.createElement("span");
		ele_span.setAttribute("id", results[0].text); //添加id 与数据库 JGID 相对应
		ele_span.style.cursor = "hand"; //添加鼠标显示手型
		ele_span.style.color = "black"; //添加字体颜色
			    	    	    	   
	    //创建div子节点
		var ele_div = document.createElement("div");
		
		//div添加展现id 即doOnClick 方法第一个参数
	    ele_div.setAttribute("id", results[3].text);
	    //div添加展现name，记录父节点id
	    ele_div.setAttribute("name", results[0].text);
	    
		ele_div.style.display = "none";
		
		//添加onClick事件传div 的ID(用来展现) span 的ID 用来获得父节点,此ID与数据库中的JGID相对应		
		ele_span.onclick = new Function("doOnClick('" + ele_div.getAttribute("id") + "','" + results[0].text + "')");
		
		//添加image子节点
		ele_span.appendChild(ele_image);
		
		//显示机构图片+名称	
		ele_span.innerHTML = loadBlank(G_divID,ele_span.innerHTML + results[1].text);			
		var divObj = document.getElementById(G_divID);		
		//添加span子节点
		divObj.appendChild(ele_span);
		
		//添加换行
		var ele_br = document.createElement("br");
		ele_br.setAttribute("id", results[3].text+results[0].text);
		divObj.appendChild(ele_br);
		
		//添加div子节点
		divObj.appendChild(ele_div);
	}
	
	
	
}
/** 
* 功能 ：显示机构和人员基本信息
* 顺序:   RYID姓名 警号 性别 所属机构 现任职务
* 时间：2008-04-10
*/
function doDepartInfo(JGID) {
	if (JGID != "") {
		var url = "pcs.departmentManage.getDataById.d";
		url = encodeURI(url);
		var params = "JGID=" + JGID;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:writeToPage});
	}
}

/** 
* 功能：将一条记录的明细信息写入右侧明细页面
* 时间：2008-04-10
*/
function writeToPage(xmlHttp) {
    var xmldoc = xmlHttp.responseXML;
    var rows = xmldoc.getElementsByTagName("row");
    var cars = xmldoc.getElementsByTagName("car");
    var results = rows[0].childNodes;
    document.getElementById("JGID").value = results[0].text;
    document.getElementById("JGMC").value = results[1].text;
    document.getElementById("XZJB").value = results[3].text;
    document.getElementById("JGZN").value = results[4].text;
    document.getElementById("YWMS").value = results[5].text;
    document.getElementById("JGLX").value = results[6].text;
	document.getElementById("SCDZ").value= results[7].text;
	document.getElementById("YZBM").value= results[8].text;
	document.getElementById("BZRS").value= results[9].text;
	document.getElementById("ZBMJS").value= results[10].text;
	document.getElementById("ZBZGS").value= results[11].text;
	document.getElementById("QTRS").value= results[12].text;
	document.getElementById("FZR").value= results[13].text;
	document.getElementById("FZRDH").value= results[14].text;
	document.getElementById("FZRSJ").value= results[15].text;
	document.getElementById("ZBDH1").value= results[16].text;
	document.getElementById("ZBDH2").value= results[17].text;
	document.getElementById("ZBDH3").value= results[18].text;
	document.getElementById("CZDH").value= results[19].text;
	//人员
	if(rows.length >1){
	   var str = "<table border='0' cellpadding='0' cellspacing='0' width='100%' class='scrollTable'>"+
	                "<thead class='fixedHeader' id='fixedHeader'>"+
                      "<tr >"+
                         "<th >姓名</th>"+
                         "<th >警号</th>"+
                         "<th >性别</th>"+
                         "<th >所属单位</th>"+
                         "<th >现任职务</th>"+
                      "</tr></thead>";
      for(var j = 1; j < rows.length; j++){
          var personRes =  rows[j].childNodes;
          str += "<tbody class='scrollContent'>";
          str += "<tr>";
          str += "<td width='20%' align='center'><a href='#' TITLE='查看明细' onClick='showPersonInfo(\""+personRes[0].text+"\");'>"+personRes[1].text+"</a></td>";
          str += "<td width='10%' align='center'>"+personRes[2].text+"</td>";
          str += "<td width='10%' align='center'>"+personRes[3].text+"</td>";
          str += "<td width='30%' align='center'>"+personRes[4].text+"</td>";
          str += "<td width='15%' align='center'>"+personRes[5].text+"</td>";
          str += "</tr></tbody>";
      }
       str += "</table>";
       var obj = document.getElementById("tableContainer");
	   obj.innerHTML = str;
	}else{
	   var str = "<table border='0' cellpadding='0' cellspacing='0' width='100%' class='scrollTable'>"+
	                "<thead class='fixedHeader' id='fixedHeader'>"+
                      "<tr >"+
                         "<th width='20%'>姓名</th>"+
                         "<th width='10%'>警号</th>"+
                         "<th width='10%'>性别</th>"+
                         "<th width='30%'>所属单位</th>"+
                         "<th width='15%'>现任职务</th>"+
                      "</tr></thead></table>";
       var obj = document.getElementById("tableContainer");
	   obj.innerHTML = str;
	}
	//车辆
	if(cars.length >0){
	   var strCar = "<table border='0' cellpadding='0' cellspacing='0' width='100%' class='scrollTable'>"+
	                "<thead class='fixedHeader' id='fixedHeader'>"+
                      "<tr >"+
                         "<th >号牌种类</th>"+
                         "<th >号牌号码</th>"+
                         "<th >车辆种类</th>"+
                         "<th >所属单位</th>"+
                      "</tr></thead>";
      for(var j = 0; j < cars.length; j++){
          var carRes =  cars[j].childNodes;
          strCar += "<tbody class='scrollContent'>";
          strCar += "<tr>";
          strCar += "<td width='20%' align='center'><a href='#' TITLE='查看明细' onClick='showCarInfo(\""+carRes[0].text+"\");'>"+carRes[1].text+"</a></td>";
          strCar += "<td width='20%' align='center'>"+carRes[2].text+"</td>";
          strCar += "<td width='20%' align='center'>"+carRes[3].text+"</td>";
          strCar += "<td width='30%' align='center'>"+carRes[4].text+"</td>";
          strCar += "</tr></tbody>";
      }
       strCar += "</table>";
       var obj = document.getElementById("tableContainerCar");
	   obj.innerHTML = strCar;
	}else{
	   var strCar = "<table border='0' cellpadding='0' cellspacing='0' width='100%' class='scrollTable'>"+
	                "<thead class='fixedHeader' id='fixedHeader'>"+
                      "<tr >"+
                         "<th width='20%'>号牌种类</th>"+
                         "<th width='20%'>号牌号码</th>"+
                         "<th width='20%'>车辆种类</th>"+
                         "<th width='30%'>所属单位</th>"+
                      "</tr></thead></table>";
       var obj = document.getElementById("tableContainerCar");
	   obj.innerHTML = strCar;
	}
}


//查看人员明细
 function showPersonInfo(ryid){
    window.showModalDialog("PersonList.jsp?RYID=" + ryid + "", "",  "dialogWidth:460px;dialogHeight:620px");
 }
//查看人员明细
 function showCarInfo(carid){
	window.showModalDialog("CarInfo.jsp?insrtOrUpdt=1&CLBM=" + carid , "", "dialogWidth:410px;dialogHeight:655px");
  }
/** 
* 功能：根据divid即机构层次编码添加空格方法
* 时间：2008-04-10
*/
function loadBlank(divID,s)
{  
  var length = divID.length;
  
  var s_0 =""; 
  for(var i=0;i<length;i++)
  {
    s_0 += "&nbsp;&nbsp;";
  }
  return  s_0+s;
}

/** 
* 功能：设置选中颜色方法
* 时间：2008-04-12
*/
function setSeleColor(jgID)
{
	var span = document.getElementById(jgID);
	var divObj = document.getElementById("treeDiv");
	var sArray = divObj.getElementsByTagName("span");
	for (i=0; i<sArray.length; i++) {
		sArray[i].style.color= "black";
	}
	span.style.color= "blue";
}
//判断数据库是否有终极根节点，没有则按照常量文件中的值插入数据库
function isHaveRoot()
{
    var url = "pcs.departmentTree.insertRoot.d";
	url = encodeURI(url);
	var params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showXmlResponse});
}

function showXmlResponse(xmlHttp){
  return null;
}