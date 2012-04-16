<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.util.*" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	
	
	String flag=request.getParameter("flag");

	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String pname = ""; 		  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");		
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>警力查找信息</title>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	
</head>
	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
 	<script src="../../js/common/prototype.js" type="text/javascript"></script>
 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
	
	<script type="text/javascript" src="../../js/policeWatch/dispatchWatching.js"></script>
	<script type="text/javascript" src="../../js/common/utility/DhtmUtilTable.js"></script>
	<script type="text/javascript" src="../../js/Supervise/supervise.js"></script>
	<script type="text/javascript" src="../../js/editPolice/editPolice.js"></script>
	<script type="text/javascript" src="../../js/complement/complementEdit.js"></script>
	<script type="text/javascript" src="../../js/common/utility/ListChose.js"></script>
	<script type="text/javascript" src="../../js/common/utility/comLogic.js"></script>
	<script type="text/javascript" src="../../js/common/utility/DivHolder.js"></script>
	<script type="text/javascript" src="../../js/common/utility/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../js/common/CommonClear.js"></script>
	<script type="text/javascript" src="../../js/policeAdd/addPolice.js"></script>
	<script type="text/javascript" src="../../js/policeDispose/disposePolice.js"></script>
	
	<script type="text/javascript">
		
	 	var deptcode = '<%=deptcode%>';
		var userCountyText = '<%= deptname %>';
		var pname = '<%=pname%>';
		var pid = '<%=pid%>';
		var userNameText = '<%= uname %>';
		var mobilephone='<%= mobilephone%>';		
		
	</script>
<body onload="initPersons('<%=flag %>');">
	<div style="text-align: center;width: 99%">
		<br>
		<div id="textdiv" style="text-align: left;width: 90%"></div>
		<div id="checkdiv" style="text-align: left;width: 90%;font-size: 12">中队所有人员：<input type="checkbox" id="rychk"></div>
		<div id="infolist" style="border: 1px solid #CCCCCC;overflow-Y: scroll;width:90%;height:285;text-align: left;font-size: 12">
		</div>
		<input type="button" value="确 定" onclick="checkedOk('<%=flag %>');">
		&nbsp;&nbsp;
		<input type="button" value="关 闭" onclick="window.close();">
	</div> 
</body>
</html>