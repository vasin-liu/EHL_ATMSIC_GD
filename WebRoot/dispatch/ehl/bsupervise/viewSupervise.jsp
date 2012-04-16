<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%
	String dbbh = StringHelper.obj2str(request.getParameter("dbbh"),"");
	String dbsj = StringHelper.obj2str(request.getParameter("dbsj"),"");
	String dbr = StringHelper.obj2str(request.getParameter("dbr"),"");
	String dbdw = StringHelper.obj2str(request.getParameter("dbdw"),"");
	String dbnr = StringHelper.obj2str(request.getParameter("dbnr"),"");
	String sjbh = StringHelper.obj2str(request.getParameter("sjbh"),"");
	String bz = StringHelper.obj2str(request.getParameter("bz"),"");
%>
<html>
	<head>
		<title>督办信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style type="text/css">
			.table2{
				background:#ffffff;
				border-top: 1 solid #000000;
				border-right: 0 solid #000000;
				border-bottom: 0 solid #000000;
				border-left: 1 solid #000000;
				border-collapse:collapse;
				font-size:11px;
				text-align: center;
			}
			.td_3{
				border-top: 0 solid #000000;
				border-right: 1 solid #000000;
				border-bottom: 1 solid #000000;
				border-left: 1 solid #000000;
				font-size: 12px;
				text-decoration: none;
				background-color: #B4C1E2;
				line-height: 20px;
			}	
			.td_4{
				border-top: 0 solid #000000;
				border-right: 1 solid #000000;
				border-bottom: 1 solid #000000;
				border-left: 1 solid #000000;
				align:center;
				font-size: 12px;
				text-decoration: none;
				background-color: #ffffff;
			}
		</style>
	</head>
	<body>
		<fieldset style="width:320;height:250;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">督办信息</legend>
			<table width="300" height="200" class="table2" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="37%"  align="right" class="td_3">督办编号：</td>
					<td width="63%" align="left" class="td_4"><%=dbbh %></td>
				</tr>
				<tr>
					<td  align="right" class="td_3">督办时间：</td>
					<td align="left" class="td_4"><%=dbsj %></td>
				</tr>
				<tr>
					<td  align="right" class="td_3">督办人：</td>
					<td width="63%" align="left" class="td_4"><%=dbr %></td>
				</tr>
				<tr>
					<td  align="right" class="td_3">督办目标单位：</td>
					<td width="63%" align="left" class="td_4"><%=dbdw %></td>
				</tr>
				<tr>
					<td  align="right" class="td_3">督办内容：</td>
					<td width="63%" align="left" class="td_4"><%=dbnr %></td>
				</tr>
				<tr>
					<td  align="right" class="td_3">对应事件编号：</td>
					<td width="63%" align="left" class="td_4"><%=sjbh %></td>
				</tr>
				<tr>
					<td  align="right" class="td_3">备注：</td>
					<td width="63%" align="left" class="td_4"><%=bz %></td>
				</tr>
				<tr>
					<td align="center" colspan="2" class="td_4">
						<input type="button" value="返回" onClick="window.close();" />
					</td>
				</tr>
			</table>
		</fieldset>
	</body>
</html>
