<%@page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/base/jsp/base.jsp"%>
<%
String funcs = JSONArray.fromObject(request.getAttribute("jsonFunc")).toString();
String script = "<script>baseInfo.data={funcs:"+funcs+"};</script>";
out.write(script);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>${titleSelf}</title>
		<link href="${contextPath}base/css/new/main.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}base/css/new/detail.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}base/css/new/util.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}base/css/new/button.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/util/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.json-2.3.min.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/new/api.date.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/new/api.array.js"></script>
		<link rel="stylesheet" type="text/css" href="${contextPath}util/jquery/jquery-ui/themes/start/jquery-ui-1.8.17.custom.css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-ui/jquery-ui-1.8.17.custom.min.js"></script>
		<script type="text/javascript" src="${contextPath}/util/jquery/jquery.bgiframe.js"></script>
		<link href="${contextPath}util/jquery/jquery-validation/jquery.validate.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-validation/jquery.validate.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-validation/localization/messages_cn.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-validation/jquery.validate.custom.js"></script>
<%-- 		<link href="${contextPath}util/jquery/tooltips/poshytip-1.1/src/tip-darkgray/tip-darkgray.css" rel="stylesheet" type="text/css" /> --%>
<%-- 		<script type="text/javascript" src="${contextPath}util/jquery/tooltips/poshytip-1.1/src/jquery.poshytip.min.js"></script> --%>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.metadata.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.form.js"></script>
		<link href="${contextPath}util/jquery/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}util/jquery/uploadify/uploadify.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}util/jquery/uploadify/swfobject.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/uploadify/jquery.uploadify.custom.js"></script>
		<link rel="stylesheet" href="${contextPath}util/jquery/JQuery zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${contextPath}util/jquery/JQuery zTree/js/jquery.ztree.core-3.1.js"></script>
		<script type="text/javascript" src="${contextPath}dispatch/js/ccommon/Department.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/new/base.js"></script>
		<script type="text/javascript" src="${contextPath}help/js/leave.js"></script>
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
											<table width="100%" border="0" cellspacing="0" cellpadding="0" class="data_detail">
												<form id="formLeave" class="validate">
												<input id="id" name="id" type="hidden" value="${leave.id}"/>
												<tr class="item">
													<td class="item label">
														录入单位
													</td>
													<td class="item value">
														<input id="jgmc" name="jgmc" type="text"   value="${leave!=null?leave.jgmc:jgmc}" disabled="disabled" />
													</td>
													<td class="item label">
														录入人
													</td>
													<td class="item value">
														<input id="pname" name="pname" type="text"  value="${leave!=null?leave.pname:zbrxm}"  class="required {rangelength:[2,15],specialchar:true}" disabled="disabled"/>
													</td>
												</tr>
												<tr class="item">
													<td class="item label">录入时间</td>
													<td class="item value" >
														<input id="time" name="time" type="text" value="${leave!=null?leave.time:time}" disabled="disabled" />
													</td>
													<td class="item label">
														紧急程度
													</td>
													<td class="item value">
														<select id="jjcd" name="jjcd" disabled="disabled" class="requited">
															<c:forEach items="${listJjcd}" var="jjcd" >
																<option value="${jjcd.dm}" ${jjcd.dm==leave.jjcd?"selected":""}>${jjcd.dmsm}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr class="item">
													<td class="item label">
														反馈类型
													</td>
													<td id="tdFklx" class="item value">
														<select id="fklx" name="fklx" disabled="disabled">
															<c:forEach items="${listFklx}" var="fklx" >
																<option value="${fklx.dm}" ${fklx.dm==leave.fklx?"selected":""}>${fklx.dmsm}</option>
															</c:forEach>
														</select>
													</td>
													<td id="tdFuncDesc" class="item label">
														相关功能
													</td>
													<td id="tdFunc" class="item value">
														<input id="funcDesc" type="text" class="{required:'#tdFunc:visible'}" disabled="disabled" />
														<input id="func" name="func" type="hidden" value="${leave.func}" />
														<img id="imgFunc" src="${contextPath}sm/image/popup/btnselect.gif" alt="选择问题或者漏洞出现的功能模块" style="cursor: hand;display: none;"/>
														<div id="divFunc" style="display: none;" title="选择功能模块">
															<ul id="ulFunc" class="ztree"></ul>
														</div>
													</td>
												</tr>
												<tr class="item">
													<td class="item label" >标题</td>
													<td id="tdTitle" class="item value" colspan="3">
														<input id="title" name="title" type="text"  value="${leave.title}" class="colspan3 required {rangelength:[3,24],specialchar:true}" disabled="disabled" />
													</td>
												</tr>
												<tr class="item">
													<td class="item label" >内容</td>
													<td id="tdContent" class="item value"  colspan="3">
														<textarea id="content" name="content" class="colspan3 required {rangelength:[1,2000],specialchar:true}"  disabled="disabled">${leave.content}</textarea>
													</td>
												</tr>
												<tr class="item">
													<td class="item label" >附件</td>
													<td id="tdApath" class="item value" colspan="3" >
														<input id="apathDesc" type="text" disabled="disabled" />
														<input id="apath" name="apath" type="hidden" value="${leave.apath}" />
													</td>
												</tr>
												</form>
												<form id="formReply" class="validate">
												<input name="jgid"  type="hidden" value="${jgid}"/>
												<input name="pname" type="hidden" value="${zbrxm}"/>
												<input name="time"  type="hidden" value="${time}"/>
												<input name="lid"   type="hidden" value="${leave.id}"/>
												<tr id="trState" class="item hidden" >
													<td class="item label">
														处理完成
													</td>
													<td class="item value" colspan="3">
														<input id="ckState" type="checkbox" style="width: auto;" disabled="disabled"/>
														<input id="state" name="state" type="hidden" value="${leave.state}"/>
													</td>
												</tr>
												<tr id="trReply" class="item hidden" >
													<td class="item label" >解答</td>
													<td id="tdReply" class="item value" colspan="3">
														<c:if test="${fn:length(leave.replys)>0}">
														<div style="margin: 5 0 10 10;padding: 0;">
															<c:forEach items="${leave.replys}" var="reply" >
															<p style="text-indent: 2em;margin: 10 0 0 0;">
															 ${reply.content}<br/>
															 [${reply.jgmc} ${reply.pname} ${reply.time}]<br/>
															 </p>
															</c:forEach>
														</div>
														</c:if>
														<textarea id="content" name="content" class="colspan3 required {specialchar:true}" ></textarea>
													</td>
												</tr>
												</form>
											</table>
											<div class="operates_detail">
												<a id="btnPrint" href="javascript:void(0)" class="operate_detail hidden" > 打印 </a>
												<a id="btnAdd" href="javascript:void(0)"  class="operate_detail hidden" > 新增 </a>
												<a id="btnModify" href="javascript:void(0)" class="operate_detail hidden" > 修改 </a>
												<a id="btnReply" href="javascript:void(0)" class="operate_detail hidden" > 回复 </a>
												<a id="btnClose" href="javascript:void(0)" class="operate_detail hidden"  onclick="window.close()"> 关闭 </a>
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
