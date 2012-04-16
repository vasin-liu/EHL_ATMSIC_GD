<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<%
	String insrtOrUpdt = request.getParameter("insertorupdate");
    String groupid = request.getParameter("groupid");
%>
<html>
	<head>
		<title>车辆分组管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../css/zt.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
		<link rel="STYLESHEET" type="text/css" href="../../../sm/css/dhtmlx/dhtmlXToolbar.css"> 
		<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script> 
		<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXProtobar.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXToolbar.js"></script> 
		<script type="text/javascript" src="../../../sm/js/common/toolbar/toolbarManager.js"></script> 
		<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script> 
		<script type="text/javascript" src="../../js/classify/GpsGroupRole.js"></script> 
	</head>
	<body onLoad="doQueryById('<%=groupid%>');">
		<div style="padding-left: 15px; text-align: center">
			<fieldset style="width: 360px; height: 180px; border: 1px solid #CCCCCC;text-align:center">
				<br>
				<legend style="border: 0px;">
					车辆分组编辑
				</legend>
				<br>
				<table width="300" align="center" class="tableInput" id="dataTable">
					<tr style="display: none">
						<td width="31%" align="right">
							分组编号：
						</td>
						<td width="66%" id="tdXtlb">
							<input type="text" name="groupid" class="text" id="groupid" />
						</td>
						<td width="3%" class="RedFont">
							※
						</td>
					</tr>
					
					<tr>
						<td width="31%" align="right">
							分组名称：
						</td>
						<td width="66%" id="tdXtlb">
							<input type="text" name="groupname" class="text" id="groupname" />
						</td>
						<td width="3%" class="RedFont">
							※
						</td>
					</tr>
					
					<tr>
						<td width="31%" align="right">
							描述：
						</td>
						<td width="66%">
						<textarea id="remark"></textarea>
						</td>
					
					</tr>
					<tr>
						<td class="tdRight" colspan="2">
							<br>
							<div align="right">
								<input type="image" src="../../images/button/btnsave.gif"
									name="button" value="<msg:Common_zh.Global.save.desc>"
									onClick="save(<%=insrtOrUpdt%>);" />
								<input type="image" src="../../images/button/btnreset.gif"
									name="button" value="<msg:Common_zh.Global.reset.desc>"
									onClick="reset();" />
								<input type="image" src="../../images/button/btnclose.gif"
									name="button" value="<msg:Common_zh.Global.close.desc>"
									onClick="doClose();" />
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</body>
</html>
