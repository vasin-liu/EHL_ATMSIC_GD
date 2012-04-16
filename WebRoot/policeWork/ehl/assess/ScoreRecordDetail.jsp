<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../Message.oni"%>
<%
request.setCharacterEncoding("utf-8");
String jgid = request.getParameter("jgid");
String jgmc = request.getParameter("jgmc");
String dayid = request.getParameter("dayid");
String type = request.getParameter("type");
String jfrq = request.getParameter("jfrq");
String reason = request.getParameter("reason");
String typeCode = request.getParameter("typeCode");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>计分录入</title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../sm/js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		
		<style  type="text/css">
			.btn{
				BORDER-RIGHT: #000000 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #000000 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#0c9ad3, EndColorStr=#085887); 
				BORDER-LEFT: #000000 1px solid; CURSOR: hand; COLOR: #ffffff; PADDING-TOP: 2px; BORDER-BOTTOM: #000000 1px solid
			}
			.deptDivClass {
				z-index:10000;
				position:absolute;
				display:inline;
				width:250;
				height:340;
			}
		</style>
	</head>
<body bgcolor="#FFFFFF" >
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;border:1px solid #a5d3ef" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%" >
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif"
									width="16" height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置</b></span>：计分信息明细查看
						</td>
					</tr>
		</table>
			<div id="showLength" style="color:red"> </div>
			<form action="">
			<table class="table3" width="100%" border="1" cellspacing="0" cellpadding="0" id="block" borderColor='#a5d1ec'>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='15%'>交警机构</td>
					<td style="text-indent: 10px;">
						<%=jgmc%>
 					</td>
 					<td bgcolor="#F0FFFF"  align="center" >信息发生时间</td>
					<td style="text-indent: 10px;">
						<%=jfrq %>
 					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" >扣分类型</td>
					<td style="text-indent: 10px;">
						<%=type %>
 					</td>
 					<td bgcolor="#F0FFFF"  align="center" width='15%'>扣分时间</td>
					<td style="text-indent: 10px;">
						<%=dayid %>
					</td>
				</tr>
				<tr height="150px">
					<td bgcolor="#F0FFFF"  align="center" >备注</td>
					<td colspan="3" style="text-indent: 10px; vertical-align: top;">
						<div style="width: 100%;height: 100%;overflow-y:auto;overflow-x:hidden;">
							<%=reason %>
						</div>
					</td>
				</tr>
				
			</table>
			</form>
		</fieldset>
		<input class="btn" type="button" value=" 关闭 " style="margin-top: 10px;"  onclick="window.close();">
	</div>
</body>
</html>