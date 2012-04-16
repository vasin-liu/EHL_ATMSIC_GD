/**
 * 
 * @说明：机构树divjs
 * @创建日期：2008-08-12
 * @作者：郭亮亮
 */
 
//全局变量
var G_jgID = "";
var G_divID  = "";
var G_JGMC="";
var nodeID = "";

/**
 * @作者：郭亮亮
 * @函数说明：生成悬浮div方法
 * @参数：divID：层次编码, jgID：机构id, jgmc：机构名称, nodeId：机构输入框id, state：设置根据鼠标位置定位标志.
 * @创建日期：2008-08-12
 */
function showDepartTree(divID,jgID,jgmc,nodeId,top,left){
 	nodeID = nodeId;//赋值全局变量
 	//创建容器div
 	var useDiv = document.createElement("div");
 	useDiv.id="deptDiv";
 	useDiv.className="deptDivClass";
 	//给弹出窗口左右边距默认值0
 	if(top==null||top==""){
 		top=0;
 	}
 	if(left==null||left==""){
 		left=0;
 	}
 	useDiv.style.top=top;
 	useDiv.style.left=left;
 	
    document.body.appendChild(useDiv);
	var html = "<span id=\""+jgID+"\" style=\"color:black;cursor:hand\" ondblclick=\"saveSelect();\" onclick=\"doOnClick('"
	+divID+"','"+jgID+"','"+jgmc+"')\"><img id=\""+"i"+jgID+"\" src=\"../../images/tree/plusOpen.gif\"/>"+jgmc
	+"</span><br><div id=\""+divID+"\" style=\"display:none\"></div>";
	
	Popup.prototype.showPopup('deptDiv','请选择单位...',html);
	doOnClick(divID,jgID,jgmc);
	
}

/**
 * 
 * @函数说明：查询数据库,如有子机构则进行展现
 * @创建日期：2008-08-12
 * @作者：郭亮亮
 */
function doOnClick(divID, jgID,jgmc) {

	G_jgID = jgID; //赋值机构ID全局变量
	G_divID = divID; //赋值层次ID全局变量
	G_JGMC = jgmc;   //赋值机构名称
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
			
			img.src = "../../images/tree/plusOpen.gif";
			
		} else {
			document.all(divID).style.display = "";
			img.src = "../../images/tree/minusClosed.gif";
		}
	}
}

/**
 * @函数说明：从数据库取出子机构进行页面展现
 * @创建日期：2008-08-12
 * @作者：郭亮亮
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


/** 
* 功能：删除子节点
*@作者：郭亮亮
* 时间：22008-08-12
*/
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


/** 
* 功能：写入子节点
*@作者：郭亮亮
* 时间：22008-08-12
*/
function writeElement(xmlHttp)
{
   
    var xmldoc = xmlHttp.responseXML;
		
	var rows = xmldoc.getElementsByTagName("row");
	
	for (var i = 0; i < rows.length; i++) {
	    var results = rows[i].getElementsByTagName("col");
	    //创建images子节点
		var ele_image = document.createElement("img");
		
		//判断子节点是否含有下级节点，确定节点图片链接
		if(results[2].text>0)
		{
			ele_image.setAttribute("src", "../../images/tree/plusOpen.gif");

			//为图片添加id，用于之后删除
			ele_image.setAttribute("id", "i" + results[0].text);
		}else{
			ele_image.setAttribute("src", "../../images/tree/blankLeaf.gif");

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
	    ele_div.setAttribute("id",results[3].text);	    
		ele_div.style.display = "none";
		//添加双击事件 --zhaoy
		ele_span.ondblclick = new Function("saveSelect();");
		//添加onClick事件传div 的ID(用来展现) span 的ID 用来获得父节点,此ID与数据库中的JGID相对应
		ele_span.onclick = new Function("doOnClick('" + ele_div.getAttribute("id") + "','" + results[0].text + "','"+results[1].text+"')");
		
		//添加image子节点
		ele_span.appendChild(ele_image);
		
		//显示机构图片+名称	
		ele_span.innerHTML = loadBlank(G_divID,ele_span.innerHTML + results[1].text);
		
		var divObj = document.getElementById(G_divID);
		
		//添加span子节点
		divObj.appendChild(ele_span);
		
		//添加换行
		var ele_br = document.createElement("br");
		ele_br.setAttribute("id",results[3].text+results[0].text);
		divObj.appendChild(ele_br);
		
		//添加div子节点
		divObj.appendChild(ele_div);
	}
}

/** 
* 功能：根据divid即机构层次编码添加空格方法
*@作者：郭亮亮
* 时间：22008-08-12
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
* 时间：2008-08-12
*/
function setSeleColor(jgID)
{
	var span = document.getElementById(jgID);
	
	var divObj = document.getElementById("popup");
	var sArray = divObj.getElementsByTagName("span");
	for (i=0; i<sArray.length; i++) {
		sArray[i].style.color= "black";
	}
	span.style.color= "blue";
}

/** 
* 功能：返回选中的机构
* 时间：2008-08-14
*/
function saveSelect(){
	//全段是否选择了机构
	if(G_JGMC!=""&&G_jgID!=""){
		var node = document.getElementById(nodeID);
		node.value = G_JGMC;//所选机构名写入input
		node.focus();
		if($('depUnitId')){
			$('depUnitId').value = G_jgID;
		}
	}
	Popup.prototype.hidePopup();//弹出div关闭
}

/** 
* 功能：取消选择机构
* 时间：2008-08-14
*/
function cancelSelect(){
	 G_jgID   = "";
	 G_divID  = "";
	 G_JGMC   = "";
	Popup.prototype.hidePopup();//弹出div关闭
}