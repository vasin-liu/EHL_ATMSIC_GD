<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String tid = request.getParameter("tid");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>数据表格定义</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<script src="../../../base/js/prototype.js"></script>
		<script src="../../../base/js/table/dataTableUtil.js"></script>
		<script src="../../../base/js/dhtmlx/xmlCreator.js"></script>
	</head>
	<body onLoad="doQuery(<%=tid %>);">
	<div style="padding-left: 15px;text-align: center">
		<fieldset style="width:360;height:350;border:1px solid #CCCCCC"
			align="center">
			<br>
			<legend style="border:0px;">
				<%=insrtOrUpdt.equals("0")?"数据表格新增":"数据表格编辑"%><br>
			</legend>
			
			<table width="100%" align="center" id="dataTable">
				<tr>
					<td width="44%">
						<div align="right">
							表ID：


						</div>
					</td>
					<td width="35%">
						<input name="tid" type="text" id="tid" atrbt="tid" value=<%=tid %>
							size="18">
					</td><td></td>
				</tr>
				<tr>
					<td width="44%">
						<div align="right">
							表名：


						</div>
					</td>
					<td>
						<input name="tname" type="text" id="tname" atrbt="tname" size="18" />
					</td><td></td>
				</tr>
				<tr>
					<td>
						<div align="right">
							相关数据表：
						</div>
					</td>
					<td>
						<input name="tablename" type="text" id="tablename"
							atrbt="tablename" size="18" />
					</td><td></td>
				</tr>
				<tr>
					<td>
						<div align="right">
							SQL查询条件：


						</div>
					</td>
					<td>
						<input name="selcondition" type="text" id="selcondition"
							atrbt="selcondition" size="18" />
					</td><td></td>
				</tr>
				<tr>
					<td>
						<div align="right">
							整表宽度：


						</div>
					</td>
				
					<td>
						<input name="width" type="text" id="width" atrbt="width" size="18" />
					</td>

					<td>
						<select name="widthed" class='select' id="widthed" />
							<option value="px">
								真实数值


							</option>
							<option value="pt">
								百分比数值


							</option>
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							整表高度：


						</div>
					</td>
					<td>
						<input name="height" type="text" id="height" atrbt="height"
							size="18" />
					</td>
					<td>
						<select name="heighted" class='select' id="heighted" />
							<option value="px">
								真实数值


							</option>
							<option value="pt">
								百分比数值


							</option>
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							允许拖拽：


						</div>
					</td>
					<td >
						<input name="drag" type="drag" id="drag" atrbt="drag" size="18"
							value="0" />
						


					</td><td>"1"为允许 </td>
				</tr>
				<tr>
					<td>
						<div align="right">
							主子关系描述：


						</div>
					</td>
					<td >
						<input name="pcrel" type="text" id="pcrel" atrbt="pcrel" size="18" />
					</td><td></td>
				</tr>
				<tr>
					<td>
						<div align="right">
							父表ID：


						</div>
					</td>
					<td >
						<input name="parentid" type="text" id="parentid" atrbt="parentid"
							size="18" />
					</td><td></td>
				</tr>
				<tr>
					<td colspan="3"><br>
						<div align="center">
							<img src="../../image/button/btnsave.gif" style="cursor:hand" onClick="modify(<%=insrtOrUpdt %>);">
							<img src="../../image/button/btnreset.gif" style="cursor:hand" onClick="reset();">
							<img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();">
						</div>
					</td>
			</table>
		</fieldset>
		</div>
	</body>
</html>
