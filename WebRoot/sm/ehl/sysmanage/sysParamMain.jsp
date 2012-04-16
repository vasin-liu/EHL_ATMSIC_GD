<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ include file="../../../base/Message.oni"%>	
<%@ page import="com.ehl.sm.sysmanage.SysParamData" %>
	<!--
	    * author：zhaoyu
	    * date:   2007-01-23
	    * version:
	-->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!--  	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">-->
		<Link Rel="STYLESHEET" Href="../../../base/css/style2/pagetag/pagetag.css" Type="text/css">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script type="text/javascript" src="../../js/sysmanage/sysParamMain.js"></script>
	</head>
<body align="center">
	<table align=center  class="font1"> 
		<caption><font STYLE="font-size:12pt" color="#99C4F2">
			<strong><msg:Common_zh.sysparam.main.title></strong></font>
		</caption>
	</table>
	<ul id="tags" align = "center">
		<%=SysParamData.getType() %>
	</ul>
	<div id="tagContent"  align = "center" >
		<%=SysParamData.getParam() %>
	</div>
</body>
</html>
