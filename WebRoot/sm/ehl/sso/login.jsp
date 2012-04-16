<%@ page language="java" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>

<html>
	<head>
		<title><msg:Common_zh.Global.title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<link href="../css/nioa.css" rel="stylesheet" type="text/css">
		<script src="../../js/sso/cookiet.js"></script>
		<script language="javascript">
		function check()
		{
			var name=document.userform.name.value;
			var pwd=document.userform.pwd.value;
			var recode=document.userform.recode.value;
　			if(name==""||pwd=="")
			{
    			alert("用户名或密码不能为空");
    			document.userform.name.focus();
    			return false;
　			}
  			document.userform.submit();
  			return true;
   			
   		}
   	
		</script>
		<style type="text/css"></style>
	</head>
	<body>
		<% 
			String tableName = request.getParameter("tname");
			String subURL = request.getParameter("subURL");
		    
      %> 
		<center>
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
			</p>
			<p class="STYLE2">
				<msg:Common_zh.Global.login.desc>
			</p>
		</center>
		<!--   onSubmit="return check();"" -->
		<form name="userform" action="execute.jsp" method="post" onSubmit="return check();" >
			<table width="362" align="center" class="tableInput" id="dataTable">
				<tr>
					<td width="20%">
						<msg:Common_zh.login.jsp.username>：

					</td>
					<td colspan="2">
						<input type="text" name="name" class="text" id="name" />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%">
						<msg:Common_zh.login.jsp.pwd>：

					</td>
					<td colspan="2">
						<input name="pwd" type="password" class="text" id="pwd" size="20" />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				
				<tr>
					<td class="tdRight" colspan="4">
						<div align="center">
							<input style="visibility:hidden" type="text" name="tname" id="tname" value=<%=tableName%> />
							<input style="visibility:hidden" type="text" name="subURL" id="subURL" value=<%=subURL %> />
							<input type="image" src="../../image/button/btnlogin.gif" name="button" class="sub" value='<msg:Common_zh.Global.login.desc>' onclick="check();"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="image" src="../../image/button/btnreset.gif" name="reset" value='<msg:Common_zh.Global.reset.desc>' />
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
