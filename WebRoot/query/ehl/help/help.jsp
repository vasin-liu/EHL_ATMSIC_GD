<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String htm = request.getParameter("htm");
   if(htm == null){
     htm = "index.htm";
   }
 %>
<html>
	<head>
	    <script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	    <script type="text/javascript" src="../../../sm/js/user/UserManage.js"></script>
	    <script language="javascript">
   	    </script>
   	    <script language="javascript"> 
   	         //监视浏览器的关闭动作，然后调用doLogout()向服务器发送一个请求---by ldq
			 window.onunload=function (){   
				if(event.clientX<0&&event.clientY<0){
			   }
			}
			
  		</script> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查控分析系统</title>
	</head>
	<frameset cols="0%,100%,*" border="1px" frameborder="1" framespacing="0px" noResize="true"> 
	<frame src="#" scrolling="no" noresize /> 
	<frameset rows="40,*" framespacing="0" frameborder="1" noResize="true">
		<frame name="header" scrolling="no" noresize src="help_top.html" style="border-bottom:0px #00a3e4 solid" />
		<frameset cols="230,*" framespacing="0" frameborder="1" noResize="true">
			<frame name="prop" src="help_left.jsp" noResize="true" style="border-right:0px #00a3e4 solid" />
			<frame name="moduletarget" src="<%= htm%>" style="border-left:0px #00a3e4 solid">
		</frameset>
		<noframes></noframes>
	</frameset>
<frame src="#" scrolling="no" noresize /> 
</frameset>	
	
</html>