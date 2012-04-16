<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.ehl.sm.common.Constants" %>
<% 
	//判断用户是否已经登录,如果未登录则跳转到登录页
	Object pname = session.getAttribute(Constants.PNAME_KEY);
   	if (null == pname){
%>
	<jsp:forward page="login.jsp"/>
<%
	} 
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>视频系统</title>
	    <script type="text/javascript" src="../sm/js/common/prototype.js"></script>
		<script type="text/javascript" src="../sm/js/user/UserManage.js"></script>
   	    <script language="javascript"> 
   	         //监视浏览器的关闭动作，然后调用doLogout()向服务器发送一个请求---by ldq
			 window.onunload=function (){   
				if(event.clientX<0&&event.clientY<0){ 
				   doLogout();  
			   }
			}
  		</script>
  		<meta http-equiv="refresh" content="0;url=../cctv/ehl/device/VidiconInfo.jsp">
	</head>
</html>