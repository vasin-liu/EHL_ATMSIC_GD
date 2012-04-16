<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="com.ehl.gps.GPSUtility" %>
<%
	String userName = GPSUtility.getCurrentUserName(request);	//登陆用户标识
%>
 
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	    <title>GPS分类查询</title>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
        <script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/DXMLCreateTree.js"></script>
		<script type="text/javascript" src="../../js/instant/InstantMap.js"></script>
		<script type="text/javascript" src="../../js/DhtmlPaging.js"></script>
		<script type="text/javascript" src="../../js/DhtmlWindow.js"></script>
		<script type="text/javascript" src="../../js/GPSHelper.js"></script>
		
		<script type="text/javascript">
			
			
			var classify = 'true';
		
			window.attachEvent("onload",pageInit);
			var userName = '<%=userName%>';
			var gpsInterval = 60;			
			function pageInit()
			{
				gpsInterval = '<%=com.appframe.common.Setting.getString("gpsInterval")%>';
			}
		</script>
	</head>
	
	<body>
	  <!-- 地图显示 -->
	  <div id="map" style="position: relative; width: 100%; height: 100%; left: 0px; top: 0px;">
	  </div>
    </body>

    <script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
    <script type="text/javascript" src="../../js/instant/InstantConfig.js"></script>
</html>