<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ page import="com.ehl.sm.common.util.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc = strObj[1];//机构名称
%>
<html>
	<head>
		<title>春运交通管理工作统计日报表</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"  type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"	type="text/css" />
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../js/dailyRpt/dailyRptWeekStat.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/output/SaveAsExcel.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript">
			var jgid = '<%=jgid%>';
			var jgmc = '<%=jgmc%>';
		</script>
	    <style type="text/css">
			<!--
			table{
				font-size:11px;
			}
			.tableInput{
				border:1px solid #b5d6e6;
			}
			.td_r_b1{ 
			    border-bottom:1px solid #b5d6e6; 
				border-right:1px solid #b5d6e6; 
			}
			.STYLE2 {
				color: #FF0000;
			}
			.input1{
				border:0;
				width:40px;
				text-align:center;
			}
			.input2{
				border:0;
				width:40px;
				border-bottom:1px solid #b5d6e6; 
			}
			.input3{
				border:0;
				width:100px;
			}
			*{
				margin:0;
				pading:0;
			}
			.td_r_lastline{ 
			     border-bottom:0px solid #b5d6e6; 
				 border-right:1px solid #b5d6e6; 
				 text-align:center;
			}
			.td_r_lastline2{ 
			     border-bottom:0px solid #b5d6e6; 
				 border-right:1px solid #b5d6e6; 
				 text-align:left;
			}
			-->
        </style>
	</head>
	<body onload='initWeekTime(jgid)'>
		<table width="100%" height="100%" id="queryTable" border="0" cellspacing="0"
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
														<span class="currentLocationBold">您当前的位置</span>：报部交管局日报表
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
									<!-- 查询条件部分 -->
									<tr>
										<td>
											<table border="0" cellspacing="0"
												cellpadding="0" width=99% align=center>
												<tr>
													<td align="left">
														<table width="141" border="0" cellspacing="0" cellpadding="0">
															<tr><td class="sleek textB">统计条件</td></tr>
														</table>
													</td>
												</tr>
												<tr>
												  <td class="sleektd">
														<table width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr align="center" height="35">
																<td width="12%" align="right" class="currentLocation">
																	<p align="right">开始时间：</p></td>
																<td align="left" width="20%">
																	<input name="startTime" type='text' id='startTime' style="width:170px;" onClick="SelectDate(this,0);" readOnly>																</td>
																<td align="right" width="12%" class="currentLocation">
																	<p align="right">结束时间：</p></td>
																<td align="left" width="20%">
																	<input name="endTime" type='text' id='endTime' style="width:170px;" onClick="SelectDate(this,0);" readOnly>																</td>
																<td class="currentLocation" style="text-align:right;" width="12%" >
																	<input id="statisticsCaseInfo" type="image" src="../../images/button/btnStat.gif"  onClick="weekStat('<%=jgid%>')"/>															</td>
																
															  <td class="currentLocation" style="text-align:left;" >	&nbsp;
																	<input id="OutExcelCaseInfo" type="image" src="../../images/button/btnOutExcel.gif"  alt="统计有数据时可用导出" onClick="outExcel()"/>
																	&nbsp;<span class="currentLocation" style="text-align:left;">
																	<input name="image" type="image" id="image" onClick="printFor()" src="../../images/button/print.gif" 
																		alt="打印"/>
																</span>																</td>
																<td class="currentLocation" style="text-align:left;" align="left">&nbsp;</td>
															</tr>
														</table>
												  </td>
												</tr>
											</table>
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
										<td>
											<div id ='print'>
											<table width="99%" align="center" class="tableInput" id="dataTable" cellpadding="0" cellspacing="0">
									 			<tr>
									                <th width="10%" class="td_r_b">序号</th>
									                <th width="28%" class="td_r_b">项目</th>
									                <th width="8%" class="td_r_b">数量</th>
									                <th width="10%" colspan="2" class="td_r_b">序号</th>
									                <th width="36%" colspan="2" class="td_r_b">项目</th>
									                <th width="8%" class="td_b">数量</th>
									            </tr>
									            <tr>
									                <td class="td_r_b">1</td>
									                <td class="td_r_b1">投入警力（人次）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text1" readonly/></td>
									                <td colspan="2" class="td_r_b">11</td>
									                <td colspan="2" class="td_r_b1">吊销或暂扣机动车驾驶证（本）</td>
									                <td class="td_b"><input type="text" class="input1" id="text11" readonly/></td>
									            </tr>
									            <tr>
									                <td class="td_r_b">2</td>
									                <td class="td_r_b1">出动警车（辆次）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text2" readonly/></td>
									                <td colspan="2" class="td_r_b">12</td>
									                <td colspan="2" class="td_r_b1">卸客转运（人）</td>
									                <td class="td_b"><input    type="text" class="input1" id="text12" readonly/></td>
									            </tr>
									            <tr>
									                <td class="td_r_b">3</td>
									                <td class="td_r_b1">启动检查服务站（个）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text3" readonly/></td>
									                <td colspan="2" class="td_r_b">13</td>
									                <td colspan="2" class="td_r_b1">清理驾驶人记分（人）</td>
									                <td class="td_b"><input   type="text" class="input1" id="text13" readonly/></td>
									            </tr>
									            <tr>
									                <td class="td_r_b">4</td>
									                <td class="td_r_b1">设置临时执勤点（个）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text4" readonly/></td>
									                <td colspan="2" class="td_r_b">14</td>
									                <td colspan="2" class="td_r_b1">深入专业运输企业（个）</td>
									                <td class="td_b"><input type="text" class="input1" id="text14" readonly/></td>
									            </tr>
									            <tr>
									                <td class="td_r_b">5</td>
									                <td class="td_r_b1">检查客运车辆（辆）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text5" readonly/></td>
									                <td colspan="2" class="td_r_b">15</td>
									                <td colspan="2" class="td_r_b1">排除安全隐患客车（辆）</td>
									                <td class="td_b"><input   type="text" class="input1" id="text15" readonly/></td>
									            </tr>
									            <tr>
									                <td class="td_r_b">6</td>
									                <td class="td_r_b1">共查处交通违法行为（起）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text6" readonly/></td>
									                <td colspan="2" class="td_r_b">16</td>
									                <td colspan="2" class="td_r_b1">通过广播、电视开展宣传（次）</td>
									                <td class="td_b"><input  type="text" class="input1" id="text16" readonly/></td>
									            </tr>
												<tr>
									                <td class="td_r_b">7</td>
									                <td class="td_r_b1">查处超速行驶（起）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text7" readonly/></td>
									                <td colspan="2" class="td_r_b">17</td>
									                <td colspan="2" class="td_r_b1">发放宣传材料、设置宣传提示牌（份、块）</td>
									                <td class="td_b"><input  type="text" class="input1" id="text17" readonly/></td>
									            </tr>
												<tr>
									                <td class="td_r_b">8</td>
									                <td class="td_r_b1">查处客车超员（起）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text8" readonly/></td>
									                <td colspan="2" class="td_r_b">18</td>
									                <td colspan="2" class="td_r_b1">启动恶劣天气应急预案（次）</td>
									                <td class="td_b"><input  type="text" class="input1" id="text18" readonly/></td>
									            </tr>
												<tr>
									                <td class="td_r_b">9</td>
									                <td class="td_r_b1">查处疲劳驾驶（起）</td>
									                <td class="td_r_b"><input class="input1" type="text" id="text9" readonly/></td>
									                <td colspan="2" class="td_r_b">19</td>
									                <td colspan="2" class="td_r_b1">应急疏导、分流车辆（辆）</td>
									                <td class="td_b"><input  type="text" class="input1" id="text19" readonly/></td>
									            </tr>
												<tr>
									                <td class="td_r_lastline">10</td>
									                <td class="td_r_lastline2">查处酒后驾驶（起）</td>
									                <td class="td_r_lastline"><input class="input1" type="text" id="text10" readonly/></td>
									                <td colspan="2" class="td_r_lastline">20</td>
									                <td colspan="2" class="td_r_lastline2">整治危险路段（处）</td>
									                <td align='center'><input  type="text" class="input1" id="text20" readonly/></td>
									            </tr>
									            
											</table>
											</div>
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
			<!-- 尾部end -->
		</table>
		<%@ include file="weekPrintOperation.jsp"%>
	</body>
</html>