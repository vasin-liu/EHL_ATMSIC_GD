<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../Message.oni"%>
<%@page import="com.appframe.utils.*"%>
<%
	String roadname23 = StringHelper.obj2str(request.getParameter("tgsName"),""); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>卡口流量统计分析</title>
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
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript"
			src="../../../dispatch/js/bayonetcartogram/bayonetcartogram.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/FusionCharts.js"></script>
		
		<style type="text/css">
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
				filter: glow(color =     #ffffff, strength =     1);
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
				top: expression (     this .   parentElement .   parentElement .  
					parentElement .   scrollTop );
				z-index: 1;
				overflow: hidden;
				height: 25px;
				width: 100% text-overflow :     ellipsis
			}
			
			.rowstyle {
				height: 25px;
				cursor: hand;
				align: center;
			}
			
			.scrollRowThead {
				position: relative;
				left: expression(this .     parentElement .                
					  parentElement .   
					      
					       parentElement .         
					         parentElement .     scrollLeft);
				z-index: 0;
				text-align: left;
				background-color: #B4C1E2;
			}
			</style>
	</head>
	<body style="background: none; padding-top: 0px;" onload="Loading('<%=roadname23%>>')">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0" >
			<!-- 头部 -->
			<tr style="display: none">
				<td>
				</td>
			</tr>
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
														<span class="currentLocationBold">您当前的位置</span>：单独卡口流量统计
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
								<table width="1059" border="0" cellspacing="0" cellpadding="0" height="411">
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
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd" align="left">
											<p>
											</p>
											<table width="1015" border="0" cellspacing="3" cellpadding="0" height="63">
												<tr>
													<td class="currentLocation" style="text-align: right"
														width="110">
															统计类型：
													</td>
													<td width="74">
														<select id="CountType" onchange="reSetTimeShow();">
															<option value="1" selected="selected">环比统计</option>
															<option value="2">同比统计</option>
														</select>
													</td>
													<td class="currentLocation" width="110"
														style="text-align: right;">
														选择卡口：
													</td>
													<td id="showDwInfo">
														<input type="text" name="roadname" id="roadname"
															onclick="getRoadxml(),showMe(),hideView();" style="width: 80"/>
													</td>
													<td class="currentLocation" style="text-align: right" width="110">
														卡口方向：
													</td>
													<td id="showDepartType" width="74">
														<select style="width: 74" name="departType"
															id="departType">
															<option value="1" selected>
																全部
															</option>
															<option value="2">
																出省
															</option>
															<option value="3">
																入省
															</option>
														</select>
													</td>
													<td width="100" >
														<table id="table1"  width="325" height="33">
															<tr>
																<td style="text-align: left;">
																	<div class="lsearch">
																		<a href="#" onclick="searchTotalInfo(),showView();"
																			class="currentLocation"><span class="lbl">
																				分析</span> </a>
																	</div>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align: right"
														width="110">
														统计条件：
													</td>
													<td width="74">
														<select style="width: 74" id="alarmTotalRadio" 
															name="alarmTotalRadio" onChange="reSetTimeShow(),reSetTime();">
															<option value="1" selected>
																时间
															</option>
															<option value="2" onclick="showdatetype();">
																节假日
															</option>
														</select>
													</td>
													<td class="currentLocation" width="110"
														style="text-align: right" id="typename" >
														时&nbsp;&nbsp;&nbsp;&nbsp;间：
													</td>
													<td width="30" id="datename">
														<select style="width: 50" id="timeType" name="timeType"
															onChange="setTimeRadio();">
															<option value="1" selected>
																日
															</option>
															<option value="7">
																时
															</option>
															<option value="3">
																月
															</option>
															<option value="6">
																年
															</option>
														</select>
													</td>
													<td width="90" id="ResetTimeName" align="right">
														起止日期：
													</td>
													<td width="450" id="ResetTime">
														<input name="text2" type="text" style="width: 80"
															class="text" id="STARTTIME" onClick="SelectDate(this,0);" />
														-
														<input name="text" type="text" style="width: 80"
															class="text" id="ENDTIME" onClick="SelectDate(this,0);" />
													</td>
												</tr>
												<tr>
													<td align="right" id="counttypename">
													</td>
													<td id="checkbok" colspan="2">
													</td>
													<td id="countname" align="right"></td>
													<td id="countvalue" colspan="2"></td>
												</tr>
											</table>
											<p>
											</p>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height">
											<div id="theLayer" onblur="hideMe()"
											style="position: absolute; width: 550px; left: 15%;
											 top: 150; right: 15%; visibility: hidden;">
											
											</div>
											<div id="barids" style="visibility: visible"></div>
											<div id="barId" style="visibility: visible"></div>
											<div id='msg' align="center" style="width: 100%">
											</div>
										</td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData">
											<!--数据表格 -->
											<div style="overflow:scroll;border:solid;border-width:1px;height:200px;width:784px;margin-left:5" id="overSpeedList"  align = "center">
											</div>
											<!--数据表格-->
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
