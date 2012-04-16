<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.sm.common.Constants" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>系统管理中心</title>
<!--		<link href="init.no.d" rel="stylesheet" type="text/css">-->
		<link href="css/Global.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common/prototype.js"></script>
		<script language="javascript">
	        /** 登录验证,在用户开始登录时进行的前端验证 */
	        function restart(){
		        var pname = document.getElementById("pname");
		        var pwd = document.getElementById("pwd");
		        pname.value = "";
		        pwd.value = "";
	       } 
	       function login(){
	          var pname=document.userform.pname.value;
	          var pwd=document.userform.pwd.value;

	　         if(pname==""&&pname!=null){
	             alert("用户名不能为空！");
	             document.userform.pname.focus();
	             return false;
	　          }
			 if(pwd==""&&pname!=null){
	             alert("密码不能为空！");
	             document.userform.pname.focus();
	             return false;
	　          }
	            return true;
	      }
    </script>
  </head>
<body>

<%
	String errMessUser = (String) session.getAttribute(Constants.ERRMESSUSER_KEY);
	//获取输入用户名或密码时，出现'用户名或密码错误'的session值	if (errMessUser == null) {
		errMessUser = "";
	}
	session.removeAttribute("errMessUser");//移除session值errMessUser
%>
<table width="601" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="70">&nbsp;</td>
  </tr>
  <tr>
    <td height="331" background="image/login/login.jpg">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><!-- form表单 action直接调用类：com.ehl.login.Login.java 进行用户登录业务处理-->
		<form id="userform" name="userform" action="common.userlogin.login.d" method="post" onSubmit="return login();">
			<table width="218" align="center" class="tableInput" id="dataTable">
				<tr>
					<td height="67" colspan="4">
						<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
					</td>
				</tr>
				<tr>
				  <td width="66"><div align="center">用户名：</div></td>
				  <td width="140"><input type="text" name="pname"  id="pname" size="20" class="text"></td>
				</tr>
				<tr>
					<td><div align="center">密 &nbsp;&nbsp;码：</div></td>
					<td><input  type="password" name="pwd"  id="pwd"  size="20" class="text"/></td>
				</tr>
				<tr>
					<td class="tdRight" colspan="4">
						
						<div align="center">
							<input type="image" src="image/button/btnlogin.gif" name="button" value="登陆" onKeyDown="if(event.keyCode==13 || event.keyCode == 32) login();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="#" onClick="restart();"><img style="border:0" src="image/button/btnreset.gif"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tdRight" colspan="4">
						<div align="center">
							<%=errMessUser%>
						</div>
					</td>
				</tr>
			</table>
		</form></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<script language=javascript>
	document.userform.pname.focus();
</script>
</body>
</html>
