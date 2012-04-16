<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String RYID = request.getParameter("RYID");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../../base/js/dhtmlx/dhtmlXTree.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
	</head>
<body style="overflow: hidden">
	<fieldset style="width:300px;border:1px solid #CCCCCC" align="center">
		<br>
		<legend style="border:1px;">
			机构树：
		</legend>
		<table align="center">
			<tr>
				<td>请选择机构：</td>
			</tr>
			<tr>
				<td>
					<div id="treeboxbox_tree" style="width:250px;height:260px;" class="text"></div>
					<script> 
			            tree = new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
			            tree.setImagePath("../../image/tree/");
			            
			            //设置鼠标点击事件
						//tree.setOnClickHandler(setSSJGID);
						
			            <%if(insrtOrUpdt.equals("0")){%>
			            tree.loadXML("pcs.personManage.load.d");
						<%}else{%>
						tree.loadXML("pcs.personManage.load.d?RYID= "+ <%=RYID%>);
						<%}%>
			            
					</script>
				</td>
			</tr>
			<tr>
				<td class="tdRight" colspan="2">
					<div align="center">
						<input type="button" id="dbutton" value="确定" onClick="checkDept();">
						<input type="button" id="cbutton" value="关闭" onClick="window.close();">
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
</body>
</html>
