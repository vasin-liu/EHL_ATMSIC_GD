<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="com.ehl.dispatch.common.FlowUtil"%>
<%@page import="com.appframe.common.Setting"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String title = FlowUtil.getFuncText("880905");	
	String daytime = Constant.getCurrentTime(false).substring(0,10);
	String publishTime = Setting.getString("publish_time");
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));//单位编码
	String jgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));//机构名称
	String zbrxm = Constant.nvl(session.getAttribute(Constant.ZBRXM_VAR));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<title><%=title %></title>
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/popup/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../js/util/String.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../FusionChartsV3.1/JSClass/FusionCharts.js"></script>
		<script type="text/javascript" src="../../js/common/Util.js"></script>
		<script type="text/javascript" src="../../js/util/Parameter.js"></script>
		<script type="text/javascript" src="../../js/util/Chart.js"></script>
		<script type="text/javascript" src="../../js/util/Date.js"></script>
		<script type="text/javascript" src="../../js/util/StatAnalysis.js"></script>
		<script type="text/javascript" src="../../js/util/Page.js"></script>
		<script type="text/javascript" src="../../js/assess/assessInfo.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/calendar/DateAndSchar.js"></script>
		<style type="text/css">
			A
			{
				COLOR: #291E40;
				FONT-FAMILY: Verdana;
				TEXT-DECORATION:none;
			}
			A:hover
			{
				COLOR: #FFA000;
			}
			/*宽高行高背景不显示超过对象尺寸的内容*/
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) no-repeat; 
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) center; 
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) center center; 
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
	<body style="background:none; padding-top:0px;" onload="setAssessItems();doAssessInfo('1');">

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
														<span class="currentLocationBold">您当前的位置</span>：<%=title %>
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
			<input type="hidden" id="publishTime" value="<%=publishTime %>" />
			<input id="cjgid" type="hidden" value="<%=jgid%>"/>
			<input id="cjgmc" type="hidden" value="<%=jgmc%>"/>
			<input id="cpname" type="hidden" value="<%=zbrxm%>"/>
			<!-- 数据 -->
			<tr>
				<td>
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
														分析条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr align="center" height="30">
													<td class="currentLocation" style="text-align:right">
														<label>
															考核范围：
														</label>
													</td>
													<td id="zhiduiTdId" align="left" colspan="1">

													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															考核日期：
														</label>
													</td>
													<td align="left" colspan="1">
														<input type="text" name="startDate"
															style="width:100;border: 1px #7B9EBD solid"
															id="startDate" maxLength="50" readonly value=''
															onclick="SelectDate(this,0);" onpropertychange="limitStartTime(this)"/>
															-
															<input type="text" name="endDate"
															style="width:100;border: 1px #7B9EBD solid" id="endDate"
															maxLength="50" readonly value=''
															onclick="SelectDate(this,0);" />
													</td>
													<script language="javascript">
														var date = new Date();
														var end = date.format_(3);
														date.setMonth(0);
														date.setDate(1);
														var start = date.format_(3);
														$("startDate").value = start;
														$("endDate").value = end;
													</script>
													
													<td align="center">
														<span class="lsearch"> <a href="#"
															onclick="showExcel()" class="currentLocation">
															 <span class="lbl">导出Excel</span> </a> </span>
													</td>
												</tr>
												<tr align="center" height="30">
													<td class="currentLocation" style="text-align:right">
														<label>
															考核类型：
														</label>
													</td>
													<td  align="left" colspan="3">
														<select id="type" style="width: 170px;" onchange="typeChange()">
															<option value="">全部</option>
															<option value="1">上报交通信息</option>
															<option value="2">上报新闻材料</option>
														</select>
													</td>
													<td align="center">
														<span id="statis" class="lsearch"> <a href="#"
															onclick="doAssessInfo()" class="currentLocation"> <span
																class="lbl">统计</span>
														</a> </span>
													</td>
												</tr>	
											</table>
										</td>
									</tr>
									<tr height="40">
										<td align="center" valign="middle">
											<font style="font-size: 15px;font-weight: bold;">综合计分考评表</font>
											<div style="width: 40%;margin:-17 0 0 0;padding:1px;display: inline;float: right;">
												<label style="font-size: 12px;">选择排序项目：</label><select id="assessItem" style="width:120px;" ></select>
											</div>
										</td>
									</tr>
									<tr>
										<td class="height" id="showTable">
											
										</td>
									</tr>
									<tr>
										<td id="showTdId" class="sleektd" align="center">
											<div id="showDivId" width="700" height="350"
												style="overflow: scroll;">

											</div>
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
	</body>
</html>
