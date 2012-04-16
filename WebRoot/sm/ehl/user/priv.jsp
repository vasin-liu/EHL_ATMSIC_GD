<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.user.PrivManage"%>		
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String PrivCode = request.getParameter("PrivCode");
	String sysCode = request.getParameter("sysCode");
%>
<html>
	<head>
		<title>权限管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script src="../../../base/js/dhtmlx/dhtmlXTree.js"></script>
		<script type="text/javascript" src="../../js/user/sysMenu.js"></script>
		<script type="text/javascript" src="../../js/user/privManage.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
	</head>
<body style="overflow:auto">
	<div style="padding-left: 8px;text-align: center;overflow:auto">
		<fieldset style="width:400;height:400;border:1px solid #CCCCCC" align="center" >
			<br>
			<legend style="border:0px;"><%=insrtOrUpdt.equals("0") ? "权限信息新增" : "权限信息编辑"%></legend>
			<table  style="text-align:right" width="400px">
				<tr style="display:none">
					<td width="25%">权限ID：</td>
					<td width="65%">  <input type="text" class="text" id="PrivCode" maxLength="4" value=<%=PrivCode %> /> </td>
					<td width="3%">※</td>
				</tr>
			    <tr>
		    	<td width="25%" align="right">系统类别：</td>
				<td width="65%"> 
				<table width="100%"> 
				  <tr> 
				  <%if(insrtOrUpdt.equals("0")){ %>
					<td id="tdXtlb" align="center" style="padding-left: 15px;">				
						<script language="javascript">
							fillListBox("tdXtlb","XTLB","278","SELECT sysid,sysname FROM t_sys_config where IsDisplay = '1'","未选择","setValue(<%=sysCode %>);","getMenu");
					  	</script>
					</td>					
					<%}else{ %>
					<td id="tdXtlb"style="display:none">					
						<script language="javascript">
							fillListBox("tdXtlb","XTLB","275","SELECT sysid,sysname FROM t_sys_config where IsDisplay = '1'","未选择","doQuery(<%=PrivCode %>);","");
					  	</script>
					</td>				
					<td> 
					  <input type="text" class="text" id="sysID" style="width:280px;"/>
					</td>										
					<% }%> 

				  </tr>
				</table> 											
				</td>								
				<td width="10%" align="left"class="RedFont">※</td>			
			    </tr>			
				<tr>
					<td width="25%"align="right">权限名称：</td>
					<td width="65%"><input type="text" id="PrivName"style="width:280px;" maxLength="64" class="text" /></td>
					<td width="10%" align="left"class="RedFont">※</td>
				</tr>								
				<tr id="trid" style="display:none">
					<td align="right"valign="top">选择功能项：</td>
					<td>
						<div id="menu"></div>
					</td>
					<td width="10%" align="left"class="RedFont">※</td>
				</tr>
				<tr>
					<td align="right">备注：</td>
					<td><input type="text" id="Remark" style="width:280px;"maxLength="32" class="text" /></td>
					<td width="10%" align="left"></td>
				</tr>
				<tr>
					<td class="tdRight" colspan="2">
						<div style="text-align:center">
							<input type="image" src="../../image/button/btnsave.gif" name="button"   value="<msg:Common_zh.Global.save.desc>" onClick="addOrUpdate(<%=insrtOrUpdt %>);">
		                 	<input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html>
