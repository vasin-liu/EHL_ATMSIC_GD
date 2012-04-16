<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%
	String HPZL = request.getParameter("HPZL");
	String HPHM = request.getParameter("HPHM");
%>
<html>
  <head>
    <title>相关车辆信息查询</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/vehicle/VehicleQuery.js"></script>
  </head>

	<body class="body" onload="vehicleLinkQuery('<%=HPZL %>','<%=HPHM %>')">
		<div id="dContainer" class="table_tab" align="center">
			<ul id="tags">
				<li class='selectTag'>
					<a href='#' class="td_2">车辆信息</a>
				</li>
			</ul>

			<!--数据表格2009-4-28 -->
				<table width="100%" border="0" height="25%" style="margin-left: 2;">
					<tr height="100%">
						<td width="45%" height="100%" valign="top">
							<div id="tdData" class="rollDiv">
								<table id="tabVeh" class="scrollTable" width=100% cellSpacing=0
									cellPadding=0>
									<tr class='scrollColThead' valign="top"
										style="text-decoration: none; background-color: #B4C1E2; line-height: 20px;">
										<td width='15%' align='center'>
											机动车序号
										</td>
										<td width='10%' align='center'>
											号牌种类
										</td>
										<td width='10%' align='center'>
											号牌号码
										</td>
										<td width='10%' align='center'>
											中文品牌
										</td>
										<td width='15%' align='center'>
											车辆类型
										</td>
										<td width='10%' align='center'>
											车身颜色
										</td>
										<td width='30%' align='center'>
											机动车所有人
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			<!--数据表格2009-4-28-->

			<!--详细数据表格2009-4-28 -->
				<table width="100%" border="0" height="65%" style="margin-left: 2;">
					<tr height="100%">
						<td width="100%" height="100%" valign="top">
							<div id="detailData" class="rollDiv">
								<table id="tabVeh" class="scrollTable" width=100%
									cellSpacing=0 cellPadding=0 align="center">

								</table>
							</div>
						</td>
					</tr>
				</table>
			<!--详细数据表格2009-4-28-->
			<!--详细数据表格2009-4-28 -->
				<table width="100%" border="0" height="5%" style="margin-left: 3;">
					<tr height="100%">
						<td width="100%" height="100%" valign="top" align="center">
						<input type="image" src="../../image/button/btnclose.gif" onclick="window.close();" />
						</td>
					</tr>
				</table>
			<!--详细数据表格2009-4-28-->
		</div>
	</body>
</html>	   