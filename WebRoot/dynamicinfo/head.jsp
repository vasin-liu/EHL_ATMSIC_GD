<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.sm.common.Constants" %>
<%
	Object pname = session.getAttribute(Constants.PNAME_KEY);
	String userName = pname.toString();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>设备管理</title>
		<link href="../sm/css/Global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../sm/js/common/prototype.js"></script>
		<script type="text/javascript" src="../sm/js/user/UserManage.js"></script>
		<script type="text/javascript" src="../sm/js/sysmanage/head.js"></script>
		<script type="text/javascript">
			function getURL(){
				var url = window.location.href;
				var inner = url.indexOf("vcas");
				var target = url.substring(0, inner);
				return target;
			}
		</script>
		<style>
			<!--
			* { padding:0; margin:0}
			body{ margin:0; padding:0; text-align:center; width:100%}
			#head_top span .a:link { color:#ffffff; text-decoration:none;} 
			#head_top span a:hover { color:#ff0000; text-decoration:none;}
		    #head_top span a:active { color:#0000ff; text-decoration:none;}
			#head_top span { color:#ffffff; text-decoration:none;}
			#head_top{ margin:0 auto; padding:0px; height:80px; text-align:left; background:url(image/head/BANNER.gif) left top repeat-x;}
			#head_top ul { list-style-type:none; text-align:right; margin-right:10px !important; margin-right:15px; padding-top:2px;}
			#head_top ul li { display:block; line-height:19px; font-size:12px; font-weight:bold; color:#ffffff}
			.clr01{ clear:both; height:1px; font-size:1px; background-color:#919497}
			<!--滤镜图片拉伸 -->
			#head_top{filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='image/head/head.gif',sizingMethod='scale');} 
			-->
		</style>
	</head>
	<body>
	
	    <div id="head_top" class="nav">
			<table align="right">
			   <tr height="20">
			      <td colspan="4">
			      </td>
			   </tr>
			   <tr height="30">
			      <td colspan="4">
			            <font color="white"><b>当前用户：<%=userName %></b></font>
			      </td>
			   </tr>
			   <tr>
			       <td width="">
			         <span style="position:relative"><a href="javascript:showPage();"><b>修改密码</b></a>｜</span>
			      </td>
			      <td>
			         <span style="position:relative"><a href="javascript:doLogout('header');"><b>注销</b></a>｜</span>
			      </td>
			      <td>
			         <span style="position:relative"><a href="javascript:showHelpPage('ehl/about/about.jsp','../../xml/version.xml');"><b>关于</b></a>｜</span>
			      </td>
			     
			      <td>
			         <span style="position:relative"><a href="javascript:help('../sm/ehl/help/help.jsp');"><b>帮助</b></a></span>
			      </td>
			      
			   </tr>
			</table>
		</div>
	</body>
</html>