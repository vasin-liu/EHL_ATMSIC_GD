<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%
	String SGBH = request.getParameter("SGBH");
	String XZQH = request.getParameter("XZQH");
%>
<html>
	<head>
		<title>事故详细信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
		<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css"
			Type="text/css">
		<link href="../../css/tira.css" rel="stylesheet" type="text/css">
		<script type="text/javascript"
			src="../../../sm/js/common/prototype.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/popup/Popup.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/page/PageCtrl.js"></script>
		<script type="text/javascript"
			src="../../js/accident/AccidentQuery.js"></script>
	</head>
	<body class="body"
		onload="accidentDetailsQuery('<%=SGBH %>','<%=XZQH %>')">
		<div id="loadind">
			<table id="linkDetail" width=97% cellSpacing=0 align="center">
				<tr valign="middle" height="40px">
					<td>
						<a href="#" onclick="person('<%=SGBH %>','<%=XZQH %>')"
							class="td_2">当事人信息</a>
					</td>
				</tr>
			</table>
			<!--数据表格2009-4-23 18:27:46-->
			<div id="detailData" class="rollDiv">
				<table id="tabVeh" class="scrollTable" width=97% cellSpacing=0
					cellPadding=0 align="center" border="0">
					<tr>
						<td>
						</td>
					</tr>
				</table>
			</div>
			<!--数据表格2009-4-23 18:27:52-->
			
			<table id="colse" width=97% cellSpacing=0 align="center" border="0" height="40px">
				<tr valign="bottom">
					<td width="100%" height="100%" valign="bottom" align="center">
						<input type="image" src="../../image/button/btnclose.gif"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
