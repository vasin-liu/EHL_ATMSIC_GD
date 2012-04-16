<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage" %>
<%@ page import="com.ehl.sm.common.Constants"%>

<% 
	//判断用户是否已经登录,如果未登录则跳转到登录页
	Object pname2 = session.getAttribute(Constants.PNAME_KEY);
   	if (null == pname2){
%>
	<script language="javascript"> 
	    window.navigate('login.jsp');
	</script>
<%} %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菏泽市交通管理综合信息研判平台</title>
<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/header.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/main.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/sidebar.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/navigation.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style2/pagetag/pagetag.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../base/js/about.js" type=text/javascript></SCRIPT>

<SCRIPT src="../js/main.js" type=text/javascript></SCRIPT>
</head>
<%
	Object pnameObj = session.getAttribute(Constants.PNAME_KEY);
	String deptId = DepartmentManage.getDeptInfo(request,"1");//获取机构信息串

	String depts[] = deptId.split(",");
%>

<body style="padding:0">
<!--内容-->
<!--页头 START-->
<div id="header"><!--  -->
<h1>&nbsp;&nbsp;&nbsp;&nbsp;菏泽市交通管理综合信息研判平台</h1>
<table style="float:right;margin-top:20px;font-size:12px;color:#FFF;" >
	<tr>
		<td>
			当前用户：<%=pnameObj%>
		</td >
		<td width="40px" align="right" height="22px">
			<a href="#" onclick="javascript:showHelpPage('../base/about.jsp','../tira/xml/about.xml');"><b>关于</b></a>
		</td>
	</tr>
	<tr>
		<td>
			所属机构：<%=depts[1]%>
		</td >
		<td width="40px" align="right">
			<a href="#" onclick="logout()"><b>注销</b></a>
		</td>
	</tr>
</table>

</div>
<!--导航/*= WELCOME =*/ START-->
<span id="navigation"><!--  -->
      <h1></h1>
      <div id="navid">
      <a href="#" onclick="window.open('./ypw.jsp');">研判网站</a>
      <a href="#" onclick="intoSys('./index_tira.jsp');">研判平台</a>
      <a href="#" onclick="intoSys('../sm/index_sm.jsp');">系统管理中心</a>
      </div>
</span>
<!--页面主体部分 START-->
<div id="pagebody">
<!--页面主体左侧 START-->
    <iframe frameborder="0" id="mainbody" name="mainbody"   scrolling="auto" noresize="noresize"  src="./index_tira.jsp" style="height:542px; *height:542px !important; *height:542px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1;padding-top:6px;"  allowtransparency="true"> </iframe>
	
</div>
</body>
</html>
<script type="text/javascript">
 if(self!=top){top.location=self.location;}
//if(window.screen.width<'1024'){switchSysBar()}
</script>
