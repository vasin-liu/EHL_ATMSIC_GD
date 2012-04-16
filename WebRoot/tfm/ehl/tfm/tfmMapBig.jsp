<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html >
	<head>
	    <title>路况地图信息发布</title>
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
		<script type="text/javascript"  src="../../../sm/js/common/dhtmlx/xmlCreator.js"></script>				
	    <script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	    <script type="text/javascript" src="../../../sm/js/common/global.js"></script>
	    <script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/tfm/TfmMap.js"></script>
		<style>
		   #roadMap {background:url(../../image/mapInfo.gif) repeat-x;}
           #roadInfo {background:url(../../image/roadinfo.gif) repeat-x;}
		</style>
	</head>
	<body align="center">
	  <!-- 地图显示 -->
		 <table width="99%" height="100%" align="center" border="1">
		    <tr>
		        <td width="71%">
			       <table width="100%" height="100%" align="center">
					    <tr id ="mapTr" style="text-align:center;height:22;" >
					       <td id="roadMap" align="right" style="padding-right:7px">
					       <a href="#" onclick="synLinkDir();">更新道路网</a>
					       </td>
					    </tr>
					    <tr>
					       <td colspan='2'>
					          <div id="map" style="position: relative;  width: 100%; height:98%; left: 0px; top: 0px;">
					          </div>
					       </td>
					    </tr>
					</table>
				</td>
			    
			    
		    </tr>
		 </table>
	 </body>

    <script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
    <script type="text/javascript" src="../../js/tfm/TfmMapConfig.js"></script>
</html>