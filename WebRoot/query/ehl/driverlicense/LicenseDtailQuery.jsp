<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<% 
	String SFZMHM = request.getParameter("SFZMHM");
 %>
<html>
  <head>
    <title>驾驶员查询</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/driver/LicenseDtailQuery.js"></script>
  </head>
     <style>
     
.showTd { 
    text-decoration: none;
    	background-color: #B4C1E2;
    	line-height: 20px
	}
  </style>
	<body onload="showLicenseDtailInfo('<%=SFZMHM %>')">
		<div id="loadind">
			<table width="100%" height="10%" >
				<tr>
					<td align="left">
					</td>
				</tr>
			</table>
			<!--数据表格2009-4-23 18:27:46-->
			<div id="detailData" class="rollDiv">
				<table id="LicenseTab" class="scrollTable" width="97%" height="45%" cellSpacing=0 align="center" cellPadding=0 >
					<tr>
						<td>
						</td>
					</tr>
				</table>
			</div>
			<!--数据表格2009-4-23 18:27:52-->
			<table>
				<tr>
					<td>
					<td align="center" width="100%" height="30px" valign="bottom">
						<input type="image" src="../../image/button/btnclose.gif"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
