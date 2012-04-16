<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String did = request.getParameter("did");
%>
<html>
	<head>
		<title>功能管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	    
		<script type="text/javascript" src="../../js/user/FunctionManage.js"></script>
	</head>
	<body onload="doQuery('<%=did%>');">
	<div style="padding-left: 15px;text-align: center">
		<fieldset style="width:300;height:300;border:1px solid #CCCCCC"
			align="center"><br>
			<legend style="border:0px;">
			<%=insrtOrUpdt.equals("0")?"功能信息新增":"功能信息编辑"%>
			</legend>
			<input type="hidden" id="oldid" value="">
			<table width="95%" align="center" id="dataTable">
				<tr>
					<td width="27%" align="right"><msg:Common_zh.function.jsp.fcode>:</td>
					<td width="70%"><input type="text" id="fcode" value="" size="24" maxlength="6"/></td>
					<td width="3%" align="left" class="RedFont">※</td>
				</tr>
				<tr>
					<td align="right"><msg:Common_zh.function.jsp.fname>:</td>
					<td><input name="text" type="text" id="text" size="24" maxlength="32"/></td>
					<td width="3%" align="left" class="RedFont">※</td>
				</tr>
				<tr>
					<td align="right">链接地址:</td>
					<td><input type="text" id="did" value="" size="24" maxlength="32"/></td>
				</tr>
				<tr style="display:none">
					<td align="right"><msg:Common_zh.function.jsp.n_parent_id>:</td>
					<td><input id="parent" type="text" size="24" maxlength="32"/></td>
				</tr>
				<tr>
					<td align="right"><msg:Common_zh.function.jsp.im0>:</td>
					<td><input name="im0" type="text" id="im0" size="24" maxlength="32"/></td>
				</tr>
				<tr>
					<td align="right"><msg:Common_zh.function.jsp.im1>:</td>
					<td><input name="im1" type="text" id="im1" size="24" maxlength="32"/></td>
				</tr>
				<tr>
					<td align="right"><msg:Common_zh.function.jsp.im2>:</td>
					<td><input name="im2" type="text" id="im2" size="24" maxlength="32"/></td>
				</tr>
				<tr style="display:none">
					<td align="right"><msg:Common_zh.function.jsp.n_call>:</td>
					<td><input name="n_call" type="text" id="n_call" size="24" maxlength="32"/></td>
				</tr>
				<tr style="display:none">
					<td align="right"><msg:Common_zh.function.jsp.n_select>:</td>
					<td><input name="n_select" type="text" id="n_select" size="24" maxlength="32"/></td>
				</tr>
				<tr>
					<td align="right">是否显示:</td>
					<td>
				 <select name="IsDisplay" id="IsDisplay" style="width:180px;" >				  
				  <option value="1">显示</option>
				  <option value="0">隐藏</option>
				</select>
					</td>
				</tr>
				<tr>
					<td align="right"><msg:Common_zh.Global.remark.desc>:</td>
					<td><input name="remark" type="text id="remark"  size="24" maxlength="32"/></td>
				</tr>								
				<tr>
					<td colspan="2">
						<div align="center">
							<input type="image" src="../../image/button/btnsave.gif" name="button" value="<msg:Common_zh.Global.save.desc>"onClick="addOrUpdate(<%=insrtOrUpdt%>);">
							<%if(insrtOrUpdt.equals("0")){ %>
							  <input type="image" src="../../image/button/btnreset.gif"  value="<msg:Common_zh.Global.reset.desc>" onClick="reset();"/>
							  <%} %>
							<input type="image" src="../../image/button/btnclose.gif" name="button" value="<msg:Common_zh.Global.close.desc>"onClick="window.close();" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset></div>
	</body>
</html>
