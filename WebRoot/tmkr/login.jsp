<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.common.Constants" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	String errMessUser = (String) session.getAttribute(Constants.ERRMESSUSER_KEY);
	//获取输入用户名或密码时，出现'用户名或密码错误'的session值


	if (errMessUser == null) {
		errMessUser = "";
	}
	session.removeAttribute("errMessUser");//移除session值errMessUser
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>登录页面</title>
		<script type="text/javascript" src="../base/js/prototype.js"></script>
		<script type="text/javascript" src="../dispatch/js/login/login.js"></script>
		<style type="text/css">
<!--
body { background:url(../dispatch/images/login/back.gif); font:宋体, Helvetica, sans-serif;}
.input {
	font-size: 12px;
	background:url(../dispatch/images/login/text_button.gif) no-repeat;
	width:134px;
	height:23px;
	border:0px;
	line-height:21px;
}
.font { color:#ffffff; font-size:12px; line-height:22px;}
td,th {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}
.text { font-family:Arial ; font-weight:bold; font-size:36px; color:#ffffff;}  
-->
</style>
	</head>

	<body>
		<form id="userform" name="userform" action="dispatch.userlogin.login.d"
			method="post" onSubmit="return login();">
			<table width="700" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top:100px;">
				<tr>
					<td height="81" background="../dispatch/images/login/top.gif" align=center>
						<font size=5 color=white>广东总队警情上报系统</font>
					</td>
				</tr>
				<tr>
					<td height="179" background="../dispatch/images/login/bottom.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="right" width="56%">
									<img src="../dispatch/images/login/00.gif" width="34" height="39" />
								</td>
								<td width="12%" align="right" class="font">
									用户名：
								</td>
								<td width="32%" height="28" colspan="2">
									<label>
										<input type="text" class="input" name="pname" id="pname" />
									</label>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td align="right" class="font">
									密 码：
								</td>
								<td height="28" colspan="2">
									<input type="password" class="input" name="pwd" id="pwd" />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td height="28" align="right" class="font">
									&nbsp;
								</td>
								<td height="25" colspan="2">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="32%" align="center">
												<input type="image" src="../dispatch/images/login/button1.gif"
													name="Submit" value="登录"
													onKeyDown="if(event.keyCode==13 || event.keyCode == 32) login();" " />
											</td>
											<td width="51%" align="left">
												<a href="#" onClick="restart();"><img style="border:0"
														src="../dispatch/images/login/button2.gif" value="取消" /> </a>
											</td>
											<td width="18%">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							<td></td>
								<td class="tdRight" colspan="3" height="25">
									<div align="center">
										&nbsp;<%=errMessUser%>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td height="20" align="right" class="font">
									&nbsp;
								</td>
								<td height="20" colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="28" colspan="4" align="center" class="font">
									北京易华录信息技术股份有限公司


								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<script language=javascript>
		document.userform.pname.focus();
   </script>
	</body>
</html>
