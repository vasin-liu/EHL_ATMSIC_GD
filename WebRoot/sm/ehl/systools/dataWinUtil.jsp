<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*" %>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String did = request.getParameter("did");

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>数据窗口定义</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<script src="../../../base/js/prototype.js"></script>
		<script src="../../../base/js/table/dataWinUtil.js"></script>
		<script src="../../../base/js/dhtmlx/xmlCreator.js"></script>
		<script language="javascript"></script>
	</head>
	<body onLoad="doQuery(<%=did %>);">
	<div style="padding-left: 15px;text-align: center">
	    <fieldset style="width:300;height:210;border:1px solid #CCCCCC"align="center">
	          <br><legend style="border:0px;">
	       <%=insrtOrUpdt.equals("0")?"数据窗口新增":"数据窗口编辑"%>   
       <br></legend>    
		<table align="center" id="dataTable">
			<tr><td><div align="right">编号：</div></td><td><input name="did" type="text" id="did" atrbt="did" value=<%=did %>/></td></tr>			 
			<tr><td><div align="right">报表名：</div></td><td><input name="dname" type="text" id="dname" atrbt="dname"/></td></tr>
			<tr><td><div align="right">相关表格编号：</div></td><td><input name="relatedTables" type="text" id="relatedTables" atrbt="dvalue"/></td></tr>
			<tr><td><div align="right">描述：</div></td><td><input name="description" type="text" id="description" atrbt="description"/></td></tr>
			
			<tr><td colspan="2"><br><div align="center">
			  <img src="../../image/button/btnsave.gif" style="cursor:hand" onClick="modify(<%=insrtOrUpdt %>);">
			  <img src="../../image/button/btnreset.gif" style="cursor:hand" onClick="reset();">
			  <img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();">
			
			  </div></td></tr>
		</table>
	    </fieldset>
	    </div>
	</body>
</html>
