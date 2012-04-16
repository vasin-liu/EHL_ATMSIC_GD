<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.sm.common.Constants" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%
	Object pname = session.getAttribute(Constants.PNAME_KEY);
	String userName = pname.toString();	
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(",");//获取机构信息串

	String jgid = strObj[0];//机构编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="DownloadOptions" content="noopen"/>
		
		<title></title>
		<link href="css/Global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/user/UserManage.js"></script>
		<script type="text/javascript" src="js/sysmanage/sysParamMain.js"></script>
		<script type="text/javascript" src="js/sysmanage/head.js"></script>
		<script type="text/javascript" src="js/common/prototype.js"></script>
		
		<script type="text/javascript">
			function getURL(){
				var url = window.location.href;
				var inner = url.indexOf("sm");
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
			    <tr height=15>
			      <td colspan="4">
			           
			      </td>
			   </tr>
			   
			   <tr >
			      <td colspan="4">
			            <span color="white"><b>当前用户：<%=userName %></b></span>
			      </td>
			   </tr>
			   <tr >
			      <td colspan="4">
			            <span color="white"><b>当前单位：<%=jgmc %></b></span>
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
			         <span style="position:relative"><a href="javascript:showHelpPage('ehl/sysmanage/about.jsp','../../xml/version.xml');"><b>关于</b></a>｜</span>
			      </td>
			     
			      <td>
			         <span style="position:relative"><a href="javascript:help('ehl/help/help.jsp');"><b>帮助</b></a></span>
			      </td>
			      
			   </tr>
			</table>
		</div> 
	</body>
</html>