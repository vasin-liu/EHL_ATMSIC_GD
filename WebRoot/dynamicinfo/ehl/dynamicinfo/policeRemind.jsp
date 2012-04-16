<%@page import="com.appframe.common.Setting"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/jsp/base.jsp"%>
<%
pageContext.setAttribute("titleSelf", "交警提醒信息编辑");
pageContext.setAttribute("title", "交警提醒信息编辑");
String script = "";
script += "baseInfo.crowdAutoOver='"+Setting.getString("crowd_auto_over")+"';";
script += "baseInfo.buildAutoOver='"+Setting.getString("build_auto_over")+"';";
script = "<script>"+script+"</script>";
out.println(script);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${titleSelf}</title>
		<link href='${contextPath}base/css/new/main.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/detail.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/util.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/button.css' rel='stylesheet' type='text/css' />
		
		<script src="${contextPath}util/jquery/jquery-1.7.1.min.js"></script>
		<script src="${contextPath}util/jquery/jquery.json-2.3.min.js"></script>
		<link href="${contextPath}util/jquery/jquery-ui/themes/redmond/jquery-ui-1.8.17.custom.css" rel="stylesheet" type="text/css" /> 
		<script src="${contextPath}util/jquery/jquery-ui/jquery-ui-1.8.17.custom.min.js"></script>
		
		<link href='${contextPath}dynamicinfo/css/table.simple.css' rel='stylesheet' type='text/css' />
		<script src="${contextPath}base/js/new/dom.js"></script>
		<link href='${contextPath}dynamicinfo/css/text.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}dynamicinfo/css/policeremind.css' rel='stylesheet' type='text/css' />
		<script type='text/javascript' src='../../js/dynamicinfo/policeRemind/policeRemind.js'></script>
	</head>
	<body>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr>
				<td height="30" class="wTableTopCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30" class="wTableTopLeft"></td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
											<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title_page">
												<tr >
													<td class="image">
														<img src="${contextPath}base/image/cssimg/table/tb.gif" class="image" alt="标题图标" />
													</td>
													<td>
														<span class="label">您当前的位置：</span>
														<span id="page_title" class="value">${title}</span>
													</td>
												</tr>
											</table>	
										</td>
									</tr>
								</table>
							</td>
							<td width="16" class="wTableTopRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 头部end -->
			<!-- 数据 -->
			<tr>
				<td>
					<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wTableCenterLeft"></td>
							<td class="wTableCenterCenter" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="left" class="height"></td>
									</tr>
									<tr >
										<td class="container">
											<table id="tblForm" width="100%" border="0" cellspacing="0" cellpadding="0" class="data_detail">
												<tr id="trJgTime" class="item">
													<td class="item label">
														发布单位
													</td>
													<td class="item value">
														<input type="text" id="departmentname" disabled />
														<input type="hidden" id="departmentjgid"  />
													</td>
													<td class="item label hidden">
														发布时间
													</td>
													<td class="item value hidden" >
														<input type="text" id="remindtime" disabled   />
													</td>
												</tr>
												<tr id="trInfo" class="item">
													<td class="item label">当前信息</td>
													<td class="item value" colspan="3" style="position: relative;">
														<textarea id="remindinfo" class="colspan3 copy_text copy_text_indent" style="height: 70px;width:600px;" ></textarea>
														<a id="btnModify" href="#" name="btnModify" class="operate_detail hidden" style="position: absolute;bottom:0px;right:0px;">
															 修改
													    </a>
													</td>
												</tr>
												<tr class="item">
													<td id="tdRecord" class="item value" colspan="4">
														<div id="tabsRecord" style="font-size: 15px;"><ul></ul></div>
													</td>
												</tr>
											</table>
											<div class="operates_detail">
												<a id="btnClose" href="#btnClose" name="btnClose" class="operate_detail"  onclick="window.close()"> 关闭 </a>
											</div>
										</td>
									</tr>
									<tr>
										<td align="left" class="height"></td>
									</tr>
								</table>
							</td>
							<td class="wTableCenterRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 数据end -->
			<!-- 尾部 -->
			<tr>
				<td height="35" class="wTableBottomCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="35" class="wTableBottomLeft"></td>
							<td height="35" class="wTableBottomCenter"></td>
							<td width="16" class="wTableBottomRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 尾部end -->
		</table>
	</body>
</html>
