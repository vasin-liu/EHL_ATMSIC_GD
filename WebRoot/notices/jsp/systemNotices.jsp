<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.dispatch.cdispatch.action.MaterialInfoAction"%>
<%@include file="../../Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String noticeId = StringHelper.obj2str(request.getParameter("noticeId"), "");
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统公告信息</title>
		
		<link href="general/css/jqgrid/jquery-ui-1.8.2.custom.css" rel="stylesheet" type="text/css" />
		<link href="notices/css/notices_style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="util/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="notices/js/notices.js"></script>
		<script type=""></script>
		<script type="text/javascript">
			var noticeId = "<%=noticeId%>";
			var page = "<%=insrtOrUpdt%>";
		</script>
	</head>
<body bgcolor="#E5F4FD" onload="getCurrentNoticeDetail(noticeId,page);">
		<table id="notices_view_table" border="1">
			<tr style="display: none">
				<td><input id="page" type="text" value="<%=insrtOrUpdt%>"
					readonly></input> <input id="noticeId" type="text"
					value="<%=noticeId%>" readonly></input></td>
			</tr>
			<tr height='35px'>
				<td align="center">发布单位</td>
				<td><input ID="notice_department" name="notice_department"
					type="text" maxlength=20 size="25" /></td>
				<td align="center">发布时间</td>
				<td algin="right"><input type="text" id="notice_time" readonly
					size="25" /></td>
			</tr>
			<tr>
				<td align="center">信息类型</td>
				<td algin="right"><input type="text" id="notice_type" readonly
					size="25" /></td>
			</tr>
			</tr>
			<tr>
				<td align="center">公告标题</td>
				<td colspan="3"><input type="text" id="notice_title" size="62" /></td>
			</tr>
			<tr>
				<td align="center">公告内容</td>
				<td colspan="3"><textarea rows="7" ID="notice_content"
						name="notice_content" name="S1" cols="62" onkeypress=""></textarea>
				</td>
			</tr>
		</table>
</body>
</html>
