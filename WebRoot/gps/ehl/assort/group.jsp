<%@ page language="java" import="java.util.*" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../../css/zt.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">  
<link rel="STYLESHEET" type="text/css" href="../../../sm/css/dhtmlx/dhtmlXToolbar.css"> 
<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script> 
<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXProtobar.js"></script> 
<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXToolbar.js"></script> 
<script type="text/javascript" src="../../../sm/js/common/toolbar/toolbarManager.js"></script>
<script type="text/javascript" src="../../js/classify/GpsGroupRole.js"></script>
 <script type="text/javascript">
	function getFunccode() 
	{
		return '<%= request.getParameter("funURI") %>';
	}
</script>
<title>GPS车辆分组</title>
</head>
<body onLoad="load();">
	<center><div id="toolbar_zone" style="width: 700; border: 1px solid Silver;">
	</div></center><br>
	<div style="width:100% ;text-align:center" >
			<font style="color:#0000FF;size:14pt;font-weight:800">GPS车辆分组</font>
	</div>
	&nbsp;&nbsp;<hr style="height:1px"/>
	<div id="grouprole">
	</div>
</body>
</html>