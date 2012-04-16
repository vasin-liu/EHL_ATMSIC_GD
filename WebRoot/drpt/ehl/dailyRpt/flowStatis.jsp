<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/jsp/base.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${titleSelf}</title>
		<script type='text/javascript' src='${contextPath}base/js/prototype.js'></script>
		<script type='text/javascript' src='${contextPath}base/js/new/api.js'></script>
		<script type='text/javascript' src='${contextPath}base/js/new/validate.js'></script>
		<script type='text/javascript' src='${contextPath}base/js/new/dom.js'></script>
		<script type='text/javascript' src='${contextPath}dispatch/js/ccommon/FileUpDownload.js'></script>
		<script type='text/javascript' src='${contextPath}base/js/calendar/CalendarDate.js'></script>
		<script type='text/javascript' src='${contextPath}sm/js/pcs/DepartmentSelect.js'></script>
		<script type='text/javascript' src='${contextPath}sm/js/common/popup/Popup.js'></script>
		<script type='text/javascript' src='${contextPath}base/js/page/PageCtrl.js'></script>
	 	<script type='text/javascript' src='${contextPath}base/js/new/base.js'></script> 
		<script type='text/javascript' src='../../js/dailyRpt/flow.statis.js'></script>
		<link href='${contextPath}base/css/new/main.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/util.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}sm/css/popup/Popup.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/query.css' rel='stylesheet' type='text/css' />
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
														统计条件
													</td>
													<td></td>
												</tr>
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content">
															<tr class="height"><td></td></tr>
															<tr class="item">
																<td class="item label" >
																	录入日期：
																</td>
																<td class="item value" colspan="4">
																	<input type="text" id="stime" class="date" readonly onclick="SelectDate(this,0);" />
																	－<input type="text" id="etime"  class="date" readonly  onclick="SelectDate(this,0);" />
																</td>
																<td class="operates_query">
																	<a id="btnQuery" href="javascript:void(0)" class="operate_query">
																		统计
																	</a>
																</td>
															</tr>
															<tr class="height"><td></td></tr>
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
