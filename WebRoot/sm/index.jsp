<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.base.Constants" %>
<html>
<% 
	//判断用户是否已经登录,如果未登录则跳转到登录页
	Object pname = session.getAttribute(Constants.PNAME_KEY);
   	if (null == pname){
%>
	<script language="javascript"> 
	if((window.location).toString().indexOf("sm") > -1){
	     window.navigate('login.jsp');   
	}else{
	
	    window.navigate('sm/login.jsp');
	}
	</script>
<%} %>

	<head>
	    <script type="text/javascript" src="../base/js/prototype.js"></script>
	    <script type="text/javascript" src="js/user/UserManage.js"></script>
	    <script language="javascript">
   	    </script>
   	    <script language="javascript"> 
   	         //监视浏览器的关闭动作，然后调用doLogout()向服务器发送一个请求---by ldq
			 window.onunload=function (){   
				if(event.clientX<0&&event.clientY<0){ 
				   doLogout();  
			   }
			}
  		</script> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>系统管理中心</title>
	</head>
	<frameset cols="1*,1022,1*" border="0px" frameborder="0"
		framespacing="0px">
		<frame src="#" scrolling="no" noresize />
		<frameset rows="79,*" framespacing="0" frameborder="no">
			<frame name="header" scrolling="no" noresize src="head.jsp"
				style="border-bottom: 0px #00a3e4 solid" />
			<frameset cols="220,*" framespacing="0" frameborder="no">
				<frame name="prop" src="lefttree.jsp"
					style="border-right: 0px #00a3e4 solid" />
				<frame name="moduletarget" src="../dispatch/ehl/cpoliceedit/roadManage.jsp"
					style="border-left: 0px #00a3e4 solid">
			</frameset>
			<noframes></noframes>
		</frameset>
		<frame src="#" scrolling="no" noresize />
	</frameset>

</html>