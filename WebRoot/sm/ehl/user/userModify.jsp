<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.Constants"%>	
<%@ page import="com.ehl.sm.user.UserManage"%>	
<%
	String insrtOrUpdt =request.getParameter("insrtOrUpdt") == null? "" :request.getParameter("insrtOrUpdt");
	//String did = request.getParameter("did")== null? "" :request.getParameter("did"); 
	//String pname = "";
	String namestr = (String)session.getAttribute(Constants.PNAME_KEY);
	String username = request.getParameter("username");
	if(username != null  && username.length() >0){
	    namestr = username;
	}
	String usercode = UserManage.getUserCodeByName(namestr);
%>
<html>
	<head>
		<title>用户管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../ShareInc.html"></jsp:include>	
		<script type="text/javascript" src="../../js/user/UserManage.js"></script>
	</head>
	<body  SCROLL="no">
		<fieldset style="width:270;height:170;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">用户密码修改</legend>
			<table width="230" align="center" class="tableInput" id="dataTable">
			    <tr style="display:none">
					<td width="30%">用户编号：</td>
					<td width="66%" colspan=3><input id="USERCODE" type="text" maxLength="32" name="" class="text" value=<%=usercode%> readonly=true></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr >
					<td width="30%">用户名：</td>
					<td width="66%" colspan=3><input id="USERNAME" type="text" maxLength="32" name="" class="text" value=<%=namestr%> ></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.user.jsp.opwd>：</td>
					<td width="66%"colspan=3><input id="Opassword" type="password"  maxlength="20" class="text"></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.user.jsp.npwd>：</td>
					<td width="66%"colspan=3><input id="password" type="password"  onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value) maxlength="20" class="text"></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.user.jsp.rpwd>：</td>
					<td width="66%" colspan=3><input id="Rpassword" type="password"  maxlength="20" class="text"></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%">密码强度：</td>
					<td width="22%" id="strength_L" style="text-align:center">弱</td>
					<td width="22%" id="strength_M" style="text-align:center">中</td>
					<td width="22%" id="strength_H" style="text-align:center">强</td>
					<td width="4%"></td>
				</tr>
				<tr>
					<td class="tdRight" colspan="5">
						<div align="center">
							<input type="image" src="../../image/button/btnsave.gif"  name="button" value="<msg:Common_zh.Global.save.desc>" onClick="editPwd(<%=insrtOrUpdt%>);" />
							<input type="image" src="../../image/button/btnclose.gif" name="button" value="<msg:Common_zh.Global.close.desc>" onClick="window.close();" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</body>
</html>
