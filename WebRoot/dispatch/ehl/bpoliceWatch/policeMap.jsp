<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.ehl.dispatch.util.*" %>
<%@page import="java.util.Hashtable" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	
	//当前用户信息
	String deptcode = ""; 	//部门编码
	String deptname = ""; 	//部门名称
	String pname = ""; 		//姓名
	String uname = ""; 		//帐号
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
	}
%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>'
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		
		<script type="text/javascript" src="../../js/policeWatch/dispatchWatching.js"></script>
		<script type="text/javascript" src="../../js/common/utility/DhtmUtilTable.js"></script>
		<script type="text/javascript" src="../../js/Supervise/supervise.js"></script>
		<script type="text/javascript" src="../../js/policeFeedback/policeFeedback.js"></script>
		<script type="text/javascript" src="../../js/editPolice/editPolice.js"></script>
		<script type="text/javascript" src="../../js/complement/complementEdit.js"></script>
		<script type="text/javascript" src="../../js/common/utility/ListChose.js"></script>
		<script type="text/javascript" src="../../js/common/utility/comLogic.js"></script>
		<script type="text/javascript" src="../../js/common/utility/DivHolder.js"></script>
		<script type="text/javascript" src="../../js/common/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/common/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/editPolice/PoliceTool.js"></script>
</head>
<body>

 	<div id="map" style="position: relative; width: 100%; height: 100%; left: 0px; top: 0px;">
	 </div>
	  
	  	<script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
		<script type="text/javascript" src="../../js/editPolice/policeConfig.js"></script>
</body>
</html>