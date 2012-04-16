<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<Link Rel="STYLESHEET" Href="../sm/css/pagetag/pagetag.css" Type="text/css">
		<script type="text/javascript" src="../sm/js/common/prototype.js"></script>
		<script type="text/javascript" src="js/GPSClassify.js"></script>
		<script type="text/javascript" src="../classify/CpsCarDetail.js"></script>
		<script type="text/javascript">
		
		function itemChoiceHandler()
		{
			var oLIEle = document.getElementById("tags").getElementsByTagName("li");
		  	var leLength = oLIEle.length;
		  
		  	for(i=0; i<leLength; i++)
		  	{
		    	oLIEle[i].className = "";
		   		oLIEle[i].style.background="url('../sm/image/sysoption/bg.gif')  right bottom";
		  	}
		  	var oDivs = $('tagContent').getElementsByTagName("div");
		  	for(i=0; i<oDivs.length; i++)
		  	{
		    	oDivs[0].style.display = "none";
		  	}
		}
		
		function zu()
		{
			itemChoiceHandler();
			var zu = document.getElementById("zu");
			zu.style.display='';
		 	  
			event.srcElement.parentNode.className = "selectTag";
			event.srcElement.parentNode.style.background="url('../sm/image/sysoption/bg2.gif')  right bottom";
			event.srcElement.style.background = "left bottom";
		}
		function qu()
		{
			itemChoiceHandler();
			var qu = document.getElementById("qu");
			qu.style.display='';
		 
			event.srcElement.parentNode.className = "selectTag";
			event.srcElement.parentNode.style.background="url('../sm/image/sysoption/bg2.gif')  right bottom";
			event.srcElement.style.background = "left bottom";
		}
 
		window.onload = function()
		{
			buildClassifyTree();
			//getClassifyStatTree('zu', 'gps.groupcountctrl.ReadGpsGroupCount.d',leafClickGroup);
			//getClassifyStatTree('qu', 'gps.classifycountctrl.readGpsClassifyCount.d',leafDeptClick);
		};
		
		
		function getClassifyStatTree(holder,serverURI,leafClickHandler)
		{
			var oClassifyTree = new GPSClassify();
			oClassifyTree.setTreeHolderID(holder);
			oClassifyTree.serverURI = serverURI;
			oClassifyTree.treeRootOpenImg = '4.gif';
			oClassifyTree.treeRootCloseImg = '5.gif';
			oClassifyTree.setLeafClickHandler(leafClickHandler);
			oClassifyTree.getClassifyXML();
		}
		function buildClassifyTree()
		{
			getClassifyStatTree('zu', 'gps.groupcountctrl.ReadGpsGroupCount.d',leafClickGroup);
			getClassifyStatTree('qu', 'gps.classifycountctrl.readGpsClassifyCount.d',leafDeptClick);
		}
		function leafDeptClick(nodes)
		{
			var classid = nodes[0];
			var deptid = nodes[1];
			window.open("classify/GpsCarDeatail.jsp?classid="+classid+"&deptid="+deptid, 'gpsList', "width=750px,height=450px"); 
			
		}
		function leafClickGroup(nodes)
		{
			var classid = nodes[0];
			var groupid = nodes[1];
			window.open("classify/GpsCarDeatail.jsp?classid="+classid+"&groupid="+groupid, 'gpsList', "width=750px,height=450px"); 	
		}
	</script>
	</head>
	<body style="margin:0;padding: 0;text-align:center;" scroll-x=no>
	<div style="height:20px;float:right;font-size:8pt;vertical-align:bottom" >
		<br style="line-height:5px"/>
		<a href='javascript:void(0)'style="vertical-align:bottom"   onclick="buildClassifyTree()"  ><span> 刷 新 </span></a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<ul id="tags">
		<li class="selectTag" style="background=url('../sm/image/sysoption/bg2.gif')  right bottom">
			<a href='javascript:void(0)'  onclick="zu()"  >分组查看</a>
		</li>
		<li>
			<a href='javascript:void(0)'  onclick="qu()" >分辖区查看</a>
		</li>
	</ul>	 
	
	<div id="tagContent" style=" width:100%;text-align:left;height:100%" >
		<div id="zu" style="width:100%;text-align:left;height:96%" class='tagContent selectTag'>
		</div>
		<div id="qu" style="text-align:left;width:100%;height:96%" class='tagContent selectTag'>
		</div>
	</div>
	</body>
</html>