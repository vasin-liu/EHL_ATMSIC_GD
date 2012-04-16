<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%
	String XH = request.getParameter("XH");
%>
<html>
  <head>
    <title>车辆详细信息</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/vehicle/VehicleQuery.js"></script>	
  </head>
	<body class="body" onload="vehicleDetailsQuery('<%=XH %>')">
		<div id="loadind">
			<table id="linkDetail" width=97% cellSpacing=0 align="center"
				cellPadding=0>
				<tr height="40px" valign="middle">
					<td>
						<a href="#" onclick="violationLinkQuery('<%=XH %>')" class="td_2">相关违法信息</a>
					</td>
				</tr>
			</table>
			<!--数据表格2009-4-23 18:27:46-->
			<div id="detailData">
				<table id="tabVeh" class="scrollTable" width=97% cellSpacing=0
					align="center" cellPadding=0>
					<tr>
						<td>
						</td>
					</tr>
				</table>
				<table>
					<tr height="100%">
						<td align="center" width="100%" height="20px" valign="bottom">
							<input type="image" src="../../image/button/btnclose.gif"
								onclick="window.close();" />
						</td>
					</tr>
				</table>
			</div>
			<!--数据表格2009-4-23 18:27:52-->
			<div id="colse">
				<table width=97% cellSpacing=0 align="center" cellPadding=0>
					<tr>
						<td align="center" width="100%" height="30px" valign="bottom">
							<input type="image" src="../../image/button/btnclose.gif"
								onclick="window.close();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
	   