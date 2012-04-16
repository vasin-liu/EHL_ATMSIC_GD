<%@page language="java" contentType="text/html;  charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.appframe.utils.StringHelper" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	    <title>GPS车辆历史轨迹</title>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<%
			//获取有求显示车辆轨迹的车辆编码
			String carCode = StringHelper.obj2str(request.getParameter("carCode"), "");
			
			//车牌号码
			String regnumber = StringHelper.obj2str(request.getParameter("regnumber"), "");
			//驾驶员
			String driver = StringHelper.obj2str(request.getParameter("driver"), "");
		 %>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		
		<script type="text/javascript" src="../../js/instant/traceMap.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript">
		window.attachEvent("onload",pageInit);
	 
		var carCode = '<%=carCode%>';
		var regnumber = '<%=regnumber%>';
		var driver = '<%=driver%>';
		
		function pageInit()
		{
			if( $('btnShowTrace') )
			{
				$('btnShowTrace').onclick = function()
				{
					gpstracemap.readPoints(carCode);
				}
			}
		}
		
		var traceInterval = '<%=com.appframe.common.Setting.getString("gpsTraceInterval")%>';
		
		
		var gpsInterval   = '<%=com.appframe.common.Setting.getString("gpsInterval")%>';
		if( isNaN ( parseInt(traceInterval )))
		{
		 	traceInterval = 2000;
		}
		
		
		</script>
	</head>
	
	<body>
	  <!-- 地图显示 -->
	  <div id="map" style="position: relative; width: 100%; height: 100%; left: 0px; top: 0px;">
	  </div>
    </body>

    <script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
    <script type="text/javascript" src="../../js/instant/traceConfig.js"></script>
</html>