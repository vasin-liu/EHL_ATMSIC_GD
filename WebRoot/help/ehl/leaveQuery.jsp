<%@page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/base/jsp/base.jsp"%>
<%
String states = JSONArray.fromObject(request.getAttribute("listState")).toString();
String jjcds = JSONArray.fromObject(request.getAttribute("listJjcd")).toString();
String fklxs = JSONArray.fromObject(request.getAttribute("listFklx")).toString();
String funcs = JSONArray.fromObject(request.getAttribute("jsonFunc")).toString();
String script = "<script>baseInfo.data={states:"+states+",jjcds:"+jjcds+",fklxs:"+fklxs+",funcs:"+funcs+"};</script>";
// System.out.println("script:"+script);
out.write(script);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${titleSelf}</title>
		<link href="${contextPath}base/css/new/main.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}base/css/new/util.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}base/css/new/query.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}sm/css/popup/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.json-2.3.min.js"></script>
		<link href="${contextPath}util/jquery/jquery-ui/themes/start/jquery-ui-1.8.17.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-ui/jquery-ui-1.8.17.custom.min.js"></script>
		<script type="text/javascript" src="${contextPath}/util/jquery/jquery.bgiframe.js"></script>
		<link href="${contextPath}util/jquery/jquery-validation/jquery.validate.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-validation/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${contextPath}/util/jquery/jquery-validation/localization/messages_cn.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-validation/jquery.validate.custom.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.metadata.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.form.js"></script>
		<link href="${contextPath}util/jquery/jqgrid/ui.jqgrid.css" rel="stylesheet"  type="text/css" />
		<link href="${contextPath}util/jquery/jqgrid/ui.jqgrid.specialConfig.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jqgrid/i18n/grid.locale-cn.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jqgrid/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/prototype.js"></script>
		<script type="text/javascript" src="${contextPath}sm/js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="${contextPath}sm/js/common/popup/Popup.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/calendar/CalendarDate.js"></script>
	 	<script type="text/javascript" src="${contextPath}base/js/new/base.js"></script>
	 	<link rel="stylesheet" href="${contextPath}util/jquery/JQuery zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${contextPath}util/jquery/JQuery zTree/js/jquery.ztree.core-3.1.js"></script>
<%-- 		<link href="${contextPath}1/tooltips/poshytip-1.1/src/tip-darkgray/tip-darkgray.css" rel="stylesheet" type="text/css" /> --%>
<%-- 		<script type="text/javascript" src="${contextPath}1/tooltips/poshytip-1.1/src/jquery.poshytip.min.js"></script> --%>
		<script type="text/javascript" src="${contextPath}dispatch/js/ccommon/Department.js"></script>
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
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="container">
									<!-- 查询条件部分 -->
									<tr>
										<td align="left">
											<table width="100%" border="0" cellspacing="0" cellpadding="0" class="conditioncontainer">
												<tr>
													<td class="title">
														查询条件
													</td>
													<td></td>
												</tr>
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content">
															<tr class="height_low"><td></td></tr>
															<form id="formLeave" class="validate">
																<select id="jjcd" name="jjcd"  style="display: none;">
																	<c:forEach items="${listJjcd}" var="jjcd" >
																		<option value="${jjcd.dm}" >${jjcd.dmsm}</option>
																	</c:forEach>
																</select>
															<tr class="item">
																<td class="item label">
																	录入单位：
																</td>
																<td class="item value dept" >
																	<input id="jgmc" type="text" readonly />
																	<input id="jgid" name="jgid" type="hidden" value="${jgid}"/>
																</td>
																<td align="left">
																	<img id="imgJgid" src="${contextPath}sm/image/popup/deptselect.gif" alt="选择机构" style="cursor: hand;"
																		onclick="showDepartTree('${jgcc}','${jgid }','${jgmc }','jgmc')">
																</td>
																<td class="item label">
																	标题关键字：
																</td>
																<td class="item value" >
																	<input id="title" name="title" type="text" class="{rangelength:[1,24],specialchar:true}" />
																</td>
																<td class="operates_query">
																	<a id="btnQuery" href="javascript:void(0)" class="operate_query">
																		查询
																	</a>
																</td>
															</tr>
															<tr class="height_low"><td></td></tr>
															<tr class="item">
																<td class="item label" >
																	录入日期：
																</td>
																<td class="item value" colspan="2">
																	<input id="stime" name="stime" type="text" class="datewidth" readonly onclick="SelectDate(this,0);" />
																      －<input id="etime" name="etime" type="text" class="datewidth" readonly onclick="SelectDate(this,0);" />
																</td>
																<td class="item label">
																	处理状态：
																</td>
																<td class="item value" >
																	<select id="state" name="state" >
																		<option value="">全部</option>
																		<c:forEach items="${listState}" var="state" >
																			<option value="${state.dm}" ${state.dm=="000001"?"selected":""}>${state.dmsm}</option>
																		</c:forEach>
																	</select>
																</td>
															</tr>
															<tr class="height_low"><td></td></tr>
															<tr class="item">
																<td class="item label" >
																	反馈类型：
																</td>
																<td id="tdFklx" class="item value" colspan="2">
																	<select id="fklx" name="fklx" >
																		<option value="">全部</option>
																		<c:forEach items="${listFklx}" var="fklx" >
																			<option value="${fklx.dm}" >${fklx.dmsm}</option>
																		</c:forEach>
																	</select>
																</td>
																<td id="tdFuncDesc" class="item label">
																	相关功能：
																</td>
																<td id="tdFunc" class="item value" >
																	<input id="funcDesc" type="text" class="{required:'#tdFunc:visible'}" readonly="readonly" />
																	<input id="func" name="func" type="hidden" value="${leave.func}" />
																	<img id="imgFunc" src="${contextPath}sm/image/popup/btnselect.gif" alt="选择问题或者漏洞出现的功能模块" style="cursor: hand;"/>
																	<div id="divFunc" style="display: none;" title="选择功能模块">
																		<ul id="ulFunc" class="ztree"></ul>
																	</div>
																</td>
															</tr>
															</form>
															<tr class="height_low"><td ></td></tr>
														</table>
													</td>
												</tr>
											</table>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData" class="datacontainer">
											<table id="tblData"></table>
											<div id="pager"></div>
										</td>
									</tr>
									<!-- 部数据列表分end -->
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
