<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.ehl.drpt.dailyRpt.action.DailyRptModifyAction"%>
<%@ page import="com.ehl.sm.common.Constants" %>
<%@ include file="../../../Message.oni"%>
<%
	DailyRptModifyAction dra = new DailyRptModifyAction();
	Object[] dpt = dra.getRptDpt((String)pname);
	dpt = dpt == null ? new Object[]{"000000","测试"}:dpt;
	String tbdwid = dpt[0].toString();
	String user = (String) session.getAttribute(Constants.PNAME_KEY);
%>

<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>春运道路交通安全管理日报表</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<SCRIPT type="text/javascript" src = "../../js/drpt/dailyReport.js"></SCRIPT>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<style type="text/css">
			.td_font{
				font-size:13px;
			}
		</style>
		<script type="text/javascript">
			var user = '<%=user%>';
		</script>
	</head>
	<body style="background:none; padding-top:0px;" onLoad="doOnLoad();">
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
														<span class="currentLocationBold">您当前的位置</span>：春运日报表查询
														<input type="hidden" value="<%= tbdwid %>" id="jgbh" />
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
													<td class="sleek textB">查询条件</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd" align="center">
											<table border="0" cellspacing="0"
												cellpadding="0" width=92%>
												<tr>
													<td class="currentLocation" style="text-align:right" width=70>
														<label>
															填表人：
														</label>
													</td>
													<td align="left" width=172>
														<input type="text" name="tbr" style="width:170"
															id="tbr" maxLength="50"/>
													</td>
													<td class="currentLocation" style="text-align:right" width=156>
														<label>
															日报时间段：
														</label>
													</td>
													<td align="left" width=75>
														<input type="text" name="sj1" style="width:75px;"
															id="sj1" maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td style="text-align:center;width:20px;margin-right:5px;" width=12>-</td>
													<td align="left" width=75>
														<input type="text" name="sj2" style="width:75px;"
															id="sj2" maxLength="50" readonly onclick="SelectDate(this,0);" />
														<script language="javascript">
															var d = new Date(); 
															d.setTime(d.getTime()-1000*60*60*24);   
															var year = d.getYear(); 
															var month = d.getMonth()+1; 
															var date = d.getDate(); 
															if(month<10){
																month="0"+month;
															}
															if(date<10){
																date="0"+date;
															}
															$("sj1").value=year+"-"+month+"-"+date;
															$("sj2").value=year+"-"+month+"-"+date;
														</script>
													</td>
													<td width=60></td>
												</tr>
												<tr align="center" height="35">
													<%
														//总队用户
														if("0000".equals(tbdwid.substring(2,6))){
													%>
													
													
													<td align="center" class="currentLocation" style="text-align:right;">支队：
			
			
													</td>
													<td align="center" class="currentLocation" id="zhiduiTdId">
													<script language="javascript"> 
														fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00'","全部","","zongduiOnChange");
													</script>
													</td>
													<td align="center" class="currentLocation" style="text-align:right;">大队：
			
			
													</td>
													<td align="center" class="currentLocation" id="daduiTdId" colspan="3">
														<script language="javascript">
															fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","");
														</script>
													</td>
													
													<%
														//支队用户
														}else if("00".equals(tbdwid.substring(4,6))){
													%>
													
													<td align="center" class="currentLocation" style="text-align:right;">支队：
			
			
													</td>
													<td align="center" class="currentLocation" id="zhiduiTdId">
														<script language="javascript">
														   fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=tbdwid%>'","未选择","zhiduiCallback('<%=tbdwid%>')");
														</script>
													</td>
													<td align="center" class="currentLocation" style="text-align:right;">大队：
			
			
													</td>
													<td align="center" class="currentLocation" id="daduiTdId" colspan="3">
													</td>
													<%
														//大队用户
														}else {
													%>
													<td align="center" class="currentLocation" style="text-align:right;">大队：</td>
													<td align="center" class="currentLocation" id="daduiTdId" colspan="5">
														<script language="javascript">
														   fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=tbdwid%>'","未选择","daduiCallback('<%=tbdwid%>')");
														</script>
													</td>
													<% } %>
													<td>
														<div class="search currentLocation" style="float:right;margin-right:25px;">
															<a href="#" onclick="doOnLoad()"
																class="currentLocation"> <span class="lbl">查询</span>
															</a>
														</div>
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
										<td id="tdData">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
												<tr class="titleTopBack">
													<th width='16%' class='td_r_b td_font'>
														填报单位
													</th>
													<th width='13%' class='td_r_b td_font'>
														统计日期
													</th>
													<th width='12%' class='td_r_b td_font'>
														填表人
													</th>
													<th width='12%' class='td_r_b td_font'>
														审核人
													</th>
													<th width='12%' class='td_r_b td_font'>
														联系电话
													</th>
													<th width='5%' class='td_r_b td_font'>明细</th>
													<th width='5%' class='td_r_b td_font'>修改</th>
													<th width='5%' class='td_r_b td_font'>删除</th>
												</tr>
											</table>
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
