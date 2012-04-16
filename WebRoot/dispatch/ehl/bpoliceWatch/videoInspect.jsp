<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.util.*" %>
<%
	Hashtable propVideo = DispatchUtil.getCurrentUserData(request);
	
 %>
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：videoInspect.jsp
	 * 摘 要：视频监控界面。

	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-14
	 * 修改人：
	 * 修改日期：
 -->
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <title>视频监控</title>
    <link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
	<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
	<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
	
	<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
	<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
	
	<script type="text/javascript" src="../../js/policeWatch/videoInspect.js"></script>
	<script type="text/javascript" src="../../js/policeWatch/videoInspectMap.js"></script>
	<script type="text/javascript" src="../../js/common/utility/DhtmlPaging.js"	></script>
	<script type="text/javascript" src="../../js/common/utility/comLogic.js"	></script>
	<script src="../../js/common/utility/DhtmlBranchTree.js"	type="text/javascript"></script>
	 
	<script type="text/javascript">
		var userCountyCode = '<%= (String)propVideo.get("BRANCHID") %>';
		var hrefPhasic    = '<%=com.appframe.common.Setting.getString("hrefPhasic") %>';
	</script>
	
  </head>
  <body>
    <!-- 地图显示 -->
	  <div id="map" style="position: relative; width: 100%; height: 100%; left: 0px; top: 0px;">
	  </div>
  </body>
  <script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
  <script type="text/javascript" src="../../js/policeWatch/videoInspectCnfg.js"></script>
</html>
