<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	    <title>地图标签维护</title>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<script type="text/javascript">
	    	window.onerror = function(sMessage,sUrl,sLine)
	    	{
	    		alert("执行脚本过程出现错误:\n" + sMessage + "\n" + "地址:" + sUrl + "\n行号:" + sLine);
	    		return true;
	    	}
        </script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/sysmanage/LabelMap.js"></script>
		<script type="text/javascript" src="../../js/sysmanage/LabelTool.js"></script>
	</head>
	
	<body style="overflow:hidden" bottomMargin ="0" leftMargin="0" topMargin="0" rightMargin="0">
	  <!-- 地图显示 -->
	  <div id="map" style="position: relative; width: 100%; height: 100%; left: 2px; top: 3px;">
	  </div>
    </body>

    <script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
    <script type="text/javascript" src="../../js/sysmanage/LabelMapConfig.js"></script>
</html>