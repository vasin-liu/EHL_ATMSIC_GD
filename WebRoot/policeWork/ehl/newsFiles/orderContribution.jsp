<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.ehl.dispatch.common.*" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("880605");
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));//单位编码
	String jgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));//机构名称
	String zbrxm = Constant.nvl(session.getAttribute(Constant.ZBRXM_VAR));
	String octime = Constant.getCurrentTime(false).substring(0,16);
	//""：添加，1：查询，2：查看，3：处理
	String ptype = Constant.nvl(request.getParameter("page"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=title%></title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
	 	<link rel="stylesheet" href="../../../base/css/util/button.css" type="text/css"></link>
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/Flow.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/FileUpDownload.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/calendar/DateAndSchar.js"></script>
		<script type="text/javascript" src="../../js/newsFiles/orderContribution.js"></script>
</head>
<body bgcolor="#FFFFFF">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;border:1px solid #a5d3ef" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%">
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif" width="16"
									height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置</b>
							</span>：<%=title%>
						</td>
					</tr>
				</table>
				<div id="showLength" style="color:red"> </div>
				<input id="cjgid" type="hidden" value="<%=jgid%>"/>
				<input id="cjgmc" type="hidden" value="<%=jgmc%>"/>
				<input id="cpname" type="hidden" value="<%=zbrxm%>"/>
				<input id="page" type="hidden" value="<%=ptype%>"/>
				<input id="ctime" type="hidden" value="<%=octime%>"/>
			<table class="table3" width="100%" border="1" cellspacing="0" cellpadding="0" id="block" borderColor='#a5d1ec'>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='18%'>
						约稿单位<input id="jgid" type="hidden" value="<%=jgid%>" />
					</td>
					<td >
						<input type="text" id="jgmc" disabled value="<%=jgmc%>" />
					</td>
					<td bgcolor="#F0FFFF"  align="center" width='18%'>
						约稿人
					</td>
					<td >
						<input type="text" id="pname"  value="<%=zbrxm%>" maxlength="30" />
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='15%'>约稿时间</td>
					<td colspan="3">
						<input type="text" id="time" disabled  onclick="SelectDateTime(this)" value="<%=octime %>"  />
					</td>
				</tr>
				<tr height="35" >
					<td bgcolor="#F0FFFF"  align="center" >约稿内容</td>
					<td id="contentTd" colspan="3" style="text-align: left;height:120px;">
						<textarea  id="content" style="border: 1px #7B9EBD solid;width:562px;height:120px;" ></textarea>
					</td>
				</tr>
				 <tr id="attachTr" height="30">
                   	<td id="apathDescTd" align="center" bgcolor="#F0FFFF" >
						 添加附件
                    </td>
                    <td id="fileRegion" colspan="3" >
                 	</td>
                 </tr>
				<tr>
					<td id="rpdndescTd" align="center" bgcolor="#F0FFFF">
						接收单位<input id="adcode" type="hidden" />
					</td>
					<td id="rpdnameTd" algin="right" colspan="3">
						<input type="hidden" id="ajgid" />
                        <input id="ajgmc" type="text" value="" style="width: 540px;" readonly onmouseover="this.title=this.value"/>
                        <img id="aimg" alt="选择机构" src="../../images/popup/btnselect.gif" onclick="setTree('<%=jgid %>',180,400,null,'01')" style="cursor:hand;"/>
					</td>
				</tr>
				<tr id="rpdremarkTr" style="display: none;">
					<td colspan="4" style="left">
						<span style="font-weight: bold;">◆接收单位及处理状态中，单位名称显示为红色表示未签收，显示为绿色表示已签收</span>
					</td>
				</tr>
			</table>
		</fieldset>
		<div style="margin-top: 10px;">
			<input class="btn" type="button" id="add" value=" 报 送 " style="margin-right:70px;display: none;" onclick="add();">
			<input class="btn" type="button" id="modify" value=" 保 存 " style="margin-right:70px;display:none;"  onclick="modify()">
			<input class="btn" type="button" id="signIn" value=" 签 收 " style="margin-right:70px;display:none;"  onclick="signIn()">
			<input class="btn" type="button" id="close" value=" 关 闭 " style="margin-right:70px;display:none;"  onclick="window.close()">
		</div>
	</div>
</body>
</html>