<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.sm.common.Constants" %>
<% String newsURL = com.appframe.common.Setting.getString("newsurl");
%>
<html>
	<head>
	    <script type="text/javascript" src="sm/js/common/prototype.js"></script>
	    <script type="text/javascript" src="sm/js/user/UserManage.js"></script>
		
		<script type="text/javascript" src="js/main.js"></script> 
	    <link href="css/atmsic.css" rel="stylesheet" type="text/css" />
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
		<title>交通管理综合信息中心</title>
		<style type="text/css">
             body {
        margin-left: 0px;
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
         }
		</style>
	</head><!--head-->
<body align="center" scrolling="no" scroll="no" >
	<div align="center" scrolling="no" scroll="no" > 
		<div  scrolling="no" scroll="no" border="1" bordercolor="#E0ECF8" height="100%" align="center">
			   <table width="100%" height="100%" border="0" bordercolor="#E0ECF8" align="center">
			    <tr>
			        <td width="15%" align="center" height="100%">
			         <iframe id="rightContent" name="rightContent" src="main_login.jsp" width="100%" height="100%" scrolling="no" scroll="no" frameborder="0" framespacing="0px"></iframe>
			        </td>
			       <td width="85%" align="center" height="100%">
			         <iframe id="rightContent" name="rightContent" src="trafficinfo/ehl/index_jnga.jsp" width="100%" height="100%" scrolling="no" scroll="no" frameborder="0" framespacing="0px"></iframe>
			      </td>
			      <!--
			      <td width="46%" align="center">
			         <iframe src="<%=newsURL %>" width="100%" height="100%" scrolling="no" scroll="no" frameborder="0" framespacing="0px"></iframe>
			      </td>
			      <td width="39%" height="100%" align="center">
			         <iframe src="tfm/ehl/tfm/tfm.jsp" width="100%" height="100%" scrolling="no"  frameborder="0" framespacing="0px"></iframe>
			      </td>
			      -->
			   </tr>
			   <tr>
			      <td colspan="2" class="foot" style="font-size:10">
			          技术支持：北京易华录信息技术股份有限公司
			      </td>
			   </tr>
			</table>
		</div>    <!--foot-->
	</div>
</body>
</html>