<%@ page language="java" import="java.util.*" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String classid = request.getParameter("classid");
	String groupid = request.getParameter("groupid");
	String deptid = request.getParameter("deptid");
%>
<html>
	<head>
		<title>GPS车辆详细信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="zt.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="../../sm/css/Global.css">
		<link rel="STYLESHEET" type="text/css"
			href="../../sm/css/popup/Popup.css">
		<link rel="STYLESHEET" type="text/css"
			href="../../sm/css/dhtmlx/dhtmlXGrid.css">
		<link rel="STYLESHEET" type="text/css"
			href="../../sm/css/dhtmlx/dhtmlXGrid_skins.css">
		<link rel="STYLESHEET" type="text/css"
			href="../../sm/css/dhtmlx/dhtmlXToolbar.css">
		<link rel="STYLESHEET" type="text/css"
			href="../../sm/css/dhtmlx/dhtmlXTree.css">
		<script type="text/javascript" src="../../sm/js/common/prototype.js"></script>
 		<script type="text/javascript" src="../js/DhtmlPaging.js"></script>	
		<script type="text/javascript" src="../js/classify/GpsCarDetail.js"></script>
		 
		<script type="text/javascript">
	    
	</script>
	</head>
	<body onLoad="doQuery('<%=classid%>','<%=groupid%>','<%=deptid %>')";>
	    <br>
	    <div id="ddd">
	            <div align="center"
					style="height: 27px; font-weight: 500; float: center; width: 95%">
					<span style="text-align:center">GPS警车详细信息</span>
				</div>
		<div align="center" id="gpscarPaging1" style="offsetTop: 0px;"></div>
		<div id="gpscarQueryResult" class="scrollDiv"
			style="text-align: center"></div>
        </div>
	</body>
</html>
