<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/jsp/base.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${param.titleSelf}</title>
		<link href='${contextPath}base/css/new/main.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/util.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/query.css' rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${contextPath}general/js/jqgrid/jquery-1.7.1.min.js"></script>
		<script type='text/javascript' src='${contextPath}base/js/calendar/CalendarDate.js'></script>
		<script type='text/javascript' src='../../js/interface/bayonetVideo.js'></script>
		<link href="${contextPath}general/css/jqgrid/jquery-ui-1.8.2.custom.css" rel="stylesheet" media="screen" type="text/css" />
		<link href="${contextPath}general/css/jqgrid/ui.jqgrid.css" rel="stylesheet" media="screen" type="text/css" />
		<script type="text/javascript" src="${contextPath}general/js/jqgrid/grid.locale-cn.js"></script>
		<script type="text/javascript" src="${contextPath}general/js/jqgrid/grid.loader.js"></script>
		<script type="text/javascript" src="${contextPath}general/js/jqgrid/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="${contextPath}general/js/jquery.blockUI.js"></script>
		
	</head>
	<body>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr id="trTop" >
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
									<tr id="trQuery" >
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
																	卡口状态：
																</td>
																<td class="item value" >
																	<select id="state" style="width: 100px;">
																		<option value="0">全部</option>
																		<option value="1">正常</option>
																		<option value="2">异常</option>
																	</select>
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
									<tr id="trSepLine">
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
			<tr id="tdBottom" >
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
