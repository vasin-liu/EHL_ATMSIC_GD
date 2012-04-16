<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%
	String SGBH = request.getParameter("SGBH");
	String XZQH = request.getParameter("XZQH");
%>
<html>
  <head>
    <title>当事人信息查询</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/accident/AccidentQuery.js"></script>

  </head>

	<body class="body" onload="personQuery('<%=SGBH%>','<%=XZQH%>')">
		<div id="dContainer" class="table_tab" align="center">
			<ul id="tags">
				<li class='selectTag'>
					<a href='#' class="td_2">当事人信息</a>
				</li>
			</ul>

			<!--数据表格2009-4-28 -->
			<table width="100%" border="0" height="30%" style="margin-left: 2;">
				<tr height="100%">
					<td width="45%" height="100%" valign="top">
						<div id="tdData" class="rollDiv">
							<table id="tabVeh" class="scrollTable" width=100% cellSpacing=0
								cellPadding=0>
								<tr class='scrollColThead' valign="top"
									style="text-decoration: none; background-color: #B4C1E2; line-height: 20px;">
									<td width='17%' align='center'>
										事故编号
									</td>
									<td width='13%' align='center'>
										行政区划
									</td>
									<td width='13%' align='center'>
										人员编号
									</td>
									<td width='13%' align='center'>
										姓名
									</td>
									<td width='18%' align='center'>
										身份证号码
									</td>
									<td width='13%' align='center'>
										地址
									</td>
									<td width='13%' align='center'>
										电话
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<!--数据表格2009-4-28-->

			<table width="100%" border="0" height="60%" style="margin-left: 2;">
				<tr height="100%">
					<td width="100%" height="100%" valign="top">

						<!--详细数据表格2009-4-28 -->
						<div id="detailData" class="rollDiv">
							<table id="tabDtail" class="scrollTable" width=97% cellSpacing=0
								cellPadding=0 align="center">

							</table>
						</div>
						<!--详细数据表格2009-4-28-->
					</td>
				</tr>
			</table>

			<table width="100%" border="0" height="5%" style="margin-left: 3;">
				<tr height="100%">
					<td width="100%" height="100%" valign="top" align="center">
						<input type="image" src="../../image/button/btnclose.gif"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>	   