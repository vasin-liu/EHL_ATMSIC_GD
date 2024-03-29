<%@ page language="java" pageEncoding="UTF-8" session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作导航</title>
<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/sidebar.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/navigation.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="STYLESHEET" type="text/css" href="../base/css/style2/dhtmlx/dhtmlXTree.css" />
<link href="../base/css/style1/Global.css" rel="stylesheet" type="text/css">
<script src="../base/js/global.js"></script>
<script src="../base/js/dhtmlx/dhtmlXCommon.js"></script>
<script src="../base/js/dhtmlx/dhtmlXTree.js"></script>
</head>

<body scroll="no" style=" overflow:hidden; background:none">
<!--主页面左侧内容-->
<div class="sidebarContainer">
<!--工作导航 START-->
<div class="work">工作导航</div>
<!--子菜单 START-->
<div class="tree" id="treeboxbox_tree"></div>
 <script>				
	tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
	tree.setImagePath("../base/image/tree/");
	tree.enableCheckBoxes(false);
	tree.enableDragAndDrop(false);
	tree.setOnClickHandler(onNodeSelect);
	tree.loadXML("common.treectrl.load.d?showtree=87",onOpenNode);
	      
	function onNodeSelect(){
	  var sID = tree.getSelectedItemId();
	 
	  if (sID.substr(sID.length-4) != "main"){ //屏蔽根结点
		  sID += ".jsp";
		  window.open(sID,"moduletarget");
		  //top.frames["moduletarget"].location.href = "AccManager.html";
	  }
	}
	
	function onOpenNode(){
	  tree.openAllItems();
	}
 </script>
<!--版本号 START-->
<div class="version">版本2009V1.0</div>
</div>
</body>
</html>
