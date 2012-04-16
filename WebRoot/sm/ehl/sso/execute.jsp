<%@ page language="java" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.sm.sso.*"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script src="../../js/sso/cookiet.js"></script>
		<script src="../../js/sso/ssoUtil.js"></script>
		<script type="text/javascript">
		  
		</script>
			
		<%
			String usrname = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			String tname = request.getParameter("tname");
			String subURL = request.getParameter("subURL");
			
			int start = tname.indexOf(".");
			String tname0 = tname.substring(start+1);
			
			String pname = SsoUtil.getUsername(usrname);
			String pcode = SsoUtil.getUsercode(usrname,tname0);
			System.out.println("subname="+usrname+",tname=="+tname+",pname=="+pname+",pcode=="+pcode);
			String cookiet = SsoUtil.GenCookiet(usrname,pwd,tname);
			System.out.println("cookiet is new======================"+cookiet);
			//如果用户名和密码不符，则跳转到登录服务器登录页面
			if("incorrect".equals(cookiet))
			{
				String loginURL = "http://192.168.1.233:8088/EHL_ITGS/ehl/sso/login.jsp";
				response.sendRedirect(loginURL);
			}
			//session.setAttribute("usrname",usrname);
			
		 %>
		 
		<style type="text/css"></style>
	</head>
	<body >
			<iframe name="goal" id="goal" frameborder=2 style="background: #555;width:200px;height:200px;" src=""></iframe>
		  	
		<center>
		
			<form name="jump" id="jump" action="" method="post" >
				<input style="visibility:hidden" type="text" name="cookiet" id="cookiet" value=<%=cookiet%> />
		 		<input style="visibility:hidden" type="text" name="pname" id="pname" value=<%=pname %> />
		 		
		 		<input style="visibility:hidden" type="text" name="pwd" id="pwd" value=<%=pwd%> />
		 		<input style="visibility:hidden" type="text" name="pcode" id="pcode" value=<%=pcode %> />
		 		<input style="visibility:hidden" type="text" name="subURL" id="subURL" value=<%=subURL %> />
		 	</form>
		 	<script language="javascript">
		 			var cookiet = document.getElementById("cookiet").value;
					var pcode = document.getElementById("pcode").value;
					//行动方案为“增添”

					var behaviour = "add";
					var subURL = document.getElementById("subURL").value; //子系统入口url
					document.jump.action=subURL;
					//开始分发cookie票

   					getSysUrlByPcode(pcode,behaviour);
 		 	</script>
		</center>
	
	</body>
</html>
