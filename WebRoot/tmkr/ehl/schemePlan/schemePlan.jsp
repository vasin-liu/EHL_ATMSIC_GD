<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@page import="com.ehl.dispatch.common.FlowUtil"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("580102");
	title = title.substring(2);
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));//单位编码
	String jgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));//机构名称
	String zbrxm = Constant.nvl(session.getAttribute(Constant.ZBRXM_VAR));
	String appid = Constant.nvl(session.getAttribute(Constant.APPID_VAR));
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
		<script type="text/javascript" src="../../js/common/common.js"></script>
		<script type="text/javascript" src="../../js/schemePlan/schemePlan.js"></script>
</head>
<body bgcolor="#FFFFFF" style="padding: 0px;margin: 0px; overflow: auto;">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;border:1px solid #a5d3ef;" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%">
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif" width="16"
									height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置：</b>
							</span><span id="pageTitle"><%=title%></span>
						</td>
					</tr>
				</table>
				<input id="cjgid" type="hidden" value="<%=jgid%>"/>
				<input id="cjgmc" type="hidden" value="<%=jgmc%>"/>
				<input id="cpname" type="hidden" value="<%=zbrxm%>"/>
				<input id="appid" type="hidden" value="<%=appid%>"/>
				<input id="page" type="hidden" value="<%=ptype%>"/>
				<input id="ctime" type="hidden" value="<%=octime%>"/>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" id="block" borderColor='#a5d1ec'>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='18%'>
						录入单位<input id="jgid" type="hidden" value="<%=jgid%>" />
					</td>
					<td >
						<input type="text" id="jgmc" disabled value="<%=jgmc%>" />
					</td>
					<td bgcolor="#F0FFFF"  align="center" width='18%'>
						录入人
					</td>
					<td >
						<input type="text" id="pname"  value="<%=zbrxm%>" maxlength="30" />
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='15%'>录入时间</td>
					<td colspan="3">
						<input type="text" id="time" disabled  onclick="SelectDateTime(this)" value="<%=octime %>"  />
					</td>
				</tr>
				<tr height="35" >
					<td bgcolor="#F0FFFF"  align="center" >预案标题</td>
					<td id="titleTd" colspan="3">
						<input type="text" id="title"  value="" maxlength="100" style="width:562px;"/>
					</td>
				</tr>
				<tr height="35" style="display: none;">
					<td bgcolor="#F0FFFF"  align="center" >预案内容</td>
					<td id="contentTd" colspan="3" style="text-align: left;height:120px;">
						<textarea  id="content" style="border: 1px #7B9EBD solid;width:562px;height:120px;" ></textarea>
					</td>
				</tr>
				 <tr id="attachTr" height="35">
                   	<td id="apathDescTd" align="center" bgcolor="#F0FFFF" >
						 添加附件
                    </td>
                    <td id="fileRegion" colspan="3">
                 	</td>
                 </tr>
                 <tr height="35" >
					<td bgcolor="#F0FFFF"  align="center" >开放给相关单位查询</td>
					<td colspan="3" >
						<input id="isPublish_yes" name="isPublish" type="radio" value="1" onclick="setIsPublish(this)" /><label for="isPublish_yes">是</label>
						<input id="isPublish_no" name="isPublish" type="radio" value="0"  onclick="setIsPublish(this)" checked /><label for="isPublish_no">否</label>
					</td>
				</tr>
				<tr id="ajgTr" style="display: none;">
					<td align="center" bgcolor="#F0FFFF">
						相关查询单位<input type="hidden" id="ajgid" />
					</td>
					<td id="ajgmcTd" algin="right" colspan="3">
                        <input id="ajgmc" type="text" value="" style="width: 540px;" readonly onmouseover="this.title=this.value"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div style="margin-top: 10px;">
			<input class="btn" type="button" id="add" value=" 保 存  " style="margin-right:70px;display: none;" onclick="add();">
			<input class="btn" type="button" id="modify" value=" 保 存 " style="margin-right:70px;display:none;"  onclick="modify()">
			<input class="btn" type="button" id="signIn" value=" 签 收 " style="margin-right:70px;display:none;"  onclick="signIn()">
			<input class="btn" type="button" id="close" value=" 关 闭 " style="margin-right:70px;display:none;"  onclick="window.close()">
		</div>
	</div>
</body>
</html>