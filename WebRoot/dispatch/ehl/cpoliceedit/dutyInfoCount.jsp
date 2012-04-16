<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));//单位编码
	String jgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));//机构名称
	String jgcc = Constant.nvl(session.getAttribute(Constant.JGCC_VAR));
	String jglx = Constant.nvl(session.getAttribute(Constant.JGLX_VAR));
	String zbrxm = Constant.nvl(session
			.getAttribute(Constant.ZBRXM_VAR));
	String appid = Constant.nvl(session
			.getAttribute(Constant.APPID_VAR));
	String octime = Constant.getCurrentTime(false).substring(0, 16);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>值班信息统计</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/Popup.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript"
			src="../../js/ceditpolice/dutyInfoCount.js"></script>
		<%--		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>--%>
		<%--		<script type="text/javascript" src="../../../sm/js/pcs/PersonTree.js"></script>--%>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<script language="JavaScript"
			src="../../../FusionChartsV3.1/JSClass/FusionCharts.js"></script>
		<script type="text/javascript"
			src="../../js/ccommon/calendar/DateAndSchar.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Department.js"></script>
		<style type="text/css">
<!--
机构复选框列表，在dutyInfoCount.jsp的样式背景下，需要使用poptable的ID样式。
		-->#poptable {
	position: static;
}

.scrollColThead {
	position: relative;
	top: expression(this.parentElement.parentElement.parentElement.scrollTop);
	z-index: 2;
	overflow: hidden;
	text-overflow: ellipsis
}

.td_font {
	font-size: 13px;
}

.deptDivClass {
	z-index: 10000;
	position: absolute;
	display: inline;
	width: 250;
	height: 340;
}

table {
	font-size: 9pt;
	font-weight: normal;
	margin-left: 0px;
}  /*宽高行高背景不显示超过对象尺寸的内容*/
.lsearch {
	width: 82px;
	height: 22px;
	line-height: 22px;
	background: url(../../images/dispatch/btn.png) no-repeat;
	overflow: hidden;
}

/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
.lsearch a {
	display: block;
	height: 22px;
	background: url(../../images/dispatch/btn.png) center;
	text-decoration: none;
	line-height: 22px;
	overflow: hidden;
}

/*高度固定背景行高*/
.lsearch a:hover {
	height: 22px;
	background: url(../../images/dispatch/btn.png) center center;
	line-height: 22px;
}

/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
.lsearch .lbl {
	display: block;
	width: 79px;
	height: 15px;
	padding-left: 3px;
	padding-top: 0px;
	margin: 0 auto;
	text-align: center;
	color: #ffffff;
	cursor: pointer;
}

/*颜色滤镜效果*/
.lsearch a:hover .lbl {
	color: #dae76b;
	filter: glow(color = #ffffff, strength = 1);
}

.scrolltable {
	border-bottom: 1px solid #CCCCCC;
	border-right: 1px solid #CCCCCC;
}

.scrolltable td,.scrollTable th {
	border-left: 1px solid #CCCCCC;
	border-top: 1px solid #CCCCCC;
	padding: 1px;
}

.scrollColThead {
	position: relative;
	top: expression (
		this.parentElement.parentElement.parentElement.scrollTop );
	z-index: 1;
	overflow: hidden;
	height: 25px;
	width: 100% text-overflow : ellipsis
}

.rowstyle {
	height: 25px;
	cursor: hand;
	align: center;
}

.scrollRowThead {
	position: relative;
	left: expression(this.parentElement.parentElement.parentElement.parentElement.scrollLeft
		);
	z-index: 0;
	text-align: left;
	background-color: #B4C1E2;
}
</style>
	</head>
	<body style="background: none; padding-top: 0px;">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
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
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="5%">
														<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif"
																width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：值班信息统计
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
					<div align="right">
					</div>
					<table height="100%" width="100%" border="0" cellspacing="0"
						cellpadding="0">
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
														统计条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<input id="cjgid" type="hidden" value="<%=jgid%>" />
									<input id="cjgmc" type="hidden" value="<%=jgmc%>" />
									<input id="cjglx" type="hidden" value="<%=jglx%>" />
									<input id="cpname" type="hidden" value="<%=zbrxm%>" />
									<input id="appid" type="hidden" value="<%=appid%>" />
									<input id="ctime" type="hidden" value="<%=octime%>" />
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd" align="left">
											<p>
												&nbsp;

											</p>
											<table width="100%" border="0" cellspacing="3" cellpadding="0">
												<tr>
													<td class="currentLocation" style="text-align: right"
														width="15%">
														信息类别：
													</td>
													<td width="25%">
														<select style="width: 190" id="atype" onchange="setSortItems();">
															<option value="0" selected>
																全部
															</option>
															<option value="1">
																交通事故
															</option>
														</select>
													</td>
													<td width="20%" align="right">
														起止日期：
													</td>
													<td width="25%">
														<input type="text" style="width: 125" class="text"
															id="stime" onClick="SelectDate(this,0);"
															readonly="readonly" />
														-
														<input type="text" style="width: 127" class="text"
															id="etime" onClick="SelectDate(this,0);"
															readonly="readonly" />
													</td>
													<td valign="middle" align="center" rowspan="3">
														<div id="query" class="lsearch">
															<a href="#" onclick="query()" class="currentLocation">
																<span class="lbl">统计</span> </a>
														</div>
													</td>
												</tr>
												<tr height="25">
													<td class="currentLocation" style="text-align: right"
														width="15%">
														<div>
															统计级别：
														</div>
													</td>
													<td width="25%">
														<select style="width: 190; disabled: true;" id="sostyle"
															onchange="sostyleOnchange(this)">
															<option value="4" selected>
																支队统计
															</option>
															<option value="6">
																大队统计
															</option>
														</select>
													</td>
													<td class="currentLocation" width="15%"
														style="text-align: right">
														<div>
															选择单位：
														</div>
													</td>
													<td width="25%">
														<div id="sobject_single_div">
														</div>
														<div id="sobject_multi_div">
															<table cellpadding="0" cellspacing="0">
																<tr height="25">
																	<td>
																		<input type="text" id="sobject_multi_jgmc"
																			style="width: 270;" readOnly />
																		<input type="hidden" id="sobject_multi_jgid" />
																	</td>
																	<td>
																		<img id="sobject_multi_img"
																			src="../../images/popup/btnselect.gif" alt="选择单位"
																			style="cursor: hand;"
																			onClick="setTree(' ',125,415,null,'02')">
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
												<tr height="25">
													<td  class="currentLocation" style="text-align: right" width="15%">
														统计范围：
													</td>
													<td >
														<input type="radio" id="area_rule" name="area" value="1" checked>
														<label for="area_rule">
															总队规定须报送和发布的信息
														</label>
														<br/>
														<input type="radio" id="area_all" name="area" value="">
														<label for="area_all">
															全部信息
														</label>
													</td>
													<td class="currentLocation" style="text-align: right" width="15%">
														<label style="font-size: 12px;">排序项目：</label>
													</td>
													<td>
														<select id="sortItem" style="width:120px;" ></select>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 部数据列表分 -->
									<tr>
										<td id="datatab"></td>
									</tr>
									<!-- 部数据列表分end -->
									<tr>
										<td class="height"></td>
									</tr>
									<tr>
										<td class="sleektd" align="center">
											<div id="barId" align="center"></div>
										</td>
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
							<td width="16" class="wTableBottomRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 尾部end -->
		</table>
		<div id="showDiv2Id" style="display: none"></div>
	</body>
</html>
