<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.common.Constants"%>	
<%@ include file="../../Message.oni"%>
<%
	
	String linkDirId = request.getParameter("linkDirId");
%>
<html>
	<head>
		<title>路况信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../ShareInc.html"></jsp:include>	
		<script type="text/javascript" src="../../js/user/UserManage.js"></script>
		<script type="text/javascript" src="../../js/common/list/FillListBox.js"></script>
	</head>
	<body  SCROLL="no" >
		<fieldset style="width:270;height:170;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">路况信息编辑</legend>
			<table width="230" align="center" class="tableInput" id="dataTable">
				<tr style="display:none">
					<td width="25%">所属辖区：</td>
					<td width="50%"><input id="USERCODE" type="text" maxLength="32" name="" class="text" value=<%=ucode%> readonly=true></input></td>
					<td width="3%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="25%">连线名称：</td>
					<td width="50%"><input id="Opassword" type="password"  maxlength="20" class="text"></input></td>
					<td width="3%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="25%">方向：</td>
					<td width="50%"><input id="password" type="password"  maxlength="20" class="text"></input></td>
					<td width="3%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="25%">道路路况：</td>
					<td width="50%"><input id="Rpassword" type="password"  maxlength="20" class="text"></input></td>
					<td width="3%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="25%">拥堵原因：</td>
					<td width="50%"><input id="Rpassword" type="password"  maxlength="20" class="text"></input></td>
					<td width="3%" class="RedFont">※</td>
				</tr>
				<tr>
					<td class="tdRight" colspan="2">
						<div align="center">
							<input type="image" src="../../image/button/btnsave.gif"  name="button" value="<msg:Common_zh.Global.save.desc>" onClick="editPwd(<%=insrtOrUpdt%>);" />
							<input type="image" src="../../image/button/btnclose.gif" name="button" value="<msg:Common_zh.Global.close.desc>" onClick="window.close();" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</body>
</html>
