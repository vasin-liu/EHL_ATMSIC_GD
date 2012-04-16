<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.page import="com.ehl.sm.pcs.DepartmentTree"/>
<jsp:directive.page import="com.ehl.sm.pcs.DepartmentManage"/>
<%@ include file="../../../sm/Message.oni"%>
<%@page import="com.ehl.cctv.CctvTools"%>
<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String deptid=strObj[0];//单位编码
  //  String jgmc=strObj[1];//单位名称
    String jgccbm=strObj[2];//机构层次编码

%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	    <title>视频专题地图</title>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/device/VidiconInfoMap.js"></script>
		<script type="text/javascript" src="../../js/device/CctvView.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>	
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		<script language="javascript">
			var deptidForCenter = '<%=deptid%>';
			var jgccbm = '<%=jgccbm%>';
			var model='<%=CctvTools.MODELTYPE%>'
		</script>
	</head>
	<body bottomMargin ="0" leftMargin="0" topMargin="0" rightMargin="0">
	  	<!-- 地图显示 -->
		<div id="map" style="position:relative;width:99.8%;height:99.5%;left:2px;top:3px;">
		</div>
    </body>
    <script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
    <script type="text/javascript" src="../../js/device/VidiconInfoMapConfig.js"></script>
</html>