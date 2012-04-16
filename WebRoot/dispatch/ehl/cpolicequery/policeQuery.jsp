<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.common.*" %>
<%@include file="../../../base/Message.oni"%>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String depttype = ""; //机构类型   
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		depttype = (String)prop.get("DEPTTYPE");
	}
	String timer = com.appframe.common.Setting.getString("msgWatchingTimeRange");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>警情信息查询</title>
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/popup/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/DivWindow.js"></script>
		<script type="text/javascript" src="../../js/cpolicequery/PoliceRemind.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/cpolicequery/PoliceQuery.js"></script>
		<style type="text/css">
			.td_font{
				font-size:13px;
			}
		</style>
		<script type="text/javascript">
			var deptcode = '<%=deptcode%>';
			var depttype = '<%= depttype %>';
			var timer = '<%=timer%>';
			var isRefresh = true;
			if(depttype != '2'){
				timeShowAlarm(timer);
			}
			
		</script>
	</head>
	<body style="background:none; padding-top:0px;"  onload="doQuery('init');">
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
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：警情上报系统-警情信息查询
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
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr align="center" height="35">
													<td width="10%" align="right" class="currentLocation">
														<p align="right">警情类型：</p>
													</td>
													<td width="12%" id="policeType_td" align="left">
														<script language="javascript">
										            		fillListBox("policeType_td","policeType","100","SELECT T.ID,T.NAME FROM T_ATTEMPER_CODE T WHERE T.ID LIKE '001%' AND T.ISSHOW='1'","","");
										               	</script> 			          
											        </td>
													<td width="10%" align="right" class="currentLocation">
														<p align="right">开始时间：</p>
													</td>
													<td width="20%" align="left">
														<input type="text" id="startTime" readonly style="width:170" onclick="SelectDateTime(this)" />
													</td>
													<td width="10%" align="right" class="currentLocation">
														<p align="right">结束时间：</p>
													</td>
											        <td align="center" width="15%" class="currentLocation">
														<input type="text" id="endsTime" readonly style="width:170" onclick="SelectDateTime(this)" />
													</td>
													<td align="center" width="15%" class="currentLocation">
														<div style="display:none;">&nbsp;&nbsp;&nbsp;需关注<input type="checkbox" id="xgz" name="editinput"></div>
													</td>
											    </tr>
											    <tr align="center" height="35">
											    	<td width="10%" align="right" class="currentLocation">
														<p align="right">警情状态：</p>
													</td>
													<td width="12%" align="left" id="policeState_td">
														<script language="javascript">
										            		fillListBox("policeState_td","policeState","100","SELECT T.ID,T.NAME FROM T_ATTEMPER_CODE T WHERE T.ID LIKE '004%' AND T.ISSHOW='1'","","");
										               	</script> 
													</td>
													<%
														//总队用户
														if("0".equals(depttype)){
													%>
													<td align="center" width="12%" class="currentLocation" style="text-align:right;">支队：</td>
													<td align="center" width="20%" class="currentLocation" id="zhiduiTdId">
														<script language="javascript"> 
															fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00'","全部","","zongduiOnChange");
														</script>
													</td>
													<td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：</td>
													<td align="center" width="20%" class="currentLocation" id="daduiTdId">
														<script language="javascript">
															fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","");
														</script>
													</td>
													<%
														//支队用户
														}else if("1".equals(depttype)){
													%>
													<td align="center" width="12%" class="currentLocation" style="text-align:right;">支队：</td>
													<td align="center" width="20%" class="currentLocation" id="zhiduiTdId">
														<script language="javascript">
														   fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=deptcode%>'","未选择","zhiduiCallback('<%=deptcode%>')");
														</script>
													</td>
													<td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：</td>
													<td align="center" width="20%"  class="currentLocation" id="daduiTdId"></td>
													<%
														//大队用户
														}else  if("2".equals(depttype)){
													%>
													<td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：</td>
													<td align="center" width="20%" class="currentLocation" id="daduiTdId">
														<script language="javascript">
														   fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=deptcode%>'","未选择","daduiCallback('<%=deptcode%>')");
														</script>
													</td>
													<td align="center" width="12%"  class="currentLocation"></td>
													<td align="center" width="20%"  class="currentLocation"></td>
													<% }else{ %>
													<td align="center" width="12%"  class="currentLocation"></td>
													<td align="center" width="20%"  class="currentLocation"></td>
													<td align="center" width="12%"  class="currentLocation"></td>
													<td align="center" width="20%"  class="currentLocation"></td>
													<% }%>
											        <td align="center" width="15%" class="currentLocation">
														<div class="search">
															<a href="javascript:doQuery();"><span class="lbl">查询</span></a>
														</div>
													</td>
											    </tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<tr>
										<td class="height"></td>
									</tr>
									<tr>
										<td id="tdData">
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
												<tr class="titleTopBack">
													<th width='8%' class='td_r_b td_font'>警情类型</th>
													<th width='14%' class='td_r_b td_font'>上报时间</th>
													<th width='15%' class='td_r_b td_font'>上报机构</th>
													<th width='28%' class='td_r_b td_font'>警情摘要</th>
													<th width='10%' class='td_r_b td_font'>警情状态</th>
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
