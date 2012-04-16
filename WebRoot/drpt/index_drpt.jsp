<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.dispatch.common.DispatchUtil"%>
<%@ include file="Message.oni"%>
<%
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	String path = request.getHeader("host");

	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pname = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>系统管理主界面</title>
		<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/header.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/main.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/sidebar.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/navigation.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<SCRIPT src="../sm/js/sysmanage/head.js" type=text/javascript></SCRIPT>
		<SCRIPT src="../base/js/style1/iframe.js" type=text/javascript></SCRIPT>
		<SCRIPT src="../js/main.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="../dispatch/js/login/prototype.js"></script>
		<SCRIPT src="js/prompter/prompter.js" type=text/javascript></SCRIPT>
<!-- 		<script type="text/javascript" src="../base/js/dialog/jquery.js"></script> -->
	</head>
	<body style="padding: 0" onload="setHomePage('90','<%=uname %>')">
		<!--内容-->
		<!--页面主体部分 START-->
		<div id="pagebody">
			<!--页面主体左侧 START-->
			<table width="100%">
				<tr>
					<td width="10">
						<div class="sidebar" id="frmTitle">
							<iframe frameborder="0" id="sidebar" name="sidebar" scrolling="auto" noresize="noresize" src="lefttree.jsp"
								style="HEIGHT: 521px; VISIBILITY: inherit; WIDTH: 168px; Z-INDEX: 2" allowtransparency="true"></iframe>
						</div>
					</td>
					<td width="8">
						<div class="switch">
							<div style="HEIGHT: 521px; float: left; background: url(../base/image/cssimg/switch.gif) no-repeat center;" onClick="switchSysBar()">
								<font style="FONT-SIZE: 12pt; width: 9px; CURSOR: default;">
									<span class="navPoint" id="switchPoint" title="切换">3</span> </font>
							</div>
						</div>
					</td>
					<td>
						<div class="mainbody" style="width: 100%">
							<iframe frameborder="0" id="moduletarget" name="moduletarget" scrolling="auto" noresize="noresize" src="./ehl/drpt/dailyReport.jsp"
								style="height: 540px; * height: 540px !important; * height: 522px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1; padding-top: 6px;"
								allowtransparency="true">
							</iframe>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<script type="text/javascript">
	if(window.screen.width<'1024'){switchSysBar()}
</script>
<%
	Object pnameObj = session.getAttribute(Constants.PNAME_KEY);
	String deptId = DepartmentManage.getDeptInfo(request, "1");//获取机构信息串

	String depts[] = deptId.split(",");
	//总队用户
	if ("0000".equals(depts[0].substring(2, 6))) {
%>
<script type="text/javascript">
	var widthv = 430;
	var heightv = 480;
	var xposition = (screen.availWidth - widthv)/2;
	var yposition = (screen.availHeight - heightv)/2;
	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no';	
	//提示总队现在各个支队上报情况。
	window.open('zongduiPrompter.jsp',"",feature);
</script>
<%
	//支队用户
	} else if ("00".equals(depts[0].substring(4, 6))) {
		Object[][] result = null;
		String sqlTemp = "SELECT * FROM ( SELECT A.PARAVALUE AS STARTTIME, B.PARAVALUE AS ENDTIME FROM T_SYS_PARA A,T_SYS_PARA B "
				+ " WHERE A.PARAID='90001' AND B.PARAID='90002')"
				+ " WHERE SUBSTR(TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI'),12,5)>STARTTIME"
				+ " AND SUBSTR(TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI'),12,5)<ENDTIME";
		result = DBHandler.getMultiResult(sqlTemp);
		//System.out.println("==================================>>>>>"+result);
		if (result != null) {
%>
<script type="text/javascript">
	zhidui(<%=depts[0]%>);
</script>
<%
	}
%>
<script type="text/javascript">
	var widthv = 400;
	var heightv = 480;
	var xposition = (screen.availWidth - widthv)/2;
	var yposition = (screen.availHeight - heightv)/2;
	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no';	
	//提示支队现在各个大队上报情况。
	window.open("zhiduiPrompter.jsp?jgid="+<%=depts[0]%>,"",feature);
</script>
<jsp:directive.page import="com.appframe.data.sql.DBHandler"/>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%
	//大队用户，提示是否提交了昨天的数据。
	} else {
		Object[][] result = null;
		String sqlTemp = "SELECT * FROM ( SELECT A.PARAVALUE AS STARTTIME, B.PARAVALUE AS ENDTIME FROM T_SYS_PARA A,T_SYS_PARA B "
				+ " WHERE A.PARAID='90003' AND B.PARAID='90004')"
				+ " WHERE SUBSTR(TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI'),12,5)>STARTTIME"
				+ " AND SUBSTR(TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI'),12,5)<ENDTIME";
		result = DBHandler.getMultiResult(sqlTemp);
		if (result != null) {
%>
<script type="text/javascript">
	dadui(<%=depts[0]%>);
</script>
<%
		}
	}
%>