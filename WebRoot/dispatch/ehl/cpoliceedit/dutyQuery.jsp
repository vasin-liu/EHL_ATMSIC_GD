<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.dispatch.common.FlowUtil"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("570900");
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));//单位编码
	String jgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));//机构名称
	String zbrxm = Constant.nvl(session
			.getAttribute(Constant.ZBRXM_VAR));
%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=title%></title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ccommon/calendar/DateAndSchar.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Department.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/dutyQuery.js"></script>
		<style type="text/css">
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
				 background:url(../../images/dispatch/btn.png) no-repeat; 
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
	<div id="showDivId"></div>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<input id="jgid" type="hidden" value="<%=jgid%>"></input>
			<input id="jgmc" type="hidden" value="<%=jgmc%>"></input>
			<input id="zbrxm" type="hidden" value="<%=zbrxm%>"></input>
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
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：<%=title%>
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
										<div align="left" style="width:100%; border: 1px solid #BFBFBF;">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr height="10px">
													<td colspan="7"></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align:right">
														<label>
															值班时间：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text"  style="width:105" id="dutyTime1" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" style="width:105" id="dutyTime2" maxLength="50" readonly  onclick="SelectDate(this,0);" />
													</td>
													<td class="currentLocation" style="text-align:right;">
														<label>
															值班单位：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<table border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td>
																	<input type="text" id="dutyUnit" maxLength="15" style="width: 160;"  readonly/>
																	<input type="hidden" id="dutyUCode"/>
																</td>
																<td>
																	<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor: hand;" onclick="showDepartTree('1','<%=jgid%>','<%=jgmc%>','dutyUnit','95','470')">
																</td>
															</tr>
														</table>
													</td>
													<td  align="left">
														<div class="lsearch">
															<a href="#" onclick="query();" class="currentLocation">
																<span class="lbl">查询</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td colspan="5"></td>
												</tr>
											</table>
											</div>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData">
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
							<td width="16" class="wTableBottomRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 尾部end -->
		</table>
	</body>
</html>
