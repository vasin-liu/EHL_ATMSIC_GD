<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../Message.oni"%>
<%
String title = request.getParameter("nodeDesc");
title = title == null ? "交通监控表":title;
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=title %></title>
		<link href="../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<link href="../css/jqgrid/jquery-ui-1.8.2.custom.css" rel="stylesheet" media="screen" type="text/css" />
		<link href="../css/jqgrid/ui.jqgrid.css" rel="stylesheet" media="screen" type="text/css" />
		<script type="text/javascript" src="../js/jqgrid/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../js/jqgrid/grid.locale-cn.js"></script>
		<script type="text/javascript" src="../js/jqgrid/grid.loader.js"></script>
		<script type="text/javascript" src="../js/jqgrid/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="../js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../js/alarmScanWord.js"></script>
		<style type="text/css">
			.not_m_line {
    			white-space:nowrap; overflow:hidden;
			}
			
			.td_font {
				font-size: 13px;
			}
			
			.deptDivClass {
				z-index:10000;
				position:absolute;
				display:inline;
				width:250;
				height:340;
			}
			table{
				font-size:9pt;
				font-weight:normal;
				margin-left:0px;
			}
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../image/button/btn.png) no-repeat; 
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../images/dispatch/btn.png) center; 
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../images/dispatch/btn.png) center center; 
				 line-height:22px;
			}
			/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
			.lsearch .lbl{ 
			     display:block;
				 width:79px; 
				 height:15px; 
				 padding-left:3px; 
				 padding-top:0px; 
				 margin:0 auto; 
				 text-align:center; 
				 color:#ffffff; 
				 cursor:pointer;
			}
			/*颜色滤镜效果*/
			.lsearch a:hover .lbl{ 
			     color:#dae76b; 
				 filter:glow(color=#ffffff,strength=1);
			}
		</style>
	</head>
	<body style="background: none; padding-top: 0px;">
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
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="5%">
														<div align="center">
															<img src="../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：路面动态 -> <%=title %>
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
									<tr>
										<td align="left">
											<table width="141" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="sleek textB">
														查询条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align:right" width="80">
														<label>
															起止时间：
														</label>
													</td>
													<td id="columnTd" align="left"  colspan="2" width="30%">
														<input type="text" name="startDate" id="startDate"  readonly onclick="SelectDay(this,0);" style="width: 110px" />
														-
														<input type="text" name="endDate"  id="endDate"  readonly onclick="SelectDay(this,0);" style="width: 110px" />
													</td>
													<td class="currentLocation" style="text-align:right;" width="120">
														<label>
															统计方式：
														</label>
													</td>
													<td id="generalInfoType" align="left" >
														<select id="countType_select" style="width:133;">
															<option value="alarmregionid">按区域统计</option>
															<option value="roadid">按道路统计</option>
														</select>
													</td>
													<td align="center">
														<div class="lsearch" >
															<span class="lbl" style="padding-right: 10px;" onclick="loadGrid();" >
																查询
															</span>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td width="100%">
											<table id="tb_tableId"></table>
											<div id="div_pageId"></div>
										</td>
									</tr>
									<!-- 占行高部分end -->
								</table>
							</td>
							<td class="wTableCenterRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 数据end -->
			<!-- 尾部 -->
			<!-- 
			<tr>
				<td height="35" class="wTableBottomCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="35" class="wTableBottomLeft"></td>
							<td width="16" class="wTableBottomRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			 -->
			<!-- 尾部end -->
		</table>
	</body>
</html>
