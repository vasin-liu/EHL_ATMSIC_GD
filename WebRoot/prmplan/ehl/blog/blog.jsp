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
		<script type='text/javascript' src='${contextPath}base/js/new/filter.js'></script>
		<script type='text/javascript' src='${contextPath}base/js/new/base.js'></script>
		<script type='text/javascript' src='${contextPath}dispatch/js/ccommon/FileUpDownload.js'></script>
		<script type='text/javascript' src='../../js/blog.js'></script>
		<link href='${contextPath}base/css/new/main.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/detail.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/util.css' rel='stylesheet' type='text/css' />
		<link href='${contextPath}base/css/new/button.css' rel='stylesheet' type='text/css' />
		
	</head>
	<body>
		<input type="hidden" id="id" value="${param.id}">
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
												<tr class="item">
													<td class="item label">
														录入单位
													</td>
													<td class="item value">
														<input type="text" id="jgmc" disabled  />
														<input type="hidden" id="jgid"  />
													</td>
													<td class="item label">
														录入人
													</td>
													<td class="item value">
														<input type="text" id="pname" />
													</td>
												</tr>
												<tr class="item">
													<td class="item label">录入时间</td>
													<td class="item value" colspan="3" >
														<input type="text" id="time" disabled   />
													</td>
												</tr>
												<tr class="item">
													<td class="item label" >标题</td>
													<td id="tdTitle" class="item value" colspan="3">
														<input type="text" class="colspan3" id="title"/>
													</td>
												</tr>
												<tr class="item">
													<td class="item label" >内容</td>
													<td id="tdContent" class="item value"  colspan="3">
														<textarea id="content" class="colspan3"  ></textarea>
													</td>
												</tr>
												<tr class="item">
													<td class="item label" >附件</td>
													<td id="tdApath" class="item value" colspan="3">
													</td>
												</tr>
												<tr id="trReply" class="item hidden">
													<td class="item label" >疑难解答</td>
													<td id="tdReply" class="item value" style="width: 670px;" colspan="3">
														<div id="reply_content_" class="hidden"></div>
														<textarea id="reply_content" class="colspan3 hidden" ></textarea>
													</td>
												</tr>
											</table>
											<div class="operates_detail">
												<a id="btnAdd" href="javascript:void(0)"  class="operate_detail hidden" > 新增 </a>
												<a id="btnModify" href="javascript:void(0)" class="operate_detail hidden" > 修改 </a>
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
