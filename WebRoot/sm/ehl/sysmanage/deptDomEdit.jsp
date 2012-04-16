<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.sysmanage.DeptDomManage"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串

    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//单位名称
    String ccbm=strObj[2];//单位层次编码
 %>
<%
	String strFlag =request.getParameter("strFlag") == null? "" :request.getParameter("strFlag");// 0:新增；1：修改

	String strJgid = request.getParameter("strJgid")== null? "" :request.getParameter("strJgid"); //单位编码
%>	
<html>
	<head>
		<title>警用辖区权限分配</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	 
	    <script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>	 
		<script type="text/javascript" src="../../js/sysmanage/deptDomManage.js"></script>
     </head>
<body onLoad="getDeptdomById('<%=strJgid%>');">
	<div style="padding-left: 15px;text-align: center">
	   <fieldset style="width:380;height:400;border:1px solid #CCCCCC" align="center" >
	   <br> 
	   <legend style="border:0px;"><%=strFlag.equals("0")?"警用辖区权限新增":"警用辖区权限编辑"%></legend><br>
		   <table width="380" align="center" class="tableInput" id="dataTable">
		   <% if(strFlag.equals("0")){%>
			<tr>
				<td align="left" width="22%">所属单位：</td>
				  <td align="left" width="65%" >
				  <input type="text" style="width:100%;"name="tnDepartmentName" id='tiDepartmentName'  onClick="reset_dept('tiDepartmentName');"readonly/>				  
				  </td>
				<td align="left">
					<div><img src="../../image/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" onClick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','tiDepartmentName','40','110')"></div>
				</td>
				<td width="10%"align="left" class="RedFont"><p align="left">※</p></td>
			</tr>
			 <% }else{%>
			 <tr>
				<td align="left" width="22%">所属单位：</td>
				  <td align="left" width="60%"colspan="2" >
				  <input type="text" style="width:100%;"name="tnDepartmentName" id='tiDepartmentName' readonly></input>
				  <input type="text" style="width:0%;"name="tnDepartmentID" id='tiDepartmentID'value="<%=strJgid %>" style="display:none" readonly/>
				  </td>
				  <td width="10%"align="left" class="RedFont"><p align="left"></p></td>
			</tr>			 
			 <% }%>
		    <tr>
		        <td align="left" width="20%">辖区名称：</td>
		        <td align="left" width="70%" colspan="2">
				  <div id="tiXqbh" align="left"style=" border: 1px solid #7f9db9;overflow:auto;height:280;width:100%;">
				    <%=DeptDomManage.getXqNameList()%>
				  </div>
			  </td>
			  <td width="10%"align="left" class="RedFont"><p align="left">※</p></td>
		    </tr>
			<tr>			    
		        <td class="tdRight" colspan="4"><br>	        
			        <div align="center">
				      	<input type="image" src="../../image/button/btnsave.gif"  name="button"  value="<msg:Common_zh.Global.save.desc>"  onClick="doSave('<%=strJgid%>','<%=strFlag%>');"/>
				        <input type="image" src="../../image/button/btnreset.gif" name="button"  value="<msg:Common_zh.Global.reset.desc>" onClick="doReset();"/>
				        <input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="doClose();"/>
		            </div>
		        </td>
		     </tr>
			</table> 
		</fieldset>
	</div>
</body>
</html>
