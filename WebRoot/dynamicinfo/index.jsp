<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.ehl.sm.common.Constants" %>
<% System.out.println("-----------------------------gggggggggggggggggggggggggggggggggggggggg---------------");
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
		<title>设备管理</title>
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
	</head>
	<frameset cols="1*,1022,1*" border="0px" frameborder="0" framespacing="0px"> 
		<frame src="#" scrolling="no" noresize /> 
		<frameset rows="79,*" framespacing="0" frameborder="no" >
			<frame name="header" scrolling="no" noresize src="head.jsp" style="border-bottom:0px #00a3e4 solid" />
			<frameset name="frmst" cols="215,*" framespacing="0" frameborder="no">
				<frame name="prop" src="lefttree.jsp" style="border-right:0px #00a3e4 solid" />
				<frame name="moduletarget" src="ehl/monitor/MonitorInfo.jsp" style="border-left:0px #00a3e4 solid" />
			</frameset>
			<noframes></noframes>
		</frameset>
       <frame src="#" scrolling="no" noresize /> 
</frameset>	
	
</html>