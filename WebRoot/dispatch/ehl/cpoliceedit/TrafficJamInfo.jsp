<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),""); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>交通拥堵情况信息</title>
	</head>
<body>
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">交通管制填报表</legend>
			<table class="table2" width="100%">
				<tr>
					<td align="center" class="tdtitle">填报单号</td>
					
					<td align="center" class="tdtitle">填报时间</td>
					
					<td align="center" class="tdtitle">填报人</td>
					
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html>
