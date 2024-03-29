<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ include file="Message.oni"%>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作导航</title>
<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/header.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/main.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/sidebar.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/navigation.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/login.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="STYLESHEET" type="text/css" href="../base/css/style2/dhtmlx/dhtmlXTree.css" />
<link href="../base/css/style2/Global.css" rel="stylesheet" type="text/css">
<script src="../sm/js/common/global.js"></script>
<script src="/EHL_ATMSIC_GD/general/js/left.js" type="text/javascript"></script>
<script src="../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
<script src="../sm/js/common/dhtmlx/dhtmlXTree.js"></script>
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
	tree.setImagePath("../sm/image/tree/");
	tree.enableCheckBoxes(false);
	tree.enableDragAndDrop(false);
	tree.setOnClickHandler(onNodeSelect);
	tree.loadXML("common.treectrl.load.d?showtree=95",onOpenNode);
	
	function onNodeSelect(id){
	  var sID = id;
	  var itext = tree.getItemText(sID);
	  if (sID.substr(sID.length-4) != "main") //屏蔽根结点
	  {
		  sID = sID + ".jsp?nodeId="+sID+"&nodeDesc="+itext;
		  window.open(sID,"moduletarget");
	  }
	}
	function onOpenNode(){
	  tree.openAllItems();
	  //var manager = new LoopManager(tree);
	  //manager.addNode("950100", "ehl/alarmCount", 1000, true);
	  //manager.addNode("950200", "ehl/dutyPerson", 1000, true);
	  //manager.addNode("950300", "ehl/alarmScanWord", 1000, true);
	  //manager.addNode("950400", "ehl/alarmScanPicture", 1000, true);
	  //manager.start();
	}
	
	</script>
<!--版本号 START-->
<div class="version"></div>
</div>
</body>
</html>
