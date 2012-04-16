<!DoCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ page import="com.ehl.sm.pcs.*"%>

<%
	String deviceid = request.getParameter("deviceid");
%>
<html>
	<head>
		<title>PDA管理
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/PDAManage.js"></script>
	</head>
	<body onLoad="doQuery('<%=deviceid %>');">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:340px;height:390px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:1px;">PDA信息明细</legend>
			<table width="90%" align="center" class="tableInput" id="dataTable">
				
				<tr>
					<td width="14%">设备编号：</td>
					<td width="44%"><input type="text" class="text" id="deviceid" readOnly=true></input></td>
				</tr>
				<tr>
					<td>所属部门：</td>
					<td><input type="text" class="text" id="deptname" maxLength="20" readOnly=true></input></td>
				</tr>
				<tr>
					<td>警员姓名：</td>
					<td><input type="text" class="text" id="policename" readOnly=true></input></td>
				</tr>
				<tr>
					<td>是否在线：</td>
					<td><input type="text" class="text" id="devicestate" readOnly=true></input></td>
				</tr>
				<tr>
					<td>经度：</td>
					<td><input type="text" class="text" id="longitude" readOnly=true></input></td>
				</tr>
				<tr>
					<td>纬度：</td>
					<td><input type="text" class="text" id="latitude" readOnly=true></input></td>
				</tr>
				<tr>
					<td>速率：</td>
					<td><input type="text" class="text" id="velocity" readOnly=true></input></td>
				</tr>
				<tr>
					<td>方向：</td>
					<td><input type="text" class="text" id="angle" readOnly=true></input></td>
				</tr>
				<tr>
					<td>GPS时间：</td>
					<td><input type="text" class="text" id="gpstime" readOnly=true></input></td>
				</tr>
				<tr>
					<td>上班时间：</td>
					<td><input type="text" class="text" id="ondutytime" readOnly=true></input></td>
				</tr>
				<tr>
					<td>下班时间：</td>
					<td><input type="text" class="text" id="offdutytime" readOnly=true></input></td>
				</tr>
				
				<tr>
					<td colspan="4"><br>
						<div align="center"><img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();"></div>
					</td>
				</tr>
			</table>
		</fieldset>
		</div>
	</body>
</html>
