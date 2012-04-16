<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查看子单信息</title>
	</head>

	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	<script type="text/javascript" src="../../js/common/prototype.js"></script>
	<script type="text/javascript" src="../../js/editPolice/editPolice.js"></script>
	<script type="text/javascript"
		src="../../js/common/list/FillListBox.js"></script>


	<%
	String id = request.getParameter("id");
	%>
	<body onload="getSonList('<%=id %>')">
		<table>
			<tr>
				<td width="30%" valign="top">
					<fieldset>
						<legend>
							子单列表
						</legend>

						<table class="table2" width="240">
							<tbody id="sonListTbody">
							</tbody>
						</table>

					</fieldset>
				</td>
				
				<td valign="top">
					<div id="divEditAlarm" style="width:100%">
						<jsp:include flush="true" page="../policeEdit/policeInfo.jsp"></jsp:include>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
