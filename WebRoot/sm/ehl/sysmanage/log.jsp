<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String did = request.getParameter("did"); 
%>
<html>
	<head>
		<title>日志管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	  
        <script type="text/javascript" src="../../js/sysmanage/logManage.js"></script> 
     </head>
<body onload ="doQuery(<%=did %>);">
  <div style="padding-left: 15px;text-align: center">
	<fieldset style="width:280;height:300;border:1px solid #CCCCCC" align="center" ><br>
	<legend style="border:0px;">日志信息明细</legend>
		<table width="300" align="center" class="tableInput" id="dataTable">
		   <tr style="height:5">
			    <td width="25%"></td>
			    <td width="75%"></td>
			</tr>
			<tr>
			    <td width="25%"><msg:Common_zh.logManage.jsp.lcode>：</td>
			    <td width="75%"><input type="text" class="text" id="did" value=<%=did %> readonly=true/></td>
			</tr>
			<tr>
			    <td width="25%"><msg:Common_zh.logManage.jsp.uname>：</td>
			    <td width="75%"><input type="text" name="pname" class="text" id="pname" readonly=true/></td>
			</tr>
			<tr>
				<td width="25%"><msg:Common_zh.logManage.jsp.etime>：</td>
				<td width="75%"><input type="text" name="etime" class="text" id="etime" readonly=true/></td>
			</tr>
			<tr>
				<td width="25%"><msg:Common_zh.logManage.jsp.qtime>：</td>
				<td width="75%"><input type="text" name="qtime" class="text" id="qtime" readonly=true/></td>
			</tr>
			<tr>
				<td width="25%">IP：</td>
				<td width="75%"><input type="text" name="ipaddress" class="text" id="ipaddress" readonly=true/></td>
			</tr>
			<tr>
				<td width="25%"><msg:Common_zh.log.jsp.event>：</td>
				<td width="75%"><div  id="event"   name="event" style="width:100%;height:160;overflow:auto;" readonly=true class="text" align="left"></div></td>
			</tr>
			<tr>
	    
        <td class="tdRight" colspan="2">
	        <div align="center">
		      	<input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
            </div>
        </td>
     </tr>
		</table>
	</fieldset>
	</div>
</body>
</html>
