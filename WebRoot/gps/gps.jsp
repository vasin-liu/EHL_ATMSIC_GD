<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="com.ehl.gps.GPSUtility" %>
<%
	String userName = GPSUtility.getCurrentUserName(request);	//登陆用户标识
 	String classify = request.getParameter("classify");
 	
 	if( null == classify)
 	{
 		classify = "0";
 	}
%>
 
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	    <title>GPS车辆实时信息</title>
		<link type="text/css" rel="Stylesheet" href="../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../webgis/css/contents.css" />
        <script type="text/javascript" src="../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="js/Atms45GPSTree.js"></script>
		<script type="text/javascript" src="js/GPSInstantMap.js"></script>
		<script type="text/javascript" src="js/DhtmlPaging.js"></script>
		<script type="text/javascript" src="js/DhtmlWindow.js"></script>
		<script type="text/javascript" src="js//DhtmBranchTree.js"></script>
		<script type="text/javascript" src="js/GPSHelper.js"></script>
		
		<script type="text/javascript">
			
			
			var classify = '<%= classify %>';
		
			window.attachEvent("onload",pageInit);
			var branch_param1 = null;
			var branch_param2 = null;
			var branch_param3 = null;
			var branch_code	= null;
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

    <script type="text/javascript" src="../webgis/script/map/MapConfig.js"></script>
    <script type="text/javascript" src="js/GPSInstantConfig.js"></script>
</html>