<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.user.RoleManage"%>		
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String did 		   = request.getParameter("did"); 
	String rcode  = "";
	 if(insrtOrUpdt.equals("1")){
		rcode =did;
	}	
%>
<html>
<head>
	<title>角色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	
	<script src="../../../base/js/dhtmlx/dhtmlXTree.js"></script>	
	<script type="text/javascript" src="../../../base/js/global.js"></script>
	<script type="text/javascript" 	src="../../js/user/RoleManage.js"></script>
	<script type="text/javascript" src="../../js/user/privMenu.js"></script>	
</head> 
<body onload ="doQuery(<%=did %>,<%=insrtOrUpdt %>);loadPrivTree(<%=did %>,<%=insrtOrUpdt %>);">
 <div style="padding-left: 15px;text-align: center">
	<fieldset style="width:440;height:480;border:1px solid #CCCCCC" align="center" >
	<br>
	    <legend style="border:0px;"><%=insrtOrUpdt.equals("0")?"角色信息新增":"角色信息编辑"%></legend>
	    <table width="440px" style="text-align:right">
		     <tr id="codetr">
		      <td width="20%"><msg:Common_zh.roleManage.jsp.rcode>：</td>
		      <td width="77%"><input type="text" class="text" id="did" maxLength="32" value=<%=rcode %> readonly=true /></td>
		      <td width="3%" class="RedFont">※</td>
		     </tr>
		     <tr>
		      <td width="20%"><msg:Common_zh.roleManage.jsp.rname>：</td>
		      <td width="77%"><input  type="text" id="jname" maxLength="64" class="text"/></td>
		      <td width="3%" class="RedFont">※</td>
		     </tr>
		     <tr>
		      <td width="20%"><msg:Common_zh.role.jsp.desc>：</td>
		      <td width="77%"><input type="text" id="desc" maxLength="32" class="text"/></td>
		    </tr>

	   		<tr>
				<td width="20%" valign="top"><msg:Common_zh.role.jsp.select>：</td>
				<td width="77%">
					<div id="role_id" align="left"  class="text" style="width:100%;text-align:left;overflow-y:auto;overflow-x:hidden;overflow:auto;height:300;">									
					</div>
				</td>
				<td width="3%" class="RedFont">※</td>
			</tr>
		    <tr>
		      <td width="20%"><msg:Common_zh.Global.remark.desc>：</td>
		      <td width="77%"><input  type="text" id="remark" maxLength="128" class="text" /></td>
		      <td>&nbsp;</td>
		    </tr>			
	    	<tr>
		        <td class="tdRight" colspan="2">
		            <div align="center">
		            		<input type="image" src="../../image/button/btnsave.gif" name="button" value="<msg:Common_zh.Global.save.desc>" onClick="addOrUpdate(<%=insrtOrUpdt %>);">
							<%if(insrtOrUpdt.equals("0")) {%>
								<input type="image" src="../../image/button/btnreset.gif" name="button"	value="<msg:Common_zh.Global.reset.desc>" onClick="reset();" />
							<%}%>
		                 	<input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
		            </div>
		        </td>
	        </tr>
		</table> 
	</fieldset>
	</div>
</body>
</html>
