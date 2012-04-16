<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
	<head>
		<title>出错了！</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style>
			.blocktitle {
				PADDING-LEFT: 10px; FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR:red; LINE-HEIGHT: 26px; BORDER-BOTTOM: #99C4F2 1px solid; FONT-FAMILY: "宋体"; BACKGROUND-COLOR: #99C4F2; TEXT-ALIGN: center; TEXT-DECORATION: none
			}
		</style>
		<SCRIPT LANGUAGE="JavaScript">
			function Redirect(){
				window.location = "./";
			}
			var timer = setTimeout('Redirect()',3000); //跳转
		</SCRIPT> 
	</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0">
	<br>
	  <br>
	    <br>
	      <br>
	        <br>
	<table width="550" border="1" bordercolorlight="#BBBBBB" bordercolordark="#ffffff" cellspacing="0" cellpadding="6" align="center">
		<tr bgcolor="#FAFAFA">
			<div style="bgcolor:#FAFAFA"><font color="red"></font></div>
		</tr>
		<tr>
			<div class="blocktitle">出错了！请返回首页！</div>
			<div style="MARGIN: 3px; WIDTH: 99.8%;font-size: 9pt;"><br />如不能自动跳转，<a href="./">点击这里直接进入！</a><br /><br /></div>
	  	</tr>
	</table>
</body>
</html>


